package com.sp.prpall.blsvr.misc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sp.platform.bl.action.domain.BLBankInterFace_DetailAction;
import com.sp.platform.bl.action.domain.BLPrpDoutsideAgreedetailAction;
import com.sp.platform.bl.facade.BLPrpDagentFacade;
import com.sp.platform.bl.facade.BLPrpDagreeDetailFacade;
import com.sp.platform.bl.facade.BLPrpDagreementFacade;
import com.sp.platform.bl.facade.BLPrpDcompanyFacade;
import com.sp.platform.bl.facade.BLPrpDriskConfigFacade;
import com.sp.platform.bl.facade.BLPrpdBankCompannyFacade;
import com.sp.platform.dto.domain.BankInterFace_DetailDto;
import com.sp.platform.dto.domain.PrpDagentDto;
import com.sp.platform.dto.domain.PrpDagreeDetailDto;
import com.sp.platform.dto.domain.PrpDagreementDto;
import com.sp.platform.dto.domain.PrpDcompanyDto;
import com.sp.platform.dto.domain.PrpDoutsideAgreedetailDto;
import com.sp.platform.dto.domain.PrpDriskConfigDto;
import com.sp.platform.dto.domain.PrpdBankCompannyDto;
import com.sp.platform.ui.control.action.UIPrpDriskConfigAction;
import com.sp.prpall.blsvr.tb.BLProposal;
import com.sp.prpall.blsvr.tb.BLPrpTaddress;
import com.sp.prpall.blsvr.tb.BLPrpTbatch;
import com.sp.prpall.blsvr.tb.BLPrpTexpense;
import com.sp.prpall.blsvr.tb.BLPrpTfee;
import com.sp.prpall.blsvr.tb.BLPrpTinsured;
import com.sp.prpall.blsvr.tb.BLPrpTitemKind;
import com.sp.prpall.blsvr.tb.BLPrpTmain;
import com.sp.prpall.blsvr.tb.BLPrpTmainBank;
import com.sp.prpall.blsvr.tb.BLPrpTplan;
import com.sp.prpall.pubfun.CheckChannelYB;
import com.sp.prpall.pubfun.eiespublictool.EiesPublicTool;
import com.sp.prpall.schema.PrpTaddressSchema;
import com.sp.prpall.schema.PrpTbatchSchema;
import com.sp.prpall.schema.PrpTexpenseSchema;
import com.sp.prpall.schema.PrpTfeeSchema;
import com.sp.prpall.schema.PrpTinsuredSchema;
import com.sp.prpall.schema.PrpTitemKindSchema;
import com.sp.prpall.schema.PrpTmainBankSchema;
import com.sp.prpall.schema.PrpTmainSchema;
import com.sp.prpall.schema.PrpTplanSchema;
import com.sp.sysframework.common.datatype.DateTime;
import com.sp.sysframework.reference.AppConfig;
import com.sp.sysframework.reference.DBManager;
import com.sp.utiall.blsvr.BLPrpDcustomerIdvNew;
import com.sp.utiall.dbsvr.DBPrpDcompany;
import com.sp.utiall.dbsvr.DBPrpDkind;
import com.sp.utility.SysConfig;
import com.sp.utility.UtiPower;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.utility.string.ChgDate;
import com.sp.utility.string.Str;

public class BLTransDataYB0302 {
  
  /*
     * 本类完成的功能：
     * 将BankInterFace_Detail表中当前所有的数据转换为0302XX数据，并送相关数据到收付的接口表中
     * for(BankInterFace_Detail) {
     *   1、传入接口表BankInterFace_Detail数据
     *   2、获得XXXXX代码
     *   3、生成XXXXX资料数据
     *   4、生成XX单数据
     *   5、提交核XXXXX
     *   6、生成XX数据
     *   7、修改BankInterFace_Detail表数据
     *   8、反写BankInterFace_Detail表中的数据
     *   9、将对象放入Attribute中
     * }
     */
  String[] arrCustomerCode = new String[2];
  
  public BLTransDataYB0302() {
  }
  protected final static Log logger = LogFactory.getLog(BLTransDataYB0302.class);
  

  
  String jobName="中间业务平台转数失败日志记录";
  String comCode ="";
  String jobGroup ="";
  String jobDes="BLTransDataYB0302转数失败日志记录";
  String businessKey ="";
  String executeDate ="";
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
  

