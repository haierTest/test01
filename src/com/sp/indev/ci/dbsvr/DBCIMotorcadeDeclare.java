package com.sp.indiv.ci.dbsvr;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import javax.naming.NamingException;

import com.sp.indiv.ci.schema.CIMotorcadeDeclareSchema;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;

/**
 * ����CIMotorcadeDeclare-�ų��걨�ӿڱ��DB��
 * ��pdm��ȡ���ݿ���Ϣ,�������ݿ�����ɶ�Ӧ��DB��
 * <p>Title: SinosoftJavaTools v1.4</p>
 * <p>Description: �п���JavaԴ�����ɹ���</p>
 * <p>Company: Sinosoft</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>@createdate 2010-03-19</p>
 * @FirstAuthor: Zhouxianli(JavaTools v1.0)
 * @SecondAuthor: Yangkun(JavaTools v1.1->v1.2->v1.3->v1.4)
 * @LastVersion: v1.4.3
 * @UpdateList: 1.����DB���Զ����ɴ��󶨱���insert();update();delete();getInfo();�ȷ���;
 *              2.������CIXXXXXƽ̨������ɴ���;
 *              3.�Ż�getCount()����select count(*) from tableNameΪselect count(1) from tableName;
 */
public class DBCIMotorcadeDeclare extends CIMotorcadeDeclareSchema{
    /**
     * ���캯��
     */       
    public DBCIMotorcadeDeclare(){
    }

    /**
     * ����һ����¼
     * @param dbpool dbpool
     * @throws Exception
     */       
    public void insert(DbPool dbpool) throws Exception{       
        String strSQL = " Insert Into CIMotorcadeDeclare(" + 
                           " SerialNo," + 
                           " GroupCode," + 
                           " ContractNo," + 
                           " ProposalNo," + 
                           " CarMark," + 
                           " EngineNo," + 
                           " RackNo," + 
                           " Owner," + 
                           " PolicyHolder," + 
                           " InsuredName," + 
                           " PassVehicle," + 
                           " FailureVehicle," + 
                           " GroupStartDate," + 
                           " GroupEndDate) values(?,?,?,?,?,?,?,?,?,?,?,?,to_date(?,'yyyy-MM-dd'),to_date(?,'yyyy-MM-dd'))";
        dbpool.prepareInnerStatement(strSQL);
        int index = 1;
        dbpool.setString(index++,getSerialNo());
        dbpool.setString(index++,getGroupCode());
        
        dbpool.setString(index++,getContractNo());
        dbpool.setString(index++,getProposalNo());
        dbpool.setString(index++,getCarMark());
        dbpool.setString(index++,getEngineNo());
        dbpool.setString(index++,getRackNo());
        dbpool.setString(index++,getOwner());
        dbpool.setString(index++,getPolicyHolder());
        dbpool.setString(index++,getInsuredName());
        dbpool.setString(index++,getPassVehicle());
        dbpool.setString(index++,getFailureVehicle());
        dbpool.setString(index++,getGroupStartDate());
        dbpool.setString(index++,getGroupEndDate());
        dbpool.executePreparedUpdate();
        dbpool.closePreparedStatement();
    }
    
