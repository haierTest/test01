/***************************************************************************************
 * DESC      :20040接口发送报文解析
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

import com.sp.prpall.blsvr.tb.BLProposal;
import com.sp.utility.error.UserException;


public class IdentityVerificationDecoder {
	/**
	 * 返回报文的编译
	 * @param doc
	 * @param paramMap
	 * @param blProposalMap
	 * @throws Exception 
	 * @throws Exception
	 */
	public void decode(Document doc, Map paramMap,BLProposal blProposal)throws Exception{
		
		Element root = doc.getRootElement();
		parseHead(root.element("HEAD"), paramMap, blProposal);
		parseBody(root.element("BODY"), paramMap, blProposal);
	
	}
	public void parseHead(Element head, Map paramMap, BLProposal blProposal)throws Exception{
		Element Trans_Type = head.element("TRANSTYPE");
		paramMap.put("TRANSTYPE", Trans_Type.getText());
	}
	public void parseBody(Element Body, Map paramMap, BLProposal blProposal)throws Exception{
		 String ProposalNo   = Body.elementText("PROPOSALNO");      
		 blProposal.getData(ProposalNo);
	}
}
