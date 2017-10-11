package com.sp.indiv.ci.interf;

import com.sp.indiv.ci.interf.*;
import com.sp.utility.database.*;
import com.sp.utility.string.ChgDate;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.phonesale.common.ProposalWriteOffTask;
import com.sp.prpall.pubfun.BancassuranceGHDG;
import com.sp.prpall.pubfun.LogFile;
import com.sp.sysframework.common.datatype.DateTime;
import com.sp.sysframework.reference.AppConfig;
import java.io.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * ��ʱ����ǿ��ƽ̨�������. 1.�ط�����ȷ����Ϣ 2.�ط�XXע����Ϣ 3.�ظ�����XXȷ����Ϣ 4.�ط�XX��XXXXX��Ϣ 5.ɾ��������������ѯ��
 * 6.ɾ��������XX����ѯ��. 7.��������ǿ����־����
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author �̿�
 * @version 1.0
 */
public class CITimeExecute 
{
	static Log logger = LogFactory.getLog(CITimeExecute.class.getName());
	public CITimeExecute() 
	{
	}

	public static void main(String[] args) throws Exception 
	{
		
		if (args.length < 1) 
		{
			showUsage();
			System.exit(0);
		}
		SysConst.init(args[0], false);
		File file = new File("");
		AppConfig.reconfigure(file.getAbsolutePath());
		file = null;
		
		String strUrl 			= "";
		String strUser 			= "";
		String strPwd 			= "";
		String strExceptionNo 	= "";
		
		strUrl 	= SysConst.getProperty("CIIDB_URL");
		strUser = SysConst.getProperty("CIIDB_USER");
		strPwd 	= SysConst.getProperty("CIIDB_PASSWORD");
		
		
		
		
		
		logger.info("��ʱ�������strURL:=========" + strUrl);
		logger.info("��ʱ�������strUser:=========" + strUser);
		logger.info("��ʱ�������strPwd:=========" + strPwd);
		
		
		DbPool dbpool1 = new DbPool();
		DbPool dbpool2 = new DbPool();
		DbPool dbpool3 = new DbPool();
		DbPool dbpool4 = new DbPool();
		DbPool dbpool5 = new DbPool();
		DbPool dbpool6 = new DbPool();
		
		
		logger.info("====��ʼ��������ǿ����־����====");
		
		try 
		{
			dbpool1.openOra(strUrl, strUser, strPwd);
			strExceptionNo = "��������ǿ����־��������쳣���������Ա��ϵ��";
			RepeatSendCISignNo repeatSendCISignNo = new RepeatSendCISignNo();
			repeatSendCISignNo.repeatSendCISignNo(dbpool1, "rownum < 200");
			








			
		} 
		catch (Exception ex) 
		{
			
			logger.error("===========��ʱ�����������" + strExceptionNo);
			
			
			
		}
		finally
		{
			dbpool1.close();
		}
		
		logger.info("====������������ǿ����־����====");
		
		
		
        
        
		/*
		try 
		{
			dbpool2.openOra(strUrl, strUser, strPwd);
			strExceptionNo = "�ظ�����XXȷ�ϳ����쳣���������Ա��ϵ��";
			RepeatSendPolicyValid repeatSendPolicyValid = new RepeatSendPolicyValid();
			repeatSendPolicyValid.repeatPolicyValid(dbpool2, "", "", "");
		} 
		catch (Exception ex) 
		{
			
			
		}
		finally
		{
			dbpool2.close();
		}
		
		
		try 
		{
			dbpool3.openOra(strUrl, strUser, strPwd);
			strExceptionNo = "�ط���XXע�������쳣���������Ա��ϵ��";
			RepeatSendPolicyCancel repeatSendPolicyCancel = new RepeatSendPolicyCancel();
			repeatSendPolicyCancel.repeatSendPolicyCancel(dbpool3, "", "", "");	
		} 
		catch (Exception ex) 
		{
			
			
		}
		finally
		{
			dbpool3.close();
		}
		
		
		try 
		{
			dbpool4.openOra(strUrl, strUser, strPwd);
			strExceptionNo = "�ط�XX��XXXXX�����쳣���������Ա��ϵ��";
			RepeatSendPolicyWithDraw repeatSendPolicyWithDraw = new RepeatSendPolicyWithDraw();
			repeatSendPolicyWithDraw.repeatSendPolicyWithDraw(dbpool4, "", "","");
		} 
		catch (Exception ex) 
		{
			
			
		}
		finally
		{
			dbpool4.close();
		}
		
		
		try 
		{
			dbpool5.openOra(strUrl, strUser, strPwd);
			strExceptionNo = "�ط�XX��XXXXX�����쳣���������Ա��ϵ��";
			RepeatSendEndorseValid repeatSendEndorseValid = new RepeatSendEndorseValid();
			repeatSendEndorseValid.repeatSendEndorseValid(dbpool5, "", "", "");
		}
		catch (Exception ex) 
		{
			
			
		}
		finally
		{
			dbpool5.close();
		}
        */
	}

	public void execute() throws Exception {
		
		
		logger.info("====��ʼ��������ǿ����־����====");
		
	    DbPool dbPool = new DbPool();
        try {
        	dbPool.open(SysConfig.CONST_DDCCDATASOURCE);
			RepeatSendCISignNo repeatSendCISignNo = new RepeatSendCISignNo();
			repeatSendCISignNo.repeatSendCISignNo(dbPool, "rownum < 200");
        }
        catch (Exception e)
        {
        	
        	throw e;
        }
        finally {
        	dbPool.close();
        }
        
        logger.info("====������������ǿ����־����====");
        
	}
	/**
	 * ��ʾ�÷�
	 */
	private static void showUsage() {
		
		logger.info("Usage: java CITimeExecute {conf}");
		logger.info("  conf: CIIConstConfig.xml file");
		
	}
}