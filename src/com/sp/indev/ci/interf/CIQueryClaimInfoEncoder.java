package com.sp.indiv.ci.interf;

import com.sp.indiv.ci.schema.CIQueryClaimInfoSchema;
import com.sp.sysframework.common.util.StringUtils;
import com.sp.sysframework.reference.AppConfig;

/**
 * ���Ͳ�ѯ ����ı�����
 * 
 */
public class CIQueryClaimInfoEncoder {

	/**
	 * ����
	 * 
	 * @return XXXXX��ѯ����XML��ʽ��
	 * @throws Exception
	 */
	public String encode(CIQueryClaimInfoSchema ciQueryClaimInfoSchema, String comcode) throws Exception {
		
		StringBuffer buf = new StringBuffer(500);
		addXMLHead(buf);
		addPacket(buf, ciQueryClaimInfoSchema, comcode);
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
	protected void addPacket(StringBuffer buf, CIQueryClaimInfoSchema ciQueryClaimInfoSchema, String comcode) throws Exception {
		buf.append("<PACKET type=\"REQUEST\" version=\"1.0\" >");
		addHead(buf, comcode);
		addBody(buf, ciQueryClaimInfoSchema);
		buf.append("</PACKET>");

	}

	/**
	 * ���HEAD��
	 * 
	 * @param buf
	 *            StringBuffer
	 * @throws Exception
	 */
	protected void addHead(StringBuffer buf, String comcode) throws Exception {
		buf.append("<HEAD>");
		addNode(buf, "REQUEST_TYPE", "41");
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
	protected void addBody(StringBuffer buf, CIQueryClaimInfoSchema ciQueryClaimInfoSchema) throws Exception {
		buf.append("<BODY>");
		buf.append("<BASE_PART>");
		addNode(buf, "CAR_MARK", ciQueryClaimInfoSchema.getCarMark());
		addNode(buf, "VEHICLE_TYPE", ciQueryClaimInfoSchema.getVehicleType());
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
