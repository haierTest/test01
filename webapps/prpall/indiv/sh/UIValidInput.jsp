<%--
****************************************************************************
* DESC       ����������ȷ������¼��ҳ��
* Author     : X
* CREATEDATE �� 2003-02-10
* MODIFYLIST ��   Name       Date            Reason/Contents
****************************************************************************
--%>

<%--  ����bean����  --%>
<%@page errorPage="/UIErrorPage"%>
<%@page import="com.sp.utility.SysConfig"%>
<%@page import="com.sp.utility.error.*"%>

<html>
  <head>
    <title>��������ȷ������¼��ҳ��</title>
    <%-- ���ú��� --%>
    <script src="/prpall/common/pub/UICommon.js"></script>
    <%-- ҳ����ʽ  --%>
    <link rel="stylesheet" type="text/css" href="/prpall/css/Standard.css">
      <script language=javascript>
      function submitForm()
      {
        fm.submit();
      }
      function resetForm()
      {
        fm.reset();
      }
      function quickSubmit()
      {
        var varDemandNo = "";
        varDemandNo = fm.DemandNoSubmit.value;
        if(varDemandNo == "" || varDemandNo.length != 17 )
        {
          alert("������Ĳ�ѯ�벻���Ϲ��������������ѯ�룡");
        }
        else
        {
          fm.action="/prpall/indiv/sh/UIDemandShow.jsp?DemandNo=" + varDemandNo;
          fm.submit();
        }
      }
      </script>
  </head>
  <body class="interface" background="/prpall/common/images/bgCommon.gif"> 
    <form name="fm" method="post" action="/prpall/indiv/sh/UIValidInputList.jsp" >
    <input type="hidden" name="ShowType" value="ValidInput">
    <table class=common align=center>
       <tr>
         <td class=formtitle colspan="4">ȷ������¼��</td>
       </tr>
       <tr>
         <td class=title>��ѯ�룺</td>
         <td class=input><input name="DemandNoSubmit" class="common" maxlength="17"></td>
         <td class=button><input name="buttonQuickSubmit" class="button" type="button"  alt="ȷ������" value="ȷ������"  onclick="quickSubmit()"></td>
       </tr>
       <tr>
         <td class=formtitle colspan="4">��ѯ�����ѯ</td>
       </tr>
       <tr>
         <td class=title>��ѯ�룺</td>
         <td class=input>
           <select class=query name="DemandNoSign">
             <jsp:include page="/common/pub/UIStringOption.html" />
           </select><input name="DemandNo" class="common" maxlength=17>
         </td>
         <td class=input colspan=2></td>
       </tr>
       <tr>
         <td class=title>XX���ţ�</td>
         <td class=input>
           <select class=query name="ProposalNoSign">
             <jsp:include page="/common/pub/UIStringOption.html" />
           </select><input name="ProposalNo" class="common" maxlength=22>
         </td>
         <td class=title>XX�ţ�</td>
         <td class=input>
           <select class=query name="PolicyNoSign">
             <jsp:include page="/common/pub/UIStringOption.html" />
           </select><input name="PolicyNo" class="common" maxlength=22>
         </td>
       </tr>
       <tr>
         <td class=title>���ƺ��룺</td>
         <td class=input>
           <select class=query name="LicenseNoSign">
             <jsp:include page="/common/pub/UIStringOption.html" />
           </select><input name="LicenseNo" class="common" >
         </td>
         <td class=title>�������ţ�</td>
         <td class=input>
           <select class=query name="EngineNoSign">
             <jsp:include page="/common/pub/UIStringOption.html" />
           </select><input name="EngineNo" class="common">
         </td>
       </tr>
       <tr>
         <td class=title>���ܺţ�</td>
         <td class=input>
           <select class=query name="FrameNoSign">
             <jsp:include page="/common/pub/UIStringOption.html" />
           </select><input name="FrameNo" class="common" >
         </td>
         <td class=title>��ѯʱ�䣺</td>
         <td class=input>
           <select class=query name="DemandTimeSign">
             <jsp:include page="/common/pub/UIDateOption.html" />
           </select><input name="DemandTime" class="common" >
         </td>
       </tr>
       <tr>
         <td class=title>XX�޶</td>
         <td class=input>
           <select class=query name="AmountSign">
             <jsp:include page="/common/pub/UINumOption.html" />
           </select><input name="Amount" class="common" >
         </td>
         <td class=title>XX�ѣ�</td>
         <td class=input>
           <select class=query name="PremiumSign">
             <jsp:include page="/common/pub/UINumOption.html" />
           </select><input name="Premium" class="common" >
         </td>
       </tr>
    </table>                                                                            
    <table width="85%">
     <tr>
      <td colspan=1 class=button>
        <input name="buttonSubmit" class="button" type="button"  alt="��ѯ"  value="��ѯ" onclick="submitForm()">
      </td>
      <td colspan=1 class=button>
        <input name="buttonCancel" class="button" type="button"  alt="����"  value="����" onclick="resetForm()">
      </td>
    </tr>
    </table>
    </form>
  </body>
</html>
