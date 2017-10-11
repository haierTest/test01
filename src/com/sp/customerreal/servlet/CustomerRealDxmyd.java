package com.sp.customerreal.servlet;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sp.customerreal.blsvr.BLPrpCustomerMessage;
import com.sp.customerreal.blsvr.BLSendMessage;
import com.sp.taskmng.util.TaskMngUtil;


/**
 * @ClassName: CustomerRealDxmyd
 * @Description: XX���������
 * @author yinwch
 * @date 2013-5-20 ����09:25:39
 * 
 */
public class CustomerRealDxmyd {
	private final Log logger = LogFactory.getLog(getClass());

	public void run() {
		logger.info("XX��������ȣ���ʼ������������������������������������");
		try{
    	logger.info("CustomerRealDxmyd->run()->XX���������->��ȡXXXXX�ظ�->��ʼ");
    	
    	BLPrpCustomerMessage blPrpCustomerMessage = new BLPrpCustomerMessage();
    	BLSendMessage blSendMessage = new BLSendMessage();
    	String sql = "SELECT COUNT(*) FROM prpcustomermessage  " +
						"WHERE " +
						"messagetype = '1' " +
						"AND messageflag = '1' " +
						"AND iscontent = '-1' " +
						"AND CHANNELTYPE in ('N071','N075','N105') " +
						"AND createddate BETWEEN TO_DATE(TO_CHAR(SYSDATE - 2, 'YYYY-MM-DD'), 'YYYY-MM-DD') " +
						"AND TO_DATE(TO_CHAR(SYSDATE - 1, 'YYYY-MM-DD'), 'YYYY-MM-DD') " +
						"AND GROUPSENDSEQ IS NULL " +
						"AND (READMESSAGE IS NULL OR READMESSAGE < SYSDATE-1/4) ";
    	
    	int forCount = blPrpCustomerMessage.getDxmydForCount(sql);
   		logger.info("ѭ��"+forCount+"�Σ�ȡXXXXX���Żظ�״̬��");
    	for(int i=0;i<forCount;i++)
    	{
   			logger.info("ѭ����"+i+"��");
    		
    		List list = blPrpCustomerMessage.getDxmydInfo();
    		
    		list = blSendMessage.getDxmydContext(list);
    		
    		blPrpCustomerMessage.updatePrpCustomerClaimFlag(list);
    	}
    	
    	logger.info("ClaimIsSatisfied->run()->XX���������->��ȡXXXXX�ظ�->����");
		}catch (Exception e) {
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			String message = "CustomerRealDxmyd->run()->XX���������->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("CustomerRealDxmyd.run", message,e,TaskMngUtil.CUSTOMERREALDXMYD_JOBNAME);
		}
		
		logger.info("XX��������ȣ�����������������������������������������");
	}
}
