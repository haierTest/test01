
<%@page import="org.dom4j.*"%>
<%@page import="java.util.*"%>
<%@page import="com.sp.prpall.pubfun.prpallSSDB"%>
<%@page import="org.nutz.ssdb4j.spi.Response"%>
<%@page import="com.sp.interactive.interf.QuotationObjectiveInfoEncoder"%>

<%
String strResultSSDB="";
Document baowen=null;
String mapValue = request.getParameter("mapValue").trim();
String keyValue = request.getParameter("keyValue").trim();
if(mapValue!=null&&!(mapValue.trim().equals(""))&&keyValue!=null&&!(keyValue.trim().equals(""))){
	Response  ruleInfo=prpallSSDB.hget(mapValue, keyValue);
	try{	
		strResultSSDB=ruleInfo.asString();
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println(strResultSSDB);
		strResultSSDB=strResultSSDB.replace("<", "&lt;");
		strResultSSDB=strResultSSDB.replace(">", "&gt;");
		
	}
	if(strResultSSDB==null||"".equals(strResultSSDB)){
		strResultSSDB="��Ǹ��û�д�����¼�����ݿ����Ѿ���ɾ��";
	}

%>
<html>
<head>
<script language="javascript">
function check(){
	window.confirm("ȷ��Ҫɾ������Ϣ��");
   var  mapValue=fm.mapValue.value;
	var keyValue=fm.keyValue.value;
   window.location.href="/prpall/admin/delSsdbResult.jsp?mapValue="+mapValue+"&&keyValue="+keyValue;
	
}
function back(){
	var  mapValue=fm.mapValue.value;
	var keyValue=fm.keyValue.value;
  window.location.href="/prpall/admin/QuerySsdb.jsp?mapValue="+mapValue+"&&keyValue="+keyValue;
}
</script>
</head>
<form name="fm">
<body>
<table>
  	  	<tr>
        	<td>
        	<input type="hidden" name="mapValue" value="<%=mapValue%>"/> 
			<input type="hidden" name="keyValue" value="<%=keyValue%>"/> 
           <%=strResultSSDB%>
        	</td>
      	</tr>
      	<tr>
      	<td>
      <input type="button" align=center value="ɾ��" class="button"  onclick="check();"/>
      <input type="button" align=center value="����" class="button"  onclick="back();"/>
      	</td>
      	</tr>
  	</table>
</form>
</body>
</html>

