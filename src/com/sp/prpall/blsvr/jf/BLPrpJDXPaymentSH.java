package com.sp.prpall.blsvr.jf;
 
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sp.indiv.ci.interf.EbaoProxy;
import com.sp.indiv.ci.interf.PolicyAccQueryDecoder;
import com.sp.indiv.ci.interf.PolicyAccQueryEncoder;
import com.sp.indiv.ci.interf.PolicyPayRecDecoder;
import com.sp.indiv.ci.interf.PolicyPayRecEncoder;
import com.sp.payment.dbsvr.jf.DBCmsInterface001;
import com.sp.payment.utility.ErrorLoger;
import com.sp.prpall.blsvr.tb.BLPrpTinsured;
import com.sp.prpall.blsvr.tb.BLPrpTmain;
import com.sp.prpall.dbsvr.jf.DBPrpJplanFeePre;
import com.sp.prpall.dbsvr.jf.DBPrpJpoaInfo;
import com.sp.prpall.dbsvr.jf.DBPrpJtransPolicy;
import com.sp.prpall.schema.PrpJpoaInfoSchema;
import com.sp.sysframework.common.datatype.DateTime;
import com.sp.sysframework.common.util.StringUtils;
import com.sp.sysframework.reference.AppConfig;
import com.sp.sysframework.reference.DBManager;
import com.sp.utiall.blsvr.BLPrpDcompany;
import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.utility.string.ChgDate;

public class BLPrpJDXPaymentSH {
    protected final Log logger = LogFactory.getLog(getClass());

	/**
	 * ���������ո���ѯ������
	 * @param list List<Map> key:CardType ֧�����͡�RcptNo �����š�PayId ��ˮ�š�SumPremium ��ProList XX���б�
	 * @throws �쳣��
	 */
	public OMElement queryPoaCodeByDX(OMElement omElement) throws Exception {
        logPrintln("��ʼ�Ϻ������ո���ѯ�����뷽��...");
        Map resultMap = null;
        OMElement resultOME = null;
        
    	DbPool dbpool = new DbPool();
    	try {
			
			
    		
    		
    		String payment_switch = SysConfig.getProperty("PAYMENT_SWITCH_prpall");
    		if("0".equals(payment_switch)){
    			dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
    		}else{
    			dbpool.open(SysConfig.CONST_PAYMENTNEWDATASOURCE);
    		}
    		
			dbpool.beginTransaction();
			
			resultMap = queryPoaCodeByDX(dbpool,omElement);
			
			dbpool.commitTransaction();
			
			
	        resultOME = createOMElementFromMap(resultMap, "DXPaymentSHService", "queryPoaCodeByDX");
	    } catch (Exception exception) {
	        dbpool.rollbackTransaction();
	        exception.printStackTrace();
	        ErrorLoger.callErrorLogProcedure("queryPoaCodeByDX", "�����ո���ѯ�������쳣:"+ omElement + "," + exception.getMessage());
	    } finally {
	        logPrintln("�Ϻ������ո���ѯ�����뷽������.");
	        dbpool.close();
	    }		

        return resultOME;		
	}

	/**
	 * ���������ո���ѯ������
	 * @param dbpool���ݿ����ӳ�
	 * @param list List<Map> key:CardType ֧�����͡�RcptNo �����š�PayId ��ˮ�š�SumPremium ��ProList XX���б�
	 * @throws �쳣��
	 */
	public Map queryPoaCodeByDX(DbPool dbpool,OMElement omElement) throws Exception {
        String strCardType = "";
        String strRcptNo = "";
        String strPayId = "";
        String strSumPremium = "";
        String strProList = "";
        /* ADD BY PENGJINLING 2013-8-5 �Ϻ�XXXXX��������������֧�� BEGIN */        
        String strCERTINOANDAPPNAME = "";
        String strTypeName = "";
        /* ADD BY PENGJINLING 2013-8-5 �Ϻ�XXXXX��������������֧�� END */
        String messageCode = "";
        String messageDesc = "";
        Map enterMap = null;
        Map checkMap = null;
        Map resultMap = null;
        
        

        enterMap = createMapFromElement(omElement);
        
        if (!enterMap.isEmpty()) {
            strCardType = enterMap.get("CardType").toString();
            strPayId = enterMap.get("PayId").toString();
            
            if(strCardType == null || "".equals(strCardType)){
				messageCode = "999999";
				messageDesc = enterMap + " �Ϻ������ո���ѯƽ̨�����뷽�����CardTypeΪ��";
				ErrorLoger.callErrorLogProcedure("queryPoaCodeByDX", messageCode + "," + messageDesc);
				return this.setStatusMap(messageCode, messageDesc);	            	
            }else{
            	
                if(!"H".equals(strCardType) && !"M".equals(strCardType) && !"N".equals(strCardType)&& !"A".equals(strCardType)){
    				messageCode = "999999";
    				messageDesc = enterMap + " �Ϻ������ո���ѯƽ̨�����뷽�����CardTypeΪ:" + strCardType + "������ȷ";
    				ErrorLoger.callErrorLogProcedure("queryPoaCodeByDX", messageCode + "," + messageDesc);
    				return this.setStatusMap(messageCode, messageDesc);	            	
                }
                
            }
            
            /* MODIFY BY PENGJINLING 2013-8-5 �Ϻ�XXXXX��������������֧�� BEGIN */
            if("H".equals(strCardType)){
	            strRcptNo = enterMap.get("RcptNo").toString();
	            strSumPremium =  enterMap.get("SumPremium").toString();
	            strProList = enterMap.get("ProList").toString();
	            
	            if(strRcptNo == null || "".equals(strRcptNo)){
					messageCode = "999999";
					messageDesc = enterMap + " �Ϻ����������ո���ѯƽ̨�����뷽�����V_RCPTNOΪ��";
					ErrorLoger.callErrorLogProcedure("queryPoaCodeByDX", messageCode + "," + messageDesc);
					return this.setStatusMap(messageCode, messageDesc);	            	
	            }
	            
	            if(strSumPremium == null || "".equals(strSumPremium)){
					messageCode = "999999";
					messageDesc = enterMap + " �Ϻ����������ո���ѯƽ̨�����뷽�����SumPremiumΪ��";
					ErrorLoger.callErrorLogProcedure("queryPoaCodeByDX", messageCode + "," + messageDesc);
					return this.setStatusMap(messageCode, messageDesc);	            	
	            }
	            
	            if(strProList == null || "".equals(strProList)){
					messageCode = "999999";
					messageDesc = enterMap + " �Ϻ����������ո���ѯƽ̨�����뷽�����ProListΪ��";
					ErrorLoger.callErrorLogProcedure("queryPoaCodeByDX", messageCode + "," + messageDesc);
					return this.setStatusMap(messageCode, messageDesc);	            	
	            }	            
            }else{
	            strCERTINOANDAPPNAME =  enterMap.get("CERTINOANDAPPNAME").toString();

	            
	            if("M".equals(strCardType)){
	            	strTypeName = "����";
	            }else if("N".equals(strCardType)){
	            	strTypeName = "����";
	            }else if("A".equals(strCardType)){
	            	strTypeName = "�Ա�����";
	            }
	            
	            
	            if(strCERTINOANDAPPNAME == null || "".equals(strCERTINOANDAPPNAME)){
					messageCode = "999999";
					messageDesc = enterMap + " �Ϻ�" + strTypeName + "�����ո���ѯƽ̨�����뷽�����CERTINOANDAPPNAMEΪ��";
					ErrorLoger.callErrorLogProcedure("queryPoaCodeByDX", messageCode + "," + messageDesc);
					return this.setStatusMap(messageCode, messageDesc);	            	
	            }	            
            }
            /* MODIFY BY PENGJINLING 2013-8-5 �Ϻ�XXXXX��������������֧�� end */
        }
        else{
			messageCode = "999999";
			messageDesc = omElement + " �Ϻ������ո���ѯƽ̨�����뷽�����Ϊ��";
			ErrorLoger.callErrorLogProcedure("queryPoaCodeByDX", messageCode + "," + messageDesc);
			return this.setPoaCodeMap("","","","","",messageCode, messageDesc);
        }
        
		
		try {
            
			if("H".equals(strCardType)){
				checkMap = this.callProcedure(dbpool,strCardType,strRcptNo,strPayId,strSumPremium,strProList);
			}else{
				checkMap = this.callProcedure(dbpool,strCardType,strPayId,strCERTINOANDAPPNAME);
			}
            
            dbpool.commitTransaction(); 
		} catch (Exception exception) {
			messageCode = "999999";
			messageDesc = "�Ϻ�����֧��У��ʧ��,ԭ��:"+ enterMap + "," + exception.getMessage();			
			dbpool.rollbackTransaction();
			exception.printStackTrace();
			ErrorLoger.callErrorLogProcedure("queryPoaCodeByDX", messageCode + "," + messageDesc);
			return this.setPoaCodeMap("","","","","",messageCode, messageDesc);
		}
        
		
		try {
	        messageCode = checkMap.get("MessageCode").toString();
	        messageDesc = checkMap.get("MessageDesc").toString();
	        
	        if ("000000".equals(messageCode)) {
	        	/* MODIFY BY PENGJINLING 2013-8-5 �Ϻ�XXXXX��������������֧�� BEGIN */	
	        	if("H".equals(strCardType)){
		        	logPrintln("�Ϻ����������ո���ѯ�����뿪ʼ��֧�����ͣ�" + strCardType + "\n�����ţ�" + strRcptNo + 
		            		"\n������ˮ�ţ�" + strPayId + "\n��" + strSumPremium + "\nXX���б�" + strProList + "\n");
		            resultMap = queryPoaCodeByDX(dbpool,strCardType,strRcptNo,strSumPremium);
	        	}else{
		        	logPrintln("�Ϻ�" + strTypeName + "�����ո���ѯ�����뿪ʼ��֧�����ͣ�" + strCardType + 
		            		"\n������ˮ�ţ�" + strPayId + "\nҵ��ż�XX�����ƣ�" + strCERTINOANDAPPNAME + "\n");
		        	String strCertiNo = strCERTINOANDAPPNAME.split(",")[0];
		        	strSumPremium = checkMap.get("SumPremium").toString();
		            resultMap = queryPoaCodeByDX(dbpool,strCardType,strCertiNo,strSumPremium);
	        	}
	        	/* MODIFY BY PENGJINLING 2013-8-5 �Ϻ�XXXXX��������������֧�� END */	
	        }
	        else{
				return this.setPoaCodeMap("","","","","",messageCode, messageDesc);         	
	        }			
        } catch (Exception exception) {
			messageCode = "999999";
			messageDesc = "�Ϻ������ո���ѯ�������쳣:"+ enterMap + "," + exception.getMessage();
            dbpool.rollbackTransaction();
            exception.printStackTrace();
            ErrorLoger.callErrorLogProcedure("queryPoaCodeByDX", messageCode + "," + messageDesc);
			return this.setPoaCodeMap("","","","","",messageCode, messageDesc);
        } finally {
            logPrintln("�Ϻ������ո���ѯ�����뷽������.");
        }
        
        return resultMap;
	}	
	
	/**
	 * ��ѯƽ̨������
	 * @param dbpool���ݿ����ӳ�
	 * @param RcptNo �����š�SumPremium ���
	 * @return Map ������Ϣ
	 * @throws �쳣��
	 */
	public Map queryPoaCodeByDX(DbPool dbpool, String strPoaType, String strRcptNo,String strSumPremium) throws UserException, Exception {
		String messageCode = "";
		String messageDesc = "";
		String strSqlStatement = "";
		String payMethod = "";
		String accBankAccounts = "";
		
		BLPrpJpoaInfo blPrpJpoaInfo = new BLPrpJpoaInfo();
		BLPrpJplanFeePre blPrpJplanFeePre = new BLPrpJplanFeePre();
		
		
		/* MODIFY BY PENGJINLING 2013-8-8 �Ϻ�XXXXX��������������֧�� BEGIN */	
		Map checkMap = this.checkPoaCodeIsExist(dbpool, strPoaType, strRcptNo);
    	if("000000".equals(checkMap.get("MessageCode"))){
    		return checkMap;
    	}
    	/* MODIFY BY PENGJINLING 2013-8-8 �Ϻ�XXXXX��������������֧�� END */	
    	
		else{
			
			/* MODIFY BY PENGJINLING 2013-8-5 �Ϻ�XXXXX��������������֧�� BEGIN */	
			if("H".equals(strPoaType)){
				strSqlStatement = " ORDERID = '" + strRcptNo + "' ";
			}else {
				strSqlStatement = " (CERTINO = '" + strRcptNo + "' OR (RELIEVINGCERTINO =  '" + strRcptNo + "'  AND RelievingAreaCode = '8' and nvl(attribute3,0) != '4')) AND SERIALNO = 0 ";
			}
        	/* MODIFY BY PENGJINLING 2013-8-5 �Ϻ�XXXXX��������������֧�� END */	
        	
        	blPrpJplanFeePre.initArr();
        	blPrpJplanFeePre.query(dbpool, strSqlStatement);
			
        	
        	blPrpJpoaInfo.setBLPrpJplanFeePre(blPrpJplanFeePre);
			
        	
        	if("H".equals(strPoaType)){
        		payMethod = "07";
    			accBankAccounts = "007";
        	}else if("M".equals(strPoaType) || "N".equals(strPoaType)){
        		payMethod = "07";
    			accBankAccounts = "008";
        	}else if("A".equals(strPoaType)){
        		payMethod = "12";
    			accBankAccounts = "009";
        	}
        	
		  	
		  	
			PrpJpoaInfoSchema prpJpoaInfoSehema = this.setPoaInfoScema(blPrpJpoaInfo, payMethod, strSumPremium, accBankAccounts,strPoaType);
			blPrpJpoaInfo.setArr(prpJpoaInfoSehema);
			
			
			Map requestMap =  this.sendPolicyPayRecRequestDX(dbpool,blPrpJpoaInfo,accBankAccounts,"");
            
            dbpool.commitTransaction(); 
			
			
			if("000000".equals(requestMap.get("MessageCode"))){
				
				/* MODIFY BY PENGJINLING 2013-8-8 �Ϻ�XXXXX��������������֧�� BEGIN */	
				checkMap = this.checkPoaCodeIsExist(dbpool, strPoaType, strRcptNo);
		    	if("000000".equals(checkMap.get("MessageCode"))){
		    		return checkMap;
		    	}
		    	/* MODIFY BY PENGJINLING 2013-8-8 �Ϻ�XXXXX��������������֧�� BEGIN */	
	        	else{
	        		if("H".equals(strPoaType)){	
	    				messageCode = "100000";
	    			}else{
	    				messageCode = "010000";
	    			}
	    			messageDesc = "��ѯ������ʧ��," + strRcptNo;
	    			ErrorLoger.callErrorLogProcedure("queryPoaCodeByDX", messageCode + "," + messageDesc);
	    			return this.setPoaCodeMap("","","","","",messageCode, messageDesc);
	        	}
			}
			
			else{
    			return requestMap;
			}
		}
	}
	
