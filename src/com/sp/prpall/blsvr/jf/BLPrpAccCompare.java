package com.sp.prpall.blsvr.jf;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.util.*;
import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.prpall.dbsvr.jf.DBPrpAccCompare;
import com.sp.prpall.schema.PrpAccCompareSchema;

/**
 * ����ҵ��XXXXX������ձ��BL��
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>@createdate 2004-12-30</p>
 * @author Īչ��
 * @version 1.0
 */
public class BLPrpAccCompare{
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */
    public BLPrpAccCompare(){
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
     *����һ��PrpAccCompareSchema��¼
     *@param iPrpAccCompareSchema PrpAccCompareSchema
     *@throws Exception
     */
    public void setArr(PrpAccCompareSchema iPrpAccCompareSchema) throws Exception
    {
       try
       {
         schemas.add(iPrpAccCompareSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *�õ�һ��PrpAccCompareSchema��¼
     *@param index �±�
     *@return һ��PrpAccCompareSchema����
     *@throws Exception
     */
    public PrpAccCompareSchema getArr(int index) throws Exception
    {
     PrpAccCompareSchema prpAccCompareSchema = null;
       try
       {
        prpAccCompareSchema = (PrpAccCompareSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpAccCompareSchema;
     }
    /**
     *ɾ��һ��PrpAccCompareSchema��¼
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
       this.query(iWherePart,Integer.parseInt(SysConfig.getProperty("QUERY_LIMIT_COUNT").trim()));
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
      DBPrpAccCompare dbPrpAccCompare = new DBPrpAccCompare();
      if (iLimitCount > 0 && dbPrpAccCompare.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpAccCompare.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM AccCompare WHERE " + iWherePart;
        schemas = dbPrpAccCompare.findByConditions(strSqlStatement);
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
       this.query(dbpool,iWherePart,Integer.parseInt(SysConfig.getProperty("QUERY_LIMIT_COUNT").trim()));
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
      DBPrpAccCompare dbPrpAccCompare = new DBPrpAccCompare();
      if (iLimitCount > 0 && dbPrpAccCompare.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpAccCompare.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM AccCompare WHERE " + iWherePart;
        schemas = dbPrpAccCompare.findByConditions(dbpool,strSqlStatement);
      }
    }

    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpAccCompare dbPrpAccCompare = new DBPrpAccCompare();

      int i = 0;

      for(i = 0; i< schemas.size(); i++)
      {
      dbPrpAccCompare.setSchema((PrpAccCompareSchema)schemas.get(i));
      dbPrpAccCompare.insert(dbpool);
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
     *��dbpool��ɾ������
     *@param dbpool    ���ӳ�
     *@param codeType ��������
     *@param businessCode ҵ�����
     *@return ��
     */
    public void cancel(DbPool dbpool,String codeType,String businessCode) throws Exception
    {
      String strSqlStatement = "";

      strSqlStatement = " Delete From AccCompare Where CodeType = " + "'" + codeType + "'" +
                            " And BusinessCode = " + "'" + businessCode + "'";

      dbpool.delete(strSqlStatement);
    }

    /**
     * ����dbpool��ɾ������
     *@param codeType ��������
     *@param businessCode ҵ�����
     *@return ��
     */
    public void cancel(String codeType,String businessCode ) throws Exception
    {
      DbPool dbpool = new DbPool();

        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        dbpool.beginTransaction();
        cancel(dbpool,codeType,businessCode);
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
     * ����dbpool���ݴ������ͣ�ҵ������ȡ����
     *@param codeType ��������
     *@param businessCode ҵ�����
     *@return ��
     */
    public void getData(String codeType,String businessCode) throws Exception
    {
        DbPool dbpool = new DbPool();

  	try {
  		dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
  		getData(dbpool, codeType, businessCode);
  	} catch (Exception e) {
  		
  	}finally {      
  		dbpool.close();
  	}

    }

    /**
     * ��dbpool���ݴ������ͣ�ҵ������ȡ����
     *@param dbpool ���ӳ�
     *@param codeType ��������
     *@param businessCode ҵ�����
     *@return ��
     */
    public void getData(DbPool dbpool,String codeType,String businessCode) throws Exception
    {
    String strWherePart = "";
    strWherePart = "CodeType = " + "'" + codeType + "'" +
                   " And BusinessCode = " + "'" + businessCode + "'";
    query(dbpool,strWherePart,0);
    }

    /**
     * �������
     @Author  Īչ��
     * @param codeType ��������
     * @param businessCode ҵ�����
     * @throw UserException,Exception
     * @return ���ݴ������͡�ҵ����뷵��XXXXX����
     *         ���û����Ӧ��XXXXX�����򷵻�ԭҵ�����
     * @date    20041230
     */
    public String translateCode(String codeType,String businessCode)
      throws UserException,Exception
    {
      if(codeType.equals("")||businessCode.equals("")) return ""; 
      DBPrpAccCompare dbPrpAccCompare = new DBPrpAccCompare();
      dbPrpAccCompare.getInfo(codeType,businessCode);
      if(!(dbPrpAccCompare.getAccCode().equals("")||dbPrpAccCompare.getAccCode()== null))
        return dbPrpAccCompare.getAccCode();
      else
        return businessCode;
    }
    /**
     * �������
     @Author  Īչ��
     * @param businessCode ҵ�����
     * @throw UserException,Exception
     * @return ���ݴ������͡�ҵ����뷵��XXXXX����
     *         ���û����Ӧ��XXXXX�����򷵻�ԭҵ�����
     * @date    20041230
     */
    public String translateBeforQuery(String businessCode) throws UserException,Exception
    {
      String accCode="";
      PrpAccCompareSchema prpAccCompareSchema = null;
      for (int i = 0; i < this.schemas.size(); i++)
      {
        prpAccCompareSchema = (PrpAccCompareSchema)schemas.get(i);
        if(prpAccCompareSchema.getBusinessCode().equals(businessCode))
          accCode = prpAccCompareSchema.getAccCode();
      }
      return accCode;
    }


    /**
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
