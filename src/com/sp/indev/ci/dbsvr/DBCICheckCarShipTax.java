package com.sp.indiv.ci.dbsvr;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.util.*;

import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.indiv.ci.schema.CICheckCarShipTaxSchema;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 定义CICheckCarShipTax的DB类
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>@createdate 2008-01-21</p>
 * @author DBGenerator
 * @version 1.0
 */
public class DBCICheckCarShipTax extends CICheckCarShipTaxSchema{
	Log logger = LogFactory.getLog(getClass());
    /**
     * 构造函数
     */       
    public DBCICheckCarShipTax(){
    }

    /**
     * 插入一条记录
     * @param dbpool dbpool
     * @throws Exception
     */       
    public void insert(DbPool dbpool) throws Exception{
    	 String strSQL1 = " Insert Into CICheckCarShipTax(" +
    	" CheckNo," + 
        " ComCode," + 
        " StartDate," + 
        " EndDate," + 
        " MTotalPayNo," + 
        " TTotalPayNo," + 
        " MTotalTax," + 
        " TTotalTax," + 
        " OperateDate," + 
        " OperateCode," + 
        " ExtendChar1," + 
        " ExtendChar2," + 
        " CheckType," + 
		 " Flag) values(" +
		 "'" + getCheckNo() +"'" + "," +
		 "'" + getComCode() +"'" + "," +
		 "'" + getStartDate() +"'" + "," +
		 "'" + getEndDate() +"'" + "," +
		 "'" + getMTotalPayNo() +"'" + "," +
		 "'" + getTTotalPayNo() +"'" + "," +
		 "'" + getMTotalTax() +"'" + "," +
		 "'" + getTTotalTax() +"'" + "," +
		 "'" + getOperateDate() +"'" + "," +
		 "'" + getOperateCode() +"'" + "," +
		 "'" + getExtendChar1() +"'" + "," +
		 "'" + getExtendChar2() +"'" + "," +
		 "'" + getCheckType() +"'" + "," +
		 "'" + getFlag() +"'" +
		 ")";
		
		logger.info("strSQL1="+strSQL1);
		

    	String strSQL = " Insert Into CICheckCarShipTax(" + 
                           " CheckNo," + 
                           " ComCode," + 
                           " StartDate," + 
                           " EndDate," + 
                           " MTotalPayNo," + 
                           " TTotalPayNo," + 
                           " MTotalTax," + 
                           " TTotalTax," + 
                           " OperateDate," + 
                           " OperateCode," + 
                           " ExtendChar1," + 
                           " ExtendChar2," + 
                           " CheckType," + 
                           " Flag) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";            
        dbpool.prepareInnerStatement(strSQL);
		int index = 1;
		dbpool.setString(index++, getCheckNo());
		dbpool.setString(index++, getComCode());
		dbpool.setString(index++, getStartDate());
		dbpool.setString(index++, getEndDate());
		dbpool.setString(index++, getMTotalPayNo());
		dbpool.setString(index++, getTTotalPayNo());
		dbpool.setString(index++, getMTotalTax());
		dbpool.setString(index++, getTTotalTax());			
		dbpool.setString(index++, getOperateDate());
		dbpool.setString(index++, getOperateCode());
		dbpool.setString(index++, getExtendChar1());
		dbpool.setString(index++, getExtendChar2());
		dbpool.setString(index++, getCheckType());	
		dbpool.setString(index++, getFlag());		
		dbpool.executePreparedUpdate();
		dbpool.closePreparedStatement();  
    }

    /**
     * 插入一条记录
     * @throws Exception
     */       
    public void insert() throws Exception{
        DbPool dbpool = new DbPool();
        
        dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
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
        String strSQL = " Delete From CICheckCarShipTax Where CheckNo = ?";
        dbpool.prepareInnerStatement(strSQL);
		dbpool.setString(1, checkNo);
		dbpool.executePreparedUpdate();
		dbpool.closePreparedStatement();
    }

