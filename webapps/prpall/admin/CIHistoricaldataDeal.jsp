<%@page errorPage="/UIErrorPage"%>
<%@page import="com.sp.indiv.historicaldata.interf.*"%>
<%@page im[ort="java.util.Date"%>
<%@page import="org.apache.commons.logging.Log"%>
<%@page import="org.apache.commons.logging.LogFactory"%>

<%
  Log logger = LogFactory.getLog("RunTime");
  String area = request.getParameter("area");
  String deadline = request.getParameter("deadline");
  String deadlineMode = request.getParameter("deadlineMode");
  
  
  logger.info("the area is "+area);
  logger.info("the deadline is "+deadline);
  logger.info("the deadlineMode is "+deadlineMode);
  

      
  int intarea = new Integer(area).intValue();
  int intdeadlineMode = new Integer(deadlineMode).intValue();
  DBICgetHistoricaldata Historicaldata  = new DBICgetHistoricaldata();
  Historicaldata.getHistoricalData(intarea,deadline,intdeadlineMode);
  
  
%>

