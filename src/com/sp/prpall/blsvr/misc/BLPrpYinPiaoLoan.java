package com.sp.prpall.blsvr.misc;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.util.*;

import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.prpall.dbsvr.misc.DBPrpYinPiaoLoan;
import com.sp.prpall.dbsvr.tb.DBPrpTmain;
import com.sp.prpall.schema.PrpTmainSchema;
import com.sp.prpall.schema.PrpYinPiaoLoanSchema;

/**
 * ������Ʊ��ѺXXXXX֤XX��Ϣ���BL��
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>@createdate 2015-06-16</p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLPrpYinPiaoLoan{
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */       
    public BLPrpYinPiaoLoan(){
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
     *����һ��PrpYinPiaoLoanSchema��¼
     *@param iPrpYinPiaoLoanSchema PrpYinPiaoLoanSchema
     *@throws Exception
     */
    public void setArr(PrpYinPiaoLoanSchema iPrpYinPiaoLoanSchema) throws Exception
    {
       try
       {
         schemas.add(iPrpYinPiaoLoanSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *�õ�һ��PrpYinPiaoLoanSchema��¼
     *@param index �±�
     *@return һ��PrpYinPiaoLoanSchema����
     *@throws Exception
     */
    public PrpYinPiaoLoanSchema getArr(int index) throws Exception
    {
     PrpYinPiaoLoanSchema prpYinPiaoLoanSchema = null;
       try
       {
        prpYinPiaoLoanSchema = (PrpYinPiaoLoanSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpYinPiaoLoanSchema;
     }
    /**
     *ɾ��һ��PrpYinPiaoLoanSchema��¼
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
       this.query(iWherePart, Integer.parseInt(SysConst.getProperty("QUERY_LIMIT_COUNT").trim()));
    }
    /**
     *���ղ�ѯ�����ͼ�¼�����Ƶõ�һ���¼�������������¼����schemas����
     *@param iWherePart ��ѯ����(���������־�)
     *@param iLimitCount ��¼������(iLimitCount=0: ������)
     *@throws UserException
     *@throws Exception
     */
    public void query(String iWherePart, int iLimitCount) throws UserException,Exception
    {
      String strSqlStatement = "";
      DBPrpYinPiaoLoan dbPrpYinPiaoLoan = new DBPrpYinPiaoLoan();
      if (iLimitCount > 0 && dbPrpYinPiaoLoan.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpYinPiaoLoan.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpYinPiaoLoan WHERE " + iWherePart; 
        schemas = dbPrpYinPiaoLoan.findByConditions(strSqlStatement);
      }
    }
    /**
     *���ղ�ѯ�����õ�һ���¼�������������¼����schemas����
     *@param dbpool ȫ�ֳ�
     *@param iWherePart ��ѯ����(���������־�)
     *@throws UserException
     *@throws Exception
     */
    public void query(DbPool dbpool, String iWherePart) throws UserException,Exception
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
    public void query(DbPool dbpool, String iWherePart, int iLimitCount) throws UserException,Exception
    {
      String strSqlStatement = "";
      DBPrpYinPiaoLoan dbPrpYinPiaoLoan = new DBPrpYinPiaoLoan();
      if (iLimitCount > 0 && dbPrpYinPiaoLoan.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpYinPiaoLoan.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpYinPiaoLoan WHERE " + iWherePart; 
        schemas = dbPrpYinPiaoLoan.findByConditions(dbpool, strSqlStatement);
      }
    }
      
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpYinPiaoLoan dbPrpYinPiaoLoan = new DBPrpYinPiaoLoan();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
      dbPrpYinPiaoLoan.setSchema((PrpYinPiaoLoanSchema)schemas.get(i));
      dbPrpYinPiaoLoan.insert(dbpool);
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
    	dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
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
     *��dbpool��update����
     *@param ��
     *@return ��
     */
    public void update(DbPool dbpool) throws Exception
    {
      DBPrpYinPiaoLoan dbPrpYinPiaoLoan= new DBPrpYinPiaoLoan();

      int i = 0;

      for(i = 0; i< schemas.size(); i++)
      {
    	dbPrpYinPiaoLoan.setSchema((PrpYinPiaoLoanSchema)schemas.get(i));
    	dbPrpYinPiaoLoan.update(dbpool);
      }
    }
    /**
     *����dbpool�ĸ��·���
     *@param ��
     *@return ��
     */
    public void update() throws Exception
    {
      DbPool dbpool = new DbPool();

      dbpool.open(SysConfig.getProperty("DDCCDATASOURCE"));

      try
      {
        dbpool.beginTransaction();
        update(dbpool);
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
     *@param insuranceNo XX������
     *@return ��
     */
    public void cancel(DbPool dbpool, String insuranceNo) throws Exception
    {
      String strSqlStatement = "";
      
      strSqlStatement = " DELETE FROM PrpYinPiaoLoan WHERE insuranceNo='" + insuranceNo + "'";
      
      dbpool.delete(strSqlStatement);
    }
      
    /**
     * ����dbpool��ɾ������
     *@param insuranceNo XX������
     *@return ��
     */
    public void cancel(String insuranceNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      try
      {
      	dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        dbpool.beginTransaction();
        cancel(dbpool, insuranceNo);
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
     *@param insuranceNo XX������
     *@return ��
     */
    public void getData(String insuranceNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      try {
      	dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
    	getData(dbpool, insuranceNo);
		
	  } catch (Exception e) {
	  }finally{
		  dbpool.close();
	  }
    }
      
    /**
     * ��dbpool����������ȡ����
     *@param dbpool ���ӳ�
     *@param insuranceNo XX������
     *@return ��
     */
    public void getData(DbPool dbpool, String insuranceNo) throws Exception
    {
       String strWherePart = "";
       strWherePart = "insuranceNo='" + insuranceNo + "'";
       query(dbpool, strWherePart, 0);
    }
    /**
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
