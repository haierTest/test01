package com.sp.indiv.ci.dbsvr;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;
import java.util.Vector;
import javax.naming.NamingException;
import com.sp.indiv.ci.schema.CIInsurePmVehicleSchema;
import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;

/**
 * 定义XX查询主表-CIInsurePmVehicle的DB类
 *
 * @createdate 2014-01-24
 * @author fanjiangtao
 * 
 */
public class DBCIInsurePmVehicle extends CIInsurePmVehicleSchema{
    /**
     * 构造函数
     */       
    public DBCIInsurePmVehicle(){
    }

    /**
     * 插入一条记录
     * @param dbpool dbpool
     * @throws Exception
     */       
    public void insert(DbPool dbpool) throws Exception{
        String strSQL = " Insert Into CIInsurePmVehicle(" + 
                           " PmVehicleID," + 
                           " CarMark," + 
                           " VehicleType," + 
                           " RackNo," + 
                           " EngineNo," + 
                           " VehicleStyle," + 
                           " PmUseType," + 
                           " IneffectualDate," + 
                           " RejectDate," + 
                           " VehicleRegisterDate," + 
                           " LastCheckDate," + 
                           " TransferDate," + 
                           " WholeWeight," + 
                           " LimitLoadPerson," + 
                           " LimitLoad," + 
                           " Displacement," + 
                           " MadeFactory," + 
                           " VehicleModel," + 
                           " ProducerType," + 
                           " VehicleBrand1," + 
                           " VehicleBrand2," + 
                           " Haulage," + 
                           " VehicleColour," + 
                           " SalePrice," + 
                           " PmFuelType," + 
                           " Status," + 
                           " VehicleCategory," + 
                           " InsertDate," + 
                           " UpdateDate," +  
                           " ValidFlag," +  
                           " OperateSite," +  
                           " Flag," + 
                           " OwnerName," +
                           " DemandNo)values(" + 
                           "'" + UUID.randomUUID().toString() +"'" + "," +
                           "'" + getCarMark() +"'" + "," +
                           "'" + getVehicleType() +"'" + "," +
                           "'" + getRackNo() +"'" + "," +
                           "'" + getEngineNo() +"'" + "," +
                           "'" + getVehicleStyle() +"'" + "," +
                           "'" + getPmUseType() +"'" + "," +
                           "to_date('" + getIneffectualDate() + "','yyyy-mm-dd')" + "," + 
                           "to_date('" + getRejectDate() + "','yyyy-mm-dd')" + "," +
                           "to_date('" + getVehicleRegisterDate() + "','yyyy-mm-dd')" + "," +
                           "to_date('" + getLastCheckDate() + "','yyyy-mm-dd')" + "," +
                           "to_date('" + getTransferDate() + "','yyyy-mm-dd')" + "," +
                           "'" + getWholeWeight() +"'" + "," +
                           "'" + getLimitLoadPerson() +"'" + "," +
                           "'" + getLimitLoad() +"'" + "," +
                           "'" + getDisplacement() +"'" + "," +
                           "'" + getMadeFactory() +"'" + "," +
                           "'" + getVehicleModel() +"'" + "," +
                           "'" + getProducerType() +"'" + "," +
                           "'" + getVehicleBrand1() +"'" + "," +
                           "'" + getVehicleBrand2() +"'" + "," +
                           "'" + getHaulage() +"'" + "," +
                           "'" + getVehicleColour() +"'" + "," +
                           "'" + getSalePrice() +"'" + "," +
                           "'" + getPmFuelType() +"'" + "," +
                           "'" + getStatus() +"'" + "," +
                           "'" + getVehicleCategory() +"'" + "," +
                           "to_date('" + getInsertDate() + "','yyyy-mm-dd hh24:mi:ss')" + "," +
                           "to_date('" + getUpdateDate() + "','yyyy-mm-dd hh24:mi:ss')" + "," +
                           "'" + getValidFlag() +"'" + "," +
                           "'" + getOperateSite() +"'" + "," +
                           "'" + getFlag() +"'" + "," +
                           "'" + getOwnerName() +"'" + "," +
                           "'" + getDemandNo() +"'" +
                           ")";
        dbpool.insert(strSQL);
    }

