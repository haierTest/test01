package com.sp.indiv.ci.interf;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.w3c.dom.Document;
import org.w3c.dom.Node;


import com.sp.sysframework.common.util.XMLUtils;
import com.sp.indiv.ci.blsvr.BLCICheckCarShipTax;
import com.sp.indiv.ci.blsvr.BLCICheckCarShipTaxDetail;
import com.sp.indiv.ci.schema.CICheckCarShipTaxDetailSchema;
import com.sp.indiv.ci.schema.CICheckCarShipTaxSchema;
import com.sp.utility.string.ChgDate;
import com.sp.utility.UtiPower;
/**
 * 车船税对账查询请求数据的解码器
 * 
 */
public class CarShipTaxCheckDecoder {

	public static void main(String[] args) throws Exception {}

	/**
	 * 解码
	 * 
	 * @return 车船税对账请求XML格式串
	 * @throws Exception
	 */
	public void decode(CarShipTaxCheck carShipTaxCheck ,String content) throws Exception {
		InputStream in = new ByteArrayInputStream(content.getBytes());
		Document document = XMLUtils.parse(in);

		processHead(XMLUtils.getChildNodeByPath(document, "/PACKET/HEAD"));
		processBody(carShipTaxCheck, XMLUtils.getChildNodeByPath(document,
				"/PACKET/BODY"));
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
			String errorMessage = XMLUtils.getChildNodeValue(node,
					"ERROR_MESSAGE");
			throw new Exception(errorMessage);
		}
	}

	/**
	 * 处理BODY节
	 * 
	 * @param node
	 *            Node
	 * @throws Exception
	 */
	protected void processBody(CarShipTaxCheck carShipTaxCheck , Node node)
			throws Exception {
		processBasePart(carShipTaxCheck.getBlCICheckCarShipTax(), XMLUtils.getChildNodeByTagName(node,
				"BASE_PART"));
		processPayNoArrayList(carShipTaxCheck.getBlCICheckCarShipTaxDetail(), XMLUtils.getChildNodeByTagName(node,
				"PAY_NO_ARRAY_LIST"));
	}

	/**
	 * 处理BASE_PART节
	 * 
	 * @param node
	 *            Node
	 * @throws Exception
	 */
	protected void processBasePart(BLCICheckCarShipTax blCICheckCarShipTax , Node node)
			throws Exception {
		
		String totalPage = XMLUtils.getChildNodeValue(node,
				"TOTAL_PAGE");
		String totalPayNo = XMLUtils.getChildNodeValue(node, "TOTAL_PAY_NO");
		String pageNum = XMLUtils.getChildNodeValue(node, "PAGE_NUMBER");
		String sheetPayNo = XMLUtils.getChildNodeValue(node, "SHEET_PAY_NO");
		String totalTax = XMLUtils.getChildNodeValue(node, "TOTAL_TAX");
		
        CICheckCarShipTaxSchema schema = new CICheckCarShipTaxSchema();
        schema.setTotalPage(totalPage);
        schema.setTTotalPayNo(totalPayNo);
        schema.setPageNum(pageNum);
        schema.setSheetPayNo(sheetPayNo);
        schema.setTTotalTax(totalTax);
		
        blCICheckCarShipTax.setArr(schema);
	}



	/**
	 * 处理COVERAGE_LIST节
	 * 
	 * @param node
	 *            Node
	 */
	protected void processPayNoArrayList(BLCICheckCarShipTaxDetail blCICheckCarShipTaxDetail, Node node)
    throws Exception {
		Node[] nodes = XMLUtils.getChildNodesByTagName(node, "PAY_NO_ARRAY");
		
		for (int i = 0; i < nodes.length; i++) {
            processPayNoArray(blCICheckCarShipTaxDetail, nodes[i]);
		}
	}

	/**
	 * 处理COVERAGE节
	 * 
	 * @param node
	 *            Node
	 * @throws Exception 
	 */
	protected void processPayNoArray(BLCICheckCarShipTaxDetail blCICheckCarShipTaxDetail, Node node) 
    throws Exception {
		
		String payNo = XMLUtils.getChildNodeValue(node, "PAY_NO");
		String policyNo = XMLUtils.getChildNodeValue(node, "POLICY_NO");
		String payTax = XMLUtils.getChildNodeValue(node,"PAY_TAX");
		String lateFee = XMLUtils.getChildNodeValue(node, "LATE_FEE");
		
        CICheckCarShipTaxDetailSchema schema = new CICheckCarShipTaxDetailSchema();
        schema.setTPayNo(payNo);
        schema.setMPolicyNo("无");
        schema.setTPolicyNo(policyNo);
        schema.setTPayTax(payTax);
        schema.setTLateFee(lateFee);
        String currentDate=new ChgDate().getCurrentTime("yyyy-MM-dd");
        
        if(new UtiPower().CarShipTaxCheckQueryBJ("01",currentDate)){
            String agent = XMLUtils.getChildNodeValue(node, "AGENT");
            schema.setExtendChar1(agent);
        }
        
        blCICheckCarShipTaxDetail.setArr(schema);
	}
}
