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
import com.sp.platform.bl.facade.BLPrpDbankFacade;
import com.sp.platform.dto.domain.BankInterFace_DetailDto;
import com.sp.platform.dto.domain.PrpDagreementDto;
import com.sp.platform.dto.domain.PrpDagreeDetailDto;
import com.sp.platform.bl.action.domain.BLBankInterFace_DetailAction;
import com.sp.platform.bl.action.domain.BLPrpDkindAction;
import com.sp.platform.bl.action.domain.BLPrpDbankInvestAction;
import com.sp.platform.bl.action.domain.BLPrpDbankRateAction;
import com.sp.platform.bl.action.domain.BLPrpDfloatYieldAction;
import com.sp.platform.dto.domain.PrpDbankDto;
import com.sp.platform.dto.domain.PrpDbankInvestDto;
import com.sp.platform.dto.domain.PrpDbankRateDto;
import com.sp.platform.dto.domain.PrpDfloatYieldDto;
import com.sp.platform.dto.domain.PrpDkindDto;
import com.sp.platform.resource.dtofactory.domain.DBBankInterFace_Detail;
import com.sp.utiall.blsvr.BLPrpDcustomer;
import com.sp.utiall.blsvr.BLPrpDcustomerIdv;
import com.sp.utiall.schema.PrpDcustomerIdvSchema;
import com.sp.utiall.schema.PrpDcustomerSchema;
import com.sp.prpall.blsvr.cb.BLPolicy;
import com.sp.prpall.blsvr.cb.BLPolicyOrigin;
import com.sp.prpall.blsvr.cb.BLPrpCaddress;
import com.sp.prpall.blsvr.cb.BLPrpCfee;
import com.sp.prpall.blsvr.cb.BLPrpCinsured;
import com.sp.prpall.blsvr.cb.BLPrpCitemKind;
import com.sp.prpall.blsvr.cb.BLPrpCmain;
import com.sp.prpall.blsvr.cb.BLPrpCmainInvest;
import com.sp.prpall.blsvr.cb.BLPrpCplan;
import com.sp.prpall.blsvr.jf.BLPrpJinvest;
import com.sp.prpall.blsvr.tb.BLProposal;
import com.sp.prpall.blsvr.tb.BLPrpTaddress;
import com.sp.prpall.blsvr.tb.BLPrpTfee;
import com.sp.prpall.blsvr.tb.BLPrpTinsured;
import com.sp.prpall.blsvr.tb.BLPrpTitemKind;
import com.sp.prpall.blsvr.tb.BLPrpTmain;
import com.sp.prpall.blsvr.tb.BLPrpTmainInvest;
import com.sp.prpall.blsvr.tb.BLPrpTplan;
import com.sp.prpall.dbsvr.cb.DBPrpCmain;
import com.sp.prpall.dbsvr.misc.DBInvest;
import com.sp.prpall.interf.Visa;
import com.sp.prpall.schema.PrpCaddressSchema;
import com.sp.prpall.schema.PrpCfeeSchema;
import com.sp.prpall.schema.PrpCinsuredSchema;
import com.sp.prpall.schema.PrpCitemKindSchema;
import com.sp.prpall.schema.PrpCmainInvestSchema;
import com.sp.prpall.schema.PrpCmainSchema;
import com.sp.prpall.schema.PrpCplanSchema;
import com.sp.prpall.schema.PrpCommissionSchema;
import com.sp.prpall.schema.PrpJinvestSchema;
import com.sp.prpall.schema.PrpTaddressSchema;
import com.sp.prpall.schema.PrpTfeeSchema;
import com.sp.prpall.schema.PrpTinsuredSchema;
import com.sp.prpall.schema.PrpTitemKindSchema;
import com.sp.prpall.schema.PrpTmainInvestSchema;
import com.sp.prpall.schema.PrpTmainSchema;
import com.sp.prpall.schema.PrpTplanSchema;
import com.sp.prpall.pubfun.Bill;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 定义投联XXXXXBLInvest类
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * 
 * <p>@createdate 2007-03-21</p>
 * @author Luyang
 * @version 1.0
 */
public class BLInvest {

	protected final Log logger = LogFactory.getLog(getClass());

	/*
	 * 本类完成的功能： 将BankInterFace_Detail表中当前所有的数据转换为XX数据，并送相关数据到收付的接口表中
	 * for(BankInterFace_Detail) { 1、获得BankInterFace_Detail表数据 2、获得XXXXX代码
	 * 3、生成XXXXX资料数据 4、生成XX单数据 5、获得XX号码 6、生成XX数据 7、生成XXXXX接口表数据
	 * 8、修改BankInterFace_Detail表数据 9、反写BankInterFace_Detail表中的数据
	 * 10、11、将对象放入Attribute中 }
	 */

	public BLInvest() {
    }

