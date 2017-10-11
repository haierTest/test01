package com.sp.prpall.blsvr.misc;

import java.util.ArrayList;
import java.util.Vector;

import com.sp.prpall.dbsvr.misc.DBWorkFlowRec;
import com.sp.prpall.schema.WorkFlowRecSchema;
import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;

public class BLWorkFlowRec{
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */       
    public BLWorkFlowRec(){
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
     *����һ��WorkFlowRecSchema��¼
     *@param iWorkFlowRecSchema WorkFlowRecSchema
     *@throws Exception
     */
    public void setArr(WorkFlowRecSchema iWorkFlowRecSchema) throws Exception
    {
       try
       {
         schemas.add(iWorkFlowRecSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *�õ�һ��WorkFlowRecSchema��¼
     *@param index �±�
     *@return һ��WorkFlowRecSchema����
     *@throws Exception
     */
    public WorkFlowRecSchema getArr(int index) throws Exception
    {
     WorkFlowRecSchema workFlowRecSchema = null;
       try
       {
        workFlowRecSchema = (WorkFlowRecSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return workFlowRecSchema;
     }
    /**
     *ɾ��һ��WorkFlowRecSchema��¼
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
      DBWorkFlowRec dbWorkFlowRec = new DBWorkFlowRec();
      if (iLimitCount > 0 && dbWorkFlowRec.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLWorkFlowRec.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM WorkFlowRec WHERE " + iWherePart; 
        schemas = dbWorkFlowRec.findByConditions(strSqlStatement);
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
      DBWorkFlowRec dbWorkFlowRec = new DBWorkFlowRec();
      if (iLimitCount > 0 && dbWorkFlowRec.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLWorkFlowRec.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM WorkFlowRec WHERE " + iWherePart; 
        schemas = dbWorkFlowRec.findByConditions(dbpool,strSqlStatement);
      }
    }
      
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBWorkFlowRec dbWorkFlowRec = new DBWorkFlowRec();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
      dbWorkFlowRec.setSchema((WorkFlowRecSchema)schemas.get(i));
      dbWorkFlowRec.insert(dbpool);
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
    public void cancel(DbPool dbpool,String businessno) throws Exception
    {




    	String strSqlStatement = " DELETE FROM WorkFlowRec WHERE businessno= ?";
    	dbpool.prepareInnerStatement(strSqlStatement);
    	dbpool.setString(1, businessno);
	dbpool.executePreparedUpdate();
	dbpool.closePreparedStatement();
     
    }
      
    /**
     * ����dbpool��ɾ������
     *@param null null
     *@return ��
     */
    public void cancel(String businessno ) throws Exception
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
    public void getData(String businessno) throws Exception
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
    public void getData(DbPool dbpool,String businessno) throws Exception
    {
        
        
        
        
        String strWherePart = " businessno= ? ";
        ArrayList arrWhereValue = new ArrayList();
        arrWhereValue.add(businessno);
        query(dbpool, strWherePart, arrWhereValue, 0);
        
    }
    
      /**
       *���ղ�ѯ�����ͼ�¼�����Ƶõ�һ���¼�������������¼����schemas����
       *@author gaohaifeng 20100602
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
          DBWorkFlowRec dbWorkFlowRec = new DBWorkFlowRec();
          if (iLimitCount > 0 && dbWorkFlowRec.getCount(dbpool,iWherePart,iWhereValue) > iLimitCount)
          {
              throw new UserException(-98,-1003,"BLWorkFlowRec.query");
          }else
          {
              initArr();
              strSqlStatement = " SELECT * FROM WorkFlowRec WHERE " + iWherePart;
              schemas = dbWorkFlowRec.findByConditions(dbpool,strSqlStatement,iWhereValue);
          }
      }
      
    /**
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
