package com.sp.indiv.ci.interf;

import java.util.Iterator;
import java.util.Vector;
import java.util.ArrayList;
import java.sql.ResultSet;

import com.sp.indiv.ci.blsvr.BLCIInsureValid;
import com.sp.indiv.ci.dbsvr.DBCIInsureValid;
import com.sp.indiv.ci.schema.CIInsureValidSchema;
import com.sp.prpall.blsvr.cb.BLPolicy;
import com.sp.sysframework.common.util.StringUtils;
import com.sp.sysframework.reference.AppConfig;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * XX��־ʵʱ�ϴ��ӿ�
 * 
 */
public class RepeatSendCISignNoRealTime{

	static Log logger = LogFactory.getLog(RepeatSendCISignNoRealTime.class.getName());
	/**
	 * @throws Exception
	 * 
	 */
	public static void main(String[] args) 
		throws Exception 
	{
		
		if (args.length < 1) 
		{
			showUsage();
			System.exit(0);
		}
		SysConst.init(args[0], false);
		
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
		    repeatSendCISignNo(dbPool, "");
		}
		catch(Exception ex)
		{
			
			logger.info("===========XXXXX��ǿXXXXXƽ̨-���ݱȶ�����ϸ��ѯ��" + ex.getMessage());
			
			dbPool.rollbackTransaction();
			throw ex;
		}
		finally
		{
			dbPool.close();
		}
	}

	/**
	 * XX��־ʵʱ�ϴ��ӿ�
	 * 
	 * @param dbPool
	 *            ������ͬһ�����null
	 * @param columnName
	 *            ����������Ϊ��
	 * @param certiNo
	 *            �еĲ�ѯ������
	 * @throws Exception
	 */
	public static void repeatSendCISignNo(DbPool dbPool, String conditions)
			throws Exception 
	{
		String strWhere = ""; 
		String request 	= ""; 
		String contet 	= ""; 
		String comCode 	= "";
		int count 		= 0;

		
		
		
		
		
		
			 strWhere = "SELECT * FROM CIINSUREVALID " 			 + 
			 			 "WHERE POLICYNO IN(SELECT A.POLICYNO "  + 
			 			  "FROM CIINSUREVALID A, PRPCITEMCAR B, PRPCMAIN C " +   
                         "WHERE A.POLICYNO = B.POLICYNO " 		 +  
                           "AND B.POLICYNO = C.POLICYNO "        +
                           "AND SUBSTR(C.COMCODE, 0, 2) = '01' " + 
                           "AND B.CARDEALERCODE IS NOT NULL " 	 +  
                           "AND ((SUBSTR(A.FLAG, 1, 1) = '0' " 	 +
                           "AND  SUBSTR(A.FLAG, 4, 1) != '0') "  +
                            "OR (SUBSTR(A.FLAG, 1, 1) = '0' " 	 + 
                           "AND  SUBSTR(A.FLAG, 3, 1) = '0'  " 	 +
                           "AND  SUBSTR(A.FLAG, 4, 1) != '0'))) ";
        
		if(conditions != null && !conditions.equals(""))
		{
			strWhere += " AND " + conditions;
		}
		
		logger.info("XX��־�ϴ���SQL��䣺" + strWhere);
		
		BLCIInsureValid blCIInsureValid = getRepeatCollection(dbPool, strWhere);
		CIInsureValidSchema cIInsureValidSchema = null; 	
		CISignNoRealTimeDecoder cISignNoRealTimeDecoder = new CISignNoRealTimeDecoder();
		CISignNoRealTimeEncoder cISignNoRealTimeEncoder = new CISignNoRealTimeEncoder();
		
		BLPolicy blPolicy = null;
		
		if(blCIInsureValid != null)
		{
			
			logger.info("����XX��־�ϴ���-blCIInsureValid.getSize(): " + blCIInsureValid.getSize());
			
			for (int i = 0; i < blCIInsureValid.getSize(); i++) 
			{
				
				count += 1; 
				cIInsureValidSchema = blCIInsureValid.getArr(i);
				blPolicy 			= getBLPolicyDate(dbPool, cIInsureValidSchema.getPolicyNo());
				comCode 			= cIInsureValidSchema.getComCode();
				request 			= cISignNoRealTimeEncoder.encode(dbPool, blPolicy);
				
				logger.info("����XX��־�ϴ���-request: " + request);
				
				
				contet 				= EbaoProxy.getInstance().request(request, comCode);
				contet 	= StringUtils.replace(contet, "GBK", "GB2312");
				
				logger.info("����XX��־�ϴ���-contet: " + contet);
				
				cISignNoRealTimeDecoder.decode(dbPool, blPolicy, contet);
			}
		}
		
		logger.info("��������XX��־�����ܹ�������  " + count + "  ��!");
		
	}

	/**
	 * ������еĶ���
	 * 
	 * @param dbPool
	 * @param querySQL
	 * @return
	 * @throws Exception
	 */
	private static BLCIInsureValid getRepeatCollection(DbPool dbPool, String strWhere)
			throws Exception 
	{
		BLCIInsureValid blCIInsureValid = new BLCIInsureValid();
		DBCIInsureValid dbCIInsureValid = new DBCIInsureValid();
		CIInsureValidSchema schema 		= null;

		if (dbPool != null) 
		{
			
			Vector vector = dbCIInsureValid.findByConditions(dbPool, strWhere);
			Iterator it = vector.iterator();
			while(it.hasNext()) 
			{
				schema = (CIInsureValidSchema) it.next();
				blCIInsureValid.setArr(schema);
			}
		}
		else 
		{
			DbPool newDbPool = new DbPool();
			try 
			{
				newDbPool.open(SysConfig.CONST_DDCCDATASOURCE);
				newDbPool.beginTransaction();
				Vector vector = dbCIInsureValid.findByConditions(newDbPool, strWhere);
				Iterator it = vector.iterator();
				while (it.hasNext()) 
				{
					schema = (CIInsureValidSchema) it.next();
					blCIInsureValid.setArr(schema);
				}
				newDbPool.commitTransaction();
			} 
			catch (Exception exception) 
			{
				newDbPool.rollbackTransaction();
				exception.printStackTrace();
			} 
			finally 
			{
				newDbPool.close();
			}
		}
		return blCIInsureValid;
	}

	/*
	 * ���Policy�����
	 */
	private static BLPolicy getBLPolicyDate(DbPool dbPool, String policyNo)
			throws Exception 
	{
		BLPolicy blPolicy = new BLPolicy();
		if (dbPool != null) 
		{
			
			blPolicy.getData(dbPool, policyNo);
		} 
		else 
		{
			DbPool newDbPool = new DbPool();
			try 
			{
				newDbPool.open(SysConfig.CONST_DDCCDATASOURCE);
				newDbPool.beginTransaction();

				
				blPolicy.getData(newDbPool, policyNo);

				newDbPool.commitTransaction();
			} 
			catch (Exception exception) 
			{
				newDbPool.rollbackTransaction();
				exception.printStackTrace();
			} 
			finally 
			{
				newDbPool.close();
			}
		}
		return blPolicy;
	}
	
	/**
	 * ��ʾ�÷�
	 */
	private static void showUsage() {
		
		logger.info("Usage: java CITimeExecute {conf}");
		logger.info("  conf: CIIConstConfig.xml file");
		
	}
}
