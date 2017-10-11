/***************************************************************************************
 * DESC      :20040接口返回报文封装
 * Author    :zhangxiayu
 * CreateDate:2015-02-02
 ***************************************************************************************/
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
import com.sp.utility.error.UserException;


public class IdentityVerificationEncoder {
	/**
	 * 返回报文的编译
	 * @param doc
	 * @param paramMap
	 * @param blProposalMap
	 * @throws Exception 
	 * @throws Exception
	 */
	public String encode(Map paramMap, Map blProposalMap) throws Exception{
		
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("PACKET");
		root.addAttribute("type", "RESPONSE");
		root.addAttribute("version", "1.0");

		addHead(root, paramMap, blProposalMap);
		addBody(root, paramMap, blProposalMap);
		return formateXml(doc.asXML());
		
	}
	
	
	public String encodeException(Map paramMap, Map blProposalMap,Exception e) throws IOException, DocumentException, UserException{
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("PACKET");
		root.addAttribute("type", "RESPONSE");
		root.addAttribute("version", "1.0");
		
		Element head = root.addElement("HEAD");
		Element transType = head.addElement("TRANSTYPE");
		String requestType = (String) paramMap.get("TRANSTYPE");
		transType.addText(requestType);
		
		 Element ResponseCode = head.addElement("RESPONSECODE");
		 ResponseCode.addText("100000");
		 Element ErrorMessage = head.addElement("ERRORMESSAGE");
		 ErrorMessage.addText(e.getMessage());
		 return formateXml(doc.asXML());
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
	/**
	 * 报文头部信息
	 * @param root
	 * @param paramMap
	 * @param blProposalMap
	 * @throws Exception
	 */
	public void addHead(Element root, Map paramMap, Map blProposalMap)throws Exception{
		Element head = root.addElement("HEAD");
		Element transType = head.addElement("TRANSTYPE");
		String requestType = (String) paramMap.get("TRANSTYPE");
		transType.addText(requestType);
		
		 Element ResponseCode = head.addElement("RESPONSECODE");
		 ResponseCode.addText("000000");
		 Element ErrorMessage = head.addElement("ERRORMESSAGE");
		 ErrorMessage.addText("成功");
	}
	/**
	 * 报文体信息
	 * @param root
	 * @param paramMap
	 * @param blProposalMap
	 * @throws Exception
	 */
	public void addBody(Element root, Map paramMap, Map blProposalMap)throws Exception{
		Element  body = root.addElement("BODY");
		}
}
