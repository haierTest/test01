package com.sp.indiv.ci.interf;

import java.sql.ResultSet;
import java.util.*;
import com.sp.indiv.ci.blsvr.*;
import com.sp.indiv.ci.dbsvr.*;
import com.sp.indiv.ci.schema.*;
import com.sp.prpall.blsvr.cb.BLPolicy;
import com.sp.sysframework.common.util.StringUtils;
import com.sp.sysframework.reference.AppConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.string.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CIInsureCompareEncoder 
{
	Log logger = LogFactory.getLog(getClass());
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
						 String iComCode)
		throws Exception 
	{
		
		StringBuffer buf = new StringBuffer(4000);
		if (dbPool != null) 
		{
			addXMLHead(buf);
			addPacket(buf, blPolicy, dbPool, compareDate, maxSerialNo, maxSerialNoClaim, iComCode);
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
							 String iComCode)
			throws Exception 
	{
		buf.append("<PACKET type=\"REQUEST\" version=\"1.0\" >");

		addHead(buf, blPolicy, iComCode);
		addBody(buf, blPolicy, dbPool, compareDate, maxSerialNo, maxSerialNoClaim, iComCode);

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
		addNode(buf, "REQUEST_TYPE", "60");
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
						   String iComCode)
			throws Exception 
	{
		buf.append("<BODY>");
		addBasePart(buf, blPolicy, dbPool, compareDate, maxSerialNo, maxSerialNoClaim, iComCode);
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
							   String iComCode)
		throws Exception 
	{
		buf.append("<BASE_PART>");
		addPolicyList(buf, blPolicy, dbPool, compareDate, maxSerialNo, maxSerialNoClaim, iComCode);
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
								 String iComCode) 
		throws Exception 
	{
		
		addPolicyData(buf, blPolicy, dbPool, compareDate, maxSerialNo, maxSerialNoClaim, iComCode);
		
	}

	protected void addPolicyData(StringBuffer buf, 
			 					BLPolicy blPolicy, 
			 					DbPool dbPool, 
			 					String compareDate,
			 					int maxSerialNo,
			 					int maxSerialNoClaim,
			 					String iComCode) 
		throws Exception 
	{
		CIInsureValidSchema sechema = null;
		CIInsureCompareSchema cIInsureCompareSchema = null;
		cIInsureCompareSchema = getCIInsureCompanyCompareBusiness(dbPool, blPolicy, 
																  compareDate, maxSerialNo, 
																  maxSerialNoClaim, iComCode);
		
		if (cIInsureCompareSchema != null)
		{
			
			logger.info("aaaaaaaaaaaaaaa-correctDate(cIInsureCompareSchema.getCompareDate()): " + correctDate(cIInsureCompareSchema.getCompareDate()));
			logger.info("aaaaaaaaaaaaaaa-cIInsureCompareSchema.getPutConfirm()): " + cIInsureCompareSchema.getPutConfirm());
			logger.info("aaaaaaaaaaaaaaa-cIInsureCompareSchema.getPutCancel()): " + cIInsureCompareSchema.getPutCancel());
			logger.info("aaaaaaaaaaaaaaa-cIInsureCompareSchema.getPutWithDraw()): " + cIInsureCompareSchema.getPutWithDraw());
			logger.info("aaaaaaaaaaaaaaa-cIInsureCompareSchema.getPutReport()): " + cIInsureCompareSchema.getPutReport());
			
			
			addNode(buf, "COMPARE_DATE", correctDate(cIInsureCompareSchema.getCompareDate()));		
			addNode(buf, "GS_CONFIRM", cIInsureCompareSchema.getPutConfirm());			
			addNode(buf, "GS_CANCEL", cIInsureCompareSchema.getPutCancel());			
			addNode(buf, "GS_SURRENDER", cIInsureCompareSchema.getPutWithDraw());		
			addNode(buf, "GS_REPORT", cIInsureCompareSchema.getPutReport());			
			addNode(buf, "GS_REGISTRATION", cIInsureCompareSchema.getPutRegistration());
			addNode(buf, "GS_ENDCASE", cIInsureCompareSchema.getPutEndClaim());			
			addNode(buf, "GS_CANCELCLAIM", cIInsureCompareSchema.getPutCancelClaim());	
		}
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
	 * ��CIInsureCompanyCompare��ȡXX��ͳ�����ݣ�XXXXX��ͳ������
	 * 
	 * @param blEndorse
	 * @throws Exception
	 */
	private CIInsureCompareSchema getCIInsureCompanyCompareBusiness(DbPool dbPool, 
																	BLPolicy blPolicy, 
																	String compareDate,
																	int maxSerialNo,
																	int maxSerialNoClaim,
																	String iComCode)
		throws Exception 
	{
		BLCIInsureCompare blCIInsureCompare 		= blPolicy.getBLCIInsureCompare();
		CIInsureCompareSchema cIInsureCompareSchema = blCIInsureCompare.getArr(0);
		
		int intConfirmTotal 		= 0;		
		int intCancleTotal 			= 0;		
		int intWithDrawTotal 		= 0;		
		int intReportTotal 			= 0;		
		int intRegistrationTotal 	= 0;		
		int intEndClaimTotal 		= 0;		
		int intCancleClaimTotal 	= 0;		
		
		
		intConfirmTotal = blCIInsureCompare.getConfirmTotal(dbPool, compareDate , maxSerialNo, iComCode);
		
		logger.info("=======================XXXXX��ǿXXXXXƽ̨���ݱȶ�-XXȷ�ϼ���: " + intConfirmTotal);
		
		
		intCancleTotal = blCIInsureCompare.getCancleTotal(dbPool, compareDate , maxSerialNo, iComCode);
		
		logger.info("=======================XXXXX��ǿXXXXXƽ̨���ݱȶ�-ע��XX����: " + intCancleTotal);
		
		
		intWithDrawTotal = blCIInsureCompare.getWithDrawTotal(dbPool, compareDate , maxSerialNo, iComCode);
		
		logger.info("=======================XXXXX��ǿXXXXXƽ̨���ݱȶ�-��XXXXXXX����: " + intWithDrawTotal);
		
		
		intReportTotal = blCIInsureCompare.getReportTotal(dbPool, compareDate , maxSerialNoClaim, iComCode);
		
		logger.info("=======================XXXXX��ǿXXXXXƽ̨���ݱȶ�-��������: " + intReportTotal);
		
		
		intRegistrationTotal = blCIInsureCompare.getRegistrationTotal(dbPool, compareDate , maxSerialNoClaim, iComCode);
		
		logger.info("=======================XXXXX��ǿXXXXXƽ̨���ݱȶ�-��������: " + intRegistrationTotal);
		
		
		intEndClaimTotal = blCIInsureCompare.getEndClaimTotal(dbPool, compareDate , maxSerialNoClaim, iComCode);
		
		logger.info("=======================XXXXX��ǿXXXXXƽ̨���ݱȶ�-�᰸����: " + intEndClaimTotal);
		
		
		intCancleClaimTotal = blCIInsureCompare.getCancelClaimTotal(dbPool, compareDate , maxSerialNoClaim, iComCode);
		
		logger.info("=======================XXXXX��ǿXXXXXƽ̨���ݱȶ�-ע��XX����: " + intCancleClaimTotal);
		
		
		cIInsureCompareSchema.setComCode(iComCode);
		cIInsureCompareSchema.setCompareDate(compareDate);
		cIInsureCompareSchema.setPutConfirm(String.valueOf(intConfirmTotal));
		cIInsureCompareSchema.setPutCancel(String.valueOf(intCancleTotal));
		cIInsureCompareSchema.setPutWithDraw(String.valueOf(intWithDrawTotal));
		cIInsureCompareSchema.setPutReport(String.valueOf(intReportTotal));
		cIInsureCompareSchema.setPutRegistration(String.valueOf(intRegistrationTotal));
		cIInsureCompareSchema.setPutEndClaim(String.valueOf(intEndClaimTotal));
		cIInsureCompareSchema.setPutCancelClaim(String.valueOf(intCancleClaimTotal));
		
		return cIInsureCompareSchema;
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
