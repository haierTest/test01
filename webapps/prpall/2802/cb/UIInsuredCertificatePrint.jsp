<%@page import="com.sp.prpall.schema.PrpCnameSchema"%>
<%@page import="com.sp.prpall.blsvr.cb.BLPrpCname"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.sp.prpall.blsvr.cb.BLPrpCmain"%>
<%@page import="com.sp.prpall.schema.PrpCmainSchema"%>
<%@page import="com.sp.prpall.blsvr.misc.BLPrpAgriInfo"%>
<%@page import="com.sp.utility.string.Str"%>
<%@page import="com.sp.prpall.blsvr.cb.BLPolicy"%>
<%@page import="com.sp.prpall.blsvr.cb.BLPrpCitemKind"%>
<%@page import="com.sp.prpall.ui.UIItemKindSeparator"%>
<%@page import="com.sp.prpall.schema.PrpAgriInfoSchema"%>
<%@page import="com.sp.prpall.schema.PrpCsubsidySchema"%>
<%@page import="com.sp.prpall.schema.PrpCinsuredSchema"%>
<%@page import="com.sp.utility.string.Date"%>
<%@page import="com.sp.prpall.blsvr.cb.BLPrpCengage"%>
<%@page import="com.sp.prpall.ui.UIEngageSeparator"%>
<%@page import="com.sp.utiall.dbsvr.DBPrpDcompany"%>
<%@page import="com.sp.visa.interf.VisaInterfaceForPrpAction"%>
<%@page import="com.sp.sysframework.exceptionlog.UserException"%>
<%@page import="java.text.*"%>
<%--modify by echaonan 20100908 begin XX打印生僻字乱码 --%>
<%--<%@page contentType="text/html;charset=gb2312"%>--%>
<%@page contentType="text/html;charset=gbk"%>
<%--modify by echaonan 20100908 end XX打印生僻字乱码 --%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="com.sp.utility.SysConfig"%>
<%@page import="com.sp.sysframework.common.datatype.DateTime"%>
<%@page import="com.sp.prpall.schema.PrpCitemKindSchema"%>
<%@page import="com.sp.prpall.blsvr.cb.BLPrpCaddress"%>
<%@page import="com.sp.prpall.schema.PrpCaddressSchema"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+":
   String strInsuredCode= ""; 
   
   String strSerialNo = "";             
   
   
   String strRiskCode    = (String)session.getValue("RiskCode");   
   String strTitle = "";
   if("2801".equals(strRiskCode)||"2891".equals(strRiskCode)){
    strTitle = "养殖业XX凭证";
   }else{
    strTitle = "种植业XX凭证";
   }
   
   
   String strBizNo = request.getParameter("BizNo");   
   String strEndorseNo = request.getParameter("EndorseNo");   
   String chooseCheckString=request.getParameter("chooseCheckString");
   String indexNo=request.getParameter("chooseIndex");
   String[] arrIndexNo = indexNo.split(",");
	BLPolicy blPolicy = new BLPolicy();
	blPolicy.getData(strBizNo);
	
	PrpCmainSchema prpCmainSchema = new PrpCmainSchema();
	if(blPolicy.getBLPrpCmain().getSize()>0){
	prpCmainSchema = blPolicy.getBLPrpCmain().getArr(0);
	}
	BLPrpCitemKind blPrpCitemKind = null;
	BLPrpCitemKind blPrpCitemKindMain = null;
	BLPrpCitemKind blPrpCitemKindSub = null;
	UIItemKindSeparator uiItemKindSeparator = null;
	blPrpCitemKind = blPolicy.getBLPrpCitemKind();
	
	
	uiItemKindSeparator = new UIItemKindSeparator(blPrpCitemKind);
	blPrpCitemKindMain = uiItemKindSeparator.getCMain();
	
	String kindName = "";
	String deductiblerate = "";
	String deductible = "";
	if(blPrpCitemKindMain.getSize()>0){
	kindName = blPrpCitemKindMain.getArr(0).getKindName();
	deductible = blPrpCitemKindMain.getArr(0).getDeductible();
	deductiblerate = blPrpCitemKindMain.getArr(0).getDeductibleRate();
	}
   
   PrpCnameSchema prpCnameSchema = new PrpCnameSchema();
  String proposalNo = prpCmainSchema.getProposalNo();
   
   String DZNumberString=request.getParameter("DZNumberString");
   String[] DZNumberStrings = DZNumberString.split(",");
   String strVisaCode = SysConfig.getProperty("2802VisaCode");
   String strUserCode    = (String)session.getValue("UserCode"); 
   String DZNumberStringForCheck = "";
   String strErrorMessage = "";
   String VisaSucess = "1";   
   String PoicyBizType = "P";
   
   VisaInterfaceForPrpAction visaInterfaceForPrpAction = new VisaInterfaceForPrpAction();
   for(int i=1;i<DZNumberStrings.length; i++){
	   DZNumberStringForCheck = DZNumberStrings[i];
	   try{
   	   	visaInterfaceForPrpAction.checkVisaCodeValid(strVisaCode, DZNumberStringForCheck, strUserCode);
	   }catch(UserException e){
		   e.printStackTrace();
		   strErrorMessage = e.getErrorMessage();
		   VisaSucess = "0";
		   break;
	   }
		   
   }
   Map SerTOVscode = new HashMap(); 
   String SerTOVscodes = "";
   
  
	for(int i=0;i<arrIndexNo.length;i++){
	}
   
         String[] arrChooseflag=null ;
   
        int index = 0;
    
        int length = 0;
        if(chooseCheckString == null ||  chooseCheckString.equals(""))
        {
            arrChooseflag=new String[0];
        }
        if(chooseCheckString.equals("")  || chooseCheckString.length() < 1)
        {
             arrChooseflag= (new String[] { chooseCheckString});
        }
        String strTemp = chooseCheckString;
        do
        {
            if(strTemp == null || strTemp.equals(""))
            {
                break;
            }
            index = strTemp.indexOf("|");
            if(index == -1)
            {
                break;
            }
            length++;
            strTemp = strTemp.substring(index + 1);
        } while(true);
        arrChooseflag = new String[++length];
        for(int k = 0; k < length - 1; k++)
        {
            index = chooseCheckString.indexOf("|");
            arrChooseflag[k] = chooseCheckString.substring(0, index);
            chooseCheckString = chooseCheckString.substring(index + 1);
        }
        arrChooseflag[length - 1] = chooseCheckString;
  int arrlength=arrChooseflag.length;
  BLPrpCname blPrpCname = new BLPrpCname();
  blPrpCname.query("Policyno = '"+strBizNo+"' order by age", 0);
  if(blPrpCname.getSize()<=0){
  	 throw new Exception("没有导入被XX人资料，无法打印凭证");
  }
  
  String [][] PrpCnameArr = new String[++length][12];
  
   for(int h=1;h<arrlength;h++) {
   	  String identifyNumber = arrChooseflag[h];
   	  int indexp = 0;
	  for(int i=0;i<blPrpCname.getSize();i++){
	  	prpCnameSchema = blPrpCname.getArr(i);
		  	if(identifyNumber.equals(prpCnameSchema.getIdentifyNumber())){
		  	if(indexp==0){
		  	
		  	    if("2801".equals(strRiskCode)||"2891".equals(strRiskCode)){	
		  	     PrpCnameArr[h][3] = arrlength+"";
		  	    }else{
		  		 PrpCnameArr[h][3] = prpCnameSchema.getLandCoverArea();
		  		}
		  		PrpCnameArr[h][7] =String.valueOf(Double.parseDouble(prpCnameSchema.getSumLimit()));
		  	}else{
		  		PrpCnameArr[h][7] = String.valueOf(Double.parseDouble(PrpCnameArr[h][7]) + Double.parseDouble(prpCnameSchema.getSumLimit()));
		  		if("2801".equals(strRiskCode)||"2891".equals(strRiskCode)){	
		  	     PrpCnameArr[h][3] = arrlength+"";
		  	    }else{
		  		 PrpCnameArr[h][3] = String.valueOf(Double.parseDouble(PrpCnameArr[h][3]) + Double.parseDouble(prpCnameSchema.getLandCoverArea()));
		  		}
		  		
		  	}
		  	PrpCnameArr[h][0] = prpCnameSchema.getInsuredName();
		  	PrpCnameArr[h][1] = prpCnameSchema.getIdentifyNumber();
		  	PrpCnameArr[h][2] = prpCnameSchema.getInsuredName();
		  	PrpCnameArr[h][4] = prpCnameSchema.getUnitAmount();
		  	PrpCnameArr[h][5] = String.valueOf(Double.parseDouble(PrpCnameArr[h][3]) * Double.parseDouble(PrpCnameArr[h][4]));
		  	PrpCnameArr[h][8] = prpCnameSchema.getAge();
		  	PrpCnameArr[h][6] = prpCnameSchema.getRate();
		  	PrpCnameArr[h][9] = prpCnameSchema.getKindAdress();
		  	PrpCnameArr[h][10] = prpCnameSchema.getKindName();
		  	
		  	if("2801".equals(strRiskCode)||"2891".equals(strRiskCode)){
		  	  PrpCitemKindSchema prpCitemKindSchema = new PrpCitemKindSchema();
		  	  double Amount = 0.0d;
		  	  double Quantity = 0.0d;
		  	  double rate = 0.0d;
		  	  if(blPrpCitemKind.getSize()>0){
		  	    for(int j=0;j<blPrpCitemKind.getSize();j++){
		  	       prpCitemKindSchema = blPrpCitemKind.getArr(j);
		  	       Amount+=  Double.parseDouble(prpCitemKindSchema.getAmount());
		  	       Quantity+=  Double.parseDouble(prpCitemKindSchema.getQuantity());
		  	       rate+= Double.parseDouble(prpCitemKindSchema.getRate());
		  	    }
		  	  }
		  	  rate = rate/10;
		  	  BLPrpCaddress blPrpCaddress = new BLPrpCaddress();
		  	  PrpCaddressSchema prpCaddressSchema = new PrpCaddressSchema();  
		  	  if(blPolicy.getBLPrpCaddress().getSize()>0){
		  	  prpCaddressSchema = blPolicy.getBLPrpCaddress().getArr(0);
		  	  }
		  	  PrpCnameArr[h][4] = String.valueOf(Amount/Quantity);
		  	  PrpCnameArr[h][5] = String.valueOf(Double.parseDouble(PrpCnameArr[h][3]) * Amount/Quantity);
		  	  PrpCnameArr[h][6] = String.valueOf(rate);
		  	  PrpCnameArr[h][9] = prpCaddressSchema.getAddressName();
		  	  if(blPrpCitemKindMain.getSize()>0){
		  	   PrpCnameArr[h][10] = blPrpCitemKindMain.getArr(0).getKindName();
		  	  }
		  	}
		  	
		  	
		  	PrpCnameArr[h][11] = DZNumberStrings[h];
		  	SerTOVscode.put(prpCnameSchema.getAge(), PrpCnameArr[h][11]);
		  	SerTOVscodes = SerTOVscodes +","+prpCnameSchema.getAge()+":"+PrpCnameArr[h][11];
		        
		  	indexp++;
		  	}else{
		  		continue;
		  	}
	   }
  }

