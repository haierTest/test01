<%--
****************************************************************************
* Description:		��ǿ����ҵXX������ϵ�ϴ�(ҳ��)
* Author:			  pengyuming
* Date:				  2015.5.8				  	  
****************************************************************************
--%>
<%@ page language="java" pageEncoding="GBK" %>
<%@ page import="com.sp.prpall.pubfun.PubTools" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Calendar" %>
<%@ page errorPage="/UIErrorPage"%>
<%
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	Date today = Calendar.getInstance().getTime();
	Date dateOperateDate = PubTools.getEndDay(today,-1);
	String strOperateDate = sdf.format(dateOperateDate);
%>
<html>
<script language="javascript">
function changeDes(field)
{
	document.getElementById("Des1").innerHTML="(��"+field.value+"ƽ̨���صĹ���ʧ�ܵ���Ϣ���²���)";
	document.getElementById("Des2").innerHTML="(��"+field.value+"���е�XX��Ϣ�ϴ�ƽ̨)";
}

function doTask1()
{
	var strOperateDate = trim(fm.operateDate.value);
	if(strOperateDate=="")
	{
		errorMessage("��ҵXXXXX¼���ղ���Ϊ�գ�");
		fm.operateDate.focus();
	}
	else
	{
		if(confirm("ȷ��Ҫ��"+strOperateDate+"ƽ̨���صĹ���ʧ�ܵ���Ϣ���²�����\n��Ϣ���ܴ�ʱ������ʱ���ϳ����벻Ҫ�رմ��ڣ�")==true)
		{
			window.open("/prpall/admin/BiPolicyRelationFGTaskSubmit.jsp?operateDate="+strOperateDate+"&taskFlg=1");
		}
    }
	fm.btnSubmit1.disabled=false;
}


function doTask2()
{
	var strOperateDate = trim(fm.operateDate.value);
	if(strOperateDate=="")
	{
		errorMessage("��ҵXXXXX¼���ղ���Ϊ�գ�");
		fm.operateDate.focus();
	}
	else
	{
		if(confirm("ȷ��Ҫ��"+strOperateDate+"������XX��Ϣ�ϴ�ƽ̨��\n��Ϣ���ܴ�ʱ������ʱ���ϳ����벻Ҫ�رմ��ڣ�")==true)
		{
			window.open("/prpall/admin/BiPolicyRelationFGTaskSubmit.jsp?operateDate="+strOperateDate+"&taskFlg=2");
		}
    	
    }
	fm.btnSubmit2.disabled=false;
}
</script>
	<head>
		<title>��ǿ����ҵXX������ϵ�ϴ�</title>
		<script src="/prpall/commonship/pub/UICommon.js"></script>
	</head>
	<body>
		<form name="fm" action="" method="post">
			<table align="center">
				<tr>
					<td style="font-size:25px;"><b>---��ǿ����ҵXX������ϵ�ϴ�---</b></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td align="center">
						��ҵXXXXX¼���գ�<input class="codecode" maxLength="10" name="operateDate"
							style="width: 90px" onchange="checkFullDate(this);changeDes(this)"
							value="<%=strOperateDate%>">
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
			</table>
			<table align="center">
				<tr>
					<td class=button align="center">
						<input class="button" type="button" name="btnSubmit1" alt="ʧ����Ϣ���� "
							value="ʧ����Ϣ���� " onclick="this.disabled=true;doTask1();">
					</td>
				</tr>
				<tr>
					<td style="font-size:11px;" align="center" id="Des1">(��<%=strOperateDate%>ƽ̨���صĹ���ʧ�ܵ���Ϣ���²���)</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td class=button align="center">
						<input class="button" type="button" name="btnSubmit2" alt="ȫ����Ϣ�ϴ�"
							value="ȫ����Ϣ�ϴ� " onclick="this.disabled=true;doTask2();">
					</td>
				</tr>
				<tr>
					<td style="font-size:11px;" align="center" id="Des2">(��<%=strOperateDate%>���е�XX��Ϣ�ϴ�ƽ̨)</td>
				</tr>
			</table>
		</form>
	</body>
</html>
