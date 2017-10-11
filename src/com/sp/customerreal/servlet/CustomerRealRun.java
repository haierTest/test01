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
 * @Description: 定时程序：航意XXXXXXX汇总、生成XX单和XX、激活卡定时匹配大XX号
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
	 * 定时程序：航意XXXXXXX汇总、生成XX单和XX、激活卡定时匹配大XX号
	 */
	@SuppressWarnings("unchecked")
	public void run() 
	{
		logger.info("XXXXX真实性定时任务！开始………………………………………………");
		String CUSTOMERREAL_SWITCH_STR = "";
		String CUSTOMERREAL_DUANXIN_SWITCH_STR = "";
		String CUSTOMERREAL_HENDUANXIN_SWITCH_STR="";
		String CUSTOMERREAL_XUBAODUANXIN_SWITCH_STR="";
		
		if(CUSTOMERREAL_SWITCH ==null || "".equals(CUSTOMERREAL_SWITCH))
		{
			CUSTOMERREAL_SWITCH_STR = "0";
			logger.info("CustomerRealRun->run()--->从配置文件读取sysconst.CUSTOMERREAL_SWITCH 失败，默认值为'0' 是关");
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
	
        Calendar cal = Calendar.getInstance(); 
        
        	String calendarTime = Integer.toString(cal.get(Calendar.HOUR_OF_DAY));
        	String strBDate = "";
        	String strNDate = "";
        	
        	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        	logger.info("现在小时数calendarTime="+calendarTime);
        	
                try{
                	Date date=new Date();
                	SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
                	Date xbDate=dateFormat1.parse("2015-08-19");
	                logger.info("CustomerRealRun->run()->跑XXXXX真实性 ->取数，发短信->开始");
                	BLPrpCustomerMessage blPrpCustomerMessage = new BLPrpCustomerMessage();
        	    	BLSendMessage blSendMessage = new BLSendMessage();
        	    	    logger.info("XXXXX真实性监控：从打印池取数据并插入PrpCustomerMessage表，开始："+ dateFormat.format(new Date()) );
	            		
        	    	    List prpCustomerMessageList = blPrpCustomerMessage.createPrpCustomerMessage(calendarTime,strBDate,strNDate);
	       	    	    
	        			if(CUSTOMERREAL_XUBAODUANXIN_SWITCH ==null || "".equals(CUSTOMERREAL_XUBAODUANXIN_SWITCH)){
	        				CUSTOMERREAL_XUBAODUANXIN_SWITCH_STR = "0";
	        				logger.info("CustomerRealRun->run()--->从配置文件读取sysconst.CUSTOMERREAL_XUBAODUANXIN_SWITCH 失败，默认值为'0' 是关");
	        			}
	        			else{
	        				CUSTOMERREAL_XUBAODUANXIN_SWITCH_STR = CUSTOMERREAL_XUBAODUANXIN_SWITCH;
	        			}
	        			if("1".equals(CUSTOMERREAL_XUBAODUANXIN_SWITCH_STR)){
	        				if(date.before(xbDate)){
	        					blPrpCustomerMessage.updateXBMessage(prpCustomerMessageList);	
	        				}
	        				
	        			}else{
	        				logger.info("续XXXXX短信发送开关 是关！CUSTOMERREAL_XUBAODUANXIN_SWITCH_STR ="+CUSTOMERREAL_XUBAODUANXIN_SWITCH_STR+"，不替换续XXXXX短信内容！");
	        			}
        	    	    
	        	    	blPrpCustomerMessage.insertPrpCustomerMessage(prpCustomerMessageList);
	        	    	logger.info("XXXXX真实性监控：从打印池取数据并插入PrpCustomerMessage表，结束："+ dateFormat.format(new Date()) );

	        	    	BLCif blcif  = new BLCif();



	        	    	logger.info("XXXXX真实性监控：杂项补充数据，开始："+ dateFormat.format(new Date()) );
	        	    	
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
	       	    		logger.info("XXXXX真实性监控：杂项补充数据结束，开始："+ dateFormat.format(new Date()) );
	        	    	
	        	    	
	        			if(CUSTOMERREAL_DUANXIN_SWITCH ==null || "".equals(CUSTOMERREAL_DUANXIN_SWITCH))
	        			{
	        				CUSTOMERREAL_DUANXIN_SWITCH_STR = "0";
	        				logger.info("CustomerRealRun->run()--->从配置文件读取sysconst.CUSTOMERREAL_DUANXIN_SWITCH 失败，默认值为'0' 是关");
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
			        	    
			        	    if(CUSTOMERREAL_HENDUANXIN_SWITCH ==null || "".equals(CUSTOMERREAL_HENDUANXIN_SWITCH))
		        			{
			        	    	CUSTOMERREAL_HENDUANXIN_SWITCH_STR = "0";
		        				logger.info("CustomerRealRun->run()--->从配置文件读取sysconst.CUSTOMERREAL_HENDUANXIN_SWITCH 失败，默认值为'0' 是关");
		        			}
		        			else
		        			{
		        				CUSTOMERREAL_HENDUANXIN_SWITCH_STR = CUSTOMERREAL_HENDUANXIN_SWITCH;
		        			}
			        	    if("1".equals(CUSTOMERREAL_HENDUANXIN_SWITCH_STR)){
			        	    	logger.info("河南快赔短信发送开关 是开！CUSTOMERREAL_HENDUANXIN_SWITCH ="+CUSTOMERREAL_HENDUANXIN_SWITCH_STR+"，向短信平台发短信！");
			        	    
			        	    
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
		        				logger.info("河南快赔短信发送开关 是关！CUSTOMERREAL_HENDUANXIN_SWITCH ="+CUSTOMERREAL_HENDUANXIN_SWITCH_STR+"，不向短信平台发短信！");
		        			}
	        			}
	        			else
	        			{
	        				logger.info("短信发送开关 是关！CUSTOMERREAL_DUANXIN_SWITCH ="+CUSTOMERREAL_DUANXIN_SWITCH_STR+"，不向短信平台发短信！");
	        			}
	        	    	logger.info("CustomerRealRun->run()->跑XXXXX真实性 ->取数，发短信->结束");
                }catch(Exception e)
                {
        				logger.error("XXXXX真实性错误信息：",e);
        				
        				String message = "CustomerRealRun->run()->XXXXX真实性跑数->"+e.getMessage();
        				TaskMngUtil.insertMidDataLog("CustomerRealRun.run", message,e,
        						TaskMngUtil.CUSTOMERREALRUN_JOBNAME);
        				
                }
                logger.info("XXXXX真实性定时任务！结束………………………………………………");
                logger.info("XXXXX真实性监控：XXXXX真实性定时任务！结束："+ dateFormat.format(new Date()) );
           }
	
}
