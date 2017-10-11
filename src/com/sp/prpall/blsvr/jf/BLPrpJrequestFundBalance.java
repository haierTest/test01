package com.sp.prpall.blsvr.jf;

import java.util.*;

import com.sp.utility.*;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.prpall.dbsvr.jf.DBPrpJrequestFundBalance;
import com.sp.prpall.schema.PrpJrequestFundBalanceSchema;
import org.apache.commons.logging.Log; 
import org.apache.commons.logging.LogFactory;

/**
 * ����BLPrpJrequestFundBalance��BL��
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>@createdate 2008-10-23</p>
 * @author zhanglingjian
 * @version 1.0
 */
public class BLPrpJrequestFundBalance{
    private Vector schemas = new Vector();
    protected final Log logger = LogFactory.getLog(getClass());

    /**
     * ���캯��
     */
    public BLPrpJrequestFundBalance(){
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
     *����һ��PrpJrequestFundBalanceSchema��¼
     *@param PrpJrequestFundBalanceSchema PrpJrequestFundBalanceSchema
     *@throws Exception
     */
    public void setArr(PrpJrequestFundBalanceSchema iPrpJrequestFundBalanceSchema) throws Exception
    {
      try
      {
        schemas.add(iPrpJrequestFundBalanceSchema);
      }
      catch(Exception e)
      {
        throw e;
      }
    }

    /**
     *�õ�һ��PrpJrequestFundBalanceSchema��¼
     *@param index �±�
     *@return һ��PrpJrequestFundBalanceSchema����
     *@throws Exception
     */
    public PrpJrequestFundBalanceSchema getArr(int index) throws Exception
    {
    	PrpJrequestFundBalanceSchema prpJrequestFundBalanceSchema = null;
      try
      {
    	  prpJrequestFundBalanceSchema = (PrpJrequestFundBalanceSchema)this.schemas.get(index);
      }
      catch(Exception e)
      {
        throw e;
      }
      return prpJrequestFundBalanceSchema;
    }
    
    /**
     *ɾ��һ��PrpJrequestFundBalanceSchema��¼
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
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpJrequestFundBalance dbPrpJrequestFundBalance = new DBPrpJrequestFundBalance();

      int i = 0;

      for(i = 0; i< schemas.size(); i++)
      {
    	  dbPrpJrequestFundBalance.setSchema((PrpJrequestFundBalanceSchema)schemas.get(i));
    	  dbPrpJrequestFundBalance.insert(dbpool);
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
      DBPrpJrequestFundBalance dbPrpJrequestFundBalance = new DBPrpJrequestFundBalance();
      if (iLimitCount > 0 && dbPrpJrequestFundBalance.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJrequestFundBalance.query");
      }else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpJrequestFundBalance WHERE " + iWherePart;
        schemas = dbPrpJrequestFundBalance.findByConditions(strSqlStatement);
      }
    }
    
    /**
     *���ղ�ѯ�����õ�һ���¼�������������¼����schemas����
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
     *@param iWherePart ��ѯ����(���������־�)
     *@param iLimitCount ��¼������(iLimitCount=0: ������)
     *@throws UserException
     *@throws Exception
     */
    public void query(DbPool dbpool,String iWherePart,int iLimitCount) throws UserException,Exception
    {
      String strSqlStatement = "";
      DBPrpJrequestFundBalance dbPrpJrequestFundBalance = new DBPrpJrequestFundBalance();
      if (iLimitCount > 0 && dbPrpJrequestFundBalance.getCount(dbpool,iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJrequestFundBalance.query");
      }else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpJrequestFundBalance WHERE " + iWherePart;
        schemas = dbPrpJrequestFundBalance.findByConditions(dbpool,strSqlStatement);
      }
    }
    
    /**
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
