<%--
****************************************************************************
* DESC       �����Ӹ�֪����ӡ
* Author     : X
* CREATEDATE ��2004-02-27
* MODIFYLIST ��   Name       Date            Reason/Contents
****************************************************************************
--%>
<%-- ������ --%>
<%@page errorPage="/UIErrorPage"%>

<%-- ����javabean --%>
<%@page import="com.sp.utility.error.*"%>
<%
  
  int i = 0;
  String[] arrDemandNo = request.getParameterValues("CheckedDemandNo");
  if(arrDemandNo==null || arrDemandNo.length==0)
  {
    throw new UserException(-98,-2,"UIMotorcadeDemandPrint",
      "û��ѡ���ѯ�벻�ܴ�ӡ������ѡ����Ҫ��ӡ�Ĳ�ѯ��!");
  }
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
<%
  
  for(i=0; i<arrDemandNo.length; i++)
  {
    
    if(i>0){ out.println("<br style=\"page-break-before:always\" clear=all>"); }
%>
    <%-- ��֪����ӡʵ�� --%>
    <jsp:include page="/indiv/sh/UIDemandPrintBody.jsp">
      <jsp:param name="PrintDemandNo" value="<%=arrDemandNo[i]%>"/>
    </jsp:include>
<%
  }
%>  
  <%-- include��ӡ��ť --%>
  <jsp:include page="/common/pub/UIPrintButton.jsp" />
</body>
</html>
