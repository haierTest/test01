package com.sp.indiv.ci.interf;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.DecimalFormat;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import com.sp.sysframework.common.util.XMLUtils;
import com.sp.utility.string.ChgData;
import com.sp.indiv.ci.schema.CICarModelSchema;
import com.sp.indiv.ci.schema.CIVehiclePriceSchema;

/**
 * 车价查询请求数据的解码器
 * 
 */
public class CICarModelQueryDecoder {

	public static void main(String[] args) throws Exception {}

	/**
	 * 解码
	 * 
	 * @return 车价查询请求XML格式串
	 * @throws Exception
	 */
	public void decode(CICarModelSchema ciCarModelSchema ,String content) throws Exception {
		InputStream in = new ByteArrayInputStream(content.getBytes());
		Document document = XMLUtils.parse(in);
		processHead(XMLUtils.getChildNodeByPath(document, "/PACKET/HEAD"));
		processBody( ciCarModelSchema, XMLUtils.getChildNodeByPath(document,"/PACKET/BODY"));
	}

	/**
	 * 处理HEAD节
	 * 
	 * @param node
	 *            Node
	 * @throws Exception
	 */
	protected void processHead(Node node) throws Exception {
		String responseCode = XMLUtils.getChildNodeValue(node, "RESPONSE_CODE");
		if (!responseCode.equals("1")) {
			throw new Exception(XMLUtils.getChildNodeValue(node,"ERROR_MESSAGE"));
		}
	}

	/**
	 * 处理BODY节
	 * 
	 * @param node
	 *            Node
	 * @throws Exception
	 */
	protected void processBody(CICarModelSchema ciCarModelSchema, Node node)
			throws Exception {
		processBasePart(ciCarModelSchema, XMLUtils.getChildNodeByTagName(node,"BASE_PART"));
		processVehiclePriceList(ciCarModelSchema, XMLUtils.getChildNodeByTagName(node,"VEHICLE_PRICE_LIST"));
	}

	/**
	 * 处理BASE_PART节
	 * 
	 * @param node
	 *            Node
	 * @throws Exception
	 */
	protected void processBasePart(CICarModelSchema ciCarModelSchema , Node node)
			throws Exception {
		ciCarModelSchema.setSearchSequenceNo(XMLUtils.getChildNodeValue(node,"SEARCH_SEQUENCE_NO"));
		ciCarModelSchema.setCarMarkCI(XMLUtils.getChildNodeValue(node,"CAR_MARK"));
		ciCarModelSchema.setVehicleTypeCI(XMLUtils.getChildNodeValue(node,"VEHICLE_TYPE"));
		ciCarModelSchema.setRackNoCI(XMLUtils.getChildNodeValue(node,"RACK_NO"));
		ciCarModelSchema.setEngineNoCI(XMLUtils.getChildNodeValue(node,"ENGINE_NO"));
		
		String strRegisterDate = XMLUtils.getChildNodeValue(node,"VEHICLE_REGISTER_DATE");
		if(!"".equals(strRegisterDate)&&strRegisterDate.length()>=8){
	        ciCarModelSchema.setRegisterDate(strRegisterDate.substring(0, 4) + "-" + strRegisterDate.substring(4, 6) + "-"
	                + strRegisterDate.substring(6, 8));
		}
		
		
		ciCarModelSchema.setCarOwer(XMLUtils.getChildNodeValue(node,"OWNER"));
		
	}

	/**
	 * 处理COVERAGE_LIST节
	 * 
	 * @param node
	 *            Node
	 */
	protected void processVehiclePriceList(CICarModelSchema ciCarModelSchema, Node node)
    throws Exception {
		Node[] nodes = XMLUtils.getChildNodesByTagName(node, "VEHICLE_PRICE_DATA");
		
		for (int i = 0; i < nodes.length; i++) {
            processVehiclePriceArray(ciCarModelSchema, nodes[i]);
		}
	}

	/**
	 * 处理COVERAGE节
	 * 
	 * @param node
	 *            Node
	 * @throws Exception 
	 */
	protected void processVehiclePriceArray(CICarModelSchema ciCarModelSchema, Node node) throws Exception {
		
		CIVehiclePriceSchema schema = new CIVehiclePriceSchema();
		schema.setVehicleCode(XMLUtils.getChildNodeValue(node, "VEHICLE_CODE"));
		schema.setRVenicleName(XMLUtils.getChildNodeValue(node, "R_VEHICLE_NAME"));
		schema.setRVenicleBrand(XMLUtils.getChildNodeValue(node, "R_VEHICLE_BRAND"));
		schema.setRVehicleFamily(XMLUtils.getChildNodeValue(node, "R_VEHICLE_FAMILY"));
		schema.setRImportFlag(XMLUtils.getChildNodeValue(node, "R_IMPORT_FLAG"));
		schema.setRLimitLoadPersonNumber(XMLUtils.getChildNodeValue(node, "R_LIMIT_LOAD_PERSON"));
		schema.setRVenicleWeight(XMLUtils.getChildNodeValue(node, "R_VEHICLE_WEIGHT"));
		schema.setRVehicleTonnage(XMLUtils.getChildNodeValue(node, "R_VEHICLE_TONNAGE"));
		schema.setTransmissionType(XMLUtils.getChildNodeValue(node, "TRANSMISSION_TYPE"));
		schema.setAbsFlag(XMLUtils.getChildNodeValue(node, "ABS_FLAG"));
		schema.setAlarmFlag(XMLUtils.getChildNodeValue(node, "ALARM_FLAG"));
		schema.setAirbagNum(XMLUtils.getChildNodeValue(node, "AIRBAG_NUM"));
		schema.setVehicleDescription(XMLUtils.getChildNodeValue(node, "VEHICLE_DESCRIPTION"));
		schema.setVeniclePrice(XMLUtils.getChildNodeValue(node, "VEHICLE_PRICE"));
		schema.setRefcode1(XMLUtils.getChildNodeValue(node, "REFCODE1"));
		schema.setRefcode2(XMLUtils.getChildNodeValue(node, "REFCODE2"));
		
		String strRExhaustCapacity = XMLUtils.getChildNodeValue(node, "R_EXHAUST_CAPACITY");
		if(!"".equals(strRExhaustCapacity)){
			schema.setRExhaustCapacity(new DecimalFormat("0.0000").format(Double.parseDouble(ChgData.chgStrZero(strRExhaustCapacity))/1000));
		}
		schema.setRMarketDate(XMLUtils.getChildNodeValue(node, "R_MARKET_DATE"));
		schema.setIsPriced(XMLUtils.getChildNodeValue(node, "IS_PRICED"));
		
		schema.setRiskFlag(XMLUtils.getChildNodeValue(node, "RISK_FLAG"));
		
		
		schema.setFuelType(XMLUtils.getChildNodeValue(node, "R_FUEL_TYPE"));
		
		
		schema.setRVehicleModel(XMLUtils.getChildNodeValue(node, "R_VEHICLE_MODEL"));
		
        ciCarModelSchema.getCIVehiclePriceList().add(schema);
	}
}
