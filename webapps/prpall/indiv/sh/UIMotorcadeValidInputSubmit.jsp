<%--
****************************************************************************
* DESC       ：法定三者确认请求提交页面
* Author     : X
* CREATEDATE ：2004-02-12
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
--%>
<%-- 错误处理 --%>
<%@page errorPage="/UIErrorPage"%>

<%-- 引入bean类部分 --%>
<%@page import="com.sp.utility.*"%>
<%@page import="com.sp.utility.error.*"%>
<%@page import="com.sp.utility.string.*"%>
<%@page import="com.sp.indiv.sh.util.*"%>
<%@page import="com.sp.indiv.sh.blsvr.*"%>
<%@page import="com.sp.indiv.sh.schema.*"%>

<%
  
  String strPrecontractNo = request.getParameter("PrecontractNo");
  int intNumCar = Integer.parseInt(request.getParameter("NumCar"));
  String[] arrDemandNo = request.getParameterValues("CheckedDemandNo");
  if(arrDemandNo==null)
  {
    throw new UserException(-98,-2,"UIMotorcadeValidInputSubmit.jsp",
      "您没有选择任何查询码，请选择好查询码后再提交！");
  }

  BLMotorcadeThird blMotorcadeThird = new BLMotorcadeThird();
  
  
  blMotorcadeThird.valid(strPrecontractNo,arrDemandNo);
%>
  <%-- 页面显示 --%>
  <jsp:include page="/indiv/sh/UIMotorcadeValidShow.jsp">
    <jsp:param name="PrecontractNo" value="<%=strPrecontractNo%>" />
  </jsp:include>