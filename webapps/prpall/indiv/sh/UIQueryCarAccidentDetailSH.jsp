<%--
****************************************************************************
* DESC       : �Ϻ���XXXXX��Ϣ��XXXXX������ʾҳ��
* Author     : liubin
* CREATEDATE : 2013-05-07
* MODIFYLIST ��   Name       Date            Reason/Contents
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
  <%-- ���ú��� --%>
  <script src="/prpall/common/pub/UICommon.js"></script>
  <%-- ҳ����ʽ  --%>
  <link rel="stylesheet" type="text/css" href="/prpall/css/Standard.css">
</head>
<%-- ����loadForm ��ʼ��ҳ�� --%>
<body class="interface" background="/prpall/common/images/bgCommon.gif">
  <form name="fm" method="POST">
    <table class=common cellpadding="5" cellspacing="1">
    <tr class=listtitle>
      <td >��XXXXX����</td>
    </tr>
    <tr>
      <td ><%=strAccidentDesc%></td>
    </tr>
    <tr>
      <td align="center"><input value="�� ��"  type="button" class="button" alt="�ر�" src='/prpall/commonship/images/butClose.gif' onclick="javascript:window.close();"></td>
    </tr>
  </table>
  </form>
</body>
</html>