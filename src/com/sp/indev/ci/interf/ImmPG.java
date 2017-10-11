package com.sp.indiv.ci.interf;

import com.sp.prpall.blsvr.pg.BLEndorse;
import com.sp.prpall.blsvr.pg.BLPrpPmain;
import com.sp.prpall.schema.PrpPmainSchema;
import com.sp.sysframework.common.util.StringUtils;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ImmPG {

	Log logger = LogFactory.getLog(getClass());
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		

	}
	
	/**
	 * @批单号直接批改
	 * 
	 */
	public void sendEndorseNo(DbPool dbPool, String strEndorseNo)
	     throws Exception
	{	
		 String request = "";
		 String response  = "";
		 String comCode = "";
		 BLEndorse blEndorse =  null;
		 ImmpgEncoder ImmpgEncoder = new ImmpgEncoder();
		 ImmpgDecoder ImmpgDecoder = new ImmpgDecoder();
		 
		 try 
		 {
			 blEndorse 	= getBLEndorseDate(dbPool, strEndorseNo);
			 comCode 	= blEndorse.getBLPrpPmain().getArr(0).getComCode();
			 
			 logger.info("**************22****in ImmPG*****************comCode="+comCode);
			 
			 request 	= ImmpgEncoder.encode(dbPool, blEndorse);
			
			 logger.info("直接批改发给平台的串："+request);
			 
			 
			 response 	= EbaoProxy.getInstance().request(request, comCode);
			 response 	= StringUtils.replace(response, "GBK", "GB2312");	
			 
			 logger.info("直接批改平台返回的串："+response);
			 
			 ImmpgDecoder.decode(dbPool, blEndorse, response);
		 } 
		 catch (Exception exception) 
		 {
			 
			 logger.error("单独直接批改失败，批单号：" + strEndorseNo);
			 
			 exception.printStackTrace();
			 throw exception;
		 }
	}
	
	/**
	 * @按日期批量处理
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
    	sqlMain += " and underwriteenddate >= to_date('" + startDate + "','YYYY-MM-DD')  ";
    	sqlMain += " and underwriteenddate <= to_date('" + endDate + "','YYYY-MM-DD')  ";
    	sqlMain += " and endorseno not in(select endorseno from ciendorvalid )";
 
    	
    	logger.info("sqlMain"+sqlMain);
    	
    	BLPrpPmain blPrpPmain = new BLPrpPmain();
    	blPrpPmain.query(sqlMain, 0);
    	
    	PrpPmainSchema prppmainshema = new PrpPmainSchema();
    	String strEndorseNo = "";
    	try
    	{
    		
    		logger.info("blPrpPmain.getSize():  = = "+blPrpPmain.getSize());
    		
    		dbPool.open(SysConst.getProperty("DDCCDATASOURCE"));
    		for(int i = 0; i < blPrpPmain.getSize(); i++)
    		{
	      	   	
	      	   	logger.info("近来循环 第 " + i + "  次");
	      	   	
      	     	prppmainshema = blPrpPmain.getArr(i);
      	      	strEndorseNo = prppmainshema.getEndorseNo();
	      	   	
	      	   	logger.info("  批单  " + strEndorseNo + "");
	      	   	
      	   		dbPool.beginTransaction();
      	   	    try{
      	   	    	sendEndorseNo(dbPool, strEndorseNo);
      	   	    }catch(Exception exception){
      	   	        throw exception;
      	   	    }
      	   		dbPool.commitTransaction();
    		}
        }
        catch(Exception exception)
        {
        	dbPool.rollbackTransaction();
        	
        	logger.error("批量处理直接批改失败，批单号：" + strEndorseNo);
        	
        	exception.printStackTrace();
            throw exception;
        }
        finally
        {
        	dbPool.close();
        }
  }		

	/*
	 * 获得Endorse大对象 BLEndorse blEndorse = new BLEndorse();
	 */
	private BLEndorse getBLEndorseDate(DbPool dbPool, String strEndorseNo)
			throws Exception 
	{
		BLEndorse blEndorse = new BLEndorse();
		
		try
		{
			blEndorse.getData(strEndorseNo);
		}
		catch(Exception exception)
        {
            exception.printStackTrace();
            throw exception;
        }
		return blEndorse;
	}
}