	/* ADD BY PENGJINLING 2013-8-5 �Ϻ�XXXXX��������������֧�� BEGIN */
	/**
	 * ���ƽ̨�������Ƿ��ѻ�ȡ��
	 * @param dbpool���ݿ����ӳ�
	 * @param RcptNo �����š�SumPremium ���
	 * @return Map ������Ϣ
	 * @throws �쳣��
	 */
	public Map checkPoaCodeIsExist(DbPool dbpool, String strPoaType,String strRcptNo) throws UserException, Exception {
		String poaCode = "";
		String sumPremium = "";
		String paymentStartDate = "";
		String paymentEndDate = "";
		String certiNos = "";
		String messageCode = "999999";
		String messageDesc = "";
		String strSqlStatement = "";
		
		String acntUnit = "";    
		String phoneNumber = ""; 
		String appliName =  "";
    	String licenseNo = "";
    	String csTaxPlanFee =  "";
    	String prePayRefFee =  "";
    	String validDate    =  "";
		String remark = "";      
		String jqPremiumPlanFee = "";
		String syPremiumPlanFee = "";
		String riskCode = "";
    	
		
		BLPrpJpoaInfo blPrpJpoaInfo = new BLPrpJpoaInfo();
		BLPrpJplanFeePre blPrpJplanFeePre = new BLPrpJplanFeePre();
		BLPrpDcompany blPrpDcompany = new BLPrpDcompany();
		BLPrpTinsured blPrpTinsured = new BLPrpTinsured();
		
		if("H".equals(strPoaType)){
			strSqlStatement = " ORDERID = '" + strRcptNo + "' AND CertiStatus = '1' AND nvl(PoaCode,'0') != '0' ";
		}else{
			strSqlStatement = " CertiNo = '" + strRcptNo + "' AND CertiStatus = '1' AND nvl(PoaCode,'0') != '0' ";
		}	
		
    	blPrpJplanFeePre.initArr();
    	blPrpJplanFeePre.query(dbpool, strSqlStatement);
    	if(blPrpJplanFeePre.getSize() >0){
    		poaCode = blPrpJplanFeePre.getArr(0).getPoaCode();
    		
    		if(!"H".equals(strPoaType)){
    			String certiNoOne = blPrpJplanFeePre.getArr(0).getCertiNo();
            	String centerCode =  blPrpJplanFeePre.getArr(0).getCenterCode();
            	
            	String iAcntUnit = " comcode = '" + centerCode +"' ";
            	blPrpDcompany.query(dbpool, iAcntUnit, 0);
            	acntUnit = blPrpDcompany.getArr(0).getAcntUnit();
            	
            	String iPhoneNumber = " proposalNo = '" + certiNoOne +"' ";
            	blPrpTinsured.query(dbpool, iPhoneNumber);
            	phoneNumber = blPrpDcompany.getArr(0).getPhoneNumber();
            	
            	appliName =  blPrpJplanFeePre.getArr(0).getAppliName();
            	licenseNo = blPrpJplanFeePre.getArr(0).getLicenseNo();
            	prePayRefFee =  blPrpJplanFeePre.getArr(0).getPrePayRefFee();
            	validDate    =  new DateTime(blPrpJplanFeePre.getArr(0).getValidDate(), DateTime.YEAR_TO_DAY).toString();
    		}
        	
			PrpJpoaInfoSchema prpJpoaInfoSchema = blPrpJpoaInfo.getData(dbpool, poaCode);
			if(prpJpoaInfoSchema != null){
				sumPremium = prpJpoaInfoSchema.getTotalFee();
				paymentStartDate = prpJpoaInfoSchema.getPaymentStartDate();
				paymentEndDate = prpJpoaInfoSchema.getPaymentEndDate();
			}
			messageCode = "000000";
			messageDesc = "��ѯ������ɹ�," + poaCode;
			ErrorLoger.callErrorLogProcedure("queryPoaCodeByDX", messageCode + "," + messageDesc);

			String poaSql = " PoaCode = '" + poaCode + "'";
			blPrpJplanFeePre.query(dbpool, poaSql);
			for(int i = 0;i < blPrpJplanFeePre.getSize(); i++){
				certiNos += blPrpJplanFeePre.getArr(i).getCertiNo() + ",";
				
				riskCode = blPrpJplanFeePre.getArr(i).getRiskCode();
            	if("0507".equals(riskCode)){
            		jqPremiumPlanFee = blPrpJplanFeePre.getArr(i).getPremiumPlanFee();
            		csTaxPlanFee =  blPrpJplanFeePre.getArr(i).getCSTaxPlanFee();
            	}else{
            		syPremiumPlanFee = blPrpJplanFeePre.getArr(i).getPremiumPlanFee();
            	}
            	
            	
			}
			if(certiNos.length() > 0){
				certiNos = certiNos.substring(0, certiNos.length()-1);
			}
			
			
			if(!"".equals(appliName)){
				remark += appliName+";";
			}else{
				remark += ";";
			}
			if(!"".equals(licenseNo)){
				remark += licenseNo+";";
			}else{
				remark += ";";
			}
			if(!"".equals(syPremiumPlanFee)&&!"0.0".equals(syPremiumPlanFee)){
				remark += syPremiumPlanFee+";";
			}else{
				remark += ";";
			}
			if(!"".equals(jqPremiumPlanFee)&&!"0.0".equals(jqPremiumPlanFee)){
				remark += jqPremiumPlanFee+";";
			}else{
				remark += ";";
			}
			if(!"".equals(csTaxPlanFee)&&!"0.0".equals(csTaxPlanFee)){
				remark += csTaxPlanFee+";";
			}else{
				remark += ";";
			}
			if(!"".equals(prePayRefFee)&&!"0.0".equals(prePayRefFee)){
				remark += prePayRefFee+";";
			}else{
				remark += ";";
			}
			if(!"".equals(validDate)){
				remark += validDate+";";
			}else{
				remark += ";";
			}
			if(remark.length() > 0){
				remark = remark.substring(0, remark.length()-1);
			}
			
			
    	}
    	
    	if("H".equals(strPoaType)){
    		return this.setPoaCodeMap(poaCode,sumPremium,paymentStartDate,paymentEndDate,certiNos,messageCode, messageDesc);
		}else{
			return this.setPoaCodeMNMap(poaCode,sumPremium,paymentStartDate,paymentEndDate,acntUnit,phoneNumber,certiNos,remark,messageCode,messageDesc);
		}
    	
	}	
	/* ADD BY PENGJINLING 2013-8-5 �Ϻ�XXXXX��������������֧�� END */
	
	/**
	 * @desc XX�ɷ������͵�ƽ̨
	 * @author pengjinling
	 * @throws Exception
	 */
    public Map sendPolicyPayRecRequestDX(DbPool dbpool,BLPrpJpoaInfo blPrpJpoaInfo,String bankAccounts,String warrantBank)throws Exception{
 
        String messageCode = "000000";
        String messageDesc = "";
		BLPrpJpreFeeCharge blPrpJpreFeeCharge = new BLPrpJpreFeeCharge();
    	PolicyPayRecEncoder policyPayRecEncoder = new PolicyPayRecEncoder();
    	PolicyPayRecDecoder policyPayRecDecoder = new PolicyPayRecDecoder();

    	String context="";
    	String response = "";
    	
    	try
		{
			context = policyPayRecEncoder.encode(blPrpJpoaInfo,bankAccounts,warrantBank);
		}
		catch (Exception ex)
		{
			String orderId = blPrpJpoaInfo.getBLPrpJplanFeePre().getArr(0).getOrderId();
			messageCode = "999999";
			messageDesc = "���ɽ�ǿXXXXXƽ̨XX�ɷ�����ӿڴ�ʧ�ܣ�" + orderId + "," + ex.getMessage();
			ex.printStackTrace();
			ErrorLoger.callErrorLogProcedure("sendPolicyPayRecRequestDX", messageCode + "," + messageDesc);
			return this.setPoaCodeMap("","","","","",messageCode, messageDesc);
		}
		
		try
		{
			
			logger.info("context"+context);
			
			response = EbaoProxy.getInstance().request(context, blPrpJpoaInfo.getArr(0).getComCode());
			response = StringUtils.replace(response, "GBK", "GB2312");
			
			logger.info("response"+response);
			
			policyPayRecDecoder.decode(dbpool, blPrpJpoaInfo, response);
			blPrpJpreFeeCharge.convertFlagForSH(dbpool, blPrpJpoaInfo);
		}catch (UserException ue){
			String orderId = blPrpJpoaInfo.getBLPrpJplanFeePre().getArr(0).getOrderId();
			messageCode = "999999";
			messageDesc = "������ǿXXXXXƽ̨����XX�ɷ�����ӿڴ�ʧ�ܣ�" + orderId + "," + ue.getErrorMessage();
			ErrorLoger.callErrorLogProcedure("sendPolicyPayRecRequestDX", messageCode + "," + messageDesc);
			return this.setPoaCodeMap("","","","","",messageCode, messageDesc);
		}catch (Exception ex)
		{
			String orderId = blPrpJpoaInfo.getBLPrpJplanFeePre().getArr(0).getOrderId();
			messageCode = "999999";
			messageDesc = "������ǿXXXXXƽ̨����XX�ɷ�����ӿڴ�ʧ�ܣ�" + orderId + "," + ex.getMessage();
			ex.printStackTrace();
			ErrorLoger.callErrorLogProcedure("sendPolicyPayRecRequestDX", messageCode + "," + messageDesc);
			return this.setPoaCodeMap("","","","","",messageCode, messageDesc);
		} 
		
		return this.setPoaCodeMap("","","","","",messageCode, messageDesc);
    }	
	
	/**
	 * �Ϻ������ո���ѯƽ̨״̬
	 * @param list List<Map> key:V_RCPTNO ������ˮ�š�V_PAYID ƽ̨�����롢V_CMSSTATUS �ۿ���
	 * @throws �쳣��
	 */
	public OMElement queryAccStatusByDX(OMElement omElement) throws Exception {
        logPrintln("��ʼ�Ϻ������ո���ѯƽ̨״̬����...");
        
        Map resultMap = null;
        OMElement resultOME = null;
		
        DbPool dbpool = new DbPool();
        try {
            
            
        	
    		String payment_switch = SysConfig.getProperty("PAYMENT_SWITCH_prpall");
    		if("0".equals(payment_switch)){
    			dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
    		}else{
    			dbpool.open(SysConfig.CONST_PAYMENTNEWDATASOURCE);
    		}
    		
            
            dbpool.beginTransaction();
            resultMap = this.queryAccStatusByDX(dbpool, omElement);
            
            dbpool.commitTransaction();
            
            resultOME = createOMElementFromMap(resultMap, "DXPaymentSHService", "queryAccStatusByDX");
        } catch (Exception exception) {
            dbpool.rollbackTransaction();
            exception.printStackTrace();
            ErrorLoger.callErrorLogProcedure("queryAccStatusByDX", "�Ϻ������ո���ѯ�������쳣:"+ omElement + "," + exception.getMessage());
        } finally {
            logPrintln("�Ϻ������ո���ѯ�����뷽������.");
            dbpool.close();
        }        
      
        return resultOME;
	}
	
