package com.sp.indiv.ci.dbsvr;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.util.*;

import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.indiv.ci.schema.CIEndorValidLossSchema;
import com.sp.indiv.ci.schema.CIEndorValidSchema;

/**
 * 定义批改查询确认表-CIEndorValid的DB类
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>@createdate 2006-06-14</p>
 * @author DBGenerator
 * @version 1.0
 */
public class DBCIEndorValid extends CIEndorValidSchema{
    /**
     * 构造函数
     */       
    public DBCIEndorValid(){
    }

    /**
     * 插入一条记录
     * @param dbpool dbpool
     * @throws Exception
     */       
    public void insert(DbPool dbpool) throws Exception{
        String strSQL = " Insert Into CIEndorValid(" + 
                           " ValidNo," + 
                           " DemandNo," + 
                           " EndorseNo," + 
                           " PolicyNo," + 
                           " ChgPremium," + 
                           " Ptext," + 
                           " ValidTime," + 
                           " ValidStatus," + 
                           " AmendBasedPremium," + 
                           " AmendStandArdPremium," + 
                           " Flag," + 
                           /**<<<<<< added by liujia on 22/08/07 新增批改查询确认重复XX标志，批改类型标识**/
                           " CoverageType," + 
                           " CoverageCode," + 
                           " PeccancyAdjustValue," + 
                           " ClaimAdjustValue," + 
                           " DriverRate," + 
                           " DistrictRate," + 
                           " AdditionRate," + 
                           " PeccancyAdjustReason," + 
                           " ClaimAdjustReason," + 
                           " DriverRateReason," + 
                           " DistrictRateReason," + 
                           " RateFloatFlag," + 
                           " LastBillDate," + 
                           " EndorType," + 
                           " QReinsureFlag," + 
                           " AReinsureFlag," +
                           /**<<<<<< added by liujia end 22/08/07 新增批改查询确认重复XX标志，批改类型标识**/
                           
                           " CurrentTax," +
                           " FormerTax," +
                           " LateFee," +
                           " CancelTax," +
                           
                           " RestricFlag," +
                           " PreferentialPremium," +
                           " PreferentialFormula," +
                           " PreferentialDay," +
                           
                           
                           " SearchSequenceNo," +
                           
                           
                           " TaxAmendPremium," +
                           " TaxAmendDeclare," +
                           " IsAmendTax," +
                            
                           
                           " LoyaltyAdjustReason," +
                           " QueryPastDate," +
                           " TransferDate," +
                           " NonclaimAdjust," +
                           " LoyaltyAdjust," +
                           
                           
                           " NoLoyaltyAdjustReason," +
                           " NoClaimAdjustReason," +
                           
                           
                           " ClaimRecordStartDate," +
                           " ClaimRecordEndDate," +
                           " PeccancyStartDate," +
                           " PeccancyEndDate," +
                           " KindAdjustValue," +
                           " KindAdjustReason," +
                           " NoKindAdjustReason," +
                           " NoPeccancyReason," +
                           
                           
                           " LastPolicyQueryDate," +
                           " LastYearStartDate," +
                           " LastYearEndDate," +
                           " LastPolicyTotalPremium," +
                           " InsureInDoorValue," +
                           " InsureInDoorReason," +
                           " ClaimAmountValue," +
                           " ClaimAmountReason," +
                           " NoClaimAmountReason," +
                           " SpecificRiskValue," +
                           " IndependentValue," +
                           
                           
                           " StartDate," + 
                           " EndDate," + 
                           
                           
                           " LastModelCode,"+
                           " LastModel,"+
                           " LastPurchasePrice,"+
                           
                           
                           " LastProducerCode," +
                           
                           
                           " LicensePlateNo,"+
                           " LicensePlateType,"+
                           " VIN,"+
                           " EngineNo,"+
                           " VehicleType,"+
                           " PmMotorUsageTypeCode,"+
                           " IneffectualDate,"+
                           " RejectDate,"+
                           " FirstRegisterDate,"+
                           " LastCheckDate,"+
                           " WholeWeight,"+
                           " RatedPassengerCapacity,"+
                           " Tonnage,"+
                           " Displacement,"+
                           " MadeFactory,"+
                           " Model,"+
                           " ProducerType,"+
                           " BrandCN,"+
                           " BrandEN,"+
                           " Haulage,"+
                           " VehicleColour,"+
                           " SalePrice,"+
                           " PmFuelType,"+
                           " Status,"+
                           " MotorTypeCode,"+
                           " VehicleOwnerName,"+
                           " NoDriverAdjustReason,"+
                           " UsageTypeMessage,"+
                           " FleetMessage,"+
                           " MileageFactorMessage"+
                           
                           ") values(" +
                           
                           "'" + getValidNo() +"'" + "," +
                           "'" + getDemandNo() +"'" + "," +
                           "'" + getEndorseNo() +"'" + "," +
                           "'" + getPolicyNo() +"'" + "," +
                           "'" + getChgPremium() +"'" + "," +
                           "'" + getPtext() +"'" + "," +
                           "'" + getValidTime() +"'" + "," +
                           "'" + getValidStatus() +"'" + "," +
                           "'" + getAmendBasedPremium() +"'" + "," +
                           "'" + getAmendStandArdPremium() +"'" + "," +
                           "'" + getFlag() +"'" + "," +
                           /**<<<<<< added by liujia on 22/08/07 新增批改查询确认重复XX标志，批改类型标识**/
                           "'" + getCoverageType() +"'" + "," +
                           "'" + getCoverageCode() +"'" + "," +
                           "'" + getPeccancyAdjustValue() +"'" + "," +
                           "'" + getClaimAdjustValue() +"'" + "," +
                           "'" + getDriverRate() +"'" + "," +
                           "'" + getDistrictRate() +"'" + "," +
                           "'" + getAdditionRate() +"'" + "," +
                           "'" + getPeccancyAdjustReason() +"'" + "," +
                           "'" + getClaimAdjustReason() +"'" + "," +
                           "'" + getDriverRateReason() +"'" + "," +
                           "'" + getDistrictRateReason() +"'" + "," +
                           "'" + getRateFloatFlag() +"'" + "," +
                           "'" + getLastBillDate() +"'" + "," +
                           "'" + getEndorType() +"'" + "," +
                           "'" + getQReinsureFlag() +"'" + "," +
                           "'" + getAReinsureFlag() +"'" + "," +
                           /**<<<<<< added by liujia end 22/08/07 新增批改查询确认重复XX标志，批改类型标识**/
                           
                           "'" + getCurrentTax() +"'" + "," +
                           "'" + getFormerTax() +"'" + "," +
                           "'" + getLateFee() +"'" + "," +
                           "'" + getCancelTax() +"'" + "," +
                           
                           
                           "'" + getRestricFlag() +"'" + "," +
                           "'" + getPreferentialPremium() +"'" + "," +
                           "'" + getPreferentialFormula() +"'" + "," +
                           "'" + getPreferentialDay() +"'" +"," +
                           
                           
                           "'" + getSearchSequenceNo() + "', " +
                           
                           
                           "'" + getTaxAmendPremium() +"'" +"," +
                           "'" + getTaxAmendDeclare() +"'" +"," +
                           "'" + getIsAmendTax()      +"'" +"," +
                           
                           
                           "'" + getLoyaltyAdjustReason() + "'," +
                           "to_date('" + getQueryPastDate() + "','yyyy-mm-dd')" + "," +
                           "to_date('" + getTransferDate() + "','yyyy-mm-dd')"  + "," + 
                           "'" + getNonclaimAdjust() +"'" + "," +
                           "'" + getLoyaltyAdjust() +"'" + "," +
                           
                           
                           "'" + getNoLoyaltyAdjustReason() +"'," +
                           "'" + getNoClaimAdjustReason() + "'," +
                           
                           "to_date('"+ getClaimRecordStartDate() + "','yyyy-mm-dd hh24:mi:ss')" + "," +  
                           "to_date('"+ getClaimRecordEndDate() + "','yyyy-mm-dd hh24:mi:ss')" + "," +
                           "to_date('"+ getPeccancyStartDate() + "','yyyy-mm-dd hh24:mi:ss')" + "," +
                           "to_date('"+ getPeccancyEndDate() + "','yyyy-mm-dd hh24:mi:ss')" + "," +
                           "'" + getKindAdjustValue() + "'," +
                           "'" + getKindAdjustReason() + "'," +
                           "'" + getNoKindAdjustReason() + "'," +
                           "'" + getNoPeccancyReason() + "'," +
                           
                           
                           
                           "to_date('" + getLastPolicyQueryDate() + "','yyyy-mm-dd hh24:mi:ss')" + "," +
                           "to_date('" + getLastYearStartDate() + "','yyyy-mm-dd hh24:mi:ss')" + "," +
                           "to_date('" + getLastYearEndDate() + "','yyyy-mm-dd hh24:mi:ss')" + "," +
                           "'" + getLastPolicyTotalPremium() + "'," +
                           "'" + getInsureInDoorValue() + "'," +
                           "'" + getInsureInDoorReason() + "'," +
                           "'" + getClaimAmountValue() + "'," +
                           "'" + getClaimAmountReason() + "'," +
                           "'" + getNoClaimAmountReason() + "'," +
                           "'" + getSpecificRiskValue() + "'," +
                           "'" + getIndependentValue() + "'," +
                           
                           
                           "to_date('" + getStartDate() + "','yyyy-mm-dd hh24:mi:ss')" + "," +
                           "to_date('" + getEndDate() + "','yyyy-mm-dd hh24:mi:ss')" + "," +
                           
                           
                           "'" + getLastModelCode() + "'," +
                           "'" + getLastModel() + "'," +
                           "'" + getLastPurchasePrice() + "'," +
                           
                           
                           "'" + getLastProducerCode() + "'," + 
                           
                           
                           "'" + getLicensePlateNo() +"'" + "," +
                           "'" + getLicensePlateType() +"'" + "," +
                           "'" + getVIN() +"'" + "," +
                           "'" + getEngineNo() +"'" + "," +
                           "'" + getVehicleType() +"'" + "," +
                           "'" + getPmMotorUsageTypeCode() +"'" + "," +
                           "to_date('" + getIneffectualDate() +"','yyyy-MM-dd')" + "," +
                           "to_date('" + getRejectDate() +"','yyyy-MM-dd')" + "," +
                           "to_date('" + getFirstRegisterDate() +"','yyyy-MM-dd')" + "," +
                           "to_date('" + getLastCheckDate() +"','yyyy-MM-dd')" + "," +
                           "'" + getWholeWeight() +"'" + "," +
                           "'" + getRatedPassengerCapacity() +"'" + "," +
                           "'" + getTonnage() +"'" + "," +
                           "'" + getDisplacement() +"'" + "," +
                           "'" + getMadeFactory() +"'" + "," +
                           "'" + getModel() +"'" + "," +
                           "'" + getProducerType() +"'" + "," +
                           "'" + getBrandCN() +"'" + "," +
                           "'" + getBrandEN() +"'" + "," +
                           "'" + getHaulage() +"'" + "," +
                           "'" + getVehicleColour() +"'" + "," +
                           "'" + getSalePrice() +"'" + "," +
                           "'" + getPmFuelType() +"'" + "," +
                           "'" + getStatus() +"'" + "," +
                           "'" + getMotorTypeCode() +"'" + "," +
                           "'" + getVehicleOwnerName() +"'" + "," +
                           "'" + getNoDriverAdjustReason() +"'" + "," +
                           "'" + getUsageTypeMessage() +"'" + "," +
                           "'" + getFleetMessage() +"'" + "," +
                           "'" + getMileageFactorMessage() +"'" + "" +
                           
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

    public void delete(DbPool dbpool,String validNo) throws Exception{
        String strSQL = " Delete From CIEndorValid Where ValidNo = " + "'" + validNo + "'";
        dbpool.delete(strSQL);
    }

    public void delete(String validNo) throws Exception{
        DbPool dbpool = new DbPool();
        
        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
            dbpool.beginTransaction();
            delete(dbpool,validNo);
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
        String strSQL = " Update CIEndorValid Set" +
                           " ValidNo = " + "'" +getValidNo() + "'" + "," +
                           " DemandNo = " + "'" +getDemandNo() + "'" + "," +
                           " EndorseNo = " + "'" +getEndorseNo() + "'" + "," +
                           " PolicyNo = " + "'" +getPolicyNo() + "'" + "," +
                           " ChgPremium = " + "'" +getChgPremium() + "'" + "," +
                           " Ptext = " + "'" +getPtext() + "'" + "," +
                           " ValidTime = " + "'" +getValidTime() + "'" + "," +
                           " ValidStatus = " + "'" +getValidStatus() + "'" + "," +
                           " AmendBasedPremium = " + "'" +getAmendBasedPremium() + "'" + "," +
                           " AmendStandArdPremium = " + "'" +getAmendStandArdPremium() + "'" + "," +
                           " Flag = " + "'" +getFlag() + "'" + "," +
                           /**<<<<<< added by liujia on 22/08/07 新增批改查询确认重复XX标志，批改类型标识**/
                           " CoverageType = " + "'" +getCoverageType() + "'" + "," +
                           " CoverageCode = " + "'" +getCoverageCode() + "'" + "," +
                           " PeccancyAdjustValue = " + "'" +getPeccancyAdjustValue() + "'" + "," +
                           " ClaimAdjustValue = " + "'" +getClaimAdjustValue() + "'" + "," +
                           " DriverRate = " + "'" +getDriverRate() + "'" + "," +
                           " DistrictRate = " + "'" +getDistrictRate() + "'" + "," +
                           " AdditionRate = " + "'" +getAdditionRate() + "'" + "," +
                           " PeccancyAdjustReason = " + "'" +getPeccancyAdjustReason() + "'" + "," +
                           " ClaimAdjustReason = " + "'" +getClaimAdjustReason() + "'" + "," +
                           " DriverRateReason = " + "'" +getDriverRateReason() + "'" + "," +
                           " DistrictRateReason = " + "'" +getDistrictRateReason() + "'" + "," +
                           " RateFloatFlag = " + "'" +getRateFloatFlag() + "'" + "," +
                           " LastBillDate = " + "'" +getLastBillDate() + "'" + "," +
                           " EndorType = " + "'" +getEndorType() + "'" + "," +
                           " QReinsureFlag = " + "'" +getQReinsureFlag() + "'" + "," +
                           " AReinsureFlag = " + "'" +getAReinsureFlag() + "'" + "," +
                           /**<<<<<< added by liujia end 22/08/07 新增批改查询确认重复XX标志，批改类型标识**/
                           
                           " CurrentTax = " + "'" +getCurrentTax() + "'" + "," +
                           " FormerTax = " + "'" +getFormerTax() + "'" + "," +
                           " LateFee = " + "'" +getLateFee() + "'" + "," +
                           " CancelTax = " + "'" +getCancelTax() + "'" + "," +
                           
                           
                           " RestricFlag = " + "'" +getRestricFlag() + "'" + "," +
                           " PreferentialPremium = " + "'" +getPreferentialPremium() + "'" + "," +
                           " PreferentialFormula = " + "'" +getPreferentialFormula() + "'" + "," +
                           " PreferentialDay = " + "'" +getPreferentialDay() + "'" +"," +
                           
                           
                           " SearchSequenceNo = " + "'" +getSearchSequenceNo() + "'" +"," +
                           
                           
                           " TaxAmendPremium = " + "'" +getTaxAmendPremium() + "'" +"," +
                           " TaxAmendDeclare = " + "'" +getTaxAmendDeclare() + "'" +"," +
                           " IsAmendTax = " + "'" +getIsAmendTax() + "'" +"," +
                           
                           
                           " LoyaltyAdjustReason = '" +getLoyaltyAdjustReason() + "'," +
                           " QueryPastDate =to_date('" + getQueryPastDate() + "','yyyy-mm-dd')" + "," +
                           " TransferDate =to_date('" + getTransferDate() + "','yyyy-mm-dd')"  + "," + 
                           " NonclaimAdjust = '"+getNonclaimAdjust() + "'," +
                           " LoyaltyAdjust = '"+getLoyaltyAdjust() + "'," +
                           
                           
                           " NoLoyaltyAdjustReason = '"+getNoLoyaltyAdjustReason() + "'," +
                           " NoClaimAdjustReason = '"+getNoClaimAdjustReason() + "'," +
                           
                           
                           " ClaimRecordStartDate =to_date('" +getClaimRecordStartDate() + "','yyyy-mm-dd hh24:mi:ss')" + "," + 
                           " ClaimRecordEndDate = to_date('" +getClaimRecordEndDate() + "','yyyy-mm-dd hh24:mi:ss')" + "," +
                           " PeccancyStartDate = to_date('"  +getPeccancyStartDate() + "','yyyy-mm-dd hh24:mi:ss')" + "," +
                           " PeccancyEndDate =to_date('" +getPeccancyEndDate() + "','yyyy-mm-dd hh24:mi:ss')" + "," +
                           " KindAdjustValue = '" +getKindAdjustValue() + "'," +
                           " KindAdjustReason = '" +getKindAdjustReason() + "'," +
                           " NoKindAdjustReason = '" +getNoKindAdjustReason() + "'," +
                           " NoPeccancyReason = '" +getNoPeccancyReason() + "'," +
                           
                           
                           " LastPolicyQueryDate =to_date('" +getLastPolicyQueryDate()+ "','yyyy-mm-dd hh24:mi:ss')" + "," + 
                           " LastYearStartDate =to_date('" +getLastYearStartDate()+ "','yyyy-mm-dd hh24:mi:ss')" + "," +
                           " LastYearEndDate =to_date('" +getLastYearEndDate()+ "','yyyy-mm-dd hh24:mi:ss')" + "," +
                           " LastPolicyTotalPremium = '" +getLastPolicyTotalPremium() + "'," +
                           " InsureInDoorValue = '" +getInsureInDoorValue() + "'," +
                           " InsureInDoorReason = '" +getInsureInDoorReason() + "'," +
                           " ClaimAmountValue = '" +getClaimAmountValue() + "'," +
                           " ClaimAmountReason = '" +getClaimAmountReason() + "'," +
                           " NoClaimAmountReason = '" +getNoClaimAmountReason() + "'," +
                           " SpecificRiskValue = '" +getSpecificRiskValue() + "'," +
                           " IndependentValue = '" +getIndependentValue() + "'," +
                           
                           
                           " StartDate =to_date('" +getStartDate() + "','yyyy-mm-dd hh24:mi:ss')" + "," +
                           " EndDate =to_date('" +getEndDate() + "','yyyy-mm-dd hh24:mi:ss')" + "," +
                           
                           
                           " LastModelCode = '" +getLastModelCode() + "'," +
                           " LastModel = '" +getLastModel() + "'," +
                           " LastPurchasePrice = '" +getLastPurchasePrice() + "'," +
                           
                           
                           " LastProducerCode = '" +getLastProducerCode() + "'," + 
                           
                           
                           " LicensePlateNo = " + "'" +getLicensePlateNo() + "'" + "," +
                           " LicensePlateType = " + "'" +getLicensePlateType() + "'" + "," +
                           " VIN = " + "'" +getVIN() + "'" + "," +
                           " EngineNo = " + "'" +getEngineNo() + "'" + "," +
                           " VehicleType = " + "'" +getVehicleType() + "'" + "," +
                           " PmMotorUsageTypeCode = " + "'" +getPmMotorUsageTypeCode() + "'" + "," +
                           " IneffectualDate = to_date(" + "'" +getIneffectualDate() + "','yyyy-mm-dd')" + "," +
                           " RejectDate = to_date(" + "'" +getRejectDate() + "','yyyy-mm-dd')" + "," +
                           " FirstRegisterDate = to_date(" + "'" +getFirstRegisterDate() + "','yyyy-mm-dd')" + "," +
                           " LastCheckDate = to_date(" + "'" +getLastCheckDate() + "','yyyy-mm-dd')" + "," +
                           " WholeWeight = " + "'" +getWholeWeight() + "'" + "," +
                           " RatedPassengerCapacity = " + "'" +getRatedPassengerCapacity() + "'" + "," +
                           " Tonnage = " + "'" +getTonnage() + "'" + "," +
                           " Displacement = " + "'" +getDisplacement() + "'" + "," +
                           " MadeFactory = " + "'" +getMadeFactory() + "'" + "," +
                           " Model = " + "'" +getModel() + "'" + "," +
                           " ProducerType = " + "'" +getProducerType() + "'" + "," +
                           " BrandCN = " + "'" +getBrandCN() + "'" + "," +
                           " BrandEN = " + "'" +getBrandEN() + "'" + "," +
                           " Haulage = " + "'" +getHaulage() + "'" + "," +
                           " VehicleColour = " + "'" +getVehicleColour() + "'" + "," +
                           " SalePrice = " + "'" +getSalePrice() + "'" + "," +
                           " PmFuelType = " + "'" +getPmFuelType() + "'" + "," +
                           " Status = " + "'" +getStatus() + "'" + "," +
                           " MotorTypeCode = " + "'" +getMotorTypeCode() + "'" + "," +
                           " VehicleOwnerName = " + "'" +getVehicleOwnerName() + "'" + "," +
                           " NoDriverAdjustReason = " + "'" +getNoDriverAdjustReason() + "'" + "," +
                           " UsageTypeMessage = " + "'" +getUsageTypeMessage() + "'" + "," +
                           " FleetMessage = " + "'" +getFleetMessage() + "'" + "," +
                           " MileageFactorMessage = " + "'" +getMileageFactorMessage() + "'" + "" +
                           
                           " Where ValidNo = " + "'" +getValidNo() + "'";
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

    public int getInfo(DbPool dbpool,String validNo) throws Exception{
        int intResult = 0;
        String strSQL = " Select * From CIEndorValid Where ValidNo = " + "'" + validNo + "'";
        ResultSet resultSet = dbpool.query(strSQL);
        if(resultSet.next()){
            setValidNo(resultSet.getString("validNo"));
            setDemandNo(resultSet.getString("demandNo"));
            setEndorseNo(resultSet.getString("endorseNo"));
            setPolicyNo(resultSet.getString("policyNo"));
            setChgPremium(resultSet.getString("chgPremium"));
            setPtext(resultSet.getString("ptext"));
            setValidTime("" + resultSet.getDate("validTime"));
            setValidStatus(resultSet.getString("validStatus"));
            setAmendBasedPremium(resultSet.getString("AmendBasedPremium"));
	        setAmendStandArdPremium(resultSet.getString("AmendStandArdPremium"));
            setFlag(resultSet.getString("Flag"));
            /**<<<<<< added by liujia on 22/08/07 新增批改查询确认重复XX标志，批改类型标识**/
            setCoverageType(resultSet.getString("coverageType"));
            setCoverageCode(resultSet.getString("coverageCode"));
            setPeccancyAdjustValue(resultSet.getString("peccancyAdjustValue"));
            setClaimAdjustValue(resultSet.getString("claimAdjustValue"));
            setDriverRate(resultSet.getString("driverRate"));
            setDistrictRate(resultSet.getString("districtRate"));
            setAdditionRate(resultSet.getString("additionRate"));
            setPeccancyAdjustReason(resultSet.getString("peccancyAdjustReason"));
            setClaimAdjustReason(resultSet.getString("claimAdjustReason"));
            setDriverRateReason(resultSet.getString("driverRateReason"));
            setDistrictRateReason(resultSet.getString("districtRateReason"));
            setRateFloatFlag(resultSet.getString("rateFloatFlag"));
            setLastBillDate("" + resultSet.getDate("lastBillDate"));
            setEndorType(resultSet.getString("endorType"));
            setQReinsureFlag(resultSet.getString("qReinsureFlag"));
            setAReinsureFlag(resultSet.getString("aReinsureFlag"));
            /**<<<<<< added by liujia end 22/08/07 新增批改查询确认重复XX标志，批改类型标识**/
            
            setCurrentTax(resultSet.getString("currentTax"));
            setFormerTax(resultSet.getString("formerTax"));
            setLateFee(resultSet.getString("lateFee"));
            setCancelTax(resultSet.getString("cancelTax"));
            
            
            setRestricFlag(resultSet.getString("restricFlag"));
            setPreferentialPremium(resultSet.getString("preferentialPremium"));
            setPreferentialFormula(resultSet.getString("preferentialFormula"));
            setPreferentialDay(resultSet.getString("preferentialDay"));
            
            
            setSearchSequenceNo(resultSet.getString("SearchSequenceNo"));
            
            
            setTaxAmendPremium(resultSet.getString("taxAmendPremium"));
            setTaxAmendDeclare(resultSet.getString("taxAmendDeclare"));
            setIsAmendTax(resultSet.getString("IsAmendTax"));
            
            
            setLoyaltyAdjustReason(resultSet.getString("LoyaltyAdjustReason"));
            setQueryPastDate(resultSet.getString("QueryPastDate"));
            setTransferDate(resultSet.getString("TransferDate"));
            setNonclaimAdjust(resultSet.getString("NonclaimAdjust"));
            setLoyaltyAdjust(resultSet.getString("LoyaltyAdjust"));
            
            
            setNoLoyaltyAdjustReason(resultSet.getString("NoLoyaltyAdjustReason"));
            setNoClaimAdjustReason(resultSet.getString("NoClaimAdjustReason"));
            
            
            setClaimRecordStartDate(resultSet.getString("ClaimRecordStartDate"));
            setClaimRecordEndDate(resultSet.getString("ClaimRecordEndDate"));
            setPeccancyStartDate(resultSet.getString("PeccancyStartDate"));
            setPeccancyEndDate(resultSet.getString("PeccancyEndDate"));
            setKindAdjustValue(resultSet.getString("KindAdjustValue"));
            setKindAdjustReason(resultSet.getString("KindAdjustReason"));
            setNoKindAdjustReason(resultSet.getString("NoKindAdjustReason"));
            setNoPeccancyReason(resultSet.getString("NoPeccancyReason"));
            
            
            setLastPolicyQueryDate(""+resultSet.getDate("LastPolicyQueryDate"));
            setLastYearStartDate(""+resultSet.getDate("LastYearStartDate"));
            setLastYearEndDate(""+resultSet.getDate("LastYearEndDate"));
            setLastPolicyTotalPremium(resultSet.getString("LastPolicyTotalPremium"));
            setInsureInDoorValue(resultSet.getString("InsureInDoorValue"));
            setInsureInDoorReason(resultSet.getString("InsureInDoorReason"));
            setClaimAmountValue(resultSet.getString("ClaimAmountValue"));
            setClaimAmountReason(resultSet.getString("ClaimAmountReason"));
            setNoClaimAmountReason(resultSet.getString("NoClaimAmountReason"));
            setSpecificRiskValue(resultSet.getString("SpecificRiskValue"));
            setIndependentValue(resultSet.getString("IndependentValue"));
            
            
            setStartDate(""+resultSet.getDate("StartDate"));
            setEndDate(""+resultSet.getDate("EndDate"));
            
            
            setLastModelCode(""+resultSet.getDate("LastModelCode"));
            setLastModel(""+resultSet.getDate("LastModel"));
            setLastPurchasePrice(""+resultSet.getDate("LastPurchasePrice"));
            
            
            setLastProducerCode(resultSet.getString("LastProducerCode"));
            
            
            setLicensePlateNo(resultSet.getString("LicensePlateNo"));
            setLicensePlateType(resultSet.getString("LicensePlateType"));
            setVIN(resultSet.getString("VIN"));
            setEngineNo(resultSet.getString("EngineNo"));
            setVehicleType(resultSet.getString("VehicleType"));
            setPmMotorUsageTypeCode(resultSet.getString("PmMotorUsageTypeCode"));
            setIneffectualDate("" + resultSet.getDate("IneffectualDate"));
            setRejectDate("" + resultSet.getDate("RejectDate"));
            setFirstRegisterDate("" + resultSet.getDate("FirstRegisterDate"));
            setLastCheckDate("" + resultSet.getDate("LastCheckDate"));
            setWholeWeight(resultSet.getString("WholeWeight"));
            setRatedPassengerCapacity(resultSet.getString("RatedPassengerCapacity"));
            setTonnage(resultSet.getString("Tonnage"));
            setDisplacement(resultSet.getString("Displacement"));
            setMadeFactory(resultSet.getString("MadeFactory"));
            setModel(resultSet.getString("Model"));
            setProducerType(resultSet.getString("ProducerType"));
            setBrandCN(resultSet.getString("BrandCN"));
            setBrandEN(resultSet.getString("BrandEN"));
            setHaulage(resultSet.getString("Haulage"));
            setVehicleColour(resultSet.getString("VehicleColour"));
            setSalePrice(resultSet.getString("SalePrice"));
            setPmFuelType(resultSet.getString("PmFuelType"));
            setStatus(resultSet.getString("Status"));
            setMotorTypeCode(resultSet.getString("MotorTypeCode"));
            setVehicleOwnerName(resultSet.getString("VehicleOwnerName"));
            setNoDriverAdjustReason(resultSet.getString("NoDriverAdjustReason"));
            setUsageTypeMessage(resultSet.getString("UsageTypeMessage"));
            setFleetMessage(resultSet.getString("FleetMessage"));
            setMileageFactorMessage(resultSet.getString("MileageFactorMessage"));
            
            intResult = 0;
        }
        else{
            intResult = 100;
        }
        resultSet.close();
        return intResult;
    }

    public int getInfo(String validNo) throws Exception{
        int intResult = 0;
        DbPool dbpool = new DbPool();
        String strDataSource =SysConfig.CONST_DDCCDATASOURCE;
        
        try {
            dbpool.open(strDataSource);
            intResult=getInfo(dbpool,validNo);
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
        String strSQL = " SELECT COUNT(*) FROM CIEndorValid WHERE "+ strWhere;
        ResultSet resultSet = dbpool.query(strSQL);
        if(resultSet.next()){
            intCount = resultSet.getInt(1);
            resultSet.close();
        }
        return intCount;
    }
    /**
     * 根据条件获取查询数据条数
     * @author mengxiangbin 20110418
     * @param dbpool
     * @param strWhere
     * @param iWhereValue
     * @return
     * @throws Exception
     */
    public int getCount(DbPool dbpool,String strWhere,ArrayList iWhereValue)
    throws Exception{
      int intCount = 0;
      String strSQL = " SELECT COUNT(1) FROM CIEndorValid WHERE "+ strWhere;
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
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
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
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
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
     * 查询满足模糊查询条件的记录数
     * @param strSQL  SQLStatement
     * @return 满足模糊查询条件的记录数
     * @throws Exception
     */
    public int getCountHistory(String strWhere) throws
        Exception{
        int intCount = 0;
        DbPool dbpool = new DbPool();
        
        try {
            dbpool.open(SysConfig.CONST_HISTORYDATASOURCE);
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
    public Vector findByConditionsHistory(String strSQL) throws
        Exception,SQLException,NamingException{
        Vector vector = new Vector();
        DbPool dbpool = new DbPool();
        
        try {
            dbpool.open(SysConfig.CONST_HISTORYDATASOURCE);
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
        CIEndorValidSchema cIEndorValidSchema = null;
        ResultSet resultSet = dbpool.query(strSQL);
        while(resultSet.next())
        {
            cIEndorValidSchema = new CIEndorValidSchema();
            cIEndorValidSchema.setValidNo(resultSet.getString("validNo"));
            cIEndorValidSchema.setDemandNo(resultSet.getString("demandNo"));
            cIEndorValidSchema.setEndorseNo(resultSet.getString("endorseNo"));
            cIEndorValidSchema.setPolicyNo(resultSet.getString("policyNo"));
            cIEndorValidSchema.setChgPremium(resultSet.getString("chgPremium"));
            cIEndorValidSchema.setPtext(resultSet.getString("ptext"));
            cIEndorValidSchema.setValidTime("" + resultSet.getDate("validTime"));
            cIEndorValidSchema.setValidStatus(resultSet.getString("validStatus"));
            cIEndorValidSchema.setAmendBasedPremium(resultSet.getString("AmendBasedPremium"));
            cIEndorValidSchema.setAmendStandArdPremium(resultSet.getString("AmendStandArdPremium"));
            cIEndorValidSchema.setFlag(resultSet.getString("flag"));
            /**<<<<<< added by liujia on 22/08/07 新增批改查询确认重复XX标志，批改类型标识**/
            cIEndorValidSchema.setCoverageType(resultSet.getString("coverageType"));
            cIEndorValidSchema.setCoverageCode(resultSet.getString("coverageCode"));
            cIEndorValidSchema.setPeccancyAdjustValue(resultSet.getString("peccancyAdjustValue"));
            cIEndorValidSchema.setClaimAdjustValue(resultSet.getString("claimAdjustValue"));
            cIEndorValidSchema.setDriverRate(resultSet.getString("driverRate"));
            cIEndorValidSchema.setDistrictRate(resultSet.getString("districtRate"));
            cIEndorValidSchema.setAdditionRate(resultSet.getString("additionRate"));
            cIEndorValidSchema.setPeccancyAdjustReason(resultSet.getString("peccancyAdjustReason"));
            cIEndorValidSchema.setClaimAdjustReason(resultSet.getString("claimAdjustReason"));
            cIEndorValidSchema.setDriverRateReason(resultSet.getString("driverRateReason"));
            cIEndorValidSchema.setDistrictRateReason(resultSet.getString("districtRateReason"));
            cIEndorValidSchema.setRateFloatFlag(resultSet.getString("rateFloatFlag"));
            cIEndorValidSchema.setLastBillDate("" + resultSet.getDate("lastBillDate"));
            cIEndorValidSchema.setEndorType(resultSet.getString("endorType"));
            cIEndorValidSchema.setQReinsureFlag(resultSet.getString("qReinsureFlag"));
            cIEndorValidSchema.setAReinsureFlag(resultSet.getString("aReinsureFlag"));
            /**<<<<<< added by liujia on 22/08/07 新增批改查询确认重复XX标志，批改类型标识**/
            
            cIEndorValidSchema.setCurrentTax(resultSet.getString("currentTax"));
            cIEndorValidSchema.setFormerTax(resultSet.getString("formerTax"));
            cIEndorValidSchema.setLateFee(resultSet.getString("lateFee"));
            cIEndorValidSchema.setCancelTax(resultSet.getString("cancelTax"));
            
            
            cIEndorValidSchema.setRestricFlag(resultSet.getString("restricFlag"));
            cIEndorValidSchema.setPreferentialPremium(resultSet.getString("preferentialPremium"));
            cIEndorValidSchema.setPreferentialFormula(resultSet.getString("preferentialFormula"));
            cIEndorValidSchema.setPreferentialDay(resultSet.getString("preferentialDay"));
            
            
            cIEndorValidSchema.setSearchSequenceNo(resultSet.getString("SearchSequenceNo"));
            
            
            cIEndorValidSchema.setTaxAmendPremium(resultSet.getString("taxAmendPremium"));
            cIEndorValidSchema.setTaxAmendDeclare(resultSet.getString("taxAmendDeclare"));
            cIEndorValidSchema.setIsAmendTax(resultSet.getString("IsAmendTax"));
            
            
            cIEndorValidSchema.setLoyaltyAdjustReason(resultSet.getString("LoyaltyAdjustReason"));
            cIEndorValidSchema.setQueryPastDate(resultSet.getString("QueryPastDate"));
            cIEndorValidSchema.setTransferDate(resultSet.getString("TransferDate"));
            cIEndorValidSchema.setNonclaimAdjust(resultSet.getString("NonclaimAdjust"));
            cIEndorValidSchema.setLoyaltyAdjust(resultSet.getString("LoyaltyAdjust"));
            
            
            cIEndorValidSchema.setNoLoyaltyAdjustReason(resultSet.getString("NoLoyaltyAdjustReason"));
            cIEndorValidSchema.setNoClaimAdjustReason(resultSet.getString("NoClaimAdjustReason"));
            
            
            cIEndorValidSchema.setClaimRecordStartDate(resultSet.getString("ClaimRecordStartDate"));
            cIEndorValidSchema.setClaimRecordEndDate(resultSet.getString("ClaimRecordEndDate"));
            cIEndorValidSchema.setPeccancyStartDate(resultSet.getString("PeccancyStartDate"));
            cIEndorValidSchema.setPeccancyEndDate(resultSet.getString("PeccancyEndDate"));
            cIEndorValidSchema.setKindAdjustValue(resultSet.getString("KindAdjustValue"));
            cIEndorValidSchema.setKindAdjustReason(resultSet.getString("KindAdjustReason"));
            cIEndorValidSchema.setNoKindAdjustReason(resultSet.getString("NoKindAdjustReason"));
            cIEndorValidSchema.setNoPeccancyReason(resultSet.getString("NoPeccancyReason"));
            
            
            cIEndorValidSchema.setLastPolicyQueryDate(""+resultSet.getDate("LastPolicyQueryDate"));
            cIEndorValidSchema.setLastYearStartDate(""+resultSet.getDate("LastYearStartDate"));
            cIEndorValidSchema.setLastYearEndDate(""+resultSet.getDate("LastYearEndDate"));
            cIEndorValidSchema.setLastPolicyTotalPremium(resultSet.getString("LastPolicyTotalPremium"));
            cIEndorValidSchema.setInsureInDoorValue(resultSet.getString("InsureInDoorValue"));
            cIEndorValidSchema.setInsureInDoorReason(resultSet.getString("InsureInDoorReason"));
            cIEndorValidSchema.setClaimAmountValue(resultSet.getString("ClaimAmountValue"));
            cIEndorValidSchema.setClaimAmountReason(resultSet.getString("ClaimAmountReason"));
            cIEndorValidSchema.setNoClaimAmountReason(resultSet.getString("NoClaimAmountReason"));
            cIEndorValidSchema.setSpecificRiskValue(resultSet.getString("SpecificRiskValue"));
            cIEndorValidSchema.setIndependentValue(resultSet.getString("IndependentValue"));
            
            
            cIEndorValidSchema.setStartDate(""+resultSet.getDate("StartDate"));
            cIEndorValidSchema.setEndDate(""+resultSet.getDate("EndDate"));
            
            
            cIEndorValidSchema.setLastModelCode(resultSet.getString("LastModelCode"));
            cIEndorValidSchema.setLastModel(resultSet.getString("LastModel"));
            cIEndorValidSchema.setLastPurchasePrice(resultSet.getString("LastPurchasePrice"));
            
            
            cIEndorValidSchema.setLastProducerCode(resultSet.getString("LastProducerCode"));
            
            
            cIEndorValidSchema.setLicensePlateNo(resultSet.getString("LicensePlateNo"));
            cIEndorValidSchema.setLicensePlateType(resultSet.getString("LicensePlateType"));
            cIEndorValidSchema.setVIN(resultSet.getString("VIN"));
            cIEndorValidSchema.setEngineNo(resultSet.getString("EngineNo"));
            cIEndorValidSchema.setVehicleType(resultSet.getString("VehicleType"));
            cIEndorValidSchema.setPmMotorUsageTypeCode(resultSet.getString("PmMotorUsageTypeCode"));
            cIEndorValidSchema.setIneffectualDate("" + resultSet.getDate("IneffectualDate"));
            cIEndorValidSchema.setRejectDate("" + resultSet.getDate("RejectDate"));
            cIEndorValidSchema.setFirstRegisterDate("" + resultSet.getDate("FirstRegisterDate"));
            cIEndorValidSchema.setLastCheckDate("" + resultSet.getDate("LastCheckDate"));
            cIEndorValidSchema.setWholeWeight(resultSet.getString("WholeWeight"));
            cIEndorValidSchema.setRatedPassengerCapacity(resultSet.getString("RatedPassengerCapacity"));
            cIEndorValidSchema.setTonnage(resultSet.getString("Tonnage"));
            cIEndorValidSchema.setDisplacement(resultSet.getString("Displacement"));
            cIEndorValidSchema.setMadeFactory(resultSet.getString("MadeFactory"));
            cIEndorValidSchema.setModel(resultSet.getString("Model"));
            cIEndorValidSchema.setProducerType(resultSet.getString("ProducerType"));
            cIEndorValidSchema.setBrandCN(resultSet.getString("BrandCN"));
            cIEndorValidSchema.setBrandEN(resultSet.getString("BrandEN"));
            cIEndorValidSchema.setHaulage(resultSet.getString("Haulage"));
            cIEndorValidSchema.setVehicleColour(resultSet.getString("VehicleColour"));
            cIEndorValidSchema.setSalePrice(resultSet.getString("SalePrice"));
            cIEndorValidSchema.setPmFuelType(resultSet.getString("PmFuelType"));
            cIEndorValidSchema.setStatus(resultSet.getString("Status"));
            cIEndorValidSchema.setMotorTypeCode(resultSet.getString("MotorTypeCode"));
            cIEndorValidSchema.setVehicleOwnerName(resultSet.getString("VehicleOwnerName"));
            cIEndorValidSchema.setNoDriverAdjustReason(resultSet.getString("NoDriverAdjustReason"));
            cIEndorValidSchema.setUsageTypeMessage(resultSet.getString("UsageTypeMessage"));
            cIEndorValidSchema.setFleetMessage(resultSet.getString("FleetMessage"));
            cIEndorValidSchema.setMileageFactorMessage(resultSet.getString("MileageFactorMessage"));
            
            vector.add(cIEndorValidSchema);
        }
        resultSet.close();
        return vector;
    }
    
    /**
     * 根据SQL语句获取结果集
     * @author mengxiangbin 20110418
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
        CIEndorValidSchema cIEndorValidSchema = null;
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
            cIEndorValidSchema = new CIEndorValidSchema();
            cIEndorValidSchema.setValidNo(resultSet.getString("validNo"));
            cIEndorValidSchema.setDemandNo(resultSet.getString("demandNo"));
            cIEndorValidSchema.setEndorseNo(resultSet.getString("endorseNo"));
            cIEndorValidSchema.setPolicyNo(resultSet.getString("policyNo"));
            cIEndorValidSchema.setChgPremium(resultSet.getString("chgPremium"));
            cIEndorValidSchema.setPtext(resultSet.getString("ptext"));
            cIEndorValidSchema.setValidTime("" + resultSet.getDate("validTime"));
            cIEndorValidSchema.setValidStatus(resultSet.getString("validStatus"));
            cIEndorValidSchema.setAmendBasedPremium(resultSet.getString("AmendBasedPremium"));
            cIEndorValidSchema.setAmendStandArdPremium(resultSet.getString("AmendStandArdPremium"));
            cIEndorValidSchema.setFlag(resultSet.getString("flag"));
            /**<<<<<< added by liujia on 22/08/07 新增批改查询确认重复XX标志，批改类型标识**/
            cIEndorValidSchema.setCoverageType(resultSet.getString("coverageType"));
            cIEndorValidSchema.setCoverageCode(resultSet.getString("coverageCode"));
            cIEndorValidSchema.setPeccancyAdjustValue(resultSet.getString("peccancyAdjustValue"));
            cIEndorValidSchema.setClaimAdjustValue(resultSet.getString("claimAdjustValue"));
            cIEndorValidSchema.setDriverRate(resultSet.getString("driverRate"));
            cIEndorValidSchema.setDistrictRate(resultSet.getString("districtRate"));
            cIEndorValidSchema.setAdditionRate(resultSet.getString("additionRate"));
            cIEndorValidSchema.setPeccancyAdjustReason(resultSet.getString("peccancyAdjustReason"));
            cIEndorValidSchema.setClaimAdjustReason(resultSet.getString("claimAdjustReason"));
            cIEndorValidSchema.setDriverRateReason(resultSet.getString("driverRateReason"));
            cIEndorValidSchema.setDistrictRateReason(resultSet.getString("districtRateReason"));
            cIEndorValidSchema.setRateFloatFlag(resultSet.getString("rateFloatFlag"));
            cIEndorValidSchema.setLastBillDate("" + resultSet.getDate("lastBillDate"));
            cIEndorValidSchema.setEndorType(resultSet.getString("endorType"));
            cIEndorValidSchema.setQReinsureFlag(resultSet.getString("qReinsureFlag"));
            cIEndorValidSchema.setAReinsureFlag(resultSet.getString("aReinsureFlag"));
            /**<<<<<< added by liujia on 22/08/07 新增批改查询确认重复XX标志，批改类型标识**/
            
            cIEndorValidSchema.setCurrentTax(resultSet.getString("currentTax"));
            cIEndorValidSchema.setFormerTax(resultSet.getString("formerTax"));
            cIEndorValidSchema.setLateFee(resultSet.getString("lateFee"));
            cIEndorValidSchema.setCancelTax(resultSet.getString("cancelTax"));
            
            
            cIEndorValidSchema.setRestricFlag(resultSet.getString("restricFlag"));
            cIEndorValidSchema.setPreferentialPremium(resultSet.getString("preferentialPremium"));
            cIEndorValidSchema.setPreferentialFormula(resultSet.getString("preferentialFormula"));
            cIEndorValidSchema.setPreferentialDay(resultSet.getString("preferentialDay"));
            
            
            cIEndorValidSchema.setSearchSequenceNo(resultSet.getString("SearchSequenceNo"));
            
            
            cIEndorValidSchema.setTaxAmendPremium(resultSet.getString("taxAmendPremium"));
            cIEndorValidSchema.setTaxAmendDeclare(resultSet.getString("taxAmendDeclare"));
            cIEndorValidSchema.setIsAmendTax(resultSet.getString("IsAmendTax"));
            
            
            cIEndorValidSchema.setLoyaltyAdjustReason(resultSet.getString("LoyaltyAdjustReason"));
            cIEndorValidSchema.setQueryPastDate(resultSet.getString("QueryPastDate"));
            cIEndorValidSchema.setTransferDate(resultSet.getString("TransferDate"));
            cIEndorValidSchema.setNonclaimAdjust(resultSet.getString("NonclaimAdjust"));
            cIEndorValidSchema.setLoyaltyAdjust(resultSet.getString("LoyaltyAdjust"));
            
            
            cIEndorValidSchema.setNoLoyaltyAdjustReason(resultSet.getString("NoLoyaltyAdjustReason"));
            cIEndorValidSchema.setNoClaimAdjustReason(resultSet.getString("NoClaimAdjustReason"));
            
            
            cIEndorValidSchema.setClaimRecordStartDate(resultSet.getString("ClaimRecordStartDate"));
            cIEndorValidSchema.setClaimRecordEndDate(resultSet.getString("ClaimRecordEndDate"));
            cIEndorValidSchema.setPeccancyStartDate(resultSet.getString("PeccancyStartDate"));
            cIEndorValidSchema.setPeccancyEndDate(resultSet.getString("PeccancyEndDate"));
            cIEndorValidSchema.setKindAdjustValue(resultSet.getString("KindAdjustValue"));
            cIEndorValidSchema.setKindAdjustReason(resultSet.getString("KindAdjustReason"));
            cIEndorValidSchema.setNoKindAdjustReason(resultSet.getString("NoKindAdjustReason"));
            cIEndorValidSchema.setNoPeccancyReason(resultSet.getString("NoPeccancyReason"));
            
            
            cIEndorValidSchema.setLastPolicyQueryDate(""+resultSet.getDate("LastPolicyQueryDate"));
            cIEndorValidSchema.setLastYearStartDate(""+resultSet.getDate("LastYearStartDate"));
            cIEndorValidSchema.setLastYearEndDate(""+resultSet.getDate("LastYearEndDate"));
            cIEndorValidSchema.setLastPolicyTotalPremium(resultSet.getString("LastPolicyTotalPremium"));
            cIEndorValidSchema.setInsureInDoorValue(resultSet.getString("InsureInDoorValue"));
            cIEndorValidSchema.setInsureInDoorReason(resultSet.getString("InsureInDoorReason"));
            cIEndorValidSchema.setClaimAmountValue(resultSet.getString("ClaimAmountValue"));
            cIEndorValidSchema.setClaimAmountReason(resultSet.getString("ClaimAmountReason"));
            cIEndorValidSchema.setNoClaimAmountReason(resultSet.getString("NoClaimAmountReason"));
            cIEndorValidSchema.setSpecificRiskValue(resultSet.getString("SpecificRiskValue"));
            cIEndorValidSchema.setIndependentValue(resultSet.getString("IndependentValue"));
            
            
            cIEndorValidSchema.setStartDate(""+resultSet.getDate("StartDate"));
            cIEndorValidSchema.setEndDate(""+resultSet.getDate("EndDate"));
            
            
            cIEndorValidSchema.setLastModelCode(resultSet.getString("LastModelCode"));
            cIEndorValidSchema.setLastModel(resultSet.getString("LastModel"));
            cIEndorValidSchema.setLastPurchasePrice(resultSet.getString("LastPurchasePrice"));
            
            
            cIEndorValidSchema.setLastProducerCode(resultSet.getString("LastProducerCode"));
            
            
            cIEndorValidSchema.setLicensePlateNo(resultSet.getString("LicensePlateNo"));
            cIEndorValidSchema.setLicensePlateType(resultSet.getString("LicensePlateType"));
            cIEndorValidSchema.setVIN(resultSet.getString("VIN"));
            cIEndorValidSchema.setEngineNo(resultSet.getString("EngineNo"));
            cIEndorValidSchema.setVehicleType(resultSet.getString("VehicleType"));
            cIEndorValidSchema.setPmMotorUsageTypeCode(resultSet.getString("PmMotorUsageTypeCode"));
            cIEndorValidSchema.setIneffectualDate("" + resultSet.getDate("IneffectualDate"));
            cIEndorValidSchema.setRejectDate("" + resultSet.getDate("RejectDate"));
            cIEndorValidSchema.setFirstRegisterDate("" + resultSet.getDate("FirstRegisterDate"));
            cIEndorValidSchema.setLastCheckDate("" + resultSet.getDate("LastCheckDate"));
            cIEndorValidSchema.setWholeWeight(resultSet.getString("WholeWeight"));
            cIEndorValidSchema.setRatedPassengerCapacity(resultSet.getString("RatedPassengerCapacity"));
            cIEndorValidSchema.setTonnage(resultSet.getString("Tonnage"));
            cIEndorValidSchema.setDisplacement(resultSet.getString("Displacement"));
            cIEndorValidSchema.setMadeFactory(resultSet.getString("MadeFactory"));
            cIEndorValidSchema.setModel(resultSet.getString("Model"));
            cIEndorValidSchema.setProducerType(resultSet.getString("ProducerType"));
            cIEndorValidSchema.setBrandCN(resultSet.getString("BrandCN"));
            cIEndorValidSchema.setBrandEN(resultSet.getString("BrandEN"));
            cIEndorValidSchema.setHaulage(resultSet.getString("Haulage"));
            cIEndorValidSchema.setVehicleColour(resultSet.getString("VehicleColour"));
            cIEndorValidSchema.setSalePrice(resultSet.getString("SalePrice"));
            cIEndorValidSchema.setPmFuelType(resultSet.getString("PmFuelType"));
            cIEndorValidSchema.setStatus(resultSet.getString("Status"));
            cIEndorValidSchema.setMotorTypeCode(resultSet.getString("MotorTypeCode"));
            cIEndorValidSchema.setVehicleOwnerName(resultSet.getString("VehicleOwnerName"));
            cIEndorValidSchema.setNoDriverAdjustReason(resultSet.getString("NoDriverAdjustReason"));
            cIEndorValidSchema.setUsageTypeMessage(resultSet.getString("UsageTypeMessage"));
            cIEndorValidSchema.setFleetMessage(resultSet.getString("FleetMessage"));
            cIEndorValidSchema.setMileageFactorMessage(resultSet.getString("MileageFactorMessage"));
            
            vector.add(cIEndorValidSchema);
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
