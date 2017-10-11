package com.sp.prpall.blsvr.misc;

import java.util.ArrayList;
import java.util.Vector;

import com.sp.prpall.dbsvr.misc.DBPrpBillCoreCard;
import com.sp.prpall.schema.PrpBillCoreCardSchema;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;

/**
 * ������Ŀ�Ǯ��Ϣ���BL��
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>@createdate 2009-04-28</p>
 * @author zhengxiaoluo
 * @version 1.0
 */
public class BLPrpBillCoreCard{
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */       
    public BLPrpBillCoreCard(){
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
     *����һ��PrpBillCoreCardSchema��¼
     *@param iPrpBillCoreCardSchema PrpBillCoreCardSchema
     *@throws Exception
     */
    public void setArr(PrpBillCoreCardSchema iPrpBillCoreCardSchema) throws Exception
    {
       try
       {
         schemas.add(iPrpBillCoreCardSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *�õ�һ��PrpBillCoreCardSchema��¼
     *@param index �±�
     *@return һ��PrpBillCoreCardSchema����
     *@throws Exception
     */
    public PrpBillCoreCardSchema getArr(int index) throws Exception
    {
     PrpBillCoreCardSchema prpBillCoreCardSchema = null;
       try
       {
        prpBillCoreCardSchema = (PrpBillCoreCardSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpBillCoreCardSchema;
     }
    /**
     *ɾ��һ��PrpBillCoreCardSchema��¼
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
      DBPrpBillCoreCard dbPrpBillCoreCard = new DBPrpBillCoreCard();
      if (iLimitCount > 0 && dbPrpBillCoreCard.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpBillCoreCard.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpBillCoreCard WHERE " + iWherePart; 
        schemas = dbPrpBillCoreCard.findByConditions(strSqlStatement);
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
      DBPrpBillCoreCard dbPrpBillCoreCard = new DBPrpBillCoreCard();
      if (iLimitCount > 0 && dbPrpBillCoreCard.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpBillCoreCard.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpBillCoreCard WHERE " + iWherePart; 
        schemas = dbPrpBillCoreCard.findByConditions(dbpool,strSqlStatement);
      }
    }
    /*
     * @param dbpool���ӳ�
     * @param iBusinessNoҵ��ţ�����XX����ʱΪ��XX�ţ�������������ʱΪ2700СXX������
     * @param iEditiTypeҵ�����ͣ�PXX��E����
     */
    public void echoPrpBillCoreCard(DbPool dbpool, String iBusinessNo, String iEditiType) throws Exception{
    	
    }
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpBillCoreCard dbPrpBillCoreCard = new DBPrpBillCoreCard();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
      dbPrpBillCoreCard.setSchema((PrpBillCoreCardSchema)schemas.get(i));
      dbPrpBillCoreCard.insert(dbpool);
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
     *��dbpool��ɾ������
     *@param dbpool    ���ӳ�
     *@param iBusinessNo ҵ���
     *@return ��
     */
    public void cancel(DbPool dbpool,String iBusinessNo) throws Exception
    {




    	String strSqlStatement = " DELETE FROM PrpBillCoreCard WHERE BusinessNo= ?";
    	dbpool.prepareInnerStatement(strSqlStatement);
    	dbpool.setString(1, iBusinessNo);
	dbpool.executePreparedUpdate();
	dbpool.closePreparedStatement();
     
    }
      
    /**
     * ����dbpool��ɾ������
     *@param iBusinessNo ҵ���
     *@return ��
     */
    public void cancel(String iBusinessNo ) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
      try
      {
        dbpool.beginTransaction();
        cancel(dbpool,iBusinessNo);
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
     * ��dbpool����null��ȡ����
     *@param iBusinessNo ҵ���
     *@return ��
     */
    public void getData(String iBusinessNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
      getData(dbpool,iBusinessNo);
      dbpool.close();
    }
      
    /**
     * ����dbpool����null��ȡ����
     *@param dbpool ���ӳ�
     *@param iBusinessNo ҵ���
     *@return ��
     */
    public void getData(DbPool dbpool,String iBusinessNo) throws Exception
    {
        
        
        
        
        String strWherePart = " BusinessNo= ? ";
        ArrayList arrWhereValue = new ArrayList();
        arrWhereValue.add(iBusinessNo);
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
        DBPrpBillCoreCard dbPrpBillCoreCard = new DBPrpBillCoreCard();
        if (iLimitCount > 0 && dbPrpBillCoreCard.getCount(dbpool,iWherePart,iWhereValue) > iLimitCount)
        {
            throw new UserException(-98,-1003,"BLPrpBillCoreCard.query");
        }else
        {
            initArr();
            strSqlStatement = " SELECT * FROM PrpBillCoreCard WHERE " + iWherePart;
            schemas = dbPrpBillCoreCard.findByConditions(dbpool,strSqlStatement,iWhereValue);
        }
    }
    /**
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