  /**银XXXXX通0302转数
   * @param request
   * @param bankInterFace_DetailDto
   * @param isEiesFlag
   * @param vscode
   * @throws Exception
   */
  public String transData(DbPool dbPool,BankInterFace_DetailDto bankInterFace_DetailDto,String isEiesFlag,String vscode)throws Exception{
    DBManager dbManager = null;
    dbManager = dbPool.getDBManager(SysConfig.CONST_DDCCDATASOURCE);
    BLBankInterFace_DetailAction blBankInterFace_DetailAction = new BLBankInterFace_DetailAction();
    String strReturn = "";

    String strRevolutionFlag = "0";

    
  	
    businessKey =bankInterFace_DetailDto.getPolicyno();
    comCode = bankInterFace_DetailDto.getComCode();
  	jobGroup =bankInterFace_DetailDto.getBfbankCode();
	if("402".equals(jobGroup)){
		
		
		jobGroup=eiesPublicTool.getExtEnterpCode(bankInterFace_DetailDto.getBankbranchCode());
	}
    intYear = Date.getYear();
    intMonth = Date.getMonth();
    intDay = Date.getDay();
    successFlag =bankInterFace_DetailDto.getRevolutionFlag();
    errorStack  ="";
   createCode   ="ZJYW_0302_admin";
 
    try{
      BLProposal blProposal = null;
      String strProposalNo = "";
      
      strProposalNo = bankInterFace_DetailDto.getProposalNo();
      blProposal = generateObjectOfProposal(dbPool, arrCustomerCode, bankInterFace_DetailDto,vscode,strProposalNo,isEiesFlag);
      dbPool.beginTransaction();
      blProposal.save(dbPool, "I", false);    
      com.sp.undwrt.bl.facade.BLTaskFacade blTaskFacade = new com.sp.undwrt.bl.facade.BLTaskFacade();
      blTaskFacade.start(dbManager,"11","T",strProposalNo,"0302",
                   "03",bankInterFace_DetailDto.getMakeCom(),bankInterFace_DetailDto.getComCode(),
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
     * @param arrCustomerCode
     * @param bankInterFace_DetailDto
     * @param vscode
     * @param arrProposalNo
     * @return blProposal
     * @throws Exception
     */
  
    public BLProposal generateObjectOfProposal(DbPool dbPool,String[] arrCustomerCode, BankInterFace_DetailDto bankInterFace_DetailDto, String vscode, String arrProposalNo,String IsEiesFlag) throws Exception {
      BLProposal blProposal = new BLProposal();
      BLPrpTmain blPrpTmain = null;
      BLPrpTinsured blPrpTinsured = null;
      BLPrpTaddress blPrpTaddress = null;
      BLPrpTmainBank blPrpTmainBank = null;
      BLPrpTitemKind blPrpTitemKind = null;
      BLPrpTfee blPrpTfee = null;
      BLPrpTplan blPrpTplan = null;
      BLPrpTbatch blPrpTbatch = null;
      BLPrpTexpense blPrpTexpense = null;
      blPrpTinsured = generateObjectOfTinsured(arrCustomerCode, bankInterFace_DetailDto,arrProposalNo,IsEiesFlag);
      blPrpTmain = generateObjectOfTmain(arrCustomerCode, bankInterFace_DetailDto,vscode,arrProposalNo,IsEiesFlag); 
      blPrpTaddress = generateObjectOfTaddress(bankInterFace_DetailDto,arrProposalNo);
      blPrpTmainBank = generateObjectOfTmainBank(bankInterFace_DetailDto,arrProposalNo); 
      blPrpTitemKind = generateObjectOfTitemKind(bankInterFace_DetailDto,arrProposalNo,IsEiesFlag);
      blPrpTfee  = generateObjectOfTfee(bankInterFace_DetailDto,arrProposalNo,IsEiesFlag); 
      blPrpTplan = generateObjectOfTplan(bankInterFace_DetailDto,arrProposalNo);
      blPrpTexpense = generateObjectOfTexpense(dbPool,bankInterFace_DetailDto);
      if(!"WYEies".equals(IsEiesFlag)){
        blPrpTbatch = generateObjectOfTbatch(bankInterFace_DetailDto,arrProposalNo,IsEiesFlag,vscode);
      }
      blProposal.setBLPrpTmain(blPrpTmain);
      blProposal.setBLPrpTinsured(blPrpTinsured);
      blProposal.setBLPrpTaddress(blPrpTaddress);
      blProposal.setBLPrpTmainBank(blPrpTmainBank);
      blProposal.setBLPrpTitemKind(blPrpTitemKind);
      blProposal.setBLPrpTfee(blPrpTfee);
      blProposal.setBLPrpTplan(blPrpTplan);
      if(!"WYEies".equals(IsEiesFlag)){
        blProposal.setBLPrpTbatch(blPrpTbatch);
      }
      blProposal.setBLPrpTexpense(blPrpTexpense);
      return blProposal;
    }
    /**
     * 生成费用信息表
     * 原0302不存费用表信息，为XXXXX持一致存值。
     * @param bankInterFace_DetailDto 中间表对象
     * @return blPrpTexpense
     */
    public BLPrpTexpense generateObjectOfTexpense(DbPool dbPool,BankInterFace_DetailDto bankInterFace_DetailDto)throws Exception 
    {
    	logger.debug("进入0302 生成费用信息表----------");
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
      	 errorMessage="BLTransDataYB0302.generateObjectOfTexpense"+"prpdbankcompanny表"+strBankCode+"取值失败,请管理员进行配置！" ;
        throw new UserException(-98, -1167, "BLTransDataYB0302.generateObjectOfTexpense",
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









      PrpTexpenseSchema prpTexpenseSchema = new PrpTexpenseSchema();
      prpTexpenseSchema.setProposalNo(bankInterFace_DetailDto.getProposalNo());
      prpTexpenseSchema.setRiskCode(bankInterFace_DetailDto.getRiskCode());
      prpTexpenseSchema.setManageFeeRate(manageFeeRate);
      prpTexpenseSchema.setSalesSalaryRate(salesSalaryRate);

      prpTexpenseSchema.setOperateFee(operateFee_PrpTexpense+"");
      prpTexpenseSchema.setBankDisRate(bankDisRate_PrpTexpense+"");
      prpTexpenseSchema.setFlag("I1");
      blPrpTexpense.setArr(prpTexpenseSchema);    
      return blPrpTexpense;
    }
    
    /**
     * @desc 由接口表数据生成3套XX数据：生成XX单主信息对象BLPrpTmain
     * @param arrCustomerCode
     * @param bankInterFace_DetailDto
     * @param visacode
     * @param arrProposalNo
     * @return blPrpTmain
     * @throws Exception
     */
    public BLPrpTmain generateObjectOfTmain(String[] arrCustomerCode, BankInterFace_DetailDto bankInterFace_DetailDto, String visacode, String arrProposalNo,String IsEiesFlag) throws Exception {
      BLPrpDagreementFacade blPrpDagreementFacade = new BLPrpDagreementFacade();
      PrpDagreementDto prpDagreementDto = null;
      BLPrpTmain blPrpTmain = new BLPrpTmain();
      BLPrpdBankCompannyFacade blPrpdBankCompannyFacade = null;
      ArrayList prpdBankCompannyDtoList = null;
      BLPrpDagreeDetailFacade blPrpDagreeDetailFacade = new BLPrpDagreeDetailFacade();
      PrpTmainSchema prpTmainSchema = new PrpTmainSchema();
      PrpDagreeDetailDto prpDagreeDetailDto = null;
      Collection prpDagreementDtoList = null;
      
      BLPrpDriskConfigFacade blPrpDriskConfigFacade = new BLPrpDriskConfigFacade();
      String strAgreementNo = "";
      String strStartDatePrpT = "";
      String strEndDatePrpT = "";
      String strUploadDate  = "";
      int intYear = 0;
      int intMonth = 0;
      int intDay = 0;
      double dbDisrate = 0;
      
      double dbPolicyFee = 0.0;
      double newDisrate = 0.0;
      
      String strAgentCode = "";
      String strBankAgentCode = "";
      String strUserFor = "";
      
      DBPrpDcompany dbPrpDcompany = new DBPrpDcompany();
      String strChannelType = "";
      int iResult = 0;
      
      blPrpdBankCompannyFacade = new BLPrpdBankCompannyFacade();
      strBankAgentCode = bankInterFace_DetailDto.getBankbranchCode(); 
      strUserFor = bankInterFace_DetailDto.getUseFor();
      if("ABC".equals(IsEiesFlag)||"CCB".equals(IsEiesFlag)){
        strUserFor=bankInterFace_DetailDto.getGrade();
        if("A".equals(strUserFor)){
          strUserFor="01";
        }           
        else if("B".equals(strUserFor)){
          strUserFor="02";
        }           
        else if("C".equals(strUserFor)){
          strUserFor="03";
        }           
      }
      



        
        prpdBankCompannyDtoList = (ArrayList)blPrpdBankCompannyFacade.findByConditions(" BankCode='" + strBankAgentCode + "'");

      
      if(prpdBankCompannyDtoList.size() == 0)
      { errorMessage="BLTransDataYB0302.generateObjectOfCommission"+"prpdbankcompanny表"+strBankAgentCode+"取值失败,请管理员进行配置！";
        throw new UserException(-98, -1167, "BLTransDataYB0302.generateObjectOfCommission",
              "prpdbankcompanny表银行网点代码"+strBankAgentCode+"取值失败,请管理员进行配置！" ); 
      }
      if(prpdBankCompannyDtoList.size() > 0) {
        strAgentCode = ((PrpdBankCompannyDto)prpdBankCompannyDtoList.get(0)).getAgentCode();
      }              
      prpDagreementDtoList = blPrpDagreementFacade.findByConditions("AgentCode='" + strAgentCode + "' and validstatus = '1'");        
      for(Iterator prpDagreementDtoListTmp = prpDagreementDtoList.iterator(); prpDagreementDtoListTmp.hasNext();) {
        prpDagreementDto = (PrpDagreementDto)prpDagreementDtoListTmp.next();
        strAgreementNo = prpDagreementDto.getAgreementNo();
        break;
      } 

      CheckChannelYB checkChannelYB = new CheckChannelYB();
      if(!checkChannelYB.checkChannel(bankInterFace_DetailDto.getComCode(),strAgentCode)){
    	  
    	  errorMessage="BLTransDataYB0302.generateObjectOfTmain"+"业务来源与渠道校验失败，请检查相关配置！";
    	  throw new UserException(-98, -1167, "generateObjectOfTmain",
    	            "业务来源与渠道校验失败，请检查相关配置！" ); 
      }
      
      prpDagreeDetailDto = blPrpDagreeDetailFacade.findByPrimaryKey(strAgreementNo, bankInterFace_DetailDto.getRiskCode(),"0000");
      if(prpDagreeDetailDto==null)
      {errorMessage="BLTransDataYB0302.generateObjectOfTmain"+"手续费比例取值失败,请管理员进行配置！";
        throw new UserException(-98, -1167, "BLTransDataYB0302.generateObjectOfTmain",
        "手续费比例取值失败,请管理员进行配置！" ); 
      }      
      if((String.valueOf(prpDagreeDetailDto.getTopCommission())).trim().equals("")
           ||prpDagreeDetailDto.getTopCommission()<0){
    	  errorMessage="BLTransDataYB0302.generateObjectOfTmain"+"手续费比例取值失败,请管理员进行配置！";
        throw new UserException(-98, -1167, "BLTransDataYB0302.generateObjectOfTmain",
          "手续费比例取值失败,请管理员进行配置！" ); 
      }else
      {
        dbDisrate = prpDagreeDetailDto.getTopCommission(); 
    	
    	dbPolicyFee = prpDagreeDetailDto.getPolicyFee();
    	
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

      intYear = bankInterFace_DetailDto.getInvestDate().getYear();
      intMonth = bankInterFace_DetailDto.getInvestDate().getMonth();
      intDay = bankInterFace_DetailDto.getInvestDate().getDay();
      
      prpTmainSchema.setProposalNo(arrProposalNo);
      prpTmainSchema.setClassCode("03");
      prpTmainSchema.setRiskCode("0302");
      prpTmainSchema.setPolicySort("2");
      
      prpTmainSchema.setBusinessNature("9");
      prpTmainSchema.setLanguage("C");
      prpTmainSchema.setPolicyType("01");       
      prpTmainSchema.setAppliCode(arrCustomerCode[0]);
      prpTmainSchema.setAppliName(bankInterFace_DetailDto.getAppliName());
      prpTmainSchema.setAppliAddress(bankInterFace_DetailDto.getEstateAddress());
      prpTmainSchema.setInsuredCode(arrCustomerCode[1]);
      prpTmainSchema.setInsuredName(bankInterFace_DetailDto.getInsuredName());
      prpTmainSchema.setOperateDate(bankInterFace_DetailDto.getInvestDate().toString());
      prpTmainSchema.setStartDate(strStartDatePrpT);  
      prpTmainSchema.setStartHour("0");
      prpTmainSchema.setEndDate(strEndDatePrpT);
      prpTmainSchema.setEndHour("24");
      prpTmainSchema.setSignDate(bankInterFace_DetailDto.getStartDate().addDay(-1).toString());
      prpTmainSchema.setPureRate("0.0000");
      prpTmainSchema.setDisRate(String.valueOf(dbDisrate));
      prpTmainSchema.setDiscount("100.0000");
      prpTmainSchema.setCurrency(bankInterFace_DetailDto.getCurrency());
      prpTmainSchema.setSumValue("0");       
      if("01".equals(strUserFor)){
        prpTmainSchema.setSumAmount(String.valueOf(500000*bankInterFace_DetailDto.getInvestCount()));
      }else if("02".equals(strUserFor)){
        prpTmainSchema.setSumAmount(String.valueOf(750000*bankInterFace_DetailDto.getInvestCount()));
      }else if("03".equals(strUserFor)){
        prpTmainSchema.setSumAmount(String.valueOf(1200000*bankInterFace_DetailDto.getInvestCount()));
      }
      prpTmainSchema.setPolicyNo(bankInterFace_DetailDto.getPolicyno());
      prpTmainSchema.setSumDiscount("");
      prpTmainSchema.setSumPremium(String.valueOf(bankInterFace_DetailDto.getSumPremium()));
      prpTmainSchema.setSumSubPrem("0");
      prpTmainSchema.setSumQuantity(String.valueOf(bankInterFace_DetailDto.getInvestCount()));
      prpTmainSchema.setAutoTransRenewFlag("1");
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
      prpTmainSchema.setReinsFlag("");
      prpTmainSchema.setAllinsFlag("2");
      prpTmainSchema.setUnderWriteFlag("4");
      prpTmainSchema.setOthFlag("000000YY000000000000");
      prpTmainSchema.setFlag("");
      prpTmainSchema.setDisRate1("0.000");
      prpTmainSchema.setBusinessFlag("0");
      prpTmainSchema.setAgreementNo(strAgreementNo);
      prpTmainSchema.setShareHolderFlag("0");
      prpTmainSchema.setArgueSolution("1");
      prpTmainSchema.setManualType("0");
      prpTmainSchema.setAppliType("1");
    
      prpTmainSchema.setVisaCode(bankInterFace_DetailDto.getVisacode());
      if("ABC".equals(IsEiesFlag)||"CCB".equals(IsEiesFlag)){
        prpTmainSchema.setVisaCode(visacode);
      }
      prpTmainSchema.setUnderWriteCode("");
      prpTmainSchema.setUnderWriteName("");
      
      prpTmainSchema.setUnderWriteEndDate("");

      
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
	          throw new UserException(-98, -1167, "BLTransDataYB0302.generateObjectOfTmain","渠道取值失败,XX归属渠道为N01,子渠道不存在S13!" ); 
          }
       }else{
           if(!"N03".equals(strChannelType)){
	           throw new UserException(-98, -1167, "BLTransDataYB0302.generateObjectOfTmain","渠道取值失败,渠道只能N01、N03!" ); 
           }
       }
      
      
    



    	  

















      
	









		if("1".equals(bankInterFace_DetailDto.getIseiesflag())&&dbPolicyFee!=0.0){
			double tempValue =(dbPolicyFee*100)/bankInterFace_DetailDto.getSumPremium();
			if("1".equals(AppConfig.get("sysconst.SECTION_ACCOUNT_0302_OPEN"))){
				if("103".equals(bankInterFace_DetailDto.getBfbankCode())){
		  		  
		  		  
				  String province = AppConfig.get("sysconst.SECTION_CONDITION.ABC.BANKCODE");
				  String newBankCode = strBankAgentCode.substring(0, 2);
				  
				  String conditionBank = "sysconst.SECTION_CONDITION.ABC.CONDITION.CONDITION_"+newBankCode;
				  
				  double disFee = bankInterFace_DetailDto.getSumPremium()*(dbDisrate/100); 
		  		  if(province.indexOf(newBankCode) > -1){
		  			  if(disFee > Double.parseDouble(AppConfig.get(conditionBank))){
		  				  newDisrate = tempValue+dbDisrate;
		  				  operateFee_PrpTexpense=dbPolicyFee;
		  			  }else{
		  				  newDisrate = dbDisrate;
		  				  operateFee_PrpTexpense=0.0;
		  			  } 
		  		  }else{
		  			  newDisrate = tempValue+dbDisrate;
		  			  operateFee_PrpTexpense=dbPolicyFee;
		  		  }
			    }else{
		  			  newDisrate = tempValue+dbDisrate;
			  		  
		              operateFee_PrpTexpense=dbPolicyFee;
			  	}
			}else{
				newDisrate = tempValue+dbDisrate;
				operateFee_PrpTexpense=dbPolicyFee;
			}
			
			newDisrate = Str.round(newDisrate,4);
			prpTmainSchema.setDisRate(String.valueOf(newDisrate));
	  		bankDisRate_PrpTexpense=dbDisrate;





		} else if("2".equals(bankInterFace_DetailDto.getIseiesflag())){
			
		}


	
      blPrpTmain.setArr(prpTmainSchema);
      return blPrpTmain;
    }

    /**
     * @desc 由接口表数据生成XX数据：生成XX单XX/被XX人信息对象BLPrpTinsured
     * @param arrCustomerCode
     * @param bankInterFace_DetailDto
     * @param arrProposalNo
     * @return blPrpTinsured
     * @throws Exception
     */
    public BLPrpTinsured generateObjectOfTinsured(String[] arrCustomerCode, BankInterFace_DetailDto bankInterFace_DetailDto, String arrProposalNo,String IsEiesFlag) throws Exception {
      BLPrpTinsured blPrpTinsured = new BLPrpTinsured();
      PrpTinsuredSchema prpTinsuredSchema1 = new PrpTinsuredSchema();
      PrpTinsuredSchema prpTinsuredSchema2 = new PrpTinsuredSchema();
      BLPrpDcustomerIdvNew blPrpDcustomerIdvNew= new BLPrpDcustomerIdvNew();
      String strHandler1Code = bankInterFace_DetailDto.getHandler1Code(); 
      String strOperCode = bankInterFace_DetailDto.getHandlerCode(); 
      String strComcode = bankInterFace_DetailDto.getComCode(); 
      prpTinsuredSchema1.setProposalNo(arrProposalNo);
      prpTinsuredSchema1.setRiskCode("0302");
      prpTinsuredSchema1.setSerialNo("1");
      prpTinsuredSchema1.setLanguage("C");
      prpTinsuredSchema1.setInsuredType("1");
      prpTinsuredSchema1.setInsuredName(bankInterFace_DetailDto.getAppliName());
      prpTinsuredSchema1.setInsuredNature("3");
      prpTinsuredSchema1.setInsuredFlag("2");
      
      prpTinsuredSchema1.setInsuredIdentity("");
      prpTinsuredSchema1.setIdentifyType(bankInterFace_DetailDto.getIdType());
      prpTinsuredSchema1.setIdentifyNumber(bankInterFace_DetailDto.getAppidNo());
      prpTinsuredSchema1.setBank(bankInterFace_DetailDto.getBankcode());
      prpTinsuredSchema1.setAccount(bankInterFace_DetailDto.getBfaccountNo());
      prpTinsuredSchema1.setPostCode(bankInterFace_DetailDto.getPostCode());
      prpTinsuredSchema1.setMobile(bankInterFace_DetailDto.getMobileCode());
      
      prpTinsuredSchema1.setBenefitFlag("");
      prpTinsuredSchema1.setBenefitRate("");
      arrCustomerCode[0] = blPrpDcustomerIdvNew.createCustomer(prpTinsuredSchema1, strComcode, strOperCode,strHandler1Code);
      prpTinsuredSchema1.setInsuredCode(arrCustomerCode[0]);
      blPrpTinsured.setArr(prpTinsuredSchema1);
      prpTinsuredSchema2.setProposalNo(arrProposalNo);
      prpTinsuredSchema2.setRiskCode("0302");
      prpTinsuredSchema2.setSerialNo("2");
      prpTinsuredSchema2.setLanguage("C");
      prpTinsuredSchema2.setInsuredType("1");
      prpTinsuredSchema2.setInsuredName(bankInterFace_DetailDto.getInsuredName());
      prpTinsuredSchema2.setInsuredNature("3");
      prpTinsuredSchema2.setInsuredFlag("1");
      prpTinsuredSchema2.setInsuredIdentity("01");
      prpTinsuredSchema2.setBenefitFlag("");
      prpTinsuredSchema2.setBenefitRate("");
      
      prpTinsuredSchema2.setBank(bankInterFace_DetailDto.getBankcode());
      prpTinsuredSchema2.setAccount(bankInterFace_DetailDto.getBfaccountNo());
      prpTinsuredSchema2.setPostCode(bankInterFace_DetailDto.getInsurepostCode());
      
      prpTinsuredSchema2.setMobile(bankInterFace_DetailDto.getMobileCode());
      if("ABC".equals(IsEiesFlag)||"CCB".equals(IsEiesFlag)){
        prpTinsuredSchema2.setIdentifyType(bankInterFace_DetailDto.getInsuredType());
        prpTinsuredSchema2.setIdentifyNumber(bankInterFace_DetailDto.getInsuredNumber());
      }
      arrCustomerCode[1] = blPrpDcustomerIdvNew.createCustomer(prpTinsuredSchema2, strComcode, strOperCode,strHandler1Code);
      prpTinsuredSchema2.setInsuredCode(arrCustomerCode[1]);
      blPrpTinsured.setArr(prpTinsuredSchema2);
      return blPrpTinsured;
    }

    /**
     * @desc 由接口表数据生成XX数据：生成XX单地址信息对象BLPrpTaddress
     * @param bankInterFace_DetailDto
     * @param arrProposalNo
     * @return blPrpTaddress
     * @throws Exception
     */
    public BLPrpTaddress generateObjectOfTaddress(BankInterFace_DetailDto bankInterFace_DetailDto, String arrProposalNo) throws Exception {
      BLPrpTaddress blPrpTaddress = new BLPrpTaddress();
      PrpTaddressSchema prpTaddressSchema = new PrpTaddressSchema();
      prpTaddressSchema.setProposalNo(arrProposalNo);
      prpTaddressSchema.setRiskCode("0302");
      prpTaddressSchema.setAddressNo("1");
      prpTaddressSchema.setAddressName(bankInterFace_DetailDto.getEstateAddress());
      prpTaddressSchema.setAddressCode(bankInterFace_DetailDto.getEstatepostCode());
      blPrpTaddress.setArr(prpTaddressSchema);
      return blPrpTaddress;
    }

    /**
     * @desc 由接口表数据生成XX数据：生成XX单投资信息对象BLPrpTmainInvest
     * @param bankInterFace_DetailDto
     * @param dblInterestRate
     * @param arrProposalNo
     * @return blPrpTmainInvest
     * @throws Exception
     */
    public BLPrpTmainBank generateObjectOfTmainBank(BankInterFace_DetailDto bankInterFace_DetailDto, String arrProposalNo) throws Exception {
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
      prpTmainBankSchema.setProposalNo(arrProposalNo);
      prpTmainBankSchema.setRiskCode("0302");
      prpTmainBankSchema.setClassCode("03");
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
    public BLPrpTitemKind generateObjectOfTitemKind(BankInterFace_DetailDto bankInterFace_DetailDto, String arrProposalNo,String IsEiesFlag) throws Exception {
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
      if("ABC".equals(IsEiesFlag)||"CCB".equals(IsEiesFlag)){
        strUserFor=bankInterFace_DetailDto.getGrade();
        if("A".equals(strUserFor)){
          strUserFor="01";
        }           
        else if("B".equals(strUserFor)){
          strUserFor="02";
        }           
        else if("C".equals(strUserFor)){
          strUserFor="03";
        }           
      }
      prpTitemKindSchema = new PrpTitemKindSchema();
      strKindCode ="001";
      dbPrpDkind.getInfo(strRiskCode,strKindCode);
      strKindName  = dbPrpDkind.getKindCName();   
      strItemCode  = "9998";
      prpTitemKindSchema.setCalculateFlag("Y");  
      prpTitemKindSchema.setFlag(strFlag + Str.space(1-strFlag.length()) + "1");
      prpTitemKindSchema.setProposalNo(arrProposalNo);
      prpTitemKindSchema.setRiskCode("0302");
      prpTitemKindSchema.setItemKindNo("0");
      prpTitemKindSchema.setKindCode(strKindCode);
      prpTitemKindSchema.setKindName(strKindName); 
      prpTitemKindSchema.setItemNo("0"); 
      prpTitemKindSchema.setItemCode(strItemCode);
      prpTitemKindSchema.setItemDetailName("虚拟标的");
      prpTitemKindSchema.setEndDate(strEndDate);
      prpTitemKindSchema.setEndHour("24");
      prpTitemKindSchema.setStartDate(strStartDate);
      prpTitemKindSchema.setStartHour("0");
      prpTitemKindSchema.setCurrency("CNY");
      if("01".equals(strUserFor)){
        prpTitemKindSchema.setAmount(String.valueOf(500000*bankInterFace_DetailDto.getInvestCount()));
      }else if ("02".equals(strUserFor)){
        prpTitemKindSchema.setAmount(String.valueOf(750000*bankInterFace_DetailDto.getInvestCount()));
      }else if ("03".equals(strUserFor)){
        prpTitemKindSchema.setAmount(String.valueOf(1200000*bankInterFace_DetailDto.getInvestCount()));
      }
      prpTitemKindSchema.setRate("");
      prpTitemKindSchema.setShortRate("100.0000");
      prpTitemKindSchema.setShortRateFlag("3");
      prpTitemKindSchema.setPremium(String.valueOf(bankInterFace_DetailDto.getSumPremium()));
      if("ABC".equals(IsEiesFlag)||"CCB".equals(IsEiesFlag)){
        prpTitemKindSchema.setValue(bankInterFace_DetailDto.getInvestCount()+"");
      }
      blPrpTitemKind.setArr(prpTitemKindSchema);
      return blPrpTitemKind ;
     }
    
  
    /**
     * @desc 由接口表数据生成XX数据：生成XX单金额信息对象BLPrpTfee
     * @param bankInterFace_DetailDto

     * @param arrProposalNo
     * @return blPrpTfee
     * @throws Exception
     */
    public BLPrpTfee generateObjectOfTfee(BankInterFace_DetailDto bankInterFace_DetailDto, String arrProposalNo,String IsEiesFlag) throws Exception {
      BLPrpTfee          blPrpTfee            = new BLPrpTfee();
      PrpTfeeSchema      prpTfeeSchema      = new PrpTfeeSchema();       
      String strRiskCode  = "";
      String strUserFor ="";
      strRiskCode     = bankInterFace_DetailDto.getRiskCode();
      strUserFor      = bankInterFace_DetailDto.getUseFor();
      if("ABC".equals(IsEiesFlag)||"CCB".equals(IsEiesFlag)){
        strUserFor=bankInterFace_DetailDto.getGrade();
        if("A".equals(strUserFor)){
          strUserFor="01";
        }           
        else if("B".equals(strUserFor)){
          strUserFor="02";
        }           
        else if("C".equals(strUserFor)){
          strUserFor="03";
        }           
      }
      prpTfeeSchema.setProposalNo(arrProposalNo);
      prpTfeeSchema.setRiskCode(strRiskCode);
      prpTfeeSchema.setCurrency(bankInterFace_DetailDto.getCurrency());
      if("01".equals(strUserFor)){
        prpTfeeSchema.setAmount(String.valueOf(500000*bankInterFace_DetailDto.getInvestCount()));
        prpTfeeSchema.setAmount1(String.valueOf(500000*bankInterFace_DetailDto.getInvestCount()));
        prpTfeeSchema.setAmount2(String.valueOf(500000*bankInterFace_DetailDto.getInvestCount()));
      }else if ("02".equals(strUserFor)){
        prpTfeeSchema.setAmount(String.valueOf(750000*bankInterFace_DetailDto.getInvestCount()));
        prpTfeeSchema.setAmount1(String.valueOf(750000*bankInterFace_DetailDto.getInvestCount()));
        prpTfeeSchema.setAmount2(String.valueOf(750000*bankInterFace_DetailDto.getInvestCount()));
      }else if ("03".equals(strUserFor)){ 
        prpTfeeSchema.setAmount(String.valueOf(1200000*bankInterFace_DetailDto.getInvestCount()));
        prpTfeeSchema.setAmount1(String.valueOf(1200000*bankInterFace_DetailDto.getInvestCount()));
        prpTfeeSchema.setAmount2(String.valueOf(1200000*bankInterFace_DetailDto.getInvestCount()));
      }
      prpTfeeSchema.setPremium(String.valueOf(bankInterFace_DetailDto.getSumPremium()));
      prpTfeeSchema.setCurrency1(bankInterFace_DetailDto.getCurrency());
      prpTfeeSchema.setExchangeRate1("1");
      prpTfeeSchema.setPremium1(String.valueOf(bankInterFace_DetailDto.getSumPremium()));
      prpTfeeSchema.setCurrency2(bankInterFace_DetailDto.getCurrency());
      prpTfeeSchema.setExchangeRate2("1");
      prpTfeeSchema.setPremium2(String.valueOf(bankInterFace_DetailDto.getSumPremium()));
      blPrpTfee.setArr(prpTfeeSchema);
      return blPrpTfee;
    }

    /**
     * @desc 关爱1+家由接口表数据生成XX数据：生成XX单缴费计划信息对象BLPrpTplan
     * @param bankInterFace_DetailDto
     * @param arrProposalNo
     * @return blPrpTplan
     * @throws Exception
     */
    public BLPrpTplan generateObjectOfTplan(BankInterFace_DetailDto bankInterFace_DetailDto,String arrProposalNo) throws Exception {
      BLPrpTplan         blPrpTplan       = new BLPrpTplan();
      PrpTplanSchema     prpTplanSchema = new PrpTplanSchema();
      prpTplanSchema.setProposalNo(arrProposalNo);
      prpTplanSchema.setSerialNo("1");
      prpTplanSchema.setPayNo("1");
      prpTplanSchema.setPayReason("R10");
      prpTplanSchema.setPlanDate(bankInterFace_DetailDto.getInvestDate().toString());
      prpTplanSchema.setCurrency(bankInterFace_DetailDto.getCurrency());
      prpTplanSchema.setPlanFee(String.valueOf(bankInterFace_DetailDto.getSumPremium()));
      prpTplanSchema.setDelinquentFee(String.valueOf(bankInterFace_DetailDto.getSumPremium()));
      prpTplanSchema.setPlanStartDate(bankInterFace_DetailDto.getInvestDate().toString());
      blPrpTplan.setArr(prpTplanSchema);
      return blPrpTplan;
    }
 
    /**
     * 
     * @desc 由接口表数据生成XX数据：生成prpTbatch对象
     * @param bankInterFace_DetailDto
     * @param arrProposalNo
     * @return
     * @throws Exception
     */
    public BLPrpTbatch generateObjectOfTbatch(BankInterFace_DetailDto bankInterFace_DetailDto,String arrProposalNo,String IsEiesFlag,String visacode) throws Exception {
      BLPrpTbatch blprpTbatch = new BLPrpTbatch();
      PrpTbatchSchema prpTbatchSchema = new PrpTbatchSchema();
      prpTbatchSchema.setProposalNo(arrProposalNo);
      prpTbatchSchema.setRiskCode("0302");
      prpTbatchSchema.setSerialNo("1");
      prpTbatchSchema.setVisaCode(bankInterFace_DetailDto.getVisacode());
      if("ABC".equals(IsEiesFlag)||"CCB".equals(IsEiesFlag)){
        prpTbatchSchema.setVisaCode(visacode);
      }
      prpTbatchSchema.setBillStartNo(bankInterFace_DetailDto.getPrintno()); 
      prpTbatchSchema.setBillEndNo(bankInterFace_DetailDto.getPrintno());  
      prpTbatchSchema.setBillCount("1");                                   
      prpTbatchSchema.setVisaStatus("04");
      prpTbatchSchema.setCurrency(bankInterFace_DetailDto.getCurrency());
      blprpTbatch.setArr(prpTbatchSchema);
      return blprpTbatch;
    }

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
