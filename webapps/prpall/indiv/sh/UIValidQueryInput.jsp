<%--
****************************************************************************
* DESC       ����������ȷ�������ѯҳ��
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
    <title>��������ȷ�������ѯҳ��</title>
    <%-- �������� --%>
    <script src="/prpall/common/pub/UICommon.js"></script>
    <%-- ҳ����ʽ --%>
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
    </script>
  </head>
  <body class="interface" background="/prpall/common/images/bgCommon.gif">
    <form name="fm" class="common" action="/prpall/indiv/sh/UIValidQueryList.jsp" method=post>
      <table class="common" align=center>
        <tr>
         <td class=formtitle colspan="4">ȷ�������ѯ</td>
        </tr>
        <tr>
          <td class=title>ȷ���룺</td>
          <td class=input >
            <select class=query name="ValidNoSign">
              <jsp:include page="/common/pub/UIStringOption.html"/>
            </select><input name="ValidNo" class="common" maxlength=17>
          </td>
          <td class=title>��ѯ�룺</td>
          <td class=input >
            <select class=query name="DemandNoSign">
              <jsp:include page="/common/pub/UIStringOption.html"/>
            </select><input name="DemandNo" class="common" maxlength=17>
          </td>
        </tr>
        <tr>
          <td class=title>XX���ţ�</td>
          <td class=input >
            <select class=query name="ProposalNoSign">
              <jsp:include page="/common/pub/UIStringOption.html"/>
            </select><input name="ProposalNo" class="common" maxlength=22>
          </td>
          <td class=title>XX�ţ�</td>
          <td class=input >
            <select class=query name="PolicyNoSign">
              <jsp:include page="/common/pub/UIStringOption.html"/>
            </select><input name="PolicyNo" class="common" maxlength=22>
          </td>
        </tr>
        <tr>
          <td class=title>���ƺ��룺</td>
          <td class=input >
            <select class=query name="LicenseNoSign">
              <jsp:include page="/common/pub/UIStringOption.html"/>
            </select><input name="LicenseNo" class="common" maxlength=20>
          </td>
          <td class=title>�������ţ�</td>
          <td class=input >
            <select class=query name="EngineNoSign">
              <jsp:include page="/common/pub/UIStringOption.html"/>
            </select><input name="EngineNo" class="common" maxlength=30>
          </td>
        </tr>
        <tr>
          <td class=title>���ܺţ�</td>
          <td class=input >
            <select class=query name="FrameNoSign">
              <jsp:include page="/common/pub/UIStringOption.html"/>
            </select><input name="FrameNo" class="common" maxlength=30>
          </td>
          <td class=title>ȷ��ʱ�䣺</td>
          <td class=input >
            <select class=query name="ValidTimeSign">
              <jsp:include page="/common/pub/UIDateOption.html"/>
            </select><input name="ValidTime" class="common">
          </td>
        </tr>
        <tr>
          <td class=title>��XXXXX���ڣ�</td>
          <td class=input >
            <select class=query name="StartDateSign">
              <jsp:include page="/common/pub/UIDateOption.html"/>
            </select><input name="StartDate" class="common">
          </td>
          <td class=title>��XXXXX���ڣ�</td>
          <td class=input >
            <select class=query name="EndDateSign">
              <jsp:include page="/common/pub/UIDateOption.html"/>
            </select><input name="EndDate" class="common">
          </td>
        </tr>
      </table>
      <table width="85%">
        <tr>
           <td class=button>
             <input name="buttonSubmit" class="button" type="button"  alt="��ѯ"  value="��ѯ" onclick="submitForm()">
           </td>
           <td class=button>
             <input name="buttonCancel" class="button" type="button"  alt="����"  value="����" onclick="resetForm()">
           </td>
        </tr>
      </table>
    </form>
  </body>
</html>