package com.sp.interactive.interf;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.sp.sysframework.common.datatype.DateTime;
import com.sp.sysframework.reference.AppConfig;
import com.sp.sysframework.reference.DBManager;
import com.sp.utiall.blsvr.BLPrpDagent;
import com.sp.utiall.blsvr.BLPrpDcompany;
import com.sp.utility.string.ChgDate;
import com.sp.indiv.ci.schema.CIInsureDemandSchema;
import com.sp.platform.bl.action.domain.BLPrpDagreementAction;
import com.sp.platform.bl.action.domain.BLPrpDagreementActionBase;
import com.sp.platform.bl.facade.BLPrpDagreeDetailFacade;
import com.sp.prpall.blsvr.tb.BLProposal;
import com.sp.prpall.pubfun.PubTools;
import com.sp.prpall.schema.PrpIntefInfoSchema;
import com.sp.prpall.schema.PrpTNewEngageSchema;
import com.sp.prpall.schema.PrpTTrafficDetailSchema;
import com.sp.prpall.schema.PrpTcarDeviceSchema;
import com.sp.prpall.schema.PrpTcarDriverSchema;
import com.sp.prpall.schema.PrpTcarshipTaxSchema;
import com.sp.prpall.schema.PrpTcarshipTaxTJSchema;
import com.sp.prpall.schema.PrpTexpenseSchema;
import com.sp.prpall.schema.PrpTinsuredNatureSchema;
import com.sp.prpall.schema.PrpTinsuredSchema;
import com.sp.prpall.schema.PrpTitemCarExtSchema;
import com.sp.prpall.schema.PrpTitemCarSchema;
import com.sp.prpall.schema.PrpTitemKindSchema;
import com.sp.prpall.schema.PrpTmainSchema;
import com.sp.prpall.schema.PrpTmainSubSchema;
import com.sp.prpall.schema.PrpTprofitDetailSchema;
import com.sp.prpall.schema.PrpTrenewalSchema;
import com.sp.quotation.pub.QuotationUtils;
import com.sp.quotation.test.TestClass;
import com.sp.utility.UtiPower;
import com.sp.utility.database.DbPool;


/**
 * 询报价报文解析
 *
 */

public class QuotationObjectiveInfoDecoder {
	
	/**
	 * 将询价报文转换为XX单对象，支持同时转换交强XXXXXXX单对象和商业XXXXXXX单对象
	 * @param doc 询价报文
	 * @param paramMap 将一些特殊参数放到此map中，如XXXXX种、询价类型等
	 * @param BLProposalMap 将需要转换的XX单对象放到此map中，如交强XXXXX为map.get(QuotationUtils.BLPROPOSALCI);
	 * @throws Exception
	 */
	public static String request_ip;
	public void decode(Document doc, Map paramMap, Map BLProposalMap)throws Exception{

		Element root = doc.getRootElement();
		parseHead(root.element("HEAD"), paramMap, BLProposalMap);
		parseBody(root.element("BODY"), paramMap, BLProposalMap);
	}
	
	/**
	 * 处理HEAD节
	 * @param head
	 * @param paramMap
	 * @param BLProposalMap
	 * @throws Exception
	 */
	public void parseHead(Element head, Map paramMap, Map BLProposalMap)throws Exception{

		Element REQUESTTYPE = head.element("REQUESTTYPE");
		paramMap.put("requestType", REQUESTTYPE.getText());
		
		Element comCode = head.element("COMCODE");
		paramMap.put("comcode", comCode.getText());


	}
	
