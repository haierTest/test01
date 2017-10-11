package com.sp.prpall.newImageInput.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sp.prpall.newImageInput.xmlHandler.MaterialFollwedXMLHandler;

public class MaterailDealingServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	public void destroy() {
		super.destroy();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			doPost(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("GBK");
		String strData = "";

		String returnString = "";

		response.setHeader("Content-Type", "text/html;charset=GBK");
		Writer out = response.getWriter();
		
		InputStream in = request.getInputStream();
		try {
			
			strData = parseInputFlow2String(in);
		} catch (Exception e3) {
			returnString=MaterialFollwedXMLHandler.encodeReturnMessage("0","0","1000","读取XML数据流失败");
			out.write(returnString);
			out.close();
			in.close();
			e3.printStackTrace();
			return;
		}
		
		try {
			returnString=MaterialFollwedXMLHandler.imageDeal(strData);
		} catch (Exception e2) {
			e2.printStackTrace();
			returnString=MaterialFollwedXMLHandler.encodeReturnMessage("0","1","1000","服务器异常:"+e2.toString());
		}
		out.write(returnString);
		out.close();	
		in.close();
	}


	private String parseInputFlow2String(InputStream in) throws Exception{
		String returnString="";
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(in,"GBK"));
		StringBuffer sb = new StringBuffer(); 
		String line = null;
		while ((line = reader.readLine()) != null) {
			sb.append(line + "\n");
		}
		returnString=sb.toString();
		in.close();
		return returnString;
	}

	public void init() throws ServletException {

	}
}
