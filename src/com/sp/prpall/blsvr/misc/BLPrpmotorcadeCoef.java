package com.sp.prpall.blsvr.misc;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;

import org.apache.commons.logging.LogFactory;

import java.util.*;

import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.utility.log.Log;
import com.sp.prpall.dbsvr.misc.DBPrpMotorcade;
import com.sp.prpall.dbsvr.misc.DBPrpmotorcadeCoef;
import com.sp.prpall.schema.PrpMotorcadeSchema;
import com.sp.prpall.schema.PrpmotorcadeCoefSchema;

/**
 * 定义PRPMOTORCADECOEF的BL类
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
public class BLPrpmotorcadeCoef{
    private final org.apache.commons.logging.Log logger = LogFactory.getLog(getClass());
    private Vector schemas = new Vector();
    /**
     * 构造函数
     */       
    public BLPrpmotorcadeCoef(){
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
     *增加一条PrpmotorcadeCoefSchema记录
     *@param iPrpmotorcadeCoefSchema PrpmotorcadeCoefSchema
     *@throws Exception
     */
    public void setArr(PrpmotorcadeCoefSchema iPrpmotorcadeCoefSchema) throws Exception
    {
      try
      {
        schemas.add(iPrpmotorcadeCoefSchema);
      }
      catch(Exception e)
      {
        throw e;
      }
    }
    /**
     *得到一条PrpmotorcadeCoefSchema记录
     *@param index 下标
     *@return 一个PrpmotorcadeCoefSchema对象
     *@throws Exception
     */
    public PrpmotorcadeCoefSchema getArr(int index) throws Exception
    {
      PrpmotorcadeCoefSchema prpmotorcadeCoefSchema = null;
       try
       {
         prpmotorcadeCoefSchema = (PrpmotorcadeCoefSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpmotorcadeCoefSchema;
     }
    /**
     *删除一条PrpmotorcadeCoefSchema记录
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
      DBPrpmotorcadeCoef dbPrpmotorcadeCoef = new DBPrpmotorcadeCoef();
      if (iLimitCount > 0 && dbPrpmotorcadeCoef.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpmotorcadeCoef.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpmotorcadeCoef WHERE " + iWherePart; 
        schemas = dbPrpmotorcadeCoef.findByConditions(strSqlStatement);
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
      DBPrpmotorcadeCoef dbPrpmotorcadeCoef = new DBPrpmotorcadeCoef();
      if (iLimitCount > 0 && dbPrpmotorcadeCoef.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpmotorcadeCoef.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpmotorcadeCoef WHERE " + iWherePart; 
        schemas = dbPrpmotorcadeCoef.findByConditions(dbpool,strSqlStatement);
      }
    }
      
    /**
     *带dbpool的save方法
     *@param 无
     *@return 无
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpmotorcadeCoef dbPrpmotorcadeCoef = new DBPrpmotorcadeCoef();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
        dbPrpmotorcadeCoef.setSchema((PrpmotorcadeCoefSchema)schemas.get(i));
        dbPrpmotorcadeCoef.insert(dbpool);
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
     *@param iSerialNo SERIALNO
     *@return 无
     */
    public void cancel(DbPool dbpool,String iSerialNo) throws Exception
    {
      String strSqlStatement = "";
      
      strSqlStatement = " DELETE FROM PrpmotorcadeCoef WHERE SerialNo= '" + iSerialNo + "'";
      
      dbpool.delete(strSqlStatement);
    }
      
    /**
     * 不带dbpool的删除方法
     *@param iSerialNo SERIALNO
     *@return 无
     */
    public void cancel(String iSerialNo ) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      try
      {
        dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
        dbpool.beginTransaction();
        cancel(dbpool,iSerialNo);
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
     * 带dbpool根据SERIALNO获取数据
     *@param iSerialNo SERIALNO
     *@return 无
     */
    public void getData(String iSerialNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
      getData(dbpool,iSerialNo);
      dbpool.close();
    }
      
    /**
     * 不带dbpool根据SERIALNO获取数据
     *@param dbpool 连接池
     *@param iSerialNo SERIALNO
     *@return 无
     */
    public void getData(DbPool dbpool,String iSerialNo) throws Exception
    {
      String strWherePart = "";
      strWherePart = "SerialNo= '" + iSerialNo + "'";
      query(dbpool,strWherePart,0);
    }
    /**
     * 主函数
     * @param args 参数列表
     */
    public static void main(String[] args){
        
    }
    
    /**
     *带dbpool的update方法
     *@param 无
     *@return 无
     */
	public void update(DbPool dbpool) throws Exception{
		DBPrpmotorcadeCoef dBPrpmotorcadeCoef = new DBPrpmotorcadeCoef();
		int i = 0;
		for(i = 0; i< schemas.size(); i++){
			dBPrpmotorcadeCoef.setSchema((PrpmotorcadeCoefSchema)schemas.get(i));
			dBPrpmotorcadeCoef.update(dbpool);
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
     *带dbpool的update方法
     *@param 无
     *@return 无
     */
	public void updateByContractNo(DbPool dbpool) throws Exception{
		DBPrpmotorcadeCoef dBPrpmotorcadeCoef = new DBPrpmotorcadeCoef();
		int i = 0;
		for(i = 0; i< schemas.size(); i++){
			dBPrpmotorcadeCoef.setSchema((PrpmotorcadeCoefSchema)schemas.get(i));
			dBPrpmotorcadeCoef.updateByContractNo(dbpool);
	    }
	}
}
