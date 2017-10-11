package com.sp.prpall.blsvr.misc;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.util.*;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.prpall.dbsvr.misc.DBTBExpense;
import com.sp.prpall.schema.TBExpenseSchema;

/**
 * 定义TbExpense的BL类
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>@createdate 2015-04-21</p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLTBExpense{
    private Vector schemas = new Vector();
    /**
     * 构造函数
     */       
    public BLTBExpense(){
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
     *增加一条TbExpenseSchema记录
     *@param iTbExpenseSchema TbExpenseSchema
     *@throws Exception
     */
    public void setArr(TBExpenseSchema iTbExpenseSchema) throws Exception
    {
       try
       {
         schemas.add(iTbExpenseSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *得到一条TbExpenseSchema记录
     *@param index 下标
     *@return 一个TbExpenseSchema对象
     *@throws Exception
     */
    public TBExpenseSchema getArr(int index) throws Exception
    {
     TBExpenseSchema tbExpenseSchema = null;
       try
       {
        tbExpenseSchema = (TBExpenseSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return tbExpenseSchema;
     }
    /**
     *删除一条TbExpenseSchema记录
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
      DBTBExpense dbTbExpense = new DBTBExpense();
      if (iLimitCount > 0 && dbTbExpense.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLTbExpense.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM TB_EXPENSE WHERE " + iWherePart; 
        schemas = dbTbExpense.findByConditions(strSqlStatement, bindValues);
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
      DBTBExpense dbTbExpense = new DBTBExpense();
      if (iLimitCount > 0 && dbTbExpense.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLTbExpense.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM TB_EXPENSE WHERE " + iWherePart; 
        schemas = dbTbExpense.findByConditions(dbpool, strSqlStatement, bindValues);
      }
    }
      
    /**
     *带dbpool的save方法
     *@param 无
     *@return 无
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBTBExpense dbTbExpense = new DBTBExpense();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
      dbTbExpense.setSchema((TBExpenseSchema)schemas.get(i));
      dbTbExpense.insert(dbpool);
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
     *@param policyNo policyNo
     *@return 无
     */
    public void cancel(DbPool dbpool, String policyNo) throws Exception
    {
      String strSqlStatement = "";
      
      strSqlStatement = " DELETE FROM TB_EXPENSE WHERE policyNo='" + policyNo + "'";
      
      dbpool.delete(strSqlStatement);
    }
      
    /**
     * 不带dbpool的删除方法
     *@param policyNo policyNo
     *@return 无
     */
    public void cancel(String policyNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
      try
      {
        dbpool.beginTransaction();
        cancel(dbpool, policyNo);
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
     *@param policyNo policyNo
     *@return 无
     */
    public void getData(String policyNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
      getData(dbpool, policyNo);
      dbpool.close();
    }
      
    /**
     * 带dbpool根据主键获取数据
     *@param dbpool 连接池
     *@param policyNo policyNo
     *@return 无
     */
    public void getData(DbPool dbpool, String policyNo) throws Exception
    {
       String strWherePart = "";
       strWherePart = "policyNo='" + policyNo + "'";
       query(dbpool, strWherePart, 0, null);
    }
    /**
     * 主函数
     * @param args 参数列表
     */
    public static void main(String[] args){
        
    }
}
