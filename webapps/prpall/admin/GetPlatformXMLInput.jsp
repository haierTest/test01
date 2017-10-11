
<%@page contentType="text/html;charset=GBK"%>
<%-- 引入bean类部分 --%>
<%@page import="com.sp.prpall.ui.UIHTMLGenerator"%>
<%@page import="com.sp.utility.SysConfig"%>
<%@page import="com.sp.utility.StringConvertor"%>
<%@page import="com.sp.prpall.schema.RenewalCheckSchema"%>
<%@page import="com.sp.prpall.ui.UIRefUtil"%>
<%@page import="com.sp.utility.UtiPower;"%>
<html>
<head>
    <title>外部系统报文录入页面</title>
    <link rel="stylesheet" type="text/css" href="/prpall/css/Standard.css">
    <link rel="stylesheet" type="text/css" href="/prpall/css/prpcar.css">
    <script src="/prpall/common/pub/UICommon.js"></script>
    <script src="/prpall/commoncar/tb/UIProposalQueryInputNew.js"></script>
    <script src="/prpall/commonship/pub/UIQueryScopeInput.js"></script> 
    <script language="javascript" src="/prpall/common/js/prototype.js"></script>
	<script language="javascript" src="/prpall/common/js/sunsug.js"></script>
	<script language="javascript">
function submitForm(){
	fm.action="/prpall/admin/GetPlatformXMLShow.jsp";
    fm.submit(); 
} 
     </script>
</head>
<body class="logon">
  <form name=fm method=post action="" >
 <table cellpadding="5" cellspacing="1" width="1000"  align="center" border="3" bgcolor= "#F7F7F7">
          <thead>
            <tr class="listtitle">
              <td colspan="2">
               	获取与平台交互报文
              </td>
            </tr>
          </thead>
            <tr>
              <td width="25%" align="center"><font size="3"><b>发送中间业务平台报文：</b></font></td>
              <td width="75%">
                <textarea name = "ToCCSIResquestXML" rows="43" cols="120" >
                </textarea>
             
              </td>
            </tr>
			<tr>
			<tr>
				<td></td>
            	<td class="input" align="center">
            		<input name="btnInput" class="button" type="button" value="获取报文" alt="获取报文"  onclick="submitForm()">
            	</td>
            </tr>
            </table>
</form>
</body>
</html>