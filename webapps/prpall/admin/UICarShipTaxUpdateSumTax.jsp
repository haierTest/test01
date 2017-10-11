<%@page errorPage="/UIErrorPage"%>
<%@page contentType="text/html;charset=GBK"%>
<%@page import="com.sp.utility.database.DbPool"%>
<%@page import="com.sp.utility.*"%>

<html>
  <head>
    <title>f</title>
    <%-- 公用函数 --%>
    <script src="/prpall/common/pub/UICommon.js"></script>
    <script src="/prpall/common/pub/SimpleCalendar.js"></script>
    <%-- 页面样式 --%>
    <link rel="stylesheet" type="text/css" href="/prpall/css/Standard.css"> 
    <script type="text/javascript">
      function submitForm(){
        
          var oldAction = fm.action;
          var oldTarget = fm.target;
          var strURL = "/prpall/admin/UICarShipTaxUpdateSumTaxDeal.jsp?proposalno="+fm.proposalno.value;
          var strXmlText = getResponseXmlText(strURL);
          fm.result.value=strXmlText;
      }
    </script>
  </head>
  <body>
    <form name="fm">
      <table class=common cellpadding="5" cellspacing="1">
        <tr>
          <td class="formtitle" colspan="4">更新XXXXX车船税合计</td>
        </tr>
        <tr>
          <td class="input">
              请输入XX单号：
          </td>
          <td class="input">
            <input type="text" name="proposalno">
          </td>
          <td class="input">
          </td>
          <td class="input">
            <input type="button" class="button" name="button" value="更新" onclick="submitForm()">
          </td>
        </tr>
        <tr>
          <td class="formtitle" colspan="4">
              更新结果
          </td>
        </tr>
        <tr>
          <td class="input" colspan="4">
            <input type="text" name="result" class="readonly" readonly>
          </td>
        </tr>
      </table>
    </form>
  </body>
</html>