	/**
	 * @desc 投联XXXXX由接口表数据生成XX数据
	 * @param request
	 * @param response
	 * @param strRiskCode
	 * @param strComCode
	 * @return strReturnMessage：size=0 没有可生成的XX单、XX/size>0 空字符串，将对象放入Attribute中
	 * @throws Exception
	 */
    public String build(HttpServletRequest request, HttpServletResponse response, String strRiskCode, String strComCode, String userCode ,String vscode) throws Exception {
        DbPool dbPool = new DbPool();
        DBManager dbManager = null;
        BLBankInterFace_DetailAction blBankInterFace_DetailAction = new BLBankInterFace_DetailAction();
        ArrayList bankInterFace_DetailDtoList = null;
        ArrayList policyNoList = new ArrayList();
        String strSessionId = request.getSession().getId();
        String strCondition = "";
        String strReturnMessage = "";
        
        String strTOCOMCODE = "";     
        String strTOCENTERCODE = "";  
        String isScenePolicy = "Y";    
        
        try {
			
			if ("1".equals(SysConfig.getProperty("PLAT_FORM_NEW_DATA_SOURCE"))) {
				dbPool.open(SysConfig.CONST_PLATFORMNEWDATASOURCE);
				dbPool.beginTransaction();
				dbManager = dbPool
						.getDBManager(SysConfig.CONST_PLATFORMNEWDATASOURCE);
			} else {
				dbPool.open(SysConfig.CONST_DDCCDATASOURCE);
				dbPool.beginTransaction();
				dbManager = dbPool.getDBManager(SysConfig.CONST_DDCCDATASOURCE);
			}
			

            if(vscode.equals("EIES")){
            strCondition = " RevolutionFlag='0'" 
                         + " AND Invalid='0'"    
            	         + " AND IseiesFlag='1'" 
                         + " AND RiskCode='" + strRiskCode + "'" 

                         + " AND HandlerCode='" + userCode + "'" 

                         /* 此处的[ORDER BY UploadFileSeq]非常重要，因为jsp的显示结果依赖这个顺序 */
                         + " ORDER BY UploadFileSeq,Trans_Seq"; 
            }else{
            strCondition = " RevolutionFlag='0'" 
            		 + " AND Invalid='0'"    
                     + " AND IseiesFlag='0'" 
                     + " AND RiskCode='" + strRiskCode + "'" 

                     + " AND HandlerCode='" + userCode + "'" 

                     /* 此处的[ORDER BY UploadFileSeq]非常重要，因为jsp的显示结果依赖这个顺序 */
                     + " ORDER BY UploadFileSeq,Trans_Seq"; 
            }


            DBBankInterFace_Detail dbBankInterFace_Detail = new DBBankInterFace_Detail(dbManager);

            bankInterFace_DetailDtoList = (ArrayList)dbBankInterFace_Detail.findByConditions(strCondition,1,100);

            for(int i = 0; i < bankInterFace_DetailDtoList.size(); i++) {
                BankInterFace_DetailDto bankInterFace_DetailDto = null;
                BLPrpDcustomer blPrpDcustomer = null;
                BLPrpDcustomerIdv blPrpDcustomerIdv = null;

                BLProposal blProposal = null;
                BLPolicy blPolicy = null;
                
                BLPrpCommission blPrpCommission = null;
             	BLPrpCommissionDetail blPrpCommissionDetail = new BLPrpCommissionDetail();
                DBPrpCmain dbPrpCmain = new DBPrpCmain();
                
                BLPolicyOrigin blPolicyOrigin = null;

                BLPrpJinvest blPrpJinvest = null;

                String[] arrCustomerCode = new String[2];
                String strRelation = ""; 
                String strPolicyNo = "";
                
                String strIsEiesFlag = "";
                

                
                bankInterFace_DetailDto = (BankInterFace_DetailDto)bankInterFace_DetailDtoList.get(i);                
                
                strRelation = bankInterFace_DetailDto.getRelation();
                
                strIsEiesFlag = bankInterFace_DetailDto.getIseiesFlag();
                if(strIsEiesFlag.equals("1"))
                {
                	vscode= bankInterFace_DetailDto.getVisacode();
                }
                
                arrCustomerCode = getCustomerCodes(dbPool, strComCode, strRelation);
                
                blPrpDcustomer = generateObjectOfDcustomer(arrCustomerCode, bankInterFace_DetailDto);
                blPrpDcustomer.addCustomerList(dbPool);
                blPrpDcustomerIdv = generateObjectOfDcustomerIdv(arrCustomerCode, bankInterFace_DetailDto);
                blPrpDcustomerIdv.addCustomerIdvList(dbPool);
                
                blProposal = generateObjectOfProposal(dbPool, arrCustomerCode, bankInterFace_DetailDto,vscode);
                blProposal.save(dbPool, "I", false);
                
                strPolicyNo = bankInterFace_DetailDto.getPolicyno();
                
                if(strPolicyNo.equals(""))
                {
                	strPolicyNo = getPolicyNo(dbPool, strComCode, strSessionId, blProposal.getBLPrpTmain());
                	
                	isScenePolicy = "N"; 
                    
                }
                
                
                blPolicy = generateObjectOfPolicy(strPolicyNo, blProposal);
                blPolicy.save(dbPool);
                blPolicyOrigin = new BLPolicyOrigin();
                blPolicyOrigin.policyToOriginPolicy(dbPool, blPolicy);
                
                
                
                blPrpCommission = generateObjectOfCommission(bankInterFace_DetailDto, blPolicy);
                blPrpCommission.save(dbPool);
                dbPrpCmain.getInfo(dbPool,strPolicyNo);
                blPrpCommissionDetail.createCommissionCDetail(dbPool,blPrpCommission,dbPrpCmain);
                blPrpCommissionDetail.save(dbPool);
                
                
                
                
                if(isScenePolicy.equals("Y")){
                BLPrpDbankFacade blPrpDbankFacade = new BLPrpDbankFacade();
                ArrayList prpDbankDtoList = null;
                String strBankCode = bankInterFace_DetailDto.getBankbranchCode();
                String condition = " BankCode='" + strBankCode + "'";
                prpDbankDtoList = (ArrayList)blPrpDbankFacade.findByConditions(condition);
                 if(prpDbankDtoList.size() > 0) 
                 {
                	strTOCOMCODE = ((PrpDbankDto)prpDbankDtoList.get(0)).getComCode();
                    
                    logger.info("strTOCOMCODE======现场出单===="+strTOCOMCODE);
                    
                 }else
                 {
                	/*throw new UserException(-98, -1167, "BLInvest.build",
        					"PrpDbank表中没有配置银行代码"+strBankCode+"对应的数据" );  */
                	
                 }
                }   
                else
                {
                	strTOCOMCODE = bankInterFace_DetailDto.getComCode();
                	
                	logger.info("strTOCOMCODE======非现场======"+strTOCOMCODE);
                	
                }
                
                
                
                blPrpJinvest = new BLPrpJinvest();
                bankInterFace_DetailDto.setRevolutionFlag(strTOCOMCODE);
                blPrpJinvest.generateObjectOfJinvest(dbPool,bankInterFace_DetailDto, blPolicy);
               
                blPrpJinvest.save(dbPool);
                
                bankInterFace_DetailDto.setRevolutionFlag("1");
                blBankInterFace_DetailAction.update(dbManager, bankInterFace_DetailDto);
                
                bankInterFace_DetailDto.setRevolutionFlag("1");
                blBankInterFace_DetailAction.update(dbManager,bankInterFace_DetailDto);
                
                policyNoList.add(strPolicyNo);
            }
            if(bankInterFace_DetailDtoList.size() <= 0) {
                strReturnMessage = "没有可生成的XX单、XX！";
            } else {
                
                request.setAttribute("bankInterFace_DetailDtoList", bankInterFace_DetailDtoList);
                request.setAttribute("policyNoList", policyNoList);
            }           
            dbPool.commitTransaction();
            
            for(int i = 0; i < bankInterFace_DetailDtoList.size(); i++) 
            {     
            	BankInterFace_DetailDto bankInterFaceDetailDto = null;
            	bankInterFaceDetailDto = (BankInterFace_DetailDto)bankInterFace_DetailDtoList.get(i);
            	String comcode = "";
            	String riskcode = "";
            	String usercode = "";
            	String printno = "";
                
                String strIsEiesFlag = "";
                
            	comcode = bankInterFaceDetailDto.getComCode();
            	riskcode = bankInterFaceDetailDto.getRiskCode();
            	strIsEiesFlag = bankInterFaceDetailDto.getIseiesFlag();
                if(strIsEiesFlag.equals("1"))
                {
                	 usercode = bankInterFaceDetailDto.getVisaUserCode();
                	 
                	 logger.info("VisaUsercode=======银XXXXX通单证归属人员===="+usercode);
                	 
                }
                else
                {
            	      usercode = bankInterFaceDetailDto.getHandlerCode();
            	      
            	      logger.info("VisaUsercode======富安居单证归属人员===="+usercode);
            	      
                }
            	printno = bankInterFaceDetailDto.getPrintno();
            	Visa visaintef = new Visa();
            	if(!bankInterFaceDetailDto.getPolicyno().equals(""))
            	{
            		if(visaintef.checkUsedReady(usercode,vscode,printno))
            		{
            			visaintef.useTrans(usercode,vscode,printno,bankInterFaceDetailDto.getPolicyno());
            		}
            		else
            		{
            			strReturnMessage += bankInterFaceDetailDto.getPolicyno()+"、";
            		}
            		if(!strReturnMessage.equals(""))
            		{
            			if(i==bankInterFace_DetailDtoList.size()-1)
            			{
            				strReturnMessage = strReturnMessage.substring(0,strReturnMessage.lastIndexOf("、"));
            				strReturnMessage += "号XX单证销号不成功，请检查流水号是否可用！";
            			}
            		}
            	}
            }
            
        } catch(Exception e) {
            dbPool.rollbackTransaction();
            throw e;
        }
        finally {
            dbPool.close();
        }

        return strReturnMessage;
    }

    /**
     * @desc 投联XXXXX由接口表数据生成XX数据：获得XX人、被XX人XXXXX代码
     * @param dbPool
     * @param strComCode
     * @param strRelation
     * @return arrCustomerCode：arrCustomerCode[0] XX人代码/arrCustomerCode[1] 被XX人代码
     * @throws Exception
     */
    public String[] getCustomerCodes(DbPool dbPool, String strComCode, String strRelation) throws Exception {
        BLPrpDcustomer blPrpDcustomer = new BLPrpDcustomer();
        String[] arrCustomerCode = new String[2];
        String strCustomerCode1 = "";
        String strCustomerCode2 = "";
        /* modify by yangkun 20071229 17:56 begin reason: 调整投联XXXXX由接口表数据生成XX数据时调用取oracle序列处理 */
        strCustomerCode1 = blPrpDcustomer.getPrpdCustomerId();
        if("01".equals(strRelation)) {
            strCustomerCode2 = strCustomerCode1;
        } else {
            long longCustomerCode2 = Long.parseLong(blPrpDcustomer.getPrpdCustomerId());
            strCustomerCode2 = new BigDecimal(longCustomerCode2).toString();
        }
        /* modify by yangkun 20071229 17:56 end reason: 调整投联XXXXX由接口表数据生成XX数据时调用取oracle序列处理 */
        arrCustomerCode[0] = strCustomerCode1;
        arrCustomerCode[1] = strCustomerCode2;
        return arrCustomerCode;
    }

