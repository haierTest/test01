package com.sp.prpall.newImageInput.servlet;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sp.prpall.newImageInput.common.MaterialFollwedHttpClient;
import com.sp.sysframework.reference.AppConfig;

public class MaterialTemplateInfoServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;


	public void destroy() {
		super.destroy();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
      doPost(request,response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
      String operateType=request.getParameter("operateType");
	  String fileTransIP=AppConfig.get("sysconst.NEW_IMAGE_SERVER_URL");
	  if(!fileTransIP.endsWith("/")){
		  fileTransIP=fileTransIP+"/";
	  }
      if("getTemplateInfo".equals(operateType)){
    	  String fileTrans=request.getParameter("fileTrans");
    	  if(fileTrans==null||fileTrans.length()==0){
    		  fileTrans=fileTransIP;
    	  }
    	  String flowId=request.getParameter("flowId");
    	  String docType=request.getParameter("docType");
    	  String templateIndex=request.getParameter("templateIndex");
    	  String requestURL="/filemanager/material/getImageInfo.do";
    	  String jsonData="";
          
    	  try {
    		  jsonData=MaterialFollwedHttpClient.getTemplateInfo(fileTrans+requestURL,fileTrans,flowId,docType,templateIndex);
		  } catch (Exception e) {
			  jsonData="error";
			  e.printStackTrace();
		  }
    	  
    	  Writer out=response.getWriter();
    	  out.write(jsonData);
    	  out.close();
      }else if("getTemplateInformationByPost".equals(operateType)){
    	  String docType=request.getParameter("docType");
    	  String clientType=request.getParameter("clientType"); 
    	  String requestURL="/filemanager/material/getList.do";
    	  String jsonData="";
    	  try {
    		  if(clientType!=null&&clientType!=""){
        		  jsonData=MaterialFollwedHttpClient.getTemplateInformationByPost(fileTransIP+requestURL,docType,clientType);
        	  }else{
        	      jsonData=MaterialFollwedHttpClient.getTemplateInformationByPost(fileTransIP+requestURL,docType);
        	  }
		  } catch (Exception e) {
			  jsonData="error";
			  e.printStackTrace();
		  }
    	 
    	  Writer out=response.getWriter();
    	  out.write(jsonData);
    	  out.close();
      }else if("showImage".equals(operateType)){
    	  String fileTrans=request.getParameter("fileTrans");
    	  if(fileTrans==null||fileTrans.length()==0){
    		  fileTrans=fileTransIP;
    	  }
    	  String flowId=request.getParameter("flowId");
    	  String docType=request.getParameter("docType");
    	  String requestURL="/filemanager/material/showImag.do";
    	  String jsonData="";
    	  
    	  try {
    		  jsonData=MaterialFollwedHttpClient.showImage(fileTrans+requestURL,fileTrans,flowId,docType);
		  } catch (Exception e) {
			  jsonData="error";
			  e.printStackTrace();
		  }
    	 
    	  Writer out=response.getWriter();
    	  out.write(jsonData);
    	  out.close();
      }

	}


	public void init() throws ServletException {
		
	}

}
