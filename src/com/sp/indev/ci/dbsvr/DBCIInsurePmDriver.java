package com.sp.indiv.ci.dbsvr;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import javax.naming.NamingException;

import com.sp.indiv.ci.schema.CIInsurePmDriverSchema;
import com.sp.indiv.ci.schema.CIInsurePmVehicleSchema;
import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;

/**
 * ����XX��ѯ����-CIInsurePmDriver��DB��
 *
 * @createdate 2014-01-24
 * @author fanjiangtao
 * 
 */
public class DBCIInsurePmDriver extends CIInsurePmDriverSchema{
    /**
     * ���캯��
     */       
    public DBCIInsurePmDriver(){
    }

    /**
     * ����һ����¼
     * @param dbpool dbpool
     * @throws Exception
     */       
    public void insert(DbPool dbpool) throws Exception{
        String strSQL = " Insert Into CIInsurePmDriver(" + 
                           " DemandNo," + 
                           " Name," + 
                           " DriverTypeCode," + 
                           " LicenseNo," + 
                           " LicenseStatusCode," + 
                           " LicensedDate," + 
                           " InsertDate," + 
                           " UpdateDate," +  
                           " ValidFlag," +  
                           " OperateSite," +  
                           " Flag)values(" + 
                           "'" + getDemandNo() +"'" + "," +
                           "'" + getName() +"'" + "," +
                           "'" + getDriverTypeCode() +"'" + "," +
                           "'" + getLicenseNo() +"'" + "," +
                           "'" + getLicenseStatusCode() +"'" + "," +
                           "to_date('" + getLicensedDate() + "','yyyy-mm-dd')" + "," + 
                           "to_date('" + getInsertDate() + "','yyyy-mm-dd hh24:mi:ss')" + "," +     
                           "to_date('" + getUpdateDate() + "','yyyy-mm-dd hh24:mi:ss')" + "," +
                           "'" + getValidFlag() +"'" + "," +
                           "'" + getOperateSite() +"'" + "," + 
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
        
        try {
            String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
            dbpool.open("platformNewDataSource");            
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
        String strSQL = " SELECT COUNT(*) FROM CIInsurePmDriver WHERE "+ strWhere;
        ResultSet resultSet = dbpool.query(strSQL);
        if(resultSet.next()){
            intCount = resultSet.getInt(1);
            resultSet.close();
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
      String strSQL = " SELECT COUNT(1) FROM CIInsurePmDriver WHERE "+ strWhere;
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
            dbpool.open("platformNewDataSource");            
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

            dbpool.open("platformNewDataSource");            
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
        CIInsurePmDriverSchema cIInsurePmDriverSchema = null;
        ResultSet resultSet = dbpool.query(strSQL);
        while(resultSet.next())
        {
            cIInsurePmDriverSchema = new CIInsurePmDriverSchema();
            cIInsurePmDriverSchema.setDemandNo(resultSet.getString("demandNo"));
            cIInsurePmDriverSchema.setName(resultSet.getString("name"));
            cIInsurePmDriverSchema.setDriverTypeCode(resultSet.getString("driverTypeCode"));
            cIInsurePmDriverSchema.setLicenseNo(resultSet.getString("licenseNo"));
            cIInsurePmDriverSchema.setLicenseStatusCode(resultSet.getString("licenseStatusCode"));
            cIInsurePmDriverSchema.setLicensedDate(resultSet.getString("licensedDate"));
            cIInsurePmDriverSchema.setInsertDate(resultSet.getString("insertDate"));
            cIInsurePmDriverSchema.setUpdateDate(resultSet.getString("updateDate"));
            cIInsurePmDriverSchema.setValidFlag(resultSet.getString("validFlag"));
            cIInsurePmDriverSchema.setOperateSite(resultSet.getString("operateSite"));
            cIInsurePmDriverSchema.setFlag(resultSet.getString("flag"));
            vector.add(cIInsurePmDriverSchema);
        }
        resultSet.close();
        return vector;
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
        CIInsurePmDriverSchema cIInsurePmDriverSchema = null;
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
            cIInsurePmDriverSchema = new CIInsurePmDriverSchema();
            cIInsurePmDriverSchema.setDemandNo(resultSet.getString("demandNo"));
            cIInsurePmDriverSchema.setName(resultSet.getString("name"));
            cIInsurePmDriverSchema.setDriverTypeCode(resultSet.getString("driverTypeCode"));
            cIInsurePmDriverSchema.setLicenseNo(resultSet.getString("licenseNo"));
            cIInsurePmDriverSchema.setLicenseStatusCode(resultSet.getString("licenseStatusCode"));
            cIInsurePmDriverSchema.setLicensedDate(resultSet.getString("licensedDate"));
            cIInsurePmDriverSchema.setInsertDate(resultSet.getString("insertDate"));
            cIInsurePmDriverSchema.setUpdateDate(resultSet.getString("updateDate"));
            cIInsurePmDriverSchema.setValidFlag(resultSet.getString("validFlag"));
            cIInsurePmDriverSchema.setOperateSite(resultSet.getString("operateSite"));
            cIInsurePmDriverSchema.setFlag(resultSet.getString("flag"));
            vector.add(cIInsurePmDriverSchema);
        }
        resultSet.close();
        return vector;
    }
    
    public void delete(String LicenseNo) throws Exception{
        DbPool dbpool = new DbPool();
        
        try {
            dbpool.open("platformNewDataSource");
            dbpool.beginTransaction();
            delete(dbpool,LicenseNo);
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
    
    public void delete(DbPool dbpool,String LicenseNo) throws Exception{

  	  String strSQL = " Delete From PrpTmain Where LicenseNo = ?";
  	  dbpool.prepareInnerStatement(strSQL);
      dbpool.setString(1,LicenseNo);
      dbpool.executePreparedUpdate();
      dbpool.closePreparedStatement(); 
  }
    
    /**
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
