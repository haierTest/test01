<%--
****************************************************************************
* DESC       ：法定第三者查询请求录入初始化页面
* Author     : X
* CREATEDATE ： 2003-02-09
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
--%>

<%-- 引入bean类部分 --%>
<%@page import="com.sp.utility.string.Date"%>
<%@page import="com.sp.utiall.blsvr.BLPrpDcode"%>

<%
  String strRiskCode = (String)session.getAttribute("RiskCode");
%>
<%-- 界面赋值部分 --%>
<script language="javascript">
  function loadForm()
  {
    setOption('UseNatureCode','<%=new BLPrpDcode().getOptionCode("UseNature"   ,strRiskCode)%>');
    
    
    
    
    
    
    
    
    
    fm.StartDate.oldValue = '<%=new Date().getString(Date.YEAR+Date.MONTH+Date.DATE)%>';
    fm.StartDate.value    = getNextDateFullDate(fm.StartDate.oldValue,1);
    fm.EndDate.value      = getNextYearFullDate(fm.StartDate.oldValue,1); 
  }
</script>
