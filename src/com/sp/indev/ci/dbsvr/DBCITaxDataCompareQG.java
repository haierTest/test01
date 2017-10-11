package com.sp.indiv.ci.dbsvr;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import javax.naming.NamingException;

import com.sp.indiv.ci.schema.CITaxDataCompareQGSchema;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;

/**
 * 定义CITaxDataCompareQG的DB类
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>@createdate 2009-06-16</p>
 * @author DBGenerator
 * @version 1.0
 */
public class DBCITaxDataCompareQG extends CITaxDataCompareQGSchema{
    /**
     * 构造函数
     */       
    public DBCITaxDataCompareQG(){
    }

    /**
     * 插入一条记录
     * @param dbpool dbpool
     * @throws Exception
     */       
    public void insert(DbPool dbpool) throws Exception{
        String strSQL = " Insert Into CITaxDataCompareQG(" + 
                           " Type," + 
                           " ConFirmNo," + 
                           " TaxToTalPayIA," + 
                           " TaxToTalPayLT," + 
                           " DeclareStatusIA," + 
                           " DeclareStatusLT," + 
                           " TaxToTalPayIC," + 
                           " DeclareStatusIC," + 
                           " ISAmendment," + 
                           " ExtendChar1," + 
                           " ExtendChar2," + 
                           " StartDate," + 
                           " EndDate," + 
                           " CompareNo) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        dbpool.prepareInnerStatement(strSQL);
        int index = 1;
        dbpool.setString(index++,getType());
        dbpool.setString(index++,getConFirmNo());
        dbpool.setString(index++,getTaxToTalPayIA());
        dbpool.setString(index++,getTaxToTalPayLT());
        dbpool.setString(index++,getDeclareStatusIA());
        dbpool.setString(index++,getDeclareStatusLT());
        dbpool.setString(index++,getTaxToTalPayIC());
        dbpool.setString(index++,getDeclareStatusIC());
        dbpool.setString(index++,getISAmendment());
        dbpool.setString(index++,getExtendChar1());
        dbpool.setString(index++,getExtendChar2());
        dbpool.setString(index++,getStartDate());
        dbpool.setString(index++,getEndDate());
        dbpool.setString(index++,getCompareNo());
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

    public void delete(DbPool dbpool,String compareNo) throws Exception{
        String strSQL = " Delete From CITaxDataCompareQG Where CompareNo = ?";
        dbpool.prepareInnerStatement(strSQL);
        int index = 1;
        dbpool.setString(index++,compareNo);
        dbpool.executePreparedUpdate();
        dbpool.closePreparedStatement();
    }

    public void delete(String compareNo) throws Exception{
        DbPool dbpool = new DbPool();
        
        dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
        try{
            dbpool.beginTransaction();
            delete(dbpool,compareNo);
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
        String strSQL = " Update CITaxDataCompareQG Set" +
                           " Type = ?," +
                           " ConFirmNo = ?," +
                           " TaxToTalPayIA = ?," +
                           " TaxToTalPayLT = ?," +
                           " DeclareStatusIA = ?," +
                           " DeclareStatusLT = ?," +
                           " TaxToTalPayIC = ?," +
                           " DeclareStatusIC = ?," +
                           " ISAmendment = ?," +
                           " ExtendChar1 = ?," +
                           " ExtendChar2 = ?," +
                           " StartDate = ?," +
                           " EndDate = ?," +
                           " CompareNo = ?" +
                           " Where CompareNo = ?";
        dbpool.prepareInnerStatement(strSQL);
        int index = 1;
        dbpool.setString(index++,getType());
        dbpool.setString(index++,getConFirmNo());
        dbpool.setString(index++,getTaxToTalPayIA());
        dbpool.setString(index++,getTaxToTalPayLT());
        dbpool.setString(index++,getDeclareStatusIA());
        dbpool.setString(index++,getDeclareStatusLT());
        dbpool.setString(index++,getTaxToTalPayIC());
        dbpool.setString(index++,getDeclareStatusIC());
        dbpool.setString(index++,getISAmendment());
        dbpool.setString(index++,getExtendChar1());
        dbpool.setString(index++,getExtendChar2());
        dbpool.setString(index++,getStartDate());
        dbpool.setString(index++,getEndDate());
        dbpool.setString(index++,getCompareNo());
        dbpool.setString(index++,getCompareNo());
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

    public int getInfo(DbPool dbpool,String compareNo) throws Exception{
        int intResult = 0;
        String strSQL = " SELECT TYPE,CONFIRMNO,TAXTOTALPAYIA,TAXTOTALPAYLT,DECLARESTATUSIA,DECLARESTATUSLT,TAXTOTALPAYIC,DECLARESTATUSIC,"
            +" ISAMENDMENT,EXTENDCHAR1,EXTENDCHAR2,to_char(STARTDATE,'YYYY-MM-DD') STARTDATE,to_char(ENDDATE,'YYYY-MM-DD') ENDDATE,COMPARENO"
            +" FROM CITaxDataCompareQG Where CompareNo = ?" ;
        dbpool.prepareInnerStatement(strSQL);
        int index = 1;
        dbpool.setString(index++,compareNo);
        ResultSet resultSet = dbpool.executePreparedQuery();
        if(resultSet.next()){
            setType(resultSet.getString("type"));
            setConFirmNo(resultSet.getString("conFirmNo"));
            setTaxToTalPayIA(resultSet.getString("taxToTalPayIA"));
            setTaxToTalPayLT(resultSet.getString("taxToTalPayLT"));
            setDeclareStatusIA(resultSet.getString("declareStatusIA"));
            setDeclareStatusLT(resultSet.getString("declareStatusLT"));
            setTaxToTalPayIC(resultSet.getString("taxToTalPayIC"));
            setDeclareStatusIC(resultSet.getString("declareStatusIC"));
            setISAmendment(resultSet.getString("iSAmendment"));
            setExtendChar1(resultSet.getString("extendChar1"));
            setExtendChar2(resultSet.getString("extendChar2"));
            setStartDate(resultSet.getString("startDate"));
            setEndDate(resultSet.getString("endDate"));
            setCompareNo(resultSet.getString("compareNo"));
            intResult = 0;
        }
        else{
            intResult = 100;
        }
        resultSet.close();
        dbpool.closePreparedStatement();
        return intResult;
    }

    public int getInfo(String compareNo) throws Exception{
        int intResult = 0;
        DbPool dbpool = new DbPool();
        String strDataSource =SysConst.getProperty("DDCCDATASOURCE");
        
        dbpool.open(strDataSource);
        try{
            intResult=getInfo(dbpool,compareNo);
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
        String strSQL = " SELECT COUNT(*) FROM CITaxDataCompareQG WHERE "+ strWhere;
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
        CITaxDataCompareQGSchema cITaxDataCompareQGSchema = null;
        ResultSet resultSet = dbpool.query(strSQL);
        while(resultSet.next())
        {
            cITaxDataCompareQGSchema = new CITaxDataCompareQGSchema();
            cITaxDataCompareQGSchema.setType(resultSet.getString("type"));
            cITaxDataCompareQGSchema.setConFirmNo(resultSet.getString("conFirmNo"));
            cITaxDataCompareQGSchema.setTaxToTalPayIA(resultSet.getString("taxToTalPayIA"));
            cITaxDataCompareQGSchema.setTaxToTalPayLT(resultSet.getString("taxToTalPayLT"));
            cITaxDataCompareQGSchema.setDeclareStatusIA(resultSet.getString("declareStatusIA"));
            cITaxDataCompareQGSchema.setDeclareStatusLT(resultSet.getString("declareStatusLT"));
            cITaxDataCompareQGSchema.setTaxToTalPayIC(resultSet.getString("taxToTalPayIC"));
            cITaxDataCompareQGSchema.setDeclareStatusIC(resultSet.getString("declareStatusIC"));
            cITaxDataCompareQGSchema.setISAmendment(resultSet.getString("iSAmendment"));
            cITaxDataCompareQGSchema.setExtendChar1(resultSet.getString("extendChar1"));
            cITaxDataCompareQGSchema.setExtendChar2(resultSet.getString("extendChar2"));
            cITaxDataCompareQGSchema.setStartDate(resultSet.getString("startDate"));
            cITaxDataCompareQGSchema.setEndDate(resultSet.getString("endDate"));
            cITaxDataCompareQGSchema.setCompareNo(resultSet.getString("compareNo"));
            vector.add(cITaxDataCompareQGSchema);
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
      String strSQL = " SELECT COUNT(1) FROM CITaxDataCompareQG WHERE "+ strWhere;
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
        CITaxDataCompareQGSchema cITaxDataCompareQGSchema = null;
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
            cITaxDataCompareQGSchema = new CITaxDataCompareQGSchema();
            cITaxDataCompareQGSchema.setType(resultSet.getString("type"));
            cITaxDataCompareQGSchema.setConFirmNo(resultSet.getString("conFirmNo"));
            cITaxDataCompareQGSchema.setTaxToTalPayIA(resultSet.getString("taxToTalPayIA"));
            cITaxDataCompareQGSchema.setTaxToTalPayLT(resultSet.getString("taxToTalPayLT"));
            cITaxDataCompareQGSchema.setDeclareStatusIA(resultSet.getString("declareStatusIA"));
            cITaxDataCompareQGSchema.setDeclareStatusLT(resultSet.getString("declareStatusLT"));
            cITaxDataCompareQGSchema.setTaxToTalPayIC(resultSet.getString("taxToTalPayIC"));
            cITaxDataCompareQGSchema.setDeclareStatusIC(resultSet.getString("declareStatusIC"));
            cITaxDataCompareQGSchema.setISAmendment(resultSet.getString("iSAmendment"));
            cITaxDataCompareQGSchema.setExtendChar1(resultSet.getString("extendChar1"));
            cITaxDataCompareQGSchema.setExtendChar2(resultSet.getString("extendChar2"));
            cITaxDataCompareQGSchema.setStartDate(resultSet.getString("startDate"));
            cITaxDataCompareQGSchema.setEndDate(resultSet.getString("endDate"));
            cITaxDataCompareQGSchema.setCompareNo(resultSet.getString("compareNo"));
            vector.add(cITaxDataCompareQGSchema);
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
