<%-- ��ʼ�� --%>
<%@page import="com.sp.utility.SysConfig"%>
<%@include file="/2802/cb/UIPolicy2802NoneFormatPrintIni.jsp"%>
<%--���ѳ��� --%>
<%@include file="/commonship/cb/UIJFCDPrintTimes.jsp"%>
<%
	String strLocTypeCode = request.getParameter("LocTypeCode");
	String strSerialNo = request.getParameter("SerialNo");
%>

<html>
	<head>
		<title>ũҵXX��ֲҵͨ��XXXXX��XX����ӡ</title>
		<link rel="stylesheet" type="text/css" href="/prpall/css/Standard.css">
	</head>
	<body style="font-size:9pt">
		<form id=fm>
			<%-- ���ⲿ�� --%>
			<table width="96%" align="center" cellspacing="0" cellpadding="0"
				border="0">
				<tr>
					<td class=print_headtitle>
						<b>ũҵXX��ֲҵͨ��XXXXX��XX��</b>
					</td>
				</tr>
				<tr>
					<td align=right class=print_head>
						XX��:
						<%=strPolicyNo%>
						<BR>
						<BR>
					</td>
				</tr>
				<tr>
					<td class=print_headheight>
						&nbsp;&nbsp;&nbsp;&nbsp;����XX������XX��XX<%=strKindName%>
						��������XX��Լ������XX�ѣ�XX��ͬ�ⰴ�ա�<%=strKindName%>�����丽��XXXXX�����Լ���е�XX���κ��������������XX��Ϊƾ���뱾XX�йص�XXƾ֤����Լ�����������XXXXX��ѯ��XX����XX�ֻ��嵥�Ǳ�XX���ɷָ����ɲ��֡�
					</td>
				</tr>
			</table>
			<%-- XX��Ϣ --%>
			<table name="table1" border=1 width="96%" align="center"
				cellspacing="0" cellpadding="2" style="border-collapse: collapse"
				bordercolor="#111111" style="font-family:����; font-size:10pt;">
				<tr>
					<td width="100%" height=25 colspan="7" rowspan="1" align="left">
						&nbsp;&nbsp;XX��:&nbsp;&nbsp;&nbsp;&nbsp;
						<%=strAppliName%>
					</td>
				</tr>
				<tr>
					<td width="20%" height=25 colspan="1" rowspan="1" align="center">
						���֤����
					</td>
					<td width="30%" height=25 colspan="2" align="left">
						<%=strIdentifyNumber%>
					</td>
					<td width="20%" height=25 colspan="1" align="center">
						�绰
					</td>
					<td width="30%" height=25 colspan="3" align="left">
						<%=strAppliPhone%>
					</td>
				</tr>
				<tr>
					<td width="20%" height=25 colspan="1" rowspan="1" align="center">
						��ϵ��ַ
					</td>
					<td width="30%" height=25 colspan="2" align="left">
						<%=strPostAddress%>
					</td>
					<td width="20%" height=25 colspan="1" align="center">
						�ʱ�
					</td>
					<td width="30%" height=25 colspan="3" align="left">
						<%=strAppliPostCode%>
					</td>
				</tr>
				<tr>
					<td width="20%" height=25 colspan="1" rowspan="1" align="center">
						��XX��
					</td>
					<td width="80%" height=25 colspan="6" rowspan="1" align="left">
						<%=strInsuredName%>
					</td>
				</tr>
				<tr>
					<td width="20%" height=25 colspan="1" align="center">
						XX�����Ŀ
					</td>
					<td width="15%" height=25 colspan="1" align="center">
						XX������Ķ��
					</td>
					<td width="15%" height=25 colspan="1" align="center">
						��λXX��Ԫ/Ķ��
					</td>
					<td width="20%" height=25 colspan="1" align="center">
						XX��Ԫ��
					</td>
					<td width="15%" height=25 colspan="1" align="center">
					<%--modify by zhangdongdong begin 20130329 ũҵXXXXX���� --%>
						XX���ʣ��룩
					<%--modify by zhangdongdong end 20130329 ũҵXXXXX���� --%>
					</td>
					<td width="15%" height=25 colspan="2" align="center">
						XX�ѣ�Ԫ��
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
						XX������Ķ��
					</td>
					<td width="15%" height=25 colspan="1" align="center">
						��λXX��Ԫ/Ķ��
					</td>
					<td width="20%" height=25 colspan="1" align="center">
						XX��Ԫ��
					</td>
					<td width="15%" height=25 colspan="1" align="center">
					<%--modify by zhangdongdong begin 20130329 ũҵXXXXX���� --%>
						XX���ʣ��룩
					<%--modify by zhangdongdong end 20130329 ũҵXXXXX���� --%>
					</td>
					<td width="15%" height=25 colspan="2" align="center">
						XX�ѣ�Ԫ��
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
						XX��������ַ
					</td>
					<td width="80%" height=25 colspan="6" align="left">
						<%=strAddress.toString()%>
					</td>
				</tr>
				<tr>
					<td width="20%" height=25 colspan="1" align="center">
						��XX���&nbsp;&nbsp; &nbsp;
					</td>
					<td width="80%" height=25 colspan="6" align="center">
						&nbsp;&nbsp;&nbsp;&nbsp;
						<%=strSumAmountMain%>
						(��д)&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;��
						<%=sumAmountMain%>
					</td>
				</tr>
				<tr>
					<td width="20%" height=25 colspan="1" align="center">
						��XX��&nbsp;&nbsp; &nbsp;
					</td>
					<td width="80%" height=25 colspan="6" align="center">
						&nbsp;&nbsp;&nbsp;&nbsp;
						<%=strSumPremiumMain%>
						(��д)&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;��
						<%=sumPremiumMain%>
					</td>
				</tr>
			</table>
			<table name="table2" border=1 width="96%" align="center"
				cellspacing="0" cellpadding="0"
				style="border-collapse: collapse;display:<%=strDisPlay2%>;"
				bordercolor="#111111" style="font-family:����; font-size:10pt;"
				frame="vsides";>
				<tr>
					<td width="20%" height=25 colspan="1" align="center">
						����XX������Ϣ
					</td>
					<td width="12%" height=25 colspan="1" align="center">
						���������
					</td>
					<td width="13%" height=25 colspan="1" align="center">
						XX��������
					</td>
					<td width="20%" height=25 colspan="1" align="center">
						����XX�������
					</td>
					<td width="5%" height=25 colspan="1" align="center">
						�ұ�
					</td>
					<td width="10%" height=25 colspan="1" align="center">
						��������%
					</td>
					<td width="20%" height=25 colspan="1" align="center">
						����XX
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
				bordercolor="#111111" style="font-family:����; font-size:10pt;"
				frame="<%=strFrame3%>">
				<tr>
					<td width="20%" height=25 colspan="1" align="center">
						�ɷ�����&nbsp;&nbsp; &nbsp;
					</td>
					<td width="80%" height=25 colspan="6" align="left">
						&nbsp;&nbsp;&nbsp;&nbsp;
						<%=strPlanDate%>
					</td>
				</tr>
				<tr>
					<td width="20%" height=25 colspan="1" align="center">
						XX�ڼ�&nbsp;&nbsp; &nbsp;
					</td>
					<td width="80%" height=25 colspan="6" align="left">
						&nbsp;&nbsp;&nbsp;&nbsp;
						<%=strPeriod%>
					</td>
				</tr>
				<tr>
					<td width="20%" height=25 colspan="1" align="center">
						ÿ���¹ʾ�������&nbsp;&nbsp; &nbsp;
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
						���鴦��&nbsp;&nbsp; &nbsp;
					</td>
					<td width="80%" height=25 colspan="6" align="left">
						&nbsp;&nbsp;&nbsp;&nbsp;
						<%=strArgueSolution%>
					</td>
				</tr>
				<tr>
					<td width="20%" height=80 colspan="1" align="center">
						�ر�Լ��
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
				style="font-family:����;word-wrap;font-size:10.5pt;" colspan=4
				frame="<%=strFrame4%>" rules="none">
				<tr>
					<td colspan=2>
						&nbsp;&nbsp;&nbsp;&nbsp;ǩ����˾��
						<%=strComName.toString()%>
					</td>
				</tr>
				<tr>
					<td colspan=2>
						&nbsp;&nbsp;&nbsp;&nbsp;ǩ����˾��ַ��
						<%=strComAddressName%>
					</td>
				</tr>
				<tr>
					<td>
						&nbsp;&nbsp;&nbsp;&nbsp;�������룺
						<%=strComPostCode%>
					</td>
					<td align="center">
						XX��˾�����£�
					</td>
				</tr>
				<tr>
					<td colspan=2>
						&nbsp;&nbsp;&nbsp;&nbsp;����绰��
						<%=strComPhoneNumber%>
					</td>
				</tr>
				<tr>
					<td colspan=2>
						&nbsp;&nbsp;&nbsp;&nbsp;ǩ�����ڣ�
						<%=strOperateDate%>
					</td>
				</tr>
				<tr class=print_title>
					<td colspan="2">
						<table width="100%" border="0">
							<tr class=print_title>
								<td class=print_title>
									&nbsp;&nbsp;&nbsp;&nbsp;���ˣ�
									<%=strUnderwriteName%>
								</td>
								<td class=print_title>
									�Ƶ���
									<%=strOperatorName%>
								</td>
								<td class=print_title>
									�����ˣ�
									<%=strHandlerName%>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
			<%-- include��ӡ��ť --%>
			<jsp:include page="/commonship/pub/UIPrintButton.jsp">
				<jsp:param name="BizNo" value="<%=strPolicyNo%>" />
				<jsp:param name="LocTypeCode" value="<%=strLocTypeCode%>" />
				<jsp:param name="SerialNo" value="<%=strSerialNo%>" />
			</jsp:include>
		</form>
	</body>
</html>
