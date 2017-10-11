<%-- 引入bean类部分 --%>
<%@page import="java.text.*"%>
<%@page import="com.sp.utility.*"%>
<%@page import="com.sp.utility.string.Date"%>
<%@page import="com.sp.utility.error.*"%>
<%@page import="com.sp.utility.string.*"%>
<%@page import="com.sp.prpall.pubfun.PubTools"%>
<%@page import="com.sp.utiall.blsvr.BLPrpDcode"%>
<%@page import="com.sp.indiv.sh.schema.*"%>
<%@page import="com.sp.indiv.sh.blsvr.*"%>
<%@page import="com.sp.indiv.sh.util.*"%>
<%
  
  String strDemandNo    = request.getParameter("PrintDemandNo");
  BLDemand blDemand = new BLDemand();
  PrpExtraBDemandSchema  prpExtraBDemandSchema  = null;
  BLPrpExtraBDemandLoss  blPrpExtraBDemandLoss = null;
  BLPrpExtraBDemandPay   blPrpExtraBDemandPay  = null;
  PrpExtraBDemandLossSchema prpExtraBDemandLossSchema = null;
  PrpExtraBDemandPaySchema  prpExtraBDemandPaySchema  = null;
  int i = 0;
  int intLossCount = 0;
  int intPayCount = 0;
   
  
  BLPrpDcode blPrpDcode = new BLPrpDcode();
  DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
  SimpleDateFormat dfStrToDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  SimpleDateFormat dfDateToStr = new SimpleDateFormat("yyyy/MM/dd");
  Date dateStart = null;
  Date dateEnd = null;
  Date dateNow = new Date();
  
  blDemand.getData(strDemandNo);
  if(blDemand.getBLPrpExtraBDemand().getSize()==0)
  {
    throw new UserException(-98,-2,"UIDemandPrint.jsp","XX查询码对应的数据不存在:"+strDemandNo);
  }
  prpExtraBDemandSchema = blDemand.getBLPrpExtraBDemand().getArr(0);
  blPrpExtraBDemandLoss = blDemand.getBLPrpExtraBDemandLoss();
  blPrpExtraBDemandPay  = blDemand.getBLPrpExtraBDemandPay();
  dateStart = new Date(prpExtraBDemandSchema.getStartDate());
  dateEnd = new Date(prpExtraBDemandSchema.getEndDate());
  intLossCount = blPrpExtraBDemandLoss.getSize();
  intPayCount = blPrpExtraBDemandPay.getSize();
%>

  <table width="98%" border="0" cellpadding="0" cellspacing="3">
    <tr>
      <td align="right">f<%=prpExtraBDemandSchema.getDemandNo()%><br><br></td>
    </tr>
    <tr>
      <td align="center" style="font-size:18pt;font-family:宋体;"><b>s</b></font><br></td>
    </tr>
    <tr>
      <td align="center" height="40" style="font-size:12pt;font-family:宋体;">
      <font style="font-size:16pt;font-family:宋体;"><b>f</b></font></td>
    </tr>
    <tr>
      <td><b>s</b><br><br></td>
    </tr>
    <tr>
      <td>f</td>
    </tr>
    <tr>
      <td>s<%=blPrpDcode.translateCode("CarKind",prpExtraBDemandSchema.getCarKindCode(),true)%></td>
    </tr>
    <tr>
      <td>f<%=blPrpDcode.translateCode("UseNature",prpExtraBDemandSchema.getUseNatureCode(),true)%></td>
    </tr>
    <tr>
      <td>s<%=decimalFormat.format(prpExtraBDemandSchema.getAmount())%>元<%if(prpExtraBDemandSchema.getAmount()>40000){%>　(ds)<%}%></td>
    </tr>
    <tr>
      <td>fd<%=dateStart.get(Date.YEAR)%>年<%=dateStart.get(Date.MONTH)%>月<%=dateStart.get(Date.DATE)%>日零点 至<%=dateEnd.get(Date.YEAR)%>年<%=dateEnd.get(Date.MONTH)%>月<%=dateEnd.get(Date.DATE)%>日时二十四点</td>
    </tr>
    <tr>
      <td>ds<%=decimalFormat.format(prpExtraBDemandSchema.getBasePremium())%>元<br><br></td>
    </tr>
    <tr>
      <td>fs<%=prpExtraBDemandSchema.getLicenseNo()%>df<%=prpExtraBDemandSchema.getEngineNo()%>s<%=prpExtraBDemandSchema.getFrameNo()%>f<br><br></td>
    </tr>
    <tr>
      <td>s<%=intLossCount%> 次</td>
    </tr>
    <tr>
      <td>
        <table width="100%" border="1" cellspacing="0" cellpadding="3" style="border-collapse: collapse" bordercolor="#111111">
          <tr> 
            <td width="10%" align="center">&nbsp;</td>
            <td width="15%" align="center"> f</td>
            <td width="30%" align="center"> d</td>
            <td width="45%" align="center"> d</td>
          </tr>
