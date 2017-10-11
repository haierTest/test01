package com.sp.prpall.blsvr.jf;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.util.*;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.prpall.dbsvr.jf.DBPrpJmanageFeePackage;
import com.sp.prpall.schema.PrpJmanageFeePackageSchema;

/**
 * 定义管理费用打包表的BL类
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>@createdate 2007-01-31</p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLPrpJmanageFeePackage{
    private Vector schemas = new Vector();
    /**
     * 构造函数
     */       
    public BLPrpJmanageFeePackage(){
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
     *增加一条PrpJmanageFeePackageSchema记录
     *@param iPrpJmanageFeePackageSchema PrpJmanageFeePackageSchema
     *@throws Exception
     */
    public void setArr(PrpJmanageFeePackageSchema iPrpJmanageFeePackageSchema) throws Exception
    {
       try
       {
         schemas.add(iPrpJmanageFeePackageSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *得到一条PrpJmanageFeePackageSchema记录
     *@param index 下标
     *@return 一个PrpJmanageFeePackageSchema对象
     *@throws Exception
     */
    public PrpJmanageFeePackageSchema getArr(int index) throws Exception
    {
     PrpJmanageFeePackageSchema prpJmanageFeePackageSchema = null;
       try
       {
        prpJmanageFeePackageSchema = (PrpJmanageFeePackageSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpJmanageFeePackageSchema;
     }
    /**
     *删除一条PrpJmanageFeePackageSchema记录
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
      DBPrpJmanageFeePackage dbPrpJmanageFeePackage = new DBPrpJmanageFeePackage();
      if (iLimitCount > 0 && dbPrpJmanageFeePackage.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJmanageFeePackage.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpJmanageFeePackage WHERE " + iWherePart; 
        schemas = dbPrpJmanageFeePackage.findByConditions(strSqlStatement);
      }
    }
    
    /**
     *按照查询条件和记录数限制得到一组记录数，并将这组记录赋给schemas对象
     *@param iWherePart 查询条件(包括排序字句)
     *@throws UserException
     *@throws Exception
     */
    public void queryByLimitCount(String iWherePart) throws UserException,Exception
    {
    	int iLimitCount = Integer.parseInt(SysConst.getProperty("QUERY_LIMIT_COUNT").trim());
    	String strSqlStatement = "";
    	DBPrpJmanageFeePackage dbPrpJmanageFeePackage = new DBPrpJmanageFeePackage();
    	initArr();
    	String sql = " SELECT * FROM PrpJmanageFeePackage WHERE " + iWherePart;
    	strSqlStatement = " SELECT * FROM (" + sql + ") WHERE ROWNUM<=" + iLimitCount;
    	schemas = dbPrpJmanageFeePackage.findByConditions(strSqlStatement);
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
      DBPrpJmanageFeePackage dbPrpJmanageFeePackage = new DBPrpJmanageFeePackage();
      if (iLimitCount > 0 && dbPrpJmanageFeePackage.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJmanageFeePackage.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpJmanageFeePackage WHERE " + iWherePart; 
        schemas = dbPrpJmanageFeePackage.findByConditions(dbpool,strSqlStatement);
      }
    }
      
    /**
     *带dbpool的save方法
     *@param 无
     *@return 无
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpJmanageFeePackage dbPrpJmanageFeePackage = new DBPrpJmanageFeePackage();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
      dbPrpJmanageFeePackage.setSchema((PrpJmanageFeePackageSchema)schemas.get(i));
      dbPrpJmanageFeePackage.insert(dbpool);
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
     * 主函数
     * @param args 参数列表
     */
    public static void main(String[] args){
        
    }
}
