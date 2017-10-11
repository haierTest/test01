package com.sp.interactive.interf;

import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;

import com.sp.indiv.ci.schema.CICarModelSchema;
import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;

/**
 * 解析由中间平台传进核心的报文
 * @author qilin
 * */

public class CarInfoQueryDecoder {

	public void decode(DbPool dbpool,Map paramMap,CICarModelSchema ciCarModelSchema,Document doc)throws Exception 
	{
		Element root = doc.getRootElement();
		parseHead(root.element("HEAD"),paramMap,ciCarModelSchema);
		parseBody(root.element("BODY"),paramMap,ciCarModelSchema);
	}

	/**
	 * 处理HEAD节点
	 * */
	private void parseHead(Element head,Map paramMap,CICarModelSchema ciCarModelSchema) {
		
		Element REQUESTTYPE = head.element("REQUESTTYPE");
		paramMap.put("requestType", REQUESTTYPE.getText());
		
		String comCode = head.elementText("COMCODE");
		ciCarModelSchema.setComCode(comCode);
		
	}

	/**
	 * 处理BODY节点
	 * @throws Exception 
	 * */
	private void parseBody(Element body, Map paramMap,CICarModelSchema ciCarModelSchema) throws Exception {
		
		String licenseNo=body.elementText("LicenseNo");
		if("".equals(licenseNo.trim())){
			throw new Exception("车牌号不能为空，如果是未上牌车，请将车牌号写为“新车”！");
		}
		
		
		if(SysConfig.getProperty("NewLicenseNoFlag").indexOf(","+licenseNo+",") > -1){
		
			licenseNo="";
		}
		String brandName=body.elementText("BrandName");
		String modelCode = body.elementText("RBCode");
		String carKindCode=body.elementText("CarKindCode");
		String enginNo=body.elementText("EnginNo");
		String enrollDate=body.elementText("EnrollDate");
		String startDate=body.elementText("StartDate");
		String newVehicleFlag=body.elementText("NewVehicleFlag");
		String ecdemicVehicleFlag=body.elementText("EcdemicVehicleFlag");
		String licenseType=body.elementText("LicenseType");
		String frameNo=body.elementText("FrameNo");
		
		String seatCount=body.elementText("SeatCount");
		String vehicleTonnage=body.elementText("VehicleTonnage");
		
		
		
		
		String vehicleStyleCode=body.elementText("VehicleStyleCode");
        String userType=body.elementText("UseType");
		String companyName=body.elementText("CompanyName");
		String vehicleAlias=body.elementText("VehicleAlias");
		ciCarModelSchema.setCarMark(licenseNo);
		ciCarModelSchema.setVenicleModel(brandName);
		paramMap.put("ModelCode", modelCode);
		paramMap.put("CarKindCode", carKindCode);
		paramMap.put("UserType", userType);
		ciCarModelSchema.setEngineNo(enginNo);
		ciCarModelSchema.setRegisterDate(enrollDate);
		ciCarModelSchema.setNewVehicleFlag(newVehicleFlag);
		ciCarModelSchema.setEcdemicVehicleFlag(ecdemicVehicleFlag);
		ciCarModelSchema.setVehicleType(licenseType);
		ciCarModelSchema.setRackNo(frameNo);
		ciCarModelSchema.setVehicleBrand(brandName);
		ciCarModelSchema.setVehicleStyleDesc(vehicleStyleCode);
		ciCarModelSchema.setMadeFactory(companyName);
		
		ciCarModelSchema.setLimitLoadPerson(seatCount);
		ciCarModelSchema.setLimitLoad(vehicleTonnage);
		
		paramMap.put("VehicleAlias", vehicleAlias);
		paramMap.put("StartDate", startDate);
	}

}
