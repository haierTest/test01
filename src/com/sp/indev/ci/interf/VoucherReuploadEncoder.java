package com.sp.indiv.ci.interf;

import com.sp.indiv.ci.blsvr.BLCICarshipTaxDemand;
import com.sp.indiv.ci.blsvr.BLCIInsureValid;
import com.sp.prpall.blsvr.cb.BLPolicy;
import com.sp.prpall.blsvr.cb.BLPrpCmain;
import com.sp.sysframework.common.util.StringUtils;
import com.sp.sysframework.reference.AppConfig;
import com.sp.utility.database.DbPool;

public class VoucherReuploadEncoder {
	/**
	 * @return 凭证补传编码
	 * @throws Exception
	 */
    public String encode(DbPool dbPool, String iPolicyNo, String iFeeNo, String iFreeNo, String iDepartment,String iYearNo) 
    	throws Exception 
    {
    	
        StringBuffer buf = new StringBuffer(4000);
        addXMLHead(buf);
        addPacket(buf, dbPool, iPolicyNo, iFeeNo, iFreeNo, iDepartment, iYearNo);
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
    protected void addPacket(StringBuffer buf, DbPool dbPool, String iPolicyNo, String iFeeNo, String iFreeNo, String iDepartment, String iYearNo) 
    	throws Exception 
    {
    	buf.append("<PACKET type=\"REQUEST\" version=\"1.0\" >");
        addHead(buf, iPolicyNo);
        addBody(buf, dbPool, iPolicyNo, iFeeNo, iFreeNo, iDepartment, iYearNo);
        buf.append("</PACKET>");

    }

    /**
	 * 添加HEAD节
	 *
	 * @param buf
	 *            StringBuffer
	 * @throws Exception
	 */
    protected void addHead(StringBuffer buf, String iPolicyNo) 
    	throws Exception 
    {
    	BLPrpCmain blPrpCmain = new BLPrpCmain();
    	blPrpCmain.getData(iPolicyNo);
        String strComCode = blPrpCmain.getArr(0).getComCode();
        buf.append("<HEAD>");
        addNode(buf, "REQUEST_TYPE", "27");
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
    protected void addBody(StringBuffer buf, DbPool dbPool, String iPolicyNo, String iFeeNo, String iFreeNo, String iDepartment, String iYearNo) 
    	throws Exception 
    {
    	String strWherePart = " PolicyNo = '" + iPolicyNo + "'";
    	BLCIInsureValid blCIInsureValid = new BLCIInsureValid();
        blCIInsureValid.query(dbPool, strWherePart);
        buf.append("<BODY>");
        addNode(buf, "CONFIRM_SEQUENCE_NO", blCIInsureValid.getArr(0).getValidNo());
        addNode(buf, "COMPUTER_CODE",AppConfig.get("sysconst.CI_INSURED_01_COMPUTER_CODE"));
        addNode(buf, "PAY_NO", iFeeNo);
        addNode(buf, "FREE_NO", iFreeNo);
        addNode(buf, "DEPARTMENT", iDepartment);
        addNode(buf, "YEAR_NO", iYearNo);
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
	 * @param args
	 */
	public static void main(String[] args) {

	}
}
