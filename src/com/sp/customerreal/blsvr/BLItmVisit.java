package com.sp.customerreal.blsvr;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sp.customerreal.dbsvr.DBItmVisit;
import com.sp.customerreal.schema.ItmVisitSchema;
import com.sp.taskmng.util.TaskMngUtil;
import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;

public class BLItmVisit {

	private final Log logger = LogFactory.getLog(getClass());

	/**
	 * 获取需要回访的XX个单数据电销所有
	 * 
	 * @return
	 * @throws Exception
	 */
	public List getItmVisitData() {
		logger.info("BLItmVisit->getItmVisitData()->获取XX电销数据准备写入95510接口表->开始");
		DBItmVisit dbItmVisit = new DBItmVisit();
		DbPool dbpool = new DbPool();
		List list = null;
		try {
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			
			list = dbItmVisit.getItmVisitData(dbpool);
		} catch (Exception e) {
			logger.error("电销回访错误信息：", e);
			 String message = "BLItmVisit->getItmVisitData()->获取XX电销数据数据->"
					+ e.getMessage();
			TaskMngUtil.insertMidDataLog("BLItmVisit.getItmVisitData", message,
					e, TaskMngUtil.DXVISIT_JOBNAME);
		} finally {
			try {
				dbpool.close();
			} catch (SQLException e) {
				logger.error("电销回访错误信息：", e);
			}
		}
		logger.info("BLItmVisit->getItmVisitData()->获取XX电销数据准备写入95510接口表->结束");
		return list;
	}

	/**
	 * 按时间段获取需要回访的XX个单数据电销所有
	 * 
	 * @return
	 * @throws Exception
	 */
	public List getItmVisitData(String strBDate,String strNDate,String policyNo) {
		logger.info("按时间段获取XX电销数据准备写入95510接口表->开始");
		DBItmVisit dbItmVisit = new DBItmVisit();
		DbPool dbpool = new DbPool();
		List list = null;
		try {
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			
			list = dbItmVisit.getItmVisitData(dbpool,strBDate,strNDate,policyNo);
		} catch (Exception e) {
			logger.error("电销回访错误信息：", e);
			 String message = "BLItmVisit->getItmVisitData()->获取XX电销数据数据->"
					+ e.getMessage();
			TaskMngUtil.insertMidDataLog("BLItmVisit.getItmVisitData", message,
					e, TaskMngUtil.DXVISIT_JOBNAME);
		} finally {
			try {
				dbpool.close();
			} catch (SQLException e) {
				logger.error("电销回访错误信息：", e);
			}
		}
		logger.info("BLItmVisit->getItmVisitData()->按时间段获取XX电销数据准备写入95510接口表->结束");
		return list;
	}
	
	/**
	 * @Title: insertVisitDataData
	 * @Description: 插入95510电销回访接口表
	 * @param
	 * @param list
	 *            设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public void insertVisitDataData(List list) {
		logger.info("BLItmVisit->insertVisitDataData()->XX电销数据写入95510接口表->开始");
		if (list == null || list.size() < 1) {
			logger.info("BLItmVisit->getItmVisitData()->无符合条件的数据");
		} else {
			DBItmVisit dbItmVisit = new DBItmVisit();
			DbPool dbpool = new DbPool();
			try {
				dbpool.open(SysConfig.CONST_CALLDATASOURCE);
				
				ItmVisitSchema itmVisitSchema = null;
				for (int i = 0; i < list.size(); i++) {
					itmVisitSchema = (ItmVisitSchema) list.get(i);
					
					int count = 0;
					count = dbItmVisit.getCountPolicy(dbpool,
							itmVisitSchema.getPolicyNo());
					if (count == 0) {
						dbItmVisit.insertItmVisitData(dbpool, itmVisitSchema);
					}
				}
			} catch (Exception e) {
				logger.error("电销回访错误信息：", e);
				String message = "BLItmVisit->insertVisitDataData()->XX电销数据写入95510接口表："
						+ e.getMessage();
				TaskMngUtil.insertMidDataLog("BLItmVisit.insertVisitDataData",
						message, e, TaskMngUtil.DXVISIT_JOBNAME);
			} finally {
				try {
					if (dbpool != null) {
						dbpool.close();
					}
				} catch (SQLException e) {
					logger.error("电销回访错误信息：", e);
				}
			}
		}
		logger
				.info("BLPolicyClaim->insertSignPolicyData()->XX电销数据写入95510接口表->结束");
	}
}
