package com.sp.indiv.ci.blsvr;

import java.util.Vector;

import com.sp.indiv.ci.dbsvr.DBCICarShipTaxPayMsg;
import com.sp.indiv.ci.schema.CICarShipTaxPayMsgSchema;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;


/**
 * 定义查询本地或上传外地完税信息表的BL类
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>@createdate 2008-01-21</p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLCICarShipTaxPayMsg{
    private Vector schemas = new Vector();
    /**
     * 构造函数
     */       
    public BLCICarShipTaxPayMsg(){
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
     *增加一条CICarShipTaxPayMsgSchema记录
     *@param iCICarShipTaxPayMsgSchema CICarShipTaxPayMsgSchema
     *@throws Exception
     */
    public void setArr(CICarShipTaxPayMsgSchema iCICarShipTaxPayMsgSchema) throws Exception
    {
       try
       {
         schemas.add(iCICarShipTaxPayMsgSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *得到一条CICarShipTaxPayMsgSchema记录
     *@param index 下标
     *@return 一个CICarShipTaxPayMsgSchema对象
     *@throws Exception
     */
    public CICarShipTaxPayMsgSchema getArr(int index) throws Exception
    {
    	CICarShipTaxPayMsgSchema iCICarShipTaxPayMsgSchema = null;
       try
       {
    	   iCICarShipTaxPayMsgSchema = (CICarShipTaxPayMsgSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return iCICarShipTaxPayMsgSchema;
     }
    /**
     *删除一条CICarShipTaxPayMsgSchema记录
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
      DBCICarShipTaxPayMsg dbCICarShipTaxPayMsg = new DBCICarShipTaxPayMsg();
      if (iLimitCount > 0 && dbCICarShipTaxPayMsg.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLCICarShipTaxPayMsg.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM CICarShipTaxPayMsg WHERE " + iWherePart; 
        schemas = dbCICarShipTaxPayMsg.findByConditions(strSqlStatement);
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
      DBCICarShipTaxPayMsg dbCICarShipTaxPayMsg = new DBCICarShipTaxPayMsg();
      if (iLimitCount > 0 && dbCICarShipTaxPayMsg.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"DBCICarShipTaxPayMsg.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM CICarShipTaxPayMsg WHERE " + iWherePart; 
        schemas = dbCICarShipTaxPayMsg.findByConditions(dbpool,strSqlStatement);
      }
    }
    
      
    /**
     *带dbpool的save方法
     *@param 无
     *@return 无
     */
    public void save(DbPool dbpool) throws Exception
    {
    	DBCICarShipTaxPayMsg dbCICarShipTaxPayMsg = new DBCICarShipTaxPayMsg();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
    	  dbCICarShipTaxPayMsg.setSchema((CICarShipTaxPayMsgSchema)schemas.get(i));
    	  dbCICarShipTaxPayMsg.insert(dbpool);
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
     * 主函数
     * @param args 参数列表
     */
    public static void main(String[] args){
        
    }
}

