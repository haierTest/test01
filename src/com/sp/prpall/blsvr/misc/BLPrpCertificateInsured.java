package com.sp.prpall.blsvr.misc;

import java.util.ArrayList;
import java.util.Vector;

import com.sp.prpall.dbsvr.misc.DBPrpCertificateInsured;
import com.sp.prpall.schema.PrpCertificateInsuredSchema;
import com.sp.utility.SysConfig; 
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;

/**
 * ���廪�Ĵ浥��XX����Ϣ���BL�� 
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>@createdate 2015-06-26</p> 
 * @author BLGenerator
 * @version 1.0
 */
public class BLPrpCertificateInsured{ 
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */       
    public BLPrpCertificateInsured(){
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
     *����һ��PrpCertificateInsuredSchema��¼
     *@param iPrpCertificateInsuredSchema PrpCertificateInsuredSchema
     *@throws Exception
     */
    public void setArr(PrpCertificateInsuredSchema iPrpCertificateInsuredSchema) throws Exception
    {
       try
       {
         schemas.add(iPrpCertificateInsuredSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *�õ�һ��PrpCertificateInsuredSchema��¼
     *@param index �±�
     *@return һ��PrpCertificateInsuredSchema����
     *@throws Exception
     */
    public PrpCertificateInsuredSchema getArr(int index) throws Exception
    {
     PrpCertificateInsuredSchema prpCertificateInsuredSchema = null;
       try
       {
        prpCertificateInsuredSchema = (PrpCertificateInsuredSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpCertificateInsuredSchema;
     }
    /**
     *ɾ��һ��PrpCertificateInsuredSchema��¼
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
       this.query(iWherePart, Integer.parseInt(SysConst.getProperty("QUERY_LIMIT_COUNT").trim()));
    }
    /**
     *���ղ�ѯ�����ͼ�¼�����Ƶõ�һ���¼�������������¼����schemas����
     *@param iWherePart ��ѯ����(���������־�)
     *@param iLimitCount ��¼������(iLimitCount=0: ������)
     *@throws UserException
     *@throws Exception
     */
    public void query(String iWherePart, int iLimitCount) throws UserException,Exception
    {
      String strSqlStatement = "";
      DBPrpCertificateInsured dbPrpCertificateInsured = new DBPrpCertificateInsured();
      if (iLimitCount > 0 && dbPrpCertificateInsured.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpCertificateInsured.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpCertificateInsured WHERE " + iWherePart; 
        schemas = dbPrpCertificateInsured.findByConditions(strSqlStatement);
      }
    }
    /**
     *���ղ�ѯ�����õ�һ���¼�������������¼����schemas����
     *@param dbpool ȫ�ֳ�
     *@param iWherePart ��ѯ����(���������־�)
     *@throws UserException
     *@throws Exception
     */
    public void query(DbPool dbpool, String iWherePart) throws UserException,Exception
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
    public void query(DbPool dbpool, String iWherePart, int iLimitCount) throws UserException,Exception
    {
      String strSqlStatement = "";
      DBPrpCertificateInsured dbPrpCertificateInsured = new DBPrpCertificateInsured();
      if (iLimitCount > 0 && dbPrpCertificateInsured.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpCertificateInsured.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpCertificateInsured WHERE " + iWherePart; 
        schemas = dbPrpCertificateInsured.findByConditions(dbpool, strSqlStatement);
      }
    }
      
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpCertificateInsured dbPrpCertificateInsured = new DBPrpCertificateInsured();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
      dbPrpCertificateInsured.setSchema((PrpCertificateInsuredSchema)schemas.get(i));
      dbPrpCertificateInsured.insert(dbpool);
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
    	dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
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
     *@param serialNo ���
     *@return ��
     */
    public void cancel(DbPool dbpool, String serialNo) throws Exception
    {
      String strSqlStatement = "";
      
      strSqlStatement = " DELETE FROM PrpCertificateInsured WHERE serialNo='" + serialNo + "'";
      
      dbpool.delete(strSqlStatement);
    }
      
    /**
     * ����dbpool��ɾ������
     *@param serialNo ���
     *@return ��
     */
    public void cancel(String serialNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      try
      {
    	dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        dbpool.beginTransaction();
        cancel(dbpool, serialNo);
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
     *@param serialNo ���
     *@return ��
     */
    public void getData(String serialNo) throws Exception
    {
      DbPool dbpool = new DbPool();
  	  try {
  		  dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
  		  getData(dbpool, serialNo);
	  } catch (Exception e) {
	  }finally{
		  dbpool.close();
	  }
    }
      
    /**
     * ��dbpool����������ȡ����
     *@param dbpool ���ӳ�
     *@param serialNo ���
     *@return ��
     */
    public void getData(DbPool dbpool, String serialNo) throws Exception
    {
       String strWherePart = "";
       strWherePart = "serialNo='" + serialNo + "'";
       query(dbpool, strWherePart, 0);
    }
    /**
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
