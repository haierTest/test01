<%@page errorPage="/UIErrorPage"%>

<%-- ����bean�ಿ�� --%>
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
        	  errorMessage("��֤��ˮ����ʼ������Ϊ��");
        	  fm.DZNumberStart.focus();
        	  return false;
          }else if(isEmpty(fm.DZNumberEnd)){
        	  errorMessage("��֤��ˮ����ֹ������Ϊ��");
        	  fm.DZNumberEnd.focus();
        	  return false;
          }     
          if(floatDZNumberStart>flaotDZNumberEnd){
        	 errorMessage("��֤��ˮ����ʼ�����ܴ�����ֹ��");
     		 return false;     		 
          }
          if(choosecount>vDZNumber){
        	 errorMessage("��֤��ˮ������ũ����");
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

       alert("��ѡ��һ���ֻ���");

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
    strMessage ="����Ч��ҵ�����!";
  }
  
   if(strPrintType!=null && strPrintType.equals("E")){
     BLPrpPhead blPrpPhead = new BLPrpPhead();
     blPrpPhead.getData(strBizNo);
      if(blPrpPhead.getSize()<=0)
    {
     	 strMessage = "�����Ų����ڣ�";
    }else{
     PrpPheadSchema prpPheadSchema = blPrpPhead.getArr(0);
     EndorseNo = strBizNo;
     strBizNo = prpPheadSchema.getPolicyNo();
     if(strMessage.equals("")&&
         !prpPheadSchema.getUnderWriteFlag().equals("1")&&
         !prpPheadSchema.getUnderWriteFlag().equals("3"))
      {
        strMessage = "������δ����,���ܴ�ӡ��������!";
      }
     
     if(
         prpPheadSchema.getUnderWriteFlag()==null||
         prpPheadSchema.getUnderWriteFlag().equals("8"))
      {
        strMessage ="������"+EndorseNo+"�����Ѻ�δ��Ч�����������ܽ��д�ӡƾ֤��";
      }
      
      blPrpPname.getData(EndorseNo);
      if(blPrpPname.getSize()<=0){
        strMessage = "������"+EndorseNo+"���������ĵ�ũ����Ϣ!";
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
   throw new Exception("û�б����ĵı�XX������");
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
      <jsp:param name="Content" value="ȱ�ٴ�ӡ���ã���ϵͳ����Աά����" />
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
      <jsp:param name="Content" value="ȱ�ٴ�ӡ���ã���ϵͳ����Աά����" />
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
	  <title>�ֻ�����ӡ��ѯ�б�ҳ��</title>
	  <%-- ���ú��� --%>
	  <script src="/prpall/commonship/pub/UICommon.js"></script>
	  <%-- ҳ����ʽ  --%>
	  <link rel="stylesheet" type="text/css" href="/prpall/css/Standard.css">
	</head>
	
	<%-- ����loadForm ��ʼ��ҳ�� --%>
	<body class="interface" background="/prpall/commonship/images/bgCommon.gif" >
	  <form name="fm">
	   
	    <table class="common" cellpadding="7" cellspacing="1" align="center" name="yongku">
	      <tr class=listtitle>
	       <input name="BizNo"  type="hidden"  value=<%=strBizNo%> >
	       <input name="EndorseNo"  type="hidden"  value=<%=EndorseNo%> >
	       <input name="chooseflag"  type="hidden">
	       <input name="indexNo"  type="hidden"  >
			<td style="width:10%">&nbsp;</td>
	      	<td style="width:10%">���</td>
	        <td style="width:25%">�ֻ�����</td>
	        <td style="width:10%">֤������</td>
	        <td style="width:15%">֤������</td>
	        <td style="width:15%">��XX�˴���</td>
	        <td style="width:15%">�ؿ����</td>      
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
	 	  document.getElementById('addressNo<%=index1%>').innerHTML=document.getElementById('addressNo<%=index1%>').innerHTML+'��'+"<%=prpPnameSchema.getKindCode()%>";  
	 	 </script>
	 	<%
	  }else{
%>
	      <tr class="<%=strClass%>" name="tr1">

	      <td style="width:10%"> 
	         ��<%-- modify by zhengxiaoluo 20100906 begin �ŵ��Ż����ŵ������˴���XXXXX����һ���Ķ������ݣ�����Ϊ��XXXXX����� --%>
	         ��<input name="chooseflag"  type="checkbox" value=<%=prpPnameSchema.getAge()%> >
	     <%-- modify by zhengxiaoluo 20100906 end �ŵ��Ż����ŵ������˴���XXXXX����һ���Ķ������ݣ�����Ϊ��XXXXX����� --%>��
	         <input name="insuredCode"  type="hidden"  value=<%=prpPnameSchema.getAge()%>> 
	         </td>
	      	<td id="order<%=index%>" style="width:10%"><%=prpPnameSchema.getAge() %>
	      	<input name="indexNo"  type="hidden"  value=<%=index%>></td>
	        <td  style="width:25%"><%=prpPnameSchema.getInsuredName()%></td>       
	        <td id="identifyType<%=index%>" style="width:10%"><%="���֤��"%></td>	
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
	        <Input name="buttonSelect" class="button" align="right" type="button"  alt="ȫѡ"  value="ȫ ѡ" onclick="selectAll();">	       
	       
	      </td>
	       <td colspan=3 class=button>
	      <br>	       
	        <Input name="buttonCancel" class="button" align="right" type="button"  alt="ȡ��"  value="ȡ ��" onclick="cancelAll();">	       
	      </td>
	       <td colspan=2 class=button>
	      <br>	       
	        <Input name="buttonSubmit" class="button" align="center" type="button"  alt="��ӡ"  value="�� ӡ"   onclick="submitForm();">
	      </td>
	     
	    </tr>
	     <tr>
            <td class="title" align="center" colspan="2" >��֤���ͣ�</td>
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
        <td colspan=2 class=title align="center" >��֤��ˮ�ţ�</td>
        	<td class=input colspan=3>
	       		<input name="DZNumberStart" class="common" maxlength="20" style="width:23%" 
	          	  onkeypress="return pressDecimal(event)" onblur="checkDecimal(this,20,0,'','')">
	       		��<input name="DZNumberEnd" class="common" maxlength="20" style="width:23%" 
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