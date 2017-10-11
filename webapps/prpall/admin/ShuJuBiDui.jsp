<%--
****************************************************************************
* DESC       ：数据比对
* Author     : chends
* CREATEDATE ：2007-04-25
* MODIFYLIST ：
*
****************************************************************************
--%>

<%@page contentType="text/html;charset=GBK"%>
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
<%@page import="org.apache.commons.logging.Log"%>
<%@page import="org.apache.commons.logging.LogFactory"%>

<%@ page errorPage="/UIErrorPage"%>
<%
	Log logger = LogFactory.getLog("RunTime");
    
    
    
    String strCompareDate = (String)request.getParameter("strCompareDate"); 
    String strComCode     = (String)request.getParameter("strComCode");
    
    
    BLPolicy blPolicy = new BLPolicy();
    BLCIInsureCompanyCompare blCIInsureCompanyCompare 	= new BLCIInsureCompanyCompare();
    BLCIInsureCompare blCIInsureCompare 								= new BLCIInsureCompare();
    CIInsureCompareSchema cIInsureCompareSchema 				= new CIInsureCompareSchema();
    CIInsureCompareEncoder cIInsureCompareEncoder 			= new CIInsureCompareEncoder();
		CIInsureCompareDecoder cIInsureCompareDncoder 			= new CIInsureCompareDecoder();
    
    
    String strRequest 				= ""; 				
		String strContext 				= ""; 				
		
		DateTime dateTime 		= new DateTime();
		String strCurrentDate = dateTime.current().addDay(-1).addDay(1).toString(); 

		
		int iMaxSerialNo 				= 0;	
		int iMaxSerialNoClaim 	= 0;	
		int isSuccessFlag				= 2;	
    
    DbPool dbPool = new DbPool();
    dbPool.open(SysConst.getProperty("DDCCDATASOURCE"));
    
    
    
    try
      {
        dbPool.beginTransaction();
        
        
			  iMaxSerialNo = blCIInsureCompanyCompare.getMaxSerialNo(dbPool, strCurrentDate);
			  iMaxSerialNoClaim = blCIInsureCompanyCompare.getMaxSerialNoClaim(dbPool, strCurrentDate);
			  
			  cIInsureCompareSchema.setSerialNo(String.valueOf(iMaxSerialNo + 1));
				strCurrentDate = dateTime.current().getYear() 
			   								+ ":" + dateTime.current().getMonth() 
			   								+ ":" + dateTime.current().getDate() 
			   								+ ":" + dateTime.current().getHour() 
			   								+ ":" + dateTime.current().getMinute()
			   								+ ":" + dateTime.current().getSecond();
				
			  
			  cIInsureCompareSchema.setOperateDate(strCurrentDate);
			  blCIInsureCompare.setArr(cIInsureCompareSchema);
			  blPolicy.setBLCIInsureCompare(blCIInsureCompare);
			  
			  
			  blCIInsureCompanyCompare.setCompanyComparePolicy(dbPool, strCompareDate, strCurrentDate, iMaxSerialNo + 1, strComCode);
			  
			  blCIInsureCompanyCompare.setCompanyCompareEndorse(dbPool, strCompareDate, strCurrentDate, iMaxSerialNo + 1, strComCode);
			  
			  
			  strRequest = cIInsureCompareEncoder.encode(dbPool, blPolicy, strCompareDate, iMaxSerialNo + 1, iMaxSerialNoClaim, strComCode);
			  
			  logger.info("============XXXXX交强XXXXX平台数据比对strRequest: " + strRequest);
			  

			  strContext 	= EbaoProxy.getInstance().request(strRequest, strComCode);
			  
			  logger.info("============XXXXX交强XXXXX平台数据比对strContext: " + strContext);
			  

			  
			  strContext = StringUtils.replace(strContext, "GBK", "GB2312");
			  
			  isSuccessFlag = cIInsureCompareDncoder.decode(dbPool, blPolicy, strContext, iMaxSerialNo + 1, strComCode);
        dbPool.commitTransaction(); 
      }
      catch(Exception e)
      {
        dbPool.rollbackTransaction();
      }
      finally
      {
        dbPool.close();
      }
%>
    <%
    if(isSuccessFlag == 0)
    {
    %>
    	发送数据成功！<br><br>
    	请比较两个串的最后七位是否相同！
    	<br>返回串后八位分别是比对结果(1:完全一致; 0:存在差异),平台XX确认件数, 平台注销XX件数, 平台退XXXXX件数, 平台报案数, 平台立案数, 平台结案数
    	<br>
    <%	
    }
  	else
  	{
  	%>
  		发送数据失败！<br>
  	<%
  	}
    %>
    
    <br>最大序列号：
    <%=(iMaxSerialNo + 1)%>
    
    <br>发送数据：
    <%=strRequest%>
    
    <br>接收数据：
    <%=strContext%>