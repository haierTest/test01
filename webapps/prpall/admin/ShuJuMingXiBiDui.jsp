<%--
****************************************************************************
* DESC       �����ݱȶ���ϸ�ԱȽ��
* Author     : chends
* CREATEDATE ��2007-04-25
* MODIFYLIST ��
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
    <title>���ݱȶ���ϸ�ȶԲ�ѯҳ��</title>
    <%-- ���ú��� --%>
    <script src="/prpall/common/pub/UICommon.js"></script>
    <%-- �������뺯�� --%>
    <script src="/prpall/common/pub/UIMulLine.js"></script>
    <%-- �������뺯�� --%>
    <script src="/prpall/common/pub/UICodeSelect.js"></script>
    <%-- ҳ����ʽ --%>
    <link rel="stylesheet" type="text/css" href="/prpall/css/Standard.css">
    <script language=Javascript>
    function submitForm()
    {
      
      if(trim(fm.strCompareDate.value) == "" || 
         trim(fm.strOperateDate.value) == "" ||
         trim(fm.strSerialNo.value) == "")
      {
      	alert("�ȶ����ںͲ������ں�������кŶ�����Ϊ��");
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
              <td class="title">�ȶ�����<br>(2007-04-19)��</td>
              <td class="input" colspan="3">
                <input type="text" name="strCompareDate" maxlength="100"  class="common" style="width:120px" value="<%=strCompareDate%>">
              </td>
              <td class="title">��������<br>(2007-04-20)��</td>
              <td class="input">
                <input type="text" name="strOperateDate" maxlength="100" class="common" style="width:120px" value="<%=strOperateDate%>">
              </td>
            </tr>

            <tr>
              <td class="title">��ѯ���ͣ�</td>
              <td class="input" colspan="3">
              	<select name=strRequestType>
              		<option selected></option>
                  <option value=1>XXȷ�ϼ���</option>
                  <option value=2>ע��XX����</option>
                  <option value=3>��XXXXX����</option>
                  <option value=4>����</option>
                </select>
              </td>
              <td class="title">������кţ�</td>
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
            <input class="button" type="button" name="find"  value="��ѯ"  onclick="submitForm()">
          </td>
        </tr>
        <tr>
          <td align="left" valign="top">
            <span class="style1">
            CIInsureCompareDetail��Ϣ˵����<br>
            	1�����ͣ�1��XXȷ�ϼ�����ϸ; 2��ע��XX������ϸ; 3����XXXXX������ϸ; 4����������ϸ; 5����������ϸ; 6���᰸����ϸ; 7��ע����������ϸ<br>
            	2����ţ���ǿXXXXXƽ̨���ز�ѯĳһ�����µ��������<br>
            	3��������������ǿXXXXXƽ̨���ز�ѯĳһ�����µ�����<br><br>
            CIInsureCompanyCompare��Ϣ˵����<br>
            	1����XXXXX��־��1��ͨ��; 3�������XXXXX;<br>
            	2��������־������λΪ1����XXXXX; ����λΪ1��ע��<br>
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
   		 		CIInsureCompareDetail��Ϣ
   		 </td>
   		 <td class="title" colspan="5" style="text-align:center"> 
   		 		CIInsureCompanyCompare��Ϣ
   		 </td>
   </tr>
   <tr>
       <td class=title style="width:150px" style="text-align:center">XX��</td>
       <td class=title style="width:250px" style="text-align:center">ҵ�����</td>
       <td class=title style="width:50px" style="text-align:center">����</td>
       <td class=title style="width:50px" style="text-align:center">���к�</td> 
       <td class=title style="width:80px" style="text-align:center">��������</td>
       <td class=title style="width:200px" style="text-align:center">������Ϣ</td>
       <td class=title style="width:150px" style="text-align:center">�ȶ�����</td>
       <td class=title style="width:150px" style="text-align:center">��������</td>
       <td class=title style="width:150px" style="text-align:center">XX��</td>
       <td class=title style="width:50px" style="text-align:center">��XXXXX��־</td>
       <td class=title style="width:150px" style="text-align:center">������־</td>
       <td class=title style="width:150px" style="text-align:center">�ȶ�����</td>
       <td class=title style="width:150px" style="text-align:center">��������</td>
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
