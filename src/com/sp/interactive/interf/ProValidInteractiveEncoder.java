package com.sp.interactive.interf;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

/**
 * XX确认封装报文
 * @author qilin
 * */

public class ProValidInteractiveEncoder {
	
	 public String encode(Map paramMap) throws Exception{
		 Document doc = DocumentHelper.createDocument();
			Element root = doc.addElement("PACKET");
			root.addAttribute("type", "RESPONSE");
			root.addAttribute("version", "1.0");
			
			addHead(root,paramMap);
			addBody(root,paramMap);
			return formateXml(doc.asXML());
		 
	 }

	private void addHead(Element root,Map paramMap) {
		Element head = root.addElement("HEAD");
		Element REQUESTTYPE = head.addElement("REQUESTTYPE");
		String requestType = ((String) paramMap.get("REQUESTTYPE")).trim();
		REQUESTTYPE.addText(requestType);
		Element responseCode = head.addElement("RESPONSECODE");
		Element responseMessage = head.addElement("RESPONSEMESSAGE");
		responseCode.addText("000000");
		responseMessage.addText("成功");
		
	}

	private void addBody(Element root,Map paramMap) {
		Element  body = root.addElement("BODY");
		
		Element ProposalNoBI = body.addElement("BIProposalNo");
		String proposalNoBI = (String) paramMap.get("proposalNoBI");
		ProposalNoBI.addText(proposalNoBI);
		
		Element UnderWriteFlagBI = body.addElement("BIUndwrtFlag");
		String underWriteFlagBI = (String) paramMap.get("UnderWriteFlagBI");
		UnderWriteFlagBI.addText(underWriteFlagBI);
		
		Element UndwrtMessageBI = body.addElement("BIUndwrtMessage");
		String undwrtMessageBI = (String) paramMap.get("UndwrtMessageBI");
		UndwrtMessageBI.addText(undwrtMessageBI);
		
		Element ProposalNoCI = body.addElement("CIProposalNo");
		String proposalNoCI = (String) paramMap.get("proposalNoCI");
		ProposalNoCI.addText(proposalNoCI);
		
		Element UnderWriteFlagCI = body.addElement("CIUndwrtFlag");
		String underWriteFlagCI = (String) paramMap.get("UnderWriteFlagCI");
		UnderWriteFlagCI.addText(underWriteFlagCI);
		
		Element UndwrtMessageCI = body.addElement("CIUndwrtMessage");
		String undwrtMessageCI = (String) paramMap.get("UndwrtMessageCI");
		UndwrtMessageCI.addText(undwrtMessageCI);
		
	}
	
	public String encodeException(Map paramMap, Map blProposalMap,Exception e) throws IOException, DocumentException{
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("PACKET");
		root.addAttribute("type", "RESPONSE");
		root.addAttribute("version", "1.0");
		
		addErrorHead(root,paramMap);
		addErrorBody(root,paramMap);
		return formateXml(doc.asXML());

	}
	
	
	private void addErrorHead(Element root, Map paramMap) {
		Element head = root.addElement("HEAD");



		
		Element responseCode = head.addElement("RESPONSECODE");
		Element responseMessage = head.addElement("RESPONSEMESSAGE");
		
		responseCode.addText("100000");
		responseMessage.addText("核XXXXX不通过");
		
	}

	private void addErrorBody(Element root, Map paramMap) {
		Element  body = root.addElement("BODY");
		
		Element ProposalNoBI = body.addElement("BIProposalNo");
		String proposalNoBI = (String) paramMap.get("proposalNoBI");
		ProposalNoBI.addText(proposalNoBI);
		
		Element UnderWriteFlagBI = body.addElement("BIUndwrtFlag");
		String underWriteFlagBI = (String) paramMap.get("UndwrtFlagBI");
		UnderWriteFlagBI.addText(underWriteFlagBI);
		
		Element UndwrtMessageBI = body.addElement("BIUndwrtMessage");
		String undwrtMessageBI = (String) paramMap.get("UndwrtMessageBI");
		UndwrtMessageBI.addText(undwrtMessageBI);
		
		Element ProposalNoCI = body.addElement("CIProposalNo");
		String proposalNoCI = (String) paramMap.get("proposalNoCI");
		ProposalNoCI.addText(proposalNoCI);
		
		Element UnderWriteFlagCI = body.addElement("CIUndwrtFlag");
		String underWriteFlagCI = (String) paramMap.get("UndwrtFlagCI");
		UnderWriteFlagCI.addText(underWriteFlagCI);
		
		Element UndwrtMessageCI = body.addElement("CIUndwrtMessage");
		String undwrtMessageCI = (String) paramMap.get("UndwrtMessageCI");
		UndwrtMessageCI.addText(undwrtMessageCI);
		
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
