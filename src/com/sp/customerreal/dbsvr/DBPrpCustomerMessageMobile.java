package com.sp.customerreal.dbsvr;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sp.customerreal.schema.PrpCustomerMessageMobileSchema;
import com.sp.taskmng.util.TaskMngUtil;
import com.sp.utility.database.DbPool;

public class DBPrpCustomerMessageMobile {
	private final Log logger = LogFactory.getLog(getClass());
	/**
	 * 像XXXXX手机表插入数据
	 * @param dbpool
	 * @param prpCustomerMessageMobileSchema
	 * @return
	 * @throws Exception
	 */
	public int insertPrpCustomerMessageMobile(DbPool dbpool,PrpCustomerMessageMobileSchema prpCustomerMessageMobileSchema) 
	{
		int count = 1;
		String sql = "insert into prpcustomermessage_mobile(messageType,mobile,year,id) " +
		             "values(?,?,to_char(sysdate,'yyyy'),?)";
		try{
			dbpool.prepareInnerStatement(sql);
			int j = 0;
			dbpool.setString(++j,prpCustomerMessageMobileSchema.getMessageType());
			dbpool.setString(++j,prpCustomerMessageMobileSchema.getMobile());
			dbpool.setString(++j,prpCustomerMessageMobileSchema.getId());
			dbpool.executePreparedUpdate();
			dbpool.closePreparedStatement();
			count = 0;  
		}catch(Exception e)
		{
			count = 1;  
			logger.error("XXXXX真实性错误信息：",e);
			logger.error(e+"向prpcustomermessage_moblie插入数据，出错id号："+prpCustomerMessageMobileSchema.getId());
		}
		return count;
	}
	/**
	 * 是否存在
	 * @param dbpool
	 * @param prpCustomerMessageMobileSchema
	 * @return
	 */
	public int getCount(DbPool dbpool, PrpCustomerMessageMobileSchema prpCustomerMessageMobileSchema)  
	{
		int count = 0;
		String sql = "select count(1) from prpcustomermessage_mobile where mobile=? and year=to_char(sysdate,'yyyy')";
		try{
			dbpool.prepareInnerStatement(sql);
			dbpool.setString(1,prpCustomerMessageMobileSchema.getMobile());
			ResultSet resultSet = dbpool.executePreparedQuery();
			while(resultSet.next())
			{
				count = Integer.parseInt(dbpool.getString(resultSet,"count(1)"));
			}
			resultSet.close();
			dbpool.closePreparedStatement();
		}catch(Exception e)
		{
			 logger.error("XXXXX真实性错误信息：",e);
		}
		return count;
		
	}
	/**
	 * 选取未读取过的回访数据
	 * @param dbpool
	 * @return
	 * @throws Exception
	 */
	public List getVisaData(DbPool dbpool) throws Exception
	{
		List list = new ArrayList();
		String sql = "select * from prpcustomermessage_mobile where exportflag='0' ";
		ResultSet resultSet = dbpool.query(sql);
		PrpCustomerMessageMobileSchema prpCustomerMessageMobileSchema = null;
		while(resultSet.next())
		{
			prpCustomerMessageMobileSchema = new PrpCustomerMessageMobileSchema();
			prpCustomerMessageMobileSchema.setId(dbpool.getString(resultSet,"id"));
			list.add(prpCustomerMessageMobileSchema);
		}
		return list;
	}
	/**
	 * 读取后回写状态
	 * @param dbpool
	 * @param id
	 * @param exportFlag
	 */
	public void updatePrpCustomerMessageMobileExportFlag(DbPool dbpool,String id,String exportFlag)
	{
		String sql = "update prpcustomermessage_mobile set exportflag =? where id=? ";
		try{
			dbpool.prepareInnerStatement(sql);
			dbpool.setString(1,exportFlag);
			dbpool.setString(2,id);
			dbpool.executePreparedUpdate();
			dbpool.closePreparedStatement();
		}catch(Exception e)
		{
			logger.error("XXXXX真实性错误信息：",e);
			logger.error(e+"回写prpcustomermessage_moblie的状态，出错id"+id);
			
			String message = "DBPrpCustomerMessageMobile->updatePrpCustomerMessageMobileExportFlag()->读取后回写状态->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("DBPrpCustomerMessageMobile.updatePrpCustomerMessageMobileExportFlag", message,e,
					TaskMngUtil.CUSTOMERREALCOMPLETE_JOBNAME);
			
		}
	}
}
