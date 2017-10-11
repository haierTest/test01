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
     * ���캯��
     */       
    public BLPrpLifeTableInfo(){
    }

    /**
     *��ʼ����¼
     *@param ��
     *@return ��
     *@throws Exception
     */
    public void initArr() throws Exception
    {
      schemas = new Vector();
    }
    /**
     *����һ��PrpLifeTableInfoSchema��¼
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
     *�õ�һ��PrpLifeTableInfoSchema��¼
     *@param index �±�
     *@return һ��PrpLifeTableInfoSchema����
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
     *ɾ��һ��PrpLifeTableInfoSchema��¼
     *@param index �±�
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
     *�õ�schemas��¼��
     *@return schemas��¼��
     *@throws Exception
     */
    public int getSize() throws Exception
    {
      return this.schemas.size();
    }
    /**
     *���ղ�ѯ�����õ�һ���¼�������������¼����schemas����
     *@param iWherePart ��ѯ����(���������־�)
     *@throws UserException
     *@throws Exception
     */
    public void query(String iWherePart) throws UserException,Exception
    {
      this.query(iWherePart,Integer.parseInt(SysConst.getProperty("QUERY_LIMIT_COUNT").trim()));
    }
    /**
     *���ղ�ѯ�����ͼ�¼�����Ƶõ�һ���¼�������������¼����schemas����
     *@param iWherePart ��ѯ����(���������־�)
     *@param iLimitCount ��¼������(iLimitCount=0: ������)
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
     *���ղ�ѯ�����õ�һ���¼�������������¼����schemas����
     *@param dbpool ȫ�ֳ�
     *@param iWherePart ��ѯ����(���������־�)
     *@throws UserException
     *@throws Exception
     */
    public void query(DbPool dbpool,String iWherePart) throws UserException,Exception
    {
      this.query(dbpool,iWherePart,Integer.parseInt(SysConst.getProperty("QUERY_LIMIT_COUNT").trim()));
    }
    /**
     *���ղ�ѯ�����ͼ�¼�����Ƶõ�һ���¼�������������¼����schemas����
     *@param dbpool ȫ�ֳ�
     *@param iWherePart ��ѯ����(���������־�)
     *@param iLimitCount ��¼������(iLimitCount=0: ������)
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
     *��dbpool��save����
     *@param ��
     *@return ��
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
     *����dbpool��XXXXX�淽��
     *@param ��
     *@return ��
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
     *��dbpool��ɾ������
     *@param dbpool    ���ӳ�
     *@param iProposalNo XX����
     *@return ��
     */
    public void cancel(DbPool dbpool,String iProposalNo) throws Exception
    {
      String strSqlStatement = "";
      
      strSqlStatement = " DELETE FROM PrpLifeTableInfo WHERE ProposalNo= '" + iProposalNo + "'";
      
      dbpool.delete(strSqlStatement);
    }
      
    /**
     * ����dbpool��ɾ������
     *@param iProposalNo XX����
     *@return ��
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
     * ��dbpool����XX���Ż�ȡ����
     *@param iProposalNo XX����
     *@return ��
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
     * ����dbpool����XX���Ż�ȡ����
     *@param dbpool ���ӳ�
     *@param iProposalNo XX����
     *@return ��
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
     *���ղ�ѯ�����ͼ�¼�����Ƶõ�һ���¼�������������¼����schemas����
     *@param dbpool ȫ�ֳ�
     *@param iWherePart ��ѯ����(���������־�)
     *@param iWhereValue ��ѯ�������ֶ�ֵ
     *@param iLimitCount ��¼������(iLimitCount=0: ������)
     *@throws UserException
     *@throws Exception
     */
    public void query(DbPool dbpool,String iWherePart,ArrayList iWhereValue,int iLimitCount) throws UserException,Exception
    {
      if(iWherePart.indexOf("'null'")>-1){
          throw new Exception("��ѯ�����쳣������ϵϵͳ����Ա��");
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
     *����dbpool�ĸ��·���
     *@param ��
     *@return ��
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
     *��dbpool��update����
     *@param ��
     *@return ��
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
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
    
    /**
     * t��¼ת��Ϊc��¼
     *@param iPrpLifeTableInfoSchema t��¼
     *@param iPolicyNo XX��
     *@return c��¼
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
