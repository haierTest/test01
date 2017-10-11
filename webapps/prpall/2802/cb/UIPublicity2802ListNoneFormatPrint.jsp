<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.sp.prpall.blsvr.tb.BLPrpTmain"%>
<%@page import="com.sp.prpall.schema.PrpTmainSchema"%>
<%@page import="com.sp.utility.string.Str"%>
<%@page import="com.sp.prpall.schema.PrpTinsuredSchema"%>
<%@page import="com.sp.prpall.blsvr.tb.*"%>
<%@page import="com.sp.prpall.schema.PrpTnameSchema"%>
<%@page import="java.text.*"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+":
String strProposalNo = request.getParameter("BizNo");
%>

<!DOCTYPE HTML PUBLIC "-
<html>
  <head>
    <base href="<%=basePath%>">	
    
 <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>b</title>
<style type="text/css">
.STYLE8 {font-family: "宋体"; font-size: 15px; }
</style>
<script src="/prpall/commonship/pub/UICommon.js"></script>
<link rel="stylesheet" type="text/css" href="/prpall/css/Standard.css">
  </head>
<body>  
<h3 align="center">f</h3>
<table width="95%" border="0" align="center" cellspacing="0">
<tr><td width="30%">&nbsp;</td><td width="45%">&nbsp;</td>
<td align="left" width="30%"><span class="STYLE8">No.<%=strProposalNo%></span>
</td></tr>
<tr>
<td colspan="3"><span class="STYLE8">
</span></td>
</tr>
</table>
   <%
   String strMessage = "";
   	if(strProposalNo!=null){
	  strProposalNo = strProposalNo.trim();
	}
    if("".equals(strProposalNo)||strProposalNo==null)
  {
    
    strMessage = "无有效的业务号码!";
  }
  
 BLPrpTmain blPrpTmain= new BLPrpTmain(); 
 BLPrpTname blPrpTname = new BLPrpTname();
 PrpTnameSchema prpTnameSchema = new PrpTnameSchema();
 
 blPrpTmain.getData(strProposalNo);
 if (!(blPrpTmain.getSize()>0))
  {
     strMessage = "XX单号不存在!";
  }else{
     blPrpTname.query("ProposalNo = '"+strProposalNo+"' order by age",0);
	  if(!(blPrpTname.getSize()>0)){
	      
    	 strMessage = "没有导入被XX人资料！";
	  }else{
	   prpTnameSchema = blPrpTname.getArr(0);
	  }
  }
  
  
    if(!(strMessage.equals("")))
  {
    %>
    <jsp:forward page="/commonship/pub/UIMessagePage.jsp">
      <jsp:param name="Picture" value="F"/>
      <jsp:param name="Content" value="<%=strMessage%>"/>
    </jsp:forward>
  <%
    return;
  }  
  %>  
<table width="95%" border="0" align="center" cellspacing="0">
  <tr>
    <td width="40%"><span class="STYLE8">投XX种：<%=prpTnameSchema.getRiskName().replaceAll("\\？", "") %></span></td>
    <td width="20%"><span class="STYLE8">XX作物：<%=prpTnameSchema.getKindName().replaceAll("\\？", "") %></span></td>
    <td width="15%">&nbsp;</td>
    <td width="20%">&nbsp;</td>
  </tr>
  <tr>
    <td width="40%"><span class="STYLE8">标的种植地点：<%=prpTnameSchema.getKindAdress().replaceAll("\\？", "")%></span></td>
    <td width="20%"><span class="STYLE8">单位XX金额（元/亩）：<%=new DecimalFormat("0.00").format(Double.parseDouble(prpTnameSchema.getUnitAmount()))%></span></td>
    <td width="15%"><span class="STYLE8">XX费率：<%=new DecimalFormat("0.0000").format(Double.parseDouble(prpTnameSchema.getRate()))%></span></td>
    <td width="20%"><span class="STYLE8">单位XX费（元/亩）：<%=new DecimalFormat("0.00").format(Double.parseDouble(prpTnameSchema.getUnitPrice()))%></span></td>
  </tr>
  <tr>
   <td width="40%"><span class="STYLE8">XX组织者（公章）：<%=prpTnameSchema.getAppliName().replaceAll("\\？", "")%></span></td>
   <td width="20%"><span class="STYLE8">&nbsp;</td>
   <td width="15%"><span class="STYLE8">&nbsp;</td>
   <td width="20%"><span class="STYLE8">&nbsp;</td>
  </tr>
</table><p></p>
<table name="table1" border=1 width="95%" align="center"
				cellspacing="0" cellpadding="2" style="border-collapse:collapse"
				bordercolor="#111111" style="font-family:宋体; font-size:10pt;">
  <tr>
    <td  width="3%" class="STYLE8">序号</td>
    <td  width="8%" class="STYLE8">XX人/被XX人姓名</td>
    <td  width="12%" class="STYLE8">被XX人组织机构代码/身份证号 </td>
    <td  width="9%" class="STYLE8">联系方式</td>
    <td  width="5%" class="STYLE8">地块序号</td>
    <td  width="9%" class="STYLE8">地块名称</td>
    <td  width="8%" class="STYLE8">地块XX面积（亩）</td>
    <td  width="8%" class="STYLE8">总XX费（元）</td>
    <td  width="8%" class="STYLE8">农户自交XX（元）</td>
    <td  width="13%" class="STYLE8">开户银行（明确到支行信息）</td>
    <td  width="12%" class="STYLE8">银行账号/一卡通号码</td>
  </tr>
  <%
  for(int i=0;i<blPrpTname.getSize();i++){
    prpTnameSchema = blPrpTname.getArr(i);
  %>
  <tr>
    <td width="3%" class="STYLE8"><%=prpTnameSchema.getAge().replaceAll("\\？", "")%></td>
    <td width="8%" class="STYLE8"><%=prpTnameSchema.getInsuredName().replaceAll("\\？", "")%></td>
    <td width="12%" class="STYLE8"><%=prpTnameSchema.getIdentifyNumber().replaceAll("\\？", "")%></td>
    <td width="9%" class="STYLE8"><%=prpTnameSchema.getJobUnit().replaceAll("\\？", "")%></td>
    <td width="5%" class="STYLE8"><%=prpTnameSchema.getKindCode().replaceAll("\\？", "")%></td>
    <td width="9%" class="STYLE8"><%=prpTnameSchema.getItemDetailName().replaceAll("\\？", "")%></td>
    <td width="8%" class="STYLE8"><%=new DecimalFormat("0.00").format(Double.parseDouble(prpTnameSchema.getLandCoverArea()))%></td>
    <td width="8%" class="STYLE8"><%=new DecimalFormat("0.00").format(Double.parseDouble(prpTnameSchema.getSumLimit()))%></td>
    <td width="8%" class="STYLE8"><%=new DecimalFormat("0.00").format(Double.parseDouble(prpTnameSchema.getMonthWage()))%></td>
    <td width="13%" class="STYLE8"><%=prpTnameSchema.getJobName().replaceAll("\\？", "")%></td>
    <td width="12%" class="STYLE8"><%=prpTnameSchema.getBankNo().replaceAll("\\？", "")%></td>
  </tr>
  <%
}
   %>
  
  </table>
    <%--包含打印按钮--%>
    <jsp:include page="/commonship/pub/UIPrintButton.jsp">
    </jsp:include>
  </body>
</html>
