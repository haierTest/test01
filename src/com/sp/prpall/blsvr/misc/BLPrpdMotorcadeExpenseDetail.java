package com.sp.prpall.blsvr.misc;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.*;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.prpall.dbsvr.misc.DBPrpdMotorcadeExpenseDetail;
import com.sp.prpall.schema.PrpdMotorcadeExpenseDetailSchema;

public class BLPrpdMotorcadeExpenseDetail{
    private final Log logger = LogFactory.getLog(getClass());
    private Vector schemas = new Vector();
    /**
     * 构造函数
     */       
    public BLPrpdMotorcadeExpenseDetail(){
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
     *增加一条PrpdMotorcadeExpenseDetailSchema记录
     *@param PrpdMotorcadeExpenseDetailSchema PrpdMotorcadeExpenseDetailSchema
     *@throws Exception
     */
    public void setArr(PrpdMotorcadeExpenseDetailSchema iPrpdMotorcadeExpenseDetailSchema) throws Exception
    {
      try
      {
        schemas.add(iPrpdMotorcadeExpenseDetailSchema);
      }
      catch(Exception e)
      {
        throw e;
      }
    }
    /**
     *得到一条PrpdMotorcadeExpenseDetailSchema记录
     *@param index 下标
     *@return 一个PrpdMotorcadeExpenseDetailSchema对象
     *@throws Exception
     */
    public PrpdMotorcadeExpenseDetailSchema getArr(int index) throws Exception
    {
        PrpdMotorcadeExpenseDetailSchema prpdMotorcadeExpenseDetailSchema = null;
       try
       {
         prpdMotorcadeExpenseDetailSchema = (PrpdMotorcadeExpenseDetailSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpdMotorcadeExpenseDetailSchema;
     }
    /**
     *删除一条PrpdMotorcadeExpenseDetailSchema记录
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
      DBPrpdMotorcadeExpenseDetail dbPrpdMotorcadeExpenseDetail = new DBPrpdMotorcadeExpenseDetail();
      if (iLimitCount > 0 && dbPrpdMotorcadeExpenseDetail.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpdMotorcadeExpenseDetail.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpdMotorcadeExpenseDetail WHERE " + iWherePart; 
        schemas = dbPrpdMotorcadeExpenseDetail.findByConditions(strSqlStatement);
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
      DBPrpdMotorcadeExpenseDetail dbPrpdMotorcadeExpenseDetail = new DBPrpdMotorcadeExpenseDetail();
      if (iLimitCount > 0 && dbPrpdMotorcadeExpenseDetail.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpdMotorcadeExpenseDetail.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpdMotorcadeExpenseDetail WHERE " + iWherePart; 
        schemas = dbPrpdMotorcadeExpenseDetail.findByConditions(dbpool,strSqlStatement);
      }
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
          DBPrpdMotorcadeExpenseDetail dbPrpdMotorcadeExpenseDetail = new DBPrpdMotorcadeExpenseDetail();
          if (iLimitCount > 0 && dbPrpdMotorcadeExpenseDetail.getCount(dbpool,iWherePart,iWhereValue) > iLimitCount)
          {
              throw new UserException(-98,-1003,"BLPrpdMotorcadeExpenseDetail.query");
          }else
          {
              initArr();
              strSqlStatement = " SELECT * FROM PrpdMotorcadeExpenseDetail WHERE " + iWherePart;
              schemas = dbPrpdMotorcadeExpenseDetail.findByConditions(dbpool,strSqlStatement,iWhereValue);
          }
      }
      
    /**
     *带dbpool的save方法
     *@param 无
     *@return 无
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpdMotorcadeExpenseDetail dbPrpdMotorcadeExpenseDetail = new DBPrpdMotorcadeExpenseDetail();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
        dbPrpdMotorcadeExpenseDetail.setSchema((PrpdMotorcadeExpenseDetailSchema)schemas.get(i));
        dbPrpdMotorcadeExpenseDetail.insert(dbpool);
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
    	
    	if("1".equals(SysConfig.getProperty("PLAT_FORM_NEW_DATA_SOURCE"))){
    		dbpool.open(SysConfig.CONST_PLATFORMNEWDATASOURCE);
    	}else{       		
        dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
    	}
    	
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
     *@param iContractNo CONTRACTNO
     *@return 无
     */
    public void cancel(DbPool dbpool,String iContractNo) throws Exception
    {
      String strSqlStatement = "";
      
      strSqlStatement = " DELETE FROM PrpdMotorcadeExpenseDetail WHERE ContractNo= '" + iContractNo + "'";
      
      dbpool.delete(strSqlStatement);
    }
      
    /**
     * 不带dbpool的删除方法
     *@param iContractNo CONTRACTNO
     *@return 无
     */
    public void cancel(String iContractNo ) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      try
      {
      	
      	if("1".equals(SysConfig.getProperty("PLAT_FORM_NEW_DATA_SOURCE"))){
      		dbpool.open(SysConfig.CONST_PLATFORMNEWDATASOURCE);
      	}else{       		
        dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
      	}
      	
        dbpool.beginTransaction();
        cancel(dbpool,iContractNo);
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
     * 带dbpool根据CONTRACTNO获取数据
     *@param iContractNo CONTRACTNO
     *@return 无
     */
    public void getData(String iContractNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
      getData(dbpool,iContractNo);
      dbpool.close();
    }
      
    /**
     * 不带dbpool根据CONTRACTNO获取数据
     *@param dbpool 连接池
     *@param iContractNo CONTRACTNO
     *@return 无
     */
    public void getData(DbPool dbpool,String iContractNo) throws Exception
    {
      String strWherePart = "";
      strWherePart = "ContractNo= '" + iContractNo + "'";
      query(dbpool,strWherePart,0);
    }
    /**
     *带dbpool的update方法
     *@param 无
     *@return 无
     */
    public void update(DbPool dbpool) throws Exception{
        DBPrpdMotorcadeExpenseDetail dbPrpdMotorcadeExpenseDetail = new DBPrpdMotorcadeExpenseDetail();
        int i = 0;
        for(i = 0; i< schemas.size(); i++){
            dbPrpdMotorcadeExpenseDetail.setSchema((PrpdMotorcadeExpenseDetailSchema)schemas.get(i));
            dbPrpdMotorcadeExpenseDetail.update(dbpool);
        }
    }
    
   /**
    *不带dbpool的更新方法
    *@param 无
    *@return 无
    */
    public void update() throws Exception{
        DbPool dbpool = new DbPool();
        try{
        	
        	if("1".equals(SysConfig.getProperty("PLAT_FORM_NEW_DATA_SOURCE"))){
        		dbpool.open(SysConfig.CONST_PLATFORMNEWDATASOURCE);
        	}else{       		
          dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        	}
        	
          dbpool.beginTransaction();
          update(dbpool);
          dbpool.commitTransaction();
        }catch (Exception e){
          dbpool.rollbackTransaction();
          throw e;
        }finally{
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
