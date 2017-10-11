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
import com.sp.platform.bl.facade.BLPrpDkindFacade;
import com.sp.platform.bl.facade.BLPrpdBankCompannyFacade;
import com.sp.platform.dto.domain.BankInterFace_DetailDto;
import com.sp.platform.dto.domain.PrpDagreementDto;
import com.sp.platform.dto.domain.PrpDagreeDetailDto;
import com.sp.platform.dto.domain.PrpdBankCompannyDto;
import com.sp.platform.bl.action.domain.BLBankInterFace_DetailAction;
import com.sp.platform.bl.action.domain.BLPrpDkindAction;
import com.sp.platform.dto.domain.PrpDkindDto;
import com.sp.platform.resource.dtofactory.domain.DBBankInterFace_Detail;
import com.sp.utiall.blsvr.BLPrpDcompany;
import com.sp.utiall.blsvr.BLPrpDcustomer;
import com.sp.utiall.blsvr.BLPrpDcustomerIdv;
import com.sp.utiall.blsvr.BLPrpDitem;
import com.sp.utiall.blsvr.BLPrpDkind;
import com.sp.utiall.blsvr.BLPrpDration;
import com.sp.utiall.dbsvr.DBPrpDcompany;
import com.sp.utiall.dbsvr.DBPrpDkind;
import com.sp.utiall.dbsvr.DBPrpDration;
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
import com.sp.prpall.blsvr.cb.BLPrpCmainInvest;
import com.sp.prpall.blsvr.cb.BLPrpCmainSub;
import com.sp.prpall.blsvr.cb.BLPrpCplan;
import com.sp.prpall.blsvr.cb.BLPrpCmainCasualty;
import com.sp.payment.blsvr.jf.BLPrpJplanFee;
import com.sp.prpall.blsvr.tb.BLProposal;
import com.sp.prpall.blsvr.tb.BLPrpTaddress;
import com.sp.prpall.blsvr.tb.BLPrpTfee;
import com.sp.prpall.blsvr.tb.BLPrpTinsured;
import com.sp.prpall.blsvr.tb.BLPrpTitemKind;
import com.sp.prpall.blsvr.tb.BLPrpTmain;
import com.sp.prpall.blsvr.tb.BLPrpTmainBank;
import com.sp.prpall.blsvr.tb.BLPrpTmainSub;
import com.sp.prpall.blsvr.tb.BLPrpTplan;
import com.sp.prpall.blsvr.tb.BLPrpTinsuredNature;
import com.sp.prpall.blsvr.tb.BLPrpTmainCasualty;
import com.sp.prpall.dbsvr.cb.DBPrpCmain;
import com.sp.prpall.dbsvr.misc.DBInvest;
import com.sp.prpall.interf.Visa;
import com.sp.prpall.schema.PrpCaddressSchema;
import com.sp.prpall.schema.PrpCfeeSchema;
import com.sp.prpall.schema.PrpCinsuredSchema;
import com.sp.prpall.schema.PrpCitemKindSchema;
import com.sp.prpall.schema.PrpCmainBankSchema;
import com.sp.prpall.schema.PrpCmainInvestSchema;
import com.sp.prpall.schema.PrpCmainSchema;
import com.sp.prpall.schema.PrpCmainSubSchema;
import com.sp.prpall.schema.PrpCinsuredNatureSchema;
import com.sp.prpall.schema.PrpCplanSchema;
import com.sp.prpall.schema.PrpCmainCasualtySchema;
import com.sp.prpall.schema.PrpCommissionSchema;
import com.sp.prpall.schema.PrpJinvestSchema;
import com.sp.prpall.schema.PrpTaddressSchema;
import com.sp.prpall.schema.PrpTfeeSchema;
import com.sp.prpall.schema.PrpTinsuredSchema;
import com.sp.prpall.schema.PrpTinsuredNatureSchema;
import com.sp.prpall.schema.PrpTitemKindSchema;
import com.sp.prpall.schema.PrpTmainBankSchema;
import com.sp.prpall.schema.PrpTmainSchema;
import com.sp.prpall.schema.PrpTmainSubSchema;
import com.sp.prpall.schema.PrpTplanSchema;
import com.sp.utiall.blsvr.BLPrpDcustomerIdvNew;
import com.sp.prpall.schema.PrpTmainCasualtySchema;
import com.sp.prpall.pubfun.Bill;
import com.sp.prpall.pubfun.CheckChannelYB;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 定义组合XXXXX导入BLGenerateYjData类
 * <p>Copyright: </p>
 * <p>@createdate </p>
 * @author 
 * @version 1.0
 */
public class BLGenerateYjData {
	protected final Log logger = LogFactory.getLog(getClass());

    /*
     * 本类完成的功能：
     * 将BankInterFace_Detail表中当前所有的数据转换为XX数据，并送相关数据到收付的接口表中
     * for(BankInterFace_Detail) {
     *   1、获得BankInterFace_Detail表数据
     *   2、获得XXXXX代码
     *   3、生成XXXXX资料数据
     *   4、生成XX单数据
     *   5、获得XX号码
     *   6、生成XX数据
     *   7、生成XXXXX接口表数据
     *   8、修改BankInterFace_Detail表数据
     *   9、反写BankInterFace_Detail表中的数据
     *   10、11、将对象放入Attribute中
     * }
     */
    
	
	String[] arrCustomerCode = new String[4];
	
	
    public BLGenerateYjData() {
    }

    /**
     * @desc 投联XXXXX由接口表数据生成XX数据
     * @param request
     * @param response
     * @param strRiskCode
     * @param IsEiesFlag
     * @return strReturnMessage：size=0 没有可生成的XX单、XX/size>0 空字符串，将对象放入Attribute中
     * @throws Exception
     */
     
