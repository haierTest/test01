package com.sp.prpall.blsvr.jf;

import java.util.ArrayList;
import java.util.Vector;

import com.sp.prpall.dbsvr.jf.DBUtiPrintConfig;
import com.sp.prpall.schema.UtiPrintConfigSchema;
import com.sp.utiall.dbsvr.DBPrpDcompany;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;

/**
 * ����UtiPrintConfig��BL��
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>@createdate 2013-04-18</p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLUtiPrintConfig {
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */       
    public BLUtiPrintConfig() {
    }

    /**
     *��ʼ����¼
     *@param ��
     *@return ��
     *@throws Exception
     */
    public void initArr() throws Exception {
        schemas = new Vector();
    }
    /**
     *����һ��UtiPrintConfigSchema��¼
     *@param iUtiPrintConfigSchema UtiPrintConfigSchema
     *@throws Exception
     */
    public void setArr(UtiPrintConfigSchema iUtiPrintConfigSchema) throws Exception {
        try {
            schemas.add(iUtiPrintConfigSchema);
        } catch(Exception e) {
            throw e;
        }
    }
    /**
     *�õ�һ��UtiPrintConfigSchema��¼
     *@param index �±�
     *@return һ��UtiPrintConfigSchema����
     *@throws Exception
     */
    public UtiPrintConfigSchema getArr(int index) throws Exception {
        UtiPrintConfigSchema utiPrintConfigSchema = null;
        try {
            utiPrintConfigSchema = (UtiPrintConfigSchema)this.schemas.get(index);
        } catch(Exception e) {
            throw e;
        }
        return utiPrintConfigSchema;
    }
    /**
     *ɾ��һ��UtiPrintConfigSchema��¼
     *@param index �±�
     *@throws Exception
     */
    public void remove(int index) throws Exception {
        try {
            this.schemas.remove(index);
        } catch(Exception e) {
            throw e;
        }
    }
    /**
     *�õ�schemas��¼��
     *@return schemas��¼��
     *@throws Exception
     */
    public int getSize() throws Exception {
        return this.schemas.size();
    }
    /**
     *���ղ�ѯ�����õ�һ���¼�������������¼����schemas����
     *@param iWherePart ��ѯ����(���������־�)
     *@param bindValues �󶨲���
     *@throws UserException
     *@throws Exception
     */
    public void query(String iWherePart, ArrayList bindValues) throws UserException,Exception {
        this.query(iWherePart, Integer.parseInt(SysConst.getProperty("QUERY_LIMIT_COUNT").trim()), bindValues);
    }
    /**
     *���ղ�ѯ�����ͼ�¼�����Ƶõ�һ���¼�������������¼����schemas����
     *@param iWherePart ��ѯ����(���������־�)
     *@param iLimitCount ��¼������(iLimitCount=0: ������)
     *@param bindValues �󶨲���
     *@throws UserException
     *@throws Exception
     */
    public void query(String iWherePart, int iLimitCount, ArrayList bindValues) throws UserException,Exception {
        String strSqlStatement = "";
        DBUtiPrintConfig dbUtiPrintConfig = new DBUtiPrintConfig();
        if (iLimitCount > 0 && dbUtiPrintConfig.getCount(iWherePart) > iLimitCount) {
            throw new UserException(-98,-1003,"BLUtiPrintConfig.query");
        } else {
            initArr();
            strSqlStatement = " SELECT * FROM UtiPrintConfig WHERE " + iWherePart; 
            schemas = dbUtiPrintConfig.findByConditions(strSqlStatement, bindValues);
        }
    }
    /**
     *���ղ�ѯ�����õ�һ���¼�������������¼����schemas����
     *@param dbpool ȫ�ֳ�
     *@param iWherePart ��ѯ����(���������־�)
     *@param bindValues �󶨲���
     *@throws UserException
     *@throws Exception
     */
    public void query(DbPool dbpool, String iWherePart, ArrayList bindValues) throws UserException,Exception {
        this.query(dbpool,iWherePart,Integer.parseInt(SysConst.getProperty("QUERY_LIMIT_COUNT").trim()),bindValues);
    }
    /**
     *���ղ�ѯ�����ͼ�¼�����Ƶõ�һ���¼�������������¼����schemas����
     *@param dbpool ȫ�ֳ�
     *@param iWherePart ��ѯ����(���������־�)
     *@param iLimitCount ��¼������(iLimitCount=0: ������)
     *@param bindValues �󶨲���
     *@throws UserException
     *@throws Exception
     */
    public void query(DbPool dbpool, String iWherePart, int iLimitCount, ArrayList bindValues) throws UserException,Exception {
        String strSqlStatement = "";
        DBUtiPrintConfig dbUtiPrintConfig = new DBUtiPrintConfig();
        if (iLimitCount > 0 && dbUtiPrintConfig.getCount(iWherePart) > iLimitCount) {
            throw new UserException(-98,-1003,"BLUtiPrintConfig.query");
        } else {
            initArr();
            strSqlStatement = " SELECT * FROM UtiPrintConfig WHERE " + iWherePart; 
            schemas = dbUtiPrintConfig.findByConditions(dbpool, strSqlStatement, bindValues);
        }
    }
      
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception {
        DBUtiPrintConfig dbUtiPrintConfig = new DBUtiPrintConfig();
        int i = 0;
        for(i = 0; i< schemas.size(); i++) {
            dbUtiPrintConfig.setSchema((UtiPrintConfigSchema)schemas.get(i));
            dbUtiPrintConfig.insert(dbpool);
        }
    }
      
    /**
     *����dbpool��XXXXX�淽��
     *@param ��
     *@return ��
     */
    public void save() throws Exception {
        DbPool dbpool = new DbPool();
        dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
        try {
            dbpool.beginTransaction();
            save(dbpool);
            dbpool.commitTransaction(); 
        } catch (Exception e) {
            dbpool.rollbackTransaction();
        } finally {
            dbpool.close();
        }
    }
      
    /**
     * ��dbpool��ɾ������
     *@param dbpool    ���ӳ�
     *@param systemCode SystemCode
     *@return ��
     */
    public void cancel(DbPool dbpool, String systemCode) throws Exception {
        String strSqlStatement = "";
        strSqlStatement = " DELETE FROM UtiPrintConfig WHERE systemCode='" + systemCode + "'";
        dbpool.delete(strSqlStatement);
    }
      
    /**
     * ����dbpool��ɾ������
     *@param systemCode SystemCode
     *@return ��
     */
    public void cancel(String systemCode) throws Exception {
        DbPool dbpool = new DbPool();
        dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
        try {
            dbpool.beginTransaction();
            cancel(dbpool, systemCode);
            dbpool.commitTransaction(); 
        } catch (Exception e) {
            dbpool.rollbackTransaction();
        } finally {
            dbpool.close();
        }
    }
      
    /**
     * ����dbpool����������ȡ����
     *@param systemCode SystemCode
     *@return ��
     */
    public void getData(String systemCode) throws Exception {
        DbPool dbpool = new DbPool();
        dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
        getData(dbpool, systemCode);
        dbpool.close();
    }
      
    /**
     * ��dbpool����������ȡ����
     *@param dbpool ���ӳ�
     *@param systemCode SystemCode
     *@return ��
     */
    public void getData(DbPool dbpool, String systemCode) throws Exception {
        String strWherePart = "";
        strWherePart = "systemCode='" + systemCode + "'";
        query(dbpool, strWherePart, 0, null);
    }
    
    public String getConfigValueByComCode(String systemCode, String comCode,
            String configCode) throws Exception {
        String configValue = "";
        DBUtiPrintConfig dbUtiPrintConfig = new DBUtiPrintConfig();
        while (true) {
        	int intResult = dbUtiPrintConfig.getInfo(systemCode, comCode, configCode);
            if (intResult == 0) {
                configValue = dbUtiPrintConfig.getConfigvalue();
                break;
            } else {
                if("00000000".equals(comCode)){
                    throw new UserException(-98,-1200,"BLUtiPrintConfig.getConfigValueByComCode()","��ȡ�ܹ�˾���ô���("+configCode+")ʧ�ܣ�");
                }
                DBPrpDcompany dbPrpDcompany = new DBPrpDcompany();
                int intResult1 = dbPrpDcompany.getInfo(comCode);
                if(intResult1 ==100) {
                    throw new UserException(-98,-1200,"BLUtiPrintConfig.getConfigValueByComCode()","��ȡ�ϼ���������ʧ�ܣ�");
                } else {
                	comCode = dbPrpDcompany.getUpperComCode();
                }
            }
        }
        return configValue;
    }
    
    
    /**
     * ������
     * @param args �����б�
     */
    public static void main(String[] args) {
        
    }
}
