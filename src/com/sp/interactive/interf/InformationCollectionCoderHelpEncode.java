package com.sp.interactive.interf;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.sp.prpall.blsvr.tb.BLProposal;
import com.sp.prpall.schema.PrpTengageSchema;
import com.sp.utility.SysConfig;
import com.sunshine.ruleapp.bomPreUwrt.BomRuleResult;

public class InformationCollectionCoderHelpEncode {
	    public String encode(Map paramMap,List list) throws Exception{
			
			Document doc = (Document) DocumentHelper.createDocument();
			Element root = doc.addElement("PACKET");
			root.addAttribute("type", "RESPONSE");
			root.addAttribute("version", "1.0");
			addHead(root, paramMap,list);
			addBody(root, paramMap,list);
			return formateXml(doc.asXML());
			
		}
	    public String encodeException(Map paramMap,List list) throws Exception{
			
				Document doc = (Document) DocumentHelper.createDocument();
				Element root = doc.addElement("PACKET");
				root.addAttribute("type", "RESPONSE");
				root.addAttribute("version", "1.0");
				addHead(root, paramMap,list);
				
				return formateXml(doc.asXML());
				
			}
	    public void addHead(Element root, Map paramMap,List list)throws Exception{
	    	BLProposal blProposal=(BLProposal)list.get(0);
	    	Element head = root.addElement("HEAD");
	    	
	    	Element REQUESTTYPE = head.addElement("REQUESTTYPE");
	    	String requestType = (String) paramMap.get("requestType");
	    	REQUESTTYPE.addText(requestType);
	    	
	    	Element responseCode = head.addElement("RESPONSECODE");
	    	Element responseMessage = head.addElement("RESPONSEMESSAGE");
	    	
	    	if(paramMap.get("Error_Code") != null && !paramMap.get("Error_Code").equals("000000") ){
	    		responseMessage.addText((String)paramMap.get("Error_Message") );
	    		responseCode.addText((String)paramMap.get("Error_Code"));
	    	}else{
	    		responseMessage.addText("成功");
	    		responseCode.addText("000000");
	    	}
	    	
	    }
	    public void addBody(Element root, Map paramMap,List list)throws Exception{
	    	BLProposal blProposal=(BLProposal)list.get(0);
	    	String OperateSiteBC = (String) paramMap.get("OperateSiteBC");
			Element  body = root.addElement("BODY");
			
			Element INFOTRANSNO = body.addElement("INFOTRANSNO");
			String InfoTransNo = (String) paramMap.get("InfoTransNo");
			INFOTRANSNO.addText(InfoTransNo);
			
			Element OPERATESITE = body.addElement("OPERATESITE");
			OPERATESITE.addText(blProposal.getBLPrpTmain().getArr(0).getOperateSite());
			Element isLegal = body.addElement("ISCOMPLIANCE");
		    isLegal.addText((String)paramMap.get("isLegal"));
		    for(int i=0;i<list.size();i++){
		    	   addEngage(body,(BLProposal)list.get(i),paramMap);
		    }
		    
			addUndwrt(body, blProposal, paramMap);
			
			
			addQuality(body, blProposal, paramMap);
			
		    /*
		    
		    BLPrpRuleUndwrtInfo blPrpRuleUndwrtInfo=new BLPrpRuleUndwrtInfo();
		    Element UNDWRTLIST=body.addElement("UNDWRT_LIST");
            for(int i=1;i<=blPrpRuleUndwrtInfo.getMaxSerialNo(InfoTransNo);i++){
            	blPrpRuleUndwrtInfo.getData(InfoTransNo, Integer.toString(i));
            	if(blPrpRuleUndwrtInfo.getSize()>0){
            		if(blPrpRuleUndwrtInfo.getArr(0)!=null&&("3").equals(blPrpRuleUndwrtInfo.getArr(0).getFlag().trim())){
            			Element UNDWRTDATA=UNDWRTLIST.addElement("UNDWRT_DATA");
            			Element EBISUNDWRT = UNDWRTDATA.addElement("BISUNDWRT");
            			if(paramMap.get("isUndwrt")!=null){
            			boolean bIsUndwrt=(Boolean) paramMap.get("isUndwrt");
            			String strIsUndwrt=String.valueOf(bIsUndwrt);
            			EBISUNDWRT.addText(strIsUndwrt!= null ?strIsUndwrt: "");
            			
            			Element UNDWRTINFO = UNDWRTDATA.addElement("UNDWRTINFO");
            			UNDWRTINFO.addText(blPrpRuleUndwrtInfo.getArr(0).getUndwrtInfo()!= null ?blPrpRuleUndwrtInfo.getArr(0).getUndwrtInfo() : "");
            			
            			Element RISKCODE = UNDWRTDATA.addElement("RISKCODE");
            			RISKCODE.addText(blPrpRuleUndwrtInfo.getArr(0).getRiskCode()!= null ? blPrpRuleUndwrtInfo.getArr(0).getRiskCode() : "");
            		}
            		}
            	}
            }
            
			*/
	    }
	    
