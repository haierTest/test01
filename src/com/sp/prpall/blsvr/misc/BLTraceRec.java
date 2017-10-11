package com.sp.prpall.blsvr.misc;

import java.util.ArrayList;
import java.util.Vector;

import com.sp.prpall.dbsvr.misc.DBTraceRec;
import com.sp.prpall.schema.TraceRecSchema;
import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;

/**
 * ����TraceRec��BL��
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>@createdate 2003-07-17</p>
 * @Author     : X
 * @version 1.0
 */
public class BLTraceRec{
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */       
    public BLTraceRec(){
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
     *����һ��TraceRecSchema��¼
     *@param iTraceRecSchema TraceRecSchema
     *@throws Exception
     */
    public void setArr(TraceRecSchema iTraceRecSchema) throws Exception
    {
       try
       {
         schemas.add(iTraceRecSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *�õ�һ��TraceRecSchema��¼
     *@param index �±�
     *@return һ��TraceRecSchema����
     *@throws Exception
     */
    public TraceRecSchema getArr(int index) throws Exception
    {
     TraceRecSchema traceRecSchema = null;
       try
       {
        traceRecSchema = (TraceRecSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return traceRecSchema;
     }
    /**
     *ɾ��һ��TraceRecSchema��¼
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
      DBTraceRec dbTraceRec = new DBTraceRec();
      if (iLimitCount > 0 && dbTraceRec.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLTraceRec.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM TraceRec WHERE " + iWherePart; 
        schemas = dbTraceRec.findByConditions(strSqlStatement);
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
      DBTraceRec dbTraceRec = new DBTraceRec();
      if (iLimitCount > 0 && dbTraceRec.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLTraceRec.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM TraceRec WHERE " + iWherePart; 
        schemas = dbTraceRec.findByConditions(dbpool,strSqlStatement);
      }
    }
      
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBTraceRec dbTraceRec = new DBTraceRec();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
      dbTraceRec.setSchema((TraceRecSchema)schemas.get(i));
      dbTraceRec.insert(dbpool);
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
    public void cancel(DbPool dbpool,String certino) throws Exception
    {




    	String strSqlStatement = " DELETE FROM TraceRec WHERE certino= ?";
    	dbpool.prepareInnerStatement(strSqlStatement);
    	dbpool.setString(1, certino);
	dbpool.executePreparedUpdate();
	dbpool.closePreparedStatement();
     
    }
      
    /**
     * ����dbpool��ɾ������
     *@param null null
     *@return ��
     */
    public void cancel(String certino ) throws Exception
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
    public void getData(String certino) throws Exception
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
    public void getData(DbPool dbpool,String certino) throws Exception
    {
        
        
        
        
        String strWherePart = " certino= ? ";
        ArrayList arrWhereValue = new ArrayList();
        arrWhereValue.add(certino);
        query(dbpool, strWherePart, arrWhereValue, 0);
        
    }
    
      /**
       *���ղ�ѯ�����ͼ�¼�����Ƶõ�һ���¼�������������¼����schemas����
       *@author yangxiaodong 20100602
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
          DBTraceRec dbTraceRec = new DBTraceRec();
          if (iLimitCount > 0 && dbTraceRec.getCount(dbpool,iWherePart,iWhereValue) > iLimitCount)
          {
              throw new UserException(-98,-1003,"BLTraceRec.query");
          }else
          {
              initArr();
              strSqlStatement = " SELECT * FROM TraceRec WHERE " + iWherePart;
              schemas = dbTraceRec.findByConditions(dbpool,strSqlStatement,iWhereValue);
          }
      }
      
    /**
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
