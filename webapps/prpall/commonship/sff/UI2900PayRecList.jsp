<%--
****************************************************************************
* DESC       �����������ڸ����ʵ���Ϣҳ��
* Author     : ���ٽ�
* CREATEDATE ��2008-06-26
* MODIFYLIST ��   Name       Date             Reason/Contents

****************************************************************************
--%>
<%@page import="com.sp.utility.SysConfig"%>
<%@page errorPage="/UIErrorPage"%>
<%@page import="com.sp.prpall.blsvr.jf.*"%>
<html>
	<head>
		<title>��ʾ�б�</title>
		<link rel="stylesheet" type="text/css" href="/prpall/css/Standard.css">
	</head>
	<body class="interface">
		<form name="fm" method="post" action="">
			<table class="common1" cellpadding="5" cellspacing="1" align="center">
				<tr class="listtitle">
					<td nowrap>
						���
					</td>
					<td nowrap>
						����
					</td>
					<td nowrap>
						�����ʺ�
					</td>
					<td nowrap>
						ת�˽��
					</td>
					<td nowrap>
						�Ƿ�ɹ�
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
							flag = "��";
						} else {
							flag = "��";
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
						<input class="button" type="button" alt=" ���� " value="�� ��"
							onclick="history.back()">
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>

