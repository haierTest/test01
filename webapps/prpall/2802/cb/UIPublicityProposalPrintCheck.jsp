<%@page contentType="text/xml;charset=GBK"%>
<%@page import="com.sp.utility.string.Str"%>
<%@page import="com.sp.prpall.blsvr.tb.BLPrpTmain"%>

<%!
  /**
   *@desc 将strXml中的&进行XML的转意，并返回结果　
   *@reason 当字符串中有&时XML显示会有问题
   */
  private String getXMLString(String strXml)
  {
    strXml = Str.replace(strXml,"&","&amp;");
    return strXml;
  }

 %>

<%
  String strProposalNo = request.getParameter("BizNo");
  BLPrpTmain blPrpTmain = new BLPrpTmain();
  String iWherePart = "proposalno='"+strProposalNo+"'";
  blPrpTmain.query(iWherePart);
  String strUnderWriteFlag = "";
  if(blPrpTmain.getSize()>0){
   strUnderWriteFlag = blPrpTmain.getArr(0).getUnderWriteFlag();
  }
  
  
  out.print("<?xml version=\"1.0\" encoding=\"GBK\"?>"); 
  out.print("<root>[");
  out.print(getXMLString(strUnderWriteFlag));
  out.print("]</root>");

 %>

