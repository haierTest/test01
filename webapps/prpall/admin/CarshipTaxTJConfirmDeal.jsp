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
    strMessage = "XX" + strPolicyNo + "�����ڣ�";
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
				strMessage = "����˰���˰ƽ̨ȷ�ϳɹ���";
%>      
		    <jsp:forward page="/common/pub/UIMessagePage.jsp">
		   		 <jsp:param name="Picture" value="S" />
		       <jsp:param name="Content" value="<%=strMessage%>" />
		    </jsp:forward>
<%     
			}
			else
			{
    		strMessage = "��XX�Ѿ�������˰ƽ̨ȷ�ϣ�";
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
    	strMessage = "��򳵴�˰ƽ̨�������̳����쳣\r\n" + ex.getMessage();
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
    strMessage = "XX" + strPolicyNo + "������ҵ��Ȩ��";
%>    
	  <jsp:forward page="/common/pub/UIMessagePage.jsp">
	    <jsp:param name="Picture" value="C" />
	    <jsp:param name="Content" value="<%=strMessage%>" />
	  </jsp:forward>
<%	
	}
%>