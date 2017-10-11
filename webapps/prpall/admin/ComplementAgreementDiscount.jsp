<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="com.sp.utility.SysConfig"%>
<%@page import="com.sp.prpall.pubfun.PubTools"%>
<%@page import="com.sp.utility.string.ChgDate"%>
<%@page import="com.sp.utility.database.DbPool"%>
<%@page import="com.sp.utility.StringConvertor"%>
<%@page import="com.sp.prpall.blsvr.cb.BLPrpCmain"%>
<%@page import="com.sp.platform.utility.SplitSwitch"%>
<%@page import="com.sp.prpall.schema.PrpCmainSchema"%>
<%@page import="com.sp.sysframework.reference.DBManager"%>
<%@page import="com.sp.platform.dto.domain.PrpDagreementDto"%>
<%@page import="com.sp.platform.dto.domain.PrpDagreeDetailDto"%>
<%@page import="com.sp.platform.bl.action.domain.BLPrpDagreementAction"%>
<%@page import="com.sp.platform.bl.action.domain.BLPrpDagreeDetailAction"%>
<%@page errorPage="/UIErrorPage"%>
<%

	String strEditFlag = StringConvertor.changeNullToEmpty(request.getParameter("EditFlag"));
	String strWorkStartDate = StringConvertor.changeNullToEmpty(request.getParameter("WorkStartDate"));
	String strWorkEndDate = StringConvertor.changeNullToEmpty(request.getParameter("WorkEndDate"));
	String strAgreementNo = StringConvertor.changeNullToEmpty(request.getParameter("AgreementNo"));
	String strMessage = "";
	if(!"".equals(strEditFlag)){
		DBManager dbManager = null;
		DbPool dbPool = null;
		StringBuffer strWhereBuffer = new StringBuffer();
		ChgDate chgDate = new ChgDate();
		String strCurrentDate = chgDate.getCurrentTime("yyyy-MM-dd"); 
		BLPrpDagreementAction blPrpDagreementAction = new BLPrpDagreementAction();
		BLPrpDagreeDetailAction blPrpDagreeDetailAction = new BLPrpDagreeDetailAction();
		BLPrpCmain blPrpCmain = null;
		List<PrpDagreementDto> agreementList = null;
		List<PrpDagreeDetailDto> agreeDetailList = null;
		Map<String,Map<String,Map<String,Double>>> agreeDetailMap = null;
		Map<String,PrpDagreeDetailDto> agreeDetailDtoMap = null;
		Map<String,Double> totalPremium = null;
		String reg = "^[A-Z]{1,2}$";
		strWhereBuffer.append("1=1");
		
		
		
		strWhereBuffer.append(" and (feestatus = '1' or feestatus is null)");
		
		/* strWhereBuffer.append(" and enddate > to_date('");
		strWhereBuffer.append(strCurrentDate);
		strWhereBuffer.append("','yyyy-mm-dd')"); */
		if("UPDATEDATE".equals(strEditFlag)){
			
			strWhereBuffer.append(" and (to_date(updatedate,'yyyy-mm-dd') between to_date('");
			strWhereBuffer.append(strWorkStartDate);
			strWhereBuffer.append("','yyyy-mm-dd') and to_date('");
			strWhereBuffer.append(strWorkEndDate);
			strWhereBuffer.append("','yyyy-mm-dd'))");
		}else if("AGREEMENTNO".equals(strEditFlag)){
			strWhereBuffer.append(" and agreementno = '");
			strWhereBuffer.append(strAgreementNo);
			strWhereBuffer.append("'");
		}
		
		try{
			String strDataSource = SysConfig.getProperty("ggSunDBDataSource");
			dbPool = new DbPool();
			dbPool.open(strDataSource);
			
			
			
			
			agreementList = (ArrayList<PrpDagreementDto>) blPrpDagreementAction.findByConditions(dbPool.getDBManager(strDataSource), strWhereBuffer.toString());
			if(agreementList != null && agreementList.size() > 0){
				agreeDetailMap = new HashMap<String,Map<String,Map<String,Double>>>();
				agreeDetailDtoMap = new HashMap<String,PrpDagreeDetailDto>();
				totalPremium = new HashMap<String,Double>();
				for(PrpDagreementDto agreement:agreementList){
					
					strWhereBuffer.delete(0,strWhereBuffer.length());
					strWhereBuffer.append("1=1");
					strWhereBuffer.append(" and riskcode in ('0507','0508','0521')");
					strWhereBuffer.append(" and agreementno = ");
					strWhereBuffer.append("'"+agreement.getAgreementNo()+"'");
					strWhereBuffer.append(" and businessclass is not null");
					
					agreeDetailList = (ArrayList<PrpDagreeDetailDto>) blPrpDagreeDetailAction.findByConditions(dbPool.getDBManager(strDataSource), strWhereBuffer.toString());
					
					if(agreeDetailList != null && agreeDetailList.size() > 0){
						boolean isDiffFee = false;
						strWhereBuffer.append(" and feeclass in (");
						for(PrpDagreeDetailDto agree : agreeDetailList){
							if(!"0000".equals(agree.getBusinessClass())){
								isDiffFee = true;
							}
							String agreementno = agree.getAgreementNo();
							String riskcode = agree.getRiskCode();
							String feetype = agree.getBusinessClass();
							if(!agreeDetailMap.containsKey(agreementno)){
								agreeDetailMap.put(agreementno, new HashMap<String,Map<String,Double>>());
								totalPremium.put(agreementno, 0d);
							}
							String key = agreementno+"_"+riskcode+"_"+feetype;
							agreeDetailDtoMap.put(key, agree );
						}
						
						if(isDiffFee){
							
							strWhereBuffer.delete(0,strWhereBuffer.length());
							strWhereBuffer.append("1=1 and agreementno = ? and agreementno is not null");
							strWhereBuffer.append(" and riskcode in ('0507','0508','0521')");
							strWhereBuffer.append(" and startdate > to_date(?,'yyyy-mm-dd')");
							strWhereBuffer.append(" and feeclass is not null");
							ArrayList iWhereValue = new ArrayList();
							iWhereValue.add(agreement.getAgreementNo());
							iWhereValue.add("2013-01-01");
							
							blPrpCmain = new BLPrpCmain();
							
							
							blPrpCmain.query(dbPool, strWhereBuffer.toString(), iWhereValue, 0);
							if(blPrpCmain != null && blPrpCmain.getSize() > 0){
								
								for(int i = 0;i < blPrpCmain.getSize();i ++){
									PrpCmainSchema cmainSchema = blPrpCmain.getArr(i);
									if(cmainSchema.getFeeClass().matches(reg)){
										for(PrpDagreeDetailDto agree : agreeDetailList){
											if(cmainSchema.getRiskCode().equals(agree.getRiskCode()) && cmainSchema.getFeeClass().equals(agree.getBusinessClass())){
												totalPremium.put(agreement.getAgreementNo(),totalPremium.get(agreement.getAgreementNo()) + PubTools.conversionDouble(cmainSchema.getSumPremium()));
												String c_riskcode = cmainSchema.getRiskCode();
												String c_feetype = cmainSchema.getFeeClass();
												double c_sumpremium = PubTools.conversionDouble(cmainSchema.getSumPremium());
												if(agreeDetailMap.get(agreement.getAgreementNo()).containsKey(c_riskcode)){
													if(agreeDetailMap.get(agreement.getAgreementNo()).get(c_riskcode).containsKey(c_feetype)){
														double c_total =agreeDetailMap.get(agreement.getAgreementNo()).get(c_riskcode).get(c_feetype) + c_sumpremium;
														agreeDetailMap.get(agreement.getAgreementNo()).get(c_riskcode).remove(c_feetype);
														agreeDetailMap.get(agreement.getAgreementNo()).get(c_riskcode).put(c_feetype,c_total);
													}else{
														agreeDetailMap.get(agreement.getAgreementNo()).get(c_riskcode).put(c_feetype,c_sumpremium);
													}
												}else{
													Map<String,Double> c_totalMap = new HashMap<String,Double>();
													c_totalMap.put(c_feetype,c_sumpremium);
													agreeDetailMap.get(agreement.getAgreementNo()).put(c_riskcode,c_totalMap);
												}
											}
										}
									}
								}
							}
							
						}
					}
				}
				

				dbManager = new DBManager();
				
				SplitSwitch.getDBSplitSwitchSec(dbManager);
				dbManager.beginTransaction();
				for(String agreementno:agreeDetailMap.keySet()){
					
					for(String riskcode:agreeDetailMap.get(agreementno).keySet()){
						for(String feetype:agreeDetailMap.get(agreementno).get(riskcode).keySet()){
							DecimalFormat df = new DecimalFormat("#0.00");
							String discount = df.format(agreeDetailMap.get(agreementno).get(riskcode).get(feetype)/totalPremium.get(agreementno));
							String key = agreementno+"_"+riskcode+"_";
							PrpDagreeDetailDto agree = agreeDetailDtoMap.get(key+feetype);
							if(agree != null){
								
								agree.setDiscount1(PubTools.conversionDouble(discount));
								if(agreeDetailDtoMap.get(key+"0000") != null){
									agree.setAmount1(agreeDetailDtoMap.get(key+"0000").getAmount1());
								}
								blPrpDagreeDetailAction.update(dbManager, agree);
								
								
								
							}
							
						}
					}
					
					
				}
				dbManager.commitTransaction();
				if("UPDATEDATE".equals(strEditFlag)){
					strMessage = "已将"+strWorkStartDate+"——"+strWorkEndDate+"生效的代理协议差异占比与赔付率补齐！";
				}else{
					strMessage = "已将代理协议"+strAgreementNo+"的差异占比与赔付率补齐！";
				}
			}else{
				strMessage = "没有符合条件的数据！";
			}
		}catch(Exception e){
			if(dbManager != null){
				dbManager.rollbackTransaction();
			}
			throw e;
		}finally{
			if(dbPool!= null){
				dbPool.close();
			}
			if(dbManager != null){
				dbManager.close();
			}
		}
	}