    /**
     * @desc 投联XXXXX由接口表数据生成XX数据：生成XXXXX资料信息PrpDcustomer
     * @param arrCustomerCode
     * @param bankInterFace_DetailDto
     * @return blPrpDcustomer
     * @throws Exception
     */
    public BLPrpDcustomer generateObjectOfDcustomer(String[] arrCustomerCode, BankInterFace_DetailDto bankInterFace_DetailDto) throws Exception {
        BLPrpDcustomer blPrpDcustomer = new BLPrpDcustomer();
        PrpDcustomerSchema prpDcustomerSchema1 = new PrpDcustomerSchema();
        PrpDcustomerSchema prpDcustomerSchema2 = new PrpDcustomerSchema();

        prpDcustomerSchema1.setCustomerType("1");
        prpDcustomerSchema1.setCustomerCode(arrCustomerCode[0]);
        prpDcustomerSchema1.setCustomerCName(bankInterFace_DetailDto.getAppliName());
        prpDcustomerSchema1.setAddressCName(bankInterFace_DetailDto.getAddress());
        prpDcustomerSchema1.setValidStatus("1");
        blPrpDcustomer.setArr(prpDcustomerSchema1);

        if(!arrCustomerCode[1].equals(arrCustomerCode[0])) {
            prpDcustomerSchema2.setCustomerType("1");
            prpDcustomerSchema2.setCustomerCode(arrCustomerCode[1]);
            prpDcustomerSchema2.setCustomerCName(bankInterFace_DetailDto.getInsuredName());
            prpDcustomerSchema2.setAddressCName(bankInterFace_DetailDto.getInsuredAdress());
            prpDcustomerSchema2.setValidStatus("1");
            blPrpDcustomer.setArr(prpDcustomerSchema2);
        }

        return blPrpDcustomer;
    }

