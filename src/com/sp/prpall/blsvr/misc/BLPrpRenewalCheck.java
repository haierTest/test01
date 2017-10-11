package com.sp.prpall.blsvr.misc;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.util.*;

import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.prpall.dbsvr.misc.DBPrpRenewalCheck;
import com.sp.prpall.schema.PrpRenewalCheckSchema;

/**
 * ����prpRenewalCheck��BL��
 * ��pdm��ȡ���ݿ���Ϣ,�������ݿ�����ɶ�Ӧ��BL��
 * <p>Title: SinosoftJavaTools v1.4</p>
 * <p>Description: �п���JavaԴ�����ɹ���</p>
 * <p>Company: Sinosoft</p>
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>@createdate 2013-06-12</p>
 * @FirstAuthor: Zhouxianli(JavaTools v1.0)
 * @SecondAuthor: Yangkun(JavaTools v1.1->v1.2->v1.3->v1.4)
 * @LastVersion: v1.4.2
 * @UpdateList: 1.������CIXXXXXƽ̨������ɴ���;
 *              2.���������������cancel()��getdata()����ʱ��ȡpdm���������Ϊnull������;
 *              3.��������������ಿ��import��Ϊnull������;
 *              4.����log4j��־bl�����Զ���ʼ��;
 */
public class BLPrpRenewalCheck{
    
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */       
    public BLPrpRenewalCheck(){
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
     *����һ��PrpRenewalCheckSchema��¼
     *@param iPrpRenewalCheckSchema PrpRenewalCheckSchema
     *@throws Exception
     */
    public void setArr(PrpRenewalCheckSchema iPrpRenewalCheckSchema) throws Exception
    {
      try
      {
        schemas.add(iPrpRenewalCheckSchema);
      }
      catch(Exception e)
      {
        throw e;
      }
    }
    /**
     *�õ�һ��PrpRenewalCheckSchema��¼
     *@param index �±�
     *@return һ��PrpRenewalCheckSchema����
     *@throws Exception
     */
    public PrpRenewalCheckSchema getArr(int index) throws Exception
    {
      PrpRenewalCheckSchema prpRenewalCheckSchema = null;
       try
       {
         prpRenewalCheckSchema = (PrpRenewalCheckSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpRenewalCheckSchema;
     }
    /**
     *ɾ��һ��PrpRenewalCheckSchema��¼
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
      DBPrpRenewalCheck dbPrpRenewalCheck = new DBPrpRenewalCheck();
      if (iLimitCount > 0 && dbPrpRenewalCheck.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpRenewalCheck.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpRenewalCheck WHERE " + iWherePart; 
        schemas = dbPrpRenewalCheck.findByConditions(strSqlStatement);
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
      DBPrpRenewalCheck dbPrpRenewalCheck = new DBPrpRenewalCheck();
      if (iLimitCount > 0 && dbPrpRenewalCheck.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpRenewalCheck.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpRenewalCheck WHERE " + iWherePart; 
        schemas = dbPrpRenewalCheck.findByConditions(dbpool,strSqlStatement);
      }
    }
      
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpRenewalCheck dbPrpRenewalCheck = new DBPrpRenewalCheck();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
        dbPrpRenewalCheck.setSchema((PrpRenewalCheckSchema)schemas.get(i));
        dbPrpRenewalCheck.insert(dbpool);
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
        dbpool.open(SysConfig.CONST_RENEWALCHECKDATASOURCE);
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
     *@param iSeqNo SeqNo
     *@return ��
     */
    public void cancel(DbPool dbpool,String iSeqNo) throws Exception
    {
      String strSqlStatement = "";
      
      strSqlStatement = " DELETE FROM PrpRenewalCheck WHERE SeqNo= '" + iSeqNo + "'";
      
      dbpool.delete(strSqlStatement);
    }
      
    /**
     * ����dbpool��ɾ������
     *@param iSeqNo SeqNo
     *@return ��
     */
    public void cancel(String iSeqNo ) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      try
      {
        dbpool.open(SysConfig.CONST_RENEWALCHECKDATASOURCE);
        dbpool.beginTransaction();
        cancel(dbpool,iSeqNo);
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
     * ��dbpool����SeqNo��ȡ����
     *@param iSeqNo SeqNo
     *@return ��
     */
    public void getData(String iSeqNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      dbpool.open(SysConfig.CONST_RENEWALCHECKDATASOURCE);
      getData(dbpool,iSeqNo);
      dbpool.close();
    }
      
    /**
     * ����dbpool����SeqNo��ȡ����
     *@param dbpool ���ӳ�
     *@param iSeqNo SeqNo
     *@return ��
     */
    public void getData(DbPool dbpool,String iSeqNo) throws Exception
    {
      String strWherePart = "";
      strWherePart = "SeqNo= '" + iSeqNo + "'";
      query(dbpool,strWherePart,0);
    }
    /**
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
