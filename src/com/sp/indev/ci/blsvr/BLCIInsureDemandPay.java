package com.sp.indiv.ci.blsvr;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.util.*;

import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.indiv.ci.dbsvr.DBCIInsureDemand;
import com.sp.indiv.ci.dbsvr.DBCIInsureDemandPay;
import com.sp.indiv.ci.schema.CIInsureDemandPaySchema;

/**
 * ����XX��ѯXXXXX��-CIInsureDemandPay��BL��
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>@createdate 2006-06-14</p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLCIInsureDemandPay{
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */       
    public BLCIInsureDemandPay(){
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
     *����һ��CIInsureDemandPaySchema��¼
     *@param iCIInsureDemandPaySchema CIInsureDemandPaySchema
     *@throws Exception
     */
    public void setArr(CIInsureDemandPaySchema iCIInsureDemandPaySchema) throws Exception
    {
       try
       {
         schemas.add(iCIInsureDemandPaySchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *�õ�һ��CIInsureDemandPaySchema��¼
     *@param index �±�
     *@return һ��CIInsureDemandPaySchema����
     *@throws Exception
     */
    public CIInsureDemandPaySchema getArr(int index) throws Exception
    {
     CIInsureDemandPaySchema cIInsureDemandPaySchema = null;
       try
       {
        cIInsureDemandPaySchema = (CIInsureDemandPaySchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return cIInsureDemandPaySchema;
     }
    /**
     *ɾ��һ��CIInsureDemandPaySchema��¼
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
     *���ݲ�ѯSQL���ð󶨱�������
     *@param iWherePart ��ѯ����(���������־�)
     *@param iWhereValue ��ѯ����ֵ
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
           
           query(dbpool, iWherePart, iWhereValue, Integer.parseInt(SysConst.getProperty("QUERY_LIMIT_COUNT").trim()));
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
      DBCIInsureDemandPay dbCIInsureDemandPay = new DBCIInsureDemandPay();
      if (iLimitCount > 0 && dbCIInsureDemandPay.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLCIInsureDemandPay.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM CIInsureDemandPay WHERE " + iWherePart; 
        schemas = dbCIInsureDemandPay.findByConditions(strSqlStatement);
      }
    }
    
    
    /**
     *���ղ�ѯ�����õ�һ���¼�������������¼����schemas����
     *@param iWherePart ��ѯ����(���������־�)
     *@throws UserException
     *@throws Exception
     */
    public void queryHistory(String iWherePart) throws UserException,Exception
    {
       this.queryHistory(iWherePart,Integer.parseInt(SysConst.getProperty("QUERY_LIMIT_COUNT").trim()));
    }
    /**
     *���ղ�ѯ�����ͼ�¼�����Ƶõ�һ���¼�������������¼����schemas����
     *@param iWherePart ��ѯ����(���������־�)
     *@param iLimitCount ��¼������(iLimitCount=0: ������)
     *@throws UserException
     *@throws Exception
     */
    public void queryHistory(String iWherePart,int iLimitCount) throws UserException,Exception
    {
      String strSqlStatement = "";
      DBCIInsureDemandPay dbCIInsureDemandPay = new DBCIInsureDemandPay();
      if (iLimitCount > 0 && dbCIInsureDemandPay.getCountHistory(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLCIInsureDemandPay.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM CIInsureDemandPay WHERE " + iWherePart; 
        schemas = dbCIInsureDemandPay.findByConditionsHistory(strSqlStatement);
      }
    }    
    
    /**
     *���ղ�ѯ�����õ�һ���¼�������������¼����schemas����,�ȴ�����������ȡ����ȡ����������ݣ������ʷ��Ϣ���ȡ
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
     *���ղ�ѯ�����õ�һ���¼�����󶨱�����
     *@param iWherePart ��ѯ����(���������־�)
     *@throws UserException
     *@throws Exception
     */
    public void queryCurrentAndHistory(String iWherePart,ArrayList iWhereValue) throws UserException,Exception
    {
       
       this.query(iWherePart,iWhereValue);
       
       if(this.getSize() == 0){
    	   this.queryHistory(iWherePart,iWhereValue);
       }
    }
    /**
     *���ղ�ѯ�����õ�һ���¼�����󶨱���
     *@param iWherePart ��ѯ����(���������־�)
     *@param iWhereValue ��ѯ����ֵ
     *@throws UserException
     *@throws Exception
     */
    public void queryHistory(String iWherePart,ArrayList iWhereValue) throws UserException,Exception
    {
       DbPool dbpool = new DbPool();
       
       try {
           
           String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
           if ("1".equals(strSwitch)) {
               dbpool.open("platformNewDataSource");            
           } else {
               dbpool.open(SysConfig.CONST_HISTORYDATASOURCE);
           }
           
           query(dbpool,iWherePart,iWhereValue,Integer.parseInt(SysConst.getProperty("QUERY_LIMIT_COUNT").trim()));
        }catch (Exception e){
           	throw e;
        }finally{
            dbpool.close();
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
      DBCIInsureDemandPay dbCIInsureDemandPay = new DBCIInsureDemandPay();
      if (iLimitCount > 0 && dbCIInsureDemandPay.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLCIInsureDemandPay.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM CIInsureDemandPay WHERE " + iWherePart; 
        schemas = dbCIInsureDemandPay.findByConditions(dbpool,strSqlStatement);
      }
    }
      
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBCIInsureDemandPay dbCIInsureDemandPay = new DBCIInsureDemandPay();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
      dbCIInsureDemandPay.setSchema((CIInsureDemandPaySchema)schemas.get(i));
      dbCIInsureDemandPay.insert(dbpool);
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
     *���ղ�ѯ�����ͼ�¼�����Ƶõ�һ���¼�������������¼����schemas����
     *@author wanghe 2011-01-04
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
        DBCIInsureDemandPay dbCIInsureDemandPay = new DBCIInsureDemandPay();
        if (iLimitCount > 0 && dbCIInsureDemandPay.getCount(dbpool,iWherePart,iWhereValue) > iLimitCount)
        {
            throw new UserException(-98,-1003,"BLCIInsureDemandPay.query");
        }else
        {
            initArr();
            strSqlStatement = " SELECT * FROM CIInsureDemandPay WHERE " + iWherePart;
            schemas = dbCIInsureDemandPay.findByConditions(dbpool,strSqlStatement,iWhereValue);
        }
    }
    /**
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
