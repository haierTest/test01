package com.sp.indiv.ci.dbsvr;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import javax.naming.NamingException;

import com.sp.indiv.ci.schema.PrpCIEndorClaimDetailSchema;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;

/**
 * ����PrpCIEndorClaimDetail��DB��
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>@createdate 2007-08-23</p>
 * @author DBGenerator
 * @version 1.0
 */
public class DBPrpCIEndorClaimDetail extends PrpCIEndorClaimDetailSchema{
    /**
     * ���캯��
     */       
    public DBPrpCIEndorClaimDetail(){
    }

    /**
     * ����һ����¼
     * @param dbpool dbpool
     * @throws Exception
     */       
    public void insert(DbPool dbpool) throws Exception{
        String strSQL = " Insert Into PrpCIEndorClaimDetail(" + 
                           " DemandNo," + 
                           " FSerialNo," + 
                           " SerialNo," + 
                           " PolicyNo," + 
                           " LossFee," + 
                           " KindCode," + 
                           " Remarks," + 
                           " Flag) values(" + 
                           "'" + getDemandNo() +"'" + "," +
                           "'" + getFSerialNo() +"'" + "," +
                           "'" + getSerialNo() +"'" + "," +
                           "'" + getPolicyNo() +"'" + "," +
                           "'" + getLossFee() +"'" + "," +
                           "'" + getKindCode() +"'" + "," +
                           "'" + getRemarks() +"'" + "," +
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

    public void delete(DbPool dbpool,String demandNo,String fSerialNo,String serialNo) throws Exception{
        String strSQL = " Delete From PrpCIEndorClaimDetail Where DemandNo = " + "'" + demandNo + "'" +
                            " And FSerialNo = " + "'" + fSerialNo + "'" +
                            " And SerialNo = " + "'" + serialNo + "'";
        dbpool.delete(strSQL);
    }

    public void delete(String demandNo,String fSerialNo,String serialNo) throws Exception{
        DbPool dbpool = new DbPool();
        
        dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
        try{
            dbpool.beginTransaction();
            delete(dbpool,demandNo,fSerialNo,serialNo);
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
        String strSQL = " Update PrpCIEndorClaimDetail Set" +
                           " DemandNo = " + "'" +getDemandNo() + "'" + "," +
                           " FSerialNo = " + "'" +getFSerialNo() + "'" + "," +
                           " SerialNo = " + "'" +getSerialNo() + "'" + "," +
                           " PolicyNo = " + "'" +getPolicyNo() + "'" + "," +
                           " LossFee = " + "'" +getLossFee() + "'" + "," +
                           " KindCode = " + "'" +getKindCode() + "'" + "," +
                           " Remarks = " + "'" +getRemarks() + "'" + "," +
                           " Flag = " + "'" +getFlag() + "'" +
                           " Where DemandNo = " + "'" +getDemandNo() + "'" +
                            " And FSerialNo = " + "'" +getFSerialNo() + "'" +
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

    public int getInfo(DbPool dbpool,String demandNo,String fSerialNo,String serialNo) throws Exception{
        int intResult = 0;
        String strSQL = " Select * From PrpCIEndorClaimDetail Where DemandNo = " + "'" + demandNo + "'" +
                            " And FSerialNo = " + "'" + fSerialNo + "'" +
                            " And SerialNo = " + "'" + serialNo + "'";
        ResultSet resultSet = dbpool.query(strSQL);
        if(resultSet.next()){
            setDemandNo(resultSet.getString("demandNo"));
            setFSerialNo("" + resultSet.getInt("fSerialNo"));
            setSerialNo("" + resultSet.getInt("serialNo"));
            setPolicyNo(resultSet.getString("policyNo"));
            setLossFee("" + resultSet.getDouble("lossFee"));
            setKindCode(resultSet.getString("kindCode"));
            setRemarks(resultSet.getString("remarks"));
            setFlag(resultSet.getString("flag"));
            intResult = 0;
        }
        else{
            intResult = 100;
        }
        resultSet.close();
        return intResult;
    }

    public int getInfo(String demandNo,String fSerialNo,String serialNo) throws Exception{
        int intResult = 0;
        DbPool dbpool = new DbPool();
        String strDataSource =SysConst.getProperty("DDCCDATASOURCE");
        
        dbpool.open(strDataSource);
        try{
            intResult=getInfo(dbpool,demandNo,fSerialNo,serialNo);
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
        String strSQL = " SELECT COUNT(*) FROM PrpCIEndorClaimDetail WHERE "+ strWhere;
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
        PrpCIEndorClaimDetailSchema prpCIEndorClaimDetailSchema = null;
        ResultSet resultSet = dbpool.query(strSQL);
        while(resultSet.next())
        {
            prpCIEndorClaimDetailSchema = new PrpCIEndorClaimDetailSchema();
            prpCIEndorClaimDetailSchema.setDemandNo(resultSet.getString("demandNo"));
            prpCIEndorClaimDetailSchema.setFSerialNo("" + resultSet.getInt("fSerialNo"));
            prpCIEndorClaimDetailSchema.setSerialNo("" + resultSet.getInt("serialNo"));
            prpCIEndorClaimDetailSchema.setPolicyNo(resultSet.getString("policyNo"));
            prpCIEndorClaimDetailSchema.setLossFee("" + resultSet.getDouble("lossFee"));
            prpCIEndorClaimDetailSchema.setKindCode(resultSet.getString("kindCode"));
            prpCIEndorClaimDetailSchema.setRemarks(resultSet.getString("remarks"));
            prpCIEndorClaimDetailSchema.setFlag(resultSet.getString("flag"));
            vector.add(prpCIEndorClaimDetailSchema);
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
      String strSQL = " SELECT COUNT(1) FROM PrpCIEndorClaimDetail WHERE "+ strWhere;
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
        PrpCIEndorClaimDetailSchema prpCIEndorClaimDetailSchema = null;
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
            prpCIEndorClaimDetailSchema = new PrpCIEndorClaimDetailSchema();
            prpCIEndorClaimDetailSchema.setDemandNo(resultSet.getString("demandNo"));
            prpCIEndorClaimDetailSchema.setFSerialNo("" + resultSet.getInt("fSerialNo"));
            prpCIEndorClaimDetailSchema.setSerialNo("" + resultSet.getInt("serialNo"));
            prpCIEndorClaimDetailSchema.setPolicyNo(resultSet.getString("policyNo"));
            prpCIEndorClaimDetailSchema.setLossFee("" + resultSet.getDouble("lossFee"));
            prpCIEndorClaimDetailSchema.setKindCode(resultSet.getString("kindCode"));
            prpCIEndorClaimDetailSchema.setRemarks(resultSet.getString("remarks"));
            prpCIEndorClaimDetailSchema.setFlag(resultSet.getString("flag"));
            vector.add(prpCIEndorClaimDetailSchema);
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
