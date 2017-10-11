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
 * 定义CIIdentifyInfo的DB类
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>@createdate 2015-01-11</p>
 * @author DBGenerator
 * @version 1.0
 */
public class DBCIIdentifyInfo extends CIIdentifyInfoSchema{
    /**
     * 构造函数
     */       
    public DBCIIdentifyInfo(){
    }

    /**
     * 插入一条记录
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
     * 插入一条记录
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
     * 更新一条记录
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
        String strSQL = " SELECT COUNT(*) FROM CIIdentifyInfo WHERE "+ strWhere;
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
     * 根据SQL语句获取结果集
     * @param strSQL  SQL语句
     * @param bindValues 参数值
     * @return Vector 查询结果记录集
     * @throws SQLException    数据库操作异常类
     * @throws NamingException 名字异常类
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
     * 根据SQL语句获取结果集
     * @param dbpool  数据库连接池
     * @param bindValues 参数值
     * @param strSQL  SQL语句
     * @return Vector 查询结果记录集
     * @throws SQLException    数据库操作异常类
     * @throws NamingException 名字异常类
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
     * 根据条件获取满足条件数据条数
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
     * 根据条件获取查询数据条数
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
     * 主函数
     * @param args 参数列表
     */
    public static void main(String[] args){
        
    }
}
