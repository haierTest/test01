package com.sp.customerreal.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sp.customerreal.blsvr.BLCif;
import com.sp.customerreal.blsvr.BLItmVisit;
import com.sp.customerreal.blsvr.BLPolicyClaim;
import com.sp.customerreal.blsvr.BLPrpCustomerMessage;
import com.sp.customerreal.blsvr.BLPrpCustomerMessageMobile;
import com.sp.customerreal.blsvr.BLSendMessage;
import com.sp.sysframework.reference.AppConfig;
import com.sp.taskmng.util.TaskMngUtil;

public class CustomerRealServlet extends HttpServlet{

	private java.util.Timer timer = null; 
	private final Log logger = LogFactory.getLog(getClass());
	
	public void init() 
	{
		




		
	}
    @SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	String postType = request.getParameter("flag");
    	String calendarTime = request.getParameter("calendarTime");
    	String strBDate = request.getParameter("strBDate");
    	String strNDate = request.getParameter("strNDate");
    	String messagetype = request.getParameter("messagetype");
		logger.info("postType: "+postType);
		logger.info("calendarTime: "+calendarTime);
		logger.info("strBDate: "+strBDate);
		logger.info("strNDate: "+strNDate);
		logger.info("messagetype: "+messagetype);
		
		BLPrpCustomerMessage blPrpCustomerMessage = new BLPrpCustomerMessage();
		BLPrpCustomerMessageMobile blPrpCustomerMessageMobile = new BLPrpCustomerMessageMobile();
       	BLSendMessage blSendMessage = new BLSendMessage();
		BLPolicyClaim blPolicyClaim = new BLPolicyClaim();
		if("0".equals(postType))
		{
			 try{
				logger.info("CustomerRealServlet->doPost()->��XXXXX��ʵ�� ->ȡ����������->��ʼ");
				String groupFlag[] = {"0","1"};
    	    	String messageType[] = new String[2];
    	    	if(messagetype == null){
    	    		messageType[0] = "1";
    	    	}else{
    	    		messageType[0] = messagetype;
    	    	}
            	if(messageType[0].equals("1")){
		    		
		    	    List prpCustomerMessageList = blPrpCustomerMessage.createPrpCustomerMessage(calendarTime,strBDate,strNDate);
		        	
		        	blPrpCustomerMessage.insertPrpCustomerMessage(prpCustomerMessageList);
            	}
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
	        	
	        	String CUSTOMERREAL_DUANXIN_SWITCH = AppConfig.get("sysconst.CUSTOMERREAL_DUANXIN_SWITCH");  
	        	String CUSTOMERREAL_DUANXIN_SWITCH_STR = "";

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
    				
    				blPrpCustomerMessage.getAndUpdateFailed(messageType[0]);
        	    	
        	    	List list = blSendMessage.findFlexSeCmInfo(messageType[0]);
        	    	
        	    	blSendMessage.insertFlexSeCm(list);
        	    	
        	    	
        	    	
        	    	
        	    	blPrpCustomerMessage.updatePrpCustomerSendedFlag(list);
        	    	
        	        
	        	    
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
    				logger.info("���ŷ��Ϳ��� �ǹأ�CUSTOMERREAL_DUANXIN_SWITCH ="+CUSTOMERREAL_DUANXIN_SWITCH_STR+"���������ƽ̨�����ţ�");
    			}
	        	logger.info("CustomerRealServlet->doPost()->��XXXXX��ʵ�� ->ȡ����������->����");
	              	
	        	
			 }catch(Exception e)
			 {
				logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			 }
		}
		if("1".equals(postType))
		{
			try{
				logger.info("CustomerRealServlet->doPost()->��XXXXX��ʵ��-> ������Ϣ ->��ʼ");
	        	
	        	int forCount1 = blPrpCustomerMessage.getCompleteInfoForCount();
	        	logger.info("ѭ��"+forCount1+"�Σ�������Ϣ��");
	        	for(int i=0;i<forCount1;i++)
	        	{
	        		logger.info("ѭ����"+i+"��");
	        		
	        		List list1 = blPrpCustomerMessage.completePrpCustomerMessageOtherInfo();
	        		
	        		blPrpCustomerMessage.updatePrpCustomerMessageOtherInfo(list1);
	        	}
	        	logger.info("CustomerRealServlet->doPost()->��XXXXX��ʵ��-> ������Ϣ ->����");
			 }catch(Exception e)
			 {
				 logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			 }
	        	
		}
		if("2".equals(postType))
		{
			try{
				logger.info("CustomerRealServlet->doPost()->��XXXXX��ʵ��-> ȡ����״̬ ->��ʼ");
	        	
	        	int forCount2 = blPrpCustomerMessage.getSendInfoForCount();
	        	logger.info("ѭ��"+forCount2+"�Σ�ȡ����״̬��");
	        	for(int i=0;i<forCount2;i++)
	        	{
	        		logger.info("ѭ����"+i+"��");
	        		
	        		List list2 = blPrpCustomerMessage.getSendInfo();
	        		
	        		list2 = blSendMessage.getMessageStatus(list2);
	        		
	        		blPrpCustomerMessage.updatePrpCustomerMessageFlag(list2);
	        	}
	        	logger.info("CustomerRealServlet->doPost()->��XXXXX��ʵ��-> ȡ����״̬ ->����");
			 }catch(Exception e)
			 {
				 logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			 }
		}
		if("4".equals(postType))
		{
			try{
				logger.info("CustomerRealServlet->doPost()->��XXXXX��ʵ��-> ɸѡ�ط� ->��ʼ");
				
   	    		blPrpCustomerMessage.updatePrpCustomerGroupChannel();
				
	        	List leverTwoComCodeList = blPrpCustomerMessage.getLevelTwoComCode();
            	
        	   	blPrpCustomerMessage.getVisitMap(leverTwoComCodeList);
            	
        	   	List visitList = blPrpCustomerMessageMobile.getVisaData();
        	   	
        	   	visitList = blPrpCustomerMessage.updatePrpCustomerVisitStatues(visitList);
        	   	
        	   	blPrpCustomerMessageMobile.updatePrpCustomerMessageMobileExportFlag(visitList);
        	   	
            	blPrpCustomerMessage.updatePrpCustomerNoVisitStatues();
	        	logger.info("CustomerRealServlet->doPost()->��XXXXX��ʵ��-> ɸѡ�ط� ->����");
			 }catch(Exception e)
			 {
				 logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			 }
		}
		if("5".equals(postType))
		{
			try{
				logger.info("CustomerRealServlet->doPost()->��95510����XX�������� ->��ʼ");
				
            	List signPolicyDataList = blPolicyClaim.getSignPolicyData();
            	
            	signPolicyDataList = blPolicyClaim.insertSignPolicyData(signPolicyDataList);
            	
            	blPrpCustomerMessage.updatePrpCustomerMessageExportFlag(signPolicyDataList,"policy");
				logger.info("CustomerRealServlet->doPost()->��95510����XX�������� ->����");
			 }catch(Exception e)
			 {
				 logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			 }
		}
		if("6".equals(postType))
		{
			try{
				logger.info("CustomerRealServlet->doPost()->��95510����XX�ŵ����� ->��ʼ");
				
            	List groupPolicyDataList = blPolicyClaim.getGroupPolicyData();
            	
            	groupPolicyDataList = blPolicyClaim.insertGroupPolicyData(groupPolicyDataList);
            	
            	blPrpCustomerMessage.updatePrpCustomerMessageExportFlag(groupPolicyDataList,"policy");
				logger.info("CustomerRealServlet->doPost()->��95510����XX�ŵ����� ->����");
			 }catch(Exception e)
			 {
				 logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			 }
		}
		if("7".equals(postType))
		{
			try{
				logger.info("CustomerRealServlet->doPost()->��95510����XXXXX��������  ->��ʼ");
				
            	List signClaimDataList = blPolicyClaim.getSignClaimData();
            	
            	signClaimDataList = blPolicyClaim.insertSignClaimData(signClaimDataList);
            	
            	blPrpCustomerMessage.updatePrpCustomerMessageExportFlag(signClaimDataList,"claim");
				logger.info("CustomerRealServlet->doPost()->��95510����XXXXX��������  ->����");
			 }catch(Exception e)
			 {
				 logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			 }
		}
		if("8".equals(postType))
		{
			try{
				logger.info("CustomerRealServlet->doPost()->��95510����XXXXX�ŵ�����  ->��ʼ");
				
            	List groupClaimDataList = blPolicyClaim.getGroupClaimData();
            	
            	groupClaimDataList = blPolicyClaim.insertGroupClaimData(groupClaimDataList);
            	
            	blPrpCustomerMessage.updatePrpCustomerMessageExportFlag(groupClaimDataList,"claim");
				logger.info("CustomerRealServlet->doPost()->��95510����XXXXX�ŵ�����  ->����");
			 }catch(Exception e)
			 {
				 logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			 }
		}
		if("9".equals(postType))
		{
				logger.info("CustomerRealServlet->doPost()->���¶���״̬��ʱ��Σ�  ->��ʼ");
				try{
					logger.info("CustomerRealServlet->doPost()->��XXXXX��ʵ��-> ȡ����״̬ ->��ʼ");
		        	
		        	int forCount2 = blPrpCustomerMessage.getDateSendInfoForCount(strBDate,strNDate);
		        	logger.info("ѭ��"+forCount2+"�Σ�ȡ����״̬��");
		        	for(int i=0;i<forCount2;i++)
		        	{
		        		logger.info("ѭ����"+i+"��");
		        		
		        		List list2 = blPrpCustomerMessage.getDateSendInfo(strBDate,strNDate);
		        		
		        		list2 = blSendMessage.getMessageStatus(list2);
		        		
		        		blPrpCustomerMessage.updatePrpCustomerMessageFlag(list2);
		        	}
		        	logger.info("CustomerRealServlet->doPost()->��XXXXX��ʵ��-> ȡ����״̬ ->����");
				 }catch(Exception e)
				 {
					 logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
				 }
				logger.info("CustomerRealServlet->doPost()->���¶���״̬��ʱ��Σ� ->����");
		}
		if("10".equals(postType)){
			try {
    			logger.info("�µײ��������ط�����");
				
				
    			Map channelMap = blPrpCustomerMessage.atLeastThree();
    			if(channelMap!=null && channelMap.size()>0)
    			{
    				logger.info("�µײ��������ط�����->��ʼ");
    				blPrpCustomerMessage.getChannelMap(channelMap);
    				
    				List backList = blPrpCustomerMessageMobile.getVisaData();
    				
    				backList = blPrpCustomerMessage.updatePrpCustomerVisitStatues(backList);
    				
    				blPrpCustomerMessageMobile.updatePrpCustomerMessageMobileExportFlag(backList);
    				logger.info("�µײ��������ط�����->����");
    			}
    		} catch (Exception e) {
				
    			logger.error("��������ȡ�ط����������������ϴ�����Ϣ��",e);
    			e.printStackTrace();
			}
		}
		if("11".equals(postType)){
			try {
    			logger.info("�µײ��������ط�����");
				
				
    			Map channelMap = blPrpCustomerMessage.atLeastThreeForServlet(strBDate,strNDate);
    			if(channelMap!=null && channelMap.size()>0)
    			{
    				logger.info("�µײ��������ط�����->��ʼ");
    				blPrpCustomerMessage.getChannelMapForServlet(channelMap,strBDate,strNDate);
    				
    				List backList = blPrpCustomerMessageMobile.getVisaData();
    				
    				backList = blPrpCustomerMessage.updatePrpCustomerVisitStatues(backList);
    				
    				blPrpCustomerMessageMobile.updatePrpCustomerMessageMobileExportFlag(backList);
    				logger.info("�µײ��������ط�����->����");
    			}
    		} catch (Exception e) {
				
    			logger.error("��������ȡ�ط����������������ϴ�����Ϣ��",e);
    			e.printStackTrace();
			}
		}
		if("12".equals(postType)){
			try {
				logger.info("�µײ��������ط�����->��ʼ");
				
		    	String messageType[] = {"1","2"};
		    	
		    	List leverTwoComCodeList = blPrpCustomerMessage.getLevelTwoComCode();
		    	for(int j=0;j<messageType.length;j++)
		    	{
		    		
		    		for(int i=0;i<leverTwoComCodeList.size();i++)
					{
		    			
						
		    			Map channelMap = blPrpCustomerMessage.atLeastThreeForServlet(strBDate,strNDate,messageType[j],(String)leverTwoComCodeList.get(i));
		    			if(channelMap!=null && channelMap.size()>0)
		    			{
		    				logger.info("�µ�������Դ��"+messageType[j]+" ������"+(String)leverTwoComCodeList.get(i)+"���������ط�����->��ʼ");
		    				blPrpCustomerMessage.getChannelMapForServlet(channelMap,strBDate,strNDate,messageType[j],(String)leverTwoComCodeList.get(i));
		    				
		    				List backList = blPrpCustomerMessageMobile.getVisaData();
		    				
		    				backList = blPrpCustomerMessage.updatePrpCustomerVisitStatues(backList);
		    				
		    				blPrpCustomerMessageMobile.updatePrpCustomerMessageMobileExportFlag(backList);
		    				logger.info("�µ�������Դ��"+messageType[j]+" ������"+(String)leverTwoComCodeList.get(i)+"���������ط�����->����");
		    			}
					}
		    	}
		    	logger.info("�µײ��������ط�����->����");
				} catch (Exception e) {
					
					logger.error("��������ȡ�ط����������������ϴ�����Ϣ��",e);
					e.printStackTrace();
				}
			
		}
		if("13".equals(postType)){
			CustomerRealComplete run = new CustomerRealComplete();
			run.run();
		}
		if("14".equals(postType)){
			ClaimIsSatisfied run = new ClaimIsSatisfied();
			run.run();
		}
		if("15".equals(postType)){
			logger.info("�ֶ�ִ��XX����طö�ʱ���񣡿�ʼ������������������������������������");
			
			
			String sql = "SELECT a.*," +
						"(SELECT t.ventureflag FROM prpcmain t WHERE a.policyno=t.policyno) ventureflag, " +
						"(SELECT t.mobile FROM prpduser t WHERE a.SURVEYORHANDLID=t.usercode) managertel," +
						"b.cif_custid,b.mobile1,b.mobile2,b.mobile3,b.mobile4,b.kxd1,b.kxd2,b.kxd3,b.kxd4,b.resource1,b.resource2,b.resource3,b.resource4,b.modifydate1,b.modifydate2,b.modifydate3,b.modifydate4  " +
						"FROM PRPCUSTOMERMESSAGE a, prpcitemcar c,PRPCUSTOMERMESSAGE_CIFMOBILE b " +
						"WHERE a.MESSAgetype='1' " +
						"AND a.CHANNELTYPE = 'N01' " +
						"AND a.groupflag ='0' " +
						"AND a.CREATEDDATE BETWEEN TO_DATE('"+strBDate+" 00:00:00','YYYY-MM-DD HH24:MI:SS') " +
						"AND TO_DATE('"+strNDate+" 23:59:59','YYYY-MM-DD HH24:MI:SS') " +
						"AND a.policyno = c.policyno " +
						"AND c.usenaturecode = '8A' " +
						"AND a.id = b.id(+) ";













			try {
				
				List dxDataDataList = blPolicyClaim.getNewPolicy(sql);
				
				blPolicyClaim.insertNewPolicyData(dxDataDataList);
			} catch (Exception e) {
				logger.error("��������ȡ�ط����������������ϴ�����Ϣ��",e);
			}
			logger.info("�ֶ�ִ��XX����طö�ʱ���񣡽���������������������������������������");
		}
		if("16".equals(postType)){
			CustomerRealRun run = new CustomerRealRun();
			run.run();
		}
		if("17".equals(postType)){
			try {
				BLCif blcif  = new BLCif();
		    	
		    	List cifList = blPrpCustomerMessage.getCIFList("1");
		    	
    	    	List cifListAdd = blPrpCustomerMessage.getCIFListAdd(strBDate, strNDate);
    	    	cifList.addAll(cifListAdd);
		    	
		    	List insertCifMobile = blcif.getDxVisitData(cifList);
		    	
		    	blcif.insetCifMobile(insertCifMobile);
			} catch (Exception e) {
				logger.error("������Ϣ��",e);
			}
		}
		if("18".equals(postType)){
			try {
				BLCif blcif  = new BLCif();
		    	
		    	List cifList = blPrpCustomerMessage.getCIFList("2");
		    	
		    	List insertCifMobile = blcif.getDxVisitData(cifList);
		    	
		    	blcif.insetCifMobile(insertCifMobile);
			} catch (Exception e) {
				logger.error("������Ϣ��",e);
			}
		}
		if("19".equals(postType)){
			try {
				List prpCustomerMessageList = blPrpCustomerMessage.getDXhezuo(strBDate,strNDate);
				
    	    	BLCif blcif  = new BLCif();
    	    	List tmmobile = blcif.getDXmobile(prpCustomerMessageList);
    	    	
    	    	blcif.insertDXmobile(tmmobile);
			} catch (Exception e) {
				logger.error("������Ϣ��",e);
			}
		}
		if("20".equals(postType)){
			String sql = request.getParameter("sql");
			try {
				blPrpCustomerMessage.updateSQL(sql);
			} catch (Exception e) {
				logger.error("������Ϣ��",e);
			}
		}
		if("21".equals(postType)){
			logger.info("�����طö�ʱ���񣡿�ʼ������������������������������������");
			try {
				String policyNo = request.getParameter("policyNo");
				BLItmVisit blItmVisit = new BLItmVisit();
				
				List itmVisitDataDataList = blItmVisit.getItmVisitData(strBDate, strNDate,policyNo);
				
				blItmVisit.insertVisitDataData(itmVisitDataDataList);
			} catch (Exception e) {
				logger.error("�����طô�����Ϣ��", e);
				 String message = "ItmVisit->run()->�����ط�->" + e.getMessage();
				 TaskMngUtil.insertMidDataLog("CustomerRealComplete.run", message,
						e, TaskMngUtil.DXVISIT_JOBNAME);
			}
			logger.info("�����طö�ʱ���񣡽���������������������������������������");
		}
		if("22".equals(postType)){
			ItmVisitRun run = new ItmVisitRun();
			run.run();
		}
		if("23".equals(postType)){
			CustomerRealDxhz run = new CustomerRealDxhz();
			run.run();
		}
		if("24".equals(postType)){
			logger.info("�ֹ�ִ�ж�ʱ���񣡴Ӵ�ӡ�ر�����ʼ������������������������������������");
			try {
				
				List prpCustomerMessageList = blPrpCustomerMessage.createPrpCustomerMessage(calendarTime,strBDate,strNDate);
				
	        	blPrpCustomerMessage.insertPrpCustomerMessage(prpCustomerMessageList);
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			logger.info("�ֹ�ִ�ж�ʱ���񣡴Ӵ�ӡ�ر�������������������������������������������");
		}
		if("25".equals(postType)){
			logger.info("�ֹ�ִ�ж�ʱ���񣡵���XX��������ȶ�ȡ����ƽ̨��������������������������������������ʼ");
			try {
				CustomerRealDxmyd run = new CustomerRealDxmyd();
				run.run();
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info("�ֹ�ִ�ж�ʱ���񣡵���XX��������ȶ�ȡ����ƽ̨����������������������������������������");
		}
		if("26".equals(postType)){
			logger.info("�ֶ�ִ��XX��������ȣ���ʼ������������������������������������");
			
			String sql = "SELECT COUNT(*) FROM prpcustomermessage  " +
						"WHERE " +
						"messagetype = '1' " +
						"AND messageflag = '1' " +
						"AND iscontent = '-1' " +
						"AND CHANNELTYPE in ('N071','N075','N105') " +
						"AND CREATEDDATE BETWEEN TO_DATE('"+strBDate+" 00:00:00','YYYY-MM-DD HH24:MI:SS') " +
						"AND TO_DATE('"+strNDate+" 23:59:59','YYYY-MM-DD HH24:MI:SS') " +
						"AND GROUPSENDSEQ IS NULL " +
						"AND (READMESSAGE IS NULL OR READMESSAGE < SYSDATE-1/4) ";
			
			String  sql2 =  "SELECT * FROM " +
			 "(SELECT to_char(t.createddate,'YYYY-MM-DD HH24:MI:SS') createddate1,t.* FROM PRPCUSTOMERMESSAGE T " +
			 "WHERE T.MESSAGETYPE = '1' AND T.MESSAGEFLAG = '1' AND T.CHANNELTYPE in ('N071','N075','N105') " +
			 "AND T.ISCONTENT = '-1' " +
			 "AND T.CREATEDDATE BETWEEN TO_DATE('"+strBDate+" 00:00:00','YYYY-MM-DD HH24:MI:SS') " +
			 "AND TO_DATE('"+strNDate+" 23:59:59','YYYY-MM-DD HH24:MI:SS') " +
			 "AND t.groupSendSeq is null " +
			 "and (T.READMESSAGE IS NULL OR T.READMESSAGE < SYSDATE - 1 / 4) " +
			 "ORDER BY CREATEDDATE ASC) " +
			 "WHERE ROWNUM < 3001";;
			try {
				
		    	int forCount = blPrpCustomerMessage.getDxmydForCount(sql);
		   		logger.info("ѭ��"+forCount+"�Σ�ȡXXXXX���Żظ�״̬��");
		    	for(int i=0;i<forCount;i++)
		    	{
		   			logger.info("ѭ����"+i+"��");
		    		
		    		List list = blPrpCustomerMessage.getDxmydInfoBySelf(sql2);
		    		
		    		list = blSendMessage.getDxmydContext(list);
		    		
		    		blPrpCustomerMessage.updatePrpCustomerClaimFlag(list);
		    	}
			} catch (Exception e) {
				logger.error("�ֶ�ִ��XX��������ȣ�",e);
			}
			logger.info("�ֶ�ִ��XX��������ȣ�����������������������������������������");
		}
		if("27".equals(postType)){
			CustomerRealRunClaim run = new CustomerRealRunClaim();
			run.run();
		}
		if("28".equals(postType)){
			String groupFlag[] = {"0","1"};
	    	String messageType[] = {"2"};
	    	for(int j=0;j<groupFlag.length;j++)
	    	{
    	    	
    	    	for(int m=0;m<messageType.length;m++){
       	    		try {
       	    		    
						Map repeatPhoneMap = blPrpCustomerMessage.getJudgeRepeatPhoneListNew(groupFlag[j],messageType[m]);
					} catch (Exception e) {
						e.printStackTrace();
					}
       	    		
    	    	}
	    	}
			
		}
		if("29".equals(postType)){
			String groupFlag[] = {"0","1"};
	    	String messageType[] = {"2"};
	    	for(int j=0;j<groupFlag.length;j++)
	    	{
    	    	
    	    	for(int m=0;m<messageType.length;m++){
       	    		try {
       	    		    
						Map repeatPhoneMap = blPrpCustomerMessage.getJudgeRepeatPhoneList(groupFlag[j],messageType[m]);
					} catch (Exception e) {
						e.printStackTrace();
					}
       	    		
    	    	}
	    	}
			
		}
		if ("30".equals(postType)) {
			try {
				
				blPrpCustomerMessage.updateDXHFMonth();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if("31".equals(postType))
		{
			try{
				logger.info("CustomerRealServlet->doPost()->��XXXXX��ʵ��-> ���Ͽ�������ȡ����״̬ ->��ʼ");
	        	
	        	int forCount3 = blPrpCustomerMessage.getSendInfoForCountSpecific();
	        	logger.info("ѭ��"+forCount3+"�Σ����Ͽ�������ȡ����״̬��");
	        	for(int i=0;i<forCount3;i++)
	        	{
	        		logger.info("ѭ����"+i+"��");
	        		
	        		List list3 = blPrpCustomerMessage.getSendInfoSpecific();
	        		
	        		list3 = blSendMessage.getMessageStatusSpecific(list3);
	        		
	        		blPrpCustomerMessage.updatePrpCustomerMessageFlag1(list3);
	        	}
	        	logger.info("CustomerRealServlet->doPost()->��XXXXX��ʵ��-> ���Ͽ�������ȡ����״̬ ->����");
			 }catch(Exception e)
			 {
				 logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			 }
		}
		
		if("32".equals(postType))
		{
				logger.info("CustomerRealServlet->doPost()->���Ͽ������Ѹ��¶���״̬��ʱ��Σ�  ->��ʼ");
				try{
					logger.info("CustomerRealServlet->doPost()->��XXXXX��ʵ��-> ȡ����״̬ ->��ʼ");
		        	
		        	int forCount3 = blPrpCustomerMessage.getDateSendInfoForCountSpecific(strBDate,strNDate);
		        	logger.info("ѭ��"+forCount3+"�Σ����Ͽ�������ȡ����״̬��");
		        	for(int i=0;i<forCount3;i++)
		        	{
		        		logger.info("ѭ����"+i+"��");
		        		
		        		List list3 = blPrpCustomerMessage.getDateSendInfoSpecific(strBDate,strNDate);
		        		
		        		list3 = blSendMessage.getMessageStatusSpecific(list3);
		        		
		        		blPrpCustomerMessage.updatePrpCustomerMessageFlag1(list3);
		        	}
		        	logger.info("CustomerRealServlet->doPost()->��XXXXX��ʵ��-> ȡ����״̬ ->����");
				 }catch(Exception e)
				 {
					 logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
				 }
				logger.info("CustomerRealServlet->doPost()->���Ͽ������Ѹ��¶���״̬��ʱ��Σ� ->����");
		}	
		
		if("33".equals(postType))
		{
				logger.info("CustomerRealServlet->doPost()->���Ͽ�������ͬXXXXX���������ݷ��ͱ�ʶ���->��ʼ");
				try{
					logger.info("CustomerRealServlet->doPost()->��XXXXX��ʵ��-> ͬXXXXX���������ݷ��ͱ�ʶ���->��ʼ");
					 
	        	    blPrpCustomerMessage.updatePrpCustomerBuySimultaneity("1");
		        	logger.info("CustomerRealServlet->doPost()->��XXXXX��ʵ��-> ͬXXXXX���������ݷ��ͱ�ʶ��� ->����");
				 }catch(Exception e)
				 {
					 logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
				 }
				logger.info("CustomerRealServlet->doPost()->���Ͽ�������ͬXXXXX���������ݷ��ͱ�ʶ��� ->����");
		}	
		
		if ("34".equals(postType)) {
			logger.info("CustomerRealServlet->doPost()->���Ͽ������Ѷ��Ų���  ->��ʼ");
			try {
				String messageType[] = {"1"};
				String CUSTOMERREAL_HENDUANXIN_SWITCH = AppConfig
						.get("sysconst.CUSTOMERREAL_HENDUANXIN_SWITCH"); 
				String CUSTOMERREAL_HENDUANXIN_SWITCH_STR = "";
				
				if (CUSTOMERREAL_HENDUANXIN_SWITCH == null
						|| "".equals(CUSTOMERREAL_HENDUANXIN_SWITCH)) {
					CUSTOMERREAL_HENDUANXIN_SWITCH_STR = "0";
					logger.info("CustomerRealRun->run()--->�������ļ���ȡsysconst.CUSTOMERREAL_HENDUANXIN_SWITCH ʧ�ܣ�Ĭ��ֵΪ'0' �ǹ�");
				} else {
					CUSTOMERREAL_HENDUANXIN_SWITCH_STR = CUSTOMERREAL_HENDUANXIN_SWITCH;
				}

				if ("1".equals(CUSTOMERREAL_HENDUANXIN_SWITCH_STR)) {
					logger.info("���Ͽ�����ŷ��Ϳ��� �ǿ���CUSTOMERREAL_HENDUANXIN_SWITCH ="
							+ CUSTOMERREAL_HENDUANXIN_SWITCH_STR + "�������ƽ̨�����ţ�");
				   
	        	    
	        	    
                    
	        	    List list1 = blSendMessage.findFlexSeCmInfoSpecificH(messageType[0],strBDate,strNDate);

	        	    if(list1!=null&&list1.size()>0){
	        	                                      
	        	    blSendMessage.insertFlexSeCmSpecific(list1);
	    	        blPrpCustomerMessage.updatePrpCustomerSendedFlagSpecific(list1);
	        	    }
	        	  
				} else {
					logger.info("���Ͽ�����ŷ��Ϳ��� �ǹأ�CUSTOMERREAL_HENDUANXIN_SWITCH ="
							+ CUSTOMERREAL_HENDUANXIN_SWITCH_STR + "���������ƽ̨�����ţ�");
				}
				logger.info("CustomerRealRun->run()->��XXXXX��ʵ�� ->���Ͽ������Ѷ��Ų���->����");
			} catch (Exception e) {
				logger.error("XXXXX��ʵ�Դ�����Ϣ��", e);
			}
		}
		
		if ("35".equals(postType)) {
			logger.info("CustomerRealServlet->doPost()->���Ͽ������״̬����  ->��ʼ");
			try{  blPrpCustomerMessage.updateDXflagSpecific(calendarTime);
				
			logger.info("CustomerRealRun->run()->��XXXXX��ʵ�� ->���Ͽ������״̬����->����");
			}catch (Exception e){
				logger.error("XXXXX��ʵ�Դ�����Ϣ��", e);
			}
		}
		
		if("36".equals(postType)){
			String groupFlag[] = {"0","1"};
	    	String messageType[] = {"1"};
	    	List groupPhoneList =null;
	    	List groupPhoneListSpecific=null;
			for(int j=0;j<groupFlag.length;j++)
	    	{
    	    
    	    	for(int m=0;m<messageType.length;m++){
    	    	
       	 
       	    		if("1".equals(groupFlag[j]))
       	    		{
	       	    		
       	    			try {
							groupPhoneList=	blPrpCustomerMessage.getGroupPhoneList(messageType[m]);
							blPrpCustomerMessage.updateGroupPhoneMessageStatues(groupPhoneList);
							
						} catch (Exception e) {
							
							e.printStackTrace();
						}
	       	    		
	       	    		try {
	       	    			groupPhoneListSpecific=blPrpCustomerMessage.getGroupPhoneListSpecific(messageType[m]);
							blPrpCustomerMessage.updateGroupPhoneMessageStatuesSpecific(groupPhoneListSpecific);
						} catch (Exception e) {
							
							e.printStackTrace();
						}
       	    		}
    	    	}
	    	}
	    	
	    		try {
					blPrpCustomerMessage.updatePrpCustomerNoRepeatMoblieStatues();
				} catch (Exception e) {
					
					e.printStackTrace();
				}
		}
		
		if("37".equals(postType)){
			String groupFlag[] = {"0","1"};
	    	String messageType[] = {"1"};
	    	for(int j=0;j<groupFlag.length;j++)
	    	{
    	    	
    	    	for(int m=0;m<messageType.length;m++){
       	    		try {
       	    		    
						Map repeatPhoneMap = blPrpCustomerMessage.getJudgeRepeatPhoneList(groupFlag[j],messageType[m]);
						
	       	    		blPrpCustomerMessage.updatePrpCustomerMoblieStatues(repeatPhoneMap);
					} catch (Exception e) {
						e.printStackTrace();
					}
       	    		
    	    	}
	    	}
			
		}
		
		if("38".equals(postType)){
			String groupFlag[] = {"0","1"};
	    	String messageType[] = {"1"};
	    	for(int j=0;j<groupFlag.length;j++)
	    	{
    	    	
    	    	for(int m=0;m<messageType.length;m++){
       	    		try {
       	    		    
						Map repeatPhoneMap = blPrpCustomerMessage.getJudgeRepeatPhoneListNew(groupFlag[j],messageType[m]);
						
	       	    		blPrpCustomerMessage.updatePrpCustomerMoblieStatues(repeatPhoneMap);
					} catch (Exception e) {
						e.printStackTrace();
					}
       	    		
    	    	}
	    	}
			
		}
		
		
		if ("39".equals(postType)) {
			String messageType[] = {"1"};
			logger.info("CustomerRealServlet->doPost()->���Ͽ������״̬����  ->��ʼ");
			try{ 
				blPrpCustomerMessage.getAndUpdateFailedSpecific(messageType[0]);
				
			logger.info("CustomerRealRun->run()->��XXXXX��ʵ�� ->���Ͽ������״̬����->����");
			}catch (Exception e){
				logger.error("XXXXX��ʵ�Դ�����Ϣ��", e);
			}
		}
		
		if("40".equals(postType)){
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			logger.info("����XXXXX��ʵ�Զ�ʱ���񣡿�ʼ������������������������������������");
			logger.info("XXXXX��ʵ�Լ�أ�����XXXXX��ʵ�Զ�ʱ���񣡿�ʼ��"+ dateFormat.format(new Date()) );
			try{
	    	logger.info("CustomerRealComplete->run()->��XXXXX��ʵ��->������Ϣ����ȡ���ŷ���״̬��ɸѡ�ظ����롢�Ƿ�طá�д��95510�ӿڱ� ->��ʼ");
	    	
	    	String messageType[] = new String[2];
	    	if(messagetype == null){
	    		messageType[0] = "1";
	    	}else{
	    		messageType[0] = messagetype;
	    	}
	    	logger.info("XXXXX��ʵ�Լ�أ�ȡ������ʼ��"+ dateFormat.format(new Date()) );
        	if(messageType[0].equals("1")){
	    		
	    	    List prpCustomerMessageList = blPrpCustomerMessage.createPrpCustomerMessageGX40(calendarTime,strBDate,strNDate);
	        	
	        	blPrpCustomerMessage.insertPrpCustomerMessage(prpCustomerMessageList);
        	}
        	logger.info("XXXXX��ʵ�Լ�أ���������ʼ��"+ dateFormat.format(new Date()) );
        	
        	int forCount1 = blPrpCustomerMessage.getCompleteInfoForCountGX();
    			logger.info("ѭ��"+forCount1+"�Σ�������Ϣ��");
        	for(int i=0;i<forCount1;i++)
        	{
    				logger.info("ѭ����"+i+"��");
        		
        		List list1 = blPrpCustomerMessage.completePrpCustomerMessageOtherInfoGX();
        		
        		blPrpCustomerMessage.updatePrpCustomerMessageOtherInfo(list1);
        	}
        	logger.info("XXXXX��ʵ�Լ�أ����£���ʼ��"+ dateFormat.format(new Date()) );
	    	
		   	blPrpCustomerMessage.updatePrpCustomerGXVisitStatues();
		   	
	    	blPrpCustomerMessage.updatePrpCustomerNoVisitStatues();

	    	    	
	    	    	blPrpCustomerMessage.updateDXHFMonth();
	    	    	
	    	    	logger.info("�µײ��������ط����ݣ�->����");
	    			
		   	
	    	blPrpCustomerMessage.updateDXhuifang();
	    	logger.info("XXXXX��ʵ�Լ�أ���������ʼ��"+ dateFormat.format(new Date()) );
	    	
	    	List signPolicyDataListGX = blPolicyClaim.getSignPolicyGXData();
            
	    	signPolicyDataListGX = blPolicyClaim.insertSignPolicyData(signPolicyDataListGX);
	    	logger.info("XXXXX��ʵ�Լ�أ�����������"+ signPolicyDataListGX.size() );
	    	logger.info("XXXXX��ʵ�Լ�أ���д����ʼ��"+ dateFormat.format(new Date()) );
	    	
	    	blPrpCustomerMessage.updatePrpCustomerMessageExportFlag(signPolicyDataListGX,"policy");
	    	
	    	logger.info("CustomerRealComplete->run()->��XXXXX��ʵ��->������Ϣ����ȡ���ŷ���״̬��ɸѡ�ظ����롢�Ƿ�طá�д��95510�ӿڱ� ->����");
	    	logger.info("XXXXX��ʵ�Լ�أ�������"+ dateFormat.format(new Date()) );
			}catch (Exception e) {
				logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
				
				String message = "CustomerRealComplete->run()->XXXXX��ʵ�Բ���->"+e.getMessage();
				TaskMngUtil.insertMidDataLog("CustomerRealComplete.run", message,e,
						TaskMngUtil.CUSTOMERREALCOMPLETE_JOBNAME);
				
			}
			
			logger.info("XXXXX��ʵ�Զ�ʱ���񣡽���������������������������������������");
		}
		
		
		if("41".equals(postType)){
			logger.info("����XXXXX��ʵ�Զ�ʱ���񣡿�ʼ������������������������������������");
			try{
	    	logger.info("CustomerRealComplete->run()->��XXXXX��ʵ��->������Ϣ����ȡ���ŷ���״̬��ɸѡ�ظ����롢�Ƿ�طá�д��95510�ӿڱ� ->��ʼ");
	    	
	    	String messageType[] = new String[2];
	    	if(messagetype == null){
	    		messageType[0] = "1";
	    	}else{
	    		messageType[0] = messagetype;
	    	}
        	if(messageType[0].equals("1")){
	    		
	    	    List prpCustomerMessageList = blPrpCustomerMessage.createPrpCustomerMessageGX(calendarTime,strBDate,strNDate);
	        	
	        	blPrpCustomerMessage.insertPrpCustomerMessage(prpCustomerMessageList);
        	}
        	
        	
        	int forCount1 = blPrpCustomerMessage.getCompleteInfoForCountGX();
    			logger.info("ѭ��"+forCount1+"�Σ�������Ϣ��");
        	for(int i=0;i<forCount1;i++)
        	{
    				logger.info("ѭ����"+i+"��");
        		
        		List list1 = blPrpCustomerMessage.completePrpCustomerMessageOtherInfoGX();
        		
        		blPrpCustomerMessage.updatePrpCustomerMessageOtherInfo(list1);
        	}
        	
	    	
		   	blPrpCustomerMessage.updatePrpCustomerGXVisitStatues();
		   	
	    	blPrpCustomerMessage.updatePrpCustomerNoVisitStatues();

	    	    	
	    	    	blPrpCustomerMessage.updateDXHFMonth();
	    	    	
	    	    	logger.info("�µײ��������ط����ݣ�->����");
	    			
		   	
	    	blPrpCustomerMessage.updateDXhuifang();
	    	
	    	
	    	List signPolicyDataListGX = blPolicyClaim.getSignPolicyGXNData(calendarTime,strBDate,strNDate);
	    	
	    	signPolicyDataListGX = blPolicyClaim.insertSignPolicyData(signPolicyDataListGX);
	    	
	    	blPrpCustomerMessage.updatePrpCustomerMessageExportFlag(signPolicyDataListGX,"policy");
	    	
	    	logger.info("CustomerRealComplete->run()->��XXXXX��ʵ��->������Ϣ����ȡ���ŷ���״̬��ɸѡ�ظ����롢�Ƿ�طá�д��95510�ӿڱ� ->����");
			}catch (Exception e) {
				logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
				
				String message = "CustomerRealComplete->run()->XXXXX��ʵ�Բ���->"+e.getMessage();
				TaskMngUtil.insertMidDataLog("CustomerRealComplete.run", message,e,
						TaskMngUtil.CUSTOMERREALCOMPLETE_JOBNAME);
				
			}
			
			logger.info("XXXXX��ʵ�Զ�ʱ���񣡽���������������������������������������");
		}
		
		
		if("42".equals(postType)){
			logger.info("����XXXXX��ʵ�Զ�ʱ���񣡿�ʼ������������������������������������");
			try{
	    	logger.info("CustomerRealComplete->run()->��XXXXX��ʵ��->������Ϣ����ȡ���ŷ���״̬��ɸѡ�ظ����롢�Ƿ�طá�д��95510�ӿڱ� ->��ʼ");
	    	
	    	String messageType[] = new String[2];
	    	if(messagetype == null){
	    		messageType[0] = "1";
	    	}else{
	    		messageType[0] = messagetype;
	    	}
  	
        	
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
    	
        	
        	
        	int forCount3 = blPrpCustomerMessage.getSendInfoForCountSpecific();
       		logger.info("ѭ��"+forCount3+"�Σ����Ͽ�������ȡ����״̬��");
        	for(int i=0;i<forCount3;i++)
        	{
       			logger.info("ѭ����"+i+"��");
        		
        		List list3 = blPrpCustomerMessage.getSendInfoSpecific();
        		
        		list3 = blSendMessage.getMessageStatusSpecific(list3);
        		
        		blPrpCustomerMessage.updatePrpCustomerMessageFlag1(list3);
        	}
        	
        	
       		
       		blPrpCustomerMessage.updatePrpCustomerGroupChannel();
        	
        	List leverTwoComCodeList = blPrpCustomerMessage.getLevelTwoComCode();
        	
    	   	blPrpCustomerMessage.getVisitMap(leverTwoComCodeList);
        	
    	   	List visitList = blPrpCustomerMessageMobile.getVisaData();
    	   	
    	   	visitList = blPrpCustomerMessage.updatePrpCustomerVisitStatues(visitList);
    	   	
    	   	blPrpCustomerMessageMobile.updatePrpCustomerMessageMobileExportFlag(visitList);
    	   	
    	   	blPrpCustomerMessage.updatePrpCustomerGXVisitStatues();
    	   	
        	blPrpCustomerMessage.updatePrpCustomerNoVisitStatues();
        	
        	
        	Calendar a=Calendar.getInstance();
        	a.set(Calendar.DATE, 1);    
        	a.roll(Calendar.DATE, -1);  
        	Date   newDate   =   a.getTime();
        	String   lastDate   = new SimpleDateFormat("yyyy-MM-dd").format(newDate); 
        	String   todayDate   = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        	
        	if(lastDate.equals(todayDate)){
        		try {
        			logger.info("�µײ��������ط����ݣ�"+todayDate +"->��ʼ");
        			
        			
        	    	String messageType1[] = {"1","2"};
        	    	for(int j=0;j<messageType1.length;j++)
        	    	{
        	    		
        	    		for(int i=0;i<leverTwoComCodeList.size();i++)
        				{
        	    			
        					
        	    			Map channelMap = blPrpCustomerMessage.atLeastThree(messageType1[j],(String)leverTwoComCodeList.get(i));
        	    			if(channelMap!=null && channelMap.size()>0)
        	    			{
        	    				logger.info("�µ�������Դ��"+messageType1[j]+" ������"+(String)leverTwoComCodeList.get(i)+"���������ط�����->��ʼ");
        	    				blPrpCustomerMessage.getChannelMap(channelMap,messageType1[j],(String)leverTwoComCodeList.get(i));
        	    				
        	    				List backList = blPrpCustomerMessageMobile.getVisaData();
        	    				
        	    				backList = blPrpCustomerMessage.updatePrpCustomerVisitStatues(backList);
        	    				
        	    				blPrpCustomerMessageMobile.updatePrpCustomerMessageMobileExportFlag(backList);
        	    				logger.info("�µ�������Դ��"+messageType1[j]+" ������"+(String)leverTwoComCodeList.get(i)+"���������ط�����->����");
        	    			}
        				}
        	    	}
        	    	
        	    	blPrpCustomerMessage.updateDXHFMonth();
        	    	
        	    	logger.info("�µײ��������ط����ݣ�"+todayDate +"->����");
        		} catch (Exception e) {
    				
        			logger.error("��������ȡ�ط����������������ϴ�����Ϣ��",e);
        			e.printStackTrace();
    			}
        	}
        	
    	   	
        	blPrpCustomerMessage.updateDXhuifang();
        	
        	blPolicyClaim = new BLPolicyClaim();
        	
        	List signPolicyDataList = blPolicyClaim.getSignPolicyData();
        	
        	List signPolicyGXDataList = blPolicyClaim.getSignPolicyGXData();
        	if(signPolicyGXDataList != null && signPolicyGXDataList.size() >0){
        		for(int i = 0; i < signPolicyGXDataList.size(); i++){
        			signPolicyDataList.add(signPolicyGXDataList.get(i));
        		}
        	}
        	
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
        	
	    	logger.info("CustomerRealComplete->run()->��XXXXX��ʵ��->������Ϣ����ȡ���ŷ���״̬��ɸѡ�ظ����롢�Ƿ�طá�д��95510�ӿڱ� ->����");
			}catch (Exception e) {
				logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
				
				String message = "CustomerRealComplete->run()->XXXXX��ʵ�Բ���->"+e.getMessage();
				TaskMngUtil.insertMidDataLog("CustomerRealComplete.run", message,e,
						TaskMngUtil.CUSTOMERREALCOMPLETE_JOBNAME);
				
			}
			
			logger.info("XXXXX��ʵ�Զ�ʱ���񣡽���������������������������������������");
		}
		
		
		if("43".equals(postType)){
			logger.info("����XXXXX��ʵ�Զ�ʱ���񣡿�ʼ������������������������������������");
			try{
	    	logger.info("CustomerRealComplete->run()->��XXXXX��ʵ��->������Ϣ����ȡ���ŷ���״̬��ɸѡ�ظ����롢�Ƿ�طá�д��95510�ӿڱ� ->��ʼ");
	    	
	    	String messageType[] = new String[2];
	    	if(messagetype == null){
	    		messageType[0] = "1";
	    	}else{
	    		messageType[0] = messagetype;
	    	}
	    	
	    	int forCount1 = blPrpCustomerMessage.getCompleteInfoForCountGX();
				logger.info("ѭ��"+forCount1+"�Σ�������Ϣ��");
	    	for(int i=0;i<forCount1;i++)
	    	{
					logger.info("ѭ����"+i+"��");
	    		
	    		List list1 = blPrpCustomerMessage.completePrpCustomerMessageOtherInfoGX();
	    		
	    		blPrpCustomerMessage.updatePrpCustomerMessageOtherInfo(list1);
	    	}

	    	
		   	blPrpCustomerMessage.updatePrpCustomerGXVisitStatues();
		   	
	    	blPrpCustomerMessage.updatePrpCustomerNoVisitStatues();

	    	    	
	    	    	blPrpCustomerMessage.updateDXHFMonth();
	    	    	
	    	    	logger.info("�µײ��������ط����ݣ�->����");
	    			
		   	
	    	blPrpCustomerMessage.updateDXhuifang();
	    	
	    	blPolicyClaim = new BLPolicyClaim();
	    	
	    	List signPolicyDataListGX = blPolicyClaim.getSignPolicyGXTData();
	    	
	    	signPolicyDataListGX = blPolicyClaim.insertSignPolicyData(signPolicyDataListGX);
	    	
	    	blPrpCustomerMessage.updatePrpCustomerMessageExportFlag(signPolicyDataListGX,"policy");
	    	logger.info("CustomerRealComplete->run()->��XXXXX��ʵ��->������Ϣ����ȡ���ŷ���״̬��ɸѡ�ظ����롢�Ƿ�طá�д��95510�ӿڱ� ->����");
			}catch (Exception e) {
				logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
				
				String message = "CustomerRealComplete->run()->XXXXX��ʵ�Բ���->"+e.getMessage();
				TaskMngUtil.insertMidDataLog("CustomerRealComplete.run", message,e,
						TaskMngUtil.CUSTOMERREALCOMPLETE_JOBNAME);
				
			}
			
			logger.info("XXXXX��ʵ�Զ�ʱ���񣡽���������������������������������������");
		}

  }
  public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
    public void destroy() {
  }
}