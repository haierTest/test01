package com.sp.prpall.blsvr.jf;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.util.*;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.prpall.dbsvr.jf.DBPrpJmanageFeePackage;
import com.sp.prpall.schema.PrpJmanageFeePackageSchema;

/**
 * ���������ô�����BL��
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>@createdate 2007-01-31</p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLPrpJmanageFeePackage{
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */       
    public BLPrpJmanageFeePackage(){
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
     *����һ��PrpJmanageFeePackageSchema��¼
     *@param iPrpJmanageFeePackageSchema PrpJmanageFeePackageSchema
     *@throws Exception
     */
    public void setArr(PrpJmanageFeePackageSchema iPrpJmanageFeePackageSchema) throws Exception
    {
       try
       {
         schemas.add(iPrpJmanageFeePackageSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *�õ�һ��PrpJmanageFeePackageSchema��¼
     *@param index �±�
     *@return һ��PrpJmanageFeePackageSchema����
     *@throws Exception
     */
    public PrpJmanageFeePackageSchema getArr(int index) throws Exception
    {
     PrpJmanageFeePackageSchema prpJmanageFeePackageSchema = null;
       try
       {
        prpJmanageFeePackageSchema = (PrpJmanageFeePackageSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpJmanageFeePackageSchema;
     }
    /**
     *ɾ��һ��PrpJmanageFeePackageSchema��¼
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
      DBPrpJmanageFeePackage dbPrpJmanageFeePackage = new DBPrpJmanageFeePackage();
      if (iLimitCount > 0 && dbPrpJmanageFeePackage.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJmanageFeePackage.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpJmanageFeePackage WHERE " + iWherePart; 
        schemas = dbPrpJmanageFeePackage.findByConditions(strSqlStatement);
      }
    }
    
    /**
     *���ղ�ѯ�����ͼ�¼�����Ƶõ�һ���¼�������������¼����schemas����
     *@param iWherePart ��ѯ����(���������־�)
     *@throws UserException
     *@throws Exception
     */
    public void queryByLimitCount(String iWherePart) throws UserException,Exception
    {
    	int iLimitCount = Integer.parseInt(SysConst.getProperty("QUERY_LIMIT_COUNT").trim());
    	String strSqlStatement = "";
    	DBPrpJmanageFeePackage dbPrpJmanageFeePackage = new DBPrpJmanageFeePackage();
    	initArr();
    	String sql = " SELECT * FROM PrpJmanageFeePackage WHERE " + iWherePart;
    	strSqlStatement = " SELECT * FROM (" + sql + ") WHERE ROWNUM<=" + iLimitCount;
    	schemas = dbPrpJmanageFeePackage.findByConditions(strSqlStatement);
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
      DBPrpJmanageFeePackage dbPrpJmanageFeePackage = new DBPrpJmanageFeePackage();
      if (iLimitCount > 0 && dbPrpJmanageFeePackage.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJmanageFeePackage.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpJmanageFeePackage WHERE " + iWherePart; 
        schemas = dbPrpJmanageFeePackage.findByConditions(dbpool,strSqlStatement);
      }
    }
      
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpJmanageFeePackage dbPrpJmanageFeePackage = new DBPrpJmanageFeePackage();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
      dbPrpJmanageFeePackage.setSchema((PrpJmanageFeePackageSchema)schemas.get(i));
      dbPrpJmanageFeePackage.insert(dbpool);
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
