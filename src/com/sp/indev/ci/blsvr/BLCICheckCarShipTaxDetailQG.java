package com.sp.indiv.ci.blsvr;

import java.util.ArrayList;
import java.util.Vector;

import com.sp.indiv.ci.dbsvr.DBCICheckCarShipTaxDetailQG;
import com.sp.indiv.ci.schema.CICheckCarShipTaxDetailQGSchema;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;

/**
 * ����CICheckCarShipTaxDetailQG��BL��
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>@createdate 2009-06-02</p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLCICheckCarShipTaxDetailQG{
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */       
    public BLCICheckCarShipTaxDetailQG(){
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
     *����һ��CICheckCarShipTaxDetailQGSchema��¼
     *@param iCICheckCarShipTaxDetailQGSchema CICheckCarShipTaxDetailQGSchema
     *@throws Exception
     */
    public void setArr(CICheckCarShipTaxDetailQGSchema iCICheckCarShipTaxDetailQGSchema) throws Exception
    {
       try
       {
         schemas.add(iCICheckCarShipTaxDetailQGSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *�õ�һ��CICheckCarShipTaxDetailQGSchema��¼
     *@param index �±�
     *@return һ��CICheckCarShipTaxDetailQGSchema����
     *@throws Exception
     */
    public CICheckCarShipTaxDetailQGSchema getArr(int index) throws Exception
    {
     CICheckCarShipTaxDetailQGSchema cICheckCarShipTaxDetailQGSchema = null;
       try
       {
        cICheckCarShipTaxDetailQGSchema = (CICheckCarShipTaxDetailQGSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return cICheckCarShipTaxDetailQGSchema;
     }
    /**
     *ɾ��һ��CICheckCarShipTaxDetailQGSchema��¼
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
      DBCICheckCarShipTaxDetailQG dbCICheckCarShipTaxDetailQG = new DBCICheckCarShipTaxDetailQG();
      if (iLimitCount > 0 && dbCICheckCarShipTaxDetailQG.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLCICheckCarShipTaxDetailQG.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM CICheckCarShipTaxDetailQG WHERE " + iWherePart; 
        schemas = dbCICheckCarShipTaxDetailQG.findByConditions(strSqlStatement);
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
      DBCICheckCarShipTaxDetailQG dbCICheckCarShipTaxDetailQG = new DBCICheckCarShipTaxDetailQG();
      if (iLimitCount > 0 && dbCICheckCarShipTaxDetailQG.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLCICheckCarShipTaxDetailQG.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM CICheckCarShipTaxDetailQG WHERE " + iWherePart; 
        schemas = dbCICheckCarShipTaxDetailQG.findByConditions(dbpool,strSqlStatement);
      }
    }
      
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBCICheckCarShipTaxDetailQG dbCICheckCarShipTaxDetailQG = new DBCICheckCarShipTaxDetailQG();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
      dbCICheckCarShipTaxDetailQG.setSchema((CICheckCarShipTaxDetailQGSchema)schemas.get(i));
      dbCICheckCarShipTaxDetailQG.insert(dbpool);
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
     *@param ConFirmSequenceNo XXȷ����/����ȷ����
     *@return ��
     */
    public void cancel(DbPool dbpool,String iConFirmSequenceNo) throws Exception
    {




    	String strSqlStatement = " DELETE FROM CICheckCarShipTaxDetailQG WHERE ConFirmSequenceNo= ?";
    	dbpool.prepareInnerStatement(strSqlStatement);
    	dbpool.setString(1, iConFirmSequenceNo);
    	dbpool.executePreparedUpdate();
    	dbpool.closePreparedStatement();
     
    }
      
    /**
     * ����dbpool��ɾ������
     *@param iConFirmSequenceNo XXȷ����/����ȷ����
     *@return ��
     */
    public void cancel(String iConFirmSequenceNo ) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
      try
      {
        dbpool.beginTransaction();
        cancel(dbpool,iConFirmSequenceNo);
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
     * ��dbpool����iConFirmSequenceNo��ȡ����
     *@param iConFirmSequenceNo XXȷ����/����ȷ����
     *@return ��
     */
    public void getData(String iConFirmSequenceNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
      getData(dbpool,iConFirmSequenceNo);
      dbpool.close();
    }
      
    /**
     * ����dbpool����iConFirmSequenceNo��ȡ����
     *@param dbpool ���ӳ�
     *@param iConFirmSequenceNo XXȷ����/����ȷ����
     *@return ��
     */
    public void getData(DbPool dbpool,String iConFirmSequenceNo) throws Exception
    {
        
        
        
        
        
        String strWherePart = " ConFirmSequenceNo= ? ";
        ArrayList arrWhereValue = new ArrayList();
        arrWhereValue.add(iConFirmSequenceNo);
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
        DBCICheckCarShipTaxDetailQG dbCICheckCarShipTaxDetailQG = new DBCICheckCarShipTaxDetailQG();
        if (iLimitCount > 0 && dbCICheckCarShipTaxDetailQG.getCount(dbpool,iWherePart,iWhereValue) > iLimitCount)
        {
            throw new UserException(-98,-1003,"BLCICheckCarShipTaxDetailQG.query");
        }else
        {
            initArr();
            strSqlStatement = " SELECT * FROM CICheckCarShipTaxDetailQG WHERE " + iWherePart;
            schemas = dbCICheckCarShipTaxDetailQG.findByConditions(dbpool,strSqlStatement,iWhereValue);
        }
    }
    
    /**
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
