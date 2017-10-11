package com.sp.prpall.solr.db;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.sp.utility.database.DbPool;

public class DBCustomerIndex {
    private static String QUERY_COMPARE = "select distinct CUSTOMERCNAME from t_spis_customer_unit_sync ts  where not exists (select ti.CUSTOMERCNAME  from t_spis_customer_unit_index ti  where ts.CUSTOMERCNAME = ti.CUSTOMERCNAME)";
    
    private static String SYNC_COMPARE = "select distinct  CUSTOMERCNAME from t_spis_customer_unit ts  where not exists (select ti.CUSTOMERCNAME  from t_spis_customer_unit_sync ti  where ts.CUSTOMERCNAME = ti.CUSTOMERCNAME)";
    
    /**
    * �Ƚ�t_spis_customer_unit ��t_spis_customer_unit_sync���в�ͬ���ݵĸ���
    * @param dbPool
    * @return
    * @throws SQLException 
    */
    public int compareCoustomerCount(DbPool dbPool) throws SQLException{
        String sql = "select count(*) from ("+SYNC_COMPARE+")";
        return dbPool.getCount(sql);
    }
    /**
     * ��ʱ��t_spis_customer_unit ���е�CUSTOMERCNAMEͬ����t_spis_customer_unit_sync����
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
     * ��������ǰ�������� add by cuiyan20130509
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
     * ���������� ���ñ��� add by cuiyan20130509
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
     * ��������� �ύ add by cuiyan20130509
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
     * ��ѯt_spis_customer_unit_sync��t_spis_customer_unit_index ��ͬ������
     * 
     * @param dbPool
     * @return
     * @throws SQLException
     */
    public ResultSet queryDifferefentNames(DbPool dbPool) throws SQLException {
        return dbPool.query(QUERY_COMPARE);
    }

    /**
     * ��ѯt_spis_customer_unit_sync��t_spis_customer_unit_index ��ͬ�����ݵĸ��� add by
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
