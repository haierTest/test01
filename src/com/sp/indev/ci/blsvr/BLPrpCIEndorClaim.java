package com.sp.indiv.ci.blsvr;

import java.util.ArrayList;
import java.util.Vector;

import com.sp.indiv.ci.dbsvr.DBPrpCIEndorClaim;
import com.sp.indiv.ci.schema.PrpCIEndorClaimSchema;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;

/**
 * 定义PrpCIEndorClaim的BL类
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>@createdate 2007-08-23</p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLPrpCIEndorClaim{
    private Vector schemas = new Vector();
    /**
     * 构造函数
     */       
    public BLPrpCIEndorClaim(){
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
     *增加一条PrpCIEndorClaimSchema记录
     *@param iPrpCIEndorClaimSchema PrpCIEndorClaimSchema
     *@throws Exception
     */
    public void setArr(PrpCIEndorClaimSchema iPrpCIEndorClaimSchema) throws Exception
    {
       try
       {
         schemas.add(iPrpCIEndorClaimSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *得到一条PrpCIEndorClaimSchema记录
     *@param index 下标
     *@return 一个PrpCIEndorClaimSchema对象
     *@throws Exception
     */
    public PrpCIEndorClaimSchema getArr(int index) throws Exception
    {
     PrpCIEndorClaimSchema prpCIEndorClaimSchema = null;
       try
       {
        prpCIEndorClaimSchema = (PrpCIEndorClaimSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpCIEndorClaimSchema;
     }
    /**
     *删除一条PrpCIEndorClaimSchema记录
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
      DBPrpCIEndorClaim dbPrpCIEndorClaim = new DBPrpCIEndorClaim();
      if (iLimitCount > 0 && dbPrpCIEndorClaim.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpCIEndorClaim.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpCIEndorClaim WHERE " + iWherePart; 
        schemas = dbPrpCIEndorClaim.findByConditions(strSqlStatement);
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
      DBPrpCIEndorClaim dbPrpCIEndorClaim = new DBPrpCIEndorClaim();
      if (iLimitCount > 0 && dbPrpCIEndorClaim.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpCIEndorClaim.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpCIEndorClaim WHERE " + iWherePart; 
        schemas = dbPrpCIEndorClaim.findByConditions(dbpool,strSqlStatement);
      }
    }
      
    /**
     *带dbpool的save方法
     *@param 无
     *@return 无
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpCIEndorClaim dbPrpCIEndorClaim = new DBPrpCIEndorClaim();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
      dbPrpCIEndorClaim.setSchema((PrpCIEndorClaimSchema)schemas.get(i));
      dbPrpCIEndorClaim.insert(dbpool);
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
     *@param iPolicyNo XX号
     *@return 无
     */
    public void cancel(DbPool dbpool,String Demandno) throws Exception
    {




    	String strSqlStatement = " DELETE FROM PrpCIEndorClaim WHERE Demandno= ?";
    	dbpool.prepareInnerStatement(strSqlStatement);
    	dbpool.setString(1, Demandno);
    	dbpool.executePreparedUpdate();
    	dbpool.closePreparedStatement();
     
    }
      
    /**
     * 不带dbpool的删除方法
     *@param iPolicyNo XX号
     *@return 无
     */
    public void cancel(String iPolicyNo ) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
      try
      {
        dbpool.beginTransaction();
        cancel(dbpool,iPolicyNo);
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
     * 带dbpool根据XX号获取数据
     *@param iPolicyNo XX号
     *@return 无
     */
    public void getData(String iPolicyNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
      getData(dbpool,iPolicyNo);
      dbpool.close();
    }
      
    /**
     * 不带dbpool根据XX号获取数据
     *@param dbpool 连接池
     *@param iPolicyNo XX号
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
        DBPrpCIEndorClaim dbPrpCIEndorClaim = new DBPrpCIEndorClaim();
        if (iLimitCount > 0 && dbPrpCIEndorClaim.getCount(dbpool,iWherePart,iWhereValue) > iLimitCount)
        {
            throw new UserException(-98,-1003,"BLPrpCIEndorClaim.query");
        }else
        {
            initArr();
            strSqlStatement = " SELECT * FROM PrpCIEndorClaim WHERE " + iWherePart;
            schemas = dbPrpCIEndorClaim.findByConditions(dbpool,strSqlStatement,iWhereValue);
        }
    }
    /**
     * 主函数
     * @param args 参数列表
     */
    public static void main(String[] args){
        
    }
}
