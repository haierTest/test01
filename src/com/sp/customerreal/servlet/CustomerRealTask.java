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
	 * ��ʱ���򣺺���XXXXXXX���ܡ�����XX����XX�������ʱƥ���XX��
	 */
	public void run() 
	{
		logger.info("XXXXX��ʵ�Զ�ʱ���񣡿�ʼ������������������������������������");
		String CUSTOMERREAL_MESSAGE_TASKHOUR_STR = "";
		String CUSTOMERREAL_OTHER_TASKHOUR_STR = "";
		String CUSTOMERREAL_SWITCH_STR = "";
		String CUSTOMERREAL_DUANXIN_SWITCH_STR = "";
		
		if(CUSTOMERREAL_SWITCH ==null || "".equals(CUSTOMERREAL_SWITCH))
		{
			CUSTOMERREAL_SWITCH_STR = "0";
			logger.info("CustomerRealTask->run()--->�������ļ���ȡsysconst.CUSTOMERREAL_SWITCH ʧ�ܣ�Ĭ��ֵΪ'0' �ǹ�");
		}
		else
		{
			CUSTOMERREAL_SWITCH_STR = CUSTOMERREAL_SWITCH;
		}
		
		if("0".equals(CUSTOMERREAL_SWITCH_STR))
		{
			logger.info("XXXXX��ʵ�Զ�ʱ���񣡿��� �ǹأ���ֹ������CUSTOMERREAL_SWITCH ="+CUSTOMERREAL_SWITCH_STR);
			return;
		}
		
		logger.info("XXXXX��ʵ�Զ�ʱ���񣡿��� �ǿ�����ʼ������");
		
		if(CUSTOMERREAL_MESSAGE_TASKHOUR ==null || "".equals(CUSTOMERREAL_MESSAGE_TASKHOUR))
		{
			CUSTOMERREAL_MESSAGE_TASKHOUR_STR = "9,12,18";
			logger.info("CustomerRealTask->run()--->�������ļ���ȡsysconst.CUSTOMERREAL_MESSAGE_TASKHOUR ʧ�ܣ�Ĭ��ֵΪ'9,12,18'");
		}
		else
		{
			CUSTOMERREAL_MESSAGE_TASKHOUR_STR = CUSTOMERREAL_MESSAGE_TASKHOUR;
		}
	
		
		if(CUSTOMERREAL_OTHER_TASKHOUR ==null || "".equals(CUSTOMERREAL_OTHER_TASKHOUR))
		{
			CUSTOMERREAL_OTHER_TASKHOUR_STR = "22";
			logger.info("CustomerRealTask->run()--->�������ļ���ȡsysconst.CUSTOMERREAL_OTHER_TASKHOUR ʧ�ܣ�Ĭ��ֵΪ'22'");
		}
		else
		{
			CUSTOMERREAL_OTHER_TASKHOUR_STR = CUSTOMERREAL_OTHER_TASKHOUR;
		}
		
        Calendar cal = Calendar.getInstance(); 
        logger.info("CUSTOMERREAL_MESSAGE_TASKHOUR��"+CUSTOMERREAL_MESSAGE_TASKHOUR_STR+"   CUSTOMERREAL_OTHER_TASKHOUR:"+CUSTOMERREAL_OTHER_TASKHOUR_STR );
        
        String[] hour = CUSTOMERREAL_MESSAGE_TASKHOUR_STR.split(",");
        if (!isRunning) 
        { 
        	isRunning = true;
        	String calendarTime = Integer.toString(cal.get(Calendar.HOUR_OF_DAY));
        	String strBDate = "";
        	String strNDate = "";
        	logger.info("����Сʱ��calendarTime="+calendarTime);
        	
            for(int i=0;i<hour.length;i++)
            {
        		hour[i] = hour[i].trim();
        		logger.info("hour[i]="+hour[i]);
	        	if (hour[i].equals(calendarTime)) 
	            { 
        			logger.info("ʱ��ƥ����ȷ,��ʼȡ����");
                try{
	                	logger.info("CustomerRealTask->run()->��XXXXX��ʵ�� ->ȡ����������->��ʼ");
	                	
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
	        				logger.info("CustomerRealTask->run()--->�������ļ���ȡsysconst.CUSTOMERREAL_DUANXIN_SWITCH ʧ�ܣ�Ĭ��ֵΪ'0' �ǹ�");
	        			}
	        			else
	        			{
	        				CUSTOMERREAL_DUANXIN_SWITCH_STR = CUSTOMERREAL_DUANXIN_SWITCH;
	        			}
	        			
	        			if("1".equals(CUSTOMERREAL_DUANXIN_SWITCH_STR))
	        			{
	        				logger.info("���ŷ��Ϳ��� �ǿ���CUSTOMERREAL_DUANXIN_SWITCH ="+CUSTOMERREAL_DUANXIN_SWITCH_STR+"�������ƽ̨�����ţ�");
	        	    	
	        	    	List list = blSendMessage.findFlexSeCmInfo(messageType[0]);
	        	    	
	        	    	blSendMessage.insertFlexSeCm(list);
	        	    	
	        	    	
		        	    	
		        	    	
		        	    	blPrpCustomerMessage.updatePrpCustomerSendedFlag(list);
	        			}
	        			else
	        			{
	        				logger.info("���ŷ��Ϳ��� �ǹأ�CUSTOMERREAL_DUANXIN_SWITCH ="+CUSTOMERREAL_DUANXIN_SWITCH_STR+"���������ƽ̨�����ţ�");
	        			}
	        	    	logger.info("CustomerRealTask->run()->��XXXXX��ʵ�� ->ȡ����������->����");
                }catch(Exception e)
                {
        				isRunning = false;
        				logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
                }
        		}
        		else
        		{
        			logger.info("����ƥ��ʱ���hour[i]��"+hour[i]+",������ȡ������!");
            } 
           }
           logger.info("�ж��Ƿ���22������");
            
           if (CUSTOMERREAL_OTHER_TASKHOUR_STR.equals(calendarTime))  
            { 
           		logger.info("����ʱ��㣺"+calendarTime+"���պ�ƥ������ʱ��㣺"+CUSTOMERREAL_OTHER_TASKHOUR_STR+",��ʼ��22������!");
                try{
                	logger.info("CustomerRealTask->run()->��XXXXX��ʵ��->������Ϣ����ȡ���ŷ���״̬��ɸѡ�ظ����롢�Ƿ�طá�д��95510�ӿڱ� ->��ʼ");
                	
                	BLPrpCustomerMessage blPrpCustomerMessage = new BLPrpCustomerMessage();
                	BLSendMessage blSendMessage = new BLSendMessage();
                	BLPrpCustomerMessageMobile blPrpCustomerMessageMobile = new BLPrpCustomerMessageMobile();
                	
                	
        	    	int forCount1 = blPrpCustomerMessage.getCompleteInfoForCount();
           			logger.info("ѭ��"+forCount1+"�Σ�������Ϣ��");
        	    	for(int i=0;i<forCount1;i++)
        	    	{
           				logger.info("ѭ����"+i+"��");
        	    		
        	    		List list1 = blPrpCustomerMessage.completePrpCustomerMessageOtherInfo();
        	    		
        	    		blPrpCustomerMessage.updatePrpCustomerMessageOtherInfo(list1);
        	    	}
            	
        	    	
        	    	int forCount2 = blPrpCustomerMessage.getSendInfoForCount();
       	    		logger.info("ѭ��"+forCount2+"�Σ�ȡ����״̬��");
        	    	for(int i=0;i<forCount2;i++)
        	    	{
       	    			logger.info("ѭ����"+i+"��");
        	    		
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
                	
                	
                	logger.info("CustomerRealTask->run()->��XXXXX��ʵ��->������Ϣ����ȡ���ŷ���״̬��ɸѡ�ظ����롢�Ƿ�طá�д��95510�ӿڱ� ->����");
        	    	
           		}catch(Exception e)
           		{
           			isRunning = false;
           			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
                }
            } 
           else
           {
             	logger.info("����ʱ��㣺"+calendarTime+"������ƥ������ʱ��㣺"+CUSTOMERREAL_OTHER_TASKHOUR+",����22������");
        }	
    	logger.info("XXXXX��ʵ�Զ�ʱ���񣡽���������������������������������������");
           isRunning = false;
	}	
	}
}
