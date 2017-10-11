<%--
****************************************************************************
* DESC       ����������ȷ�������ύ���ҳ��
* Author     : X
* CREATEDATE �� 2003-02-10
* MODIFYLIST ��   Name       Date            Reason/Contents
****************************************************************************
--%>

<%-- ������ --%>
<%@page errorPage="/UIErrorPage"%>

<%-- ����bean���� --%>
<%@page import="java.text.*"%>
<%@page import="com.sp.utility.SysConfig"%>
<%@page import="com.sp.utility.string.*"%>
<%@page import="com.sp.utility.error.*"%>
<%@page import="com.sp.indiv.sh.blsvr.*"%>
<%@page import="com.sp.indiv.sh.schema.*"%>
<%@page import="com.sp.indiv.sh.util.*"%>
<%@page import="com.sp.utility.UtiPower"%>
<%@page import="com.sp.platform.ui.control.action.UIPowerAction"%>
<%@page import="com.sp.platform.dto.domain.PrpDuserDto"%>
<%@page import="com.sp.sysframework.common.Constants"%>

<%
  PrpDuserDto user = (PrpDuserDto) (session.getAttribute("user"));

  
  BLValid blValid = null;
  PrpExtraBValidSchema   prpExtraBValidSchema  = null;
  BLPrpExtraBValidDriver blPrpExtraBValidDriver = null;
  PrpExtraBValidDriverSchema prpExtraBValidDriverSchema = null;
  BLPrpExtraBDemand blPrpExtraBDemand = new BLPrpExtraBDemand();
  int i = 0;
  String trClass = "";
  DecimalFormat decimalFormat = new DecimalFormat("0.00");
  String strUserCode = (String)session.getValue("UserCode");
  boolean bEnableValid = false; 
  
  
  String strValidNo = request.getParameter("ValidNo");
  String strExistValid = request.getParameter("ExistValid");
  
  
  if( strExistValid!=null )
  {
    if( strExistValid.equals("Yes") )
      blValid = (BLValid)request.getAttribute("Valid");
    else
      throw new UserException(-98,-2,"UIValidShow.jsp","����ExistValid����ȷ:"+strExistValid);
  }
  else if(strValidNo!=null)
  {
    blValid = new BLValid();
    blValid.getData(strValidNo);
  }
  else
  {
    throw new UserException(-98,-2,"UIValidShow.jsp","ȱ�ٲ���ValidNo��ExistValid");
  }
    
  prpExtraBValidSchema = blValid.getBLPrpExtraBValid().getArr(0);
  blPrpExtraBValidDriver = blValid.getBLPrpExtraBValidDriver();
  
  strValidNo = prpExtraBValidSchema.getValidNo();
  String strDemandNo    = prpExtraBValidSchema.getDemandNo();
  String strProposalNo  = prpExtraBValidSchema.getProposalNo();
  String strPolicyNo    = prpExtraBValidSchema.getPolicyNo();
  String strEnrollDate  = prpExtraBValidSchema.getEnrollDate();
  String strProductDate = prpExtraBValidSchema.getProductDate();
  int    SeatCount      = prpExtraBValidSchema.getSeatCount();
  double TonCount       = prpExtraBValidSchema.getTonCount();
  String strColor       = prpExtraBValidSchema.getColor();
  String strValidDate   = prpExtraBValidSchema.getValidDate();
  String strOwner       = prpExtraBValidSchema.getOwner();
  String strFactory     = prpExtraBValidSchema.getFactory();
  String strBrandName   = prpExtraBValidSchema.getBrandName();
  String strCarMarker1  = prpExtraBValidSchema.getCarMarker1();
  String strCarMarker2  = prpExtraBValidSchema.getCarMarker2();
  String strValidTime   = prpExtraBValidSchema.getValidTime();
  
  String strLicenseNo   = "";
  String strEngineNo    = "";
  String strFrameNo     = "";
  String strStartDate   = "";
  String strEndDate     = "";
  double dblAmount      = 0d;
  double dblPremium     = 0d;
  blPrpExtraBDemand.getInfo(strDemandNo);
  
  if(blPrpExtraBDemand.getSize()>0)
  {
    strLicenseNo   = blPrpExtraBDemand.getArr(0).getLicenseNo();
    strEngineNo    = blPrpExtraBDemand.getArr(0).getEngineNo ();
    strFrameNo     = blPrpExtraBDemand.getArr(0).getFrameNo  ();
    strStartDate   = blPrpExtraBDemand.getArr(0).getStartDate();
    strEndDate     = blPrpExtraBDemand.getArr(0).getEndDate  ();
    dblAmount      = blPrpExtraBDemand.getArr(0).getAmount   ();
    dblPremium     = blPrpExtraBDemand.getArr(0).getPremium  ();
  }

  


  
  String CheckCode     = SysConfig.getProperty("CHECKCODE_WRITE");
  String TaskCode      = SysConfig.getProperty("TASKCODE_TB");
  String strRiskCode   = (String)session.getValue("RiskCode");
  boolean bCheckPower  = false;
  
  
  bCheckPower   = UIPowerAction.checkPowerReturn(user,Constants.TASK_PROPOSAL_INSERT);

  if(strProposalNo.equals("")&&bCheckPower)
  {
    bEnableValid = true;
  }
  else
  {
    bEnableValid = false;
  }
