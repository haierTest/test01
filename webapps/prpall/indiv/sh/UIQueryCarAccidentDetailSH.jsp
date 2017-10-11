<%--
****************************************************************************
* DESC       : 上海风XXXXX信息出XXXXX经过显示页面
* Author     : liubin
* CREATEDATE : 2013-05-07
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
--%>

<%@page errorPage="/UIErrorPage"%>
<%
  String strAccidentDesc = (String)request.getParameter("accidentDesc");
  String strTitle = (String)request.getParameter("title");
%>
<html>
<head>
  <title><%=strTitle %></title>
  <%-- 公用函数 --%>
  <script src="/prpall/common/pub/UICommon.js"></script>
  <%-- 页面样式  --%>
  <link rel="stylesheet" type="text/css" href="/prpall/css/Standard.css">
</head>
<%-- 调用loadForm 初始化页面 --%>
<body class="interface" background="/prpall/common/images/bgCommon.gif">
  <form name="fm" method="POST">
    <table class=common cellpadding="5" cellspacing="1">
    <tr class=listtitle>
      <td >出XXXXX经过</td>
    </tr>
    <tr>
      <td ><%=strAccidentDesc%></td>
    </tr>
    <tr>
      <td align="center"><input value="关 闭"  type="button" class="button" alt="关闭" src='/prpall/commonship/images/butClose.gif' onclick="javascript:window.close();"></td>
    </tr>
  </table>
  </form>
</body>
</html>