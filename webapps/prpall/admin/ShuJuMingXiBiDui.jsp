<%--
****************************************************************************
* DESC       ：数据比对明细对比结果
* Author     : chends
* CREATEDATE ：2007-04-25
* MODIFYLIST ：
*
****************************************************************************
--%>
<%@page contentType="text/html;charset=GBK"%>

<%@ page import="java.util.*"%>
<%@ page import="java.lang.*"%>
<%@ page import="com.sp.utility.*"%>
<%@ page import="com.sp.utility.log.*"%>
<%@ page import="com.sp.utility.error.*"%>
<%@ page import="com.sp.prpall.ui.UICentralControl"%>
<%@ page import="com.sp.prpall.ui.*"%>
<%@ page import="com.sp.indiv.ci.interf.*"%>
<%@page import="com.sp.utility.*"%>
<%@page import="com.sp.utility.log.*"%>
<%@page import="com.sp.utility.error.*"%>
<%@page import="com.sp.utiall.dbsvr.*"%>
<%@page import="com.sp.utiall.dbsvr.*"%>
<%@page import="com.sp.prpall.blsvr.tb.*"%>
<%@page import="com.sp.prpall.blsvr.cb.*"%>
<%@page import="com.sp.prpall.blsvr.pg.*"%>
<%@page import="com.sp.prpall.blsvr.misc.*"%>
<%@page import="com.sp.prpall.dbsvr.misc.*"%>
<%@page import="com.sp.prpall.pubfun.*"%>
<%@page import="com.sp.prpall.schema.*"%>
<%@page import="com.sp.utility.string.*"%>
<%@page import="com.sp.indiv.sh.util.*"%>
<%@page import="com.sp.indiv.sh.blsvr.*"%>
<%@page import="com.sp.indiv.sh.schema.*"%>
<%@page import="com.sp.indiv.ci.blsvr.*"%>
<%@page import="com.sp.indiv.ci.dbsvr.*"%>
<%@page import="com.sp.indiv.ci.interf.*"%>
<%@page import="com.sp.indiv.ci.schema.*"%>
<%@page import="java.sql.SQLException"%>
<%@page import="com.sp.utility.database.DbPool"%>
<%@page import="com.sp.utility.SysConst"%>
<%@page import="com.sp.sysframework.common.util.StringUtils"%>
<%@page import="com.sp.sysframework.common.datatype.DateTime"%>

