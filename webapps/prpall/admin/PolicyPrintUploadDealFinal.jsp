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
	String strIP= request.getParameter("IP");
	String strMessage = "";
    String strWhere = "";
	String strUsbKey= request.getParameter("UsbKey");
	String strComLevel= request.getParameter("ComLevel");
	String strComCode= request.getParameter("ComCode");
	UtiPower utiPower = new UtiPower();
	BLPolicy blPolicy = new BLPolicy();
	blPolicy.getData(strPolicyNo);
	if(blPolicy.getBLPrpCmain().getSize() == 0)
	{
    strMessage = "f" + strPolicyNo + "�����ڣ�";
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
	    strMessage = "ϵͳ�в�����" + strUserCode + "�˲�����Ա��";
%>    
	    	<jsp:forward page="/common/pub/UIMessagePage.jsp">
	    	   <jsp:param name="Picture" value="F" />
	    	   <jsp:param name="Content" value="<%=strMessage%>" />
	    	</jsp:forward>
<%		
	   return;		
	}
	if(!utiPower.checkCIInsureBJ(blPolicy.getBLPrpCmain().getArr(0).getRiskCode(),strComCode)){
	    strMessage = "XX" + strPolicyNo + "���ܽ��в���Ȩ�ޣ�";
%>    
	    	  <jsp:forward page="/common/pub/UIMessagePage.jsp">
	    	    <jsp:param name="Picture" value="F" />
	    	    <jsp:param name="Content" value="<%=strMessage%>" />
	    	  </jsp:forward>
<%		
	    		return;	
	}
	if(strComLevel!=null&&!strComLevel.equals("")){
		try{
			new PolicyPrintUpload().upload(strPolicyNo,strComLevel,strIP,strUsbKey,strUserCode);
		}catch(Exception e){
			strMessage = "XXXXXƽ̨�ϴ�XX�����쳣\r\n" + e.getMessage();
%>
			<jsp:forward page="/common/pub/UIMessagePage.jsp">
				<jsp:param name="Picture" value="F" />
				<jsp:param name="Content" value="<%=strMessage%>" />
			</jsp:forward>
<%   	    

			return;
		}
		
	}else{
	    strMessage = "������Աû��Ȩ�ޣ�";
%>    
	    	    	  <jsp:forward page="/common/pub/UIMessagePage.jsp">
	    	    	    <jsp:param name="Picture" value="F" />
	    	    	    <jsp:param name="Content" value="<%=strMessage%>" />
	    	    	  </jsp:forward>
<%		
	    	    		return;		
	}
%>