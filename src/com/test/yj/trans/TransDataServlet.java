package com.test.yj.trans;

import javax.servlet.*;
import javax.servlet.http.*;

import com.sp.sysframework.reference.AppConfig;

import java.io.*;
import java.sql.*;
import java.util.*;

public class TransDataServlet extends HttpServlet {
  private static final String CONTENT_TYPE = "text/html; charset=GB2312";
  private static  int startFlag = 1;
  
  public void init() throws ServletException {
  	 
     
	 


  	 
  }

  
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  	 try {
    	request.setCharacterEncoding("GBK");
	 }catch (UnsupportedEncodingException ex) {
	 }
  }
  
 
}