<%--
****************************************************************************
* DESC       ����ȡ����ȷ�Ͻ��
* Author     : X
* CREATEDATE ��2003-03-04
* MODIFYLIST ��   Name       Date            Reason/Contents
****************************************************************************
--%>

<html>
<head>
  <title>��ȡ����ȷ�Ͻ��</title>
  <%-- ҳ����ʽ --%>
  <link rel="stylesheet" type="text/css" href="/prpall/css/Standard.css">
  <%-- �������� --%>
  <script src="/prpall/common/pub/UICommon.js"></script>
  <script language="javascript">
  function queryNotice()
  {
    if(fm.PrecontractNo.value.length!=17)
    {
      errorMessage("����������ԤԼ��!");
      fm.PrecontractNo.focus();
      return;
    }
    var strURL = "/prpall/indiv/sh/UIMotorcadeValidFetch.jsp?PrecontractNo="+fm.PrecontractNo.value;
    window.location = strURL;
  }
  </script>
</head>
<body class="interface" background="/prpall/common/images/bgCommon.gif" method=post>
<form name="fm">
  <table class="common" align=center style="width:80%">
    <tr>
      <td class=formtitle colspan="4">��ȡ����ȷ�Ͻ��</td>
    </tr>
    <tr>
      <td class=title>ԤԼ�ţ�</td>
      <td class=input><input name="PrecontractNo" class=common maxlength=17>
        <input class="button" type="button"  name="buttonPrint" alt="ȷ��" value="ȷ��" onclick="queryNotice()">
      </td>
    </tr>
  </table>
</form>
</body>
</html>