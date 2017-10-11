<%--
****************************************************************************
* Description:		交强和商业XX关联关系上传(页面)
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
	document.getElementById("Des1").innerHTML="(将"+field.value+"平台返回的关联失败的信息重新补传)";
	document.getElementById("Des2").innerHTML="(将"+field.value+"所有的XX信息上传平台)";
}

function doTask1()
{
	var strOperateDate = trim(fm.operateDate.value);
	if(strOperateDate=="")
	{
		errorMessage("商业XXXXX录单日不能为空！");
		fm.operateDate.focus();
	}
	else
	{
		if(confirm("确定要将"+strOperateDate+"平台返回的关联失败的信息重新补传吗？\n信息量很大时，处理时间会较长，请不要关闭窗口！")==true)
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
		errorMessage("商业XXXXX录单日不能为空！");
		fm.operateDate.focus();
	}
	else
	{
		if(confirm("确定要将"+strOperateDate+"的所有XX信息上传平台吗？\n信息量很大时，处理时间会较长，请不要关闭窗口！")==true)
		{
			window.open("/prpall/admin/BiPolicyRelationFGTaskSubmit.jsp?operateDate="+strOperateDate+"&taskFlg=2");
		}
    	
    }
	fm.btnSubmit2.disabled=false;
}
</script>
	<head>
		<title>交强和商业XX关联关系上传</title>
		<script src="/prpall/commonship/pub/UICommon.js"></script>
	</head>
	<body>
		<form name="fm" action="" method="post">
			<table align="center">
				<tr>
					<td style="font-size:25px;"><b>---交强和商业XX关联关系上传---</b></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td align="center">
						商业XXXXX录单日：<input class="codecode" maxLength="10" name="operateDate"
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
						<input class="button" type="button" name="btnSubmit1" alt="失败信息补传 "
							value="失败信息补传 " onclick="this.disabled=true;doTask1();">
					</td>
				</tr>
				<tr>
					<td style="font-size:11px;" align="center" id="Des1">(将<%=strOperateDate%>平台返回的关联失败的信息重新补传)</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td class=button align="center">
						<input class="button" type="button" name="btnSubmit2" alt="全体信息上传"
							value="全体信息上传 " onclick="this.disabled=true;doTask2();">
					</td>
				</tr>
				<tr>
					<td style="font-size:11px;" align="center" id="Des2">(将<%=strOperateDate%>所有的XX信息上传平台)</td>
				</tr>
			</table>
		</form>
	</body>
</html>
