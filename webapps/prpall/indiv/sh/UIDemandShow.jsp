<%--
****************************************************************************
* DESC       ：法定三者查询请求提交页面
* Author     : X
* CREATEDATE ：2004-02-11
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
--%>

<%-- 错误处理 --%>
<%@page errorPage="/UIErrorPage"%>

<%-- 引入bean类部分 --%>
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
      throw new UserException(-98,-2,"UIDemandShow.jsp","参数ExistDemand不正确:"+strExistDemand);
  }
  else if(strDemandNo!=null)
  {
    blDemand = new BLDemand();
    blDemand.getData(strDemandNo);
  }
  else
  {
    throw new UserException(-98,-2,"UIDemandShow.jsp","缺少参数DemandNo或ExistDemand");
  }

  prpExtraBDemandSchema = blDemand.getBLPrpExtraBDemand().getArr(0);
  blPrpExtraBDemandLoss = blDemand.getBLPrpExtraBDemandLoss();
  blPrpExtraBDemandPay  = blDemand.getBLPrpExtraBDemandPay();

  
  count = blPrpExtraBValid.getCount("SELECT COUNT(*) FROM PrpExtraBValid WHERE DemandNo='"+ prpExtraBDemandSchema.getDemandNo() +"'");
  if(count>0)
  {
    strTitle = "已确认";
    bHadConfirm = true;
  }
  else
  {
    strTitle = "未确认";
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
    <title>法定三者查询请求结果信息页面</title>
    <%-- 公用函数 --%>
	  <script src="/prpall/common/pub/UICommon.js"></script>
	  <%-- 页面样式  --%>
	  <link rel="stylesheet" type="text/css" href="/prpall/css/Standard.css">
	  <script language="javascript">
	  function printDemand()
	  {
       strURL = "/prpall/indiv/sh/UIDemandPrint.jsp?DemandNo=<%=prpExtraBDemandSchema.getDemandNo()%>";
       printWindow(strURL,"告知单打印");
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
      <td class=formtitle colspan="4">查询请求结果信息（<%=strTitle%>）</td>
    </tr>
    <tr>
      <td class="title">查询码：</td>
      <td class="input" colspan="3">
        <input name="DemandNo" class="readonly" readonly value="<%=prpExtraBDemandSchema.getDemandNo()%>">
      </td>
    </tr>
    <tr>
      <td class="title">XX单号：</td>
      <td class="input">
        <input name="ProposalNo" class="readonly" readonly value="<%=prpExtraBDemandSchema.getProposalNo()%>">
      </td>
      <td class="title">XX号：</td>
      <td class="input">
        <input name="PolicyNo" class="readonly" readonly value="<%=prpExtraBDemandSchema.getPolicyNo()%>">
      </td>
    </tr>
    <tr>
      <td class="title">号牌号码：</td>
      <td class="input">
        <input name="LicenseNo" class="readonly" readonly value="<%=prpExtraBDemandSchema.getLicenseNo()%>">
      </td>
      <td class="title">发动机号：</td>
      <td class="input">
        <input name="EngineNo" class="readonly" readonly value="<%=prpExtraBDemandSchema.getEngineNo()%>">
      </td>
    </tr>
    <tr>
      <td class="title">车架号：</td>
      <td class="input">
        <input name="FrameNo" class="readonly" readonly value="<%=prpExtraBDemandSchema.getFrameNo()%>">
      </td>
      <td class="title">车辆使用性质：</td>
      <td class="input">
        <input type="hidden" name="UseNatureCode" value="<%=prpExtraBDemandSchema.getUseNatureCode()%>">
        <input name="UseNatureName" class="readonly" readonly value="<%=blPrpDcode.translateCode("UseNature",prpExtraBDemandSchema.getUseNatureCode(),true)%>">
      </td>
    </tr>
    <tr>
      <td class="title">车辆种类：</td>
      <td class="input">
        <input type="hidden" name="CarKindCode" value="<%=prpExtraBDemandSchema.getCarKindCode()%>">
        <input name="CarKindName" class="readonly" readonly value="<%=blPrpDcode.translateCode("CarKind",prpExtraBDemandSchema.getCarKindCode(),true)%>">
      </td>
      <td class="title">核定载客：</td>
      <td class="input">
        <input name="SeatCount" class="readonly" readonly value="<%=prpExtraBDemandSchema.getSeatCount()%>">
      </td>
    </tr>
    <tr>
      <td class="title">总质量：</td>
      <td class="input">
        <input name="TonCount" class="readonly" readonly value="<%=decimalFormat.format(prpExtraBDemandSchema.getTonCount())%>">吨
      </td>
      <td class="title">排量/功率：</td>
      <td class="input">
        <input name="ExhaustScale" class="readonly" readonly value="<%=decimalFormat.format(prpExtraBDemandSchema.getExhaustScale())%>"> L/KW
      </td>
    </tr>
    <tr>
      <td class="title">起XXXXX日期：</td>
      <td class="input">
        <input name="StartDate" class="readonly" readonly value="<%=prpExtraBDemandSchema.getStartDate()%>">
      </td>
      <td class="title">终XXXXX日期：</td>
      <td class="input">
        <input name="EndDate" class="readonly" readonly value="<%=prpExtraBDemandSchema.getEndDate()%>">
      </td>
    </tr>
    <tr>
      <td class="title">第三者责任XXXXX限额：</td>
      <td class="input">
        <input name="Amount" class="readonly" readonly value="<%=decimalFormat.format(prpExtraBDemandSchema.getAmount())%>">
      </td>
      <td class="title">XX：</td>
      <td class="input">
        <input name="Premium" class="readonly" readonly value="<%=decimalFormat.format(prpExtraBDemandSchema.getPremium())%>">
      </td>
    </tr>
    <tr>
      <td class="title">基准XX：</td>
      <td class="input">
        <input name="BasePremium" class="readonly" readonly value="<%=decimalFormat.format(prpExtraBDemandSchema.getBasePremium())%>">
      </td>
      <td class="title">由于交通违章引起的XX变化：</td>
      <td class="input">
        <input name="LossPremium" class="readonly" readonly value="<%=decimalFormat.format(prpExtraBDemandSchema.getLossPremium())%>">
      </td>
    </tr>
    <tr>
      <td class="title">违章调整系数：</td>
      <td class="input">
        <input name="LossCoeff" class="readonly" readonly value="<%=decimalFormat.format(prpExtraBDemandSchema.getLossCoeff())%>">
      </td>
      <td class="title">XXXXX调整系数：</td>
      <td class="input">
        <input name="PayCoeff" class="readonly" readonly value="<%=decimalFormat.format(prpExtraBDemandSchema.getPayCoeff())%>">
      </td>
    </tr>
    <tr>
      <td class="title">无赔款优待：</td>
      <td class="input">
        <input name="AdjustRate" class="readonly" readonly value="<%=decimalFormat.format(prpExtraBDemandSchema.getAdjustRate())%>">
      </td>
      <td class="title">查询时间：</td>
      <td class="input">
        <input name="DemandTime" class="readonly" readonly value="<%=prpExtraBDemandSchema.getDemandTime()%>">
      </td>
    </tr>
  </table>

  <table class=common align=center>
    <tr>
      <td class=formtitle colspan="4">交通违章列表</td>
    </tr>
    <tr class=listtitle>
      <td width="15%">违章时间</td>
      <td width="15%">违章地点</td>
      <td width="60%">违章行为</td>
      <td width="10%">调整系数</td>
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
      <td class=formtitle colspan="5">XXXXX列表</td>
    </tr>
    <tr class=listtitle>
      <td>XX公司名称</td>
      <td>XX号</td>
      <td>赔案号</td>
      <td>出XXXXX时间</td>
      <td>赔款金额</td>
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
      <input name="buttonPrint" class="button" type="button"  alt="告知单打印" value="告知单打印" onclick="printDemand()">
    </td>
    <td class=button>
      <input name="buttonPrint" class="button" type="button"  alt="XX试算" value="XX试算" onclick="submitBeforeTBForm()">
    </td>
    <td class=button style="display:<%=bHadConfirm?"none":""%>">
      <input name="buttonSubmit" class="button" type="button"  alt="确认请求" value="确认请求" onclick="submitForm()">
    </td>
    <td class=button>
      <input name="buttonCancel" class="button" type="button"  alt="取消" value="取消" onclick="cancelForm()">
    </td>
  </tr>
  </table>
</form>
</body>
</html>
