package com.sp.indiv.ci.interf;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import com.sp.sysframework.common.util.XMLUtils;
import com.sp.indiv.ci.schema.CIQueryClaimInfoDetailSchema;
import com.sp.indiv.ci.schema.CIQueryClaimInfoSchema;

/**
 * XXXXXXX信息查询请求数据的解码器
 * 
 */
public class CIQueryClaimInfoDecoder {

	public static void main(String[] args) throws Exception {
	}

	/**
	 * 解码
	 * 
	 * @return XXXXXXX信息请求XML格式串
	 * @throws Exception
	 */
	public void decode(CIQueryClaimInfoSchema ciQueryClaimInfoSchema, String content) throws Exception {
		InputStream in = new ByteArrayInputStream(content.getBytes());
		Document document = XMLUtils.parse(in);
		processHead(XMLUtils.getChildNodeByPath(document, "/PACKET/HEAD"));
		processBody(ciQueryClaimInfoSchema, XMLUtils.getChildNodeByPath(document, "/PACKET/BODY"));
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
			throw new Exception(XMLUtils.getChildNodeValue(node, "ERROR_MESSAGE"));
		}
	}

	/**
	 * 处理BODY节
	 * 
	 * @param node
	 *            Node
	 * @throws Exception
	 */
	protected void processBody(CIQueryClaimInfoSchema ciQueryClaimInfoSchema, Node node) throws Exception {
		processBasePart(ciQueryClaimInfoSchema, XMLUtils.getChildNodeByTagName(node, "BASE_PART"));
		processBusiClaimList(ciQueryClaimInfoSchema, XMLUtils.getChildNodeByTagName(node, "BUSI_CLAIM_LIST"));
	}

	/**
	 * 处理BASE_PART节
	 * 
	 * @param node
	 *            Node
	 * @throws Exception
	 */
	protected void processBasePart(CIQueryClaimInfoSchema ciQueryClaimInfoSchema, Node node) throws Exception {
		ciQueryClaimInfoSchema.setCarMark(XMLUtils.getChildNodeValue(node, "CAR_MARK"));
		ciQueryClaimInfoSchema.setVehicleType(XMLUtils.getChildNodeValue(node, "VEHICLE_TYPE"));
		ciQueryClaimInfoSchema.setRackNo(XMLUtils.getChildNodeValue(node, "RACK_NO"));
		ciQueryClaimInfoSchema.setEngineNo(XMLUtils.getChildNodeValue(node, "ENGINE_NO"));
	}

	/**
	 * 处理COVERAGE_LIST节
	 * 
	 * @param node
	 *            Node
	 */
	protected void processBusiClaimList(CIQueryClaimInfoSchema ciQueryClaimInfoSchema, Node node) throws Exception {
		Node[] nodes = XMLUtils.getChildNodesByTagName(node, "BUSI_CLAIM_DATA");
		
		for (int i = 0; i < nodes.length; i++) {
			processBusiClaimArray(ciQueryClaimInfoSchema, nodes[i]);
		}
	}

	/**
	 * 处理COVERAGE节
	 * 
	 * @param node
	 *            Node
	 * @throws Exception
	 */
	protected void processBusiClaimArray(CIQueryClaimInfoSchema ciQueryClaimInfoSchema, Node node) throws Exception {
		
		CIQueryClaimInfoDetailSchema schema = new CIQueryClaimInfoDetailSchema();
		schema.setCompanyId(XMLUtils.getChildNodeValue(node, "COMPANY_ID"));
		schema.setPolicyNo(XMLUtils.getChildNodeValue(node, "POLICY_NO"));
		schema.setStartDate(XMLUtils.getChildNodeValue(node, "START_DATE"));
		schema.setEndDate(XMLUtils.getChildNodeValue(node, "END_DATE"));
		schema.setRegisreationNo(XMLUtils.getChildNodeValue(node, "REGISREATION_NO"));
		schema.setAccidentTime(XMLUtils.getChildNodeValue(node, "ACCIDENT_TIME"));
		schema.setEndCaseTime(XMLUtils.getChildNodeValue(node, "ENDCASE_TIME"));
		schema.setEstimate(XMLUtils.getChildNodeValue(node, "ESTIMATE"));
		schema.setClaimAmount(XMLUtils.getChildNodeValue(node, "CLAIM_AMOUNT"));
		schema.setClaimStatus(XMLUtils.getChildNodeValue(node, "CLAIM_STATUS"));
		ciQueryClaimInfoSchema.getCIQueryClaimInfoList().add(schema);
	}
}
