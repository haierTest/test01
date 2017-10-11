package com.sp.indiv.ci.dbsvr;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.util.*;

import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.indiv.ci.schema.CIIdentifyInfoSchema;

/**
 * ����CIIdentifyInfo��DB��
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>@createdate 2015-01-11</p>
 * @author DBGenerator
 * @version 1.0
 */
public class DBCIIdentifyInfo extends CIIdentifyInfoSchema{
    /**
     * ���캯��
     */       
    public DBCIIdentifyInfo(){
    }

    /**
     * ����һ����¼
     * @param dbpool dbpool
     * @throws Exception
     */       
    public void insert(DbPool dbpool) throws Exception{
        String strSQL = " Insert Into CIIdentifyInfo(" + 
                           " BusinessNo," + 
                           " SerialNo," + 
                           " IdentifyNumber," + 
                           " IssueCode," + 
                           " UploadDate," + 
                           " Nation," + 
                           " Signer," + 
                           " CollectorCode," + 
                           " Flag) values(?,?,?,?,to_date(?,'yyyy-MM-dd hh24:mi:ss'),?,?,?,?)";
        dbpool.prepareInnerStatement(strSQL);
        int index = 1;
        dbpool.setString(index++,getBusinessNo());
        dbpool.setString(index++,getSerialNo());
        dbpool.setString(index++,getIdentifyNumber());
        dbpool.setString(index++,getIssueCode());
        dbpool.setString(index++,getUploadDate());
        dbpool.setString(index++,getNation());
        dbpool.setString(index++,getSigner());
        dbpool.setString(index++,getCollectorCode());
        dbpool.setString(index++,getFlag());
        dbpool.executePreparedUpdate();
        dbpool.closePreparedStatement();
    }

