package com.sp.prpall.blsvr.misc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sinosig.schedule.client.dto.MidLogSchema;
import com.sinosig.schedule.client.dao.MidLogJdbcDao;

import com.sp.casualtyloan.pubfun.PubFuns;
import com.sp.platform.bl.action.domain.BLBankInterFace_DetailAction;
import com.sp.platform.bl.action.domain.BLPrpDoutsideAgreedetailAction;
import com.sp.platform.bl.facade.BLPrpDagentFacade;
import com.sp.platform.bl.facade.BLPrpDagreeDetailFacade;
import com.sp.platform.bl.facade.BLPrpDagreementFacade;
import com.sp.platform.bl.facade.BLPrpDcompanyFacade;
import com.sp.platform.bl.facade.BLPrpDitemFacade;
import com.sp.platform.bl.facade.BLPrpDkindFacade;
import com.sp.platform.bl.facade.BLPrpDriskConfigFacade;
import com.sp.platform.bl.facade.BLPrpdBankCompannyFacade;
import com.sp.platform.dto.domain.BankInterFace_DetailDto;
import com.sp.platform.dto.domain.PrpDagentDto;
import com.sp.platform.dto.domain.PrpDagreeDetailDto;
import com.sp.platform.dto.domain.PrpDagreementDto;
import com.sp.platform.dto.domain.PrpDcompanyDto;
import com.sp.platform.dto.domain.PrpDitemDto;
import com.sp.platform.dto.domain.PrpDkindDto;
import com.sp.platform.dto.domain.PrpDoutsideAgreedetailDto;
import com.sp.platform.dto.domain.PrpDriskConfigDto;
import com.sp.platform.dto.domain.PrpdBankCompannyDto;

import com.sp.platform.resource.dtofactory.domain.DBBankInterFace_Detail;
import com.sp.platform.ui.control.action.UIPrpDriskConfigAction;
import com.sp.prpall.blsvr.tb.BLProposal;
import com.sp.prpall.blsvr.tb.BLPrpTaddress;
import com.sp.prpall.blsvr.tb.BLPrpTexpense;
import com.sp.prpall.blsvr.tb.BLPrpTfee;
import com.sp.prpall.blsvr.tb.BLPrpTinsured;
import com.sp.prpall.blsvr.tb.BLPrpTinsuredNature;
import com.sp.prpall.blsvr.tb.BLPrpTitemKind;
import com.sp.prpall.blsvr.tb.BLPrpTmain;
import com.sp.prpall.blsvr.tb.BLPrpTmainCasualty;
import com.sp.prpall.blsvr.tb.BLPrpTplan;
import com.sp.prpall.jfcd.cb.CFeedBack;
import com.sp.prpall.pubfun.CheckChannelYB;
import com.sp.prpall.pubfun.PubTools;
import com.sp.prpall.pubfun.eiespublictool.EiesPublicTool;
import com.sp.prpall.schema.PrpTaddressSchema;
import com.sp.prpall.schema.PrpTexpenseSchema;
import com.sp.prpall.schema.PrpTfeeSchema;
import com.sp.prpall.schema.PrpTinsuredNatureSchema;
import com.sp.prpall.schema.PrpTinsuredSchema;
import com.sp.prpall.schema.PrpTitemKindSchema;
import com.sp.prpall.schema.PrpTmainCasualtySchema;
import com.sp.prpall.schema.PrpTmainSchema;
import com.sp.prpall.schema.PrpTplanSchema;
import com.sp.sysframework.common.datatype.DateTime;
import com.sp.sysframework.reference.DBManager;
import com.sp.taskmng.util.TaskMngUtil;
import com.sp.utiall.blsvr.BLPrpDcustomerIdvNew;
import com.sp.utiall.blsvr.BLPrpDration;
import com.sp.utiall.dbsvr.DBPrpDcompany;
import com.sp.utility.SysConfig;
import com.sp.utility.UtiPower;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.utility.string.ChgDate;
import com.sp.utility.string.Date;
import com.sp.utility.string.Str;

/**
 * 信XXXXX通生成核心数据
 * @author wangchuanzhong 20091221
 *
 */
public class BLGenerateACCData {
	private static final Log logger = LogFactory.getLog(BLGenerateACCData.class);
	
    /**
     * 
     * 本类完成的功能：
     * 将BankInterFace_Detail表中当前所有的数据转换为XX数据，并送相关数据到收付的接口表中
     * for(BankInterFace_Detail) {
     *   获得BankInterFace_Detail表数据
     *   生成XX单数据
     *   获得XX号码
     *   生成XX数据
     *   反写BankInterFace_Detail表中的数据
     *   将对象放入Attribute中
     * }
     */
	String[] arrCustomerCode = new String[2];

    public BLGenerateACCData() {
    }
  
    
    String jobName="中间业务平台转数失败日志记录";
    String comCode ="";
    String jobGroup ="";
    String jobDes="ACC渠道转数失败日志记录";
    String businessKey ="";
    String executeDate ="";
    String successFlag ="";
    String errorMessage ="";
    String errorStack  ="";
    String createDate  ="";
    String createCode   ="";
    EiesPublicTool eiesPublicTool=new EiesPublicTool();
    
    /**
     * @desc 信XXXXX通由接口表数据生成XX数据
     * @param request
     * @param response
     * @param strRiskCode
     * @param strComCode
     * @return strReturnMessage：size=0 没有可生成的XX单、XX/size>0 空字符串，将对象放入Attribute中
     * @throws Exception
     */
    public String build(HttpServletRequest request, HttpServletResponse response, String iRiskCode, String iComCode, String iVscode) throws Exception {
        DbPool dbPool = new DbPool();
        DBManager dbManager = null;
        
 
        int intYear = 0;
        int intMonth = 0;
        int intDay = 0;
        DateTime Date = new DateTime().current();
        
        BLBankInterFace_DetailAction blBankInterFace_DetailAction = new BLBankInterFace_DetailAction();
        ArrayList bankInterFace_DetailDtoList = null;
        BankInterFace_DetailDto bankInterFace_DetailDto = null;
        BLProposal blProposal = null;
        String strManageRate = "0.0000";     
        String strSaleSalaryRate = "0.0000"; 
        
        
        String strIsEiesFlag = "";
        ArrayList policyNoList = new ArrayList();
        String strCondition = "";
        String strReturnMessage = "";
        String strPolicyNo = "";
        String strProposalNo = "";
        String strToComCode = iComCode;     
       
      
        CFeedBack cFeedBack = new CFeedBack();
        Map<String, Boolean> mapJMS=new HashMap<String, Boolean>();
        
        try {
            dbPool.open(SysConfig.CONST_DDCCDATASOURCE);
            dbManager = dbPool.getDBManager(SysConfig.CONST_DDCCDATASOURCE);
            strCondition = " RevolutionFlag='0'" 
         		 + " AND Invalid='0'"    
                + " AND RiskCode='" + iRiskCode + "'" 
                + " AND IsEiesFlag='3'"
                + " AND ComCode ='" + strToComCode + "'"
                + " ORDER BY UploadFileSeq,Trans_Seq"; 
            DBBankInterFace_Detail dbBankInterFace_Detail = new DBBankInterFace_Detail(dbManager);
            bankInterFace_DetailDtoList = (ArrayList)dbBankInterFace_Detail.findByConditions(strCondition,1,100);
            for(int i = 0; i < bankInterFace_DetailDtoList.size(); i++) {
                
                try{
                	bankInterFace_DetailDto = (BankInterFace_DetailDto)bankInterFace_DetailDtoList.get(i);
                 
                	
                  businessKey =bankInterFace_DetailDto.getPolicyno();
                  comCode = bankInterFace_DetailDto.getComCode();
              	 jobGroup =bankInterFace_DetailDto.getBfbankCode();
              	
              	  if("402".equals(jobGroup)){
              		
              		
              		jobGroup=eiesPublicTool.getExtEnterpCode(bankInterFace_DetailDto.getBankbranchCode());
              	}
                  intYear = Date.getYear();
                  intMonth = Date.getMonth();
                  intDay = Date.getDay();
                  executeDate = new Integer(intYear).toString() + "-"
                                   + new Integer(intMonth).toString() + "-"
                                   + new Integer(intDay).toString();
                  successFlag =bankInterFace_DetailDto.getRevolutionFlag();
                 
                  errorStack  ="";
                  createDate  =executeDate;
                 createCode   ="ZJYW_ACC_admin";
               
                 
                	
                    strPolicyNo = bankInterFace_DetailDto.getPolicyno();
                    
                    strIsEiesFlag = bankInterFace_DetailDto.getIseiesFlag();
                    if(strIsEiesFlag.equals("3"))
                    {
                    	iVscode= bankInterFace_DetailDto.getVisacode();
                    }
                    
                    strProposalNo = bankInterFace_DetailDto.getProposalNo();
                    
                    blProposal = generateObjectOfProposal(dbManager,bankInterFace_DetailDto,iVscode);
                    
                    
                    dbPool.beginTransaction();
                    blProposal.save(dbPool, "I", false);
                    
                    
                    com.sp.undwrt.bl.facade.BLTaskFacade blTaskFacade = new com.sp.undwrt.bl.facade.BLTaskFacade();
                    
                    blTaskFacade.start(dbManager,"11","T",strProposalNo,iRiskCode,
                 		   "27",bankInterFace_DetailDto.getComCode(),bankInterFace_DetailDto.getComCode(),
                 		   

                 		  bankInterFace_DetailDto.getHandlerCode(),bankInterFace_DetailDto.getHandlerCode(),bankInterFace_DetailDto.getHandler1Code(),"");
                           
                  
                    mapJMS = cFeedBack.getJMSTransBusinessNo(dbPool, "T", strProposalNo);
                    dbPool.commitTransaction();
                  
                    cFeedBack.checkIsJMSSendBatch(dbPool,"T" ,mapJMS);
                    bankInterFace_DetailDto.setRevolutionFlag("1");
                    
                }catch(Exception uwtExcetion){
                	
                	
                	if(errorMessage.equals("")){
                		errorMessage="双核系统自动核XXXXX不通过。请检查错误日志表失败堆栈信息！";
                	}
                	





                    eiesPublicTool.insertMidDataLog( businessKey, errorMessage, uwtExcetion, jobName, comCode , jobGroup,jobDes );
                	
                    
                	uwtExcetion.printStackTrace();
                	
                	

                	
                	dbPool.rollbackTransaction();
                    
                }
                
                policyNoList.add(strPolicyNo);
                try
                {
                	
                    blBankInterFace_DetailAction.update(dbManager,bankInterFace_DetailDto);
                }catch(Exception ex)
                {
                	
                }
            }
            if(bankInterFace_DetailDtoList.size() <= 0) {
                strReturnMessage = "没有可生成的XX单、XX！";
            } else {
                
                request.setAttribute("bankInterFace_DetailDtoList", bankInterFace_DetailDtoList);
                request.setAttribute("policyNoList", policyNoList);
            }           
        } catch(Exception e) {
        	
        	e.printStackTrace();
        	
            throw e;
        }
        finally {
            dbPool.close();
        }

        return strReturnMessage;
    }

