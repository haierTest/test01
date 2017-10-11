package com.sp.prpall.blsvr.misc;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.util.*;

import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.prpall.dbsvr.misc.DBTBLimit;
import com.sp.prpall.dbsvr.misc.DBTbItemDevice;
import com.sp.prpall.schema.TbItemDeviceSchema;

/**
 * 定义TbItemDevice的BL类
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>@createdate 2014-02-11</p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLTbItemDevice{
    private Vector schemas = new Vector();
    /**
     * 构造函数
     */       
    public BLTbItemDevice(){
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
     *增加一条TbItemDeviceSchema记录
     *@param iTbItemDeviceSchema TbItemDeviceSchema
     *@throws Exception
     */
    public void setArr(TbItemDeviceSchema iTbItemDeviceSchema) throws Exception
    {
       try
       {
         schemas.add(iTbItemDeviceSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *得到一条TbItemDeviceSchema记录
     *@param index 下标
     *@return 一个TbItemDeviceSchema对象
     *@throws Exception
     */
    public TbItemDeviceSchema getArr(int index) throws Exception
    {
     TbItemDeviceSchema tbItemDeviceSchema = null;
       try
       {
        tbItemDeviceSchema = (TbItemDeviceSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return tbItemDeviceSchema;
     }
    /**
     *删除一条TbItemDeviceSchema记录
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
      DBTbItemDevice dbTbItemDevice = new DBTbItemDevice();
      if (iLimitCount > 0 && dbTbItemDevice.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLTbItemDevice.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM TB_ITEMDEVICE WHERE " + iWherePart; 
        schemas = dbTbItemDevice.findByConditions(strSqlStatement, bindValues);
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
      DBTbItemDevice dbTbItemDevice = new DBTbItemDevice();
      if (iLimitCount > 0 && dbTbItemDevice.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLTbItemDevice.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM TB_ITEMDEVICE WHERE " + iWherePart; 
        schemas = dbTbItemDevice.findByConditions(dbpool, strSqlStatement, bindValues);
      }
    }
      
    /**
     *带dbpool的save方法
     *@param 无
     *@return 无
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBTbItemDevice dbTbItemDevice = new DBTbItemDevice();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
      dbTbItemDevice.setSchema((TbItemDeviceSchema)schemas.get(i));
      dbTbItemDevice.insert(dbpool);
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
     *@param policylNo PolicylNo
     *@param itemNo ItemNo
     *@return 无
     */
    public void cancel(DbPool dbpool, String policylNo, String itemNo) throws Exception
    {
      String strSqlStatement = "";
      
      strSqlStatement = " DELETE FROM TB_ITEMDEVICE WHERE policylNo='" + policylNo + "', AND itemNo='" + itemNo + "'";
      
      dbpool.delete(strSqlStatement);
    }
      
    /**
     * 不带dbpool的删除方法
     *@param policylNo PolicylNo
     *@param itemNo ItemNo
     *@return 无
     */
    public void cancel(String policylNo, String itemNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
      try
      {
        dbpool.beginTransaction();
        cancel(dbpool, policylNo, itemNo);
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
     * 带dbpool根据PolicyNo获取数据
     *@param iPolicyNo PolicyNo
     *@return 无
     */
    public void getData(String iPolicyNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      try
      {
        dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
        getData(dbpool,iPolicyNo);
      }
      catch (Exception e)
      {
        
      }
      finally
      {
        dbpool.close();
      }
    }
      
    /**
     * 不带dbpool根据PolicyNo获取数据
     *@param dbpool 连接池
     *@param iPolicyNo PolicyNo
     *@return 无
     */
    public void getData(DbPool dbpool,String iPolicyNo) throws Exception
    {
      String strWherePart = " PolicyNo= ? ";
      ArrayList arrWhereValue = new ArrayList();
      arrWhereValue.add(iPolicyNo);
      query(dbpool, strWherePart, arrWhereValue, 0);
    }
    
    /**
     *按照查询条件和记录数限制得到一组记录数，并将这组记录赋给schemas对象
     *@author liuweichang 20140212
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
        DBTbItemDevice dbTbItemDevice = new DBTbItemDevice();
        if (iLimitCount > 0 && dbTbItemDevice.getCount(dbpool,iWherePart,iWhereValue) > iLimitCount)
        {
            throw new UserException(-98,-1003,"BLTbItemDevice.query");
        }else
        {
            initArr();
            strSqlStatement = " SELECT * FROM TB_ITEMDEVICE WHERE " + iWherePart;
            schemas = dbTbItemDevice.findByConditions(dbpool,strSqlStatement,iWhereValue);
        }
    }
    
    
    /**
     * 主函数
     * @param args 参数列表
     */
    public static void main(String[] args){
        
    }
}
