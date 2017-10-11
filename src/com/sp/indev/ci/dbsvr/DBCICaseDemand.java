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
 * 定义结案数据交互信息表-CICaseDemand的DB类
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>@createdate 2006-06-14</p>
 * @author DBGenerator
 * @version 1.0
 */
public class DBCICaseDemand extends CICaseDemandSchema{
    /**
     * 构造函数
     */       
    public DBCICaseDemand(){
    }

    /**
     * 插入一条记录
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
     * 插入一条记录
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
     * 更新一条记录
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
     * 打开模糊查询的游标
     * @param strSQL SQLstatement
     */
    public void open(String strSQL){
    }

    /**
     * 根据游标提取一条记录
     * @param index index
     */
    public void fetch(int index){
    }

    /**
     * 关闭模糊查询的游标
     */
    public void close(){
    }

    /**
     * 查询满足模糊查询条件的记录数
     * @param dbpool dbpool
     * @param statement statement
     * @return 满足模糊查询条件的记录数
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
     * 查询满足模糊查询条件的记录数
     * @param strSQL  SQLStatement
     * @return 满足模糊查询条件的记录数
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
     * 根据SQL语句获取结果集
     * @param strSQL  SQL语句
     * @return Vector 查询结果记录集
     * @throws SQLException    数据库操作异常类
     * @throws NamingException 名字异常类
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
     * 根据SQL语句获取结果集
     * @param dbpool  数据库连接池
     * @param strSQL  SQL语句
     * @return Vector 查询结果记录集
     * @throws SQLException    数据库操作异常类
     * @throws NamingException 名字异常类
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
     * 主函数
     * @param args 参数列表
     */
    public static void main(String[] args){
        
    }
}
