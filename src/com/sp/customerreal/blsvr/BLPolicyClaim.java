package com.sp.customerreal.blsvr;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sp.customerreal.dbsvr.DBPolicyClaim;
import com.sp.customerreal.schema.CustomerClaimSchema;
import com.sp.customerreal.schema.CustomerPolicySchema;
import com.sp.taskmng.util.TaskMngUtil;
import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;

public class BLPolicyClaim {

	private final Log logger = LogFactory.getLog(getClass());

	/**
	 * ��ȡ��Ҫ�طõ�XX��������
	 * 
	 * @return
	 * @throws Exception
	 */
	public List getSignPolicyData() throws Exception {
		logger.info("BLPolicyClaim->getSignPolicyData()->��ȡ��Ҫ�طõ�XX��������׼��д��95510�ӿڱ�->��ʼ");
		DBPolicyClaim dbPolicyClaim = new DBPolicyClaim();
		DbPool dbpool = new DbPool();
		List list = null;
		try {
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			
			list = dbPolicyClaim.getSignPolicyData(dbpool);
		} catch (Exception e) {
			logger.error("XXXXX��ʵ�Դ�����Ϣ��", e);
			
			String message = "BLPolicyClaim->getSignPolicyData()->��ȡ��Ҫ�طõ�XX��������->"
					+ e.getMessage();
			TaskMngUtil.insertMidDataLog("BLPolicyClaim.getSignPolicyData", message, e,
					TaskMngUtil.CUSTOMERREALCOMPLETE_JOBNAME);
			
		} finally {
			try {
				if (dbpool != null) {
					dbpool.close();
				}
				if (dbpool != null) {
					dbpool.close();
				}
			} catch (SQLException e) {
				logger.error("XXXXX��ʵ�Դ�����Ϣ��", e);
				throw new Exception(e);
			}
		}
		logger.info("BLPolicyClaim->getSignPolicyData()->��ȡ��Ҫ�طõ�XX��������׼��д��95510�ӿڱ�->����");
		return list;
	}
	
	
	/**
	 * ��ȡ������Ҫ�طõ�XX�������� add by linqian 20141216
	 * 
	 * @return
	 * @throws Exception
	 */
	public List getSignPolicyGXData() throws Exception {
		logger.info("BLPolicyClaim->getSignPolicyData()->��ȡ��Ҫ�طõ�XX��������׼��д��95510�ӿڱ�->��ʼ");
		DBPolicyClaim dbPolicyClaim = new DBPolicyClaim();
		DbPool dbpool = new DbPool();
		List list = null;
		try {
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			
			list = dbPolicyClaim.getSignPolicyGXData(dbpool);
		} catch (Exception e) {
			logger.error("XXXXX��ʵ�Դ�����Ϣ��", e);
			
			String message = "BLPolicyClaim->getSignPolicyGXData()->��ȡ��Ҫ�طõ�XX��������->"
					+ e.getMessage();
			TaskMngUtil.insertMidDataLog("BLPolicyClaim.getSignPolicyGXData", message, e,
					TaskMngUtil.CUSTOMERREALCOMPLETE_JOBNAME);
			
		} finally {
			try {
				if (dbpool != null) {
					dbpool.close();
				}
				if (dbpool != null) {
					dbpool.close();
				}
			} catch (SQLException e) {
				logger.error("XXXXX��ʵ�Դ�����Ϣ��", e);
				throw new Exception(e);
			}
		}
		logger
				.info("BLPolicyClaim->getSignPolicyGXData()->��ȡ��Ҫ�طõ�XX��������׼��д��95510�ӿڱ�->����");
		return list;
	}
	
