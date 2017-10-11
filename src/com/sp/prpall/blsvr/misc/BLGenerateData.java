package com.sp.prpall.blsvr.misc;

import java.math.BigDecimal;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sp.sysframework.reference.DBManager;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.utility.string.ChgDate;
import com.sp.utility.string.Str;
import com.sp.utility.SysConfig;

import com.sp.sysframework.common.datatype.DateTime;
import com.sp.platform.bl.facade.BLPrpDagreementFacade;
import com.sp.platform.bl.facade.BLPrpDagreeDetailFacade;
import com.sp.platform.bl.facade.BLPrpdBankCompannyFacade;
import com.sp.platform.dto.domain.BankInterFace_DetailDto;
import com.sp.platform.dto.domain.PrpDagreementDto;
import com.sp.platform.dto.domain.PrpDagreeDetailDto;
import com.sp.platform.dto.domain.PrpdBankCompannyDto;
import com.sp.platform.bl.action.domain.BLBankInterFace_DetailAction;
import com.sp.platform.resource.dtofactory.domain.DBBankInterFace_Detail;
import com.sp.utiall.blsvr.BLPrpDcompany;
import com.sp.utiall.blsvr.BLPrpDcustomer;
import com.sp.utiall.blsvr.BLPrpDcustomerIdvNew;
import com.sp.utiall.blsvr.BLPrpDcustomerIdv;
import com.sp.utiall.dbsvr.DBPrpDcompany;
import com.sp.utiall.dbsvr.DBPrpDcustomerIdvNew;
import com.sp.utiall.dbsvr.DBPrpDkind;
import com.sp.utiall.schema.PrpDcustomerIdvSchema;
import com.sp.utiall.schema.PrpDcustomerSchema;
import com.sp.utiall.schema.PrpDrationSchema;
import com.sp.prpall.blsvr.cb.BLPolicy;
import com.sp.prpall.blsvr.cb.BLPolicyOrigin;
import com.sp.prpall.blsvr.cb.BLPrpCaddress;
import com.sp.prpall.blsvr.cb.BLPrpCfee;
import com.sp.prpall.blsvr.cb.BLPrpCinsured;
import com.sp.prpall.blsvr.cb.BLPrpCitemKind;
import com.sp.prpall.blsvr.cb.BLPrpCinsuredNature;
import com.sp.prpall.blsvr.cb.BLPrpCmain;
import com.sp.prpall.blsvr.cb.BLPrpCmainBank;
import com.sp.prpall.blsvr.cb.BLPrpCmainSub;
import com.sp.prpall.blsvr.cb.BLPrpCplan;
import com.sp.prpall.blsvr.cb.BLPrpCmainCasualty;
import com.sp.payment.blsvr.jf.BLPrpJplanFee;
import com.sp.prpall.blsvr.tb.BLProposal;
import com.sp.prpall.blsvr.tb.BLPrpTaddress;
import com.sp.prpall.blsvr.tb.BLPrpTbatch;
import com.sp.prpall.blsvr.tb.BLPrpTfee;
import com.sp.prpall.blsvr.tb.BLPrpTinsured;
import com.sp.prpall.blsvr.tb.BLPrpTitemKind;
import com.sp.prpall.blsvr.tb.BLPrpTmain;
import com.sp.prpall.blsvr.tb.BLPrpTmainBank;
import com.sp.prpall.blsvr.tb.BLPrpTmainSub;
import com.sp.prpall.blsvr.tb.BLPrpTplan;
import com.sp.prpall.blsvr.tb.BLPrpTinsuredNature;
import com.sp.prpall.blsvr.tb.BLPrpTmainCasualty;
import com.sp.prpall.dbsvr.misc.DBInvest;
import com.sp.prpall.interf.Visa;
import com.sp.prpall.schema.PrpCaddressSchema;
import com.sp.prpall.schema.PrpCfeeSchema;
import com.sp.prpall.schema.PrpCinsuredSchema;
import com.sp.prpall.schema.PrpCitemKindSchema;
import com.sp.prpall.schema.PrpCmainBankSchema;
import com.sp.prpall.schema.PrpCmainSchema;
import com.sp.prpall.schema.PrpCmainSubSchema;
import com.sp.prpall.schema.PrpCinsuredNatureSchema;
import com.sp.prpall.schema.PrpCplanSchema;
import com.sp.prpall.schema.PrpCmainCasualtySchema;
import com.sp.prpall.schema.PrpCommissionSchema;
import com.sp.prpall.schema.PrpTaddressSchema;
import com.sp.prpall.schema.PrpTbatchSchema;
import com.sp.prpall.schema.PrpTfeeSchema;
import com.sp.prpall.schema.PrpTinsuredSchema;
import com.sp.prpall.schema.PrpTinsuredNatureSchema;
import com.sp.prpall.schema.PrpTitemKindSchema;
import com.sp.prpall.schema.PrpTmainBankSchema;
import com.sp.prpall.schema.PrpTmainSchema;
import com.sp.prpall.schema.PrpTplanSchema;
import com.sp.prpall.schema.PrpTmainCasualtySchema;
import com.sp.prpall.pubfun.Bill;
import com.sp.prpall.pubfun.CheckChannelYB;


