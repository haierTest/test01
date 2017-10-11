package com.sp.indiv.ci.interf;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.sp.indiv.ci.blsvr.BLCIAssemblePayNo;
import com.sp.sysframework.common.util.XMLUtils;

/**
 * ����˰���ܽ����ѯ�������ݵĽ�����
 * 
 */
public class CarShipTaxCheckQueryDecoder {

	public static void main(String[] args) throws Exception {}

	/**
	 * ����
	 * 
	 * @return ����˰���ܽ����ѯXML��ʽ��
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
	 * ����HEAD��
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
	 * ����BODY��
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
	 * ����BASE_PART��
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
