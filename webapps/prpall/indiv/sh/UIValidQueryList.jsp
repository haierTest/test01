<%--
****************************************************************************
* DESC       ：法定三者确认请求查询页面
* Author     : X
* CREATEDATE ： 2003-02-12
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
--%>
<%--  引入bean部分  --%>
<%@page errorPage="/UIErrorPage"%>
<%@page import="com.sp.utility.SysConfig"%>
<%@page import="com.sp.utility.string.*"%>
<%@page import="com.sp.utility.error.*"%>
<%@page import="com.sp.utility.UtiPower"%>
<%@page import="com.sp.utiall.blsvr.BLPrpDuser"%>
<%@page import="com.sp.indiv.sh.blsvr.*"%>
<%@page import="com.sp.indiv.sh.schema.*"%>
<%@page import="com.sp.utiall.dbsvr.*"%>

<%
  
  
  BLPrpExtraBValid  blPrpExtraBValid        = new BLPrpExtraBValid();
  BLPrpExtraBDemand blPrpExtraBDemand       = new BLPrpExtraBDemand();
  PrpExtraBValidSchema prpExtraBValidSchema = null;
  int count = 0;
  int limitCount = Integer.parseInt(SysConfig.getProperty("QUERY_LIMIT_COUNT"));
  int i = 0;
  String trClass = "";
  String strSql = "";
  String strCountSql = "";
  String strCondition = "";
  BLPrpDuser blPrpDuser = new BLPrpDuser();
  
  String strDemandNo   = request.getParameter("DemandNo");
  String strValidNo    = request.getParameter("ValidNo");
  String strProposalNo = request.getParameter("ProposalNo");
  String strPolicyNo   = request.getParameter("PolicyNo");
  String strLicenseNo  = request.getParameter("LicenseNo");
  String strEngineNo   = request.getParameter("EngineNo");
  String strFrameNo    = request.getParameter("FrameNo");
  String strValidTime  = request.getParameter("ValidTime");
  String strStartDate  = request.getParameter("StartDate");
  String strEndDate    = request.getParameter("EndDate");
  
  String strDemandNoSign   = request.getParameter("DemandNoSign");
  String strValidNoSign    = request.getParameter("ValidNoSign");
  String strProposalNoSign = request.getParameter("ProposalNoSign");
  String strPolicyNoSign   = request.getParameter("PolicyNoSign");
  String strLicenseNoSign  = request.getParameter("LicenseNoSign");
  String strEngineNoSign   = request.getParameter("EngineNoSign");
  String strFrameNoSign    = request.getParameter("FrameNoSign");
  String strValidTimeSign  = request.getParameter("ValidTimeSign");
  String strStartDateSign  = request.getParameter("StartDateSign");
  String strEndDateSign    = request.getParameter("EndDateSign");
  
  
  strCondition = " WHERE a.DemandNo=b.DemandNo ";
  strCondition += Str.convertString("a.DemandNo"  ,strDemandNo  ,strDemandNoSign   );
  strCondition += Str.convertString("a.ValidNo"   ,strValidNo   ,strValidNoSign    );
  strCondition += Str.convertString("a.ProposalNo",strProposalNo,strProposalNoSign );
  strCondition += Str.convertString("a.PolicyNo"  ,strPolicyNo  ,strPolicyNoSign   );
  strCondition += Str.convertString("b.LicenseNo" ,strLicenseNo ,strLicenseNoSign  );
  strCondition += Str.convertString("b.EngineNo"  ,strEngineNo  ,strEngineNoSign   );
  strCondition += Str.convertString("b.FrameNo"   ,strFrameNo   ,strFrameNoSign    );
  strCondition += Str.convertDate  ("a.ValidTime" ,strValidTime ,strValidTimeSign );
  strCondition += Str.convertDate  ("b.StartDate" ,strStartDate ,strStartDateSign  );
  strCondition += Str.convertDate  ("b.EndDate"   ,strEndDate   ,strEndDateSign    );
  
  
  UtiPower utiPower = (UtiPower)session.getValue("Power");
  String strUserCode = (String)session.getValue("UserCode");
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
      strCondition += " AND a.OperatorCode='"+ strUserCode +"' ";
      break;
    case 5:  
      strCondition += " AND a.ComCode='"+ strComCode +"' ";
      break;
    case 4:  
      strCondition += " AND SUBSTR(a.ComCode,1,6)='"+ strComCode.substring(0,6) +"' ";
      break;
    case 3:  
      strCondition += " AND SUBSTR(a.ComCode,1,4)='"+ strComCode.substring(0,4) +"' ";
      break;
    case 2:  
      strCondition += " AND SUBSTR(a.ComCode,1,2)='"+ strComCode.substring(0,2) +"' ";
      break;
    case 1:  
      break;
    default:
      strCondition += " AND a.OperatorCode='"+ strUserCode +"' ";
  }

  
  strCountSql = "SELECT COUNT(a.ValidNo) FROM PrpExtraBValid a,PrpExtraBDemand b "+strCondition;
  count = blPrpExtraBValid.getCount(strCountSql);
  if(count>limitCount)
  {
    throw new UserException(-98,-1003,"UIValidQueryList.jsp",
      "查询结果有 "+count+" 条满足条件记录,超过了系统限制数( "+limitCount+" 条),请缩小查询范围!");
  }

  
  strSql = "SELECT a.* FROM PrpExtraBValid a,PrpExtraBDemand b " + strCondition + " ORDER BY a.ValidTime";  
  blPrpExtraBValid.query(strSql);
  count = blPrpExtraBValid.getSize();
