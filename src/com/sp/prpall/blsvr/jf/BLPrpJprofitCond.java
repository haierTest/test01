package com.sp.prpall.blsvr.jf;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.util.*;
import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.prpall.dbsvr.jf.DBPrpJprofitCond;
import com.sp.prpall.schema.PrpJprofitCondSchema;

/**
 * ����PrpJprofitCond��BL��
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>@createdate 2003-07-17</p>
 * @Author     : X
 * @version 1.0
 */
public class BLPrpJprofitCond{
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */       
    public BLPrpJprofitCond(){
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
     *����һ��PrpJprofitCondSchema��¼
     *@param iPrpJprofitCondSchema PrpJprofitCondSchema
     *@throws Exception
     */
    public void setArr(PrpJprofitCondSchema iPrpJprofitCondSchema) throws Exception
    {
       try
       {
         schemas.add(iPrpJprofitCondSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *�õ�һ��PrpJprofitCondSchema��¼
     *@param index �±�
     *@return һ��PrpJprofitCondSchema����
     *@throws Exception
     */
    public PrpJprofitCondSchema getArr(int index) throws Exception
    {
     PrpJprofitCondSchema prpJprofitCondSchema = null;
       try
       {
        prpJprofitCondSchema = (PrpJprofitCondSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpJprofitCondSchema;
     }
    /**
     *ɾ��һ��PrpJprofitCondSchema��¼
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
      DBPrpJprofitCond dbPrpJprofitCond = new DBPrpJprofitCond();
      if (iLimitCount > 0 && dbPrpJprofitCond.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJprofitCond.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpJprofitCond WHERE " + iWherePart; 
        schemas = dbPrpJprofitCond.findByConditions(strSqlStatement);
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
      DBPrpJprofitCond dbPrpJprofitCond = new DBPrpJprofitCond();
      if (iLimitCount > 0 && dbPrpJprofitCond.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJprofitCond.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpJprofitCond WHERE " + iWherePart; 
        schemas = dbPrpJprofitCond.findByConditions(dbpool,strSqlStatement);
      }
    }
      
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpJprofitCond dbPrpJprofitCond = new DBPrpJprofitCond();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
      dbPrpJprofitCond.setSchema((PrpJprofitCondSchema)schemas.get(i));
      dbPrpJprofitCond.insert(dbpool);
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
     *@param null null
     *@return ��
     */
    public void cancel(DbPool dbpool,String strcertino) throws Exception
    {




    	String strSqlStatement = " DELETE FROM PrpJprofitCond WHERE certino= ?";
    	dbpool.prepareInnerStatement(strSqlStatement);
    	dbpool.setString(1, strcertino);
	dbpool.executePreparedUpdate();
	dbpool.closePreparedStatement();
     
    }
      
    /**
     * ����dbpool��ɾ������
     *@param null null
     *@return ��
     */
    public void cancel(String strcertino ) throws Exception
    {
      DbPool dbpool = new DbPool();
      
        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        dbpool.beginTransaction();
        cancel(dbpool,null);
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
     * ��dbpool����null��ȡ����
     *@param null null
     *@return ��
     */
    public void getData(String strcertino) throws Exception
    {
      DbPool dbpool = new DbPool();
        
      	try {
      		dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
      		getData(dbpool,null);
      	} catch (Exception e) {
      	}
      	finally {
      		dbpool.close();
      	}
    }
      
    /**
     * ����dbpool����null��ȡ����
     *@param dbpool ���ӳ�
     *@param null null
     *@return ��
     */
    public void getData(DbPool dbpool,String strcertino) throws Exception
    {
    String strWherePart = "";
    strWherePart = "null= ' " + null + "'";
    query(dbpool,strWherePart,0);
    }
    /**
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
