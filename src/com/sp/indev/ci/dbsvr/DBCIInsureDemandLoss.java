package com.sp.indiv.ci.dbsvr;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.util.*;

import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.indiv.ci.schema.CIInsureDemandLossSchema;
import com.sp.indiv.ci.schema.CIInsureDemandPaySchema;

/**
 * 定义XX查询违章表-CIInsureDemandLoss的DB类
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>@createdate 2006-06-14</p>
 * @author DBGenerator
 * @version 1.0
 */
public class DBCIInsureDemandLoss extends CIInsureDemandLossSchema{
    /**
     * 构造函数
     */       
    public DBCIInsureDemandLoss(){
    }

    /**
     * 插入一条记录
     * @param dbpool dbpool
     * @throws Exception
     */       
    public void insert(DbPool dbpool) throws Exception{
        String strSQL = " Insert Into CIInsureDemandLoss(" + 
                           " DemandNo," + 
                           " SerialNo," + 
                           " LossTime," + 
                           " LossAddress," + 
                           " LossAction," + 
                           " Coeff," + 
                           " LossType," + 
                           " IdentifyType," + 
                           " IdentifyNumber," + 
                           " Remark," + 
                           " Flag," + 
                           
                           " AdjustFlag," +
                           
                           " DecisionCode," +
                           " DecisionTypeCode," +
                           " LicensePlateNo," +
                           " LicensePlateTypeCode," +
                           " JurisdictionAgencyCode," +
                           
                           " PeccancyNumber," +
                           
                           
        				   " ProcessingStatus," +
                           
        			        
        			        "DriverName,"+
        			        "PeccancyDetail,"+
        			        
        			        
        			        "VouchCode,"+
        			        "SanctionPerson,"+
        			        "SanctionDate,"+
        			        
        			        
        			        "MonitorId,"+
        			        
                           " LossAcceptDate) values(" + 
                           "'" + getDemandNo() +"'" + "," +
                           "'" + getSerialNo() +"'" + "," +
                           "'" + getLossTime() +"'" + "," +
                           "'" + getLossAddress() +"'" + "," +
                           "'" + getLossAction() +"'" + "," +
                           "'" + getCoeff() +"'" + "," +
                           "'" + getLossType() +"'" + "," +
                           "'" + getIdentifyType() +"'" + "," +
                           "'" + getIdentifyNumber() +"'" + "," +
                           "'" + getRemark() +"'" + "," +
                           "'" + getFlag() +"', " +
                           
                           "'" + getAdjustFlag() + "', " +
                           
                           
                           "'" + getDecisionCode() + "', " +
                           "'" + getDecisionTypeCode() + "', " +
                           "'" + getLicensePlateNo() + "', " +
                           "'" + getLicensePlateTypeCode() + "', " +
                           "'" + getJurisdictionAgencyCode() + "', " +
                           
                           
                           "'" + getPeccancyNumber() + "', " +
                           
                           
                           "'" + getProcessingStatus() + "', " +
                           
       			           
                           "'" + getDriverName() + "', " +
                           "'" + getPeccancyDetail() + "', " +
       			           
                           
                           "'" + getVouchCode() + "', " +
                           "'" + getSanctionPerson() + "', " +
                           "'" + getSanctionDate() + "', " +
                           
                           
                           "'" + getMonitorId() + "', " +
                           
                           "'" + getAcceptDate() + "'" + 
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
            
            String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
            if ("1".equals(strSwitch)) {
                dbpool.open("platformNewDataSource");
            } else {
                dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
            }
            
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

    public void delete(DbPool dbpool,String demandNo,String serialNo) throws Exception{
        String strSQL = " Delete From CIInsureDemandLoss Where DemandNo = " + "'" + demandNo + "'" +
                            " And SerialNo = " + "'" + serialNo + "'";
        dbpool.delete(strSQL);
    }
    
    public void deleteByDemandNo(DbPool dbpool,String demandNo) throws Exception{
        String strSQL = " Delete From CIInsureDemandLoss Where DemandNo = " + "'" + demandNo + "'";
        dbpool.delete(strSQL);
    }

    public void delete(String demandNo,String serialNo) throws Exception{
        DbPool dbpool = new DbPool();
        
        try {
            
            String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
            if ("1".equals(strSwitch)) {
                dbpool.open("platformNewDataSource");
            } else {
                dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
            }
            
            dbpool.beginTransaction();
            delete(dbpool,demandNo,serialNo);
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
        String strSQL = " Update CIInsureDemandLoss Set" +
                           " DemandNo = " + "'" +getDemandNo() + "'" + "," +
                           " SerialNo = " + "'" +getSerialNo() + "'" + "," +
                           " LossTime = " + "'" +getLossTime() + "'" + "," +
                           " LossAddress = " + "'" +getLossAddress() + "'" + "," +
                           " LossAction = " + "'" +getLossAction() + "'" + "," +
                           " Coeff = " + "'" +getCoeff() + "'" + "," +
                           " LossType = " + "'" +getLossType() + "'" + "," +
                           " IdentifyType = " + "'" +getIdentifyType() + "'" + "," +
                           " IdentifyNumber = " + "'" +getIdentifyNumber() + "'" + "," +
                           " Remark = " + "'" +getRemark() + "'" + "," +
                           " Flag = " + "'" +getFlag() + "', " +
                           
                           " AdjustFlag = '" + getAdjustFlag() + "', " +
                           
                           
                           " DecisionCode = '" + getDecisionCode() + "', " +
                           " DecisionTypeCode = '" + getDecisionTypeCode() + "', " +
                           " LicensePlateNo = '" + getLicensePlateNo() + "', " +
                           " LicensePlateTypeCode = '" + getLicensePlateTypeCode() + "', " +
                           " JurisdictionAgencyCode = '" + getJurisdictionAgencyCode() + "', " +
                           
                           
                           " PeccancyNumber = '" + getPeccancyNumber() + "', " +
                           
                           
                           " ProcessingStatus = '" + getProcessingStatus() + "', " +
                           
       			           
                           " DriverName = '" + getDriverName() + "', " +
                           " PeccancyDetail = '" + getPeccancyDetail() + "', " +
       			           
                           
                           " VouchCode = '" + getVouchCode() + "', " +
                           " SanctionPerson = '" + getSanctionPerson() + "', " +
                           " SanctionDate = '" + getSanctionDate() + "', " +
                           
                           
                           " MonitorId = '" + getMonitorId() + "', " +
                           
                           " LossAcceptDate = '" + getAcceptDate() + "'" +  
                           " Where DemandNo = " + "'" +getDemandNo() + "'" +
                            " And SerialNo = " + "'" +getSerialNo() + "'";
        			
        dbpool.update(strSQL);
    }

    /**
     * 更新一条记录
     * @throws Exception
     */       
    public void update() throws Exception{
        DbPool dbpool = new DbPool();
        
        try {
            
            String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
            if ("1".equals(strSwitch)) {
                dbpool.open("platformNewDataSource");
            } else {
                dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
            }
            
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

    public int getInfo(DbPool dbpool,String demandNo,String serialNo) throws Exception{
        int intResult = 0;
        String strSQL = " Select * From CIInsureDemandLoss Where DemandNo = " + "'" + demandNo + "'" +
                            " And SerialNo = " + "'" + serialNo + "'";
        ResultSet resultSet = dbpool.query(strSQL);
        if(resultSet.next()){
            setDemandNo(resultSet.getString("demandNo"));
            setSerialNo(resultSet.getString("serialNo"));
            setLossTime(resultSet.getString("lossTime"));
            setLossAddress(resultSet.getString("lossAddress"));
            setLossAction(resultSet.getString("lossAction"));
            setCoeff(resultSet.getString("coeff"));
            setLossType(resultSet.getString("lossType"));
            setIdentifyType(resultSet.getString("identifyType"));
            setIdentifyNumber(resultSet.getString("identifyNumber"));
            setRemark(resultSet.getString("remark"));
            setFlag(resultSet.getString("flag"));
            
            setAdjustFlag(resultSet.getString("adjustFlag"));
            
            
            setDecisionCode(resultSet.getString("DecisionCode"));
            setDecisionTypeCode(resultSet.getString("DecisionTypeCode"));
            setLicensePlateNo(resultSet.getString("LicensePlateNo"));
            setLicensePlateTypeCode(resultSet.getString("LicensePlateTypeCode"));
            setJurisdictionAgencyCode(resultSet.getString("JurisdictionAgencyCode"));
            
            
            setPeccancyNumber(resultSet.getString("PeccancyNumber"));
            
            
            setProcessingStatus(resultSet.getString("ProcessingStatus"));
            
	        
            setDriverName(resultSet.getString("DriverName"));
            setPeccancyDetail(resultSet.getString("PeccancyDetail"));
	        
            
            setVouchCode(resultSet.getString("VouchCode"));
            setSanctionPerson(resultSet.getString("SanctionPerson"));
            setSanctionDate(resultSet.getString("SanctionDate"));
            
            
            setMonitorId(resultSet.getString("monitorId"));
            
            setAcceptDate(resultSet.getString("lossAcceptDate"));
            
            intResult = 0;
        }
        else{
            intResult = 100;
        }
        resultSet.close();
        return intResult;
    }

    public int getInfo(String demandNo,String serialNo) throws Exception{
        int intResult = 0;
        DbPool dbpool = new DbPool();
        String strDataSource = SysConfig.CONST_DDCCDATASOURCE;
        
        try {
            
            String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
            if ("1".equals(strSwitch)) {
                dbpool.open("platformNewDataSource");
            } else {
                dbpool.open(strDataSource);
            }
            
            intResult=getInfo(dbpool,demandNo,serialNo);
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
        String strSQL = " SELECT COUNT(*) FROM CIInsureDemandLoss WHERE "+ strWhere;
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
            
            String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
            if ("1".equals(strSwitch)) {
                dbpool.open("platformNewDataSource");
            } else {
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
            
            String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
            if ("1".equals(strSwitch)) {
                dbpool.open("platformNewDataSource");
            } else {
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
     * 查询满足模糊查询条件的记录数,从历史信息库查询
     * @param strSQL  SQLStatement
     * @return 满足模糊查询条件的记录数
     * @throws Exception
     */
    public int getCountHistory(String strWhere) throws
        Exception{
        int intCount = 0;
        DbPool dbpool = new DbPool();
        
        try {
            
            String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
            if ("1".equals(strSwitch)) {
                dbpool.open("platformNewDataSource");
            } else {
                dbpool.open(SysConfig.CONST_HISTORYDATASOURCE);
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
     * 根据SQL语句获取结果集，从历史信息库查询
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
            
            String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
            if ("1".equals(strSwitch)) {
                dbpool.open("platformNewDataSource");
            } else {
                dbpool.open(SysConfig.CONST_HISTORYDATASOURCE);
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
        CIInsureDemandLossSchema cIInsureDemandLossSchema = null;
        ResultSet resultSet = dbpool.query(strSQL);
        while(resultSet.next())
        {
            cIInsureDemandLossSchema = new CIInsureDemandLossSchema();
            cIInsureDemandLossSchema.setDemandNo(resultSet.getString("demandNo"));
            cIInsureDemandLossSchema.setSerialNo(resultSet.getString("serialNo"));
            cIInsureDemandLossSchema.setLossTime(resultSet.getString("lossTime"));
            cIInsureDemandLossSchema.setLossAddress(resultSet.getString("lossAddress"));
            cIInsureDemandLossSchema.setLossAction(resultSet.getString("lossAction"));
            cIInsureDemandLossSchema.setCoeff(resultSet.getString("coeff"));
            cIInsureDemandLossSchema.setLossType(resultSet.getString("lossType"));
            cIInsureDemandLossSchema.setIdentifyType(resultSet.getString("identifyType"));
            cIInsureDemandLossSchema.setIdentifyNumber(resultSet.getString("identifyNumber"));
            cIInsureDemandLossSchema.setRemark(resultSet.getString("remark"));
            cIInsureDemandLossSchema.setFlag(resultSet.getString("flag"));
            
            cIInsureDemandLossSchema.setAdjustFlag(resultSet.getString("AdjustFlag"));
            
            
            cIInsureDemandLossSchema.setDecisionCode(resultSet.getString("DecisionCode"));
            cIInsureDemandLossSchema.setDecisionTypeCode(resultSet.getString("DecisionTypeCode"));
            cIInsureDemandLossSchema.setLicensePlateNo(resultSet.getString("LicensePlateNo"));
            cIInsureDemandLossSchema.setLicensePlateTypeCode(resultSet.getString("LicensePlateTypeCode"));
            cIInsureDemandLossSchema.setJurisdictionAgencyCode(resultSet.getString("JurisdictionAgencyCode"));
            
            
            cIInsureDemandLossSchema. setPeccancyNumber(resultSet.getString("PeccancyNumber"));
            
            
            cIInsureDemandLossSchema. setProcessingStatus(resultSet.getString("ProcessingStatus"));
            
	        
            cIInsureDemandLossSchema.setDriverName(resultSet.getString("DriverName"));
            cIInsureDemandLossSchema.setPeccancyDetail(resultSet.getString("PeccancyDetail"));
	        
            
            cIInsureDemandLossSchema.setVouchCode(resultSet.getString("VouchCode"));
            cIInsureDemandLossSchema.setSanctionPerson(resultSet.getString("SanctionPerson"));
            cIInsureDemandLossSchema.setSanctionDate(resultSet.getString("SanctionDate"));
            
            
            cIInsureDemandLossSchema.setMonitorId(resultSet.getString("monitorId"));
            
            cIInsureDemandLossSchema.setAcceptDate(resultSet.getString("lossAcceptDate"));
            
            vector.add(cIInsureDemandLossSchema);
        }
        resultSet.close();
        return vector;
    }
    /**
     * 根据条件获取查询数据条数
     * @author wanghe 20100104
     * @param dbpool
     * @param strWhere
     * @param iWhereValue
     * @return
     * @throws Exception
     */
    public int getCount(DbPool dbpool,String strWhere,ArrayList iWhereValue)
    throws Exception{
      int intCount = 0;
      String strSQL = " SELECT COUNT(1) FROM CIInsureDemandLoss WHERE "+ strWhere;
      ResultSet resultSet = null;
      if(iWhereValue != null)
      {
          dbpool.prepareInnerStatement(strSQL);
          for(int i=0;i<iWhereValue.size();i++)
          {
            
        	  if(iWhereValue.get(i)!=null){
        		  dbpool.setString(i+1,(iWhereValue.get(i)).toString());
        	  }else{
        		  dbpool.setString(i+1,""); 
        	  }
        	  
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
     * 根据SQL语句获取结果集
     * @author wanghe 20110104
     * @param dbpool      数据库连接池
     * @param strSQL      SQL语句
     * @param iWhereValue 查询条件各属性值
     * @return Vector 查询结果记录集
     * @throws SQLException    数据库操作异常类
     * @throws NamingException 名字异常类
     */
    public Vector findByConditions(DbPool dbpool,String strSQL ,ArrayList iWhereValue) throws
    SQLException,NamingException,Exception{
        Vector vector = new Vector();
        CIInsureDemandLossSchema cIInsureDemandLossSchema = null;
        ResultSet resultSet = null;
        if(iWhereValue != null)
        {
          dbpool.prepareInnerStatement(strSQL);
          for(int i=0;i<iWhereValue.size();i++)
            {
            
        	  if(iWhereValue.get(i)!=null){
        		  dbpool.setString(i+1,(iWhereValue.get(i)).toString());
        	  }else{
        		  dbpool.setString(i+1,""); 
        	  }
        	  
            }
          resultSet = dbpool.executePreparedQuery();
        }else
        {
          resultSet = dbpool.query(strSQL);
        }
        while(resultSet.next())
        {
            cIInsureDemandLossSchema = new CIInsureDemandLossSchema();
            cIInsureDemandLossSchema.setDemandNo(resultSet.getString("demandNo"));
            cIInsureDemandLossSchema.setSerialNo(resultSet.getString("serialNo"));
            cIInsureDemandLossSchema.setLossTime(resultSet.getString("lossTime"));
            cIInsureDemandLossSchema.setLossAddress(resultSet.getString("lossAddress"));
            cIInsureDemandLossSchema.setLossAction(resultSet.getString("lossAction"));
            cIInsureDemandLossSchema.setCoeff(resultSet.getString("coeff"));
            cIInsureDemandLossSchema.setLossType(resultSet.getString("lossType"));
            cIInsureDemandLossSchema.setIdentifyType(resultSet.getString("identifyType"));
            cIInsureDemandLossSchema.setIdentifyNumber(resultSet.getString("identifyNumber"));
            cIInsureDemandLossSchema.setRemark(resultSet.getString("remark"));
            cIInsureDemandLossSchema.setFlag(resultSet.getString("flag"));
            
            cIInsureDemandLossSchema.setAdjustFlag(resultSet.getString("AdjustFlag"));
            
            
            cIInsureDemandLossSchema.setDecisionCode(resultSet.getString("DecisionCode"));
            cIInsureDemandLossSchema.setDecisionTypeCode(resultSet.getString("DecisionTypeCode"));
            cIInsureDemandLossSchema.setLicensePlateNo(resultSet.getString("LicensePlateNo"));
            cIInsureDemandLossSchema.setLicensePlateTypeCode(resultSet.getString("LicensePlateTypeCode"));
            cIInsureDemandLossSchema.setJurisdictionAgencyCode(resultSet.getString("JurisdictionAgencyCode"));
            
            
            cIInsureDemandLossSchema. setPeccancyNumber(resultSet.getString("PeccancyNumber"));
            
            
            cIInsureDemandLossSchema. setProcessingStatus(resultSet.getString("ProcessingStatus"));
            
	        
            cIInsureDemandLossSchema.setDriverName(resultSet.getString("DriverName"));
            cIInsureDemandLossSchema.setPeccancyDetail(resultSet.getString("PeccancyDetail"));
	        
            
            cIInsureDemandLossSchema.setVouchCode(resultSet.getString("VouchCode"));
            cIInsureDemandLossSchema.setSanctionPerson(resultSet.getString("SanctionPerson"));
            cIInsureDemandLossSchema.setSanctionDate(resultSet.getString("SanctionDate"));
            
            
            cIInsureDemandLossSchema.setMonitorId(resultSet.getString("monitorId"));
            
            cIInsureDemandLossSchema.setAcceptDate(resultSet.getString("lossAcceptDate"));
            
            vector.add(cIInsureDemandLossSchema);
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
