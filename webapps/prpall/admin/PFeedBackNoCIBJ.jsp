<%--
****************************************************************************
* DESC       ��XXXXX�Ѻ�����תXX���
* Author     : ����
* CREATEDATE �� 2013-01-29
* MODIFYLIST ��   Name       Date            Reason/Contents
****************************************************************************
--%>

<%@page errorPage="/UIErrorPage"%>


<html>
  <head>
    <title>�Ѻ�����תXX����ҳ��</title>
    <link rel="stylesheet" type="text/css" href="/prpall/css/Standard.css">
    <script src="/prpall/common/pub/UICommon.js"></script>
    <script src="/prpall/common/pub/SimpleCalendar.js"></script>
    <%-- ҳ����ʽ --%>
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
          <td class="formtitle" colspan="4">XXXXX�Ѻ�����תXX</td>
        </tr>
        <tr>
          <td class="input">
              �����ţ�
          </td>
          <td class="input">
            <input type="text" name="endorseno">
          </td>
          <td class="input">
              XXȷ���룺
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
            <input type="button" class="button" name="button" value="�ύ" onclick="submitForm()">
          </td>
        </tr>
        <tr>
          <td class="formtitle" colspan="4">
              ����������Ϣ
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

