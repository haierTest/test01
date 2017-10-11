package com.sp.prpall.blsvr.misc;

import java.util.ArrayList;
import java.util.Vector;

import com.sp.prpall.dbsvr.misc.DBPrpTimeRegister;
import com.sp.prpall.schema.PrpTimeRegisterSchema;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;

/**
 * 定义PrpTimeRegister的BL类
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>@createdate 2009-07-14</p>
 * @author Zhouxianli(JavaTools v1.0)
 * @updateauthor yangkun(JavaTools v1.1 - v1.2)
 * @lastversion v1.2
 */
public class BLPrpTimeRegister{
    private Vector schemas = new Vector();
    /**
     * 构造函数
     */       
    public BLPrpTimeRegister(){
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
     *增加一条PrpTimeRegisterSchema记录
     *@param iPrpTimeRegisterSchema PrpTimeRegisterSchema
     *@throws Exception
     */
    public void setArr(PrpTimeRegisterSchema iPrpTimeRegisterSchema) throws Exception
    {
       try
       {
         schemas.add(iPrpTimeRegisterSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *得到一条PrpTimeRegisterSchema记录
     *@param index 下标
     *@return 一个PrpTimeRegisterSchema对象
     *@throws Exception
     */
    public PrpTimeRegisterSchema getArr(int index) throws Exception
    {
     PrpTimeRegisterSchema prpTimeRegisterSchema = null;
       try
       {
        prpTimeRegisterSchema = (PrpTimeRegisterSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpTimeRegisterSchema;
     }
    /**
     *删除一条PrpTimeRegisterSchema记录
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
      DBPrpTimeRegister dbPrpTimeRegister = new DBPrpTimeRegister();
      if (iLimitCount > 0 && dbPrpTimeRegister.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpTimeRegister.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpTimeRegister WHERE " + iWherePart; 
        schemas = dbPrpTimeRegister.findByConditions(strSqlStatement);
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
      DBPrpTimeRegister dbPrpTimeRegister = new DBPrpTimeRegister();
      
      if (iLimitCount > 0 && dbPrpTimeRegister.getCount(dbpool, iWherePart) > iLimitCount)
      
      {
        throw new UserException(-98,-1003,"BLPrpTimeRegister.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpTimeRegister WHERE " + iWherePart; 
        schemas = dbPrpTimeRegister.findByConditions(dbpool,strSqlStatement);
      }
    }
      
    /**
     *带dbpool的save方法
     *@param 无
     *@return 无
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpTimeRegister dbPrpTimeRegister = new DBPrpTimeRegister();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
      dbPrpTimeRegister.setSchema((PrpTimeRegisterSchema)schemas.get(i));
      dbPrpTimeRegister.insert(dbpool);
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
     *@param iProposalNo 业务号
     *@return 无
     */
    public void cancel(DbPool dbpool,String iBusinessNo) throws Exception
    {




    	String strSqlStatement = " DELETE FROM PrpTimeRegister WHERE BusinessNo= ?";
    	dbpool.prepareInnerStatement(strSqlStatement);
    	dbpool.setString(1, iBusinessNo);
	dbpool.executePreparedUpdate();
	dbpool.closePreparedStatement();
     
    }
      
    /**
     * 不带dbpool的删除方法
     *@param iProposalNo 业务号
     *@return 无
     */
    public void cancel(String iBusinessNo ) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
      try
      {
        dbpool.beginTransaction();
        cancel(dbpool,iBusinessNo);
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
     * 不带dbpool的更新方法
     *@param iPolicyNo XX号
     *@return 无
     */
    public void update() throws Exception
    {
      DbPool dbpool = new DbPool();
        try {
        dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        dbpool.beginTransaction();
        update(dbpool);
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
      *带dbpool的更新方法
      *@param dbpool    连接池
      *@param iPolicyNo XX号
      *@return 无
      */
     public void update(DbPool dbpool) throws Exception
     {
    	 DBPrpTimeRegister dbPrpTimeRegister = new DBPrpTimeRegister();

       int i = 0;

       for(i = 0; i< schemas.size(); i++)
       {
    	   dbPrpTimeRegister.setSchema((PrpTimeRegisterSchema)schemas.get(i));
    	   dbPrpTimeRegister.update(dbpool);
        }
     }
    /**
     * 带dbpool根据XX单号获取数据
     *@param iProposalNo 业务号
     *@return 无
     */
    public void getData(String iBusinessNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
      getData(dbpool,iBusinessNo);
      dbpool.close();
    }
      
    /**
     * 不带dbpool根据XX单号获取数据
     *@param dbpool 连接池
     *@param iProposalNo 业务号
     *@return 无
     */
    public void getData(DbPool dbpool,String iBusinessNo) throws Exception
    {
        
        
        
        
        String strWherePart = " BusinessNo= ? ";
        ArrayList arrWhereValue = new ArrayList();
        arrWhereValue.add(iBusinessNo);
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
        DBPrpTimeRegister dbPrpTimeRegister = new DBPrpTimeRegister();
        if (iLimitCount > 0 && dbPrpTimeRegister.getCount(dbpool,iWherePart,iWhereValue) > iLimitCount)
        {
            throw new UserException(-98,-1003,"BLPrpTimeRegister.query");
        }else
        {
            initArr();
            strSqlStatement = " SELECT * FROM PrpTimeRegister WHERE " + iWherePart;
            schemas = dbPrpTimeRegister.findByConditions(dbpool,strSqlStatement,iWhereValue);
        }
    }
    
    /**
     * 主函数
     * @param args 参数列表
     */
    public static void main(String[] args){
        
    }
}
