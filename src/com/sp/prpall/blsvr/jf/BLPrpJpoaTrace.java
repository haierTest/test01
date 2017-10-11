package com.sp.prpall.blsvr.jf;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.util.*;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.prpall.dbsvr.jf.DBPrpJpoaMain;
import com.sp.prpall.dbsvr.jf.DBPrpJpoaTrace;
import com.sp.prpall.schema.PrpJpoaTraceSchema;


/**
 * @version 1.0
 */
public class BLPrpJpoaTrace{
    private Vector schemas = new Vector();
    /**
     * 构造函数
     */       
    public BLPrpJpoaTrace(){
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
     *增加一条PrpjPoaMainSchema记录
     *@param PrpjPoaMainSchema PrpjPoaMainSchema
     *@throws Exception
     */
    public void setArr(PrpJpoaTraceSchema prpJpoaTraceSchema) throws Exception
    {
       try
       {
         schemas.add(prpJpoaTraceSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *得到一条PrpJpoaMainSchema记录
     *@param index 下标
     *@return 一个PrpJpoaMainSchema对象
     *@throws Exception
     */
    public PrpJpoaTraceSchema getArr(int index) throws Exception
    {
    	PrpJpoaTraceSchema prpJpoaTraceSchema = null;
       try
       {
    	   prpJpoaTraceSchema = (PrpJpoaTraceSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpJpoaTraceSchema;
     }
    /**
     *删除一条PrpJcommAllocSchema记录
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
      DBPrpJpoaTrace dbPrpjPoaTrace = new DBPrpJpoaTrace();
      if (iLimitCount > 0 && dbPrpjPoaTrace.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJpoaTrace.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpJpoaTrace WHERE " + iWherePart;
        schemas = dbPrpjPoaTrace.findByConditions(strSqlStatement);
      }
    }
    /**
     * 得到记录数
     * @param condition
     * @return
     * @throws Exception
     */
    public int getCount(String condition) throws Exception
    {
    	DBPrpJpoaTrace dbPrpJpoaTrace = new DBPrpJpoaTrace();
        return dbPrpJpoaTrace.getCount(condition);
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
      DBPrpJpoaTrace dbPrpjPoaTrace = new DBPrpJpoaTrace();
      if (iLimitCount > 0 && dbPrpjPoaTrace.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJpoatrace.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpJpoaTrace WHERE " + iWherePart; 
        schemas = dbPrpjPoaTrace.findByConditions(dbpool,strSqlStatement);
      }
    }
      
    /**
     *带dbpool的save方法
     *@param 无
     *@return 无
     */
    public void save(DbPool dbpool) throws Exception
    {
    	DBPrpJpoaTrace dbPrpjPoaTrace = new DBPrpJpoaTrace();
      
      int i = 0;
      
      
      for(i = 0; i< schemas.size(); i++)
      {
    	  dbPrpjPoaTrace.setSchema((PrpJpoaTraceSchema)schemas.get(i));
    	  dbPrpjPoaTrace.insert(dbpool);
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
    public void cancel(DbPool dbpool,String PoaCode) throws Exception
    {




    	String strSqlStatement = " DELETE FROM PrpJpoaTrace WHERE PoaCode= ?";
    	dbpool.prepareInnerStatement(strSqlStatement);
    	dbpool.setString(1, PoaCode);
	dbpool.executePreparedUpdate();
	dbpool.closePreparedStatement();
     
    }
      
    /**
     * 不带dbpool的删除方法
     *@param null null
     *@return 无
     */
   public void cancel(String PoaCode ) throws Exception
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
    public void getData(String PoaCode) throws Exception
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
    public void getData(DbPool dbpool,String PoaCode) throws Exception
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
