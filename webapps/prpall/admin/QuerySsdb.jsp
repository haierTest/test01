<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+":
%>

<!DOCTYPE HTML PUBLIC "-
<html>
  <head>
    <base href="<%=basePath%>">
    <%
    	String strAction = "";

    	String mapValue = request.getParameter("mapValue");
    	String keyValue = request.getParameter("keyValue");
    	String serialno = request.getParameter("serialno");
    	String riskcode = request.getParameter("riskcode");
    	if (mapValue != null && !(mapValue.trim().equals(""))
    			&& ((keyValue != null && !(keyValue.trim().equals("")))
    				|| ((serialno != null && !(serialno.trim().equals(""))) 
    					|| (riskcode != null && !(riskcode.trim().equals("")))))) {
    		strAction = "/prpall/admin/QuerySsdbResult.jsp";
    	}else{
    		mapValue = "";
    		keyValue = "";
    		serialno = "";
    		riskcode = "";
    		strAction = "/prpall/admin/QuerySsdb.jsp";
    	}
    %>
    <title>SSDB查询</title>
    <link rel="stylesheet" type="text/css" href="/prpall/css/Standard.css">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script language="javascript">
	function submitForm(){
      if (fm.mapValue.value==""){
      		  alert("MAP名称为空！");
		      return false;
	  }
      if(fm.keyValue.value==""){
		  if(fm.serialno.value=="" || fm.riskcode.value==""){
		  	  alert("KEY值为空！或  流水号XXXXX别为空！");
			  return false;
		  }		
      }
      fm.submit();
    }
		function autoQuery(){
		    <%if (mapValue != null && !(mapValue.trim().equals(""))
    			&& ((keyValue != null && !(keyValue.trim().equals("")))
    				|| ((serialno != null && !(serialno.trim().equals(""))) 
    					|| (riskcode != null && !(riskcode.trim().equals("")))))) {%>
		   	document.fm.submitbutton.click();
		   	<%}%>

		}
		function change(){
			var mapValue=fm.mapValue.value;
			var keyValue=fm.keyValue.value;
			var serialno=fm.serialno.value;
			var riskcode=fm.riskcode.value;
			window.location.href="/prpall/admin/UpdateSsdbResult.jsp?mapValue="+mapValue+"&&keyValue="+keyValue+"&&serialno="+serialno+"&&riskcode="+riskcode;
			
		}
		function del(){
			var mapValue=fm.mapValue.value;
			var keyValue=fm.keyValue.value;
			var serialno=fm.serialno.value;
			var riskcode=fm.riskcode.value;
			window.location.href="/prpall/admin/DeleteSsdbResult.jsp?mapValue="+mapValue+"&&keyValue="+keyValue+"&&serialno="+serialno+"&&riskcode="+riskcode;
			
		}
	</script>
  </head>
 
  <body onload="autoQuery();">
  	<form name="fm" method="post" action="<%=strAction%>" target="resultFrame">
    	<table class="three" align=center>
    		<tr>
    			<td class="formtitle"  colspan="4">SSDB查询结果</td>
    		</tr>
			<tr>
				<td>MAP名称<input type="text" name="mapValue" value="prpBasicInfoCaChe"/></td>
				<td>KEY值<input type="text" name="keyValue" value="<%=keyValue%>"/></td>
				<td>流水号<input type="text" name="serialno" value="<%=serialno%>"/></td>
				<td>XXXXX种<input type="text" name="riskcode" value="<%=riskcode%>"/></td>
			</tr>
			<tr>
			    <td><input type=button value="查询" class="button" name="submitbutton" onclick="submitForm();"></td>
			    <td><input type=button value="修改" class="button" name="updatebutton" onclick="change();"></td>
			    <td><input type=button value="删除" class="button" name="deletebutton" onclick="del();"></td>
			    <td></td>
			</tr>
			<tr>
				<td class="formtitle"  colspan="4">查询接口</td>
			</tr>
    	</table>
    </form>
   	<table width="100%" height="100%" align=center>
  	  	<tr>
        	<td>
           	<iframe name="resultFrame" src="" frameborder='0' width="100%" height="100%" ></iframe>
        	</td>
      	</tr>
  	</table>
  </body>
</html>
