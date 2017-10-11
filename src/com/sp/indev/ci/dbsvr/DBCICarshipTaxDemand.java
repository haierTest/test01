package com.sp.indiv.ci.dbsvr;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import javax.naming.NamingException;

import com.sp.indiv.ci.schema.CICarshipTaxDemandSchema;
import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;

/**
 * ����cicarshiptaxdemand��DB��
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>@createdate 2007-06-21</p>
 * @author DBGenerator
 * @version 1.0
 */
public class DBCICarshipTaxDemand extends CICarshipTaxDemandSchema{
    /**
     * ���캯��
     */       
    public DBCICarshipTaxDemand(){
    }

    /**
     * ����һ����¼
     * @param dbpool dbpool
     * @throws Exception
     */       
    public void insert(DbPool dbpool) throws Exception{
        String strSQL = " Insert Into CICarshipTaxDemand(" + 
                           " ProposalNo," + 
                           " DemandNo," + 
                           " PolicyNo," + 
                           " TaxNo," + 
                           " TaxFlag," + 
                           " LicenseCategory," + 
                           " CompleteKerbMass," + 
                           " TaxpayerCertiType," + 
                           " IdentifyNumber," + 
                           " PaidCertificate," + 
                           " FreeCertificate," + 
                           " TaxComCode," + 
                           " TaxComName," + 
                           " payerCode," + 
                           " TaxAbateReason," + 
                           " TaxAbateAmount," + 
                           " TaxActual," + 
                           " PreviousPay," + 
                           " LateFee," + 
                           " SumTax," + 
                           " TaxType," + 
                           
                           "CarNumber," +
                           "PayStartDate," +
                           "PayEndDate," + 
                           
                           
                           " CommissionTax," +
                           " CertificateDate," +
                           
                           
                           " OwnerName," +
                           
                           " Flag) values(" + 
                           "'" + getProposalNo() +"'" + "," +
                           "'" + getDemandNo() +"'" + "," +
                           "'" + getPolicyNo() +"'" + "," +
                           "'" + getTaxNo() +"'" + "," +
                           "'" + getTaxFlag() +"'" + "," +
                           "'" + getLicenseCategory() +"'" + "," +
                           "'" + getCompleteKerbMass() +"'" + "," +
                           "'" + getTaxpayerCertiType() +"'" + "," +
                           "'" + getIdentifyNumber() +"'" + "," +
                           "'" + getPaidCertificate() +"'" + "," +
                           "'" + getFreeCertificate() +"'" + "," +
                           "'" + getTaxComCode() +"'" + "," +
                           "'" + getTaxComName() +"'" + "," +
                           "'" + getPayerCode() +"'" + "," +
                           "'" + getTaxAbateReason() +"'" + "," +
                           "'" + getTaxAbateAmount() +"'" + "," +
                           "'" + getTaxActual() +"'" + "," +
                           "'" + getPreviousPay() +"'" + "," +
                           "'" + getLateFee() +"'" + "," +
                           "'" + getSumTax() +"'" + "," +
                           "'" + getTaxType() +"'" + "," +
                           
                           "'" + getCarNumber() + "'" + "," + 
                           "'" + getPayStartDate() + "'" + "," +
                           "'" + getPayEndDate() + "'" + "," +
                           
                           
                           "'" + getCommissionTax() +"'" + "," +
                           "'" + getCertificateDate() +"'" + "," +
                           
                           
                           "'" + getOwnerName() +"'" + "," +
                           
                           "'" + getFlag() +"'" +
                           ")";
        dbpool.insert(strSQL);
    }

