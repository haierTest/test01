package com.sp.indiv.ci.blsvr;

import java.util.ArrayList;
import java.util.Vector;

import com.sp.indiv.ci.dbsvr.DBCICheckCarShipTaxDetailQG;
import com.sp.indiv.ci.schema.CICheckCarShipTaxDetailQGSchema;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;

/**
 * 定义CICheckCarShipTaxDetailQG的BL类
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>@createdate 2009-06-02</p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLCICheckCarShipTaxDetailQG{
    private Vector schemas = new Vector();
    /**
     * 构造函数
     */       
    public BLCICheckCarShipTaxDetailQG(){
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
     *增加一条CICheckCarShipTaxDetailQGSchema记录
     *@param iCICheckCarShipTaxDetailQGSchema CICheckCarShipTaxDetailQGSchema
     *@throws Exception
     */
    public void setArr(CICheckCarShipTaxDetailQGSchema iCICheckCarShipTaxDetailQGSchema) throws Exception
    {
       try
       {
         schemas.add(iCICheckCarShipTaxDetailQGSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *得到一条CICheckCarShipTaxDetailQGSchema记录
     *@param index 下标
     *@return 一个CICheckCarShipTaxDetailQGSchema对象
     *@throws Exception
     */
    public CICheckCarShipTaxDetailQGSchema getArr(int index) throws Exception
    {
     CICheckCarShipTaxDetailQGSchema cICheckCarShipTaxDetailQGSchema = null;
       try
       {
        cICheckCarShipTaxDetailQGSchema = (CICheckCarShipTaxDetailQGSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return cICheckCarShipTaxDetailQGSchema;
     }
    /**
     *删除一条CICheckCarShipTaxDetailQGSchema记录
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
      DBCICheckCarShipTaxDetailQG dbCICheckCarShipTaxDetailQG = new DBCICheckCarShipTaxDetailQG();
      if (iLimitCount > 0 && dbCICheckCarShipTaxDetailQG.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLCICheckCarShipTaxDetailQG.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM CICheckCarShipTaxDetailQG WHERE " + iWherePart; 
        schemas = dbCICheckCarShipTaxDetailQG.findByConditions(strSqlStatement);
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
      DBCICheckCarShipTaxDetailQG dbCICheckCarShipTaxDetailQG = new DBCICheckCarShipTaxDetailQG();
      if (iLimitCount > 0 && dbCICheckCarShipTaxDetailQG.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLCICheckCarShipTaxDetailQG.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM CICheckCarShipTaxDetailQG WHERE " + iWherePart; 
        schemas = dbCICheckCarShipTaxDetailQG.findByConditions(dbpool,strSqlStatement);
      }
    }
      
    /**
     *带dbpool的save方法
     *@param 无
     *@return 无
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBCICheckCarShipTaxDetailQG dbCICheckCarShipTaxDetailQG = new DBCICheckCarShipTaxDetailQG();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
      dbCICheckCarShipTaxDetailQG.setSchema((CICheckCarShipTaxDetailQGSchema)schemas.get(i));
      dbCICheckCarShipTaxDetailQG.insert(dbpool);
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
     *带dbpool的删除方法
     *@param dbpool    连接池
     *@param ConFirmSequenceNo XX确认码/批改确认码
     *@return 无
     */
    public void cancel(DbPool dbpool,String iConFirmSequenceNo) throws Exception
    {




    	String strSqlStatement = " DELETE FROM CICheckCarShipTaxDetailQG WHERE ConFirmSequenceNo= ?";
    	dbpool.prepareInnerStatement(strSqlStatement);
    	dbpool.setString(1, iConFirmSequenceNo);
    	dbpool.executePreparedUpdate();
    	dbpool.closePreparedStatement();
     
    }
      
    /**
     * 不带dbpool的删除方法
     *@param iConFirmSequenceNo XX确认码/批改确认码
     *@return 无
     */
    public void cancel(String iConFirmSequenceNo ) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
      try
      {
        dbpool.beginTransaction();
        cancel(dbpool,iConFirmSequenceNo);
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
     * 带dbpool根据iConFirmSequenceNo获取数据
     *@param iConFirmSequenceNo XX确认码/批改确认码
     *@return 无
     */
    public void getData(String iConFirmSequenceNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
      getData(dbpool,iConFirmSequenceNo);
      dbpool.close();
    }
      
    /**
     * 不带dbpool根据iConFirmSequenceNo获取数据
     *@param dbpool 连接池
     *@param iConFirmSequenceNo XX确认码/批改确认码
     *@return 无
     */
    public void getData(DbPool dbpool,String iConFirmSequenceNo) throws Exception
    {
        
        
        
        
        
        String strWherePart = " ConFirmSequenceNo= ? ";
        ArrayList arrWhereValue = new ArrayList();
        arrWhereValue.add(iConFirmSequenceNo);
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
        DBCICheckCarShipTaxDetailQG dbCICheckCarShipTaxDetailQG = new DBCICheckCarShipTaxDetailQG();
        if (iLimitCount > 0 && dbCICheckCarShipTaxDetailQG.getCount(dbpool,iWherePart,iWhereValue) > iLimitCount)
        {
            throw new UserException(-98,-1003,"BLCICheckCarShipTaxDetailQG.query");
        }else
        {
            initArr();
            strSqlStatement = " SELECT * FROM CICheckCarShipTaxDetailQG WHERE " + iWherePart;
            schemas = dbCICheckCarShipTaxDetailQG.findByConditions(dbpool,strSqlStatement,iWhereValue);
        }
    }
    
    /**
     * 主函数
     * @param args 参数列表
     */
    public static void main(String[] args){
        
    }
}
