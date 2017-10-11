package com.sp.indiv.ci.blsvr;

import java.util.Vector;

import com.sp.indiv.ci.dbsvr.DBCIInsureDemandLog;
import com.sp.indiv.ci.schema.CIInsureDemandLogSchema;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;

/**
 * 定义表-CIInsureDemandLog的BL类
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>CreateDate 2013-05-07</p>
 * @author
 * @version 1.0
 */
public class BLCIInsureDemandLog {
    private Vector schemas = new Vector();

    /**
     * 构造函数
     */
    public BLCIInsureDemandLog() {
    }

    /**
     * 初始化记录
     * 
     * @throws Exception
     */
    public void initArr() throws Exception {
        schemas = new Vector();
    }

    /**
     * 增加一条CIInsureDemandLogSchema记录
     * @param cIInsureDemandLogSchema Schema记录
     * @throws Exception
     */
    public void setArr(CIInsureDemandLogSchema cIInsureDemandLogSchema)
            throws Exception {
        schemas.add(cIInsureDemandLogSchema);
    }

    /**
     * 得到一条CIInsureDemandLogSchema记录
     * @param index 下标
     * @return 一个CIInsureDemandLogSchema对象
     * @throws Exception
     */
    public CIInsureDemandLogSchema getArr(int index) throws Exception {
        CIInsureDemandLogSchema cIInsureDemandLogSchema = null;
        cIInsureDemandLogSchema = (CIInsureDemandLogSchema) this.schemas.get(index);
        return cIInsureDemandLogSchema;
    }

    /**
     * 删除一条CIInsureDemandLogSchema记录
     * @param index 下标
     * @throws Exception
     */
    public void remove(int index) throws Exception {
        this.schemas.remove(index);
    }

    /**
     * 得到schemas记录数
     * @return schemas记录数
     * @throws Exception
     */
    public int getSize() throws Exception {
        return this.schemas.size();
    }

    /**
     * 不带连接池，查询一组记录，并将这组记录赋给schemas对象
     * @param iWherePart 查询条件(包括排序字句)
     * @throws UserException
     * @throws Exception
     */
    public void query(String iWherePart) throws UserException, Exception {
        this.query(iWherePart, Integer.parseInt(SysConst.getProperty(
                "QUERY_LIMIT_COUNT").trim()));
    }

    /**
     * 带连接池，按照查询条件得到一组记录数，并将这组记录赋给schemas对象
     * @param dbPool 连接池
     * @param iWherePart 查询条件(包括排序字句)
     * @throws UserException
     * @throws Exception
     */
    public void query(DbPool dbPool, String iWherePart) throws UserException,
            Exception {
        this.query(dbPool, iWherePart, Integer.parseInt(SysConst.getProperty(
                "QUERY_LIMIT_COUNT").trim()));
    }

    /**
     * 不带连接池，查询得到一组记录，并将这组记录赋给schemas对象
     * @param iWherePart 查询条件(包括排序字句)
     * @param iLimitCount 记录数限制(iLimitCount=0: 无限制)
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
     * 带连接池，查询得到一组记录，并将这组记录赋给schemas对象
     * @param dbPool 连接池
     * @param iWherePart 查询条件(包括排序字句)
     * @param iLimitCount 记录数限制(iLimitCount=0: 无限制)
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
     * 带连接池的XXXXX存方法
     * 
     * @param dbPool 连接池
     * @return 无
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
     * 不带连接池的XXXXX存方法
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
     * 不带连接池根据根据XXXXX查询码、序号获取数据
     * @param iDemandNo 根据XXXXX查询码
     * @param iSerialNo 序号
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
     * 带连接池根据根据XXXXX查询码、序号获取数据
     * 
     * @param dbPool 连接池
     * @param iDemandNo 根据XXXXX查询码
     * @param iSerialNo 序号
     */
    public void getData(DbPool dbPool, String ItemNo)
            throws Exception {
        String strWherePart = " ItemNo='" + ItemNo + "'";
        query(strWherePart);
    }

    /**
     * 不带连接池根据XXXXX查询码、序号更新数据
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
     * 带连接池根据XXXXX查询码、序号更新数据
     * 
     * @param dbPool 连接池
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
     * 带连接池根据XXXXX查询码、序号删除记录
     * 
     * @param dbPool 连接池
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
     * 不带连接池根据序号删除记录
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