    /**
     * @desc 投联XXXXX由接口表数据生成XX数据：生成XXXXX资料信息PrpDcustomerIdv
     * @param arrCustomerCode
     * @param bankInterFace_DetailDto
     * @return blPrpDcustomerIdv
     * @throws Exception
     */
    public BLPrpDcustomerIdv generateObjectOfDcustomerIdv(String[] arrCustomerCode, BankInterFace_DetailDto bankInterFace_DetailDto) throws Exception {
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
        prpDcustomerIdvSchema1.setAddressCName(bankInterFace_DetailDto.getAddress());
        prpDcustomerIdvSchema1.setIdentifyType(bankInterFace_DetailDto.getIdType());
        prpDcustomerIdvSchema1.setIdentifyNumber(bankInterFace_DetailDto.getAppidNo());
        prpDcustomerIdvSchema1.setPhoneNumber(bankInterFace_DetailDto.getMobileCode());
        prpDcustomerIdvSchema1.setMobile(bankInterFace_DetailDto.getMobileCode());
        prpDcustomerIdvSchema1.setLinkAddress(bankInterFace_DetailDto.getAddress());
        prpDcustomerIdvSchema1.setPostCode(bankInterFace_DetailDto.getPostCode());
        prpDcustomerIdvSchema1.setEmail(bankInterFace_DetailDto.getEmail());
        prpDcustomerIdvSchema1.setNewCustomerCode(arrCustomerCode[0]);
        prpDcustomerIdvSchema1.setValidStatus("1");
        prpDcustomerIdvSchema1.setLowerViewFlag("0");
        prpDcustomerIdvSchema1.setOperatorCode(bankInterFace_DetailDto.getHandlerCode());
        
        prpDcustomerIdvSchema1.setInputDate(strUploadDate);
        
        prpDcustomerIdvSchema1.setUpdaterCode(bankInterFace_DetailDto.getHandlerCode());
        prpDcustomerIdvSchema1.setUpdateDate(strCurrentDate);
        prpDcustomerIdvSchema1.setComcode(bankInterFace_DetailDto.getComCode());
        blPrpDcustomerIdv.setArr(prpDcustomerIdvSchema1);

        if(!arrCustomerCode[1].equals(arrCustomerCode[0])) {
            prpDcustomerIdvSchema2.setCustomerCode(arrCustomerCode[1]);
            prpDcustomerIdvSchema2.setCustomerCName(bankInterFace_DetailDto.getInsuredName());
            prpDcustomerIdvSchema2.setAddressCName(bankInterFace_DetailDto.getInsuredAdress());
            prpDcustomerIdvSchema2.setPhoneNumber(bankInterFace_DetailDto.getInsuredPhone());
            prpDcustomerIdvSchema2.setMobile(bankInterFace_DetailDto.getInsuredPhone());
            prpDcustomerIdvSchema2.setLinkAddress(bankInterFace_DetailDto.getInsuredAdress());
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
    }

    /**
     * @desc 投联XXXXX由接口表数据生成XX数据：生成XX单对象BLProposal
     * @param dbPool
     * @param arrCustomerCode
     * @param bankInterFace_DetailDto
     * @return blProposal
     * @throws Exception
     */
    public BLProposal generateObjectOfProposal(DbPool dbPool,String[] arrCustomerCode, BankInterFace_DetailDto bankInterFace_DetailDto, String vscode) throws Exception {
        DBManager dbManager = dbPool.getDBManager(SysConfig.CONST_DDCCDATASOURCE);

        BLProposal blProposal = new BLProposal();
        BLPrpTmain blPrpTmain = null;
        BLPrpTinsured blPrpTinsured = null;
        BLPrpTaddress blPrpTaddress = null;
        BLPrpTmainInvest blPrpTmainInvest = null;
        BLPrpTitemKind blPrpTitemKind = null;
        BLPrpTfee blPrpTfee = null;
        BLPrpTplan blPrpTplan = null;

        DateTime currentDate = new DateTime().current();
        String strCurrentDate = "";
        String strCondition = "";

        strCurrentDate = new Integer(currentDate.getYear()).toString() + "-"
                       + new Integer(currentDate.getMonth()).toString() + "-"
                       + new Integer(currentDate.getDay()).toString();

        
        BLPrpDbankInvestAction blPrpDbankInvestAction = new BLPrpDbankInvestAction();
        PrpDbankInvestDto prpDbankInvestDto = new PrpDbankInvestDto();
        strCondition = " RiskCode='" + bankInterFace_DetailDto.getRiskCode() + "'"
        
                     + " AND ComCode='" + bankInterFace_DetailDto.getHandler1Code() + "'"
        
                     + " AND StartDate<='" + strCurrentDate + "'"
                     + " AND EndDate>='" + strCurrentDate + "'"
                     + " AND InureFlag='1'"
                     + " ORDER BY StartDate DESC";
        ArrayList bankInvestDtoList = (ArrayList)blPrpDbankInvestAction.findByConditions(dbManager, strCondition);
        if(bankInvestDtoList.size() > 0) {
            prpDbankInvestDto = (PrpDbankInvestDto)bankInvestDtoList.get(0);
        }

        
        BLPrpDbankRateAction blPrpDbankRateAction = new BLPrpDbankRateAction();
        BLPrpDfloatYieldAction blPrpDfloatYieldAction = new BLPrpDfloatYieldAction();
        PrpDbankRateDto prpDbankRateDto = new PrpDbankRateDto();
        PrpDfloatYieldDto prpDfloatYieldDto = new PrpDfloatYieldDto();
        double dblInterestRate = 0; 
        strCondition = " Currency='" + bankInterFace_DetailDto.getCurrency() + "'"
                     + " AND SavePeriod='" + prpDbankInvestDto.getPeriod()+"'"
                     + " AND ValidDate<='" + strCurrentDate + "'"
                     + " AND ValidStatus='1'"
                     + " ORDER BY ValidDate DESC";
        ArrayList bankRateDtoList = (ArrayList)blPrpDbankRateAction.findByConditions(dbManager,strCondition);
        if(bankRateDtoList.size() > 0) {
            prpDbankRateDto = (PrpDbankRateDto)bankRateDtoList.get(0);
        }

        strCondition = " RiskCode='" + bankInterFace_DetailDto.getRiskCode() + "'"
                     + " AND Currency='" + bankInterFace_DetailDto.getCurrency() + "'"
                     + " AND ValidDate<='" + strCurrentDate + "'"
                     + " AND ValidStatus='1'"
                     + " ORDER BY ValidDate DESC";
        ArrayList floatYieldDtoList = (ArrayList)blPrpDfloatYieldAction.findByConditions(dbManager,strCondition);
        if(floatYieldDtoList.size() > 0) {
            prpDfloatYieldDto = (PrpDfloatYieldDto)floatYieldDtoList.get(0);
        }
        dblInterestRate = prpDbankRateDto.getBankRate() + prpDfloatYieldDto.getFloatRate();

        
        BLPrpDkindAction blPrpDkindAction = new BLPrpDkindAction();
        PrpDkindDto prpDkindDto = new PrpDkindDto();
        strCondition = " RiskCode='"+ bankInterFace_DetailDto.getRiskCode() + "'"
                     + " AND KindCode='001'";
        prpDkindDto =  blPrpDkindAction.findByPrimaryKey(dbManager, bankInterFace_DetailDto.getRiskCode(), "001");

        blPrpTmain = generateObjectOfTmain(arrCustomerCode, bankInterFace_DetailDto, prpDbankInvestDto,vscode); 
        blPrpTinsured = generateObjectOfTinsured(arrCustomerCode, bankInterFace_DetailDto);
        blPrpTaddress = generateObjectOfTaddress(bankInterFace_DetailDto);
        blPrpTmainInvest = generateObjectOfTmainInvest(bankInterFace_DetailDto, prpDbankInvestDto, dblInterestRate); 
        blPrpTitemKind = generateObjectOfTitemKind(bankInterFace_DetailDto, prpDbankInvestDto, prpDkindDto);
        blPrpTfee = generateObjectOfTfee(bankInterFace_DetailDto, prpDbankInvestDto); 
        
        blPrpTplan = generateObjectOfTplanNew(bankInterFace_DetailDto,prpDbankInvestDto);
        
        blProposal.setBLPrpTmain(blPrpTmain);
        blProposal.setBLPrpTinsured(blPrpTinsured);
        blProposal.setBLPrpTaddress(blPrpTaddress);
        blProposal.setBLPrpTmainInvest(blPrpTmainInvest);
        blProposal.setBLPrpTitemKind(blPrpTitemKind);
        blProposal.setBLPrpTfee(blPrpTfee);
        blProposal.setBLPrpTplan(blPrpTplan);

        return blProposal;
    }

    /**
     * @desc 投联XXXXX由接口表数据生成XX数据：生成XX单主信息对象BLPrpTmain
     * @param arrCustomerCode
     * @param bankInterFace_DetailDto
     * @param prpDbankInvestDto
     * @return blPrpTmain
     * @throws Exception
     */
    public BLPrpTmain generateObjectOfTmain(String[] arrCustomerCode, BankInterFace_DetailDto bankInterFace_DetailDto, PrpDbankInvestDto prpDbankInvestDto, String visacode) throws Exception {
        BLPrpDagreementFacade blPrpDagreementFacade = new BLPrpDagreementFacade();
        PrpDagreementDto prpDagreementDto = null;
        BLPrpTmain blPrpTmain = new BLPrpTmain();
        PrpTmainSchema prpTmainSchema = new PrpTmainSchema();

        Collection prpDagreementDtoList = null;
        DateTime currentDate = new DateTime().current();
        
        String strBankCode = "";
        
        String strAgreementNo = "";
        String strStartDatePrpT = "";
        String strEndDatePrpT = "";
        
        String strUploadDate  = "";
        
        String strCurrentDate = "";
        String strUnderWriteEndDate = "";
        
        String strIsEiesFlag = "";
        

        int intYear = 0;
        int intMonth = 0;
        int intDay = 0;

        
        
        strBankCode = bankInterFace_DetailDto.getBankcode();
        prpDagreementDtoList = blPrpDagreementFacade.findByConditions("AgentCode='" + strBankCode + "'");
        
        
        strIsEiesFlag = bankInterFace_DetailDto.getIseiesFlag();
        
        for(Iterator prpDagreementDtoListTmp = prpDagreementDtoList.iterator(); prpDagreementDtoListTmp.hasNext();) {
            prpDagreementDto = (PrpDagreementDto)prpDagreementDtoListTmp.next();
            strAgreementNo = prpDagreementDto.getAgreementNo();
            break;
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

        intYear = bankInterFace_DetailDto.getInvestDate().getYear();
        intMonth = bankInterFace_DetailDto.getInvestDate().getMonth();
        intDay = bankInterFace_DetailDto.getInvestDate().getDay();
        strUnderWriteEndDate = new Integer(intYear).toString() + "-"
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
        prpTmainSchema.setDisRate(new Double(bankInterFace_DetailDto.getPoundageRate()).toString());
        prpTmainSchema.setDiscount("100");
        prpTmainSchema.setCurrency(bankInterFace_DetailDto.getCurrency());
        prpTmainSchema.setSumValue("0");
        prpTmainSchema.setSumAmount(String.valueOf(bankInterFace_DetailDto.getInvestCount() * prpDbankInvestDto.getUnitAmount()));
        prpTmainSchema.setSumDiscount("0");
        prpTmainSchema.setSumPremium(String.valueOf(bankInterFace_DetailDto.getInvestCount() * prpDbankInvestDto.getUnitPremium()));
        prpTmainSchema.setSumSubPrem("0");
        prpTmainSchema.setAutoTransRenewFlag("2");
        prpTmainSchema.setPayTimes("1");
        prpTmainSchema.setEndorseTimes("0");
        prpTmainSchema.setClaimTimes("0");
        prpTmainSchema.setMakeCom(bankInterFace_DetailDto.getMakeCom());
        prpTmainSchema.setOperateSite(bankInterFace_DetailDto.getMakeCom());
        prpTmainSchema.setComCode(bankInterFace_DetailDto.getHandler1Code());
        prpTmainSchema.setHandlerCode(bankInterFace_DetailDto.getHandlerCode());
        prpTmainSchema.setHandler1Code(bankInterFace_DetailDto.getHandlerCode()); 
        prpTmainSchema.setOperatorCode(bankInterFace_DetailDto.getHandlerCode());
        
        prpTmainSchema.setInputDate(strUploadDate); 
        
        prpTmainSchema.setInputHour("12");
        
        prpTmainSchema.setAgentCode(bankInterFace_DetailDto.getBankcode());
        
        prpTmainSchema.setCoinsFlag("0");
        prpTmainSchema.setReinsFlag("0");
        prpTmainSchema.setAllinsFlag("2");
        prpTmainSchema.setUnderWriteFlag("0");
        prpTmainSchema.setOthFlag("000000YY000000000000");
        
        if(strIsEiesFlag.equals("1"))
        {
        	  String flag =prpTmainSchema.getFlag();       
              if(prpTmainSchema.getFlag() != null && flag.length()>=4){
              		 flag = flag.substring(0, 3) + "1" + flag.substring(4, flag.length());
              }else{
              	     flag = flag + Str.space(3-flag.length()) + "1";
              }
              prpTmainSchema.setFlag(flag);
        }
        else
        {
      	      String flag =prpTmainSchema.getFlag();       
              if(prpTmainSchema.getFlag() != null && flag.length()>=4){
          		     flag = flag.substring(0, 3) + "0" + flag.substring(4, flag.length());
              }else{
          	         flag = flag + Str.space(3-flag.length()) + "0";
              }
              prpTmainSchema.setFlag(flag);
        }
        
        prpTmainSchema.setDisRate1("0");
        prpTmainSchema.setBusinessFlag("0");
        prpTmainSchema.setAgreementNo(strAgreementNo);
        prpTmainSchema.setShareHolderFlag("0");
        
        

        prpTmainSchema.setVisaCode(visacode); 
        
        prpTmainSchema.setUnderWriteCode("99999999");
        prpTmainSchema.setUnderWriteName("自动核XXXXX");
        prpTmainSchema.setManualType("0");
        
        prpTmainSchema.setUnderWriteEndDate(strCurrentDate);
        
        prpTmainSchema.setUnderWriteFlag("1");
        blPrpTmain.setArr(prpTmainSchema);

        return blPrpTmain;
    }

    /**
     * @desc 投联XXXXX由接口表数据生成XX数据：生成XX单XX/被XX人信息对象BLPrpTinsured
     * @param arrCustomerCode
     * @param bankInterFace_DetailDto
     * @return blPrpTinsured
     * @throws Exception
     */
    public BLPrpTinsured generateObjectOfTinsured(String[] arrCustomerCode, BankInterFace_DetailDto bankInterFace_DetailDto) throws Exception {
        BLPrpTinsured blPrpTinsured = new BLPrpTinsured();
        PrpTinsuredSchema prpTinsuredSchema1 = new PrpTinsuredSchema();
        PrpTinsuredSchema prpTinsuredSchema2 = new PrpTinsuredSchema();

        prpTinsuredSchema1.setProposalNo(bankInterFace_DetailDto.getProposalNo());
        prpTinsuredSchema1.setRiskCode(bankInterFace_DetailDto.getRiskCode());
        prpTinsuredSchema1.setSerialNo("1");
        prpTinsuredSchema1.setLanguage("C");
        prpTinsuredSchema1.setInsuredType("1");
        prpTinsuredSchema1.setInsuredCode(arrCustomerCode[0]);
        prpTinsuredSchema1.setInsuredName(bankInterFace_DetailDto.getAppliName());
        prpTinsuredSchema1.setInsuredAddress(bankInterFace_DetailDto.getAddress());
        prpTinsuredSchema1.setInsuredNature("3");
        prpTinsuredSchema1.setInsuredFlag("2");
        prpTinsuredSchema1.setIdentifyType(bankInterFace_DetailDto.getIdType());
        prpTinsuredSchema1.setIdentifyNumber(bankInterFace_DetailDto.getAppidNo());
        prpTinsuredSchema1.setBank(bankInterFace_DetailDto.getBfbankCode());
        prpTinsuredSchema1.setAccount(bankInterFace_DetailDto.getBfaccountNo());
        prpTinsuredSchema1.setPostAddress(bankInterFace_DetailDto.getAddress());
        prpTinsuredSchema1.setPostCode(bankInterFace_DetailDto.getPostCode());
        
        prpTinsuredSchema1.setPhoneNumber(bankInterFace_DetailDto.getPhone());
        
        prpTinsuredSchema1.setMobile(bankInterFace_DetailDto.getMobileCode());
        prpTinsuredSchema1.setEmail(bankInterFace_DetailDto.getEmail());
        blPrpTinsured.setArr(prpTinsuredSchema1);

        prpTinsuredSchema2.setProposalNo(bankInterFace_DetailDto.getProposalNo());
        prpTinsuredSchema2.setRiskCode(bankInterFace_DetailDto.getRiskCode());
        prpTinsuredSchema2.setSerialNo("2");
        prpTinsuredSchema2.setLanguage("C");
        prpTinsuredSchema2.setInsuredType("1");
        prpTinsuredSchema2.setInsuredCode(arrCustomerCode[1]);
        prpTinsuredSchema2.setInsuredName(bankInterFace_DetailDto.getInsuredName());
        prpTinsuredSchema2.setInsuredAddress(bankInterFace_DetailDto.getInsuredAdress());
        prpTinsuredSchema2.setInsuredNature("3");
        prpTinsuredSchema2.setInsuredFlag("1");
        prpTinsuredSchema2.setIdentifyType(bankInterFace_DetailDto.getIdType());
        prpTinsuredSchema2.setBank(bankInterFace_DetailDto.getBfbankCode());
        prpTinsuredSchema2.setAccount(bankInterFace_DetailDto.getBfaccountNo());
        prpTinsuredSchema2.setPostAddress(bankInterFace_DetailDto.getInsuredAdress());
        prpTinsuredSchema2.setPostCode(bankInterFace_DetailDto.getInsurepostCode());
        prpTinsuredSchema2.setPhoneNumber(bankInterFace_DetailDto.getInsuredPhone());
        
        prpTinsuredSchema2.setMobile(bankInterFace_DetailDto.getMobileCode());
        
        blPrpTinsured.setArr(prpTinsuredSchema2);

        return blPrpTinsured;
    }

    /**
     * @desc 投联XXXXX由接口表数据生成XX数据：生成XX单地址信息对象BLPrpTaddress
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
     * @desc 投联XXXXX由接口表数据生成XX数据：生成XX单投资信息对象BLPrpTmainInvest
     * @param bankInterFace_DetailDto
     * @param dblInterestRate
     * @return blPrpTmainInvest
     * @throws Exception
     */
    public BLPrpTmainInvest generateObjectOfTmainInvest(BankInterFace_DetailDto bankInterFace_DetailDto, PrpDbankInvestDto prpDbankInvestDto, double dblInterestRate) throws Exception {
        BLPrpTmainInvest blPrpTmainInvest = new BLPrpTmainInvest();
        PrpTmainInvestSchema prpTmainInvestSchema = new PrpTmainInvestSchema();
        BLPrpDbankFacade blPrpDbankFacade = null;

        ArrayList prpDbankDtoList = null;
        String condition = "";
        String code = "";
        String name = "";
        double interest = 0;
        double backAmount = 0;

        prpTmainInvestSchema.setProposalNo(bankInterFace_DetailDto.getProposalNo());
        prpTmainInvestSchema.setRiskCode(bankInterFace_DetailDto.getRiskCode());
        prpTmainInvestSchema.setInvestYear(String.valueOf(prpDbankInvestDto.getPeriod())); 
        prpTmainInvestSchema.setCurrency(prpDbankInvestDto.getCurrency()); 
        prpTmainInvestSchema.setUnitInvestment(String.valueOf(prpDbankInvestDto.getUnitMaxRate())); 
        prpTmainInvestSchema.setQuantity(String.valueOf(bankInterFace_DetailDto.getInvestCount()));
        prpTmainInvestSchema.setInvestment(String.valueOf(bankInterFace_DetailDto.getSumPremium())); 
        prpTmainInvestSchema.setInterestRate(String.valueOf(dblInterestRate)); 
        interest = bankInterFace_DetailDto.getSumPremium() * dblInterestRate / 100;
        prpTmainInvestSchema.setInterest(String.valueOf(interest)); 
        backAmount = bankInterFace_DetailDto.getInvestCount() * prpDbankInvestDto.getUnitMaxRate() + interest;
        prpTmainInvestSchema.setBackAmount(String.valueOf(backAmount)); 
        
        blPrpDbankFacade = new BLPrpDbankFacade();
        prpDbankDtoList = null;
        
        code = bankInterFace_DetailDto.getBankcode();
        
        condition = " BankCode='" + code + "'";
        prpDbankDtoList = (ArrayList)blPrpDbankFacade.findByConditions(condition);
        if(prpDbankDtoList.size() > 0) {
            name = ((PrpDbankDto)prpDbankDtoList.get(0)).getBankName();
        } else {
            name = code;
        }
        prpTmainInvestSchema.setBankCode(code);
        prpTmainInvestSchema.setBankName(name);
        
        blPrpDbankFacade = new BLPrpDbankFacade();
        prpDbankDtoList = null;
        code = bankInterFace_DetailDto.getBankbranchCode();
        condition = " BankCode='" + code + "'";
        prpDbankDtoList = (ArrayList)blPrpDbankFacade.findByConditions(condition);
        if(prpDbankDtoList.size() > 0) {
            name = ((PrpDbankDto)prpDbankDtoList.get(0)).getBankName();
        } else {
            name = code;
        }
        prpTmainInvestSchema.setSBranchBankCode(code);
        prpTmainInvestSchema.setSBranchBankName(name);
        
        prpTmainInvestSchema.setIsCatchOutFlag("0");
        
        blPrpTmainInvest.setArr(prpTmainInvestSchema);
        return blPrpTmainInvest;
    }

    /**
     * @desc 投联XXXXX由接口表数据生成XX数据：生成XX单XXXXX别信息对象BLPrpTitemKind
     * @param bankInterFace_DetailDto
     * @param prpDbankInvestDto
     * @param prpDkindDto
     * @return blPrpTitemKind
     * @throws Exception
     */
    public BLPrpTitemKind generateObjectOfTitemKind(BankInterFace_DetailDto bankInterFace_DetailDto,PrpDbankInvestDto prpDbankInvestDto,PrpDkindDto prpDkindDto) throws Exception {
        BLPrpTitemKind blPrpTitemKind = new BLPrpTitemKind();
        PrpTitemKindSchema prpTitemKindSchema = new PrpTitemKindSchema();

        String strStartDate = "";
        String strEndDate = "";

        int intYear = 0;
        int intMonth = 0;
        int intDay = 0;

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

        prpTitemKindSchema.setProposalNo(bankInterFace_DetailDto.getProposalNo());
        prpTitemKindSchema.setRiskCode(bankInterFace_DetailDto.getRiskCode());
        prpTitemKindSchema.setItemKindNo("1");
        prpTitemKindSchema.setKindCode("001");
        prpTitemKindSchema.setKindName(prpDkindDto.getKindCName());
        prpTitemKindSchema.setItemCode("0001");
        prpTitemKindSchema.setItemDetailName(prpDkindDto.getKindCName());
        prpTitemKindSchema.setStartDate(strStartDate); 
        prpTitemKindSchema.setStartHour("0");
        prpTitemKindSchema.setEndDate(strEndDate);
        prpTitemKindSchema.setEndHour("24");
        prpTitemKindSchema.setAddressNo("1");
        prpTitemKindSchema.setCalculateFlag("Y");
        prpTitemKindSchema.setCurrency("CNY");
        prpTitemKindSchema.setUnitAmount(String.valueOf(prpDbankInvestDto.getUnitAmount())); 
        prpTitemKindSchema.setAmount(String.valueOf(bankInterFace_DetailDto.getInvestCount() * prpDbankInvestDto.getUnitAmount()));
        prpTitemKindSchema.setRate(String.valueOf(prpDbankInvestDto.getUnitRate())); 
        prpTitemKindSchema.setShortRateFlag("3"); 

        prpTitemKindSchema.setShortRate(String.valueOf(prpDbankInvestDto.getPeriod()*100)); 

        prpTitemKindSchema.setPremium(String.valueOf(bankInterFace_DetailDto.getInvestCount() * prpDbankInvestDto.getUnitPremium()));
        prpTitemKindSchema.setFlag(" 1");
        blPrpTitemKind.setArr(prpTitemKindSchema);

        return blPrpTitemKind ;
    }

    /**
     * @desc 投联XXXXX由接口表数据生成XX数据：生成XX单金额信息对象BLPrpTfee
     * @param bankInterFace_DetailDto
     * @param prpDbankInvestDto
     * @return blPrpTfee
     * @throws Exception
     */
    public BLPrpTfee generateObjectOfTfee(BankInterFace_DetailDto bankInterFace_DetailDto, PrpDbankInvestDto prpDbankInvestDto) throws Exception {
        BLPrpTfee blPrpTfee = new BLPrpTfee();
        PrpTfeeSchema prpTfeeSchema = new PrpTfeeSchema();

        prpTfeeSchema.setProposalNo(bankInterFace_DetailDto.getProposalNo());
        prpTfeeSchema.setRiskCode(bankInterFace_DetailDto.getRiskCode());
        prpTfeeSchema.setCurrency(bankInterFace_DetailDto.getCurrency());
        prpTfeeSchema.setAmount(String.valueOf(bankInterFace_DetailDto.getInvestCount() * prpDbankInvestDto.getUnitAmount()));
        prpTfeeSchema.setPremium(String.valueOf(bankInterFace_DetailDto.getInvestCount() * prpDbankInvestDto.getUnitPremium()));
        prpTfeeSchema.setCurrency1(bankInterFace_DetailDto.getCurrency());
        prpTfeeSchema.setExchangeRate1("1");
        prpTfeeSchema.setAmount1(String.valueOf(bankInterFace_DetailDto.getInvestCount() * prpDbankInvestDto.getUnitAmount()));
        prpTfeeSchema.setPremium1(String.valueOf(bankInterFace_DetailDto.getInvestCount() * prpDbankInvestDto.getUnitPremium()));
        prpTfeeSchema.setCurrency2(bankInterFace_DetailDto.getCurrency());
        prpTfeeSchema.setExchangeRate2("1");
        prpTfeeSchema.setAmount2(String.valueOf(bankInterFace_DetailDto.getInvestCount() * prpDbankInvestDto.getUnitAmount()));
        prpTfeeSchema.setPremium2(String.valueOf(bankInterFace_DetailDto.getInvestCount() * prpDbankInvestDto.getUnitPremium()));
        blPrpTfee.setArr(prpTfeeSchema);

        return blPrpTfee;
    }

    /**
     * @desc 投联XXXXX由接口表数据生成XX数据：生成XX单缴费计划信息对象BLPrpTplan
     * @param bankInterFace_DetailDto
     * @return blPrpTplan
     * @throws Exception
     */
    
    public BLPrpTplan generateObjectOfTplanNew(BankInterFace_DetailDto bankInterFace_DetailDto, PrpDbankInvestDto prpDbankInvestDto) throws Exception {
        BLPrpTplan blPrpTplan = new BLPrpTplan();
        PrpTplanSchema prpTplanSchema = new PrpTplanSchema();
        prpTplanSchema.setProposalNo(bankInterFace_DetailDto.getProposalNo());
        prpTplanSchema.setSerialNo("1");
        prpTplanSchema.setPayNo("1");
        prpTplanSchema.setPayReason("R10");
        prpTplanSchema.setPlanDate(bankInterFace_DetailDto.getInvestDate().toString());
        prpTplanSchema.setCurrency(bankInterFace_DetailDto.getCurrency());
        prpTplanSchema.setPlanFee(""+(bankInterFace_DetailDto.getInvestCount() * prpDbankInvestDto.getUnitPremium()));
        prpTplanSchema.setDelinquentFee(""+(bankInterFace_DetailDto.getInvestCount() * prpDbankInvestDto.getUnitPremium()));
        prpTplanSchema.setPlanStartDate(bankInterFace_DetailDto.getInvestDate().toString());
        blPrpTplan.setArr(prpTplanSchema);

        return blPrpTplan;
    }
    
    /**
     * @desc 投联XXXXX由接口表数据生成XX数据：生成XX单缴费计划信息对象BLPrpTplan
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
     * @desc 投联XXXXX由接口表数据生成XX数据：生成XX号码
     * @param dbPool
     * @param strComCode
     * @param strSessionId
     * @param blPrpTmain
     * @return strPolicyNo
     * @throws Exception
     */
    public String getPolicyNo(DbPool dbPool, String strComCode, String strSessionId, BLPrpTmain blPrpTmain) throws Exception {
        Bill bill = new Bill();

        String strRiskCode = "";
        String strPolicyNo = "";
        int intYear = 0;

        strRiskCode = blPrpTmain.getArr(0).getRiskCode();
        intYear = new DateTime().current().getYear();

        strPolicyNo = bill.getNo(dbPool, "prpcmain", strRiskCode, strComCode, intYear, strSessionId);
        return strPolicyNo;
    }

    /**
     * @desc 投联XXXXX由接口表数据生成XX数据：生成XX对象BLPolicy
     * @param strPolicyNo
     * @param blProposal
     * @return blPolicy
     * @throws Exception
     */
    public BLPolicy generateObjectOfPolicy(String strPolicyNo, BLProposal blProposal) throws Exception {
        BLPolicy blPolicy = new BLPolicy();
        BLPrpCmain blPrpCmain = null;
        BLPrpCinsured blPrpCinsured = null;
        BLPrpCaddress blPrpCaddress = null;
        BLPrpCmainInvest blPrpCmainInvest = null;
        BLPrpCitemKind blPrpCitemKind = null;
        BLPrpCfee blPrpCfee = null;
        BLPrpCplan blPrpCplan = null;

        blPrpCmain = generateObjectOfCmain(strPolicyNo, blProposal.getBLPrpTmain());
        blPrpCinsured = generateObjectOfCinsured(strPolicyNo, blProposal.getBLPrpTinsured());
        blPrpCaddress = generateObjectOfCaddress(strPolicyNo, blProposal.getBLPrpTaddress());
        blPrpCmainInvest = generateObjectOfCmainInvest(strPolicyNo, blProposal.getBLPrpTmainInvest());
        blPrpCitemKind = generateObjectOfCitemKind(strPolicyNo, blProposal.getBLPrpTitemKind());
        blPrpCfee = generateObjectOfCfee(strPolicyNo, blProposal.getBLPrpTfee());
        blPrpCplan = generateObjectOfCplan(strPolicyNo, blProposal.getBLPrpTplan());
        
        
        blPolicy.setBLPrpCmain(blPrpCmain);
        blPolicy.setBLPrpCinsured(blPrpCinsured);
        blPolicy.setBLPrpCaddress(blPrpCaddress);
        blPolicy.setBLPrpCmainInvest(blPrpCmainInvest);
        blPolicy.setBLPrpCitemKind(blPrpCitemKind);
        blPolicy.setBLPrpCfee(blPrpCfee);
        blPolicy.setBLPrpCplan(blPrpCplan);

        return blPolicy;
    }
    
    
    /**
     * @desc 投联XXXXX由接口表数据生成XX数据：生成XX手续费信息
     * @param bankInterFace_DetailDto
     * @return blPolicy
     * @throws Exception
     */
    public BLPrpCommission generateObjectOfCommission(BankInterFace_DetailDto bankInterFace_DetailDto, BLPolicy blPolicy) throws Exception {
    	BLPrpCommission blPrpCommission = new BLPrpCommission();
    	BLPrpDagreementFacade blPrpDagreementFacade = new BLPrpDagreementFacade();
    	BLPrpDagreeDetailFacade blPrpDagreeDetailFacade = new BLPrpDagreeDetailFacade();
    	PrpCommissionSchema prpCommissionSchema = new PrpCommissionSchema();
    	PrpCmainSchema prpCmainSchema = new PrpCmainSchema();
    	ChgDate chgDate = new ChgDate();
    	PrpDagreementDto prpDagreementDto = null;
    	PrpDagreeDetailDto prpDagreeDetailDto = null;
    	Collection prpDagreementDtoList = null;
    	String strBankCode = "";
        String strAgreementNo = "";
        double dbDisrate = 0;
        double dbDisFee = 0;
    	strBankCode = bankInterFace_DetailDto.getBankcode();
        prpDagreementDtoList = blPrpDagreementFacade.findByConditions("AgentCode='" + strBankCode + "'");
        for(Iterator prpDagreementDtoListTmp = prpDagreementDtoList.iterator(); prpDagreementDtoListTmp.hasNext();) {
            prpDagreementDto = (PrpDagreementDto)prpDagreementDtoListTmp.next();
            strAgreementNo = prpDagreementDto.getAgreementNo();
            
            logger.info("strAgreementNo======协议号===="+strAgreementNo);
            
            break;
        }
        
        prpDagreeDetailDto = blPrpDagreeDetailFacade.findByPrimaryKey(strAgreementNo, bankInterFace_DetailDto.getRiskCode(),"0000");
        
        if(prpDagreeDetailDto==null)
			throw new UserException(-98, -1167, "BLInvest.generateObjectOfCommission",
			"手续费比例取值失败,请管理员进行配置！" ); 
        if((String.valueOf(prpDagreeDetailDto.getTopCommission())).trim().equals("")
           ||prpDagreeDetailDto.getTopCommission()<0){
			throw new UserException(-98, -1167, "BLInvest.generateObjectOfCommission",
					"手续费比例取值失败,请管理员进行配置！" ); 
        }else
        {
        	dbDisrate = prpDagreeDetailDto.getTopCommission(); 
        }
        if(blPolicy != null && blPolicy.getBLPrpCmain().getSize() > 0) {
            prpCmainSchema = blPolicy.getBLPrpCmain().getArr(0);
        }
        dbDisFee = bankInterFace_DetailDto.getSumPremium()*dbDisrate/100;
        prpCommissionSchema.setClassCode(bankInterFace_DetailDto.getClassCode());
        prpCommissionSchema.setRiskCode(bankInterFace_DetailDto.getRiskCode());
        prpCommissionSchema.setCertiNo(prpCmainSchema.getPolicyNo());
        prpCommissionSchema.setCertiType("P");
        prpCommissionSchema.setPolicyNo(prpCmainSchema.getPolicyNo());
        prpCommissionSchema.setDisRate(String.valueOf(dbDisrate));
        prpCommissionSchema.setCurrency(bankInterFace_DetailDto.getCurrency());
        prpCommissionSchema.setDisFee(String.valueOf(dbDisFee));
        prpCommissionSchema.setGenerateDate(chgDate.getCurrentTime("yyyy-MM-dd"));
        prpCommissionSchema.setGenerateTime(chgDate.getCurrentTime("HH:mm"));
        prpCommissionSchema.setSelfRate("");
        blPrpCommission.setArr(prpCommissionSchema);
    	return blPrpCommission;
}
    
    
    /**
     * @desc 投联XXXXX由接口表数据生成XX数据：生成XX主信息对象BLPrpCmain
     * @param strPolicyNo
     * @param blPrpTmain
     * @return blPrpCmain
     * @throws Exception
     */
    public BLPrpCmain generateObjectOfCmain(String strPolicyNo, BLPrpTmain blPrpTmain) throws Exception {
        BLPrpCmain blPrpCmain = new BLPrpCmain();

        for(int i = 0; i < blPrpTmain.getSize(); i++) {
            PrpCmainSchema prpCmainSchema = null;
            PrpTmainSchema prpTmainSchema = null;

            prpTmainSchema = blPrpTmain.getArr(i);
            prpCmainSchema = blPrpCmain.Evaluate(prpTmainSchema, strPolicyNo);
            blPrpCmain.setArr(prpCmainSchema);
        }

        return blPrpCmain;
    }

    /**
     * @desc 投联XXXXX由接口表数据生成XX数据：生成XXXX/被XX人信息对象BLPrpCinsured
     * @param strPolicyNo
     * @param blPrpTinsured
     * @return blPrpCinsured
     * @throws Exception
     */
    public BLPrpCinsured generateObjectOfCinsured(String strPolicyNo, BLPrpTinsured blPrpTinsured) throws Exception {
        BLPrpCinsured blPrpCinsured = new BLPrpCinsured();

        for(int i = 0; i < blPrpTinsured.getSize(); i++) {
            PrpCinsuredSchema prpCinsuredSchema = null;
            PrpTinsuredSchema prpTinsuredSchema = null;

            prpTinsuredSchema = blPrpTinsured.getArr(i);
            prpCinsuredSchema = blPrpCinsured.Evaluate(prpTinsuredSchema, strPolicyNo);
            blPrpCinsured.setArr(prpCinsuredSchema);
        }

        return blPrpCinsured;
    }

    /**
     * @desc 投联XXXXX由接口表数据生成XX数据：生成XX地址信息对象BLPrpCaddress
     * @param strPolicyNo
     * @param blPrpTaddress
     * @return blPrpCaddress
     * @throws Exception
     */
    public BLPrpCaddress generateObjectOfCaddress(String strPolicyNo, BLPrpTaddress blPrpTaddress) throws Exception {
        BLPrpCaddress blPrpCaddress = new BLPrpCaddress();

        for(int i = 0; i < blPrpTaddress.getSize(); i++) {
            PrpCaddressSchema prpCaddressSchema = null;
            PrpTaddressSchema prpTaddressSchema = null;

            prpTaddressSchema = blPrpTaddress.getArr(i);
            prpCaddressSchema = blPrpCaddress.Evaluate(prpTaddressSchema, strPolicyNo);
            blPrpCaddress.setArr(prpCaddressSchema);
        }

        return blPrpCaddress;
    }

    /**
     * @desc 投联XXXXX由接口表数据生成XX数据：生成XX投资信息对象BLPrpCmainInvest
     * @param strPolicyNo
     * @param blPrpTmainInvest
     * @return blPrpCmainInvest
     * @throws Exception
     */
    public BLPrpCmainInvest generateObjectOfCmainInvest(String strPolicyNo, BLPrpTmainInvest blPrpTmainInvest) throws Exception {
        BLPrpCmainInvest blPrpCmainInvest = new BLPrpCmainInvest();

        for(int i = 0; i < blPrpTmainInvest.getSize(); i++) {
            PrpCmainInvestSchema prpCmainInvestSchema = null;
            PrpTmainInvestSchema prpTmainInvestSchema = null;

            prpTmainInvestSchema = blPrpTmainInvest.getArr(i);
            prpCmainInvestSchema = blPrpCmainInvest.Evaluate(prpTmainInvestSchema, strPolicyNo);
            blPrpCmainInvest.setArr(prpCmainInvestSchema);
        }

        return blPrpCmainInvest;
    }

    /**
     * @desc 投联XXXXX由接口表数据生成XX数据：生成XXXXXXX别信息对象BLPrpCitemKind
     * @param strPolicyNo
     * @param blPrpTitemKind
     * @return blPrpCitemKind
     * @throws Exception
     */
    public BLPrpCitemKind generateObjectOfCitemKind(String strPolicyNo, BLPrpTitemKind blPrpTitemKind) throws Exception {
        BLPrpCitemKind blPrpCitemKind = new BLPrpCitemKind();

        for(int i = 0; i < blPrpTitemKind.getSize(); i++) {
            PrpCitemKindSchema prpCitemKindSchema = null;
            PrpTitemKindSchema prpTitemKindSchema = null;

            prpTitemKindSchema = blPrpTitemKind.getArr(i);
            prpCitemKindSchema = blPrpCitemKind.Evaluate(prpTitemKindSchema, strPolicyNo);
            blPrpCitemKind.setArr(prpCitemKindSchema);
        }

        return blPrpCitemKind;
    }

    /**
     * @desc 投联XXXXX由接口表数据生成XX数据：生成XX金额信息对象BLPrpCfee
     * @param strPolicyNo
     * @param blPrpTfee
     * @return blPrpCfee
     * @throws Exception
     */
    public BLPrpCfee generateObjectOfCfee(String strPolicyNo, BLPrpTfee blPrpTfee) throws Exception {
        BLPrpCfee blPrpCfee = new BLPrpCfee();

        for(int i = 0; i < blPrpTfee.getSize(); i++) {
            PrpCfeeSchema prpCfeeSchema = null;
            PrpTfeeSchema prpTfeeSchema = null;

            prpTfeeSchema = blPrpTfee.getArr(i);
            prpCfeeSchema = blPrpCfee.Evaluate(prpTfeeSchema, strPolicyNo);
            blPrpCfee.setArr(prpCfeeSchema);
        }

        return blPrpCfee;
    }

    /**
     * @desc 投联XXXXX由接口表数据生成XX数据：生成XX缴费计划信息对象BLPrpCplan
     * @param strPolicyNo
     * @param blPrpTplan
     * @return blPrpCplan
     * @throws Exception
     */
    public BLPrpCplan generateObjectOfCplan(String strPolicyNo, BLPrpTplan blPrpTplan) throws Exception {
        BLPrpCplan blPrpCplan = new BLPrpCplan();

        for(int i = 0; i < blPrpTplan.getSize(); i++) {
            PrpCplanSchema prpCplanSchema = null;
            PrpTplanSchema prpTplanSchema = null;

            prpTplanSchema = blPrpTplan.getArr(i);
            prpCplanSchema = blPrpCplan.Evaluate(prpTplanSchema, strPolicyNo);
            blPrpCplan.setArr(prpCplanSchema);
        }

        return blPrpCplan;
    }

    public Vector getSumInvestInfoC(String strCondition, String strStartDate, String strEndDate) throws Exception {
        Vector vector = null;
        DBInvest dbInvest = new DBInvest();
        String strSelect = "";
        String strFrom = "";
        String strWhere = "";
        String strGroupBy = "";
        String strOrderBy = "";
        String strSQL = "";

        strSelect = "select cm.riskcode,dr.riskcname as riskname,"
                + "cm.comcode,dc.comcname as comname,"
                + "cm.agentcode,da.agentname,"
                + "sum(cmi.investment)as investment,sum(cmi.investment*cm.disrate/100) as commissionfee,"
                + "count(cm.policyno) as count,sum(cmi.quantity) as quantity";
        strFrom = " from prpcmain cm,prpcmaininvest cmi,"
                + "prpdrisk dr,prpdcompany dc,prpdagent da";
        strWhere = " where cm.classcode='29' and cm.policyno=cmi.policyno"
                + " and cm.riskcode=dr.riskcode and cm.comcode=dc.comcode"
                + " and cm.agentcode=da.agentcode";
        strGroupBy = " group by cm.riskcode,dr.riskcname,cm.comcode,dc.comcname,cm.agentcode,da.agentname";
        strOrderBy = " order by cm.riskcode,cm.comcode,cm.agentcode";

        if(strStartDate.trim().length() > 0) {
            strWhere = strWhere + " and cm.startdate>='" + strStartDate + "'";
        }
        if(strEndDate.trim().length() > 0) {
            strWhere = strWhere + " and cm.startdate<='" + strEndDate + "'";
        }
        strCondition = strCondition.replaceAll("prpcmain.", "cm.");
        strWhere = strWhere + strCondition;

        strSQL = strSelect + strFrom + strWhere + strGroupBy + strOrderBy;
        vector = dbInvest.getSumInvestInfoC(strSQL);
        return vector;
    }

    public Vector getSumInvestInfoP(String strCondition, String strStartDate, String strEndDate) throws Exception {
        Vector vector = null;
        DBInvest dbInvest = new DBInvest();
        String strSelect = "";
        String strFrom = "";
        String strWhere = "";
        String strGroupBy = "";
        String strOrderBy = "";
        String strSQL = "";

        strSelect = "select pm.riskcode,dr.riskcname as riskname,"
                + "pm.comcode,dc.comcname as comname,"
                + "pm.agentcode,da.agentname,"
                + "sum(cmi.investment)as investment,sum(cmi.investment*pm.disrate/100) as commissionfee,"
                + "count(pm.endorseno) as count,sum(cmi.quantity) as quantity";
        strFrom = " from prppmain pm,prpcmaininvest cmi,"
                + "prpdrisk dr,prpdcompany dc,prpdagent da";
        strWhere = " where pm.classcode='29' and pm.policyno=cmi.policyno"
                + " and pm.riskcode=dr.riskcode and pm.comcode=dc.comcode"
                + " and pm.agentcode=da.agentcode";
        strGroupBy = " group by pm.riskcode,dr.riskcname,pm.comcode,dc.comcname,pm.agentcode,da.agentname";
        strOrderBy = " order by pm.riskcode,pm.comcode,pm.agentcode";

        if(strEndDate.trim().length() > 0) {
            strFrom = strFrom + ",prpphead ph";
            strWhere = strWhere
                    + " and ph.endorseno=pm.endorseno"
                    + " and ph.validdate<='" + strEndDate + "'";
        }
        strCondition = strCondition.replaceAll("prpcmain.", "pm.");
        strWhere = strWhere + strCondition;

        strSQL = strSelect + strFrom + strWhere + strGroupBy + strOrderBy;
        vector = dbInvest.getSumInvestInfoP(strSQL);
        return vector;
    }
}
