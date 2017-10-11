package com.sp.prpall.blsvr.misc;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sp.casualtyloan.pubfun.PubFuns;
import com.sp.platform.bl.action.domain.BLBankInterFace_DetailAction;
import com.sp.platform.bl.action.domain.BLPrpDoutsideAgreedetailAction;
import com.sp.platform.bl.facade.BLPrpDagentFacade;
import com.sp.platform.bl.facade.BLPrpDagentFacadeBase;
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
import com.sp.platform.ui.control.action.UIPrpDriskConfigAction;
import com.sp.prpall.blsvr.tb.BLProposal;
import com.sp.prpall.blsvr.tb.BLPrpTaddress;
import com.sp.prpall.blsvr.tb.BLPrpTexpense;
import com.sp.prpall.blsvr.tb.BLPrpTfee;
import com.sp.prpall.blsvr.tb.BLPrpTinsured;
import com.sp.prpall.blsvr.tb.BLPrpTinsuredNature;
import com.sp.prpall.blsvr.tb.BLPrpTitemHouse;
import com.sp.prpall.blsvr.tb.BLPrpTitemKind;
import com.sp.prpall.blsvr.tb.BLPrpTmain;
import com.sp.prpall.blsvr.tb.BLPrpTmainBank;
import com.sp.prpall.blsvr.tb.BLPrpTmainCasualty;
import com.sp.prpall.blsvr.tb.BLPrpTmainLoan;
import com.sp.prpall.blsvr.tb.BLPrpTplan;
import com.sp.prpall.pubfun.CheckChannelYB;
import com.sp.prpall.pubfun.PubTools;
import com.sp.prpall.pubfun.eiespublictool.EiesPublicTool;
import com.sp.prpall.schema.BankInterface_ExtSchema;
import com.sp.prpall.schema.PrpTaddressSchema;
import com.sp.prpall.schema.PrpTexpenseSchema;
import com.sp.prpall.schema.PrpTfeeSchema;
import com.sp.prpall.schema.PrpTinsuredNatureSchema;
import com.sp.prpall.schema.PrpTinsuredSchema;
import com.sp.prpall.schema.PrpTitemHouseSchema;
import com.sp.prpall.schema.PrpTitemKindSchema;
import com.sp.prpall.schema.PrpTmainBankSchema;
import com.sp.prpall.schema.PrpTmainCasualtySchema;
import com.sp.prpall.schema.PrpTmainLoanSchema;
import com.sp.prpall.schema.PrpTmainSchema;
import com.sp.prpall.schema.PrpTplanSchema;
import com.sp.sysframework.common.datatype.DateTime;
import com.sp.sysframework.reference.DBManager;
import com.sp.utiall.blsvr.BLPrpDcustomerIdvNew;
import com.sp.utiall.blsvr.BLPrpDration;
import com.sp.utiall.dbsvr.DBPrpDcompany;
import com.sp.utiall.dbsvr.DBPrpDkind;
import com.sp.utility.SysConfig;
import com.sp.utility.UtiPower;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.utility.string.ChgDate;
import com.sp.utility.string.Date;
import com.sp.utility.string.Str;
import com.sp.sysframework.reference.AppConfig;

  public class BLTransDataYB0401 {

  String[] arrCustomerCode = new String[2];
 protected final Log logger = LogFactory.getLog(getClass());
  public BLTransDataYB0401() {
  }
 
  

  
  String jobName="中间业务平台转数失败日志记录";
  String comCode ="";
  String jobGroup ="";
  String jobDes="BLTransDataYB0401转数失败日志记录";
  String businessKey ="";
  String executeDate   ="";
  String successFlag ="";
  String errorMessage ="";
  String errorStack  ="";
  String createDate  ="";
  String createCode   ="";
  EiesPublicTool eiesPublicTool=new EiesPublicTool();
  int intYear = 0;
  int intMonth = 0;
  int intDay = 0;
  DateTime Date = new DateTime().current();

  double operateFee_PrpTexpense=0.0;
  double bankDisRate_PrpTexpense=0.0;

  
  
  /**银XXXXX通0401转数
   * @param dbPool
   * @param bankInterFace_DetailDto
   * @param isEiesFlag
   * @param vscode
   * @return
   * @throws Exception
   */
  public String transData( DbPool dbPool,BankInterFace_DetailDto bankInterFace_DetailDto,
        String isEiesFlag,String vscode)throws Exception
  {
    DBManager dbManager = null;
    dbManager = dbPool.getDBManager(SysConfig.CONST_DDCCDATASOURCE);
    BLBankInterFace_DetailAction blBankInterFace_DetailAction = new BLBankInterFace_DetailAction();
    String strReturn = "";

    String strRevolutionFlag = "0";

    
  	comCode = bankInterFace_DetailDto.getComCode();
  	jobGroup =bankInterFace_DetailDto.getBfbankCode();
  	if("402".equals(jobGroup)){
		
		
		jobGroup=eiesPublicTool.getExtEnterpCode(bankInterFace_DetailDto.getBankbranchCode());
	}
    businessKey =bankInterFace_DetailDto.getPolicyno();
    intYear = Date.getYear();
    intMonth = Date.getMonth();
    intDay = Date.getDay();
    successFlag ="0";
    errorStack  ="";
   createCode   ="ZJYW_0401_admin";
 
    try{     
      BLProposal blProposal = null;     
      String strProposalNo = "";
      if ("Eies".equals(isEiesFlag)) {
        vscode = bankInterFace_DetailDto.getVisacode();
      }
      
      strProposalNo = bankInterFace_DetailDto.getProposalNo();
      
      blProposal = generateObjectOfProposal(dbManager, bankInterFace_DetailDto,vscode);
      dbPool.beginTransaction();
      blProposal.save(dbPool, "I", false);
      com.sp.undwrt.bl.facade.BLTaskFacade blTaskFacade = new com.sp.undwrt.bl.facade.BLTaskFacade();
      
      blTaskFacade.start(dbManager,"11","T",strProposalNo,"0401",
                    "04",bankInterFace_DetailDto.getComCode(),bankInterFace_DetailDto.getComCode(),
                    bankInterFace_DetailDto.getHandlerCode(),bankInterFace_DetailDto.getHandlerCode(),bankInterFace_DetailDto.getHandler1Code(),""); 
      
      dbPool.commitTransaction(); 
      strRevolutionFlag = "1";
    }catch(Exception e) {
    	
    	if(errorMessage.equals("")){
    		errorMessage="双核系统自动核XXXXX不通过。请检查错误日志表失败堆栈信息！";
    	}
    	
    	
        logger.info(jobName);
        logger.debug(jobName);
        logger.debug(businessKey);
        logger.info(errorMessage);
        logger.debug(errorMessage);
        eiesPublicTool.insertMidDataLog( businessKey, errorMessage, e, jobName, comCode , jobGroup,jobDes );
    	
      dbPool.rollbackTransaction();
      strReturn = "生成XX单、XX异常，请联系系统管理员！";



     
  	  e.printStackTrace();
     
    }
    bankInterFace_DetailDto.setRevolutionFlag(strRevolutionFlag);
    try
    {
      
      blBankInterFace_DetailAction.update(dbManager,bankInterFace_DetailDto);
    }catch(Exception ex)
    {
   
    	ex.printStackTrace();
   
    }    
    return strReturn;
  }
  

   /**
     * @desc 由接口表数据生成XX数据：生成XX单对象BLProposal
     * @param dbPool
     * @param bankInterFace_DetailDto
     * @param iVisaCode
     * @return blProposal
     * @throws Exception
     */
    public BLProposal generateObjectOfProposal(DBManager dbManager, BankInterFace_DetailDto bankInterFace_DetailDto, String iVisaCode) 
        throws Exception 
    {
      BLProposal blProposal = new BLProposal();
      BLPrpTmain blPrpTmain = null;
      BLPrpTinsured blPrpTinsured = null;
      BLPrpTaddress blPrpTaddress = null;
      BLPrpTitemKind blPrpTitemKind = null;
      BLPrpTfee blPrpTfee = null;
      BLPrpTplan blPrpTplan = null;
      BLPrpTmainCasualty blPrpTmainCasualty = null;
      BLPrpTexpense blPrpTexpense = null;
      BLPrpTmainLoan blPrpTmainLoan = null;
      BLPrpTitemHouse blPrpTitemHouse = null;
      BLBankInterface_Ext blBankInterface_Ext = new BLBankInterface_Ext();
      BankInterface_ExtSchema bankInterface_ExtSchema =null;
      
      blPrpTinsured = generateObjectOfTinsured(bankInterFace_DetailDto);
      blPrpTmain = generateObjectOfTmain(dbManager,bankInterFace_DetailDto,iVisaCode); 
      
      BLPrpTmainBank blPrpTmainBank = null;
      blPrpTmainBank = generateObjectOfTmainBank(bankInterFace_DetailDto); 
      
      blPrpTaddress = generateObjectOfTaddress(bankInterFace_DetailDto);
      blPrpTitemKind = generateObjectOfTitemKind(bankInterFace_DetailDto);
      blPrpTfee = generateObjectOfTfee(bankInterFace_DetailDto); 
      blPrpTplan = generateObjectOfTplanNew(bankInterFace_DetailDto);
      blPrpTmainCasualty = generateObjectOfTmainCasualty(bankInterFace_DetailDto);
      blPrpTexpense = generateObjectOfTexpense(dbManager,bankInterFace_DetailDto); 

      BLPrpTinsuredNature blPrpTinsuredNature = generateObjectOfTinsuredNature(bankInterFace_DetailDto,blPrpTinsured);
      blProposal.setBLPrpTinsuredNature(blPrpTinsuredNature);
      
      
      
      
      blBankInterface_Ext.getData(bankInterFace_DetailDto.getPolicyno());
      if(blBankInterface_Ext.getSize()>0){
    	  bankInterface_ExtSchema = blBankInterface_Ext.getArr(0);
      }
      blPrpTmainLoan = generateObjectOfTmainLoan(bankInterFace_DetailDto,bankInterface_ExtSchema);
      blPrpTitemHouse = generateObjectOfTitemHouse(bankInterFace_DetailDto,bankInterface_ExtSchema);
      blProposal.setBLPrpTmainLoan(blPrpTmainLoan);
      blProposal.setBLPrpTitemHouse(blPrpTitemHouse);
      
      
      blProposal.setBLPrpTmain(blPrpTmain);
      
      blProposal.setBLPrpTmainBank(blPrpTmainBank);
      
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
     * @desc 由接口表数据生成XX数据
     * @author wangbingchen 
     * @param bankInterFace_DetailDto
     * @param dblInterestRate
     * @param arrProposalNo
     * @return blPrpTmainInvest
     * @throws Exception
     */
    public BLPrpTmainBank generateObjectOfTmainBank(BankInterFace_DetailDto bankInterFace_DetailDto) throws Exception {
      BLPrpTmainBank blPrpTmainBank = new BLPrpTmainBank();
      PrpTmainBankSchema prpTmainBankSchema = new PrpTmainBankSchema();
      BLPrpdBankCompannyFacade blPrpdBankCompannyFacade = null;
      ArrayList prpdBankCompannyDtoList = null;        
      String condition = "";
      String strBankAgentCode = "";
      String strBankAgentName = "";
      String strBankHandlerName = "";
      
      blPrpdBankCompannyFacade = new BLPrpdBankCompannyFacade();
      strBankAgentCode = bankInterFace_DetailDto.getBankbranchCode();
      condition = " BankCode='" + strBankAgentCode + "'";
      prpdBankCompannyDtoList = (ArrayList)blPrpdBankCompannyFacade.findByConditions(condition);
      if(prpdBankCompannyDtoList.size() > 0) {
        strBankAgentName = ((PrpdBankCompannyDto)prpdBankCompannyDtoList.get(0)).getBankName();
        strBankHandlerName = ((PrpdBankCompannyDto)prpdBankCompannyDtoList.get(0)).getBankLinkMan();
      } else {
        strBankAgentName = strBankAgentCode;
      }        
      prpTmainBankSchema.setProposalNo(bankInterFace_DetailDto.getProposalNo());
      prpTmainBankSchema.setRiskCode("0401");
      
      
      prpTmainBankSchema.setClassCode("04");
      
      prpTmainBankSchema.setBankCode(bankInterFace_DetailDto.getBankcode());
      prpTmainBankSchema.setBankAgentCode(strBankAgentCode); 
      prpTmainBankSchema.setBankAgentName(strBankAgentName); 
      prpTmainBankSchema.setBankHandlerCode(bankInterFace_DetailDto.getCounterperson()); 
      prpTmainBankSchema.setBankHandlerName(strBankHandlerName); 
      prpTmainBankSchema.setFlag(""); 
      prpTmainBankSchema.setRemark("");        
      blPrpTmainBank.setArr(prpTmainBankSchema);
      return blPrpTmainBank;
    }
    

    /**
     * @desc 由接口表数据生成XX数据：生成XX单主信息对象BLPrpTmain
     * @param arrCustomerCode
     * @param bankInterFace_DetailDto
     * @param iVisaCode
     * @return blPrpTmain
     * @throws Exception
     */
    public BLPrpTmain generateObjectOfTmain(DBManager dbManager, BankInterFace_DetailDto bankInterFace_DetailDto, 
        String iVisaCode) throws Exception 
    {
      BLPrpDagreementFacade blPrpDagreementFacade = new BLPrpDagreementFacade();
      PrpDagreementDto prpDagreementDto = null;
      BLPrpTmain blPrpTmain = new BLPrpTmain();
      PrpTmainSchema prpTmainSchema = new PrpTmainSchema();
      BLPrpdBankCompannyFacade blPrpdBankCompannyFacade = null;
      
      BLPrpDriskConfigFacade blPrpDriskConfigFacade = new BLPrpDriskConfigFacade();
      Collection prpDagreementDtoList = null;
      PrpDagreeDetailDto prpDagreeDetailDto = null;
      BLPrpDagreeDetailFacade blPrpDagreeDetailFacade = new BLPrpDagreeDetailFacade();
      DateTime currentDate = new DateTime().current();
      
      String strBankCode = "";
      String strAgentCode = "";
      String strAgreementNo = "";
      String strStartDatePrpT = "";
      String strEndDatePrpT = "";
      ArrayList prpdBankCompannyDtoList = null;
      
      String strUploadDate  = "";
      String strCurrentDate = "";
      int intYear = 0;
      int intMonth = 0;
      int intDay = 0;       
      double dbDisRate = 0;
      
      double dbPolicyFee = 0.0;
      double newDisrate = 0.0;
      
      
      DBPrpDcompany dbPrpDcompany = new DBPrpDcompany();
      String strChannelType = "";
      int iResult = 0;
      
      
      blPrpdBankCompannyFacade = new BLPrpdBankCompannyFacade();
      strBankCode = bankInterFace_DetailDto.getBankbranchCode();
      prpdBankCompannyDtoList = (ArrayList)blPrpdBankCompannyFacade.findByConditions(" BankCode='" + strBankCode + "'");
      if(prpdBankCompannyDtoList.size() == 0){
    	  errorMessage="BLTransDataYB0401.generateObjectOfTmain"+"prpdbankcompanny表"+ "prpdbankcompanny表"+strBankCode+"取值失败,请管理员进行配置！";
        throw new UserException(-98, -1167, "BLTransDataYB0401.generateObjectOfTmain",
            "prpdbankcompanny表银行网点代码"+strBankCode+"取值失败,请管理员进行配置！" ); 
      }      
      if(prpdBankCompannyDtoList.size() > 0) {
        strAgentCode = ((PrpdBankCompannyDto)prpdBankCompannyDtoList.get(0)).getAgentCode();
      }   
      String test =strAgentCode;
      prpDagreementDtoList = blPrpDagreementFacade.findByConditions("AgentCode='" + strAgentCode + "' and validstatus = '1'");
      for(Iterator prpDagreementDtoListTmp = prpDagreementDtoList.iterator(); prpDagreementDtoListTmp.hasNext();) {
        prpDagreementDto = (PrpDagreementDto)prpDagreementDtoListTmp.next();
        strAgreementNo = prpDagreementDto.getAgreementNo();
        break;
      }
      
      CheckChannelYB checkChannelYB = new CheckChannelYB();
      if(!checkChannelYB.checkChannel(bankInterFace_DetailDto.getComCode(),strAgentCode)){
    	  errorMessage="BLTransDataYB0401.generateObjectOfTmain"+  "业务来源与渠道校验失败，请检查相关配置！" ;
    	  throw new UserException(-98, -1167, "BLTransDataYB0401.generateObjectOfTmain",
    	            "业务来源与渠道校验失败，请检查相关配置！" ); 
      }
      
      
      
      
      String strSwitch = "";
      if("103".equals(bankInterFace_DetailDto.getBfbankCode())){
        strSwitch = "CASUALTY_ABC_SWITCH";
      }else if("102".equals(bankInterFace_DetailDto.getBfbankCode())){
        strSwitch = "CASUALTY_ICBC_SWITCH";
      }
      if(!strSwitch.equals("")){
	      if("1".equals(Str.rightTrim(SysConfig.getProperty(strSwitch)))){
	          
	          BLPrpDoutsideAgreedetailAction blPrpDoutsideAgreedetailAction = new BLPrpDoutsideAgreedetailAction();
	          PrpDoutsideAgreedetailDto prpDoutsideAgreedetailDto = null;
	          prpDoutsideAgreedetailDto = blPrpDoutsideAgreedetailAction.findByPrimaryKey(dbManager, strAgreementNo,bankInterFace_DetailDto.getProductCode());
	          if(prpDoutsideAgreedetailDto==null){
	          	 errorMessage="BLTransDataYB2700.generateObjectOfTmain"+  "手续费比例取值失败,请管理员进行配置！" ;
	            throw new UserException(-98, -1167, "BLTransDataYB2700.generateObjectOfTmain",
	              "手续费比例取值失败,请管理员进行配置！" ); 
	          }         
	          if((String.valueOf(prpDoutsideAgreedetailDto.getCommissionrate())).trim().equals("")
	            ||prpDoutsideAgreedetailDto.getCommissionrate()<0){
	          	 errorMessage="BLTransDataYB2700.generateObjectOfTmain"+ "手续费比例取值失败,请管理员进行配置！";
	            throw new UserException(-98, -1167, "BLTransDataYB2700.generateObjectOfTmain",
	            "手续费比例取值失败,请管理员进行配置！" ); 
	          }else
	          {
	            dbDisRate = prpDoutsideAgreedetailDto.getCommissionrate();
	      	  
	      	  dbPolicyFee = prpDoutsideAgreedetailDto.getPolicyFee();
	      	  
	          }
	          
	        }
	        else{
	          prpDagreeDetailDto = blPrpDagreeDetailFacade.findByPrimaryKey(strAgreementNo, bankInterFace_DetailDto.getRiskCode(),"0000");
	          if(prpDagreeDetailDto==null){
	          	errorMessage="BLTransDataYB2700.generateObjectOfTmain"+ "手续费比例取值失败,请管理员进行配置！" ;
	            throw new UserException(-98, -1167, "BLTransDataYB2700.generateObjectOfTmain",
	            "手续费比例取值失败,请管理员进行配置！" ); 
	          }    
	          if((String.valueOf(prpDagreeDetailDto.getTopCommission())).trim().equals("")
	            ||prpDagreeDetailDto.getTopCommission()<0){
	          	errorMessage="BLTransDataYB2700.generateObjectOfTmain"+ "手续费比例取值失败,请管理员进行配置！" ;
	            throw new UserException(-98, -1167, "BLTransDataYB2700.generateObjectOfTmain",
	            "手续费比例取值失败,请管理员进行配置！" ); 
	          }else
	          {
	
	            dbDisRate = prpDagreeDetailDto.getTopCommission();
	        	
	
	        	dbPolicyFee = prpDagreeDetailDto.getPolicyFee();
	        	
	          }
	        }
      }else{
    	  
          if("1".equals(AppConfig.get("sysconst.CASUALTY_CCB_SWITCH"))){
        	
              BLPrpDoutsideAgreedetailAction blPrpDoutsideAgreedetailAction = new BLPrpDoutsideAgreedetailAction();
              PrpDoutsideAgreedetailDto prpDoutsideAgreedetailDto = null;
              prpDoutsideAgreedetailDto = blPrpDoutsideAgreedetailAction.findByPrimaryKey(dbManager, strAgreementNo,bankInterFace_DetailDto.getProductCode());
              if(prpDoutsideAgreedetailDto==null){
              	 errorMessage="BLTransDataYB0401.generateObjectOfTmain"+  "手续费比例取值失败,请管理员进行配置！" ;
                throw new UserException(-98, -1167, "BLTransDataYB0401.generateObjectOfTmain",
                  "手续费比例取值失败,请管理员进行配置！" ); 
              }         
              if((String.valueOf(prpDoutsideAgreedetailDto.getCommissionrate())).trim().equals("")
                ||prpDoutsideAgreedetailDto.getCommissionrate()<0){
              	 errorMessage="BLTransDataYB0401.generateObjectOfTmain"+ "手续费比例取值失败,请管理员进行配置！";
                throw new UserException(-98, -1167, "BLTransDataYB0401.generateObjectOfTmain",
                "手续费比例取值失败,请管理员进行配置！" ); 
              }else
              {
                dbDisRate = prpDoutsideAgreedetailDto.getCommissionrate();
          	  
          	  dbPolicyFee = prpDoutsideAgreedetailDto.getPolicyFee();
          	  
              }
          }else{
              prpDagreeDetailDto = blPrpDagreeDetailFacade.findByPrimaryKey(strAgreementNo, bankInterFace_DetailDto.getRiskCode(),"0000");
              if(prpDagreeDetailDto==null){
              	errorMessage="BLTransDataYB2700.generateObjectOfTmain"+ "手续费比例取值失败,请管理员进行配置！" ;
                throw new UserException(-98, -1167, "BLTransDataYB2700.generateObjectOfTmain",
                "手续费比例取值失败,请管理员进行配置！" ); 
              }    
              if((String.valueOf(prpDagreeDetailDto.getTopCommission())).trim().equals("")
                ||prpDagreeDetailDto.getTopCommission()<0){
              	errorMessage="BLTransDataYB2700.generateObjectOfTmain"+ "手续费比例取值失败,请管理员进行配置！" ;
                throw new UserException(-98, -1167, "BLTransDataYB2700.generateObjectOfTmain",
                "手续费比例取值失败,请管理员进行配置！" ); 
              }else
              {

                dbDisRate = prpDagreeDetailDto.getTopCommission();
            	

            	dbPolicyFee = prpDagreeDetailDto.getPolicyFee();
            	
              }
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
      prpTmainSchema.setSumAmount(String.valueOf(bankInterFace_DetailDto.getSumamount()));
      prpTmainSchema.setSumDiscount("0");
      prpTmainSchema.setSumPremium(String.valueOf(bankInterFace_DetailDto.getSumPremium()));
      prpTmainSchema.setSumSubPrem("0");
      prpTmainSchema.setAutoTransRenewFlag("2");
      prpTmainSchema.setPayTimes("1");
      prpTmainSchema.setEndorseTimes("0");
      prpTmainSchema.setClaimTimes("0");
      prpTmainSchema.setMakeCom(bankInterFace_DetailDto.getMakeCom());
      prpTmainSchema.setOperateSite("YBT");
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
	            throw new UserException(-98, -1167, "BLTransDataYB0401.generateObjectOfTmain","渠道取值失败,XX归属渠道为N01,子渠道不存在S13!" ); 
	        }
      }else{
	        if(!"N03".equals(strChannelType)){
              throw new UserException(-98, -1167, "BLTransDataYB0401.generateObjectOfTmain","渠道取值失败,渠道只能N01、N03!" ); 
	        }
      }
      
      
      



    	  

















      
      
      
      









    	
    	if(dbPolicyFee==0.0){
    		operateFee_PrpTexpense = dbPolicyFee;
    	}
    	
		if("1".equals(bankInterFace_DetailDto.getIseiesflag())&&dbPolicyFee!=0.0){
			double tempValue =(dbPolicyFee*100)/bankInterFace_DetailDto.getSumPremium();
			if("1".equals(AppConfig.get("sysconst.SECTION_ACCOUNT_0401_OPEN"))){
				if("103".equals(bankInterFace_DetailDto.getBfbankCode())){
		  		  
		  		  
				  String province = AppConfig.get("sysconst.SECTION_CONDITION.ABC.BANKCODE");
				  String newBankCode = strBankCode.substring(0, 2);
				  
				  String conditionBank = "sysconst.SECTION_CONDITION.ABC.CONDITION.CONDITION_"+newBankCode;
				  
				  double disFee = bankInterFace_DetailDto.getSumPremium()*(dbDisRate/100); 
		  		  if(province.indexOf(newBankCode) > -1){
		  			  if(disFee > Double.parseDouble(AppConfig.get(conditionBank))){
		  				  newDisrate = tempValue+dbDisRate;
		  				  operateFee_PrpTexpense=dbPolicyFee;
		  			  }else{
		  				  newDisrate = dbDisRate;
		  				  operateFee_PrpTexpense=0.0;
		  			  }
		  		  }else{
		  			  newDisrate = tempValue+dbDisRate;
		  			  operateFee_PrpTexpense=dbPolicyFee;
		  		  }
			    }else{
		  			  newDisrate = tempValue+dbDisRate;
		              operateFee_PrpTexpense=dbPolicyFee;
			  	}
			}else{
				newDisrate = tempValue+dbDisRate;
				operateFee_PrpTexpense=dbPolicyFee;
			}
			
			bankDisRate_PrpTexpense=dbDisRate;
			
			newDisrate = Str.round(newDisrate,4);
	  		






	  		  prpTmainSchema.setDisRate(String.valueOf(newDisrate));
	  	  } else if("2".equals(bankInterFace_DetailDto.getIseiesflag())){
			
		}


  	  
      
      prpTmainSchema.setSumQuantity("1");
      
      blPrpTmain.setArr(prpTmainSchema);
      return blPrpTmain;
    }

    /**
     * @desc 由接口表数据生成XX数据：生成XX单XX/被XX人信息对象BLPrpTinsured
     * @param bankInterFace_DetailDto
     * @return blPrpTinsured
     * @throws Exception
     */
    public BLPrpTinsured generateObjectOfTinsured(BankInterFace_DetailDto bankInterFace_DetailDto) throws Exception 
    {
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

      if("102".equals(bankInterFace_DetailDto.getBfbankCode())){
    	  prpTinsuredSchema1.setIdentifyType(this.getIdentifyType(bankInterFace_DetailDto.getIdType()));
      }else if ("103".equals(bankInterFace_DetailDto.getBfbankCode())){
    	  prpTinsuredSchema1.setIdentifyType(bankInterFace_DetailDto.getIdType());
      }else if ("105".equals(bankInterFace_DetailDto.getBfbankCode())){
    	  prpTinsuredSchema1.setIdentifyType(this.getIdentifyTypeCCB(bankInterFace_DetailDto.getIdType()));
      }

      prpTinsuredSchema1.setIdentifyNumber(bankInterFace_DetailDto.getAppidNo());
      prpTinsuredSchema1.setBank(bankInterFace_DetailDto.getBfbankCode());
      prpTinsuredSchema1.setAccount(bankInterFace_DetailDto.getBfaccountNo());
      prpTinsuredSchema1.setPostAddress(bankInterFace_DetailDto.getAddress());
      prpTinsuredSchema1.setPostCode(bankInterFace_DetailDto.getPostCode());
      
      prpTinsuredSchema1.setPhoneNumber(bankInterFace_DetailDto.getPhone());
      prpTinsuredSchema1.setMobile(bankInterFace_DetailDto.getMobileCode());
      prpTinsuredSchema1.setEmail(bankInterFace_DetailDto.getEmail());
      prpTinsuredSchema1.setInsuredIdentity("01");
      
      prpTinsuredSchema1.setRelateSerialNo("0");
      
      arrCustomerCode[0] = blPrpDcustomerIdvNew.createCustomer(prpTinsuredSchema1, bankInterFace_DetailDto.getComCode(), 
          bankInterFace_DetailDto.getHandlerCode(), bankInterFace_DetailDto.getHandler1Code());
      prpTinsuredSchema1.setInsuredCode(arrCustomerCode[0]);
      blPrpTinsured.setArr(prpTinsuredSchema1);

      
      if("102".equals(bankInterFace_DetailDto.getBfbankCode())){
    	  prpTinsuredSchema2.setIdentifyType(this.getIdentifyType(bankInterFace_DetailDto.getInsuredType()));
      }else if ("103".equals(bankInterFace_DetailDto.getBfbankCode())){
    	  prpTinsuredSchema2.setIdentifyType(bankInterFace_DetailDto.getInsuredType());
      }else if ("105".equals(bankInterFace_DetailDto.getBfbankCode())){
    	  prpTinsuredSchema2.setIdentifyType(this.getIdentifyTypeCCB(bankInterFace_DetailDto.getInsuredType()));
      }        

      prpTinsuredSchema2.setIdentifyNumber(bankInterFace_DetailDto.getInsuredNumber());

      prpTinsuredSchema2.setProposalNo(bankInterFace_DetailDto.getProposalNo());
      prpTinsuredSchema2.setRiskCode(bankInterFace_DetailDto.getRiskCode());
      prpTinsuredSchema2.setSerialNo("2");
      prpTinsuredSchema2.setLanguage("C");
      prpTinsuredSchema2.setInsuredType("1");
      prpTinsuredSchema2.setInsuredName(bankInterFace_DetailDto.getInsuredName());
      prpTinsuredSchema2.setInsuredAddress(bankInterFace_DetailDto.getInsuredAdress());
      prpTinsuredSchema2.setInsuredNature("3");
      prpTinsuredSchema2.setInsuredFlag("1");
      prpTinsuredSchema2.setBank(bankInterFace_DetailDto.getBfbankCode());
      prpTinsuredSchema2.setAccount(bankInterFace_DetailDto.getBfaccountNo());
      prpTinsuredSchema2.setPostAddress(bankInterFace_DetailDto.getInsuredAdress());
      prpTinsuredSchema2.setPostCode(bankInterFace_DetailDto.getInsurepostCode());
      prpTinsuredSchema2.setPhoneNumber(bankInterFace_DetailDto.getInsuredPhone());
      
      prpTinsuredSchema2.setMobile(bankInterFace_DetailDto.getMobileCode());
      prpTinsuredSchema2.setOccupationCode(bankInterFace_DetailDto.getOccupationCode());
      arrCustomerCode[1] = blPrpDcustomerIdvNew.createCustomer(prpTinsuredSchema1, bankInterFace_DetailDto.getComCode(), 
          bankInterFace_DetailDto.getHandlerCode(), bankInterFace_DetailDto.getHandler1Code());
      prpTinsuredSchema2.setInsuredCode(arrCustomerCode[1]);
      
      prpTinsuredSchema2.setInsuredIdentity("01");
      prpTinsuredSchema2.setRelateSerialNo("0");
      
      blPrpTinsured.setArr(prpTinsuredSchema2);
      int intBenefitCount = 0;
      if(bankInterFace_DetailDto.getBenefitCount() != null && !"".equals(bankInterFace_DetailDto.getBenefitCount()))
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

        if("102".equals(bankInterFace_DetailDto.getBfbankCode())){
        	prpTinsuredSchema3.setIdentifyType(this.getIdentifyType(bankInterFace_DetailDto.getBenefitIdType1()));
        }else if ("103".equals(bankInterFace_DetailDto.getBfbankCode())){
        	prpTinsuredSchema3.setIdentifyType(bankInterFace_DetailDto.getBenefitIdType1());
        }else if ("105".equals(bankInterFace_DetailDto.getBfbankCode())){
        	prpTinsuredSchema3.setIdentifyType(this.getIdentifyTypeCCB(bankInterFace_DetailDto.getBenefitIdType1()));
        }       

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
        
        prpTinsuredSchema3.setRelateSerialNo("2");
        if("".equals(bankInterFace_DetailDto.getBenefitIdentity1()) || bankInterFace_DetailDto.getBenefitIdentity1() == null){
        	prpTinsuredSchema3.setInsuredIdentity("99");
        }
        
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

          if("102".equals(bankInterFace_DetailDto.getBfbankCode())){
          	prpTinsuredSchema4.setIdentifyType(this.getIdentifyType(bankInterFace_DetailDto.getBenefitIdType2()));
          }else if ("103".equals(bankInterFace_DetailDto.getBfbankCode())){
        	  prpTinsuredSchema4.setIdentifyType(bankInterFace_DetailDto.getBenefitIdType2());
          }else if ("105".equals(bankInterFace_DetailDto.getBfbankCode())){
        	  prpTinsuredSchema4.setIdentifyType(this.getIdentifyTypeCCB(bankInterFace_DetailDto.getBenefitIdType2()));
          }  

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
          
          prpTinsuredSchema3.setRelateSerialNo("2");
          if("".equals(bankInterFace_DetailDto.getBenefitIdentity1()) || bankInterFace_DetailDto.getBenefitIdentity1() == null){
          	prpTinsuredSchema3.setInsuredIdentity("99");
          }
          
          blPrpTinsured.setArr(prpTinsuredSchema4);
        }
      }
      return blPrpTinsured;
    }

    /**
     * @desc 由接口表数据生成XX数据：生成XX单地址信息对象BLPrpTaddress
     * @param bankInterFace_DetailDto
     * @return blPrpTaddress
     * @throws Exception
     */
    public BLPrpTaddress generateObjectOfTaddress(BankInterFace_DetailDto bankInterFace_DetailDto) throws Exception 
    {
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
     * @desc 由接口表数据生成XX数据：生成XX单XXXXX别信息对象BLPrpTitemKind
     * @param bankInterFace_DetailDto
     * @param prpDbankInvestDto
     * @param prpDkindDto
     * @param arrProposalNo
     * @param blPrpDration0301
     * @param blPrpDration2700
     * @return blPrpTitemKind
     * @throws Exception
     */
    public BLPrpTitemKind generateObjectOfTitemKind(BankInterFace_DetailDto bankInterFace_DetailDto) throws Exception {
      BLPrpTitemKind blPrpTitemKind = new BLPrpTitemKind();     
      DBPrpDkind dbPrpDkind = new DBPrpDkind();  
      PrpTitemKindSchema   prpTitemKindSchema = null;     
      String strStartDate  = "";
      String strEndDate    = "";
      String strRiskCode   = "";
      String strKindCode   = "";
      String strKindName   = "";  
      String strFlag       = "";     
      String strItemCode = "";
      int intYear = 0;
      int intMonth = 0;
      int intDay = 0;
      String strUserFor = "";
      intYear = bankInterFace_DetailDto.getStartDate().getYear();
      intMonth = bankInterFace_DetailDto.getStartDate().getMonth();
      intDay = bankInterFace_DetailDto.getStartDate().getDay();
      strStartDate = new Integer(intYear).toString() + "-"
                    + new Integer(intMonth).toString() + "-"
                    + new Integer(intDay).toString();

      intYear  = bankInterFace_DetailDto.getEndDate().getYear();
      intMonth = bankInterFace_DetailDto.getEndDate().getMonth();
      intDay   = bankInterFace_DetailDto.getEndDate().getDay();
      strEndDate  = new Integer(intYear).toString() + "-"
                   + new Integer(intMonth).toString() + "-"
                   + new Integer(intDay).toString();
      strRiskCode = bankInterFace_DetailDto.getRiskCode();      
      strUserFor = bankInterFace_DetailDto.getUseFor();
      prpTitemKindSchema = new PrpTitemKindSchema();
      strKindCode ="001";
      dbPrpDkind.getInfo(strRiskCode,strKindCode);
      strKindName  = dbPrpDkind.getKindCName();   
      strItemCode  = "0001";
      prpTitemKindSchema.setCalculateFlag("Y");  
      prpTitemKindSchema.setFlag(strFlag + Str.space(1-strFlag.length()) + "1");
      prpTitemKindSchema.setProposalNo(bankInterFace_DetailDto.getProposalNo());
      prpTitemKindSchema.setRiskCode("0401");
      prpTitemKindSchema.setItemKindNo("1");
      prpTitemKindSchema.setKindCode(strKindCode);
      prpTitemKindSchema.setKindName(strKindName); 
      
      
      prpTitemKindSchema.setAddressNo("1");
      
      prpTitemKindSchema.setItemCode(strItemCode);
      prpTitemKindSchema.setItemDetailName("房屋");
      prpTitemKindSchema.setEndDate(strEndDate);
      prpTitemKindSchema.setEndHour("24");
      prpTitemKindSchema.setStartDate(strStartDate);
      prpTitemKindSchema.setStartHour("0");
      prpTitemKindSchema.setCurrency("CNY");
      prpTitemKindSchema.setAmount(String.valueOf(bankInterFace_DetailDto.getSumamount()));
      prpTitemKindSchema.setShortRate("100.0000");
      prpTitemKindSchema.setShortRateFlag("3");
      prpTitemKindSchema.setPremium(String.valueOf(bankInterFace_DetailDto.getSumPremium()));
      prpTitemKindSchema.setValue(String.valueOf(bankInterFace_DetailDto.getInvestCount()));
      prpTitemKindSchema.setDiscount("0.0000");
      
      DecimalFormat dcmFmt = new DecimalFormat("0.0000");
      prpTitemKindSchema.setRate(dcmFmt.format(bankInterFace_DetailDto.getSumPremium()/bankInterFace_DetailDto.getSumamount()));
      prpTitemKindSchema.setAdjustRate("1.0000");
      blPrpTitemKind.setArr(prpTitemKindSchema);
      return blPrpTitemKind ;
     }

    /**
     * @desc 由接口表数据生成XX数据：生成XX单金额信息对象BLPrpTfee
     * @param bankInterFace_DetailDto
     * @param prpDbankInvestDto
     * @return blPrpTfee
     * @throws Exception
     */
    public BLPrpTfee generateObjectOfTfee(BankInterFace_DetailDto bankInterFace_DetailDto) throws Exception 
    {
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
      blPrpTfee.setArr(prpTfeeSchema);
      return blPrpTfee;
    }

    /**
     * @desc 由接口表数据生成XX数据：生成XX单缴费计划信息对象BLPrpTplan
     * @param bankInterFace_DetailDto
     * @return blPrpTplan
     * @throws Exception
     */
    public BLPrpTplan generateObjectOfTplanNew(BankInterFace_DetailDto bankInterFace_DetailDto) throws Exception 
    {
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
     * @desc 由接口表数据生成XX数据：生成XX单缴费计划信息对象BLPrpTplan
     * @param bankInterFace_DetailDto
     * @return blPrpTplan
     * @throws Exception
     */
    public BLPrpTplan generateObjectOfTplan(BankInterFace_DetailDto bankInterFace_DetailDto) throws Exception 
    {
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
      throws Exception 
  {
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
  public BLPrpTexpense generateObjectOfTexpense(DBManager dbManager,BankInterFace_DetailDto bankInterFace_DetailDto)throws Exception 
  {
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
    	 errorMessage="BLTransDataYB0401.generateObjectOfTexpense"+"prpdbankcompanny表"+strBankCode+"取值失败,请管理员进行配置！" ;
      throw new UserException(-98, -1167, "BLTransDataYB0401.generateObjectOfTexpense",
          "prpdbankcompanny表"+strBankCode+"取值失败,请管理员进行配置！" ); 
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

    String strSwitch = "";
    if("103".equals(bankInterFace_DetailDto.getBfbankCode())){
      strSwitch = "CASUALTY_ABC_SWITCH";
    }else if("102".equals(bankInterFace_DetailDto.getBfbankCode())){
      strSwitch = "CASUALTY_ICBC_SWITCH";
    }
    
    if(!strSwitch.equals("")){
	    if("1".equals(Str.rightTrim(SysConfig.getProperty(strSwitch)))){
	
	      BLPrpDoutsideAgreedetailAction blPrpDoutsideAgreedetailAction = new BLPrpDoutsideAgreedetailAction();
	      PrpDoutsideAgreedetailDto prpDoutsideAgreedetailDto = null;
	      prpDoutsideAgreedetailDto = blPrpDoutsideAgreedetailAction.findByPrimaryKey(dbManager, strAgreementNo,bankInterFace_DetailDto.getProductCode());
	      if(prpDoutsideAgreedetailDto==null){
	    	  errorMessage="BLTransDataYB2700.generateObjectOfTexpense"+"手续费比例取值失败,请管理员进行配置！" ;
	        throw new UserException(-98, -1167, "BLTransDataYB2700.generateObjectOfTexpense",
	      "手续费比例取值失败,请管理员进行配置！" );
	      }         
	      if((String.valueOf(prpDoutsideAgreedetailDto.getCommissionrate())).trim().equals("")
	         ||prpDoutsideAgreedetailDto.getCommissionrate()<0){
	    	  errorMessage="BLTransDataYB2700.generateObjectOfTexpense"+"手续费比例取值失败,请管理员进行配置！" ;
	        throw new UserException(-98, -1167, "BLTransDataYB2700.generateObjectOfTexpense",
	            "手续费比例取值失败,请管理员进行配置！" ); 
	      }else
	      {
	        manageFeeRate = prpDoutsideAgreedetailDto.getManagefeerate()+"";
	        salesSalaryRate = prpDoutsideAgreedetailDto.getSalessalaryrate()+"";
	      }  
	    }
    }else{
	    
	    if("1".equals(AppConfig.get("sysconst.CASUALTY_CCB_SWITCH"))){
	    	BLPrpDoutsideAgreedetailAction blPrpDoutsideAgreedetailAction = new BLPrpDoutsideAgreedetailAction();
	        PrpDoutsideAgreedetailDto prpDoutsideAgreedetailDto = null;
	        prpDoutsideAgreedetailDto = blPrpDoutsideAgreedetailAction.findByPrimaryKey(dbManager, strAgreementNo,bankInterFace_DetailDto.getProductCode());
	        if(prpDoutsideAgreedetailDto==null){
	      	  errorMessage="BLTransDataYB2700.generateObjectOfTexpense"+"手续费比例取值失败,请管理员进行配置！" ;
	          throw new UserException(-98, -1167, "BLTransDataYB2700.generateObjectOfTexpense",
	        "手续费比例取值失败,请管理员进行配置！" );
	        }         
	        if((String.valueOf(prpDoutsideAgreedetailDto.getCommissionrate())).trim().equals("")
	           ||prpDoutsideAgreedetailDto.getCommissionrate()<0){
	      	  errorMessage="BLTransDataYB2700.generateObjectOfTexpense"+"手续费比例取值失败,请管理员进行配置！" ;
	          throw new UserException(-98, -1167, "BLTransDataYB2700.generateObjectOfTexpense",
	              "手续费比例取值失败,请管理员进行配置！" ); 
	        }else
	        {
	          manageFeeRate = prpDoutsideAgreedetailDto.getManagefeerate()+"";
	          salesSalaryRate = prpDoutsideAgreedetailDto.getSalessalaryrate()+"";
	        } 
	    }
    }
       
    PrpTexpenseSchema prpTexpenseSchema = new PrpTexpenseSchema();
    prpTexpenseSchema.setProposalNo(bankInterFace_DetailDto.getProposalNo());
    prpTexpenseSchema.setRiskCode(bankInterFace_DetailDto.getRiskCode());
    prpTexpenseSchema.setManageFeeRate(manageFeeRate);
    prpTexpenseSchema.setSalesSalaryRate(salesSalaryRate);

    String operateFee= operateFee_PrpTexpense+"";
    String bankDisRate= bankDisRate_PrpTexpense+"";
    prpTexpenseSchema.setOperateFee(operateFee);
    prpTexpenseSchema.setBankDisRate(bankDisRate);
    prpTexpenseSchema.setFlag("I1");
    blPrpTexpense.setArr(prpTexpenseSchema);    
    return blPrpTexpense;
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
  
  /**
	 * 工行获取系统内证件类型
	 * @param iIdentifyType
	 * @return
	 * @throws Exception
	 */
	public String getIdentifyType (String iIdentifyType) throws Exception {
		String strIdentifyType = iIdentifyType;
		if("0".equals(iIdentifyType)){
			strIdentifyType="01";
		}else if("1".equals(iIdentifyType)){
			strIdentifyType="03";
		}else if("2".equals(iIdentifyType)){
			strIdentifyType="04";
		}else if("3".equals(iIdentifyType)){
			strIdentifyType="99";
		}else if("4".equals(iIdentifyType)){
			strIdentifyType="06";
		}else if("5".equals(iIdentifyType)){
			strIdentifyType="99";
		}else if("6".equals(iIdentifyType)){
			strIdentifyType="02";
		}else if("7".equals(iIdentifyType)){
			strIdentifyType="99";
		}else if("9".equals(iIdentifyType)){
			strIdentifyType="99";
		}
  	return strIdentifyType;
  }

	/**
	 * 建行获取系统内证件类型
	 * @param iIdentifyType
	 * @return
	 * @throws Exception
	 */
	public String getIdentifyTypeCCB (String iIdentifyType) throws Exception {
		String strIdentifyType = iIdentifyType;
		if("A".equals(iIdentifyType)){
			strIdentifyType="01";
		}else if("B".equals(iIdentifyType)){
			strIdentifyType="04";
		}else if("C".equals(iIdentifyType)){
			strIdentifyType="09";
		}else if("D".equals(iIdentifyType)){
			strIdentifyType="09";
		}else if("E".equals(iIdentifyType)){
			strIdentifyType="09";
		}else if("F".equals(iIdentifyType)){
			strIdentifyType="02";
		}else if("G".equals(iIdentifyType)){
			strIdentifyType="06";
		}else if("H".equals(iIdentifyType)){
			strIdentifyType="09";
		}else if("I".equals(iIdentifyType)){
			strIdentifyType="03";
		}else if("J".equals(iIdentifyType)){
			strIdentifyType="03";
		}else if("K".equals(iIdentifyType)){
			strIdentifyType="09";
		}else if("L".equals(iIdentifyType)){
			strIdentifyType="09";
		}else if("3".equals(iIdentifyType)){
			strIdentifyType="05";
		}else if("5".equals(iIdentifyType)){
			strIdentifyType="09";
		}else if("6".equals(iIdentifyType)){
			strIdentifyType="09";
		}else if("9".equals(iIdentifyType)){
			strIdentifyType="09";
		}else if("Z".equals(iIdentifyType)){
			strIdentifyType="09";
		}
  	return strIdentifyType;
  }
	
	
	/**
	 * @desc 生成PrpTmainLoan表数据
	 * @param bankInterFace_DetailDto
	 * @return
	 * @throws Exception 
	 */
	private BLPrpTmainLoan generateObjectOfTmainLoan(
			BankInterFace_DetailDto bankInterFace_DetailDto,BankInterface_ExtSchema bankInterface_ExtSchema) throws Exception {
		BLPrpTmainLoan blPrpTmainLoan = new BLPrpTmainLoan();
		PrpTmainLoanSchema prpTmainLoanSchema = new PrpTmainLoanSchema();
		BLPrpdBankCompannyFacade blPrpdBankCompannyFacade = new BLPrpdBankCompannyFacade();
	    ArrayList prpdBankCompannyDtoList = null;
	    String strBankAgentCode = "";
	    String strBankAgentName = "";
		prpTmainLoanSchema.setProposalNo(bankInterFace_DetailDto.getProposalNo());
		prpTmainLoanSchema.setRiskCode(bankInterFace_DetailDto.getRiskCode());
		prpTmainLoanSchema.setMortgageNo(bankInterFace_DetailDto.getMobileCode());
		prpTmainLoanSchema.setLoanBankCode(bankInterFace_DetailDto.getBankcode());
		 String strBankCode = bankInterFace_DetailDto.getBankbranchCode();
		    StringBuffer strQuery = new StringBuffer();
		    strQuery.append(" BankCode='");
		    strQuery.append(strBankCode);
		    strQuery.append("'");
			prpdBankCompannyDtoList = (ArrayList) blPrpdBankCompannyFacade .findByConditions(strQuery.toString());
			strBankAgentCode = bankInterFace_DetailDto.getBankbranchCode();
			 if(prpdBankCompannyDtoList.size() > 0) {
			        strBankAgentName = ((PrpdBankCompannyDto)prpdBankCompannyDtoList.get(0)).getBankName();
			      } else {
			        strBankAgentName = strBankAgentCode;
			      }    
		prpTmainLoanSchema.setLoanBankName(strBankAgentName);
		prpTmainLoanSchema.setLoanUsage(bankInterFace_DetailDto.getUseFor());
		prpTmainLoanSchema.setLoanStartDate(String.valueOf(bankInterFace_DetailDto.getStartDate()));
		prpTmainLoanSchema.setLoanEndDate(String.valueOf(bankInterFace_DetailDto.getEndDate()));
	    int startYear = bankInterFace_DetailDto.getStartDate().getYear();
	    int endYear = bankInterFace_DetailDto.getEndDate().getYear();
	    int loanYear = endYear-startYear;
	    if(loanYear==0){ 
	    	loanYear++;
	    }
		prpTmainLoanSchema.setLoanYear(String.valueOf(loanYear));
		prpTmainLoanSchema.setCurrency("CNY");
		String strLoanAmount = bankInterface_ExtSchema.getLoanAmount();
	    double sumAmount = bankInterFace_DetailDto.getSumamount();
	    double loanAmount =Double.parseDouble(strLoanAmount);
	    if(sumAmount<loanAmount){
	    	  errorMessage="BLTransDataYB0401.generateObjectOfTmainLoan 借款金额不能大于总XX,此单转数失败！";
	        throw new UserException(-98, -1167, "BLTransDataYB0401.generateObjectOfTmainLoan",
	            "BLTransDataYB0410.generateObjectOfTmainLoan 借款金额不能大于总XX,此单转数失败！" ); 
	    }
		prpTmainLoanSchema.setLoanAmount(bankInterface_ExtSchema.getLoanAmount());
		blPrpTmainLoan.setArr(prpTmainLoanSchema);
		return blPrpTmainLoan;
	}
	
	private BLPrpTitemHouse generateObjectOfTitemHouse(
			BankInterFace_DetailDto bankInterFace_DetailDto,BankInterface_ExtSchema bankInterface_ExtSchema) throws Exception {
		BLPrpTitemHouse blPrpTitemHouse = new BLPrpTitemHouse();
		PrpTitemHouseSchema prpTitemHouseSchema = new PrpTitemHouseSchema();
		prpTitemHouseSchema.setProposalNo(bankInterFace_DetailDto.getProposalNo());
		prpTitemHouseSchema.setRiskCode(bankInterFace_DetailDto.getRiskCode());
		prpTitemHouseSchema.setItemNo("1");
		prpTitemHouseSchema.setStructure(bankInterface_ExtSchema.getHouseConstruction());
		blPrpTitemHouse.setArr(prpTitemHouseSchema);
		return blPrpTitemHouse;
	}
	
public String getErrorMessage() {
	return errorMessage;
}


public void setErrorMessage(String errorMessage) {
	this.errorMessage = errorMessage;
}

}