public class BLGenerateData {


	
	String[] arrCustomerCode = new String[2];
	
    public BLGenerateData() {
    }

    /**
     * @desc 投联XXXXX由接口表数据生成XX数据
     * @param request
     * @param response
     * @param strRiskCode
     * @return strReturnMessage：size=0 没有可生成的XX单、XX/size>0 空字符串，将对象放入Attribute中
     * @throws Exception
     */
    public String build(HttpServletRequest request, HttpServletResponse response, String strRiskCode ,String vscode,String isEiesFlag,String icomCode) throws Exception {
        DbPool dbPool = new DbPool();
        DBManager dbManager = null;
        BLBankInterFace_DetailAction blBankInterFace_DetailAction = new BLBankInterFace_DetailAction();
        ArrayList bankInterFace_DetailDtoList = null;
        ArrayList policyNoList = new ArrayList();
        String strSessionId = request.getSession().getId();
        String strCondition = "";
        String strReturnMessage = "";
        String flag = "";
        
        String strTOCOMCODE = "";     
        try {
            dbPool.open(SysConfig.CONST_DDCCDATASOURCE);
            dbPool.beginTransaction();
            dbManager = dbPool.getDBManager(SysConfig.CONST_DDCCDATASOURCE);
            
            if("Eies".equals(isEiesFlag)){
            	strCondition = " RevolutionFlag='0'" 
           		    + " AND Invalid='0'"    
                    + " AND RiskCode='" + strRiskCode + "'" 
                    + " AND IsEiesFlag='1'"
                    + " AND ComCode ='" + icomCode + "'"

                    + " AND bfbankcode='102' " 

                    /*应XXXXX要求此处HandlerCode生成XX人员不需要，任何有权限的人员都可以生成XX*/
                    /*+ " AND HandlerCode='" + userCode + "'" 
                    /* 此处的[ORDER BY UploadFileSeq]非常重要，因为jsp的显示结果依赖这个顺序 */
                    + " ORDER BY UploadFileSeq,Trans_Seq"; 
            }else if ("WYEies".equals(isEiesFlag)){
            	strCondition = " RevolutionFlag='0'" 
           		    + " AND Invalid='0'"    
                    + " AND RiskCode='" + strRiskCode + "'" 
                    + " AND IsEiesFlag='2'"
                    + " AND ComCode ='" + icomCode + "'"

                    + " AND bfbankcode='102' " 

                    /*应XXXXX要求此处HandlerCode生成XX人员不需要，任何有权限的人员都可以生成XX*/
                    /*+ " AND HandlerCode='" + userCode + "'" 
                    /* 此处的[ORDER BY UploadFileSeq]非常重要，因为jsp的显示结果依赖这个顺序 */
                    + " ORDER BY UploadFileSeq,Trans_Seq"; 
            }
            else if("ABC".equals(isEiesFlag)){
                strCondition = " RevolutionFlag='0'" 
                    + " AND Invalid='0'"    
                    + " AND RiskCode='" + strRiskCode + "'" 
                    + " AND BANKCODE='103'"
                    + " AND ComCode ='" + icomCode + "'"
                    + " ORDER BY UploadFileSeq,Trans_Seq"; 
            }
            
            DBBankInterFace_Detail dbBankInterFace_Detail = new DBBankInterFace_Detail(dbManager);
            bankInterFace_DetailDtoList = (ArrayList)dbBankInterFace_Detail.findByConditions(strCondition,1,100);
            for(int i = 0; i < bankInterFace_DetailDtoList.size(); i++) {
                BankInterFace_DetailDto bankInterFace_DetailDto = null;
                
                
                
              
                BLProposal blProposal = null;
                
                
                
                String strRelation = ""; 
                String strPolicyNo = "";
                String strProposalNo = "";
                String strUseFor = "";

                
                bankInterFace_DetailDto = (BankInterFace_DetailDto)bankInterFace_DetailDtoList.get(i);                
                
                
                if("ABC".equals(isEiesFlag)){
                    strRelation = bankInterFace_DetailDto.getRelation();
                    strUseFor = bankInterFace_DetailDto.getGrade();
                }else{
                    strRelation = bankInterFace_DetailDto.getPhone();
                    strUseFor = bankInterFace_DetailDto.getUseFor();
                }
                
                
                
                
                
                
                /*blPrpDcustomer = generateObjectOfDcustomer(arrCustomerCode, bankInterFace_DetailDto,strUseFor);
                blPrpDcustomer.addCustomerList(dbPool);
                blPrpDcustomerIdv = generateObjectOfDcustomerIdv(arrCustomerCode, bankInterFace_DetailDto, strUseFor,isEiesFlag);
                blPrpDcustomerIdv.addCustomerIdvList(dbPool);*/
                
                
                strProposalNo = bankInterFace_DetailDto.getProposalNo();
                strPolicyNo = bankInterFace_DetailDto.getPolicyno();
                
                blProposal = generateObjectOfProposal(dbPool, arrCustomerCode, bankInterFace_DetailDto,vscode,strProposalNo,isEiesFlag);
                
                try{
                 blProposal.save(dbPool, "I", false);
                 dbPool.commitTransaction();
                }catch(Exception e1){
                	e1.printStackTrace();
                	flag = "1";
                	dbPool.rollbackTransaction();
                	throw e1; 
                }
                if(flag.equals("")){
                
                try{
                com.sp.undwrt.bl.facade.BLTaskFacade blTaskFacade = new com.sp.undwrt.bl.facade.BLTaskFacade();
                blTaskFacade.start("11","T",strProposalNo,"0302",
                        "03",bankInterFace_DetailDto.getMakeCom(),bankInterFace_DetailDto.getComCode(),
                        bankInterFace_DetailDto.getHandlerCode(),bankInterFace_DetailDto.getHandlerCode(),bankInterFace_DetailDto.getHandler1Code(),""); 
                
                bankInterFace_DetailDto.setRevolutionFlag("1");
                blBankInterFace_DetailAction.update(dbManager,bankInterFace_DetailDto);
                
                policyNoList.add(strPolicyNo);
                dbPool.commitTransaction();
                }catch(Exception e2){
                	dbPool.rollbackTransaction();
                	e2.printStackTrace();
                	throw e2; 
                }
               }
            }
               if(bankInterFace_DetailDtoList.size() <= 0) {
                    strReturnMessage = "没有可生成的XX单、XX！";
                } else {
                
                request.setAttribute("bankInterFace_DetailDtoList", bankInterFace_DetailDtoList);
                request.setAttribute("policyNoList", policyNoList);
                }    
           
            
            





























        }catch(Exception e) {
            dbPool.rollbackTransaction();
            throw e;
        }
        finally {
            dbPool.close();
        }

        return strReturnMessage;
    }