	/**
	 * ��ȡ������Ҫ�طõ�XX�������� ǰ��18�㵽����9�� add by linqian 20141216
	 * 
	 * @return
	 * @throws Exception
	 */
	public List getSignPolicyGXTData() throws Exception {
		logger.info("BLPolicyClaim->getSignPolicyData()->��ȡ��Ҫ�طõ�XX��������׼��д��95510�ӿڱ�->��ʼ");
		DBPolicyClaim dbPolicyClaim = new DBPolicyClaim();
		DbPool dbpool = new DbPool();
		Map map = new HashMap();
		List list = null;
		try {
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			String startTime = "";
			String endTime = "";
			String nowDay = "";
			String beforeDay = "";
			{
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			nowDay = simpleDateFormat.format(new Date()); 
			GregorianCalendar gregorianCalendar = new GregorianCalendar();
	        gregorianCalendar.setTime(simpleDateFormat.parse(nowDay));
	        gregorianCalendar.add(GregorianCalendar.DATE, -1);
				beforeDay = simpleDateFormat.format(gregorianCalendar.getTime());  
			}
			logger.info("beforeDay:"+beforeDay+"nowDay:"+nowDay);
			
			startTime = beforeDay + " 18:00:00";
			endTime = nowDay + " 8:59:59";
			map.put("startTime",startTime);
			map.put("endTime",endTime);
			
			list = dbPolicyClaim.getSignPolicyGXTData(dbpool,map);
			logger.info("BLPolicyClaim->getSignPolicyData()->��ȡ��Ҫ�طõ�XX��������׼��д��95510�ӿڱ�->����");
		} catch (Exception e) {
			logger.error("XXXXX��ʵ�Դ�����Ϣ��", e);
			
			String message = "BLPolicyClaim->getSignPolicyGXTData()->��ȡ��Ҫ�طõĹ���XX��������->"
					+ e.getMessage();
			TaskMngUtil.insertMidDataLog("BLPolicyClaim.getSignPolicyGXTData", message, e,
					TaskMngUtil.CUSTOMERREALCOMPLETE_JOBNAME);
			
		} finally {
			try {
				if (dbpool != null) {
					dbpool.close();
				}
			} catch (SQLException e) {
				logger.error("XXXXX��ʵ�Դ�����Ϣ��", e);
				throw new Exception(e);
			}
		}
		logger.info("BLPolicyClaim->getSignPolicyData()->��ȡ��Ҫ�طõ�XX��������׼��д��95510�ӿڱ�->����");
		return list;
	}
	
