<%@page import="java.text.DecimalFormat"%>
<%@page import="com.sp.utility.string.Str"%>
<%@page import="com.sp.utility.string.Money"%>
<%@page import="com.sp.utility.string.Date"%>
<%@page import="com.sp.utility.string.ChgData"%>
<%@page import="com.sp.prpall.blsvr.cb.BLPolicy"%>
<%@page import="com.sp.prpall.blsvr.cb.BLPrpCitemKind"%>
<%@page import="com.sp.prpall.blsvr.cb.BLPrpCengage"%>
<%@page import="com.sp.prpall.blsvr.cb.BLPrpCplan"%>
<%@page import="com.sp.prpall.blsvr.cb.BLPrpCaddress"%>
<%@page import="com.sp.prpall.blsvr.cb.BLPrpCinsured"%>
<%@page import="com.sp.prpall.blsvr.cb.BLPrpCsubsidy"%>
<%@page import="com.sp.prpall.schema.PrpCmainSchema"%>
<%@page import="com.sp.prpall.schema.PrpCaddressSchema"%>
<%@page import="com.sp.prpall.schema.PrpCitemKindSchema"%>
<%@page import="com.sp.prpall.schema.PrpCinsuredSchema"%>
<%@page import="com.sp.prpall.schema.PrpCsubsidySchema"%>
<%@page import="com.sp.prpall.schema.PrpCengageSchema"%>
<%@page import="com.sp.prpall.pubfun.PubTools" %>
<%@page import="com.sp.utiall.blsvr.BLPrpDkind"%>
<%@page import="com.sp.utiall.blsvr.BLPrpDcode"%>
<%@page import="com.sp.utiall.blsvr.BLPrpDuser"%>
<%@page import="com.sp.prpall.ui.UIItemKindSeparator"%>
<%@page import="com.sp.prpall.ui.UIEngageSeparator"%>
<%@page import="com.sp.utiall.dbsvr.DBPrpDcompany"%>
<%@page import="com.sp.utiall.dbsvr.DBPrpDcurrency"%>
<%@page import="java.util.ArrayList"%>

