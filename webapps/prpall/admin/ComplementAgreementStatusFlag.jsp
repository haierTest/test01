<%@page import="com.sp.sysframework.reference.DBManager"%>
<%@page import="com.sp.utility.SysConfig"%>
<%@page import="com.sp.platform.bl.action.domain.BLPrpDagreementAction"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.sp.platform.bl.facade.BLPrpDagreementFacade"%>
<%@page import="com.sp.platform.dto.domain.PrpDagreementDto"%>
<%@page import="java.util.List"%>
<%@page import="com.sp.utility.StringConvertor"%>
<%
	String strEditFlag = StringConvertor.changeNullToEmpty(request.getParameter("EditFlag"));
	String strStartDate = StringConvertor.changeNullToEmpty(request.getParameter("StartDate"));
	String strMessage = "";
	if("1".equals(strEditFlag)){
		String strDataSource = SysConfig.getProperty("ggSunDBDataSource");
		DBManager dbManager = null;
		try{
			dbManager = new DBManager();
			if(strDataSource != null && strDataSource.length() > 0){
				dbManager.open(strDataSource);
			}
			List<PrpDagreementDto> agreementList = null;
			BLPrpDagreementFacade blPrpDagreementFacade = new BLPrpDagreementFacade();
			BLPrpDagreementAction blPrpDagreementAction = new BLPrpDagreementAction();
			StringBuffer strWhereBuffer = new StringBuffer();
			strWhereBuffer.append(" 1=1");
			strWhereBuffer.append(" AND AGREEMENTNO IN (");
			strWhereBuffer.append(" SELECT D.AGREEMENTNO FROM PRPDAGREEDETAIL D WHERE ");
			strWhereBuffer.append(" D.RISKCODE IN ('0507','0508','0521')");
			strWhereBuffer.append(" AND D.AGREEMENTNO IN (");
			strWhereBuffer.append(" SELECT M.AGREEMENTNO FROM PRPDAGREEMENT M WHERE");
			strWhereBuffer.append(" VALIDSTATUS = '1'");
			strWhereBuffer.append(" AND STARTDATE > TO_DATE('");
			strWhereBuffer.append(strStartDate);
			strWhereBuffer.append("')");
			strWhereBuffer.append(" AND ENDDATE > SYSDATE");
			strWhereBuffer.append(" AND (FEESTATUS = '1' OR FEESTATUS IS NULL)");
			strWhereBuffer.append(" AND TO_DATE(UPDATEDATE,'YYYY-MM-DD') < TO_DATE('2014-08-23','YYYY-MM-DD')))");
			agreementList = (ArrayList<PrpDagreementDto>) blPrpDagreementAction.findByConditions(dbManager,strWhereBuffer.toString());
			if(agreementList != null && agreementList.size() > 0){
				for(PrpDagreementDto agreement:agreementList){
					agreement.setFeeStatus("1");
					blPrpDagreementFacade.update(agreement);
				}
				strMessage = "�Ѹ��£�"+agreementList.size()+"�����ݣ�";
			}
		}catch(Exception ex){
			strMessage = "����ʧ�ܣ�";
			ex.printStackTrace();
		}finally{
			if(dbManager != null){
				dbManager.close();
			}
		}
		
	}else{
		strStartDate = "2013-01-01";
	}
%>


<html>
  <head>
    <title>����Э�鲹��XXռ��ҳ��</title>
    <link rel="stylesheet" type="text/css" href="/prpall/css/Standard.css">
    <script src="/prpall/common/pub/UICommon.js"></script>
    <script language=javascript>
    function submitForm()
    {
    	var messages = document.getElementById("messages");
    	var arrElement = new Array();
		var i=0;
		  
		  arrElement[i++]=fm.StartDate;
		  for(i=0;i<arrElement.length;i++)
			{
				if(isEmpty(arrElement[i])==true)
			  {
			    errorMessage(arrElement[i].description+"����Ϊ��");
			    arrElement[i].focus();
			    arrElement[i].select();
			    return false;
			  }
			}
		  messages.innerHTML = "��ʾ�����ڰ�ʱ��β����������������ϴ�����ʱ���Ƚϳ��������ĵȴ���";
    	var inputs = document.getElementsByTagName("input");
		for(var index=0;index<inputs.length;index++){
			if(inputs[index].type == "button"){
				inputs[index].disabled = true;
			}
		}
      fm.submit();
    }
    function resetForm()
    {
  		fm.StartDate.value = "2013-01-01";
      
    }
    function loadForm(message){
    	if(message != ""){
    		errorMessage(message);
    	}
    }
    </script>
  </head>
<body onload="loadForm('<%=strMessage%>')">
  <form name="fm" action="/prpall/admin/ComplementAgreementStatusFlag.jsp" method="post">
  <input type="hidden" name="EditFlag" value="1">
  <table class="common" cellpadding="5" cellspacing="1" >
    <tr class=listtitle>
      <td colspan="7"><b>����Э�鲹�����걨״̬</b>
      </td>
    </tr>
    <tr class=listodd>
    <td>
    	<b>������XXXXX���ڲ���</b>
    </td>
    	<td align="left">
        ��XXXXX���ڣ�
      </td>
      <td colspan="3" align="left">
        <input type="text" name="StartDate" maxlength="10" class="common" description="��XXXXX����" onblur="checkFullDate(this);" value="<%=strStartDate%>">
        <img src="/prpall/common/images/markMustInput.jpg">
      </td>
      <td>
        <input class="button" type="button" name="buttonSubmit" alt=" ȷ �� " value="ȷ ��" onclick="submitForm()">
      </td>
      <td>
        <input class="button" type="button" name="buttonCancel" alt=" �� �� " value="�� ��" onclick="resetForm()">
      </td>
      
    </tr>
    <tr>
    	<td colspan="6" align="center">
    		<h3 id="messages"></h3>
    	</td>
    </tr>
  </table>
  </form>
</body>
</html>
