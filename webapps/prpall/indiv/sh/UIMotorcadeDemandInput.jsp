<%--
****************************************************************************
* DESC       ：车队查询请求录入
* Author     : X
* CREATEDATE ：2003-03-04
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
--%>

<%-- 错误处理 --%>
<%@page errorPage="/UIErrorPage"%>

<%@page import="com.sp.utility.error.*"%>

<%
  
  String strUserCode = (String)session.getValue("UserCode");
  String strComCode = (String)session.getValue("ComCode");
  if( strUserCode == null || strUserCode.equals("") ||
      strComCode == null  || strComCode.equals("") )
  {
    throw new UserException(-98,-1003,"UIMotorcadeDemandInput.jsp","session超时,请重新登录!");
  }
%>

<html>
  <head>
    <title>车队查询请求录入</title>
    <%-- 公用函数 --%>
	  <script src="/prpall/common/pub/UICommon.js"></script>
    <%-- 代码选择函数 --%>
    <script src="/prpall/common/pub/UICodeSelect.js"></script>
    <%-- 多行操作函数 --%>
    <script src="/prpall/common/pub/UIMulLine.js"></script>
    <%-- 页面脚本 --%>
    <script src="/prpall/indiv/sh/UIMotorcadeDemandInput.js"></script>
	  <%-- 页面样式  --%>
	  <link rel="stylesheet" type="text/css" href="/prpall/css/Standard.css">
	  <%-- 初始化 --%>
    <jsp:include page="/indiv/sh/UIMotorcadeDemandInputIni.jsp" />
  </head>
  <body class="interface" onload="loadForm();" leftmargin=0  background="/prpall/common/images/bgCommon.gif">
    <form name="fm" method="post" action="/prpall/indiv/sh/UIMotorcadeDemandInputSubmit.jsp">
      <input type="hidden" name="RiskCode" value="0501">
      <input type="hidden" name="UserCode" value="<%=strUserCode%>">
      <input type="hidden" name="ComCode" value="<%=strComCode%>">

      <table class="common">
        <tr>
          <td class=formtitle colspan=2>车队查询请求录入</td>
        </tr>
        <tr>
          <td class=title style="width:20%">XX的车辆数：</td>
          <td class=input style="width:80%"><input name="NumCar" class=common style="width:50px" maxlength=4
                            onkeypress="return pressInteger(event)"
                            onblur="checkSmallint(this,'0','6000')" value="1">
                           <input class="button" type="button"  name="buttonSubmit" alt="确定"  value="确定"  onclick="changeNumCar()" >
          </td>
        </tr>
        <tr>
          <td class=title>
            车队续XXXXX<input type="checkbox" name="CheckBoxContract" onclick="Contract_Onclick()">
          </td>
          <td class=input>
            <span id="spanOldContractNo" style="display:none">
              原车队合同号：<input name="OldContractNo" class=common maxlength=22>
              <input name="queryQuery" class="button" type="button"  alt="查询"  value="查询" onclick="renewalMotorcade()" >
            </span>
          </td>
        </tr>
      </table>

      <%-- 类型参数隐藏域 --%>
      <table id="Motorcade_Data" style="display:none">
        <tbody>
          <tr>
            <td class=inputfree><input class="readonly" readonly name="SerialNo"
                             style="width:25px"><input type="hidden" name="OldPolicyNo"></td>
            <td class=inputfree><input class="common" type="text" name="LicenseNo" maxlength="12"
                             style="width:80px" description="号牌号码"></td>
            <td class=inputfree><input class="common" type="text" name="EngineNo"
                             maxlength="30"  style="width:160px" description="发动机号"><br>
                             <select name="LicenseType">
                               <option value="">---请选择号牌种类---</option>
                               <jsp:include page="/indiv/sh/UILicenseType.html" />
                             </select></td>
            <td class=inputfree><input class="common" type="text" name="FrameNo"
                             maxlength="30"  style="width:160px" description="车架号"></td>
            <td class=inputfree><select name="UseNatureCode" style="width:80px">
                            </select><br><input type="text" name="CarKindCode"
                             maxlength="10" class="codecode"
                             description="车辆种类" style="width:30px"
                             querytype="always" codetype="CarKind" coderelation="1" codelimit="must"
                             ondblclick="code_CodeSelect(this)"
                             onkeyup="code_CodeSelect(this)"
                             onblur="code_CodeChange(this)"
                             onkeypress="return uppercaseKey()"><input type="text" name="CarKindName" class="readonly" readonly style="width:50px">
            </td>
            <td class=inputfree><input class="common" type="text" name="SeatCount" style="width:45px"
                             description="核定载客" onkeypress="return pressInteger(event)"
                             onblur="checkSmallint(this,'0','')">人<br>
                            <input class="common" type="text" name="TonCount" style="width:45px" description="总质量"
                             onkeypress="return pressDecimal(event)"
                             onblur="checkDecimal(this,8,2,'','')">吨<br>
                            <input class="common" type="text" name="ExhaustScale" style="width:45px"
                             description="排量/功率" onkeypress="return pressDecimal(event)"
                             onblur="checkDecimal(this,8,2,'','')">KW/L
            </td>
            <td class=inputfree><input class="common" type="text" name="Amount"
                            style="width:60px" description="第三者责任XXXXX限额"
                            onkeypress="return pressDecimal(event)"
                            onblur="checkDecimal(this,14,2,'','')"></td>
            <td class=inputfree><input class="common" name="StartDate"
                             style="width:75px" onkeypress="return pressFullDate(event)"
                             onblur="return checkStartDate(this);"
                             description="起XXXXX日期"><br>零时起至<br><input class="common"
                             name="EndDate" style="width:75px"
                             onkeypress="return pressFullDate(event)" onblur="return checkEndDate(this);"
                             description="终XXXXX日期"><br>24时止
                             <img src="/prpall/common/images/markMustInput.jpg"></td>
          </tr>
        </tbody>
      </table>
      <%-- 显示内容 --%>
      <span id="spanMotorcade">
      <table class="common" id="Motorcade">
        <thead>
          <tr class=mline>
            <td colspan=8 class="subformtitle" style="text-align:left">
             车队各单车信息录入<img src="/prpall/common/images/markMustInput.jpg">
			      </td>
          </tr>
          <tr>
            <td class="centertitle" style="width:5%">序号</td>
            <td class="centertitle" style="width:10%">号牌号码</td>
            <td class="centertitle" >发动机号</td>
            <td class="centertitle" >车架号</td>
            <td class="centertitle" >车辆使用性质<br>车辆种类</td>
            <td class="centertitle" >核定载客<br>总质量<br>排量/功率</td>
            <td class="centertitle" >三者限额</td>
            <td class="centertitle" >XX期限</td>
          </tr>
        </thead>
        <tfoot>
          <tr>
            <td colspan="8">
            <p align="right"><input name="button_Motorcade_Insert" class="button" type="button"  alt="新增" value="新增" onclick="insertSingleCar();" ></p>
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
