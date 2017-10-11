package com.sp.indiv.ci.schema;

import java.io.Serializable;

public class CIInsureQuerySchema implements Serializable
{
	
    private String PolicyNo = "";
	
    private String AppliCode = "";
    
    private String AppliName = "";
    
    private String InsuredCode = "";
    
    private String InsuredName = "";
    
    private String IdentifyNumber = "";
    
    private String CarOwner = "";
    
    private String LicenseNo = "";
    
    private String CarKindCode = "";
    
    private String SeatCount = "";
    
    private String TonCount = "";
    
    private String FrameNo = "";
    
    private String EngineNo = "";
    
    private String VINNo = "";
    
    private String ExhaustScale = "";
    
    private String UseYears = "";
    
    private String RunMiles = "";
    
    private String UseNatureCode = "";
    
    private String LicenseKindCode = "";
    
	
	
	
    private String CIInsureQueryFlag = "";
    
    
    private String ChangOwnerFlag =""; 
    
    
    private String EndorseTypeItemList="";
    
    
    private String IsAmendTax="";
    
    
    private String ValidDate="";
    private String EndorDate="";
    private String EndDate="";
    
    
	private String StopTravelStratDate = "";	
	private String StopTravelEndDate	  = "";	
	private String ExtendStartDate     = "";	
	private String ExtendEndDate 	  = "";	
	private String ValidHour			  = "";	
	private String RecoverTravelDate	  = "";	
	private String StopTravelStratHour = ""; 
	private String StopTravelEndHour	  = ""; 
	private String StopTravelTypeFlag  = ""; 
	private String StartDate = "";	
	private String StartHour = "";  
	private String EndHour = "";	
	private String ValidStatus = ""; 
    
	
	private String SurrenderFlag = "";
	
	
	private String isWholeEndorse = "";
	
	
	private String EndorseType = "";
	public String getEndorseType(){
        return EndorseType;
    }
    public void setEndorseType(String endorseType){
    	EndorseType = endorseType;
    }
	
	
    public String getEndorseTypeItemList(){
         return EndorseTypeItemList;
    }
    
    public void setEndorseTypeItemList(String endorseTypeItemList){
    	EndorseTypeItemList = endorseTypeItemList;
    }
    
    public String getChangOwnerFlag(){
    	  return ChangOwnerFlag;
    }
    
    public void setChangOwnerFlag(String changOwnerFlag){
         ChangOwnerFlag=changOwnerFlag;
    }
    
    public String getCIInsureQueryFlag() {
		return CIInsureQueryFlag;
	}
	public void setCIInsureQueryFlag(String cIInsureQueryFlag) {
		CIInsureQueryFlag = cIInsureQueryFlag;
	}
    
