package com.sp.customerreal.servlet;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import utils.system;

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
public class CustomerRealRun {
	private static final String CUSTOMERREAL_SWITCH = AppConfig.get("sysconst.CUSTOMERREAL_SWITCH");  
	private static final String CUSTOMERREAL_DUANXIN_SWITCH = AppConfig.get("sysconst.CUSTOMERREAL_DUANXIN_SWITCH");  
	private static final String CUSTOMERREAL_HENDUANXIN_SWITCH = AppConfig.get("sysconst.CUSTOMERREAL_HENDUANXIN_SWITCH");  
	private static final String CUSTOMERREAL_XUBAODUANXIN_SWITCH = AppConfig.get("sysconst.CUSTOMERREAL_XUBAODUANXIN_SWITCH");  
	private final Log logger = LogFactory.getLog(getClass());
	/**
	 * ��ʱ���򣺺���XXXXXXX���ܡ�����XX����XX�������ʱƥ���XX��
	 */
	@SuppressWarnings("unchecked")
	public void run() 
	{
		logger.info("XXXXX��ʵ�Զ�ʱ���񣡿�ʼ������������������������������������");
		String CUSTOMERREAL_SWITCH_STR = "";
		String CUSTOMERREAL_DUANXIN_SWITCH_STR = "";
		String CUSTOMERREAL_HENDUANXIN_SWITCH_STR="";
		String CUSTOMERREAL_XUBAODUANXIN_SWITCH_STR="";
		
		if(CUSTOMERREAL_SWITCH ==null || "".equals(CUSTOMERREAL_SWITCH))
		{
			CUSTOMERREAL_SWITCH_STR = "0";
			logger.info("CustomerRealRun->run()--->�������ļ���ȡsysconst.CUSTOMERREAL_SWITCH ʧ�ܣ�Ĭ��ֵΪ'0' �ǹ�");
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
	
        Calendar cal = Calendar.getInstance(); 
        
        	String calendarTime = Integer.toString(cal.get(Calendar.HOUR_OF_DAY));
        	String strBDate = "";
        	String strNDate = "";
        	
        	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        	logger.info("����Сʱ��calendarTime="+calendarTime);
        	
                try{
                	Date date=new Date();
                	SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
                	Date xbDate=dateFormat1.parse("2015-08-19");
	                logger.info("CustomerRealRun->run()->��XXXXX��ʵ�� ->ȡ����������->��ʼ");
                	BLPrpCustomerMessage blPrpCustomerMessage = new BLPrpCustomerMessage();
        	    	BLSendMessage blSendMessage = new BLSendMessage();
        	    	    logger.info("XXXXX��ʵ�Լ�أ��Ӵ�ӡ��ȡ���ݲ�����PrpCustomerMessage����ʼ��"+ dateFormat.format(new Date()) );
	            		
        	    	    List prpCustomerMessageList = blPrpCustomerMessage.createPrpCustomerMessage(calendarTime,strBDate,strNDate);
	       	    	    
	        			if(CUSTOMERREAL_XUBAODUANXIN_SWITCH ==null || "".equals(CUSTOMERREAL_XUBAODUANXIN_SWITCH)){
	        				CUSTOMERREAL_XUBAODUANXIN_SWITCH_STR = "0";
	        				logger.info("CustomerRealRun->run()--->�������ļ���ȡsysconst.CUSTOMERREAL_XUBAODUANXIN_SWITCH ʧ�ܣ�Ĭ��ֵΪ'0' �ǹ�");
	        			}
	        			else{
	        				CUSTOMERREAL_XUBAODUANXIN_SWITCH_STR = CUSTOMERREAL_XUBAODUANXIN_SWITCH;
	        			}
	        			if("1".equals(CUSTOMERREAL_XUBAODUANXIN_SWITCH_STR)){
	        				if(date.before(xbDate)){
	        					blPrpCustomerMessage.updateXBMessage(prpCustomerMessageList);	
	        				}
	        				
	        			}else{
	        				logger.info("��XXXXX���ŷ��Ϳ��� �ǹأ�CUSTOMERREAL_XUBAODUANXIN_SWITCH_STR ="+CUSTOMERREAL_XUBAODUANXIN_SWITCH_STR+"�����滻��XXXXX�������ݣ�");
	        			}
        	    	    
	        	    	blPrpCustomerMessage.insertPrpCustomerMessage(prpCustomerMessageList);
	        	    	logger.info("XXXXX��ʵ�Լ�أ��Ӵ�ӡ��ȡ���ݲ�����PrpCustomerMessage��������"+ dateFormat.format(new Date()) );

	        	    	BLCif blcif  = new BLCif();



	        	    	logger.info("XXXXX��ʵ�Լ�أ���������ݣ���ʼ��"+ dateFormat.format(new Date()) );
	        	    	
	        	    	blPrpCustomerMessage.updateDXflag(calendarTime);
	        	    	
	        	    	blPrpCustomerMessage.updateDXflagSpecific(calendarTime);
	        	    	
	        	    	List cifList = blPrpCustomerMessage.getCIFList("1");
	        	    	
	        	    	List cifListAdd = blPrpCustomerMessage.getCIFListAdd(calendarTime);
	        	    	cifList.addAll(cifListAdd);
	        	    	
	        	    	List insertCifMobile = blcif.getDxVisitData(cifList);
	        	    	
	        	    	blcif.insetCifMobile(insertCifMobile);
	        	    	
	        	    	
	        	    	String groupFlag[] = {"0","1"};
	        	    	
	        	    	String messageType[] = {"1"};
	        	    	for(int j=0;j<groupFlag.length;j++)
	        	    	{
		        	    	
	        	    		
		        	    	for(int m=0;m<messageType.length;m++){
		        	    		
			       	    		Map repeatPhoneMap = blPrpCustomerMessage.getJudgeRepeatPhoneListNew(groupFlag[j],messageType[m]);
			       	    		
			       	    		blPrpCustomerMessage.updatePrpCustomerMoblieStatues(repeatPhoneMap);
			       	    		
			       	    		if("1".equals(groupFlag[j]))
			       	    		{
				       	    		
				       	    		List groupPhoneList = blPrpCustomerMessage.getGroupPhoneList(messageType[m]);
				       	    		
				       	    		blPrpCustomerMessage.updateGroupPhoneMessageStatues(groupPhoneList);
				       	    		
				       	    		List groupPhoneListSpecific=blPrpCustomerMessage.getGroupPhoneListSpecific(messageType[m]);
				       	    	    
				       	    		blPrpCustomerMessage.updateGroupPhoneMessageStatuesSpecific(groupPhoneListSpecific);
			       	    		}
		        	    	}
	        	    	}
	        	    	
	       	    		blPrpCustomerMessage.updatePrpCustomerNoRepeatMoblieStatues();
	       	    		logger.info("XXXXX��ʵ�Լ�أ���������ݽ�������ʼ��"+ dateFormat.format(new Date()) );
	        	    	
	        	    	
	        			if(CUSTOMERREAL_DUANXIN_SWITCH ==null || "".equals(CUSTOMERREAL_DUANXIN_SWITCH))
	        			{
	        				CUSTOMERREAL_DUANXIN_SWITCH_STR = "0";
	        				logger.info("CustomerRealRun->run()--->�������ļ���ȡsysconst.CUSTOMERREAL_DUANXIN_SWITCH ʧ�ܣ�Ĭ��ֵΪ'0' �ǹ�");
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
			        	    
			        	    if(CUSTOMERREAL_HENDUANXIN_SWITCH ==null || "".equals(CUSTOMERREAL_HENDUANXIN_SWITCH))
		        			{
			        	    	CUSTOMERREAL_HENDUANXIN_SWITCH_STR = "0";
		        				logger.info("CustomerRealRun->run()--->�������ļ���ȡsysconst.CUSTOMERREAL_HENDUANXIN_SWITCH ʧ�ܣ�Ĭ��ֵΪ'0' �ǹ�");
		        			}
		        			else
		        			{
		        				CUSTOMERREAL_HENDUANXIN_SWITCH_STR = CUSTOMERREAL_HENDUANXIN_SWITCH;
		        			}
			        	    if("1".equals(CUSTOMERREAL_HENDUANXIN_SWITCH_STR)){
			        	    	logger.info("���Ͽ�����ŷ��Ϳ��� �ǿ���CUSTOMERREAL_HENDUANXIN_SWITCH ="+CUSTOMERREAL_HENDUANXIN_SWITCH_STR+"�������ƽ̨�����ţ�");
			        	    
			        	    
			        	    blPrpCustomerMessage.updatePrpCustomerBuySimultaneity(messageType[0]);
			        	    
			        	    blPrpCustomerMessage.getAndUpdateFailedSpecific(messageType[0]);
                            
			        	    List list1 = blSendMessage.findFlexSeCmInfoSpecific(messageType[0]);

			        	    if(list1!=null&&list1.size()>0){
			        	                                      
			        	    blSendMessage.insertFlexSeCmSpecific(list1);
			    	        blPrpCustomerMessage.updatePrpCustomerSendedFlagSpecific(list1);
			        	    }
			        	  
			        	    }
			        	    else
		        			{
		        				logger.info("���Ͽ�����ŷ��Ϳ��� �ǹأ�CUSTOMERREAL_HENDUANXIN_SWITCH ="+CUSTOMERREAL_HENDUANXIN_SWITCH_STR+"���������ƽ̨�����ţ�");
		        			}
	        			}
	        			else
	        			{
	        				logger.info("���ŷ��Ϳ��� �ǹأ�CUSTOMERREAL_DUANXIN_SWITCH ="+CUSTOMERREAL_DUANXIN_SWITCH_STR+"���������ƽ̨�����ţ�");
	        			}
	        	    	logger.info("CustomerRealRun->run()->��XXXXX��ʵ�� ->ȡ����������->����");
                }catch(Exception e)
                {
        				logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
        				
        				String message = "CustomerRealRun->run()->XXXXX��ʵ������->"+e.getMessage();
        				TaskMngUtil.insertMidDataLog("CustomerRealRun.run", message,e,
        						TaskMngUtil.CUSTOMERREALRUN_JOBNAME);
        				
                }
                logger.info("XXXXX��ʵ�Զ�ʱ���񣡽���������������������������������������");
                logger.info("XXXXX��ʵ�Լ�أ�XXXXX��ʵ�Զ�ʱ���񣡽�����"+ dateFormat.format(new Date()) );
           }
	
}
