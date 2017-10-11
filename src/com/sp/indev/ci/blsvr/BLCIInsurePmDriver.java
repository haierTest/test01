package com.sp.indiv.ci.blsvr;

import java.util.ArrayList;
import java.util.Vector;

import com.sp.indiv.ci.dbsvr.DBCIInsurePmDriver;
import com.sp.indiv.ci.dbsvr.DBCIInsurePmVehicle;
import com.sp.indiv.ci.schema.CIInsurePmDriverSchema;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;

/**
 * ����XX��ѯ����-CIInsurePmDriver��BL��
 * 
 * @createdate 2014-01-24
 * @author  fanjiangtao 2014-01-24
 * 
 */
public class BLCIInsurePmDriver{
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */       
    public BLCIInsurePmDriver(){
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
     *����һ��CIInsurePmDriverSchema��¼
     *@param CIInsurePmDriverSchema iCIInsurePmDriverSchema
     *@throws Exception
     */
    public void setArr(CIInsurePmDriverSchema iCIInsurePmDriverSchema) throws Exception
    {
       try
       {
         schemas.add(iCIInsurePmDriverSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *�õ�һ��CIInsurePmDriverSchema��¼
     *@param index �±�
     *@return һ��CIInsurePmDriverSchema����
     *@throws Exception
     */
    public CIInsurePmDriverSchema getArr(int index) throws Exception
    {
        CIInsurePmDriverSchema cIInsurePmDriverSchema = null;
       try
       {
           cIInsurePmDriverSchema = (CIInsurePmDriverSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return cIInsurePmDriverSchema;
     }
    /**
     *ɾ��һ��CIInsurePmDriverSchema��¼
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
      DBCIInsurePmDriver dbCIInsurePmDriver = new DBCIInsurePmDriver();
      if (iLimitCount > 0 && dbCIInsurePmDriver.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLCIInsurePmDriver.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM CIInsurePmDriver WHERE " + iWherePart; 
        schemas = dbCIInsurePmDriver.findByConditions(strSqlStatement);
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
      DBCIInsurePmDriver dbCIInsurePmDriver = new DBCIInsurePmDriver();
      if (iLimitCount > 0 && dbCIInsurePmDriver.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLCIInsurePmDriver.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM CIInsurePmDriver WHERE " + iWherePart; 
        schemas = dbCIInsurePmDriver.findByConditions(dbpool,strSqlStatement);
      }
    }
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
        DBCIInsurePmDriver dbCIInsurePmDriver = new DBCIInsurePmDriver();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
          dbCIInsurePmDriver.setSchema((CIInsurePmDriverSchema)schemas.get(i));
          dbCIInsurePmDriver.insert(dbpool);
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
      {e.printStackTrace();
        dbpool.rollbackTransaction();
      }
      finally {
        dbpool.close();
      }
    }
    
    public void cancel(String LicenseNo ) throws Exception
    {
      DbPool dbpool = new DbPool();

       try {
        dbpool.open("platformNewDataSource");
        dbpool.beginTransaction();
        cancel(dbpool,LicenseNo);
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
    
    public void cancel(DbPool dbpool,String LicenseNo) throws Exception
    {
	  
  	     String strSqlStatement = " DELETE FROM CIInsurePmDriver WHERE LicenseNo= ?";
    	 dbpool.prepareInnerStatement(strSqlStatement);
    	 dbpool.setString(1, LicenseNo);
  	     dbpool.executePreparedUpdate();
  	     dbpool.closePreparedStatement();
    }
    
    /**
     * ����dbpool����XX���Ż�ȡ����
     *@param demandno 
     *@return ��
     */
      public void getData(String LicenseNo) throws Exception
      {
          DbPool dbpool = new DbPool();
          try {
            dbpool.open("platformNewDataSource");            
            getData(dbpool, LicenseNo);
          } catch (Exception e) {
        	  e.printStackTrace();
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
    public void getData(DbPool dbpool,String LicenseNo) throws Exception
    {
      String strWherePart = " LicenseNo= ? ";
      ArrayList arrWhereValue = new ArrayList();
      arrWhereValue.add(LicenseNo);
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
        DBCIInsurePmDriver dbCIInsurePmDriver = new DBCIInsurePmDriver();
        if (iLimitCount > 0 && dbCIInsurePmDriver.getCount(dbpool,iWherePart,iWhereValue) > iLimitCount)
        {
            throw new UserException(-98,-1003,"BLCIInsurePmDriver.query");
        }else
        {
            initArr();
            strSqlStatement = " SELECT * FROM CIInsurePmDriver WHERE " + iWherePart;
            schemas = dbCIInsurePmDriver.findByConditions(dbpool,strSqlStatement,iWhereValue);
        }
    }
    /**
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
