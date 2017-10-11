package com.sp.prpall.blsvr.misc;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.util.*;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.prpall.dbsvr.misc.DBPRisk;
import com.sp.prpall.schema.PRiskSchema;

/**
 * ����P_RISK��BL��
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>@createdate 2015-04-01</p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLPRisk{
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */       
    public BLPRisk(){
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
     *����һ��P_RISKSchema��¼
     *@param iP_RISKSchema P_RISKSchema
     *@throws Exception
     */
    public void setArr(PRiskSchema iP_RISKSchema) throws Exception
    {
       try
       {
         schemas.add(iP_RISKSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *�õ�һ��P_RISKSchema��¼
     *@param index �±�
     *@return һ��P_RISKSchema����
     *@throws Exception
     */
    public PRiskSchema getArr(int index) throws Exception
    {
     PRiskSchema p_RISKSchema = null;
       try
       {
        p_RISKSchema = (PRiskSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return p_RISKSchema;
     }
    /**
     *ɾ��һ��P_RISKSchema��¼
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
      DBPRisk dbP_RISK = new DBPRisk();
      if (iLimitCount > 0 && dbP_RISK.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLP_RISK.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM P_RISK WHERE " + iWherePart; 
        schemas = dbP_RISK.findByConditions(strSqlStatement, bindValues);
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
      DBPRisk dbP_RISK = new DBPRisk();
      if (iLimitCount > 0 && dbP_RISK.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLP_RISK.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM P_RISK WHERE " + iWherePart; 
        schemas = dbP_RISK.findByConditions(dbpool, strSqlStatement, bindValues);
      }
    }
      
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPRisk dbP_RISK = new DBPRisk();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
      dbP_RISK.setSchema((PRiskSchema)schemas.get(i));
      dbP_RISK.insert(dbpool);
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
      
      strSqlStatement = " DELETE FROM P_RISK WHERE pOLICYNO='" + pOLICYNO + "'";
      
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
