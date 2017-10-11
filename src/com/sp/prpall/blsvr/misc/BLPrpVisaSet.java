package com.sp.prpall.blsvr.misc;

import java.util.ArrayList;
import java.util.Vector;

import com.sp.prpall.dbsvr.misc.DBPrpVisaSet;
import com.sp.prpall.schema.PrpVisaSetSchema;
import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;

/**
 * 定义单证打印配置表的BL类
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>@createdate 2003-11-03</p>
 * @Author     : X
 * @version 1.0
 */
public class BLPrpVisaSet{
    private Vector schemas = new Vector();
    /**
     * 构造函数
     */
    public BLPrpVisaSet(){
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
     *增加一条PrpVisaSetSchema记录
     *@param iPrpVisaSetSchema PrpVisaSetSchema
     *@throws Exception
     */
    public void setArr(PrpVisaSetSchema iPrpVisaSetSchema) throws Exception
    {
       try
       {
         schemas.add(iPrpVisaSetSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *得到一条PrpVisaSetSchema记录
     *@param index 下标
     *@return 一个PrpVisaSetSchema对象
     *@throws Exception
     */
    public PrpVisaSetSchema getArr(int index) throws Exception
    {
     PrpVisaSetSchema prpVisaSetSchema = null;
       try
       {
        prpVisaSetSchema = (PrpVisaSetSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpVisaSetSchema;
     }
    /**
     *删除一条PrpVisaSetSchema记录
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
      DBPrpVisaSet dbPrpVisaSet = new DBPrpVisaSet();
      if (iLimitCount > 0 && dbPrpVisaSet.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpVisaSet.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpVisaSet WHERE " + iWherePart;
        schemas = dbPrpVisaSet.findByConditions(strSqlStatement);
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
      DBPrpVisaSet dbPrpVisaSet = new DBPrpVisaSet();
      if (iLimitCount > 0 && dbPrpVisaSet.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpVisaSet.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpVisaSet WHERE " + iWherePart;
        schemas = dbPrpVisaSet.findByConditions(dbpool,strSqlStatement);
      }
    }

    /**
     *带dbpool的save方法
     *@param 无
     *@return 无
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpVisaSet dbPrpVisaSet = new DBPrpVisaSet();

      int i = 0;

      for(i = 0; i< schemas.size(); i++)
      {
      dbPrpVisaSet.setSchema((PrpVisaSetSchema)schemas.get(i));
      dbPrpVisaSet.insert(dbpool);
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
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        dbpool.beginTransaction();
        save(dbpool);
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
     *带dbpool的删除方法
     *@param dbpool    连接池
     *@param RiskCode XXXXX种
     *@return 无
     */
    public void cancel(DbPool dbpool,String RiskCode) throws Exception
    {




    	String strSqlStatement = " DELETE FROM PrpVisaSet WHERE RiskCode= ?";
    	dbpool.prepareInnerStatement(strSqlStatement);
    	dbpool.setString(1, RiskCode);
	dbpool.executePreparedUpdate();
	dbpool.closePreparedStatement();
     
    }

    /**
     * 不带dbpool的删除方法
     *@param RiskCode XXXXX种
     *@return 无
     */
    public void cancel(String RiskCode ) throws Exception
    {
      DbPool dbpool = new DbPool();

        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        dbpool.beginTransaction();
        cancel(dbpool,RiskCode);
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
     * 带dbpool根据RiskCode获取数据
     *@param dbpool 连接池
     *@param RiskCode XXXXX种
     */
    public void getData(String RiskCode) throws Exception
    {
      DbPool dbpool = new DbPool();

      try {
		dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
		getData(dbpool, RiskCode);
	} catch (Exception e) {
		
	}finally {      
	dbpool.close();
    }

    }

    /**
     * 不带dbpool根据RiskCode获取数据
     *@param dbpool 连接池
     *@param RiskCode XXXXX种
     *@return 无
     */
    public void getData(DbPool dbpool,String RiskCode) throws Exception
    {
        
        
        
        
        String strWherePart = " RiskCode= ? ";
        ArrayList arrWhereValue = new ArrayList();
        arrWhereValue.add(RiskCode);
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
        DBPrpVisaSet dbPrpVisaSet = new DBPrpVisaSet();
        if (iLimitCount > 0 && dbPrpVisaSet.getCount(dbpool,iWherePart,iWhereValue) > iLimitCount)
        {
            throw new UserException(-98,-1003,"BLPrpVisaSet.query");
        }else
        {
            initArr();
            strSqlStatement = " SELECT * FROM PrpVisaSet WHERE " + iWherePart;
            schemas = dbPrpVisaSet.findByConditions(dbpool,strSqlStatement,iWhereValue);
        }
    }
    
    /**
     * 主函数
     * @param args 参数列表
     */
    public static void main(String[] args){
        
    }
}