	    /**
	     * 特别约定
	     * @param body
	     * @param blProposalCI
	     * @param blProposalBI
	     * @throws 
	     * */
	    private void addEngage(Element body, BLProposal blProposal,Map paramMap) throws Exception{
			
	    	if (blProposal != null) {
	    		Element ENGAGE_LIST = body.addElement("ENGAGE_LIST");
	    		String riskCode =  blProposal.getBLPrpTmain().getArr(0).getRiskCode();
	    		ENGAGE_LIST.addAttribute("RISKCODE", riskCode);
	    		int length = blProposal.getBLPrpTengage().getSize();
	    		if(length>0){
	    		for  (int  i=0;i<length;i++){
	    		PrpTengageSchema  prpTengageSchema=blProposal.getBLPrpTengage().getArr(i);
	    		
	    		Element engageData = ENGAGE_LIST.addElement("ENGAGE_DATA");
	    		Element ClauseCode = engageData.addElement("CLAUSECODE");
				Element Serialno = engageData.addElement("SERIALNO");
				Element Lineno = engageData.addElement("LINENO");
				Element TitleFlag = engageData.addElement("TITLEFLAG");
				Element ClauseName = engageData.addElement("CLAUSENAME");
				Element Content = engageData.addElement("CONTENT");
				Element RiskFlag = engageData.addElement("RISKFLAG");
				
				Element RiskCode = engageData.addElement("RISKCODE");
				RiskCode.addText(riskCode);
				

				ClauseCode.addText(prpTengageSchema.getClauseCode());
				Serialno.addText(Integer.parseInt(prpTengageSchema.getSerialNo())+1+"");
				Lineno.addText(prpTengageSchema.getLineNo());
				TitleFlag.addText(prpTengageSchema.getTitleFlag());
				if("0".equals(prpTengageSchema.getTitleFlag())){
					ClauseName.addText(prpTengageSchema.getClauses());
					Content.addText("");
				}else{
					ClauseName.addText("");
					Content.addText(prpTengageSchema.getClauses());
				}
				
				    if("0507".equals(riskCode)){
						RiskFlag.addText("2");
				    }else{
				    	RiskFlag.addText("1");
				    }
				    
	    		}
	    		
	    		}
	    			
	    		
	    	}
		}
	    
	    
	    


























































































	/**
	 * 特别约定 内部信息节点
	 * @param engageData
	 * @param engageSchema
	 * @throws Exception
	 * */
			
