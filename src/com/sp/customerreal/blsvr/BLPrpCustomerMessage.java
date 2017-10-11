package com.sp.customerreal.blsvr;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sp.customerreal.dbsvr.DBPrpCustomerMessage;
import com.sp.customerreal.schema.CustomerClaimSchema;
import com.sp.customerreal.schema.CustomerPolicySchema;
import com.sp.customerreal.schema.FlexSeCmSchema;
import com.sp.customerreal.schema.PrpCustomerMessageMobileSchema;
import com.sp.customerreal.schema.PrpCustomerMessageSchema;
import com.sp.taskmng.util.TaskMngUtil;
import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;

public class BLPrpCustomerMessage {
	private final Log logger = LogFactory.getLog(getClass());
	
	/**
	 * 取XX新单团个单数据，写入PrpCustomerMessage
	 * @throws Exception 
	 */
	public List createPrpCustomerMessage(String hour,String strBDate,String strNDate) throws Exception
	{
		logger.info("BLPrpCustomerMessage->createPrpCustomerMessage()->向打印池取承XX子->开始"+hour+"点");
		DBPrpCustomerMessage dbPrpCustomerMessage = new DBPrpCustomerMessage();
		DbPool dbpool = new DbPool();
		List list = null;

		try{
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			
			Map map = beforeGetPrpCustomerMessagePolicy(hour, strBDate,strNDate);
				
				list = dbPrpCustomerMessage.findPrpCustomerMessageInfo(dbpool,map);
		}catch(Exception e)
		{
			logger.error("XXXXX真实性错误信息：",e);
			
			String message = "BLPrpCustomerMessage->createPrpCustomerMessage()->取XX新单团个单数据，写入PrpCustomerMessage->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("BLPrpCustomerMessage.createPrpCustomerMessage", message,e,
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
		logger.info("BLPrpCustomerMessage->createPrpCustomerMessage()->向打印池取承XX子->结束"+hour+"点");
		return list;
	}
	
	
	/**
	 * 取XX新单团个单数据，写入PrpCustomerMessage
	 * @throws Exception 
	 */
	public List createPrpCustomerMessageGX40(String hour,String strBDate,String strNDate) throws Exception
	{
		logger.info("BLPrpCustomerMessage->createPrpCustomerMessage()->向打印池取承XX子->开始"+hour+"点");
		DBPrpCustomerMessage dbPrpCustomerMessage = new DBPrpCustomerMessage();
		DbPool dbpool = new DbPool();
		List list = null;

		try{
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			
			Map map = beforeGetPrpCustomerMessagePolicy(hour, strBDate,strNDate);
				
				list = dbPrpCustomerMessage.findPrpCustomerMessageInfoGX(dbpool,map);
		}catch(Exception e)
		{
			logger.error("XXXXX真实性错误信息：",e);
			
			String message = "BLPrpCustomerMessage->createPrpCustomerMessage()->取XX新单团个单数据，写入PrpCustomerMessage->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("BLPrpCustomerMessage.createPrpCustomerMessage", message,e,
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
		logger.info("BLPrpCustomerMessage->createPrpCustomerMessage()->向打印池取承XX子->结束"+hour+"点");
		return list;
	}
	
	/**
	 * 取XX新单团个单数据，Servlet参数41时执行，实时获取数据使用，写入PrpCustomerMessage
	 * @throws Exception 
	 */
	public List createPrpCustomerMessageGX(String hour,String strBDate,String strNDate) throws Exception
	{
		logger.info("BLPrpCustomerMessage->createPrpCustomerMessage()->向打印池取承XX子->开始"+hour+"点");
		DBPrpCustomerMessage dbPrpCustomerMessage = new DBPrpCustomerMessage();
		DbPool dbpool = new DbPool();
		List list = null;

		try{
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			
			Map map = beforeGetPrpCustomerMessagePolicyGX(hour, strBDate,strNDate);
			
			list = dbPrpCustomerMessage.findPrpCustomerMessageInfoGX(dbpool,map);
		}catch(Exception e)
		{
			logger.error("XXXXX真实性错误信息：",e);
			
			String message = "BLPrpCustomerMessage->createPrpCustomerMessage()->取XX新单团个单数据，写入PrpCustomerMessage->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("BLPrpCustomerMessage.createPrpCustomerMessage", message,e,
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
		logger.info("BLPrpCustomerMessage->createPrpCustomerMessage()->向打印池取承XX子->结束"+hour+"点");
		return list;
	}
	
	/**
	 * 把XX新单团个单数据，写入PrpCustomerMessage
	 * @throws Exception 
	 */
	public void insertPrpCustomerMessage(List list) throws Exception
	{
		logger.info("BLPrpCustomerMessage->insertPrpCustomerMessage()->把XX数据插入PrpCustomerMessage->开始");
		DBPrpCustomerMessage dbPrpCustomerMessage = new DBPrpCustomerMessage();
		DbPool dbpool = new DbPool();
		try{
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			
			if(list==null || list.size()<1)
				return ;
			PrpCustomerMessageSchema prpCustomerMessageSchema = null;
			for(int i=0;i<list.size();i++)
			{
				prpCustomerMessageSchema = (PrpCustomerMessageSchema)list.get(i);
				
				int count = 0;
				int count1 = 0;
				
				if("1".equals(prpCustomerMessageSchema.getGXCondition()))
				{
			    count1 = dbPrpCustomerMessage.getCountBusinessNo(dbpool,prpCustomerMessageSchema.getBusinessNo());
				}
				else{
				count = dbPrpCustomerMessage.getCountPolicy(dbpool,prpCustomerMessageSchema.getPolicyNo());
				}
				
				if(count==0&&count1==0)
				{
				
				String flowId = getShortinsFlowId(dbpool);
				
				prpCustomerMessageSchema.setId(flowId);
				
				String sendSeq = getSendSeq(flowId);
				
				prpCustomerMessageSchema.setSendSeq(sendSeq);
				    
					if (prpCustomerMessageSchema.getComCode() != null&& !prpCustomerMessageSchema.getComCode().equals("")) {
						if (prpCustomerMessageSchema.getComCode().startsWith("0651")) {
							
							String flowId0 = getShortinsFlowId(dbpool);
							String sendSeqSpecific = getSendSeq(flowId0);
							
							prpCustomerMessageSchema.setSendSeqSpecific(sendSeqSpecific);
						}
					}
				
				dbPrpCustomerMessage.insertPrpCustomerMessage(dbpool,prpCustomerMessageSchema);
				}
			}
		}catch(Exception e)
		{
			logger.error("XXXXX真实性错误信息：",e);
			
			String message = "BLPrpCustomerMessage->insertPrpCustomerMessage()->把XX新单团个单数据，写入PrpCustomerMessage->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("BLPrpCustomerMessage.insertPrpCustomerMessage", message,e,
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
		logger.info("BLPrpCustomerMessage->insertPrpCustomerMessage()->把XX数据插入PrpCustomerMessage->结束");
	}
	/**
	 * @param hour
	 * @return
	 * @throws Exception
	 */
	private Map beforeGetPrpCustomerMessagePolicy(String hour,String strBDate,String strNDate) throws Exception 
	{
		String startTime = "";
		String endTime = "";
		String nowDay = "";
		String beforeDay = "";
		String createTime="";
		if(strBDate==null || "".equals(strBDate)||strNDate==null || "".equals(strNDate))
		{
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			nowDay = simpleDateFormat.format(new Date()); 
			GregorianCalendar gregorianCalendar = new GregorianCalendar();
			gregorianCalendar.setTime(simpleDateFormat.parse(nowDay));
			gregorianCalendar.add(GregorianCalendar.DATE, -1);
			beforeDay = simpleDateFormat.format(gregorianCalendar.getTime());  
			
			logger.info("beforeDay:"+beforeDay+"    nowDay:"+nowDay+"    hour:"+hour);
			if("9".equals(hour))
			{
				startTime = beforeDay + " 18:00:00";
				endTime = nowDay + " 8:59:59";
				createTime=nowDay + " 9:10:10";
			}
			
			if("12".equals(hour))
			{
				startTime = nowDay + " 9:00:00";
				endTime = nowDay + " 11:59:59";
				createTime=nowDay + " 12:10:10";
			}
			
			if("18".equals(hour))
			{
				startTime = nowDay + " 12:00:00";
				endTime = nowDay + " 17:59:59";
				createTime=nowDay + " 18:10:10";
			}
			
		}
		else
		{
			beforeDay = strBDate;
			nowDay = strNDate;
			
			if("9".equals(hour))
			{
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
				GregorianCalendar gregorianCalendar = new GregorianCalendar();
		        gregorianCalendar.setTime(simpleDateFormat.parse(strBDate));
		        gregorianCalendar.add(GregorianCalendar.DATE, -1);
				beforeDay = simpleDateFormat.format(gregorianCalendar.getTime());  
				
				startTime = beforeDay + " 18:00:00";
				endTime = nowDay + " 8:59:59";
				createTime=nowDay + " 9:10:10";
			}
			
			if("12".equals(hour))
			{
				startTime = beforeDay + " 9:00:00";
				endTime = nowDay + " 11:59:59";
				createTime=nowDay + " 12:10:10";
			}
			
			if("18".equals(hour))
			{
				startTime = beforeDay + " 12:00:00";
				endTime = nowDay + " 17:59:59";
				createTime=nowDay + " 18:10:10";
			}
		}
		
		logger.info("startTime:"+startTime+"    endTime:"+endTime+"  createTime:"+createTime+"   hour:"+hour);
		Map map = new HashMap();
		map.put("startTime",startTime);
		map.put("endTime",endTime);
		map.put("createTime",createTime);
		return map;
	}
	
	/**
	 * @param hour
	 * @return
	 * @throws Exception
	 */
	private Map afterGetPrpCustomerMessagePolicy(String hour,String strBDate,String strNDate) throws Exception 
	{
		String startTime = "";
		String endTime = "";
		String nowDay = "";
		String beforeDay = "";
		if(strBDate==null || "".equals(strBDate)||strNDate==null || "".equals(strNDate))
		{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			nowDay = simpleDateFormat.format(new Date()); 
		GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(simpleDateFormat.parse(nowDay));
        gregorianCalendar.add(GregorianCalendar.DATE, -1);
			beforeDay = simpleDateFormat.format(gregorianCalendar.getTime());  
			nowDay = beforeDay;
		}
		else
		{
			beforeDay = strBDate;
			nowDay = strNDate;
		}
		logger.info("beforeDay:"+beforeDay+"    nowDay:"+nowDay+"    hour:"+hour);
		
		if("9".equals(hour) || "10".equals(hour) || "11".equals(hour))
		{
			startTime = beforeDay + " 9:00:00";
			endTime = nowDay + " 11:59:59";
		}
		
		else if("12".equals(hour) || "13".equals(hour) || "14".equals(hour) || "15".equals(hour) || "16".equals(hour) || "17".equals(hour))
		{
			startTime = beforeDay + " 12:00:00";
			endTime = nowDay + " 17:59:59";
		}
		
		else if("18".equals(hour) || "19".equals(hour) || "20".equals(hour) || "21".equals(hour) || "22".equals(hour) || "23".equals(hour))
		{
			startTime = beforeDay + " 18:00:00";
			endTime = nowDay + " 23:59:59";
		}else {
			startTime = beforeDay + " 00:00:00";
			endTime = nowDay + " 23:59:59";
		}
		Map map = new HashMap();
		map.put("startTime",startTime);
		map.put("endTime",endTime);
		logger.info("startTime:"+startTime+"    endTime:"+endTime);
		return map;
	}
	
	/**实时获取时间
	 * @param hour
	 * @return
	 * @throws Exception
	 */
	private Map beforeGetPrpCustomerMessagePolicyGX(String hour,String strBDate,String strNDate) throws Exception 
	{
		String startTime = "";
		String endTime = "";
		String nowDay = "";
		String beforeDay = "";
		String hours = "";
		if(strBDate==null || "".equals(strBDate)||strNDate==null || "".equals(strNDate))
		{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			nowDay = simpleDateFormat.format(new Date()); 
			beforeDay = simpleDateFormat.format(new Date()); 
		}
		else
		{
			beforeDay = strBDate;
			nowDay = strNDate;
		}
		logger.info("beforeDay:"+beforeDay+"    nowDay:"+nowDay+"    hour:"+hour);


		
		if(hour==null || "".equals(hour)){
			Calendar ca = Calendar.getInstance(); 
			hours = String.valueOf(ca.get(Calendar.HOUR_OF_DAY));
		}else{
			hours = hour;
		}
		startTime = beforeDay + " "+hours+":00:00";
		endTime = nowDay + " "+hours+":59:59";
		
		Map map = new HashMap();
		map.put("startTime",startTime);
		map.put("endTime",endTime);
		return map;
	}
	/**
	 * 获取sequence
	 * @param dbpool
	 * @return
	 */
	private String getShortinsFlowId(DbPool dbpool)
	{
		String sequence = "";
		String sql      = "select SHORTINS_FLOWID.nextval sequence from  dual";
		try {
			ResultSet resultSet = dbpool.query(sql);
			while(resultSet.next())
			{
				sequence = resultSet.getString("sequence");
			}
			resultSet.close();
		} catch (SQLException e) 
		{
			logger.error("XXXXX真实性错误信息：",e);
		}
		return sequence;
	}
	/**
	 * 构造CMyyyyMMddHHmmssSSSXXXXXX（按照短信id规则）
	 * @param flowId
	 * @return
	 */
	private String getSendSeq(String flowId)
	{
		
		if(flowId.length()>6)
		{
			
			
			flowId = flowId.substring(flowId.length()-6,flowId.length());
		}
		
		else
		{
			int len = 0;
			String formatStr = "000000";
			len = 6 - flowId.length();
			if(len !=0 )
			{
				flowId = formatStr.substring(0,len)+flowId;
			}
		}
		SimpleDateFormat asimpleDateFormat = new  SimpleDateFormat("yyyyMMddHHmmssSSS");
		String sendSeq =  "CX" + asimpleDateFormat.format(new Date())+ flowId;
		return sendSeq;
	}
	
	/**
	 * 更新PrpCustomerMessage表的messageflag的状态
	 * @param map
	 * @throws Exception 
	 */
	public void updatePrpCustomerMessageFlag( List list) throws Exception
	{
		logger.info("BLPrpCustomerMessage->updatePrpCustomerMessageFlag()->更新短信发送状态->开始");
		if(list==null || list.size()<1)
			return;
		DBPrpCustomerMessage dbPrpCustomerMessage = new DBPrpCustomerMessage();
		DbPool dbpool = new DbPool();
		try{
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
		PrpCustomerMessageSchema prpCustomerMessageSchema = null;
		for(int i=0;i<list.size();i++)
		{
			prpCustomerMessageSchema = (PrpCustomerMessageSchema)list.get(i);
				if(prpCustomerMessageSchema.getMessageFlag()!=null && prpCustomerMessageSchema.getMessageFlag().length()>0 && !"0".equals(prpCustomerMessageSchema.getMessageFlag()))
				{
			dbPrpCustomerMessage.updatePrpCustomerMessageFlag(dbpool,prpCustomerMessageSchema.getSendSeq(),prpCustomerMessageSchema.getMessageFlag());
				
				if("1".equals(prpCustomerMessageSchema.getGroupFlag()))
				{
					dbPrpCustomerMessage.updateGroupMessageFlag(dbpool,prpCustomerMessageSchema.getMobile(),prpCustomerMessageSchema.getMessageFlag(),prpCustomerMessageSchema.getSendSeq());
					}
				}
			}
		}catch(Exception e)
		{
			logger.error("XXXXX真实性错误信息：",e);
			
			String message = "BLPrpCustomerMessage->updatePrpCustomerMessageFlag()->更新PrpCustomerMessage表的messageflag的状态->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("BLPrpCustomerMessage.updatePrpCustomerMessageFlag", message,e,
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
		logger.info("BLPrpCustomerMessage->updatePrpCustomerMessageFlag()->更新短信发送状态->结束");
	}
	
	/**
	 * 河南快赔更新PrpCustomerMessage表的messageflag的状态
	 * add by linqian 20131216
	 * @param map
	 * @throws Exception 
	 */
	public void updatePrpCustomerMessageFlag1( List list) throws Exception
	{
		logger.info("BLPrpCustomerMessage->updatePrpCustomerMessageFlag1()->河南快赔更新短信发送状态->开始");
		if(list==null || list.size()<1)
			return;
		DBPrpCustomerMessage dbPrpCustomerMessage = new DBPrpCustomerMessage();
		DbPool dbpool = new DbPool();
		try{
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
		PrpCustomerMessageSchema prpCustomerMessageSchema = null;
		for(int i=0;i<list.size();i++)
		{
			prpCustomerMessageSchema = (PrpCustomerMessageSchema)list.get(i);
				if(prpCustomerMessageSchema.getMessageFlag1()!=null && prpCustomerMessageSchema.getMessageFlag1().length()>0 && !"0".equals(prpCustomerMessageSchema.getMessageFlag1()))
				{
			dbPrpCustomerMessage.updatePrpCustomerMessageFlag1(dbpool,prpCustomerMessageSchema.getSendSeqSpecific(),prpCustomerMessageSchema.getMessageFlag1());
				
				if("1".equals(prpCustomerMessageSchema.getGroupFlag()))
				{
					dbPrpCustomerMessage.updateGroupMessageFlag1(dbpool,prpCustomerMessageSchema.getMobile(),prpCustomerMessageSchema.getMessageFlag1(),prpCustomerMessageSchema.getSendSeqSpecific());
					}
				}
			}
		}catch(Exception e)
		{
			logger.error("XXXXX真实性错误信息：",e);
			
			String message = "BLPrpCustomerMessage->updatePrpCustomerMessageFlag1()->更新PrpCustomerMessage表的messageflag1的状态->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("BLPrpCustomerMessage.updatePrpCustomerMessageFlag1", message,e,
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
		logger.info("BLPrpCustomerMessage->updatePrpCustomerMessageFlag()->更新短信发送状态->结束");
	}
	
	/**
	 * 补充完整PrpCustomerMessage的信息
	 * @throws Exception 
	 */
	public List completePrpCustomerMessageOtherInfo() throws Exception
	{
		logger.info("BLPrpCustomerMessage->completePrpCustomerMessageOtherInfo()->收集补充单子信息->开始");
		List list = null;
		DBPrpCustomerMessage dbPrpCustomerMessage = new DBPrpCustomerMessage();
		DbPool dbpool = new DbPool();
		try{
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			list = dbPrpCustomerMessage.getPrpCustomerMessageOtherInfo(dbpool);
		}catch(Exception e)
		{
			logger.error("XXXXX真实性错误信息：",e);
			
			String message = "BLPrpCustomerMessage->completePrpCustomerMessageOtherInfo()->补充完整PrpCustomerMessage的信息->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("BLPrpCustomerMessage.completePrpCustomerMessageOtherInfo", message,e,
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
		logger.info("BLPrpCustomerMessage->completePrpCustomerMessageOtherInfo()->收集补充单子信息->结束");
		return list;
	}
	
	/**
	 * 补充完整PrpCustomerMessage的信息
	 * @throws Exception 
	 */
	public List completePrpCustomerMessageOtherInfoGX() throws Exception
	{
		logger.info("BLPrpCustomerMessage->completePrpCustomerMessageOtherInfo()->收集补充单子信息->开始");
		List list = null;
		DBPrpCustomerMessage dbPrpCustomerMessage = new DBPrpCustomerMessage();
		DbPool dbpool = new DbPool();
		try{
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			list = dbPrpCustomerMessage.getPrpCustomerMessageOtherInfoGX(dbpool);
		}catch(Exception e)
		{
			logger.error("XXXXX真实性错误信息：",e);
			
			String message = "BLPrpCustomerMessage->completePrpCustomerMessageOtherInfo()->补充完整PrpCustomerMessage的信息->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("BLPrpCustomerMessage.completePrpCustomerMessageOtherInfo", message,e,
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
		logger.info("BLPrpCustomerMessage->completePrpCustomerMessageOtherInfo()->收集补充单子信息->结束");
		return list;
	}
	/**
	 * 补充完整PrpCustomerMessage的信息
	 * @throws Exception 
	 */
	public void updatePrpCustomerMessageOtherInfo(List list) throws Exception
	{
		logger.info("BLPrpCustomerMessage->updatePrpCustomerMessageOtherInfo()->补充单子信息->update开始");
		if(list==null || list.size()<1)
			return ;
		DBPrpCustomerMessage dbPrpCustomerMessage = new DBPrpCustomerMessage();
		DbPool dbpool = new DbPool();
		try{
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			PrpCustomerMessageSchema prpCustomerMessageSchema = null;
			for(int i=0;i<list.size();i++)
			{
				prpCustomerMessageSchema = (PrpCustomerMessageSchema)list.get(i);
				dbPrpCustomerMessage.completePrpCustomerMessage(dbpool,prpCustomerMessageSchema);
			}
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
		logger.info("BLPrpCustomerMessage->updatePrpCustomerMessageOtherInfo()->补充单子信息->update结束");
	}
	/**
	 * 补充数据，每次补充1000条，计算要循环几次
	 * @return
	 * @throws Exception
	 */
	public int getCompleteInfoForCount() throws Exception
	{
		int forCount = 0;
		DBPrpCustomerMessage dbPrpCustomerMessage = new DBPrpCustomerMessage();
		DbPool dbpool = new DbPool();
		try{
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			String sql = "select count(*) from PrpCustomerMessage where MESSAGETYPE='1' " +
				 	     "and (PhoneVisitFlag='2' or(PhoneVisitFlag = '1' and GXCondition= '1') ) and createddate > sysdate-60 and SURVEYORHANDLCOMCODE1 is null ";
			int count = dbPrpCustomerMessage.getCount(dbpool,sql);
			logger.info("需要补充信息的数量count:"+count);
			forCount = count/1000+1;
		}catch(Exception e)
		{
			logger.error("XXXXX真实性错误信息：",e);
			
			String message = "BLPrpCustomerMessage->getCompleteInfoForCount()->补充数据，每次补充1000条，计算要循环几次->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("BLPrpCustomerMessage.getCompleteInfoForCount", message,e,
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
		return forCount;
	}
	
	/**
	 * 补充数据，每次补充1000条，计算要循环几次
	 * @return
	 * @throws Exception
	 */
	public int getCompleteInfoForCountGX() throws Exception
	{
		int forCount = 0;
		DBPrpCustomerMessage dbPrpCustomerMessage = new DBPrpCustomerMessage();
		DbPool dbpool = new DbPool();
		try{
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			String sql = "select count(*) from PrpCustomerMessage where MESSAGETYPE='1' " +
				 	     "and PhoneVisitFlag = '1' and GXCondition= '1' and createddate > sysdate-60 " +
				 	     "and SURVEYORHANDLCOMCODE1 is null ";
			int count = dbPrpCustomerMessage.getCount(dbpool,sql);
			logger.info("需要补充信息的数量count:"+count);
			forCount = count/1000+1;
		}catch(Exception e)
		{
			logger.error("XXXXX真实性错误信息：",e);
			
			String message = "BLPrpCustomerMessage->getCompleteInfoForCount()->补充数据，每次补充1000条，计算要循环几次->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("BLPrpCustomerMessage.getCompleteInfoForCount", message,e,
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
		return forCount;
	}
	
	/**
	 * 取需要更新短信发送状态，每次1000条，计算要循环的次数
	 * @return
	 * @throws Exception
	 */
	public int getSendInfoForCount() throws Exception
	{
		int forCount = 0;
		DbPool dbpool = new DbPool();
		DBPrpCustomerMessage dbPrpCustomerMessage = new DBPrpCustomerMessage();
		try{
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			String sql = "select count(*) from prpCustomerMessage where messageFlag='0' and IsRepeatPhone='0' and PhoneVisitFlag='2' " +
					     "and createddate > sysdate-60 and createddate <  sysdate-1/12 ";
			int count = dbPrpCustomerMessage.getCount(dbpool,sql);
			logger.info("总个数："+count+"  sql: "+sql);
			forCount = count/3000+1;
		}catch(Exception e)
		{
			logger.error("XXXXX真实性错误信息：",e);
			
			String message = "BLPrpCustomerMessage->getSendInfoForCount()->取需要更新短信发送状态，每次1000条，计算要循环的次数->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("BLPrpCustomerMessage.getSendInfoForCount", message,e,
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
		return forCount;
	}
	
	/**
	 * 河南快赔提醒短信
	 * 取需要更新短信发送状态，每次1000条，计算要循环的次数
	 * @return
	 * @throws Exception
	 */
	public int getSendInfoForCountSpecific() throws Exception
	{
		int forCount = 0;
		DbPool dbpool = new DbPool();
		DBPrpCustomerMessage dbPrpCustomerMessage = new DBPrpCustomerMessage();
		try{
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			String sql = "select count(*) from prpCustomerMessage where messageFlag1='0' and IsRepeatPhone='0' " +
					     "and createddate > sysdate-60 and createddate <  sysdate-1/12 ";
			int count = dbPrpCustomerMessage.getCount(dbpool,sql);
			logger.info("总个数："+count+"  sql: "+sql);
			forCount = count/3000+1;
		}catch(Exception e)
		{
			logger.error("XXXXX真实性错误信息：",e);
			
			String message = "BLPrpCustomerMessage->getSendInfoForCountSpecific()->河南快赔，取需要更新短信发送状态，每次1000条，计算要循环的次数->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("BLPrpCustomerMessage.getSendInfoForCountSpecific", message,e,
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
		return forCount;
	}
	
	/**
	 * 时间段内取短信状态
	 * @param strBDate
	 * @param strNDate
	 * @return
	 */
	public int getDateSendInfoForCount(String strBDate,String strNDate) throws Exception
	{
		int forCount = 0;
		DbPool dbpool = new DbPool();
		DBPrpCustomerMessage dbPrpCustomerMessage = new DBPrpCustomerMessage();
		try{
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			String sql = "select count(*) from prpCustomerMessage " +
					     "where messageFlag = '0' and IsRepeatPhone = '0' " +
					     "and PhoneVisitFlag = '2' and createddate  " +
					     "between to_date('"+strBDate+" 00:00:00','YYYY-MM-DD HH24:MI:SS') " +
					     "and to_date('"+strNDate+" 23:59:59','YYYY-MM-DD HH24:MI:SS') ";
			int count = dbPrpCustomerMessage.getCount(dbpool,sql);
			logger.info("总个数："+count+",获取发送成功状态（时间段）"+sql);
			forCount = count/5000+1;
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
		return forCount;
	}
	
	/**
	 * 河南快赔短信时间段内取短信状态
	 * add by linqian 20131216
	 * @param strBDate
	 * @param strNDate
	 * @return
	 */
	public int getDateSendInfoForCountSpecific(String strBDate,String strNDate) throws Exception
	{
		int forCount = 0;
		DbPool dbpool = new DbPool();
		DBPrpCustomerMessage dbPrpCustomerMessage = new DBPrpCustomerMessage();
		try{
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			String sql = "select count(*) from prpCustomerMessage " +
					     "where messageFlag1 = '0' and IsRepeatPhone = '0' and createddate  " +
					     "between to_date('"+strBDate+" 00:00:00','YYYY-MM-DD HH24:MI:SS') " +
					     "and to_date('"+strNDate+" 23:59:59','YYYY-MM-DD HH24:MI:SS') ";
			int count = dbPrpCustomerMessage.getCount(dbpool,sql);
			logger.info("总个数："+count+",获取发送成功状态（时间段）"+sql);
			forCount = count/5000+1;
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
		return forCount;
	}
	/**
	 * 获取发送成功状态
	 * @return
	 * @throws Exception
	 */
	public List getSendInfo() throws Exception
	{
		logger.info("BLPrpCustomerMessage->getSendInfo()->获取短信发送状态->开始");
		List list = null;
		DBPrpCustomerMessage dbPrpCustomerMessage = new DBPrpCustomerMessage();
		DbPool dbpool = new DbPool();
		try{
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			list = dbPrpCustomerMessage.getSenList(dbpool);
		}catch(Exception e)
		{
			logger.error("XXXXX真实性错误信息：",e);
			
			String message = "BLPrpCustomerMessage->getSendInfo()->获取短信发送状态->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("BLPrpCustomerMessage.getSendInfo", message,e,
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
		logger.info("BLPrpCustomerMessage->getSendInfo()->获取短信发送状态->结束");
		return list;
	}
	
	/**
	 * 获取河南快赔发送成功状态
	 * add by linqian 20131216
	 * @return 
	 * @throws Exception
	 */
	public List getSendInfoSpecific() throws Exception
	{
		logger.info("BLPrpCustomerMessage->getSendInfoSpecific()->河南快赔获取短信发送状态->开始");
		List list = null;
		DBPrpCustomerMessage dbPrpCustomerMessage = new DBPrpCustomerMessage();
		DbPool dbpool = new DbPool();
		try{
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			list = dbPrpCustomerMessage.getSenListSpecific(dbpool);
		}catch(Exception e)
		{
			logger.error("XXXXX真实性错误信息：",e);
			
			String message = "BLPrpCustomerMessage->getSendInfoSpecific()->河南快赔获取短信发送状态->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("BLPrpCustomerMessage.getSendInfoSpecific", message,e,
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
		logger.info("BLPrpCustomerMessage->getSendInfoSpecific()->河南快赔获取短信发送状态->结束");
		return list;
	}
	
	public List getDateSendInfo(String strBDate,String strNDate) throws Exception
	{
		logger.info("BLPrpCustomerMessage->getDateSendInfo()->获取短信发送状态(时间段)->开始");
		List list = null;
		DBPrpCustomerMessage dbPrpCustomerMessage = new DBPrpCustomerMessage();
		DbPool dbpool = new DbPool();
		try{
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			list = dbPrpCustomerMessage.getDateSenList(dbpool,strBDate,strNDate);
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
		logger.info("BLPrpCustomerMessage->getDateSendInfo()->获取短信发送状态(时间段)->结束");
		return list;
	}
	
	/**
	 * 河南快赔获取某个时间段内的短信
	 * @param strBDate
	 * @param strNDate
	 * @return
	 * @throws Exception
	 */
	public List getDateSendInfoSpecific(String strBDate,String strNDate) throws Exception
	{
		logger.info("BLPrpCustomerMessage->getDateSendInfoSpecific()->河南快赔获取短信发送状态(时间段)->开始");
		List list = null;
		DBPrpCustomerMessage dbPrpCustomerMessage = new DBPrpCustomerMessage();
		DbPool dbpool = new DbPool();
		try{
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			list = dbPrpCustomerMessage.getDateSenListSpecific(dbpool,strBDate,strNDate);
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
		logger.info("BLPrpCustomerMessage->getDateSendInfoSpecific()->河南快赔获取短信发送状态(时间段)->结束");
		return list;
	}
	
	/**
	 * 获取需要判断的号码列表
	 * @return
	 * @throws Exception
	 * 
	 */
	public Map getJudgeRepeatPhoneList(String groupFlag,String messageType) throws Exception
	{
		logger.info("BLPrpCustomerMessage->getJudgeRepeatPhoneList()->获取需要判断非重复号码list->开始");
		Map map = new HashMap();
		List list = new ArrayList();
		List repeatPhoneList = new ArrayList();
		DBPrpCustomerMessage dbPrpCustomerMessage = new DBPrpCustomerMessage();
		DbPool dbpool = new DbPool();
		try{
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			list = dbPrpCustomerMessage.getPhoneList(dbpool,groupFlag,messageType);
			PrpCustomerMessageSchema prpCustomerMessageSchema = null;
			for(int i=0;i<list.size();i++)
			{
				prpCustomerMessageSchema = (PrpCustomerMessageSchema)list.get(i);
				prpCustomerMessageSchema.setGroupFlag(groupFlag);
				prpCustomerMessageSchema.setMessageType(messageType);
				repeatPhoneList = dbPrpCustomerMessage.judgeNoRepeatPhoneList(dbpool,prpCustomerMessageSchema);
				map.put(prpCustomerMessageSchema.getMobile(),repeatPhoneList);
			}
		}catch(Exception e)
		{
			logger.error("XXXXX真实性错误信息：",e);
			
			String message = "BLPrpCustomerMessage->getJudgeRepeatPhoneList()->获取需要判断的号码列表->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("BLPrpCustomerMessage.getJudgeRepeatPhoneList", message,e,
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
		logger.info("BLPrpCustomerMessage->getJudgeRepeatPhoneList()->获取需要判断非重复号码list->结束");
		return map;
	}
	/**
	 * 从接口表PrpCustomerMessage获取无需判断的非重复号码列表
	 * @return
	 * @throws Exception
	 */
	public void updatePrpCustomerMoblieStatues(Map map) throws Exception
	{
		logger.info("BLPrpCustomerMessage->updatePrpCustomerMoblieStatues()->更新非重复号码->开始");
		DBPrpCustomerMessage dbPrpCustomerMessage = new DBPrpCustomerMessage();
		DbPool dbpool = new DbPool();
		try{
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			PrpCustomerMessageSchema prpCustomerMessageSchema = null;
			Iterator iter =  map.entrySet().iterator();
			while(iter.hasNext())
			{
				Map.Entry entry = (Map.Entry)iter.next();
				List list = (List)entry.getValue();
				if(list==null || list.size()<1)
				{
					continue;
				}
				for(int i=0;i<list.size();i++)
				{
					prpCustomerMessageSchema = (PrpCustomerMessageSchema)list.get(i);
					dbPrpCustomerMessage.updatePrpCustomerMoblieStatues(dbpool,prpCustomerMessageSchema);
				}
			}
		}catch(Exception e)
		{
			logger.error("XXXXX真实性错误信息：",e);
			
			String message = "BLPrpCustomerMessage->insertPrpCustomerMessage()->从接口表PrpCustomerMessage获取无需判断的非重复号码列表->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("BLPrpCustomerMessage.updatePrpCustomerMoblieStatues", message,e,
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
		logger.info("BLPrpCustomerMessage->updatePrpCustomerMoblieStatues()->更新非重复号码->结束");
	}
	
	/**
	 * 获取需要筛选的回访名单
	 * @return
	 * @throws Exception
	 */
	public void getVisitMap(List comCodeList) throws Exception
	{
		logger.info("BLPrpCustomerMessage->getVisitMap()->需要判断回访list->开始");
		if(comCodeList==null || comCodeList.size()<1)
			return ;
		DBPrpCustomerMessage dbPrpCustomerMessage = new DBPrpCustomerMessage();
		DbPool dbpool = new DbPool();
		try{
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			for(int i=0;i<comCodeList.size();i++)
			{
				dbPrpCustomerMessage.getVisitMap(dbpool,(String)comCodeList.get(i) );
			}
		}catch(Exception e)
		{
			logger.error("XXXXX真实性错误信息：",e);
			
			String message = "BLPrpCustomerMessage->getVisitMap()->获取需要筛选的回访名单->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("BLPrpCustomerMessage.getVisitMap", message,e,
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
		logger.info("BLPrpCustomerMessage->getVisitMap()->需要判断回访list->结束");
	}
	/**
	 * 更新回访状态
	 * @param map
	 * @throws Exception
	 */
	public List updatePrpCustomerVisitStatues(List list) throws Exception
	{
		if(list==null || list.size()<1)
			return null;
		DBPrpCustomerMessage dbPrpCustomerMessage = new DBPrpCustomerMessage();
		DbPool dbpool = new DbPool();
		try{
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			PrpCustomerMessageMobileSchema prpCustomerMessageMobileSchema = null;
			for(int i=0;i<list.size();i++)
			{
				prpCustomerMessageMobileSchema = (PrpCustomerMessageMobileSchema)list.get(i);
				dbPrpCustomerMessage.updatePrpCustomerVisitStatues(dbpool,prpCustomerMessageMobileSchema.getId());
				prpCustomerMessageMobileSchema.setExportFlag("1");
			}
		}catch(Exception e)
		{
			logger.error("XXXXX真实性错误信息：",e);
			
			String message = "BLPrpCustomerMessage->updatePrpCustomerVisitStatues()->更新回访状态->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("BLPrpCustomerMessage.updatePrpCustomerVisitStatues", message,e,
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
	 * 向flex_se_cm插入短信内容后，更新PrpCustomerMessage的状态，由-1改为0
	 * @param list
	 * @throws Exception
	 */
	public void updatePrpCustomerSendedFlag(List list) throws Exception
	{
		if(list==null || list.size()<1)
			return;
		DBPrpCustomerMessage dbPrpCustomerMessage = new DBPrpCustomerMessage();
		DbPool dbpool = new DbPool();
		try{
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			FlexSeCmSchema flexSeCmSchema = null;
			for(int i=0;i<list.size();i++)
			{
				flexSeCmSchema = (FlexSeCmSchema)list.get(i);
				if(flexSeCmSchema.getStatus() != null && "0".equals(flexSeCmSchema.getStatus())){
				     dbPrpCustomerMessage.updatePrpCustomerMessageFlagNew(dbpool,flexSeCmSchema.getId(),flexSeCmSchema.getStatus());
				}			
			}
		}catch(Exception e)
		{
			logger.error("XXXXX真实性错误信息：",e);
			
			String message = "BLPrpCustomerMessage->updatePrpCustomerSendedFlag()->向flex_se_cm插入短信内容后，更新PrpCustomerMessage的状态，由-1改为0->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("BLPrpCustomerMessage.updatePrpCustomerSendedFlag", message,e,
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
	}
	
	/**
	 * 河南快赔提醒短信 add by linqian 20131213
	 * 向flex_se_cm插入短信内容后，更新PrpCustomerMessage的状态，由-1改为0
	 * @param list
	 * @throws Exception
	 */
	public void updatePrpCustomerSendedFlagSpecific(List list) throws Exception
	{
		if(list==null || list.size()<1)
			return;
		DBPrpCustomerMessage dbPrpCustomerMessage = new DBPrpCustomerMessage();
		DbPool dbpool = new DbPool();
		try{
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			FlexSeCmSchema flexSeCmSchema = null;
			for(int i=0;i<list.size();i++)
			{
				flexSeCmSchema = (FlexSeCmSchema)list.get(i);
				if(flexSeCmSchema.getStatus() != null && "0".equals(flexSeCmSchema.getStatus())){
				     dbPrpCustomerMessage.updatePrpCustomerMessageFlagSpecific(dbpool,flexSeCmSchema.getId(),flexSeCmSchema.getStatus());
				}			
			}
		}catch(Exception e)
		{
			logger.error("XXXXX真实性错误信息：",e);
			
			String message = "BLPrpCustomerMessage->updatePrpCustomerSendedFlagSpecific->向flex_se_cm插入短信内容后，更新PrpCustomerMessage的状态，由-1改为0->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("BLPrpCustomerMessage.updatePrpCustomerSendedFlagSpecific", message,e,
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
	}
	/**
	 * 更新非重复号码状态
	 * @throws Exception 
	 */
	public void updatePrpCustomerNoRepeatMoblieStatues() throws Exception
	{
		DBPrpCustomerMessage dbPrpCustomerMessage = new DBPrpCustomerMessage();
		DbPool dbpool = new DbPool();
		try{
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			dbPrpCustomerMessage.updatePrpCustomerNoRepeatMoblieStatues(dbpool);
		}catch(Exception e)
		{
			logger.error("XXXXX真实性错误信息：",e);
			
			String message = "BLPrpCustomerMessage->updatePrpCustomerNoRepeatMoblieStatues()->更新非重复号码状态->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("BLPrpCustomerMessage.updatePrpCustomerNoRepeatMoblieStatues", message,e,
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
	}
	
	/**
	 * 河南郑州更新同XXXXX不发送状态 add by linqian 20131213
	 * @throws Exception 
	 */
	public void updatePrpCustomerBuySimultaneity(String messageType) throws Exception
	{
		DBPrpCustomerMessage dbPrpCustomerMessage = new DBPrpCustomerMessage();
		List list=new ArrayList();
		DbPool dbpool = new DbPool();
		try{
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			
			list = dbPrpCustomerMessage.getBuySimultaneity(dbpool,messageType);
			for(int i=0;i<list.size();i++)
			{
		    
			dbPrpCustomerMessage.updateBuySimultaneity(dbpool,(String)list.get(i));
			}
		}catch(Exception e)
		{
			logger.error("XXXXX真实性错误信息：",e);
			
			String message = "BLPrpCustomerMessage->updatePrpCustomerBuySimultaneity()->更同XXXXX不发送状态->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("BLPrpCustomerMessage.updateBuySimultaneity", message,e,
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
	}
	/**
	 * 取二级机构代码列表
	 * @return
	 * @throws Exception 
	 */
	public List getLevelTwoComCode() throws Exception
	{
		List list = null;
		DBPrpCustomerMessage dbPrpCustomerMessage = new DBPrpCustomerMessage();
		DbPool dbpool = new DbPool();
		try{
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			list = dbPrpCustomerMessage.getLevelTwoComCode(dbpool);
		}catch(Exception e)
		{
			logger.error("XXXXX真实性错误信息：",e);
			
			String message = "BLPrpCustomerMessage->getLevelTwoComCode()->取二级机构代码列表->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("BLPrpCustomerMessage.getLevelTwoComCode", message,e,
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
	 * 更新 回访状态
	 * @throws Exception
	 */
	public void updatePrpCustomerNoVisitStatues() throws Exception
	{
		DBPrpCustomerMessage dbPrpCustomerMessage = new DBPrpCustomerMessage();
		DbPool dbpool = new DbPool();
		try{
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			dbPrpCustomerMessage.updatePrpCustomerNoVisitStatues(dbpool);
		}catch(Exception e)
		{
			logger.error("XXXXX真实性错误信息：",e);
			
			String message = "BLPrpCustomerMessage->updatePrpCustomerNoVisitStatues()->更新 回访状态->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("BLPrpCustomerMessage.updatePrpCustomerNoVisitStatues", message,e,
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
	}
	
	
	/**
	 * 更新广西 回访状态 add by linqian 20141216
	 * @throws Exception
	 */
	public void updatePrpCustomerGXVisitStatues() throws Exception
	{
		DBPrpCustomerMessage dbPrpCustomerMessage = new DBPrpCustomerMessage();
		DbPool dbpool = new DbPool();
		try{
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			dbPrpCustomerMessage.updatePrpCustomerGXVisitStatues(dbpool);
		}catch(Exception e)
		{
			logger.error("XXXXX真实性错误信息：",e);
			
			String message = "BLPrpCustomerMessage->updatePrpCustomerGXVisitStatues()->更新 回访状态->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("BLPrpCustomerMessage.updatePrpCustomerGXVisitStatues", message,e,
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
	}
	/**
	 * 更新 回访状态
	 * @throws Exception
	 */
	public void updatePrpCustomerGroupChannel() throws Exception
	{
		DBPrpCustomerMessage dbPrpCustomerMessage = new DBPrpCustomerMessage();
		DbPool dbpool = new DbPool();
		try{
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			dbPrpCustomerMessage.updatePrpCustomerGroupChannel(dbpool);
		}catch(Exception e)
		{
			logger.error("XXXXX真实性错误信息：",e);
			
			String message = "BLPrpCustomerMessage->updatePrpCustomerGroupChannel()->更新 回访状态->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("BLPrpCustomerMessage.updatePrpCustomerGroupChannel", message,e,
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
	}
	/**
	 * 回写导出标志位exportflag状态
	 * @param list
	 * @throws Exception
	 */
	public void updatePrpCustomerMessageExportFlag(List list,String type) throws Exception
	{
		if(list==null || list.size()<1)
			return;
		DBPrpCustomerMessage dbPrpCustomerMessage = new DBPrpCustomerMessage();
		DbPool dbpool = new DbPool();
		String id = "";
		String exportFlag = "";
		try{
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			logger.info("BLPrpCustomerMessage->updatePrpCustomerMessageExportFlag()->回写导出标志位exportflag状态->开始");
			for(int i=0;i<list.size();i++)
			{
				
				if("policy".equals(type))
				{
					CustomerPolicySchema customerPolicySchema = (CustomerPolicySchema)list.get(i);
					id = customerPolicySchema.getId();
					exportFlag = customerPolicySchema.getExportFlag();
				}
				
				if("claim".equals(type))
				{
					CustomerClaimSchema customerClaimSchema = (CustomerClaimSchema)list.get(i);
					id = customerClaimSchema.getId();
					exportFlag = customerClaimSchema.getExportFlag();
				}
				dbPrpCustomerMessage.updatePrpCustomerMessageExportFlag(dbpool,id,exportFlag);
			}
			logger.info("BLPrpCustomerMessage->updatePrpCustomerMessageExportFlag()->回写导出标志位exportflag状态->结束");
		}catch(Exception e)
		{
			logger.error("XXXXX真实性错误信息：",e);
			
			String message = "BLPrpCustomerMessage->updatePrpCustomerMessageExportFlag()->回写导出标志位exportflag状态->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("BLPrpCustomerMessage.updatePrpCustomerMessageExportFlag", message,e,
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
	}
	/**
	 * 获取非重复号码的团单手机
	 * 
	 * @return
	 * @throws Exception 
	 */
	public List getGroupPhoneList(String messageType) throws Exception
	{
		logger.info("BLPrpCustomerMessage->getGroupPhoneList()->获取非重复号码的团单手机list->开始");
		List list = new ArrayList();
		DBPrpCustomerMessage dbPrpCustomerMessage = new DBPrpCustomerMessage();
		DbPool dbpool = new DbPool();
		try{
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			list = dbPrpCustomerMessage.getGroupPhoneList(dbpool,messageType);
		}catch(Exception e)
		{
			logger.error("XXXXX真实性错误信息：",e);
			
			String message = "BLPrpCustomerMessage->getGroupPhoneList()->获取非重复号码的团单手机list->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("BLPrpCustomerMessage.getGroupPhoneList", message,e,
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
		logger.info("BLPrpCustomerMessage->getGroupPhoneList()->获取非重复号码的团单手机list->结束");
		return list;
	}
	
	/**
	 * 获取非重复号码的团单手机
	 * 
	 * @return
	 * @throws Exception 
	 */
	public List getGroupPhoneListSpecific(String messageType) throws Exception
	{
		logger.info("BLPrpCustomerMessage->getGroupPhoneListSpecific()->河南快赔获取非重复号码的团单手机list->开始");
		List list = new ArrayList();
		DBPrpCustomerMessage dbPrpCustomerMessage = new DBPrpCustomerMessage();
		DbPool dbpool = new DbPool();
		try{
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			list = dbPrpCustomerMessage.getGroupPhoneListSpecific(dbpool,messageType);
		}catch(Exception e)
		{
			logger.error("XXXXX真实性错误信息：",e);
			
			String message = "BLPrpCustomerMessage->getGroupPhoneListSpecific()->河南快赔获取非重复号码的团单手机list->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("BLPrpCustomerMessage.getGroupPhoneListSpecific", message,e,
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
		logger.info("BLPrpCustomerMessage->getGroupPhoneList()->河南快赔获取非重复号码的团单手机list->结束");
		return list;
	}
	/**
	 * 团单联系人只发送一条短信，更新状态
	 * @param list
	 * @throws Exception
	 */
	public void updateGroupPhoneMessageStatues(List list) throws Exception
	{
		logger.info("BLPrpCustomerMessage->updateGroupPhoneMessageStatues()->团单联系人只发送一条短信，更新状态->开始");
		DBPrpCustomerMessage dbPrpCustomerMessage = new DBPrpCustomerMessage();
		DbPool dbpool = new DbPool();
		try{
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			PrpCustomerMessageSchema prpCustomerMessageSchema = null;
			for(int i=0;i<list.size();i++)
			{
				prpCustomerMessageSchema = (PrpCustomerMessageSchema)list.get(i);
				int num = (Integer.parseInt(prpCustomerMessageSchema.getNum()))-1;
				prpCustomerMessageSchema.setNum(num+"");
				
				
				prpCustomerMessageSchema.setMessageFlag("5");
				dbPrpCustomerMessage.updateGroupPhoneMessageStatues(dbpool,prpCustomerMessageSchema);
			}
			
			
		}catch(Exception e)
		{
			logger.error("XXXXX真实性错误信息：",e);
			
			String message = "BLPrpCustomerMessage->updateGroupPhoneMessageStatues()->团单联系人只发送一条短信，更新状态->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("BLPrpCustomerMessage.updateGroupPhoneMessageStatues", message,e,
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
		logger.info("BLPrpCustomerMessage->updateGroupPhoneMessageStatues()->团单联系人只发送一条短信，更新状态->结束");
	}
	
	/**
	 * 河南快赔团单联系人只发送一条短信，更新状态
	 * add by linqian 20131231
	 * @param list
	 * @throws Exception
	 */
	public void updateGroupPhoneMessageStatuesSpecific(List list) throws Exception
	{
		logger.info("BLPrpCustomerMessage->updateGroupPhoneMessageStatuesSpecific()->河南快赔团单联系人只发送一条短信，更新状态->开始");
		DBPrpCustomerMessage dbPrpCustomerMessage = new DBPrpCustomerMessage();
		DbPool dbpool = new DbPool();
		try{
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			PrpCustomerMessageSchema prpCustomerMessageSchema = null;
			for(int i=0;i<list.size();i++)
			{
				prpCustomerMessageSchema = (PrpCustomerMessageSchema)list.get(i);
				int num = (Integer.parseInt(prpCustomerMessageSchema.getNum()))-1;
				prpCustomerMessageSchema.setNum(num+"");
				
				prpCustomerMessageSchema.setMessageFlag1("5");
				dbPrpCustomerMessage.updateGroupPhoneMessageStatuesSpecific(dbpool,prpCustomerMessageSchema);
				
				
			}		
			
		}catch(Exception e)
		{
			logger.error("XXXXX真实性错误信息：",e);
			
			String message = "BLPrpCustomerMessage->updateGroupPhoneMessageStatuesSpecific()->河南快赔团单联系人只发送一条短信，更新状态->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("BLPrpCustomerMessage.updateGroupPhoneMessageStatuesSpecific", message,e,
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
		logger.info("BLPrpCustomerMessage->updateGroupPhoneMessageStatuesSpecific()->河南快赔团单联系人只发送一条短信，更新状态->结束");
	}
	
	/**
	 * 得到发送失败的数据并更新PrpCustomerMessage，使其重新发送
	 * modify XXXXXXX分开 20120105
	 * @throws Exception 
	 */
	public void getAndUpdateFailed(String messageType) throws Exception
	{
		logger.info("BLPrpCustomerMessage->getAndUpdateFailed()->得到发送失败的数据并更新PrpCustomerMessage，使其重新发送->开始");
		DBPrpCustomerMessage dbPrpCustomerMessage = new DBPrpCustomerMessage();
		DbPool dbpool = new DbPool();
		List list = null;
		try{
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			
			list = dbPrpCustomerMessage.getFailedSendList(dbpool,messageType);
			
			if(list==null || list.size()<1)
				return ;
			PrpCustomerMessageSchema prpCustomerMessageSchema = null;
			for(int i=0;i<list.size();i++)
			{
				prpCustomerMessageSchema = (PrpCustomerMessageSchema)list.get(i);
				
				prpCustomerMessageSchema.setGroupSendSeq(prpCustomerMessageSchema.getSendSeq());
				
				prpCustomerMessageSchema.setGroupFlag(prpCustomerMessageSchema.getGroupFlag());
				
				String sendSeq = getSendSeq(prpCustomerMessageSchema.getId());
				
				prpCustomerMessageSchema.setSendSeq(sendSeq);
				
				prpCustomerMessageSchema.setId(prpCustomerMessageSchema.getId());
				
				dbPrpCustomerMessage.updateSendFailed(dbpool,prpCustomerMessageSchema);
			}
		}catch(Exception e)
		{
			logger.error("XXXXX真实性错误信息：",e);
			
			String message = "BLPrpCustomerMessage->getAndUpdateFailed()->得到发送失败的数据并更新PrpCustomerMessage，使其重新发送->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("BLPrpCustomerMessage.getAndUpdateFailed", message,e,
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
		logger.info("BLPrpCustomerMessage->getAndUpdateFailed()->得到发送失败的数据并更新PrpCustomerMessage，使其重新发送->结束");
	}
	
	/**
	 * 得到发送失败的数据并更新PrpCustomerMessage，使其重新发送
	 * XX发送快赔短信 add by linqian 20131211
	 * @throws Exception 
	 */
	public void getAndUpdateFailedSpecific(String messageType) throws Exception
	{
		logger.info("BLPrpCustomerMessage->getAndUpdateFailedSpecific()->得到发送失败的数据并更新PrpCustomerMessage，使其重新发送->开始");
		DBPrpCustomerMessage dbPrpCustomerMessage = new DBPrpCustomerMessage();
		DbPool dbpool = new DbPool();
		List list = null;
		try{
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			
			list = dbPrpCustomerMessage.getFailedSendListSpecific(dbpool,messageType);
			
			if(list==null || list.size()<1)
				return ;
			PrpCustomerMessageSchema prpCustomerMessageSchema = null;
			for(int i=0;i<list.size();i++)
			{
				prpCustomerMessageSchema = (PrpCustomerMessageSchema)list.get(i);
				
				String sendSeqSpecific=prpCustomerMessageSchema.getSendSeqSpecific();			
				
				prpCustomerMessageSchema.setGroupSendSeqSpecific(sendSeqSpecific);
			   
				prpCustomerMessageSchema.setGroupFlag(prpCustomerMessageSchema.getGroupFlag());
				String sendSeqsendSpecific0 = getSendSeq(sendSeqSpecific);
				
				prpCustomerMessageSchema.setSendSeqSpecific(sendSeqsendSpecific0);
		    	
				prpCustomerMessageSchema.setId(prpCustomerMessageSchema.getId());
				
				dbPrpCustomerMessage.updateSendFailedSpecific(dbpool,prpCustomerMessageSchema);
			}
		}catch(Exception e)
		{
			logger.error("XXXXX真实性错误信息：",e);
			
			String message = "BLPrpCustomerMessage->getAndUpdateFailedSpecific()->得到发送失败的数据并更新PrpCustomerMessage，使其重新发送->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("BLPrpCustomerMessage.getAndUpdateFailedSpecific", message,e,
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
		logger.info("BLPrpCustomerMessage->getAndUpdateFailedSpecific()->得到发送失败的数据并更新PrpCustomerMessage，使其重新发送->结束");
	}
	
	/**
	 * 判断各渠道是否有3条回访记录
	 * @return
	 * @throws Exception
	 */
	public Map atLeastThree() throws Exception
	{
		DbPool dbpool = new DbPool();
		DBPrpCustomerMessage dbPrpCustomerMessage = new DBPrpCustomerMessage();
		Map channelMap =  new HashMap();
		try{
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			List allChannelType =  dbPrpCustomerMessage.getAllChannelType(dbpool);
			for (int i = 0; i < allChannelType.size(); i++) {
				int count = dbPrpCustomerMessage.getChannelCount(dbpool,(String)allChannelType.get(i));
				channelMap.put((String)allChannelType.get(i),String.valueOf(count));
				logger.info("BLPrpCustomerMessage->atLeastThree()->判断各渠道是否有3条回访记录->【"+(String)allChannelType.get(i)+"】->【"+String.valueOf(count)+"】");
			}
		}catch(Exception e)
		{
			logger.error("XXXXX真实性错误信息：",e);
			
			String message = "BLPrpCustomerMessage->atLeastThree()->判断各渠道是否有3条回访记录->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("BLPrpCustomerMessage.atLeastThree", message,e,
					TaskMngUtil.CUSTOMERREALCOMPLETE_JOBNAME);
			
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
		return channelMap;
	}
	
	/**
	 * 判断各渠道是否有3条回访记录
	 * @return
	 * @throws Exception
	 */
	public Map atLeastThree(String messageType,String comCode) throws Exception
	{
		DbPool dbpool = new DbPool();
		DBPrpCustomerMessage dbPrpCustomerMessage = new DBPrpCustomerMessage();
		Map channelMap =  new HashMap();
		try{
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			List allChannelType =  dbPrpCustomerMessage.getAllChannelType(dbpool);
			for (int i = 0; i < allChannelType.size(); i++) {
				int count = dbPrpCustomerMessage.getChannelCount(dbpool,(String)allChannelType.get(i),messageType,comCode);
				channelMap.put((String)allChannelType.get(i),String.valueOf(count));
				logger.info("BLPrpCustomerMessage->atLeastThree()->判断任务来源："+messageType+" 机构："+comCode+"各渠道是否有3条回访记录->【"+(String)allChannelType.get(i)+"】->【"+String.valueOf(count)+"】");
			}
		}catch(Exception e)
		{
			logger.error("XXXXX真实性错误信息：",e);
			
			String message = "BLPrpCustomerMessage->atLeastThree()->判断任务来源："+messageType+" 机构："+comCode+"各渠道是否有3条回访记录->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("BLPrpCustomerMessage.atLeastThree", message,e,
					TaskMngUtil.CUSTOMERREALCOMPLETE_JOBNAME);
			
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
		return channelMap;
	}
	
	/**
	 * 判断各渠道是否有3条回访记录,为了手动执行传入起始和终止日期,为了手动执行传入起始和终止日期
	 * @return
	 * @throws Exception
	 */
	public Map atLeastThreeForServlet(String strBDate,String strNDate) throws Exception
	{
		DbPool dbpool = new DbPool();
		DBPrpCustomerMessage dbPrpCustomerMessage = new DBPrpCustomerMessage();
		Map channelMap =  new HashMap();
		try{
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			List allChannelType =  dbPrpCustomerMessage.getAllChannelType(dbpool);
			for (int i = 0; i < allChannelType.size(); i++) {
				int count = dbPrpCustomerMessage.getChannelCountForServlet(dbpool,(String)allChannelType.get(i),strBDate,strNDate);
				channelMap.put((String)allChannelType.get(i),String.valueOf(count));
				logger.info("BLPrpCustomerMessage->atLeastThree()->判断各渠道是否有3条回访记录->【"+(String)allChannelType.get(i)+"】->【"+String.valueOf(count)+"】");
			}
		}catch(Exception e)
		{
			logger.error("XXXXX真实性错误信息：",e);
			
			String message = "BLPrpCustomerMessage->atLeastThree()->判断各渠道是否有3条回访记录->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("BLPrpCustomerMessage.atLeastThree", message,e,
					TaskMngUtil.CUSTOMERREALCOMPLETE_JOBNAME);
			
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
		return channelMap;
	}
	
	/**
	 * 判断各渠道是否有3条回访记录,为了手动执行传入起始和终止日期,为了手动执行传入起始和终止日期
	 * @return
	 * @throws Exception
	 */
	public Map atLeastThreeForServlet(String strBDate,String strNDate,String messageType,String comCode) throws Exception
	{
		DbPool dbpool = new DbPool();
		DBPrpCustomerMessage dbPrpCustomerMessage = new DBPrpCustomerMessage();
		Map channelMap =  new HashMap();
		try{
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			List allChannelType =  dbPrpCustomerMessage.getAllChannelType(dbpool);
			for (int i = 0; i < allChannelType.size(); i++) {
				int count = dbPrpCustomerMessage.getChannelCountForServlet(dbpool,(String)allChannelType.get(i),strBDate,strNDate,messageType,comCode);
				channelMap.put((String)allChannelType.get(i),String.valueOf(count));
				logger.info("BLPrpCustomerMessage->atLeastThreeForServlet()->判断任务来源："+messageType+" 机构："+comCode+"各渠道是否有3条回访记录->【"+(String)allChannelType.get(i)+"】->【"+String.valueOf(count)+"】");
			}
		}catch(Exception e)
		{
			logger.error("XXXXX真实性错误信息：",e);
			
			String message = "BLPrpCustomerMessage->atLeastThreeForServlet()->判断任务来源："+messageType+" 机构："+comCode+"各渠道是否有3条回访记录->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("BLPrpCustomerMessage.atLeastThree", message,e,
					TaskMngUtil.CUSTOMERREALCOMPLETE_JOBNAME);
			
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
		return channelMap;
	}
	
	/**
	 * 获取需要筛选的回访名单
	 * @return
	 * @throws Exception
	 */
	public void getChannelMap(Map channelMap) throws Exception
	{
		logger.info("BLPrpCustomerMessage->getChannelMap()->需要判断回访list->开始");
		if(channelMap==null || channelMap.size()<1)
			return ;
		DBPrpCustomerMessage dbPrpCustomerMessage = new DBPrpCustomerMessage();
		DbPool dbpool = new DbPool();
		try{
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			Iterator it = channelMap.entrySet().iterator();
			while(it.hasNext())
			{
				Map.Entry entry = (Map.Entry) it.next();
				String  channelType = (String) entry.getKey();
				int  count = Integer.parseInt((String) entry.getValue());
				if(3-count>0){
					dbPrpCustomerMessage.getChannelMap(dbpool, channelType, 3-count);
				}
			}
		}catch(Exception e)
		{
			logger.error("XXXXX真实性错误信息：",e);
			
			String message = "BLPrpCustomerMessage->getChannelMap()->获取需要筛选的回访名单->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("BLPrpCustomerMessage.getVisitMap", message,e,
					TaskMngUtil.CUSTOMERREALCOMPLETE_JOBNAME);
			
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
		logger.info("BLPrpCustomerMessage->getChannelMap()->需要判断回访list->结束");
	}
	
	/**
	 * 获取需要筛选的回访名单
	 * @return
	 * @throws Exception
	 */
	public void getChannelMap(Map channelMap,String messageType,String comCode) throws Exception
	{
		logger.info("BLPrpCustomerMessage->getChannelMap()->需要判断回访list->开始");
		if(channelMap==null || channelMap.size()<1)
			return ;
		DBPrpCustomerMessage dbPrpCustomerMessage = new DBPrpCustomerMessage();
		DbPool dbpool = new DbPool();
		try{
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			Iterator it = channelMap.entrySet().iterator();
			while(it.hasNext())
			{
				Map.Entry entry = (Map.Entry) it.next();
				String  channelType = (String) entry.getKey();
				int  count = Integer.parseInt((String) entry.getValue());
				if(3-count>0){
					dbPrpCustomerMessage.getChannelMap(dbpool, channelType, 3-count,messageType,comCode);
				}
			}
		}catch(Exception e)
		{
			logger.error("XXXXX真实性错误信息：",e);
			
			String message = "BLPrpCustomerMessage->getChannelMap()->获取需要筛选的回访名单->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("BLPrpCustomerMessage.getVisitMap", message,e,
					TaskMngUtil.CUSTOMERREALCOMPLETE_JOBNAME);
			
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
		logger.info("BLPrpCustomerMessage->getChannelMap()->需要判断回访list->结束");
	}
	
	
	/**
	 * 获取需要筛选的回访名单,为了手动执行传入起始和终止日期,为了手动执行传入起始和终止日期
	 * @return
	 * @throws Exception
	 */
	public void getChannelMapForServlet(Map channelMap,String strBDate,String strNDate) throws Exception
	{
		logger.info("BLPrpCustomerMessage->getChannelMap()->需要判断回访list->开始");
		if(channelMap==null || channelMap.size()<1)
			return ;
		DBPrpCustomerMessage dbPrpCustomerMessage = new DBPrpCustomerMessage();
		DbPool dbpool = new DbPool();
		try{
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			Iterator it = channelMap.entrySet().iterator();
			while(it.hasNext())
			{
				Map.Entry entry = (Map.Entry) it.next();
				String  channelType = (String) entry.getKey();
				int  count = Integer.parseInt((String) entry.getValue());
				if(3-count>0){
					dbPrpCustomerMessage.getChannelMapForServlet(dbpool, channelType, 3-count,strBDate,strNDate);
				}
			}
		}catch(Exception e)
		{
			logger.error("XXXXX真实性错误信息：",e);
			
			String message = "BLPrpCustomerMessage->getChannelMap()->获取需要筛选的回访名单->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("BLPrpCustomerMessage.getVisitMap", message,e,
					TaskMngUtil.CUSTOMERREALCOMPLETE_JOBNAME);
			
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
		logger.info("BLPrpCustomerMessage->getChannelMap()->需要判断回访list->结束");
	}
	
	/**
	 * 获取需要筛选的回访名单,为了手动执行传入起始和终止日期,为了手动执行传入起始和终止日期
	 * @return
	 * @throws Exception
	 */
	public void getChannelMapForServlet(Map channelMap,String strBDate,String strNDate,String messageType,String comCode) throws Exception
	{
		logger.info("BLPrpCustomerMessage->getChannelMap()->需要判断回访list->开始");
		if(channelMap==null || channelMap.size()<1)
			return ;
		DBPrpCustomerMessage dbPrpCustomerMessage = new DBPrpCustomerMessage();
		DbPool dbpool = new DbPool();
		try{
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			Iterator it = channelMap.entrySet().iterator();
			while(it.hasNext())
			{
				Map.Entry entry = (Map.Entry) it.next();
				String  channelType = (String) entry.getKey();
				int  count = Integer.parseInt((String) entry.getValue());
				if(3-count>0){
					dbPrpCustomerMessage.getChannelMapForServlet(dbpool, channelType, 3-count,strBDate,strNDate,messageType,comCode);
				}
			}
		}catch(Exception e)
		{
			logger.error("XXXXX真实性错误信息：",e);
			
			String message = "BLPrpCustomerMessage->getChannelMapForServlet()->获取需要筛选的回访名单->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("BLPrpCustomerMessage.getVisitMap", message,e,
					TaskMngUtil.CUSTOMERREALCOMPLETE_JOBNAME);
			
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
		logger.info("BLPrpCustomerMessage->getChannelMapForServlet()->需要判断回访list->结束");
	}
	
	/**
	 * XXXXX100%回访，每次补充3000条，计算要循环几次
	 * @return
	 * @throws Exception
	 */
	public int getClaimForCount() throws Exception
	{
		int forCount = 0;
		DBPrpCustomerMessage dbPrpCustomerMessage = new DBPrpCustomerMessage();
		DbPool dbpool = new DbPool();
		try{
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			String sql = "SELECT COUNT(*) FROM prpcustomermessage  " +
							"WHERE " +
							"messagetype = '2' " +
							"AND messageflag = '1' " +
							"AND iscontent = '-1' " +
							"AND createddate > to_date(to_char(SYSDATE-7,'YYYY-MM-DD'),'YYYY-MM-DD HH24:MI:SS') " +
							"AND GROUPSENDSEQ IS NULL " +
							"AND (READMESSAGE IS NULL OR READMESSAGE < SYSDATE-1/4) ";
			int count = dbPrpCustomerMessage.getCount(dbpool,sql);
			logger.info("需要补充信息的数量count:"+count);
			forCount = count/3000+1;
		}catch(Exception e)
		{
			logger.error("XXXXX100%回访：",e);
			
			String message = "BLPrpCustomerMessage->getClaimForCount()->补充数据，每次补充3000条，计算要循环几次->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("BLPrpCustomerMessage.getClaimForCount", message,e,
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
				logger.error("XXXXX100%回访错误信息：",e);
				throw new Exception(e);
			}
		}
		return forCount;
	}
	
	/**
	 * XXXXX100%回访，获取需要读取回复短信的数据，每次3000条
	 * @return
	 * @throws Exception
	 */
	public List getClaimInfo() throws Exception
	{
		logger.info("BLPrpCustomerMessage->getClaimInfo()->获取回复短信->开始");
		List list = null;
		DBPrpCustomerMessage dbPrpCustomerMessage = new DBPrpCustomerMessage();
		DbPool dbpool = new DbPool();
		try{
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			list = dbPrpCustomerMessage.getClaimList(dbpool);
		}catch(Exception e)
		{
			logger.error("XXXXX真实性错误信息：",e);
			
			String message = "BLPrpCustomerMessage->getSendInfo()->获取短信发送状态->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("BLPrpCustomerMessage.getSendInfo", message,e,
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
		logger.info("BLPrpCustomerMessage->getSendInfo()->获取短信发送状态->结束");
		return list;
	}
	
	/**
	 * XXXXX100%回访，更新PrpCustomerMessage表的短信回复
	 * @param map
	 * @throws Exception 
	 */
	public void updatePrpCustomerClaimFlag( List list) throws Exception
	{
		logger.info("BLPrpCustomerMessage->updatePrpCustomerClaimFlag()->更新短信回复->开始");
		if(list==null || list.size()<1)
			return;
		DBPrpCustomerMessage dbPrpCustomerMessage = new DBPrpCustomerMessage();
		DbPool dbpool = new DbPool();
		try{
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
		PrpCustomerMessageSchema prpCustomerMessageSchema = null;
		for(int i=0;i<list.size();i++)
		{
			prpCustomerMessageSchema = (PrpCustomerMessageSchema)list.get(i);
			dbPrpCustomerMessage.updatePrpCustomerClaim(dbpool,prpCustomerMessageSchema);
			
			if ("1".equals(prpCustomerMessageSchema.getGroupFlag())) {
				dbPrpCustomerMessage.updateGroupClaim(dbpool,prpCustomerMessageSchema);
			}
			}
		}catch(Exception e)
		{
			logger.error("XXXXX真实性错误信息：",e);
			
			String message = "BLPrpCustomerMessage->updatePrpCustomerMessageFlag()->更新PrpCustomerMessage表的messageflag的状态->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("BLPrpCustomerMessage.updatePrpCustomerMessageFlag", message,e,
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
		logger.info("BLPrpCustomerMessage->updatePrpCustomerMessageFlag()->更新短信发送状态->结束");
	}
	
	/**
	 * XXXXX100%回访，回写导出标志位EXPORTMESFLAG状态
	 * @param list
	 * @throws Exception
	 */
	public void updateExportMesFlag(List list,String type) throws Exception
	{
		if(list==null || list.size()<1)
			return;
		DBPrpCustomerMessage dbPrpCustomerMessage = new DBPrpCustomerMessage();
		DbPool dbpool = new DbPool();
		String id = "";
		String exportFlag = "";
		try{
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			for(int i=0;i<list.size();i++)
			{
				CustomerClaimSchema customerClaimSchema = (CustomerClaimSchema)list.get(i);
				id = customerClaimSchema.getId();
				exportFlag = customerClaimSchema.getExportMesFlag();
				dbPrpCustomerMessage.updateExportMesFlag(dbpool,id,exportFlag);
			}
		}catch(Exception e)
		{
			logger.error("XXXXX真实性错误信息：",e);
			
			String message = "BLPrpCustomerMessage->updateExportMesFlag()->回写导出标志位exportmesflag状态->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("BLPrpCustomerMessage.updateExportMesFlag", message,e,
					TaskMngUtil.CLAIMISSATISFIED_JOBNAME);
			
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
	}
	
	
	/**
	 * 取得要与从cif取数的主单list
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getCIFList(String messagetype) throws Exception{
		logger.info("取得要与从cif取数的主单list -> 开始");
		List list = new ArrayList();
		DbPool dbpool = new DbPool();
		
		
		String sql = "select t.* from PrpCustomerMessage t where t.messageflag= '-1' and t.createddate > sysdate-15 and t.messagetype = ?";
		try {
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			dbpool.prepareInnerStatement(sql);
			dbpool.setString(1,messagetype);
			ResultSet resultSet = dbpool.executePreparedQuery();
			PrpCustomerMessageSchema prpCustomerMessageSchema = null;
			while(resultSet.next())
			{
				prpCustomerMessageSchema = new PrpCustomerMessageSchema();
				prpCustomerMessageSchema.setId(dbpool.getString(resultSet,"id"));
				prpCustomerMessageSchema.setGroupFlag(dbpool.getString(resultSet,"GroupFlag"));
				prpCustomerMessageSchema.setMobile(dbpool.getString(resultSet,"Mobile"));
				prpCustomerMessageSchema.setMessageType(dbpool.getString(resultSet,"messageType"));
				prpCustomerMessageSchema.setCustomerCode(dbpool.getString(resultSet,"customerCode"));
				prpCustomerMessageSchema.setPolicyNo(dbpool.getString(resultSet,"policyNo"));
				list.add(prpCustomerMessageSchema);
			}
			resultSet.close();
		} catch (Exception e) {
			logger.error("XXXXX真实性错误信息：", e);
			String message = "BLPrpCustomerMessage->getCIFList()->取得要与从cif取数的主单list->"+ e.getMessage();
			TaskMngUtil.insertMidDataLog("DBPrpCustomerMessage.updateDXdate", message, e,TaskMngUtil.CUSTOMERREALRUN_JOBNAME);
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
			}
		}
		logger.info("取得要与从cif取数的主单list -> 结束");
		return list;
	}
	
	/**
	 * 更新短信状态的
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void updateDXflag(String hour) throws Exception{
		logger.info("更新短信状态为-1 -> 开始");
		String strBDate = "0";
    	String strNDate = "24";
		if("9".equals(hour) || "10".equals(hour) || "11".equals(hour) ){
			strBDate = "9";
			strNDate = "12";
		}else if("12".equals(hour) || "13".equals(hour) || "14".equals(hour) || "15".equals(hour) || "16".equals(hour) || "17".equals(hour)){
			strBDate = "12";
			strNDate = "18";
		}else if("18".equals(hour) || "19".equals(hour) || "20".equals(hour) || "21".equals(hour) || "22".equals(hour) || "23".equals(hour)){
			strBDate = "18";
			strNDate = "23.999";
		}
		DbPool dbpool = new DbPool();
		String sql = "update PrpCustomerMessage t set t.messageflag='-1' where t.messageflag= '-2' and t.createddate " +
					"between (trunc(sysdate-1)+"+strBDate+"/24) and (trunc(sysdate-1)+"+strNDate+"/24)";
		try {
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			dbpool.prepareInnerStatement(sql);
			dbpool.executePreparedUpdate();
			dbpool.closePreparedStatement();
		} catch (Exception e) {
			logger.error("XXXXX真实性错误信息：", e);
			String message = "BLPrpCustomerMessage->updateDXdate()->更新PrpCustomerMessage表的发送短信状态的数据->"+ e.getMessage();
			TaskMngUtil.insertMidDataLog("DBPrpCustomerMessage.updateDXdate", message, e,TaskMngUtil.CUSTOMERREALRUN_JOBNAME);
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
			}
		}
		logger.info("更新短信状态为-1 -> 结束");
	}
	
	/**
	 * 河南快赔更新短信状态 add by linqian 20131211
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void updateDXflagSpecific(String hour) throws Exception{
		logger.info("郑州快赔更新短信状态为-1 -> 开始");
		String strBDate = "0";
    	String strNDate = "24";
		if("9".equals(hour) || "10".equals(hour) || "11".equals(hour) ){
			strBDate = "9";
			strNDate = "12";
		}else if("12".equals(hour) || "13".equals(hour) || "14".equals(hour) || "15".equals(hour) || "16".equals(hour) || "17".equals(hour)){
			strBDate = "12";
			strNDate = "18";
		}else if("18".equals(hour) || "19".equals(hour) || "20".equals(hour) || "21".equals(hour) || "22".equals(hour) || "23".equals(hour)){
			strBDate = "18";
			strNDate = "23.999";
		}
		DbPool dbpool = new DbPool();
		String sql = "update PrpCustomerMessage t set t.messageflag1='-1' where t.messageflag1='-2' and t.createddate " +
					"between (trunc(sysdate-1)+"+strBDate+"/24) and (trunc(sysdate-1)+"+strNDate+"/24) and comCode like '0651%'";
		try {
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			dbpool.prepareInnerStatement(sql);
			dbpool.executePreparedUpdate();
			dbpool.closePreparedStatement();
		} catch (Exception e) {
			logger.error("XXXXX真实性错误信息：", e);
			String message = "BLPrpCustomerMessage->updateDXdateSpecific()->更新PrpCustomerMessage表的发送短信状态的数据->"+ e.getMessage();
			TaskMngUtil.insertMidDataLog("DBPrpCustomerMessage.updateDXdateSpecific", message, e,TaskMngUtil.CUSTOMERREALRUN_JOBNAME);
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
			}
		}
		logger.info("郑州快赔更新短信状态为-1 -> 结束");
	}
	
	/**
	 * 更新短信状态的
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void updateDXflag2(String hour,String strBDate,String strNDate) throws Exception{
		logger.info("更新短信状态为-1 -> 开始");
		Map map = afterGetPrpCustomerMessagePolicy(hour, strBDate,strNDate);
		DbPool dbpool = new DbPool();
		String sql = "update PrpCustomerMessage t set t.messageflag='-1' where t.messageflag= '-2' and t.createddate " +
					 "between to_date('"+map.get("startTime")+"','YYYY-MM-DD HH24:MI:SS') " +
			         "and to_date('" + map.get("endTime") + "','YYYY-MM-DD HH24:MI:SS')";
		try {
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			dbpool.prepareInnerStatement(sql);
			dbpool.executePreparedUpdate();
			dbpool.closePreparedStatement();
		} catch (Exception e) {
			logger.error("XXXXX真实性错误信息：", e);
			String message = "BLPrpCustomerMessage->updateDXdate2()->更新PrpCustomerMessage表的发送短信状态的数据->"+ e.getMessage();
			TaskMngUtil.insertMidDataLog("DBPrpCustomerMessage.updateDXdate2", message, e,TaskMngUtil.CUSTOMERREALRUN_JOBNAME);
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
			}
		}
		logger.info("更新短信状态为-1 -> 结束");
	}
	
	/**
	 * 河南快赔更新短信状态 add by linqian 20131211
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void updateDXflagSpecific2(String hour,String strBDate,String strNDate) throws Exception{
		logger.info("郑州快赔更新短信状态为-1 -> 开始");
		Map map = afterGetPrpCustomerMessagePolicy(hour, strBDate,strNDate);
		DbPool dbpool = new DbPool();
		String sql = "update PrpCustomerMessage t set t.messageflag1='-1' where t.messageflag1='-2' and t.createddate " +
				     "between to_date('"+map.get("startTime")+"','YYYY-MM-DD HH24:MI:SS') " +
		             "and to_date('" + map.get("endTime") + "','YYYY-MM-DD HH24:MI:SS') " +
		             "and comCode like '0651%'";
		try {
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			dbpool.prepareInnerStatement(sql);
			dbpool.executePreparedUpdate();
			dbpool.closePreparedStatement();
		} catch (Exception e) {
			logger.error("XXXXX真实性错误信息：", e);
			String message = "BLPrpCustomerMessage->updateDXdateSpecific2()->更新PrpCustomerMessage表的发送短信状态的数据->"+ e.getMessage();
			TaskMngUtil.insertMidDataLog("DBPrpCustomerMessage.updateDXdateSpecific2", message, e,TaskMngUtil.CUSTOMERREALRUN_JOBNAME);
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
			}
		}
		logger.info("郑州快赔更新短信状态为-1 -> 结束");
	}
	
	/**
	 * 手动执行取得电销合作数据
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getDXhezuo(String strBDate,String strNDate) throws Exception{
		logger.info("手动执行取得电销合作数据主单list -> 开始");
		List list = new ArrayList();
		DbPool dbpool = new DbPool();
		String sql = "select t.* from PrpCustomerMessage t where t.messagetype = '1' " +
		 "and t.groupflag='0' " +
		 "and t.createddate  " +
	     "between to_date('"+strBDate+" 00:00:00','YYYY-MM-DD HH24:MI:SS') " +
	     "and to_date('"+strNDate+" 23:59:59','YYYY-MM-DD HH24:MI:SS') ";
		try {
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			dbpool.prepareInnerStatement(sql);
			ResultSet resultSet = dbpool.executePreparedQuery();
			PrpCustomerMessageSchema prpCustomerMessageSchema = null;
			while(resultSet.next())
			{
				prpCustomerMessageSchema = new PrpCustomerMessageSchema();
				prpCustomerMessageSchema.setId(dbpool.getString(resultSet,"id"));
				prpCustomerMessageSchema.setGroupFlag(dbpool.getString(resultSet,"GroupFlag"));
				prpCustomerMessageSchema.setMobile(dbpool.getString(resultSet,"Mobile"));
				prpCustomerMessageSchema.setMessageType(dbpool.getString(resultSet,"messageType"));
				prpCustomerMessageSchema.setCustomerCode(dbpool.getString(resultSet,"customerCode"));
				prpCustomerMessageSchema.setPolicyNo(dbpool.getString(resultSet,"policyNo"));
				prpCustomerMessageSchema.setLicenseNo(dbpool.getString(resultSet,"licenseNo"));
				prpCustomerMessageSchema.setEngineNo(dbpool.getString(resultSet,"EngineNo"));
				list.add(prpCustomerMessageSchema);
			}
			resultSet.close();
		} catch (Exception e) {
			logger.error("XXXXX真实性错误信息：", e);
			String message = "BLPrpCustomerMessage->getCIFList()->手动执行取得电销合作数据主单list->"+ e.getMessage();
			TaskMngUtil.insertMidDataLog("DBPrpCustomerMessage.updateDXdate", message, e,TaskMngUtil.CUSTOMERREALRUN_JOBNAME);
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
			}
		}
		logger.info("手动执行取得电销合作数据主单list -> 结束");
		return list;
	}
	
	/**
	 * 更新
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void updateSQL(String sql) throws Exception{
		if(!(sql.indexOf("PrpCustomerMessage")>-1)){
			return;
		}
		DbPool dbpool = new DbPool();
		try {
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			dbpool.prepareInnerStatement(sql);
			dbpool.executePreparedUpdate();
			dbpool.closePreparedStatement();
		} catch (Exception e) {
			logger.error("XXXXX真实性错误信息：", e);
			String message = "BLPrpCustomerMessage->updateSQL()->updateSQL->"+ e.getMessage();
			TaskMngUtil.insertMidDataLog("DBPrpCustomerMessage.updateSQL", message, e,TaskMngUtil.CUSTOMERREALRUN_JOBNAME);
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
			}
		}
	}
	
	/**
	 * 把XXmessageflag=3的数据加入匹配集合
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getCIFListAdd(String hour) throws Exception{
		logger.info("取得要与从cif取数的主单list -> 开始");
		List list = new ArrayList();
		DbPool dbpool = new DbPool();
		String strBDate = "0";
    	String strNDate = "24";
		if("9".equals(hour) || "10".equals(hour) || "11".equals(hour) ){
			strBDate = "9";
			strNDate = "12";
		}else if("12".equals(hour) || "13".equals(hour) || "14".equals(hour) || "15".equals(hour) || "16".equals(hour) || "17".equals(hour)){
			strBDate = "12";
			strNDate = "18";
		}else if("18".equals(hour) || "19".equals(hour) || "20".equals(hour) || "21".equals(hour) || "22".equals(hour) || "23".equals(hour)){
			strBDate = "18";
			strNDate = "23.999";
		}
		String sql = "select t.* from PrpCustomerMessage t " +
				"where t.messageflag= '3' and t.messagetype = '1' and t.createddate " +
					"between (trunc(sysdate-1)+"+strBDate+"/24) and (trunc(sysdate-1)+"+strNDate+"/24)";
		try {
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			dbpool.prepareInnerStatement(sql);
			ResultSet resultSet = dbpool.executePreparedQuery();
			PrpCustomerMessageSchema prpCustomerMessageSchema = null;
			while(resultSet.next())
			{
				prpCustomerMessageSchema = new PrpCustomerMessageSchema();
				prpCustomerMessageSchema.setId(dbpool.getString(resultSet,"id"));
				prpCustomerMessageSchema.setGroupFlag(dbpool.getString(resultSet,"GroupFlag"));
				prpCustomerMessageSchema.setMobile(dbpool.getString(resultSet,"Mobile"));
				prpCustomerMessageSchema.setMessageType(dbpool.getString(resultSet,"messageType"));
				prpCustomerMessageSchema.setCustomerCode(dbpool.getString(resultSet,"customerCode"));
				prpCustomerMessageSchema.setPolicyNo(dbpool.getString(resultSet,"policyNo"));
				list.add(prpCustomerMessageSchema);
			}
			resultSet.close();
		}  catch (Exception e) {
			logger.error("XXXXX真实性错误信息：", e);
			String message = "BLPrpCustomerMessage->getCIFListAdd()->把XXmessageflag=3的数据加入匹配list->"+ e.getMessage();
			TaskMngUtil.insertMidDataLog("DBPrpCustomerMessage.getCIFListAdd", message, e,TaskMngUtil.CUSTOMERREALRUN_JOBNAME);
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
			}
		}
		logger.info("取得要与从cif取数的主单list -> 结束");
		return list;
	}
	
	
	/**
	 * 把XXmessageflag=3的数据加入匹配集合
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getCIFListAdd2(String hour,String strBDate,String strNDate) throws Exception{
		logger.info("取得要与从cif取数的主单list -> 开始");
		List list = new ArrayList();
		DbPool dbpool = new DbPool();
		Map map = afterGetPrpCustomerMessagePolicy(hour, strBDate,strNDate);
		String sql = "select t.* from PrpCustomerMessage t " +
				"where t.messageflag= '3' and t.messagetype = '1' and t.createddate " +
				"between to_date('"+map.get("startTime")+"','YYYY-MM-DD HH24:MI:SS') " +
	             "and to_date('" + map.get("endTime") + "','YYYY-MM-DD HH24:MI:SS') ";
		try {
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			dbpool.prepareInnerStatement(sql);
			ResultSet resultSet = dbpool.executePreparedQuery();
			PrpCustomerMessageSchema prpCustomerMessageSchema = null;
			while(resultSet.next())
			{
				prpCustomerMessageSchema = new PrpCustomerMessageSchema();
				prpCustomerMessageSchema.setId(dbpool.getString(resultSet,"id"));
				prpCustomerMessageSchema.setGroupFlag(dbpool.getString(resultSet,"GroupFlag"));
				prpCustomerMessageSchema.setMobile(dbpool.getString(resultSet,"Mobile"));
				prpCustomerMessageSchema.setMessageType(dbpool.getString(resultSet,"messageType"));
				prpCustomerMessageSchema.setCustomerCode(dbpool.getString(resultSet,"customerCode"));
				prpCustomerMessageSchema.setPolicyNo(dbpool.getString(resultSet,"policyNo"));
				list.add(prpCustomerMessageSchema);
			}
			resultSet.close();
		}  catch (Exception e) {
			logger.error("XXXXX真实性错误信息：", e);
			String message = "BLPrpCustomerMessage->getCIFListAdd2()->把XXmessageflag=3的数据加入匹配list->"+ e.getMessage();
			TaskMngUtil.insertMidDataLog("DBPrpCustomerMessage.getCIFListAdd2", message, e,TaskMngUtil.CUSTOMERREALRUN_JOBNAME);
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
			}
		}
		logger.info("取得要与从cif取数的主单list -> 结束");
		return list;
	}
	
	/**
	 * 手动执行把XXmessageflag=3的数据加入匹配集合
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getCIFListAdd(String strBDate,String strNDate) throws Exception{
		logger.info("取得要与从cif取数的主单list -> 开始");
		List list = new ArrayList();
		DbPool dbpool = new DbPool();
		String sql = "select t.* from PrpCustomerMessage t " +
		"where t.messageflag= '3' and t.messagetype = '1' " +
		 "and t.createddate  " +
	     "between to_date('"+strBDate+"','YYYY-MM-DD HH24:MI:SS') " +
	     "and to_date('"+strNDate+"','YYYY-MM-DD HH24:MI:SS') ";
		try {
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			dbpool.prepareInnerStatement(sql);
			ResultSet resultSet = dbpool.executePreparedQuery();
			PrpCustomerMessageSchema prpCustomerMessageSchema = null;
			while(resultSet.next())
			{
				prpCustomerMessageSchema = new PrpCustomerMessageSchema();
				prpCustomerMessageSchema.setId(dbpool.getString(resultSet,"id"));
				prpCustomerMessageSchema.setGroupFlag(dbpool.getString(resultSet,"GroupFlag"));
				prpCustomerMessageSchema.setMobile(dbpool.getString(resultSet,"Mobile"));
				prpCustomerMessageSchema.setMessageType(dbpool.getString(resultSet,"messageType"));
				prpCustomerMessageSchema.setCustomerCode(dbpool.getString(resultSet,"customerCode"));
				prpCustomerMessageSchema.setPolicyNo(dbpool.getString(resultSet,"policyNo"));
				list.add(prpCustomerMessageSchema);
			}
			resultSet.close();
		}  catch (Exception e) {
			logger.error("XXXXX真实性错误信息：", e);
			String message = "BLPrpCustomerMessage->getCIFListAdd()->把XXmessageflag=3的数据加入匹配list->"+ e.getMessage();
			TaskMngUtil.insertMidDataLog("DBPrpCustomerMessage.getCIFListAdd", message, e,TaskMngUtil.CUSTOMERREALRUN_JOBNAME);
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
			}
		}
		logger.info("取得要与从cif取数的主单list -> 结束");
		return list;
	}
	
	/**
	 * 回访前电销回访导出标志EXPORTFLAG=0
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void updateDXhuifang() throws Exception{
		DBPrpCustomerMessage dbPrpCustomerMessage = new DBPrpCustomerMessage();
		DbPool dbpool = new DbPool();
		try{
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			dbPrpCustomerMessage.updateDXhuifang(dbpool);
		}catch(Exception e)
		{
			logger.error("XXXXX真实性错误信息：",e);
			String message = "BLPrpCustomerMessage->updateDXhuifang()->回访前电销回访导出标志EXPORTFLAG=0->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("BLPrpCustomerMessage.updateDXhuifang", message,e,
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
	}
	
	/**
	 * XX电销满意度，每次补充3000条，计算要循环几次
	 * @return
	 * @throws Exception
	 */
	public int getDxmydForCount(String sql) throws Exception
	{
		int forCount = 0;
		DBPrpCustomerMessage dbPrpCustomerMessage = new DBPrpCustomerMessage();
		DbPool dbpool = new DbPool();
		try{
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			int count = dbPrpCustomerMessage.getCount(dbpool,sql);
			logger.info("XX电销满意度需要补充信息的数量count:"+count);
			forCount = count/3000+1;
		}catch(Exception e)
		{
			logger.error("XX电销满意度：",e);
			
			String message = "BLPrpCustomerMessage->getClaimForCount()->XX电销满意度补充数据，每次补充3000条，计算要循环几次->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("BLPrpCustomerMessage.getClaimForCount", message,e,
					TaskMngUtil.CUSTOMERREALDXMYD_JOBNAME);
			
		}finally
		{
			try {
				if (dbpool != null) {
					dbpool.close();
				}
			} catch (SQLException e) {
				logger.error("XX电销满意度错误信息：", e);
				throw new Exception(e);
			}
		}
		return forCount;
	}
	
	/**
	 * XX电销满意度，获取需要读取回复短信的数据，每次3000条
	 * @return
	 * @throws Exception
	 */
	public List getDxmydInfo() throws Exception
	{
		logger.info("BLPrpCustomerMessage->getDxmydInfo()->XX电销满意度获取读取列表->开始");
		List list = null;
		DBPrpCustomerMessage dbPrpCustomerMessage = new DBPrpCustomerMessage();
		DbPool dbpool = new DbPool();
		try{
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			list = dbPrpCustomerMessage.getDxmydList(dbpool);
		}catch(Exception e)
		{
			logger.error("XXXXX真实性错误信息：",e);
			String message = "BLPrpCustomerMessage->getDxmydInfo()->XX电销满意度获取读取列表->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("BLPrpCustomerMessage.getDxmydInfo", message,e,
					TaskMngUtil.CUSTOMERREALDXMYD_JOBNAME);
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
		logger.info("BLPrpCustomerMessage->getDxmydInfo()->XX电销满意度获取读取列表->结束");
		return list;
	}
	
	/**
	 * XX电销满意度，手动获取需要读取回复短信的数据，每次3000条
	 * @return
	 * @throws Exception
	 */
	public List getDxmydInfoBySelf(String sql) throws Exception
	{
		logger.info("BLPrpCustomerMessage->getDxmydInfo()->XX电销满意度获取读取列表->开始");
		List list = null;
		DBPrpCustomerMessage dbPrpCustomerMessage = new DBPrpCustomerMessage();
		DbPool dbpool = new DbPool();
		try{
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			list = dbPrpCustomerMessage.getDxmydListBySelf(dbpool,sql);
		}catch(Exception e)
		{
			logger.error("XXXXX真实性错误信息：",e);
			String message = "BLPrpCustomerMessage->getDxmydInfo()->XX电销满意度获取读取列表->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("BLPrpCustomerMessage.getDxmydInfo", message,e,
					TaskMngUtil.CUSTOMERREALDXMYD_JOBNAME);
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
		logger.info("BLPrpCustomerMessage->getDxmydInfo()->XX电销满意度获取读取列表->结束");
		return list;
	}
	
	/**
	 * 获取需要判断的号码列表
	 * @return
	 * @throws Exception
	 * 新增于20130619，XXXXX判断重复号码时漏数据，重写XXXXX判断重复号码逻辑
	 */
	public Map getJudgeRepeatPhoneListNew(String groupFlag,String messageType) throws Exception
	{
		logger.info("BLPrpCustomerMessage->getJudgeRepeatPhoneListNew()->获取XXXXX需要判断非重复号码list->开始");
		Map map = new HashMap();
		List list = new ArrayList();
		List repeatPhoneList = new ArrayList();
		DBPrpCustomerMessage dbPrpCustomerMessage = new DBPrpCustomerMessage();
		DbPool dbpool = new DbPool();
		try{
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			list = dbPrpCustomerMessage.getPhoneListNew(dbpool,groupFlag,messageType);
			logger.info("需要判断的电话号码总数（已去重）："+list.size());
			PrpCustomerMessageSchema prpCustomerMessageSchema = null;
			for(int i=0;i<list.size();i++)
			{
				prpCustomerMessageSchema = (PrpCustomerMessageSchema)list.get(i);
				prpCustomerMessageSchema.setGroupFlag(groupFlag);
				prpCustomerMessageSchema.setMessageType(messageType);
				repeatPhoneList = dbPrpCustomerMessage.judgeNoRepeatPhoneListNew(dbpool,prpCustomerMessageSchema);
				map.put(prpCustomerMessageSchema.getMobile(),repeatPhoneList);
			}
		}catch(Exception e)
		{
			logger.error("XXXXX真实性错误信息：",e);
			String message = "BLPrpCustomerMessage->getJudgeRepeatPhoneListNew()->获取需要判断的号码列表->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("BLPrpCustomerMessage.getJudgeRepeatPhoneListNew", message,e,
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
		logger.info("BLPrpCustomerMessage->getJudgeRepeatPhoneListNew()->获取需要判断非重复号码list->结束");
		return map;
	}
	
	/**
	 * 月末回访前电销回访导出标志EXPORTFLAG=0
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void updateDXHFMonth() throws Exception{
		DBPrpCustomerMessage dbPrpCustomerMessage = new DBPrpCustomerMessage();
		DbPool dbpool = new DbPool();
		try{
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			dbPrpCustomerMessage.updateDXHFMonth(dbpool);
		}catch(Exception e)
		{
			logger.error("XXXXX真实性错误信息：",e);
			String message = "BLPrpCustomerMessage->updateDXHFMonth()->月末回访前电销回访导出标志EXPORTFLAG=0->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("BLPrpCustomerMessage.updateDXHFMonth", message,e,
					TaskMngUtil.CUSTOMERREALCOMPLETE_JOBNAME);
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
	}
	
	
	/**
	 * 查询PrpCustomerMessage表的issuccess和GXCondition的状态
	 * @param sql
	 * @throws Exception
	 */
	public Map queryPrpCustomerMessageIssuccess(String sql) throws Exception
	{
		logger.info("BLPrpCustomerMessage->queryPrpCustomerMessageIssuccess()->查询PrpCustomerMessage表的issuccess的状态");
		DBPrpCustomerMessage dbPrpCustomerMessage = new DBPrpCustomerMessage();
		String querySQL = "";
		String issuccess = "";
		String GXCondition = "";
		Map map = new HashMap();
		DbPool dbpool = new DbPool();
		try{
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			map = dbPrpCustomerMessage.queryPrpCustomerMessageIssuccess(dbpool,sql);
		}catch(Exception e)
		{
			logger.error("XXXXX真实性错误信息：",e);
			String message = "BLPrpCustomerMessage->queryPrpCustomerMessageIssuccess()->查询PrpCustomerMessage表的issuccess的状态->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("BLPrpCustomerMessage.queryPrpCustomerMessageIssuccess", message,e,
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
		return map;
	}


	/**
	 * 删除PrpCustomerMessage中指定时间点的数据
	 * @throws Exception
	 */
	public void delPrpCustomerMessage(String calendarTime, String strBDate,
			String strNDate) throws Exception{
		DBPrpCustomerMessage dbPrpCustomerMessage = new DBPrpCustomerMessage();
		DbPool dbpool = new DbPool();
		List list = null;
		try{
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			
			Map map = afterGetPrpCustomerMessagePolicy(calendarTime, strBDate,strNDate);
				
				dbPrpCustomerMessage.delPrpCustomerMessageInfo(dbpool,map);
		}catch(Exception e)
		{
			logger.error("XXXXX真实性错误信息：",e);
			
			String message = "BLPrpCustomerMessage->delPrpCustomerMessage()"+e.getMessage();
			TaskMngUtil.insertMidDataLog("BLPrpCustomerMessage.delPrpCustomerMessage", message,e,
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
		
	}
	
	/**
	 * 续XXXXX短信需求，根据条件更新短息内容条件如下：
	          发送对象范围
		1.全渠道个人非营业XXXXX续XXXXXXXXXX
		 个  人：PrpPrintPool.GroupFlag=0 
		 非营业：prpcitemcar.usenaturecode=8D||8A （需要新增字段）
		 车  XXXXX：PrpPrintPool.classcode = '05' （不用别判断）
		 续  XXXXX：prpCmain.othflag第一位为1 （需要新增字段）
		2.剔除摩托车、拖拉机
		 prpcitemcar.carkindcode not like 'M%' and prpcitemcar.carkindcode not like 'J%'
		3.剔除出XXXXX次数3次（含）以上XXXXX，以结案判断出XXXXX次数
		  prpintefinfo.lastClaimCount出XXXXX次数，
		4.剔除手机号码重复（一个号码对应三个及以上XXXXX）及错误的（不符合号码规则或不存在），系统默认有判断规则，无需修改
		5.剔除上海、江西、云南、山东、湖北宜昌地区XXXXX
		  根据PrpPrintPool.Comcode的前两位区分机构(湖北宜昌是前四位)，上海 07， 江西 34，云南 24，山东 02，湖北宜昌 2656
	 * @throws Exception 
	 */
	public void updateXBMessage(List prpCustomerMessageList) {
		logger.info("BLPrpCustomerMessage->updateXBMessage()->续XXXXX短信需求，开始");
		try{
			if(prpCustomerMessageList==null || prpCustomerMessageList.size()<1)
				return ;
			PrpCustomerMessageSchema prpCustomerMessageSchema = null;
			
			String GroupFlag = "";
			
			String usenaturecode = "";
			
			String othflag = "";
			
			String carkindcode = "";
			
			String lastClaimCount = "0";
			
			String Comcode = "";
			
			String licenseNo = "";
			
			String message = "";
			for(int i=0;i<prpCustomerMessageList.size();i++)
			{
				prpCustomerMessageSchema = (PrpCustomerMessageSchema)prpCustomerMessageList.get(i);
				if(prpCustomerMessageSchema.getGroupFlag() != null&& !"".equals(prpCustomerMessageSchema.getGroupFlag())){
					GroupFlag = prpCustomerMessageSchema.getGroupFlag();
				}
				if(prpCustomerMessageSchema.getUsenaturecode() != null&& !"".equals(prpCustomerMessageSchema.getUsenaturecode())){
					usenaturecode = prpCustomerMessageSchema.getUsenaturecode();
				}
				if(prpCustomerMessageSchema.getOthflag() != null&& !"".equals(prpCustomerMessageSchema.getOthflag())){
					othflag = prpCustomerMessageSchema.getOthflag();
				}
				if(prpCustomerMessageSchema.getCarKindCode() != null&& !"".equals(prpCustomerMessageSchema.getCarKindCode())){
					carkindcode = prpCustomerMessageSchema.getCarKindCode();
				}
				if(prpCustomerMessageSchema.getLastClaimCount() != null&& !"".equals(prpCustomerMessageSchema.getLastClaimCount())){
					lastClaimCount = prpCustomerMessageSchema.getLastClaimCount();
				}
				if(prpCustomerMessageSchema.getComCode() != null&& !"".equals(prpCustomerMessageSchema.getComCode())){
					Comcode = prpCustomerMessageSchema.getComCode();
				}
				if("0".equals(GroupFlag)
						&&"1".equals(othflag.substring(0,1))
						&&("8D".equals(usenaturecode)||"8A".equals(usenaturecode))
						&&!carkindcode.startsWith("M")&&!carkindcode.startsWith("J")
						&&!Comcode.startsWith("07")&&!Comcode.startsWith("34")
						&&!Comcode.startsWith("24")&&!Comcode.startsWith("02")
						&&!Comcode.startsWith("2656")&&Integer.parseInt(lastClaimCount)<3){
					licenseNo = prpCustomerMessageSchema.getLicenseNo();
					message="您好！您的车辆"+licenseNo+"已在我司顺利XX，请在收到XX正本后认真阅读XX条款和信息。" +
							"恭喜您";
					prpCustomerMessageSchema.setMessage(message);
					prpCustomerMessageList.set(i, prpCustomerMessageSchema);
				}
				
			}
		}catch(Exception e){
			logger.error("XXXXX真实性错误信息：",e);
			String message = "BLPrpCustomerMessage->updateXBMessage()->续XXXXX短信需求->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("BLPrpCustomerMessage.updateXBMessage", message,e,
					TaskMngUtil.CUSTOMERREALRUN_JOBNAME);
		}
		logger.info("BLPrpCustomerMessage->updateXBMessage()->续XXXXX短信需求，结束");
	}
}