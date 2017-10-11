package com.sp.indiv.ci.dbsvr;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import javax.naming.NamingException;

import com.sp.indiv.ci.schema.CICarShipTaxQGEndorseSchema;
import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;
import com.sp.utility.string.ChgData;

/**
 * 定义CICarShipTaxQGEndorse-全国车船税基本类型批改查询表的DB类
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>@createdate 2009-04-09</p>
 * @author Zhouxianli(JavaTools v1.0)
 * @updateauthor yangkun(JavaTools v1.1 - v1.2)
 * @lastversion v1.2
 */
public class DBCICarShipTaxQGEndorse extends CICarShipTaxQGEndorseSchema{
    /**
     * 构造函数
     */       
    public DBCICarShipTaxQGEndorse(){
    }

    /**
     * 插入一条记录
     * @param dbpool dbpool
     * @throws Exception
     */       
    public void insert(DbPool dbpool) throws Exception{
        String strSQL = " Insert Into CICarShipTaxQGEndorse(" + 
                           " EndorseNo," + 
                           " DemandNo," + 
                           " PolicyNo," + 
                           " TaxType," + 
                           " SerialNo," + 
                           " TaxTermTypeCode," + 
                           " TaxConditionCode," + 
                           " TaxRegistryNumber," + 
                           " TaxPayerName," + 
                           " TaxPayerIdentificationCode," + 
                           " TaxLocationCode," + 
                           " TaxStartDate," + 
                           " TaxEndDate," + 
                           " DeclareDate," + 
                           " TaxDepartmentCode," + 
                           " TaxDepartment," + 
                           " TaxDocumentNumber," + 
                           " AnnualTaxAmount," + 
                           " AppliedArea," + 
                           " TaxRateIdentifier," + 
                           " TaxItemDetailCode," + 
                           " TaxUnitTypeCode," + 
                           " UnitRate," + 
                           " TaxRateStartDate," + 
                           " TaxRateEndDate," + 
                           " DeductionDueCode," + 
                           " DeductionDueType," + 
                           " DeductionDueProportion," + 
                           " Deduction," + 
                           " DeductionDocumentNumber," + 
                           " DeductionDepartmentCode," + 
                           " DeductionDepartment," + 
                           " TaxDue," + 
                           " ExceedDate," + 
                           " ExceedDaysCount," + 
                           " OverDue," + 
                           " TotalAmount," + 
                           " AnnualTaxDue," + 
                           " SumTaxDefault," + 
                           " SumOverdue," + 
                           " SumTax," + 
                       		
                           " TaxAmountFlag," +
                           " DeclareStatusIA," +
                           " CalcTaxFlag," +
                           " TaxPrintNo," +
                       		
                           " TaxDescription) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        dbpool.prepareInnerStatement(strSQL);
        int index = 1;
        dbpool.setString(index++,getEndorseNo());
        dbpool.setString(index++,getDemandNo());
        dbpool.setString(index++,getPolicyNo());
        dbpool.setString(index++,getTaxType());
        dbpool.setString(index++,getSerialNo());
        dbpool.setString(index++,getTaxTermTypeCode());
        dbpool.setString(index++,getTaxConditionCode());
        dbpool.setString(index++,getTaxRegistryNumber());
        dbpool.setString(index++,getTaxPayerName());
        dbpool.setString(index++,getTaxPayerIdentificationCode());
        dbpool.setString(index++,getTaxLocationCode());
        dbpool.setString(index++,getTaxStartDate());
        dbpool.setString(index++,getTaxEndDate());
        dbpool.setString(index++,getDeclareDate());
        dbpool.setString(index++,getTaxDepartmentCode());
        dbpool.setString(index++,getTaxDepartment());
        dbpool.setString(index++,getTaxDocumentNumber());
        dbpool.setString(index++,getAnnualTaxAmount());
        dbpool.setString(index++,getAppliedArea());
        dbpool.setString(index++,getTaxRateIdentifier());
        dbpool.setString(index++,getTaxItemDetailCode());
        dbpool.setString(index++,getTaxUnitTypeCode());
        dbpool.setString(index++,getUnitRate());
        dbpool.setString(index++,getTaxRateStartDate());
        dbpool.setString(index++,getTaxRateEndDate());
        dbpool.setString(index++,getDeductionDueCode());
        dbpool.setString(index++,getDeductionDueType());
        dbpool.setString(index++,getDeductionDueProportion());
        dbpool.setString(index++,getDeduction());
        dbpool.setString(index++,getDeductionDocumentNumber());
        dbpool.setString(index++,getDeductionDepartmentCode());
        dbpool.setString(index++,getDeductionDepartment());
        dbpool.setString(index++,getTaxDue());
        dbpool.setString(index++,getExceedDate());
        dbpool.setString(index++,getExceedDaysCount());
        dbpool.setString(index++,getOverDue());
        dbpool.setString(index++,getTotalAmount());
        dbpool.setString(index++,getAnnualTaxDue());
        dbpool.setString(index++,getSumTaxDefault());
        dbpool.setString(index++,getSumOverdue());
        dbpool.setString(index++,getSumTax());
    	
        dbpool.setString(index++,getTaxAmountFlag());
        dbpool.setString(index++,getDeclareStatusIA());
        dbpool.setString(index++,getCalcTaxFlag());
        dbpool.setString(index++,getTaxPrintNo());
    	
        dbpool.setString(index++,getTaxDescription());
        dbpool.executePreparedUpdate();
        dbpool.closePreparedStatement();
    }

