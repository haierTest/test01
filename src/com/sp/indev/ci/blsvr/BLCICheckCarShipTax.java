package com.sp.indiv.ci.blsvr;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.util.*;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.indiv.ci.dbsvr.DBCICheckCarShipTax;
import com.sp.indiv.ci.schema.CICheckCarShipTaxSchema;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 定义CICheckCarShipTax的BL类
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>@createdate 2008-01-22</p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLCICheckCarShipTax{
    private Vector schemas = new Vector();
    Log logger = LogFactory.getLog(getClass());
    /**
     * 构造函数
     */       
    public BLCICheckCarShipTax(){
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
     *增加一条CICheckCarShipTaxSchema记录
     *@param iCICheckCarShipTaxSchema CICheckCarShipTaxSchema
     *@throws Exception
     */
    public void setArr(CICheckCarShipTaxSchema iCICheckCarShipTaxSchema) throws Exception
    {
       try
       {
         schemas.add(iCICheckCarShipTaxSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *得到一条CICheckCarShipTaxSchema记录
     *@param index 下标
     *@return 一个CICheckCarShipTaxSchema对象
     *@throws Exception
     */
    public CICheckCarShipTaxSchema getArr(int index) throws Exception
    {
     CICheckCarShipTaxSchema cICheckCarShipTaxSchema = null;
       try
       {
        cICheckCarShipTaxSchema = (CICheckCarShipTaxSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return cICheckCarShipTaxSchema;
     }
    /**
     *删除一条CICheckCarShipTaxSchema记录
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
      DBCICheckCarShipTax dbCICheckCarShipTax = new DBCICheckCarShipTax();
      if (iLimitCount > 0 && dbCICheckCarShipTax.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLCICheckCarShipTax.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM CICheckCarShipTax WHERE " + iWherePart; 
        schemas = dbCICheckCarShipTax.findByConditions(strSqlStatement);
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
      DBCICheckCarShipTax dbCICheckCarShipTax = new DBCICheckCarShipTax();
      if (iLimitCount > 0 && dbCICheckCarShipTax.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLCICheckCarShipTax.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM CICheckCarShipTax WHERE " + iWherePart; 
        schemas = dbCICheckCarShipTax.findByConditions(dbpool,strSqlStatement);
      }
    }
      
    /**
     *带dbpool的save方法
     *@param 无
     *@return 无
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBCICheckCarShipTax dbCICheckCarShipTax = new DBCICheckCarShipTax();
      
      int i = 0;
      
      logger.info("schemas.size()="+schemas.size());
      
      for(i = 0; i< schemas.size(); i++)
      {
      dbCICheckCarShipTax.setSchema((CICheckCarShipTaxSchema)schemas.get(i));
      dbCICheckCarShipTax.insert(dbpool);
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
     * 主函数
     * @param args 参数列表
     */
    public static void main(String[] args){
        
    }
}