    /**
     * @desc 关爱1+家由接口表数据生成XX数据：获得XX人、被XX人XXXXX代码
     * @param dbPool
     * @param strComCode
     * @param strRelation
     * @param strUseFor
     * @return arrCustomerCode：arrCustomerCode[0] XX人代码/arrCustomerCode[1]被XXXXX人代码
     * @throws Exception
     */
  
    /*
    public String[] getCustomerCodes(DbPool dbPool, BankInterFace_DetailDto bankInterFace_DetailDto) throws Exception {
        BLPrpDcustomer blPrpDcustomer = new BLPrpDcustomer();
        String[] CustomerCode = new String[4];
        String strCustomerCode1 = "";
        String strCustomerCode2 = "";
        String strCode1 = "";
        String strCode2 = "";

        String appName = bankInterFace_DetailDto.getAppliName();
        String insueName = bankInterFace_DetailDto.getInsuredName();
        strCode1 = blPrpDcustomer.getPrpdCustomerId();
        if(appName.equals(insueName)){
        	strCustomerCode1 = strCode1;
        	strCustomerCode2 = strCode1;
        }else{
        	 
        	 
             strCode2=blPrpDcustomer.getPrpdCustomerId();
        	 strCustomerCode1 = strCode1;
         	 strCustomerCode2 = strCode2;
        }
        CustomerCode[0] = strCustomerCode1;
        CustomerCode[1] = strCustomerCode2;
        return CustomerCode;
    }*/

