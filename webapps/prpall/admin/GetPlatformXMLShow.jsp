<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@page import="com.sp.phonesale.interf.SendToCCSI"%>
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
	    <title>外部系统报文录入页面</title>
	    <link rel="stylesheet" type="text/css" href="/prpall/css/Standard.css">
	    <link rel="stylesheet" type="text/css" href="/prpall/css/prpcar.css">
	    <script src="/prpall/common/pub/UICommon.js"></script>
	    <script src="/prpall/commoncar/tb/UIProposalQueryInputNew.js"></script>
	    <script src="/prpall/commonship/pub/UIQueryScopeInput.js"></script> 
	    <script language="javascript" src="/prpall/common/js/prototype.js"></script>
		<script language="javascript" src="/prpall/common/js/sunsug.js"></script>
	</head>  
	<body class="logon" background="/prpall/common/images/bgCommon.gif">
		<table  cellpadding="5" cellspacing="1" width="1000" align="center" border="3" bgcolor= "#F7F7F7">
	    	<thead>
	        	<tr class="listtitle">
	        	<td colspan="2">
	        		获取取与平台交互报文
	        	</td>
	        	</tr>
	        </thead>
	  					<% 
	  					
	  					String strFailMessage = "";
  						String[] arrMessage = new String[]{"与平台交互过程出现异常！"};
  						String[] s = null;   
  						String responseXML = "";   
	  					try{
	  						String requestXML = request.getParameter("ToCCSIResquestXML");    
		  			        SendToCCSI sendToCCSI= new SendToCCSI();
		  			    	responseXML = sendToCCSI.sendReceiver(requestXML);
		  			    	s = responseXML.split("平台返回报文：");
	  						}catch(Exception e){
  								e.printStackTrace();
    							strFailMessage =  arrMessage[0] + "\r\n" + e.getMessage();
  							}
  						
  						  
  						if(!strFailMessage.trim().equals("")){
						%>
						<script language=javascript>
      					alert("<%=Str.encode(strFailMessage)%>");
   					 	</script>
<%
	}
	   					%>
	   					<tr>
	   						<td  width="25%" align="center"><font size="3"><b>发送行业平台报文：</b></font></td>
              				<td  width="75%" >
              					<textarea name = "RequestPlatformXML" rows="25" cols="130"><%=s[0]%>
	  							</textarea>
              				</td>
	   					</tr>
	   					<tr>
	   						<td  width="25%" align="center"><font size="3"><b>行业平台返回报文：</b></font></td>
              				<td width="75%">
              					<textarea name = "PlatformResponseXML" rows="15" cols="130"><%=s[1]%>
	  							</textarea>
              				</td>
	   					</tr>
		</table> 
	</body>
</html>
