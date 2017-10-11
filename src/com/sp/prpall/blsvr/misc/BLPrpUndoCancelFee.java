package com.sp.prpall.blsvr.misc;

import java.util.ArrayList;
import java.util.Vector;

import com.sp.prpall.dbsvr.misc.DBPrpUndoCancelFee;
import com.sp.prpall.schema.PrpUndoCancelFeeSchema;
import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;

/**
 * ����PrpUndoCancelFee��BL��
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>@createdate 2003-07-17</p>
 * @Author     : X
 * @version 1.0
 */
public class BLPrpUndoCancelFee{
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */       
    public BLPrpUndoCancelFee(){
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
     *����һ��PrpUndoCancelFeeSchema��¼
     *@param iPrpUndoCancelFeeSchema PrpUndoCancelFeeSchema
     *@throws Exception
     */
    public void setArr(PrpUndoCancelFeeSchema iPrpUndoCancelFeeSchema) throws Exception
    {
       try
       {
         schemas.add(iPrpUndoCancelFeeSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *�õ�һ��PrpUndoCancelFeeSchema��¼
     *@param index �±�
     *@return һ��PrpUndoCancelFeeSchema����
     *@throws Exception
     */
    public PrpUndoCancelFeeSchema getArr(int index) throws Exception
    {
     PrpUndoCancelFeeSchema prpUndoCancelFeeSchema = null;
       try
       {
        prpUndoCancelFeeSchema = (PrpUndoCancelFeeSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpUndoCancelFeeSchema;
     }
    /**
     *ɾ��һ��PrpUndoCancelFeeSchema��¼
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
      DBPrpUndoCancelFee dbPrpUndoCancelFee = new DBPrpUndoCancelFee();
      if (iLimitCount > 0 && dbPrpUndoCancelFee.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpUndoCancelFee.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpUndoCancelFee WHERE " + iWherePart; 
        schemas = dbPrpUndoCancelFee.findByConditions(strSqlStatement);
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
      DBPrpUndoCancelFee dbPrpUndoCancelFee = new DBPrpUndoCancelFee();
      if (iLimitCount > 0 && dbPrpUndoCancelFee.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpUndoCancelFee.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpUndoCancelFee WHERE " + iWherePart; 
        schemas = dbPrpUndoCancelFee.findByConditions(dbpool,strSqlStatement);
      }
    }
      
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpUndoCancelFee dbPrpUndoCancelFee = new DBPrpUndoCancelFee();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
      dbPrpUndoCancelFee.setSchema((PrpUndoCancelFeeSchema)schemas.get(i));
      dbPrpUndoCancelFee.insert(dbpool);
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
     *@param null null
     *@return ��
     */
    public void cancel(DbPool dbpool,String certino) throws Exception
    {




    	String strSqlStatement = " DELETE FROM PrpUndoCancelFee WHERE certino= ?";
    	dbpool.prepareInnerStatement(strSqlStatement);
    	dbpool.setString(1, certino);
	dbpool.executePreparedUpdate();
	dbpool.closePreparedStatement();
     
    }
      
    /**
     * ����dbpool��ɾ������
     *@param null null
     *@return ��
     */
    public void cancel(String certino ) throws Exception
    {
      DbPool dbpool = new DbPool();
      
        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        dbpool.beginTransaction();
        cancel(dbpool,null);
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
     * ��dbpool����null��ȡ����
     *@param null null
     *@return ��
     */
    public void getData(String certino) throws Exception
    {
      DbPool dbpool = new DbPool();

      try {
		dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
		getData(dbpool, null);
	} catch (Exception e) {
		
	}finally {      
	dbpool.close();
    }

    }
      
    /**
     * ����dbpool����null��ȡ����
     *@param dbpool ���ӳ�
     *@param null null
     *@return ��
     */
    public void getData(DbPool dbpool,String certino) throws Exception
    {
        
        
        
        
        String strWherePart = " certino= ? ";
        ArrayList arrWhereValue = new ArrayList();
        arrWhereValue.add(certino);
        query(dbpool, strWherePart, arrWhereValue, 0);
        
    }
    
      /**
       *���ղ�ѯ�����ͼ�¼�����Ƶõ�һ���¼�������������¼����schemas����
       *@author yangxiaodong 20100602
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
          DBPrpUndoCancelFee dbPrpUndoCancelFee = new DBPrpUndoCancelFee();
          if (iLimitCount > 0 && dbPrpUndoCancelFee.getCount(dbpool,iWherePart,iWhereValue) > iLimitCount)
          {
              throw new UserException(-98,-1003,"BLPrpUndoCancelFee.query");
          }else
          {
              initArr();
              strSqlStatement = " SELECT * FROM PrpUndoCancelFee WHERE " + iWherePart;
              schemas = dbPrpUndoCancelFee.findByConditions(dbpool,strSqlStatement,iWhereValue);
          }
      }
    /**
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
