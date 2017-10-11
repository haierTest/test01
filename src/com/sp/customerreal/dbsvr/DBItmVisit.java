package com.sp.customerreal.dbsvr;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sp.customerreal.schema.ItmVisitSchema;
import com.sp.sysframework.common.datatype.DateTime;
import com.sp.taskmng.util.TaskMngUtil;
import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;

public class DBItmVisit {

	private final Log logger = LogFactory.getLog(getClass());

	/**
	 * 电销XX的所有
	 * 
	 * @param dbpool
	 * @return
	 * @throws Exception
	 */
	public List getItmVisitData(DbPool dbpool) throws Exception {
		
		
		String sql = "SELECT PRPCUSTOMERMESSAGE.*," 
				+ "(SELECT t.ventureflag FROM prpcmain t WHERE PRPCUSTOMERMESSAGE.policyno=t.policyno) ventureflag,"
				+ "(SELECT T.SERVICEAREA FROM PRPCMAIN T WHERE PRPCUSTOMERMESSAGE.POLICYNO = T.POLICYNO) SERVICEAREA,"
				+ "(SELECT T.HANDLERCODE FROM PRPCMAIN T WHERE PRPCUSTOMERMESSAGE.POLICYNO = T.POLICYNO) HANDLERCODE,"
				+ "(SELECT CONFIRMDATE FROM prpcjfcdlogmsg WHERE prpcjfcdlogmsg.policyno=PRPCUSTOMERMESSAGE.Policyno) paydate "
				+ "FROM PRPCUSTOMERMESSAGE "
				+ "WHERE MESSAgetype='1' "
				
				
				+ "AND CHANNELTYPE in ('N071','N075','N105') "
				
				+ "AND CREATEDDATE BETWEEN to_date(to_char(SYSDATE-1,'YYYY-MM-DD'),'YYYY-MM-DD') "
				+ "AND to_date(to_char(SYSDATE,'YYYY-MM-DD'),'YYYY-MM-DD') " 
				+ "AND SURVEYORHANDLCOMCODE1 NOT LIKE '07%' " 
				+ "UNION "
				+ "SELECT PRPCUSTOMERMESSAGE.*," 
				+ "(SELECT t.ventureflag FROM prpcmain t WHERE PRPCUSTOMERMESSAGE.policyno=t.policyno) ventureflag,"
				+ "(SELECT T.SERVICEAREA FROM PRPCMAIN T WHERE PRPCUSTOMERMESSAGE.POLICYNO = T.POLICYNO) SERVICEAREA,"
				+ "(SELECT T.HANDLERCODE FROM PRPCMAIN T WHERE PRPCUSTOMERMESSAGE.POLICYNO = T.POLICYNO) HANDLERCODE,"
				+ "(SELECT CONFIRMDATE FROM prpcjfcdlogmsg WHERE prpcjfcdlogmsg.policyno=PRPCUSTOMERMESSAGE.Policyno) paydate "
				+ "FROM PRPCUSTOMERMESSAGE "
				+ "WHERE MESSAgetype='1' "
				+ "AND CHANNELTYPE in ('N071','N075','N105') "
				+ "AND CREATEDDATE BETWEEN to_date(to_char(SYSDATE-2,'YYYY-MM-DD'),'YYYY-MM-DD') "
				+ "AND to_date(to_char(SYSDATE-1,'YYYY-MM-DD'),'YYYY-MM-DD') " 
				+ "AND SURVEYORHANDLCOMCODE1 LIKE '07%' ";
		List list = new ArrayList();
		ResultSet resultSet = dbpool.query(sql);
		ItmVisitSchema itmVisitSchema = null;
		DbPool itmdbpool = new DbPool();
		try {
			itmdbpool.open(SysConfig.CONST_ITMDATASOURCE);
			while (resultSet.next()) {
				try {
					itmVisitSchema = new ItmVisitSchema();
					
					
					if ((dbpool.getString(resultSet, "ventureflag") == null || !"DT".equals(dbpool.getString(resultSet, "ventureflag")))
							&& ((dbpool.getString(resultSet, "SERVICEAREA") == null || (!"D27551603"
									.equals(dbpool.getString(resultSet,"SERVICEAREA")) && !"D27551312"
									.equals(dbpool.getString(resultSet,"SERVICEAREA")))))) {
					itmVisitSchema.setAddress(dbpool.getString(resultSet, "Address"));
					itmVisitSchema.setAgentId(dbpool.getString(resultSet, "OPERATORCODE"));
					itmVisitSchema.setAgentName(dbpool.getString(resultSet, "OPERATORNAME"));
					
					itmVisitSchema.setBirthDate(""+dbpool.getDateTime(resultSet,"BirthDate"));
					itmVisitSchema.setBusinessNature(dbpool.getString(resultSet,"BUSINESSNATURENAME"));
					
					itmVisitSchema.setComCode(dbpool.getString(resultSet,"COMCODE"));
					itmVisitSchema.setComCodeName(dbpool.getString(resultSet,"COMNAME"));
					itmVisitSchema.setComPersonCode(dbpool.getString(resultSet,"OPERATORCODE"));
					itmVisitSchema.setComPersonName(dbpool.getString(resultSet, "OPERATORNAME"));
					itmVisitSchema.setCustomerCname(dbpool.getString(resultSet,"CUSTOMERNAME"));
					itmVisitSchema.setCustomerId(dbpool.getString(resultSet,"CUSTOMERCODE"));
					
					itmVisitSchema.setIdentifyNumber(dbpool.getString(resultSet,"IDENTIFYNUMBER"));
					itmVisitSchema.setIdentifyType(dbpool.getString(resultSet,"IDENTIFYTYPE"));
					itmVisitSchema.setJobCode(dbpool.getString(resultSet,"occupationname"));
					itmVisitSchema.setLicenseNo(dbpool.getString(resultSet,"LicenseNo"));
					itmVisitSchema.setMobile(dbpool.getString(resultSet,"Mobile"));
					itmVisitSchema.setPaidDate(""+dbpool.getDateTime(resultSet,DateTime.YEAR_TO_DAY,"paydate"));
					itmVisitSchema.setPhone(dbpool.getString(resultSet,"Phone"));
					itmVisitSchema.setPolicyNo(dbpool.getString(resultSet,"PolicyNo"));
					itmVisitSchema.setProvince(dbpool.getString(resultSet,"SURVEYORHANDLCOMNAME1"));
					itmVisitSchema.setSex(dbpool.getString(resultSet,"Sex"));
					
					itmVisitSchema.setThirdComcode(dbpool.getString(resultSet,"SURVEYORHANDLCOMNAME2"));
					itmVisitSchema.setUnderWriteEndDate(""+dbpool.getDateTime(resultSet,"UnderWriteEndDate"));
					
					itmVisitSchema = completeInfo(itmdbpool,itmVisitSchema,dbpool.getString(resultSet,"riskcode"));
					
					itmVisitSchema.setVentureFlag(dbpool.getString(resultSet,"ventureflag"));
					itmVisitSchema.setServiceArea(dbpool.getString(resultSet,"SERVICEAREA"));
					itmVisitSchema.setHandlerCode(dbpool.getString(resultSet,"HANDLERCODE"));
					
					list.add(itmVisitSchema);
					}
				} catch (Exception e) {
					logger.error("电销回访取数错误信息PolicyNo："+dbpool.getString(resultSet,"PolicyNo"), e);
					String message = "BLItmVisit->getItmVisitData()->PolicyNo："+dbpool.getString(resultSet,"PolicyNo")+"->"
							+ e.getMessage();
					TaskMngUtil.insertMidDataLog("BLItmVisit.getItmVisitData", message, e,
							TaskMngUtil.DXVISIT_JOBNAME);
				}
		    }
			resultSet.close();
		} catch (Exception e) {
			logger.error("电销回访打开电销数据源时出错", e);
			String message = "BLItmVisit->getItmVisitData()->PolicyNo：电销回访打开电销数据源时出错->"+ e.getMessage();
	        TaskMngUtil.insertMidDataLog("BLItmVisit.getItmVisitData", message, e,TaskMngUtil.DXVISIT_JOBNAME);
		}finally
		{
				if(itmdbpool!=null)
				{
					itmdbpool.close();
				}
		}
		return list;
	}
	
