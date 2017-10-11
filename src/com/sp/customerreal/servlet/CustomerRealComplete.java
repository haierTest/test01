package com.sp.customerreal.servlet;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sp.customerreal.blsvr.BLPolicyClaim;
import com.sp.customerreal.blsvr.BLPrpCustomerMessage;
import com.sp.customerreal.blsvr.BLPrpCustomerMessageMobile;
import com.sp.customerreal.blsvr.BLSendMessage;
import com.sp.taskmng.util.TaskMngUtil;

/**
 * @ClassName: CustomerRealComplete
 * @Description: ������Ϣ����ȡ���ŷ���״̬��ɸѡ�ظ����롢�Ƿ�ط� 22���Ժ�ʼִ��
 * @author yinwch
 * @date Apr 15, 2011 12:57:19 PM
 * 
 */
public class CustomerRealComplete {
	private final Log logger = LogFactory.getLog(getClass());

	/**
	 * ��ʱ���򣺲�����Ϣ����ȡ���ŷ���״̬��ɸѡ�ظ����롢�Ƿ�طá�д��95510�ӿڱ�
	 */
	@SuppressWarnings("unchecked")
	public void run() {
		logger.info("XXXXX��ʵ�Զ�ʱ���񣡿�ʼ������������������������������������");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		try{
    	logger.info("CustomerRealComplete->run()->��XXXXX��ʵ��->������Ϣ����ȡ���ŷ���״̬��ɸѡ�ظ����롢�Ƿ�طá�д��95510�ӿڱ� ->��ʼ");
    	
    	BLPrpCustomerMessage blPrpCustomerMessage = new BLPrpCustomerMessage();
    	BLSendMessage blSendMessage = new BLSendMessage();
    	BLPrpCustomerMessageMobile blPrpCustomerMessageMobile = new BLPrpCustomerMessageMobile();
    	logger.info("XXXXX��ʵ�Լ�أ�CustomerRealComplete�������ݣ���ʼ��"+ dateFormat.format(new Date()) );
    	
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
    	logger.info("XXXXX��ʵ�Լ�أ�CustomerRealComplete�������ݣ�������"+ dateFormat.format(new Date()) );
    	
    	
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
    			
    			
    	    	String messageType[] = {"1","2"};
    	    	for(int j=0;j<messageType.length;j++)
    	    	{
    	    		
    	    		for(int i=0;i<leverTwoComCodeList.size();i++)
    				{
    	    			
    					
    	    			Map channelMap = blPrpCustomerMessage.atLeastThree(messageType[j],(String)leverTwoComCodeList.get(i));
    	    			if(channelMap!=null && channelMap.size()>0)
    	    			{
    	    				logger.info("�µ�������Դ��"+messageType[j]+" ������"+(String)leverTwoComCodeList.get(i)+"���������ط�����->��ʼ");
    	    				blPrpCustomerMessage.getChannelMap(channelMap,messageType[j],(String)leverTwoComCodeList.get(i));
    	    				
    	    				List backList = blPrpCustomerMessageMobile.getVisaData();
    	    				
    	    				backList = blPrpCustomerMessage.updatePrpCustomerVisitStatues(backList);
    	    				
    	    				blPrpCustomerMessageMobile.updatePrpCustomerMessageMobileExportFlag(backList);
    	    				logger.info("�µ�������Դ��"+messageType[j]+" ������"+(String)leverTwoComCodeList.get(i)+"���������ط�����->����");
    	    			}
    				}
    	    	}
    	    	
    	    	blPrpCustomerMessage.updateDXHFMonth();
    	    	
    	    	logger.info("�µײ��������ط����ݣ�"+todayDate +"->����");
    			/*
				
				
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
    			*/
    		} catch (Exception e) {
				
    			logger.error("��������ȡ�ط����������������ϴ�����Ϣ��",e);
    			e.printStackTrace();
			}
    	}
    	
	   	
    	blPrpCustomerMessage.updateDXhuifang();
    	
    	BLPolicyClaim blPolicyClaim = new BLPolicyClaim();
    	
    	logger.info("XXXXX��ʵ�Լ�أ��ǹ���ȡ������ʼ��"+ dateFormat.format(new Date()) );
    	List signPolicyDataList = blPolicyClaim.getSignPolicyData();
    	logger.info("XXXXX��ʵ�Լ�أ��ǹ�������������"+ signPolicyDataList.size() );
    	
    	logger.info("XXXXX��ʵ�Լ�أ�����ȡ������ʼ��"+ dateFormat.format(new Date()) );
    	List signPolicyGXDataList = blPolicyClaim.getSignPolicyGXData();
    	logger.info("XXXXX��ʵ�Լ�أ���������������"+ signPolicyGXDataList.size() );
    	if(signPolicyGXDataList != null && signPolicyGXDataList.size() >0){
    		for(int i = 0; i < signPolicyGXDataList.size(); i++){
    			signPolicyDataList.add(signPolicyGXDataList.get(i));
    		}
    	}
    	logger.info("XXXXX��ʵ�Լ�أ�������������"+ signPolicyDataList.size() );
    	logger.info("XXXXX��ʵ�Լ�أ��������ݣ���ʼ��"+ dateFormat.format(new Date()) );
    	
    	signPolicyDataList = blPolicyClaim.insertSignPolicyData(signPolicyDataList);
    	logger.info("XXXXX��ʵ�Լ�أ���д״̬����ʼ��"+ dateFormat.format(new Date()) );
    	
    	blPrpCustomerMessage.updatePrpCustomerMessageExportFlag(signPolicyDataList,"policy");
    	logger.info("XXXXX��ʵ�Լ�أ���д��������ʼ��"+ dateFormat.format(new Date()) );
    	
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
		logger.info("XXXXX��ʵ�Լ�أ�XXXXX��ʵ�Զ�ʱ���񣡽�����"+ dateFormat.format(new Date()) );
		logger.info("XXXXX��ʵ�Զ�ʱ���񣡽���������������������������������������");
	}
}
