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
 * ���峵��˰������˰ƾ֤���DB��
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>@createdate 2008-01-21</p>
 * @author DBGenerator
 * @version 1.0
 */
public class DBCIAssemblePayNo extends CIAssemblePayNoSchema{
    /**
     * ���캯��
     */       
    public DBCIAssemblePayNo(){
    }

    /**
     * ����һ����¼
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
        String strSQL = " SELECT COUNT(*) FROM CIAssemblePayNo WHERE "+ strWhere;
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
     * ����������ȡ����������������
     * @author wangchuanzhong 20100602
     * @param strWhere      ��ѯ����
     * @param iWhereValue   ��ѯ����������ֵ
     * @return intCount     ��ѯ����
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
     * ����������ȡ��ѯ��������
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
     * ����SQL����ȡ�����
     * @author wangchuanzhong 20100602
     * @param dbpool      ���ݿ����ӳ�
     * @param strSQL      SQL���
     * @param iWhereValue ��ѯ����������ֵ
     * @return Vector ��ѯ�����¼��
     * @throws SQLException    ���ݿ�����쳣��
     * @throws NamingException �����쳣��
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
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
