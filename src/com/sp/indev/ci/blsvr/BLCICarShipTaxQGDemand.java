package com.sp.indiv.ci.blsvr;

import java.util.ArrayList;
import java.util.Vector;

import com.sp.indiv.ci.dbsvr.DBCICarShipTaxQGDemand;
import com.sp.indiv.ci.schema.CICarShipTaxQGDemandSchema;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;

/**
 * 定义CICarShipTaxQGDemand-全国车船税基本类型XX查询表的BL类
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>@createdate 2009-04-09</p>
 * @author Zhouxianli(JavaTools v1.0)
 * @updateauthor yangkun(JavaTools v1.1 - v1.2)
 * @lastversion v1.2
 */
public class BLCICarShipTaxQGDemand{
    private Vector schemas = new Vector();
    /**
     * 构造函数
     */       
    public BLCICarShipTaxQGDemand(){
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
     *增加一条CICarShipTaxQGDemandSchema记录
     *@param iCICarShipTaxQGDemandSchema CICarShipTaxQGDemandSchema
     *@throws Exception
     */
    public void setArr(CICarShipTaxQGDemandSchema iCICarShipTaxQGDemandSchema) throws Exception
    {
       try
       {
         schemas.add(iCICarShipTaxQGDemandSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *得到一条CICarShipTaxQGDemandSchema记录
     *@param index 下标
     *@return 一个CICarShipTaxQGDemandSchema对象
     *@throws Exception
     */
    public CICarShipTaxQGDemandSchema getArr(int index) throws Exception
    {
      CICarShipTaxQGDemandSchema ciCarShipTaxQGDemandSchema = null;
       try
       {
        ciCarShipTaxQGDemandSchema = (CICarShipTaxQGDemandSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return ciCarShipTaxQGDemandSchema;
     }
    /**
     *删除一条CICarShipTaxQGDemandSchema记录
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
     *按照查询条件和记录数限制得到一组记录数，并将这组记录赋给schemas对象
     *@param iWherePart 查询条件(包括排序字句)
     *@param iLimitCount 记录数限制(iLimitCount=0: 无限制)
     *@throws UserException
     *@throws Exception
     */
    public void query(String iWherePart,int iLimitCount) throws UserException,Exception
    {
      String strSqlStatement = "";
      DBCICarShipTaxQGDemand dbCICarShipTaxQGDemand = new DBCICarShipTaxQGDemand();
      if (iLimitCount > 0 && dbCICarShipTaxQGDemand.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLCICarShipTaxQGDemand.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM CICarShipTaxQGDemand WHERE " + iWherePart; 
        schemas = dbCICarShipTaxQGDemand.findByConditions(strSqlStatement);
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
      DBCICarShipTaxQGDemand dbCICarShipTaxQGDemand = new DBCICarShipTaxQGDemand();
      if (iLimitCount > 0 && dbCICarShipTaxQGDemand.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLCICarShipTaxQGDemand.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM CICarShipTaxQGDemand WHERE " + iWherePart; 
        schemas = dbCICarShipTaxQGDemand.findByConditions(dbpool,strSqlStatement);
      }
    }
      
    /**
     *带dbpool的save方法
     *@param 无
     *@return 无
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBCICarShipTaxQGDemand dbCICarShipTaxQGDemand = new DBCICarShipTaxQGDemand();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
      dbCICarShipTaxQGDemand.setSchema((CICarShipTaxQGDemandSchema)schemas.get(i));
      dbCICarShipTaxQGDemand.insert(dbpool);
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

      
      String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
      if ("1".equals(strSwitch)) {
          dbpool.open("platformNewDataSource");
      } else {
          dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
      }
      
      
      try
      {
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
     *@param iPolicyNo XX号
     *@return 无
     */
    public void cancel(DbPool dbpool,String iDemandNo) throws Exception
    {




    	String strSqlStatement = " DELETE FROM CICarShipTaxQGDemand WHERE DemandNo= ?";
    	dbpool.prepareInnerStatement(strSqlStatement);
    	dbpool.setString(1, iDemandNo);
    	dbpool.executePreparedUpdate();
    	dbpool.closePreparedStatement();	
     
    }
      
    /**
     * 不带dbpool的删除方法
     *@param iPolicyNo XX号
     *@return 无
     */
    public void cancel(String iDemandNo ) throws Exception
    {
      DbPool dbpool = new DbPool();

      
      String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
      if ("1".equals(strSwitch)) {
          dbpool.open("platformNewDataSource");
      } else {
          dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
      }
      
      try
      {
        dbpool.beginTransaction();
        cancel(dbpool,iDemandNo);
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
     * 带dbpool根据XX号获取数据
     *@param iPolicyNo XX号
     *@return 无
     */
    public void getData(String iDemandNo) throws Exception
    {
      DbPool dbpool = new DbPool();
  	  
		try {
		      
		      String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
		      if ("1".equals(strSwitch)) {
		          dbpool.open("platformNewDataSource");
		      } else {
		          dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
		      }
		      
			getData(dbpool,iDemandNo);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbpool.close();
		}
		
    }
      
    /**
     * 不带dbpool根据XX号获取数据
     *@param dbpool 连接池
     *@param iPolicyNo XX号
     *@return 无
     */
    public void getData(DbPool dbpool,String iDemandNo) throws Exception
    {
        
        
        
        
        
        String strWherePart = " DemandNo=? ";
        ArrayList arrWhereValue = new ArrayList();
        arrWhereValue.add(iDemandNo);
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
        DBCICarShipTaxQGDemand dbCICarShipTaxQGDemand = new DBCICarShipTaxQGDemand();
        if (iLimitCount > 0 && dbCICarShipTaxQGDemand.getCount(dbpool,iWherePart,iWhereValue) > iLimitCount)
        {
            throw new UserException(-98,-1003,"BLCICarShipTaxQGDemand.query");
        }else
        {
            initArr();
            strSqlStatement = " SELECT * FROM CICarShipTaxQGDemand WHERE " + iWherePart;
            schemas = dbCICarShipTaxQGDemand.findByConditions(dbpool,strSqlStatement,iWhereValue);
        }
    }
    
    /**
     *带dbpool的删除方法
     *@param dbpool    连接池
     *@param null null
     *@return 无
     */
    public void cancelByProposalno(DbPool dbpool,String iProposalNo) throws Exception
    {
      String strSqlStatement = "";
      
      strSqlStatement = " DELETE FROM CICarShipTaxQGDemand WHERE ProposalNo= '" + iProposalNo + "'";
      
      dbpool.delete(strSqlStatement);
    }
    
    /**
     *带dbpool的删除方法
     *@param dbpool    连接池
     *@param iProposalNo XX单号
     **@param iProposalNo XX查询码
     *@return 无
     */
    public void cancel(DbPool dbpool,String iProposalNo, String iDemandNo) throws Exception
    {
      String strSqlStatement = "";

      strSqlStatement = " DELETE FROM CICarShipTaxQGDemand WHERE ProposalNo= '" + iProposalNo + "'" + 
      					" AND DemandNo != '" + iDemandNo + "' ";
      
      dbpool.delete(strSqlStatement);
    }
    /**
     *带dbpool的update方法
     *@param dbpool    连接池
     *@param iProposalNo XX单号
     **@param iDemandNo XX查询码
     *@return 无
     *@author zhaoyingchao-ghq
     *@description 系统优化，解决XX修改XXXXX存时丢XX查询码问题
     */
    public void update(DbPool dbpool,String iProposalNo, String iDemandNo) throws Exception
    {
      String strSqlStatement = "";

      strSqlStatement = " UPDATE CICarShipTaxQGDemand SET ProposalNo = '' " +  
      					" WHERE ProposalNo = '" + iProposalNo + "' and DemandNo != '" + iDemandNo + "' ";
      
      dbpool.update(strSqlStatement);
    }
    
    /**
     *带dbpool的save方法
     *@param 无
     *@return 无
     */
    public void ProposalUpdate(DbPool dbpool, String iProposalNo, String iDemandNo) 
    	throws Exception
    {
      String strSqlStatement = "";

      strSqlStatement = " UPDATE CICarShipTaxQGDemand SET ProposalNo = '" + iProposalNo + "' " +  
      					" WHERE DemandNo = '" + iDemandNo + "' ";
      
      dbpool.delete(strSqlStatement);
    }
    
    /**
     * 不带dbpool根据null获取数据
     *@param dbpool 连接池
     *@param null null
     *@return 无
     */
    public void getDataByProposalNo(DbPool dbpool,String iProposalNo) throws Exception
    {
      String strWherePart = "";
      strWherePart = "ProposalNo = '" + iProposalNo + "'";
      query(dbpool,strWherePart,0);
    }
    /**
     * 不带dbpool根据null获取数据
     *@param dbpool 连接池
     *@param null null
     *@return 无
     */
    public void getDataByProposalNo(String iProposalNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
      if ("1".equals(strSwitch)) {
          dbpool.open("platformNewDataSource");
      } else {
          dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
      }
      
      getDataByProposalNo(dbpool,iProposalNo);
      dbpool.close();
    }
    /**
     * 不带dbpool根据null获取数据
     *@param dbpool 连接池
     *@param null null
     *@return 无
     */
    public void getDataByPolicyNo(DbPool dbpool,String iPolicyNo) throws Exception
    {
      String strWherePart = "";
      strWherePart = "PolicyNo = '" + iPolicyNo + "'";
      query(dbpool,strWherePart,0);
    }
    
    /**
     * 主函数
     * @param args 参数列表
     */
    public static void main(String[] args){
        
    }
}
