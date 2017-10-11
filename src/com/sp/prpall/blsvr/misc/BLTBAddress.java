package com.sp.prpall.blsvr.misc;

import java.util.*;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.prpall.dbsvr.misc.DBTBAddress;
import com.sp.prpall.schema.TBAddressSchema;

/**
 * 定义TB_Address的BL类
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>@createdate 2013-07-05</p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLTBAddress{
    private Vector schemas = new Vector();
    /**
     * 构造函数
     */       
    public BLTBAddress(){
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
     *增加一条TB_AddressSchema记录
     *@param iTB_AddressSchema TB_AddressSchema
     *@throws Exception
     */
    public void setArr(TBAddressSchema iTB_AddressSchema) throws Exception
    {
       try
       {
         schemas.add(iTB_AddressSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *得到一条TB_AddressSchema记录
     *@param index 下标
     *@return 一个TB_AddressSchema对象
     *@throws Exception
     */
    public TBAddressSchema getArr(int index) throws Exception
    {
     TBAddressSchema tB_AddressSchema = null;
       try
       {
        tB_AddressSchema = (TBAddressSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return tB_AddressSchema;
     }
    /**
     *删除一条TB_AddressSchema记录
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
      DBTBAddress dbTB_Address = new DBTBAddress();
      if (iLimitCount > 0 && dbTB_Address.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLTB_Address.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM TB_Address WHERE " + iWherePart; 
        schemas = dbTB_Address.findByConditions(strSqlStatement, bindValues);
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
      DBTBAddress dbTB_Address = new DBTBAddress();
      if (iLimitCount > 0 && dbTB_Address.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLTB_Address.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM TB_Address WHERE " + iWherePart; 
        schemas = dbTB_Address.findByConditions(dbpool, strSqlStatement, bindValues);
      }
    }
      
    /**
     *带dbpool的save方法
     *@param 无
     *@return 无
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBTBAddress dbTB_Address = new DBTBAddress();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
      dbTB_Address.setSchema((TBAddressSchema)schemas.get(i));
      dbTB_Address.insert(dbpool);
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
    	dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
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
     *@return 无
     */
    public void cancel(DbPool dbpool, String policyNo) throws Exception
    {
      
      
      
      
  	String strSqlStatement = "DELETE FROM TB_Address WHERE policyNo= ?";
    dbpool.prepareInnerStatement(strSqlStatement);
    dbpool.setString(1, policyNo);
    dbpool.executePreparedUpdate();
    dbpool.closePreparedStatement();
    }
      
    /**
     * 不带dbpool的删除方法
     *@param policyNo PolicyNo
     *@return 无
     */
    public void cancel(String policyNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      try
      {
    	dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        dbpool.beginTransaction();
        cancel(dbpool, policyNo);
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
     *@return 无
     */
    public void getData(String policyNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      try
      {
    	dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
    	getData(dbpool, policyNo);
      }
      catch (Exception e)
      {
    	  e.printStackTrace();
      }
      finally
      {
        dbpool.close();
      }
      
    }
      
    /**
     * 带dbpool根据主键获取数据
     *@param dbpool 连接池
     *@param policyNo PolicyNo
     *@return 无
     */
    public void getData(DbPool dbpool, String policyNo) throws Exception
    {
       
       
       
       
       String strWherePart = " PolicyNo= ? ";
       ArrayList arrWhereValue = new ArrayList();
       arrWhereValue.add(policyNo);
       query(dbpool, strWherePart, arrWhereValue, 0);
       
    }
    
    /**
     *按照查询条件和记录数限制得到一组记录数，并将这组记录赋给schemas对象
     *@author liuweichang 20130705
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
        DBTBAddress dbTBAddress = new DBTBAddress();
        if (iLimitCount > 0 && dbTBAddress.getCount(dbpool,iWherePart,iWhereValue) > iLimitCount)
        {
            throw new UserException(-98,-1003,"BLTBAddress.query");
        }else
        {
            initArr();
            strSqlStatement = " SELECT * FROM TB_Address WHERE " + iWherePart;
            schemas = dbTBAddress.findByConditions(dbpool,strSqlStatement,iWhereValue);
        }
    }
    
    
    
    /**
     * 主函数
     * @param args 参数列表
     */
    public static void main(String[] args){
        
    }
}