	/**
	 * �Ϻ������ո���ѯƽ̨״̬
	 * @param list List<Map> key:V_PAYID ƽ̨�����롢V_CMSSTATUS �ۿ���
	 * @throws �쳣��
	 */
	public Map queryAccStatusByDX(DbPool dbpool,OMElement omElement) throws Exception {
	    logPrintln("��ʼ�Ϻ������ո���ѯƽ̨״̬����...");
	    
	    String strRcptNo = "";
	    String strPayId = "";
	    String strCmsStatus = "";
	    String messageCode = "";
	    String messageDesc = "";
	    Map enterMap = null;
	    Map requestMap = null;
	    /* ADD BY PENGJINLING 2013-8-6 �Ϻ�XXXXX��������������֧�� BEGIN */
	    String strPoaType = "";
	    /* ADD BY PENGJINLING 2013-8-6 �Ϻ�XXXXX��������������֧�� END */
	    
		BLPrpJplanFeePre blPrpJplanFeePre = new BLPrpJplanFeePre();;
		BLPrpJpoaInfo blPrpJpoaInfo = new BLPrpJpoaInfo();
		DBCmsInterface001 dbCmsInterface001 = new DBCmsInterface001();

	    try{
	        enterMap = createMapFromElement(omElement);
	        if (!enterMap.isEmpty()) {
	        	/* ADD BY PENGJINLING 2013-8-6 �Ϻ�XXXXX��������������֧�� BEGIN */
	        	logPrintln("��ʼ�Ϻ������ո���ѯƽ̨״̬����:" + enterMap);
	        	/* ADD BY PENGJINLING 2013-8-6 �Ϻ�XXXXX��������������֧�� end */
	        	
	            strPayId = enterMap.get("V_PAYID").toString();
	            strCmsStatus = enterMap.get("V_CMSSTATUS").toString();
	            
	            if(strPayId == null || "".equals(strPayId)){
					messageCode = "999999";
					messageDesc = enterMap + " �Ϻ������ո���ѯƽ̨״̬�������V_PAYIDΪ��";
					ErrorLoger.callErrorLogProcedure("queryAccStatusByDX", messageCode + "," + messageDesc);
					return this.setStatusMap(messageCode, messageDesc);	            	
	            }
	            
	            /* ADD BY PENGJINLING 2013-8-6 �Ϻ�XXXXX��������������֧�� BEGIN */
	            DBPrpJpoaInfo dbPrpJpoaInfo = new DBPrpJpoaInfo();
	            int result = dbPrpJpoaInfo.getInfo(strPayId);
	        	
	        	if(result == 100){
	        		if("H".equals(strPoaType)){	
	    				messageCode = "000003";
	    			}else{
	    				messageCode = "010003";
	    			}
	    			messageDesc = "û���ҵ�PrpJpoaInfo������Ϣ!V_PAYID:'" + strPayId;                    		
	    			return this.setStatusMap(messageCode, messageDesc);
	        	}
	        	strPoaType = dbPrpJpoaInfo.getPoaType();
	        	/* ADD BY PENGJINLING 2013-8-6 �Ϻ�XXXXX��������������֧�� END */
	        	
	        	/* MODIFY BY PENGJINLING 2013-8-6 �Ϻ�XXXXX��������������֧�� BEGIN */
	        	if("H".equals(strPoaType)){
	        		strRcptNo = enterMap.get("V_RCPTNO").toString();
		            if(strRcptNo == null || "".equals(strRcptNo)){
						messageCode = "999999";
						messageDesc = enterMap + " �Ϻ����������ո���ѯƽ̨״̬�������V_RCPTNOΪ��";
						ErrorLoger.callErrorLogProcedure("queryAccStatusByDX", messageCode + "," + messageDesc);
						return this.setStatusMap(messageCode, messageDesc);	            	
		            }
	        	}
	        	/* MODIFY BY PENGJINLING 2013-8-6 �Ϻ�XXXXX��������������֧�� END */
	            
	            if(strCmsStatus == null || "".equals(strCmsStatus)){
					messageCode = "999999";
					messageDesc = enterMap + " �Ϻ������ո���ѯƽ̨״̬�������V_CMSSTATUSΪ��";
					ErrorLoger.callErrorLogProcedure("queryAccStatusByDX", messageCode + "," + messageDesc);
					return this.setStatusMap(messageCode, messageDesc);	            	
	            }	            
	        }
	        else{
				messageCode = "999999";
				messageDesc = omElement + " �Ϻ������ո���ѯƽ̨״̬�������Ϊ��";
				ErrorLoger.callErrorLogProcedure("queryAccStatusByDX", messageCode + "," + messageDesc);
				return this.setStatusMap(messageCode, messageDesc);
	        }

            if("S".equals(strCmsStatus)){
            	
            	/* MODIFY BY PENGJINLING 2013-8-6 �Ϻ�XXXXX��������������֧�� BEGIN */
            	Map checkMap = this.queryAccStatusByDXCheck(dbpool,strPoaType,strRcptNo,strPayId,strCmsStatus);
            	/* MODIFY BY PENGJINLING 2013-8-6 �Ϻ�XXXXX��������������֧�� END */
            	if("000000".equals(checkMap.get("MessageCode"))){
                    logPrintln("�Ϻ������ո���ѯƽ̨״̬��ʼ��������ˮ�ţ�" + strPayId + "\n�ۿ�״̬��" + strCmsStatus + "\n");
                    
        	    	String checkSql = " POACODE = '"+ strPayId + "'";
        	    	blPrpJpoaInfo.query(dbpool, checkSql, 0);

    	    		
    	    		if("1".equals(blPrpJpoaInfo.getArr(0).getAccFlag())){
            			messageCode = "000000";
            			messageDesc = "�ѵ���," + enterMap;
            			ErrorLoger.callErrorLogProcedure("queryAccStatusByDX", messageCode + "," + messageDesc);
    	    			return this.setStatusMap(messageCode, messageDesc);
    	    		}        	    	
        	    	
        	    	blPrpJplanFeePre.query(dbpool, checkSql, 0);
                    blPrpJpoaInfo.setBLPrpJplanFeePre(blPrpJplanFeePre);
                    
                    requestMap = sendPolicyAccQueryRequestDX(dbpool,blPrpJpoaInfo);
                    
                    if("000000".equals(requestMap.get("MessageCode"))){
                		if(blPrpJpoaInfo.getArr(0).getAccFlag().equals("1")){
                			for(int i =0;i < blPrpJpoaInfo.getBLPrpJplanFeePre().getSize();i++){
                				
                				blPrpJpoaInfo.getBLPrpJplanFeePre().getArr(i).setCertiStatus("4");
                	    		DBPrpJplanFeePre dbPrpJplanFeePre = new DBPrpJplanFeePre();
                	    		dbPrpJplanFeePre.setSchema(blPrpJpoaInfo.getBLPrpJplanFeePre().getArr(i));
                	    		dbPrpJplanFeePre.update(dbpool);
                			}
                			
                			dbCmsInterface001.getInfo(dbpool, blPrpJpoaInfo.getArr(0).getPoaCode());
                			dbCmsInterface001.setDISHONOR_FLAG("1");
                			dbCmsInterface001.update(dbpool);
                			
                			messageCode = "000000";
                			messageDesc = "�ѵ���," + enterMap;			
                		}
                		else if(blPrpJpoaInfo.getArr(0).getAccFlag().equals("0")){
                			if("H".equals(strPoaType)){	
                				messageCode = "000001";
                			}else{
                				messageCode = "010001";
                			}
                			messageDesc = "δ����," + enterMap;	
                		}
                		else if(blPrpJpoaInfo.getArr(0).getAccFlag().equals("3")){
                			if("H".equals(strPoaType)){	
                				messageCode = "000002";
                			}else{
                				messageCode = "010002";
                			}
                			messageDesc = "���׸���," + enterMap;	
                		}
                		ErrorLoger.callErrorLogProcedure("queryAccStatusByDX", messageCode + "," + messageDesc);
                		return this.setStatusMap(messageCode, messageDesc);
                    }
                    else{
                    	return requestMap;
                    }
            	}
            	else{
            		ErrorLoger.callErrorLogProcedure("queryAccStatusByDX", "" + checkMap);
            		return checkMap;
                }
            }
            else{
            	if("H".equals(strPoaType)){	
    				messageCode = "000005";
    			}else{
    				messageCode = "010005";
    			}
    			messageDesc = "����ۿ�״̬��Ϊ�ɹ�����˲�!," + enterMap;
    			ErrorLoger.callErrorLogProcedure("queryAccStatusByDX", messageCode + "," + messageDesc);
				return this.setStatusMap(messageCode, messageDesc);
            }            
	    } catch (Exception exception) {
			messageCode = "999999";
			messageDesc = "�Ϻ������ո���ѯƽ̨״̬�쳣:"+ omElement + "" + exception.getMessage();
	        dbpool.rollbackTransaction();
	        exception.printStackTrace();
	        ErrorLoger.callErrorLogProcedure("queryAccStatusByDX", messageCode + "," + messageDesc);
	        return this.setStatusMap(messageCode, messageDesc);
	    } finally {
	        logPrintln("�Ϻ������ո���ѯƽ̨״̬��������.");
	    }
	}

	/**
	 * ��ѯƽ̨״̬ǰУ��
	 * @param dbpool���ݿ����ӳ�
	 * @param blPrpJpoaInfo ������Ϣ
	 * @return Map ������Ϣ
	 */
	/* MODIFY BY PENGJINLING 2013-8-6 �Ϻ�XXXXX��������������֧�� BEGIN */
    public Map queryAccStatusByDXCheck(DbPool dbpool, String strPoaType,
			String strRcptNo, String strPayId, String strCmsStatus)
			throws Exception {
    /* MODIFY BY PENGJINLING 2013-8-6 �Ϻ�XXXXX��������������֧�� END */
	    String messageCode = "000000";
	    String messageDesc = "";
		BLPrpJpoaInfo blPrpJpoaInfo = new BLPrpJpoaInfo();
    	BLPrpJplanFeePre blPrpJplanFeePre = new BLPrpJplanFeePre();;
    	DBCmsInterface001 dbCmsInterface001 = new DBCmsInterface001();
    	
    	/* MODIFY BY PENGJINLING 2013-8-6 �Ϻ�XXXXX��������������֧�� BEGIN */
    	String checkSql = "";
    	if("H".equals(strPoaType)){
    		checkSql = " POACODE = '"+ strPayId + "' AND ORDERID = '" + strRcptNo + "'";
    	}else{
    		checkSql = " POACODE = '"+ strPayId + "'";
    	}
    	/* MODIFY BY PENGJINLING 2013-8-6 �Ϻ�XXXXX��������������֧�� END */
	    blPrpJplanFeePre.query(dbpool, checkSql,0);
	    
	    if(blPrpJplanFeePre.getSize() == 0){
	    	if("H".equals(strPoaType)){	
				messageCode = "000003";
			}else{
				messageCode = "010003";
			}
			/* MODIFY BY PENGJINLING 2013-8-6 �Ϻ�XXXXX��������������֧�� BEGIN */
	    	if("H".equals(strPoaType)){			
	    		messageDesc = "û���ҵ�PrpJplanFeePre������Ϣ!V_PAYID:'" + strPayId + "';V_RCPTNO:'" + strRcptNo + "';V_CMSSTATUS:" + strCmsStatus;
	    	}else{
	    		messageDesc = "û���ҵ�PrpJplanFeePre������Ϣ!V_PAYID:'" + strPayId + "';V_CMSSTATUS:" + strCmsStatus;	    		
	    	}
	    	/* MODIFY BY PENGJINLING 2013-8-6 �Ϻ�XXXXX��������������֧�� END */
			return this.setStatusMap(messageCode, messageDesc);
	    }
    
		int count = dbCmsInterface001.getInfo(dbpool, strPayId);
		
		if(count == 100){
			if("H".equals(strPoaType)){	
				messageCode = "000003";
			}else{
				messageCode = "010003";
			}
			/* MODIFY BY PENGJINLING 2013-8-6 �Ϻ�XXXXX��������������֧�� BEGIN */
			messageDesc = "û���ҵ�CmsInterface001������Ϣ!V_PAYID:'" + strPayId + "';V_CMSSTATUS:" + strCmsStatus;
			/* MODIFY BY PENGJINLING 2013-8-6 �Ϻ�XXXXX��������������֧�� END */                    		
			return this.setStatusMap(messageCode, messageDesc);    			
		}
		else{
			if("H".equals(strPoaType)){
				
				if(!"S".equals(dbCmsInterface001.getCMS_STATUS())){
	    			messageCode = "000004";
	    			/* MODIFY BY PENGJINLING 2013-8-6 �Ϻ�XXXXX��������������֧�� BEGIN */
	    			messageDesc = "����CmsInterface001��Ϊ�ɹ�״̬����˲�CMS_STATUS!V_PAYID:'" + strPayId + "';V_CMSSTATUS:" + strCmsStatus;
	    			/* MODIFY BY PENGJINLING 2013-8-6 �Ϻ�XXXXX��������������֧�� END */                   			
	    			return this.setStatusMap(messageCode, messageDesc);
				}	
			}
		}
	    
	    return this.setStatusMap(messageCode, messageDesc);
    }
	