<%
		

		String strCompareDate = request.getParameter("strCompareDate");
		String strOperateDate = request.getParameter("strOperateDate");
		String strRequestType = request.getParameter("strRequestType");
		String strSerialNo 		= request.getParameter("strSerialNo");
		String strSQLWhere1 = "";
		String strSQLWhere2 = "";
		if(strCompareDate == null)
		{
			strCompareDate = "";
		}
		if(strOperateDate == null)
		{
			strOperateDate = "";
		}
		if(strRequestType == null)
		{
			strRequestType = "";
		}
		if(strSerialNo == null)
		{
			strSerialNo = "";
		}
		BLPolicy blPolicy = new BLPolicy();
    BLCIInsureCompanyCompare blCIInsureCompanyCompare 	= new BLCIInsureCompanyCompare();
    BLCIInsureCompare blCIInsureCompare 								= new BLCIInsureCompare();
    BLCIInsureCompareDetail blCIInsureCompareDetail     = new BLCIInsureCompareDetail();
    CIInsureCompanyCompareSchema cIInsureCompanyCompareSchema 	= new CIInsureCompanyCompareSchema();
    CIInsureCompareDetailSchema cIInsureCompareDetailSchema 	  = new CIInsureCompareDetailSchema();
    CIInsureCompareDetailEncoder cIInsureCompareDetailEncoder 	= new CIInsureCompareDetailEncoder();
		CIInsureCompareDetailDecoder cIInsureCompareDetailDecoder 	= new CIInsureCompareDetailDecoder();
		
		try
		{
		   if(!strCompareDate.trim().equals("") && strCompareDate != null)
		   {
		      strSQLWhere1 = " SELECT * FROM CIINSURECOMPAREDETAIL WHERE";
		      if(!strCompareDate.trim().equals("") && strCompareDate != null)
		      {
		      	strSQLWhere1 += " TO_DATE(CompareDate, 'YYYY-MM-DD') = " + "TO_DATE('" + strCompareDate.trim() + "', 'YYYY-MM-DD') AND ";
		      }
		      
		      if(!strOperateDate.trim().equals("") && strOperateDate != null)
		      {
		      	strSQLWhere1 += " TO_DATE(OperateDate, 'YYYY-MM-DD') = " + "TO_DATE('" + strOperateDate.trim() + "', 'YYYY-MM-DD') AND ";
		      }
		      
		      if(!strRequestType.trim().equals("") && strRequestType != null)
		      {
		      	if(strRequestType.trim().equals("4"))
		      	{
		      		strSQLWhere1 += " RequestType IN ('1', '2', '3') AND ";
		      	}
		        else
		      	{
		      		strSQLWhere1 += " RequestType = '" + strRequestType.trim() + "' AND ";
		      	}
		      }
		      
		      if(!strSerialNo.trim().equals("") && strSerialNo != null)
		      {
		      	strSQLWhere1 += " SerialNoCom = '" + strSerialNo.trim() + "'";
		      }
		      strSQLWhere1 += " ORDER BY POLICYNO, SERIALNOCOM, SERIALNO, REQUESTTYPE ";
		      
		      strSQLWhere2 = " SELECT * FROM CIINSURECOMPANYCOMPARE WHERE";
		      if(!strCompareDate.trim().equals("") && strCompareDate != null)
		      {
		      	strSQLWhere2 += " TO_DATE(CompareDate, 'YYYY-MM-DD') = " + "TO_DATE('" + strCompareDate.trim() + "', 'YYYY-MM-DD') AND ";
		      }
		      
		      if(!strOperateDate.trim().equals("") && strOperateDate != null)
		      {
		      	strSQLWhere2 += " TO_DATE(OperateDate, 'YYYY-MM-DD') = " + "TO_DATE('" + strOperateDate.trim() + "', 'YYYY-MM-DD') AND ";
		      }
		      
		      if(!strRequestType.trim().equals("") && strRequestType != null)
		      {
		      	if(strRequestType.trim().equals("1"))
		      	{
		      		strSQLWhere2 += " UnderWriteFlag IN ('1', '3') AND ";
		      	}
		        else if(strRequestType.trim().equals("2"))
		      	{
		      		strSQLWhere2 += " UnderWriteFlag IN ('1', '3') AND " + " SUBSTR(OTHFLAG, 4, 1) != '0' AND ";
		      	}
		      	else if(strRequestType.trim().equals("3"))
		      	{
		      		strSQLWhere2 += " UnderWriteFlag IN ('1', '3') AND " + " SUBSTR(OTHFLAG, 3, 1) != '0' AND ";
		      	}
		      }
		      
		      if(!strSerialNo.trim().equals("") && strSerialNo != null)
		      {
		      	strSQLWhere2 += " SerialNo = '" + strSerialNo.trim() + "'";
		      }
		      strSQLWhere2 += "  ORDER BY POLICYNO, SERIALNO ";
		   }
		}
		catch(Exception ex)
		{
       ex.printStackTrace();
    }
%>

<html>
  <head>
    <title>数据比对明细比对查询页面</title>
    <%-- 公用函数 --%>
    <script src="/prpall/common/pub/UICommon.js"></script>
    <%-- 多行输入函数 --%>
    <script src="/prpall/common/pub/UIMulLine.js"></script>
    <%-- 代码输入函数 --%>
    <script src="/prpall/common/pub/UICodeSelect.js"></script>
    <%-- 页面样式 --%>
    <link rel="stylesheet" type="text/css" href="/prpall/css/Standard.css">
    <script language=Javascript>
    function submitForm()
    {
      
      if(trim(fm.strCompareDate.value) == "" || 
         trim(fm.strOperateDate.value) == "" ||
         trim(fm.strSerialNo.value) == "")
      {
      	alert("比对日期和操作日期和最大序列号都不能为空");
      	return false;
      }
      fm.submit();
      return true;
    }
  </script>
  <style type="text/css">
