package com.sp.indiv.ci.schema;

import com.sp.utility.string.Str;

public class CIVehiclePriceSchema {

	private String VehicleCode = "";

	private String RVenicleName = "";

	private String RVenicleBrand = "";

	private String RVehicleFamily = "";

	private String RImportFlag = "";

	private String RLimitLoadPersonNumber = "";

	private String RVenicleWeight = "";

	private String RVehicleTonnage = "";

	private String TransmissionType = "";

	private String ABSFlag = "";

	private String AlarmFlag = "";

	private String AirbagNum = "";

	private String VehicleDescription = "";

	private String VeniclePrice = "";

	private String Refcode1 = "";

	private String Refcode2 = "";

	private String RExhaustCapacity = "";

	private String RMarketDate = "";

	private String IsPriced = "";

	private String RiskFlag = "";
	
	
	private String FuelType = "";
	
	
	private String RVehicleModel = "";
	

	public String getFuelType() {
		return Str.rightTrim(FuelType);
	}

	public void setFuelType(String fuelType) {
		FuelType = Str.rightTrim(fuelType);
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer(400);
		sb.append("[VehicleCode=" + VehicleCode);
		sb.append(",RVenicleName=" + RVenicleName);
		sb.append(",RVenicleBrand=" + RVenicleBrand);
		sb.append(",RVehicleFamily=" + RVehicleFamily);
		sb.append(",RImportFlag=" + RImportFlag);
		sb.append(",RLimitLoadPersonNumber=" + RLimitLoadPersonNumber);
		sb.append(",RVenicleWeight=" + RVenicleWeight);
		sb.append(",RVehicleTonnage=" + RVehicleTonnage);
		sb.append(",TransmissionType=" + TransmissionType);
		sb.append(",ABSFlag=" + ABSFlag);
		sb.append(",AlarmFlag=" + AlarmFlag);
		sb.append(",AirbagNum=" + AirbagNum);
		sb.append(",VehicleDescription=" + VehicleDescription);
		sb.append(",VeniclePrice=" + VeniclePrice);
		sb.append(",Refcode1=" + Refcode1);
		sb.append(",Refcode2=" + Refcode2);
		sb.append(",RExhaustCapacity=" + RExhaustCapacity);
		sb.append(",RMarketDate=" + RMarketDate);
		sb.append(",IsPriced=" + IsPriced);
		sb.append(",RiskFlag=" + RiskFlag);
		
		sb.append(",FuelType=" + FuelType);
		
		sb.append("]");
		return sb.toString();
	}

	public String getAbsFlag() {
		return Str.rightTrim(ABSFlag);
	}

	public void setAbsFlag(String absFlag) {
		ABSFlag = Str.rightTrim(absFlag);
	}

	public String getAirbagNum() {
		return Str.rightTrim(AirbagNum);
	}

	public void setAirbagNum(String airbagNum) {
		AirbagNum = Str.rightTrim(airbagNum);
	}

	public String getAlarmFlag() {
		return Str.rightTrim(AlarmFlag);
	}

	public void setAlarmFlag(String alarmFlag) {
		AlarmFlag = Str.rightTrim(alarmFlag);
	}

	public String getIsPriced() {
		return Str.rightTrim(IsPriced);
	}

	public void setIsPriced(String isPriced) {
		IsPriced = Str.rightTrim(isPriced);
	}

	public String getRefcode1() {
		return Str.rightTrim(Refcode1);
	}

	public void setRefcode1(String refcode1) {
		Refcode1 = Str.rightTrim(refcode1);
	}

	public String getRefcode2() {
		return Str.rightTrim(Refcode2);
	}

	public void setRefcode2(String refcode2) {
		Refcode2 = Str.rightTrim(refcode2);
	}

	public String getRExhaustCapacity() {
		return Str.rightTrim(RExhaustCapacity);
	}

	public void setRExhaustCapacity(String exhaustCapacity) {
		RExhaustCapacity = Str.rightTrim(exhaustCapacity);
	}

	public String getRImportFlag() {
		return Str.rightTrim(RImportFlag);
	}

	public void setRImportFlag(String importFlag) {
		RImportFlag = Str.rightTrim(importFlag);
	}

