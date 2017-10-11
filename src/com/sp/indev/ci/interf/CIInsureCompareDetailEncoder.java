package com.sp.indiv.ci.interf;

import com.sp.indiv.ci.blsvr.BLCIInsureCompare;
import com.sp.indiv.ci.schema.CIInsureCompareSchema;
import com.sp.indiv.ci.schema.CIInsureValidSchema;
import com.sp.prpall.blsvr.cb.BLPolicy;
import com.sp.sysframework.common.util.StringUtils;
import com.sp.sysframework.reference.AppConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;

public class CIInsureCompareDetailEncoder 
{
	/**
	 * ����
	 * @return XX��ѯ����XML��ʽ��
	 * @throws Exception
	 */
	public String encode(DbPool dbPool, 
						 BLPolicy blPolicy, 
						 String compareDate, 
						 int maxSerialNo,
						 int maxSerialNoClaim, 
						 String iRequestType,
						 String iComCode)
		throws Exception 
	{
		
		StringBuffer buf = new StringBuffer(4000);
		if (dbPool != null) 
		{
			addXMLHead(buf);
			addPacket(buf, blPolicy, dbPool, compareDate, maxSerialNo, 
					  maxSerialNoClaim, iRequestType, iComCode);
		} 
		else 
		{
			throw new Exception("��ǿXXXXXƽ̨-���ݱȶԽӿڣ�encode()����Դ����ʧ�ܣ�");
		}
		return buf.toString();
	}

	/**
	 * ���XML�ļ�ͷ
	 * 
	 * @param buf
	 *            StringBuffer
	 */
	protected void addXMLHead(StringBuffer buf) 
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
	protected void addPacket(StringBuffer buf, 
							 BLPolicy blPolicy, 
							 DbPool dbPool, 
							 String compareDate, 
							 int maxSerialNo, 
							 int maxSerialNoClaim,
							 String iRequestType,
							 String iComCode)
			throws Exception 
	{
		buf.append("<PACKET type=\"REQUEST\" version=\"1.0\" >");
		addHead(buf, blPolicy, iComCode);
		addBody(buf, blPolicy, dbPool, compareDate, maxSerialNo, 
				maxSerialNoClaim, iRequestType, iComCode);
		buf.append("</PACKET>");

	}

	/**
	 * ���HEAD��
	 * 
	 * @param buf
	 *            StringBuffer
	 * @throws Exception
	 */
	protected void addHead(StringBuffer buf, BLPolicy blPolicy, String iComCode)
			throws Exception 
	{
		buf.append("<HEAD>");
		addNode(buf, "REQUEST_TYPE", "61");
		addNode(buf, "USER", AppConfig.get("sysconst.CI_INSURED_" + iComCode.substring(0, 2) + "_USER"));
		addNode(buf, "PASSWORD", AppConfig.get("sysconst.CI_INSURED_" + iComCode.substring(0, 2) + "_PASSWORD"));
		
		
		
        
		buf.append("</HEAD>");
	}

	/**
	 * ���BODY��
	 * 
	 * @param buf
	 *            StringBuffer
	 * @throws Exception
	 */
	protected void addBody(StringBuffer buf, 
						   BLPolicy blPolicy, 
						   DbPool dbPool,
						   String compareDate,
						   int maxSerialNo, 
						   int maxSerialNoClaim,
						   String iRequestType,
						   String iComCode)
			throws Exception 
	{
		buf.append("<BODY>");
		addBasePart(buf, blPolicy, dbPool, compareDate, maxSerialNo, 
					maxSerialNoClaim, iRequestType, iComCode);
		buf.append("</BODY>");
	}

	/**
	 * ���BASE_PART��
	 * 
	 * @param buf
	 *            StringBuffer
	 * @throws Exception
	 */
	protected void addBasePart(StringBuffer buf, 
							   BLPolicy blPolicy, 
							   DbPool dbPool, 
							   String compareDate, 
							   int maxSerialNo,
							   int maxSerialNoClaim,
							   String iRequestType,
							   String iComCode)
		throws Exception 
	{
		buf.append("<BASE_PART>");
		addPolicyList(buf, blPolicy, dbPool, compareDate, maxSerialNo, maxSerialNoClaim, iRequestType, iComCode);
		buf.append("</BASE_PART>");
	}

	/**
	 * ����Policy_List��
	 * 
	 * @param buf
	 * @param blPolicy
	 * @throws Exception 
	 */
	protected void addPolicyList(StringBuffer buf, 
								 BLPolicy blPolicy, 
								 DbPool dbPool, 
								 String compareDate,
								 int maxSerialNo, 
								 int maxSerialNoClaim, 
								 String iRequestType, 
								 String iComCode) 
		throws Exception 
	{
		
		addPolicyData(buf, blPolicy, dbPool, compareDate, maxSerialNo, 
					  maxSerialNoClaim, iRequestType, iComCode);
		
	}

	protected void addPolicyData(StringBuffer buf, 
			 					BLPolicy blPolicy, 
			 					DbPool dbPool, 
			 					String compareDate,
			 					int maxSerialNo,
			 					int maxSerialNoClaim,
			 					String iRequestType,
			 					String iComCode) 
		throws Exception 
	{
		addNode(buf, "QUERY_DATE", correctDate(compareDate));		
		addNode(buf, "QUERY_TYPE", iRequestType);					
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
}
