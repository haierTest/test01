package com.sp.indiv.ci.dbsvr;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.util.*;

import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.indiv.ci.schema.CICheckCarShipTaxDetailSchema;

/**
 * ����CICheckCarShipTaxDetail��DB��
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>@createdate 2008-01-21</p>
 * @author DBGenerator
 * @version 1.0
 */
public class DBCICheckCarShipTaxDetail extends CICheckCarShipTaxDetailSchema{
    /**
     * ���캯��
     */       
    public DBCICheckCarShipTaxDetail(){
    }

    /**
     * ����һ����¼
     * @param dbpool dbpool
     * @throws Exception
     */       
    public void insert(DbPool dbpool) throws Exception{
        String strSQL = " Insert Into CICheckCarShipTaxDetail(" + 
                           " CheckNo," + 
                           " MPolicyNo," + 
                           " TPolicyNo," + 
                           " MPayNo," + 
                           " TPayNo," + 
                           " MPayTax," + 
                           " TPayTax," + 
                           " MLateFee," + 
                           " TLateFee," + 
                           " ExtendChar1," + 
                           " ExtendChar2," + 
                           " Flag) values(?,?,?,?,?,?,?,?,?,?,?,?)";
        dbpool.prepareInnerStatement(strSQL);
		int index = 1;
		dbpool.setString(index++, getCheckNo());
		dbpool.setString(index++, getMPolicyNo());
		dbpool.setString(index++, getTPolicyNo());
		dbpool.setString(index++, getMPayNo());
		dbpool.setString(index++, getTPayNo());
		dbpool.setString(index++, getMPayTax());
		dbpool.setString(index++, getTPayTax());			
		dbpool.setString(index++, getMLateFee());
		dbpool.setString(index++, getTLateFee());
		dbpool.setString(index++, getExtendChar1());
		dbpool.setString(index++, getExtendChar2());
		dbpool.setString(index++, getFlag());		
		dbpool.executePreparedUpdate();
		dbpool.closePreparedStatement();  
    }

    /**
     * ����һ����¼
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
        String strSQL = " Update CICheckCarShipTaxDetail Set" +
                           " CheckNo = ?," +
                           " MPolicyNo = ?," +
                           " TPolicyNo = ?," +
                           " MPayNo = ?," +
                           " TPayNo = ?," +
                           " MPayTax = ?," +
                           " TPayTax = ?," +
                           " MLateFee = ?," +
                           " TLateFee = ?," +
                           " ExtendChar1 = ?," +
                           " ExtendChar2 = ?," +
                           " Flag = ?" +
                           " Where CheckNo = ?";
        dbpool.prepareInnerStatement(strSQL);
		int index = 1;
		dbpool.setString(index++, getCheckNo());
		dbpool.setString(index++, getMPolicyNo());
		dbpool.setString(index++, getTPolicyNo());
		dbpool.setString(index++, getMPayNo());
		dbpool.setString(index++, getTPayNo());
		dbpool.setString(index++, getMPayTax());
		dbpool.setString(index++, getTPayTax());			
		dbpool.setString(index++, getMLateFee());
		dbpool.setString(index++, getTLateFee());
		dbpool.setString(index++, getExtendChar1());
		dbpool.setString(index++, getExtendChar2());
		dbpool.setString(index++, getFlag());		
		dbpool.executePreparedUpdate();
		dbpool.closePreparedStatement();       
    }

    /**
     * ����һ����¼
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
        String strSQL = " Select * From CICheckCarShipTax Where CheckNo = ?";
        dbpool.prepareInnerStatement(strSQL);
		dbpool.setString(1, checkNo);     
        ResultSet resultSet = dbpool.executePreparedQuery();
        if(resultSet.next()){
            setCheckNo(resultSet.getString("checkNo"));
            setMPolicyNo(resultSet.getString("mPolicyNo"));
            setTPolicyNo(resultSet.getString("tPolicyNo"));
            setMPayNo(resultSet.getString("mPayNo"));
            setTPayNo(resultSet.getString("tPayNo"));
            setMPayTax(resultSet.getString("mPayTax"));
            setTPayTax(resultSet.getString("tPayTax"));
            setMLateFee(resultSet.getString("mLateFee"));
            setTLateFee(resultSet.getString("tLateFee"));
            setExtendChar1(resultSet.getString("extendChar1"));
            setExtendChar2(resultSet.getString("extendChar2"));
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
        String strSQL = " SELECT COUNT(*) FROM CICheckCarShipTaxDetail WHERE "+ strWhere;
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
     * ����SQL����ȡ�����
     * @param strSQL  SQL���
     * @return Vector ��ѯ�����¼��
     * @throws SQLException    ���ݿ�����쳣��
     * @throws NamingException �����쳣��
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
     * ����SQL����ȡ�����
     * @param dbpool  ���ݿ����ӳ�
     * @param strSQL  SQL���
     * @return Vector ��ѯ�����¼��
     * @throws SQLException    ���ݿ�����쳣��
     * @throws NamingException �����쳣��
     */
    public Vector findByConditions(DbPool dbpool,String strSQL) throws
        SQLException,NamingException{
        Vector vector = new Vector();
        CICheckCarShipTaxDetailSchema CICheckCarShipTaxDetailSchema = null;
        ResultSet resultSet = dbpool.query(strSQL);
        while(resultSet.next())
        {
            CICheckCarShipTaxDetailSchema = new CICheckCarShipTaxDetailSchema();
            CICheckCarShipTaxDetailSchema.setCheckNo(resultSet.getString("checkNo"));
            CICheckCarShipTaxDetailSchema.setMPolicyNo(resultSet.getString("mPolicyNo"));
            CICheckCarShipTaxDetailSchema.setTPolicyNo(resultSet.getString("tPolicyNo"));
            CICheckCarShipTaxDetailSchema.setMPayNo(resultSet.getString("mPayNo"));
            CICheckCarShipTaxDetailSchema.setTPayNo(resultSet.getString("tPayNo"));
            CICheckCarShipTaxDetailSchema.setMPayTax(resultSet.getString("mPayTax"));
            CICheckCarShipTaxDetailSchema.setTPayTax(resultSet.getString("tPayTax"));
            CICheckCarShipTaxDetailSchema.setMLateFee(resultSet.getString("mLateFee"));
            CICheckCarShipTaxDetailSchema.setTLateFee(resultSet.getString("tLateFee"));
            CICheckCarShipTaxDetailSchema.setExtendChar1(resultSet.getString("extendChar1"));
            CICheckCarShipTaxDetailSchema.setExtendChar2(resultSet.getString("extendChar2"));
            CICheckCarShipTaxDetailSchema.setFlag(resultSet.getString("flag"));
            vector.add(CICheckCarShipTaxDetailSchema);
        }
        resultSet.close();
        return vector;
    }

    /**
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
