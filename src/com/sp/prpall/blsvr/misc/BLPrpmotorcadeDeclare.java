package com.sp.prpall.blsvr.misc;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.prpall.dbsvr.misc.DBPrpMotorcade;
import com.sp.prpall.dbsvr.misc.DBPrpmotorcadeDeclare;
import com.sp.prpall.schema.PrpMotorcadeSchema;
import com.sp.prpall.schema.PrpmotorcadeDeclareSchema;

/**
 * 定义PRPMOTORCADEDECLARE的BL类
 * 从pdm中取数据库信息,根据数据库表生成对应的BL类
 * <p>Title: SinosoftJavaTools v1.4</p>
 * <p>Description: 中科软Java源码生成工具</p>
 * <p>Company: Sinosoft</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>@createdate 2011-12-21</p>
 * @FirstAuthor: Zhouxianli(JavaTools v1.0)
 * @SecondAuthor: Yangkun(JavaTools v1.1->v1.2->v1.3->v1.4)
 * @LastVersion: v1.4.2
 * @UpdateList: 1.新增对CIXXXXX平台类的生成处理;
 *              2.修正特殊表生成类cancel()、getdata()方法时获取pdm表主键入参为null的问题;
 *              3.修正特殊表生成类部分import类为null的问题;
 *              4.新增log4j日志bl层类自动初始化;
 */
public class BLPrpmotorcadeDeclare{
    private final Log logger = LogFactory.getLog(getClass());
    private Vector schemas = new Vector();
    /**
     * 构造函数
     */       
    public BLPrpmotorcadeDeclare(){
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
     *增加一条PrpmotorcadeDeclareSchema记录
     *@param iPrpmotorcadeDeclareSchema PrpmotorcadeDeclareSchema
     *@throws Exception
     */
    public void setArr(PrpmotorcadeDeclareSchema iPrpmotorcadeDeclareSchema) throws Exception
    {
      try
      {
        schemas.add(iPrpmotorcadeDeclareSchema);
      }
      catch(Exception e)
      {
        throw e;
      }
    }
    /**
     *得到一条PrpmotorcadeDeclareSchema记录
     *@param index 下标
     *@return 一个PrpmotorcadeDeclareSchema对象
     *@throws Exception
     */
    public PrpmotorcadeDeclareSchema getArr(int index) throws Exception
    {
      PrpmotorcadeDeclareSchema prpmotorcadeDeclareSchema = null;
       try
       {
         prpmotorcadeDeclareSchema = (PrpmotorcadeDeclareSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpmotorcadeDeclareSchema;
     }
    /**
     *删除一条PrpmotorcadeDeclareSchema记录
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
      DBPrpmotorcadeDeclare dbPrpmotorcadeDeclare = new DBPrpmotorcadeDeclare();
      if (iLimitCount > 0 && dbPrpmotorcadeDeclare.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpmotorcadeDeclare.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpmotorcadeDeclare WHERE " + iWherePart; 
        schemas = dbPrpmotorcadeDeclare.findByConditions(strSqlStatement);
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
      DBPrpmotorcadeDeclare dbPrpmotorcadeDeclare = new DBPrpmotorcadeDeclare();
      if (iLimitCount > 0 && dbPrpmotorcadeDeclare.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpmotorcadeDeclare.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpmotorcadeDeclare WHERE " + iWherePart; 
        schemas = dbPrpmotorcadeDeclare.findByConditions(dbpool,strSqlStatement);
      }
    }
      
    /**
     *带dbpool的save方法
     *@param 无
     *@return 无
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpmotorcadeDeclare dbPrpmotorcadeDeclare = new DBPrpmotorcadeDeclare();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
        dbPrpmotorcadeDeclare.setSchema((PrpmotorcadeDeclareSchema)schemas.get(i));
        dbPrpmotorcadeDeclare.insert(dbpool);
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
     *@param iContractNo CONTRACTNO
     *@return 无
     */
    public void cancel(DbPool dbpool,String iContractNo) throws Exception
    {
      String strSqlStatement = "";
      
      strSqlStatement = " DELETE FROM PrpmotorcadeDeclare WHERE ContractNo= '" + iContractNo + "'";
      
      dbpool.delete(strSqlStatement);
    }
      
    /**
     * 不带dbpool的删除方法
     *@param iContractNo CONTRACTNO
     *@return 无
     */
    public void cancel(String iContractNo ) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      try
      {
        dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
        dbpool.beginTransaction();
        cancel(dbpool,iContractNo);
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
     * 带dbpool根据CONTRACTNO获取数据
     *@param iContractNo CONTRACTNO
     *@return 无
     */
    public void getData(String iContractNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
      getData(dbpool,iContractNo);
      dbpool.close();
    }
      
    /**
     * 不带dbpool根据CONTRACTNO获取数据
     *@param dbpool 连接池
     *@param iContractNo CONTRACTNO
     *@return 无
     */
    public void getData(DbPool dbpool,String iContractNo) throws Exception
    {
      String strWherePart = "";
      strWherePart = "ContractNo= '" + iContractNo + "'";
      query(dbpool,strWherePart,0);
    }  
	
    /**
     *带dbpool的update方法
     *@param 无
     *@return 无
     */
	public void update(DbPool dbpool) throws Exception{
		DBPrpmotorcadeDeclare dbPrpmotorcadeDeclare = new DBPrpmotorcadeDeclare();
		int i = 0;
		for(i = 0; i< schemas.size(); i++){
			dbPrpmotorcadeDeclare.setSchema((PrpmotorcadeDeclareSchema)schemas.get(i));
			dbPrpmotorcadeDeclare.update(dbpool);
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
		  dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
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
