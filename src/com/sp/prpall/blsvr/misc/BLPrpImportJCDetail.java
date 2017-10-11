package com.sp.prpall.blsvr.misc;

import java.util.ArrayList;
import java.util.Vector;

import com.sp.prpall.dbsvr.misc.DBPrpImportJCDetail;
import com.sp.prpall.schema.PrpImportJCDetailSchema;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;


public class BLPrpImportJCDetail{
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */       
    public BLPrpImportJCDetail(){
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
     *����һ��PrpImportJCDetailSchema��¼
     *@param iPrpImportJCDetailSchema PrpImportJCDetailSchema
     *@throws Exception
     */
    public void setArr(PrpImportJCDetailSchema iPrpImportJCDetailSchema) throws Exception
    {
       try
       {
         schemas.add(iPrpImportJCDetailSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *�õ�һ��PrpImportJCDetailSchema��¼
     *@param index �±�
     *@return һ��PrpImportJCDetailSchema����
     *@throws Exception
     */
    public PrpImportJCDetailSchema getArr(int index) throws Exception
    {
     PrpImportJCDetailSchema prpImportJCDetailSchema = null;
       try
       {
        prpImportJCDetailSchema = (PrpImportJCDetailSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpImportJCDetailSchema;
     }
    /**
     *ɾ��һ��PrpImportJCDetailSchema��¼
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
      DBPrpImportJCDetail dbPrpImportJCDetail = new DBPrpImportJCDetail();
      if (iLimitCount > 0 && dbPrpImportJCDetail.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpImportJCDetail.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpImportJCDetail WHERE " + iWherePart; 
        schemas = dbPrpImportJCDetail.findByConditions(strSqlStatement);
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
      DBPrpImportJCDetail dbPrpImportJCDetail = new DBPrpImportJCDetail();
      if (iLimitCount > 0 && dbPrpImportJCDetail.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpImportJCDetail.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpImportJCDetail WHERE " + iWherePart; 
        schemas = dbPrpImportJCDetail.findByConditions(dbpool,strSqlStatement);
      }
    }
      
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpImportJCDetail dbPrpImportJCDetail = new DBPrpImportJCDetail();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
      dbPrpImportJCDetail.setSchema((PrpImportJCDetailSchema)schemas.get(i));
      dbPrpImportJCDetail.insert(dbpool);
      }
    }
      

    /**
     * ��ɾ���ķ�ʽ����XXXXX�档��dbpool
     * @param dbpool
     * @param batchNo
     * @param serialNo
     * @throws Exception
     */
    public void saveAfterCancel(DbPool dbpool,String batchNo,String serialNo) throws Exception
    {
    	 cancel(dbpool,batchNo,serialNo);  
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
     * @param serialNo
     * @throws Exception
     */
    public void saveAfterCancel(String batchNo,String serialNo) throws Exception
    {
        DbPool dbpool = new DbPool();
        
        dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
        
        try
        {
          dbpool.beginTransaction();
          saveAfterCancel(dbpool,batchNo,serialNo);
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
     *@param serialNo ���
     *@return ��
     */
    public void cancel(DbPool dbpool,String batchNo,String serialNo) throws Exception
    {
      String strSqlStatement = "";      
      strSqlStatement = " DELETE FROM PrpImportJCDetail WHERE  BatchNo = ?" +
                           " And SerialNo = ?";
      dbpool.prepareInnerStatement(strSqlStatement);
      int index = 1;
      dbpool.setString(index++,batchNo);
      dbpool.setString(index++,serialNo);
      dbpool.executePreparedUpdate();
      dbpool.closePreparedStatement();   
    }
      
    /**
     * ����dbpool��ɾ������
     *@param batchNo ���κ�
     *@param serialNo ���
     *@return ��
     */
    public void cancel(String batchNo,String serialNo ) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
      try
      {
        dbpool.beginTransaction();
        cancel(dbpool,batchNo,serialNo);
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
     * ��dbpool����batchNo,serialNo��ȡ����
     *@param batchNo ���κ�
     *@param serialNo ���
     *@return ��
     */
    public void getData(String batchNo,String serialNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
      getData(dbpool,batchNo,serialNo);
      dbpool.close();
    }
      
    /**
     * ����dbpool����batchNo,serialNo��ȡ����
     *@param dbpool ���ӳ�
     *@param batchNo ���κ�
     *@param serialNo ���
     *@return ��
     */
    public void getData(DbPool dbpool,String batchNo,String serialNo) throws Exception
    {
        
        
        
        
        String  strWherePart = " BatchNo= ?  AND SerialNo = ? ";
        ArrayList arrWhereValue = new ArrayList();
        arrWhereValue.add(batchNo);
        arrWhereValue.add(serialNo);
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
        DBPrpImportJCDetail dbPrpImportJCDetail = new DBPrpImportJCDetail();
        if (iLimitCount > 0 && dbPrpImportJCDetail.getCount(dbpool,iWherePart,iWhereValue) > iLimitCount)
        {
            throw new UserException(-98,-1003,"BLPrpImportJCDetail.query");
        }else
        {
            initArr();
            strSqlStatement = " SELECT * FROM PrpImportJCDetail WHERE " + iWherePart;
            schemas = dbPrpImportJCDetail.findByConditions(dbpool,strSqlStatement,iWhereValue);
        }
    }
    
    /**
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
