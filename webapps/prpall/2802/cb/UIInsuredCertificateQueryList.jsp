<%--
****************************************************************************
* DESC       ：XX打印查询列表页面
* Author     : X
* CREATEDATE ：2003-07-21
* MODIFYLIST ：   Name       Date            Reason/Contents
*
****************************************************************************
--%>
<%@page errorPage="/UIErrorPage"%>

<%-- 引入bean类部分 --%>
<%@page import="com.sp.utility.*"%>
<%@page import="com.sp.utility.error.*"%>
<%@page import="com.sp.utility.string.*"%>
<%@page import="com.sp.utiall.dbsvr.*"%>
<%@page import="com.sp.prpall.dbsvr.pg.*"%>
<%@page import="com.sp.prpall.blsvr.cb.*"%>
<%@page import="com.sp.prpall.blsvr.pg.*"%>
<%@page import="com.sp.prpall.schema.*"%>
<%@page import="com.sp.utility.*"%>
<%@page import="com.sp.utility.error.*"%>
<%@page import="com.sp.utility.SysConfig"%>
<%--add by dengliwei 2013-9-17 begin --%>
<%@page import="org.apache.commons.logging.Log"%>
<%@page import="org.apache.commons.logging.LogFactory"%>
<%@page import="com.sp.prpall.blsvr.misc.BLPrpAgriInfo"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%--add by dengliwei 2013-9-17 end --%>

 <script language=javascript>
    	
    	function selectAll()
    	{
    	  var ilength = fm.chooseflag.length; 
      
    	  for(i=1;i<ilength;i++)

       {
          fm.chooseflag[i].checked=true;
       }
   		}
    		function cancelAll()
    	{
    	  var ilength = fm.chooseflag.length; 
      
    	  for(i=1;i<ilength;i++)

       {
          fm.chooseflag[i].checked=false;
       }
    	}
    	function submitForm()
    	{
    	var i = 0;
        var choosecount = 0;      
        var chooseCheckString="";
       
        var chooseIndex = "";
        

        var ilength = fm.chooseflag.length; 
      
    	  for(i=0;i<ilength;i++)
        {
          if(fm.chooseflag[i].checked)
          {         
              choosecount = choosecount+1;
              chooseCheckString=chooseCheckString+"|"+fm.chooseflag[i].value;
              
              chooseIndex = chooseIndex +","+fm.indexNo[i].value;
              
          }       
        }
    	  
    	  
    	  
          var floatDZNumberStart = parseFloat(fm.DZNumberStart.value);   
       	  var flaotDZNumberEnd = parseFloat(fm.DZNumberEnd.value);  
          var DZNumberStart = fm.DZNumberStart.value;    
       	  var DZNumberEnd = fm.DZNumberEnd.value;
          var vDZNumber = flaotDZNumberEnd-floatDZNumberStart+1;
          var DZNumberString = "";
          var varDZNumber = DZNumberStart;
      
       	  
          if(isEmpty(fm.DZNumberStart)){
        	  errorMessage("单证流水号起始数不能为空");
        	  fm.NumberStart.focus();
        	  return false;
          }else if(isEmpty(fm.DZNumberEnd)){
        	  errorMessage("单证流水号终止数不能为空");
        	  fm.NumberEnd.focus();
        	  return false;
          }     
          if(floatDZNumberStart>flaotDZNumberEnd){
        	 errorMessage("单证流水号起始数不能大于终止数");
     		 return false;     		 
          }
          if(choosecount>vDZNumber){
        	 errorMessage("单证流水号少于农户数");
      		 return false;     		 
           }
    	  var DZlength  = 0;
    	  var floatDZNumber = floatDZNumberStart;;
      	  for(i=floatDZNumberStart;i<=flaotDZNumberEnd;i++){
      		if(i!=floatDZNumberStart){
      			floatDZNumber = floatDZNumber+1;
      			varDZNumber = floatDZNumber;
      		}
      		DZlength = 10-varDZNumber.toString().length;
      		if(DZlength>=0){
      			for(var j=0; j<DZlength; j++){
      				varDZNumber = "0"+varDZNumber;
      			}
      		}
      		DZNumberString = DZNumberString + ","+varDZNumber;
      	  }
          
    	  
        
        if(choosecount>100){
    	alert("打印人数超过100请“按人数段打印”");
    	return false;
    	}
    	
        if(choosecount==0) 

        {

       alert("请选择一个分户单");

       return false;

       }     
        
   		window.open("/prpall/2802/cb/UIInsuredCertificatePrint.jsp?BizNo="+fm.BizNo.value+"&chooseCheckString="+chooseCheckString+"&chooseIndex="+chooseIndex+"&EndorseNo="+fm.EndorseNo.value+"&DZNumberString="+DZNumberString,"new","width=800,height=700,top=200,left=70,scrollbars=yes");
   	    
    	}    	
    	
    	function resetForm()
    	{
    	  fm.reset();
    	}
    	
    	function submitFormNew()
    	{
    	  var i = 0; 
          var chooseCheckString="";
       	  var vNumberStart = parseInt(fm.NumberStart.value);
       	  var vNumberEnd = parseInt(fm.NumberEnd.value);
       	  var count = fm.chooseflag.length;
          var chooseIndex = "";
          
          
          var floatDZNumberStart = parseFloat(fm.DZNumberStart.value);   
       	  var flaotDZNumberEnd = parseFloat(fm.DZNumberEnd.value);  
          var DZNumberStart = fm.DZNumberStart.value;    
       	  var DZNumberEnd = fm.DZNumberEnd.value;
          var vDZNumber = flaotDZNumberEnd-floatDZNumberStart+1;
          var DZNumberString = "";
          var varDZNumber = DZNumberStart;
          var vNumber = vNumberEnd-vNumberStart+1;
          
       	  
          if(isEmpty(fm.DZNumberStart)){
        	  errorMessage("单证流水号起始数不能为空");
        	  fm.NumberStart.focus();
        	  return false;
          }else if(isEmpty(fm.DZNumberEnd)){
        	  errorMessage("单证流水号终止数不能为空");
        	  fm.NumberEnd.focus();
        	  return false;
          }     
          if(floatDZNumberStart>flaotDZNumberEnd){
        	 errorMessage("单证流水号起始数不能大于终止数");
     		 return false;     		 
          }
          if(vNumber>vDZNumber){
        	 errorMessage("单证流水号少于农户数");
      		 return false;     		 
           }
    	  var DZlength  = 0;
    	  var floatDZNumber = floatDZNumberStart;;
      	  for(i=floatDZNumberStart;i<=flaotDZNumberEnd;i++){
      		if(i!=floatDZNumberStart){
      			floatDZNumber = floatDZNumber+1;
      			varDZNumber = floatDZNumber;
      		}
      		DZlength = 10-varDZNumber.toString().length;
      		if(DZlength>=0){
      			for(var j=0; j<DZlength; j++){
      				varDZNumber = "0"+varDZNumber;
      			}
      		}
      		DZNumberString = DZNumberString + ","+varDZNumber;
      	  }
          
          
          if(isEmpty(fm.NumberStart)){
        	  errorMessage("起始数不能为空");
        	  fm.NumberStart.focus();
        	  return false;
          }else if(isEmpty(fm.NumberEnd)){
        	  errorMessage("终止数不能为空");
        	  fm.NumberEnd.focus();
        	  return false;
          }     
          if(vNumberStart>vNumberEnd){
        	 errorMessage("起始数不能大于终止数");
     		 return false;     		 
          }
     	  if(vNumberStart>count-1){
     		  errorMessage("起始数不能大于页面总人数");
     		  fm.NumberStart.focus();
     		  return false;     		 
      	  }
     	 if(vNumberEnd>count-1){
     		 errorMessage("终止数不能大于页面总人数");
     		 fm.NumberEnd.focus();
     		 return false;     		 
       	  }
    	  if(vNumberStart==0){
    		  errorMessage("起始数不能等于0");
     		  fm.NumberStart.focus();
     		  return false;     		 
      	  }
    	  for(i=vNumberStart;i<=vNumberEnd;i++)
          {
    		  chooseCheckString=chooseCheckString+"|"+fm.chooseflag[i].value;
              chooseIndex = chooseIndex +","+fm.indexNo[i].value;    
          }
    	  fm1.chooseCheckString.value=chooseCheckString;
    	  fm1.chooseIndex.value=chooseIndex; 
    	  
    	  fm1.action = "/prpall/2802/cb/UIInsuredCertificatePrint.jsp?BizNo="+fm.BizNo.value+"&EndorseNo="+fm.EndorseNo.value+"&DZNumberString="+DZNumberString;
    	  
    	  fm1.submit();    	    
    	}      	
    	
    </script>

