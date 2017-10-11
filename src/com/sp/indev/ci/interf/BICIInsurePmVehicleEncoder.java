package com.sp.indiv.ci.interf;

import java.text.DecimalFormat;

import org.apache.log4j.Logger;
import com.sp.sysframework.common.util.StringUtils;
import com.sp.sysframework.reference.AppConfig;
/**
 * 发送XX查询请求数据的编码器
 * add by fanjiangtao 20140126
 */
public class BICIInsurePmVehicleEncoder {
    
	private static Logger logger = Logger.getLogger(CIInsurePmVehicleEncoder.class);
	
    /**
	 * 
	 *
	 * @return XX查询请求XML格式串
	 * @throws Exception
	 */
    public String encode(String CarMark,String FrameLastSixNo,String ComCode) 
    	throws Exception 
    {
    	
        StringBuffer buf = new StringBuffer(4000);
        addXMLHead(buf);
        addPacket(buf, CarMark,FrameLastSixNo,ComCode);
        logger.info("[商业XXXXX交管车辆信息查询发送报文]:"+buf.toString());
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
    protected void addPacket(StringBuffer buf, String CarMark,String FrameLastSixNo,String ComCode) 
    	throws Exception 
    {
    	buf.append("<Packet type=\"REQUEST\" version=\"1.0\" >");
        addHead(buf,ComCode);
        addBody(buf,CarMark,FrameLastSixNo);
        buf.append("</Packet>");

    }

    /**
	 * 添加HEAD节
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
	 * 添加BODY节
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
	 * 添加BASE_PART节
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
        result = StringUtils.replace(dateString, " ", "");
        result = StringUtils.replace(dateString, ":", "");
        return result;
    }
   
}

