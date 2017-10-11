package com.sp.customerreal.blsvr;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sp.customerreal.dbsvr.DBPrpCustomerMessageMobile;
import com.sp.customerreal.schema.PrpCustomerMessageMobileSchema;
import com.sp.taskmng.util.TaskMngUtil;
import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;

public class BLPrpCustomerMessageMobile {

	private final Log logger = LogFactory.getLog(getClass());
	
	/**
	 * 把需要回访的电话等信息插入XXXXX电话表
	 * @param prpCustomerMessageMobileSchema
	 * @return
	 * @throws Exception
	 */
	public int insertPrpCustomerMessageMobile(PrpCustomerMessageMobileSchema prpCustomerMessageMobileSchema) throws Exception
	{
		int count = 1;
		DBPrpCustomerMessageMobile dbPrpCustomerMessageMobile = new DBPrpCustomerMessageMobile();
		DbPool dbpool = new DbPool();
		try{
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			logger.info("BLPrpCustomerMessageMobile->insertPrpCustomerMessageMobile()->判断号码是否已存在->开始");
			count = dbPrpCustomerMessageMobile.getCount(dbpool, prpCustomerMessageMobileSchema);
			logger.info("BLPrpCustomerMessageMobile->insertPrpCustomerMessageMobile()->判断号码是否已存在->结束    count:(1存在，0不存在)"+count);
			if(count==0)
			{
				logger.info("BLPrpCustomerMessageMobile->insertPrpCustomerMessageMobile()->号码插入是否成功->开始");
				count = dbPrpCustomerMessageMobile.insertPrpCustomerMessageMobile(dbpool, prpCustomerMessageMobileSchema);
				logger.info("BLPrpCustomerMessageMobile->insertPrpCustomerMessageMobile()->号码插入是否成功->结束    count:(1失败，0成功)"+count);
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
		return count;
	}
	/**
	 * 获取回访名单
	 * @return
	 * @throws Exception
	 */
	public List getVisaData() throws Exception
	{
		DBPrpCustomerMessageMobile dbPrpCustomerMessageMobile = new DBPrpCustomerMessageMobile();
		DbPool dbpool = new DbPool();
		List list = new ArrayList();
		try{
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			list = dbPrpCustomerMessageMobile.getVisaData(dbpool);
		}catch(Exception e)
		{
			logger.error("XXXXX真实性错误信息：",e);
			
			String message = "BLPrpCustomerMessageMobile->getVisaData()->获取回访名单->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("BLPrpCustomerMessageMobile.getVisaData", message,e,
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
	 * 更新读取状态
	 * @param list
	 * @throws Exception
	 */
	public void updatePrpCustomerMessageMobileExportFlag(List list) throws Exception
	{
		if(list==null || list.size()<1)
			return;
		DBPrpCustomerMessageMobile dbPrpCustomerMessageMobile = new DBPrpCustomerMessageMobile();
		DbPool dbpool = new DbPool();
		try{
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			PrpCustomerMessageMobileSchema prpCustomerMessageMobileSchema = null;
			for(int i=0;i<list.size();i++)
			{
				prpCustomerMessageMobileSchema = (PrpCustomerMessageMobileSchema)list.get(i);
				dbPrpCustomerMessageMobile.updatePrpCustomerMessageMobileExportFlag(dbpool,prpCustomerMessageMobileSchema.getId(),prpCustomerMessageMobileSchema.getExportFlag());
			}
		}catch(Exception e)
		{
			logger.error("XXXXX真实性错误信息：",e);
			
			String message = "BLPrpCustomerMessageMobile->updatePrpCustomerMessageMobileExportFlag()->更新读取状态->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("BLPrpCustomerMessageMobile.updatePrpCustomerMessageMobileExportFlag", message,e,
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
}