	/**
	 * 处理BODY节
	 * @param body
	 * @param paramMap
	 * @param BLProposalMap
	 * @throws Exception
	 */
	public void parseBody(Element body, Map paramMap, Map BLProposalMap)throws Exception{
		
		BLProposal blProposalCI = (BLProposal)BLProposalMap.get(QuotationUtils.BLPROPOSALCI);
		BLProposal blProposalBI = (BLProposal)BLProposalMap.get(QuotationUtils.BLPROPOSALBI);
		
		
		String infoTransno = body.elementText("INFOTRANSNO");
		String modelInfoID = body.elementText("MODELINFOID");
		
		String makeCom = body.elementText("MAKECOM");
		
		String comCode = (String)paramMap.get("comcode");
		String handlerCode = body.elementText("HANDLERCODE");
		String handler1Code = body.elementText("HANDLER1CODE");
		
		String opreratorCode = body.elementText("OPERATORCODE");
		String businessNature = body.elementText("BUSSINESSNATURE");
		String agentCode = body.elementText("AGENTCODE");
		String strAgreementNo = body.elementText("AGREEMENTNO");
		String agentName = body.elementText("AGENTNAME");
		
		
		String lasyPolicyNoCI = body.elementText("RENEWALCIPOLICYNO");
		String lasyPolicyNoBI = body.elementText("RENEWALBIPOLICYNO");

		String contractNo = body.elementText("CONTRACTNO");
		
		String OPERATEDATECI = body.elementText("OPERATEDATECI");
		OPERATEDATECI = new ChgDate().getCurrentTime("yyyy-MM-dd");
		String STARTDATECI = body.elementText("CISTARTDATE");
		String STARTHOURCI = body.elementText("CISTARTHOUR");
		String ENDDATECI = body.elementText("CIENDDATE");
		
		String ENDHOURCI = body.elementText("CIENDHOUR");
		if(STARTHOURCI==null||"".equals(STARTHOURCI)){
			STARTHOURCI="00";
		}
		if(ENDHOURCI==null||"".equals(ENDHOURCI)){
			ENDHOURCI="24";
		}
		
		String OPERATEDATEBI = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		String STARTDATEBI = body.elementText("BISTARTDATE");
		String STARTHOURBI = body.elementText("BISTARTHOUR");
		String ENDDATEBI = body.elementText("BIENDDATE");
		String ENDHOURBI = body.elementText("BIENDHOUR");
		
		if(STARTHOURBI==null||"".equals(STARTHOURBI)){
			STARTHOURBI="00";
		}
		if(ENDHOURBI==null||"".equals(ENDHOURBI)){
			ENDHOURBI="24";
		}
		paramMap.put("startDateCI", STARTDATECI);
		paramMap.put("startDateBI", STARTDATEBI);
		String strInputDate = body.elementText("INPUTDATE");
		
		
		
		
		String cipolicyNo = body.elementText("CIPOLICYNO");
		String cicompanyCode = body.elementText("CICOMPANYCODE");
		
		
		String adjustRateOfmainCI=body.elementText("ADJUSTRATEOFMAINCI");
		String adjustPremiumOfmainCI=body.elementText("ADJUSTPREMIUMOFMAINCI");
		String adjustRateOfmainBI=body.elementText("ADJUSTRATEOFMAINBI");
		String adjustPremiumOfmainBI=body.elementText("ADJUSTPREMIUMOFMAINBI");
		
		String bizFlag = body.elementText("BIZFLAG");
		String computerIp = body.elementText("COMPUTERIP");
		String checkCode = body.elementText("CHECKCODE");
		
		
		String ImmeValidFlagCI = body.elementText("IMMEVALIDFLAGCI");
		String ImmeValidFlagBI = body.elementText("IMMEVALIDFLAGBI");
		String ImmeValidStartDateCI = body.elementText("IMMEVALIDSTARTDATECI");
		String ImmeValidStartDateBI = body.elementText("IMMEVALIDSTARTDATEBI");
		String ImmeValidEndDateCI = body.elementText("IMMEVALIDENDDATECI");
		String ImmeValidEndDateBI = body.elementText("IMMEVALIDENDDATEBI");
		String OperateSite = body.elementText("OPERATESITE");
		String strServiceArea = body.elementText("SERVICEAREA");
		String strProspectNo = body.elementText("PROSPECTNO");
		String strBusinessRemark2 = body.elementText("BUSINESSREMARK2");
		String strRiskCodeCI = body.elementText("CIRISKCODE");
		String strRiskCodeBI = body.elementText("BIRISKCODE");
		String strChannelType = body.elementText("CHANNELTYPE");
		String strBusinessMarks = body.elementText("BUSINESSMARKS");
		String strExceptiontags = body.elementText("EXCEPTIONTAGS");
		String strCustomerLevel = body.elementText("CUSTOMERLEVEL");
		String strCrenewPolicyNoCI = body.elementText("CRENEWPOLICYNOCI");
		String strCrenewPolicyNoBI = body.elementText("CRENEWPOLICYNOBI");
		String strRelationPolicyNoCI = body.elementText("RELATIONPOLICYNOCI");
		String strRelationPolicyNoBI = body.elementText("RELATIONPOLICYNOBI");
		String strTaxComCode = body.elementText("TAXCOMCODE").trim();
    	String strPaidFreeCertificate = body.elementText("PAIDFREECERTIFICATE").trim();
    	String strNoClaimAdjustLevel = body.elementText("NOCLAIMADJUSTLEVEL").trim();
    	
    	String strBusinessModel = body.elementText("BUSINESSMODEL");
    	
    	
    	String strLastClaimCountCI =  body.elementText("LASTCLAIMCOUNTCI");
    	String strLastClaimCountBI =  body.elementText("LASTCLAIMCOUNTBI");
    	
    	
    	String strPolicySort=body.elementText("POLICYSORT");
    	String strRelivingareaCode=body.elementText("RELIEVINGAREACODE");
    	paramMap.put("RELIEVINGAREACODE", strRelivingareaCode);
    	
		
		parseCar(body.element("CARMODEL_LIST"), paramMap, BLProposalMap);
		parseCarOwner(body.element("INSURED_LIST"), paramMap, BLProposalMap);
		parseAppli(body.element("INSURED_LIST"), paramMap, BLProposalMap);
		parseInsured(body.element("INSURED_LIST"), paramMap, BLProposalMap);
		
		
		if(body.element("PRPTNEWENGAGE_LIST")!=null){
		parsePrptnewengage(body.element("PRPTNEWENGAGE_LIST"), paramMap, BLProposalMap);
		}
		
		
		
		
		
		
		if(blProposalCI != null){
			
			PrpTexpenseSchema prpTexpenseSchema = new PrpTexpenseSchema();
			
			prpTexpenseSchema.setRiskCode(strRiskCodeCI);
			
			blProposalCI.getBLPrpTexpense().setArr(prpTexpenseSchema);
			PrpTrenewalSchema prpTrenewalSchema = new PrpTrenewalSchema();
			PrpTmainSubSchema prpTmainSubSchema = new PrpTmainSubSchema();
			blProposalCI.getBLPrpTmainSub().setArr(prpTmainSubSchema);
			PrpTcarshipTaxSchema prpTcarshipTaxSchema = new PrpTcarshipTaxSchema();
			blProposalCI.getBLPrpTcarshipTax().setArr(prpTcarshipTaxSchema);
			
			PrpIntefInfoSchema prpIntefInfoSchema = new PrpIntefInfoSchema();
			blProposalCI.getBLPrpIntefInfo().setArr(prpIntefInfoSchema);
			
			
			
			blProposalCI.getBLPrpTmain().getArr(0).setClassCode("05");
			blProposalCI.getBLPrpTmain().getArr(0).setMakeCom(makeCom);
			blProposalCI.getBLPrpTmain().getArr(0).setComCode(comCode);
			
			BLPrpDcompany blPrpDcompany = new BLPrpDcompany();
			blPrpDcompany.query("comCode='"+comCode+"'");
			String channelType = blPrpDcompany.getArr(0).getChannelType();
			
			
			blProposalCI.getBLPrpTmain().getArr(0).setChannelType(channelType);
			blProposalCI.getBLPrpTmain().getArr(0).setHandlerCode(handlerCode);
			
			blProposalCI.getBLPrpTmain().getArr(0).setHandler1Code(handler1Code);
			
			blProposalCI.getBLPrpTmain().getArr(0).setBusinessNature(businessNature);
			blProposalCI.getBLPrpTmain().getArr(0).setAgentCode(agentCode);
		    
			blProposalCI.getBLPrpTmain().getArr(0).setAgreementNo(strAgreementNo);

			
			blProposalCI.getBLPrpTmain().getArr(0).setSendLastPolicyNo(lasyPolicyNoCI);
			blProposalCI.getBLPrpTmain().getArr(0).setContractNo(contractNo);
			
			blProposalCI.getBLPrpTmain().getArr(0).setOperateDate(OPERATEDATECI);
			
			blProposalCI.getBLPrpTitemCar().getArr(0).setMakeDate(OPERATEDATECI);			
			blProposalCI.getBLPrpTmain().getArr(0).setStartDate(STARTDATECI);
			blProposalCI.getBLPrpTmain().getArr(0).setStartHour(STARTHOURCI);
			blProposalCI.getBLPrpTmain().getArr(0).setEndDate(ENDDATECI);
			blProposalCI.getBLPrpTmain().getArr(0).setEndHour(ENDHOURCI);
			blProposalCI.getBLPrpTmain().getArr(0).setInputDate(strInputDate);
			blProposalCI.getBLPrpTmain().getArr(0).setOperatorCode(opreratorCode);
			
			
			blProposalCI.getBLPrpTmain().getArr(0).setIP(computerIp);
			
			
			blProposalCI.getBLPrpTmain().getArr(0).setAdjustRate(adjustRateOfmainCI);
			blProposalCI.getBLPrpTmain().getArr(0).setAdjustPremium(adjustPremiumOfmainCI);
			
			
			blProposalCI.getBLPrpTmain().getArr(0).setImmeValidFlag(ImmeValidFlagCI);
			blProposalCI.getBLPrpTmain().getArr(0).setImmeValidStartDate(ImmeValidStartDateCI);
			blProposalCI.getBLPrpTmain().getArr(0).setImmeValidEndDate(ImmeValidEndDateCI);
			blProposalCI.getBLPrpTmain().getArr(0).setOperateSite(OperateSite);
			blProposalCI.getBLPrpTmain().getArr(0).setServiceArea(strServiceArea);
			blProposalCI.getBLPrpTexpense().getArr(0).setProspectNo(strProspectNo);
			blProposalCI.getBLPrpTmain().getArr(0).setBusinessRemark2(strBusinessRemark2);
			blProposalCI.getBLPrpTmain().getArr(0).setRiskCode(strRiskCodeCI);
			blProposalCI.getBLPrpTmain().getArr(0).setChannelType(strChannelType);
			blProposalCI.getBLPrpTinsured().getArr(0).setCusTomerLevel(strCustomerLevel);
			
			if(strCrenewPolicyNoCI!=null  && !"".equals(strCrenewPolicyNoCI)){
				blProposalCI.getBLPrpTrenewal().setArr(prpTrenewalSchema);
				blProposalCI.getBLPrpTrenewal().getArr(0).setOldPolicyNo(strCrenewPolicyNoCI);				
			}
			
			blProposalCI.getBLPrpTmainSub().getArr(0).setMainPolicyNo(strRelationPolicyNoCI);
			
			blProposalCI.getBLPrpTmain().getArr(0).setBusinessModel(strBusinessModel);
			
			
			blProposalCI.getBLPrpTmain().getArr(0).setInfoTransNo(infoTransno);
			blProposalCI.getBLPrpTmain().getArr(0).setModelInfoID(modelInfoID);
			
			
			blProposalCI.getBLPrpTcarshipTax().getArr(0).setTaxComCode(strTaxComCode);
			blProposalCI.getBLPrpTcarshipTax().getArr(0).setPaidFreeCertificate(strPaidFreeCertificate);
			blProposalCI.getBLPrpTitemCarExt().getArr(0).setDamagedFactorGrade(strNoClaimAdjustLevel);
			
			
			blProposalCI.getBLPrpIntefInfo().getArr(0).setLastClaimCount(strLastClaimCountCI);
			
			
			blProposalCI.getBLPrpTmain().getArr(0).setPolicySort(strPolicySort);
			
		}
		
		if(blProposalBI != null){
			
			PrpTexpenseSchema prpTexpenseSchema = new PrpTexpenseSchema();
			
			prpTexpenseSchema.setRiskCode(strRiskCodeBI);
			

			blProposalBI.getBLPrpTexpense().setArr(prpTexpenseSchema);
			PrpTrenewalSchema prpTrenewalSchema = new PrpTrenewalSchema();
			
			PrpTmainSubSchema prpTmainSubSchema = new PrpTmainSubSchema();
			blProposalBI.getBLPrpTmainSub().setArr(prpTmainSubSchema);
			PrpTcarshipTaxSchema prpTcarshipTaxSchema = new PrpTcarshipTaxSchema();
			blProposalBI.getBLPrpTcarshipTax().setArr(prpTcarshipTaxSchema);
			
			PrpIntefInfoSchema prpIntefInfoSchema = new PrpIntefInfoSchema();
			blProposalBI.getBLPrpIntefInfo().setArr(prpIntefInfoSchema);
			
			
			
			blProposalBI.getBLPrpTmain().getArr(0).setClassCode("05");
			
			blProposalBI.getBLPrpTmain().getArr(0).setMakeCom(makeCom);
			blProposalBI.getBLPrpTmain().getArr(0).setComCode(comCode);
			
			BLPrpDcompany blPrpDcompany = new BLPrpDcompany();
			blPrpDcompany.query("comCode='"+comCode+"'");
			String channelType = blPrpDcompany.getArr(0).getChannelType();
			
			blProposalBI.getBLPrpTmain().getArr(0).setChannelType(channelType);
			blProposalBI.getBLPrpTmain().getArr(0).setHandlerCode(handlerCode);
			blProposalBI.getBLPrpTmain().getArr(0).setHandler1Code(handler1Code);
			blProposalBI.getBLPrpTmain().getArr(0).setBusinessNature(businessNature);
			blProposalBI.getBLPrpTmain().getArr(0).setAgentCode(agentCode);
			blProposalBI.getBLPrpTmain().getArr(0).setAgreementNo(strAgreementNo);
			blProposalBI.getBLPrpTmain().getArr(0).setSendLastPolicyNo(lasyPolicyNoBI);
			blProposalBI.getBLPrpTmain().getArr(0).setContractNo(contractNo);
			
			blProposalBI.getBLPrpTmain().getArr(0).setOperateDate(OPERATEDATEBI);
			
			blProposalBI.getBLPrpTmain().getArr(0).setStartDate(STARTDATEBI);
			blProposalBI.getBLPrpTmain().getArr(0).setStartHour(STARTHOURBI);
			blProposalBI.getBLPrpTmain().getArr(0).setEndDate(ENDDATEBI);
			blProposalBI.getBLPrpTmain().getArr(0).setEndHour(ENDHOURBI);
			blProposalBI.getBLPrpTmain().getArr(0).setInputDate(strInputDate);
			
			blProposalBI.getBLPrpTmain().getArr(0).setOperatorCode(opreratorCode);
			
			
			blProposalBI.getBLPrpTmain().getArr(0).setIP(computerIp);
			
			













			
			paramMap.put("cipolicyNo", cipolicyNo);
			paramMap.put("cicompanyCode", cicompanyCode);
			
			
			blProposalBI.getBLPrpTmain().getArr(0).setAdjustRate(adjustRateOfmainBI);
			blProposalBI.getBLPrpTmain().getArr(0).setAdjustPremium(adjustPremiumOfmainBI);
			
			
			
			blProposalBI.getBLPrpTmain().getArr(0).setImmeValidFlag(ImmeValidFlagBI);
			blProposalBI.getBLPrpTmain().getArr(0).setImmeValidStartDate(ImmeValidStartDateBI);
			blProposalBI.getBLPrpTmain().getArr(0).setImmeValidEndDate(ImmeValidEndDateBI);
			blProposalBI.getBLPrpTmain().getArr(0).setOperateSite(OperateSite);
			
			blProposalBI.getBLPrpTmain().getArr(0).setServiceArea(strServiceArea);
			blProposalBI.getBLPrpTexpense().getArr(0).setProspectNo(strProspectNo);
			blProposalBI.getBLPrpTmain().getArr(0).setBusinessRemark2(strBusinessRemark2);
			blProposalBI.getBLPrpTmain().getArr(0).setRiskCode(strRiskCodeBI);
			blProposalBI.getBLPrpTmain().getArr(0).setChannelType(strChannelType);
			blProposalBI.getBLPrpTinsured().getArr(0).setCusTomerLevel(strCustomerLevel);
			
			if(strCrenewPolicyNoBI!=null  && !"".equals(strCrenewPolicyNoBI)){
				blProposalBI.getBLPrpTrenewal().setArr(prpTrenewalSchema);
				blProposalBI.getBLPrpTrenewal().getArr(0).setOldPolicyNo(strCrenewPolicyNoBI);				
			}
			
			blProposalBI.getBLPrpTmainSub().getArr(0).setMainPolicyNo(strRelationPolicyNoBI);
			
			blProposalBI.getBLPrpTmain().getArr(0).setBusinessModel(strBusinessModel);
			
			
			blProposalBI.getBLPrpTmain().getArr(0).setInfoTransNo(infoTransno);
			blProposalBI.getBLPrpTmain().getArr(0).setModelInfoID(modelInfoID);
			
			
			blProposalBI.getBLPrpTcarshipTax().getArr(0).setTaxComCode(strTaxComCode);
			blProposalBI.getBLPrpTcarshipTax().getArr(0).setPaidFreeCertificate(strPaidFreeCertificate);
			blProposalBI.getBLPrpTitemCarExt().getArr(0).setDamagedFactorGrade(strNoClaimAdjustLevel);
			
			
			blProposalBI.getBLPrpIntefInfo().getArr(0).setLastClaimCount(strLastClaimCountBI);
			
			
			blProposalBI.getBLPrpTmain().getArr(0).setPolicySort(strPolicySort);
			
		}
	}
	