    /**
     * @desc 关爱1+家由接口表数据生成XX数据：生成XXXXX资料信息PrpDcustomer
     * @param arrCustomerCode
     * @param bankInterFace_DetailDto
     * @param strUseFor
     * @return blPrpDcustomer
     * @throws Exception
     */
    /*public BLPrpDcustomer generateObjectOfDcustomer(String[] arrCustomerCode, BankInterFace_DetailDto bankInterFace_DetailDto, String strUseFor) throws Exception {
        BLPrpDcustomer blPrpDcustomer = new BLPrpDcustomer();
        PrpDcustomerSchema prpDcustomerSchema1 = new PrpDcustomerSchema(); 
        PrpDcustomerSchema prpDcustomerSchema2 = new PrpDcustomerSchema(); 


        prpDcustomerSchema1.setCustomerType("1");
        prpDcustomerSchema1.setCustomerCode(arrCustomerCode[0]);
        prpDcustomerSchema1.setCustomerCName(bankInterFace_DetailDto.getAppliName());
        prpDcustomerSchema1.setValidStatus("1");
        blPrpDcustomer.setArr(prpDcustomerSchema1);
        
        String appName = bankInterFace_DetailDto.getAppliName();
        String insueName = bankInterFace_DetailDto.getInsuredName();
        if(!appName.equals(insueName)){
        	 prpDcustomerSchema2.setCustomerType("1");
             prpDcustomerSchema2.setCustomerCode(arrCustomerCode[1]);
             prpDcustomerSchema2.setCustomerCName(bankInterFace_DetailDto.getInsuredName());
             prpDcustomerSchema2.setValidStatus("1");
             blPrpDcustomer.setArr(prpDcustomerSchema2);
        }
        return blPrpDcustomer;
    }*/

