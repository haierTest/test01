<%--
****************************************************************************
* DESC       �����ݱȶ�
* Author     : chends
* CREATEDATE ��2007-04-25
* MODIFYLIST ��
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
			  
			  logger.info("============XXXXX��ǿXXXXXƽ̨���ݱȶ�strRequest: " + strRequest);
			  

			  strContext 	= EbaoProxy.getInstance().request(strRequest, strComCode);
			  
			  logger.info("============XXXXX��ǿXXXXXƽ̨���ݱȶ�strContext: " + strContext);
			  

			  
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
    	�������ݳɹ���<br><br>
    	��Ƚ��������������λ�Ƿ���ͬ��
    	<br>���ش����λ�ֱ��ǱȶԽ��(1:��ȫһ��; 0:���ڲ���),ƽ̨XXȷ�ϼ���, ƽ̨ע��XX����, ƽ̨��XXXXX����, ƽ̨������, ƽ̨������, ƽ̨�᰸��
    	<br>
    <%	
    }
  	else
  	{
  	%>
  		��������ʧ�ܣ�<br>
  	<%
  	}
    %>
    
    <br>������кţ�
    <%=(iMaxSerialNo + 1)%>
    
    <br>�������ݣ�
    <%=strRequest%>
    
    <br>�������ݣ�
    <%=strContext%>