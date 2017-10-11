package com.sp.prpall.blsvr.misc;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.util.*;

import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.prpall.dbsvr.misc.DBPrpBorrowFinanc;
import com.sp.prpall.schema.PrpBorrowFinancSchema;

/**
 * 定义质押借款业务数据表的BL类
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>@createdate 2015-06-16</p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLPrpBorrowFinanc{
    private Vector schemas = new Vector();
    /**
     * 构造函数
     */       
    public BLPrpBorrowFinanc(){
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
     *增加一条PrpBorrowFinancSchema记录
     *@param iPrpBorrowFinancSchema PrpBorrowFinancSchema
     *@throws Exception
     */
    public void setArr(PrpBorrowFinancSchema iPrpBorrowFinancSchema) throws Exception
    {
       try
       {
         schemas.add(iPrpBorrowFinancSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *得到一条PrpBorrowFinancSchema记录
     *@param index 下标
     *@return 一个PrpBorrowFinancSchema对象
     *@throws Exception
     */
    public PrpBorrowFinancSchema getArr(int index) throws Exception
    {
     PrpBorrowFinancSchema prpBorrowFinancSchema = null;
       try
       {
        prpBorrowFinancSchema = (PrpBorrowFinancSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpBorrowFinancSchema;
     }
    /**
     *删除一条PrpBorrowFinancSchema记录
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
     *按照查询条件得到一组记录数，并将这组记录赋给schemas对象
     *@param iWherePart 查询条件(包括排序字句)
     *@throws UserException
     *@throws Exception
     */
    public void query(String iWherePart) throws UserException,Exception
    {
       this.query(iWherePart, Integer.parseInt(SysConst.getProperty("QUERY_LIMIT_COUNT").trim()));
    }
    /**
     *按照查询条件和记录数限制得到一组记录数，并将这组记录赋给schemas对象
     *@param iWherePart 查询条件(包括排序字句)
     *@param iLimitCount 记录数限制(iLimitCount=0: 无限制)
     *@throws UserException
     *@throws Exception
     */
    public void query(String iWherePart, int iLimitCount) throws UserException,Exception
    {
      String strSqlStatement = "";
      DBPrpBorrowFinanc dbPrpBorrowFinanc = new DBPrpBorrowFinanc();
      if (iLimitCount > 0 && dbPrpBorrowFinanc.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpBorrowFinanc.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpBorrowFinanc WHERE " + iWherePart; 
        schemas = dbPrpBorrowFinanc.findByConditions(strSqlStatement);
      }
    }
    /**
     *按照查询条件得到一组记录数，并将这组记录赋给schemas对象
     *@param dbpool 全局池
     *@param iWherePart 查询条件(包括排序字句)
     *@throws UserException
     *@throws Exception
     */
    public void query(DbPool dbpool, String iWherePart) throws UserException,Exception
    {
       this.query(dbpool,iWherePart,Integer.parseInt(SysConst.getProperty("QUERY_LIMIT_COUNT").trim()));
    }
    /**
     *按照查询条件和记录数限制得到一组记录数，并将这组记录赋给schemas对象
     *@param dbpool 全局池
     *@param iWherePart 查询条件(包括排序字句)
     *@param iLimitCount 记录数限制(iLimitCount=0: 无限制)
     *@throws UserException
     *@throws Exception
     */
    public void query(DbPool dbpool, String iWherePart, int iLimitCount) throws UserException,Exception
    {
      String strSqlStatement = "";
      DBPrpBorrowFinanc dbPrpBorrowFinanc = new DBPrpBorrowFinanc();
      if (iLimitCount > 0 && dbPrpBorrowFinanc.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpBorrowFinanc.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpBorrowFinanc WHERE " + iWherePart; 
        schemas = dbPrpBorrowFinanc.findByConditions(dbpool, strSqlStatement);
      }
    }
      
    /**
     *带dbpool的save方法
     *@param 无
     *@return 无
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpBorrowFinanc dbPrpBorrowFinanc = new DBPrpBorrowFinanc();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
      dbPrpBorrowFinanc.setSchema((PrpBorrowFinancSchema)schemas.get(i));
      dbPrpBorrowFinanc.insert(dbpool);
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
      try
      {
    	dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        dbpool.beginTransaction();
        save(dbpool);
        dbpool.commitTransaction(); 
      }
      catch (Exception e)
      {
        dbpool.rollbackTransaction();
      }
      finally
      {
        dbpool.close();
      }
    }
      
    /**
     * 带dbpool的删除方法
     *@param dbpool    连接池
     *@param insuranceNo XX订单号
     *@return 无
     */
    public void cancel(DbPool dbpool, String insuranceNo) throws Exception
    {
      String strSqlStatement = "";
      
      strSqlStatement = " DELETE FROM PrpBorrowFinanc WHERE insuranceNo='" + insuranceNo + "'";
      
      dbpool.delete(strSqlStatement);
    }
      
    /**
     * 不带dbpool的删除方法
     *@param insuranceNo XX订单号
     *@return 无
     */
    public void cancel(String insuranceNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      try
      {
      	dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        dbpool.beginTransaction();
        cancel(dbpool, insuranceNo);
        dbpool.commitTransaction(); 
      }
      catch (Exception e)
      {
        dbpool.rollbackTransaction();
      }
      finally
      {
        dbpool.close();
      }
    }
      
    /**
     * 不带dbpool根据主键获取数据
     *@param insuranceNo XX订单号
     *@return 无
     */
    public void getData(String insuranceNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      try {
    	  dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
    	  getData(dbpool, insuranceNo);
	  } catch (Exception e) {

	  }finally{
		  dbpool.close();
	  }
    }
      
    /**
     * 带dbpool根据主键获取数据
     *@param dbpool 连接池
     *@param insuranceNo XX订单号
     *@return 无
     */
    public void getData(DbPool dbpool, String insuranceNo) throws Exception
    {
       String strWherePart = "";
       strWherePart = "insuranceNo='" + insuranceNo + "'";
       query(dbpool, strWherePart, 0);
    }
    /**
     * 主函数
     * @param args 参数列表
     */
    public static void main(String[] args){
        
    }
}
