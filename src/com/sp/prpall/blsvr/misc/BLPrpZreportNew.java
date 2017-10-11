package com.sp.prpall.blsvr.misc;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.util.*;
import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.prpall.dbsvr.misc.DBPrpZreportNew;
import com.sp.prpall.schema.PrpZreportNewSchema;

/**
 * ���屨���¼���BL��
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>@createdate 2003-08-20</p>
 * @Author     : X
 * @version 1.0
 */
public class BLPrpZreportNew{
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */
    public BLPrpZreportNew(){
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
     *����һ��PrpZreportNewSchema��¼
     *@param iPrpZreportNewSchema PrpZreportNewSchema
     *@throws Exception
     */
    public void setArr(PrpZreportNewSchema iPrpZreportNewSchema) throws Exception
    {
       try
       {
         schemas.add(iPrpZreportNewSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    public void setArr(int icurr,PrpZreportNewSchema iprpZreportSchema) throws Exception
    {
       try
       {
         schemas.set(icurr,iprpZreportSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *�õ�һ��PrpZreportNewSchema��¼
     *@param index �±�
     *@return һ��PrpZreportNewSchema����
     *@throws Exception
     */
    public PrpZreportNewSchema getArr(int index) throws Exception
    {
     PrpZreportNewSchema prpZreportSchema = null;
       try
       {
        prpZreportSchema = (PrpZreportNewSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpZreportSchema;
     }
    /**
     *ɾ��һ��PrpZreportNewSchema��¼
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
      DBPrpZreportNew dbPrpZreportNew = new DBPrpZreportNew();
      if (iLimitCount > 0 && dbPrpZreportNew.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpZreportNew.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpZreportNew WHERE " + iWherePart;
        schemas = dbPrpZreportNew.findByConditions(strSqlStatement);
      }
    }
    
    
    public void query1(String iWherePart,int iLimitCount) throws UserException,Exception
    {
      String strSqlStatement = "";
      DBPrpZreportNew dbPrpZreportNew = new DBPrpZreportNew();
      if (iLimitCount > 0 && dbPrpZreportNew.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpZreportNew.query1");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT distinct ReportCode,ComCode,YearMonth FROM PrpZreportNew WHERE " + iWherePart;
        schemas = dbPrpZreportNew.findByConditions1(strSqlStatement);
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
      DBPrpZreportNew dbPrpZreportNew = new DBPrpZreportNew();
      if (iLimitCount > 0 && dbPrpZreportNew.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpZreportNew.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpZreportNew WHERE " + iWherePart;
        schemas = dbPrpZreportNew.findByConditions(dbpool,strSqlStatement);
      }
    }

    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpZreportNew dbPrpZreportNew = new DBPrpZreportNew();

      int i = 0;

      for(i = 0; i< schemas.size(); i++)
      {
      dbPrpZreportNew.setSchema((PrpZreportNewSchema)schemas.get(i));
      dbPrpZreportNew.insert(dbpool);
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
        throw e;
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
    public void cancel(DbPool dbpool,String iReportCode,String iComCode,String iYearMonth,String iRecordType) throws Exception
    {
      String strSqlStatement = "";

      strSqlStatement = " DELETE FROM PrpZreportNew WHERE ReportCode= '" + iReportCode + "' AND ComCode='"
                        + iComCode + "' AND RecordType ='" + iRecordType + "' AND YearMonth ='" + iYearMonth
                        + "'";

      dbpool.delete(strSqlStatement);
    }

    /**
     * ����dbpool��ɾ������
     *@param null null
     *@return ��
     */
    public void cancel(String iReportCode,String iComCode,String iYearMonth,String iRecordType ) throws Exception
    {
      DbPool dbpool = new DbPool();

        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        dbpool.beginTransaction();
        cancel(dbpool,iReportCode,iComCode,iYearMonth,iRecordType);
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

    /*add begin by zhangsl200412-23 ������XXXXX��,ҵ����Դ��������*/
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool,int i) throws Exception
    {
      DBPrpZreportNew dbPrpZreportNew = new DBPrpZreportNew();

      int j = 0;

      for(j = 0; j< schemas.size(); j++)
      {
      dbPrpZreportNew.setSchema((PrpZreportNewSchema)schemas.get(j));
      dbPrpZreportNew.insert(dbpool,i);
      }
    }

    /**
     *����dbpool��XXXXX�淽��
     *@param ��
     *@return ��
     */
    public void save(int i) throws Exception
    {
      DbPool dbpool = new DbPool();

        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        dbpool.beginTransaction();
        save(dbpool,i);
        dbpool.commitTransaction();
      }
      catch (Exception e)
      {
        dbpool.rollbackTransaction();
        throw e;
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
    public void cancel(DbPool dbpool,String iReportCode,String iComCode,String iYearMonth,String iRecordType,String iClassCode,String iBusinessNature) throws Exception
    {
      String strSqlStatement = "";

        strSqlStatement = " DELETE FROM PrpZreportNew WHERE ReportCode= '" + iReportCode + "' AND ComCode='"
                        + iComCode + "' AND RecordType ='" + iRecordType + "' AND YearMonth ='" + iYearMonth
                        + "' and ClassCode = '" + iClassCode +"' and BusinessNature = '" + iBusinessNature +"'";

      dbpool.delete(strSqlStatement);
    }

    /**
     * ����dbpool��ɾ������
     *@param null null
     *@return ��
     */
    public void cancel(String iReportCode,String iComCode,String iYearMonth,String iRecordType,String iClassCode,String iBusinessNature) throws Exception
    {
      DbPool dbpool = new DbPool();

        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        dbpool.beginTransaction();
        cancel(dbpool,iReportCode,iComCode,iYearMonth,iRecordType,iClassCode,iBusinessNature);
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
    public void getData(String iReportCode,String iComCode,String iYearMonth,String iRecordType,String iClassCode,String iBusinessNature,int iRowNo,int iColNo) throws Exception
    {
      DbPool dbpool = new DbPool();

      try {
		dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
		getData(dbpool, iReportCode, iComCode, iYearMonth, iRecordType,
				iRecordType, iClassCode, iRowNo, iColNo);
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
    public void getData(DbPool dbpool,String iReportCode,String iComCode,String iYearMonth,String iRecordType,String iClassCode,String iBusinessNature,int iRowNo,int iColNo) throws Exception
    {
    String strWherePart = "";

      strWherePart = "ReportCode= '" + iReportCode + "' AND ComCode='" + iComCode + "' AND YearMonth = '"
                   + iYearMonth + "' AND RecordType ='" + iRecordType + "' AND ClassCode ='"+ iClassCode +"' AND BusinessNature ='"
                   +iBusinessNature +"' AND RowNo ='" + iRowNo
                   + "' AND ColNo ='" + iColNo + "' ";
    query(dbpool,strWherePart,0);
    }

    /*add end by zhangsl20041223 ����XXXXX��,ҵ����Դ��������*/
    /**
     * ��dbpool����null��ȡ����
     *@param null null
     *@return ��
     */
    public void getData(String iReportCode,String iComCode,String iYearMonth,String iRecordType,int iRowNo,int iColNo) throws Exception
    {
      DbPool dbpool = new DbPool();
        
        try {
      	    dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
            getData(dbpool,iReportCode,iComCode,iYearMonth,iRecordType,iRowNo,iColNo);
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
    public void getData(DbPool dbpool,String iReportCode,String iComCode,String iYearMonth,String iRecordType,int iRowNo,int iColNo) throws Exception
    {
    String strWherePart = "";
    strWherePart = "ReportCode= '" + iReportCode + "' AND ComCode='" + iComCode + "' AND YearMonth = '"
                   + iYearMonth + "' AND RecordType ='" + iRecordType + "' AND RowNo ='" + iRowNo
                   + "' AND ColNo ='" + iColNo + "' ";
    query(dbpool,strWherePart,0);
    }
    /**
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
