package com.sp.prpall.blsvr.misc;

import java.util.ArrayList;
import java.util.Vector;

import com.sp.prpall.dbsvr.misc.DBPrpCertificate;
import com.sp.prpall.schema.PrpCertificateSchema;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst; 
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException; 
 
/**
 * 定义华夏存单XX资料信息表的BL类
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>@createdate 2015-06-26</p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLPrpCertificate{
    private Vector schemas = new Vector(); 
    /**
     * 构造函数
     */       
    public BLPrpCertificate(){
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
     *增加一条PrpCertificateSchema记录
     *@param iPrpCertificateSchema PrpCertificateSchema
     *@throws Exception
     */
    public void setArr(PrpCertificateSchema iPrpCertificateSchema) throws Exception
    {
       try
       {
         schemas.add(iPrpCertificateSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *得到一条PrpCertificateSchema记录
     *@param index 下标
     *@return 一个PrpCertificateSchema对象
     *@throws Exception
     */
    public PrpCertificateSchema getArr(int index) throws Exception
    {
     PrpCertificateSchema prpCertificateSchema = null;
       try
       {
        prpCertificateSchema = (PrpCertificateSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpCertificateSchema;
     }
    /**
     *删除一条PrpCertificateSchema记录
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
       this.query(iWherePart, Integer.parseInt(SysConst.getProperty("QUERY_LIMIT_COUNT").trim()));
    }
    /**
     *按照查询条件和记录数限制得到一组记录数，并将这组记录赋给schemas对象
     *@param iWherePart 查询条件(包括排序字句)
     *@param iLimitCount 记录数限制(iLimitCount=0: 无限制)
     *@throws UserException
     *@throws Exception
     */
    public void query(String iWherePart, int iLimitCount) throws UserException,Exception
    {
      String strSqlStatement = "";
      DBPrpCertificate dbPrpCertificate = new DBPrpCertificate();
      if (iLimitCount > 0 && dbPrpCertificate.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpCertificate.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpCertificate WHERE " + iWherePart; 
        schemas = dbPrpCertificate.findByConditions(strSqlStatement);
      }
    }
    /**
     *按照查询条件得到一组记录数，并将这组记录赋给schemas对象
     *@param dbpool 全局池
     *@param iWherePart 查询条件(包括排序字句)
     *@throws UserException
     *@throws Exception
     */
    public void query(DbPool dbpool, String iWherePart) throws UserException,Exception
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
    public void query(DbPool dbpool, String iWherePart, int iLimitCount) throws UserException,Exception
    {
      String strSqlStatement = "";
      DBPrpCertificate dbPrpCertificate = new DBPrpCertificate();
      if (iLimitCount > 0 && dbPrpCertificate.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpCertificate.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpCertificate WHERE " + iWherePart; 
        schemas = dbPrpCertificate.findByConditions(dbpool, strSqlStatement);
      }
    }
      
    /**
     *带dbpool的save方法
     *@param 无
     *@return 无
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpCertificate dbPrpCertificate = new DBPrpCertificate();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
      dbPrpCertificate.setSchema((PrpCertificateSchema)schemas.get(i));
      dbPrpCertificate.insert(dbpool);
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
    	dbpool.open(SysConfig.CONST_DDCCDATASOURCE);  
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
     *@param productCode 机构产品代码
     *@return 无
     */
    public void cancel(DbPool dbpool, String productCode) throws Exception
    {
      String strSqlStatement = "";
      strSqlStatement = " DELETE FROM PrpCertificate WHERE productCode='" + productCode + "'";
      dbpool.delete(strSqlStatement);
    }
      
    /**
     * 不带dbpool的删除方法
     *@param productCode 机构产品代码
     *@return 无
     */
    public void cancel(String productCode) throws Exception
    {
      DbPool dbpool = new DbPool();
      try
      {
    	dbpool.open(SysConfig.CONST_DDCCDATASOURCE);  
        dbpool.beginTransaction();
        cancel(dbpool, productCode);
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
     *@param productCode 机构产品代码
     *@return 无
     */
    public void getData(String productCode) throws Exception
    {
      DbPool dbpool = new DbPool();
      try {
    	  dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
          getData(dbpool, productCode);
	  } catch (Exception e) {
	  }finally{
		  dbpool.close();
	  }
      
      
    }
      
    /**
     * 带dbpool根据主键获取数据
     *@param dbpool 连接池
     *@param productCode 机构产品代码
     *@return 无
     */
    public void getData(DbPool dbpool, String productCode) throws Exception
    {
       String strWherePart = "";
       strWherePart = "productCode='" + productCode + "'";
       query(dbpool, strWherePart, 0);
    }
    /**
     * 主函数
     * @param args 参数列表
     */
    public static void main(String[] args){
        
    }
}
