<%--
****************************************************************************
* DESC       ������ʵ��״̬����
* Author     ��zhaoyingchao-ghq
* CREATEDATE ��2014-08-13
* MODIFYLIST ��  Name       Date      Reason/Contents
* 
****************************************************************************
--%>

<%@page errorPage="/UIErrorPage"%>
<%@page import="com.sp.prpall.pubfun.PubTools"%>
<%@page import="com.sp.sysframework.reference.AppConfig"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.sp.utility.SysConfig"%>
<html>
  <head>
    <title>ʵ��״̬����ҳ��</title>
    <link rel="stylesheet" type="text/css" href="/prpall/css/Standard.css">
    <script language="javascript">
    function submitForm(){
	    fm.action="/prpall/admin/PayConfirmComplementShow.jsp";
        fm.submit(); 
    } 
    </script>
  </head>
<body>
  <form name=fm method=post action="" >
  <table class="common"  cellpadding="5" cellspacing="1"  align=center>
    <tr>
      <td class=formtitle colspan="4">����ʵ��״̬����
      <input type=hidden name="ClassCode"></td>
    </tr>
    <tr>
      <td class=title>
                        XX�ţ�</td>
      <td class="input" >
        <input name="PolicyNo" class="common" maxlength="19" style="width:40%">
      </td>
      <td colspan=2 class="button" align=left>
        <Input name="btnPayConfirmByPolicyNo" class="button" type="button" value="�� ��" alt="����""  onclick="submitForm()">
      </td>
    </tr>
    <tr>
      <td class=title>
                        ʵ�����ڣ���ʽ��YYYY-MM-DD����
      </td>
      <td class="input" >
        <input name="PayRefDate" class="common" maxlength="10" style="width:40%">
                        ��ܿ�ϵͳ��æʱ�䣬����19:30��ִ�� 
      </td>
      <td colspan=2 class="button" align=left>
        <Input name="btnPayConfirmByPayDate" class="button" type="button" value="�� ��" alt="����""  onclick="submitForm()">
      </td>
    </tr>
  </table>
  </form>
</body>
</html>