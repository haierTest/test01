package com.sp.indiv.ci.interf;

import java.util.Vector;

import com.sp.indiv.ci.blsvr.BLCIInsureValid;
import com.sp.indiv.ci.dbsvr.DBCIInsureValid;
import com.sp.indiv.ci.schema.CIInsureValidSchema;
import com.sp.prpall.blsvr.pg.BLEndorse;
import com.sp.sysframework.common.util.StringUtils;
import com.sp.sysframework.reference.AppConfig;
import com.sp.utility.SysConfig;
import com.sp.utility.UtiPower;
import com.sp.utility.database.DbPool;

/**
 * 发送XX注销请求数据的编码器
 * 
 */
public class PolicyCancelEncoder {

	/**
	 * 编码
	 * 
	 * @return XX查询请求XML格式串
	 * @throws Exception
	 */
	public String encode(DbPool dbPool, BLEndorse blEndorse) throws Exception {
		
		StringBuffer buf = new StringBuffer(4000);
		getCIInsureValid(dbPool, blEndorse);
		addXMLHead(buf);
		addPacket(buf, blEndorse);
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
	protected void addPacket(StringBuffer buf, BLEndorse blEndorse)
			throws Exception {
		buf.append("<PACKET type=\"REQUEST\" version=\"1.0\" >");
		
		addHead(buf,blEndorse);		
		addBody(buf, blEndorse);
		
		buf.append("</PACKET>");

	}

	/**
	 * 添加HEAD节
	 * 
	 * @param buf
	 *            StringBuffer
	 * @throws Exception
	 */
	protected void addHead(StringBuffer buf,BLEndorse blEndorse) 
		throws Exception 
	{
		String strComCode = blEndorse.getBLPrpPmain().getArr(0).getComCode();
		buf.append("<HEAD>");
        addNode(buf, "REQUEST_TYPE", "03");
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
	protected void addBody(StringBuffer buf, BLEndorse blEndorse)
		throws Exception 
	{
		buf.append("<BODY>");
		addBasePart(buf, blEndorse);
		buf.append("</BODY>");
	}

	/**
	 * 添加BASE_PART节
	 * 
	 * @param buf
	 *            StringBuffer
	 * @throws Exception
	 */
	protected void addBasePart(StringBuffer buf, BLEndorse blEndorse)
		throws Exception 
	{
		buf.append("<BASE_PART>");
		addNode(buf, "CONFIRM_SEQUENCE_NO", blEndorse.getBLCIInsureValid().getArr(0).getValidNo());
		buf.append("</BASE_PART>");
	}

	public static void addNode(StringBuffer buffer, String name, String value) 
	{
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

	/**
	 * 获得XX确认主信息
	 * 
	 * @param blEndorse
	 * @throws Exception
	 */
	private void getCIInsureValid(DbPool dbPool, 
								  BLEndorse blEndorse) 
		throws Exception 
	{
		try {
			String sqlWhere = " Select * from CIInsureValid WHERE PolicyNo = '"
							  + blEndorse.getBLPrpPhead().getArr(0).getPolicyNo() + "'";
			DBCIInsureValid dbCIInsureValid = new DBCIInsureValid();
			Vector vector = dbCIInsureValid.findByConditions(dbPool, sqlWhere);
			CIInsureValidSchema cIInsureValidSchema = (CIInsureValidSchema) vector .get(0);
			BLCIInsureValid blCIInsureValid = new BLCIInsureValid();
			blCIInsureValid.setArr(cIInsureValidSchema);
			blEndorse.setBLCIInsureValid(blCIInsureValid);
		} 
		catch (Exception ex) 
		{
			throw ex;
		}
	}
}
