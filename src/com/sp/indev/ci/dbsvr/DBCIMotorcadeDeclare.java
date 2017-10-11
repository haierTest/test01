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
 * 定义CIMotorcadeDeclare-团车申报接口表的DB类
 * 从pdm中取数据库信息,根据数据库表生成对应的DB类
 * <p>Title: SinosoftJavaTools v1.4</p>
 * <p>Description: 中科软Java源码生成工具</p>
 * <p>Company: Sinosoft</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>@createdate 2010-03-19</p>
 * @FirstAuthor: Zhouxianli(JavaTools v1.0)
 * @SecondAuthor: Yangkun(JavaTools v1.1->v1.2->v1.3->v1.4)
 * @LastVersion: v1.4.3
 * @UpdateList: 1.新增DB类自动生成带绑定变量insert();update();delete();getInfo();等方法;
 *              2.新增对CIXXXXX平台类的生成处理;
 *              3.优化getCount()方法select count(*) from tableName为select count(1) from tableName;
 */
public class DBCIMotorcadeDeclare extends CIMotorcadeDeclareSchema{
    /**
     * 构造函数
     */       
    public DBCIMotorcadeDeclare(){
    }

    /**
     * 插入一条记录
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
     * 插入一条记录
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
     * 更新一条记录
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
     * 更新一条记录
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
        String strSQL = " SELECT COUNT(1) FROM CIMotorcadeDeclare WHERE "+ strWhere;
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
     * 主函数
     * @param args 参数列表
     */
    public static void main(String[] args){
        
    }
}
