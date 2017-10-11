package com.sp.prpall.blsvr.misc;


import java.util.*;

import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.prpall.dbsvr.misc.DBPrpDAgreementPlate;
import com.sp.prpall.schema.PrpDAgreementPlateSchema;

/**
 * ����PrpDAgreementPlate��BL��
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>@createdate 2014-09-01</p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLPrpDAgreementPlate{
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */       
    public BLPrpDAgreementPlate(){
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
     *����һ��PrpDAgreementPlateSchema��¼
     *@param iPrpDAgreementPlateSchema PrpDAgreementPlateSchema
     *@throws Exception
     */
    public void setArr(PrpDAgreementPlateSchema iPrpDAgreementPlateSchema) throws Exception
    {
       try
       {
         schemas.add(iPrpDAgreementPlateSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *�õ�һ��PrpDAgreementPlateSchema��¼
     *@param index �±�
     *@return һ��PrpDAgreementPlateSchema����
     *@throws Exception
     */
    public PrpDAgreementPlateSchema getArr(int index) throws Exception
    {
     PrpDAgreementPlateSchema prpDAgreementPlateSchema = null;
       try
       {
        prpDAgreementPlateSchema = (PrpDAgreementPlateSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpDAgreementPlateSchema;
     }
    /**
     *ɾ��һ��PrpDAgreementPlateSchema��¼
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
      DBPrpDAgreementPlate dbPrpDAgreementPlate = new DBPrpDAgreementPlate();
      if (iLimitCount > 0 && dbPrpDAgreementPlate.getCount(iWherePart,bindValues) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpDAgreementPlate.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpDAgreementPlate WHERE " + iWherePart; 
        schemas = dbPrpDAgreementPlate.findByConditions(strSqlStatement, bindValues);
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
      DBPrpDAgreementPlate dbPrpDAgreementPlate = new DBPrpDAgreementPlate();
      if (iLimitCount > 0 && dbPrpDAgreementPlate.getCount(iWherePart,bindValues) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpDAgreementPlate.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpDAgreementPlate WHERE " + iWherePart; 
        schemas = dbPrpDAgreementPlate.findByConditions(dbpool, strSqlStatement, bindValues);
      }
    }
      
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpDAgreementPlate dbPrpDAgreementPlate = new DBPrpDAgreementPlate();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
      dbPrpDAgreementPlate.setSchema((PrpDAgreementPlateSchema)schemas.get(i));
      dbPrpDAgreementPlate.insert(dbpool);
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
     *@param agreementNo AgreementNo
     *@param riskCode RiskCode
     *@param motorcadeType MotorcadeType
     *@return ��
     */
    public void cancel(DbPool dbpool, String agreementNo, String riskCode, String motorcadeType) throws Exception
    {
      DBPrpDAgreementPlate dbPrpDAgreementPlate = new DBPrpDAgreementPlate();
      dbPrpDAgreementPlate.delete(dbpool, agreementNo, riskCode, motorcadeType);
    }
      
    /**
     * ����dbpool��ɾ������
     *@param agreementNo AgreementNo
     *@param riskCode RiskCode
     *@param motorcadeType MotorcadeType
     *@return ��
     */
    public void cancel(String agreementNo, String riskCode, String motorcadeType) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      try
      {
        dbpool.open("platformNewDataSource");
        dbpool.beginTransaction();
        cancel(dbpool, agreementNo, riskCode, motorcadeType);
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
     *@param agreementNo AgreementNo
     *@return ��
     */
    public void cancel(DbPool dbpool, String agreementNo) throws Exception
    {
      DBPrpDAgreementPlate dbPrpDAgreementPlate = new DBPrpDAgreementPlate();
      dbPrpDAgreementPlate.delete(dbpool, agreementNo);
    }
      
    /**
     * ����dbpool��ɾ������
     *@param agreementNo AgreementNo
     *@return ��
     */
    public void cancel(String agreementNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      try
      {
        dbpool.open("platformNewDataSource");
        dbpool.beginTransaction();
        cancel(dbpool, agreementNo);
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
     *@param agreementNo AgreementNo
     *@param riskCode RiskCode
     *@param motorcadeType MotorcadeType
     *@return ��
     */
    public void getData(String agreementNo, String riskCode, String motorcadeType) throws Exception
    {
      DbPool dbpool = new DbPool();
      dbpool.open("platformNewDataSource");
      getData(dbpool, agreementNo, riskCode, motorcadeType);
      dbpool.close();
    }
      
    /**
     * ��dbpool����������ȡ����
     *@param dbpool ���ӳ�
     *@param agreementNo AgreementNo
     *@param riskCode RiskCode
     *@param motorcadeType MotorcadeType
     *@return ��
     */
    public void getData(DbPool dbpool, String agreementNo, String riskCode, String motorcadeType) throws Exception
    {
       String strWherePart = "";
       ArrayList bindValues = new ArrayList();
       strWherePart = "agreementNo = ? AND riskCode = ? AND motorcadeType = ?";
       bindValues.add(agreementNo);
       bindValues.add(riskCode);
       bindValues.add(motorcadeType);
       query(dbpool, strWherePart, 0, bindValues);
    }
    
    /**
     * ����dbpool����������ȡ����
     *@param agreementNo AgreementNo
     *@param riskCode RiskCode
     *@param motorcadeType MotorcadeType
     *@return ��
     */
    public void getData(String agreementNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      dbpool.open("platformNewDataSource");
      getData(dbpool, agreementNo);
      dbpool.close();
    }
      
    /**
     * ��dbpool����������ȡ����
     *@param dbpool ���ӳ�
     *@param agreementNo AgreementNo
     *@param riskCode RiskCode
     *@param motorcadeType MotorcadeType
     *@return ��
     */
    public void getData(DbPool dbpool, String agreementNo) throws Exception
    {
       String strWherePart = "";
       ArrayList bindValues = new ArrayList();
       strWherePart = "agreementNo = ?";
       bindValues.add(agreementNo);
       query(dbpool, strWherePart, 0, bindValues);
    }
    
    /**
     * ��dbpool��update����
     * @param dbpool ����Դ
     * @return ��
     */
	public void update(DbPool dbpool) throws Exception{
		DBPrpDAgreementPlate dbPrpDAgreementPlate = new DBPrpDAgreementPlate();
		int i = 0;
		for(i = 0; i< schemas.size(); i++){
			dbPrpDAgreementPlate.setSchema((PrpDAgreementPlateSchema)schemas.get(i));
			dbPrpDAgreementPlate.update(dbpool);
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