	public String getPolicyNo() {
		return PolicyNo;
	}
	public void setPolicyNo(String policyNo) {
		PolicyNo = policyNo;
	}
	public String getAppliCode() {
		return AppliCode;
	}
	public void setAppliCode(String appliCode) {
		AppliCode = appliCode;
	}
	public String getAppliName() {
		return AppliName;
	}
	public void setAppliName(String appliName) {
		AppliName = appliName;
	}
	public String getCarKindCode() {
		return CarKindCode;
	}
	public void setCarKindCode(String carKindCode) {
		CarKindCode = carKindCode;
	}
	public String getCarOwner() {
		return CarOwner;
	}
	public void setCarOwner(String carOwner) {
		CarOwner = carOwner;
	}
	public String getEngineNo() {
		return EngineNo;
	}
	public void setEngineNo(String engineNo) {
		EngineNo = engineNo;
	}
	public String getExhaustScale() {
		return ExhaustScale;
	}
	public void setExhaustScale(String exhaustScale) {
		ExhaustScale = exhaustScale;
	}
	public String getFrameNo() {
		return FrameNo;
	}
	public void setFrameNo(String frameNo) {
		FrameNo = frameNo;
	}
	public String getIdentifyNumber() {
		return IdentifyNumber;
	}
	public void setIdentifyNumber(String identifyNumber) {
		IdentifyNumber = identifyNumber;
	}
	public String getInsuredCode() {
		return InsuredCode;
	}
	public void setInsuredCode(String insuredCode) {
		InsuredCode = insuredCode;
	}
	public String getInsuredName() {
		return InsuredName;
	}
	public void setInsuredName(String insuredName) {
		InsuredName = insuredName;
	}
	public String getLicenseKindCode() {
		return LicenseKindCode;
	}
	public void setLicenseKindCode(String licenseKindCode) {
		LicenseKindCode = licenseKindCode;
	}
	public String getLicenseNo() {
		return LicenseNo;
	}
	public void setLicenseNo(String licenseNo) {
		LicenseNo = licenseNo;
	}
	public String getRunMiles() {
		return RunMiles;
	}
	public void setRunMiles(String runMiles) {
		RunMiles = runMiles;
	}
	public String getSeatCount() {
		return SeatCount;
	}
	public void setSeatCount(String seatCount) {
		SeatCount = seatCount;
	}
	public String getTonCount() {
		return TonCount;
	}
	public void setTonCount(String tonCount) {
		TonCount = tonCount;
	}
	public String getUseNatureCode() {
		return UseNatureCode;
	}
	public void setUseNatureCode(String useNatureCode) {
		UseNatureCode = useNatureCode;
	}
	public String getUseYears() {
		return UseYears;
	}
	public void setUseYears(String useYears) {
		UseYears = useYears;
	}
	public String getVINNo() {
		return VINNo;
	}
	public void setVINNo(String no) {
		VINNo = no;
	}
	public String getIsAmendTax(){
        return IsAmendTax;   
    }
    public void setIsAmendTax(String isAmendTax){
        IsAmendTax=isAmendTax;   
    }
    
    public String getValidDate(){
        return ValidDate;
    }
    public void setValidDate(String ValidDate){
        this.ValidDate=ValidDate;
    }
    public String getEndorDate(){
        return EndorDate;
    }
    public void setEndorDate(String EndorDate){
        this.EndorDate=EndorDate;
    }
    public String getEndDate(){
        return EndDate;
    }
    public void setEndDate(String EndDate){
        this.EndDate=EndDate;
    }
    
    
    public String getStopTravelStratDate(){
        return StopTravelStratDate;
    }
    public void setStopTravelStratDate(String StopTravelStratDate){
        this.StopTravelStratDate=StopTravelStratDate;
    }
    
    public String getStopTravelEndDate(){
        return StopTravelEndDate;
    }
    public void setStopTravelEndDate(String StopTravelEndDate){
        this.StopTravelEndDate=StopTravelEndDate;
    }
    
    public String getExtendStartDate(){
        return ExtendStartDate;
    }
    public void setExtendStartDate(String ExtendStartDate){
        this.ExtendStartDate=ExtendStartDate;
    }
    
    public String getExtendEndDate(){
        return ExtendEndDate;
    }
    public void setExtendEndDate(String ExtendEndDate){
        this.ExtendEndDate=ExtendEndDate;
    }
    
    public String getValidHour(){
        return ValidHour;
    }
    public void setValidHour(String ValidHour){
        this.ValidHour=ValidHour;
    }
    
    public String getRecoverTravelDate(){
        return RecoverTravelDate;
    }
    public void setRecoverTravelDate(String RecoverTravelDate){
        this.RecoverTravelDate=RecoverTravelDate;
    }
    
    public String getStopTravelStratHour(){
        return StopTravelStratHour;
    }
    public void setStopTravelStratHour(String StopTravelStratHour){
        this.StopTravelStratHour=StopTravelStratHour;
    }
    
    public String getStopTravelEndHour(){
        return StopTravelEndHour;
    }
    public void setStopTravelEndHour(String StopTravelEndHour){
        this.StopTravelEndHour=StopTravelEndHour;
    }
    
