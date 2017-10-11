package com.sp.client;

import java.net.*;
import java.io.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class testParser {
	static String loggingName = testParser.class.getName();
	public static void main(String args[]) {
		Log logger = LogFactory.getLog(loggingName);
		FileOutputStream fos = null;
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		HttpURLConnection httpUrl = null;
		String stFileName = "D:\\aa.XML";
		byte[] buf = new byte[8096];
		int size = 0;
		try {
			URL testUrl = new URL(
					"http:
			httpUrl = (HttpURLConnection) testUrl.openConnection();
			httpUrl.setDoOutput(true);

			httpUrl.setRequestProperty("Content-Type", "text/xml");
			
			httpUrl.connect();
			
			
			fis = new FileInputStream(stFileName);

			OutputStreamWriter out = new OutputStreamWriter(httpUrl
					.getOutputStream(), "GBK");

			while ((size = fis.read(buf)) != -1) {
				out.write((new String(buf)).trim());
			}
			out.flush();
			fis.close();
			
			InputStream in = httpUrl.getInputStream();
			String line = null;
			StringBuffer content = new StringBuffer();
			byte[] bufread = new byte[8096];
			while ((size = in.read(bufread)) != -1 )
			{
				
				
				logger.info((new String(bufread)).trim());
				
			}
			out.close();
			in.close();
			httpUrl.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
