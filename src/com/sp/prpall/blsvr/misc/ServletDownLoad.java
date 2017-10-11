package com.sp.prpall.blsvr.misc;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.*;
import java.util.*;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;

import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.utiall.dbsvr.DBPrpdExpiresDataForClean;
import com.sp.utiall.schema.PrpdExpiresDataForCleanSchema;
import com.sp.utiall.blsvr.BLPrpdExpiresDataForClean;

import com.sp.utiall.dbsvr.DBPrpdExpiresDataForRenewal;
import com.sp.utiall.schema.PrpdExpiresDataForRenewalSchema;
import com.sp.utiall.blsvr.BLPrpdExpiresDataForRenewal;
import com.sp.sysframework.reference.AppConfig;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ServletDownLoad extends HttpServlet{

	protected final Log logger = LogFactory.getLog(getClass());
	/**
	 * @param args
	 */
	private static final String CONTENT_TYPE = "text/html; charset=GB2312";

	public void init() throws ServletException {
		
	}

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		ServletOutputStream sos = null;
		java.io.FileInputStream fileIn = null;
		PrintWriter out = null;
		try{
		int iResult = expiresDateForClean(request,response);	
		if(iResult==1)
		 {
		  
		  String strStartDate = request.getParameter("startDate");
		  String strEndDate = request.getParameter("endDate");
		  String fileName = strStartDate+"-"+strEndDate;	
		  String addressSave = AppConfig.get("sysconst.EIES_CLEANFILEUP_URL")+"\\"+fileName+".txt";
		  
		  logger.info("addressSave-------::::"+addressSave);
		  
		  
		  response.setContentType("APPLICATION/OCTET-STREAM");
		  
		  response.setHeader("Content-Disposition", "attachment;  filename="+ fileName+".txt" );
	      fileIn = new java.io.FileInputStream(addressSave);
	      sos    = response.getOutputStream();
		  
		  int intlength = fileIn.available();
		  byte buf_1[] = new byte[intlength];
		  while (intlength > 0) {
			fileIn.read(buf_1);
			sos.write(buf_1);
			intlength = fileIn.available();
		  }
		}
		else
		{
			String errorMessage = "对不起,您选择的期限内没有可生成的待清洗XXXXX信息!!!!";
			response.setContentType("text/html;charset=gb2312");
		    out = response.getWriter();
			/*out.println("<html>");
			out.println("<body>");
			out.println("<h1>");
			out.println("</body>");
			out.println("</html>");*/
		    out.println("<html><body>"+errorMessage+"</body></html>");
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		finally{
		    if(sos!=null)
		    {
			 sos.close();
		    }
		    if(fileIn!=null)
		    {
		     fileIn.close();
		    }
		    if(out!=null)
		    {
		     out.flush();
		     out.close();
		    }
		}
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		doGet(request,response);
	}
	
	public int expiresDateForClean(HttpServletRequest request, HttpServletResponse response){
		String strStartDate = "";
		String strEndDate = "";
		String fileName = "";
		
		int iResult = 1;
		try {
		
		strStartDate = request.getParameter("startDate");
		
		strEndDate = request.getParameter("endDate");
		
		fileName = strStartDate+"-"+strEndDate;		
		Vector vector = new Vector();
		BLPrpdExpiresDataForClean blPrpdExpiresDataForClean = new BLPrpdExpiresDataForClean();
		vector = blPrpdExpiresDataForClean.query(strStartDate,strEndDate);		
		
		if(vector.size()>0){
			blPrpdExpiresDataForClean.generateTxt(vector, fileName);
			
		}else{
			
			iResult=0;
		}
		
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		return iResult;
	}

	
	public void destroy() {
	}

	public static void main(String[] args) {
		
	

	}

}
