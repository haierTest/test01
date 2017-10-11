package com.sp.indiv.ci.dbsvr;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.util.*;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.indiv.ci.schema.CICarShipTaxPayMsgSchema;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * �����ѯ���ػ��ϴ������˰��Ϣ���DB��
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>@createdate 2008-01-21</p>
 * @author DBGenerator
 * @version 1.0
 */
public class DBCICarShipTaxPayMsg extends CICarShipTaxPayMsgSchema{
	Log logger = LogFactory.getLog(getClass());
    /**
     * ���캯��
     */       
    public DBCICarShipTaxPayMsg(){
    }

    /**
     * ����һ����¼
     * @param dbpool dbpool
     * @throws Exception
     */       
    public void insert(DbPool dbpool) throws Exception{
        String strSQL = " Insert Into CICarShipTaxPayMsg(" + 
                           " PolicyNo," + 
                           " ValidNo," + 
                           " PayNo," + 
                           " TaxComName," + 
                           " OperaterCode," + 
                           " OperateDate," +  
                           " Flag) values(?,?,?,?,?,?,?)";
		dbpool.prepareStatement(strSQL);
		
		logger.info("strSQL-----------" + strSQL);
		
		int index = 1;
		
		logger.info("policyno-----------::" + getPolicyNo());
		logger.info("policyno-----------::" + getValidNo());
		logger.info("policyno-----------::" + getPayNo());
		logger.info("policyno-----------::" + getTaxComName());
		logger.info("policyno-----------::" + getOperaterCode());
		logger.info("policyno-----------::" + getOperateDate());
		logger.info("policyno-----------::" + getFlag());
		
		dbpool.setString(index++, getPolicyNo());
		dbpool.setString(index++, getValidNo());
		dbpool.setString(index++, getPayNo());
		dbpool.setString(index++, getTaxComName());
		dbpool.setString(index++, getOperaterCode());
		dbpool.setString(index++, getOperateDate());
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

    public void delete(DbPool dbpool,String policyno) throws Exception{
        String strSQL = " Delete From CICarShipTaxPayMsg Where policyno = ?";
		dbpool.prepareStatement(strSQL);
		dbpool.setString(1, policyno);
		dbpool.executePreparedUpdate();
		dbpool.closePreparedStatement();
    }

    public void delete(String policyno) throws Exception{
        DbPool dbpool = new DbPool();
        
        dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
        try{
            dbpool.beginTransaction();
            delete(dbpool,policyno);
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
        String strSQL = " Update CICarShipTaxPayMsg Set" +
                           " validno = ?," +
                           " payno = ?, " +
                           " taxcomname = ?," +
                           " operatercode = ?, " +
                           " operatedate = ?,"  +
                           " flag = ?" +
                           " Where policyno = ?";
		dbpool.prepareStatement(strSQL);
		int index = 1;
		dbpool.setString(index++, getValidNo());
		dbpool.setString(index++, getPayNo());
		dbpool.setString(index++, getTaxComName());
		dbpool.setString(index++, getOperaterCode());
		dbpool.setString(index++, getOperateDate());
		dbpool.setString(index++, getFlag());
		dbpool.setString(index++, getPolicyNo());
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

    public int getInfo(DbPool dbpool,String policyno) throws Exception{
        int intResult = 0;
        String strSQL = " Select * From CICarShipTaxPayMsg Where policyno = ?";
		dbpool.prepareStatement(strSQL);
		dbpool.setString(1, policyno);
        ResultSet resultSet = dbpool.executePreparedQuery();
        if(resultSet.next()){
            setPolicyNo(resultSet.getString("PolicyNo"));
            setValidNo(resultSet.getString("ValidNo"));
            setPayNo(resultSet.getString("PayNo"));
            setTaxComName(resultSet.getString("TaxComName"));
            setOperaterCode(resultSet.getString("OperaterCode"));
            setOperateDate(resultSet.getString("OperateDate"));
            setFlag(resultSet.getString("Flag"));
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
        String strSQL = " SELECT COUNT(*) FROM CICarShipTaxPayMsg WHERE "+ strWhere;
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
        CICarShipTaxPayMsgSchema ciCarShipTaxPayMsgSchema = null;
        ResultSet resultSet = dbpool.query(strSQL);
        while(resultSet.next())
        {
        	ciCarShipTaxPayMsgSchema = new CICarShipTaxPayMsgSchema();
        	ciCarShipTaxPayMsgSchema.setPolicyNo(resultSet.getString("policyno"));
        	ciCarShipTaxPayMsgSchema.setValidNo(resultSet.getString("validno"));
        	ciCarShipTaxPayMsgSchema.setPayNo(resultSet.getString("payno"));
        	ciCarShipTaxPayMsgSchema.setTaxComName(resultSet.getString("TaxComName"));
        	ciCarShipTaxPayMsgSchema.setOperaterCode(resultSet.getString("OperaterCode"));
        	ciCarShipTaxPayMsgSchema.setOperateDate("" + resultSet.getDate("OperateDate"));
        	ciCarShipTaxPayMsgSchema.setFlag(resultSet.getString("flag"));
            vector.add(ciCarShipTaxPayMsgSchema);
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
