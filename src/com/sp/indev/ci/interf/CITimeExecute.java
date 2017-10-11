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
 * 定时出发强三平台相关内容. 1.重发批单确认信息 2.重发XX注销信息 3.重复发送XX确认信息 4.重发XX退XXXXX信息 5.删除废弃的批单查询码
 * 6.删除废弃的XX单查询码. 7.批量发送强三标志号码
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
 * @author 程凯
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
		
		
		
		
		
		logger.info("定时处理程序strURL:=========" + strUrl);
		logger.info("定时处理程序strUser:=========" + strUser);
		logger.info("定时处理程序strPwd:=========" + strPwd);
		
		
		DbPool dbpool1 = new DbPool();
		DbPool dbpool2 = new DbPool();
		DbPool dbpool3 = new DbPool();
		DbPool dbpool4 = new DbPool();
		DbPool dbpool5 = new DbPool();
		DbPool dbpool6 = new DbPool();
		
		
		logger.info("====开始批量发送强三标志号码====");
		
		try 
		{
			dbpool1.openOra(strUrl, strUser, strPwd);
			strExceptionNo = "批量发送强三标志号码出现异常。请与管理员联系！";
			RepeatSendCISignNo repeatSendCISignNo = new RepeatSendCISignNo();
			repeatSendCISignNo.repeatSendCISignNo(dbpool1, "rownum < 200");
			








			
		} 
		catch (Exception ex) 
		{
			
			logger.error("===========定时程序操作错误：" + strExceptionNo);
			
			
			
		}
		finally
		{
			dbpool1.close();
		}
		
		logger.info("====结束批量发送强三标志号码====");
		
		
		
        
        
		/*
		try 
		{
			dbpool2.openOra(strUrl, strUser, strPwd);
			strExceptionNo = "重复发送XX确认出现异常。请与管理员联系！";
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
			strExceptionNo = "重发送XX注销出现异常。请与管理员联系！";
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
			strExceptionNo = "重发XX退XXXXX出现异常。请与管理员联系！";
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
			strExceptionNo = "重发XX退XXXXX出现异常。请与管理员联系！";
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
		
		
		logger.info("====开始批量发送强三标志号码====");
		
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
        
        logger.info("====结束批量发送强三标志号码====");
        
	}
	/**
	 * 显示用法
	 */
	private static void showUsage() {
		
		logger.info("Usage: java CITimeExecute {conf}");
		logger.info("  conf: CIIConstConfig.xml file");
		
	}
}