    /**
     * @desc 信XXXXX通由接口表数据生成XX数据：生成XX单对象BLProposal
     * @param dbPool
     * @param bankInterFace_DetailDto
     * @param iVisaCode
     * @return blProposal
     * @throws Exception
     */
    public BLProposal generateObjectOfProposal(DBManager dbManager,BankInterFace_DetailDto bankInterFace_DetailDto, String iVisaCode) throws Exception {
        BLProposal blProposal = new BLProposal();
        BLPrpTmain blPrpTmain = null;
        BLPrpTinsured blPrpTinsured = null;
        BLPrpTaddress blPrpTaddress = null;
        BLPrpTitemKind blPrpTitemKind = null;
        BLPrpTfee blPrpTfee = null;
        BLPrpTplan blPrpTplan = null;
        BLPrpTmainCasualty blPrpTmainCasualty = null;
        BLPrpTexpense blPrpTexpense = null;

        
        blPrpTinsured = generateObjectOfTinsured(bankInterFace_DetailDto);
        blPrpTmain = generateObjectOfTmain(dbManager,bankInterFace_DetailDto,iVisaCode); 
        blPrpTaddress = generateObjectOfTaddress(bankInterFace_DetailDto);
        blPrpTitemKind = generateObjectOfTitemKind(bankInterFace_DetailDto);
        blPrpTfee = generateObjectOfTfee(bankInterFace_DetailDto); 
        blPrpTplan = generateObjectOfTplanNew(bankInterFace_DetailDto);
        blPrpTmainCasualty = generateObjectOfTmainCasualty(bankInterFace_DetailDto);
        blPrpTexpense = generateObjectOfTexpense(dbManager,bankInterFace_DetailDto);
        
        BLPrpTinsuredNature blPrpTinsuredNature = generateObjectOfTinsuredNature(bankInterFace_DetailDto,blPrpTinsured);
        blProposal.setBLPrpTinsuredNature(blPrpTinsuredNature);
        
        
        
        blProposal.setBLPrpTmain(blPrpTmain);
        blProposal.setBLPrpTinsured(blPrpTinsured);
        blProposal.setBLPrpTaddress(blPrpTaddress);
        blProposal.setBLPrpTitemKind(blPrpTitemKind);
        blProposal.setBLPrpTfee(blPrpTfee);
        blProposal.setBLPrpTplan(blPrpTplan);
        blProposal.setBLPrpTmainCasualty(blPrpTmainCasualty);
        blProposal.setBLPrpTexpense(blPrpTexpense);

        return blProposal;
    }

