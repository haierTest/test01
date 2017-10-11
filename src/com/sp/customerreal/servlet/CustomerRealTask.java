package com.sp.customerreal.servlet;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;
import javax.servlet.ServletException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sp.customerreal.blsvr.BLPolicyClaim;
import com.sp.customerreal.blsvr.BLPrpCustomerMessage;
import com.sp.customerreal.blsvr.BLPrpCustomerMessageMobile;
import com.sp.customerreal.blsvr.BLSendMessage;
import com.sp.sysframework.reference.AppConfig;

public class CustomerRealTask extends TimerTask{
	private static final String CUSTOMERREAL_MESSAGE_TASKHOUR = AppConfig.get("sysconst.CUSTOMERREAL_MESSAGE_TASKHOUR");
	private static final String CUSTOMERREAL_OTHER_TASKHOUR = AppConfig.get("sysconst.CUSTOMERREAL_OTHER_TASKHOUR");  
	private static final String CUSTOMERREAL_SWITCH = AppConfig.get("sysconst.CUSTOMERREAL_SWITCH");  
	private static final String CUSTOMERREAL_DUANXIN_SWITCH = AppConfig.get("sysconst.CUSTOMERREAL_DUANXIN_SWITCH");  
	private static boolean isRunning = false;
	private final Log logger = LogFactory.getLog(getClass());
	public void init() throws ServletException
	{ 
    }
	
