package com.sp.indiv.ci.blsvr;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.util.*;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.indiv.ci.dbsvr.DBCIInsureValid;
import com.sp.indiv.ci.schema.CIInsureValidSchema;

/**
 * ����XXȷ������-CIInsureValid��BL��
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>@createdate 2006-06-14</p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLCIInsureValid{
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */       
    public BLCIInsureValid(){
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
     *����һ��CIInsureValidSchema��¼
     *@param iCIInsureValidSchema CIInsureValidSchema
     *@throws Exception
     */
    public void setArr(CIInsureValidSchema iCIInsureValidSchema) throws Exception
    {
       try
       {
         schemas.add(iCIInsureValidSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *�õ�һ��CIInsureValidSchema��¼
     *@param index �±�
     *@return һ��CIInsureValidSchema����
     *@throws Exception
     */
    public CIInsureValidSchema getArr(int index) throws Exception
    {
     CIInsureValidSchema cIInsureValidSchema = null;
       try
       {
        cIInsureValidSchema = (CIInsureValidSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return cIInsureValidSchema;
     }
    /**
     *ɾ��һ��CIInsureValidSchema��¼
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
      DBCIInsureValid dbCIInsureValid = new DBCIInsureValid();
      if (iLimitCount > 0 && dbCIInsureValid.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLCIInsureValid.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM CIInsureValid WHERE " + iWherePart; 
        schemas = dbCIInsureValid.findByConditions(strSqlStatement);
      }
    }
    
    
    /**
     *���ղ�ѯ�����õ�һ���¼�������������¼����schemas����
     *@param iWherePart ��ѯ����(���������־�)
     *@throws UserException
     *@throws Exception
     */
    public void queryHistory(String iWherePart) throws UserException,Exception
    {
       this.queryHistory(iWherePart,Integer.parseInt(SysConst.getProperty("QUERY_LIMIT_COUNT").trim()));
    }
    /**
     *���ղ�ѯ�����ͼ�¼�����Ƶõ�һ���¼�������������¼����schemas����
     *@param iWherePart ��ѯ����(���������־�)
     *@param iLimitCount ��¼������(iLimitCount=0: ������)
     *@throws UserException
     *@throws Exception
     */
    public void queryHistory(String iWherePart,int iLimitCount) throws UserException,Exception
    {
      String strSqlStatement = "";
      DBCIInsureValid dbCIInsureValid = new DBCIInsureValid();
      if (iLimitCount > 0 && dbCIInsureValid.getCountHistory(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLCIInsureValid.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM CIInsureValid WHERE " + iWherePart; 
        schemas = dbCIInsureValid.findByConditionsHistory(strSqlStatement);
      }
    }
        
    /**
     *���ղ�ѯ�����õ�һ���¼�������������¼����schemas����.�ȴ��������ѯ���ٴ���ʷ���ѯ
     *@param iWherePart ��ѯ����(���������־�)
     *@throws UserException
     *@throws Exception
     */
    public void queryCurrentAndHistory(String iWherePart) throws UserException,Exception
    {
       this.query(iWherePart);
       
       if(this.getSize() == 0){
    	   this.queryHistory(iWherePart);
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
      DBCIInsureValid dbCIInsureValid = new DBCIInsureValid();
      if (iLimitCount > 0 && dbCIInsureValid.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLCIInsureValid.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM CIInsureValid WHERE " + iWherePart; 
        schemas = dbCIInsureValid.findByConditions(dbpool,strSqlStatement);
      }
    }
      
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBCIInsureValid dbCIInsureValid = new DBCIInsureValid();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
      dbCIInsureValid.setSchema((CIInsureValidSchema)schemas.get(i));
      dbCIInsureValid.insert(dbpool);
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
	 *��dbpool��update����
	 *@param ��
	 *@return ��
	 */
	public void update(DbPool dbpool) throws Exception {
		DBCIInsureValid dbCIInsureValid = new DBCIInsureValid();

		int i = 0;

		for (i = 0; i < schemas.size(); i++) {
			dbCIInsureValid.setSchema((CIInsureValidSchema) schemas.get(i));
			dbCIInsureValid.update(dbpool);
		}
	}

	/**
	 *����dbpool��update����
	 *@param ��
	 *@return ��
	 */
	public void update() throws Exception {
		DbPool dbpool = new DbPool();

		try {
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			dbpool.beginTransaction();
			update(dbpool);
			dbpool.commitTransaction();
		} catch (Exception e) {
			dbpool.rollbackTransaction();
		} finally {
			dbpool.close();
		}
	}
	
      
    /**
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