    /**
     * @desc 信XXXXX通由接口表数据生成XX数据：生成XX单主信息对象BLPrpTmain
     * @param arrCustomerCode
     * @param bankInterFace_DetailDto
     * @param iVisaCode
     * @return blPrpTmain
     * @throws Exception
     */
    public BLPrpTmain generateObjectOfTmain(DBManager dbManager,BankInterFace_DetailDto bankInterFace_DetailDto, 
    		String iVisaCode) throws Exception {
        BLPrpDagreementFacade blPrpDagreementFacade = new BLPrpDagreementFacade();
        PrpDagreementDto prpDagreementDto = null;
        
        
        MidLogSchema midLogSchema= new MidLogSchema();
      
        BLPrpDriskConfigFacade blPrpDriskConfigFacade = new BLPrpDriskConfigFacade();
        BLPrpTmain blPrpTmain = new BLPrpTmain();
        PrpTmainSchema prpTmainSchema = new PrpTmainSchema();
        BLPrpdBankCompannyFacade blPrpdBankCompannyFacade = new BLPrpdBankCompannyFacade();

        Collection prpDagreementDtoList = null;
        PrpDagreeDetailDto prpDagreeDetailDto = null;
        BLPrpDagreeDetailFacade blPrpDagreeDetailFacade = new BLPrpDagreeDetailFacade();
        DateTime currentDate = new DateTime().current();
        
        String strBankCode = "";
        String strAgentCode = "";
        String strAgreementNo = "";
        String strStartDatePrpT = "";
        String strEndDatePrpT = "";
        Collection prpdBankCompannyDtoList = null;
        
        String strUploadDate  = "";
        String strCurrentDate = "";

        int intYear = 0;
        int intMonth = 0;
        int intDay = 0;
        
        double dbDisRate = 0;
        
        DBPrpDcompany dbPrpDcompany = new DBPrpDcompany();
        String strChannelType = "";
        int iResult = 0;
        
        
        
        
        
        
        strBankCode = bankInterFace_DetailDto.getBankbranchCode();
        StringBuffer strQuery = new StringBuffer();
        strQuery.append(" BankCode='");
        strQuery.append(strBankCode);
        strQuery.append("'");
        try{
        prpdBankCompannyDtoList = blPrpdBankCompannyFacade.findByConditions(strQuery.toString());
        }catch (Exception ex){
        	ex.printStackTrace();
        }

















        if(prpdBankCompannyDtoList.size() == 0)
        {
        	errorMessage="prpdbankcompanny表"+strBankCode+"取值失败,请管理员进行配置！" ;
        throw new UserException(-98, -1167, "BLGenerateACCData.generateObjectOfTmain",
    			"prpdbankcompanny表银行网点代码"+strBankCode+"取值失败,请管理员进行配置！" ); 
        }
        if(prpdBankCompannyDtoList.size() > 0) {
        	strAgentCode = ((PrpdBankCompannyDto)((ArrayList)prpdBankCompannyDtoList).get(0)).getAgentCode();
        }   
        prpDagreementDtoList = blPrpDagreementFacade.findByConditions("AgentCode='" + strAgentCode + "' and validstatus = '1'");
        for(Iterator prpDagreementDtoListTmp = prpDagreementDtoList.iterator(); prpDagreementDtoListTmp.hasNext();) {
            prpDagreementDto = (PrpDagreementDto)prpDagreementDtoListTmp.next();
            strAgreementNo = prpDagreementDto.getAgreementNo();
            break;
        }

        CheckChannelYB checkChannelYB = new CheckChannelYB();
        if(!checkChannelYB.checkChannel(bankInterFace_DetailDto.getComCode(),strAgentCode)){
        	
        	errorMessage="generateObjectOfTmain"+"业务来源与渠道校验失败，请检查相关配置！";
        	
      	  throw new UserException(-98, -1167, "generateObjectOfTmain",
      	            "业务来源与渠道校验失败，请检查相关配置！" ); 
        }
        
        
        if("1".equals(Str.rightTrim(SysConfig.getProperty("CASUALTY_ACC_SWITCH")))){
        	BLPrpDoutsideAgreedetailAction blPrpDoutsideAgreedetailAction = new BLPrpDoutsideAgreedetailAction();
			PrpDoutsideAgreedetailDto prpDoutsideAgreedetailDto = null;
		    prpDoutsideAgreedetailDto = blPrpDoutsideAgreedetailAction.findByPrimaryKey(dbManager, strAgreementNo,bankInterFace_DetailDto.getProductCode());
		    if(prpDoutsideAgreedetailDto==null){
		    	errorMessage="BLGenerateACCData.generateObjectOfTmain"+"手续费比例取值失败,请管理员进行配置！" ;
				throw new UserException(-98, -1167, "BLGenerateACCData.generateObjectOfTmain",
				"手续费比例取值失败,请管理员进行配置！" ); }
	        if((String.valueOf(prpDoutsideAgreedetailDto.getCommissionrate())).trim().equals("")
	           ||prpDoutsideAgreedetailDto.getCommissionrate()<0){
	        	errorMessage="BLGenerateACCData.generateObjectOfTmain"+"手续费比例取值失败,请管理员进行配置！" ;
				throw new UserException(-98, -1167, "BLGenerateACCData.generateObjectOfTmain",
						"手续费比例取值失败,请管理员进行配置！" ); 
	        }else
	        {
	        	dbDisRate = prpDoutsideAgreedetailDto.getCommissionrate();
	        }
		    
        }else{
            prpDagreeDetailDto = blPrpDagreeDetailFacade.findByPrimaryKey(strAgreementNo, bankInterFace_DetailDto.getRiskCode(),"0000");

            if(prpDagreeDetailDto==null){
            	errorMessage="BLGenerateACCData.generateObjectOfTmain"+"手续费比例取值失败,请管理员进行配置！" ;
			      throw new UserException(-98, -1167, "BLGenerateACCData.generateObjectOfTmain",
			      "手续费比例取值失败,请管理员进行配置！" );} 
           if((String.valueOf(prpDagreeDetailDto.getTopCommission())).trim().equals("")
              ||prpDagreeDetailDto.getTopCommission()<0){
        	   errorMessage="BLGenerateACCData.generateObjectOfTmain"+"手续费比例取值失败,请管理员进行配置！" ;
			      throw new UserException(-98, -1167, "BLGenerateACCData.generateObjectOfTmain",
					  "手续费比例取值失败,请管理员进行配置！" ); 
           }else
           {
        	dbDisRate = prpDagreeDetailDto.getTopCommission();
           }
        }
        
        intYear = bankInterFace_DetailDto.getStartDate().getYear();
        intMonth = bankInterFace_DetailDto.getStartDate().getMonth();
        intDay = bankInterFace_DetailDto.getStartDate().getDay();
        strStartDatePrpT = new Integer(intYear).toString() + "-"
                         + new Integer(intMonth).toString() + "-"
                         + new Integer(intDay).toString();

        intYear = bankInterFace_DetailDto.getEndDate().getYear();
        intMonth = bankInterFace_DetailDto.getEndDate().getMonth();
        intDay = bankInterFace_DetailDto.getEndDate().getDay();
        strEndDatePrpT = new Integer(intYear).toString() + "-"
                       + new Integer(intMonth).toString() + "-"
                       + new Integer(intDay).toString();
        
        
        intYear = bankInterFace_DetailDto.getUploadDate().getYear();
        intMonth = bankInterFace_DetailDto.getUploadDate().getMonth();
        intDay = bankInterFace_DetailDto.getUploadDate().getDay();
        strUploadDate = new Integer(intYear).toString() + "-"
                      + new Integer(intMonth).toString() + "-"
                      + new Integer(intDay).toString();
        
        intYear = currentDate.getYear();
        intMonth = currentDate.getMonth();
        intDay = currentDate.getDay();
        strCurrentDate = new Integer(intYear).toString() + "-"
                       + new Integer(intMonth).toString() + "-"
                       + new Integer(intDay).toString();

        
        prpTmainSchema.setProposalNo(bankInterFace_DetailDto.getProposalNo());
        prpTmainSchema.setClassCode(bankInterFace_DetailDto.getClassCode());
        prpTmainSchema.setRiskCode(bankInterFace_DetailDto.getRiskCode());
        prpTmainSchema.setPolicySort("2"); 
        
        prpTmainSchema.setBusinessNature("9"); 
        prpTmainSchema.setLanguage("C");
        prpTmainSchema.setPolicyType("01"); 
        prpTmainSchema.setAppliCode(arrCustomerCode[0]);
        prpTmainSchema.setAppliName(bankInterFace_DetailDto.getAppliName());
        prpTmainSchema.setAppliAddress(bankInterFace_DetailDto.getAddress());
        prpTmainSchema.setInsuredCode(arrCustomerCode[1]);
        prpTmainSchema.setInsuredName(bankInterFace_DetailDto.getInsuredName());
        prpTmainSchema.setInsuredAddress(bankInterFace_DetailDto.getInsuredAdress());
        prpTmainSchema.setOperateDate(bankInterFace_DetailDto.getInvestDate().toString());
        prpTmainSchema.setStartDate(strStartDatePrpT);
        prpTmainSchema.setStartHour("0");
        prpTmainSchema.setEndDate(strEndDatePrpT);
        prpTmainSchema.setEndHour("24");
        prpTmainSchema.setSignDate(bankInterFace_DetailDto.getStartDate().addDay(-1).toString());
        prpTmainSchema.setDisRate(String.valueOf(dbDisRate));
        prpTmainSchema.setDiscount("100");
        prpTmainSchema.setCurrency(bankInterFace_DetailDto.getCurrency());
        prpTmainSchema.setSumValue("0");
        
        double kindMainAmount = 0; 
        if(bankInterFace_DetailDto.getProductCode()!=null  && "P35022".equals(bankInterFace_DetailDto.getProductCode()) ){
        	kindMainAmount = Double.valueOf(bankInterFace_DetailDto.getSumamount());
        	if(kindMainAmount <= 200000){
        		kindMainAmount = PubTools.round(kindMainAmount/2,2);
        	}if(kindMainAmount > 200000){
        		kindMainAmount = PubTools.round(kindMainAmount-100000,2);
        	}
        	prpTmainSchema.setSumAmount(String.valueOf(kindMainAmount));
        }else{
        	prpTmainSchema.setSumAmount(String.valueOf(bankInterFace_DetailDto.getSumamount()));
        }
        
        prpTmainSchema.setSumDiscount("0");
        prpTmainSchema.setSumPremium(String.valueOf(bankInterFace_DetailDto.getSumPremium()));
        prpTmainSchema.setSumSubPrem("0");
        prpTmainSchema.setAutoTransRenewFlag("2");
        prpTmainSchema.setPayTimes("1");
        prpTmainSchema.setEndorseTimes("0");
        prpTmainSchema.setClaimTimes("0");
        prpTmainSchema.setMakeCom(bankInterFace_DetailDto.getMakeCom());
        prpTmainSchema.setOperateSite("XBT");
        prpTmainSchema.setComCode(bankInterFace_DetailDto.getComCode());
        prpTmainSchema.setHandlerCode(bankInterFace_DetailDto.getHandlerCode());
        prpTmainSchema.setHandler1Code(bankInterFace_DetailDto.getHandler1Code()); 
        prpTmainSchema.setOperatorCode(bankInterFace_DetailDto.getHandlerCode());
        
        prpTmainSchema.setInputDate(strUploadDate); 
        prpTmainSchema.setInputHour("12");
        prpTmainSchema.setAgentCode(strAgentCode);
        prpTmainSchema.setCoinsFlag("0");
        prpTmainSchema.setReinsFlag("0");
        prpTmainSchema.setAllinsFlag("2");
        prpTmainSchema.setUnderWriteFlag("0");
        prpTmainSchema.setOthFlag("000000YY000000000001");
        prpTmainSchema.setFlag("  1");
        prpTmainSchema.setDisRate1("0");
        prpTmainSchema.setBusinessFlag("0");
        prpTmainSchema.setAgreementNo(strAgreementNo);
        prpTmainSchema.setShareHolderFlag("0");
        prpTmainSchema.setArgueSolution("1");
        prpTmainSchema.setAppliType("1");
        
        prpTmainSchema.setVisaCode(iVisaCode);
        prpTmainSchema.setUnderWriteCode("99999999");
        prpTmainSchema.setUnderWriteName("自动核XXXXX");
        prpTmainSchema.setManualType("1");
        prpTmainSchema.setPolicyNo(bankInterFace_DetailDto.getPolicyno());
        prpTmainSchema.setProductCode(bankInterFace_DetailDto.getProductCode());
        prpTmainSchema.setPrintNo(bankInterFace_DetailDto.getPrintno());
        
        prpTmainSchema.setUnderWriteEndDate(strCurrentDate);
        prpTmainSchema.setUnderWriteFlag("4");
        
        iResult = dbPrpDcompany.getInfo(bankInterFace_DetailDto.getComCode());
        if(iResult == 0)
        {
          strChannelType = dbPrpDcompany.getChannelType();
        }
		prpTmainSchema.setChannelType(strChannelType);
		
        
        
        if("N01".equals(strChannelType) && "5".equals(dbPrpDcompany.getComLevel())){
            String strChannelTypesubSwitch = "CHANNELTYPESUB_SWTICH_N01";
            PrpDriskConfigDto prpDriskConfigDto = new PrpDriskConfigDto();
            UIPrpDriskConfigAction uiPrpDriskConfigAction = new UIPrpDriskConfigAction();
            prpDriskConfigDto=uiPrpDriskConfigAction.queryRiskConfig(bankInterFace_DetailDto.getComCode(),"0000",strChannelTypesubSwitch);
            if(prpDriskConfigDto!=null && prpDriskConfigDto.getConfigValue().indexOf("S13")>-1){
                prpTmainSchema.setChannelTypeSub("S13");
            }else{
	            throw new UserException(-98, -1167, "BLGenerateACCData.generateObjectOfTmain","渠道取值失败,XX归属渠道为N01,子渠道不存在S13!" ); 
	        }
        }else{
	        if(!"N03".equals(strChannelType)){
                throw new UserException(-98, -1167, "BLGenerateACCData.generateObjectOfTmain","渠道取值失败,渠道只能N01、N03!" ); 
	        }
        }
        
		
        
		



	    	

















	    
        
        prpTmainSchema.setSumQuantity("1");
        
        blPrpTmain.setArr(prpTmainSchema);
      
        return blPrpTmain;
    }

