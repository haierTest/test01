package com.sp.customerreal.servlet;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sp.customerreal.blsvr.BLPolicyClaim;
import com.sp.customerreal.blsvr.BLPrpCustomerMessage;
import com.sp.customerreal.blsvr.BLSendMessage;
import com.sp.taskmng.util.TaskMngUtil;


/**
 * @ClassName: ClaimIsSatisfied
 * @Description: XXXXX100%回访
 * @author yinwch
 * @date 2012-8-16 上午09:25:39
 * 
 */
public class ClaimIsSatisfied {
	private final Log logger = LogFactory.getLog(getClass());

	public void run() {
		logger.info("XXXXX100%回访定时任务！开始………………………………………………");
		try{
    	logger.info("ClaimIsSatisfied->run()->XXXXX100%回访->补充信息、获取短信发送状态、筛选重复号码、是否回访、写入95510接口表 ->开始");
    	
    	BLPrpCustomerMessage blPrpCustomerMessage = new BLPrpCustomerMessage();
    	BLSendMessage blSendMessage = new BLSendMessage();
    	
    	
    	int forCount = blPrpCustomerMessage.getClaimForCount();
   		logger.info("循环"+forCount+"次，取XXXXX短信回复状态！");
    	for(int i=0;i<forCount;i++)
    	{
   			logger.info("循环第"+i+"次");
    		
    		List list2 = blPrpCustomerMessage.getClaimInfo();
    		
    		list2 = blSendMessage.getClaimContext(list2);
    		
    		blPrpCustomerMessage.updatePrpCustomerClaimFlag(list2);
    	}
    	
    	BLPolicyClaim blPolicyClaim = new BLPolicyClaim();
    	
    	List signClaimList = blPolicyClaim.getSignClaim();
    	
    	signClaimList = blPolicyClaim.insertSignClaim(signClaimList);
    	
    	blPrpCustomerMessage.updateExportMesFlag(signClaimList,"claim");
    	
    	
    	List groupClaimDataList = blPolicyClaim.getGroupClaim();
    	
    	groupClaimDataList = blPolicyClaim.insertGroupClaim(groupClaimDataList);
    	
    	blPrpCustomerMessage.updateExportMesFlag(groupClaimDataList,"claim");
    	
    	
    	logger.info("ClaimIsSatisfied->run()->XXXXX100%回访->补充信息、获取短信发送状态、筛选重复号码、是否回访、写入95510接口表 ->结束");
		}catch (Exception e) {
			logger.error("XXXXX真实性错误信息：",e);
			
			String message = "ClaimIsSatisfied->run()->XXXXX100%回访->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("ClaimIsSatisfied.run", message,e,
					TaskMngUtil.CLAIMISSATISFIED_JOBNAME);
			
		}
		
		logger.info("XXXXX100%回访定时任务！结束………………………………………………");
	}
}
