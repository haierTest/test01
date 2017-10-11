
<%@page import="org.dom4j.*"%>
<%@page import="java.util.*"%>
<%@page import="com.sp.prpall.pubfun.prpallSSDB"%>
<%@page import="org.nutz.ssdb4j.spi.Response"%>
<%@page import="com.sp.interactive.interf.QuotationObjectiveInfoEncoder"%>
<%@page import="com.sp.prpall.blsvr.cb.BLPrpBasicInfoCaChe" %>
<%@page import="com.sp.prpall.schema.PrpBasicInfoCaCheSchema" %>

<%
	String strResultSSDB = "";
	Document baowen = null;
	String mapValue = request.getParameter("mapValue").trim();
	String keyValue = request.getParameter("keyValue").trim();
	String serialno = request.getParameter("serialno").trim();
    String riskcode = request.getParameter("riskcode").trim();
    String flag = "";
    
    if(keyValue == null || "".equals(keyValue)){
    	if(serialno != null && !(serialno.trim().equals(""))
			&& riskcode != null && !(riskcode.trim().equals(""))){
			BLPrpBasicInfoCaChe blPrpBasicInfoCaChe = new BLPrpBasicInfoCaChe();
			PrpBasicInfoCaCheSchema prpBasicInfoCaCheSchema = new PrpBasicInfoCaCheSchema();
			blPrpBasicInfoCaChe.getData(serialno,riskcode);
			if(blPrpBasicInfoCaChe.getSize() > 0){
				prpBasicInfoCaCheSchema = blPrpBasicInfoCaChe.getArr(0);
				keyValue = prpBasicInfoCaCheSchema.getProposalXml();
				flag = "1";
			}else{
				flag = "2";
			}
    	}
    }

	if (mapValue != null && !(mapValue.trim().equals(""))
			&& keyValue != null && !(keyValue.trim().equals(""))) {
		Response ruleInfo = prpallSSDB.hget(mapValue, keyValue);
		System.out.println("ruleInfo=" + ruleInfo);
		try {
			strResultSSDB = ruleInfo.asString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(strResultSSDB);
		strResultSSDB = strResultSSDB.replace("<", "&lt;");
		strResultSSDB = strResultSSDB.replace(">", "&gt;");

		if (strResultSSDB == null || "".equals(strResultSSDB)) {
			if("1".equals(flag)){
				strResultSSDB = "流水号查询，存在KEY值，没有此条记录，内容可能已经被删除";
			}else{
				strResultSSDB = "KEY值查询，没有此条记录，内容可能已经被删除";
			}
		}
	}
	
	if (strResultSSDB == null || "".equals(strResultSSDB)) {
		if("1".equals(flag)){
			strResultSSDB = "流水号查询,数据库KEY值为空。";
		}else if("2".equals(flag)){
			strResultSSDB = "流水号有误？未能查询数据。";
		}else{
			strResultSSDB = "什么情况，程序有问题？？？";
		}
	}
%>
<html>
<head></head>
<body>
<%=strResultSSDB%>
</body>
</html>