	/**
	 * 定时程序：航意XXXXXXX汇总、生成XX单和XX、激活卡定时匹配大XX号
	 */
	public void run() 
	{
		logger.info("XXXXX真实性定时任务！开始………………………………………………");
		String CUSTOMERREAL_MESSAGE_TASKHOUR_STR = "";
		String CUSTOMERREAL_OTHER_TASKHOUR_STR = "";
		String CUSTOMERREAL_SWITCH_STR = "";
		String CUSTOMERREAL_DUANXIN_SWITCH_STR = "";
		
		if(CUSTOMERREAL_SWITCH ==null || "".equals(CUSTOMERREAL_SWITCH))
		{
			CUSTOMERREAL_SWITCH_STR = "0";
			logger.info("CustomerRealTask->run()--->从配置文件读取sysconst.CUSTOMERREAL_SWITCH 失败，默认值为'0' 是关");
		}
		else
		{
			CUSTOMERREAL_SWITCH_STR = CUSTOMERREAL_SWITCH;
		}
		
		if("0".equals(CUSTOMERREAL_SWITCH_STR))
		{
			logger.info("XXXXX真实性定时任务！开关 是关，禁止跑数！CUSTOMERREAL_SWITCH ="+CUSTOMERREAL_SWITCH_STR);
			return;
		}
		
		logger.info("XXXXX真实性定时任务！开关 是开，开始跑数！");
		
		if(CUSTOMERREAL_MESSAGE_TASKHOUR ==null || "".equals(CUSTOMERREAL_MESSAGE_TASKHOUR))
		{
			CUSTOMERREAL_MESSAGE_TASKHOUR_STR = "9,12,18";
			logger.info("CustomerRealTask->run()--->从配置文件读取sysconst.CUSTOMERREAL_MESSAGE_TASKHOUR 失败，默认值为'9,12,18'");
		}
		else
		{
			CUSTOMERREAL_MESSAGE_TASKHOUR_STR = CUSTOMERREAL_MESSAGE_TASKHOUR;
		}
	
		
		if(CUSTOMERREAL_OTHER_TASKHOUR ==null || "".equals(CUSTOMERREAL_OTHER_TASKHOUR))
		{
			CUSTOMERREAL_OTHER_TASKHOUR_STR = "22";
			logger.info("CustomerRealTask->run()--->从配置文件读取sysconst.CUSTOMERREAL_OTHER_TASKHOUR 失败，默认值为'22'");
		}
		else
		{
			CUSTOMERREAL_OTHER_TASKHOUR_STR = CUSTOMERREAL_OTHER_TASKHOUR;
		}
		
        Calendar cal = Calendar.getInstance(); 
        logger.info("CUSTOMERREAL_MESSAGE_TASKHOUR："+CUSTOMERREAL_MESSAGE_TASKHOUR_STR+"   CUSTOMERREAL_OTHER_TASKHOUR:"+CUSTOMERREAL_OTHER_TASKHOUR_STR );
        
        String[] hour = CUSTOMERREAL_MESSAGE_TASKHOUR_STR.split(",");
        if (!isRunning) 
        { 
        	isRunning = true;
        	String calendarTime = Integer.toString(cal.get(Calendar.HOUR_OF_DAY));
        	String strBDate = "";
        	String strNDate = "";
        	logger.info("现在小时数calendarTime="+calendarTime);
        	
            for(int i=0;i<hour.length;i++)
            {
        		hour[i] = hour[i].trim();
        		logger.info("hour[i]="+hour[i]);
	        	if (hour[i].equals(calendarTime)) 
	            { 
        			logger.info("时间匹配正确,开始取数！");
                try{
	                	logger.info("CustomerRealTask->run()->跑XXXXX真实性 ->取数，发短信->开始");
	                	
                	BLPrpCustomerMessage blPrpCustomerMessage = new BLPrpCustomerMessage();
        	    	BLSendMessage blSendMessage = new BLSendMessage();
	            		
        	    	    List prpCustomerMessageList = blPrpCustomerMessage.createPrpCustomerMessage(calendarTime,strBDate,strNDate);
	        	    	
	        	    	blPrpCustomerMessage.insertPrpCustomerMessage(prpCustomerMessageList);
	        	    	
	        	    	String groupFlag[] = {"0","1"};
	        	    	String messageType[] = {"1","2"};
	        	    	for(int j=0;j<groupFlag.length;j++)
	        	    	{
	        	    		
		        	    	for(int m=0;m<messageType.length;m++){
		        	    		
			       	    		Map repeatPhoneMap = blPrpCustomerMessage.getJudgeRepeatPhoneList(groupFlag[j],messageType[m]);
			       	    		
			       	    		blPrpCustomerMessage.updatePrpCustomerMoblieStatues(repeatPhoneMap);
		        	    	}
		       	    		
		       	    		if("1".equals(groupFlag[j]))
		       	    		{
			       	    		
			       	    		List groupPhoneList = blPrpCustomerMessage.getGroupPhoneList(messageType[0]);
			       	    		
			       	    		blPrpCustomerMessage.updateGroupPhoneMessageStatues(groupPhoneList);
		       	    		}
	        	    	}
	        	    	
	       	    		blPrpCustomerMessage.updatePrpCustomerNoRepeatMoblieStatues();

	        	    	
	        	    	
	        			if(CUSTOMERREAL_DUANXIN_SWITCH ==null || "".equals(CUSTOMERREAL_DUANXIN_SWITCH))
	        			{
	        				CUSTOMERREAL_DUANXIN_SWITCH_STR = "0";
	        				logger.info("CustomerRealTask->run()--->从配置文件读取sysconst.CUSTOMERREAL_DUANXIN_SWITCH 失败，默认值为'0' 是关");
	        			}
	        			else
	        			{
	        				CUSTOMERREAL_DUANXIN_SWITCH_STR = CUSTOMERREAL_DUANXIN_SWITCH;
	        			}
	        			
	        			if("1".equals(CUSTOMERREAL_DUANXIN_SWITCH_STR))
	        			{
	        				logger.info("短信发送开关 是开！CUSTOMERREAL_DUANXIN_SWITCH ="+CUSTOMERREAL_DUANXIN_SWITCH_STR+"，向短信平台发短信！");
	        	    	
	        	    	List list = blSendMessage.findFlexSeCmInfo(messageType[0]);
	        	    	
	        	    	blSendMessage.insertFlexSeCm(list);
	        	    	
	        	    	
		        	    	
		        	    	
		        	    	blPrpCustomerMessage.updatePrpCustomerSendedFlag(list);
	        			}
	        			else
	        			{
	        				logger.info("短信发送开关 是关！CUSTOMERREAL_DUANXIN_SWITCH ="+CUSTOMERREAL_DUANXIN_SWITCH_STR+"，不向短信平台发短信！");
	        			}
	        	    	logger.info("CustomerRealTask->run()->跑XXXXX真实性 ->取数，发短信->结束");
                }catch(Exception e)
                {
        				isRunning = false;
        				logger.error("XXXXX真实性错误信息：",e);
                }
        		}
        		else
        		{
        			logger.info("不能匹配时间点hour[i]："+hour[i]+",不进行取数操作!");
            } 
           }
           logger.info("判断是否跑22点任务！");
            
           if (CUSTOMERREAL_OTHER_TASKHOUR_STR.equals(calendarTime))  
            { 
           		logger.info("现在时间点："+calendarTime+"，刚好匹配配置时间点："+CUSTOMERREAL_OTHER_TASKHOUR_STR+",开始跑22点任务!");
                try{
                	logger.info("CustomerRealTask->run()->跑XXXXX真实性->补充信息、获取短信发送状态、筛选重复号码、是否回访、写入95510接口表 ->开始");
                	
                	BLPrpCustomerMessage blPrpCustomerMessage = new BLPrpCustomerMessage();
                	BLSendMessage blSendMessage = new BLSendMessage();
                	BLPrpCustomerMessageMobile blPrpCustomerMessageMobile = new BLPrpCustomerMessageMobile();
                	
                	
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
           	
       	    		
       	    		
       	    		blPrpCustomerMessage.updatePrpCustomerGroupChannel();
                	
                	List leverTwoComCodeList = blPrpCustomerMessage.getLevelTwoComCode();
                	
            	   	blPrpCustomerMessage.getVisitMap(leverTwoComCodeList);
                	
            	   	List visitList = blPrpCustomerMessageMobile.getVisaData();
            	   	
            	   	visitList = blPrpCustomerMessage.updatePrpCustomerVisitStatues(visitList);
            	   	
            	   	blPrpCustomerMessageMobile.updatePrpCustomerMessageMobileExportFlag(visitList);
            	   	
                	blPrpCustomerMessage.updatePrpCustomerNoVisitStatues();
                	
                	
                	
                	BLPolicyClaim blPolicyClaim = new BLPolicyClaim();
                	
                	List signPolicyDataList = blPolicyClaim.getSignPolicyData();
                	
                	signPolicyDataList = blPolicyClaim.insertSignPolicyData(signPolicyDataList);
                	
                	blPrpCustomerMessage.updatePrpCustomerMessageExportFlag(signPolicyDataList,"policy");
                	
                	
                	List groupPolicyDataList = blPolicyClaim.getGroupPolicyData();
                	
                	groupPolicyDataList = blPolicyClaim.insertGroupPolicyData(groupPolicyDataList);
                	
                	blPrpCustomerMessage.updatePrpCustomerMessageExportFlag(groupPolicyDataList,"policy");
                	
                	
                	List signClaimDataList = blPolicyClaim.getSignClaimData();
                	
                	signClaimDataList = blPolicyClaim.insertSignClaimData(signClaimDataList);
                	
                	blPrpCustomerMessage.updatePrpCustomerMessageExportFlag(signClaimDataList,"claim");
                	
                	
                	List groupClaimDataList = blPolicyClaim.getGroupClaimData();
                	
                	groupClaimDataList = blPolicyClaim.insertGroupClaimData(groupClaimDataList);
                	
                	blPrpCustomerMessage.updatePrpCustomerMessageExportFlag(groupClaimDataList,"claim");
                	
                	
                	logger.info("CustomerRealTask->run()->跑XXXXX真实性->补充信息、获取短信发送状态、筛选重复号码、是否回访、写入95510接口表 ->结束");
        	    	
           		}catch(Exception e)
           		{
           			isRunning = false;
           			logger.error("XXXXX真实性错误信息：",e);
                }
            } 
           else
           {
             	logger.info("现在时间点："+calendarTime+"，不能匹配配置时间点："+CUSTOMERREAL_OTHER_TASKHOUR+",不跑22点任务！");
        }	
    	logger.info("XXXXX真实性定时任务！结束………………………………………………");
           isRunning = false;
	}	
	}
}
