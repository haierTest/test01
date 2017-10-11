<%--
****************************************************************************
* DESC       ：车队确认请求录入
* Author     : X
* CREATEDATE ：2004-03-08
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
--%>

<html>
<head>
  <title>车队确认请求录入</title>
  <%-- 页面样式 --%>
  <link rel="stylesheet" type="text/css" href="/prpall/css/Standard.css">
  <%-- 公共函数 --%>
  <script src="/prpall/common/pub/UICommon.js"></script>
  <script language="javascript">
  function submitForm()
  {
    if(fm.PrecontractNo.value.length!=17)
    {
      errorMessage("请输入完整预约号!");
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
      <td class=formtitle colspan="4">车队确认请求录入</td>
    </tr>
    <tr>
      <td class=title>预约号：</td>
      <td class=input><input name="PrecontractNo" class=common maxlength=17>
        <input class="button" type="button"  name="buttonSubmit" alt="确定" value="确定" onclick="submitForm()">
      </td>
    </tr>
  </table>
</form>
</body>
</html>