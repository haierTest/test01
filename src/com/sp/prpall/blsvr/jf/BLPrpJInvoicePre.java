 package com.sp.prpall.blsvr.jf;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.util.*;

import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.prpall.dbsvr.jf.DBPrpJInvoicePre;
import com.sp.prpall.schema.PrpJInvoicePreSchema;

/**
 * 定义预借发票信息表的BL类
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>@createdate 2013-10-24</p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLPrpJInvoicePre{
    private Vector schemas = new Vector();
    /**
     * 构造函数
     */       
    public BLPrpJInvoicePre(){
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
     *增加一条PrpJInvoicePreSchema记录
     *@param iPrpJInvoicePreSchema PrpJInvoicePreSchema
     *@throws Exception
     */
    public void setArr(PrpJInvoicePreSchema iPrpJInvoicePreSchema) throws Exception
    {
       try
       {
         schemas.add(iPrpJInvoicePreSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *得到一条PrpJInvoicePreSchema记录
     *@param index 下标
     *@return 一个PrpJInvoicePreSchema对象
     *@throws Exception
     */
    public PrpJInvoicePreSchema getArr(int index) throws Exception
    {
     PrpJInvoicePreSchema prpJInvoicePreSchema = null;
       try
       {
        prpJInvoicePreSchema = (PrpJInvoicePreSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpJInvoicePreSchema;
     }
    /**
     *删除一条PrpJInvoicePreSchema记录
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
     *@param bindValues 绑定参数
     *@throws UserException
     *@throws Exception
     */
    public void query(String iWherePart, ArrayList bindValues) throws UserException,Exception
    {
       this.query(iWherePart, Integer.parseInt(SysConst.getProperty("QUERY_LIMIT_COUNT").trim()), bindValues);
    }
    /**
     *按照查询条件和记录数限制得到一组记录数，并将这组记录赋给schemas对象
     *@param iWherePart 查询条件(包括排序字句)
     *@param iLimitCount 记录数限制(iLimitCount=0: 无限制)
     *@param bindValues 绑定参数
     *@throws UserException
     *@throws Exception
     */
    public void query(String iWherePart, int iLimitCount, ArrayList bindValues) throws UserException,Exception
    {
      String strSqlStatement = "";
      DBPrpJInvoicePre dbPrpJInvoicePre = new DBPrpJInvoicePre();
      if (iLimitCount > 0 && dbPrpJInvoicePre.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJInvoicePre.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpJInvoicePre WHERE " + iWherePart; 
        schemas = dbPrpJInvoicePre.findByConditions(strSqlStatement, bindValues);
      }
    }
    /**
     *按照查询条件得到一组记录数，并将这组记录赋给schemas对象
     *@param dbpool 全局池
     *@param iWherePart 查询条件(包括排序字句)
     *@param bindValues 绑定参数
     *@throws UserException
     *@throws Exception
     */
    public void query(DbPool dbpool, String iWherePart, ArrayList bindValues) throws UserException,Exception
    {
       this.query(dbpool,iWherePart,Integer.parseInt(SysConst.getProperty("QUERY_LIMIT_COUNT").trim()),bindValues);
    }
    /**
     *按照查询条件和记录数限制得到一组记录数，并将这组记录赋给schemas对象
     *@param dbpool 全局池
     *@param iWherePart 查询条件(包括排序字句)
     *@param iLimitCount 记录数限制(iLimitCount=0: 无限制)
     *@param bindValues 绑定参数
     *@throws UserException
     *@throws Exception
     */
    public void query(DbPool dbpool, String iWherePart, int iLimitCount, ArrayList bindValues) throws UserException,Exception
    {
      String strSqlStatement = "";
      DBPrpJInvoicePre dbPrpJInvoicePre = new DBPrpJInvoicePre();
      if (iLimitCount > 0 && dbPrpJInvoicePre.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJInvoicePre.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpJInvoicePre WHERE " + iWherePart; 
        schemas = dbPrpJInvoicePre.findByConditions(dbpool, strSqlStatement, bindValues);
      }
    }
      
    
    public void queryByLimit(String iWherePart,int iLimitCount,ArrayList bindValues) throws UserException,Exception
    {
        String strSqlStatement = "";
        String strOthWherePart =" ORDER BY CertiNo,PolicyNo,PayNo,SerialNo DESC";
        DBPrpJInvoicePre dbPrpJInvoicePre = new DBPrpJInvoicePre();

        initArr();
        strSqlStatement = " SELECT * FROM PrpJInvoicePre WHERE " + iWherePart+" and ROWNUM<=1000"+strOthWherePart;
        schemas = dbPrpJInvoicePre.findByConditions(strSqlStatement,bindValues);

    }
    
    /**
     *带dbpool的save方法
     *@param 无
     *@return 无
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpJInvoicePre dbPrpJInvoicePre = new DBPrpJInvoicePre();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
      dbPrpJInvoicePre.setSchema((PrpJInvoicePreSchema)schemas.get(i));
      dbPrpJInvoicePre.insert(dbpool);
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
      
      dbpool.open(SysConst.getProperty("paymentDataSource"));
      
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
     * 带dbpool的删除方法
     *@param dbpool    连接池
     *@param certiType 业务类型
     *@param certiNo 业务号
     *@param serialNo 序号
     *@param payRefReason 收付原因
     *@return 无
     */
    public void cancel(DbPool dbpool, String certiType, String certiNo, String serialNo, String payRefReason) throws Exception
    {
      String strSqlStatement = "";
      
      strSqlStatement = " DELETE FROM PrpJInvoicePre WHERE certiType='" + certiType + "', AND certiNo='" + certiNo + "', AND serialNo='" + serialNo + "', AND payRefReason='" + payRefReason + "'";
      
      dbpool.delete(strSqlStatement);
    }
      
    /**
     * 不带dbpool的删除方法
     *@param certiType 业务类型
     *@param certiNo 业务号
     *@param serialNo 序号
     *@param payRefReason 收付原因
     *@return 无
     */
    public void cancel(String certiType, String certiNo, String serialNo, String payRefReason) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      dbpool.open(SysConst.getProperty("paymentDataSource"));
      try
      {
        dbpool.beginTransaction();
        cancel(dbpool, certiType, certiNo, serialNo, payRefReason);
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
     * 不带dbpool根据主键获取数据
     *@param certiType 业务类型
     *@param certiNo 业务号
     *@param serialNo 序号
     *@param payRefReason 收付原因
     *@return 无
     */
    public void getData(String certiType, String certiNo, String serialNo, String payRefReason) throws Exception
    {
      DbPool dbpool = new DbPool();
      dbpool.open(SysConst.getProperty("paymentDataSource"));
      getData(dbpool, certiType, certiNo, serialNo, payRefReason);
      dbpool.close();
    }
      
    /**
     * 带dbpool根据主键获取数据
     *@param dbpool 连接池
     *@param certiType 业务类型
     *@param certiNo 业务号
     *@param serialNo 序号
     *@param payRefReason 收付原因
     *@return 无
     */
    public void getData(DbPool dbpool, String certiType, String certiNo, String serialNo, String payRefReason) throws Exception
    {
       String strWherePart = "";
       strWherePart = "certiType='" + certiType + "', AND certiNo='" + certiNo + "', AND serialNo='" + serialNo + "', AND payRefReason='" + payRefReason + "'";
       query(dbpool, strWherePart, 0, null);
    }
    /**
     * 主函数
     * @param args 参数列表
     */
    public static void main(String[] args){
        
    }
}