    /**
     * @desc 关爱1+家由接口表数据生成XX数据：生成XXXXX资料信息PrpDcustomerIdv
     * @param arrCustomerCode
     * @param bankInterFace_DetailDto
     * @param strUseFor
     * @return blPrpDcustomerIdv
     * @throws Exception
     */
    /*public BLPrpDcustomerIdv generateObjectOfDcustomerIdv(String[] arrCustomerCode, BankInterFace_DetailDto bankInterFace_DetailDto, String strUseFor) throws Exception {
        BLPrpDcustomerIdv blPrpDcustomerIdv = new BLPrpDcustomerIdv();
        PrpDcustomerIdvSchema prpDcustomerIdvSchema1 = new PrpDcustomerIdvSchema();
        PrpDcustomerIdvSchema prpDcustomerIdvSchema2 = new PrpDcustomerIdvSchema();

        DateTime currentDate = new DateTime().current();
        String strCurrentDate = "";
        
        String strUploadDate = "";
        int intYear = 0;
        int intMonth = 0;
        int intDay = 0;

        intYear = currentDate.getYear();
        intMonth = currentDate.getMonth();
        intDay = currentDate.getDay();
        strCurrentDate = new Integer(intYear).toString() + "-"
                       + new Integer(intMonth).toString() + "-"
                       + new Integer(intDay).toString();
        
        
        intYear = bankInterFace_DetailDto.getUploadDate().getYear();
        intMonth = bankInterFace_DetailDto.getUploadDate().getMonth();
        intDay = bankInterFace_DetailDto.getUploadDate().getDay();
        strUploadDate = new Integer(intYear).toString() + "-"
                      + new Integer(intMonth).toString() + "-"
                      + new Integer(intDay).toString();

        prpDcustomerIdvSchema1.setCustomerCode(arrCustomerCode[0]);
        prpDcustomerIdvSchema1.setCustomerCName(bankInterFace_DetailDto.getAppliName());
        prpDcustomerIdvSchema1.setIdentifyType(bankInterFace_DetailDto.getIdType());
        prpDcustomerIdvSchema1.setIdentifyNumber(bankInterFace_DetailDto.getAppidNo());
        prpDcustomerIdvSchema1.setPhoneNumber(bankInterFace_DetailDto.getMobileCode());
        prpDcustomerIdvSchema1.setMobile(bankInterFace_DetailDto.getMobileCode());
        prpDcustomerIdvSchema1.setPostCode(bankInterFace_DetailDto.getPostCode());
        prpDcustomerIdvSchema1.setNewCustomerCode(arrCustomerCode[0]);
        prpDcustomerIdvSchema1.setValidStatus("1");
        prpDcustomerIdvSchema1.setLowerViewFlag("0");
        prpDcustomerIdvSchema1.setOperatorCode(bankInterFace_DetailDto.getHandlerCode());
        prpDcustomerIdvSchema1.setInputDate(strUploadDate);
        prpDcustomerIdvSchema1.setUpdaterCode(bankInterFace_DetailDto.getHandlerCode());
        prpDcustomerIdvSchema1.setUpdateDate(strCurrentDate);
        prpDcustomerIdvSchema1.setComcode(bankInterFace_DetailDto.getComCode());
        blPrpDcustomerIdv.setArr(prpDcustomerIdvSchema1);
        
        String appName = bankInterFace_DetailDto.getAppliName();
        String insueName = bankInterFace_DetailDto.getInsuredName();
        if(!appName.equals(insueName)){
        	prpDcustomerIdvSchema2.setCustomerCode(arrCustomerCode[1]);
            prpDcustomerIdvSchema2.setCustomerCName(bankInterFace_DetailDto.getInsuredName());
            prpDcustomerIdvSchema2.setIdentifyType("02");
            prpDcustomerIdvSchema2.setIdentifyNumber(bankInterFace_DetailDto.getAppidNo());
            prpDcustomerIdvSchema2.setPhoneNumber(bankInterFace_DetailDto.getInsuredPhone());
            prpDcustomerIdvSchema2.setMobile(bankInterFace_DetailDto.getInsuredPhone());
            prpDcustomerIdvSchema2.setPostCode(bankInterFace_DetailDto.getInsurepostCode());
            prpDcustomerIdvSchema2.setNewCustomerCode(arrCustomerCode[1]);
            prpDcustomerIdvSchema2.setValidStatus("1");
            prpDcustomerIdvSchema2.setLowerViewFlag("0");
            prpDcustomerIdvSchema2.setOperatorCode(bankInterFace_DetailDto.getHandlerCode());
            prpDcustomerIdvSchema2.setInputDate(strUploadDate);
            prpDcustomerIdvSchema2.setUpdaterCode(bankInterFace_DetailDto.getHandlerCode());
            prpDcustomerIdvSchema2.setUpdateDate(strCurrentDate);
            prpDcustomerIdvSchema2.setComcode(bankInterFace_DetailDto.getComCode());
            blPrpDcustomerIdv.setArr(prpDcustomerIdvSchema2);
        }

        return blPrpDcustomerIdv;
    }*/
  

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
    
    public BLProposal generateObjectOfProposal(DbPool dbPool,String[] arrCustomerCode, BankInterFace_DetailDto bankInterFace_DetailDto, String vscode, String arrProposalNo,String IsEiesFlag) throws Exception {
    
        DBManager dbManager = dbPool.getDBManager(SysConfig.CONST_DDCCDATASOURCE);

        BLProposal blProposal = new BLProposal();
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
        BLPrpTbatch blPrpTbatch = null;
        
        blPrpTinsured = generateObjectOfTinsured(arrCustomerCode, bankInterFace_DetailDto,arrProposalNo,IsEiesFlag);
        
        
        blPrpTmain = generateObjectOfTmain(arrCustomerCode, bankInterFace_DetailDto,vscode,arrProposalNo,IsEiesFlag); 
        
        
        blPrpTaddress = generateObjectOfTaddress(bankInterFace_DetailDto,arrProposalNo);
        blPrpTmainBank = generateObjectOfTmainBank(bankInterFace_DetailDto,arrProposalNo); 
 
        blPrpTitemKind = generateObjectOfTitemKind(bankInterFace_DetailDto,arrProposalNo,IsEiesFlag);

        blPrpTfee  = generateObjectOfTfee(bankInterFace_DetailDto,arrProposalNo,IsEiesFlag); 
        blPrpTplan = generateObjectOfTplan(bankInterFace_DetailDto,arrProposalNo);

        
        
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
        


        return blProposal;
    }

