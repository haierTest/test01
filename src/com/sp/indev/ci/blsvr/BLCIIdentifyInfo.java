package com.sp.indiv.ci.blsvr;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.util.*;

import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.indiv.ci.dbsvr.DBCIIdentifyInfo;
import com.sp.indiv.ci.schema.CIIdentifyInfoSchema;

/**
 * 定义CIIdentifyInfo的BL类
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>@createdate 2015-01-11</p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLCIIdentifyInfo{
    private Vector schemas = new Vector();
    /**
     * 构造函数
     */       
    public BLCIIdentifyInfo(){
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
     *增加一条CIIdentifyInfoSchema记录
     *@param iCIIdentifyInfoSchema CIIdentifyInfoSchema
     *@throws Exception
     */
    public void setArr(CIIdentifyInfoSchema iCIIdentifyInfoSchema) throws Exception
    {
       try
       {
         schemas.add(iCIIdentifyInfoSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *得到一条CIIdentifyInfoSchema记录
     *@param index 下标
     *@return 一个CIIdentifyInfoSchema对象
     *@throws Exception
     */
    public CIIdentifyInfoSchema getArr(int index) throws Exception
    {
     CIIdentifyInfoSchema cIIdentifyInfoSchema = null;
       try
       {
        cIIdentifyInfoSchema = (CIIdentifyInfoSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return cIIdentifyInfoSchema;
     }
    /**
     *删除一条CIIdentifyInfoSchema记录
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
      DBCIIdentifyInfo dbCIIdentifyInfo = new DBCIIdentifyInfo();
      if (iLimitCount > 0 && dbCIIdentifyInfo.getCount(iWherePart,bindValues) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLCIIdentifyInfo.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM CIIdentifyInfo WHERE " + iWherePart; 
        schemas = dbCIIdentifyInfo.findByConditions(strSqlStatement, bindValues);
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
      DBCIIdentifyInfo dbCIIdentifyInfo = new DBCIIdentifyInfo();
      if (iLimitCount > 0 && dbCIIdentifyInfo.getCount(dbpool,iWherePart,bindValues) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLCIIdentifyInfo.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM CIIdentifyInfo WHERE " + iWherePart; 
        schemas = dbCIIdentifyInfo.findByConditions(dbpool, strSqlStatement, bindValues);
      }
    }
      
    /**
     *带dbpool的save方法
     *@param 无
     *@return 无
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBCIIdentifyInfo dbCIIdentifyInfo = new DBCIIdentifyInfo();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
      dbCIIdentifyInfo.setSchema((CIIdentifyInfoSchema)schemas.get(i));
      dbCIIdentifyInfo.insert(dbpool);
      }
    }
    /**
     *不带dbpool的XXXXX存方法
     *@param 无
     *@return 无
     */
    public void save(String Flag) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      
      try
      {
    	String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
        if ("1".equals(strSwitch)) {
             dbpool.open("platformNewDataSource");            
        } else {
             dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        }
        dbpool.beginTransaction();
        save(dbpool,Flag);
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
     *带dbpool的save方法
     *@param 无
     *@return 无
     *@注：flag为"CP",用来区别C表数据和CP表数据
     */
    public void save(DbPool dbpool,String Flag) throws Exception
    {
      DBCIIdentifyInfo dbCIIdentifyInfo = new DBCIIdentifyInfo();
      
      int i = 0;
      for(i = 0; i< schemas.size(); i++)
      {
    	  CIIdentifyInfoSchema ciIdentifyInfoSchema=(CIIdentifyInfoSchema)schemas.get(i);
    	  ciIdentifyInfoSchema.setFlag(Flag);
    	  dbCIIdentifyInfo.setSchema((CIIdentifyInfoSchema)schemas.get(i));
    	  dbCIIdentifyInfo.insert(dbpool);
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
    	String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
        if ("1".equals(strSwitch)) {
             dbpool.open("platformNewDataSource");            
        } else {
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
     *带dbpool的update方法
     *@param 无
     *@return 无
     */
    public void update(DbPool dbpool) throws Exception
    {
      DBCIIdentifyInfo dbCIIdentifyInfo = new DBCIIdentifyInfo();

      int i = 0;

      for(i = 0; i< schemas.size(); i++)
      {
    	  dbCIIdentifyInfo.setSchema((CIIdentifyInfoSchema)schemas.get(i));
    	  dbCIIdentifyInfo.update(dbpool);
      }
    }

    /**
     *不带dbpool的更新方法
     *@param 无
     *@return 无
     */
    public void update() throws Exception
    {
      DbPool dbpool = new DbPool();
      try
      {
    	String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
        if ("1".equals(strSwitch)) {
              dbpool.open("platformNewDataSource");            
        } else {
              dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        }
        dbpool.beginTransaction();
        update(dbpool);
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
     *@param businessNo BusinessNo
     *@param serialNo SerialNo
     *@return 无
     */
    public void cancel(DbPool dbpool, String businessNo, String serialNo) throws Exception
    {
      DBCIIdentifyInfo dbCIIdentifyInfo = new DBCIIdentifyInfo();
      dbCIIdentifyInfo.delete(dbpool,businessNo,serialNo);
    }
    /**
     * 不带dbpool的删除方法
     *@param businessNo BusinessNo
     *@param serialNo SerialNo
     *@return 无
     */
    public void cancelByFlag(String businessNo, String Flag) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      try
      {
    	  String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
          if ("1".equals(strSwitch)) {
               dbpool.open("platformNewDataSource");            
          } else {
               dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
          }
        dbpool.beginTransaction();
        cancelByFlag(dbpool, businessNo, Flag);
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
     *@param businessNo BusinessNo
     *@param serialNo SerialNo
     *@return 无
     */
    public void cancelByFlag(DbPool dbpool, String businessNo, String Flag) throws Exception
    {
      DBCIIdentifyInfo dbCIIdentifyInfo = new DBCIIdentifyInfo();
      dbCIIdentifyInfo.deleteByFlag(dbpool,businessNo,Flag);
    }
    /**
     * 不带dbpool的删除方法
     *@param businessNo BusinessNo
     *@param serialNo SerialNo
     *@return 无
     */
    public void cancel(String businessNo, String serialNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      try
      {
    	  String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
          if ("1".equals(strSwitch)) {
               dbpool.open("platformNewDataSource");            
          } else {
               dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
          }
        dbpool.beginTransaction();
        cancel(dbpool, businessNo, serialNo);
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
     *@param businessNo
     *@param serialNo SerialNo
     *@return 无
     */
    public void cancel(DbPool dbpool, String businessNo) throws Exception
    {
      DBCIIdentifyInfo dbCIIdentifyInfo = new DBCIIdentifyInfo();
      dbCIIdentifyInfo.delete(dbpool,businessNo);
    }
      
    /**
     * 不带dbpool的删除方法
     *@param businessNo
     *@param serialNo SerialNo
     *@return 无
     */
    public void cancel(String businessNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      try
      {
    	  String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
          if ("1".equals(strSwitch)) {
               dbpool.open("platformNewDataSource");            
          } else {
               dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
          }
        dbpool.beginTransaction();
        cancel(dbpool, businessNo);
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
     *@param businessNo BusinessNo
     *@param serialNo SerialNo
     *@return 无
     */
    public void getData(String businessNo, String serialNo,String Flag) throws Exception
    {
      DbPool dbpool = new DbPool();
      try{
	      String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
	      if ("1".equals(strSwitch)) {
	           dbpool.open("platformNewDataSource");            
	      } else {
	           dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
	      }
	      getData(dbpool, businessNo, serialNo, Flag);
      }catch(Exception e){
    	  
      }finally{
    	  dbpool.close();
      }
    }
    /**
     * 不带dbpool根据主键获取数据
     *@param businessNo BusinessNo
     *@param
     *@return 无
     */
    public void getData(String businessNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      try{
	      String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
	      if ("1".equals(strSwitch)) {
	           dbpool.open("platformNewDataSource");            
	      } else {
	           dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
	      }
	      getData(dbpool, businessNo);
      }catch(Exception e){
    	  
      }finally{
    	  dbpool.close();
      }
    }
    /**
     * 带dbpool根据businessNo获取数据
     *@param dbpool 连接池
     *@param businessNo BusinessNo
     *@return 无
     */
    public void getData(DbPool dbpool, String businessNo) throws Exception
    {
       String strWherePart = "";
       strWherePart = "businessNo=?";
	   ArrayList arrWhereValue = new ArrayList();
       arrWhereValue.add(businessNo);
       query(dbpool, strWherePart, 0, arrWhereValue);
    } 
    /**
     * 带dbpool根据主键获取数据
     *@param dbpool 连接池
     *@param businessNo BusinessNo
     *@param serialNo SerialNo
     *@return 无
     */
    public void getData(DbPool dbpool, String businessNo, String serialNo,String Flag) throws Exception
    {
       String strWherePart = "";
       strWherePart = "businessNo=? AND serialNo=? AND Flag=?";
	   ArrayList arrWhereValue = new ArrayList();
       arrWhereValue.add(businessNo);
       arrWhereValue.add(serialNo);
       arrWhereValue.add(Flag);
       query(dbpool, strWherePart, 0, arrWhereValue);
    }
    /**
     * 不带dbpool根据主键获取数据
     *@param businessNo BusinessNo
     *@param
     *@return 无
     */
    public void getDataByFlag(String businessNo, String Flag) throws Exception
    {
      DbPool dbpool = new DbPool();
      try{
	      String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
	      if ("1".equals(strSwitch)) {
	           dbpool.open("platformNewDataSource");            
	      } else {
	           dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
	      }
	      getDataByFlag(dbpool, businessNo,Flag);
      }catch(Exception e){
    	  
      }finally{
    	  dbpool.close();
      }
    }
    /**
     * 带dbpool根据主键获取数据
     *@param dbpool 连接池
     *@param businessNo BusinessNo
     *@param serialNo SerialNo
     *@return 无
     */
    public void getDataByFlag(DbPool dbpool, String businessNo, String Flag) throws Exception
    {
       String strWherePart = "";
       strWherePart = "businessNo=? AND Flag=?";
	   ArrayList arrWhereValue = new ArrayList();
       arrWhereValue.add(businessNo);
       arrWhereValue.add(Flag);
       query(dbpool, strWherePart, 0, arrWhereValue);
    }
    /**
	  * t记录转化为c记录
	      *@param
	      *@param iPolicyNo XX号
	  *@return 无
	  */
    public CIIdentifyInfoSchema Evaluate(CIIdentifyInfoSchema tCIIdentifyInfoSchema,String strPolicyNo)throws Exception{
    	CIIdentifyInfoSchema cCIIdentifyInfoSchema = new CIIdentifyInfoSchema();
    	
    	cCIIdentifyInfoSchema.setBusinessNo(strPolicyNo);
		cCIIdentifyInfoSchema.setSerialNo(tCIIdentifyInfoSchema.getSerialNo());
		cCIIdentifyInfoSchema.setIssueCode(tCIIdentifyInfoSchema.getIssueCode());
		cCIIdentifyInfoSchema.setUploadDate(tCIIdentifyInfoSchema.getUploadDate());
		cCIIdentifyInfoSchema.setNation(tCIIdentifyInfoSchema.getNation());
		cCIIdentifyInfoSchema.setSigner(tCIIdentifyInfoSchema.getSigner());
		cCIIdentifyInfoSchema.setCollectorCode(tCIIdentifyInfoSchema.getCollectorCode());
		cCIIdentifyInfoSchema.setIdentifyNumber(tCIIdentifyInfoSchema.getIdentifyNumber());
		cCIIdentifyInfoSchema.setFlag(tCIIdentifyInfoSchema.getFlag());
		
		return cCIIdentifyInfoSchema;
    	
    }
    
    /**
     * 主函数
     * @param args 参数列表
     */
    public static void main(String[] args){
        
    }
}
