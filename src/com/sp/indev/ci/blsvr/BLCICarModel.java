package com.sp.indiv.ci.blsvr;

import java.util.*;
import com.sp.utiall.blsvr.BLPrpDcarModel;
import com.sp.utiall.schema.PrpDcarModelSchema;
import com.sp.utility.error.UserException;
import com.sp.indiv.ci.interf.CICarModelQueryDecoder;
import com.sp.indiv.ci.interf.CICarModelQueryEncoder;
import com.sp.indiv.ci.interf.EbaoProxy;
import com.sp.indiv.ci.schema.CICarModelSchema;
import com.sp.indiv.ci.schema.CIVehiclePriceSchema;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BLCICarModel {
	Log logger = LogFactory.getLog(getClass());

	public CICarModelSchema query(CICarModelSchema ciCarModelSchema, String comcode) throws UserException, Exception {
		String requestXML = new CICarModelQueryEncoder().encode(ciCarModelSchema, comcode);
		
		logger.info("车价查询requestXML=="+requestXML);
		
		String responseXML = EbaoProxy.getInstance().request(requestXML, comcode);
		String responseXMLreplace = responseXML.replaceFirst("GBK", "GB2312");
		
		logger.info("车价查询responseXML=="+responseXMLreplace);
		
		
		if("1".equals(ciCarModelSchema.getCCSITestFlag())){
			ciCarModelSchema.getExchangeXMLSchema().setRequestPlatformXML(requestXML);
			ciCarModelSchema.getExchangeXMLSchema().setPlatformResponseXML(responseXML);
			return ciCarModelSchema;
		}
		
		
		new CICarModelQueryDecoder().decode(ciCarModelSchema, responseXMLreplace);
		return ciCarModelSchema;
	}

	public BLPrpDcarModel generateBLPrpDcarModel(CICarModelSchema ciCarModelSchema) throws UserException, Exception {
		BLPrpDcarModel blPrpDcarModel = new BLPrpDcarModel();
		List ciVehiclePriceList = ciCarModelSchema.getCIVehiclePriceList();
		for (int i = 0; i < ciVehiclePriceList.size(); i++) {
			CIVehiclePriceSchema ciVehiclePriceSchema = (CIVehiclePriceSchema) ciVehiclePriceList.get(i);
			PrpDcarModelSchema iPrpDcarModelSchema = new PrpDcarModelSchema();
			iPrpDcarModelSchema.setModelCode(ciVehiclePriceSchema.getVehicleCode());
			iPrpDcarModelSchema.setModelName(ciVehiclePriceSchema.getRVenicleName());
			iPrpDcarModelSchema.setCompleteKerbMass(ciVehiclePriceSchema.getRVenicleWeight());
			iPrpDcarModelSchema.setSeatCount(ciVehiclePriceSchema.getRLimitLoadPersonNumber());
			iPrpDcarModelSchema.setSeatCountLB(ciVehiclePriceSchema.getRLimitLoadPersonNumber());
			iPrpDcarModelSchema.setTonCount(ciVehiclePriceSchema.getRVehicleTonnage());
			iPrpDcarModelSchema.setTonCountLB(ciVehiclePriceSchema.getRVehicleTonnage());
			iPrpDcarModelSchema.setCarYear(ciVehiclePriceSchema.getRMarketDate());
			iPrpDcarModelSchema.setPurchasePrice(ciVehiclePriceSchema.getVeniclePrice());
			
			iPrpDcarModelSchema.setPurchasePriceNotax(ciVehiclePriceSchema.getVeniclePrice());
			
			iPrpDcarModelSchema.setPurchasePriceLB(ciVehiclePriceSchema.getVeniclePrice());
			iPrpDcarModelSchema.setKindredPrice(ciVehiclePriceSchema.getVeniclePrice());
			iPrpDcarModelSchema.setKindredPriceNotax(ciVehiclePriceSchema.getVeniclePrice());
			iPrpDcarModelSchema.setExHaustScale(ciVehiclePriceSchema.getRExhaustCapacity());
			iPrpDcarModelSchema.setExhaustScaleLB(ciVehiclePriceSchema.getRExhaustCapacity());
			iPrpDcarModelSchema.setCarBrand(ciVehiclePriceSchema.getRVenicleBrand());
			iPrpDcarModelSchema.setAlarmFlag(ciVehiclePriceSchema.getAlarmFlag());
			iPrpDcarModelSchema.setCarSeriesName(ciVehiclePriceSchema.getVehicleDescription());
			iPrpDcarModelSchema.setRemark(ciVehiclePriceSchema.getVehicleDescription());
			
			iPrpDcarModelSchema.setALIASNAME(ciVehiclePriceSchema.getRVehicleFamily());
			
			if("国产".equals(ciVehiclePriceSchema.getRImportFlag())){
				iPrpDcarModelSchema.setCarStyle("B");
			} else if("进口".equals(ciVehiclePriceSchema.getRImportFlag())){
				iPrpDcarModelSchema.setCarStyle("A");
			}else if("合资".equals(ciVehiclePriceSchema.getRImportFlag())){
				iPrpDcarModelSchema.setCarStyle("C");
			}
			
			iPrpDcarModelSchema.setRiskFlag(ciVehiclePriceSchema.getRiskFlag());
			
			
			iPrpDcarModelSchema.setFuelType(ciVehiclePriceSchema.getFuelType());
			
			
			iPrpDcarModelSchema.setRVehicleModel(ciVehiclePriceSchema.getRVehicleModel());
			
			
			iPrpDcarModelSchema.setEnrollDate(ciCarModelSchema.getRegisterDate());
			iPrpDcarModelSchema.setEngineNo(ciCarModelSchema.getEngineNoCI());
			iPrpDcarModelSchema.setFrameNo(ciCarModelSchema.getRackNoCI());
			
			blPrpDcarModel.setArr(iPrpDcarModelSchema);
		}
		return blPrpDcarModel;

	}
}
