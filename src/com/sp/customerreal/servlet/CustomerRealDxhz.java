package com.sp.customerreal.servlet;

import java.util.Calendar;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sp.customerreal.blsvr.BLCif;
import com.sp.customerreal.blsvr.BLPrpCustomerMessage;
import com.sp.taskmng.util.TaskMngUtil;

/**
 * @ClassName: CustomerRealRun
 * @Description: 因为补充电销合作数据时，查询过慢，从CustomerRealRun中单独提取出来。不影响后续流程。
 * @author yinwch
 * @date Apr 15, 2011 12:55:59 PM
 * 
 */
public class CustomerRealDxhz {
	
	private final Log logger = LogFactory.getLog(getClass());
	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void run() 
	{
		logger.info("补充电销合作定时任务！");
	
        Calendar cal = Calendar.getInstance(); 
        
        	String calendarTime = Integer.toString(cal.get(Calendar.HOUR_OF_DAY));
        	String strBDate = "";
        	String strNDate = "";
        	logger.info("现在小时数calendarTime="+calendarTime);
        	
                try{
	                logger.info("CustomerRealDxhz->run()->补充电销合作定时任务！->开始");
	                	
                	BLPrpCustomerMessage blPrpCustomerMessage = new BLPrpCustomerMessage();
	            		
        	    	    List prpCustomerMessageList = blPrpCustomerMessage.createPrpCustomerMessage(calendarTime,strBDate,strNDate);
	        	    	
	        	    	BLCif blcif  = new BLCif();
	        	    	List tmmobile = blcif.getDXmobile(prpCustomerMessageList);
	        	    	
	        	    	blcif.insertDXmobile(tmmobile);
	        	    	logger.info("CustomerRealDxhz->run()->补充电销合作定时任务！->结束");
                }catch(Exception e)
                {
        				logger.error("补充电销合作定时任务错误信息：",e);
        				
        				String message = "CustomerRealDxhz->run()->补充电销合作定时任务->"+e.getMessage();
        				TaskMngUtil.insertMidDataLog("CustomerRealDxhz.run", message,e,
        						TaskMngUtil.CUSTOMERREALRUN_JOBNAME);
        				
                }
                logger.info("补充电销合作定时任务！结束………………………………………………");
           }
	
}
