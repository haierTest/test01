package com.sp.indiv.ci.interf;

import java.util.Date;
import com.sp.indiv.ci.interf.*;
import com.sp.indiv.ci.schema.*;
import com.sp.indiv.ci.blsvr.*;
import com.sp.utility.database.*;
import com.sp.utility.SysConst;
import com.sp.prpall.blsvr.cb.BLPolicy;
import com.sp.sysframework.common.util.StringUtils;
import com.sp.sysframework.reference.AppConfig;
import com.sp.sysframework.common.datatype.DateTime;
import java.io.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CIInsureCompare 
{
	static Log logger = LogFactory.getLog(CIInsureCompare.class.getName());
	public static void main(String[] args) 
		throws Exception 
	{
		
		if (args.length < 1) 
		{
			showUsage();
			System.exit(0);
		}
		SysConst.init(args[0], false);
		
		BLPolicy blPolicy = new BLPolicy();
		
		BLCIInsureCompanyCompare blCIInsureCompanyCompare 	= new BLCIInsureCompanyCompare();
		BLCIInsureCompare blCIInsureCompare 				= new BLCIInsureCompare();
		BLCIInsureCompareDetail blCIInsureCompareDetail     = new BLCIInsureCompareDetail();
		CIInsureCompareSchema cIInsureCompareSchema 		= new CIInsureCompareSchema();
		CIInsureCompareDetailSchema cIInsureCompareDetailSchema 	= new CIInsureCompareDetailSchema();
		CIInsureCompareEncoder cIInsureCompareEncoder 		= new CIInsureCompareEncoder();
		CIInsureCompareDecoder cIInsureCompareDncoder 		= new CIInsureCompareDecoder();
		CIInsureCompareDetailEncoder cIInsureCompareDetailEncoder 	= new CIInsureCompareDetailEncoder();
		CIInsureCompareDetailDecoder cIInsureCompareDetailDecoder 	= new CIInsureCompareDetailDecoder();
		
		String strComCode 	= "01000000";		
		String request 		= ""; 				
		String context 		= ""; 				
		
		
		DateTime dateTime = new DateTime();
		String strCurrentDate = dateTime.current().addDay(-1).addDay(1).toString(); 

		
		int iMaxSerialNo 		= 0;	
		int iMaxSerialNoClaim 	= 0;	
		int isSuccessFlag		= 2;	
		
		
		
		
		String[] iRequestTypeArray = new String[]{"1","2","3","4","5","6","7"};	
		
		
		String strUrl 			= "";
		String strUser 			= "";
		String strPwd 			= "";
		String strExceptionNo 	= "";
		
		strUrl 	= SysConst.getProperty("CIIDB_URL");
		strUser = SysConst.getProperty("CIIDB_USER");
		strPwd 	= SysConst.getProperty("CIIDB_PASSWORD");
		
		
		
		
		DbPool dbPool = new DbPool();
		
		try 
		{
			dbPool.openOra(strUrl, strUser, strPwd);
			dbPool.beginTransaction();
			
			
			iMaxSerialNo = blCIInsureCompanyCompare.getMaxSerialNo(dbPool, strCurrentDate);
			iMaxSerialNoClaim = blCIInsureCompanyCompare.getMaxSerialNoClaim(dbPool, strCurrentDate);
			
			
			String strCompareDate = dateTime.current().addDay(-1).toString();
			
			cIInsureCompareSchema.setSerialNo(String.valueOf(iMaxSerialNo + 1));
			strCurrentDate = dateTime.current().getYear() 
			   				+ ":" + dateTime.current().getMonth() 
			   				+ ":" + dateTime.current().getDate() 
			   				+ ":" + dateTime.current().getHour() 
			   				+ ":" + dateTime.current().getMinute()
			   				+ ":" + dateTime.current().getSecond();
			
			
			cIInsureCompareSchema.setOperateDate(correctDate(strCurrentDate));
			blCIInsureCompare.setArr(cIInsureCompareSchema);
			blPolicy.setBLCIInsureCompare(blCIInsureCompare);
			
			
			setCIInsureCompanyCompareBusinessPolicy(dbPool, strCompareDate, strCurrentDate, iMaxSerialNo + 1, strComCode);
			
			setCIInsureCompanyCompareBusinessEndorse(dbPool, strCompareDate, strCurrentDate, iMaxSerialNo + 1, strComCode);
			
			
			request = cIInsureCompareEncoder.encode(dbPool, blPolicy, strCompareDate, iMaxSerialNo + 1, iMaxSerialNoClaim, strComCode);
			
			logger.info("============XXXXXƽ̨���ݱȶԽӿ�request: " + request);
			
			
			context 	= EbaoProxy.getInstance().request(request, strComCode);
			context 	= StringUtils.replace(context, "GBK", "GB2312");
			
			logger.info("============XXXXXƽ̨���ݱȶԽӿ�contet: " + context);
			
			
			
			isSuccessFlag = cIInsureCompareDncoder.decode(dbPool, blPolicy, context, iMaxSerialNo + 1, strComCode);
			
			
			blPolicy.setCIInsureCompareDetail(blCIInsureCompareDetail);
			for(int i = 0; i < iRequestTypeArray.length; i++)
			{
				String iRequestType = iRequestTypeArray[i].trim();
				context = "";
				request = "";
				strCurrentDate = dateTime.current().getYear() 
   								+ ":" + dateTime.current().getMonth() 
   								+ ":" + dateTime.current().getDate() 
   								+ ":" + dateTime.current().getHour() 
   								+ ":" + dateTime.current().getMinute() 
   								+ ":" + dateTime.current().getSecond();
				
				request = cIInsureCompareDetailEncoder.encode(dbPool, blPolicy, strCompareDate, 
															  iMaxSerialNo + 1, iMaxSerialNoClaim, 
															  iRequestType, strComCode);
				
				logger.info("=====XXXXX��ǿXXXXXƽ̨-���ݱȶ���ϸ��ѯ���ش�-request: " + request);
				
				
				context = EbaoProxy.getInstance().request(request, strComCode);
				context 	= StringUtils.replace(context, "GBK", "GB2312");
				
				logger.info("=====XXXXX��ǿXXXXXƽ̨-���ݱȶ���ϸ��ѯ���ش�-context: " + context);
				
				
				cIInsureCompareDetailDecoder.decode(dbPool, blPolicy, context, iRequestType, iMaxSerialNo + 1,
													strComCode, strCurrentDate, strCompareDate);
			}
			
			blCIInsureCompareDetail.save(dbPool);
			
			dbPool.commitTransaction();
		}
		catch(Exception ex)
		{
			
			logger.error("===========XXXXX��ǿXXXXXƽ̨-���ݱȶ�����ϸ��ѯ��" + ex.getMessage());
			
			dbPool.rollbackTransaction();
			throw ex;
		}
		finally
		{
			dbPool.close();
		}
	}
	
	/**
	 * ��ҵ�����CIInsureCompanyCompare����������
	 * @param dbPool
	 * @param compareDate
	 * @throws Exception
	 */
	private static void setCIInsureCompanyCompareBusinessPolicy(DbPool dbPool, 
														  		String iCompareDate, 
														  		String iCurrentDate, 
														  		int iMaxSerialNo,
														  		String iComCode)
		throws Exception
	{
		BLCIInsureCompanyCompare blCIInsureCompanyCompare = new BLCIInsureCompanyCompare();
		
		blCIInsureCompanyCompare.setCompanyComparePolicy(dbPool, iCompareDate, iCurrentDate, iMaxSerialNo, iComCode);
	}
	
	/**
	 * ��ҵ�����CIInsureCompanyCompare����������
	 * @param dbPool
	 * @param compareDate
	 * @throws Exception
	 */
	private static void setCIInsureCompanyCompareBusinessEndorse(DbPool dbPool, 
														  		String iCompareDate, 
														  		String iCurrentDate, 
														  		int iMaxSerialNo,
														  		String iComCode)
		throws Exception
	{
		BLCIInsureCompanyCompare blCIInsureCompanyCompare = new BLCIInsureCompanyCompare();
		
		blCIInsureCompanyCompare.setCompanyCompareEndorse(dbPool, iCompareDate, iCurrentDate, iMaxSerialNo, iComCode);
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

    /**
	 * ��������ʱ���ʽ
	 *
	 * @param dateString
	 *            ����ǰ������ʱ��
	 * @return �����������ʱ��
	 */
    private static String correctDateTime(String dateString) {
        String result = correctDate(dateString);
        result = StringUtils.replace(dateString, " ", "");
        result = StringUtils.replace(dateString, ":", "");
        return result;
    }
	
	/**
	 * ��ʾ�÷�
	 */
	private static void showUsage() {
		
		logger.info("Usage: java CITimeExecute {conf}");
		logger.info("  conf: CIIConstConfig.xml file");
		
		
		
	}
}
