package com.sp.indiv.ci.interf;

import com.sp.prpall.blsvr.jf.BLPrpJpoaInfo;
import com.sp.sysframework.common.util.StringUtils;
import com.sp.sysframework.reference.AppConfig;
import com.sp.utility.SysConfig;


/**
 * 发送XX缴费请求数据的编码器
 *
 */
public class PolicyPayRecEncoder {

    /**
	 * 
	 * @param blPrpJpoaInfo BLPrpJpoaInfo
	 * @return XX缴费请求XML格式串
	 * @throws Exception
	 */
    public String encode(BLPrpJpoaInfo blPrpJpoaInfo,String bankAccounts,String warrantBank)  
    	throws Exception 
    {
    	
        StringBuffer buf = new StringBuffer(4000);
        addXMLHead(buf);
        addPacket(buf, blPrpJpoaInfo,bankAccounts,warrantBank);
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
    protected void addPacket(StringBuffer buf, BLPrpJpoaInfo blPrpJpoaInfo,String bankAccounts,String warrantBank)  
    	throws Exception 
    {
    	buf.append("<PACKET type=\"REQUEST\" version=\"1.0\" >");
        addHead(buf, blPrpJpoaInfo);
        addBody(buf, blPrpJpoaInfo,bankAccounts,warrantBank);
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
        String strComCode = blPrpJpoaInfo.getBLPrpJplanFeePre().getArr(0).getComCode();
    	buf.append("<HEAD>");
        addNode(buf, "REQUEST_TYPE", "18");
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
    protected void addBody(StringBuffer buf, BLPrpJpoaInfo blPrpJpoaInfo,String bankAccounts,String warrantBank) 
    	throws Exception 
    {
        buf.append("<BODY>");
        addBasePart(buf, blPrpJpoaInfo,bankAccounts,warrantBank);
        buf.append("</BODY>");
    }

    /**
	 * 添加BASE_PART节
	 *
	 * @param buf StringBuffer
	 * @throws Exception
	 */
    protected void addBasePart(StringBuffer buf, BLPrpJpoaInfo blPrpJpoaInfo,String bankAccounts,String warrantBank) 
    	throws Exception 
    {
        String strPayMethod 	    = ""; 
        String strWarrantNo 		= ""; 
        String strBankAccounts  	= ""; 
        String strWarrantBank       = ""; 
        String strProConfirmSequenceNo = ""; 
        
        strPayMethod = blPrpJpoaInfo.getArr(0).getPayMethod();
        strWarrantNo = blPrpJpoaInfo.getArr(0).getAccBillNo();
        strBankAccounts = bankAccounts;
        strWarrantBank = warrantBank;
        
        
        
        if(strPayMethod != null && !"".equals(strPayMethod)&&"07".equals(strPayMethod)){
            /*MODIFY BY PENGJINLING 2013-4-8 payment-600 上海电销在线支付 BEGIN*/
        	
        	if("006".equals(bankAccounts)){
	        	strWarrantNo = SysConfig.getProperty("SH_UNIONPAYNO" );
	            strWarrantBank ="9998";
            }
        	if("007".equals(bankAccounts)){
	        	strWarrantNo = SysConfig.getProperty("SH_DXPAYNO" );
	        	strWarrantBank = "9998";
            }
        	if("008".equals(bankAccounts)){ 
	        	strWarrantNo = SysConfig.getProperty("SH_MNXPAYNO" );
	        	strWarrantBank = "9998";
            } 
        	if("009".equals(bankAccounts)){ 
	        	strWarrantNo = SysConfig.getProperty("SH_AXPAYNO" );
	        	strWarrantBank = "9998";
            }
        	
        	/*MODIFY BY PENGJINLING 2013-4-8 payment-600 上海电销在线支付 END*/
        }
        
        
        buf.append("<BASE_PART>");
        addNode(buf, "PAY_METHOD", strPayMethod);
        addNode(buf, "WARRANT_NO", strWarrantNo);
        addNode(buf, "BANK_ACCOUNTS", strBankAccounts);
        addNode(buf, "WARRANT_BANK", strWarrantBank);
        if (blPrpJpoaInfo.getBLPrpJplanFeePre().getArr(0).getCertiType()
				.equals("T")) {
			buf.append("<POLICY_LIST>");

			for (int i = 0; i < blPrpJpoaInfo.getBLPrpJplanFeePre().getSize(); i++) {
				strProConfirmSequenceNo = blPrpJpoaInfo.getBLPrpJplanFeePre()
						.getArr(i).getProConfirmSequenceNo();
				addNode(buf, "PROCONFIRM_SEQUENCE_NO", strProConfirmSequenceNo);
			}
			buf.append("</POLICY_LIST>");
		} else {
			buf.append("<AMEND_LIST>");

			for (int i = 0; i < blPrpJpoaInfo.getBLPrpJplanFeePre().getSize(); i++) {
				strProConfirmSequenceNo = blPrpJpoaInfo.getBLPrpJplanFeePre()
						.getArr(i).getProConfirmSequenceNo();
				addNode(buf, "AMEND_QUERY_NO", strProConfirmSequenceNo);
			}
			buf.append("</AMEND_LIST>");
		}
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

