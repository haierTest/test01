/*
 * 
 */
package com.sp.indiv.ci.interf;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.ResultSet;
import java.util.Calendar;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import com.sp.prpall.blsvr.tb.BLProposal;
import com.sp.sysframework.common.util.XMLUtils;
import com.sp.sysframework.reference.AppConfig;
import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;
import com.sp.utility.string.ChgDate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.sp.indiv.ci.blsvr.BLCIInsureDemandLog;
import com.sp.indiv.ci.schema.CIInsureDemandLogSchema;
import com.sp.utility.UtiPower; 

public class EbaoProxy {
	Log logger = LogFactory.getLog(getClass());
    private static EbaoProxy ebaoProxy = new EbaoProxy();
    
    /*add by liujia start*/
    private static final  String CI_ALLOW_SEND_REQUEST="CI_ALLOW_SEND_REQUEST";
    /*add by liujia end*/
    private EbaoProxy() {
    	
    	logger.info("EbaoProxy类初始化！");
    	
    	
    	
    }

    public static EbaoProxy getInstance() {
        return ebaoProxy;
    }

    /**
     * 请求服务
     * @param requestXML 请求串
     * @return
     * @throws Exception
     */
    public String request(String requestXML, String strComCode) throws Exception {
    	int intConnectTimeout=Integer.parseInt(AppConfig.get("sysconst.CI_DEFAULT_CONNECT_TIMEOUT"));
    	int intReadTimeout= Integer.parseInt(AppConfig.get("sysconst.CI_DEFAULT_READ_TIMEOUT"));
    	String strURL = AppConfig.get("sysconst.CI_INSURED_" + strComCode.substring(0, 2) +  "_URL");
    	
    	HttpClient httpClient = new HttpClient();

    	  
    	  logger.info("come in");
    	  
		  HttpConnectionManagerParams managerParams = httpClient.getHttpConnectionManager().getParams();
		  
		  managerParams.setConnectionTimeout(intConnectTimeout);
		  
		  managerParams.setSoTimeout(intReadTimeout);
		  PostMethod postMethod = new PostMethod(strURL);
		  
		  String strResponse = null;
		  StringBuffer buffer = new StringBuffer();

        try {
		   postMethod.setRequestEntity(new StringRequestEntity( requestXML, "text/xml", "GBK"));
		    
		   int statusCode = httpClient.executeMethod(postMethod);
		   if (statusCode != HttpStatus.SC_OK) {
		    throw new IllegalStateException("Method failed: "  + postMethod.getStatusLine());
		   }
		   
		   BufferedReader reader = null;
           reader = new BufferedReader(new InputStreamReader(postMethod.getResponseBodyAsStream()));
           while ((strResponse = reader.readLine()) != null) {
               buffer.append(strResponse);
           }
		   
        } catch (Exception ex) {
        	ex.printStackTrace();
            throw ex;
        } finally {
        	
        	postMethod.releaseConnection();
        }
        if (buffer.length() < 1){
  			throw new Exception("交强XXXXX平台返回数据失败！");
        }
        return buffer.toString();
    }
    
