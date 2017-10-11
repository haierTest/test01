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


public class CustomerRealCompleteGX
{
	private final Log logger = LogFactory.getLog(getClass());

	/**
	 * ��ʱ���򣺽��ط�����д��95510�ӿڱ�
	 */
	@SuppressWarnings("unchecked")
	public void run() {
		logger.info("XXXXX��ʵ�Զ�ʱ���񣡿�ʼ������������������������������������");
		try{
    	logger.info("CustomerRealCompleteGX->run()->��XXXXX��ʵ��->������Ϣ����ȡ���ŷ���״̬��ɸѡ�ظ����롢�Ƿ�طá�д��95510�ӿڱ� ->��ʼ");
    	
    	BLPrpCustomerMessage blPrpCustomerMessage = new BLPrpCustomerMessage();
    	
    	
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
    	
    	BLPolicyClaim blPolicyClaim = new BLPolicyClaim();
    	
    	List signPolicyDataListGX = blPolicyClaim.getSignPolicyGXTData();
    	
    	signPolicyDataListGX = blPolicyClaim.insertSignPolicyData(signPolicyDataListGX);
    	
    	blPrpCustomerMessage.updatePrpCustomerMessageExportFlag(signPolicyDataListGX,"policy");
    	
    	logger.info("CustomerRealCompleteGX->run()->��XXXXX��ʵ��->������Ϣ����ȡ���ŷ���״̬��ɸѡ�ظ����롢�Ƿ�طá�д��95510�ӿڱ� ->����");
		}catch (Exception e) {
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			
			String message = "CustomerRealCompleteGX->run()->XXXXX��ʵ�Բ���->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("CustomerRealCompleteGX.run", message,e,
					TaskMngUtil.CUSTOMERREALCOMPLETE_JOBNAME);
			
		}
		
		logger.info("XXXXX��ʵ�Զ�ʱ���񣡽���������������������������������������");
	}
}
