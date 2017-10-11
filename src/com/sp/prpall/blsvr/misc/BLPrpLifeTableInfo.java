package com.sp.prpall.blsvr.misc;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.util.*;

import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.prpall.dbsvr.misc.DBPrpLifeTableInfo;
import com.sp.prpall.schema.PrpClifeTableInfoSchema;
import com.sp.prpall.schema.PrpLifeTableInfoSchema;


public class BLPrpLifeTableInfo{
    private Vector schemas = new Vector();
    /**
     * 构造函数
     */       
    public BLPrpLifeTableInfo(){
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
     *增加一条PrpLifeTableInfoSchema记录
     *@param iPrpLifeTableInfoSchema PrpLifeTableInfoSchema
     *@throws Exception
     */
    public void setArr(PrpLifeTableInfoSchema iPrpLifeTableInfoSchema) throws Exception
    {
      try
      {
        schemas.add(iPrpLifeTableInfoSchema);
      }
      catch(Exception e)
      {
        throw e;
      }
    }
    /**
     *得到一条PrpLifeTableInfoSchema记录
     *@param index 下标
     *@return 一个PrpLifeTableInfoSchema对象
     *@throws Exception
     */
    public PrpLifeTableInfoSchema getArr(int index) throws Exception
    {
      PrpLifeTableInfoSchema prpLifeTableInfoSchema = null;
       try
       {
         prpLifeTableInfoSchema = (PrpLifeTableInfoSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpLifeTableInfoSchema;
     }
    /**
     *删除一条PrpLifeTableInfoSchema记录
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
      DBPrpLifeTableInfo dbPrpLifeTableInfo = new DBPrpLifeTableInfo();
      if (iLimitCount > 0 && dbPrpLifeTableInfo.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpLifeTableInfo.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpLifeTableInfo WHERE " + iWherePart; 
        schemas = dbPrpLifeTableInfo.findByConditions(strSqlStatement);
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
      DBPrpLifeTableInfo dbPrpLifeTableInfo = new DBPrpLifeTableInfo();
      if (iLimitCount > 0 && dbPrpLifeTableInfo.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpLifeTableInfo.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpLifeTableInfo WHERE " + iWherePart; 
        schemas = dbPrpLifeTableInfo.findByConditions(dbpool,strSqlStatement);
      }
    }
      
    /**
     *带dbpool的save方法
     *@param 无
     *@return 无
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpLifeTableInfo dbPrpLifeTableInfo = new DBPrpLifeTableInfo();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
        dbPrpLifeTableInfo.setSchema((PrpLifeTableInfoSchema)schemas.get(i));
        dbPrpLifeTableInfo.insert(dbpool);
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
     *@param iProposalNo XX单号
     *@return 无
     */
    public void cancel(DbPool dbpool,String iProposalNo) throws Exception
    {
      String strSqlStatement = "";
      
      strSqlStatement = " DELETE FROM PrpLifeTableInfo WHERE ProposalNo= '" + iProposalNo + "'";
      
      dbpool.delete(strSqlStatement);
    }
      
    /**
     * 不带dbpool的删除方法
     *@param iProposalNo XX单号
     *@return 无
     */
    public void cancel(String iProposalNo ) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      try
      {
        dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
        dbpool.beginTransaction();
        cancel(dbpool,iProposalNo);
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
     * 带dbpool根据XX单号获取数据
     *@param iProposalNo XX单号
     *@return 无
     */
    public void getData(String iProposalNo) throws Exception
    {
    	
		DbPool dbpool = new DbPool();
		try {
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			getData(dbpool, iProposalNo);
		} catch (Exception e) {
			e.printStackTrace();
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
     *@author zhanghaoyu 20141020 
     *按照查询条件和记录数限制得到一组记录数，并将这组记录赋给schemas对象
     *@param dbpool 全局池
     *@param iWherePart 查询条件(包括排序字句)
     *@param iWhereValue 查询条件各字段值
     *@param iLimitCount 记录数限制(iLimitCount=0: 无限制)
     *@throws UserException
     *@throws Exception
     */
    public void query(DbPool dbpool,String iWherePart,ArrayList iWhereValue,int iLimitCount) throws UserException,Exception
    {
      if(iWherePart.indexOf("'null'")>-1){
          throw new Exception("查询条件异常，请联系系统管理员！");
      }
      String strSqlStatement = "";
      DBPrpLifeTableInfo dbPrpLifeTableInfo = new DBPrpLifeTableInfo();
      if (iLimitCount > 0 && dbPrpLifeTableInfo.getCount(dbpool, iWherePart, iWhereValue) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpLifeTableInfo.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpLifeTableInfo WHERE " + iWherePart; 
        schemas = dbPrpLifeTableInfo.findByConditions(dbpool,strSqlStatement, iWhereValue);
      }
    }    
    

    public void updateProfitMarginBC(DbPool dbpool,String proposalNo) throws Exception{
  	    DBPrpLifeTableInfo dbPrpLifeTableInfo = new DBPrpLifeTableInfo();

  	    int i = 0;

  	    for(i = 0; i< schemas.size(); i++)
  	    {
  	    	dbPrpLifeTableInfo.setSchema((PrpLifeTableInfoSchema)schemas.get(i));
  	    	dbPrpLifeTableInfo.updateProfitMarginBC(dbpool,proposalNo);
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
      dbpool.open(SysConfig.getProperty("DDCCDATASOURCE"));
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
      DBPrpLifeTableInfo dbPrpLifeTableInfo = new DBPrpLifeTableInfo();
      int i = 0;
      for(i = 0; i< schemas.size(); i++)
      {
    	dbPrpLifeTableInfo.setSchema((PrpLifeTableInfoSchema)schemas.get(i));
    	dbPrpLifeTableInfo.update(dbpool);
      }
    }
    /**
     * 主函数
     * @param args 参数列表
     */
    public static void main(String[] args){
        
    }
    
    /**
     * t记录转化为c记录
     *@param iPrpLifeTableInfoSchema t记录
     *@param iPolicyNo XX号
     *@return c记录
     */
    public PrpClifeTableInfoSchema Evaluate(PrpLifeTableInfoSchema iPrpLifeTableInfoSchema, String iPolicyNo) {
        PrpClifeTableInfoSchema prpClifeTableInfoSchema = new PrpClifeTableInfoSchema();
        prpClifeTableInfoSchema.setPolicyNo(iPolicyNo);
        prpClifeTableInfoSchema.setBusinessClass(iPrpLifeTableInfoSchema.getBusinessClass());
        prpClifeTableInfoSchema.setBusinessKind(iPrpLifeTableInfoSchema.getBusinessKind());
        prpClifeTableInfoSchema.setCostBusinessClass(iPrpLifeTableInfoSchema.getCostBusinessClass());
        prpClifeTableInfoSchema.setCostBusinessClassBC(iPrpLifeTableInfoSchema.getCostBusinessClassBC());
        prpClifeTableInfoSchema.setCostBusinessClassBCM(iPrpLifeTableInfoSchema.getCostBusinessClassBCM());
        prpClifeTableInfoSchema.setCostMargin(iPrpLifeTableInfoSchema.getCostMargin());
        prpClifeTableInfoSchema.setCostMarginBC(iPrpLifeTableInfoSchema.getCostMarginBC());
        prpClifeTableInfoSchema.setCostMarginBCM(iPrpLifeTableInfoSchema.getCostMarginBCM());
        prpClifeTableInfoSchema.setDepositRate(iPrpLifeTableInfoSchema.getDepositRate());
        prpClifeTableInfoSchema.setExpenseSpace(iPrpLifeTableInfoSchema.getExpenseSpace());
        prpClifeTableInfoSchema.setExpenseType(iPrpLifeTableInfoSchema.getExpenseType());
        prpClifeTableInfoSchema.setFeeClass(iPrpLifeTableInfoSchema.getFeeClass());
        prpClifeTableInfoSchema.setFinalBusinessClass(iPrpLifeTableInfoSchema.getFinalBusinessClass());
        prpClifeTableInfoSchema.setFinalBusinessClassBC(iPrpLifeTableInfoSchema.getFinalBusinessClassBC());
        prpClifeTableInfoSchema.setFinalBusinessClassBCM(iPrpLifeTableInfoSchema.getFinalBusinessClassBCM());
        prpClifeTableInfoSchema.setFinalPayRate(iPrpLifeTableInfoSchema.getFinalPayRate());
        prpClifeTableInfoSchema.setFinalPayRateBC(iPrpLifeTableInfoSchema.getFinalPayRateBC());
        prpClifeTableInfoSchema.setFinalPayRateBCM(iPrpLifeTableInfoSchema.getFinalPayRateBCM());
        prpClifeTableInfoSchema.setFixedProfitMargin(iPrpLifeTableInfoSchema.getFixedProfitMargin());
        prpClifeTableInfoSchema.setFlag(iPrpLifeTableInfoSchema.getFlag());
        prpClifeTableInfoSchema.setMaxFeeRate(iPrpLifeTableInfoSchema.getMaxFeeRate());
        prpClifeTableInfoSchema.setMaxFeeRateBC(iPrpLifeTableInfoSchema.getMaxFeeRateBC());
        prpClifeTableInfoSchema.setProfitBusinessClassBC(iPrpLifeTableInfoSchema.getProfitBusinessClassBC());
        prpClifeTableInfoSchema.setProfitBusinessKind(iPrpLifeTableInfoSchema.getProfitBusinessKind());
        prpClifeTableInfoSchema.setProfitMargin(iPrpLifeTableInfoSchema.getProfitMargin());
        prpClifeTableInfoSchema.setProfitMarginBC(iPrpLifeTableInfoSchema.getProfitMarginBC());
        prpClifeTableInfoSchema.setProposalNo(iPrpLifeTableInfoSchema.getProposalNo());
        prpClifeTableInfoSchema.setRemark(iPrpLifeTableInfoSchema.getRemark());
        prpClifeTableInfoSchema.setRiskPremium(iPrpLifeTableInfoSchema.getRiskPremium());
        prpClifeTableInfoSchema.setSalesFeeRate(iPrpLifeTableInfoSchema.getSalesFeeRate());
        prpClifeTableInfoSchema.setZeroProfitPremium(iPrpLifeTableInfoSchema.getZeroProfitPremium());
        
    	prpClifeTableInfoSchema.setFinalPayRateM(iPrpLifeTableInfoSchema.getFinalPayRateM());
    	prpClifeTableInfoSchema.setCostMarginM(iPrpLifeTableInfoSchema.getCostMarginM());
    	prpClifeTableInfoSchema.setFinalBusinessClassM(iPrpLifeTableInfoSchema.getFinalBusinessClassM());
    	prpClifeTableInfoSchema.setCostBusinessClassM(iPrpLifeTableInfoSchema.getCostBusinessClassM());
        
    	
        
    	prpClifeTableInfoSchema.setMotorCadeType(iPrpLifeTableInfoSchema.getMotorCadeType());
        
    	
        
    	prpClifeTableInfoSchema.setSuggestWays(iPrpLifeTableInfoSchema.getSuggestWays());
        
    	
        return prpClifeTableInfoSchema;
    }
    
}
