<%--
****************************************************************************
* DESC       ：告知单打印入口
* Author     : X
* CREATEDATE ：2003-02-10
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
--%>

<html>
<head>
  <title>告知单打印入口</title>
  <%-- 页面样式 --%>
  <link rel="stylesheet" type="text/css" href="/prpall/css/Standard.css">
  <%-- 公共函数 --%>
  <script src="/prpall/common/pub/UICommon.js"></script>
  <script language="javascript">
  function printNotice()
  {
    if(fm.DemandNo.value.length!=17)
    {
      errorMessage("请输入完整XX查询码!");
      fm.DemandNo.focus();
      return;
    }
    var strURL = "/prpall/indiv/sh/UIDemandPrint.jsp?DemandNo="+fm.DemandNo.value;
    printWindow(strURL,"告知单打印");
  }
  </script>
</head>
<body class="interface" background="/prpall/common/images/bgCommon.gif" method=post>
<form name="fm">
  <table class="common" align=center style="width:80%">
    <tr>
      <td class=formtitle colspan="4">告知单打印</td>
    </tr>
    <tr>
      <td class=title>查询码：</td>
      <td class=input><input name="DemandNo" class=common maxlength=17>
        <input class="button" type="button"  name="buttonPrint" alt="打印" value="打印..." onclick="printNotice()">
      </td>
    </tr>
  </table>
</form>
</body>
</html>