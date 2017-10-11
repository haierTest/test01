<%--
****************************************************************************
* DESC       ��ԤԼ������������ʾҳ��
* Author     : X
* CREATEDATE ��2003-03-04
* MODIFYLIST ��   Name       Date            Reason/Contents
****************************************************************************
--%>

<%-- ������ҳ�� --%>
<%@page errorPage="/UIErrorPage"%>

<%-- ����bean�ಿ�� --%>
<%@page import="java.text.*"%>
<%@page import="com.sp.utiall.dbsvr.*"%>
<%@page import="com.sp.utiall.dbsvr.*"%>
<%@page import="com.sp.utiall.blsvr.*"%>
<%@page import="com.sp.utiall.blsvr.*"%>
<%@page import="com.sp.prpall.blsvr.tb.*"%>
<%@page import="com.sp.prpall.blsvr.cb.*"%>
<%@page import="com.sp.utility.*"%>
<%@page import="com.sp.utility.error.*"%>
<%@page import="com.sp.utility.string.*"%>
<%@page import="com.sp.utiall.blsvr.BLPrpDcode"%>
<%@page import="com.sp.indiv.sh.schema.*"%>
<%@page import="com.sp.indiv.sh.blsvr.*"%>
<%@page import="com.sp.indiv.sh.util.*"%>

<%
  
  String strPrecontractNo = request.getParameter("PrecontractNo");
  String strValidOnly = request.getParameter("ValidOnly");
  String strUserCode = (String)session.getValue("UserCode");
  String strComCode  = (String)session.getValue("ComCode");
  
  int i = 0;
  String strTitle = "";
  BLMotorcadeThird blMotorcadeThird = new BLMotorcadeThird();
  BLValid[] arrBLValid = null;
  
  
  
  if(strValidOnly!=null && strValidOnly.equals("Y")) 
  {
    Vector vecBLValid = new Vector();
    BLPrpExtraBValid blPrpExtraBValid = new BLPrpExtraBValid();
    String strSql = "SELECT a.* FROM PrpExtraBValid a"
                  + " WHERE a.PrecontractNo='"+ strPrecontractNo +"'"
                  + " AND a.OperatorCode='"+ strUserCode +"'"
                  + " AND (a.ProposalNo IS NULL OR length(a.ProposalNo)=0)";
    blPrpExtraBValid.query(strSql);
    for(i=0; i<blPrpExtraBValid.getSize(); i++)
    {
      BLValid blValid = new BLValid();
      blValid.getData( blPrpExtraBValid.getArr(i).getValidNo() );
      vecBLValid.add( blValid );
    }
    arrBLValid = (BLValid[])vecBLValid.toArray(new BLValid[vecBLValid.size()]);
    strTitle = "����ȷ�������ѯ";
  }
  else
  {
    arrBLValid = blMotorcadeThird.fetchValidResult(strComCode,strUserCode,strPrecontractNo);
    strTitle = "��ȡ����������";
  }
  
%>

<html>
  <head>
    <title>�������߲�ѯ��������Ϣҳ��</title>
    <%-- ���ú��� --%>
	  <script src="/prpall/common/pub/UICommon.js"></script>
	  <%-- ���в������� --%>
    <script src="/prpall/common/pub/UIMulLine.js"></script>
    <%-- ��ҳ�Ľű����� --%>
    <script src="/prpall/indiv/sh/UIMotorcadeValidFetch.js"></script>
	  <%-- ҳ����ʽ  --%>
	  <link rel="stylesheet" type="text/css" href="/prpall/css/Standard.css">
	</head>
	<body class="interface" background="/prpall/common/images/bgCommon.gif">
	  <form name="fm" method="post" action="/prpall/indiv/sh/UIMotorcadeValidFetchNext.jsp">
	  <input type="hidden" name="PrecontractNo" value="<%=strPrecontractNo%>">
	  <input type="hidden" name="NumCar" value="<%=arrBLValid.length%>">
	    
	    <table class="common" id="Motorcade" align=center style="width:640px">
	      <tr>
	        <td class=formtitle colspan=7><%=strTitle%>(ԤԼ��:<%=strPrecontractNo%>)</td>
	      </tr>
        <tr>
          <td class="centertitle" style="width:25px">���</td>
          <td class="centertitle" style="width:60px">���ƺ���</td>
          <td class="centertitle" >�����޶�</td>
          <td class="centertitle" >����XX</td>
          <td class="centertitle" >��ѯ��</td>
          <td class="centertitle" >ȷ����</td>
          <td class="centertitle"></td>
        </tr>
