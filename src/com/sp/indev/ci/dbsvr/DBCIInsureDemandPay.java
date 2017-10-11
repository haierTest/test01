package com.sp.indiv.ci.dbsvr;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.util.*;

import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.indiv.ci.schema.CIInsureDemandPaySchema;
import com.sp.indiv.ci.schema.CIInsureDemandSchema;

/**
 * ����XX��ѯXXXXX��-CIInsureDemandPay��DB��
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>@createdate 2006-06-14</p>
 * @author DBGenerator
 * @version 1.0
 */
public class DBCIInsureDemandPay extends CIInsureDemandPaySchema{
    /**
     * ���캯��
     */       
    public DBCIInsureDemandPay(){
    }

    /**
     * ����һ����¼
     * @param dbpool dbpool
     * @throws Exception
     */       
    public void insert(DbPool dbpool) throws Exception{
        String strSQL = " Insert Into CIInsureDemandPay(" + 
                           " DemandNo," + 
                           " SerialNo," + 
                           " PayCompany," + 
                           " PolicyNo," + 
                           " KindCode," + 
                           " PayType," + 
                           " PersonPayType," + 
                           " CompensateNo," + 
                           " LossTime," +
                          
                           " EffectiveDate," + 
                           " ExpireDate," +    
                          
                           " LossFee," + 
                           " AccidentDeathFlag, " +
                           " Remark," + 
                           " Flag," +
                           
                           " OptionType," +
                           " TotalAmount," +
                           
                           " ClaimNotificationNo," +
                           " ClaimRegistrationNo," +
                           
                           " RiskWarningType," +
                           " LossArea," +
                           
                           
                           " InsurerArea," +
                           
                           " CaseID," +
                           " EndCaseTime" + ") values(" + 
                           "'" + getDemandNo() +"'" + "," +
                           "'" + getSerialNo() +"'" + "," +
                           "'" + getPayCompany() +"'" + "," +
                           "'" + getPolicyNo() +"'" + "," +
                           "'" + getKindCode() +"'" + "," +
                           "'" + getPayType() +"'" + "," +
                           "'" + getPersonPayType() +"'" + "," +
                           "'" + getCompensateNo() +"'" + "," +
                           "'" + getLossTime() +"'" + "," +
                           
                           "to_date('" + getEffectiveDate()+"','yyyy-mm-dd')" + "," +
                           "to_date('" + getExpireDate() +"','yyyy-mm-dd')" + "," +
                           
                           "'" + getLossFee() +"'" + "," +
                           "'" + getAccidentDeathFlag() +"'" + "," +
                           "'" + getRemark() +"'" + "," +
                           "'" + getFlag() +"', " +
                           
                           "'" + getOptionType() + "', " +
                           "'" + getTotalAmount() + "', " +
                           
                           
                           "'" + getClaimNotificationNo() + "', " +
                           "'" + getClaimRegistrationNo() + "', " +
                           
                           "'" + getRiskWarningType() + "', " +
                           "'" + getLossArea() + "', " +
                           
                           
                           "'" + getInsurerArea() + "', " +
                           
                           "'" + getCaseID() + "', " +
                           
                           "'" + getEndCaseTime() + "'" + 
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
        String strSQL = " Delete From CIInsureDemandPay Where DemandNo = " + "'" + demandNo + "'" +
                            " And SerialNo = " + "'" + serialNo + "'";
        dbpool.delete(strSQL);
    }
    
    public void deleteByDemandNo(DbPool dbpool,String demandNo) throws Exception{
        String strSQL = " Delete From CIInsureDemandPay Where DemandNo = " + "'" + demandNo + "'";
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
        String strSQL = " Update CIInsureDemandPay Set" +
                           " DemandNo = " + "'" +getDemandNo() + "'" + "," +
                           " SerialNo = " + "'" +getSerialNo() + "'" + "," +
                           " PayCompany = " + "'" +getPayCompany() + "'" + "," +
                           " PolicyNo = " + "'" +getPolicyNo() + "'" + "," +
                           " KindCode = " + "'" +getKindCode() + "'" + "," +
                           " PayType = " + "'" +getPayType() + "'" + "," +
                           " PersonPayType = " + "'" +getPersonPayType() + "'" + "," +
                           " CompensateNo = " + "'" +getCompensateNo() + "'" + "," +
                           " LossTime = " + "'" +getLossTime() + "'" + "," +
                           
                           " EffectiveDate=to_date('" + getEffectiveDate() + "','yyyy-mm-dd')" + "," +
                           " ExpireDate=to_date('" + getExpireDate() + "','yyyy-mm-dd')" + "," +                     
                           
                           " LossFee = " + "'" +getLossFee() + "'" + "," +
                           " AccidentDeathFlag = " + "'" +getAccidentDeathFlag() + "'" + "," +
                           " Remark = " + "'" +getRemark() + "'" + "," +
                           " Flag = " + "'" +getFlag() + "', " +
                           
                           " OptionType = '" + getOptionType() + "', " +
                           " TotalAmount = '" + getTotalAmount() + "', " +
                           
                           
                           " ClaimNotificationNo = '" + getClaimNotificationNo() + "', " +
                           " ClaimRegistrationNo = '" + getClaimRegistrationNo() + "', " +
                           
                           " RiskWarningType = '" + getRiskWarningType() + "', " +
                           " LossArea = '" + getLossArea() + "', " +
                           
                           
                           " InsurerArea = '" + getInsurerArea() + "', " +
                           
                           " CaseID = '" + getCaseID() + "', " +
                           
                           " EndCaseTime = '" + getEndCaseTime() + "'" + 
                           " Where DemandNo = " + "'" +getDemandNo() + "'" +
                            " And SerialNo = " + "'" +getSerialNo() + "'";
        dbpool.update(strSQL);
    }

