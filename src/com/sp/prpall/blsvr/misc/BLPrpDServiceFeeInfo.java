package com.sp.prpall.blsvr.misc;



import java.util.*;

import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.prpall.dbsvr.misc.DBPrpDServiceFeeInfo;
import com.sp.prpall.schema.PrpDServiceFeeInfoSchema;

/**
 * ���������������ϸ���BL��
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>@createdate 2014-05-27</p>
 * @author BLGenerator 
 * @version 1.0
 */
public class BLPrpDServiceFeeInfo{
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */       
    public BLPrpDServiceFeeInfo(){
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
     *����һ��PrpDServiceFeeInfoSchema��¼
     *@param iPrpDServiceFeeInfoSchema PrpDServiceFeeInfoSchema
     *@throws Exception
     */
    public void setArr(PrpDServiceFeeInfoSchema iPrpDServiceFeeInfoSchema) throws Exception
    {
       try
       {
         schemas.add(iPrpDServiceFeeInfoSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *�õ�һ��PrpDServiceFeeInfoSchema��¼
     *@param index �±�
     *@return һ��PrpDServiceFeeInfoSchema����
     *@throws Exception
     */
    public PrpDServiceFeeInfoSchema getArr(int index) throws Exception
    {
     PrpDServiceFeeInfoSchema prpDServiceFeeInfoSchema = null;
       try
       {
        prpDServiceFeeInfoSchema = (PrpDServiceFeeInfoSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpDServiceFeeInfoSchema;
     }
    /**
     *ɾ��һ��PrpDServiceFeeInfoSchema��¼
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
      DBPrpDServiceFeeInfo dbPrpDServiceFeeInfo = new DBPrpDServiceFeeInfo();
      if (iLimitCount > 0 && dbPrpDServiceFeeInfo.getCount(iWherePart,bindValues) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpDServiceFeeInfo.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpDServiceFeeInfo WHERE " + iWherePart; 
        schemas = dbPrpDServiceFeeInfo.findByConditions(strSqlStatement, bindValues);
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
      DBPrpDServiceFeeInfo dbPrpDServiceFeeInfo = new DBPrpDServiceFeeInfo();
      if (iLimitCount > 0 && dbPrpDServiceFeeInfo.getCount(iWherePart,bindValues) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpDServiceFeeInfo.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpDServiceFeeInfo WHERE " + iWherePart; 
        schemas = dbPrpDServiceFeeInfo.findByConditions(dbpool, strSqlStatement, bindValues);
      }
    }
      
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpDServiceFeeInfo dbPrpDServiceFeeInfo = new DBPrpDServiceFeeInfo();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
      dbPrpDServiceFeeInfo.setSchema((PrpDServiceFeeInfoSchema)schemas.get(i));
      dbPrpDServiceFeeInfo.insert(dbpool);
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
     *@return ��
     */
    public void cancel(DbPool dbpool, String serverAreaCode) throws Exception
    {
      String strSqlStatement = "";
      strSqlStatement = " DELETE FROM PrpDServiceFeeInfo WHERE serverAreaCode=?";
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
     * ��dbpool��ɾ������
     *@param dbpool    ���ӳ�
     *@param serverAreaCode ����������
     *@param riskCode XXXXX��
     *@param feeTypes �������
     *@return ��
     */
    public void cancel(DbPool dbpool, String serverAreaCode, String riskCode, String feeTypes) throws Exception
    {
      String strSqlStatement = "";
      strSqlStatement = " DELETE FROM PrpDServiceFeeInfo WHERE serverAreaCode=? AND riskCode=? AND feeTypes=?";
      dbpool.prepareInnerStatement(strSqlStatement);
      dbpool.setString(1, serverAreaCode);
      dbpool.setString(2, riskCode);
      dbpool.setString(3, feeTypes);
	  dbpool.executePreparedUpdate();
	  dbpool.closePreparedStatement();
    }
      
    /**
     * ����dbpool��ɾ������
     *@param serverAreaCode ����������
     *@param riskCode XXXXX��
     *@param feeTypes �������
     *@return ��
     */
    public void cancel(String serverAreaCode, String riskCode, String feeTypes) throws Exception
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
        cancel(dbpool, serverAreaCode, riskCode, feeTypes);
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
          dbpool.beginTransaction();
          getData(dbpool, serverAreaCode);
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
     *@param feeTypes �������
     *@return ��
     */
    public void getData(String serverAreaCode, String riskCode, String feeTypes) throws Exception
    {
    	DbPool dbpool = new DbPool();
        try
        {
          dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
          dbpool.beginTransaction();
          getData(dbpool, serverAreaCode, riskCode, feeTypes);
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
     * ��dbpool����������ȡ����
     *@param dbpool ���ӳ�
     *@param serverAreaCode ����������
     *@param riskCode XXXXX��
     *@param feeTypes �������
     *@return ��
     */
    public void getData(DbPool dbpool, String serverAreaCode, String riskCode, String feeTypes) throws Exception
    {
       String strWherePart = "serverAreaCode= ? AND riskCode= ? AND feeTypes= ?";
       ArrayList arrWhereValue = new ArrayList();
       arrWhereValue.add(serverAreaCode);
       arrWhereValue.add(riskCode);
       arrWhereValue.add(feeTypes);
       query(dbpool, strWherePart, 0, arrWhereValue);
    }
    
    /**
     * ��dbpool��update����
     * @param dbpool ����Դ
     * @return ��
     */
	public void update(DbPool dbpool) throws Exception{
		DBPrpDServiceFeeInfo dbPrpDServiceFeeInfo = new DBPrpDServiceFeeInfo();
		int i = 0;
		for(i = 0; i< schemas.size(); i++){
			dbPrpDServiceFeeInfo.setSchema((PrpDServiceFeeInfoSchema)schemas.get(i));
			dbPrpDServiceFeeInfo.update(dbpool);
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
