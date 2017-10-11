package com.sp.indiv.ci.interf;

import java.text.DecimalFormat;

import org.apache.log4j.Logger;
import com.sp.sysframework.common.util.StringUtils;
import com.sp.sysframework.reference.AppConfig;
/**
 * ����XX��ѯ�������ݵı�����
 * add by fanjiangtao 20140126
 */
public class BICIInsurePmVehicleEncoder {
    
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
        logger.info("[��ҵXXXXX���ܳ�����Ϣ��ѯ���ͱ���]:"+buf.toString());
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
    	buf.append("<Packet type=\"REQUEST\" version=\"1.0\" >");
        addHead(buf,ComCode);
        addBody(buf,CarMark,FrameLastSixNo);
        buf.append("</Packet>");

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
    	buf.append("<Head>");
        addNode(buf, "RequestType", "V43");
        addNode(buf, "User", AppConfig.get("sysconst.BI_INSURED_" + ComCode.substring(0, 2) + "_USER"));
        addNode(buf, "Password", AppConfig.get("sysconst.BI_INSURED_" + ComCode.substring(0, 2) + "_PASSWORD"));
        buf.append("</Head>");
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
        buf.append("<Body>");
        addBasePart(buf, CarMark,FrameLastSixNo);
        buf.append("</Body>");
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
        buf.append("<BasePart>");
        addNode(buf, "LicensePlateNo", CarMark);
        addNode(buf, "VINLastSixNo", FrameLastSixNo);
        buf.append("</BasePart>");
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

