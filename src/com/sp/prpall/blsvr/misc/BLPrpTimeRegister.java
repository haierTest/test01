package com.sp.prpall.blsvr.misc;

import java.util.ArrayList;
import java.util.Vector;

import com.sp.prpall.dbsvr.misc.DBPrpTimeRegister;
import com.sp.prpall.schema.PrpTimeRegisterSchema;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;

/**
 * ����PrpTimeRegister��BL��
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>@createdate 2009-07-14</p>
 * @author Zhouxianli(JavaTools v1.0)
 * @updateauthor yangkun(JavaTools v1.1 - v1.2)
 * @lastversion v1.2
 */
public class BLPrpTimeRegister{
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */       
    public BLPrpTimeRegister(){
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
     *����һ��PrpTimeRegisterSchema��¼
     *@param iPrpTimeRegisterSchema PrpTimeRegisterSchema
     *@throws Exception
     */
    public void setArr(PrpTimeRegisterSchema iPrpTimeRegisterSchema) throws Exception
    {
       try
       {
         schemas.add(iPrpTimeRegisterSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *�õ�һ��PrpTimeRegisterSchema��¼
     *@param index �±�
     *@return һ��PrpTimeRegisterSchema����
     *@throws Exception
     */
    public PrpTimeRegisterSchema getArr(int index) throws Exception
    {
     PrpTimeRegisterSchema prpTimeRegisterSchema = null;
       try
       {
        prpTimeRegisterSchema = (PrpTimeRegisterSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpTimeRegisterSchema;
     }
    /**
     *ɾ��һ��PrpTimeRegisterSchema��¼
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
      DBPrpTimeRegister dbPrpTimeRegister = new DBPrpTimeRegister();
      if (iLimitCount > 0 && dbPrpTimeRegister.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpTimeRegister.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpTimeRegister WHERE " + iWherePart; 
        schemas = dbPrpTimeRegister.findByConditions(strSqlStatement);
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
      DBPrpTimeRegister dbPrpTimeRegister = new DBPrpTimeRegister();
      
      if (iLimitCount > 0 && dbPrpTimeRegister.getCount(dbpool, iWherePart) > iLimitCount)
      
      {
        throw new UserException(-98,-1003,"BLPrpTimeRegister.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpTimeRegister WHERE " + iWherePart; 
        schemas = dbPrpTimeRegister.findByConditions(dbpool,strSqlStatement);
      }
    }
      
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpTimeRegister dbPrpTimeRegister = new DBPrpTimeRegister();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
      dbPrpTimeRegister.setSchema((PrpTimeRegisterSchema)schemas.get(i));
      dbPrpTimeRegister.insert(dbpool);
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
     *��dbpool��ɾ������
     *@param dbpool    ���ӳ�
     *@param iProposalNo ҵ���
     *@return ��
     */
    public void cancel(DbPool dbpool,String iBusinessNo) throws Exception
    {




    	String strSqlStatement = " DELETE FROM PrpTimeRegister WHERE BusinessNo= ?";
    	dbpool.prepareInnerStatement(strSqlStatement);
    	dbpool.setString(1, iBusinessNo);
	dbpool.executePreparedUpdate();
	dbpool.closePreparedStatement();
     
    }
      
    /**
     * ����dbpool��ɾ������
     *@param iProposalNo ҵ���
     *@return ��
     */
    public void cancel(String iBusinessNo ) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
      try
      {
        dbpool.beginTransaction();
        cancel(dbpool,iBusinessNo);
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
     * ����dbpool�ĸ��·���
     *@param iPolicyNo XX��
     *@return ��
     */
    public void update() throws Exception
    {
      DbPool dbpool = new DbPool();
        try {
        dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        dbpool.beginTransaction();
        update(dbpool);
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
      *��dbpool�ĸ��·���
      *@param dbpool    ���ӳ�
      *@param iPolicyNo XX��
      *@return ��
      */
     public void update(DbPool dbpool) throws Exception
     {
    	 DBPrpTimeRegister dbPrpTimeRegister = new DBPrpTimeRegister();

       int i = 0;

       for(i = 0; i< schemas.size(); i++)
       {
    	   dbPrpTimeRegister.setSchema((PrpTimeRegisterSchema)schemas.get(i));
    	   dbPrpTimeRegister.update(dbpool);
        }
     }
    /**
     * ��dbpool����XX���Ż�ȡ����
     *@param iProposalNo ҵ���
     *@return ��
     */
    public void getData(String iBusinessNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
      getData(dbpool,iBusinessNo);
      dbpool.close();
    }
      
    /**
     * ����dbpool����XX���Ż�ȡ����
     *@param dbpool ���ӳ�
     *@param iProposalNo ҵ���
     *@return ��
     */
    public void getData(DbPool dbpool,String iBusinessNo) throws Exception
    {
        
        
        
        
        String strWherePart = " BusinessNo= ? ";
        ArrayList arrWhereValue = new ArrayList();
        arrWhereValue.add(iBusinessNo);
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
        DBPrpTimeRegister dbPrpTimeRegister = new DBPrpTimeRegister();
        if (iLimitCount > 0 && dbPrpTimeRegister.getCount(dbpool,iWherePart,iWhereValue) > iLimitCount)
        {
            throw new UserException(-98,-1003,"BLPrpTimeRegister.query");
        }else
        {
            initArr();
            strSqlStatement = " SELECT * FROM PrpTimeRegister WHERE " + iWherePart;
            schemas = dbPrpTimeRegister.findByConditions(dbpool,strSqlStatement,iWhereValue);
        }
    }
    
    /**
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