PrpCsubsidySchema prpCsubsidySchema1 = new PrpCsubsidySchema();
PrpCsubsidySchema prpCsubsidySchema2 = new PrpCsubsidySchema();
PrpCsubsidySchema prpCsubsidySchema3 = new PrpCsubsidySchema();
PrpCsubsidySchema prpCsubsidySchema4 = new PrpCsubsidySchema();
	for(int sub=0;sub<blPolicy.getBLPrpCsubsidy().getSize();sub++){
		int subsidySort =  Integer.parseInt(blPolicy.getBLPrpCsubsidy().getArr(sub).getSubsidySort());
		switch(subsidySort){
			case 1:{
			prpCsubsidySchema1 = blPolicy.getBLPrpCsubsidy().getArr(sub);
			break;
			}
			case 2:{
			prpCsubsidySchema2 = blPolicy.getBLPrpCsubsidy().getArr(sub);
			break;
			}
			case 3:{
			prpCsubsidySchema3 = blPolicy.getBLPrpCsubsidy().getArr(sub);
			break;
			}
		}
	}
		double  SubsidyRate1 = "".equals(prpCsubsidySchema1.getSubsidyRate())?0:Double.parseDouble(prpCsubsidySchema1.getSubsidyRate());
		double  SubsidyRate2 = "".equals(prpCsubsidySchema2.getSubsidyRate())?0:Double.parseDouble(prpCsubsidySchema2.getSubsidyRate());
		double  SubsidyRate3 = "".equals(prpCsubsidySchema3.getSubsidyRate())?0:Double.parseDouble(prpCsubsidySchema3.getSubsidyRate());
		double  SubsidyRate4 = 100-(SubsidyRate1+SubsidyRate2+SubsidyRate3);
		prpCsubsidySchema4.setSubsidyRate(String.valueOf(SubsidyRate4));

 PrpCinsuredSchema PrpCinsuredSchema = new PrpCinsuredSchema();
 String strAppliName = "";
 String pastcode = "";
 for(int j=0;j<blPolicy.getBLPrpCinsured().getSize();j++){
 PrpCinsuredSchema = blPolicy.getBLPrpCinsured().getArr(0);
 if ("2".equals(PrpCinsuredSchema.getInsuredFlag())) {
			strAppliName = PrpCinsuredSchema.getInsuredName(); 
			pastcode = PrpCinsuredSchema.getPostCode();
			}
 }

 String	strStartDate = prpCmainSchema.getStartDate();
 String	strEndDate = prpCmainSchema.getEndDate();
 String	strStartDateYear = "" + new Date(strStartDate).get(Date.YEAR);
 String	strStartDateMonth = "" + new Date(strStartDate).get(Date.MONTH);
 String	strStartDateDay = "" + new Date(strStartDate).get(Date.DATE);
 String	strStartHour = prpCmainSchema.getStartHour();
 String	strEndDateYear = "" + new Date(strEndDate).get(Date.YEAR);
 String	strEndDateMonth = "" + new Date(strEndDate).get(Date.MONTH);
 String	strEndDateDay = "" + new Date(strEndDate).get(Date.DATE);
 String	strEndHour = prpCmainSchema.getEndHour();
 String strPeriod = "自 " + strStartDateYear + "年" + strStartDateMonth + "月"
			+ strStartDateDay + "日" + strStartHour + "时起，至 "
			+ strEndDateYear + "年" + strEndDateMonth + "月"
			+ strEndDateDay + "日" + strEndHour + "时止。";

 String strStart = strStartDateYear+"/"+strStartDateMonth+"/"+strStartDateDay+" 00:00:00";       
 String strEnd   = strEndDateYear+"/"+strEndDateMonth+"/"+strEndDateDay+" 24:00:00";    
 
  SimpleDateFormat formatter = new SimpleDateFormat ("yyyy/MM/dd HH:mm:ss"); 
  ParsePosition pos1 = new ParsePosition(0); 
  ParsePosition pos2 = new ParsePosition(0); 
  java.util.Date dt1=formatter.parse(strStart,pos1); 
  java.util.Date dt2=formatter.parse(strEnd,pos2); 
  int intDays = (int)((dt2.getTime() - dt1.getTime())/(24  *  3600  *  1000));

  BLPrpCengage blPrpCengage = null;
  BLPrpCengage blPrpCengageT = null;
  UIEngageSeparator uiEngageSeparator = null;
  blPrpCengage = blPolicy.getBLPrpCengage();
  uiEngageSeparator = new UIEngageSeparator(blPrpCengage);
  blPrpCengageT = uiEngageSeparator.getCengageT();
  String strPrpCengageT = "";
  if(blPrpCengageT.getSize()>0){
  strPrpCengageT = "详见清单";
  }


  DBPrpDcompany dbPrpDcompany = new DBPrpDcompany();
  try
  {
    dbPrpDcompany.getInfo(prpCmainSchema.getComCode()); 
  }
  catch(Exception e1)
  {
    dbPrpDcompany=new DBPrpDcompany();
  }
  
  String strComAddressName = dbPrpDcompany.getAddressCName();
  
  String strComPostCode    = dbPrpDcompany.getPostCode(); 
  
  String strPhoneNumber = dbPrpDcompany.getPhoneNumber();
 
