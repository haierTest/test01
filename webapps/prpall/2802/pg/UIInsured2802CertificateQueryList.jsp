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
<%@page import="org.apache.commons.logging.Log"%>
<%@page import="org.apache.commons.logging.LogFactory"%>
<%@page import="com.sp.prpall.blsvr.misc.BLPrpAgriInfo"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="com.sp.prpall.interf.Visa"%>
<%@page import="com.sp.prpall.interf.VisaLocType"%>
<%@page import="com.sp.prpall.blsvr.misc.BLPrpVisaSet"%>
<%@page import="com.sp.prpall.schema.PrpVisaSetSchema"%>


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
    	
    	function submitForm(){
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
        	  fm.DZNumberStart.focus();
        	  return false;
          }else if(isEmpty(fm.DZNumberEnd)){
        	  errorMessage("单证流水号终止数不能为空");
        	  fm.DZNumberEnd.focus();
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
        if(choosecount==0) 

        {

       alert("请选择一个分户单");

       return false;

        }
        var  visaCode = fm.LocTypeCode.value;    
   		window.open("/prpall/2802/pg/UIEndorse2802SpecialSubmit.jsp?BizNo="+fm.BizNo.value+"&chooseCheckString="+chooseCheckString+"&chooseIndex="+chooseIndex+"&EndorseNo="+fm.EndorseNo.value+"&VisaSerialNo="+DZNumberString+"&VisaCode="+visaCode,"new","width=800,height=700,top=200,left=70,scrollbars=yes");
       }    	  	
    	
    	function resetForm()
    	{
    	  fm.reset();
    	}
    </script>

