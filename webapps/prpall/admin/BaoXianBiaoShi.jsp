<%@page import="com.sp.indiv.ci.interf.*"%>
<%@page import="java.sql.SQLException"%>
<%@page import="com.sp.utility.database.DbPool"%>
<%@page import="com.sp.utility.SysConst"%>



<%@ page errorPage="/UIErrorPage"%>
<%
    RepeatSendCISignNo repeatSendCISignNo = new RepeatSendCISignNo();          
    
    
    String strSigPolicyNo  = null;         
    String strRownumMax    = null;         
    strSigPolicyNo         =   request.getParameter("sigPolicyno");  
    strRownumMax           =   request.getParameter("rownumMax");    
    
    DbPool dbpool = new DbPool();
    dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
    try
    {
    		dbpool.beginTransaction();
        if(strSigPolicyNo!=null)
        {
           
           
           
           repeatSendCISignNo.repeatSendCISignNo(dbpool, "policyno = '" + strSigPolicyNo + "'");
           
%>         
           <br><br><p align="center"><b>����XX��־�ϴ��ɹ�!!!</b></p><br><hr><br> 
           <p align="center">XX��:<%=strSigPolicyNo%></p>
<%           
        }  
        if(strRownumMax != null)
        {
        
            
            
            
            repeatSendCISignNo.repeatSendCISignNo(dbpool, "rownum < " + strRownumMax);
            
%>          
            <br><br><p align="center"><b>�����ϴ�XX��־�ɹ�!!!</b></p><br><hr><br>
            <p align="center">�ϴ�����:<%= java.lang.Integer.parseInt(strRownumMax) - 1 %></p>
<%        
        }
    		dbpool.commitTransaction(); 
    }
    catch (Exception e)
    {
      dbpool.rollbackTransaction();
    }
    finally
    {
      dbpool.close();
    }
%>
   