<%
	String strPolicyNo = request.getParameter("BizNo");
	boolean isChinese = true;

	BLPolicy blPolicy = null;
	BLPrpCitemKind blPrpCitemKind = null;
	BLPrpCitemKind blPrpCitemKindMain = null;
	BLPrpCitemKind blPrpCitemKindSub = null;
	BLPrpCengage blPrpCengage = null;
	BLPrpCengage blPrpCengageT = null;
	BLPrpCplan blPrpCplan = null;
	BLPrpCaddress blPrpCaddress = null;
	BLPrpCinsured blPrpCinsured = null;
	BLPrpCsubsidy blPrpCsubsidy = null;
	BLPrpDuser blPrpDuser = null;
	BLPrpDkind blPrpDkind = new BLPrpDkind();
	BLPrpDcode blPrpDcode = new BLPrpDcode();
	UIItemKindSeparator uiItemKindSeparator = null;
	UIEngageSeparator uiEngageSeparator = null;
	PrpCmainSchema prpCmainSchema = new PrpCmainSchema();
	PrpCitemKindSchema prpCitemKindSchema = null;
	PrpCaddressSchema prpCaddressSchema = null;
	PrpCinsuredSchema prpCinsuredSchema = null;
	PrpCsubsidySchema prpCsubsidySchema = null;
	DBPrpDcompany dbPrpDcompany = new DBPrpDcompany();
	DBPrpDcurrency dbPrpDcurrency = new DBPrpDcurrency();

	String strComCode = ""; 
	String strKindName = ""; 
	String strItemDetailName = ""; 
	String strPremium = ""; 
	String strAmount = ""; 
	String strRate = ""; 
	String strQuantity = ""; 
	double dbAmount = 0.0;
	double dbQuantity = 0.0;
	double dbUnitAmount = 0.0;
    String strUnitAmount = ""; 
	String strComPhoneNumber = ""; 
	StringBuffer strComName = new StringBuffer(); 
	String strComAddressName = ""; 
	String strComPostCode = ""; 

	String strStartDate = ""; 
	String strEndDate = ""; 
	String strStartDateYear = "";
	String strStartDateMonth = "";
	String strStartDateDay = "";
	String strStartHour = "";
	String strEndDateYear = "";
	String strEndDateMonth = "";
	String strEndDateDay = "";
	String strEndHour = "";
	String strPeriod = ""; 
	String strArgueSolution = ""; 
	int i = 0, j = 0, lv_CountAppend = 0;
	int intCountMain = 0;
	int intCountSub = 0;
	int intCountSubsidy = 0;

	
	String strAppliName = ""; 
	String strIdentifyType = ""; 
	String strIdentifyNumber = ""; 
	String strPostAddress = ""; 
	String strAppliPostCode = ""; 
	String strAppliPhone = ""; 
	StringBuffer strInsuredNameBuf = new StringBuffer();
	String strInsuredName = "";
	StringBuffer strAddress = new StringBuffer();
	String strClauses = ""; 
	String strOperateDate = "";
	String strOperateDateYear = "";
	String strOperateDateMonth = "";
	String strOperateDateDay = "";
	String strHandlerCode = "";
	String strHandlerName = "";
	String strOperatorCode = "";
	String strOperatorName = "";
	String strUnderwriteName = "";
	String strLanguage = ""; 
	String strPlanDate = ""; 
	String strPlanDateYear = "";
	String strPlanDateMonth = "";
	String strPlanDateDay = "";
	String sumPremiumMain = ""; 
	String strSumPremiumMain = ""; 
	String sumAmountMain = ""; 
	String strSumAmountMain = ""; 
	String strAgriNature = ""; 
	String strAgriPolicySort = "";
	String strAgriPolicySortName = ""; 
	String strSubsidyNatureName = ""; 
	String strSubsidySortName = ""; 
	String strCurrencyCode = ""; 
	String strCurrencyCName = ""; 
	String strSubsidyRate = ""; 
	String strSubsidyPremium = ""; 

	ArrayList listItemKindMain = null;
	ArrayList listItemKindMains = new ArrayList();
	ArrayList listItemKindSub = null;
	ArrayList listItemKindSubs = new ArrayList();
	ArrayList listSubsidy = null;
	ArrayList listSubsidys = new ArrayList();

  
	
	boolean isPrintKind = false; 
  
	String strFrame3 = ""; 
	String strFrame4 = ""; 
	String strDisPlay2 = "";
	String strDisPlayTr = "";

	
	blPolicy = new BLPolicy();
	blPolicy.getData(strPolicyNo);

	prpCmainSchema = blPolicy.getBLPrpCmain().getArr(0);
	blPrpCitemKind = blPolicy.getBLPrpCitemKind();
	blPrpCengage = blPolicy.getBLPrpCengage();
	blPrpCplan = blPolicy.getBLPrpCplan();
	blPrpCsubsidy = blPolicy.getBLPrpCsubsidy();
	blPrpCinsured = blPolicy.getBLPrpCinsured();
	
	strComCode = prpCmainSchema.getComCode();
  

	
	
	uiItemKindSeparator = new UIItemKindSeparator(blPrpCitemKind);
	blPrpCitemKindMain = uiItemKindSeparator.getCMain();
	blPrpCitemKindSub = uiItemKindSeparator.getCSub();

	
	intCountMain = blPrpCitemKindMain.getSize();
	for (i = 0; i < intCountMain; i++) {
		prpCitemKindSchema = blPrpCitemKindMain.getArr(i);
		listItemKindMain = new ArrayList();
		strPremium = new DecimalFormat("#,##0.00").format(Double
		            .parseDouble(Str.chgStrZero(prpCitemKindSchema.getPremium()))); 
		strAmount = new DecimalFormat("#,##0.00").format(Double
		            .parseDouble(Str.chgStrZero(prpCitemKindSchema.getAmount()))); 
		strQuantity = new DecimalFormat("#,##0.00").format(Double
		            .parseDouble(Str.chgStrZero(prpCitemKindSchema.getQuantity()))); 
		strItemDetailName = prpCitemKindSchema.getItemDetailName();
		dbAmount = Double.parseDouble(ChgData.chgStrZero(prpCitemKindSchema.getAmount()));
		dbQuantity = Double.parseDouble(ChgData.chgStrZero(prpCitemKindSchema.getQuantity()));
		
		if(dbQuantity != 0.00)
		{
		  dbUnitAmount = PubTools.div(dbAmount, dbQuantity, 2);
		  strUnitAmount = new DecimalFormat("#,##0.00").format(dbUnitAmount);
		}
		strRate = new DecimalFormat("#,##0.0000").format(Double
		          .parseDouble(Str.chgStrZero(prpCitemKindSchema.getRate())));
		
		
		/*
		if ("001".equals(prpCitemKindSchema.getKindCode())) {
			
			isPrintKind = true;
		
			strKindName = blPrpDkind.translateCode(prpCitemKindSchema
			.getRiskCode(), prpCitemKindSchema.getKindCode(),
			isChinese);
		}
		
	  else if("006".equals(prpCitemKindSchema.getKindCode()))
	  {
	    if("04".equals(strComCode.substring(0,2)))
	    {
	      isPrintKind = true;
			  strKindName = blPrpDkind.translateCode(prpCitemKindSchema
			  .getRiskCode(), prpCitemKindSchema.getKindCode(),
			  isChinese);
	    }
	  }
	  
	  */
	  if (i == 0) {
	    if("006".equals(prpCitemKindSchema.getKindCode()))
	    {
	      if("04".equals(strComCode.substring(0,2)))
	      {
	        isPrintKind = true;
		  	  strKindName = blPrpDkind.translateCode(prpCitemKindSchema
		  	  .getRiskCode(), prpCitemKindSchema.getKindCode(),
		  	  isChinese);
	      }
	    }else
	    {
	      isPrintKind = true;
			  strKindName = blPrpDkind.translateCode(prpCitemKindSchema
			  .getRiskCode(), prpCitemKindSchema.getKindCode(),
			  isChinese);
			}
		}
	  
		listItemKindMain.add(strItemDetailName);
		listItemKindMain.add(strQuantity);
		listItemKindMain.add(strUnitAmount);
		listItemKindMain.add(strAmount);
		listItemKindMain.add(strRate);
		listItemKindMain.add(strPremium);

		listItemKindMains.add(listItemKindMain);
	}
	
	
	
	if (!isPrintKind) {
  
		throw new Exception("无此打印页面！");
	}
	
	intCountSub = blPrpCitemKindSub.getSize();
	if (intCountSub == 0) {
		strDisPlayTr = "none";
	}
	for (i = 0; i < intCountSub; i++) {
		prpCitemKindSchema = blPrpCitemKindSub.getArr(i);
		listItemKindSub = new ArrayList();

		strPremium = new DecimalFormat("#,##0.00").format(Double
		            .parseDouble(Str.chgStrZero(prpCitemKindSchema.getPremium()))); 
		strAmount = new DecimalFormat("#,##0.00").format(Double
		            .parseDouble(Str.chgStrZero(prpCitemKindSchema.getAmount()))); 
		strItemDetailName = prpCitemKindSchema.getItemDetailName();
		strRate = new DecimalFormat("#,##0.0000").format(Double
		.parseDouble(Str.chgStrZero(prpCitemKindSchema
				.getRate())));

		listItemKindSub.add(strItemDetailName);
		listItemKindSub.add(strAmount);
		listItemKindSub.add(strRate);
		listItemKindSub.add(strPremium);

		listItemKindSubs.add(listItemKindSub);
	}
	
	blPrpCaddress = blPolicy.getBLPrpCaddress();
	if (blPrpCaddress.getSize() > 3) {
		strAddress = strAddress.append("详见清单");
	} else {
		for (i = 0; i < blPrpCaddress.getSize(); i++) {
			prpCaddressSchema = blPrpCaddress.getArr(i);
			strAddress.append(Str.encode(prpCaddressSchema
			.getAddressName()));
			strAddress.append("<br>");
		}
	}
	
	for (i = 0; i < blPrpCinsured.getSize(); i++) {
		prpCinsuredSchema = blPrpCinsured.getArr(i);
		
		if ("2".equals(prpCinsuredSchema.getInsuredFlag())) {

			strAppliName = prpCinsuredSchema.getInsuredName(); 
			strIdentifyType = prpCinsuredSchema.getIdentifyType(); 
			if ("01".equals(strIdentifyType)) {
		strIdentifyNumber = prpCinsuredSchema
				.getIdentifyNumber();
			}
			strPostAddress = prpCinsuredSchema.getPostAddress(); 
			strAppliPostCode = prpCinsuredSchema.getPostCode(); 
			strAppliPhone = prpCinsuredSchema.getPhoneNumber();
		} else if ("1".equals(prpCinsuredSchema.getInsuredFlag())) {
			strInsuredNameBuf
			.append(prpCinsuredSchema.getInsuredName());
			strInsuredNameBuf.append(",");
		}
	}
	strInsuredName = strInsuredNameBuf.toString();
	i = strInsuredNameBuf.lastIndexOf(",");
	strInsuredName = strInsuredNameBuf.substring(0, i);

	
	strCurrencyCode = prpCmainSchema.getCurrency();

  
	
  
	strAgriNature = prpCmainSchema.getAgriNature();
	
	if ("1".equals(strAgriNature)) {
	    
		strFrame3 = "box";
		strFrame4 = "void";
		strDisPlay2 = "";

		intCountSubsidy = blPrpCsubsidy.getSize();
		for (i = 0; i < intCountSubsidy; i++) {
			prpCsubsidySchema = blPrpCsubsidy.getArr(i);
			listSubsidy = new ArrayList();

			strAgriPolicySort = prpCsubsidySchema.getAgriPolicySort();
			strAgriPolicySortName = blPrpDcode.translateCode(
			"AgriPolicySort", strAgriPolicySort, isChinese);
			strSubsidyNatureName = prpCsubsidySchema
			.getSubsidyNatureName(); 
			strSubsidySortName = prpCsubsidySchema.getSubsidySortName(); 
			strCurrencyCode = prpCsubsidySchema.getCurrency(); 
			dbPrpDcurrency.getInfo(strCurrencyCode);
			strCurrencyCName = dbPrpDcurrency.getCurrencyCName();
			strSubsidyRate = new DecimalFormat("#,##0.0000").format(Double
		                     .parseDouble(Str.chgStrZero(prpCsubsidySchema.getSubsidyRate()))); 
			strSubsidyPremium = new DecimalFormat("#,##0.00").format(Double
		                     .parseDouble(Str.chgStrZero(prpCsubsidySchema.getSubsidyPremium()))); 

			listSubsidy.add(strAgriPolicySortName);
			listSubsidy.add(strSubsidyNatureName);
			listSubsidy.add(strSubsidySortName);
			listSubsidy.add(strCurrencyCName);
			listSubsidy.add(strSubsidyRate);
			listSubsidy.add(strSubsidyPremium);

			listSubsidys.add(listSubsidy);
		}
	} else {
	    
		strFrame3 = "vsides";
		strFrame4 = "above";
		strDisPlay2 = "none";
	}

	
	sumAmountMain = new DecimalFormat("#,##0.00")
			.format(Double.parseDouble(Str.chgStrZero(prpCmainSchema
			.getSumAmount())));
	strSumAmountMain = Money.toChineseMoney(Double.parseDouble(Str
			.chgStrZero(prpCmainSchema.getSumAmount())),
			strCurrencyCode);
	
	sumPremiumMain = new DecimalFormat("#,##0.00")
			.format(Double.parseDouble(Str.chgStrZero(prpCmainSchema
			.getSumPremium())));
	strSumPremiumMain = Money.toChineseMoney(Double.parseDouble(Str
			.chgStrZero(prpCmainSchema.getSumPremium())),
			strCurrencyCode);

	
	if (blPrpCplan.getSize() > 1) {
		strPlanDate = "详见缴费计划清单";
	} else {
		strPlanDate = blPrpCplan.getArr(0).getPlanDate();
		strPlanDateYear = "" + new Date(strPlanDate).get(Date.YEAR);
		strPlanDateMonth = "" + new Date(strPlanDate).get(Date.MONTH);
		strPlanDateDay = "" + new Date(strPlanDate).get(Date.DATE);
		strPlanDate = strPlanDateYear + "年" + strPlanDateMonth + "月"
		+ strPlanDateDay + "日";
	}

	
	strStartDate = prpCmainSchema.getStartDate();
	strEndDate = prpCmainSchema.getEndDate();
	strStartDateYear = "" + new Date(strStartDate).get(Date.YEAR);
	strStartDateMonth = "" + new Date(strStartDate).get(Date.MONTH);
	strStartDateDay = "" + new Date(strStartDate).get(Date.DATE);
	strStartHour = prpCmainSchema.getStartHour();
	strEndDateYear = "" + new Date(strEndDate).get(Date.YEAR);
	strEndDateMonth = "" + new Date(strEndDate).get(Date.MONTH);
	strEndDateDay = "" + new Date(strEndDate).get(Date.DATE);
	strEndHour = prpCmainSchema.getEndHour();

	strPeriod = "自 " + strStartDateYear + "年" + strStartDateMonth + "月"
			+ strStartDateDay + "日" + strStartHour + "时起，至 "
			+ strEndDateYear + "年" + strEndDateMonth + "月"
			+ strEndDateDay + "日" + strEndHour + "时止。";

	
	strArgueSolution = prpCmainSchema.getArgueSolution();
	if (strArgueSolution.equals("1"))
		strArgueSolution = "诉讼";
	else if (strArgueSolution.equals("2"))
		strArgueSolution = "提交" + prpCmainSchema.getArbitBoardName();

	
	uiEngageSeparator = new UIEngageSeparator(blPrpCengage);
	blPrpCengageT = uiEngageSeparator.getCengageT();

	StringBuffer strBuffer = new StringBuffer();
	StringBuffer strBufferTX001 = new StringBuffer();
	StringBuffer strBufferTX002 = new StringBuffer();
	String strClause = "";
	PrpCengageSchema prpCengageSchema = null;

	strBuffer.setLength(0);
	strBufferTX001.setLength(0);
	strBufferTX002.setLength(0);

	
	for (i = 0; i < blPrpCengageT.getSize(); i++) {
		prpCengageSchema = blPrpCengageT.getArr(i);

		
		if (prpCengageSchema.getClauseCode().equals("T9998"))
		{
			continue;
		}
		
		if (prpCengageSchema.getClauseCode().equals("TX001"))
		{
			if (prpCengageSchema.getTitleFlag().equals("0"))
			{
		      continue;
		    }
			strClause = prpCengageSchema.getClauses();
			
			strClause = Str.replace(strClause, "\r\n", "<br>");
			strClause = Str.replace(strClause, " ", "&nbsp;");
			strBufferTX001.append(strClause);
		}else if (prpCengageSchema.getClauseCode().equals("TX002"))
		{
			if (prpCengageSchema.getTitleFlag().equals("0"))
			{
		      continue;
		    }
			strClause = prpCengageSchema.getClauses();
			
			strClause = Str.replace(strClause, "\r\n", "<br>");
			strClause = Str.replace(strClause, " ", "&nbsp;");
			strBufferTX002.append(strClause);
		} else 
		{
			if (Integer.parseInt(prpCengageSchema.getLineNo()) > 1) {
		    strClauses = strClauses + prpCengageSchema.getClauses()
				+ "\\r\\n";
			}
			strClauses = strClauses + "<BR>";
		}
	}
	
	int intLimitLine = 5;
	int intEngageCountTmp1 = 1; 
	String strClausesTmp = ""; 

	for (i = 0; i < strClauses.length(); i++) {
		if (strClauses.substring(i, i + 1).equals("\\")) {
			if (!(strClauses.substring(i).length() < 4)) {
		if (strClauses.substring(i, i + 4).equals("\\r\\n")) {
			intEngageCountTmp1 += 1; 
			if (intEngageCountTmp1 == intLimitLine) {
				strClausesTmp = strClauses.substring(0, i + 4) 
				+ "更多内容，请详见清单...";
			}
		}
			}
		}
	}
	strClausesTmp = Str.decode(strClausesTmp);
	strClauses = Str.decode(strClauses);
	if (strBufferTX001.length() == 0)
		strBufferTX001.append("无");
	if (strClausesTmp.length() > 0) {
		strBuffer.append(strClausesTmp);
	} else {
		strBuffer.append(strClauses);
	}
	
	strOperateDate = prpCmainSchema.getOperateDate();
	strOperateDateYear = "" + new Date(strOperateDate).get(Date.YEAR);
	strOperateDateMonth = "" + new Date(strOperateDate).get(Date.MONTH);
	strOperateDateDay = "" + new Date(strOperateDate).get(Date.DATE);
	strOperateDate = strOperateDateYear + "年" + strOperateDateMonth
			+ "月" + strOperateDateDay + "日";

	
	strHandlerCode = prpCmainSchema.getHandlerCode();
	blPrpDuser = new BLPrpDuser();
	strHandlerName = blPrpDuser
			.translateCode(strHandlerCode, isChinese);
	strOperatorCode = prpCmainSchema.getOperatorCode();
	strOperatorName = blPrpDuser.translateCode(strOperatorCode,
			isChinese);
	strUnderwriteName = prpCmainSchema.getUnderWriteName();

	strComCode = prpCmainSchema.getComCode();
	strLanguage = prpCmainSchema.getLanguage();
	dbPrpDcompany.getInfo(strComCode);
	strComName.append("b");
	strComName.append(dbPrpDcompany.getComCName());
	strComAddressName = dbPrpDcompany.getAddressCName();

	strComPhoneNumber = dbPrpDcompany.getPhoneNumber();
	strComPostCode = dbPrpDcompany.getPostCode();
%>
