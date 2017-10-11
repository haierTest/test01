package com.sp.prpall.blsvr.misc;



import java.util.*;

import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.prpall.dbsvr.misc.DBPrpDServiceFeeInfo;
import com.sp.prpall.schema.PrpDServiceFeeInfoSchema;

/**
 * 定义服务区费用详细表的BL类
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>@createdate 2014-05-27</p>
 * @author BLGenerator 
 * @version 1.0
 */
public class BLPrpDServiceFeeInfo{
    private Vector schemas = new Vector();
    /**
     * 构造函数
     */       
    public BLPrpDServiceFeeInfo(){
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
     *增加一条PrpDServiceFeeInfoSchema记录
     *@param iPrpDServiceFeeInfoSchema PrpDServiceFeeInfoSchema
     *@throws Exception
     */
    public void setArr(PrpDServiceFeeInfoSchema iPrpDServiceFeeInfoSchema) throws Exception
    {
       try
       {
         schemas.add(iPrpDServiceFeeInfoSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *得到一条PrpDServiceFeeInfoSchema记录
     *@param index 下标
     *@return 一个PrpDServiceFeeInfoSchema对象
     *@throws Exception
     */
    public PrpDServiceFeeInfoSchema getArr(int index) throws Exception
    {
     PrpDServiceFeeInfoSchema prpDServiceFeeInfoSchema = null;
       try
       {
        prpDServiceFeeInfoSchema = (PrpDServiceFeeInfoSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpDServiceFeeInfoSchema;
     }
    /**
     *删除一条PrpDServiceFeeInfoSchema记录
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
      DBPrpDServiceFeeInfo dbPrpDServiceFeeInfo = new DBPrpDServiceFeeInfo();
      if (iLimitCount > 0 && dbPrpDServiceFeeInfo.getCount(iWherePart,bindValues) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpDServiceFeeInfo.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpDServiceFeeInfo WHERE " + iWherePart; 
        schemas = dbPrpDServiceFeeInfo.findByConditions(strSqlStatement, bindValues);
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
      DBPrpDServiceFeeInfo dbPrpDServiceFeeInfo = new DBPrpDServiceFeeInfo();
      if (iLimitCount > 0 && dbPrpDServiceFeeInfo.getCount(iWherePart,bindValues) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpDServiceFeeInfo.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpDServiceFeeInfo WHERE " + iWherePart; 
        schemas = dbPrpDServiceFeeInfo.findByConditions(dbpool, strSqlStatement, bindValues);
      }
    }
      
    /**
     *带dbpool的save方法
     *@param 无
     *@return 无
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpDServiceFeeInfo dbPrpDServiceFeeInfo = new DBPrpDServiceFeeInfo();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
      dbPrpDServiceFeeInfo.setSchema((PrpDServiceFeeInfoSchema)schemas.get(i));
      dbPrpDServiceFeeInfo.insert(dbpool);
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
      	
      	if("1".equals(SysConfig.getProperty("PLAT_FORM_NEW_DATA_SOURCE"))){
      		dbpool.open(SysConfig.CONST_PLATFORMNEWDATASOURCE);
      	}else{       		
        dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
      	}
      	
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
     *@param serverAreaCode 服务区代码
     *@return 无
     */
    public void cancel(DbPool dbpool, String serverAreaCode) throws Exception
    {
      String strSqlStatement = "";
      strSqlStatement = " DELETE FROM PrpDServiceFeeInfo WHERE serverAreaCode=?";
      dbpool.prepareInnerStatement(strSqlStatement);
      dbpool.setString(1, serverAreaCode);
	  dbpool.executePreparedUpdate();
	  dbpool.closePreparedStatement();
    }
      
    /**
     * 不带dbpool的删除方法
     *@param serverAreaCode 服务区代码
     *@return 无
     */
    public void cancel(String serverAreaCode) throws Exception
    {
      DbPool dbpool = new DbPool();
      try
      {
      	
      	if("1".equals(SysConfig.getProperty("PLAT_FORM_NEW_DATA_SOURCE"))){
      		dbpool.open(SysConfig.CONST_PLATFORMNEWDATASOURCE);
      	}else{       		
        dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
      	}
      	
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
     * 带dbpool的删除方法
     *@param dbpool    连接池
     *@param serverAreaCode 服务区代码
     *@param riskCode XXXXX种
     *@param feeTypes 费用类别
     *@return 无
     */
    public void cancel(DbPool dbpool, String serverAreaCode, String riskCode, String feeTypes) throws Exception
    {
      String strSqlStatement = "";
      strSqlStatement = " DELETE FROM PrpDServiceFeeInfo WHERE serverAreaCode=? AND riskCode=? AND feeTypes=?";
      dbpool.prepareInnerStatement(strSqlStatement);
      dbpool.setString(1, serverAreaCode);
      dbpool.setString(2, riskCode);
      dbpool.setString(3, feeTypes);
	  dbpool.executePreparedUpdate();
	  dbpool.closePreparedStatement();
    }
      
    /**
     * 不带dbpool的删除方法
     *@param serverAreaCode 服务区代码
     *@param riskCode XXXXX种
     *@param feeTypes 费用类别
     *@return 无
     */
    public void cancel(String serverAreaCode, String riskCode, String feeTypes) throws Exception
    {
      DbPool dbpool = new DbPool();
      try
      {
      	
      	if("1".equals(SysConfig.getProperty("PLAT_FORM_NEW_DATA_SOURCE"))){
      		dbpool.open(SysConfig.CONST_PLATFORMNEWDATASOURCE);
      	}else{       		
        dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
      	}
      	
        dbpool.beginTransaction();
        cancel(dbpool, serverAreaCode, riskCode, feeTypes);
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
     *@param serverAreaCode 服务区代码
     *@return 无
     */
    public void getData(String serverAreaCode) throws Exception
    {
    	DbPool dbpool = new DbPool();
        try
        {
          dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
          dbpool.beginTransaction();
          getData(dbpool, serverAreaCode);
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
     * 带dbpool根据主键获取数据
     *@param dbpool 连接池
     *@param serverAreaCode 服务区代码
     *@return 无
     */
    public void getData(DbPool dbpool, String serverAreaCode) throws Exception
    {
       String strWherePart = "serverAreaCode= ?";
       ArrayList arrWhereValue = new ArrayList();
       arrWhereValue.add(serverAreaCode);
       query(dbpool, strWherePart, 0, arrWhereValue);
    }
    
    
    
    /**
     * 不带dbpool根据主键获取数据
     *@param serverAreaCode 服务区代码
     *@param riskCode XXXXX种
     *@param feeTypes 费用类别
     *@return 无
     */
    public void getData(String serverAreaCode, String riskCode, String feeTypes) throws Exception
    {
    	DbPool dbpool = new DbPool();
        try
        {
          dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
          dbpool.beginTransaction();
          getData(dbpool, serverAreaCode, riskCode, feeTypes);
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
     * 带dbpool根据主键获取数据
     *@param dbpool 连接池
     *@param serverAreaCode 服务区代码
     *@param riskCode XXXXX种
     *@param feeTypes 费用类别
     *@return 无
     */
    public void getData(DbPool dbpool, String serverAreaCode, String riskCode, String feeTypes) throws Exception
    {
       String strWherePart = "serverAreaCode= ? AND riskCode= ? AND feeTypes= ?";
       ArrayList arrWhereValue = new ArrayList();
       arrWhereValue.add(serverAreaCode);
       arrWhereValue.add(riskCode);
       arrWhereValue.add(feeTypes);
       query(dbpool, strWherePart, 0, arrWhereValue);
    }
    
    /**
     * 带dbpool的update方法
     * @param dbpool 数据源
     * @return 无
     */
	public void update(DbPool dbpool) throws Exception{
		DBPrpDServiceFeeInfo dbPrpDServiceFeeInfo = new DBPrpDServiceFeeInfo();
		int i = 0;
		for(i = 0; i< schemas.size(); i++){
			dbPrpDServiceFeeInfo.setSchema((PrpDServiceFeeInfoSchema)schemas.get(i));
			dbPrpDServiceFeeInfo.update(dbpool);
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
        	
        	if("1".equals(SysConfig.getProperty("PLAT_FORM_NEW_DATA_SOURCE"))){
        		dbpool.open(SysConfig.CONST_PLATFORMNEWDATASOURCE);
        	}else{       		
		  dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        	}
        	
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
