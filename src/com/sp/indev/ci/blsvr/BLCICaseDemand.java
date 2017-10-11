package com.sp.indiv.ci.blsvr;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.util.*;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.indiv.ci.dbsvr.DBCICaseDemand;
import com.sp.indiv.ci.schema.CICaseDemandSchema;

/**
 * ����᰸���ݽ�����Ϣ��-CICaseDemand��BL��
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>@createdate 2006-06-14</p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLCICaseDemand{
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */       
    public BLCICaseDemand(){
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
     *����һ��CICaseDemandSchema��¼
     *@param iCICaseDemandSchema CICaseDemandSchema
     *@throws Exception
     */
    public void setArr(CICaseDemandSchema iCICaseDemandSchema) throws Exception
    {
       try
       {
         schemas.add(iCICaseDemandSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *�õ�һ��CICaseDemandSchema��¼
     *@param index �±�
     *@return һ��CICaseDemandSchema����
     *@throws Exception
     */
    public CICaseDemandSchema getArr(int index) throws Exception
    {
     CICaseDemandSchema cICaseDemandSchema = null;
       try
       {
        cICaseDemandSchema = (CICaseDemandSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return cICaseDemandSchema;
     }
    /**
     *ɾ��һ��CICaseDemandSchema��¼
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
      DBCICaseDemand dbCICaseDemand = new DBCICaseDemand();
      if (iLimitCount > 0 && dbCICaseDemand.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLCICaseDemand.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM CICaseDemand WHERE " + iWherePart; 
        schemas = dbCICaseDemand.findByConditions(strSqlStatement);
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
      DBCICaseDemand dbCICaseDemand = new DBCICaseDemand();
      if (iLimitCount > 0 && dbCICaseDemand.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLCICaseDemand.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM CICaseDemand WHERE " + iWherePart; 
        schemas = dbCICaseDemand.findByConditions(dbpool,strSqlStatement);
      }
    }
      
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBCICaseDemand dbCICaseDemand = new DBCICaseDemand();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
      dbCICaseDemand.setSchema((CICaseDemandSchema)schemas.get(i));
      dbCICaseDemand.insert(dbpool);
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
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
