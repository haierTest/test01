package com.sp.customerreal.blsvr;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sp.customerreal.dbsvr.DBSendMessage;
import com.sp.customerreal.schema.FlexSeCmSchema;
import com.sp.customerreal.schema.PrpCustomerMessageSchema;
import com.sp.taskmng.util.TaskMngUtil;
import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;

public class BLSendMessage {
	private final Log logger = LogFactory.getLog(getClass());
	
	/**
	 * @return
	 * @throws Exception
	 */
	public List findFlexSeCmInfo(String messageType) throws Exception
	{
		logger.info("BLSendMessage->findFlexSeCmInfo()->��ȡ���Ͷ�������list->��ʼ");
		List list = null;
		DBSendMessage dbSendMessage = new DBSendMessage();
		DbPool dbpool = new DbPool();
		try{
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			
			list = dbSendMessage.findFlexSeCmInfo(dbpool,messageType);
		}catch(Exception e)
		{
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			
			String message = "BLSendMessage->findFlexSeCmInfo()->��ȡ���Ͷ�������list->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("BLSendMessage.findFlexSeCmInfo", message,e,
					TaskMngUtil.CUSTOMERREALRUN_JOBNAME);
			
		}finally
		{
			try {
				if(dbpool!=null)
				{
					dbpool.close();
				}
			} catch (SQLException e) 
			{
				logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
				throw new Exception(e);
			}
		}
		logger.info("BLSendMessage->findFlexSeCmInfo()->��ȡ���Ͷ�������list->����");
		return list;
	}
	
	/**����֣�ݿ�����Ż�ȡ add by linqian 20131213
	 * @return
	 * @throws Exception
	 */
	public List findFlexSeCmInfoSpecific(String messageType) throws Exception
	{
		logger.info("BLSendMessage->findFlexSeCmInfoSpecific()->��ȡ���Ͷ�������list->��ʼ");
		List list = null;
		DBSendMessage dbSendMessage = new DBSendMessage();
		DbPool dbpool = new DbPool();
		try{
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			
			list = dbSendMessage.findFlexSeCmInfoSpecific(dbpool,messageType);
		}catch(Exception e)
		{
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			
			String message = "BLSendMessage->findFlexSeCmInfoSpecific()->��ȡ���Ͷ�������list->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("BLSendMessage.findFlexSeCmInfoSpecific", message,e,
					TaskMngUtil.CUSTOMERREALRUN_JOBNAME);
			
		}finally
		{
			try {
				if(dbpool!=null)
				{
					dbpool.close();
				}
				if(dbpool!=null)
				{
					dbpool.close();
				}
			} catch (SQLException e) 
			{
				logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
				throw new Exception(e);
			}
		}
		logger.info("BLSendMessage->findFlexSeCmInfoSpecific()->��ȡ���Ͷ�������list->����");
		return list;
	}
	
	/**����֣�ݿ�����Ż�ȡ add by linqian 20131218
	 * @return
	 * @throws Exception
	 */
	public List findFlexSeCmInfoSpecificH(String messageType,String strBDate,String strNDate) throws Exception
	{
		logger.info("BLSendMessage->findFlexSeCmInfoSpecific()->��ȡ���Ͷ�������list->��ʼ");
		List list = null;
		DBSendMessage dbSendMessage = new DBSendMessage();
		DbPool dbpool = new DbPool();
		try{
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			
			list = dbSendMessage.findFlexSeCmInfoSpecificH(dbpool,messageType,strBDate,strNDate);
		}catch(Exception e)
		{
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			
			String message = "BLSendMessage->findFlexSeCmInfoSpecific()->��ȡ���Ͷ�������list->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("BLSendMessage.findFlexSeCmInfoSpecific", message,e,
					TaskMngUtil.CUSTOMERREALRUN_JOBNAME);
			
		}finally
		{
			try {
				if(dbpool!=null)
				{
					dbpool.close();
				}
				if(dbpool!=null)
				{
					dbpool.close();
				}
			} catch (SQLException e) 
			{
				logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
				throw new Exception(e);
			}
		}
		logger.info("BLSendMessage->findFlexSeCmInfoSpecific()->��ȡ���Ͷ�������list->����");
		return list;
	}
	
	
	/**
	 * @return
	 * @throws Exception
	 */
	public void insertFlexSeCm(List list) throws Exception
	{
		logger.info("BLSendMessage->insertFlexSeCm()->�����ƽ̨���Ͷ�������->��ʼ");
		DBSendMessage dbSendMessage = new DBSendMessage();
		DbPool dbpool = new DbPool();
		int coutntNum = 0;
		try{
			dbpool.open(SysConfig.CONST_DUANXINDATASOURCE);
			
			if(list==null || list.size()<1)
				return ;
			FlexSeCmSchema flexSeCmSchema = null;
			for(int i=0;i<list.size();i++)
			{
				flexSeCmSchema = (FlexSeCmSchema)list.get(i);
				coutntNum = dbSendMessage.getCount(dbpool,flexSeCmSchema);
				if(coutntNum>=1)
					dbSendMessage.update(flexSeCmSchema);
				else
				dbSendMessage.insertFlexSeCm(dbpool,flexSeCmSchema);
				
			}
		}catch(Exception e)
		{
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			
			String message = "BLSendMessage->insertFlexSeCm()->�����ƽ̨���Ͷ�������->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("BLSendMessage.insertFlexSeCm", message, e,
					TaskMngUtil.CUSTOMERREALRUN_JOBNAME);
			
		}finally
		{
			try {
				if(dbpool!=null)
				{
					dbpool.close();
				}
				if(dbpool!=null)
				{
					dbpool.close();
				}
			} catch (SQLException e) 
			{
				logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
				throw new Exception(e);
			}
		}
		logger.info("BLSendMessage->insertFlexSeCm()->�����ƽ̨���Ͷ�������->����");
	}
	
	/**
	 * ����XX������Ų��뷽�� add by linqian 20131213
	 * @return
	 * @throws Exception
	 */
	public void insertFlexSeCmSpecific(List list) throws Exception
	{
		logger.info("BLSendMessage->insertFlexSeCmSpecific()->�����ƽ̨���Ͷ�������->��ʼ");
		DBSendMessage dbSendMessage = new DBSendMessage();
		DbPool dbpool = new DbPool();
		int coutntNum = 0;
		try{
			dbpool.open(SysConfig.CONST_DUANXINDATASOURCE);
			
			if(list==null || list.size()<1)
				return ;
			FlexSeCmSchema flexSeCmSchema = null;
			for(int i=0;i<list.size();i++)
			{
				flexSeCmSchema = (FlexSeCmSchema)list.get(i);
				coutntNum = dbSendMessage.getCount(dbpool,flexSeCmSchema);
				if(coutntNum>=1)
					dbSendMessage.updateSpecific(flexSeCmSchema);
				else
				dbSendMessage.insertFlexSeCm(dbpool,flexSeCmSchema);
				
			}
		}catch(Exception e)
		{
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			
			String message = "BLSendMessage->insertFlexSeCmSpecific()->�����ƽ̨���Ͷ�������->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("BLSendMessage.insertFlexSeCSpecificm", message, e,
					TaskMngUtil.CUSTOMERREALRUN_JOBNAME);
			
		}finally
		{
			try {
				if(dbpool!=null)
				{
					dbpool.close();
				}
				if(dbpool!=null)
				{
					dbpool.close();
				}
			} catch (SQLException e) 
			{
				logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
				throw new Exception(e);
			}
		}
		logger.info("BLSendMessage->insertFlexSeCm()->�����ƽ̨���Ͷ�������->����");
	}
	/**
	 * ��ȡflex_se_cm����״̬
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public List getMessageStatus(List list) throws Exception
	{
		if(list==null || list.size()<1)
			return null;
		DBSendMessage dbSendMessage = new DBSendMessage();
		DbPool dbpool = new DbPool();
		try{
			dbpool.open(SysConfig.CONST_DUANXINDATASOURCE);
			PrpCustomerMessageSchema prpCustomerMessageSchema = null;
			for(int i=0;i<list.size();i++)
			{
				prpCustomerMessageSchema = (PrpCustomerMessageSchema)list.get(i);
				
				
				prpCustomerMessageSchema = dbSendMessage.getMessageStatusCMRE(dbpool,prpCustomerMessageSchema);
			}
		}catch(Exception e)
		{
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			
			String message = "BLSendMessage->getMessageStatus()->��ȡflex_se_cm����״̬->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("BLSendMessage.getMessageStatus", message,e,
					TaskMngUtil.CUSTOMERREALCOMPLETE_JOBNAME);
			
		}finally
		{
			try {
				if(dbpool!=null)
				{
					dbpool.close();
				}
				if(dbpool!=null)
				{
					dbpool.close();
				}
			} catch (SQLException e) 
			{
				logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
				throw new Exception(e);
			}
		}
		return list;
	}
	
	/**
	 * ���Ͽ����ȡflex_se_cm����״̬
	 * add by linqian 20131216
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public List getMessageStatusSpecific(List list) throws Exception
	{
		if(list==null || list.size()<1)
			return null;
		DBSendMessage dbSendMessage = new DBSendMessage();
		DbPool dbpool = new DbPool();
		try{
			dbpool.open(SysConfig.CONST_DUANXINDATASOURCE);
			PrpCustomerMessageSchema prpCustomerMessageSchema = null;
			for(int i=0;i<list.size();i++)
			{
				prpCustomerMessageSchema = (PrpCustomerMessageSchema)list.get(i);
				
				prpCustomerMessageSchema = dbSendMessage.getMessageStatusHNSpecific(dbpool,prpCustomerMessageSchema);
			}
		}catch(Exception e)
		{
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			
			String message = "BLSendMessage->getMessageStatusSpecific()->��ȡflex_se_cm����״̬->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("BLSendMessage.getMessageStatusSpecific", message,e,
					TaskMngUtil.CUSTOMERREALCOMPLETE_JOBNAME);
			
		}finally
		{
			try {
				if(dbpool!=null)
				{
					dbpool.close();
				}
				if(dbpool!=null)
				{
					dbpool.close();
				}
			} catch (SQLException e) 
			{
				logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
				throw new Exception(e);
			}
		}
		return list;
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	public List getFlexSeCmIniSendFlag() throws Exception
	{
		logger.info("BLSendMessage->getFlexSeCmSendFlag()->��ȡ���Ͷ�������list->��ʼ");
		List list = null;
		DBSendMessage dbSendMessage = new DBSendMessage();
		DbPool dbpool = new DbPool();
		try{
			dbpool.open(SysConfig.CONST_DUANXINDATASOURCE);
			
			list = dbSendMessage.getFlexSeCmIniSendFlag(dbpool);
		}catch(Exception e)
		{
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
		}finally
		{
			try {
				if(dbpool!=null)
				{
					dbpool.close();
				}
				if(dbpool!=null)
				{
					dbpool.close();
				}
			} catch (SQLException e) 
			{
				logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
				throw new Exception(e);
			}
		}
		logger.info("BLSendMessage->getFlexSeCmSendFlag()->��ȡ���Ͷ�������list->����");
		return list;
	}
	
	
	/**
	 * XXXXX100%�طã���ȡflex_incept_cm����״̬
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public List getClaimContext(List list) throws Exception
	{
		if(list==null || list.size()<1)
			return null;
		DBSendMessage dbSendMessage = new DBSendMessage();
		DbPool dbpool = new DbPool();
		try{
			dbpool.open(SysConfig.CONST_DUANXINDATASOURCE);
			PrpCustomerMessageSchema prpCustomerMessageSchema = null;
			for(int i=0;i<list.size();i++)
			{
				prpCustomerMessageSchema = (PrpCustomerMessageSchema)list.get(i);
				prpCustomerMessageSchema = dbSendMessage.getClaimContext(dbpool,prpCustomerMessageSchema);
			}
		}catch(Exception e)
		{
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			String message = "BLSendMessage->getClaimContext()->��ȡflex_incept_cm���Żظ�״̬->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("BLSendMessage.getMessageStatus", message,e,
					TaskMngUtil.CLAIMISSATISFIED_JOBNAME);
		}finally
		{
			try {
				if(dbpool!=null)
				{
					dbpool.close();
				}
				if(dbpool!=null)
				{
					dbpool.close();
				}
			} catch (SQLException e) 
			{
				logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
				throw new Exception(e);
			}
		}
		return list;
	}
	
	/**
	 * XX��������ȣ���ȡflex_incept_cm����״̬
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public List getDxmydContext(List list) throws Exception
	{
		if(list==null || list.size()<1)
			return null;
		DBSendMessage dbSendMessage = new DBSendMessage();
		DbPool dbpool = new DbPool();
		try{
			dbpool.open(SysConfig.CONST_DUANXINDATASOURCE);
			PrpCustomerMessageSchema prpCustomerMessageSchema = null;
			for(int i=0;i<list.size();i++)
			{
				prpCustomerMessageSchema = (PrpCustomerMessageSchema)list.get(i);
				prpCustomerMessageSchema = dbSendMessage.getDxmydContext(dbpool,prpCustomerMessageSchema);
			}
		}catch(Exception e)
		{
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			String message = "BLSendMessage->getClaimContext()->XX��������Ȼ�ȡXXXXX�ظ�״̬->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("BLSendMessage.getDxmydContext", message,e,
					TaskMngUtil.CUSTOMERREALDXMYD_JOBNAME);
		}finally
		{
			try {
				if (dbpool != null) {
					dbpool.close();
				}
			} catch (SQLException e) {
				logger.error("XXXXX��ʵ�Դ�����Ϣ��", e);
				throw new Exception(e);
			}
		}
		return list;
	}
}