<%
  
  String strEDITTYPE      = request.getParameter(SysConfig.getProperty("EDITTYPE"));
  String strCondition1     = "";
  String strCondition2     = "";
  String strCondition     = "";
  String PrintEntryName   = "PolicyPrint";    
  String strLink 					= "";
  String strClass         = "";   
  String strRiskCode    = (String)session.getValue("RiskCode");   
  int    index            = 0;
  int    i                = 0;
  String striOthWherePart         = "";
  DBPrpDuser 	  dbPrpDuser      = null;  			
  BLPrpPinsured      blPrpPinsured      = null;
  BLPrpPmain blPrpPmain= new BLPrpPmain(); 
  BLPrpPname blPrpPname = new BLPrpPname();
  BLPrpCPname blPrpCPname = new BLPrpCPname();
  PrpPmainSchema  prpPmainSchema = new PrpPmainSchema();
  PrpPnameSchema prpPnameSchema = new PrpPnameSchema();
  PrpPinsuredSchema  prpPinsuredSchema  = null;
  
  
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
  String strMessage = "";
	if(strBizNo!=null){
	  strBizNo = strBizNo.trim();
	}
    if("".equals(strBizNo)||strBizNo==null)
  {
    strMessage ="无有效的业务号码!";
  }
  
   if(strPrintType!=null && strPrintType.equals("E")){
     BLPrpPhead blPrpPhead = new BLPrpPhead();
     blPrpPhead.getData(strBizNo);
      if(blPrpPhead.getSize()<=0)
    {
     	 strMessage = "批单号不存在！";
    }else{
     PrpPheadSchema prpPheadSchema = blPrpPhead.getArr(0);
     EndorseNo = strBizNo;
     strBizNo = prpPheadSchema.getPolicyNo();
     if(strMessage.equals("")&&
         !prpPheadSchema.getUnderWriteFlag().equals("1")&&
         !prpPheadSchema.getUnderWriteFlag().equals("3"))
      {
        strMessage = "该批单未核批,不能打印批单正本!";
      }
     
     if(
         prpPheadSchema.getUnderWriteFlag()==null||
         prpPheadSchema.getUnderWriteFlag().equals("8"))
      {
        strMessage ="批单号"+EndorseNo+"存在已核未生效的批单，不能进行打印凭证！";
      }
      
      blPrpPname.getData(EndorseNo);
      if(blPrpPname.getSize()<=0){
        strMessage = "批单号"+EndorseNo+"不存在批改的农户信息!";
      }
    }
 }
  if(!(strMessage.equals("")))
  {
%>
    <jsp:forward page="/commonship/pub/UIMessagePage.jsp">
      <jsp:param name="Picture" value="F"/>
      <jsp:param name="Content" value="<%=strMessage%>"/>
    </jsp:forward>
<%
    return;
  }

  
  strCondition2 = "1=1";
  strCondition2 += " And EndorseNo = '" + EndorseNo + "'  ";
  strCondition2 += Str.convertString("Employeename" ,strInsuredCode ,strInsuredCodeSign  );
  strCondition2 += Str.convertString("InsuredName" ,strInsuredName ,strInsuredNameSign  );
  strCondition2 += Str.convertString("IdentifyNumber" ,strIdentifyNumber ,strIdentifyNumberSign  );
  strCondition2 +=" order by age";
  blPrpPname.query(strCondition2,0);
  if(blPrpPname.getSize()<=0){
   throw new Exception("没有被批改的被XX人资料");
  }
   
  
  String strBizType        = "";    
  String strComCode        = (String)session.getValue("ComCode");
  
  BLPrpVisaSet blPrpVisaSet = null;
  PrpVisaSetSchema prpVisaSetSchema = new PrpVisaSetSchema();
  String strLocTypeCodes   = "";
    strBizType  = "Endorse";
    String strCertiType = "E";                                           
    String strVisaCode1 = "";
	strVisaCode1 = new Visa().getOptionCode(strComCode,strRiskCode,strCertiType,""); 
    if(strVisaCode1==null||strVisaCode1.equals("")){
 %>
	<jsp:include page="/common/pub/UIMessagePage.jsp">
      <jsp:param name="Picture" value="F" />
      <jsp:param name="Content" value="缺少打印配置，请系统管理员维护！" />
      </jsp:include>
<%
			return;
		}
	String VisaCodeArray1[] = strVisaCode1.split("GROUP_SEPARATOR");
		String strVisaCode2 = "";
   	for(i = 0;i<VisaCodeArray1.length;i++)
   	{ 
			int x = VisaCodeArray1[i].indexOf("FIELD_SEPARATOR");
			strVisaCode2 += VisaCodeArray1[i].substring(0,x);
   	}
   	String VisaCodeArray2[] = strVisaCode2.split("__");
   	if(VisaCodeArray2.length>0)
   	{
   		for(i = 0;i<VisaCodeArray2.length;i++)
   		{
  			if(i!=VisaCodeArray2.length-1)
  			{
   				strLocTypeCodes += VisaCodeArray2[i]+";";
   			}
   			else
   			{
   				int x = VisaCodeArray2[i].indexOf("_");
   				strLocTypeCodes += VisaCodeArray2[i].substring(0,x);
   			}
   		}
   	}
     
    else
    {
%>
      <jsp:include page="/common/pub/UIMessagePage.jsp">
      <jsp:param name="Picture" value="F" />
      <jsp:param name="Content" value="缺少打印配置，请系统管理员维护！" />
      </jsp:include>
<%
      return;
    }
 
   VisaLocType[] arrVisaLocType = null;
   Visa visa = new Visa();
   arrVisaLocType = visa.queryLocType(strLocTypeCodes);  
   
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
			<td style="width:10%">&nbsp;</td>
	      	<td style="width:10%">序号</td>
	        <td style="width:25%">分户名称</td>
	        <td style="width:10%">证件类型</td>
	        <td style="width:15%">证件号码</td>
	        <td style="width:15%">被XX人代码</td>
	        <td style="width:15%">地块序号</td>      
	      </tr>
