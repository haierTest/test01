package com.sp.indiv.ci.blsvr;

import java.util.ArrayList;
import java.util.Vector;

import com.sp.indiv.ci.dbsvr.DBCIAssemblePayNoQG;
import com.sp.indiv.ci.schema.CIAssemblePayNoQGSchema;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;

/**
 * ����CIAssemblePayNoQG��BL��
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>@createdate 2009-06-02</p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLCIAssemblePayNoQG{
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */       
    public BLCIAssemblePayNoQG(){
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
     *����һ��CIAssemblePayNoQGSchema��¼
     *@param iCIAssemblePayNoQGSchema CIAssemblePayNoQGSchema
     *@throws Exception
     */
    public void setArr(CIAssemblePayNoQGSchema iCIAssemblePayNoQGSchema) throws Exception
    {
       try
       {
         schemas.add(iCIAssemblePayNoQGSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *�õ�һ��CIAssemblePayNoQGSchema��¼
     *@param index �±�
     *@return һ��CIAssemblePayNoQGSchema����
     *@throws Exception
     */
    public CIAssemblePayNoQGSchema getArr(int index) throws Exception
    {
     CIAssemblePayNoQGSchema cIAssemblePayNoQGSchema = null;
       try
       {
        cIAssemblePayNoQGSchema = (CIAssemblePayNoQGSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return cIAssemblePayNoQGSchema;
     }
    /**
     *ɾ��һ��CIAssemblePayNoQGSchema��¼
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
      DBCIAssemblePayNoQG dbCIAssemblePayNoQG = new DBCIAssemblePayNoQG();
      if (iLimitCount > 0 && dbCIAssemblePayNoQG.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLCIAssemblePayNoQG.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM CIAssemblePayNoQG WHERE " + iWherePart; 
        schemas = dbCIAssemblePayNoQG.findByConditions(strSqlStatement);
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
      DBCIAssemblePayNoQG dbCIAssemblePayNoQG = new DBCIAssemblePayNoQG();
      if (iLimitCount > 0 && dbCIAssemblePayNoQG.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLCIAssemblePayNoQG.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM CIAssemblePayNoQG WHERE " + iWherePart; 
        schemas = dbCIAssemblePayNoQG.findByConditions(dbpool,strSqlStatement);
      }
    }
      
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBCIAssemblePayNoQG dbCIAssemblePayNoQG = new DBCIAssemblePayNoQG();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
      dbCIAssemblePayNoQG.setSchema((CIAssemblePayNoQGSchema)schemas.get(i));
      dbCIAssemblePayNoQG.insert(dbpool);
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
     *@param iTaxReceiptNo ����Ʊ�ݺ�
     *@return ��
     */
    public void cancel(DbPool dbpool,String iTaxReceiptNo) throws Exception
    {




    	String strSqlStatement = " DELETE FROM CIAssemblePayNoQG WHERE TaxReceiptNo= ?";
    	dbpool.prepareInnerStatement(strSqlStatement);
    	dbpool.setString(1, iTaxReceiptNo);
    	dbpool.executePreparedUpdate();
    	dbpool.closePreparedStatement();
     
    }
      
    /**
     * ����dbpool��ɾ������
     *@param iTaxReceiptNo ����Ʊ�ݺ�
     *@return ��
     */
    public void cancel(String iTaxReceiptNo ) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
      try
      {
        dbpool.beginTransaction();
        cancel(dbpool,iTaxReceiptNo);
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
     * ��dbpool����iTaxReceiptNo��ȡ����
     *@param iTaxReceiptNo ����Ʊ�ݺ�
     *@return ��
     */
    public void getData(String iTaxReceiptNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
      getData(dbpool,iTaxReceiptNo);
      dbpool.close();
    }
      
    /**
     * ����dbpool����iTaxReceiptNo��ȡ����
     *@param dbpool ���ӳ�
     *@param iTaxReceiptNo ����Ʊ�ݺ�
     *@return ��
     */
    public void getData(DbPool dbpool,String iTaxReceiptNo) throws Exception
    {
        
        
        
        
        String strWherePart = " TaxReceiptNo= ? ";
        ArrayList arrWhereValue = new ArrayList();
        arrWhereValue.add(iTaxReceiptNo);
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
        DBCIAssemblePayNoQG dbCIAssemblePayNoQG = new DBCIAssemblePayNoQG();
        if (iLimitCount > 0 && dbCIAssemblePayNoQG.getCount(dbpool,iWherePart,iWhereValue) > iLimitCount)
        {
            throw new UserException(-98,-1003,"BLCIAssemblePayNoQG.query");
        }else
        {
            initArr();
            strSqlStatement = " SELECT * FROM CIAssemblePayNoQG WHERE " + iWherePart;
            schemas = dbCIAssemblePayNoQG.findByConditions(dbpool,strSqlStatement,iWhereValue);
        }
    }
    
    /**
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
