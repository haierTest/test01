<%--
****************************************************************************
* DESC       ：法定三者查询请求录入页面
* Author     : X
* CREATEDATE ： 2003-02-09
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
--%>

<%-- 错误处理 --%>
<%@page errorPage="/UIErrorPage"%>

<html>
  <head>
    <title>法定三者查询请求录入页面</title>
    <%-- 公用函数 --%>
	  <script src="/prpall/common/pub/UICommon.js"></script>
    <%-- 代码选择函数 --%>
    <script src="/prpall/common/pub/UICodeSelect.js"></script>
    <%-- 本页js函数 --%>
    <script src="/prpall/indiv/sh/UIDemandInput.js"></script>
	  <%-- 页面样式  --%>
	  <link rel="stylesheet" type="text/css" href="/prpall/css/Standard.css">
	  <%-- 初始化 --%>
    <jsp:include page="/indiv/sh/UIDemandInputIni.jsp" />
  </head>
  <body class="interface" onload="loadForm();"  background="/prpall/common/images/bgCommon.gif">
    
    <form name="fm" method="post" action="/prpall/indiv/sh/UIDemandInputSubmit.jsp">
    <input type="hidden" name="RiskCode" value="0501">
    <table class=common align=center>
      <tr>
        <td class=formtitle colspan="4">查询请求录入</td>
      </tr>
      <tr>
        <td class=title>号牌号码：</td>
        <td class=input>
          <input type="text" name="LicenseNo" maxlength="12" class="common" description="号牌号码"
            onblur="checkLength(this)">
          <%--  <img src="/prpall/common/images/markMustInput.jpg">  --%>
        </td>
        <td class=title>发动机号：</td>
        <td class=input>
          <input type="text" name="EngineNo" maxlength="30" class="common" description="发动机号"
            onblur="checkLength(this)">
           <img src="/prpall/common/images/markMustInput.jpg">
        </td>
      </tr>
      <tr>
        <td class=title>车架号：</td>
        <td class=input>
          <input type="text" name="FrameNo" maxlength="30" class="common" description="车架号"
            onblur="checkLength(this);">
        </td>
        <td class=title>车辆使用性质：</td>
        <td class=input>
          <select name="UseNatureCode">
          </select>
        </td>
      </tr>
      <tr>
        <td class=title>车辆种类：</td>
        <td class=input>
          <input type="text" name="CarKindCode"
            maxlength="10" class="codecode"
            description="车辆种类" style="width:50px"
            querytype="always" codetype="CarKind" coderelation="1" codelimit="must"
            ondblclick="code_CodeSelect(this)"
            onkeyup="code_CodeSelect(this)"
            onblur="code_CodeChange(this)"
            onkeypress="return uppercaseKey()">
          <input type="text" name="CarKindName" class="readonly" readonly style="width:105px">
          <img src="/prpall/common/images/markMustInput.jpg">
        </td>
        <td class=title>核定载客：</td>
        <td class=input>
          <input type="text" name="SeatCount" maxlength="5" class="common"
            description="核定载客"
            onkeypress="return pressInteger(event)"
            onblur="checkSmallint(this,'0','')">人
          <img src="/prpall/common/images/markMustInput.jpg">
        </td>
      </tr>
      <tr>
        <td class=title>总质量：</td>
        <td class=input>
          <input type="text" name="TonCount" maxlength="9" class="common"
            description="总质量"
            onkeypress="return pressDecimal(event)"
            onblur="checkDecimal(this,8,2,'','')">吨
        </td>
        <td class=title>排量/功率：</td>
        <td class=input>
          <input type="text" name="ExhaustScale" maxlength="7" class="common"
            description="排量/功率"
            onkeypress="return pressDecimal(event)"
            onblur="checkDecimal(this,6,2,'','')"> L/KW
        </td>
      </tr>
      <tr>
        <td class=title>第三者责任XXXXX限额：</td>
        <td class=input>
          <input class="common" name="Amount"
            maxlength="15" description="第三者责任XXXXX限额"
            onkeypress="return pressDecimal(event)"
            onblur="return checkDecimal(this,14,2,'','')">
          <img src="/prpall/common/images/markMustInput.jpg">
        </td>
        <td class=title>号牌种类：</td>
        <td class=input>
          <select name="LicenseType">
            <option value="">---请选择号牌种类---</option>
            <jsp:include page="/indiv/sh/UILicenseType.html" />
          </select>
        </td>
      </tr>
      <tr>
        <td class=title colspan=4>
          XX期限从
          <input name="StartDate" maxlength=10 class="common" description="起XXXXX日期" onkeypress="return pressFullDate(event)" onblur="return checkStartDate(this);">
          零时起至
          <input name="EndDate" maxlength=10 class="common" description="终XXXXX日期" onkeypress="return pressFullDate(event)" onblur="return checkEndDate(this);">
          二十四时止
          <img src="/prpall/common/images/markMustInput.jpg">
        </td>
      </tr>
      <tr>
        <td class=input colspan=4>
          <font color=red>注意：新车不要填写“号牌号码”。</font>
        </td>
      </tr>
    </table>
    <table width="85%">
     <tr>
      <td colspan=1 class=button>
        <input name="buttonSubmit" class="button" type="button"  alt="查询请求" value="查询请求" onclick="submitForm()">
      </td>
      <td colspan=1 class=button>
        <input name="buttonCancel" class="button" type="button"  alt="取消" value="取消" onclick="cancelForm()">
      </td>
    </tr>
    </table>
    </form>
  </body>
</html>
