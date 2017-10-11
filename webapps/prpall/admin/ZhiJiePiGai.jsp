<%@page import="com.sp.indiv.ci.interf.*"%>
<%@page import="java.sql.SQLException"%>
<%@page import="com.sp.utility.database.DbPool"%>
<%@page import="com.sp.utility.SysConst"%>
<%@page import="org.apache.commons.logging.Log"%>
<%@page import="org.apache.commons.logging.LogFactory"%>


<%@ page errorPage="/UIErrorPage"%>
<%
	Log logger = LogFactory.getLog("RunTime");
    
	logger.info("immpg.jsp begin---------------**********------>>");
	

    String strEndorseNo     = null;         
    String strStartDate    = null;         
    String strEndDate      = null;         
    String strComCode      = null;				 
    
    strEndorseNo            =   request.getParameter("strEndorseNo");     
    strStartDate           =   request.getParameter("startdate");    
    strEndDate             =   request.getParameter("enddate");      
    strComCode						 =   request.getParameter("comcode");      
    
       
    
    
	
	logger.info("********************11********strEndorseNo="+strEndorseNo);
	

    DbPool dbpool1 = new DbPool();
    if(strEndorseNo != null)
    {
        try
        {
           dbpool1.open(SysConst.getProperty("DDCCDATASOURCE"));
           dbpool1.beginTransaction();
           ImmPG immPG = new ImmPG();
           immPG.sendEndorseNo(dbpool1, strEndorseNo); 
           dbpool1.commitTransaction();      
        }
        catch (Exception e)
        {
          dbpool1.rollbackTransaction();
          throw new Exception(e.getMessage());
        }
        finally
        {
          dbpool1.close();
        }  
%>             
        <br><br><p align="center"><b>直接批改成功!!!</b></p><br><hr><br> 
        <p align="center">批单号:<%=strEndorseNo%></p>
<%      
    }


    DbPool dbpool2 = new DbPool();
    
    if(strStartDate != null && strEndDate != null && strComCode != null)
    {
        try
        {
           ImmPG immPG = new ImmPG();
           immPG.sendDate(dbpool2, strStartDate, strEndDate, strComCode); 
        }
        catch(Exception e)
        { 
          dbpool2.rollbackTransaction();
          throw new Exception(e.getMessage());
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
   