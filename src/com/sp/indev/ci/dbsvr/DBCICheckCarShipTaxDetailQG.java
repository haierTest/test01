package com.sp.indiv.ci.dbsvr;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import javax.naming.NamingException;

import com.sp.indiv.ci.schema.CICheckCarShipTaxDetailQGSchema;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.string.ChgData;

/**
 * 定义CICheckCarShipTaxDetailQG的DB类
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>@createdate 2009-06-02</p>
 * @author DBGenerator
 * @version 1.0
 */
public class DBCICheckCarShipTaxDetailQG extends CICheckCarShipTaxDetailQGSchema{
    /**
     * 构造函数
     */       
    public DBCICheckCarShipTaxDetailQG(){
    }

    /**
     * 插入一条记录
     * @param dbpool dbpool
     * @throws Exception
     */       
    public void insert(DbPool dbpool) throws Exception{
        String strSQL = " Insert Into CICheckCarShipTaxDetailQG(" + 
                           " CheckNo," + 
                           " TaxDeclareQueryNo," + 
                           " ConFirmSequenceNo," + 
                           " Type," + 
                           " ProposalNo," + 
                           " CretiNo," + 
                           " SerialNo," + 
                           " TaxConditionCode," + 
                           " TaxAnnualTaxDue," + 
                           " TaxSumTaxDeFault," + 
                           " TaxSumOverDue," + 
                           " TaxSumTax," + 
                           " MTaxAnnualTaxDue," + 
                           " MTaxSumTaxDeFault," + 
                           " MTaxSumOverDue," + 
                           " MTaxSumTax," + 
                           " ExtendChar1," + 
                           
                           " SignDate," + 
                           " LicenseNo," + 
                           " FrameNo," + 
                           " EngineNo," + 
                           " Status," + 
                           " DeclareDate," + 
                           " ErrorMsg," + 
                           " CheckSuccessFlag," + 
                           " ComCode," + 
                           
                           " ExtendChar2) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,to_date(?,'yyyy-MM-dd hh24:mi:ss'),?,?,?,?,?,?,?,?,?)";
                           
        					
        dbpool.prepareInnerStatement(strSQL);
        int index = 1;
        dbpool.setString(index++,getCheckNo());
        dbpool.setString(index++,getTaxDeclareQueryNo());
        dbpool.setString(index++,getConFirmSequenceNo());
        dbpool.setString(index++,getType());
        dbpool.setString(index++,getProposalNo());
        dbpool.setString(index++,getCretiNo());
        dbpool.setString(index++,getSerialNo());
        dbpool.setString(index++,getTaxConditionCode());
        dbpool.setString(index++,getTaxAnnualTaxDue());
        dbpool.setString(index++,getTaxSumTaxDeFault());
        dbpool.setString(index++,getTaxSumOverDue());
        dbpool.setString(index++,getTaxSumTax());
        dbpool.setString(index++,getMTaxAnnualTaxDue());
        dbpool.setString(index++,getMTaxSumTaxDeFault());
        dbpool.setString(index++,getMTaxSumOverDue());
        dbpool.setString(index++,getMTaxSumTax());
        dbpool.setString(index++,getExtendChar1());
        
        dbpool.setString(index++,getSignDate());
        dbpool.setString(index++,getLicenseNo());
        dbpool.setString(index++,getFrameNo());
        dbpool.setString(index++,getEngineNo());
        dbpool.setString(index++,getStatus());
        dbpool.setString(index++,getDeclareDate());
        dbpool.setString(index++,getErrorMsg());
        dbpool.setString(index++,getCheckSuccessFlag());
        dbpool.setString(index++,getComCode());
        
        dbpool.setString(index++,getExtendChar2());
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

    public void delete(DbPool dbpool,String taxDeclareQueryNo) throws Exception{
        String strSQL = " Delete From CICheckCarShipTaxDetailQG Where taxDeclareQueryNo = ?";
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
        String strSQL = " Update CICheckCarShipTaxDetailQG Set" +
                           " CheckNo = ?," +
                           " TaxDeclareQueryNo = ?," +
                           " ConFirmSequenceNo = ?," +
                           " Type = ?," +
                           " ProposalNo = ?," +
                           " CretiNo = ?," +
                           " SerialNo = ?," +
                           " TaxConditionCode = ?," +
                           " TaxAnnualTaxDue = ?," +
                           " TaxSumTaxDeFault = ?," +
                           " TaxSumOverDue = ?," +
                           " TaxSumTax = ?," +
                           " MTaxAnnualTaxDue = ?," +
                           " MTaxSumTaxDeFault = ?," +
                           " MTaxSumOverDue = ?," +
                           " MTaxSumTax = ?," +
                           " ExtendChar1 = ?," +
                           
                           
                           " SignDate = to_date(?,'yyyy-MM-dd hh24:mi:ss')," +
                           
                           " LicenseNo = ?," +
                           " FrameNo = ?," +
                           " EngineNo = ?," +
                           " Status = ?," +
                           " DeclareDate = ?," +
                           " ErrorMsg = ?," +
                           " CheckSuccessFlag = ?," +
                           " ComCode = ?," +
                           
                           " ExtendChar2 = ?" +
                           " Where ConFirmSequenceNo = ?";
        dbpool.prepareInnerStatement(strSQL);
        int index = 1;
        dbpool.setString(index++,getCheckNo());
        dbpool.setString(index++,getTaxDeclareQueryNo());
        dbpool.setString(index++,getConFirmSequenceNo());
        dbpool.setString(index++,getType());
        dbpool.setString(index++,getProposalNo());
        dbpool.setString(index++,getCretiNo());
        dbpool.setString(index++,getSerialNo());
        dbpool.setString(index++,getTaxConditionCode());
        dbpool.setString(index++,getTaxAnnualTaxDue());
        dbpool.setString(index++,getTaxSumTaxDeFault());
        dbpool.setString(index++,getTaxSumOverDue());
        dbpool.setString(index++,getTaxSumTax());
        dbpool.setString(index++,getMTaxAnnualTaxDue());
        dbpool.setString(index++,getMTaxSumTaxDeFault());
        dbpool.setString(index++,getMTaxSumOverDue());
        dbpool.setString(index++,getMTaxSumTax());
        dbpool.setString(index++,getExtendChar1());
        
        dbpool.setString(index++,getSignDate());
        dbpool.setString(index++,getLicenseNo());
        dbpool.setString(index++,getFrameNo());
        dbpool.setString(index++,getEngineNo());
        dbpool.setString(index++,getStatus());
        dbpool.setString(index++,getDeclareDate());
        dbpool.setString(index++,getErrorMsg());
        dbpool.setString(index++,getCheckSuccessFlag());
        dbpool.setString(index++,getComCode());
        
        dbpool.setString(index++,getExtendChar2());
        dbpool.setString(index++,getConFirmSequenceNo());
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

    public int getInfo(DbPool dbpool,String conFirmSequenceNo) throws Exception{
        int intResult = 0;
        String strSQL = " Select * From CICheckCarShipTaxDetailQG Where ConFirmSequenceNo = ?";
        dbpool.prepareInnerStatement(strSQL);
        int index = 1;
        dbpool.setString(index++,conFirmSequenceNo);
        ResultSet resultSet = dbpool.executePreparedQuery();
        if(resultSet.next()){
            setCheckNo(resultSet.getString("checkNo"));
            setTaxDeclareQueryNo(resultSet.getString("TaxDeclareQueryNo"));
            setConFirmSequenceNo(resultSet.getString("conFirmSequenceNo"));
            setType(resultSet.getString("type"));
            setProposalNo(resultSet.getString("proposalNo"));
            setCretiNo(resultSet.getString("cretiNo"));
            setSerialNo(resultSet.getString("serialNo"));
            setTaxConditionCode(resultSet.getString("taxConditionCode"));
            setTaxAnnualTaxDue(resultSet.getString("taxAnnualTaxDue"));
            setTaxSumTaxDeFault(resultSet.getString("taxSumTaxDeFault"));
            setTaxSumOverDue(resultSet.getString("taxSumOverDue"));
            setTaxSumTax(resultSet.getString("taxSumTax"));
            setMTaxAnnualTaxDue(resultSet.getString("mTaxAnnualTaxDue"));
            setMTaxSumTaxDeFault(resultSet.getString("mTaxSumTaxDeFault"));
            setMTaxSumOverDue(resultSet.getString("mTaxSumOverDue"));
            setMTaxSumTax(resultSet.getString("mTaxSumTax"));
            setExtendChar1(resultSet.getString("extendChar1"));
            
            setSignDate("" + ChgData.nullToString(resultSet.getDate("SignDate")));
            setLicenseNo(resultSet.getString("licenseNo"));
            setFrameNo(resultSet.getString("frameNo"));
            setEngineNo(resultSet.getString("engineNo"));
            setStatus(resultSet.getString("status"));
            setDeclareDate("" + ChgData.nullToString(resultSet.getDate("DeclareDate")));
            setErrorMsg(resultSet.getString("errorMsg"));
            setCheckSuccessFlag(resultSet.getString("checkSuccessFlag"));
            setComCode(resultSet.getString("comCode"));
            
            setExtendChar2(resultSet.getString("extendChar2"));
            intResult = 0;
        }
        else{
            intResult = 100;
        }
        resultSet.close();
        dbpool.closePreparedStatement();
        return intResult;
    }

    public int getInfo(String conFirmSequenceNo) throws Exception{
        int intResult = 0;
        DbPool dbpool = new DbPool();
        String strDataSource =SysConst.getProperty("DDCCDATASOURCE");
        
        dbpool.open(strDataSource);
        try{
            intResult=getInfo(dbpool,conFirmSequenceNo);
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
        String strSQL = " SELECT COUNT(*) FROM CICheckCarShipTaxDetailQG WHERE "+ strWhere;
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
        CICheckCarShipTaxDetailQGSchema cICheckCarShipTaxDetailQGSchema = null;
        ResultSet resultSet = dbpool.query(strSQL);
        while(resultSet.next())
        {
            cICheckCarShipTaxDetailQGSchema = new CICheckCarShipTaxDetailQGSchema();
            cICheckCarShipTaxDetailQGSchema.setCheckNo(resultSet.getString("checkNo"));
            cICheckCarShipTaxDetailQGSchema.setTaxDeclareQueryNo(resultSet.getString("TaxDeclareQueryNo"));
            cICheckCarShipTaxDetailQGSchema.setConFirmSequenceNo(resultSet.getString("conFirmSequenceNo"));
            cICheckCarShipTaxDetailQGSchema.setType(resultSet.getString("type"));
            cICheckCarShipTaxDetailQGSchema.setProposalNo(resultSet.getString("proposalNo"));
            cICheckCarShipTaxDetailQGSchema.setCretiNo(resultSet.getString("cretiNo"));
            cICheckCarShipTaxDetailQGSchema.setSerialNo(resultSet.getString("serialNo"));
            cICheckCarShipTaxDetailQGSchema.setTaxConditionCode(resultSet.getString("taxConditionCode"));
            cICheckCarShipTaxDetailQGSchema.setTaxAnnualTaxDue(resultSet.getString("taxAnnualTaxDue"));
            cICheckCarShipTaxDetailQGSchema.setTaxSumTaxDeFault(resultSet.getString("taxSumTaxDeFault"));
            cICheckCarShipTaxDetailQGSchema.setTaxSumOverDue(resultSet.getString("taxSumOverDue"));
            cICheckCarShipTaxDetailQGSchema.setTaxSumTax(resultSet.getString("taxSumTax"));
            cICheckCarShipTaxDetailQGSchema.setMTaxAnnualTaxDue(resultSet.getString("mTaxAnnualTaxDue"));
            cICheckCarShipTaxDetailQGSchema.setMTaxSumTaxDeFault(resultSet.getString("mTaxSumTaxDeFault"));
            cICheckCarShipTaxDetailQGSchema.setMTaxSumOverDue(resultSet.getString("mTaxSumOverDue"));
            cICheckCarShipTaxDetailQGSchema.setMTaxSumTax(resultSet.getString("mTaxSumTax"));
            cICheckCarShipTaxDetailQGSchema.setExtendChar1(resultSet.getString("extendChar1"));
            
            cICheckCarShipTaxDetailQGSchema.setSignDate(""+resultSet.getDate("signDate"));
            cICheckCarShipTaxDetailQGSchema.setLicenseNo(resultSet.getString("licenseNo"));
            cICheckCarShipTaxDetailQGSchema.setFrameNo(resultSet.getString("frameNo"));
            cICheckCarShipTaxDetailQGSchema.setEngineNo(resultSet.getString("engineNo"));
            cICheckCarShipTaxDetailQGSchema.setStatus(resultSet.getString("status"));
            cICheckCarShipTaxDetailQGSchema.setDeclareDate(""+resultSet.getDate("declareDate"));
            cICheckCarShipTaxDetailQGSchema.setErrorMsg(resultSet.getString("errorMsg"));
            cICheckCarShipTaxDetailQGSchema.setCheckSuccessFlag(resultSet.getString("checkSuccessFlag"));
            cICheckCarShipTaxDetailQGSchema.setComCode(resultSet.getString("comCode"));
            
            cICheckCarShipTaxDetailQGSchema.setExtendChar2(resultSet.getString("extendChar2"));
            vector.add(cICheckCarShipTaxDetailQGSchema);
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
      String strSQL = " SELECT COUNT(1) FROM CICheckCarShipTaxDetailQG WHERE "+ strWhere;
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
        CICheckCarShipTaxDetailQGSchema cICheckCarShipTaxDetailQGSchema = null;
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
            cICheckCarShipTaxDetailQGSchema = new CICheckCarShipTaxDetailQGSchema();
            cICheckCarShipTaxDetailQGSchema.setCheckNo(resultSet.getString("checkNo"));
            cICheckCarShipTaxDetailQGSchema.setTaxDeclareQueryNo(resultSet.getString("TaxDeclareQueryNo"));
            cICheckCarShipTaxDetailQGSchema.setConFirmSequenceNo(resultSet.getString("conFirmSequenceNo"));
            cICheckCarShipTaxDetailQGSchema.setType(resultSet.getString("type"));
            cICheckCarShipTaxDetailQGSchema.setProposalNo(resultSet.getString("proposalNo"));
            cICheckCarShipTaxDetailQGSchema.setCretiNo(resultSet.getString("cretiNo"));
            cICheckCarShipTaxDetailQGSchema.setSerialNo(resultSet.getString("serialNo"));
            cICheckCarShipTaxDetailQGSchema.setTaxConditionCode(resultSet.getString("taxConditionCode"));
            cICheckCarShipTaxDetailQGSchema.setTaxAnnualTaxDue(resultSet.getString("taxAnnualTaxDue"));
            cICheckCarShipTaxDetailQGSchema.setTaxSumTaxDeFault(resultSet.getString("taxSumTaxDeFault"));
            cICheckCarShipTaxDetailQGSchema.setTaxSumOverDue(resultSet.getString("taxSumOverDue"));
            cICheckCarShipTaxDetailQGSchema.setTaxSumTax(resultSet.getString("taxSumTax"));
            cICheckCarShipTaxDetailQGSchema.setMTaxAnnualTaxDue(resultSet.getString("mTaxAnnualTaxDue"));
            cICheckCarShipTaxDetailQGSchema.setMTaxSumTaxDeFault(resultSet.getString("mTaxSumTaxDeFault"));
            cICheckCarShipTaxDetailQGSchema.setMTaxSumOverDue(resultSet.getString("mTaxSumOverDue"));
            cICheckCarShipTaxDetailQGSchema.setMTaxSumTax(resultSet.getString("mTaxSumTax"));
            cICheckCarShipTaxDetailQGSchema.setExtendChar1(resultSet.getString("extendChar1"));
            
            cICheckCarShipTaxDetailQGSchema.setSignDate(""+resultSet.getDate("SignDate"));
            cICheckCarShipTaxDetailQGSchema.setLicenseNo(resultSet.getString("LicenseNo"));
            cICheckCarShipTaxDetailQGSchema.setFrameNo(resultSet.getString("FrameNo"));
            cICheckCarShipTaxDetailQGSchema.setEngineNo(resultSet.getString("EngineNo"));
            cICheckCarShipTaxDetailQGSchema.setStatus(resultSet.getString("Status"));
            cICheckCarShipTaxDetailQGSchema.setDeclareDate(""+resultSet.getDate("DeclareDate"));
            cICheckCarShipTaxDetailQGSchema.setErrorMsg(resultSet.getString("ErrorMsg"));
            cICheckCarShipTaxDetailQGSchema.setCheckSuccessFlag(resultSet.getString("CheckSuccessFlag"));
            cICheckCarShipTaxDetailQGSchema.setComCode(resultSet.getString("ComCode"));
            
            cICheckCarShipTaxDetailQGSchema.setExtendChar2(resultSet.getString("extendChar2"));
            vector.add(cICheckCarShipTaxDetailQGSchema);
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
