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
     * 构造函数
     */
    public BLCIEndorValidCarInfo() {
    }

    /**
     * 初始化记录
     * 
     * @param 无
     * @return 无
     * @throws Exception
     */
    public void initArr() throws Exception {
        schemas = new Vector();
    }

    /**
     * 增加一条CIEndorValidCarInfoSchema记录
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
     * 得到一条CIEndorValidCarInfoSchema记录
     * 
     * @param index
     *            下标
     * @return 一个CIEndorValidCarInfoSchema对象
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
     * 删除一条CIEndorValidCarInfoSchema记录
     * 
     * @param index
     *            下标
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
     * 得到schemas记录数
     * 
     * @return schemas记录数
     * @throws Exception
     */
    public int getSize() throws Exception {
        return this.schemas.size();
    }

    /**
     * 带dbpool的save方法
     * 
     * @param 无
     * @return 无
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
     * 不带dbpool的XXXXX存方法
     * 
     * @param 无
     * @return 无
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
     * 根据XX号获取数据,调用绑定变量方法
     * 
     * @param dbpool
     *            连接池
     * @param iDemandNo
     *            查询码
     * @return 无
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
     * 根据XX号获取数据,调用绑定变量方法
     * 
     * @param dbpool
     *            连接池
     * @param iDemandNo
     *            查询码
     * @return 无
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