	/**
	 * 处理CAR_LIST节
	 * @param carList
	 * @param paramMap
	 * @param BLProposalMap
	 * @throws Exception
	 */
	public void parseCar(Element carList, Map paramMap, Map BLProposalMap)throws Exception{
		
		BLProposal blProposalCI = (BLProposal)BLProposalMap.get(QuotationUtils.BLPROPOSALCI);
		BLProposal blProposalBI = (BLProposal)BLProposalMap.get(QuotationUtils.BLPROPOSALBI);
		
		Iterator iterator = carList.elementIterator();
		while(iterator.hasNext()){
			
			PrpTitemCarSchema prpTitemCarSchema = new PrpTitemCarSchema();
			PrpTcarshipTaxSchema prpTcarshipTaxSchema = new PrpTcarshipTaxSchema();
			PrpTitemCarExtSchema prpTitemCarExtSchema = new PrpTitemCarExtSchema();

			Element carData = (Element)iterator.next(); 
			
			if("".equals(carData.elementText("LICENSENO"))){
				throw new Exception("车牌号不能为空，如果是新车请将车牌号写为“新车”！");
			}
			String strRelivingareaCode=(String) paramMap.get("RELIEVINGAREACODE");
			prpTitemCarSchema.setRelievingAreaCode(strRelivingareaCode!=null?strRelivingareaCode:"");
			prpTitemCarSchema.setLicenseNo(carData.elementText("LICENSENO"));
			prpTitemCarSchema.setHKLicenseNo(carData.elementText("HKLICENSENO"));
			
			if (carData.elementText("HKLICENSENO")==null||carData.elementText("HKLICENSENO").trim().equals("")) {
				prpTitemCarSchema.setRateCode("20");
			}else{
				prpTitemCarSchema.setRateCode("25");
			}
			prpTitemCarSchema.setLicenseColorCode(carData.elementText("LICENSECOLOR"));
			prpTitemCarSchema.setLicenseKindCode(carData.elementText("LICENSETYPE"));
			prpTitemCarSchema.setMainCarKindCode(carData.elementText("MAINCARKINDCODE"));
			prpTitemCarSchema.setCarKindCode(carData.elementText("CARKINDCODE"));
			prpTitemCarSchema.setUseNatureCode(carData.elementText("USETYPE"));
			prpTitemCarSchema.setColorCode(carData.elementText("COLORCODE"));
			prpTitemCarSchema.setCountryNature(carData.elementText("IMPORTFLAG"));
			prpTitemCarSchema.setCarOwner(carData.elementText("CAROWNER"));
			prpTitemCarSchema.setBrandName(carData.elementText("BRANDNAME"));
			prpTitemCarSchema.setCarUsage(carData.elementText("PRINTBRANDNAME"));
			prpTitemCarSchema.setModelCode(carData.elementText("RBCODE"));
			prpTitemCarSchema.setSeatCount(carData.elementText("SEATCOUNT"));
			prpTitemCarSchema.setSeatCountLB(carData.elementText("OLDSEATCOUNT"));
			
			prpTitemCarSchema.setPurchasePrice(carData.elementText("PURCHASEPRICE"));
			prpTitemCarSchema.setPurchasePriceLB(carData.elementText("PURCHASEPRICELB"));
			prpTitemCarSchema.setPurchasePriceOrigin(carData.elementText("OLDPURCHASEPRICE"));
			
			double EXHAUSTSCALE = Double.parseDouble(carData.elementText("EXHAUSTCAPACITY"))/1000;
			prpTitemCarSchema.setExhaustScale(EXHAUSTSCALE+"");
			double EXHAUSTSCALELB = Double.parseDouble(carData.elementText("OLDEXHAUSTCAPACITY"))/1000;
			prpTitemCarSchema.setExhaustScaleLB(EXHAUSTSCALELB+"");
			prpTitemCarSchema.setTonCount(carData.elementText("VEHICLETONNAGE"));
			prpTitemCarSchema.setTonCountLB(carData.elementText("OLDVEHICLETONNAGE"));
			prpTitemCarSchema.setFrameNo(carData.elementText("FRAMENO"));
			prpTitemCarSchema.setEngineNo(carData.elementText("ENGINNO"));
			prpTitemCarSchema.setEnrollDate(carData.elementText("ENROLLDATE").trim());
			if (carData.elementText("VEHICLEUSEDYEARS")!=null&&!"".equals(carData.elementText("VEHICLEUSEDYEARS"))) {
				prpTitemCarSchema.setUseYears(carData.elementText("VEHICLEUSEDYEARS"));
			}
			
			
			prpTitemCarSchema.setCompleteKerbMass(carData.elementText("VEHICLEWEIGHT"));
			prpTitemCarSchema.setRunAreaCode(carData.elementText("RUNAREACODE"));
			prpTitemCarSchema.setOtherNature(carData.elementText("SPECIALCARFLAG"));
			paramMap.put("ESPECIALLYCARTYPE",carData.elementText("SPECIALCARFLAG"));
			paramMap.put("RENEWALCIFLAG", carData.elementText("RENEWALCIFLAG"));
			paramMap.put("RENEWALBIFLAG", carData.elementText("RENEWALFLAG"));








			String strSafeDevice = ""+carData.elementText("ABSFLAG")+carData.elementText("AIRBAGNUM")
					+carData.elementText("ALARMFLAG");
			prpTitemCarSchema.setSafeDevice(strSafeDevice);
			prpTitemCarExtSchema.setDamagedFactorGrade(carData.elementText("DAMAGEDFACTORGRADE"));
			if(prpTitemCarExtSchema.getDamagedFactorGrade()==null||"".equals(prpTitemCarExtSchema.getDamagedFactorGrade())){
				prpTitemCarExtSchema.setDamagedFactorGrade("4");
			}
			prpTitemCarSchema.setAddonCount(carData.elementText("PECCANCYADJUST"));
			prpTitemCarSchema.setCarLoanFlag(carData.elementText("EVERPOLICY"));
			if(prpTitemCarSchema.getCarLoanFlag()==null||"".equals(prpTitemCarSchema.getCarLoanFlag())){
				prpTitemCarSchema.setCarLoanFlag("0");
			}
			
			prpTitemCarSchema.setMadeFactory(carData.elementText("MADEFACTORY"));
			prpTitemCarSchema.setMakeDate(carData.elementText("MAKEDATE").trim());
			prpTitemCarSchema.setLicenseCategory(carData.elementText("VEHICLETYPE"));
			prpTitemCarSchema.setOutLandCarFlag(carData.elementText("ECDEMICVEHICLEFLAG"));
			prpTitemCarSchema.setNewCarFlag(carData.elementText("NEWVEHICLEFLAG"));
			prpTitemCarSchema.setNoLicenseFlag(carData.elementText("NOLICENSEFLAG"));
			if(prpTitemCarSchema.getNoLicenseFlag()==null){
				prpTitemCarSchema.setNoLicenseFlag("");
			}
			prpTitemCarExtSchema.setInDoorFlag(carData.elementText("ISINDOOR"));
			prpTitemCarSchema.setChgOwnerFlag(carData.elementText("CHGOWNERFLAG"));
			prpTitemCarSchema.setChgOwnerDate(carData.elementText("FIRSTREGISTERDATE").trim());
			if("1".equals(prpTitemCarSchema.getChgOwnerFlag())&&"".equals(prpTitemCarSchema.getChgOwnerDate())){
				throw new Exception("过户车时，转移登记日期不能为空");
			}
			prpTitemCarSchema.setLoanVehicleFlag(carData.elementText("LOANVEHICLEFLAG"));
			prpTitemCarSchema.setEnrollDate(carData.elementText("ENROLLDATE"));
			
			prpTitemCarExtSchema.setNoDamageYears(carData.elementText("BINODAMAGEYEARS"));
			
			
			
			
			prpTitemCarExtSchema.setFuelType(carData.elementText("FUELTYPE"));
			
			
			
			prpTitemCarExtSchema.setBuyCarDate(carData.elementText("ENROLLDATE").trim());  
			
			
			prpTitemCarExtSchema.setNoneFluctuateFlag(carData.elementText("NEWDEVICEFLAG"));
			if(prpTitemCarExtSchema.getNoneFluctuateFlag()==null||"".equals(prpTitemCarExtSchema.getNoneFluctuateFlag())){
				prpTitemCarExtSchema.setNoneFluctuateFlag("0");
			}
			
			
			
			
			
			
			prpTitemCarExtSchema.setVerificationCode(carData.elementText("VEHICLETYPE"));  
			
			
			prpTitemCarSchema.setVINNo(carData.elementText("VINNO"));
			prpTitemCarSchema.setRunMiles(carData.elementText("RUNMILES"));
			prpTitemCarSchema.setRelievingAreaCode((String)paramMap.get("RELIEVINGAREACODE")!=null?(String)paramMap.get("RELIEVINGAREACODE"):"");
			String strLastYearEndDateCI=carData.elementText("LASTYEARENDDATECI");
			paramMap.put("LASTYEARENDDATECI", strLastYearEndDateCI!=null?strLastYearEndDateCI:"");
	    	String strLastYearEndDateBI=carData.elementText("LASTYEARENDDATEBI");
	    	paramMap.put("LASTYEARENDDATEBI", strLastYearEndDateBI!=null?strLastYearEndDateBI:"");
			
	    	
	    	prpTitemCarExtSchema.setTradeName(carData.elementText("TRADENAME"));
	    	prpTitemCarExtSchema.setBrand(carData.elementText("BRAND"));
	    	prpTitemCarExtSchema.setDeptName(carData.elementText("DEPTNAME"));
	    	
			if(blProposalCI!=null){
				
				String strComCode = (String)paramMap.get("comcode");
				String strStartDateCI = (String)paramMap.get("startDateCI");
				String strEnrollDateCI = carData.elementText("ENROLLDATE").trim();
				String strUseYearsCI = PubTools.calculateCarYears(strComCode,strStartDateCI, strEnrollDateCI);
				prpTitemCarSchema.setUseYears(strUseYearsCI);
				prpTitemCarSchema.setClauseType(carData.elementText("CLAUSETYPECI"));
				
				
				PrpTitemCarSchema prpTitemCarSchemaCI = new PrpTitemCarSchema();
				prpTitemCarSchemaCI.setSchema(prpTitemCarSchema);
				blProposalCI.getBLPrpTitemCar().setArr(prpTitemCarSchemaCI);
				
				PrpTitemCarExtSchema prpTitemCarExtSchemaCI = new PrpTitemCarExtSchema();
				prpTitemCarExtSchemaCI.setSchema(prpTitemCarExtSchema);
				blProposalCI.getBLPrpTitemCarExt().setArr(prpTitemCarExtSchemaCI);

				blProposalCI.getBLPrpTitemCar().getArr(0).setSearchSequenceNo(carData.elementText("SEARCHSEQUENCENO"));
				
			}
			if(blProposalBI != null){
				
				String strComCode = (String)paramMap.get("comcode");
				String strStartDateBI = (String)paramMap.get("startDateBI");
				String strEnrollDateBI = carData.elementText("ENROLLDATE").trim();
				String strUseYearsBI = PubTools.calculateCarYears(strComCode,strStartDateBI, strEnrollDateBI);
				prpTitemCarSchema.setUseYears(strUseYearsBI);
				prpTitemCarSchema.setClauseType(carData.elementText("CLAUSETYPEBI"));
				PrpTitemCarSchema prpTitemCarSchemaBI = new PrpTitemCarSchema();
				prpTitemCarSchemaBI.setSchema(prpTitemCarSchema);
				blProposalBI.getBLPrpTitemCar().setArr(prpTitemCarSchemaBI);
				
				PrpTitemCarExtSchema prpTitemCarExtSchemaBI = new PrpTitemCarExtSchema();
				prpTitemCarExtSchemaBI.setSchema(prpTitemCarExtSchema);
				blProposalBI.getBLPrpTitemCarExt().setArr(prpTitemCarExtSchemaBI);
				
				blProposalBI.getBLPrpTitemCar().getArr(0).setSearchSequenceNo(carData.elementText("SEARCHSEQUENCENO"));
				
			}
		}
	}
	
