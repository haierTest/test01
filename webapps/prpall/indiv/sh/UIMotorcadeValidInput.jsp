<%--
****************************************************************************
* DESC       ������ȷ������¼��
* Author     : X
* CREATEDATE ��2004-03-08
* MODIFYLIST ��   Name       Date            Reason/Contents
****************************************************************************
--%>

<html>
<head>
  <title>����ȷ������¼��</title>
  <%-- ҳ����ʽ --%>
  <link rel="stylesheet" type="text/css" href="/prpall/css/Standard.css">
  <%-- �������� --%>
  <script src="/prpall/common/pub/UICommon.js"></script>
  <script language="javascript">
  function submitForm()
  {
    if(fm.PrecontractNo.value.length!=17)
    {
      errorMessage("����������ԤԼ��!");
      fm.PrecontractNo.focus();
      return;
    }
    var strURL = "/prpall/indiv/sh/UIMotorcadeDemandFetch.jsp"
               + "?PrecontractNo="+fm.PrecontractNo.value
               + "&ValidOnly=Y";
    window.location = strURL;
  }
  </script>
</head>
<body class="interface" background="/prpall/common/images/bgCommon.gif">
<form name="fm">
  <table class="common" align=center style="width:80%">
    <tr>
      <td class=formtitle colspan="4">����ȷ������¼��</td>
    </tr>
    <tr>
      <td class=title>ԤԼ�ţ�</td>
      <td class=input><input name="PrecontractNo" class=common maxlength=17>
        <input class="button" type="button"  name="buttonSubmit" alt="ȷ��" value="ȷ��" onclick="submitForm()">
      </td>
    </tr>
  </table>
</form>
</body>
</html>