package com.sp.prpall.blsvr.misc;

import java.util.ArrayList;
import java.util.Vector;

import com.sp.prpall.dbsvr.misc.DBPrpVisaSet;
import com.sp.prpall.schema.PrpVisaSetSchema;
import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;

/**
 * ���嵥֤��ӡ���ñ��BL��
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>@createdate 2003-11-03</p>
 * @Author     : X
 * @version 1.0
 */
public class BLPrpVisaSet{
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */
    public BLPrpVisaSet(){
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
     *����һ��PrpVisaSetSchema��¼
     *@param iPrpVisaSetSchema PrpVisaSetSchema
     *@throws Exception
     */
    public void setArr(PrpVisaSetSchema iPrpVisaSetSchema) throws Exception
    {
       try
       {
         schemas.add(iPrpVisaSetSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *�õ�һ��PrpVisaSetSchema��¼
     *@param index �±�
     *@return һ��PrpVisaSetSchema����
     *@throws Exception
     */
    public PrpVisaSetSchema getArr(int index) throws Exception
    {
     PrpVisaSetSchema prpVisaSetSchema = null;
       try
       {
        prpVisaSetSchema = (PrpVisaSetSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpVisaSetSchema;
     }
    /**
     *ɾ��һ��PrpVisaSetSchema��¼
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
       this.query(iWherePart,Integer.parseInt(SysConfig.getProperty("QUERY_LIMIT_COUNT").trim()));
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
      DBPrpVisaSet dbPrpVisaSet = new DBPrpVisaSet();
      if (iLimitCount > 0 && dbPrpVisaSet.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpVisaSet.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpVisaSet WHERE " + iWherePart;
        schemas = dbPrpVisaSet.findByConditions(strSqlStatement);
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
       this.query(dbpool,iWherePart,Integer.parseInt(SysConfig.getProperty("QUERY_LIMIT_COUNT").trim()));
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
      DBPrpVisaSet dbPrpVisaSet = new DBPrpVisaSet();
      if (iLimitCount > 0 && dbPrpVisaSet.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpVisaSet.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpVisaSet WHERE " + iWherePart;
        schemas = dbPrpVisaSet.findByConditions(dbpool,strSqlStatement);
      }
    }

    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpVisaSet dbPrpVisaSet = new DBPrpVisaSet();

      int i = 0;

      for(i = 0; i< schemas.size(); i++)
      {
      dbPrpVisaSet.setSchema((PrpVisaSetSchema)schemas.get(i));
      dbPrpVisaSet.insert(dbpool);
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

        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        dbpool.beginTransaction();
        save(dbpool);
        dbpool.commitTransaction();
      }
      catch (Exception e)
      {
        dbpool.rollbackTransaction();
      }
      finally {
        dbpool.close();
      }
    }

    /**
     *��dbpool��ɾ������
     *@param dbpool    ���ӳ�
     *@param RiskCode XXXXX��
     *@return ��
     */
    public void cancel(DbPool dbpool,String RiskCode) throws Exception
    {




    	String strSqlStatement = " DELETE FROM PrpVisaSet WHERE RiskCode= ?";
    	dbpool.prepareInnerStatement(strSqlStatement);
    	dbpool.setString(1, RiskCode);
	dbpool.executePreparedUpdate();
	dbpool.closePreparedStatement();
     
    }

    /**
     * ����dbpool��ɾ������
     *@param RiskCode XXXXX��
     *@return ��
     */
    public void cancel(String RiskCode ) throws Exception
    {
      DbPool dbpool = new DbPool();

        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        dbpool.beginTransaction();
        cancel(dbpool,RiskCode);
        dbpool.commitTransaction();
      }
      catch (Exception e)
      {
        dbpool.rollbackTransaction();
      }
      finally {
        dbpool.close();
      }
    }

    /**
     * ��dbpool����RiskCode��ȡ����
     *@param dbpool ���ӳ�
     *@param RiskCode XXXXX��
     */
    public void getData(String RiskCode) throws Exception
    {
      DbPool dbpool = new DbPool();

      try {
		dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
		getData(dbpool, RiskCode);
	} catch (Exception e) {
		
	}finally {      
	dbpool.close();
    }

    }

    /**
     * ����dbpool����RiskCode��ȡ����
     *@param dbpool ���ӳ�
     *@param RiskCode XXXXX��
     *@return ��
     */
    public void getData(DbPool dbpool,String RiskCode) throws Exception
    {
        
        
        
        
        String strWherePart = " RiskCode= ? ";
        ArrayList arrWhereValue = new ArrayList();
        arrWhereValue.add(RiskCode);
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
        DBPrpVisaSet dbPrpVisaSet = new DBPrpVisaSet();
        if (iLimitCount > 0 && dbPrpVisaSet.getCount(dbpool,iWherePart,iWhereValue) > iLimitCount)
        {
            throw new UserException(-98,-1003,"BLPrpVisaSet.query");
        }else
        {
            initArr();
            strSqlStatement = " SELECT * FROM PrpVisaSet WHERE " + iWherePart;
            schemas = dbPrpVisaSet.findByConditions(dbpool,strSqlStatement,iWhereValue);
        }
    }
    
    /**
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
