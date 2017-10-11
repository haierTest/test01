<%--
****************************************************************************
* DESC       ��ԤԼ���Ӳ�ѯ�����ʾҳ��
* Author     : X
* CREATEDATE ��2003-03-04
* MODIFYLIST ��   Name       Date            Reason/Contents
****************************************************************************
--%>

<%-- ������ҳ�� --%>
<%@page errorPage="/UIErrorPage"%>

<%-- ����bean�ಿ�� --%>
<%@page import="java.text.*"%>
<%@page import="java.util.*"%>
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
  String strUserCode = (String)session.getValue("UserCode");
  String strValidOnly = request.getParameter("ValidOnly");
  BLMotorcadeThird blMotorcadeThird = new BLMotorcadeThird();
  BLDemand[] arrBLDemand = null;

  int i = 0;
  boolean isChinese   = true;
  DecimalFormat decimalFormat = new DecimalFormat("#0.00");
  BLPrpDcode blPrpDcode = new BLPrpDcode();
  String strTitle = ""; 
    
  
  if(strUserCode==null)
  {
    throw new UserException(-98,-2,"UIMotorcadeDemandFetch.jsp",
      "�û���SessionֵΪ�գ������µ�½!");
  }
  
  
  if(strValidOnly!=null && strValidOnly.equals("Y")) 
  {
    Vector vecBLDemand = new Vector();
    BLPrpExtraBDemand blPrpExtraBDemand = new BLPrpExtraBDemand();
    String strSql = "SELECT a.* FROM PrpExtraBDemand a"
                  + " WHERE a.PreContractNo='"+ strPrecontractNo +"'"
                  + " AND a.OperatorCode='"+ strUserCode +"'"
                  + " AND (SELECT COUNT(*) FROM PrpExtraBValid WHERE DemandNo=a.DemandNo)=0";
    blPrpExtraBDemand.query(strSql);
    for(i=0; i<blPrpExtraBDemand.getSize(); i++)
    {
      BLDemand blDemand = new BLDemand();
      blDemand.getData( blPrpExtraBDemand.getArr(i).getDemandNo() );
      vecBLDemand.add( blDemand );
    }
    arrBLDemand = (BLDemand[])vecBLDemand.toArray(new BLDemand[vecBLDemand.size()]);
    strTitle = "����ȷ������¼��";
  }
  else 
  {
    
    arrBLDemand = blMotorcadeThird.fetchDemandResult(strUserCode,strPrecontractNo);
    strTitle = "��ȡ���Ӳ�ѯ�����ʾ";
  }
%>

<html>
<head>
<title>�������߲�ѯ��������Ϣҳ��</title>
<%-- ���ú��� --%>
<script src="/prpall/common/pub/UICommon.js"></script>
<%-- ���в������� --%>
<script src="/prpall/common/pub/UIMulLine.js"></script>
<%-- ҳ����ʽ  --%>
<link rel="stylesheet" type="text/css" href="/prpall/css/Standard.css">
<script language="javascript">
function printMotorcadeDemand()
{
  var oldAction = fm.action;
  var oldTarget = fm.target;
  var winPrint = printWindow("about:blank","PrintMotorcadeDemand");
  fm.action = "/prpall/indiv/sh/UIMotorcadeDemandPrint.jsp";
  fm.target = "PrintMotorcadeDemand";
  fm.submit();
  fm.action = oldAction;
  fm.target = oldTarget;
}
</script>
</head>
<body class="interface" background="/prpall/common/images/bgCommon.gif">
<form name="fm" method="post" action="/prpall/indiv/sh/UIMotorcadeValidInputSubmit.jsp">
  <input type="hidden" name="PrecontractNo" value="<%=strPrecontractNo%>">
  <input type="hidden" name="NumCar" value="<%=arrBLDemand.length%>">
  <table class="common" id="Motorcade" align=center>
    <tr>
      <td class=formtitle colspan=7><%=strTitle%>��ԤԼ��:<%=strPrecontractNo%>��</td>
    </tr>
    <tr>
      <td class="centertitle"></td>
      <td class="centertitle" style="width:25px">���</td>
      <td class="centertitle" style="width:60px">���ƺ���</td>
      <td class="centertitle" >�����޶�</td>
      <td class="centertitle" >����XX</td>
      <td class="centertitle" >��ѯ��</td>
      <td class="centertitle"></td>
    </tr>
    <tr style="display:none">
      <td><input name="button_Motorcade_Open" class="button" type="button"  alt="��ʾ��ҳ��" value="��ʾ��ҳ��" onclick="showSubPage(this,'span_Motorcade_SubPage')">
        <span id="span_Motorcade_SubPage" style="display:none;position:absolute;background-color:FFFFFF">
          <table class=sub style="width:270px" >
            <tr>
              <td class=formtitle colspan=2>������չ��Ϣ��ʾ</td>
            </tr>
            <tr>
              <td class=title>�������ţ�</td>
              <td class=input></td>
            </tr>
            <tr>
              <td class=title>���ܺţ�</td>
              <td class=input></td>
            </tr>
            <tr>
              <td class=title>����ʹ�����ʣ�</td>
              <td class=input></td>
            </tr>
            <tr>
              <td class=title>�������ࣺ</td>
              <td class=input></td>
            </tr>
            <tr>
              <td class=title>�˶��ؿͣ�</td>
              <td class=input></td>
            </tr>
            <tr>
              <td class=title>��������</td>
              <td class=input></td>
            </tr>
            <tr>
              <td class=title>����/���ʣ�</td>
              <td class=input></td>
            </tr>
            <tr>
              <td class=title>XX���ޣ�</td>
              <td class=input></td>
            </tr>
            <tr>
              <td colspan=2 align=center>
                  <input class="button" type="button"  name="button_Motorcade_Close" alt="ȷ��"  value="ȷ��"
                    onclick="hideSubPage(this,'span_Motorcade_SubPage')" style="width:125px">
              </td>
            </tr>
          </table>
        </span>
      </td>
      <td class=input><input class="readonly" readonly name="SerialNo" style="width:25px" ></td>
      <td class=input><input class="readonly" readonly name="LicenseNo" style="width:125px" ></td>
      <td class=input><input class="readonly" readonly name="Amount" style="width:90px" ></td>
      <td class=input><input class="readonly" readonly name="Premium" style="width:90px" ></td> 
      <td class=input><input class="readonly" readonly name="DemandNo" style="width:150px" ></td>
      <td><input type="checkbox" ></td>
    </tr>
