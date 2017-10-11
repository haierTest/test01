<%--
****************************************************************************
* DESC       : 上海风XXXXX信息显示页面
* Author     : liubin
* CREATEDATE : 2013-05-02
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
--%>
<%@page import="java.util.List"%>
<%@page import="com.sp.utility.string.ChgData"%>
<%@page import="com.sp.indiv.bi.schema.QueryCarRiskInfoSchema"%>
<%@page import="com.sp.indiv.bi.schema.CIInsureDemandRiskSchema"%>
<%@page import="com.sp.indiv.bi.schema.QueryCarClaimInfoSchema"%>
<%@page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@page errorPage="/UIErrorPage"%>
<%@include file="/indiv/sh/UIQueryCarRiskInfoSubmitIni.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>风XXXXX信息查询</title>
<link rel="stylesheet" type="text/css" href="/prpall/css/Standard.css">
<script src="/prpall/common/pub/UICommon.js"></script>
<script language="javascript">
  function querySettlementDetail(accidentDesc, bcflag) {
    var strTitle = "";
    if (bcflag == "CI") {
      strTitle = "交强XXXXX出XXXXX经过";
    } else if (bcflag == "BI") {
      strTitle = "商业XXXXX出XXXXX经过";
    }
    var strURL = "/prpall/indiv/sh/UIQueryCarAccidentDetailSH.jsp?accidentDesc=" + accidentDesc + "&title=" + strTitle;
    window.open(strURL,"",'width=640,height=350,top=50,left=80,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
    return false;
  }
</script>
</head>
<body>
  <table class="common" align="center">
    <thead>
      <tr>
        <td class="formtitle" colspan="6">基本信息</td>
      </tr>
    </thead>
    <tfoot>
    </tfoot>
    <tbody>
      <tr>
        <td class=title style="width: 15%">号牌号码：</td>
        <td class=input style="width: 15%">
          <input type=text name="codeCName" class="readonly" readonly value="<%=queryCarRiskInfoSchema.getCarMark()%>">
        </td>
        <td class="title" style="width: 15%">号牌种类：</td>
        <td class=input style="width: 15%">
          <input type=text name="codeCName" class="readonly" readonly value="<%=queryCarRiskInfoSchema.getVehicleType()%>">
        </td>
        <td class="title" style="width: 15%">车架号：</td>
        <td class=input style="width: 15%">
          <input type=text name="userName" class="readonly" readonly value="<%=queryCarRiskInfoSchema.getRackNo()%>">
        </td>
      </tr>
      <tr>
        <td class=title style="width: 15%">发动机号：</td>
        <td class=input style="width: 15%">
          <input type=text name="appliName" class="readonly" readonly value="<%=queryCarRiskInfoSchema.getEngineNo()%>">
        </td>
        <td class="title" style="width: 15%">车主：</td>
         <td class=input style="width: 15%">
           <input type="text" name="renewalName" class="readonly" readonly value="<%=queryCarRiskInfoSchema.getOwner()%>">
         </td>
         <td class="title" style="width: 15%">过户标志：</td>
         <td class=input style="width: 15%">
           <input type="text" name="lastPayRate" class="readonly" readonly value="<%=queryCarRiskInfoSchema.getTransferFlag()%>">
         </td>
      </tr>
      <tr>
        <td class="title" style="width: 15%">最近一张交强XX起期：</td>
        <td class=input style="width: 15%">
          <input type=text name="discount" class="readonly" readonly value="<%=queryCarRiskInfoSchema.getLastyearStartDate()%>">
        </td>
         <td class="title" style="width: 15%">最近一张交强XX止期：</td>
         <td class=input style="width: 15%">
           <input type="text" name="hisPremiumRate" class="readonly" readonly value="<%=queryCarRiskInfoSchema.getLastyearEndDate()%>">
         </td>
         <td class="title" style="width: 15%">最近一张交强XXXXXXXXX公司：</td>
         <td class=input style="width: 15%">
           <input type="text" name="hisPremiumScale" class="readonly" readonly value="<%=queryCarRiskInfoSchema.getLastyearCompanyId()%>">
         </td>
      </tr>
      <tr>
        <td class="title" style="width: 15%">高风XXXXX标志：</td>
        <td class=input style="width: 15%">
          <input type=text name="discount" class="readonly" readonly value="<%=queryCarRiskInfoSchema.getHighRiskFlag()%>">
        </td>
      </tr>
      <tr>
        <td class="title" style="width: 15%">交运有效期起理由：</td>
        <td class=input style="width: 15%">
          <input type=text name="discount" class="readonly" readonly  value="<%=queryCarRiskInfoSchema.getEffectReason()%>">
        </td>
      </tr>
    </tbody>
  </table>
  <br><br>
  <table class=common cellpadding="5" cellspacing="1" >
    <thead>
      <tr>
        <td class="formtitle" colspan="8">商业XXXXXXX信息</td>
      </tr>
    </thead>
    <tfoot>
    </tfoot>
    <tbody>
      <tr class=listtitle>
        <td width='10%'>XX公司</td>
        <td width='10%'>XX起期</td>
        <td width='10%'>XX止期</td>
        <td width='10%'>车主</td>
        <td width='10%'>公司XXXXX种名称</td>
        <td width='10%'>赔偿限额</td>
        <td width='10%'>XX起期</td>
        <td width='10%'>XX止期</td>
      </tr>
      <%
        List demRiskList = queryCarRiskInfoSchema
          .getCIInsureDemandRiskSchemas();
        for (int i = 0; i < demRiskList.size(); i++) {
          CIInsureDemandRiskSchema demRiskSchema = (CIInsureDemandRiskSchema)demRiskList.get(i);
      %>
      <tr>
        <td width='10%' align="center"><%=demRiskSchema.getCompanyID() %></td>
        <td width='10%' align="center"><%=demRiskSchema.getStartDate() %></td>
        <td width='10%' align="center"><%=demRiskSchema.getEndDate() %></td>
        <td width='10%' align="center"><%=demRiskSchema.getOwner() %></td>
        <td width='10%' align="center"><%=demRiskSchema.getComCoverageName() %></td>
        <td width='10%' align="center"><%=demRiskSchema.getLimitAmount() %></td>
        <td width='10%' align="center"><%=demRiskSchema.getKindStartDate() %></td>
        <td width='10%' align="center"><%=demRiskSchema.getKindEndDate() %></td>
      </tr>
      <%
        }
      %>
    </tbody>
  </table>
  <br><br>
  <table class=common cellpadding="5" cellspacing="1" >
    <thead>
      <tr>
        <td class="formtitle" colspan="5">历史三年交强XXXXXXXXXX信息</td>
      </tr>
    </thead>
    <tfoot>
    </tfoot>
    <tbody>
      <tr class=listtitle>
        <td width='16%'>XX公司</td>
        <td width='16%'>出XXXXX时间</td>
        <td width='16%'>赔款总金额</td>
        <td width='16%'>案件状态</td>
        <td width='16%'>出XXXXX经过</td>
      </tr>
      <%
        List carCIClaimList = queryCarRiskInfoSchema
          .getQueryCarClaimInfoSchemas();
        for (int i = 0; i < carCIClaimList.size(); i++) {
          QueryCarClaimInfoSchema carCIClaim = (QueryCarClaimInfoSchema)carCIClaimList.get(i);
          if ("CI".equals(carCIClaim.getBCFlag())) {
      %>
      <tr>
        <td width='16%' align="center"><%=carCIClaim.getCompanyId() %></td>
        <td width='16%' align="center"><%=carCIClaim.getEndCaseTime() %></td>
        <td width='16%' align="center"><%=carCIClaim.getClaimAmount() %></td>
        <td width='16%' align="center"><%=carCIClaim.getClaimStatus() %></td>
        <td width='16%' align="center"><a class="check" href="#" onclick="querySettlementDetail('<%=carCIClaim.getAccidentDesc()%>','CI')">查看</a></td>
      </tr>
      <%
          }
        }
      %>
    </tbody>
  </table>
  <br><br>
  <table class=common cellpadding="5" cellspacing="1" >
    <thead>
      <tr>
        <td class="formtitle" colspan="5">历史三年商业XXXXXXXXXX信息</td>
      </tr>
    </thead>
    <tfoot>
    </tfoot>
    <tbody>
      <tr class=listtitle>
        <td width='16%'>XX公司</td>
        <td width='16%'>出XXXXX时间</td>
        <td width='16%'>赔款总金额</td>
        <td width='16%'>案件状态</td>
        <td width='16%'>出XXXXX经过</td>
      </tr>
      <%
        List carBIClaimList = queryCarRiskInfoSchema
          .getQueryCarClaimInfoSchemas();
        for (int i = 0; i < carBIClaimList.size(); i++) {
          QueryCarClaimInfoSchema carBIClaim = (QueryCarClaimInfoSchema)carBIClaimList.get(i);
          if ("BI".equals(carBIClaim.getBCFlag())) {
      %>
      <tr>
        <td width='16%' align="center"><%=carBIClaim.getCompanyId() %></td>
        <td width='16%' align="center"><%=carBIClaim.getEndCaseTime() %></td>
        <td width='16%' align="center"><%=carBIClaim.getClaimAmount() %></td>
        <td width='16%' align="center"><%=carBIClaim.getClaimStatus() %></td>
        <td width='16%' align="center"><a class="check" href="#" onclick="querySettlementDetail('<%=carBIClaim.getAccidentDesc()%>','BI')">查看</a></td>
      </tr>
      <%
          }
        }
      %>
    </tbody>
  </table>
</body>
</html>