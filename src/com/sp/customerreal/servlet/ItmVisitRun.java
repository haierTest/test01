package com.sp.customerreal.servlet;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sp.customerreal.blsvr.BLItmVisit;
import com.sp.taskmng.util.TaskMngUtil;

/**
 * @ClassName: ItmVisitRun
 * @Description: ��ʱ���򣺵����طö�ʱ����
 * @author yinwch
 * @date May 25, 2011 8:12:46 PM
 * 
 */
public class ItmVisitRun {
	private final Log logger = LogFactory.getLog(getClass());

	/**
	 * ��ʱ���򣺵����طö�ʱ����
	 */
	public void run() {
		logger.info("�����طö�ʱ���񣡿�ʼ������������������������������������");
		try {
			BLItmVisit blItmVisit = new BLItmVisit();
			
			List itmVisitDataDataList = blItmVisit.getItmVisitData();
			
			blItmVisit.insertVisitDataData(itmVisitDataDataList);
		} catch (Exception e) {
			logger.error("�����طô�����Ϣ��", e);
			 String message = "ItmVisit->run()->�����ط�->" + e.getMessage();
			 TaskMngUtil.insertMidDataLog("CustomerRealComplete.run", message,
					e, TaskMngUtil.DXVISIT_JOBNAME);
		}
		logger.info("�����طö�ʱ���񣡽���������������������������������������");
	}
}
