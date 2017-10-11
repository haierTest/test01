package com.sp.indiv.ci.interf;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.sp.indiv.ci.schema.CIInsureDemandLossSchema;
import com.sp.indiv.ci.schema.CIInsureDemandPaySchema;
import com.sp.indiv.ci.schema.CIInsureDemandSchema;
import com.sp.prpall.blsvr.jf.BLPrpJpoaInfo;
import com.sp.prpall.blsvr.tb.BLProposal;
import com.sp.prpall.dbsvr.jf.DBPrpJplanFeePre;
import com.sp.prpall.dbsvr.jf.DBPrpJpoaInfo;
import com.sp.prpall.schema.PrpJplanFeePreSchema;
import com.sp.prpall.schema.PrpJpoaInfoSchema;
import com.sp.sysframework.common.datatype.DateTime;
import com.sp.sysframework.common.util.XMLUtils;
import com.sp.sysframework.reference.AppConfig;
import com.sp.utility.UtiPower;
import com.sp.utility.database.DbPool;
import com.sp.utility.string.ChgDate;

/**
 * 发送到账查询请求数据的解码器
 * 
 */
public class PolicyAccQueryDecoder {

    public static void main(String[] args) 
    	throws Exception 
    {
    }
    
    String errorMessage = "";

    /**
     * 解码
     * @return 到账查询请求XML格式串
     * @throws Exception
     */
	public void decode(DbPool dbPool, BLPrpJpoaInfo blPrpJpoaInfo,
			String content) throws Exception 
    {
    	InputStream in = new ByteArrayInputStream(content.getBytes());
        Document document = XMLUtils.parse(in);
        int type = -1;
		try
		{
			type = processHead(blPrpJpoaInfo, XMLUtils.getChildNodeByPath(document, "/PACKET/HEAD"));
			if(type == 1)
			{
				processBody(blPrpJpoaInfo, XMLUtils.getChildNodeByPath(document, "/PACKET/BODY"));
				blPrpJpoaInfo.getArr(0).setAccFlag("1");
			} 
		}
		catch(Exception e)
		{
			throw new Exception("解析平台返回串错误。平台交易号：" + blPrpJpoaInfo.getArr(0).getPoaCode()	
		             + "，请与管理员联系！" + e.getMessage());
		}
		
		if(type != 1)
		{
			throw new Exception(blPrpJpoaInfo.getArr(0).getErrorMessage());
		}
		saveData(dbPool, blPrpJpoaInfo);
    }
	
    /**
     * XXXXX存数据
     * @param blPrpJpoaInfo
     * @throws Exception
     */
    protected void saveData(DbPool dbPool, BLPrpJpoaInfo blPrpJpoaInfo) 
    	throws Exception 
    {
    	if(blPrpJpoaInfo.getBLPrpJplanFeePre().getArr(0).getAttribute1().equals("1")){
            DateTime currentDate = new DateTime(DateTime.current(),DateTime.YEAR_TO_SECOND);
        	blPrpJpoaInfo.getArr(0).setAccDate(currentDate.toString());
    		blPrpJpoaInfo.getArr(0).setAccFlag("1");
    	}else if(blPrpJpoaInfo.getBLPrpJplanFeePre().getArr(0).getAttribute1().equals("2")){
    		blPrpJpoaInfo.getArr(0).setAccFlag("3");
    	}else if(blPrpJpoaInfo.getBLPrpJplanFeePre().getArr(0).getAttribute1().equals("0")){
    		blPrpJpoaInfo.getArr(0).setAccFlag("0");
    	}
		DBPrpJpoaInfo dbPrpJpoaInfo = new DBPrpJpoaInfo();
		dbPrpJpoaInfo.setSchema(blPrpJpoaInfo.getArr(0));
		dbPrpJpoaInfo.update(dbPool);
		for(int i=0;i<blPrpJpoaInfo.getBLPrpJplanFeePre().getSize();i++){
    		DBPrpJplanFeePre dbPrpJplanFeePre = new DBPrpJplanFeePre();
    		dbPrpJplanFeePre.setSchema(blPrpJpoaInfo.getBLPrpJplanFeePre().getArr(i));
    		dbPrpJplanFeePre.update(dbPool);
    	}
	}
    

    /**
     * 处理HEAD节
     * @param node Node
     * @throws Exception
     */
    protected int processHead(BLPrpJpoaInfo blPrpJpoaInfo,Node node) throws Exception {
    	int type = 1;
        String responseCode = XMLUtils.getChildNodeValue(node, "RESPONSE_CODE");
        if (!responseCode.equals("1")) {
        	errorMessage = XMLUtils.getChildNodeValue(node, "ERROR_MESSAGE");
        	blPrpJpoaInfo.getArr(0).setErrorMessage(errorMessage);
        	type = 0;
        }
        return type ;
    }

    /**
     * 处理BODY节
     * @param node Node
     * @throws Exception
     */
    protected void processBody(BLPrpJpoaInfo blPrpJpoaInfo,Node node) 
    	throws Exception 
    {
        processBasePart(blPrpJpoaInfo, XMLUtils.getChildNodeByTagName(node, "BASE_PART"));
    }