    /**
     * @desc 关爱1+家由接口表数据生成3套XX数据：生成XX单主信息对象BLPrpTmain
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
        
        
        DBPrpDcompany dbPrpDcompany = new DBPrpDcompany();
        String strChannelType = "";
        int iResult = 0;
        
        String strAgentCode = "";
        String strBankAgentCode = "";
        String strUserFor = "";
        blPrpdBankCompannyFacade = new BLPrpdBankCompannyFacade();
        strBankAgentCode = bankInterFace_DetailDto.getBankbranchCode(); 
        strUserFor = bankInterFace_DetailDto.getUseFor();
        
        if("ABC".equals(IsEiesFlag)){
            strUserFor=bankInterFace_DetailDto.getGrade();
            if("A".equals(strUserFor))
                strUserFor="01";
            else if("B".equals(strUserFor))
                strUserFor="02";
            else if("C".equals(strUserFor))
                strUserFor="03";
        }
        
        
        




            
            prpdBankCompannyDtoList = (ArrayList)blPrpdBankCompannyFacade.findByConditions(" BankCode='" + strBankAgentCode + "'");

        
        
        if(prpdBankCompannyDtoList.size() == 0)
			throw new UserException(-98, -1167, "BLGenerateData.generateObjectOfCommission",
			"prpdbankcompanny表"+strBankAgentCode+"取值失败,请管理员进行配置！" ); 
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
      	  throw new UserException(-98, -1167, "generateObjectOfTmain",
      	            "业务来源与渠道校验失败，请检查相关配置！" ); 
        }
        
        prpDagreeDetailDto = blPrpDagreeDetailFacade.findByPrimaryKey(strAgreementNo, bankInterFace_DetailDto.getRiskCode(),"0000");

        if(prpDagreeDetailDto==null)
			throw new UserException(-98, -1167, "BLGenerateYjData.generateObjectOfTmain",
			"手续费比例取值失败,请管理员进行配置！" ); 
        if((String.valueOf(prpDagreeDetailDto.getTopCommission())).trim().equals("")
           ||prpDagreeDetailDto.getTopCommission()<0){
			throw new UserException(-98, -1167, "BLGenerateYjData.generateObjectOfTmain",
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
        	prpTmainSchema.setSumAmount("500000");
        }else if("02".equals(strUserFor)){
        	prpTmainSchema.setSumAmount("750000");
        }else if("03".equals(strUserFor)){
        	prpTmainSchema.setSumAmount("1200000");
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
     
        prpTmainSchema.setOperateSite(bankInterFace_DetailDto.getMakeCom());

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

        prpTmainSchema.setVisaCode(bankInterFace_DetailDto.getVisacode());
        
        if("ABC".equals(IsEiesFlag)){
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
        
		


		if("1".equals(bankInterFace_DetailDto.getIseiesflag())&&dbPolicyFee!=0.0){
			double tempValue =(dbPolicyFee*100)/bankInterFace_DetailDto.getSumPremium();
			newDisrate = tempValue+dbDisrate;
			
			newDisrate = Str.round(newDisrate,4);
			prpTmainSchema.setDisRate(String.valueOf(newDisrate));
		}else if("2".equals(bankInterFace_DetailDto.getIseiesflag())){
			
		}

		

        blPrpTmain.setArr(prpTmainSchema);

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
        if("ABC".equals(IsEiesFlag)){
           prpTinsuredSchema2.setIdentifyType(bankInterFace_DetailDto.getInsuredType());
           prpTinsuredSchema2.setIdentifyNumber(bankInterFace_DetailDto.getInsuredNumber());
        }
        
        arrCustomerCode[1] = blPrpDcustomerIdvNew.createCustomer(prpTinsuredSchema2, strComcode, strOperCode,strHandler1Code);
        prpTinsuredSchema2.setInsuredCode(arrCustomerCode[1]);
        
        blPrpTinsured.setArr(prpTinsuredSchema2);
             	
      

        return blPrpTinsured;
    }

    /**
     * @desc 关爱1+家由接口表数据生成XX数据：生成XX单地址信息对象BLPrpTaddress
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
     * @desc 关爱1+家由接口表数据生成XX数据：生成XX单投资信息对象BLPrpTmainInvest
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
     * @desc 关爱1+家由接口表数据生成XX数据：生成关联XX表对象BLPrpTmainSub
     * @param bankInterFace_DetailDto
     * @param dblInterestRate
     * @param arrProposalNo
     * @return blPrpTmainInvest
     * @throws Exception
     */






















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
        