<%
  for(i=0;i<arrBLDemand.length;i++)
  {
    if( !(arrBLDemand[i].getBLPrpExtraBDemand().getArr(0).getDemandNo()==null || 
          arrBLDemand[i].getBLPrpExtraBDemand().getArr(0).getDemandNo().equals(""))
      )
    {
%>
      <tr>
        <td><input name="button_Motorcade_Open" class="button" type="button"  alt="��ʾ��ҳ��" value="��ʾ��ҳ��" onclick="showSubPage(this,'span_Motorcade_SubPage')">
          <span id="span_Motorcade_SubPage" style="display:none;position:absolute;background-color:FFFFFF">
            <table class=sub style="width:270px" >
              <tr>
                <td class=formtitle colspan=2>������չ��Ϣ��ʾ</td>
              </tr>
              <tr>
                <td class=title>�������ţ�</td>
                <td class=input><%=arrBLDemand[i].getBLPrpExtraBDemand().getArr(0).getEngineNo()%></td>
              </tr>
              <tr>
                <td class=title>���ܺţ�</td>
                <td class=input><%=arrBLDemand[i].getBLPrpExtraBDemand().getArr(0).getFrameNo()%></td>
              </tr>
              <tr>
                <td class=title>����ʹ�����ʣ�</td>
                <td class=input><%=blPrpDcode.translateCode("UseNature",arrBLDemand[i].getBLPrpExtraBDemand().getArr(0).getUseNatureCode(),isChinese)%></td>
              </tr>
              <tr>
                <td class=title>�������ࣺ</td>
                <td class=input><%=blPrpDcode.translateCode("CarKind",arrBLDemand[i].getBLPrpExtraBDemand().getArr(0).getCarKindCode(),isChinese)%></td>
              </tr>
              <tr>
                <td class=title>�˶��ؿͣ�</td>
                <td class=input><%=arrBLDemand[i].getBLPrpExtraBDemand().getArr(0).getSeatCount()%>��</td>
              </tr>
              <tr>
                <td class=title>��������</td>
                <td class=input><%=decimalFormat.format(arrBLDemand[i].getBLPrpExtraBDemand().getArr(0).getTonCount())%>��</td>
              </tr>
              <tr>
                <td class=title>����/���ʣ�</td>
                <td class=input><%=decimalFormat.format(arrBLDemand[i].getBLPrpExtraBDemand().getArr(0).getExhaustScale())%>KW/L</td>
              </tr>
              <tr>
                <td class=title>XX���ޣ�</td>
                <td class=input>��<%=arrBLDemand[i].getBLPrpExtraBDemand().getArr(0).getStartDate()%>��ʱ����<%=arrBLDemand[i].getBLPrpExtraBDemand().getArr(0).getEndDate()%>��ʮ��ʱֹ</td>
              </tr>
              <tr>
                <td colspan=2 align=center>
                    <input class="button" type="button"  name="button_Motorcade_Close" alt="ȷ��" value="ȷ��"
                      onclick="hideSubPage(this,'span_Motorcade_SubPage')" style="width:125px">
                </td>
              </tr>
            </table>
          </span>
        </td>
        <td class=input><input class="readonly" readonly name="SerialNo" style="width:25px" value="<%=i+1%>"></td>
        <td class=input><input class="readonly" readonly name="LicenseNo" style="width:125px" value="<%=arrBLDemand[i].getBLPrpExtraBDemand().getArr(0).getLicenseNo()%>"></td>
        <td class=input><input class="readonly" readonly name="Amount" style="width:90px" value="<%=decimalFormat.format(arrBLDemand[i].getBLPrpExtraBDemand().getArr(0).getAmount())%>"></td>
        <td class=input><input class="readonly" readonly name="Premium" style="width:90px" value="<%=decimalFormat.format(arrBLDemand[i].getBLPrpExtraBDemand().getArr(0).getPremium())%>"></td> 
        <td class=input><input class="readonly" readonly name="DemandNo" style="width:150px" value="<%=arrBLDemand[i].getBLPrpExtraBDemand().getArr(0).getDemandNo()%>"></td>
        <td><input type="checkbox" name="CheckedDemandNo" value="<%=arrBLDemand[i].getBLPrpExtraBDemand().getArr(0).getDemandNo()%>" checked></td>
      </tr>
<%
    }
    else
    {
%>
      <tr>
        <td><input name="button_Motorcade_Open" class="button" type="button"  alt="��ʾ��ҳ��" value="��ʾ��ҳ��" onclick="showSubPage(this,'span_Motorcade_SubPage')">
          <span id="span_Motorcade_SubPage" style="display:none;position:absolute;background-color:FFFFFF">
            <table class=sub style="width:270px" >
              <tr>
                <td class=formtitle colspan=2>������չ��Ϣ��ʾ</td>
              </tr>
              <tr>
                <td class=title>�������ţ�</td>
                <td class=input><%=arrBLDemand[i].getBLPrpExtraBDemand().getArr(0).getEngineNo()%></td>
              </tr>
              <tr>
                <td class=title>���ܺţ�</td>
                <td class=input><%=arrBLDemand[i].getBLPrpExtraBDemand().getArr(0).getFrameNo()%></td>
              </tr>
              <tr>
                <td class=title>����ʹ�����ʣ�</td>
                <td class=input><%=blPrpDcode.translateCode("UseNature",arrBLDemand[i].getBLPrpExtraBDemand().getArr(0).getUseNatureCode(),isChinese)%></td>
              </tr>
              <tr>
                <td class=title>�������ࣺ</td>
                <td class=input><%=blPrpDcode.translateCode("CarKind",arrBLDemand[i].getBLPrpExtraBDemand().getArr(0).getCarKindCode(),isChinese)%></td>
              </tr>
              <tr>
                <td class=title>�˶��ؿͣ�</td>
                <td class=input><%=arrBLDemand[i].getBLPrpExtraBDemand().getArr(0).getSeatCount()%>��</td>
              </tr>
              <tr>
                <td class=title>��������</td>
                <td class=input><%=decimalFormat.format(arrBLDemand[i].getBLPrpExtraBDemand().getArr(0).getTonCount())%>��</td>
              </tr>
              <tr>
                <td class=title>����/���ʣ�</td>
                <td class=input><%=decimalFormat.format(arrBLDemand[i].getBLPrpExtraBDemand().getArr(0).getExhaustScale())%>KW/L</td>
              </tr>
              <tr>
                <td class=title>XX���ޣ�</td>
                <td class=input>��<%=arrBLDemand[i].getBLPrpExtraBDemand().getArr(0).getStartDate()%>��ʱ����<%=arrBLDemand[i].getBLPrpExtraBDemand().getArr(0).getEndDate()%>��ʮ��ʱֹ</td>
              </tr>
              <tr>
                <td colspan=2 align=center>
                    <input class="button" type="button"  name="button_Motorcade_Close" alt="ȷ��" value="ȷ��"
                      onclick="hideSubPage(this,'span_Motorcade_SubPage')" style="width:125px">
                </td>
              </tr>
            </table>
          </span>
        </td>
        <td class=input><input class="readonly" readonly name="SerialNo" style="width:25px" value="<%=i+1%>"></td>
        <td class=input><input class="readonly" readonly name="LicenseNo" style="width:125px" value="<%=arrBLDemand[i].getBLPrpExtraBDemand().getArr(0).getLicenseNo()%>"></td>
        <td class=input><input class="readonly" readonly name="Amount" style="width:90px" value="<%=decimalFormat.format(arrBLDemand[i].getBLPrpExtraBDemand().getArr(0).getAmount())%>"></td>
        <td class=input colspan=2><input class="readonly" readonly name="Remark" style="width:240px" value="<%=arrBLDemand[i].getBLPrpExtraBDemand().getArr(0).getRemarks()%>"></td> 
        <td></td>
      </tr>
<%
    }
  }
%>
  </table>
  <table class=common align=center>
    <tr>
      <td class=button>
        <input name="buttonPrint" class="button" type="button"  alt="��֪����ӡ" value="��֪����ӡ" onclick="printMotorcadeDemand();">
      </td>
      <td class=button>
        <input name="buttonSubmit" class="button" type="button"  alt="ȷ������" value="ȷ������" onclick="javascript:fm.submit();">
      </td>
      <td class=button>
        <input name="buttonCancel" class="button" type="button"  alt="ȡ��"  value="ȡ��" onclick="cancelForm()">
      </td>
    </tr>
  </table>
</form>
</body>
</html>
