<%--
****************************************************************************
* DESC       ：电销实收状态补数
* Author     ：zhaoyingchao-ghq
* CREATEDATE ：2014-08-13
* MODIFYLIST ：  Name       Date      Reason/Contents
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
    <title>实收状态补数页面</title>
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
      <td class=formtitle colspan="4">电销实收状态补数
      <input type=hidden name="ClassCode"></td>
    </tr>
    <tr>
      <td class=title>
                        XX号：</td>
      <td class="input" >
        <input name="PolicyNo" class="common" maxlength="19" style="width:40%">
      </td>
      <td colspan=2 class="button" align=left>
        <Input name="btnPayConfirmByPolicyNo" class="button" type="button" value="补 数" alt="补数""  onclick="submitForm()">
      </td>
    </tr>
    <tr>
      <td class=title>
                        实收日期（格式：YYYY-MM-DD）：
      </td>
      <td class="input" >
        <input name="PayRefDate" class="common" maxlength="10" style="width:40%">
                        请避开系统繁忙时间，建议19:30后执行 
      </td>
      <td colspan=2 class="button" align=left>
        <Input name="btnPayConfirmByPayDate" class="button" type="button" value="补 数" alt="补数""  onclick="submitForm()">
      </td>
    </tr>
  </table>
  </form>
</body>
</html>