package com.sp.indiv.ci.blsvr;

import java.util.Vector;

import com.sp.indiv.ci.dbsvr.DBCIInsureDemandLog;
import com.sp.indiv.ci.schema.CIInsureDemandLogSchema;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;

/**
 * �����-CIInsureDemandLog��BL��
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>CreateDate 2013-05-07</p>
 * @author
 * @version 1.0
 */
public class BLCIInsureDemandLog {
    private Vector schemas = new Vector();

    /**
     * ���캯��
     */
    public BLCIInsureDemandLog() {
    }

    /**
     * ��ʼ����¼
     * 
     * @throws Exception
     */
    public void initArr() throws Exception {
        schemas = new Vector();
    }

    /**
     * ����һ��CIInsureDemandLogSchema��¼
     * @param cIInsureDemandLogSchema Schema��¼
     * @throws Exception
     */
    public void setArr(CIInsureDemandLogSchema cIInsureDemandLogSchema)
            throws Exception {
        schemas.add(cIInsureDemandLogSchema);
    }

    /**
     * �õ�һ��CIInsureDemandLogSchema��¼
     * @param index �±�
     * @return һ��CIInsureDemandLogSchema����
     * @throws Exception
     */
    public CIInsureDemandLogSchema getArr(int index) throws Exception {
        CIInsureDemandLogSchema cIInsureDemandLogSchema = null;
        cIInsureDemandLogSchema = (CIInsureDemandLogSchema) this.schemas.get(index);
        return cIInsureDemandLogSchema;
    }

    /**
     * ɾ��һ��CIInsureDemandLogSchema��¼
     * @param index �±�
     * @throws Exception
     */
    public void remove(int index) throws Exception {
        this.schemas.remove(index);
    }

    /**
     * �õ�schemas��¼��
     * @return schemas��¼��
     * @throws Exception
     */
    public int getSize() throws Exception {
        return this.schemas.size();
    }

    /**
     * �������ӳأ���ѯһ���¼�����������¼����schemas����
     * @param iWherePart ��ѯ����(���������־�)
     * @throws UserException
     * @throws Exception
     */
    public void query(String iWherePart) throws UserException, Exception {
        this.query(iWherePart, Integer.parseInt(SysConst.getProperty(
                "QUERY_LIMIT_COUNT").trim()));
    }

    /**
     * �����ӳأ����ղ�ѯ�����õ�һ���¼�������������¼����schemas����
     * @param dbPool ���ӳ�
     * @param iWherePart ��ѯ����(���������־�)
     * @throws UserException
     * @throws Exception
     */
    public void query(DbPool dbPool, String iWherePart) throws UserException,
            Exception {
        this.query(dbPool, iWherePart, Integer.parseInt(SysConst.getProperty(
                "QUERY_LIMIT_COUNT").trim()));
    }

    /**
     * �������ӳأ���ѯ�õ�һ���¼�����������¼����schemas����
     * @param iWherePart ��ѯ����(���������־�)
     * @param iLimitCount ��¼������(iLimitCount=0: ������)
     * @throws UserException
     * @throws Exception
     */
    public void query(String iWherePart, int iLimitCount) throws UserException,
            Exception {
        String strSqlStatement = "";
        DBCIInsureDemandLog dbBCIInsureDemandLog = new DBCIInsureDemandLog();
        if (iLimitCount > 0 && dbBCIInsureDemandLog.getCount(iWherePart) > iLimitCount) {
            throw new UserException(-98, -1003, "BLCIInsureDemandLog.query");
        } else {
            initArr();
            strSqlStatement = " SELECT * FROM CIInsureDemandLog WHERE " + iWherePart;
            schemas = dbBCIInsureDemandLog.findByConditions(strSqlStatement);
        }
    }

    /**
     * �����ӳأ���ѯ�õ�һ���¼�����������¼����schemas����
     * @param dbPool ���ӳ�
     * @param iWherePart ��ѯ����(���������־�)
     * @param iLimitCount ��¼������(iLimitCount=0: ������)
     * @throws UserException
     * @throws Exception
     */
    public void query(DbPool dbPool, String iWherePart, int iLimitCount)
            throws UserException, Exception {
        String strSqlStatement = "";
        DBCIInsureDemandLog dbBCIInsureDemandLog = new DBCIInsureDemandLog();
        if (iLimitCount > 0 && dbBCIInsureDemandLog.getCount(iWherePart) > iLimitCount) {
            throw new UserException(-98, -1003, "BLCIInsureDemandLog.query");
        } else {
            initArr();
            strSqlStatement = " SELECT * FROM CIInsureDemandLog WHERE " + iWherePart;
            schemas = dbBCIInsureDemandLog.findByConditions(dbPool, strSqlStatement);
        }
    }

