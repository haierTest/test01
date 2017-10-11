package com.sp.indiv.ci.dbsvr;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import javax.naming.NamingException;

import com.sp.indiv.ci.schema.PrpCIEndorClaimSchema;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;

/**
 * ����PrpCIEndorClaim��DB��
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>@createdate 2007-08-23</p>
 * @author DBGenerator
 * @version 1.0
 */
public class DBPrpCIEndorClaim extends PrpCIEndorClaimSchema{
    /**
     * ���캯��
     */       
    public DBPrpCIEndorClaim(){
    }

    /**
     * ����һ����¼
     * @param dbpool dbpool
     * @throws Exception
     */       
    public void insert(DbPool dbpool) throws Exception{
        String strSQL = " Insert Into PrpCIEndorClaim(" + 
                           " DemandNo," + 
                           " SerialNo," + 
                           " PayCompany," + 
                           " PolicyNo," + 
                           " PersonPayType," + 
                           " CompensateNo," + 
                           " ClaimNo," + 
                           " LossTime," + 
                           
                           " EffectiveDate," + 
                           " ExpireDate," +    
                           
                           " EndCaseTime," + 
                           " Remarks," + 
                           
                           " RegistNo,"+
                           " CaseID,"+
                           
                           
                           " totalAmount,"+
                           
                           
                           "insurerArea,"+
                           
                           " Flag"  +                    
                           ") values(" + 
                           "'" + getDemandNo() +"'" + "," +
                           "'" + getSerialNo() +"'" + "," +
                           "'" + getPayCompany() +"'" + "," +
                           "'" + getPolicyNo() +"'" + "," +
                           "'" + getPersonPayType() +"'" + "," +
                           "'" + getCompensateNo() +"'" + "," +
                           "'" + getClaimNo() +"'" + "," +
                           "'" + getLossTime() +"'" + "," +
                           
                           "to_date('" + getEffectiveDate()+"','yyyy-mm-dd')" + "," +
                           "to_date('" + getExpireDate() +"','yyyy-mm-dd')" + "," +
                           
                           "'" + getEndCaseTime() +"'" + "," +
                           "'" + getRemarks() +"'" + "," +
                           
                           "'" + getRegistNo() +"'" + "," +
                           "'" + getCaseID() +"'" + "," +
                           
                           
                           "'" + getTotalAmount() +"'" + "," +
                           
                           
                           "'" + getInsurerArea() +"'" + "," +
                           
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
        
        dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
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

    public void delete(DbPool dbpool,String demandNo,String serialNo) throws Exception{
        String strSQL = " Delete From PrpCIEndorClaim Where DemandNo = " + "'" + demandNo + "'" +
                            " And SerialNo = " + "'" + serialNo + "'";
        dbpool.delete(strSQL);
    }

    public void delete(String demandNo,String serialNo) throws Exception{
        DbPool dbpool = new DbPool();
        
        dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
        try{
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
        String strSQL = " Update PrpCIEndorClaim Set" +
                           " DemandNo = " + "'" +getDemandNo() + "'" + "," +
                           " SerialNo = " + "'" +getSerialNo() + "'" + "," +
                           " PayCompany = " + "'" +getPayCompany() + "'" + "," +
                           " PolicyNo = " + "'" +getPolicyNo() + "'" + "," +
                           " PersonPayType = " + "'" +getPersonPayType() + "'" + "," +
                           " CompensateNo = " + "'" +getCompensateNo() + "'" + "," +
                           " ClaimNo = " + "'" +getClaimNo() + "'" + "," +
                           " LossTime = " + "'" +getLossTime() + "'" + "," +
                           
                           " EffectiveDate=to_date('" + getEffectiveDate() + "','yyyy-mm-dd')" + "," +
                           " ExpireDate=to_date('" + getExpireDate() + "','yyyy-mm-dd')" + "," +                     
                           
                           " EndCaseTime = " + "'" +getEndCaseTime() + "'" + "," +
                           " Remarks = " + "'" +getRemarks() + "'" + "," +
                           
                           " RegistNo = " + "'" +getRegistNo() + "'" + "," +
                           " CaseID = " + "'" +getCaseID() + "'" + "," +
                           
                           
                           " totalAmount = " + "'" +getTotalAmount() + "'" + "," +
                           
                           
                           " insurerArea = " + "'" +getInsurerArea() + "'" + "," +
                           
                           " Flag = " + "'" +getFlag() + "'" +
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
        
        dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
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

    public int getInfo(DbPool dbpool,String demandNo,String serialNo) throws Exception{
        int intResult = 0;
        String strSQL = " Select * From PrpCIEndorClaim Where DemandNo = " + "'" + demandNo + "'" +
                            " And SerialNo = " + "'" + serialNo + "'";
        ResultSet resultSet = dbpool.query(strSQL);
        if(resultSet.next()){
            setDemandNo(resultSet.getString("demandNo"));
            setSerialNo("" + resultSet.getInt("serialNo"));
            setPayCompany(resultSet.getString("payCompany"));
            setPolicyNo(resultSet.getString("policyNo"));
            setPersonPayType(resultSet.getString("personPayType"));
            setCompensateNo(resultSet.getString("compensateNo"));
            setClaimNo(resultSet.getString("claimNo"));
            setLossTime("" + resultSet.getDate("lossTime"));
            
            setEffectiveDate("" + resultSet.getDate("EffectiveDate"));
            setExpireDate("" + resultSet.getDate("ExpireDate"));
            
            setEndCaseTime("" + resultSet.getDate("endCaseTime"));
            setRemarks(resultSet.getString("remarks"));
            
            setRegistNo(resultSet.getString("RegistNo"));
            setCaseID(resultSet.getString("CaseID"));
            
            
            setTotalAmount(resultSet.getString("totalAmount"));
            
            
            setInsurerArea(resultSet.getString("insurerArea"));
            
            setFlag(resultSet.getString("flag"));
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
        String strDataSource =SysConst.getProperty("DDCCDATASOURCE");
        
        dbpool.open(strDataSource);
        try{
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
        String strSQL = " SELECT COUNT(*) FROM PrpCIEndorClaim WHERE "+ strWhere;
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
        
        dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
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
        
        dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
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
        PrpCIEndorClaimSchema prpCIEndorClaimSchema = null;
        ResultSet resultSet = dbpool.query(strSQL);
        while(resultSet.next())
        {
            prpCIEndorClaimSchema = new PrpCIEndorClaimSchema();
            prpCIEndorClaimSchema.setDemandNo(resultSet.getString("demandNo"));
            prpCIEndorClaimSchema.setSerialNo("" + resultSet.getInt("serialNo"));
            prpCIEndorClaimSchema.setPayCompany(resultSet.getString("payCompany"));
            prpCIEndorClaimSchema.setPolicyNo(resultSet.getString("policyNo"));
            prpCIEndorClaimSchema.setPersonPayType(resultSet.getString("personPayType"));
            prpCIEndorClaimSchema.setCompensateNo(resultSet.getString("compensateNo"));
            prpCIEndorClaimSchema.setClaimNo(resultSet.getString("claimNo"));
            prpCIEndorClaimSchema.setLossTime("" + resultSet.getDate("lossTime"));
            
            prpCIEndorClaimSchema.setEffectiveDate("" + resultSet.getDate("EffectiveDate"));
            prpCIEndorClaimSchema.setExpireDate("" + resultSet.getDate("ExpireDate"));
            
            prpCIEndorClaimSchema.setEndCaseTime("" + resultSet.getDate("endCaseTime"));
            prpCIEndorClaimSchema.setRemarks(resultSet.getString("remarks"));
            
            prpCIEndorClaimSchema.setRegistNo(resultSet.getString("RegistNo"));
            prpCIEndorClaimSchema.setCaseID(resultSet.getString("CaseID"));
            
            
            prpCIEndorClaimSchema.setTotalAmount(resultSet.getString("totalAmount"));
            
            
            prpCIEndorClaimSchema.setInsurerArea(resultSet.getString("insurerArea"));
            
            prpCIEndorClaimSchema.setFlag(resultSet.getString("flag"));
            vector.add(prpCIEndorClaimSchema);
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
      String strSQL = " SELECT COUNT(1) FROM PrpCIEndorClaim WHERE "+ strWhere;
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
        PrpCIEndorClaimSchema prpCIEndorClaimSchema = null;
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
            prpCIEndorClaimSchema = new PrpCIEndorClaimSchema();
            prpCIEndorClaimSchema.setDemandNo(resultSet.getString("demandNo"));
            prpCIEndorClaimSchema.setSerialNo("" + resultSet.getInt("serialNo"));
            prpCIEndorClaimSchema.setPayCompany(resultSet.getString("payCompany"));
            prpCIEndorClaimSchema.setPolicyNo(resultSet.getString("policyNo"));
            prpCIEndorClaimSchema.setPersonPayType(resultSet.getString("personPayType"));
            prpCIEndorClaimSchema.setCompensateNo(resultSet.getString("compensateNo"));
            prpCIEndorClaimSchema.setClaimNo(resultSet.getString("claimNo"));
            prpCIEndorClaimSchema.setLossTime("" + resultSet.getDate("lossTime"));
            prpCIEndorClaimSchema.setEffectiveDate("" + resultSet.getDate("EffectiveDate"));
            prpCIEndorClaimSchema.setExpireDate("" + resultSet.getDate("ExpireDate"));
            prpCIEndorClaimSchema.setEndCaseTime("" + resultSet.getDate("endCaseTime"));
            prpCIEndorClaimSchema.setRemarks(resultSet.getString("remarks"));
            prpCIEndorClaimSchema.setRegistNo(resultSet.getString("RegistNo"));
            prpCIEndorClaimSchema.setCaseID(resultSet.getString("CaseID"));
            
            prpCIEndorClaimSchema.setTotalAmount(resultSet.getString("totalAmount"));
            
            
            prpCIEndorClaimSchema.setInsurerArea(resultSet.getString("insurerArea"));
            
            prpCIEndorClaimSchema.setFlag(resultSet.getString("flag"));
            vector.add(prpCIEndorClaimSchema);
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
