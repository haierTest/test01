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
<%@page import="com.sp.utility.string.*"%>
<%@page import="com.sp.indiv.sh.util.*"%>
<%@page import="com.sp.indiv.sh.blsvr.*"%>
<%@page import="com.sp.indiv.sh.schema.*"%>

<%
  
  String strDemandNo = "";
  
  BLValid blValid = new BLValid();
  PrpExtraBValidSchema prpExtraBValidSchema = new PrpExtraBValidSchema();
  String strUserCode = (String)session.getValue("UserCode");
  String strComCode  = (String)session.getValue("ComCode");

  
  strDemandNo = request.getParameter("DemandNo");

  
  blValid.getBLPrpExtraBValid().setArr(prpExtraBValidSchema);
  prpExtraBValidSchema.setDemandNo    ( strDemandNo );
  prpExtraBValidSchema.setComCode     ( strComCode  );
  prpExtraBValidSchema.setOperatorCode( strUserCode );
  prpExtraBValidSchema.setValidTime   ( new Formater(Formater.DATETOMINUTE).format(new java.util.Date()) );
  
  
  blValid.request();
  
  
  blValid.save();
  
  
  request.setAttribute("Valid",blValid);
%>

  <%-- ��ʾ��� --%>
  <jsp:include page="/indiv/sh/UIValidShow.jsp">
    <jsp:param name="ExistValid" value="Yes" />
  </jsp:include>