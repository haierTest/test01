<%--
****************************************************************************
* DESC       ����������ȷ�������ύҳ��
* Author     : X
* CREATEDATE �� 2003-02-10
* MODIFYLIST ��   Name       Date            Reason/Contents
****************************************************************************
--%>

<%--  ����bean����  --%>
<%@page errorPage="/UIErrorPage"%>
<%@page import="java.text.*"%>
<%@page import="com.sp.utility.SysConfig"%>
<%@page import="com.sp.utility.string.*"%>
<%@page import="com.sp.utility.error.*"%>
<%@page import="com.sp.utility.*"%>
<%@page import="com.sp.indiv.sh.blsvr.*"%>
<%@page import="com.sp.indiv.sh.schema.*"%>
<%@page import="com.sp.utiall.dbsvr.*"%>

<%
  
  PrpExtraBDemandSchema prpExtraBDemandSchema = new PrpExtraBDemandSchema();
  BLPrpExtraBDemand     blPrpExtraBDemand     = new BLPrpExtraBDemand();
  BLPrpExtraBValid      blPrpExtraBValid      = new BLPrpExtraBValid();
  int count = 0;
  int limitCount = Integer.parseInt(SysConfig.getProperty("QUERY_LIMIT_COUNT"));
  int i = 0;
  String strSql = "";
  String strCountSql = "";
  String strCondition = "";
  double Amount = 0d;
  double Premium = 0d;
  String DemandTime = "";
  String trClass = "";
  DecimalFormat decimalFormat = new DecimalFormat("0.00");
  String strShowType = request.getParameter("ShowType");
  boolean bShowOnly = false;
  boolean bHadConfirm = false; 
  String strUserCode = (String)session.getValue("UserCode");
  String strConfirmTitle = ""; 
  
  
  String strDemandNo         = request.getParameter("DemandNo"); 
  String strDemandNoSign     = request.getParameter("DemandNoSign");
  String strProposalNo       = request.getParameter("ProposalNo");
  String strProposalNoSign   = request.getParameter("ProposalNoSign");
  String strPolicyNo         = request.getParameter("PolicyNo");
  String strPolicyNoSign     = request.getParameter("PolicyNoSign");
  String strLicenseNo        = request.getParameter("LicenseNo");
  String strLicenseNoSign    = request.getParameter("LicenseNoSign");
  String strEngineNo         = request.getParameter("EngineNo");
  String strEngineNoSign     = request.getParameter("EngineNoSign");
  String strFrameNo          = request.getParameter("FrameNo");
  String strFrameNoSign      = request.getParameter("FrameNoSign");
  String strDemandTime       = request.getParameter("DemandTime");
  String strDemandTimeSign   = request.getParameter("DemandTimeSign");
  String strAmount           = request.getParameter("Amount");
  String strAmountSign       = request.getParameter("AmountSign");
  String strPremium          = request.getParameter("Premium");
  String strPremiumSign      = request.getParameter("PremiumSign");
  String strStartDate        = request.getParameter("StartDate");
  String strStartDateSign    = request.getParameter("StartDateSign");
  String strEndDate          = request.getParameter("EndDate");
  String strEndDateSign      = request.getParameter("EndDateSign");
    
  
  strCondition = " WHERE 1=1 ";
  strCondition += Str.convertString("DemandNo"  ,strDemandNo  ,strDemandNoSign  );
  strCondition += Str.convertString("ProposalNo",strProposalNo,strProposalNoSign);
  strCondition += Str.convertString("PolicyNo"  ,strPolicyNo  ,strPolicyNoSign  );
  strCondition += Str.convertString("LicenseNo" ,strLicenseNo ,strLicenseNoSign );
  strCondition += Str.convertString("EngineNo"  ,strEngineNo  ,strEngineNoSign  );
  strCondition += Str.convertString("FrameNo"   ,strFrameNo   ,strFrameNoSign   );
  strCondition += Str.convertDate  ("DemandTime",strDemandTime,strDemandTimeSign);
  strCondition += Str.convertNumber("Amount"    ,strAmount    ,strAmountSign    );
  strCondition += Str.convertNumber("Premium"   ,strPremium   ,strPremiumSign   );
  strCondition += Str.convertDate  ("StartDate" ,strStartDate ,strStartDateSign );
  strCondition += Str.convertDate  ("EndDate"   ,strEndDate   ,strEndDateSign   );
  if(strShowType!=null && strShowType.equals("ValidInput"))
  {
    
    strCondition += " AND OperatorCode='"+ strUserCode +"' ";
  }
  else
  {
    
    UtiPower utiPower = (UtiPower)session.getValue("Power");
    String strComCode  = (String)session.getValue("ComCode");
	  String CheckCode     = SysConfig.getProperty("CHECKCODE_READ");
	  String TaskCode      = SysConfig.getProperty("TASKCODE_TB");
	  DBPrpUserGrade dbPrpUserGrade = new DBPrpUserGrade();

    int level = dbPrpUserGrade.getInfo(strUserCode,"01",TaskCode,CheckCode);
    level = Integer.parseInt(Str.chgStrZero(dbPrpUserGrade.getValue()));
    
    switch(level)
    {
      case 0:  
        strCondition += " AND 1=0 ";
        break;
      case 9:  
        strCondition += " AND OperatorCode='"+ strUserCode +"' ";
        break;
      case 5:  
        strCondition += " AND ComCode='"+ strComCode +"' ";
        break;
      case 4:  
        strCondition += " AND SUBSTR(ComCode,1,6)='"+ strComCode.substring(0,6) +"' ";
        break;
      case 3:  
        strCondition += " AND SUBSTR(ComCode,1,4)='"+ strComCode.substring(0,4) +"' ";
        break;
      case 2:  
        strCondition += " AND SUBSTR(ComCode,1,2)='"+ strComCode.substring(0,2) +"' ";
        break;
      case 1:  
        break;
      default:
        strCondition += " AND OperatorCode='"+ strUserCode +"' ";
    }
  }

  
  strCountSql = "SELECT COUNT(*) FROM PrpExtraBDemand "+strCondition;
  count = blPrpExtraBDemand.getCount(strCountSql);
  if(count>limitCount)
  {
    throw new UserException(-98,-1003,"UIValidInputList.jsp",
      "��ѯ����� "+count+" ������������¼,������ϵͳ������( "+limitCount+" ��),����С��ѯ��Χ!");
  }
  
  
  strSql = "SELECT * FROM PrpExtraBDemand " + strCondition + " ORDER BY DemandTime";
  blPrpExtraBDemand.query(strSql);
  count = blPrpExtraBDemand.getSize();
  
  
  if(strShowType!=null && strShowType.equals("Show"))
  {
    bShowOnly = true;
    strConfirmTitle = "ȷ��״̬";
  }
  else
  {
    bShowOnly = false;
    strConfirmTitle = "XXȷ��";
  }
