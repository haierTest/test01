<%--
****************************************************************************
* DESC       ：告知单打印
* Author     : X
* CREATEDATE ：2004-02-27
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
--%>
<%-- 错误处理 --%>
<%@page errorPage="/UIErrorPage"%>
<%
  
  String strPrintDemandNo = request.getParameter("DemandNo");
%>
<html>
<head>
  <title>告知单打印</title>
  <style>
    body { font-family:宋体;
           font-size:10pt }
    td   { font-family:宋体;
           font-size:10pt }
    td.little { font-family:宋体;
                font-size:9pt }
  </style>
</head>
<body>
  <%-- 告知单打印实体 --%>
  <jsp:include page="/indiv/sh/UIDemandPrintBody.jsp">
    <jsp:param name="PrintDemandNo" value="<%=strPrintDemandNo%>"/>
  </jsp:include>
  <%-- include打印按钮 --%>
  <jsp:include page="/common/pub/UIPrintButton.jsp" />
  
</body>
</html>
