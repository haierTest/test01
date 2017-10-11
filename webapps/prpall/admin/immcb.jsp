<%@page import="com.sp.indiv.ci.interf.*"%>
<%@page import="java.sql.SQLException"%>
<%@page import="com.sp.utility.database.DbPool"%>
<%@page import="com.sp.utility.SysConst"%>
<%@page import="org.apache.commons.logging.Log"%>
<%@page import="org.apache.commons.logging.LogFactory"%>


<%@ page errorPage="/UIErrorPage"%>
<%
	Log logger = LogFactory.getLog("RunTime");
    
	logger.info("immcb.jsp begin--------------------->>");
	

    String strPolicyNo     = null;         
    String strStartDate    = null;         
    String strEndDate      = null;         
    String strComCode      = null;				 
    
    strPolicyNo            =   request.getParameter("policyno");     
    strStartDate           =   request.getParameter("startdate");    
    strEndDate             =   request.getParameter("enddate");      
    strComCode						 =   request.getParameter("comcode");      
    
       
    
    
    DbPool dbpool1 = new DbPool();
    if(strPolicyNo != null)
    {
        try
        {
           dbpool1.open(SysConst.getProperty("DDCCDATASOURCE"));
           dbpool1.beginTransaction();
           ImmCB immCb = new ImmCB();
           immCb.sendPolicy(dbpool1, strPolicyNo); 
           dbpool1.commitTransaction();      
        }
        catch (Exception e)
        {
          dbpool1.rollbackTransaction();
        }
        finally
        {
          dbpool1.close();
        }  
%>             
        <br><br><p align="center"><b>单个XX直接XX成功!!!</b></p><br><hr><br> 
        <p align="center">XX号:<%=strPolicyNo%></p>
<%      
    }
    
    
    DbPool dbpool2 = new DbPool();
    
    if(strStartDate != null && strEndDate != null)
    {
        try
        {
           
           
           
           ImmCB immCb = new ImmCB();
           immCb.sendDate(dbpool2, strStartDate, strEndDate, strComCode);
           
        }
        catch(Exception e)
        {
          e.printStackTrace();
          dbpool2.rollbackTransaction();
        }
        finally
        {
          dbpool2.close();
        }
%>             
        <br><br><p align="center"><b>批量直接XX成功!!!</b></p><br><hr><br>
<%    
    }
	
	logger.info("immcb.jsp end---------------------====");
	

%>
   