package com.sp.prpall.blsvr.jf;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.util.*;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.prpall.dbsvr.jf.DBPrpJPowerRule;
import com.sp.prpall.schema.PrpJPowerRuleSchema;

/**
 * ����PrpJPowerRule��BL��
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>@createdate 2014-09-29</p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLPrpJPowerRule{
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */       
    public BLPrpJPowerRule(){ 
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
     *����һ��PrpJPowerRuleSchema��¼
     *@param iPrpJPowerRuleSchema PrpJPowerRuleSchema
     *@throws Exception
     */
    public void setArr(PrpJPowerRuleSchema iPrpJPowerRuleSchema) throws Exception
    {
       try
       {
         schemas.add(iPrpJPowerRuleSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *�õ�һ��PrpJPowerRuleSchema��¼
     *@param index �±�
     *@return һ��PrpJPowerRuleSchema����
     *@throws Exception
     */
    public PrpJPowerRuleSchema getArr(int index) throws Exception
    {
     PrpJPowerRuleSchema prpJPowerRuleSchema = null;
       try
       {
        prpJPowerRuleSchema = (PrpJPowerRuleSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpJPowerRuleSchema;
     }
    /**
     *ɾ��һ��PrpJPowerRuleSchema��¼
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
      DBPrpJPowerRule dbPrpJPowerRule = new DBPrpJPowerRule();
      if (iLimitCount > 0 && dbPrpJPowerRule.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJPowerRule.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpJPowerRule WHERE " + iWherePart; 
        schemas = dbPrpJPowerRule.findByConditions(strSqlStatement, bindValues);
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
      DBPrpJPowerRule dbPrpJPowerRule = new DBPrpJPowerRule();
      if (iLimitCount > 0 && dbPrpJPowerRule.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJPowerRule.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpJPowerRule WHERE " + iWherePart; 
        schemas = dbPrpJPowerRule.findByConditions(dbpool, strSqlStatement, bindValues);
      }
    }
      
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpJPowerRule dbPrpJPowerRule = new DBPrpJPowerRule();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
      dbPrpJPowerRule.setSchema((PrpJPowerRuleSchema)schemas.get(i));
      dbPrpJPowerRule.insert(dbpool);
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
     *@param iD ID
     *@return ��
     */
    public void cancel(DbPool dbpool, String iD) throws Exception
    {
      String strSqlStatement = "";
      
      strSqlStatement = " DELETE FROM PrpJPowerRule WHERE iD='" + iD + "'";
      
      dbpool.delete(strSqlStatement);
    }
      
    /**
     * ����dbpool��ɾ������
     *@param iD ID
     *@return ��
     */
    public void cancel(String iD) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
      try
      {
        dbpool.beginTransaction();
        cancel(dbpool, iD);
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
     *@param iD ID
     *@return ��
     */
    public void getData(String iD) throws Exception
    {
      DbPool dbpool = new DbPool();
      dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
      getData(dbpool, iD);
      dbpool.close();
    }
      
    /**
     * ��dbpool����������ȡ����
     *@param dbpool ���ӳ�
     *@param iD ID
     *@return ��
     */
    public void getData(DbPool dbpool, String iD) throws Exception
    {
       String strWherePart = "";
       strWherePart = "iD='" + iD + "'";
       query(dbpool, strWherePart, 0, null);
    }
    /**
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
