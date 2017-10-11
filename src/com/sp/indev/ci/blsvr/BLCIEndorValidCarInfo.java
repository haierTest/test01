package com.sp.indiv.ci.blsvr;

import java.util.ArrayList;
import java.util.Vector;

import com.sp.indiv.ci.dbsvr.DBCIEndorValid;
import com.sp.indiv.ci.dbsvr.DBCIEndorValidCarInfo;
import com.sp.indiv.ci.schema.CIEndorValidCarInfoSchema;
import com.sp.indiv.ci.schema.CIEndorValidCarInfoSchema;
import com.sp.indiv.ci.schema.CIEndorValidSchema;
import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;

public class BLCIEndorValidCarInfo {
    private Vector schemas = new Vector();

    /**
     * ���캯��
     */
    public BLCIEndorValidCarInfo() {
    }

    /**
     * ��ʼ����¼
     * 
     * @param ��
     * @return ��
     * @throws Exception
     */
    public void initArr() throws Exception {
        schemas = new Vector();
    }

    /**
     * ����һ��CIEndorValidCarInfoSchema��¼
     * 
     * @param iCIEndorValidCarInfoSchema
     *            CIEndorValidCarInfoSchema
     * @throws Exception
     */
    public void setArr(CIEndorValidCarInfoSchema iCIEndorValidCarInfoSchema)
            throws Exception {
        try {
            schemas.add(iCIEndorValidCarInfoSchema);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * �õ�һ��CIEndorValidCarInfoSchema��¼
     * 
     * @param index
     *            �±�
     * @return һ��CIEndorValidCarInfoSchema����
     * @throws Exception
     */
    public CIEndorValidCarInfoSchema getArr(int index) throws Exception {
        CIEndorValidCarInfoSchema cIEndorValidCarInfoSchema = null;
        try {
            cIEndorValidCarInfoSchema = (CIEndorValidCarInfoSchema) this.schemas
                    .get(index);
        } catch (Exception e) {
            throw e;
        }
        return cIEndorValidCarInfoSchema;
    }

    /**
     * ɾ��һ��CIEndorValidCarInfoSchema��¼
     * 
     * @param index
     *            �±�
     * @throws Exception
     */
    public void remove(int index) throws Exception {
        try {
            this.schemas.remove(index);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * �õ�schemas��¼��
     * 
     * @return schemas��¼��
     * @throws Exception
     */
    public int getSize() throws Exception {
        return this.schemas.size();
    }

    /**
     * ��dbpool��save����
     * 
     * @param ��
     * @return ��
     */
    public void save(DbPool dbpool) throws Exception {
        DBCIEndorValidCarInfo dbCIEndorValidCarInfo = new DBCIEndorValidCarInfo();

        int i = 0;

        for (i = 0; i < schemas.size(); i++) {
            dbCIEndorValidCarInfo.setSchema((CIEndorValidCarInfoSchema) schemas
                    .get(i));
            dbCIEndorValidCarInfo.insert(dbpool);
        }
    }

    /**
     * ����dbpool��XXXXX�淽��
     * 
     * @param ��
     * @return ��
     */
    public void save() throws Exception {
        DbPool dbpool = new DbPool();

        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
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
     * ����XX�Ż�ȡ����,���ð󶨱�������
     * 
     * @param dbpool
     *            ���ӳ�
     * @param iDemandNo
     *            ��ѯ��
     * @return ��
     */
    public void getData(String iDemandNo) throws Exception {
        DbPool dbpool = new DbPool();
        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
            getData(dbpool, iDemandNo);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            dbpool.close();
        }
    }

    /**
     * ����XX�Ż�ȡ����,���ð󶨱�������
     * 
     * @param dbpool
     *            ���ӳ�
     * @param iDemandNo
     *            ��ѯ��
     * @return ��
     */
    public void getData(DbPool dbpool, String iDemandNo) throws Exception {
        try {
            DBCIEndorValidCarInfo dbCIEndorValidCarInfo = new DBCIEndorValidCarInfo();
            dbCIEndorValidCarInfo.getInfo(dbpool, iDemandNo);
            this.setArr(dbCIEndorValidCarInfo);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