    /**
     * @desc 信XXXXX通由接口表数据生成XX数据：生成XX单XX/被XX人信息对象BLPrpTinsured
     * @param bankInterFace_DetailDto
     * @return blPrpTinsured
     * @throws Exception
     */
    public BLPrpTinsured generateObjectOfTinsured(BankInterFace_DetailDto bankInterFace_DetailDto) throws Exception {
    	BLPrpDcustomerIdvNew blPrpDcustomerIdvNew = new BLPrpDcustomerIdvNew();
        BLPrpTinsured blPrpTinsured = new BLPrpTinsured();
        PrpTinsuredSchema prpTinsuredSchema1 = new PrpTinsuredSchema();
        PrpTinsuredSchema prpTinsuredSchema2 = new PrpTinsuredSchema();
        PrpTinsuredSchema prpTinsuredSchema3 = new PrpTinsuredSchema();
        PrpTinsuredSchema prpTinsuredSchema4 = new PrpTinsuredSchema();

        prpTinsuredSchema1.setProposalNo(bankInterFace_DetailDto.getProposalNo());
        prpTinsuredSchema1.setRiskCode(bankInterFace_DetailDto.getRiskCode());
        prpTinsuredSchema1.setSerialNo("1");
        prpTinsuredSchema1.setLanguage("C");
        prpTinsuredSchema1.setInsuredType("1");
        prpTinsuredSchema1.setInsuredName(bankInterFace_DetailDto.getAppliName());
        prpTinsuredSchema1.setInsuredAddress(bankInterFace_DetailDto.getAddress());
        prpTinsuredSchema1.setInsuredNature("3");
        prpTinsuredSchema1.setInsuredFlag("2");
        







        prpTinsuredSchema1.setIdentifyType(this.getIdentifyType(bankInterFace_DetailDto.getComCode(),bankInterFace_DetailDto.getIdType()));
        
        prpTinsuredSchema1.setIdentifyNumber(bankInterFace_DetailDto.getAppidNo());
        prpTinsuredSchema1.setBank(bankInterFace_DetailDto.getBfbankCode());
        prpTinsuredSchema1.setAccount(bankInterFace_DetailDto.getBfaccountNo());
        prpTinsuredSchema1.setPostAddress(bankInterFace_DetailDto.getAddress());
        prpTinsuredSchema1.setPostCode(bankInterFace_DetailDto.getPostCode());
        
        prpTinsuredSchema1.setPhoneNumber(bankInterFace_DetailDto.getPhone());
        prpTinsuredSchema1.setMobile(bankInterFace_DetailDto.getMobileCode());
        prpTinsuredSchema1.setEmail(bankInterFace_DetailDto.getEmail());
        prpTinsuredSchema1.setInsuredIdentity("01");
        arrCustomerCode[0] = blPrpDcustomerIdvNew.createCustomer(prpTinsuredSchema1, bankInterFace_DetailDto.getComCode(), 
        		bankInterFace_DetailDto.getHandlerCode(), bankInterFace_DetailDto.getHandler1Code());
        prpTinsuredSchema1.setInsuredCode(arrCustomerCode[0]);
        blPrpTinsured.setArr(prpTinsuredSchema1);

        prpTinsuredSchema2.setProposalNo(bankInterFace_DetailDto.getProposalNo());
        prpTinsuredSchema2.setRiskCode(bankInterFace_DetailDto.getRiskCode());
        prpTinsuredSchema2.setSerialNo("2");
        prpTinsuredSchema2.setLanguage("C");
        prpTinsuredSchema2.setInsuredType("1");
        prpTinsuredSchema2.setInsuredName(bankInterFace_DetailDto.getInsuredName());
        prpTinsuredSchema2.setInsuredAddress(bankInterFace_DetailDto.getInsuredAdress());
        prpTinsuredSchema2.setInsuredNature("3");
        prpTinsuredSchema2.setInsuredFlag("1");
        







        prpTinsuredSchema2.setIdentifyType(this.getIdentifyType(bankInterFace_DetailDto.getComCode(),bankInterFace_DetailDto.getInsuredType()));
        
        prpTinsuredSchema2.setIdentifyNumber(bankInterFace_DetailDto.getInsuredNumber());
        prpTinsuredSchema2.setBank(bankInterFace_DetailDto.getBfbankCode());
        prpTinsuredSchema2.setAccount(bankInterFace_DetailDto.getBfaccountNo());
        prpTinsuredSchema2.setPostAddress(bankInterFace_DetailDto.getInsuredAdress());
        prpTinsuredSchema2.setPostCode(bankInterFace_DetailDto.getInsurepostCode());
        prpTinsuredSchema2.setPhoneNumber(bankInterFace_DetailDto.getInsuredPhone());
        
        prpTinsuredSchema2.setMobile(bankInterFace_DetailDto.getMobileCode());
        prpTinsuredSchema2.setInsuredIdentity(bankInterFace_DetailDto.getPolicyType());
        prpTinsuredSchema2.setOccupationCode(bankInterFace_DetailDto.getOccupationCode());
        arrCustomerCode[1] = blPrpDcustomerIdvNew.createCustomer(prpTinsuredSchema1, bankInterFace_DetailDto.getComCode(), 
        		bankInterFace_DetailDto.getHandlerCode(), bankInterFace_DetailDto.getHandler1Code());
        prpTinsuredSchema2.setInsuredCode(arrCustomerCode[1]);
        blPrpTinsured.setArr(prpTinsuredSchema2);
        int intBenefitCount = 0;
        if(bankInterFace_DetailDto.getBenefitCount() != null && !"".equals(bankInterFace_DetailDto.getBenefitCount().trim()))
        {
        	intBenefitCount = Integer.parseInt(bankInterFace_DetailDto.getBenefitCount());
        }
        if(intBenefitCount > 0)
        {
        	prpTinsuredSchema3.setProposalNo(bankInterFace_DetailDto.getProposalNo());
        	prpTinsuredSchema3.setRiskCode(bankInterFace_DetailDto.getRiskCode());
        	prpTinsuredSchema3.setSerialNo("3");
        	prpTinsuredSchema3.setLanguage("C");
        	prpTinsuredSchema3.setInsuredType("1");
        	prpTinsuredSchema3.setInsuredName(bankInterFace_DetailDto.getBenefitName1());
        	prpTinsuredSchema3.setInsuredAddress(bankInterFace_DetailDto.getBenePostAddress1());
        	prpTinsuredSchema3.setInsuredNature("3");
        	prpTinsuredSchema3.setInsuredFlag("9");
        	







        	prpTinsuredSchema3.setIdentifyType(this.getIdentifyType(bankInterFace_DetailDto.getComCode(),bankInterFace_DetailDto.getBenefitIdType1()));
        	
        	prpTinsuredSchema3.setIdentifyNumber(bankInterFace_DetailDto.getBenefitIdNo1());
        	prpTinsuredSchema3.setBank(bankInterFace_DetailDto.getBfbankCode());
        	prpTinsuredSchema3.setAccount(bankInterFace_DetailDto.getBfaccountNo());
        	prpTinsuredSchema3.setPostAddress(bankInterFace_DetailDto.getBenePostAddress1());
        	prpTinsuredSchema3.setPostCode(bankInterFace_DetailDto.getBenePostCode1());
        	prpTinsuredSchema3.setInsuredIdentity(bankInterFace_DetailDto.getBenefitIdentity1());
        	prpTinsuredSchema3.setBenefitFlag(bankInterFace_DetailDto.getBenefitFlag1());
        	prpTinsuredSchema3.setBenefitRate(Double.toString(bankInterFace_DetailDto.getBenefitRate1()));
            
        	prpTinsuredSchema3.setInsuredCode(blPrpDcustomerIdvNew.createCustomer(prpTinsuredSchema3, bankInterFace_DetailDto.getComCode(), 
            		bankInterFace_DetailDto.getHandlerCode(), bankInterFace_DetailDto.getHandler1Code()));
            blPrpTinsured.setArr(prpTinsuredSchema3);
            if(intBenefitCount == 2)
            {
            	prpTinsuredSchema4.setProposalNo(bankInterFace_DetailDto.getProposalNo());
                prpTinsuredSchema4.setRiskCode(bankInterFace_DetailDto.getRiskCode());
                prpTinsuredSchema4.setSerialNo("4");
                prpTinsuredSchema4.setLanguage("C");
                prpTinsuredSchema4.setInsuredType("1");
                prpTinsuredSchema4.setInsuredName(bankInterFace_DetailDto.getBenefitName2());
                prpTinsuredSchema4.setInsuredAddress(bankInterFace_DetailDto.getBenePostAddress2());
                prpTinsuredSchema4.setInsuredNature("3");
                prpTinsuredSchema4.setInsuredFlag("9");
                







                prpTinsuredSchema4.setIdentifyType(this.getIdentifyType(bankInterFace_DetailDto.getBankcode(),bankInterFace_DetailDto.getBenefitIdType2()));
                
                prpTinsuredSchema4.setIdentifyNumber(bankInterFace_DetailDto.getBenefitIdNo2());
                prpTinsuredSchema4.setBank(bankInterFace_DetailDto.getBfbankCode());
                prpTinsuredSchema4.setAccount(bankInterFace_DetailDto.getBfaccountNo());
                prpTinsuredSchema4.setPostAddress(bankInterFace_DetailDto.getBenePostAddress2());
                prpTinsuredSchema4.setPostCode(bankInterFace_DetailDto.getBenePostCode2());
                prpTinsuredSchema4.setInsuredIdentity(bankInterFace_DetailDto.getBenefitIdentity2());
                prpTinsuredSchema4.setBenefitFlag(bankInterFace_DetailDto.getBenefitFlag2());
                prpTinsuredSchema4.setBenefitRate(Double.toString(bankInterFace_DetailDto.getBenefitRate2()));
                
                prpTinsuredSchema4.setInsuredCode(blPrpDcustomerIdvNew.createCustomer(prpTinsuredSchema4, bankInterFace_DetailDto.getComCode(), 
                		bankInterFace_DetailDto.getHandlerCode(), bankInterFace_DetailDto.getHandler1Code()));
                blPrpTinsured.setArr(prpTinsuredSchema4);
            }
        }

        return blPrpTinsured;
    }

