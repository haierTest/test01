<%--
****************************************************************************
* DESC       ：车队告知单打印
* Author     : X
* CREATEDATE ：2004-02-27
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
--%>
<%-- 错误处理 --%>
<%@page errorPage="/UIErrorPage"%>

<%-- 引用javabean --%>
<%@page import="com.sp.utility.error.*"%>
<%
  
  int i = 0;
  String[] arrDemandNo = request.getParameterValues("CheckedDemandNo");
  if(arrDemandNo==null || arrDemandNo.length==0)
  {
    throw new UserException(-98,-2,"UIMotorcadeDemandPrint",
      "没有选择查询码不能打印，请先选中需要打印的查询码!");
  }
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
<%
  
  for(i=0; i<arrDemandNo.length; i++)
  {
    
    if(i>0){ out.println("<br style=\"page-break-before:always\" clear=all>"); }
%>
    <%-- 告知单打印实体 --%>
    <jsp:include page="/indiv/sh/UIDemandPrintBody.jsp">
      <jsp:param name="PrintDemandNo" value="<%=arrDemandNo[i]%>"/>
    </jsp:include>
<%
  }
%>  
  <%-- include打印按钮 --%>
  <jsp:include page="/common/pub/UIPrintButton.jsp" />
</body>
</html>