<%
	
	Log logger = LogFactory.getLog("RunTime");
	
  

  String strEDITTYPE      = request.getParameter(SysConfig.getProperty("EDITTYPE"));
  String strCondition1     = "";
  String strCondition2     = "";
  String strCondition     = "";
  String PrintEntryName   = "PolicyPrint";    
  String strHandlerName 	= "";  	
  String strOperatorName 	= ""; 	
  String strLink 					= "";
  String strClass         = "";   
  String strRiskCode    = (String)session.getValue("RiskCode");   
  int    index            = 0;
  int    i                = 0;
  String striOthWherePart         = "";
  DBPrpDuser 	  dbPrpDuser      = null;  			
  BLPrpCinsured      blPrpCinsured      = null;
  BLPrpCmain blPrpCmain= new BLPrpCmain(); 
  BLPrpCname blPrpCname = new BLPrpCname();
  BLPrpCPname blPrpCPname = new BLPrpCPname();
  PrpCmainSchema  prpCmainSchema = new PrpCmainSchema();
  PrpCnameSchema prpCnameSchema = new PrpCnameSchema();
  PrpCinsuredSchema  prpCinsuredSchema  = null;
  
  
  String strPOLICYSORT_BUDGET = SysConfig.getProperty("POLICYSORT_BUDGET");
  String strENDORSE_TYPE_BUDGET = SysConfig.getProperty("ENDORSE_TYPE_BUDGET");
 
 
  
  String strPrintType     = request.getParameter("PrintType");
  String strBizNo	           = request.getParameter("BizNo");
  String strIdentifyNumberSign     = request.getParameter("IdentifyNumberSign");
  String strIdentifyNumber         = request.getParameter("IdentifyNumber");
  String strInsuredCodeSign  = request.getParameter("InsuredCodeSign");  
  String strInsuredCode      = request.getParameter("InsuredCode");
  String strInsuredNameSign  = request.getParameter("InsuredNameSign");
  String strInsuredName      = request.getParameter("InsuredName");
  String EndorseNo = "";
	if(strBizNo!=null){
	  strBizNo = strBizNo.trim();
	}
    if("".equals(strBizNo)||strBizNo==null)
  {
    throw new Exception("无有效的业务号码");
  }
  
  if(strPrintType!=null && strPrintType.equals("P")){
	  blPrpCmain.getData(strBizNo);
	  if(!(blPrpCmain.getSize()>0)){
	      
    	 throw new Exception("XX号不存在！");
	  }
  }
  
   if(strPrintType!=null && strPrintType.equals("E")){
     BLPrpPhead blPrpPhead = new BLPrpPhead();
     blPrpPhead.getData(strBizNo);
      if(blPrpPhead.getSize()<=0)
    {
     	 throw new Exception("批单号不存在！");
    }else{
     PrpPheadSchema prpPheadSchema = blPrpPhead.getArr(0);
     EndorseNo = strBizNo;
     strBizNo = prpPheadSchema.getPolicyNo();
     if(
         prpPheadSchema.getUnderWriteFlag()==null||
         prpPheadSchema.getUnderWriteFlag().equals("8"))
      {
         throw new Exception("批单号"+EndorseNo+"存在已核未生效的批单，不能进行打印凭证。");
      }
    }
 }
  
  strCondition2 = "1=1";
  strCondition2 += " And Policyno = '" + strBizNo + "'  ";
  strCondition2 += Str.convertString("Employeename" ,strInsuredCode ,strInsuredCodeSign  );
  strCondition2 += Str.convertString("InsuredName" ,strInsuredName ,strInsuredNameSign  );
  strCondition2 += Str.convertString("IdentifyNumber" ,strIdentifyNumber ,strIdentifyNumberSign  );
  strCondition2 +=" order by age";
  blPrpCname.query(strCondition2,0);
  if(blPrpCname.getSize()<=0){
   throw new Exception("没有导入被XX人资料");
  }
  
   logger.info("strCondition2---------"+strCondition2);
   
