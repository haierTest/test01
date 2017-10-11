package com.sp.prpall.blsvr.misc;

import java.util.ArrayList;
import java.util.Vector;

import com.sp.prpall.dbsvr.misc.DBPrpCertificateInsured;
import com.sp.prpall.schema.PrpCertificateInsuredSchema;
import com.sp.utility.SysConfig; 
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;

/**
 * 定义华夏存单被XX人信息表的BL类 
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>@createdate 2015-06-26</p> 
 * @author BLGenerator
 * @version 1.0
 */
public class BLPrpCertificateInsured{ 
    private Vector schemas = new Vector();
    /**
     * 构造函数
     */       
    public BLPrpCertificateInsured(){
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
     *增加一条PrpCertificateInsuredSchema记录
     *@param iPrpCertificateInsuredSchema PrpCertificateInsuredSchema
     *@throws Exception
     */
    public void setArr(PrpCertificateInsuredSchema iPrpCertificateInsuredSchema) throws Exception
    {
       try
       {
         schemas.add(iPrpCertificateInsuredSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *得到一条PrpCertificateInsuredSchema记录
     *@param index 下标
     *@return 一个PrpCertificateInsuredSchema对象
     *@throws Exception
     */
    public PrpCertificateInsuredSchema getArr(int index) throws Exception
    {
     PrpCertificateInsuredSchema prpCertificateInsuredSchema = null;
       try
       {
        prpCertificateInsuredSchema = (PrpCertificateInsuredSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpCertificateInsuredSchema;
     }
    /**
     *删除一条PrpCertificateInsuredSchema记录
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
      DBPrpCertificateInsured dbPrpCertificateInsured = new DBPrpCertificateInsured();
      if (iLimitCount > 0 && dbPrpCertificateInsured.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpCertificateInsured.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpCertificateInsured WHERE " + iWherePart; 
        schemas = dbPrpCertificateInsured.findByConditions(strSqlStatement);
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
      DBPrpCertificateInsured dbPrpCertificateInsured = new DBPrpCertificateInsured();
      if (iLimitCount > 0 && dbPrpCertificateInsured.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpCertificateInsured.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpCertificateInsured WHERE " + iWherePart; 
        schemas = dbPrpCertificateInsured.findByConditions(dbpool, strSqlStatement);
      }
    }
      
    /**
     *带dbpool的save方法
     *@param 无
     *@return 无
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpCertificateInsured dbPrpCertificateInsured = new DBPrpCertificateInsured();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
      dbPrpCertificateInsured.setSchema((PrpCertificateInsuredSchema)schemas.get(i));
      dbPrpCertificateInsured.insert(dbpool);
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
     *@param serialNo 序号
     *@return 无
     */
    public void cancel(DbPool dbpool, String serialNo) throws Exception
    {
      String strSqlStatement = "";
      
      strSqlStatement = " DELETE FROM PrpCertificateInsured WHERE serialNo='" + serialNo + "'";
      
      dbpool.delete(strSqlStatement);
    }
      
    /**
     * 不带dbpool的删除方法
     *@param serialNo 序号
     *@return 无
     */
    public void cancel(String serialNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      try
      {
    	dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        dbpool.beginTransaction();
        cancel(dbpool, serialNo);
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
     *@param serialNo 序号
     *@return 无
     */
    public void getData(String serialNo) throws Exception
    {
      DbPool dbpool = new DbPool();
  	  try {
  		  dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
  		  getData(dbpool, serialNo);
	  } catch (Exception e) {
	  }finally{
		  dbpool.close();
	  }
    }
      
    /**
     * 带dbpool根据主键获取数据
     *@param dbpool 连接池
     *@param serialNo 序号
     *@return 无
     */
    public void getData(DbPool dbpool, String serialNo) throws Exception
    {
       String strWherePart = "";
       strWherePart = "serialNo='" + serialNo + "'";
       query(dbpool, strWherePart, 0);
    }
    /**
     * 主函数
     * @param args 参数列表
     */
    public static void main(String[] args){
        
    }
}
