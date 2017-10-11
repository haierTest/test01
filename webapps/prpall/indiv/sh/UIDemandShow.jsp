<%--
****************************************************************************
* DESC       ���������߲�ѯ�����ύҳ��
* Author     : X
* CREATEDATE ��2004-02-11
* MODIFYLIST ��   Name       Date            Reason/Contents
****************************************************************************
--%>

<%-- ������ --%>
<%@page errorPage="/UIErrorPage"%>

<%-- ����bean�ಿ�� --%>
<%@page import="java.text.*"%>
<%@page import="com.sp.utility.*"%>
<%@page import="com.sp.utility.error.*"%>
<%@page import="com.sp.utility.string.*"%>
<%@page import="com.sp.utiall.blsvr.BLPrpDcode"%>
<%@page import="com.sp.indiv.sh.schema.*"%>
<%@page import="com.sp.indiv.sh.blsvr.*"%>
<%@page import="com.sp.indiv.sh.util.ExtraUtil"%>
<%
  
  String strDemandNo    = request.getParameter("DemandNo");
  String strExistDemand = request.getParameter("ExistDemand");
  BLDemand blDemand = null;
  PrpExtraBDemandSchema  prpExtraBDemandSchema  = null;
  BLPrpExtraBDemandLoss  blPrpExtraBDemandLoss = null;
  BLPrpExtraBDemandPay   blPrpExtraBDemandPay  = null;
  PrpExtraBDemandLossSchema prpExtraBDemandLossSchema = null;
  PrpExtraBDemandPaySchema  prpExtraBDemandPaySchema  = null;
  int i = 0;
  int count = 0;
  String trClass = "";
  boolean bHadConfirm = false; 
  BLPrpExtraBValid blPrpExtraBValid = new BLPrpExtraBValid();
  String strTitle = "";
  String strUserCode = (String)session.getValue("UserCode");
  boolean bSelfDemand = false; 

  
  BLPrpDcode blPrpDcode = new BLPrpDcode();
  DecimalFormat decimalFormat = new DecimalFormat("0.00");

  
  if( strExistDemand!=null )
  {
    if( strExistDemand.equals("Yes") )
      blDemand = (BLDemand)request.getAttribute("Demand");
    else
      throw new UserException(-98,-2,"UIDemandShow.jsp","����ExistDemand����ȷ:"+strExistDemand);
  }
  else if(strDemandNo!=null)
  {
    blDemand = new BLDemand();
    blDemand.getData(strDemandNo);
  }
  else
  {
    throw new UserException(-98,-2,"UIDemandShow.jsp","ȱ�ٲ���DemandNo��ExistDemand");
  }

  prpExtraBDemandSchema = blDemand.getBLPrpExtraBDemand().getArr(0);
  blPrpExtraBDemandLoss = blDemand.getBLPrpExtraBDemandLoss();
  blPrpExtraBDemandPay  = blDemand.getBLPrpExtraBDemandPay();

  
  count = blPrpExtraBValid.getCount("SELECT COUNT(*) FROM PrpExtraBValid WHERE DemandNo='"+ prpExtraBDemandSchema.getDemandNo() +"'");
  if(count>0)
  {
    strTitle = "��ȷ��";
    bHadConfirm = true;
  }
  else
  {
    strTitle = "δȷ��";
    bHadConfirm = false;
  }
  
  if(prpExtraBDemandSchema.getOperatorCode().equals(strUserCode))
  {
    bSelfDemand = true;
  }
  else
  {
    bSelfDemand = false;
  }
%>

<html>
  <head>
    <title>�������߲�ѯ��������Ϣҳ��</title>
    <%-- ���ú��� --%>
	  <script src="/prpall/common/pub/UICommon.js"></script>
	  <%-- ҳ����ʽ  --%>
	  <link rel="stylesheet" type="text/css" href="/prpall/css/Standard.css">
	  <script language="javascript">
	  function printDemand()
	  {
       strURL = "/prpall/indiv/sh/UIDemandPrint.jsp?DemandNo=<%=prpExtraBDemandSchema.getDemandNo()%>";
       printWindow(strURL,"��֪����ӡ");
	  }
	  function submitForm()
	  {
	    fm.submit();
	  }
	  function submitBeforeTBForm()
	  {
      var oldAction = fm.action;

      fm.action="/prpall/common/tb/UIProposalBeforeTBInput.jsp?DemandNo=<%=prpExtraBDemandSchema.getDemandNo()%>";
      fm.submit();
      fm.action=oldAction;
	  }
	  </script>
  </head>