	/**
	 * @Title: completeInfo
	 * @Description: 从电销库查询数据
	 * @param @param dbpool
	 * @param @param itmVisitSchema
	 * @param @param riskcode
	 * @param @return
	 * @param @throws Exception    设定文件
	 * @return ItmVisitSchema    返回类型
	 * @throws
	 */
	private ItmVisitSchema completeInfo(DbPool dbpool,ItmVisitSchema itmVisitSchema,String riskcode) throws Exception {
		
		Map mapDX = getDXdate(dbpool,itmVisitSchema.getPolicyNo(),riskcode);
		itmVisitSchema.setDxCustomerId((String)mapDX.get("dxCustomerId"));
		itmVisitSchema.setYwMode((String)mapDX.get("ywMode"));
		itmVisitSchema.setSuppPostStyle((String)mapDX.get("suppPostStyle"));
		
		Map mapXG = getXGdate(dbpool,itmVisitSchema.getAgentId());
		itmVisitSchema.setTeamName((String)mapXG.get("teamname"));
		itmVisitSchema.setAreaCode((String)mapXG.get("areacode"));
		itmVisitSchema.setCenterCode((String)mapXG.get("centercode"));
		return itmVisitSchema;
	}
	
	/**
	 * 根据XX号从电销库获取电销数据，具体逻辑得问电销
	 * @param dbpool
	 * @param customerCode
	 * @return
	 * @throws Exception
	 */
	private Map getDXdate(DbPool dbpool,String policyNo,String riskcode) throws Exception
	{
		Map map = new HashMap();
		try {
			String sql = "select d.dicvalue,s.cust_id,p.supp_post_style from sales_card s,dic_content d,Post_Info p where d.dictypeid = 'BIZ_MODEL' AND d.dicid = s.biz_model AND p.sales_no = s.sales_no ";
			
			if("0509".equals(riskcode)){
				sql += " and s.contract_no1 = ?";}
			
			else if("0507".equals(riskcode)){
				sql += " and s.contract_no2 = ?";} 
			
			else if("2700".equals(riskcode)){
				sql += " and s.yjcontract_no = ?";} 
			
			else if("0301".equals(riskcode)||"0302".equals(riskcode)){
				sql += " and s.jccontract_no = ?"; }
			
			else if("3012".equals(riskcode)){
				sql += " and s.cycontract_no = ?";
			}else{
				sql += " AND 0 = ? ";
				logger.info("电销数据riskcode："+riskcode+"根据提供的条件不存在");
			}
			dbpool.prepareInnerStatement(sql);
			dbpool.setString(1, policyNo);
			ResultSet resultSet = dbpool.executePreparedQuery();
			while (resultSet.next()) {
				map.put("dxCustomerId", dbpool.getString(resultSet,"cust_id"));
				map.put("ywMode", dbpool.getString(resultSet, "dicvalue"));
				map.put("suppPostStyle", dbpool.getString(resultSet, "supp_post_style"));
			}
		} catch (Exception e) {
			logger.error("根据XX号从电销库获取电销数据错误信息policyNo:"+policyNo+",riskcode:"+riskcode, e);
			 String message = "BLItmVisit->getDXdate()->获取XX电销数据数据->"+ e.getMessage();
			 TaskMngUtil.insertMidDataLog("BLItmVisit.getDXdate", message,
					e, TaskMngUtil.DXVISIT_JOBNAME);
			throw new Exception(e);
		}
		return map;
	}
	