%>

<html>
  <head>
    <title>法定三者确认请求查询结构页面</title>
    <%-- 公用函数 --%>
	  <script src="/prpall/common/pub/UICommon.js"></script>
	  <%-- 页面样式  --%>
	  <link rel="stylesheet" type="text/css" href="/prpall/css/Standard.css">
  </head>
    <form name="fm" action="##.jsp" method=post>
    <body class="interface" background="/prpall/common/images/bgCommon.gif">
      <table class="common" align="center">
        <tr>
          <td class=formtitle colspan=7>请求确认查询结果</td>
        </tr>
        <tr class=listtitle>
          <td style="width:15%">确认码</td>
          <td style="width:15%">查询码</td>
          <td style="width:15%">预约号</td>
          <td style="width:10%">号牌号码</td>
          <td style="width:22%">XX单号</td>
          <td style="width:8%">操作员</td>
          <td style="width:15%">确认时间</td>
        </tr>
<%
  for(i=0; i<count; i++)
  {
    prpExtraBValidSchema = blPrpExtraBValid.getArr(i);
    
    String strValidNo2     = prpExtraBValidSchema.getValidNo();
    String strDemandNo2    = prpExtraBValidSchema.getDemandNo();
    String strLicenseNo2   = "";
    String strProposalNo2  = prpExtraBValidSchema.getProposalNo();
    String strOperatorName = blPrpDuser.translateCode(prpExtraBValidSchema.getOperatorCode(),true);
    String strValidTime2   = prpExtraBValidSchema.getValidTime();
    blPrpExtraBDemand.getInfo(strDemandNo2);
    if(blPrpExtraBDemand.getSize()>0)
      strLicenseNo2 = blPrpExtraBDemand.getArr(0).getLicenseNo();
      
    if(i%2==0)
      trClass = "listodd";
    else
      trClass = "listeven";
%>
      <tr class="<%=trClass%>">
        <td name="tdValidNo"><a class="check" href="/prpall/indiv/sh/UIValidShow.jsp?ValidNo=<%=strValidNo2%>"><%=strValidNo2%></a></td>
        <td name="tdDemandNo"><%=strDemandNo2%></td>
        <td name="tdPrecontractNo"><%=prpExtraBValidSchema.getPrecontractNo()%></td>   
        <td name="tdLicenseNo"><%=strLicenseNo2%></td>   
        <td name="tdProposalNo"><%=strProposalNo2%></td>
        <td name="tdOperatorName"><%=strOperatorName%></td>
        <td name="tdValidTime"><%=strValidTime2%></td>
      </tr>
<%
  }
%>
      <tr>
        <td class=input colspan=7 align=center>共<%=count%>条数据</td>
      </tr>
    </table>
    </form>
  </body>
</html>
