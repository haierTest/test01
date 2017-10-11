/***************************************************************************************
 * DESC      :20041接口返回报文封装
 * Author    :zhangxiayu
 * CreateDate:2015-02-02
 ***************************************************************************************/
package com.sp.interactive.interf;

import java.io.StringWriter;
import java.util.Map;
import org.dom4j.Document;
import org.dom4j.Element;

import com.sp.indiv.ci.blsvr.BLCIIdentifyInfo;
import com.sp.prpall.blsvr.tb.BLProposal;
import com.sp.prpall.blsvr.tb.BLPrpTmainSub;

public class IdentityCardStorageDecoder {
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
		 String RiskCode   = Body.elementText("RISKCODE");     
		 String IssueCode   = Body.elementText("ISSUECODE"); 
		 BLCIIdentifyInfo blCIIdentifyInfo = new BLCIIdentifyInfo();
		 blCIIdentifyInfo.getData(ProposalNo); 
		 BLPrpTmainSub  blPrpTmainSub = new BLPrpTmainSub();
		 if(blCIIdentifyInfo.getSize()==0){
			 throw new Exception("请检查数据是否正确，单号无效！！");
		 }
		for(int i=0;i<blCIIdentifyInfo.getSize();i++){
			 blCIIdentifyInfo.getArr(i).setIssueCode(IssueCode);
		 }
		 blCIIdentifyInfo.update();
		 String Proposalno=blPrpTmainSub.queryProposalNo(RiskCode, ProposalNo);
		 BLCIIdentifyInfo blCIIdentifyInfo1 = new BLCIIdentifyInfo();
		 if(!"".equals(Proposalno)&&Proposalno!=null){
		 blCIIdentifyInfo1.getData(Proposalno);
		 for(int i=0;i<blCIIdentifyInfo1.getSize();i++){
			 blCIIdentifyInfo1.getArr(i).setIssueCode(IssueCode);
		 }
		 blCIIdentifyInfo1.update();
		 }
	}
}