<body class="interface" background="/prpall/common/images/bgCommon.gif">
<form name="fm" method="post" action="/prpall/indiv/sh/UIValidInputSubmit.jsp">
  <table class=common align=center>
    <tr>
      <td class=formtitle colspan="4">��ѯ��������Ϣ��<%=strTitle%>��</td>
    </tr>
    <tr>
      <td class="title">��ѯ�룺</td>
      <td class="input" colspan="3">
        <input name="DemandNo" class="readonly" readonly value="<%=prpExtraBDemandSchema.getDemandNo()%>">
      </td>
    </tr>
    <tr>
      <td class="title">XX���ţ�</td>
      <td class="input">
        <input name="ProposalNo" class="readonly" readonly value="<%=prpExtraBDemandSchema.getProposalNo()%>">
      </td>
      <td class="title">XX�ţ�</td>
      <td class="input">
        <input name="PolicyNo" class="readonly" readonly value="<%=prpExtraBDemandSchema.getPolicyNo()%>">
      </td>
    </tr>
    <tr>
      <td class="title">���ƺ��룺</td>
      <td class="input">
        <input name="LicenseNo" class="readonly" readonly value="<%=prpExtraBDemandSchema.getLicenseNo()%>">
      </td>
      <td class="title">�������ţ�</td>
      <td class="input">
        <input name="EngineNo" class="readonly" readonly value="<%=prpExtraBDemandSchema.getEngineNo()%>">
      </td>
    </tr>
    <tr>
      <td class="title">���ܺţ�</td>
      <td class="input">
        <input name="FrameNo" class="readonly" readonly value="<%=prpExtraBDemandSchema.getFrameNo()%>">
      </td>
      <td class="title">����ʹ�����ʣ�</td>
      <td class="input">
        <input type="hidden" name="UseNatureCode" value="<%=prpExtraBDemandSchema.getUseNatureCode()%>">
        <input name="UseNatureName" class="readonly" readonly value="<%=blPrpDcode.translateCode("UseNature",prpExtraBDemandSchema.getUseNatureCode(),true)%>">
      </td>
    </tr>
    <tr>
      <td class="title">�������ࣺ</td>
      <td class="input">
        <input type="hidden" name="CarKindCode" value="<%=prpExtraBDemandSchema.getCarKindCode()%>">
        <input name="CarKindName" class="readonly" readonly value="<%=blPrpDcode.translateCode("CarKind",prpExtraBDemandSchema.getCarKindCode(),true)%>">
      </td>
      <td class="title">�˶��ؿͣ�</td>
      <td class="input">
        <input name="SeatCount" class="readonly" readonly value="<%=prpExtraBDemandSchema.getSeatCount()%>">
      </td>
    </tr>
    <tr>
      <td class="title">��������</td>
      <td class="input">
        <input name="TonCount" class="readonly" readonly value="<%=decimalFormat.format(prpExtraBDemandSchema.getTonCount())%>">��
      </td>
      <td class="title">����/���ʣ�</td>
      <td class="input">
        <input name="ExhaustScale" class="readonly" readonly value="<%=decimalFormat.format(prpExtraBDemandSchema.getExhaustScale())%>"> L/KW
      </td>
    </tr>
    <tr>
      <td class="title">��XXXXX���ڣ�</td>
      <td class="input">
        <input name="StartDate" class="readonly" readonly value="<%=prpExtraBDemandSchema.getStartDate()%>">
      </td>
      <td class="title">��XXXXX���ڣ�</td>
      <td class="input">
        <input name="EndDate" class="readonly" readonly value="<%=prpExtraBDemandSchema.getEndDate()%>">
      </td>
    </tr>
    <tr>
      <td class="title">����������XXXXX�޶</td>
      <td class="input">
        <input name="Amount" class="readonly" readonly value="<%=decimalFormat.format(prpExtraBDemandSchema.getAmount())%>">
      </td>
      <td class="title">XX��</td>
      <td class="input">
        <input name="Premium" class="readonly" readonly value="<%=decimalFormat.format(prpExtraBDemandSchema.getPremium())%>">
      </td>
    </tr>
    <tr>
      <td class="title">��׼XX��</td>
      <td class="input">
        <input name="BasePremium" class="readonly" readonly value="<%=decimalFormat.format(prpExtraBDemandSchema.getBasePremium())%>">
      </td>
      <td class="title">���ڽ�ͨΥ�������XX�仯��</td>
      <td class="input">
        <input name="LossPremium" class="readonly" readonly value="<%=decimalFormat.format(prpExtraBDemandSchema.getLossPremium())%>">
      </td>
    </tr>
    <tr>
      <td class="title">Υ�µ���ϵ����</td>
      <td class="input">
        <input name="LossCoeff" class="readonly" readonly value="<%=decimalFormat.format(prpExtraBDemandSchema.getLossCoeff())%>">
      </td>
      <td class="title">XXXXX����ϵ����</td>
      <td class="input">
        <input name="PayCoeff" class="readonly" readonly value="<%=decimalFormat.format(prpExtraBDemandSchema.getPayCoeff())%>">
      </td>
    </tr>
    <tr>
      <td class="title">������Ŵ���</td>
      <td class="input">
        <input name="AdjustRate" class="readonly" readonly value="<%=decimalFormat.format(prpExtraBDemandSchema.getAdjustRate())%>">
      </td>
      <td class="title">��ѯʱ�䣺</td>
      <td class="input">
        <input name="DemandTime" class="readonly" readonly value="<%=prpExtraBDemandSchema.getDemandTime()%>">
      </td>
    </tr>
  </table>

  <table class=common align=center>
    <tr>
      <td class=formtitle colspan="4">��ͨΥ���б�</td>
    </tr>
    <tr class=listtitle>
      <td width="15%">Υ��ʱ��</td>
      <td width="15%">Υ�µص�</td>
      <td width="60%">Υ����Ϊ</td>
      <td width="10%">����ϵ��</td>
    </tr>
