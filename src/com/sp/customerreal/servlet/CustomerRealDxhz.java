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
 * @Description: ��Ϊ���������������ʱ����ѯ��������CustomerRealRun�е�����ȡ��������Ӱ��������̡�
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
		logger.info("�������������ʱ����");
	
        Calendar cal = Calendar.getInstance(); 
        
        	String calendarTime = Integer.toString(cal.get(Calendar.HOUR_OF_DAY));
        	String strBDate = "";
        	String strNDate = "";
        	logger.info("����Сʱ��calendarTime="+calendarTime);
        	
                try{
	                logger.info("CustomerRealDxhz->run()->�������������ʱ����->��ʼ");
	                	
                	BLPrpCustomerMessage blPrpCustomerMessage = new BLPrpCustomerMessage();
	            		
        	    	    List prpCustomerMessageList = blPrpCustomerMessage.createPrpCustomerMessage(calendarTime,strBDate,strNDate);
	        	    	
	        	    	BLCif blcif  = new BLCif();
	        	    	List tmmobile = blcif.getDXmobile(prpCustomerMessageList);
	        	    	
	        	    	blcif.insertDXmobile(tmmobile);
	        	    	logger.info("CustomerRealDxhz->run()->�������������ʱ����->����");
                }catch(Exception e)
                {
        				logger.error("�������������ʱ���������Ϣ��",e);
        				
        				String message = "CustomerRealDxhz->run()->�������������ʱ����->"+e.getMessage();
        				TaskMngUtil.insertMidDataLog("CustomerRealDxhz.run", message,e,
        						TaskMngUtil.CUSTOMERREALRUN_JOBNAME);
        				
                }
                logger.info("�������������ʱ���񣡽���������������������������������������");
           }
	
}
