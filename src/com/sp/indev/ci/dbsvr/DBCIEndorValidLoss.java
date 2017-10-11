package com.sp.indiv.ci.dbsvr;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.util.*;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.indiv.ci.schema.CIEndorValidLossSchema;

/**
 * 定义CIEndorValidLoss的DB类
 * 从pdm中取数据库信息,根据数据库表生成对应的DB类
 * <p>Title: SinosoftJavaTools v1.4</p>
 * <p>Description: 中科软Java源码生成工具</p>
 * <p>Company: Sinosoft</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>@createdate 2010-11-05</p>
 * @FirstAuthor: Zhouxianli(JavaTools v1.0)
 * @SecondAuthor: Yangkun(JavaTools v1.1->v1.2->v1.3->v1.4)
 * @LastVersion: v1.4.2
 * @UpdateList: 1.新增DB类自动生成带绑定变量insert();update();delete();getInfo();等方法;
 *              2.新增对CIXXXXX平台类的生成处理;
 *              3.优化getCount()方法select count(*) from tableName为select count(1) from tableName;
 */
public class DBCIEndorValidLoss extends CIEndorValidLossSchema{
    /**
     * 构造函数
     */       
    public DBCIEndorValidLoss(){
    }

    /**
     * 插入一条记录
     * @param dbpool dbpool
     * @throws Exception
     */       
    public void insert(DbPool dbpool) throws Exception{
        String strSQL = " Insert Into CIEndorValidLoss(" + 
                           " DemandNo," + 
                           " SerialNo," + 
                           " PolicyNo," + 
                           " LossTime," + 
                           " LossAddress," + 
                           " LossAction," + 
                           " Coeff," + 
                           " LossType," + 
                           " IdentifyType," + 
                           " IdentifyNumber," + 
                           " Remark," + 
                           " Flag," + 
                           " LossAcceptDate," + 
                           " AdjustFlag," + 
                           " DecisionCode," + 
                           " LicensePlateNo," + 
                           " LicensePlateTypeCode," + 
                           " JurisdictionAgencyCode," + 
                           " DecisionTypeCode," + 
                           
                           " DriverName," + 
                           " VouchCode," + 
                           " SanctionPerson," + 
                           " SanctionDate," + 
                           
                           " PeccancyNumber) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        dbpool.prepareInnerStatement(strSQL);
        int index = 1;
        dbpool.setString(index++,getDemandNo());
        dbpool.setString(index++,getSerialNo());
        dbpool.setString(index++,getPolicyNo());
        dbpool.setString(index++,getLossTime());
        dbpool.setString(index++,getLossAddress());
        dbpool.setString(index++,getLossAction());
        dbpool.setString(index++,getCoeff());
        dbpool.setString(index++,getLossType());
        dbpool.setString(index++,getIdentifyType());
        dbpool.setString(index++,getIdentifyNumber());
        dbpool.setString(index++,getRemark());
        dbpool.setString(index++,getFlag());
        dbpool.setString(index++,getLossAcceptDate());
        dbpool.setString(index++,getAdjustFlag());
        dbpool.setString(index++,getDecisionCode());
        dbpool.setString(index++,getLicensePlateNo());
        dbpool.setString(index++,getLicensePlateTypeCode());
        dbpool.setString(index++,getJurisdictionAgencyCode());
        dbpool.setString(index++,getDecisionTypeCode());
        
        dbpool.setString(index++,getDriverName());
        dbpool.setString(index++,getVouchCode());
        dbpool.setString(index++,getSanctionPerson());
        dbpool.setString(index++,getSanctionDate());
        
        dbpool.setString(index++,getPeccancyNumber());
        dbpool.executePreparedUpdate();
        dbpool.closePreparedStatement();
    }

