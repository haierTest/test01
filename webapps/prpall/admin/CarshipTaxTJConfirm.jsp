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
      <td class="formtitle" colspan="4">�����복��˰ȷ�ϵ�XX��
      </td>
    </tr>
    <tr>
      <td class=title>
        XX�ţ�
      </td>
      <td class=input>
      	<input type="text" class="common"  name="PolicyNo">
      </td>
    </tr>
    <tr>
      <td colspan=2 class=button>
        <input class="button" type="button" name="buttonSubmit" alt=" ȷ �� " value="ȷ ��" onclick="submitForm()">
      </td>
      <td colspan=2 class=button>
        <input class="button" type="button" name="buttonCancel" alt=" �� �� " value="�� ��" onclick="resetForm()">
      </td>
    </tr>
  </table>
  </form>
</body>
</html>