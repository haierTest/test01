<%--
****************************************************************************
* DESC       ����֪����ӡ
* Author     : X
* CREATEDATE ��2004-02-27
* MODIFYLIST ��   Name       Date            Reason/Contents
****************************************************************************
--%>
<%-- ������ --%>
<%@page errorPage="/UIErrorPage"%>
<%
  
  String strPrintDemandNo = request.getParameter("DemandNo");
%>
<html>
<head>
  <title>��֪����ӡ</title>
  <style>
    body { font-family:����;
           font-size:10pt }
    td   { font-family:����;
           font-size:10pt }
    td.little { font-family:����;
                font-size:9pt }
  </style>
</head>
<body>
  <%-- ��֪����ӡʵ�� --%>
  <jsp:include page="/indiv/sh/UIDemandPrintBody.jsp">
    <jsp:param name="PrintDemandNo" value="<%=strPrintDemandNo%>"/>
  </jsp:include>
  <%-- include��ӡ��ť --%>
  <jsp:include page="/common/pub/UIPrintButton.jsp" />
  
</body>
</html>
