package com.test.yj.dto;

import java.io.IOException;
import java.util.*;
import java.net.*;
import com.test.euse.*;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
public class HLHttpClient {
	private java.util.Properties pro = new Properties();
	static String proxyPort=null;
	static String proxyIpAddress=null;
	static String urlRequest=null;
	HttpClient client = null;
	HostConfiguration objHostConfiguration = null;
	PostMethod m = null;
    private DB                      db                       = DB.getDB(); 

    
	protected final static Log logger = LogFactory.getLog(HLHttpClient.class);
    

    public static void main(String []args){
    	String str = new HLHttpClient().getResponse("YGBX", "507",
				"a", "20050901092401", "20070925000001");
		XmltoConnection xtc = new XmltoConnection();
		ArrayList list =new ArrayList();
		list = xtc.excute(str);
	    
		logger.info(str);
	    
    }
	/**
	 * @param args
	 */
	public void  getPly() {
		DbPool dbpool=null;
		
	    
		long maxid=db.queryRecordID("select max(recordid) from HL_INSURANCEREQUEST");
	    
		logger.info(maxid);
	    
		ArrayList requestRecord = new ArrayList();
	    try{
		
  	    dbpool = new DbPool();	
	    dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
	    requestRecord=db.queryAgtManager("select COMPANYCODE,COMPANYNAME,to_char(ENDTIME+1/24/3600,'yyyymmddhh24miss'),PASSWORD,to_char(sysdate,'yyyymmddhh24miss') from HL_INSURANCEREQUEST where recordid="+maxid+"",dbpool);
	    }catch(Exception e){
	    }finally{
	       try{
	       	 dbpool.close();
	       	}catch(Exception es){}
	    }
	    ArrayList itemList =(ArrayList)requestRecord.get(0);
	    String beginTime=(String)itemList.get(2);
	    String endTime=(String)itemList.get(4);
	    String COMPANYCODE=(String)itemList.get(0);
	    String COMPANYNAME=(String)itemList.get(1);
	    String PASSWORD=(String)itemList.get(3);
	    
	    logger.info("ssss");
	    logger.info(COMPANYCODE);
	    logger.info(PASSWORD);
	    logger.info(COMPANYNAME);
	    logger.info(beginTime);
	    logger.info(endTime);
	    
		String str = new HLHttpClient().getResponse(COMPANYCODE, PASSWORD,
				COMPANYNAME, beginTime, endTime);
		XmltoConnection xtc = new XmltoConnection();
		ArrayList list =new ArrayList();
		try{
			list = xtc.excute(str);
		}catch(Exception e){
		    
			logger.error(e.toString());
		    
		}
	    
		logger.info("size:"+list.size());
	    
		if(list.size()>0){
			try{
				CreateTpolicys createt =new CreateTpolicys();
			    createt.createTpolicys(list);
			}catch(Exception ee){
				ee.printStackTrace();
			}
			
		}
		
		maxid=maxid+1;
		String insertSql="insert into HL_INSURANCEREQUEST (COMPANYCODE,COMPANYNAME,BEGINTIME,ENDTIME,RECORDID,PASSWORD) values('"
		                 +COMPANYCODE+"','"+COMPANYNAME+"',to_date('"+beginTime+"','yyyymmddhh24miss'),to_date('"+endTime+"','yyyymmddhh24miss'),"+maxid+",'"+PASSWORD+"')";
	    
		logger.info(insertSql);
	    
		db.exceUpdate(insertSql);
	}


	public HLHttpClient() {
		client = new HttpClient();
		try{
			
			if(proxyPort==null){
				pro.load(this.getClass().getResourceAsStream(
           				"urlConfigure.properties"));
           		proxyPort=pro.getProperty("proxyPort");
           		proxyIpAddress=pro.getProperty("proxyIpAddress");
           		urlRequest=pro.getProperty("urlRequest");
           	}		
		}catch(IOException e){
		    
			logger.error("url配置文件不存在");
		    
			
		}
		String proxyFlag=pro.getProperty("proxyFlag");
		if(proxyFlag!=null)
		  proxyFlag=proxyFlag.trim();
		  
		if(proxyFlag!=null&&proxyFlag.equals("1")){
			int portTemp=Integer.parseInt(proxyPort);
			objHostConfiguration = new HostConfiguration();
			objHostConfiguration.setProxy(proxyIpAddress, portTemp);
			client.setHostConfiguration(objHostConfiguration);
		}
		m = new PostMethod(urlRequest);
	}


	public String getResponse(String companyCode, String password,
			String companyName, String beginTime, String endTime) {

		StringBuffer s = new StringBuffer();
		StringBuffer sb = new StringBuffer();
		sb.append("<InsuranceRequest><CompanyCode>");
		sb.append(companyCode);
		sb.append("</CompanyCode>");
		sb.append("<Password>");
		sb.append(password);
		sb.append("</Password>");
		sb.append("<CompanyName>");
		sb.append(companyName);
		sb.append("</CompanyName>");
		sb.append("<BeginTime>");
		sb.append(beginTime);
		sb.append("</BeginTime>");
		sb.append("<EndTime>");
		sb.append(endTime);
		sb.append("</EndTime></InsuranceRequest>");

		m.setParameter("request", sb.toString());

		try {
			client.executeMethod(m);
			s.append(m.getResponseBodyAsString());
		} catch (IOException e) {
			return "";
		}
		return s.toString();
	}
}
