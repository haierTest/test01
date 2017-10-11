package com.sp.indiv.ci.blsvr;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.util.*;

import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.indiv.ci.dbsvr.DBCIEndorValid;
import com.sp.indiv.ci.schema.CIEndorValidSchema;

/**
 * �������Ĳ�ѯȷ�ϱ�-CIEndorValid��BL��
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>@createdate 2006-06-14</p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLCIEndorValid{
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */
    public BLCIEndorValid(){
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
     *����һ��CIEndorValidSchema��¼
     *@param iCIEndorValidSchema CIEndorValidSchema
     *@throws Exception
     */
    public void setArr(CIEndorValidSchema iCIEndorValidSchema) throws Exception
    {
       try
       {
         schemas.add(iCIEndorValidSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *�õ�һ��CIEndorValidSchema��¼
     *@param index �±�
     *@return һ��CIEndorValidSchema����
     *@throws Exception
     */
    public CIEndorValidSchema getArr(int index) throws Exception
    {
     CIEndorValidSchema cIEndorValidSchema = null;
       try
       {
        cIEndorValidSchema = (CIEndorValidSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return cIEndorValidSchema;
     }
    /**
     *ɾ��һ��CIEndorValidSchema��¼
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
     * ����XX�Ż�ȡ����,���ð󶨱�������
     *@param dbpool ���ӳ�
     *@param iPolicyNo XX��
     *@return ��
     */
    public void query(int iLimitCount,String iEndorDemandNo) throws Exception
    {
    	DbPool dbpool = new DbPool();
        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
            String strWherePart = " DemandNo= ? ";
            ArrayList arrWhereValue = new ArrayList();
            arrWhereValue.add(iEndorDemandNo);
            query(dbpool, strWherePart, arrWhereValue, iLimitCount);
        } catch (Exception e) {
        } finally {
            dbpool.close();
        }    
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
        DBCIEndorValid dbCIEndorValid = new DBCIEndorValid();
        if (iLimitCount > 0 && dbCIEndorValid.getCount(dbpool,iWherePart,iWhereValue) > iLimitCount)
        {
            throw new UserException(-98,-1003,"BLCIEndorValid.query");
        }else
        {
            initArr();
            strSqlStatement = " SELECT * FROM CIEndorValid WHERE " + iWherePart;
            schemas = dbCIEndorValid.findByConditions(dbpool,strSqlStatement,iWhereValue);
        }
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
      DBCIEndorValid dbCIEndorValid = new DBCIEndorValid();
      if (iLimitCount > 0 && dbCIEndorValid.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLCIEndorValid.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM CIEndorValid WHERE " + iWherePart;
        schemas = dbCIEndorValid.findByConditions(strSqlStatement);
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
      DBCIEndorValid dbCIEndorValid = new DBCIEndorValid();
      if (iLimitCount > 0 && dbCIEndorValid.getCountHistory(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLCIEndorValid.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM CIEndorValid WHERE " + iWherePart;
        schemas = dbCIEndorValid.findByConditionsHistory(strSqlStatement);
      }
    }
    
    /**
     *���ղ�ѯ�����õ�һ���¼�������������¼����schemas����
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
      DBCIEndorValid dbCIEndorValid = new DBCIEndorValid();
      if (iLimitCount > 0 && dbCIEndorValid.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLCIEndorValid.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM CIEndorValid WHERE " + iWherePart;
        schemas = dbCIEndorValid.findByConditions(dbpool,strSqlStatement);
      }
    }

    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBCIEndorValid dbCIEndorValid = new DBCIEndorValid();

      int i = 0;

      for(i = 0; i< schemas.size(); i++)
      {
      dbCIEndorValid.setSchema((CIEndorValidSchema)schemas.get(i));
      dbCIEndorValid.insert(dbpool);
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
     *@param iEndorseNo ������
     *@return ��
     */
    public void cancel(DbPool dbpool,String iEndorseNo) throws Exception
    {




    	String strSqlStatement = " DELETE FROM CIEndorValid WHERE EndorseNo= ?";
    	dbpool.prepareInnerStatement(strSqlStatement);
    	dbpool.setString(1, iEndorseNo);
    	dbpool.executePreparedUpdate();
    	dbpool.closePreparedStatement();
     
    }

    /**
     * ����dbpool��ɾ������
     *@param iEndorseNo ������
     *@return ��
     */
    public void cancel(String iEndorseNo ) throws Exception
    {
      DbPool dbpool = new DbPool();

        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        dbpool.beginTransaction();
        cancel(dbpool,iEndorseNo);
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
   * ǿ��XXXXX�Ϻ�ƽ̨   add by jinbiao 2006.8.22
   */
   public void setEndorseNo(DbPool dbpool, String iEndorseNo) throws Exception {
   	    
    String strSql = "";
    String strSql2 = "";
    if (this.getSize() == 0)
      return;

    strSql = "UPDATE CIEndorValid SET EndorseNo='" + iEndorseNo + "'" +
        " WHERE DemandNo  = '" + this.getArr(0).getDemandNo() + "' ";
    strSql2 = "UPDATE CIEndorValid SET ValidNo='" + iEndorseNo + "'" +
        " WHERE DemandNo  = '" + this.getArr(0).getDemandNo() + "' ";
     
    try {
      dbpool.update(strSql);
      dbpool.update(strSql2);
      
    }
    
    catch (Exception e) {
      throw e;
    }
           
      
  }

    /**
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
