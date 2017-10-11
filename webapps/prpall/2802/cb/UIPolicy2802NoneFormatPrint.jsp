<%-- 初始化 --%>
<%@page import="com.sp.utility.SysConfig"%>
<%@include file="/2802/cb/UIPolicy2802NoneFormatPrintIni.jsp"%>
<%--见费出单 --%>
<%@include file="/commonship/cb/UIJFCDPrintTimes.jsp"%>
<%
	String strLocTypeCode = request.getParameter("LocTypeCode");
	String strSerialNo = request.getParameter("SerialNo");
%>

<html>
	<head>
		<title>农业XX种植业通用XXXXX种XX单打印</title>
		<link rel="stylesheet" type="text/css" href="/prpall/css/Standard.css">
	</head>
	<body style="font-size:9pt">
		<form id=fm>
			<%-- 标题部分 --%>
			<table width="96%" align="center" cellspacing="0" cellpadding="0"
				border="0">
				<tr>
					<td class=print_headtitle>
						<b>农业XX种植业通用XXXXX种XX单</b>
					</td>
				</tr>
				<tr>
					<td align=right class=print_head>
						XX号:
						<%=strPolicyNo%>
						<BR>
						<BR>
					</td>
				</tr>
				<tr>
					<td class=print_headheight>
						&nbsp;&nbsp;&nbsp;&nbsp;鉴于XX人已向本XX人XX<%=strKindName%>
						，并按本XX单约定交付XX费，XX人同意按照《<%=strKindName%>》及其附加XXXXX条款的约定承担XX责任和相关义务，特立本XX单为凭。与本XX有关的XX凭证、特约条款、批单、风XXXXX问询表、XX单和XX分户清单是本XX不可分割的组成部分。
					</td>
				</tr>
			</table>
			<%-- XX信息 --%>
			<table name="table1" border=1 width="96%" align="center"
				cellspacing="0" cellpadding="2" style="border-collapse: collapse"
				bordercolor="#111111" style="font-family:宋体; font-size:10pt;">
				<tr>
					<td width="100%" height=25 colspan="7" rowspan="1" align="left">
						&nbsp;&nbsp;XX人:&nbsp;&nbsp;&nbsp;&nbsp;
						<%=strAppliName%>
					</td>
				</tr>
				<tr>
					<td width="20%" height=25 colspan="1" rowspan="1" align="center">
						身份证号码
					</td>
					<td width="30%" height=25 colspan="2" align="left">
						<%=strIdentifyNumber%>
					</td>
					<td width="20%" height=25 colspan="1" align="center">
						电话
					</td>
					<td width="30%" height=25 colspan="3" align="left">
						<%=strAppliPhone%>
					</td>
				</tr>
				<tr>
					<td width="20%" height=25 colspan="1" rowspan="1" align="center">
						联系地址
					</td>
					<td width="30%" height=25 colspan="2" align="left">
						<%=strPostAddress%>
					</td>
					<td width="20%" height=25 colspan="1" align="center">
						邮编
					</td>
					<td width="30%" height=25 colspan="3" align="left">
						<%=strAppliPostCode%>
					</td>
				</tr>
				<tr>
					<td width="20%" height=25 colspan="1" rowspan="1" align="center">
						被XX人
					</td>
					<td width="80%" height=25 colspan="6" rowspan="1" align="left">
						<%=strInsuredName%>
					</td>
				</tr>
				<tr>
					<td width="20%" height=25 colspan="1" align="center">
						XX标的项目
					</td>
					<td width="15%" height=25 colspan="1" align="center">
						XX数量（亩）
					</td>
					<td width="15%" height=25 colspan="1" align="center">
						单位XX金额（元/亩）
					</td>
					<td width="20%" height=25 colspan="1" align="center">
						XX金额（元）
					</td>
					<td width="15%" height=25 colspan="1" align="center">
					<%--modify by zhangdongdong begin 20130329 农业XXXXX需求 --%>
						XX费率（‰）
					<%--modify by zhangdongdong end 20130329 农业XXXXX需求 --%>
					</td>
					<td width="15%" height=25 colspan="2" align="center">
						XX费（元）
					</td>
				</tr>
				<%
						for (i = 0; i < intCountMain; i++) {
						listItemKindMain = (ArrayList) listItemKindMains.get(i);
				%>
				<tr>
					<td width="20%" height=25 colspan="1" align="center">
						<%=listItemKindMain.get(0)%>
					</td>
					<td width="15%" height=25 colspan="1" align="center">
						<%=listItemKindMain.get(1)%>
					</td>
					<td width="15%" height=25 colspan="1" align="center">
						<%=listItemKindMain.get(2)%>
					</td>
					<td width="20%" height=25 colspan="1" align="center">
						<%=listItemKindMain.get(3)%>
					</td>
					<td width="15%" height=25 colspan="1" align="center">
						<%=listItemKindMain.get(4)%>
					</td>
					<td width="15%" height=25 colspan="2" align="center">
						<%=listItemKindMain.get(5)%>
					</td>
				</tr>
				<%
				}
				%>

				<tr style="display:<%=strDisPlayTr%>;">
					<td width="20%" height=25 colspan="1" align="center">
						a
					</td>
					<td width="15%" height=25 colspan="1" align="center">
						XX数量（亩）
					</td>
					<td width="15%" height=25 colspan="1" align="center">
						单位XX金额（元/亩）
					</td>
					<td width="20%" height=25 colspan="1" align="center">
						XX金额（元）
					</td>
					<td width="15%" height=25 colspan="1" align="center">
					<%--modify by zhangdongdong begin 20130329 农业XXXXX需求 --%>
						XX费率（‰）
					<%--modify by zhangdongdong end 20130329 农业XXXXX需求 --%>
					</td>
					<td width="15%" height=25 colspan="2" align="center">
						XX费（元）
					</td>
				</tr>
				<%
						for (i = 0; i < intCountSub; i++) {
						listItemKindSub = (ArrayList) listItemKindSubs.get(i);
				%>
				<tr>
					<td width="20%" height=25 colspan="1" align="center">
						<%=listItemKindSub.get(0)%>
					</td>
					<td width="15%" height=25 colspan="1" align="center">
					</td>
					<td width="15%" height=25 colspan="1" align="center">
					</td>
					<td width="15%" height=25 colspan="1" align="center">
						<%=listItemKindSub.get(1)%>
					</td>
					<td width="20%" height=25 colspan="1" align="center">
						<%=listItemKindSub.get(2)%>
					</td>
					<td width="15%" height=25 colspan="1" align="center">
						<%=listItemKindSub.get(3)%>
					</td>
				</tr>
				<%
				}
				%>


				<tr>
					<td width="20%" height=25 colspan="1" align="center">
						XX标的坐落地址
					</td>
					<td width="80%" height=25 colspan="6" align="left">
						<%=strAddress.toString()%>
					</td>
				</tr>
				<tr>
					<td width="20%" height=25 colspan="1" align="center">
						总XX金额&nbsp;&nbsp; &nbsp;
					</td>
					<td width="80%" height=25 colspan="6" align="center">
						&nbsp;&nbsp;&nbsp;&nbsp;
						<%=strSumAmountMain%>
						(大写)&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;￥
						<%=sumAmountMain%>
					</td>
				</tr>
				<tr>
					<td width="20%" height=25 colspan="1" align="center">
						总XX费&nbsp;&nbsp; &nbsp;
					</td>
					<td width="80%" height=25 colspan="6" align="center">
						&nbsp;&nbsp;&nbsp;&nbsp;
						<%=strSumPremiumMain%>
						(大写)&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;￥
						<%=sumPremiumMain%>
					</td>
				</tr>
			</table>
			<table name="table2" border=1 width="96%" align="center"
				cellspacing="0" cellpadding="0"
				style="border-collapse: collapse;display:<%=strDisPlay2%>;"
				bordercolor="#111111" style="font-family:宋体; font-size:10pt;"
				frame="vsides";>
				<tr>
					<td width="20%" height=25 colspan="1" align="center">
						政府XX补贴信息
					</td>
					<td width="12%" height=25 colspan="1" align="center">
						政策性类别
					</td>
					<td width="13%" height=25 colspan="1" align="center">
						XX补贴性质
					</td>
					<td width="20%" height=25 colspan="1" align="center">
						政府XX补贴类别
					</td>
					<td width="5%" height=25 colspan="1" align="center">
						币别
					</td>
					<td width="10%" height=25 colspan="1" align="center">
						补贴比率%
					</td>
					<td width="20%" height=25 colspan="1" align="center">
						补贴XX
					</td>
				</tr>
				<%
						for (i = 0; i < intCountSubsidy; i++) {
						listSubsidy = (ArrayList) listSubsidys.get(i);
				%>
				<tr>
					<td width="20%" height=25 colspan="1" align="center"></td>
					<td width="12%" height=25 colspan="1" align="center">
						<%=listSubsidy.get(0)%>
					</td>
					<td width="13%" height=25 colspan="1" align="center">
						<%=listSubsidy.get(1)%>
					</td>
					<td width="20%" height=25 colspan="1" align="center">
						<%=listSubsidy.get(2)%>
					</td>
					<td width="5%" height=25 colspan="1" align="center">
						<%=listSubsidy.get(3)%>
					</td>
					<td width="10%" height=25 colspan="1" align="center">
						<%=listSubsidy.get(4)%>
					</td>
					<td width="20%" height=25 colspan="1" align="center">
						<%=listSubsidy.get(5)%>
					</td>
				</tr>
				<%
				}
				%>
			</table>
			<table name="table3" border=1 width="96%" align="center"
				cellspacing="0" cellpadding="2" style="border-collapse: collapse"
				bordercolor="#111111" style="font-family:宋体; font-size:10pt;"
				frame="<%=strFrame3%>">
				<tr>
					<td width="20%" height=25 colspan="1" align="center">
						缴费日期&nbsp;&nbsp; &nbsp;
					</td>
					<td width="80%" height=25 colspan="6" align="left">
						&nbsp;&nbsp;&nbsp;&nbsp;
						<%=strPlanDate%>
					</td>
				</tr>
				<tr>
					<td width="20%" height=25 colspan="1" align="center">
						XX期间&nbsp;&nbsp; &nbsp;
					</td>
					<td width="80%" height=25 colspan="6" align="left">
						&nbsp;&nbsp;&nbsp;&nbsp;
						<%=strPeriod%>
					</td>
				</tr>
				<tr>
					<td width="20%" height=25 colspan="1" align="center">
						每次事故绝对免赔&nbsp;&nbsp; &nbsp;
					</td>
					<td width="80%" height=25 colspan="6" align="left">
						&nbsp;&nbsp;&nbsp;&nbsp;
						<%=strBufferTX001.toString()%>
