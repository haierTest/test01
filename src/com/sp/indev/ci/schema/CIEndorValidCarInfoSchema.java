package com.sp.indiv.ci.schema;

import java.io.Serializable;

public class CIEndorValidCarInfoSchema implements Serializable {

	private String DemandNo = "";
	private String Carmark = "";
	private String VehicleType = "";
	private String Rackno = "";
	private String Engineno = "";
	private String VehiclesTyle = "";
	private String PmuseType = "";
	private String IneffectualDate = "";
	private String RejectDate = "";
	private String VehicleRegisterDate = "";
	private String LastcheckDate = "";
	private String TransferDate = "";
	private String WholeWeight = "";
	private String LimitLoadPerson = "";
	private String LimitLoad = "";
	private String Displacement = "";
	private String MadeFactory = "";
	private String VehicleModel = "";
	private String ProducerType = "";
	private String VehiclebrandCH = "";
	private String VehiclebrandEN = "";
	private String Haulage = "";
	private String VehicleColour = "";
	private String SalePrice = "";
	private String PmfuelType = "";
	private String Status = "";
	private String VehicleCategory = "";
	private String OwnerName = "";

	public String getDemandNo() {
		return DemandNo;
	}
	public void setDemandNo(String demandNo) {
		DemandNo = demandNo;
	}
	public String getCarmark() {
		return Carmark;
	}
	public void setCarmark(String carmark) {
		Carmark = carmark;
	}
	public String getVehicleType() {
		return VehicleType;
	}
	public void setVehicleType(String vehicleType) {
		VehicleType = vehicleType;
	}
	public String getRackno() {
		return Rackno;
	}
	public void setRackno(String rackno) {
		Rackno = rackno;
	}
	public String getEngineno() {
		return Engineno;
	}
	public void setEngineno(String engineno) {
		Engineno = engineno;
	}
	public String getVehiclesTyle() {
		return VehiclesTyle;
	}
	public void setVehiclesTyle(String vehiclesTyle) {
		VehiclesTyle = vehiclesTyle;
	}
	public String getPmuseType() {
		return PmuseType;
	}
	public void setPmuseType(String pmuseType) {
		PmuseType = pmuseType;
	}
	public String getIneffectualDate() {
		return IneffectualDate;
	}
	public void setIneffectualDate(String ineffectualDate) {
		IneffectualDate = ineffectualDate;
	}
	public String getRejectDate() {
		return RejectDate;
	}
	public void setRejectDate(String rejectDate) {
		RejectDate = rejectDate;
	}
	public String getVehicleRegisterDate() {
		return VehicleRegisterDate;
	}
	public void setVehicleRegisterDate(String vehicleRegisterDate) {
		VehicleRegisterDate = vehicleRegisterDate;
	}
	public String getLastcheckDate() {
		return LastcheckDate;
	}
	public void setLastcheckDate(String lastcheckDate) {
		LastcheckDate = lastcheckDate;
	}
	public String getTransferDate() {
		return TransferDate;
	}
	public void setTransferDate(String transferDate) {
		TransferDate = transferDate;
	}
	public String getWholeWeight() {
		return WholeWeight;
	}
	public void setWholeWeight(String wholeWeight) {
		WholeWeight = wholeWeight;
	}
	public String getLimitLoadPerson() {
		return LimitLoadPerson;
	}
	public void setLimitLoadPerson(String limitLoadPerson) {
		LimitLoadPerson = limitLoadPerson;
	}
	public String getLimitLoad() {
		return LimitLoad;
	}
	public void setLimitLoad(String limitLoad) {
		LimitLoad = limitLoad;
	}
	public String getDisplacement() {
		return Displacement;
	}
	public void setDisplacement(String displacement) {
		Displacement = displacement;
	}
	public String getMadeFactory() {
		return MadeFactory;
	}
	public void setMadeFactory(String madeFactory) {
		MadeFactory = madeFactory;
	}
	public String getVehicleModel() {
		return VehicleModel;
	}
	public void setVehicleModel(String vehicleModel) {
		VehicleModel = vehicleModel;
	}
	public String getProducerType() {
		return ProducerType;
	}
	public void setProducerType(String producerType) {
		ProducerType = producerType;
	}
	public String getVehiclebrandCH() {
		return VehiclebrandCH;
	}
	public void setVehiclebrandCH(String vehiclebrandCH) {
		VehiclebrandCH = vehiclebrandCH;
	}
	public String getVehiclebrandEN() {
		return VehiclebrandEN;
	}
	public void setVehiclebrandEN(String vehiclebrandEN) {
		VehiclebrandEN = vehiclebrandEN;
	}
	public String getHaulage() {
		return Haulage;
	}
	public void setHaulage(String haulage) {
		Haulage = haulage;
	}
	public String getVehicleColour() {
		return VehicleColour;
	}
	public void setVehicleColour(String vehicleColour) {
		VehicleColour = vehicleColour;
	}
	public String getSalePrice() {
		return SalePrice;
	}
	public void setSalePrice(String salePrice) {
		SalePrice = salePrice;
	}
	public String getPmfuelType() {
		return PmfuelType;
	}
	public void setPmfuelType(String pmfuelType) {
		PmfuelType = pmfuelType;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public String getVehicleCategory() {
		return VehicleCategory;
	}
	public void setVehicleCategory(String vehicleCategory) {
		VehicleCategory = vehicleCategory;
	}
	public String getOwnerName() {
		return OwnerName;
	}
	public void setOwnerName(String ownerName) {
		OwnerName = ownerName;
	}       
    public void setSchema(CIEndorValidCarInfoSchema iSchema) {
    	this.DemandNo = iSchema.getDemandNo();
    	this.Carmark = iSchema.getCarmark();
    	this.VehicleType = iSchema.getVehicleType();
    	this.Rackno = iSchema.getRackno();
    	this.Engineno = iSchema.getEngineno();
    	this.VehiclesTyle = iSchema.getVehiclesTyle();
    	this.PmuseType = iSchema.getPmuseType();
    	this.IneffectualDate = iSchema.getIneffectualDate();
    	this.RejectDate = iSchema.getRejectDate();
    	this.VehicleRegisterDate = iSchema.getVehicleRegisterDate();
    	this.LastcheckDate = iSchema.getLastcheckDate();
    	this.TransferDate = iSchema.getTransferDate();
    	this.WholeWeight = iSchema.getWholeWeight();
    	this.LimitLoadPerson = iSchema.getLimitLoadPerson();
    	this.LimitLoad = iSchema.getLimitLoad();
    	this.Displacement = iSchema.getDisplacement();
    	this.MadeFactory = iSchema.getMadeFactory();
    	this.VehicleModel = iSchema.getVehicleModel();
    	this.ProducerType = iSchema.getProducerType();
    	this.VehiclebrandCH = iSchema.getVehiclebrandCH();
    	this.VehiclebrandEN = iSchema.getVehiclebrandEN();
    	this.Haulage = iSchema.getHaulage();
    	this.VehicleColour = iSchema.getVehicleColour();
    	this.SalePrice = iSchema.getSalePrice();
    	this.PmfuelType = iSchema.getPmfuelType();
    	this.Status = iSchema.getStatus();
    	this.VehicleCategory = iSchema.getVehicleCategory();
    	this.OwnerName = iSchema.getOwnerName();
    }
}