<%
  for(i=0; i<blPrpExtraBDemandLoss.getSize(); i++)
  {
    prpExtraBDemandLossSchema = blPrpExtraBDemandLoss.getArr(i);
    if(i%2==0)
      trClass = "listodd";
    else
      trClass = "listeven";
%>
    <tr class=<%=trClass%>>
      <td><%=prpExtraBDemandLossSchema.getLossTime()%></td>
      <td><%=prpExtraBDemandLossSchema.getLossAddress()%></td>
      <td align="left"><%=ExtraUtil.translateCode("LossAction",prpExtraBDemandLossSchema.getLossAction())%></td>
      <td><%=decimalFormat.format(prpExtraBDemandLossSchema.getCoeff())%></td>
    </tr>
<%
  }
%>
  </table>

  <table class=common align=center>
    <tr>
      <td class=formtitle colspan="5">XXXXX�б�</td>
    </tr>
    <tr class=listtitle>
      <td>XX��˾����</td>
      <td>XX��</td>
      <td>�ⰸ��</td>
      <td>��XXXXXʱ��</td>
      <td>�����</td>
    </tr>
<%
  for(i=0; i<blPrpExtraBDemandPay.getSize(); i++)
  {
    prpExtraBDemandPaySchema = blPrpExtraBDemandPay.getArr(i);
    if(i%2==0)
      trClass = "listodd";
    else
      trClass = "listeven";
%>
    <tr class=<%=trClass%>>
      <td><%=prpExtraBDemandPaySchema.getPayCompany()%></td>
      <td><%=prpExtraBDemandPaySchema.getPolicyNo()%></td>
      <td><%=prpExtraBDemandPaySchema.getCompensateNo()%></td>
      <td><%=prpExtraBDemandPaySchema.getLossTime()%></td>
      <td><%=decimalFormat.format(prpExtraBDemandPaySchema.getLossFee())%></td>
    </tr>
<%
  }
%>
  </table>

  <table class=common align=center style="display:<%=bSelfDemand?"":"none"%>">
   <tr>
    <td class=button>
      <input name="buttonPrint" class="button" type="button"  alt="��֪����ӡ" value="��֪����ӡ" onclick="printDemand()">
    </td>
    <td class=button>
      <input name="buttonPrint" class="button" type="button"  alt="XX����" value="XX����" onclick="submitBeforeTBForm()">
    </td>
    <td class=button style="display:<%=bHadConfirm?"none":""%>">
      <input name="buttonSubmit" class="button" type="button"  alt="ȷ������" value="ȷ������" onclick="submitForm()">
    </td>
    <td class=button>
      <input name="buttonCancel" class="button" type="button"  alt="ȡ��" value="ȡ��" onclick="cancelForm()">
    </td>
  </tr>
  </table>
</form>
</body>
</html>