%>

<html>
	<head>
	  <title>分户单打印查询列表页面</title>
	  <%-- 公用函数 --%>
	  <script src="/prpall/commonship/pub/UICommon.js"></script>
	  <%-- 页面样式  --%>
	  <link rel="stylesheet" type="text/css" href="/prpall/css/Standard.css">
	</head>
	
	<%-- 调用loadForm 初始化页面 --%>
	<body class="interface" background="/prpall/commonship/images/bgCommon.gif" >
	  <form name="fm">
	   
	    <table class="common" cellpadding="7" cellspacing="1" align="center" name="yongku">
	      <tr class=listtitle>
	       <input name="BizNo"  type="hidden"  value=<%=strBizNo%> >
	       <input name="EndorseNo"  type="hidden"  value=<%=EndorseNo%> >
	       <input name="chooseflag"  type="hidden">
	       <input name="indexNo"  type="hidden"  >
	       <%--modify by zhangliming 20150126 begin 关于农XXXXX2801养殖业通用XXXXX改造的申请 --%>
			<% if("2801".equals(strRiskCode)||"2891".equals(strRiskCode)){%>
			<td style="width:10%">&nbsp;</td>
	      	<td style="width:10%">序号</td>
	        <td style="width:25%">被XX人名称</td>
	        <td style="width:10%">证件类型</td>
	        <td style="width:15%">证件号码</td>
	        <td style="width:15%">被XX人代码</td>
	        <td style="width:15%">专用耳标号码</td>   
			<%} else{%>
			<td style="width:10%">&nbsp;</td>
	      	<td style="width:10%">序号</td>
	        <td style="width:25%">分户名称</td>
	        <td style="width:10%">证件类型</td>
	        <td style="width:15%">证件号码</td>
	        <td style="width:15%">被XX人代码</td>
	        <td style="width:15%">地块序号</td>   
			<%}%>
			<%--modify by zhangliming 20150126 end 关于农XXXXX2801养殖业通用XXXXX改造的申请 --%>
	      </tr>