	/**
	 * ��ѯƽ̨״̬
	 * @param dbpool���ݿ����ӳ�
	 * @param blPrpJpoaInfo ������Ϣ
	 * @return Map ������Ϣ
	 */
    public Map sendPolicyAccQueryRequestDX(DbPool dbpool,BLPrpJpoaInfo blPrpJpoaInfo)throws Exception{
		String messageCode = "000000";
		String messageDesc = "";
		
    	BLPrpJpreFeeCharge blPrpJpreFeeCharge = new BLPrpJpreFeeCharge();
		PolicyAccQueryEncoder policyAccQueryEncoder = new PolicyAccQueryEncoder();
		PolicyAccQueryDecoder policyAccQueryDecoder = new PolicyAccQueryDecoder();
		String context = "";
		String response = "";
		try {
			context = policyAccQueryEncoder.encode(blPrpJpoaInfo);
		} catch (Exception ex) {
			String poaCode = blPrpJpoaInfo.getArr(0).getPoaCode();
			messageCode = "999999";
			messageDesc = "���ɽ�ǿXXXXXƽ̨��ѯ״̬����ӿڴ�ʧ�ܣ�" + poaCode + "," + ex.getMessage();
			ex.printStackTrace();
			ErrorLoger.callErrorLogProcedure("sendPolicyAccQueryRequestDX", messageCode + "," + messageDesc);
			return this.setStatusMap(messageCode, messageDesc);
		}
		
		try {		
			
			logger.info("context" + context);
			
			response = EbaoProxy.getInstance().request(context, blPrpJpoaInfo.getArr(0).getComCode());
			response = StringUtils.replace(response, "GBK", "GB2312");
			
			logger.info("response" + response);
			
			policyAccQueryDecoder.decode(dbpool, blPrpJpoaInfo, response);
			blPrpJpreFeeCharge.convertFlagForSH(dbpool, blPrpJpoaInfo);			
		} catch (UserException ex) {
			String poaCode = blPrpJpoaInfo.getArr(0).getPoaCode();
			messageCode = "999999";
			messageDesc = "������ǿXXXXXƽ̨���ز�ѯ״̬����ӿڴ�ʧ�ܣ�" + poaCode + "," + ex.getErrorMessage();
			ex.printStackTrace();
			ErrorLoger.callErrorLogProcedure("sendPolicyAccQueryRequestDX",messageCode + "," +  messageDesc);
			return this.setStatusMap(messageCode, messageDesc);
		} catch (Exception ex) {
			String poaCode = blPrpJpoaInfo.getArr(0).getPoaCode();
			messageCode = "999999";
			messageDesc = "������ǿXXXXXƽ̨���ز�ѯ״̬����ӿڴ�ʧ�ܣ�" + poaCode + "," + ex.getMessage();
			ex.printStackTrace();
			ErrorLoger.callErrorLogProcedure("sendPolicyAccQueryRequestDX", messageCode + "," + messageDesc);
			return this.setStatusMap(messageCode, messageDesc);
		}
		
		return this.setStatusMap(messageCode, messageDesc);
	} 	
	
	/**
	 * ��������У��洢����
	 * @param dbpool���ݿ����ӳ�
	 * @param CardType ֧�����͡�RcptNo �����š�PayId ��ˮ�š�SumPremium ��ProList XX���б�
	 * @return String ������Ϣ
	 * @throws �쳣��
	 */
	public Map callProcedure(DbPool dbpool, String strCardType,String strRcptNo,String strPayId,
			String strSumPremium,String strProList) throws Exception {
		String messageCode = "";
		String messageDesc = "";
		Map checkMap = new HashMap();
		
		Connection conn = this.getConnection(dbpool);
		CallableStatement cstmt = conn.prepareCall("{call PAYMENTMUTUAL_PKG.PdxToPaymentCheckSH(?,?,?,?,?,?,?)}");
		cstmt.registerOutParameter(6, Types.VARCHAR);
		cstmt.registerOutParameter(7, Types.VARCHAR);
		cstmt.setString(1, strCardType);
		cstmt.setString(2, strRcptNo);
		cstmt.setString(3, strPayId);
		cstmt.setString(4, strSumPremium);
		cstmt.setString(5, strProList);
		cstmt.setString(6, messageCode);
		cstmt.setString(7, messageDesc);
		cstmt.execute();
		
		messageCode = cstmt.getString(6);
		messageDesc = cstmt.getString(7);
		logPrintln("�洢���̷�����Ϣ���룺"+messageCode);
		logPrintln("�洢���̷�����ϸ��Ϣ��"+messageDesc);
		checkMap.put("MessageCode", messageCode);
		checkMap.put("MessageDesc", messageDesc);
		return checkMap;
	}
	
	/* ADD BY PENGJINLING 2013-8-5 �Ϻ�XXXXX��������������֧�� BEGIN */
	/**
	 * ��������У��洢����
	 * @param dbpool���ݿ����ӳ�
	 * @param CardType ֧�����͡�RcptNo �����š�PayId ��ˮ�š�SumPremium ��ProList XX���б�
	 * @return String ������Ϣ
	 * @throws �쳣��
	 */
	public Map callProcedure(DbPool dbpool, String strCardType,String strPayId,String strCERTINOANDAPPNAME) throws Exception {
		String strSumPremium  = "";
		String messageCode = "";
		String messageDesc = "";
		Map checkMap = new HashMap();
		
		Connection conn = this.getConnection(dbpool);
		CallableStatement cstmt = conn.prepareCall("{call PAYMENTMUTUAL_PKG.PmnToPaymentCheckSH(?,?,?,?,?,?)}");
		cstmt.registerOutParameter(4, Types.VARCHAR);
		cstmt.registerOutParameter(5, Types.VARCHAR);
		cstmt.registerOutParameter(6, Types.VARCHAR);
		cstmt.setString(1, strCardType);
		cstmt.setString(2, strPayId);
		cstmt.setString(3, strCERTINOANDAPPNAME);		
		cstmt.setString(4, strSumPremium);
		cstmt.setString(5, messageCode);
		cstmt.setString(6, messageDesc);		
		cstmt.execute();
		
		strSumPremium = cstmt.getString(4);
		messageCode = cstmt.getString(5);
		messageDesc = cstmt.getString(6);
		logPrintln("�洢���̷��ؽ�"+strSumPremium);
		logPrintln("�洢���̷�����Ϣ���룺"+messageCode);
		logPrintln("�洢���̷�����ϸ��Ϣ��"+messageDesc);
		checkMap.put("SumPremium", strSumPremium);
		checkMap.put("MessageCode", messageCode);
		checkMap.put("MessageDesc", messageDesc);
		return checkMap;
	}
	/* ADD BY PENGJINLING 2013-8-5 �Ϻ�XXXXX��������������֧�� END */
	
	
	/**
	 * �гϰ���XXXXXƽ̨ͨ�����ߵ����ո���ѯƽ̨������
	 * @param list 	 XX���б�
	 * @param String �ܽ��
	 * @param String ֧����ʽ���������Ǯ��01-������09-��Ǯ
	 * @throws �쳣��
	 */
	public OMElement queryPoaCodeByEX(OMElement omElement) throws Exception {
        logPrintln("��ʼ�гϰ���XXXXXƽ̨ͨ�����ߵ����ո���ѯƽ̨�����뷽��...");
        Map resultMap = null;
        OMElement resultOME = null;
        
    	DbPool dbpool = new DbPool();
    	try {
			
			dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);			
			
			dbpool.beginTransaction();
			
			resultMap = queryPoaCodeByEX(dbpool,omElement);
			
			dbpool.commitTransaction();
			
			
	        resultOME = createOMElementFromMap(resultMap, "DXPaymentSHService", "queryPoaCodeByEX");
	    } catch (Exception exception) {
	        dbpool.rollbackTransaction();
	        exception.printStackTrace();
	        ErrorLoger.callErrorLogProcedure("queryPoaCodeByEX", "�гϰ���XXXXXƽ̨ͨ�����ߵ����ո���ѯƽ̨�������쳣:"+ omElement + "," + exception.getMessage());
	    } finally {
	        logPrintln("�гϰ���XXXXXƽ̨ͨ�����ߵ����ո���ѯƽ̨�����뷽������.");
	        dbpool.close();
	    }		