	public String getRLimitLoadPersonNumber() {
		return Str.rightTrim(RLimitLoadPersonNumber);
	}

	public void setRLimitLoadPersonNumber(String limitLoadPersonNumber) {
		RLimitLoadPersonNumber = Str.rightTrim(limitLoadPersonNumber);
	}

	public String getRMarketDate() {
		return Str.rightTrim(RMarketDate);
	}

	public void setRMarketDate(String marketDate) {
		RMarketDate = Str.rightTrim(marketDate);
	}

	public String getRVehicleFamily() {
		return Str.rightTrim(RVehicleFamily);
	}

	public void setRVehicleFamily(String vehicleFamily) {
		RVehicleFamily = Str.rightTrim(vehicleFamily);
	}

	public String getRVehicleTonnage() {
		return Str.rightTrim(RVehicleTonnage);
	}

	public void setRVehicleTonnage(String vehicleTonnage) {
		RVehicleTonnage = Str.rightTrim(vehicleTonnage);
	}

	public String getRVenicleBrand() {
		return Str.rightTrim(RVenicleBrand);
	}

	public void setRVenicleBrand(String venicleBrand) {
		RVenicleBrand = Str.rightTrim(venicleBrand);
	}

	public String getRVenicleName() {
		return Str.rightTrim(RVenicleName);
	}

	public void setRVenicleName(String venicleName) {
		RVenicleName = Str.rightTrim(venicleName);
	}

	public String getRVenicleWeight() {
		return Str.rightTrim(RVenicleWeight);
	}

	public void setRVenicleWeight(String venicleWeight) {
		RVenicleWeight = Str.rightTrim(venicleWeight);
	}

	public String getTransmissionType() {
		return Str.rightTrim(TransmissionType);
	}

	public void setTransmissionType(String transmissionType) {
		TransmissionType = Str.rightTrim(transmissionType);
	}

	public String getVehicleCode() {
		return Str.rightTrim(VehicleCode);
	}

	public void setVehicleCode(String vehicleCode) {
		VehicleCode = Str.rightTrim(vehicleCode);
	}

	public String getVehicleDescription() {
		return Str.rightTrim(VehicleDescription);
	}

	public void setVehicleDescription(String vehicleDescription) {
		VehicleDescription = Str.rightTrim(vehicleDescription);
	}

	public String getVeniclePrice() {
		return Str.rightTrim(VeniclePrice);
	}

	public void setVeniclePrice(String veniclePrice) {
		VeniclePrice = Str.rightTrim(veniclePrice);
	}

	public String getRiskFlag() {
		return Str.rightTrim(RiskFlag);
	}

	public void setRiskFlag(String riskFlag) {
		RiskFlag = Str.rightTrim(riskFlag);
	}
	
	
	public String getRVehicleModel() {
		return RVehicleModel;
	}

	public void setRVehicleModel(String rVehicleModel) {
		RVehicleModel = rVehicleModel;
	}
	

	public void setSchema(CIVehiclePriceSchema iSchema) {
		VehicleCode = iSchema.getVehicleCode();
		RVenicleName = iSchema.getRVenicleName();
		RVenicleBrand = iSchema.getRVenicleBrand();
		RVehicleFamily = iSchema.getRVehicleFamily();
		RImportFlag = iSchema.getRImportFlag();
		RLimitLoadPersonNumber = iSchema.getRLimitLoadPersonNumber();
		RVenicleWeight = iSchema.getRVenicleWeight();
		RVehicleTonnage = iSchema.getRVehicleTonnage();
		TransmissionType = iSchema.getTransmissionType();
		ABSFlag = iSchema.getAbsFlag();
		AlarmFlag = iSchema.getAlarmFlag();
		AirbagNum = iSchema.getAirbagNum();
		VehicleDescription = iSchema.getVehicleDescription();
		VeniclePrice = iSchema.getVeniclePrice();
		Refcode1 = iSchema.getRefcode1();
		Refcode2 = iSchema.getRefcode2();
		RExhaustCapacity = iSchema.getRExhaustCapacity();
		RMarketDate = iSchema.getRMarketDate();
		IsPriced = iSchema.getIsPriced();
		RiskFlag = iSchema.getRiskFlag();
		
		FuelType = iSchema.getFuelType();
		
	}
}