%>

<html>
<head>
  <title>��������ȷ�������ύ���ҳ��</title>
  <%-- ���ú��� --%>
	<script src="/prpall/common/pub/UICommon.js"></script>
	<%-- ��ҳjs --%>
	<script src="/prpall/indiv/sh/UIValidShow.js"></script>
	<%-- ҳ����ʽ  --%>
	<link rel="stylesheet" type="text/css" href="/prpall/css/Standard.css">
</head>
<body class="interface" background="/prpall/common/images/bgCommon.gif">
<form name="fm">
  <table class="common" align="center">
    <tr>
      <td class=formtitle colspan="4">ȷ����������Ϣ</td>
    </tr>
    <tr>
      <td class=title>ȷ���룺</td>
      <td class=input><input name="ValidNo" class="readonly" readonly value="<%=strValidNo%>"></td>
      <td class=title>��ѯ�룺</td>
      <td class=input><input name="DemandNo" class="readonly" readonly value="<%=strDemandNo%>"></td>
    </tr>
    <tr>
      <td class=title>XX���ţ�</td>
      <td class=input><input name="ProposalNo" class="readonly" readonly value="<%=strProposalNo%>"></td>
      <td class=title>XX�ţ�</td>
      <td class=input><input name="PolicyNo" class="readonly" readonly value="<%=strPolicyNo%>"></td>
    </tr>
    <tr>
      <td class=title>���ƺ��룺</td>
      <td class=input><input name="LicenseNo" class="readonly" readonly value="<%=strLicenseNo%>"></td>
      <td class=title>�������ţ�</td>
      <td class=input><input name="EngineNo" class="readonly" readonly value="<%=strEngineNo%>"></td>
    </tr>
    <tr>
      <td class=title>��XXXXX���ڣ�</td>
      <td class=input><input name="StartDate" class="readonly" readonly value="<%=strStartDate%>"></td>
      <td class=title>��XXXXX���ڣ�</td>
      <td class=input><input name="EndDate" class="readonly" readonly value="<%=strEndDate%>"></td>
    </tr>
    <tr>
      <td class=title>����������XXXXX�޶</td>
      <td class=input><input name="Amount" class="readonly" readonly value="<%=decimalFormat.format(dblAmount)%>"></td>
      <td class=title>XX��</td>
      <td class=input><input name="Premium" class="readonly" readonly value="<%=decimalFormat.format(dblPremium)%>"></td>
    </tr>
    <tr>
      <td class=title>������ɫ��</td>
      <td class=input><input name="Color" class="readonly" readonly value="<%=ExtraUtil.translateCode("Color",strColor)%>"></td>
      <td class=title>��ʻ֤������</td>
      <td class=input><input name="Owner" class="readonly" readonly value="<%=strOwner%>"></td>
    </tr>
    <tr>
      <td class=title>�����������ڣ�</td>
      <td class=input><input name="EnrollDate" class="readonly" readonly value="<%=strEnrollDate%>"></td>
      <td class=title>�������ڣ�</td>
      <td class=input><input name="ProductDate" class="readonly" readonly value="<%=strProductDate%>"></td>
    </tr>
    <tr>
      <td class=title>�ؿ�������</td>
      <td class=input><input name="SeatCount" class="readonly" readonly value="<%=SeatCount%>"></td>
      <td class=title>��������</td>
      <td class=input><input name="TonCount" class="readonly" readonly value="<%=decimalFormat.format(TonCount)%>"></td>
    </tr>
    <tr>
      <td class=title>������Ч�ڣ�</td>
      <td class=input><input name="ValidDate" class="readonly" readonly value="<%=strValidDate%>"></td>
      <td class=title>���쳧���ƣ�</td>
      <td class=input><input name="Factory" class="readonly" readonly value="<%=strFactory%>"></td>
    </tr>
    <tr>
      <td class=title>�����ͺţ�</td>
      <td class=input><input name="BrandName" class="readonly" readonly value="<%=strBrandName%>"></td>
      <td class=title>����Ʒ��1��</td>
      <td class=input><input name="CarMarker1" class="readonly" readonly value="<%=strCarMarker1%>"></td>
    </tr>
    <tr>
      <td class=title>����Ʒ��2��</td>
      <td class=input><input name="CarMarker2" class="readonly" readonly value="<%=strCarMarker2%>"></td>
      <td class=title>ȷ��ʱ�䣺</td>
      <td class=input><input name="ValidTime" class="readonly" readonly value="<%=strValidTime%>"></td>
    </tr>
  </table>
    
  <table class="common" align="center">
    <tr>
      <td class=formtitle colspan=8>Υ�¼�ʻԱ��Ϣ�б�</td>
    </tr>
    <tr class="listtitle">
      <td>��ʻԱ����</td>
      <td>�Ա�</td>
      <td>��������</td>
      <td>����</td>
      <td>������֤����</td>
      <td>��ʻ֤��Ч��</td>
      <td>������</td>
      <td>׼�ݳ���</td>
    </tr>
