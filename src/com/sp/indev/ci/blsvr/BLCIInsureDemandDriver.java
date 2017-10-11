package com.sp.indiv.ci.blsvr;

import java.util.ArrayList;
import java.util.Vector;

import com.sp.indiv.ci.dbsvr.DBCIInsureDemandDriver;
import com.sp.indiv.ci.schema.CIInsureDemandDriverSchema;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;

/**
 * ����XX��ѯ����-CIInsureDemandDriver��BL��
 * 
 * @createdate 2014-01-24
 * @author  fanjiangtao 2014-01-24
 * 
 */
public class BLCIInsureDemandDriver{
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */       
    public BLCIInsureDemandDriver(){
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
     *����һ��CIInsureDemandDriverSchema��¼
     *@param CIInsureDemandDriverSchema iCIInsureDemandDriverSchema
     *@throws Exception
     */
    public void setArr(CIInsureDemandDriverSchema iCIInsureDemandDriverSchema) throws Exception
    {
       try
       {
         schemas.add(iCIInsureDemandDriverSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *�õ�һ��CIInsureDemandDriverSchema��¼
     *@param index �±�
     *@return һ��CIInsureDemandDriverSchema����
     *@throws Exception
     */
    public CIInsureDemandDriverSchema getArr(int index) throws Exception
    {
        CIInsureDemandDriverSchema cIInsurePmDriverSchema = null;
       try
       {
           cIInsurePmDriverSchema = (CIInsureDemandDriverSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return cIInsurePmDriverSchema;
     }
    /**
     *ɾ��һ��CIInsureDemandDriverSchema��¼
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
      DBCIInsureDemandDriver dbCIInsureDemandDriver = new DBCIInsureDemandDriver();
      if (iLimitCount > 0 && dbCIInsureDemandDriver.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLCIInsureDemandDriver.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM CIInsureDemandDriver WHERE " + iWherePart; 
        schemas = dbCIInsureDemandDriver.findByConditions(strSqlStatement);
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
      DBCIInsureDemandDriver dbCIInsureDemandDriver = new DBCIInsureDemandDriver();
      if (iLimitCount > 0 && dbCIInsureDemandDriver.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLCIInsureDemandDriver.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM CIInsureDemandDriver WHERE " + iWherePart; 
        schemas = dbCIInsureDemandDriver.findByConditions(dbpool,strSqlStatement);
      }
    }
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
        DBCIInsureDemandDriver dbCIInsureDemandDriver = new DBCIInsureDemandDriver();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
          dbCIInsureDemandDriver.setSchema((CIInsureDemandDriverSchema)schemas.get(i));
          dbCIInsureDemandDriver.insert(dbpool);
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
            String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
            if ("1".equals(strSwitch)) {
                dbpool.open("platformNewDataSource");            
            } else {
                dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
            }
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
     * ����dbpool����XX���Ż�ȡ����
     *@param demandno 
     *@return ��
     */
      public void getData(String iDemandNo) throws Exception
      {
          DbPool dbpool = new DbPool();
          try {
              String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
              if ("1".equals(strSwitch)) {
                  dbpool.open("platformNewDataSource");            
              } else {
                  dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
              }
              getData(dbpool, iDemandNo);
          } catch (Exception e) {
          } finally {
              dbpool.close();
          }
      }

    /**
     * ��dbpool����XX���Ż�ȡ����
     *@param dbpool ���ӳ�
     *@param iDemandNo 
     *@return ��
     */
    public void getData(DbPool dbpool,String iDemandNo) throws Exception
    {
      String strWherePart = " DemandNo= ? ";
      ArrayList arrWhereValue = new ArrayList();
      arrWhereValue.add(iDemandNo);
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
        DBCIInsureDemandDriver dbCIInsureDemandDriver = new DBCIInsureDemandDriver();
        if (iLimitCount > 0 && dbCIInsureDemandDriver.getCount(dbpool,iWherePart,iWhereValue) > iLimitCount)
        {
            throw new UserException(-98,-1003,"BLCIInsureDemandDriver.query");
        }else
        {
            initArr();
            strSqlStatement = " SELECT * FROM CIInsureDemandDriver WHERE " + iWherePart;
            schemas = dbCIInsureDemandDriver.findByConditions(dbpool,strSqlStatement,iWhereValue);
        }
    }
    /**
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
