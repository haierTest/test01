package com.sp.indiv.ci.interf;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.sp.indiv.ci.blsvr.BLCIAssemblePayNo;
import com.sp.sysframework.common.util.XMLUtils;

/**
 * 车船税汇总结果查询请求数据的解码器
 * 
 */
public class CarShipTaxCheckQueryDecoder {

	public static void main(String[] args) throws Exception {}

	/**
	 * 解码
	 * 
	 * @return 车船税汇总结果查询XML格式串
	 * @throws Exception
	 */
	public void decode(BLCIAssemblePayNo blCIAssemblePayNo ,String content) throws Exception {
		InputStream in = new ByteArrayInputStream(content.getBytes());
		Document document = XMLUtils.parse(in);
		processHead(blCIAssemblePayNo, XMLUtils.getChildNodeByPath(document, "/PACKET/HEAD"));
		if("1".equals(blCIAssemblePayNo.getArr(0).getResponseCode())){
		processBody(blCIAssemblePayNo, XMLUtils.getChildNodeByPath(document,
				"/PACKET/BODY"));
		}
	}

	/**
	 * 处理HEAD节
	 * 
	 * @param node
	 *            Node
	 * @throws Exception
	 */
	protected void processHead(BLCIAssemblePayNo blCIAssemblePayNo, Node node) throws Exception {
		String responseCode = XMLUtils.getChildNodeValue(node, "RESPONSE_CODE");
		String errorMessage = XMLUtils.getChildNodeValue(node, "ERROR_MESSAGE");
		blCIAssemblePayNo.getArr(0).setRemark(errorMessage);
		blCIAssemblePayNo.getArr(0).setResponseCode(responseCode);
	}

	/**
	 * 处理BODY节
	 * 
	 * @param node
	 *            Node
	 * @throws Exception
	 */
	protected void processBody(BLCIAssemblePayNo blCIAssemblePayNo , Node node)
			throws Exception {
		processBasePart(blCIAssemblePayNo, XMLUtils.getChildNodeByTagName(node,
				"BASE_PART"));
	}

	/**
	 * 处理BASE_PART节
	 * 
	 * @param node
	 *            Node
	 * @throws Exception
	 */
	protected void processBasePart(BLCIAssemblePayNo blCIAssemblePayNo , Node node)
			throws Exception {
		String declareNo = XMLUtils.getChildNodeValue(node, "DECLARE_NO");
		blCIAssemblePayNo.getArr(0).setDeclareNo(declareNo);
        
	}
}
