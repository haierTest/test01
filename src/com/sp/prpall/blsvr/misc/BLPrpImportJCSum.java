package com.sp.prpall.blsvr.misc;

import java.util.ArrayList;
import java.util.Vector;

import com.sp.prpall.dbsvr.misc.DBPrpImportJCSum;
import com.sp.prpall.schema.PrpImportJCSumSchema;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;


public class BLPrpImportJCSum{
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */       
    public BLPrpImportJCSum(){
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
     *����һ��PrpImportJCSumSchema��¼
     *@param iPrpImportJCSumSchema PrpImportJCSumSchema
     *@throws Exception
     */
    public void setArr(PrpImportJCSumSchema iPrpImportJCSumSchema) throws Exception
    {
       try
       {
         schemas.add(iPrpImportJCSumSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *�õ�һ��PrpImportJCSumSchema��¼
     *@param index �±�
     *@return һ��PrpImportJCSumSchema����
     *@throws Exception
     */
    public PrpImportJCSumSchema getArr(int index) throws Exception
    {
     PrpImportJCSumSchema prpImportJCSumSchema = null;
       try
       {
        prpImportJCSumSchema = (PrpImportJCSumSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpImportJCSumSchema;
     }
    /**
     *ɾ��һ��PrpImportJCSumSchema��¼
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
      DBPrpImportJCSum dbPrpImportJCSum = new DBPrpImportJCSum();
      if (iLimitCount > 0 && dbPrpImportJCSum.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpImportJCSum.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpImportJCSum WHERE " + iWherePart; 
        schemas = dbPrpImportJCSum.findByConditions(strSqlStatement);
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
      DBPrpImportJCSum dbPrpImportJCSum = new DBPrpImportJCSum();
      if (iLimitCount > 0 && dbPrpImportJCSum.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpImportJCSum.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpImportJCSum WHERE " + iWherePart; 
        schemas = dbPrpImportJCSum.findByConditions(dbpool,strSqlStatement);
      }
    }
      
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpImportJCSum dbPrpImportJCSum = new DBPrpImportJCSum();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
      dbPrpImportJCSum.setSchema((PrpImportJCSumSchema)schemas.get(i));
      dbPrpImportJCSum.insert(dbpool);
      }
    }
      
    /**
     * ��ɾ���ķ�ʽ����XXXXX�档��dbpool
     * @param dbpool
     * @param batchNo
     * @throws Exception
     */
    public void saveAfterCancel(DbPool dbpool,String batchNo) throws Exception
    {
    	 cancel(dbpool,batchNo);  
         save(dbpool);
    }
    
    /**
     *����dbpool��XXXXX�淽��
     *@param ��
     *@return ��
     */
    public void save() throws Exception
    {
      DbPool dbpool = new DbPool();
      
      dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
      
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
     * ��ɾ���ķ�ʽ����XXXXX�档����dbpool
     * @param dbpool
     * @param batchNo
     * @throws Exception
     */
    public void saveAfterCancel(String batchNo) throws Exception
    {
        DbPool dbpool = new DbPool();
        
        dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
        
        try
        {
          dbpool.beginTransaction();
          saveAfterCancel(dbpool,batchNo);
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
     *@param batchNo ���κ�
     *@return ��
     */
    public void cancel(DbPool dbpool,String batchNo) throws Exception
    {




    	String strSqlStatement = " DELETE FROM PrpImportJCSum WHERE BatchNo= ?";
    	dbpool.prepareInnerStatement(strSqlStatement);
    	dbpool.setString(1, batchNo);
	dbpool.executePreparedUpdate();
	dbpool.closePreparedStatement();
     
    }
      
    /**
     * ����dbpool��ɾ������
     *@param batchNo ���κ�
     *@return ��
     */
    public void cancel(String batchNo ) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
      try
      {
        dbpool.beginTransaction();
        cancel(dbpool,null);
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
     * ��dbpool����batchNo��ȡ����
     *@param batchNo ���κ�
     *@return ��
     */
    public void getData(String batchNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
      getData(dbpool,batchNo);
      dbpool.close();
    }
      
    /**
     * ����dbpool����batchNo��ȡ����
     *@param dbpool ���ӳ�
     *@param batchNo ���κ�
     *@return ��
     */
    public void getData(DbPool dbpool,String batchNo) throws Exception
    {
        
        
        
        
        String strWherePart = " BatchNo= ? ";
        ArrayList arrWhereValue = new ArrayList();
        arrWhereValue.add(batchNo);
        query(dbpool, strWherePart, arrWhereValue, 0);
        
    }
    
    /**
     *���ղ�ѯ�����ͼ�¼�����Ƶõ�һ���¼�������������¼����schemas����
     *@author wangchuanzhong 20100602
     *@param dbpool      ȫ�ֳ�
     *@param iWherePart  ��ѯ����,�����������Ѱ󶨱�����ʽ���ʺŸ���������ֵ����һ��
     *@param iWhereValue ��ѯ�������ֶ�ֵ
     *@param iLimitCount ��¼������(iLimitCount=0: ������)
     *@throws UserException
     *@throws Exception
     */
    public void query(DbPool dbpool,String iWherePart,ArrayList iWhereValue, int iLimitCount) throws UserException,Exception
    {
        String strSqlStatement = "";
        DBPrpImportJCSum dbPrpImportJCSum = new DBPrpImportJCSum();
        if (iLimitCount > 0 && dbPrpImportJCSum.getCount(dbpool,iWherePart,iWhereValue) > iLimitCount)
        {
            throw new UserException(-98,-1003,"BLPrpImportJCSum.query");
        }else
        {
            initArr();
            strSqlStatement = " SELECT * FROM PrpImportJCSum WHERE " + iWherePart;
            schemas = dbPrpImportJCSum.findByConditions(dbpool,strSqlStatement,iWhereValue);
        }
    }
    
    /**
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
