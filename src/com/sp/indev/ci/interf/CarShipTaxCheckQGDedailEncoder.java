package com.sp.indiv.ci.interf;

import com.sp.sysframework.common.util.StringUtils;
import com.sp.sysframework.reference.AppConfig;
import com.sp.utility.UtiPower;

/**
 * ����˰���˲�ѯ����ı�����
 * 
 */
public class CarShipTaxCheckQGDedailEncoder {

    /**
     * ����
     * 
     * @return ����˰���˲�ѯ����XML��ʽ��
     * @throws Exception
     */
    public String encode(String comcode,String taxDeclareQueryNo,String policyPageLength, String policyPageNum,String endorsePageLength,String endorsePageNum) throws Exception {
        
        StringBuffer buf = new StringBuffer(4000);
        addXMLHead(buf);
        addPacket(buf,comcode,taxDeclareQueryNo,policyPageLength, policyPageNum,endorsePageLength,endorsePageNum);
        return buf.toString();
    }

    /**
     * ���XML�ļ�ͷ
     * 
     * @param buf
     *            StringBuffer
     */
    protected void addXMLHead(StringBuffer buf) {
        buf.append("<?xml version=\"1.0\" encoding=\"GBK\"?>");
    }

    /**
     * ���PACKET��
     * 
     * @param buf
     *            StringBuffer
     * @throws Exception
     */
    protected void addPacket(StringBuffer buf,String comcode,String taxDeclareQueryNo,String policyPageLength, String policyPageNum,String endorsePageLength,String endorsePageNum) throws Exception {
        buf.append("<PACKET type=\"REQUEST\" version=\"1.0\" >");
        addHead(buf, comcode);
        addBody(buf,taxDeclareQueryNo, policyPageLength,policyPageNum,endorsePageLength,endorsePageNum);
        buf.append("</PACKET>");

    }

    /**
     * ���HEAD��
     * 
     * @param buf
     *            StringBuffer
     * @throws Exception
     */
    protected void addHead(StringBuffer buf,String comcode) throws Exception {
        buf.append("<HEAD>");
        
        if(new UtiPower().isCarShipTaxNB(comcode.substring(0, 2))){
        	addNode(buf, "REQUEST_TYPE", "92");
        }else{
        	addNode(buf, "REQUEST_TYPE", "82");
        }
        
        addNode(buf, "USER", AppConfig.get("sysconst.CI_INSURED_" + comcode.substring(0, 2) + "_USER"));
        addNode(buf, "PASSWORD", AppConfig.get("sysconst.CI_INSURED_" + comcode.substring(0, 2) + "_PASSWORD"));
        buf.append("</HEAD>");
    }

    /**
     * ���BODY��
     * 
     * @param buf
     *            StringBuffer
     * @throws Exception
     */
    protected void addBody(StringBuffer buf,String taxDeclareQueryNo, String policyPageLength,String policyPageNum,String endorsePageLength,String endorsePageNum) throws Exception {
        buf.append("<BODY>");
        
        buf.append("<BASE_PART>");
        addNode(buf, "TAX_DECLARE_QUERY_NO", taxDeclareQueryNo);
        addNode(buf, "TAX_POLICY_PERPAGE", policyPageLength);
        addNode(buf, "TAX_POLICY_PAGE_NO", policyPageNum);
        addNode(buf, "TAX_AMEND_PERPAGE", endorsePageLength);
        addNode(buf, "TAX_AMEND_PAGE_NO", endorsePageNum);
        buf.append("</BASE_PART>");
        buf.append("</BODY>");
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
}

