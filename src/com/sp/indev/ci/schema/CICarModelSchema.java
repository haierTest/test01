package com.sp.indiv.ci.schema;

import java.util.ArrayList;
import java.util.List;

import com.sp.phonesale.schema.ExchangeXMLSchema;
import com.sp.sysframework.common.util.XMLUtils;
import com.sp.utility.string.Str;

/**
 * ≥µº€≤È—Øschema
 * 
 * @author luogang
 * 
 */
public class CICarModelSchema {

	private String CarMark = "";

	private String VehicleType = "";

	private String EngineNo = "";

	private String RackNo = "";

	private String NewVehicleFlag = "";

	private String EcdemicVehicleFlag = "";

	private String MadeFactory = "";

	private String VehicleBrand = "";

	private String VenicleModel = "";

	private String PoWeight = "";

	private String MadeDate = "";

	private String RegisterDate = "";

	private String VehicleStyleDesc = "";

	private String LimitLoad = "";

	private String LimitLoadPerson = "";

	private String ExhaustCapacity = "";

	private String SalesChannelCode = "";

	private String SearchSequenceNo = "";

	private String CarMarkCI = "";

	private String VehicleTypeCI = "";

	private String EngineNoCI = "";

	private String RackNoCI = "";

	private List CIVehiclePriceList = new ArrayList(); 
	
	
	private String ComCode = "";
	
	
	
	private String CarOwer = "";
	
	
    private String CCSITestFlag="";
    
    private String newFlag = "";
    private String infotranseno = "";
    private String operatesite = "";
    
    public String getNewFlag() {
		return newFlag;
	}

	public void setNewFlag(String newFlag) {
		this.newFlag = newFlag;
	}

	public String getInfotranseno() {
		return infotranseno;
	}

	public void setInfotranseno(String infotranseno) {
		this.infotranseno = infotranseno;
	}

	public String getOperatesite() {
		return operatesite;
	}

	public void setOperatesite(String operatesite) {
		this.operatesite = operatesite;
	}
	


	public String getCCSITestFlag() {
		return CCSITestFlag;
	}

	public void setCCSITestFlag(String cCSITestFlag) {
		CCSITestFlag = cCSITestFlag;
	}
    ExchangeXMLSchema exchangeXMLSchema = new ExchangeXMLSchema();
    public ExchangeXMLSchema getExchangeXMLSchema() {
		return exchangeXMLSchema;
	}

	public void setExchangeXMLSchema(ExchangeXMLSchema exchangeXMLSchema) {
		this.exchangeXMLSchema = exchangeXMLSchema;
	}

	

	public String toString() {
		StringBuffer sb = new StringBuffer(400);
		sb.append("[CarMark=" + CarMark);
		sb.append(",VehicleType=" + VehicleType);
		sb.append(",EngineNo=" + EngineNo);
		sb.append(",RackNo=" + RackNo);
		sb.append(",NewVehicleFlag=" + NewVehicleFlag);
		sb.append(",EcdemicVehicleFlag=" + EcdemicVehicleFlag);
		sb.append(",MadeFactory=" + MadeFactory);
		sb.append(",VehicleBrand=" + VehicleBrand);
		sb.append(",VenicleModel=" + VenicleModel);
		sb.append(",PoWeight=" + PoWeight);
		sb.append(",MadeDate=" + MadeDate);
		sb.append(",RegisterDate=" + RegisterDate);
		sb.append(",VehicleStyleDesc=" + VehicleStyleDesc);
		sb.append(",LimitLoad=" + LimitLoad);
		sb.append(",LimitLoadPerson=" + LimitLoadPerson);
		sb.append(",ExhaustCapacity=" + ExhaustCapacity);
		sb.append(",SalesChannelCode=" + SalesChannelCode);
		sb.append(",SearchSequenceNo=" + SearchSequenceNo);
		sb.append(",CarMarkCI=" + CarMarkCI);
		sb.append(",VehicleTypeCI=" + VehicleTypeCI);
		sb.append(",EngineNoCI=" + EngineNoCI);
		sb.append(",RackNoCI=" + RackNoCI);
		sb.append("]");
		return sb.toString();
	}

	public String getCarMark() {
		return Str.rightTrim(CarMark);
	}

	public void setCarMark(String carMark) {
		CarMark = Str.rightTrim(carMark);
	}

	public String getEcdemicVehicleFlag() {
		return Str.rightTrim(EcdemicVehicleFlag);
	}

	public void setEcdemicVehicleFlag(String ecdemicVehicleFlag) {
		EcdemicVehicleFlag = Str.rightTrim(ecdemicVehicleFlag);
	}

	public String getEngineNo() {
		return Str.rightTrim(EngineNo);
	}

	public void setEngineNo(String engineNo) {
		EngineNo = Str.rightTrim(engineNo);
	}

