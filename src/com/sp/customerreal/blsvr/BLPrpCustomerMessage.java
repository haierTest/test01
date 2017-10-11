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
	 * ȡXX�µ��Ÿ������ݣ�д��PrpCustomerMessage
	 * @throws Exception 
	 */
	public List createPrpCustomerMessage(String hour,String strBDate,String strNDate) throws Exception
	{
		logger.info("BLPrpCustomerMessage->createPrpCustomerMessage()->���ӡ��ȡ��XX��->��ʼ"+hour+"��");
		DBPrpCustomerMessage dbPrpCustomerMessage = new DBPrpCustomerMessage();
		DbPool dbpool = new DbPool();
		List list = null;

		try{
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			
			Map map = beforeGetPrpCustomerMessagePolicy(hour, strBDate,strNDate);
				
				list = dbPrpCustomerMessage.findPrpCustomerMessageInfo(dbpool,map);
		}catch(Exception e)
		{
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			
			String message = "BLPrpCustomerMessage->createPrpCustomerMessage()->ȡXX�µ��Ÿ������ݣ�д��PrpCustomerMessage->"+e.getMessage();
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
				logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
				throw new Exception(e);
			}
		}
		logger.info("BLPrpCustomerMessage->createPrpCustomerMessage()->���ӡ��ȡ��XX��->����"+hour+"��");
		return list;
	}
	
	
	/**
	 * ȡXX�µ��Ÿ������ݣ�д��PrpCustomerMessage
	 * @throws Exception 
	 */
	public List createPrpCustomerMessageGX40(String hour,String strBDate,String strNDate) throws Exception
	{
		logger.info("BLPrpCustomerMessage->createPrpCustomerMessage()->���ӡ��ȡ��XX��->��ʼ"+hour+"��");
		DBPrpCustomerMessage dbPrpCustomerMessage = new DBPrpCustomerMessage();
		DbPool dbpool = new DbPool();
		List list = null;

		try{
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			
			Map map = beforeGetPrpCustomerMessagePolicy(hour, strBDate,strNDate);
				
				list = dbPrpCustomerMessage.findPrpCustomerMessageInfoGX(dbpool,map);
		}catch(Exception e)
		{
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			
			String message = "BLPrpCustomerMessage->createPrpCustomerMessage()->ȡXX�µ��Ÿ������ݣ�д��PrpCustomerMessage->"+e.getMessage();
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
				logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
				throw new Exception(e);
			}
		}
		logger.info("BLPrpCustomerMessage->createPrpCustomerMessage()->���ӡ��ȡ��XX��->����"+hour+"��");
		return list;
	}
	
	/**
	 * ȡXX�µ��Ÿ������ݣ�Servlet����41ʱִ�У�ʵʱ��ȡ����ʹ�ã�д��PrpCustomerMessage
	 * @throws Exception 
	 */
	public List createPrpCustomerMessageGX(String hour,String strBDate,String strNDate) throws Exception
	{
		logger.info("BLPrpCustomerMessage->createPrpCustomerMessage()->���ӡ��ȡ��XX��->��ʼ"+hour+"��");
		DBPrpCustomerMessage dbPrpCustomerMessage = new DBPrpCustomerMessage();
		DbPool dbpool = new DbPool();
		List list = null;

		try{
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			
			Map map = beforeGetPrpCustomerMessagePolicyGX(hour, strBDate,strNDate);
			
			list = dbPrpCustomerMessage.findPrpCustomerMessageInfoGX(dbpool,map);
		}catch(Exception e)
		{
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			
			String message = "BLPrpCustomerMessage->createPrpCustomerMessage()->ȡXX�µ��Ÿ������ݣ�д��PrpCustomerMessage->"+e.getMessage();
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
				logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
				throw new Exception(e);
			}
		}
		logger.info("BLPrpCustomerMessage->createPrpCustomerMessage()->���ӡ��ȡ��XX��->����"+hour+"��");
		return list;
	}
	
	/**
	 * ��XX�µ��Ÿ������ݣ�д��PrpCustomerMessage
	 * @throws Exception 
	 */
	public void insertPrpCustomerMessage(List list) throws Exception
	{
		logger.info("BLPrpCustomerMessage->insertPrpCustomerMessage()->��XX���ݲ���PrpCustomerMessage->��ʼ");
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
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			
			String message = "BLPrpCustomerMessage->insertPrpCustomerMessage()->��XX�µ��Ÿ������ݣ�д��PrpCustomerMessage->"+e.getMessage();
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
				logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
				throw new Exception(e);
			}
		}
		logger.info("BLPrpCustomerMessage->insertPrpCustomerMessage()->��XX���ݲ���PrpCustomerMessage->����");
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
	
	/**ʵʱ��ȡʱ��
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
	 * ��ȡsequence
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
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
		}
		return sequence;
	}
	/**
	 * ����CMyyyyMMddHHmmssSSSXXXXXX�����ն���id����
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
	 * ����PrpCustomerMessage���messageflag��״̬
	 * @param map
	 * @throws Exception 
	 */
	public void updatePrpCustomerMessageFlag( List list) throws Exception
	{
		logger.info("BLPrpCustomerMessage->updatePrpCustomerMessageFlag()->���¶��ŷ���״̬->��ʼ");
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
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			
			String message = "BLPrpCustomerMessage->updatePrpCustomerMessageFlag()->����PrpCustomerMessage���messageflag��״̬->"+e.getMessage();
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
				logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
				throw new Exception(e);
			}
		}
		logger.info("BLPrpCustomerMessage->updatePrpCustomerMessageFlag()->���¶��ŷ���״̬->����");
	}
	
	/**
	 * ���Ͽ������PrpCustomerMessage���messageflag��״̬
	 * add by linqian 20131216
	 * @param map
	 * @throws Exception 
	 */
	public void updatePrpCustomerMessageFlag1( List list) throws Exception
	{
		logger.info("BLPrpCustomerMessage->updatePrpCustomerMessageFlag1()->���Ͽ�����¶��ŷ���״̬->��ʼ");
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
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			
			String message = "BLPrpCustomerMessage->updatePrpCustomerMessageFlag1()->����PrpCustomerMessage���messageflag1��״̬->"+e.getMessage();
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
				logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
				throw new Exception(e);
			}
		}
		logger.info("BLPrpCustomerMessage->updatePrpCustomerMessageFlag()->���¶��ŷ���״̬->����");
	}
	
	/**
	 * ��������PrpCustomerMessage����Ϣ
	 * @throws Exception 
	 */
	public List completePrpCustomerMessageOtherInfo() throws Exception
	{
		logger.info("BLPrpCustomerMessage->completePrpCustomerMessageOtherInfo()->�ռ����䵥����Ϣ->��ʼ");
		List list = null;
		DBPrpCustomerMessage dbPrpCustomerMessage = new DBPrpCustomerMessage();
		DbPool dbpool = new DbPool();
		try{
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			list = dbPrpCustomerMessage.getPrpCustomerMessageOtherInfo(dbpool);
		}catch(Exception e)
		{
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			
			String message = "BLPrpCustomerMessage->completePrpCustomerMessageOtherInfo()->��������PrpCustomerMessage����Ϣ->"+e.getMessage();
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
				logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
				throw new Exception(e);
			}
		}
		logger.info("BLPrpCustomerMessage->completePrpCustomerMessageOtherInfo()->�ռ����䵥����Ϣ->����");
		return list;
	}
	
	/**
	 * ��������PrpCustomerMessage����Ϣ
	 * @throws Exception 
	 */
	public List completePrpCustomerMessageOtherInfoGX() throws Exception
	{
		logger.info("BLPrpCustomerMessage->completePrpCustomerMessageOtherInfo()->�ռ����䵥����Ϣ->��ʼ");
		List list = null;
		DBPrpCustomerMessage dbPrpCustomerMessage = new DBPrpCustomerMessage();
		DbPool dbpool = new DbPool();
		try{
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			list = dbPrpCustomerMessage.getPrpCustomerMessageOtherInfoGX(dbpool);
		}catch(Exception e)
		{
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			
			String message = "BLPrpCustomerMessage->completePrpCustomerMessageOtherInfo()->��������PrpCustomerMessage����Ϣ->"+e.getMessage();
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
				logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
				throw new Exception(e);
			}
		}
		logger.info("BLPrpCustomerMessage->completePrpCustomerMessageOtherInfo()->�ռ����䵥����Ϣ->����");
		return list;
	}
	/**
	 * ��������PrpCustomerMessage����Ϣ
	 * @throws Exception 
	 */
	public void updatePrpCustomerMessageOtherInfo(List list) throws Exception
	{
		logger.info("BLPrpCustomerMessage->updatePrpCustomerMessageOtherInfo()->���䵥����Ϣ->update��ʼ");
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
		logger.info("BLPrpCustomerMessage->updatePrpCustomerMessageOtherInfo()->���䵥����Ϣ->update����");
	}
	/**
	 * �������ݣ�ÿ�β���1000��������Ҫѭ������
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
			logger.info("��Ҫ������Ϣ������count:"+count);
			forCount = count/1000+1;
		}catch(Exception e)
		{
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			
			String message = "BLPrpCustomerMessage->getCompleteInfoForCount()->�������ݣ�ÿ�β���1000��������Ҫѭ������->"+e.getMessage();
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
				logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
				throw new Exception(e);
			}
		}
		return forCount;
	}
	
	/**
	 * �������ݣ�ÿ�β���1000��������Ҫѭ������
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
			logger.info("��Ҫ������Ϣ������count:"+count);
			forCount = count/1000+1;
		}catch(Exception e)
		{
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			
			String message = "BLPrpCustomerMessage->getCompleteInfoForCount()->�������ݣ�ÿ�β���1000��������Ҫѭ������->"+e.getMessage();
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
				logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
				throw new Exception(e);
			}
		}
		return forCount;
	}
	
	/**
	 * ȡ��Ҫ���¶��ŷ���״̬��ÿ��1000��������Ҫѭ���Ĵ���
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
			logger.info("�ܸ�����"+count+"  sql: "+sql);
			forCount = count/3000+1;
		}catch(Exception e)
		{
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			
			String message = "BLPrpCustomerMessage->getSendInfoForCount()->ȡ��Ҫ���¶��ŷ���״̬��ÿ��1000��������Ҫѭ���Ĵ���->"+e.getMessage();
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
				logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
				throw new Exception(e);
			}
		}
		return forCount;
	}
	
	/**
	 * ���Ͽ������Ѷ���
	 * ȡ��Ҫ���¶��ŷ���״̬��ÿ��1000��������Ҫѭ���Ĵ���
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
			logger.info("�ܸ�����"+count+"  sql: "+sql);
			forCount = count/3000+1;
		}catch(Exception e)
		{
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			
			String message = "BLPrpCustomerMessage->getSendInfoForCountSpecific()->���Ͽ��⣬ȡ��Ҫ���¶��ŷ���״̬��ÿ��1000��������Ҫѭ���Ĵ���->"+e.getMessage();
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
				logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
				throw new Exception(e);
			}
		}
		return forCount;
	}
	
	/**
	 * ʱ�����ȡ����״̬
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
			logger.info("�ܸ�����"+count+",��ȡ���ͳɹ�״̬��ʱ��Σ�"+sql);
			forCount = count/5000+1;
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
		return forCount;
	}
	
	/**
	 * ���Ͽ������ʱ�����ȡ����״̬
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
			logger.info("�ܸ�����"+count+",��ȡ���ͳɹ�״̬��ʱ��Σ�"+sql);
			forCount = count/5000+1;
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
		return forCount;
	}
	/**
	 * ��ȡ���ͳɹ�״̬
	 * @return
	 * @throws Exception
	 */
	public List getSendInfo() throws Exception
	{
		logger.info("BLPrpCustomerMessage->getSendInfo()->��ȡ���ŷ���״̬->��ʼ");
		List list = null;
		DBPrpCustomerMessage dbPrpCustomerMessage = new DBPrpCustomerMessage();
		DbPool dbpool = new DbPool();
		try{
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			list = dbPrpCustomerMessage.getSenList(dbpool);
		}catch(Exception e)
		{
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			
			String message = "BLPrpCustomerMessage->getSendInfo()->��ȡ���ŷ���״̬->"+e.getMessage();
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
				logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
				throw new Exception(e);
			}
		}
		logger.info("BLPrpCustomerMessage->getSendInfo()->��ȡ���ŷ���״̬->����");
		return list;
	}
	
	/**
	 * ��ȡ���Ͽ��ⷢ�ͳɹ�״̬
	 * add by linqian 20131216
	 * @return 
	 * @throws Exception
	 */
	public List getSendInfoSpecific() throws Exception
	{
		logger.info("BLPrpCustomerMessage->getSendInfoSpecific()->���Ͽ����ȡ���ŷ���״̬->��ʼ");
		List list = null;
		DBPrpCustomerMessage dbPrpCustomerMessage = new DBPrpCustomerMessage();
		DbPool dbpool = new DbPool();
		try{
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			list = dbPrpCustomerMessage.getSenListSpecific(dbpool);
		}catch(Exception e)
		{
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			
			String message = "BLPrpCustomerMessage->getSendInfoSpecific()->���Ͽ����ȡ���ŷ���״̬->"+e.getMessage();
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
				logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
				throw new Exception(e);
			}
		}
		logger.info("BLPrpCustomerMessage->getSendInfoSpecific()->���Ͽ����ȡ���ŷ���״̬->����");
		return list;
	}
	
	public List getDateSendInfo(String strBDate,String strNDate) throws Exception
	{
		logger.info("BLPrpCustomerMessage->getDateSendInfo()->��ȡ���ŷ���״̬(ʱ���)->��ʼ");
		List list = null;
		DBPrpCustomerMessage dbPrpCustomerMessage = new DBPrpCustomerMessage();
		DbPool dbpool = new DbPool();
		try{
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			list = dbPrpCustomerMessage.getDateSenList(dbpool,strBDate,strNDate);
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
		logger.info("BLPrpCustomerMessage->getDateSendInfo()->��ȡ���ŷ���״̬(ʱ���)->����");
		return list;
	}
	
	/**
	 * ���Ͽ����ȡĳ��ʱ����ڵĶ���
	 * @param strBDate
	 * @param strNDate
	 * @return
	 * @throws Exception
	 */
	public List getDateSendInfoSpecific(String strBDate,String strNDate) throws Exception
	{
		logger.info("BLPrpCustomerMessage->getDateSendInfoSpecific()->���Ͽ����ȡ���ŷ���״̬(ʱ���)->��ʼ");
		List list = null;
		DBPrpCustomerMessage dbPrpCustomerMessage = new DBPrpCustomerMessage();
		DbPool dbpool = new DbPool();
		try{
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			list = dbPrpCustomerMessage.getDateSenListSpecific(dbpool,strBDate,strNDate);
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
		logger.info("BLPrpCustomerMessage->getDateSendInfoSpecific()->���Ͽ����ȡ���ŷ���״̬(ʱ���)->����");
		return list;
	}
	
	/**
	 * ��ȡ��Ҫ�жϵĺ����б�
	 * @return
	 * @throws Exception
	 * 
	 */
	public Map getJudgeRepeatPhoneList(String groupFlag,String messageType) throws Exception
	{
		logger.info("BLPrpCustomerMessage->getJudgeRepeatPhoneList()->��ȡ��Ҫ�жϷ��ظ�����list->��ʼ");
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
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			
			String message = "BLPrpCustomerMessage->getJudgeRepeatPhoneList()->��ȡ��Ҫ�жϵĺ����б�->"+e.getMessage();
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
				logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
				throw new Exception(e);
			}
		}
		logger.info("BLPrpCustomerMessage->getJudgeRepeatPhoneList()->��ȡ��Ҫ�жϷ��ظ�����list->����");
		return map;
	}
	/**
	 * �ӽӿڱ�PrpCustomerMessage��ȡ�����жϵķ��ظ������б�
	 * @return
	 * @throws Exception
	 */
	public void updatePrpCustomerMoblieStatues(Map map) throws Exception
	{
		logger.info("BLPrpCustomerMessage->updatePrpCustomerMoblieStatues()->���·��ظ�����->��ʼ");
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
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			
			String message = "BLPrpCustomerMessage->insertPrpCustomerMessage()->�ӽӿڱ�PrpCustomerMessage��ȡ�����жϵķ��ظ������б�->"+e.getMessage();
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
				logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
				throw new Exception(e);
			}
		}
		logger.info("BLPrpCustomerMessage->updatePrpCustomerMoblieStatues()->���·��ظ�����->����");
	}
	
	/**
	 * ��ȡ��Ҫɸѡ�Ļط�����
	 * @return
	 * @throws Exception
	 */
	public void getVisitMap(List comCodeList) throws Exception
	{
		logger.info("BLPrpCustomerMessage->getVisitMap()->��Ҫ�жϻط�list->��ʼ");
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
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			
			String message = "BLPrpCustomerMessage->getVisitMap()->��ȡ��Ҫɸѡ�Ļط�����->"+e.getMessage();
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
				logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
				throw new Exception(e);
			}
		}
		logger.info("BLPrpCustomerMessage->getVisitMap()->��Ҫ�жϻط�list->����");
	}
	/**
	 * ���»ط�״̬
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
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			
			String message = "BLPrpCustomerMessage->updatePrpCustomerVisitStatues()->���»ط�״̬->"+e.getMessage();
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
				logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
				throw new Exception(e);
			}
		}
		return list;
	}
	/**
	 * ��flex_se_cm����������ݺ󣬸���PrpCustomerMessage��״̬����-1��Ϊ0
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
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			
			String message = "BLPrpCustomerMessage->updatePrpCustomerSendedFlag()->��flex_se_cm����������ݺ󣬸���PrpCustomerMessage��״̬����-1��Ϊ0->"+e.getMessage();
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
				logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
				throw new Exception(e);
			}
		}
	}
	
	/**
	 * ���Ͽ������Ѷ��� add by linqian 20131213
	 * ��flex_se_cm����������ݺ󣬸���PrpCustomerMessage��״̬����-1��Ϊ0
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
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			
			String message = "BLPrpCustomerMessage->updatePrpCustomerSendedFlagSpecific->��flex_se_cm����������ݺ󣬸���PrpCustomerMessage��״̬����-1��Ϊ0->"+e.getMessage();
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
				logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
				throw new Exception(e);
			}
		}
	}
	/**
	 * ���·��ظ�����״̬
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
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			
			String message = "BLPrpCustomerMessage->updatePrpCustomerNoRepeatMoblieStatues()->���·��ظ�����״̬->"+e.getMessage();
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
				logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
				throw new Exception(e);
			}
		}
	}
	
	/**
	 * ����֣�ݸ���ͬXXXXX������״̬ add by linqian 20131213
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
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			
			String message = "BLPrpCustomerMessage->updatePrpCustomerBuySimultaneity()->��ͬXXXXX������״̬->"+e.getMessage();
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
				logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
				throw new Exception(e);
			}
		}
	}
	/**
	 * ȡ�������������б�
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
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			
			String message = "BLPrpCustomerMessage->getLevelTwoComCode()->ȡ�������������б�->"+e.getMessage();
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
				logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
				throw new Exception(e);
			}
		}
		return list;
	}
	/**
	 * ���� �ط�״̬
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
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			
			String message = "BLPrpCustomerMessage->updatePrpCustomerNoVisitStatues()->���� �ط�״̬->"+e.getMessage();
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
				logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
				throw new Exception(e);
			}
		}
	}
	
	
	/**
	 * ���¹��� �ط�״̬ add by linqian 20141216
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
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			
			String message = "BLPrpCustomerMessage->updatePrpCustomerGXVisitStatues()->���� �ط�״̬->"+e.getMessage();
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
				logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
				throw new Exception(e);
			}
		}
	}
	/**
	 * ���� �ط�״̬
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
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			
			String message = "BLPrpCustomerMessage->updatePrpCustomerGroupChannel()->���� �ط�״̬->"+e.getMessage();
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
				logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
				throw new Exception(e);
			}
		}
	}
	/**
	 * ��д������־λexportflag״̬
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
			logger.info("BLPrpCustomerMessage->updatePrpCustomerMessageExportFlag()->��д������־λexportflag״̬->��ʼ");
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
			logger.info("BLPrpCustomerMessage->updatePrpCustomerMessageExportFlag()->��д������־λexportflag״̬->����");
		}catch(Exception e)
		{
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			
			String message = "BLPrpCustomerMessage->updatePrpCustomerMessageExportFlag()->��д������־λexportflag״̬->"+e.getMessage();
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
				logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
				throw new Exception(e);
			}
		}
	}
	/**
	 * ��ȡ���ظ�������ŵ��ֻ�
	 * 
	 * @return
	 * @throws Exception 
	 */
	public List getGroupPhoneList(String messageType) throws Exception
	{
		logger.info("BLPrpCustomerMessage->getGroupPhoneList()->��ȡ���ظ�������ŵ��ֻ�list->��ʼ");
		List list = new ArrayList();
		DBPrpCustomerMessage dbPrpCustomerMessage = new DBPrpCustomerMessage();
		DbPool dbpool = new DbPool();
		try{
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			list = dbPrpCustomerMessage.getGroupPhoneList(dbpool,messageType);
		}catch(Exception e)
		{
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			
			String message = "BLPrpCustomerMessage->getGroupPhoneList()->��ȡ���ظ�������ŵ��ֻ�list->"+e.getMessage();
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
				logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
				throw new Exception(e);
			}
		}
		logger.info("BLPrpCustomerMessage->getGroupPhoneList()->��ȡ���ظ�������ŵ��ֻ�list->����");
		return list;
	}
	
	/**
	 * ��ȡ���ظ�������ŵ��ֻ�
	 * 
	 * @return
	 * @throws Exception 
	 */
	public List getGroupPhoneListSpecific(String messageType) throws Exception
	{
		logger.info("BLPrpCustomerMessage->getGroupPhoneListSpecific()->���Ͽ����ȡ���ظ�������ŵ��ֻ�list->��ʼ");
		List list = new ArrayList();
		DBPrpCustomerMessage dbPrpCustomerMessage = new DBPrpCustomerMessage();
		DbPool dbpool = new DbPool();
		try{
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			list = dbPrpCustomerMessage.getGroupPhoneListSpecific(dbpool,messageType);
		}catch(Exception e)
		{
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			
			String message = "BLPrpCustomerMessage->getGroupPhoneListSpecific()->���Ͽ����ȡ���ظ�������ŵ��ֻ�list->"+e.getMessage();
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
				logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
				throw new Exception(e);
			}
		}
		logger.info("BLPrpCustomerMessage->getGroupPhoneList()->���Ͽ����ȡ���ظ�������ŵ��ֻ�list->����");
		return list;
	}
	/**
	 * �ŵ���ϵ��ֻ����һ�����ţ�����״̬
	 * @param list
	 * @throws Exception
	 */
	public void updateGroupPhoneMessageStatues(List list) throws Exception
	{
		logger.info("BLPrpCustomerMessage->updateGroupPhoneMessageStatues()->�ŵ���ϵ��ֻ����һ�����ţ�����״̬->��ʼ");
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
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			
			String message = "BLPrpCustomerMessage->updateGroupPhoneMessageStatues()->�ŵ���ϵ��ֻ����һ�����ţ�����״̬->"+e.getMessage();
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
				logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
				throw new Exception(e);
			}
		}
		logger.info("BLPrpCustomerMessage->updateGroupPhoneMessageStatues()->�ŵ���ϵ��ֻ����һ�����ţ�����״̬->����");
	}
	
	/**
	 * ���Ͽ����ŵ���ϵ��ֻ����һ�����ţ�����״̬
	 * add by linqian 20131231
	 * @param list
	 * @throws Exception
	 */
	public void updateGroupPhoneMessageStatuesSpecific(List list) throws Exception
	{
		logger.info("BLPrpCustomerMessage->updateGroupPhoneMessageStatuesSpecific()->���Ͽ����ŵ���ϵ��ֻ����һ�����ţ�����״̬->��ʼ");
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
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			
			String message = "BLPrpCustomerMessage->updateGroupPhoneMessageStatuesSpecific()->���Ͽ����ŵ���ϵ��ֻ����һ�����ţ�����״̬->"+e.getMessage();
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
				logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
				throw new Exception(e);
			}
		}
		logger.info("BLPrpCustomerMessage->updateGroupPhoneMessageStatuesSpecific()->���Ͽ����ŵ���ϵ��ֻ����һ�����ţ�����״̬->����");
	}
	
	/**
	 * �õ�����ʧ�ܵ����ݲ�����PrpCustomerMessage��ʹ�����·���
	 * modify XXXXXXX�ֿ� 20120105
	 * @throws Exception 
	 */
	public void getAndUpdateFailed(String messageType) throws Exception
	{
		logger.info("BLPrpCustomerMessage->getAndUpdateFailed()->�õ�����ʧ�ܵ����ݲ�����PrpCustomerMessage��ʹ�����·���->��ʼ");
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
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			
			String message = "BLPrpCustomerMessage->getAndUpdateFailed()->�õ�����ʧ�ܵ����ݲ�����PrpCustomerMessage��ʹ�����·���->"+e.getMessage();
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
				logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
				throw new Exception(e);
			}
		}
		logger.info("BLPrpCustomerMessage->getAndUpdateFailed()->�õ�����ʧ�ܵ����ݲ�����PrpCustomerMessage��ʹ�����·���->����");
	}
	
	/**
	 * �õ�����ʧ�ܵ����ݲ�����PrpCustomerMessage��ʹ�����·���
	 * XX���Ϳ������ add by linqian 20131211
	 * @throws Exception 
	 */
	public void getAndUpdateFailedSpecific(String messageType) throws Exception
	{
		logger.info("BLPrpCustomerMessage->getAndUpdateFailedSpecific()->�õ�����ʧ�ܵ����ݲ�����PrpCustomerMessage��ʹ�����·���->��ʼ");
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
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			
			String message = "BLPrpCustomerMessage->getAndUpdateFailedSpecific()->�õ�����ʧ�ܵ����ݲ�����PrpCustomerMessage��ʹ�����·���->"+e.getMessage();
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
				logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
				throw new Exception(e);
			}
		}
		logger.info("BLPrpCustomerMessage->getAndUpdateFailedSpecific()->�õ�����ʧ�ܵ����ݲ�����PrpCustomerMessage��ʹ�����·���->����");
	}
	
	/**
	 * �жϸ������Ƿ���3���طü�¼
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
				logger.info("BLPrpCustomerMessage->atLeastThree()->�жϸ������Ƿ���3���طü�¼->��"+(String)allChannelType.get(i)+"��->��"+String.valueOf(count)+"��");
			}
		}catch(Exception e)
		{
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			
			String message = "BLPrpCustomerMessage->atLeastThree()->�жϸ������Ƿ���3���طü�¼->"+e.getMessage();
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
				logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
				throw new Exception(e);
			}
		}
		return channelMap;
	}
	
	/**
	 * �жϸ������Ƿ���3���طü�¼
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
				logger.info("BLPrpCustomerMessage->atLeastThree()->�ж�������Դ��"+messageType+" ������"+comCode+"�������Ƿ���3���طü�¼->��"+(String)allChannelType.get(i)+"��->��"+String.valueOf(count)+"��");
			}
		}catch(Exception e)
		{
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			
			String message = "BLPrpCustomerMessage->atLeastThree()->�ж�������Դ��"+messageType+" ������"+comCode+"�������Ƿ���3���طü�¼->"+e.getMessage();
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
				logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
				throw new Exception(e);
			}
		}
		return channelMap;
	}
	
	/**
	 * �жϸ������Ƿ���3���طü�¼,Ϊ���ֶ�ִ�д�����ʼ����ֹ����,Ϊ���ֶ�ִ�д�����ʼ����ֹ����
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
				logger.info("BLPrpCustomerMessage->atLeastThree()->�жϸ������Ƿ���3���طü�¼->��"+(String)allChannelType.get(i)+"��->��"+String.valueOf(count)+"��");
			}
		}catch(Exception e)
		{
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			
			String message = "BLPrpCustomerMessage->atLeastThree()->�жϸ������Ƿ���3���طü�¼->"+e.getMessage();
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
				logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
				throw new Exception(e);
			}
		}
		return channelMap;
	}
	
	/**
	 * �жϸ������Ƿ���3���طü�¼,Ϊ���ֶ�ִ�д�����ʼ����ֹ����,Ϊ���ֶ�ִ�д�����ʼ����ֹ����
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
				logger.info("BLPrpCustomerMessage->atLeastThreeForServlet()->�ж�������Դ��"+messageType+" ������"+comCode+"�������Ƿ���3���طü�¼->��"+(String)allChannelType.get(i)+"��->��"+String.valueOf(count)+"��");
			}
		}catch(Exception e)
		{
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			
			String message = "BLPrpCustomerMessage->atLeastThreeForServlet()->�ж�������Դ��"+messageType+" ������"+comCode+"�������Ƿ���3���طü�¼->"+e.getMessage();
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
				logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
				throw new Exception(e);
			}
		}
		return channelMap;
	}
	
	/**
	 * ��ȡ��Ҫɸѡ�Ļط�����
	 * @return
	 * @throws Exception
	 */
	public void getChannelMap(Map channelMap) throws Exception
	{
		logger.info("BLPrpCustomerMessage->getChannelMap()->��Ҫ�жϻط�list->��ʼ");
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
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			
			String message = "BLPrpCustomerMessage->getChannelMap()->��ȡ��Ҫɸѡ�Ļط�����->"+e.getMessage();
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
				logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
				throw new Exception(e);
			}
		}
		logger.info("BLPrpCustomerMessage->getChannelMap()->��Ҫ�жϻط�list->����");
	}
	
	/**
	 * ��ȡ��Ҫɸѡ�Ļط�����
	 * @return
	 * @throws Exception
	 */
	public void getChannelMap(Map channelMap,String messageType,String comCode) throws Exception
	{
		logger.info("BLPrpCustomerMessage->getChannelMap()->��Ҫ�жϻط�list->��ʼ");
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
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			
			String message = "BLPrpCustomerMessage->getChannelMap()->��ȡ��Ҫɸѡ�Ļط�����->"+e.getMessage();
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
				logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
				throw new Exception(e);
			}
		}
		logger.info("BLPrpCustomerMessage->getChannelMap()->��Ҫ�жϻط�list->����");
	}
	
	
	/**
	 * ��ȡ��Ҫɸѡ�Ļط�����,Ϊ���ֶ�ִ�д�����ʼ����ֹ����,Ϊ���ֶ�ִ�д�����ʼ����ֹ����
	 * @return
	 * @throws Exception
	 */
	public void getChannelMapForServlet(Map channelMap,String strBDate,String strNDate) throws Exception
	{
		logger.info("BLPrpCustomerMessage->getChannelMap()->��Ҫ�жϻط�list->��ʼ");
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
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			
			String message = "BLPrpCustomerMessage->getChannelMap()->��ȡ��Ҫɸѡ�Ļط�����->"+e.getMessage();
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
				logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
				throw new Exception(e);
			}
		}
		logger.info("BLPrpCustomerMessage->getChannelMap()->��Ҫ�жϻط�list->����");
	}
	
	/**
	 * ��ȡ��Ҫɸѡ�Ļط�����,Ϊ���ֶ�ִ�д�����ʼ����ֹ����,Ϊ���ֶ�ִ�д�����ʼ����ֹ����
	 * @return
	 * @throws Exception
	 */
	public void getChannelMapForServlet(Map channelMap,String strBDate,String strNDate,String messageType,String comCode) throws Exception
	{
		logger.info("BLPrpCustomerMessage->getChannelMap()->��Ҫ�жϻط�list->��ʼ");
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
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			
			String message = "BLPrpCustomerMessage->getChannelMapForServlet()->��ȡ��Ҫɸѡ�Ļط�����->"+e.getMessage();
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
				logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
				throw new Exception(e);
			}
		}
		logger.info("BLPrpCustomerMessage->getChannelMapForServlet()->��Ҫ�жϻط�list->����");
	}
	
	/**
	 * XXXXX100%�طã�ÿ�β���3000��������Ҫѭ������
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
			logger.info("��Ҫ������Ϣ������count:"+count);
			forCount = count/3000+1;
		}catch(Exception e)
		{
			logger.error("XXXXX100%�طã�",e);
			
			String message = "BLPrpCustomerMessage->getClaimForCount()->�������ݣ�ÿ�β���3000��������Ҫѭ������->"+e.getMessage();
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
				logger.error("XXXXX100%�طô�����Ϣ��",e);
				throw new Exception(e);
			}
		}
		return forCount;
	}
	
	/**
	 * XXXXX100%�طã���ȡ��Ҫ��ȡ�ظ����ŵ����ݣ�ÿ��3000��
	 * @return
	 * @throws Exception
	 */
	public List getClaimInfo() throws Exception
	{
		logger.info("BLPrpCustomerMessage->getClaimInfo()->��ȡ�ظ�����->��ʼ");
		List list = null;
		DBPrpCustomerMessage dbPrpCustomerMessage = new DBPrpCustomerMessage();
		DbPool dbpool = new DbPool();
		try{
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			list = dbPrpCustomerMessage.getClaimList(dbpool);
		}catch(Exception e)
		{
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			
			String message = "BLPrpCustomerMessage->getSendInfo()->��ȡ���ŷ���״̬->"+e.getMessage();
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
				logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
				throw new Exception(e);
			}
		}
		logger.info("BLPrpCustomerMessage->getSendInfo()->��ȡ���ŷ���״̬->����");
		return list;
	}
	
	/**
	 * XXXXX100%�طã�����PrpCustomerMessage��Ķ��Żظ�
	 * @param map
	 * @throws Exception 
	 */
	public void updatePrpCustomerClaimFlag( List list) throws Exception
	{
		logger.info("BLPrpCustomerMessage->updatePrpCustomerClaimFlag()->���¶��Żظ�->��ʼ");
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
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			
			String message = "BLPrpCustomerMessage->updatePrpCustomerMessageFlag()->����PrpCustomerMessage���messageflag��״̬->"+e.getMessage();
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
				logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
				throw new Exception(e);
			}
		}
		logger.info("BLPrpCustomerMessage->updatePrpCustomerMessageFlag()->���¶��ŷ���״̬->����");
	}
	
	/**
	 * XXXXX100%�طã���д������־λEXPORTMESFLAG״̬
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
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			
			String message = "BLPrpCustomerMessage->updateExportMesFlag()->��д������־λexportmesflag״̬->"+e.getMessage();
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
				logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
				throw new Exception(e);
			}
		}
	}
	
	
	/**
	 * ȡ��Ҫ���cifȡ��������list
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getCIFList(String messagetype) throws Exception{
		logger.info("ȡ��Ҫ���cifȡ��������list -> ��ʼ");
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
			logger.error("XXXXX��ʵ�Դ�����Ϣ��", e);
			String message = "BLPrpCustomerMessage->getCIFList()->ȡ��Ҫ���cifȡ��������list->"+ e.getMessage();
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
				logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			}
		}
		logger.info("ȡ��Ҫ���cifȡ��������list -> ����");
		return list;
	}
	
	/**
	 * ���¶���״̬��
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void updateDXflag(String hour) throws Exception{
		logger.info("���¶���״̬Ϊ-1 -> ��ʼ");
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
			logger.error("XXXXX��ʵ�Դ�����Ϣ��", e);
			String message = "BLPrpCustomerMessage->updateDXdate()->����PrpCustomerMessage��ķ��Ͷ���״̬������->"+ e.getMessage();
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
				logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			}
		}
		logger.info("���¶���״̬Ϊ-1 -> ����");
	}
	
	/**
	 * ���Ͽ�����¶���״̬ add by linqian 20131211
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void updateDXflagSpecific(String hour) throws Exception{
		logger.info("֣�ݿ�����¶���״̬Ϊ-1 -> ��ʼ");
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
			logger.error("XXXXX��ʵ�Դ�����Ϣ��", e);
			String message = "BLPrpCustomerMessage->updateDXdateSpecific()->����PrpCustomerMessage��ķ��Ͷ���״̬������->"+ e.getMessage();
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
				logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			}
		}
		logger.info("֣�ݿ�����¶���״̬Ϊ-1 -> ����");
	}
	
	/**
	 * ���¶���״̬��
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void updateDXflag2(String hour,String strBDate,String strNDate) throws Exception{
		logger.info("���¶���״̬Ϊ-1 -> ��ʼ");
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
			logger.error("XXXXX��ʵ�Դ�����Ϣ��", e);
			String message = "BLPrpCustomerMessage->updateDXdate2()->����PrpCustomerMessage��ķ��Ͷ���״̬������->"+ e.getMessage();
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
				logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			}
		}
		logger.info("���¶���״̬Ϊ-1 -> ����");
	}
	
	/**
	 * ���Ͽ�����¶���״̬ add by linqian 20131211
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void updateDXflagSpecific2(String hour,String strBDate,String strNDate) throws Exception{
		logger.info("֣�ݿ�����¶���״̬Ϊ-1 -> ��ʼ");
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
			logger.error("XXXXX��ʵ�Դ�����Ϣ��", e);
			String message = "BLPrpCustomerMessage->updateDXdateSpecific2()->����PrpCustomerMessage��ķ��Ͷ���״̬������->"+ e.getMessage();
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
				logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			}
		}
		logger.info("֣�ݿ�����¶���״̬Ϊ-1 -> ����");
	}
	
	/**
	 * �ֶ�ִ��ȡ�õ�����������
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getDXhezuo(String strBDate,String strNDate) throws Exception{
		logger.info("�ֶ�ִ��ȡ�õ���������������list -> ��ʼ");
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
			logger.error("XXXXX��ʵ�Դ�����Ϣ��", e);
			String message = "BLPrpCustomerMessage->getCIFList()->�ֶ�ִ��ȡ�õ���������������list->"+ e.getMessage();
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
				logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			}
		}
		logger.info("�ֶ�ִ��ȡ�õ���������������list -> ����");
		return list;
	}
	
	/**
	 * ����
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
			logger.error("XXXXX��ʵ�Դ�����Ϣ��", e);
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
				logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			}
		}
	}
	
	/**
	 * ��XXmessageflag=3�����ݼ���ƥ�伯��
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getCIFListAdd(String hour) throws Exception{
		logger.info("ȡ��Ҫ���cifȡ��������list -> ��ʼ");
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
			logger.error("XXXXX��ʵ�Դ�����Ϣ��", e);
			String message = "BLPrpCustomerMessage->getCIFListAdd()->��XXmessageflag=3�����ݼ���ƥ��list->"+ e.getMessage();
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
				logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			}
		}
		logger.info("ȡ��Ҫ���cifȡ��������list -> ����");
		return list;
	}
	
	
	/**
	 * ��XXmessageflag=3�����ݼ���ƥ�伯��
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getCIFListAdd2(String hour,String strBDate,String strNDate) throws Exception{
		logger.info("ȡ��Ҫ���cifȡ��������list -> ��ʼ");
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
			logger.error("XXXXX��ʵ�Դ�����Ϣ��", e);
			String message = "BLPrpCustomerMessage->getCIFListAdd2()->��XXmessageflag=3�����ݼ���ƥ��list->"+ e.getMessage();
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
				logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			}
		}
		logger.info("ȡ��Ҫ���cifȡ��������list -> ����");
		return list;
	}
	
	/**
	 * �ֶ�ִ�а�XXmessageflag=3�����ݼ���ƥ�伯��
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getCIFListAdd(String strBDate,String strNDate) throws Exception{
		logger.info("ȡ��Ҫ���cifȡ��������list -> ��ʼ");
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
			logger.error("XXXXX��ʵ�Դ�����Ϣ��", e);
			String message = "BLPrpCustomerMessage->getCIFListAdd()->��XXmessageflag=3�����ݼ���ƥ��list->"+ e.getMessage();
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
				logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			}
		}
		logger.info("ȡ��Ҫ���cifȡ��������list -> ����");
		return list;
	}
	
	/**
	 * �ط�ǰ�����طõ�����־EXPORTFLAG=0
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
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			String message = "BLPrpCustomerMessage->updateDXhuifang()->�ط�ǰ�����طõ�����־EXPORTFLAG=0->"+e.getMessage();
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
				logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
				throw new Exception(e);
			}
		}
	}
	
	/**
	 * XX��������ȣ�ÿ�β���3000��������Ҫѭ������
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
			logger.info("XX�����������Ҫ������Ϣ������count:"+count);
			forCount = count/3000+1;
		}catch(Exception e)
		{
			logger.error("XX��������ȣ�",e);
			
			String message = "BLPrpCustomerMessage->getClaimForCount()->XX��������Ȳ������ݣ�ÿ�β���3000��������Ҫѭ������->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("BLPrpCustomerMessage.getClaimForCount", message,e,
					TaskMngUtil.CUSTOMERREALDXMYD_JOBNAME);
			
		}finally
		{
			try {
				if (dbpool != null) {
					dbpool.close();
				}
			} catch (SQLException e) {
				logger.error("XX��������ȴ�����Ϣ��", e);
				throw new Exception(e);
			}
		}
		return forCount;
	}
	
	/**
	 * XX��������ȣ���ȡ��Ҫ��ȡ�ظ����ŵ����ݣ�ÿ��3000��
	 * @return
	 * @throws Exception
	 */
	public List getDxmydInfo() throws Exception
	{
		logger.info("BLPrpCustomerMessage->getDxmydInfo()->XX��������Ȼ�ȡ��ȡ�б�->��ʼ");
		List list = null;
		DBPrpCustomerMessage dbPrpCustomerMessage = new DBPrpCustomerMessage();
		DbPool dbpool = new DbPool();
		try{
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			list = dbPrpCustomerMessage.getDxmydList(dbpool);
		}catch(Exception e)
		{
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			String message = "BLPrpCustomerMessage->getDxmydInfo()->XX��������Ȼ�ȡ��ȡ�б�->"+e.getMessage();
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
				logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
				throw new Exception(e);
			}
		}
		logger.info("BLPrpCustomerMessage->getDxmydInfo()->XX��������Ȼ�ȡ��ȡ�б�->����");
		return list;
	}
	
	/**
	 * XX��������ȣ��ֶ���ȡ��Ҫ��ȡ�ظ����ŵ����ݣ�ÿ��3000��
	 * @return
	 * @throws Exception
	 */
	public List getDxmydInfoBySelf(String sql) throws Exception
	{
		logger.info("BLPrpCustomerMessage->getDxmydInfo()->XX��������Ȼ�ȡ��ȡ�б�->��ʼ");
		List list = null;
		DBPrpCustomerMessage dbPrpCustomerMessage = new DBPrpCustomerMessage();
		DbPool dbpool = new DbPool();
		try{
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			list = dbPrpCustomerMessage.getDxmydListBySelf(dbpool,sql);
		}catch(Exception e)
		{
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			String message = "BLPrpCustomerMessage->getDxmydInfo()->XX��������Ȼ�ȡ��ȡ�б�->"+e.getMessage();
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
				logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
				throw new Exception(e);
			}
		}
		logger.info("BLPrpCustomerMessage->getDxmydInfo()->XX��������Ȼ�ȡ��ȡ�б�->����");
		return list;
	}
	
	/**
	 * ��ȡ��Ҫ�жϵĺ����б�
	 * @return
	 * @throws Exception
	 * ������20130619��XXXXX�ж��ظ�����ʱ©���ݣ���дXXXXX�ж��ظ������߼�
	 */
	public Map getJudgeRepeatPhoneListNew(String groupFlag,String messageType) throws Exception
	{
		logger.info("BLPrpCustomerMessage->getJudgeRepeatPhoneListNew()->��ȡXXXXX��Ҫ�жϷ��ظ�����list->��ʼ");
		Map map = new HashMap();
		List list = new ArrayList();
		List repeatPhoneList = new ArrayList();
		DBPrpCustomerMessage dbPrpCustomerMessage = new DBPrpCustomerMessage();
		DbPool dbpool = new DbPool();
		try{
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			list = dbPrpCustomerMessage.getPhoneListNew(dbpool,groupFlag,messageType);
			logger.info("��Ҫ�жϵĵ绰������������ȥ�أ���"+list.size());
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
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			String message = "BLPrpCustomerMessage->getJudgeRepeatPhoneListNew()->��ȡ��Ҫ�жϵĺ����б�->"+e.getMessage();
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
				logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
				throw new Exception(e);
			}
		}
		logger.info("BLPrpCustomerMessage->getJudgeRepeatPhoneListNew()->��ȡ��Ҫ�жϷ��ظ�����list->����");
		return map;
	}
	
	/**
	 * ��ĩ�ط�ǰ�����طõ�����־EXPORTFLAG=0
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
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			String message = "BLPrpCustomerMessage->updateDXHFMonth()->��ĩ�ط�ǰ�����طõ�����־EXPORTFLAG=0->"+e.getMessage();
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
				logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
				throw new Exception(e);
			}
		}
	}
	
	
	/**
	 * ��ѯPrpCustomerMessage���issuccess��GXCondition��״̬
	 * @param sql
	 * @throws Exception
	 */
	public Map queryPrpCustomerMessageIssuccess(String sql) throws Exception
	{
		logger.info("BLPrpCustomerMessage->queryPrpCustomerMessageIssuccess()->��ѯPrpCustomerMessage���issuccess��״̬");
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
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			String message = "BLPrpCustomerMessage->queryPrpCustomerMessageIssuccess()->��ѯPrpCustomerMessage���issuccess��״̬->"+e.getMessage();
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
				logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
				throw new Exception(e);
			}
		}
		return map;
	}


	/**
	 * ɾ��PrpCustomerMessage��ָ��ʱ��������
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
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			
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
				logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
				throw new Exception(e);
			}
		}
		
	}
	
	/**
	 * ��XXXXX�������󣬸����������¶�Ϣ�����������£�
	          ���Ͷ���Χ
		1.ȫ�������˷�ӪҵXXXXX��XXXXXXXXXX
		 ��  �ˣ�PrpPrintPool.GroupFlag=0 
		 ��Ӫҵ��prpcitemcar.usenaturecode=8D||8A ����Ҫ�����ֶΣ�
		 ��  XXXXX��PrpPrintPool.classcode = '05' �����ñ��жϣ�
		 ��  XXXXX��prpCmain.othflag��һλΪ1 ����Ҫ�����ֶΣ�
		2.�޳�Ħ�г���������
		 prpcitemcar.carkindcode not like 'M%' and prpcitemcar.carkindcode not like 'J%'
		3.�޳���XXXXX����3�Σ���������XXXXX���Խ᰸�жϳ�XXXXX����
		  prpintefinfo.lastClaimCount��XXXXX������
		4.�޳��ֻ������ظ���һ�������Ӧ����������XXXXX��������ģ������Ϻ������򲻴��ڣ���ϵͳĬ�����жϹ��������޸�
		5.�޳��Ϻ������������ϡ�ɽ���������˲�����XXXXX
		  ����PrpPrintPool.Comcode��ǰ��λ���ֻ���(�����˲���ǰ��λ)���Ϻ� 07�� ���� 34������ 24��ɽ�� 02�������˲� 2656
	 * @throws Exception 
	 */
	public void updateXBMessage(List prpCustomerMessageList) {
		logger.info("BLPrpCustomerMessage->updateXBMessage()->��XXXXX�������󣬿�ʼ");
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
					message="���ã����ĳ���"+licenseNo+"������˾˳��XX�������յ�XX�����������Ķ�XX�������Ϣ��" +
							"��ϲ��";
					prpCustomerMessageSchema.setMessage(message);
					prpCustomerMessageList.set(i, prpCustomerMessageSchema);
				}
				
			}
		}catch(Exception e){
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			String message = "BLPrpCustomerMessage->updateXBMessage()->��XXXXX��������->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("BLPrpCustomerMessage.updateXBMessage", message,e,
					TaskMngUtil.CUSTOMERREALRUN_JOBNAME);
		}
		logger.info("BLPrpCustomerMessage->updateXBMessage()->��XXXXX�������󣬽���");
	}
}