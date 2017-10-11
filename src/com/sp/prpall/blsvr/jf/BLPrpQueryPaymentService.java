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
	 * �ո���װ���ࣺcom.sp.payment.schema.PrpJplanFeeSchema
	 */ 
	public static final String PrpJplanFee= "com.sp.payment.schema.PrpJplanFeeSchema";
	/**
	 * �ո���װ���ࣺcom.sp.payment.schema.PrpJpayRefRecSchema
	 */ 
	public static final String PrpJpayRefRec="com.sp.payment.schema.PrpJpayRefRecSchema";
	/**
	 * �ո���װ���ࣺcom.sp.payment.schema.PrpJPayBankSchema
	 */ 
	public static final String PrpJPayBank="com.sp.payment.schema.PrpJPayBankSchema";
	/**
	 * �ո���װ���ࣺcom.sp.payment.schema.PrpJplanFeePreSchema
	 */ 
	public static final String PrpJplanFeePre="com.sp.payment.schema.PrpJplanFeePreSchema";
	/**
	 * �ո���װ���ࣺcom.sp.payment.schema.PrpJplanCommissionSchema
	 */ 
	public static final String PrpJplanCommission="com.sp.payment.schema.PrpJplanCommissionSchema";
	
	/**
	 * �ո���װ���ࣺcom.sp.payment.schema.PrpJrequestFundSchema
	 */
	public static final String PrpJrequestFund="com.sp.payment.schema.PrpJrequestFundSchema";
	/**
     * �ո���װ���ࣺcom.sp.payment.schema.PrpJpoaInfoSchema
     */ 
	public static final String PrpJpoaInfo = "com.sp.payment.schema.PrpJpoaInfoSchema";
	/**
     * �ո���װ���ࣺcom.sp.payment.schema.PrpJvisaExportMidSchema
     */ 
	public static final String PrpJvisaExportMid = "com.sp.payment.schema.PrpJvisaExportMidSchema";
	/**
	 * @description:���ݶ������ͺͲ�ѯ�������ո�����
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
            	logger.info("�����������������ת���쳣����");
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
	 * @description:�ո���ֿ�����Դ�л������д�����Դ����
	 * @param dbPool
	 * @param intFlag 1�������ѯ������2�������ѯ
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
	 * @description:�����ո���ֿ⿪�����ã��ж��Ƿ��ѯ����� 20140916
	 * @return 1����ѯ����⿪�ش򿪣���֮ �ر�
	 * @throws UserException
	 * @throws Exception
	 * @author Administrator WangGhengGui
	 */
	public static String queryMirrorSwitch() throws UserException,Exception{
		String strSwitch="0";
        try {
	        strSwitch = SysConfig.getProperty("PAYMENT_SWITCH_prpall");
        } catch (Exception e) {
        	String strMsg = "��ѯ�ո�ϵͳ��ֿ⿪�أ�PAYMENT_SWITCH���쳣";
        	logger.info(strMsg);
        	e.printStackTrace();
        	throw new Exception(strMsg);
        }
        return strSwitch;
	}
	
	/**
	 * @description:�����ո�ת���ӿڷ���
	 * @param list XX����
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
            	logger.info("�����������������ת���쳣����");
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