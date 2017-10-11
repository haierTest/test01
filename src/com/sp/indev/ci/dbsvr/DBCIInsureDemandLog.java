package com.sp.indiv.ci.dbsvr;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javax.naming.NamingException;
import com.sp.indiv.ci.schema.CIInsureDemandLogSchema;
import com.sp.utility.database.DbPool;

/**
 * �����-CIInsureDemandLog��DB��
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>CreateDate 2013-05-07</p>
 * @author
 * @version 1.0
 */
public class DBCIInsureDemandLog extends CIInsureDemandLogSchema {

    /**
     * ���캯��
     */
    public DBCIInsureDemandLog() {}

   /**
    * �����ӳز���һ����¼
    * 
    * @param dbPool ���ݿ����ӳ�
    * @throws Exception
    */
    public void insert(DbPool dbPool) throws Exception {
        String strSQL = "insert into CIInsureDemandLog (" +
                          "ItemNo," +
                          "LicenseNo," +
                          "FrameNo," +
                          "EngineNo," +
                          "Flag," +
                          "ErrorMessage," +
                          "DemandNo," +
                          "ComCode," +
                          "RiskCode," +
                          "ChannelType," +
                          "OperateSite," +
                          "OperaterCode," +
                          "CreateDate," +
                          
                          "IniTime," +
                          
                          "Remark" +
                          ") values (" +
                          "'" + getItemNo() + "'," +
                          "'" + getLicenseNo() + "'," +
                          "'" + getFrameNo() + "'," +
                          "'" + getEngineNo() + "'," +
                          "'" + getFlag() + "'," +
                          "'" + getErrorMessage() + "'," +
                          "'" + getDemandNo() + "'," +
                          "'" + getComCode() + "'," +
                          "'" + getRiskCode() + "'," +
                          "'" + getChannelType() + "'," +
                          "'" + getOperateSite() + "'," +
                          "'" + getOperaterCode() + "'," +
                          "to_date('" + getCreateDate() + "','yyyy-mm-dd hh24:mi:ss')," +
                          
                          "to_date('" + getIniTime() + "','yyyy-mm-dd hh24:mi:ss')," +
                          
                          "'" + getRemark() + "')";
        dbPool.insert(strSQL);
    }

    /**
     * �������ӳز���һ����¼
     * 
     * @throws Exception
     */       
    public void insert() throws Exception{
        DbPool dbPool = new DbPool();
        
        try {
            dbPool.open("platformNewDataSource");
            dbPool.beginTransaction();
            insert(dbPool);
            dbPool.commitTransaction();
        } catch(Exception exception){
            dbPool.rollbackTransaction();
            throw exception;
        }
        finally {
            dbPool.close();
        }
    }

    /**
     * �����ӳ�ɾ����¼
     * 
     * @param dbPool ���ݿ����ӳ�
     * @throws Exception
     */
    public void delete(DbPool dbPool, String strWhere)
            throws Exception {
        String strSQL = " Delete From CIInsureDemandLog Where " + strWhere;
        dbPool.delete(strSQL);
    }

    /**
     * �������ӳ�ɾ����¼
     * 
     * @param dbPool ���ݿ����ӳ�
     * @throws Exception
     */
    public void delete(String strWhere) throws Exception{
        DbPool dbPool = new DbPool();
        
        try {
            dbPool.open("platformNewDataSource");
            dbPool.beginTransaction();
            delete(dbPool, strWhere);
            dbPool.commitTransaction();
        } catch(Exception exception){
            dbPool.rollbackTransaction();
            throw exception;
        }
        finally {
            dbPool.close();
        }
    }

    /**
     * �����ӳظ���XX��ѯ��ɾ����¼
     * 
     * @param dbPool ���ݿ����ӳ�
     * @throws Exception
     */
    public void deleteByDemandNo(DbPool dbPool, String demanNo)
            throws Exception {
        String strSQL = " Delete From CIInsureDemandLog Where DemandNo='" + demanNo + "'";
        dbPool.delete(strSQL);
    }

    /**
     * �������ӳظ���XX��ѯ��ɾ����¼
     * 
     * @param dbPool ���ݿ����ӳ�
     * @throws Exception
     */
    public void deleteByDemandNo(String demanNo) throws Exception{
        DbPool dbPool = new DbPool();
        
        try {
            dbPool.open("platformNewDataSource");
            dbPool.beginTransaction();
            deleteByDemandNo(dbPool, demanNo);
            dbPool.commitTransaction();
        } catch(Exception exception){
            dbPool.rollbackTransaction();
            throw exception;
        }
        finally {
            dbPool.close();
        }
    }