        return resultOME;		
	}
	/**
	 * �гϰ���XXXXXƽ̨ͨ�����ߵ����ո���ѯXX״̬
	 * @param String �ܽ��
	 * @param String ƽ̨������
	 * @throws �쳣��
	 */
	public OMElement preConfirmEx(OMElement omElement) throws Exception {
        logPrintln("��ʼ�гϰ���XXXXXƽ̨ͨ�����ߵ����ո���ѯXX״̬����...");
        
        List resultList = null;
        OMElement resultOME = null;
		
        DbPool dbpool = new DbPool();
        try {
            
            dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
            
            dbpool.beginTransaction();
            resultList = this.preConfirmEx(dbpool, omElement);
            
            dbpool.commitTransaction();
            
            resultOME = createOMElementFromList(resultList, "DXPaymentSHService", "queryAccStatusByEX");
        } catch (Exception exception) {
            dbpool.rollbackTransaction();
            exception.printStackTrace();
            ErrorLoger.callErrorLogProcedure("queryAccStatusByEX", "�гϰ���XXXXXƽ̨ͨ�����ߵ����ո���ѯXX״̬�쳣:"+ omElement + "," + exception.getMessage());
        } finally {
            logPrintln("�гϰ���XXXXXƽ̨ͨ�����ߵ����ո���ѯXX״̬��������.");
            dbpool.close();
        }        
      
        return resultOME;
	}
	/**
	 * �гϰ���XXXXXƽ̨ͨ�����ߵ����ո���ѯƽ̨������
	 * @param dbpool���ݿ����ӳ�
	 * @param list 	 XX���б�
	 * @param String �ܽ��
	 * @param String ֧����ʽ���������Ǯ��01-������09-��Ǯ
	 * @throws �쳣��
	 */
	public Map queryPoaCodeByEX(DbPool dbpool,OMElement omElement) throws Exception {
		List certiNoList = null;
		String certiNo = "";
		String payMethod = "";
		String sumFee = "";
        
		String messageCode = "";
        String messageDesc = "";
        
		BLPrpJpoaInfo blPrpJpoaInfo = new BLPrpJpoaInfo();
		BLPrpJplanFeePre blPrpJplanFeePre = new BLPrpJplanFeePre();
		PrpJpoaInfoSchema prpJpoaInfoSehema = new PrpJpoaInfoSchema();
		DBPrpJplanFeePre dbPrpJplanFeePre = null;
		DBPrpJpoaInfo dbPrpJpoaInfo = new DBPrpJpoaInfo();
		DateTime currentTime = new DateTime(new Date(), DateTime.YEAR_TO_SECOND);
		double sumPremium = 0.00;
		String customNo = "";
        String strPoaCode = "";
        String strSumPremium = "";
        String strPaymentStartDate = "";
        String strPaymentEndDate = "";
        Map enterMap = null;
        Map checkMap = null;
        Map resultMap = null;
        
        
        enterMap = createMapOfListFromElement(omElement);
        
        
        if (!enterMap.isEmpty()) {
        	
        	
        	certiNoList = (List) enterMap.get("certiNoList");
        	payMethod = enterMap.get("payMethod").toString();
        	sumFee = enterMap.get("sumFee").toString();
        	
        	checkMap = checkPoaCodeByEX(dbpool,enterMap,certiNoList,payMethod,sumFee);
        	if("000000".equals(checkMap.get("MessageCode"))){
        		if(checkMap.get("PayId")!=null && !"".equals(checkMap.get("PayId"))){
        			return checkMap;
        		}else{
	        		for(int i = 0; i < certiNoList.size(); i++){
	        			certiNo = (String) certiNoList.get(i);
	        			dbPrpJplanFeePre = new DBPrpJplanFeePre();
	        			dbPrpJplanFeePre.getInfo(certiNo, "0");
	        			blPrpJplanFeePre.setArr(dbPrpJplanFeePre);
	        			
	        			sumPremium += Double.parseDouble(dbPrpJplanFeePre.getPlanFee());
	        		}
	        		blPrpJpoaInfo.setBLPrpJplanFeePre(blPrpJplanFeePre);
        			prpJpoaInfoSehema.setPoaType("2");
        			prpJpoaInfoSehema.setPOSModelNo("");
        			prpJpoaInfoSehema.setAccBillAmount(Double.toString(sumPremium));
        			prpJpoaInfoSehema.setAccBillNo("");
        			prpJpoaInfoSehema.setAccBillProvider("");
        			prpJpoaInfoSehema.setPayMethod(payMethod);
        			prpJpoaInfoSehema.setAccFlag("0");
        			prpJpoaInfoSehema.setOperatorCode("00000000");
        			prpJpoaInfoSehema.setComCode(blPrpJplanFeePre.getArr(0).getComCode());
        			prpJpoaInfoSehema.setMakeCom(blPrpJplanFeePre.getArr(0).getMakeCom());
        			prpJpoaInfoSehema.setAgentCode(blPrpJplanFeePre.getArr(0).getAgentCode());
        			prpJpoaInfoSehema.setCenterCode(blPrpJplanFeePre.getArr(0).getCenterCode());
        			prpJpoaInfoSehema.setTotalFee(Double.toString(sumPremium));
        			prpJpoaInfoSehema.setOpenDate(currentTime.toString());
        			prpJpoaInfoSehema.setBusinessNo(blPrpJplanFeePre.getArr(0).getCertiNo());
        			prpJpoaInfoSehema.setValidDate(blPrpJplanFeePre.getArr(0).getValidDate());	
        			prpJpoaInfoSehema.setAccBillBank("");
        			if("01".equals(payMethod)){
        				customNo = "005";
        			}else if("09".equals(payMethod)){
        				customNo = "004";
        			}
        			prpJpoaInfoSehema.setCustomNo(customNo);
        			blPrpJpoaInfo.setArr(prpJpoaInfoSehema);
	        		
	        		Map requestMap =  this.sendPolicyPayRecRequestEX(blPrpJpoaInfo,customNo,"");
	        		String requestMessageCode = requestMap.get("MessageCode").toString();
	        		if(!"000000".equals(requestMessageCode)){
	        			return requestMap;
	        		}else{
	        			
	        			dbPrpJplanFeePre = new DBPrpJplanFeePre();
	        			dbPrpJplanFeePre.getInfo((String) certiNoList.get(0), "0");
	        			if(!"".equals(dbPrpJplanFeePre.getPoaCode())){
	        				dbPrpJpoaInfo.getInfo(dbPrpJplanFeePre.getPoaCode());
	        				strPoaCode = dbPrpJpoaInfo.getPoaCode();
	        				strSumPremium = dbPrpJpoaInfo.getTotalFee();
	        				strPaymentStartDate = dbPrpJpoaInfo.getPaymentStartDate();
	        				strPaymentEndDate = dbPrpJpoaInfo.getPaymentEndDate();
	        				messageCode = "000000";
	        				messageDesc = "��ȡ������ɹ�:"+ certiNoList + "," + strPoaCode;
	        				ErrorLoger.callErrorLogProcedure("queryPoaCodeByEX", messageCode + "," + messageDesc);
	        				return this.setPoaCodeZCMap(strPoaCode,strSumPremium,strPaymentStartDate,strPaymentEndDate,messageCode, messageDesc);
	        			}else{
	        				messageCode = "100000";
	        				messageDesc = "��ѯ������ʧ��," + certiNoList;
	        				ErrorLoger.callErrorLogProcedure("queryPoaCodeByEX", messageCode + "," + messageDesc);
	        				return this.setPoaCodeZCMap("","","","",messageCode, messageDesc);
	        			}
	        		}
	        	}
        	}else{
        		return checkMap;
            }
        }else{
			messageCode = "999999";
			messageDesc = omElement + " �гϰ���XXXXXƽ̨ͨ�����ߵ����ո���ѯƽ̨���������Ϊ��";
			return this.setPoaCodeZCMap("","","","",messageCode, messageDesc);
        }
	}
	/**
	 * �гϰ���XXXXXƽ̨ͨ�����ߵ����ո���ѯƽ̨������У��
	 * @param String �ܽ��
	 * @param String ƽ̨������
	 * @throws Exception 
	 * @throws �쳣��
	 */
	public Map checkPoaCodeByEX(DbPool dbpool,Map enterMap,List<String> certiNoList,String payMethod,String sumFee) throws Exception{
		String messageCode = "000000";
        String messageDesc = "";
        double sumFeeD = 0D;
        double sumPremium = 0D;
        String strPoaCode = "";
        String strSumPremium = "";
        String strPaymentStartDate = "";
        String strPaymentEndDate = "";
        String certiStatusOne = "";
        String poaTypeOne = "";
        String poaCodeOne = "";
        DBPrpJplanFeePre dbPrpJplanFeePre = null;
        DBPrpJpoaInfo dbPrpJpoaInfo = new DBPrpJpoaInfo();
        DateTime currentTime = new DateTime(new Date(), DateTime.YEAR_TO_SECOND);
        
		if(certiNoList == null || certiNoList.size()==0){
			messageCode = "999999";
			messageDesc = enterMap + " �гϰ���XXXXXƽ̨ͨ�����ߵ����ո���ѯƽ̨���������certiNoListΪ��";
			ErrorLoger.callErrorLogProcedure("queryPoaCodeByEX", messageCode + "," + messageDesc);
			return this.setStatusMap(messageCode, messageDesc);	            	
        }
		if(payMethod == null || "".equals(payMethod)){
			messageCode = "999999";
			messageDesc = enterMap + " �гϰ���XXXXXƽ̨ͨ�����ߵ����ո���ѯƽ̨���������payMethodΪ��";
			ErrorLoger.callErrorLogProcedure("queryPoaCodeByEX", messageCode + "," + messageDesc);
			return this.setStatusMap(messageCode, messageDesc);	            	
        }
		if(!"01".equals(payMethod) && !"09".equals(payMethod)){
			messageCode = "999999";
			messageDesc = enterMap + " �гϰ���XXXXXƽ̨ͨ�����ߵ����ո���ѯƽ̨���������payMethod��Ϊ01��09";
			ErrorLoger.callErrorLogProcedure("queryPoaCodeByEX", messageCode + "," + messageDesc);
			return this.setStatusMap(messageCode, messageDesc);	            	
        }
		if(sumFee == null || "".equals(sumFee)){
			messageCode = "999999";
			messageDesc = enterMap + " �гϰ���XXXXXƽ̨ͨ�����ߵ����ո���ѯƽ̨���������sumFeeΪ��";
			ErrorLoger.callErrorLogProcedure("queryPoaCodeByEX", messageCode + "," + messageDesc);
			return this.setStatusMap(messageCode, messageDesc);	            	
        }
		
		try {
			
			BigDecimal BigDecimalSumFee = new BigDecimal(sumFee).setScale(2, BigDecimal.ROUND_HALF_UP);
		} catch (Exception ex) {
			messageCode = "999999";
			messageDesc = enterMap + " �гϰ���XXXXXƽ̨ͨ�����ߵ����ո���ѯƽ̨���������sumFee��ʽ����ȷ";
			ErrorLoger.callErrorLogProcedure("queryPoaCodeByEX", messageCode + "," + messageDesc);
			return this.setStatusMap(messageCode, messageDesc);
		}
		sumFeeD = Double.parseDouble(sumFee);
		if(sumFeeD<=0){
			messageCode = "999999";
			messageDesc = enterMap + " �гϰ���XXXXXƽ̨ͨ�����ߵ����ո���ѯƽ̨���������sumFee������0";
			ErrorLoger.callErrorLogProcedure("queryPoaCodeByEX", messageCode + "," + messageDesc);
			return this.setStatusMap(messageCode, messageDesc);	            	
        }
		
		for(int i = 0; i < certiNoList.size(); i++){
			String certiNo = certiNoList.get(i);
			
			dbPrpJplanFeePre = new DBPrpJplanFeePre();
			int count = dbPrpJplanFeePre.getInfo(certiNo, "0");
	    	if(count == 100){
	    		messageCode = "000001";
				messageDesc = enterMap + "CertiNo:"+certiNo+"XX��δ���ͨ��";
				ErrorLoger.callErrorLogProcedure("queryPoaCodeByEX", messageCode + "," + messageDesc);
				return this.setPoaCodeZCMap("","","","",messageCode, messageDesc);
	    	}else{
	    		if(!"07".equals(dbPrpJplanFeePre.getComCode().substring(0, 2))){
	    			messageCode = "000002";
					messageDesc = enterMap + "CertiNo:"+certiNo+"XX��������Ϊ�Ϻ���ϵͳ��֧��";
					ErrorLoger.callErrorLogProcedure("queryPoaCodeByEX", messageCode + "," + messageDesc);
					return this.setPoaCodeZCMap("","","","",messageCode, messageDesc);
	    		}
	    		if(!"05".equals(dbPrpJplanFeePre.getClassCode())){
	    			messageCode = "000003";
					messageDesc = enterMap + "CertiNo:"+certiNo+"XX����ΪXXXXX��ϵͳ��֧��";
					ErrorLoger.callErrorLogProcedure("queryPoaCodeByEX", messageCode + "," + messageDesc);
					return this.setPoaCodeZCMap("","","","",messageCode, messageDesc);
	    		}
	    		
    			if(i == 0){
    				certiStatusOne = dbPrpJplanFeePre.getCertiStatus();
    				poaTypeOne = dbPrpJplanFeePre.getPoaType();
    				poaCodeOne = dbPrpJplanFeePre.getPoaCode();
    				
    				if(poaTypeOne != null && !"".equals(poaTypeOne)){
    					if(!"2".equals(poaTypeOne)){
    	    				messageCode = "000004";
    						messageDesc = enterMap + "CertiNo:"+certiNo+"XX��֧����ʽ����";
    						ErrorLoger.callErrorLogProcedure("queryPoaCodeByEX", messageCode + "," + messageDesc);
    						return this.setPoaCodeZCMap("","","","",messageCode, messageDesc);   						
    					}
    				}
    			}else{
	    			if(!dbPrpJplanFeePre.getCertiStatus().equals(certiStatusOne)){
	    				messageCode = "000005";
						messageDesc = enterMap + "CertiNo:"+certiNo+"XX��״̬��һ��";
						ErrorLoger.callErrorLogProcedure("queryPoaCodeByEX", messageCode + "," + messageDesc);
						return this.setPoaCodeZCMap("","","","",messageCode, messageDesc);
	    			}
	    			if(poaTypeOne == null || "".equals(poaTypeOne)){
	    				if(!(dbPrpJplanFeePre.getPoaType() == null) && !"".equals(dbPrpJplanFeePre.getPoaType())){
		    				messageCode = "000006";
							messageDesc = enterMap + "CertiNo:"+certiNo+"XX��֧����ʽ��һ��";
							ErrorLoger.callErrorLogProcedure("queryPoaCodeByEX", messageCode + "," + messageDesc);
							return this.setPoaCodeZCMap("","","","",messageCode, messageDesc);    					    					
	    				}
	    			}else{
	        			if(!poaTypeOne.equals(dbPrpJplanFeePre.getPoaType())){
	        				messageCode = "000006";
	    					messageDesc = enterMap + "CertiNo:"+certiNo+"XX��֧����ʽ��һ��";
	    					ErrorLoger.callErrorLogProcedure("queryPoaCodeByEX", messageCode + "," + messageDesc);
	    					return this.setPoaCodeZCMap("","","","",messageCode, messageDesc);
	        			}
	    			}
	    			if(poaCodeOne == null || "".equals(poaCodeOne)){
	    				if(!(dbPrpJplanFeePre.getPoaCode() == null) && !"".equals(dbPrpJplanFeePre.getPoaCode())){
	        				messageCode = "000007";
	    					messageDesc = enterMap + "CertiNo:"+certiNo+"XX�������벻һ��";
	    					ErrorLoger.callErrorLogProcedure("queryPoaCodeByEX", messageCode + "," + messageDesc);
	    					return this.setPoaCodeZCMap("","","","",messageCode, messageDesc); 					    					
	    				}
	    			}else{
		    			if(!poaCodeOne.equals(dbPrpJplanFeePre.getPoaCode())){
		    				messageCode = "000007";
							messageDesc = enterMap + "CertiNo:"+certiNo+"XX�������벻һ��";
							ErrorLoger.callErrorLogProcedure("queryPoaCodeByEX", messageCode + "," + messageDesc);
							return this.setPoaCodeZCMap("","","","",messageCode, messageDesc);
		    			}
	    			}
    			}
	    	}
	    	sumPremium += Double.parseDouble(dbPrpJplanFeePre.getPlanFee());
		}
		
		
		BigDecimal sumPremiumBig = new BigDecimal(sumPremium).setScale(2, BigDecimal.ROUND_HALF_UP);
    	BigDecimal sumFeeBig = new BigDecimal(sumFee).setScale(2, BigDecimal.ROUND_HALF_UP);
		int compareValue = sumPremiumBig.compareTo(sumFeeBig);
		if(compareValue != 0){
			messageCode = "000008";
			messageDesc = enterMap + "����ܽ��sumFee��"+sumFee+"��XX���ܽ��SumPremium��"+Double.toString(sumPremium)+"�����";
			ErrorLoger.callErrorLogProcedure("queryPoaCodeByEX", messageCode + "," + messageDesc);
			return this.setPoaCodeZCMap("","","","",messageCode, messageDesc);         	
        }
		
		
		if("1".equals(certiStatusOne) && poaCodeOne!=null && !"".equals(poaCodeOne)){
			dbPrpJpoaInfo.getInfo(poaCodeOne);
			
			DateTime paymentEndDate = new DateTime(dbPrpJpoaInfo.getPaymentEndDate(), DateTime.YEAR_TO_SECOND);
			if(currentTime.after(paymentEndDate)){
				messageCode = "000009";
				messageDesc = enterMap + "�ѹ��ɷ�ֹ��"+dbPrpJpoaInfo.getPaymentEndDate();
				ErrorLoger.callErrorLogProcedure("queryPoaCodeByEX", messageCode + "," + messageDesc);
				return this.setPoaCodeZCMap("","","","",messageCode, messageDesc);
			}else{
				strPoaCode = dbPrpJpoaInfo.getPoaCode();
				strSumPremium = dbPrpJpoaInfo.getTotalFee();
				strPaymentStartDate = dbPrpJpoaInfo.getPaymentStartDate();
				strPaymentEndDate = dbPrpJpoaInfo.getPaymentEndDate();
				messageCode = "000000";
				messageDesc = "��ȡ������ɹ�:"+ certiNoList + "," + strPoaCode;
				ErrorLoger.callErrorLogProcedure("queryPoaCodeByEX", messageCode + "," + messageDesc);
				return this.setPoaCodeZCMap(strPoaCode,strSumPremium,strPaymentStartDate,strPaymentEndDate,messageCode, messageDesc);
			}
		}else{
			if(!"0".equals(certiStatusOne)){
				messageCode = "000010";
				messageDesc = enterMap + "XX��״̬���ǳ�ʼ";
				ErrorLoger.callErrorLogProcedure("queryPoaCodeByEX", messageCode + "," + messageDesc);
				return this.setPoaCodeZCMap("","","","",messageCode, messageDesc);
			}
			if(poaTypeOne != null && !"".equals(poaTypeOne)){
				messageCode = "000011";
				messageDesc = enterMap + "XX��֧����ʽ��Ϊ��";
				ErrorLoger.callErrorLogProcedure("queryPoaCodeByEX", messageCode + "," + messageDesc);
				return this.setPoaCodeZCMap("","","","",messageCode, messageDesc);
			}
		}
	
		return this.setPoaCodeZCMap("","","","",messageCode, messageDesc);
	}
	/**
	 * @desc ��ȡ�����������͵�ƽ̨
	 * @author suxiaotong
	 * @throws Exception
	 */
    public Map sendPolicyPayRecRequestEX(BLPrpJpoaInfo blPrpJpoaInfo,String bankAccounts,String warrantBank)throws Exception{
 
        String messageCode = "000000";
        String messageDesc = "";
		BLPrpJpreFeeCharge blPrpJpreFeeCharge = new BLPrpJpreFeeCharge();
    	PolicyPayRecEncoder policyPayRecEncoder = new PolicyPayRecEncoder();
    	PolicyPayRecDecoder policyPayRecDecoder = new PolicyPayRecDecoder();

    	String context="";
    	String response = "";
    	
    	DbPool dbpool = new DbPool();
        
        
    	
		String payment_switch = SysConfig.getProperty("PAYMENT_SWITCH_prpall");
		if("0".equals(payment_switch)){
			dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
		}else{
			dbpool.open(SysConfig.CONST_PAYMENTNEWDATASOURCE);
		}
		
    	
    	try
		{
			context = policyPayRecEncoder.encode(blPrpJpoaInfo,bankAccounts,warrantBank);
		}
		catch (Exception ex)
		{
			String businessNo = blPrpJpoaInfo.getArr(0).getBusinessNo();
			messageCode = "999999";
			messageDesc = "����ƽ̨����������ӿڴ�ʧ�ܣ�" + businessNo + "," + ex.getMessage();
			ex.printStackTrace();
			ErrorLoger.callErrorLogProcedure("sendPolicyPayRecRequestEX", messageCode + "," + messageDesc);
			return this.setPoaCodeZCMap("","","","",messageCode, messageDesc);
		}
		
		try
		{
			dbpool.beginTransaction();
			
			logger.info("context"+context);
			
			response = EbaoProxy.getInstance().request(context, blPrpJpoaInfo.getArr(0).getComCode());
			response = StringUtils.replace(response, "GBK", "GB2312");
			
			logger.info("response"+response);
			
			policyPayRecDecoder.decode(dbpool, blPrpJpoaInfo, response);
			blPrpJpreFeeCharge.convertFlagForSH(dbpool, blPrpJpoaInfo);
			dbpool.commitTransaction(); 
		}catch (UserException ue){
			String businessNo = blPrpJpoaInfo.getArr(0).getBusinessNo();
			messageCode = "999999";
			messageDesc = "����ƽ̨��������������ӿڴ�ʧ�ܣ�" + businessNo + "," + ue.getErrorMessage();
			ErrorLoger.callErrorLogProcedure("sendPolicyPayRecRequestEX", messageCode + "," + messageDesc);
			dbpool.rollbackTransaction(); 
			return this.setPoaCodeZCMap("","","","",messageCode, messageDesc);
		}catch (Exception ex)
		{
			String businessNo = blPrpJpoaInfo.getArr(0).getBusinessNo();
			messageCode = "999999";
			messageDesc = "����ƽ̨�����뷵������ӿڴ�ʧ�ܣ�" + businessNo + "," + ex.getMessage();
			ex.printStackTrace();
			ErrorLoger.callErrorLogProcedure("sendPolicyPayRecRequestEX", messageCode + "," + messageDesc);
			dbpool.rollbackTransaction(); 
			return this.setStatusMap(messageCode, messageDesc);
		} 
		finally{
            dbpool.close();
        }
		return this.setPoaCodeZCMap("","","","",messageCode, messageDesc);
    }	
	/**
	 * �гϰ���XXXXXƽ̨ͨ�����ߵ����ո���ѯXX״̬
	 * @param dbpool���ݿ����ӳ�
	 * @param String �ܽ��
	 * @param String ƽ̨������
	 * @throws Exception 
	 * @throws �쳣��
	 */
    public ArrayList preConfirmEx(DbPool dbpool,OMElement omElement) throws Exception{

    	Map enterMap = null;
         List checkPreConfirmList = null;
         Map resultCheckMap = null;
         Map listMap = null;
         List mapList = new ArrayList();
         ArrayList policyNoList = null;
         String chectMessageCode = "";
         String sumFee = "";
         String poaCode = "";
         BLPrpJpoaInfo blPrpJpoaInfo = new BLPrpJpoaInfo();
         BLPrpJplanFeePre blPrpJplanFeePre = new BLPrpJplanFeePre();;
         DBPrpJpoaInfo dbPrpJpoaInfo = new DBPrpJpoaInfo();
         
         enterMap = createMapFromElement(omElement);
         
         if (!enterMap.isEmpty()) {
         	
        	poaCode = (String) enterMap.get("poaCode").toString();
         	sumFee = (String)enterMap.get("sumFee").toString();
         	
         	checkPreConfirmList = checkPreConfirmEX(dbpool,enterMap,poaCode,sumFee);
         	
         	resultCheckMap = (Map) checkPreConfirmList.get(0);
         	chectMessageCode = resultCheckMap.get("MessageCode").toString();
            
            
         	if("000000".equals(chectMessageCode)){
             	dbPrpJpoaInfo.getInfo(poaCode);
             	
         		String checkSql = " POACODE = '"+ poaCode + "' ";
    	    	blPrpJplanFeePre.query(dbpool, checkSql, 0);
                blPrpJpoaInfo.setBLPrpJplanFeePre(blPrpJplanFeePre);
    	    	blPrpJpoaInfo.setArr(dbPrpJpoaInfo);
         		
         		if("0".equals(dbPrpJpoaInfo.getAccFlag())){
                 	
        	    	
         			Map requestMap =  this.sendPolicyAccQueryRequestEX(blPrpJpoaInfo);
         			String requestMessageCode = requestMap.get("MessageCode").toString();
	        		if(!"000000".equals(requestMessageCode)){
	        			for(int i = 0; i < blPrpJplanFeePre.getSize(); i++){
        					requestMap.put("CertiNo",blPrpJplanFeePre.getArr(i).getCertiNo());
        					requestMap.put("PolicyNo",blPrpJplanFeePre.getArr(i).getPolicyNo());
	        				mapList.add(requestMap);
        				}
	        		}else{
	        			
	        			dbPrpJpoaInfo.getInfo(poaCode);
	        			if("1".equals(dbPrpJpoaInfo.getAccFlag())){
	        		    	String[] certiType = new String[blPrpJplanFeePre.getSize()];
	                		String[] certiNo = new String[blPrpJplanFeePre.getSize()];
	                		String[] serialNo = new String[blPrpJplanFeePre.getSize()];
	        		    	for(int i = 0; i < blPrpJplanFeePre.getSize(); i++){
	        		    		certiType[i] = blPrpJplanFeePre.getArr(i).getCertiType();
	             				certiNo[i] = blPrpJplanFeePre.getArr(i).getCertiNo();
	             				serialNo[i] = blPrpJplanFeePre.getArr(i).getSerialNo();
	        		    	}
	        		    	mapList = createPolicyByServeltEx(certiType,certiNo,serialNo);
	        		    }else if("0".equals(dbPrpJpoaInfo.getAccFlag())){
	        				for(int i = 0; i < blPrpJplanFeePre.getSize(); i++){
	        					listMap = new HashMap();
	        					listMap.put("CertiNo",blPrpJplanFeePre.getArr(i).getCertiNo());
		        				listMap.put("PolicyNo",blPrpJplanFeePre.getArr(i).getPolicyNo());
		        				listMap.put("MessageCode","000001");
		        				listMap.put("MessageDesc",enterMap + "δ����");
		        				mapList.add(listMap);
		        				ErrorLoger.callErrorLogProcedure("queryAccStatusByEX", "000001" + "," + enterMap + "δ����");
	        				}
	        			}else if("3".equals(dbPrpJpoaInfo.getAccFlag())){
	        				for(int i = 0; i < blPrpJplanFeePre.getSize(); i++){
	        					listMap = new HashMap();
	        					listMap.put("CertiNo",blPrpJplanFeePre.getArr(i).getCertiNo());
		        				listMap.put("PolicyNo",blPrpJplanFeePre.getArr(i).getPolicyNo());
		        				listMap.put("MessageCode","000002");
		        				listMap.put("MessageDesc",enterMap + "���׸���");
		        				mapList.add(listMap);
		        				ErrorLoger.callErrorLogProcedure("queryAccStatusByEX", "000002" + "," + enterMap + "���׸���");
	        				}
	        			}
	        		}
         		}else if("1".equals(dbPrpJpoaInfo.getAccFlag())){
     				int j = 0;
     				int length = 0;
            		for(int i = 0; i < blPrpJplanFeePre.getSize(); i++){
         				if(!"".equals(blPrpJplanFeePre.getArr(i).getPolicyNo())){
         					listMap = new HashMap();
         					listMap.put("CertiNo",blPrpJplanFeePre.getArr(i).getCertiNo());
             				listMap.put("PolicyNo",blPrpJplanFeePre.getArr(i).getPolicyNo());
             				listMap.put("MessageCode","000000");
             				listMap.put("MessageDesc",enterMap + "�ɹ�����XX");
    	        			mapList.add(listMap);
    	        			ErrorLoger.callErrorLogProcedure("queryAccStatusByEX", "000000" + "," + enterMap + "�ɹ�����XX");
    	        			length++;
         				}
           		    }
            		
            		String[] certiType = new String[blPrpJplanFeePre.getSize()-length];
            		String[] certiNo = new String[blPrpJplanFeePre.getSize()-length];
            		String[] serialNo = new String[blPrpJplanFeePre.getSize()-length];
            		for(int i = 0; i < blPrpJplanFeePre.getSize(); i++){
         				if(blPrpJplanFeePre.getArr(i).getPolicyNo().equals("")){
         					certiType[j] = blPrpJplanFeePre.getArr(i).getCertiType();
         					certiNo[j] = blPrpJplanFeePre.getArr(i).getCertiNo();
         					serialNo[j] = blPrpJplanFeePre.getArr(i).getSerialNo();
         					j++;
         				}
            		}
            		
            		List policyList = createPolicyByServeltEx(certiType,certiNo,serialNo);
            		mapList.addAll(policyList);
         		}else if("3".equals(dbPrpJpoaInfo.getAccFlag())){
         			for(int i = 0; i < blPrpJplanFeePre.getSize(); i++){
    					listMap = new HashMap();
    					listMap.put("CertiNo",blPrpJplanFeePre.getArr(i).getCertiNo());
        				listMap.put("PolicyNo",blPrpJplanFeePre.getArr(i).getPolicyNo());
        				listMap.put("MessageCode","000002");
        				listMap.put("MessageDesc",enterMap + "���׸���");
        				mapList.add(listMap);
        				ErrorLoger.callErrorLogProcedure("queryAccStatusByEX", "000002" + "," + enterMap + "���׸���");
    				}
         		}
         		
         		return (ArrayList) mapList; 
         		
         	}else{
         		return (ArrayList) checkPreConfirmList;
         	}
         }else{
        	 listMap = new HashMap();
        	 listMap.put("MessageCode","999999");
        	 listMap.put("MessageDesc",omElement + " �гϰ���XXXXXƽ̨ͨ�����ߵ����ո���ѯXX״̬���Ϊ��");
			 mapList.add(listMap);
			 ErrorLoger.callErrorLogProcedure("queryAccStatusByEX", "999999" + "," + enterMap + "�гϰ���XXXXXƽ̨ͨ�����ߵ����ո���ѯXX״̬���Ϊ��");
			 return (ArrayList) mapList;
        }
    }
    /**
     * @desc ����XX����������
     * @author suxiaotong
     * @param iCertiType
     * @param iCertiNo
     * @param iSerialNo
     * @return ���ɵ�XX�ż��Ϻ�δ����XX��XX���ŵļ���
     * @throws Exception
     */
    public ArrayList createPolicyByServeltEx(String[] iCertiType,String[] iCertiNo,String[] iSerialNo)
    throws Exception{
    	ArrayList policyNoList = new ArrayList();
    	ArrayList errorProposalList = new ArrayList();
    	ArrayList allList = new ArrayList();
    	Map map = null;
    	
    	
    	
    	String BASE_URL = "";
    	String switchFlag =  SysConfig.getProperty("GENERATEPOLICY_URL_SWITCH");
    	String switchRiskCode = SysConfig.getProperty("GENERATEPOLICY_URL_RISKCODE");
    	String strRiskCodeFlag = "0";
    	if("0".equals(switchFlag)){
    		BASE_URL = SysConfig.getProperty("GENERATEPOLICY_URL");
    	}
    	
        InputStream inputStream = null;
        BufferedReader reader = null;
        OutputStream outputStream = null;
        OutputStreamWriter writer = null;
        String businessMsg = "";
        String strMessage = "";
        StringBuffer strBuffer = null;
        
        List iiCertiType = new ArrayList();
        List iiCertiNo = new ArrayList();
        List iiiCertiNo = new ArrayList();
        List iiiCertiType = new ArrayList();
        List iiSerialNo = new ArrayList();
        String strWhere ="proposalno in ('";
        BLPrpJplanFeePre blPrpJplanFeePre = new BLPrpJplanFeePre();
        DBPrpJtransPolicy dbPrpJtransPolicy = new DBPrpJtransPolicy();
        for(int i=0;i<iCertiNo.length;i++){
        	if(iCertiType[i].equals("T")){
        		blPrpJplanFeePre.getData(iCertiNo[i], iSerialNo[i]);
        		if("4".equals(blPrpJplanFeePre.getArr(0).getAttribute3())){
        			if(!iiCertiNo.contains(blPrpJplanFeePre.getArr(0).getContractNo())){
        				iiCertiType.add(iCertiType[i]);
        				iiCertiNo.add(blPrpJplanFeePre.getArr(0).getContractNo());
        			}else{
        				continue;
        			}
        		}else{
        			iiCertiType.add(iCertiType[i]);
            		iiCertiNo.add(iCertiNo[i]);
        		}
        	}else{
        		iiCertiType.add(iCertiType[i]);
        		iiCertiNo.add(iCertiNo[i]);
        	}
        }
        
        boolean isflag = false;
        for(int j=0;j<iiCertiNo.size();j++){
        	String flag = (String)iiCertiNo.get(j);
        	if("T".equals(flag.substring(0,1))){
        	  isflag = true;       	  
        	  if(j>0&&j%900 == 0)
        		 strWhere += (String)iiCertiNo.get(j) +"') or proposalno in ('";
			  else if (j!=iiCertiNo.size()-1)
				 strWhere += (String)iiCertiNo.get(j)+"','";
        	}else{
        		 iiiCertiNo.add(iiCertiNo.get(j));
        		 iiiCertiType.add(iiCertiType.get(j));
        	}
        	if(j==iiCertiNo.size()-1)
       		  strWhere += (String)iiCertiNo.get(j)+"')";
        }
        if(isflag){
          strWhere += " order by riskcode ";
          BLPrpTmain blPrpTmain = new BLPrpTmain();
          blPrpTmain.query(strWhere,0);
          for(int k=0;k<blPrpTmain.getSize();k++){
        	  iiiCertiNo.add(blPrpTmain.getArr(k).getProposalNo());
        	  iiiCertiType.add("T");
          }
        }
        
        
        String iWherePart = "";
        BLPrpJplanFeePre blPre = new BLPrpJplanFeePre();
        for(int j=0;j<iiiCertiNo.size();j++){
        	
        	iWherePart = "certino = '" + iiiCertiNo.get(j)+"'";
        	blPre.query(iWherePart,0);
        	if(blPre.getSize()>0 && (switchRiskCode.indexOf(blPre.getArr(0).getRiskCode())>0)){
        		
        		strRiskCodeFlag = "1";
        		break;
        	}
        }
        if("1".equals(switchFlag)){
        	if("1".equals(strRiskCodeFlag)){
        		BASE_URL = SysConfig.getProperty("GENERATEPOLICY_URL_BAK");
        	}else{
        		BASE_URL = SysConfig.getProperty("GENERATEPOLICY_URL");
        	}
        }
        
        
        DbPool dbpool = new DbPool();
        dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
    	for(int i=0;i<iiiCertiNo.size();i++){
    	    dbpool.beginTransaction();
    	    int intResult = dbPrpJtransPolicy.getInfo(iiiCertiType.get(i).toString() ,iiiCertiNo.get(i).toString());
    	    DateTime dateTime = new DateTime(new Date(),DateTime.YEAR_TO_SECOND);
    	    String currentDate = dateTime.toString();
    		try{
    			
    			URL uploadServlet = new URL(BASE_URL);
    			HttpURLConnection servletConnection = (HttpURLConnection)uploadServlet.openConnection();
    			
    			servletConnection.setUseCaches(false);
    			servletConnection.setDoOutput(true);
    			servletConnection.setDoInput(true);
    			servletConnection.setRequestMethod("POST");
    			servletConnection.setAllowUserInteraction(true);
    			servletConnection.connect();
    			
    			businessMsg = iiiCertiType.get(i) + "," +iiiCertiNo.get(i);
                outputStream = servletConnection.getOutputStream();
                writer = new OutputStreamWriter(outputStream);
                writer.write(businessMsg);
                writer.flush();
                writer.close();
    			
    			inputStream = servletConnection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(inputStream));
                strBuffer = new StringBuffer();
                while ((strMessage = reader.readLine()) != null) {
                	strBuffer.append(strMessage);
                }
                try {
                    if (strBuffer.length() < 1){

                        map = new HashMap();
                    	map.put("CertiNo",iiiCertiNo.get(i));
                		map.put("PolicyNo","");
                		map.put("MessageCode","000005");
                		map.put("MessageDesc","�ѵ��˵�����XXʧ��");
                		allList.add(map);
           			    ErrorLoger.callErrorLogProcedure("queryAccStatusByEX", "000005" + "," + allList + "�ѵ��˵�����XXʧ��");
                		
                        if(intResult == 100){
                            if(iiiCertiType.get(i).toString().equals("E")){
                                dbPrpJtransPolicy.setPolicyNo(iiiCertiNo.get(i).toString().substring(0, iiiCertiNo.get(i).toString().length()-4));  
                            }
                            dbPrpJtransPolicy.setTransPolicyStatus("0");
                            dbPrpJtransPolicy.setCertiNo(iiiCertiNo.get(i).toString());
                            dbPrpJtransPolicy.setCertiType(iiiCertiType.get(i).toString());
                            dbPrpJtransPolicy.setCreateDateTime(currentDate);
                            dbPrpJtransPolicy.setUpdateErrorReason("ʧ��");
                            dbPrpJtransPolicy.insert(dbpool);
                        }else{
                            dbPrpJtransPolicy.setExecuteDateTime(currentDate);
                            dbPrpJtransPolicy.setUpdateErrorReason("ʧ��");
                            dbPrpJtransPolicy.update(dbpool);
                        }
                    }else{
                        if(intResult == 0){
                            if(iiiCertiType.get(i).toString().equals("T")){
                                dbPrpJtransPolicy.setPolicyNo(strBuffer.toString());
                            }else if(iiiCertiType.get(i).toString().equals("E")){
                                dbPrpJtransPolicy.setPolicyNo(strBuffer.toString().substring(0, strBuffer.toString().length()-4));
                            }
                            dbPrpJtransPolicy.setTransPolicyStatus("1");
                            dbPrpJtransPolicy.setExecuteDateTime(currentDate);
                            dbPrpJtransPolicy.setUpdateErrorReason("�ɹ�");
                        }

                        map = new HashMap();
                        map.put("CertiNo",iiiCertiNo.get(i));
                		map.put("PolicyNo","");
                		map.put("MessageCode","000000");
                		map.put("MessageDesc","�ɹ�����XX");
                		allList.add(map);
           			    ErrorLoger.callErrorLogProcedure("queryAccStatusByEX", "000000" + "," + allList + "�ɹ�����XX");
                		
                        this.logPrintln("����XXXXX/�����ɹ���ҵ��ţ�"+strBuffer.toString());
                    } 
                    dbpool.commitTransaction(); 
                } catch (Exception e) {
                    dbpool.rollbackTransaction();
                    e.printStackTrace();
                }
        	}catch (MalformedURLException me) {
        		this.logPrintln("�����Ƿ�URL������ϵͳ����Ա��ϵ��");

        		map = new HashMap();
        		map.put("CertiNo",iiiCertiNo.get(i));
        		map.put("PolicyNo","");
        		map.put("MessageCode","000005");
        		map.put("MessageDesc","�ѵ��˵�����XXʧ��");
        		allList.add(map);
   			    ErrorLoger.callErrorLogProcedure("queryAccStatusByEX", "000005" + "," + allList + "�ѵ��˵�����XXʧ��");
    			me.printStackTrace();
    		} catch (Exception e) {
    			map = new HashMap();
                map.put("CertiNo",iiiCertiNo.get(i));
        		map.put("PolicyNo","");
        		map.put("MessageCode","000005");
        		map.put("MessageDesc","�ѵ��˵�����XXʧ��");
        		allList.add(map);
   			    ErrorLoger.callErrorLogProcedure("queryAccStatusByEX", "000005" + "," + allList + "�ѵ��˵�����XXʧ��");
                
                if(intResult == 100){
                    if(iiiCertiType.get(i).toString().equals("E")){
                        dbPrpJtransPolicy.setPolicyNo(iiiCertiNo.get(i).toString().substring(0, iiiCertiNo.get(i).toString().length()-4));  
                    }
                    dbPrpJtransPolicy.setCertiNo(iiiCertiNo.get(i).toString());
                    dbPrpJtransPolicy.setCertiType(iiiCertiType.get(i).toString());
                    dbPrpJtransPolicy.setCreateDateTime(currentDate);
                    dbPrpJtransPolicy.setUpdateErrorReason("servlet����ʧ��");
                    dbPrpJtransPolicy.setTransPolicyStatus("0");
                    dbPrpJtransPolicy.insert(dbpool);
                }else{
                    dbPrpJtransPolicy.setExecuteDateTime(currentDate);
                    dbPrpJtransPolicy.setUpdateErrorReason("servlet����ʧ��");
                    dbPrpJtransPolicy.update(dbpool);
                }
                dbpool.commitTransaction(); 
                
        		this.logPrintln("XX/����"+iiiCertiNo.get(i)+"���ݷ��ͣ�û�з������ɵ�XX��Ϣ��");
    			e.printStackTrace();
    		}
    	}
    	dbpool.close();
    	return allList;
    }  
    /**
	 * �гϰ���XXXXXƽ̨ͨ�����ߵ����ո���ѯXX״̬У��
	 * @param String �ܽ��
	 * @param String ƽ̨������
	 * @throws Exception 
	 * @throws �쳣��
	 */
	public List checkPreConfirmEX(DbPool dbpool,Map enterMap,String poaCode,String sumFee) throws Exception {
	    
		String messageCode = "000000";
        String messageDesc = "";
        Map ListMap = null;
        List mapList = new ArrayList();
        BLPrpJplanFeePre blPrpJplanFeePre = new BLPrpJplanFeePre();
        DBPrpJpoaInfo dbPrpJpoaInfo = new DBPrpJpoaInfo();
        
        if(poaCode == null || poaCode.equals("")){
        	ListMap = new HashMap();
    		ListMap.put("MessageCode","999999");
			ListMap.put("MessageDesc",enterMap + " �гϰ���XXXXXƽ̨ͨ�����ߵ����ո���ѯXX״̬���poaCodeΪ��");
			mapList.add(ListMap);
			ErrorLoger.callErrorLogProcedure("queryAccStatusByEX", "999999" + "," + enterMap + "�гϰ���XXXXXƽ̨ͨ�����ߵ����ո���ѯXX״̬���poaCodeΪ��");
			return mapList;
        }
		if(sumFee == null || sumFee.equals("")){
			ListMap = new HashMap();
    		ListMap.put("MessageCode","999999");
			ListMap.put("MessageDesc",enterMap + " �гϰ���XXXXXƽ̨ͨ�����ߵ����ո���ѯXX״̬���sumFeeΪ��");
			mapList.add(ListMap);
			ErrorLoger.callErrorLogProcedure("queryAccStatusByEX", "999999" + "," + enterMap + "�гϰ���XXXXXƽ̨ͨ�����ߵ����ո���ѯXX״̬���sumFeeΪ��");
			return mapList;
        }
		
		try {
			
			BigDecimal BigDecimalSumFee = new BigDecimal(sumFee).setScale(2, BigDecimal.ROUND_HALF_UP);
		} catch (Exception ex) {
			ListMap = new HashMap();
    		ListMap.put("MessageCode","999999");
			ListMap.put("MessageDesc",enterMap + " �гϰ���XXXXXƽ̨ͨ�����ߵ����ո���ѯXX״̬���sumFee��ʽ����ȷ");
			mapList.add(ListMap);
			ErrorLoger.callErrorLogProcedure("queryAccStatusByEX", "999999" + "," + enterMap + "�гϰ���XXXXXƽ̨ͨ�����ߵ����ո���ѯXX״̬���sumFee��ʽ����ȷ");
			return mapList;
		}
		
        
        int countPoa = dbPrpJpoaInfo.getInfo(poaCode);
    	if(countPoa == 100){
    		ListMap = new HashMap();
    		ListMap.put("MessageCode","000003");
			ListMap.put("MessageDesc",enterMap + "û���ҵ�PrpJpoaInfo������Ϣ!V_PAYID:'" + poaCode);
			mapList.add(ListMap);
			ErrorLoger.callErrorLogProcedure("queryAccStatusByEX", "000003" + "," + enterMap + "û���ҵ�PrpJpoaInfo������Ϣ!V_PAYID:'" + poaCode);
			return mapList;
    	}
    	
    	String conditionPre = " poacode ='" + poaCode + "' ";
    	blPrpJplanFeePre.query(dbpool, conditionPre,0);
    	if(blPrpJplanFeePre.getSize() == 0){
    		ListMap = new HashMap();
    		ListMap.put("MessageCode","000003");
			ListMap.put("MessageDesc",enterMap + "û���ҵ�PrpJplanFeePre������Ϣ!V_PAYID:'" + poaCode);
			ErrorLoger.callErrorLogProcedure("queryAccStatusByEX", "000003" + "," + enterMap + "û���ҵ�PrpJplanFeePre������Ϣ!V_PAYID:'" + poaCode);
			return mapList;
    	}
		
    	BigDecimal totalFeeBig = new BigDecimal(dbPrpJpoaInfo.getTotalFee()).setScale(2, BigDecimal.ROUND_HALF_UP);
    	BigDecimal sumFeeBig = new BigDecimal(sumFee).setScale(2, BigDecimal.ROUND_HALF_UP);
		int compareValue = totalFeeBig.compareTo(sumFeeBig);
    	if(compareValue != 0){
			String certiNoPre = "";
			String policyNoPre = "";
			for(int i = 0; i < blPrpJplanFeePre.getSize(); i++){
				ListMap = new HashMap();
				ListMap.put("CertiNo",blPrpJplanFeePre.getArr(i).getCertiNo());
				ListMap.put("PolicyNo",blPrpJplanFeePre.getArr(i).getPolicyNo());
				ListMap.put("MessageCode","000004");
				ListMap.put("MessageDesc",enterMap + "PrpJpoaInfo.totalfee�����ڴ����ܽ��sumFee! PrpJpoaInfo.totalfee:'"
										+ dbPrpJpoaInfo.getTotalFee()
										+ ",sumFee:" + sumFee);
				mapList.add(ListMap);
				ErrorLoger.callErrorLogProcedure("queryAccStatusByEX", "000004" + "," + enterMap + "PrpJpoaInfo.totalfee�����ڴ����ܽ��sumFee! PrpJpoaInfo.totalfee:'"
						+ dbPrpJpoaInfo.getTotalFee()
						+ ",sumFee:" + sumFee);
			}
			return mapList;
		}
		
		ListMap = new HashMap();
		ListMap.put("MessageCode",messageCode);
		ListMap.put("MessageDesc",messageDesc);
		mapList.add(ListMap);
		return mapList;
	}
	/**
	 * ��ƽ̨������ѯXX״̬������dbpool��
	 * @param blPrpJpoaInfo ������Ϣ
	 * @return Map ������Ϣ
	 */
    public Map sendPolicyAccQueryRequestEX(BLPrpJpoaInfo blPrpJpoaInfo)throws Exception{
		String messageCode = "000000";
		String messageDesc = "";
		
    	BLPrpJpreFeeCharge blPrpJpreFeeCharge = new BLPrpJpreFeeCharge();
		PolicyAccQueryEncoder policyAccQueryEncoder = new PolicyAccQueryEncoder();
		PolicyAccQueryDecoder policyAccQueryDecoder = new PolicyAccQueryDecoder();
		String context = "";
		String response = "";
		
		DbPool dbpool = new DbPool();
        
        
		
		String payment_switch = SysConfig.getProperty("PAYMENT_SWITCH_prpall");
		if("0".equals(payment_switch)){
			dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
		}else{
			dbpool.open(SysConfig.CONST_PAYMENTNEWDATASOURCE);
		}
		
        
		try {
			context = policyAccQueryEncoder.encode(blPrpJpoaInfo);
		} catch (Exception ex) {
			String poaCode = blPrpJpoaInfo.getArr(0).getPoaCode();
			messageCode = "999999";
			messageDesc = "����ƽ̨��ѯXX״̬����ӿڴ�ʧ�ܣ�" + poaCode + "," + ex.getMessage();
			ex.printStackTrace();
			ErrorLoger.callErrorLogProcedure("sendPolicyAccQueryRequestDX", messageCode + "," + messageDesc);
			return this.setStatusMap(messageCode, messageDesc);
		}
		
		try {		
			dbpool.beginTransaction();
			
			logger.info("context" + context);
			
			response = EbaoProxy.getInstance().request(context, blPrpJpoaInfo.getArr(0).getComCode());
			response = StringUtils.replace(response, "GBK", "GB2312");
			
			logger.info("response" + response);
			
			policyAccQueryDecoder.decode(dbpool, blPrpJpoaInfo, response);
			blPrpJpreFeeCharge.convertFlagForSH(dbpool, blPrpJpoaInfo);
			dbpool.commitTransaction();
		} catch (UserException ex) {
			String poaCode = blPrpJpoaInfo.getArr(0).getPoaCode();
			messageCode = "999999";
			messageDesc = "����ƽ̨��ѯXX״̬����ӿڴ�ʧ�ܣ�" + poaCode + "," + ex.getErrorMessage();
			ex.printStackTrace();
			ErrorLoger.callErrorLogProcedure("sendPolicyAccQueryRequestDX",messageCode + "," +  messageDesc);
			dbpool.rollbackTransaction(); 
			return this.setStatusMap(messageCode, messageDesc);
		} catch (Exception ex) {
			String poaCode = blPrpJpoaInfo.getArr(0).getPoaCode();
			messageCode = "999999";
			messageDesc = "����ƽ̨���ز�ѯ״̬����ӿڴ�ʧ�ܣ�" + poaCode + "," + ex.getMessage();
			ex.printStackTrace();
			ErrorLoger.callErrorLogProcedure("sendPolicyAccQueryRequestDX", messageCode + "," + messageDesc);
			dbpool.rollbackTransaction(); 
			return this.setStatusMap(messageCode, messageDesc);
		}
		finally{
            dbpool.close();
        }
		return this.setStatusMap(messageCode, messageDesc);
	} 
    /**
	 * ��list����ΪOMElement��ʽ
	 */
    public static OMElement createOMElementFromList(List list,
			String serviceName, String requestMethod) {
		
		OMFactory fac = OMAbstractFactory.getOMFactory();
		
		OMNamespace omNs = fac.createOMNamespace(
				" http:
		
		OMElement resp = fac.createOMElement(requestMethod, omNs);
		
		
		for(int i = 0; i < list.size(); i++){
			Map map = (HashMap)list.get(i);
			Set set = map.entrySet();
			Iterator iterator = set.iterator();
			OMElement omElementRoot = fac.createOMElement("Map", omNs);			
			while (iterator.hasNext()) {
				Map.Entry mapentry = (Map.Entry) iterator.next();
				OMElement omElement = fac.createOMElement(mapentry.getKey()
						.toString(), omNs);
				omElement.addChild(fac.createOMText(omElement, ""
						+ mapentry.getValue()));
				omElementRoot.addChild(omElement);
			}
			resp.addChild(omElementRoot);
		}
		return resp;
	}
    /**
	 * ��OMElement����Ϊmap(����map�а���list)��ʽ
	 */
    public static Map createMapOfListFromElement(OMElement omElement){
		
		Map map = new HashMap();
		
		Iterator iterator = omElement.getChildElements();
		
		String value = "";
		List certiNoList = new ArrayList();
		
		while (iterator.hasNext())
		{
			
			OMElement ome = (OMElement) iterator.next();
			
			try {
				if ("record".equals(ome.getLocalName()))
				{
					certiNoList.add(ome.getText());
				}else{
					value = ome.getText();
					map.put(ome.getLocalName(), value);
				}
			} catch (Exception e) {
				e.printStackTrace();
				value = "";
			}
			
		}
		
		map.put("certiNoList", certiNoList);
        return map;
	}
	
    
	/**
	 * ����DbPool��ȡConnection
	 * @param dbpool���ݿ����ӳ�
	 * @return Connection ���ݿ�����
	 * @throws SQLException ���ݿ�����쳣��
	 */
	private Connection getConnection(DbPool dbpool) {
		Connection connection = null;
		try {
			
			DBManager dbManager = dbpool.getDBManager(SysConfig.CONST_PAYMENTDATASOURCE);
			
			connection = dbManager.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}	

	public static Map createMapFromElement(OMElement omElement){
		
		Map map = new HashMap();
		
		Iterator iterator = omElement.getChildElements();
		
		String value = "";
		
		while (iterator.hasNext())
		{
			
			OMElement ome = (OMElement) iterator.next();
			
			try {
				value = ome.getText();
			} catch (Exception e) {
				e.printStackTrace();
				value = "";
			}			
			map.put(ome.getLocalName(), value);
		}
        return map;
	}
	
	public static OMElement createOMElementFromMap(Map map,
			String serviceName, String requestMethod) {
		
		OMFactory fac = OMAbstractFactory.getOMFactory();
		
		OMNamespace omNs = fac.createOMNamespace(
				"http:
		
		OMElement resp = fac.createOMElement(requestMethod, omNs);
		
		
		Set set = map.entrySet();
		Iterator iterator = set.iterator();
		while (iterator.hasNext()) {
			Map.Entry mapentry = (Map.Entry) iterator.next();
			OMElement omElement = fac.createOMElement(mapentry.getKey()
					.toString(), omNs);
			omElement.addChild(fac.createOMText(omElement, ""
					+ mapentry.getValue()));
			resp.addChild(omElement);
		}
		return resp;
	}	
	
    /**
     * @desc ����map messageCode��messageDesc
     * @author pengjinling
     * @param iLog
     * @throws Exception
     */
    public PrpJpoaInfoSchema setPoaInfoScema(BLPrpJpoaInfo blPrpJpoaInfo,String payMethod,String strSumPremium,String accBankAccounts,String strPoaType) throws Exception {
    	DateTime currentDateTimeDetail = new DateTime(new Date(),DateTime.YEAR_TO_SECOND);
		PrpJpoaInfoSchema prpJpoaInfoSehema = new PrpJpoaInfoSchema();
		BLPrpJplanFeePre blPrpJplanFeePre = blPrpJpoaInfo.getBLPrpJplanFeePre();
		prpJpoaInfoSehema.setPoaType(strPoaType);
		prpJpoaInfoSehema.setAccBillAmount(strSumPremium);
		prpJpoaInfoSehema.setAccBillNo("");
		prpJpoaInfoSehema.setAccBillProvider("");
		prpJpoaInfoSehema.setPayMethod(payMethod);
		prpJpoaInfoSehema.setAccFlag("0");
		prpJpoaInfoSehema.setOperatorCode("00000000");
		prpJpoaInfoSehema.setComCode(blPrpJplanFeePre.getArr(0).getComCode());
		prpJpoaInfoSehema.setMakeCom(blPrpJplanFeePre.getArr(0).getMakeCom());
		prpJpoaInfoSehema.setAgentCode(blPrpJplanFeePre.getArr(0).getAgentCode());
		prpJpoaInfoSehema.setCenterCode(blPrpJplanFeePre.getArr(0).getCenterCode());
		prpJpoaInfoSehema.setTotalFee(strSumPremium);
		prpJpoaInfoSehema.setOpenDate(currentDateTimeDetail.toString());
		prpJpoaInfoSehema.setBusinessNo(blPrpJplanFeePre.getArr(0).getCertiNo());
		prpJpoaInfoSehema.setValidDate(blPrpJplanFeePre.getArr(0).getValidDate());	
		prpJpoaInfoSehema.setAccBillBank("");
		prpJpoaInfoSehema.setCustomNo(accBankAccounts);
		return prpJpoaInfoSehema;
    }
	
    /**
     * @desc ����map messageCode��messageDesc
     * @author pengjinling
     * @param iLog
     * @throws Exception
     */
    /* MODIFY BY PENGJINLING 2013-8-8 �Ϻ�XXXXX��������������֧�� BEGIN */
    public Map setPoaCodeMap(String strPayId,String strSumPremium,String strPaymentStartDate,String strpaymentEndDate,String strCertiNos,
    		String messageCode,String messageDesc) throws Exception {
    /* MODIFY BY PENGJINLING 2013-8-8 �Ϻ�XXXXX��������������֧�� END */ 
    	Map map = new HashMap();
    	map.put("PayId", strPayId);
    	map.put("SumPremium",strSumPremium);
    	map.put("PaymentStartDate",strPaymentStartDate);
    	map.put("PaymentEndDate",strpaymentEndDate);
    	
    	map.put("CertiNos",strCertiNos);
    	map.put("MessageCode",messageCode);
    	map.put("MessageDesc",messageDesc);
    	return map;
    }
    
    /**
     * @desc �����Ϻ��������ز���map messageCode��messageDesc
     * @author suxiaotong
     * @param iLog
     * @throws Exception
     */
    public Map setPoaCodeMNMap(String strPayId, String strSumPremium,
			String strPaymentStartDate, String strpaymentEndDate,String acntUnit, String phoneNumber, String strCertiNos, String remark,
			String messageCode, String messageDesc) throws Exception {
    	Map map = new HashMap();
    	map.put("PayId", strPayId);
    	map.put("SumPremium",strSumPremium);
    	map.put("PaymentStartDate",strPaymentStartDate);
    	map.put("PaymentEndDate",strpaymentEndDate);
    	map.put("AcntUnit",acntUnit);
    	map.put("PhoneNumber",phoneNumber);
    	map.put("CertiNo",strCertiNos);
    	map.put("Remark",remark);
    	map.put("MessageCode",messageCode);
    	map.put("MessageDesc",messageDesc);
    	return map;
    }
    /**
     * @desc �����гϰ��ŷ��ز���map messageCode��messageDesc
     * @author suxiaotong
     * @param iLog
     * @throws Exception
     */
    public Map setPoaCodeZCMap(String strPayId, String strSumPremium,
			String strPaymentStartDate, String strpaymentEndDate,
			String messageCode, String messageDesc) throws Exception {
    	Map map = new HashMap();
    	map.put("PayId", strPayId);
    	map.put("SumPremium",strSumPremium);
    	map.put("PaymentStartDate",strPaymentStartDate);
    	map.put("PaymentEndDate",strpaymentEndDate);
    	map.put("MessageCode",messageCode);
    	map.put("MessageDesc",messageDesc);
    	return map;
    }
    
    /**
     * @desc ����map messageCode��messageDesc
     * @author pengjinling
     * @param iLog
     * @throws Exception
     */
    public Map setStatusMap(String messageCode,String messageDesc) throws Exception {
    	Map map = new HashMap();
    	map.put("MessageCode",messageCode);
    	map.put("MessageDesc",messageDesc);
    	return map;
    }
    
    /**
     * @desc ��־���
     * @author pengjinling
     * @param iLog
     * @throws Exception
     */
    public void logPrintln(String iLog) throws Exception {
        ChgDate chgDate = new ChgDate();
        logger.info(chgDate.getCurrentTime("yyyy-MM-dd hh:mm:ss") + "><" + iLog);
    }
}
