<%-- 错误处理页面 --%>
<%@page errorPage="/UIErrorPage"%>
<%-- 引入bean类部分 --%>
<%@page import="com.sp.utility.*"%>
<%@page import="com.sp.utility.error.*"%>
<%@page import="com.sp.utility.string.*"%>
<%@page import="com.sp.utility.string.Date"%>
<%@page import="com.sp.prpall.blsvr.cb.*"%>
<%@page import="com.sp.prpall.blsvr.pg.*"%>
<%@page import="com.sp.prpall.dbsvr.cb.*"%>
<%@page import="com.sp.prpall.dbsvr.lp.*"%>
<%@page import="com.sp.prpall.dbsvr.pg.*"%>
<%@page import="com.sp.prpall.schema.*"%>
<%@page import="com.sp.utiall.dbsvr.*"%>
<%@page import="com.sp.prpall.blsvr.pg.*"%>
<%@page import="utils.system"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="com.sp.platform.dto.domain.PrpDriskConfigDto"%>
<%@page import="com.sp.platform.ui.control.action.UIPrpDriskConfigAction"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.sp.visa.interf.VisaInterfaceForPrpAction"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>

<%
  
  String strBizNo = request.getParameter("BizNo");
  String strEndorseNo = request.getParameter("EndorseNo");
  String strRiskCode = (String)session.getValue("RiskCode");
  String strAge = "";
  BLPrpPname blPrpPname = new BLPrpPname();
  BLEndorse blEndorse = new BLEndorse();
  BLPolicy blPolicy = new BLPolicy();
  BLPrpCname blPrpCname = new BLPrpCname();
  BLPrpCmain blPrpCmain = new BLPrpCmain();
  
  String chooseCheckString=request.getParameter("chooseCheckString");
  String indexNo=request.getParameter("chooseIndex");
  String[] arrIndexNo = indexNo.split(",");
  String DZNumberString=request.getParameter("VisaSerialNo");
   String[] DZNumberStrings = DZNumberString.split(",");
   String strVisaCode = request.getParameter("VisaCode");
   String strUserCode = (String)session.getValue("UserCode");
   
   String DZNumberStringForCheck = "";
   String strErrorMessage = "";
   String VisaSucess = "1";   
   String PoicyBizType = "E";
   
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
        blPrpPname.query("EndorseNo = '"+strEndorseNo+"' order by age", 0);
        if(blPrpPname.getSize()<=0){
  	         throw new Exception("没有批改的农户资料，无法打印凭证");
        }
        
    int arrlength=arrChooseflag.length;
    
    for(int h=1;h<arrlength;h++) {
	   strAge = arrChooseflag[h];

	    
	  String strPiWherePart = "EndorseNo = '"+strEndorseNo+"'"+
	                         "and Age = '"+strAge+"'";
	 
	 blPrpPname.query(strPiWherePart);
	 blPrpCmain.getData(strBizNo);
	 for(int i=0;i<blPrpPname.getSize();i++){
        SerTOVscode.put(blPrpPname.getArr(i).getAge(), DZNumberStrings[h]);
        SerTOVscodes = SerTOVscodes +","+blPrpPname.getArr(i).getAge()+":"+DZNumberStrings[h];
	   }
	   
	 String strStartDate = blPrpCmain.getArr(0).getStartDate();
	 String	strStartDateYear = "" + new Date(strStartDate).get(Date.YEAR);
	 String	strStartDateMonth = "" + new Date(strStartDate).get(Date.MONTH);
	 String	strStartDateDay = "" + new Date(strStartDate).get(Date.DATE);
	 String	strStartHour = blPrpCmain.getArr(0).getStartHour();
	 String strPeriod = "自" + strStartDateYear + "年" + strStartDateMonth + "月"
			+ strStartDateDay + "日" + strStartHour + "时起";
	String strPtext ="";
	
    strPtext = "&nbsp;&nbsp;&nbsp;&nbsp;变更信息农户："+blPrpPname.getArr(0).getInsuredName()+"</br>"+
      "&nbsp;&nbsp;&nbsp;&nbsp;兹经被XX人申请,公司同意"+strRiskCode+"XXXXX种"+strBizNo+"号XX"+strPeriod+",对"+"</br>"+
                      "XX单作如下批改："+"</br>";
	  strPtext+= "<<变更农户信息>>"+"</br>" ;
           
      strPtext+= "    原农户" + blPrpPname.getArr(0).getInsuredName() + "作如下变更："+"</br>";
      
       
	  if(blPrpPname.getSize()>1){
	    for(int k=0;k<blPrpPname.getSize();k++){
	    String strSerialNo  =  blPrpPname.getArr(k).getSerialNo();
	      
	    String strCiWherePart = "PolicyNo = '"+strBizNo+"'"+
	                         "and SerialNo = '"+strSerialNo+"'";
	    blPrpCname.query(strCiWherePart);
	    PrpPnameSchema iPrpPnameSchema = blPrpPname.getArr(k);
	    PrpCnameSchema iPrpCnameSchema = null;
	    if(blPrpCname.getSize()>0){
	     iPrpCnameSchema = blPrpCname.getArr(0);
	    }
	  
      BLPrpPtextUsual  blPrpPtextUsual = new BLPrpPtextUsual();
      String strOption = iPrpPnameSchema.getFlag();
      if ((iPrpPnameSchema.getFlag().length() > 0) &&
            (iPrpPnameSchema.getFlag().substring(
                0, 1).equals("I"))) {
          strPtext+= blPrpPtextUsual.ptext2802ForAgriInsertDelete(iPrpPnameSchema,strOption);
        }
        if ((iPrpPnameSchema.getFlag().length() > 0) &&
            (iPrpPnameSchema.getFlag().substring(
                0, 1).equals("D"))) {
                
          strPtext+= blPrpPtextUsual.ptext2802ForAgriInsertDelete(iPrpPnameSchema,strOption);
        }
        if ((iPrpPnameSchema.getFlag().length() > 0) &&
            (iPrpPnameSchema.getFlag().substring(
                0, 1).equals("U"))) {
          strPtext+= blPrpPtextUsual.ptext2802ForAgriUpdate(iPrpCnameSchema,iPrpPnameSchema);
        }
	     
	   }
	   
	  
	 }else if(blPrpPname.getSize()==1){
	    String strSerialNo  =  blPrpPname.getArr(0).getSerialNo();
	     
	    String strCiWherePart = "PolicyNo = '"+strBizNo+"'"+
	                         "and SerialNo = '"+strSerialNo+"'";
	    blPrpCname.query(strCiWherePart);
	    PrpPnameSchema iPrpPnameSchema = blPrpPname.getArr(0);
	    PrpCnameSchema iPrpCnameSchema = null;
	    if(blPrpCname.getSize()>0){
	     iPrpCnameSchema = blPrpCname.getArr(0);
	    }
        BLPrpPtextUsual  blPrpPtextUsual = new BLPrpPtextUsual();
        String strOption = iPrpPnameSchema.getFlag();
        if ((iPrpPnameSchema.getFlag().length() > 0) &&
            (iPrpPnameSchema.getFlag().substring(
                0, 1).equals("I"))) {
          strPtext+= blPrpPtextUsual.ptext2802ForAgriInsertDelete(iPrpPnameSchema,strOption);
        }
        if ((iPrpPnameSchema.getFlag().length() > 0) &&
            (iPrpPnameSchema.getFlag().substring(
                0, 1).equals("D"))) {
                
          strPtext+= blPrpPtextUsual.ptext2802ForAgriInsertDelete(iPrpPnameSchema,strOption);
        }
        if ((iPrpPnameSchema.getFlag().length() > 0) &&
            (iPrpPnameSchema.getFlag().substring(
                0, 1).equals("U"))) {
          strPtext+= blPrpPtextUsual.ptext2802ForAgriUpdate(iPrpCnameSchema,iPrpPnameSchema);
        }
	    
	  }
	   strPtext+="本XX单所载其他的条件不变，特此批注。";  
     
%>
<html>
<head>
  <title>农户凭证批文打印显示页面</title>
  <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
  <link rel="stylesheet" type="text/css" href="/prpall/css/Standard.css">
  <script src="/prpall/commonship/pub/UICommon.js"></script>
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
<body class="interface">
  <form name="fm" method="post" action="/prpall/UICentralControl?SelfPage=<%=request.getServletPath()%>">
  <table  border="0" align="center" width="80%" >
	<tr>
	  <td>
       <%=strPtext %>
      </td>
    </tr>
  </table>
  </form>
<%
			if(h%1==0){
			if( h != arrlength-1){ 
			%>
			<p style='page-break-before:always;'>
			<%
			}
	       }
	
	 } 
%>

  <%-- include打印按钮 --%>
        <jsp:include page="/commonship/pub/UIPrintButton.jsp" >
           <jsp:param name="SerTOVscodes" value="<%=SerTOVscodes%>" />
		   <jsp:param name="IsAgro" value="1" />
		   <jsp:param name="VisaType" value="<%=strVisaCode%>" />
		   <jsp:param name="BizNo" value="<%=strEndorseNo%>" />
		   <jsp:param name="VisaSucess" value="<%=VisaSucess%>" />
		   <jsp:param name="strErrorMessage" value="<%=strErrorMessage%>" />
		   <jsp:param name="PoicyBizType" value="<%=PoicyBizType%>" />
		</jsp:include>
</body>
</html>

