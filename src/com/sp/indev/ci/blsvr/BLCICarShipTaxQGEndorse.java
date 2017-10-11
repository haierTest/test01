package com.sp.indiv.ci.blsvr;

import java.util.ArrayList;
import java.util.Vector;

import com.sp.indiv.ci.dbsvr.DBCICarShipTaxQGEndorse;
import com.sp.indiv.ci.schema.CICarShipTaxQGEndorseSchema;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;

/**
 * 定义CICarShipTaxQGEndorse-全国车船税基本类型批改查询表的BL类
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>@createdate 2009-04-09</p>
 * @author Zhouxianli(JavaTools v1.0)
 * @updateauthor yangkun(JavaTools v1.1 - v1.2)
 * @lastversion v1.2
 */
public class BLCICarShipTaxQGEndorse{
    private Vector schemas = new Vector();
    /**
     * 构造函数
     */       
    public BLCICarShipTaxQGEndorse(){
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
     *增加一条CICarShipTaxQGEndorseSchema记录
     *@param iCICarShipTaxQGEndorseSchema CICarShipTaxQGEndorseSchema
     *@throws Exception
     */
    public void setArr(CICarShipTaxQGEndorseSchema iCICarShipTaxQGEndorseSchema) throws Exception
    {
       try
       {
         schemas.add(iCICarShipTaxQGEndorseSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *得到一条CICarShipTaxQGEndorseSchema记录
     *@param index 下标
     *@return 一个CICarShipTaxQGEndorseSchema对象
     *@throws Exception
     */
    public CICarShipTaxQGEndorseSchema getArr(int index) throws Exception
    {
       CICarShipTaxQGEndorseSchema ciCarShipTaxQGEndorseSchema = null;
       try
       {
         ciCarShipTaxQGEndorseSchema = (CICarShipTaxQGEndorseSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return ciCarShipTaxQGEndorseSchema;
     }
    /**
     *删除一条CICarShipTaxQGEndorseSchema记录
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
      DBCICarShipTaxQGEndorse dbCICarShipTaxQGEndorse = new DBCICarShipTaxQGEndorse();
      if (iLimitCount > 0 && dbCICarShipTaxQGEndorse.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLCICarShipTaxQGEndorse.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM CICarShipTaxQGEndorse WHERE " + iWherePart; 
        schemas = dbCICarShipTaxQGEndorse.findByConditions(strSqlStatement);
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
      DBCICarShipTaxQGEndorse dbCICarShipTaxQGEndorse = new DBCICarShipTaxQGEndorse();
      if (iLimitCount > 0 && dbCICarShipTaxQGEndorse.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLCICarShipTaxQGEndorse.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM CICarShipTaxQGEndorse WHERE " + iWherePart; 
        schemas = dbCICarShipTaxQGEndorse.findByConditions(dbpool,strSqlStatement);
      }
    }
      
    /**
     *带dbpool的save方法
     *@param 无
     *@return 无
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBCICarShipTaxQGEndorse dbCICarShipTaxQGEndorse = new DBCICarShipTaxQGEndorse();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
      dbCICarShipTaxQGEndorse.setSchema((CICarShipTaxQGEndorseSchema)schemas.get(i));
      dbCICarShipTaxQGEndorse.insert(dbpool);
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
      
      dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
      
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
     *带dbpool的删除方法
     *@param dbpool    连接池
     *@param iEndorseNo XX号
     *@return 无
     */
    
    public void cancel(DbPool dbpool,String iEndorseNo) throws Exception
    {




    	String strSqlStatement = "DELETE FROM CICarShipTaxQGEndorse WHERE EndorseNo= ?";
    	dbpool.prepareInnerStatement(strSqlStatement);
    	dbpool.setString(1, iEndorseNo);
    	dbpool.executePreparedUpdate();
    	dbpool.closePreparedStatement();
     
    }
    
      
    /**
     * 不带dbpool的删除方法
     *@param iEndorseNo XX号
     *@return 无
     */
    public void cancel(String iDemandNo ) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
      try
      {
        dbpool.beginTransaction();
        cancel(dbpool,iDemandNo);
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
     * 不带dbpool根据XX号获取数据
     *@param iEndorseNo XX号
     *@return 无
     */
    public void getData(String iDemandNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
      getData(dbpool,iDemandNo);
      dbpool.close();
    }
      
    /**
     * 带dbpool根据XX号获取数据
     *@param dbpool 连接池
     *@param iEndorseNo XX号
     *@return 无
     */
    public void getData(DbPool dbpool,String iDemandNo) throws Exception
    {
        
        
        
        
        String strWherePart = " DemandNo= ? ";
        ArrayList arrWhereValue = new ArrayList();
        arrWhereValue.add(iDemandNo);
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
        DBCICarShipTaxQGEndorse dbCICarShipTaxQGEndorse = new DBCICarShipTaxQGEndorse();
        if (iLimitCount > 0 && dbCICarShipTaxQGEndorse.getCount(dbpool,iWherePart,iWhereValue) > iLimitCount)
        {
            throw new UserException(-98,-1003,"BLCICarShipTaxQGEndorse.query");
        }else
        {
            initArr();
            strSqlStatement = " SELECT * FROM CICarShipTaxQGEndorse WHERE " + iWherePart;
            schemas = dbCICarShipTaxQGEndorse.findByConditions(dbpool,strSqlStatement,iWhereValue);
        }
    }
 
    /**
     * 不带dbpool根据XX号获取数据
     *@param iEndorseNo XX号
     *@return 无
     */
    public void getDataByEndorseNo(String iEndorseNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
      getDataByEndorseNo(dbpool,iEndorseNo);
      dbpool.close();
    }
      
    /**
     * 带dbpool根据XX号获取数据
     *@param dbpool 连接池
     *@param iEndorseNo XX号
     *@return 无
     */
    public void getDataByEndorseNo(DbPool dbpool,String iEndorseNo) throws Exception
    {
    String strWherePart = "";
    strWherePart = "EndorseNo= '" + iEndorseNo + "'";
    query(dbpool,strWherePart,0);
    }
    

    /**
     *带dbpool的删除方法
     *@param dbpool    连接池
     *@param null null
     *@return 无
     */
    public void cancelByEndorseNo(DbPool dbpool,String iEndorseNo) throws Exception
    {
      String strSqlStatement = "";
      
      strSqlStatement = " DELETE FROM CICarShipTaxQGDemand WHERE EndorseNo= '" + iEndorseNo + "'";
      
      String strDataSourceSwitch=SysConfig.getProperty("DB_SPLIT_SWITCH");
      DbPool dbpool1=new DbPool();
      try {
          if("1".equals(strDataSourceSwitch)){
              dbpool1.open("platformNewDataSource");
              dbpool1.beginTransaction();
              dbpool1.delete(strSqlStatement);
              dbpool1.commitTransaction();
          }else{
              dbpool.delete(strSqlStatement);
          }
      } catch (Exception e) {
          if("1".equals(strDataSourceSwitch)){
              dbpool1.rollbackTransaction();
          }
          throw e;
      }finally{
          dbpool1.close();
      }
      
    }
    
    /**
     *带dbpool的删除方法
     *@param dbpool    连接池
     *@param iProposalNo 批单号
     **@param iProposalNo 批改查询码
     *@return 无
     */
    public void cancel(DbPool dbpool,String iEndorseNo, String iDemandNo) throws Exception
    {
      String strSqlStatement = "";

      strSqlStatement = " DELETE FROM CICarShipTaxQGDemand WHERE EndorseNo= '" + iEndorseNo + "'" + 
      					" AND DemandNo != '" + iDemandNo + "' ";
      
      String strDataSourceSwitch=SysConfig.getProperty("DB_SPLIT_SWITCH");
      DbPool dbpool1=new DbPool();
      try {
          if("1".equals(strDataSourceSwitch)){
              dbpool1.open("platformNewDataSource");
              dbpool1.beginTransaction();
              dbpool1.delete(strSqlStatement);
              dbpool1.commitTransaction();
          }else{
              dbpool.delete(strSqlStatement);
          }
      } catch (Exception e) {
          if("1".equals(strDataSourceSwitch)){
              dbpool1.rollbackTransaction();
          }
          throw e;
      }finally{
          dbpool1.close();
      }
      
    }
    /**
     * 主函数
     * @param args 参数列表
     */
    public static void main(String[] args){
        
    }
}
