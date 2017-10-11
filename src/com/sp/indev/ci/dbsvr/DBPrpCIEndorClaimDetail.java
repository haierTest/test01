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
 * 定义PrpCIEndorClaimDetail的DB类
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>@createdate 2007-08-23</p>
 * @author DBGenerator
 * @version 1.0
 */
public class DBPrpCIEndorClaimDetail extends PrpCIEndorClaimDetailSchema{
    /**
     * 构造函数
     */       
    public DBPrpCIEndorClaimDetail(){
    }

    /**
     * 插入一条记录
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
     * 插入一条记录
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
     * 更新一条记录
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
        String strSQL = " SELECT COUNT(*) FROM PrpCIEndorClaimDetail WHERE "+ strWhere;
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
     * 主函数
     * @param args 参数列表
     */
    public static void main(String[] args){
        
    }
}
