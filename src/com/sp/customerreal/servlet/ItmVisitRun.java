package com.sp.customerreal.servlet;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sp.customerreal.blsvr.BLItmVisit;
import com.sp.taskmng.util.TaskMngUtil;

/**
 * @ClassName: ItmVisitRun
 * @Description: 定时程序：电销回访定时任务
 * @author yinwch
 * @date May 25, 2011 8:12:46 PM
 * 
 */
public class ItmVisitRun {
	private final Log logger = LogFactory.getLog(getClass());

	/**
	 * 定时程序：电销回访定时任务
	 */
	public void run() {
		logger.info("电销回访定时任务！开始………………………………………………");
		try {
			BLItmVisit blItmVisit = new BLItmVisit();
			
			List itmVisitDataDataList = blItmVisit.getItmVisitData();
			
			blItmVisit.insertVisitDataData(itmVisitDataDataList);
		} catch (Exception e) {
			logger.error("电销回访错误信息：", e);
			 String message = "ItmVisit->run()->电销回访->" + e.getMessage();
			 TaskMngUtil.insertMidDataLog("CustomerRealComplete.run", message,
					e, TaskMngUtil.DXVISIT_JOBNAME);
		}
		logger.info("电销回访定时任务！结束………………………………………………");
	}
}