<!--
.style1 {color: #FF0000}
-->
    </style>
  </head>
<body class="interface" background="/prpall/common/images/bgCommon.gif">

<form name="fm" method="post" action="/prpall/admin/ShuJuMingXiBiDui.jsp" >
<table class=common>
    <tr class=mline>
      <td class=common style="text-align:left">
        <table class=common cellpadding="5" cellspacing="1">
            <tr>
              <td class="title">比对日期<br>(2007-04-19)：</td>
              <td class="input" colspan="3">
                <input type="text" name="strCompareDate" maxlength="100"  class="common" style="width:120px" value="<%=strCompareDate%>">
              </td>
              <td class="title">操作日期<br>(2007-04-20)：</td>
              <td class="input">
                <input type="text" name="strOperateDate" maxlength="100" class="common" style="width:120px" value="<%=strOperateDate%>">
              </td>
            </tr>

            <tr>
              <td class="title">查询类型：</td>
              <td class="input" colspan="3">
              	<select name=strRequestType>
              		<option selected></option>
                  <option value=1>XX确认件数</option>
                  <option value=2>注销XX件数</option>
                  <option value=3>退XXXXX件数</option>
                  <option value=4>所有</option>
                </select>
              </td>
              <td class="title">最大序列号：</td>
              <td class="input">
                <input type="text" name="strSerialNo" maxlength="17" class="common" style="width:120px" value="<%=strSerialNo%>">
              </td>
            </tr>
        </table>
      </td>
    </tr>
  </table>

  <table class=common cellpadding="5" cellspacing="1">
        <tr>
          <td class="button" >
            <input class="button" type="button" name="find"  value="查询"  onclick="submitForm()">
          </td>
        </tr>
        <tr>
          <td align="left" valign="top">
            <span class="style1">
            CIInsureCompareDetail信息说明：<br>
            	1、类型：1：XX确认件数明细; 2：注销XX件数明细; 3：退XXXXX件数明细; 4：报案数明细; 5：立案数明细; 6：结案数明细; 7：注销案件数明细<br>
            	2、序号：交强XXXXX平台返回查询某一类型下的依次序号<br>
            	3、返回总数：交强XXXXX平台返回查询某一类型下的总数<br><br>
            CIInsureCompanyCompare信息说明：<br>
            	1、核XXXXX标志：1：通过; 3：无需核XXXXX;<br>
            	2、其它标志：第三位为1：退XXXXX; 第四位为1：注销<br>
            </span>
          </td>
        </tr>
  </table>
</form>

<form name="fm2" method="post" action="" >
<span id="dsheet01" style="">
<table class=common>
   <tr>
   		 <td class="title" colspan="8" style="text-align:center"> 
   		 		CIInsureCompareDetail信息
   		 </td>
   		 <td class="title" colspan="5" style="text-align:center"> 
   		 		CIInsureCompanyCompare信息
   		 </td>
   </tr>
   <tr>
       <td class=title style="width:150px" style="text-align:center">XX号</td>
       <td class=title style="width:250px" style="text-align:center">业务代码</td>
       <td class=title style="width:50px" style="text-align:center">类型</td>
       <td class=title style="width:50px" style="text-align:center">序列号</td> 
       <td class=title style="width:80px" style="text-align:center">返回总数</td>
       <td class=title style="width:200px" style="text-align:center">返回信息</td>
       <td class=title style="width:150px" style="text-align:center">比对日期</td>
       <td class=title style="width:150px" style="text-align:center">操作日期</td>
       <td class=title style="width:150px" style="text-align:center">XX号</td>
       <td class=title style="width:50px" style="text-align:center">核XXXXX标志</td>
       <td class=title style="width:150px" style="text-align:center">其它标志</td>
       <td class=title style="width:150px" style="text-align:center">比对日期</td>
       <td class=title style="width:150px" style="text-align:center">操作日期</td>
   </tr>
   <tr>
   	<td class="title" colspan="8" align="left" valign="top"> 
   		 		<table class=common>
<%
		try
		{
			if(!strCompareDate.trim().equals("") && strCompareDate != null)
			{
					Vector vCIInsureCompanyDetail = new Vector();
					Vector vCIInsureCompanyCompare = new Vector();
					DbPool dbPool = new DbPool();
          dbPool.open(SysConst.getProperty("DDCCDATASOURCE"));
          vCIInsureCompanyDetail	= blCIInsureCompareDetail.query(dbPool, strSQLWhere1);
          vCIInsureCompanyCompare = blCIInsureCompanyCompare.query(dbPool, strSQLWhere2);
          if(vCIInsureCompanyDetail.size() > 0)
		      {
		      	  Iterator itCIInsureCompanyDetail = vCIInsureCompanyDetail.iterator();
		      	  while(itCIInsureCompanyDetail.hasNext())
		      	  {
		      	  	cIInsureCompareDetailSchema  = (CIInsureCompareDetailSchema)itCIInsureCompanyDetail.next();
%>
   		 			<tr>
             <td class=title style="width:150px" style="text-align:center"><%=cIInsureCompareDetailSchema.getPolicyNo() == null ?"":cIInsureCompareDetailSchema.getPolicyNo()%></td>
             <td class=title style="width:250px" style="text-align:center"><%=cIInsureCompareDetailSchema.getBusinessNo() == null ?"":cIInsureCompareDetailSchema.getBusinessNo()%></td>
             <td class=title style="width:50px" style="text-align:center"><%=cIInsureCompareDetailSchema.getRequestType() == null ?"":cIInsureCompareDetailSchema.getRequestType()%></td>
             <td class=title style="width:50px" style="text-align:center"><%=cIInsureCompareDetailSchema.getSerialNo() == null ?"":cIInsureCompareDetailSchema.getSerialNo()%></td>
             <td class=title style="width:80px" style="text-align:center"><%=cIInsureCompareDetailSchema.getReturnTotalNum() == null ?"":cIInsureCompareDetailSchema.getReturnTotalNum()%></td>
             <td class=title style="width:200px" style="text-align:center"><%=cIInsureCompareDetailSchema.getResponseMessage() == null ?"":cIInsureCompareDetailSchema.getResponseMessage()%></td>
             <td class=title style="width:150px" style="text-align:center"><%=cIInsureCompareDetailSchema.getCompareDate() == null ?"":cIInsureCompareDetailSchema.getCompareDate()%></td>
             <td class=title style="width:150px" style="text-align:center"><%=cIInsureCompareDetailSchema.getOperateDate() == null ?"":cIInsureCompareDetailSchema.getOperateDate()%></td>
            </tr>
<%
							}
					}
%>
					</table>
   		 </td>
   		 <td class="title" colspan="5" align="left" valign="top"> 
   		 		<table class=common>
<%				
					if(vCIInsureCompanyCompare.size() > 0)
		      {
		      		Iterator itCIInsureCompanyCompare = vCIInsureCompanyCompare.iterator();
		      	  while(itCIInsureCompanyCompare.hasNext())
		      	  {
		      	  	cIInsureCompanyCompareSchema = (CIInsureCompanyCompareSchema)itCIInsureCompanyCompare.next();
		      	  	if(!cIInsureCompanyCompareSchema.getPolicyNo().trim().equals(""))
		      	  	{
%>
   		 			<tr>
   		 				 <td class=title style="width:150px" style="text-align:center"><%=cIInsureCompanyCompareSchema.getPolicyNo() == null ?"":cIInsureCompanyCompareSchema.getPolicyNo()%></td>
               <td class=title style="width:50px" style="text-align:center"><%=cIInsureCompanyCompareSchema.getUnderWriteFlag() == null ?"":cIInsureCompanyCompareSchema.getUnderWriteFlag()%></td>
               <td class=title style="width:150px" style="text-align:center"><%=cIInsureCompanyCompareSchema.getOthFlag() == null ?"":cIInsureCompanyCompareSchema.getOthFlag()%></td>
               <td class=title style="width:150px" style="text-align:center"><%=cIInsureCompanyCompareSchema.getCompareDate() == null ?"":cIInsureCompanyCompareSchema.getCompareDate()%></td>
               <td class=title style="width:150px" style="text-align:center"><%=cIInsureCompanyCompareSchema.getOperateDate() == null ?"":cIInsureCompanyCompareSchema.getOperateDate()%></td>
           </tr>
<%
								}
						}
				}
%>
   			</table>
   		 </td>
   </tr>
<%
			}
		}
		catch(Exception ex)
		{
       ex.printStackTrace();
    }
%>
</table>	
</span>
</form>      
</body>
</html>	
