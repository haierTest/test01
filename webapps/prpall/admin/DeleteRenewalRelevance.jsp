<%@page errorPage="/UIErrorPage"%>
<%@page contentType="text/html;charset=GBK"%>
<html>
  <head>
    <title>f</title>
    <%-- ���ú��� --%>
    <script src="/prpall/common/pub/UICommon.js"></script>
    <%-- ҳ����ʽ --%>
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
              ������XX�ţ�
          </td>
          <td class="input">
            <input type="text" name="policyno">
          </td>
          <td class="input">ɾ����ʽ��
          	<input type="text" name="deleteFlag">
          </td>
          <td class="input">
            <input type="button" class="button" name="button" value="ɾ��" onclick="submitForm()">
          </td>
        </tr>
      </table>
     <br><br><br> 
     &nbsp;&nbsp;&nbsp;&nbsp;ɾ����ʽע�ͣ�����1��ɾ����XXXXX��Ϣ�������ݣ�����2�����Ļ�����Ϣ���е���XXXXX��ʶ��3��������XXXXX�����е���XXXXX��ʶ��
    </form>
  </body>
</html>