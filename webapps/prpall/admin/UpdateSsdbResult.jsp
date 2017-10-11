
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
		strResultSSDB="抱歉，没有此条记录，内容可能已经被删除";
	}
%>
<html>
<head>
<script language="javascript">
function save(){
	var  mapValue=fm.mapValue.value;
	var keyValue=fm.keyValue.value;
	var value=fm.resultValue.value;
	window.location.href="/prpall/admin/SaveSsdbResult.jsp?mapValue="+mapValue+"&&keyValue="+keyValue+"&&value="+value;
	
}
function back(){
	var  mapValue=fm.mapValue.value;
	var keyValue=fm.keyValue.value;
  window.location.href="/prpall/admin/QuerySsdb.jsp?mapValue="+mapValue+"&&keyValue="+keyValue;
}
</script>
</head>
<body>
<form name="fm">
<table >
  	  	<tr>
        	<td>
        	<input type="hidden" name="mapValue" value="<%=mapValue%>"/> 
			<input type="hidden" name="keyValue" value="<%=keyValue%>"/> 
           	<textarea name="resultValue" style="width:1000px;height:500px; overflow: auto"><%=strResultSSDB%></textarea>
        	</td>
      	</tr>
      	<tr>
      	<td>
      	<input type="button" align=center  value="XXXXX存"  class="button" onclick="save();" />
      	 <input type="button" align=center value="返回" class="button"  onclick="back();"/>
      	</td>
      	</tr>
  	</table>

</form>

</body>
</html>

