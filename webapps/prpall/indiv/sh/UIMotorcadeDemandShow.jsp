<%--
****************************************************************************
* DESC       ：预约车队查询请求结果显示页面
* Author     : X
* CREATEDATE ：2003-03-04
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
--%>
<%
  
  String strPrecontractNo = request.getParameter("PrecontractNo");
%>

<html>
  <head>
    <%-- 页面样式  --%>
	  <link rel="stylesheet" type="text/css" href="/prpall/css/Standard.css">
  </head>
  <body>
    <table class=common border=0>
      <tr>
        <td>您的车队的预约号是:<%=strPrecontractNo%>,现在已提交同业平台校验,请明天来确认数据.</td>
      </tr>
    </table>
  </body>
</html>