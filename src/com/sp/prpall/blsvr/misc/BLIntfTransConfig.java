package com.sp.prpall.blsvr.misc;

import java.util.ArrayList;
import java.util.Vector;

import com.sp.prpall.dbsvr.misc.DBIntfTransConfig;
import com.sp.prpall.schema.IntfTransConfigSchema;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;

/**
 * 定义INTF_TRANS_CONFIG的BL类
 * 从pdm中取数据库信息,根据数据库表生成对应的BL类
 * <p>Title: SinosoftJavaTools v1.4</p>
 * <p>Description: 中科软Java源码生成工具</p>
 * <p>Company: Sinosoft</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>@createdate 2009-11-12</p>
 * @FirstAuthor: Zhouxianli(JavaTools v1.0)
 * @SecondAuthor: Yangkun(JavaTools v1.1->v1.2->v1.3->v1.4)
 * @LastVersion: v1.4
 * @UpdateList: 1.新增对CIXXXXX平台类的生成处理;
 *              2.修正特殊表生成类cancel()、getdata()方法时获取pdm表主键入参为null的问题;
 */
public class BLIntfTransConfig{
    private Vector schemas = new Vector();
    /**
     * 构造函数
     */       
    public BLIntfTransConfig(){
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
     *增加一条IntfTransConfigSchema记录
     *@param iIntfTransConfigSchema IntfTransConfigSchema
     *@throws Exception
     */
    public void setArr(IntfTransConfigSchema iIntfTransConfigSchema) throws Exception
    {
       try
       {
         schemas.add(iIntfTransConfigSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *得到一条IntfTransConfigSchema记录
     *@param index 下标
     *@return 一个IntfTransConfigSchema对象
     *@throws Exception
     */
    public IntfTransConfigSchema getArr(int index) throws Exception
    {
     IntfTransConfigSchema intfTransConfigSchema = null;
       try
       {
        intfTransConfigSchema = (IntfTransConfigSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return intfTransConfigSchema;
     }
    /**
     *删除一条IntfTransConfigSchema记录
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
      DBIntfTransConfig dbIntfTransConfig = new DBIntfTransConfig();
      if (iLimitCount > 0 && dbIntfTransConfig.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLIntfTransConfig.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM INTF_TRANS_CONFIG WHERE " + iWherePart; 
        schemas = dbIntfTransConfig.findByConditions(strSqlStatement);
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
      DBIntfTransConfig dbIntfTransConfig = new DBIntfTransConfig();
      if (iLimitCount > 0 && dbIntfTransConfig.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLIntfTransConfig.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM INTF_TRANS_CONFIG WHERE " + iWherePart; 
        schemas = dbIntfTransConfig.findByConditions(dbpool,strSqlStatement);
      }
    }
      
    /**
     *带dbpool的save方法
     *@param 无
     *@return 无
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBIntfTransConfig dbIntfTransConfig = new DBIntfTransConfig();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
    	  dbIntfTransConfig.setSchema((IntfTransConfigSchema)schemas.get(i));
    	  dbIntfTransConfig.insert(dbpool);
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
        dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
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
     *@param iTransID TransID
     *@return 无
     */
    public void cancel(DbPool dbpool,String iTransID) throws Exception
    {




    	String strSqlStatement = " DELETE FROM INTF_TRANS_CONFIG WHERE TransID=  ?";
    	dbpool.prepareInnerStatement(strSqlStatement);
    	dbpool.setString(1, iTransID);
	dbpool.executePreparedUpdate();
	dbpool.closePreparedStatement();
     
    }
      
    /**
     * 不带dbpool的删除方法
     *@param iTransID TransID
     *@return 无
     */
    public void cancel(String iTransID ) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      try
      {
        dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
        dbpool.beginTransaction();
        cancel(dbpool,iTransID);
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
     * 带dbpool根据TransID获取数据
     *@param iTransID TransID
     *@return 无
     */
    public void getData(String iTransID) throws Exception
    {
      DbPool dbpool = new DbPool();
      dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
      getData(dbpool,iTransID);
      dbpool.close();
    }
      
    /**
     * 不带dbpool根据TransID获取数据
     *@param dbpool 连接池
     *@param iTransID TransID
     *@return 无
     */
    public void getData(DbPool dbpool,String iTransID) throws Exception
    {
        
        
        
        
        String strWherePart = " TransID=? ";
        ArrayList arrWhereValue = new ArrayList();
        arrWhereValue.add(iTransID);
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
        DBIntfTransConfig dbIntfTransConfig = new DBIntfTransConfig();
        if (iLimitCount > 0 && dbIntfTransConfig.getCount(dbpool,iWherePart,iWhereValue) > iLimitCount)
        {
            throw new UserException(-98,-1003,"BLIntfTransConfig.query");
        }else
        {
            initArr();
            strSqlStatement = " SELECT * FROM IntfTransConfig WHERE " + iWherePart;
            schemas = dbIntfTransConfig.findByConditions(dbpool,strSqlStatement,iWhereValue);
        }
    }
    
    /**
     * 主函数
     * @param args 参数列表
     */
    public static void main(String[] args){
        
    }
}
