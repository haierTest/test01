package com.sp.indiv.ci.blsvr;

import java.util.ArrayList;
import java.util.Vector;

import com.sp.indiv.ci.dbsvr.DBCIInsureDemand;
import com.sp.indiv.ci.dbsvr.DBCIInsurePmVehicle;
import com.sp.indiv.ci.schema.CIInsureDemandSchema;
import com.sp.indiv.ci.schema.CIInsurePmVehicleSchema;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;

/**
 * 定义XX查询主表-CIInsurePmVehicle的BL类
 * 
 * @createdate 2014-01-24
 * @author  fanjiangtao 2014-01-24
 * 
 */
public class BLCIInsurePmVehicle{
    private Vector schemas = new Vector();
    /**
     * 构造函数
     */       
    public BLCIInsurePmVehicle(){
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
     *增加一条CIInsurePmVehicleSchema记录
     *@param CIInsurePmVehicleSchema iCIInsurePmVehicleSchema
     *@throws Exception
     */
    public void setArr(CIInsurePmVehicleSchema iCIInsurePmVehicleSchema) throws Exception
    {
       try
       {
         schemas.add(iCIInsurePmVehicleSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *得到一条CIInsurePmVehicleSchema记录
     *@param index 下标
     *@return 一个CIInsurePmVehicleSchema对象
     *@throws Exception
     */
    public CIInsurePmVehicleSchema getArr(int index) throws Exception
    {
        CIInsurePmVehicleSchema cIInsurePmVehicleSchema = null;
       try
       {
           cIInsurePmVehicleSchema = (CIInsurePmVehicleSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return cIInsurePmVehicleSchema;
     }
    /**
     *删除一条CIInsurePmVehicleSchema记录
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
     * 按照查询条件得到一组记录数，并将这组记录赋给schemas对象
     * @param EngineNo    发动机号
     * @param CarMark     号牌号码
     * @param InsertDate  插入时间
     * @param RackNo      车辆识别代号（车架号/VIN码）
     * @throws UserException
     * @throws Exception
     */
    public void query(String EngineNo,String CarMark,String InsertDate,String RackNo) throws UserException,Exception
    {
        this.query( EngineNo, CarMark, InsertDate, RackNo,Integer.parseInt(SysConst.getProperty("QUERY_LIMIT_COUNT").trim()));
    }
    
    /**
     * 根据XX号获取数据,调用绑定变量方法
     *@param dbpool 连接池
     *@param iPolicyNo XX号
     *@return 无
     */
    public void query(int iLimitCount,String iPolicyNo) throws Exception
    {
    	DbPool dbpool = new DbPool();
        try {
            String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
            if ("1".equals(strSwitch)) {
                dbpool.open("platformNewDataSource");            
            } else {
                dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
            }
            String strWherePart = " PolicyNo= ? ";
            ArrayList arrWhereValue = new ArrayList();
            arrWhereValue.add(iPolicyNo);
            query(dbpool, strWherePart, arrWhereValue, iLimitCount);
        } catch (Exception e) 
        {
        	throw e;
        } finally {
            dbpool.close();
        }    
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
      DBCIInsurePmVehicle dbCIInsurePmVehicle = new DBCIInsurePmVehicle();
      if (iLimitCount > 0 && dbCIInsurePmVehicle.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLCIInsurePmVehicle.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM CIInsurePmVehicle WHERE " + iWherePart; 
        schemas = dbCIInsurePmVehicle.findByConditions(strSqlStatement);
      }
    }
    /**
     * 按照查询条件和记录数限制得到一组记录数，并将这组记录赋给schemas对象
     * @param EngineNo   发动机号
     * @param CarMark    号牌号码
     * @param InsertDate 插入时间
     * @param RackNo     车辆识别代号（车架号/VIN码）
     * @param iLimitCount  记录数限制(iLimitCount=0: 无限制)
     * @throws UserException
     * @throws Exception
     */
    public void query(String EngineNo,String CarMark,String InsertDate,String RackNo,int iLimitCount)throws UserException,Exception
    {
        String strSqlStatement = "";
        String iWherePart = "engineNo='"+EngineNo+"' and carMark='"+CarMark+"' and InsertDate=to_date('"+InsertDate+"','yyyy-mm-dd hh24:mi:ss') and RackNo='"+RackNo+"'";
        DBCIInsurePmVehicle dbCIInsurePmVehicle = new DBCIInsurePmVehicle();
        if (iLimitCount > 0 && dbCIInsurePmVehicle.getCount(iWherePart) > iLimitCount)
        {
          throw new UserException(-98,-1003,"BLCIInsurePmVehicle.query");
        }
        else
        {
          initArr();
          strSqlStatement = " SELECT * FROM CIInsurePmVehicle WHERE " + iWherePart; 
          schemas = dbCIInsurePmVehicle.findByConditions(strSqlStatement);
        }
    }
    /**
     *按照查询条件得到一组记录数，并将这组记录赋给schemas对象，从历史信息库获取
     *@param iWherePart 查询条件(包括排序字句)
     *@throws UserException
     *@throws Exception
     */
    public void queryHistory(String iWherePart) throws UserException,Exception
    {
       this.queryHistory(iWherePart,Integer.parseInt(SysConst.getProperty("QUERY_LIMIT_COUNT").trim()));
    }
    /**
     *按照查询条件和记录数限制得到一组记录数，并将这组记录赋给schemas对象，从历史信息库获取
     *@param iWherePart 查询条件(包括排序字句)
     *@param iLimitCount 记录数限制(iLimitCount=0: 无限制)
     *@throws UserException
     *@throws Exception
     */
    public void queryHistory(String iWherePart,int iLimitCount) throws UserException,Exception
    {
      String strSqlStatement = "";
      DBCIInsurePmVehicle dbCIInsurePmVehicle = new DBCIInsurePmVehicle();
      if (iLimitCount > 0 && dbCIInsurePmVehicle.getCountHistory(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLCIInsurePmVehicle.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM CIInsurePmVehicle WHERE " + iWherePart; 
        schemas = dbCIInsurePmVehicle.findByConditionsHistory(strSqlStatement);
      }
    }
       
    /**
     *按照查询条件得到一组记录数，并将这组记录赋给schemas对象。先从生产库获取相关信息，如果未取到，再从历史信息库获取。
     *@param iWherePart 查询条件(包括排序字句)
     *@throws UserException
     *@throws Exception
     */
    public void queryCurrentAndHistory(String iWherePart,int iLimitCount) throws UserException,Exception
    {
       
       this.query(iWherePart,iLimitCount);
       
       if(this.getSize() == 0){
    	   this.queryHistory(iWherePart,iLimitCount);
       }
    }
    
    /**
     *按照查询条件得到一组记录数，并将这组记录赋给schemas对象。先从生产库获取相关信息，如果未取到，再从历史信息库获取。
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
     * 按照查询条件得到一组记录数，并将这组记录赋给schemas对象
     * @param dbpool    全局池
     * @param EngineNo  发动机号
     * @param CarMark   号牌号码
     * @param InsertDate 插入时间
     * @param RackNo     车辆识别代号（车架号/VIN码）
     * @throws UserException
     * @throws Exception
     */
    public void query(DbPool dbpool,String EngineNo,String CarMark,String InsertDate,String RackNo)throws UserException,Exception
    {
        this.query(dbpool,EngineNo,CarMark,InsertDate,RackNo,Integer.parseInt(SysConst.getProperty("QUERY_LIMIT_COUNT").trim()));
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
      DBCIInsurePmVehicle dbCIInsurePmVehicle = new DBCIInsurePmVehicle();
      if (iLimitCount > 0 && dbCIInsurePmVehicle.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLCIInsurePmVehicle.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM CIInsurePmVehicle WHERE " + iWherePart; 
        schemas = dbCIInsurePmVehicle.findByConditions(dbpool,strSqlStatement);
      }
    }
    /**
     * 按照查询条件和记录数限制得到一组记录数，并将这组记录赋给schemas对象
     * @param dbpool     全局池
     * @param EngineNo   发动机号
     * @param CarMark    号牌号码
     * @param InsertDate 插入时间
     * @param RackNo     车辆识别代号（车架号/VIN码）
     * @param iLimitCount
     * @throws UserException
     * @throws Exception
     */
    public void query (DbPool dbpool,String EngineNo,String CarMark,String InsertDate,String RackNo,int iLimitCount )throws UserException,Exception
    {
        String strSqlStatement = "";
        String iWherePart = "engineNo='"+EngineNo+"' and carMark='"+CarMark+"' and InsertDate=to_date('"+InsertDate+"','yyyy-mm-dd hh24:mi:ss') and RackNo='"+RackNo+"'";
        DBCIInsurePmVehicle dbCIInsurePmVehicle = new DBCIInsurePmVehicle();
        if (iLimitCount > 0 && dbCIInsurePmVehicle.getCount(iWherePart) > iLimitCount)
        {
          throw new UserException(-98,-1003,"BLCIInsurePmVehicle.query");
        }
        else
        {
          initArr();
          strSqlStatement = " SELECT * FROM CIInsurePmVehicle WHERE " + iWherePart; 
          schemas = dbCIInsurePmVehicle.findByConditions(dbpool,strSqlStatement);
        }
    }
    public void updateByProposalNo(DbPool dbpool, String strWhere) throws Exception
    {
        DBCIInsurePmVehicle dbCIInsurePmVehicle = new DBCIInsurePmVehicle();
        
        int i = 0;
        
        for(i = 0; i< schemas.size(); i++)
        {
            dbCIInsurePmVehicle.setSchema((CIInsurePmVehicleSchema)schemas.get(i));
            dbCIInsurePmVehicle.update(dbpool, strWhere);
        }
    }
      
    /**
     *带dbpool的save方法
     *@param 无
     *@return 无
     */
    public void save(DbPool dbpool) throws Exception
    {
        DBCIInsurePmVehicle dbCIInsurePmVehicle = new DBCIInsurePmVehicle();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
          dbCIInsurePmVehicle.setSchema((CIInsurePmVehicleSchema)schemas.get(i));
          dbCIInsurePmVehicle.insert(dbpool);
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
      finally {
        dbpool.close();
      }
    }
    /**
     * 不带dbpool根据XX单号获取数据
     *@param demandno 
     *@return 无
     */
      public void getData(String iPmVehicleID) throws Exception
      {
          DbPool dbpool = new DbPool();
          try {
              dbpool.open("platformNewDataSource");            
              getData(dbpool, iPmVehicleID);
          } catch (Exception e) {
        	  e.printStackTrace();
          } finally {
              dbpool.close();
          }
      }

    /**
     * 带dbpool根据XX单号获取数据
     *@param dbpool 连接池
     *@param iDemandNo 
     *@return 无
     */
    public void getData(DbPool dbpool,String iPmVehicleID) throws Exception
    {
      String strWherePart = " PmVehicleID= ? ";
      ArrayList arrWhereValue = new ArrayList();
      arrWhereValue.add(iPmVehicleID);
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
        DBCIInsurePmVehicle dbCIInsurePmVehicle = new DBCIInsurePmVehicle();
        if (iLimitCount > 0 && dbCIInsurePmVehicle.getCount(dbpool,iWherePart,iWhereValue) > iLimitCount)
        {
            throw new UserException(-98,-1003,"BLCIInsurePmVehicle.query");
        }else
        {
            initArr();
            strSqlStatement = " SELECT * FROM CIInsurePmVehicle WHERE " + iWherePart;
            schemas = dbCIInsurePmVehicle.findByConditions(dbpool,strSqlStatement,iWhereValue);
        }
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
    public void query(String iWherePart,ArrayList iWhereValue) throws UserException,Exception
    {
    	 DbPool dbpool = new DbPool();
         try {
             String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
             if ("1".equals(strSwitch)) {
                 dbpool.open("platformNewDataSource");            
             } else {
                 dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
             }
             query(dbpool, iWherePart,iWhereValue,0);
         } catch (Exception e) {
        	 e.printStackTrace();
         } finally {
             dbpool.close();
         }
    }
    
    
    public void update() throws Exception
    {
      DbPool dbpool = new DbPool();

      dbpool.open("platformNewDataSource");

      try
      {
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
     *带dbpool的update方法
     *@param 无
     *@return 无
     */
    public void update(DbPool dbpool) throws Exception
    {
    	DBCIInsurePmVehicle dbCIInsurePmVehicle = new DBCIInsurePmVehicle();

      int i = 0;

      for(i = 0; i< schemas.size(); i++)
      {
    	  dbCIInsurePmVehicle.setSchema((CIInsurePmVehicleSchema)schemas.get(i));
    	  dbCIInsurePmVehicle.update(dbpool);
      }
    }
    
    
    /**
     * 主函数
     * @param args 参数列表
     */
    public static void main(String[] args){
        
    }
}
