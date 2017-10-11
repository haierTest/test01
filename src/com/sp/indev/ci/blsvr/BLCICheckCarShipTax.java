package com.sp.indiv.ci.blsvr;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.util.*;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.indiv.ci.dbsvr.DBCICheckCarShipTax;
import com.sp.indiv.ci.schema.CICheckCarShipTaxSchema;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * ����CICheckCarShipTax��BL��
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>@createdate 2008-01-22</p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLCICheckCarShipTax{
    private Vector schemas = new Vector();
    Log logger = LogFactory.getLog(getClass());
    /**
     * ���캯��
     */       
    public BLCICheckCarShipTax(){
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
     *����һ��CICheckCarShipTaxSchema��¼
     *@param iCICheckCarShipTaxSchema CICheckCarShipTaxSchema
     *@throws Exception
     */
    public void setArr(CICheckCarShipTaxSchema iCICheckCarShipTaxSchema) throws Exception
    {
       try
       {
         schemas.add(iCICheckCarShipTaxSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *�õ�һ��CICheckCarShipTaxSchema��¼
     *@param index �±�
     *@return һ��CICheckCarShipTaxSchema����
     *@throws Exception
     */
    public CICheckCarShipTaxSchema getArr(int index) throws Exception
    {
     CICheckCarShipTaxSchema cICheckCarShipTaxSchema = null;
       try
       {
        cICheckCarShipTaxSchema = (CICheckCarShipTaxSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return cICheckCarShipTaxSchema;
     }
    /**
     *ɾ��һ��CICheckCarShipTaxSchema��¼
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
      DBCICheckCarShipTax dbCICheckCarShipTax = new DBCICheckCarShipTax();
      if (iLimitCount > 0 && dbCICheckCarShipTax.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLCICheckCarShipTax.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM CICheckCarShipTax WHERE " + iWherePart; 
        schemas = dbCICheckCarShipTax.findByConditions(strSqlStatement);
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
      DBCICheckCarShipTax dbCICheckCarShipTax = new DBCICheckCarShipTax();
      if (iLimitCount > 0 && dbCICheckCarShipTax.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLCICheckCarShipTax.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM CICheckCarShipTax WHERE " + iWherePart; 
        schemas = dbCICheckCarShipTax.findByConditions(dbpool,strSqlStatement);
      }
    }
      
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBCICheckCarShipTax dbCICheckCarShipTax = new DBCICheckCarShipTax();
      
      int i = 0;
      
      logger.info("schemas.size()="+schemas.size());
      
      for(i = 0; i< schemas.size(); i++)
      {
      dbCICheckCarShipTax.setSchema((CICheckCarShipTaxSchema)schemas.get(i));
      dbCICheckCarShipTax.insert(dbpool);
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
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
