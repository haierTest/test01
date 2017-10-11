package com.sp.client.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import com.sp.client.Util.XmlUtil;
import com.sp.client.bl.BLCheckValid;
import com.sp.client.bl.BLCreatePolicy;
import com.sp.client.bl.BLParseDom;
import com.sp.client.dto.Policy;
import com.sp.sysframework.exceptionlog.UserException;
import com.sp.sysframework.reference.DBManager;
import com.sp.utility.database.DbPool;

public class ReceiveInfo extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public ReceiveInfo() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); 
		
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=gb2312");
		PrintWriter out = response.getWriter();
		DBManager dbManager = new DBManager();
		DbPool dbpool = new DbPool();
		try {
			
			dbManager.open("undwrtDataSource");
			dbpool.setDBManager(dbManager);

			InputSource is = new InputSource(request.getInputStream());
			java.io.InputStream ins = is.getByteStream();
			Element element = XmlUtil.getRootElement(is);

			
			ArrayList policyList = BLParseDom.ParseDom(element);
			dbManager.beginTransaction();
			Iterator it = policyList.iterator();
			while (it.hasNext()) {
				Policy policyBean = (Policy) it.next();
				
				BLCheckValid bLCheckValid = new BLCheckValid();
				bLCheckValid.checkForm(policyBean);
				bLCheckValid.checkDbData(policyBean, dbManager);
				
				
				BLCreatePolicy.createPolicy(policyBean, dbpool, dbManager);
			}
			
			dbManager.commitTransaction();
		} catch (UserException usee) {
			try {
				dbManager.rollbackTransaction();
			} catch (Exception e1) {
			}
			usee.printStackTrace();
			out.println("<?xml version=\"1.0\" encoding=\"GB2312\"?>");
			out.println("<RETURN_INFO>");
			out.println("<RETURN_TYPE>-1></RETURN_TYPE>");
			out.println("<RETURN_MESSAGE>"+usee.getErrorMessage()+"</RETURN_MESSAGE>");
			out.println("</RETURN_INFO>");
			return;
		} catch (Exception e) {
			try {
				dbManager.rollbackTransaction();
			} catch (Exception e1) {
			}
			out.println("<?xml version=\"1.0\" encoding=\"GB2312\"?>");
			out.println("<RETURN_INFO>");
			out.println("<RETURN_TYPE>-1</RETURN_TYPE>");
			out.println("<RETURN_MESSAGE>"+e.getMessage()+"</RETURN_MESSAGE>");
			out.println("</RETURN_INFO>");
			e.printStackTrace();
			return;
		}

		
		finally {
			try {
				dbpool.close();
			} catch(Exception dbe) {
			}
			try {
				dbManager.close();
			} catch (Exception dbe) {
			}
		}

		
		out.println("<?xml version=\"1.0\" encoding=\"GB2312\"?>");
		out.println("<RETURN_INFO>");
		out.println("<RETURN_TYPE>'1'</RETURN_TYPE>");
		out.println("<RETURN_MESSAGE>²Ù×÷³É¹¦£¡</RETURN_MESSAGE>");
		out.println("</RETURN_INFO>");
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request,response);
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occure
	 */
	public void init() throws ServletException {
		
	}

}
