package com.sp.prpall.blsvr.misc;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.util.*;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.prpall.dbsvr.misc.DBAgDBranchMap;
import com.sp.prpall.schema.AgDBranchMapSchema;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.sp.utiall.dbsvr.DBPrpDkind;
import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.utility.string.Str;
/**
 * 定义业务归属表的BL类
 * 引入中间业务平台表agdbranchmap，执行中间业务平台转数时错误日志处理时，信XXXXX通网点转换
 * 从pdm中取数据库信息,根据数据库表生成对应的BL类
 * <p>Title: SinosoftJavaTools v1.4</p>
 * <p>Description: 中科软Java源码生成工具</p>
 * <p>Company: Sinosoft</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>@createdate 2011-05-27</p>
 * @FirstAuthor: Zhouxianli(JavaTools v1.0)
 * @SecondAuthor: Yangkun(JavaTools v1.1->v1.2->v1.3->v1.4)
 * @LastVersion: v1.4.3
 * @UpdateList: 1.新增对CIXXXXX平台类的生成处理;
 *              2.修正特殊表生成类cancel()、getdata()方法时获取pdm表主键入参为null的问题;
 *              3.修正特殊表生成类部分import类为null的问题;
 *              4.新增log4j日志bl层类自动初始化;
 *              5.getData方法新增try、catch、finally异常处理;
 */
public class BLAgDBranchMap{
    private final Log logger = LogFactory.getLog(getClass());
    private Vector schemas = new Vector();
    /**
     * 构造函数
     */       
    public BLAgDBranchMap(){
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
     *增加一条AgDBranchMapSchema记录
     *@param iAgDBranchMapSchema AgDBranchMapSchema
     *@throws Exception
     */
    public void setArr(AgDBranchMapSchema iAgDBranchMapSchema) throws Exception
    {
      try
      {
        schemas.add(iAgDBranchMapSchema);
      }
      catch(Exception e)
      {
        throw e;
      }
    }
    /**
     *得到一条AgDBranchMapSchema记录
     *@param index 下标
     *@return 一个AgDBranchMapSchema对象
     *@throws Exception
     */
    public AgDBranchMapSchema getArr(int index) throws Exception
    {
      AgDBranchMapSchema agDBranchMapSchema = null;
       try
       {
         agDBranchMapSchema = (AgDBranchMapSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return agDBranchMapSchema;
     }
    /**
     *删除一条AgDBranchMapSchema记录
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
    public Vector query(String iWherePart,int iLimitCount) throws UserException,Exception
    {
      String strSqlStatement = "";
      DBAgDBranchMap dbAgDBranchMap = new DBAgDBranchMap();
      if (iLimitCount > 0 && dbAgDBranchMap.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLAgDBranchMap.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM AgDBranchMap WHERE 1=1 " + iWherePart; 
        schemas = dbAgDBranchMap.findByConditions(strSqlStatement);
        return schemas;
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
      DBAgDBranchMap dbAgDBranchMap = new DBAgDBranchMap();
      if (iLimitCount > 0 && dbAgDBranchMap.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLAgDBranchMap.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM AgDBranchMap WHERE " + iWherePart; 
        schemas = dbAgDBranchMap.findByConditions(dbpool,strSqlStatement);
      }
    }
      
    /**
     *带dbpool的save方法
     *@param 无
     *@return 无
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBAgDBranchMap dbAgDBranchMap = new DBAgDBranchMap();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
        dbAgDBranchMap.setSchema((AgDBranchMapSchema)schemas.get(i));
        dbAgDBranchMap.insert(dbpool);
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
        dbpool.open(SysConst.getProperty("agentDataSource"));
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
     *@param iExtEnterpCode 行业企业代码
     *@return 无
     */
    public void cancel(DbPool dbpool,String iExtEnterpCode) throws Exception
    {
      String strSqlStatement = "";
      
      strSqlStatement = " DELETE FROM AgDBranchMap WHERE ExtEnterpCode= '" + iExtEnterpCode + "'";
      
      dbpool.delete(strSqlStatement);
    }
      
    /**
     * 不带dbpool的删除方法
     *@param iExtEnterpCode 行业企业代码
     *@return 无
     */
    public void cancel(String iExtEnterpCode ) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      try
      {
        dbpool.open(SysConst.getProperty("agentDataSource"));
        dbpool.beginTransaction();
        cancel(dbpool,iExtEnterpCode);
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
     * 带dbpool根据行业企业代码获取数据
     *@param iExtEnterpCode 行业企业代码
     *@return 无
     */
    public void getData(String iExtEnterpCode) throws Exception
    {
      DbPool dbpool = new DbPool();
      try
      {
        dbpool.open(SysConst.getProperty("agentDataSource"));
        getData(dbpool,iExtEnterpCode);
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
     * 不带dbpool根据行业企业代码获取数据
     *@param dbpool 连接池
     *@param iExtEnterpCode 行业企业代码
     *@return 无
     */
    public void getData(DbPool dbpool,String iExtEnterpCode) throws Exception
    {
      String strWherePart = "";
      strWherePart = "ExtEnterpCode= '" + iExtEnterpCode + "'";
      query(dbpool,strWherePart,0);
    }
    /**
     * 主函数
     * @param args 参数列表
     */
    public static void main(String[] args){
        
    }
}
