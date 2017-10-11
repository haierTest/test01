package com.sp.indiv.ci.interf;

import com.sp.prpall.blsvr.jf.BLPrpJpoaInfo;
import com.sp.sysframework.common.util.StringUtils;
import com.sp.sysframework.reference.AppConfig;


/**
 * 发送到账确认请求数据的编码器
 *
 */
public class PolicyAccConfirmEncoder {

    /**
	 * 
	 * @param blPrpJpoaInfo BLPrpJpoaInfo
	 * @return 到账确认请求XML格式串
	 * @throws Exception
	 */
    public String encode(BLPrpJpoaInfo blPrpJpoaInfo) 
    	throws Exception 
    {
    	
        StringBuffer buf = new StringBuffer(4000);
        addXMLHead(buf);
        addPacket(buf, blPrpJpoaInfo);
        return buf.toString();
    }

    /**
	 * 添加XML文件头
	 *
	 * @param buf StringBuffer
	 */
    protected void addXMLHead(StringBuffer buf) 
    	throws Exception
    {
        buf.append("<?xml version=\"1.0\" encoding=\"GBK\"?>");
    }

    /**
	 * 添加PACKET节
	 *
	 * @param buf StringBuffer
	 * @param blPrpJpoaInfo BLPrpJpoaInfo
	 * @throws Exception
	 */
    protected void addPacket(StringBuffer buf, BLPrpJpoaInfo blPrpJpoaInfo) 
    	throws Exception 
    {
    	buf.append("<PACKET type=\"REQUEST\" version=\"1.0\" >");
        addHead(buf, blPrpJpoaInfo);
        addBody(buf, blPrpJpoaInfo);
        buf.append("</PACKET>");

    }

    /**
	 * 添加HEAD节
	 *
	 * @param buf StringBuffer
	 * @throws Exception
	 */
    protected void addHead(StringBuffer buf, BLPrpJpoaInfo blPrpJpoaInfo) 
    	throws Exception 
    {
        String strComCode = blPrpJpoaInfo.getArr(0).getComCode();
    	buf.append("<HEAD>");
        addNode(buf, "REQUEST_TYPE", "19");
        addNode(buf, "USER", AppConfig.get("sysconst.CI_INSURED_" + strComCode.substring(0, 2) + "_USER"));
        addNode(buf, "PASSWORD", AppConfig.get("sysconst.CI_INSURED_" + strComCode.substring(0, 2) + "_PASSWORD"));
        buf.append("</HEAD>");
    }

    /**
	 * 添加BODY节
	 *
	 * @param buf StringBuffer
	 * @throws Exception
	 */
    protected void addBody(StringBuffer buf, BLPrpJpoaInfo blPrpJpoaInfo) 
    	throws Exception 
    {
        buf.append("<BODY>");
        addBasePart(buf, blPrpJpoaInfo);
        buf.append("</BODY>");
    }

    /**
	 * 添加BASE_PART节
	 *
	 * @param buf StringBuffer
	 * @throws Exception
	 */
    protected void addBasePart(StringBuffer buf, BLPrpJpoaInfo blPrpJpoaInfo) 
    	throws Exception 
    {
        String strCircPaymentNo 	    = ""; 
        String strWarrantNo 		    = ""; 
        String strPayment  	            = ""; 
        String strBankPaymentTime       = ""; 
        
        strCircPaymentNo = blPrpJpoaInfo.getArr(0).getPoaCode();
        strWarrantNo = blPrpJpoaInfo.getArr(0).getAccBillNo();
        strPayment = blPrpJpoaInfo.getArr(0).getTotalFee();
        strBankPaymentTime =  blPrpJpoaInfo.getArr(0).getOpenDate();
        strBankPaymentTime = correctDateTime(strBankPaymentTime);
        
        
        
        buf.append("<BASE_PART>");
        addNode(buf, "CIRC_PAYMENT_NO", strCircPaymentNo);
        addNode(buf, "WARRANT_NO", strWarrantNo);
        addNode(buf, "PAYMENT", strPayment);
        addNode(buf, "BANK_PAYMENT_TIME", strBankPaymentTime);
        buf.append("</BASE_PART>");
    }

    public static void addNode(StringBuffer buffer, String name, String value) {
        value = StringUtils.replace(value, "<", "&lt;");
        value = StringUtils.replace(value, ">", "&gt;");
        value = StringUtils.replace(value, "&", "&amp;");
        value = StringUtils.replace(value, "'", "&apos;");
        value = StringUtils.replace(value, "\"", "&quot;");

        buffer.append("<");
        buffer.append(name);
        buffer.append(">");
        buffer.append(value);
        buffer.append("</");
        buffer.append(name);
        buffer.append(">");
        buffer.append("\r\n");
    }

    
    /**
	 * 纠正日期格式
	 *
	 * @param dateString
	 *            8个字符的日期
	 * @return YYYYMMDD形式的日期
	 */
    private String correctDate(String dateString) {
        String result = StringUtils.replace(dateString, "-", "");
        return result;
    }

    /**
	 * 纠正日期时间格式
	 *
	 * @param dateString
	 *            修正前的日期时间
	 * @return 修正后的日期时间
	 */
    private String correctDateTime(String dateString) {
        String result = correctDate(dateString);
        result = StringUtils.replace(result, " ", "");
        result = StringUtils.replace(result, ":", "");
        return result;
    }
}