<%
	index = 0;
	Map<String,String> map = new HashMap();
	for(i=0;i<blPrpCname.getSize();i++)
	{
	  if(i%2==0)
	  {
	    strClass = "listodd";
	  }
	  else
	  {
	    strClass = "listeven";
	  }
	  prpCnameSchema = blPrpCname.getArr(i);
	  
	  String index1 = prpCnameSchema.getIdentifyNumber();
	  if(map.containsKey(prpCnameSchema.getIdentifyNumber())){
	   index1 = map.get(prpCnameSchema.getIdentifyNumber());
	 	%>
	 	  <script language=javascript>
	 	  document.getElementById('addressNo<%=index1%>').innerHTML=document.getElementById('addressNo<%=index1%>').innerHTML+'、'+"<%=prpCnameSchema.getKindCode()%>";  
	 	 </script>
	 	<%
	  }else{
%>
	      <tr class="<%=strClass%>" name="tr1">

	        <td style="width:10%"> 
	         　<%-- modify by zhengxiaoluo 20100906 begin 团单优化后，团单虚拟人存在XXXXX代码一样的多条数据，调整为被XXXXX人序号 --%>
	         　<input name="chooseflag"  type="checkbox" value=<%=prpCnameSchema.getIdentifyNumber()%> >
	           <%-- modify by zhengxiaoluo 20100906 end 团单优化后，团单虚拟人存在XXXXX代码一样的多条数据，调整为被XXXXX人序号 --%>　
	         <input name="insuredCode"  type="hidden"  value=<%=prpCnameSchema.getAge()%>> 
	         </td>
	      	<td id="order<%=index%>" style="width:10%"><%=prpCnameSchema.getAge()%>
	      	<input name="indexNo"  type="hidden"  value=<%=index%>></td>
	        <td  style="width:25%"><%=prpCnameSchema.getInsuredName()%></td>       
	        <td id="identifyType<%=index%>" style="width:10%"><%="身份证号"%></td>	
	        <td id="identifyNumber<%=index%>" style="width:15%"><%=prpCnameSchema.getIdentifyNumber()%></td>
	        <td id="insuredCode<%=index%>" style="width:15%"><%=prpCnameSchema.getEmployeeName()%></td> 
	        <td id="addressNo<%=index1%>" style="width:15%"><%=prpCnameSchema.getKindCode()%></td>      
	      </tr>
<%
	}
	  map.put(prpCnameSchema.getIdentifyNumber(),index1);
  }
