<%--
****************************************************************************
* DESC       ����֪����ӡ���
* Author     : X
* CREATEDATE ��2003-02-10
* MODIFYLIST ��   Name       Date            Reason/Contents
****************************************************************************
--%>

<html>
<head>
  <title>��֪����ӡ���</title>
  <%-- ҳ����ʽ --%>
  <link rel="stylesheet" type="text/css" href="/prpall/css/Standard.css">
  <%-- �������� --%>
  <script src="/prpall/common/pub/UICommon.js"></script>
  <script language="javascript">
  function printNotice()
  {
    if(fm.DemandNo.value.length!=17)
    {
      errorMessage("����������XX��ѯ��!");
      fm.DemandNo.focus();
      return;
    }
    var strURL = "/prpall/indiv/sh/UIDemandPrint.jsp?DemandNo="+fm.DemandNo.value;
    printWindow(strURL,"��֪����ӡ");
  }
  </script>
</head>
<body class="interface" background="/prpall/common/images/bgCommon.gif" method=post>
<form name="fm">
  <table class="common" align=center style="width:80%">
    <tr>
      <td class=formtitle colspan="4">��֪����ӡ</td>
    </tr>
    <tr>
      <td class=title>��ѯ�룺</td>
      <td class=input><input name="DemandNo" class=common maxlength=17>
        <input class="button" type="button"  name="buttonPrint" alt="��ӡ" value="��ӡ..." onclick="printNotice()">
      </td>
    </tr>
  </table>
</form>
</body>
</html>