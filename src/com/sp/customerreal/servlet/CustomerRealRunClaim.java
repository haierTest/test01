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
 * @Description: ��ʱ���򣺺���XXXXXXX���ܡ�����XX����XX�������ʱƥ���XX��
 * @author yinwch
 * @date Apr 15, 2011 12:55:59 PM
 * 
 */
public class CustomerRealRunClaim {
	private static final String CUSTOMERREAL_SWITCH = AppConfig.get("sysconst.CUSTOMERREAL_SWITCH");  
	private static final String CUSTOMERREAL_DUANXIN_SWITCH = AppConfig.get("sysconst.CUSTOMERREAL_DUANXIN_SWITCH");  
	private final Log logger = LogFactory.getLog(getClass());
	/**
	 * ��ʱ���򣺺���XXXXXXX���ܡ�����XX����XX�������ʱƥ���XX��
	 */
	public void run() 
	{
		logger.info("XXXXX��ʵ��XXXXX��ʱ���񣡿�ʼ������������������������������������");
		String CUSTOMERREAL_SWITCH_STR = "";
		String CUSTOMERREAL_DUANXIN_SWITCH_STR = "";
		
		if(CUSTOMERREAL_SWITCH ==null || "".equals(CUSTOMERREAL_SWITCH))
		{
			CUSTOMERREAL_SWITCH_STR = "0";
			logger.info("CustomerRealRunClaim->run()--->�������ļ���ȡsysconst.CUSTOMERREAL_SWITCH ʧ�ܣ�Ĭ��ֵΪ'0' �ǹ�");
		}
		else
		{
			CUSTOMERREAL_SWITCH_STR = CUSTOMERREAL_SWITCH;
		}
		
		if("0".equals(CUSTOMERREAL_SWITCH_STR))
		{
			logger.info("XXXXX��ʵ��XXXXX��ʱ���񣡿��� �ǹأ���ֹ������CUSTOMERREAL_SWITCH ="+CUSTOMERREAL_SWITCH_STR);
			return;
		}
		
		logger.info("XXXXX��ʵ��XXXXX��ʱ���񣡿��� �ǿ�����ʼ������");
	
        Calendar cal = Calendar.getInstance(); 
        
        	String calendarTime = Integer.toString(cal.get(Calendar.HOUR_OF_DAY));
        	
        	logger.info("����Сʱ��calendarTime="+calendarTime);
                try{
	                logger.info("CustomerRealRunClaim->run()->��XXXXX��ʵ��XXXXX ->ȡ����������->��ʼ");
	                	
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
	        				logger.info("CustomerRealRunClaim->run()--->�������ļ���ȡsysconst.CUSTOMERREAL_DUANXIN_SWITCH ʧ�ܣ�Ĭ��ֵΪ'0' �ǹ�");
	        			}
	        			else
	        			{
	        				CUSTOMERREAL_DUANXIN_SWITCH_STR = CUSTOMERREAL_DUANXIN_SWITCH;
	        			}
	        			
	        			if("1".equals(CUSTOMERREAL_DUANXIN_SWITCH_STR))
	        			{
	        				logger.info("���ŷ��Ϳ��� �ǿ���CUSTOMERREAL_DUANXIN_SWITCH ="+CUSTOMERREAL_DUANXIN_SWITCH_STR+"�������ƽ̨�����ţ�");
	        				
	        				
	        				blPrpCustomerMessage.getAndUpdateFailed(messageType[0]);
		        			
		        	    	List list = blSendMessage.findFlexSeCmInfo(messageType[0]);
		        	    	
		        	    	blSendMessage.insertFlexSeCm(list);
		        	    	
		        	    	
			        	    
			        	    
			        	    blPrpCustomerMessage.updatePrpCustomerSendedFlag(list);
	        			}
	        			else
	        			{
	        				logger.info("���ŷ��Ϳ��� �ǹأ�CUSTOMERREAL_DUANXIN_SWITCH ="+CUSTOMERREAL_DUANXIN_SWITCH_STR+"���������ƽ̨�����ţ�");
	        			}
	        	    	logger.info("CustomerRealRunClaim->run()->��XXXXX��ʵ��XXXXX ->ȡ����������->����");
                }catch(Exception e)
                {
        				logger.error("XXXXX��ʵ��XXXXX������Ϣ��",e);
        				
        				String message = "CustomerRealRunClaim->run()->XXXXX��ʵ��XXXXX����->"+e.getMessage();
        				TaskMngUtil.insertMidDataLog("CustomerRealRunClaim.run", message,e,
        						TaskMngUtil.CUSTOMERREALRUN_JOBNAME);
        				
                }
                logger.info("XXXXX��ʵ��XXXXX��ʱ���񣡽���������������������������������������");
           }
}
