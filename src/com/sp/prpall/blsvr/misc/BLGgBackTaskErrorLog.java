package com.sp.prpall.blsvr.misc;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.util.*;

import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.prpall.dbsvr.misc.DBGgBackTaskErrorLog;
import com.sp.prpall.schema.GgBackTaskErrorLogSchema;

/**
 * 定义GgBackTaskErrorLog的BL类
 * 从pdm中取数据库信息,根据数据库表生成对应的BL类
 * <p>Title: SinosoftJavaTools v1.4</p>
 * <p>Description: 中科软Java源码生成工具</p>
 * <p>Company: Sinosoft</p>
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>@createdate 2013-06-18</p>
 * @FirstAuthor: Zhouxianli(JavaTools v1.0)
 * @SecondAuthor: Yangkun(JavaTools v1.1->v1.2->v1.3->v1.4)
 * @LastVersion: v1.4.2
 * @UpdateList: 1.新增对CIXXXXX平台类的生成处理;
 *              2.修正特殊表生成类cancel()、getdata()方法时获取pdm表主键入参为null的问题;
 *              3.修正特殊表生成类部分import类为null的问题;
 *              4.新增log4j日志bl层类自动初始化;
 */
public class BLGgBackTaskErrorLog{
    
    private Vector schemas = new Vector();
    /**
     * 构造函数
     */       
    public BLGgBackTaskErrorLog(){
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
     *增加一条GgBackTaskErrorLogSchema记录
     *@param iGgBackTaskErrorLogSchema GgBackTaskErrorLogSchema
     *@throws Exception
     */
    public void setArr(GgBackTaskErrorLogSchema iGgBackTaskErrorLogSchema) throws Exception
    {
      try
      {
        schemas.add(iGgBackTaskErrorLogSchema);
      }
      catch(Exception e)
      {
        throw e;
      }
    }
    /**
     *得到一条GgBackTaskErrorLogSchema记录
     *@param index 下标
     *@return 一个GgBackTaskErrorLogSchema对象
     *@throws Exception
     */
    public GgBackTaskErrorLogSchema getArr(int index) throws Exception
    {
      GgBackTaskErrorLogSchema ggBackTaskErrorLogSchema = null;
       try
       {
         ggBackTaskErrorLogSchema = (GgBackTaskErrorLogSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return ggBackTaskErrorLogSchema;
     }
    /**
     *删除一条GgBackTaskErrorLogSchema记录
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
      DBGgBackTaskErrorLog dbGgBackTaskErrorLog = new DBGgBackTaskErrorLog();
      if (iLimitCount > 0 && dbGgBackTaskErrorLog.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLGgBackTaskErrorLog.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM GgBackTaskErrorLog WHERE " + iWherePart; 
        schemas = dbGgBackTaskErrorLog.findByConditions(strSqlStatement);
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
    public void query(DbPool dbpool,String iWherePart,int iLimitCount) throws UserException,Exception
    {
      String strSqlStatement = "";
      DBGgBackTaskErrorLog dbGgBackTaskErrorLog = new DBGgBackTaskErrorLog();
      if (iLimitCount > 0 && dbGgBackTaskErrorLog.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLGgBackTaskErrorLog.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM GgBackTaskErrorLog WHERE " + iWherePart; 
        schemas = dbGgBackTaskErrorLog.findByConditions(dbpool,strSqlStatement);
      }
    }
      
    /**
     *带dbpool的save方法
     *@param 无
     *@return 无
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBGgBackTaskErrorLog dbGgBackTaskErrorLog = new DBGgBackTaskErrorLog();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
        dbGgBackTaskErrorLog.setSchema((GgBackTaskErrorLogSchema)schemas.get(i));
        dbGgBackTaskErrorLog.insert(dbpool);
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
        dbpool.open(SysConfig.CONST_RENEWALCHECKDATASOURCE);
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
     *带dbpool的删除方法
     *@param dbpool    连接池
     *@param iSeqNo SeqNo
     *@return 无
     */
    public void cancel(DbPool dbpool,String iSeqNo) throws Exception
    {
      String strSqlStatement = "";
      
      strSqlStatement = " DELETE FROM GgBackTaskErrorLog WHERE SeqNo= '" + iSeqNo + "'";
      
      dbpool.delete(strSqlStatement);
    }
      
    /**
     * 不带dbpool的删除方法
     *@param iSeqNo SeqNo
     *@return 无
     */
    public void cancel(String iSeqNo ) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      try
      {
        dbpool.open(SysConfig.CONST_RENEWALCHECKDATASOURCE);
        dbpool.beginTransaction();
        cancel(dbpool,iSeqNo);
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
     * 带dbpool根据SeqNo获取数据
     *@param iSeqNo SeqNo
     *@return 无
     */
    public void getData(String iSeqNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      dbpool.open(SysConfig.CONST_RENEWALCHECKDATASOURCE);
      getData(dbpool,iSeqNo);
      dbpool.close();
    }
      
    /**
     * 不带dbpool根据SeqNo获取数据
     *@param dbpool 连接池
     *@param iSeqNo SeqNo
     *@return 无
     */
    public void getData(DbPool dbpool,String iSeqNo) throws Exception
    {
      String strWherePart = "";
      strWherePart = "SeqNo= '" + iSeqNo + "'";
      query(dbpool,strWherePart,0);
    }
    /**
     * 主函数
     * @param args 参数列表
     */
    public static void main(String[] args){
        
    }
}
