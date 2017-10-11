package com.sp.indiv.ci.interf;

import java.util.Iterator;
import java.util.Vector;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import com.sp.indiv.ci.blsvr.BLCIInsureValid;
import com.sp.indiv.ci.dbsvr.DBCIInsureValid;
import com.sp.indiv.ci.schema.CIInsureValidSchema;
import com.sp.prpall.blsvr.cb.BLPolicy;
import com.sp.sysframework.common.util.StringUtils;
import com.sp.sysframework.reference.AppConfig;
import com.sp.utility.database.DbPool;
import com.sp.prpall.blsvr.cb.BLPrpCmain;
import com.sp.prpall.schema.PrpCmainSchema;
import com.sp.sysframework.reference.DBManager;
import com.sp.utiall.blsvr.BLPrpDuser;
import com.sp.utility.SysConfig;
import com.sp.utility.error.UserException;
import com.sp.visa.bl.action.domain.BLVsMarkAction;
import com.sp.visa.bl.facade.BLVsMarkFacade;
import com.sp.visa.blsvr.BLVsMarks;
import com.sp.visa.dto.domain.VsMarkDto;
import com.sp.visa.schema.VsMarksSchema;
import com.sp.utility.SysConst;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 直接XX
 * 
 * @author 
 */
public class ImmCB{

	Log logger = LogFactory.getLog(getClass());
	/**
	 * @throws Exception
	 * 
	 */
	public static void main(String[] args) throws Exception {

	}
	
	
	/**
	 * @XX号直接XX
	 * 
	 */
	public void sendPolicy(DbPool dbPool, String policyNo)
	     throws Exception
	{	
		 String request = "";
		 String response  = "";
		 String comCode = "";
		 BLPolicy blPolicy =  null;
		 PolicyImmcbEncoder policyImmcbEncoder = new PolicyImmcbEncoder();
		 PolicyImmcbDecoder policyImmcbDecoder = new PolicyImmcbDecoder();
		 
		 try 
		 {
			 blPolicy 	= getBLPolicyDate(dbPool, policyNo);
			 comCode 	= blPolicy.getBLPrpCmain().getArr(0).getComCode();
			 request 	= policyImmcbEncoder.encode(dbPool, blPolicy);
			
			 logger.info("直接XX发给平台的串："+request);
			 
			 
			 response 	= EbaoProxy.getInstance().request(request, comCode);
			 response 	= StringUtils.replace(response, "GBK", "GB2312");	
			 
			 logger.info("直接XX平台返回的串："+response);
			 
			 policyImmcbDecoder.decode(dbPool, blPolicy, response);
		 } 
		 catch (Exception exception) 
		 {
			
			logger.error("单独直接XX失败，XX号：" + policyNo);
			
			 exception.printStackTrace();
			 throw exception;
		 }
	}

	/**
	 * @日期查询
	 * 
	 */	
  public void sendDate(DbPool dbPool, 
		               String startDate, 
		               String endDate,
		               String comCode)
       throws Exception
  {
	    String sqlMain = "";
    	sqlMain += " riskcode='0507'  And comcode  Like  '" + comCode.substring(0, 2) + "%'  ";
    	sqlMain += " And substr(othflag, 3 ,1) != '1'  And  substr(othflag, 4, 1) != '1'  ";
    	sqlMain += " and operatedate >= to_date('" + startDate + "','YYYY-MM-DD')  ";
    	sqlMain += " and operatedate <= to_date('" + endDate + "','YYYY-MM-DD')  ";
    	sqlMain += " and policyno not in(select policyno from ciinsurevalid where  comcode like '" + comCode.substring(0, 2) + "%')";
 
    	
    	logger.info("sqlMain"+sqlMain);
    	
    	BLPrpCmain blPrpCmain = new BLPrpCmain();
    	blPrpCmain.query(sqlMain, 0);
    	
    	PrpCmainSchema prpcmainshema = new PrpCmainSchema();
    	String strPolicyno = "";
    	try
    	{
    		
    		logger.info("blPrpCmain.getSize():  = = "+blPrpCmain.getSize());
    		
    		dbPool.open(SysConst.getProperty("DDCCDATASOURCE"));
    		for(int i = 0; i < blPrpCmain.getSize(); i++)
    		{
	      	   	
	      	   	logger.info("近来循环 第 " + i + "  次");
	      	   	
      	   		prpcmainshema = blPrpCmain.getArr(i);
      	   		strPolicyno = prpcmainshema.getPolicyNo();
	      	   	
	      	   	logger.info("  XX  " + strPolicyno + "");
	      	   	
      	   		dbPool.beginTransaction();
      	   	try{
      	   		sendPolicy(dbPool, strPolicyno);
      	   	}catch(Exception exception){
      	   	    throw exception;
      	   	}
      	   		dbPool.commitTransaction();
    		}
        }
        catch(Exception exception)
        {
        	dbPool.rollbackTransaction();
        	
        	logger.error("批量处理直接XX失败，XX号：" + strPolicyno);
        	
        	exception.printStackTrace();
            throw exception;
        }
        finally
        {
        	dbPool.close();
        }
  }		
	
	/*
	 * 获得Policy大对象
	 */
	private BLPolicy getBLPolicyDate(DbPool dbPool, String policyNo)
			throws Exception 
	{
		BLPolicy blPolicy = new BLPolicy();
		
		try
		{
			blPolicy.getData(dbPool, policyNo);
		}
		catch(Exception exception)
        {
            exception.printStackTrace();
            throw exception;
        }
		return blPolicy;
	}
}

  