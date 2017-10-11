package com.sp.prpall.blsvr.misc;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.util.*;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.prpall.dbsvr.misc.DBPrpQualityCheck;
import com.sp.prpall.schema.PrpQualityCheckSchema;

/**
 * 定义PrpQualityCheck的BL类
 * 从pdm中取数据库信息,根据数据库表生成对应的BL类
 * <p>Title: SinosoftJavaTools v1.4</p>
 * <p>Description: 中科软Java源码生成工具</p>
 * <p>Company: Sinosoft</p>
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>@createdate 2012-07-04</p>
 * @FirstAuthor: Zhouxianli(JavaTools v1.0)
 * @SecondAuthor: Yangkun(JavaTools v1.1->v1.2->v1.3->v1.4)
 * @LastVersion: v1.4.2
 * @UpdateList: 1.新增对CIXXXXX平台类的生成处理;
 *              2.修正特殊表生成类cancel()、getdata()方法时获取pdm表主键入参为null的问题;
 *              3.修正特殊表生成类部分import类为null的问题;
 *              4.新增log4j日志bl层类自动初始化;
 */
public class BLPrpQualityCheck{
    private Vector schemas = new Vector();
    /**
     * 构造函数
     */       
    public BLPrpQualityCheck(){
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
     *增加一条PrpQualityCheckSchema记录
     *@param iPrpQualityCheckSchema PrpQualityCheckSchema
     *@throws Exception
     */
    public void setArr(PrpQualityCheckSchema iPrpQualityCheckSchema) throws Exception
    {
      try
      {
        schemas.add(iPrpQualityCheckSchema);
      }
      catch(Exception e)
      {
        throw e;
      }
    }
    /**
     *得到一条PrpQualityCheckSchema记录
     *@param index 下标
     *@return 一个PrpQualityCheckSchema对象
     *@throws Exception
     */
    public PrpQualityCheckSchema getArr(int index) throws Exception
    {
      PrpQualityCheckSchema prpQualityCheckSchema = null;
       try
       {
         prpQualityCheckSchema = (PrpQualityCheckSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpQualityCheckSchema;
     }
    /**
     *删除一条PrpQualityCheckSchema记录
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
      DBPrpQualityCheck dbPrpQualityCheck = new DBPrpQualityCheck();
      if (iLimitCount > 0 && dbPrpQualityCheck.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpQualityCheck.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpQualityCheck WHERE " + iWherePart; 
        schemas = dbPrpQualityCheck.findByConditions(strSqlStatement);
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
      DBPrpQualityCheck dbPrpQualityCheck = new DBPrpQualityCheck();
      if (iLimitCount > 0 && dbPrpQualityCheck.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpQualityCheck.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpQualityCheck WHERE " + iWherePart; 
        schemas = dbPrpQualityCheck.findByConditions(dbpool,strSqlStatement);
      }
    }
      
    /**
     *带dbpool的save方法
     *@param 无
     *@return 无
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpQualityCheck dbPrpQualityCheck = new DBPrpQualityCheck();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
        dbPrpQualityCheck.setSchema((PrpQualityCheckSchema)schemas.get(i));
        dbPrpQualityCheck.insert(dbpool);
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
        dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
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
     *@param iBussinessno 业务号
     *@return 无
     */
    public void cancel(DbPool dbpool,String iBussinessno) throws Exception
    {
      String strSqlStatement = "";
      
      strSqlStatement = " DELETE FROM PrpQualityCheck WHERE Bussinessno= '" + iBussinessno + "'";
      
      dbpool.delete(strSqlStatement);
    }
      
    /**
     * 不带dbpool的删除方法
     *@param iBussinessno 业务号
     *@return 无
     */
    public void cancel(String iBussinessno ) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      try
      {
        dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
        dbpool.beginTransaction();
        cancel(dbpool,iBussinessno);
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
     * 带dbpool根据业务号获取数据
     *@param iBussinessno 业务号
     *@return 无
     */
    public void getData(String iBussinessno) throws Exception
    {
      DbPool dbpool = new DbPool();
      dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
      getData(dbpool,iBussinessno);
      dbpool.close();
    }
      
    /**
     * 不带dbpool根据业务号获取数据
     *@param dbpool 连接池
     *@param iBussinessno 业务号
     *@return 无
     */
    public void getData(DbPool dbpool,String iBussinessno) throws Exception
    {
      String strWherePart = "";
      strWherePart = "Bussinessno= '" + iBussinessno + "'";
      query(dbpool,strWherePart,0);
    }
    /**
     * 主函数
     * @param args 参数列表
     */
    public static void main(String[] args){
        
    }
}
