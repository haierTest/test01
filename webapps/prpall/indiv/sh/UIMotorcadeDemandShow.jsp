<%--
****************************************************************************
* DESC       ��ԤԼ���Ӳ�ѯ��������ʾҳ��
* Author     : X
* CREATEDATE ��2003-03-04
* MODIFYLIST ��   Name       Date            Reason/Contents
****************************************************************************
--%>
<%
  
  String strPrecontractNo = request.getParameter("PrecontractNo");
%>

<html>
  <head>
    <%-- ҳ����ʽ  --%>
	  <link rel="stylesheet" type="text/css" href="/prpall/css/Standard.css">
  </head>
  <body>
    <table class=common border=0>
      <tr>
        <td>���ĳ��ӵ�ԤԼ����:<%=strPrecontractNo%>,�������ύͬҵƽ̨У��,��������ȷ������.</td>
      </tr>
    </table>
  </body>
</html>