	    private void addEngageData(Element engageData,Map paramMap)throws Exception {
			Element CLAUSECODE = engageData.addElement("CLAUSECODE");
	    	String clauseCode = (String) paramMap.get("CLAUSECODE");
	    	CLAUSECODE.addText(clauseCode);
			
	    	Element ENGAGESERIALNO = engageData.addElement("ENGAGESERIALNO");
	    	String engageSerialNo = (String) paramMap.get("ENGAGESERIALNO");
	    	ENGAGESERIALNO.addText(engageSerialNo);
			
			
	    	Element TITLEFLAG = engageData.addElement("TITLEFLAG");
	    	String TitleFlag = "";
	    	TITLEFLAG.addText(TitleFlag);
			
	    	Element TITLE = engageData.addElement("TITLE");
	    	String Title = (String) paramMap.get("TITLE");
	    	TITLE.addText(Title);
			
	    	Element CONTENT = engageData.addElement("CONTENT");
	    	String content = (String) paramMap.get("CONTENT");
	    	CONTENT.addText(content);
	    	
	    	Element LINENO = engageData.addElement("LINENO");
	    	String lineno = (String) paramMap.get("LINENO");
	    	LINENO.addText(lineno);
	    	
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
		 * 转人工
		 * 
		 * @param body
		 * @param blProposalCI
		 * @param blProposalBI
		 * @throws
		 * */
		public void addUndwrt(Element body, BLProposal blProposal, Map paramMap) throws Exception {
			Element UNDWRT_LIST = body.addElement("UNDWRT_LIST");
			BomRuleResult ruleResult = (BomRuleResult) paramMap
					.get("ruleResult");
			
			if (ruleResult != null && blProposal != null) {
				String riskcode = blProposal.getBLPrpTmain().getArr(0)
						.getRiskCode();
				if (ruleResult.getUndwrtInfo().size() > 0) {
					for (int i = 0; i < ruleResult.getUndwrtInfo().size(); i++) {
						Element undwrtData = UNDWRT_LIST.addElement("UNDWRT_DATA");
						addUndwrtData(undwrtData, ruleResult.getUndwrtInfo()
								.get(i), true, riskcode);
					}
				}
			}
			
		}

		/**
		 * 转人工 内部信息节点
		 * 
		 * @param engageData
		 * @param engageSchema
		 * @throws Exception
		 * */
		public void addUndwrtData(Element undwrtData, String undwrtInfo,
				boolean bIsUndwrt, String riskcode) throws Exception {
			
			Element ebisundwrt = undwrtData.addElement("BISUNDWRT");
			String strBisUndwrt = "0";
			if(bIsUndwrt){
				strBisUndwrt="1";
			}
			ebisundwrt.addText(strBisUndwrt);
			
			Element eundwrtinfo = undwrtData.addElement("UNDWRTINFO");
			eundwrtinfo.addText(undwrtInfo);
			
			Element eriskcode = undwrtData.addElement("RISKCODE");
			eriskcode.addText(riskcode);
		}

		
		
		/**
		 * @throws Exception
		 *             质检
		 * @param body
		 * @param blProposalCI
		 * @param blProposalBI
		 * @throws
		 * */
		private void addQuality(Element body, BLProposal blProposal, Map paramMap) throws Exception {
			Element UNDWRT_LIST = body.addElement("QUALITY_LIST");
			BomRuleResult ruleResult = (BomRuleResult) paramMap
					.get("ruleResult");
			
			if (ruleResult != null && blProposal != null) {
				String riskcode = blProposal.getBLPrpTmain().getArr(0)
						.getRiskCode();
				if (ruleResult.getUndwrtInfo().size() > 0) {
					for (int i = 0; i < ruleResult.getUndwrtInfo().size(); i++) {
						Element undwrtData = UNDWRT_LIST.addElement("QUALITY_DATA");
						addQualityData(undwrtData, ruleResult.getUndwrtInfo()
								.get(i), true, riskcode);
					}
				}
			}
			
		}

		/**
		 * 质检 内部信息节点
		 * 
		 * @param engageData
		 * @param engageSchema
		 * @throws Exception
		 * */
		private void addQualityData(Element qualityData, String qualityInfo,
				boolean bIsQuality, String riskcode) {
			
			Element BISQUALITY = qualityData.addElement("BISQUALITY");
			String strBisUndwrt = "0";
			if(bIsQuality){
				strBisUndwrt="1";
			}
			BISQUALITY.addText(strBisUndwrt);
			
			Element QUALITYINFO = qualityData.addElement("QUALITYINFO");
			QUALITYINFO.addText(qualityInfo);
			
			Element RISKCODE = qualityData.addElement("RISKCODE");
			RISKCODE.addText(riskcode);
		}
		
}
