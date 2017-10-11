package com.sp.indiv.ci.interf;

import com.sp.sysframework.common.util.StringUtils;
import com.sp.sysframework.reference.AppConfig;
import com.sp.utility.string.ChgDate;
import com.sp.utility.UtiPower;
/**
 * ����˰������˰ƾ֤	����ı�����
 * 
 */
public class CarShipTaxAssembleEncoder {

    /**
	 * ����
	 * 
	 * @return ����˰������˰ƾ֤����XML��ʽ��
	 * @throws Exception
	 */
    public String encode(String comcode,String startDate,String endDate) throws Exception {
        
        StringBuffer buf = new StringBuffer(4000);
        addXMLHead(buf);
        addPacket(buf, comcode,startDate,endDate);
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
    protected void addPacket(StringBuffer buf,String comcode,String startDate,String endDate) throws Exception {
    	buf.append("<PACKET type=\"REQUEST\" version=\"1.0\" >");
        addHead(buf, comcode);
        addBody(buf,startDate,endDate);
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
        addNode(buf, "REQUEST_TYPE", "32");
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
    protected void addBody(StringBuffer buf,String startDate,String endDate) throws Exception {
        buf.append("<BODY>");
        buf.append("<BASE_PART>");
        addNode(buf, "TAX_START_DAT", correctDate(startDate));
        addNode(buf, "TAX_END_DAT", correctDate(endDate));


        addNode(buf, "COMPUTER_CODE", AppConfig.get("sysconst.CI_INSURED_01_COMPUTER_CODE"));
        
        String currentDate=new ChgDate().getCurrentTime("yyyy-MM-dd");
        if(new UtiPower().CarShipTaxCheckQueryBJ("01",currentDate)){
            addNode(buf, "BANK_CODE", AppConfig.get("sysconst.CI_INSURED_01_BANK_CODE"));
        }
        
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
    
    
    /**
	 * �������ڸ�ʽ
	 *
	 * @param dateString
	 *            8���ַ�������
	 * @return YYYYMMDD��ʽ������
	 */
    private static String correctDate(String dateString) {
        String result = StringUtils.replace(dateString, "-", "");
        return result;
    }
    
}
