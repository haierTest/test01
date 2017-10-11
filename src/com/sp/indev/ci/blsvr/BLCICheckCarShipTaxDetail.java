package com.sp.indiv.ci.blsvr;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.util.*;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.indiv.ci.dbsvr.DBCICheckCarShipTaxDetail;
import com.sp.indiv.ci.schema.CICheckCarShipTaxDetailSchema;

/**
 * ����CICheckCarShipTaxDetail��BL��
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>@createdate 2008-01-22</p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLCICheckCarShipTaxDetail{
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */       
    public BLCICheckCarShipTaxDetail(){
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
     *����һ��CICheckCarShipTaxDetailSchema��¼
     *@param iCICheckCarShipTaxDetailSchema CICheckCarShipTaxDetailSchema
     *@throws Exception
     */
    public void setArr(CICheckCarShipTaxDetailSchema iCICheckCarShipTaxDetailSchema) throws Exception
    {
       try
       {
         schemas.add(iCICheckCarShipTaxDetailSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *�õ�һ��CICheckCarShipTaxDetailSchema��¼
     *@param index �±�
     *@return һ��CICheckCarShipTaxDetailSchema����
     *@throws Exception
     */
    public CICheckCarShipTaxDetailSchema getArr(int index) throws Exception
    {
     CICheckCarShipTaxDetailSchema cICheckCarShipTaxDetailSchema = null;
       try
       {
        cICheckCarShipTaxDetailSchema = (CICheckCarShipTaxDetailSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return cICheckCarShipTaxDetailSchema;
     }
    /**
     *ɾ��һ��CICheckCarShipTaxDetailSchema��¼
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
      DBCICheckCarShipTaxDetail dbCICheckCarShipTaxDetail = new DBCICheckCarShipTaxDetail();
      if (iLimitCount > 0 && dbCICheckCarShipTaxDetail.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLCICheckCarShipTaxDetail.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM CICheckCarShipTaxDetail WHERE " + iWherePart; 
        schemas = dbCICheckCarShipTaxDetail.findByConditions(strSqlStatement);
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
      DBCICheckCarShipTaxDetail dbCICheckCarShipTaxDetail = new DBCICheckCarShipTaxDetail();
      if (iLimitCount > 0 && dbCICheckCarShipTaxDetail.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLCICheckCarShipTaxDetail.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM CICheckCarShipTaxDetail WHERE " + iWherePart; 
        schemas = dbCICheckCarShipTaxDetail.findByConditions(dbpool,strSqlStatement);
      }
    }
      
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBCICheckCarShipTaxDetail dbCICheckCarShipTaxDetail = new DBCICheckCarShipTaxDetail();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
      dbCICheckCarShipTaxDetail.setSchema((CICheckCarShipTaxDetailSchema)schemas.get(i));
      dbCICheckCarShipTaxDetail.insert(dbpool);
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