    public String biRequest(String requestXML, String strComCode) throws Exception {
    	int intConnectTimeout=Integer.parseInt(AppConfig.get("sysconst.BI_DEFAULT_CONNECT_TIMEOUT"));
    	int intReadTimeout= Integer.parseInt(AppConfig.get("sysconst.BI_DEFAULT_READ_TIMEOUT"));
    	String strURL = AppConfig.get("sysconst.BI_INSURED_" + strComCode.substring(0, 2) +  "_URL");
    	
    	logger.info("=======URL=========="+strURL);
    	
    	HttpClient httpClient = new HttpClient();

    	
    	logger.info("come in");
    	
		  HttpConnectionManagerParams managerParams = httpClient.getHttpConnectionManager().getParams();
		  
		  managerParams.setConnectionTimeout(intConnectTimeout);
		  
		  managerParams.setSoTimeout(intReadTimeout);
		  PostMethod postMethod = new PostMethod(strURL);
		  
		  String strResponse = null;
		  StringBuffer buffer = new StringBuffer();

        try {
		   postMethod.setRequestEntity(new StringRequestEntity( requestXML, "text/xml", "GBK"));
		    
		   int statusCode = httpClient.executeMethod(postMethod);
		   if (statusCode != HttpStatus.SC_OK) {
		    throw new IllegalStateException("Method failed: "  + postMethod.getStatusLine());
		   }
		   
		   BufferedReader reader = null;
           reader = new BufferedReader(new InputStreamReader(postMethod.getResponseBodyAsStream()));
           while ((strResponse = reader.readLine()) != null) {
               buffer.append(strResponse);
           }
		   
        } catch (Exception ex) {
        	ex.printStackTrace();
            throw ex;
        } finally {
        	
        	postMethod.releaseConnection();
        }
        if (buffer.length() < 1){
  			throw new Exception("交强XXXXX平台返回数据失败！");
        }
        return buffer.toString();
    }
    
    
    /*add by  liujia start*/
    /*reason:增加发送权限校验方法*/
    /**
     * 请求服务
     * @param comcode 归属机构
     * @return
     * @throws Exception 
     * @throws Exception
     */    
    public boolean  checkAllowSendQuestPower(String comcode) throws Exception{
    	try{
    		if(SysConfig.getProperty(CI_ALLOW_SEND_REQUEST).trim().indexOf(comcode.substring(0,2))>-1){
    		      return false;
    		}else{
    			  return true;
    		}
    	}catch(Exception ex){
    		throw new Exception("缺少配置项"+ex);
    	}
    }
   
    
    public boolean  checkCiTimeAllowSendQuestPower(String comcode) throws Exception{
        try{
            
            logger.info(AppConfig.get("sysconst.CI_ALLOW_SEND_REQUEST"));
            
            
            if(AppConfig.get("sysconst.CI_ALLOW_SEND_REQUEST").trim().indexOf(comcode.substring(0,2))>-1){
                  return false;
            }else{
                  return true;
            }
        }catch(Exception ex){
            throw new Exception("缺少配置项"+ex);
        }
    }
    /**
     * 请求服务
     * @param requestXML 请求串
     * @return
     * @throws Exception
     */
    
