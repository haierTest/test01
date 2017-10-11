package com.sp.indiv.ci.dbsvr;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.util.*;
import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.indiv.ci.schema.CICaseDemandSchema;

/**
 * ����᰸���ݽ�����Ϣ��-CICaseDemand��DB��
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>@createdate 2006-06-14</p>
 * @author DBGenerator
 * @version 1.0
 */
public class DBCICaseDemand extends CICaseDemandSchema{
    /**
     * ���캯��
     */       
    public DBCICaseDemand(){
    }

    /**
     * ����һ����¼
     * @param dbpool dbpool
     * @throws Exception
     */       
    public void insert(DbPool dbpool) throws Exception{
        String strSQL = " Insert Into CICaseDemand(" + 
                           " ClaimNo," + 
                           " ClaimCode," + 
                           " CaseCheckNo) values(" + 
                           "'" + getClaimNo() +"'" + "," +
                           "'" + getClaimCode() +"'" + "," +
                           "'" + getCaseCheckNo() +"'" +
                           ")";
        dbpool.insert(strSQL);
    }

    /**
     * ����һ����¼
     * @throws Exception
     */       
    public void insert() throws Exception{
        DbPool dbpool = new DbPool();
        
        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
            dbpool.beginTransaction();
            insert(dbpool);
            dbpool.commitTransaction();
            dbpool.close();
        }
        catch(Exception exception){
            dbpool.rollbackTransaction();
            dbpool.close();
            throw exception;
        }
        finally {
            dbpool.close();
        }
    }

    public void delete(DbPool dbpool,String claimNo) throws Exception{
        String strSQL = " Delete From CICaseDemand Where ClaimNo = " + "'" + claimNo + "'";
        dbpool.delete(strSQL);
    }

    public void delete(String claimNo) throws Exception{
        DbPool dbpool = new DbPool();
        
        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
            dbpool.beginTransaction();
            delete(dbpool,claimNo);
            dbpool.commitTransaction();
            dbpool.close();
        }
        catch(Exception exception){
            dbpool.rollbackTransaction();
            dbpool.close();
            throw exception;
        }
        finally {
            dbpool.close();
        }
    }

    public void update(DbPool dbpool) throws Exception{
        String strSQL = " Update CICaseDemand Set" +
                           " ClaimNo = " + "'" +getClaimNo() + "'" + "," +
                           " ClaimCode = " + "'" +getClaimCode() + "'" + "," +
                           " CaseCheckNo = " + "'" +getCaseCheckNo() + "'" +
                           " Where ClaimNo = " + "'" +getClaimNo() + "'";
        dbpool.update(strSQL);
    }

    /**
     * ����һ����¼
     * @throws Exception
     */       
    public void update() throws Exception{
        DbPool dbpool = new DbPool();
        
        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
            dbpool.beginTransaction();
            update(dbpool);
            dbpool.commitTransaction();
            dbpool.close();
        }
        catch(Exception exception){
            dbpool.rollbackTransaction();
            dbpool.close();
            throw exception;
        }
        finally {
            dbpool.close();
        }
    }

    public int getInfo(DbPool dbpool,String claimNo) throws Exception{
        int intResult = 0;
        String strSQL = " Select * From CICaseDemand Where ClaimNo = " + "'" + claimNo + "'";
        ResultSet resultSet = dbpool.query(strSQL);
        if(resultSet.next()){
            setClaimNo(resultSet.getString("claimNo"));
            setClaimCode(resultSet.getString("claimCode"));
            setCaseCheckNo(resultSet.getString("caseCheckNo"));
            intResult = 0;
        }
        else{
            intResult = 100;
        }
        resultSet.close();
        return intResult;
    }

    public int getInfo(String claimNo) throws Exception{
        int intResult = 0;
        DbPool dbpool = new DbPool();
        String strDataSource =SysConfig.CONST_DDCCDATASOURCE;
        
        try {
            dbpool.open(strDataSource);
            intResult=getInfo(dbpool,claimNo);
            dbpool.close();
        }
        catch(Exception exception){
            dbpool.close();
            throw exception;
        }
        finally {
            dbpool.close();
        }
        return intResult;
    }

    /**
     * ��ģ����ѯ���α�
     * @param strSQL SQLstatement
     */
    public void open(String strSQL){
    }

    /**
     * �����α���ȡһ����¼
     * @param index index
     */
    public void fetch(int index){
    }

    /**
     * �ر�ģ����ѯ���α�
     */
    public void close(){
    }

    /**
     * ��ѯ����ģ����ѯ�����ļ�¼��
     * @param dbpool dbpool
     * @param statement statement
     * @return ����ģ����ѯ�����ļ�¼��
     * @throws Exception
     */
    public int getCount(DbPool dbpool,String strWhere) 
        throws Exception{
        int intCount = 0;
        String strSQL = " SELECT COUNT(*) FROM CICaseDemand WHERE "+ strWhere;
        ResultSet resultSet = dbpool.query(strSQL);
        if(resultSet.next()){
            intCount = resultSet.getInt(1);
            resultSet.close();
        }
        return intCount;
    }

    /**
     * ��ѯ����ģ����ѯ�����ļ�¼��
     * @param strSQL  SQLStatement
     * @return ����ģ����ѯ�����ļ�¼��
     * @throws Exception
     */
    public int getCount(String strWhere) throws
        Exception{
        int intCount = 0;
        DbPool dbpool = new DbPool();
        
        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
            intCount=getCount(dbpool,strWhere);
            dbpool.close();
        }
        catch(Exception exception){
            dbpool.close();
            throw exception;
        }
        finally {
            dbpool.close();
        }
        return intCount;
    }

    /**
     * ����SQL����ȡ�����
     * @param strSQL  SQL���
     * @return Vector ��ѯ�����¼��
     * @throws SQLException    ���ݿ�����쳣��
     * @throws NamingException �����쳣��
     */
    public Vector findByConditions(String strSQL) throws
        Exception,SQLException,NamingException{
        Vector vector = new Vector();
        DbPool dbpool = new DbPool();
        
        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
            vector=findByConditions(dbpool,strSQL);
            dbpool.close();
        }
        catch(SQLException sqlException){
            dbpool.close();
            throw sqlException;
        }
        catch(NamingException namingException){
            dbpool.close();
            throw namingException;
        }
        finally {
            dbpool.close();
        }
        return vector;
    }

    /**
     * ����SQL����ȡ�����
     * @param dbpool  ���ݿ����ӳ�
     * @param strSQL  SQL���
     * @return Vector ��ѯ�����¼��
     * @throws SQLException    ���ݿ�����쳣��
     * @throws NamingException �����쳣��
     */
    public Vector findByConditions(DbPool dbpool,String strSQL) throws
        SQLException,NamingException{
        Vector vector = new Vector();
        CICaseDemandSchema cICaseDemandSchema = null;
        ResultSet resultSet = dbpool.query(strSQL);
        while(resultSet.next())
        {
            cICaseDemandSchema = new CICaseDemandSchema();
            cICaseDemandSchema.setClaimNo(resultSet.getString("claimNo"));
            cICaseDemandSchema.setClaimCode(resultSet.getString("claimCode"));
            cICaseDemandSchema.setCaseCheckNo(resultSet.getString("caseCheckNo"));
            vector.add(cICaseDemandSchema);
        }
        resultSet.close();
        return vector;
    }

    /**
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