    /**
     * �����ӳظ���SQL����ȡ�����
     * @param dbPool ���ݿ����ӳ�
     * @param strSQL SQL���
     * @throws SQLException
     * @throws NamingException
     * @return Vector ��ѯ�����¼��
     */
    public Vector findByConditions(DbPool dbPool,
            String strSQL) throws SQLException, NamingException {
        Vector vector = new Vector();
        CIInsureDemandLogSchema cIInsureDemandLogSchema = null;
        ResultSet resultSet = dbPool.query(strSQL);
        while(resultSet.next()) {
            cIInsureDemandLogSchema = new CIInsureDemandLogSchema();
            cIInsureDemandLogSchema.setItemNo(resultSet.getString("ItemNo"));
            cIInsureDemandLogSchema.setLicenseNo(resultSet.getString("LicenseNo"));
            cIInsureDemandLogSchema.setFrameNo(resultSet.getString("FrameNo"));
            cIInsureDemandLogSchema.setEngineNo(resultSet.getString("EngineNo"));
            cIInsureDemandLogSchema.setFlag(resultSet.getString("Flag"));
            cIInsureDemandLogSchema.setErrorMessage(resultSet.getString("ErrorMessage"));
            cIInsureDemandLogSchema.setDemandNo(resultSet.getString("DemandNo"));
            cIInsureDemandLogSchema.setComCode(resultSet.getString("ComCode"));
            cIInsureDemandLogSchema.setRiskCode(resultSet.getString("RiskCode"));
            cIInsureDemandLogSchema.setChannelType(resultSet.getString("ChannelType"));
            cIInsureDemandLogSchema.setOperateSite(resultSet.getString("OperateSite"));
            cIInsureDemandLogSchema.setOperaterCode(resultSet.getString("OperaterCode"));
            cIInsureDemandLogSchema.setCreateDate(resultSet.getString("CreateDate"));
            cIInsureDemandLogSchema.setRemark(resultSet.getString("Remark"));
            
            cIInsureDemandLogSchema.setIniTime(resultSet.getString("IniTime"));
            
            vector.add(cIInsureDemandLogSchema);
        }
        return vector;
    }

    /**
     * �������ӳظ���SQL����ȡ�����
     * @param strSQL  SQL���
     * @return Vector ��ѯ�����¼��
     */
    public Vector findByConditions(String strSQL) throws Exception,
            SQLException, NamingException {
        Vector vector = new Vector();
        DbPool dbPool = new DbPool();
        
        try {
            dbPool.open("platformNewDataSource");
            vector = findByConditions(dbPool, strSQL);
        } catch (SQLException sqlException) {
            throw sqlException;
        } catch (NamingException namingException) {
            throw namingException;
        } finally {
            dbPool.close();
        }
        return vector;
    }

    /**
     * �����ӳظ���SQL����������
     * 
     * @param dbPool ���ݿ����ӳ�
     * @param strWhere SQL���
     * @throws Exception
     */
    public void update(DbPool dbPool, String strWhere) throws Exception {
        String strSQL = "Update CIInsureDemandLog Set" +
                          "ItemNo='"+getItemNo()+"',"+
                          "LicenseNo='"+getLicenseNo()+"',"+
                          "FrameNo='"+getFrameNo()+"',"+
                          "EngineNo='"+getEngineNo()+"',"+
                          "Flag='"+getFlag()+"',"+
                          "ErrorMessage='"+getErrorMessage()+"',"+
                          "DemandNo='"+getDemandNo()+"',"+
                          "ComCode='"+getComCode()+"',"+
                          "RiskCode='"+getRiskCode()+"',"+
                          "ChannelType='"+getChannelType()+"',"+
                          "OperateSite='"+getOperateSite()+"',"+
                          "OperaterCode='"+getOperaterCode()+"',"+
                          "CreateDate=to_date('"+getCreateDate()+"','yyyy-mm-ddhh24:mi:ss'),"+
                          
                          "IniTime=to_date('"+getIniTime()+"','yyyy-mm-ddhh24:mi:ss'),"+
                          
                          "Remark='"+getRemark()+"',"+
                          "Where " + strWhere;
        dbPool.update(strSQL);
    }

    /**
     * �������ӳظ���SQL����������
     * 
     * @param strWhere SQL���
     * @throws Exception
     */
    public void update(String strWhere) throws Exception {
        DbPool dbPool = new DbPool();
        try {
            dbPool.open("platformNewDataSource");
            update(dbPool, strWhere);
            dbPool.commitTransaction();
        } catch (Exception e) {
            throw e;
        } finally {
            dbPool.close();
        }
    }

    /**
     * ��ѯ����ģ����ѯ�����ļ�¼��
     * 
     * @param dbPool ���ӳ�
     * @param statement ��ѯ����
     * @return ����ģ����ѯ�����ļ�¼��
     * @throws Exception
     */
    public int getCount(DbPool dbPool, String strWhere) throws Exception {
        int intCount = 0;
        String strSQL = " SELECT COUNT(*) FROM CIInsureDemandLog WHERE "
                + strWhere;
        ResultSet resultSet = dbPool.query(strSQL);
        if (resultSet.next()) {
            intCount = resultSet.getInt(1);
            resultSet.close();
        }
        return intCount;
    }

    /**
     * ��ѯ����ģ����ѯ�����ļ�¼��
     * @param strSQL ��ѯ����
     * @return ����ģ����ѯ�����ļ�¼��
     * @throws Exception
     */
    public int getCount(String strWhere) throws Exception {
        int intCount = 0;
        DbPool dbPool = new DbPool();
        
        try {
            dbPool.open("platformNewDataSource");
            intCount = getCount(dbPool, strWhere);
        } catch (Exception exception) {
            throw exception;
        } finally {
            dbPool.close();
        }
        return intCount;
    }
}