    public String ciTimeRequest(String requestXML, String strComCode) throws Exception {
        
        logger.info("sun.net.client.defaultConnectTimeout--:" + System.getProperty("sun.net.client.defaultConnectTimeout"));
        logger.info("sun.net.client.defaultReadTimeout--:" + System.getProperty("sun.net.client.defaultReadTimeout"));
        
        StringBuffer buffer = new StringBuffer();
        String strMessage = "";
        String strURL = "";
        InputStream inputStream = null;
        OutputStream outputStream = null;

        BufferedReader reader = null;
        OutputStreamWriter writer = null;
        URL url = null;
        HttpURLConnection connection = null;
        strURL = AppConfig.get("sysconst.CI_INSURED_" + strComCode.substring(0, 2) +  "_URL");
        
        logger.info("======EbaoProxy-strURL: " + strURL);
        

        

        try {
            
            
            
            
            
            
            
            if(checkCiTimeAllowSendQuestPower(strComCode)){
                url = new URL(strURL);
                connection = (HttpURLConnection) url.openConnection();

                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setRequestMethod("POST");
                connection.setAllowUserInteraction(true);
                connection.connect();
                outputStream = connection.getOutputStream();
                writer = new OutputStreamWriter(outputStream);
                writer.write(requestXML);
                writer.flush();
                writer.close();

                inputStream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(inputStream));
                while ((strMessage = reader.readLine()) != null) {
                    buffer.append(strMessage);
                }
                if (buffer.length() < 1){
                    throw new Exception("  与强三平台交互失败！ ");
                }
            }else{
                throw new Exception("与平台网络连接关闭！");
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return buffer.toString();
    }    
    /*add by liujia end*/
    public String request(String requestXML, String strComCode, BLProposal blProposal) throws Exception {
        int intConnectTimeout=Integer.parseInt(AppConfig.get("sysconst.CI_DEFAULT_CONNECT_TIMEOUT"));
        int intReadTimeout= Integer.parseInt(AppConfig.get("sysconst.CI_DEFAULT_READ_TIMEOUT"));
        String strURL = AppConfig.get("sysconst.CI_INSURED_" + strComCode.substring(0, 2) +  "_URL");
        
        HttpClient httpClient = new HttpClient();
        
        long time1=System.currentTimeMillis();
        String iniTime =  new ChgDate().getCurrentTime("yyyy-MM-dd HH:mm:ss");
        
          
          logger.info("come in");
          
          HttpConnectionManagerParams managerParams = httpClient.getHttpConnectionManager().getParams();
          
          managerParams.setConnectionTimeout(intConnectTimeout);
          
          managerParams.setSoTimeout(intReadTimeout);
          PostMethod postMethod = new PostMethod(strURL);
          
          String strResponse = null;
          StringBuffer buffer = new StringBuffer();

          String flag = "";
          try {
           postMethod.setRequestEntity(new StringRequestEntity( requestXML, "text/xml", "GBK"));
           flag = "0";
           int statusCode = httpClient.executeMethod(postMethod);
           if (statusCode != HttpStatus.SC_OK) {
               flag = "4";
               throw new IllegalStateException("Method failed: "  + postMethod.getStatusLine());
           }
           BufferedReader reader = null;
           flag = "3";
           reader = new BufferedReader(new InputStreamReader(postMethod.getResponseBodyAsStream()));
           while ((strResponse = reader.readLine()) != null) {
               buffer.append(strResponse);
           }
        } catch (Exception ex) {
            if("3".equals(flag)){
                flag = "5";
            }else if("0".equals(flag)){
                flag = "6";
            }
            ex.printStackTrace();
            throw ex;
        } finally {
            DbPool dbPool = new DbPool();
            try{
                String errorMessage = "";
                String demandNo = "";
                String ResponseCode = "";
                String renewalFlag = "";
                
                if("3".equals(flag)){
                    try{
                        
                        InputStream in = new ByteArrayInputStream(buffer.toString().getBytes());
                        Document document = XMLUtils.parse(in);
                        Node node = null;
                        Node node2 = null;
                        node  = XMLUtils.getChildNodeByPath(document, "/PACKET/HEAD");
                        node2 = XMLUtils.getChildNodeByPath(document, "/PACKET/BODY/BASE_PART");
                        if(node!=null){
                            
                            errorMessage = XMLUtils.getChildNodeValue(node, "ERROR_MESSAGE");
                            ResponseCode = XMLUtils.getChildNodeValue(node, "RESPONSE_CODE");
                        }
                        if(node2!=null){
                            
                            demandNo = XMLUtils.getChildNodeValue(node2, "QUERY_SEQUENCE_NO");
                            
                            renewalFlag = XMLUtils.getChildNodeValue(node2, "RENEWAL_FLAG");
                        }
                        if(ResponseCode!=null && !"1".equals(ResponseCode)){
                            flag = "1";
                        }
                        if(renewalFlag!=null && "1".equals(renewalFlag)){
                            flag = "2";
                        }
                    }catch(Exception e){
                        errorMessage = e.toString();
                    }
                }
                
                if("3".equals(flag)){
                	long time2=System.currentTimeMillis();
                	double difference = time2 - time1;
                	String rule = SysConfig.getProperty("saveCILogSwitch_Second");
                	int second = Integer.parseInt(rule);
                	
                	if(difference/1000>second){
                		flag ="7";
                	}
                }
                dbPool.open("platformNewDataSource");  
                if(UtiPower.saveCILogSwitch(strComCode,flag)){
                	BLCIInsureDemandLog blCIInsureDemandLog = new BLCIInsureDemandLog();
                	CIInsureDemandLogSchema ciInsureDemandLogSchema = new CIInsureDemandLogSchema(); 	
                	ciInsureDemandLogSchema.setIniTime(iniTime);
                	setCIInsureDemandLog(dbPool, blProposal, ciInsureDemandLogSchema, flag, errorMessage, demandNo);
                	blCIInsureDemandLog.setArr(ciInsureDemandLogSchema);
                        dbPool.beginTransaction();
                	blCIInsureDemandLog.save(dbPool);
                	dbPool.commitTransaction();
                }
                
            }catch(Exception ex){
                dbPool.rollbackTransaction();
                ex.printStackTrace();
            }finally{
                dbPool.close();
            }
            
            postMethod.releaseConnection();
        }
        if (buffer.length() < 1){
            throw new Exception("交强XXXXX平台返回数据失败！");
        }
        return buffer.toString();
    }
    
    public String biRequest(String requestXML, String strComCode, BLProposal blProposal) throws Exception {
        int intConnectTimeout=Integer.parseInt(AppConfig.get("sysconst.BI_DEFAULT_CONNECT_TIMEOUT"));
        int intReadTimeout= Integer.parseInt(AppConfig.get("sysconst.BI_DEFAULT_READ_TIMEOUT"));
        String strURL = AppConfig.get("sysconst.BI_INSURED_" + strComCode.substring(0, 2) +  "_URL");
        
        logger.info("=======URL=========="+strURL);
        
        HttpClient httpClient = new HttpClient();
        
        logger.info("come in");
        
        HttpConnectionManagerParams managerParams = httpClient.getHttpConnectionManager().getParams();
        
        managerParams.setConnectionTimeout(intConnectTimeout);
        
        managerParams.setSoTimeout(intReadTimeout);
        PostMethod postMethod = new PostMethod(strURL);
        
        String strResponse = null;
        StringBuffer buffer = new StringBuffer();
        String flag = "";
        
        long time1=System.currentTimeMillis();
        String iniTime =  new ChgDate().getCurrentTime("yyyy-MM-dd HH:mm:ss");
        
        try {
            flag = "";
            postMethod.setRequestEntity(new StringRequestEntity( requestXML, "text/xml", "GBK"));
            flag = "0";
            int statusCode = httpClient.executeMethod(postMethod);
            if (statusCode != HttpStatus.SC_OK) {
                flag = "4";
                throw new IllegalStateException("Method failed: "  + postMethod.getStatusLine());
            }
            BufferedReader reader = null;
            flag = "3";
            reader = new BufferedReader(new InputStreamReader(postMethod.getResponseBodyAsStream()));
            while ((strResponse = reader.readLine()) != null) {
                buffer.append(strResponse);
            }
        } catch (Exception ex) {
            if("3".equals(flag)){
                flag = "5";
            }else if("0".equals(flag)){
                flag = "6";
            }
            ex.printStackTrace();
            throw ex;
        } finally {
            DbPool dbPool = new DbPool();
            try{
                String errorMessage = "";
                String demandNo = "";
                String ResponseCode = "";
                String renewalFlag = "";
                
                if("3".equals(flag)){
                    try{
                        
                        InputStream in = new ByteArrayInputStream(buffer.toString().getBytes());
                        Document document = XMLUtils.parse(in);
                        Node node = null;
                        Node node2 = null;
                        node  = XMLUtils.getChildNodeByPath(document, "/Packet/Head");
                        node2 = XMLUtils.getChildNodeByPath(document, "/Packet/Body/BasePart");
                        if(node!=null){
                            
                            errorMessage = XMLUtils.getChildNodeValue(node, "ErrorMessage");
                            ResponseCode = XMLUtils.getChildNodeValue(node, "ResponseCode");
                        }
                        if(node2!=null){
                            
                            demandNo = XMLUtils.getChildNodeValue(node2, "QuerySequenceNo");
                            
                            renewalFlag = XMLUtils.getChildNodeValue(node2, "RenewalFlag");
                        }
                        if(ResponseCode!=null && !"1".equals(ResponseCode)){
                            flag = "1";
                        }
                        if(renewalFlag!=null && "1".equals(renewalFlag)){
                            flag = "2";
                        }
                    }catch(Exception e){
                        errorMessage = e.toString();
                    }
                }
                dbPool.open("platformNewDataSource");
                
                if(UtiPower.saveCILogSwitch(strComCode,flag)){
                BLCIInsureDemandLog blCIInsureDemandLog = new BLCIInsureDemandLog();
                CIInsureDemandLogSchema ciInsureDemandLogSchema = new CIInsureDemandLogSchema();
                if("3".equals(flag)){
                	long time2=System.currentTimeMillis();
                	double difference = time2 - time1;
                	String rule = SysConfig.getProperty("saveCILogSwitch_Second");
                	int second = Integer.parseInt(rule);
                	
                	if(difference/1000>second){
                		flag ="7";
                	}
                }
                ciInsureDemandLogSchema.setIniTime(iniTime);
                setCIInsureDemandLog(dbPool, blProposal, ciInsureDemandLogSchema, flag, errorMessage, demandNo);
                	blCIInsureDemandLog.setArr(ciInsureDemandLogSchema);
                        dbPool.beginTransaction();
                	blCIInsureDemandLog.save(dbPool);
                	dbPool.commitTransaction();                	
                }
                
            }catch(Exception ex){
                dbPool.rollbackTransaction();
                ex.printStackTrace();
            }finally{
                dbPool.close();
            }
            
            postMethod.releaseConnection();
        }
        if (buffer.length() < 1){
            throw new Exception("交强XXXXX平台返回数据失败！");
        }
        return buffer.toString();
    }
    protected void setCIInsureDemandLog(DbPool dbPool, BLProposal blProposal, CIInsureDemandLogSchema ciInsureDemandLogSchema,
            String flag, String errorMessage, String demandNo) 
            throws Exception {
        if(blProposal.getBLPrpTmain().getSize()>0&&blProposal.getBLPrpTitemCar().getSize()>0){
            ResultSet resultSet = dbPool.query("select SEQ_CILOG_LSNO.nextval as maxno from dual");
            String strMaxNo = "";
            if (resultSet.next()) {
                strMaxNo = resultSet.getString("maxno");
            }
            ciInsureDemandLogSchema.setItemNo(getYear()+getMonth()+getDay()+strMaxNo);
            ciInsureDemandLogSchema.setLicenseNo(blProposal.getBLPrpTitemCar().getArr(0).getLicenseNo());
            ciInsureDemandLogSchema.setFrameNo(blProposal.getBLPrpTitemCar().getArr(0).getFrameNo());
            ciInsureDemandLogSchema.setEngineNo(blProposal.getBLPrpTitemCar().getArr(0).getEngineNo());
            ciInsureDemandLogSchema.setComCode(blProposal.getBLPrpTmain().getArr(0).getComCode());
            ciInsureDemandLogSchema.setRiskCode(blProposal.getBLPrpTmain().getArr(0).getRiskCode());
            ciInsureDemandLogSchema.setOperaterCode(blProposal.getBLPrpTmain().getArr(0).getOperatorCode());
            ciInsureDemandLogSchema.setChannelType(blProposal.getBLPrpTmain().getArr(0).getChannelType());
            ciInsureDemandLogSchema.setOperateSite(blProposal.getBLPrpTmain().getArr(0).getOperateSite());
            ciInsureDemandLogSchema.setCreateDate(new ChgDate().getCurrentTime("yyyy-MM-dd HH:mm:ss"));
            ciInsureDemandLogSchema.setFlag(flag);
            ciInsureDemandLogSchema.setErrorMessage(errorMessage);
            ciInsureDemandLogSchema.setDemandNo(demandNo);
        }
    }
    /**
     * 获取单号年份
     * @return
     */
    public static String getYear() {
        return Calendar.getInstance().get(Calendar.YEAR)+"";
    }
    /**
     * 获取单号月份
     * @return
     */
    public static String getMonth() {
        String month = Calendar.getInstance().get(Calendar.MONTH)+1+"";
        if(month.length()==1){
            month = "0" + month;
        }
        return month;
    }
    /**
     * 获取单号日期
     * @return
     */
    public static String getDay() {
        String day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)+"";
        if(day.length()==1){
            day = "0" + day;
        }
        return day;
    }
    
