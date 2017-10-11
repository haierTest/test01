package com.sp.prpall.blsvr.jf;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.util.*;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.prpall.dbsvr.jf.DBPrpJpoaMain;
import com.sp.prpall.dbsvr.jf.DBPrpJpoaTrace;
import com.sp.prpall.schema.PrpJpoaTraceSchema;


/**
 * @version 1.0
 */
public class BLPrpJpoaTrace{
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */       
    public BLPrpJpoaTrace(){
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
     *����һ��PrpjPoaMainSchema��¼
     *@param PrpjPoaMainSchema PrpjPoaMainSchema
     *@throws Exception
     */
    public void setArr(PrpJpoaTraceSchema prpJpoaTraceSchema) throws Exception
    {
       try
       {
         schemas.add(prpJpoaTraceSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *�õ�һ��PrpJpoaMainSchema��¼
     *@param index �±�
     *@return һ��PrpJpoaMainSchema����
     *@throws Exception
     */
    public PrpJpoaTraceSchema getArr(int index) throws Exception
    {
    	PrpJpoaTraceSchema prpJpoaTraceSchema = null;
       try
       {
    	   prpJpoaTraceSchema = (PrpJpoaTraceSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpJpoaTraceSchema;
     }
    /**
     *ɾ��һ��PrpJcommAllocSchema��¼
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
      DBPrpJpoaTrace dbPrpjPoaTrace = new DBPrpJpoaTrace();
      if (iLimitCount > 0 && dbPrpjPoaTrace.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJpoaTrace.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpJpoaTrace WHERE " + iWherePart;
        schemas = dbPrpjPoaTrace.findByConditions(strSqlStatement);
      }
    }
    /**
     * �õ���¼��
     * @param condition
     * @return
     * @throws Exception
     */
    public int getCount(String condition) throws Exception
    {
    	DBPrpJpoaTrace dbPrpJpoaTrace = new DBPrpJpoaTrace();
        return dbPrpJpoaTrace.getCount(condition);
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
      DBPrpJpoaTrace dbPrpjPoaTrace = new DBPrpJpoaTrace();
      if (iLimitCount > 0 && dbPrpjPoaTrace.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJpoatrace.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpJpoaTrace WHERE " + iWherePart; 
        schemas = dbPrpjPoaTrace.findByConditions(dbpool,strSqlStatement);
      }
    }
      
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
    	DBPrpJpoaTrace dbPrpjPoaTrace = new DBPrpJpoaTrace();
      
      int i = 0;
      
      
      for(i = 0; i< schemas.size(); i++)
      {
    	  dbPrpjPoaTrace.setSchema((PrpJpoaTraceSchema)schemas.get(i));
    	  dbPrpjPoaTrace.insert(dbpool);
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
    public void cancel(DbPool dbpool,String PoaCode) throws Exception
    {




    	String strSqlStatement = " DELETE FROM PrpJpoaTrace WHERE PoaCode= ?";
    	dbpool.prepareInnerStatement(strSqlStatement);
    	dbpool.setString(1, PoaCode);
	dbpool.executePreparedUpdate();
	dbpool.closePreparedStatement();
     
    }
      
    /**
     * ����dbpool��ɾ������
     *@param null null
     *@return ��
     */
   public void cancel(String PoaCode ) throws Exception
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
    public void getData(String PoaCode) throws Exception
    {
        DbPool dbpool = new DbPool();

  	try {
  		dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
  		getData(dbpool, null);
  	} catch (Exception e) {
  		
  	}finally {
  		dbpool.close();
  	}

    }
      
    /**
     * ����dbpool����null��ȡ����
     *@param dbpool ���ӳ�
     *@param null null
     *@return ��
     */
    public void getData(DbPool dbpool,String PoaCode) throws Exception
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
