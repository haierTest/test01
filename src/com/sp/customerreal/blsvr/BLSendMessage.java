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
		logger.info("BLSendMessage->findFlexSeCmInfo()->获取发送短信内容list->开始");
		List list = null;
		DBSendMessage dbSendMessage = new DBSendMessage();
		DbPool dbpool = new DbPool();
		try{
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			
			list = dbSendMessage.findFlexSeCmInfo(dbpool,messageType);
		}catch(Exception e)
		{
			logger.error("XXXXX真实性错误信息：",e);
			
			String message = "BLSendMessage->findFlexSeCmInfo()->获取发送短信内容list->"+e.getMessage();
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
				logger.error("XXXXX真实性错误信息：",e);
				throw new Exception(e);
			}
		}
		logger.info("BLSendMessage->findFlexSeCmInfo()->获取发送短信内容list->结束");
		return list;
	}
	
	/**河南郑州快赔短信获取 add by linqian 20131213
	 * @return
	 * @throws Exception
	 */
	public List findFlexSeCmInfoSpecific(String messageType) throws Exception
	{
		logger.info("BLSendMessage->findFlexSeCmInfoSpecific()->获取发送短信内容list->开始");
		List list = null;
		DBSendMessage dbSendMessage = new DBSendMessage();
		DbPool dbpool = new DbPool();
		try{
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			
			list = dbSendMessage.findFlexSeCmInfoSpecific(dbpool,messageType);
		}catch(Exception e)
		{
			logger.error("XXXXX真实性错误信息：",e);
			
			String message = "BLSendMessage->findFlexSeCmInfoSpecific()->获取发送短信内容list->"+e.getMessage();
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
				logger.error("XXXXX真实性错误信息：",e);
				throw new Exception(e);
			}
		}
		logger.info("BLSendMessage->findFlexSeCmInfoSpecific()->获取发送短信内容list->结束");
		return list;
	}
	
	/**河南郑州快赔短信获取 add by linqian 20131218
	 * @return
	 * @throws Exception
	 */
	public List findFlexSeCmInfoSpecificH(String messageType,String strBDate,String strNDate) throws Exception
	{
		logger.info("BLSendMessage->findFlexSeCmInfoSpecific()->获取发送短信内容list->开始");
		List list = null;
		DBSendMessage dbSendMessage = new DBSendMessage();
		DbPool dbpool = new DbPool();
		try{
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			
			list = dbSendMessage.findFlexSeCmInfoSpecificH(dbpool,messageType,strBDate,strNDate);
		}catch(Exception e)
		{
			logger.error("XXXXX真实性错误信息：",e);
			
			String message = "BLSendMessage->findFlexSeCmInfoSpecific()->获取发送短信内容list->"+e.getMessage();
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
				logger.error("XXXXX真实性错误信息：",e);
				throw new Exception(e);
			}
		}
		logger.info("BLSendMessage->findFlexSeCmInfoSpecific()->获取发送短信内容list->结束");
		return list;
	}
	
	
	/**
	 * @return
	 * @throws Exception
	 */
	public void insertFlexSeCm(List list) throws Exception
	{
		logger.info("BLSendMessage->insertFlexSeCm()->向短信平台发送短信内容->开始");
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
			logger.error("XXXXX真实性错误信息：",e);
			
			String message = "BLSendMessage->insertFlexSeCm()->向短信平台发送短信内容->"+e.getMessage();
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
				logger.error("XXXXX真实性错误信息：",e);
				throw new Exception(e);
			}
		}
		logger.info("BLSendMessage->insertFlexSeCm()->向短信平台发送短信内容->结束");
	}
	
	/**
	 * 河南XX快赔短信插入方法 add by linqian 20131213
	 * @return
	 * @throws Exception
	 */
	public void insertFlexSeCmSpecific(List list) throws Exception
	{
		logger.info("BLSendMessage->insertFlexSeCmSpecific()->向短信平台发送短信内容->开始");
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
			logger.error("XXXXX真实性错误信息：",e);
			
			String message = "BLSendMessage->insertFlexSeCmSpecific()->向短信平台发送短信内容->"+e.getMessage();
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
				logger.error("XXXXX真实性错误信息：",e);
				throw new Exception(e);
			}
		}
		logger.info("BLSendMessage->insertFlexSeCm()->向短信平台发送短信内容->结束");
	}
	/**
	 * 获取flex_se_cm短信状态
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
			logger.error("XXXXX真实性错误信息：",e);
			
			String message = "BLSendMessage->getMessageStatus()->获取flex_se_cm短信状态->"+e.getMessage();
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
				logger.error("XXXXX真实性错误信息：",e);
				throw new Exception(e);
			}
		}
		return list;
	}
	
	/**
	 * 河南快赔获取flex_se_cm短信状态
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
			logger.error("XXXXX真实性错误信息：",e);
			
			String message = "BLSendMessage->getMessageStatusSpecific()->获取flex_se_cm短信状态->"+e.getMessage();
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
				logger.error("XXXXX真实性错误信息：",e);
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
		logger.info("BLSendMessage->getFlexSeCmSendFlag()->获取发送短信内容list->开始");
		List list = null;
		DBSendMessage dbSendMessage = new DBSendMessage();
		DbPool dbpool = new DbPool();
		try{
			dbpool.open(SysConfig.CONST_DUANXINDATASOURCE);
			
			list = dbSendMessage.getFlexSeCmIniSendFlag(dbpool);
		}catch(Exception e)
		{
			logger.error("XXXXX真实性错误信息：",e);
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
				logger.error("XXXXX真实性错误信息：",e);
				throw new Exception(e);
			}
		}
		logger.info("BLSendMessage->getFlexSeCmSendFlag()->获取发送短信内容list->结束");
		return list;
	}
	
	
	/**
	 * XXXXX100%回访，获取flex_incept_cm短信状态
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
			logger.error("XXXXX真实性错误信息：",e);
			String message = "BLSendMessage->getClaimContext()->获取flex_incept_cm短信回复状态->"+e.getMessage();
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
				logger.error("XXXXX真实性错误信息：",e);
				throw new Exception(e);
			}
		}
		return list;
	}
	
	/**
	 * XX电销满意度，获取flex_incept_cm短信状态
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
			logger.error("XXXXX真实性错误信息：",e);
			String message = "BLSendMessage->getClaimContext()->XX电销满意度获取XXXXX回复状态->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("BLSendMessage.getDxmydContext", message,e,
					TaskMngUtil.CUSTOMERREALDXMYD_JOBNAME);
		}finally
		{
			try {
				if (dbpool != null) {
					dbpool.close();
				}
			} catch (SQLException e) {
				logger.error("XXXXX真实性错误信息：", e);
				throw new Exception(e);
			}
		}
		return list;
	}
}