    /**
     * @desc 信XXXXX通由接口表数据生成XX数据：生成XX单地址信息对象BLPrpTaddress
     * @param bankInterFace_DetailDto
     * @return blPrpTaddress
     * @throws Exception
     */
    public BLPrpTaddress generateObjectOfTaddress(BankInterFace_DetailDto bankInterFace_DetailDto) throws Exception {
        BLPrpTaddress blPrpTaddress = new BLPrpTaddress();
        PrpTaddressSchema prpTaddressSchema = new PrpTaddressSchema();

        prpTaddressSchema.setProposalNo(bankInterFace_DetailDto.getProposalNo());
        prpTaddressSchema.setRiskCode(bankInterFace_DetailDto.getRiskCode());
        prpTaddressSchema.setAddressNo("1");
        prpTaddressSchema.setAddressCode(bankInterFace_DetailDto.getPostCode());
        prpTaddressSchema.setAddressName(bankInterFace_DetailDto.getEstateAddress());
        prpTaddressSchema.setProjectName(bankInterFace_DetailDto.getUseFor()); 
        blPrpTaddress.setArr(prpTaddressSchema);

        return blPrpTaddress;
    }

    /**
     * @desc 信XXXXX通由接口表数据生成XX数据：生成XX单XXXXX别信息对象BLPrpTitemKind
     * @param bankInterFace_DetailDto
     * @return blPrpTitemKind
     * @throws Exception
     */
    public BLPrpTitemKind generateObjectOfTitemKind(BankInterFace_DetailDto bankInterFace_DetailDto) throws Exception {
        BLPrpTitemKind blPrpTitemKind = new BLPrpTitemKind();;
        PrpTitemKindSchema prpTitemKindSchema = null;
        
        BLPrpDration blPrpDration = new BLPrpDration();
        BLPrpDkindFacade blPrpDkindFacade = new BLPrpDkindFacade();
        PrpDkindDto prpDkindDto = null;
        BLPrpDitemFacade blPrpDitemFacade = new BLPrpDitemFacade();
        PrpDitemDto prpDitemDto = null;
        PubTools pubTools = new PubTools();

        String strStartDate = "";
        String strEndDate = "";
        String strComCode = bankInterFace_DetailDto.getComCode();
        
        ArrayList arrRate = new ArrayList();
        
        double dblShortRate = 0D;
        int intMonthCount = 0;
        int intDutyDays = 0;
        double dblRateSum = 0D;

        int intYear = 0;
        int intMonth = 0;
        int intDay = 0;
        
        
        double rate = 0.00;             
        

        intYear = bankInterFace_DetailDto.getStartDate().getYear();
        intMonth = bankInterFace_DetailDto.getStartDate().getMonth();
        intDay = bankInterFace_DetailDto.getStartDate().getDay();
        strStartDate = new Integer(intYear).toString() + "-"
                     + new Integer(intMonth).toString() + "-"
                     + new Integer(intDay).toString();

        intYear = bankInterFace_DetailDto.getEndDate().getYear();
        intMonth = bankInterFace_DetailDto.getEndDate().getMonth();
        intDay = bankInterFace_DetailDto.getEndDate().getDay();
        strEndDate = new Integer(intYear).toString() + "-"
                     + new Integer(intMonth).toString() + "-"
                     + new Integer(intDay).toString();

        if(strComCode != null && "06".equals(strComCode.substring(0,2)))
        {
        	intDutyDays = pubTools.getDayMinus(new Date(strStartDate), 0, new Date(strEndDate), 24);
            if(intDutyDays == 30)
            {
            	intMonthCount = 1;
            }else
            {
            	intMonthCount = intDutyDays/30;
            }
            dblShortRate = intMonthCount / 12 * 100;
        }else
        {
        	dblShortRate = pubTools.getShortRate(bankInterFace_DetailDto.getRiskCode(), new Date(strStartDate), 0,
    				new Date(strEndDate), 24, "3");
        }
        
        StringBuffer strRateSQL = new StringBuffer();
        strRateSQL.append("RiskCode='");
        strRateSQL.append(bankInterFace_DetailDto.getRiskCode());
        strRateSQL.append("' AND RationType='");
        strRateSQL.append(bankInterFace_DetailDto.getProductCode());
        strRateSQL.append("'");
        blPrpDration.query(strRateSQL.toString());
        
        
        double kindMainAmount = 0; 
        if(bankInterFace_DetailDto.getProductCode()!=null  && "P35022".equals(bankInterFace_DetailDto.getProductCode()) ){
        	kindMainAmount = Double.valueOf(bankInterFace_DetailDto.getSumamount());
        	if(kindMainAmount <= 200000){
        		kindMainAmount = PubTools.round(kindMainAmount/2,2);
        	}if(kindMainAmount > 200000){
        		kindMainAmount = PubTools.round(kindMainAmount-100000,2);
        	}
        }
       
        
        if(blPrpDration.getSize() > 0)
        {
        	for(int i=0;i<blPrpDration.getSize();i++)
        	{
        		prpTitemKindSchema = new PrpTitemKindSchema();
        		prpDkindDto = blPrpDkindFacade.findByPrimaryKey(bankInterFace_DetailDto.getRiskCode(), blPrpDration.getArr(i).getKindCode());
        		prpDitemDto = blPrpDitemFacade.findByPrimaryKey(bankInterFace_DetailDto.getRiskCode(), blPrpDration.getArr(i).getItemCode());
        		
        		rate = PubTools.round(Double.parseDouble(blPrpDration.getArr(i).getRate()) * 1000, 4);
        		
        		prpTitemKindSchema.setProposalNo(bankInterFace_DetailDto.getProposalNo());
                prpTitemKindSchema.setRiskCode(bankInterFace_DetailDto.getRiskCode());
                prpTitemKindSchema.setItemKindNo(Integer.toString(i+1));
                prpTitemKindSchema.setFamilyNo("2");
                prpTitemKindSchema.setKindCode(blPrpDration.getArr(i).getKindCode());
                prpTitemKindSchema.setKindName(prpDkindDto.getKindCName());
                prpTitemKindSchema.setItemCode(blPrpDration.getArr(i).getItemCode());
                prpTitemKindSchema.setItemDetailName(prpDitemDto.getItemCName());
                prpTitemKindSchema.setStartDate(strStartDate);
                prpTitemKindSchema.setStartHour("0");
                prpTitemKindSchema.setEndDate(strEndDate);
                prpTitemKindSchema.setEndHour("24");
                prpTitemKindSchema.setAddressNo("1");
                if("0050".equals(blPrpDration.getArr(i).getItemCode()))
                {
                	prpTitemKindSchema.setCalculateFlag("Y");
                }

                else if("0076".equals(blPrpDration.getArr(i).getItemCode())){
                	if("P35013".equals(bankInterFace_DetailDto.getProductCode()) || "P35014".equals(bankInterFace_DetailDto.getProductCode())){
                		prpTitemKindSchema.setCalculateFlag("Y");
                	}else{
                		prpTitemKindSchema.setCalculateFlag("N");
                	}
                }

                else
                {
                	prpTitemKindSchema.setCalculateFlag("N");
                }
                prpTitemKindSchema.setCurrency("CNY");
                prpTitemKindSchema.setUnitAmount(String.valueOf(bankInterFace_DetailDto.getSumamount())); 
                prpTitemKindSchema.setAmount(String.valueOf(bankInterFace_DetailDto.getSumamount()));
                

                prpTitemKindSchema.setRate("" + rate); 
                
                prpTitemKindSchema.setShortRateFlag("4"); 
                prpTitemKindSchema.setShortRate(String.valueOf(PubTools.round(dblShortRate,4)));
                prpTitemKindSchema.setFlag(" 1");
                prpTitemKindSchema.setValue(String.valueOf(bankInterFace_DetailDto.getInvestCount()));
                
                prpTitemKindSchema.setQuantity("1");
                
                prpTitemKindSchema.setDiscount("100.00");
                arrRate.add(blPrpDration.getArr(i).getRate());
                dblRateSum = dblRateSum + Double.parseDouble(blPrpDration.getArr(i).getRate());
                
                
                
                
                
                
               if(bankInterFace_DetailDto.getProductCode()!=null && "P35022".equals(bankInterFace_DetailDto.getProductCode())){
            	   
            	   double[] arrShortRateTable = new double[] {10,20,30,40,50,60,70,80,85,90,95,100};
	    	         int  intMonths = bankInterFace_DetailDto.getInsureTerm();
	    	         
	    	         int year = intMonths/12;
	    	         int arr = intMonths%12;
	    	         double shortRate ;
	    	         if(arr==0){
	    	    	     shortRate=(year-1)*100+100;
	    	         }
	    	         else{
	    	             shortRate= year*100+arrShortRateTable[arr-1];
	    	         }


                   if("210".equals(blPrpDration.getArr(i).getKindCode())){ 
                		prpTitemKindSchema.setCalculateFlag("Y");
                		prpTitemKindSchema.setAmount(String.valueOf(kindMainAmount));
                		prpTitemKindSchema.setPremium((String.valueOf(PubTools.round(Double.valueOf(prpTitemKindSchema.getAmount())*Double.valueOf(blPrpDration.getArr(i).getRate())*(shortRate/100),2))));
                	}else if("158".equals(blPrpDration.getArr(i).getKindCode())){
                		prpTitemKindSchema.setCalculateFlag("N");
                		if( kindMainAmount >= 100000){
                			prpTitemKindSchema.setAmount("100000");
                		}else{
                			prpTitemKindSchema.setAmount(String.valueOf(kindMainAmount));
                		}
                		prpTitemKindSchema.setPremium((String.valueOf(PubTools.round(Double.valueOf(prpTitemKindSchema.getAmount())*Double.valueOf(blPrpDration.getArr(i).getRate())*(shortRate/100),2))));
                	}
                   prpTitemKindSchema.setUnitAmount(prpTitemKindSchema.getAmount());
                }
               
                                
                blPrpTitemKind.setArr(prpTitemKindSchema);
        	}
        	
        	boolean blProductA = false;
        	double dbAmoumt = 0.0D;
        	if("210".equals(blPrpTitemKind.getArr(0).getKindCode()) || "101".equals(blPrpTitemKind.getArr(0).getKindCode()))
        	{
        		blProductA = true;
        	}
        	
        	
        	if(blPrpTitemKind.getSize() > 1 && bankInterFace_DetailDto.getProductCode()!=null && !"P35022".equals(bankInterFace_DetailDto.getProductCode())) 
            {
          
        		double sumPremium = 0D;
        		
        		double dbTempAmount = 0.0D;
        		
            	for(int i=0;i<arrRate.size()-1;i++)
            	{
            		blPrpTitemKind.getArr(i).setPremium(String.valueOf(PubTools
							.round(bankInterFace_DetailDto.getSumPremium()*(Double.parseDouble((arrRate.get(i)).toString()))/dblRateSum,2)));
            		sumPremium = sumPremium + PubTools.round(bankInterFace_DetailDto.getSumPremium()*(Double.parseDouble((arrRate.get(i)).toString()))/dblRateSum,2);
            		
            		if(blProductA)
            		{
            			dbAmoumt = PubTools.round(bankInterFace_DetailDto.getSumamount()*(Double.parseDouble((arrRate.get(i)).toString()))/dblRateSum,2);
            			blPrpTitemKind.getArr(i).setAmount(String.valueOf(dbAmoumt));
            			dbTempAmount = dbTempAmount + dbAmoumt;
            			blPrpTitemKind.getArr(i).setCalculateFlag("Y");
            			
            			blPrpTitemKind.getArr(i).setUnitAmount(String.valueOf(dbAmoumt));
            		}
            		
            	}
            	blPrpTitemKind.getArr(blPrpTitemKind.getSize()-1).setPremium(String.valueOf(bankInterFace_DetailDto.getSumPremium() - sumPremium));
            	
            	if(blProductA)
        		{
            		blPrpTitemKind.getArr(blPrpTitemKind.getSize()-1).setAmount(String.valueOf(bankInterFace_DetailDto.getSumamount() - dbTempAmount));
                	blPrpTitemKind.getArr(blPrpTitemKind.getSize()-1).setCalculateFlag("Y");
                	blPrpTitemKind.getArr(blPrpTitemKind.getSize()-1).setUnitAmount(blPrpTitemKind.getArr(blPrpTitemKind.getSize()-1).getAmount());
        		}
            	
            	

        		if("P35013".equals(bankInterFace_DetailDto.getProductCode()) || "P35014".equals(bankInterFace_DetailDto.getProductCode())){
        			for(int j = 0; j<blPrpTitemKind.getSize();j++){
        				double dblShortRateMonth = pubTools.getShortRate(bankInterFace_DetailDto.getRiskCode(), new Date(strStartDate), 0,
                				new Date(strEndDate), 24, "4")/100;
            			if(bankInterFace_DetailDto.getSumamount()<= 200000){ 
            				double amount1 = bankInterFace_DetailDto.getSumamount()/2 ;
            				double premium1 = PubTools.round(amount1*dblShortRateMonth*(Double.parseDouble((arrRate.get(j)).toString())), 2);
            				blPrpTitemKind.getArr(j).setAmount(String.valueOf(amount1));
            				blPrpTitemKind.getArr(j).setPremium(String.valueOf(premium1));
            				blPrpTitemKind.getArr(j).setUnitAmount(String.valueOf(amount1));
            			}else if(bankInterFace_DetailDto.getSumamount()> 200000){ 
            				if("0050".equals(blPrpTitemKind.getArr(j).getItemCode())){
            					double amount0050 = bankInterFace_DetailDto.getSumamount()- 100000 ;
            					double premium0050 = PubTools.round(amount0050*dblShortRateMonth*(Double.parseDouble((arrRate.get(j)).toString())), 2);
            					blPrpTitemKind.getArr(j).setAmount(String.valueOf(amount0050)); 
            					blPrpTitemKind.getArr(j).setPremium(String.valueOf(premium0050));
            					blPrpTitemKind.getArr(j).setUnitAmount(String.valueOf(amount0050)); 
            				}else if("0076".equals(blPrpTitemKind.getArr(j).getItemCode())){
            					double amount0076 = 100000 ;
            					double premium0076 = PubTools.round(amount0076*dblShortRateMonth*(Double.parseDouble((arrRate.get(j)).toString())), 2);
            					blPrpTitemKind.getArr(j).setAmount(String.valueOf(amount0076)); 
            					blPrpTitemKind.getArr(j).setPremium(String.valueOf(premium0076));
            					blPrpTitemKind.getArr(j).setUnitAmount(String.valueOf(amount0076));
            				}
            			}      			
        			}
        		}

            }else if(blPrpTitemKind.getSize() == 1)
            {
            	blPrpTitemKind.getArr(0).setPremium(String.valueOf(bankInterFace_DetailDto.getSumPremium()));
            	
            	if(blProductA)
            	{
            		blPrpTitemKind.getArr(0).setCalculateFlag("Y");
            	}
            	
            }
        }else
        {
        	errorMessage="产品条款及费率未进行配置！" ;
        	throw new Exception("产品条款及费率未进行配置！");
        }

        return blPrpTitemKind ;
    }

