package com.sp.prpall.solr.db;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.sp.utility.database.DbPool;

public class DBModelIndex {

    private static String QUERY_COMPARE=" select distinct MODELNAME from  prpdcarmodel_sync ps  where not exists (select pi.MODELNAME  from prpdcarmodel_index pi  where ps.MODELNAME = pi.MODELNAME)";
    
    private static String SYNC_COMPARE = "select distinct MODELNAME from  prpdcarmodel p  where not exists (select ps.MODELNAME  from prpdcarmodel_sync ps  where p.MODELNAME = ps.MODELNAME)";
	
	/**
	 * �Ƚ�prpdcarmodel ��prpdcarmodel_sync���в�ͬ���ݵĸ��� add by cuiyan 20130523
	 * @param dbPool
	 * @return
	 * @throws SQLException 
	 */
    public int compareModelCount(DbPool dbPool) throws SQLException{
        String sql = "select count(*) from ("+SYNC_COMPARE+")";
        return dbPool.getCount(sql);
    }
    /**
     * ��ʱ��prpdcarmodel ���е�MODELNAMEͬ����prpdcarmodel_sync����
     * @param dbPool
     */
    public void insertModelSync(DbPool dbPool){
        String sql = "insert into prpdcarmodel_sync("+SYNC_COMPARE+")";
        try {
            dbPool.insert(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	/**
	 * ��������ǰ�������� add by cuiyan20130509 
	 * @param dbPool
	 */
	public void insertBatchPre(DbPool dbPool){
		
		String insertSql = "insert into  prpdcarmodel_index(ID,MODELNAME) values(?,?)";
		try {
				dbPool.prepareInnerStatement(insertSql);	
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	/**
	 * ���������� ���ñ��� 	  add by cuiyan20130509 
	 * @param dbPool
	 * @param id
	 * @param customerCname
	 */
	public void insertBatch(DbPool dbPool,String id ,String modelName){
		try {
			dbPool.setString(1, id);
			dbPool.setString(2, modelName);
			dbPool.addBatch();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * ��������� �ύ add by cuiyan20130509 
	 * @param dbPool 
	 */
	public void insertBatchLast(DbPool dbPool){
		try {
			dbPool.executePreparedUpdateBatch();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
	/**
	 * ��ѯprpdcarmodel_sync��prpdcarmodel_index ��ͬ������result add by cuiyan 20130509
	 * @param dbPool
	 * @return
	 * @throws SQLException
	 */
	public ResultSet queryDifferefentNames(DbPool dbPool) throws SQLException{
	    return dbPool.query(QUERY_COMPARE);
	}
	/**
	 * ��ѯprpdcarmodel_sync��prpdcarmodel_index ���в�ͬ���ݵĸ��� add by cuiyan 20130510
	 * @param dbPool
	 * @return
	 * @throws SQLException
	 */
	public int getNotSameCount(DbPool dbPool) throws SQLException{
		String sql = "select count(*) from ("+QUERY_COMPARE+")";
		return dbPool.getCount(sql);
	}
}
