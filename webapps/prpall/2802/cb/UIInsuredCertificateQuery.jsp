<%--
****************************************************************************
* DESC       ：XX打印按条件查询
* Author     : X
* CREATEDATE ：2003-07-21
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
--%>
<%@page import="com.sp.utility.SysConfig"%>

<%
	String strPolicyHead = SysConfig.getProperty("POLICY_HEAD");
	String strEDITTYPE   = request.getParameter(SysConfig.getProperty("EDITTYPE"));
%>

<html>
  <head>
    <title>查询条件页面</title>
    <link rel="stylesheet" type="text/css" href="/prpall/css/Standard.css">
    <script src="/prpall/commonship/pub/UICommon.js"></script>
    <script language=javascript>
    	
    	
    	function submitForm()
    	{
	    	 if(trim(fm.BizNo.value)==""&&trim(fm.PrintType.value)=="P")
	      {
	        errorMessage("\"XX号\"不能为空!");
	        fm.BizNo.focus();
	        fm.BizNo.select();
	        return false ;
	      }
	      	if(trim(fm.BizNo.value)==""&&trim(fm.PrintType.value)=="E")
	      {
	        errorMessage("\"批单号\"不能为空!");
	        fm.BizNo.focus();
	        fm.BizNo.select();
	        return false ;
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
	  <form name=fm action="/prpall/2802/cb/UIInsuredCertificateQueryList.jsp" method=post>
	 
	  
	  <table class="common" cellpadding="5" cellspacing="1" align="center">
  
	   
	    <tr>
	      <td class=formtitle colspan="4">XX凭证打印查询
	        <input type="hidden" name="RiskCode" value="1101">
	      </td>
	    </tr>
	    <tr>
	      <td class=title>
	        业务号：</td>
	      <td class="input">
	       <select style="width:50px" class=query name="PrintType">
	           <option value="P">XX</option>
	           <option value="E">批单</option>
	         </select>	       
	        <input type="text" name="BizNo" class="common" maxlength="25"> <img src="/prpall/commonship/images/markMustInput.jpg"></td>
	       <td class=title>
	        身份证号码：</td>
	      <td class=input>
	        <select class=query name="IdentifyNumberSign" style="width:50px">
	          <%@include file="/commonship/pub/UIStringOption.html"%>
	        </select><input type="text" name="IdentifyNumber" class="common" maxlength="21"></td>
	    </tr>	   
	    <tr>
	      <td class=title>
	        被XX人代码：</td>
	      <td class=input>
	        <select class=query name="InsuredCodeSign" style="width:50px">
	          <%@include file="/commonship/pub/UIStringOption.html"%>
	        </select><input type="text" name="InsuredCode" class="common" maxlength="16"></td>
	      <td class=title>
	        被XX人名称：</td>
	      <td class=input>
	        <select class=query name="InsuredNameSign" style="width:50px">
	          <%@include file="/commonship/pub/UIStringOption.html"%>
	        </select><input type="text" name="InsuredName" class="common" maxlength="120"></td>
	    </tr>
	   	  
	    <tr>
	      <td colspan=2 class=button>
	        <Input name="buttonSubmit" class="button" type="button"  alt="查询"  value="查 询" onclick="submitForm()">
	      </td>
	      <td colspan=2 class=button>
	        <Input name="buttonReset" class="button" type="button"  alt="重置"  value="重 置" onclick="resetForm()">
	      </td>
	    </tr>
	  </table>
	  </form>
	</body>
</html>