    /**
     * �����ӳص�XXXXX�淽��
     * 
     * @param dbPool ���ӳ�
     * @return ��
     */
    public void save(DbPool dbPool) throws Exception {
        DBCIInsureDemandLog dbBCIInsureDemandLog = new DBCIInsureDemandLog();
        int i = 0;
        for (i = 0; i < schemas.size(); i++) {
            dbBCIInsureDemandLog.setSchema((CIInsureDemandLogSchema) schemas.get(i));
            dbBCIInsureDemandLog.insert(dbPool);
        }
    }

    /**
     * �������ӳص�XXXXX�淽��
     */
    public void save() throws Exception {
        DbPool dbPool = new DbPool();
        try {
            dbPool.open("platformNewDataSource");
            dbPool.beginTransaction();
            save(dbPool);
            dbPool.commitTransaction();
        } catch (Exception e) {
            dbPool.rollbackTransaction();
        } finally {
            dbPool.close();
        }
    }

    /**
     * �������ӳظ��ݸ���XXXXX��ѯ�롢��Ż�ȡ����
     * @param iDemandNo ����XXXXX��ѯ��
     * @param iSerialNo ���
     */
    public void getData(String ItemNo) throws Exception {
        DbPool dbPool = new DbPool();
        try {
            dbPool.open("platformNewDataSource");
            getData(dbPool, ItemNo);
        } catch (Exception e) {
        } finally {
            dbPool.close();
        }
    }

    /**
     * �����ӳظ��ݸ���XXXXX��ѯ�롢��Ż�ȡ����
     * 
     * @param dbPool ���ӳ�
     * @param iDemandNo ����XXXXX��ѯ��
     * @param iSerialNo ���
     */
    public void getData(DbPool dbPool, String ItemNo)
            throws Exception {
        String strWherePart = " ItemNo='" + ItemNo + "'";
        query(strWherePart);
    }

    /**
     * �������ӳظ���XXXXX��ѯ�롢��Ÿ�������
     * 
     * @throws Exception
     */
    public void update() throws Exception {
        DBCIInsureDemandLog dbBCIInsureDemandLog = new DBCIInsureDemandLog();
        int i = 0;
        CIInsureDemandLogSchema schema = null;
        for (i = 0; i < schemas.size(); i++) {
            schema = (CIInsureDemandLogSchema) schemas.get(i);
            String strWherePart = " ItemNo='" + schema.getItemNo() + "'";
            query(strWherePart);
            dbBCIInsureDemandLog.setSchema((CIInsureDemandLogSchema) schemas.get(i));
            dbBCIInsureDemandLog.update(strWherePart);
        }
    }

    /**
     * �����ӳظ���XXXXX��ѯ�롢��Ÿ�������
     * 
     * @param dbPool ���ӳ�
     * @throws Exception
     */
    public void update(DbPool dbPool) throws Exception {
        DBCIInsureDemandLog dbBCIInsureDemandLog = new DBCIInsureDemandLog();
        int i = 0;
        CIInsureDemandLogSchema schema = null;
        for (i = 0; i < schemas.size(); i++) {
            schema = (CIInsureDemandLogSchema) schemas.get(i);
            String strWherePart = " ItemNo='" + schema.getItemNo() + "'";
            query(strWherePart);
            dbBCIInsureDemandLog.setSchema((CIInsureDemandLogSchema) schemas.get(i));
            dbBCIInsureDemandLog.update(dbPool, strWherePart);
        }
    }

    /**
     * �����ӳظ���XXXXX��ѯ�롢���ɾ����¼
     * 
     * @param dbPool ���ӳ�
     * @throws Exception
     */
    public void delete(DbPool dbPool) throws Exception {
        DBCIInsureDemandLog dbBCIInsureDemandLog = new DBCIInsureDemandLog();
        int i = 0;
        CIInsureDemandLogSchema schema = null;
        for (i = 0; i < schemas.size(); i++) {
            schema = (CIInsureDemandLogSchema) schemas.get(i);
            String strWherePart = " ItemNo='" + schema.getItemNo() + "'";
            query(strWherePart);
            dbBCIInsureDemandLog.setSchema((CIInsureDemandLogSchema) schemas.get(i));
            dbBCIInsureDemandLog.delete(dbPool, strWherePart);
        }
    }

    /**
     * �������ӳظ������ɾ����¼
     * @throws Exception
     */
    public void delete() throws Exception {
        DBCIInsureDemandLog dbBCIInsureDemandLog = new DBCIInsureDemandLog();
        int i = 0;
        CIInsureDemandLogSchema schema = null;
        for (i = 0; i < schemas.size(); i++) {
            schema = (CIInsureDemandLogSchema) schemas.get(i);
            String strWherePart = " ItemNo='" + schema.getItemNo() + "'";
            query(strWherePart);
            dbBCIInsureDemandLog.setSchema((CIInsureDemandLogSchema) schemas.get(i));
            dbBCIInsureDemandLog.delete(strWherePart);
        }
    }
}