    /**
     * 插入一条记录
     * @throws Exception
     */       
    public void insert() throws Exception{
        DbPool dbpool = new DbPool();
        
        try {
            String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
            if ("1".equals(strSwitch)) {
                dbpool.open("platformNewDataSource");            
            } else {
                dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
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
        finally {
            dbpool.close();
        }
    }

    public void delete(DbPool dbpool,String PmVehicleID) throws Exception{
        String strSQL = " Delete From CIInsurePmVehicle Where PmVehicleID = " + "'" + PmVehicleID + "'";
        dbpool.delete(strSQL);
    }

    public void delete(String demandNo) throws Exception{
        DbPool dbpool = new DbPool();
        
        try {
            String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
            if ("1".equals(strSwitch)) {
                dbpool.open("platformNewDataSource");            
            } else {
                dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
            }
            dbpool.beginTransaction();
            delete(dbpool,demandNo);
            dbpool.commitTransaction();
            dbpool.close();
        }
        catch(Exception exception){
            dbpool.rollbackTransaction();
            dbpool.close();
            throw exception;
        }
        finally {
            dbpool.close();
        }
    }

    public void update(DbPool dbpool) throws Exception{
        String strSQL = " Update CIInsurePmVehicle Set" +
                           " DemandNo = " + "'" +getDemandNo() + "'" + "," +
                           " CarMark = " + "'" +getCarMark() + "'" + "," +
                           " VehicleType = " + "'" +getVehicleType() + "'" + "," +
                           " RackNo = " + "'" +getRackNo() + "'" + "," +
                           " EngineNo = " + "'" +getEngineNo() + "'" + "," +
                           " VehicleStyle = " + "'" +getVehicleStyle() + "'" + "," +
                           " PmUseType = " + "'" +getPmUseType() + "'" + "," +
                           " IneffectualDate=to_date('" + getIneffectualDate() + "','yyyy-mm-dd')" + "," +
                           " RejectDate=to_date('" + getRejectDate() + "','yyyy-mm-dd')" + "," +
                           " VehicleRegisterDate=to_date('" + getVehicleRegisterDate() + "','yyyy-mm-dd')" + "," +
                           " LastCheckDate=to_date('" + getLastCheckDate() + "','yyyy-mm-dd')" + "," +
                           " TransferDate=to_date('" + getTransferDate() + "','yyyy-mm-dd')" + "," +
                           " WholeWeight = " + "'" +getWholeWeight() + "'" + "," +
                           " LimitLoadPerson = " + "'" +getLimitLoadPerson() + "'" + "," +
                           " LimitLoad = " + "'" +getLimitLoad() + "'" + "," +
                           " Displacement = " + "'" +getDisplacement() + "'" + "," +
                           " MadeFactory = " + "'" +getMadeFactory() + "'" + "," +
                           " VehicleModel = " + "'" +getVehicleModel() + "'" + "," +
                           " ProducerType = " + "'" +getProducerType() + "'" + "," +
                           " VehicleBrand1 = " + "'" +getVehicleBrand1() + "'" + "," +
                           " VehicleBrand2 = " + "'" +getVehicleBrand2() + "'" + "," +
                           " Haulage = " + "'" +getHaulage() + "'" + "," +
                           " VehicleColour = " + "'" +getVehicleColour() + "'" + "," +
                           " SalePrice = " + "'" +getSalePrice() + "'" + "," +
                           " PmFuelType = " + "'" +getPmFuelType() + "'" + "," +
                           " Status = " + "'" +getStatus() + "'" + "," +
                           " VehicleCategory = " + "'" +getVehicleCategory() + "'" + "," +
                           " InsertDate = to_date('" + getInsertDate() + "','yyyy-mm-dd hh24:mi:ss')" + "," +
                           " UpdateDate = to_date('" + getUpdateDate() + "','yyyy-mm-dd hh24:mi:ss')" + "," +
                           " ValidFlag = " + "'" +getValidFlag() + "'" + "," +
                           " OperateSite = " + "'" +getOperateSite() + "'" + "," +
                           " Flag = " + "'" +getFlag() + "'" + "," +
                           " OwnerName = " + "'" +getOwnerName() + "'" +
                           " Where PmVehicleID = " + "'" +getPmVehicleID() + "'";
        dbpool.update(strSQL);
    }
    
    public void update(DbPool dbpool, String strWhere) throws Exception
    {
                        	String strSQL = " Update CIInsurePmVehicle Set" +
                        	" DemandNo = " + "'" +getDemandNo() + "'" + "," +
                            " CarMark = " + "'" +getCarMark() + "'" + "," +
                            " VehicleType = " + "'" +getVehicleType() + "'" + "," +
                            " RackNo = " + "'" +getRackNo() + "'" + "," +
                            " EngineNo = " + "'" +getEngineNo() + "'" + "," +
                            " VehicleStyle = " + "'" +getVehicleStyle() + "'" + "," +
                            " PmUseType = " + "'" +getPmUseType() + "'" + "," +
                            " IneffectualDate=to_date('" + getIneffectualDate() + "','yyyy-mm-dd')" + "," +
                            " RejectDate=to_date('" + getRejectDate() + "','yyyy-mm-dd')" + "," +
                            " VehicleRegisterDate=to_date('" + getVehicleRegisterDate() + "','yyyy-mm-dd')" + "," +
                            " LastCheckDate=to_date('" + getLastCheckDate() + "','yyyy-mm-dd')" + "," +
                            " TransferDate=to_date('" + getTransferDate() + "','yyyy-mm-dd')" + "," +
                            " WholeWeight = " + "'" +getWholeWeight() + "'" + "," +
                            " LimitLoadPerson = " + "'" +getLimitLoadPerson() + "'" + "," +
                            " LimitLoad = " + "'" +getLimitLoad() + "'" + "," +
                            " Displacement = " + "'" +getDisplacement() + "'" + "," +
                            " MadeFactory = " + "'" +getMadeFactory() + "'" + "," +
                            " VehicleModel = " + "'" +getVehicleModel() + "'" + "," +
                            " ProducerType = " + "'" +getProducerType() + "'" + "," +
                            " VehicleBrand1 = " + "'" +getVehicleBrand1() + "'" + "," +
                            " VehicleBrand2 = " + "'" +getVehicleBrand2() + "'" + "," +
                            " Haulage = " + "'" +getHaulage() + "'" + "," +
                            " VehicleColour = " + "'" +getVehicleColour() + "'" + "," +
                            " SalePrice = " + "'" +getSalePrice() + "'" + "," +
                            " PmFuelType = " + "'" +getPmFuelType() + "'" + "," +
                            " Status = " + "'" +getStatus() + "'" + "," +
                            " VehicleCategory = " + "'" +getVehicleCategory() + "'" + "," +
                            " InsertDate = to_date('" + getInsertDate() + "','yyyy-mm-dd hh24:mi:ss')" + "," +
                            " UpdateDate = to_date('" + getUpdateDate() + "','yyyy-mm-dd hh24:mi:ss')" + "," +
                            " ValidFlag = " + "'" +getValidFlag() + "'" +
                            " OperateSite = " + "'" +getOperateSite() + "'" +
                            " Flag = " + "'" +getFlag() + "'" +
                            " OwnerName = " + "'" +getOwnerName() + "'" +
                            " Where " + strWhere;
    	dbpool.update(strSQL);
    }

    /**
     * 更新一条记录
     * @throws Exception
     */       
    public void update() throws Exception{
        DbPool dbpool = new DbPool();
        
        try {
            String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
            if ("1".equals(strSwitch)) {
                dbpool.open("platformNewDataSource");            
            } else {
                dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
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
        finally {
            dbpool.close();
        }
    }

    public int getInfo(DbPool dbpool,String pmvehicleid) throws Exception{
        int intResult = 0;
        String strSQL = " Select * From CIInsurePmVehicle Where pmvehicleid = " + "'" + pmvehicleid + "'";
        ResultSet resultSet = dbpool.query(strSQL);
        if(resultSet.next()){
        	setPmVehicleID(resultSet.getString("PmVehicleID"));
            setDemandNo(resultSet.getString("demandNo"));
            setCarMark(resultSet.getString("carMark"));
            setVehicleType(resultSet.getString("vehicleType"));
            setRackNo(resultSet.getString("rackNo"));
            setEngineNo(resultSet.getString("engineNo"));
            setVehicleStyle(resultSet.getString("vehicleStyle"));
            setPmUseType(resultSet.getString("pmUseType"));
            setIneffectualDate( resultSet.getString("ineffectualDate"));
            setRejectDate( resultSet.getString("rejectDate"));
            setVehicleRegisterDate( resultSet.getString("vehicleRegisterDate"));
            setLastCheckDate( resultSet.getString("lastCheckDate"));
            setTransferDate( resultSet.getString("transferDate"));
            setWholeWeight("" + resultSet.getDouble("wholeWeight"));
            setLimitLoadPerson("" + resultSet.getInt("limitLoadPerson"));
            setLimitLoad("" + resultSet.getDouble("limitLoad"));
            setDisplacement("" + resultSet.getDouble("displacement"));
            setMadeFactory(resultSet.getString("madeFactory"));
            setVehicleModel(resultSet.getString("vehicleModel"));
            setProducerType(resultSet.getString("producerType"));
            setVehicleBrand1( resultSet.getString("vehicleBrand1"));
            setVehicleBrand2( resultSet.getString("vehicleBrand2"));
            setHaulage("" + resultSet.getDouble("haulage"));
            setVehicleColour( resultSet.getString("vehicleColour"));
            setSalePrice( resultSet.getString("salePrice"));
            setPmFuelType(resultSet.getString("pmFuelType"));
            setStatus(resultSet.getString("status"));
            setVehicleCategory(resultSet.getString("vehicleCategory"));
            setInsertDate( resultSet.getString("insertDate"));
            setUpdateDate( resultSet.getString("updateDate"));
            setValidFlag(resultSet.getString("validFlag"));
            setOperateSite(resultSet.getString("operateSite"));
            setFlag(resultSet.getString("flag"));
            setOwnerName(resultSet.getString("OwnerName"));
            intResult = 0;
        }
        else{
            intResult = 100;
        }
        resultSet.close();
        return intResult;
    }

    public int getInfo(String pmvehicleid) throws Exception{
        int intResult = 0;
        DbPool dbpool = new DbPool();
        String strDataSource =SysConfig.CONST_DDCCDATASOURCE;
        
        try {
            String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
            if ("1".equals(strSwitch)) {
                dbpool.open("platformNewDataSource");            
            } else {
                dbpool.open(strDataSource);
            }
            intResult=getInfo(dbpool,pmvehicleid);
            dbpool.close();
        }
        catch(Exception exception){
            dbpool.close();
            throw exception;
        }
        finally {
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
        String strSQL = " SELECT COUNT(*) FROM CIInsurePmVehicle WHERE "+ strWhere;
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
        
        try {
            String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
            if ("1".equals(strSwitch)) {
                dbpool.open("platformNewDataSource");            
            } else {
                dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
            }
            intCount=getCount(dbpool,strWhere);
            dbpool.close();
        }
        catch(Exception exception){
            dbpool.close();
            throw exception;
        }
        finally {
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
        
        try {
            String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
            if ("1".equals(strSwitch)) {
                dbpool.open("platformNewDataSource");            
            } else {
                dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
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
        finally {
            dbpool.close();
        }
        return vector;
    }


    /**
     * 查询满足模糊查询条件的记录数,从历史信息库获取
     * @param strSQL  SQLStatement
     * @return 满足模糊查询条件的记录数
     * @throws Exception
     */
    public int getCountHistory(String strWhere) throws
        Exception{
        int intCount = 0;
        DbPool dbpool = new DbPool();
        
        try {
            String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
            if ("1".equals(strSwitch)) {
                dbpool.open("platformNewDataSource");            
            } else {
                dbpool.open(SysConfig.CONST_HISTORYDATASOURCE);
            }
            intCount=getCount(dbpool,strWhere);
            dbpool.close();
        }
        catch(Exception exception){
            dbpool.close();
            throw exception;
        }
        finally {
            dbpool.close();
        }
        return intCount;
    }

    /**
     * 根据SQL语句获取结果集，从历史信息库获取
     * @param strSQL  SQL语句
     * @return Vector 查询结果记录集
     * @throws SQLException    数据库操作异常类
     * @throws NamingException 名字异常类
     */
    public Vector findByConditionsHistory(String strSQL) throws
        Exception,SQLException,NamingException{
        Vector vector = new Vector();
        DbPool dbpool = new DbPool();
        
        try {
            String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
            if ("1".equals(strSwitch)) {
                dbpool.open("platformNewDataSource");            
            } else {
                dbpool.open(SysConfig.CONST_HISTORYDATASOURCE);
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
        finally {
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
        CIInsurePmVehicleSchema cIInsurePmVehicleSchema = null;
        ResultSet resultSet = dbpool.query(strSQL);
        while(resultSet.next())
        {
            cIInsurePmVehicleSchema = new CIInsurePmVehicleSchema();
            cIInsurePmVehicleSchema.setPmVehicleID(resultSet.getString("PmVehicleID"));
            cIInsurePmVehicleSchema.setDemandNo(resultSet.getString("demandNo"));
            cIInsurePmVehicleSchema.setCarMark(resultSet.getString("carMark"));
            cIInsurePmVehicleSchema.setVehicleType(resultSet.getString("vehicleType"));
            cIInsurePmVehicleSchema.setRackNo(resultSet.getString("rackNo"));
            cIInsurePmVehicleSchema.setEngineNo(resultSet.getString("engineNo"));
            cIInsurePmVehicleSchema.setVehicleStyle(resultSet.getString("vehicleStyle"));
            cIInsurePmVehicleSchema.setPmUseType(resultSet.getString("pmUseType"));
            cIInsurePmVehicleSchema.setIneffectualDate("" + resultSet.getDate("ineffectualDate"));
            cIInsurePmVehicleSchema.setRejectDate("" + resultSet.getDate("rejectDate"));
            cIInsurePmVehicleSchema.setVehicleRegisterDate("" + resultSet.getDate("vehicleRegisterDate"));
            cIInsurePmVehicleSchema.setLastCheckDate("" + resultSet.getDate("lastCheckDate"));
            cIInsurePmVehicleSchema.setTransferDate("" + resultSet.getDate("transferDate"));
            cIInsurePmVehicleSchema.setWholeWeight("" + resultSet.getDouble("wholeWeight"));
            cIInsurePmVehicleSchema.setLimitLoadPerson("" + resultSet.getInt("limitLoadPerson"));
            cIInsurePmVehicleSchema.setLimitLoad("" + resultSet.getDouble("limitLoad"));
            cIInsurePmVehicleSchema.setDisplacement("" + resultSet.getDouble("displacement"));
            cIInsurePmVehicleSchema.setMadeFactory(resultSet.getString("madeFactory"));
            cIInsurePmVehicleSchema.setVehicleModel(resultSet.getString("vehicleModel"));
            cIInsurePmVehicleSchema.setProducerType(resultSet.getString("producerType"));
            cIInsurePmVehicleSchema.setVehicleBrand1("" + resultSet.getString("vehicleBrand1"));
            cIInsurePmVehicleSchema.setVehicleBrand2("" + resultSet.getString("vehicleBrand2"));
            cIInsurePmVehicleSchema.setHaulage("" + resultSet.getDouble("haulage"));
            cIInsurePmVehicleSchema.setVehicleColour("" + resultSet.getString("vehicleColour"));
            cIInsurePmVehicleSchema.setSalePrice("" + resultSet.getDouble("salePrice"));
            cIInsurePmVehicleSchema.setPmFuelType(resultSet.getString("pmFuelType"));
            cIInsurePmVehicleSchema.setStatus(resultSet.getString("status"));
            cIInsurePmVehicleSchema.setVehicleCategory(resultSet.getString("vehicleCategory"));
            cIInsurePmVehicleSchema.setInsertDate("" + resultSet.getTimestamp("insertDate"));
            cIInsurePmVehicleSchema.setUpdateDate("" + resultSet.getTimestamp("updateDate"));
            cIInsurePmVehicleSchema.setValidFlag(resultSet.getString("validFlag"));
            cIInsurePmVehicleSchema.setOperateSite(resultSet.getString("operateSite"));
            cIInsurePmVehicleSchema.setFlag(resultSet.getString("flag"));
            cIInsurePmVehicleSchema.setOwnerName(resultSet.getString("OwnerName"));
            vector.add(cIInsurePmVehicleSchema);
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
      String strSQL = " SELECT COUNT(1) FROM CIInsurePmVehicle WHERE "+ strWhere;
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
        CIInsurePmVehicleSchema cIInsurePmVehicleSchema = null;
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
            cIInsurePmVehicleSchema = new CIInsurePmVehicleSchema();
            cIInsurePmVehicleSchema.setPmVehicleID(resultSet.getString("PmVehicleID"));
            cIInsurePmVehicleSchema.setDemandNo(resultSet.getString("demandNo"));
            cIInsurePmVehicleSchema.setCarMark(resultSet.getString("carMark"));
            cIInsurePmVehicleSchema.setVehicleType(resultSet.getString("vehicleType"));
            cIInsurePmVehicleSchema.setRackNo(resultSet.getString("rackNo"));
            cIInsurePmVehicleSchema.setEngineNo(resultSet.getString("engineNo"));
            cIInsurePmVehicleSchema.setVehicleStyle(resultSet.getString("vehicleStyle"));
            cIInsurePmVehicleSchema.setPmUseType(resultSet.getString("pmUseType"));
            cIInsurePmVehicleSchema.setIneffectualDate("" + resultSet.getDate("ineffectualDate"));
            cIInsurePmVehicleSchema.setRejectDate("" + resultSet.getDate("rejectDate"));
            cIInsurePmVehicleSchema.setVehicleRegisterDate("" + resultSet.getDate("vehicleRegisterDate"));
            cIInsurePmVehicleSchema.setLastCheckDate("" + resultSet.getDate("lastCheckDate"));
            cIInsurePmVehicleSchema.setTransferDate("" + resultSet.getDate("transferDate"));
            cIInsurePmVehicleSchema.setWholeWeight("" + resultSet.getDouble("wholeWeight"));
            cIInsurePmVehicleSchema.setLimitLoadPerson("" + resultSet.getInt("limitLoadPerson"));
            cIInsurePmVehicleSchema.setLimitLoad("" + resultSet.getDouble("limitLoad"));
            cIInsurePmVehicleSchema.setDisplacement("" + resultSet.getDouble("displacement"));
            cIInsurePmVehicleSchema.setMadeFactory(resultSet.getString("madeFactory"));
            cIInsurePmVehicleSchema.setVehicleModel(resultSet.getString("vehicleModel"));
            cIInsurePmVehicleSchema.setProducerType(resultSet.getString("producerType"));
            cIInsurePmVehicleSchema.setVehicleBrand1(resultSet.getString("vehicleBrand1"));
            cIInsurePmVehicleSchema.setVehicleBrand2(resultSet.getString("vehicleBrand2"));
            cIInsurePmVehicleSchema.setHaulage("" + resultSet.getDouble("haulage"));
            cIInsurePmVehicleSchema.setVehicleColour(resultSet.getString("vehicleColour"));
            cIInsurePmVehicleSchema.setSalePrice(resultSet.getString("salePrice"));
            cIInsurePmVehicleSchema.setPmFuelType(resultSet.getString("pmFuelType"));
            cIInsurePmVehicleSchema.setStatus(resultSet.getString("status"));
            cIInsurePmVehicleSchema.setVehicleCategory(resultSet.getString("vehicleCategory"));
            cIInsurePmVehicleSchema.setInsertDate("" + resultSet.getTimestamp("insertDate"));
            cIInsurePmVehicleSchema.setUpdateDate("" + resultSet.getTimestamp("updateDate"));
            cIInsurePmVehicleSchema.setValidFlag(resultSet.getString("validFlag"));
            cIInsurePmVehicleSchema.setOperateSite(resultSet.getString("operateSite"));
            cIInsurePmVehicleSchema.setFlag(resultSet.getString("flag"));
            cIInsurePmVehicleSchema.setOwnerName(resultSet.getString("OwnerName"));
            vector.add(cIInsurePmVehicleSchema);
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
