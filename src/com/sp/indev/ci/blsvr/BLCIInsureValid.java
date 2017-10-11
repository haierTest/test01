package com.sp.indiv.ci.blsvr;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.util.*;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.indiv.ci.dbsvr.DBCIInsureValid;
import com.sp.indiv.ci.schema.CIInsureValidSchema;

/**
 * 定义XX确认主表-CIInsureValid的BL类
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>@createdate 2006-06-14</p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLCIInsureValid{
    private Vector schemas = new Vector();
    /**
     * 构造函数
     */       
    public BLCIInsureValid(){
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
     *增加一条CIInsureValidSchema记录
     *@param iCIInsureValidSchema CIInsureValidSchema
     *@throws Exception
     */
    public void setArr(CIInsureValidSchema iCIInsureValidSchema) throws Exception
    {
       try
       {
         schemas.add(iCIInsureValidSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *得到一条CIInsureValidSchema记录
     *@param index 下标
     *@return 一个CIInsureValidSchema对象
     *@throws Exception
     */
    public CIInsureValidSchema getArr(int index) throws Exception
    {
     CIInsureValidSchema cIInsureValidSchema = null;
       try
       {
        cIInsureValidSchema = (CIInsureValidSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return cIInsureValidSchema;
     }
    /**
     *删除一条CIInsureValidSchema记录
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
      DBCIInsureValid dbCIInsureValid = new DBCIInsureValid();
      if (iLimitCount > 0 && dbCIInsureValid.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLCIInsureValid.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM CIInsureValid WHERE " + iWherePart; 
        schemas = dbCIInsureValid.findByConditions(strSqlStatement);
      }
    }
    
    
    /**
     *按照查询条件得到一组记录数，并将这组记录赋给schemas对象
     *@param iWherePart 查询条件(包括排序字句)
     *@throws UserException
     *@throws Exception
     */
    public void queryHistory(String iWherePart) throws UserException,Exception
    {
       this.queryHistory(iWherePart,Integer.parseInt(SysConst.getProperty("QUERY_LIMIT_COUNT").trim()));
    }
    /**
     *按照查询条件和记录数限制得到一组记录数，并将这组记录赋给schemas对象
     *@param iWherePart 查询条件(包括排序字句)
     *@param iLimitCount 记录数限制(iLimitCount=0: 无限制)
     *@throws UserException
     *@throws Exception
     */
    public void queryHistory(String iWherePart,int iLimitCount) throws UserException,Exception
    {
      String strSqlStatement = "";
      DBCIInsureValid dbCIInsureValid = new DBCIInsureValid();
      if (iLimitCount > 0 && dbCIInsureValid.getCountHistory(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLCIInsureValid.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM CIInsureValid WHERE " + iWherePart; 
        schemas = dbCIInsureValid.findByConditionsHistory(strSqlStatement);
      }
    }
        
    /**
     *按照查询条件得到一组记录数，并将这组记录赋给schemas对象.先从生产库查询，再从历史库查询
     *@param iWherePart 查询条件(包括排序字句)
     *@throws UserException
     *@throws Exception
     */
    public void queryCurrentAndHistory(String iWherePart) throws UserException,Exception
    {
       this.query(iWherePart);
       
       if(this.getSize() == 0){
    	   this.queryHistory(iWherePart);
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
      DBCIInsureValid dbCIInsureValid = new DBCIInsureValid();
      if (iLimitCount > 0 && dbCIInsureValid.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLCIInsureValid.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM CIInsureValid WHERE " + iWherePart; 
        schemas = dbCIInsureValid.findByConditions(dbpool,strSqlStatement);
      }
    }
      
    /**
     *带dbpool的save方法
     *@param 无
     *@return 无
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBCIInsureValid dbCIInsureValid = new DBCIInsureValid();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
      dbCIInsureValid.setSchema((CIInsureValidSchema)schemas.get(i));
      dbCIInsureValid.insert(dbpool);
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
      }
      finally {
        dbpool.close();
      }
    }
    
    
    /**
	 *带dbpool的update方法
	 *@param 无
	 *@return 无
	 */
	public void update(DbPool dbpool) throws Exception {
		DBCIInsureValid dbCIInsureValid = new DBCIInsureValid();

		int i = 0;

		for (i = 0; i < schemas.size(); i++) {
			dbCIInsureValid.setSchema((CIInsureValidSchema) schemas.get(i));
			dbCIInsureValid.update(dbpool);
		}
	}

	/**
	 *不带dbpool的update方法
	 *@param 无
	 *@return 无
	 */
	public void update() throws Exception {
		DbPool dbpool = new DbPool();

		try {
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			dbpool.beginTransaction();
			update(dbpool);
			dbpool.commitTransaction();
		} catch (Exception e) {
			dbpool.rollbackTransaction();
		} finally {
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
