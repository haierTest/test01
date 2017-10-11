package com.sp.prpall.blsvr.misc;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.util.*;
import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.prpall.dbsvr.misc.DBPrpZreportNew;
import com.sp.prpall.schema.PrpZreportNewSchema;

/**
 * 定义报表记录表的BL类
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>@createdate 2003-08-20</p>
 * @Author     : X
 * @version 1.0
 */
public class BLPrpZreportNew{
    private Vector schemas = new Vector();
    /**
     * 构造函数
     */
    public BLPrpZreportNew(){
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
     *增加一条PrpZreportNewSchema记录
     *@param iPrpZreportNewSchema PrpZreportNewSchema
     *@throws Exception
     */
    public void setArr(PrpZreportNewSchema iPrpZreportNewSchema) throws Exception
    {
       try
       {
         schemas.add(iPrpZreportNewSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    public void setArr(int icurr,PrpZreportNewSchema iprpZreportSchema) throws Exception
    {
       try
       {
         schemas.set(icurr,iprpZreportSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *得到一条PrpZreportNewSchema记录
     *@param index 下标
     *@return 一个PrpZreportNewSchema对象
     *@throws Exception
     */
    public PrpZreportNewSchema getArr(int index) throws Exception
    {
     PrpZreportNewSchema prpZreportSchema = null;
       try
       {
        prpZreportSchema = (PrpZreportNewSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpZreportSchema;
     }
    /**
     *删除一条PrpZreportNewSchema记录
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
      DBPrpZreportNew dbPrpZreportNew = new DBPrpZreportNew();
      if (iLimitCount > 0 && dbPrpZreportNew.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpZreportNew.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpZreportNew WHERE " + iWherePart;
        schemas = dbPrpZreportNew.findByConditions(strSqlStatement);
      }
    }
    
    
    public void query1(String iWherePart,int iLimitCount) throws UserException,Exception
    {
      String strSqlStatement = "";
      DBPrpZreportNew dbPrpZreportNew = new DBPrpZreportNew();
      if (iLimitCount > 0 && dbPrpZreportNew.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpZreportNew.query1");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT distinct ReportCode,ComCode,YearMonth FROM PrpZreportNew WHERE " + iWherePart;
        schemas = dbPrpZreportNew.findByConditions1(strSqlStatement);
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
      DBPrpZreportNew dbPrpZreportNew = new DBPrpZreportNew();
      if (iLimitCount > 0 && dbPrpZreportNew.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpZreportNew.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpZreportNew WHERE " + iWherePart;
        schemas = dbPrpZreportNew.findByConditions(dbpool,strSqlStatement);
      }
    }

    /**
     *带dbpool的save方法
     *@param 无
     *@return 无
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpZreportNew dbPrpZreportNew = new DBPrpZreportNew();

      int i = 0;

      for(i = 0; i< schemas.size(); i++)
      {
      dbPrpZreportNew.setSchema((PrpZreportNewSchema)schemas.get(i));
      dbPrpZreportNew.insert(dbpool);
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
        throw e;
      }
      finally {
        dbpool.close();
      }
    }

    /**
     *带dbpool的删除方法
     *@param dbpool    连接池
     *@param null null
     *@return 无
     */
    public void cancel(DbPool dbpool,String iReportCode,String iComCode,String iYearMonth,String iRecordType) throws Exception
    {
      String strSqlStatement = "";

      strSqlStatement = " DELETE FROM PrpZreportNew WHERE ReportCode= '" + iReportCode + "' AND ComCode='"
                        + iComCode + "' AND RecordType ='" + iRecordType + "' AND YearMonth ='" + iYearMonth
                        + "'";

      dbpool.delete(strSqlStatement);
    }

    /**
     * 不带dbpool的删除方法
     *@param null null
     *@return 无
     */
    public void cancel(String iReportCode,String iComCode,String iYearMonth,String iRecordType ) throws Exception
    {
      DbPool dbpool = new DbPool();

        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        dbpool.beginTransaction();
        cancel(dbpool,iReportCode,iComCode,iYearMonth,iRecordType);
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

    /*add begin by zhangsl200412-23 增加了XXXXX类,业务来源两个参数*/
    /**
     *带dbpool的save方法
     *@param 无
     *@return 无
     */
    public void save(DbPool dbpool,int i) throws Exception
    {
      DBPrpZreportNew dbPrpZreportNew = new DBPrpZreportNew();

      int j = 0;

      for(j = 0; j< schemas.size(); j++)
      {
      dbPrpZreportNew.setSchema((PrpZreportNewSchema)schemas.get(j));
      dbPrpZreportNew.insert(dbpool,i);
      }
    }

    /**
     *不带dbpool的XXXXX存方法
     *@param 无
     *@return 无
     */
    public void save(int i) throws Exception
    {
      DbPool dbpool = new DbPool();

        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        dbpool.beginTransaction();
        save(dbpool,i);
        dbpool.commitTransaction();
      }
      catch (Exception e)
      {
        dbpool.rollbackTransaction();
        throw e;
      }
      finally {
        dbpool.close();
      }
    }

    /**
     *带dbpool的删除方法
     *@param dbpool    连接池
     *@param null null
     *@return 无
     */
    public void cancel(DbPool dbpool,String iReportCode,String iComCode,String iYearMonth,String iRecordType,String iClassCode,String iBusinessNature) throws Exception
    {
      String strSqlStatement = "";

        strSqlStatement = " DELETE FROM PrpZreportNew WHERE ReportCode= '" + iReportCode + "' AND ComCode='"
                        + iComCode + "' AND RecordType ='" + iRecordType + "' AND YearMonth ='" + iYearMonth
                        + "' and ClassCode = '" + iClassCode +"' and BusinessNature = '" + iBusinessNature +"'";

      dbpool.delete(strSqlStatement);
    }

    /**
     * 不带dbpool的删除方法
     *@param null null
     *@return 无
     */
    public void cancel(String iReportCode,String iComCode,String iYearMonth,String iRecordType,String iClassCode,String iBusinessNature) throws Exception
    {
      DbPool dbpool = new DbPool();

        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        dbpool.beginTransaction();
        cancel(dbpool,iReportCode,iComCode,iYearMonth,iRecordType,iClassCode,iBusinessNature);
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
     * 带dbpool根据null获取数据
     *@param null null
     *@return 无
     */
    public void getData(String iReportCode,String iComCode,String iYearMonth,String iRecordType,String iClassCode,String iBusinessNature,int iRowNo,int iColNo) throws Exception
    {
      DbPool dbpool = new DbPool();

      try {
		dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
		getData(dbpool, iReportCode, iComCode, iYearMonth, iRecordType,
				iRecordType, iClassCode, iRowNo, iColNo);
	} catch (Exception e) {
		
	}finally {      
	dbpool.close();
    }

    }

    /**
     * 不带dbpool根据null获取数据
     *@param dbpool 连接池
     *@param null null
     *@return 无
     */
    public void getData(DbPool dbpool,String iReportCode,String iComCode,String iYearMonth,String iRecordType,String iClassCode,String iBusinessNature,int iRowNo,int iColNo) throws Exception
    {
    String strWherePart = "";

      strWherePart = "ReportCode= '" + iReportCode + "' AND ComCode='" + iComCode + "' AND YearMonth = '"
                   + iYearMonth + "' AND RecordType ='" + iRecordType + "' AND ClassCode ='"+ iClassCode +"' AND BusinessNature ='"
                   +iBusinessNature +"' AND RowNo ='" + iRowNo
                   + "' AND ColNo ='" + iColNo + "' ";
    query(dbpool,strWherePart,0);
    }

    /*add end by zhangsl20041223 增加XXXXX类,业务来源两个参数*/
    /**
     * 带dbpool根据null获取数据
     *@param null null
     *@return 无
     */
    public void getData(String iReportCode,String iComCode,String iYearMonth,String iRecordType,int iRowNo,int iColNo) throws Exception
    {
      DbPool dbpool = new DbPool();
        
        try {
      	    dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
            getData(dbpool,iReportCode,iComCode,iYearMonth,iRecordType,iRowNo,iColNo);
        } catch (Exception e) {
        }
        finally {
            dbpool.close();
        }
    }

    /**
     * 不带dbpool根据null获取数据
     *@param dbpool 连接池
     *@param null null
     *@return 无
     */
    public void getData(DbPool dbpool,String iReportCode,String iComCode,String iYearMonth,String iRecordType,int iRowNo,int iColNo) throws Exception
    {
    String strWherePart = "";
    strWherePart = "ReportCode= '" + iReportCode + "' AND ComCode='" + iComCode + "' AND YearMonth = '"
                   + iYearMonth + "' AND RecordType ='" + iRecordType + "' AND RowNo ='" + iRowNo
                   + "' AND ColNo ='" + iColNo + "' ";
    query(dbpool,strWherePart,0);
    }
    /**
     * 主函数
     * @param args 参数列表
     */
    public static void main(String[] args){
        
    }
}
