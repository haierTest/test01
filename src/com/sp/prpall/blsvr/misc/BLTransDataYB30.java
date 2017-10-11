package com.sp.prpall.blsvr.misc;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sinosig.schedule.client.dao.MidLogJdbcDao;
import com.sp.platform.bl.action.domain.BLBankInterFace_DetailAction;
import com.sp.platform.bl.action.domain.BLPrpDagreeDetailAction;
import com.sp.platform.bl.action.domain.BLPrpDoutsideAgreedetailAction;
import com.sp.platform.bl.facade.BLPrpDagentFacade;
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
import com.sp.prpall.blsvr.tb.BLPrpTexpense;
import com.sp.prpall.blsvr.tb.BLPrpTfee;
import com.sp.prpall.blsvr.tb.BLPrpTinsured;
import com.sp.prpall.blsvr.tb.BLPrpTinsuredNature;
import com.sp.prpall.blsvr.tb.BLPrpTitemKind;
import com.sp.prpall.blsvr.tb.BLPrpTmain;
import com.sp.prpall.blsvr.tb.BLPrpTmainBank;
import com.sp.prpall.blsvr.tb.BLPrpTmainCasualty;
import com.sp.prpall.blsvr.tb.BLPrpTmainSub;
import com.sp.prpall.blsvr.tb.BLPrpTplan;
import com.sp.prpall.pubfun.Bill;
import com.sp.prpall.pubfun.CheckChannelYB;
import com.sp.prpall.pubfun.eiespublictool.EiesPublicTool;
import com.sp.prpall.schema.PrpTaddressSchema;
import com.sp.prpall.schema.PrpTexpenseSchema;
import com.sp.prpall.schema.PrpTfeeSchema;
import com.sp.prpall.schema.PrpTinsuredNatureSchema;
import com.sp.prpall.schema.PrpTinsuredSchema;
import com.sp.prpall.schema.PrpTitemKindSchema;
import com.sp.prpall.schema.PrpTmainBankSchema;
import com.sp.prpall.schema.PrpTmainCasualtySchema;
import com.sp.prpall.schema.PrpTmainSchema;
import com.sp.prpall.schema.PrpTmainSubSchema;
import com.sp.prpall.schema.PrpTplanSchema;
import com.sp.sysframework.common.datatype.DateTime;
import com.sp.sysframework.reference.AppConfig;
import com.sp.sysframework.reference.DBManager;
import com.sp.utiall.blsvr.BLPrpDcustomerIdvNew;
import com.sp.utiall.blsvr.BLPrpDitem;
import com.sp.utiall.blsvr.BLPrpDkind;
import com.sp.utiall.blsvr.BLPrpDration;
import com.sp.utiall.dbsvr.DBPrpDcompany;
import com.sp.utiall.dbsvr.DBPrpDkind;
import com.sp.utiall.dbsvr.DBPrpDration;
import com.sp.utiall.schema.PrpDrationSchema;
import com.sp.utility.SysConfig;
import com.sp.utility.UtiPower;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.utility.string.ChgDate;
import com.sp.utility.string.Date;
import com.sp.utility.string.Str;

public class BLTransDataYB30 {
  
  String[] arrCustomerCode = new String[4];
 
  public BLTransDataYB30() {
  }
  protected final static Log logger = LogFactory.getLog(BLTransDataYB30.class);
	   

  
  String jobName="中间业务平台转数失败日志记录";
  String comCode ="";
  String jobGroup ="";
  String jobDes="BLTransDataYB30转数失败日志记录";
  String businessKey ="";
  String executeDate   ="";
  String successFlag ="";
  String errorMessage ="";
  String errorStack  ="";
  String createDate  ="";
  String createCode   ="";
  int intYear = 0;
  int intMonth = 0;
  int intDay = 0;
  DateTime Date = new DateTime().current();
  EiesPublicTool eiesPublicTool=new EiesPublicTool();
  
  double operateFee_PrpTexpense=0.0;
  double bankDisRate_PrpTexpense=0.0;
  double operateFee_PrpTexpense0301=0.0;
  double bankDisRate_PrpTexpense0301=0.0;
  double operateFee_PrpTexpense2700=0.0;
  double bankDisRate_PrpTexpense2700=0.0;
  
  
  private String strDisRate = "0.0000"; 
  private String strManageRate = "0.0000"; 
  private String strSaleSalaryRate = "0.0000"; 
  private String strDisRate0301 = "0.0000"; 
  private String strManageRate0301 = "0.0000"; 
  private String strSaleSalaryRate0301 = "0.0000"; 
  private String strDisRate2700 = "0.0000"; 
  private String strManageRate2700 = "0.0000"; 
  private String strSaleSalaryRate2700 = "0.0000"; 
  private String strPolicyFee = "0.0000";
  private String strPolicyFee2700 = "0.0000";
  private String strPolicyFee0301 = "0.0000";
  private boolean blnFeeSwitch = false; 
  private String strAgentCode = "";
  private String strAgreementNo = "";
  
