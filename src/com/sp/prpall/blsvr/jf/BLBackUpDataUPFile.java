package com.sp.prpall.blsvr.jf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.SocketException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import com.sp.prpall.blsvr.cb.BLPolicy;
import com.sp.prpall.dbsvr.jf.DBPrpCBackUpDataUP;
import com.sp.prpall.jfcd.cb.CFeedBack;
import com.sp.prpall.schema.PrpCBackUpDataUPSchema;
import com.sp.sysframework.reference.AppConfig;
import com.sp.thirdparty.claim.util.DateTime;
import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;

/**
 * �������ݿ�����ֱ��Ĺ�����
 * <p>
 * Copyright: Copyright (c) 2014
 * </p>
 * <p>
 * @createdate 2014-08-25
 * </p>
 * 
 * @author BLGenerator
 * @version 1.0
 */
public class BLBackUpDataUPFile {

	private int n = 1;
	private final Log logger = LogFactory.getLog(getClass());
	private SimpleDateFormat smf = new SimpleDateFormat("yyyy-MM-dd");
	private Date date = new Date();

	/**
	 * ���л��ļ�
	 * 
	 * @param policyNoXX��
	 * @param url ���л��ļ����ɵĵ�ַ
	 * @return ��
	 * @throws Exception
	 */
	public void serial(String policyNo, String url) {
		DBPrpCBackUpDataUP backUpDataUP = new DBPrpCBackUpDataUP();
		BLPolicy blPolicy = new BLPolicy();
		Map map = null;
		DbPool dbpool = new DbPool();
		
		
		ObjectOutputStream oos = null;
		try {
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			map = blPolicy.getDataForXuLieHua(dbpool, policyNo);
			oos = new ObjectOutputStream(new FileOutputStream(url + "/"
					+ policyNo + ".txt"));
			
			oos.writeObject(map);
		} catch (FileNotFoundException e) {
			
			CFeedBack cFeedBack = new CFeedBack();
			String strErrorMesg = cFeedBack.getErrorMessageDetails(e,false);
			logger.info("XX��Ϊ" + policyNo + "���������л�ʱ�ļ�δ�ҵ�"+"����Ϊ��"+strErrorMesg);
			backUpDataUP.callErrorLogProcedureNew("BLBackUpDataUPFile",
					smf.format(date) + "XX��Ϊ" + policyNo + "���������л�ʧ��", "S");
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (UserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			CFeedBack cFeedBack = new CFeedBack();
			String strErrorMesg = cFeedBack.getErrorMessageDetails(e,false);
			logger.info("XX��Ϊ" + policyNo + "���������л�ʱ������IO�쳣"+"����Ϊ��"+strErrorMesg);
			backUpDataUP.callErrorLogProcedureNew("BLBackUpDataUPFile",
					smf.format(date) + "XX��Ϊ" + policyNo + "���������л�ʧ��", "S");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				dbpool.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			if (oos != null) {
				try {
					oos.flush();
					oos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * �����л��ļ�
	 * @param dir �����л��ļ��ĵ�ַ
	 * @return ��
	 * @throws Exception
	 */
	public void unSerial(String dir) {
		try {
			String dire = dir.substring(0, dir.length() - 1);
			File fileFolders = new File(dire);
			File[] srcfFiles = fileFolders.listFiles();
			for (int i = 0; i < srcfFiles.length; i++) {
				
				
				ObjectInputStream ois = new ObjectInputStream(
						new FileInputStream(srcfFiles[i]));
				BLPolicy blPolicy = new BLPolicy();
				blPolicy = (BLPolicy) ois.readObject();
				
				

			}
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	/**
	 * ѹ���ļ�
	 * 
	 * @param sourceURL��ѹ���ļ��ĵ�ַ
	 * @param policyNo XX��
	 * @return ��
	 * @throws Exception
	 */

	public void zip(String sourceURL, String policyNo) {
		DBPrpCBackUpDataUP backUpDataUP = new DBPrpCBackUpDataUP();
		try {
			long t1 = System.currentTimeMillis();
			
			File sourceFile = new File(sourceURL + "/" + policyNo + ".txt");
			File zipFile = new File(sourceURL + "/" + policyNo + ".rar");
			
			InputStream input = new FileInputStream(sourceFile);
			
			ZipOutputStream zipOut = null;
			
			zipOut = new ZipOutputStream(new FileOutputStream(zipFile));
			zipOut.putNextEntry(new ZipEntry(sourceFile.getName()));
			int temp = 0;
			while ((temp = input.read()) != -1) {
				zipOut.write(temp);
			}
			input.close();
			zipOut.close();
		} catch (Exception e) {
			CFeedBack cFeedBack = new CFeedBack();
			String strErrorMesg = cFeedBack.getErrorMessageDetails(e,
					false);
			logger.info("XX��Ϊ" + policyNo + "��������ѹ��ʱ������IO�쳣"+"�쳣Ϊ:"+strErrorMesg);
			backUpDataUP.callErrorLogProcedureNew("BLBackUpDateUPFile",
					smf.format(date) + "XX��Ϊ" + policyNo + "������ѹ��ʧ��", "S");
			e.printStackTrace();
		}

	}
	/**
	 * �ļ����ϴ�
	 * 
	 * @param Map �����key��XX��  value��ѹ���ļ��ĵ�ַ
	 * @return ��
	 * @throws UserException 
	 * @throws Exception
	 */
	public void uploadFTP(Map map) throws UserException {
		String ip =AppConfig.get("sysconst.DataBackUp_IP").toString();
		int port =Integer.parseInt(AppConfig.get("sysconst.DataBackUp_PORT"));
		String username = AppConfig.get("sysconst.DataBackUp_USERNAME");
		String password = AppConfig.get("sysconst.DataBackUp_PASSWD");
		this.uploadFile(ip, port, username, password, map);
	}
	
	/**
	 * Description: ��FTP�������ϴ��ļ�
	 * 
	 * @Version 1.0
	 * @param url
	 *            FTP������hostname
	 * @param port
	 *            FTP�������˿�
	 * @param username
	 *            FTP��¼�˺�
	 * @param password
	 *            FTP��¼����
	 * @param Map ��������ļ�·����XX��        
	 * @return �ɹ�����true�����򷵻�false *
	 */
	public boolean uploadFile(String url,
			int port,
			String username, 
			String password, 
			Map map
	) {

		BLPrpCBackUpDataUP blbackUpDataUP = new BLPrpCBackUpDataUP();
		SimpleDateFormat smf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		DbPool dbpool = new DbPool();
		boolean success = false;
		FTPClient ftp = new FTPClient();
		ftp.setControlEncoding("GBK");
		int reply;
		try {
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			dbpool.beginTransaction();
			ftp.connect(url, port);
			
			ftp.login(username, password);
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return true;
			}
			ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
			Iterator iterator = map.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry entry = (Map.Entry) iterator.next();
				String targetURL = (String) entry.getValue();
				String policyNo = (String) entry.getKey();
				String arrURL[] = targetURL.split("-");
				String distanceURL = arrURL[0];
				String localURL = arrURL[1].replace('\\','/');
				String paths[] = distanceURL.split("/");
				String filename = policyNo + ".rar";
				String urlname =localURL + "/" + policyNo + ".rar";
				FileInputStream input = new FileInputStream(new File(urlname));
				for (int a = 0; a <= paths.length - 1; a++) {
					Boolean f = ftp.makeDirectory(paths[a]);
					String newFile = ftp.printWorkingDirectory();
					boolean b = ftp.changeWorkingDirectory(paths[a]);
				}

				ftp.storeFile(filename, input);
				ftp.changeWorkingDirectory("/weblogic");
				input.close();
				PrpCBackUpDataUPSchema schema = new PrpCBackUpDataUPSchema();
				blbackUpDataUP.getData(dbpool, policyNo);
				schema = blbackUpDataUP.getArr(0);
				schema.setCreateDate(DateTime.current().toString().substring(0,19));
				schema.setStatus("1");
				schema.setFlag(distanceURL);
				blbackUpDataUP.setArr(schema);
			    blbackUpDataUP.upDate(dbpool);
			}
			dbpool.commitTransaction();
			ftp.logout();
			success = true;
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) { 
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			try {
				dbpool.rollbackTransaction();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				dbpool.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
				}
			}
		}
		return success;
	}
	/**
	 * ����ļ�һ��ɾ��
	 * 
	 * @param adders�����ļ�������ļ��е�����
	 * @return ��
	 * @throws Exception
	 */
	public void del(String adders) {
		File file = new File(adders);
		if (file.isDirectory() == true) { 
			if (file.listFiles().length == 0) { 
				file.delete();
			} else {
				int files = file.listFiles().length; 
				File[] delfile = file.listFiles(); 
				for (int i = 0; i < files; i++) {
					if (delfile[i].isDirectory()) {
						del(delfile[i].getAbsolutePath()); 
					}
					delfile[i].delete();
				}
				file.delete();
			}
		}
	}
}
