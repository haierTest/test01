package com.sp.indiv.ci.blsvr;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.util.*;

import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.indiv.ci.dbsvr.DBCIEndorValid;
import com.sp.indiv.ci.schema.CIEndorValidSchema;

/**
 * 定义批改查询确认表-CIEndorValid的BL类
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>@createdate 2006-06-14</p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLCIEndorValid{
    private Vector schemas = new Vector();
    /**
     * 构造函数
     */
    public BLCIEndorValid(){
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
     *增加一条CIEndorValidSchema记录
     *@param iCIEndorValidSchema CIEndorValidSchema
     *@throws Exception
     */
    public void setArr(CIEndorValidSchema iCIEndorValidSchema) throws Exception
    {
       try
       {
         schemas.add(iCIEndorValidSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *得到一条CIEndorValidSchema记录
     *@param index 下标
     *@return 一个CIEndorValidSchema对象
     *@throws Exception
     */
    public CIEndorValidSchema getArr(int index) throws Exception
    {
     CIEndorValidSchema cIEndorValidSchema = null;
       try
       {
        cIEndorValidSchema = (CIEndorValidSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return cIEndorValidSchema;
     }
    /**
     *删除一条CIEndorValidSchema记录
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
     * 根据XX号获取数据,调用绑定变量方法
     *@param dbpool 连接池
     *@param iPolicyNo XX号
     *@return 无
     */
    public void query(int iLimitCount,String iEndorDemandNo) throws Exception
    {
    	DbPool dbpool = new DbPool();
        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
            String strWherePart = " DemandNo= ? ";
            ArrayList arrWhereValue = new ArrayList();
            arrWhereValue.add(iEndorDemandNo);
            query(dbpool, strWherePart, arrWhereValue, iLimitCount);
        } catch (Exception e) {
        } finally {
            dbpool.close();
        }    
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
        DBCIEndorValid dbCIEndorValid = new DBCIEndorValid();
        if (iLimitCount > 0 && dbCIEndorValid.getCount(dbpool,iWherePart,iWhereValue) > iLimitCount)
        {
            throw new UserException(-98,-1003,"BLCIEndorValid.query");
        }else
        {
            initArr();
            strSqlStatement = " SELECT * FROM CIEndorValid WHERE " + iWherePart;
            schemas = dbCIEndorValid.findByConditions(dbpool,strSqlStatement,iWhereValue);
        }
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
      DBCIEndorValid dbCIEndorValid = new DBCIEndorValid();
      if (iLimitCount > 0 && dbCIEndorValid.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLCIEndorValid.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM CIEndorValid WHERE " + iWherePart;
        schemas = dbCIEndorValid.findByConditions(strSqlStatement);
      }
    }
    
    
    /**
     *按照查询条件得到一组记录数，并将这组记录赋给schemas对象
     *@param iWherePart 查询条件(包括排序字句)
     *@throws UserException
     *@throws Exception
     */
    public void queryHistory(String iWherePart) throws UserException,Exception
    {
       this.queryHistory(iWherePart,Integer.parseInt(SysConst.getProperty("QUERY_LIMIT_COUNT").trim()));
    }
    /**
     *按照查询条件和记录数限制得到一组记录数，并将这组记录赋给schemas对象
     *@param iWherePart 查询条件(包括排序字句)
     *@param iLimitCount 记录数限制(iLimitCount=0: 无限制)
     *@throws UserException
     *@throws Exception
     */
    public void queryHistory(String iWherePart,int iLimitCount) throws UserException,Exception
    {
      String strSqlStatement = "";
      DBCIEndorValid dbCIEndorValid = new DBCIEndorValid();
      if (iLimitCount > 0 && dbCIEndorValid.getCountHistory(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLCIEndorValid.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM CIEndorValid WHERE " + iWherePart;
        schemas = dbCIEndorValid.findByConditionsHistory(strSqlStatement);
      }
    }
    
    /**
     *按照查询条件得到一组记录数，并将这组记录赋给schemas对象
     *@param iWherePart 查询条件(包括排序字句)
     *@throws UserException
     *@throws Exception
     */
    public void queryCurrentAndHistory(String iWherePart) throws UserException,Exception
    {
       this.query(iWherePart);
       
       if(this.getSize() == 0){
    	   this.queryHistory(iWherePart);
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
      DBCIEndorValid dbCIEndorValid = new DBCIEndorValid();
      if (iLimitCount > 0 && dbCIEndorValid.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLCIEndorValid.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM CIEndorValid WHERE " + iWherePart;
        schemas = dbCIEndorValid.findByConditions(dbpool,strSqlStatement);
      }
    }

    /**
     *带dbpool的save方法
     *@param 无
     *@return 无
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBCIEndorValid dbCIEndorValid = new DBCIEndorValid();

      int i = 0;

      for(i = 0; i< schemas.size(); i++)
      {
      dbCIEndorValid.setSchema((CIEndorValidSchema)schemas.get(i));
      dbCIEndorValid.insert(dbpool);
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
     *@param iEndorseNo 批单号
     *@return 无
     */
    public void cancel(DbPool dbpool,String iEndorseNo) throws Exception
    {




    	String strSqlStatement = " DELETE FROM CIEndorValid WHERE EndorseNo= ?";
    	dbpool.prepareInnerStatement(strSqlStatement);
    	dbpool.setString(1, iEndorseNo);
    	dbpool.executePreparedUpdate();
    	dbpool.closePreparedStatement();
     
    }

    /**
     * 不带dbpool的删除方法
     *@param iEndorseNo 批单号
     *@return 无
     */
    public void cancel(String iEndorseNo ) throws Exception
    {
      DbPool dbpool = new DbPool();

        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        dbpool.beginTransaction();
        cancel(dbpool,iEndorseNo);
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
   * 强三XXXXX上海平台   add by jinbiao 2006.8.22
   */
   public void setEndorseNo(DbPool dbpool, String iEndorseNo) throws Exception {
   	    
    String strSql = "";
    String strSql2 = "";
    if (this.getSize() == 0)
      return;

    strSql = "UPDATE CIEndorValid SET EndorseNo='" + iEndorseNo + "'" +
        " WHERE DemandNo  = '" + this.getArr(0).getDemandNo() + "' ";
    strSql2 = "UPDATE CIEndorValid SET ValidNo='" + iEndorseNo + "'" +
        " WHERE DemandNo  = '" + this.getArr(0).getDemandNo() + "' ";
     
    try {
      dbpool.update(strSql);
      dbpool.update(strSql2);
      
    }
    
    catch (Exception e) {
      throw e;
    }
           
      
  }

    /**
     * 主函数
     * @param args 参数列表
     */
    public static void main(String[] args){
        
    }
}
