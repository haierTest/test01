<%@page errorPage="/UIErrorPage"%>
<%@page import="com.sp.indiv.historicaldata.interf.*"%>

<html>
  <head>
    <title>��ʷ���ݴ���ҳ��</title>
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
      <td class=formtitle colspan="4">����������
      </td>
    </tr>

       <tr>
      <td class=title>
        ����������ͣ�</td>
      <td class=input>
        <select  name="area">
         <option value="1">�㽭</option>
         <option value="2">����</option>
         <option value="3">����</option>
         <option value="4">����</option>
        </select></td>

      <td class=title>
       ��ֹʱ��(YYYYMMDD)
      </td>
       <td class=input>
      <input  type="text" class="input"  name="deadline">
      </td>
    </tr>
    
     <tr>
      <td class=title>
        ��ֹʱ�����ͣ�</td>
      <td class=input>
        <select  name="deadlineMode">
         <option value="1">��XXXXX����</option>
         <option value="2">¼������</option>
         <option value="3">ǩ������</option>
        </select></td>

      <td class=title>
      </td>
      <td class=input>
      </td>
    </tr>
    

    <tr>
      <td colspan=2 class=button>
        <input class="button" type="button" name="buttonSubmit" alt=" �� ѯ " value="�� ѯ" onclick="submitForm()">
      </td>
      <td colspan=2 class=button>
        <input class="button" type="button" name="buttonCancel" alt=" �� �� " value="�� ��" onclick="resetForm()">
      </td>
    </tr>
  </table>
  </form>
</body>
</html>