	/**
	 * 根据人员id从电销库获取数据,具体逻辑得问电销
	 * @param dbpool
	 * @param customerCode
	 * @return
	 * @throws Exception
	 */
	private Map getXGdate(DbPool dbpool,String agentId) throws Exception
	{
		Map map = new HashMap();
		try {
			String sql = "SELECT U.AGENTID,                                                           "
					+ "       U.TEAMID,                                                            "
					+ "       (SELECT C.TMCOMNAME                                                  "
					+ "          FROM CSR_DEPARTMENT_INFO C                                        "
					+ "         WHERE C.TMCOMCODE = U.TEAMID) AS TEAMNAME,               "
					+ "       (SELECT C.TMCOMNAME                                                  "
					+ "          FROM CSR_DEPARTMENT_INFO C                                        "
					+ "         WHERE C.TMCOMLEVEL = 'C1'                                          "
					+ "         START WITH C.TMCOMCODE = U.TEAMID                                  "
					+ "        CONNECT BY PRIOR C.UPPERTMCODE = C.TMCOMCODE                        "
					+ "               AND PRIOR C.TMCOMCODE != C.TMCOMCODE) AS AREACODE,"
					+ "       (SELECT C.TMCOMNAME                                                  "
					+ "          FROM CSR_DEPARTMENT_INFO C                                        "
					+ "         WHERE C.TMCOMLEVEL = 'C2'                                          "
					+ "         START WITH C.TMCOMCODE = U.TEAMID                                  "
					+ "        CONNECT BY PRIOR C.UPPERTMCODE = C.TMCOMCODE                        "
					+ "               AND PRIOR C.TMCOMCODE != C.TMCOMCODE) AS CENTERCODE   "
					+ "  FROM CSR_AGENT U                                                          "
					+ " WHERE U.AGENTID = ?                                                        ";
			dbpool.prepareInnerStatement(sql);
			dbpool.setString(1, agentId);
			ResultSet resultSet = dbpool.executePreparedQuery();
			while (resultSet.next())
			{
				map.put("teamname", dbpool.getString(resultSet,"TEAMNAME"));
				map.put("areacode", dbpool.getString(resultSet,"AREACODE"));
				map.put("centercode", dbpool.getString(resultSet,"CENTERCODE"));
			}
		} catch (Exception e) {
			logger.error("根据XX号从电销库获取电销数据错误信息agentId:"+agentId, e);
			String message = "BLItmVisit->getXGdate()->获取XX电销数据数据->agentId:"+agentId
					+ e.getMessage();
			TaskMngUtil.insertMidDataLog("BLItmVisit.getXGdate", message,
					e, TaskMngUtil.DXVISIT_JOBNAME);
			throw new Exception(e);
		} 
		return map;
	}
	
