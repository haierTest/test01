<%--
****************************************************************************
* DESC       ���������߲�ѯ����¼��ҳ��
* Author     : X
* CREATEDATE �� 2003-02-09
* MODIFYLIST ��   Name       Date            Reason/Contents
****************************************************************************
--%>

<%-- ������ --%>
<%@page errorPage="/UIErrorPage"%>

<html>
  <head>
    <title>�������߲�ѯ����¼��ҳ��</title>
    <%-- ���ú��� --%>
	  <script src="/prpall/common/pub/UICommon.js"></script>
    <%-- ����ѡ���� --%>
    <script src="/prpall/common/pub/UICodeSelect.js"></script>
    <%-- ��ҳjs���� --%>
    <script src="/prpall/indiv/sh/UIDemandInput.js"></script>
	  <%-- ҳ����ʽ  --%>
	  <link rel="stylesheet" type="text/css" href="/prpall/css/Standard.css">
	  <%-- ��ʼ�� --%>
    <jsp:include page="/indiv/sh/UIDemandInputIni.jsp" />
  </head>
  <body class="interface" onload="loadForm();"  background="/prpall/common/images/bgCommon.gif">
    
    <form name="fm" method="post" action="/prpall/indiv/sh/UIDemandInputSubmit.jsp">
    <input type="hidden" name="RiskCode" value="0501">
    <table class=common align=center>
      <tr>
        <td class=formtitle colspan="4">��ѯ����¼��</td>
      </tr>
      <tr>
        <td class=title>���ƺ��룺</td>
        <td class=input>
          <input type="text" name="LicenseNo" maxlength="12" class="common" description="���ƺ���"
            onblur="checkLength(this)">
          <%--  <img src="/prpall/common/images/markMustInput.jpg">  --%>
        </td>
        <td class=title>�������ţ�</td>
        <td class=input>
          <input type="text" name="EngineNo" maxlength="30" class="common" description="��������"
            onblur="checkLength(this)">
           <img src="/prpall/common/images/markMustInput.jpg">
        </td>
      </tr>
      <tr>
        <td class=title>���ܺţ�</td>
        <td class=input>
          <input type="text" name="FrameNo" maxlength="30" class="common" description="���ܺ�"
            onblur="checkLength(this);">
        </td>
        <td class=title>����ʹ�����ʣ�</td>
        <td class=input>
          <select name="UseNatureCode">
          </select>
        </td>
      </tr>
      <tr>
        <td class=title>�������ࣺ</td>
        <td class=input>
          <input type="text" name="CarKindCode"
            maxlength="10" class="codecode"
            description="��������" style="width:50px"
            querytype="always" codetype="CarKind" coderelation="1" codelimit="must"
            ondblclick="code_CodeSelect(this)"
            onkeyup="code_CodeSelect(this)"
            onblur="code_CodeChange(this)"
            onkeypress="return uppercaseKey()">
          <input type="text" name="CarKindName" class="readonly" readonly style="width:105px">
          <img src="/prpall/common/images/markMustInput.jpg">
        </td>
        <td class=title>�˶��ؿͣ�</td>
        <td class=input>
          <input type="text" name="SeatCount" maxlength="5" class="common"
            description="�˶��ؿ�"
            onkeypress="return pressInteger(event)"
            onblur="checkSmallint(this,'0','')">��
          <img src="/prpall/common/images/markMustInput.jpg">
        </td>
      </tr>
      <tr>
        <td class=title>��������</td>
        <td class=input>
          <input type="text" name="TonCount" maxlength="9" class="common"
            description="������"
            onkeypress="return pressDecimal(event)"
            onblur="checkDecimal(this,8,2,'','')">��
        </td>
        <td class=title>����/���ʣ�</td>
        <td class=input>
          <input type="text" name="ExhaustScale" maxlength="7" class="common"
            description="����/����"
            onkeypress="return pressDecimal(event)"
            onblur="checkDecimal(this,6,2,'','')"> L/KW
        </td>
      </tr>
      <tr>
        <td class=title>����������XXXXX�޶</td>
        <td class=input>
          <input class="common" name="Amount"
            maxlength="15" description="����������XXXXX�޶�"
            onkeypress="return pressDecimal(event)"
            onblur="return checkDecimal(this,14,2,'','')">
          <img src="/prpall/common/images/markMustInput.jpg">
        </td>
        <td class=title>�������ࣺ</td>
        <td class=input>
          <select name="LicenseType">
            <option value="">---��ѡ���������---</option>
            <jsp:include page="/indiv/sh/UILicenseType.html" />
          </select>
        </td>
      </tr>
      <tr>
        <td class=title colspan=4>
          XX���޴�
          <input name="StartDate" maxlength=10 class="common" description="��XXXXX����" onkeypress="return pressFullDate(event)" onblur="return checkStartDate(this);">
          ��ʱ����
          <input name="EndDate" maxlength=10 class="common" description="��XXXXX����" onkeypress="return pressFullDate(event)" onblur="return checkEndDate(this);">
          ��ʮ��ʱֹ
          <img src="/prpall/common/images/markMustInput.jpg">
        </td>
      </tr>
      <tr>
        <td class=input colspan=4>
          <font color=red>ע�⣺�³���Ҫ��д�����ƺ��롱��</font>
        </td>
      </tr>
    </table>
    <table width="85%">
     <tr>
      <td colspan=1 class=button>
        <input name="buttonSubmit" class="button" type="button"  alt="��ѯ����" value="��ѯ����" onclick="submitForm()">
      </td>
      <td colspan=1 class=button>
        <input name="buttonCancel" class="button" type="button"  alt="ȡ��" value="ȡ��" onclick="cancelForm()">
      </td>
    </tr>
    </table>
    </form>
  </body>
</html>
