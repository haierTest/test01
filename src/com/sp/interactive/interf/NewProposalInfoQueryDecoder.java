package com.sp.interactive.interf;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.dom4j.Element;
import org.w3c.dom.Node;


import com.sp.indiv.bi.pub.TransCode;
import com.sp.indiv.bi.schema.CIInsureCarModelSchema;
import com.sp.indiv.bj.NewCarRecordQueryEncoder;
import com.sp.indiv.ci.interf.ProposalQueryEncoder;
import com.sp.prpall.blsvr.cb.BLTPubVehicle;
import com.sp.prpall.blsvr.tb.BLProposal;
import com.sp.prpall.schema.NewCarRecordSchema;
import com.sp.sysframework.common.util.StringUtils;
import com.sp.sysframework.common.util.XMLUtils;
import com.sp.sysframework.reference.AppConfig;
import com.sp.utility.UtiPower;
import com.sp.utility.database.DbPool;


public class NewProposalInfoQueryDecoder {
	private static Logger logger = Logger.getLogger(ProposalQueryEncoder.class);
	 String errorMessage = "";
	 /**
	     * 解码
	     * @return 风XXXXXXX查询请求XML格式串
	     * @throws Exception
	     */
	 public void decode(
			   BLProposal blProposal, 
			   String content, 
			   String editType, 
			   String proposalNo) 
throws Exception 
{
	
	logger.info("[风XXXXXXX查询返回报文]:"+content);
	System.out.println("[风XXXXXXX查询返回报文]:"+content);
	InputStream in = new ByteArrayInputStream(content.getBytes());
	Document document = XMLUtils.parse(in);
    if(!"1".equals(blProposal.getBLPrpTmain().getArr(0).getCCSITestFlag())){
	processHead(XMLUtils.getChildNodeByPath(document, "/Packet/Head"));
    }
	processBody(blProposal, XMLUtils.getChildNodeByPath(document, "/Packet/Body"));
 }
/**
 * 处理HEAD节
 * @param node
 *            Node
 * @throws Exception
 */
protected void processHead(Node node) throws Exception {
	String responseCode = XMLUtils.getChildNodeValue(node, "ResponseCode");
	errorMessage = XMLUtils.getChildNodeValue(node, "ErrorMessage");
	if (!responseCode.equals("1")) {
		throw new Exception(errorMessage);
	}
}

/**
 * 处理BODY节
 * @param node
 *            Node
 * @throws Exception
 */
protected void processBody(BLProposal blProposal, Node node) throws Exception {
	
	Node[] nodes = XMLUtils.getChildNodesByTagName(node, "CoveragePremiumItem");
	for(int i =0;i<nodes.length;i++){
	processCoveragePremiumItem(blProposal, nodes[i], i + 1);
	}  
}
protected void processCoveragePremiumItem(BLProposal blProposal, Node node, int Serialno) throws Exception {
	String strCoverageCode = TransCode.transCoverageCodeFG(XMLUtils.getChildNodeValue(node, "CoverageCode"));  
	Node[] nodes = XMLUtils.getChildNodesByTagName(node, "PureRiskPremiumItem");  
	for(int i=0;i < nodes.length;i++){
		String strModelCode  = "";
		String strPureRiskPremium  = "";
		String strPureRiskPremiumFlag="";
		strModelCode  = XMLUtils.getChildNodeValue(nodes[i], "ModelCode");
		strPureRiskPremium = XMLUtils.getChildNodeValue(nodes[i], "PureRiskPremium");
		strPureRiskPremiumFlag=XMLUtils.getChildNodeValue(nodes[i], "PureRiskPremiumFlag");
		CIInsureCarModelSchema ciInsureCarModelSchema = new CIInsureCarModelSchema();
        ciInsureCarModelSchema.setCoverageCode(strCoverageCode);
		ciInsureCarModelSchema.setPureRiskPremium(strPureRiskPremium);
		ciInsureCarModelSchema.setPureRiskPremiumFlag(strPureRiskPremiumFlag);
		blProposal.getBlCIInsureCarModel().setArr(ciInsureCarModelSchema);
	}
}
}