    /**
     * 插入一条记录
     * @throws Exception
     */       
    public void insert() throws Exception{
        DbPool dbpool = new DbPool();
        
        dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        try{
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

    public void delete(DbPool dbpool,String demandNo,String taxType,String serialNo) throws Exception{
        String strSQL = " Delete From CICarShipTaxQGEndorse Where DemandNo = ?" +
                           " And TaxType = ?" +
                           " And SerialNo = ?";
        dbpool.prepareInnerStatement(strSQL);
        int index = 1;
        dbpool.setString(index++,demandNo);
        dbpool.setString(index++,taxType);
        dbpool.setString(index++,serialNo);
        dbpool.executePreparedUpdate();
        dbpool.closePreparedStatement();
    }

    public void delete(String demandNo,String taxType,String serialNo) throws Exception{
        DbPool dbpool = new DbPool();
        
        dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        try{
            dbpool.beginTransaction();
            delete(dbpool,demandNo,taxType,serialNo);
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
        String strSQL = " Update CICarShipTaxQGEndorse Set" +
                           " EndorseNo = ?," +
                           " DemandNo = ?," +
                           " PolicyNo = ?," +
                           " TaxType = ?," +
                           " SerialNo = ?," +
                           " TaxTermTypeCode = ?," +
                           " TaxConditionCode = ?," +
                           " TaxRegistryNumber = ?," +
                           " TaxPayerName = ?," +
                           " TaxPayerIdentificationCode = ?," +
                           " TaxLocationCode = ?," +
                           " TaxStartDate = ?," +
                           " TaxEndDate = ?," +
                           " DeclareDate = ?," +
                           " TaxDepartmentCode = ?," +
                           " TaxDepartment = ?," +
                           " TaxDocumentNumber = ?," +
                           " AnnualTaxAmount = ?," +
                           " AppliedArea = ?," +
                           " TaxRateIdentifier = ?," +
                           " TaxItemDetailCode = ?," +
                           " TaxUnitTypeCode = ?," +
                           " UnitRate = ?," +
                           " TaxRateStartDate = ?," +
                           " TaxRateEndDate = ?," +
                           " DeductionDueCode = ?," +
                           " DeductionDueType = ?," +
                           " DeductionDueProportion = ?," +
                           " Deduction = ?," +
                           " DeductionDocumentNumber = ?," +
                           " DeductionDepartmentCode = ?," +
                           " DeductionDepartment = ?," +
                           " TaxDue = ?," +
                           " ExceedDate = ?," +
                           " ExceedDaysCount = ?," +
                           " OverDue = ?," +
                           " TotalAmount = ?," +
                           " AnnualTaxDue = ?," +
                           " SumTaxDefault = ?," +
                           " SumOverdue = ?," +
                           " SumTax = ?," +
                           
                           " TaxAmountFlag = ?," +
                           " DeclareStatusIA = ?," +
                           " CalcTaxFlag = ?," +
                           " TaxPrintNo = ?," +
                           
                           " TaxDescription = ?" +
                           " Where DemandNo = ?" +
                            " And TaxType = ?" +
                            " And SerialNo = ?";
        dbpool.prepareInnerStatement(strSQL);
        int index = 1;
        dbpool.setString(index++,getEndorseNo());
        dbpool.setString(index++,getDemandNo());
        dbpool.setString(index++,getPolicyNo());
        dbpool.setString(index++,getTaxType());
        dbpool.setString(index++,getSerialNo());
        dbpool.setString(index++,getTaxTermTypeCode());
        dbpool.setString(index++,getTaxConditionCode());
        dbpool.setString(index++,getTaxRegistryNumber());
        dbpool.setString(index++,getTaxPayerName());
        dbpool.setString(index++,getTaxPayerIdentificationCode());
        dbpool.setString(index++,getTaxLocationCode());
        dbpool.setString(index++,getTaxStartDate());
        dbpool.setString(index++,getTaxEndDate());
        dbpool.setString(index++,getDeclareDate());
        dbpool.setString(index++,getTaxDepartmentCode());
        dbpool.setString(index++,getTaxDepartment());
        dbpool.setString(index++,getTaxDocumentNumber());
        dbpool.setString(index++,getAnnualTaxAmount());
        dbpool.setString(index++,getAppliedArea());
        dbpool.setString(index++,getTaxRateIdentifier());
        dbpool.setString(index++,getTaxItemDetailCode());
        dbpool.setString(index++,getTaxUnitTypeCode());
        dbpool.setString(index++,getUnitRate());
        dbpool.setString(index++,getTaxRateStartDate());
        dbpool.setString(index++,getTaxRateEndDate());
        dbpool.setString(index++,getDeductionDueCode());
        dbpool.setString(index++,getDeductionDueType());
        dbpool.setString(index++,getDeductionDueProportion());
        dbpool.setString(index++,getDeduction());
        dbpool.setString(index++,getDeductionDocumentNumber());
        dbpool.setString(index++,getDeductionDepartmentCode());
        dbpool.setString(index++,getDeductionDepartment());
        dbpool.setString(index++,getTaxDue());
        dbpool.setString(index++,getExceedDate());
        dbpool.setString(index++,getExceedDaysCount());
        dbpool.setString(index++,getOverDue());
        dbpool.setString(index++,getTotalAmount());
        dbpool.setString(index++,getAnnualTaxDue());
        dbpool.setString(index++,getSumTaxDefault());
        dbpool.setString(index++,getSumOverdue());
        dbpool.setString(index++,getSumTax());
    	
        dbpool.setString(index++,getTaxAmountFlag());
        dbpool.setString(index++,getDeclareStatusIA());
        dbpool.setString(index++,getCalcTaxFlag());
        dbpool.setString(index++,getTaxPrintNo());
    	
        dbpool.setString(index++,getTaxDescription());
        dbpool.setString(index++,getDemandNo());
        dbpool.setString(index++,getTaxType());
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
        
        dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        try{
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

    public int getInfo(DbPool dbpool,String demandNo,String taxType,String serialNo) throws Exception{
        int intResult = 0;
        String strSQL = " Select * From CICarShipTaxQGEndorse Where DemandNo = ?" +
                            " And TaxType = ?" +
                            " And SerialNo = ?";
        dbpool.prepareInnerStatement(strSQL);
        int index = 1;
        dbpool.setString(index++,demandNo);
        dbpool.setString(index++,taxType);
        dbpool.setString(index++,serialNo);
        ResultSet resultSet = dbpool.executePreparedQuery();
        if(resultSet.next()){
            setEndorseNo(resultSet.getString("endorseNo"));
            setDemandNo(resultSet.getString("demandNo"));
            setPolicyNo(resultSet.getString("policyNo"));
            setTaxType(resultSet.getString("taxType"));
            setSerialNo(resultSet.getString("serialNo"));
            setTaxTermTypeCode(resultSet.getString("taxTermTypeCode"));
            setTaxConditionCode(resultSet.getString("taxConditionCode"));
            setTaxRegistryNumber(resultSet.getString("taxRegistryNumber"));
            setTaxPayerName(resultSet.getString("taxPayerName"));
            setTaxPayerIdentificationCode(resultSet.getString("taxPayerIdentificationCode"));
            setTaxLocationCode(resultSet.getString("taxLocationCode"));
            setTaxStartDate("" + ChgData.nullToString(resultSet.getDate("taxStartDate")));
            setTaxEndDate("" + ChgData.nullToString(resultSet.getDate("taxEndDate")));
            setDeclareDate("" + ChgData.nullToString(resultSet.getDate("declareDate")));
            setTaxDepartmentCode(resultSet.getString("taxDepartmentCode"));
            setTaxDepartment(resultSet.getString("taxDepartment"));
            setTaxDocumentNumber(resultSet.getString("taxDocumentNumber"));
            setAnnualTaxAmount(resultSet.getString("annualTaxAmount"));
            setAppliedArea(resultSet.getString("appliedArea"));
            setTaxRateIdentifier(resultSet.getString("taxRateIdentifier"));
            setTaxItemDetailCode(resultSet.getString("taxItemDetailCode"));
            setTaxUnitTypeCode(resultSet.getString("taxUnitTypeCode"));
            setUnitRate(resultSet.getString("unitRate"));
            setTaxRateStartDate("" + ChgData.nullToString(resultSet.getDate("taxRateStartDate")));
            setTaxRateEndDate("" + ChgData.nullToString(resultSet.getDate("taxRateEndDate")));
            setDeductionDueCode(resultSet.getString("deductionDueCode"));
            setDeductionDueType(resultSet.getString("deductionDueType"));
            setDeductionDueProportion(resultSet.getString("deductionDueProportion"));
            setDeduction(resultSet.getString("deduction"));
            setDeductionDocumentNumber(resultSet.getString("deductionDocumentNumber"));
            setDeductionDepartmentCode(resultSet.getString("deductionDepartmentCode"));
            setDeductionDepartment(resultSet.getString("deductionDepartment"));
            setTaxDue(resultSet.getString("taxDue"));
            setExceedDate("" + ChgData.nullToString(resultSet.getDate("exceedDate")));
            setExceedDaysCount(resultSet.getString("exceedDaysCount"));
            setOverDue(resultSet.getString("overDue"));
            setTotalAmount(resultSet.getString("totalAmount"));
            setAnnualTaxDue(resultSet.getString("annualTaxDue"));
            setSumTaxDefault(resultSet.getString("sumTaxDefault"));
            setSumOverdue(resultSet.getString("sumOverdue"));
            setSumTax(resultSet.getString("sumTax"));
        	
            setTaxAmountFlag(resultSet.getString("TaxAmountFlag"));
            setDeclareStatusIA(resultSet.getString("DeclareStatusIA"));
            setCalcTaxFlag(resultSet.getString("CalcTaxFlag"));
            setTaxPrintNo(resultSet.getString("TaxPrintNo"));
        	
            setTaxDescription(resultSet.getString("taxDescription"));
            intResult = 0;
        }
        else{
            intResult = 100;
        }
        resultSet.close();
        dbpool.closePreparedStatement();
        return intResult;
    }

    public int getInfo(String demandNo,String taxType,String serialNo) throws Exception{
        int intResult = 0;
        DbPool dbpool = new DbPool();
        
        dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        try{
            intResult=getInfo(dbpool,demandNo,taxType,serialNo);
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
        String strSQL = " SELECT COUNT(*) FROM CICarShipTaxQGEndorse WHERE "+ strWhere;
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
        
        dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        try{
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
        
        dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        try{
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
        CICarShipTaxQGEndorseSchema ciCarShipTaxQGEndorseSchema = null;
        ResultSet resultSet = dbpool.query(strSQL);
        while(resultSet.next())
        {
            ciCarShipTaxQGEndorseSchema = new CICarShipTaxQGEndorseSchema();
            ciCarShipTaxQGEndorseSchema.setEndorseNo(resultSet.getString("endorseNo"));
            ciCarShipTaxQGEndorseSchema.setDemandNo(resultSet.getString("demandNo"));
            ciCarShipTaxQGEndorseSchema.setPolicyNo(resultSet.getString("policyNo"));
            ciCarShipTaxQGEndorseSchema.setTaxType(resultSet.getString("taxType"));
            ciCarShipTaxQGEndorseSchema.setSerialNo(resultSet.getString("serialNo"));
            ciCarShipTaxQGEndorseSchema.setTaxTermTypeCode(resultSet.getString("taxTermTypeCode"));
            ciCarShipTaxQGEndorseSchema.setTaxConditionCode(resultSet.getString("taxConditionCode"));
            ciCarShipTaxQGEndorseSchema.setTaxRegistryNumber(resultSet.getString("taxRegistryNumber"));
            ciCarShipTaxQGEndorseSchema.setTaxPayerName(resultSet.getString("taxPayerName"));
            ciCarShipTaxQGEndorseSchema.setTaxPayerIdentificationCode(resultSet.getString("taxPayerIdentificationCode"));
            ciCarShipTaxQGEndorseSchema.setTaxLocationCode(resultSet.getString("taxLocationCode"));
            ciCarShipTaxQGEndorseSchema.setTaxStartDate("" + resultSet.getDate("taxStartDate"));
            ciCarShipTaxQGEndorseSchema.setTaxEndDate("" + resultSet.getDate("taxEndDate"));
            ciCarShipTaxQGEndorseSchema.setDeclareDate("" + resultSet.getDate("declareDate"));
            ciCarShipTaxQGEndorseSchema.setTaxDepartmentCode(resultSet.getString("taxDepartmentCode"));
            ciCarShipTaxQGEndorseSchema.setTaxDepartment(resultSet.getString("taxDepartment"));
            ciCarShipTaxQGEndorseSchema.setTaxDocumentNumber(resultSet.getString("taxDocumentNumber"));
            ciCarShipTaxQGEndorseSchema.setAnnualTaxAmount(resultSet.getString("annualTaxAmount"));
            ciCarShipTaxQGEndorseSchema.setAppliedArea(resultSet.getString("appliedArea"));
            ciCarShipTaxQGEndorseSchema.setTaxRateIdentifier(resultSet.getString("taxRateIdentifier"));
            ciCarShipTaxQGEndorseSchema.setTaxItemDetailCode(resultSet.getString("taxItemDetailCode"));
            ciCarShipTaxQGEndorseSchema.setTaxUnitTypeCode(resultSet.getString("taxUnitTypeCode"));
            ciCarShipTaxQGEndorseSchema.setUnitRate(resultSet.getString("unitRate"));
            ciCarShipTaxQGEndorseSchema.setTaxRateStartDate("" +resultSet.getDate("taxRateStartDate"));
            ciCarShipTaxQGEndorseSchema.setTaxRateEndDate("" +resultSet.getDate("taxRateEndDate"));
            ciCarShipTaxQGEndorseSchema.setDeductionDueCode(resultSet.getString("deductionDueCode"));
            ciCarShipTaxQGEndorseSchema.setDeductionDueType(resultSet.getString("deductionDueType"));
            ciCarShipTaxQGEndorseSchema.setDeductionDueProportion(resultSet.getString("deductionDueProportion"));
            ciCarShipTaxQGEndorseSchema.setDeduction(resultSet.getString("deduction"));
            ciCarShipTaxQGEndorseSchema.setDeductionDocumentNumber(resultSet.getString("deductionDocumentNumber"));
            ciCarShipTaxQGEndorseSchema.setDeductionDepartmentCode(resultSet.getString("deductionDepartmentCode"));
            ciCarShipTaxQGEndorseSchema.setDeductionDepartment(resultSet.getString("deductionDepartment"));
            ciCarShipTaxQGEndorseSchema.setTaxDue(resultSet.getString("taxDue"));
            ciCarShipTaxQGEndorseSchema.setExceedDate("" + resultSet.getDate("exceedDate"));
            ciCarShipTaxQGEndorseSchema.setExceedDaysCount(resultSet.getString("exceedDaysCount"));
            ciCarShipTaxQGEndorseSchema.setOverDue(resultSet.getString("overDue"));
            ciCarShipTaxQGEndorseSchema.setTotalAmount(resultSet.getString("totalAmount"));
            ciCarShipTaxQGEndorseSchema.setAnnualTaxDue(resultSet.getString("annualTaxDue"));
            ciCarShipTaxQGEndorseSchema.setSumTaxDefault(resultSet.getString("sumTaxDefault"));
            ciCarShipTaxQGEndorseSchema.setSumOverdue(resultSet.getString("sumOverdue"));
            ciCarShipTaxQGEndorseSchema.setSumTax(resultSet.getString("sumTax"));
        	
            ciCarShipTaxQGEndorseSchema.setTaxAmountFlag(resultSet.getString("TaxAmountFlag"));
            ciCarShipTaxQGEndorseSchema.setDeclareStatusIA(resultSet.getString("DeclareStatusIA"));
            ciCarShipTaxQGEndorseSchema.setCalcTaxFlag(resultSet.getString("CalcTaxFlag"));
            ciCarShipTaxQGEndorseSchema.setTaxPrintNo(resultSet.getString("TaxPrintNo"));
        	
            ciCarShipTaxQGEndorseSchema.setTaxDescription(resultSet.getString("taxDescription"));
            vector.add(ciCarShipTaxQGEndorseSchema);
        }
        resultSet.close();
        return vector;
    }
    
    /**
     * 根据条件获取满足条件数据条数
     * @author wangchuanzhong 20100602
     * @param strWhere      查询条件
     * @param iWhereValue   查询条件各属性值
     * @return intCount     查询个数
     * @exception Exception
     */
    public int getCount(String strWhere,ArrayList iWhereValue) throws Exception
    {
      int intCount = 0;
      DbPool dbpool = new DbPool();
      
      try {
          dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
          intCount = this.getCount(dbpool,strWhere,iWhereValue);
      }
      catch(Exception exception){
          throw exception;
      }
      finally {
          dbpool.close();
      }
      return intCount;
    }
    
    /**
     * 根据条件获取查询数据条数
     * @author wangchuanzhong 20100602
     * @param dbpool
     * @param strWhere
     * @param iWhereValue
     * @return
     * @throws Exception
     */
    public int getCount(DbPool dbpool,String strWhere,ArrayList iWhereValue)
    throws Exception{
      int intCount = 0;
      String strSQL = " SELECT COUNT(1) FROM CICarShipTaxQGEndorse WHERE "+ strWhere;
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
     * 根据SQL语句获取结果集
     * @author wangchuanzhong 20100602
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
        CICarShipTaxQGEndorseSchema ciCarShipTaxQGEndorseSchema = null;
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
        while(resultSet.next())
        {
            ciCarShipTaxQGEndorseSchema = new CICarShipTaxQGEndorseSchema();
            ciCarShipTaxQGEndorseSchema.setEndorseNo(resultSet.getString("endorseNo"));
            ciCarShipTaxQGEndorseSchema.setDemandNo(resultSet.getString("demandNo"));
            ciCarShipTaxQGEndorseSchema.setPolicyNo(resultSet.getString("policyNo"));
            ciCarShipTaxQGEndorseSchema.setTaxType(resultSet.getString("taxType"));
            ciCarShipTaxQGEndorseSchema.setSerialNo(resultSet.getString("serialNo"));
            ciCarShipTaxQGEndorseSchema.setTaxTermTypeCode(resultSet.getString("taxTermTypeCode"));
            ciCarShipTaxQGEndorseSchema.setTaxConditionCode(resultSet.getString("taxConditionCode"));
            ciCarShipTaxQGEndorseSchema.setTaxRegistryNumber(resultSet.getString("taxRegistryNumber"));
            ciCarShipTaxQGEndorseSchema.setTaxPayerName(resultSet.getString("taxPayerName"));
            ciCarShipTaxQGEndorseSchema.setTaxPayerIdentificationCode(resultSet.getString("taxPayerIdentificationCode"));
            ciCarShipTaxQGEndorseSchema.setTaxLocationCode(resultSet.getString("taxLocationCode"));
            ciCarShipTaxQGEndorseSchema.setTaxStartDate("" + resultSet.getDate("taxStartDate"));
            ciCarShipTaxQGEndorseSchema.setTaxEndDate("" + resultSet.getDate("taxEndDate"));
            ciCarShipTaxQGEndorseSchema.setDeclareDate("" + resultSet.getDate("declareDate"));
            ciCarShipTaxQGEndorseSchema.setTaxDepartmentCode(resultSet.getString("taxDepartmentCode"));
            ciCarShipTaxQGEndorseSchema.setTaxDepartment(resultSet.getString("taxDepartment"));
            ciCarShipTaxQGEndorseSchema.setTaxDocumentNumber(resultSet.getString("taxDocumentNumber"));
            ciCarShipTaxQGEndorseSchema.setAnnualTaxAmount(resultSet.getString("annualTaxAmount"));
            ciCarShipTaxQGEndorseSchema.setAppliedArea(resultSet.getString("appliedArea"));
            ciCarShipTaxQGEndorseSchema.setTaxRateIdentifier(resultSet.getString("taxRateIdentifier"));
            ciCarShipTaxQGEndorseSchema.setTaxItemDetailCode(resultSet.getString("taxItemDetailCode"));
            ciCarShipTaxQGEndorseSchema.setTaxUnitTypeCode(resultSet.getString("taxUnitTypeCode"));
            ciCarShipTaxQGEndorseSchema.setUnitRate(resultSet.getString("unitRate"));
            ciCarShipTaxQGEndorseSchema.setTaxRateStartDate("" +resultSet.getDate("taxRateStartDate"));
            ciCarShipTaxQGEndorseSchema.setTaxRateEndDate("" +resultSet.getDate("taxRateEndDate"));
            ciCarShipTaxQGEndorseSchema.setDeductionDueCode(resultSet.getString("deductionDueCode"));
            ciCarShipTaxQGEndorseSchema.setDeductionDueType(resultSet.getString("deductionDueType"));
            ciCarShipTaxQGEndorseSchema.setDeductionDueProportion(resultSet.getString("deductionDueProportion"));
            ciCarShipTaxQGEndorseSchema.setDeduction(resultSet.getString("deduction"));
            ciCarShipTaxQGEndorseSchema.setDeductionDocumentNumber(resultSet.getString("deductionDocumentNumber"));
            ciCarShipTaxQGEndorseSchema.setDeductionDepartmentCode(resultSet.getString("deductionDepartmentCode"));
            ciCarShipTaxQGEndorseSchema.setDeductionDepartment(resultSet.getString("deductionDepartment"));
            ciCarShipTaxQGEndorseSchema.setTaxDue(resultSet.getString("taxDue"));
            ciCarShipTaxQGEndorseSchema.setExceedDate("" + resultSet.getDate("exceedDate"));
            ciCarShipTaxQGEndorseSchema.setExceedDaysCount(resultSet.getString("exceedDaysCount"));
            ciCarShipTaxQGEndorseSchema.setOverDue(resultSet.getString("overDue"));
            ciCarShipTaxQGEndorseSchema.setTotalAmount(resultSet.getString("totalAmount"));
            ciCarShipTaxQGEndorseSchema.setAnnualTaxDue(resultSet.getString("annualTaxDue"));
            ciCarShipTaxQGEndorseSchema.setSumTaxDefault(resultSet.getString("sumTaxDefault"));
            ciCarShipTaxQGEndorseSchema.setSumOverdue(resultSet.getString("sumOverdue"));
            ciCarShipTaxQGEndorseSchema.setSumTax(resultSet.getString("sumTax"));
        	
            ciCarShipTaxQGEndorseSchema.setTaxAmountFlag(resultSet.getString("TaxAmountFlag"));
            ciCarShipTaxQGEndorseSchema.setDeclareStatusIA(resultSet.getString("DeclareStatusIA"));
            ciCarShipTaxQGEndorseSchema.setCalcTaxFlag(resultSet.getString("CalcTaxFlag"));
            ciCarShipTaxQGEndorseSchema.setTaxPrintNo(resultSet.getString("TaxPrintNo"));
        	
            ciCarShipTaxQGEndorseSchema.setTaxDescription(resultSet.getString("taxDescription"));
            vector.add(ciCarShipTaxQGEndorseSchema);
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
