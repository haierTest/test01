package com.sp.customerreal.servlet;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sp.customerreal.blsvr.BLCif;
import com.sp.customerreal.blsvr.BLPrpCustomerMessage;
import com.sp.customerreal.blsvr.BLSendMessage;
import com.sp.sysframework.reference.AppConfig;
import com.sp.taskmng.util.TaskMngUtil;

/**
 * @ClassName: CustomerRealRun
 * @Description: 定时程序：航意XXXXXXX汇总、生成XX单和XX、激活卡定时匹配大XX号
 * @author yinwch
 * @date Apr 15, 2011 12:55:59 PM
 * 
 */
public class CustomerRealRunClaim {
	private static final String CUSTOMERREAL_SWITCH = AppConfig.get("sysconst.CUSTOMERREAL_SWITCH");  
	private static final String CUSTOMERREAL_DUANXIN_SWITCH = AppConfig.get("sysconst.CUSTOMERREAL_DUANXIN_SWITCH");  
	private final Log logger = LogFactory.getLog(getClass());
	/**
	 * 定时程序：航意XXXXXXX汇总、生成XX单和XX、激活卡定时匹配大XX号
	 */
	public void run() 
	{
		logger.info("XXXXX真实性XXXXX定时任务！开始………………………………………………");
		String CUSTOMERREAL_SWITCH_STR = "";
		String CUSTOMERREAL_DUANXIN_SWITCH_STR = "";
		
		if(CUSTOMERREAL_SWITCH ==null || "".equals(CUSTOMERREAL_SWITCH))
		{
			CUSTOMERREAL_SWITCH_STR = "0";
			logger.info("CustomerRealRunClaim->run()--->从配置文件读取sysconst.CUSTOMERREAL_SWITCH 失败，默认值为'0' 是关");
		}
		else
		{
			CUSTOMERREAL_SWITCH_STR = CUSTOMERREAL_SWITCH;
		}
		
		if("0".equals(CUSTOMERREAL_SWITCH_STR))
		{
			logger.info("XXXXX真实性XXXXX定时任务！开关 是关，禁止跑数！CUSTOMERREAL_SWITCH ="+CUSTOMERREAL_SWITCH_STR);
			return;
		}
		
		logger.info("XXXXX真实性XXXXX定时任务！开关 是开，开始跑数！");
	
        Calendar cal = Calendar.getInstance(); 
        
        	String calendarTime = Integer.toString(cal.get(Calendar.HOUR_OF_DAY));
        	
        	logger.info("现在小时数calendarTime="+calendarTime);
                try{
	                logger.info("CustomerRealRunClaim->run()->跑XXXXX真实性XXXXX ->取数，发短信->开始");
	                	
                	BLPrpCustomerMessage blPrpCustomerMessage = new BLPrpCustomerMessage();
        	    	BLSendMessage blSendMessage = new BLSendMessage();
	            		
        	    	
        	    	BLCif blcif  = new BLCif();
        	    	
        	    	List cifList = blPrpCustomerMessage.getCIFList("2");
        	    	
        	    	List insertCifMobile = blcif.getDxVisitData(cifList);
        	    	
        	    	blcif.insetCifMobile(insertCifMobile);
        	    	
	        	    	String groupFlag[] = {"0","1"};
	        	    	String messageType[] = {"2"};
	        	    	for(int j=0;j<groupFlag.length;j++)
	        	    	{
		        	    	
		        	    	for(int m=0;m<messageType.length;m++){
		        	    		
			       	    		Map repeatPhoneMap = blPrpCustomerMessage.getJudgeRepeatPhoneListNew(groupFlag[j],messageType[m]);
			       	    		
			       	    		blPrpCustomerMessage.updatePrpCustomerMoblieStatues(repeatPhoneMap);
			       	    		if("1".equals(groupFlag[j]))
			       	    		{
				       	    		
				       	    		List groupPhoneList = blPrpCustomerMessage.getGroupPhoneList(messageType[m]);
				       	    		
				       	    		blPrpCustomerMessage.updateGroupPhoneMessageStatues(groupPhoneList);
			       	    		}
		        	    	}
	        	    	}
	        	    	
	       	    		blPrpCustomerMessage.updatePrpCustomerNoRepeatMoblieStatues();

	        	    	
	        	    	
	        			if(CUSTOMERREAL_DUANXIN_SWITCH ==null || "".equals(CUSTOMERREAL_DUANXIN_SWITCH))
	        			{
	        				CUSTOMERREAL_DUANXIN_SWITCH_STR = "0";
	        				logger.info("CustomerRealRunClaim->run()--->从配置文件读取sysconst.CUSTOMERREAL_DUANXIN_SWITCH 失败，默认值为'0' 是关");
	        			}
	        			else
	        			{
	        				CUSTOMERREAL_DUANXIN_SWITCH_STR = CUSTOMERREAL_DUANXIN_SWITCH;
	        			}
	        			
	        			if("1".equals(CUSTOMERREAL_DUANXIN_SWITCH_STR))
	        			{
	        				logger.info("短信发送开关 是开！CUSTOMERREAL_DUANXIN_SWITCH ="+CUSTOMERREAL_DUANXIN_SWITCH_STR+"，向短信平台发短信！");
	        				
	        				
	        				blPrpCustomerMessage.getAndUpdateFailed(messageType[0]);
		        			
		        	    	List list = blSendMessage.findFlexSeCmInfo(messageType[0]);
		        	    	
		        	    	blSendMessage.insertFlexSeCm(list);
		        	    	
		        	    	
			        	    
			        	    
			        	    blPrpCustomerMessage.updatePrpCustomerSendedFlag(list);
	        			}
	        			else
	        			{
	        				logger.info("短信发送开关 是关！CUSTOMERREAL_DUANXIN_SWITCH ="+CUSTOMERREAL_DUANXIN_SWITCH_STR+"，不向短信平台发短信！");
	        			}
	        	    	logger.info("CustomerRealRunClaim->run()->跑XXXXX真实性XXXXX ->取数，发短信->结束");
                }catch(Exception e)
                {
        				logger.error("XXXXX真实性XXXXX错误信息：",e);
        				
        				String message = "CustomerRealRunClaim->run()->XXXXX真实性XXXXX跑数->"+e.getMessage();
        				TaskMngUtil.insertMidDataLog("CustomerRealRunClaim.run", message,e,
        						TaskMngUtil.CUSTOMERREALRUN_JOBNAME);
        				
                }
                logger.info("XXXXX真实性XXXXX定时任务！结束………………………………………………");
           }
}
