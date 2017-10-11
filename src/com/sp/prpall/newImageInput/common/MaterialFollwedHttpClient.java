package com.sp.prpall.newImageInput.common;

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class MaterialFollwedHttpClient {
	private static HttpClient httpClient = new HttpClient();
	static {
		
		setConnectionTimeout(30000);
		setSoTimeout(120000);
		httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,
				"utf-8");
	}

	/**
	 * 设置连接超时时间
	 * 
	 * @param connectionTimeout
	 *          连接超时时间(单位毫秒)
	 */
	public static void setConnectionTimeout(int connectionTimeout) {
		HttpConnectionManagerParams managerParams = httpClient
				.getHttpConnectionManager().getParams();
		managerParams.setConnectionTimeout(connectionTimeout);
	}

	/**
	 * 设置读数据超时时间
	 * 
	 * @param soTimeout
	 *          读数据超时时间(单位毫秒)
	 */
	public static void setSoTimeout(int soTimeout) {
		HttpConnectionManagerParams managerParams = httpClient
				.getHttpConnectionManager().getParams();
		managerParams.setSoTimeout(soTimeout);
	}

	/**
	 * 设置代理服务器
	 * 
	 * @param ip
	 *          IP地址
	 * @param port
	 *          端口号
	 */
	public static void setProxy(String ip, int port) {
		HostConfiguration hcf = new HostConfiguration();
		hcf.setProxy(ip, port);
		httpClient.setHostConfiguration(hcf);
	}

	public static String getTemplateInformationByPost(String fileTransServiceUrl,String docType) {
		PostMethod postMethod = new PostMethod(fileTransServiceUrl);
		try {
			postMethod.setQueryString(new NameValuePair[] {
					new NameValuePair("docType", docType)});
			int statusCode = httpClient.executeMethod(postMethod);
			if (statusCode != HttpStatus.SC_OK) {
				throw new IllegalStateException("Method failed: "
						+ postMethod.getStatusLine());
			}
		/*	InputStream in=postMethod.getResponseBodyAsStream();
			int res=0;
			
			String s="";
			byte[] temp=new byte[1024];
			while((res=in.read(temp))!=-1){	
				
				s=s+new String(temp);
			}
			return s;
			
*/			
			return postMethod.getResponseBodyAsString();
		} catch (Exception ex) {
			throw new IllegalStateException(ex.toString());
		} finally {
			
			postMethod.releaseConnection();
		}
	}
	
	public static String getTemplateInformationByPost(String fileTransServiceUrl,String docType,String clientType) {
		PostMethod postMethod = new PostMethod(fileTransServiceUrl);
		try {









			postMethod.setQueryString(new NameValuePair[] {
					new NameValuePair("docType", docType),
					new NameValuePair("clientType", clientType) });
			int statusCode = httpClient.executeMethod(postMethod);
			if (statusCode != HttpStatus.SC_OK) {
				throw new IllegalStateException("Method failed: "
						+ postMethod.getStatusLine());
			}
		/*	InputStream in=postMethod.getResponseBodyAsStream();
			int res=0;
			
			String s="";
			byte[] temp=new byte[1024];
			while((res=in.read(temp))!=-1){	
				
				s=s+new String(temp);
			}
			return s;
			
*/			
			return postMethod.getResponseBodyAsString();
		} catch (Exception ex) {
			throw new IllegalStateException(ex.toString());
		} finally {
			
			postMethod.releaseConnection();
		}
	}
	
	public static String getTemplateInformationByGet(String fileTransServiceUrl,String docType) {
		
		GetMethod getMethod =new GetMethod(fileTransServiceUrl);
		try {
			getMethod.setQueryString(new NameValuePair[] {
					new NameValuePair("docType", docType)});
			int statusCode = httpClient.executeMethod(getMethod);
			if (statusCode != HttpStatus.SC_OK) {
				throw new IllegalStateException("Method failed: "
						+ getMethod.getStatusLine());
			}
			return getMethod.getResponseBodyAsString();
		} catch (Exception ex) {
			throw new IllegalStateException(ex.toString());
		} finally {
			
			getMethod.releaseConnection();
		}
	}
	public static String getTemplateInfo(String fileTransServiceUrl,String fileTrans,String flowId,String docType,String templateIndex) {
		PostMethod postMethod = new PostMethod(fileTransServiceUrl);
		try {
			postMethod.setQueryString(new NameValuePair[] {
					new NameValuePair("fileTrans", fileTrans),
					new NameValuePair("flowId", flowId),
					new NameValuePair("docType", docType),
					new NameValuePair("templateIndex", templateIndex)});
			int statusCode = httpClient.executeMethod(postMethod);
			if (statusCode != HttpStatus.SC_OK) {
				throw new IllegalStateException("Method failed: "
						+ postMethod.getStatusLine());
			}
			String str=postMethod.getResponseBodyAsString();
			return str;
		} catch (Exception ex) {
			throw new IllegalStateException(ex.toString());
		} finally {
			
			postMethod.releaseConnection();
		}
	}
	
	public static String showImage(String fileTransServiceUrl,String fileTrans,String flowId,String docType) {
		PostMethod postMethod = new PostMethod(fileTransServiceUrl);
		try {
			postMethod.setQueryString(new NameValuePair[] {
					new NameValuePair("fileTrans", fileTrans),
					new NameValuePair("flowId", flowId),
					new NameValuePair("docType", docType)});
			int statusCode = httpClient.executeMethod(postMethod);
			if (statusCode != HttpStatus.SC_OK) {
				throw new IllegalStateException("Method failed: "
						+ postMethod.getStatusLine());
			}
			String str=postMethod.getResponseBodyAsString();
			
			return str;
		} catch (Exception ex) {
			throw new IllegalStateException(ex.toString());
		} finally {
			
			postMethod.releaseConnection();
		}
	}
   public static void main(String[] args){






       String a= "http:
       String d = "0101";
       String c = "";
       getTemplateInformationByPost(a,d,c);
   }
}