	/**
	 * 处理CAROWNER_LIST节           考虑此接口是否需要此节点
	 * @param carOwnerList
	 * @param paramMap
	 * @param BLProposalMap
	 * @throws Exception
	 */
	public void parseCarOwner(Element carOwnerList, Map paramMap, Map BLProposalMap)throws Exception{
		
		BLProposal blProposalCI = (BLProposal)BLProposalMap.get(QuotationUtils.BLPROPOSALCI);
		BLProposal blProposalBI = (BLProposal)BLProposalMap.get(QuotationUtils.BLPROPOSALBI);
		
		Iterator iterator = carOwnerList.elementIterator();
		while(iterator.hasNext()){
			
			Element carownerData = (Element)iterator.next();
			if("0".equals(carownerData.elementText("PERSONTYPE"))){

				
				if(blProposalCI!=null){
					blProposalCI.getBLPrpTitemCar().getArr(0).setCarOwner(carownerData.elementText("PERSONNAME"));
					blProposalCI.getBLPrpTitemCar().getArr(0).setCarInsuredRelation(carownerData.elementText("CARINSUREDRALATION"));




					blProposalCI.getBLPrpTitemCar().getArr(0).setCarOwnerNature(carownerData.elementText("PERSONCLASS"));
					blProposalCI.getBLPrpTitemCar().getArr(0).setInsuredTypeCode(carownerData.elementText("IDENTIFYTYPE"));
					blProposalCI.getBLPrpTitemCar().getArr(0).setOwnerAddress(carownerData.elementText("IDENTIFYNUMBER"));
				}
				
				if(blProposalBI != null){
					blProposalBI.getBLPrpTitemCar().getArr(0).setCarOwner(carownerData.elementText("PERSONNAME"));
					blProposalBI.getBLPrpTitemCar().getArr(0).setCarInsuredRelation(carownerData.elementText("CARINSUREDRALATION"));




					blProposalBI.getBLPrpTitemCar().getArr(0).setCarOwnerNature(carownerData.elementText("PERSONCLASS"));
					blProposalBI.getBLPrpTitemCar().getArr(0).setInsuredTypeCode(carownerData.elementText("IDENTIFYTYPE"));
					blProposalBI.getBLPrpTitemCar().getArr(0).setOwnerAddress(carownerData.elementText("IDENTIFYNUMBER"));
	
				}
				
			}
		}
	}
	
	
	/**
	 * 处理APPLI_LIST节
	 * @param appliList
	 * @param paramMap
	 * @param BLProposalMap
	 * @throws Exception
	 */
	public void parseAppli(Element appliList, Map paramMap, Map BLProposalMap)throws Exception{
		
		BLProposal blProposalCI = (BLProposal)BLProposalMap.get(QuotationUtils.BLPROPOSALCI);
		BLProposal blProposalBI = (BLProposal)BLProposalMap.get(QuotationUtils.BLPROPOSALBI);
		
		Iterator iterator = appliList.elementIterator();
		while(iterator.hasNext()){
			
			Element appliData = (Element)iterator.next();
			if("2".equals(appliData.elementText("PERSONTYPE"))){
				
				if(blProposalCI!=null){
					if(blProposalCI.getBLPrpTmain().getSize()>0){
						blProposalCI.getBLPrpTmain().getArr(0).setAppliName(appliData.elementText("PERSONNAME"));
					}else{
						PrpTmainSchema prpTmainSchemaCI = new PrpTmainSchema();
						prpTmainSchemaCI.setAppliName(appliData.elementText("PERSONNAME"));
						blProposalCI.getBLPrpTmain().setArr(prpTmainSchemaCI);
					}
				}
				
				if(blProposalBI!=null){
					if(blProposalBI.getBLPrpTmain().getSize()>0){
						blProposalBI.getBLPrpTmain().getArr(0).setAppliName(appliData.elementText("PERSONNAME"));
					}else{
						PrpTmainSchema prpTmainSchemaBI = new PrpTmainSchema();
						prpTmainSchemaBI.setAppliName(appliData.elementText("PERSONNAME"));
						blProposalBI.getBLPrpTmain().setArr(prpTmainSchemaBI);
					}
	
				}
			}
		}
	}
	
