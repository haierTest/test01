package com.sp.prpall.blsvr.misc;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.util.*;

import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.prpall.dbsvr.misc.DBIntegralErrorLog;
import com.sp.prpall.schema.IntegralErrorLogSchema;

/**
 * ����IntegralErrorLog��BL��
 * ��pdm��ȡ���ݿ���Ϣ,�������ݿ�����ɶ�Ӧ��BL��
 * <p>Title: SinosoftJavaTools v1.4</p>
 * <p>Description: �п���JavaԴ�����ɹ���</p>
 * <p>Company: Sinosoft</p>
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>@createdate 2013-05-03</p>
 * @FirstAuthor: Zhouxianli(JavaTools v1.0)
 * @SecondAuthor: Yangkun(JavaTools v1.1->v1.2->v1.3->v1.4)
 * @LastVersion: v1.4.2
 * @UpdateList: 1.������CIXXXXXƽ̨������ɴ���;
 *              2.���������������cancel()��getdata()����ʱ��ȡpdm���������Ϊnull������;
 *              3.��������������ಿ��import��Ϊnull������;
 *              4.����log4j��־bl�����Զ���ʼ��;
 */
public class BLIntegralErrorLog{
    
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */       
    public BLIntegralErrorLog(){
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
     *����һ��IntegralErrorLogSchema��¼
     *@param iIntegralErrorLogSchema IntegralErrorLogSchema
     *@throws Exception
     */
    public void setArr(IntegralErrorLogSchema iIntegralErrorLogSchema) throws Exception
    {
      try
      {
        schemas.add(iIntegralErrorLogSchema);
      }
      catch(Exception e)
      {
        throw e;
      }
    }
    /**
     *�õ�һ��IntegralErrorLogSchema��¼
     *@param index �±�
     *@return һ��IntegralErrorLogSchema����
     *@throws Exception
     */
    public IntegralErrorLogSchema getArr(int index) throws Exception
    {
      IntegralErrorLogSchema integralErrorLogSchema = null;
       try
       {
         integralErrorLogSchema = (IntegralErrorLogSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return integralErrorLogSchema;
     }
    /**
     *ɾ��һ��IntegralErrorLogSchema��¼
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
      DBIntegralErrorLog dbIntegralErrorLog = new DBIntegralErrorLog();
      if (iLimitCount > 0 && dbIntegralErrorLog.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLIntegralErrorLog.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM IntegralErrorLog WHERE " + iWherePart; 
        schemas = dbIntegralErrorLog.findByConditions(strSqlStatement);
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
      DBIntegralErrorLog dbIntegralErrorLog = new DBIntegralErrorLog();
      if (iLimitCount > 0 && dbIntegralErrorLog.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLIntegralErrorLog.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM IntegralErrorLog WHERE " + iWherePart; 
        schemas = dbIntegralErrorLog.findByConditions(dbpool,strSqlStatement);
      }
    }
      
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBIntegralErrorLog dbIntegralErrorLog = new DBIntegralErrorLog();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
        dbIntegralErrorLog.setSchema((IntegralErrorLogSchema)schemas.get(i));
        dbIntegralErrorLog.insert(dbpool);
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
        dbpool.open(SysConfig.CONST_INTEGRALDATASOURCE);
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
      
      strSqlStatement = " DELETE FROM IntegralErrorLog WHERE SeqNo= '" + iSeqNo + "'";
      
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
        dbpool.open(SysConfig.CONST_INTEGRALDATASOURCE);
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
      dbpool.open(SysConfig.CONST_INTEGRALDATASOURCE);
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