    /**
     * @desc 信XXXXX通由接口表数据生成XX数据：生成XX单金额信息对象BLPrpTfee
     * @param bankInterFace_DetailDto
     * @param prpDbankInvestDto
     * @return blPrpTfee
     * @throws Exception
     */
    public BLPrpTfee generateObjectOfTfee(BankInterFace_DetailDto bankInterFace_DetailDto) throws Exception {
        BLPrpTfee blPrpTfee = new BLPrpTfee();
        PrpTfeeSchema prpTfeeSchema = new PrpTfeeSchema();

        prpTfeeSchema.setProposalNo(bankInterFace_DetailDto.getProposalNo());
        prpTfeeSchema.setRiskCode(bankInterFace_DetailDto.getRiskCode());
        prpTfeeSchema.setCurrency(bankInterFace_DetailDto.getCurrency());
        prpTfeeSchema.setAmount(String.valueOf(bankInterFace_DetailDto.getSumamount()));
        prpTfeeSchema.setPremium(String.valueOf(bankInterFace_DetailDto.getSumPremium()));
        prpTfeeSchema.setCurrency1(bankInterFace_DetailDto.getCurrency());
        prpTfeeSchema.setExchangeRate1("1");
        prpTfeeSchema.setAmount1(String.valueOf(bankInterFace_DetailDto.getSumamount()));
        prpTfeeSchema.setPremium1(String.valueOf(bankInterFace_DetailDto.getSumPremium()));
        prpTfeeSchema.setCurrency2(bankInterFace_DetailDto.getCurrency());
        prpTfeeSchema.setExchangeRate2("1");
        prpTfeeSchema.setAmount2(String.valueOf(bankInterFace_DetailDto.getSumamount()));
        prpTfeeSchema.setPremium2(String.valueOf(bankInterFace_DetailDto.getSumPremium()));
        
        
        double kindMainAmount = 0; 
       if(bankInterFace_DetailDto.getProductCode()!=null  && "P35022".equals(bankInterFace_DetailDto.getProductCode()) ){
	       	kindMainAmount = Double.valueOf(bankInterFace_DetailDto.getSumamount());
	       	if(kindMainAmount <= 200000){
	       		kindMainAmount = PubTools.round(kindMainAmount/2,2);
	       	}if(kindMainAmount > 200000){
	       		kindMainAmount = PubTools.round(kindMainAmount-100000,2);
	       	}
	        prpTfeeSchema.setAmount(String.valueOf(kindMainAmount));
	        prpTfeeSchema.setAmount1(String.valueOf(kindMainAmount));
	        prpTfeeSchema.setPremium2(String.valueOf(kindMainAmount));
	        
       }
      
        
        blPrpTfee.setArr(prpTfeeSchema);

        return blPrpTfee;
    }

