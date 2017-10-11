package com.sp.interactive.interf;

import java.io.IOException;
import java.io.StringWriter;
import java.text.DecimalFormat;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.sp.indiv.ci.schema.CICarModelSchema;
import com.sp.indiv.ci.schema.CIVehiclePriceSchema;
import com.sp.utiall.blsvr.BLPrpDcarModel;
import com.sp.utiall.schema.PrpDcarModelSchema;
import com.sp.utility.database.DbPool;

/**
 * 车价查询后将其返回值封装成报文
 * @author qilin
 * */

public class CarInfoQueryEncoder {

	public String encode(DbPool dbPool,Map paramMap,BLPrpDcarModel blPrpDcarModel,CICarModelSchema ciCarModelSchema) throws Exception{
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("PACKET");
		root.addAttribute("type", "RESPONSE");
		root.addAttribute("version", "1.0");

		addHead(root,paramMap, blPrpDcarModel, ciCarModelSchema);
		addBody(root,paramMap, blPrpDcarModel, ciCarModelSchema);
		return formateXml(doc.asXML());
		
	}
	
	public String encodeException(Map paramMap,Exception e) throws IOException, DocumentException{
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("PACKET");
		root.addAttribute("type", "RESPONSE");
		root.addAttribute("version", "1.0");
		
		Element head = root.addElement("HEAD");
		Element REQUESTTYPE = head.addElement("REQUESTTYPE");
		String requestType = (String) paramMap.get("requestType");
		REQUESTTYPE.addText(requestType);
		
		Element responseCode = head.addElement("RESPONSECODE");
		Element responseMessage = head.addElement("RESPONSEMESSAGE");
		
		responseCode.addText("100000");
		responseMessage.addText(e.getMessage());
			
		return formateXml(doc.asXML());
	}
	
	public String encodeException2(Map paramMap,Exception e) throws IOException, DocumentException{
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("PACKET");
		root.addAttribute("type", "RESPONSE");
		root.addAttribute("version", "1.0");
		
		Element head = root.addElement("HEAD");
		Element REQUESTTYPE = head.addElement("REQUESTTYPE");
		String requestType = (String) paramMap.get("requestType");
		REQUESTTYPE.addText(requestType);
		
		Element responseCode = head.addElement("RESPONSECODE");
		Element responseMessage = head.addElement("RESPONSEMESSAGE");
		
		responseCode.addText("100000");
		responseMessage.addText("没有查到任何数据");
			
		return formateXml(doc.asXML());
	}
	
	private void addHead(Element root,Map paramMap, BLPrpDcarModel blPrpDcarModel,
			CICarModelSchema ciCarModelSchema) {
		Element head = root.addElement("HEAD");
		Element REQUESTTYPE = head.addElement("REQUESTTYPE");
		String requestType = ((String) paramMap.get("requestType")).trim();
		REQUESTTYPE.addText(requestType);
		Element responseCode = head.addElement("RESPONSECODE");
		Element responseMessage = head.addElement("RESPONSEMESSAGE");
		responseCode.addText("000000");
		responseMessage.addText("成功");
		
	}

	private void addBody(Element root,Map paramMap, BLPrpDcarModel blPrpDcarModel,
			CICarModelSchema ciCarModelSchema) throws Exception {
		Element  body = root.addElement("BODY");
		addCar(body,paramMap,blPrpDcarModel,ciCarModelSchema);
		
	}

	private void addCar(Element body, Map paramMap,BLPrpDcarModel blPrpDcarModel,
			CICarModelSchema ciCarModelSchema) throws Exception {
		Element carModelList = body.addElement("Car_Model_List");
		int length=blPrpDcarModel.getSize();
		if(length>0){
			for(int i=0;i<length;i++){
				
				Element carModelData = carModelList.addElement("Car_Model_Data");
				PrpDcarModelSchema prpDcarModelSchema=blPrpDcarModel.getArr(i);
				 
				Datadeal datadeal = new Datadeal();
		        datadeal.RealPurchasePrice(paramMap, prpDcarModelSchema, ciCarModelSchema);
				addCarModelData(carModelData,paramMap,prpDcarModelSchema,ciCarModelSchema);
			}
		}
		
	}

