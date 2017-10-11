package com.sp.indiv.ci.blsvr;

import java.util.ArrayList;
import java.util.Vector;

import com.sp.indiv.ci.dbsvr.DBCIInsurePmDriver;
import com.sp.indiv.ci.dbsvr.DBCIInsurePmVehicle;
import com.sp.indiv.ci.schema.CIInsurePmDriverSchema;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;

/**
 * 定义XX查询主表-CIInsurePmDriver的BL类
 * 
 * @createdate 2014-01-24
 * @author  fanjiangtao 2014-01-24
 * 
 */
public class BLCIInsurePmDriver{
    private Vector schemas = new Vector();
    /**
     * 构造函数
     */       
    public BLCIInsurePmDriver(){
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
     *增加一条CIInsurePmDriverSchema记录
     *@param CIInsurePmDriverSchema iCIInsurePmDriverSchema
     *@throws Exception
     */
    public void setArr(CIInsurePmDriverSchema iCIInsurePmDriverSchema) throws Exception
    {
       try
       {
         schemas.add(iCIInsurePmDriverSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *得到一条CIInsurePmDriverSchema记录
     *@param index 下标
     *@return 一个CIInsurePmDriverSchema对象
     *@throws Exception
     */
    public CIInsurePmDriverSchema getArr(int index) throws Exception
    {
        CIInsurePmDriverSchema cIInsurePmDriverSchema = null;
       try
       {
           cIInsurePmDriverSchema = (CIInsurePmDriverSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return cIInsurePmDriverSchema;
     }
    /**
     *删除一条CIInsurePmDriverSchema记录
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
      DBCIInsurePmDriver dbCIInsurePmDriver = new DBCIInsurePmDriver();
      if (iLimitCount > 0 && dbCIInsurePmDriver.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLCIInsurePmDriver.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM CIInsurePmDriver WHERE " + iWherePart; 
        schemas = dbCIInsurePmDriver.findByConditions(strSqlStatement);
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
      DBCIInsurePmDriver dbCIInsurePmDriver = new DBCIInsurePmDriver();
      if (iLimitCount > 0 && dbCIInsurePmDriver.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLCIInsurePmDriver.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM CIInsurePmDriver WHERE " + iWherePart; 
        schemas = dbCIInsurePmDriver.findByConditions(dbpool,strSqlStatement);
      }
    }
    /**
     *带dbpool的save方法
     *@param 无
     *@return 无
     */
    public void save(DbPool dbpool) throws Exception
    {
        DBCIInsurePmDriver dbCIInsurePmDriver = new DBCIInsurePmDriver();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
          dbCIInsurePmDriver.setSchema((CIInsurePmDriverSchema)schemas.get(i));
          dbCIInsurePmDriver.insert(dbpool);
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
            String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
            if ("1".equals(strSwitch)) {
                dbpool.open("platformNewDataSource");            
            } else {
                dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
            }
        dbpool.beginTransaction();
        save(dbpool);
        dbpool.commitTransaction(); 
      }
      catch (Exception e)
      {e.printStackTrace();
        dbpool.rollbackTransaction();
      }
      finally {
        dbpool.close();
      }
    }
    
    public void cancel(String LicenseNo ) throws Exception
    {
      DbPool dbpool = new DbPool();

       try {
        dbpool.open("platformNewDataSource");
        dbpool.beginTransaction();
        cancel(dbpool,LicenseNo);
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
    
    public void cancel(DbPool dbpool,String LicenseNo) throws Exception
    {
	  
  	     String strSqlStatement = " DELETE FROM CIInsurePmDriver WHERE LicenseNo= ?";
    	 dbpool.prepareInnerStatement(strSqlStatement);
    	 dbpool.setString(1, LicenseNo);
  	     dbpool.executePreparedUpdate();
  	     dbpool.closePreparedStatement();
    }
    
    /**
     * 不带dbpool根据XX单号获取数据
     *@param demandno 
     *@return 无
     */
      public void getData(String LicenseNo) throws Exception
      {
          DbPool dbpool = new DbPool();
          try {
            dbpool.open("platformNewDataSource");            
            getData(dbpool, LicenseNo);
          } catch (Exception e) {
        	  e.printStackTrace();
          } finally {
              dbpool.close();
          }
      }

    /**
     * 带dbpool根据XX单号获取数据
     *@param dbpool 连接池
     *@param iDemandNo 
     *@return 无
     */
    public void getData(DbPool dbpool,String LicenseNo) throws Exception
    {
      String strWherePart = " LicenseNo= ? ";
      ArrayList arrWhereValue = new ArrayList();
      arrWhereValue.add(LicenseNo);
      query(dbpool, strWherePart, arrWhereValue, 0);
    }
    
    /**
     *按照查询条件和记录数限制得到一组记录数，并将这组记录赋给schemas对象
     *@author wangchuanzhong 20100602
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
        DBCIInsurePmDriver dbCIInsurePmDriver = new DBCIInsurePmDriver();
        if (iLimitCount > 0 && dbCIInsurePmDriver.getCount(dbpool,iWherePart,iWhereValue) > iLimitCount)
        {
            throw new UserException(-98,-1003,"BLCIInsurePmDriver.query");
        }else
        {
            initArr();
            strSqlStatement = " SELECT * FROM CIInsurePmDriver WHERE " + iWherePart;
            schemas = dbCIInsurePmDriver.findByConditions(dbpool,strSqlStatement,iWhereValue);
        }
    }
    /**
     * 主函数
     * @param args 参数列表
     */
    public static void main(String[] args){
        
    }
}
