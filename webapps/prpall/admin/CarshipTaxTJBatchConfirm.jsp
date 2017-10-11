<%@page errorPage="/UIErrorPage"%>

<html>
  <head>
    <title>天津车船税确认手工批量处理页面</title>
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
			    errorMessage(arrElement[i].description+"不能为空");
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
      <td class="formtitle" colspan="4">请输入需要车船税确认的时间段
      </td>
    </tr>
    <tr>
      <td class=title>
        起始日期：
      </td>
      <td class=input>
      	<input type="text" name="WorkStartDate" maxlength="10" class="common" description="确认起始时间" onblur="checkFullDate(this);">
        <img src="/prpall/common/images/markMustInput.jpg">
      </td>
      <td class=title>
        终止日期：
      </td>
      <td class=input>
      	<input type="text" name="WorkEndDate" maxlength="10" class="common" description="确认终止时间" onblur="checkFullDate(this);">
      	<img src="/prpall/common/images/markMustInput.jpg">
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