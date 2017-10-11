package com.sp.customerreal.servlet;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sp.customerreal.blsvr.BLPrpCustomerMessage;
import com.sp.customerreal.blsvr.BLSendMessage;
import com.sp.taskmng.util.TaskMngUtil;


/**
 * @ClassName: CustomerRealDxmyd
 * @Description: XX电销满意度
 * @author yinwch
 * @date 2013-5-20 上午09:25:39
 * 
 */
public class CustomerRealDxmyd {
	private final Log logger = LogFactory.getLog(getClass());

	public void run() {
		logger.info("XX电销满意度！开始………………………………………………");
		try{
    	logger.info("CustomerRealDxmyd->run()->XX电销满意度->获取XXXXX回复->开始");
    	
    	BLPrpCustomerMessage blPrpCustomerMessage = new BLPrpCustomerMessage();
    	BLSendMessage blSendMessage = new BLSendMessage();
    	String sql = "SELECT COUNT(*) FROM prpcustomermessage  " +
						"WHERE " +
						"messagetype = '1' " +
						"AND messageflag = '1' " +
						"AND iscontent = '-1' " +
						"AND CHANNELTYPE in ('N071','N075','N105') " +
						"AND createddate BETWEEN TO_DATE(TO_CHAR(SYSDATE - 2, 'YYYY-MM-DD'), 'YYYY-MM-DD') " +
						"AND TO_DATE(TO_CHAR(SYSDATE - 1, 'YYYY-MM-DD'), 'YYYY-MM-DD') " +
						"AND GROUPSENDSEQ IS NULL " +
						"AND (READMESSAGE IS NULL OR READMESSAGE < SYSDATE-1/4) ";
    	
    	int forCount = blPrpCustomerMessage.getDxmydForCount(sql);
   		logger.info("循环"+forCount+"次，取XXXXX短信回复状态！");
    	for(int i=0;i<forCount;i++)
    	{
   			logger.info("循环第"+i+"次");
    		
    		List list = blPrpCustomerMessage.getDxmydInfo();
    		
    		list = blSendMessage.getDxmydContext(list);
    		
    		blPrpCustomerMessage.updatePrpCustomerClaimFlag(list);
    	}
    	
    	logger.info("ClaimIsSatisfied->run()->XX电销满意度->获取XXXXX回复->结束");
		}catch (Exception e) {
			logger.error("XXXXX真实性错误信息：",e);
			String message = "CustomerRealDxmyd->run()->XX电销满意度->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("CustomerRealDxmyd.run", message,e,TaskMngUtil.CUSTOMERREALDXMYD_JOBNAME);
		}
		
		logger.info("XX电销满意度！结束………………………………………………");
	}
}