    public String getStopTravelTypeFlag(){
        return StopTravelTypeFlag;
    }
    public void setStopTravelTypeFlag(String StopTravelTypeFlag){
        this.StopTravelTypeFlag=StopTravelTypeFlag;
    }
    
    public String getStartDate(){
        return StartDate;
    }
    public void setStartDate(String StartDate){
        this.StartDate=StartDate;
    }
    
    public String getStartHour(){
        return StartHour;
    }
    public void setStartHour(String StartHour){
        this.StartHour=StartHour;
    }
    
    public String getEndHour(){
        return EndHour;
    }
    public void setEndHour(String EndHour){
        this.EndHour=EndHour;
    }
    public String getValidStatus(){
        return ValidStatus;
    }
    public void setValidStatus(String ValidStatus){
        this.ValidStatus=ValidStatus;
    }
    
    
    
    public String getSurrenderFlag(){
        return SurrenderFlag;
    }
    public void setSurrenderFlag(String SurrenderFlag){
        this.SurrenderFlag=SurrenderFlag;
    }
    
    
    public String getIsWholeEndorse() {
		return isWholeEndorse;
	}
	public void setIsWholeEndorse(String isWholeEndorse) {
		this.isWholeEndorse = isWholeEndorse;
	}
    
	/**
     * @param iSchema ∂‘œÛCIInsureQuerySchema
     */       
    public void setSchema(CIInsureQuerySchema iSchema)
    {
        this.PolicyNo  = iSchema.getPolicyNo();
    	this.AppliCode = iSchema.getAppliCode();
        this.AppliName = iSchema.getAppliName();
        this.InsuredCode = iSchema.getInsuredCode();
        this.InsuredName = iSchema.getInsuredName();
        this.IdentifyNumber = iSchema.getIdentifyNumber();
        this.CarOwner = iSchema.getCarOwner();
        this.LicenseNo = iSchema.getLicenseNo();
        this.CarKindCode = iSchema.getCarKindCode();
        this.SeatCount = iSchema.getSeatCount();
        this.TonCount = iSchema.getTonCount();
        this.FrameNo = iSchema.getFrameNo();
        this.EngineNo = iSchema.getEngineNo();
        this.VINNo = iSchema.getVINNo();
        this.ExhaustScale = iSchema.getExhaustScale();
        this.UseYears = iSchema.getUseYears();
        this.RunMiles = iSchema.getRunMiles();
        this.UseNatureCode = iSchema.getUseNatureCode();
        this.LicenseKindCode = iSchema.getLicenseKindCode();
        this.CIInsureQueryFlag = iSchema.getCIInsureQueryFlag();
        this.ChangOwnerFlag=iSchema.getChangOwnerFlag();
        this.IsAmendTax=iSchema.getIsAmendTax();
        
        this.ValidDate=iSchema.getValidDate();
        this.EndorDate=iSchema.getEndorDate();
        this.EndDate=iSchema.getEndDate();
        this.StopTravelStratDate=iSchema.getStopTravelStratDate();
        this.StopTravelEndDate=iSchema.getStopTravelEndDate();
        this.ExtendStartDate=iSchema.getExtendStartDate();
        this.ExtendEndDate=iSchema.getExtendEndDate();
        this.ValidHour=iSchema.getValidHour();
        this.RecoverTravelDate=iSchema.getRecoverTravelDate();
        this.StopTravelStratHour=iSchema.getStopTravelStratHour();
        this.StopTravelEndHour=iSchema.getStopTravelEndHour();
        this.StopTravelTypeFlag=iSchema.getStopTravelTypeFlag();
        this.StartDate=iSchema.getStartDate();
        this.StartHour =iSchema.getStartHour();
        this.EndHour =iSchema.getEndHour();
        this.ValidStatus =iSchema.getValidStatus();
        
        
        this.SurrenderFlag = iSchema.getSurrenderFlag();
        
    	
        this.EndorseType = iSchema.getEndorseType();
        
        
        this.isWholeEndorse = iSchema.getIsWholeEndorse();
        
    }
}
