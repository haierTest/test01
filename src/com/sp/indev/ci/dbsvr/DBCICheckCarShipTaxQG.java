package com.sp.indiv.ci.dbsvr;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import javax.naming.NamingException;

import com.sp.indiv.ci.schema.CICheckCarShipTaxQGSchema;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;

/**
 * 定义CICheckCarShipTaxQG的DB类
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>@createdate 2009-06-02</p>
 * @author DBGenerator
 * @version 1.0
 */
public class DBCICheckCarShipTaxQG extends CICheckCarShipTaxQGSchema{
    /**
     * 构造函数
     */       
    public DBCICheckCarShipTaxQG(){
    }

    /**
     * 插入一条记录
     * @param dbpool dbpool
     * @throws Exception
     */       
    public void insert(DbPool dbpool) throws Exception{
        String strSQL = " Insert Into CICheckCarShipTaxQG(" + 
                           " CheckNo," + 
                           " ComCode," + 
                           " StartDate," + 
                           " EndDate," + 
                           " TaxDeclareQueryNo," + 
                           " TaxDeclearConfirmNo," + 
                           " TaxPolicyCount," + 
                           " TaxPolicyMoney," + 
                           " TaxAmendCount," + 
                           " TaxAmendMoney," + 
                           " MTaxPolicyCount," + 
                           " MTaxPolicyMoney," + 
                           " MTaxAmendCount," + 
                           " MTaxAmendMoney," + 
                           " OperateDate," + 
                           " ConfirmOperateDate," + 
                           " OperateCode," + 
                           " UpdateCode," + 
                           " ExtendChar1," + 
                           " ExtendChar2," + 
                           " CorpDelayPay," + 
                           " CorpDelayPayReason," + 
                           " Flag) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        dbpool.prepareInnerStatement(strSQL);
        int index = 1;
        dbpool.setString(index++,getCheckNo());
        dbpool.setString(index++,getComCode());
        dbpool.setString(index++,getStartDate());
        dbpool.setString(index++,getEndDate());
        dbpool.setString(index++,getTaxDeclareQueryNo());
        dbpool.setString(index++,getTaxDeclearConfirmNo());
        dbpool.setString(index++,getTaxPolicyCount());
        dbpool.setString(index++,getTaxPolicyMoney());
        dbpool.setString(index++,getTaxAmendCount());
        dbpool.setString(index++,getTaxAmendMoney());
        dbpool.setString(index++,getMTaxPolicyCount());
        dbpool.setString(index++,getMTaxPolicyMoney());
        dbpool.setString(index++,getMTaxAmendCount());
        dbpool.setString(index++,getMTaxAmendMoney());
        dbpool.setString(index++,getOperateDate());
        dbpool.setString(index++,getConfirmOperateDate());
        dbpool.setString(index++,getOperateCode());
        dbpool.setString(index++,getUpdateCode());
        dbpool.setString(index++,getExtendChar1());
        dbpool.setString(index++,getExtendChar2());
        dbpool.setString(index++,getCorpDelayPay());
        dbpool.setString(index++,getCorpDelayPayReason());
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
    
    public void delete1(String taxDeclareQueryNo) throws Exception{
        DbPool dbpool = new DbPool();
        DBCICheckCarShipTaxDetailQG dbCICheckCarShipTaxDetailQG = new DBCICheckCarShipTaxDetailQG();
        
        dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
        try{
            dbpool.beginTransaction();
            delete(dbpool,taxDeclareQueryNo);
            dbCICheckCarShipTaxDetailQG.delete(dbpool,taxDeclareQueryNo);
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
    public void delete(DbPool dbpool,String taxDeclareQueryNo) throws Exception{
        String strSQL = " Delete From CICheckCarShipTaxQG Where TaxDeclareQueryNo = ?";
        dbpool.prepareInnerStatement(strSQL);
        int index = 1;
        dbpool.setString(index++,taxDeclareQueryNo);
        dbpool.executePreparedUpdate();
        dbpool.closePreparedStatement();
    }

    public void delete(String taxDeclareQueryNo) throws Exception{
        DbPool dbpool = new DbPool();
        
        dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
        try{
            dbpool.beginTransaction();
            delete(dbpool,taxDeclareQueryNo);
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
        String strSQL = " Update CICheckCarShipTaxQG Set" +
                           " CheckNo = ?," +
                           " ComCode = ?," +
                           " StartDate = ?," +
                           " EndDate = ?," +
                           " TaxDeclareQueryNo = ?," +
                           " TaxDeclearConfirmNo = ?," +
                           " TaxPolicyCount = ?," +
                           " TaxPolicyMoney = ?," +
                           " TaxAmendCount = ?," +
                           " TaxAmendMoney = ?," +
                           " MTaxPolicyCount = ?," +
                           " MTaxPolicyMoney = ?," +
                           " MTaxAmendCount = ?," +
                           " MTaxAmendMoney = ?," +
                           " OperateDate = ?," +
                           " ConfirmOperateDate = ?," +
                           " OperateCode = ?," +
                           " UpdateCode = ?," +
                           " ExtendChar1 = ?," +
                           " ExtendChar2 = ?," +
                           " CorpDelayPay = ?," +
                           " CorpDelayPayReason = ?," +
                           " Flag = ?" +
                           " Where TaxDeclareQueryNo = ?";
        dbpool.prepareInnerStatement(strSQL);
        int index = 1;
        dbpool.setString(index++,getCheckNo());
        dbpool.setString(index++,getComCode());
        dbpool.setString(index++,getStartDate());
        dbpool.setString(index++,getEndDate());
        dbpool.setString(index++,getTaxDeclareQueryNo());
        dbpool.setString(index++,getTaxDeclearConfirmNo());
        dbpool.setString(index++,getTaxPolicyCount());
        dbpool.setString(index++,getTaxPolicyMoney());
        dbpool.setString(index++,getTaxAmendCount());
        dbpool.setString(index++,getTaxAmendMoney());
        dbpool.setString(index++,getMTaxPolicyCount());
        dbpool.setString(index++,getMTaxPolicyMoney());
        dbpool.setString(index++,getMTaxAmendCount());
        dbpool.setString(index++,getMTaxAmendMoney());
        dbpool.setString(index++,getOperateDate());
        dbpool.setString(index++,getConfirmOperateDate());
        dbpool.setString(index++,getOperateCode());
        dbpool.setString(index++,getUpdateCode());
        dbpool.setString(index++,getExtendChar1());
        dbpool.setString(index++,getExtendChar2());
        dbpool.setString(index++,getCorpDelayPay());
        dbpool.setString(index++,getCorpDelayPayReason());
        dbpool.setString(index++,getFlag());
        dbpool.setString(index++,getTaxDeclareQueryNo());
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

    public int getInfo(DbPool dbpool,String taxDeclareQueryNo) throws Exception{
        int intResult = 0;
        String strSQL = " Select checkNo,comCode,to_char(startDate,'yyyy-mm-dd') startDate,to_char(endDate,'yyyy-mm-dd') endDate, "
                      +" taxDeclareQueryNo,taxDeclearConfirmNo,taxPolicyCount,taxPolicyMoney,taxAmendCount,taxAmendMoney,"
                      +" mTaxPolicyCount,mTaxPolicyMoney,mTaxAmendCount,mTaxAmendMoney,to_char(operateDate,'yyyy-mm-dd') operateDate,"
                      +" to_char(confirmOperateDate,'yyyy-mm-dd') confirmOperateDate,operateCode,updateCode,extendChar1,extendChar2,"
                      +" corpDelayPay,corpDelayPayReason,flag From CICheckCarShipTaxQG Where TaxDeclareQueryNo = ?";
        dbpool.prepareInnerStatement(strSQL);
        int index = 1;
        dbpool.setString(index++,taxDeclareQueryNo);
        ResultSet resultSet = dbpool.executePreparedQuery();
        if(resultSet.next()){
            setCheckNo(resultSet.getString("checkNo"));
            setComCode(resultSet.getString("comCode"));
            setStartDate(resultSet.getString("startDate"));
            setEndDate(resultSet.getString("endDate"));
            setTaxDeclareQueryNo(resultSet.getString("taxDeclareQueryNo"));
            setTaxDeclearConfirmNo(resultSet.getString("taxDeclearConfirmNo"));
            setTaxPolicyCount(resultSet.getString("taxPolicyCount"));
            setTaxPolicyMoney(resultSet.getString("taxPolicyMoney"));
            setTaxAmendCount(resultSet.getString("taxAmendCount"));
            setTaxAmendMoney(resultSet.getString("taxAmendMoney"));
            setMTaxPolicyCount(resultSet.getString("mTaxPolicyCount"));
            setMTaxPolicyMoney(resultSet.getString("mTaxPolicyMoney"));
            setMTaxAmendCount(resultSet.getString("mTaxAmendCount"));
            setMTaxAmendMoney(resultSet.getString("mTaxAmendMoney"));
            setOperateDate(resultSet.getString("operateDate"));
            setConfirmOperateDate(resultSet.getString("confirmOperateDate"));
            setOperateCode(resultSet.getString("operateCode"));
            setUpdateCode(resultSet.getString("updateCode"));
            setExtendChar1(resultSet.getString("extendChar1"));
            setExtendChar2(resultSet.getString("extendChar2"));
            setCorpDelayPay(resultSet.getString("corpDelayPay"));
            setCorpDelayPayReason(resultSet.getString("corpDelayPayReason"));
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

    public int getInfo(String taxDeclareQueryNo) throws Exception{
        int intResult = 0;
        DbPool dbpool = new DbPool();
        String strDataSource =SysConst.getProperty("DDCCDATASOURCE");
        
        dbpool.open(strDataSource);
        try{
            intResult=getInfo(dbpool,taxDeclareQueryNo);
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
        String strSQL = " SELECT COUNT(*) FROM CICheckCarShipTaxQG WHERE "+ strWhere;
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
        CICheckCarShipTaxQGSchema cICheckCarShipTaxQGSchema = null;
        ResultSet resultSet = dbpool.query(strSQL);
        while(resultSet.next())
        {
            cICheckCarShipTaxQGSchema = new CICheckCarShipTaxQGSchema();
            cICheckCarShipTaxQGSchema.setCheckNo(resultSet.getString("checkNo"));
            cICheckCarShipTaxQGSchema.setComCode(resultSet.getString("comCode"));
            cICheckCarShipTaxQGSchema.setStartDate(resultSet.getString("startDate"));
            cICheckCarShipTaxQGSchema.setEndDate(resultSet.getString("endDate"));
            cICheckCarShipTaxQGSchema.setTaxDeclareQueryNo(resultSet.getString("taxDeclareQueryNo"));
            cICheckCarShipTaxQGSchema.setTaxDeclearConfirmNo(resultSet.getString("taxDeclearConfirmNo"));
            cICheckCarShipTaxQGSchema.setTaxPolicyCount(resultSet.getString("taxPolicyCount"));
            cICheckCarShipTaxQGSchema.setTaxPolicyMoney(resultSet.getString("taxPolicyMoney"));
            cICheckCarShipTaxQGSchema.setTaxAmendCount(resultSet.getString("taxAmendCount"));
            cICheckCarShipTaxQGSchema.setTaxAmendMoney(resultSet.getString("taxAmendMoney"));
            cICheckCarShipTaxQGSchema.setMTaxPolicyCount(resultSet.getString("mTaxPolicyCount"));
            cICheckCarShipTaxQGSchema.setMTaxPolicyMoney(resultSet.getString("mTaxPolicyMoney"));
            cICheckCarShipTaxQGSchema.setMTaxAmendCount(resultSet.getString("mTaxAmendCount"));
            cICheckCarShipTaxQGSchema.setMTaxAmendMoney(resultSet.getString("mTaxAmendMoney"));
            cICheckCarShipTaxQGSchema.setOperateDate(resultSet.getString("operateDate"));
            cICheckCarShipTaxQGSchema.setConfirmOperateDate(resultSet.getString("confirmOperateDate"));
            cICheckCarShipTaxQGSchema.setOperateCode(resultSet.getString("operateCode"));
            cICheckCarShipTaxQGSchema.setUpdateCode(resultSet.getString("updateCode"));
            cICheckCarShipTaxQGSchema.setExtendChar1(resultSet.getString("extendChar1"));
            cICheckCarShipTaxQGSchema.setExtendChar2(resultSet.getString("extendChar2"));
            cICheckCarShipTaxQGSchema.setCorpDelayPay(resultSet.getString("corpDelayPay"));
            cICheckCarShipTaxQGSchema.setCorpDelayPayReason(resultSet.getString("corpDelayPayReason"));
            cICheckCarShipTaxQGSchema.setFlag(resultSet.getString("flag"));
            vector.add(cICheckCarShipTaxQGSchema);
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
      String strSQL = " SELECT COUNT(1) FROM CICheckCarShipTaxQG WHERE "+ strWhere;
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
        CICheckCarShipTaxQGSchema cICheckCarShipTaxQGSchema = null;
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
            cICheckCarShipTaxQGSchema = new CICheckCarShipTaxQGSchema();
            cICheckCarShipTaxQGSchema.setCheckNo(resultSet.getString("checkNo"));
            cICheckCarShipTaxQGSchema.setComCode(resultSet.getString("comCode"));
            cICheckCarShipTaxQGSchema.setStartDate(resultSet.getString("startDate"));
            cICheckCarShipTaxQGSchema.setEndDate(resultSet.getString("endDate"));
            cICheckCarShipTaxQGSchema.setTaxDeclareQueryNo(resultSet.getString("taxDeclareQueryNo"));
            cICheckCarShipTaxQGSchema.setTaxDeclearConfirmNo(resultSet.getString("taxDeclearConfirmNo"));
            cICheckCarShipTaxQGSchema.setTaxPolicyCount(resultSet.getString("taxPolicyCount"));
            cICheckCarShipTaxQGSchema.setTaxPolicyMoney(resultSet.getString("taxPolicyMoney"));
            cICheckCarShipTaxQGSchema.setTaxAmendCount(resultSet.getString("taxAmendCount"));
            cICheckCarShipTaxQGSchema.setTaxAmendMoney(resultSet.getString("taxAmendMoney"));
            cICheckCarShipTaxQGSchema.setMTaxPolicyCount(resultSet.getString("mTaxPolicyCount"));
            cICheckCarShipTaxQGSchema.setMTaxPolicyMoney(resultSet.getString("mTaxPolicyMoney"));
            cICheckCarShipTaxQGSchema.setMTaxAmendCount(resultSet.getString("mTaxAmendCount"));
            cICheckCarShipTaxQGSchema.setMTaxAmendMoney(resultSet.getString("mTaxAmendMoney"));
            cICheckCarShipTaxQGSchema.setOperateDate(resultSet.getString("operateDate"));
            cICheckCarShipTaxQGSchema.setConfirmOperateDate(resultSet.getString("confirmOperateDate"));
            cICheckCarShipTaxQGSchema.setOperateCode(resultSet.getString("operateCode"));
            cICheckCarShipTaxQGSchema.setUpdateCode(resultSet.getString("updateCode"));
            cICheckCarShipTaxQGSchema.setExtendChar1(resultSet.getString("extendChar1"));
            cICheckCarShipTaxQGSchema.setExtendChar2(resultSet.getString("extendChar2"));
            cICheckCarShipTaxQGSchema.setCorpDelayPay(resultSet.getString("corpDelayPay"));
            cICheckCarShipTaxQGSchema.setCorpDelayPayReason(resultSet.getString("corpDelayPayReason"));
            cICheckCarShipTaxQGSchema.setFlag(resultSet.getString("flag"));
            vector.add(cICheckCarShipTaxQGSchema);
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
