 package com.sp.prpall.blsvr.jf;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.util.*;

import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.prpall.dbsvr.jf.DBPrpJInvoicePre;
import com.sp.prpall.schema.PrpJInvoicePreSchema;

/**
 * ����Ԥ�跢Ʊ��Ϣ���BL��
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>@createdate 2013-10-24</p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLPrpJInvoicePre{
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */       
    public BLPrpJInvoicePre(){
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
     *����һ��PrpJInvoicePreSchema��¼
     *@param iPrpJInvoicePreSchema PrpJInvoicePreSchema
     *@throws Exception
     */
    public void setArr(PrpJInvoicePreSchema iPrpJInvoicePreSchema) throws Exception
    {
       try
       {
         schemas.add(iPrpJInvoicePreSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *�õ�һ��PrpJInvoicePreSchema��¼
     *@param index �±�
     *@return һ��PrpJInvoicePreSchema����
     *@throws Exception
     */
    public PrpJInvoicePreSchema getArr(int index) throws Exception
    {
     PrpJInvoicePreSchema prpJInvoicePreSchema = null;
       try
       {
        prpJInvoicePreSchema = (PrpJInvoicePreSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpJInvoicePreSchema;
     }
    /**
     *ɾ��һ��PrpJInvoicePreSchema��¼
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
      DBPrpJInvoicePre dbPrpJInvoicePre = new DBPrpJInvoicePre();
      if (iLimitCount > 0 && dbPrpJInvoicePre.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJInvoicePre.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpJInvoicePre WHERE " + iWherePart; 
        schemas = dbPrpJInvoicePre.findByConditions(strSqlStatement, bindValues);
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
      DBPrpJInvoicePre dbPrpJInvoicePre = new DBPrpJInvoicePre();
      if (iLimitCount > 0 && dbPrpJInvoicePre.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJInvoicePre.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpJInvoicePre WHERE " + iWherePart; 
        schemas = dbPrpJInvoicePre.findByConditions(dbpool, strSqlStatement, bindValues);
      }
    }
      
    
    public void queryByLimit(String iWherePart,int iLimitCount,ArrayList bindValues) throws UserException,Exception
    {
        String strSqlStatement = "";
        String strOthWherePart =" ORDER BY CertiNo,PolicyNo,PayNo,SerialNo DESC";
        DBPrpJInvoicePre dbPrpJInvoicePre = new DBPrpJInvoicePre();

        initArr();
        strSqlStatement = " SELECT * FROM PrpJInvoicePre WHERE " + iWherePart+" and ROWNUM<=1000"+strOthWherePart;
        schemas = dbPrpJInvoicePre.findByConditions(strSqlStatement,bindValues);

    }
    
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpJInvoicePre dbPrpJInvoicePre = new DBPrpJInvoicePre();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
      dbPrpJInvoicePre.setSchema((PrpJInvoicePreSchema)schemas.get(i));
      dbPrpJInvoicePre.insert(dbpool);
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
      
      dbpool.open(SysConst.getProperty("paymentDataSource"));
      
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
     * ��dbpool��ɾ������
     *@param dbpool    ���ӳ�
     *@param certiType ҵ������
     *@param certiNo ҵ���
     *@param serialNo ���
     *@param payRefReason �ո�ԭ��
     *@return ��
     */
    public void cancel(DbPool dbpool, String certiType, String certiNo, String serialNo, String payRefReason) throws Exception
    {
      String strSqlStatement = "";
      
      strSqlStatement = " DELETE FROM PrpJInvoicePre WHERE certiType='" + certiType + "', AND certiNo='" + certiNo + "', AND serialNo='" + serialNo + "', AND payRefReason='" + payRefReason + "'";
      
      dbpool.delete(strSqlStatement);
    }
      
    /**
     * ����dbpool��ɾ������
     *@param certiType ҵ������
     *@param certiNo ҵ���
     *@param serialNo ���
     *@param payRefReason �ո�ԭ��
     *@return ��
     */
    public void cancel(String certiType, String certiNo, String serialNo, String payRefReason) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      dbpool.open(SysConst.getProperty("paymentDataSource"));
      try
      {
        dbpool.beginTransaction();
        cancel(dbpool, certiType, certiNo, serialNo, payRefReason);
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
     *@param certiType ҵ������
     *@param certiNo ҵ���
     *@param serialNo ���
     *@param payRefReason �ո�ԭ��
     *@return ��
     */
    public void getData(String certiType, String certiNo, String serialNo, String payRefReason) throws Exception
    {
      DbPool dbpool = new DbPool();
      dbpool.open(SysConst.getProperty("paymentDataSource"));
      getData(dbpool, certiType, certiNo, serialNo, payRefReason);
      dbpool.close();
    }
      
    /**
     * ��dbpool����������ȡ����
     *@param dbpool ���ӳ�
     *@param certiType ҵ������
     *@param certiNo ҵ���
     *@param serialNo ���
     *@param payRefReason �ո�ԭ��
     *@return ��
     */
    public void getData(DbPool dbpool, String certiType, String certiNo, String serialNo, String payRefReason) throws Exception
    {
       String strWherePart = "";
       strWherePart = "certiType='" + certiType + "', AND certiNo='" + certiNo + "', AND serialNo='" + serialNo + "', AND payRefReason='" + payRefReason + "'";
       query(dbpool, strWherePart, 0, null);
    }
    /**
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
