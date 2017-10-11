<%--
****************************************************************************
* DESC       ：法定三者查询请求提交页面
* Author     : X
* CREATEDATE ：2004-02-11
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
--%>

<%-- 错误处理 --%>
<%@page errorPage="/UIErrorPage"%>

<%-- 引入bean类部分 --%>
<%@page import="com.sp.utility.*"%>
<%@page import="com.sp.utility.string.*"%>
<%@page import="com.sp.indiv.sh.util.*"%>
<%@page import="com.sp.indiv.sh.blsvr.*"%>
<%@page import="com.sp.indiv.sh.schema.*"%>

<%
  
  String strLicenseNo       = request.getParameter("LicenseNo");
  String strEngineNo        = request.getParameter("EngineNo");
  String strFrameNo         = request.getParameter("FrameNo");
  String strUseNatureCode   = request.getParameter("UseNatureCode");
  String strCarKindCode     = request.getParameter("CarKindCode");
  String strSeatCount       = request.getParameter("SeatCount");
  String strTonCount        = request.getParameter("TonCount");
  String strExhaustScale    = request.getParameter("ExhaustScale");
  String strAmount          = request.getParameter("Amount");

  String strLicenseType     = request.getParameter("LicenseType"); 
  String strStartDate       = request.getParameter("StartDate");
  String strEndDate         = request.getParameter("EndDate");
  
  BLDemand blDemand = new BLDemand();
  PrpExtraBDemandSchema prpExtraBDemandSchema = new PrpExtraBDemandSchema();
  String strUserCode = (String)session.getValue("UserCode");
  String strComCode  = (String)session.getValue("ComCode");
  
  
  blDemand.getBLPrpExtraBDemand().setArr(prpExtraBDemandSchema);
  prpExtraBDemandSchema.setLicenseNo       ( strLicenseNo     );
  prpExtraBDemandSchema.setEngineNo        ( strEngineNo      );
  prpExtraBDemandSchema.setFrameNo         ( strFrameNo       );
  prpExtraBDemandSchema.setUseNatureCode   ( strUseNatureCode );
  prpExtraBDemandSchema.setCarKindCode     ( strCarKindCode   );
  prpExtraBDemandSchema.setSeatCount       ( Integer.parseInt(ChgData.chgStrZero(strSeatCount)) );
  prpExtraBDemandSchema.setTonCount        ( Double.parseDouble(ChgData.chgStrZero(strTonCount)) );
  prpExtraBDemandSchema.setExhaustScale    ( Double.parseDouble(ChgData.chgStrZero(strExhaustScale)) );
  prpExtraBDemandSchema.setAmount          ( Double.parseDouble(ChgData.chgStrZero(strAmount)) );
  prpExtraBDemandSchema.setStartDate       ( strStartDate     );
  prpExtraBDemandSchema.setEndDate         ( strEndDate       );
  prpExtraBDemandSchema.setComCode         ( strComCode       );
  prpExtraBDemandSchema.setOperatorCode    ( strUserCode      );
  prpExtraBDemandSchema.setDemandTime      ( new Formater(Formater.DATETOMINUTE).format(new java.util.Date()) );
  
  prpExtraBDemandSchema.setLicenseType     ( strLicenseType   );

  
  blDemand.request();

  
  blDemand.save();
  
  
  request.setAttribute("Demand",blDemand);
%>
  
  <%-- 显示结果 --%>
  <jsp:include page="/indiv/sh/UIDemandShow.jsp">
    <jsp:param name="ExistDemand" value="Yes" />
  </jsp:include>