    /**
     * 插入一条记录
     * @throws Exception
     */       
    public void insert() throws Exception{
        DbPool dbpool = new DbPool();
        
        try{
          dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
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
        finally{
          dbpool.close();
        }
    }

    public void delete(DbPool dbpool,String demandNo,String serialNo) throws Exception{
        String strSQL = " Delete From CIEndorValidLoss Where DemandNo = ?" +
                           " And SerialNo = ?";
        dbpool.prepareInnerStatement(strSQL);
        int index = 1;
        dbpool.setString(index++,demandNo);
        dbpool.setString(index++,serialNo);
        dbpool.executePreparedUpdate();
        dbpool.closePreparedStatement();
    }

    public void delete(String demandNo,String serialNo) throws Exception{
        DbPool dbpool = new DbPool();
        
        try{
          dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
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
        finally{
          dbpool.close();
        }
    }

    public void update(DbPool dbpool) throws Exception{
        String strSQL = " Update CIEndorValidLoss Set" +
                           " DemandNo = ?," +
                           " SerialNo = ?," +
                           " PolicyNo = ?," +
                           " LossTime = ?," +
                           " LossAddress = ?," +
                           " LossAction = ?," +
                           " Coeff = ?," +
                           " LossType = ?," +
                           " IdentifyType = ?," +
                           " IdentifyNumber = ?," +
                           " Remark = ?," +
                           " Flag = ?," +
                           " LossAcceptDate = ?," +
                           " AdjustFlag = ?," +
                           " DecisionCode = ?," +
                           " LicensePlateNo = ?," +
                           " LicensePlateTypeCode = ?," +
                           " JurisdictionAgencyCode = ?," +
                           " DecisionTypeCode = ?," +
                           
                           " DriverName = ?," +
                           " VouchCode = ?," +
                           " SanctionPerson = ?," +
                           " SanctionDate = ?," +
                           
                           " PeccancyNumber = ?" +
                           " Where DemandNo = ?" +
                            " And SerialNo = ?";
        dbpool.prepareInnerStatement(strSQL);
        int index = 1;
        dbpool.setString(index++,getDemandNo());
        dbpool.setString(index++,getSerialNo());
        dbpool.setString(index++,getPolicyNo());
        dbpool.setString(index++,getLossTime());
        dbpool.setString(index++,getLossAddress());
        dbpool.setString(index++,getLossAction());
        dbpool.setString(index++,getCoeff());
        dbpool.setString(index++,getLossType());
        dbpool.setString(index++,getIdentifyType());
        dbpool.setString(index++,getIdentifyNumber());
        dbpool.setString(index++,getRemark());
        dbpool.setString(index++,getFlag());
        dbpool.setString(index++,getLossAcceptDate());
        dbpool.setString(index++,getAdjustFlag());
        dbpool.setString(index++,getDecisionCode());
        dbpool.setString(index++,getLicensePlateNo());
        dbpool.setString(index++,getLicensePlateTypeCode());
        dbpool.setString(index++,getJurisdictionAgencyCode());
        dbpool.setString(index++,getDecisionTypeCode());
        
        dbpool.setString(index++,getDriverName());
        dbpool.setString(index++,getVouchCode());
        dbpool.setString(index++,getSanctionPerson());
        dbpool.setString(index++,getSanctionDate());
        
        dbpool.setString(index++,getPeccancyNumber());
        dbpool.setString(index++,getDemandNo());
        dbpool.setString(index++,getSerialNo());
        dbpool.executePreparedUpdate();
        dbpool.closePreparedStatement();
    }

    /**
     * 更新一条记录
     * @throws Exception
     */       
    public void update() throws Exception{
        DbPool dbpool = new DbPool();
        
        try{
          dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
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
        finally{
          dbpool.close();
        }
    }

    public int getInfo(DbPool dbpool,String demandNo,String serialNo) throws Exception{
        int intResult = 0;
        String strSQL = " Select * From CIEndorValidLoss Where DemandNo = ?" +
                            " And SerialNo = ?";
        dbpool.prepareInnerStatement(strSQL);
        int index = 1;
        dbpool.setString(index++,demandNo);
        dbpool.setString(index++,serialNo);
        ResultSet resultSet = dbpool.executePreparedQuery();
        if(resultSet.next()){
          setDemandNo(resultSet.getString("demandNo"));
          setSerialNo(resultSet.getString("serialNo"));
          setPolicyNo(resultSet.getString("policyNo"));
          setLossTime(resultSet.getString("lossTime"));
          setLossAddress(resultSet.getString("lossAddress"));
          setLossAction(resultSet.getString("lossAction"));
          setCoeff(resultSet.getString("coeff"));
          setLossType(resultSet.getString("lossType"));
          setIdentifyType(resultSet.getString("identifyType"));
          setIdentifyNumber(resultSet.getString("identifyNumber"));
          setRemark(resultSet.getString("remark"));
          setFlag(resultSet.getString("flag"));
          setLossAcceptDate(resultSet.getString("lossAcceptDate"));
          setAdjustFlag(resultSet.getString("adjustFlag"));
          setDecisionCode(resultSet.getString("decisionCode"));
          setLicensePlateNo(resultSet.getString("licensePlateNo"));
          setLicensePlateTypeCode(resultSet.getString("licensePlateTypeCode"));
          setJurisdictionAgencyCode(resultSet.getString("jurisdictionAgencyCode"));
          setDecisionTypeCode(resultSet.getString("decisionTypeCode"));
          
          setDriverName(resultSet.getString("DriverName"));
          setVouchCode(resultSet.getString("vouchCode"));
          setSanctionPerson(resultSet.getString("sanctionPerson"));
          setSanctionDate(resultSet.getString("sanctionDate"));
          
          setPeccancyNumber(resultSet.getString("peccancyNumber"));
          intResult = 0;
        }
        else{
          intResult = 100;
        }
          resultSet.close();
          dbpool.closePreparedStatement();
          return intResult;
    }

    public int getInfo(String demandNo,String serialNo) throws Exception{
        int intResult = 0;
        DbPool dbpool = new DbPool();
        
        try{
          dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
          intResult=getInfo(dbpool,demandNo,serialNo);
          dbpool.close();
        }
        catch(Exception exception){
          dbpool.close();
          throw exception;
        }
        finally{
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
        String strSQL = " SELECT COUNT(1) FROM CIEndorValidLoss WHERE "+ strWhere;
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
        
        try{
          dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
          intCount=getCount(dbpool,strWhere);
          dbpool.close();
        }
        catch(Exception exception){
          dbpool.close();
          throw exception;
        }
        finally{
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
        
        try{
          dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
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
        finally{
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
        CIEndorValidLossSchema cIEndorValidLossSchema = null;
        ResultSet resultSet = dbpool.query(strSQL);
        while(resultSet.next())
        {
          cIEndorValidLossSchema = new CIEndorValidLossSchema();
          cIEndorValidLossSchema.setDemandNo(resultSet.getString("demandNo"));
          cIEndorValidLossSchema.setSerialNo(resultSet.getString("serialNo"));
          cIEndorValidLossSchema.setPolicyNo(resultSet.getString("policyNo"));
          cIEndorValidLossSchema.setLossTime(resultSet.getString("lossTime"));
          cIEndorValidLossSchema.setLossAddress(resultSet.getString("lossAddress"));
          cIEndorValidLossSchema.setLossAction(resultSet.getString("lossAction"));
          cIEndorValidLossSchema.setCoeff(resultSet.getString("coeff"));
          cIEndorValidLossSchema.setLossType(resultSet.getString("lossType"));
          cIEndorValidLossSchema.setIdentifyType(resultSet.getString("identifyType"));
          cIEndorValidLossSchema.setIdentifyNumber(resultSet.getString("identifyNumber"));
          cIEndorValidLossSchema.setRemark(resultSet.getString("remark"));
          cIEndorValidLossSchema.setFlag(resultSet.getString("flag"));
          cIEndorValidLossSchema.setLossAcceptDate(resultSet.getString("lossAcceptDate"));
          cIEndorValidLossSchema.setAdjustFlag(resultSet.getString("adjustFlag"));
          cIEndorValidLossSchema.setDecisionCode(resultSet.getString("decisionCode"));
          cIEndorValidLossSchema.setLicensePlateNo(resultSet.getString("licensePlateNo"));
          cIEndorValidLossSchema.setLicensePlateTypeCode(resultSet.getString("licensePlateTypeCode"));
          cIEndorValidLossSchema.setJurisdictionAgencyCode(resultSet.getString("jurisdictionAgencyCode"));
          cIEndorValidLossSchema.setDecisionTypeCode(resultSet.getString("decisionTypeCode"));
          
          cIEndorValidLossSchema.setDriverName(resultSet.getString("DriverName"));
          cIEndorValidLossSchema.setVouchCode(resultSet.getString("vouchCode"));
          cIEndorValidLossSchema.setSanctionPerson(resultSet.getString("sanctionPerson"));
          cIEndorValidLossSchema.setSanctionDate(resultSet.getString("sanctionDate"));
          
          cIEndorValidLossSchema.setPeccancyNumber(resultSet.getString("peccancyNumber"));
          vector.add(cIEndorValidLossSchema);
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