%>
<br>
<br>
 <tr>
	      <td colspan=2 class=button>
	      <br>
	        <Input name="buttonSelect" class="button" align="right" type="button"  alt="全选"  value="全 选" onclick="selectAll()">	       
	       
	      </td>
	       <td colspan=3 class=button>
	      <br>	       
	        <Input name="buttonCancel" class="button" align="right" type="button"  alt="取消"  value="取 消" onclick="cancelAll()">	       
	      </td>
	       <td colspan=2 class=button>
	      <br>	       
	        <Input name="buttonSubmit" class="button" align="center" type="button"  alt="打印"  value="打 印" onclick="submitForm()">
	      </td>
	     
	    </tr>
	    <%--  add by xianglijun 20140729 begin 农户优化 --%>
	    <tr>
        <td colspan=2 class=title>单证流水号段：</td>
        	<td class=input colspan=3>
	       		<input name="DZNumberStart" class="common" maxlength="20" style="width:38%" 
	          	  onkeypress="return pressDecimal(event)" onblur="checkDecimal(this,20,0,'','')">
	       		至<input name="DZNumberEnd" class="common" maxlength="20" style="width:38%" 
              	onkeypress="return pressDecimal(event)" onblur="checkDecimal(this,20,0,'','')">
              	<img src="/prpall/commonship/images/markMustInput.jpg">
        	</td>    	       
	    </tr>
	    <%--  add by xianglijun 20140729 end 农户优化 --%>
	    <tr>
        <td colspan=2 class=title>人数段：</td>
        	<td class=input colspan=3>
	       		<input name="NumberStart" class="common" maxlength="10" style="width:38%" 
	          	  onkeypress="return pressDecimal(event)" onblur="checkDecimal(this,14,0,'','')">
	       		至<input name="NumberEnd" class="common" maxlength="10" style="width:38%" 
              	onkeypress="return pressDecimal(event)" onblur="checkDecimal(this,14,0,'','')">
        	</td>    	    
	      	<td colspan=2 class=button  width=40% align="center">
	        	<Input name="buttonSubmit" type="button"  style="width:82px" alt="按人数段打印"  value="按人数段打印" onclick="submitFormNew()">	       	       
	      </td>	    
	    </tr>
	    <%--add by yanjiming 20130621 end 分户单打印优化，可通过人数段来打印--%>
	    </table>
	  </form>
	  <%--add by yanjiming 20130807 begin 分户单打印优化，可通过人数段来打印--%>
	  <form  name="fm1" method="post" target="newWin" >
	  	<input type="hidden" name="chooseCheckString" />
	  	<input type="hidden" name="chooseIndex" />
	  </form>
	  <%--add by yanjiming 20130807 end 分户单打印优化，可通过人数段来打印--%>
	</body>
</html> 