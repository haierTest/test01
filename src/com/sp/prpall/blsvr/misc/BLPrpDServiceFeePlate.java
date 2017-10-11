package com.sp.prpall.blsvr.misc;



import java.util.*;

import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.prpall.dbsvr.misc.DBPrpDServiceFeePlate;
import com.sp.prpall.schema.PrpDServiceFeePlateSchema;

/**
 * 定义PrpDServiceFeePlate的BL类
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>@createdate 2014-09-01</p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLPrpDServiceFeePlate{
    private Vector schemas = new Vector();
    /**
     * 构造函数
     */       
    public BLPrpDServiceFeePlate(){
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
     *增加一条PrpDServiceFeePlateSchema记录
     *@param iPrpDServiceFeePlateSchema PrpDServiceFeePlateSchema
     *@throws Exception
     */
    public void setArr(PrpDServiceFeePlateSchema iPrpDServiceFeePlateSchema) throws Exception
    {
       try
       {
         schemas.add(iPrpDServiceFeePlateSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *得到一条PrpDServiceFeePlateSchema记录
     *@param index 下标
     *@return 一个PrpDServiceFeePlateSchema对象
     *@throws Exception
     */
    public PrpDServiceFeePlateSchema getArr(int index) throws Exception
    {
     PrpDServiceFeePlateSchema prpDServiceFeePlateSchema = null;
       try
       {
        prpDServiceFeePlateSchema = (PrpDServiceFeePlateSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpDServiceFeePlateSchema;
     }
    /**
     *删除一条PrpDServiceFeePlateSchema记录
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
      DBPrpDServiceFeePlate dbPrpDServiceFeePlate = new DBPrpDServiceFeePlate();
      if (iLimitCount > 0 && dbPrpDServiceFeePlate.getCount(iWherePart,bindValues) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpDServiceFeePlate.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpDServiceFeePlate WHERE " + iWherePart; 
        schemas = dbPrpDServiceFeePlate.findByConditions(strSqlStatement, bindValues);
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
      DBPrpDServiceFeePlate dbPrpDServiceFeePlate = new DBPrpDServiceFeePlate();
      if (iLimitCount > 0 && dbPrpDServiceFeePlate.getCount(iWherePart,bindValues) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpDServiceFeePlate.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpDServiceFeePlate WHERE " + iWherePart; 
        schemas = dbPrpDServiceFeePlate.findByConditions(dbpool, strSqlStatement, bindValues);
      }
    }
      
    /**
     *带dbpool的save方法
     *@param 无
     *@return 无
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpDServiceFeePlate dbPrpDServiceFeePlate = new DBPrpDServiceFeePlate();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
      dbPrpDServiceFeePlate.setSchema((PrpDServiceFeePlateSchema)schemas.get(i));
      dbPrpDServiceFeePlate.insert(dbpool);
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
        dbpool.open("platformNewDataSource");
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
     *@param serverAreaCode ServerAreaCode
     *@param riskCode RiskCode
     *@param motorcadeType MotorcadeType
     *@return 无
     */
    public void cancel(DbPool dbpool, String serverAreaCode, String riskCode, String motorcadeType) throws Exception
    {
      DBPrpDServiceFeePlate dbPrpDServiceFeePlate = new DBPrpDServiceFeePlate();
      dbPrpDServiceFeePlate.delete(dbpool, serverAreaCode,riskCode,motorcadeType);
    }
      
    /**
     * 不带dbpool的删除方法
     *@param serverAreaCode ServerAreaCode
     *@param riskCode RiskCode
     *@param motorcadeType MotorcadeType
     *@return 无
     */
    public void cancel(String serverAreaCode, String riskCode, String motorcadeType) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      try
      {
        dbpool.open("platformNewDataSource");
        dbpool.beginTransaction();
        cancel(dbpool, serverAreaCode, riskCode, motorcadeType);
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
     *@param serverAreaCode ServerAreaCode
     *@return 无
     */
    public void cancel(DbPool dbpool, String serverAreaCode) throws Exception
    {
     
      DBPrpDServiceFeePlate dbPrpDServiceFeePlate = new DBPrpDServiceFeePlate();
      dbPrpDServiceFeePlate.delete(dbpool, serverAreaCode);
    }
      
    /**
     * 不带dbpool的删除方法
     *@param serverAreaCode ServerAreaCode
     *@return 无
     */
    public void cancel(String serverAreaCode) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      try
      {
        dbpool.open("platformNewDataSource");
        dbpool.beginTransaction();
        cancel(dbpool, serverAreaCode);
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
     *@param serverAreaCode ServerAreaCode
     *@param riskCode RiskCode
     *@param motorcadeType MotorcadeType
     *@return 无
     */
    public void getData(String serverAreaCode, String riskCode, String motorcadeType) throws Exception
    {
      DbPool dbpool = new DbPool();
      dbpool.open("platformNewDataSource");
      getData(dbpool, serverAreaCode, riskCode, motorcadeType);
      dbpool.close();
    }
      
    /**
     * 带dbpool根据主键获取数据
     *@param dbpool 连接池
     *@param serverAreaCode ServerAreaCode
     *@param riskCode RiskCode
     *@param motorcadeType MotorcadeType
     *@return 无
     */
    public void getData(DbPool dbpool, String serverAreaCode, String riskCode, String motorcadeType) throws Exception
    {
       String strWherePart = "";
       ArrayList bindValues = new ArrayList();
       strWherePart = "serverAreaCode = ? AND riskCode = ? AND motorcadeType = ?";
       bindValues.add(serverAreaCode);
       bindValues.add(riskCode);
       bindValues.add(motorcadeType);
       query(dbpool, strWherePart, 0, bindValues);
    }
    
    /**
     * 不带dbpool根据主键获取数据
     *@param serverAreaCode ServerAreaCode
     *@return 无
     */
    public void getData(String serverAreaCode) throws Exception
    {
      DbPool dbpool = new DbPool();
      dbpool.open("platformNewDataSource");
      getData(dbpool, serverAreaCode);
      dbpool.close();
    }
      
    /**
     * 带dbpool根据主键获取数据
     *@param dbpool 连接池
     *@param serverAreaCode ServerAreaCode
     *@return 无
     */
    public void getData(DbPool dbpool, String serverAreaCode) throws Exception
    {
       String strWherePart = "";
       ArrayList bindValues = new ArrayList();
       strWherePart = "serverAreaCode = ?";
       bindValues.add(serverAreaCode);
       query(dbpool, strWherePart, 0, bindValues);
    }
    
    
    /**
     * 带dbpool的update方法
     * @param dbpool 数据源
     * @return 无
     */
	public void update(DbPool dbpool) throws Exception{
		DBPrpDServiceFeePlate dbPrpDServiceFeePlate = new DBPrpDServiceFeePlate();
		int i = 0;
		for(i = 0; i< schemas.size(); i++){
			dbPrpDServiceFeePlate.setSchema((PrpDServiceFeePlateSchema)schemas.get(i));
			dbPrpDServiceFeePlate.update(dbpool);
	    }
	}
	
   /**
    *不带dbpool的更新方法
    *@param 无
    *@return 无
    */
	public void update() throws Exception{
		DbPool dbpool = new DbPool();
		try{
		  dbpool.open("platformNewDataSource");
	      dbpool.beginTransaction();
	      update(dbpool);
	      dbpool.commitTransaction();
	    }catch (Exception e){
	      dbpool.rollbackTransaction();
	      throw e;
	    }finally{
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