    public void delete(String checkNo) throws Exception{
        DbPool dbpool = new DbPool();
        
        dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
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
        String strSQL = " Update CICheckCarShipTax Set" +
                           " CheckNo = ?," +
                           " ComCode = ?," +
                           " StartDate = ?," +
                           " EndDate = ?," +
                           " MTotalPayNo = ?," +
                           " TTotalPayNo = ?," +
                           " MTotalTax = ?," +
                           " TTotalTax = ?," +
                           " OperateDate = ?," +
                           " OperateCode = ?," +
                           " ExtendChar1 = ?," +
                           " ExtendChar2 = ?," +
                           " CheckType = ?," +
                           " Flag = ?" +
                           " Where CheckNo = ?";
        dbpool.prepareInnerStatement(strSQL);
		int index = 1;
		dbpool.setString(index++, getCheckNo());
		dbpool.setString(index++, getComCode());
		dbpool.setString(index++, getStartDate());
		dbpool.setString(index++, getEndDate());
		dbpool.setString(index++, getMTotalPayNo());
		dbpool.setString(index++, getTTotalPayNo());
		dbpool.setString(index++, getMTotalTax());
		dbpool.setString(index++, getTTotalTax());			
		dbpool.setString(index++, getOperateDate());
		dbpool.setString(index++, getOperateCode());
		dbpool.setString(index++, getExtendChar1());
		dbpool.setString(index++, getExtendChar2());
		dbpool.setString(index++, getCheckType());	
		dbpool.setString(index++, getFlag());		
		dbpool.executePreparedUpdate();
		dbpool.closePreparedStatement();       
    }

    /**
     * 更新一条记录
     * @throws Exception
     */       
    public void update() throws Exception{
        DbPool dbpool = new DbPool();
        
        dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
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
        String strSQL = " Select * From CICheckCarShipTax Where CheckNo = ?";
        dbpool.prepareInnerStatement(strSQL);
		dbpool.setString(1, checkNo);       
        ResultSet resultSet = dbpool.executePreparedQuery();
        if(resultSet.next()){
            setCheckNo(resultSet.getString("checkNo"));
            setComCode(resultSet.getString("comCode"));
            setStartDate("" + resultSet.getDate("startDate"));
            setEndDate("" + resultSet.getDate("endDate"));
            setMTotalPayNo(resultSet.getString("mTotalPayNo"));
            setTTotalPayNo(resultSet.getString("tTotalPayNo"));
            setMTotalTax(resultSet.getString("mTotalTax"));
            setTTotalTax(resultSet.getString("tTotalTax"));
            setOperateDate("" + resultSet.getDate("operateDate"));
            setOperateCode(resultSet.getString("operateCode"));
            setExtendChar1(resultSet.getString("extendChar1"));
            setExtendChar2(resultSet.getString("extendChar2"));
            setCheckType(resultSet.getString("checkType"));
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

    public int getInfo(String checkNo) throws Exception{
        int intResult = 0;
        DbPool dbpool = new DbPool();
        String strDataSource =SysConfig.CONST_DDCCDATASOURCE;
        
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
        String strSQL = " SELECT COUNT(*) FROM CICheckCarShipTax WHERE "+ strWhere;
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
        
        dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
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
        
        dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
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
        CICheckCarShipTaxSchema CICheckCarShipTaxSchema = null;
        ResultSet resultSet = dbpool.query(strSQL);
        while(resultSet.next())
        {
            CICheckCarShipTaxSchema = new CICheckCarShipTaxSchema();
            CICheckCarShipTaxSchema.setCheckNo(resultSet.getString("checkNo"));
            CICheckCarShipTaxSchema.setComCode(resultSet.getString("comCode"));
            CICheckCarShipTaxSchema.setStartDate("" + resultSet.getDate("startDate"));
            CICheckCarShipTaxSchema.setEndDate("" + resultSet.getDate("endDate"));
            CICheckCarShipTaxSchema.setMTotalPayNo(resultSet.getString("mTotalPayNo"));
            CICheckCarShipTaxSchema.setTTotalPayNo(resultSet.getString("tTotalPayNo"));
            CICheckCarShipTaxSchema.setMTotalTax(resultSet.getString("mTotalTax"));
            CICheckCarShipTaxSchema.setTTotalTax(resultSet.getString("tTotalTax"));
            CICheckCarShipTaxSchema.setOperateDate("" + resultSet.getDate("operateDate"));
            CICheckCarShipTaxSchema.setOperateCode(resultSet.getString("operateCode"));
            CICheckCarShipTaxSchema.setExtendChar1(resultSet.getString("extendChar1"));
            CICheckCarShipTaxSchema.setExtendChar2(resultSet.getString("extendChar2"));
            CICheckCarShipTaxSchema.setCheckType(resultSet.getString("checkType"));
            CICheckCarShipTaxSchema.setFlag(resultSet.getString("flag"));
            vector.add(CICheckCarShipTaxSchema);
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