%>

<html>
  <head>
    <title>代理协议补填XX占比页面</title>
    <link rel="stylesheet" type="text/css" href="/prpall/css/Standard.css">
    <script src="/prpall/common/pub/UICommon.js"></script>
    <script language=javascript>
    function submitForm(type)
    {
    	var messages = document.getElementById("messages");
    	if("UPDATEDATE"==type){
			fm.EditFlag.value = "UPDATEDATE";    		
	    	var arrElement = new Array();
			var i=0;
			  
			  arrElement[i++]=fm.WorkStartDate;
			  arrElement[i++]=fm.WorkEndDate;
			  for(i=0;i<arrElement.length;i++)
				{
					if(isEmpty(arrElement[i])==true)
				  {
				    errorMessage(arrElement[i].description+"不能为空");
				    arrElement[i].focus();
				    arrElement[i].select();
				    return false;
				  }
				}
			  var date1=Date.parse(arrElement[0].value.replace(/-/g,"/"));
			  var date2=Date.parse(arrElement[1].value.replace(/-/g,"/"));
			  month1=parseInt(arrElement[0].value.split("-")[0],10)*12+parseInt(arrElement[0].value.split("-")[1],10);
			  month2=parseInt(arrElement[1].value.split("-")[0],10)*12+parseInt(arrElement[1].value.split("-")[1],10);
			  
			 if(date2 > Date.parse("2014-08-23".replace(/-/g,"/"))){
				  errorMessage(arrElement[1].description+"应早于2014-08-23！");
				  arrElement[1].focus();
			      arrElement[1].select();
			      return false;
			  }
			  if(month2 - month1 > 6 || month2 - month1 < 0){
				  errorMessage("时间段不能大于6个月！");
			      return false;
			  }
			  messages.innerHTML = "提示：正在按时间段补数，由于数据量较大，所用时间会比较长，请耐心等待！";
    	}else{
			fm.EditFlag.value = "AGREEMENTNO";
    		var agreementno = fm.AgreementNo;
    		if(isEmpty(agreementno)==true){
    			errorMessage(agreementno.description+"不能为空");
    			agreementno.focus();
    			agreementno.select();
    			return false;
    		}
    		messages.innerHTML = "提示：正在按协议号补数，由于数据量较大，所用时间会比较长，请耐心等待！";
    	}
    	var inputs = document.getElementsByTagName("input");
		for(var index=0;index<inputs.length;index++){
			if(inputs[index].type == "button"){
				inputs[index].disabled = true;
			}
		}
      fm.submit();
    }
    function resetForm(type)
    {
    	if("UPDATEDATE"==type){
    		fm.WorkStartDate.value = "";
			fm.WorkEndDate.value = "";
    	}else{
    		fm.AgreementNo.value = "";
    	}
      
    }
    function loadForm(message){
    	if(message != ""){
    		errorMessage(message);
    	}
    }
    </script>
  </head>