    public String build(HttpServletRequest request, HttpServletResponse response, String strRiskCode ,String vscode,String IsEiesFlag,String icomCode) throws Exception {
     
    	DbPool dbPool = new DbPool();
        DBManager dbManager = null;
        BLPrpCmainSub blPrpCmainSub = new BLPrpCmainSub();
        PrpCommissionSchema prpCommissionSchema = null;
        BLBankInterFace_DetailAction blBankInterFace_DetailAction = new BLBankInterFace_DetailAction();
        ArrayList bankInterFace_DetailDtoList = null;
        ArrayList policyNoList = new ArrayList();
        String strSessionId = request.getSession().getId();
        String strCondition = "";
        String strReturnMessage = "";
        
        String strTOCOMCODE = icomCode;     
        
        try {
            dbPool.open(SysConfig.CONST_DDCCDATASOURCE);
            
            dbManager = dbPool.getDBManager(SysConfig.CONST_DDCCDATASOURCE);
            
            if("Eies".equals(IsEiesFlag)){
                strCondition = " RevolutionFlag='0'" 
            		 + " AND Invalid='0'"    
                     + " AND RiskCode='" + strRiskCode + "'" 
                     + " AND IsEiesFlag='1'"
                     + " AND ComCode ='" + strTOCOMCODE + "'"
                     /*应XXXXX要求此处HandlerCode生成XX人员不需要，任何有权限的人员都可以生成XX*/
                     /*+ " AND HandlerCode='" + userCode + "'" 
                     /* 此处的[ORDER BY UploadFileSeq]非常重要，因为jsp的显示结果依赖这个顺序 */
                     + " ORDER BY UploadFileSeq,Trans_Seq"; 
            }
            
            else if ("WYEies".equals(IsEiesFlag)){
            	strCondition = " RevolutionFlag='0'" 
           		 + " AND Invalid='0'"    
                    + " AND RiskCode='" + strRiskCode + "'" 
                    + " AND IsEiesFlag='2'"
                    + " AND ComCode ='" + strTOCOMCODE + "'"
                    /*应XXXXX要求此处HandlerCode生成XX人员不需要，任何有权限的人员都可以生成XX*/
                    /*+ " AND HandlerCode='" + userCode + "'" 
                    /* 此处的[ORDER BY UploadFileSeq]非常重要，因为jsp的显示结果依赖这个顺序 */
                    + " ORDER BY UploadFileSeq,Trans_Seq"; 
            }
            
            else if("ABC".equals(IsEiesFlag)){
                strCondition = " RevolutionFlag='0'" 
                     + " AND Invalid='0'"    
                       + " AND RiskCode='" + strRiskCode + "'" 
                       + " AND bankCode='103'"
                       + " AND ComCode ='" + strTOCOMCODE + "'"
                       /*应XXXXX要求此处HandlerCode生成XX人员不需要，任何有权限的人员都可以生成XX*/
                       /*+ " AND HandlerCode='" + userCode + "'" 
                       /* 此处的[ORDER BY UploadFileSeq]非常重要，因为jsp的显示结果依赖这个顺序 */
                       + " ORDER BY UploadFileSeq,Trans_Seq"; 
            }
            
            else{
            	strCondition = " RevolutionFlag='0'" 
           		    + " AND Invalid='0'"    
                    + " AND RiskCode='" + strRiskCode + "'" 
                    + " AND IsEiesFlag='0'"
                    + " AND ComCode like'" + strTOCOMCODE.substring(0,2) + "%'" 
                    /*应XXXXX要求此处HandlerCode生成XX人员不需要，任何有权限的人员都可以生成XX*/
                    /*+ " AND HandlerCode='" + userCode + "'" 
                    /* 此处的[ORDER BY UploadFileSeq]非常重要，因为jsp的显示结果依赖这个顺序 */
                    + " ORDER BY UploadFileSeq,Trans_Seq"; 
            }
            
            logger.info("strCondition:" + strCondition);
            

            
            DBBankInterFace_Detail dbBankInterFace_Detail = new DBBankInterFace_Detail(dbManager);
            bankInterFace_DetailDtoList = (ArrayList)dbBankInterFace_Detail.findByConditions(strCondition,1,100);
            for(int i = 0; i < bankInterFace_DetailDtoList.size(); i++) {
                dbPool.beginTransaction();
                BankInterFace_DetailDto bankInterFace_DetailDto = null;
              
                
                
              
                BLProposal blProposal = null;
                
            	
            	
                String[] arrProposalNo = new String[3];
                String strRelation = ""; 
                String strPolicyNo = "";
                String strProposalNo = "";
                String strProposalNofor0301 = "";
                String strProposalNofor2700 = "";
                String strUseFor = "";
                String iUserDesc = "";
                
                
                bankInterFace_DetailDto = (BankInterFace_DetailDto)bankInterFace_DetailDtoList.get(i);
                
                if("Eies".equals(IsEiesFlag)){
                	vscode = bankInterFace_DetailDto.getVisacode();
                }
                
                String iRiskCode = bankInterFace_DetailDto.getRiskCode();
                
                strRelation = bankInterFace_DetailDto.getPhone();
                
                if("ABC".equals(IsEiesFlag)){
                    strUseFor = bankInterFace_DetailDto.getPolicyType();
                    iUserDesc = bankInterFace_DetailDto.getPolicyType();
                }else{
                    strUseFor = bankInterFace_DetailDto.getUseFor();
                    iUserDesc = bankInterFace_DetailDto.getUserDesc();
                }
                
                
                
                
              
                /*if("ABC".equals(IsEiesFlag)){
                    arrCustomerCode = getCustomerCodesNew(dbPool, bankInterFace_DetailDto.getComCode(), strRelation, iUserDesc,iUserDesc,iRiskCode);
                    blPrpDcustomer = generateObjectOfDcustomerNew(arrCustomerCode, bankInterFace_DetailDto,iUserDesc,iUserDesc,iRiskCode);
                    blPrpDcustomer.addCustomerList(dbPool);
                    blPrpDcustomerIdv = generateObjectOfDcustomerIdvNew(arrCustomerCode, bankInterFace_DetailDto, iUserDesc ,iUserDesc,iRiskCode);
                    blPrpDcustomerIdv.addCustomerIdvList(dbPool);
                }else{
                    arrCustomerCode = getCustomerCodes(dbPool, bankInterFace_DetailDto.getComCode(), strRelation, strUseFor,iUserDesc,iRiskCode);
                    blPrpDcustomer = generateObjectOfDcustomer(arrCustomerCode, bankInterFace_DetailDto,strUseFor,iUserDesc,iRiskCode);
                    blPrpDcustomer.addCustomerList(dbPool);
                    blPrpDcustomerIdv = generateObjectOfDcustomerIdv(arrCustomerCode, bankInterFace_DetailDto, strUseFor ,iUserDesc,iRiskCode);
                    blPrpDcustomerIdv.addCustomerIdvList(dbPool);
                }*/
              
                
                
                
                strProposalNo = bankInterFace_DetailDto.getProposalNo();
                strProposalNofor2700 = getProposalNo(dbPool, bankInterFace_DetailDto.getComCode(), strSessionId, "2700");
                strProposalNofor0301 = getProposalNo(dbPool, bankInterFace_DetailDto.getComCode(), strSessionId, "0301");
                arrProposalNo[0] = strProposalNo;
                arrProposalNo[1] = strProposalNofor0301;
                arrProposalNo[2] = strProposalNofor2700;
                
                blProposal = generateObjectOfProposal(dbPool, arrCustomerCode, bankInterFace_DetailDto,vscode,arrProposalNo,strRiskCode,IsEiesFlag);               
                
                
                blProposal.save(dbPool, "I", false);
                dbPool.commitTransaction();
                
                
                strPolicyNo = bankInterFace_DetailDto.getPolicyno();
                
                
                try{
                com.sp.undwrt.bl.facade.BLTaskFacade blTaskFacade = new com.sp.undwrt.bl.facade.BLTaskFacade();
                blTaskFacade.start("11","T",strProposalNo,strRiskCode,
                        "30",bankInterFace_DetailDto.getComCode(),bankInterFace_DetailDto.getComCode(),
                        bankInterFace_DetailDto.getHandlerCode(),bankInterFace_DetailDto.getHandlerCode(),bankInterFace_DetailDto.getHandler1Code(),"");
                }catch(Exception uwtExcetion){
                    uwtExcetion.printStackTrace();



                }
                
                
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
            
            
            
            if(!"WYEies".equals(IsEiesFlag)){
                for(int i = 0; i < bankInterFace_DetailDtoList.size(); i++) 
                {     
                	BankInterFace_DetailDto bankInterFaceDetailDto = null;
                	bankInterFaceDetailDto = (BankInterFace_DetailDto)bankInterFace_DetailDtoList.get(i);
                	String usercode = "";
                	String printno = "";
                	usercode = bankInterFaceDetailDto.getHandlerCode();
                	printno = bankInterFaceDetailDto.getPrintno();
                	Visa visaintef = new Visa();
                	if(!bankInterFaceDetailDto.getPolicyno().equals(""))
                	{
                		
                		logger.info("usercode:" + usercode);
                		logger.info("vscode:" + vscode);
                		logger.info("visaintef.checkUsedReady(usercode,vscode,printno):" + visaintef.checkUsedReady(usercode,vscode,printno));
                		

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
     * @desc 关爱1+家由接口表数据生成XX数据：获得XX人、被XX人XXXXX代码
     * @param dbPool
     * @param strComCode
     * @param strRelation
     * @param strUseFor
     * @return arrCustomerCode：arrCustomerCode[0] XX人代码/arrCustomerCode[1] 被XX人本人代码/arrCustomerCode[2] 被XX人配偶代码/arrCustomerCode[3] 被XX人子女代码
     * @throws Exception
     */
    
    /*public String[] getCustomerCodes(DbPool dbPool, String strComCode, String strRelation, String strUseFor, String strUserDesc,String strRiskCode) throws Exception {
        BLPrpDcustomer blPrpDcustomer = new BLPrpDcustomer();
        String[] CustomerCode = new String[4];
        String strCustomerCode1 = "";
        String strCustomerCode2 = "";
        String strCustomerCode3 = "";
        String strCustomerCode4 = "";
        String strCode1 = "";
        String strCode2 = "";
        String strCode3 = "";
     
     if("3006".equals(strRiskCode)){
        strCode1 = blPrpDcustomer.getPrpdCustomerId();
        if("01".equals(strUseFor)){
        	strCustomerCode1 = strCode1;
        	strCustomerCode2 = strCode1;
        }else if("02".equals(strUseFor)){
        	
            strCode2 = blPrpDcustomer.getPrpdCustomerId();
        	if("本人".equals(strRelation)){
        		strCustomerCode1 = strCode1;
            	strCustomerCode2 = strCode1;
            	strCustomerCode3 = strCode2;            	
        	}else if("配偶".equals(strRelation)){
        		strCustomerCode1 = strCode2;
            	strCustomerCode2 = strCode1;
            	strCustomerCode3 = strCode2; 
        	}        	
        }else if("03".equals(strUseFor)){
        	
            strCode2 = blPrpDcustomer.getPrpdCustomerId();
            
            strCode3 = blPrpDcustomer.getPrpdCustomerId();
        	if("本人".equals(strRelation)){
        		strCustomerCode1 = strCode1;
            	strCustomerCode2 = strCode1;
            	strCustomerCode3 = strCode2;
            	strCustomerCode4 = strCode3;
        	}else if("配偶".equals(strRelation)){
        		strCustomerCode1 = strCode2;
            	strCustomerCode2 = strCode1;
            	strCustomerCode3 = strCode2;
            	strCustomerCode4 = strCode3;
        	}else if("子女".equals(strRelation)){
        		strCustomerCode1 = strCode3;
            	strCustomerCode2 = strCode1;
            	strCustomerCode3 = strCode2;
            	strCustomerCode4 = strCode3;
        	}
        }        	
     }else if ("3007".equals(strRiskCode)){
    	 strCode1 = blPrpDcustomer.getPrpdCustomerId();
         if("25".equals(strUserDesc)){
         	strCustomerCode1 = strCode1;
         	strCustomerCode2 = strCode1;
         }else if("26".equals(strUserDesc)){
         	
             strCode2 = blPrpDcustomer.getPrpdCustomerId();
         	if("本人".equals(strRelation)){
         		strCustomerCode1 = strCode1;
             	strCustomerCode2 = strCode1;
             	strCustomerCode3 = strCode2;            	
         	}else if("配偶".equals(strRelation)){
         		strCustomerCode1 = strCode2;
             	strCustomerCode2 = strCode1;
             	strCustomerCode3 = strCode2; 
         	}        	
         }else if("27".equals(strUserDesc)){
         	
             strCode2 =blPrpDcustomer.getPrpdCustomerId();
             
             strCode3 = blPrpDcustomer.getPrpdCustomerId();
         	if("本人".equals(strRelation)){
         		strCustomerCode1 = strCode1;
             	strCustomerCode2 = strCode1;
             	strCustomerCode3 = strCode2;
             	strCustomerCode4 = strCode3;
         	}else if("配偶".equals(strRelation)){
         		strCustomerCode1 = strCode2;
             	strCustomerCode2 = strCode1;
             	strCustomerCode3 = strCode2;
             	strCustomerCode4 = strCode3;
         	}else if("子女".equals(strRelation)){
         		strCustomerCode1 = strCode3;
             	strCustomerCode2 = strCode1;
             	strCustomerCode3 = strCode2;
             	strCustomerCode4 = strCode3;
         	} 
         }
     }

        CustomerCode[0] = strCustomerCode1;
        CustomerCode[1] = strCustomerCode2;
        CustomerCode[2] = strCustomerCode3;
        CustomerCode[3] = strCustomerCode4;
        
        return CustomerCode;
    }*/

    /**
     * @desc 农行关爱1+家由接口表数据生成XX数据：获得XX人、被XX人XXXXX代码
     * @param dbPool
     * @param strComCode
     * @param strRelation
     * @param strUseFor
     * @return arrCustomerCode：arrCustomerCode[0] XX人代码/arrCustomerCode[1] 被XX人本人代码/arrCustomerCode[2] 被XX人配偶代码/arrCustomerCode[3] 被XX人子女代码
     * @throws Exception
     */
    /*public String[] getCustomerCodesNew(DbPool dbPool, String strComCode, String strRelation, String strUseFor, String strUserDesc,String strRiskCode) throws Exception {
    	
    	
    	BLPrpDcustomerIdvNew blPrpDcustomer= new BLPrpDcustomerIdvNew();
    	
        String[] CustomerCode = new String[4];
        String strCustomerCode1 = "";
        String strCustomerCode2 = "";
        String strCustomerCode3 = "";
        String strCustomerCode4 = "";
        String strCode1 = "";
        String strCode2 = "";
        String strCode3 = "";
        strCode1 = blPrpDcustomer.getPrpdCustomerId();
        if("25".equals(strUseFor)){
            strCustomerCode1 = strCode1;
            strCustomerCode2 = strCode1;
        }else if("26".equals(strUseFor)){
            
            strCode2 = blPrpDcustomer.getPrpdCustomerId();
            strCustomerCode1 = strCode1;
            strCustomerCode2 = strCode1;
            strCustomerCode3 = strCode2;                         
        }else if("27".equals(strUseFor)){;
            strCode2 = blPrpDcustomer.getPrpdCustomerId();
            strCode3 = blPrpDcustomer.getPrpdCustomerId();
            strCustomerCode1 = strCode1;
            strCustomerCode2 = strCode1;
            strCustomerCode3 = strCode2;
            strCustomerCode4 = strCode3;
            
        }           
        CustomerCode[0] = strCustomerCode1;
        CustomerCode[1] = strCustomerCode2;
        CustomerCode[2] = strCustomerCode3;
        CustomerCode[3] = strCustomerCode4;
        
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
    /*public BLPrpDcustomer generateObjectOfDcustomer(String[] arrCustomerCode, BankInterFace_DetailDto bankInterFace_DetailDto, String strUseFor ,String strUserDesc, String strRiskCode) throws Exception {
        BLPrpDcustomer blPrpDcustomer = new BLPrpDcustomer();
        PrpDcustomerSchema prpDcustomerSchema1 = new PrpDcustomerSchema(); 
        PrpDcustomerSchema prpDcustomerSchema2 = new PrpDcustomerSchema(); 
        PrpDcustomerSchema prpDcustomerSchema3 = new PrpDcustomerSchema(); 

       if("3006".equals(strRiskCode)){
        
        prpDcustomerSchema1.setCustomerType("1");
        prpDcustomerSchema1.setCustomerCode(arrCustomerCode[1]);
        prpDcustomerSchema1.setCustomerCName(bankInterFace_DetailDto.getAppliName());
        prpDcustomerSchema1.setValidStatus("1");
        blPrpDcustomer.setArr(prpDcustomerSchema1);
        if("02".equals(strUseFor)){
        		prpDcustomerSchema2.setCustomerType("1");
                prpDcustomerSchema2.setCustomerCode(arrCustomerCode[2]);
                prpDcustomerSchema2.setCustomerCName(bankInterFace_DetailDto.getEmail());             
                prpDcustomerSchema2.setValidStatus("1");
                blPrpDcustomer.setArr(prpDcustomerSchema2);
        	}else if("03".equals(strUseFor)){
        		prpDcustomerSchema2.setCustomerType("1");
                prpDcustomerSchema2.setCustomerCode(arrCustomerCode[2]);
                prpDcustomerSchema2.setCustomerCName(bankInterFace_DetailDto.getEmail());             
                prpDcustomerSchema2.setValidStatus("1");
                
                prpDcustomerSchema3.setCustomerType("1");
                prpDcustomerSchema3.setCustomerCode(arrCustomerCode[3]);
                prpDcustomerSchema3.setCustomerCName(bankInterFace_DetailDto.getInsuredPhone());                
                prpDcustomerSchema3.setValidStatus("1");
                blPrpDcustomer.setArr(prpDcustomerSchema2);
                blPrpDcustomer.setArr(prpDcustomerSchema3);
        	}
       }else if ("3007".equals(strRiskCode)){
    	   prpDcustomerSchema1.setCustomerType("1");
           prpDcustomerSchema1.setCustomerCode(arrCustomerCode[1]);
           prpDcustomerSchema1.setCustomerCName(bankInterFace_DetailDto.getAppliName());
           prpDcustomerSchema1.setValidStatus("1");
           blPrpDcustomer.setArr(prpDcustomerSchema1);
           if("26".equals(strUserDesc)){
           		prpDcustomerSchema2.setCustomerType("1");
                   prpDcustomerSchema2.setCustomerCode(arrCustomerCode[2]);
                   prpDcustomerSchema2.setCustomerCName(bankInterFace_DetailDto.getEmail());             
                   prpDcustomerSchema2.setValidStatus("1");
                   blPrpDcustomer.setArr(prpDcustomerSchema2);
           	}else if("27".equals(strUserDesc)){
           		prpDcustomerSchema2.setCustomerType("1");
                   prpDcustomerSchema2.setCustomerCode(arrCustomerCode[2]);
                   prpDcustomerSchema2.setCustomerCName(bankInterFace_DetailDto.getEmail());             
                   prpDcustomerSchema2.setValidStatus("1");
                   
                   prpDcustomerSchema3.setCustomerType("1");
                   prpDcustomerSchema3.setCustomerCode(arrCustomerCode[3]);
                   prpDcustomerSchema3.setCustomerCName(bankInterFace_DetailDto.getInsuredPhone());                
                   prpDcustomerSchema3.setValidStatus("1");
                   blPrpDcustomer.setArr(prpDcustomerSchema2);
                   blPrpDcustomer.setArr(prpDcustomerSchema3);
    	   
           }
       }

        return blPrpDcustomer;
    }*/

    /**
     * @desc 农行关爱1+家由接口表数据生成XX数据：生成XXXXX资料信息PrpDcustomer
     * @param arrCustomerCode
     * @param bankInterFace_DetailDto
     * @param strUseFor
     * @return blPrpDcustomer
     * @throws Exception
     */
    /*public BLPrpDcustomer generateObjectOfDcustomerNew(String[] arrCustomerCode, BankInterFace_DetailDto bankInterFace_DetailDto, String strUseFor ,String strUserDesc, String strRiskCode) throws Exception {
        BLPrpDcustomer blPrpDcustomer = new BLPrpDcustomer();
        PrpDcustomerSchema prpDcustomerSchema1 = new PrpDcustomerSchema(); 
        PrpDcustomerSchema prpDcustomerSchema2 = new PrpDcustomerSchema(); 
        PrpDcustomerSchema prpDcustomerSchema3 = new PrpDcustomerSchema(); 
        prpDcustomerSchema1.setCustomerType("1");
        prpDcustomerSchema1.setCustomerCode(arrCustomerCode[1]);
        prpDcustomerSchema1.setCustomerCName(bankInterFace_DetailDto.getAppliName());
        prpDcustomerSchema1.setValidStatus("1");
        blPrpDcustomer.setArr(prpDcustomerSchema1);
        if("26".equals(strUserDesc)){
            prpDcustomerSchema2.setCustomerType("1");
            prpDcustomerSchema2.setCustomerCode(arrCustomerCode[2]);
            prpDcustomerSchema2.setCustomerCName(bankInterFace_DetailDto.getEmail());             
            prpDcustomerSchema2.setValidStatus("1");
            blPrpDcustomer.setArr(prpDcustomerSchema2);
        }else if("27".equals(strUserDesc)){
            prpDcustomerSchema2.setCustomerType("1");
            prpDcustomerSchema2.setCustomerCode(arrCustomerCode[2]);
            prpDcustomerSchema2.setCustomerCName(bankInterFace_DetailDto.getEmail());             
            prpDcustomerSchema2.setValidStatus("1");
            
            prpDcustomerSchema3.setCustomerType("1");
            prpDcustomerSchema3.setCustomerCode(arrCustomerCode[3]);
            prpDcustomerSchema3.setCustomerCName(bankInterFace_DetailDto.getInsuredPhone());                
            prpDcustomerSchema3.setValidStatus("1");
            blPrpDcustomer.setArr(prpDcustomerSchema2);
            blPrpDcustomer.setArr(prpDcustomerSchema3);
            
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
    /*public BLPrpDcustomerIdv generateObjectOfDcustomerIdv(String[] arrCustomerCode, BankInterFace_DetailDto bankInterFace_DetailDto, String strUseFor, String strUserDesc, String strRiskCode) throws Exception {
        BLPrpDcustomerIdv blPrpDcustomerIdv = new BLPrpDcustomerIdv();
        PrpDcustomerIdvSchema prpDcustomerIdvSchema1 = new PrpDcustomerIdvSchema();
        PrpDcustomerIdvSchema prpDcustomerIdvSchema2 = new PrpDcustomerIdvSchema();
        PrpDcustomerIdvSchema prpDcustomerIdvSchema3 = new PrpDcustomerIdvSchema();

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

        if("3006".equals(strRiskCode)){
        prpDcustomerIdvSchema1.setCustomerCode(arrCustomerCode[1]);
        prpDcustomerIdvSchema1.setCustomerCName(bankInterFace_DetailDto.getAppliName());
        prpDcustomerIdvSchema1.setIdentifyType(bankInterFace_DetailDto.getIdType());
        prpDcustomerIdvSchema1.setIdentifyNumber(bankInterFace_DetailDto.getAppidNo());
        prpDcustomerIdvSchema1.setPhoneNumber(bankInterFace_DetailDto.getMobileCode());
        prpDcustomerIdvSchema1.setMobile(bankInterFace_DetailDto.getMobileCode());
        prpDcustomerIdvSchema1.setPostCode(bankInterFace_DetailDto.getPostCode());
        prpDcustomerIdvSchema1.setNewCustomerCode(arrCustomerCode[1]);
        prpDcustomerIdvSchema1.setValidStatus("1");
        prpDcustomerIdvSchema1.setLowerViewFlag("0");
        prpDcustomerIdvSchema1.setOperatorCode(bankInterFace_DetailDto.getHandlerCode());
        prpDcustomerIdvSchema1.setInputDate(strUploadDate);
        prpDcustomerIdvSchema1.setUpdaterCode(bankInterFace_DetailDto.getHandlerCode());
        prpDcustomerIdvSchema1.setUpdateDate(strCurrentDate);
        prpDcustomerIdvSchema1.setComcode(bankInterFace_DetailDto.getComCode());
        blPrpDcustomerIdv.setArr(prpDcustomerIdvSchema1);

        if("02".equals(strUseFor)) {
            prpDcustomerIdvSchema2.setCustomerCode(arrCustomerCode[2]);
            prpDcustomerIdvSchema2.setCustomerCName(bankInterFace_DetailDto.getEmail());
            prpDcustomerIdvSchema2.setPostCode(bankInterFace_DetailDto.getInsurepostCode());
            prpDcustomerIdvSchema2.setNewCustomerCode(arrCustomerCode[2]);
            prpDcustomerIdvSchema2.setValidStatus("1");
            prpDcustomerIdvSchema2.setLowerViewFlag("0");
            prpDcustomerIdvSchema2.setOperatorCode(bankInterFace_DetailDto.getHandlerCode());
            prpDcustomerIdvSchema2.setInputDate(strUploadDate);
            prpDcustomerIdvSchema2.setUpdaterCode(bankInterFace_DetailDto.getHandlerCode());
            prpDcustomerIdvSchema2.setUpdateDate(strCurrentDate);
            prpDcustomerIdvSchema2.setComcode(bankInterFace_DetailDto.getComCode());
            blPrpDcustomerIdv.setArr(prpDcustomerIdvSchema2);
        }else if("03".equals(strUseFor)){
        	prpDcustomerIdvSchema2.setCustomerCode(arrCustomerCode[2]);
            prpDcustomerIdvSchema2.setCustomerCName(bankInterFace_DetailDto.getEmail());
            prpDcustomerIdvSchema2.setPostCode(bankInterFace_DetailDto.getInsurepostCode());
            prpDcustomerIdvSchema2.setNewCustomerCode(arrCustomerCode[2]);
            prpDcustomerIdvSchema2.setValidStatus("1");
            prpDcustomerIdvSchema2.setLowerViewFlag("0");
            prpDcustomerIdvSchema2.setOperatorCode(bankInterFace_DetailDto.getHandlerCode());
            prpDcustomerIdvSchema2.setInputDate(strUploadDate);
            prpDcustomerIdvSchema2.setUpdaterCode(bankInterFace_DetailDto.getHandlerCode());
            prpDcustomerIdvSchema2.setUpdateDate(strCurrentDate);
            prpDcustomerIdvSchema2.setComcode(bankInterFace_DetailDto.getComCode());
           
            prpDcustomerIdvSchema3.setCustomerCode(arrCustomerCode[3]);
            prpDcustomerIdvSchema3.setCustomerCName(bankInterFace_DetailDto.getInsuredPhone());
            prpDcustomerIdvSchema3.setPostCode(bankInterFace_DetailDto.getInsurepostCode());
            prpDcustomerIdvSchema3.setNewCustomerCode(arrCustomerCode[3]);
            prpDcustomerIdvSchema3.setValidStatus("1");
            prpDcustomerIdvSchema3.setLowerViewFlag("0");
            prpDcustomerIdvSchema3.setOperatorCode(bankInterFace_DetailDto.getHandlerCode());
            prpDcustomerIdvSchema3.setInputDate(strUploadDate);
            prpDcustomerIdvSchema3.setUpdaterCode(bankInterFace_DetailDto.getHandlerCode());
            prpDcustomerIdvSchema3.setUpdateDate(strCurrentDate);
            prpDcustomerIdvSchema3.setComcode(bankInterFace_DetailDto.getComCode());
            blPrpDcustomerIdv.setArr(prpDcustomerIdvSchema2);
            blPrpDcustomerIdv.setArr(prpDcustomerIdvSchema3);
        }
        }else if ("3007".equals(strRiskCode)) {
        	prpDcustomerIdvSchema1.setCustomerCode(arrCustomerCode[1]);
            prpDcustomerIdvSchema1.setCustomerCName(bankInterFace_DetailDto.getAppliName());
            prpDcustomerIdvSchema1.setIdentifyType(bankInterFace_DetailDto.getIdType());
            prpDcustomerIdvSchema1.setIdentifyNumber(bankInterFace_DetailDto.getAppidNo());
            prpDcustomerIdvSchema1.setPhoneNumber(bankInterFace_DetailDto.getMobileCode());
            prpDcustomerIdvSchema1.setMobile(bankInterFace_DetailDto.getMobileCode());
            prpDcustomerIdvSchema1.setPostCode(bankInterFace_DetailDto.getPostCode());
            prpDcustomerIdvSchema1.setNewCustomerCode(arrCustomerCode[1]);
            prpDcustomerIdvSchema1.setValidStatus("1");
            prpDcustomerIdvSchema1.setLowerViewFlag("0");
            prpDcustomerIdvSchema1.setOperatorCode(bankInterFace_DetailDto.getHandlerCode());
            prpDcustomerIdvSchema1.setInputDate(strUploadDate);
            prpDcustomerIdvSchema1.setUpdaterCode(bankInterFace_DetailDto.getHandlerCode());
            prpDcustomerIdvSchema1.setUpdateDate(strCurrentDate);
            prpDcustomerIdvSchema1.setComcode(bankInterFace_DetailDto.getComCode());
            blPrpDcustomerIdv.setArr(prpDcustomerIdvSchema1);

            if("26".equals(strUserDesc)) {
                prpDcustomerIdvSchema2.setCustomerCode(arrCustomerCode[2]);
                prpDcustomerIdvSchema2.setCustomerCName(bankInterFace_DetailDto.getEmail());
                prpDcustomerIdvSchema2.setPostCode(bankInterFace_DetailDto.getInsurepostCode());
                prpDcustomerIdvSchema2.setNewCustomerCode(arrCustomerCode[2]);
                prpDcustomerIdvSchema2.setValidStatus("1");
                prpDcustomerIdvSchema2.setLowerViewFlag("0");
                prpDcustomerIdvSchema2.setOperatorCode(bankInterFace_DetailDto.getHandlerCode());
                prpDcustomerIdvSchema2.setInputDate(strUploadDate);
                prpDcustomerIdvSchema2.setUpdaterCode(bankInterFace_DetailDto.getHandlerCode());
                prpDcustomerIdvSchema2.setUpdateDate(strCurrentDate);
                prpDcustomerIdvSchema2.setComcode(bankInterFace_DetailDto.getComCode());
                blPrpDcustomerIdv.setArr(prpDcustomerIdvSchema2);
            }else if("27".equals(strUserDesc)){
            	prpDcustomerIdvSchema2.setCustomerCode(arrCustomerCode[2]);
                prpDcustomerIdvSchema2.setCustomerCName(bankInterFace_DetailDto.getEmail());
                prpDcustomerIdvSchema2.setPostCode(bankInterFace_DetailDto.getInsurepostCode());
                prpDcustomerIdvSchema2.setNewCustomerCode(arrCustomerCode[2]);
                prpDcustomerIdvSchema2.setValidStatus("1");
                prpDcustomerIdvSchema2.setLowerViewFlag("0");
                prpDcustomerIdvSchema2.setOperatorCode(bankInterFace_DetailDto.getHandlerCode());
                prpDcustomerIdvSchema2.setInputDate(strUploadDate);
                prpDcustomerIdvSchema2.setUpdaterCode(bankInterFace_DetailDto.getHandlerCode());
                prpDcustomerIdvSchema2.setUpdateDate(strCurrentDate);
                prpDcustomerIdvSchema2.setComcode(bankInterFace_DetailDto.getComCode());
               
                prpDcustomerIdvSchema3.setCustomerCode(arrCustomerCode[3]);
                prpDcustomerIdvSchema3.setCustomerCName(bankInterFace_DetailDto.getInsuredPhone());
                prpDcustomerIdvSchema3.setPostCode(bankInterFace_DetailDto.getInsurepostCode());
                prpDcustomerIdvSchema3.setNewCustomerCode(arrCustomerCode[3]);
                prpDcustomerIdvSchema3.setValidStatus("1");
                prpDcustomerIdvSchema3.setLowerViewFlag("0");
                prpDcustomerIdvSchema3.setOperatorCode(bankInterFace_DetailDto.getHandlerCode());
                prpDcustomerIdvSchema3.setInputDate(strUploadDate);
                prpDcustomerIdvSchema3.setUpdaterCode(bankInterFace_DetailDto.getHandlerCode());
                prpDcustomerIdvSchema3.setUpdateDate(strCurrentDate);
                prpDcustomerIdvSchema3.setComcode(bankInterFace_DetailDto.getComCode());
                blPrpDcustomerIdv.setArr(prpDcustomerIdvSchema2);
                blPrpDcustomerIdv.setArr(prpDcustomerIdvSchema3);
           }
        }
 
        return blPrpDcustomerIdv;
    }*/

    /**
     * @desc 农行关爱1+家由接口表数据生成XX数据：生成XXXXX资料信息PrpDcustomerIdv
     * @param arrCustomerCode
     * @param bankInterFace_DetailDto
     * @param strUseFor
     * @return blPrpDcustomerIdv
     * @throws Exception
     */
    /*public BLPrpDcustomerIdv generateObjectOfDcustomerIdvNew(String[] arrCustomerCode, BankInterFace_DetailDto bankInterFace_DetailDto, String strUseFor, String strUserDesc, String strRiskCode) throws Exception {
        BLPrpDcustomerIdv blPrpDcustomerIdv = new BLPrpDcustomerIdv();
        PrpDcustomerIdvSchema prpDcustomerIdvSchema1 = new PrpDcustomerIdvSchema();
        PrpDcustomerIdvSchema prpDcustomerIdvSchema2 = new PrpDcustomerIdvSchema();
        PrpDcustomerIdvSchema prpDcustomerIdvSchema3 = new PrpDcustomerIdvSchema();

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
       
        prpDcustomerIdvSchema1.setCustomerCode(arrCustomerCode[1]);
        prpDcustomerIdvSchema1.setCustomerCName(bankInterFace_DetailDto.getAppliName());
        prpDcustomerIdvSchema1.setIdentifyType(bankInterFace_DetailDto.getIdType());
        prpDcustomerIdvSchema1.setIdentifyNumber(bankInterFace_DetailDto.getAppidNo());
        prpDcustomerIdvSchema1.setPhoneNumber(bankInterFace_DetailDto.getMobileCode());
        prpDcustomerIdvSchema1.setMobile(bankInterFace_DetailDto.getMobileCode());
        prpDcustomerIdvSchema1.setPostCode(bankInterFace_DetailDto.getPostCode());
        prpDcustomerIdvSchema1.setNewCustomerCode(arrCustomerCode[1]);
        prpDcustomerIdvSchema1.setValidStatus("1");
        prpDcustomerIdvSchema1.setLowerViewFlag("0");
        prpDcustomerIdvSchema1.setOperatorCode(bankInterFace_DetailDto.getHandlerCode());
        prpDcustomerIdvSchema1.setInputDate(strUploadDate);
        prpDcustomerIdvSchema1.setUpdaterCode(bankInterFace_DetailDto.getHandlerCode());
        prpDcustomerIdvSchema1.setUpdateDate(strCurrentDate);
        prpDcustomerIdvSchema1.setComcode(bankInterFace_DetailDto.getComCode());
        blPrpDcustomerIdv.setArr(prpDcustomerIdvSchema1);
        if("26".equals(strUserDesc)) {
            prpDcustomerIdvSchema2.setCustomerCode(arrCustomerCode[2]);
            prpDcustomerIdvSchema2.setPostCode(bankInterFace_DetailDto.getInsurepostCode());
            prpDcustomerIdvSchema2.setCustomerCName(bankInterFace_DetailDto.getInsuredWifeName());
            prpDcustomerIdvSchema2.setIdentifyType(bankInterFace_DetailDto.getInsuredWifeIdType());
            prpDcustomerIdvSchema2.setIdentifyNumber(bankInterFace_DetailDto.getInsuredWifeIdNumber());
            prpDcustomerIdvSchema2.setNewCustomerCode(arrCustomerCode[2]);
            prpDcustomerIdvSchema2.setValidStatus("1");
            prpDcustomerIdvSchema2.setLowerViewFlag("0");
            prpDcustomerIdvSchema2.setOperatorCode(bankInterFace_DetailDto.getHandlerCode());
            prpDcustomerIdvSchema2.setInputDate(strUploadDate);
            prpDcustomerIdvSchema2.setUpdaterCode(bankInterFace_DetailDto.getHandlerCode());
            prpDcustomerIdvSchema2.setUpdateDate(strCurrentDate);
            prpDcustomerIdvSchema2.setComcode(bankInterFace_DetailDto.getComCode());
            blPrpDcustomerIdv.setArr(prpDcustomerIdvSchema2);
        }else if("27".equals(strUserDesc)){
            prpDcustomerIdvSchema2.setCustomerCode(arrCustomerCode[2]);
            prpDcustomerIdvSchema2.setCustomerCName(bankInterFace_DetailDto.getInsuredWifeName());
            prpDcustomerIdvSchema2.setIdentifyType(bankInterFace_DetailDto.getInsuredWifeIdType());
            prpDcustomerIdvSchema2.setIdentifyNumber(bankInterFace_DetailDto.getInsuredWifeIdNumber());
            prpDcustomerIdvSchema2.setPostCode(bankInterFace_DetailDto.getInsurepostCode());
            prpDcustomerIdvSchema2.setNewCustomerCode(arrCustomerCode[2]);
            prpDcustomerIdvSchema2.setValidStatus("1");
            prpDcustomerIdvSchema2.setLowerViewFlag("0");
            prpDcustomerIdvSchema2.setOperatorCode(bankInterFace_DetailDto.getHandlerCode());
            prpDcustomerIdvSchema2.setInputDate(strUploadDate);
            prpDcustomerIdvSchema2.setUpdaterCode(bankInterFace_DetailDto.getHandlerCode());
            prpDcustomerIdvSchema2.setUpdateDate(strCurrentDate);
            prpDcustomerIdvSchema2.setComcode(bankInterFace_DetailDto.getComCode());
            
            prpDcustomerIdvSchema3.setCustomerCode(arrCustomerCode[3]);
            prpDcustomerIdvSchema3.setCustomerCName(bankInterFace_DetailDto.getInsuredChildName());
            prpDcustomerIdvSchema3.setIdentifyType(bankInterFace_DetailDto.getInsuredChildIdType());
            prpDcustomerIdvSchema3.setIdentifyNumber(bankInterFace_DetailDto.getInsuredChildIdNumber());
            prpDcustomerIdvSchema3.setPostCode(bankInterFace_DetailDto.getInsurepostCode());
            prpDcustomerIdvSchema3.setNewCustomerCode(arrCustomerCode[3]);
            prpDcustomerIdvSchema3.setValidStatus("1");
            prpDcustomerIdvSchema3.setLowerViewFlag("0");
            prpDcustomerIdvSchema3.setOperatorCode(bankInterFace_DetailDto.getHandlerCode());
            prpDcustomerIdvSchema3.setInputDate(strUploadDate);
            prpDcustomerIdvSchema3.setUpdaterCode(bankInterFace_DetailDto.getHandlerCode());
            prpDcustomerIdvSchema3.setUpdateDate(strCurrentDate);
            prpDcustomerIdvSchema3.setComcode(bankInterFace_DetailDto.getComCode());
            blPrpDcustomerIdv.setArr(prpDcustomerIdvSchema2);
            blPrpDcustomerIdv.setArr(prpDcustomerIdvSchema3);
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
    
    public BLProposal generateObjectOfProposal(DbPool dbPool,String[] arrCustomerCode, BankInterFace_DetailDto bankInterFace_DetailDto, String vscode, String[] arrProposalNo, String RiskCode ,String IsEiesFlag) throws Exception {
    
    	DBManager dbManager = dbPool.getDBManager(SysConfig.CONST_DDCCDATASOURCE);

        BLProposal blProposal = new BLProposal();
    	BLPrpDration blPrpDration = new BLPrpDration();
    	BLPrpDration blPrpDration0301 = new BLPrpDration();
    	BLPrpDration blPrpDration2700 = new BLPrpDration();
    	PrpDrationSchema prpDrationSchema = new PrpDrationSchema();
    	PrpDrationSchema prpDrationSchema0301 = new PrpDrationSchema();
    	PrpDrationSchema prpDrationSchema2700 = new PrpDrationSchema();
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
        ArrayList prpDkindDtoList = null;
        Vector vblPrpDration = null;
        Vector vblPrpDration0301 = null;
        Vector vblPrpDration2700 = null;

        DateTime currentDate  = new DateTime().current();
        String strCurrentDate = "";
        
        String strRiskCode    = "";
        String strUseFor      = "";  
        String strUserDesc    = "";
        String strRelation    = "";
        String grade="";
        strCurrentDate = new Integer(currentDate.getYear()).toString() + "-"
                       + new Integer(currentDate.getMonth()).toString() + "-"
                       + new Integer(currentDate.getDay()).toString();

        
        strRiskCode = bankInterFace_DetailDto.getRiskCode();
        
        if("ABC".equals(IsEiesFlag)){
            strUseFor   = bankInterFace_DetailDto.getPolicyType();
            strUserDesc = bankInterFace_DetailDto.getPolicyType();
            grade=bankInterFace_DetailDto.getGrade();
        }else{
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
        
        
        blPrpTmain = generateObjectOfTmain(arrCustomerCode, bankInterFace_DetailDto,vscode,IsEiesFlag,arrProposalNo,blPrpDration0301,blPrpDration2700,strRiskCode,vblPrpDration0301,vblPrpDration2700); 
        
        
        
        
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
    
    	BLPrpDagreementFacade blPrpDagreementFacade = new BLPrpDagreementFacade();
        PrpDagreementDto prpDagreementDto = null;
        BLPrpTmain blPrpTmain = new BLPrpTmain();
    	
    	BLPrpdBankCompannyFacade blPrpdBankCompannyFacade = null;
        ArrayList prpdBankCompannyDtoList = null;
        
        PrpTmainSchema prpTmainSchema = new PrpTmainSchema();
        PrpTmainSchema prpTmainSchemafor0301 = new PrpTmainSchema();
        PrpTmainSchema prpTmainSchemafor2700 = new PrpTmainSchema();
        PrpDrationSchema prpDrationSchema0301 = new PrpDrationSchema();
        PrpDrationSchema prpDrationSchema2700 = new PrpDrationSchema();
        PrpDagreeDetailDto prpDagreeDetailDto = null;
        Collection prpDagreementDtoList = null;
        BLPrpDagreeDetailFacade blPrpDagreeDetailFacade = new BLPrpDagreeDetailFacade();
        DateTime currentDate = new DateTime().current();
        
        String strAgreementNo = "";
        String strStartDatePrpT = "";
        String strEndDatePrpT = "";
        
        String strUploadDate  = "";
        String strCurrentDate = "";
        String strUnderWriteEndDate = "";
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
      
      if("ABC".equals(IsEiesFlag)){
          strPolicyType=bankInterFace_DetailDto.getPolicyType();
      }
      
        
        String strAgentCode = "";
        String strBankAgentCode = "";
        blPrpdBankCompannyFacade = new BLPrpdBankCompannyFacade();
        strBankAgentCode = bankInterFace_DetailDto.getBankbranchCode(); 
        
        



        	
            
            prpdBankCompannyDtoList = (ArrayList)blPrpdBankCompannyFacade.findByConditions(" BankCode='" + strBankAgentCode + "'");

        
        
        if(prpdBankCompannyDtoList.size() == 0)
			throw new UserException(-98, -1167, "BLGenerateYjData.generateObjectOfCommission",
			"prpdbankcompanny表"+strBankAgentCode+"取值失败,请管理员进行配置！" ); 
        if(prpdBankCompannyDtoList.size() > 0) {
        	strAgentCode = ((PrpdBankCompannyDto)prpdBankCompannyDtoList.get(0)).getAgentCode();
        	
        	logger.info("strAgentCode======Tmain表代理人代码===="+strAgentCode);
        	

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
        	
        	
        	logger.info("dbDisrate:" + dbDisrate);
        	

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
        
        prpTmainSchema.setDisRate(String.valueOf(dbDisrate));
        prpTmainSchemafor0301.setDisRate(String.valueOf(dbDisrate));
        prpTmainSchemafor2700.setDisRate(String.valueOf(dbDisrate));
        
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
        prpTmainSchema.setOperateSite(bankInterFace_DetailDto.getMakeCom());
        prpTmainSchemafor0301.setOperateSite(bankInterFace_DetailDto.getMakeCom());
        prpTmainSchemafor2700.setOperateSite(bankInterFace_DetailDto.getMakeCom());        
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
        
		


		if("1".equals(bankInterFace_DetailDto.getIseiesflag())&&dbPolicyFee!=0.0){
			double tempValue =(dbPolicyFee*100)/bankInterFace_DetailDto.getSumPremium();
			newDisrate = tempValue+dbDisrate;
			
			newDisrate = Str.round(newDisrate,4);
			prpTmainSchema.setDisRate(String.valueOf(newDisrate));
			prpTmainSchemafor0301.setDisRate(String.valueOf(newDisrate));
			prpTmainSchemafor2700.setDisRate(String.valueOf(newDisrate));
		}else if("2".equals(bankInterFace_DetailDto.getIseiesflag())){
			
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
        
        if("ABC".equals(IsEiesFlag)){
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
        BLPrpTaddress blPrpTaddressfor0301 = new BLPrpTaddress();
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
        double interest = 0;
        double backAmount = 0;

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
    public BLPrpTitemKind generateObjectOf3006TitemKind(BankInterFace_DetailDto bankInterFace_DetailDto,BLPrpDration blPrpDration, String[] arrProposalNo,BLPrpDration blPrpDration0301,BLPrpDration blPrpDration2700) throws Exception {
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
        String strItemName   = "";
        String strFlag       = "";
        String strCalculateflag  = "";
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
                if("0003".equals(prpDrationSchema2700.getItemCode())){
        			strItemCode = "0051";
        		}else if("0004".equals(prpDrationSchema2700.getItemCode())){
        			strItemCode = "0148";
        		}
        		strItemDetailName = blPrpDitem.translateCode(strRiskCode,strItemCode,isChinese); 
                dblAmount2700         = Double.parseDouble(prpDrationSchema2700.getAmount())*intQuantity;
                dblPremium2700        = Double.parseDouble(prpDrationSchema2700.getPremium())*intQuantity;
                dblAmountunit     = Double.parseDouble(prpDrationSchema2700.getAmount());
                dblRate           = 1000.0*Double.parseDouble(prpDrationSchema2700.getRate());
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
                dblAmount0301         = Double.parseDouble(prpDrationSchema0301.getAmount())*intQuantity;
                dblPremium0301        = Double.parseDouble(prpDrationSchema0301.getPremium())*intQuantity;
                dblAmountunit     = Double.parseDouble(prpDrationSchema0301.getAmount());
                dblRate           = 1000.0*Double.parseDouble(prpDrationSchema0301.getRate());
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
    public BLPrpTitemKind generateObjectOf3007TitemKind(BankInterFace_DetailDto bankInterFace_DetailDto,Vector blPrpDration , String[] arrProposalNo,Vector blPrpDration0301,Vector blPrpDration2700) throws Exception {
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
        String strItemName   = "";
        String strFlag       = "";
        String strCalculateflag  = "";
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
            	strKindCode          = prpDrationSchema0301.getKindCode();
            	dbPrpDkind.getInfo(strRiskCode,strKindCode);
                
                strKindName       = dbPrpDkind.getKindCName();   
                if("0002".equals(prpDrationSchema0301.getItemCode())){
        			strItemCode = "0004";
        		}else{
        			strItemCode = prpDrationSchema0301.getItemCode();
        		}
                strItemDetailName = blPrpDitem.translateCode(strRiskCode,strItemCode,isChinese); 
                dblAmount0301         = Double.parseDouble(prpDrationSchema0301.getAmount())*intQuantity;
                dblPremium0301        = Double.parseDouble(prpDrationSchema0301.getPremium())*intQuantity;
                dblAmountunit     = Double.parseDouble(prpDrationSchema0301.getAmount());
                dblRate           = 1000.0*Double.parseDouble(prpDrationSchema0301.getRate());
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
    * @desc 关爱1+家由接口表数据生成XX数据：生成XX单XXXXX别信息对象BLPrpTinsuredNature
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
                    blPrpTinsuredNature.setArr(prpTinsuredNatureSchema4);
                    blPrpTinsuredNature.setArr(prpTinsuredNatureSchema4for0301);
                    blPrpTinsuredNature.setArr(prpTinsuredNatureSchema4for2700);
        		}
        		
        	}
        		
        } 
    	return blPrpTinsuredNature;
    }
    /**
     * @desc 关爱1+家由接口表数据生成XX数据：生成XX单金额信息对象BLPrpTfee
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
                 	 if("001".equals(prpDrationSchemafor0301.getKindCode()))
                   	   dblAmount0301   =  dblAmount0301  + Double.parseDouble(Str.chgStrZero(prpDrationSchemafor0301.getAmount()));
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
     * @desc 关爱1+家由接口表数据生成XX数据：生成XX单缴费计划信息对象BLPrpTplan
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
     * @desc 关爱1+家由接口表数据生成XX数据：生成BLPrpTmainCasualty对象
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
        
    	if("ABC".equals(IsEiesFlag)||"25".equals(strUseFor)){
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
     * @desc 关爱1+家由接口表数据生成XX数据：生成XX号码
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

 

    
    
    public Vector getDration(String RiskCode ,String UseFor,String UserDesc,String Relation,String IsEiesFlag,String grade) throws Exception{
    	Vector DrationList = new Vector();
    	String strRiskCode=RiskCode;
    	String strUseFor = UseFor;
    	String strUserDesc=UserDesc;
    	String strRelation=Relation;
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
    	    if("ABC".equals(IsEiesFlag)){
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
          
          if("ABC".equals(IsEiesFlag)){
              if("A".equals(grade))
                  strUseFor="01";
              else if("B".equals(grade))
                  strUseFor="02";
              else if("C".equals(grade))
                  strUseFor="03";
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
   
}
