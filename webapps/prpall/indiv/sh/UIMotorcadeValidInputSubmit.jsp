<%--
****************************************************************************
* DESC       ����������ȷ�������ύҳ��
* Author     : X
* CREATEDATE ��2004-02-12
* MODIFYLIST ��   Name       Date            Reason/Contents
****************************************************************************
--%>
<%-- ������ --%>
<%@page errorPage="/UIErrorPage"%>

<%-- ����bean�ಿ�� --%>
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
      "��û��ѡ���κβ�ѯ�룬��ѡ��ò�ѯ������ύ��");
  }

  BLMotorcadeThird blMotorcadeThird = new BLMotorcadeThird();
  
  
  blMotorcadeThird.valid(strPrecontractNo,arrDemandNo);
%>
  <%-- ҳ����ʾ --%>
  <jsp:include page="/indiv/sh/UIMotorcadeValidShow.jsp">
    <jsp:param name="PrecontractNo" value="<%=strPrecontractNo%>" />
  </jsp:include>