<%
				if (strBufferTX002.length() != 0)
				{ 
%>
						<br>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<%=strBufferTX002.toString()%>
<%
			    }	
%>
					</td>
				</tr>
				<tr>
					<td width="20%" height=25 colspan="1" align="center">
						争议处理&nbsp;&nbsp; &nbsp;
					</td>
					<td width="80%" height=25 colspan="6" align="left">
						&nbsp;&nbsp;&nbsp;&nbsp;
						<%=strArgueSolution%>
					</td>
				</tr>
				<tr>
					<td width="20%" height=80 colspan="1" align="center">
						特别约定
					</td>
					<td width="80%" height=80 colspan="6" align="left">
						&nbsp;&nbsp;&nbsp;&nbsp;
						<%=strBuffer.toString()%>
					</td>
				</tr>
			</table>
			<table name="table4" border=1 width="96%" align="center"
				cellspacing="0" cellpadding="2" style="border-collapse: collapse"
				bordercolor="#111111"
				style="font-family:宋体;word-wrap;font-size:10.5pt;" colspan=4
				frame="<%=strFrame4%>" rules="none">
				<tr>
					<td colspan=2>
						&nbsp;&nbsp;&nbsp;&nbsp;签单公司：
						<%=strComName.toString()%>
					</td>
				</tr>
				<tr>
					<td colspan=2>
						&nbsp;&nbsp;&nbsp;&nbsp;签单公司地址：
						<%=strComAddressName%>
					</td>
				</tr>
				<tr>
					<td>
						&nbsp;&nbsp;&nbsp;&nbsp;邮政编码：
						<%=strComPostCode%>
					</td>
					<td align="center">
						XX公司（盖章）
					</td>
				</tr>
				<tr>
					<td colspan=2>
						&nbsp;&nbsp;&nbsp;&nbsp;服务电话：
						<%=strComPhoneNumber%>
					</td>
				</tr>
				<tr>
					<td colspan=2>
						&nbsp;&nbsp;&nbsp;&nbsp;签单日期：
						<%=strOperateDate%>
					</td>
				</tr>
				<tr class=print_title>
					<td colspan="2">
						<table width="100%" border="0">
							<tr class=print_title>
								<td class=print_title>
									&nbsp;&nbsp;&nbsp;&nbsp;复核：
									<%=strUnderwriteName%>
								</td>
								<td class=print_title>
									制单：
									<%=strOperatorName%>
								</td>
								<td class=print_title>
									经办人：
									<%=strHandlerName%>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
			<%-- include打印按钮 --%>
			<jsp:include page="/commonship/pub/UIPrintButton.jsp">
				<jsp:param name="BizNo" value="<%=strPolicyNo%>" />
				<jsp:param name="LocTypeCode" value="<%=strLocTypeCode%>" />
				<jsp:param name="SerialNo" value="<%=strSerialNo%>" />
			</jsp:include>
		</form>
	</body>
</html>
