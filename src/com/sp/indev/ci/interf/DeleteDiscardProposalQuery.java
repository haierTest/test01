package com.sp.indiv.ci.interf;

import com.sp.sysframework.common.datatype.DateTime;
import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;

/**
 * ɾ��������XX����ѯ��
 *
 * @author �̿�
 */
public class DeleteDiscardProposalQuery {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
	/*	String strUrl = "";
		String strUser = "";
		String strPwd = "";
		DbPool dbpool = new DbPool();
		try { 
			strUrl = "jdbc:oracle:thin:@192.168.60.117:1521:devdb";
			strUser = "sunnytest";
			strPwd = "sunnytest";

			dbpool.openOra(strUrl, strUser, strPwd);

			DeleteDiscardProposalQuery deleteDiscardProposalQuery = new DeleteDiscardProposalQuery();
			deleteDiscardProposalQuery.deleteDiscardProposalQuery(dbpool, "", "2006-06-30");
		} catch (Exception ex) {
			throw ex;
		} finally {
			dbpool.close();
		}*/
	}

	/**
	 * ɾ��������XX����ѯ��,����ɾ���������ݣ�
	 *
	 * @param dbPool �������DbPool �����п�������
	 * @param beginDate ɾ���Ŀ�ʼʱ�䣬��������
	 * @param endDate ɾ���Ľ���ʱ�䣬��������
	 */
	public void deleteDiscardProposalQuery(DbPool dbPool, 
										   String beginDate,
										   String endDate) 
		throws Exception 
	{
		DateTime current = new DateTime(DateTime.current(), DateTime.YEAR_TO_DAY);
		String deleteSQL = " DELETE FROM CIInsureDemand WHERE ProPosalNo IS NULL AND DemandTime != date'" + current + "'";

		if (beginDate != null && !beginDate.equals("")){
			deleteSQL += " AND DemandTime >= date'" + beginDate + "'";
		}

		if (endDate != null && !endDate.equals("")){
			deleteSQL += " AND DemandTime <= date'" + endDate + "'";
		}
		
		
		String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
		if(!"1".equals(strSwitch)){
		    dbPool.delete(deleteSQL);
		}
		
	}
}
