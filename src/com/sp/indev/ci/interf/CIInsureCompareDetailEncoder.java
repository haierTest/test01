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
	 * 编码
	 * @return XX查询请求XML格式串
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
			throw new Exception("交强XXXXX平台-数据比对接口：encode()数据源接收失败！");
		}
		return buf.toString();
	}

	/**
	 * 添加XML文件头
	 * 
	 * @param buf
	 *            StringBuffer
	 */
	protected void addXMLHead(StringBuffer buf) 
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
	 * 添加HEAD节
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
	 * 添加BODY节
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
	 * 添加BASE_PART节
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
	 * 增加Policy_List节
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
