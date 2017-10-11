package com.sp.prpall.blsvr.misc;



import java.util.*;

import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.prpall.dbsvr.misc.DBPrpDServiceFeePlate;
import com.sp.prpall.schema.PrpDServiceFeePlateSchema;

/**
 * ����PrpDServiceFeePlate��BL��
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>@createdate 2014-09-01</p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLPrpDServiceFeePlate{
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */       
    public BLPrpDServiceFeePlate(){
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
     *����һ��PrpDServiceFeePlateSchema��¼
     *@param iPrpDServiceFeePlateSchema PrpDServiceFeePlateSchema
     *@throws Exception
     */
    public void setArr(PrpDServiceFeePlateSchema iPrpDServiceFeePlateSchema) throws Exception
    {
       try
       {
         schemas.add(iPrpDServiceFeePlateSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *�õ�һ��PrpDServiceFeePlateSchema��¼
     *@param index �±�
     *@return һ��PrpDServiceFeePlateSchema����
     *@throws Exception
     */
    public PrpDServiceFeePlateSchema getArr(int index) throws Exception
    {
     PrpDServiceFeePlateSchema prpDServiceFeePlateSchema = null;
       try
       {
        prpDServiceFeePlateSchema = (PrpDServiceFeePlateSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpDServiceFeePlateSchema;
     }
    /**
     *ɾ��һ��PrpDServiceFeePlateSchema��¼
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
      DBPrpDServiceFeePlate dbPrpDServiceFeePlate = new DBPrpDServiceFeePlate();
      if (iLimitCount > 0 && dbPrpDServiceFeePlate.getCount(iWherePart,bindValues) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpDServiceFeePlate.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpDServiceFeePlate WHERE " + iWherePart; 
        schemas = dbPrpDServiceFeePlate.findByConditions(strSqlStatement, bindValues);
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
      DBPrpDServiceFeePlate dbPrpDServiceFeePlate = new DBPrpDServiceFeePlate();
      if (iLimitCount > 0 && dbPrpDServiceFeePlate.getCount(iWherePart,bindValues) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpDServiceFeePlate.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpDServiceFeePlate WHERE " + iWherePart; 
        schemas = dbPrpDServiceFeePlate.findByConditions(dbpool, strSqlStatement, bindValues);
      }
    }
      
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpDServiceFeePlate dbPrpDServiceFeePlate = new DBPrpDServiceFeePlate();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
      dbPrpDServiceFeePlate.setSchema((PrpDServiceFeePlateSchema)schemas.get(i));
      dbPrpDServiceFeePlate.insert(dbpool);
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
        dbpool.open("platformNewDataSource");
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
     *@param serverAreaCode ServerAreaCode
     *@param riskCode RiskCode
     *@param motorcadeType MotorcadeType
     *@return ��
     */
    public void cancel(DbPool dbpool, String serverAreaCode, String riskCode, String motorcadeType) throws Exception
    {
      DBPrpDServiceFeePlate dbPrpDServiceFeePlate = new DBPrpDServiceFeePlate();
      dbPrpDServiceFeePlate.delete(dbpool, serverAreaCode,riskCode,motorcadeType);
    }
      
    /**
     * ����dbpool��ɾ������
     *@param serverAreaCode ServerAreaCode
     *@param riskCode RiskCode
     *@param motorcadeType MotorcadeType
     *@return ��
     */
    public void cancel(String serverAreaCode, String riskCode, String motorcadeType) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      try
      {
        dbpool.open("platformNewDataSource");
        dbpool.beginTransaction();
        cancel(dbpool, serverAreaCode, riskCode, motorcadeType);
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
     *@param serverAreaCode ServerAreaCode
     *@return ��
     */
    public void cancel(DbPool dbpool, String serverAreaCode) throws Exception
    {
     
      DBPrpDServiceFeePlate dbPrpDServiceFeePlate = new DBPrpDServiceFeePlate();
      dbPrpDServiceFeePlate.delete(dbpool, serverAreaCode);
    }
      
    /**
     * ����dbpool��ɾ������
     *@param serverAreaCode ServerAreaCode
     *@return ��
     */
    public void cancel(String serverAreaCode) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      try
      {
        dbpool.open("platformNewDataSource");
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
     *@param serverAreaCode ServerAreaCode
     *@param riskCode RiskCode
     *@param motorcadeType MotorcadeType
     *@return ��
     */
    public void getData(String serverAreaCode, String riskCode, String motorcadeType) throws Exception
    {
      DbPool dbpool = new DbPool();
      dbpool.open("platformNewDataSource");
      getData(dbpool, serverAreaCode, riskCode, motorcadeType);
      dbpool.close();
    }
      
    /**
     * ��dbpool����������ȡ����
     *@param dbpool ���ӳ�
     *@param serverAreaCode ServerAreaCode
     *@param riskCode RiskCode
     *@param motorcadeType MotorcadeType
     *@return ��
     */
    public void getData(DbPool dbpool, String serverAreaCode, String riskCode, String motorcadeType) throws Exception
    {
       String strWherePart = "";
       ArrayList bindValues = new ArrayList();
       strWherePart = "serverAreaCode = ? AND riskCode = ? AND motorcadeType = ?";
       bindValues.add(serverAreaCode);
       bindValues.add(riskCode);
       bindValues.add(motorcadeType);
       query(dbpool, strWherePart, 0, bindValues);
    }
    
    /**
     * ����dbpool����������ȡ����
     *@param serverAreaCode ServerAreaCode
     *@return ��
     */
    public void getData(String serverAreaCode) throws Exception
    {
      DbPool dbpool = new DbPool();
      dbpool.open("platformNewDataSource");
      getData(dbpool, serverAreaCode);
      dbpool.close();
    }
      
    /**
     * ��dbpool����������ȡ����
     *@param dbpool ���ӳ�
     *@param serverAreaCode ServerAreaCode
     *@return ��
     */
    public void getData(DbPool dbpool, String serverAreaCode) throws Exception
    {
       String strWherePart = "";
       ArrayList bindValues = new ArrayList();
       strWherePart = "serverAreaCode = ?";
       bindValues.add(serverAreaCode);
       query(dbpool, strWherePart, 0, bindValues);
    }
    
    
    /**
     * ��dbpool��update����
     * @param dbpool ����Դ
     * @return ��
     */
	public void update(DbPool dbpool) throws Exception{
		DBPrpDServiceFeePlate dbPrpDServiceFeePlate = new DBPrpDServiceFeePlate();
		int i = 0;
		for(i = 0; i< schemas.size(); i++){
			dbPrpDServiceFeePlate.setSchema((PrpDServiceFeePlateSchema)schemas.get(i));
			dbPrpDServiceFeePlate.update(dbpool);
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
		  dbpool.open("platformNewDataSource");
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
