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
				logger.info("CustomerRealServlet->doPost()->跑XXXXX真实性 ->取数，发短信->开始");
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
	        				logger.info("CustomerRealTask->run()--->从配置文件读取sysconst.CUSTOMERREAL_DUANXIN_SWITCH 失败，默认值为'0' 是关");
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
    				logger.info("短信发送开关 是关！CUSTOMERREAL_DUANXIN_SWITCH ="+CUSTOMERREAL_DUANXIN_SWITCH_STR+"，不向短信平台发短信！");
    			}
	        	logger.info("CustomerRealServlet->doPost()->跑XXXXX真实性 ->取数，发短信->结束");
	              	
	        	
			 }catch(Exception e)
			 {
				logger.error("XXXXX真实性错误信息：",e);
			 }
		}
		if("1".equals(postType))
		{
			try{
				logger.info("CustomerRealServlet->doPost()->跑XXXXX真实性-> 补充信息 ->开始");
	        	
	        	int forCount1 = blPrpCustomerMessage.getCompleteInfoForCount();
	        	logger.info("循环"+forCount1+"次，补充信息！");
	        	for(int i=0;i<forCount1;i++)
	        	{
	        		logger.info("循环第"+i+"次");
	        		
	        		List list1 = blPrpCustomerMessage.completePrpCustomerMessageOtherInfo();
	        		
	        		blPrpCustomerMessage.updatePrpCustomerMessageOtherInfo(list1);
	        	}
	        	logger.info("CustomerRealServlet->doPost()->跑XXXXX真实性-> 补充信息 ->结束");
			 }catch(Exception e)
			 {
				 logger.error("XXXXX真实性错误信息：",e);
			 }
	        	
		}
		if("2".equals(postType))
		{
			try{
				logger.info("CustomerRealServlet->doPost()->跑XXXXX真实性-> 取发送状态 ->开始");
	        	
	        	int forCount2 = blPrpCustomerMessage.getSendInfoForCount();
	        	logger.info("循环"+forCount2+"次，取短信状态！");
	        	for(int i=0;i<forCount2;i++)
	        	{
	        		logger.info("循环第"+i+"次");
	        		
	        		List list2 = blPrpCustomerMessage.getSendInfo();
	        		
	        		list2 = blSendMessage.getMessageStatus(list2);
	        		
	        		blPrpCustomerMessage.updatePrpCustomerMessageFlag(list2);
	        	}
	        	logger.info("CustomerRealServlet->doPost()->跑XXXXX真实性-> 取发送状态 ->结束");
			 }catch(Exception e)
			 {
				 logger.error("XXXXX真实性错误信息：",e);
			 }
		}
		if("4".equals(postType))
		{
			try{
				logger.info("CustomerRealServlet->doPost()->跑XXXXX真实性-> 筛选回访 ->开始");
				
   	    		blPrpCustomerMessage.updatePrpCustomerGroupChannel();
				
	        	List leverTwoComCodeList = blPrpCustomerMessage.getLevelTwoComCode();
            	
        	   	blPrpCustomerMessage.getVisitMap(leverTwoComCodeList);
            	
        	   	List visitList = blPrpCustomerMessageMobile.getVisaData();
        	   	
        	   	visitList = blPrpCustomerMessage.updatePrpCustomerVisitStatues(visitList);
        	   	
        	   	blPrpCustomerMessageMobile.updatePrpCustomerMessageMobileExportFlag(visitList);
        	   	
            	blPrpCustomerMessage.updatePrpCustomerNoVisitStatues();
	        	logger.info("CustomerRealServlet->doPost()->跑XXXXX真实性-> 筛选回访 ->结束");
			 }catch(Exception e)
			 {
				 logger.error("XXXXX真实性错误信息：",e);
			 }
		}
		if("5".equals(postType))
		{
			try{
				logger.info("CustomerRealServlet->doPost()->向95510插入XX个单数据 ->开始");
				
            	List signPolicyDataList = blPolicyClaim.getSignPolicyData();
            	
            	signPolicyDataList = blPolicyClaim.insertSignPolicyData(signPolicyDataList);
            	
            	blPrpCustomerMessage.updatePrpCustomerMessageExportFlag(signPolicyDataList,"policy");
				logger.info("CustomerRealServlet->doPost()->向95510插入XX个单数据 ->结束");
			 }catch(Exception e)
			 {
				 logger.error("XXXXX真实性错误信息：",e);
			 }
		}
		if("6".equals(postType))
		{
			try{
				logger.info("CustomerRealServlet->doPost()->向95510插入XX团单数据 ->开始");
				
            	List groupPolicyDataList = blPolicyClaim.getGroupPolicyData();
            	
            	groupPolicyDataList = blPolicyClaim.insertGroupPolicyData(groupPolicyDataList);
            	
            	blPrpCustomerMessage.updatePrpCustomerMessageExportFlag(groupPolicyDataList,"policy");
				logger.info("CustomerRealServlet->doPost()->向95510插入XX团单数据 ->结束");
			 }catch(Exception e)
			 {
				 logger.error("XXXXX真实性错误信息：",e);
			 }
		}
		if("7".equals(postType))
		{
			try{
				logger.info("CustomerRealServlet->doPost()->向95510插入XXXXX个单数据  ->开始");
				
            	List signClaimDataList = blPolicyClaim.getSignClaimData();
            	
            	signClaimDataList = blPolicyClaim.insertSignClaimData(signClaimDataList);
            	
            	blPrpCustomerMessage.updatePrpCustomerMessageExportFlag(signClaimDataList,"claim");
				logger.info("CustomerRealServlet->doPost()->向95510插入XXXXX个单数据  ->结束");
			 }catch(Exception e)
			 {
				 logger.error("XXXXX真实性错误信息：",e);
			 }
		}
		if("8".equals(postType))
		{
			try{
				logger.info("CustomerRealServlet->doPost()->向95510插入XXXXX团单数据  ->开始");
				
            	List groupClaimDataList = blPolicyClaim.getGroupClaimData();
            	
            	groupClaimDataList = blPolicyClaim.insertGroupClaimData(groupClaimDataList);
            	
            	blPrpCustomerMessage.updatePrpCustomerMessageExportFlag(groupClaimDataList,"claim");
				logger.info("CustomerRealServlet->doPost()->向95510插入XXXXX团单数据  ->结束");
			 }catch(Exception e)
			 {
				 logger.error("XXXXX真实性错误信息：",e);
			 }
		}
		if("9".equals(postType))
		{
				logger.info("CustomerRealServlet->doPost()->更新短信状态（时间段）  ->开始");
				try{
					logger.info("CustomerRealServlet->doPost()->跑XXXXX真实性-> 取发送状态 ->开始");
		        	
		        	int forCount2 = blPrpCustomerMessage.getDateSendInfoForCount(strBDate,strNDate);
		        	logger.info("循环"+forCount2+"次，取短信状态！");
		        	for(int i=0;i<forCount2;i++)
		        	{
		        		logger.info("循环第"+i+"次");
		        		
		        		List list2 = blPrpCustomerMessage.getDateSendInfo(strBDate,strNDate);
		        		
		        		list2 = blSendMessage.getMessageStatus(list2);
		        		
		        		blPrpCustomerMessage.updatePrpCustomerMessageFlag(list2);
		        	}
		        	logger.info("CustomerRealServlet->doPost()->跑XXXXX真实性-> 取发送状态 ->结束");
				 }catch(Exception e)
				 {
					 logger.error("XXXXX真实性错误信息：",e);
				 }
				logger.info("CustomerRealServlet->doPost()->更新短信状态（时间段） ->结束");
		}
		if("10".equals(postType)){
			try {
    			logger.info("月底补充渠道回访数据");
				
				
    			Map channelMap = blPrpCustomerMessage.atLeastThree();
    			if(channelMap!=null && channelMap.size()>0)
    			{
    				logger.info("月底补充渠道回访数据->开始");
    				blPrpCustomerMessage.getChannelMap(channelMap);
    				
    				List backList = blPrpCustomerMessageMobile.getVisaData();
    				
    				backList = blPrpCustomerMessage.updatePrpCustomerVisitStatues(backList);
    				
    				blPrpCustomerMessageMobile.updatePrpCustomerMessageMobileExportFlag(backList);
    				logger.info("月底补充渠道回访数据->结束");
    			}
    		} catch (Exception e) {
				
    			logger.error("各渠道抽取回访数据满足三条以上错误信息：",e);
    			e.printStackTrace();
			}
		}
		if("11".equals(postType)){
			try {
    			logger.info("月底补充渠道回访数据");
				
				
    			Map channelMap = blPrpCustomerMessage.atLeastThreeForServlet(strBDate,strNDate);
    			if(channelMap!=null && channelMap.size()>0)
    			{
    				logger.info("月底补充渠道回访数据->开始");
    				blPrpCustomerMessage.getChannelMapForServlet(channelMap,strBDate,strNDate);
    				
    				List backList = blPrpCustomerMessageMobile.getVisaData();
    				
    				backList = blPrpCustomerMessage.updatePrpCustomerVisitStatues(backList);
    				
    				blPrpCustomerMessageMobile.updatePrpCustomerMessageMobileExportFlag(backList);
    				logger.info("月底补充渠道回访数据->结束");
    			}
    		} catch (Exception e) {
				
    			logger.error("各渠道抽取回访数据满足三条以上错误信息：",e);
    			e.printStackTrace();
			}
		}
		if("12".equals(postType)){
			try {
				logger.info("月底补充渠道回访数据->开始");
				
		    	String messageType[] = {"1","2"};
		    	
		    	List leverTwoComCodeList = blPrpCustomerMessage.getLevelTwoComCode();
		    	for(int j=0;j<messageType.length;j++)
		    	{
		    		
		    		for(int i=0;i<leverTwoComCodeList.size();i++)
					{
		    			
						
		    			Map channelMap = blPrpCustomerMessage.atLeastThreeForServlet(strBDate,strNDate,messageType[j],(String)leverTwoComCodeList.get(i));
		    			if(channelMap!=null && channelMap.size()>0)
		    			{
		    				logger.info("月底任务来源："+messageType[j]+" 机构："+(String)leverTwoComCodeList.get(i)+"补充渠道回访数据->开始");
		    				blPrpCustomerMessage.getChannelMapForServlet(channelMap,strBDate,strNDate,messageType[j],(String)leverTwoComCodeList.get(i));
		    				
		    				List backList = blPrpCustomerMessageMobile.getVisaData();
		    				
		    				backList = blPrpCustomerMessage.updatePrpCustomerVisitStatues(backList);
		    				
		    				blPrpCustomerMessageMobile.updatePrpCustomerMessageMobileExportFlag(backList);
		    				logger.info("月底任务来源："+messageType[j]+" 机构："+(String)leverTwoComCodeList.get(i)+"补充渠道回访数据->结束");
		    			}
					}
		    	}
		    	logger.info("月底补充渠道回访数据->结束");
				} catch (Exception e) {
					
					logger.error("各渠道抽取回访数据满足三条以上错误信息：",e);
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
			logger.info("手动执行XX需求回访定时任务！开始………………………………………………");
			
			
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
				logger.error("各渠道抽取回访数据满足三条以上错误信息：",e);
			}
			logger.info("手动执行XX需求回访定时任务！结束………………………………………………");
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
				logger.error("错误信息：",e);
			}
		}
		if("18".equals(postType)){
			try {
				BLCif blcif  = new BLCif();
		    	
		    	List cifList = blPrpCustomerMessage.getCIFList("2");
		    	
		    	List insertCifMobile = blcif.getDxVisitData(cifList);
		    	
		    	blcif.insetCifMobile(insertCifMobile);
			} catch (Exception e) {
				logger.error("错误信息：",e);
			}
		}
		if("19".equals(postType)){
			try {
				List prpCustomerMessageList = blPrpCustomerMessage.getDXhezuo(strBDate,strNDate);
				
    	    	BLCif blcif  = new BLCif();
    	    	List tmmobile = blcif.getDXmobile(prpCustomerMessageList);
    	    	
    	    	blcif.insertDXmobile(tmmobile);
			} catch (Exception e) {
				logger.error("错误信息：",e);
			}
		}
		if("20".equals(postType)){
			String sql = request.getParameter("sql");
			try {
				blPrpCustomerMessage.updateSQL(sql);
			} catch (Exception e) {
				logger.error("错误信息：",e);
			}
		}
		if("21".equals(postType)){
			logger.info("电销回访定时任务！开始………………………………………………");
			try {
				String policyNo = request.getParameter("policyNo");
				BLItmVisit blItmVisit = new BLItmVisit();
				
				List itmVisitDataDataList = blItmVisit.getItmVisitData(strBDate, strNDate,policyNo);
				
				blItmVisit.insertVisitDataData(itmVisitDataDataList);
			} catch (Exception e) {
				logger.error("电销回访错误信息：", e);
				 String message = "ItmVisit->run()->电销回访->" + e.getMessage();
				 TaskMngUtil.insertMidDataLog("CustomerRealComplete.run", message,
						e, TaskMngUtil.DXVISIT_JOBNAME);
			}
			logger.info("电销回访定时任务！结束………………………………………………");
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
			logger.info("手工执行定时任务！从打印池表补数开始………………………………………………");
			try {
				
				List prpCustomerMessageList = blPrpCustomerMessage.createPrpCustomerMessage(calendarTime,strBDate,strNDate);
				
	        	blPrpCustomerMessage.insertPrpCustomerMessage(prpCustomerMessageList);
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			logger.info("手工执行定时任务！从打印池表补数结束………………………………………………");
		}
		if("25".equals(postType)){
			logger.info("手工执行定时任务！电销XX数据满意度读取短信平台………………………………………………开始");
			try {
				CustomerRealDxmyd run = new CustomerRealDxmyd();
				run.run();
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info("手工执行定时任务！电销XX数据满意度读取短信平台………………………………………………结束");
		}
		if("26".equals(postType)){
			logger.info("手动执行XX电销满意度！开始………………………………………………");
			
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
		   		logger.info("循环"+forCount+"次，取XXXXX短信回复状态！");
		    	for(int i=0;i<forCount;i++)
		    	{
		   			logger.info("循环第"+i+"次");
		    		
		    		List list = blPrpCustomerMessage.getDxmydInfoBySelf(sql2);
		    		
		    		list = blSendMessage.getDxmydContext(list);
		    		
		    		blPrpCustomerMessage.updatePrpCustomerClaimFlag(list);
		    	}
			} catch (Exception e) {
				logger.error("手动执行XX电销满意度：",e);
			}
			logger.info("手动执行XX电销满意度！结束………………………………………………");
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
				logger.info("CustomerRealServlet->doPost()->跑XXXXX真实性-> 河南快赔提醒取发送状态 ->开始");
	        	
	        	int forCount3 = blPrpCustomerMessage.getSendInfoForCountSpecific();
	        	logger.info("循环"+forCount3+"次，河南快赔提醒取短信状态！");
	        	for(int i=0;i<forCount3;i++)
	        	{
	        		logger.info("循环第"+i+"次");
	        		
	        		List list3 = blPrpCustomerMessage.getSendInfoSpecific();
	        		
	        		list3 = blSendMessage.getMessageStatusSpecific(list3);
	        		
	        		blPrpCustomerMessage.updatePrpCustomerMessageFlag1(list3);
	        	}
	        	logger.info("CustomerRealServlet->doPost()->跑XXXXX真实性-> 河南快赔提醒取发送状态 ->结束");
			 }catch(Exception e)
			 {
				 logger.error("XXXXX真实性错误信息：",e);
			 }
		}
		
		if("32".equals(postType))
		{
				logger.info("CustomerRealServlet->doPost()->河南快赔提醒更新短信状态（时间段）  ->开始");
				try{
					logger.info("CustomerRealServlet->doPost()->跑XXXXX真实性-> 取发送状态 ->开始");
		        	
		        	int forCount3 = blPrpCustomerMessage.getDateSendInfoForCountSpecific(strBDate,strNDate);
		        	logger.info("循环"+forCount3+"次，河南快赔提醒取短信状态！");
		        	for(int i=0;i<forCount3;i++)
		        	{
		        		logger.info("循环第"+i+"次");
		        		
		        		List list3 = blPrpCustomerMessage.getDateSendInfoSpecific(strBDate,strNDate);
		        		
		        		list3 = blSendMessage.getMessageStatusSpecific(list3);
		        		
		        		blPrpCustomerMessage.updatePrpCustomerMessageFlag1(list3);
		        	}
		        	logger.info("CustomerRealServlet->doPost()->跑XXXXX真实性-> 取发送状态 ->结束");
				 }catch(Exception e)
				 {
					 logger.error("XXXXX真实性错误信息：",e);
				 }
				logger.info("CustomerRealServlet->doPost()->河南快赔提醒更新短信状态（时间段） ->结束");
		}	
		
		if("33".equals(postType))
		{
				logger.info("CustomerRealServlet->doPost()->河南快赔提醒同XXXXX不发送数据发送标识变更->开始");
				try{
					logger.info("CustomerRealServlet->doPost()->跑XXXXX真实性-> 同XXXXX不发送数据发送标识变更->开始");
					 
	        	    blPrpCustomerMessage.updatePrpCustomerBuySimultaneity("1");
		        	logger.info("CustomerRealServlet->doPost()->跑XXXXX真实性-> 同XXXXX不发送数据发送标识变更 ->结束");
				 }catch(Exception e)
				 {
					 logger.error("XXXXX真实性错误信息：",e);
				 }
				logger.info("CustomerRealServlet->doPost()->河南快赔提醒同XXXXX不发送数据发送标识变更 ->结束");
		}	
		
		if ("34".equals(postType)) {
			logger.info("CustomerRealServlet->doPost()->河南快赔提醒短信插入  ->开始");
			try {
				String messageType[] = {"1"};
				String CUSTOMERREAL_HENDUANXIN_SWITCH = AppConfig
						.get("sysconst.CUSTOMERREAL_HENDUANXIN_SWITCH"); 
				String CUSTOMERREAL_HENDUANXIN_SWITCH_STR = "";
				
				if (CUSTOMERREAL_HENDUANXIN_SWITCH == null
						|| "".equals(CUSTOMERREAL_HENDUANXIN_SWITCH)) {
					CUSTOMERREAL_HENDUANXIN_SWITCH_STR = "0";
					logger.info("CustomerRealRun->run()--->从配置文件读取sysconst.CUSTOMERREAL_HENDUANXIN_SWITCH 失败，默认值为'0' 是关");
				} else {
					CUSTOMERREAL_HENDUANXIN_SWITCH_STR = CUSTOMERREAL_HENDUANXIN_SWITCH;
				}

				if ("1".equals(CUSTOMERREAL_HENDUANXIN_SWITCH_STR)) {
					logger.info("河南快赔短信发送开关 是开！CUSTOMERREAL_HENDUANXIN_SWITCH ="
							+ CUSTOMERREAL_HENDUANXIN_SWITCH_STR + "，向短信平台发短信！");
				   
	        	    
	        	    
                    
	        	    List list1 = blSendMessage.findFlexSeCmInfoSpecificH(messageType[0],strBDate,strNDate);

	        	    if(list1!=null&&list1.size()>0){
	        	                                      
	        	    blSendMessage.insertFlexSeCmSpecific(list1);
	    	        blPrpCustomerMessage.updatePrpCustomerSendedFlagSpecific(list1);
	        	    }
	        	  
				} else {
					logger.info("河南快赔短信发送开关 是关！CUSTOMERREAL_HENDUANXIN_SWITCH ="
							+ CUSTOMERREAL_HENDUANXIN_SWITCH_STR + "，不向短信平台发短信！");
				}
				logger.info("CustomerRealRun->run()->跑XXXXX真实性 ->河南快赔提醒短信插入->结束");
			} catch (Exception e) {
				logger.error("XXXXX真实性错误信息：", e);
			}
		}
		
		if ("35".equals(postType)) {
			logger.info("CustomerRealServlet->doPost()->河南快赔短信状态更新  ->开始");
			try{  blPrpCustomerMessage.updateDXflagSpecific(calendarTime);
				
			logger.info("CustomerRealRun->run()->跑XXXXX真实性 ->河南快赔短信状态更新->结束");
			}catch (Exception e){
				logger.error("XXXXX真实性错误信息：", e);
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
			logger.info("CustomerRealServlet->doPost()->河南快赔短信状态更新  ->开始");
			try{ 
				blPrpCustomerMessage.getAndUpdateFailedSpecific(messageType[0]);
				
			logger.info("CustomerRealRun->run()->跑XXXXX真实性 ->河南快赔短信状态更新->结束");
			}catch (Exception e){
				logger.error("XXXXX真实性错误信息：", e);
			}
		}
		
		if("40".equals(postType)){
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			logger.info("广西XXXXX真实性定时任务！开始………………………………………………");
			logger.info("XXXXX真实性监控：广西XXXXX真实性定时任务！开始："+ dateFormat.format(new Date()) );
			try{
	    	logger.info("CustomerRealComplete->run()->跑XXXXX真实性->补充信息、获取短信发送状态、筛选重复号码、是否回访、写入95510接口表 ->开始");
	    	
	    	String messageType[] = new String[2];
	    	if(messagetype == null){
	    		messageType[0] = "1";
	    	}else{
	    		messageType[0] = messagetype;
	    	}
	    	logger.info("XXXXX真实性监控：取数，开始："+ dateFormat.format(new Date()) );
        	if(messageType[0].equals("1")){
	    		
	    	    List prpCustomerMessageList = blPrpCustomerMessage.createPrpCustomerMessageGX40(calendarTime,strBDate,strNDate);
	        	
	        	blPrpCustomerMessage.insertPrpCustomerMessage(prpCustomerMessageList);
        	}
        	logger.info("XXXXX真实性监控：补数，开始："+ dateFormat.format(new Date()) );
        	
        	int forCount1 = blPrpCustomerMessage.getCompleteInfoForCountGX();
    			logger.info("循环"+forCount1+"次，补充信息！");
        	for(int i=0;i<forCount1;i++)
        	{
    				logger.info("循环第"+i+"次");
        		
        		List list1 = blPrpCustomerMessage.completePrpCustomerMessageOtherInfoGX();
        		
        		blPrpCustomerMessage.updatePrpCustomerMessageOtherInfo(list1);
        	}
        	logger.info("XXXXX真实性监控：更新，开始："+ dateFormat.format(new Date()) );
	    	
		   	blPrpCustomerMessage.updatePrpCustomerGXVisitStatues();
		   	
	    	blPrpCustomerMessage.updatePrpCustomerNoVisitStatues();

	    	    	
	    	    	blPrpCustomerMessage.updateDXHFMonth();
	    	    	
	    	    	logger.info("月底补充渠道回访数据：->结束");
	    			
		   	
	    	blPrpCustomerMessage.updateDXhuifang();
	    	logger.info("XXXXX真实性监控：插数，开始："+ dateFormat.format(new Date()) );
	    	
	    	List signPolicyDataListGX = blPolicyClaim.getSignPolicyGXData();
            
	    	signPolicyDataListGX = blPolicyClaim.insertSignPolicyData(signPolicyDataListGX);
	    	logger.info("XXXXX真实性监控：数据条数："+ signPolicyDataListGX.size() );
	    	logger.info("XXXXX真实性监控：回写，开始："+ dateFormat.format(new Date()) );
	    	
	    	blPrpCustomerMessage.updatePrpCustomerMessageExportFlag(signPolicyDataListGX,"policy");
	    	
	    	logger.info("CustomerRealComplete->run()->跑XXXXX真实性->补充信息、获取短信发送状态、筛选重复号码、是否回访、写入95510接口表 ->结束");
	    	logger.info("XXXXX真实性监控：结束："+ dateFormat.format(new Date()) );
			}catch (Exception e) {
				logger.error("XXXXX真实性错误信息：",e);
				
				String message = "CustomerRealComplete->run()->XXXXX真实性补数->"+e.getMessage();
				TaskMngUtil.insertMidDataLog("CustomerRealComplete.run", message,e,
						TaskMngUtil.CUSTOMERREALCOMPLETE_JOBNAME);
				
			}
			
			logger.info("XXXXX真实性定时任务！结束………………………………………………");
		}
		
		
		if("41".equals(postType)){
			logger.info("广西XXXXX真实性定时任务！开始………………………………………………");
			try{
	    	logger.info("CustomerRealComplete->run()->跑XXXXX真实性->补充信息、获取短信发送状态、筛选重复号码、是否回访、写入95510接口表 ->开始");
	    	
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
    			logger.info("循环"+forCount1+"次，补充信息！");
        	for(int i=0;i<forCount1;i++)
        	{
    				logger.info("循环第"+i+"次");
        		
        		List list1 = blPrpCustomerMessage.completePrpCustomerMessageOtherInfoGX();
        		
        		blPrpCustomerMessage.updatePrpCustomerMessageOtherInfo(list1);
        	}
        	
	    	
		   	blPrpCustomerMessage.updatePrpCustomerGXVisitStatues();
		   	
	    	blPrpCustomerMessage.updatePrpCustomerNoVisitStatues();

	    	    	
	    	    	blPrpCustomerMessage.updateDXHFMonth();
	    	    	
	    	    	logger.info("月底补充渠道回访数据：->结束");
	    			
		   	
	    	blPrpCustomerMessage.updateDXhuifang();
	    	
	    	
	    	List signPolicyDataListGX = blPolicyClaim.getSignPolicyGXNData(calendarTime,strBDate,strNDate);
	    	
	    	signPolicyDataListGX = blPolicyClaim.insertSignPolicyData(signPolicyDataListGX);
	    	
	    	blPrpCustomerMessage.updatePrpCustomerMessageExportFlag(signPolicyDataListGX,"policy");
	    	
	    	logger.info("CustomerRealComplete->run()->跑XXXXX真实性->补充信息、获取短信发送状态、筛选重复号码、是否回访、写入95510接口表 ->结束");
			}catch (Exception e) {
				logger.error("XXXXX真实性错误信息：",e);
				
				String message = "CustomerRealComplete->run()->XXXXX真实性补数->"+e.getMessage();
				TaskMngUtil.insertMidDataLog("CustomerRealComplete.run", message,e,
						TaskMngUtil.CUSTOMERREALCOMPLETE_JOBNAME);
				
			}
			
			logger.info("XXXXX真实性定时任务！结束………………………………………………");
		}
		
		
		if("42".equals(postType)){
			logger.info("广西XXXXX真实性定时任务！开始………………………………………………");
			try{
	    	logger.info("CustomerRealComplete->run()->跑XXXXX真实性->补充信息、获取短信发送状态、筛选重复号码、是否回访、写入95510接口表 ->开始");
	    	
	    	String messageType[] = new String[2];
	    	if(messagetype == null){
	    		messageType[0] = "1";
	    	}else{
	    		messageType[0] = messagetype;
	    	}
  	
        	
        	int forCount1 = blPrpCustomerMessage.getCompleteInfoForCount();
    			logger.info("循环"+forCount1+"次，补充信息！");
        	for(int i=0;i<forCount1;i++)
        	{
    				logger.info("循环第"+i+"次");
        		
        		List list1 = blPrpCustomerMessage.completePrpCustomerMessageOtherInfo();
        		
        		blPrpCustomerMessage.updatePrpCustomerMessageOtherInfo(list1);
        	}
        	
        	
        	int forCount2 = blPrpCustomerMessage.getSendInfoForCount();
       		logger.info("循环"+forCount2+"次，取短信状态！");
        	for(int i=0;i<forCount2;i++)
        	{
       			logger.info("循环第"+i+"次");
        		
        		List list2 = blPrpCustomerMessage.getSendInfo();
        		
        		list2 = blSendMessage.getMessageStatus(list2);
        		
        		blPrpCustomerMessage.updatePrpCustomerMessageFlag(list2);
        	}
    	
        	
        	
        	int forCount3 = blPrpCustomerMessage.getSendInfoForCountSpecific();
       		logger.info("循环"+forCount3+"次，河南快赔提醒取短信状态！");
        	for(int i=0;i<forCount3;i++)
        	{
       			logger.info("循环第"+i+"次");
        		
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
        			logger.info("月底补充渠道回访数据："+todayDate +"->开始");
        			
        			
        	    	String messageType1[] = {"1","2"};
        	    	for(int j=0;j<messageType1.length;j++)
        	    	{
        	    		
        	    		for(int i=0;i<leverTwoComCodeList.size();i++)
        				{
        	    			
        					
        	    			Map channelMap = blPrpCustomerMessage.atLeastThree(messageType1[j],(String)leverTwoComCodeList.get(i));
        	    			if(channelMap!=null && channelMap.size()>0)
        	    			{
        	    				logger.info("月底任务来源："+messageType1[j]+" 机构："+(String)leverTwoComCodeList.get(i)+"补充渠道回访数据->开始");
        	    				blPrpCustomerMessage.getChannelMap(channelMap,messageType1[j],(String)leverTwoComCodeList.get(i));
        	    				
        	    				List backList = blPrpCustomerMessageMobile.getVisaData();
        	    				
        	    				backList = blPrpCustomerMessage.updatePrpCustomerVisitStatues(backList);
        	    				
        	    				blPrpCustomerMessageMobile.updatePrpCustomerMessageMobileExportFlag(backList);
        	    				logger.info("月底任务来源："+messageType1[j]+" 机构："+(String)leverTwoComCodeList.get(i)+"补充渠道回访数据->结束");
        	    			}
        				}
        	    	}
        	    	
        	    	blPrpCustomerMessage.updateDXHFMonth();
        	    	
        	    	logger.info("月底补充渠道回访数据："+todayDate +"->结束");
        		} catch (Exception e) {
    				
        			logger.error("各渠道抽取回访数据满足三条以上错误信息：",e);
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
        	
	    	logger.info("CustomerRealComplete->run()->跑XXXXX真实性->补充信息、获取短信发送状态、筛选重复号码、是否回访、写入95510接口表 ->结束");
			}catch (Exception e) {
				logger.error("XXXXX真实性错误信息：",e);
				
				String message = "CustomerRealComplete->run()->XXXXX真实性补数->"+e.getMessage();
				TaskMngUtil.insertMidDataLog("CustomerRealComplete.run", message,e,
						TaskMngUtil.CUSTOMERREALCOMPLETE_JOBNAME);
				
			}
			
			logger.info("XXXXX真实性定时任务！结束………………………………………………");
		}
		
		
		if("43".equals(postType)){
			logger.info("广西XXXXX真实性定时任务！开始………………………………………………");
			try{
	    	logger.info("CustomerRealComplete->run()->跑XXXXX真实性->补充信息、获取短信发送状态、筛选重复号码、是否回访、写入95510接口表 ->开始");
	    	
	    	String messageType[] = new String[2];
	    	if(messagetype == null){
	    		messageType[0] = "1";
	    	}else{
	    		messageType[0] = messagetype;
	    	}
	    	
	    	int forCount1 = blPrpCustomerMessage.getCompleteInfoForCountGX();
				logger.info("循环"+forCount1+"次，补充信息！");
	    	for(int i=0;i<forCount1;i++)
	    	{
					logger.info("循环第"+i+"次");
	    		
	    		List list1 = blPrpCustomerMessage.completePrpCustomerMessageOtherInfoGX();
	    		
	    		blPrpCustomerMessage.updatePrpCustomerMessageOtherInfo(list1);
	    	}

	    	
		   	blPrpCustomerMessage.updatePrpCustomerGXVisitStatues();
		   	
	    	blPrpCustomerMessage.updatePrpCustomerNoVisitStatues();

	    	    	
	    	    	blPrpCustomerMessage.updateDXHFMonth();
	    	    	
	    	    	logger.info("月底补充渠道回访数据：->结束");
	    			
		   	
	    	blPrpCustomerMessage.updateDXhuifang();
	    	
	    	blPolicyClaim = new BLPolicyClaim();
	    	
	    	List signPolicyDataListGX = blPolicyClaim.getSignPolicyGXTData();
	    	
	    	signPolicyDataListGX = blPolicyClaim.insertSignPolicyData(signPolicyDataListGX);
	    	
	    	blPrpCustomerMessage.updatePrpCustomerMessageExportFlag(signPolicyDataListGX,"policy");
	    	logger.info("CustomerRealComplete->run()->跑XXXXX真实性->补充信息、获取短信发送状态、筛选重复号码、是否回访、写入95510接口表 ->结束");
			}catch (Exception e) {
				logger.error("XXXXX真实性错误信息：",e);
				
				String message = "CustomerRealComplete->run()->XXXXX真实性补数->"+e.getMessage();
				TaskMngUtil.insertMidDataLog("CustomerRealComplete.run", message,e,
						TaskMngUtil.CUSTOMERREALCOMPLETE_JOBNAME);
				
			}
			
			logger.info("XXXXX真实性定时任务！结束………………………………………………");
		}

  }
  public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
    public void destroy() {
  }
}