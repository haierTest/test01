package com.sp.prpall.blsvr.misc;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.util.*;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sp.prpall.dbsvr.misc.DBCYBComPara;
import com.sp.prpall.schema.CYBComParaSchema;

/**
 * 定义CYBComPara的BL类
 * 从pdm中取数据库信息,根据数据库表生成对应的BL类
 * <p>Title: SinosoftJavaTools v1.4</p>
 * <p>Description: 中科软Java源码生成工具</p>
 * <p>Company: Sinosoft</p>
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>@createdate 2012-08-03</p>
 * @FirstAuthor: Zhouxianli(JavaTools v1.0)
 * @SecondAuthor: Yangkun(JavaTools v1.1->v1.2->v1.3->v1.4)
 * @LastVersion: v1.4.2
 * @UpdateList: 1.新增对CIXXXXX平台类的生成处理;
 *              2.修正特殊表生成类cancel()、getdata()方法时获取pdm表主键入参为null的问题;
 *              3.修正特殊表生成类部分import类为null的问题;
 *              4.新增log4j日志bl层类自动初始化;
 */
public class BLCYBComPara{
    private final Log logger = LogFactory.getLog(getClass());
    private Vector schemas = new Vector();
    /**
     * 构造函数
     */       
    public BLCYBComPara(){
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
     *增加一条CYBComParaSchema记录
     *@param iCYBComParaSchema CYBComParaSchema
     *@throws Exception
     */
    public void setArr(CYBComParaSchema iCYBComParaSchema) throws Exception
    {
      try
      {
        schemas.add(iCYBComParaSchema);
      }
      catch(Exception e)
      {
        throw e;
      }
    }
    /**
     *得到一条CYBComParaSchema记录
     *@param index 下标
     *@return 一个CYBComParaSchema对象
     *@throws Exception
     */
    public CYBComParaSchema getArr(int index) throws Exception
    {
      CYBComParaSchema cYBComParaSchema = null;
       try
       {
         cYBComParaSchema = (CYBComParaSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return cYBComParaSchema;
     }
    /**
     *删除一条CYBComParaSchema记录
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
      DBCYBComPara dbCYBComPara = new DBCYBComPara();
      if (iLimitCount > 0 && dbCYBComPara.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLCYBComPara.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM T_SPIS_CYBCOMPARA WHERE " + iWherePart; 
        schemas = dbCYBComPara.findByConditions(strSqlStatement);
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
      DBCYBComPara dbCYBComPara = new DBCYBComPara();
      if (iLimitCount > 0 && dbCYBComPara.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLCYBComPara.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM T_SPIS_CYBCOMPARA WHERE " + iWherePart; 
        schemas = dbCYBComPara.findByConditions(dbpool,strSqlStatement);
      }
    }
      
    /**
     *带dbpool的save方法
     *@param 无
     *@return 无
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBCYBComPara dbCYBComPara = new DBCYBComPara();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
        dbCYBComPara.setSchema((CYBComParaSchema)schemas.get(i));
        dbCYBComPara.insert(dbpool);
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
     *带dbpool的update方法
     *@param 无
     *@return 无
     */
    public void update(DbPool dbpool) throws Exception
    {
      DBCYBComPara dbCYBComPara = new DBCYBComPara();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
        dbCYBComPara.setSchema((CYBComParaSchema)schemas.get(i));
        dbCYBComPara.update(dbpool);
      }
    }
      
    /**
     *不带dbpool的update方法
     *@param 无
     *@return 无
     */
    public void update() throws Exception
    {
      DbPool dbpool = new DbPool();
      
      try
      {
        dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
        dbpool.beginTransaction();
        update(dbpool);
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
     *带dbpool的delete方法
     *@param 无
     *@return 无
     */
    public void delete(DbPool dbpool,String strComCode,String strQueryYear,String strChannelType,String strServiceArea) throws Exception
    {
      DBCYBComPara dbCYBComPara = new DBCYBComPara();
      dbCYBComPara.delete(dbpool,strComCode,strQueryYear,strChannelType,strServiceArea);
    }
    
    /**
     *不带dbpool的delete方法
     *@param 无
     *@return 无
     */
    public void delete(String strComCode,String strQueryYear,String strChannelType,String strServiceArea) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      try
      {
        dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
        dbpool.beginTransaction();
        delete(dbpool,strComCode,strQueryYear,strChannelType,strServiceArea);
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
     *@param iSEQNO SEQNO
     *@return 无
     */
    public void cancel(DbPool dbpool,String iSEQNO) throws Exception
    {
      String strSqlStatement = "";
      
      strSqlStatement = " DELETE FROM T_SPIS_CYBCOMPARA WHERE SEQNO= '" + iSEQNO + "'";
      
      dbpool.delete(strSqlStatement);
    }
      
    /**
     * 不带dbpool的删除方法
     *@param iSEQNO SEQNO
     *@return 无
     */
    public void cancel(String iSEQNO ) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      try
      {
        dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
        dbpool.beginTransaction();
        cancel(dbpool,iSEQNO);
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
     * 带dbpool根据SEQNO获取数据
     *@param iSEQNO SEQNO
     *@return 无
     */
    public void getData(String iSEQNO) throws Exception
    {
      DbPool dbpool = new DbPool();
      dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
      getData(dbpool,iSEQNO);
      dbpool.close();
    }
      
    /**
     * 不带dbpool根据SEQNO获取数据
     *@param dbpool 连接池
     *@param iSEQNO SEQNO
     *@return 无
     */
    public void getData(DbPool dbpool,String iSEQNO) throws Exception
    {
      String strWherePart = "";
      strWherePart = "SEQNO= '" + iSEQNO + "'";
      query(dbpool,strWherePart,0);
    }
    /**
     * 主函数
     * @param args 参数列表
     */
    public static void main(String[] args){
        
    }
}