	private void addCarModelData(Element carModelData,Map paramMap,
			PrpDcarModelSchema prpDcarModelSchema,CICarModelSchema ciCarModelSchema)
	{
		     String comcode=ciCarModelSchema.getComCode();
		     if("01".equals(comcode.substring(0, 2))||"07".equals(comcode.substring(0, 2))){
		    	 Element SearchSequenceNo = carModelData.addElement("SearchSequenceNo");
		     	String searchSequenceNo = ciCarModelSchema.getSearchSequenceNo().trim();
		     	SearchSequenceNo.addText(searchSequenceNo);
		 		
		     	Element LicenseNo = carModelData.addElement("LicenseNo");
		     	String licenseNo = ciCarModelSchema.getCarMarkCI().trim();
		     	LicenseNo.addText(licenseNo);
		 		
		     	Element LicenseType = carModelData.addElement("LicenseType");
		     	String licenseType = ciCarModelSchema.getVehicleTypeCI().trim();
		     	LicenseType.addText(licenseType);
		 		
		     	Element FrameNo = carModelData.addElement("FrameNo");
		     	String frameNo = ciCarModelSchema.getRackNoCI().trim();
		     	FrameNo.addText(frameNo);
		 		
		     	Element EnginNo = carModelData.addElement("EnginNo");
		     	String enginNo = ciCarModelSchema.getEngineNoCI().trim();
		     	EnginNo.addText(enginNo);
		 		
		     	Element CarOwner = carModelData.addElement("CarOwner");
		     	String carOwner = ciCarModelSchema.getCarOwer().trim();
		     	CarOwner.addText(carOwner);
		 		
		     	Element EnrollDate = carModelData.addElement("EnrollDate");
		     	String enrollDate = ciCarModelSchema.getRegisterDate().trim();
		     	EnrollDate.addText(enrollDate);
		     }else{
			    
				Element SearchSequenceNo = carModelData.addElement("SearchSequenceNo");
		    	String searchSequenceNo = ciCarModelSchema.getSearchSequenceNo().trim();
		    	SearchSequenceNo.addText(searchSequenceNo);
				
		    	Element LicenseNo = carModelData.addElement("LicenseNo");
		    	String licenseNo = ciCarModelSchema.getCarMark().trim();
		    	LicenseNo.addText(licenseNo);
				
		    	Element LicenseType = carModelData.addElement("LicenseType");
		    	String licenseType = ciCarModelSchema.getVehicleType().trim();
		    	LicenseType.addText(licenseType);
				
		    	Element FrameNo = carModelData.addElement("FrameNo");
		    	String frameNo = ciCarModelSchema.getRackNo().trim();
		    	FrameNo.addText(frameNo);
				
		    	Element EnginNo = carModelData.addElement("EnginNo");
		    	String enginNo = ciCarModelSchema.getEngineNo().trim();
		    	EnginNo.addText(enginNo);
				
		    	Element CarOwner = carModelData.addElement("CarOwner");
		    	String carOwner = "".trim();
		    	CarOwner.addText(carOwner);
				
		    	Element EnrollDate = carModelData.addElement("EnrollDate");
		    	String enrollDate = ciCarModelSchema.getRegisterDate().trim();
		    	EnrollDate.addText(enrollDate);
		     }
			
	    	Element BrandName = carModelData.addElement("BrandName");
	    	String brandName = prpDcarModelSchema.getModelName().trim();
	    	BrandName.addText(brandName);
			
	    	Element RBCode = carModelData.addElement("RBCode");
	    	String rBCode =prpDcarModelSchema.getModelCode().trim();
	    	RBCode.addText(rBCode);
			
	    	Element VehicleAlias = carModelData.addElement("VehicleAlias");
	    	String vehicleAlias = prpDcarModelSchema.getALIASNAME().trim();
	    	VehicleAlias.addText(vehicleAlias);

			
	    	Element VehicleBrand1 = carModelData.addElement("VehicleBrand1");
	    	String vehicleBrand1 = prpDcarModelSchema.getCarBrand().trim();
	    	VehicleBrand1.addText(vehicleBrand1);
			
	    	Element SeatCount = carModelData.addElement("SeatCount");
	    	String seatCount = prpDcarModelSchema.getSeatCount().trim();
	    	SeatCount.addText(seatCount);
	    	DecimalFormat decimalFormat =new DecimalFormat("0");
			
	    	if("".equals(prpDcarModelSchema.getCompleteKerbMass().trim())){
	    		Element VehicleWeight = carModelData.addElement("VehicleWeight");
	        	String vehicleWeight = "";
	        	VehicleWeight.addText(vehicleWeight);
	    	}else{
	    	Element VehicleWeight = carModelData.addElement("VehicleWeight");
	    	String vehicleWeight = decimalFormat.format(Double.parseDouble(prpDcarModelSchema.getCompleteKerbMass().trim()));
	    	VehicleWeight.addText(vehicleWeight);
	    	}
	    	
			
	    	if("".equals( prpDcarModelSchema.getTonCount().trim())){
	    	Element VehicleTonnage = carModelData.addElement("VehicleTonnage");
	    	String vehicleTonnage ="0";
	    	VehicleTonnage.addText(vehicleTonnage);
	    	}else{
	    		Element VehicleTonnage = carModelData.addElement("VehicleTonnage");
	        	String vehicleTonnage =decimalFormat.format(Double.parseDouble(prpDcarModelSchema.getTonCount().trim()));
	        	VehicleTonnage.addText(vehicleTonnage);
	    	}
			
	    	if("".equals(prpDcarModelSchema.getExHaustScale().trim())){
	    		Element ExhaustCapacity = carModelData.addElement("ExhaustCapacity");
		    	String exhaustCapacity = "0";
		    	ExhaustCapacity.addText(exhaustCapacity);
	    	}else{
	    		Element ExhaustCapacity = carModelData.addElement("ExhaustCapacity");
		    	String exhaustCapacity = decimalFormat.format(Double.parseDouble(prpDcarModelSchema.getExHaustScale().trim())*1000);
		    	ExhaustCapacity.addText(exhaustCapacity);
	    	}
			
	    	Element CarKindCode = carModelData.addElement("CarKindCode");
	    	String carKindCode = (String) paramMap.get("CarKindCode");
	    	CarKindCode.addText(carKindCode);








			
	    	Element CompanyName = carModelData.addElement("CompanyName");
	    	String companyName = prpDcarModelSchema.getFactory().trim();
	    	CompanyName.addText(companyName);
			
	    	Element ColorCode = carModelData.addElement("ColorCode");
	    	String colorCode = "".trim();
	    	ColorCode.addText(colorCode);
			
	    	Element LicenseColor = carModelData.addElement("LicenseColor");
	    	String licenseColor = "".trim();
	    	LicenseColor.addText(licenseColor);
			
	    	Element UseType = carModelData.addElement("UseType");
	    	String useType = "".trim();
	    	UseType.addText(useType);
	    	
			
	    	Element VehicleType = carModelData.addElement("VehicleType");
	    	String vehicleType = ciCarModelSchema.getVehicleStyleDesc().trim();
	    	VehicleType.addText(vehicleType);
	    	
			
	    	Element MakeDate = carModelData.addElement("MakeDate");
	    	String makeDate = prpDcarModelSchema.getValidDate().trim();
	    	MakeDate.addText(makeDate);
			
	    	if("01".equals(comcode.substring(0, 2))||"07".equals(comcode.substring(0, 2))){
	    	Element VehicleStyleDesc = carModelData.addElement("VehicleStyleDesc");
	    	String vehicleStyleDesc = prpDcarModelSchema.getCarSeriesName().trim();
	    	VehicleStyleDesc.addText(vehicleStyleDesc);
	    	}else if("08".equals(comcode.substring(0, 2))){
	    		Element VehicleStyleDesc = carModelData.addElement("VehicleStyleDesc");
	        	String vehicleStyleDesc = prpDcarModelSchema.getRemark().trim();
	        	VehicleStyleDesc.addText(vehicleStyleDesc);
	    	}
			
	    	Element ImportFlag = carModelData.addElement("ImportFlag");
	    	String importFlag = "".trim();
	    	ImportFlag.addText(importFlag);
			
	    	Element MarketDate = carModelData.addElement("MarketDate");
	    	String marketDate = prpDcarModelSchema.getCarYear().trim();
	    	MarketDate.addText(marketDate);
			
	    	Element TransmissionType = carModelData.addElement("TransmissionType");
	    	String transmissionType = prpDcarModelSchema.getTransmissionType().trim();
	    	TransmissionType.addText(transmissionType);
			
	    	Element ABSFlag = carModelData.addElement("ABSFlag");
	    	String aBSFlag = prpDcarModelSchema.getABSFlag().trim();
	    	ABSFlag.addText(aBSFlag);
	    	if("07".equals(comcode.substring(0, 2))){
			
	    	Element PurchasePrice = carModelData.addElement("PurchasePrice");
	    	String purchasePrice = prpDcarModelSchema.getPurchasePrice().trim();
	    	PurchasePrice.addText(purchasePrice);
	    	}else if("08".equals(comcode.substring(0, 2))||"01".equals(comcode.substring(0, 2))){
	    	
	    	Element PurchasePriceNoTax = carModelData.addElement("PurchasePrice");
	    	String purchasePriceNoTax = prpDcarModelSchema.getPurchasePriceNotax().trim();
	    	PurchasePriceNoTax.addText(purchasePriceNoTax);
	    	}
	    	
	    	Element ActualValue = carModelData.addElement("ActualValue");
	    	String actualValue = (String) paramMap.get("ActualValue");
	    	ActualValue.addText(actualValue);
			
	    	Element AirBagNum = carModelData.addElement("AirBagNum");
	    	String airBagNum = prpDcarModelSchema.getAirbagNum().trim();
	    	AirBagNum.addText(airBagNum);
			
	    	Element AlarmFlag = carModelData.addElement("AlarmFlag");
	    	String alarmFlag =prpDcarModelSchema.getAlarmFlag().trim();
	    	AlarmFlag.addText(alarmFlag);
			
	    	Element RefCode1 = carModelData.addElement("RefCode1");
	    	String refCode1 = prpDcarModelSchema.getRefCode2().trim();
	    	RefCode1.addText(refCode1);
			
	    	Element RefCode2 = carModelData.addElement("RefCode2");
	    	String refCode2 = prpDcarModelSchema.getRefCode2().trim();
	    	RefCode2.addText(refCode2);
	    	
	    	Element FuelType = carModelData.addElement("FuelType");
	    	String feulType = prpDcarModelSchema.getFuelType().trim();
	    	FuelType.addText(feulType);
	 }

	public String formateXml(String xmlStr) throws IOException, DocumentException{
		   String encoding = "GBK";
		   Document doc = DocumentHelper.parseText(xmlStr);

		   StringWriter writer = new StringWriter();
		   OutputFormat format = OutputFormat.createPrettyPrint();
		   format.setTrimText(false);
		   format.setEncoding(encoding);
		   format.setExpandEmptyElements(true);
		  
		   XMLWriter xmlwriter = new XMLWriter(writer, format);
		   xmlwriter.write(doc);
		   
		   return writer.toString().replaceAll("&lt;", "＜");
		   
	}
}
