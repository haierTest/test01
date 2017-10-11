package com.sp.indiv.ci.schema;

public class PrpCIEndorClaimLossSchema {

	private String DemandNo = "";
	private String SerialNo = "";
	private String DecisionId = "";
	private String DriverLicenseNo = "";
	private String LicensePlateNo = "";
	private String LicensePlateType = "";
	private String ViolationPlace = "";
	private String ViolationTime = "";
	private String RecognitionDate = "";
	private String JurisdictionAgencyCod = "";
	private String ViolationRecordTypeCode = "";
	private String MonitorId = "";

	public String getDemandNo() {
		return DemandNo;
	}
	public void setDemandNo(String demandNo) {
		DemandNo = demandNo;
	}
	public String getSerialNo() {
		return SerialNo;
	}
	public void setSerialNo(String serialNo) {
		SerialNo = serialNo;
	}
	public String getDecisionId() {
		return DecisionId;
	}
	public void setDecisionId(String decisionId) {
		DecisionId = decisionId;
	}
	public String getDriverLicenseNo() {
		return DriverLicenseNo;
	}
	public void setDriverLicenseNo(String driverLicenseNo) {
		DriverLicenseNo = driverLicenseNo;
	}
	public String getLicensePlateNo() {
		return LicensePlateNo;
	}
	public void setLicensePlateNo(String licensePlateNo) {
		LicensePlateNo = licensePlateNo;
	}
	public String getLicensePlateType() {
		return LicensePlateType;
	}
	public void setLicensePlateType(String licensePlateType) {
		LicensePlateType = licensePlateType;
	}
	public String getViolationPlace() {
		return ViolationPlace;
	}
	public void setViolationPlace(String violationPlace) {
		ViolationPlace = violationPlace;
	}
	public String getViolationTime() {
		return ViolationTime;
	}
	public void setViolationTime(String violationTime) {
		ViolationTime = violationTime;
	}
	public String getRecognitionDate() {
		return RecognitionDate;
	}
	public void setRecognitionDate(String recognitionDate) {
		RecognitionDate = recognitionDate;
	}
	public String getJurisdictionAgencyCod() {
		return JurisdictionAgencyCod;
	}
	public void setJurisdictionAgencyCod(String jurisdictionAgencyCod) {
		JurisdictionAgencyCod = jurisdictionAgencyCod;
	}
	public String getViolationRecordTypeCode() {
		return ViolationRecordTypeCode;
	}
	public void setViolationRecordTypeCode(String violationRecordTypeCode) {
		ViolationRecordTypeCode = violationRecordTypeCode;
	}
	public String getMonitorId() {
		return MonitorId;
	}
	public void setMonitorId(String monitorId) {
		MonitorId = monitorId;
	}

	public void setSchema(PrpCIEndorClaimLossSchema iSchema){
		this.DemandNo = iSchema.getDemandNo();
		this.SerialNo = iSchema.getSerialNo();
		this.DecisionId = iSchema.getDecisionId();
		this.DriverLicenseNo = iSchema.getDriverLicenseNo();
		this.LicensePlateNo = iSchema.getLicensePlateNo();
		this.LicensePlateType = iSchema.getLicensePlateType();
		this.ViolationPlace = iSchema.getViolationPlace();
		this.ViolationTime = iSchema.getViolationTime();
		this.RecognitionDate = iSchema.getRecognitionDate();
		this.JurisdictionAgencyCod = iSchema.getJurisdictionAgencyCod();
		this.ViolationRecordTypeCode = iSchema.getViolationRecordTypeCode();
		this.MonitorId = iSchema.getMonitorId();
	}
}