    public String vinRequest(String requestXML) throws Exception {
    	int intConnectTimeout=Integer.parseInt(AppConfig.get("sysconst.BI_DEFAULT_CONNECT_TIMEOUT"));
    	int intReadTimeout= Integer.parseInt(AppConfig.get("sysconst.BI_DEFAULT_READ_TIMEOUT"));
    	String strURL = "";
    	 try {
    		 strURL = SysConfig.getProperty("SendCarVinJyUrl");
         } catch (Exception e) {
            e.printStackTrace();
         }
    	logger.info("=======URL=========="+strURL);
    	HttpClient httpClient = new HttpClient();
    	logger.info("come in");
		  HttpConnectionManagerParams managerParams = httpClient.getHttpConnectionManager().getParams();
		  
		  managerParams.setConnectionTimeout(intConnectTimeout);
		  
		  managerParams.setSoTimeout(intReadTimeout);
		  PostMethod postMethod = new PostMethod(strURL);
		  
		  String strResponse = null;
		  StringBuffer buffer = new StringBuffer();

        try {
		   postMethod.setRequestEntity(new StringRequestEntity( requestXML, "text/xml", "GBK"));
		    
		   int statusCode = httpClient.executeMethod(postMethod);
		   if (statusCode != HttpStatus.SC_OK) {
		    throw new IllegalStateException("Method failed: "  + postMethod.getStatusLine());
		   }
		   
		   BufferedReader reader = null;
           reader = new BufferedReader(new InputStreamReader(postMethod.getResponseBodyAsStream()));
           while ((strResponse = reader.readLine()) != null) {
               buffer.append(strResponse);
           }
		   
        } catch (Exception ex) {
        	ex.printStackTrace();
            throw ex;
        } finally {
        	
        	postMethod.releaseConnection();
        }
        if (buffer.length() < 1){
  			throw new Exception("精友VIN码平台返回数据失败！");
        }
        return buffer.toString();
    }
}
