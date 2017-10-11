package com.sp.indiv.ci.blsvr;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.util.*;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.indiv.ci.dbsvr.DBCICheckCarShipTaxDetail;
import com.sp.indiv.ci.schema.CICheckCarShipTaxDetailSchema;

/**
 * 定义CICheckCarShipTaxDetail的BL类
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>@createdate 2008-01-22</p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLCICheckCarShipTaxDetail{
    private Vector schemas = new Vector();
    /**
     * 构造函数
     */       
    public BLCICheckCarShipTaxDetail(){
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
     *增加一条CICheckCarShipTaxDetailSchema记录
     *@param iCICheckCarShipTaxDetailSchema CICheckCarShipTaxDetailSchema
     *@throws Exception
     */
    public void setArr(CICheckCarShipTaxDetailSchema iCICheckCarShipTaxDetailSchema) throws Exception
    {
       try
       {
         schemas.add(iCICheckCarShipTaxDetailSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *得到一条CICheckCarShipTaxDetailSchema记录
     *@param index 下标
     *@return 一个CICheckCarShipTaxDetailSchema对象
     *@throws Exception
     */
    public CICheckCarShipTaxDetailSchema getArr(int index) throws Exception
    {
     CICheckCarShipTaxDetailSchema cICheckCarShipTaxDetailSchema = null;
       try
       {
        cICheckCarShipTaxDetailSchema = (CICheckCarShipTaxDetailSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return cICheckCarShipTaxDetailSchema;
     }
    /**
     *删除一条CICheckCarShipTaxDetailSchema记录
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
      DBCICheckCarShipTaxDetail dbCICheckCarShipTaxDetail = new DBCICheckCarShipTaxDetail();
      if (iLimitCount > 0 && dbCICheckCarShipTaxDetail.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLCICheckCarShipTaxDetail.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM CICheckCarShipTaxDetail WHERE " + iWherePart; 
        schemas = dbCICheckCarShipTaxDetail.findByConditions(strSqlStatement);
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
      DBCICheckCarShipTaxDetail dbCICheckCarShipTaxDetail = new DBCICheckCarShipTaxDetail();
      if (iLimitCount > 0 && dbCICheckCarShipTaxDetail.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLCICheckCarShipTaxDetail.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM CICheckCarShipTaxDetail WHERE " + iWherePart; 
        schemas = dbCICheckCarShipTaxDetail.findByConditions(dbpool,strSqlStatement);
      }
    }
      
    /**
     *带dbpool的save方法
     *@param 无
     *@return 无
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBCICheckCarShipTaxDetail dbCICheckCarShipTaxDetail = new DBCICheckCarShipTaxDetail();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
      dbCICheckCarShipTaxDetail.setSchema((CICheckCarShipTaxDetailSchema)schemas.get(i));
      dbCICheckCarShipTaxDetail.insert(dbpool);
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
