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
		
		logger.info("CustomerRealRunServlet--->XXXXX��ʵ�Զ�ʱ���񣡿�ʼ��~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		this.logger.info("����URL��" + req.getRequestURL() + "��,���������"
				+ req.getQueryString() + "��,XXXXX��ip��" + req.getRemoteAddr()
				+ "��,���󷽷���"
				+ req.getMethod() + "'��;�����IP��" + req.getServerName()
				+ "�������PORT��" + req.getServerPort() + "��,����Ʊ�ݡ�"
				+ req.getParameter("TICKET") + "��");
		
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
				logger.info("CustomerRealRunServlet--->�������ļ���ȡsysconst.CUSTOMERREAL_SWITCH ʧ�ܣ�Ĭ��ֵΪ'0' �ǹ�");
			}
			else
			{
				CUSTOMERREAL_SWITCH_STR = CUSTOMERREAL_SWITCH;
			}
			
			if("0".equals(CUSTOMERREAL_SWITCH_STR))
			{
				logger.info("CustomerRealRunServlet--->XXXXX��ʵ�Զ�ʱ���񣡿��� �ǹأ���ֹ������CUSTOMERREAL_SWITCH ="+CUSTOMERREAL_SWITCH_STR);
				return;
			}

			logger.info("CustomerRealRunServlet--->XXXXX��ʵ�Զ�ʱ���񣡿��� �ǿ�����ʼ������");
			
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
			logger.info("����Сʱ��calendarTime: "+calendarTime);
			logger.info("strBDate: "+strBDate);
			logger.info("strNDate: "+strNDate);
			Date date=new Date();
        	SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        	Date xbDate=dateFormat1.parse("2015-08-19");
			
        	BLPrpCustomerMessage blPrpCustomerMessage = new BLPrpCustomerMessage();
	    	BLSendMessage blSendMessage = new BLSendMessage();
	    	
	    	if("1".equals(delFlag)){
	    		 logger.info("CustomerRealRunServlet--->XXXXX��ʵ�Լ�أ�ɾ�����ݣ���ʼ��"+ dateFormat.format(new Date()) );
	    		blPrpCustomerMessage.delPrpCustomerMessage(calendarTime,strBDate,strNDate);
	    		 logger.info("CustomerRealRunServlet--->XXXXX��ʵ�Լ�أ�ɾ�����ݣ�������"+ dateFormat.format(new Date()) );
	    	}
	    	    logger.info("CustomerRealRunServlet--->XXXXX��ʵ�Լ�أ��Ӵ�ӡ��ȡ���ݲ�����PrpCustomerMessage����ʼ��"+ dateFormat.format(new Date()) );
        		
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
    	    	logger.info("CustomerRealRunServlet--->XXXXX��ʵ�Լ�أ��Ӵ�ӡ��ȡ���ݲ�����PrpCustomerMessage��������"+ dateFormat.format(new Date()) );

    	    	BLCif blcif  = new BLCif();



    	    	logger.info("CustomerRealRunServlet--->XXXXX��ʵ�Լ�أ���������ݣ���ʼ��"+ dateFormat.format(new Date()) );
    	    	
    	    	blPrpCustomerMessage.updateDXflag2(calendarTime,strBDate,strNDate);
    	    	
    	    	blPrpCustomerMessage.updateDXflagSpecific2(calendarTime,strBDate,strNDate);
    	    	
    	    	List cifList = blPrpCustomerMessage.getCIFList("1");
    	    	
    	    	List cifListAdd = blPrpCustomerMessage.getCIFListAdd2(calendarTime,strBDate,strNDate);
    	    	cifList.addAll(cifListAdd);
    	    	
    	    	List insertCifMobile = blcif.getDxVisitData(cifList);
    	    	
    	    	blcif.insetCifMobile(insertCifMobile);
    	    	logger.info("CustomerRealRunServlet--->XXXXX��ʵ�Լ�أ���������ݣ�������"+ dateFormat.format(new Date()) );
    	    	
    	    	String groupFlag[] = {"0","1"};
    	    	
    	    	String messageType[] = {"1"};
    	    	logger.info("CustomerRealRunServlet--->XXXXX��ʵ�Լ�أ��жϺ����ظ�����ʼ��"+ dateFormat.format(new Date()) );
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
   	    		logger.info("CustomerRealRunServlet--->XXXXX��ʵ�Լ�أ��жϺ����ظ���������"+ dateFormat.format(new Date()) );
    	    	
    	    	
    			if(CUSTOMERREAL_DUANXIN_SWITCH ==null || "".equals(CUSTOMERREAL_DUANXIN_SWITCH))
    			{
    				CUSTOMERREAL_DUANXIN_SWITCH_STR = "0";
    				logger.info("CustomerRealRunServlet--->�������ļ���ȡsysconst.CUSTOMERREAL_DUANXIN_SWITCH ʧ�ܣ�Ĭ��ֵΪ'0' �ǹ�");
    			}
    			else
    			{
    				CUSTOMERREAL_DUANXIN_SWITCH_STR = CUSTOMERREAL_DUANXIN_SWITCH;
    			}
    			logger.info("CustomerRealRunServlet--->XXXXX��ʵ�Լ�أ����ŷ��ͣ���ʼ��"+ dateFormat.format(new Date()) );
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
    			logger.info("CustomerRealRunServlet--->XXXXX��ʵ�Լ�أ����ŷ��ͣ�������"+ dateFormat.format(new Date()) );
			logger.info("CustomerRealRunServlet--->XXXXX��ʵ�Զ�ʱ���񣡽�����~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		}catch(Exception e){
			this.logger.error("CustomerRealRunServlet--->XXXXX��ʵ�Զ�ʱ����ʱ�����쳣:" + e.getMessage(), e);
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
