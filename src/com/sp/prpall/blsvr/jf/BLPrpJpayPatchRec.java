package com.sp.prpall.blsvr.jf;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.util.*;
import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.prpall.dbsvr.jf.DBPrpJpayPatchRec;
import com.sp.prpall.schema.PrpJpayPatchRecSchema;

/**
 * ����PrpJpayPatchRec��BL��
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>@createdate 2003-07-17</p>
 * @Author     : X
 * @version 1.0
 */
public class BLPrpJpayPatchRec{
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */       
    public BLPrpJpayPatchRec(){
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
     *����һ��PrpJpayPatchRecSchema��¼
     *@param iPrpJpayPatchRecSchema PrpJpayPatchRecSchema
     *@throws Exception
     */
    public void setArr(PrpJpayPatchRecSchema iPrpJpayPatchRecSchema) throws Exception
    {
       try
       {
         schemas.add(iPrpJpayPatchRecSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *�õ�һ��PrpJpayPatchRecSchema��¼
     *@param index �±�
     *@return һ��PrpJpayPatchRecSchema����
     *@throws Exception
     */
    public PrpJpayPatchRecSchema getArr(int index) throws Exception
    {
     PrpJpayPatchRecSchema prpJpayPatchRecSchema = null;
       try
       {
        prpJpayPatchRecSchema = (PrpJpayPatchRecSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpJpayPatchRecSchema;
     }
    /**
     *ɾ��һ��PrpJpayPatchRecSchema��¼
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
      DBPrpJpayPatchRec dbPrpJpayPatchRec = new DBPrpJpayPatchRec();
      if (iLimitCount > 0 && dbPrpJpayPatchRec.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJpayPatchRec.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpJpayPatchRec WHERE " + iWherePart; 
        schemas = dbPrpJpayPatchRec.findByConditions(strSqlStatement);
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
      DBPrpJpayPatchRec dbPrpJpayPatchRec = new DBPrpJpayPatchRec();
      if (iLimitCount > 0 && dbPrpJpayPatchRec.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJpayPatchRec.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpJpayPatchRec WHERE " + iWherePart; 
        schemas = dbPrpJpayPatchRec.findByConditions(dbpool,strSqlStatement);
      }
    }
      
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpJpayPatchRec dbPrpJpayPatchRec = new DBPrpJpayPatchRec();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
      dbPrpJpayPatchRec.setSchema((PrpJpayPatchRecSchema)schemas.get(i));
      dbPrpJpayPatchRec.insert(dbpool);
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




    	String strSqlStatement = " DELETE FROM PrpJpayPatchRec WHERE certino= ?";
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
			getData(dbpool, null);
		} catch (Exception e) {
			
		} finally {
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
