package com.sp.customerreal.servlet;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sp.customerreal.blsvr.BLPolicyClaim;
import com.sp.customerreal.blsvr.BLPrpCustomerMessage;
import com.sp.customerreal.blsvr.BLSendMessage;
import com.sp.taskmng.util.TaskMngUtil;


/**
 * @ClassName: ClaimIsSatisfied
 * @Description: XXXXX100%�ط�
 * @author yinwch
 * @date 2012-8-16 ����09:25:39
 * 
 */
public class ClaimIsSatisfied {
	private final Log logger = LogFactory.getLog(getClass());

	public void run() {
		logger.info("XXXXX100%�طö�ʱ���񣡿�ʼ������������������������������������");
		try{
    	logger.info("ClaimIsSatisfied->run()->XXXXX100%�ط�->������Ϣ����ȡ���ŷ���״̬��ɸѡ�ظ����롢�Ƿ�طá�д��95510�ӿڱ� ->��ʼ");
    	
    	BLPrpCustomerMessage blPrpCustomerMessage = new BLPrpCustomerMessage();
    	BLSendMessage blSendMessage = new BLSendMessage();
    	
    	
    	int forCount = blPrpCustomerMessage.getClaimForCount();
   		logger.info("ѭ��"+forCount+"�Σ�ȡXXXXX���Żظ�״̬��");
    	for(int i=0;i<forCount;i++)
    	{
   			logger.info("ѭ����"+i+"��");
    		
    		List list2 = blPrpCustomerMessage.getClaimInfo();
    		
    		list2 = blSendMessage.getClaimContext(list2);
    		
    		blPrpCustomerMessage.updatePrpCustomerClaimFlag(list2);
    	}
    	
    	BLPolicyClaim blPolicyClaim = new BLPolicyClaim();
    	
    	List signClaimList = blPolicyClaim.getSignClaim();
    	
    	signClaimList = blPolicyClaim.insertSignClaim(signClaimList);
    	
    	blPrpCustomerMessage.updateExportMesFlag(signClaimList,"claim");
    	
    	
    	List groupClaimDataList = blPolicyClaim.getGroupClaim();
    	
    	groupClaimDataList = blPolicyClaim.insertGroupClaim(groupClaimDataList);
    	
    	blPrpCustomerMessage.updateExportMesFlag(groupClaimDataList,"claim");
    	
    	
    	logger.info("ClaimIsSatisfied->run()->XXXXX100%�ط�->������Ϣ����ȡ���ŷ���״̬��ɸѡ�ظ����롢�Ƿ�طá�д��95510�ӿڱ� ->����");
		}catch (Exception e) {
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			
			String message = "ClaimIsSatisfied->run()->XXXXX100%�ط�->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("ClaimIsSatisfied.run", message,e,
					TaskMngUtil.CLAIMISSATISFIED_JOBNAME);
			
		}
		
		logger.info("XXXXX100%�طö�ʱ���񣡽���������������������������������������");
	}
}