<body onload="loadForm('<%=strMessage%>')">
  <form name="fm" action="/prpall/admin/ComplementAgreementDiscount.jsp" method="post">
  <input type="hidden" name="EditFlag" value="">
  <table class="common" cellpadding="5" cellspacing="1" >
    <tr class=listtitle>
      <td colspan="7"><b>代理协议补填XX占比与渠道赔付率</b>
      </td>
    </tr>
    <tr class=listodd>
    <td>
    	<b>根据时间段补数</b>
    </td>
      <td align="left">
        起始日期：
      </td>
      <td align="left">
        <input type="text" name="WorkStartDate" maxlength="10" class="common" description="起始日期" onblur="checkFullDate(this);" value="<%=strWorkStartDate%>">
        <img src="/prpall/common/images/markMustInput.jpg">
      </td>
      <td align="left">
        终止日期：
      </td>
      <td align="left">
        <input type="text" name="WorkEndDate" maxlength="10" class="common" description="终止日期" onblur="checkFullDate(this);" value="<%=strWorkEndDate%>">
      	<img src="/prpall/common/images/markMustInput.jpg">
      </td>
      <td>
        <input class="button" type="button" name="buttonSubmit" alt=" 确 认 " value="确 认" onclick="submitForm('UPDATEDATE')">
      </td>
      <td>
        <input class="button" type="button" name="buttonCancel" alt=" 重 置 " value="重 置" onclick="resetForm('UPDATEDATE')">
      </td>
    </tr>
    <tr class=listodd>
    <td>
    	<b>根据协议号补数</b>
    </td>
    	<td align="left">
        代理协议号：
      </td>
      <td colspan="3" align="left">
        <input type="text" name="AgreementNo" maxlength="30" class="common" description="代理协议号" value="<%=strAgreementNo%>">
        <img src="/prpall/common/images/markMustInput.jpg">
      </td>
      <td>
        <input class="button" type="button" name="buttonSubmit" alt=" 确 认 " value="确 认" onclick="submitForm('AGREEMENTNO')">
      </td>
      <td>
        <input class="button" type="button" name="buttonCancel" alt=" 重 置 " value="重 置" onclick="resetForm('AGREEMENTNO')">
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