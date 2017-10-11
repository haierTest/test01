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

/**
 * @ClassName: CustomerRealComplete
 * @Description: 补充信息、获取短信发送状态、筛选重复号码、是否回访 22点以后开始执行
 * @author yinwch
 * @date Apr 15, 2011 12:57:19 PM
 * 
 */
public class CustomerRealComplete {
	private final Log logger = LogFactory.getLog(getClass());

	/**
	 * 定时程序：补充信息、获取短信发送状态、筛选重复号码、是否回访、写入95510接口表
	 */
	@SuppressWarnings("unchecked")
	public void run() {
		logger.info("XXXXX真实性定时任务！开始………………………………………………");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		try{
    	logger.info("CustomerRealComplete->run()->跑XXXXX真实性->补充信息、获取短信发送状态、筛选重复号码、是否回访、写入95510接口表 ->开始");
    	
    	BLPrpCustomerMessage blPrpCustomerMessage = new BLPrpCustomerMessage();
    	BLSendMessage blSendMessage = new BLSendMessage();
    	BLPrpCustomerMessageMobile blPrpCustomerMessageMobile = new BLPrpCustomerMessageMobile();
    	logger.info("XXXXX真实性监控：CustomerRealComplete补充数据，开始："+ dateFormat.format(new Date()) );
    	
    	int forCount1 = blPrpCustomerMessage.getCompleteInfoForCount();
			logger.info("循环"+forCount1+"次，补充信息！");
    	for(int i=0;i<forCount1;i++)
    	{
				logger.info("循环第"+i+"次");
    		
    		List list1 = blPrpCustomerMessage.completePrpCustomerMessageOtherInfo();
    		
    		blPrpCustomerMessage.updatePrpCustomerMessageOtherInfo(list1);
    	}
	
    	
    	int forCount2 = blPrpCustomerMessage.getSendInfoForCount();
   		logger.info("循环"+forCount2+"次，取短信状态！");
    	for(int i=0;i<forCount2;i++)
    	{
   			logger.info("循环第"+i+"次");
    		
    		List list2 = blPrpCustomerMessage.getSendInfo();
    		
    		list2 = blSendMessage.getMessageStatus(list2);
    		
    		blPrpCustomerMessage.updatePrpCustomerMessageFlag(list2);
    	}
    	logger.info("XXXXX真实性监控：CustomerRealComplete补充数据，结束："+ dateFormat.format(new Date()) );
    	
    	
    	int forCount3 = blPrpCustomerMessage.getSendInfoForCountSpecific();
   		logger.info("循环"+forCount3+"次，河南快赔提醒取短信状态！");
    	for(int i=0;i<forCount3;i++)
    	{
   			logger.info("循环第"+i+"次");
    		
    		List list3 = blPrpCustomerMessage.getSendInfoSpecific();
    		
    		list3 = blSendMessage.getMessageStatusSpecific(list3);
    		
    		blPrpCustomerMessage.updatePrpCustomerMessageFlag1(list3);
    	}
    	
    	
   		
   		blPrpCustomerMessage.updatePrpCustomerGroupChannel();
    	
    	List leverTwoComCodeList = blPrpCustomerMessage.getLevelTwoComCode();
    	
	   	blPrpCustomerMessage.getVisitMap(leverTwoComCodeList);
    	
	   	List visitList = blPrpCustomerMessageMobile.getVisaData();
	   	
	   	visitList = blPrpCustomerMessage.updatePrpCustomerVisitStatues(visitList);
	   	
	   	blPrpCustomerMessageMobile.updatePrpCustomerMessageMobileExportFlag(visitList);
	   	
	   	blPrpCustomerMessage.updatePrpCustomerGXVisitStatues();
	   	
    	blPrpCustomerMessage.updatePrpCustomerNoVisitStatues();
    	
    	
    	Calendar a=Calendar.getInstance();
    	a.set(Calendar.DATE, 1);    
    	a.roll(Calendar.DATE, -1);  
    	Date   newDate   =   a.getTime();
    	String   lastDate   = new SimpleDateFormat("yyyy-MM-dd").format(newDate); 
    	String   todayDate   = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    	
    	if(lastDate.equals(todayDate)){
    		try {
    			logger.info("月底补充渠道回访数据："+todayDate +"->开始");
    			
    			
    	    	String messageType[] = {"1","2"};
    	    	for(int j=0;j<messageType.length;j++)
    	    	{
    	    		
    	    		for(int i=0;i<leverTwoComCodeList.size();i++)
    				{
    	    			
    					
    	    			Map channelMap = blPrpCustomerMessage.atLeastThree(messageType[j],(String)leverTwoComCodeList.get(i));
    	    			if(channelMap!=null && channelMap.size()>0)
    	    			{
    	    				logger.info("月底任务来源："+messageType[j]+" 机构："+(String)leverTwoComCodeList.get(i)+"补充渠道回访数据->开始");
    	    				blPrpCustomerMessage.getChannelMap(channelMap,messageType[j],(String)leverTwoComCodeList.get(i));
    	    				
    	    				List backList = blPrpCustomerMessageMobile.getVisaData();
    	    				
    	    				backList = blPrpCustomerMessage.updatePrpCustomerVisitStatues(backList);
    	    				
    	    				blPrpCustomerMessageMobile.updatePrpCustomerMessageMobileExportFlag(backList);
    	    				logger.info("月底任务来源："+messageType[j]+" 机构："+(String)leverTwoComCodeList.get(i)+"补充渠道回访数据->结束");
    	    			}
    				}
    	    	}
    	    	
    	    	blPrpCustomerMessage.updateDXHFMonth();
    	    	
    	    	logger.info("月底补充渠道回访数据："+todayDate +"->结束");
    			/*
				
				
    			Map channelMap = blPrpCustomerMessage.atLeastThree();
    			if(channelMap!=null && channelMap.size()>0)
    			{
    				logger.info("月底补充渠道回访数据->开始");
    				blPrpCustomerMessage.getChannelMap(channelMap);
    				
    				List backList = blPrpCustomerMessageMobile.getVisaData();
    				
    				backList = blPrpCustomerMessage.updatePrpCustomerVisitStatues(backList);
    				
    				blPrpCustomerMessageMobile.updatePrpCustomerMessageMobileExportFlag(backList);
    				logger.info("月底补充渠道回访数据->结束");
    			}
    			*/
    		} catch (Exception e) {
				
    			logger.error("各渠道抽取回访数据满足三条以上错误信息：",e);
    			e.printStackTrace();
			}
    	}
    	
	   	
    	blPrpCustomerMessage.updateDXhuifang();
    	
    	BLPolicyClaim blPolicyClaim = new BLPolicyClaim();
    	
    	logger.info("XXXXX真实性监控：非广西取数，开始："+ dateFormat.format(new Date()) );
    	List signPolicyDataList = blPolicyClaim.getSignPolicyData();
    	logger.info("XXXXX真实性监控：非广西数据条数："+ signPolicyDataList.size() );
    	
    	logger.info("XXXXX真实性监控：广西取数，开始："+ dateFormat.format(new Date()) );
    	List signPolicyGXDataList = blPolicyClaim.getSignPolicyGXData();
    	logger.info("XXXXX真实性监控：广西数据条数："+ signPolicyGXDataList.size() );
    	if(signPolicyGXDataList != null && signPolicyGXDataList.size() >0){
    		for(int i = 0; i < signPolicyGXDataList.size(); i++){
    			signPolicyDataList.add(signPolicyGXDataList.get(i));
    		}
    	}
    	logger.info("XXXXX真实性监控：总数据条数："+ signPolicyDataList.size() );
    	logger.info("XXXXX真实性监控：插入数据，开始："+ dateFormat.format(new Date()) );
    	
    	signPolicyDataList = blPolicyClaim.insertSignPolicyData(signPolicyDataList);
    	logger.info("XXXXX真实性监控：回写状态，开始："+ dateFormat.format(new Date()) );
    	
    	blPrpCustomerMessage.updatePrpCustomerMessageExportFlag(signPolicyDataList,"policy");
    	logger.info("XXXXX真实性监控：回写结束，开始："+ dateFormat.format(new Date()) );
    	
    	List groupPolicyDataList = blPolicyClaim.getGroupPolicyData();
    	
    	groupPolicyDataList = blPolicyClaim.insertGroupPolicyData(groupPolicyDataList);
    	
    	blPrpCustomerMessage.updatePrpCustomerMessageExportFlag(groupPolicyDataList,"policy");
    	
    	
    	List signClaimDataList = blPolicyClaim.getSignClaimData();
    	
    	signClaimDataList = blPolicyClaim.insertSignClaimData(signClaimDataList);
    	
    	blPrpCustomerMessage.updatePrpCustomerMessageExportFlag(signClaimDataList,"claim");
    	
    	
    	List groupClaimDataList = blPolicyClaim.getGroupClaimData();
    	
    	groupClaimDataList = blPolicyClaim.insertGroupClaimData(groupClaimDataList);
    	
    	blPrpCustomerMessage.updatePrpCustomerMessageExportFlag(groupClaimDataList,"claim");
    	
    	
    	logger.info("CustomerRealComplete->run()->跑XXXXX真实性->补充信息、获取短信发送状态、筛选重复号码、是否回访、写入95510接口表 ->结束");
		}catch (Exception e) {
			logger.error("XXXXX真实性错误信息：",e);
			
			String message = "CustomerRealComplete->run()->XXXXX真实性补数->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("CustomerRealComplete.run", message,e,
					TaskMngUtil.CUSTOMERREALCOMPLETE_JOBNAME);
			
		}
		logger.info("XXXXX真实性监控：XXXXX真实性定时任务！结束："+ dateFormat.format(new Date()) );
		logger.info("XXXXX真实性定时任务！结束………………………………………………");
	}
}
