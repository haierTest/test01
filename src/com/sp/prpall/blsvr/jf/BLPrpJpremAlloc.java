package com.sp.prpall.blsvr.jf;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.util.*;
import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.prpall.dbsvr.jf.DBPrpJpremAlloc;
import com.sp.prpall.schema.PrpJpremAllocSchema;

/**
 * 定义PrpJpremAlloc的BL类
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>@createdate 2003-07-17</p>
 * @Author     : X
 * @version 1.0
 */
public class BLPrpJpremAlloc{
    private Vector schemas = new Vector();
    /**
     * 构造函数
     */       
    public BLPrpJpremAlloc(){
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
     *增加一条PrpJpremAllocSchema记录
     *@param iPrpJpremAllocSchema PrpJpremAllocSchema
     *@throws Exception
     */
    public void setArr(PrpJpremAllocSchema iPrpJpremAllocSchema) throws Exception
    {
       try
       {
         schemas.add(iPrpJpremAllocSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *得到一条PrpJpremAllocSchema记录
     *@param index 下标
     *@return 一个PrpJpremAllocSchema对象
     *@throws Exception
     */
    public PrpJpremAllocSchema getArr(int index) throws Exception
    {
     PrpJpremAllocSchema prpJpremAllocSchema = null;
       try
       {
        prpJpremAllocSchema = (PrpJpremAllocSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpJpremAllocSchema;
     }
    /**
     *删除一条PrpJpremAllocSchema记录
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
       this.query(iWherePart,Integer.parseInt(SysConfig.getProperty("QUERY_LIMIT_COUNT").trim()));
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
      DBPrpJpremAlloc dbPrpJpremAlloc = new DBPrpJpremAlloc();
      if (iLimitCount > 0 && dbPrpJpremAlloc.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJpremAlloc.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpJpremAlloc WHERE " + iWherePart; 
        schemas = dbPrpJpremAlloc.findByConditions(strSqlStatement);
      }
    }
    /**
     *按照查询条件得到一组记录数，并将这组记录赋给schemas对象
     *@param dbpool 全局池
     *@param iWherePart 查询条件(包括排序字句)
     *@throws UserException
     *@throws Exception
     */
    public void query(DbPool dbpool,String iWherePart) throws UserException,Exception
    {
       this.query(dbpool,iWherePart,Integer.parseInt(SysConfig.getProperty("QUERY_LIMIT_COUNT").trim()));
    }
    /**
     *按照查询条件和记录数限制得到一组记录数，并将这组记录赋给schemas对象
     *@param dbpool 全局池
     *@param iWherePart 查询条件(包括排序字句)
     *@param iLimitCount 记录数限制(iLimitCount=0: 无限制)
     *@throws UserException
     *@throws Exception
     */
    public void query(DbPool dbpool,String iWherePart,int iLimitCount) throws UserException,Exception
    {
      String strSqlStatement = "";
      DBPrpJpremAlloc dbPrpJpremAlloc = new DBPrpJpremAlloc();
      if (iLimitCount > 0 && dbPrpJpremAlloc.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJpremAlloc.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpJpremAlloc WHERE " + iWherePart; 
        schemas = dbPrpJpremAlloc.findByConditions(dbpool,strSqlStatement);
      }
    }
      
    /**
     *带dbpool的save方法
     *@param 无
     *@return 无
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpJpremAlloc dbPrpJpremAlloc = new DBPrpJpremAlloc();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
      dbPrpJpremAlloc.setSchema((PrpJpremAllocSchema)schemas.get(i));
      dbPrpJpremAlloc.insert(dbpool);
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
     *带dbpool的删除方法
     *@param dbpool    连接池
     *@param null null
     *@return 无
     */
    public void cancel(DbPool dbpool,String strcertino) throws Exception
    {




    	String strSqlStatement = " DELETE FROM PrpJpremAlloc WHERE certino= ?";
    	dbpool.prepareInnerStatement(strSqlStatement);
    	dbpool.setString(1, strcertino);
	dbpool.executePreparedUpdate();
	dbpool.closePreparedStatement();
     
    }
      
    /**
     * 不带dbpool的删除方法
     *@param null null
     *@return 无
     */
    public void cancel(String strcertino ) throws Exception
    {
      DbPool dbpool = new DbPool();
      
        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        dbpool.beginTransaction();
        cancel(dbpool,null);
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
     * 带dbpool根据null获取数据
     *@param null null
     *@return 无
     */
    public void getData(String strcertino) throws Exception
    {
        DbPool dbpool = new DbPool();

  	try {
  		dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
  		getData(dbpool, null);
  	} catch (Exception e) {
  		
  	}finally {      
  		dbpool.close();
  	}

    }
      
    /**
     * 不带dbpool根据null获取数据
     *@param dbpool 连接池
     *@param null null
     *@return 无
     */
    public void getData(DbPool dbpool,String strcertino) throws Exception
    {
    String strWherePart = "";
    strWherePart = "null= ' " + null + "'";
    query(dbpool,strWherePart,0);
    }
    /**
     * 主函数
     * @param args 参数列表
     */
    public static void main(String[] args){
        
    }
}
