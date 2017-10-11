<%@page errorPage="/UIErrorPage"%>
<%@page contentType="text/html;charset=GBK"%>
<html>
  <head>
    <title>f</title>
    <%-- 公用函数 --%>
    <script src="/prpall/common/pub/UICommon.js"></script>
    <%-- 页面样式 --%>
    <link rel="stylesheet" type="text/css" href="/prpall/css/Standard.css"> 
    <script type="text/javascript">
      function submitForm(){
          var oldAction = fm.action;
          var oldTarget = fm.target;
          var strURL = "/prpall/admin/DeleteRenewalRelevanceDeal.jsp?policyno="+fm.policyno.value+"&deleteFlag="+fm.deleteFlag.value;
          var strXmlText = getResponseXmlText(strURL);
          alert(strXmlText);
      }
    </script>
  </head>
  <body>
    <form name="fm">
      <table class=common cellpadding="5" cellspacing="1">
        <tr>
          <td class="formtitle" colspan="4">d</td>
        </tr>
        <tr>
          <td class="input">
              请输入XX号：
          </td>
          <td class="input">
            <input type="text" name="policyno">
          </td>
          <td class="input">删除方式：
          	<input type="text" name="deleteFlag">
          </td>
          <td class="input">
            <input type="button" class="button" name="button" value="删除" onclick="submitForm()">
          </td>
        </tr>
      </table>
     <br><br><br> 
     &nbsp;&nbsp;&nbsp;&nbsp;删除方式注释：输入1，删除续XXXXX信息表中数据；输入2，更改基本信息表中的续XXXXX标识：3，更改续XXXXX检查表中的续XXXXX标识。
    </form>
  </body>
</html>