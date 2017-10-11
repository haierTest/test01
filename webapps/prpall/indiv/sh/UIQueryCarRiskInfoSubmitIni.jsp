<%--
****************************************************************************
* DESC       : 上海风XXXXX信息初始化页面
* Author     : liubin
* CREATEDATE : 2013-05-02
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
--%>
<%@page import="com.sp.indiv.ci.dbsvr.DBCIInsureDemand"%>
<%@page import="com.sp.thirdparty.claim.util.DateTime"%>
<%@page import="com.sp.utility.string.ChgData"%>
<%@page import="com.sp.indiv.bi.interf.BLBIQueryCarRiskInfo"%>
<%@page import="com.sp.indiv.bi.schema.QueryCarRiskInfoSchema"%>
<%@page import="com.sp.indiv.ci.interf.EbaoProxy"%>
<%@page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%
  
  String strDemandNo = ChgData.nullToString(request.getParameter("DemandNo"));
  String strComCode = (String) session.getAttribute("ComCode");
  if(strComCode==null || "".equals(strComCode)){
    strComCode = ChgData.nullToString(request.getParameter("ComCode"));
  }
  String strErrMsg = "";
  QueryCarRiskInfoSchema queryCarRiskInfoSchema = new QueryCarRiskInfoSchema();
  try {
    new BLBIQueryCarRiskInfo().query(queryCarRiskInfoSchema, strComCode, strDemandNo);
  } catch (Exception e) {
    strErrMsg = e.getMessage();
  }
  if (strErrMsg != null && !"".equals(strErrMsg)) {
%>
  <script type="text/javascript">
    alert("<%=strErrMsg %>");
    window.close();
  </script>
<%
  }
%>