<%
	index = 0;
	Map<String,String> map = new HashMap();
	for(i=0;i<blPrpPname.getSize();i++)
	{
	  if(i%2==0)
	  {
	    strClass = "listodd";
	  }
	  else
	  {
	    strClass = "listeven";
	  }
	  prpPnameSchema = blPrpPname.getArr(i);
	  
	  String index1 = prpPnameSchema.getAge();
	  if(map.containsKey(prpPnameSchema.getAge())){
	   index1 = map.get(prpPnameSchema.getAge());
	 	%>
	 	  <script language=javascript>
	 	  document.getElementById('addressNo<%=index1%>').innerHTML=document.getElementById('addressNo<%=index1%>').innerHTML+'、'+"<%=prpPnameSchema.getKindCode()%>";  
	 	 </script>
	 	<%
	  }else{
%>
	      <tr class="<%=strClass%>" name="tr1">

	      <td style="width:10%"> 
	         　<%-- modify by zhengxiaoluo 20100906 begin 团单优化后，团单虚拟人存在XXXXX代码一样的多条数据，调整为被XXXXX人序号 --%>
	         　<input name="chooseflag"  type="checkbox" value=<%=prpPnameSchema.getAge()%> >
	     <%-- modify by zhengxiaoluo 20100906 end 团单优化后，团单虚拟人存在XXXXX代码一样的多条数据，调整为被XXXXX人序号 --%>　
	         <input name="insuredCode"  type="hidden"  value=<%=prpPnameSchema.getAge()%>> 
	         </td>
	      	<td id="order<%=index%>" style="width:10%"><%=prpPnameSchema.getAge() %>
	      	<input name="indexNo"  type="hidden"  value=<%=index%>></td>
	        <td  style="width:25%"><%=prpPnameSchema.getInsuredName()%></td>       
	        <td id="identifyType<%=index%>" style="width:10%"><%="身份证号"%></td>	
	        <td id="identifyNumber<%=index%>" style="width:15%"><%=prpPnameSchema.getIdentifyNumber()%></td>
	        <td id="insuredCode<%=index%>" style="width:15%"><%=prpPnameSchema.getEmployeeName()%></td> 
	        <td id="addressNo<%=index1%>" style="width:15%"><%=prpPnameSchema.getKindCode()%></td>      
	      </tr>
<%
	}
	  map.put(prpPnameSchema.getAge(),index1);
  }
%>
<br>
<br>
 <tr>
	      <td colspan=2 class=button>
	      <br>
	        <Input name="buttonSelect" class="button" align="right" type="button"  alt="全选"  value="全 选" onclick="selectAll();">	       
	       
	      </td>
	       <td colspan=3 class=button>
	      <br>	       
	        <Input name="buttonCancel" class="button" align="right" type="button"  alt="取消"  value="取 消" onclick="cancelAll();">	       
	      </td>
	       <td colspan=2 class=button>
	      <br>	       
	        <Input name="buttonSubmit" class="button" align="center" type="button"  alt="打印"  value="打 印"   onclick="submitForm();">
	      </td>
	     
	    </tr>
	     <tr>
            <td class="title" align="center" colspan="2" >单证类型：</td>
            <td class="input" colspan="3"  >
              <select name="LocTypeCode" style='width:50%'>
<%
                for (i=0;i<arrVisaLocType.length;i++)
                
                {
                 if(!"PZ18001".equals(arrVisaLocType[i].getLocRationType()))
                {
%>
                  <option value="<%=arrVisaLocType[i].getLocTypeCode()%>"><%=arrVisaLocType[i].getLocTypeCode()%>--<%=arrVisaLocType[i].getLocTypeName()%></option>
<%
                 }
               }
%>
              </select>
            </td>
          </tr>
	    <tr>
        <td colspan=2 class=title align="center" >单证流水号：</td>
        	<td class=input colspan=3>
	       		<input name="DZNumberStart" class="common" maxlength="20" style="width:23%" 
	          	  onkeypress="return pressDecimal(event)" onblur="checkDecimal(this,20,0,'','')">
	       		至<input name="DZNumberEnd" class="common" maxlength="20" style="width:23%" 
              	onkeypress="return pressDecimal(event)" onblur="checkDecimal(this,20,0,'','')">
              	<img src="/prpall/commonship/images/markMustInput.jpg">
        	</td>    	    
	    </tr>
	    </table>
	  </form>
	  <form  name="fm1" method="post" target="newWin" >
	  	<input type="hidden" name="chooseCheckString" />
	  	<input type="hidden" name="chooseIndex" />
	  </form>
	</body>
</html> 