	/**
	 * 回访XX电销数据插入95510接口表
	 * @param dbpool
	 * @param customerPolicySchema
	 */
	public void insertItmVisitData(DbPool dbpool,ItmVisitSchema itmVisitSchema)
	{
		String sql =    "insert into APP_BIZ_DXSIGNDATA(" +
						"AGENTID," +
						"AGENTNAME," +
						"DXCUSTOMERID," +
						"PROVINCE," +
						"thirdcomcode," +
						"AREACODE," +
						"CENTERCODE," +
						"TEAMNAME," +
						"YWMODE," +
						"POLICYNO," +
						"comcode," +
						"comcodename," +
						"compersoncode," +
						"compersonname," +
						"LICENSENO," +
						"UNDERWRITEENDDATE," +
						"MOBILE," +
						"PHONE," +
						"CUSTOMERID," +
						"CUSTOMERCNAME," +
						"SEX," +
						"BIRTHDATE," +
						"IDENTIFYTYPE," +
						"IDENTIFYNUMBER," +
						"ADDRESS," +
						"BUSINESSNATURE," +
						"jobcode," +
						"paiddate," +
						"CREATEDATE, " +
						"ventureflag," +
						"SERVICEAREA," +
						"HANDLERCODE, " +
						"POST_STYLE " +
						") values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate,?,?,?,?) ";
		try{
			dbpool.prepareInnerStatement(sql);
			int j = 0;
			dbpool.setString(++j,itmVisitSchema.getAgentId());
			dbpool.setString(++j,itmVisitSchema.getAgentName());
			dbpool.setString(++j,itmVisitSchema.getDxCustomerId());
			dbpool.setString(++j,itmVisitSchema.getProvince());
			dbpool.setString(++j,itmVisitSchema.getThirdComcode());
			dbpool.setString(++j,itmVisitSchema.getAreaCode());
			dbpool.setString(++j,itmVisitSchema.getCenterCode());
			dbpool.setString(++j,itmVisitSchema.getTeamName());
			dbpool.setString(++j,itmVisitSchema.getYwMode());
			dbpool.setString(++j,itmVisitSchema.getPolicyNo());
			dbpool.setString(++j,itmVisitSchema.getComCode());
			dbpool.setString(++j,itmVisitSchema.getComCodeName());
			dbpool.setString(++j,itmVisitSchema.getComPersonCode());
			dbpool.setString(++j,itmVisitSchema.getComPersonName());
			dbpool.setString(++j,itmVisitSchema.getLicenseNo());
			dbpool.setDateTime(++j,new DateTime(itmVisitSchema.getUnderWriteEndDate()));
			dbpool.setString(++j,itmVisitSchema.getMobile());
			dbpool.setString(++j,itmVisitSchema.getPhone());
			dbpool.setString(++j,itmVisitSchema.getCustomerId());
			dbpool.setString(++j,itmVisitSchema.getCustomerCname());
			dbpool.setString(++j,itmVisitSchema.getSex());
			dbpool.setDateTime(++j,new DateTime(itmVisitSchema.getBirthDate()));
			dbpool.setString(++j,itmVisitSchema.getIdentifyType());
			dbpool.setString(++j,itmVisitSchema.getIdentifyNumber());
			dbpool.setString(++j,itmVisitSchema.getAddress());
			dbpool.setString(++j,itmVisitSchema.getBusinessNature());
			dbpool.setString(++j,itmVisitSchema.getJobCode());
			dbpool.setDateTime(++j,new DateTime(itmVisitSchema.getPaidDate()));
			dbpool.setString(++j,itmVisitSchema.getVentureFlag());
			dbpool.setString(++j,itmVisitSchema.getServiceArea());
			dbpool.setString(++j,itmVisitSchema.getHandlerCode());
			dbpool.setString(++j,itmVisitSchema.getSuppPostStyle());
			dbpool.executePreparedUpdate();
			dbpool.closePreparedStatement();
		}catch(Exception e)
		{
			logger.error(e+" XX电销回访写入95510接口表出错XX号是："+itmVisitSchema.getPolicyNo());
			String message = "BLItmVisit->insertSignPolicyData()->电销回访写入95510接口表出错的id号是："+itmVisitSchema.getPolicyNo()+e.getMessage();
			TaskMngUtil.insertMidDataLog("DBPolicyClaim.insertSignPolicyData", message,e,
					TaskMngUtil.DXVISIT_JOBNAME);

		}
	}
	
