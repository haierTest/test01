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
	 * ����Ҫ�طõĵ绰����Ϣ����XXXXX�绰��
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
			logger.info("BLPrpCustomerMessageMobile->insertPrpCustomerMessageMobile()->�жϺ����Ƿ��Ѵ���->��ʼ");
			count = dbPrpCustomerMessageMobile.getCount(dbpool, prpCustomerMessageMobileSchema);
			logger.info("BLPrpCustomerMessageMobile->insertPrpCustomerMessageMobile()->�жϺ����Ƿ��Ѵ���->����    count:(1���ڣ�0������)"+count);
			if(count==0)
			{
				logger.info("BLPrpCustomerMessageMobile->insertPrpCustomerMessageMobile()->��������Ƿ�ɹ�->��ʼ");
				count = dbPrpCustomerMessageMobile.insertPrpCustomerMessageMobile(dbpool, prpCustomerMessageMobileSchema);
				logger.info("BLPrpCustomerMessageMobile->insertPrpCustomerMessageMobile()->��������Ƿ�ɹ�->����    count:(1ʧ�ܣ�0�ɹ�)"+count);
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
		return count;
	}
	/**
	 * ��ȡ�ط�����
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
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			
			String message = "BLPrpCustomerMessageMobile->getVisaData()->��ȡ�ط�����->"+e.getMessage();
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
				logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
				throw new Exception(e);
			}
		}
		return list;
	}
	/**
	 * ���¶�ȡ״̬
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
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			
			String message = "BLPrpCustomerMessageMobile->updatePrpCustomerMessageMobileExportFlag()->���¶�ȡ״̬->"+e.getMessage();
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
				logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
				throw new Exception(e);
			}
		}
	}
}
