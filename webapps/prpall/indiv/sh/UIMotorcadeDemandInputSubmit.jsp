<%--
****************************************************************************
* DESC       ：预约车队查询请求提交页面
* Author     : X
* CREATEDATE ：2003-03-04
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
--%>

<%-- 错误处理 --%>
<%@page errorPage="/UIErrorPage"%>

<%-- 引入bean类部分 --%>
<%@page import="com.sp.utility.*"%>
<%@page import="com.sp.utility.string.*"%>
<%@page import="com.sp.indiv.sh.util.*"%>
<%@page import="com.sp.indiv.sh.blsvr.*"%>
<%@page import="com.sp.indiv.sh.schema.*"%>

<%
  
  int CarCount = Integer.parseInt(request.getParameter("NumCar"));
  BLPrpExtraBDemand blPrpExtraBDemand = new BLPrpExtraBDemand();

  String[] arrLicenseNo       = request.getParameterValues("LicenseNo");
  String[] arrOldPolicyNo     = request.getParameterValues("OldPolicyNo");
  String[] arrEngineNo        = request.getParameterValues("EngineNo");
  String[] arrFrameNo         = request.getParameterValues("FrameNo");
  String[] arrUseNatureCode   = request.getParameterValues("UseNatureCode");
  String[] arrCarKindCode     = request.getParameterValues("CarKindCode");
  String[] arrSeatCount       = request.getParameterValues("SeatCount");
  String[] arrTonCount        = request.getParameterValues("TonCount");
  String[] arrExhaustScale    = request.getParameterValues("ExhaustScale");
  String[] arrAmount          = request.getParameterValues("Amount");
  String[] arrStartDate       = request.getParameterValues("StartDate");
  String[] arrEndDate         = request.getParameterValues("EndDate");
  
  String[] arrLicenseType     = request.getParameterValues("LicenseType");
  
  
  BLMotorcadeThird blMotorcadeThird = new BLMotorcadeThird();
  int i = 0;
  String strUserCode = request.getParameter("UserCode");
  String strComCode  = request.getParameter("ComCode");
  for(i=1;i<=CarCount;i++)
  {
    PrpExtraBDemandSchema prpExtraBDemandSchema = new PrpExtraBDemandSchema();
    prpExtraBDemandSchema.setOldPolicyNo    (arrOldPolicyNo[i]    );
    prpExtraBDemandSchema.setLicenseNo      (arrLicenseNo[i]      );
    prpExtraBDemandSchema.setEngineNo       (arrEngineNo[i]       );
    prpExtraBDemandSchema.setFrameNo        (arrFrameNo[i]        );
    prpExtraBDemandSchema.setUseNatureCode  (arrUseNatureCode[i]  );
    prpExtraBDemandSchema.setCarKindCode    (arrCarKindCode[i]    );
    prpExtraBDemandSchema.setSeatCount      (Integer.parseInt(ChgData.chgStrZero(arrSeatCount[i]))      );
    prpExtraBDemandSchema.setTonCount       (Double.parseDouble(ChgData.chgStrZero(arrTonCount[i]))       );
    prpExtraBDemandSchema.setExhaustScale   (Double.parseDouble(ChgData.chgStrZero(arrExhaustScale[i]))   );
    prpExtraBDemandSchema.setAmount         (Double.parseDouble(ChgData.chgStrZero(arrAmount[i]))         );
    prpExtraBDemandSchema.setStartDate      (arrStartDate[i]      );
    prpExtraBDemandSchema.setEndDate        (arrEndDate[i]        ); 
    prpExtraBDemandSchema.setLicenseType    (arrLicenseType[i]    ); 
    prpExtraBDemandSchema.setComCode        ( strComCode       );
    prpExtraBDemandSchema.setOperatorCode   ( strUserCode      );
    prpExtraBDemandSchema.setDemandTime     ( new Formater(Formater.DATETOMINUTE).format(new java.util.Date()) );

    blPrpExtraBDemand.setArr(prpExtraBDemandSchema);
  }
  
  
  String strPrecontractNo = "";
  strPrecontractNo = blMotorcadeThird.demand(blPrpExtraBDemand);

%>

  <%-- 页面显示 --%>
  <jsp:include page="/indiv/sh/UIMotorcadeDemandShow.jsp">
    <jsp:param name="PrecontractNo" value="<%=strPrecontractNo%>" />
  </jsp:include>