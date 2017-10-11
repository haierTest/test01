package com.sp.indiv.ci.blsvr;

import java.util.ArrayList;
import java.util.Vector;

import com.sp.indiv.ci.dbsvr.DBCIInsureDemand;
import com.sp.indiv.ci.dbsvr.DBCIInsurePmVehicle;
import com.sp.indiv.ci.schema.CIInsureDemandSchema;
import com.sp.indiv.ci.schema.CIInsurePmVehicleSchema;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;

/**
 * ����XX��ѯ����-CIInsurePmVehicle��BL��
 * 
 * @createdate 2014-01-24
 * @author  fanjiangtao 2014-01-24
 * 
 */
public class BLCIInsurePmVehicle{
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */       
    public BLCIInsurePmVehicle(){
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
     *����һ��CIInsurePmVehicleSchema��¼
     *@param CIInsurePmVehicleSchema iCIInsurePmVehicleSchema
     *@throws Exception
     */
    public void setArr(CIInsurePmVehicleSchema iCIInsurePmVehicleSchema) throws Exception
    {
       try
       {
         schemas.add(iCIInsurePmVehicleSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *�õ�һ��CIInsurePmVehicleSchema��¼
     *@param index �±�
     *@return һ��CIInsurePmVehicleSchema����
     *@throws Exception
     */
    public CIInsurePmVehicleSchema getArr(int index) throws Exception
    {
        CIInsurePmVehicleSchema cIInsurePmVehicleSchema = null;
       try
       {
           cIInsurePmVehicleSchema = (CIInsurePmVehicleSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return cIInsurePmVehicleSchema;
     }
    /**
     *ɾ��һ��CIInsurePmVehicleSchema��¼
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
     * ���ղ�ѯ�����õ�һ���¼�������������¼����schemas����
     * @param EngineNo    ��������
     * @param CarMark     ���ƺ���
     * @param InsertDate  ����ʱ��
     * @param RackNo      ����ʶ����ţ����ܺ�/VIN�룩
     * @throws UserException
     * @throws Exception
     */
    public void query(String EngineNo,String CarMark,String InsertDate,String RackNo) throws UserException,Exception
    {
        this.query( EngineNo, CarMark, InsertDate, RackNo,Integer.parseInt(SysConst.getProperty("QUERY_LIMIT_COUNT").trim()));
    }
    
    /**
     * ����XX�Ż�ȡ����,���ð󶨱�������
     *@param dbpool ���ӳ�
     *@param iPolicyNo XX��
     *@return ��
     */
    public void query(int iLimitCount,String iPolicyNo) throws Exception
    {
    	DbPool dbpool = new DbPool();
        try {
            String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
            if ("1".equals(strSwitch)) {
                dbpool.open("platformNewDataSource");            
            } else {
                dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
            }
            String strWherePart = " PolicyNo= ? ";
            ArrayList arrWhereValue = new ArrayList();
            arrWhereValue.add(iPolicyNo);
            query(dbpool, strWherePart, arrWhereValue, iLimitCount);
        } catch (Exception e) 
        {
        	throw e;
        } finally {
            dbpool.close();
        }    
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
      DBCIInsurePmVehicle dbCIInsurePmVehicle = new DBCIInsurePmVehicle();
      if (iLimitCount > 0 && dbCIInsurePmVehicle.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLCIInsurePmVehicle.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM CIInsurePmVehicle WHERE " + iWherePart; 
        schemas = dbCIInsurePmVehicle.findByConditions(strSqlStatement);
      }
    }
    /**
     * ���ղ�ѯ�����ͼ�¼�����Ƶõ�һ���¼�������������¼����schemas����
     * @param EngineNo   ��������
     * @param CarMark    ���ƺ���
     * @param InsertDate ����ʱ��
     * @param RackNo     ����ʶ����ţ����ܺ�/VIN�룩
     * @param iLimitCount  ��¼������(iLimitCount=0: ������)
     * @throws UserException
     * @throws Exception
     */
    public void query(String EngineNo,String CarMark,String InsertDate,String RackNo,int iLimitCount)throws UserException,Exception
    {
        String strSqlStatement = "";
        String iWherePart = "engineNo='"+EngineNo+"' and carMark='"+CarMark+"' and InsertDate=to_date('"+InsertDate+"','yyyy-mm-dd hh24:mi:ss') and RackNo='"+RackNo+"'";
        DBCIInsurePmVehicle dbCIInsurePmVehicle = new DBCIInsurePmVehicle();
        if (iLimitCount > 0 && dbCIInsurePmVehicle.getCount(iWherePart) > iLimitCount)
        {
          throw new UserException(-98,-1003,"BLCIInsurePmVehicle.query");
        }
        else
        {
          initArr();
          strSqlStatement = " SELECT * FROM CIInsurePmVehicle WHERE " + iWherePart; 
          schemas = dbCIInsurePmVehicle.findByConditions(strSqlStatement);
        }
    }
    /**
     *���ղ�ѯ�����õ�һ���¼�������������¼����schemas���󣬴���ʷ��Ϣ���ȡ
     *@param iWherePart ��ѯ����(���������־�)
     *@throws UserException
     *@throws Exception
     */
    public void queryHistory(String iWherePart) throws UserException,Exception
    {
       this.queryHistory(iWherePart,Integer.parseInt(SysConst.getProperty("QUERY_LIMIT_COUNT").trim()));
    }
    /**
     *���ղ�ѯ�����ͼ�¼�����Ƶõ�һ���¼�������������¼����schemas���󣬴���ʷ��Ϣ���ȡ
     *@param iWherePart ��ѯ����(���������־�)
     *@param iLimitCount ��¼������(iLimitCount=0: ������)
     *@throws UserException
     *@throws Exception
     */
    public void queryHistory(String iWherePart,int iLimitCount) throws UserException,Exception
    {
      String strSqlStatement = "";
      DBCIInsurePmVehicle dbCIInsurePmVehicle = new DBCIInsurePmVehicle();
      if (iLimitCount > 0 && dbCIInsurePmVehicle.getCountHistory(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLCIInsurePmVehicle.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM CIInsurePmVehicle WHERE " + iWherePart; 
        schemas = dbCIInsurePmVehicle.findByConditionsHistory(strSqlStatement);
      }
    }
       
    /**
     *���ղ�ѯ�����õ�һ���¼�������������¼����schemas�����ȴ��������ȡ�����Ϣ�����δȡ�����ٴ���ʷ��Ϣ���ȡ��
     *@param iWherePart ��ѯ����(���������־�)
     *@throws UserException
     *@throws Exception
     */
    public void queryCurrentAndHistory(String iWherePart,int iLimitCount) throws UserException,Exception
    {
       
       this.query(iWherePart,iLimitCount);
       
       if(this.getSize() == 0){
    	   this.queryHistory(iWherePart,iLimitCount);
       }
    }
    
    /**
     *���ղ�ѯ�����õ�һ���¼�������������¼����schemas�����ȴ��������ȡ�����Ϣ�����δȡ�����ٴ���ʷ��Ϣ���ȡ��
     *@param iWherePart ��ѯ����(���������־�)
     *@throws UserException
     *@throws Exception
     */
    public void queryCurrentAndHistory(String iWherePart) throws UserException,Exception
    {
       
       this.query(iWherePart);
       
       if(this.getSize() == 0){
    	   this.queryHistory(iWherePart);
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
     * ���ղ�ѯ�����õ�һ���¼�������������¼����schemas����
     * @param dbpool    ȫ�ֳ�
     * @param EngineNo  ��������
     * @param CarMark   ���ƺ���
     * @param InsertDate ����ʱ��
     * @param RackNo     ����ʶ����ţ����ܺ�/VIN�룩
     * @throws UserException
     * @throws Exception
     */
    public void query(DbPool dbpool,String EngineNo,String CarMark,String InsertDate,String RackNo)throws UserException,Exception
    {
        this.query(dbpool,EngineNo,CarMark,InsertDate,RackNo,Integer.parseInt(SysConst.getProperty("QUERY_LIMIT_COUNT").trim()));
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
      DBCIInsurePmVehicle dbCIInsurePmVehicle = new DBCIInsurePmVehicle();
      if (iLimitCount > 0 && dbCIInsurePmVehicle.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLCIInsurePmVehicle.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM CIInsurePmVehicle WHERE " + iWherePart; 
        schemas = dbCIInsurePmVehicle.findByConditions(dbpool,strSqlStatement);
      }
    }
    /**
     * ���ղ�ѯ�����ͼ�¼�����Ƶõ�һ���¼�������������¼����schemas����
     * @param dbpool     ȫ�ֳ�
     * @param EngineNo   ��������
     * @param CarMark    ���ƺ���
     * @param InsertDate ����ʱ��
     * @param RackNo     ����ʶ����ţ����ܺ�/VIN�룩
     * @param iLimitCount
     * @throws UserException
     * @throws Exception
     */
    public void query (DbPool dbpool,String EngineNo,String CarMark,String InsertDate,String RackNo,int iLimitCount )throws UserException,Exception
    {
        String strSqlStatement = "";
        String iWherePart = "engineNo='"+EngineNo+"' and carMark='"+CarMark+"' and InsertDate=to_date('"+InsertDate+"','yyyy-mm-dd hh24:mi:ss') and RackNo='"+RackNo+"'";
        DBCIInsurePmVehicle dbCIInsurePmVehicle = new DBCIInsurePmVehicle();
        if (iLimitCount > 0 && dbCIInsurePmVehicle.getCount(iWherePart) > iLimitCount)
        {
          throw new UserException(-98,-1003,"BLCIInsurePmVehicle.query");
        }
        else
        {
          initArr();
          strSqlStatement = " SELECT * FROM CIInsurePmVehicle WHERE " + iWherePart; 
          schemas = dbCIInsurePmVehicle.findByConditions(dbpool,strSqlStatement);
        }
    }
    public void updateByProposalNo(DbPool dbpool, String strWhere) throws Exception
    {
        DBCIInsurePmVehicle dbCIInsurePmVehicle = new DBCIInsurePmVehicle();
        
        int i = 0;
        
        for(i = 0; i< schemas.size(); i++)
        {
            dbCIInsurePmVehicle.setSchema((CIInsurePmVehicleSchema)schemas.get(i));
            dbCIInsurePmVehicle.update(dbpool, strWhere);
        }
    }
      
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
        DBCIInsurePmVehicle dbCIInsurePmVehicle = new DBCIInsurePmVehicle();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
          dbCIInsurePmVehicle.setSchema((CIInsurePmVehicleSchema)schemas.get(i));
          dbCIInsurePmVehicle.insert(dbpool);
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
      public void getData(String iPmVehicleID) throws Exception
      {
          DbPool dbpool = new DbPool();
          try {
              dbpool.open("platformNewDataSource");            
              getData(dbpool, iPmVehicleID);
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
    public void getData(DbPool dbpool,String iPmVehicleID) throws Exception
    {
      String strWherePart = " PmVehicleID= ? ";
      ArrayList arrWhereValue = new ArrayList();
      arrWhereValue.add(iPmVehicleID);
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
        DBCIInsurePmVehicle dbCIInsurePmVehicle = new DBCIInsurePmVehicle();
        if (iLimitCount > 0 && dbCIInsurePmVehicle.getCount(dbpool,iWherePart,iWhereValue) > iLimitCount)
        {
            throw new UserException(-98,-1003,"BLCIInsurePmVehicle.query");
        }else
        {
            initArr();
            strSqlStatement = " SELECT * FROM CIInsurePmVehicle WHERE " + iWherePart;
            schemas = dbCIInsurePmVehicle.findByConditions(dbpool,strSqlStatement,iWhereValue);
        }
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
    public void query(String iWherePart,ArrayList iWhereValue) throws UserException,Exception
    {
    	 DbPool dbpool = new DbPool();
         try {
             String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
             if ("1".equals(strSwitch)) {
                 dbpool.open("platformNewDataSource");            
             } else {
                 dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
             }
             query(dbpool, iWherePart,iWhereValue,0);
         } catch (Exception e) {
        	 e.printStackTrace();
         } finally {
             dbpool.close();
         }
    }
    
    
    public void update() throws Exception
    {
      DbPool dbpool = new DbPool();

      dbpool.open("platformNewDataSource");

      try
      {
        dbpool.beginTransaction();
        update(dbpool);
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
     *��dbpool��update����
     *@param ��
     *@return ��
     */
    public void update(DbPool dbpool) throws Exception
    {
    	DBCIInsurePmVehicle dbCIInsurePmVehicle = new DBCIInsurePmVehicle();

      int i = 0;

      for(i = 0; i< schemas.size(); i++)
      {
    	  dbCIInsurePmVehicle.setSchema((CIInsurePmVehicleSchema)schemas.get(i));
    	  dbCIInsurePmVehicle.update(dbpool);
      }
    }
    
    
    /**
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
