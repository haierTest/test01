package com.sp.indiv.ci.blsvr;

import java.util.ArrayList;
import java.util.Vector;

import com.sp.indiv.ci.dbsvr.DBCICarshipTaxDemand;
import com.sp.indiv.ci.schema.CICarshipTaxDemandSchema;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;

/**
 * 定义cicarshiptaxdemand的BL类
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>@createdate 2007-06-21</p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLCICarshipTaxDemand{
    private Vector schemas = new Vector();
    /**
     * 构造函数
     */       
    public BLCICarshipTaxDemand(){
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
     *增加一条CicarshiptaxdemandSchema记录
     *@param iCicarshiptaxdemandSchema CicarshiptaxdemandSchema
     *@throws Exception
     */
    public void setArr(CICarshipTaxDemandSchema iCicarshiptaxdemandSchema) throws Exception
    {
       try
       {
         schemas.add(iCicarshiptaxdemandSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *得到一条CicarshiptaxdemandSchema记录
     *@param index 下标
     *@return 一个CicarshiptaxdemandSchema对象
     *@throws Exception
     */
    public CICarshipTaxDemandSchema getArr(int index) throws Exception
    {
    	CICarshipTaxDemandSchema cicarshiptaxdemandSchema = null;
       try
       {
        cicarshiptaxdemandSchema = (CICarshipTaxDemandSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return cicarshiptaxdemandSchema;
     }
    /**
     *删除一条CicarshiptaxdemandSchema记录
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
     *按照查询条件得到一组记录数，并将这组记录赋给schemas对象（绑定变量）
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
		}catch (Exception e) 
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
      DBCICarshipTaxDemand dbCicarshiptaxdemand = new DBCICarshipTaxDemand();
      if (iLimitCount > 0 && dbCicarshiptaxdemand.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLCicarshiptaxdemand.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM Cicarshiptaxdemand WHERE " + iWherePart; 
        schemas = dbCicarshiptaxdemand.findByConditions(strSqlStatement);
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
      DBCICarshipTaxDemand dbCicarshiptaxdemand = new DBCICarshipTaxDemand();
      if (iLimitCount > 0 && dbCicarshiptaxdemand.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLCicarshiptaxdemand.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM Cicarshiptaxdemand WHERE " + iWherePart; 
        schemas = dbCicarshiptaxdemand.findByConditions(dbpool,strSqlStatement);
      }
    }
      
    /**
     *带dbpool的save方法
     *@param 无
     *@return 无
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBCICarshipTaxDemand dbCicarshiptaxdemand = new DBCICarshipTaxDemand();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
      dbCicarshiptaxdemand.setSchema((CICarshipTaxDemandSchema)schemas.get(i));
      dbCicarshiptaxdemand.insert(dbpool);
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
     *@param null null
     *@return 无
     */
    public void cancel(DbPool dbpool,String demandno) throws Exception
    {




    	String strSqlStatement = " DELETE FROM Cicarshiptaxdemand WHERE demandno= ?"; 
    	dbpool.prepareInnerStatement(strSqlStatement);
    	dbpool.setString(1, demandno);
	    dbpool.executePreparedUpdate();
	    dbpool.closePreparedStatement();
     
    }
      
    /**
     * 不带dbpool的删除方法
     *@param null null
     *@return 无
     */
    public void cancel(String demandno ) throws Exception
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
        cancel(dbpool,demandno);
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
     * 带dbpool根据null获取数据
     *@param null null
     *@return 无
     */
    public void getData(String demandno) throws Exception
    {
      DbPool dbpool = new DbPool();

    	
		try {
			
			String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
			if ("1".equals(strSwitch)) {
				dbpool.open("platformNewDataSource");
			} else {
				dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			}
			
			getData(dbpool,demandno);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbpool.close();
		}
		
    }
      
    /**
     * 不带dbpool根据null获取数据
     *@param dbpool 连接池
     *@param null null
     *@return 无
     */
    public void getData(DbPool dbpool,String demandno) throws Exception
    {
        
        
        
        
        String strWherePart = " demandno= ? ";
        ArrayList arrWhereValue = new ArrayList();
        arrWhereValue.add(demandno);
        query(dbpool, strWherePart, arrWhereValue, 0);
        
    }
    
    /**
     *按照查询条件和记录数限制得到一组记录数，并将这组记录赋给schemas对象
     *@author yangxiaodong 20100602
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
        DBCICarshipTaxDemand dbCICarshipTaxDemand = new DBCICarshipTaxDemand();
        if (iLimitCount > 0 && dbCICarshipTaxDemand.getCount(dbpool,iWherePart,iWhereValue) > iLimitCount)
        {
            throw new UserException(-98,-1003,"BLCICarshipTaxDemand.query");
        }else
        {
            initArr();
            strSqlStatement = " SELECT * FROM CICarshipTaxDemand WHERE " + iWherePart;
            schemas = dbCICarshipTaxDemand.findByConditions(dbpool,strSqlStatement,iWhereValue);
        }
    }
    /**
     *带dbpool的删除方法
     *@param dbpool    连接池
     *@param null null
     *@return 无
     */
    public void cancelByProposalno(DbPool dbpool,String Proposalno) throws Exception
    {
      String strSqlStatement = "";
      
      strSqlStatement = " DELETE FROM CICarshipTaxDemand WHERE ProposalNo= '" + Proposalno + "'";
      
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

      strSqlStatement = " DELETE FROM CICarshipTaxDemand WHERE ProposalNo= '" + iProposalNo + "'" + 
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

      strSqlStatement = " UPDATE CICarshipTaxDemand SET ProposalNo = '' " +  
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

      strSqlStatement = " UPDATE CICarshipTaxDemand SET ProposalNo = '" + iProposalNo + "' " +  
      					" WHERE DemandNo = '" + iDemandNo + "' ";
      
      dbpool.delete(strSqlStatement);
    }
    
    /**
     * 不带dbpool根据null获取数据
     *@param dbpool 连接池
     *@param null null
     *@return 无
     */
    public void getDataByProposalno(DbPool dbpool,String Proposalno) throws Exception
    {
      String strWherePart = "";
      strWherePart = "PROPOSALNO= '" + Proposalno + "'";
      query(dbpool,strWherePart,0);
    }
    /**
     * 不带dbpool根据null获取数据
     *@param dbpool 连接池
     *@param null null
     *@return 无
     */
    public void getDataByProposalno(String Proposalno) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
      if ("1".equals(strSwitch)) {
          dbpool.open("platformNewDataSource");
      } else {
          dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
      }
      
      getDataByProposalno(dbpool,Proposalno);
      dbpool.close();
    }
    /**
     * 不带dbpool根据null获取数据
     *@param dbpool 连接池
     *@param null null
     *@return 无
     */
    public void getDataByPolicyno(DbPool dbpool,String Policyno) throws Exception
    {
      String strWherePart = "";
      strWherePart = "POLICYNO= '" + Policyno + "'";
      query(dbpool,strWherePart,0);
    }
    
    /**
     * 主函数
     * @param args 参数列表
     */
    public static void main(String[] args){
        
    }
}
