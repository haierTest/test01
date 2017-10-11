package com.sp.indiv.ci.dbsvr;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import javax.naming.NamingException;

import com.sp.indiv.ci.schema.CIInsureDemandSchema;
import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;

/**
 * 定义XX查询主表-CIInsureDemand的DB类 
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>@createdate 2006-06-14</p>
 * @author DBGenerator
 * @version 1.0
 */
public class DBCIInsureDemand extends CIInsureDemandSchema{
    /**
     * 构造函数
     */       
    public DBCIInsureDemand(){
    }

    /**
     * 插入一条记录
     * @param dbpool dbpool
     * @throws Exception
     */       
    public void insert(DbPool dbpool) throws Exception{
        String strSQL = " Insert Into CIInsureDemand(" + 
                           " ProposalNo," + 
                           " DemandNo," + 
                           " PolicyNo," + 
                           " LicenseNo," + 
                           " LicenseType," + 
                           " UseNatureCode," + 
                           " FrameNo," + 
                           " EngineNo," + 
                           " LicenseColorCode," + 
                           " CarOwner," + 
                           " EnrollDate," + 
                           " MakeDate," + 
                           " SeatCount," + 
                           " TonCount," + 
                           " ValidCheckDate," + 
                           " ManufacturerName," + 
                           " ModelCode," + 
                           " BrandCName," + 
                           " BrandName," + 
                           " CarKindCode," + 
                           " CheckDate," + 
                           " EndValidDate," + 
                           " CarStatus," + 
                           " Haulage," + 
                           " StartDate," + 
                           " EndDate," + 
                           " Amount," + 
                           " Premium," + 
                           " BenchmarkPremium," + 
                           " PeccancyCoeff," + 
                           " ClaimCoeff," + 
                           " DriverCoeff," + 
                           " DistrictCoeff," + 
                           " CommissionRate," + 
                           " BasePremium," + 
                           " ComCode," + 
                           " OperatorCode," + 
                           " DemandTime," + 
                           " BillDate," + 
                           " ReinsureFlag," + 
                           " LastBillDate," + 
                           " RateFloatFlag," + 
                           " PeccancyAdjustReason," + 
                           " ClaimAdjustReason," + 
                           " DriverRateReason," + 
                           " DistrictRateReason," + 
                           " Remark," + 
                           " ProconfirmSequenceNo," +                  
                           " ProconfirmStartDate," +                   
                           " ProconfirmEndDate," +                     
                           
                           " RestricFlag," +
                           " PreferentialPremium," +
                           " PreferentialFormula," +
                           " PreferentialDay," +
                           
                           
                           " LastYearStartDate," +
                           " LastYearEndDate," +
                           
                           
                           " SearchSequenceNo," +
                           " SafeAdjust," +
                           " NonclaimAdjust," +
                           " LoyaltyAdjust," +
                           " UseTypeSource," +
                           
                           
                           " LoyaltyAdjustReason," +
                           " QueryPastDate," +
                           " TransferDate," +
                           
                           
                           " BusiLastYearEndDate," +
                           " BusiAdjustStart," +
                           " BusiAdjustEnd," +
                           " PoWeight," +
                           
						   
                           " PolicyCoeff,"+
                           
                           
                           " NoLoyaltyAdjustReason," +
                           " NoClaimAdjustReason," +
                           
                           
                           " sendLastPolicyNo," +
                           
                           
                           " IP," +
                           " UsbKey," +
                           
                           
                           " PeccancyStartDate," +
                           " PeccancyEndDate," +
                           " KindAdjustValue," +
                           " KindAdjustReason," +
                           " NoKindAdjustReason," +
                           " NoPeccancyReason," +
                           
                           
                           " LastProducerCode," +
                           
                           
                           " LastPolicyQueryDate," +
                           " LastPolicyTotalPremium," +
                           " InsureInDoorValue," +
                           " InsureInDoorReason," +
                           " ClaimAmountValue," +
                           " ClaimAmountReason," +
                           " NoClaimAmountReason," +
                           " SpecificRiskValue," +
                           " IndependentValue," +
                           
                           
                           " commissionrateupper," +
                           " companycommissionrateupper," +
                           " claimeffectreason," +
                           " newvehicleeffectreason," +
                           " producereffectreason," +
                           
                           
                           " FuelType," +
                           
                           
                           "TransferFlag," +
                           "HighRiskFlag," +
                           "EffectReason," +
                           "LastyearCompanyId," +
                           
                           
                           "LastModelCode," +
                           "LastModel," +
                           "LastPurchasePrice," +
                           
                           
                           " RepeatLastYearStartDate," +
                           " RepeatLastYearEndDate," +
                           " ReCoverMsg," +
                           
                           
                           " ChannelType," +
                           
                           
                           " UsageTypeMessage," +
                           " Wholeweight," +
                           " Displacement," +
                           " ChgOwnerDate," +
                           " ProducerType," +
                           " SalePrice," +
                           " PmFuelType," +
                           " VehicleCategory," +
                           " NoPeccancyAdjustReason," +
                           " NoDriverAdjustReason," +
                           " VehicleOwnerName," +
                           " FleetMessage," +
                           " MileageFactorMessage," +
                           
                           
                           " RepeatInsurerArea," +
                           
                           " Flag) values(" + 
                           "'" + getProposalNo() +"'" + "," +
                           "'" + getDemandNo() +"'" + "," +
                           "'" + getPolicyNo() +"'" + "," +
                           "'" + getLicenseNo() +"'" + "," +
                           "'" + getLicenseType() +"'" + "," +
                           "'" + getUseNatureCode() +"'" + "," +
                           "'" + getFrameNo() +"'" + "," +
                           "'" + getEngineNo() +"'" + "," +
                           "'" + getLicenseColorCode() +"'" + "," +
                           "'" + getCarOwner() +"'" + "," +
                           "to_date('" + getEnrollDate() +"','yyyy-mm-dd hh24:mi:ss')" + "," +
                           "to_date('" + getMakeDate() +"','yyyy-mm-dd hh24:mi:ss')" + "," +
                           "'" + getSeatCount() +"'" + "," +
                           "'" + getTonCount() +"'" + "," +
                           "to_date('" + getValidCheckDate() +"','yyyy-mm-dd hh24:mi:ss')" + "," +
                           "'" + getManufacturerName() +"'" + "," +
                           "'" + getModelCode() +"'" + "," +
                           "'" + getBrandCName() +"'" + "," +
                           "'" + getBrandName() +"'" + "," +
                           "'" + getCarKindCode() +"'" + "," +
                           "to_date('" + getCheckDate() +"','yyyy-mm-dd hh24:mi:ss')" + "," +
                           "to_date('" + getEndValidDate() +"','yyyy-mm-dd hh24:mi:ss')" + "," +
                           "'" + getCarStatus() +"'" + "," +
                           "'" + getHaulage() +"'" + "," +
                           "'" + getStartDate() +"'" + "," +
                           "'" + getEndDate() +"'" + "," +
                           "'" + getAmount() +"'" + "," +
                           "'" + getPremium() +"'" + "," +
                           "'" + getBenchmarkPremium() +"'" + "," +
                           "'" + getPeccancyCoeff() +"'" + "," +
                           "'" + getClaimCoeff() +"'" + "," +
                           "'" + getDriverCoeff() +"'" + "," +
                           "'" + getDistrictCoeff() +"'" + "," +
                           "'" + getCommissionRate() +"'" + "," +
                           "'" + getBasePremium() +"'" + "," +
                           "'" + getComCode() +"'" + "," +
                           "'" + getOperatorCode() +"'" + "," +
                           "to_date('" + getDemandTime() +"','yyyy-mm-dd hh24:mi:ss')" + "," +
                           "'" + getBillDate() +"'" + "," +
                           "'" + getReinsureFlag() +"'" + "," +
                           "'" + getLastBillDate() +"'" + "," +
                           "'" + getRateFloatFlag() +"'" + "," +
                           "'" + getPeccancyAdjustReason() +"'" + "," +
                           "'" + getClaimAdjustReason() +"'" + "," +
                           "'" + getDriverRateReason() +"'" + "," +
                           "'" + getDistrictRateReason() +"'" + "," +
                           "'" + getRemark() +"'" + "," +
                           "'" + getProconfirmSequenceNo() +"'" + "," +                   
                           "to_date('" + getProconfirmStartDate() + "','yyyy-mm-dd hh24:mi:ss')" + "," +                    
                           
                           "to_date('" + getProconfirmEndDate() + "','yyyy-mm-dd hh24:mi:ss')" + "," + 
                           
                           "'" + getRestricFlag() + "'" + "," +
                           "'" + getPreferentialPremium() + "'" + "," +
                           "'" + getPreferentialFormula() + "'" + "," +
                           "'" + getPreferentialDay() + "'" + "," +
                           
                           
                           "to_date('" + getLastYearStartDate() + "','yyyy-mm-dd hh24:mi:ss')" + "," +
                           
                           "to_date('" + getLastYearEndDate() + "','yyyy-mm-dd hh24:mi:ss')" + ","+
                           
                           
                           
                           "'" + getSearchSequenceNo() + "'," +
                           "'" + getSafeAdjust() + "'," +
                           "'" + getNonclaimAdjust() + "'," +
                           "'" + getLoyaltyAdjust() + "'," +
                           "'" + getUseTypeSource() + "'," +
                           
                           
                           "'" + getLoyaltyAdjustReason() + "'," +
                           "to_date('" + getQueryPastDate() + "','yyyy-mm-dd')" + "," +
                           "to_date('" + getTransferDate() + "','yyyy-mm-dd')" + "," + 
                           
                           
                           "to_date('" + getBusiLastYearEndDate() + "','yyyy-mm-dd')" + "," +
                           
                           "to_date('" + getBusiAdjustStart() + "','yyyy-mm-dd hh24:mi:ss')" + "," +
                           "to_date('" + getBusiAdjustEnd() + "','yyyy-mm-dd hh24:mi:ss')" + "," +
                           
                           "'" + getPoWeight() + "'," +
                           
						   
                           "'" + getPolicyCoeff() + "'," +
                           
                           
                           "'" + getNoLoyaltyAdjustReason() + "'," +
                           "'" + getNoClaimAdjustReason() + "'," +
                           
                           
                           "'" + getSendLastPolicyNo() + "'," +
                           
                           
                           "'" + getIP() + "'," +
                           "'" + getUsbKey() + "'," +
                           
                           
                           "to_date('" + getPeccancyStartDate() + "','yyyy-mm-dd hh24:mi:ss')" + "," +
                           "to_date('" + getPeccancyEndDate() + "','yyyy-mm-dd hh24:mi:ss')" + "," +
                           "'" + getKindAdjustValue() + "'," +
                           "'" + getKindAdjustReason() + "'," +
                           "'" + getNoKindAdjustReason() + "'," +
                           "'" + getNoPeccancyReason() + "'," +
                           
                           
                           "'" + getLastProducerCode() + "'," + 
                           
                           
                           "to_date('" + getLastPolicyQueryDate() + "','yyyy-mm-dd hh24:mi:ss')" + "," +
                           "'" + getLastPolicyTotalPremium() + "'," +
                           "'" + getInsureInDoorValue() + "'," +
                           "'" + getInsureInDoorReason() + "'," +
                           "'" + getClaimAmountValue() + "'," +
                           "'" + getClaimAmountReason() + "'," +
                           "'" + getNoClaimAmountReason() + "'," +
                           "'" + getSpecificRiskValue() + "'," +
                           "'" + getIndependentValue() + "'," +
                           
                           
                           "'" + getCommissionrateupper() + "'," +
                           "'" + getCompanycommissionrateupper() + "'," +
                           "'" + getClaimeffectreason() + "'," +
                           "'" + getNewvehicleeffectreason() + "'," +
                           "'" + getProducereffectreason() + "'," +
                           
                           
                           "'" + getFuelType() + "'," +
                           
                           
                           "'" + getTransferFlag() + "'," +
                           "'" + getHighRiskFlag() + "'," +
                           "'" + getEffectReason() + "'," +
                           "'" + getLastyearCompanyId() + "'," +
                           
                           
                           "'" + getLastModelCode() + "'," +
                           "'" + getLastModel() + "'," +
                           "'" + getLastPurchasePrice() + "'," +
                           
                           
                           "to_date('" + getRepeatLastYearStartDate() + "','yyyy-mm-dd hh24:mi:ss')," +
                           "to_date('" + getRepeatLastYearEndDate() + "','yyyy-mm-dd hh24:mi:ss')," +
                           
                           "'" + (getReCoverMsg()==null||getReCoverMsg().length()>1000?"":getReCoverMsg())+ "'," +
                           
                           
                           
                           "'" + getChannelType() + "'," +
                           
                           
                           "'" + getUsageTypeMessage() + "'," +
                           "'" + getWholeweight() + "'," +
                           "'" + getDisplacement() + "'," +
                           "to_date('" + getChgOwnerDate() + "','yyyyMMdd')," +
                           "'" + getProducerType() + "'," +
                           "'" + getSalePrice() + "'," +
                           "'" + getPmFuelType() + "'," +
                           "'" + getVehicleCategory() + "'," +
                           "'" + getNoPeccancyAdjustReason() + "'," +
                           "'" + getNoDriverAdjustReason() + "'," +
                           "'" + getVehicleOwnerName() + "'," +
                           "'" + getFleetMessage() + "'," +
                           "'" + getMileageFactorMessage() + "'," +
                           
                           
                           "'" + getRepeatInsurerArea() + "'," +
                           
                           "'" + getFlag() +"'" +

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

    public void delete(DbPool dbpool,String demandNo) throws Exception{
        String strSQL = " Delete From CIInsureDemand Where DemandNo = " + "'" + demandNo + "'";
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
        String strSQL = " Update CIInsureDemand Set" +
                           " ProposalNo = " + "'" +getProposalNo() + "'" + "," +
                           " DemandNo = " + "'" +getDemandNo() + "'" + "," +
                           " PolicyNo = " + "'" +getPolicyNo() + "'" + "," +
                           " LicenseNo = " + "'" +getLicenseNo() + "'" + "," +
                           " LicenseType = " + "'" +getLicenseType() + "'" + "," +
                           " UseNatureCode = " + "'" +getUseNatureCode() + "'" + "," +
                           " FrameNo = " + "'" +getFrameNo() + "'" + "," +
                           " EngineNo = " + "'" +getEngineNo() + "'" + "," +
                           " LicenseColorCode = " + "'" +getLicenseColorCode() + "'" + "," +
                           " CarOwner = " + "'" +getCarOwner() + "'" + "," +
                           " EnrollDate = " + "to_date('" +getEnrollDate() + "','yyyy-mm-dd')" + "," +   
							" MakeDate = " + "to_date('" +getMakeDate() + "','yyyy-mm-dd')" + "," +
                           " SeatCount = " + "'" +getSeatCount() + "'" + "," +
                           " TonCount = " + "'" +getTonCount() + "'" + "," +
                           " ValidCheckDate = " + "'" +getValidCheckDate() + "'" + "," +
                           " ManufacturerName = " + "'" +getManufacturerName() + "'" + "," +
                           " ModelCode = " + "'" +getModelCode() + "'" + "," +
                           " BrandCName = " + "'" +getBrandCName() + "'" + "," +
                           " BrandName = " + "'" +getBrandName() + "'" + "," +
                           " CarKindCode = " + "'" +getCarKindCode() + "'" + "," +
                           " CheckDate = " + "to_date('" +getCheckDate() + "','yyyy-mm-dd')" + "," +
                           " EndValidDate = " + "to_date('" +getEndValidDate() + "','yyyy-mm-dd')" + "," +
                           " CarStatus = " + "'" +getCarStatus() + "'" + "," +
                           " Haulage = " + "'" +getHaulage() + "'" + "," +
                           " StartDate = " + "'" +getStartDate() + "'" + "," +
                           " EndDate = " + "'" +getEndDate() + "'" + "," +
                           " Amount = " + "'" +getAmount() + "'" + "," +
                           " Premium = " + "'" +getPremium() + "'" + "," +
                           " BenchmarkPremium = " + "'" +getBenchmarkPremium() + "'" + "," +
                           " PeccancyCoeff = " + "'" +getPeccancyCoeff() + "'" + "," +
                           " ClaimCoeff = " + "'" +getClaimCoeff() + "'" + "," +
                           " DriverCoeff = " + "'" +getDriverCoeff() + "'" + "," +
                           " DistrictCoeff = " + "'" +getDistrictCoeff() + "'" + "," +
                           " CommissionRate = " + "'" +getCommissionRate() + "'" + "," +
                           " BasePremium = " + "'" +getBasePremium() + "'" + "," +
                           " ComCode = " + "'" +getComCode() + "'" + "," +
                           " OperatorCode = " + "'" +getOperatorCode() + "'" + "," +
                           " DemandTime = " + "to_date('" +getDemandTime() + "','yyyy-mm-dd')" + "," +
                           " BillDate = " + "'" +getBillDate() + "'" + "," +
                           " ReinsureFlag = " + "'" +getReinsureFlag() + "'" + "," +
                           " LastBillDate = " + "'" +getLastBillDate() + "'" + "," +
                           " RateFloatFlag = " + "'" +getRateFloatFlag() + "'" + "," +
                           " PeccancyAdjustReason = " + "'" +getPeccancyAdjustReason() + "'" + "," +
                           " ClaimAdjustReason = " + "'" +getClaimAdjustReason() + "'" + "," +
                           " DriverRateReason = " + "'" +getDriverRateReason() + "'" + "," +
                           " DistrictRateReason = " + "'" +getDistrictRateReason() + "'" + "," +
                           " Remark = " + "'" +getRemark() + "'" + "," +
                           " ProconfirmSequenceNo = " + "'" +getProconfirmSequenceNo() + "'" + "," +     
                           " ProconfirmStartDate = to_date('" + getProconfirmStartDate() + "','yyyy-mm-dd hh24:mi:ss')" + "," +
                           
                           " ProconfirmEndDate = to_date('" + getProconfirmEndDate() + "','yyyy-mm-dd hh24:mi:ss')" + "," +
                           
                           
                           " RestricFlag = " + "'" +getRestricFlag() + "'" + "," +
                           " PreferentialPremium = " + "'" +getPreferentialPremium() + "'" + "," +
                           " PreferentialFormula = " + "'" +getPreferentialFormula() + "'" + "," +
                           " PreferentialDay = " + "'" +getPreferentialDay() + "'" + "," +
                           
                           
                           " LastYearStartDate = " + "to_date('" +getLastYearStartDate() + "','yyyy-mm-dd')" + "," +
                           " LastYearEndDate = " + "to_date('" +getLastYearEndDate() + "','yyyy-mm-dd')" + "," +
                           
                           
                           " SearchSequenceNo = '" +getSearchSequenceNo() + "'," +
                           " SafeAdjust = '" +getSafeAdjust() + "'," +
                           " NonclaimAdjust = '" +getNonclaimAdjust() + "'," +
                           " LoyaltyAdjust = '" +getLoyaltyAdjust() + "'," +
                           " UseTypeSource = '" +getUseTypeSource() + "'," +
                           
                           
                           " LoyaltyAdjustReason = '" +getLoyaltyAdjustReason() + "'," +
                           " QueryPastDate=to_date('" + getQueryPastDate() + "','yyyy-mm-dd')" + "," +
                           " TransferDate=to_date('" + getTransferDate() + "','yyyy-mm-dd')" + "," + 
                           
                           
                           " BusiLastYearEndDate=to_date('" + getBusiLastYearEndDate() + "','yyyy-mm-dd')" + "," + 
                           
                           " BusiAdjustStart=to_date('" + getBusiAdjustStart() + "','yyyy-mm-dd hh24:mi:ss')" + "," + 
                           " BusiAdjustEnd=to_date('" + getBusiAdjustEnd() + "','yyyy-mm-dd hh24:mi:ss')" + "," +
                           
                           " PoWeight = '" +getPoWeight() + "'," +
                           
						   
                           " PolicyCoeff = '" +getPolicyCoeff() + "'," +
                           
                           
                           " NoLoyaltyAdjustReason = '" +getNoLoyaltyAdjustReason() + "'," +
                           " NoClaimAdjustReason = '" +getNoClaimAdjustReason() + "'," +
                           
                           
                           " sendLastPolicyNo = '" +getSendLastPolicyNo() + "'," +
                           
                           
                           " IP = '" +getIP() + "'," +
                           " UsbKey = '" +getUsbKey() + "'," +
                           
                           
                           " PeccancyStartDate =to_date('" +getPeccancyStartDate()+ "','yyyy-mm-dd hh24:mi:ss')" + "," + 
                           " PeccancyEndDate = to_date('" +getPeccancyEndDate() + "','yyyy-mm-dd hh24:mi:ss')" + "," + 
                           " KindAdjustValue = '" +getKindAdjustValue() + "'," +
                           " KindAdjustReason = '" +getKindAdjustReason() + "'," +
                           " NoKindAdjustReason = '" +getNoKindAdjustReason() + "'," +
                           " NoPeccancyReason = '" +getNoPeccancyReason() + "'," +
                           
                           
                           " LastProducerCode = '" +getLastProducerCode() + "'," + 
                           
                           
                           " LastPolicyQueryDate =to_date('" +getLastPolicyQueryDate()+ "','yyyy-mm-dd hh24:mi:ss')" + "," + 
                           " LastPolicyTotalPremium = '" +getLastPolicyTotalPremium() + "'," +
                           " InsureInDoorValue = '" +getInsureInDoorValue() + "'," +
                           " InsureInDoorReason = '" +getInsureInDoorReason() + "'," +
                           " ClaimAmountValue = '" +getClaimAmountValue() + "'," +
                           " ClaimAmountReason = '" +getClaimAmountReason() + "'," +
                           " NoClaimAmountReason = '" +getNoClaimAmountReason() + "'," +
                           " SpecificRiskValue = '" +getSpecificRiskValue() + "'," +
                           " IndependentValue = '" +getIndependentValue() + "'," +
                           
                           
                           " commissionrateupper = '" +getCommissionrateupper() + "'," +
                           " companycommissionrateupper = '" +getCompanycommissionrateupper() + "'," +
                           " claimeffectreason = '" +getClaimeffectreason() + "'," +
                           " newvehicleeffectreason = '" +getNewvehicleeffectreason() + "'," +
                           " producereffectreason = '" +getProducereffectreason() + "'," +
                           
                           
                           " FuelType = '" +getFuelType() + "'," +
                           
                           
                           " TransferFlag = '" +getTransferFlag() + "'," +
                           " HighRiskFlag = '" +getHighRiskFlag() + "'," +
                           " EffectReason = '" +getEffectReason() + "'," +
                           " LastyearCompanyId = '" +getLastyearCompanyId() + "'," +
                           
                           
                           " LastModelCode = '" +getLastModelCode() + "'," +
                           " LastModel = '" +getLastModel() + "'," +
                           " LastPurchasePrice = '" +getLastPurchasePrice() + "'," +
                           
                           
                           " RepeatLastYearStartDate = '" + getRepeatLastYearStartDate() + "'," +
                           " RepeatLastYearEndDate = '" + getRepeatLastYearEndDate() + "'," +
                           " ReCoverMsg = '" + getReCoverMsg() + "'," +
                           
                           
                           " ChannelType = '" + getChannelType() + "'," +
                           
                           
                           " UsageTypeMessage = '" + getUsageTypeMessage() + "'," +
                           " Wholeweight = '" + getWholeweight() + "'," +
                           " Displacement = '" + getDisplacement() + "'," +
                           " ChgOwnerDate = to_date('" + getChgOwnerDate() + "','yyyyMMdd')," +
                           " ProducerType = '" + getProducerType() + "'," +
                           " SalePrice = '" + getSalePrice() + "'," +
                           " PmFuelType = '" + getPmFuelType() + "'," +
                           " VehicleCategory = '" + getVehicleCategory() + "'," +
                           " NoPeccancyAdjustReason = '" + getNoPeccancyAdjustReason() + "'," +
                           " NoDriverAdjustReason = '" + getNoDriverAdjustReason() + "'," +
                           " VehicleOwnerName = '" + getVehicleOwnerName() + "'," +
                           " FleetMessage = '" + getFleetMessage() + "'," +
                           " MileageFactorMessage = '" + getMileageFactorMessage() + "'," +
                           
                           
                           " RepeatInsurerArea = '" + getRepeatInsurerArea() + "'," +
                           
                           " Flag = " + "'" +getFlag() + "'" +
                           " Where DemandNo = " + "'" +getDemandNo() + "'";
        dbpool.update(strSQL);
    }
    
    public void update(DbPool dbpool, String strWhere) throws Exception
    {
    	String strSQL = " Update CIInsureDemand Set" +
        				
        				" DemandNo = " + "'" +getDemandNo() + "'" + "," +
        				" PolicyNo = " + "'" +getPolicyNo() + "'" + "," +
        				" LicenseNo = " + "'" +getLicenseNo() + "'" + "," +
        				" LicenseType = " + "'" +getLicenseType() + "'" + "," +
        				" UseNatureCode = " + "'" +getUseNatureCode() + "'" + "," +
        				" FrameNo = " + "'" +getFrameNo() + "'" + "," +
        				" EngineNo = " + "'" +getEngineNo() + "'" + "," +
        				" LicenseColorCode = " + "'" +getLicenseColorCode() + "'" + "," +
        				" CarOwner = " + "'" +getCarOwner() + "'" + "," +
        				" EnrollDate = " + "'" +getEnrollDate() + "'" + "," +
        				" MakeDate = " + "'" +getMakeDate() + "'" + "," +
        				" SeatCount = " + "'" +getSeatCount() + "'" + "," +
        				" TonCount = " + "'" +getTonCount() + "'" + "," +
        				" ValidCheckDate = " + "'" +getValidCheckDate() + "'" + "," +
        				" ManufacturerName = " + "'" +getManufacturerName() + "'" + "," +
        				" ModelCode = " + "'" +getModelCode() + "'" + "," +
        				" BrandCName = " + "'" +getBrandCName() + "'" + "," +
        				" BrandName = " + "'" +getBrandName() + "'" + "," +
        				" CarKindCode = " + "'" +getCarKindCode() + "'" + "," +
        				" CheckDate = " + "'" +getCheckDate() + "'" + "," +
        				" EndValidDate = " + "'" +getEndValidDate() + "'" + "," +
        				" CarStatus = " + "'" +getCarStatus() + "'" + "," +
        				" Haulage = " + "'" +getHaulage() + "'" + "," +
        				" StartDate = " + "'" +getStartDate() + "'" + "," +
        				" EndDate = " + "'" +getEndDate() + "'" + "," +
        				" Amount = " + "'" +getAmount() + "'" + "," +
        				" Premium = " + "'" +getPremium() + "'" + "," +
        				" BenchmarkPremium = " + "'" +getBenchmarkPremium() + "'" + "," +
        				" PeccancyCoeff = " + "'" +getPeccancyCoeff() + "'" + "," +
        				" ClaimCoeff = " + "'" +getClaimCoeff() + "'" + "," +
        				" DriverCoeff = " + "'" +getDriverCoeff() + "'" + "," +
        				" DistrictCoeff = " + "'" +getDistrictCoeff() + "'" + "," +
        				" CommissionRate = " + "'" +getCommissionRate() + "'" + "," +
        				" BasePremium = " + "'" +getBasePremium() + "'" + "," +
        				" ComCode = " + "'" +getComCode() + "'" + "," +
        				" OperatorCode = " + "'" +getOperatorCode() + "'" + "," +
        				" DemandTime = " + "'" +getDemandTime() + "'" + "," +
        				" BillDate = " + "'" +getBillDate() + "'" + "," +
                        " ReinsureFlag = " + "'" +getReinsureFlag() + "'" + "," +
                        " LastBillDate = " + "'" +getLastBillDate() + "'" + "," +
                        " RateFloatFlag = " + "'" +getRateFloatFlag() + "'" + "," +
                        " PeccancyAdjustReason = " + "'" +getPeccancyAdjustReason() + "'" + "," +
                        " ClaimAdjustReason = " + "'" +getClaimAdjustReason() + "'" + "," +
                        " DriverRateReason = " + "'" +getDriverRateReason() + "'" + "," +
                        " DistrictRateReason = " + "'" +getDistrictRateReason() + "'" + "," +
        				" Remark = " + "'" +getRemark() + "'" + "," +
                        " ProconfirmSequenceNo = " + "'" +getProconfirmSequenceNo() + "'" + "," +     
                        " ProconfirmStartDate = to_date('" + getProconfirmStartDate() + "','yyyy-mm-dd hh24:mi:ss')" + "," +
                        
        				" ProconfirmEndDate = to_date('" + getProconfirmEndDate() + "','yyyy-mm-dd hh24:mi:ss')" + "," +
        				
                        
                        " RestricFlag = " + "'" +getRestricFlag() + "'" + "," +
                        " PreferentialPremium = " + "'" +getPreferentialPremium() + "'" + "," +
                        " PreferentialFormula = " + "'" +getPreferentialFormula() + "'" + "," +
                        " PreferentialDay = " + "'" +getPreferentialDay() + "'" + "," +
                        
                        
        				" LastYearStartDate = " + "'" +getLastYearStartDate() + "'" + "," +
                        " LastYearEndDate = " + "'" +getLastYearEndDate() + "'" + "," +
                        
                        
                        " SearchSequenceNo = '" +getSearchSequenceNo() + "'," +
                        " SafeAdjust = '" +getSafeAdjust() + "'," +
                        " NonclaimAdjust = '" +getNonclaimAdjust() + "'," +
                        " LoyaltyAdjust = '" +getLoyaltyAdjust() + "'," +
                        " UseTypeSource = '" +getUseTypeSource() + "'," +
                        
                        
                        " LoyaltyAdjustReason = '" +getLoyaltyAdjustReason() + "'," +
                        " QueryPastDate=to_date('" + getQueryPastDate() + "','yyyy-mm-dd')" + "," +
                        " TransferDate=to_date('" + getTransferDate() + "','yyyy-mm-dd')" + "," + 
                        
                        
                        " BusiLastYearEndDate=to_date('" + getBusiLastYearEndDate() + "','yyyy-mm-dd')" + "," +
                        
                        " BusiAdjustStart=to_date('" + getBusiAdjustStart() + "','yyyy-mm-dd hh24:mi:ss')" + "," +
                        " BusiAdjustEnd=to_date('" + getBusiAdjustEnd() + "','yyyy-mm-dd hh24:mi:ss')" + "," +
                        
                        " PoWeight = '" +getPoWeight() + "'," +
                        
						
                        " PolicyCoeff = '" +getPolicyCoeff() + "'," +
                        
                        
                        " NoLoyaltyAdjustReason = '" +getNoLoyaltyAdjustReason() + "'," +
                        " NoClaimAdjustReason = '" +getNoClaimAdjustReason() + "'," +
                        
                        
                        " sendLastPolicyNo = '" +getSendLastPolicyNo() + "'," +
                        
                        
                        " IP = '" +getIP() + "'," +
                        " UsbKey = '" +getUsbKey() + "'," +
                        
                        
                        " PeccancyStartDate = to_date('" +getPeccancyStartDate() + "','yyyy-mm-dd hh24:mi:ss')" + "," +
                        " PeccancyEndDate = to_date('" +getPeccancyEndDate() +"','yyyy-mm-dd hh24:mi:ss')" + "," +
                        " KindAdjustValue = '" +getKindAdjustValue() + "'," +
                        " KindAdjustReason = '" +getKindAdjustReason() + "'," +
                        " NoKindAdjustReason = '" +getNoKindAdjustReason() + "'," +
                        " NoPeccancyReason = '" +getNoPeccancyReason() + "'," + 
                        
                        
                        " LastProducerCode = '" +getLastProducerCode() + "'," + 
                        
                        
                        " LastPolicyQueryDate =to_date('" +getLastPolicyQueryDate()+ "','yyyy-mm-dd hh24:mi:ss')" + "," + 
                        " LastPolicyTotalPremium = '" +getLastPolicyTotalPremium() + "'," +
                        " InsureInDoorValue = '" +getInsureInDoorValue() + "'," +
                        " InsureInDoorReason = '" +getInsureInDoorReason() + "'," +
                        " ClaimAmountValue = '" +getClaimAmountValue() + "'," +
                        " ClaimAmountReason = '" +getClaimAmountReason() + "'," +
                        " NoClaimAmountReason = '" +getNoClaimAmountReason() + "'," +
                        " SpecificRiskValue = '" +getSpecificRiskValue() + "'," +
                        " IndependentValue = '" +getIndependentValue() + "'," +
                        
                        
                        " commissionrateupper = '" +getCommissionrateupper() + "'," +
                        " companycommissionrateupper = '" +getCompanycommissionrateupper() + "'," +
                        " claimeffectreason = '" +getClaimeffectreason() + "'," +
                        " newvehicleeffectreason = '" +getNewvehicleeffectreason() + "'," +
                        " producereffectreason = '" +getProducereffectreason() + "'," +
                        
                		
                        " FuelType = '" +getFuelType() + "'," +
                		
                        
                        " TransferFlag = '" +getTransferFlag() + "'," +
                        " HighRiskFlag = '" +getHighRiskFlag() + "'," +
                        " EffectReason = '" +getEffectReason() + "'," +
                        " LastyearCompanyId = '" +getLastyearCompanyId() + "'," +
                        
                        
                        " LastModelCode = '" +getLastModelCode() + "'," +
                        " LastModel = '" +getLastModel() + "'," +
                        " LastPurchasePrice = '" +getLastPurchasePrice() + "'," +
                        
                        
                        " RepeatLastYearStartDate = '" + getRepeatLastYearStartDate() + "'," +
                        " RepeatLastYearEndDate = '" + getRepeatLastYearEndDate() + "'," +
                        " ReCoverMsg = '" + getReCoverMsg() + "'," +
                        
                        
                        " ChannelType = '" + getChannelType() + "'," +
                        
                        
                        " UsageTypeMessage = '" + getUsageTypeMessage() + "'," +
                        " Wholeweight = '" + getWholeweight() + "'," +
                        " Displacement = '" + getDisplacement() + "'," +
                        " ChgOwnerDate = to_date('" + getChgOwnerDate() + "','yyyyMMdd')," +
                        " ProducerType = '" + getProducerType() + "'," +
                        " SalePrice = '" + getSalePrice() + "'," +
                        " PmFuelType = '" + getPmFuelType() + "'," +
                        " VehicleCategory = '" + getVehicleCategory() + "'," +
                        " NoPeccancyAdjustReason = '" + getNoPeccancyAdjustReason() + "'," +
                        " NoDriverAdjustReason = '" + getNoDriverAdjustReason() + "'," +
                        " VehicleOwnerName = '" + getVehicleOwnerName() + "'," +
                        " FleetMessage = '" + getFleetMessage() + "'," +
                        " MileageFactorMessage = '" + getMileageFactorMessage() + "'," +
                        
                        
                        " RepeatInsurerArea = '" + getRepeatInsurerArea() + "'," +
                        
        				" Flag = " + "'" +getFlag() + "'" +
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

    public int getInfo(DbPool dbpool,String demandNo) throws Exception{
        int intResult = 0;
        String strSQL = " Select * From CIInsureDemand Where DemandNo = " + "'" + demandNo + "'";
        ResultSet resultSet = dbpool.query(strSQL);
        if(resultSet.next()){
            setProposalNo(resultSet.getString("proposalNo"));
            setDemandNo(resultSet.getString("demandNo"));
            setPolicyNo(resultSet.getString("policyNo"));
            setLicenseNo(resultSet.getString("licenseNo"));
            setLicenseType(resultSet.getString("licenseType"));
            setUseNatureCode(resultSet.getString("useNatureCode"));
            setFrameNo(resultSet.getString("frameNo"));
            setEngineNo(resultSet.getString("engineNo"));
            setLicenseColorCode(resultSet.getString("licenseColorCode"));
            setCarOwner(resultSet.getString("carOwner"));
            setEnrollDate("" + resultSet.getDate("enrollDate"));
            setMakeDate("" + resultSet.getDate("makeDate"));
            setSeatCount(resultSet.getString("seatCount"));
            setTonCount(resultSet.getString("tonCount"));
            setValidCheckDate("" + resultSet.getDate("validCheckDate"));
            setManufacturerName(resultSet.getString("manufacturerName"));
            setModelCode(resultSet.getString("modelCode"));
            setBrandCName(resultSet.getString("brandCName"));
            setBrandName(resultSet.getString("brandName"));
            setCarKindCode(resultSet.getString("carKindCode"));
            setCheckDate("" + resultSet.getDate("checkDate"));
            setEndValidDate("" + resultSet.getDate("endValidDate"));
            setCarStatus(resultSet.getString("carStatus"));
            setHaulage(resultSet.getString("haulage"));
            setStartDate("" + resultSet.getDate("startDate"));
            setEndDate("" + resultSet.getDate("endDate"));
            setAmount(resultSet.getString("amount"));
            setPremium(resultSet.getString("premium"));
            setBenchmarkPremium(resultSet.getString("benchmarkPremium"));
            setPeccancyCoeff(resultSet.getString("peccancyCoeff"));
            setClaimCoeff(resultSet.getString("claimCoeff"));
            setDriverCoeff(resultSet.getString("driverCoeff"));
            setDistrictCoeff(resultSet.getString("districtCoeff"));
            setCommissionRate(resultSet.getString("commissionRate"));
            setBasePremium(resultSet.getString("basePremium"));
            setComCode(resultSet.getString("comCode"));
            setOperatorCode(resultSet.getString("operatorCode"));
            setDemandTime("" + resultSet.getDate("demandTime"));
            setBillDate("" + resultSet.getDate("BillDate"));
            setReinsureFlag(resultSet.getString("ReinsureFlag"));
            setLastBillDate("" + resultSet.getDate("LastBillDate"));
            setRateFloatFlag(resultSet.getString("RateFloatFlag"));
            setPeccancyAdjustReason(resultSet.getString("PeccancyAdjustReason"));
            setClaimAdjustReason(resultSet.getString("ClaimAdjustReason"));
            setDriverRateReason(resultSet.getString("DriverRateReason"));
            setDistrictRateReason(resultSet.getString("DistrictRateReason"));
            setRemark(resultSet.getString("remark"));
            setProconfirmSequenceNo(resultSet.getString("ProconfirmSequenceNo"));         
            setProconfirmStartDate("" + resultSet.getTimestamp("ProconfirmStartDate"));           
            setProconfirmEndDate("" + resultSet.getTimestamp("ProconfirmEndDate"));               
            
            setRestricFlag(resultSet.getString("RestricFlag"));
            setPreferentialPremium(resultSet.getString("PreferentialPremium"));
            setPreferentialFormula(resultSet.getString("PreferentialFormula"));
            setPreferentialDay(resultSet.getString("PreferentialDay"));
            
            
            
            
            
            setLastYearStartDate(""+resultSet.getDate("LastYearStartDate"));
            setLastYearEndDate(""+resultSet.getDate("LastYearEndDate"));
            
            
            
            setSearchSequenceNo(resultSet.getString("SearchSequenceNo"));
            setSafeAdjust(resultSet.getString("SafeAdjust"));
            setNonclaimAdjust(resultSet.getString("NonclaimAdjust"));
            setLoyaltyAdjust(resultSet.getString("LoyaltyAdjust"));
            setUseTypeSource(resultSet.getString("UseTypeSource"));
            
            
            setLoyaltyAdjustReason(resultSet.getString("LoyaltyAdjustReason"));
            setQueryPastDate(resultSet.getString("QueryPastDate"));
            setTransferDate(resultSet.getString("TransferDate"));
            
            
            
            
            setBusiLastYearEndDate(""+resultSet.getDate("BusiLastYearEndDate"));
            
            setBusiAdjustStart(resultSet.getString("BusiAdjustStart"));
            setBusiAdjustEnd(resultSet.getString("BusiAdjustEnd"));
            setPoWeight(resultSet.getString("PoWeight"));
            
			
            setPolicyCoeff(resultSet.getString("PolicyCoeff"));
            
            
            setNoLoyaltyAdjustReason(resultSet.getString("NoLoyaltyAdjustReason"));
            setNoClaimAdjustReason(resultSet.getString("NoClaimAdjustReason"));
            
            
            setSendLastPolicyNo(resultSet.getString("sendLastPolicyNo"));
            
            
            setIP(resultSet.getString("IP"));
            setUsbKey(resultSet.getString("UsbKey"));
            
            
            setPeccancyStartDate(resultSet.getString("PeccancyStartDate"));
            setPeccancyEndDate(resultSet.getString("PeccancyEndDate"));
            setKindAdjustValue(resultSet.getString("KindAdjustValue"));
            setKindAdjustReason(resultSet.getString("KindAdjustReason"));
            setNoKindAdjustReason(resultSet.getString("NoKindAdjustReason"));
            setNoPeccancyReason(resultSet.getString("NoPeccancyReason"));
            
            
            setLastProducerCode(resultSet.getString("LastProducerCode"));
            
            
            setLastPolicyQueryDate(""+resultSet.getDate("LastPolicyQueryDate"));
            setLastPolicyTotalPremium(resultSet.getString("LastPolicyTotalPremium"));
            setInsureInDoorValue(resultSet.getString("InsureInDoorValue"));
            setInsureInDoorReason(resultSet.getString("InsureInDoorReason"));
            setClaimAmountValue(resultSet.getString("ClaimAmountValue"));
            setClaimAmountReason(resultSet.getString("ClaimAmountReason"));
            setNoClaimAmountReason(resultSet.getString("NoClaimAmountReason"));
            setSpecificRiskValue(resultSet.getString("SpecificRiskValue"));
            setIndependentValue(resultSet.getString("IndependentValue"));
            
            
            setCommissionrateupper(resultSet.getString("commissionrateupper"));
            setCompanycommissionrateupper(resultSet.getString("companycommissionrateupper"));
            setClaimeffectreason(resultSet.getString("claimeffectreason"));
            setNewvehicleeffectreason(resultSet.getString("newvehicleeffectreason"));
            setProducereffectreason(resultSet.getString("producereffectreason"));
            
    		
            setFuelType(resultSet.getString("FuelType"));
    		
            
            setTransferFlag(resultSet.getString("TransferFlag"));
            setHighRiskFlag(resultSet.getString("HighRiskFlag"));
            setEffectReason(resultSet.getString("EffectReason"));
            setLastyearCompanyId(resultSet.getString("LastyearCompanyId"));
            
            
            setLastModelCode(resultSet.getString("LastModelCode"));
            setLastModel(resultSet.getString("LastModel"));
            setLastPurchasePrice(resultSet.getString("LastPurchasePrice"));
            
            
            setRepeatLastYearStartDate(resultSet.getTimestamp("RepeatLastYearStartDate") + "");
            setRepeatLastYearEndDate(resultSet.getTimestamp("RepeatLastYearEndDate") + "");
            setReCoverMsg(resultSet.getString("ReCoverMsg"));
            
            
             setChannelType(resultSet.getString("ChannelType"));
            
            
            setUsageTypeMessage(resultSet.getString("UsageTypeMessage"));
            setWholeweight(resultSet.getString("Wholeweight"));
            setDisplacement(resultSet.getString("Displacement"));
            setChgOwnerDate(resultSet.getTimestamp("ChgOwnerDate") + "");
            setProducerType(resultSet.getString("ProducerType"));
            setSalePrice(resultSet.getString("SalePrice"));
            setPmFuelType(resultSet.getString("PmFuelType"));
            setVehicleCategory(resultSet.getString("VehicleCategory"));
            setNoPeccancyAdjustReason(resultSet.getString("NoPeccancyAdjustReason"));
            setNoDriverAdjustReason(resultSet.getString("NoDriverAdjustReason"));
            setVehicleOwnerName(resultSet.getString("VehicleOwnerName"));
            setFleetMessage(resultSet.getString("FleetMessage"));
            setMileageFactorMessage(resultSet.getString("MileageFactorMessage"));
            
            
            setRepeatInsurerArea(resultSet.getString("RepeatInsurerArea"));
            
            setFlag(resultSet.getString("flag"));
            intResult = 0;
        }
        else{
            intResult = 100;
        }
        resultSet.close();
        return intResult;
    }

    public int getInfo(String demandNo) throws Exception{
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
            
            intResult=getInfo(dbpool,demandNo);
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
        String strSQL = " SELECT COUNT(*) FROM CIInsureDemand WHERE "+ strWhere;
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
        CIInsureDemandSchema cIInsureDemandSchema = null;
        ResultSet resultSet = dbpool.query(strSQL);
        while(resultSet.next())
        {
            cIInsureDemandSchema = new CIInsureDemandSchema();
            cIInsureDemandSchema.setProposalNo(resultSet.getString("proposalNo"));
            cIInsureDemandSchema.setDemandNo(resultSet.getString("demandNo"));
            cIInsureDemandSchema.setPolicyNo(resultSet.getString("policyNo"));
            cIInsureDemandSchema.setLicenseNo(resultSet.getString("licenseNo"));
            cIInsureDemandSchema.setLicenseType(resultSet.getString("licenseType"));
            cIInsureDemandSchema.setUseNatureCode(resultSet.getString("useNatureCode"));
            cIInsureDemandSchema.setFrameNo(resultSet.getString("frameNo"));
            cIInsureDemandSchema.setEngineNo(resultSet.getString("engineNo"));
            cIInsureDemandSchema.setLicenseColorCode(resultSet.getString("licenseColorCode"));
            cIInsureDemandSchema.setCarOwner(resultSet.getString("carOwner"));
            cIInsureDemandSchema.setEnrollDate("" + resultSet.getDate("enrollDate"));
            cIInsureDemandSchema.setMakeDate("" + resultSet.getDate("makeDate"));
            cIInsureDemandSchema.setSeatCount(resultSet.getString("seatCount"));
            cIInsureDemandSchema.setTonCount(resultSet.getString("tonCount"));
            cIInsureDemandSchema.setValidCheckDate("" + resultSet.getDate("validCheckDate"));
            cIInsureDemandSchema.setManufacturerName(resultSet.getString("manufacturerName"));
            cIInsureDemandSchema.setModelCode(resultSet.getString("modelCode"));
            cIInsureDemandSchema.setBrandCName(resultSet.getString("brandCName"));
            cIInsureDemandSchema.setBrandName(resultSet.getString("brandName"));
            cIInsureDemandSchema.setCarKindCode(resultSet.getString("carKindCode"));
            cIInsureDemandSchema.setCheckDate("" + resultSet.getDate("checkDate"));
            cIInsureDemandSchema.setEndValidDate("" + resultSet.getDate("endValidDate"));
            cIInsureDemandSchema.setCarStatus(resultSet.getString("carStatus"));
            cIInsureDemandSchema.setHaulage(resultSet.getString("haulage"));
            cIInsureDemandSchema.setStartDate("" + resultSet.getDate("startDate"));
            cIInsureDemandSchema.setEndDate("" + resultSet.getDate("endDate"));
            cIInsureDemandSchema.setAmount(resultSet.getString("amount"));
            cIInsureDemandSchema.setPremium(resultSet.getString("premium"));
            cIInsureDemandSchema.setBenchmarkPremium(resultSet.getString("benchmarkPremium"));
            cIInsureDemandSchema.setPeccancyCoeff(resultSet.getString("peccancyCoeff"));
            cIInsureDemandSchema.setClaimCoeff(resultSet.getString("claimCoeff"));
            cIInsureDemandSchema.setDriverCoeff(resultSet.getString("driverCoeff"));
            cIInsureDemandSchema.setDistrictCoeff(resultSet.getString("districtCoeff"));
            cIInsureDemandSchema.setCommissionRate(resultSet.getString("commissionRate"));
            cIInsureDemandSchema.setBasePremium(resultSet.getString("basePremium"));
            cIInsureDemandSchema.setComCode(resultSet.getString("comCode"));
            cIInsureDemandSchema.setOperatorCode(resultSet.getString("operatorCode"));
            cIInsureDemandSchema.setDemandTime("" + resultSet.getDate("demandTime"));
            cIInsureDemandSchema.setBillDate("" + resultSet.getDate("BillDate"));
            cIInsureDemandSchema.setReinsureFlag(resultSet.getString("ReinsureFlag"));
            cIInsureDemandSchema.setLastBillDate("" + resultSet.getDate("LastBillDate"));
            cIInsureDemandSchema.setRateFloatFlag(resultSet.getString("RateFloatFlag"));
            cIInsureDemandSchema.setPeccancyAdjustReason(resultSet.getString("PeccancyAdjustReason"));
            cIInsureDemandSchema.setClaimAdjustReason(resultSet.getString("ClaimAdjustReason"));
            cIInsureDemandSchema.setDriverRateReason(resultSet.getString("DriverRateReason"));
            cIInsureDemandSchema.setDistrictRateReason(resultSet.getString("DistrictRateReason"));
            cIInsureDemandSchema.setRemark(resultSet.getString("remark"));
            cIInsureDemandSchema.setProconfirmSequenceNo(resultSet.getString("ProconfirmSequenceNo"));    
            cIInsureDemandSchema.setProconfirmStartDate("" + resultSet.getTimestamp("ProconfirmStartDate"));      
            cIInsureDemandSchema.setProconfirmEndDate("" + resultSet.getTimestamp("ProconfirmEndDate"));          
            
            cIInsureDemandSchema.setRestricFlag(resultSet.getString("RestricFlag"));
            cIInsureDemandSchema.setPreferentialPremium(resultSet.getString("PreferentialPremium"));
            cIInsureDemandSchema.setPreferentialFormula(resultSet.getString("PreferentialFormula"));
            cIInsureDemandSchema.setPreferentialDay(resultSet.getString("PreferentialDay"));
            
            
            
            
            
            cIInsureDemandSchema.setLastYearStartDate(""+resultSet.getDate("LastYearStartDate"));
            cIInsureDemandSchema.setLastYearEndDate(""+resultSet.getDate("LastYearEndDate"));
            
            
            
            cIInsureDemandSchema.setSearchSequenceNo(resultSet.getString("SearchSequenceNo"));
            cIInsureDemandSchema.setSafeAdjust(resultSet.getString("SafeAdjust"));
            cIInsureDemandSchema.setNonclaimAdjust(resultSet.getString("NonclaimAdjust"));
            cIInsureDemandSchema.setLoyaltyAdjust(resultSet.getString("LoyaltyAdjust"));
            cIInsureDemandSchema.setUseTypeSource(resultSet.getString("UseTypeSource"));
            
            
            cIInsureDemandSchema.setLoyaltyAdjustReason(resultSet.getString("LoyaltyAdjustReason"));
            cIInsureDemandSchema.setQueryPastDate(resultSet.getString("QueryPastDate"));
            cIInsureDemandSchema.setTransferDate(resultSet.getString("TransferDate"));
            
            
            
            
            cIInsureDemandSchema.setBusiLastYearEndDate(""+resultSet.getDate("BusiLastYearEndDate"));
            
            cIInsureDemandSchema.setBusiAdjustStart(resultSet.getString("BusiAdjustStart"));
            cIInsureDemandSchema.setBusiAdjustEnd(resultSet.getString("BusiAdjustEnd"));
            cIInsureDemandSchema.setPoWeight(resultSet.getString("PoWeight"));
            
			
            cIInsureDemandSchema.setPolicyCoeff(resultSet.getString("PolicyCoeff"));
            
            
            cIInsureDemandSchema.setNoLoyaltyAdjustReason(resultSet.getString("NoLoyaltyAdjustReason"));
            cIInsureDemandSchema.setNoClaimAdjustReason(resultSet.getString("NoClaimAdjustReason"));
            
            
            cIInsureDemandSchema.setSendLastPolicyNo(resultSet.getString("sendLastPolicyNo"));
            
            
            cIInsureDemandSchema.setIP(resultSet.getString("IP"));
            cIInsureDemandSchema.setUsbKey(resultSet.getString("UsbKey"));
            
            
            cIInsureDemandSchema.setPeccancyStartDate(resultSet.getString("PeccancyStartDate"));
            cIInsureDemandSchema.setPeccancyEndDate(resultSet.getString("PeccancyEndDate"));
            cIInsureDemandSchema.setKindAdjustValue(resultSet.getString("KindAdjustValue"));
            cIInsureDemandSchema.setKindAdjustReason(resultSet.getString("KindAdjustReason"));
            cIInsureDemandSchema.setNoKindAdjustReason(resultSet.getString("NoKindAdjustReason"));
            cIInsureDemandSchema.setNoPeccancyReason(resultSet.getString("NoPeccancyReason"));
            
            
            cIInsureDemandSchema.setLastProducerCode(resultSet.getString("LastProducerCode"));
            
            
            cIInsureDemandSchema.setLastPolicyQueryDate(""+resultSet.getDate("LastPolicyQueryDate"));
            cIInsureDemandSchema.setLastPolicyTotalPremium(resultSet.getString("LastPolicyTotalPremium"));
            cIInsureDemandSchema.setInsureInDoorValue(resultSet.getString("InsureInDoorValue"));
            cIInsureDemandSchema.setInsureInDoorReason(resultSet.getString("InsureInDoorReason"));
            cIInsureDemandSchema.setClaimAmountValue(resultSet.getString("ClaimAmountValue"));
            cIInsureDemandSchema.setClaimAmountReason(resultSet.getString("ClaimAmountReason"));
            cIInsureDemandSchema.setNoClaimAmountReason(resultSet.getString("NoClaimAmountReason"));
            cIInsureDemandSchema.setSpecificRiskValue(resultSet.getString("SpecificRiskValue"));
            cIInsureDemandSchema.setIndependentValue(resultSet.getString("IndependentValue"));
            
            
            cIInsureDemandSchema.setCommissionrateupper(resultSet.getString("commissionrateupper"));
            cIInsureDemandSchema.setCompanycommissionrateupper(resultSet.getString("companycommissionrateupper"));
            cIInsureDemandSchema.setClaimeffectreason(resultSet.getString("claimeffectreason"));
            cIInsureDemandSchema.setNewvehicleeffectreason(resultSet.getString("newvehicleeffectreason"));
            cIInsureDemandSchema.setProducereffectreason(resultSet.getString("producereffectreason"));
            
    		
            cIInsureDemandSchema.setFuelType(resultSet.getString("FuelType"));
    		
            
            cIInsureDemandSchema.setTransferFlag(resultSet.getString("TransferFlag"));
            cIInsureDemandSchema.setHighRiskFlag(resultSet.getString("HighRiskFlag"));
            cIInsureDemandSchema.setEffectReason(resultSet.getString("EffectReason"));
            cIInsureDemandSchema.setLastyearCompanyId(resultSet.getString("LastyearCompanyId"));
            
            
            cIInsureDemandSchema.setLastModelCode(resultSet.getString("LastModelCode"));
            cIInsureDemandSchema.setLastModel(resultSet.getString("LastModel"));
            cIInsureDemandSchema.setLastPurchasePrice(resultSet.getString("LastPurchasePrice"));
            
            
            cIInsureDemandSchema.setRepeatLastYearStartDate(resultSet.getTimestamp("RepeatLastYearStartDate") + "");
            cIInsureDemandSchema.setRepeatLastYearEndDate(resultSet.getTimestamp("RepeatLastYearEndDate") + "");
            cIInsureDemandSchema.setReCoverMsg(resultSet.getString("ReCoverMsg"));
            
            
            cIInsureDemandSchema.setChannelType(resultSet.getString("ChannelType"));
            
            
            cIInsureDemandSchema.setUsageTypeMessage(resultSet.getString("UsageTypeMessage"));
            cIInsureDemandSchema.setWholeweight(resultSet.getString("Wholeweight"));
            cIInsureDemandSchema.setDisplacement(resultSet.getString("Displacement"));
            cIInsureDemandSchema.setChgOwnerDate(resultSet.getTimestamp("ChgOwnerDate") + "");
            cIInsureDemandSchema.setProducerType(resultSet.getString("ProducerType"));
            cIInsureDemandSchema.setSalePrice(resultSet.getString("SalePrice"));
            cIInsureDemandSchema.setPmFuelType(resultSet.getString("PmFuelType"));
            cIInsureDemandSchema.setVehicleCategory(resultSet.getString("VehicleCategory"));
            cIInsureDemandSchema.setNoPeccancyAdjustReason(resultSet.getString("NoPeccancyAdjustReason"));
            cIInsureDemandSchema.setNoDriverAdjustReason(resultSet.getString("NoDriverAdjustReason"));
            cIInsureDemandSchema.setVehicleOwnerName(resultSet.getString("VehicleOwnerName"));
            cIInsureDemandSchema.setFleetMessage(resultSet.getString("FleetMessage"));
            cIInsureDemandSchema.setMileageFactorMessage(resultSet.getString("MileageFactorMessage"));
            
            
            cIInsureDemandSchema.setRepeatInsurerArea(resultSet.getString("RepeatInsurerArea"));
            
            cIInsureDemandSchema.setFlag(resultSet.getString("flag"));
            vector.add(cIInsureDemandSchema);
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
      String strSQL = " SELECT COUNT(1) FROM CIInsureDemand WHERE "+ strWhere;
      ResultSet resultSet = null;
      if(iWhereValue != null)
      {
    	  
          
    	  
          dbpool.prepareInnerStatement1007(strSQL);
          
          
          for(int i=0;i<iWhereValue.size();i++)
          {
              
        	  if(iWhereValue.get(i)!=null){
        		  dbpool.setString(i+1,(iWhereValue.get(i)).toString());
        	  }else{
        		  dbpool.setString(i+1,""); 
        	  }
        	  
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
        CIInsureDemandSchema cIInsureDemandSchema = null;
        ResultSet resultSet = null;
        if(iWhereValue != null)
        {
          dbpool.prepareInnerStatement(strSQL);
          for(int i=0;i<iWhereValue.size();i++)
            {
        	  
        	  if(iWhereValue.get(i)!=null){
        		  dbpool.setString(i+1,(iWhereValue.get(i)).toString());
        	  }else{
        		  dbpool.setString(i+1,""); 
        	  }
        	  
            }
          resultSet = dbpool.executePreparedQuery();
        }else
        {
          resultSet = dbpool.query(strSQL);
        }
        while(resultSet.next())
        {
            cIInsureDemandSchema = new CIInsureDemandSchema();
            cIInsureDemandSchema.setProposalNo(resultSet.getString("proposalNo"));
            cIInsureDemandSchema.setDemandNo(resultSet.getString("demandNo"));
            cIInsureDemandSchema.setPolicyNo(resultSet.getString("policyNo"));
            cIInsureDemandSchema.setLicenseNo(resultSet.getString("licenseNo"));
            cIInsureDemandSchema.setLicenseType(resultSet.getString("licenseType"));
            cIInsureDemandSchema.setUseNatureCode(resultSet.getString("useNatureCode"));
            cIInsureDemandSchema.setFrameNo(resultSet.getString("frameNo"));
            cIInsureDemandSchema.setEngineNo(resultSet.getString("engineNo"));
            cIInsureDemandSchema.setLicenseColorCode(resultSet.getString("licenseColorCode"));
            cIInsureDemandSchema.setCarOwner(resultSet.getString("carOwner"));
            cIInsureDemandSchema.setEnrollDate("" + resultSet.getDate("enrollDate"));
            cIInsureDemandSchema.setMakeDate("" + resultSet.getDate("makeDate"));
            cIInsureDemandSchema.setSeatCount(resultSet.getString("seatCount"));
            cIInsureDemandSchema.setTonCount(resultSet.getString("tonCount"));
            cIInsureDemandSchema.setValidCheckDate("" + resultSet.getDate("validCheckDate"));
            cIInsureDemandSchema.setManufacturerName(resultSet.getString("manufacturerName"));
            cIInsureDemandSchema.setModelCode(resultSet.getString("modelCode"));
            cIInsureDemandSchema.setBrandCName(resultSet.getString("brandCName"));
            cIInsureDemandSchema.setBrandName(resultSet.getString("brandName"));
            cIInsureDemandSchema.setCarKindCode(resultSet.getString("carKindCode"));
            cIInsureDemandSchema.setCheckDate("" + resultSet.getDate("checkDate"));
            cIInsureDemandSchema.setEndValidDate("" + resultSet.getDate("endValidDate"));
            cIInsureDemandSchema.setCarStatus(resultSet.getString("carStatus"));
            cIInsureDemandSchema.setHaulage(resultSet.getString("haulage"));
            cIInsureDemandSchema.setStartDate("" + resultSet.getDate("startDate"));
            cIInsureDemandSchema.setEndDate("" + resultSet.getDate("endDate"));
            cIInsureDemandSchema.setAmount(resultSet.getString("amount"));
            cIInsureDemandSchema.setPremium(resultSet.getString("premium"));
            cIInsureDemandSchema.setBenchmarkPremium(resultSet.getString("benchmarkPremium"));
            cIInsureDemandSchema.setPeccancyCoeff(resultSet.getString("peccancyCoeff"));
            cIInsureDemandSchema.setClaimCoeff(resultSet.getString("claimCoeff"));
            cIInsureDemandSchema.setDriverCoeff(resultSet.getString("driverCoeff"));
            cIInsureDemandSchema.setDistrictCoeff(resultSet.getString("districtCoeff"));
            cIInsureDemandSchema.setCommissionRate(resultSet.getString("commissionRate"));
            cIInsureDemandSchema.setBasePremium(resultSet.getString("basePremium"));
            cIInsureDemandSchema.setComCode(resultSet.getString("comCode"));
            cIInsureDemandSchema.setOperatorCode(resultSet.getString("operatorCode"));
            cIInsureDemandSchema.setDemandTime("" + resultSet.getDate("demandTime"));
            cIInsureDemandSchema.setBillDate("" + resultSet.getDate("BillDate"));
            cIInsureDemandSchema.setReinsureFlag(resultSet.getString("ReinsureFlag"));
            cIInsureDemandSchema.setLastBillDate("" + resultSet.getDate("LastBillDate"));
            cIInsureDemandSchema.setRateFloatFlag(resultSet.getString("RateFloatFlag"));
            cIInsureDemandSchema.setPeccancyAdjustReason(resultSet.getString("PeccancyAdjustReason"));
            cIInsureDemandSchema.setClaimAdjustReason(resultSet.getString("ClaimAdjustReason"));
            cIInsureDemandSchema.setDriverRateReason(resultSet.getString("DriverRateReason"));
            cIInsureDemandSchema.setDistrictRateReason(resultSet.getString("DistrictRateReason"));
            cIInsureDemandSchema.setRemark(resultSet.getString("remark"));
            cIInsureDemandSchema.setProconfirmSequenceNo(resultSet.getString("ProconfirmSequenceNo"));    
            cIInsureDemandSchema.setProconfirmStartDate("" + resultSet.getTimestamp("ProconfirmStartDate"));      
            cIInsureDemandSchema.setProconfirmEndDate("" + resultSet.getTimestamp("ProconfirmEndDate"));          
            cIInsureDemandSchema.setRestricFlag(resultSet.getString("RestricFlag"));
            cIInsureDemandSchema.setPreferentialPremium(resultSet.getString("PreferentialPremium"));
            cIInsureDemandSchema.setPreferentialFormula(resultSet.getString("PreferentialFormula"));
            cIInsureDemandSchema.setPreferentialDay(resultSet.getString("PreferentialDay"));
            
            
            
            cIInsureDemandSchema.setLastYearStartDate(""+resultSet.getDate("LastYearStartDate"));
            cIInsureDemandSchema.setLastYearEndDate(""+resultSet.getDate("LastYearEndDate"));
            
            cIInsureDemandSchema.setSearchSequenceNo(resultSet.getString("SearchSequenceNo"));
            cIInsureDemandSchema.setSafeAdjust(resultSet.getString("SafeAdjust"));
            cIInsureDemandSchema.setNonclaimAdjust(resultSet.getString("NonclaimAdjust"));
            cIInsureDemandSchema.setLoyaltyAdjust(resultSet.getString("LoyaltyAdjust"));
            cIInsureDemandSchema.setUseTypeSource(resultSet.getString("UseTypeSource"));
            cIInsureDemandSchema.setLoyaltyAdjustReason(resultSet.getString("LoyaltyAdjustReason"));
            cIInsureDemandSchema.setQueryPastDate(resultSet.getString("QueryPastDate"));
            cIInsureDemandSchema.setTransferDate(resultSet.getString("TransferDate"));
            
            
            cIInsureDemandSchema.setBusiLastYearEndDate(""+resultSet.getDate("BusiLastYearEndDate"));
            
            cIInsureDemandSchema.setBusiAdjustStart(resultSet.getString("BusiAdjustStart"));
            cIInsureDemandSchema.setBusiAdjustEnd(resultSet.getString("BusiAdjustEnd"));
            cIInsureDemandSchema.setPoWeight(resultSet.getString("PoWeight"));
            cIInsureDemandSchema.setPolicyCoeff(resultSet.getString("PolicyCoeff"));
            cIInsureDemandSchema.setNoLoyaltyAdjustReason(resultSet.getString("NoLoyaltyAdjustReason"));
            cIInsureDemandSchema.setNoClaimAdjustReason(resultSet.getString("NoClaimAdjustReason"));
            
            cIInsureDemandSchema.setSendLastPolicyNo(resultSet.getString("sendLastPolicyNo"));
            
            
            cIInsureDemandSchema.setIP(resultSet.getString("IP"));
            cIInsureDemandSchema.setUsbKey(resultSet.getString("UsbKey"));
            
            
            cIInsureDemandSchema.setPeccancyStartDate(resultSet.getString("PeccancyStartDate"));
            cIInsureDemandSchema.setPeccancyEndDate(resultSet.getString("PeccancyEndDate"));
            cIInsureDemandSchema.setKindAdjustValue(resultSet.getString("KindAdjustValue"));
            cIInsureDemandSchema.setKindAdjustReason(resultSet.getString("KindAdjustReason"));
            cIInsureDemandSchema.setNoKindAdjustReason(resultSet.getString("NoKindAdjustReason"));
            cIInsureDemandSchema.setNoPeccancyReason(resultSet.getString("NoPeccancyReason"));
            
            
            cIInsureDemandSchema.setLastProducerCode(resultSet.getString("LastProducerCode"));
            
            
            cIInsureDemandSchema.setLastPolicyQueryDate(""+resultSet.getDate("LastPolicyQueryDate"));
            cIInsureDemandSchema.setLastPolicyTotalPremium(resultSet.getString("LastPolicyTotalPremium"));
            cIInsureDemandSchema.setInsureInDoorValue(resultSet.getString("InsureInDoorValue"));
            cIInsureDemandSchema.setInsureInDoorReason(resultSet.getString("InsureInDoorReason"));
            cIInsureDemandSchema.setClaimAmountValue(resultSet.getString("ClaimAmountValue"));
            cIInsureDemandSchema.setClaimAmountReason(resultSet.getString("ClaimAmountReason"));
            cIInsureDemandSchema.setNoClaimAmountReason(resultSet.getString("NoClaimAmountReason"));
            cIInsureDemandSchema.setSpecificRiskValue(resultSet.getString("SpecificRiskValue"));
            cIInsureDemandSchema.setIndependentValue(resultSet.getString("IndependentValue"));
            
            
            cIInsureDemandSchema.setCommissionrateupper(resultSet.getString("commissionrateupper"));
            cIInsureDemandSchema.setCompanycommissionrateupper(resultSet.getString("companycommissionrateupper"));
            cIInsureDemandSchema.setClaimeffectreason(resultSet.getString("claimeffectreason"));
            cIInsureDemandSchema.setNewvehicleeffectreason(resultSet.getString("newvehicleeffectreason"));
            cIInsureDemandSchema.setProducereffectreason(resultSet.getString("producereffectreason"));
            
    		
            cIInsureDemandSchema.setFuelType(resultSet.getString("FuelType"));
    		
            
            cIInsureDemandSchema.setTransferFlag(resultSet.getString("TransferFlag"));
            cIInsureDemandSchema.setHighRiskFlag(resultSet.getString("HighRiskFlag"));
            cIInsureDemandSchema.setEffectReason(resultSet.getString("EffectReason"));
            cIInsureDemandSchema.setLastyearCompanyId(resultSet.getString("LastyearCompanyId"));
            
            
            cIInsureDemandSchema.setLastModelCode(resultSet.getString("LastModelCode"));
            cIInsureDemandSchema.setLastModel(resultSet.getString("LastModel"));
            cIInsureDemandSchema.setLastPurchasePrice(resultSet.getString("LastPurchasePrice"));
            
            
            cIInsureDemandSchema.setRepeatLastYearStartDate(resultSet.getTimestamp("RepeatLastYearStartDate") + "");
            cIInsureDemandSchema.setRepeatLastYearEndDate(resultSet.getTimestamp("RepeatLastYearEndDate") + "");
            cIInsureDemandSchema.setReCoverMsg(resultSet.getString("ReCoverMsg"));
            
            
            cIInsureDemandSchema.setChannelType(resultSet.getString("ChannelType"));
            
            
            cIInsureDemandSchema.setUsageTypeMessage(resultSet.getString("UsageTypeMessage"));
            cIInsureDemandSchema.setWholeweight(resultSet.getString("Wholeweight"));
            cIInsureDemandSchema.setDisplacement(resultSet.getString("Displacement"));
            cIInsureDemandSchema.setChgOwnerDate(resultSet.getTimestamp("ChgOwnerDate") + "");
            cIInsureDemandSchema.setProducerType(resultSet.getString("ProducerType"));
            cIInsureDemandSchema.setSalePrice(resultSet.getString("SalePrice"));
            cIInsureDemandSchema.setPmFuelType(resultSet.getString("PmFuelType"));
            cIInsureDemandSchema.setVehicleCategory(resultSet.getString("VehicleCategory"));
            cIInsureDemandSchema.setNoPeccancyAdjustReason(resultSet.getString("NoPeccancyAdjustReason"));
            cIInsureDemandSchema.setNoDriverAdjustReason(resultSet.getString("NoDriverAdjustReason"));
            cIInsureDemandSchema.setVehicleOwnerName(resultSet.getString("VehicleOwnerName"));
            cIInsureDemandSchema.setFleetMessage(resultSet.getString("FleetMessage"));
            cIInsureDemandSchema.setMileageFactorMessage(resultSet.getString("MileageFactorMessage"));
            
            
            cIInsureDemandSchema.setRepeatInsurerArea(resultSet.getString("RepeatInsurerArea"));
            
            cIInsureDemandSchema.setFlag(resultSet.getString("flag"));
            vector.add(cIInsureDemandSchema);
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
