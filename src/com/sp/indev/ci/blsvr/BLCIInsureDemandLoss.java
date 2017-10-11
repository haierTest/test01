package com.sp.indiv.ci.blsvr;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.util.*;

import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.indiv.ci.dbsvr.DBCIInsureDemandLoss;
import com.sp.indiv.ci.dbsvr.DBCIInsureDemandPay;
import com.sp.indiv.ci.schema.CIInsureDemandLossSchema;

/**
 * ����XX��ѯΥ�±�-CIInsureDemandLoss��BL��
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>@createdate 2006-06-14</p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLCIInsureDemandLoss{
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */       
    public BLCIInsureDemandLoss(){
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
     *����һ��CIInsureDemandLossSchema��¼
     *@param iCIInsureDemandLossSchema CIInsureDemandLossSchema
     *@throws Exception
     */
    public void setArr(CIInsureDemandLossSchema iCIInsureDemandLossSchema) throws Exception
    {
       try
       {
         schemas.add(iCIInsureDemandLossSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *�õ�һ��CIInsureDemandLossSchema��¼
     *@param index �±�
     *@return һ��CIInsureDemandLossSchema����
     *@throws Exception
     */
    public CIInsureDemandLossSchema getArr(int index) throws Exception
    {
     CIInsureDemandLossSchema cIInsureDemandLossSchema = null;
       try
       {
        cIInsureDemandLossSchema = (CIInsureDemandLossSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return cIInsureDemandLossSchema;
     }
    /**
     *ɾ��һ��CIInsureDemandLossSchema��¼
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
     *���ղ�ѯ�����õ�һ���¼�������ð󶨱����Ĳ�ѯ����
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
      DBCIInsureDemandLoss dbCIInsureDemandLoss = new DBCIInsureDemandLoss();
      if (iLimitCount > 0 && dbCIInsureDemandLoss.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLCIInsureDemandLoss.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM CIInsureDemandLoss WHERE " + iWherePart; 
        schemas = dbCIInsureDemandLoss.findByConditions(strSqlStatement);
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
      DBCIInsureDemandLoss dbCIInsureDemandLoss = new DBCIInsureDemandLoss();
      if (iLimitCount > 0 && dbCIInsureDemandLoss.getCountHistory(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLCIInsureDemandLoss.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM CIInsureDemandLoss WHERE " + iWherePart; 
        schemas = dbCIInsureDemandLoss.findByConditionsHistory(strSqlStatement);
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
     *���ղ�ѯ�����õ�һ���¼�����󶨱���
     *@param iWherePart ��ѯ����(���������־�)
     *@param iWhereValue ��ѯ����ֵ
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
      DBCIInsureDemandLoss dbCIInsureDemandLoss = new DBCIInsureDemandLoss();
      if (iLimitCount > 0 && dbCIInsureDemandLoss.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLCIInsureDemandLoss.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM CIInsureDemandLoss WHERE " + iWherePart; 
        schemas = dbCIInsureDemandLoss.findByConditions(dbpool,strSqlStatement);
      }
    }
      
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBCIInsureDemandLoss dbCIInsureDemandLoss = new DBCIInsureDemandLoss();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
      dbCIInsureDemandLoss.setSchema((CIInsureDemandLossSchema)schemas.get(i));
      dbCIInsureDemandLoss.insert(dbpool);
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
        DBCIInsureDemandLoss dbCIInsureDemandLoss = new DBCIInsureDemandLoss();
        if (iLimitCount > 0 && dbCIInsureDemandLoss.getCount(dbpool,iWherePart,iWhereValue) > iLimitCount)
        {
            throw new UserException(-98,-1003,"BLCIInsureDemandLoss.query");
        }else
        {
            initArr();
            strSqlStatement = " SELECT * FROM CIInsureDemandLoss WHERE " + iWherePart;
            schemas = dbCIInsureDemandLoss.findByConditions(dbpool,strSqlStatement,iWhereValue);
        }
    } 
    /**
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
