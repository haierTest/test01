<%--
****************************************************************************
* DESC       �����������߲�ѯ����¼���ʼ��ҳ��
* Author     : X
* CREATEDATE �� 2003-02-09
* MODIFYLIST ��   Name       Date            Reason/Contents
****************************************************************************
--%>

<%-- ����bean�ಿ�� --%>
<%@page import="com.sp.utility.string.Date"%>
<%@page import="com.sp.utiall.blsvr.BLPrpDcode"%>

<%
  String strRiskCode = (String)session.getAttribute("RiskCode");
%>
<%-- ���渳ֵ���� --%>
<script language="javascript">
  function loadForm()
  {
    setOption('UseNatureCode','<%=new BLPrpDcode().getOptionCode("UseNature"   ,strRiskCode)%>');
    
    
    
    
    
    
    
    
    
    fm.StartDate.oldValue = '<%=new Date().getString(Date.YEAR+Date.MONTH+Date.DATE)%>';
    fm.StartDate.value    = getNextDateFullDate(fm.StartDate.oldValue,1);
    fm.EndDate.value      = getNextYearFullDate(fm.StartDate.oldValue,1); 
  }
</script>
