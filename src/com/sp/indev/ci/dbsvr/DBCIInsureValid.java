package com.sp.indiv.ci.dbsvr;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.util.*;

import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.indiv.ci.schema.CIInsureValidSchema;

/**
 * 定义XX确认主表-CIInsureValid的DB类
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>@createdate 2006-06-14</p>
 * @author DBGenerator
 * @version 1.0
 */
public class DBCIInsureValid extends CIInsureValidSchema{
    /**
     * 构造函数
     */       
    public DBCIInsureValid(){
    }

    /**
     * 插入一条记录
     * @param dbpool dbpool
     * @throws Exception
     */       
    public void insert(DbPool dbpool) throws Exception{
        String strSQL = " Insert Into CIInsureValid(" + 
                           " ValidNo," + 
                           " DemandNo," + 
                           " ProposalNo," + 
                           " PolicyNo," + 
                           " InsureMarkNo," + 
                           " BussinessNature," + 
                           " OperateDate," + 
                           " Clauses," + 
                           " HandlerName," + 
                           " OperatorName," + 
                           " Currency," + 
                           " Premium," + 
                           " ChangeReason," + 
                           " ChangeDetail," + 
                           " ComCode," + 
                           " ValidTime," + 
                           " ReinsureFlag, " +
                           " Remark," + 
                           
                           " ImmeValidFlag, " +
                           " ImmeValidDate, " +
                           
                           
                           " WarrantNo, " +
                           " BankPaymentTime, " +
                           
                           
                           " DeficitFlag, "+
                           
                           
                           " IP, "+
                           " UsbKey, "+
                           
                           " Flag) values(" + 
                           "'" + getValidNo() +"'" + "," +
                           "'" + getDemandNo() +"'" + "," +
                           "'" + getProposalNo() +"'" + "," +
                           "'" + getPolicyNo() +"'" + "," +
                           "'" + getInsureMarkNo() +"'" + "," +
                           "'" + getBussinessNature() +"'" + "," +
                           "'" + getOperateDate() +"'" + "," +
                           "'" + getClauses() +"'" + "," +
                           "'" + getHandlerName() +"'" + "," +
                           "'" + getOperatorName() +"'" + "," +
                           "'" + getCurrency() +"'" + "," +
                           "'" + getPremium() +"'" + "," +
                           "'" + getChangeReason() +"'" + "," +
                           "'" + getChangeDetail() +"'" + "," +
                           "'" + getComCode() +"'" + "," +
                           "'" + getValidTime() +"'" + "," +
                           "'" + getReinsureFlag() +"'" + "," +
                           "'" + getRemark() +"'" + "," +
                           
                           "'" + getImmeValidFlag() + "'," +
                           "'" + getImmeValidDate() + "'," +
                           
                           
                           "'" + getWarrantNo() + "'," +
                           " to_date('" + getBankPaymentTime() + "','yyyy-MM-dd hh24:mi:ss')," +
                           
                           
                           "'" + getDeficitFlag() +"'," +
                           
                           
                           "'" + getIP() +"'," +
                           "'" + getUsbKey() +"'," +
                           
                           "'" + getFlag() +"'" +
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

    public void delete(DbPool dbpool,String validNo) throws Exception{
        String strSQL = " Delete From CIInsureValid Where ValidNo = " + "'" + validNo + "'";
        dbpool.delete(strSQL);
    }

    public void delete(String validNo) throws Exception{
        DbPool dbpool = new DbPool();
        
        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
            dbpool.beginTransaction();
            delete(dbpool,validNo);
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
        String strSQL = " Update CIInsureValid Set" +
                           " ValidNo = " + "'" +getValidNo() + "'" + "," +
                           " DemandNo = " + "'" +getDemandNo() + "'" + "," +
                           " ProposalNo = " + "'" +getProposalNo() + "'" + "," +
                           " PolicyNo = " + "'" +getPolicyNo() + "'" + "," +
                           " InsureMarkNo = " + "'" +getInsureMarkNo() + "'" + "," +
                           " BussinessNature = " + "'" +getBussinessNature() + "'" + "," +
                           " OperateDate = " + "'" +getOperateDate() + "'" + "," +
                           " Clauses = " + "'" +getClauses() + "'" + "," +
                           " HandlerName = " + "'" +getHandlerName() + "'" + "," +
                           " OperatorName = " + "'" +getOperatorName() + "'" + "," +
                           " Currency = " + "'" +getCurrency() + "'" + "," +
                           " Premium = " + "'" +getPremium() + "'" + "," +
                           " ChangeReason = " + "'" +getChangeReason() + "'" + "," +
                           " ChangeDetail = " + "'" +getChangeDetail() + "'" + "," +
                           " ComCode = " + "'" +getComCode() + "'" + "," +
                           " ValidTime = " + "'" +getValidTime() + "'" + "," +
                           " ReinsureFlag = " + "'" +getReinsureFlag() + "'" + "," +
                           " Remark = " + "'" +getRemark() + "'" + "," +
                           
                           " ImmeValidFlag= '" + getImmeValidFlag() + "'" + "," +
                           " ImmeValidDate= '" + getImmeValidDate() + "'" + "," +
                           
                           
                           " WarrantNo = '" + getWarrantNo() + "', " +
                           " BankPaymentTime = to_date('" + getBankPaymentTime() + "','yyyy-MM-dd hh24:mi:ss'), " +
                           
                           
                           " DeficitFlag ='" + getDeficitFlag() + "'," +
                           
                           
                           " IP ='" + getIP() + "'," +
                           " UsbKey ='" + getUsbKey() + "'," +
                           
                           " Flag = " + "'" +getFlag() + "'" +
                           " Where ValidNo = " + "'" +getValidNo() + "'";
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

    public int getInfo(DbPool dbpool,String validNo) throws Exception{
        int intResult = 0;
        String strSQL = " Select * From CIInsureValid Where ValidNo = " + "'" + validNo + "'";
        ResultSet resultSet = dbpool.query(strSQL);
        if(resultSet.next()){
            setValidNo(resultSet.getString("validNo"));
            setDemandNo(resultSet.getString("demandNo"));
            setProposalNo(resultSet.getString("proposalNo"));
            setPolicyNo(resultSet.getString("policyNo"));
            setInsureMarkNo(resultSet.getString("insureMarkNo"));
            setBussinessNature(resultSet.getString("bussinessNature"));
            setOperateDate("" + resultSet.getDate("operateDate"));
            setClauses(resultSet.getString("clauses"));
            setHandlerName(resultSet.getString("handlerName"));
            setOperatorName(resultSet.getString("operatorName"));
            setCurrency(resultSet.getString("currency"));
            setPremium(resultSet.getString("premium"));
            setChangeReason(resultSet.getString("changeReason"));
            setChangeDetail(resultSet.getString("changeDetail"));
            setComCode(resultSet.getString("comCode"));
            setValidTime("" + resultSet.getDate("validTime"));
            setReinsureFlag(resultSet.getString("ReinsureFlag"));
            setRemark(resultSet.getString("remark"));
            
            setImmeValidFlag(resultSet.getString("ImmeValidFlag"));
            setImmeValidDate(resultSet.getString("ImmeValidDate"));
            
            
            setWarrantNo(resultSet.getString("WarrantNo"));
            setBankPaymentTime("" + resultSet.getTimestamp("BankPaymentTime"));
            
            
            setDeficitFlag(resultSet.getString("DeficitFlag"));
            
            
            setIP(resultSet.getString("IP"));
            setUsbKey(resultSet.getString("UsbKey"));
            
            setFlag(resultSet.getString("flag"));
            intResult = 0;
        }
        else{
            intResult = 100;
        }
        resultSet.close();
        return intResult;
    }

    public int getInfo(String validNo) throws Exception{
        int intResult = 0;
        DbPool dbpool = new DbPool();
        String strDataSource =SysConfig.CONST_DDCCDATASOURCE;
        
        try {
            dbpool.open(strDataSource);
            intResult=getInfo(dbpool,validNo);
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
        String strSQL = " SELECT COUNT(*) FROM CIInsureValid WHERE "+ strWhere;
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
            
            if("1".equals(SysConfig.getProperty("DDCCTDATASOURCE_SWITCH"))){
                dbpool.open(SysConfig.CONST_DDCCTDATASOURCE);
            }else{
                dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
            }
            
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
            
            if("1".equals(SysConfig.getProperty("DDCCTDATASOURCE_SWITCH"))){
                dbpool.open(SysConfig.CONST_DDCCTDATASOURCE);
            }else{
                dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
            }
            
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
     * 查询满足模糊查询条件的记录数
     * @param strSQL  SQLStatement
     * @return 满足模糊查询条件的记录数
     * @throws Exception
     */
    public int getCountHistory(String strWhere) throws
        Exception{
        int intCount = 0;
        DbPool dbpool = new DbPool();
        
        try {
            dbpool.open(SysConfig.CONST_HISTORYDATASOURCE);
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
    public Vector findByConditionsHistory(String strSQL) throws
        Exception,SQLException,NamingException{
        Vector vector = new Vector();
        DbPool dbpool = new DbPool();
        
        try {
            dbpool.open(SysConfig.CONST_HISTORYDATASOURCE);
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
        CIInsureValidSchema cIInsureValidSchema = null;
        ResultSet resultSet = dbpool.query(strSQL);
        while(resultSet.next())
        {
            cIInsureValidSchema = new CIInsureValidSchema();
            cIInsureValidSchema.setValidNo(resultSet.getString("validNo"));
            cIInsureValidSchema.setDemandNo(resultSet.getString("demandNo"));
            cIInsureValidSchema.setProposalNo(resultSet.getString("proposalNo"));
            cIInsureValidSchema.setPolicyNo(resultSet.getString("policyNo"));
            cIInsureValidSchema.setInsureMarkNo(resultSet.getString("insureMarkNo"));
            cIInsureValidSchema.setBussinessNature(resultSet.getString("bussinessNature"));
            cIInsureValidSchema.setOperateDate("" + resultSet.getDate("operateDate"));
            cIInsureValidSchema.setClauses(resultSet.getString("clauses"));
            cIInsureValidSchema.setHandlerName(resultSet.getString("handlerName"));
            cIInsureValidSchema.setOperatorName(resultSet.getString("operatorName"));
            cIInsureValidSchema.setCurrency(resultSet.getString("currency"));
            cIInsureValidSchema.setPremium(resultSet.getString("premium"));
            cIInsureValidSchema.setChangeReason(resultSet.getString("changeReason"));
            cIInsureValidSchema.setChangeDetail(resultSet.getString("changeDetail"));
            cIInsureValidSchema.setComCode(resultSet.getString("comCode"));
            cIInsureValidSchema.setValidTime("" + resultSet.getDate("validTime"));
            cIInsureValidSchema.setReinsureFlag(resultSet.getString("ReinsureFlag"));
            cIInsureValidSchema.setRemark(resultSet.getString("remark"));
            
            cIInsureValidSchema.setImmeValidFlag(resultSet.getString("ImmeValidFlag"));
            cIInsureValidSchema.setImmeValidDate(resultSet.getString("ImmeValidDate"));
            
            
            cIInsureValidSchema.setWarrantNo(resultSet.getString("WarrantNo"));
            cIInsureValidSchema.setBankPaymentTime("" + resultSet.getTimestamp("BankPaymentTime"));
            
            
            cIInsureValidSchema.setDeficitFlag(resultSet.getString("DeficitFlag"));
            
            
            cIInsureValidSchema.setIP(resultSet.getString("IP"));
            cIInsureValidSchema.setUsbKey(resultSet.getString("UsbKey"));
            
            cIInsureValidSchema.setFlag(resultSet.getString("flag"));
            vector.add(cIInsureValidSchema);
        }
        resultSet.close();
        return vector;
    }
    /**
     * 查询满足模糊查询条件的记录数
     * @param dbpool dbpool
     * @param statement statement
     * @param iWhereValue iWhereValue
     * @return 满足模糊查询条件的记录数
     * @throws Exception
     */
    public int getCount(DbPool dbpool,String strWhere,ArrayList iWhereValue) 
        throws Exception{
        int intCount = 0;
        String strSQL = " SELECT COUNT(*) FROM CIInsureValid WHERE "+ strWhere;
        ResultSet resultSet = null;
        if(iWhereValue != null)
        {
            dbpool.prepareInnerStatement(strSQL);
            for(int i=0;i<iWhereValue.size();i++)
            {
                dbpool.setString(i+1,(iWhereValue.get(i)).toString());
            }
            resultSet = dbpool.executePreparedQuery();
        }else
        {
            resultSet = dbpool.query(strSQL);
        }
        if(resultSet.next())
        {
            intCount = resultSet.getInt(1);
            resultSet.close();
        }
        return intCount;
    }

    /**
     * 查询满足模糊查询条件的记录数
     * @param strSQL  SQLStatement
     * @param iWhereValue iWhereValue
     * @return 满足模糊查询条件的记录数
     * @throws Exception
     */
    public int getCount(String strWhere,ArrayList iWhereValue) throws
        Exception{
        int intCount = 0;
        DbPool dbpool = new DbPool();
        
        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
            intCount=getCount(dbpool,strWhere,iWhereValue);
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
     * 主函数
     * @param args 参数列表
     */
    public static void main(String[] args){
        
    }
}
