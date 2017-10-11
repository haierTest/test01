package com.sp.prpall.blsvr.misc;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.util.*;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.prpall.dbsvr.misc.DBPrpTimeDiffRecord;
import com.sp.prpall.schema.PrpTimeDiffRecordSchema;

/**
 * 定义PrpTimeDiffRecord的BL类
 * 从pdm中取数据库信息,根据数据库表生成对应的BL类
 * <p>Title: SinosoftJavaTools v1.4</p>
 * <p>Description: 中科软Java源码生成工具</p>
 * <p>Company: Sinosoft</p>
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>@createdate 2013-07-18</p>
 * @FirstAuthor: Zhouxianli(JavaTools v1.0)
 * @SecondAuthor: Yangkun(JavaTools v1.1->v1.2->v1.3->v1.4)
 * @LastVersion: v1.4.2
 * @UpdateList: 1.新增对CIXXXXX平台类的生成处理;
 *              2.修正特殊表生成类cancel()、getdata()方法时获取pdm表主键入参为null的问题;
 *              3.修正特殊表生成类部分import类为null的问题;
 *              4.新增log4j日志bl层类自动初始化;
 */
public class BLPrpTimeDiffRecord{
    
    private Vector schemas = new Vector();
    /**
     * 构造函数
     */       
    public BLPrpTimeDiffRecord(){
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
     *增加一条PrpTimeDiffRecordSchema记录
     *@param iPrpTimeDiffRecordSchema PrpTimeDiffRecordSchema
     *@throws Exception
     */
    public void setArr(PrpTimeDiffRecordSchema iPrpTimeDiffRecordSchema) throws Exception
    {
      try
      {
        schemas.add(iPrpTimeDiffRecordSchema);
      }
      catch(Exception e)
      {
        throw e;
      }
    }
    /**
     *得到一条PrpTimeDiffRecordSchema记录
     *@param index 下标
     *@return 一个PrpTimeDiffRecordSchema对象
     *@throws Exception
     */
    public PrpTimeDiffRecordSchema getArr(int index) throws Exception
    {
      PrpTimeDiffRecordSchema prpTimeDiffRecordSchema = null;
       try
       {
         prpTimeDiffRecordSchema = (PrpTimeDiffRecordSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpTimeDiffRecordSchema;
     }
    /**
     *删除一条PrpTimeDiffRecordSchema记录
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
      DBPrpTimeDiffRecord dbPrpTimeDiffRecord = new DBPrpTimeDiffRecord();
      if (iLimitCount > 0 && dbPrpTimeDiffRecord.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpTimeDiffRecord.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpTimeDiffRecord WHERE " + iWherePart; 
        schemas = dbPrpTimeDiffRecord.findByConditions(strSqlStatement);
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
      DBPrpTimeDiffRecord dbPrpTimeDiffRecord = new DBPrpTimeDiffRecord();
      if (iLimitCount > 0 && dbPrpTimeDiffRecord.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpTimeDiffRecord.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpTimeDiffRecord WHERE " + iWherePart; 
        schemas = dbPrpTimeDiffRecord.findByConditions(dbpool,strSqlStatement);
      }
    }
      
    /**
     *带dbpool的save方法
     *@param 无
     *@return 无
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpTimeDiffRecord dbPrpTimeDiffRecord = new DBPrpTimeDiffRecord();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
        dbPrpTimeDiffRecord.setSchema((PrpTimeDiffRecordSchema)schemas.get(i));
        dbPrpTimeDiffRecord.insert(dbpool);
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
    	  e.printStackTrace();
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
     *@param iProposalNo XX单号
     *@return 无
     */
    public void cancel(DbPool dbpool,String iProposalNo) throws Exception
    {
      String strSqlStatement = "";
      
      strSqlStatement = " DELETE FROM PrpTimeDiffRecord WHERE ProposalNo= '" + iProposalNo + "'";
      
      dbpool.delete(strSqlStatement);
    }
      
    /**
     * 不带dbpool的删除方法
     *@param iProposalNo XX单号
     *@return 无
     */
    public void cancel(String iProposalNo ) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      try
      {
        dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
        dbpool.beginTransaction();
        cancel(dbpool,iProposalNo);
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
     * 带dbpool根据XX单号获取数据
     *@param iProposalNo XX单号
     *@return 无
     */
    public void getData(String iProposalNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
      getData(dbpool,iProposalNo);
      dbpool.close();
    }
      
    /**
     * 不带dbpool根据XX单号获取数据
     *@param dbpool 连接池
     *@param iProposalNo XX单号
     *@return 无
     */
    public void getData(DbPool dbpool,String iProposalNo) throws Exception
    {
      String strWherePart = "";
      strWherePart = "ProposalNo= '" + iProposalNo + "'";
      query(dbpool,strWherePart,0);
    }
    /**
     * 主函数
     * @param args 参数列表
     */
    public static void main(String[] args){
        
    }
}