	public String getExhaustCapacity() {
		return Str.rightTrim(ExhaustCapacity);
	}

	public void setExhaustCapacity(String exhaustCapacity) {
		ExhaustCapacity = Str.rightTrim(exhaustCapacity);
	}

	public String getLimitLoad() {
		return Str.rightTrim(LimitLoad);
	}

	public void setLimitLoad(String limitLoad) {
		LimitLoad = Str.rightTrim(limitLoad);
	}

	public String getLimitLoadPerson() {
		return Str.rightTrim(LimitLoadPerson);
	}

	public void setLimitLoadPerson(String limitLoadPerson) {
		LimitLoadPerson = Str.rightTrim(limitLoadPerson);
	}

	public String getMadeDate() {
		return Str.rightTrim(MadeDate);
	}

	public void setMadeDate(String madeDate) {
		MadeDate = Str.rightTrim(madeDate);
	}

	public String getMadeFactory() {
		return Str.rightTrim(MadeFactory);
	}

	public void setMadeFactory(String madeFactory) {
		MadeFactory = Str.rightTrim(madeFactory);
	}

	public String getNewVehicleFlag() {
		return Str.rightTrim(NewVehicleFlag);
	}

	public void setNewVehicleFlag(String newVehicleFlag) {
		NewVehicleFlag = Str.rightTrim(newVehicleFlag);
	}

	public String getPoWeight() {
		return Str.rightTrim(PoWeight);
	}

	public void setPoWeight(String poWeight) {
		PoWeight = Str.rightTrim(poWeight);
	}

	public String getRackNo() {
		return Str.rightTrim(RackNo);
	}

	public void setRackNo(String rackNo) {
		RackNo = Str.rightTrim(rackNo);
	}

	public String getRegisterDate() {
		return Str.rightTrim(RegisterDate);
	}

	public void setRegisterDate(String registerDate) {
		RegisterDate = Str.rightTrim(registerDate);
	}

	public String getSalesChannelCode() {
		return Str.rightTrim(SalesChannelCode);
	}

	public void setSalesChannelCode(String salesChannelCode) {
		SalesChannelCode = Str.rightTrim(salesChannelCode);
	}

	public String getVehicleBrand() {
		return Str.rightTrim(VehicleBrand);
	}

	public void setVehicleBrand(String vehicleBrand) {
		VehicleBrand = Str.rightTrim(vehicleBrand);
	}

	public String getVehicleStyleDesc() {
		return Str.rightTrim(VehicleStyleDesc);
	}

	public void setVehicleStyleDesc(String vehicleStyleDesc) {
		VehicleStyleDesc = Str.rightTrim(vehicleStyleDesc);
	}

	public String getVehicleType() {
		return Str.rightTrim(VehicleType);
	}

	public void setVehicleType(String vehicleType) {
		VehicleType = Str.rightTrim(vehicleType);
	}

	public String getVenicleModel() {
		return Str.rightTrim(VenicleModel);
	}

	public void setVenicleModel(String venicleModel) {
		VenicleModel = Str.rightTrim(venicleModel);
	}

	public String getSearchSequenceNo() {
		return Str.rightTrim(SearchSequenceNo);
	}

	public void setSearchSequenceNo(String searchSequenceNo) {
		SearchSequenceNo = Str.rightTrim(searchSequenceNo);
	}

	public String getCarMarkCI() {
		return Str.rightTrim(CarMarkCI);
	}

	public void setCarMarkCI(String carMarkCI) {
		CarMarkCI = Str.rightTrim(carMarkCI);
	}

	public String getEngineNoCI() {
		return Str.rightTrim(EngineNoCI);
	}

	public void setEngineNoCI(String engineNoCI) {
		EngineNoCI = Str.rightTrim(engineNoCI);
	}

	public String getRackNoCI() {
		return Str.rightTrim(RackNoCI);
	}

	public void setRackNoCI(String rackNoCI) {
		RackNoCI = Str.rightTrim(rackNoCI);
	}

	public String getVehicleTypeCI() {
		return Str.rightTrim(VehicleTypeCI);
	}

	public void setVehicleTypeCI(String vehicleTypeCI) {
		VehicleTypeCI = Str.rightTrim(vehicleTypeCI);
	}

	public List getCIVehiclePriceList() {
		return CIVehiclePriceList;
	}

	public void setCIVehiclePriceList(List vehiclePriceList) {
		CIVehiclePriceList = vehiclePriceList;
	}

	public String getComCode() {
		return ComCode;
	}

	public void setComCode(String comCode) {
		ComCode = comCode;
	}

	public String getCarOwer() {
		return CarOwer;
	}

	public void setCarOwer(String carOwer) {
		CarOwer = carOwer;
	}
	
}
