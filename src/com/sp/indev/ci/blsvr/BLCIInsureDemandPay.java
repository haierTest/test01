package com.sp.indiv.ci.blsvr;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.util.*;

import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.indiv.ci.dbsvr.DBCIInsureDemand;
import com.sp.indiv.ci.dbsvr.DBCIInsureDemandPay;
import com.sp.indiv.ci.schema.CIInsureDemandPaySchema;

/**
 * 定义XX查询XXXXX表-CIInsureDemandPay的BL类
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>@createdate 2006-06-14</p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLCIInsureDemandPay{
    private Vector schemas = new Vector();
    /**
     * 构造函数
     */       
    public BLCIInsureDemandPay(){
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
     *增加一条CIInsureDemandPaySchema记录
     *@param iCIInsureDemandPaySchema CIInsureDemandPaySchema
     *@throws Exception
     */
    public void setArr(CIInsureDemandPaySchema iCIInsureDemandPaySchema) throws Exception
    {
       try
       {
         schemas.add(iCIInsureDemandPaySchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *得到一条CIInsureDemandPaySchema记录
     *@param index 下标
     *@return 一个CIInsureDemandPaySchema对象
     *@throws Exception
     */
    public CIInsureDemandPaySchema getArr(int index) throws Exception
    {
     CIInsureDemandPaySchema cIInsureDemandPaySchema = null;
       try
       {
        cIInsureDemandPaySchema = (CIInsureDemandPaySchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return cIInsureDemandPaySchema;
     }
    /**
     *删除一条CIInsureDemandPaySchema记录
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
     *根据查询SQL调用绑定变量方法
     *@param iWherePart 查询条件(包括排序字句)
     *@param iWhereValue 查询条件值
     *@throws UserException
     *@throws Exception
     */
    public void query(String iWherePart,ArrayList iWhereValue) throws UserException,Exception
    {
   	   DbPool dbpool = new DbPool();
       try {
           
           String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
           if ("1".equals(strSwitch)) {
               dbpool.open("platformNewDataSource");            
           } else {
               dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
           }
           
           query(dbpool, iWherePart, iWhereValue, Integer.parseInt(SysConst.getProperty("QUERY_LIMIT_COUNT").trim()));
       } catch (Exception e) 
       {
       	throw e;
       } finally {
           dbpool.close();
       }    
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
      DBCIInsureDemandPay dbCIInsureDemandPay = new DBCIInsureDemandPay();
      if (iLimitCount > 0 && dbCIInsureDemandPay.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLCIInsureDemandPay.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM CIInsureDemandPay WHERE " + iWherePart; 
        schemas = dbCIInsureDemandPay.findByConditions(strSqlStatement);
      }
    }
    
    
    /**
     *按照查询条件得到一组记录数，并将这组记录赋给schemas对象
     *@param iWherePart 查询条件(包括排序字句)
     *@throws UserException
     *@throws Exception
     */
    public void queryHistory(String iWherePart) throws UserException,Exception
    {
       this.queryHistory(iWherePart,Integer.parseInt(SysConst.getProperty("QUERY_LIMIT_COUNT").trim()));
    }
    /**
     *按照查询条件和记录数限制得到一组记录数，并将这组记录赋给schemas对象
     *@param iWherePart 查询条件(包括排序字句)
     *@param iLimitCount 记录数限制(iLimitCount=0: 无限制)
     *@throws UserException
     *@throws Exception
     */
    public void queryHistory(String iWherePart,int iLimitCount) throws UserException,Exception
    {
      String strSqlStatement = "";
      DBCIInsureDemandPay dbCIInsureDemandPay = new DBCIInsureDemandPay();
      if (iLimitCount > 0 && dbCIInsureDemandPay.getCountHistory(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLCIInsureDemandPay.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM CIInsureDemandPay WHERE " + iWherePart; 
        schemas = dbCIInsureDemandPay.findByConditionsHistory(strSqlStatement);
      }
    }    
    
    /**
     *按照查询条件得到一组记录数，并将这组记录赋给schemas对象,先从生产环境获取，如取不到相关数据，则从历史信息库获取
     *@param iWherePart 查询条件(包括排序字句)
     *@throws UserException
     *@throws Exception
     */
    public void queryCurrentAndHistory(String iWherePart) throws UserException,Exception
    {
       this.query(iWherePart);
       
       if(this.getSize() == 0){
    	   this.queryHistory(iWherePart);
       }
    }
    /**
     *按照查询条件得到一组记录数，绑定变量。
     *@param iWherePart 查询条件(包括排序字句)
     *@throws UserException
     *@throws Exception
     */
    public void queryCurrentAndHistory(String iWherePart,ArrayList iWhereValue) throws UserException,Exception
    {
       
       this.query(iWherePart,iWhereValue);
       
       if(this.getSize() == 0){
    	   this.queryHistory(iWherePart,iWhereValue);
       }
    }
    /**
     *按照查询条件得到一组记录数，绑定变量
     *@param iWherePart 查询条件(包括排序字句)
     *@param iWhereValue 查询条件值
     *@throws UserException
     *@throws Exception
     */
    public void queryHistory(String iWherePart,ArrayList iWhereValue) throws UserException,Exception
    {
       DbPool dbpool = new DbPool();
       
       try {
           
           String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
           if ("1".equals(strSwitch)) {
               dbpool.open("platformNewDataSource");            
           } else {
               dbpool.open(SysConfig.CONST_HISTORYDATASOURCE);
           }
           
           query(dbpool,iWherePart,iWhereValue,Integer.parseInt(SysConst.getProperty("QUERY_LIMIT_COUNT").trim()));
        }catch (Exception e){
           	throw e;
        }finally{
            dbpool.close();
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
      DBCIInsureDemandPay dbCIInsureDemandPay = new DBCIInsureDemandPay();
      if (iLimitCount > 0 && dbCIInsureDemandPay.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLCIInsureDemandPay.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM CIInsureDemandPay WHERE " + iWherePart; 
        schemas = dbCIInsureDemandPay.findByConditions(dbpool,strSqlStatement);
      }
    }
      
    /**
     *带dbpool的save方法
     *@param 无
     *@return 无
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBCIInsureDemandPay dbCIInsureDemandPay = new DBCIInsureDemandPay();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
      dbCIInsureDemandPay.setSchema((CIInsureDemandPaySchema)schemas.get(i));
      dbCIInsureDemandPay.insert(dbpool);
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
     *按照查询条件和记录数限制得到一组记录数，并将这组记录赋给schemas对象
     *@author wanghe 2011-01-04
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
        DBCIInsureDemandPay dbCIInsureDemandPay = new DBCIInsureDemandPay();
        if (iLimitCount > 0 && dbCIInsureDemandPay.getCount(dbpool,iWherePart,iWhereValue) > iLimitCount)
        {
            throw new UserException(-98,-1003,"BLCIInsureDemandPay.query");
        }else
        {
            initArr();
            strSqlStatement = " SELECT * FROM CIInsureDemandPay WHERE " + iWherePart;
            schemas = dbCIInsureDemandPay.findByConditions(dbpool,strSqlStatement,iWhereValue);
        }
    }
    /**
     * 主函数
     * @param args 参数列表
     */
    public static void main(String[] args){
        
    }
}
