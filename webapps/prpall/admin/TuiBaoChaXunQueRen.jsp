<%@ page import="java.lang.*"%>
<%@ page import="com.sp.utility.*"%>
<%@ page import="com.sp.utility.error.*"%>
<%@ page import="com.sp.prpall.ui.UICentralControl"%>
<%@ page import="com.sp.prpall.ui.*"%>
<%@ page import="com.sp.indiv.ci.interf.*"%>
<%@page import="com.sp.utility.*"%>
<%@page import="com.sp.utility.error.*"%>
<%@page import="com.sp.utiall.dbsvr.*"%>
<%@page import="com.sp.utiall.dbsvr.*"%>
<%@page import="com.sp.prpall.blsvr.tb.*"%>
<%@page import="com.sp.prpall.blsvr.cb.*"%>
<%@page import="com.sp.prpall.blsvr.pg.*"%>
<%@page import="com.sp.prpall.blsvr.misc.*"%>
<%@page import="com.sp.prpall.dbsvr.misc.*"%>
<%@page import="com.sp.prpall.pubfun.*"%>
<%@page import="com.sp.prpall.schema.*"%>
<%@page import="com.sp.utility.string.*"%>
<%@page import="com.sp.indiv.sh.util.*"%>
<%@page import="com.sp.indiv.sh.blsvr.*"%>
<%@page import="com.sp.indiv.sh.schema.*"%>
<%@page import="com.sp.indiv.ci.blsvr.*"%>
<%@page import="com.sp.indiv.ci.dbsvr.*"%>
<%@page import="com.sp.indiv.ci.interf.*"%>
<%@page import="com.sp.indiv.ci.schema.*"%>
<%@page import="org.apache.commons.logging.Log"%>
<%@page import="org.apache.commons.logging.LogFactory"%>


<%@ page errorPage="/UIErrorPage"%>

<%
Log logger = LogFactory.getLog("RunTime");


String strPolicyNo    = (String)request.getParameter("strPolicyNo");		
String strType        = (String)request.getParameter("strType");				
String strDate        = (String)request.getParameter("strDate");				
String strValidNo     = (String)request.getParameter("strValidNo");			
String strChgPremium  = (String)request.getParameter("strChgPremium");	
String strComCode     = (String)request.getParameter("strComCode");			
String strReasonCode  = (String)request.getParameter("strReasonCode");  
String a              = "";
String strUser				= "";
String strPassword    = "";

if(strType.trim().equals("退XXXXX查询") &&
   ((!strReasonCode.trim().equals("1") && 
   !strReasonCode.trim().equals("2") &&
   !strReasonCode.trim().equals("3") &&
   !strReasonCode.trim().equals("4") &&
   !strReasonCode.trim().equals("5") &&
   !strReasonCode.trim().equals("9")) ||
   strReasonCode.trim().equals("")))
{
		throw new Exception("退XXXXX查询/确认补数程序错误-退XXXXX查询接口的退XXXXX原来不能为空！必须是1、2、3、4、5、9其中之上，具体原因见代码注释！");
}   

if(strComCode.substring(0, 2).trim().equals("01")) 
{
	
  
  strUser				 = "ygbxbj_user";
  strPassword    = "ygbx123";
}
else if(strComCode.substring(0, 2).trim().equals("07"))
{
	
  
  strUser				 = "ygbxsh_user";
  strPassword    = "ygbx123";
}
else if(strComCode.substring(0, 2).trim().equals("19"))
{
	
  
  strUser				 = "YGBX3300";
  strPassword    = "217497";
}
else if(strComCode.substring(0, 2).trim().equals("03"))  
{
	
  
  strUser				 = "YGBX3200";
  strPassword    = "217497";
}
else if(strComCode.substring(0, 2).trim().equals("18"))  
{
	
  
  strUser				 = "YGBX4300";
  strPassword    = "123456";
}
else if(strComCode.substring(0, 2).trim().equals("31"))  
{
	
  
  
  
}
else if(strComCode.substring(0, 2).trim().equals("29"))  
{
	
  
  
  
}
else
{
	throw new Exception("退XXXXX查询/确认补数程序错误-不能解析你的机构代码，只能是01000000、07000000、19000000、03000000、18000000。您的是" + strComCode);
}

if(strType.equals("退XXXXX查询"))
{

  
  a = "<?xml version='1.0' encoding='GBK'?><PACKET type='REQUEST' version='1.0' ><HEAD>	<REQUEST_TYPE>13</REQUEST_TYPE>	<USER>" + strUser + 
  		"</USER>	<PASSWORD>" + strPassword + "</PASSWORD></HEAD><BODY>	<BASE_PART><CONFIRM_SEQUENCE_NO>" + strValidNo + 
  		"</CONFIRM_SEQUENCE_NO><CANCEL_REASON>" + strReasonCode + "</CANCEL_REASON><EFFECTIVE_TIME>" + strDate + 
  		"</EFFECTIVE_TIME></BASE_PART></BODY></PACKET>";
  
  

}
else if(strType.equals("退XXXXX确认"))
{
  
  a = "<?xml version='1.0' encoding='GBK'?><PACKET type='REQUEST' version='1.0' ><HEAD>	<REQUEST_TYPE>14</REQUEST_TYPE><USER>" + strUser + 
      "</USER><PASSWORD>" + strPassword + "</PASSWORD></HEAD><BODY><BASE_PART><CANCEL_QUERY_NO>" + strValidNo + 
      "</CANCEL_QUERY_NO><CANCEL_CONFIRM_NO></CANCEL_CONFIRM_NO><CANCEL_POLICY_NO>" + strPolicyNo + 
      "</CANCEL_POLICY_NO><ACTUAL_CANCEL_PREMIUM>" + strChgPremium + 
      "</ACTUAL_CANCEL_PREMIUM><CHANGE_REASON_CODE></CHANGE_REASON_CODE><CHANGE_RENSON_DESC></CHANGE_RENSON_DESC></BASE_PART></BODY></PACKET>";
  
  
}
 

logger.info(strComCode + strType + "交互XML串：" + a);


String b = "";
b=EbaoProxy.getInstance().request(a, strComCode);

logger.info(strComCode + strType + "交互返回XML串：" + b);


%>

  <%= strComCode%>  <%= strType%>发送数据： 
  <%= a%>
  <%= strComCode%>  <%= strType%>返回数据：
  <%= b%>  