    /**
     * ����һ����¼
     * @throws Exception
     */       
    public void insert() throws Exception{
        DbPool dbpool = new DbPool();
        try{
			
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
        finally{
            dbpool.close();
        }
    }

    public void delete(DbPool dbpool,String businessNo,String serialNo) throws Exception{
        String strSQL = " Delete From CIIdentifyInfo Where BusinessNo = ?" +
                           " And SerialNo = ?";
        dbpool.prepareInnerStatement(strSQL);
        int index = 1;
        dbpool.setString(index++,businessNo);
        dbpool.setString(index++,serialNo);
        dbpool.executePreparedUpdate();
        dbpool.closePreparedStatement();
    }
    public void deleteByFlag(DbPool dbpool,String businessNo,String Flag) throws Exception{
        String strSQL = " Delete From CIIdentifyInfo Where BusinessNo = ?" +
                           " And Flag = ?";
        dbpool.prepareInnerStatement(strSQL);
        int index = 1;
        dbpool.setString(index++,businessNo);
        dbpool.setString(index++,Flag);
        dbpool.executePreparedUpdate();
        dbpool.closePreparedStatement();
    }
    public void delete(String businessNo,String serialNo) throws Exception{
        DbPool dbpool = new DbPool();
        try{
			
        	String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
            if ("1".equals(strSwitch)) {
                 dbpool.open("platformNewDataSource");            
            } else {
                 dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
            }
            dbpool.beginTransaction();
            delete(dbpool,businessNo,serialNo);
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
    
    public void delete(DbPool dbpool,String businessNo) throws Exception{
        String strSQL = " Delete From CIIdentifyInfo Where BusinessNo = ?";
        dbpool.prepareInnerStatement(strSQL);
        dbpool.setString(1,businessNo);
        dbpool.executePreparedUpdate();
        dbpool.closePreparedStatement();
    }

    public void delete(String businessNo) throws Exception{
        DbPool dbpool = new DbPool();
        try{
			
        	String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
            if ("1".equals(strSwitch)) {
                 dbpool.open("platformNewDataSource");            
            } else {
                 dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
            }
            dbpool.beginTransaction();
            delete(dbpool,businessNo);
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
        String strSQL = " Update CIIdentifyInfo Set" +
                           " BusinessNo = ?," +
                           " SerialNo = ?," +
                           " IdentifyNumber = ?," +
                           " IssueCode = ?," +
                           " UploadDate = to_date(?,'yyyy-MM-dd hh24:mi:ss')," +
                           " Nation = ?," +
                           " Signer = ?," +
                           " CollectorCode = ?," +
                           " Flag = ?" +
                           " Where BusinessNo = ?" +
                            " And SerialNo = ?"+
                            "And Flag =?";
        dbpool.prepareInnerStatement(strSQL);
        int index = 1;
        dbpool.setString(index++,getBusinessNo());
        dbpool.setString(index++,getSerialNo());
        dbpool.setString(index++,getIdentifyNumber());
        dbpool.setString(index++,getIssueCode());
        dbpool.setString(index++,getUploadDate());
        dbpool.setString(index++,getNation());
        dbpool.setString(index++,getSigner());
        dbpool.setString(index++,getCollectorCode());
        dbpool.setString(index++,getFlag());
        dbpool.setString(index++,getBusinessNo());
        dbpool.setString(index++,getSerialNo());
        dbpool.setString(index++,getFlag());
        dbpool.executePreparedUpdate();
        dbpool.closePreparedStatement();
    }

    /**
     * ����һ����¼
     * @throws Exception
     */       
    public void update() throws Exception{
        DbPool dbpool = new DbPool();
        try{
			
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
        finally{
            dbpool.close();
        }
    }

    public int getInfo(DbPool dbpool,String businessNo,String serialNo) throws Exception{
        int intResult = 0;
        String strSQL = " Select * From CIIdentifyInfo Where BusinessNo = ?" +
                            " And SerialNo = ?";
        dbpool.prepareInnerStatement(strSQL);
        int index = 1;
        dbpool.setString(index++,businessNo);
        dbpool.setString(index++,serialNo);
        ResultSet resultSet = dbpool.executePreparedQuery();
        if(resultSet.next()){
            setBusinessNo(resultSet.getString("businessNo"));
            setSerialNo("" + resultSet.getInt("serialNo"));
            setIdentifyNumber(resultSet.getString("identifyNumber"));
            setIssueCode(resultSet.getString("issueCode"));
            setUploadDate(resultSet.getString("uploadDate"));
            setNation(resultSet.getString("nation"));
            setSigner(resultSet.getString("signer"));
            setCollectorCode(resultSet.getString("collectorCode"));
            setFlag(resultSet.getString("flag"));
            intResult = 0;
        }
        else{
            intResult = 100;
        }
        resultSet.close();
        dbpool.closePreparedStatement();
        return intResult;
    }

    public int getInfo(String businessNo,String serialNo) throws Exception{
        int intResult = 0;
        DbPool dbpool = new DbPool();
        
        try{
        	String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
            if ("1".equals(strSwitch)) {
                 dbpool.open("platformNewDataSource");            
            } else {
                 dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
            }
            intResult=getInfo(dbpool,businessNo,serialNo);
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
        String strSQL = " SELECT COUNT(*) FROM CIIdentifyInfo WHERE "+ strWhere;
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
        try{
			
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
        finally{
            dbpool.close();
        }
        return intCount;
    }

    /**
     * ����SQL����ȡ�����
     * @param strSQL  SQL���
     * @param bindValues ����ֵ
     * @return Vector ��ѯ�����¼��
     * @throws SQLException    ���ݿ�����쳣��
     * @throws NamingException �����쳣��
     */
    public Vector findByConditions(String strSQL, ArrayList bindValues) throws
        Exception,SQLException,NamingException{
        Vector vector = new Vector();
        DbPool dbpool = new DbPool();
        try{
			
        	String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
            if ("1".equals(strSwitch)) {
                 dbpool.open("platformNewDataSource");            
            } else {
                 dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
            }
            vector=findByConditions(dbpool, strSQL, bindValues);
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
     * @param bindValues ����ֵ
     * @param strSQL  SQL���
     * @return Vector ��ѯ�����¼��
     * @throws SQLException    ���ݿ�����쳣��
     * @throws NamingException �����쳣��
     */
    public Vector findByConditions(DbPool dbpool, String strSQL, ArrayList bindValues) throws
        SQLException,NamingException,Exception{
        Vector vector = new Vector();
        CIIdentifyInfoSchema cIIdentifyInfoSchema = null;
		ResultSet resultSet = null;
        if(bindValues != null){
			dbpool.prepareInnerStatement(strSQL);
            for(int i=0;i<bindValues.size();i++)
            {
          	  dbpool.setString(i+1,(bindValues.get(i)).toString());
            }
			resultSet = dbpool.executePreparedQuery();
        }else{
      	  resultSet = dbpool.query(strSQL);
        }

        while(resultSet.next())
        {
            cIIdentifyInfoSchema = new CIIdentifyInfoSchema();
            cIIdentifyInfoSchema.setBusinessNo(resultSet.getString("businessNo"));
            cIIdentifyInfoSchema.setSerialNo("" + resultSet.getInt("serialNo"));
            cIIdentifyInfoSchema.setIdentifyNumber(resultSet.getString("identifyNumber"));
            cIIdentifyInfoSchema.setIssueCode(resultSet.getString("issueCode"));
            cIIdentifyInfoSchema.setUploadDate(resultSet.getString("uploadDate"));
            cIIdentifyInfoSchema.setNation(resultSet.getString("nation"));
            cIIdentifyInfoSchema.setSigner(resultSet.getString("signer"));
            cIIdentifyInfoSchema.setCollectorCode(resultSet.getString("collectorCode"));
            cIIdentifyInfoSchema.setFlag(resultSet.getString("flag"));
            vector.add(cIIdentifyInfoSchema);
        }
        resultSet.close();
        return vector;
    }
	/**
     * ����������ȡ����������������
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
     * ����������ȡ��ѯ��������
     * @param dbpool
     * @param strWhere
     * @param iWhereValue
     * @return
     * @throws Exception
     */
    public int getCount(DbPool dbpool,String strWhere,ArrayList iWhereValue)
    throws Exception{
  	  int intCount = 0;
  	  String strSQL = " SELECT COUNT(*) FROM CIIDENTIFYINFO WHERE "+ strWhere;
  	  
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
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
