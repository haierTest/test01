package com.sp.indiv.ci.dbsvr;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import javax.naming.NamingException;

import com.sp.indiv.ci.schema.PrpitemkinddetailSchema;
import com.sp.prpall.schema.PrpDServiceFeeInfoSchema;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;

/**
 * 定义prpitemkinddetail的DB类
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>@createdate 2015-03-26</p>
 * @author DBGenerator
 * @version 1.0
 */
public class DBPrpitemkinddetail extends PrpitemkinddetailSchema{
    /**
     * 构造函数
     */       
    public DBPrpitemkinddetail(){
    }

    /**
     * 插入一条记录
     * @param dbpool dbpool
     * @throws Exception
     */       
    public void insert(DbPool dbpool) throws Exception{
        String strSQL = " Insert Into Prpitemkinddetail(" + 
                           " businessno," + 
                           " businesstype," + 
                           " itemkindno," + 
                           " kindcode," + 
                           " kindcodedetail," +
                           " StartDate," +
               			   " StartHour," +
               			   " EndDate," +
               			   " EndHour," +
               			   " Amount," +
               			   " BenchMarkPremium,"+
                           " premiun) values(?,?,?,?,?,?,?,?,?,?,?,?)";
        dbpool.prepareInnerStatement(strSQL);
        int index = 1;
        dbpool.setString(index++,getBusinessno());
        dbpool.setString(index++,getBusinesstype());
        dbpool.setString(index++,getItemkindno());
        dbpool.setString(index++,getKindcode());
        dbpool.setString(index++,getKindcodedetail());
        dbpool.setString(index++,getStartDate());            
        dbpool.setString(index++,getStartHour());            
        dbpool.setString(index++,getEndDate());              
        dbpool.setString(index++,getEndHour());
        dbpool.setString(index++,getAmount());
        dbpool.setString(index++,getBenchMarkPremium());
        dbpool.setString(index++,getPremiun());
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
        	dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
            dbpool.beginTransaction();
            insert(dbpool);
            dbpool.commitTransaction();
        }
        catch(Exception exception){
            dbpool.rollbackTransaction();
            throw exception;
        }
        finally{
            dbpool.close();
        }
    }

    public void delete(DbPool dbpool,String businessno,String businesstype,String itemkindno) throws Exception{
        String strSQL = " Delete From Prpitemkinddetail Where businessno = ?" +
                           " And businesstype = ?" +
                           " And itemkindno = ?";
        dbpool.prepareInnerStatement(strSQL);
        int index = 1;
        dbpool.setString(index++,businessno);
        dbpool.setString(index++,businesstype);
        dbpool.setString(index++,itemkindno);
        dbpool.executePreparedUpdate();
        dbpool.closePreparedStatement();
    }

    public void delete(String businessno,String businesstype,String itemkindno) throws Exception{
        DbPool dbpool = new DbPool();
        
        try{
        	dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
            dbpool.beginTransaction();
            delete(dbpool,businessno,businesstype,itemkindno);
            dbpool.commitTransaction();
        }
        catch(Exception exception){
            dbpool.rollbackTransaction();
            throw exception;
        }
        finally{
            dbpool.close();
        }
    }
    
    public void delete(DbPool dbpool,String businessno, String businesstype) throws Exception{
        String strSQL = " Delete From Prpitemkinddetail Where businessno = ?" +
        				" And businesstype = ?";
        dbpool.prepareInnerStatement(strSQL);
        int index = 1;
        dbpool.setString(index++,businessno);
        dbpool.setString(index++,businesstype);
        dbpool.executePreparedUpdate();
        dbpool.closePreparedStatement();
    }

    public void delete(String businessno, String businesstype) throws Exception{
        DbPool dbpool = new DbPool();
        
        try{
        	dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
            dbpool.beginTransaction();
            delete(dbpool,businessno,businesstype);
            dbpool.commitTransaction();
        }
        catch(Exception exception){
            dbpool.rollbackTransaction();
            throw exception;
        }
        finally{
            dbpool.close();
        }
    }

    public void update(DbPool dbpool) throws Exception{
        String strSQL = " Update Prpitemkinddetail Set" +
                           " businessno = ?," +
                           " businesstype = ?," +
                           " itemkindno = ?," +
                           " kindcode = ?," +
                           " kindcodedetail = ?," +
	               		   " StartDate= ?,"+       
	            		   " StartHour= ?,"+       
	            		   " EndDate= ?,"+         
	            		   " EndHour= ?,"+
	            		   " Amount= ?,"+
	            		   " BenchMarkPremium= ?,"+
                           " premiun = ?" +
                           " Where businessno = ?" +
                            " And businesstype = ?" +
                            " And itemkindno = ?";
        dbpool.prepareInnerStatement(strSQL);
        int index = 1;
        dbpool.setString(index++,getBusinessno());
        dbpool.setString(index++,getBusinesstype());
        dbpool.setString(index++,getItemkindno());
        dbpool.setString(index++,getKindcode());
        dbpool.setString(index++,getKindcodedetail());
		dbpool.setString(index++,getStartDate());          
		dbpool.setString(index++,getStartHour());          
		dbpool.setString(index++,getEndDate());            
		dbpool.setString(index++,getEndHour());
		dbpool.setString(index++,getAmount());
		dbpool.setString(index++,getBenchMarkPremium());
        dbpool.setString(index++,getPremiun());
        dbpool.setString(index++,getBusinessno());
        dbpool.setString(index++,getBusinesstype());
        dbpool.setString(index++,getItemkindno());
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
        	dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
            dbpool.beginTransaction();
            update(dbpool);
            dbpool.commitTransaction();
        }
        catch(Exception exception){
            dbpool.rollbackTransaction();
            throw exception;
        }
        finally{
            dbpool.close();
        }
    }

    public int getInfo(DbPool dbpool,String businessno,String businesstype,String itemkindno) throws Exception{
        int intResult = 0;
        String strSQL = " Select * From Prpitemkinddetail Where businessno = ?" +
                            " And businesstype = ?" +
                            " And itemkindno = ?";
        dbpool.prepareInnerStatement(strSQL);
        int index = 1;
        dbpool.setString(index++,businessno);
        dbpool.setString(index++,businesstype);
        dbpool.setString(index++,itemkindno);
        ResultSet resultSet = dbpool.executePreparedQuery();
        if(resultSet.next()){
            setBusinessno(resultSet.getString("businessno"));
            setBusinesstype(resultSet.getString("businesstype"));
            setItemkindno(resultSet.getString("itemkindno"));
            setKindcode(resultSet.getString("kindcode"));
            setKindcodedetail(resultSet.getString("kindcodedetail"));
            setStartDate(resultSet.getDate("startDate")+"");
            setStartHour("" + resultSet.getInt("startHour"));
            setEndDate(resultSet.getDate("endDate")+"");
            setEndHour("" + resultSet.getInt("endHour"));
            setAmount(resultSet.getString("amount"));
            setBenchMarkPremium(resultSet.getString("benchMarkPremium"));
            setPremiun(resultSet.getString("premiun"));
            intResult = 0;
        }
        else{
            intResult = 100;
        }
        resultSet.close();
        dbpool.closePreparedStatement();
        return intResult;
    }

    public int getInfo(String businessno,String businesstype,String itemkindno) throws Exception{
        int intResult = 0;
        DbPool dbpool = new DbPool();
        
        try{
        	dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
            intResult=getInfo(dbpool,businessno,businesstype,itemkindno);
        }
        catch(Exception exception){
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
        String strSQL = " SELECT COUNT(*) FROM Prpitemkinddetail WHERE "+ strWhere;
        ResultSet resultSet = dbpool.query(strSQL);
        if(resultSet.next()){
            intCount = resultSet.getInt(1);
            resultSet.close();
        }
        return intCount;
    }
    
    public int getCount(DbPool dbpool,String strWhere,ArrayList bindValues) 
    throws Exception{
	    int intCount = 0;
	    String strSQL = " SELECT COUNT(1) FROM Prpitemkinddetail WHERE "+ strWhere;
	    PrpitemkinddetailSchema prpitemkinddetailSchema = null;
	    dbpool.prepareInnerStatement(strSQL);
	    if(bindValues != null){
	        int index = 1;
	        for (Iterator it = bindValues.iterator(); it.hasNext();) {
	            dbpool.setString(index++, (String) it.next());
	        }
	    }
	    ResultSet resultSet = dbpool.executePreparedQuery();
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
    public int getCount(String strWhere,ArrayList bindValues) throws
        Exception{
        int intCount = 0;
        DbPool dbpool = new DbPool();
        
        try{
            dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
            intCount=getCount(dbpool,strWhere,bindValues);
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
        	dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
            intCount=getCount(dbpool,strWhere);
        }
        catch(Exception exception){
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
        	dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
            vector=findByConditions(dbpool, strSQL, bindValues);
        }
        catch(SQLException sqlException){
            throw sqlException;
        }
        catch(NamingException namingException){
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
    public Vector findByConditions(DbPool dbpool, String strSQL, ArrayList iWhereValue) throws
        SQLException,NamingException,Exception{
        Vector vector = new Vector();
        PrpitemkinddetailSchema prpitemkinddetailSchema = null;
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
            prpitemkinddetailSchema = new PrpitemkinddetailSchema();
            prpitemkinddetailSchema.setBusinessno(resultSet.getString("businessno"));
            prpitemkinddetailSchema.setBusinesstype(resultSet.getString("businesstype"));
            prpitemkinddetailSchema.setItemkindno(resultSet.getString("itemkindno"));
            prpitemkinddetailSchema.setKindcode(resultSet.getString("kindcode"));
            prpitemkinddetailSchema.setKindcodedetail(resultSet.getString("kindcodedetail"));
            prpitemkinddetailSchema.setStartDate(resultSet.getDate("startDate")+"");
            prpitemkinddetailSchema.setStartHour("" + resultSet.getInt("startHour"));
            prpitemkinddetailSchema.setEndDate(resultSet.getDate("endDate")+"");
            prpitemkinddetailSchema.setEndHour("" + resultSet.getInt("endHour"));
            prpitemkinddetailSchema.setAmount(resultSet.getString("amount"));
            prpitemkinddetailSchema.setBenchMarkPremium("" + resultSet.getDouble("benchMarkPremium"));
            prpitemkinddetailSchema.setPremiun(resultSet.getString("premiun"));
            vector.add(prpitemkinddetailSchema);
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