    /**
     * @desc 信XXXXX通由接口表数据生成XX数据：生成XX单缴费计划信息对象BLPrpTplan
     * @param bankInterFace_DetailDto
     * @return blPrpTplan
     * @throws Exception
     */
    public BLPrpTplan generateObjectOfTplanNew(BankInterFace_DetailDto bankInterFace_DetailDto) throws Exception {
        BLPrpTplan blPrpTplan = new BLPrpTplan();
        PrpTplanSchema prpTplanSchema = new PrpTplanSchema();
        prpTplanSchema.setProposalNo(bankInterFace_DetailDto.getProposalNo());
        prpTplanSchema.setSerialNo("1");
        prpTplanSchema.setPayNo("1");
        prpTplanSchema.setPayReason("R10");
        prpTplanSchema.setPlanDate(bankInterFace_DetailDto.getInvestDate().toString());
        prpTplanSchema.setCurrency(bankInterFace_DetailDto.getCurrency());
        prpTplanSchema.setPlanFee(""+(bankInterFace_DetailDto.getSumPremium()));
        prpTplanSchema.setDelinquentFee(""+(bankInterFace_DetailDto.getSumPremium()));
        prpTplanSchema.setPlanStartDate(bankInterFace_DetailDto.getInvestDate().toString());
        blPrpTplan.setArr(prpTplanSchema);

        return blPrpTplan;
    }
    /**
     * @desc 信XXXXX通由接口表数据生成XX数据：生成XX单缴费计划信息对象BLPrpTplan
     * @param bankInterFace_DetailDto
     * @return blPrpTplan
     * @throws Exception
     */
    public BLPrpTplan generateObjectOfTplan(BankInterFace_DetailDto bankInterFace_DetailDto) throws Exception {
        BLPrpTplan blPrpTplan = new BLPrpTplan();
        PrpTplanSchema prpTplanSchema = new PrpTplanSchema();
        prpTplanSchema.setProposalNo(bankInterFace_DetailDto.getProposalNo());
        prpTplanSchema.setSerialNo("1");
        prpTplanSchema.setPayNo("1");
        prpTplanSchema.setPayReason("R10");
        prpTplanSchema.setPlanDate(bankInterFace_DetailDto.getInvestDate().toString());
        prpTplanSchema.setCurrency(bankInterFace_DetailDto.getCurrency());
        prpTplanSchema.setPlanFee(new Integer(bankInterFace_DetailDto.getInvestCount() * 6).toString());
        prpTplanSchema.setDelinquentFee(new Integer(bankInterFace_DetailDto.getInvestCount() *6).toString());
        prpTplanSchema.setPlanStartDate(bankInterFace_DetailDto.getInvestDate().toString());
        blPrpTplan.setArr(prpTplanSchema);

        return blPrpTplan;
    }
    /**
	 * 生成mainCasualty表信息 
	 * @param BankInterFace_DetailDto 中间表对象
	 * @return BLPrpTmainCasualty
	 */ 
	public BLPrpTmainCasualty generateObjectOfTmainCasualty(BankInterFace_DetailDto bankInterFace_DetailDto)
			throws Exception {
		BLPrpTmainCasualty blPrpTmainCasualty = new BLPrpTmainCasualty();
		
		PrpTmainCasualtySchema prpTmainCasualtySchema = new PrpTmainCasualtySchema();
		prpTmainCasualtySchema.setProposalNo(bankInterFace_DetailDto.getProposalNo());
		prpTmainCasualtySchema.setRiskCode(bankInterFace_DetailDto.getRiskCode());
		prpTmainCasualtySchema.setBusinessGrade("1");
		blPrpTmainCasualty.setArr(prpTmainCasualtySchema);
		
		return blPrpTmainCasualty;
	}
	/**
	 * 生成费用信息表
	 * @param bankInterFace_DetailDto 中间表对象
	 * @return blPrpTexpense
	 */
	public BLPrpTexpense generateObjectOfTexpense(DBManager dbManager,BankInterFace_DetailDto bankInterFace_DetailDto)
			throws Exception {
		
		BLPrpTexpense blPrpTexpense = new BLPrpTexpense();
		String manageFeeRate ="";
		String salesSalaryRate ="";
		String strAgentCode = "";
		String strAgreementNo ="";
		BLPrpdBankCompannyFacade blPrpdBankCompannyFacade = new BLPrpdBankCompannyFacade();
		BLPrpDagreementFacade blPrpDagreementFacade = new BLPrpDagreementFacade();
        PrpDagreementDto prpDagreementDto = null;
        Collection prpDagreementDtoList = null;
        
		String strBankCode = bankInterFace_DetailDto.getBankbranchCode();
        StringBuffer strQuery = new StringBuffer();
        strQuery.append(" BankCode='");
        strQuery.append(strBankCode);
        strQuery.append("'");
        Collection prpdBankCompannyDtoList = blPrpdBankCompannyFacade.findByConditions(strQuery.toString());
        if(prpdBankCompannyDtoList.size() == 0){
        	errorMessage="BLGenerateACCData.generateObjectOfTexpense"+"prpdbankcompanny表"+strBankCode+"取值失败,请管理员进行配置！"  ;
			throw new UserException(-98, -1167, "BLGenerateACCData.generateObjectOfTexpense",
			"prpdbankcompanny表"+strBankCode+"取值失败,请管理员进行配置！" ); }
        if(prpdBankCompannyDtoList.size() > 0) {
        	strAgentCode = ((PrpdBankCompannyDto)((ArrayList)prpdBankCompannyDtoList).get(0)).getAgentCode();
        }   
        prpDagreementDtoList = blPrpDagreementFacade.findByConditions("AgentCode='" + strAgentCode + "' and validstatus = '1'");
        for(Iterator prpDagreementDtoListTmp = prpDagreementDtoList.iterator(); prpDagreementDtoListTmp.hasNext();) {
            prpDagreementDto = (PrpDagreementDto)prpDagreementDtoListTmp.next();
            strAgreementNo = prpDagreementDto.getAgreementNo();
            break;
        }
		if("1".equals(Str.rightTrim(SysConfig.getProperty("CASUALTY_ACC_SWITCH")))){
        	BLPrpDoutsideAgreedetailAction blPrpDoutsideAgreedetailAction = new BLPrpDoutsideAgreedetailAction();
			PrpDoutsideAgreedetailDto prpDoutsideAgreedetailDto = null;
		    prpDoutsideAgreedetailDto = blPrpDoutsideAgreedetailAction.findByPrimaryKey(dbManager, strAgreementNo,bankInterFace_DetailDto.getProductCode());
		    if(prpDoutsideAgreedetailDto==null)
		    {
		    	errorMessage="BLGenerateACCData.generateObjectOfTexpense"+"手续费比例取值失败,请管理员进行配置！";
				throw new UserException(-98, -1167, "BLGenerateACCData.generateObjectOfTexpense",
				"手续费比例取值失败,请管理员进行配置！" ); 
		    }
			if((String.valueOf(prpDoutsideAgreedetailDto.getCommissionrate())).trim().equals("")
	           ||prpDoutsideAgreedetailDto.getCommissionrate()<0){
				errorMessage="BLGenerateACCData.generateObjectOfTexpense"+"手续费比例取值失败,请管理员进行配置！";
				throw new UserException(-98, -1167, "BLGenerateACCData.generateObjectOfTexpense",
						"手续费比例取值失败,请管理员进行配置！" ); 
	        }else
	        {
	        	manageFeeRate = prpDoutsideAgreedetailDto.getManagefeerate()+"";
	        	salesSalaryRate = prpDoutsideAgreedetailDto.getSalessalaryrate()+"";
	        }  
        }
		
		PrpTexpenseSchema prpTexpenseSchema = new PrpTexpenseSchema();
		prpTexpenseSchema.setProposalNo(bankInterFace_DetailDto.getProposalNo());
		prpTexpenseSchema.setRiskCode(bankInterFace_DetailDto.getRiskCode());
		prpTexpenseSchema.setManageFeeRate(manageFeeRate);
		prpTexpenseSchema.setSalesSalaryRate(salesSalaryRate);
		prpTexpenseSchema.setFlag("I1");
		blPrpTexpense.setArr(prpTexpenseSchema);
        
		return blPrpTexpense;
	}
	/**
	 * 获取系统内证件类型
	 * @param iIdentifyType
	 * @return
	 * @throws Exception
	 */
	public String getIdentifyType (String iIdentifyType) throws Exception {
		String strIdentifyType = "99";
		if(iIdentifyType != null && "1".equals(iIdentifyType))
		{
			strIdentifyType = "01";
		}
    	return strIdentifyType;
    }
	/**
	 * 获取系统内证件类型
	 * add by wangchuanzhong 20100304
	 * @param iComCode          业务归属机构
	 * @param iIdentifyType     系统外证件类型
	 * @return strIdentifyType  系统内证件类型
	 * @throws Exception
	 */
	public String getIdentifyType (String iComCode,String iIdentifyType) throws Exception {
		String strIdentifyType = "99";
		if(iIdentifyType != null && iComCode != null)
		{
			if("35".equals(iComCode.substring(0, 2)))
			{
				if("1".equals(iIdentifyType))
				{
					strIdentifyType = "01";
				}else
				{
					strIdentifyType = "99";
				}
			}else if("06".equals(iComCode.substring(0, 2)))
			{
				if("1".equals(iIdentifyType))
				{
					strIdentifyType = "01";
				}else if("2".equals(iIdentifyType))
				{
					strIdentifyType = "02";
				}else if("5".equals(iIdentifyType))
				{
					strIdentifyType = "03";
				}else
				{
					strIdentifyType = "99";
				}
			}else if("15".equals(iComCode.substring(0, 2)) || "21".equals(iComCode.substring(0, 2)) 
					|| "33".equals(iComCode.substring(0, 2)))
			{
				if("1".equals(iIdentifyType))
				{
					strIdentifyType = "01";
				}else if("3".equals(iIdentifyType))
				{
					strIdentifyType = "03";
				}else
				{
					strIdentifyType = "99";
				}
			}
			
			else if("02".equals(iComCode.substring(0, 2))){
				
				String[] idTypeArray = {"110001","110002","110003","110004","110005","110006","110007","110008","110009","110010","110011"
						,"110012","110013","110014","110015","110016","110017","110018","110019","110020","110021","110022","110023","110024"
						,"110025","110026","110027","110028","110029","110030","110031","110032","110033","110034","110035","110036","119998","119999"};
				List list = java.util.Arrays.asList(idTypeArray);
				
				if(list.contains(iIdentifyType)){
					if("110001".equals(iIdentifyType))
					{
						strIdentifyType = "01";
					}else if("110005".equals(iIdentifyType))
					{
						strIdentifyType = "02";
					}else if("110023".equals(iIdentifyType))
					{
						strIdentifyType = "03";
					}else if("110027".equals(iIdentifyType))
					{
						strIdentifyType = "04";
					}else
					{
						strIdentifyType = "99";
					}
				}else{
					strIdentifyType = iIdentifyType;
				}
			}
			
			else
			{
				strIdentifyType = iIdentifyType;
			}
		}
    	return strIdentifyType;
    }
	/**
     * @desc 生成XX单自然人信息表对象BLPrpTinsuredNature
     * @param bankInterFace_DetailDto
     * @param blPrpTinsured
     * @return BLPrpTinsuredNature
     * @throws Exception
     * @author wangchuanzhong 20100810
     */
    public BLPrpTinsuredNature generateObjectOfTinsuredNature(BankInterFace_DetailDto bankInterFace_DetailDto,
    		BLPrpTinsured blPrpTinsured) throws Exception {
    	BLPrpTinsuredNature blPrpTinsuredNature = new BLPrpTinsuredNature();
    	PrpTinsuredNatureSchema prpTinsuredNatureSchema = null;
    	PrpTinsuredSchema prpTinsuredSchema = null;
    	int intTinsuredCount = blPrpTinsured.getSize();
    	
    	if(intTinsuredCount > 0)
    	{
    		if("27".equals(bankInterFace_DetailDto.getClassCode()))
    		{
    			for(int i=0;i<intTinsuredCount;i++)
    			{
    				prpTinsuredSchema = blPrpTinsured.getArr(i);
    				prpTinsuredNatureSchema = new PrpTinsuredNatureSchema();
    				prpTinsuredNatureSchema.setProposalNo(prpTinsuredSchema.getProposalNo());
    				prpTinsuredNatureSchema.setSerialNo(prpTinsuredSchema.getSerialNo());
    				prpTinsuredNatureSchema.setInsuredFlag(prpTinsuredSchema.getInsuredFlag());
    				if("01".equals(prpTinsuredSchema.getIdentifyType()) && 
    						prpTinsuredSchema.getIdentifyNumber() != null)
    				{
    					prpTinsuredNatureSchema.setAge(Integer.toString(PubFuns.getAge(prpTinsuredSchema.getIdentifyNumber())));
        				prpTinsuredNatureSchema.setBirthday(PubFuns.getBirthday(prpTinsuredSchema.getIdentifyNumber()));
        				prpTinsuredNatureSchema.setSex(PubFuns.getSex(prpTinsuredSchema.getIdentifyNumber()));
    				}
    				blPrpTinsuredNature.setArr(prpTinsuredNatureSchema);
    			}
    		}
    	}
    	
    	return blPrpTinsuredNature;
    }

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
