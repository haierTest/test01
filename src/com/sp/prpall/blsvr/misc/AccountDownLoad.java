package com.sp.prpall.blsvr.misc;
import javax.servlet.*;
import javax.servlet.http.*;

import java.io.*;
import java.util.*;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;

import com.sp.sysframework.reference.AppConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.utility.string.ChgDate;
import com.sp.utiall.dbsvr.DBPrpdExpiresDataForRenewal;
import com.sp.utiall.schema.PrpdExpiresDataForRenewalSchema;
import com.sp.utiall.blsvr.BLPrpdExpiresDataForRenewal;

public class AccountDownLoad  extends HttpServlet{

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
			
			String fileNameAccount = request.getParameter("systemDate")+"-AccountFile.txt";
			if(isExistAccountFile(fileNameAccount)){
				String addressSave = AppConfig.get("sysconst.EIES_ACCOUNTFILEUP_URL")+"\\"+fileNameAccount;
				
				response.setContentType("APPLICATION/OCTET-STREAM");
				
				response.setHeader("Content-Disposition", "attachment;  filename="+ fileNameAccount);
				
				fileIn = new java.io.FileInputStream(addressSave);
				
				sos = response.getOutputStream();
				int intlength = fileIn.available();
				byte buf_1[] = new byte[intlength];
				while (intlength > 0) {
					fileIn.read(buf_1);
					sos.write(buf_1);
					intlength = fileIn.available();
				 }
			}else{
			String errorMessage = "对不起,您选择的期限内没有可生成的对账XXXXX信息!!!!";
			response.setContentType("text/html;charset=gb2312");
		    out = response.getWriter();
		    out.println("<html><body>"+errorMessage+"</body></html>");
		}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
		    
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
	
	/**
	 *判断服务器上是否有对账文件
	 * @param strFileName 文件名
	 * @return 
	 * @exception
	 */
	public boolean isExistAccountFile(String strFileName){
		
    	
		String addressSave = AppConfig.get("sysconst.EIES_ACCOUNTFILEUP_URL");
    	File f = new File(addressSave);
    	if(f.isDirectory()){
    		File[] files = f.listFiles();
    		for(int i=0;i<files.length;i++){
    			String  fileName = files[i].getName();
    			if(fileName.trim()!=strFileName)
    				return false;
    		}
    	}
		return true;
	}
	
	public void destroy() {
	}
/**
 * @param args
 */
public static void main(String[] args) {
	

}

}
