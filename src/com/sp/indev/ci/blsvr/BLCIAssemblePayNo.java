package com.sp.indiv.ci.blsvr;

import java.util.ArrayList;
import java.util.Vector;

import com.sp.indiv.ci.dbsvr.DBCIAssemblePayNo;
import com.sp.indiv.ci.schema.CIAssemblePayNoSchema;
import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;

/**
 * 定义车船税汇总完税凭证表的BL类
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>@createdate 2008-01-21</p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLCIAssemblePayNo{
    private Vector schemas = new Vector();
    /**
     * 构造函数
     */       
    public BLCIAssemblePayNo(){
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
     *增加一条CIAssemblePayNoSchema记录
     *@param ciAssemblePayNoSchema CIAssemblePayNoSchema
     *@throws Exception
     */
    public void setArr(CIAssemblePayNoSchema ciAssemblePayNoSchema) throws Exception
    {
       try
       {
         schemas.add(ciAssemblePayNoSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *得到一条CIAssemblePayNoSchema记录
     *@param index 下标
     *@return 一个CIAssemblePayNoSchema对象
     *@throws Exception
     */
    public CIAssemblePayNoSchema getArr(int index) throws Exception
    {
    	CIAssemblePayNoSchema ciAssemblePayNoSchema = null;
       try
       {
    	   ciAssemblePayNoSchema = (CIAssemblePayNoSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return ciAssemblePayNoSchema;
     }
    /**
     *删除一条CIAssemblePayNoSchema记录
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
       this.query(iWherePart,Integer.parseInt(SysConfig.getProperty("QUERY_LIMIT_COUNT").trim()));
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
      DBCIAssemblePayNo dbCIAssemblePayNo = new DBCIAssemblePayNo();
      if (iLimitCount > 0 && dbCIAssemblePayNo.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLCIAssemblePayNo.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM CIAssemblePayNo WHERE " + iWherePart; 
        schemas = dbCIAssemblePayNo.findByConditions(strSqlStatement);
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
       this.query(dbpool,iWherePart,Integer.parseInt(SysConfig.getProperty("QUERY_LIMIT_COUNT").trim()));
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
      DBCIAssemblePayNo dbCIAssemblePayNo = new DBCIAssemblePayNo();
      if (iLimitCount > 0 && dbCIAssemblePayNo.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLCIAssemblePayNo.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM CIAssemblePayNo WHERE " + iWherePart; 
        schemas = dbCIAssemblePayNo.findByConditions(dbpool,strSqlStatement);
      }
    }
      
    /**
     *带dbpool的save方法
     *@param 无
     *@return 无
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBCIAssemblePayNo dbCIAssemblePayNo = new DBCIAssemblePayNo();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
    	  dbCIAssemblePayNo.setSchema((CIAssemblePayNoSchema)schemas.get(i));
    	  dbCIAssemblePayNo.insert(dbpool);
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
      
      dbpool.open(SysConfig.getProperty("DDCCDATASOURCE"));
      
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
     *@param iPolicyno XX号
     *@return 无
     */
    public void cancel(DbPool dbpool,String iPolicyno) throws Exception
    {




    	String strSqlStatement =  " DELETE FROM CIAssemblePayNo WHERE Policyno= ?"; 
    	dbpool.prepareInnerStatement(strSqlStatement);
    	dbpool.setString(1, iPolicyno);
    	dbpool.executePreparedUpdate();
    	dbpool.closePreparedStatement();
     
    }
      
    /**
     * 不带dbpool的删除方法
     *@param iPolicyno XX单号
     *@return 无
     */
    public void cancel(String iPolicyno ) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      dbpool.open(SysConfig.getProperty("DDCCDATASOURCE"));
      try
      {
        dbpool.beginTransaction();
        cancel(dbpool,iPolicyno);
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
     * 带dbpool根据XX单号获取数据
     *@param iPolicyno XX号
     *@return 无
     */
    public void getData(String iPolicyno) throws Exception
    {
      DbPool dbpool = new DbPool();
      try {
		dbpool.open(SysConfig.getProperty("DDCCDATASOURCE"));
		getData(dbpool, iPolicyno);
	  } catch (Exception e) {
		
	  }finally{      
	    dbpool.close();
      }
    }
      
    /**
     * 不带dbpool根据XX单号获取数据
     *@param dbpool 连接池
     *@param iPolicyno XX号
     *@return 无
     */
    public void getData(DbPool dbpool,String iPolicyno) throws Exception
    {
        
    	
    	
    	
        String strWherePart = " Policyno= ? ";
        ArrayList arrWhereValue = new ArrayList();
        arrWhereValue.add(iPolicyno);
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
        DBCIAssemblePayNo dbCIAssemblePayNo = new DBCIAssemblePayNo();
        if (iLimitCount > 0 && dbCIAssemblePayNo.getCount(dbpool,iWherePart,iWhereValue) > iLimitCount)
        {
            throw new UserException(-98,-1003,"BLCIAssemblePayNo.query");
        }else
        {
            initArr();
            strSqlStatement = " SELECT * FROM CIAssemblePayNo WHERE " + iWherePart;
            schemas = dbCIAssemblePayNo.findByConditions(dbpool,strSqlStatement,iWhereValue);
        }
    }
   
    /**
     * 主函数
     * @param args 参数列表
     */
    public static void main(String[] args){
        
    }
}
