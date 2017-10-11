<%--
****************************************************************************
* DESC       ：富安居满期给付帐单信息页面
* Author     : 徐少将
* CREATEDATE ：2008-06-26
* MODIFYLIST ：   Name       Date             Reason/Contents

****************************************************************************
--%>
<%@page import="com.sp.utility.SysConfig"%>
<%@page errorPage="/UIErrorPage"%>
<%@page import="com.sp.prpall.blsvr.jf.*"%>
<html>
	<head>
		<title>显示列表</title>
		<link rel="stylesheet" type="text/css" href="/prpall/css/Standard.css">
	</head>
	<body class="interface">
		<form name="fm" method="post" action="">
			<table class="common1" cellpadding="5" cellspacing="1" align="center">
				<tr class="listtitle">
					<td nowrap>
						序号
					</td>
					<td nowrap>
						姓名
					</td>
					<td nowrap>
						银行帐号
					</td>
					<td nowrap>
						转账金额
					</td>
					<td nowrap>
						是否成功
					</td>
				</tr>
				<%
					
					BLPrpJpayInvest blPrpJpayInvest = new BLPrpJpayInvest();
					String strCenterCode = request.getParameter("CenterCode"); 
					String strPayRefNo = request.getParameter("PayRefNo"); 
					String strOfferType = request.getParameter("OfferType"); 
					String strYear = request.getParameter("Year"); 
					blPrpJpayInvest.ReadMethod(strOfferType, strCenterCode, strYear, strPayRefNo);
					for (int index = 0; index < blPrpJpayInvest.getSize() ; index++) {
						String flag = "";					
						if (blPrpJpayInvest.getArr(index).getPayRefUserEason().equals(
						"102")&&strOfferType.equals("102")
						||blPrpJpayInvest.getArr(index).getPayRefUserEason().equals(
						"102")&&strOfferType.equals("105")
						||blPrpJpayInvest.getArr(index).getPayRefUserEason().equals(
						"102")&&strOfferType.equals("103")
						||blPrpJpayInvest.getArr(index).getPayRefUserEason().equals(
						"102")&&strOfferType.equals("801")) {					
							flag = "是";
						} else {
							flag = "否";
						}
				%>
				<tr class="listodd">
					<td>
						<%=(index + 1)%>
					</td>
					<td>
						<%=blPrpJpayInvest.getArr(index).getAppliName()%>
					</td>
					<td>
						<%=blPrpJpayInvest.getArr(index).getPayAccountNo()%>
					</td>
					<td>
						<%=blPrpJpayInvest.getArr(index).getRealPayRefFee()%>
					</td>					
					<td>
						<%=flag%>
					</td>
					<%
					}
					%>
				</tr>
			</table>
			<table width="100%" align="center" cellpadding="5" cellspacing="1">
				<tr>
					<td align="center">
						<input class="button" type="button" alt=" 返回 " value="返 回"
							onclick="history.back()">
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>

