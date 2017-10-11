package com.sp.customerreal.servlet;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sp.customerreal.blsvr.BLPolicyClaim;
import com.sp.customerreal.blsvr.BLPrpCustomerMessage;
import com.sp.customerreal.blsvr.BLPrpCustomerMessageMobile;
import com.sp.customerreal.blsvr.BLSendMessage;
import com.sp.taskmng.util.TaskMngUtil;


public class CustomerRealCompleteGX
{
	private final Log logger = LogFactory.getLog(getClass());

	/**
	 * 定时程序：将回访数据写入95510接口表
	 */
	@SuppressWarnings("unchecked")
	public void run() {
		logger.info("XXXXX真实性定时任务！开始………………………………………………");
		try{
    	logger.info("CustomerRealCompleteGX->run()->跑XXXXX真实性->补充信息、获取短信发送状态、筛选重复号码、是否回访、写入95510接口表 ->开始");
    	
    	BLPrpCustomerMessage blPrpCustomerMessage = new BLPrpCustomerMessage();
    	
    	
    	int forCount1 = blPrpCustomerMessage.getCompleteInfoForCountGX();
			logger.info("循环"+forCount1+"次，补充信息！");
    	for(int i=0;i<forCount1;i++)
    	{
				logger.info("循环第"+i+"次");
    		
    		List list1 = blPrpCustomerMessage.completePrpCustomerMessageOtherInfoGX();
    		
    		blPrpCustomerMessage.updatePrpCustomerMessageOtherInfo(list1);
    	}

    	
	   	blPrpCustomerMessage.updatePrpCustomerGXVisitStatues();
	   	
    	blPrpCustomerMessage.updatePrpCustomerNoVisitStatues();

    	    	
    	    	blPrpCustomerMessage.updateDXHFMonth();
    	    	
    	    	logger.info("月底补充渠道回访数据：->结束");
    			
	   	
    	blPrpCustomerMessage.updateDXhuifang();
    	
    	BLPolicyClaim blPolicyClaim = new BLPolicyClaim();
    	
    	List signPolicyDataListGX = blPolicyClaim.getSignPolicyGXTData();
    	
    	signPolicyDataListGX = blPolicyClaim.insertSignPolicyData(signPolicyDataListGX);
    	
    	blPrpCustomerMessage.updatePrpCustomerMessageExportFlag(signPolicyDataListGX,"policy");
    	
    	logger.info("CustomerRealCompleteGX->run()->跑XXXXX真实性->补充信息、获取短信发送状态、筛选重复号码、是否回访、写入95510接口表 ->结束");
		}catch (Exception e) {
			logger.error("XXXXX真实性错误信息：",e);
			
			String message = "CustomerRealCompleteGX->run()->XXXXX真实性补数->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("CustomerRealCompleteGX.run", message,e,
					TaskMngUtil.CUSTOMERREALCOMPLETE_JOBNAME);
			
		}
		
		logger.info("XXXXX真实性定时任务！结束………………………………………………");
	}
}
