package com.sp.indiv.ci.interf;

import com.sp.sysframework.common.datatype.DateTime;
import com.sp.sysframework.reference.AppConfig;
import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;

/**
 * ɾ��������������ѯ��
 *
 * @author �̿�
 */
public class DeleteDiscardEndorseQuery {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) 
		throws Exception 
	{
	/*	String strUrl = "";
		String strUser = "";
		String strPwd = "";
		DbPool dbpool = new DbPool();
		try { 
			strUrl = "jdbc:oracle:thin:@192.168.60.117:1521:devdb";
			strUser = "sunnytest";
			strPwd = "sunnytest";

			dbpool.openOra(strUrl, strUser, strPwd);

			DeleteDiscardEndorseQuery deleteDiscardProposalQuery = new DeleteDiscardEndorseQuery();
			deleteDiscardProposalQuery.deleteDiscardProposalQuery(dbpool, "2006-06-29", "");
		} catch (Exception ex) {
			throw ex;
		} finally {
			dbpool.close();
		}*/
	}

	/**
	 * ɾ��������������ѯ��,����ɾ���������ݣ�
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

		String strSQL = " DELETE FROM CIEndorValid WHERE EndorseNo IS NULL AND ValidTime != date'" + current + "'";
		if (beginDate != null && !beginDate.equals(""))
		{
			strSQL += " AND ValidTime >= date'" + beginDate + "'";
		}

		if (endDate != null && !endDate.equals("")){
			strSQL += " AND ValidTime <= date'" + endDate + "'";
		}
		
		dbPool.delete(strSQL);
	}
}
