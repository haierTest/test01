package com.sp.prpall.blsvr.misc;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.util.*;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.prpall.dbsvr.misc.DBPrpQualityCheck;
import com.sp.prpall.schema.PrpQualityCheckSchema;

/**
 * ����PrpQualityCheck��BL��
 * ��pdm��ȡ���ݿ���Ϣ,�������ݿ�����ɶ�Ӧ��BL��
 * <p>Title: SinosoftJavaTools v1.4</p>
 * <p>Description: �п���JavaԴ�����ɹ���</p>
 * <p>Company: Sinosoft</p>
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>@createdate 2012-07-04</p>
 * @FirstAuthor: Zhouxianli(JavaTools v1.0)
 * @SecondAuthor: Yangkun(JavaTools v1.1->v1.2->v1.3->v1.4)
 * @LastVersion: v1.4.2
 * @UpdateList: 1.������CIXXXXXƽ̨������ɴ���;
 *              2.���������������cancel()��getdata()����ʱ��ȡpdm���������Ϊnull������;
 *              3.��������������ಿ��import��Ϊnull������;
 *              4.����log4j��־bl�����Զ���ʼ��;
 */
public class BLPrpQualityCheck{
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */       
    public BLPrpQualityCheck(){
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
     *����һ��PrpQualityCheckSchema��¼
     *@param iPrpQualityCheckSchema PrpQualityCheckSchema
     *@throws Exception
     */
    public void setArr(PrpQualityCheckSchema iPrpQualityCheckSchema) throws Exception
    {
      try
      {
        schemas.add(iPrpQualityCheckSchema);
      }
      catch(Exception e)
      {
        throw e;
      }
    }
    /**
     *�õ�һ��PrpQualityCheckSchema��¼
     *@param index �±�
     *@return һ��PrpQualityCheckSchema����
     *@throws Exception
     */
    public PrpQualityCheckSchema getArr(int index) throws Exception
    {
      PrpQualityCheckSchema prpQualityCheckSchema = null;
       try
       {
         prpQualityCheckSchema = (PrpQualityCheckSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpQualityCheckSchema;
     }
    /**
     *ɾ��һ��PrpQualityCheckSchema��¼
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
      DBPrpQualityCheck dbPrpQualityCheck = new DBPrpQualityCheck();
      if (iLimitCount > 0 && dbPrpQualityCheck.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpQualityCheck.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpQualityCheck WHERE " + iWherePart; 
        schemas = dbPrpQualityCheck.findByConditions(strSqlStatement);
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
      DBPrpQualityCheck dbPrpQualityCheck = new DBPrpQualityCheck();
      if (iLimitCount > 0 && dbPrpQualityCheck.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpQualityCheck.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpQualityCheck WHERE " + iWherePart; 
        schemas = dbPrpQualityCheck.findByConditions(dbpool,strSqlStatement);
      }
    }
      
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpQualityCheck dbPrpQualityCheck = new DBPrpQualityCheck();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
        dbPrpQualityCheck.setSchema((PrpQualityCheckSchema)schemas.get(i));
        dbPrpQualityCheck.insert(dbpool);
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
     *@param iBussinessno ҵ���
     *@return ��
     */
    public void cancel(DbPool dbpool,String iBussinessno) throws Exception
    {
      String strSqlStatement = "";
      
      strSqlStatement = " DELETE FROM PrpQualityCheck WHERE Bussinessno= '" + iBussinessno + "'";
      
      dbpool.delete(strSqlStatement);
    }
      
    /**
     * ����dbpool��ɾ������
     *@param iBussinessno ҵ���
     *@return ��
     */
    public void cancel(String iBussinessno ) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      try
      {
        dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
        dbpool.beginTransaction();
        cancel(dbpool,iBussinessno);
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
     * ��dbpool����ҵ��Ż�ȡ����
     *@param iBussinessno ҵ���
     *@return ��
     */
    public void getData(String iBussinessno) throws Exception
    {
      DbPool dbpool = new DbPool();
      dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
      getData(dbpool,iBussinessno);
      dbpool.close();
    }
      
    /**
     * ����dbpool����ҵ��Ż�ȡ����
     *@param dbpool ���ӳ�
     *@param iBussinessno ҵ���
     *@return ��
     */
    public void getData(DbPool dbpool,String iBussinessno) throws Exception
    {
      String strWherePart = "";
      strWherePart = "Bussinessno= '" + iBussinessno + "'";
      query(dbpool,strWherePart,0);
    }
    /**
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
