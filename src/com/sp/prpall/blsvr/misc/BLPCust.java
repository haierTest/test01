package com.sp.prpall.blsvr.misc;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.util.*;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.prpall.dbsvr.misc.DBPCust;
import com.sp.prpall.schema.PCustSchema;

/**
 * ����P_CUST��BL��
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>@createdate 2015-04-01</p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLPCust{
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */       
    public BLPCust(){
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
     *����һ��P_CUSTSchema��¼
     *@param iP_CUSTSchema P_CUSTSchema
     *@throws Exception
     */
    public void setArr(PCustSchema iP_CUSTSchema) throws Exception
    {
       try
       {
         schemas.add(iP_CUSTSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *�õ�һ��P_CUSTSchema��¼
     *@param index �±�
     *@return һ��P_CUSTSchema����
     *@throws Exception
     */
    public PCustSchema getArr(int index) throws Exception
    {
     PCustSchema p_CUSTSchema = null;
       try
       {
        p_CUSTSchema = (PCustSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return p_CUSTSchema;
     }
    /**
     *ɾ��һ��P_CUSTSchema��¼
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
      DBPCust dbP_CUST = new DBPCust();
      if (iLimitCount > 0 && dbP_CUST.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLP_CUST.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM P_CUST WHERE " + iWherePart; 
        schemas = dbP_CUST.findByConditions(strSqlStatement, bindValues);
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
      DBPCust dbP_CUST = new DBPCust();
      if (iLimitCount > 0 && dbP_CUST.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLP_CUST.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM P_CUST WHERE " + iWherePart; 
        schemas = dbP_CUST.findByConditions(dbpool, strSqlStatement, bindValues);
      }
    }
      
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPCust dbP_CUST = new DBPCust();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
      dbP_CUST.setSchema((PCustSchema)schemas.get(i));
      dbP_CUST.insert(dbpool);
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
     * ��dbpool��ɾ������
     *@param dbpool    ���ӳ�
     *@param pOLICYNO POLICYNO
     *@return ��
     */
    public void cancel(DbPool dbpool, String pOLICYNO) throws Exception
    {
      String strSqlStatement = "";
      
      strSqlStatement = " DELETE FROM P_CUST WHERE pOLICYNO='" + pOLICYNO + "'";
      
      dbpool.delete(strSqlStatement);
    }
      
    /**
     * ����dbpool��ɾ������
     *@param pOLICYNO POLICYNO
     *@return ��
     */
    public void cancel(String pOLICYNO) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
      try
      {
        dbpool.beginTransaction();
        cancel(dbpool, pOLICYNO);
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
     *@param pOLICYNO POLICYNO
     *@return ��
     */
    public void getData(String pOLICYNO) throws Exception
    {
      DbPool dbpool = new DbPool();
      dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
      getData(dbpool, pOLICYNO);
      dbpool.close();
    }
      
    /**
     * ��dbpool����������ȡ����
     *@param dbpool ���ӳ�
     *@param pOLICYNO POLICYNO
     *@return ��
     */
    public void getData(DbPool dbpool, String pOLICYNO) throws Exception
    {
       String strWherePart = "";
       strWherePart = "pOLICYNO='" + pOLICYNO + "'";
       query(dbpool, strWherePart, 0, null);
    }
    /**
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
