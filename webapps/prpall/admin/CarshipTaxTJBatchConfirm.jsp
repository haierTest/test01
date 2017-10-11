<%@page errorPage="/UIErrorPage"%>

<html>
  <head>
    <title>��򳵴�˰ȷ���ֹ���������ҳ��</title>
    <link rel="stylesheet" type="text/css" href="/prpall/css/Standard.css">
    <script src="/prpall/common/pub/UICommon.js"></script>
    <script language=javascript>
    function submitForm()
    {
    	var arrElement = new Array();
		  var i=0;
		  
		  arrElement[i++]=fm.WorkStartDate;
		  arrElement[i++]=fm.WorkEndDate;
		  for(i=0;i<arrElement.length;i++)
			{
				if(isEmpty(arrElement[i])==true)
			  {
			    errorMessage(arrElement[i].description+"����Ϊ��");
			    arrElement[i].focus();
			    arrElement[i].select();
			    return false;
			  }
			}
		  
      fm.submit();
    }
    function resetForm()
    {
      fm.reset();
    }
    </script>
  </head>
<body>
  <form name="fm" action="/prpall/admin/CarshipTaxTJBatchConfirmDeal.jsp" method="post">
  <table class="common" cellpadding="5" cellspacing="1" >
    <tr>
      <td class="formtitle" colspan="4">��������Ҫ����˰ȷ�ϵ�ʱ���
      </td>
    </tr>
    <tr>
      <td class=title>
        ��ʼ���ڣ�
      </td>
      <td class=input>
      	<input type="text" name="WorkStartDate" maxlength="10" class="common" description="ȷ����ʼʱ��" onblur="checkFullDate(this);">
        <img src="/prpall/common/images/markMustInput.jpg">
      </td>
      <td class=title>
        ��ֹ���ڣ�
      </td>
      <td class=input>
      	<input type="text" name="WorkEndDate" maxlength="10" class="common" description="ȷ����ֹʱ��" onblur="checkFullDate(this);">
      	<img src="/prpall/common/images/markMustInput.jpg">
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