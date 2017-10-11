package com.sp.indiv.ci.interf;

import com.sp.prpall.blsvr.tb.BLProposal;
import com.sp.sysframework.reference.AppConfig;
import com.sp.sysframework.common.util.StringUtils;

/**
 * 发送交易回退请求数据的编码器
 * 
 */
public class ProposalRepealAfterAuditingEncoder {

   /**
	 * @return 交易回退编码
	 * @throws Exception
	 */
    public String encode(BLProposal blProposal) 
    	throws Exception 
    {
    	
        StringBuffer buf = new StringBuffer(4000);
        addXMLHead(buf);
        addPacket(buf, blProposal);
        return buf.toString();
    }

    /**
	 * 添加XML文件头
	 *
	 * @param buf
	 *            StringBuffer
	 */
    protected void addXMLHead(StringBuffer buf) 
    	throws Exception
    {
        buf.append("<?xml version=\"1.0\" encoding=\"GBK\"?>");
    }

    /**
	 * 添加PACKET节
	 *
	 * @param buf
	 *            StringBuffer
	 * @throws Exception
	 */
    protected void addPacket(StringBuffer buf, BLProposal blProposal) 
    	throws Exception 
    {
    	buf.append("<PACKET type=\"REQUEST\" version=\"1.0\" >");
        addHead(buf, blProposal);
        addBody(buf, blProposal);
        buf.append("</PACKET>");

    }

    /**
	 * 添加HEAD节
	 *
	 * @param buf
	 *            StringBuffer
	 * @throws Exception
	 */
    protected void addHead(StringBuffer buf, BLProposal blProposal) 
    	throws Exception 
    {
        String strComCode = blProposal.getBLPrpTmain().getArr(0).getComCode();
        buf.append("<HEAD>");
        addNode(buf, "REQUEST_TYPE", "12");
        addNode(buf, "USER", AppConfig.get("sysconst.CI_INSURED_" + strComCode.substring(0, 2) + "_USER"));
        addNode(buf, "PASSWORD", AppConfig.get("sysconst.CI_INSURED_" + strComCode.substring(0, 2) + "_PASSWORD"));
        buf.append("</HEAD>");
    }

    /**
	 * 添加BODY节
	 *
	 * @param buf
	 *            StringBuffer
	 * @throws Exception
	 */
    protected void addBody(StringBuffer buf, BLProposal blProposal) 
    	throws Exception 
    {
        buf.append("<BODY>");
        addBasePart(buf, blProposal);
        buf.append("</BODY>");
    }
    
    /**
	 * 添加BASE_PART节
	 *
	 * @param buf
	 *            StringBuffer
	 * @throws Exception
	 */
    protected void addBasePart(StringBuffer buf, BLProposal blProposal) 
    	throws Exception 
    {
    	String strBookingCode          = ""; 
    	String strProConfirmSequenceNo = ""; 
    	String strDummyRepealFlag      = ""; 
    	strProConfirmSequenceNo = blProposal.getBLCIInsureDemand().getArr(0).getProconfirmSequenceNo();
        buf.append("<BASE_PART>");
        addNode(buf, "BOOKING_CODE", strBookingCode);
        addNode(buf, "PROCONFIRM_SEQUENCE_NO", strProConfirmSequenceNo);
        addNode(buf, "DUMMY_REPEAL_FLAG", strDummyRepealFlag);
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
	 * @param args
	 */
	public static void main(String[] args) {

	}

}
