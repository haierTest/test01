package com.sp.prpall.blsvr.misc;

import java.util.ArrayList;
import java.util.Vector;

import com.sp.prpall.dbsvr.misc.DBPrpBillCoreCard;
import com.sp.prpall.schema.PrpBillCoreCardSchema;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;

/**
 * 定义核心快钱信息表的BL类
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>@createdate 2009-04-28</p>
 * @author zhengxiaoluo
 * @version 1.0
 */
public class BLPrpBillCoreCard{
    private Vector schemas = new Vector();
    /**
     * 构造函数
     */       
    public BLPrpBillCoreCard(){
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
     *增加一条PrpBillCoreCardSchema记录
     *@param iPrpBillCoreCardSchema PrpBillCoreCardSchema
     *@throws Exception
     */
    public void setArr(PrpBillCoreCardSchema iPrpBillCoreCardSchema) throws Exception
    {
       try
       {
         schemas.add(iPrpBillCoreCardSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *得到一条PrpBillCoreCardSchema记录
     *@param index 下标
     *@return 一个PrpBillCoreCardSchema对象
     *@throws Exception
     */
    public PrpBillCoreCardSchema getArr(int index) throws Exception
    {
     PrpBillCoreCardSchema prpBillCoreCardSchema = null;
       try
       {
        prpBillCoreCardSchema = (PrpBillCoreCardSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpBillCoreCardSchema;
     }
    /**
     *删除一条PrpBillCoreCardSchema记录
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
      DBPrpBillCoreCard dbPrpBillCoreCard = new DBPrpBillCoreCard();
      if (iLimitCount > 0 && dbPrpBillCoreCard.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpBillCoreCard.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpBillCoreCard WHERE " + iWherePart; 
        schemas = dbPrpBillCoreCard.findByConditions(strSqlStatement);
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
      DBPrpBillCoreCard dbPrpBillCoreCard = new DBPrpBillCoreCard();
      if (iLimitCount > 0 && dbPrpBillCoreCard.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpBillCoreCard.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpBillCoreCard WHERE " + iWherePart; 
        schemas = dbPrpBillCoreCard.findByConditions(dbpool,strSqlStatement);
      }
    }
    /*
     * @param dbpool连接池
     * @param iBusinessNo业务号，生成XX数据时为大XX号，生成批单数据时为2700小XX批单号
     * @param iEditiType业务类型，PXX，E批单
     */
    public void echoPrpBillCoreCard(DbPool dbpool, String iBusinessNo, String iEditiType) throws Exception{
    	
    }
    /**
     *带dbpool的save方法
     *@param 无
     *@return 无
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpBillCoreCard dbPrpBillCoreCard = new DBPrpBillCoreCard();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
      dbPrpBillCoreCard.setSchema((PrpBillCoreCardSchema)schemas.get(i));
      dbPrpBillCoreCard.insert(dbpool);
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
     *@param iBusinessNo 业务号
     *@return 无
     */
    public void cancel(DbPool dbpool,String iBusinessNo) throws Exception
    {




    	String strSqlStatement = " DELETE FROM PrpBillCoreCard WHERE BusinessNo= ?";
    	dbpool.prepareInnerStatement(strSqlStatement);
    	dbpool.setString(1, iBusinessNo);
	dbpool.executePreparedUpdate();
	dbpool.closePreparedStatement();
     
    }
      
    /**
     * 不带dbpool的删除方法
     *@param iBusinessNo 业务号
     *@return 无
     */
    public void cancel(String iBusinessNo ) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
      try
      {
        dbpool.beginTransaction();
        cancel(dbpool,iBusinessNo);
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
     * 带dbpool根据null获取数据
     *@param iBusinessNo 业务号
     *@return 无
     */
    public void getData(String iBusinessNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
      getData(dbpool,iBusinessNo);
      dbpool.close();
    }
      
    /**
     * 不带dbpool根据null获取数据
     *@param dbpool 连接池
     *@param iBusinessNo 业务号
     *@return 无
     */
    public void getData(DbPool dbpool,String iBusinessNo) throws Exception
    {
        
        
        
        
        String strWherePart = " BusinessNo= ? ";
        ArrayList arrWhereValue = new ArrayList();
        arrWhereValue.add(iBusinessNo);
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
        DBPrpBillCoreCard dbPrpBillCoreCard = new DBPrpBillCoreCard();
        if (iLimitCount > 0 && dbPrpBillCoreCard.getCount(dbpool,iWherePart,iWhereValue) > iLimitCount)
        {
            throw new UserException(-98,-1003,"BLPrpBillCoreCard.query");
        }else
        {
            initArr();
            strSqlStatement = " SELECT * FROM PrpBillCoreCard WHERE " + iWherePart;
            schemas = dbPrpBillCoreCard.findByConditions(dbpool,strSqlStatement,iWhereValue);
        }
    }
    /**
     * 主函数
     * @param args 参数列表
     */
    public static void main(String[] args){
        
    }
}