        if("ABC".equals(IsEiesFlag)){
            strUserFor=bankInterFace_DetailDto.getGrade();
            if("A".equals(strUserFor))
                strUserFor="01";
            else if("B".equals(strUserFor))
                strUserFor="02";
            else if("C".equals(strUserFor))
                strUserFor="03";
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
        			prpTitemKindSchema.setAmount("500000");
        		}else if ("02".equals(strUserFor)){
        			prpTitemKindSchema.setAmount("750000");
        		}else if ("03".equals(strUserFor)){
        			prpTitemKindSchema.setAmount("1200000");
        		}
        		prpTitemKindSchema.setRate("");
                prpTitemKindSchema.setShortRate("100.0000");
                prpTitemKindSchema.setShortRateFlag("3");
        		prpTitemKindSchema.setPremium(String.valueOf(bankInterFace_DetailDto.getSumPremium()));
                if("ABC".equals(IsEiesFlag)){
                     prpTitemKindSchema.setValue(bankInterFace_DetailDto.getInvestCount()+"");
                }
        		blPrpTitemKind.setArr(prpTitemKindSchema);

        
        return blPrpTitemKind ;
     }
    
   /**
    * @desc 关爱1+家由接口表数据生成XX数据：生成XX单XXXXX别信息对象BLPrpTinsuredNature
    * @param bankInterFace_DetailDto
    * @param arrProposalNo
    * @return BLPrpTinsuredNature
    * @throws Exception
    */














































































































    /**
     * @desc 关爱1+家由接口表数据生成XX数据：生成XX单金额信息对象BLPrpTfee
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
        
        if("ABC".equals(IsEiesFlag)){
            strUserFor=bankInterFace_DetailDto.getGrade();
            if("A".equals(strUserFor))
                strUserFor="01";
            else if("B".equals(strUserFor))
                strUserFor="02";
            else if("C".equals(strUserFor))
                strUserFor="03";
        }
        
        prpTfeeSchema.setProposalNo(arrProposalNo);
        prpTfeeSchema.setRiskCode(strRiskCode);
        prpTfeeSchema.setCurrency(bankInterFace_DetailDto.getCurrency());
        if("01".equals(strUserFor)){
        	 prpTfeeSchema.setAmount("500000");
        	 prpTfeeSchema.setAmount1("500000");
        	 prpTfeeSchema.setAmount2("500000");
        }else if ("02".equals(strUserFor)){
        	 prpTfeeSchema.setAmount("750000");
        	 prpTfeeSchema.setAmount1("750000");
        	 prpTfeeSchema.setAmount2("750000");
        }else if ("03".equals(strUserFor)){ 
        	 prpTfeeSchema.setAmount("1200000");
        	 prpTfeeSchema.setAmount1("1200000");
        	 prpTfeeSchema.setAmount2("1200000");
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
     * @desc 关爱1+家由接口表数据生成XX数据：生成BLPrpTmainCasualty对象
     * @param bankInterFace_DetailDto
     * @param arrProposalNo
     * @param strUseFor
     * @return
     * @throws Exception
     */
    


























    /**
     * 
     * @desc 关爱1+家由接口表数据生成XX数据：生成prpTbatch对象
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
        
        if("ABC".equals(IsEiesFlag)){
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

}
