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
 * ����XXע���������ݵı�����
 * 
 */
public class PolicyCancelEncoder {

	/**
	 * ����
	 * 
	 * @return XX��ѯ����XML��ʽ��
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
	protected void addPacket(StringBuffer buf, BLEndorse blEndorse)
			throws Exception {
		buf.append("<PACKET type=\"REQUEST\" version=\"1.0\" >");
		
		addHead(buf,blEndorse);		
		addBody(buf, blEndorse);
		
		buf.append("</PACKET>");

	}

	/**
	 * ���HEAD��
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
	 * ���BODY��
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
	 * ���BASE_PART��
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

	/**
	 * ���XXȷ������Ϣ
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
