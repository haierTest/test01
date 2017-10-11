package com.sp.prpall.blsvr.misc;


import java.util.*;

import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.prpall.dbsvr.misc.DBPrpDServiceFeeGather;
import com.sp.prpall.schema.PrpDServiceFeeGatherSchema;

/**
 * ������������û��ܱ��BL��
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>@createdate 2014-05-27</p>
 * @author BLGenerator
 * @version 1.0 
 */
public class BLPrpDServiceFeeGather{
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */       
    public BLPrpDServiceFeeGather(){
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
     *����һ��PrpDServiceFeeGatherSchema��¼
     *@param iPrpDServiceFeeGatherSchema PrpDServiceFeeGatherSchema
     *@throws Exception
     */
    public void setArr(PrpDServiceFeeGatherSchema iPrpDServiceFeeGatherSchema) throws Exception
    {
       try
       {
         schemas.add(iPrpDServiceFeeGatherSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *�õ�һ��PrpDServiceFeeGatherSchema��¼
     *@param index �±�
     *@return һ��PrpDServiceFeeGatherSchema����
     *@throws Exception
     */
    public PrpDServiceFeeGatherSchema getArr(int index) throws Exception
    {
     PrpDServiceFeeGatherSchema prpDServiceFeeGatherSchema = null;
       try
       {
        prpDServiceFeeGatherSchema = (PrpDServiceFeeGatherSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpDServiceFeeGatherSchema;
     }
    /**
     *ɾ��һ��PrpDServiceFeeGatherSchema��¼
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
     *@param bindValues �󶨲���
     *@throws UserException
     *@throws Exception
     */
    public void query(String iWherePart, ArrayList bindValues) throws UserException,Exception
    {
       this.query(iWherePart, Integer.parseInt(SysConst.getProperty("QUERY_LIMIT_COUNT").trim()), bindValues);
    }
    /**
     *���ղ�ѯ�����ͼ�¼�����Ƶõ�һ���¼�������������¼����schemas����
     *@param iWherePart ��ѯ����(���������־�)
     *@param iLimitCount ��¼������(iLimitCount=0: ������)
     *@param bindValues �󶨲���
     *@throws UserException
     *@throws Exception
     */
    public void query(String iWherePart, int iLimitCount, ArrayList bindValues) throws UserException,Exception
    {
      String strSqlStatement = "";
      DBPrpDServiceFeeGather dbPrpDServiceFeeGather = new DBPrpDServiceFeeGather();
      if (iLimitCount > 0 && dbPrpDServiceFeeGather.getCount(iWherePart,bindValues) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpDServiceFeeGather.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpDServiceFeeGather WHERE " + iWherePart; 
        schemas = dbPrpDServiceFeeGather.findByConditions(strSqlStatement, bindValues);
      }
    }
    /**
     *���ղ�ѯ�����õ�һ���¼�������������¼����schemas����
     *@param dbpool ȫ�ֳ�
     *@param iWherePart ��ѯ����(���������־�)
     *@param bindValues �󶨲���
     *@throws UserException
     *@throws Exception
     */
    public void query(DbPool dbpool, String iWherePart, ArrayList bindValues) throws UserException,Exception
    {
       this.query(dbpool,iWherePart,Integer.parseInt(SysConst.getProperty("QUERY_LIMIT_COUNT").trim()),bindValues);
    }
    /**
     *���ղ�ѯ�����ͼ�¼�����Ƶõ�һ���¼�������������¼����schemas����
     *@param dbpool ȫ�ֳ�
     *@param iWherePart ��ѯ����(���������־�)
     *@param iLimitCount ��¼������(iLimitCount=0: ������)
     *@param bindValues �󶨲���
     *@throws UserException
     *@throws Exception
     */
    public void query(DbPool dbpool, String iWherePart, int iLimitCount, ArrayList bindValues) throws UserException,Exception
    {
      String strSqlStatement = "";
      DBPrpDServiceFeeGather dbPrpDServiceFeeGather = new DBPrpDServiceFeeGather();
      if (iLimitCount > 0 && dbPrpDServiceFeeGather.getCount(iWherePart,bindValues) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpDServiceFeeGather.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpDServiceFeeGather WHERE " + iWherePart; 
        schemas = dbPrpDServiceFeeGather.findByConditions(dbpool, strSqlStatement, bindValues);
      }
    }
      
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpDServiceFeeGather dbPrpDServiceFeeGather = new DBPrpDServiceFeeGather();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
      dbPrpDServiceFeeGather.setSchema((PrpDServiceFeeGatherSchema)schemas.get(i));
      dbPrpDServiceFeeGather.insert(dbpool);
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
      	
      	if("1".equals(SysConfig.getProperty("PLAT_FORM_NEW_DATA_SOURCE"))){
      		dbpool.open(SysConfig.CONST_PLATFORMNEWDATASOURCE);
      	}else{       		
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
     * ��dbpool��ɾ������
     *@param dbpool    ���ӳ�
     *@param serverAreaCode ����������
     *@param riskCode XXXXX��
     *@return ��
     */
    public void cancel(DbPool dbpool, String serverAreaCode, String riskCode) throws Exception
    {
      String strSqlStatement = "";
      strSqlStatement = " DELETE FROM PrpDServiceFeeGather WHERE serverAreaCode=? AND riskCode=?";
      dbpool.prepareInnerStatement(strSqlStatement);
      dbpool.setString(1, serverAreaCode);
      dbpool.setString(2, riskCode);
	  dbpool.executePreparedUpdate();
	  dbpool.closePreparedStatement();
    }
      
    /**
     * ����dbpool��ɾ������
     *@param serverAreaCode ����������
     *@param riskCode XXXXX��
     *@return ��
     */
    public void cancel(String serverAreaCode, String riskCode) throws Exception
    {
      DbPool dbpool = new DbPool();
      try
      {
      	
      	if("1".equals(SysConfig.getProperty("PLAT_FORM_NEW_DATA_SOURCE"))){
      		dbpool.open(SysConfig.CONST_PLATFORMNEWDATASOURCE);
      	}else{       		
        dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
      	}
      	
        dbpool.beginTransaction();
        cancel(dbpool, serverAreaCode, riskCode);
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
     * ��dbpool��ɾ������
     *@param dbpool    ���ӳ�
     *@param serverAreaCode ����������
     *@return ��
     */
    public void cancel(DbPool dbpool, String serverAreaCode) throws Exception
    {
      String strSqlStatement = "";
      strSqlStatement = " DELETE FROM PrpDServiceFeeGather WHERE serverAreaCode=?";
      dbpool.prepareInnerStatement(strSqlStatement);
      dbpool.setString(1, serverAreaCode);
	  dbpool.executePreparedUpdate();
	  dbpool.closePreparedStatement();
    }
      
    /**
     * ����dbpool��ɾ������
     *@param serverAreaCode ����������
     *@return ��
     */
    public void cancel(String serverAreaCode) throws Exception
    {
      DbPool dbpool = new DbPool();
      try
      {
      	
      	if("1".equals(SysConfig.getProperty("PLAT_FORM_NEW_DATA_SOURCE"))){
      		dbpool.open(SysConfig.CONST_PLATFORMNEWDATASOURCE);
      	}else{       		
        dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
      	}
      	
        dbpool.beginTransaction();
        cancel(dbpool, serverAreaCode);
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
     * ����dbpool����������ȡ����
     *@param serverAreaCode ����������
     *@return ��
     */
    public void getData(String serverAreaCode) throws Exception
    {
      DbPool dbpool = new DbPool();
      try
      {
        dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        getData(dbpool, serverAreaCode);
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
     * ��dbpool����������ȡ����
     *@param dbpool ���ӳ�
     *@param serverAreaCode ����������
     *@return ��
     */
    public void getData(DbPool dbpool, String serverAreaCode) throws Exception
    {
       String strWherePart = "serverAreaCode= ?";
       ArrayList arrWhereValue = new ArrayList();
       arrWhereValue.add(serverAreaCode);
       query(dbpool, strWherePart, 0, arrWhereValue);
    }
    
    
    /**
     * ����dbpool����������ȡ����
     *@param serverAreaCode ����������
     *@param riskCode XXXXX��
     *@return ��
     */
    public void getData(String serverAreaCode, String riskCode) throws Exception
    {
      DbPool dbpool = new DbPool();
      try
      {
        dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        getData(dbpool, serverAreaCode, riskCode);
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
     * ��dbpool����������ȡ����
     *@param dbpool ���ӳ�
     *@param serverAreaCode ����������
     *@param riskCode XXXXX��
     *@return ��
     */
    public void getData(DbPool dbpool, String serverAreaCode, String riskCode) throws Exception
    {
       String strWherePart = "serverAreaCode= ? AND riskCode= ?";
       ArrayList arrWhereValue = new ArrayList();
       arrWhereValue.add(serverAreaCode);
       arrWhereValue.add(riskCode);
       query(dbpool, strWherePart, 0, arrWhereValue);
    }
    
    /**
     * ��dbpool��update����
     * @param dbpool ����Դ
     * @return ��
     */
	public void update(DbPool dbpool) throws Exception{
		DBPrpDServiceFeeGather dbPrpDServiceFeeGather = new DBPrpDServiceFeeGather();
		int i = 0;
		for(i = 0; i< schemas.size(); i++){
			dbPrpDServiceFeeGather.setSchema((PrpDServiceFeeGatherSchema)schemas.get(i));
			dbPrpDServiceFeeGather.update(dbpool);
	    }
	}
	
   /**
    *����dbpool�ĸ��·���
    *@param ��
    *@return ��
    */
	public void update() throws Exception{
		DbPool dbpool = new DbPool();
		try{
        	
        	if("1".equals(SysConfig.getProperty("PLAT_FORM_NEW_DATA_SOURCE"))){
        		dbpool.open(SysConfig.CONST_PLATFORMNEWDATASOURCE);
        	}else{       		
		  dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        	}
        	
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
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
