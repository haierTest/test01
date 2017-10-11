package com.sp.prpall.blsvr.misc;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.util.*;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.prpall.dbsvr.misc.DBPRisk;
import com.sp.prpall.schema.PRiskSchema;

/**
 * 定义P_RISK的BL类
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>@createdate 2015-04-01</p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLPRisk{
    private Vector schemas = new Vector();
    /**
     * 构造函数
     */       
    public BLPRisk(){
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
     *增加一条P_RISKSchema记录
     *@param iP_RISKSchema P_RISKSchema
     *@throws Exception
     */
    public void setArr(PRiskSchema iP_RISKSchema) throws Exception
    {
       try
       {
         schemas.add(iP_RISKSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *得到一条P_RISKSchema记录
     *@param index 下标
     *@return 一个P_RISKSchema对象
     *@throws Exception
     */
    public PRiskSchema getArr(int index) throws Exception
    {
     PRiskSchema p_RISKSchema = null;
       try
       {
        p_RISKSchema = (PRiskSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return p_RISKSchema;
     }
    /**
     *删除一条P_RISKSchema记录
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
      DBPRisk dbP_RISK = new DBPRisk();
      if (iLimitCount > 0 && dbP_RISK.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLP_RISK.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM P_RISK WHERE " + iWherePart; 
        schemas = dbP_RISK.findByConditions(strSqlStatement, bindValues);
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
      DBPRisk dbP_RISK = new DBPRisk();
      if (iLimitCount > 0 && dbP_RISK.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLP_RISK.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM P_RISK WHERE " + iWherePart; 
        schemas = dbP_RISK.findByConditions(dbpool, strSqlStatement, bindValues);
      }
    }
      
    /**
     *带dbpool的save方法
     *@param 无
     *@return 无
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPRisk dbP_RISK = new DBPRisk();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
      dbP_RISK.setSchema((PRiskSchema)schemas.get(i));
      dbP_RISK.insert(dbpool);
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
     *@param pOLICYNO POLICYNO
     *@return 无
     */
    public void cancel(DbPool dbpool, String pOLICYNO) throws Exception
    {
      String strSqlStatement = "";
      
      strSqlStatement = " DELETE FROM P_RISK WHERE pOLICYNO='" + pOLICYNO + "'";
      
      dbpool.delete(strSqlStatement);
    }
      
    /**
     * 不带dbpool的删除方法
     *@param pOLICYNO POLICYNO
     *@return 无
     */
    public void cancel(String pOLICYNO) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
      try
      {
        dbpool.beginTransaction();
        cancel(dbpool, pOLICYNO);
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
     *@param pOLICYNO POLICYNO
     *@return 无
     */
    public void getData(String pOLICYNO) throws Exception
    {
      DbPool dbpool = new DbPool();
      dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
      getData(dbpool, pOLICYNO);
      dbpool.close();
    }
      
    /**
     * 带dbpool根据主键获取数据
     *@param dbpool 连接池
     *@param pOLICYNO POLICYNO
     *@return 无
     */
    public void getData(DbPool dbpool, String pOLICYNO) throws Exception
    {
       String strWherePart = "";
       strWherePart = "pOLICYNO='" + pOLICYNO + "'";
       query(dbpool, strWherePart, 0, null);
    }
    /**
     * 主函数
     * @param args 参数列表
     */
    public static void main(String[] args){
        
    }
}