	/**
	 * 处理INSURED_LIST节
	 * @param insuredList
	 * @param paramMap
	 * @param BLProposalMap
	 * @throws Exception
	 */
	public void parseInsured(Element insuredList, Map paramMap, Map BLProposalMap)throws Exception{
		
		BLProposal blProposalCI = (BLProposal)BLProposalMap.get(QuotationUtils.BLPROPOSALCI);
		BLProposal blProposalBI = (BLProposal)BLProposalMap.get(QuotationUtils.BLPROPOSALBI);
		
		Iterator iterator = insuredList.elementIterator();
		while(iterator.hasNext()){
			

			Element insuredData = (Element)iterator.next();
			
			PrpTinsuredSchema prpTinsuredSchema = new PrpTinsuredSchema();
			PrpTinsuredNatureSchema prpInsuredNatureSchema = new PrpTinsuredNatureSchema();
			
			if("1".equals(insuredData.elementText("PERSONTYPE"))||"2".equals(insuredData.elementText("PERSONTYPE"))){
			  if("1".equals(insuredData.elementText("PERSONTYPE"))){
				prpTinsuredSchema.setInsuredName(insuredData.elementText("PERSONNAME"));
				
				prpTinsuredSchema.setInsuredFlag(insuredData.elementText("PERSONTYPE"));

				prpTinsuredSchema.setInsuredType(insuredData.elementText("INSUREDTYPE"));
				
					
				
				
				if("0".equals(insuredData.elementText("PERSONCLASS"))){
					prpTinsuredSchema.setInsuredNature("3");					
				}else{
					prpTinsuredSchema.setInsuredNature("4");					
				}
				if("4".equals(prpTinsuredSchema.getInsuredNature())){
					prpTinsuredSchema.setInsuredType("2");
				}else{
					prpTinsuredSchema.setInsuredType("1");
				}
				

				
				prpTinsuredSchema.setIdentifyType(insuredData.elementText("IDENTIFYTYPE"));
				prpTinsuredSchema.setIdentifyNumber(insuredData.elementText("IDENTIFYNUMBER"));
				prpTinsuredSchema.setInsuredAddress(insuredData.elementText("POSTADDRESS"));
				prpTinsuredSchema.setPostCode(insuredData.elementText("POSTCODE"));
				prpTinsuredSchema.setMobile(insuredData.elementText("MOBILE"));
				prpTinsuredSchema.setEmail(insuredData.elementText("EMAIL"));
				
                if("3".equals(prpTinsuredSchema.getInsuredNature())){
					
					prpInsuredNatureSchema.setInsuredFlag(prpTinsuredSchema.getInsuredFlag());
					prpInsuredNatureSchema.setSex(insuredData.elementText("SEX"));
					prpInsuredNatureSchema.setAge(insuredData.elementText("AGE"));
					if(blProposalCI!=null){
						blProposalCI.getBLPrpTmain().getArr(0).setInsuredName(insuredData.elementText("PERSONNAME"));
						PrpTinsuredNatureSchema prpTinsuredNatureSchemaCI = new PrpTinsuredNatureSchema();
						prpTinsuredNatureSchemaCI.setSchema(prpInsuredNatureSchema);
						blProposalCI.getBLPrpTinsuredNature().setArr(prpTinsuredNatureSchemaCI);
					}
					if(blProposalBI!=null){
						blProposalBI.getBLPrpTmain().getArr(0).setInsuredName(insuredData.elementText("PERSONNAME"));
						PrpTinsuredNatureSchema prpTinsuredNatureSchemaBI = new PrpTinsuredNatureSchema();
						prpTinsuredNatureSchemaBI.setSchema(prpInsuredNatureSchema);
						blProposalBI.getBLPrpTinsuredNature().setArr(prpTinsuredNatureSchemaBI);
					}
					
				}				
			  }
			   if("2".equals(insuredData.elementText("PERSONTYPE"))){
				  
				  prpTinsuredSchema.setInsuredName(insuredData.elementText("PERSONNAME"));
					
					prpTinsuredSchema.setInsuredFlag(insuredData.elementText("PERSONTYPE"));
					prpTinsuredSchema.setInsuredType(insuredData.elementText("INSUREDTYPE"));
					if(prpTinsuredSchema.getInsuredType()==null||"".equals(prpTinsuredSchema.getInsuredType())){
						prpTinsuredSchema.setInsuredType("1");
					}
					
					if("0".equals(insuredData.elementText("PERSONCLASS"))){
						prpTinsuredSchema.setInsuredNature("3");					
					}else{
						prpTinsuredSchema.setInsuredNature("4");					
					}

					
					prpTinsuredSchema.setIdentifyNumber(insuredData.elementText("IDENTIFYNUMBER"));
					prpTinsuredSchema.setIdentifyType(insuredData.elementText("IDENTIFYTYPE"));
					prpTinsuredSchema.setInsuredAddress(insuredData.elementText("INSUREDADDRESS"));
					prpTinsuredSchema.setPostCode(insuredData.elementText("POSTCODE"));
					prpTinsuredSchema.setMobile(insuredData.elementText("MOBILE"));
					prpTinsuredSchema.setEmail(insuredData.elementText("EMAIL"));
					if("4".equals(prpTinsuredSchema.getInsuredNature())){
						prpTinsuredSchema.setInsuredType("2");
					}else{
						prpTinsuredSchema.setInsuredType("1");
					}
					
			  }
			   
			   
			   
			
			if(blProposalCI!=null){
	
				PrpTinsuredSchema prpTinsuredSchemaCI = new PrpTinsuredSchema();
				prpTinsuredSchemaCI.setSchema(prpTinsuredSchema);
				blProposalCI.getBLPrpTinsured().setArr(prpTinsuredSchemaCI);
							
			}
			
			if(blProposalBI!=null){

				PrpTinsuredSchema prpTinsuredSchemaBI = new PrpTinsuredSchema();
				prpTinsuredSchemaBI.setSchema(prpTinsuredSchema);
				blProposalBI.getBLPrpTinsured().setArr(prpTinsuredSchemaBI);
				
				
			}
			}	
		}
	}
	