<%
        for(i=0; i<intLossCount || i<3; i++)
        {
          String strLossTime = "";
          String strLossAddress = "";
          String strLossAction = "";
          
          if(i<intLossCount)
          {
            prpExtraBDemandLossSchema = blPrpExtraBDemandLoss.getArr(i);
            strLossTime = prpExtraBDemandLossSchema.getLossTime();
            strLossAddress = prpExtraBDemandLossSchema.getLossAddress();
            strLossAction = prpExtraBDemandLossSchema.getLossAction();
            
            strLossTime = dfDateToStr.format(dfStrToDate.parse(strLossTime));
            strLossAction = ExtraUtil.translateCode("LossAction",strLossAction);
          }
%>
          <tr> 
            <td align="center"><%=i+1%></td>
            <td align="center"><%=strLossTime%></td>
            <td><%=strLossAddress%></td>
            <td><%=strLossAction%></td>
          </tr>
<%
        }
%>
        </table>
        f<%=decimalFormat.format(prpExtraBDemandSchema.getLossPremium())%>元
        <br>
        <br>
      </td>
    </tr>
    <tr>
      <td>f<%=intPayCount%> 次</td>
    </tr>
    <tr>
      <td>
        <table width="100%" border="1" cellspacing="0" cellpadding="3" style="border-collapse: collapse" bordercolor="#111111">
          <tr>
            <td width="10%" align="center">&nbsp;</td>
            <td width="30%" align="center">d</td>
            <td width="30%" align="center">2</td>
            <td width="30%" align="center">1</td>
          </tr>
<%
        for(i=0; i<intPayCount || i<3; i++)
        {
          String strLossTime = "";
          String strEndCaseTime = "";
          String strLossFee = "";
          
          if(i<intPayCount)
          {
            prpExtraBDemandPaySchema = blPrpExtraBDemandPay.getArr(i);
            strLossTime = prpExtraBDemandPaySchema.getLossTime();
            strEndCaseTime = prpExtraBDemandPaySchema.getEndCaseTime();
            strLossFee = decimalFormat.format(prpExtraBDemandPaySchema.getLossFee());
            strLossTime = dfDateToStr.format(dfStrToDate.parse(strLossTime));
            strEndCaseTime = dfDateToStr.format(dfStrToDate.parse(strEndCaseTime));
          }
%>
          <tr> 
            <td align="center"><%=i+1%></td>
            <td align="center"><%=strLossTime%></td>
            <td align="center"><%=strEndCaseTime%></td>
            <td ><%=strLossFee%></td>
          </tr>
<%
        }
%>
        </table>
        1<%=decimalFormat.format(prpExtraBDemandSchema.getPayCoeff()*100)%>%
        <br>
        <br>
      </td>
    </tr>
    <tr>
      <td>2<%=decimalFormat.format(prpExtraBDemandSchema.getAdjustRate()*100)%>%<br><br></td>
    </tr>
    <tr>
      <td>f<br>
        F<%=decimalFormat.format(prpExtraBDemandSchema.getPremium())%>d<br><br>
      </td>
    </tr>
    <tr>
      <td>f<br><br></td>
    </tr>
    <tr>
      <td align="right">
        f
      </td>
    </tr>
    <tr>
      <td align="right">日期：&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;月&nbsp;&nbsp;日&nbsp;&nbsp;&nbsp;&nbsp;<br><br></td>
    </tr>
    <tr>
      <td class="little">
        f
      </td>
    </tr>
  </table>