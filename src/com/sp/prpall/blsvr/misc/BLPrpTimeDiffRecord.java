package com.sp.prpall.blsvr.misc;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.util.*;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.prpall.dbsvr.misc.DBPrpTimeDiffRecord;
import com.sp.prpall.schema.PrpTimeDiffRecordSchema;

/**
 * ����PrpTimeDiffRecord��BL��
 * ��pdm��ȡ���ݿ���Ϣ,�������ݿ�����ɶ�Ӧ��BL��
 * <p>Title: SinosoftJavaTools v1.4</p>
 * <p>Description: �п���JavaԴ�����ɹ���</p>
 * <p>Company: Sinosoft</p>
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>@createdate 2013-07-18</p>
 * @FirstAuthor: Zhouxianli(JavaTools v1.0)
 * @SecondAuthor: Yangkun(JavaTools v1.1->v1.2->v1.3->v1.4)
 * @LastVersion: v1.4.2
 * @UpdateList: 1.������CIXXXXXƽ̨������ɴ���;
 *              2.���������������cancel()��getdata()����ʱ��ȡpdm���������Ϊnull������;
 *              3.��������������ಿ��import��Ϊnull������;
 *              4.����log4j��־bl�����Զ���ʼ��;
 */
public class BLPrpTimeDiffRecord{
    
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */       
    public BLPrpTimeDiffRecord(){
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
     *����һ��PrpTimeDiffRecordSchema��¼
     *@param iPrpTimeDiffRecordSchema PrpTimeDiffRecordSchema
     *@throws Exception
     */
    public void setArr(PrpTimeDiffRecordSchema iPrpTimeDiffRecordSchema) throws Exception
    {
      try
      {
        schemas.add(iPrpTimeDiffRecordSchema);
      }
      catch(Exception e)
      {
        throw e;
      }
    }
    /**
     *�õ�һ��PrpTimeDiffRecordSchema��¼
     *@param index �±�
     *@return һ��PrpTimeDiffRecordSchema����
     *@throws Exception
     */
    public PrpTimeDiffRecordSchema getArr(int index) throws Exception
    {
      PrpTimeDiffRecordSchema prpTimeDiffRecordSchema = null;
       try
       {
         prpTimeDiffRecordSchema = (PrpTimeDiffRecordSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpTimeDiffRecordSchema;
     }
    /**
     *ɾ��һ��PrpTimeDiffRecordSchema��¼
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
      DBPrpTimeDiffRecord dbPrpTimeDiffRecord = new DBPrpTimeDiffRecord();
      if (iLimitCount > 0 && dbPrpTimeDiffRecord.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpTimeDiffRecord.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpTimeDiffRecord WHERE " + iWherePart; 
        schemas = dbPrpTimeDiffRecord.findByConditions(strSqlStatement);
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
      DBPrpTimeDiffRecord dbPrpTimeDiffRecord = new DBPrpTimeDiffRecord();
      if (iLimitCount > 0 && dbPrpTimeDiffRecord.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpTimeDiffRecord.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpTimeDiffRecord WHERE " + iWherePart; 
        schemas = dbPrpTimeDiffRecord.findByConditions(dbpool,strSqlStatement);
      }
    }
      
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpTimeDiffRecord dbPrpTimeDiffRecord = new DBPrpTimeDiffRecord();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
        dbPrpTimeDiffRecord.setSchema((PrpTimeDiffRecordSchema)schemas.get(i));
        dbPrpTimeDiffRecord.insert(dbpool);
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
    	  e.printStackTrace();
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
     *@param iProposalNo XX����
     *@return ��
     */
    public void cancel(DbPool dbpool,String iProposalNo) throws Exception
    {
      String strSqlStatement = "";
      
      strSqlStatement = " DELETE FROM PrpTimeDiffRecord WHERE ProposalNo= '" + iProposalNo + "'";
      
      dbpool.delete(strSqlStatement);
    }
      
    /**
     * ����dbpool��ɾ������
     *@param iProposalNo XX����
     *@return ��
     */
    public void cancel(String iProposalNo ) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      try
      {
        dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
        dbpool.beginTransaction();
        cancel(dbpool,iProposalNo);
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
     * ��dbpool����XX���Ż�ȡ����
     *@param iProposalNo XX����
     *@return ��
     */
    public void getData(String iProposalNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
      getData(dbpool,iProposalNo);
      dbpool.close();
    }
      
    /**
     * ����dbpool����XX���Ż�ȡ����
     *@param dbpool ���ӳ�
     *@param iProposalNo XX����
     *@return ��
     */
    public void getData(DbPool dbpool,String iProposalNo) throws Exception
    {
      String strWherePart = "";
      strWherePart = "ProposalNo= '" + iProposalNo + "'";
      query(dbpool,strWherePart,0);
    }
    /**
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
