package com.sp.prpall.blsvr.misc;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.util.*;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.prpall.dbsvr.misc.DBTBExpense;
import com.sp.prpall.schema.TBExpenseSchema;

/**
 * ����TbExpense��BL��
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>@createdate 2015-04-21</p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLTBExpense{
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */       
    public BLTBExpense(){
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
     *����һ��TbExpenseSchema��¼
     *@param iTbExpenseSchema TbExpenseSchema
     *@throws Exception
     */
    public void setArr(TBExpenseSchema iTbExpenseSchema) throws Exception
    {
       try
       {
         schemas.add(iTbExpenseSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *�õ�һ��TbExpenseSchema��¼
     *@param index �±�
     *@return һ��TbExpenseSchema����
     *@throws Exception
     */
    public TBExpenseSchema getArr(int index) throws Exception
    {
     TBExpenseSchema tbExpenseSchema = null;
       try
       {
        tbExpenseSchema = (TBExpenseSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return tbExpenseSchema;
     }
    /**
     *ɾ��һ��TbExpenseSchema��¼
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
      DBTBExpense dbTbExpense = new DBTBExpense();
      if (iLimitCount > 0 && dbTbExpense.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLTbExpense.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM TB_EXPENSE WHERE " + iWherePart; 
        schemas = dbTbExpense.findByConditions(strSqlStatement, bindValues);
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
      DBTBExpense dbTbExpense = new DBTBExpense();
      if (iLimitCount > 0 && dbTbExpense.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLTbExpense.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM TB_EXPENSE WHERE " + iWherePart; 
        schemas = dbTbExpense.findByConditions(dbpool, strSqlStatement, bindValues);
      }
    }
      
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBTBExpense dbTbExpense = new DBTBExpense();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
      dbTbExpense.setSchema((TBExpenseSchema)schemas.get(i));
      dbTbExpense.insert(dbpool);
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
     *@param policyNo policyNo
     *@return ��
     */
    public void cancel(DbPool dbpool, String policyNo) throws Exception
    {
      String strSqlStatement = "";
      
      strSqlStatement = " DELETE FROM TB_EXPENSE WHERE policyNo='" + policyNo + "'";
      
      dbpool.delete(strSqlStatement);
    }
      
    /**
     * ����dbpool��ɾ������
     *@param policyNo policyNo
     *@return ��
     */
    public void cancel(String policyNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
      try
      {
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
     *@param policyNo policyNo
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
     *@param policyNo policyNo
     *@return ��
     */
    public void getData(DbPool dbpool, String policyNo) throws Exception
    {
       String strWherePart = "";
       strWherePart = "policyNo='" + policyNo + "'";
       query(dbpool, strWherePart, 0, null);
    }
    /**
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
