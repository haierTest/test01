package com.sp.prpall.blsvr.misc;

import java.util.ArrayList;
import java.util.Vector;

import com.sp.prpall.dbsvr.misc.DBPrpCertificate;
import com.sp.prpall.schema.PrpCertificateSchema;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst; 
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException; 
 
/**
 * ���廪�Ĵ浥XX������Ϣ���BL��
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>@createdate 2015-06-26</p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLPrpCertificate{
    private Vector schemas = new Vector(); 
    /**
     * ���캯��
     */       
    public BLPrpCertificate(){
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
     *����һ��PrpCertificateSchema��¼
     *@param iPrpCertificateSchema PrpCertificateSchema
     *@throws Exception
     */
    public void setArr(PrpCertificateSchema iPrpCertificateSchema) throws Exception
    {
       try
       {
         schemas.add(iPrpCertificateSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *�õ�һ��PrpCertificateSchema��¼
     *@param index �±�
     *@return һ��PrpCertificateSchema����
     *@throws Exception
     */
    public PrpCertificateSchema getArr(int index) throws Exception
    {
     PrpCertificateSchema prpCertificateSchema = null;
       try
       {
        prpCertificateSchema = (PrpCertificateSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpCertificateSchema;
     }
    /**
     *ɾ��һ��PrpCertificateSchema��¼
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
      DBPrpCertificate dbPrpCertificate = new DBPrpCertificate();
      if (iLimitCount > 0 && dbPrpCertificate.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpCertificate.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpCertificate WHERE " + iWherePart; 
        schemas = dbPrpCertificate.findByConditions(strSqlStatement);
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
      DBPrpCertificate dbPrpCertificate = new DBPrpCertificate();
      if (iLimitCount > 0 && dbPrpCertificate.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpCertificate.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpCertificate WHERE " + iWherePart; 
        schemas = dbPrpCertificate.findByConditions(dbpool, strSqlStatement);
      }
    }
      
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpCertificate dbPrpCertificate = new DBPrpCertificate();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
      dbPrpCertificate.setSchema((PrpCertificateSchema)schemas.get(i));
      dbPrpCertificate.insert(dbpool);
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
     *@param productCode ������Ʒ����
     *@return ��
     */
    public void cancel(DbPool dbpool, String productCode) throws Exception
    {
      String strSqlStatement = "";
      strSqlStatement = " DELETE FROM PrpCertificate WHERE productCode='" + productCode + "'";
      dbpool.delete(strSqlStatement);
    }
      
    /**
     * ����dbpool��ɾ������
     *@param productCode ������Ʒ����
     *@return ��
     */
    public void cancel(String productCode) throws Exception
    {
      DbPool dbpool = new DbPool();
      try
      {
    	dbpool.open(SysConfig.CONST_DDCCDATASOURCE);  
        dbpool.beginTransaction();
        cancel(dbpool, productCode);
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
     *@param productCode ������Ʒ����
     *@return ��
     */
    public void getData(String productCode) throws Exception
    {
      DbPool dbpool = new DbPool();
      try {
    	  dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
          getData(dbpool, productCode);
	  } catch (Exception e) {
	  }finally{
		  dbpool.close();
	  }
      
      
    }
      
    /**
     * ��dbpool����������ȡ����
     *@param dbpool ���ӳ�
     *@param productCode ������Ʒ����
     *@return ��
     */
    public void getData(DbPool dbpool, String productCode) throws Exception
    {
       String strWherePart = "";
       strWherePart = "productCode='" + productCode + "'";
       query(dbpool, strWherePart, 0);
    }
    /**
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
