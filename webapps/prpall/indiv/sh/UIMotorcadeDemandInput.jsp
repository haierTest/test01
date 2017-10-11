<%--
****************************************************************************
* DESC       �����Ӳ�ѯ����¼��
* Author     : X
* CREATEDATE ��2003-03-04
* MODIFYLIST ��   Name       Date            Reason/Contents
****************************************************************************
--%>

<%-- ������ --%>
<%@page errorPage="/UIErrorPage"%>

<%@page import="com.sp.utility.error.*"%>

<%
  
  String strUserCode = (String)session.getValue("UserCode");
  String strComCode = (String)session.getValue("ComCode");
  if( strUserCode == null || strUserCode.equals("") ||
      strComCode == null  || strComCode.equals("") )
  {
    throw new UserException(-98,-1003,"UIMotorcadeDemandInput.jsp","session��ʱ,�����µ�¼!");
  }
%>

<html>
  <head>
    <title>���Ӳ�ѯ����¼��</title>
    <%-- ���ú��� --%>
	  <script src="/prpall/common/pub/UICommon.js"></script>
    <%-- ����ѡ���� --%>
    <script src="/prpall/common/pub/UICodeSelect.js"></script>
    <%-- ���в������� --%>
    <script src="/prpall/common/pub/UIMulLine.js"></script>
    <%-- ҳ��ű� --%>
    <script src="/prpall/indiv/sh/UIMotorcadeDemandInput.js"></script>
	  <%-- ҳ����ʽ  --%>
	  <link rel="stylesheet" type="text/css" href="/prpall/css/Standard.css">
	  <%-- ��ʼ�� --%>
    <jsp:include page="/indiv/sh/UIMotorcadeDemandInputIni.jsp" />
  </head>
  <body class="interface" onload="loadForm();" leftmargin=0  background="/prpall/common/images/bgCommon.gif">
    <form name="fm" method="post" action="/prpall/indiv/sh/UIMotorcadeDemandInputSubmit.jsp">
      <input type="hidden" name="RiskCode" value="0501">
      <input type="hidden" name="UserCode" value="<%=strUserCode%>">
      <input type="hidden" name="ComCode" value="<%=strComCode%>">

      <table class="common">
        <tr>
          <td class=formtitle colspan=2>���Ӳ�ѯ����¼��</td>
        </tr>
        <tr>
          <td class=title style="width:20%">XX�ĳ�������</td>
          <td class=input style="width:80%"><input name="NumCar" class=common style="width:50px" maxlength=4
                            onkeypress="return pressInteger(event)"
                            onblur="checkSmallint(this,'0','6000')" value="1">
                           <input class="button" type="button"  name="buttonSubmit" alt="ȷ��"  value="ȷ��"  onclick="changeNumCar()" >
          </td>
        </tr>
        <tr>
          <td class=title>
            ������XXXXX<input type="checkbox" name="CheckBoxContract" onclick="Contract_Onclick()">
          </td>
          <td class=input>
            <span id="spanOldContractNo" style="display:none">
              ԭ���Ӻ�ͬ�ţ�<input name="OldContractNo" class=common maxlength=22>
              <input name="queryQuery" class="button" type="button"  alt="��ѯ"  value="��ѯ" onclick="renewalMotorcade()" >
            </span>
          </td>
        </tr>
      </table>

      <%-- ���Ͳ��������� --%>
      <table id="Motorcade_Data" style="display:none">
        <tbody>
          <tr>
            <td class=inputfree><input class="readonly" readonly name="SerialNo"
                             style="width:25px"><input type="hidden" name="OldPolicyNo"></td>
            <td class=inputfree><input class="common" type="text" name="LicenseNo" maxlength="12"
                             style="width:80px" description="���ƺ���"></td>
            <td class=inputfree><input class="common" type="text" name="EngineNo"
                             maxlength="30"  style="width:160px" description="��������"><br>
                             <select name="LicenseType">
                               <option value="">---��ѡ���������---</option>
                               <jsp:include page="/indiv/sh/UILicenseType.html" />
                             </select></td>
            <td class=inputfree><input class="common" type="text" name="FrameNo"
                             maxlength="30"  style="width:160px" description="���ܺ�"></td>
            <td class=inputfree><select name="UseNatureCode" style="width:80px">
                            </select><br><input type="text" name="CarKindCode"
                             maxlength="10" class="codecode"
                             description="��������" style="width:30px"
                             querytype="always" codetype="CarKind" coderelation="1" codelimit="must"
                             ondblclick="code_CodeSelect(this)"
                             onkeyup="code_CodeSelect(this)"
                             onblur="code_CodeChange(this)"
                             onkeypress="return uppercaseKey()"><input type="text" name="CarKindName" class="readonly" readonly style="width:50px">
            </td>
            <td class=inputfree><input class="common" type="text" name="SeatCount" style="width:45px"
                             description="�˶��ؿ�" onkeypress="return pressInteger(event)"
                             onblur="checkSmallint(this,'0','')">��<br>
                            <input class="common" type="text" name="TonCount" style="width:45px" description="������"
                             onkeypress="return pressDecimal(event)"
                             onblur="checkDecimal(this,8,2,'','')">��<br>
                            <input class="common" type="text" name="ExhaustScale" style="width:45px"
                             description="����/����" onkeypress="return pressDecimal(event)"
                             onblur="checkDecimal(this,8,2,'','')">KW/L
            </td>
            <td class=inputfree><input class="common" type="text" name="Amount"
                            style="width:60px" description="����������XXXXX�޶�"
                            onkeypress="return pressDecimal(event)"
                            onblur="checkDecimal(this,14,2,'','')"></td>
            <td class=inputfree><input class="common" name="StartDate"
                             style="width:75px" onkeypress="return pressFullDate(event)"
                             onblur="return checkStartDate(this);"
                             description="��XXXXX����"><br>��ʱ����<br><input class="common"
                             name="EndDate" style="width:75px"
                             onkeypress="return pressFullDate(event)" onblur="return checkEndDate(this);"
                             description="��XXXXX����"><br>24ʱֹ
                             <img src="/prpall/common/images/markMustInput.jpg"></td>
          </tr>
        </tbody>
      </table>
      <%-- ��ʾ���� --%>
      <span id="spanMotorcade">
      <table class="common" id="Motorcade">
        <thead>
          <tr class=mline>
            <td colspan=8 class="subformtitle" style="text-align:left">
             ���Ӹ�������Ϣ¼��<img src="/prpall/common/images/markMustInput.jpg">
			      </td>
          </tr>
          <tr>
            <td class="centertitle" style="width:5%">���</td>
            <td class="centertitle" style="width:10%">���ƺ���</td>
            <td class="centertitle" >��������</td>
            <td class="centertitle" >���ܺ�</td>
            <td class="centertitle" >����ʹ������<br>��������</td>
            <td class="centertitle" >�˶��ؿ�<br>������<br>����/����</td>
            <td class="centertitle" >�����޶�</td>
            <td class="centertitle" >XX����</td>
          </tr>
        </thead>
        <tfoot>
          <tr>
            <td colspan="8">
            <p align="right"><input name="button_Motorcade_Insert" class="button" type="button"  alt="����" value="����" onclick="insertSingleCar();" ></p>
            </td>
          </tr>
        </tfoot>
        <tbody>
        </tbody>
      </table>
      </span>

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
