
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
if(mapValue!=null&&!(mapValue.trim().equals(""))&&keyValue!=null&&!(keyValue.trim().equals(""))){
	prpallSSDB.deleteSSDB(mapValue, keyValue);
	response.sendRedirect("/prpall/admin/QuerySsdb.jsp?mapValue="+mapValue+"&&keyValue="+keyValue");
	  return;

}
%>
<html>
<head>
<script language="javascript">

</script>
</head>
<body>

</body>
</html>