	/**
	 * 按日期，按XX电销XX的所有
	 * 
	 * @param dbpool
	 * @return
	 * @throws Exception
	 */
	public List getItmVisitData(DbPool dbpool,String strBDate,String strNDate,String policyNo ) throws Exception {
		String sql = "";
		
		if (strBDate != null && strNDate != null) 
			sql = "SELECT PRPCUSTOMERMESSAGE.*,(SELECT t.ventureflag FROM prpcmain t WHERE PRPCUSTOMERMESSAGE.policyno=t.policyno) ventureflag," +
					"(SELECT T.SERVICEAREA FROM PRPCMAIN T WHERE PRPCUSTOMERMESSAGE.POLICYNO = T.POLICYNO) SERVICEAREA," +
					"(SELECT T.HANDLERCODE FROM PRPCMAIN T WHERE PRPCUSTOMERMESSAGE.POLICYNO = T.POLICYNO) HANDLERCODE," + 
					"(SELECT CONFIRMDATE FROM prpcjfcdlogmsg WHERE prpcjfcdlogmsg.policyno=PRPCUSTOMERMESSAGE.Policyno) paydate "+
					"FROM PRPCUSTOMERMESSAGE WHERE MESSAGETYPE = '1' " +
					"AND CHANNELTYPE in ('N071','N075','N105') " +
					"AND CREATEDDATE BETWEEN TO_DATE('"+strBDate+"', 'YYYY-MM-DD') " +
							"AND TO_DATE('"+strNDate+"', 'YYYY-MM-DD') "
					+ "AND SURVEYORHANDLCOMCODE1 NOT LIKE '07%' " 
					+ "UNION "
					+ "SELECT PRPCUSTOMERMESSAGE.*," 
					+ "(SELECT t.ventureflag FROM prpcmain t WHERE PRPCUSTOMERMESSAGE.policyno=t.policyno) ventureflag,"
					+ "(SELECT T.SERVICEAREA FROM PRPCMAIN T WHERE PRPCUSTOMERMESSAGE.POLICYNO = T.POLICYNO) SERVICEAREA,"
					+ "(SELECT T.HANDLERCODE FROM PRPCMAIN T WHERE PRPCUSTOMERMESSAGE.POLICYNO = T.POLICYNO) HANDLERCODE,"
					+ "(SELECT CONFIRMDATE FROM prpcjfcdlogmsg WHERE prpcjfcdlogmsg.policyno=PRPCUSTOMERMESSAGE.Policyno) paydate "
					+ "FROM PRPCUSTOMERMESSAGE "
					+ "WHERE MESSAgetype='1' "
					+ "AND CHANNELTYPE in ('N071','N075','N105') " +
					"AND CREATEDDATE BETWEEN TO_DATE('"+strBDate+"', 'YYYY-MM-DD')-1 " +
					  "AND TO_DATE('"+strNDate+"', 'YYYY-MM-DD')-1 " 
					+ "AND SURVEYORHANDLCOMCODE1 LIKE '07%' ";
		
		if (policyNo != null) 
			sql = "SELECT *,(SELECT t.ventureflag FROM prpcmain t WHERE PRPCUSTOMERMESSAGE.policyno=t.policyno) ventureflag," +
			"(SELECT T.SERVICEAREA FROM PRPCMAIN T WHERE PRPCUSTOMERMESSAGE.POLICYNO = T.POLICYNO) SERVICEAREA, " +
			"(SELECT T.HANDLERCODE FROM PRPCMAIN T WHERE PRPCUSTOMERMESSAGE.POLICYNO = T.POLICYNO) HANDLERCODE," + 
			"(SELECT to_char(CONFIRMDATE,'YYYY-MM-DD') FROM prpcjfcdlogmsg WHERE prpcjfcdlogmsg.policyno=PRPCUSTOMERMESSAGE.Policyno) paydate "+
					" FROM PRPCUSTOMERMESSAGE WHERE policyno = '"+policyNo+"'";
		List list = new ArrayList();
		ResultSet resultSet = dbpool.query(sql);
		ItmVisitSchema itmVisitSchema = null;
		DbPool itmdbpool = new DbPool();
		itmdbpool.open(SysConfig.CONST_ITMDATASOURCE);
		while (resultSet.next()) {
			try {
				itmVisitSchema = new ItmVisitSchema();
				if ((dbpool.getString(resultSet, "ventureflag") == null || !"DT".equals(dbpool.getString(resultSet, "ventureflag")))
						&& ((dbpool.getString(resultSet, "SERVICEAREA") == null || (!"D27551603"
								.equals(dbpool.getString(resultSet,"SERVICEAREA")) && !"D27551312"
								.equals(dbpool.getString(resultSet,"SERVICEAREA")))))) {
				itmVisitSchema.setAddress(dbpool.getString(resultSet, "Address"));
				itmVisitSchema.setAgentId(dbpool.getString(resultSet, "OPERATORCODE"));
				itmVisitSchema.setAgentName(dbpool.getString(resultSet, "OPERATORNAME"));
				
				itmVisitSchema.setBirthDate(""+dbpool.getDateTime(resultSet,"BirthDate"));
				itmVisitSchema.setBusinessNature(dbpool.getString(resultSet,"BUSINESSNATURENAME"));
				
				itmVisitSchema.setComCode(dbpool.getString(resultSet,"COMCODE"));
				itmVisitSchema.setComCodeName(dbpool.getString(resultSet,"COMNAME"));
				itmVisitSchema.setComPersonCode(dbpool.getString(resultSet,"OPERATORCODE"));
				itmVisitSchema.setComPersonName(dbpool.getString(resultSet, "OPERATORNAME"));
				itmVisitSchema.setCustomerCname(dbpool.getString(resultSet,"CUSTOMERNAME"));
				itmVisitSchema.setCustomerId(dbpool.getString(resultSet,"CUSTOMERCODE"));
				
				itmVisitSchema.setIdentifyNumber(dbpool.getString(resultSet,"IDENTIFYNUMBER"));
				itmVisitSchema.setIdentifyType(dbpool.getString(resultSet,"IDENTIFYTYPE"));
				itmVisitSchema.setJobCode(dbpool.getString(resultSet,"occupationname"));
				itmVisitSchema.setLicenseNo(dbpool.getString(resultSet,"LicenseNo"));
				itmVisitSchema.setMobile(dbpool.getString(resultSet,"Mobile"));
				itmVisitSchema.setPaidDate(""+dbpool.getDateTime(resultSet,DateTime.YEAR_TO_DAY,"paydate"));
				itmVisitSchema.setPhone(dbpool.getString(resultSet,"Phone"));
				itmVisitSchema.setPolicyNo(dbpool.getString(resultSet,"PolicyNo"));
				itmVisitSchema.setProvince(dbpool.getString(resultSet,"SURVEYORHANDLCOMNAME1"));
				itmVisitSchema.setSex(dbpool.getString(resultSet,"Sex"));
				
				itmVisitSchema.setThirdComcode(dbpool.getString(resultSet,"SURVEYORHANDLCOMNAME2"));
				itmVisitSchema.setUnderWriteEndDate(""+dbpool.getDateTime(resultSet,"UnderWriteEndDate"));
				
				itmVisitSchema = completeInfo(itmdbpool,itmVisitSchema,dbpool.getString(resultSet,"riskcode"));
				
				itmVisitSchema.setVentureFlag(dbpool.getString(resultSet,"ventureflag"));
				itmVisitSchema.setServiceArea(dbpool.getString(resultSet,"SERVICEAREA"));
				itmVisitSchema.setHandlerCode(dbpool.getString(resultSet,"HANDLERCODE"));
				
				list.add(itmVisitSchema);
				}
			} catch (Exception e) {
				logger.error("电销回访取数错误信息PolicyNo："+dbpool.getString(resultSet,"PolicyNo"), e);
				String message = "BLItmVisit->getItmVisitData()->PolicyNo："+dbpool.getString(resultSet,"PolicyNo")+"->"
						+ e.getMessage();
				TaskMngUtil.insertMidDataLog("BLItmVisit.getItmVisitData", message, e,
						TaskMngUtil.DXVISIT_JOBNAME);
			}
		}
		resultSet.close();
		return list;
	}
	
	/**
	 * 判断重复
	 * @param dbpool
	 * @param policyNo
	 * @return
	 * @throws Exception
	 */
	public int getCountPolicy(DbPool dbpool,String policyNo) throws  Exception
	{
		int count = 0;
		String sql = "SELECT count(*) FROM APP_BIZ_DXSIGNDATA WHERE policyno =? ";
		dbpool.prepareInnerStatement(sql);
		dbpool.setString(1,policyNo);
		ResultSet resultSet = dbpool.executePreparedQuery();
		while(resultSet.next())
		{
			count = Integer.parseInt(dbpool.getString(resultSet,"count(*)"));
		}
		dbpool.closePreparedStatement();
		return count;
	}
}
