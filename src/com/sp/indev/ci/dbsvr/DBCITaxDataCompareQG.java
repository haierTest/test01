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
 * ����CITaxDataCompareQG��DB��
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>@createdate 2009-06-16</p>
 * @author DBGenerator
 * @version 1.0
 */
public class DBCITaxDataCompareQG extends CITaxDataCompareQGSchema{
    /**
     * ���캯��
     */       
    public DBCITaxDataCompareQG(){
    }

    /**
     * ����һ����¼
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
        String strSQL = " SELECT COUNT(*) FROM CITaxDataCompareQG WHERE "+ strWhere;
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
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
