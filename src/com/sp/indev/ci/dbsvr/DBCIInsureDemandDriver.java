package com.sp.indiv.ci.dbsvr;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import javax.naming.NamingException;

import com.sp.indiv.ci.schema.CIInsureDemandDriverSchema;
import com.sp.indiv.ci.schema.CIInsureDemandDriverSchema;
import com.sp.indiv.ci.schema.CIInsurePmVehicleSchema;
import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;

/**
 * 定义XX查询主表-CIInsurePmDriver的DB类
 *
 * @createdate 2014-01-24
 * @author fanjiangtao
 * 
 */
public class DBCIInsureDemandDriver extends CIInsureDemandDriverSchema{
    /**
     * 构造函数
     */       
    public DBCIInsureDemandDriver(){
    }

    /**
     * 插入一条记录
     * @param dbpool dbpool
     * @throws Exception
     */       
    public void insert(DbPool dbpool) throws Exception{
        String strSQL = " Insert Into CIInsurePmDriver(" + 
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
     * 插入一条记录
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
        String strSQL = " SELECT COUNT(*) FROM CIInsurePmDriver WHERE "+ strWhere;
        ResultSet resultSet = dbpool.query(strSQL);
        if(resultSet.next()){
            intCount = resultSet.getInt(1);
            resultSet.close();
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
     * 查询满足模糊查询条件的记录数
     * @param strSQL  SQLStatement
     * @return 满足模糊查询条件的记录数
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
        CIInsureDemandDriverSchema ciInsureDemandDriverSchema = null;
        ResultSet resultSet = dbpool.query(strSQL);
        while(resultSet.next())
        {
        	ciInsureDemandDriverSchema = new CIInsureDemandDriverSchema();
        	ciInsureDemandDriverSchema.setDemandNo(resultSet.getString("demandNo"));
        	ciInsureDemandDriverSchema.setSerialNo(resultSet.getString("serialNo"));
        	ciInsureDemandDriverSchema.setName(resultSet.getString("name"));
        	ciInsureDemandDriverSchema.setDriverTypeCode(resultSet.getString("driverTypeCode"));
        	ciInsureDemandDriverSchema.setLicenseNo(resultSet.getString("licenseNo"));
        	ciInsureDemandDriverSchema.setLicenseStatusCode(resultSet.getString("licenseStatusCode"));
        	ciInsureDemandDriverSchema.setLicensedDate(""+resultSet.getDate("licensedDate"));
            vector.add(ciInsureDemandDriverSchema);
        }
        resultSet.close();
        return vector;
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
        CIInsureDemandDriverSchema ciInsureDemandDriverSchema = null;
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
        	ciInsureDemandDriverSchema = new CIInsureDemandDriverSchema();
        	ciInsureDemandDriverSchema.setDemandNo(resultSet.getString("demandNo"));
        	ciInsureDemandDriverSchema.setSerialNo(resultSet.getString("serialNo"));
        	ciInsureDemandDriverSchema.setName(resultSet.getString("name"));
        	ciInsureDemandDriverSchema.setDriverTypeCode(resultSet.getString("driverTypeCode"));
        	ciInsureDemandDriverSchema.setLicenseNo(resultSet.getString("licenseNo"));
        	ciInsureDemandDriverSchema.setLicenseStatusCode(resultSet.getString("licenseStatusCode"));
        	ciInsureDemandDriverSchema.setLicensedDate(""+resultSet.getDate("licensedDate"));
            vector.add(ciInsureDemandDriverSchema);
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
