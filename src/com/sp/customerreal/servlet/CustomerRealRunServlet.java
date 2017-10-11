package com.sp.customerreal.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sp.customerreal.blsvr.BLCif;
import com.sp.customerreal.blsvr.BLPrpCustomerMessage;
import com.sp.customerreal.blsvr.BLSendMessage;
import com.sp.sysframework.reference.AppConfig;


public class CustomerRealRunServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	protected final Log logger = LogFactory.getLog(this.getClass());
	
	private static final String CUSTOMERREAL_SWITCH = AppConfig.get("sysconst.CUSTOMERREAL_SWITCH");
	
	private static final String CUSTOMERREAL_DUANXIN_SWITCH = AppConfig.get("sysconst.CUSTOMERREAL_DUANXIN_SWITCH");
	
	private static final String CUSTOMERREAL_HENDUANXIN_SWITCH = AppConfig.get("sysconst.CUSTOMERREAL_HENDUANXIN_SWITCH");
	
	private static final String CUSTOMERREAL_XUBAODUANXIN_SWITCH = AppConfig.get("sysconst.CUSTOMERREAL_XUBAODUANXIN_SWITCH"); 
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		logger.info("CustomerRealRunServlet--->XXXXX真实性定时任务！开始！~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		this.logger.info("请求URL【" + req.getRequestURL() + "】,请求参数【"
				+ req.getQueryString() + "】,XXXXX端ip【" + req.getRemoteAddr()
				+ "】,请求方法【"
				+ req.getMethod() + "'】;服务端IP【" + req.getServerName()
				+ "】服务端PORT【" + req.getServerPort() + "】,请求票据【"
				+ req.getParameter("TICKET") + "】");
		
		String responseStr = "1";
    	
    	String calendarTime = req.getParameter("calendarTime");
    	
    	String strBDate = req.getParameter("strBDate");
    	
    	String strNDate = req.getParameter("strNDate");
    	
    	String delFlag = req.getParameter("delFlag");
		
		String CUSTOMERREAL_SWITCH_STR = "";
		
		String CUSTOMERREAL_DUANXIN_SWITCH_STR = "";
		
		String CUSTOMERREAL_HENDUANXIN_SWITCH_STR="";
		
		String CUSTOMERREAL_XUBAODUANXIN_SWITCH_STR="";
		try{
			
			if(CUSTOMERREAL_SWITCH ==null || "".equals(CUSTOMERREAL_SWITCH))
			{
				CUSTOMERREAL_SWITCH_STR = "0";
				logger.info("CustomerRealRunServlet--->从配置文件读取sysconst.CUSTOMERREAL_SWITCH 失败，默认值为'0' 是关");
			}
			else
			{
				CUSTOMERREAL_SWITCH_STR = CUSTOMERREAL_SWITCH;
			}
			
			if("0".equals(CUSTOMERREAL_SWITCH_STR))
			{
				logger.info("CustomerRealRunServlet--->XXXXX真实性定时任务！开关 是关，禁止跑数！CUSTOMERREAL_SWITCH ="+CUSTOMERREAL_SWITCH_STR);
				return;
			}

			logger.info("CustomerRealRunServlet--->XXXXX真实性定时任务！开关 是开，开始跑数！");
			
			Calendar cal = Calendar.getInstance(); 
			
			if(delFlag==null || "".equals(delFlag))
			{
				delFlag = "0";
			}
			if(strBDate==null || "".equals(strBDate))
			{
				strBDate = "";
			}
			if(strNDate==null || "".equals(strNDate))
			{
				strNDate = "";
			}
			if(calendarTime==null || "".equals(calendarTime))
			{
				calendarTime = Integer.toString(cal.get(Calendar.HOUR_OF_DAY));
			}
        	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			
        	logger.info("delFlag: "+delFlag);
			logger.info("现在小时数calendarTime: "+calendarTime);
			logger.info("strBDate: "+strBDate);
			logger.info("strNDate: "+strNDate);
			Date date=new Date();
        	SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        	Date xbDate=dateFormat1.parse("2015-08-19");
			
        	BLPrpCustomerMessage blPrpCustomerMessage = new BLPrpCustomerMessage();
	    	BLSendMessage blSendMessage = new BLSendMessage();
	    	
	    	if("1".equals(delFlag)){
	    		 logger.info("CustomerRealRunServlet--->XXXXX真实性监控：删除数据，开始："+ dateFormat.format(new Date()) );
	    		blPrpCustomerMessage.delPrpCustomerMessage(calendarTime,strBDate,strNDate);
	    		 logger.info("CustomerRealRunServlet--->XXXXX真实性监控：删除数据，结束："+ dateFormat.format(new Date()) );
	    	}
	    	    logger.info("CustomerRealRunServlet--->XXXXX真实性监控：从打印池取数据并插入PrpCustomerMessage表，开始："+ dateFormat.format(new Date()) );
        		
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
    	    	logger.info("CustomerRealRunServlet--->XXXXX真实性监控：从打印池取数据并插入PrpCustomerMessage表，结束："+ dateFormat.format(new Date()) );

    	    	BLCif blcif  = new BLCif();



    	    	logger.info("CustomerRealRunServlet--->XXXXX真实性监控：杂项补充数据，开始："+ dateFormat.format(new Date()) );
    	    	
    	    	blPrpCustomerMessage.updateDXflag2(calendarTime,strBDate,strNDate);
    	    	
    	    	blPrpCustomerMessage.updateDXflagSpecific2(calendarTime,strBDate,strNDate);
    	    	
    	    	List cifList = blPrpCustomerMessage.getCIFList("1");
    	    	
    	    	List cifListAdd = blPrpCustomerMessage.getCIFListAdd2(calendarTime,strBDate,strNDate);
    	    	cifList.addAll(cifListAdd);
    	    	
    	    	List insertCifMobile = blcif.getDxVisitData(cifList);
    	    	
    	    	blcif.insetCifMobile(insertCifMobile);
    	    	logger.info("CustomerRealRunServlet--->XXXXX真实性监控：杂项补充数据，结束："+ dateFormat.format(new Date()) );
    	    	
    	    	String groupFlag[] = {"0","1"};
    	    	
    	    	String messageType[] = {"1"};
    	    	logger.info("CustomerRealRunServlet--->XXXXX真实性监控：判断号码重复，开始："+ dateFormat.format(new Date()) );
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
   	    		logger.info("CustomerRealRunServlet--->XXXXX真实性监控：判断号码重复，结束："+ dateFormat.format(new Date()) );
    	    	
    	    	
    			if(CUSTOMERREAL_DUANXIN_SWITCH ==null || "".equals(CUSTOMERREAL_DUANXIN_SWITCH))
    			{
    				CUSTOMERREAL_DUANXIN_SWITCH_STR = "0";
    				logger.info("CustomerRealRunServlet--->从配置文件读取sysconst.CUSTOMERREAL_DUANXIN_SWITCH 失败，默认值为'0' 是关");
    			}
    			else
    			{
    				CUSTOMERREAL_DUANXIN_SWITCH_STR = CUSTOMERREAL_DUANXIN_SWITCH;
    			}
    			logger.info("CustomerRealRunServlet--->XXXXX真实性监控：短信发送，开始："+ dateFormat.format(new Date()) );
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
    			logger.info("CustomerRealRunServlet--->XXXXX真实性监控：短信发送，结束："+ dateFormat.format(new Date()) );
			logger.info("CustomerRealRunServlet--->XXXXX真实性定时任务！结束！~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		}catch(Exception e){
			this.logger.error("CustomerRealRunServlet--->XXXXX真实性定时任务时发生异常:" + e.getMessage(), e);
			e.printStackTrace();
			responseStr = e.getMessage();
		}finally {
			OutputStream outputStream = resp.getOutputStream();
			if (responseStr == null) {
				responseStr = "";
			}
			outputStream.write(responseStr.getBytes());
			outputStream.flush();
		}
	
	}
}
