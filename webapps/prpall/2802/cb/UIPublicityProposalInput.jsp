<%@page import="utils.system"%>

<%
  
  String strRiskCode =  (String)session.getAttribute("RiskCode");
  String strTitle = "";
  if("2801".equals(strRiskCode)||"2891".equals(strRiskCode)){
     strTitle = "��������Ϣ��ʾ�嵥��ӡ��ѯ";
  }else{
    strTitle = "�ֻ���ʾ�嵥��ӡ��ѯ";
  }
 

 %>


<html>
<head>
  <title><%=strTitle %></title>
  <%-- ���ú��� --%>
  <script src="/prpall/commonship/pub/UICommon.js"></script>
  <%-- ҳ����ʽ  --%>
  <link rel="stylesheet" type="text/css" href="/prpall/css/Standard.css">
  <script language='javascript'>
    function checkProposal(){
       if(isEmpty(fm.BizNo))
       {
         errorMessage("XX���Ų���Ϊ�գ�");
         fm.BizNo.focus();
         return false;
        }else{
        
          if(trim(fm.BizNo.value).substring(0,1)=="T"){
           var strProposalNo = fm.BizNo.value;
           var strPrintPage = "";
          if(fm.RiskCode.value=="2801"||fm.RiskCode.value=="2891"){
             strPrintPage = "/prpall/2801/tb/UIAnimalItemListNoneFormatPrint.jsp?BizNo="+strProposalNo+"&BIZTYPE=PROPOSAL";
             }else{
           var varUrl = "/prpall/2802/cb/UIPublicityProposalPrintCheck.jsp?BizNo=";
           var strURL = varUrl+strProposalNo;
           var vXmlText = getResponseXmlText(strURL);
           if(vXmlText.substring(1,vXmlText.length-1)!='1'){
                strPrintPage = "/prpall/2802/cb/UIPublicity2802ListNoneFormatPrint.jsp?BizNo="+strProposalNo;
             }else {
             errorMessage("�Ѻ�XXXXXͨ����");
             fm.BizNo.value = "";
             fm.BizNo.focus();
             return false;
             }
            }
            if(strPrintPage !=""){
            printWindow(strPrintPage,"��ӡ");
            }
           
          }else{
           errorMessage("����д��ȷ��XX���ţ�");
           fm.BizNo.focus();
           return false;
          }  
         }  
       } 
  </script>
</head>

<body class="interface">
  <form name="fm"  method="get" >
    <table class="three" cellpadding="5" cellspacing="1" align="center">
       <tr>
       <%-- add by zhangliming 20150123  begin ����ũXXXXX2801��ֳҵͨ��XXXXX���������--%>
	      <td class=formtitle colspan="2"><%=strTitle %>
	        <input type="hidden" name="RiskCode" value="<%=strRiskCode %>">
	      </td>
	    <%-- add by zhangliming 20150123  begin ����ũXXXXX2801��ֳҵͨ��XXXXX���������--%>
	    </tr>
      <tr>
        <td class='title' style="width:20%" align="center">XX���ţ�</td>
        <td class='input'>
          <input class="common" type='text' name='BizNo' maxlength='21' onkeypress="return uppercaseKey();" onblur="checkLength(this)"><img src="/prpall/commonship/images/markMustInput.jpg">
        </td>
      </tr>
      <tr>
        <td class=centertitle  style="width:20%" colspan="2">
          <input type="button" value="��ѯ" class="button" name="submitbutton"
            onclick="checkProposal();">
        </td>
      </tr>
    </table>
  </form>
 </body>
</html>