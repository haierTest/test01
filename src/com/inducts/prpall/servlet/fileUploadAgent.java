/**
 * 
 */
package com.inducts.prpall.servlet;

/**
 * @author zhouming
 *
 */


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadException;

import com.inducts.prpall.dao.*;
import com.inducts.prpall.dto.BankInterfaceDetailDTO;
import com.inducts.prpall.dto.BankInterfaceSumDTO;
import com.sp.sysframework.reference.DBManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class fileUploadAgent extends HttpServlet {
	
	

	private static final String CONTENT_TYPE  = "text/html; charset=GB2312";
	
	private String strOperatorCode=null;
	private String strComCode = null;
	private String strMakeCom=null;
	private String strUser=null;
	
	Log logger = LogFactory.getLog(getClass());
	
    /**
     * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
    	try {
            request.setCharacterEncoding("GBK"); 
       }catch (UnsupportedEncodingException ex) {
       }
    	PrintWriter out = response.getWriter();
    	
    	logger.info("fileupload incoming");
    	
    	
    	boolean isMultipart = FileUpload.isMultipartContent(request);
    	if(!isMultipart){
    		throw new ServletException("不是一个文件上传的请求");
    	}
    	DiskFileUpload fu = new DiskFileUpload();
    	fu.setSizeMax(1*1024*1024);
    	
    	List items =null;
    	try{
    		items = fu.parseRequest(request);
    	}catch(FileUploadException e)
    	{
    		throw new ServletException("上传文件出错");
    	}
        fu.setRepositoryPath("c:\\tmp");
        
        strOperatorCode = (String)request.getSession().getValue("UserCode");
        strComCode = (String)request.getSession().getValue("ComCode");
           
        
        
        
        
       	
       	Iterator iter = items.iterator();
   		List sumDTO = null;
   		List details= null;
   		boolean flagend = false;
       	while (iter.hasNext()) {
       	    FileItem item = (FileItem) iter.next();
       	    if (!item.isFormField()) 
       	    	try{
       	    		if(item.getFieldName().equals("file_invest_sum"))
       	    		sumDTO=getBankInterfaceSumDTO(item);
       	    		
       	    		if(item.getFieldName().equals("file_invest_detail"))
       	    		details=getInvestDetails(item);
       	    		
       	    		if (details == null)
	       	    		
	       	    		logger.info("DEEEEEEEEEE");
	       	    		
       	    		if (sumDTO == null)
	       	    		
	       	    		logger.info("FFFFFFFFFF");
	       	    		
       	    		
       	    	}
       	    	catch(ServletException em)
       	    	{
       	    		flagend = true;
       	    	}
       	    	catch(Exception e)
       	    	{
       	    		e.printStackTrace();
       
       	    	}
       	    }
       	
       		BankInterfaceDAO dao = new BankInterfaceDAO();
   			try{
   	   		
    			boolean upFlag = false;
    			boolean istimeout = false;
    			if(strOperatorCode==null)
    			{
    				istimeout = true;
    			}
    			if(istimeout)
    			{
    				out.println("<html>");                                
        			out.println("<head><title>数据导入</title></head>");
        			out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\">");
        			out.println("<script language=\"JavaScript\">");        
        			out.println("alert(\"timeout!\")");                                      
        			out.println("</script>");                             
        			out.println("<body>");                                
        			out.println("</body></html>");   				   
    			}    			
    			if(strOperatorCode!=null&&flagend==false)
    			{
    				upFlag  = dao.insertBandInfoSumAndDetail(details,sumDTO);
    			}
    			if(flagend)
    			{
    				out.println("<html>");                                
        			out.println("<head><title>数据导入</title></head>");
        			out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\">");
        			out.println("<script language=\"JavaScript\">");        
        			out.println("alert(\"The content of the document does not end End!\")");                                      
        			out.println("</script>");                             
        			out.println("<body>");                                
        			out.println("</body></html>"); 
    			}
    			else
    			{
    			   
    			  
    			  
    				String s="";
    				if(istimeout==false)
    				{
    				if(upFlag)
    				{
    				
    					out.println("<html>");                                
    					out.println("<head><title>数据导入</title></head>");
    					out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\">");
    					out.println("<script language=\"JavaScript\">");        
    					out.println("alert(\"success!\")");                                      
    					out.println("</script>");                             
    					out.println("<body>");                                
    					out.println("</body></html>");                         
    				}
    				else
    				{
    				
    					out.println("<html>");                                
    					out.println("<head><title>数据导入</title></head>");  
    					out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\">");
    					out.println("<script language=\"JavaScript\">");       
    					out.println("alert(\"fail!\")");                                      
    					out.println("</script>");                             
    					out.println("<body>");                                
    					out.println("</body></html>");                         
    				}
    				}
    			}
   	   		
   			}catch(Exception e)
   			{
   				e.printStackTrace();
   			}

       	
    }
    /**
     * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
    
    
    private List getBankInterfaceSumDTO(FileItem item) throws ServletException,Exception
    {
       
       logger.info(item.getFieldName());
       
       Date uploaddate = new Date();
       String name=item.getName();
   	   name = name.replace(':','_');
       name = name.replace('\\','_');
	   
	   logger.info(name);
	   
   	   item.write(new File("tmp\\"+name));
   	   List return_list = new ArrayList();
       BankInterfaceSumDTO dto=null;
   	   File file = new File("tmp\\"+name);
   	   FileReader reader = new FileReader(file);
   	   BufferedReader br = null;
   	   int flagEnd = 0;
    	try{
    	br = new BufferedReader(reader);    	
    	String s1 = null;
    	  while((s1 = br.readLine()) != null) {
    		  	 if(s1.length()==3&&s1.equals("END"))
    		  	 {
    		  		flagEnd = 1;
    		  	 }
    		  	 else
    		  	 {
    		  		 String[] ss1=s1.split("\\|");
    		  		 
    		  		 logger.info(s1);
    		  		 
    		  		 dto= new BankInterfaceSumDTO();
    		  		 int i=0;
    		  		 dto.setBankCode(ss1[i++]);
    		  		 dto.setBranchBankCode(ss1[i++]);
    		  		 dto.setPolcount(ss1[i++]);
    		  		 dto.setOperatedate(ss1[i++]);
    		  		 dto.setSumprem(ss1[i++]);
    		  		 dto.setAnnulCount(ss1[i++]);
    		  		 dto.setTotal(ss1[i++]);
    		  		 dto.setInvestcount(ss1[i++]);        		 
    		  		 dto.setRiskCode(ss1[i++]);
    		  		 dto.setOperFlag("0");
    		  		 dto.setDataflag("0");    		 
    		  		 return_list.add(dto);
    		  		 
    		  		 logger.info(dto.toString());
    		  		 
    		  	 }

    	  }
    	  if(flagEnd==0)
    	  {
    		  throw new ServletException("The content of the document does not end \"End\"!");
    	  }

    	
    	
    	}catch(IOException e)
    	{
    		throw new IOException("读取文件出错！");
    	}finally{
    		 br.close();
    		 reader.close();
    	}
    	file.delete();
    	 return return_list;
    }
    
    private List getInvestDetails(FileItem item) throws ServletException,Exception
    {
	   DBManager dbManager = new DBManager();
	   dbManager.open("ddccDataSource");
	   ResultSet rs = null;
       String name=item.getName();
   	   name = name.replace(':','_');
       name = name.replace('\\','_');
	   
	   logger.info(name);
	   
   	   item.write(new File("tmp\\"+name));
   	   List return_list = new ArrayList();
   	   
   	   File file = new File("tmp\\"+name);
   	   FileReader reader = new FileReader(file);
   	   BufferedReader br = null;
   	   int flagEnd = 0;
    	try{
    	br = new BufferedReader(reader);
     	String s1 = null;
   	    BankInterfaceDetailDTO ddto=null;
   	 	while((s1 = br.readLine()) != null) {
		  if(s1.length()==3&&s1.equals("END"))
		  {
			  flagEnd = 1;
		  }
		  else
		  {
			  String riskcode = "";
			  String strprintno = "";
			  
			  logger.info("HHHHHHHHHHHHH   "+s1);
			  
			  String[] ss1=s1.split("\\|");
			  
			  logger.info(ss1.length);
			  
			  ddto= new BankInterfaceDetailDTO();		  
			  int i=0;		  
			  ddto.setUploadFileSeq(ss1[i++]);		
			  ddto.setBankcode(ss1[i++]);			
			  ddto.setBankBranchCode(ss1[i++]);		
			  ddto.setCounterperson(ss1[i++]);		
			  ddto.setAppliName(ss1[i++]);			
			  ddto.setMobileCode(ss1[i++]);			
			  ddto.setPhone(ss1[i++]);				
			  ddto.setEmail(ss1[i++]);				
			  ddto.setPostCode(ss1[i++]);			
			  ddto.setAddress(ss1[i++]);		  	
			  ddto.setIdType(ss1[i++]);				
			  ddto.setAppIdno(ss1[i++]);			
			  ddto.setInsuredName(ss1[i++]);		
			  ddto.setInsuredAdress(ss1[i++]);		
			  ddto.setInsuredPhone(ss1[i++]);		
			  ddto.setInsurePostCode(ss1[i++]);		
			  ddto.setRelation(ss1[i++]);			
			  ddto.setEstatePostCode(ss1[i++]);		
			  ddto.setEstateAddress(ss1[i++]);		
			  ddto.setUseFor(ss1[i++]);				
			  ddto.setProposalNo(ss1[i++]);			
			  ddto.setRiskCode(ss1[i++]);			
			  riskcode=ddto.getRiskCode();
			  ddto.setPolicyno(ss1[i++]);			
			  strprintno = ss1[i++];
			  while(strprintno.length()<10)
			  {
				  strprintno = "0"+strprintno;
			  }
			  ddto.setPrintno(strprintno);	    
			  ddto.setInvestCount(ss1[i++]);		
			  ddto.setBfBankCode(ss1[i++]);			
			  ddto.setBfAccountNo(ss1[i++]);	  	
			  ddto.setInvestDate(ss1[i++]);			
			  ddto.setSumPremium(ss1[i++]);			
			  ddto.setSumamount(ss1[i++]);			
			  ddto.setStartDate(ss1[i++]);			
			  ddto.setEndDate(ss1[i++]);			
			  ddto.setOperateDate(ss1[i++]);		
		  	  ddto.setDataoutdate(ss1[i++]);		
		  	  ddto.setInvalid(ss1[i++]);			
		  	  ddto.setPoundageRate(ss1[i++]);		
		  	  rs = dbManager.executeQuery("select period,classcode,riskname from prpdbankinvest where riskcode='"+riskcode+"' and comcode='"+strComCode+"'");
		  	  if(rs.next())
		  	  {
		  		  ddto.setInsureTerm(rs.getString("period"));			
		  		  ddto.setClassCode(rs.getString("classcode"));			
		  		  ddto.setRiskName(rs.getString("riskname"));			
		  	  }
		  	  rs.close();
		  	  ddto.setCurrency("CNY");						
		  	  
		  	  rs = dbManager.executeQuery("select makecom from prpduser where usercode='"+strOperatorCode+"'");
		  	  if(rs.next())
		  	  {
		  		  ddto.setMakeCom(rs.getString("makecom"));
		  		  strMakeCom = rs.getString("makecom");
		  	  }
		  	  rs.close();
		  	  ddto.setComCode(strMakeCom);
		  	  ddto.setHandler1code(strComCode);		
		  	  ddto.setHandlerCode(strOperatorCode);		  	
		  	  ddto.setOperFlag("0");						
		  	  return_list.add(ddto);	
		  	  
		  	  logger.info(ddto.toString());
		  	  
		  }
   	 	}
  	  	if(flagEnd==0)
  	  	{
		  throw new ServletException("The content of the document does not end \"End\"!");
  	  	}
    	}
    	catch(IOException e)
    	{
    		throw new IOException("读取文件出错！");
    	}
    	catch(Exception em)
    	{
    		em.printStackTrace();
    		
    		return_list.clear();
    		return return_list;
    		
    	}
    	finally{
    		 dbManager.close();
    		 br.close();
    		 reader.close();
    }
    	file.delete();
    	 return return_list;
    }
    
 


}
