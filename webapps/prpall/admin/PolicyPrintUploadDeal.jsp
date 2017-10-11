<%@page errorPage="/UIErrorPage"%>
<%@page import="com.sp.utility.*"%>
<%@page import="com.sp.utiall.schema.*"%>
<%@page import="com.sp.utiall.blsvr.*"%>
<%@page import="com.sp.prpall.blsvr.cb.*"%>
<%@page import="com.sp.prpall.pubfun.PubTools"%>
<%@page import="com.sp.indiv.ci.interf.*"%>
<%@page import="java.util.ArrayList"%>
<%
	String strPolicyNo = request.getParameter("PolicyNo");
    String strUserCode = request.getParameter("UserCode");
	String strIP= "";
	String strMessage = "";
    String strWhere = "";
	String strUsbKey="";
	UtiPower utiPower = new UtiPower();
	BLPolicy blPolicy = new BLPolicy();
	blPolicy.getData(strPolicyNo);
	if(blPolicy.getBLPrpCmain().getSize() == 0)
	{
    strMessage = "d" + strPolicyNo + "不存在！";
%>    
	  <jsp:forward page="/common/pub/UIMessagePage.jsp">
	    <jsp:param name="Picture" value="F" />
	    <jsp:param name="Content" value="<%=strMessage%>" />
	  </jsp:forward>
<%		
		return;
	}
	BLPrpDuser blPrpDuser=new BLPrpDuser();
	
	
	String iWherePart = " userCode = ? ";
	ArrayList iWhereValue=new ArrayList();
	iWhereValue.add(strUserCode);
	blPrpDuser.query(iWherePart,iWhereValue);
	
	if(blPrpDuser.getSize()==0){
	    strMessage = "系统中不存在" + strUserCode + "此操作人员！";
%>    
	    	<jsp:forward page="/common/pub/UIMessagePage.jsp">
	    	   <jsp:param name="Picture" value="F" />
	    	   <jsp:param name="Content" value="<%=strMessage%>" />
	    	</jsp:forward>
<%		
	   return;		
	}
	PrpDuserSchema prpDuserSchema=blPrpDuser.getArr(0);
	String strComCode=prpDuserSchema.getComCode();
	BLPrpDcompany blPrpDcompany=new BLPrpDcompany();
	blPrpDcompany.query("comCode='"+strComCode+"'");
	PrpDcompanySchema prpDcompanySchema=blPrpDcompany.getArr(0);
	String strComLevel=prpDcompanySchema.getComLevel();
	if(!utiPower.checkCIInsureBJ(blPolicy.getBLPrpCmain().getArr(0).getRiskCode(),strComCode)){
	    strMessage = "XX" + strPolicyNo + "不能进行操作权限！";
%>    
	    	  <jsp:forward page="/common/pub/UIMessagePage.jsp">
	    	    <jsp:param name="Picture" value="F" />
	    	    <jsp:param name="Content" value="<%=strMessage%>" />
	    	  </jsp:forward>
<%		
	    		return;	
	}
	if(strComLevel!=null&&!strComLevel.equals("")){
		if("8".equals(strComLevel)){
%>
		<OBJECT classid="clsid:FCAA4851-9E71-4BFE-8E55-212B5373F040" id="bjcactrl" style="HEIGHT: 1px; LEFT: 10px; TOP: 28px; WIDTH: 1px" height=1 width=1 VIEWASTEXT>
		</OBJECT>
		<%}%>
		<script src="/prpall/common/pub/UIGetUsbKey.js"></script>
<%
	   if(!"8".equals(strComLevel)){
		   strIP=PubTools.getIpAddr(request);
	   }
%>
		<script language="javascript" type="text/javascript">
			function submitUpload(){
				var strURL="/prpall/admin/PolicyPrintUploadDealFinal.jsp";
<%
				if("8".equals(strComLevel)){
%>
					var strUsbKey = getUsbKey();
					strURL=strURL+"?ComLevel=<%=strComLevel%>"+"&UsbKey="+strUsbKey+"&UserCode=<%=strUserCode%>"+"&PolicyNo=<%=strPolicyNo%>"+"&ComCode=<%=strComCode%>"; 
<%
				}else{
%>
					 strURL=strURL+"?ComLevel=<%=strComLevel%>"+"&IP=<%=strIP%>"+"&UserCode=<%=strUserCode%>"+"&PolicyNo=<%=strPolicyNo%>"+"&ComCode=<%=strComCode%>";
<%						
				}
%>
                window.location.href(strURL);
			}
			submitUpload();
        </script>
<%
	}else{
	    strMessage = "操作人员没有权限！";
%>    
	    	    	  <jsp:forward page="/common/pub/UIMessagePage.jsp">
	    	    	    <jsp:param name="Picture" value="F" />
	    	    	    <jsp:param name="Content" value="<%=strMessage%>" />
	    	    	  </jsp:forward>
<%		
	    	    		return;		
	}
%>
