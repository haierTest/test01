package com.sp.prpall.blsvr.misc;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.util.*;

import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.prpall.dbsvr.misc.DBPInsurance;
import com.sp.prpall.schema.PInsuranceSchema;

/**
 * ����PInsurance��BL��
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>@createdate 2014-02-26</p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLPInsurance{
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */       
    public BLPInsurance(){
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
     *����һ��PInsuranceSchema��¼
     *@param iPInsuranceSchema PInsuranceSchema
     *@throws Exception
     */
    public void setArr(PInsuranceSchema iPInsuranceSchema) throws Exception
    {
       try
       {
         schemas.add(iPInsuranceSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *�õ�һ��PInsuranceSchema��¼
     *@param index �±�
     *@return һ��PInsuranceSchema����
     *@throws Exception
     */
    public PInsuranceSchema getArr(int index) throws Exception
    {
     PInsuranceSchema pInsuranceSchema = null;
       try
       {
        pInsuranceSchema = (PInsuranceSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return pInsuranceSchema;
     }
    /**
     *ɾ��һ��PInsuranceSchema��¼
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
     *@param bindValues �󶨲���
     *@throws UserException
     *@throws Exception
     */
    public void query(String iWherePart, ArrayList bindValues) throws UserException,Exception
    {
       this.query(iWherePart, Integer.parseInt(SysConst.getProperty("QUERY_LIMIT_COUNT").trim()), bindValues);
    }
    /**
     *���ղ�ѯ�����ͼ�¼�����Ƶõ�һ���¼�������������¼����schemas����
     *@param iWherePart ��ѯ����(���������־�)
     *@param iLimitCount ��¼������(iLimitCount=0: ������)
     *@param bindValues �󶨲���
     *@throws UserException
     *@throws Exception
     */
    public void query(String iWherePart, int iLimitCount, ArrayList bindValues) throws UserException,Exception
    {
      String strSqlStatement = "";
      DBPInsurance dbPInsurance = new DBPInsurance();
      if (iLimitCount > 0 && dbPInsurance.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPInsurance.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM P_Insurance WHERE " + iWherePart; 
        schemas = dbPInsurance.findByConditions(strSqlStatement, bindValues);
      }
    }
    /**
     *���ղ�ѯ�����õ�һ���¼�������������¼����schemas����
     *@param dbpool ȫ�ֳ�
     *@param iWherePart ��ѯ����(���������־�)
     *@param bindValues �󶨲���
     *@throws UserException
     *@throws Exception
     */
    public void query(DbPool dbpool, String iWherePart, ArrayList bindValues) throws UserException,Exception
    {
       this.query(dbpool,iWherePart,Integer.parseInt(SysConst.getProperty("QUERY_LIMIT_COUNT").trim()),bindValues);
    }
    /**
     *���ղ�ѯ�����ͼ�¼�����Ƶõ�һ���¼�������������¼����schemas����
     *@param dbpool ȫ�ֳ�
     *@param iWherePart ��ѯ����(���������־�)
     *@param iLimitCount ��¼������(iLimitCount=0: ������)
     *@param bindValues �󶨲���
     *@throws UserException
     *@throws Exception
     */
    public void query(DbPool dbpool, String iWherePart, int iLimitCount, ArrayList bindValues) throws UserException,Exception
    {
      String strSqlStatement = "";
      DBPInsurance dbPInsurance = new DBPInsurance();
      if (iLimitCount > 0 && dbPInsurance.getCount(iWherePart,bindValues) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPInsurance.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM P_Insurance WHERE " + iWherePart; 
        schemas = dbPInsurance.findByConditions(dbpool, strSqlStatement, bindValues);
      }
    }
      
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPInsurance dbPInsurance = new DBPInsurance();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
      dbPInsurance.setSchema((PInsuranceSchema)schemas.get(i));
      dbPInsurance.insert(dbpool);
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
      try
      {
    	dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
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
     * ��dbpool��ɾ������
     *@param dbpool    ���ӳ�
     *@param policyNo PolicyNo
     *@param serialNo SerialNo
     *@return ��
     */
    public void cancel(DbPool dbpool, String policyNo, String serialNo) throws Exception
    {
      String strSqlStatement = "";
      
      strSqlStatement = " DELETE FROM P_Insurance WHERE policyNo='" + policyNo + "', AND serialNo='" + serialNo + "'";
      
      dbpool.delete(strSqlStatement);
    }
      
    /**
     * ����dbpool��ɾ������
     *@param policyNo PolicyNo
     *@param serialNo SerialNo
     *@return ��
     */
    public void cancel(String policyNo, String serialNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      try
      {
    	dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
        dbpool.beginTransaction();
        cancel(dbpool, policyNo, serialNo);
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
     * ����dbpool����������ȡ����
     *@param policyNo PolicyNo
     *@return ��
     */
    public void getData(String policyNo,String serialNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      try
      {
    	dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
    	getData(dbpool, policyNo,serialNo);
      }
      catch (Exception e)
      {
    	  e.printStackTrace();
      }
      finally
      {
        dbpool.close();
      }
    }
      
    /**
     * ��dbpool����������ȡ����
     *@param dbpool ���ӳ�
     *@param policyNo PolicyNo
     *@param serialNo SerialNo
     *@return ��
     */
    public void getData(DbPool dbpool, String policyNo, String serialNo) throws Exception
    {
       String strWherePart = " PolicyNo= ? and SerialNo=? ";
       ArrayList arrWhereValue = new ArrayList();
       arrWhereValue.add(policyNo);
       query(dbpool, strWherePart, arrWhereValue, 0);
    }
    
    
    /**
     * ����dbpool����������ȡ����
     *@param policyNo PolicyNo
     *@return ��
     */
    public void getData(String policyNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      try
      {
    	dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
    	getData(dbpool, policyNo);
      }
      catch (Exception e)
      {
    	  e.printStackTrace();
      }
      finally
      {
        dbpool.close();
      }
    }
    
    
    /**
     * ��dbpool����������ȡ����
     *@param dbpool ���ӳ�
     *@param policyNo PolicyNo
     *@param serialNo SerialNo
     *@return ��
     */
    public void getData(DbPool dbpool, String policyNo) throws Exception
    {
       String strWherePart = " PolicyNo= ? ";
       ArrayList arrWhereValue = new ArrayList();
       arrWhereValue.add(policyNo);
       query(dbpool, strWherePart, arrWhereValue, 0);
    }
    
    /**
     *���ղ�ѯ�����ͼ�¼�����Ƶõ�һ���¼�������������¼����schemas����
     *@author liuweichang 20140228
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
        DBPInsurance dbPInsurance = new DBPInsurance();
        if (iLimitCount > 0 && dbPInsurance.getCount(dbpool,iWherePart,iWhereValue) > iLimitCount)
        {
            throw new UserException(-98,-1003,"BLPInsurance.query");
        }else
        {
            initArr();
            strSqlStatement = " SELECT * FROM P_Insurance WHERE " + iWherePart;
            schemas = dbPInsurance.findByConditions(dbpool,strSqlStatement,iWhereValue);
        }
    }
    
    /**
     *���ղ�ѯ�����ͼ�¼�����Ƶõ�һ���¼�������������¼����schemas����
     *@author liuweichang 20140228
     *@param iWherePart  ��ѯ����,�����������Ѱ󶨱�����ʽ���ʺŸ���������ֵ����һ��
     *@param iWhereValue ��ѯ�������ֶ�ֵ
     *@param iLimitCount ��¼������(iLimitCount=0: ������)
     *@throws UserException
     *@throws Exception
     */
    public void query(String iWherePart,ArrayList iWhereValue, int iLimitCount) throws UserException,Exception
    {
        String strSqlStatement = "";
        DBPInsurance dbPInsurance = new DBPInsurance();
        if (iLimitCount > 0 && dbPInsurance.getCount(iWherePart,iWhereValue) > iLimitCount)
        {
            throw new UserException(-98,-1003,"BLPInsurance.query");
        }else
        {
            initArr();
            strSqlStatement = " SELECT * FROM P_Insurance WHERE " + iWherePart;
            schemas = dbPInsurance.findByConditions(strSqlStatement,iWhereValue);
        }
    }
    
    /**
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
