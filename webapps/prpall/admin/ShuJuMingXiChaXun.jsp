<%@page contentType="text/html;charset=GBK"%>
<%@ page import="com.sp.utility.*"%>
<%@ page import="com.sp.utility.error.*"%>
<%@ page import="com.sp.prpall.ui.UICentralControl"%>
<%@ page import="com.sp.prpall.ui.*"%>
<%@ page import="com.sp.indiv.ci.interf.*"%>
<%@page import="com.sp.utility.*"%>
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
    BLCIInsureCompareDetail blCIInsureCompareDetail     = new BLCIInsureCompareDetail();
    CIInsureCompareSchema cIInsureCompareSchema 				= new CIInsureCompareSchema();
    CIInsureCompareDetailSchema cIInsureCompareDetailSchema 	= new CIInsureCompareDetailSchema();
    CIInsureCompareDetailEncoder cIInsureCompareDetailEncoder 	= new CIInsureCompareDetailEncoder();
		CIInsureCompareDetailDecoder cIInsureCompareDetailDecoder 	= new CIInsureCompareDetailDecoder();
		
    
    
    String strRequest 				= ""; 				
		String strContext 				= ""; 				
		
		DateTime dateTime 		= new DateTime();
		String strCurrentDate = dateTime.current().addDay(-1).addDay(1).toString(); 

		
		int iMaxSerialNo 				= 0;	
		int iMaxSerialNoClaim 	= 0;	
		int isSuccessFlag				= 2;	
		
		
		
		
		String[] iRequestTypeArray = new String[]{"1","2","3","4","5","6","7"};
    
    DbPool dbPool = new DbPool();
    dbPool.open(SysConst.getProperty("DDCCDATASOURCE"));
    
    
    
    try
    {
        dbPool.beginTransaction();
        
        
			  iMaxSerialNo 			= blCIInsureCompanyCompare.getMaxSerialNo(dbPool, strCurrentDate);
			  iMaxSerialNoClaim = blCIInsureCompanyCompare.getMaxSerialNoClaim(dbPool, strCurrentDate);
				
			  
			  blPolicy.setCIInsureCompareDetail(blCIInsureCompareDetail);
			  for(int i = 0; i < iRequestTypeArray.length; i++)
			  {
			  	String iRequestType = iRequestTypeArray[i].trim();
			  	strContext = "";
			  	strRequest = "";
			  	strCurrentDate = dateTime.current().getYear()
   		  									+ ":" + dateTime.current().getMonth()
   		  									+ ":" + dateTime.current().getDate()
   		  									+ ":" + dateTime.current().getHour()
   		  									+ ":" + dateTime.current().getMinute()
   		  									+ ":" + dateTime.current().getSecond();
				
				logger.info("数据比对明细查询-strCompareDate: " + strCompareDate);
				logger.info("数据比对明细查询-iMaxSerialNo: " + iMaxSerialNo);
				logger.info("数据比对明细查询-iMaxSerialNoClaim: " + iMaxSerialNoClaim);
				logger.info("数据比对明细查询-iRequestType: " + iRequestType);
				logger.info("数据比对明细查询-strComCode: " + strComCode);
				

			  	
			  	strRequest = cIInsureCompareDetailEncoder.encode(dbPool, blPolicy, strCompareDate, 
			  												  											   iMaxSerialNo, iMaxSerialNoClaim, 
			  												  											   iRequestType, strComCode);

				
				logger.info("=====XXXXX交强XXXXX平台-数据比对明细查询返回串-strRequest: " + strRequest);
				

			  	strContext = EbaoProxy.getInstance().request(strRequest, strComCode);
				
				logger.info("=====XXXXX交强XXXXX平台-数据比对明细查询返回串-strContext: " + strContext);
				

			  	
			    strContext = StringUtils.replace(strContext, "GBK", "GB2312");
			  	
			  	cIInsureCompareDetailDecoder.decode(dbPool, blPolicy, strContext, iRequestType, iMaxSerialNo,
			  																			strComCode, strCurrentDate, strCompareDate);
			  }
			  
			  blCIInsureCompareDetail.save(dbPool);
			  
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
    
    <br>最大序列号：
    <%=(iMaxSerialNo)%>
    
    <br>发送数据：
    <%=strRequest%>
    
    <br>接收数据：
    <%=strContext%>