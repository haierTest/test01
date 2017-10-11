<%--
****************************************************************************
* DESC       ：法定三者车队查询请求录入初始化页面
* Author     : X
* CREATEDATE ： 2003-02-09
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
--%>
<%-- 引入bean类部分 --%>
<%@page import="com.sp.utility.SysConfig"%>
<%@page import="com.sp.utility.error.UserException"%>
<%@page import="com.sp.utility.string.*"%>
<%@page import="com.sp.utility.string.Date"%>
<%@page import="com.sp.utiall.blsvr.BLPrpDcode"%>
<%@page import="com.sp.prpall.blsvr.tb.*"%>
<%@page import="com.sp.prpall.blsvr.cb.*"%>
<%@page import="com.sp.prpall.blsvr.misc.*"%>
<%@page import="com.sp.prpall.dbsvr.tb.*"%>
<%@page import="com.sp.prpall.dbsvr.misc.*"%>
<%@page import="com.sp.prpall.schema.*"%>
<%@page import="com.sp.prpall.pubfun.*"%>
<%@page import="com.sp.utility.*"%>

<%
  
  String strRiskCode = (String)session.getAttribute("RiskCode");
  String strOldContractNo = request.getParameter("OldContractNo");
  BLPrpMotorcade blPrpMotorcade = new BLPrpMotorcade();
  BLPrpCmain     blPrpCmain     = new BLPrpCmain();
  BLPrpCitemCar blPrpCitemCar = new BLPrpCitemCar();
  BLPrpCitemKind blPrpCitemKind = new BLPrpCitemKind();
  PrpCmainSchema prpCmainSchema = null;
  PrpCitemCarSchema PrpCitemCarSchema = null;
  PrpCitemKindSchema prpCitemKindSchema = null;
  String strCondition = "";  
  int i = 0;
  int count = 0;
  Date today = new Date();
  Date dateTemp = null;
  String strStartDate = "";
  String strEndDate = "";
%>
<%-- 界面赋值部分 --%>
<script language="javascript">
  function loadForm()
  {
    setOption('UseNatureCode','<%=new BLPrpDcode().getOptionCode("UseNature"   ,strRiskCode)%>');
    fm.StartDate.oldValue = '<%=new Date().getString(Date.YEAR+Date.MONTH+Date.DATE)%>';
    fm.StartDate.value    = getNextDateFullDate(fm.StartDate.oldValue,1);
    fm.EndDate.value      = getNextYearFullDate(fm.StartDate.oldValue,1);
<%
    
    if(strOldContractNo!=null && !strOldContractNo.equals(""))
    {
      
      strCondition = "ContractNo='" + strOldContractNo + "'";
      blPrpMotorcade.query(strCondition,0);
      if(blPrpMotorcade.getSize()==0)
      {
       throw new UserException(-98,-998,"UIMotorcadeDemandInputIni","合同号"+ strOldContractNo +"不存在");
      }
      
      
      strCondition = " ContractNo='" + strOldContractNo +"'"
                   + " AND substr(OthFlag,2,1)<>'1'"
                   + " AND substr(OthFlag,3,1)<>'1'"
                   + " AND substr(OthFlag,4,1)<>'1'" ;
      
      blPrpCmain.query(strCondition,0);

      
      for(i=0; i<blPrpCmain.getSize(); i++)
      {
        prpCmainSchema = blPrpCmain.getArr(i);
        
        strCondition = "PolicyNo='"+ prpCmainSchema.getPolicyNo() +"' AND KindCode='B'";
        blPrpCitemKind.query(strCondition);
        if(blPrpCitemKind.getSize()==0) continue;
        strCondition = "PolicyNo='"+ prpCmainSchema.getPolicyNo() +"'";
        blPrpCitemCar.query(strCondition);
        if(blPrpCitemCar.getSize()==0) continue;
        PrpCitemCarSchema = blPrpCitemCar.getArr(0);
        prpCitemKindSchema = blPrpCitemKind.getArr(0);
        count++;
        
        
        dateTemp = new Date(prpCmainSchema.getEndDate());
        if(today.toUtilDate().compareTo(dateTemp.toUtilDate())>0) dateTemp = today;
        dateTemp.set(Date.DATE,dateTemp.get(Date.DATE)+1);
        strStartDate = dateTemp.getString(Date.YEAR+Date.MONTH+Date.DATE);
        dateTemp.set(Date.YEAR,dateTemp.get(Date.YEAR)+1);
        dateTemp.set(Date.DATE,dateTemp.get(Date.DATE)-1);
        strEndDate = dateTemp.getString(Date.YEAR+Date.MONTH+Date.DATE);
        
%>
        insertRow('Motorcade','Motorcade_Data');
        fm.SerialNo     [<%=count%>].value = '<%=count%>';
        fm.OldPolicyNo  [<%=count%>].value = '<%=prpCmainSchema.getPolicyNo()%>';
        fm.LicenseNo    [<%=count%>].value = '<%=PrpCitemCarSchema.getLicenseNo()%>';
        fm.EngineNo     [<%=count%>].value = '<%=PrpCitemCarSchema.getEngineNo()%>';
        fm.FrameNo      [<%=count%>].value = '<%=PrpCitemCarSchema.getFrameNo()%>';
        fm.UseNatureCode[<%=count%>].value = '<%=PrpCitemCarSchema.getUseNatureCode()%>';
        fm.CarKindCode  [<%=count%>].value = '<%=PrpCitemCarSchema.getCarKindCode()%>';
        fm.SeatCount    [<%=count%>].value = '<%=PrpCitemCarSchema.getSeatCount()%>';
        fm.TonCount     [<%=count%>].value = '<%=PrpCitemCarSchema.getTonCount()%>';
        fm.ExhaustScale [<%=count%>].value = '<%=PrpCitemCarSchema.getExhaustScale()%>';
        fm.Amount       [<%=count%>].value = '<%=prpCitemKindSchema.getAmount()%>';
        fm.StartDate    [<%=count%>].value = '<%=strStartDate%>';
        fm.EndDate      [<%=count%>].value = '<%=strEndDate%>';
<%
      }
%>
      fm.NumCar.value = "<%=count%>";
      fm.CheckBoxContract.checked = true;
      spanOldContractNo.style.display = "";
      fm.OldContractNo.value = "<%=strOldContractNo%>";
<%
    }
    else 
    {
%>
      insertSingleCar();
<%
    }
%>
  }
</script>