    /**
     * ����һ����¼
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
        String strSQL = " Select * From CIInsureDemandPay Where DemandNo = " + "'" + demandNo + "'" +
                            " And SerialNo = " + "'" + serialNo + "'";
        ResultSet resultSet = dbpool.query(strSQL);
        if(resultSet.next()){
            setDemandNo(resultSet.getString("demandNo"));
            setSerialNo(resultSet.getString("serialNo"));
            setPayCompany(resultSet.getString("payCompany"));
            setPolicyNo(resultSet.getString("policyNo"));
            setKindCode(resultSet.getString("kindCode"));
            setPayType(resultSet.getString("payType"));
            setPersonPayType(resultSet.getString("personPayType"));
            setCompensateNo(resultSet.getString("compensateNo"));
            setLossTime("" + resultSet.getDate("lossTime"));
            
            setEffectiveDate("" + resultSet.getDate("EffectiveDate"));
            setExpireDate("" + resultSet.getDate("ExpireDate"));
            
            setLossFee(resultSet.getString("lossFee"));
            setAccidentDeathFlag(resultSet.getString("AccidentDeathFlag"));
            setRemark(resultSet.getString("remark"));
            setFlag(resultSet.getString("flag"));
            
            setOptionType(resultSet.getString("OptionType"));
            setTotalAmount(resultSet.getString("TotalAmount"));
            
            
            setClaimNotificationNo(resultSet.getString("ClaimNotificationNo"));
            setClaimRegistrationNo(resultSet.getString("ClaimRegistrationNo"));
            
            setRiskWarningType(resultSet.getString("RiskWarningType"));
            setLossArea(resultSet.getString("LossArea"));
            
            
            setInsurerArea(resultSet.getString("InsurerArea"));
            
            setCaseID(resultSet.getString("CaseID"));
            
            
            
            setEndCaseTime(resultSet.getDate("endCaseTime")+"");
            
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
        String strDataSource =SysConfig.CONST_DDCCDATASOURCE;
        
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
        String strSQL = " SELECT COUNT(*) FROM CIInsureDemandPay WHERE "+ strWhere;
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
     * ��ѯ����ģ����ѯ�����ļ�¼��
     * @param strSQL  SQLStatement
     * @return ����ģ����ѯ�����ļ�¼��
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
     * ����SQL����ȡ�����
     * @param strSQL  SQL���
     * @return Vector ��ѯ�����¼��
     * @throws SQLException    ���ݿ�����쳣��
     * @throws NamingException �����쳣��
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
        CIInsureDemandPaySchema cIInsureDemandPaySchema = null;
        ResultSet resultSet = dbpool.query(strSQL);
        while(resultSet.next())
        {
            cIInsureDemandPaySchema = new CIInsureDemandPaySchema();
            cIInsureDemandPaySchema.setDemandNo(resultSet.getString("demandNo"));
            cIInsureDemandPaySchema.setSerialNo(resultSet.getString("serialNo"));
            cIInsureDemandPaySchema.setPayCompany(resultSet.getString("payCompany"));
            cIInsureDemandPaySchema.setPolicyNo(resultSet.getString("policyNo"));
            cIInsureDemandPaySchema.setKindCode(resultSet.getString("kindCode"));
            cIInsureDemandPaySchema.setPayType(resultSet.getString("payType"));
            cIInsureDemandPaySchema.setPersonPayType(resultSet.getString("personPayType"));
            cIInsureDemandPaySchema.setCompensateNo(resultSet.getString("compensateNo"));
            cIInsureDemandPaySchema.setLossTime("" + resultSet.getDate("lossTime"));
            
            cIInsureDemandPaySchema.setEffectiveDate("" + resultSet.getDate("EffectiveDate"));
            cIInsureDemandPaySchema.setExpireDate("" + resultSet.getDate("ExpireDate"));
            
            cIInsureDemandPaySchema.setLossFee(resultSet.getString("lossFee"));
            cIInsureDemandPaySchema.setAccidentDeathFlag(resultSet.getString("AccidentDeathFlag"));
            cIInsureDemandPaySchema.setRemark(resultSet.getString("remark"));
            cIInsureDemandPaySchema.setFlag(resultSet.getString("flag"));
            
            cIInsureDemandPaySchema.setOptionType(resultSet.getString("OptionType"));
            cIInsureDemandPaySchema.setTotalAmount(resultSet.getString("TotalAmount"));
            
            
            cIInsureDemandPaySchema.setClaimNotificationNo(resultSet.getString("ClaimNotificationNo"));
            cIInsureDemandPaySchema.setClaimRegistrationNo(resultSet.getString("ClaimRegistrationNo"));
            
            cIInsureDemandPaySchema.setRiskWarningType(resultSet.getString("RiskWarningType"));
            cIInsureDemandPaySchema.setLossArea(resultSet.getString("LossArea"));
            
            
            cIInsureDemandPaySchema.setInsurerArea(resultSet.getString("InsurerArea"));
            
            cIInsureDemandPaySchema.setCaseID(resultSet.getString("CaseID"));
            
            
            
            cIInsureDemandPaySchema.setEndCaseTime(resultSet.getDate("endCaseTime")+"");
            
            vector.add(cIInsureDemandPaySchema);
        }
        resultSet.close();
        return vector;
    }
    
    /**
     * ����������ȡ����������������
     * @author liuweichang 20150605
     * @param strWhere      ��ѯ����
     * @param iWhereValue   ��ѯ����������ֵ
     * @return intCount     ��ѯ����
     * @exception Exception
     */
    public int getCount(String strWhere,ArrayList iWhereValue) throws Exception
    {
      int intCount = 0;
      DbPool dbpool = new DbPool();
      
      try {
          
          String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
          if ("1".equals(strSwitch)) {
              dbpool.open("platformNewDataSource");
          } else {
              dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
          }
          
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
     * ����������ȡ��ѯ��������
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
      String strSQL = " SELECT COUNT(1) FROM CIInsureDemandPay WHERE "+ strWhere;
      ResultSet resultSet = null;
      if(iWhereValue != null)
      {
    	  
          
          dbpool.prepareInnerStatement1007(strSQL);
          
          
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
     * ����SQL����ȡ�����
     * @author wanghe 20110104
     * @param dbpool      ���ݿ����ӳ�
     * @param strSQL      SQL���
     * @param iWhereValue ��ѯ����������ֵ
     * @return Vector ��ѯ�����¼��
     * @throws SQLException    ���ݿ�����쳣��
     * @throws NamingException �����쳣��
     */
    public Vector findByConditions(DbPool dbpool,String strSQL ,ArrayList iWhereValue) throws
    SQLException,NamingException,Exception{
        Vector vector = new Vector();
        CIInsureDemandPaySchema cIInsureDemandPaySchema = null;
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
            cIInsureDemandPaySchema = new CIInsureDemandPaySchema();
            cIInsureDemandPaySchema.setDemandNo(resultSet.getString("demandNo"));
            cIInsureDemandPaySchema.setSerialNo(resultSet.getString("serialNo"));
            cIInsureDemandPaySchema.setPayCompany(resultSet.getString("payCompany"));
            cIInsureDemandPaySchema.setPolicyNo(resultSet.getString("policyNo"));
            cIInsureDemandPaySchema.setKindCode(resultSet.getString("kindCode"));
            cIInsureDemandPaySchema.setPayType(resultSet.getString("payType"));
            cIInsureDemandPaySchema.setPersonPayType(resultSet.getString("personPayType"));
            cIInsureDemandPaySchema.setCompensateNo(resultSet.getString("compensateNo"));
            cIInsureDemandPaySchema.setLossTime("" + resultSet.getDate("lossTime"));
            
            cIInsureDemandPaySchema.setEffectiveDate("" + resultSet.getDate("EffectiveDate"));
            cIInsureDemandPaySchema.setExpireDate("" + resultSet.getDate("ExpireDate"));
            
            cIInsureDemandPaySchema.setLossFee(resultSet.getString("lossFee"));
            cIInsureDemandPaySchema.setAccidentDeathFlag(resultSet.getString("AccidentDeathFlag"));
            cIInsureDemandPaySchema.setRemark(resultSet.getString("remark"));
            cIInsureDemandPaySchema.setFlag(resultSet.getString("flag"));
            
            cIInsureDemandPaySchema.setOptionType(resultSet.getString("OptionType"));
            cIInsureDemandPaySchema.setTotalAmount(resultSet.getString("TotalAmount"));
            
            
            cIInsureDemandPaySchema.setClaimNotificationNo(resultSet.getString("ClaimNotificationNo"));
            cIInsureDemandPaySchema.setClaimRegistrationNo(resultSet.getString("ClaimRegistrationNo"));
            
            cIInsureDemandPaySchema.setRiskWarningType(resultSet.getString("RiskWarningType"));
            cIInsureDemandPaySchema.setLossArea(resultSet.getString("LossArea"));
            
            
            cIInsureDemandPaySchema.setInsurerArea(resultSet.getString("InsurerArea"));
            
            cIInsureDemandPaySchema.setCaseID(resultSet.getString("CaseID"));
            
            
            
            cIInsureDemandPaySchema.setEndCaseTime(resultSet.getDate("endCaseTime")+"");
            
            vector.add(cIInsureDemandPaySchema);
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
