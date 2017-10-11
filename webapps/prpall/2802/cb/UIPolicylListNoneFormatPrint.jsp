<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.sp.prpall.blsvr.cb.BLPrpCmain"%>
<%@page import="com.sp.prpall.schema.PrpCmainSchema"%>
<%@page import="com.sp.prpall.schema.PrpAgriInfoSchema"%>
<%@page import="com.sp.prpall.schema.PrpPheadSchema"%>
<%@page import="com.sp.prpall.schema.PrpCnameSchema"%>
<%@page import="com.sp.prpall.blsvr.misc.BLPrpAgriInfo"%>
<%@page import="com.sp.prpall.blsvr.cb.*"%>
<%@page import="com.sp.prpall.blsvr.pg.*"%>
<%@page import="com.sp.utility.string.Str"%>
<%@page import="java.text.*"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+":
%>

<!DOCTYPE HTML PUBLIC "-
<html>
  <head>
    <base href="<%=basePath%>">
    
 <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>a</title>
<style type="text/css">
.STYLE8 {font-family: "宋体"; font-size: 15px; }
</style>
<script src="/prpall/commonship/pub/UICommon.js"></script>
<link rel="stylesheet" type="text/css" href="/prpall/css/Standard.css">
  </head>
  
   <h3 align="center">a</h3>
   <%
   String strBizNo = request.getParameter("BizNo");
	if(strBizNo!=null){
	  strBizNo = strBizNo.trim();
	}
    if("".equals(strBizNo)||strBizNo==null)
  {
    throw new Exception("无有效的业务号码");
  }
    
    BLPrpCname blPrpCname = new BLPrpCname();
    PrpCnameSchema prpCnameSchema = new PrpCnameSchema();
    BLPrpCmain blPrpCmain= new BLPrpCmain(); 
  	blPrpCmain.getData(strBizNo);
 	if (!(blPrpCmain.getSize()>0))
  {
     throw new Exception("XX号不存在！");
	  }else{
	      blPrpCname.query("PolicyNo = '"+strBizNo+"' order by age",0);
		  if(!(blPrpCname.getSize()>0)){
		      
	    	 throw new Exception("没有导入被XX人资料");
		  }else{
	  	prpCnameSchema = blPrpCname.getArr(0);
	  	}
  }
    %>
<table width="95%" border="0" align="center" cellspacing="0">
  <tr>
  	 <td width="40%"><span class="STYLE8">本分户标的清单为<%=strBizNo%>号XX的组成部分。</span></td>
    <td width="21%">&nbsp;</td>
    <td width="15%">&nbsp;</td>
    <td width="20%">&nbsp;</td>
  </tr>
  <tr>
    <td width="40%"><span class="STYLE8">投XX种：<%=prpCnameSchema.getRiskName().replaceAll("\\？", "")%></span></td>
    <td width="21%"><span class="STYLE8">XX作物：<%=prpCnameSchema.getKindName().replaceAll("\\？", "")%></span></td>
    <td width="15%">&nbsp;</td>
    <td width="20%">&nbsp;</td>
  </tr>
  <tr>
    <td width="40%"><span class="STYLE8">标的种植地点：<%=prpCnameSchema.getKindAdress().replaceAll("\\？", "")%></span></td>
    <td width="21%"><span class="STYLE8">单位XX金额（元/亩）：<%=new DecimalFormat("0.00").format(Double.parseDouble(prpCnameSchema.getUnitAmount()))%></span></td>
    <td width="15%"><span class="STYLE8">XX费率（%）：<%=new DecimalFormat("0.0000").format(Double.parseDouble(prpCnameSchema.getRate()))%></span></td>
    <td width="20%"><span class="STYLE8">单位XX费（元/亩）：<%=new DecimalFormat("0.00").format(Double.parseDouble(prpCnameSchema.getUnitPrice()))%></span></td>
  </tr>
</table><p/>
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
  for(int i=0;i<blPrpCname.getSize();i++){
  prpCnameSchema = blPrpCname.getArr(i);
  %>
  <tr>
    <td width="3%" class="STYLE8"><%=prpCnameSchema.getAge()%></td>
    <td width="8%" class="STYLE8"><%=prpCnameSchema.getInsuredName()%></td>
    <td width="12%" class="STYLE8"><%=prpCnameSchema.getIdentifyNumber()%></td>
    <td width="9%" class="STYLE8"><%=prpCnameSchema.getJobUnit()%></td>
    <td width="5%" class="STYLE8"><%=prpCnameSchema.getKindCode()%></td>
    <td width="9%" class="STYLE8"><%=prpCnameSchema.getItemDetailName().replaceAll("\\？", "")%></td>
    <td width="8%" class="STYLE8"><%=new DecimalFormat("0.00").format(Double.parseDouble(prpCnameSchema.getLandCoverArea()))%></td>
    <td width="8%" class="STYLE8"><%=new DecimalFormat("0.00").format(Double.parseDouble(prpCnameSchema.getSumLimit()))%></td>
    <td width="8%" class="STYLE8"><%=new DecimalFormat("0.00").format(Double.parseDouble(prpCnameSchema.getMonthWage()))%></td>
    <td width="13%" class="STYLE8"><%=prpCnameSchema.getJobName().replaceAll("\\？", "")%></td>
    <td width="12%" class="STYLE8"><%=prpCnameSchema.getBankNo().replaceAll("\\？", "")%></td>
  </tr>
  <%
}
   %>
  
</table>
   <%--包含打印按钮--%>
    <%--begin  add by zhouming 20070429  增加XX全打销号传参--%>
    <jsp:include page="/commonship/pub/UIPrintButton.jsp">
    </jsp:include>
    <%--end  add by zhouming 20070429--%>
  </body>
</html>
