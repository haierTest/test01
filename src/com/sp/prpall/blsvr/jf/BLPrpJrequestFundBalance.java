package com.sp.prpall.blsvr.jf;

import java.util.*;

import com.sp.utility.*;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.prpall.dbsvr.jf.DBPrpJrequestFundBalance;
import com.sp.prpall.schema.PrpJrequestFundBalanceSchema;
import org.apache.commons.logging.Log; 
import org.apache.commons.logging.LogFactory;

/**
 * 定义BLPrpJrequestFundBalance的BL类
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>@createdate 2008-10-23</p>
 * @author zhanglingjian
 * @version 1.0
 */
public class BLPrpJrequestFundBalance{
    private Vector schemas = new Vector();
    protected final Log logger = LogFactory.getLog(getClass());

    /**
     * 构造函数
     */
    public BLPrpJrequestFundBalance(){
    }

    /**
     *初始化记录
     *@param 无
     *@return 无
     *@throws Exception
     */
    public void initArr() throws Exception
    {
      schemas = new Vector();
    }

    /**
     *增加一条PrpJrequestFundBalanceSchema记录
     *@param PrpJrequestFundBalanceSchema PrpJrequestFundBalanceSchema
     *@throws Exception
     */
    public void setArr(PrpJrequestFundBalanceSchema iPrpJrequestFundBalanceSchema) throws Exception
    {
      try
      {
        schemas.add(iPrpJrequestFundBalanceSchema);
      }
      catch(Exception e)
      {
        throw e;
      }
    }

    /**
     *得到一条PrpJrequestFundBalanceSchema记录
     *@param index 下标
     *@return 一个PrpJrequestFundBalanceSchema对象
     *@throws Exception
     */
    public PrpJrequestFundBalanceSchema getArr(int index) throws Exception
    {
    	PrpJrequestFundBalanceSchema prpJrequestFundBalanceSchema = null;
      try
      {
    	  prpJrequestFundBalanceSchema = (PrpJrequestFundBalanceSchema)this.schemas.get(index);
      }
      catch(Exception e)
      {
        throw e;
      }
      return prpJrequestFundBalanceSchema;
    }
    
    /**
     *删除一条PrpJrequestFundBalanceSchema记录
     *@param index 下标
     *@throws Exception
     */
    public void remove(int index) throws Exception
    {
      try
      {
        this.schemas.remove(index);
      }
      catch(Exception e)
      {
        throw e;
      }
    }

    /**
     *得到schemas记录数
     *@return schemas记录数
     *@throws Exception
     */
    public int getSize() throws Exception
    {
      return this.schemas.size();
    }
    
    /**
     *带dbpool的save方法
     *@param 无
     *@return 无
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpJrequestFundBalance dbPrpJrequestFundBalance = new DBPrpJrequestFundBalance();

      int i = 0;

      for(i = 0; i< schemas.size(); i++)
      {
    	  dbPrpJrequestFundBalance.setSchema((PrpJrequestFundBalanceSchema)schemas.get(i));
    	  dbPrpJrequestFundBalance.insert(dbpool);
      }
    }
    
    /**
     *不带dbpool的XXXXX存方法
     *@param 无
     *@return 无
     */
    public void save() throws Exception
    {
    	DbPool dbpool = new DbPool();

        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
	        dbpool.beginTransaction();
	        save(dbpool);
	        dbpool.commitTransaction();
	    }
        catch (Exception e)
        {
        	dbpool.rollbackTransaction();
        }
        finally {
        	dbpool.close();
        }
    }

    /**
     *按照查询条件得到一组记录数，并将这组记录赋给schemas对象
     *@param iWherePart 查询条件(包括排序字句)
     *@throws UserException
     *@throws Exception
     */
    public void query(String iWherePart) throws UserException,Exception
    {
      this.query(iWherePart,Integer.parseInt(SysConst.getProperty("QUERY_LIMIT_COUNT").trim()));
    }

    /**
     *按照查询条件和记录数限制得到一组记录数，并将这组记录赋给schemas对象
     *@param iWherePart 查询条件(包括排序字句)
     *@param iLimitCount 记录数限制(iLimitCount=0: 无限制)
     *@throws UserException
     *@throws Exception
     */
    public void query(String iWherePart,int iLimitCount) throws UserException,Exception
    {
      String strSqlStatement = "";
      DBPrpJrequestFundBalance dbPrpJrequestFundBalance = new DBPrpJrequestFundBalance();
      if (iLimitCount > 0 && dbPrpJrequestFundBalance.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJrequestFundBalance.query");
      }else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpJrequestFundBalance WHERE " + iWherePart;
        schemas = dbPrpJrequestFundBalance.findByConditions(strSqlStatement);
      }
    }
    
    /**
     *按照查询条件得到一组记录数，并将这组记录赋给schemas对象
     *@param iWherePart 查询条件(包括排序字句)
     *@throws UserException
     *@throws Exception
     */
    public void query(DbPool dbpool,String iWherePart) throws UserException,Exception
    {
      this.query(dbpool,iWherePart,Integer.parseInt(SysConst.getProperty("QUERY_LIMIT_COUNT").trim()));
    }

    /**
     *按照查询条件和记录数限制得到一组记录数，并将这组记录赋给schemas对象
     *@param iWherePart 查询条件(包括排序字句)
     *@param iLimitCount 记录数限制(iLimitCount=0: 无限制)
     *@throws UserException
     *@throws Exception
     */
    public void query(DbPool dbpool,String iWherePart,int iLimitCount) throws UserException,Exception
    {
      String strSqlStatement = "";
      DBPrpJrequestFundBalance dbPrpJrequestFundBalance = new DBPrpJrequestFundBalance();
      if (iLimitCount > 0 && dbPrpJrequestFundBalance.getCount(dbpool,iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJrequestFundBalance.query");
      }else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpJrequestFundBalance WHERE " + iWherePart;
        schemas = dbPrpJrequestFundBalance.findByConditions(dbpool,strSqlStatement);
      }
    }
    
    /**
     * 主函数
     * @param args 参数列表
     */
    public static void main(String[] args){
        
    }
}
