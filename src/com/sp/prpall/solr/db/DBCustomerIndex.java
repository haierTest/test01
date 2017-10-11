package com.sp.prpall.solr.db;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.sp.utility.database.DbPool;

public class DBCustomerIndex {
    private static String QUERY_COMPARE = "select distinct CUSTOMERCNAME from t_spis_customer_unit_sync ts  where not exists (select ti.CUSTOMERCNAME  from t_spis_customer_unit_index ti  where ts.CUSTOMERCNAME = ti.CUSTOMERCNAME)";
    
    private static String SYNC_COMPARE = "select distinct  CUSTOMERCNAME from t_spis_customer_unit ts  where not exists (select ti.CUSTOMERCNAME  from t_spis_customer_unit_sync ti  where ts.CUSTOMERCNAME = ti.CUSTOMERCNAME)";
    
    /**
    * 比较t_spis_customer_unit 和t_spis_customer_unit_sync表中不同数据的个数
    * @param dbPool
    * @return
    * @throws SQLException 
    */
    public int compareCoustomerCount(DbPool dbPool) throws SQLException{
        String sql = "select count(*) from ("+SYNC_COMPARE+")";
        return dbPool.getCount(sql);
    }
    /**
     * 定时将t_spis_customer_unit 表中的CUSTOMERCNAME同步到t_spis_customer_unit_sync表中
     * @param dbPool  add by cuiyan 20130523
     */
    public void insertCoustomerSync(DbPool dbPool){
        try {
            String sql = "insert into t_spis_customer_unit_sync("+SYNC_COMPARE+")";
            dbPool.insert(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 批量插入前建立连接 add by cuiyan20130509
     * 
     * @param dbPool
     */
    public void insertBatchPre(DbPool dbPool) {

        String insertSql = "insert into  t_spis_customer_unit_index(ID,CUSTOMERCNAME) values(?,?)";
        try {
            dbPool.prepareInnerStatement(insertSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 批量插入中 设置变量 add by cuiyan20130509
     * 
     * @param dbPool
     * @param id
     * @param customerCname
     */
    public void insertBatch(DbPool dbPool, String id, String customerCname) {
        try {
            dbPool.setString(1, id);
            dbPool.setString(2, customerCname);
            dbPool.addBatch();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 批量插入后 提交 add by cuiyan20130509
     * 
     * @param dbPool
     */
    public void insertBatchLast(DbPool dbPool) {
        try {
            dbPool.executePreparedUpdateBatch();
            
            dbPool.clearBatch();
            
        } catch (Exception e) {
            
            e.printStackTrace();
        }
    }

    /**
     * 查询t_spis_customer_unit_sync与t_spis_customer_unit_index 不同的数据
     * 
     * @param dbPool
     * @return
     * @throws SQLException
     */
    public ResultSet queryDifferefentNames(DbPool dbPool) throws SQLException {
        return dbPool.query(QUERY_COMPARE);
    }

    /**
     * 查询t_spis_customer_unit_sync与t_spis_customer_unit_index 不同的数据的个数 add by
     * cuiyan 20130510
     * 
     * @param dbPool
     * @return
     * @throws SQLException
     */
    public int getNotNameCount(DbPool dbPool) throws SQLException {
        String sql = "select count(*) from (" + QUERY_COMPARE + ")";
        return dbPool.getCount(sql);
    }

}
