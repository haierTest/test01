
<%@page import="org.dom4j.*"%>
<%@page import="java.util.*"%>
<%@page import="com.sp.prpall.pubfun.prpallSSDB"%>
<%@page import="org.nutz.ssdb4j.spi.Response"%>
<%@page import="com.sp.interactive.interf.QuotationObjectiveInfoEncoder"%>

<%
String strResultSSDB="";
Document baowen=null;
String mapValue = request.getParameter("mapValue").trim();
String keyValue = request.getParameter("keyValue").trim();
String value=request.getParameter("value").trim();
if(mapValue!=null&&!(mapValue.trim().equals(""))&&keyValue!=null&&!(keyValue.trim().equals(""))&&value!=null&&!(value.trim().equals(""))){
	Response  ruleInfo=prpallSSDB.hset(mapValue, keyValue,value);
	strResultSSDB=ruleInfo.asString();
	System.out.println(strResultSSDB);
	strResultSSDB=strResultSSDB.replace("<", "&lt;");
	strResultSSDB=strResultSSDB.replace(">", "&gt;");
	 response.sendRedirect("/prpall/admin/QuerySsdb.jsp?mapValue="+mapValue+"&&keyValue="+keyValue);  
	  return;

}
%>
<html>
<head>
</head>
<body>

</body>
</html>

