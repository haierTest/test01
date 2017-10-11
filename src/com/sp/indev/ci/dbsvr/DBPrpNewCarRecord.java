package com.sp.indiv.ci.dbsvr;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.util.*;

import com.sp.sysframework.common.util.StringUtils;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.indiv.ci.schema.PrpNewCarRecordSchema;

/**
 * ����prpNewCarRecord��DB��
 * ��pdm��ȡ���ݿ���Ϣ,�������ݿ�����ɶ�Ӧ��DB��
 * <p>Title: SinosoftJavaTools v1.4</p>
 * <p>Description: �п���JavaԴ�����ɹ���</p>
 * <p>Company: Sinosoft</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>@createdate 2011-12-22</p>
 * @FirstAuthor: Zhouxianli(JavaTools v1.0)
 * @SecondAuthor: Yangkun(JavaTools v1.1->v1.2->v1.3->v1.4)
 * @LastVersion: v1.4.2
 * @UpdateList: 1.����DB���Զ����ɴ��󶨱���insert();update();delete();getInfo();�ȷ���;
 *              2.������CIXXXXXƽ̨������ɴ���;
 *              3.�Ż�getCount()����select count(*) from tableNameΪselect count(1) from tableName;
 */
public class DBPrpNewCarRecord extends PrpNewCarRecordSchema{
    /**
     * ���캯��
     */       
    public DBPrpNewCarRecord(){
    }

    /**
     * ����һ����¼
     * @param dbpool dbpool
     * @throws Exception
     */       
    public void insert(DbPool dbpool) throws Exception{
        String strSQL = " Insert Into PrpNewCarRecord(" + 
                           " EngineNo," + 
                           " RackNo," + 
                           " Owner," + 
                           " CertiType," + 
                           " CertiCode," + 
                           " PCVehicleCategory," + 
                           " LimitLodaPerson," + 
                           " LimitLoda," + 
                           " POWeight," + 
                           " ExhaustCapacity," + 
                           " FuelType," + 
                           " CertificateType," + 
                           " CertificateNO," + 
                           " CertifycateDate," + 
                           " CarRecordDate,"+
                           " UserCode) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        dbpool.prepareInnerStatement(strSQL);
        int index = 1;
        dbpool.setString(index++,getEngineNo());
        dbpool.setString(index++,getRackNo());
        dbpool.setString(index++,getOwner());
        dbpool.setString(index++,getCertiType());
        dbpool.setString(index++,getCertiCode());
        dbpool.setString(index++,getPCVehicleCategory());
        dbpool.setString(index++,getLimitLodaPerson());
        dbpool.setString(index++,getLimitLoda());
        dbpool.setString(index++,getPOWeight());
        dbpool.setString(index++,getExhaustCapacity());
        dbpool.setString(index++,getFuelType());
        dbpool.setString(index++,getCertificateType());
        dbpool.setString(index++,getCertificateNO());
        dbpool.setString(index++,getCertifycateDate());
        dbpool.setString(index++,getCarRecordDate());
        dbpool.setString(index++,getUserCode());
        dbpool.executePreparedUpdate();
        dbpool.closePreparedStatement();
    }

