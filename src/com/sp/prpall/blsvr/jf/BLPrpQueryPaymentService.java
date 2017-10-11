package com.sp.prpall.blsvr.jf;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;

/**
 */
public class BLPrpQueryPaymentService {
	static Log logger = LogFactory.getLog(BLPrpQueryPaymentService.class);
	
	/**
	 * 收付封装的类：com.sp.payment.schema.PrpJplanFeeSchema
	 */ 
	public static final String PrpJplanFee= "com.sp.payment.schema.PrpJplanFeeSchema";
	/**
	 * 收付封装的类：com.sp.payment.schema.PrpJpayRefRecSchema
	 */ 
	public static final String PrpJpayRefRec="com.sp.payment.schema.PrpJpayRefRecSchema";
	/**
	 * 收付封装的类：com.sp.payment.schema.PrpJPayBankSchema
	 */ 
	public static final String PrpJPayBank="com.sp.payment.schema.PrpJPayBankSchema";
	/**
	 * 收付封装的类：com.sp.payment.schema.PrpJplanFeePreSchema
	 */ 
	public static final String PrpJplanFeePre="com.sp.payment.schema.PrpJplanFeePreSchema";
	/**
	 * 收付封装的类：com.sp.payment.schema.PrpJplanCommissionSchema
	 */ 
	public static final String PrpJplanCommission="com.sp.payment.schema.PrpJplanCommissionSchema";
	
	/**
	 * 收付封装的类：com.sp.payment.schema.PrpJrequestFundSchema
	 */
	public static final String PrpJrequestFund="com.sp.payment.schema.PrpJrequestFundSchema";
	/**
     * 收付封装的类：com.sp.payment.schema.PrpJpoaInfoSchema
     */ 
	public static final String PrpJpoaInfo = "com.sp.payment.schema.PrpJpoaInfoSchema";
	/**
     * 收付封装的类：com.sp.payment.schema.PrpJvisaExportMidSchema
     */ 
	public static final String PrpJvisaExportMid = "com.sp.payment.schema.PrpJvisaExportMidSchema";
	/**
	 * @description:根据对象类型和查询语句调用收付服务
	 * @param clazz
	 * @param sql
	 * @return
	 * @author Administrator WangGhengGui
	 */
	public static <T> Vector<T> queryByServlet(String className , String sql){
		Vector<T> vector = null;
		InputStream inputStream = null;
		OutputStream outputStream = null;
		OutputStreamWriter writer = null;
	   try {
			URL httpUrl = new URL("http:
	        HttpURLConnection connection=(HttpURLConnection)httpUrl.openConnection();
	        connection.setUseCaches(false);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setAllowUserInteraction(true);
	        connection.connect();
	        outputStream=connection.getOutputStream();
	        writer=new OutputStreamWriter(outputStream);
	        writer.write("");
	        writer.flush();
	        writer.close();
	        inputStream = connection.getInputStream();
	        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
	        Object obj=objectInputStream.readObject();
	        try {
	            vector = (Vector<T>) obj;
            } catch (Exception e) {
            	logger.info("接收流对象进行类型转换异常！！");
            	e.printStackTrace();
            }
        } catch (MalformedURLException e) {
	        e.printStackTrace();
        } catch (IOException e) {
	        e.printStackTrace();
        } catch (ClassNotFoundException e) {
	        e.printStackTrace();
        } catch (UserException e) {
	        e.printStackTrace();
        } catch (Exception e){
        	e.printStackTrace();
        }
        return vector;
	}
	/**
	 * @description:收付拆分库数据源切换，进行打开数据源操作
	 * @param dbPool
	 * @param intFlag 1：联表查询操作，2：单表查询
	 * @author Administrator WangGhengGui
	 * @throws Exception 
	 * @throws UserException 
	 */
	public static void querySwitch(DbPool dbPool,int intFlag) throws UserException, Exception{
		if (dbPool==null) {
	        return;
        }
		String paymentSplitSwitch = queryMirrorSwitch();
		if (!"1".equals(paymentSplitSwitch)) {
	        return;
        }
		if (intFlag == 1) {
			

			dbPool.open(SysConfig.getProperty("JX_DATASOURCE_QUERY"));
			
        }else if (intFlag == 2) {
	        dbPool.open(SysConfig.CONST_PAYMENTDATASOURCE);
        }
	}
	/**
	 * @description:根据收付拆分库开关配置，判断是否查询镜像库 20140916
	 * @return 1：查询镜像库开关打开，反之 关闭
	 * @throws UserException
	 * @throws Exception
	 * @author Administrator WangGhengGui
	 */
	public static String queryMirrorSwitch() throws UserException,Exception{
		String strSwitch="0";
        try {
	        strSwitch = SysConfig.getProperty("PAYMENT_SWITCH_prpall");
        } catch (Exception e) {
        	String strMsg = "查询收付系统拆分库开关（PAYMENT_SWITCH）异常";
        	logger.info(strMsg);
        	e.printStackTrace();
        	throw new Exception(strMsg);
        }
        return strSwitch;
	}
	
	/**
	 * @description:调用收付转数接口服务
	 * @param list XX集合
	 * @return
	 * @author Administrator wangjun
	 */
	public static  String queryByServletList(List  list){
		String str = null;
		InputStream inputStream = null;
		OutputStream outputStream = null;
		OutputStreamWriter writer = null;
	   try {
			URL httpUrl = new URL("http:
			   HttpURLConnection connection=(HttpURLConnection)httpUrl.openConnection();
		        connection.setUseCaches(false);
	            connection.setDoOutput(true);
	            connection.setDoInput(true);
	            connection.setRequestMethod("POST");
	            connection.setAllowUserInteraction(true);
		        connection.connect();
		        outputStream=connection.getOutputStream();
		        ObjectOutputStream objOutputStream = new ObjectOutputStream(outputStream);
				objOutputStream.writeObject(list);
				objOutputStream.flush();
				objOutputStream.close();
				inputStream = connection.getInputStream();
		        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
		        Object obj=objectInputStream.readObject();
		        objectInputStream.close();
	        try {
	        	str = (String) obj;
            } catch (Exception e) {
            	logger.info("接收流对象进行类型转换异常！！");
            	e.printStackTrace();
            }
        } catch (MalformedURLException e) {
	        e.printStackTrace();
        } catch (IOException e) {
	        e.printStackTrace();
        } catch (ClassNotFoundException e) {
	        e.printStackTrace();
        } catch (UserException e) {
	        e.printStackTrace();
        } catch (Exception e){
        	e.printStackTrace();
        }
        return str;
	}
}