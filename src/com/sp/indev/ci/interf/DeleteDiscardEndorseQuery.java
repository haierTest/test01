package com.sp.indiv.ci.interf;

import com.sp.sysframework.common.datatype.DateTime;
import com.sp.sysframework.reference.AppConfig;
import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;

/**
 * 删除废弃的批单查询码
 *
 * @author 程凯
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
	 * 删除废弃的批单查询码,但不删除当天内容！
	 *
	 * @param dbPool 如果不传DbPool 则自行控制事物
	 * @param beginDate 删除的开始时间，包含此天
	 * @param endDate 删除的结束时间，包含此天
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
