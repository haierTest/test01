package com.sp.prpall.blsvr.misc;


import java.util.*;

import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.prpall.dbsvr.misc.DBBankInterface_Ext;
import com.sp.prpall.schema.BankInterface_ExtSchema;

/**
 * 定义BankInterface_Ext的BL类
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>@createdate 2014-01-07</p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLBankInterface_Ext{
    private Vector schemas = new Vector();
    /**
     * 构造函数
     */       
    public BLBankInterface_Ext(){
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
     *增加一条BankInterface_ExtSchema记录
     *@param iBankInterface_ExtSchema BankInterface_ExtSchema
     *@throws Exception
     */
    public void setArr(BankInterface_ExtSchema iBankInterface_ExtSchema) throws Exception
    {
       try
       {
         schemas.add(iBankInterface_ExtSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *得到一条BankInterface_ExtSchema记录
     *@param index 下标
     *@return 一个BankInterface_ExtSchema对象
     *@throws Exception
     */
    public BankInterface_ExtSchema getArr(int index) throws Exception
    {
     BankInterface_ExtSchema bankInterface_ExtSchema = null;
       try
       {
        bankInterface_ExtSchema = (BankInterface_ExtSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return bankInterface_ExtSchema;
     }
    /**
     *删除一条BankInterface_ExtSchema记录
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
      DBBankInterface_Ext dbBankInterface_Ext = new DBBankInterface_Ext();
      if (iLimitCount > 0 && dbBankInterface_Ext.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLBankInterface_Ext.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM BankInterface_Ext WHERE " + iWherePart; 
        schemas = dbBankInterface_Ext.findByConditions(strSqlStatement, bindValues);
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
      DBBankInterface_Ext dbBankInterface_Ext = new DBBankInterface_Ext();
      if (iLimitCount > 0 && dbBankInterface_Ext.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLBankInterface_Ext.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM BankInterface_Ext WHERE " + iWherePart; 
        schemas = dbBankInterface_Ext.findByConditions(dbpool, strSqlStatement, bindValues);
      }
    }
      
    /**
     *带dbpool的save方法
     *@param 无
     *@return 无
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBBankInterface_Ext dbBankInterface_Ext = new DBBankInterface_Ext();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
      dbBankInterface_Ext.setSchema((BankInterface_ExtSchema)schemas.get(i));
      dbBankInterface_Ext.insert(dbpool);
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
     * 带dbpool的删除方法
     *@param dbpool    连接池
     *@param policyNo PolicyNo
     *@return 无
     */
    public void cancel(DbPool dbpool, String policyNo) throws Exception
    {
      String strSqlStatement = "";
      
      strSqlStatement = " DELETE FROM BankInterface_Ext WHERE policyNo='" + policyNo + "'";
      
      dbpool.delete(strSqlStatement);
    }
      
    /**
     * 不带dbpool的删除方法
     *@param policyNo PolicyNo
     *@return 无
     */
    public void cancel(String policyNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      try
      {
    	dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
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
     *@param policyNo PolicyNo
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
     *@param policyNo PolicyNo
     *@return 无
     */
    public void getData(DbPool dbpool, String policyNo) throws Exception
    {
       String strWherePart = " PolicyNo= ? ";
       ArrayList arrWhereValue = new ArrayList();
       arrWhereValue.add(policyNo);
       query(dbpool, strWherePart, arrWhereValue, 0);
    }
    
    /**
     *按照查询条件和记录数限制得到一组记录数，并将这组记录赋给schemas对象
     *@param dbpool      全局池
     *@param iWherePart  查询条件,传入条件均已绑定变量形式，问号个数与属性值个数一致
     *@param iWhereValue 查询条件各字段值
     *@param iLimitCount 记录数限制(iLimitCount=0: 无限制)
     *@throws UserException
     *@throws Exception
     */
    public void query(DbPool dbpool,String iWherePart,ArrayList iWhereValue, int iLimitCount) throws UserException,Exception
    {
        String strSqlStatement = "";
        DBBankInterface_Ext dbBankInterface_Ext = new DBBankInterface_Ext();
        if (iLimitCount > 0 && dbBankInterface_Ext.getCount(dbpool,iWherePart,iWhereValue) > iLimitCount)
        {
            throw new UserException(-98,-1003,"BLBankInterface_Ext.query");
        }else
        {
            initArr();
            strSqlStatement = " SELECT * FROM BANKINTERFACE_EXT WHERE " + iWherePart;
            schemas = dbBankInterface_Ext.findByConditions(dbpool,strSqlStatement,iWhereValue);
        }
    }
    
    
    
    /**
     * 主函数
     * @param args 参数列表
     */
    public static void main(String[] args){
        
    }
}
