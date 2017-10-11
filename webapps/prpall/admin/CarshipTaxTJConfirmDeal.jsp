<%@page errorPage="/UIErrorPage"%>
<%@page import="com.sp.utility.*"%>
<%@page import="com.sp.prpall.blsvr.cb.*"%>
<%@page import="com.sp.indiv.carshiptaxtj.interf.*"%>
<%@page import="com.sp.indiv.carshiptaxtj.dbsvr.DBCICarShipTaxTransaction"%>
<%
	String strPolicyNo = request.getParameter("PolicyNo");
	String strMessage = "";
  String strWhere = "";
	
	UtiPower utiPower = new UtiPower();
  CarshipTaxTJRequest carshipTaxTJRequest = new CarshipTaxTJRequest();
	BLPolicy blPolicy = new BLPolicy();
	blPolicy.getData(strPolicyNo);
	if(blPolicy.getBLPrpCmain().getSize() == 0)
	{
    strMessage = "XX" + strPolicyNo + "不存在！";
%>    
	  <jsp:forward page="/common/pub/UIMessagePage.jsp">
	    <jsp:param name="Picture" value="F" />
	    <jsp:param name="Content" value="<%=strMessage%>" />
	  </jsp:forward>
<%		
		return;
	}
	if(utiPower.checkCICarshipTax(blPolicy.getBLPrpCmain().getArr(0).getRiskCode(), blPolicy.getBLPrpCmain().getArr(0).getComCode()) && utiPower.getAreaName().equals("10"))
  {
		try
    {
			strWhere = "PolicyNo = '" + strPolicyNo + "' AND substr(Flag,1,1) = '2'";
			DBCICarShipTaxTransaction dbCICarShipTaxTransaction = new DBCICarShipTaxTransaction();
		  if(dbCICarShipTaxTransaction.getCount(strWhere) == 0)
			{
				
				carshipTaxTJRequest.confirmRequest(blPolicy,strPolicyNo);
				strMessage = "车船税与地税平台确认成功！";
%>      
		    <jsp:forward page="/common/pub/UIMessagePage.jsp">
		   		 <jsp:param name="Picture" value="S" />
		       <jsp:param name="Content" value="<%=strMessage%>" />
		    </jsp:forward>
<%     
			}
			else
			{
    		strMessage = "该XX已经做过地税平台确认！";
%>    
		    <jsp:forward page="/common/pub/UIMessagePage.jsp">
		      <jsp:param name="Picture" value="C" />
		      <jsp:param name="Content" value="<%=strMessage%>" />
		    </jsp:forward>
<%		
			}
    }
		catch(Exception ex)
    {
    	ex.printStackTrace();
    	strMessage = "天津车船税平台交互过程出现异常\r\n" + ex.getMessage();
%>    
	    <jsp:forward page="/common/pub/UIMessagePage.jsp">
	      <jsp:param name="Picture" value="F" />
	      <jsp:param name="Content" value="<%=strMessage%>" />
	    </jsp:forward>
<%  
    }
	}
	else
	{
    strMessage = "XX" + strPolicyNo + "不符合业务权限";
%>    
	  <jsp:forward page="/common/pub/UIMessagePage.jsp">
	    <jsp:param name="Picture" value="C" />
	    <jsp:param name="Content" value="<%=strMessage%>" />
	  </jsp:forward>
<%	
	}
%>