String strSysDate = Str.getYear()+"年"+Str.getMonth()+"月"+Str.getDay()+"日";   
 for(int h=1;h<arrlength;h++)
 { 
 	  
 	  strSerialNo = arrChooseflag[h];
 	  
 	  
 	  String strSerialNo2 = "";
 	  strSerialNo2 = arrIndexNo[h];
      String str = "0000000";
      strSerialNo2 = str.substring(0, 7-strSerialNo2.length())+strSerialNo2;
      
      
      
      String VisaSerNo = "";
      VisaSerNo = PrpCnameArr[h][11];
      
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title><%=strTitle %></title>
<link rel="stylesheet" type="text/css" href="/prpall/css/Standard.css">
<style type="text/css">
.input_text{
border-color:#000000;
border-style:solid;
border-top-width:0px;
border-right-width:0px;
border-bottom-width:1px;
border-left-width:0px;
}
</style>
</head>

<body style="font-family:宋体;font-size:10pt">
<table width="100%" border="0" align="center">
  <tr>
    <td width="60%" style="font-size:10pt"><b><%=strTitle %></b></td>
  </tr>
  <tr>
  	<% 
  	if(strEndorseNo != null &&!"".equals(strEndorseNo)){
  	   
  		PoicyBizType = "E";
  		
  	%>
  	 <td>兹根据<input name="policyno" type="text" class="input_text"disabled="disabled" style="width:160px;text-align:center;" value="<%=strEndorseNo%>"/>
  	<%
  	}else{
  	%>
    <td>兹根据<input name="policyno" type="text" class="input_text"disabled="disabled" style="width:140px;text-align:center;" value="<%=strBizNo%>"/>
  	<%
  	}
  	%>
	<%--modify by xianglijun 20140802  begin 农户销号 --%>
	号XX单签发此XX凭证，未尽事项以XX单为准。  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;单证识别码：&nbsp;<%=VisaSerNo%>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;NO.<%=PrpCnameArr[h][8]%></td>
	<%--modify by xianglijun 20140802  end 农户销号 --%>
  </tr>
</table>
<table  name="table1" border=1 width="100%" align="center"
				cellspacing="0" cellpadding="2" style="border-collapse:collapse"
				bordercolor="#111111" style="font-family:宋体;">
  <tr style="text-align: center; BACKGROUND-COLOR: #FFFFFF; font-weight: ">
    <td colspan="3"  style=" text-align:left">被XX人：<%=PrpCnameArr[h][0].replaceAll("\\？", "")%> </th>
    <td colspan="9"  style=" text-align:left">被XX人组织机构代码/身份证号：<%=strSerialNo.replaceAll("\\？", "") %></tr>
  <tr  bgcolor='#FFFFFF'>
    <td colspan="12" style=" text-align:left">被XX人地址：<%=PrpCnameArr[h][9].replaceAll("\\？", "")%></td>
    </tr>
  <tr bgcolor='#FFFFFF'>
    <td>XX标的项目</td>
    <%--add by zhangliming 20150126 begin 关于农XXXXX2801养殖业通用XXXXX改造的申请--%>
    <% if("2801".equals(strRiskCode)||"2891".equals(strRiskCode)){%>
    <td colspan="2">XX数量（头）</td>
    <td colspan="2">XX金额（元）</td>
    <td colspan="5">每次事故免赔额/率</td>
    <td colspan="1">XX费率（%）</td>
    <td colspan="1">XX费（元）</td>
    <%} else{%>
    <td colspan="2">XX面积（亩）</td>
    <td colspan="2">单位XX金额（元/亩）</td>
    <td colspan="2">XX金额（元）</td>
    <td colspan="3">每次事故免赔额/率</td>
    <td>XX费率</td>
    <td>XX费（元）</td>
    <%}%>
    <%--add by zhangliming 20150126 end 关于农XXXXX2801养殖业通用XXXXX改造的申请--%>
  </tr>
  <tr  bgcolor='#FFFFFF'>
  <%--modify by zhangliming 20150310 begin 关于农XXXXX2801养殖业通用XXXXX改造的申请--%>
  <% if("2801".equals(strRiskCode)||"2891".equals(strRiskCode)){%>
    <td>&nbsp;<%=PrpCnameArr[h][10].replaceAll("\\？", "")%></td>
    <td colspan="2">&nbsp;<%=new DecimalFormat("0.00").format(Double.parseDouble(PrpCnameArr[h][3]))%></td>
    <td colspan="2">&nbsp;<%=new DecimalFormat("0.00").format(Double.parseDouble(PrpCnameArr[h][5]))%></td>
    <td colspan="5">&nbsp;<%=new DecimalFormat("0.00").format(Double.parseDouble(deductible))%>/<%=new DecimalFormat("0.00").format(Double.parseDouble(deductiblerate))%></td>
    <td colspan="1">&nbsp;<%=new DecimalFormat("0.00").format(Double.parseDouble(PrpCnameArr[h][6]))%></td>
    <td colspan="1">&nbsp;<%=new DecimalFormat("0.00").format(Double.parseDouble(PrpCnameArr[h][7]))%></td>
   <% }else{%>
    <td>&nbsp;<%=PrpCnameArr[h][10].replaceAll("\\？", "")%></td>
    <td colspan="2">&nbsp;<%=new DecimalFormat("0.00").format(Double.parseDouble(PrpCnameArr[h][3]))%></td>
    <td colspan="2">&nbsp;<%=new DecimalFormat("0.00").format(Double.parseDouble(PrpCnameArr[h][4]))%></td>
    <td colspan="2">&nbsp;<%=new DecimalFormat("0.00").format(Double.parseDouble(PrpCnameArr[h][5]))%></td>
    <td colspan="3">&nbsp;<%=new DecimalFormat("0.00").format(Double.parseDouble(deductible))%>/<%=new DecimalFormat("0.0000").format(Double.parseDouble(deductiblerate))%></td>
    <td>&nbsp;<%=new DecimalFormat("0.0000").format(Double.parseDouble(PrpCnameArr[h][6]))%></td>
    <td>&nbsp;<%=new DecimalFormat("0.00").format(Double.parseDouble(PrpCnameArr[h][7]))%></td>
   <% } %>
   <%--modify by zhangliming 201500310 end 关于农XXXXX2801养殖业通用XXXXX改造的申请--%>
  </tr>
  <tr  bgcolor='#FFFFFF'>
    <td rowspan="3">XX费构成</td>
    <td colspan="2">交付单位</td>
    <td colspan="2">中央财政</td>
    <td colspan="2">省财政</td>
    <td colspan="3">市、县（区）财政</td>
    <td>农户</td>
    <td>其他</td>
  </tr>
  <%
	
      
	String StrSubsidyRate1 = "";
	String StrSubsidyRate2 = "";
	String StrSubsidyRate3 = "";
	if("2801".equals(strRiskCode)||"2891".equals(strRiskCode)){
	 StrSubsidyRate1 = SubsidyRate1==0?"":new DecimalFormat("0.00").format(Double.parseDouble(prpCsubsidySchema1.getSubsidyRate()));
	 StrSubsidyRate2 = SubsidyRate2==0?"":new DecimalFormat("0.00").format(Double.parseDouble(prpCsubsidySchema2.getSubsidyRate()));
	 StrSubsidyRate3 = SubsidyRate3==0?"":new DecimalFormat("0.00").format(Double.parseDouble(prpCsubsidySchema3.getSubsidyRate()));
        }else{
          StrSubsidyRate1 = SubsidyRate1==0?"":new DecimalFormat("0.0000").format(Double.parseDouble(prpCsubsidySchema1.getSubsidyRate()));
	  StrSubsidyRate2 = SubsidyRate2==0?"":new DecimalFormat("0.0000").format(Double.parseDouble(prpCsubsidySchema2.getSubsidyRate()));
	  StrSubsidyRate3 = SubsidyRate3==0?"":new DecimalFormat("0.0000").format(Double.parseDouble(prpCsubsidySchema3.getSubsidyRate()));
        }
      
   %>
  <tr  bgcolor='#FFFFFF'>
    <td colspan="2">交付比例（%）</td>
    <td colspan="2">&nbsp;<%=StrSubsidyRate1%></td>
    <td colspan="2">&nbsp;<%=StrSubsidyRate2%></td>
    <td colspan="3">&nbsp;<%=StrSubsidyRate3%></td>
    <td>&nbsp;<%=new DecimalFormat("0.00").format(Double.parseDouble(prpCsubsidySchema4.getSubsidyRate()))%></td>
    <td>&nbsp;</td>
  </tr>
  <%
  
  double  SubsidyRate11 = "".equals(prpCsubsidySchema1.getSubsidyRate())?0:Double.parseDouble(prpCsubsidySchema1.getSubsidyRate());
  double  SubsidyRate22 = "".equals(prpCsubsidySchema2.getSubsidyRate())?0:Double.parseDouble(prpCsubsidySchema2.getSubsidyRate());
  double  SubsidyRate33 = "".equals(prpCsubsidySchema3.getSubsidyRate())?0:Double.parseDouble(prpCsubsidySchema3.getSubsidyRate());
  double premium1 = SubsidyRate11/100*Double.parseDouble(PrpCnameArr[h][7]);
  double premium2 = SubsidyRate22/100*Double.parseDouble(PrpCnameArr[h][7]);
  double premium3 = SubsidyRate33/100*Double.parseDouble(PrpCnameArr[h][7]);
  double premium4 = Double.parseDouble(PrpCnameArr[h][7])-premium1-premium2-premium3;
  String strPremium1 = premium1==0?"":new DecimalFormat("0.00").format(premium1);
  String strPremium2 = premium2==0?"":new DecimalFormat("0.00").format(premium2);
  String strPremium3 = premium3==0?"":new DecimalFormat("0.00").format(premium3);
  String strPremium4 = premium4==0?"":new DecimalFormat("0.00").format(premium4);
   %>
  <tr  bgcolor='#FFFFFF'>
    <td colspan="2">交付金额（元）</td>
    <td colspan="2">&nbsp;<%=strPremium1%></td>
    <td colspan="2">&nbsp;<%=strPremium2%></td>
    <td colspan="3">&nbsp;<%=strPremium3%></td>
    <td>&nbsp;<%=strPremium4%></td>
    <td>&nbsp;</td>
  </tr>
  <tr  bgcolor='#FFFFFF'>
    <td>标的地点及方位&nbsp;</td>
    <td colspan="11"><p>&nbsp;<%=PrpCnameArr[h][9].replaceAll("\\？", "")%></p></td>
    </tr>
  
  <tr bgcolor='#FFFFFF'>
    <td>XX期间&nbsp;</td>
    <td colspan="11">&nbsp;<%=intDays%>天，<%=strPeriod%></td>
    </tr>
  <tr bgcolor='#FFFFFF'>
    <td colspan="12">特别约定：&nbsp;&nbsp;&nbsp;&nbsp;<%=strPrpCengageT%></td>
    </tr>
</table>
<table width="100%" border="0" align="center" cellspacing="10">
  <tr width="33%">
    <td scope="col">XX人联系地址：<%=strComAddressName %></th>
    <td scope="col">&nbsp;</th>
    <td scope="col">XX公司（盖章）</th>
  </tr>
  <tr width="33%">
    <td>邮政编码：<%=strComPostCode %></td>
    <td>&nbsp;</td>
    <td><%=strSysDate %></td>
  </tr>
  <tr>
    <td>服务电话：<%=strPhoneNumber %> </td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr width="33%">
    <td>全国统一客服电话：95510</td>
    <td>&nbsp;</td>
    <td></td>
  </tr>
</table>
<%--modify by xianglijun 20130527  begin 机构调研 --%>
			<%
			if(h%2==0){
			if( h != arrlength-1){ %>
			<p style='page-break-before:always;'>
			<%
			}
	}
			}
			%>
			<%--modify by xianglijun 20130527  end 机构调研 --%>
<%-- include打印按钮 --%>
        <jsp:include page="/commonship/pub/UIPrintButton.jsp" >
		   <%-- add by xianglijun 2040801 begin  农户改造 --%>
                  <jsp:param name="SerTOVscodes" value="<%=SerTOVscodes%>" />
		  <jsp:param name="IsAgro" value="1" />
		  <jsp:param name="VisaType" value="<%=strVisaCode%>" />
		  <jsp:param name="strBizNo" value="<%=strBizNo%>" />
		   <jsp:param name="VisaSucess" value="<%=VisaSucess%>" />
		   <jsp:param name="strErrorMessage" value="<%=strErrorMessage%>" />
		   <jsp:param name="PoicyBizType" value="<%=PoicyBizType%>" />
		   
  		  <%-- add by xianglijun 2040801 end 农户改造 --%>
		  </jsp:include>
</body>
</html>
