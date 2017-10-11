package com.sp.interactive.interf;

import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;

public class ProposalInfoQueryDecoder {
	
	public void decode(Map paramMap,Document doc)throws Exception {
		Element root = doc.getRootElement();
		parseHead(root.element("HEAD"),paramMap);
		parseBody(root.element("BODY"),paramMap);
	}

	/**
	 * 处理HEAD节点
	 * */
	private void parseHead(Element head,Map paramMap) {
		
		Element REQUESTTYPE = head.element("REQUESTTYPE");
		paramMap.put("requestType", REQUESTTYPE.getText());
	}

	/**
	 * 处理BODY节点
	 * @throws Exception 
	 * */
	private void parseBody(Element body, Map paramMap) throws Exception {
		
		String PriceNo=body.elementText("PriceNo");
		String CIProposalNo=body.elementText("CIProposalNo");
		String BIProposalNo=body.elementText("BIProposalNo");
		
		paramMap.put("PriceNo", PriceNo);
		paramMap.put("CIProposalNo", CIProposalNo);
		paramMap.put("BIProposalNo", BIProposalNo);
		
	}
}