    /**
     * ����һ����¼
     * @throws Exception
     */       
    public void insert() throws Exception{
        DbPool dbpool = new DbPool();
        
        try{
          dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
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

    public void delete(DbPool dbpool,String engineNo,String rackNo) throws Exception{
        String strSQL = " Delete From PrpNewCarRecord Where EngineNo = ?" +
                           " And RackNo = ?";
        dbpool.prepareInnerStatement(strSQL);
        int index = 1;
        dbpool.setString(index++,engineNo);
        dbpool.setString(index++,rackNo);
        dbpool.executePreparedUpdate();
        dbpool.closePreparedStatement();
    }

    public void delete(String engineNo,String rackNo) throws Exception{
        DbPool dbpool = new DbPool();
        
        try{
          dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
          dbpool.beginTransaction();
          delete(dbpool,engineNo,rackNo);
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
        String strSQL = " Update PrpNewCarRecord Set" +
                           " EngineNo = ?," +
                           " RackNo = ?," +
                           " Owner = ?," +
                           " CertiType = ?," +
                           " CertiCode = ?," +
                           " PCVehicleCategory = ?," +
                           " LimitLodaPerson = ?," +
                           " LimitLoda = ?," +
                           " POWeight = ?," +
                           " ExhaustCapacity = ?," +
                           " FuelType = ?," +
                           " CertificateType = ?," +
                           " CertificateNO = ?," +
                           " CertifycateDate = ?," +
                           " CarRecordDate = ?" +
                           " Where EngineNo = ?" +
                            " And RackNo = ?";
        dbpool.prepareInnerStatement(strSQL);
        int index = 1;
        dbpool.setString(index++,getEngineNo());
        dbpool.setString(index++,getRackNo());
        dbpool.setString(index++,getOwner());
        dbpool.setString(index++,getCertiType());
        dbpool.setString(index++,getCertiCode());
        dbpool.setString(index++,getPCVehicleCategory());
        dbpool.setString(index++,getLimitLodaPerson());
        dbpool.setString(index++,getLimitLoda());
        dbpool.setString(index++,getPOWeight());
        dbpool.setString(index++,getExhaustCapacity());
        dbpool.setString(index++,getFuelType());
        dbpool.setString(index++,getCertificateType());
        dbpool.setString(index++,getCertificateNO());
        dbpool.setString(index++,getCertifycateDate());
        dbpool.setString(index++,getCarRecordDate());
        dbpool.setString(index++,getEngineNo());
        dbpool.setString(index++,getRackNo());
        dbpool.executePreparedUpdate();
        dbpool.closePreparedStatement();
    }

    /**
     * ����һ����¼
     * @throws Exception
     */       
    public void update() throws Exception{
        DbPool dbpool = new DbPool();
        
        try{
          dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
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

    public int getInfo(DbPool dbpool,String engineNo,String rackNo) throws Exception{
        int intResult = 0;
        String strSQL = " Select * From PrpNewCarRecord Where EngineNo = ?" +
                            " And RackNo = ?";
        dbpool.prepareInnerStatement(strSQL);
        int index = 1;
        dbpool.setString(index++,engineNo);
        dbpool.setString(index++,rackNo);
        ResultSet resultSet = dbpool.executePreparedQuery();
        if(resultSet.next()){
          setEngineNo(resultSet.getString("engineNo"));
          setRackNo(resultSet.getString("rackNo"));
          setOwner(resultSet.getString("owner"));
          setCertiType(resultSet.getString("certiType"));
          setCertiCode(resultSet.getString("certiCode"));
          setPCVehicleCategory(resultSet.getString("pCVehicleCategory"));
          setLimitLodaPerson(resultSet.getString("limitLodaPerson"));
          setLimitLoda(resultSet.getString("limitLoda"));
          setPOWeight(resultSet.getString("pOWeight"));
          setExhaustCapacity(resultSet.getString("exhaustCapacity"));
          setFuelType(resultSet.getString("fuelType"));
          setCertificateType(resultSet.getString("certificateType"));
          setCertificateNO(resultSet.getString("certificateNO"));
          
          setCertifycateDate(StringUtils.replace(resultSet.getDate("certifycateDate")+"", "-", ""));
          setCarRecordDate(resultSet.getDate("carRecordDate")+"");
          
          intResult = 0;
        }
        else{
          intResult = 100;
        }
          resultSet.close();
          dbpool.closePreparedStatement();
          return intResult;
    }

    public int getInfo(String engineNo,String rackNo) throws Exception{
        int intResult = 0;
        DbPool dbpool = new DbPool();
        
        try{
          dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
          intResult=getInfo(dbpool,engineNo,rackNo);
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
        String strSQL = " SELECT COUNT(*) FROM PrpNewCarRecord WHERE "+ strWhere;
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
        PrpNewCarRecordSchema prpNewCarRecordSchema = null;
        ResultSet resultSet = dbpool.query(strSQL);
        while(resultSet.next())
        {
          prpNewCarRecordSchema = new PrpNewCarRecordSchema();
          prpNewCarRecordSchema.setEngineNo(resultSet.getString("engineNo"));
          prpNewCarRecordSchema.setRackNo(resultSet.getString("rackNo"));
          prpNewCarRecordSchema.setOwner(resultSet.getString("owner"));
          prpNewCarRecordSchema.setCertiType(resultSet.getString("certiType"));
          prpNewCarRecordSchema.setCertiCode(resultSet.getString("certiCode"));
          prpNewCarRecordSchema.setPCVehicleCategory(resultSet.getString("pCVehicleCategory"));
          prpNewCarRecordSchema.setLimitLodaPerson(resultSet.getString("limitLodaPerson"));
          prpNewCarRecordSchema.setLimitLoda(resultSet.getString("limitLoda"));
          prpNewCarRecordSchema.setPOWeight(resultSet.getString("pOWeight"));
          prpNewCarRecordSchema.setExhaustCapacity(resultSet.getString("exhaustCapacity"));
          prpNewCarRecordSchema.setFuelType(resultSet.getString("fuelType"));
          prpNewCarRecordSchema.setCertificateType(resultSet.getString("certificateType"));
          prpNewCarRecordSchema.setCertificateNO(resultSet.getString("certificateNO"));
          
          prpNewCarRecordSchema.setCertifycateDate(StringUtils.replace(resultSet.getDate("certifycateDate")+"", "-", ""));
          prpNewCarRecordSchema.setCarRecordDate(""+resultSet.getDate("carRecordDate"));
          prpNewCarRecordSchema.setUserCode(resultSet.getString("userCode"));
          
          vector.add(prpNewCarRecordSchema);
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
      String strSQL = " SELECT COUNT(1) FROM PrpNewCarRecord WHERE "+ strWhere;
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
        PrpNewCarRecordSchema prpNewCarRecordSchema = null;
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
        	prpNewCarRecordSchema = new PrpNewCarRecordSchema();
        	prpNewCarRecordSchema.setEngineNo(resultSet.getString("engineNo"));
            prpNewCarRecordSchema.setRackNo(resultSet.getString("rackNo"));
            prpNewCarRecordSchema.setOwner(resultSet.getString("owner"));
            prpNewCarRecordSchema.setCertiType(resultSet.getString("certiType"));
            prpNewCarRecordSchema.setCertiCode(resultSet.getString("certiCode"));
            prpNewCarRecordSchema.setPCVehicleCategory(resultSet.getString("pCVehicleCategory"));
            prpNewCarRecordSchema.setLimitLodaPerson(resultSet.getString("limitLodaPerson"));
            prpNewCarRecordSchema.setLimitLoda(resultSet.getString("limitLoda"));
            prpNewCarRecordSchema.setPOWeight(resultSet.getString("pOWeight"));
            prpNewCarRecordSchema.setExhaustCapacity(resultSet.getString("exhaustCapacity"));
            prpNewCarRecordSchema.setFuelType(resultSet.getString("fuelType"));
            prpNewCarRecordSchema.setCertificateType(resultSet.getString("certificateType"));
            prpNewCarRecordSchema.setCertificateNO(resultSet.getString("certificateNO"));
            
            prpNewCarRecordSchema.setCertifycateDate(StringUtils.replace(resultSet.getDate("certifycateDate")+"", "-", ""));
            prpNewCarRecordSchema.setCarRecordDate(resultSet.getDate("carRecordDate")+"");
            
            vector.add(prpNewCarRecordSchema);
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
