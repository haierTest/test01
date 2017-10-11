<%--
****************************************************************************
* DESC       ：XXXXX已核批单转XX入口
* Author     : 刘瑶
* CREATEDATE ： 2013-01-29
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
--%>

<%@page errorPage="/UIErrorPage"%>


<html>
  <head>
    <title>已核批单转XX处理页面</title>
    <link rel="stylesheet" type="text/css" href="/prpall/css/Standard.css">
    <script src="/prpall/common/pub/UICommon.js"></script>
    <script src="/prpall/common/pub/SimpleCalendar.js"></script>
    <%-- 页面样式 --%>
    <link rel="stylesheet" type="text/css" href="/prpall/css/Standard.css"> 
    <script type="text/javascript">
      function submitForm(){
        
          var oldAction = fm.action;
          var oldTarget = fm.target;
          var strURL = "/prpall/admin/PFeedBackNoCIDealBJ.jsp?endorseno="+fm.endorseno.value+"&&confirmNo=" +fm.confirmNo.value;
          var strXmlText = getResponseXmlText(strURL);
          fm.result.value=strXmlText;
      }
    </script>
  </head>
<body>
  <form name=fm action="/prpall/admin/PFeedBackNoCIDealBJ.jsp" method=post>
      <table class=common cellpadding="5" cellspacing="1">
        <tr>
          <td class="formtitle" colspan="4">XXXXX已核批单转XX</td>
        </tr>
        <tr>
          <td class="input">
              批单号：
          </td>
          <td class="input">
            <input type="text" name="endorseno">
          </td>
          <td class="input">
              XX确认码：
          </td>
          <td class="input">
            <input type="text" name="confirmNo" maxlength="50">
          </td>
        </tr>
        <tr>
          <td class="input">
          </td>
          <td class="input">
          </td>
          <td class="input"> 
          </td>
          <td class="input">
            <input type="button" class="button" name="button" value="提交" onclick="submitForm()">
          </td>
        </tr>
        <tr>
          <td class="formtitle" colspan="4">
              操作返回信息
          </td>
        </tr>
        <tr>
          <td class="input" colspan="4">
            <input type="text" name="result" class="readonly" readonly>
          </td>
          </td>
        </tr>
      </table>
    </form>
  </body>
</html>

