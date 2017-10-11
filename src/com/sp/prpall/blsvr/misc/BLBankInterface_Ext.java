package com.sp.prpall.blsvr.misc;


import java.util.*;

import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.prpall.dbsvr.misc.DBBankInterface_Ext;
import com.sp.prpall.schema.BankInterface_ExtSchema;

/**
 * ����BankInterface_Ext��BL��
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>@createdate 2014-01-07</p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLBankInterface_Ext{
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */       
    public BLBankInterface_Ext(){
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
     *����һ��BankInterface_ExtSchema��¼
     *@param iBankInterface_ExtSchema BankInterface_ExtSchema
     *@throws Exception
     */
    public void setArr(BankInterface_ExtSchema iBankInterface_ExtSchema) throws Exception
    {
       try
       {
         schemas.add(iBankInterface_ExtSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *�õ�һ��BankInterface_ExtSchema��¼
     *@param index �±�
     *@return һ��BankInterface_ExtSchema����
     *@throws Exception
     */
    public BankInterface_ExtSchema getArr(int index) throws Exception
    {
     BankInterface_ExtSchema bankInterface_ExtSchema = null;
       try
       {
        bankInterface_ExtSchema = (BankInterface_ExtSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return bankInterface_ExtSchema;
     }
    /**
     *ɾ��һ��BankInterface_ExtSchema��¼
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
      DBBankInterface_Ext dbBankInterface_Ext = new DBBankInterface_Ext();
      if (iLimitCount > 0 && dbBankInterface_Ext.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLBankInterface_Ext.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM BankInterface_Ext WHERE " + iWherePart; 
        schemas = dbBankInterface_Ext.findByConditions(strSqlStatement, bindValues);
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
      DBBankInterface_Ext dbBankInterface_Ext = new DBBankInterface_Ext();
      if (iLimitCount > 0 && dbBankInterface_Ext.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLBankInterface_Ext.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM BankInterface_Ext WHERE " + iWherePart; 
        schemas = dbBankInterface_Ext.findByConditions(dbpool, strSqlStatement, bindValues);
      }
    }
      
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBBankInterface_Ext dbBankInterface_Ext = new DBBankInterface_Ext();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
      dbBankInterface_Ext.setSchema((BankInterface_ExtSchema)schemas.get(i));
      dbBankInterface_Ext.insert(dbpool);
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
     *@return ��
     */
    public void cancel(DbPool dbpool, String policyNo) throws Exception
    {
      String strSqlStatement = "";
      
      strSqlStatement = " DELETE FROM BankInterface_Ext WHERE policyNo='" + policyNo + "'";
      
      dbpool.delete(strSqlStatement);
    }
      
    /**
     * ����dbpool��ɾ������
     *@param policyNo PolicyNo
     *@return ��
     */
    public void cancel(String policyNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      try
      {
    	dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
        dbpool.beginTransaction();
        cancel(dbpool, policyNo);
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
    public void getData(String policyNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
      getData(dbpool, policyNo);
      dbpool.close();
    }
      
    /**
     * ��dbpool����������ȡ����
     *@param dbpool ���ӳ�
     *@param policyNo PolicyNo
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
        DBBankInterface_Ext dbBankInterface_Ext = new DBBankInterface_Ext();
        if (iLimitCount > 0 && dbBankInterface_Ext.getCount(dbpool,iWherePart,iWhereValue) > iLimitCount)
        {
            throw new UserException(-98,-1003,"BLBankInterface_Ext.query");
        }else
        {
            initArr();
            strSqlStatement = " SELECT * FROM BANKINTERFACE_EXT WHERE " + iWherePart;
            schemas = dbBankInterface_Ext.findByConditions(dbpool,strSqlStatement,iWhereValue);
        }
    }
    
    
    
    /**
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
