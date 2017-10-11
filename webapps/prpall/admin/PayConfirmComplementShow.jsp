<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@page import="com.sp.phonesale.common.PayConfirmTask"%>
<%@page import="org.dom4j.Document"%>
<%@page import="org.dom4j.DocumentHelper"%>
<%@page import="org.dom4j.Element"%>
<%@page import="com.sp.utility.string.Str"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+":
%>

<!DOCTYPE HTML PUBLIC "-
<html>
	<head>
	    <title>实收状态补数页面</title>
	    <link rel="stylesheet" type="text/css" href="/prpall/css/Standard.css">
	    <link rel="stylesheet" type="text/css" href="/prpall/css/prpcar.css">
	    <script src="/prpall/common/pub/UICommon.js"></script>
	    <script language="javascript" src="/prpall/common/js/prototype.js"></script>
		<script language="javascript" src="/prpall/common/js/sunsug.js"></script>
	</head>  
	<body class="logon" background="/prpall/common/images/bgCommon.gif">
		<table  cellpadding="5" cellspacing="1" width="1000" align="center" border="3" bgcolor= "#F7F7F7">
	    	<thead>
	        	<tr class="listtitle">
	        	<td colspan="2">
	        		实收状态补数结果。
	        	</td>
	        	</tr>
	        </thead>
	  					<%
	  					String err = "";
	  					try{
	  						String PayRefDate = request.getParameter("PayRefDate");
	  						String PolicyNo = request.getParameter("PolicyNo");
	  						PayConfirmTask payConfirmTask = new PayConfirmTask();
		  			        if(!"".equals(PolicyNo) && PolicyNo!=null){
		  			            payConfirmTask.doPayConfirmByPolicyNo(PolicyNo);
		  			        }else{
		  			            payConfirmTask.doPayConfirmByPayDate(PayRefDate);
		  			        }

		  			    }catch(Exception e){
  							e.printStackTrace();
  							err=e.toString();
  						}
  						if(!"".equals(err)){
  						%>
  		   <tr>
           <td align="center">失败！</td>
           </tr>
  						<%
  						}else{
  						%>
  		   <tr>
           <td align="center">成功！</td>
           </tr>
  						<%
  						}
						%>

  		   

		</table> 
	</body>
</html>