  /**银XXXXX通3006、3007转数
   * @param request
   * @param bankInterFace_DetailDto
   * @param IsEiesFlag
   * @param vscode
   * @throws Exception
   */
  public String transData(DbPool dbPool,String strSessionId,BankInterFace_DetailDto bankInterFace_DetailDto,
                        String isEiesFlag,String vsCode) throws Exception
  {
	logger.info(("执行银XXXXX30XXXXX种核心转数任务开始....................-------------"));
	logger.debug("进入银XXXXX30XXXXX种核心转数...------------");
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
    successFlag ="0";
    errorStack  ="";
   createCode   ="ZJYW_30_admin";
 
    try {
      BLProposal blProposal = null;
      String[] arrProposalNo = new String[3];
      String strProposalNo = "";
      String strProposalNofor0301 = "";
      String strProposalNofor2700 = "";       
      if ("Eies".equals(isEiesFlag)) {
        vsCode = bankInterFace_DetailDto.getVisacode();
      }
    
      String strRiskCode = bankInterFace_DetailDto.getRiskCode();
      


      strProposalNo = bankInterFace_DetailDto.getProposalNo();
      strProposalNofor2700 = getProposalNo(dbPool,bankInterFace_DetailDto.getComCode(), strSessionId, "2700");
      strProposalNofor0301 = getProposalNo(dbPool,bankInterFace_DetailDto.getComCode(), strSessionId,  "0301");
      arrProposalNo[0] = strProposalNo;
      arrProposalNo[1] = strProposalNofor0301;
      arrProposalNo[2] = strProposalNofor2700;
      blProposal = generateObjectOfProposal(dbPool, arrCustomerCode, bankInterFace_DetailDto, vsCode, arrProposalNo,  strRiskCode, isEiesFlag);
      dbPool.beginTransaction();
      blProposal.save(dbPool, "I", false);


      com.sp.undwrt.bl.facade.BLTaskFacade blTaskFacade = new com.sp.undwrt.bl.facade.BLTaskFacade();
      blTaskFacade.start(dbManager,"11", "T", strProposalNo, strRiskCode, "30",
            bankInterFace_DetailDto.getComCode(),  bankInterFace_DetailDto.getComCode(),
            bankInterFace_DetailDto.getHandlerCode(), bankInterFace_DetailDto.getHandlerCode(),
            bankInterFace_DetailDto.getHandler1Code(), "");          
      dbPool.commitTransaction();
      strRevolutionFlag = "1";   
      
    } catch (Exception e) {
    	
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
   * @desc 关爱1+家由接口表数据生成XX数据：生成XX单对象BLProposal
   * @param dbPool
   * @param arrCustomerCode
   * @param bankInterFace_DetailDto
   * @param vscode
   * @param arrProposalNo
   * @return blProposal
   * @throws Exception
   */
    public BLProposal generateObjectOfProposal(DbPool dbPool,String[] arrCustomerCode,
        BankInterFace_DetailDto bankInterFace_DetailDto, String vscode,
      String[] arrProposalNo, String RiskCode, String IsEiesFlag)throws Exception 
    {
      BLProposal blProposal = new BLProposal();
      BLPrpDration blPrpDration = new BLPrpDration();
      BLPrpDration blPrpDration0301 = new BLPrpDration();
      BLPrpDration blPrpDration2700 = new BLPrpDration();
      BLPrpTmain blPrpTmain = null;
      BLPrpTinsured blPrpTinsured = null;
      BLPrpTaddress blPrpTaddress = null;
      BLPrpTmainBank blPrpTmainBank = null;
      BLPrpTmainSub blPrpTmainSub   = null;
      BLPrpTitemKind blPrpTitemKind = null;
      BLPrpTfee blPrpTfee = null;
      BLPrpTplan blPrpTplan = null;
      BLPrpTinsuredNature blPrpTinsuredNature = null;
      BLPrpTmainCasualty blPrpTmainCasualty = null;
      Vector vblPrpDration = null;
      Vector vblPrpDration0301 = null;
      Vector vblPrpDration2700 = null;
      String strRiskCode    = "";
      String strUseFor      = "";  
      String strUserDesc    = "";
      String strRelation    = "";
      String grade="";
      
      
      this.initTransInfo(dbPool, bankInterFace_DetailDto);
      
      
      strRiskCode = bankInterFace_DetailDto.getRiskCode();
      if("ABC".equals(IsEiesFlag)){
        strUseFor   = bankInterFace_DetailDto.getPolicyType();
        strUserDesc = bankInterFace_DetailDto.getPolicyType();
        grade=bankInterFace_DetailDto.getGrade();
      }
      
      else if("CCB".equals(IsEiesFlag)){
          strUseFor   = bankInterFace_DetailDto.getPolicyType();
          strUserDesc = bankInterFace_DetailDto.getPolicyType();
          grade=bankInterFace_DetailDto.getGrade();
      }
      
      else{
        strUseFor   = bankInterFace_DetailDto.getUseFor();
        strUserDesc = bankInterFace_DetailDto.getUserDesc();
      }
      strRelation = bankInterFace_DetailDto.getPhone();
      Vector DrationList = getDration(strRiskCode,strUseFor,strUserDesc,strRelation,IsEiesFlag,grade);
      if("3006".equals(RiskCode)){
        blPrpDration = (BLPrpDration)DrationList.get(0);
        blPrpDration0301 = (BLPrpDration)DrationList.get(1);
        blPrpDration2700 = (BLPrpDration)DrationList.get(2);
      }else{
        vblPrpDration =  (Vector)DrationList.get(0);
        vblPrpDration0301 =  (Vector)DrationList.get(1);
        vblPrpDration2700 =  (Vector)DrationList.get(2);
      }
      
      blPrpTinsured = generateObjectOfTinsured(arrCustomerCode, bankInterFace_DetailDto,arrProposalNo,strRiskCode,IsEiesFlag);
      blPrpTmain = generateObjectOfTmain(arrCustomerCode, bankInterFace_DetailDto,vscode,IsEiesFlag,arrProposalNo,blPrpDration0301,
    		  blPrpDration2700,strRiskCode,vblPrpDration0301,vblPrpDration2700); 
          blPrpTaddress = generateObjectOfTaddress(bankInterFace_DetailDto,arrProposalNo);
      blPrpTmainBank = generateObjectOfTmainBank(bankInterFace_DetailDto,arrProposalNo); 
      
      blPrpTmainSub = generateObjectOfTmainSub(bankInterFace_DetailDto,arrProposalNo); 
      
      if("3006".equals(RiskCode)){
        blPrpTitemKind = generateObjectOf3006TitemKind(bankInterFace_DetailDto, blPrpDration,arrProposalNo,blPrpDration0301,blPrpDration2700);
      }else if ("3007".equals(RiskCode)){
        blPrpTitemKind = generateObjectOf3007TitemKind(bankInterFace_DetailDto, vblPrpDration,arrProposalNo,vblPrpDration0301,vblPrpDration2700);
      }
      
      blPrpTinsuredNature = generateObjectofTinsuredNature(bankInterFace_DetailDto,arrProposalNo,strRiskCode,IsEiesFlag);
      blPrpTfee  = generateObjectOfTfee(bankInterFace_DetailDto,blPrpDration0301,blPrpDration2700,arrProposalNo,strRiskCode,vblPrpDration0301,vblPrpDration2700); 
      
      blPrpTplan = generateObjectOfTplan(bankInterFace_DetailDto, blPrpDration0301,blPrpDration2700,arrProposalNo,strRiskCode,vblPrpDration0301,vblPrpDration2700);
      blPrpTmainCasualty  = generrateObjectofTmainCasualty(bankInterFace_DetailDto,arrProposalNo,strUseFor,strRiskCode,IsEiesFlag,grade);
      
      BLPrpTexpense blPrpTexpense = this.transExpense(bankInterFace_DetailDto, arrProposalNo);
      
     
      blProposal.setBLPrpTmain(blPrpTmain);
      blProposal.setBLPrpTinsured(blPrpTinsured);
      blProposal.setBLPrpTaddress(blPrpTaddress);
      blProposal.setBLPrpTmainBank(blPrpTmainBank);
      blProposal.setBLPrpTmainSub(blPrpTmainSub);
      blProposal.setBLPrpTitemKind(blPrpTitemKind);
      blProposal.setBLPrpTinsuredNature(blPrpTinsuredNature);
      blProposal.setBLPrpTfee(blPrpTfee);
      blProposal.setBLPrpTplan(blPrpTplan);
      blProposal.setBLPrpTmainCasualty(blPrpTmainCasualty);
      
      blProposal.setBLPrpTexpense(blPrpTexpense);
      
      PrpTitemKindSchema   premitemkindschema = null;
      PrpTmainSchema   premtmainschema = null;
      PrpTfeeSchema   premtfeeschema = null;
      String proposalno = "";
      double sumamount = 0.00;

      for(int k = 0; k<blPrpTitemKind.getSize();k++){
        premitemkindschema = blPrpTitemKind.getArr(k);
        if((premitemkindschema.getRiskCode().equals("3006")||premitemkindschema.getRiskCode().equals("3007"))&&premitemkindschema.getCalculateFlag().equals("Y")){
          sumamount = sumamount + Double.parseDouble(premitemkindschema.getAmount());
        }
      }
      for(int m=0;m<blPrpTmain.getSize();m++){
        premtmainschema = blPrpTmain.getArr(m);
        if(premtmainschema.getRiskCode().equals("3006")||premtmainschema.getRiskCode().equals("3007")){
          blPrpTmain.getArr(m).setSumAmount(String.valueOf(sumamount));
          proposalno= blPrpTmain.getArr(m).getProposalNo();
          blProposal.setBLPrpTmain(blPrpTmain);
          break;
        }
      }
      for(int n=0;n<blPrpTfee.getSize();n++){
        premtfeeschema = blPrpTfee.getArr(n);
        if(premtfeeschema.getProposalNo().equals(proposalno)){
          blPrpTfee.getArr(n).setAmount(String.valueOf(sumamount));
          blPrpTfee.getArr(n).setAmount1(String.valueOf(sumamount));
          blPrpTfee.getArr(n).setAmount2(String.valueOf(sumamount));
          blProposal.setBLPrpTfee(blPrpTfee);
          break;
        }
      }
      return blProposal;
    }
    

    /**
     * @desc 关爱1+家由接口表数据生成3套XX数据：生成XX单主信息对象BLPrpTmain
     * @param arrCustomerCode
     * @param bankInterFace_DetailDto
     * @param visacode
     * @param arrProposalNo
     * @param blPrpDration0301
     * @param blPrpDration2700
     * @return blPrpTmain
     * @throws Exception
     */
    public BLPrpTmain generateObjectOfTmain(String[] arrCustomerCode, BankInterFace_DetailDto bankInterFace_DetailDto, String visacode,String IsEiesFlag, String[] arrProposalNo,BLPrpDration blPrpDration0301,BLPrpDration blPrpDration2700 ,String strRiskCode,Vector vblPrpDration0301,Vector vblPrpDration2700) throws Exception {
      


      BLPrpTmain blPrpTmain = new BLPrpTmain();


      PrpTmainSchema prpTmainSchema = new PrpTmainSchema();
      PrpTmainSchema prpTmainSchemafor0301 = new PrpTmainSchema();
      PrpTmainSchema prpTmainSchemafor2700 = new PrpTmainSchema();
      PrpDrationSchema prpDrationSchema0301 = new PrpDrationSchema();
      PrpDrationSchema prpDrationSchema2700 = new PrpDrationSchema();



      
      DateTime currentDate = new DateTime().current();
      

      
    
      BLPrpDriskConfigFacade blPrpDriskConfigFacade = new BLPrpDriskConfigFacade();
      String strStartDatePrpT = "";
      String strEndDatePrpT = "";
      String strUploadDate  = "";
      String strCurrentDate = "";
      String strUseFor = "";      
      String strPolicyType ="";
      String strUserDesc = "";
      double dblPremium0301 = 0.0;
      double dblAmount0301  = 0.0;
      double dblPremium2700 = 0.0;
      double dblAmount2700  = 0.0;
      double dbDisrate = 0;
      
      double dbPolicyFee = 0.0;
      double newDisrate = 0.0;
      
      int intYear = 0;
      int intMonth = 0;
      int intDay = 0;
      
      DBPrpDcompany dbPrpDcompany = new DBPrpDcompany();
      String strChannelType = "";
      int iResult = 0;
      
      if("3006".equals(strRiskCode)){
        strUseFor   = bankInterFace_DetailDto.getUseFor();
        if(strUseFor.equals("01")||strUseFor.equals("25"))
        {
          strPolicyType="25"; 
        }     
        else if(strUseFor.equals("02")||strUseFor.equals("26"))
        {
          strPolicyType="26"; 
        }        
        else if(strUseFor.equals("03")||strUseFor.equals("27"))
        {
          strPolicyType="27"; 
        }
      }else if("3007".equals(strRiskCode)) {
        strUserDesc = bankInterFace_DetailDto.getUserDesc();
        if(strUserDesc.equals("25"))
        {
          strPolicyType="25"; 
        }     
        else if(strUserDesc.equals("26"))
        {
          strPolicyType="26"; 
        }        
        else if(strUserDesc.equals("27"))
        {
          strPolicyType="27"; 
        }
      }
        
        if("ABC".equals(IsEiesFlag)||"CCB".equals(IsEiesFlag)){
        
        strPolicyType=bankInterFace_DetailDto.getPolicyType();
      }
      



























      
     
        logger.info(("执行银XXXXX30XXXXX种核心转数任务...生成XX单数据前校验.................-------------"));
    	logger.debug("进入银XXXXX30XXXXX种核心转数...------校验------");
      CheckChannelYB checkChannelYB = new CheckChannelYB();
      if(!checkChannelYB.checkChannel(bankInterFace_DetailDto.getComCode(),strAgentCode)){
    	  errorMessage="BLTransDataYB30.generateObjectOfTmain"+"业务来源与渠道校验失败，请检查相关配置！";
    	  throw new UserException(-98, -1167, "generateObjectOfTmain",
    	            "业务来源与渠道校验失败，请检查相关配置！" ); 
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
      if("3006".equals(strRiskCode)){
        if(blPrpDration0301.getSize()>0)
        {
          for (int i=0;i<blPrpDration0301.getSize();i++)
          {
            prpDrationSchema0301 = blPrpDration0301.getArr(i);
            dblPremium0301  =  dblPremium0301 + Double.parseDouble(Str.chgStrZero(prpDrationSchema0301.getPremium()));
            if("001".equals(prpDrationSchema0301.getKindCode()))
              dblAmount0301   =  dblAmount0301  + Double.parseDouble(Str.chgStrZero(prpDrationSchema0301.getAmount()));
            }
        }
        if(blPrpDration2700.getSize()>0)
        {
          for (int i=0;i<blPrpDration2700.getSize();i++)
          {
            prpDrationSchema2700 = blPrpDration2700.getArr(i);
            dblPremium2700  =  dblPremium2700 + Double.parseDouble(Str.chgStrZero(prpDrationSchema2700.getPremium()));
            
            if(!"0".equals(prpDrationSchema2700.getAmountFlag()))
            	dblAmount2700   =  dblAmount2700  + Double.parseDouble(Str.chgStrZero(prpDrationSchema2700.getAmount()));
            
            
          }
        }
      }else if ("3007".equals(strRiskCode)){
        if(vblPrpDration0301.size()>0){
          for (int i=0;i<vblPrpDration0301.size();i++){
            Vector prmblPrpDration0301 = (Vector)vblPrpDration0301.get(i);
            for (int j=0;j<prmblPrpDration0301.size();j++)
            {
              prpDrationSchema0301 = (PrpDrationSchema)prmblPrpDration0301.get(j);
              dblPremium0301  =  dblPremium0301 + Double.parseDouble(Str.chgStrZero(prpDrationSchema0301.getPremium()));
              if("001".equals(prpDrationSchema0301.getKindCode()))
              dblAmount0301   =  dblAmount0301  + Double.parseDouble(Str.chgStrZero(prpDrationSchema0301.getAmount()));
            }
          }
        }
        if(vblPrpDration2700.size()>0){
          for (int i=0;i<vblPrpDration2700.size();i++){
            Vector prmblPrpDration2700 = (Vector)vblPrpDration2700.get(i);
            for (int j=0;j<prmblPrpDration2700.size();j++)
            {
              prpDrationSchema2700 = (PrpDrationSchema)prmblPrpDration2700.get(j);
              dblPremium2700  =  dblPremium2700 + Double.parseDouble(Str.chgStrZero(prpDrationSchema2700.getPremium()));
              dblAmount2700   =  dblAmount2700  + Double.parseDouble(Str.chgStrZero(prpDrationSchema2700.getAmount()));
            }
          }
        }
      }
      prpTmainSchema.setProposalNo(bankInterFace_DetailDto.getProposalNo());        
      prpTmainSchemafor0301.setProposalNo(arrProposalNo[1]);
      prpTmainSchemafor2700.setProposalNo(arrProposalNo[2]);
      prpTmainSchema.setClassCode(bankInterFace_DetailDto.getClassCode());
      prpTmainSchemafor0301.setClassCode("03");
      prpTmainSchemafor2700.setClassCode("27");
      prpTmainSchema.setRiskCode(bankInterFace_DetailDto.getRiskCode());
      prpTmainSchemafor0301.setRiskCode("0301");
      prpTmainSchemafor2700.setRiskCode("2700");        
      prpTmainSchema.setPolicySort("4"); 
      prpTmainSchemafor0301.setPolicySort("4");
      prpTmainSchemafor2700.setPolicySort("4");
      
      prpTmainSchema.setBusinessNature("9"); 
      prpTmainSchemafor0301.setBusinessNature("9");
      prpTmainSchemafor2700.setBusinessNature("9");
      prpTmainSchema.setLanguage("C");
      prpTmainSchemafor0301.setLanguage("C");
      prpTmainSchemafor2700.setLanguage("C");
      prpTmainSchema.setPolicyType(strPolicyType); 
      prpTmainSchemafor0301.setPolicyType(strPolicyType);
      prpTmainSchemafor2700.setPolicyType(strPolicyType);
      prpTmainSchema.setAppliCode(arrCustomerCode[0]);
      prpTmainSchemafor0301.setAppliCode(arrCustomerCode[0]);
      prpTmainSchemafor2700.setAppliCode(arrCustomerCode[0]);        
      prpTmainSchema.setAppliName(bankInterFace_DetailDto.getAppliName());
      prpTmainSchemafor0301.setAppliName(bankInterFace_DetailDto.getAppliName());
      prpTmainSchemafor2700.setAppliName(bankInterFace_DetailDto.getAppliName());
      prpTmainSchema.setAppliAddress(bankInterFace_DetailDto.getEstateAddress());
      prpTmainSchemafor0301.setAppliAddress(bankInterFace_DetailDto.getEstateAddress());
      prpTmainSchemafor2700.setAppliAddress(bankInterFace_DetailDto.getEstateAddress());
      prpTmainSchema.setInsuredCode(arrCustomerCode[1]);
      prpTmainSchemafor0301.setInsuredCode(arrCustomerCode[1]);
      prpTmainSchemafor2700.setInsuredCode(arrCustomerCode[1]);        
      prpTmainSchema.setInsuredName(bankInterFace_DetailDto.getInsuredName());
      prpTmainSchemafor0301.setInsuredName(bankInterFace_DetailDto.getInsuredName());
      prpTmainSchemafor2700.setInsuredName(bankInterFace_DetailDto.getInsuredName());        
      prpTmainSchema.setOperateDate(bankInterFace_DetailDto.getInvestDate().toString());
      prpTmainSchemafor0301.setOperateDate(bankInterFace_DetailDto.getInvestDate().toString());
      prpTmainSchemafor2700.setOperateDate(bankInterFace_DetailDto.getInvestDate().toString());        
      prpTmainSchema.setStartDate(strStartDatePrpT); 
      prpTmainSchemafor0301.setStartDate(strStartDatePrpT); 
      prpTmainSchemafor2700.setStartDate(strStartDatePrpT);         
      prpTmainSchema.setStartHour("0");
      prpTmainSchemafor0301.setStartHour("0");
      prpTmainSchemafor2700.setStartHour("0"); 
      prpTmainSchema.setEndDate(strEndDatePrpT);
      prpTmainSchemafor0301.setEndDate(strEndDatePrpT);
      prpTmainSchemafor2700.setEndDate(strEndDatePrpT);
      prpTmainSchema.setEndHour("24");
      prpTmainSchemafor0301.setEndHour("24");
      prpTmainSchemafor2700.setEndHour("24");        
      prpTmainSchema.setSignDate(bankInterFace_DetailDto.getStartDate().addDay(-1).toString()); 
      prpTmainSchemafor0301.setSignDate(bankInterFace_DetailDto.getStartDate().addDay(-1).toString());
      prpTmainSchemafor2700.setSignDate(bankInterFace_DetailDto.getStartDate().addDay(-1).toString());
      prpTmainSchema.setPureRate("0.00");
      prpTmainSchemafor0301.setPureRate("0.00");
      prpTmainSchemafor2700.setPureRate("0.00");
      



      prpTmainSchema.setDisRate(strDisRate);
      prpTmainSchemafor0301.setDisRate(strDisRate0301);
      prpTmainSchemafor2700.setDisRate(strDisRate2700);
      
      prpTmainSchema.setDiscount("100");
      prpTmainSchemafor0301.setDiscount("100");
      prpTmainSchemafor2700.setDiscount("100");        
      prpTmainSchema.setCurrency(bankInterFace_DetailDto.getCurrency());
      prpTmainSchemafor0301.setCurrency(bankInterFace_DetailDto.getCurrency());
      prpTmainSchemafor2700.setCurrency(bankInterFace_DetailDto.getCurrency());
      prpTmainSchema.setSumValue("0");
      prpTmainSchemafor0301.setSumValue("0");
      prpTmainSchemafor2700.setSumValue("0");        
      prpTmainSchema.setSumAmount(String.valueOf(bankInterFace_DetailDto.getSumamount()));
      prpTmainSchemafor0301.setSumAmount(String.valueOf(dblAmount0301*bankInterFace_DetailDto.getInvestCount()));
      prpTmainSchemafor2700.setSumAmount(String.valueOf(dblAmount2700*bankInterFace_DetailDto.getInvestCount()));
      prpTmainSchema.setSumDiscount("0");
      prpTmainSchemafor0301.setSumDiscount("0");
      prpTmainSchemafor2700.setSumDiscount("0");        
      prpTmainSchema.setSumPremium(String.valueOf(bankInterFace_DetailDto.getSumPremium()));
      prpTmainSchemafor0301.setSumPremium(String.valueOf(dblPremium0301*bankInterFace_DetailDto.getInvestCount()));
      prpTmainSchemafor2700.setSumPremium(String.valueOf(dblPremium2700*bankInterFace_DetailDto.getInvestCount()));        
      prpTmainSchema.setSumSubPrem("0");
      prpTmainSchemafor0301.setSumSubPrem("0");
      prpTmainSchemafor2700.setSumSubPrem("0"); 
      if("25".equals(strPolicyType)){
        prpTmainSchema.setSumQuantity("1");
        prpTmainSchemafor0301.setSumQuantity("1");
        prpTmainSchemafor2700.setSumQuantity("1");        
      }else if("26".equals(strPolicyType)){
        prpTmainSchema.setSumQuantity("2");
        prpTmainSchemafor0301.setSumQuantity("2");
        prpTmainSchemafor2700.setSumQuantity("2");
      }else{
        prpTmainSchema.setSumQuantity("3");
        prpTmainSchemafor0301.setSumQuantity("3");
        prpTmainSchemafor2700.setSumQuantity("3");
      }
      prpTmainSchema.setAutoTransRenewFlag("2");
      prpTmainSchemafor0301.setAutoTransRenewFlag("2");
      prpTmainSchemafor2700.setAutoTransRenewFlag("2");        
      prpTmainSchema.setPayTimes("1");
      prpTmainSchemafor0301.setPayTimes("1");
      prpTmainSchemafor2700.setPayTimes("1");        
      prpTmainSchema.setEndorseTimes("0");
      prpTmainSchemafor0301.setEndorseTimes("0");
      prpTmainSchemafor2700.setEndorseTimes("0");
      prpTmainSchema.setClaimTimes("0");
      prpTmainSchemafor0301.setClaimTimes("0");
      prpTmainSchemafor2700.setClaimTimes("0");        
      prpTmainSchema.setMakeCom(bankInterFace_DetailDto.getMakeCom());
      prpTmainSchemafor0301.setMakeCom(bankInterFace_DetailDto.getMakeCom());
      prpTmainSchemafor2700.setMakeCom(bankInterFace_DetailDto.getMakeCom());  
      
      
      
      
      prpTmainSchema.setOperateSite("YBT");
      prpTmainSchemafor0301.setOperateSite("YBT");
      prpTmainSchemafor2700.setOperateSite("YBT"); 
      prpTmainSchema.setComCode(bankInterFace_DetailDto.getComCode());
      prpTmainSchemafor0301.setComCode(bankInterFace_DetailDto.getComCode());
      prpTmainSchemafor2700.setComCode(bankInterFace_DetailDto.getComCode());        
      prpTmainSchema.setHandlerCode(bankInterFace_DetailDto.getHandlerCode());
      prpTmainSchemafor0301.setHandlerCode(bankInterFace_DetailDto.getHandlerCode());
      prpTmainSchemafor2700.setHandlerCode(bankInterFace_DetailDto.getHandlerCode());        
      prpTmainSchema.setHandler1Code(bankInterFace_DetailDto.getHandler1Code()); 
      prpTmainSchemafor0301.setHandler1Code(bankInterFace_DetailDto.getHandler1Code());
      prpTmainSchemafor2700.setHandler1Code(bankInterFace_DetailDto.getHandler1Code());        
      prpTmainSchema.setOperatorCode(bankInterFace_DetailDto.getHandlerCode());
      prpTmainSchemafor0301.setOperatorCode(bankInterFace_DetailDto.getHandlerCode());
      prpTmainSchemafor2700.setOperatorCode(bankInterFace_DetailDto.getHandlerCode());
      
      prpTmainSchema.setInputDate(strUploadDate); 
      prpTmainSchemafor0301.setInputDate(strUploadDate);
      prpTmainSchemafor2700.setInputDate(strUploadDate);        
      prpTmainSchema.setInputHour("12");
      prpTmainSchemafor0301.setInputHour("12");
      prpTmainSchemafor2700.setInputHour("12");
      
      prpTmainSchema.setAgentCode(strAgentCode);
      prpTmainSchemafor0301.setAgentCode(strAgentCode);
      prpTmainSchemafor2700.setAgentCode(strAgentCode);
      prpTmainSchema.setCoinsFlag("0");
      prpTmainSchemafor0301.setCoinsFlag("0");
      prpTmainSchemafor2700.setCoinsFlag("0");
      prpTmainSchema.setReinsFlag("0");
      prpTmainSchemafor0301.setReinsFlag("0");
      prpTmainSchemafor2700.setReinsFlag("0");
      prpTmainSchema.setAllinsFlag("2");
      prpTmainSchemafor0301.setAllinsFlag("2");
      prpTmainSchemafor2700.setAllinsFlag("2");
      prpTmainSchema.setUnderWriteFlag("3");
      prpTmainSchemafor0301.setUnderWriteFlag("3");
      prpTmainSchemafor2700.setUnderWriteFlag("3");
      prpTmainSchema.setOthFlag("000000YY000000000000");
      prpTmainSchemafor0301.setOthFlag("000000YY000000000000");
      prpTmainSchemafor2700.setOthFlag("000000YY000000000000");
      prpTmainSchema.setFlag("");
      prpTmainSchemafor0301.setFlag("");
      prpTmainSchemafor2700.setFlag("");
      prpTmainSchema.setDisRate1("0");
      prpTmainSchemafor0301.setDisRate1("0");
      prpTmainSchemafor2700.setDisRate1("0");
      prpTmainSchema.setBusinessFlag("0");
      prpTmainSchemafor0301.setBusinessFlag("0");
      prpTmainSchemafor2700.setBusinessFlag("0");
      prpTmainSchema.setAgreementNo(strAgreementNo);
      prpTmainSchemafor0301.setAgreementNo(strAgreementNo);
      prpTmainSchemafor2700.setAgreementNo(strAgreementNo);
      prpTmainSchema.setAppliType("1");
      prpTmainSchemafor0301.setAppliType("1");
      prpTmainSchemafor2700.setAppliType("1");
      prpTmainSchema.setShareHolderFlag("0");
      prpTmainSchema.setArgueSolution("1");
      prpTmainSchemafor0301.setShareHolderFlag("0");
      prpTmainSchemafor0301.setArgueSolution("1");
      prpTmainSchemafor2700.setShareHolderFlag("0");
      prpTmainSchemafor2700.setArgueSolution("1");

      
      prpTmainSchema.setPolicyNo(bankInterFace_DetailDto.getPolicyno());
      
      prpTmainSchema.setManualType("1");
      prpTmainSchemafor0301.setManualType("0");
      prpTmainSchemafor2700.setManualType("0");
      prpTmainSchema.setVisaCode(visacode); 
      prpTmainSchemafor0301.setVisaCode(visacode);
      prpTmainSchemafor2700.setVisaCode(visacode);
      prpTmainSchema.setUnderWriteCode("99999999");
      prpTmainSchemafor0301.setUnderWriteCode("99999999");
      prpTmainSchemafor2700.setUnderWriteCode("99999999");
      prpTmainSchema.setUnderWriteName("自动核XXXXX");
      prpTmainSchemafor0301.setUnderWriteName("自动核XXXXX");
      prpTmainSchemafor2700.setUnderWriteName("自动核XXXXX");
      prpTmainSchema.setManualType("0");
      prpTmainSchemafor0301.setManualType("0");
      prpTmainSchemafor2700.setManualType("0");        
      
      prpTmainSchema.setUnderWriteEndDate(strCurrentDate);
      prpTmainSchemafor0301.setUnderWriteEndDate(strCurrentDate);
      prpTmainSchemafor2700.setUnderWriteEndDate(strCurrentDate);  
      if(!"WYEies".equals(IsEiesFlag)){
    	  prpTmainSchema.setPrintNo(bankInterFace_DetailDto.getPrintno());
      }
      
      
      if(strRiskCode.equals("3006")){
        prpTmainSchemafor2700.setProductCode("P00197");
      }else{
    	  
        prpTmainSchemafor2700.setProductCode("P00198");
      }
      
      iResult = dbPrpDcompany.getInfo(bankInterFace_DetailDto.getComCode());
      if(iResult == 0)
      {
        strChannelType = dbPrpDcompany.getChannelType();
      }
      prpTmainSchema.setChannelType(strChannelType);
      prpTmainSchemafor0301.setChannelType(strChannelType);
      prpTmainSchemafor2700.setChannelType(strChannelType);
      
      
      
      if("N01".equals(strChannelType) && "5".equals(dbPrpDcompany.getComLevel())){
          String strChannelTypesubSwitch = "CHANNELTYPESUB_SWTICH_N01";
          PrpDriskConfigDto prpDriskConfigDto = new PrpDriskConfigDto();
          UIPrpDriskConfigAction uiPrpDriskConfigAction = new UIPrpDriskConfigAction();
          prpDriskConfigDto=uiPrpDriskConfigAction.queryRiskConfig(bankInterFace_DetailDto.getComCode(),"0000",strChannelTypesubSwitch);
          if(prpDriskConfigDto!=null && prpDriskConfigDto.getConfigValue().indexOf("S13")>-1){
              prpTmainSchema.setChannelTypeSub("S13");
              prpTmainSchemafor0301.setChannelTypeSub("S13");
              prpTmainSchemafor2700.setChannelTypeSub("S13");
          }else{
	          throw new UserException(-98, -1167, "BLTransDataYB30.generateObjectOfTmain","渠道取值失败,XX归属渠道为N01,子渠道不存在S13!" ); 
          }
       }else{
           if(!"N03".equals(strChannelType)){
	           throw new UserException(-98, -1167, "BLTransDataYB30.generateObjectOfTmain","渠道取值失败,渠道只能N01、N03!" ); 
           }
       }
      
      
      
      
      
    






          



















      
		











			if("1".equals(bankInterFace_DetailDto.getIseiesflag())&&dbPolicyFee!=0.0){
				







				logger.info(("执行银XXXXX30XXXXX种核心转数任务...生成XX单数据前封装费用信息.................-------------"));
		    	logger.debug("进入银XXXXX30XXXXX种核心转数...------封装费用信息------");
				
				double tempValue = 0.0d;
				if(strDisRate != null && !"".equals(strDisRate))
				{
					dbDisrate = Double.parseDouble(strDisRate);
				}
				if(strPolicyFee != null && !"".equals(strPolicyFee))
				{
					dbPolicyFee = Double.parseDouble(strPolicyFee);
				}
				tempValue = (dbPolicyFee*100)/bankInterFace_DetailDto.getSumPremium();
				
				
				if("1".equals(AppConfig.get("sysconst.SECTION_ACCOUNT_30_OPEN"))){
					if("103".equals(bankInterFace_DetailDto.getBfbankCode())){
						String province = AppConfig.get("sysconst.SECTION_CONDITION.ABC.BANKCODE");
						String newBankCode = bankInterFace_DetailDto.getBankbranchCode().substring(0, 2);
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
				
				
				bankDisRate_PrpTexpense=dbDisrate;





				prpTmainSchema.setDisRate(String.valueOf(newDisrate));
				
				
				
				tempValue = 0.0d;
				newDisrate = 0.0d;
				dbDisrate = 0.0d;
				if(strDisRate0301 != null && !"".equals(strDisRate0301))
				{
					dbDisrate = Double.parseDouble(strDisRate0301);
				}
				if(strPolicyFee0301 != null && !"".equals(strPolicyFee0301))
				{
					dbPolicyFee = Double.parseDouble(strPolicyFee0301);
				}
				tempValue = (dbPolicyFee*100)/bankInterFace_DetailDto.getSumPremium();
				newDisrate = tempValue+dbDisrate;
				
				operateFee_PrpTexpense0301=dbPolicyFee;
				bankDisRate_PrpTexpense0301=dbDisrate;





				prpTmainSchemafor0301.setDisRate(String.valueOf(newDisrate));
				
				
				tempValue = 0.0d;
				newDisrate = 0.0d;
				dbDisrate = 0.0d;
				if(strDisRate2700 != null && !"".equals(strDisRate2700))
				{
					dbDisrate = Double.parseDouble(strDisRate2700);
				}
				if(strPolicyFee2700 != null && !"".equals(strPolicyFee2700))
				{
					dbPolicyFee = Double.parseDouble(strPolicyFee2700);
				}
				tempValue = (dbPolicyFee*100)/bankInterFace_DetailDto.getSumPremium();
				newDisrate = tempValue+dbDisrate;
				operateFee_PrpTexpense2700=dbPolicyFee;
				bankDisRate_PrpTexpense2700=dbDisrate;





				prpTmainSchemafor2700.setDisRate(String.valueOf(newDisrate));
				
			} else if("2".equals(bankInterFace_DetailDto.getIseiesflag())){
				
				
			}


		
      blPrpTmain.setArr(prpTmainSchema);
      blPrpTmain.setArr(prpTmainSchemafor0301);
      blPrpTmain.setArr(prpTmainSchemafor2700);
      prpTmainSchema.setUnderWriteFlag("4");
      return blPrpTmain;
     }

    /**
     * @desc 关爱1+家由接口表数据生成XX数据：生成XX单XX/被XX人信息对象BLPrpTinsured
     * @param arrCustomerCode
     * @param bankInterFace_DetailDto
     * @param arrProposalNo
     * @return blPrpTinsured
     * @throws Exception
     */
    public BLPrpTinsured generateObjectOfTinsured(String[] arrCustomerCode, BankInterFace_DetailDto bankInterFace_DetailDto, String[] arrProposalNo, String strRiskCode,String IsEiesFlag) throws Exception {
      BLPrpTinsured blPrpTinsured = new BLPrpTinsured();
      PrpTinsuredSchema prpTinsuredSchema1 = new PrpTinsuredSchema();
      PrpTinsuredSchema prpTinsuredSchema1for0301 = new PrpTinsuredSchema();
      PrpTinsuredSchema prpTinsuredSchema1for2700 = new PrpTinsuredSchema();
      PrpTinsuredSchema prpTinsuredSchema2 = new PrpTinsuredSchema();
      PrpTinsuredSchema prpTinsuredSchema2for0301 = new PrpTinsuredSchema();
      PrpTinsuredSchema prpTinsuredSchema2for2700 = new PrpTinsuredSchema();
      PrpTinsuredSchema prpTinsuredSchema3 = new PrpTinsuredSchema();
      PrpTinsuredSchema prpTinsuredSchema3for0301 = new PrpTinsuredSchema();
      PrpTinsuredSchema prpTinsuredSchema3for2700 = new PrpTinsuredSchema();
      PrpTinsuredSchema prpTinsuredSchema4 = new PrpTinsuredSchema();
      PrpTinsuredSchema prpTinsuredSchema4for0301 = new PrpTinsuredSchema();
      PrpTinsuredSchema prpTinsuredSchema4for2700 = new PrpTinsuredSchema();
      BLPrpDcustomerIdvNew blPrpDcustomerIdvNew= new BLPrpDcustomerIdvNew();
      String strHandler1Code = bankInterFace_DetailDto.getHandler1Code(); 
      String strOperCode = bankInterFace_DetailDto.getHandlerCode(); 
      String strComcode = bankInterFace_DetailDto.getComCode(); 
      boolean chooseTo1 = false; 
      boolean chooseTo2 = false; 
      boolean chooseTo3 = false; 
      String  strUseFor = "";
      String  strPhone =  "";
      String  strInsuredIdentity = "";
      String  strInsuredCode = "";
      String strUserDesc = "";
      strUseFor = bankInterFace_DetailDto.getUseFor();
      strPhone = bankInterFace_DetailDto.getPhone(); 
      strUserDesc = bankInterFace_DetailDto.getUserDesc();
      if("本人".equals(strPhone)){
        strInsuredCode = arrCustomerCode[1];
        strInsuredIdentity = "01";
      }else if("配偶".equals(strPhone)){
        strInsuredCode = arrCustomerCode[2];
        strInsuredIdentity = "10";
      }else if("子女".equals(strPhone)){
        strInsuredCode = arrCustomerCode[3];
        strInsuredIdentity = "40";
      }
        
        if("ABC".equals(IsEiesFlag)||"CCB".equals(IsEiesFlag)){
        
        strUseFor = bankInterFace_DetailDto.getPolicyType();
        strPhone = bankInterFace_DetailDto.getRelation();
        strUserDesc = bankInterFace_DetailDto.getPolicyType();
        String relation= bankInterFace_DetailDto.getRelation();
        if("1".equals(relation)){
          strInsuredIdentity="01";
          strInsuredCode = arrCustomerCode[1];
        }else if("2".equals(relation)||"3".equals(relation)){
          strInsuredCode = arrCustomerCode[2];
          strInsuredIdentity="10";
        }else if("6".equals(relation)||"7".equals(relation)){
          strInsuredCode = arrCustomerCode[3];
          strInsuredIdentity = "40";
        }else if("4".equals(relation)){
          strInsuredCode = arrCustomerCode[1];
          strInsuredIdentity = "51";
        }else if("5".equals(relation)){
          strInsuredCode = arrCustomerCode[1];
          strInsuredIdentity = "52";
        }else if("23".equals(relation)){
          strInsuredCode = arrCustomerCode[1];
          strInsuredIdentity = "53";
        }else if("24".equals(relation)){
          strInsuredCode = arrCustomerCode[1];
          strInsuredIdentity = "54";
        }else if("20".equals(relation)){
          strInsuredCode = arrCustomerCode[1];
          strInsuredIdentity = "55";
        }else if("21".equals(relation)){
          strInsuredCode = arrCustomerCode[1];
          strInsuredIdentity = "56";
        }else {
          strInsuredCode = arrCustomerCode[1];
          strInsuredIdentity= "99";
        }
      }
      if("3006".equals(strRiskCode)){
            
            if("ABC".equals(IsEiesFlag)||"CCB".equals(IsEiesFlag)){
            
          if("25".equals(strUseFor)){
            chooseTo1 = true;
          }else if("26".equals(strUseFor)){
            chooseTo1 = true;
            chooseTo2 = true;
          }else if("27".equals(strUseFor)){
            chooseTo1 = true;
            chooseTo2 = true;
            chooseTo3 = true;
          }
        }else{
          if("01".equals(strUseFor)){
            chooseTo1 = true;
          }else if("02".equals(strUseFor)){
            chooseTo1 = true;
            chooseTo2 = true;
          }else if("03".equals(strUseFor)){
            chooseTo1 = true;
            chooseTo2 = true;
            chooseTo3 = true;
          }
        }
      }else if("3007".equals(strRiskCode)){
        if("25".equals(strUserDesc)){
           chooseTo1 = true;
        }else if("26".equals(strUserDesc)){
           chooseTo1 = true;
           chooseTo2 = true;
        }else if("27".equals(strUserDesc)){
           chooseTo1 = true;
           chooseTo2 = true;
           chooseTo3 = true;
        }
      }
      prpTinsuredSchema1.setProposalNo(bankInterFace_DetailDto.getProposalNo());
      prpTinsuredSchema1.setRiskCode(bankInterFace_DetailDto.getRiskCode());
      prpTinsuredSchema1.setSerialNo("1");
      prpTinsuredSchema1.setLanguage("C");
      prpTinsuredSchema1.setInsuredType("1");
      prpTinsuredSchema1.setInsuredCode(strInsuredCode);
      prpTinsuredSchema1.setInsuredName(bankInterFace_DetailDto.getAppliName());
      prpTinsuredSchema1.setInsuredNature("3");
      prpTinsuredSchema1.setInsuredFlag("2");
      
      prpTinsuredSchema1.setInsuredIdentity(strInsuredIdentity);
      prpTinsuredSchema1.setIdentifyType(bankInterFace_DetailDto.getIdType());
      prpTinsuredSchema1.setIdentifyNumber(bankInterFace_DetailDto.getAppidNo());
      prpTinsuredSchema1.setBank(bankInterFace_DetailDto.getBfbankCode());
      prpTinsuredSchema1.setAccount(bankInterFace_DetailDto.getBfaccountNo());
      prpTinsuredSchema1.setPostCode(bankInterFace_DetailDto.getPostCode());
      
      prpTinsuredSchema1.setMobile(bankInterFace_DetailDto.getMobileCode());
      
      prpTinsuredSchema1.setBenefitFlag("N");
      prpTinsuredSchema1.setBenefitRate("0.00");
      arrCustomerCode[0] = blPrpDcustomerIdvNew.createCustomer(prpTinsuredSchema1, strComcode, strOperCode, strHandler1Code);
      strInsuredCode = arrCustomerCode[0];
      prpTinsuredSchema1.setInsuredCode(strInsuredCode);
      
      prpTinsuredSchema1for0301.setProposalNo(arrProposalNo[1]);
      prpTinsuredSchema1for0301.setRiskCode("0301");
      prpTinsuredSchema1for0301.setSerialNo("1");
      prpTinsuredSchema1for0301.setLanguage("C");
      prpTinsuredSchema1for0301.setInsuredType("1");
      prpTinsuredSchema1for0301.setInsuredCode(strInsuredCode);
      prpTinsuredSchema1for0301.setInsuredName(bankInterFace_DetailDto.getAppliName());
      prpTinsuredSchema1for0301.setInsuredNature("3");
      prpTinsuredSchema1for0301.setInsuredFlag("2");
      
      prpTinsuredSchema1for0301.setInsuredIdentity(strInsuredIdentity);
      prpTinsuredSchema1for0301.setIdentifyType(bankInterFace_DetailDto.getIdType());
      prpTinsuredSchema1for0301.setIdentifyNumber(bankInterFace_DetailDto.getAppidNo());
      prpTinsuredSchema1for0301.setBank(bankInterFace_DetailDto.getBankcode());
      prpTinsuredSchema1for0301.setAccount(bankInterFace_DetailDto.getBfaccountNo());
      prpTinsuredSchema1for0301.setPostCode(bankInterFace_DetailDto.getPostCode());
      
      prpTinsuredSchema1for0301.setMobile(bankInterFace_DetailDto.getMobileCode());
      
      prpTinsuredSchema1for0301.setBenefitFlag("N");
      prpTinsuredSchema1for0301.setBenefitRate("0.00");
      
      prpTinsuredSchema1for2700.setProposalNo(arrProposalNo[2]);
      prpTinsuredSchema1for2700.setRiskCode("2700");
      prpTinsuredSchema1for2700.setSerialNo("1");
      prpTinsuredSchema1for2700.setLanguage("C");
      prpTinsuredSchema1for2700.setInsuredType("1");
      prpTinsuredSchema1for2700.setInsuredCode(strInsuredCode);
      prpTinsuredSchema1for2700.setInsuredName(bankInterFace_DetailDto.getAppliName());
      prpTinsuredSchema1for2700.setInsuredNature("3");
      prpTinsuredSchema1for2700.setInsuredFlag("2");
      
      prpTinsuredSchema1for2700.setInsuredIdentity(strInsuredIdentity);
      prpTinsuredSchema1for2700.setIdentifyType(bankInterFace_DetailDto.getIdType());
      prpTinsuredSchema1for2700.setIdentifyNumber(bankInterFace_DetailDto.getAppidNo());
      prpTinsuredSchema1for2700.setBank(bankInterFace_DetailDto.getBankcode());
      prpTinsuredSchema1for2700.setAccount(bankInterFace_DetailDto.getBfaccountNo());
      prpTinsuredSchema1for2700.setPostCode(bankInterFace_DetailDto.getPostCode());
      
      prpTinsuredSchema1for2700.setMobile(bankInterFace_DetailDto.getMobileCode());
      
      prpTinsuredSchema1for2700.setBenefitFlag("N");
      prpTinsuredSchema1for2700.setBenefitRate("0.00");
      prpTinsuredSchema1.setBenefitFlag("N");
      prpTinsuredSchema1.setBenefitRate("0.00");        
      blPrpTinsured.setArr(prpTinsuredSchema1);
      blPrpTinsured.setArr(prpTinsuredSchema1for0301);
      blPrpTinsured.setArr(prpTinsuredSchema1for2700);
      if(chooseTo1){
        prpTinsuredSchema2.setProposalNo(bankInterFace_DetailDto.getProposalNo());
        prpTinsuredSchema2.setRiskCode(bankInterFace_DetailDto.getRiskCode());
        prpTinsuredSchema2.setSerialNo("2");
        prpTinsuredSchema2.setLanguage("C");
        prpTinsuredSchema2.setInsuredType("1");
        prpTinsuredSchema2.setInsuredName(bankInterFace_DetailDto.getInsuredName());
        prpTinsuredSchema2.setInsuredNature("3");
        prpTinsuredSchema2.setInsuredFlag("1");
        prpTinsuredSchema2.setInsuredIdentity("01");
        
        prpTinsuredSchema2.setBank(bankInterFace_DetailDto.getBankcode());
        prpTinsuredSchema2.setAccount(bankInterFace_DetailDto.getBfaccountNo());
        prpTinsuredSchema2.setPostCode(bankInterFace_DetailDto.getInsurepostCode());
        
        prpTinsuredSchema2.setMobile(bankInterFace_DetailDto.getMobileCode());
        prpTinsuredSchema2.setBenefitFlag("N");
        prpTinsuredSchema2.setBenefitRate("0.00");
        
        prpTinsuredSchema2for0301.setProposalNo(arrProposalNo[1]);
        prpTinsuredSchema2for0301.setRiskCode("0301");
        prpTinsuredSchema2for0301.setSerialNo("2");
        prpTinsuredSchema2for0301.setLanguage("C");
        prpTinsuredSchema2for0301.setInsuredType("1");
        prpTinsuredSchema2for0301.setInsuredName(bankInterFace_DetailDto.getInsuredName());
        prpTinsuredSchema2for0301.setInsuredNature("3");
        prpTinsuredSchema2for0301.setInsuredFlag("1");
        prpTinsuredSchema2for0301.setInsuredIdentity("01");
        prpTinsuredSchema2for0301.setBenefitFlag("N");
        prpTinsuredSchema2for0301.setBenefitRate("0.00");
        
        prpTinsuredSchema2for0301.setBank(bankInterFace_DetailDto.getBankcode());
        prpTinsuredSchema2for0301.setAccount(bankInterFace_DetailDto.getBfaccountNo());
        prpTinsuredSchema2for0301.setPostCode(bankInterFace_DetailDto.getInsurepostCode());
        
        prpTinsuredSchema2for0301.setMobile(bankInterFace_DetailDto.getMobileCode());
        
        prpTinsuredSchema2for2700.setProposalNo(arrProposalNo[2]);
        prpTinsuredSchema2for2700.setRiskCode("2700");
        prpTinsuredSchema2for2700.setSerialNo("2");
        prpTinsuredSchema2for2700.setLanguage("C");
        prpTinsuredSchema2for2700.setInsuredType("1");
        prpTinsuredSchema2for2700.setInsuredName(bankInterFace_DetailDto.getInsuredName());
        prpTinsuredSchema2for2700.setInsuredNature("3");
        prpTinsuredSchema2for2700.setInsuredFlag("1");
        prpTinsuredSchema2for2700.setInsuredIdentity("01");
        
        prpTinsuredSchema2for2700.setBank(bankInterFace_DetailDto.getBankcode());
        prpTinsuredSchema2for2700.setAccount(bankInterFace_DetailDto.getBfaccountNo());
        prpTinsuredSchema2for2700.setPostCode(bankInterFace_DetailDto.getInsurepostCode());
        prpTinsuredSchema2for2700.setBenefitFlag("N");
        prpTinsuredSchema2for2700.setBenefitRate("0.00");
        
        prpTinsuredSchema2for2700.setMobile(bankInterFace_DetailDto.getMobileCode());
        if("ABC".equals(IsEiesFlag)){
          prpTinsuredSchema2.setIdentifyType(bankInterFace_DetailDto.getInsuredType());
          prpTinsuredSchema2.setIdentifyNumber(bankInterFace_DetailDto.getInsuredNumber());
          prpTinsuredSchema2for0301.setIdentifyType(bankInterFace_DetailDto.getInsuredType());
          prpTinsuredSchema2for0301.setIdentifyNumber(bankInterFace_DetailDto.getInsuredNumber());
          prpTinsuredSchema2for2700.setIdentifyType(bankInterFace_DetailDto.getInsuredType());
          prpTinsuredSchema2for2700.setIdentifyNumber(bankInterFace_DetailDto.getInsuredNumber());
        }
        
        else if("CCB".equals(IsEiesFlag)){
            prpTinsuredSchema2.setIdentifyType(bankInterFace_DetailDto.getInsuredType());
            prpTinsuredSchema2.setIdentifyNumber(bankInterFace_DetailDto.getInsuredNumber());
            prpTinsuredSchema2for0301.setIdentifyType(bankInterFace_DetailDto.getInsuredType());
            prpTinsuredSchema2for0301.setIdentifyNumber(bankInterFace_DetailDto.getInsuredNumber());
            prpTinsuredSchema2for2700.setIdentifyType(bankInterFace_DetailDto.getInsuredType());
            prpTinsuredSchema2for2700.setIdentifyNumber(bankInterFace_DetailDto.getInsuredNumber());
        }
        
        arrCustomerCode[1] = blPrpDcustomerIdvNew.createCustomer(prpTinsuredSchema2, strComcode, strOperCode, strHandler1Code);
        prpTinsuredSchema2.setInsuredCode(arrCustomerCode[1]);
        prpTinsuredSchema2for0301.setInsuredCode(arrCustomerCode[1]);
        prpTinsuredSchema2for2700.setInsuredCode(arrCustomerCode[1]);
        blPrpTinsured.setArr(prpTinsuredSchema2);
        blPrpTinsured.setArr(prpTinsuredSchema2for0301);
        blPrpTinsured.setArr(prpTinsuredSchema2for2700);           
        if(chooseTo2){
          prpTinsuredSchema3.setProposalNo(bankInterFace_DetailDto.getProposalNo());
          prpTinsuredSchema3.setRiskCode(bankInterFace_DetailDto.getRiskCode());
          prpTinsuredSchema3.setSerialNo("3");
          prpTinsuredSchema3.setLanguage("C");
          prpTinsuredSchema3.setInsuredType("1");
          prpTinsuredSchema3.setInsuredName(bankInterFace_DetailDto.getEmail());
          prpTinsuredSchema3.setInsuredNature("3");
          prpTinsuredSchema3.setInsuredFlag("1");
          prpTinsuredSchema3.setInsuredIdentity("10");
          
          prpTinsuredSchema3.setBank(bankInterFace_DetailDto.getBfbankCode());
          prpTinsuredSchema3.setAccount(bankInterFace_DetailDto.getBfaccountNo());
          prpTinsuredSchema3.setBenefitFlag("N");
          prpTinsuredSchema3.setBenefitRate("0.00");
          
          prpTinsuredSchema3for0301.setProposalNo(arrProposalNo[1]);
          prpTinsuredSchema3for0301.setRiskCode("0301");
          prpTinsuredSchema3for0301.setSerialNo("3");
          prpTinsuredSchema3for0301.setLanguage("C");
          prpTinsuredSchema3for0301.setInsuredType("1");
          prpTinsuredSchema3for0301.setInsuredName(bankInterFace_DetailDto.getEmail());
          prpTinsuredSchema3for0301.setInsuredNature("3");
          prpTinsuredSchema3for0301.setInsuredFlag("1");
          prpTinsuredSchema3for0301.setInsuredIdentity("10");
          
          prpTinsuredSchema3for0301.setBank(bankInterFace_DetailDto.getBankcode());
          prpTinsuredSchema3for0301.setAccount(bankInterFace_DetailDto.getBfaccountNo());
          
          prpTinsuredSchema3for0301.setMobile(bankInterFace_DetailDto.getMobileCode());
          prpTinsuredSchema3for0301.setBenefitFlag("N");
          prpTinsuredSchema3for0301.setBenefitRate("0.00");
          
          prpTinsuredSchema3for2700.setProposalNo(arrProposalNo[2]);
          prpTinsuredSchema3for2700.setRiskCode("2700");            
          prpTinsuredSchema3for2700.setSerialNo("3");
          prpTinsuredSchema3for2700.setLanguage("C");
          prpTinsuredSchema3for2700.setInsuredType("1");
          prpTinsuredSchema3for2700.setInsuredName(bankInterFace_DetailDto.getEmail());
          
          prpTinsuredSchema3for2700.setInsuredNature("3");
          prpTinsuredSchema3for2700.setInsuredFlag("1");
          prpTinsuredSchema3for2700.setInsuredIdentity("10");
          
          prpTinsuredSchema3for2700.setBank(bankInterFace_DetailDto.getBankcode());
          prpTinsuredSchema3for2700.setAccount(bankInterFace_DetailDto.getBfaccountNo());
          prpTinsuredSchema3for2700.setBenefitFlag("N");
          prpTinsuredSchema3for2700.setBenefitRate("0.00");
          if("ABC".equals(IsEiesFlag)){
            prpTinsuredSchema3for2700.setIdentifyType(bankInterFace_DetailDto.getInsuredWifeIdType());
            prpTinsuredSchema3for2700.setIdentifyNumber(bankInterFace_DetailDto.getInsuredWifeIdNumber());
            prpTinsuredSchema3for2700.setInsuredName(bankInterFace_DetailDto.getInsuredWifeName());
            prpTinsuredSchema3for0301.setIdentifyType(bankInterFace_DetailDto.getInsuredWifeIdType());
            prpTinsuredSchema3for0301.setIdentifyNumber(bankInterFace_DetailDto.getInsuredWifeIdNumber());
            prpTinsuredSchema3for0301.setInsuredName(bankInterFace_DetailDto.getInsuredWifeName());
            prpTinsuredSchema3.setIdentifyType(bankInterFace_DetailDto.getInsuredWifeIdType());
            prpTinsuredSchema3.setIdentifyNumber(bankInterFace_DetailDto.getInsuredWifeIdNumber());
            prpTinsuredSchema3.setInsuredName(bankInterFace_DetailDto.getInsuredWifeName());
          }
          
          else if("CCB".equals(IsEiesFlag)){
              prpTinsuredSchema3for2700.setIdentifyType(bankInterFace_DetailDto.getInsuredWifeIdType());
              prpTinsuredSchema3for2700.setIdentifyNumber(bankInterFace_DetailDto.getInsuredWifeIdNumber());
              prpTinsuredSchema3for2700.setInsuredName(bankInterFace_DetailDto.getInsuredWifeName());
              prpTinsuredSchema3for0301.setIdentifyType(bankInterFace_DetailDto.getInsuredWifeIdType());
              prpTinsuredSchema3for0301.setIdentifyNumber(bankInterFace_DetailDto.getInsuredWifeIdNumber());
              prpTinsuredSchema3for0301.setInsuredName(bankInterFace_DetailDto.getInsuredWifeName());
              prpTinsuredSchema3.setIdentifyType(bankInterFace_DetailDto.getInsuredWifeIdType());
              prpTinsuredSchema3.setIdentifyNumber(bankInterFace_DetailDto.getInsuredWifeIdNumber());
              prpTinsuredSchema3.setInsuredName(bankInterFace_DetailDto.getInsuredWifeName());
          }
          
          arrCustomerCode[2] = blPrpDcustomerIdvNew.createCustomer(prpTinsuredSchema3, strComcode, strOperCode, strHandler1Code);
          prpTinsuredSchema3.setInsuredCode(arrCustomerCode[2]);
          prpTinsuredSchema3for0301.setInsuredCode(arrCustomerCode[2]);
          prpTinsuredSchema3for2700.setInsuredCode(arrCustomerCode[2]);
          blPrpTinsured.setArr(prpTinsuredSchema3);
          blPrpTinsured.setArr(prpTinsuredSchema3for0301);
          blPrpTinsured.setArr(prpTinsuredSchema3for2700);
          if(chooseTo3){
            prpTinsuredSchema4.setProposalNo(bankInterFace_DetailDto.getProposalNo());
            prpTinsuredSchema4.setRiskCode(bankInterFace_DetailDto.getRiskCode());
            prpTinsuredSchema4.setSerialNo("4");
            prpTinsuredSchema4.setLanguage("C");
            prpTinsuredSchema4.setInsuredType("1");
            prpTinsuredSchema4.setInsuredName(bankInterFace_DetailDto.getInsuredPhone());
            prpTinsuredSchema4.setInsuredNature("3");
            prpTinsuredSchema4.setInsuredFlag("1");
            prpTinsuredSchema4.setInsuredIdentity("40");                  
            prpTinsuredSchema4.setBank(bankInterFace_DetailDto.getBfbankCode());
            prpTinsuredSchema4.setAccount(bankInterFace_DetailDto.getBfaccountNo());
            prpTinsuredSchema4.setBenefitFlag("N");
            prpTinsuredSchema4.setBenefitRate("0.00");
            
            prpTinsuredSchema4for0301.setProposalNo(arrProposalNo[1]);
            prpTinsuredSchema4for0301.setRiskCode("0301");
            prpTinsuredSchema4for0301.setSerialNo("4");
            prpTinsuredSchema4for0301.setLanguage("C");
            prpTinsuredSchema4for0301.setInsuredType("1");
            prpTinsuredSchema4for0301.setInsuredName(bankInterFace_DetailDto.getInsuredPhone());
            prpTinsuredSchema4for0301.setInsuredNature("3");
            prpTinsuredSchema4for0301.setInsuredFlag("1");
            prpTinsuredSchema4for0301.setInsuredIdentity("40");                  
            prpTinsuredSchema4for0301.setBank(bankInterFace_DetailDto.getBankcode());
            prpTinsuredSchema4for0301.setAccount(bankInterFace_DetailDto.getBfaccountNo());
            prpTinsuredSchema4for0301.setBenefitFlag("N");
            prpTinsuredSchema4for0301.setBenefitRate("0.00");
            
            prpTinsuredSchema4for2700.setProposalNo(arrProposalNo[2]);
            prpTinsuredSchema4for2700.setRiskCode("2700");
            prpTinsuredSchema4for2700.setSerialNo("4");
            prpTinsuredSchema4for2700.setLanguage("C");
            prpTinsuredSchema4for2700.setInsuredType("1");
            prpTinsuredSchema4for2700.setInsuredName(bankInterFace_DetailDto.getInsuredPhone());
            prpTinsuredSchema4for2700.setInsuredNature("3");
            prpTinsuredSchema4for2700.setInsuredFlag("1");
            prpTinsuredSchema4for2700.setInsuredIdentity("40"); 
            prpTinsuredSchema4for2700.setBank(bankInterFace_DetailDto.getBankcode());
            prpTinsuredSchema4for2700.setAccount(bankInterFace_DetailDto.getBfaccountNo());
            prpTinsuredSchema4for2700.setBenefitFlag("N");
            prpTinsuredSchema4for2700.setBenefitRate("0.00");
            if("ABC".equals(IsEiesFlag)){
              prpTinsuredSchema4.setIdentifyType(bankInterFace_DetailDto.getInsuredChildIdType());
              prpTinsuredSchema4.setIdentifyNumber(bankInterFace_DetailDto.getInsuredChildIdNumber());
              prpTinsuredSchema4.setInsuredName(bankInterFace_DetailDto.getInsuredChildName());
              prpTinsuredSchema4for0301.setIdentifyType(bankInterFace_DetailDto.getInsuredChildIdType());
              prpTinsuredSchema4for0301.setIdentifyNumber(bankInterFace_DetailDto.getInsuredChildIdNumber());
              prpTinsuredSchema4for0301.setInsuredName(bankInterFace_DetailDto.getInsuredChildName());
              prpTinsuredSchema4for2700.setIdentifyType(bankInterFace_DetailDto.getInsuredChildIdType());
              prpTinsuredSchema4for2700.setIdentifyNumber(bankInterFace_DetailDto.getInsuredChildIdNumber());
              prpTinsuredSchema4for2700.setInsuredName(bankInterFace_DetailDto.getInsuredChildName());
            }
            
            else if("CCB".equals(IsEiesFlag)){
                prpTinsuredSchema4.setIdentifyType(bankInterFace_DetailDto.getInsuredChildIdType());
                prpTinsuredSchema4.setIdentifyNumber(bankInterFace_DetailDto.getInsuredChildIdNumber());
                prpTinsuredSchema4.setInsuredName(bankInterFace_DetailDto.getInsuredChildName());
                prpTinsuredSchema4for0301.setIdentifyType(bankInterFace_DetailDto.getInsuredChildIdType());
                prpTinsuredSchema4for0301.setIdentifyNumber(bankInterFace_DetailDto.getInsuredChildIdNumber());
                prpTinsuredSchema4for0301.setInsuredName(bankInterFace_DetailDto.getInsuredChildName());
                prpTinsuredSchema4for2700.setIdentifyType(bankInterFace_DetailDto.getInsuredChildIdType());
                prpTinsuredSchema4for2700.setIdentifyNumber(bankInterFace_DetailDto.getInsuredChildIdNumber());
                prpTinsuredSchema4for2700.setInsuredName(bankInterFace_DetailDto.getInsuredChildName());
            }
            
            arrCustomerCode[3] = blPrpDcustomerIdvNew.createCustomer(prpTinsuredSchema4, strComcode, strOperCode, strHandler1Code);
            prpTinsuredSchema4.setInsuredCode(arrCustomerCode[3]);
            prpTinsuredSchema4for0301.setInsuredCode(arrCustomerCode[3]);
            prpTinsuredSchema4for2700.setInsuredCode(arrCustomerCode[3]);
            blPrpTinsured.setArr(prpTinsuredSchema4);
            blPrpTinsured.setArr(prpTinsuredSchema4for0301);
            blPrpTinsured.setArr(prpTinsuredSchema4for2700);                 
          }
        }          
      }
      logger.info(("执行银XXXXX30XXXXX种核心转数任务...生成XX人数据--"));
	  logger.debug("进入银XXXXX30XXXXX种核心转数...------生成XX人数据-----");
      return blPrpTinsured;
    }

    /**
     * @desc 关爱1+家由接口表数据生成XX数据：生成XX单地址信息对象BLPrpTaddress
     * @param bankInterFace_DetailDto
     * @param arrProposalNo
     * @return blPrpTaddress
     * @throws Exception
     */
    public BLPrpTaddress generateObjectOfTaddress(BankInterFace_DetailDto bankInterFace_DetailDto, String[] arrProposalNo) throws Exception {
      BLPrpTaddress blPrpTaddress = new BLPrpTaddress();
      PrpTaddressSchema prpTaddressSchema = new PrpTaddressSchema();
      PrpTaddressSchema prpTaddressSchemafor0301 = new PrpTaddressSchema();       
      prpTaddressSchema.setProposalNo(bankInterFace_DetailDto.getProposalNo());        
      prpTaddressSchema.setRiskCode(bankInterFace_DetailDto.getRiskCode());        
      prpTaddressSchema.setAddressNo("1");
      prpTaddressSchema.setAddressCode(bankInterFace_DetailDto.getEstatepostCode());
      prpTaddressSchema.setAddressName(bankInterFace_DetailDto.getEstateAddress());
      
      prpTaddressSchemafor0301.setProposalNo(arrProposalNo[1]);
      prpTaddressSchemafor0301.setRiskCode("0301");
      prpTaddressSchemafor0301.setAddressNo("1");
      prpTaddressSchemafor0301.setAddressName(bankInterFace_DetailDto.getEstateAddress());
      prpTaddressSchemafor0301.setAddressCode(bankInterFace_DetailDto.getEstatepostCode());
      blPrpTaddress.setArr(prpTaddressSchema);
      blPrpTaddress.setArr(prpTaddressSchemafor0301);
      return blPrpTaddress;
    }

    /**
     * @desc 关爱1+家由接口表数据生成XX数据：生成XX单投资信息对象BLPrpTmainInvest
     * @param bankInterFace_DetailDto
     * @param dblInterestRate
     * @param arrProposalNo
     * @return blPrpTmainInvest
     * @throws Exception
     */
    
    public BLPrpTmainBank generateObjectOfTmainBank(BankInterFace_DetailDto bankInterFace_DetailDto, String[] arrProposalNo) throws Exception {
      BLPrpTmainBank blPrpTmainBank = new BLPrpTmainBank();
      PrpTmainBankSchema prpTmainBankSchema = new PrpTmainBankSchema();
      PrpTmainBankSchema prpTmainBankSchemafor0301 = new PrpTmainBankSchema();
      PrpTmainBankSchema prpTmainBankSchemafor2700 = new PrpTmainBankSchema();
      BLPrpdBankCompannyFacade blPrpdBankCompannyFacade = null;
      ArrayList prpdBankCompannyDtoList = null;      
      String condition = "";
      String strBankAgentCode = "";
      String strBankAgentName = "";
      String strBankHandlerName = "";
      prpTmainBankSchema.setProposalNo(bankInterFace_DetailDto.getProposalNo());
      prpTmainBankSchema.setRiskCode(bankInterFace_DetailDto.getRiskCode());
      prpTmainBankSchema.setClassCode(bankInterFace_DetailDto.getClassCode());
      
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
      prpTmainBankSchema.setBankCode(bankInterFace_DetailDto.getBankcode());
      prpTmainBankSchema.setBankAgentCode(strBankAgentCode); 
      prpTmainBankSchema.setBankAgentName(strBankAgentName); 
      prpTmainBankSchema.setBankHandlerCode(bankInterFace_DetailDto.getCounterperson()); 
      prpTmainBankSchema.setBankHandlerName(strBankHandlerName); 
      prpTmainBankSchema.setFlag(""); 
      prpTmainBankSchema.setRemark("");
      
      prpTmainBankSchemafor0301.setProposalNo(arrProposalNo[1]);
      prpTmainBankSchemafor0301.setRiskCode("0301");
      prpTmainBankSchemafor0301.setClassCode("03");
      prpTmainBankSchemafor0301.setBankCode(bankInterFace_DetailDto.getBankcode());
      prpTmainBankSchemafor0301.setBankAgentCode(strBankAgentCode); 
      prpTmainBankSchemafor0301.setBankAgentName(strBankAgentName); 
      prpTmainBankSchemafor0301.setBankHandlerCode(bankInterFace_DetailDto.getCounterperson()); 
      prpTmainBankSchemafor0301.setBankHandlerName(strBankHandlerName); 
      prpTmainBankSchemafor0301.setFlag(""); 
      prpTmainBankSchemafor0301.setRemark("");
      
      prpTmainBankSchemafor2700.setProposalNo(arrProposalNo[2]);
      prpTmainBankSchemafor2700.setRiskCode("2700");
      prpTmainBankSchemafor2700.setClassCode("27");
      prpTmainBankSchemafor2700.setBankCode(bankInterFace_DetailDto.getBankcode());
      prpTmainBankSchemafor2700.setBankAgentCode(strBankAgentCode); 
      prpTmainBankSchemafor2700.setBankAgentName(strBankAgentName); 
      prpTmainBankSchemafor2700.setBankHandlerCode(bankInterFace_DetailDto.getCounterperson()); 
      prpTmainBankSchemafor2700.setBankHandlerName(strBankHandlerName); 
      prpTmainBankSchemafor2700.setFlag(""); 
      prpTmainBankSchemafor2700.setRemark("");        
      blPrpTmainBank.setArr(prpTmainBankSchema);
      blPrpTmainBank.setArr(prpTmainBankSchemafor0301);
      blPrpTmainBank.setArr(prpTmainBankSchemafor2700);
      return blPrpTmainBank;
    }
    /**
     * @desc 关爱1+家由接口表数据生成XX数据：生成关联XX表对象BLPrpTmainSub
     * @param bankInterFace_DetailDto
     * @param dblInterestRate
     * @param arrProposalNo
     * @return blPrpTmainInvest
     * @throws Exception
     */
    public BLPrpTmainSub generateObjectOfTmainSub(BankInterFace_DetailDto bankInterFace_DetailDto, String[] arrProposalNo) throws Exception {
      BLPrpTmainSub blPrpTmainSub = new BLPrpTmainSub();
      PrpTmainSubSchema prpTmainSubSchema1 = new PrpTmainSubSchema();
      PrpTmainSubSchema prpTmainSubSchema2 = new PrpTmainSubSchema();
      prpTmainSubSchema1.setProposalNo(arrProposalNo[1]); 
      prpTmainSubSchema1.setMainPolicyNo(bankInterFace_DetailDto.getProposalNo()); 
      prpTmainSubSchema1.setBIPolicyNo("");
      prpTmainSubSchema1.setFlag(""); 
      prpTmainSubSchema1.setRemark("");
      blPrpTmainSub.setArr(prpTmainSubSchema1);      
      prpTmainSubSchema2.setProposalNo(arrProposalNo[2]); 
      prpTmainSubSchema2.setMainPolicyNo(bankInterFace_DetailDto.getProposalNo()); 
      prpTmainSubSchema2.setBIPolicyNo(""); 
      prpTmainSubSchema2.setFlag(""); 
      prpTmainSubSchema2.setRemark("");
      blPrpTmainSub.setArr(prpTmainSubSchema2);       
      return blPrpTmainSub;
    }

    /**
     * @desc 关爱1+家由接口表数据生成XX数据：生成XX单XXXXX别信息对象BLPrpTitemKind
     * @param bankInterFace_DetailDto
     * @param prpDbankInvestDto
     * @param prpDkindDto
     * @param arrProposalNo
     * @param blPrpDration0301
     * @param blPrpDration2700
     * @return blPrpTitemKind
     * @throws Exception
     */
    
    public BLPrpTitemKind generateObjectOf3006TitemKind(BankInterFace_DetailDto bankInterFace_DetailDto,BLPrpDration blPrpDration, 
    		String[] arrProposalNo,BLPrpDration blPrpDration0301,BLPrpDration blPrpDration2700) throws Exception {
      BLPrpTitemKind blPrpTitemKind = new BLPrpTitemKind();
      BLPrpDitem blPrpDitem = new BLPrpDitem();       
      DBPrpDkind dbPrpDkind = new DBPrpDkind();  
      PrpTitemKindSchema   prpTitemKindSchema = null;
      PrpTitemKindSchema   prpTitemKindSchemafor0301 = null;
      PrpTitemKindSchema   prpTitemKindSchemafor2700 = null;  
      PrpDrationSchema     prpDrationSchema = null;
      PrpDrationSchema     prpDrationSchema0301 = null;
      PrpDrationSchema     prpDrationSchema2700 = null;      
      String strStartDate  = "";
      String strEndDate    = "";
      String strRiskCode   = "";
      String strKindCode   = "";
      String strKindName   = "";  
      String strFlag       = "";
      String strItemDetailName = "";
      String strQuantityRation = "1"; 
      String strRationType = "";      
      String strItemCode = "";        
      boolean isChinese = true;
      int intYear = 0;
      int intMonth = 0;
      int intDay = 0;
      int intQuantity = 0;
      double dblAmountunit = 0;
      double dblAmount     = 0;
      double dblRate       = 0;
      double dblPremium    = 0;
      double dblPremium0301 = 0.0;
      double dblPremium2700 = 0.0;
      double dblAmount0301  = 0.0;
      double dblAmount2700  = 0.0;
      intYear = bankInterFace_DetailDto.getStartDate().getYear();
      intMonth = bankInterFace_DetailDto.getStartDate().getMonth();
      intDay = bankInterFace_DetailDto.getStartDate().getDay();
      strStartDate = new Integer(intYear).toString() + "-"
                   + new Integer(intMonth).toString() + "-"
                   + new Integer(intDay).toString();
      intYear  = bankInterFace_DetailDto.getEndDate().getYear();
      intMonth = bankInterFace_DetailDto.getEndDate().getMonth();
      intDay   = bankInterFace_DetailDto.getEndDate().getDay();
      intQuantity = bankInterFace_DetailDto.getInvestCount();
      strEndDate  = new Integer(intYear).toString() + "-"
                   + new Integer(intMonth).toString() + "-"
                   + new Integer(intDay).toString();
      strRiskCode = bankInterFace_DetailDto.getRiskCode();                                          
      for (int i=0;i<blPrpDration.getSize();i++)
      {
        prpDrationSchema = blPrpDration.getArr(i);
        prpTitemKindSchema = new PrpTitemKindSchema();
        strKindCode       = prpDrationSchema.getKindCode();
        strRationType     = prpDrationSchema.getRationType();
        dbPrpDkind.getInfo(strRiskCode,strKindCode);
        strKindName       = dbPrpDkind.getKindCName();   
        strItemDetailName = blPrpDitem.translateCode(strRiskCode,prpDrationSchema.getItemCode(),isChinese); 
        dblAmountunit     = Double.parseDouble(prpDrationSchema.getAmount());
        dblRate           = 1000.0*Double.parseDouble(prpDrationSchema.getRate());
        dblAmount         = Double.parseDouble(prpDrationSchema.getAmount())*intQuantity;
        dblPremium        = Double.parseDouble(prpDrationSchema.getPremium())*intQuantity;
        strRationType     = prpDrationSchema.getRationType();
        prpTitemKindSchema.setProposalNo(bankInterFace_DetailDto.getProposalNo());
        prpTitemKindSchema.setRiskCode(strRiskCode);
        prpTitemKindSchema.setItemKindNo(""+(i+1));
        if(!strRationType.equals("P0007")){
          if(strRationType.equals("P0001") || strRationType.equals("P0002")||strRationType.equals("P0004")){
            prpTitemKindSchema.setFamilyNo("2");
          }else if(strRationType.equals("P0003") || strRationType.equals("P0005")){
            prpTitemKindSchema.setFamilyNo("3");             
          }else if(strRationType.equals("P0006")){
            prpTitemKindSchema.setFamilyNo("4");             
          }
          
          if("0".equals(prpDrationSchema.getAmountFlag()))
        	  prpTitemKindSchema.setCalculateFlag("N");
          else 
        	  prpTitemKindSchema.setCalculateFlag("Y");
          
          prpTitemKindSchema.setUnitAmount(String.valueOf(dblAmountunit));
          prpTitemKindSchema.setQuantity(strQuantityRation);
          prpTitemKindSchema.setValue(String.valueOf(bankInterFace_DetailDto.getInvestCount()));
          prpTitemKindSchema.setShortRateFlag("3");
          prpTitemKindSchema.setDiscount("100");
          prpTitemKindSchema.setFlag(strFlag + Str.space(1-strFlag.length()) + "1");
        }else{
          if(strKindCode.equals("001")){
            prpTitemKindSchema.setCalculateFlag("Y");
            prpTitemKindSchema.setFlag(strFlag + Str.space(1-strFlag.length()) + "3");
          }else{
            prpTitemKindSchema.setCalculateFlag("N");
            prpTitemKindSchema.setFlag(strFlag + Str.space(1-strFlag.length()) + "4");
          }
            prpTitemKindSchema.setShortRateFlag("1");
        }
        prpTitemKindSchema.setKindCode(strKindCode);
        prpTitemKindSchema.setKindName(strKindName);
        prpTitemKindSchema.setItemCode(prpDrationSchema.getItemCode());
        prpTitemKindSchema.setItemDetailName(strItemDetailName);
        prpTitemKindSchema.setStartDate(strStartDate); 
        prpTitemKindSchema.setStartHour("0");
        prpTitemKindSchema.setEndDate(strEndDate);
        prpTitemKindSchema.setEndHour("24");
        prpTitemKindSchema.setCurrency("CNY");
        prpTitemKindSchema.setAmount(String.valueOf(dblAmount));
        prpTitemKindSchema.setRate(String.valueOf(dblRate)); 
        prpTitemKindSchema.setShortRate("100"); 
        prpTitemKindSchema.setPremium(String.valueOf(dblPremium));
        blPrpTitemKind.setArr(prpTitemKindSchema);
      }
      if(blPrpDration2700.getSize()>0)
      {
        strRiskCode = "2700";
        for (int i=0;i<blPrpDration2700.getSize();i++)
        {
          prpDrationSchema2700 = blPrpDration2700.getArr(i);
          prpTitemKindSchemafor2700 = new PrpTitemKindSchema();  
          strKindCode       = prpDrationSchema2700.getKindCode();
          strRationType     = prpDrationSchema2700.getRationType();
          dbPrpDkind.getInfo(strRiskCode,strKindCode);
          strKindName       = dbPrpDkind.getKindCName();   
          
          
          
          
          
          
          if("0004".equals(prpDrationSchema2700.getItemCode())){
                strItemCode = "0148";
          }else{
               strItemCode = prpDrationSchema2700.getItemCode();
          }
          
          strItemDetailName = blPrpDitem.translateCode(strRiskCode,strItemCode,isChinese); 
          dblAmount2700 = Double.parseDouble(prpDrationSchema2700.getAmount())*intQuantity;
          dblPremium2700 = Double.parseDouble(prpDrationSchema2700.getPremium())*intQuantity;
          dblAmountunit = Double.parseDouble(prpDrationSchema2700.getAmount());
          dblRate = 1000.0*Double.parseDouble(prpDrationSchema2700.getRate());
          prpTitemKindSchemafor2700.setProposalNo(arrProposalNo[2]);
          prpTitemKindSchemafor2700.setRiskCode("2700");
          prpTitemKindSchemafor2700.setItemKindNo(""+(i+1));
          if(strRationType.equals("P0001") || strRationType.equals("P0002")||strRationType.equals("P0004")){
            prpTitemKindSchemafor2700.setFamilyNo("2");             
          }else if(strRationType.equals("P0003") || strRationType.equals("P0005")){
            prpTitemKindSchemafor2700.setFamilyNo("3");             
          }else if(strRationType.equals("P0006")){
            prpTitemKindSchemafor2700.setFamilyNo("4");             
          }
          
          if("0".equals(prpDrationSchema2700.getAmountFlag()))
        	  prpTitemKindSchemafor2700.setCalculateFlag("N");            
          else 
          	  prpTitemKindSchemafor2700.setCalculateFlag("Y");            
          
          prpTitemKindSchemafor2700.setUnitAmount(String.valueOf(dblAmountunit));           
          prpTitemKindSchemafor2700.setQuantity(strQuantityRation); 
          prpTitemKindSchemafor2700.setValue(String.valueOf(bankInterFace_DetailDto.getInvestCount()));
          prpTitemKindSchemafor2700.setShortRateFlag("3");  
          prpTitemKindSchemafor2700.setDiscount("100");
          prpTitemKindSchemafor2700.setFlag(strFlag + Str.space(1-strFlag.length()) + "1");    
          prpTitemKindSchemafor2700.setKindCode(strKindCode);
          prpTitemKindSchemafor2700.setKindName(strKindName);
          prpTitemKindSchemafor2700.setItemCode(strItemCode);
          prpTitemKindSchemafor2700.setItemDetailName(strItemDetailName);
          prpTitemKindSchemafor2700.setEndDate(strEndDate);
          prpTitemKindSchemafor2700.setEndHour("24");
          prpTitemKindSchemafor2700.setStartDate(strStartDate);
          prpTitemKindSchemafor2700.setStartHour("0");
          prpTitemKindSchemafor2700.setCurrency("CNY");
          prpTitemKindSchemafor2700.setAmount(String.valueOf(dblAmount2700));
          prpTitemKindSchemafor2700.setRate(String.valueOf(dblRate));
          prpTitemKindSchemafor2700.setShortRate("100");
          prpTitemKindSchemafor2700.setPremium(String.valueOf(dblPremium2700));           
          blPrpTitemKind.setArr(prpTitemKindSchemafor2700);
        }
      }
      if(blPrpDration0301.getSize()>0)
      {
        strRiskCode = "0301";       
        for (int i=0;i<blPrpDration0301.getSize();i++)
        {
          prpDrationSchema0301 = blPrpDration0301.getArr(i);
          prpTitemKindSchemafor0301 = new PrpTitemKindSchema();
          strKindCode          = prpDrationSchema0301.getKindCode();
          dbPrpDkind.getInfo(strRiskCode,strKindCode);               
          strKindName       = dbPrpDkind.getKindCName();   
          if("0002".equals(prpDrationSchema0301.getItemCode())){
            strItemCode = "0004";
          }else{
            strItemCode = prpDrationSchema0301.getItemCode();
          }
          strItemDetailName = blPrpDitem.translateCode(strRiskCode,strItemCode,isChinese); 
          dblAmount0301 = Double.parseDouble(prpDrationSchema0301.getAmount())*intQuantity;
          dblPremium0301 = Double.parseDouble(prpDrationSchema0301.getPremium())*intQuantity;
          dblAmountunit = Double.parseDouble(prpDrationSchema0301.getAmount());
          dblRate = 1000.0*Double.parseDouble(prpDrationSchema0301.getRate());
          if(strKindCode.equals("001")){              
             prpTitemKindSchemafor0301.setCalculateFlag("Y");  
             prpTitemKindSchemafor0301.setFlag(strFlag + Str.space(1-strFlag.length()) + "3");
          }else{              
             prpTitemKindSchemafor0301.setCalculateFlag("N");
            prpTitemKindSchemafor0301.setFlag(strFlag + Str.space(1-strFlag.length()) + "4");
          }
          prpTitemKindSchemafor0301.setProposalNo(arrProposalNo[1]);
          prpTitemKindSchemafor0301.setRiskCode("0301");
          prpTitemKindSchemafor0301.setItemKindNo(""+(i+1));
          prpTitemKindSchemafor0301.setKindCode(strKindCode);
          prpTitemKindSchemafor0301.setKindName(strKindName);           
          prpTitemKindSchemafor0301.setShortRateFlag("1");  
          prpTitemKindSchemafor0301.setItemCode(strItemCode);
          prpTitemKindSchemafor0301.setItemDetailName(strItemDetailName);
          prpTitemKindSchemafor0301.setEndDate(strEndDate);
          prpTitemKindSchemafor0301.setEndHour("24");
          prpTitemKindSchemafor0301.setStartDate(strStartDate);
          prpTitemKindSchemafor0301.setStartHour("0");
          prpTitemKindSchemafor0301.setCurrency("CNY");
          prpTitemKindSchemafor0301.setAmount(String.valueOf(dblAmount0301));
          prpTitemKindSchemafor0301.setRate(String.valueOf(dblRate));
          prpTitemKindSchemafor0301.setShortRate("100");
          prpTitemKindSchemafor0301.setPremium(String.valueOf(dblPremium0301));           
          blPrpTitemKind.setArr(prpTitemKindSchemafor0301);
        }
      }        
      return blPrpTitemKind ;
     }  
    /**
     * @desc 尊贵1+家由接口表数据生成XX数据：生成XX单XXXXX别信息对象BLPrpTitemKind3007
     * @param bankInterFace_DetailDto
     * @param prpDbankInvestDto
     * @param prpDkindDto
     * @param arrProposalNo
     * @param blPrpDration0301
     * @param blPrpDration2700
     * @return blPrpTitemKind
     * @throws Exception
     */
    
    public BLPrpTitemKind generateObjectOf3007TitemKind(BankInterFace_DetailDto bankInterFace_DetailDto,Vector blPrpDration , 
    		String[] arrProposalNo,Vector blPrpDration0301,Vector blPrpDration2700) throws Exception {
      BLPrpTitemKind blPrpTitemKind = new BLPrpTitemKind();
      BLPrpDitem blPrpDitem = new BLPrpDitem();       
      DBPrpDkind dbPrpDkind = new DBPrpDkind();  
      PrpTitemKindSchema   prpTitemKindSchema = null;
      PrpTitemKindSchema   prpTitemKindSchemafor0301 = null;
      PrpTitemKindSchema   prpTitemKindSchemafor2700 = null;  
      PrpDrationSchema     prpDrationSchema = null;
      PrpDrationSchema     prpDrationSchema0301 = null;
      PrpDrationSchema     prpDrationSchema2700 = null;       
      String strStartDate  = "";
      String strEndDate    = "";
      String strRiskCode   = "";
      String strKindCode   = "";
      String strKindName   = "";  
      String strFlag       = "";
      String strItemDetailName = "";
      String strQuantityRation = "1"; 
      String strRationType = "";      
      String strItemCode = "";        
      boolean isChinese = true;
      int intYear = 0;
      int intMonth = 0;
      int intDay = 0;
      int intQuantity = 0;
      double dblAmountunit = 0;
      double dblAmount     = 0;
      double dblRate       = 0;
      double dblPremium    = 0;
      double dblPremium0301 = 0.0;
      double dblPremium2700 = 0.0;
      double dblAmount0301  = 0.0;
      double dblAmount2700  = 0.0;
      intYear = bankInterFace_DetailDto.getStartDate().getYear();
      intMonth = bankInterFace_DetailDto.getStartDate().getMonth();
      intDay = bankInterFace_DetailDto.getStartDate().getDay();
      strStartDate = new Integer(intYear).toString() + "-"
                   + new Integer(intMonth).toString() + "-"
                   + new Integer(intDay).toString();
      intYear  = bankInterFace_DetailDto.getEndDate().getYear();
      intMonth = bankInterFace_DetailDto.getEndDate().getMonth();
      intDay   = bankInterFace_DetailDto.getEndDate().getDay();
      intQuantity = bankInterFace_DetailDto.getInvestCount();
      strEndDate  = new Integer(intYear).toString() + "-"
                   + new Integer(intMonth).toString() + "-"
                   + new Integer(intDay).toString();
      strRiskCode = bankInterFace_DetailDto.getRiskCode();
      BLPrpDkind blPrpDkind = new BLPrpDkind();
      blPrpDkind.query(" riskcode='3007' ");
      String CalculateFlagString ="";
      for(int n=0;n<blPrpDkind.getSize();n++){
        if(blPrpDkind.getArr(n).getCalculateFlag().substring(0,1).equals("Y")){
          CalculateFlagString=CalculateFlagString+blPrpDkind.getArr(n).getKindCode()+",";
        }
      }
      int m = 0;                        
      for(int y=0;y<blPrpDration.size();y++){
        Vector prmblPrpDration = (Vector)blPrpDration.get(y);
        for (int i=0;i<prmblPrpDration.size();i++){
          prpDrationSchema = (PrpDrationSchema)prmblPrpDration.get(i);                 
          prpTitemKindSchema = new PrpTitemKindSchema();
          strKindCode       = prpDrationSchema.getKindCode();
          strRationType     = prpDrationSchema.getRationType();
          
          dbPrpDkind.getInfo(strRiskCode,strKindCode);
          strKindName       = dbPrpDkind.getKindCName();   
          strItemDetailName = blPrpDitem.translateCode(strRiskCode,prpDrationSchema.getItemCode(),isChinese); 
          dblAmountunit     = Double.parseDouble(prpDrationSchema.getAmount());
          dblRate           = 1000.0*Double.parseDouble(prpDrationSchema.getRate());
          dblAmount         = Double.parseDouble(prpDrationSchema.getAmount())*intQuantity;
          dblPremium        = Double.parseDouble(prpDrationSchema.getPremium())*intQuantity;
          strRationType     = prpDrationSchema.getRationType();
          prpTitemKindSchema.setProposalNo(bankInterFace_DetailDto.getProposalNo());         
          prpTitemKindSchema.setRiskCode(strRiskCode);
          m++;
          prpTitemKindSchema.setItemKindNo(""+(m));        
          if(!strRationType.equals("P0008")&&!strRationType.equals("P0009")&&!strRationType.equals("P0010")){
            if(strRationType.substring(3,4).equals("1")){
              prpTitemKindSchema.setFamilyNo("2");             
            }else if(strRationType.substring(3,4).equals("2")){
              prpTitemKindSchema.setFamilyNo("3");             
            }else if(strRationType.substring(3,4).equals("3")){
              prpTitemKindSchema.setFamilyNo("4");             
            }
            if(CalculateFlagString.indexOf(strKindCode)>-1){
              prpTitemKindSchema.setCalculateFlag("Y"); 
            }else{
              prpTitemKindSchema.setCalculateFlag("N");
            }         
            prpTitemKindSchema.setUnitAmount(String.valueOf(dblAmountunit));           
            prpTitemKindSchema.setQuantity(strQuantityRation); 
            prpTitemKindSchema.setValue(String.valueOf(bankInterFace_DetailDto.getInvestCount()));
            prpTitemKindSchema.setShortRateFlag("3");  
            prpTitemKindSchema.setDiscount("100");
            prpTitemKindSchema.setFlag(strFlag + Str.space(1-strFlag.length()) + "1");           
          }else{
            if(CalculateFlagString.indexOf(strKindCode)>-1){
              prpTitemKindSchema.setCalculateFlag("Y"); 
              prpTitemKindSchema.setFlag(strFlag + Str.space(1-strFlag.length()) + "3");
            }else{
              prpTitemKindSchema.setCalculateFlag("N");
              prpTitemKindSchema.setFlag(strFlag + Str.space(1-strFlag.length()) + "4");
            }
              prpTitemKindSchema.setShortRateFlag("1");          
          }        
          prpTitemKindSchema.setKindCode(strKindCode);         
          prpTitemKindSchema.setKindName(strKindName);
          prpTitemKindSchema.setItemCode(prpDrationSchema.getItemCode());
          prpTitemKindSchema.setItemDetailName(strItemDetailName);
          prpTitemKindSchema.setStartDate(strStartDate); 
          prpTitemKindSchema.setStartHour("0");
          prpTitemKindSchema.setEndDate(strEndDate);
          prpTitemKindSchema.setEndHour("24");
          prpTitemKindSchema.setCurrency("CNY");        
          prpTitemKindSchema.setAmount(String.valueOf(dblAmount));
          prpTitemKindSchema.setRate(String.valueOf(dblRate)); 
          prpTitemKindSchema.setShortRate("100"); 
          prpTitemKindSchema.setPremium(String.valueOf(dblPremium));   
          blPrpTitemKind.setArr(prpTitemKindSchema);
        }
      }
      int k=0;
      if(blPrpDration2700.size()>0)
      {
        strRiskCode = "2700";
        for(int y=0;y<blPrpDration2700.size();y++){
          Vector prmblPrpDration2700 = (Vector)blPrpDration2700.get(y);
          for (int i=0;i<prmblPrpDration2700.size();i++)
          {
            prpDrationSchema2700 = (PrpDrationSchema)prmblPrpDration2700.get(i);
            prpTitemKindSchemafor2700 = new PrpTitemKindSchema();  
            strKindCode       = prpDrationSchema2700.getKindCode();
            strRationType     = prpDrationSchema2700.getRationType();
            dbPrpDkind.getInfo(strRiskCode,strKindCode);
            strKindName       = dbPrpDkind.getKindCName();   
            if("0008".equals(prpDrationSchema2700.getItemCode())){
              strItemCode = "0085";
            }else if("0007".equals(prpDrationSchema2700.getItemCode())){
              strItemCode = "0148";
            }
            strItemDetailName = blPrpDitem.translateCode(strRiskCode,strItemCode,isChinese); 
            dblAmount2700         = Double.parseDouble(prpDrationSchema2700.getAmount())*intQuantity;
            dblPremium2700        = Double.parseDouble(prpDrationSchema2700.getPremium())*intQuantity;
            dblAmountunit     = Double.parseDouble(prpDrationSchema2700.getAmount());
            dblRate           = 1000.0*Double.parseDouble(prpDrationSchema2700.getRate());
            prpTitemKindSchemafor2700.setProposalNo(arrProposalNo[2]);
            prpTitemKindSchemafor2700.setRiskCode("2700");
            k++;
            prpTitemKindSchemafor2700.setItemKindNo(""+(k));
            if(strRationType.substring(3,4).equals("1")){
              prpTitemKindSchemafor2700.setFamilyNo("2");             
            }else if(strRationType.substring(3,4).equals("2")){
              prpTitemKindSchemafor2700.setFamilyNo("3");             
            }else if(strRationType.substring(3,4).equals("3")){
              prpTitemKindSchemafor2700.setFamilyNo("4");             
            } 
            prpTitemKindSchemafor2700.setCalculateFlag(prpTitemKindSchema.getCalculateFlag());            
            prpTitemKindSchemafor2700.setUnitAmount(String.valueOf(dblAmountunit));           
            prpTitemKindSchemafor2700.setQuantity(strQuantityRation); 
            prpTitemKindSchemafor2700.setValue(String.valueOf(bankInterFace_DetailDto.getInvestCount()));
            prpTitemKindSchemafor2700.setShortRateFlag("3");  
            prpTitemKindSchemafor2700.setDiscount("100");
            prpTitemKindSchemafor2700.setFlag(strFlag + Str.space(1-strFlag.length()) + "1");    
            prpTitemKindSchemafor2700.setKindCode(strKindCode);
            prpTitemKindSchemafor2700.setKindName(strKindName);
            prpTitemKindSchemafor2700.setItemCode(strItemCode);
            prpTitemKindSchemafor2700.setItemDetailName(strItemDetailName);
            prpTitemKindSchemafor2700.setEndDate(strEndDate);
            prpTitemKindSchemafor2700.setEndHour("24");
            prpTitemKindSchemafor2700.setStartDate(strStartDate);
            prpTitemKindSchemafor2700.setStartHour("0");
            prpTitemKindSchemafor2700.setCurrency("CNY");
            prpTitemKindSchemafor2700.setAmount(String.valueOf(dblAmount2700));
            prpTitemKindSchemafor2700.setRate(String.valueOf(dblRate));
            prpTitemKindSchemafor2700.setShortRate("100");
            prpTitemKindSchemafor2700.setPremium(String.valueOf(dblPremium2700));           
            blPrpTitemKind.setArr(prpTitemKindSchemafor2700);
          }
        }
      }
      int n =0;
      if(blPrpDration0301.size()>0)
      {
        strRiskCode = "0301";
        for(int y=0;y<blPrpDration0301.size();y++){
          Vector prmblPrpDration0301 = (Vector)blPrpDration0301.get(y);
          for (int i=0;i<prmblPrpDration0301.size();i++)
          {
            prpDrationSchema0301 = (PrpDrationSchema)prmblPrpDration0301.get(i);
            prpTitemKindSchemafor0301 = new PrpTitemKindSchema();
            strKindCode = prpDrationSchema0301.getKindCode();
            dbPrpDkind.getInfo(strRiskCode,strKindCode);              
            strKindName = dbPrpDkind.getKindCName();   
            if("0002".equals(prpDrationSchema0301.getItemCode())){
              strItemCode = "0004";
            }else{
              strItemCode = prpDrationSchema0301.getItemCode();
            }
            strItemDetailName = blPrpDitem.translateCode(strRiskCode,strItemCode,isChinese); 
            dblAmount0301 = Double.parseDouble(prpDrationSchema0301.getAmount())*intQuantity;
            dblPremium0301 = Double.parseDouble(prpDrationSchema0301.getPremium())*intQuantity;
            dblAmountunit = Double.parseDouble(prpDrationSchema0301.getAmount());
            dblRate = 1000.0*Double.parseDouble(prpDrationSchema0301.getRate());
            if(strKindCode.equals("001")){              
               prpTitemKindSchemafor0301.setCalculateFlag("Y");  
               prpTitemKindSchemafor0301.setFlag(strFlag + Str.space(1-strFlag.length()) + "3");
             }else{              
               prpTitemKindSchemafor0301.setCalculateFlag("N");
               prpTitemKindSchemafor0301.setFlag(strFlag + Str.space(1-strFlag.length()) + "4");
             }
            prpTitemKindSchemafor0301.setProposalNo(arrProposalNo[1]);
            prpTitemKindSchemafor0301.setRiskCode("0301");
            n++;
            prpTitemKindSchemafor0301.setItemKindNo(""+(n));
            prpTitemKindSchemafor0301.setKindCode(strKindCode);
            prpTitemKindSchemafor0301.setKindName(strKindName);           
            prpTitemKindSchemafor0301.setShortRateFlag("1");  
            prpTitemKindSchemafor0301.setItemCode(strItemCode);
            prpTitemKindSchemafor0301.setItemDetailName(strItemDetailName);
            prpTitemKindSchemafor0301.setEndDate(strEndDate);
            prpTitemKindSchemafor0301.setEndHour("24");
            prpTitemKindSchemafor0301.setStartDate(strStartDate);
            prpTitemKindSchemafor0301.setStartHour("0");
            prpTitemKindSchemafor0301.setCurrency("CNY");
            prpTitemKindSchemafor0301.setAmount(String.valueOf(dblAmount0301));
            prpTitemKindSchemafor0301.setRate(String.valueOf(dblRate));
            prpTitemKindSchemafor0301.setShortRate("100");
            prpTitemKindSchemafor0301.setPremium(String.valueOf(dblPremium0301));           
            blPrpTitemKind.setArr(prpTitemKindSchemafor0301);
          }
        }
      }        
      return blPrpTitemKind ;
    }
   /**
    * @desc 1+家由接口表数据生成XX数据：生成XX单XXXXX别信息对象BLPrpTinsuredNature
    * @param bankInterFace_DetailDto
    * @param arrProposalNo
    * @return BLPrpTinsuredNature
    * @throws Exception
    */
    
    public BLPrpTinsuredNature generateObjectofTinsuredNature(BankInterFace_DetailDto bankInterFace_DetailDto,String[] arrProposalNo, String strRiskCode,String IsEiesFlag) throws Exception {
      BLPrpTinsuredNature blPrpTinsuredNature = new BLPrpTinsuredNature();
      PrpTinsuredNatureSchema prpTinsuredNatureSchema1 = new PrpTinsuredNatureSchema();
      PrpTinsuredNatureSchema prpTinsuredNatureSchema1for0301 = new PrpTinsuredNatureSchema();
      PrpTinsuredNatureSchema prpTinsuredNatureSchema1for2700 = new PrpTinsuredNatureSchema();
      PrpTinsuredNatureSchema prpTinsuredNatureSchema2 = new PrpTinsuredNatureSchema();
      PrpTinsuredNatureSchema prpTinsuredNatureSchema2for0301 = new PrpTinsuredNatureSchema();
      PrpTinsuredNatureSchema prpTinsuredNatureSchema2for2700 = new PrpTinsuredNatureSchema();
      PrpTinsuredNatureSchema prpTinsuredNatureSchema3 = new PrpTinsuredNatureSchema();
      PrpTinsuredNatureSchema prpTinsuredNatureSchema3for0301 = new PrpTinsuredNatureSchema();
      PrpTinsuredNatureSchema prpTinsuredNatureSchema3for2700 = new PrpTinsuredNatureSchema();
      PrpTinsuredNatureSchema prpTinsuredNatureSchema4 = new PrpTinsuredNatureSchema();
      PrpTinsuredNatureSchema prpTinsuredNatureSchema4for0301 = new PrpTinsuredNatureSchema();
      PrpTinsuredNatureSchema prpTinsuredNatureSchema4for2700 = new PrpTinsuredNatureSchema();      
      boolean chooseTo1 = false; 
      boolean chooseTo2 = false; 
      boolean chooseTo3 = false; 
      String  strUseFor = "";
      String  strPhone =  "";
      String  strUnit = "";
      String  strUserDesc ="";
      strUseFor = bankInterFace_DetailDto.getUseFor();
      strPhone = bankInterFace_DetailDto.getPhone();
      strUserDesc = bankInterFace_DetailDto.getUserDesc();
      if("本人".equals(strPhone)){
        strUnit = bankInterFace_DetailDto.getInsuredAdress();
      }else if("配偶".equals(strPhone)){
        strUnit = bankInterFace_DetailDto.getAddress();
      }
      if("ABC".equals(IsEiesFlag)){
        strUseFor=bankInterFace_DetailDto.getPolicyType();
        strUserDesc=bankInterFace_DetailDto.getPolicyType();
        strUnit="";
      }
      
      else if("CCB".equals(IsEiesFlag)){
          strUseFor=bankInterFace_DetailDto.getPolicyType();
          strUserDesc=bankInterFace_DetailDto.getPolicyType();
          strUnit="";
      }
      
      if("3006".equals(strRiskCode)){
        if("ABC".equals(IsEiesFlag)){
          if("25".equals(strUseFor)){
            chooseTo1 = true;
          }else if("26".equals(strUseFor)){
            chooseTo1 = true;
            chooseTo2 = true;
          }else if("27".equals(strUseFor)){
            chooseTo1 = true;
            chooseTo2 = true;
            chooseTo3 = true;
          }
        }
        
        else if("CCB".equals(IsEiesFlag)){
              if("25".equals(strUseFor)){
                chooseTo1 = true;
              }else if("26".equals(strUseFor)){
                chooseTo1 = true;
                chooseTo2 = true;
              }else if("27".equals(strUseFor)){
                chooseTo1 = true;
                chooseTo2 = true;
                chooseTo3 = true;
              }
        }
        
        else{
          if("01".equals(strUseFor)){
            chooseTo1 = true;
          }else if("02".equals(strUseFor)){
            chooseTo1 = true;
            chooseTo2 = true;
          }else if("03".equals(strUseFor)){
            chooseTo1 = true;
            chooseTo2 = true;
            chooseTo3 = true;
          }
        }        
      }else if("3007".equals(strRiskCode)){
        if("25".equals(strUserDesc)){
          chooseTo1 = true;
        }else if("26".equals(strUserDesc)){
          chooseTo1 = true;
          chooseTo2 = true;
        }else if("27".equals(strUserDesc)){
          chooseTo1 = true;
          chooseTo2 = true;
          chooseTo3 = true;
        }
      }
      prpTinsuredNatureSchema1.setProposalNo(bankInterFace_DetailDto.getProposalNo());
      prpTinsuredNatureSchema1.setSerialNo("1");
      prpTinsuredNatureSchema1.setInsuredFlag("2");
      prpTinsuredNatureSchema1.setSex("9");
      prpTinsuredNatureSchema1.setUnit(strUnit);        
      prpTinsuredNatureSchema1for0301.setProposalNo(arrProposalNo[1]);
      prpTinsuredNatureSchema1for0301.setSerialNo("1");
      prpTinsuredNatureSchema1for0301.setInsuredFlag("2");
      prpTinsuredNatureSchema1for0301.setSex("9");
      prpTinsuredNatureSchema1for0301.setUnit(strUnit); 
      prpTinsuredNatureSchema1for2700.setProposalNo(arrProposalNo[2]);
      prpTinsuredNatureSchema1for2700.setSerialNo("1");
      prpTinsuredNatureSchema1for2700.setInsuredFlag("2");
      prpTinsuredNatureSchema1for2700.setSex("9");
      prpTinsuredNatureSchema1for2700.setUnit(strUnit);
      blPrpTinsuredNature.setArr(prpTinsuredNatureSchema1);
      blPrpTinsuredNature.setArr(prpTinsuredNatureSchema1for0301);
      blPrpTinsuredNature.setArr(prpTinsuredNatureSchema1for2700);
      if(chooseTo1){
        prpTinsuredNatureSchema2.setProposalNo(bankInterFace_DetailDto.getProposalNo());
        prpTinsuredNatureSchema2.setSerialNo("2");
        prpTinsuredNatureSchema2.setInsuredFlag("1");             
        prpTinsuredNatureSchema2.setUnit(bankInterFace_DetailDto.getInsuredAdress());
        prpTinsuredNatureSchema2for0301.setProposalNo(arrProposalNo[1]);
        prpTinsuredNatureSchema2for0301.setSerialNo("2");
        prpTinsuredNatureSchema2for0301.setInsuredFlag("1");
        prpTinsuredNatureSchema2for0301.setUnit(bankInterFace_DetailDto.getInsuredAdress()); 
        prpTinsuredNatureSchema2for2700.setProposalNo(arrProposalNo[2]);
        prpTinsuredNatureSchema2for2700.setSerialNo("2");
        prpTinsuredNatureSchema2for2700.setInsuredFlag("1");
        prpTinsuredNatureSchema2for2700.setUnit(bankInterFace_DetailDto.getInsuredAdress());
        if("ABC".equals(IsEiesFlag)){
          prpTinsuredNatureSchema2.setUnit(bankInterFace_DetailDto.getInsuredWork());
          prpTinsuredNatureSchema2for0301.setUnit(bankInterFace_DetailDto.getInsuredWork());
          prpTinsuredNatureSchema2for2700.setUnit(bankInterFace_DetailDto.getInsuredWork());
        }
        
        else if("CCB".equals(IsEiesFlag)){
            prpTinsuredNatureSchema2.setUnit(bankInterFace_DetailDto.getInsuredWork());
            prpTinsuredNatureSchema2for0301.setUnit(bankInterFace_DetailDto.getInsuredWork());
            prpTinsuredNatureSchema2for2700.setUnit(bankInterFace_DetailDto.getInsuredWork());
        }
        
        blPrpTinsuredNature.setArr(prpTinsuredNatureSchema2);
        blPrpTinsuredNature.setArr(prpTinsuredNatureSchema2for0301);
        blPrpTinsuredNature.setArr(prpTinsuredNatureSchema2for2700);
        if(chooseTo2){
          prpTinsuredNatureSchema3.setProposalNo(bankInterFace_DetailDto.getProposalNo());
          prpTinsuredNatureSchema3.setSerialNo("3");
          prpTinsuredNatureSchema3.setInsuredFlag("1");             
          prpTinsuredNatureSchema3.setUnit(bankInterFace_DetailDto.getAddress());
          prpTinsuredNatureSchema3for0301.setProposalNo(arrProposalNo[1]);
          prpTinsuredNatureSchema3for0301.setSerialNo("3");
          prpTinsuredNatureSchema3for0301.setInsuredFlag("1");
          prpTinsuredNatureSchema3for0301.setUnit(bankInterFace_DetailDto.getAddress());
          prpTinsuredNatureSchema3for2700.setProposalNo(arrProposalNo[2]);
          prpTinsuredNatureSchema3for2700.setSerialNo("3");
          prpTinsuredNatureSchema3for2700.setInsuredFlag("1");
          prpTinsuredNatureSchema3for2700.setUnit(bankInterFace_DetailDto.getAddress());
          if("ABC".equals(IsEiesFlag)){
            prpTinsuredNatureSchema3.setUnit("");
            prpTinsuredNatureSchema3for0301.setUnit("");
            prpTinsuredNatureSchema3for2700.setUnit("");
          }
          
          else if("CCB".equals(IsEiesFlag)){
              prpTinsuredNatureSchema3.setUnit("");
              prpTinsuredNatureSchema3for0301.setUnit("");
              prpTinsuredNatureSchema3for2700.setUnit("");
          }
          
          blPrpTinsuredNature.setArr(prpTinsuredNatureSchema3);
          blPrpTinsuredNature.setArr(prpTinsuredNatureSchema3for0301);
          blPrpTinsuredNature.setArr(prpTinsuredNatureSchema3for2700);
          if(chooseTo3){
            prpTinsuredNatureSchema4.setProposalNo(bankInterFace_DetailDto.getProposalNo());
            prpTinsuredNatureSchema4.setSerialNo("4");
            prpTinsuredNatureSchema4.setInsuredFlag("1");             
            prpTinsuredNatureSchema4for0301.setProposalNo(arrProposalNo[1]);
            prpTinsuredNatureSchema4for0301.setSerialNo("4");
            prpTinsuredNatureSchema4for0301.setInsuredFlag("1");
            prpTinsuredNatureSchema4for2700.setProposalNo(arrProposalNo[2]);
            prpTinsuredNatureSchema4for2700.setSerialNo("4");
            prpTinsuredNatureSchema4for2700.setInsuredFlag("1");
            if("ABC".equals(IsEiesFlag)){
              prpTinsuredNatureSchema4.setUnit("");
              prpTinsuredNatureSchema4for0301.setUnit("");
              prpTinsuredNatureSchema4for2700.setUnit("");
            }
            
            else if("CCB".equals(IsEiesFlag)){
                prpTinsuredNatureSchema4.setUnit("");
                prpTinsuredNatureSchema4for0301.setUnit("");
                prpTinsuredNatureSchema4for2700.setUnit("");
            }
            
            blPrpTinsuredNature.setArr(prpTinsuredNatureSchema4);
            blPrpTinsuredNature.setArr(prpTinsuredNatureSchema4for0301);
            blPrpTinsuredNature.setArr(prpTinsuredNatureSchema4for2700);
         }           
        }           
      } 
      return blPrpTinsuredNature;
    }
    /**
     * @desc 1+家由接口表数据生成XX数据：生成XX单金额信息对象BLPrpTfee
     * @param bankInterFace_DetailDto
     * @param prpDbankInvestDto
     * @param blPrpDration0301
     * @param blPrpDration2700
     * @param arrProposalNo
     * @return blPrpTfee
     * @throws Exception
     */
    
    public BLPrpTfee generateObjectOfTfee(BankInterFace_DetailDto bankInterFace_DetailDto,BLPrpDration blPrpDration0301,BLPrpDration blPrpDration2700, String[] arrProposalNo, String RiskCode,Vector vblPrpDration0301,Vector vblPrpDration2700) throws Exception {
      BLPrpTfee          blPrpTfee            = new BLPrpTfee();
      PrpTfeeSchema      prpTfeeSchema      = new PrpTfeeSchema();
      PrpTfeeSchema      prpTfeeSchemafor0301       = new PrpTfeeSchema();
      PrpTfeeSchema      prpTfeeSchemafor2700       = new PrpTfeeSchema();
      PrpDrationSchema   prpDrationSchemafor0301 = new PrpDrationSchema();
      PrpDrationSchema   prpDrationSchemafor2700 = new PrpDrationSchema();       
      String strRiskCode  = "";        
      double dblAmount0301    = 0;
      double dblPremium0301   = 0;
      double dblAmount2700    = 0;
      double dblPremium2700   = 0;       
      strRiskCode     = bankInterFace_DetailDto.getRiskCode();
      prpTfeeSchema.setProposalNo(bankInterFace_DetailDto.getProposalNo());
      prpTfeeSchema.setRiskCode(strRiskCode);
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
      if("3006".equals(RiskCode)){
        if(blPrpDration0301.getSize()>0)
        {
          for (int i=0;i<blPrpDration0301.getSize();i++)
          {
            prpDrationSchemafor0301 = blPrpDration0301.getArr(i);
            dblPremium0301  =  dblPremium0301 + Double.parseDouble(Str.chgStrZero(prpDrationSchemafor0301.getPremium()));
            if("001".equals(prpDrationSchemafor0301.getKindCode())){
              dblAmount0301   =  dblAmount0301  + Double.parseDouble(Str.chgStrZero(prpDrationSchemafor0301.getAmount()));
            }
          }
        }
      }else if("3007".equals(RiskCode)){
        if(vblPrpDration0301.size()>0){
           for (int i=0;i<vblPrpDration0301.size();i++){
             Vector prmblPrpDration0301 = (Vector)vblPrpDration0301.get(i);
             for (int j=0;j<prmblPrpDration0301.size();j++)
             {
               prpDrationSchemafor0301 = (PrpDrationSchema)prmblPrpDration0301.get(j);
               dblPremium0301  =  dblPremium0301 + Double.parseDouble(Str.chgStrZero(prpDrationSchemafor0301.getPremium()));
               if("001".equals(prpDrationSchemafor0301.getKindCode())){
                 dblAmount0301   =  dblAmount0301  + Double.parseDouble(Str.chgStrZero(prpDrationSchemafor0301.getAmount()));            
               }                
             }
           }
        }
      }
      prpTfeeSchemafor0301.setProposalNo(arrProposalNo[1]);
      prpTfeeSchemafor0301.setRiskCode("0301");
      prpTfeeSchemafor0301.setCurrency(bankInterFace_DetailDto.getCurrency());
      prpTfeeSchemafor0301.setAmount(String.valueOf(bankInterFace_DetailDto.getInvestCount()*dblAmount0301));
      prpTfeeSchemafor0301.setPremium(String.valueOf(bankInterFace_DetailDto.getInvestCount()*dblPremium0301));
      prpTfeeSchemafor0301.setCurrency1(bankInterFace_DetailDto.getCurrency());
      prpTfeeSchemafor0301.setExchangeRate1("1");
      prpTfeeSchemafor0301.setAmount1(String.valueOf(bankInterFace_DetailDto.getInvestCount()*dblAmount0301));
      prpTfeeSchemafor0301.setPremium1(String.valueOf(bankInterFace_DetailDto.getInvestCount()*dblPremium0301));
      prpTfeeSchemafor0301.setCurrency2(bankInterFace_DetailDto.getCurrency());
      prpTfeeSchemafor0301.setExchangeRate2("1");
      prpTfeeSchemafor0301.setAmount2(String.valueOf(bankInterFace_DetailDto.getInvestCount()*dblAmount0301));
      prpTfeeSchemafor0301.setPremium2(String.valueOf(bankInterFace_DetailDto.getInvestCount()*dblPremium0301));
      blPrpTfee.setArr(prpTfeeSchemafor0301);
      if("3006".equals(RiskCode)){
        if(blPrpDration2700.getSize()>0)
        {
          for (int i=0;i<blPrpDration2700.getSize();i++)
          {
            prpDrationSchemafor2700 = blPrpDration2700.getArr(i);
            dblPremium2700  =  dblPremium2700 + Double.parseDouble(Str.chgStrZero(prpDrationSchemafor2700.getPremium()));
            dblAmount2700   =  dblAmount2700  + Double.parseDouble(Str.chgStrZero(prpDrationSchemafor2700.getAmount()));
          }
        }
      }else if ("3007".equals(RiskCode)){
        if(vblPrpDration2700.size()>0){
          for (int i=0;i<vblPrpDration2700.size();i++){
            Vector prmblPrpDration2700 = (Vector)vblPrpDration2700.get(i);
            for (int j=0;j<prmblPrpDration2700.size();j++)
            {
              prpDrationSchemafor2700 = (PrpDrationSchema)prmblPrpDration2700.get(j);
              dblPremium2700  =  dblPremium2700 + Double.parseDouble(Str.chgStrZero(prpDrationSchemafor2700.getPremium()));
              dblAmount2700   =  dblAmount2700  + Double.parseDouble(Str.chgStrZero(prpDrationSchemafor2700.getAmount()));
            }
          }
        }
      }
      prpTfeeSchemafor2700.setProposalNo(arrProposalNo[2]);
      prpTfeeSchemafor2700.setRiskCode("2700");
      prpTfeeSchemafor2700.setCurrency(bankInterFace_DetailDto.getCurrency());
      prpTfeeSchemafor2700.setAmount(String.valueOf(bankInterFace_DetailDto.getInvestCount()*dblAmount2700));
      prpTfeeSchemafor2700.setPremium(String.valueOf(bankInterFace_DetailDto.getInvestCount()*dblPremium2700));
      prpTfeeSchemafor2700.setCurrency1(bankInterFace_DetailDto.getCurrency());
      prpTfeeSchemafor2700.setExchangeRate1("1");
      prpTfeeSchemafor2700.setAmount1(String.valueOf(bankInterFace_DetailDto.getInvestCount()*dblAmount2700));
      prpTfeeSchemafor2700.setPremium1(String.valueOf(bankInterFace_DetailDto.getInvestCount()*dblPremium2700));
      prpTfeeSchemafor2700.setCurrency2(bankInterFace_DetailDto.getCurrency());
      prpTfeeSchemafor2700.setExchangeRate2("1");
      prpTfeeSchemafor2700.setAmount2(String.valueOf(bankInterFace_DetailDto.getInvestCount()*dblAmount2700));
      prpTfeeSchemafor2700.setPremium2(String.valueOf(bankInterFace_DetailDto.getInvestCount()*dblPremium2700));
      blPrpTfee.setArr(prpTfeeSchemafor2700);
      return blPrpTfee;
    }

    /**
     * @desc 1+家由接口表数据生成XX数据：生成XX单缴费计划信息对象BLPrpTplan
     * @param bankInterFace_DetailDto
     * @param blPrpDration0301
     * @param blPrpDration2700
     * @param arrProposalNo
     * @return blPrpTplan
     * @throws Exception
     */
    
    public BLPrpTplan generateObjectOfTplan(BankInterFace_DetailDto bankInterFace_DetailDto,BLPrpDration blPrpDration0301,BLPrpDration blPrpDration2700, String[] arrProposalNo,String RiskCode,Vector vblPrpDration0301,Vector vblPrpDration2700) throws Exception {
      BLPrpTplan         blPrpTplan       = new BLPrpTplan();
      PrpTplanSchema     prpTplanSchema   = new PrpTplanSchema();
      PrpTplanSchema     prpTplanSchemafor0301   = new PrpTplanSchema(); 
      PrpTplanSchema     prpTplanSchemafor2700   = new PrpTplanSchema(); 
      PrpDrationSchema   prpDrationSchema0301 = new PrpDrationSchema();
      PrpDrationSchema   prpDrationSchema2700 = new PrpDrationSchema();       
      double dblPremium0301   = 0.0;
      double dblPremium2700   = 0.0;       
      prpTplanSchema.setProposalNo(bankInterFace_DetailDto.getProposalNo());
      prpTplanSchema.setSerialNo("1");
      prpTplanSchema.setPayNo("1");
      prpTplanSchema.setPayReason("R10");
      prpTplanSchema.setPlanDate(bankInterFace_DetailDto.getInvestDate().toString());
      prpTplanSchema.setCurrency(bankInterFace_DetailDto.getCurrency());
      prpTplanSchema.setPlanFee(String.valueOf(bankInterFace_DetailDto.getSumPremium()));
      prpTplanSchema.setDelinquentFee(String.valueOf(bankInterFace_DetailDto.getSumPremium()));
      prpTplanSchema.setPlanStartDate(bankInterFace_DetailDto.getInvestDate().toString());
      blPrpTplan.setArr(prpTplanSchema);
      if("3006".equals(RiskCode)){ 
        if(blPrpDration0301.getSize()>0)
        {
          for (int i=0;i<blPrpDration0301.getSize();i++)
          { 
            prpDrationSchema0301 = blPrpDration0301.getArr(i);
            dblPremium0301  =  dblPremium0301 + Double.parseDouble(Str.chgStrZero(prpDrationSchema0301.getPremium()));
          }
        }
      }else if("3007".equals(RiskCode)) {
        if(vblPrpDration0301.size()>0){
          for (int i=0;i<vblPrpDration0301.size();i++){
            Vector prmblPrpDration0301 = (Vector)vblPrpDration0301.get(i);
            for (int j=0;j<prmblPrpDration0301.size();j++){
              prpDrationSchema0301 = (PrpDrationSchema)prmblPrpDration0301.get(j);
              dblPremium0301  =  dblPremium0301 + Double.parseDouble(Str.chgStrZero(prpDrationSchema0301.getPremium()));
            }
          }
        } 
      }
      prpTplanSchemafor0301.setProposalNo(arrProposalNo[1]);
      prpTplanSchemafor0301.setSerialNo("1");
      prpTplanSchemafor0301.setPayNo("1");
      prpTplanSchemafor0301.setPayReason("R10");
      prpTplanSchemafor0301.setPlanDate(bankInterFace_DetailDto.getInvestDate().toString());
      prpTplanSchemafor0301.setCurrency(bankInterFace_DetailDto.getCurrency());
      prpTplanSchemafor0301.setPlanFee(String.valueOf(bankInterFace_DetailDto.getInvestCount()*dblPremium0301));
      prpTplanSchemafor0301.setDelinquentFee(String.valueOf(bankInterFace_DetailDto.getInvestCount()*dblPremium0301));
      prpTplanSchemafor0301.setPlanStartDate(bankInterFace_DetailDto.getInvestDate().toString());
      blPrpTplan.setArr(prpTplanSchemafor0301);
      if("3006".equals(RiskCode)){    
        if(blPrpDration2700.getSize()>0)
        {
          for (int i=0;i<blPrpDration2700.getSize();i++)
          {
            prpDrationSchema2700 = blPrpDration2700.getArr(i);
            dblPremium2700  =  dblPremium2700 + Double.parseDouble(Str.chgStrZero(prpDrationSchema2700.getPremium()));        
          }
        }
      }else if ("3007".equals(RiskCode)){
        if(vblPrpDration2700.size()>0){
          for (int i=0;i<vblPrpDration2700.size();i++){
            Vector prmblPrpDration2700 = (Vector)vblPrpDration2700.get(i);
            for (int j=0;j<prmblPrpDration2700.size();j++)
            {
              prpDrationSchema2700 = (PrpDrationSchema)prmblPrpDration2700.get(j);
              dblPremium2700  =  dblPremium2700 + Double.parseDouble(Str.chgStrZero(prpDrationSchema2700.getPremium()));
            }
          }
        }
      }
      prpTplanSchemafor2700.setProposalNo(arrProposalNo[2]);
      prpTplanSchemafor2700.setSerialNo("1");
      prpTplanSchemafor2700.setPayNo("1");
      prpTplanSchemafor2700.setPayReason("R10");
      prpTplanSchemafor2700.setPlanDate(bankInterFace_DetailDto.getInvestDate().toString());
      prpTplanSchemafor2700.setCurrency(bankInterFace_DetailDto.getCurrency());
      prpTplanSchemafor2700.setPlanFee(String.valueOf(bankInterFace_DetailDto.getInvestCount()*dblPremium2700));
      prpTplanSchemafor2700.setDelinquentFee(String.valueOf(bankInterFace_DetailDto.getInvestCount()*dblPremium2700));
      prpTplanSchemafor2700.setPlanStartDate(bankInterFace_DetailDto.getInvestDate().toString());
      blPrpTplan.setArr(prpTplanSchemafor2700);
      return blPrpTplan;
    }

    /**
     * 
     * @desc 1+家由接口表数据生成XX数据：生成BLPrpTmainCasualty对象
     * @param bankInterFace_DetailDto
     * @param arrProposalNo
     * @param strUseFor
     * @return
     * @throws Exception
     */
    
    public BLPrpTmainCasualty generrateObjectofTmainCasualty(BankInterFace_DetailDto bankInterFace_DetailDto,String[] arrProposalNo, String strUseFor,String strRiskCode,String IsEiesFlag,String grade)throws Exception{
      BLPrpTmainCasualty blPrpTmainCasualty = new BLPrpTmainCasualty();
      PrpTmainCasualtySchema prpTmainCasualtySchema = new PrpTmainCasualtySchema();
      PrpTmainCasualtySchema prpTmainCasualtySchemafor2700 = new PrpTmainCasualtySchema();   
      String strChoose = "";     
      if("01".equals(strUseFor)){
        strChoose =  "1";
      }else if("02".equals(strUseFor)){
        strChoose =  "2";
      }else if("03".equals(strUseFor)){
        strChoose =  "3";
      }
        
      if("ABC".equals(IsEiesFlag)||"25".equals(strUseFor)||"CCB".equals(IsEiesFlag)){
        
        if("A".equals(grade)){
          strChoose =  "1";
        }else if("B".equals(grade)||"26".equals(strUseFor)){
          strChoose =  "2";
        }else if("C".equals(grade)||"27".equals(strUseFor)){
          strChoose =  "3";
        }
      }
      prpTmainCasualtySchema.setProposalNo(arrProposalNo[0]);
      prpTmainCasualtySchema.setRiskCode(strRiskCode);
      prpTmainCasualtySchema.setBusinessGrade(strChoose);
      prpTmainCasualtySchemafor2700.setProposalNo(arrProposalNo[2]);
      prpTmainCasualtySchemafor2700.setRiskCode("2700");
      prpTmainCasualtySchemafor2700.setBusinessGrade(strChoose);     
      blPrpTmainCasualty.setArr(prpTmainCasualtySchema);
      blPrpTmainCasualty.setArr(prpTmainCasualtySchemafor2700);
      return blPrpTmainCasualty;
    }
    /**
     * @desc 1+家由接口表数据生成XX数据：生成XX号码
     * @param dbPool
     * @param strComCode
     * @param strSessionId
     * @param blPrpTmain
     * @param strRiskCode
     * @return strPolicyNo
     * @throws Exception
     */
    
    public String getPolicyNo(DbPool dbPool, String strComCode, String strSessionId, String strRiskCode) throws Exception {
      Bill bill = new Bill();
      String strPolicyNo = "";
      int intYear = 0;
      intYear = new DateTime().current().getYear();
      strPolicyNo = bill.getNo(dbPool, "prpcmain", strRiskCode, strComCode, intYear, strSessionId);
      return strPolicyNo;
    }
    
    public String getProposalNo(DbPool dbPool, String strComCode, String strSessionId, String strRiskCode) throws Exception {
      Bill bill = new Bill();
      String strProposalNo = "";
      int intYear = 0;       
      intYear = new DateTime().current().getYear();
      strProposalNo = bill.getNo(dbPool, "prptmain", strRiskCode, strComCode, intYear, strSessionId);
      return strProposalNo;
    }
   
    /**获取prpdration表数据
     * @param RiskCode
     * @param UseFor
     * @param UserDesc
     * @param Relation
     * @param IsEiesFlag
     * @param grade
     * @return
     * @throws Exception
     */
    
    public Vector getDration(String RiskCode ,String UseFor,String UserDesc,String Relation,String IsEiesFlag,String grade) throws Exception{
      Vector DrationList = new Vector();
      String strRiskCode=RiskCode;
      String strUseFor = UseFor;
      String strUserDesc=UserDesc;
      String strCondition   = "";
      String strCondition0301 ="";
      String strCondition2700 ="";
      Vector premprpDrationDtoList = new Vector();;
      Vector premprpDrationDtoList2700 = new Vector();;
      Vector premprpDrationDtoList0301 = new Vector();;
      DBPrpDration dbPrpDration = new DBPrpDration();
      BLPrpDration blPrpDration = new BLPrpDration();
      BLPrpDration blPrpDration0301 = new BLPrpDration();
      BLPrpDration blPrpDration2700 = new BLPrpDration();
      Vector prpDrationDtoList = new Vector();;
      Vector prpDrationDtoList2700 = new Vector();;
      Vector prpDrationDtoList0301 = new Vector();;
      if("3006".equals(RiskCode))
      {
        String strConditionFlag="";
          
        if("ABC".equals(IsEiesFlag)||"CCB".equals(IsEiesFlag)){
          
          if(strUseFor.equals("25"))
          {
            strConditionFlag="1";
          }
          else if(strUseFor.equals("26"))
          {
            strConditionFlag="2";
          }
          else if(strUseFor.equals("27"))
          {
            strConditionFlag="3";
          }  
        }else{
          if(strUseFor.equals("01"))
          {
            strConditionFlag="1";
          }
          else if(strUseFor.equals("02"))
          {
            strConditionFlag="2";
          }
          else if(strUseFor.equals("03"))
          {
            strConditionFlag="3";
          } 
        }
        if("1".equals(strConditionFlag))
        {
          strCondition = " RiskCode = '"+strRiskCode+"'"
          + " and (RationType = 'P0001' or RationType = 'P0007')";
          
          strCondition0301 = " RiskCode = '"+strRiskCode+"'"
          + " and  RationType = 'P0007'";
          
          strCondition2700 = " RiskCode = '"+strRiskCode+"'"
          + " and  RationType = 'P0001'";
        }
        else if("2".equals(strConditionFlag))
        {
          strCondition = " RiskCode = '"+strRiskCode+"'"
          + " and (RationType = 'P0002' or RationType = 'P0003' or RationType = 'P0007')";
          
          strCondition0301 = " RiskCode = '"+strRiskCode+"'"
          + " and  RationType = 'P0007'";
          
          strCondition2700 = " RiskCode = '"+strRiskCode+"'"
          + " and (RationType = 'P0002' or RationType = 'P0003')";
        }
        else if("3".equals(strConditionFlag))
        {
          strCondition = " RiskCode = '"+strRiskCode+"'"
          + " AND (RationType = 'P0004' or RationType = 'P0005' or RationType = 'P0006' or RationType = 'P0007')";
          
          strCondition0301 = " RiskCode = '"+strRiskCode+"'"
          + " and  RationType = 'P0007'";
          
          strCondition2700 = " RiskCode = '"+strRiskCode+"'"
          + " and (RationType = 'P0004' or RationType = 'P0005' or RationType = 'P0006')";
        }
        blPrpDration.query(strCondition,0);
        blPrpDration0301.query(strCondition0301,0);
        blPrpDration2700.query(strCondition2700,0);        
        DrationList.add(blPrpDration);
        DrationList.add(blPrpDration0301);
        DrationList.add(blPrpDration2700);
      }
      else if ("3007".equals(RiskCode))
      {
          
        if("ABC".equals(IsEiesFlag)||"CCB".equals(IsEiesFlag)){
          
          if("A".equals(grade)){
            strUseFor="01";
          }           
          else if("B".equals(grade)){
            strUseFor="02";
          }            
          else if("C".equals(grade)){
            strUseFor="03";
          }           
        }
        if("25".equals(strUserDesc)){
          String RationType = getRationType(strUseFor,strUserDesc,"本人");
          if("01".equals(strUseFor)){
            strCondition = " select * from prpdration where RiskCode = '"+strRiskCode+"'"
                       + " AND RationType like '" +RationType+"%'"
                       + " OR RationType = 'P0008'";  
        
            strCondition0301 = "select * from prpdration where RiskCode = '"+strRiskCode+"'"
                           + " and  RationType = 'P0008'";
          }else if ("02".equals(strUseFor)){
            strCondition = " select * from prpdration where RiskCode = '"+strRiskCode+"'"
                       + " AND RationType like '" +RationType+"%'"
                       + " OR RationType = 'P0009'";    
            strCondition0301 = "select * from prpdration where RiskCode = '"+strRiskCode+"'"
                       + " and  RationType = 'P0009'";
          }else if ("03".equals(strUseFor)){
            strCondition = " select * from prpdration where RiskCode = '"+strRiskCode+"'"
                       + " AND RationType like '" +RationType+"%'"
                       + " OR RationType = 'P0010'";  

            strCondition0301 = "select * from prpdration where RiskCode = '"+strRiskCode+"'"
                       + " and  RationType = 'P0010'";
          }
          strCondition2700 = "select * from prpdration where RiskCode = '"+strRiskCode+"'"
                           + " AND RationType like '" +RationType+"%'";
          premprpDrationDtoList = dbPrpDration.findByConditions(strCondition);
          premprpDrationDtoList2700 = dbPrpDration.findByConditions(strCondition2700);
          premprpDrationDtoList0301 = dbPrpDration.findByConditions(strCondition0301);
          prpDrationDtoList.add(premprpDrationDtoList);
          prpDrationDtoList2700.add(premprpDrationDtoList2700);
          prpDrationDtoList0301.add(premprpDrationDtoList0301);
        }else if ("26".equals(strUserDesc)){
          String[] arrCondition = new String[2];
          String arrCondition0301 = "";
          String[] arrCondition2700 = new String[2];
          
          String iRelation = "本人";
          String RationType = getRationType(strUseFor,strUserDesc,iRelation);
          if("01".equals(strUseFor)){
            arrCondition[0] = " select * from prpdration where RiskCode = '"+strRiskCode+"'"
                              + " AND RationType like '" +RationType+"%'"
                              + " OR RationType = 'P0008'";
            arrCondition0301 = " select * from prpdration where RiskCode = '"+strRiskCode+"'"
                              + " and  RationType = 'P0008'";
          }else if ("02".equals(strUseFor)){
            arrCondition[0] = " select * from prpdration where RiskCode = '"+strRiskCode+"'"
                              + " AND RationType like '" +RationType+"%'"
                              + " OR RationType = 'P0009'";
            arrCondition0301 = " select * from prpdration where RiskCode = '"+strRiskCode+"'"
                              + " and  RationType = 'P0009'";
          }else if ("03".equals(strUseFor)){
            arrCondition[0] = " select * from prpdration where RiskCode = '"+strRiskCode+"'"
                              + " AND RationType like '" +RationType+"%'"
                              + " OR RationType = 'P0010'";
            arrCondition0301 = " select * from prpdration where RiskCode = '"+strRiskCode+"'"
                              + " and  RationType = 'P0010'";
          }
          arrCondition2700[0] = " select * from prpdration where RiskCode = '"+strRiskCode+"'"
                              + " AND RationType like '" +RationType+"%'";
          
          iRelation = "配偶";
          RationType = getRationType(strUseFor,strUserDesc,iRelation);
          arrCondition[1] = " select * from prpdration where RiskCode = '"+strRiskCode+"'"
                             + " AND RationType like '" +RationType+"%'";                             
          arrCondition2700[1] = "select * from prpdration where RiskCode = '"+strRiskCode+"'"
                              + " AND RationType like '" +RationType+"%'";
          premprpDrationDtoList0301=dbPrpDration.findByConditions(arrCondition0301);
          prpDrationDtoList0301.add(premprpDrationDtoList0301);
          for(int i=0;i<2;i++){
            premprpDrationDtoList=dbPrpDration.findByConditions(arrCondition[i]);
            premprpDrationDtoList2700=dbPrpDration.findByConditions(arrCondition2700[i]);
            prpDrationDtoList.add(premprpDrationDtoList);
            prpDrationDtoList2700.add(premprpDrationDtoList2700); 
          }
        }else if ("27".equals(strUserDesc)){
          String[] arrCondition = new String[3];
          String arrCondition0301 = "";
          String[] arrCondition2700 = new String[3];
          
          String iRelation = "本人";
          String RationType = getRationType(strUseFor,strUserDesc,iRelation);
          if("01".equals(strUseFor)){
            arrCondition[0] = "select * from prpdration where RiskCode = '"+strRiskCode+"'"
                              + " AND RationType like '" +RationType+"%'"
                              + " OR RationType = 'P0008'";  
            arrCondition0301= "select * from prpdration where RiskCode = '"+strRiskCode+"'"
                              + " and  RationType = 'P0008'";
          }else if ("02".equals(strUseFor)){
            arrCondition[0] = "select * from prpdration where RiskCode = '"+strRiskCode+"'"
                              + " AND RationType like '" +RationType+"%'"
                              + " OR RationType = 'P0009'";  
            arrCondition0301= "select * from prpdration where RiskCode = '"+strRiskCode+"'"
                              + " and  RationType = 'P0009'";
          }else if ("03".equals(strUseFor)){
            arrCondition[0] = "select * from prpdration where RiskCode = '"+strRiskCode+"'"
                              + " AND RationType like '" +RationType+"%'"
                              + " OR RationType = 'P0010'";  
            arrCondition0301= "select * from prpdration where RiskCode = '"+strRiskCode+"'"
                              + " and  RationType = 'P0010'";
          }
          arrCondition2700[0] = "select * from prpdration where RiskCode = '"+strRiskCode+"'"
                              + " AND RationType like '" +RationType+"%'";
          
          iRelation = "配偶";
          RationType = getRationType(strUseFor,strUserDesc,iRelation);
          arrCondition[1] = "select * from prpdration where RiskCode = '"+strRiskCode+"'"
                              + " AND RationType like '" +RationType+"%'";                               
          arrCondition2700[1] = "select * from prpdration where RiskCode = '"+strRiskCode+"'"
                              + " AND RationType like '" +RationType+"%'";
          
          iRelation = "子女";
          RationType = getRationType(strUseFor,strUserDesc,iRelation);
          arrCondition[2] = "select * from prpdration where RiskCode = '"+strRiskCode+"'"
                             + " AND RationType like '" +RationType+"%'";                              
          arrCondition2700[2] = "select * from prpdration where RiskCode = '"+strRiskCode+"'"
                             + " AND RationType like '" +RationType+"%'";
          premprpDrationDtoList0301=dbPrpDration.findByConditions(arrCondition0301);
          prpDrationDtoList0301.add(premprpDrationDtoList0301);
          for(int i=0;i<3;i++){
            premprpDrationDtoList=dbPrpDration.findByConditions(arrCondition[i]);
            premprpDrationDtoList2700=dbPrpDration.findByConditions(arrCondition2700[i]);
            prpDrationDtoList.add(premprpDrationDtoList);
            prpDrationDtoList2700.add(premprpDrationDtoList2700); 
          }
        }
        
        DrationList.add(prpDrationDtoList);
        DrationList.add(prpDrationDtoList0301);
        DrationList.add(prpDrationDtoList2700);          
      }
      return DrationList;
    }

    /**生成rationType编码
     * @param UseFor
     * @param UserDesc
     * @param Relation
     * @return
     * @throws Exception
     */
    
    public String getRationType(String UseFor, String UserDesc, String Relation) throws Exception{
      String rationType = "";
      String strUseFor = UseFor;
      String strUserDesc = UserDesc;
      String StrRelation = Relation;
      /* rationType编码规则：前两位： 25 --单身  26 --夫妻 27 --夫妻及子女
                            第三位： 1 -- 方案A  2 -- 方案B 3 --方案C
                            第四位： 1 -- 本人   2 -- 配偶  3 -- 子女
                            第五位： 1 --自驾车或乘坐非运营车意外身故、残疾  2 -- 绑架意外伤害（程序中没有用到）                             
      */
      if("25".equals(strUserDesc)&&"01".equals(strUseFor)){
        rationType="2511";
      }else if ("25".equals(strUserDesc)&&"02".equals(strUseFor)){
        rationType="2521";
      }else if ("25".equals(strUserDesc)&&"03".equals(strUseFor)){
        rationType="2531";
      }else if ("26".equals(strUserDesc)&&"01".equals(strUseFor)&&"01".equals(transcode(StrRelation))){
        rationType="2611";
      }else if ("26".equals(strUserDesc)&&"01".equals(strUseFor)&&"10".equals(transcode(StrRelation))){
        rationType="2612";
      }else if ("26".equals(strUserDesc)&&"02".equals(strUseFor)&&"01".equals(transcode(StrRelation))){
        rationType="2621";
      }else if ("26".equals(strUserDesc)&&"02".equals(strUseFor)&&"10".equals(transcode(StrRelation))){
        rationType="2622";
      }else if ("26".equals(strUserDesc)&&"03".equals(strUseFor)&&"01".equals(transcode(StrRelation))){
        rationType="2631";
      }else if ("26".equals(strUserDesc)&&"03".equals(strUseFor)&&"10".equals(transcode(StrRelation))){
        rationType="2632";
      }else if ("27".equals(strUserDesc)&&"01".equals(strUseFor)&&"01".equals(transcode(StrRelation))){
        rationType="2711";
      }else if ("27".equals(strUserDesc)&&"01".equals(strUseFor)&&"10".equals(transcode(StrRelation))){
        rationType="2712";
      }else if ("27".equals(strUserDesc)&&"01".equals(strUseFor)&&"40".equals(transcode(StrRelation))){
        rationType="2713";
      }else if ("27".equals(strUserDesc)&&"02".equals(strUseFor)&&"01".equals(transcode(StrRelation))){
        rationType="2721";
      }else if ("27".equals(strUserDesc)&&"02".equals(strUseFor)&&"10".equals(transcode(StrRelation))){
        rationType="2722";
      }else if ("27".equals(strUserDesc)&&"02".equals(strUseFor)&&"40".equals(transcode(StrRelation))){
        rationType="2723";
      }else if ("27".equals(strUserDesc)&&"03".equals(strUseFor)&&"01".equals(transcode(StrRelation))){
        rationType="2731";
      }else if ("27".equals(strUserDesc)&&"03".equals(strUseFor)&&"10".equals(transcode(StrRelation))){
        rationType="2732";
      }else if ("27".equals(strUserDesc)&&"03".equals(strUseFor)&&"40".equals(transcode(StrRelation))){
        rationType="2733";
      }
      return rationType;
    }
    
    /**代码转换
     * @param Relation
     * @return
     * @throws Exception
     */
    public String transcode (String Relation) throws Exception {
      String StrRelation = "";
      if("本人".equals(Relation)){
        StrRelation = "01";
      }else if ("配偶".equals(Relation)){
        StrRelation = "10";
      }else if ("子女".equals(Relation)){
        StrRelation = "40";
      }
      return StrRelation;
    }
    
	/**
	 * 插入失败日志表 (重写)
	 * @param businessKey
	 * @param message
	 * @param e
	 * @param jobName
	 */
	public static void insertMidDataLog(String businessKey, String message, Exception e, String jobName,String comCode ,String jobGroup,String jobDes ) {
		logger.debug("TaskMngUtil.insertMidDataLog开始");
		DbPool dbpool = null;
		try {
			Map paramMap = new HashMap();
			paramMap.put(MidLogJdbcDao.JOBNAME, jobName);






			
			paramMap.put(MidLogJdbcDao.BUSINESSKEY, businessKey);
			paramMap.put(MidLogJdbcDao.ERRORMESSAGE, message);
			paramMap.put(MidLogJdbcDao.COMCODE, comCode);
			paramMap.put(MidLogJdbcDao.ERRORSTACK, getStackTraceToString(e));
			paramMap.put(MidLogJdbcDao.EXECUTEDATE, new Date());
			paramMap.put(MidLogJdbcDao.JOBGROUP, jobGroup);
			paramMap.put(MidLogJdbcDao.JOBDES, jobDes);
			
			
			dbpool = new DbPool();
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			MidLogJdbcDao.insertMidDataLog(dbpool, paramMap);
		} catch (Throwable t) {
			logger.error("插入异步任务管理系统中间业务日志表失败：" + t.getMessage(), t);
			t.printStackTrace();
		} finally{
			if(dbpool != null){
				try {
					dbpool.close();
				} catch (SQLException se) {
					logger.error("TaskMngUtil.insertMidDataLog关闭dbpool失败：", se);
					se.printStackTrace();
				}
			}
		}
		logger.debug("TaskMngUtil.insertMidDataLog结束");
	}

	/**
	 * @Title: getStackTraceToString
	 * @Description: 把堆栈信息转为字符串
	 * @param @param e
	 * @param @return    设定文件
	 * @return String    返回类型
	 * @throws
	 */
	public static String getStackTraceToString(Exception e) {
		if(e == null){
			return "";
		}
		String str = "";
		try {
			StringBuffer strbufStackTrace = new StringBuffer("");
			strbufStackTrace.append(e.getClass().getName() + ":  " + e.getMessage() + "\n");
			StackTraceElement[] stackTrace = e.getStackTrace();
			if (stackTrace != null) {
				for (int i = 0; i < stackTrace.length; i++) {
					strbufStackTrace.append("\t" + stackTrace[i].toString() + "\n");
				}
			}
			str = strbufStackTrace.toString();
			if(str.length()>4000){
				str=str.substring(0, 3998);
			}
		} catch (Throwable t) {
			logger.error("中间业务平台 转数失败日志 BLGenerateACCData-getStackTraceToString失败：", t);
			t.printStackTrace();
		}
		return str;
	}
	

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	/**
	 * 转数信息初始化
	 * @author wangchuanzhong 20111107
	 * @param dbpool
	 * @param bankInterFace_DetailDto 中间表对象
	 * @return 无
	 */
	public void initTransInfo(DbPool dbpool, BankInterFace_DetailDto bankInterFace_DetailDto) throws Exception{
		
		BLPrpdBankCompannyFacade blPrpdBankCompannyFacade = new BLPrpdBankCompannyFacade();
		BLPrpDagreementFacade blPrpDagreementFacade = new BLPrpDagreementFacade();
		PrpDagreementDto prpDagreementDto = null;
		ArrayList prpdBankCompannyDtoList = null;
		Collection prpDagreementDtoList = null;
		
	    String strBankAgentCode = "";
		blPrpdBankCompannyFacade = new BLPrpdBankCompannyFacade();
		strBankAgentCode = bankInterFace_DetailDto.getBankbranchCode(); 
		
		prpdBankCompannyDtoList = (ArrayList) blPrpdBankCompannyFacade
				.findByConditions(" BankCode='" + strBankAgentCode + "'");
		if (prpdBankCompannyDtoList.size() == 0) {
			errorMessage = "BLTransDataYB30.generateObjectOfCommission"
					+ "prpdbankcompanny表" + strBankAgentCode + "取值失败,请管理员进行配置！";
			throw new UserException(-98, -1167,
					"BLTransDataYB30.generateObjectOfCommission",
					"prpdbankcompanny表银行网点代码" + strBankAgentCode
							+ "取值失败,请管理员进行配置！");
		}
		if (prpdBankCompannyDtoList.size() > 0) {
			strAgentCode = ((PrpdBankCompannyDto) prpdBankCompannyDtoList
					.get(0)).getAgentCode();
		}
		prpDagreementDtoList = blPrpDagreementFacade
				.findByConditions("AgentCode='" + strAgentCode
						+ "' and validstatus = '1'");
		for (Iterator prpDagreementDtoListTmp = prpDagreementDtoList.iterator(); prpDagreementDtoListTmp
				.hasNext();) {
			prpDagreementDto = (PrpDagreementDto) prpDagreementDtoListTmp
					.next();
			strAgreementNo = prpDagreementDto.getAgreementNo();
			break;
		}
		
		initFeeInfo(dbpool, bankInterFace_DetailDto);
	}
	
	/**
	 * 费用信息初始化 费用开关打开：代理业务，根据代理协议号，产品代码取费用信息；否则，根据业务员代码，产品代码取费用信息 费用开关关闭：XXXXX持现有规则
	 * @author wangchuanzhong 20111107
	 * @param dbpool  中间表信息
	 * @param bankInterFace_DetailDto  中间表对象
	 * @return 无
	 */
	public void initFeeInfo(DbPool dbpool, BankInterFace_DetailDto bankInterFace_DetailDto) throws Exception {
		logger.debug("银XXXXX30转数->initFeeInfo()" + bankInterFace_DetailDto.getPolicyno()+ "->begin");
		boolean blnAgent = true; 
		String strRationType = bankInterFace_DetailDto.getRiskCode() + bankInterFace_DetailDto.getRiskCode();
		String strRationType2700 = bankInterFace_DetailDto.getRiskCode() + "2700";
		String strRationType0301 = bankInterFace_DetailDto.getRiskCode() + "0301";
        
		
		UIPrpDriskConfigAction uiPrpDriskConfigAction = new UIPrpDriskConfigAction();
		PrpDriskConfigDto prpDriskConfigDto = uiPrpDriskConfigAction.queryRiskConfig(bankInterFace_DetailDto.getComCode(),
				bankInterFace_DetailDto.getRiskCode(),"EXPENSE_SWITCH_OUTSIDE");
		if (prpDriskConfigDto != null && "1".equals(prpDriskConfigDto.getConfigValue())) {
			blnFeeSwitch = true;
		}
		
		if ("".equals(strAgreementNo)) {
			blnAgent = false;
		}
		
		if (blnFeeSwitch) {
			
			if("3006,3007".indexOf(bankInterFace_DetailDto.getRiskCode()) == -1)
			{
				BLPrpDagreeDetailAction blPrpDagreeDetailAction = new BLPrpDagreeDetailAction();
				PrpDagreeDetailDto prpDagreeDetailDto = null;
				
				if ("1".equals(SysConfig.getProperty("DB_SPLIT_SWITCH"))) {
					prpDagreeDetailDto = blPrpDagreeDetailAction.findByPrimaryKey(dbpool.getDBManager("platformNewDataSource"), strAgreementNo, bankInterFace_DetailDto.getRiskCode(), "0000");
				} else {
					prpDagreeDetailDto = blPrpDagreeDetailAction.findByPrimaryKey(dbpool.getDBManager(SysConfig.CONST_DDCCDATASOURCE), strAgreementNo, bankInterFace_DetailDto.getRiskCode(), "0000");
				}
				
				if (prpDagreeDetailDto != null) {
					strDisRate = prpDagreeDetailDto.getTopCommission() + "";
					if("".equals(strDisRate) || prpDagreeDetailDto.getTopCommission()<0)
				    {
						errorMessage="BLTransDataYB30.initFeeInfo"+"prpDagreeDetail表"+"手续费配置有误！";
				        throw new UserException(-98, -1167, "BLTransDataYB30.initFeeInfo",
				          "手续费配置有误,请与管理员沟通！" ); 
				    }
					strDisRate2700 = prpDagreeDetailDto.getTopCommission() + "";
					strDisRate0301 = prpDagreeDetailDto.getTopCommission() + "";
					if("1".equals(bankInterFace_DetailDto.getIseiesflag())){
						
						if(prpDagreeDetailDto.getPolicyFee() < 0.0)
						
						{
							throw new Exception("出单费配置有误，该笔业务不转入核心！");
						}else
						{
							strPolicyFee = prpDagreeDetailDto.getPolicyFee() + "";
							strPolicyFee2700 = prpDagreeDetailDto.getPolicyFee() + "";
							strPolicyFee0301 = prpDagreeDetailDto.getPolicyFee() + "";
						}
					}
				}else
				{
					throw new UserException(-98, -1167, "BLTransDataYB30.generateObjectOfTmain",
			          "手续费比例取值失败,请管理员进行配置！" ); 
				}
			}
			BLPrpDoutsideAgreedetailAction blPrpDoutsideAgreedetailAction = new BLPrpDoutsideAgreedetailAction();
			PrpDoutsideAgreedetailDto prpDoutsideAgreedetailDto = null;
			
			if (blnAgent) {
				prpDoutsideAgreedetailDto = blPrpDoutsideAgreedetailAction.findByPrimaryKey(dbpool.getDBManager(SysConfig.CONST_DDCCDATASOURCE), strAgreementNo, strRationType2700);
			} else {
				prpDoutsideAgreedetailDto = blPrpDoutsideAgreedetailAction.findByPrimaryKey(dbpool.getDBManager(SysConfig.CONST_DDCCDATASOURCE), bankInterFace_DetailDto.getHandler1Code(), strRationType2700);
			}
			if (prpDoutsideAgreedetailDto != null) {
				strDisRate2700 = prpDoutsideAgreedetailDto.getCommissionrate() + "";
				if("".equals(strDisRate) || prpDoutsideAgreedetailDto.getCommissionrate()<0)
			    {
					errorMessage="BLTransDataYB30.initFeeInfo"+"prpDoutsideAgreedetail表"+"手续费配置有误！";
			        throw new UserException(-98, -1167, "BLTransDataYB30.initFeeInfo",
			          "手续费配置有误,请与管理员沟通！" ); 
			    }
				strManageRate2700 = prpDoutsideAgreedetailDto.getManagefeerate() + "";
				strSaleSalaryRate2700 = prpDoutsideAgreedetailDto.getSalessalaryrate() + "";
				if("1".equals(bankInterFace_DetailDto.getIseiesflag())){
					
					if(prpDoutsideAgreedetailDto.getPolicyFee() < 0.0)
					
					{
						throw new Exception("2700小单出单费配置有误，该笔业务不转入核心！");
					}else
					{
						strPolicyFee2700 = prpDoutsideAgreedetailDto.getPolicyFee() + "";
					}
				}
			} else {
				
				throw new Exception("费用开关打开，取不到费用信息，该笔业务不转入核心！");
			}
			
			prpDoutsideAgreedetailDto = null;
			if (blnAgent) {
				prpDoutsideAgreedetailDto = blPrpDoutsideAgreedetailAction.findByPrimaryKey(dbpool.getDBManager(SysConfig.CONST_DDCCDATASOURCE), strAgreementNo, strRationType0301);
			} else {
				prpDoutsideAgreedetailDto = blPrpDoutsideAgreedetailAction.findByPrimaryKey(dbpool.getDBManager(SysConfig.CONST_DDCCDATASOURCE), bankInterFace_DetailDto.getHandler1Code(), strRationType0301);
			}
			if (prpDoutsideAgreedetailDto != null) {
				strDisRate0301 = prpDoutsideAgreedetailDto.getCommissionrate() + "";
				if("".equals(strDisRate) || prpDoutsideAgreedetailDto.getCommissionrate()<0)
			    {
					errorMessage="BLTransDataYB30.initFeeInfo"+"prpDoutsideAgreedetail表"+"手续费配置有误！";
			        throw new UserException(-98, -1167, "BLTransDataYB30.initFeeInfo",
			          "手续费配置有误,请与管理员沟通！" ); 
			    }
				strManageRate0301 = prpDoutsideAgreedetailDto.getManagefeerate() + "";
				strSaleSalaryRate0301 = prpDoutsideAgreedetailDto.getSalessalaryrate() + "";
				if("1".equals(bankInterFace_DetailDto.getIseiesflag())){
					
					if(prpDoutsideAgreedetailDto.getPolicyFee() < 0.0)
					
					{
						throw new Exception("0301小单出单费配置有误，该笔业务不转入核心！");
					}else
					{
						strPolicyFee0301 = prpDoutsideAgreedetailDto.getPolicyFee() + "";
					}
				}
			} else {
				
				throw new Exception("费用开关打开，取不到费用信息，该笔业务不转入核心！");
			}
		
		} else {
			 logger.info(("执行银XXXXX30XXXXX种核心转数任务...生成XX单数据前---费用开关关闭，XXXXX持现有规则--"));
		    logger.debug("进入银XXXXX30XXXXX种核心转数...------费用开关关闭，XXXXX持现有规则------");
			
			BLPrpDagreeDetailAction blPrpDagreeDetailAction = new BLPrpDagreeDetailAction();
			PrpDagreeDetailDto prpDagreeDetailDto = null;
			
			if ("1".equals(SysConfig.getProperty("DB_SPLIT_SWITCH"))) {
				prpDagreeDetailDto = blPrpDagreeDetailAction.findByPrimaryKey(dbpool.getDBManager("platformNewDataSource"), strAgreementNo, bankInterFace_DetailDto.getRiskCode(), "0000");
			} else {
				prpDagreeDetailDto = blPrpDagreeDetailAction.findByPrimaryKey(dbpool.getDBManager(SysConfig.CONST_DDCCDATASOURCE), strAgreementNo, bankInterFace_DetailDto.getRiskCode(), "0000");
			}
			
			if (prpDagreeDetailDto != null) {
				strDisRate = prpDagreeDetailDto.getTopCommission() + "";
				if("".equals(strDisRate) || prpDagreeDetailDto.getTopCommission()<0)
			    {
					errorMessage="BLTransDataYB30.initFeeInfo"+"prpDagreeDetail表"+"手续费配置有误！";
			        throw new UserException(-98, -1167, "BLTransDataYB30.initFeeInfo",
			          "手续费配置有误,请与管理员沟通！" ); 
			    }
				strDisRate2700 = prpDagreeDetailDto.getTopCommission() + "";
				strDisRate0301 = prpDagreeDetailDto.getTopCommission() + "";
				if("1".equals(bankInterFace_DetailDto.getIseiesflag())){
					
					if(prpDagreeDetailDto.getPolicyFee() < 0.0)
					
					{
						throw new Exception("出单费配置有误，该笔业务不转入核心！");
					}else
					{
						strPolicyFee = prpDagreeDetailDto.getPolicyFee() + "";
						strPolicyFee2700 = prpDagreeDetailDto.getPolicyFee() + "";
						strPolicyFee0301 = prpDagreeDetailDto.getPolicyFee() + "";
					}
				}
			}else
			{
				throw new UserException(-98, -1167, "BLTransDataYB30.generateObjectOfTmain",
		          "手续费比例取值失败,请管理员进行配置！" ); 
			}
		}
		logger.debug("银XXXXX30转数->initFeeInfo()" + bankInterFace_DetailDto.getPolicyno()+ "->end");
	}
	
	/**
	 * Expense
	 * 
	 * @author wangchuanzhong 20111107
	 * @param bankInterFace_DetailDto 中间表对象
	 * @param arrProposalNo XX单数组信息
	 * @return BLPrpTexpense 费用信息对象
	 */
	public BLPrpTexpense transExpense(BankInterFace_DetailDto bankInterFace_DetailDto, String[] arrProposalNo) throws Exception {
	    logger.debug("银XXXXX30转数->transExpense()" + bankInterFace_DetailDto.getPolicyno()+ "->begin");
	    logger.info(("执行银XXXXX30XXXXX种核心转数任务...生成XX单数据前生成费用信息对象.................----begin--"));
    	logger.debug("进入银XXXXX30XXXXX种核心转数...------生成费用信息对象------");
		BLPrpTexpense blPrpTexpense = new BLPrpTexpense();
	    PrpTexpenseSchema prpTexpenseSchema = new PrpTexpenseSchema();
		PrpTexpenseSchema prpTexpenseSchemafor0301 = new PrpTexpenseSchema();
		PrpTexpenseSchema prpTexpenseSchemafor2700 = new PrpTexpenseSchema();
		prpTexpenseSchema.setProposalNo(bankInterFace_DetailDto.getProposalNo());
		prpTexpenseSchema.setRiskCode(bankInterFace_DetailDto.getRiskCode());
		prpTexpenseSchema.setManageFeeRate(strManageRate);
		prpTexpenseSchema.setMaxManageFeeRate(strManageRate);
		if (blnFeeSwitch) {
			
			prpTexpenseSchema.setFlag("I11");
		} else {
			prpTexpenseSchema.setFlag("I10");
		}
		prpTexpenseSchema.setSalesSalaryRate(strSaleSalaryRate);
		prpTexpenseSchema.setBankDisRate(operateFee_PrpTexpense+"");
		prpTexpenseSchema.setOperateFee(bankDisRate_PrpTexpense+"");
		blPrpTexpense.setArr(prpTexpenseSchema);

		
		prpTexpenseSchemafor0301.setProposalNo(arrProposalNo[1]);
		prpTexpenseSchemafor0301.setRiskCode("0301");
		prpTexpenseSchemafor0301.setManageFeeRate(strManageRate0301);
		prpTexpenseSchemafor0301.setMaxManageFeeRate(strManageRate0301);
		if (blnFeeSwitch) {
			
			prpTexpenseSchemafor0301.setFlag("I11");
		} else {
			prpTexpenseSchemafor0301.setFlag("I10");
		}
		prpTexpenseSchemafor0301.setSalesSalaryRate(strSaleSalaryRate0301);
		prpTexpenseSchemafor0301.setBankDisRate(operateFee_PrpTexpense0301+"");
		prpTexpenseSchemafor0301.setOperateFee(bankDisRate_PrpTexpense0301+"");
		blPrpTexpense.setArr(prpTexpenseSchemafor0301);

		
		prpTexpenseSchemafor2700.setProposalNo(arrProposalNo[2]);
		prpTexpenseSchemafor2700.setRiskCode("2700");
		prpTexpenseSchemafor2700.setManageFeeRate(strManageRate2700);
		prpTexpenseSchemafor2700.setMaxManageFeeRate(strManageRate2700);
		
		if (blnFeeSwitch) {
			
			prpTexpenseSchemafor2700.setFlag("I11");
		} else {
			prpTexpenseSchemafor2700.setFlag("I10");
		}
		prpTexpenseSchemafor2700.setSalesSalaryRate(strSaleSalaryRate2700);
		prpTexpenseSchemafor2700.setBankDisRate(operateFee_PrpTexpense2700+"");
		prpTexpenseSchemafor2700.setOperateFee(bankDisRate_PrpTexpense2700+"");
		blPrpTexpense.setArr(prpTexpenseSchemafor2700);
		logger.info(("执行银XXXXX30XXXXX种核心转数任务...生成XX单数据前生成费用信息对象结束.................-------------"));
    	logger.debug("进入银XXXXX30XXXXX种核心转数...------生成费用信息对象------");
		logger.debug("银XXXXX30转数->transExpense()" + bankInterFace_DetailDto.getPolicyno() + "->end");
		return blPrpTexpense;

	}
	
}
