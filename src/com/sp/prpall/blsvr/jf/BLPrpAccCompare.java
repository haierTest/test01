package com.sp.prpall.blsvr.jf;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.util.*;
import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.prpall.dbsvr.jf.DBPrpAccCompare;
import com.sp.prpall.schema.PrpAccCompareSchema;

/**
 * 定义业务、XXXXX代码对照表的BL类
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>@createdate 2004-12-30</p>
 * @author 莫展锋
 * @version 1.0
 */
public class BLPrpAccCompare{
    private Vector schemas = new Vector();
    /**
     * 构造函数
     */
    public BLPrpAccCompare(){
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
     *增加一条PrpAccCompareSchema记录
     *@param iPrpAccCompareSchema PrpAccCompareSchema
     *@throws Exception
     */
    public void setArr(PrpAccCompareSchema iPrpAccCompareSchema) throws Exception
    {
       try
       {
         schemas.add(iPrpAccCompareSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *得到一条PrpAccCompareSchema记录
     *@param index 下标
     *@return 一个PrpAccCompareSchema对象
     *@throws Exception
     */
    public PrpAccCompareSchema getArr(int index) throws Exception
    {
     PrpAccCompareSchema prpAccCompareSchema = null;
       try
       {
        prpAccCompareSchema = (PrpAccCompareSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpAccCompareSchema;
     }
    /**
     *删除一条PrpAccCompareSchema记录
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
      DBPrpAccCompare dbPrpAccCompare = new DBPrpAccCompare();
      if (iLimitCount > 0 && dbPrpAccCompare.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpAccCompare.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM AccCompare WHERE " + iWherePart;
        schemas = dbPrpAccCompare.findByConditions(strSqlStatement);
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
      DBPrpAccCompare dbPrpAccCompare = new DBPrpAccCompare();
      if (iLimitCount > 0 && dbPrpAccCompare.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpAccCompare.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM AccCompare WHERE " + iWherePart;
        schemas = dbPrpAccCompare.findByConditions(dbpool,strSqlStatement);
      }
    }

    /**
     *带dbpool的save方法
     *@param 无
     *@return 无
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpAccCompare dbPrpAccCompare = new DBPrpAccCompare();

      int i = 0;

      for(i = 0; i< schemas.size(); i++)
      {
      dbPrpAccCompare.setSchema((PrpAccCompareSchema)schemas.get(i));
      dbPrpAccCompare.insert(dbpool);
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
     *@param codeType 代码类型
     *@param businessCode 业务代码
     *@return 无
     */
    public void cancel(DbPool dbpool,String codeType,String businessCode) throws Exception
    {
      String strSqlStatement = "";

      strSqlStatement = " Delete From AccCompare Where CodeType = " + "'" + codeType + "'" +
                            " And BusinessCode = " + "'" + businessCode + "'";

      dbpool.delete(strSqlStatement);
    }

    /**
     * 不带dbpool的删除方法
     *@param codeType 代码类型
     *@param businessCode 业务代码
     *@return 无
     */
    public void cancel(String codeType,String businessCode ) throws Exception
    {
      DbPool dbpool = new DbPool();

        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        dbpool.beginTransaction();
        cancel(dbpool,codeType,businessCode);
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
     * 不带dbpool根据代码类型，业务代码获取数据
     *@param codeType 代码类型
     *@param businessCode 业务代码
     *@return 无
     */
    public void getData(String codeType,String businessCode) throws Exception
    {
        DbPool dbpool = new DbPool();

  	try {
  		dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
  		getData(dbpool, codeType, businessCode);
  	} catch (Exception e) {
  		
  	}finally {      
  		dbpool.close();
  	}

    }

    /**
     * 带dbpool根据代码类型，业务代码获取数据
     *@param dbpool 连接池
     *@param codeType 代码类型
     *@param businessCode 业务代码
     *@return 无
     */
    public void getData(DbPool dbpool,String codeType,String businessCode) throws Exception
    {
    String strWherePart = "";
    strWherePart = "CodeType = " + "'" + codeType + "'" +
                   " And BusinessCode = " + "'" + businessCode + "'";
    query(dbpool,strWherePart,0);
    }

    /**
     * 翻译代码
     @Author  莫展锋
     * @param codeType 代码类型
     * @param businessCode 业务代码
     * @throw UserException,Exception
     * @return 根据代码类型、业务代码返回XXXXX代码
     *         如果没有相应的XXXXX代码则返回原业务代码
     * @date    20041230
     */
    public String translateCode(String codeType,String businessCode)
      throws UserException,Exception
    {
      if(codeType.equals("")||businessCode.equals("")) return ""; 
      DBPrpAccCompare dbPrpAccCompare = new DBPrpAccCompare();
      dbPrpAccCompare.getInfo(codeType,businessCode);
      if(!(dbPrpAccCompare.getAccCode().equals("")||dbPrpAccCompare.getAccCode()== null))
        return dbPrpAccCompare.getAccCode();
      else
        return businessCode;
    }
    /**
     * 翻译代码
     @Author  莫展锋
     * @param businessCode 业务代码
     * @throw UserException,Exception
     * @return 根据代码类型、业务代码返回XXXXX代码
     *         如果没有相应的XXXXX代码则返回原业务代码
     * @date    20041230
     */
    public String translateBeforQuery(String businessCode) throws UserException,Exception
    {
      String accCode="";
      PrpAccCompareSchema prpAccCompareSchema = null;
      for (int i = 0; i < this.schemas.size(); i++)
      {
        prpAccCompareSchema = (PrpAccCompareSchema)schemas.get(i);
        if(prpAccCompareSchema.getBusinessCode().equals(businessCode))
          accCode = prpAccCompareSchema.getAccCode();
      }
      return accCode;
    }


    /**
     * 主函数
     * @param args 参数列表
     */
    public static void main(String[] args){
        
    }
}
