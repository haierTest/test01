package com.sp.indiv.ci.dbsvr;

import java.sql.ResultSet;

import com.sp.indiv.ci.schema.CIEndorValidCarInfoSchema;
import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;

public class DBCIEndorValidCarInfo extends CIEndorValidCarInfoSchema {

	public DBCIEndorValidCarInfo(){};
	
	public void insert(DbPool dbpool) throws Exception {
		String strSQL = "insert into CIEndorValidCarInfo(" +
				" DemandNo," +
				" Carmark," +
				" VehicleType," +
				" Rackno," +
				" Engineno," +
				" VehiclesTyle," +
				" PmuseType," +
				" IneffectualDate," +
				" RejectDate," +
				" VehicleRegisterDate," +
				" LastcheckDate," +
				" TransferDate," +
				" WholeWeight," +
				" LimitLoadPerson," +
				" LimitLoad," +
				" Displacement," +
				" MadeFactory," +
				" VehicleModel," +
				" ProducerType," +
				" VehiclebrandCH," +
				" VehiclebrandEN," +
				" Haulage," +
				" VehicleColour," +
				" SalePrice," +
				" PmfuelType," +
				" Status," +
				" VehicleCategory," +
				" OwnerName" +
				" )values(" +
				"'" + getDemandNo() +"'," +
				"'" + getCarmark() +"'," +
				"'" + getVehicleType() +"'," +
				"'" + getRackno() +"'," +
				"'" + getEngineno() +"'," +
				"'" + getVehiclesTyle() +"'," +
				"'" + getPmuseType() +"'," +
				"'" + getIneffectualDate() +"'," +
				"'" + getRejectDate() +"'," +
				"'" + getVehicleRegisterDate() +"'," +
				"'" + getLastcheckDate() +"'," +
				"'" + getTransferDate() +"'," +
				"'" + getWholeWeight() +"'," +
				"'" + getLimitLoadPerson() +"'," +
				"'" + getLimitLoad() +"'," +
				"'" + getDisplacement() +"'," +
				"'" + getMadeFactory() +"'," +
				"'" + getVehicleModel() +"'," +
				"'" + getProducerType() +"'," +
				"'" + getVehiclebrandCH() +"'," +
				"'" + getVehiclebrandEN() +"'," +
				"'" + getHaulage() +"'," +
				"'" + getVehicleColour() +"'," +
				"'" + getSalePrice() +"'," +
				"'" + getPmfuelType() +"'," +
				"'" + getStatus() +"'," +
				"'" + getVehicleCategory() +"'," +
				"'" + getOwnerName() +"'" +
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
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
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

    public void delete(DbPool dbpool,String iDemandNo) throws Exception{
        String strSQL = " Delete From CIEndorValidCarInfo Where DemandNo = " + "'" + iDemandNo + "'";
        dbpool.delete(strSQL);
    }

    public void delete(String iDemandNo) throws Exception{
        DbPool dbpool = new DbPool();
        
        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
            dbpool.beginTransaction();
            delete(dbpool,iDemandNo);
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

    public int getInfo(DbPool dbpool,String iDemandNo) throws Exception{
        int intResult = 0;
        String strSQL = " Select * From CIEndorValidCarInfo Where ValidNo = " + "'" + iDemandNo + "'";
        ResultSet resultSet = dbpool.query(strSQL);
        if(resultSet.next()){
        	setDemandNo(resultSet.getString("DemandNo"));
        	setCarmark(resultSet.getString("Carmark"));
        	setVehicleType(resultSet.getString("VehicleType"));
        	setRackno(resultSet.getString("Rackno"));
        	setEngineno(resultSet.getString("Engineno"));
        	setVehiclesTyle(resultSet.getString("VehiclesTyle"));
        	setPmuseType(resultSet.getString("PmuseType"));
        	setIneffectualDate(resultSet.getString("IneffectualDate"));
        	setRejectDate(resultSet.getString("RejectDate"));
        	setVehicleRegisterDate(resultSet.getString("VehicleRegisterDate"));
        	setLastcheckDate(resultSet.getString("LastcheckDate"));
        	setTransferDate(resultSet.getString("TransferDate"));
        	setWholeWeight(resultSet.getString("WholeWeight"));
        	setLimitLoadPerson(resultSet.getString("LimitLoadPerson"));
        	setLimitLoad(resultSet.getString("LimitLoad"));
        	setDisplacement(resultSet.getString("Displacement"));
        	setMadeFactory(resultSet.getString("MadeFactory"));
        	setVehicleModel(resultSet.getString("VehicleModel"));
        	setProducerType(resultSet.getString("ProducerType"));
        	setVehiclebrandCH(resultSet.getString("VehiclebrandCH"));
        	setVehiclebrandEN(resultSet.getString("VehiclebrandEN"));
        	setHaulage(resultSet.getString("Haulage"));
        	setVehicleColour(resultSet.getString("VehicleColour"));
        	setSalePrice(resultSet.getString("SalePrice"));
        	setPmfuelType(resultSet.getString("PmfuelType"));
        	setStatus(resultSet.getString("Status"));
        	setVehicleCategory(resultSet.getString("VehicleCategory"));
        	setOwnerName(resultSet.getString("OwnerName"));
            intResult = 0;
        }
        else{
            intResult = 100;
        }
        resultSet.close();
        return intResult;
    }

    public void update(DbPool dbpool) throws Exception{
        String strSQL = " Update CIEndorValidCarInfo Set" + 
        		 " DemandNo = " + "'" +getDemandNo() + "'," +
        		 " Carmark = " + "'" +getCarmark() + "'," +
        		 " VehicleType = " + "'" +getVehicleType() + "'," +
        		 " Rackno = " + "'" +getRackno() + "'," +
        		 " Engineno = " + "'" +getEngineno() + "'," +
        		 " VehiclesTyle = " + "'" +getVehiclesTyle() + "'," +
        		 " PmuseType = " + "'" +getPmuseType() + "'," +
        		 " IneffectualDate = " + "'" +getIneffectualDate() + "'," +
        		 " RejectDate = " + "'" +getRejectDate() + "'," +
        		 " VehicleRegisterDate = " + "'" +getVehicleRegisterDate() + "'," +
        		 " LastcheckDate = " + "'" +getLastcheckDate() + "'," +
        		 " TransferDate = " + "'" +getTransferDate() + "'," +
        		 " WholeWeight = " + "'" +getWholeWeight() + "'," +
        		 " LimitLoadPerson = " + "'" +getLimitLoadPerson() + "'," +
        		 " LimitLoad = " + "'" +getLimitLoad() + "'," +
        		 " Displacement = " + "'" +getDisplacement() + "'," +
        		 " MadeFactory = " + "'" +getMadeFactory() + "'," +
        		 " VehicleModel = " + "'" +getVehicleModel() + "'," +
        		 " ProducerType = " + "'" +getProducerType() + "'," +
        		 " VehiclebrandCH = " + "'" +getVehiclebrandCH() + "'," +
        		 " VehiclebrandEN = " + "'" +getVehiclebrandEN() + "'," +
        		 " Haulage = " + "'" +getHaulage() + "'," +
        		 " VehicleColour = " + "'" +getVehicleColour() + "'," +
        		 " SalePrice = " + "'" +getSalePrice() + "'," +
        		 " PmfuelType = " + "'" +getPmfuelType() + "'," +
        		 " Status = " + "'" +getStatus() + "'," +
        		 " VehicleCategory = " + "'" +getVehicleCategory() + "'," +
        		 " OwnerName = " + "'" +getOwnerName() + "'" +
        		 " Where DemandNo = " + "'" +getDemandNo() + "'";
        dbpool.update(strSQL);
    }

    /**
     * 更新一条记录
     * @throws Exception
     */       
    public void update() throws Exception{
        DbPool dbpool = new DbPool();
        
        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
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

    public int getInfo(String iDemandNo) throws Exception{
        int intResult = 0;
        DbPool dbpool = new DbPool();
        String strDataSource =SysConfig.CONST_DDCCDATASOURCE;
        
        try {
            dbpool.open(strDataSource);
            intResult=getInfo(dbpool,iDemandNo);
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
}
