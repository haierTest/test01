<%--
****************************************************************************
* DESC       ：XXXXX已核批单转XX
* AUTHOR     ：刘瑶	
* CREATEDATE ：2013-01-29
* MODIFYLIST ：Name       Date            Reason/Contents
****************************************************************************
--%>
<%-- 错误处理 --%>
<%@page errorPage="/UIErrorPage"%>
<%@page contentType="text/xml;charset=GBK"%>
<%@page import="com.sp.prpall.jfcd.pg.PFeedBackNoCI"%>
<%@page import="org.dom4j.*"%>


<%-- 公用函数 --%>
<%

	String errorMsg = "";
	String strEndorseno = request.getParameter("endorseno");
	String strConfirmNo = request.getParameter("confirmNo");
		
	Document document = DocumentHelper.createDocument();
	Element packetElement = document.addElement("PACKET").addAttribute("type", "REQUEST").addAttribute("version", "1.0");
	Element headElement = packetElement.addElement("HEAD");
	headElement.addElement("REQUEST_TYPE").setText("05");
	headElement.addElement("RESPONSE_CODE").setText("1");
	headElement.addElement("ERROR_MESSAGE").setText("成功");
	Element bodyElement = packetElement.addElement("BODY");
	Element basePartElement = bodyElement.addElement("BASE_PART");
	basePartElement.addElement("AMEND_CONFIRM_NO").setText(strConfirmNo);
	
	String strResponse= document.asXML().replaceFirst("UTF-8", "GBK");
	
	try{
		PFeedBackNoCI pFeedBackNoCI = new PFeedBackNoCI();
		pFeedBackNoCI.startPGBJ(strEndorseno,strResponse);
	}catch(Exception e){
		e.printStackTrace();
		errorMsg = e.getMessage();
	}
	if("".equals(errorMsg)){
		errorMsg = "更新成功！";
	}
	
	out.print("<?xml version=\"1.0\" encoding=\"GBK\"?>"); 
	out.print("<root>");
	out.print(errorMsg);
	out.print("</root>");
%>