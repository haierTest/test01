<%--
****************************************************************************
* DESC       ：获取车队确认结果
* Author     : X
* CREATEDATE ：2003-03-04
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
--%>

<html>
<head>
  <title>获取车队确认结果</title>
  <%-- 页面样式 --%>
  <link rel="stylesheet" type="text/css" href="/prpall/css/Standard.css">
  <%-- 公共函数 --%>
  <script src="/prpall/common/pub/UICommon.js"></script>
  <script language="javascript">
  function queryNotice()
  {
    if(fm.PrecontractNo.value.length!=17)
    {
      errorMessage("请输入完整预约号!");
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
      <td class=formtitle colspan="4">获取车队确认结果</td>
    </tr>
    <tr>
      <td class=title>预约号：</td>
      <td class=input><input name="PrecontractNo" class=common maxlength=17>
        <input class="button" type="button"  name="buttonPrint" alt="确定" value="确定" onclick="queryNotice()">
      </td>
    </tr>
  </table>
</form>
</body>
</html>