package com.sp.indiv.ci.dbsvr;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import javax.naming.NamingException;

import com.sp.indiv.ci.schema.PrpCIEndorClaimDriverSchema;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;

/**
 * ����XX��ѯ����-PrpCIEndorClaimDriver��DB��
 *
 * @createdate 2014-01-24
 * @author fanjiangtao
 * 
 */
public class DBPrpCIEndorClaimDriver extends PrpCIEndorClaimDriverSchema{
    /**
     * ���캯��
     */       
    public DBPrpCIEndorClaimDriver(){
    }

    /**
     * ����һ����¼
     * @param dbpool dbpool
     * @throws Exception
     */       
    public void insert(DbPool dbpool) throws Exception{
        String strSQL = " Insert Into PrpCIEndorClaimDriver(" + 
                           " DemandNo," + 
                           " SerialNo," + 
                           " Name," + 
                           " DriverTypeCode," + 
                           " LicenseNo," + 
                           " LicenseStatusCode," + 
                           " LicensedDate)values(" + 
                           "'" + getDemandNo() +"'" + "," +
                           "'" + getSerialNo() +"'" + "," +
                           "'" + getName() +"'" + "," +
                           "'" + getDriverTypeCode() +"'" + "," +
                           "'" + getLicenseNo() +"'" + "," +
                           "'" + getLicenseStatusCode() +"'" + "," +
                           "to_date('" + getLicensedDate() + "','yyyy-mm-dd')" + "," + 
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
        String strSQL = " SELECT COUNT(*) FROM PrpCIEndorClaimDriver WHERE "+ strWhere;
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
      String strSQL = " SELECT COUNT(1) FROM PrpCIEndorClaimDriver WHERE "+ strWhere;
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
            dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
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
        PrpCIEndorClaimDriverSchema prpCIEndorClaimDriverSchema = null;
        ResultSet resultSet = dbpool.query(strSQL);
        while(resultSet.next())
        {
        	prpCIEndorClaimDriverSchema = new PrpCIEndorClaimDriverSchema();
        	prpCIEndorClaimDriverSchema.setDemandNo(resultSet.getString("demandNo"));
        	prpCIEndorClaimDriverSchema.setSerialNo(resultSet.getString("serialNo"));
        	prpCIEndorClaimDriverSchema.setName(resultSet.getString("name"));
        	prpCIEndorClaimDriverSchema.setDriverTypeCode(resultSet.getString("driverTypeCode"));
        	prpCIEndorClaimDriverSchema.setLicenseNo(resultSet.getString("licenseNo"));
        	prpCIEndorClaimDriverSchema.setLicenseStatusCode(resultSet.getString("licenseStatusCode"));
        	prpCIEndorClaimDriverSchema.setLicensedDate(""+resultSet.getDate("licensedDate"));
            vector.add(prpCIEndorClaimDriverSchema);
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
        PrpCIEndorClaimDriverSchema prpCIEndorClaimDriverSchema = null;
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
        	prpCIEndorClaimDriverSchema = new PrpCIEndorClaimDriverSchema();
        	prpCIEndorClaimDriverSchema.setDemandNo(resultSet.getString("demandNo"));
        	prpCIEndorClaimDriverSchema.setSerialNo(resultSet.getString("serialNo"));
        	prpCIEndorClaimDriverSchema.setName(resultSet.getString("name"));
        	prpCIEndorClaimDriverSchema.setDriverTypeCode(resultSet.getString("driverTypeCode"));
        	prpCIEndorClaimDriverSchema.setLicenseNo(resultSet.getString("licenseNo"));
        	prpCIEndorClaimDriverSchema.setLicenseStatusCode(resultSet.getString("licenseStatusCode"));
        	prpCIEndorClaimDriverSchema.setLicensedDate(""+resultSet.getDate("licensedDate"));
            vector.add(prpCIEndorClaimDriverSchema);
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