	/**
	 * @param hour
	 * @return     ˽�з������� add by linqian  20141216
	 * @throws Exception
	 */
	private Map beforeGetPrpCustomerMessagePolicy(String hour,String strBDate,String strNDate) throws Exception 
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
		}
		else
		{
			beforeDay = strBDate;
			nowDay = strNDate;
		}
		logger.info("beforeDay:"+beforeDay+"    nowDay:"+nowDay+"    hour:"+hour);


		
		if("9".equals(hour))
		{
			startTime = beforeDay + " 18:00:00";
			endTime = nowDay + " 8:59:59";
		}
		
		if("12".equals(hour))
		{
			startTime = nowDay + " 9:00:00";
			endTime = nowDay + " 11:59:59";
		}
		
		if("18".equals(hour))
		{
			startTime = nowDay + " 12:00:00";
			endTime = nowDay + " 17:59:59";
		}
		Map map = new HashMap();
		map.put("startTime",startTime);
		map.put("endTime",endTime);
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
	 * ��ȡ������Ҫ�طõ�XX�����������ⷽ�� add by linqian 20141216
	 * 
	 * @return
	 * @throws Exception
	 */
	public List getSignPolicyGXSData(String hour,String strBDate,String strNDate) throws Exception {
		logger.info("BLPolicyClaim->getSignPolicyData()->��ȡ��Ҫ�طõ�XX��������׼��д��95510�ӿڱ�->��ʼ");
		DBPolicyClaim dbPolicyClaim = new DBPolicyClaim();
		DbPool dbpool = new DbPool();
		Map map = new HashMap();
		List list = null;
		try {
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			 map =beforeGetPrpCustomerMessagePolicy(hour, strBDate,strNDate);
			
			list = dbPolicyClaim.getSignPolicyGXTData(dbpool,map);
		} catch (Exception e) {
			logger.error("XXXXX��ʵ�Դ�����Ϣ��", e);
			
			String message = "BLPolicyClaim->getSignPolicyData()->��ȡ��Ҫ�طõ�XX��������->"
					+ e.getMessage();
			TaskMngUtil.insertMidDataLog("BLPolicyClaim.getSignPolicyData", message, e,
					TaskMngUtil.CUSTOMERREALCOMPLETE_JOBNAME);
			
		} finally {
			try {
				if (dbpool != null) {
					dbpool.close();
				}
				if (dbpool != null) {
					dbpool.close();
				}
			} catch (SQLException e) {
				logger.error("XXXXX��ʵ�Դ�����Ϣ��", e);
				throw new Exception(e);
			}
		}
		logger
				.info("BLPolicyClaim->getSignPolicyData()->��ȡ��Ҫ�طõ�XX��������׼��д��95510�ӿڱ�->����");
		return list;
	}
	
	/**
	 * ��ȡ������Ҫ�طõ�XX�����������ⷽ�� add by linqian 20141216
	 * 
	 * @return
	 * @throws Exception
	 */
	public List getSignPolicyGXNData(String hour,String strBDate,String strNDate) throws Exception {
		logger.info("BLPolicyClaim->getSignPolicyData()->��ȡ��Ҫ�طõ�XX��������׼��д��95510�ӿڱ�->��ʼ");
		DBPolicyClaim dbPolicyClaim = new DBPolicyClaim();
		DbPool dbpool = new DbPool();
		Map map = new HashMap();
		List list = null;
		try {
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			 map =beforeGetPrpCustomerMessagePolicyGX(hour, strBDate,strNDate);
			
			list = dbPolicyClaim.getSignPolicyGXTData(dbpool,map);
		} catch (Exception e) {
			logger.error("XXXXX��ʵ�Դ�����Ϣ��", e);
			
			String message = "BLPolicyClaim->getSignPolicyData()->��ȡ��Ҫ�طõ�XX��������->"
					+ e.getMessage();
			TaskMngUtil.insertMidDataLog("BLPolicyClaim.getSignPolicyData", message, e,
					TaskMngUtil.CUSTOMERREALCOMPLETE_JOBNAME);
			
		} finally {
			try {
				if (dbpool != null) {
					dbpool.close();
				}
				if (dbpool != null) {
					dbpool.close();
				}
			} catch (SQLException e) {
				logger.error("XXXXX��ʵ�Դ�����Ϣ��", e);
				throw new Exception(e);
			}
		}
		logger
				.info("BLPolicyClaim->getSignPolicyData()->��ȡ��Ҫ�طõ�XX��������׼��д��95510�ӿڱ�->����");
		return list;
	}

	/**
	 * ��ȡ��Ҫ�طõ�XX�ŵ�����
	 * 
	 * @return
	 * @throws Exception
	 */
	public List getGroupPolicyData() throws Exception {
		logger
				.info("BLPolicyClaim->getGroupPolicyData()->��ȡ��Ҫ�طõ�XX�ŵ�����׼��д��95510�ӿڱ�->��ʼ");
		DBPolicyClaim dbPolicyClaim = new DBPolicyClaim();
		DbPool dbpool = new DbPool();
		List list = null;
		try {
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			
			list = dbPolicyClaim.getGroupPolicyData(dbpool);
		} catch (Exception e) {
			logger.error("XXXXX��ʵ�Դ�����Ϣ��", e);
			
			String message = "BLPolicyClaim->getGroupPolicyData()->��ȡ��Ҫ�طõ�XX�ŵ�����->"
					+ e.getMessage();
			TaskMngUtil.insertMidDataLog("BLPolicyClaim.getGroupPolicyData", message, e,
					TaskMngUtil.CUSTOMERREALCOMPLETE_JOBNAME);
			
		} finally {
			try {
				if (dbpool != null) {
					dbpool.close();
				}
				if (dbpool != null) {
					dbpool.close();
				}
			} catch (SQLException e) {
				logger.error("XXXXX��ʵ�Դ�����Ϣ��", e);
				throw new Exception(e);
			}
		}
		logger
				.info("BLPolicyClaim->getGroupPolicyData()->��ȡ��Ҫ�طõ�XX�ŵ�����׼��д��95510�ӿڱ�->����");
		return list;
	}

	/**
	 * ��ȡ��Ҫ�طõ�XXXXX��������
	 * 
	 * @return
	 * @throws Exception
	 */
	public List getSignClaimData() throws Exception {
		logger
				.info("BLPolicyClaim->getSignClaimData()->��ȡ��Ҫ�طõ�XXXXX��������׼��д��95510�ӿڱ�->��ʼ");
		DBPolicyClaim dbPolicyClaim = new DBPolicyClaim();
		DbPool dbpool = new DbPool();
		List list = null;
		try {
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			
			list = dbPolicyClaim.getSignClaimData(dbpool);
		} catch (Exception e) {
			logger.error("XXXXX��ʵ�Դ�����Ϣ��", e);
			
			String message = "BLPolicyClaim->getSignClaimData()->��ȡ��Ҫ�طõ�XXXXX��������->"
					+ e.getMessage();
			TaskMngUtil.insertMidDataLog("BLPolicyClaim.getSignClaimData", message, e,
					TaskMngUtil.CUSTOMERREALCOMPLETE_JOBNAME);
			
		} finally {
			try {
				if (dbpool != null) {
					dbpool.close();
				}
				if (dbpool != null) {
					dbpool.close();
				}
			} catch (SQLException e) {
				logger.error("XXXXX��ʵ�Դ�����Ϣ��", e);
				throw new Exception(e);
			}
		}
		logger
				.info("BLPolicyClaim->getSignClaimData()->��ȡ��Ҫ�طõ�XXXXX��������׼��д��95510�ӿڱ�->����");
		return list;
	}

	/**
	 * ��ȡ��Ҫ�طõ�XXXXX�ŵ�����
	 * 
	 * @return
	 * @throws Exception
	 */
	public List getGroupClaimData() throws Exception {
		logger
				.info("BLPolicyClaim->getGroupClaimData()->��ȡ��Ҫ�طõ�XXXXX�ŵ�����׼��д��95510�ӿڱ�->��ʼ");
		DBPolicyClaim dbPolicyClaim = new DBPolicyClaim();
		DbPool dbpool = new DbPool();
		List list = null;
		try {
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			
			list = dbPolicyClaim.getGroupClaimData(dbpool);
		} catch (Exception e) {
			logger.error("XXXXX��ʵ�Դ�����Ϣ��", e);
			
			String message = "BLPolicyClaim->getGroupClaimData()->��ȡ��Ҫ�طõ�XXXXX�ŵ�����->"
					+ e.getMessage();
			TaskMngUtil.insertMidDataLog("BLPolicyClaim.getGroupClaimData", message, e,
					TaskMngUtil.CUSTOMERREALCOMPLETE_JOBNAME);
			
		} finally {
			try {
				if (dbpool != null) {
					dbpool.close();
				}
				if (dbpool != null) {
					dbpool.close();
				}
			} catch (SQLException e) {
				logger.error("XXXXX��ʵ�Դ�����Ϣ��", e);
				throw new Exception(e);
			}
		}
		logger
				.info("BLPolicyClaim->getGroupClaimData()->��ȡ��Ҫ�طõ�XXXXX�ŵ�����׼��д��95510�ӿڱ�->����");
		return list;
	}

	/**
	 * XX��������д��95510�ӿڱ�
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public List insertSignPolicyData(List list) throws Exception {
		logger.info("BLPolicyClaim->insertSignPolicyData()->XX��������д��95510�ӿڱ�->��ʼ");
		if (list == null || list.size() < 1)
			return null;
		DBPolicyClaim dbPolicyClaim = new DBPolicyClaim();
		DbPool dbpool = new DbPool();
		try {
			dbpool.open(SysConfig.CONST_CALLDATASOURCE);
			CustomerPolicySchema customerPolicySchema = null;
			for (int i = 0; i < list.size(); i++) {
				customerPolicySchema = (CustomerPolicySchema) list.get(i);
				dbPolicyClaim.insertSignPolicyData(dbpool, customerPolicySchema);
				dbPolicyClaim.insertCifcbTel(dbpool, customerPolicySchema);
			}
		} catch (Exception e) {
			logger.error("XXXXX��ʵ�Դ�����Ϣ��", e);
			
			String message = "DBPolicyClaim->insertSignPolicyData()->XX��������д��95510�ӿڱ�"+e.getMessage();
			TaskMngUtil.insertMidDataLog("BLPolicyClaim.insertSignPolicyData", message,e,
					TaskMngUtil.CUSTOMERREALCOMPLETE_JOBNAME);
			
			logger.info("BLPolicyClaim->insertSignPolicyData()->XX��������д��95510�ӿڱ�->����");
		} finally {
			try {
				if (dbpool != null) {
					dbpool.close();
				}
			} catch (SQLException e) {
				logger.error("XXXXX��ʵ�Դ�����Ϣ��", e);
				throw new Exception(e);
			}
		}
		logger.info("BLPolicyClaim->insertSignPolicyData()->XX��������д��95510�ӿڱ�->����");
		return list;
	}

	/**
	 * XX�ŵ�����д��95510�ӿڱ�
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public List insertGroupPolicyData(List list) throws Exception {
		logger
				.info("BLPolicyClaim->insertGroupPolicyData()->XX�ŵ�����д��95510�ӿڱ�->��ʼ");
		if (list == null || list.size() < 1)
			return null;
		DBPolicyClaim dbPolicyClaim = new DBPolicyClaim();
		DbPool dbpool = new DbPool();
		try {
			dbpool.open(SysConfig.CONST_CALLDATASOURCE);
			CustomerPolicySchema customerPolicySchema = null;
			for (int i = 0; i < list.size(); i++) {
				customerPolicySchema = (CustomerPolicySchema) list.get(i);
				dbPolicyClaim.insertGroupPolicyData(dbpool,
						customerPolicySchema);
			}
		} catch (Exception e) {
			logger.error("XXXXX��ʵ�Դ�����Ϣ��", e);
			
			String message = "DBPolicyClaim->insertGroupPolicyData()->XX�ŵ�����д��95510�ӿڱ�"+e.getMessage();
			TaskMngUtil.insertMidDataLog("BLPolicyClaim.insertGroupPolicyData", message,e,
					TaskMngUtil.CUSTOMERREALCOMPLETE_JOBNAME);
			
		} finally {
			try {
				if (dbpool != null) {
					dbpool.close();
				}
				if (dbpool != null) {
					dbpool.close();
				}
			} catch (SQLException e) {
				logger.error("XXXXX��ʵ�Դ�����Ϣ��", e);
				throw new Exception(e);
			}
		}
		logger
				.info("BLPolicyClaim->insertGroupPolicyData()->XX�ŵ�����д��95510�ӿڱ�->����");
		return list;
	}

	/**
	 * XXXXX��������д��95510�ӿڱ�
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public List insertSignClaimData(List list) throws Exception {
		logger
				.info("BLPolicyClaim->insertSignClaimData()->XXXXX��������д��95510�ӿڱ�->��ʼ");
		if (list == null || list.size() < 1)
			return null;
		DBPolicyClaim dbPolicyClaim = new DBPolicyClaim();
		DbPool dbpool = new DbPool();
		try {
			dbpool.open(SysConfig.CONST_CALLDATASOURCE);
			CustomerClaimSchema customerClaimSchema = null;
			for (int i = 0; i < list.size(); i++) {
				customerClaimSchema = (CustomerClaimSchema) list.get(i);
				dbPolicyClaim.insertSignClaimData(dbpool, customerClaimSchema);
				dbPolicyClaim.insertCiflpTel(dbpool, customerClaimSchema);
			}
		} catch (Exception e) {
			logger.error("XXXXX��ʵ�Դ�����Ϣ��", e);
			
			String message = "DBPolicyClaim->insertSignClaimData()->XXXXX��������д��95510�ӿڱ�"+e.getMessage();
			TaskMngUtil.insertMidDataLog("BLPolicyClaim.insertSignClaimData", message,e,
					TaskMngUtil.CUSTOMERREALCOMPLETE_JOBNAME);
			
		} finally {
			try {
				if (dbpool != null) {
					dbpool.close();
				}
				if (dbpool != null) {
					dbpool.close();
				}
			} catch (SQLException e) {
				logger.error("XXXXX��ʵ�Դ�����Ϣ��", e);
				throw new Exception(e);
			}
		}
		logger
				.info("BLPolicyClaim->insertSignClaimData()->XXXXX��������д��95510�ӿڱ�->����");
		return list;
	}

	/**
	 * XXXXX�ŵ�����д��95510�ӿڱ�
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public List insertGroupClaimData(List list) throws Exception {
		logger
				.info("BLPolicyClaim->insertGroupClaimData()->XXXXX�ŵ�����д��95510�ӿڱ�->��ʼ");
		if (list == null || list.size() < 1)
			return null;
		DBPolicyClaim dbPolicyClaim = new DBPolicyClaim();
		DbPool dbpool = new DbPool();
		try {
			dbpool.open(SysConfig.CONST_CALLDATASOURCE);
			CustomerClaimSchema customerClaimSchema = null;
			for (int i = 0; i < list.size(); i++) {
				customerClaimSchema = (CustomerClaimSchema) list.get(i);
				dbPolicyClaim.insertGroupClaimData(dbpool, customerClaimSchema);
			}
		} catch (Exception e) {
			logger.error("XXXXX��ʵ�Դ�����Ϣ��", e);
			
			String message = "DBPolicyClaim->insertGroupClaimData()->XXXXX�ŵ�����д��95510�ӿڱ�"+e.getMessage();
			TaskMngUtil.insertMidDataLog("BLPolicyClaim.insertGroupClaimData", message,e,
					TaskMngUtil.CUSTOMERREALCOMPLETE_JOBNAME);
			
		} finally {
			try {
				if (dbpool != null) {
					dbpool.close();
				}
				if (dbpool != null) {
					dbpool.close();
				}
			} catch (SQLException e) {
				logger.error("XXXXX��ʵ�Դ�����Ϣ��", e);
				throw new Exception(e);
			}
		}
		logger
				.info("BLPolicyClaim->insertGroupClaimData()->XXXXX�ŵ�����д��95510�ӿڱ�->����");
		return list;
	}
	
	/**
	 * XXXXX100%�طã���ȡ��Ҫ�طõ�XXXXX��������
	 * 
	 * @return
	 * @throws Exception
	 */
	public List getSignClaim() throws Exception {
		logger
				.info("BLPolicyClaim->getSignClaim()->XXXXX100%�ط�,��ȡ��Ҫ�طõ�XXXXX��������׼��д��95510�ӿڱ�->��ʼ");
		DBPolicyClaim dbPolicyClaim = new DBPolicyClaim();
		DbPool dbpool = new DbPool();
		List list = null;
		try {
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			
			list = dbPolicyClaim.getSignClaim(dbpool);
		} catch (Exception e) {
			logger.error("XXXXX��ʵ�Դ�����Ϣ��", e);
			
			String message = "BLPolicyClaim->getSignClaim()->XXXXX100%�ط�,��ȡ��Ҫ�طõ�XXXXX��������׼��д��95510�ӿڱ�->"
					+ e.getMessage();
			TaskMngUtil.insertMidDataLog("BLPolicyClaim.getSignClaim", message, e,
					TaskMngUtil.CLAIMISSATISFIED_JOBNAME);
			
		} finally {
			try {
				if (dbpool != null) {
					dbpool.close();
				}
				if (dbpool != null) {
					dbpool.close();
				}
			} catch (SQLException e) {
				logger.error("XXXXX��ʵ�Դ�����Ϣ��", e);
				throw new Exception(e);
			}
		}
		logger
				.info("BLPolicyClaim->getSignClaim()->XXXXX100%�ط�,��ȡ��Ҫ�طõ�XXXXX��������׼��д��95510�ӿڱ�->����");
		return list;
	}

	/**
	 * XXXXX100%�طã���ȡ��Ҫ�طõ�XXXXX�ŵ�����
	 * 
	 * @return
	 * @throws Exception
	 */
	public List getGroupClaim() throws Exception {
		logger
				.info("BLPolicyClaim->getGroupClaimData()->��ȡ��Ҫ�طõ�XXXXX�ŵ�����׼��д��95510�ӿڱ�->��ʼ");
		DBPolicyClaim dbPolicyClaim = new DBPolicyClaim();
		DbPool dbpool = new DbPool();
		List list = null;
		try {
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			
			list = dbPolicyClaim.getGroupClaim(dbpool);
		} catch (Exception e) {
			logger.error("XXXXX��ʵ�Դ�����Ϣ��", e);
			
			String message = "BLPolicyClaim->getGroupClaimData()->��ȡ��Ҫ�طõ�XXXXX�ŵ�����->"
					+ e.getMessage();
			TaskMngUtil.insertMidDataLog("BLPolicyClaim.getGroupClaimData", message, e,
					TaskMngUtil.CLAIMISSATISFIED_JOBNAME);
			
		} finally {
			try {
				if (dbpool != null) {
					dbpool.close();
				}
				if (dbpool != null) {
					dbpool.close();
				}
			} catch (SQLException e) {
				logger.error("XXXXX��ʵ�Դ�����Ϣ��", e);
				throw new Exception(e);
			}
		}
		logger
				.info("BLPolicyClaim->getGroupClaimData()->��ȡ��Ҫ�طõ�XXXXX�ŵ�����׼��д��95510�ӿڱ�->����");
		return list;
	}
	
	/**
	 * XXXXX100%�طã�XXXXX��������д��95510�ӿڱ�
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public List insertSignClaim(List list) throws Exception {
		logger
				.info("BLPolicyClaim->insertSignClaim()->XXXXX��������д��95510�ӿڱ�->��ʼ");
		if (list == null || list.size() < 1)
			return null;
		DBPolicyClaim dbPolicyClaim = new DBPolicyClaim();
		DbPool dbpool = new DbPool();
		try {
			dbpool.open(SysConfig.CONST_CALLDATASOURCE);
			CustomerClaimSchema customerClaimSchema = null;
			for (int i = 0; i < list.size(); i++) {
				customerClaimSchema = (CustomerClaimSchema) list.get(i);
				dbPolicyClaim.insertSignClaim(dbpool, customerClaimSchema);
				dbPolicyClaim.insertCifLPMTel(dbpool, customerClaimSchema);
			}
		} catch (Exception e) {
			logger.error("XXXXX��ʵ�Դ�����Ϣ��", e);
			
			String message = "DBPolicyClaim->insertSignClaim()->XXXXX��������д��95510�ӿڱ�"+e.getMessage();
			TaskMngUtil.insertMidDataLog("BLPolicyClaim.insertSignClaimData", message,e,
					TaskMngUtil.CLAIMISSATISFIED_JOBNAME);
			
		} finally {
			try {
				if (dbpool != null) {
					dbpool.close();
				}
			} catch (SQLException e) {
				logger.error("XXXXX��ʵ�Դ�����Ϣ��", e);
				throw new Exception(e);
			}
		}
		logger
				.info("BLPolicyClaim->insertSignClaim()->XXXXX��������д��95510�ӿڱ�->����");
		return list;
	}

	/**
	 * XXXXX100%�طã�XXXXX�ŵ�����д��95510�ӿڱ�
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public List insertGroupClaim(List list) throws Exception {
		logger
				.info("BLPolicyClaim->insertGroupClaim()->XXXXX�ŵ�����д��95510�ӿڱ�->��ʼ");
		if (list == null || list.size() < 1)
			return null;
		DBPolicyClaim dbPolicyClaim = new DBPolicyClaim();
		DbPool dbpool = new DbPool();
		try {
			dbpool.open(SysConfig.CONST_CALLDATASOURCE);
			CustomerClaimSchema customerClaimSchema = null;
			for (int i = 0; i < list.size(); i++) {
				customerClaimSchema = (CustomerClaimSchema) list.get(i);
				dbPolicyClaim.insertGroupClaim(dbpool, customerClaimSchema);
				dbPolicyClaim.insertCifLPMGroupTel(dbpool, customerClaimSchema);
			}
		} catch (Exception e) {
			logger.error("XXXXX��ʵ�Դ�����Ϣ��", e);
			
			String message = "DBPolicyClaim->insertGroupClaim()->XXXXX�ŵ�����д��95510�ӿڱ�"+e.getMessage();
			TaskMngUtil.insertMidDataLog("BLPolicyClaim.insertGroupClaim", message,e,
					TaskMngUtil.CLAIMISSATISFIED_JOBNAME);
			
		} finally {
			try {
				if (dbpool != null) {
					dbpool.close();
				}
			} catch (SQLException e) {
				logger.error("XXXXX��ʵ�Դ�����Ϣ��", e);
				throw new Exception(e);
			}
		}
		logger
				.info("BLPolicyClaim->insertGroupClaim()->XXXXX�ŵ�����д��95510�ӿڱ�->����");
		return list;
	}
	
	/**
	 * XX����-ȡ��XX��ֱ�����������ˡ���ͥ���ó�,��XX���ݵ����������ڶ���Ż�ƥ��cif���ݣ����ȡ��ʱ��ȡһ�졣
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List getNewPolicy(String sql) throws Exception{
		logger.info("XX����ȡ��������list -> ��ʼ");
		List list = new ArrayList();
		DbPool dbpool = new DbPool();
		try {
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			dbpool.prepareInnerStatement(sql);
			ResultSet resultSet = dbpool.executePreparedQuery();
			CustomerPolicySchema customerPolicySchema = null;
			while(resultSet.next())
			{
				customerPolicySchema = new CustomerPolicySchema();
				customerPolicySchema.setAddress(dbpool.getString(resultSet,"Address"));
				customerPolicySchema.setBirthDate(""+dbpool.getDateTime(resultSet,"BirthDate"));
				customerPolicySchema.setBusinessNature(dbpool.getString(resultSet,"BusinessNaturename"));
				customerPolicySchema.setComCName(dbpool.getString(resultSet,"CENTERORHANDLCOMNAME"));
				customerPolicySchema.setComCode(dbpool.getString(resultSet,"COMCODE"));
				customerPolicySchema.setComName(dbpool.getString(resultSet,"COMNAME"));
				customerPolicySchema.setComName1(dbpool.getString(resultSet,"SURVEYORHANDLCOMNAME1"));
				customerPolicySchema.setComName2(dbpool.getString(resultSet,"SURVEYORHANDLCOMNAME2"));
				customerPolicySchema.setCustomerCName(dbpool.getString(resultSet,"CUSTOMERNAME"));
				customerPolicySchema.setCustomerId(dbpool.getString(resultSet,"CUSTOMERCODE"));
				customerPolicySchema.setHandler(dbpool.getString(resultSet,"SURVEYORHANDLNAME"));
				customerPolicySchema.setHandlerCom(dbpool.getString(resultSet,"CENTERORHANDLCOMCODE"));
				customerPolicySchema.setHandlerCom1(dbpool.getString(resultSet,"SURVEYORHANDLCOMCODE1"));
				customerPolicySchema.setHandlerCom2(dbpool.getString(resultSet,"SURVEYORHANDLCOMCODE2"));
				customerPolicySchema.setHandlerId(dbpool.getString(resultSet,"SURVEYORHANDLID"));
				customerPolicySchema.setIdentifyNumber(dbpool.getString(resultSet,"IDENTIFYNUMBER"));
				customerPolicySchema.setIdentifyType(dbpool.getString(resultSet,"IDENTIFYTYPE"));
				customerPolicySchema.setLinkName(dbpool.getString(resultSet,"LinkName"));
				customerPolicySchema.setMobile(dbpool.getString(resultSet,"Mobile"));
				customerPolicySchema.setOccupationCode(dbpool.getString(resultSet,"OCCUPATIONNAME"));
				customerPolicySchema.setOperator(dbpool.getString(resultSet,"OPERATORNAME"));
				customerPolicySchema.setOperatorId(dbpool.getString(resultSet,"OPERATORCODE"));
				customerPolicySchema.setOrganizeCode(dbpool.getString(resultSet,"ORGANIZECODE"));
				customerPolicySchema.setPhone(dbpool.getString(resultSet,"Phone"));
				customerPolicySchema.setPolicyNo(dbpool.getString(resultSet,"PolicyNo"));
				customerPolicySchema.setSex(dbpool.getString(resultSet,"Sex"));
				customerPolicySchema.setUnderWriteEndDate(""+dbpool.getDateTime(resultSet,"UnderWriteEndDate"));
				customerPolicySchema.setId(dbpool.getString(resultSet,"id"));
				customerPolicySchema.setChannelType(dbpool.getString(resultSet,"channelType"));
				customerPolicySchema.setVentureflag(dbpool.getString(resultSet,"ventureflag"));
				
				customerPolicySchema.setCif_custid(dbpool.getString(resultSet,"cif_custid"));
				customerPolicySchema.setCan_custid(dbpool.getString(resultSet,"CUSTOMERCODE"));
				customerPolicySchema.setMobile1(dbpool.getString(resultSet,"mobile1"));
				customerPolicySchema.setMobile2(dbpool.getString(resultSet,"mobile2"));
				customerPolicySchema.setMobile3(dbpool.getString(resultSet,"mobile3"));
				customerPolicySchema.setMobile4(dbpool.getString(resultSet,"mobile"));
				customerPolicySchema.setKxd1(dbpool.getString(resultSet,"kxd1"));
				customerPolicySchema.setKxd2(dbpool.getString(resultSet,"kxd2"));
				customerPolicySchema.setKxd3(dbpool.getString(resultSet,"kxd3"));
				
				customerPolicySchema.setResource1(dbpool.getString(resultSet,"resource1"));
				customerPolicySchema.setResource2(dbpool.getString(resultSet,"resource2"));
				customerPolicySchema.setResource3(dbpool.getString(resultSet,"resource3"));
				
				customerPolicySchema.setModifydate1(""+dbpool.getDateTime(resultSet,"modifydate1"));
				customerPolicySchema.setModifydate2(""+dbpool.getDateTime(resultSet,"modifydate2"));
				customerPolicySchema.setModifydate3(""+dbpool.getDateTime(resultSet,"modifydate3"));
				
				
				customerPolicySchema.setInsurebranchid(dbpool.getString(resultSet,"SURVEYORHANDLCOMCODE1"));
				customerPolicySchema.setInsurebranchid2(dbpool.getString(resultSet,"SURVEYORHANDLCOMCODE2"));
				customerPolicySchema.setSurebranchname(dbpool.getString(resultSet,"SURVEYORHANDLCOMNAME1"));
				customerPolicySchema.setSurebranch2name(dbpool.getString(resultSet,"SURVEYORHANDLCOMNAME2"));
				customerPolicySchema.setManagername(dbpool.getString(resultSet,"SURVEYORHANDLNAME"));
				customerPolicySchema.setManagertel(dbpool.getString(resultSet,"managertel"));
				list.add(customerPolicySchema);
			}
			resultSet.close();
		} catch (Exception e) {
			logger.error("XXXXX��ʵ�Դ�����Ϣ��", e);
			String message = "BLPolicyClaim->getNewPolicy()->XX����ȡ��������list->"+ e.getMessage();
			TaskMngUtil.insertMidDataLog("BLPolicyClaim.getNewPolicy", message, e,TaskMngUtil.CUSTOMERREALRUN_JOBNAME);
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
		logger.info("XX����ȡ��������list-> ����");
		return list;
	}
	
	/**
	 * XX����-����95510
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List insertNewPolicyData(List list) throws Exception {
		logger.info("BLPolicyClaim->insertNewPolicyData()->XX����-����95510�ӿڱ�->��ʼ");
		if (list == null || list.size() < 1){
			logger.info("δ��ѯ������������XX���ݣ�");
			return null;
		}	
		DBPolicyClaim dbPolicyClaim = new DBPolicyClaim();
		DbPool dbpool = new DbPool();
		try {
			dbpool.open(SysConfig.CONST_CALLDATASOURCE);
			CustomerPolicySchema customerPolicySchema = null;
			for (int i = 0; i < list.size(); i++) {
				customerPolicySchema = (CustomerPolicySchema) list.get(i);
				dbPolicyClaim.insertNewPolicyData(dbpool, customerPolicySchema);
				dbPolicyClaim.insertCifNewPolicy(dbpool, customerPolicySchema);
			}
		} catch (Exception e) {
			logger.error("XXXXX��ʵ�Դ�����Ϣ��", e);
			
			String message = "DBPolicyClaim->insertNewPolicyData()->XX����-����95510�ӿڱ�"+e.getMessage();
			TaskMngUtil.insertMidDataLog("BLPolicyClaim.insertNewPolicyData", message,e,
					TaskMngUtil.CUSTOMERREALCOMPLETE_JOBNAME);
			
		} finally {
			try {
				if (dbpool != null) {
					dbpool.close();
				}
			} catch (SQLException e) {
				logger.error("XXXXX��ʵ�Դ�����Ϣ��", e);
				throw new Exception(e);
			}
		}
		logger.info("BLPolicyClaim->insertNewPolicyData()->XX����-����95510�ӿڱ�->����");
		return list;
	}
}
