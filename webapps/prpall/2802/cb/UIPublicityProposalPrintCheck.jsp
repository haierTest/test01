<%@page contentType="text/xml;charset=GBK"%>
<%@page import="com.sp.utility.string.Str"%>
<%@page import="com.sp.prpall.blsvr.tb.BLPrpTmain"%>

<%!
  /**
   *@desc ��strXml�е�&����XML��ת�⣬�����ؽ����
   *@reason ���ַ�������&ʱXML��ʾ��������
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

