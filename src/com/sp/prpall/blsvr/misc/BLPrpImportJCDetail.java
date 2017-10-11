package com.sp.prpall.blsvr.misc;

import java.util.ArrayList;
import java.util.Vector;

import com.sp.prpall.dbsvr.misc.DBPrpImportJCDetail;
import com.sp.prpall.schema.PrpImportJCDetailSchema;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;


public class BLPrpImportJCDetail{
    private Vector schemas = new Vector();
    /**
     * 构造函数
     */       
    public BLPrpImportJCDetail(){
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
     *增加一条PrpImportJCDetailSchema记录
     *@param iPrpImportJCDetailSchema PrpImportJCDetailSchema
     *@throws Exception
     */
    public void setArr(PrpImportJCDetailSchema iPrpImportJCDetailSchema) throws Exception
    {
       try
       {
         schemas.add(iPrpImportJCDetailSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *得到一条PrpImportJCDetailSchema记录
     *@param index 下标
     *@return 一个PrpImportJCDetailSchema对象
     *@throws Exception
     */
    public PrpImportJCDetailSchema getArr(int index) throws Exception
    {
     PrpImportJCDetailSchema prpImportJCDetailSchema = null;
       try
       {
        prpImportJCDetailSchema = (PrpImportJCDetailSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpImportJCDetailSchema;
     }
    /**
     *删除一条PrpImportJCDetailSchema记录
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
      DBPrpImportJCDetail dbPrpImportJCDetail = new DBPrpImportJCDetail();
      if (iLimitCount > 0 && dbPrpImportJCDetail.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpImportJCDetail.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpImportJCDetail WHERE " + iWherePart; 
        schemas = dbPrpImportJCDetail.findByConditions(strSqlStatement);
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
      DBPrpImportJCDetail dbPrpImportJCDetail = new DBPrpImportJCDetail();
      if (iLimitCount > 0 && dbPrpImportJCDetail.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpImportJCDetail.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpImportJCDetail WHERE " + iWherePart; 
        schemas = dbPrpImportJCDetail.findByConditions(dbpool,strSqlStatement);
      }
    }
      
    /**
     *带dbpool的save方法
     *@param 无
     *@return 无
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpImportJCDetail dbPrpImportJCDetail = new DBPrpImportJCDetail();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
      dbPrpImportJCDetail.setSchema((PrpImportJCDetailSchema)schemas.get(i));
      dbPrpImportJCDetail.insert(dbpool);
      }
    }
      

    /**
     * 先删后插的方式进行XXXXX存。带dbpool
     * @param dbpool
     * @param batchNo
     * @param serialNo
     * @throws Exception
     */
    public void saveAfterCancel(DbPool dbpool,String batchNo,String serialNo) throws Exception
    {
    	 cancel(dbpool,batchNo,serialNo);  
         save(dbpool);
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
     * 先删后插的方式进行XXXXX存。不带dbpool
     * @param dbpool
     * @param batchNo
     * @param serialNo
     * @throws Exception
     */
    public void saveAfterCancel(String batchNo,String serialNo) throws Exception
    {
        DbPool dbpool = new DbPool();
        
        dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
        
        try
        {
          dbpool.beginTransaction();
          saveAfterCancel(dbpool,batchNo,serialNo);
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
     *@param batchNo 批次号
     *@param serialNo 序号
     *@return 无
     */
    public void cancel(DbPool dbpool,String batchNo,String serialNo) throws Exception
    {
      String strSqlStatement = "";      
      strSqlStatement = " DELETE FROM PrpImportJCDetail WHERE  BatchNo = ?" +
                           " And SerialNo = ?";
      dbpool.prepareInnerStatement(strSqlStatement);
      int index = 1;
      dbpool.setString(index++,batchNo);
      dbpool.setString(index++,serialNo);
      dbpool.executePreparedUpdate();
      dbpool.closePreparedStatement();   
    }
      
    /**
     * 不带dbpool的删除方法
     *@param batchNo 批次号
     *@param serialNo 序号
     *@return 无
     */
    public void cancel(String batchNo,String serialNo ) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
      try
      {
        dbpool.beginTransaction();
        cancel(dbpool,batchNo,serialNo);
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
     * 带dbpool根据batchNo,serialNo获取数据
     *@param batchNo 批次号
     *@param serialNo 序号
     *@return 无
     */
    public void getData(String batchNo,String serialNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
      getData(dbpool,batchNo,serialNo);
      dbpool.close();
    }
      
    /**
     * 不带dbpool根据batchNo,serialNo获取数据
     *@param dbpool 连接池
     *@param batchNo 批次号
     *@param serialNo 序号
     *@return 无
     */
    public void getData(DbPool dbpool,String batchNo,String serialNo) throws Exception
    {
        
        
        
        
        String  strWherePart = " BatchNo= ?  AND SerialNo = ? ";
        ArrayList arrWhereValue = new ArrayList();
        arrWhereValue.add(batchNo);
        arrWhereValue.add(serialNo);
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
        DBPrpImportJCDetail dbPrpImportJCDetail = new DBPrpImportJCDetail();
        if (iLimitCount > 0 && dbPrpImportJCDetail.getCount(dbpool,iWherePart,iWhereValue) > iLimitCount)
        {
            throw new UserException(-98,-1003,"BLPrpImportJCDetail.query");
        }else
        {
            initArr();
            strSqlStatement = " SELECT * FROM PrpImportJCDetail WHERE " + iWherePart;
            schemas = dbPrpImportJCDetail.findByConditions(dbpool,strSqlStatement,iWhereValue);
        }
    }
    
    /**
     * 主函数
     * @param args 参数列表
     */
    public static void main(String[] args){
        
    }
}
