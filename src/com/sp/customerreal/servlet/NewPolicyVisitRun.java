package com.sp.customerreal.servlet;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sp.customerreal.blsvr.BLPolicyClaim;
import com.sp.taskmng.util.TaskMngUtil;

/**
 * @ClassName: NewPolicyVisitRun
 * @Description: ��ʱ����XX����
 * @author yinwch
 * @date May 25, 2011 8:12:46 PM
 * 
 */
public class NewPolicyVisitRun {
	private final Log logger = LogFactory.getLog(getClass());

	/**
	 * ��ʱ����XX����طö�ʱ����
	 */
	@SuppressWarnings("unchecked")
	public void run() {
		logger.info("XX����طö�ʱ���񣡿�ʼ������������������������������������");
    	logger.info("NewPolicyVisitRun->run()->XX����طö�ʱ����->��ʼ");
		try {
			BLPolicyClaim blPolicyClaim = new BLPolicyClaim();
			
			String sql = "SELECT a.*," +
						"(SELECT t.ventureflag FROM prpcmain t WHERE a.policyno=t.policyno) ventureflag, " +
						"(SELECT t.mobile FROM prpduser t WHERE a.SURVEYORHANDLID=t.usercode) managertel," +
						"b.cif_custid,b.mobile1,b.mobile2,b.mobile3,b.mobile4,b.kxd1,b.kxd2,b.kxd3,b.kxd4,b.resource1,b.resource2,b.resource3,b.resource4,b.modifydate1,b.modifydate2,b.modifydate3,b.modifydate4  " +
						"FROM PRPCUSTOMERMESSAGE a, prpcitemcar c,PRPCUSTOMERMESSAGE_CIFMOBILE b " +
						"WHERE a.MESSAgetype='1' " +
						"AND a.CHANNELTYPE = 'N01' " +
						"AND a.groupflag ='0' " +
						"AND a.CREATEDDATE BETWEEN TRUNC(SYSDATE-2,'dd') " +
						"AND TRUNC(SYSDATE-1,'dd') " +
						"AND a.policyno = c.policyno " +
						"AND c.usenaturecode = '8A' " +
						"AND a.id = b.id(+) ";
			
			List dxDataDataList = blPolicyClaim.getNewPolicy(sql);
			
			blPolicyClaim.insertNewPolicyData(dxDataDataList);
		} catch (Exception e) {
			logger.error("�����طô�����Ϣ��", e);
			 String message = "ItmVisit->run()->�����ط�->" + e.getMessage();
			 TaskMngUtil.insertMidDataLog("CustomerRealComplete.run", message,
					e, TaskMngUtil.DXVISIT_JOBNAME);
		}
		logger.info("NewPolicyVisitRun->run()->XX����طö�ʱ����->����������������������������������������");
	}
}
