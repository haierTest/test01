<%@page import="com.sp.prpall.pubfun.*"%>
<%
 String strPolicyNo=request.getParameter("policyNo");
 ModiUnwrtFlag modiUnwrtflag = new ModiUnwrtFlag();
 boolean f=modiUnwrtflag.modiFlag(strPolicyNo);
 String strMessage="";
 if(f){
    strMessage="f!";
 }else{
 	  strMessage="s!";
 }
%>
<%-- f --%>
<jsp:include page="/common/pub/UIMessagePage.jsp">
  <jsp:param name="Picture" value="C" />
  <jsp:param name="Content" value="<%=strMessage%>" />
</jsp:include>