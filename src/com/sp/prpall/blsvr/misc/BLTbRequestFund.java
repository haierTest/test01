package com.sp.prpall.blsvr.misc;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.util.*;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.prpall.dbsvr.misc.DBTbRequestFund;
import com.sp.prpall.schema.TbRequestFundSchema;

/**
 * 定义TbRequestFund的BL类
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>@createdate 2014-10-23</p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLTbRequestFund{
    private Vector schemas = new Vector();
    /**
     * 构造函数
     */       
    public BLTbRequestFund(){
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
     *增加一条TbRequestFundSchema记录
     *@param iTbRequestFundSchema TbRequestFundSchema
     *@throws Exception
     */
    public void setArr(TbRequestFundSchema iTbRequestFundSchema) throws Exception
    {
       try
       {
         schemas.add(iTbRequestFundSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *得到一条TbRequestFundSchema记录
     *@param index 下标
     *@return 一个TbRequestFundSchema对象
     *@throws Exception
     */
    public TbRequestFundSchema getArr(int index) throws Exception
    {
     TbRequestFundSchema tbRequestFundSchema = null;
       try
       {
        tbRequestFundSchema = (TbRequestFundSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return tbRequestFundSchema;
     }
    /**
     *删除一条TbRequestFundSchema记录
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
      DBTbRequestFund dbTbRequestFund = new DBTbRequestFund();
      if (iLimitCount > 0 && dbTbRequestFund.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLTbRequestFund.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM Tb_RequestFund WHERE " + iWherePart; 
        schemas = dbTbRequestFund.findByConditions(strSqlStatement, bindValues);
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
      DBTbRequestFund dbTbRequestFund = new DBTbRequestFund();
      if (iLimitCount > 0 && dbTbRequestFund.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLTbRequestFund.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM Tb_RequestFund WHERE " + iWherePart; 
        schemas = dbTbRequestFund.findByConditions(dbpool, strSqlStatement, bindValues);
      }
    }
      
    /**
     *带dbpool的save方法
     *@param 无
     *@return 无
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBTbRequestFund dbTbRequestFund = new DBTbRequestFund();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
      dbTbRequestFund.setSchema((TbRequestFundSchema)schemas.get(i));
      dbTbRequestFund.insert(dbpool);
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
     * 带dbpool的删除方法
     *@param dbpool    连接池
     *@param policyNo PolicyNo
     *@param serialNo SerialNo
     *@return 无
     */
    public void cancel(DbPool dbpool, String policyNo, String serialNo) throws Exception
    {
      String strSqlStatement = "";
      
      strSqlStatement = " DELETE FROM Tb_RequestFund WHERE policyNo='" + policyNo + "', AND serialNo='" + serialNo + "'";
      
      dbpool.delete(strSqlStatement);
    }
      
    /**
     * 不带dbpool的删除方法
     *@param policyNo PolicyNo
     *@param serialNo SerialNo
     *@return 无
     */
    public void cancel(String policyNo, String serialNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
      try
      {
        dbpool.beginTransaction();
        cancel(dbpool, policyNo, serialNo);
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
     *@param policyNo PolicyNo
     *@param serialNo SerialNo
     *@return 无
     */
    public void getData(String policyNo, String serialNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
      getData(dbpool, policyNo, serialNo);
      dbpool.close();
    }
      
    /**
     * 带dbpool根据主键获取数据
     *@param dbpool 连接池
     *@param policyNo PolicyNo
     *@param serialNo SerialNo
     *@return 无
     */
    public void getData(DbPool dbpool, String policyNo, String serialNo) throws Exception
    {
       String strWherePart = "";
       strWherePart = "policyNo='" + policyNo + "', AND serialNo='" + serialNo + "'";
       query(dbpool, strWherePart, 0, null);
    }
    /**
     * 主函数
     * @param args 参数列表
     */
    public static void main(String[] args){
        
    }
}