<%
  for(i=0; i<blPrpExtraBValidDriver.getSize(); i++)
  {
    prpExtraBValidDriverSchema = blPrpExtraBValidDriver.getArr(i);
    if(i%2==0)
      trClass = "listodd";
    else
      trClass = "listeven";
%>
    <tr class="<%=trClass%>">
      <td><%=prpExtraBValidDriverSchema.getDriverName()%></td>
      <td><%=ExtraUtil.translateCode("Sex",prpExtraBValidDriverSchema.getSex())%></td>
      <td><%=prpExtraBValidDriverSchema.getBirthDate()%></td>
      <td><%=prpExtraBValidDriverSchema.getNationality()%></td>
      <td><%=prpExtraBValidDriverSchema.getAcceptLicenseDate()%></td>
      <td><%=prpExtraBValidDriverSchema.getReceiveLicenseDate()%></td>
      <td><%=prpExtraBValidDriverSchema.getValidDate()%></td>
      <td><%=ExtraUtil.translateCode("DrivingCarType",prpExtraBValidDriverSchema.getDrivingCarType())%></td>
    </tr>
<%
  }
%>
  </table>
  
  <table class="common" align="center" style="display:<%=bEnableValid?"":"none"%>">
    <tr>
      <td class=formtitle colspan=4>XX��¼��</td>
    </tr>
    <tr>
      <td class="title">XX����/XX�ţ�</td>
      <td class="input" colspan="3">
        <input name="CertiNo" class="common">
        <input type="hidden" name="BizType" value="<%=SysConfig.getProperty("BIZTYPE_PROPOSAL")%>">
      </td>
    </tr>
    <tr>
      <td class="button" type="button" ><input name="buttonNew" class="button" type="button"  alt="��XXXXX" value="��XXXXX" onclick="newProposal('<%=SysConfig.getProperty("EDITTYPE_NEW")%>')"></td>
      <td class="button" type="button" ><input name="buttonUpdate" class="button" type="button"  alt="�޸�" value="�޸�" onclick="updateProposal('<%=SysConfig.getProperty("EDITTYPE_UPDATE")%>')"></td>
      <td class="button" type="button" ><input name="buttonCopy" class="button" type="button"  alt="����" value="����"  onclick="copyProposal('<%=SysConfig.getProperty("EDITTYPE_COPY_PROPOSAL")%>')"></td>
      <td class="button" type="button" ><input name="buttonRenewal" class="button" type="button"  alt="��XXXXX" value="��XXXXX" onclick="renewalProposal('<%=SysConfig.getProperty("EDITTYPE_RENEWAL")%>')"></td>
    </tr>
  </table>
</form>
</body>
</html>
