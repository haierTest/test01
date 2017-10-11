<%@page errorPage="/UIErrorPage"%>
<%@page import="com.sp.indiv.historicaldata.interf.*"%>

<html>
  <head>
    <title>历史数据处理页面</title>
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
    function loadForm()
    {
    }
    </script>
  </head>
<body onload="loadForm()">
  <form name=fm action="/prpall/admin/CIHistoricaldataDeal.jsp" method=post>
  <table class=common cellpadding="5" cellspacing="1" >
    <tr>
      <td class=formtitle colspan="4">按条件处理
      </td>
    </tr>

       <tr>
      <td class=title>
        处理机构类型：</td>
      <td class=input>
        <select  name="area">
         <option value="1">浙江</option>
         <option value="2">大连</option>
         <option value="3">宁波</option>
         <option value="4">辽宁</option>
        </select></td>

      <td class=title>
       截止时间(YYYYMMDD)
      </td>
       <td class=input>
      <input  type="text" class="input"  name="deadline">
      </td>
    </tr>
    
     <tr>
      <td class=title>
        截止时间类型：</td>
      <td class=input>
        <select  name="deadlineMode">
         <option value="1">起XXXXX日期</option>
         <option value="2">录单日期</option>
         <option value="3">签单日期</option>
        </select></td>

      <td class=title>
      </td>
      <td class=input>
      </td>
    </tr>
    

    <tr>
      <td colspan=2 class=button>
        <input class="button" type="button" name="buttonSubmit" alt=" 查 询 " value="查 询" onclick="submitForm()">
      </td>
      <td colspan=2 class=button>
        <input class="button" type="button" name="buttonCancel" alt=" 重 置 " value="重 置" onclick="resetForm()">
      </td>
    </tr>
  </table>
  </form>
</body>
</html>

