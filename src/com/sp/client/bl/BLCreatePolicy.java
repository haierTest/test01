package com.sp.client.bl;

import java.sql.ResultSet;
import java.util.*;
import com.sp.sysframework.exceptionlog.UserException;
import com.sp.sysframework.reference.DBManager;
import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;
import com.sp.utiall.blsvr.*;
import com.sp.utiall.dbsvr.*;
import com.sp.utiall.schema.*;
import com.sp.client.dto.*;
import com.sp.prpall.resource.dtofactory.domain.*;
import com.sp.prpall.blsvr.tb.*;
import com.sp.prpall.blsvr.cb.*;
import com.sp.prpall.blsvr.jf.BLPrpQueryPaymentService;
import com.sp.prpall.schema.*;
import com.sp.platform.dto.domain.PrpDriskConfigDto;
import com.sp.platform.ui.control.action.UIPrpDriskConfigAction;
import com.sp.utiall.dbsvr.DBPrpDagent;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BLCreatePolicy {
	static Log logger = LogFactory.getLog(BLCreatePolicy.class.getName());
	public static void createPolicy(Policy policy, DbPool dbpool,
		DBManager dbManager) throws UserException, Exception {
	    DbPool paymentDbpool = new DbPool();
		PrpTmainSchema prpTmainSchema = createPrpTmain(policy,dbManager);
		PrpCmainSchema prpCmainSchema = createPrpCmain(policy, prpTmainSchema);
		ArrayList prpTengageSchemaList = createPrpTengage(policy);
		ArrayList prpCengageSchemaList = createPrpCengage(policy);
		PrpTplanSchema prpTPlanSchema = createPrpTplan(policy);
		PrpCplanSchema prpCPlanSchema = createPrpCplan(policy);
		PrpTfeeSchema prpTfeeSchema = createPrpTfee(policy);
		PrpCfeeSchema prpCfeeSchema = createPrpCfee(policy);
		PrpTaddressSchema prpTaddressSchema = createPrpTaddress(policy);
		PrpCaddressSchema prpCaddressSchema = createPrpCaddress(policy);
		ArrayList prpTitemKind = createPrpTitemKindSchema(policy);
		ArrayList prpCitemKind = createPrpCitemKindSchema(policy);
		PrpTitemCarSchema prpTitemCarSchema = createPrpTitemCarSchema(policy);
		PrpCitemCarSchema prpCitemCarSchema = createPrpCitemCarSchema(policy);
		PrpTinsuredSchema prpTinsuredSchema1 = createPrpTinsured(policy,prpTmainSchema,"1");
		PrpTinsuredSchema prpTinsuredSchema2 = createPrpTinsured(policy,prpTmainSchema,"2");
		PrpCinsuredSchema prpCinsuredSchema1 = createPrpCinsured(policy,prpCmainSchema,"1");
		PrpCinsuredSchema prpCinsuredSchema2 = createPrpCinsured(policy,prpCmainSchema,"2");
		PrpTinsuredNatureSchema prpTinsuredNatureSchema = createPrpTinsuredNatureSchema(policy,prpTmainSchema);
		PrpCinsuredNatureSchema prpCinsuredNatureSchema = createPrpCinsuredNatureSchema(policy,prpCmainSchema);
		PrpCinsuredArtifSchema prpCinsuredArtifSchema = createPrpCinsuredArtifSchema( policy, prpCmainSchema);
		PrpTinsuredArtifSchema prpTinsuredArtifSchema = createPrpTinsuredArtifSchema( policy, prpTmainSchema);
		BLProposal blProposal = new BLProposal();
		BLPrpTmain blPrpTmain = new BLPrpTmain();
		BLPrpTinsured blPrpTinsured = new BLPrpTinsured();
		BLPrpTinsuredNature bLPrpTinsuredNature = new BLPrpTinsuredNature(); 
		BLPrpTinsuredArtif bLPrpTinsuredArtif = new BLPrpTinsuredArtif(); 
		BLPrpTitemCar blPrpTitemCar = new BLPrpTitemCar();
		BLPrpTitemKind blPrpTitemKind = new BLPrpTitemKind();
		BLPrpTplan blPrpTplan = new BLPrpTplan();
		BLPrpTengage blPrpTengage = new BLPrpTengage();
		BLPrpTfee blPrpTfee = new BLPrpTfee();
		BLPrpTaddress blPrpTaddress = new BLPrpTaddress();
		BLPrpTexpense blPrpTexpense = new BLPrpTexpense();
		BLPrpTitemCarExt blPrpTitemCarExt = new BLPrpTitemCarExt();
		BLPrpTlimit blPrpTlimit = new BLPrpTlimit();
		blPrpTmain.setArr(prpTmainSchema);
		Iterator itTitemKind = prpTitemKind.iterator();
		for (int i = 0; i < prpTitemKind.size(); i++) {
			blPrpTitemKind.setArr((PrpTitemKindSchema) itTitemKind.next());
		}
		Iterator itEngage = null;
		if ( prpTengageSchemaList != null ){
			itEngage = prpTengageSchemaList.iterator();
			while(itEngage.hasNext() ){
				blPrpTengage.setArr((PrpTengageSchema)itEngage.next());
			}
		}
		
		blPrpTinsured.setArr(prpTinsuredSchema1);
		blPrpTinsured.setArr(prpTinsuredSchema2);
		if ( policy.getbaseInfoBase().getC_PROPERTY().equals("3")){
			bLPrpTinsuredNature.setArr(prpTinsuredNatureSchema);
			blProposal.setBLPrpTinsuredNature(bLPrpTinsuredNature);
		}else{
			bLPrpTinsuredArtif.setArr(prpTinsuredArtifSchema );
			blProposal.setBLPrpTinsuredArtif(bLPrpTinsuredArtif);
		}
		blPrpTitemCar.setArr(prpTitemCarSchema);
		blPrpTplan.setArr(prpTPlanSchema);
		blPrpTfee.setArr(prpTfeeSchema);
		blPrpTaddress.setArr(prpTaddressSchema);
		blPrpTexpense = createPrpTexpense(policy);
		blPrpTitemCarExt = createPrpTitemCarExt(policy.getbaseInfoBase().getPlyAppNo());
		blPrpTlimit = createPrpTlimit(policy.getbaseInfoBase().getPlyAppNo());
		if ( null!=policy.getbaseInfoBase().getAppnt()){
			blProposal.setBLPrpTengage(blPrpTengage);
		}
		blProposal.setBLPrpTmain(blPrpTmain);
		blProposal.setBLPrpTitemCar(blPrpTitemCar);
		blProposal.setBLPrpTitemCarExt(blPrpTitemCarExt);
		blProposal.setBLPrpTitemKind(blPrpTitemKind);
		blProposal.setBLPrpTplan(blPrpTplan);
		blProposal.setBLPrpTfee(blPrpTfee);
		blProposal.setBLPrpTaddress(blPrpTaddress);

		blProposal.setBLPrpTexpense(blPrpTexpense);
		blProposal.setBLPrpTitemCarExt(blPrpTitemCarExt);
		blProposal.setBLPrpTinsured(blPrpTinsured);
		
		BLPolicy blPolicy = new BLPolicy();
		BLPrpCmain blPrpCmain = new BLPrpCmain();
		BLPrpCitemCar blPrpCitemCar = new BLPrpCitemCar();
		BLPrpCitemKind blPrpCitemKind = new BLPrpCitemKind();
		BLPrpCexpense blPrpCexpense = new BLPrpCexpense();
		BLPrpCitemCarExt blPrpCitemCarExt = new BLPrpCitemCarExt();
		BLPrpClimit blPrpClimit = new BLPrpClimit();
		BLPrpCplan blPrpCplan = new BLPrpCplan();
		BLPrpCfee blPrpCfee = new BLPrpCfee();
		BLPrpCaddress blPrpCaddress = new BLPrpCaddress();
		BLPrpCinsured blPrpCinsured = new BLPrpCinsured();
		BLPrpCinsuredNature bLPrpCinsuredNature = new BLPrpCinsuredNature(); 
		BLPrpCinsuredArtif bLPrpCinsuredArtif = new BLPrpCinsuredArtif(); 
		BLPrpCengage blPrpCengage = new BLPrpCengage();
		blPrpCmain.setArr(prpCmainSchema);
		Iterator itCitemKind = prpCitemKind.iterator();
		for (int i = 0; i < prpCitemKind.size(); i++) {
			blPrpCitemKind.setArr((PrpCitemKindSchema) itCitemKind.next());
		}
		if ( prpCengageSchemaList != null ){
			itEngage = prpCengageSchemaList.iterator();
			while(itEngage.hasNext() ){
				blPrpCengage.setArr((PrpCengageSchema)itEngage.next());
			}
		}
		blPrpCinsured.setArr(prpCinsuredSchema1);
		blPrpCinsured.setArr(prpCinsuredSchema2);
		if ( policy.getbaseInfoBase().getC_PROPERTY().equals("3")){
			bLPrpCinsuredNature.setArr(prpCinsuredNatureSchema);
			blPolicy.setBLPrpCinsuredNature(bLPrpCinsuredNature);
		}else{
			bLPrpCinsuredArtif.setArr(prpCinsuredArtifSchema);
			blPolicy.setBLPrpCinsuredArtif(bLPrpCinsuredArtif);
		}
		
		blPrpCitemCar.setArr(prpCitemCarSchema);
		blPrpCplan.setArr(prpCPlanSchema);
		blPrpCfee.setArr(prpCfeeSchema);
		blPrpCaddress.setArr(prpCaddressSchema);
		blPrpCexpense = createPrpCexpense(policy);
		blPrpCitemCarExt = createPrpCitemCarExt(policy.getbaseInfoBase().getPlyNo());
		blPrpClimit = createPrpClimit(policy.getbaseInfoBase().getPlyNo());
		if ( null!=policy.getbaseInfoBase().getAppnt()){
			blPolicy.setBLPrpCengage(blPrpCengage);
		}
		blPolicy.setBLPrpCinsured(blPrpCinsured);
		
		blPolicy.setBLPrpCmain(blPrpCmain);
		blPolicy.setBLPrpCitemCar(blPrpCitemCar);
		blPolicy.setBLPrpCitemCarExt(blPrpCitemCarExt);
		blPolicy.setBLPrpCitemKind(blPrpCitemKind);
		blPolicy.setBLPrpCplan(blPrpCplan);
		blPolicy.setBLPrpCfee(blPrpCfee);
		blPolicy.setBLPrpCaddress(blPrpCaddress);

		blPolicy.setBLPrpCexpense(blPrpCexpense);
		blPolicy.setBLPrpCitemCarExt(blPrpCitemCarExt);
		BLClient blClient = new BLClient();
		
	    PrpJpayRefRecSchema prpJpayRefRecSchema = new PrpJpayRefRecSchema();
	    prpJpayRefRecSchema = BLCreatePolicy.createPrpJpayRefRecSchema(policy);
		blClient.savePolicyAndOther(dbpool, dbManager, blProposal, blPolicy,
				policy.getvchList(),prpJpayRefRecSchema);
		Iterator it = policy.getpayList().iterator();
	    PayInfoBean pib = null;
	    try{
	    	pib = (PayInfoBean)it.next();
	    }catch(Exception e){
	    	throw new UserException(-98, -1007, "BLCreatePolicy",
			"收付款信息！");
	    }
        try {
            
            String strSwitch = BLPrpQueryPaymentService.queryMirrorSwitch();          
            if ("1".equals(strSwitch)) {
              BLPrpQueryPaymentService.querySwitch(paymentDbpool, 2);
            }else{
                paymentDbpool.open("undwrtDataSource");
            } 
            paymentDbpool.beginTransaction();
            
            int ret = paymentDbpool.executeUpdate("update prpjpayrefrec set plandate = '" + pib.getD_PAY_DATE()
                    + "' where policyno = '" + policy.getbaseInfoBase().getPlyNo() + "'");
            if (ret <= 0) {
                throw new UserException(-98, -1007, "com.ygbx.singleInterface.bl.BLCreatePolicy", "收付款确认失败！");
            } 
            paymentDbpool.commitTransaction();
	    }catch (Exception e) {
            e.printStackTrace();
            dbpool.rollbackTransaction();
            paymentDbpool.rollbackTransaction();
        } finally {
            dbpool.close();
            paymentDbpool.close();
        } 
	}

	public static PrpTmainSchema createPrpTmain(Policy policy,DBManager dbManager)
			throws UserException,Exception {
		PrpTmainSchema prpTmainSchema = new PrpTmainSchema();
		prpTmainSchema.setProposalNo(policy.getbaseInfoBase().getPlyAppNo());
		prpTmainSchema.setClassCode("05");
		prpTmainSchema.setRiskCode("0507");
		prpTmainSchema.setContractNo("");
		prpTmainSchema.setPolicySort("2");
		prpTmainSchema.setPrintNo("");
		
		prpTmainSchema
				.setBusinessNature(policy.getbaseInfoBase().getBsnsType());
		prpTmainSchema.setLanguage("C");
		prpTmainSchema.setPolicyType("99");
		if (policy.getbaseInfoBase().getAppCnm().equals(
				policy.getbaseInfoBase().getInsrntCnm())) {
			
			String stCustomerCode = getCustomerCode(policy);
			stCreateInsrntCustomerIDv(policy, stCustomerCode);
			prpTmainSchema.setAppliCode(stCustomerCode);
			prpTmainSchema.setAppliName(policy.getbaseInfoBase().getAppCnm());
			prpTmainSchema.setAppliAddress(policy.getbaseInfoBase()
					.getInsrntAdd());
			prpTmainSchema.setInsuredCode(stCustomerCode);
			prpTmainSchema.setInsuredName(policy.getbaseInfoBase()
					.getInsrntCnm());
			prpTmainSchema.setInsuredAddress(policy.getbaseInfoBase()
					.getInsrntAdd());
		} else {
			String stAppCustomerCode = getCustomerCode(policy);
			stCreateAppCustomerIDv(policy, stAppCustomerCode);
			prpTmainSchema.setAppliCode(stAppCustomerCode);
			prpTmainSchema.setAppliName(policy.getbaseInfoBase().getAppCnm());
			prpTmainSchema.setAppliAddress(policy.getbaseInfoBase()
					.getInsrntAdd());
			String stInsCustomerCode = getCustomerCode(policy);
			stCreateInsrntCustomerIDv(policy, stInsCustomerCode);
			prpTmainSchema.setInsuredCode(stInsCustomerCode);
			prpTmainSchema.setInsuredName(policy.getbaseInfoBase()
					.getInsrntCnm());
			prpTmainSchema.setInsuredAddress(policy.getbaseInfoBase()
					.getInsrntAdd());
		}

		prpTmainSchema.setOperateDate(policy.getbaseInfoBase().getAppDate());
		prpTmainSchema.setStartDate(policy.getbaseInfoBase().getInsbgnDate());
		prpTmainSchema.setStartHour("");
		prpTmainSchema.setEndDate(policy.getbaseInfoBase().getInsrntDate());
		prpTmainSchema.setEndHour("");
		prpTmainSchema.setPureRate(Double.toString(policy.getbaseInfoBase()
				.getCmmProp()));
		prpTmainSchema.setDisRate(Double.toString(policy.getbaseInfoBase()
				.getCmmProp()));
		prpTmainSchema.setDiscount("");
		prpTmainSchema.setCurrency(policy.getbaseInfoBase().getCurCde());
		prpTmainSchema.setSumValue(Double.toString(policy.getbaseInfoBase()
				.getAmt()));
		prpTmainSchema.setSumAmount(Double.toString(policy.getbaseInfoBase()
				.getAmt()));
		java.util.ArrayList rdrList = policy.getrdrList();
		Iterator it = rdrList.iterator();
		double dDisCount = 0;
		while (it.hasNext()) {
			RdrInfoBean rdrInfoBean = (RdrInfoBean) it.next();
			dDisCount = dDisCount
					+ Double.parseDouble(rdrInfoBean.getN_DIS_PRM())
					- Double.parseDouble(rdrInfoBean.getN_PRM());
		}
		prpTmainSchema.setSumDiscount(Double.toString(dDisCount));
		prpTmainSchema.setSumPremium(Double.toString(policy.getbaseInfoBase()
				.getPrm()));
		prpTmainSchema.setSumSubPrem("");
		prpTmainSchema.setSumQuantity("1");
		prpTmainSchema.setJudicalCode("");
		prpTmainSchema.setJudicalScope("");
		prpTmainSchema.setAutoTransRenewFlag("1");
		prpTmainSchema.setArgueSolution("1");
		prpTmainSchema.setArbitBoardName("");
		prpTmainSchema.setPayTimes("1");
		prpTmainSchema.setEndorseTimes("0");
		prpTmainSchema.setClaimTimes("0");
		prpTmainSchema.setMakeCom(policy.getbaseInfoBase().getDptCde());
		prpTmainSchema.setOperateSite("");
		prpTmainSchema.setComCode(policy.getbaseInfoBase().getDptCde());
		prpTmainSchema.setHandlerCode(policy.getbaseInfoBase().getC_CRT_CDE());
		prpTmainSchema.setHandler1Code(policy.getbaseInfoBase().getSlsCde());
		prpTmainSchema.setApproverCode(policy.getbaseInfoBase().getC_CRT_CDE());
		prpTmainSchema.setUnderWriteCode(policy.getbaseInfoBase().getC_CRT_CDE());
		prpTmainSchema.setUnderWriteName(policy.getbaseInfoBase().getC_CRT_CDE());
		prpTmainSchema.setOperatorCode(policy.getbaseInfoBase().getC_CRT_CDE());
		prpTmainSchema.setInputDate(policy.getbaseInfoBase().getAppDate());
		prpTmainSchema.setInputHour("");
		prpTmainSchema.setUnderWriteEndDate(policy.getbaseInfoBase()
				.getAppDate());
		prpTmainSchema.setStatisticsYM("");
		
		prpTmainSchema.setCoinsFlag("0");
		prpTmainSchema.setReinsFlag("0");
		prpTmainSchema.setAllinsFlag("0");
		prpTmainSchema.setUnderWriteFlag("1");
		
		logger.info("********************");
		logger.info(policy.getbaseInfoBase().getC_TAT_CLNT());
		
		if ( policy.getbaseInfoBase().getC_TAT_CLNT().equals("0")){
			prpTmainSchema.setOthFlag("000000YY000000000000");
		}else{
			prpTmainSchema.setOthFlag("000000YY000000100000");
		}
		prpTmainSchema.setFlag("");
		prpTmainSchema.setDisRate1("0");
		prpTmainSchema.setBusinessFlag("");
		prpTmainSchema.setPayMode("0");
		prpTmainSchema.setUpdaterCode("");
		prpTmainSchema.setUpdateDate("");
		prpTmainSchema.setUpdateHour("");
		prpTmainSchema.setSignDate(policy.getbaseInfoBase().getSignDate());
		prpTmainSchema.setShareHolderFlag("0");
		prpTmainSchema.setAgentCode(policy.getbaseInfoBase().getAgtCde());
		prpTmainSchema.setAgreementNo(policy.getbaseInfoBase().getAgtNo());
		String stQueryCondition = " select agenttype from PrpDagent where agentcode = '"+policy.getbaseInfoBase().getAgtCde()+"'";
        
        ResultSet rs = null;
        DbPool dbpoolNew = new DbPool();
        try {
            String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
            if ("1".equals(strSwitch)) {
                dbpoolNew.open("platformNewDataSource");
        		rs = dbpoolNew.executeQuery(stQueryCondition);
            } else {
        		rs = dbManager.executeQuery(stQueryCondition);
            }
    		if (rs.next()) {
    			prpTmainSchema.setBusinessNature(rs.getString("agenttype"));
    		}prpTmainSchema.setInquiryNo("");
    		prpTmainSchema.setPolicyNo(policy.getbaseInfoBase().getPlyNo());
    		prpTmainSchema.setVisaCode("");
    		prpTmainSchema.setManualType("");
    		prpTmainSchema.setRemark(policy.getbaseInfoBase().getNotice());
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            dbpoolNew.close();
        }
        
		return prpTmainSchema;
	}

	public static PrpCmainSchema createPrpCmain(Policy policy,
			PrpTmainSchema prpTmainSchema) throws UserException,Exception {
		PrpCmainSchema prpCmainSchema = new PrpCmainSchema();
		prpCmainSchema.setProposalNo(policy.getbaseInfoBase().getPlyAppNo());
		prpCmainSchema.setClassCode("05");
		prpCmainSchema.setRiskCode("0507");
		prpCmainSchema.setContractNo("");
		prpCmainSchema.setPolicySort("2");
		prpCmainSchema.setPrintNo("");
		prpCmainSchema.setBusinessNature(policy.getbaseInfoBase().getAgtCde()
				.equals("") ? "1" : "2");
		prpCmainSchema.setLanguage("C");
		prpCmainSchema.setPolicyType("99");

		prpCmainSchema.setAppliCode(prpTmainSchema.getAppliCode());
		prpCmainSchema.setAppliName(policy.getbaseInfoBase().getAppCnm());
		prpCmainSchema.setAppliAddress(policy.getbaseInfoBase().getInsrntAdd());
		prpCmainSchema.setInsuredCode(prpTmainSchema.getInsuredCode());
		prpCmainSchema.setInsuredName(policy.getbaseInfoBase().getInsrntCnm());
		prpCmainSchema.setInsuredAddress(policy.getbaseInfoBase()
				.getInsrntAdd());

		prpCmainSchema.setOperateDate(policy.getbaseInfoBase().getAppDate());
		prpCmainSchema.setStartDate(policy.getbaseInfoBase().getInsbgnDate());
		prpCmainSchema.setStartHour("");
		prpCmainSchema.setEndDate(policy.getbaseInfoBase().getInsrntDate());
		prpCmainSchema.setEndHour("");
		prpCmainSchema.setPureRate(Double.toString(policy.getbaseInfoBase()
				.getCmmProp()));
		prpCmainSchema.setDisRate(Double.toString(policy.getbaseInfoBase()
				.getCmmProp()));
		prpCmainSchema.setDiscount("");
		prpCmainSchema.setCurrency(stChangeMoney(policy.getbaseInfoBase().getCurCde()));
		prpCmainSchema.setSumValue(Double.toString(policy.getbaseInfoBase()
				.getAmt()));
		prpCmainSchema.setSumAmount(Double.toString(policy.getbaseInfoBase()
				.getAmt()));
		java.util.ArrayList rdrList = policy.getrdrList();
		Iterator it = rdrList.iterator();
		double dDisCount = 0;
		while (it.hasNext()) {
			RdrInfoBean rdrInfoBean = (RdrInfoBean) it.next();
			dDisCount = dDisCount
					+ Double.parseDouble(rdrInfoBean.getN_DIS_PRM())
					- Double.parseDouble(rdrInfoBean.getN_PRM());
		}
		prpCmainSchema.setSumDiscount(Double.toString(dDisCount));
		prpCmainSchema.setSumPremium(Double.toString(policy.getbaseInfoBase()
				.getPrm()));
		prpCmainSchema.setSumSubPrem("");
		prpCmainSchema.setSumQuantity("1");
		prpCmainSchema.setJudicalCode("");
		prpCmainSchema.setJudicalScope("");
		prpCmainSchema.setAutoTransRenewFlag("1");
		prpCmainSchema.setArgueSolution("1");
		prpCmainSchema.setArbitBoardName("");
		prpCmainSchema.setPayTimes("1");
		prpCmainSchema.setEndorseTimes("0");
		prpCmainSchema.setClaimTimes("0");
		prpCmainSchema.setMakeCom(policy.getbaseInfoBase().getDptCde());
		prpCmainSchema.setOperateSite("");
		prpCmainSchema.setComCode(policy.getbaseInfoBase().getDptCde());
		prpCmainSchema.setHandlerCode(policy.getbaseInfoBase().getC_CRT_CDE());
		prpCmainSchema.setHandler1Code(policy.getbaseInfoBase().getSlsCde());
		prpCmainSchema.setApproverCode(policy.getbaseInfoBase().getC_CRT_CDE());
		prpCmainSchema.setUnderWriteCode(policy.getbaseInfoBase().getC_CRT_CDE());
		prpCmainSchema.setUnderWriteName(policy.getbaseInfoBase().getC_CRT_CDE());
		prpCmainSchema.setOperatorCode(policy.getbaseInfoBase().getC_CRT_CDE());
		prpCmainSchema.setInputDate(policy.getbaseInfoBase().getAppDate());
		prpCmainSchema.setInputHour("");
		prpCmainSchema.setUnderWriteEndDate(policy.getbaseInfoBase()
				.getAppDate());
		prpCmainSchema.setStatisticsYM("");
		
		prpCmainSchema.setCoinsFlag("0");
		prpCmainSchema.setReinsFlag("0");
		prpCmainSchema.setAllinsFlag("0");
		prpCmainSchema.setUnderWriteFlag("1");
		prpCmainSchema.setOthFlag(prpTmainSchema.getOthFlag());
		prpCmainSchema.setFlag("");
		prpCmainSchema.setDisRate1("0");
		prpCmainSchema.setBusinessFlag("");
		prpCmainSchema.setPayMode("0");
		prpCmainSchema.setUpdaterCode("");
		prpCmainSchema.setUpdateDate("");
		prpCmainSchema.setUpdateHour("");
		prpCmainSchema.setSignDate(policy.getbaseInfoBase().getSignDate());
		prpCmainSchema.setShareHolderFlag("0");
		prpCmainSchema.setAgentCode(prpCmainSchema.getAgentCode());
		prpCmainSchema.setAgentCode(policy.getbaseInfoBase().getAgtCde());
		prpCmainSchema.setAgreementNo(policy.getbaseInfoBase().getAgtNo());
		String stQueryCondition = " select agenttype from PrpDagent where agentcode = '"+policy.getbaseInfoBase().getAgtCde()+"'";
		prpCmainSchema.setBusinessNature(prpTmainSchema.getBusinessNature());
		prpCmainSchema.setInquiryNo("");
		prpCmainSchema.setPolicyNo(policy.getbaseInfoBase().getPlyNo());
		prpCmainSchema.setVisaCode("");
		prpCmainSchema.setManualType("");
		prpCmainSchema.setRemark(policy.getbaseInfoBase().getNotice());
		return prpCmainSchema;
	}

	public static PrpTplanSchema createPrpTplan(Policy policy) {
		PrpTplanSchema prpTplanSchema = new PrpTplanSchema();
		prpTplanSchema.setProposalNo(policy.getbaseInfoBase().getPlyAppNo());
		prpTplanSchema.setEndorseNo("");
		prpTplanSchema.setSerialNo("1");
		prpTplanSchema.setPayNo("1");
		prpTplanSchema.setPayReason("R10");
		String stPayDate = null;
		String dGetPrm = "0";
		String dGotPrm = "0";
		ArrayList payList = policy.getpayList();
		Iterator it = payList.iterator();
		while (it.hasNext()) {
			PayInfoBean payInfoBean = (PayInfoBean) it.next();
			stPayDate = payInfoBean.getD_PAY_DATE();
			dGetPrm = payInfoBean.getN_GET_PRM();
			dGotPrm = payInfoBean.getN_GOT_PRM();
		}

		prpTplanSchema.setPlanDate(policy.getbaseInfoBase().getInsbgnDate());
		prpTplanSchema.setCurrency(stChangeMoney(policy.getbaseInfoBase().getCurCde()));
		prpTplanSchema.setPlanFee(dGetPrm);
		prpTplanSchema.setDelinquentFee("0");
		prpTplanSchema.setFlag("");

		prpTplanSchema.setPlanStartDate(policy.getbaseInfoBase().getAppDate());
		
		return prpTplanSchema;
	}

	public static PrpCplanSchema createPrpCplan(Policy policy) {
		PrpCplanSchema prpCplanSchema = new PrpCplanSchema();
		prpCplanSchema.setPolicyNo(policy.getbaseInfoBase().getPlyNo());
		prpCplanSchema.setEndorseNo("");
		prpCplanSchema.setSerialNo("1");
		prpCplanSchema.setPayNo("1");
		prpCplanSchema.setPayReason("R10");
		String stPayDate = null;
		String dGetPrm = "0";
		String dGotPrm = "0";
		ArrayList payList = policy.getpayList();
		Iterator it = payList.iterator();
		while (it.hasNext()) {
			PayInfoBean payInfoBean = (PayInfoBean) it.next();
			stPayDate = payInfoBean.getD_PAY_DATE();
			dGetPrm = payInfoBean.getN_GET_PRM();
			dGotPrm = payInfoBean.getN_GOT_PRM();
		}

		prpCplanSchema.setPlanStartDate(policy.getbaseInfoBase().getInsbgnDate());
		prpCplanSchema.setCurrency(stChangeMoney(policy.getbaseInfoBase().getCurCde()));
		prpCplanSchema.setPlanFee(dGetPrm);
		prpCplanSchema.setDelinquentFee("0");
		prpCplanSchema.setFlag("");

		prpCplanSchema.setPlanStartDate(policy.getbaseInfoBase().getAppDate());
		
		return prpCplanSchema;
	}

	public static PrpDcustomerSchema createDbcustomerSchema(Policy policy)
			throws UserException {
		PrpDcustomerSchema prpDcustomerSchema = new PrpDcustomerSchema();
		prpDcustomerSchema.setCustomerCName(policy.getbaseInfoBase()
				.getAppCnm());
		prpDcustomerSchema.setCustomerEName("");
		prpDcustomerSchema.setAddressCName(policy.getbaseInfoBase()
				.getInsrntAdd());
		prpDcustomerSchema.setAddressEName("");
		prpDcustomerSchema.setValidStatus("1");
		
		prpDcustomerSchema.setCustomerType("1");
		return prpDcustomerSchema;
	}

	public static PrpTitemCarSchema createPrpTitemCarSchema(Policy policy) {
		PrpTitemCarSchema prpTitemCarSchema = new PrpTitemCarSchema();
		prpTitemCarSchema.setProposalNo(policy.getbaseInfoBase().getPlyAppNo());
		prpTitemCarSchema.setRiskCode("0507");
		prpTitemCarSchema.setItemNo("1");
		prpTitemCarSchema.setInsuredTypeCode("");
		prpTitemCarSchema.setCarInsuredRelation(policy.getbaseInfoBase().getC_VHL_REL());
		prpTitemCarSchema.setCarOwner(policy.getbaseInfoBase().getOwnCnm());
		prpTitemCarSchema.setClauseType("F40");
		prpTitemCarSchema.setAgreeDriverFlag("0");
		prpTitemCarSchema.setNewDeviceFlag("0");
		prpTitemCarSchema.setCarPolicyno(policy.getbaseInfoBase().getPlyNo());
		prpTitemCarSchema.setLicenseNo(policy.getvhlInfoBean().getC_LCN_NO());
		prpTitemCarSchema.setLicenseColorCode("01");
		String stCarKind = policy.getvhlInfoBean().getC_VHL_TYP();
		String stUseType = policy.getvhlInfoBean().getC_USE_ATR();
		String stCarKindCode = null;
		String stUseNatureCode = null;
		String stCarNatureCode = null;
		if ( "02".equals(stUseType)&&stCarKind.substring(0,2).equals("11")){
			
			stCarKindCode = "A0";
			stUseNatureCode = "8A";
			stCarNatureCode="01";
		}
		if ( "03".equals(stUseType)&&stCarKind.substring(0,2).equals("11")){
			
			stCarKindCode = "A0";
			stUseNatureCode = "8B";
			stCarNatureCode="02";
		}
		if ( "04".equals(stUseType)&&stCarKind.substring(0,2).equals("11")){
			
			stCarKindCode = "A0";
			stUseNatureCode = "8C";
			stCarNatureCode="02";
		}
		if ( "06".equals(stUseType)&&stCarKind.substring(0,2).equals("21")){
			
			stCarKindCode = "H0";
			stUseNatureCode = "8C";
			stCarNatureCode="04";
		}
		if ( "07".equals(stUseType)&&stCarKind.substring(0,2).equals("11")){
			
			stCarKindCode = "A0";
			stUseNatureCode = "9A";
			stCarNatureCode="03";
		}
		if ( "08".equals(stUseType)&&stCarKind.substring(0,2).equals("11")){
			
			stCarKindCode = "A0";
			stUseNatureCode = "9B";
			stCarNatureCode="03";
		}
		if ( "09".equals(stUseType)&&stCarKind.substring(0,2).equals("11")){
			
			stCarKindCode = "A0";
			stUseNatureCode = "9C";
			stCarNatureCode="03";
		}
		if ( "10".equals(stUseType)&&stCarKind.substring(0,2).equals("21")){
			
			stCarKindCode = "H0";
			stUseNatureCode = "9C";
			stCarNatureCode="05";
		}
		if ( "11".equals(stUseType)&&stCarKind.substring(0,2).equals("31")){
			
			stCarKindCode = "T%";
			stUseNatureCode = "";
			stCarNatureCode="06";
		}
		if ( "12".equals(stUseType)&&stCarKind.substring(0,2).equals("41")){
			
			stCarKindCode = "M%";
			stUseNatureCode = "";
			stCarNatureCode="07";
		}
		if ( "13".equals(stUseType)&&stCarKind.substring(0,2).equals("51")){
			
			stCarKindCode = "J%";
			stUseNatureCode = "";
			stCarNatureCode="08";
		}
		prpTitemCarSchema.setCarKindCode(stCarKindCode);
		prpTitemCarSchema.setUseNatureCode(stUseNatureCode);
		
		prpTitemCarSchema.setHKFlag("");
		prpTitemCarSchema.setHKLicenseNo("");
		prpTitemCarSchema.setEngineNo(policy.getvhlInfoBean().getC_ENG_NO());
		prpTitemCarSchema.setVINNo(policy.getvhlInfoBean().getC_VHL_VIN());
		prpTitemCarSchema.setFrameNo(policy.getvhlInfoBean().getC_VHL_FRM());
		prpTitemCarSchema.setRunAreaCode("");
		prpTitemCarSchema.setRunAreaName("");
		prpTitemCarSchema.setRunMiles("");
		prpTitemCarSchema.setEnrollDate(policy.getvhlInfoBean()
				.getD_BOOK_DATE());
		prpTitemCarSchema.setUseYears("");
		prpTitemCarSchema.setModelCode(policy.getvhlInfoBean().getC_BRD_CDE());
		prpTitemCarSchema.setBrandName(policy.getvhlInfoBean().getC_BRD_CNM());
		prpTitemCarSchema.setCountryNature("A");
		prpTitemCarSchema.setCountryCode("");


		prpTitemCarSchema.setBusinessClassCode("");
		prpTitemCarSchema.setSeatCount(policy.getvhlInfoBean().getN_SEAT());
		prpTitemCarSchema.setTonCount(policy.getvhlInfoBean().getN_TON());
		prpTitemCarSchema.setExhaustScale("");
		prpTitemCarSchema
				.setColorCode(policy.getvhlInfoBean().getC_LCN_COLOR());
		prpTitemCarSchema.setSafeDevice("");
		prpTitemCarSchema.setParkSite("");
		prpTitemCarSchema.setOwnerAddress(policy.getbaseInfoBase()
				.getInsrntAdd());
		prpTitemCarSchema.setOtherNature("100111");
		prpTitemCarSchema.setRateCode("");

		prpTitemCarSchema.setCarUsage("");
		prpTitemCarSchema.setCurrency(stChangeMoney(policy.getbaseInfoBase().getCurCde()));
		prpTitemCarSchema.setPurchasePrice(policy.getvhlInfoBean()
				.getN_NEW_PRICE());
		prpTitemCarSchema.setActualValue(policy.getvhlInfoBean()
				.getN_NEW_PRICE());
		prpTitemCarSchema.setInvoiceNo("");
		prpTitemCarSchema.setCarLoanFlag("");
		prpTitemCarSchema.setInsurerCode("");
		prpTitemCarSchema.setLastInsurer("");
		prpTitemCarSchema.setCarCheckStatus("2");
		prpTitemCarSchema.setCarChecker("");
		prpTitemCarSchema.setCarCheckTime(policy.getvhlInfoBean()
				.getD_CHKVHL_DATE());
		prpTitemCarSchema.setSpecialTreat("");
		prpTitemCarSchema.setRelievingAreaCode("");
		prpTitemCarSchema.setAddonCount("");
		prpTitemCarSchema.setCarDealerCode("");
		prpTitemCarSchema.setCarDealerName("");
		prpTitemCarSchema.setRemark("");
		prpTitemCarSchema.setFlag("");
		prpTitemCarSchema.setCarCheckReason("");
		return prpTitemCarSchema;
	}

	public static PrpCitemCarSchema createPrpCitemCarSchema(Policy policy) {
		PrpCitemCarSchema prpCitemCarSchema = new PrpCitemCarSchema();
		prpCitemCarSchema.setProposalNo(policy.getbaseInfoBase().getPlyAppNo());
		prpCitemCarSchema.setPolicyNo(policy.getbaseInfoBase().getPlyNo());
		prpCitemCarSchema.setRiskCode("0507");
		prpCitemCarSchema.setItemNo("1");
		prpCitemCarSchema.setInsuredTypeCode("");
		prpCitemCarSchema.setCarInsuredRelation(policy.getbaseInfoBase().getC_VHL_REL());
		prpCitemCarSchema.setCarOwner(policy.getbaseInfoBase().getOwnCnm());
		prpCitemCarSchema.setClauseType("F40");
		prpCitemCarSchema.setAgreeDriverFlag("0");
		prpCitemCarSchema.setNewDeviceFlag("0");
		prpCitemCarSchema.setCarPolicyno(policy.getbaseInfoBase().getPlyNo());
		prpCitemCarSchema.setLicenseNo(policy.getvhlInfoBean().getC_LCN_NO());
		prpCitemCarSchema.setLicenseColorCode("01");
		String stCarKind = policy.getvhlInfoBean().getC_VHL_TYP();
		String stUseType = policy.getvhlInfoBean().getC_USE_ATR();
		String stCarKindCode = null;
		String stUseNatureCode = null;
		String stCarNatureCode = null;
		if ( "02".equals(stUseType)&&stCarKind.substring(0,2).equals("11")){
			
			stCarKindCode = "A0";
			stUseNatureCode = "8A";
			stCarNatureCode="01";
		}
		if ( "03".equals(stUseType)&&stCarKind.substring(0,2).equals("11")){
			
			stCarKindCode = "A0";
			stUseNatureCode = "8B";
			stCarNatureCode="02";
		}
		if ( "04".equals(stUseType)&&stCarKind.substring(0,2).equals("11")){
			
			stCarKindCode = "A0";
			stUseNatureCode = "8C";
			stCarNatureCode="02";
		}
		if ( "06".equals(stUseType)&&stCarKind.substring(0,2).equals("21")){
			
			stCarKindCode = "H0";
			stUseNatureCode = "8C";
			stCarNatureCode="04";
		}
		if ( "07".equals(stUseType)&&stCarKind.substring(0,2).equals("11")){
			
			stCarKindCode = "A0";
			stUseNatureCode = "9A";
			stCarNatureCode="03";
		}
		if ( "08".equals(stUseType)&&stCarKind.substring(0,2).equals("11")){
			
			stCarKindCode = "A0";
			stUseNatureCode = "9B";
			stCarNatureCode="03";
		}
		if ( "09".equals(stUseType)&&stCarKind.substring(0,2).equals("11")){
			
			stCarKindCode = "A0";
			stUseNatureCode = "9C";
			stCarNatureCode="03";
		}
		if ( "10".equals(stUseType)&&stCarKind.substring(0,2).equals("21")){
			
			stCarKindCode = "H0";
			stUseNatureCode = "9C";
			stCarNatureCode="05";
		}
		if ( "11".equals(stUseType)&&stCarKind.substring(0,2).equals("31")){
			
			stCarKindCode = "T%";
			stUseNatureCode = "";
			stCarNatureCode="06";
		}
		if ( "12".equals(stUseType)&&stCarKind.substring(0,2).equals("41")){
			
			stCarKindCode = "M%";
			stUseNatureCode = "";
			stCarNatureCode="07";
		}
		if ( "13".equals(stUseType)&&stCarKind.substring(0,2).equals("51")){
			
			stCarKindCode = "J%";
			stUseNatureCode = "";
			stCarNatureCode="08";
		}
		prpCitemCarSchema.setCarKindCode(stCarKindCode);
		prpCitemCarSchema.setUseNatureCode(stUseNatureCode);
		prpCitemCarSchema.setHKFlag("");
		prpCitemCarSchema.setHKLicenseNo("");
		prpCitemCarSchema.setEngineNo(policy.getvhlInfoBean().getC_ENG_NO());
		prpCitemCarSchema.setVINNo(policy.getvhlInfoBean().getC_VHL_VIN());
		prpCitemCarSchema.setFrameNo(policy.getvhlInfoBean().getC_VHL_FRM());
		prpCitemCarSchema.setRunAreaCode("");
		prpCitemCarSchema.setRunAreaName("");
		prpCitemCarSchema.setRunMiles("");
		prpCitemCarSchema.setEnrollDate(policy.getvhlInfoBean().getD_BOOK_DATE());
		
		logger.info(policy.getvhlInfoBean().getD_BOOK_DATE());
		logger.info(prpCitemCarSchema.getEnrollDate());
		

		
		logger.info(policy.getvhlInfoBean().getD_BOOK_DATE());
		logger.info(prpCitemCarSchema.getMakeDate());
		
		prpCitemCarSchema.setUseYears("");
		prpCitemCarSchema.setModelCode(policy.getvhlInfoBean().getC_BRD_CDE());
		prpCitemCarSchema.setBrandName(policy.getvhlInfoBean().getC_BRD_CNM());
		prpCitemCarSchema.setCountryNature("A");
		prpCitemCarSchema.setCountryCode("");


		prpCitemCarSchema.setBusinessClassCode("");
		prpCitemCarSchema.setSeatCount(policy.getvhlInfoBean().getN_SEAT());
		prpCitemCarSchema.setTonCount(policy.getvhlInfoBean().getN_TON());
		prpCitemCarSchema.setExhaustScale("");
		prpCitemCarSchema
				.setColorCode(policy.getvhlInfoBean().getC_LCN_COLOR());
		prpCitemCarSchema.setSafeDevice("");
		prpCitemCarSchema.setParkSite("");
		prpCitemCarSchema.setOwnerAddress(policy.getbaseInfoBase()
				.getInsrntAdd());
		prpCitemCarSchema.setOtherNature("100111");
		prpCitemCarSchema.setRateCode("");
		prpCitemCarSchema.setCarUsage("");
		prpCitemCarSchema.setCurrency(stChangeMoney(policy.getbaseInfoBase().getCurCde()));
		prpCitemCarSchema.setPurchasePrice(policy.getvhlInfoBean()
				.getN_NEW_PRICE());
		prpCitemCarSchema.setActualValue(policy.getvhlInfoBean()
				.getN_NEW_PRICE());
		prpCitemCarSchema.setInvoiceNo("");
		prpCitemCarSchema.setCarLoanFlag("");
		prpCitemCarSchema.setInsurerCode("");
		prpCitemCarSchema.setLastInsurer("");
		prpCitemCarSchema.setCarCheckStatus("2");
		prpCitemCarSchema.setCarChecker("");
		prpCitemCarSchema.setCarCheckTime(policy.getvhlInfoBean()
				.getD_CHKVHL_DATE());
		prpCitemCarSchema.setSpecialTreat("");
		prpCitemCarSchema.setRelievingAreaCode("");
		prpCitemCarSchema.setAddonCount("");
		prpCitemCarSchema.setCarDealerCode("");
		prpCitemCarSchema.setCarDealerName("");
		prpCitemCarSchema.setRemark("");
		prpCitemCarSchema.setFlag("");
		prpCitemCarSchema.setCarCheckReason("");
		return prpCitemCarSchema;
	}

	public static ArrayList createPrpTitemKindSchema(Policy policy) {
		ArrayList retList = new ArrayList();
		ArrayList rdrList = policy.getrdrList();
		Iterator it = rdrList.iterator();
		while (it.hasNext()) {
			PrpTitemKindSchema prpTitemKindSchema = new PrpTitemKindSchema();
			RdrInfoBean rib = (RdrInfoBean) it.next();
			prpTitemKindSchema.setProposalNo(policy.getbaseInfoBase()
					.getPlyAppNo());
			prpTitemKindSchema.setRiskCode("0507");
			prpTitemKindSchema.setItemKindNo("1");
			prpTitemKindSchema.setFamilyNo("0");
			prpTitemKindSchema.setFamilyName("");
			prpTitemKindSchema.setRationType("");
			prpTitemKindSchema.setKindCode("BZ");
			prpTitemKindSchema.setKindName("机动车交通事故责任强制XXXXX");
			prpTitemKindSchema.setItemNo("1");
			prpTitemKindSchema.setItemCode("0001");
			prpTitemKindSchema.setItemDetailName("车辆");
			prpTitemKindSchema.setModeCode("");
			prpTitemKindSchema.setModeName("");
			prpTitemKindSchema.setStartDate(policy.getbaseInfoBase()
					.getInsbgnDate());
			prpTitemKindSchema.setStartHour("24");
			prpTitemKindSchema.setEndDate(policy.getbaseInfoBase()
					.getInsrntDate());
			prpTitemKindSchema.setModel("");
			prpTitemKindSchema.setBuyDate("");
			prpTitemKindSchema.setAddressNo("0");
			prpTitemKindSchema.setCalculateFlag("Y");
			prpTitemKindSchema
					.setCurrency(stChangeMoney(policy.getbaseInfoBase().getCurCde()));
			prpTitemKindSchema.setUnitAmount("0");
			prpTitemKindSchema.setQuantity("0");
			prpTitemKindSchema.setUnit("");
			prpTitemKindSchema.setValue("0.00");
			prpTitemKindSchema.setAmount(rib.getN_AMT());
			prpTitemKindSchema.setRatePeriod("0");
			prpTitemKindSchema.setRate(rib.getN_RATE());
			prpTitemKindSchema.setShortRateFlag("3");
			prpTitemKindSchema.setShortRate(rib.getN_RATIO());
			prpTitemKindSchema.setBasePremium(rib.getN_DIS_PRM());
			prpTitemKindSchema.setBenchMarkPremium(rib.getN_DIS_PRM());
			prpTitemKindSchema.setDiscount("1");
			prpTitemKindSchema.setAdjustRate("1");
			prpTitemKindSchema.setPremium(rib.getN_PRM());
			prpTitemKindSchema.setDeductibleRate("0");
			prpTitemKindSchema.setDeductible("0");
			prpTitemKindSchema.setFlag(" 2 03");
			retList.add(prpTitemKindSchema);
		}
		return retList;
	}

	public static ArrayList createPrpCitemKindSchema(Policy policy) {
		ArrayList retList = new ArrayList();
		ArrayList rdrList = policy.getrdrList();
		Iterator it = rdrList.iterator();
		while (it.hasNext()) {
			PrpCitemKindSchema prpCitemKindSchema = new PrpCitemKindSchema();
			RdrInfoBean rib = (RdrInfoBean) it.next();
			prpCitemKindSchema.setProposalNo(policy.getbaseInfoBase()
					.getPlyAppNo());
			prpCitemKindSchema.setPolicyNo(policy.getbaseInfoBase().getPlyNo());
			prpCitemKindSchema.setRiskCode("0507");
			prpCitemKindSchema.setItemKindNo("1");
			prpCitemKindSchema.setFamilyNo("0");
			prpCitemKindSchema.setFamilyName("");
			prpCitemKindSchema.setRationType("");
			prpCitemKindSchema.setKindCode("BZ");
			prpCitemKindSchema.setKindName("机动车交通事故责任强制XXXXX");
			prpCitemKindSchema.setItemNo("1");
			prpCitemKindSchema.setItemCode("0001");
			prpCitemKindSchema.setItemDetailName("车辆");
			prpCitemKindSchema.setModeCode("");
			prpCitemKindSchema.setModeName("");
			prpCitemKindSchema.setStartDate(policy.getbaseInfoBase()
					.getInsbgnDate());
			prpCitemKindSchema.setStartHour("0");
			prpCitemKindSchema.setEndHour("24");
			prpCitemKindSchema.setEndDate(policy.getbaseInfoBase()
					.getInsrntDate());
			prpCitemKindSchema.setModel("");
			prpCitemKindSchema.setBuyDate("");
			prpCitemKindSchema.setAddressNo("0");
			prpCitemKindSchema.setCalculateFlag("Y");
			prpCitemKindSchema
					.setCurrency(stChangeMoney(policy.getbaseInfoBase().getCurCde()));
			prpCitemKindSchema.setUnitAmount("0.00");
			prpCitemKindSchema.setQuantity("0.00");
			prpCitemKindSchema.setUnit("");
			prpCitemKindSchema.setValue("0.00");
			prpCitemKindSchema.setAmount(rib.getN_AMT());
			prpCitemKindSchema.setRatePeriod("0");
			prpCitemKindSchema.setRate(rib.getN_RATE());
			prpCitemKindSchema.setShortRateFlag("3");
			prpCitemKindSchema.setShortRate(rib.getN_RATIO());
			prpCitemKindSchema.setBasePremium(rib.getN_DIS_PRM());
			prpCitemKindSchema.setBenchMarkPremium(rib.getN_DIS_PRM());
			prpCitemKindSchema.setDiscount("1");
			prpCitemKindSchema.setAdjustRate("1");
			prpCitemKindSchema.setPremium(rib.getN_PRM());
			prpCitemKindSchema.setDeductibleRate("0");
			prpCitemKindSchema.setDeductible("0");
			prpCitemKindSchema.setFlag(" 2 03");
			retList.add(prpCitemKindSchema);
		}
		return retList;
	}

	public static String getCustomerCode(Policy policy) throws UserException {
		String stCustomerCode = null;
		BLPrpDcustomer blPrpDcustomer = new BLPrpDcustomer();
		PrpDcustomerSchema prpDcustomerSchema = createDbcustomerSchema(policy);
		try {
			stCustomerCode = blPrpDcustomer.addCustomer(prpDcustomerSchema,
					policy.getbaseInfoBase().getDptCde());
		} catch (Exception e) {
			throw new UserException(-98, -1007,
					"com.ygbx.singleInterface.bl.BLCreatePolicy", "生成XXXXX代码失败！");
		}
		return stCustomerCode;
	}

	public static void stCreateAppCustomerIDv(Policy policy,
			String stCustomerCode) throws UserException {
		try {
			
			DBPrpDcustomerIdv dbPrpDcustomerIdv = new DBPrpDcustomerIdv();
			dbPrpDcustomerIdv.setCustomerCode(stCustomerCode);
			dbPrpDcustomerIdv.setNewCustomerCode(stCustomerCode);
			dbPrpDcustomerIdv.setCustomerCName(policy.getbaseInfoBase()
					.getAppCnm());
			dbPrpDcustomerIdv.setCustomerEName("");
			dbPrpDcustomerIdv.setAddressCName(policy.getbaseInfoBase()
					.getInsrntAdd());
			dbPrpDcustomerIdv.setAddressEName("");
			dbPrpDcustomerIdv.setValidStatus("1");
			dbPrpDcustomerIdv.setIdentifyType("01");
			dbPrpDcustomerIdv.setIdentifyNumber(policy.getbaseInfoBase()
					.getInsrntId());
			dbPrpDcustomerIdv.setSex("");
			dbPrpDcustomerIdv.setPhoneNumber(policy.getbaseInfoBase()
					.getInsrntTel());
			dbPrpDcustomerIdv
					.setMobile(policy.getbaseInfoBase().getInsrntTel());
			dbPrpDcustomerIdv.setLinkAddress(policy.getbaseInfoBase()
					.getInsrntAdd());
			dbPrpDcustomerIdv.setPostCode(policy.getbaseInfoBase()
					.getInsrntZip());
			dbPrpDcustomerIdv.setOperatorCode(policy.getbaseInfoBase()
					.getC_CRT_CDE());
			dbPrpDcustomerIdv.setInputDate(policy.getbaseInfoBase()
					.getAppDate());
			dbPrpDcustomerIdv.setUpdaterCode(policy.getbaseInfoBase()
					.getC_CRT_CDE());
			dbPrpDcustomerIdv.setUpdateDate(policy.getbaseInfoBase()
					.getAppDate());
			
			dbPrpDcustomerIdv.setComcode(policy.getbaseInfoBase().getDptCde());
			dbPrpDcustomerIdv.setLowerViewFlag("");
			dbPrpDcustomerIdv.insert();
		} catch (Exception e) {
			throw new UserException(-98, -1007,
					"com.ygbx.singleInterface.bl.BLCreatePolicy", "生成XX人信息失败！");
		}
	}

	public static void stCreateInsrntCustomerIDv(Policy policy,
			String stCustomerCode) throws UserException {
		
		try {
			DBPrpDcustomerIdv dbPrpDcustomerIdv = new DBPrpDcustomerIdv();
			dbPrpDcustomerIdv.setCustomerCode(stCustomerCode);
			dbPrpDcustomerIdv.setNewCustomerCode(stCustomerCode);
			dbPrpDcustomerIdv.setCustomerCName(policy.getbaseInfoBase()
					.getInsrntCnm());
			dbPrpDcustomerIdv.setCustomerEName("");
			dbPrpDcustomerIdv.setAddressCName(policy.getbaseInfoBase()
					.getInsrntAdd());
			dbPrpDcustomerIdv.setAddressEName("");
			dbPrpDcustomerIdv.setValidStatus("1");
			dbPrpDcustomerIdv.setIdentifyType("01");
			dbPrpDcustomerIdv.setIdentifyNumber(policy.getbaseInfoBase()
					.getInsrntId());
			dbPrpDcustomerIdv.setSex("");
			dbPrpDcustomerIdv.setPhoneNumber(policy.getbaseInfoBase()
					.getInsrntTel());
			dbPrpDcustomerIdv
					.setMobile(policy.getbaseInfoBase().getInsrntTel());
			dbPrpDcustomerIdv.setLinkAddress(policy.getbaseInfoBase()
					.getInsrntAdd());
			dbPrpDcustomerIdv.setPostCode(policy.getbaseInfoBase()
					.getInsrntZip());
			dbPrpDcustomerIdv.setOperatorCode(policy.getbaseInfoBase()
					.getC_CRT_CDE());
			dbPrpDcustomerIdv.setInputDate(policy.getbaseInfoBase()
					.getAppDate());
			dbPrpDcustomerIdv.setUpdaterCode(policy.getbaseInfoBase()
					.getC_CRT_CDE());
			dbPrpDcustomerIdv.setUpdateDate(policy.getbaseInfoBase()
					.getAppDate());
			
			dbPrpDcustomerIdv.setComcode(policy.getbaseInfoBase().getDptCde());
			dbPrpDcustomerIdv.setLowerViewFlag("");
			dbPrpDcustomerIdv.insert();
		} catch (Exception e) {
			throw new UserException(-98, -1007,
					"com.ygbx.singleInterface.bl.BLCreatePolicy", "生成被XX人信息失败！");
		}
	}

	public static PrpTaddressSchema createPrpTaddress(Policy policy) {
		PrpTaddressSchema prpTaddressSchema = new PrpTaddressSchema();
		prpTaddressSchema.setAddressName(policy.getbaseInfoBase()
				.getInsrntAdd());
		prpTaddressSchema.setProposalNo(policy.getbaseInfoBase().getPlyAppNo());
		prpTaddressSchema.setRiskCode("0507");
		prpTaddressSchema.setAddressCode(policy.getbaseInfoBase()
				.getInsrntZip());
		prpTaddressSchema.setAddressNo("1");
		prpTaddressSchema.setFlag("");
		prpTaddressSchema.setProjectName("");
		return prpTaddressSchema;
	}

	public static PrpCaddressSchema createPrpCaddress(Policy policy) {
		PrpCaddressSchema prpCaddressSchema = new PrpCaddressSchema();
		prpCaddressSchema.setAddressName(policy.getbaseInfoBase()
				.getInsrntAdd());
		prpCaddressSchema.setPolicyNo(policy.getbaseInfoBase().getPlyNo());
		prpCaddressSchema.setRiskCode("0507");
		prpCaddressSchema.setAddressCode(policy.getbaseInfoBase()
				.getInsrntZip());
		prpCaddressSchema.setAddressNo("1");
		prpCaddressSchema.setFlag("");
		prpCaddressSchema.setProjectName("");
		return prpCaddressSchema;
	}

	public static PrpTfeeSchema createPrpTfee(Policy policy) {
		PrpTfeeSchema prpTfeeSchema = new PrpTfeeSchema();
		prpTfeeSchema.setProposalNo(policy.getbaseInfoBase().getPlyAppNo());
		prpTfeeSchema.setRiskCode("0507");
		prpTfeeSchema.setFlag("");
		prpTfeeSchema.setExchangeRate1("1.0000");
		prpTfeeSchema.setExchangeRate2("1.0000");
		prpTfeeSchema.setAmount(Double.toString(policy.getbaseInfoBase()
				.getAmt()));
		prpTfeeSchema.setPremium(Double.toString(policy.getbaseInfoBase()
				.getPrm()));
		prpTfeeSchema.setCurrency(stChangeMoney(policy.getbaseInfoBase().getCurCde()));
		prpTfeeSchema.setAmount1(Double.toString(policy.getbaseInfoBase()
				.getAmt()));
		prpTfeeSchema.setPremium1(Double.toString(policy.getbaseInfoBase()
				.getPrm()));
		prpTfeeSchema.setCurrency1(stChangeMoney(policy.getbaseInfoBase().getCurCde()));
		prpTfeeSchema.setAmount2(Double.toString(policy.getbaseInfoBase()
				.getAmt()));
		prpTfeeSchema.setPremium2(Double.toString(policy.getbaseInfoBase()
				.getPrm()));
		prpTfeeSchema.setCurrency2(stChangeMoney(policy.getbaseInfoBase().getCurCde()));
		return prpTfeeSchema;
	}

	public static PrpCfeeSchema createPrpCfee(Policy policy) {
		PrpCfeeSchema prpCfeeSchema = new PrpCfeeSchema();
		prpCfeeSchema.setPolicyNo(policy.getbaseInfoBase().getPlyNo());
		prpCfeeSchema.setRiskCode("0507");
		prpCfeeSchema.setFlag("");
		prpCfeeSchema.setExchangeRate1("1.0000");
		prpCfeeSchema.setExchangeRate2("1.0000");
		prpCfeeSchema.setAmount(Double.toString(policy.getbaseInfoBase()
				.getAmt()));
		prpCfeeSchema.setPremium(Double.toString(policy.getbaseInfoBase()
				.getPrm()));
		prpCfeeSchema.setCurrency(stChangeMoney(policy.getbaseInfoBase().getCurCde()));
		prpCfeeSchema.setAmount1(Double.toString(policy.getbaseInfoBase()
				.getAmt()));
		prpCfeeSchema.setPremium1(Double.toString(policy.getbaseInfoBase()
				.getPrm()));
		prpCfeeSchema.setCurrency1(stChangeMoney(policy.getbaseInfoBase().getCurCde()));
		prpCfeeSchema.setAmount2(Double.toString(policy.getbaseInfoBase()
				.getAmt()));
		prpCfeeSchema.setPremium2(Double.toString(policy.getbaseInfoBase()
				.getPrm()));
		prpCfeeSchema.setCurrency2(stChangeMoney(policy.getbaseInfoBase().getCurCde()));
		return prpCfeeSchema;
	}

	public static PrpTinsuredSchema createPrpTinsured(Policy policy,PrpTmainSchema prpTmainSchema,String serialNo){
        
		BaseInfoBean baseInfoBean = policy.getbaseInfoBase();
		PrpTinsuredSchema prpTinsuredSchema = new PrpTinsuredSchema();
        prpTinsuredSchema.setProposalNo(prpTmainSchema.getProposalNo());
        prpTinsuredSchema.setRiskCode("0507");
        prpTinsuredSchema.setSerialNo(serialNo );
        prpTinsuredSchema.setLanguage("C");
        prpTinsuredSchema.setInsuredType("1");
        prpTinsuredSchema.setInsuredCode(prpTmainSchema.getInsuredCode());
        prpTinsuredSchema.setInsuredName(prpTmainSchema.getInsuredName());
        prpTinsuredSchema.setInsuredAddress(prpTmainSchema.getInsuredAddress());
        prpTinsuredSchema.setInsuredNature(baseInfoBean.getC_PROPERTY());
        prpTinsuredSchema.setInsuredFlag(serialNo);
        
        prpTinsuredSchema.setRelateSerialNo("0");
        prpTinsuredSchema.setInsuredIdentity("01");
        prpTinsuredSchema.setIdentifyType("01");
        prpTinsuredSchema.setIdentifyNumber(baseInfoBean.getInsrntId());
        prpTinsuredSchema.setOccupationCode("");
        prpTinsuredSchema.setBenefitFlag("N");
        prpTinsuredSchema.setBenefitRate("0.0");
        prpTinsuredSchema.setOccupationCode("");
        prpTinsuredSchema.setLinkerName(prpTmainSchema.getInsuredName());
        prpTinsuredSchema.setPostAddress(prpTmainSchema.getInsuredAddress());
        prpTinsuredSchema.setPostCode(baseInfoBean.getInsrntZip());
        prpTinsuredSchema.setPhoneNumber(baseInfoBean.getInsrntTel());
        return prpTinsuredSchema;
	}
	
	public static PrpCinsuredSchema createPrpCinsured(Policy policy,PrpCmainSchema prpCmainSchema,String serialNo){
        
		BaseInfoBean baseInfoBean = policy.getbaseInfoBase();
		PrpCinsuredSchema prpCinsuredSchema = new PrpCinsuredSchema();
		prpCinsuredSchema.setPolicyNo(prpCmainSchema.getPolicyNo());
		prpCinsuredSchema.setRiskCode("0507");
		prpCinsuredSchema.setSerialNo(serialNo );
		prpCinsuredSchema.setLanguage("C");
		prpCinsuredSchema.setInsuredType("1");
		prpCinsuredSchema.setInsuredCode(prpCmainSchema.getInsuredCode());
		prpCinsuredSchema.setInsuredName(prpCmainSchema.getInsuredName());
		prpCinsuredSchema.setInsuredAddress(prpCmainSchema.getInsuredAddress());
		prpCinsuredSchema.setInsuredNature(baseInfoBean.getC_PROPERTY());
        prpCinsuredSchema.setInsuredFlag(serialNo);
        
        prpCinsuredSchema.setRelateSerialNo("0");
        prpCinsuredSchema.setInsuredIdentity("01");
        prpCinsuredSchema.setIdentifyType("01");
        prpCinsuredSchema.setIdentifyNumber(baseInfoBean.getInsrntId());
        prpCinsuredSchema.setOccupationCode("");
        prpCinsuredSchema.setBenefitFlag("N");
        prpCinsuredSchema.setBenefitRate("0.0");
        prpCinsuredSchema.setOccupationCode("");
        prpCinsuredSchema.setLinkerName(prpCmainSchema.getInsuredName());
        prpCinsuredSchema.setPostAddress(prpCmainSchema.getInsuredAddress());
        prpCinsuredSchema.setPostCode(baseInfoBean.getInsrntZip());
        prpCinsuredSchema.setPhoneNumber(baseInfoBean.getInsrntTel());
        return prpCinsuredSchema;
	}

	public static PrpTinsuredNatureSchema createPrpTinsuredNatureSchema(Policy policy,PrpTmainSchema prpTmainSchema){
		PrpTinsuredNatureSchema prpTinsuredNatureSchema = new PrpTinsuredNatureSchema();
        prpTinsuredNatureSchema.setProposalNo(prpTmainSchema.getProposalNo());
        prpTinsuredNatureSchema.setSerialNo("1");
        prpTinsuredNatureSchema.setInsuredFlag("1");
        prpTinsuredNatureSchema.setSex(policy.getbaseInfoBase().getC_SEX());
        prpTinsuredNatureSchema.setAge(policy.getbaseInfoBase().getN_AGE());
        prpTinsuredNatureSchema.setUnitType("00");
        prpTinsuredNatureSchema.setMarriage(policy.getbaseInfoBase().getC_MARRIAGE().equals("0")?"1":"0");
        return prpTinsuredNatureSchema;
	}
	
	public static PrpCinsuredNatureSchema createPrpCinsuredNatureSchema(Policy policy,PrpCmainSchema prpCmainSchema){
		PrpCinsuredNatureSchema prpCinsuredNatureSchema = new PrpCinsuredNatureSchema();
		prpCinsuredNatureSchema.setPolicyNo(prpCmainSchema.getPolicyNo());
		prpCinsuredNatureSchema.setSerialNo("1");
		prpCinsuredNatureSchema.setInsuredFlag("1");
		prpCinsuredNatureSchema.setSex(policy.getbaseInfoBase().getC_SEX());
		prpCinsuredNatureSchema.setAge(policy.getbaseInfoBase().getN_AGE());
		prpCinsuredNatureSchema.setUnitType("00");
		prpCinsuredNatureSchema.setMarriage(policy.getbaseInfoBase().getC_MARRIAGE().equals("0")?"1":"0");
        return prpCinsuredNatureSchema;
	}
	public static PrpCinsuredArtifSchema createPrpCinsuredArtifSchema(Policy policy,PrpTmainSchema prpCmainSchema){
		PrpCinsuredArtifSchema prpCinsuredArtifSchema = new PrpCinsuredArtifSchema();
		prpCinsuredArtifSchema.setProposalNo(prpCmainSchema.getProposalNo());
		prpCinsuredArtifSchema.setPolicyNo(prpCmainSchema.getPolicyNo());
		prpCinsuredArtifSchema.setSerialNo("1");
		prpCinsuredArtifSchema.setInsuredFlag("1");
		prpCinsuredArtifSchema.setPostCode(policy.getbaseInfoBase().getInsrntZip());
		prpCinsuredArtifSchema.setPhoneNumber(policy.getbaseInfoBase().getInsrntTel());
		prpCinsuredArtifSchema.setRevenueRegistNo(policy.getbaseInfoBase().getInsrntId());
		return prpCinsuredArtifSchema;
	}
	
	public static PrpTinsuredArtifSchema createPrpTinsuredArtifSchema(Policy policy,PrpTmainSchema prpTmainSchema){
		PrpTinsuredArtifSchema prpTinsuredArtifSchema = new PrpTinsuredArtifSchema();
		prpTinsuredArtifSchema.setProposalNo(prpTmainSchema.getProposalNo());
		prpTinsuredArtifSchema.setSerialNo("1");
		prpTinsuredArtifSchema.setInsuredFlag("1");
		prpTinsuredArtifSchema.setPostCode(policy.getbaseInfoBase().getInsrntZip());
		prpTinsuredArtifSchema.setPhoneNumber(policy.getbaseInfoBase().getInsrntTel());
		prpTinsuredArtifSchema.setRevenueRegistNo(policy.getbaseInfoBase().getInsrntId());
		return prpTinsuredArtifSchema;
	}

	
	/**
	 * 单机版接口：构造Tlimit方法
	 * @param String ProposalNo
	 * @return BLPrpTlimit
	 */
	public static BLPrpTlimit createPrpTlimit(String iProposalNo)throws UserException,Exception {
		BLPrpTlimit blPrpTlimit = new BLPrpTlimit();
		PrpTlimitSchema prpTlimitSchema = new PrpTlimitSchema();
		BLPrpDlimitCar blPrpDlimitCar = new BLPrpDlimitCar();
		String strWhere = "";
		strWhere = "RiskCode='0507' AND ValidStatus='1'";
		try{
			blPrpDlimitCar.query(strWhere);
			for (int i=0;i<blPrpDlimitCar.getSize();i++){
				prpTlimitSchema.setProposalNo(iProposalNo);
				prpTlimitSchema.setRiskCode("0507");
				prpTlimitSchema.setLimitGrade("2");
				prpTlimitSchema.setLimitNo("1");
				prpTlimitSchema.setLimitType(blPrpDlimitCar.getArr(i).getLimitCode());
				prpTlimitSchema.setCurrency("CNY");
				prpTlimitSchema.setLimitFee(blPrpDlimitCar.getArr(i).getLimitFee());
				prpTlimitSchema.setLimitFlag("0");
				blPrpTlimit.setArr(prpTlimitSchema);
			}
		}catch (Exception e){
			e.printStackTrace();
		    throw e;		
		}
		return blPrpTlimit;
	}

	/**
	 * 单机版接口：构造Climit方法
	 * @param String PolicyNo
	 * @return BLPrpClimit
	 */
	public static BLPrpClimit createPrpClimit(String iPolicyNo)throws UserException,Exception {
		BLPrpClimit blPrpClimit = new BLPrpClimit();
		PrpClimitSchema prpClimitSchema = new PrpClimitSchema();
		BLPrpDlimitCar blPrpDlimitCar = new BLPrpDlimitCar();
		String strWhere = "";
		strWhere = "RiskCode='0507' AND ValidStatus='1'";
		try{
			blPrpDlimitCar.query(strWhere);
			for (int i=0;i<blPrpDlimitCar.getSize();i++){
				prpClimitSchema.setProposalNo(iPolicyNo);
				prpClimitSchema.setRiskCode("0507");
				prpClimitSchema.setLimitGrade("2");
				prpClimitSchema.setLimitNo("1");
				prpClimitSchema.setLimitType(blPrpDlimitCar.getArr(i).getLimitCode());
				prpClimitSchema.setCurrency("CNY");
				prpClimitSchema.setLimitFlag("0");
				blPrpClimit.setArr(prpClimitSchema);
			}
		}catch (Exception e){
			e.printStackTrace();
		    throw e;		
		}
		return blPrpClimit;
	}
	

	/**
	 * 单机版接口：构造prpTexpense方法
	 * @param String PolicyNo
	 * @return BLPrpClimit
	 */
	public static BLPrpTexpense createPrpTexpense(Policy policy) throws UserException,Exception {
		BLPrpTexpense blPrpTexpense = new BLPrpTexpense();
		PrpTexpenseSchema prpTexpenseSchema = new PrpTexpenseSchema();
	    PrpDriskConfigDto prpDriskConfigDto = new PrpDriskConfigDto();
	    String strConfigValue = "";
	    String strComCode = "";
	    strComCode = policy.getbaseInfoBase().getDptCde();
		try {
			prpDriskConfigDto = UIPrpDriskConfigAction.queryRiskConfig(
					              strComCode, "0507", "ALLOW_COMMISSION_ENDORSE");
			if (prpDriskConfigDto != null){
				strConfigValue = prpDriskConfigDto.getConfigValue();
			}
			prpTexpenseSchema.setProposalNo(policy.getbaseInfoBase().getPlyAppNo());
			prpTexpenseSchema.setRiskCode("0507");
			
			prpTexpenseSchema.setFlag("I1");
			blPrpTexpense.setArr(prpTexpenseSchema);
		}catch (Exception e){
			e.printStackTrace();
		    throw e;
		}
		return blPrpTexpense;
	}

	/**
	 * 单机版接口：构造prpCexpense方法
	 * @param XX对象 Policy
	 * @return BLPrpClimit
	 */
	public static BLPrpCexpense createPrpCexpense(Policy policy) throws UserException,Exception {
		BLPrpCexpense blPrpCexpense = new BLPrpCexpense();
		PrpCexpenseSchema prpCexpenseSchema = new PrpCexpenseSchema();
	    PrpDriskConfigDto prpDriskConfigDto = new PrpDriskConfigDto();
	    String strConfigValue = "";
	    String strComCode = "";
	    strComCode = policy.getbaseInfoBase().getDptCde();
		try {
			prpDriskConfigDto = UIPrpDriskConfigAction.queryRiskConfig(
					              strComCode, "0507", "ALLOW_COMMISSION_ENDORSE");
			if (prpDriskConfigDto != null){
				strConfigValue = prpDriskConfigDto.getConfigValue();
			}
			prpCexpenseSchema.setPolicyNo(policy.getbaseInfoBase().getPlyNo());
			prpCexpenseSchema.setRiskCode("0507");
			
			prpCexpenseSchema.setFlag("I1");
			blPrpCexpense.setArr(prpCexpenseSchema);
		}catch (Exception e){
			e.printStackTrace();
		    throw e;
		}
		return blPrpCexpense;
	}

	/**
	 * 单机版接口：构造prpTitemCarExt方法
	 * @param XX对象 Policy
	 * @return BLPrpClimit
	 */
	public static BLPrpTitemCarExt createPrpTitemCarExt(String iProposalNo) throws UserException,Exception {
		BLPrpTitemCarExt blPrpTitemCarExt = new BLPrpTitemCarExt();
		PrpTitemCarExtSchema prpTitemCarExtSchema = new PrpTitemCarExtSchema();
		prpTitemCarExtSchema.setProposalNo(iProposalNo);
		prpTitemCarExtSchema.setRiskCode("0507");
		prpTitemCarExtSchema.setItemNo("1");
		prpTitemCarExtSchema.setNoClaimFavorType("1");
		prpTitemCarExtSchema.setDamagedFactorGrade("4");
		blPrpTitemCarExt.setArr(prpTitemCarExtSchema);
		return blPrpTitemCarExt;
	}
	
	/**
	 * 单机版接口：构造prpCitemCarExt方法
	 * @param XX号 PolicyNo
	 * @return BLPrpClimit
	 */
	public static BLPrpCitemCarExt createPrpCitemCarExt(String iPolicyNo) throws UserException,Exception {
		BLPrpCitemCarExt blPrpCitemCarExt = new BLPrpCitemCarExt();
		PrpCitemCarExtSchema prpCitemCarExtSchema = new PrpCitemCarExtSchema();
		prpCitemCarExtSchema.setPolicyNo(iPolicyNo);
		prpCitemCarExtSchema.setRiskCode("0507");
		prpCitemCarExtSchema.setItemNo("1");
		prpCitemCarExtSchema.setNoClaimFavorType("1");
		prpCitemCarExtSchema.setDamagedFactorGrade("4");
		blPrpCitemCarExt.setArr(prpCitemCarExtSchema);
		return blPrpCitemCarExt;
	}

	/**
	 * 单机版接口：构造PrpJpayRefRecSchema方法
	 * @param XX对象 PolicyNo
	 * @return PrpJpayRefRecSchema
	 */
	public static PrpJpayRefRecSchema createPrpJpayRefRecSchema(Policy iPolicy) 
	    throws UserException,Exception {
	    PrpJpayRefRecSchema prpJpayRefRecSchema = new PrpJpayRefRecSchema();
	    prpJpayRefRecSchema.setOperateDate(iPolicy.getbaseInfoBase().getSignDate());
	    prpJpayRefRecSchema.setOperatorCode(iPolicy.getbaseInfoBase().getC_CRT_CDE());
	    prpJpayRefRecSchema.setOperateUnit(iPolicy.getbaseInfoBase().getDptCde());
	    prpJpayRefRecSchema.setCurrency2("CNY");
	    prpJpayRefRecSchema.setExchangeRate("1.0000");
	    Iterator it = iPolicy.getvchList().iterator();
	    try{
		    it.next();
	    }catch(NoSuchElementException noElemente){
	    	throw new UserException(-98, -1007, "BLCreatePolicy",
			"没有找到XX流水号！");
	    }catch(Exception e){
	    	throw new UserException(-98, -1007, "BLCreatePolicy",
			"没有销号！");
	    }
	    try{
	    	it.next();
	    }catch(NoSuchElementException noElemente){
	    	throw new UserException(-98, -1007, "BLCreatePolicy",
			"没有XX标识流水号！");
	    }catch(Exception e){
	    	throw new UserException(-98, -1007, "BLCreatePolicy",
			"没有销号！");
	    }
	    VchInfoBean vib = null;
	    try{
	    	vib = (VchInfoBean)it.next();
	    }catch(NoSuchElementException noElemente){
	    	throw new UserException(-98, -1007, "BLCreatePolicy",
			"没有发票流水号！");
	    }catch(Exception e){
	    	throw new UserException(-98, -1007, "BLCreatePolicy",
			"没有销号！");
	    }
	    prpJpayRefRecSchema.setVisaCode(vib.getC_VCH_TYP()); 
	    prpJpayRefRecSchema.setVisaName("XX业专用发票");  
	    prpJpayRefRecSchema.setVisaSerialNo(vib.getC_VCH_NO());  
	    prpJpayRefRecSchema.setPrintDate(vib.getD_USE_DATE());        
	    prpJpayRefRecSchema.setPrinterCode(iPolicy.getbaseInfoBase().getSlsCde());
	    prpJpayRefRecSchema.setVisaHandler(iPolicy.getbaseInfoBase().getSlsCde());
	    prpJpayRefRecSchema.setPayRefName(iPolicy.getbaseInfoBase().getAppCnm());
	    it = iPolicy.getpayList().iterator();
	    PayInfoBean pib = null;
	    try{
	    	pib = (PayInfoBean)it.next();
	    }catch(Exception e){
	    	throw new UserException(-98, -1007, "BLCreatePolicy",
			"收付款信息！");
	    }
	    
	    prpJpayRefRecSchema.setPlanDate(pib.getD_PAY_DATE());
	    prpJpayRefRecSchema.setRemark("");
	    return prpJpayRefRecSchema;
	}

	public static ArrayList createPrpTengage(Policy iPolicy)throws UserException,Exception{
		ArrayList prpTengageSchemaList = new ArrayList();
		String stClauseCode = iPolicy.getbaseInfoBase().getC_APPNT_CDE();
		String stClause = iPolicy.getbaseInfoBase().getAppnt();
		if (stClauseCode==null ) return null;
		String[] stArrayClauseCode = stClauseCode.split("\\|\\|\\|\\|");
		String[] stArrayClause = stClause.split("\\|\\|\\|\\|");
	
		for ( int i = 0 ; i<stArrayClauseCode.length ; i++){
			PrpTengageSchema prpTengageSchemaTitle = new PrpTengageSchema();
			PrpTengageSchema prpTengageSchema = new PrpTengageSchema();
			prpTengageSchemaTitle.setProposalNo(iPolicy.getbaseInfoBase().getPlyAppNo());	 
			prpTengageSchemaTitle.setRiskCode("0507");
			prpTengageSchemaTitle.setSerialNo(Integer.toString(i+1));
			prpTengageSchemaTitle.setLineNo("1");
			prpTengageSchemaTitle.setClauseCode(stArrayClauseCode[i]);
			if ( stArrayClauseCode[i].equals("T9999")){
				prpTengageSchemaTitle.setClauses("其他");
			}else{
				prpTengageSchemaTitle.setClauses("车主约定");
				
				logger.info("" +
						"t车主约定");
				
			}
			prpTengageSchemaTitle.setTitleFlag("0");
			prpTengageSchemaList.add(prpTengageSchemaTitle);
			prpTengageSchema.setProposalNo(iPolicy.getbaseInfoBase().getPlyAppNo());	 
			prpTengageSchema.setRiskCode("0507");
			prpTengageSchema.setSerialNo(Integer.toString(i+1));
			prpTengageSchema.setLineNo("1");
			prpTengageSchema.setClauseCode(stArrayClauseCode[i]);
			prpTengageSchema.setClauses(stArrayClause[i]);
			prpTengageSchema.setTitleFlag("1");
			prpTengageSchemaList.add(prpTengageSchema);
		}
		Iterator itEngage = prpTengageSchemaList.iterator();
		return prpTengageSchemaList;
	}
	public static ArrayList createPrpCengage(Policy iPolicy)throws UserException,Exception{
		ArrayList prpCengageSchemaList = new ArrayList();
		String stClauseCode = iPolicy.getbaseInfoBase().getC_APPNT_CDE();
		String stClause = iPolicy.getbaseInfoBase().getAppnt();
		if (stClauseCode==null ) return null;
		String[] stArrayClauseCode = stClauseCode.split("\\|\\|\\|\\|");
		String[] stArrayClause = stClause.split("\\|\\|\\|\\|");
		for ( int i = 0 ; i<stArrayClauseCode.length ; i++){
			PrpCengageSchema prpCengageSchemaTitle = new PrpCengageSchema();
			PrpCengageSchema prpCengageSchema = new PrpCengageSchema();
			prpCengageSchemaTitle.setPolicyNo(iPolicy.getbaseInfoBase().getPlyNo());	 
			prpCengageSchemaTitle.setRiskCode("0507");
			prpCengageSchemaTitle.setSerialNo(Integer.toString(i+1));
			prpCengageSchemaTitle.setLineNo("1");
			prpCengageSchemaTitle.setClauseCode(stArrayClauseCode[i]);
			if ( stArrayClauseCode[i].equals("T9999")){
				prpCengageSchemaTitle.setClauses("其他");
			}else{
				prpCengageSchemaTitle.setClauses("车主约定");
			}
			prpCengageSchemaTitle.setTitleFlag("0");
			prpCengageSchemaList.add(prpCengageSchemaTitle);
			prpCengageSchema.setPolicyNo(iPolicy.getbaseInfoBase().getPlyNo());	 
			prpCengageSchema.setRiskCode("0507");
			prpCengageSchema.setSerialNo(Integer.toString(i+1));
			prpCengageSchema.setLineNo("1");
			prpCengageSchema.setClauseCode(stArrayClauseCode[i]);
			prpCengageSchema.setClauses(stArrayClause[i]);
			prpCengageSchema.setTitleFlag("1");
			prpCengageSchemaList.add(prpCengageSchema);
		}
		return prpCengageSchemaList;
	}

	public static String stChangeMoney(String stInputMoneyType){
		String stRetMoneyType = null;
		switch(stInputMoneyType.charAt(0)){
			case '1' :	stRetMoneyType = "CNY";break;
			case '2' :	stRetMoneyType = "HKD";break;
			case '3' :	stRetMoneyType = "JPY";break;
			case '4' :	stRetMoneyType = "USD";break;
			case '6' :	stRetMoneyType = "NTD";break;
			case '7' :	stRetMoneyType = "GBP";break;
			case '8' :	stRetMoneyType = "AUD";break;
		}
		return stRetMoneyType;
	}
}
