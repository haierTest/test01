<%@page errorPage="/UIErrorPage"%>
<%@page import="com.sp.utility.*"%>
<%@page import="com.sp.prpall.blsvr.cb.*"%>
<%@page import="com.sp.prpall.dbsvr.pg.*"%>
<%@page import="com.sp.indiv.carshiptaxtj.interf.*"%>
<%@page import="com.sp.indiv.carshiptaxtj.dbsvr.DBCICarShipTaxTransaction"%>
<%@page import="com.sp.utility.database.DbPool" %>
<%@page import="java.sql.ResultSet" %>
<%@page import="org.apache.commons.logging.Log"%>
<%@page import="org.apache.commons.logging.LogFactory"%>

<%
	Log logger = LogFactory.getLog("RunTime");
	UtiPower utiPower = new UtiPower();
	DBPrpPhead dbPrpPhead = new DBPrpPhead();
	DBCICarShipTaxTransaction dbCICarShipTaxTransaction = new DBCICarShipTaxTransaction();
  CarshipTaxTJRequest carshipTaxTJRequest = new CarshipTaxTJRequest();
	String strWorkStartDate = request.getParameter("WorkStartDate");
	String strWorkEndDate = request.getParameter("WorkEndDate");
	String strMessage = "";
  String strWhere = "";
  String strPolicyNo = "";
  int result1 = 0;
  int result2 = 0;
  int result3 = 0;
  int result4 = 0;
  int result5 = 0;
  
  
  String strSql = "select distinct(policyno) from cicarshiptaxtransaction where workdate between to_date('" + strWorkStartDate + "','yyyy-MM-dd') and to_date('" + strWorkEndDate + "','yyyy-MM-dd') and policyno is not null and policyno not in (select policyno from cicarshiptaxtransaction where substr(Flag,1,1) = '2')";
  DbPool dbpoolNew = new DbPool();
  ResultSet resultSet = null;
  String strResult = "";
  try {
	  if ("1".equals(SysConfig.getProperty("DB_SPLIT_SWITCH"))) {
		  dbpoolNew.open("platformNewDataSource");
	  } else {
		  dbpoolNew.open(SysConfig.CONST_DDCCDATASOURCE);
	  }
	  resultSet = dbpoolNew.query(strSql);
	  while(resultSet.next()) {
		  strResult += "'" + resultSet.getString("policyno") + "',";
	  }
  } catch (Exception e) {
	  e.printStackTrace();
	  throw e;
  } finally {
	  dbpoolNew.close();
  }
  strWhere = "policyno in ("
           + strResult + "'') "
           + "and printno is not null and substr(othflag,4,1) <> '1'";
  
           
  BLPrpCmain blPrpCmain = new BLPrpCmain();
  blPrpCmain.query(strWhere);
  
  logger.info("查询sql：" + strWhere);
  logger.info("共查出 " + blPrpCmain.getSize() + " 笔XX");
  

  for(int i=0;i<blPrpCmain.getSize();i++)
  {
  	strPolicyNo = blPrpCmain.getArr(i).getPolicyNo();
  	BLPolicy blPolicy = new BLPolicy();
  	blPolicy.getData(strPolicyNo);
  	
  	strWhere = "PolicyNo = '" + strPolicyNo + "' "
							   + " AND (UnderWriteFlag IS NULL OR (UnderWriteFlag != '1' AND UnderWriteFlag != '3'))";
		result1 = dbPrpPhead.getCount(strWhere);
							   
  	strWhere = "PolicyNo = '" + strPolicyNo + "' AND substr(Flag,1,1) = '2'";
		result2 = dbCICarShipTaxTransaction.getCount(strWhere);
		
		if(result1 == 0 && result2 == 0)
		{
			try
    	{
				
				carshipTaxTJRequest.confirmRequest(blPolicy,strPolicyNo);
				result3++;
			}
			catch(Exception e)
			{
				e.printStackTrace();
				result4++;
			}
  	}
  	else
  	{
  		result5++;
  	}
  	
  }

  strMessage = "从" + strWorkStartDate + "到" + strWorkEndDate + "，共需要确认" + blPrpCmain.getSize() 
  					 + "笔XX，确认成功：" + result3 + "笔，" 
  					 + "确认失败：" + result4 + "笔，"
  					 + "不符合确认条件：" + result5 + "笔";
%>  
	<jsp:forward page="/common/pub/UIMessagePage.jsp">
	  <jsp:param name="Picture" value="C" />
	  <jsp:param name="Content" value="<%=strMessage%>" />
	</jsp:forward>
<%	

%>