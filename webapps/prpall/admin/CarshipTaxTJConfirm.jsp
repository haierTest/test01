<%@page errorPage="/UIErrorPage"%>

<html>
  <head>
    <title>f</title>
    <link rel="stylesheet" type="text/css" href="/prpall/css/Standard.css">
    <script src="/prpall/common/pub/UICommon.js"></script>
    <script language=javascript>
    function submitForm()
    {
      fm.submit();
    }
    function resetForm()
    {
      fm.reset();
    }
    </script>
  </head>
<body>
  <form name="fm" action="/prpall/admin/CarshipTaxTJConfirmDeal.jsp" method="post">
  <table class="common" cellpadding="5" cellspacing="1" >
    <tr>
      <td class="formtitle" colspan="4">请输入车船税确认的XX号
      </td>
    </tr>
    <tr>
      <td class=title>
        XX号：
      </td>
      <td class=input>
      	<input type="text" class="common"  name="PolicyNo">
      </td>
    </tr>
    <tr>
      <td colspan=2 class=button>
        <input class="button" type="button" name="buttonSubmit" alt=" 确 认 " value="确 认" onclick="submitForm()">
      </td>
      <td colspan=2 class=button>
        <input class="button" type="button" name="buttonCancel" alt=" 重 置 " value="重 置" onclick="resetForm()">
      </td>
    </tr>
  </table>
  </form>
</body>
</html>