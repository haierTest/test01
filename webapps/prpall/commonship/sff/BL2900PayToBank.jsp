<%--
****************************************************************************
* DESC       ：富安居满期给付生成银行报盘页面(非2901)
* Author     : 张灵建
* CREATEDATE ：2008-06-17
* MODIFYLIST ：   Name       Date             Reason/Contents

****************************************************************************
--%>
<%@page errorPage="/UIErrorPage"%>
<%@page import="java.text.*"%>
<%@page import="com.sp.utility.string.*"%>
<%@page import="com.sp.utility.string.ChgDate"%>
<%@page import="com.sp.prpall.blsvr.jf.*"%>
<%@page import="com.sp.prpall.dbsvr.jf.*"%>
<%@page import="com.sp.prpall.schema.*"%>
<%@page import="com.sp.utiall.blsvr.*"%>
<%@page import="com.sp.utiall.dbsvr.DBPrpDcompany"%>
<%@page import="com.sp.utiall.dbsvr.DBPrpDcurrency"%>
<%@page import="com.sp.prpall.dbsvr.cb.DBPrpCmain"%>
<html>
  <head>
    <title>满期给付报盘</title>
  </head>
<%
  
  String strOperatorCode = (String)session.getValue("UserCode");
  String strCenterCode  = request.getParameter("CenterCode");
  String homeCenterCode  = request.getParameter("HomeCenterCode");
  String strOfferType  = request.getParameter("OfferType");
  String strPayRefNo = "";
  BLPrpJpayInvest blPrpJpayInvest = new BLPrpJpayInvest();
  DBPrpJpayInvest dbPrpJpayInvest = null;
  String check[]           = request.getParameterValues("check");
  String strContent ="";
  ChgDate chgDate = new ChgDate();
  String currentDate = chgDate.getCurrentTime("yyyy-MM-dd");
  String strYear = currentDate.substring(0, 4);
  
  if(homeCenterCode != null && homeCenterCode.equals("00000000")){
    strCenterCode = homeCenterCode;
  }
  
  for(int i=0;i<check.length;i++)
  { 
    String[] arrProcSeq    = request.getParameterValues("ProcSeq");
    String[] arrCertiType    = request.getParameterValues("CertiType");
    String[] arrCertiNo      = request.getParameterValues("CertiNo");
    String[] arrSerialNo     = request.getParameterValues("SerialNo");
    String[] arrPayRefReason = request.getParameterValues("PayRefReason");
    dbPrpJpayInvest = new DBPrpJpayInvest();
    dbPrpJpayInvest.getInfo(arrProcSeq[Integer.parseInt(check[i])],arrCertiNo[Integer.parseInt(check[i])],arrCertiType[Integer.parseInt(check[i])],arrSerialNo[Integer.parseInt(check[i])],arrPayRefReason[Integer.parseInt(check[i])]);
 	blPrpJpayInvest.setArr(dbPrpJpayInvest);
  }
  strPayRefNo = blPrpJpayInvest.payToBank(strOfferType,currentDate,strCenterCode,strOperatorCode);
  
  if(!strPayRefNo.equals(""))
  {
 	strContent="生成转帐单号："+strPayRefNo;
%> 	
 	<table>
<tr>
<td>
	<img src='/prpall/common/images/success.gif'>
</td>
<td class="common">
	<%=strContent%>
</td></tr>
<tr>
    <td align=left>
            <a class="check" href="BL2900PayToBankDownLoad.jsp?CenterCode=<%=strCenterCode%>&PayRefNo=<%=strPayRefNo%>&Year=<%=strYear%>&OfferType=<%=strOfferType%>"> 
            点击下载
    </td>
</tr>
<%
 }
%>