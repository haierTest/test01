package com.sp.indiv.ci.interf;

import java.text.DecimalFormat;

import org.apache.log4j.Logger;
import com.sp.sysframework.common.util.StringUtils;
import com.sp.sysframework.reference.AppConfig;
/**
 * ����XX��ѯ�������ݵı�����
 * add by fanjiangtao 20140126
 */
public class CIInsurePmVehicleEncoder {
    
	private static Logger logger = Logger.getLogger(CIInsurePmVehicleEncoder.class);
	
    /**
	 * 
	 *
	 * @return XX��ѯ����XML��ʽ��
	 * @throws Exception
	 */
    public String encode(String CarMark,String FrameLastSixNo,String ComCode) 
    	throws Exception 
    {
    	
        StringBuffer buf = new StringBuffer(4000);
        addXMLHead(buf);
        addPacket(buf, CarMark,FrameLastSixNo,ComCode);
        logger.info("[���ܳ�����Ϣ��ѯ���ͱ���]:"+buf.toString());
        return buf.toString();
    }

    /**
	 * ���XML�ļ�ͷ
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
	 * ���PACKET��
	 *
	 * @param buf
	 *            StringBuffer
	 * @throws Exception
	 */
    protected void addPacket(StringBuffer buf, String CarMark,String FrameLastSixNo,String ComCode) 
    	throws Exception 
    {
    	buf.append("<PACKET type=\"REQUEST\" version=\"1.0\" >");
        addHead(buf,ComCode);
        addBody(buf,CarMark,FrameLastSixNo);
        buf.append("</PACKET>");

    }

    /**
	 * ���HEAD��
	 *
	 * @param buf
	 *            StringBuffer
	 * @throws Exception
	 */
    protected void addHead(StringBuffer buf,String ComCode) 
    	throws Exception 
    {
    	buf.append("<HEAD>");
        addNode(buf, "REQUEST_TYPE", "11");
        addNode(buf, "USER", AppConfig.get("sysconst.CI_INSURED_" + ComCode.substring(0, 2) + "_USER"));
        addNode(buf, "PASSWORD", AppConfig.get("sysconst.CI_INSURED_" + ComCode.substring(0, 2) + "_PASSWORD"));
        buf.append("</HEAD>");
    }

    /**
	 * ���BODY��
	 *
	 * @param buf
	 *            StringBuffer
	 * @throws Exception
	 */
    protected void addBody(StringBuffer buf, String CarMark,String FrameLastSixNo) 
    	throws Exception 
    {
        buf.append("<BODY>");
        addBasePart(buf, CarMark,FrameLastSixNo);
        buf.append("</BODY>");
    }

    /**
	 * ���BASE_PART��
	 *
	 * @param buf
	 *            StringBuffer
	 * @throws Exception
	 */
    protected void addBasePart(StringBuffer buf, String CarMark,String FrameLastSixNo) 
    	throws Exception 
    {
        buf.append("<BASE_PART>");
        addNode(buf, "CAR_MARK", CarMark);
        addNode(buf, "FRAME_LAST_SIX_NO", FrameLastSixNo);
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
	 * �������ڸ�ʽ
	 *
	 * @param dateString
	 *            8���ַ�������
	 * @return YYYYMMDD��ʽ������
	 */
    private String correctDate(String dateString) {
        String result = StringUtils.replace(dateString, "-", "");
        return result;
    }

    /**
	 * ��������ʱ���ʽ
	 *
	 * @param dateString
	 *            ����ǰ������ʱ��
	 * @return �����������ʱ��
	 */
    private String correctDateTime(String dateString) {
        String result = correctDate(dateString);
        result = StringUtils.replace(dateString, " ", "");
        result = StringUtils.replace(dateString, ":", "");
        return result;
    }
   
}

