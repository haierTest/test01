package com.sp.prpall.blsvr.jf;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.util.*;
import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.prpall.dbsvr.jf.DBPrpIfaceExchangeBak;
import com.sp.prpall.schema.PrpIfaceExchangeBakSchema;

/**
 * 定义XXXXX接口数据交换备份表的BL类
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>@createdate 2004-12-30</p>
 * @author 莫展锋
 * @version 1.0
 */
public class BLPrpIfaceExchangeBak{
    private Vector schemas = new Vector();
    /**
     * 构造函数
     */
    public BLPrpIfaceExchangeBak(){
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
     *增加一条PrpIfaceExchangeBakSchema记录
     *@param iPrpIfaceExchangeBakSchema PrpIfaceExchangeBakSchema
     *@throws Exception
     */
    public void setArr(PrpIfaceExchangeBakSchema iPrpIfaceExchangeBakSchema) throws Exception
    {
       try
       {
         schemas.add(iPrpIfaceExchangeBakSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *得到一条PrpIfaceExchangeBakSchema记录
     *@param index 下标
     *@return 一个PrpIfaceExchangeBakSchema对象
     *@throws Exception
     */
    public PrpIfaceExchangeBakSchema getArr(int index) throws Exception
    {
     PrpIfaceExchangeBakSchema prpIfaceExchangeBakSchema = null;
       try
       {
        prpIfaceExchangeBakSchema = (PrpIfaceExchangeBakSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpIfaceExchangeBakSchema;
     }
    /**
     *删除一条PrpIfaceExchangeBakSchema记录
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
      DBPrpIfaceExchangeBak dbPrpIfaceExchangeBak = new DBPrpIfaceExchangeBak();
      if (iLimitCount > 0 && dbPrpIfaceExchangeBak.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpIfaceExchangeBak.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM Iface_Exchange_Bak WHERE " + iWherePart;
        schemas = dbPrpIfaceExchangeBak.findByConditions(strSqlStatement);
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
      DBPrpIfaceExchangeBak dbPrpIfaceExchangeBak = new DBPrpIfaceExchangeBak();
      if (iLimitCount > 0 && dbPrpIfaceExchangeBak.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpIfaceExchangeBak.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM Iface_Exchange_Bak WHERE " + iWherePart;
        schemas = dbPrpIfaceExchangeBak.findByConditions(dbpool,strSqlStatement);
      }
    }

    /**
     *带dbpool的save方法
     *@param 无
     *@return 无
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpIfaceExchangeBak dbPrpIfaceExchangeBak = new DBPrpIfaceExchangeBak();

      int i = 0;

      for(i = 0; i< schemas.size(); i++)
      {
      dbPrpIfaceExchangeBak.setSchema((PrpIfaceExchangeBakSchema)schemas.get(i));
      dbPrpIfaceExchangeBak.insert(dbpool);
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
     *@param id ID号
     *@return 无
     */
    public void cancel(DbPool dbpool,String id) throws Exception
    {




    	String strSqlStatement = " Delete From Iface_Exchange_Bak Where Id = ?";
    	dbpool.prepareInnerStatement(strSqlStatement);
    	dbpool.setString(1, id);
	dbpool.executePreparedUpdate();
	dbpool.closePreparedStatement();
     
    }

    /**
     * 不带dbpool的删除方法
     *@param id Id号
     *@return 无
     */
    public void cancel(String id ) throws Exception
    {
      DbPool dbpool = new DbPool();

        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        dbpool.beginTransaction();
        cancel(dbpool,id);
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
     * 带dbpool根据Id号获取数据
     *@param id ID号
     *@return 无
     */
    public void getData(String id) throws Exception
    {
        DbPool dbpool = new DbPool();

  	try {
  		dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
  		getData(dbpool, id);
  	} catch (Exception e) {
  		
  	}finally {
  		dbpool.close();
  	}

    }

    /**
     * 不带dbpool根据Id号获取数据
     *@param dbpool 连接池
     *@param id Id号
     *@return 无
     */
    public void getData(DbPool dbpool,String id) throws Exception
    {
    String strWherePart = "";
    strWherePart = "Id= '" + id + "'";
    query(dbpool,strWherePart,0);
    }
    /**
     * 主函数
     * @param args 参数列表
     */
    public static void main(String[] args){
        
    }
}
