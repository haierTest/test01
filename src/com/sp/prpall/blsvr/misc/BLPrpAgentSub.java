package com.sp.prpall.blsvr.misc;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.util.*;

import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.prpall.dbsvr.misc.DBPrpAgentSub;
import com.sp.prpall.schema.PrpAgentSubSchema;

/**
 * 定义PrpAgentSub的BL类
 * 从pdm中取数据库信息,根据数据库表生成对应的BL类
 * <p>Title: SinosoftJavaTools v1.4</p>
 * <p>Description: 中科软Java源码生成工具</p>
 * <p>Company: Sinosoft</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>@createdate 2011-03-01</p>
 * @FirstAuthor: Zhouxianli(JavaTools v1.0)
 * @SecondAuthor: Yangkun(JavaTools v1.1->v1.2->v1.3->v1.4)
 * @LastVersion: v1.4
 * @UpdateList: 1.新增对CIXXXXX平台类的生成处理;
 *              2.修正特殊表生成类cancel()、getdata()方法时获取pdm表主键入参为null的问题;
 */
public class BLPrpAgentSub{
    private Vector schemas = new Vector();
    /**
     * 构造函数
     */       
    public BLPrpAgentSub(){
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
     *增加一条PrpAgentSubSchema记录
     *@param iPrpAgentSubSchema PrpAgentSubSchema
     *@throws Exception
     */
    public void setArr(PrpAgentSubSchema iPrpAgentSubSchema) throws Exception
    {
       try
       {
         schemas.add(iPrpAgentSubSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *得到一条PrpAgentSubSchema记录
     *@param index 下标
     *@return 一个PrpAgentSubSchema对象
     *@throws Exception
     */
    public PrpAgentSubSchema getArr(int index) throws Exception
    {
     PrpAgentSubSchema prpAgentSubSchema = null;
       try
       {
        prpAgentSubSchema = (PrpAgentSubSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpAgentSubSchema;
     }
    /**
     *删除一条PrpAgentSubSchema记录
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
      DBPrpAgentSub dbPrpAgentSub = new DBPrpAgentSub();
      if (iLimitCount > 0 && dbPrpAgentSub.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpAgentSub.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpAgentSub WHERE " + iWherePart; 
        schemas = dbPrpAgentSub.findByConditions(strSqlStatement);
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
      DBPrpAgentSub dbPrpAgentSub = new DBPrpAgentSub();
      if (iLimitCount > 0 && dbPrpAgentSub.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpAgentSub.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpAgentSub WHERE " + iWherePart; 
        schemas = dbPrpAgentSub.findByConditions(dbpool,strSqlStatement);
      }
    }
    /**
	 * 按照查询条件和记录数限制得到一组记录数，并将这组记录赋给schemas对象
	 * @param dbpool 全局池
	 * @param iWherePart 查询条件,传入条件均已绑定变量形式，问号个数与属性值个数一致
	 * @param iWhereValue 查询条件各字段值
	 * @param iLimitCount 记录数限制(iLimitCount=0: 无限制)
	 * @throws UserException
	 * @throws Exception
	 */
	public void query(DbPool dbpool, String iWherePart, ArrayList iWhereValue, int iLimitCount) throws UserException, Exception {
		String strSqlStatement = "";
		DBPrpAgentSub dbPrpAgentSub = new DBPrpAgentSub();
		if (iLimitCount > 0 && dbPrpAgentSub.getCount(dbpool, iWherePart, iWhereValue) > iLimitCount) {
			throw new UserException(-98, -1003, "BLPrpAgentSub.query");
		} else {
			initArr();
			strSqlStatement = " SELECT * FROM PrpAgentSub WHERE " + iWherePart;
			schemas = dbPrpAgentSub.findByConditions(dbpool, strSqlStatement, iWhereValue);
		}
	}
    /**
     *带dbpool的save方法
     *@param 无
     *@return 无
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpAgentSub dbPrpAgentSub = new DBPrpAgentSub();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
      dbPrpAgentSub.setSchema((PrpAgentSubSchema)schemas.get(i));
      dbPrpAgentSub.insert(dbpool);
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
        dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
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
     *@param iPolicyNO PolicyNO
     *@return 无
     */
    public void cancel(DbPool dbpool,String iPolicyNO) throws Exception
    {
      String strSqlStatement = "";
      strSqlStatement = " DELETE FROM PrpAgentSub WHERE PolicyNo= ?";
      dbpool.prepareInnerStatement(strSqlStatement);
      dbpool.setString(1, iPolicyNO);
      dbpool.executePreparedUpdate();
      dbpool.closePreparedStatement();
    }
      
    /**
     * 不带dbpool的删除方法
     *@param iPolicyNO PolicyNO
     *@return 无
     */
    public void cancel(String iPolicyNO ) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      try
      {
        dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
        dbpool.beginTransaction();
        cancel(dbpool,iPolicyNO);
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
     * 带dbpool根据PolicyNO获取数据
     *@param iPolicyNO PolicyNO
     *@return 无
     */
    public void getData(String iPolicyNO) throws Exception
    {
      DbPool dbpool = new DbPool();
      dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
      getData(dbpool,iPolicyNO);
      dbpool.close();
    }
      
    /**
     * 不带dbpool根据PolicyNO获取数据
     *@param dbpool 连接池
     *@param iPolicyNO PolicyNO
     *@return 无
     */
    public void getData(DbPool dbpool,String iPolicyNO) throws Exception
    {
      String strWherePart = " PolicyNo= ? ";
      ArrayList arrWhereValue = new ArrayList();
      arrWhereValue.add(iPolicyNO);
      query(dbpool, strWherePart, arrWhereValue, 0);
    }
    /**
     * 主函数
     * @param args 参数列表
     */
    public static void main(String[] args){
        
    }
}
