package com.sp.prpall.blsvr.misc;

import java.util.ArrayList;
import java.util.Vector;

import com.sp.prpall.dbsvr.misc.DBTBRisk;
import com.sp.prpall.schema.TBRiskSchema;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;

/**
 * 定义TBRisk的BL类
 * 从pdm中取数据库信息,根据数据库表生成对应的BL类
 * <p>Title: SinosoftJavaTools v1.4</p>
 * <p>Description: 中科软Java源码生成工具</p>
 * <p>Company: Sinosoft</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>@createdate 2010-09-03</p>
 * @FirstAuthor: Zhouxianli(JavaTools v1.0)
 * @SecondAuthor: Yangkun(JavaTools v1.1->v1.2->v1.3->v1.4)
 * @LastVersion: v1.4.3
 * @UpdateList: 1.新增对CIXXXXX平台类的生成处理;
 *              2.修正特殊表生成类cancel()、getdata()方法时获取pdm表主键入参为null的问题;
 *              3.修正特殊表生成类部分import类为null的问题;
 *              4.新增log4j日志bl层类自动初始化;
 *              5.getData方法新增try、catch、finally异常处理;
 */
public class BLTBRisk{
    private Vector schemas = new Vector();
    /**
     * 构造函数
     */       
    public BLTBRisk(){
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
     *增加一条TBRiskSchema记录
     *@param iTBRiskSchema TBRiskSchema
     *@throws Exception
     */
    public void setArr(TBRiskSchema iTBRiskSchema) throws Exception
    {
      try
      {
        schemas.add(iTBRiskSchema);
      }
      catch(Exception e)
      {
        throw e;
      }
    }
    /**
     *得到一条TBRiskSchema记录
     *@param index 下标
     *@return 一个TBRiskSchema对象
     *@throws Exception
     */
    public TBRiskSchema getArr(int index) throws Exception
    {
      TBRiskSchema tBRiskSchema = null;
       try
       {
         tBRiskSchema = (TBRiskSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return tBRiskSchema;
     }
    /**
     *删除一条TBRiskSchema记录
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
      DBTBRisk dbTBRisk = new DBTBRisk();
      if (iLimitCount > 0 && dbTBRisk.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLTBRisk.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM TB_Risk WHERE " + iWherePart; 
        schemas = dbTBRisk.findByConditions(strSqlStatement);
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
      DBTBRisk dbTBRisk = new DBTBRisk();
      if (iLimitCount > 0 && dbTBRisk.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLTBRisk.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM TB_Risk WHERE " + iWherePart; 
        schemas = dbTBRisk.findByConditions(dbpool,strSqlStatement);
      }
    }
      
    /**
     *带dbpool的save方法
     *@param 无
     *@return 无
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBTBRisk dbTBRisk = new DBTBRisk();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
        dbTBRisk.setSchema((TBRiskSchema)schemas.get(i));
        dbTBRisk.insert(dbpool);
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
     *@param iPolicyNo PolicyNo
     *@return 无
     */
    public void cancel(DbPool dbpool,String iPolicyNo) throws Exception
    {
      String strSqlStatement = "";
      
      strSqlStatement = " DELETE FROM TB_Risk WHERE PolicyNo= '" + iPolicyNo + "'";
      
      dbpool.delete(strSqlStatement);
    }
      
    /**
     * 不带dbpool的删除方法
     *@param iPolicyNo PolicyNo
     *@return 无
     */
    public void cancel(String iPolicyNo ) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      try
      {
        dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
        dbpool.beginTransaction();
        cancel(dbpool,iPolicyNo);
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
     * 带dbpool根据PolicyNo获取数据
     *@param iPolicyNo PolicyNo
     *@return 无
     */
    public void getData(String iPolicyNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      try
      {
        dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
        getData(dbpool,iPolicyNo);
      }
      catch (Exception e)
      {
        
      }
      finally
      {
        dbpool.close();
      }
    }
      
    /**
     * 不带dbpool根据PolicyNo获取数据
     *@param dbpool 连接池
     *@param iPolicyNo PolicyNo
     *@return 无
     */
    public void getData(DbPool dbpool,String iPolicyNo) throws Exception
    {
      String strWherePart = "PolicyNo= ?";
      ArrayList arrWhereValue = new ArrayList();
      arrWhereValue.add(iPolicyNo);
      query(dbpool,strWherePart,arrWhereValue,0);
    }
    /**
     *按照查询条件和记录数限制得到一组记录数，并将这组记录赋给schemas对象
     *@param dbpool 全局池
     *@param iWherePart    查询条件(包括排序字句)
     *@param arrWhereValue 查询条件各字段值
     *@param iLimitCount   记录数限制(iLimitCount=0: 无限制)
     *@throws UserException
     *@throws Exception
     */
    public void query(DbPool dbpool,String iWherePart,ArrayList arrWhereValue, int iLimitCount
    		) throws UserException,Exception
    {
      String strSqlStatement = "";
      DBTBRisk dbTBRisk = new DBTBRisk();
      if (iLimitCount > 0 && dbTBRisk.getCount(dbpool,iWherePart,arrWhereValue) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLTBRisk.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM TB_Risk WHERE " + iWherePart; 
        schemas = dbTBRisk.findByConditions(dbpool,strSqlStatement,arrWhereValue);
      }
    }
    /**
     * 主函数
     * @param args 参数列表
     */
    public static void main(String[] args){
        
    }
}