%>
<html>
  <head>
    <title>��������ȷ������¼��ҳ��</title>
    <%-- ���ú��� --%>
  	<script src="/prpall/common/pub/UICommon.js"></script>
  	<%-- ҳ����ʽ  --%>
  	<link rel="stylesheet" type="text/css" href="/prpall/css/Standard.css">
  	<script language="javascript">
	  function submitDemandNoForm(DemandNoSubmit)
	  {
	    
	    var varDemandNo = DemandNoSubmit;
	    fm.action="/prpall/indiv/sh/UIValidInputSubmit.jsp?DemandNo=" + varDemandNo;
	    fm.submit();
	  }
  	</script>
  </head>
  <form name="fm" action="/prpall/indiv/sh/UIValidInputSubmit.jsp" method=post>
  <body class="interface" background="/prpall/common/images/bgCommon.gif">
    <table class="common" align="center">
      <tr>
        <td class=formtitle colspan=7>��ѯ������</td>
      </tr>
      <tr class=listtitle>
        <td>��ѯ��</td>
        <td>ԤԼ��</td>
        <td>���ƺ���</td>
        <td>�޶�</td>
        <td>XX</td>
        <td>��ѯʱ��</td>
        <td><%=strConfirmTitle%></td>
      </tr>
<%
    for(i=0; i<count; i++)
    {
      prpExtraBDemandSchema = blPrpExtraBDemand.getArr(i);
      
      strDemandNo  = prpExtraBDemandSchema.getDemandNo();
      strLicenseNo = prpExtraBDemandSchema.getLicenseNo();
      Amount       = prpExtraBDemandSchema.getAmount();
      Premium      = prpExtraBDemandSchema.getPremium();
      DemandTime   = prpExtraBDemandSchema.getDemandTime();
      if(i%2==0)
        trClass = "listodd";
      else
        trClass = "listeven";
      strCountSql = "SELECT COUNT(*) FROM PrpExtraBValid WHERE DemandNo='"+ strDemandNo +"'";
      if( blPrpExtraBValid.getCount(strCountSql)>0 )
        bHadConfirm = true;
      else
        bHadConfirm = false;
%>
      <tr class="<%=trClass%>">
        <td name="tdDemandNo"><a class="check" href="/prpall/indiv/sh/UIDemandShow.jsp?DemandNo=<%=strDemandNo%>"><%=strDemandNo%></a></td>
        <td name="tdPreContractNo"><%=prpExtraBDemandSchema.getPrecontractNo()%></td>
        <td name="tdLicenseNo"><%=strLicenseNo%></td>
        <td name="tdAmount"><%=decimalFormat.format(Amount)%></td>   
        <td name="tdPremium"><%=decimalFormat.format(Premium)%></td>
        <td name="tdDemandTime"><%=DemandTime%></td>
        <td name="tdSubmitButton">
<%
          if(!bHadConfirm)
          {
            if(bShowOnly)
              out.print("δȷ��");
            else
            {
%>
              <input name="buttonSubmit" class="button" type="button"  alt="ȷ������" value="ȷ������" onclick="submitDemandNoForm('<%=strDemandNo%>')">
<%
            }
          }
          else
          {
            out.print("��ȷ��");
          }
%>
        </td>
      </tr>
<%
    }
%>
      <tr>
        <td class=input colspan=7 align=center>��<%=count%>������</td>
      </tr>
    </table>
    </form>
  </body>
</html>