    /**
     * 处理BASE_PART节
     * @param node Node
     * @throws Exception
     */
    protected void processBasePart(BLPrpJpoaInfo blPrpJpoaInfo, Node node)
			throws Exception {
		String strRequestType = XMLUtils.getChildNodeValue(node, "REQUEST_TYPE");
		String strBookingCode = XMLUtils.getChildNodeValue(node, "BOOKING_CODE");
		String strPayMethod = XMLUtils.getChildNodeValue(node, "PAY_METHOD");
		String strSumPremium = XMLUtils.getChildNodeValue(node, "SUM_PREMIUM");
		String strCircPaymentNo = XMLUtils.getChildNodeValue(node, "CIRC_PAYMENT_NO");
		String strCircPaymentTime = XMLUtils.getChildNodeValue(node, "CIRC_PAYMENT_TIME");
		String strPayment = XMLUtils.getChildNodeValue(node, "PAYMENT");
		String strBankPaymentNo = XMLUtils.getChildNodeValue(node, "BANK_PAYMENT_NO");
		String strBankPaymentTime = XMLUtils.getChildNodeValue(node, "BANK_PAYMENT_TIME");
		String strPosNo = XMLUtils.getChildNodeValue(node, "POS_NO");
		blPrpJpoaInfo.getArr(0).setAccDate(strBankPaymentTime);
		blPrpJpoaInfo.getArr(0).setPosNo(strPosNo);
		
		Node[] nodes = XMLUtils.getChildNodesByTagName(node, "ACCOUNT_LIST");
		
		for (int i = 0; i < nodes.length; i++) {
			processAccountList(blPrpJpoaInfo,nodes[i]);
		}
	}
    /**
     * 处理ACCOUNT_LIST节
     * @param node Node
     * @throws Exception
     */
    protected void processAccountList(BLPrpJpoaInfo blPrpJpoaInfo,Node node)throws Exception{
    	String strRequestType = XMLUtils.getChildNodeValue(node, "REQUEST_TYPE");
    	String strPaymentStatus = XMLUtils.getChildNodeValue(node, "PAYMENT_STATUS");
    	String strCarMark = XMLUtils.getChildNodeValue(node, "CAR_MARK");
    	String strVehicleType = XMLUtils.getChildNodeValue(node, "VEHICLE_TYPE");
    	String strRackNo = XMLUtils.getChildNodeValue(node, "RACK_NO");
    	String strPolicyNO = XMLUtils.getChildNodeValue(node, "POLICY_NO");
    	String strProConFirmSequenceNo = XMLUtils.getChildNodeValue(node, "PROCONFIRM_SEQUENCE_NO");
    	String strComfirmSequenceNo = XMLUtils.getChildNodeValue(node, "CONFIRM_SEQUENCE_NO");
    	String strAmendQueryNo = XMLUtils.getChildNodeValue(node, "AMEND_QUERY_NO");
    	String strPayMethod = XMLUtils.getChildNodeValue(node, "PAY_METHOD");
    	String strSumPremium = XMLUtils.getChildNodeValue(node, "SUM_PREMIUM");
    	String strCircPaymentNo = XMLUtils.getChildNodeValue(node, "CIRC_PAYMENT_NO");
    	String strCircPaymentTime = XMLUtils.getChildNodeValue(node, "CIRC_PAYMENT_TIME");
    	String strPayment = XMLUtils.getChildNodeValue(node, "PAYMENT");
    	String strBankPaymentNo = XMLUtils.getChildNodeValue(node, "BANK_PAYMENT_NO");
    	String StrBankPaymentTime = XMLUtils.getChildNodeValue(node, "BANK_PAYMENT_TIME");
    	String strPosNo = XMLUtils.getChildNodeValue(node, "POS_NO");
    	
    	if(blPrpJpoaInfo.getBLPrpJplanFeePre().getArr(0).getCertiType().equals("E")){
    		strProConFirmSequenceNo = strAmendQueryNo;
    	}
    	for (int i = 0; i < blPrpJpoaInfo.getBLPrpJplanFeePre().getSize(); i++) {
			if (blPrpJpoaInfo.getBLPrpJplanFeePre().getArr(i).getProConfirmSequenceNo().equals(strProConFirmSequenceNo)) {
				blPrpJpoaInfo.getBLPrpJplanFeePre().getArr(i).setAttribute1(strPaymentStatus);
				if(strPaymentStatus.equals("0")){
					blPrpJpoaInfo.getBLPrpJplanFeePre().getArr(i).setCertiStatus("1");
				}else if(strPaymentStatus.equals("1")){
					blPrpJpoaInfo.getBLPrpJplanFeePre().getArr(i).setCertiStatus("2");
				}else if(strPaymentStatus.equals("2")){
					blPrpJpoaInfo.getBLPrpJplanFeePre().getArr(i).setCertiStatus("0");
				}
			}
		}
		
    }
   
    
    /**
     * 纠正日期格式
     * @param dateString 8个字符的日期
     * @return YYYY-MM-DD形式的日期
     */
    private String correctDate(String dateString) {
    	if (dateString == null || dateString.equals("")){
    		return "";
    	}
        if (dateString.length() < 8) {
            throw new IllegalArgumentException(dateString + "的日期格式不对，必须为大于8位的纯数字的字符串");
        }
        String result = dateString.substring(0, 4) + "-" + dateString.substring(4, 6) + "-"
                + dateString.substring(6, 8);
        return result;
    }

    /**
     * 纠正日期时间格式
     * @param dateString 修正前的日期时间
     * @return 修正后的日期时间
     */
    private String correctDateTime(String dateString) {
        String result = correctDate(dateString);
        if (dateString.length() >= 10) {
            result += " " + dateString.substring(8, 10);
        }
        if (dateString.length() >= 12) {
            result += ":" + dateString.substring(10, 12);
        }
        if (dateString.length() >= 14) {
            result += ":" + dateString.substring(12, 14);
        }
        return result;
    }
}