    public void insertBySeq(DbPool dbpool) throws Exception{
   	 	StringBuffer buffer = new StringBuffer(200);
   	 	buffer.append("INSERT INTO CIMotorcadeDeclare (");
   	 	buffer.append("SerialNo,");
   	 	buffer.append("GroupCode,");
   	 	buffer.append("ContractNo,");
   	 	buffer.append("ProposalNo,");
   	 	buffer.append("CarMark,");
   	 	buffer.append("EngineNo,");
   	 	buffer.append("RackNo,");
   	 	buffer.append("Owner,");
   	 	buffer.append("PolicyHolder,");
   	 	buffer.append("InsuredName,");
   	 	buffer.append("PassVehicle,");
   	 	buffer.append("FailureVehicle,");
   	 	buffer.append("GroupStartDate,");
   	 	buffer.append("GroupEndDate)");
   	 	buffer.append("values(Seq_CiMotorcadeDeclare_SN.nextval,?,?,?,?,?,?,?,?,?,?,?,to_date(?,'yyyy-MM-dd'),to_date(?,'yyyy-MM-dd'))");
       
        dbpool.prepareInnerStatement(buffer.toString());
        int index = 1;
        
        dbpool.setString(index++,getGroupCode());
        dbpool.setString(index++,getContractNo());
        dbpool.setString(index++,getProposalNo());
        dbpool.setString(index++,getCarMark());
        dbpool.setString(index++,getEngineNo());
        dbpool.setString(index++,getRackNo());
        dbpool.setString(index++,getOwner());
        dbpool.setString(index++,getPolicyHolder());
        dbpool.setString(index++,getInsuredName());
        dbpool.setString(index++,getPassVehicle());
        dbpool.setString(index++,getFailureVehicle());
        dbpool.setString(index++,getGroupStartDate());
        dbpool.setString(index++,getGroupEndDate());
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
          
          String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
          if ("1".equals(strSwitch)) {
            dbpool.open("platformNewDataSource");
          } else {
            dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
          }
          
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

    public void delete(DbPool dbpool,String serialNo,String groupCode) throws Exception{
        String strSQL = " Delete From CIMotorcadeDeclare Where SerialNo = ?" +
                           " And GroupCode = ?";
        dbpool.prepareInnerStatement(strSQL);
        int index = 1;
        dbpool.setString(index++,serialNo);
        dbpool.setString(index++,groupCode);
        dbpool.executePreparedUpdate();
        dbpool.closePreparedStatement();
    }

    public void delete(String serialNo,String groupCode) throws Exception{
        DbPool dbpool = new DbPool();
        
        try{
          
          String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
          if ("1".equals(strSwitch)) {
            dbpool.open("platformNewDataSource");
          } else {
            dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
          }
          
          dbpool.beginTransaction();
          delete(dbpool,serialNo,groupCode);
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

    
    public void cancelProposalNo(String iProposalNo) throws Exception{
        DbPool dbpool = new DbPool();
        
        try{
          
          String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
          if ("1".equals(strSwitch)) {
            dbpool.open("platformNewDataSource");
          } else {
            dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
          }
          
          dbpool.beginTransaction();
          cancelProposalNo(dbpool,iProposalNo);
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
    
    public void cancelProposalNo(DbPool dbpool,String iProposalNo) throws Exception{
    	String strSQL = " Update CIMotorcadeDeclare Set" +
    	" ProposalNo = ?" +
    	" Where ProposalNo = ?";
    	dbpool.prepareInnerStatement(strSQL);
		int index = 1;
		dbpool.setString(index++,"");
		dbpool.setString(index++,iProposalNo);
		dbpool.executePreparedUpdate();
		dbpool.closePreparedStatement();
    } 
    
    
    public void update(DbPool dbpool) throws Exception{
        String strSQL = " Update CIMotorcadeDeclare Set" +
                           " SerialNo = ?," +
                           " GroupCode = ?," +
                           " ContractNo = ?," +
                           " ProposalNo = ?," +
                           " CarMark = ?," +
                           " EngineNo = ?," +
                           " RackNo = ?," +
                           " Owner = ?," +
                           " PolicyHolder = ?," +
                           " InsuredName = ?," +
                           " PassVehicle = ?," +
                           " FailureVehicle = ?," +
                           " GroupStartDate = to_date(?,'yyyy-MM-dd')," +
                           " GroupEndDate = to_date(?,'yyyy-MM-dd')" +
                           " Where SerialNo = ?" +
                           " And GroupCode = ?";
        dbpool.prepareInnerStatement(strSQL);
        int index = 1;
        dbpool.setString(index++,getSerialNo());
        dbpool.setString(index++,getGroupCode());
        dbpool.setString(index++,getContractNo());
        dbpool.setString(index++,getProposalNo());
        dbpool.setString(index++,getCarMark());
        dbpool.setString(index++,getEngineNo());
        dbpool.setString(index++,getRackNo());
        dbpool.setString(index++,getOwner());
        dbpool.setString(index++,getPolicyHolder());
        dbpool.setString(index++,getInsuredName());
        dbpool.setString(index++,getPassVehicle());
        dbpool.setString(index++,getFailureVehicle());
        dbpool.setString(index++,getGroupStartDate());
        dbpool.setString(index++,getGroupEndDate());
        dbpool.setString(index++,getSerialNo());
        dbpool.setString(index++,getGroupCode());
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
          
          String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
          if ("1".equals(strSwitch)) {
            dbpool.open("platformNewDataSource");
          } else {
            dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
          }
          
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

    /**
     * ����һ����¼
     * @throws Exception
     */       
    public void updateProposalNo(String iProposalNo,String iContractNo,String iRackno,String iCarmark,String iEngineNo) throws Exception{
        DbPool dbpool = new DbPool();
        
        try{
          
          String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
          if ("1".equals(strSwitch)) {
            dbpool.open("platformNewDataSource");
          } else {
            dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
          }
          
          dbpool.beginTransaction();
          String strSQL = " Update CIMotorcadeDeclare Set" +
          " ProposalNo = ?" +
          " Where ContractNo = ?" +
           " And Rackno = ?" +
           " And Carmark = ?" +
           " And EngineNo = ?";
			dbpool.prepareInnerStatement(strSQL);
			int index = 1;
			dbpool.setString(index++,iProposalNo);
			dbpool.setString(index++,iContractNo);
			dbpool.setString(index++,iRackno);
			dbpool.setString(index++,iCarmark);
			dbpool.setString(index++,iEngineNo);
			dbpool.executePreparedUpdate();
			dbpool.closePreparedStatement();
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
    
    
    public int getInfo(DbPool dbpool,String serialNo,String groupCode) throws Exception{
        int intResult = 0;
        String strSQL = " Select * From CIMotorcadeDeclare Where SerialNo = ?" +
                            " And GroupCode = ?";
        dbpool.prepareInnerStatement(strSQL);
        int index = 1;
        dbpool.setString(index++,serialNo);
        dbpool.setString(index++,groupCode);
        ResultSet resultSet = dbpool.executePreparedQuery();
        if(resultSet.next()){
          setSerialNo(resultSet.getString("serialNo"));
          setGroupCode(resultSet.getString("groupCode"));
          setContractNo(resultSet.getString("contractNo"));
          setProposalNo(resultSet.getString("proposalNo"));
          setCarMark(resultSet.getString("carMark"));
          setEngineNo(resultSet.getString("engineNo"));
          setRackNo(resultSet.getString("rackNo"));
          setOwner(resultSet.getString("owner"));
          setPolicyHolder(resultSet.getString("policyHolder"));
          setInsuredName(resultSet.getString("insuredName"));
          setPassVehicle(resultSet.getString("passVehicle"));
          setFailureVehicle(resultSet.getString("failureVehicle"));
          setGroupStartDate(""+resultSet.getDate("groupStartDate"));
          setGroupEndDate(""+resultSet.getDate("groupEndDate"));
          intResult = 0;
        }
        else{
          intResult = 100;
        }
          resultSet.close();
          dbpool.closePreparedStatement();
          return intResult;
    }

    public int getInfo(String serialNo,String groupCode) throws Exception{
        int intResult = 0;
        DbPool dbpool = new DbPool();
        
        try{
          
          String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
          if ("1".equals(strSwitch)) {
            dbpool.open("platformNewDataSource");
          } else {
            dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
          }
          
          intResult=getInfo(dbpool,serialNo,groupCode);
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
        String strSQL = " SELECT COUNT(1) FROM CIMotorcadeDeclare WHERE "+ strWhere;
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
        
        try{
          
          String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
          if ("1".equals(strSwitch)) {
            dbpool.open("platformNewDataSource");
          } else {
            dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
          }
          
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
        
        try{
          
          String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
          if ("1".equals(strSwitch)) {
            dbpool.open("platformNewDataSource");
          } else {
            dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
          }
          
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
        CIMotorcadeDeclareSchema cIMotorcadeDeclareSchema = null;
        ResultSet resultSet = dbpool.query(strSQL);
        while(resultSet.next())
        {
          cIMotorcadeDeclareSchema = new CIMotorcadeDeclareSchema();
          cIMotorcadeDeclareSchema.setSerialNo(resultSet.getString("serialNo"));
          cIMotorcadeDeclareSchema.setGroupCode(resultSet.getString("groupCode"));
          cIMotorcadeDeclareSchema.setContractNo(resultSet.getString("contractNo"));
          cIMotorcadeDeclareSchema.setProposalNo(resultSet.getString("proposalNo"));
          cIMotorcadeDeclareSchema.setCarMark(resultSet.getString("carMark"));
          cIMotorcadeDeclareSchema.setEngineNo(resultSet.getString("engineNo"));
          cIMotorcadeDeclareSchema.setRackNo(resultSet.getString("rackNo"));
          cIMotorcadeDeclareSchema.setOwner(resultSet.getString("owner"));
          cIMotorcadeDeclareSchema.setPolicyHolder(resultSet.getString("policyHolder"));
          cIMotorcadeDeclareSchema.setInsuredName(resultSet.getString("insuredName"));
          cIMotorcadeDeclareSchema.setPassVehicle(resultSet.getString("passVehicle"));
          cIMotorcadeDeclareSchema.setFailureVehicle(resultSet.getString("failureVehicle"));
          cIMotorcadeDeclareSchema.setGroupStartDate(""+resultSet.getDate("groupStartDate"));
          cIMotorcadeDeclareSchema.setGroupEndDate(""+resultSet.getDate("groupEndDate"));
          vector.add(cIMotorcadeDeclareSchema);
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
          
          String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
          if ("1".equals(strSwitch)) {
            dbpool.open("platformNewDataSource");
          } else {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
          }
          
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
      String strSQL = " SELECT COUNT(1) FROM CIMotorcadeDeclare WHERE "+ strWhere;
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
        CIMotorcadeDeclareSchema cIMotorcadeDeclareSchema = null;
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
          cIMotorcadeDeclareSchema = new CIMotorcadeDeclareSchema();
          cIMotorcadeDeclareSchema.setSerialNo(resultSet.getString("serialNo"));
          cIMotorcadeDeclareSchema.setGroupCode(resultSet.getString("groupCode"));
          cIMotorcadeDeclareSchema.setContractNo(resultSet.getString("contractNo"));
          cIMotorcadeDeclareSchema.setProposalNo(resultSet.getString("proposalNo"));
          cIMotorcadeDeclareSchema.setCarMark(resultSet.getString("carMark"));
          cIMotorcadeDeclareSchema.setEngineNo(resultSet.getString("engineNo"));
          cIMotorcadeDeclareSchema.setRackNo(resultSet.getString("rackNo"));
          cIMotorcadeDeclareSchema.setOwner(resultSet.getString("owner"));
          cIMotorcadeDeclareSchema.setPolicyHolder(resultSet.getString("policyHolder"));
          cIMotorcadeDeclareSchema.setInsuredName(resultSet.getString("insuredName"));
          cIMotorcadeDeclareSchema.setPassVehicle(resultSet.getString("passVehicle"));
          cIMotorcadeDeclareSchema.setFailureVehicle(resultSet.getString("failureVehicle"));
          cIMotorcadeDeclareSchema.setGroupStartDate(""+resultSet.getDate("groupStartDate"));
          cIMotorcadeDeclareSchema.setGroupEndDate(""+resultSet.getDate("groupEndDate"));
          vector.add(cIMotorcadeDeclareSchema);
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
