package com.sp.indiv.ci.blsvr;

import java.util.ArrayList;
import java.util.Vector;

import com.sp.indiv.ci.dbsvr.DBCIInsureDemand;
import com.sp.indiv.ci.schema.CIInsureDemandSchema;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;

/**
 * 定义XX查询主表-CIInsureDemand的BL类
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>@createdate 2006-06-14</p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLCIInsureDemand{
    private Vector schemas = new Vector();
    /**
     * 构造函数
     */       
    public BLCIInsureDemand(){
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
     *增加一条CIInsureDemandSchema记录
     *@param iCIInsureDemandSchema CIInsureDemandSchema
     *@throws Exception
     */
    public void setArr(CIInsureDemandSchema iCIInsureDemandSchema) throws Exception
    {
       try
       {
         schemas.add(iCIInsureDemandSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *得到一条CIInsureDemandSchema记录
     *@param index 下标
     *@return 一个CIInsureDemandSchema对象
     *@throws Exception
     */
    public CIInsureDemandSchema getArr(int index) throws Exception
    {
     CIInsureDemandSchema cIInsureDemandSchema = null;
       try
       {
        cIInsureDemandSchema = (CIInsureDemandSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return cIInsureDemandSchema;
     }
    /**
     *删除一条CIInsureDemandSchema记录
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
     *根据查询SQL调用绑定变量方法
     *@param iWherePart 查询条件(包括排序字句)
     *@param iWhereValue 查询条件值
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
           
           query(dbpool, iWherePart, iWhereValue, Integer.parseInt(SysConst.getProperty("QUERY_LIMIT_COUNT").trim()));
       } catch (Exception e) 
       {
       	throw e;
       } finally {
           dbpool.close();
       }    
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
      DBCIInsureDemand dbCIInsureDemand = new DBCIInsureDemand();
      if (iLimitCount > 0 && dbCIInsureDemand.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLCIInsureDemand.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM CIInsureDemand WHERE " + iWherePart; 
        schemas = dbCIInsureDemand.findByConditions(strSqlStatement);
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
     *按照查询条件得到一组记录数，绑定变量
     *@param iWherePart 查询条件(包括排序字句)
     *@param iWhereValue 查询条件值
     *@throws UserException
     *@throws Exception
     */
    public void queryHistory(String iWherePart,ArrayList iWhereValue) throws UserException,Exception
    {
       DbPool dbpool = new DbPool();
       
       try {
           
           String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
           if ("1".equals(strSwitch)) {
               dbpool.open("platformNewDataSource");            
           } else {
               dbpool.open(SysConfig.CONST_HISTORYDATASOURCE);
           }
           
           query(dbpool,iWherePart,iWhereValue,Integer.parseInt(SysConst.getProperty("QUERY_LIMIT_COUNT").trim()));
        }catch (Exception e){
           	throw e;
        }finally{
            dbpool.close();
        } 
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
      DBCIInsureDemand dbCIInsureDemand = new DBCIInsureDemand();
      if (iLimitCount > 0 && dbCIInsureDemand.getCountHistory(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLCIInsureDemand.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM CIInsureDemand WHERE " + iWherePart; 
        schemas = dbCIInsureDemand.findByConditionsHistory(strSqlStatement);
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
     *按照查询条件得到一组记录数，绑定变量。
     *@param iWherePart 查询条件(包括排序字句)
     *@throws UserException
     *@throws Exception
     */
    public void queryCurrentAndHistory(String iWherePart,ArrayList iWhereValue) throws UserException,Exception
    {
       
       this.query(iWherePart,iWhereValue);
       
       if(this.getSize() == 0){
    	   this.queryHistory(iWherePart,iWhereValue);
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
      DBCIInsureDemand dbCIInsureDemand = new DBCIInsureDemand();
      if (iLimitCount > 0 && dbCIInsureDemand.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLCIInsureDemand.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM CIInsureDemand WHERE " + iWherePart; 
        schemas = dbCIInsureDemand.findByConditions(dbpool,strSqlStatement);
      }
    }
    
    public void updateByProposalNo(DbPool dbpool, String strWhere) throws Exception
    {
    	DBCIInsureDemand dbCIInsureDemand = new DBCIInsureDemand();
        
        int i = 0;
        
        for(i = 0; i< schemas.size(); i++)
        {
        dbCIInsureDemand.setSchema((CIInsureDemandSchema)schemas.get(i));
        dbCIInsureDemand.update(dbpool, strWhere);
        }
    }
      
    /**
     *带dbpool的save方法
     *@param 无
     *@return 无
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBCIInsureDemand dbCIInsureDemand = new DBCIInsureDemand();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
      dbCIInsureDemand.setSchema((CIInsureDemandSchema)schemas.get(i));
      dbCIInsureDemand.insert(dbpool);
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
     * 带dbpool根据XX单号获取数据
     *@param iProposalNo XX单号
     *@return 无
     */
      public void getData(String iProposalNo) throws Exception
      {
          DbPool dbpool = new DbPool();
          
          try {
              
              String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
              if ("1".equals(strSwitch)) {
                  dbpool.open("platformNewDataSource");            
              } else {
                  dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
              }
              
              getData(dbpool, iProposalNo);
          } catch (Exception e) {
          } finally {
              dbpool.close();
          }
          
      }

    /**
     * 不带dbpool根据XX单号获取数据
     *@param dbpool 连接池
     *@param iProposalNo XX单号
     *@return 无
     */
    public void getData(DbPool dbpool,String iProposalNo) throws Exception
    {
      
      
      
      
      String strWherePart = " ProposalNo= ? ";
      ArrayList arrWhereValue = new ArrayList();
      arrWhereValue.add(iProposalNo);
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
        DBCIInsureDemand dbCIInsureDemand = new DBCIInsureDemand();
        if (iLimitCount > 0 && dbCIInsureDemand.getCount(dbpool,iWherePart,iWhereValue) > iLimitCount)
        {
            throw new UserException(-98,-1003,"BLCIInsureDemand.query");
        }else
        {
            initArr();
            strSqlStatement = " SELECT * FROM CIInsureDemand WHERE " + iWherePart;
            schemas = dbCIInsureDemand.findByConditions(dbpool,strSqlStatement,iWhereValue);
        }
    }
    
    public void update(DbPool dbpool,String iDemandNo,String iProconfirmSequenceNo,String iStartDate,String iEndDate) throws Exception
    {
      
      String strSqlStatement = "";

      strSqlStatement = " UPDATE CIInsureDemand SET ProconfirmSequenceNo = '" + iProconfirmSequenceNo + "',ProconfirmStartDate = to_date('" + 
                        iStartDate +"','yyyy-mm-dd hh24:mi:ss'),ProconfirmEndDate = to_date('"+ iEndDate +"','yyyy-mm-dd hh24:mi:ss')" +
      					" WHERE DemandNo = '" + iDemandNo + "' ";
      dbpool.delete(strSqlStatement);
    }
    public void updateFlag(DbPool dbpool) throws Exception
    {
      String strSqlStatement = "";
      strSqlStatement = "Update CIInsureDemand Set Flag = '" + this.getArr(0).getFlag() + "' Where DemandNo = '" + this.getArr(0).getDemandNo()+ "'";
      dbpool.update(strSqlStatement);
    }
    
    /**
     * 主函数
     * @param args 参数列表
     */
    public static void main(String[] args){
        
    }
}
