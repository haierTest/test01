package com.sp.prpall.blsvr.jf;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.util.*;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.prpall.dbsvr.jf.DBPrpJPowerRule;
import com.sp.prpall.schema.PrpJPowerRuleSchema;

/**
 * 定义PrpJPowerRule的BL类
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>@createdate 2014-09-29</p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLPrpJPowerRule{
    private Vector schemas = new Vector();
    /**
     * 构造函数
     */       
    public BLPrpJPowerRule(){ 
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
     *增加一条PrpJPowerRuleSchema记录
     *@param iPrpJPowerRuleSchema PrpJPowerRuleSchema
     *@throws Exception
     */
    public void setArr(PrpJPowerRuleSchema iPrpJPowerRuleSchema) throws Exception
    {
       try
       {
         schemas.add(iPrpJPowerRuleSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *得到一条PrpJPowerRuleSchema记录
     *@param index 下标
     *@return 一个PrpJPowerRuleSchema对象
     *@throws Exception
     */
    public PrpJPowerRuleSchema getArr(int index) throws Exception
    {
     PrpJPowerRuleSchema prpJPowerRuleSchema = null;
       try
       {
        prpJPowerRuleSchema = (PrpJPowerRuleSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpJPowerRuleSchema;
     }
    /**
     *删除一条PrpJPowerRuleSchema记录
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
     *@param bindValues 绑定参数
     *@throws UserException
     *@throws Exception
     */
    public void query(String iWherePart, ArrayList bindValues) throws UserException,Exception
    {
       this.query(iWherePart, Integer.parseInt(SysConst.getProperty("QUERY_LIMIT_COUNT").trim()), bindValues);
    }
    /**
     *按照查询条件和记录数限制得到一组记录数，并将这组记录赋给schemas对象
     *@param iWherePart 查询条件(包括排序字句)
     *@param iLimitCount 记录数限制(iLimitCount=0: 无限制)
     *@param bindValues 绑定参数
     *@throws UserException
     *@throws Exception
     */
    public void query(String iWherePart, int iLimitCount, ArrayList bindValues) throws UserException,Exception
    {
      String strSqlStatement = "";
      DBPrpJPowerRule dbPrpJPowerRule = new DBPrpJPowerRule();
      if (iLimitCount > 0 && dbPrpJPowerRule.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJPowerRule.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpJPowerRule WHERE " + iWherePart; 
        schemas = dbPrpJPowerRule.findByConditions(strSqlStatement, bindValues);
      }
    }
    /**
     *按照查询条件得到一组记录数，并将这组记录赋给schemas对象
     *@param dbpool 全局池
     *@param iWherePart 查询条件(包括排序字句)
     *@param bindValues 绑定参数
     *@throws UserException
     *@throws Exception
     */
    public void query(DbPool dbpool, String iWherePart, ArrayList bindValues) throws UserException,Exception
    {
       this.query(dbpool,iWherePart,Integer.parseInt(SysConst.getProperty("QUERY_LIMIT_COUNT").trim()),bindValues);
    }
    /**
     *按照查询条件和记录数限制得到一组记录数，并将这组记录赋给schemas对象
     *@param dbpool 全局池
     *@param iWherePart 查询条件(包括排序字句)
     *@param iLimitCount 记录数限制(iLimitCount=0: 无限制)
     *@param bindValues 绑定参数
     *@throws UserException
     *@throws Exception
     */
    public void query(DbPool dbpool, String iWherePart, int iLimitCount, ArrayList bindValues) throws UserException,Exception
    {
      String strSqlStatement = "";
      DBPrpJPowerRule dbPrpJPowerRule = new DBPrpJPowerRule();
      if (iLimitCount > 0 && dbPrpJPowerRule.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJPowerRule.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpJPowerRule WHERE " + iWherePart; 
        schemas = dbPrpJPowerRule.findByConditions(dbpool, strSqlStatement, bindValues);
      }
    }
      
    /**
     *带dbpool的save方法
     *@param 无
     *@return 无
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpJPowerRule dbPrpJPowerRule = new DBPrpJPowerRule();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
      dbPrpJPowerRule.setSchema((PrpJPowerRuleSchema)schemas.get(i));
      dbPrpJPowerRule.insert(dbpool);
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
      
      dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
      
      try
      {
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
     *@param iD ID
     *@return 无
     */
    public void cancel(DbPool dbpool, String iD) throws Exception
    {
      String strSqlStatement = "";
      
      strSqlStatement = " DELETE FROM PrpJPowerRule WHERE iD='" + iD + "'";
      
      dbpool.delete(strSqlStatement);
    }
      
    /**
     * 不带dbpool的删除方法
     *@param iD ID
     *@return 无
     */
    public void cancel(String iD) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
      try
      {
        dbpool.beginTransaction();
        cancel(dbpool, iD);
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
     *@param iD ID
     *@return 无
     */
    public void getData(String iD) throws Exception
    {
      DbPool dbpool = new DbPool();
      dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
      getData(dbpool, iD);
      dbpool.close();
    }
      
    /**
     * 带dbpool根据主键获取数据
     *@param dbpool 连接池
     *@param iD ID
     *@return 无
     */
    public void getData(DbPool dbpool, String iD) throws Exception
    {
       String strWherePart = "";
       strWherePart = "iD='" + iD + "'";
       query(dbpool, strWherePart, 0, null);
    }
    /**
     * 主函数
     * @param args 参数列表
     */
    public static void main(String[] args){
        
    }
}
