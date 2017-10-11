package com.sp.indiv.ci.interf;

import java.util.ArrayList;
import com.sp.sysframework.common.util.StringUtils;
import com.sp.sysframework.reference.AppConfig;
import com.sp.utility.UtiPower;

/**
 * 车船税对账查询请求的编码器
 * 
 */
public class CarShipTaxDataCompareQGEncoder {

    /**
     * 编码
     * 
     * @return 车船税对账查询请求XML格式串
     * @throws Exception
     */
    public String encode(String comcode,ArrayList policyConfirmNo, ArrayList endorseConfirmNo) throws Exception {
        
        StringBuffer buf = new StringBuffer(4000);
        addXMLHead(buf);
        addPacket(buf,comcode,policyConfirmNo,endorseConfirmNo);
        return buf.toString();
    }

    /**
     * 添加XML文件头
     * 
     * @param buf
     *            StringBuffer
     */
    protected void addXMLHead(StringBuffer buf) {
        buf.append("<?xml version=\"1.0\" encoding=\"GBK\"?>");
    }

    /**
     * 添加PACKET节
     * 
     * @param buf
     *            StringBuffer
     * @throws Exception
     */
    protected void addPacket(StringBuffer buf,String comcode,ArrayList policyConfirmNo, ArrayList endorseConfirmNo) throws Exception {
        buf.append("<PACKET type=\"REQUEST\" version=\"1.0\" >");
        addHead(buf, comcode);
        addBody(buf,policyConfirmNo, endorseConfirmNo);
        buf.append("</PACKET>");

    }

    /**
     * 添加HEAD节
     * 
     * @param buf
     *            StringBuffer
     * @throws Exception
     */
    protected void addHead(StringBuffer buf,String comcode) throws Exception {
        buf.append("<HEAD>");
        
        if(new UtiPower().isCarShipTaxNB(comcode.substring(0, 2))){
        	addNode(buf, "REQUEST_TYPE", "94");
        }else{
        	addNode(buf, "REQUEST_TYPE", "81");
        }
        
        addNode(buf, "USER", AppConfig.get("sysconst.CI_INSURED_" + comcode.substring(0, 2) + "_USER"));
        addNode(buf, "PASSWORD", AppConfig.get("sysconst.CI_INSURED_" + comcode.substring(0, 2) + "_PASSWORD"));
        buf.append("</HEAD>");
    }

    /**
     * 添加BODY节
     * 
     * @param buf
     *            StringBuffer
     * @throws Exception
     */
    protected void addBody(StringBuffer buf,ArrayList policyConfirmNo, ArrayList endorseConfirmNo) throws Exception {
        buf.append("<BODY>");
        addPolicyList(buf,policyConfirmNo);
        addAmendList(buf,endorseConfirmNo);
        buf.append("</BODY>");
    }
    
    /**
     * 添加POLICY_LIST节
     *
     * @param buf
     *            StringBuffer
     * @throws Exception
     */
    protected void addPolicyList(StringBuffer buf, ArrayList policyConfirmNo) throws Exception {
        buf.append("<POLICY_LIST>");
        addPolicyData(buf, policyConfirmNo);
        buf.append("</POLICY_LIST>");
    }
    
    /**
     * 添加POLICY_DATA节
     *
     * @param buf
     *            StringBuffer
     * @throws Exception
     */
    protected void addPolicyData(StringBuffer buf, ArrayList policyConfirmNo) throws Exception {
        for(int i=0;i<policyConfirmNo.size();i++){
            
               addNode(buf, "CONFIRM_SEQUENCE_NO", (String)policyConfirmNo.get(i));
            
        }
    }
    
    /**
     * 添加AMEND_LIST节
     *
     * @param buf
     *            StringBuffer
     * @throws Exception
     */
    protected void addAmendList(StringBuffer buf, ArrayList endorseConfirmNo) throws Exception {
        buf.append("<AMEND_LIST>");
           addAmendData(buf, endorseConfirmNo);
        buf.append("</AMEND_LIST>");
    }
    
    /**
     * 添加AMEND_DATA节
     *
     * @param buf
     *            StringBuffer
     * @throws Exception
     */
    protected void addAmendData(StringBuffer buf,ArrayList endorseConfirmNo) throws Exception {
        for(int i=0;i<endorseConfirmNo.size();i++){
            
               addNode(buf, "AMEND_CONFIRM_NO", (String)endorseConfirmNo.get(i));
            
        }
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