    /**
     * ����һ����¼
     * @throws Exception
     */       
    public void insert() throws Exception{
        DbPool dbpool = new DbPool();
        
        
        String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
        if ("1".equals(strSwitch)) {
            dbpool.open("platformNewDataSource");
        } else {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        }
        
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

    public void delete(DbPool dbpool,String proposalNo) throws Exception{
        String strSQL = " Delete From CICarshipTaxDemand Where ProposalNo = " + "'" + proposalNo + "'";
        dbpool.delete(strSQL);
    }

    public void delete(String proposalNo) throws Exception{
        DbPool dbpool = new DbPool();
        
        
        String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
        if ("1".equals(strSwitch)) {
            dbpool.open("platformNewDataSource");
        } else {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        }
        
        try{
            dbpool.beginTransaction();
            delete(dbpool,proposalNo);
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
    
    
    public void deleteByDemandNo(DbPool dbpool,String demandNo) throws Exception{
        String strSQL = " Delete From CICarshipTaxDemand Where demandNo = " + "'" + demandNo + "'";
        dbpool.delete(strSQL);
    }

    public void deleteByDemandNo(String demandNo) throws Exception{
        DbPool dbpool = new DbPool();
        
        
        String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
        if ("1".equals(strSwitch)) {
            dbpool.open("platformNewDataSource");
        } else {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        }
        
        try{
            dbpool.beginTransaction();
            deleteByDemandNo(dbpool,demandNo);
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
        String strSQL = " Update CICarshipTaxDemand Set" +
                           " ProposalNo = " + "'" +getProposalNo() + "'" + "," +
                           " DemandNo = " + "'" +getDemandNo() + "'" + "," +
                           " PolicyNo = " + "'" +getPolicyNo() + "'" + "," +
                           " TaxNo = " + "'" +getTaxNo() + "'" + "," +
                           " TaxFlag = " + "'" +getTaxFlag() + "'" + "," +
                           " LicenseCategory = " + "'" +getLicenseCategory() + "'" + "," +
                           " CompleteKerbMass = " + "'" +getCompleteKerbMass() + "'" + "," +
                           " TaxpayerCertiType = " + "'" +getTaxpayerCertiType() + "'" + "," +
                           " IdentifyNumber = " + "'" +getIdentifyNumber() + "'" + "," +
                           " PaidCertificate = " + "'" +getPaidCertificate() + "'" + "," +
                           " FreeCertificate = " + "'" +getFreeCertificate() + "'" + "," +
                           " TaxComCode = " + "'" +getTaxComCode() + "'" + "," +
                           " TaxComName = " + "'" +getTaxComName() + "'" + "," +
                           " payerCode = " + "'" +getPayerCode() + "'" + "," +
                           " TaxAbateReason = " + "'" +getTaxAbateReason() + "'" + "," +
                           " TaxAbateAmount = " + "'" +getTaxAbateAmount() + "'" + "," +
                           " TaxActual = " + "'" +getTaxActual() + "'" + "," +
                           " PreviousPay = " + "'" +getPreviousPay() + "'" + "," +
                           " LateFee = " + "'" +getLateFee() + "'" + "," +
                           " SumTax = " + "'" +getSumTax() + "'" + "," +
                           " TaxType = " + "'" +getTaxType() + "'" + "," +
                           
                           " CarNumber = " + "'" + getCarNumber() + "'" + "," +
                           " PayStartDate = " + "'" + getPayStartDate() + "'" + "," +
                           " PayEndDate = " + "'" + getPayEndDate() + "'" + "," +
                           
                           
                           " CommissionTax = " + "'" +getCommissionTax() + "'" + "," +
                           " CertificateDate = " + "'" +getCertificateDate() + "'" + "," +
                           
                           
                           " OwnerName = " + "'" +getOwnerName() + "'" + "," +
                           
                           " Flag = " + "'" +getFlag() + "'" +
                           " Where ProposalNo = " + "'" +getProposalNo() + "'";
        dbpool.update(strSQL);
    }

    /**
     * ����һ����¼
     * @throws Exception
     */       
    public void update() throws Exception{
        DbPool dbpool = new DbPool();
        
        
        String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
        if ("1".equals(strSwitch)) {
            dbpool.open("platformNewDataSource");
        } else {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        }
        
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

    public int getInfo(DbPool dbpool,String proposalNo) throws Exception{
        int intResult = 0;
        String strSQL = " Select * From CICarshipTaxDemand Where ProposalNo = " + "'" + proposalNo + "'";
        ResultSet resultSet = dbpool.query(strSQL);
        if(resultSet.next()){
            setProposalNo(resultSet.getString("proposalNo"));
            setDemandNo(resultSet.getString("demandNo"));
            setPolicyNo(resultSet.getString("policyNo"));
            setTaxNo(resultSet.getString("taxNo"));
            setTaxFlag(resultSet.getString("taxFlag"));
            setLicenseCategory(resultSet.getString("licenseCategory"));
            setCompleteKerbMass(resultSet.getString("completeKerbMass"));
            setTaxpayerCertiType(resultSet.getString("taxpayerCertiType"));
            setIdentifyNumber(resultSet.getString("identifyNumber"));
            setPaidCertificate(resultSet.getString("paidCertificate"));
            setFreeCertificate(resultSet.getString("freeCertificate"));
            setTaxComCode(resultSet.getString("taxComCode"));
            setTaxComName(resultSet.getString("taxComName"));
            setPayerCode(resultSet.getString("payerCode"));
            setTaxAbateReason(resultSet.getString("taxAbateReason"));
            setTaxAbateAmount(resultSet.getString("taxAbateAmount"));
            setTaxActual(resultSet.getString("taxActual"));
            setPreviousPay(resultSet.getString("previousPay"));
            setLateFee(resultSet.getString("lateFee"));
            setSumTax(resultSet.getString("sumTax"));
            setTaxType(resultSet.getString("taxType"));
            
            setCarNumber(resultSet.getString("CarNumber"));
            setPayStartDate("" + resultSet.getDate("PayStartDate"));
            setPayEndDate("" + resultSet.getDate("PayEndDate"));
            
            
            setCommissionTax(resultSet.getString("commissionTax"));
            setCertificateDate("" + resultSet.getDate("CertificateDate"));
            
            
            setOwnerName("" + resultSet.getString("OwnerName"));
            
            setFlag(resultSet.getString("flag"));
            intResult = 0;
        }
        else{
            intResult = 100;
        }
        resultSet.close();
        return intResult;
    }

    public int getInfo(String proposalNo) throws Exception{
        int intResult = 0;
        DbPool dbpool = new DbPool();
        
        
        String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
        if ("1".equals(strSwitch)) {
            dbpool.open("platformNewDataSource");
        } else {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        }
        
        try{
            intResult=getInfo(dbpool,proposalNo);
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
        String strSQL = " SELECT COUNT(*) FROM CICarshipTaxDemand WHERE "+ strWhere;
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
        
        
        String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
        if ("1".equals(strSwitch)) {
            dbpool.open("platformNewDataSource");
        } else {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        }
        
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
        
        
        String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
        if ("1".equals(strSwitch)) {
            dbpool.open("platformNewDataSource");
        } else {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        }
        
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
        CICarshipTaxDemandSchema cicarshiptaxdemandSchema = null;
        ResultSet resultSet = dbpool.query(strSQL);
        while(resultSet.next())
        {
            cicarshiptaxdemandSchema = new CICarshipTaxDemandSchema();
            cicarshiptaxdemandSchema.setProposalNo(resultSet.getString("proposalNo"));
            cicarshiptaxdemandSchema.setDemandNo(resultSet.getString("demandNo"));
            cicarshiptaxdemandSchema.setPolicyNo(resultSet.getString("policyNo"));
            cicarshiptaxdemandSchema.setTaxNo(resultSet.getString("taxNo"));
            cicarshiptaxdemandSchema.setTaxFlag(resultSet.getString("taxFlag"));
            cicarshiptaxdemandSchema.setLicenseCategory(resultSet.getString("licenseCategory"));
            cicarshiptaxdemandSchema.setCompleteKerbMass(resultSet.getString("completeKerbMass"));
            cicarshiptaxdemandSchema.setTaxpayerCertiType(resultSet.getString("taxpayerCertiType"));
            cicarshiptaxdemandSchema.setIdentifyNumber(resultSet.getString("identifyNumber"));
            cicarshiptaxdemandSchema.setPaidCertificate(resultSet.getString("paidCertificate"));
            cicarshiptaxdemandSchema.setFreeCertificate(resultSet.getString("freeCertificate"));
            cicarshiptaxdemandSchema.setTaxComCode(resultSet.getString("taxComCode"));
            cicarshiptaxdemandSchema.setTaxComName(resultSet.getString("taxComName"));
            cicarshiptaxdemandSchema.setPayerCode(resultSet.getString("payerCode"));
            cicarshiptaxdemandSchema.setTaxAbateReason(resultSet.getString("taxAbateReason"));
            cicarshiptaxdemandSchema.setTaxAbateAmount(resultSet.getString("taxAbateAmount"));
            cicarshiptaxdemandSchema.setTaxActual(resultSet.getString("taxActual"));
            cicarshiptaxdemandSchema.setPreviousPay(resultSet.getString("previousPay"));
            cicarshiptaxdemandSchema.setLateFee(resultSet.getString("lateFee"));
            cicarshiptaxdemandSchema.setSumTax(resultSet.getString("sumTax"));
            cicarshiptaxdemandSchema.setTaxType(resultSet.getString("taxType"));
            
            cicarshiptaxdemandSchema.setCarNumber(resultSet.getString("CarNumber"));
            cicarshiptaxdemandSchema.setPayStartDate("" + resultSet.getDate("PayStartDate"));
            cicarshiptaxdemandSchema.setPayEndDate("" + resultSet.getDate("PayEndDate"));
            
            
            cicarshiptaxdemandSchema.setCommissionTax(resultSet.getString("commissionTax"));
            cicarshiptaxdemandSchema.setCertificateDate("" + resultSet.getDate("CertificateDate"));
            
            
            cicarshiptaxdemandSchema.setOwnerName(resultSet.getString("OwnerName"));
            
            cicarshiptaxdemandSchema.setFlag(resultSet.getString("flag"));
            vector.add(cicarshiptaxdemandSchema);
        }
        resultSet.close();
        return vector;
    }

    /**
     * ����������ȡ����������������
     * @author wangchuanzhong 20100602
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
      String strSQL = " SELECT COUNT(1) FROM CICarshipTaxDemand WHERE "+ strWhere;
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
     * ����SQL����ȡ�����
     * @author wangchuanzhong 20100602
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
        CICarshipTaxDemandSchema cicarshiptaxdemandSchema = null;
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
            cicarshiptaxdemandSchema = new CICarshipTaxDemandSchema();
            cicarshiptaxdemandSchema.setProposalNo(resultSet.getString("proposalNo"));
            cicarshiptaxdemandSchema.setDemandNo(resultSet.getString("demandNo"));
            cicarshiptaxdemandSchema.setPolicyNo(resultSet.getString("policyNo"));
            cicarshiptaxdemandSchema.setTaxNo(resultSet.getString("taxNo"));
            cicarshiptaxdemandSchema.setTaxFlag(resultSet.getString("taxFlag"));
            cicarshiptaxdemandSchema.setLicenseCategory(resultSet.getString("licenseCategory"));
            cicarshiptaxdemandSchema.setCompleteKerbMass(resultSet.getString("completeKerbMass"));
            cicarshiptaxdemandSchema.setTaxpayerCertiType(resultSet.getString("taxpayerCertiType"));
            cicarshiptaxdemandSchema.setIdentifyNumber(resultSet.getString("identifyNumber"));
            cicarshiptaxdemandSchema.setPaidCertificate(resultSet.getString("paidCertificate"));
            cicarshiptaxdemandSchema.setFreeCertificate(resultSet.getString("freeCertificate"));
            cicarshiptaxdemandSchema.setTaxComCode(resultSet.getString("taxComCode"));
            cicarshiptaxdemandSchema.setTaxComName(resultSet.getString("taxComName"));
            cicarshiptaxdemandSchema.setPayerCode(resultSet.getString("payerCode"));
            cicarshiptaxdemandSchema.setTaxAbateReason(resultSet.getString("taxAbateReason"));
            cicarshiptaxdemandSchema.setTaxAbateAmount(resultSet.getString("taxAbateAmount"));
            cicarshiptaxdemandSchema.setTaxActual(resultSet.getString("taxActual"));
            cicarshiptaxdemandSchema.setPreviousPay(resultSet.getString("previousPay"));
            cicarshiptaxdemandSchema.setLateFee(resultSet.getString("lateFee"));
            cicarshiptaxdemandSchema.setSumTax(resultSet.getString("sumTax"));
            cicarshiptaxdemandSchema.setTaxType(resultSet.getString("taxType"));
            cicarshiptaxdemandSchema.setCarNumber(resultSet.getString("CarNumber"));
            cicarshiptaxdemandSchema.setPayStartDate("" + resultSet.getDate("PayStartDate"));
            cicarshiptaxdemandSchema.setPayEndDate("" + resultSet.getDate("PayEndDate"));
            cicarshiptaxdemandSchema.setCommissionTax(resultSet.getString("commissionTax"));
            cicarshiptaxdemandSchema.setCertificateDate("" + resultSet.getDate("CertificateDate"));
            
            cicarshiptaxdemandSchema.setOwnerName("" + resultSet.getString("OwnerName"));
            
            cicarshiptaxdemandSchema.setFlag(resultSet.getString("flag"));
            vector.add(cicarshiptaxdemandSchema);
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
