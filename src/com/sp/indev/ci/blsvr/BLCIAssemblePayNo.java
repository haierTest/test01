package com.sp.indiv.ci.blsvr;

import java.util.ArrayList;
import java.util.Vector;

import com.sp.indiv.ci.dbsvr.DBCIAssemblePayNo;
import com.sp.indiv.ci.schema.CIAssemblePayNoSchema;
import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;

/**
 * ���峵��˰������˰ƾ֤���BL��
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>@createdate 2008-01-21</p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLCIAssemblePayNo{
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */       
    public BLCIAssemblePayNo(){
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
     *����һ��CIAssemblePayNoSchema��¼
     *@param ciAssemblePayNoSchema CIAssemblePayNoSchema
     *@throws Exception
     */
    public void setArr(CIAssemblePayNoSchema ciAssemblePayNoSchema) throws Exception
    {
       try
       {
         schemas.add(ciAssemblePayNoSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *�õ�һ��CIAssemblePayNoSchema��¼
     *@param index �±�
     *@return һ��CIAssemblePayNoSchema����
     *@throws Exception
     */
    public CIAssemblePayNoSchema getArr(int index) throws Exception
    {
    	CIAssemblePayNoSchema ciAssemblePayNoSchema = null;
       try
       {
    	   ciAssemblePayNoSchema = (CIAssemblePayNoSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return ciAssemblePayNoSchema;
     }
    /**
     *ɾ��һ��CIAssemblePayNoSchema��¼
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
      DBCIAssemblePayNo dbCIAssemblePayNo = new DBCIAssemblePayNo();
      if (iLimitCount > 0 && dbCIAssemblePayNo.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLCIAssemblePayNo.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM CIAssemblePayNo WHERE " + iWherePart; 
        schemas = dbCIAssemblePayNo.findByConditions(strSqlStatement);
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
      DBCIAssemblePayNo dbCIAssemblePayNo = new DBCIAssemblePayNo();
      if (iLimitCount > 0 && dbCIAssemblePayNo.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLCIAssemblePayNo.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM CIAssemblePayNo WHERE " + iWherePart; 
        schemas = dbCIAssemblePayNo.findByConditions(dbpool,strSqlStatement);
      }
    }
      
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBCIAssemblePayNo dbCIAssemblePayNo = new DBCIAssemblePayNo();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
    	  dbCIAssemblePayNo.setSchema((CIAssemblePayNoSchema)schemas.get(i));
    	  dbCIAssemblePayNo.insert(dbpool);
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
      
      dbpool.open(SysConfig.getProperty("DDCCDATASOURCE"));
      
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
     *��dbpool��ɾ������
     *@param dbpool    ���ӳ�
     *@param iPolicyno XX��
     *@return ��
     */
    public void cancel(DbPool dbpool,String iPolicyno) throws Exception
    {




    	String strSqlStatement =  " DELETE FROM CIAssemblePayNo WHERE Policyno= ?"; 
    	dbpool.prepareInnerStatement(strSqlStatement);
    	dbpool.setString(1, iPolicyno);
    	dbpool.executePreparedUpdate();
    	dbpool.closePreparedStatement();
     
    }
      
    /**
     * ����dbpool��ɾ������
     *@param iPolicyno XX����
     *@return ��
     */
    public void cancel(String iPolicyno ) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      dbpool.open(SysConfig.getProperty("DDCCDATASOURCE"));
      try
      {
        dbpool.beginTransaction();
        cancel(dbpool,iPolicyno);
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
     * ��dbpool����XX���Ż�ȡ����
     *@param iPolicyno XX��
     *@return ��
     */
    public void getData(String iPolicyno) throws Exception
    {
      DbPool dbpool = new DbPool();
      try {
		dbpool.open(SysConfig.getProperty("DDCCDATASOURCE"));
		getData(dbpool, iPolicyno);
	  } catch (Exception e) {
		
	  }finally{      
	    dbpool.close();
      }
    }
      
    /**
     * ����dbpool����XX���Ż�ȡ����
     *@param dbpool ���ӳ�
     *@param iPolicyno XX��
     *@return ��
     */
    public void getData(DbPool dbpool,String iPolicyno) throws Exception
    {
        
    	
    	
    	
        String strWherePart = " Policyno= ? ";
        ArrayList arrWhereValue = new ArrayList();
        arrWhereValue.add(iPolicyno);
        query(dbpool, strWherePart, arrWhereValue, 0);
        
    }
    /**
     *���ղ�ѯ�����ͼ�¼�����Ƶõ�һ���¼�������������¼����schemas����
     *@author wangchuanzhong 20100602
     *@param dbpool      ȫ�ֳ�
     *@param iWherePart  ��ѯ����,�����������Ѱ󶨱�����ʽ���ʺŸ���������ֵ����һ��
     *@param iWhereValue ��ѯ�������ֶ�ֵ
     *@param iLimitCount ��¼������(iLimitCount=0: ������)
     *@throws UserException
     *@throws Exception
     */
    public void query(DbPool dbpool,String iWherePart,ArrayList iWhereValue, int iLimitCount) throws UserException,Exception
    {
        String strSqlStatement = "";
        DBCIAssemblePayNo dbCIAssemblePayNo = new DBCIAssemblePayNo();
        if (iLimitCount > 0 && dbCIAssemblePayNo.getCount(dbpool,iWherePart,iWhereValue) > iLimitCount)
        {
            throw new UserException(-98,-1003,"BLCIAssemblePayNo.query");
        }else
        {
            initArr();
            strSqlStatement = " SELECT * FROM CIAssemblePayNo WHERE " + iWherePart;
            schemas = dbCIAssemblePayNo.findByConditions(dbpool,strSqlStatement,iWhereValue);
        }
    }
   
    /**
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
