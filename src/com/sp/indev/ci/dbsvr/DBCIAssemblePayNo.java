package com.sp.indiv.ci.dbsvr;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import javax.naming.NamingException;

import com.sp.indiv.ci.schema.CIAssemblePayNoSchema;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;

/**
 * 定义车船税汇总完税凭证表的DB类
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>@createdate 2008-01-21</p>
 * @author DBGenerator
 * @version 1.0
 */
public class DBCIAssemblePayNo extends CIAssemblePayNoSchema{
    /**
     * 构造函数
     */       
    public DBCIAssemblePayNo(){
    }

    /**
     * 插入一条记录
     * @param dbpool dbpool
     * @throws Exception
     */       
    public void insert(DbPool dbpool) throws Exception{
        String strSQL = " Insert Into CIAssemblePayNo(" + 
                           " CheckNo," + 
                           " StartDate," + 
                           " EndDate," + 
                           " DeclareNo," + 
                           " ResponseCode," + 
                           " Remark," + 
                           " ExtendChar1," + 
                           " Flag," + 
                           " PayBookNum) values(?,?,?,?,?,?,?,?,?)";
		dbpool.prepareStatement(strSQL);
		int index = 1;
		dbpool.setString(index++, getCheckNo());
		dbpool.setString(index++, getStartDate());
		dbpool.setString(index++, getEndDate());
		dbpool.setString(index++, getDeclareNo());
		dbpool.setString(index++, getResponseCode());
		dbpool.setString(index++, getRemark());
		dbpool.setString(index++, getExtendChar1());
		dbpool.setString(index++, getFlag());
		dbpool.setString(index++, getPayBookNum());
		dbpool.executePreparedUpdate();
		dbpool.closePreparedStatement();

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

    public void delete(DbPool dbpool,String checkNo) throws Exception{
        String strSQL = " Delete From CIAssemblePayNo Where CheckNo = ?";
		dbpool.prepareStatement(strSQL);
		dbpool.setString(1, checkNo);
		dbpool.executePreparedUpdate();
		dbpool.closePreparedStatement();
    }

    public void delete(String checkNo) throws Exception{
        DbPool dbpool = new DbPool();
        
        dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
        try{
            dbpool.beginTransaction();
            delete(dbpool,checkNo);
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
        String strSQL = " Update CIAssemblePayNo Set" +
                           " CheckNo = ?," +
                           " StartDate = ?, " +
                           " EndDate = ?," +
                           " DeclareNo = ?, " +
                           " ResponseCode = ?,"  +
                           " Remark = ?" +
                           " ExtendChar1 = ?" +
                           " Flag = ?,"+
                           " PayBookNum = ?" +
                           " Where CheckNo = ?";
		dbpool.prepareStatement(strSQL);
		int index = 1;
		dbpool.setString(index++, getCheckNo());
		dbpool.setString(index++, getStartDate());
		dbpool.setString(index++, getEndDate());
		dbpool.setString(index++, getDeclareNo());
		dbpool.setString(index++, getResponseCode());
		dbpool.setString(index++, getRemark());
		dbpool.setString(index++, getExtendChar1());
		dbpool.setString(index++, getFlag());
		dbpool.setString(index++, getPayBookNum());
		dbpool.executePreparedUpdate();
		dbpool.closePreparedStatement();
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

    public int getInfo(DbPool dbpool,String checkNo) throws Exception{
        int intResult = 0;
        String strSQL = " Select * From CIAssemblePayNo Where CheckNo = ?";
		dbpool.prepareStatement(strSQL);
		dbpool.setString(1, checkNo);
        ResultSet resultSet = dbpool.executePreparedQuery();
        if(resultSet.next()){
            setCheckNo(resultSet.getString("checkNo"));
            setStartDate("" + resultSet.getDate("startDate"));
            setEndDate("" + resultSet.getDate("endDate"));
            setDeclareNo(resultSet.getString("declareNo"));
            setResponseCode(resultSet.getString("responseCode"));
            setRemark(resultSet.getString("remark"));
            setExtendChar1(resultSet.getString("extendChar1"));
            setFlag(resultSet.getString("flag"));
            setPayBookNum(resultSet.getString("payBookNum"));
            intResult = 0;
        }
        else{
            intResult = 100;
        }
        resultSet.close();
		dbpool.closePreparedStatement();
        return intResult;
    }

    public int getInfo(String checkNo) throws Exception{
        int intResult = 0;
        DbPool dbpool = new DbPool();
        String strDataSource =SysConst.getProperty("DDCCDATASOURCE");
        
        dbpool.open(strDataSource);
        try{
            intResult=getInfo(dbpool,checkNo);
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
        String strSQL = " SELECT COUNT(*) FROM CIAssemblePayNo WHERE "+ strWhere;
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
        CIAssemblePayNoSchema cIAssemblePayNoSchema = null;
        ResultSet resultSet = dbpool.query(strSQL);
        while(resultSet.next())
        {
            cIAssemblePayNoSchema = new CIAssemblePayNoSchema();
            cIAssemblePayNoSchema.setCheckNo(resultSet.getString("checkNo"));
            cIAssemblePayNoSchema.setStartDate("" + resultSet.getDate("startDate"));
            cIAssemblePayNoSchema.setEndDate("" + resultSet.getDate("endDate"));
            cIAssemblePayNoSchema.setDeclareNo(resultSet.getString("declareNo"));
            cIAssemblePayNoSchema.setResponseCode(resultSet.getString("responseCode"));
            cIAssemblePayNoSchema.setRemark(resultSet.getString("remark"));
            cIAssemblePayNoSchema.setExtendChar1(resultSet.getString("extendChar1"));
            cIAssemblePayNoSchema.setFlag(resultSet.getString("flag"));
            cIAssemblePayNoSchema.setPayBookNum(resultSet.getString("payBookNum"));
            vector.add(cIAssemblePayNoSchema);
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
      String strSQL = " SELECT COUNT(1) FROM CIAssemblePayNo WHERE "+ strWhere;
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
        CIAssemblePayNoSchema cIAssemblePayNoSchema = null;
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
            cIAssemblePayNoSchema = new CIAssemblePayNoSchema();
            cIAssemblePayNoSchema.setCheckNo(resultSet.getString("checkNo"));
            cIAssemblePayNoSchema.setStartDate("" + resultSet.getDate("startDate"));
            cIAssemblePayNoSchema.setEndDate("" + resultSet.getDate("endDate"));
            cIAssemblePayNoSchema.setDeclareNo(resultSet.getString("declareNo"));
            cIAssemblePayNoSchema.setResponseCode(resultSet.getString("responseCode"));
            cIAssemblePayNoSchema.setRemark(resultSet.getString("remark"));
            cIAssemblePayNoSchema.setExtendChar1(resultSet.getString("extendChar1"));
            cIAssemblePayNoSchema.setFlag(resultSet.getString("flag"));
            cIAssemblePayNoSchema.setPayBookNum(resultSet.getString("payBookNum"));
            vector.add(cIAssemblePayNoSchema);
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