	/**
	 * 处理PRPTNEWENGAGE_LIST节
	 * @param prptnewengageList
	 * @param paramMap
	 * @param BLProposalMap
	 * @throws Exception
	 */
	public void parsePrptnewengage(Element prptnewengageList, Map paramMap, Map BLProposalMap)throws Exception{
		
		BLProposal blProposalCI = (BLProposal)BLProposalMap.get(QuotationUtils.BLPROPOSALCI);
		BLProposal blProposalBI = (BLProposal)BLProposalMap.get(QuotationUtils.BLPROPOSALBI);
		
		Iterator iterator = prptnewengageList.elementIterator();
		while(iterator.hasNext()){
			
			
			Element prptnewengageData = (Element)iterator.next();
			
			PrpTNewEngageSchema prpTNewEngageSchema = new PrpTNewEngageSchema();
			
			String strPROPOSALNO	= prptnewengageData.elementText("PROPOSALNO");
	    	String strFIRBENNEFITINFO	= prptnewengageData.elementText("FIRBENNEFITINFO");
	    	String strMANAGERNAME	= prptnewengageData.elementText("MANAGERNAME");
	    	String strMANAGERMOBILENO	= prptnewengageData.elementText("MANAGERMOBILENO");
	    	String strSUNSHINECUSTOMER	= prptnewengageData.elementText("SUNSHINECUSTOMER");
	    	String strSPECIALIZE	= prptnewengageData.elementText("SPECIALIZE");
	    	String strFLAG	= prptnewengageData.elementText("FLAG");
	    	
	    	prpTNewEngageSchema.setProposalNo(strPROPOSALNO);
	    	prpTNewEngageSchema.setFirbennefitInfo(strFIRBENNEFITINFO);
	    	prpTNewEngageSchema.setManagerName(strMANAGERNAME);
	    	prpTNewEngageSchema.setManagerMobileNo(strMANAGERMOBILENO);
	    	prpTNewEngageSchema.setSunShineCustomer(strSUNSHINECUSTOMER);	
	    	prpTNewEngageSchema.setSpecialize(strSPECIALIZE)	;
	    	prpTNewEngageSchema.setFlag(strFLAG);
	    	
			
				
				
				if(blProposalCI!=null){
					
					PrpTNewEngageSchema prpTNewEngageSchemaCI = new PrpTNewEngageSchema();
					prpTNewEngageSchemaCI.setSchema(prpTNewEngageSchema);
					blProposalCI.getBlPrpTNewEngage().setArr(prpTNewEngageSchemaCI);
				}
				if(blProposalBI!=null){
					
					PrpTNewEngageSchema prpTNewEngageSchemaBI = new PrpTNewEngageSchema();
					prpTNewEngageSchemaBI.setSchema(prpTNewEngageSchema);
					blProposalBI.getBlPrpTNewEngage().setArr(prpTNewEngageSchemaBI);
				}
		}
	}
	
	
}
	

	
	
	