<%
  i = 0;
  boolean isChinese   = true;
  DecimalFormat decimalFormat = new DecimalFormat("#0.00");
  BLPrpDcode blPrpDcode = new BLPrpDcode();
  
  for(i=0;i<arrBLValid.length;i++)
  {
    BLPrpExtraBDemand blPrpExtraBDemand = new BLPrpExtraBDemand();
    PrpExtraBDemandSchema prpExtraBDemandSchema = new PrpExtraBDemandSchema();
    blPrpExtraBDemand.getInfo((arrBLValid[i].getBLPrpExtraBValid().getArr(0).getDemandNo()));
    prpExtraBDemandSchema = blPrpExtraBDemand.getArr(0);

    if(!(arrBLValid[i].getBLPrpExtraBValid().getArr(0).getValidNo().equals("")||arrBLValid[i].getBLPrpExtraBValid().getArr(0).getValidNo()==null))
    {
%>
          <tr>
            <td class=input><input class="readonly" readonly name="SerialNo" style="width:25px" value="<%=i+1%>"></td>
            <td class=input><input class="readonly" readonly name="LicenseNo" style="width:125px" value="<%=prpExtraBDemandSchema.getLicenseNo()%>"></td>
            <td class=input><input class="readonly" readonly name="Amount" style="width:90px" value="<%=decimalFormat.format(prpExtraBDemandSchema.getAmount())%>"></td>
            <td class=input><input class="readonly" readonly name="Premium" style="width:90px" value="<%=decimalFormat.format(prpExtraBDemandSchema.getPremium())%>"></td> 
            <td class=input><input class="readonly" readonly name="DemandNo" style="width:130px" value="<%=arrBLValid[i].getBLPrpExtraBValid().getArr(0).getDemandNo()%>"></td>
            <td class=input><input class="readonly" readonly name="ValidNo" style="width:130px" value="<%=arrBLValid[i].getBLPrpExtraBValid().getArr(0).getValidNo()%>"></td>
            <td><input type="checkbox" name="CheckedValidNo" value="<%=arrBLValid[i].getBLPrpExtraBValid().getArr(0).getValidNo()%>" checked></td>
          </tr>
<%
    }
    else
    {
%>
         <tr>
            <td class=input><input class="readonly" readonly name="SerialNo" style="width:25px" value="<%=i+1%>"></td>
            <td class=input><input class="readonly" readonly name="LicenseNo" style="width:125px" value="<%=prpExtraBDemandSchema.getLicenseNo()%>"></td>
            <td class=input><input class="readonly" readonly name="Amount" style="width:90px" value="<%=decimalFormat.format(prpExtraBDemandSchema.getAmount())%>"></td>
            <td class=input><input class="readonly" readonly name="Premium" style="width:90px" value="<%=decimalFormat.format(prpExtraBDemandSchema.getPremium())%>"></td> 
            <td class=input><input class="readonly" readonly name="DemandNo" style="width:120px" value="<%=arrBLValid[i].getBLPrpExtraBValid().getArr(0).getDemandNo()%>"></td>
            <td class=input colspan=2><input class="readonly" readonly name="Remark" style="width:150px" value="<%=arrBLValid[i].getBLPrpExtraBValid().getArr(0).getRemarks()%>"></td> 
          </tr>
<%
    }
  }
%>
      </table>
      <table class="common" align="center" style="width:640px">
        <tr>
          <td class=title>����¼�복�ӵĺ�ͬ�ţ�</td>
          <td class=input><input class=common name="ContractNo" maxlength="22"></td>
        </tr>
      </table>
      <input type="hidden" name="ContinueInputFlag" value="0">
      <table class="common" align="center">
        <tr>
          <td class="button" type="button" ><input class="button" type="button"  name="buttonNew" alt="�³���ҵ��" value="�³���ҵ��" onclick="submitForm()"></td>
          <td class="button" type="button" ><input class="button" type="button"  name="buttonContinue" alt="����¼�복��" value="����¼�복��" onclick="continueInputSubmitForm()"></td>
          <td class="button" type="button" ><input name="buttonRenewal" class="button" type="button"  alt="ȡ��" value="ȡ��" onclick="cancelForm()"></td>
        </tr>
      </table>
	  </form>
	</body>
</html>
