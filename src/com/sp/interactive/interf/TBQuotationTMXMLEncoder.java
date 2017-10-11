package com.sp.interactive.interf;

import java.io.IOException;
import java.io.StringWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.sp.indiv.ci.blsvr.BLCIInsureDemand;
import com.sp.indiv.ci.schema.CIInsureDemandLossSchema;
import com.sp.indiv.ci.schema.CIInsureDemandPaySchema;
import com.sp.indiv.ci.schema.CIInsureDemandSchema;
import com.sp.phonesale.schema.TargetMarketInfoSchema;
import com.sp.prpall.blsvr.tb.BLProposal;
import com.sp.prpall.schema.PrpIntefInfoSchema;
import com.sp.prpall.schema.PrpLifeTableInfoSchema;
import com.sp.prpall.schema.PrpTTrafficDetailSchema;
import com.sp.prpall.schema.PrpTcarshipTaxSchema;
import com.sp.prpall.schema.PrpTcarshipTaxTJSchema;
import com.sp.prpall.schema.PrpTengageSchema;
import com.sp.prpall.schema.PrpTitemCarExtSchema;
import com.sp.prpall.schema.PrpTitemCarSchema;
import com.sp.prpall.schema.PrpTitemKindSchema;
import com.sp.prpall.schema.PrpTmainSchema;
import com.sp.prpall.schema.PrpTprofitDetailSchema;
import com.sp.quotation.pub.QuotationUtils;
import com.sp.sysframework.common.util.XMLUtils;
import com.sp.utility.SysConfig;
import com.sp.utility.error.UserException;
import com.sunshine.ruleapp.bomPreUwrt.BomRuleResult;


/**
 * 询报价报文封装
 * 
 * */
public class TBQuotationTMXMLEncoder {
	DecimalFormat df4 = new DecimalFormat("0.0000");
	
	DecimalFormat format = new DecimalFormat("0.00");

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
		Element REQUESTTYPE = head.addElement("REQUESTTYPE");
		String requestType = (String) paramMap.get("requestType");
		REQUESTTYPE.addText(requestType);
		
		Element responseCode = head.addElement("RESPONSECODE");
		Element responseMessage = head.addElement("RESPONSEMESSAGE");
		String OperateSiteBC = (String)paramMap.get("OperateSiteBC");
		
		Element OPERATESITE = head.addElement("OPERATESITE");
		OPERATESITE.addText(OperateSiteBC);
		if((String)paramMap.get("Error_Code") == null || "".equals((String)paramMap.get("Error_Code"))){
			responseCode.addText("100000");
			responseMessage.addText(e.getMessage());
		}else{
			responseCode.addText((String)paramMap.get("Error_Code"));
			responseMessage.addText((String)paramMap.get("Error_Message"));
		}
		
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
		
		Element REQUESTTYPE = head.addElement("REQUESTTYPE");
		String requestType = (String) paramMap.get("requestType");
		REQUESTTYPE.addText(requestType);
		
		Element responseCode = head.addElement("RESPONSECODE");
		Element responseMessage = head.addElement("RESPONSEMESSAGE");
		Element OperateSite = head.addElement("OPERATESITE");
		String OperateSiteBC = (String) paramMap.get("OperateSiteBC");
		
		OperateSite.addText((String) paramMap.get("OperateSiteBC"));
		
			responseMessage.addText("成功");
			responseCode.addText("000000");
		
	}
	/**
	 * 报文体信息
	 * @param root
	 * @param paramMap
	 * @param blProposalMap
	 * @throws Exception
	 */
	public void addBody(Element root, Map paramMap, Map blProposalMap)throws Exception{
		BLProposal blProposalCI = (BLProposal)blProposalMap.get(QuotationUtils.BLPROPOSALCI);
		BLProposal blProposalBI = (BLProposal)blProposalMap.get(QuotationUtils.BLPROPOSALBI);
		
		String OperateSiteBC = (String) paramMap.get("OperateSiteBC");
		
		
		Element  body = root.addElement("BODY");
        
		Element PRICENO = body.addElement("PRICENO");
		String priceNo = (String) paramMap.get("PriceNo");
		
		PRICENO.addText(priceNo == null?"":priceNo);
		
		
		
		
			
		Element INFOTRANSNO = body.addElement("INFOTRANSNO");
		String InfoTransNo = (String) paramMap.get("InfoTransNo");
		INFOTRANSNO.addText(InfoTransNo);
			
			
		Element OPERATESITE = body.addElement("OPERATESITE");
		OPERATESITE.addText(OperateSiteBC);
			




		
		
		
		
		
		Element PROPOSALCI = body.addElement("PROPOSALCI");
		Element PROPOSALBI = body.addElement("PROPOSALBI");
		
		
		 
		Element  MAKECOM = body.addElement("MAKECOM");
		 
		Element  COMCODE = body.addElement("COMCODE");
	     
		Element  HANDLERCODE = body.addElement("HANDLERCODE");
	     
		Element  BUSINESSNATURE = body.addElement("BUSINESSNATURE");
     	 
		Element  AGENTCODE = body.addElement("AGENTCODE");
	     
		Element  USBKEY = body.addElement("USBKEY");
	     
		Element  CONTRACTNO = body.addElement("CONTRACTNO");
		
		 
		Element  GROUPCODE = body.addElement("GROUPCODE");
		
	     
		Element  OPERATEDATE = body.addElement("OPERATEDATE");
		
	     
		Element  STARTDATE = body.addElement("STARTDATE");
		
		Element  STARTHOUR = body.addElement("STARTHOUR");
	     
		Element  endDATE = body.addElement("ENDDATE");
		 
		Element  endHOUR = body.addElement("ENDHOUR");
		
		
		Element LASTYEARSTARTDATECI = body.addElement("LASTYEARSTARTDATECI");
		
		
		Element LASTYEARENDDATECI = body.addElement("LASTYEARENDDATECI");
		
		
	    Element LASTYEARSTARTDATEBI = body.addElement("LASTYEARSTARTDATEBI");
				
		
		Element LASTYEARENDDATEBI = body.addElement("LASTYEARENDDATEBI");
		
		
		 
		Element  BIZFLAG = body.addElement("BIZFLAG");
		
	     
		Element  demandNOCI = body.addElement("DEMANDNOCI");
	     
		Element  demandNOBI = body.addElement("DEMANDNOBI");
		
		
		
		Element QUESTION = body.addElement("QUESTION");
		
	     
		Element  sumAMOUNTCI = body.addElement("SUMAMOUNTCI");
	     
		Element  sumPREMIUMCI = body.addElement("SUMPREMIUMCI");
		
		Element  sumDISCOUNTCI = body.addElement("SUMDISCOUNTCI");
		
		Element basePremiumCI =body.addElement("BASEPREMIUMCI");
		
		Element benchMarkPremiumCI =body.addElement("BENCHMARKPREMIUMCI");
	     
		Element  sumAMOUNTBI = body.addElement("SUMAMOUNTBI");
		 
		Element  sumPREMIUMBI = body.addElement("SUMPREMIUMBI");
		 
		Element  sumDISCOUNTBI = body.addElement("SUMDISCOUNTBI");
		 
		Element  basePREMIUMBI = body.addElement("BASEPREMIUMBI");
		
		Element benchMarkPremiumBI =body.addElement("BENCHMARKPREMIUMBI");
		
		
		
		Element disRateCI = body.addElement("DISRATECI");
		Element disRateBI = body.addElement("DISRATEBI");
		
		
		
		
		Element SUMSUBPREMCI=body.addElement("SUMSUBPREMCI");
		Element DISCOUNTCI=body.addElement("DISCOUNTCI");
		
		Element SUMSUBPREMBI=body.addElement("SUMSUBPREMBI");
		Element DISCOUNTBI=body.addElement("DISCOUNTBI");
		
		Element isRepeatInsuranceCI=body.addElement("ISREPEATINSURANCECI");
		Element isRepeatInsuranceBI=body.addElement("ISREPEATINSURANCEBI");
		
		
		Element LOWDISCOUNT=body.addElement("LOWDISCOUNT");
		
		
		
		Element FIXEDFEERATECI=body.addElement("FIXEDFEERATECI");
		Element FIXEDFEERATEBI=body.addElement("FIXEDFEERATEBI");
		

		
		Element REMARKBI=body.addElement("REMARKBI");
		if(blProposalBI!=null&&blProposalBI.getBLCIInsureDemand()!=null&&blProposalBI.getBLCIInsureDemand().getSize()>0){
			CIInsureDemandSchema ciInsureDemand=blProposalBI.getBLCIInsureDemand().getArr(0);
			if(!"".equals(ciInsureDemand.getReCoverMsg())){
				REMARKBI.addText(ciInsureDemand.getReCoverMsg());
			}else{
				REMARKBI.addText(ciInsureDemand.getRemark());
			}
			
		}
		
		


		Element SUMPROFITCI=body.addElement("SUMPROFITCI");
		Element SUMPROFITBI=body.addElement("SUMPROFITBI");
		
		
		Element ATTHEENDOFRATEBI=body.addElement("ATTHEENDOFRATEBI");
		Element FINALMARKETFEERATEBI=body.addElement("FINALMARKETFEERATEBI");
		Element DISCOUNTMODELBI=body.addElement("DISCOUNTMODELBI");
		Element AGREEMENTDECLAREBI=body.addElement("AGREEMENTDECLAREBI");
		Element FINALTHEORYUNDWRTFACTORSBI=body.addElement("FINALTHEORYUNDWRTFACTORSBI");
		Element ACTUALUNDWRTFACTORSBI=body.addElement("ACTUALUNDWRTFACTORSBI");
		Element ACTUALCOSTBI=body.addElement("ACTUALCOSTBI");
		Element THEORYUNDWRTFACTORS=body.addElement("THEORYUNDWRTFACTORS");
		Element REQUIREDMARGINSBI=body.addElement("REQUIREDMARGINSBI");
		Element ADVICEUNDWRTFACTORSBI=body.addElement("ADVICEUNDWRTFACTORSBI");
		Element ADVICEUNDWRTFACTORSUPPERSBI=body.addElement("ADVICEUNDWRTFACTORSUPPERSBI");
		Element ADVICEUNDWRTFACTORSLOWERSBI=body.addElement("ADVICEUNDWRTFACTORSLOWERSBI");
		Element ISTHROUGHBI=body.addElement("ISTHROUGHBI");
		Element ISVEHICLEBI=body.addElement("ISVEHICLEBI");
		Element ISATTHEENDOFATEIDENTIFFCATIONBI=body.addElement("ISATTHEENDOFATEIDENTIFFCATIONBI");
		Element ISTATEUPPERBI=body.addElement("ISTATEUPPERBI");
		
		Element ATTHEENDOFRATECI=body.addElement("ATTHEENDOFRATECI");
		Element FINALMARKETFEERATECI=body.addElement("FINALMARKETFEERATECI");
		Element DISCOUNTMODELCI=body.addElement("DISCOUNTMODELCI");
		Element AGREEMENTDECLARECI=body.addElement("AGREEMENTDECLARECI");
		Element FINALTHEORYUNDWRTFACTORSCI=body.addElement("FINALTHEORYUNDWRTFACTORSCI");
		Element ACTUALUNDWRTFACTORSCI=body.addElement("ACTUALUNDWRTFACTORSCI");
		Element ACTUALCOSTCI=body.addElement("ACTUALCOSTCI");
		Element THEORYUNDWRTFACTORSCI=body.addElement("THEORYUNDWRTFACTORSCI");
		Element REQUIREDMARGINSCI=body.addElement("REQUIREDMARGINSCI");
		Element ADVICEUNDWRTFACTORSCI=body.addElement("ADVICEUNDWRTFACTORSCI");
		Element ADVICEUNDWRTFACTORSUPPERSCI=body.addElement("ADVICEUNDWRTFACTORSUPPERSCI");
		Element ADVICEUNDWRTFACTORSLOWERSCI=body.addElement("ADVICEUNDWRTFACTORSLOWERSCI");
		Element ISTHROUGHCI=body.addElement("ISTHROUGHCI");
		Element ISVEHICLECI=body.addElement("ISVEHICLECI");
		Element ISATTHEENDOFATEIDENTIFFCATIONCI=body.addElement("ISATTHEENDOFATEIDENTIFFCATIONCI");
		Element ISTATEUPPERCI=body.addElement("ISTATEUPPERCI");
		
		
		Element LIFETABLEINFO_LIST=body.addElement("LIFETABLEINFO_LIST");
		Element QUALITY_LIST=body.addElement("QUALITY_LIST");
		Element UNDWRT_LIST=body.addElement("UNDWRT_LIST");
		
		
		Element ISINVERSE=body.addElement("ISINVERSE");
		
		
		Element INVERSEBASE=body.addElement("INVERSEBASE");
		Element FREERATEUPPERSBI=body.addElement("FREERATEUPPERSBI");
		Element FREERATEUPPERSCI=body.addElement("FREERATEUPPERSCI");
		
		if (blProposalBI != null) {
			
			if(blProposalBI.getBLCIInsureDemand().getSize()>0&&"1".equals(blProposalBI.getBLCIInsureDemand().getArr(0).getRenewalFlag())){
				demandNOBI.setText(blProposalBI.getBLCIInsureDemand().getArr(0).getDemandNo());
				QUESTION.setText(blProposalBI.getBLCIInsureDemand().getArr(0).getQuestion());
				return;
				}
		
		
			
			boolean flag=false;
			if (((String) paramMap.get(QuotationUtils.QUTATIONTYPE)).equals(QuotationUtils.ROUGHQUOTATION)) {
				flag = true;
			}
			if(!flag&& QuotationUtils.isPlatformInteractBI(blProposalBI)){
				
				String demandnobi = blProposalBI.getBLCIInsureDemand().getArr(0).getDemandNo();

				demandNOBI.setText(demandnobi);
			}else{
				demandNOBI.setText("");
			}
			
			String proposalBI = blProposalBI.getBLPrpTmain().getArr(0).getProposalNo();
			PROPOSALBI.addText(proposalBI);
			
			
		
			Double vSumAmount = 0.0;
			for (int i = 0; i < blProposalBI.getBLPrpTitemKind().getSize(); i++) {
				vSumAmount +=  Double.parseDouble(blProposalBI.getBLPrpTitemKind().getArr(i).getAmount());
			}
			sumAMOUNTBI.addText(vSumAmount.toString());
			
			String sumpremiumbi = blProposalBI.getBLPrpTmain().getArr(0).getSumPremium();
			sumPREMIUMBI.addText(sumpremiumbi);
			String sumdiscountbi = blProposalBI.getBLPrpTmain().getArr(0).getDiscount();
			sumDISCOUNTBI.addText(df4.format(1-Double.parseDouble(sumdiscountbi)));
			
			
			basePREMIUMBI.addText(blProposalBI.getBLPrpTmain().getArr(0).getSumBasePremium());			
			
			
			SUMSUBPREMBI.addText(blProposalBI.getBLPrpTmain().getArr(0).getSumSubPrem());
			DISCOUNTBI.addText(blProposalBI.getBLPrpTmain().getArr(0).getDiscount());
			
			
 			benchMarkPremiumBI.addText(blProposalBI.getBLPrpTmain().getArr(0).getSumBenchMarkPremium()); 			
 			
 			
 			disRateBI.addText(blProposalBI.getBLPrpTmain().getArr(0).getDisRate());
 			
 			

 			Element SALESSALARYRATEBI=body.addElement("SALESSALARYRATEBI");
 			if(!blProposalBI.getBLPrpTexpense().getArr(0).getSalesSalaryRate().equals("")){
 				String SalesFeeRate = (Double.parseDouble(blProposalBI.getBLPrpTexpense().getArr(0).getSalesSalaryRate()))+"";
 				SALESSALARYRATEBI.addText(SalesFeeRate);
 			}
 			Element MANAGEFEERATEBI=body.addElement("MANAGEFEERATEBI");
 			if(!blProposalBI.getBLPrpTexpense().getArr(0).getManageFeeRate().equals("")){
 				String ManageFeeRate = (Double.parseDouble(blProposalBI.getBLPrpTexpense().getArr(0).getManageFeeRate()))+"";
 				MANAGEFEERATEBI.addText(ManageFeeRate);
 			}
 			
 			
 			
 			BomRuleResult bomRuleResultBI=(BomRuleResult) paramMap.get("ruleResultBI");
 			if(bomRuleResultBI!=null){
 				
 			    FINALMARKETFEERATEBI.addText(blProposalBI.getBLPrpTmainExtras().getArr(0).getFinalMarketFeeRate()!=null?blProposalBI.getBLPrpTmainExtras().getArr(0).getFinalMarketFeeRate():"");
 				DISCOUNTMODELBI.addText(bomRuleResultBI.getDiscountModel()!=null?bomRuleResultBI.getDiscountModel():"");
 				AGREEMENTDECLAREBI.addText(Double.toString(bomRuleResultBI.getAgreementDeclare())!=null?Double.toString(bomRuleResultBI.getAgreementDeclare()):"");
 				FINALTHEORYUNDWRTFACTORSBI.addText(Double.toString(bomRuleResultBI.getFinaltheoryUndwrtFactors())!=null?Double.toString(bomRuleResultBI.getFinaltheoryUndwrtFactors()):"");
 				ACTUALUNDWRTFACTORSBI.addText(blProposalBI.getBLPrpTmainExtras().getArr(0).getActualAutoUndwrtFactor()!=null?blProposalBI.getBLPrpTmainExtras().getArr(0).getActualAutoUndwrtFactor():"");
 				ACTUALCOSTBI.addText(blProposalBI.getBLPrpTmainExtras().getArr(0).getActualCost()!=null?blProposalBI.getBLPrpTmainExtras().getArr(0).getActualCost():"");
 				THEORYUNDWRTFACTORS.addText(blProposalBI.getBLPrpTmainExtras().getArr(0).getTheoryAutoUndwrtFactor()!=null?blProposalBI.getBLPrpTmainExtras().getArr(0).getTheoryAutoUndwrtFactor():"");
 				REQUIREDMARGINSBI.addText(blProposalBI.getBLPrpTmainExtras().getArr(0).getRequiredMargins()!=null?blProposalBI.getBLPrpTmainExtras().getArr(0).getRequiredMargins():"");
 				
 				ADVICEUNDWRTFACTORSBI.addText(blProposalBI.getBLPrpTmainExtras().getArr(0).getSuggestAutoUndwrtFactor()!=null?blProposalBI.getBLPrpTmainExtras().getArr(0).getSuggestAutoUndwrtFactor():"");
 				
 				ADVICEUNDWRTFACTORSUPPERSBI.addText(blProposalBI.getBLPrpTmainExtras().getArr(0).getAutoUndwrtFactorUpperLimit()!=null?blProposalBI.getBLPrpTmainExtras().getArr(0).getAutoUndwrtFactorUpperLimit():"");
 				
 				ADVICEUNDWRTFACTORSLOWERSBI.addText(blProposalBI.getBLPrpTmainExtras().getArr(0).getAutoUndwrtFactorLowerLimit()!=null?blProposalBI.getBLPrpTmainExtras().getArr(0).getAutoUndwrtFactorLowerLimit():"");
 				BomRuleResult ruleResultBI = (BomRuleResult) paramMap
 						.get("ruleResultBI");
 				
 				if(ruleResultBI!=null){
 				ATTHEENDOFRATEBI.addText(Double.toString(ruleResultBI.getGuaranteedCostBI()));
 				String isthroughbi="0";
 				if(ruleResultBI.isDirectBI()){
 					isthroughbi="1";
 				}
 				ISTHROUGHBI.addText(isthroughbi);
 				ISVEHICLEBI.addText(bomRuleResultBI.getCarCheckStatus());
 				String isattheendofateidentiffcationbi="0";
 				if(ruleResultBI.isGuaranteedCostFlagBI()){
 					isattheendofateidentiffcationbi="1";
 				}
 				ISATTHEENDOFATEIDENTIFFCATIONBI.addText(isattheendofateidentiffcationbi);
 				String istateupperbi="0";
 				if(ruleResultBI.isFeeCapFlagBI()){
 					istateupperbi="1";
 				}
 				ISTATEUPPERBI.addText(istateupperbi);
 				}
 				String isINVERSE= String.valueOf(paramMap.get("ISINVERSE"));
 				ISINVERSE.addText(isINVERSE!=null?isINVERSE:"");
 			}
 			
 			
 			
	 			
				
 				INVERSEBASE.addText(blProposalBI.getBLPrpTmainExtras().getArr(0).getInverseBase()!=null?blProposalBI.getBLPrpTmainExtras().getArr(0).getInverseBase():"");
				
 				FREERATEUPPERSBI.addText(blProposalBI.getBLPrpTmainExtras().getArr(0).getFreeRateUppersBI()!=null?blProposalBI.getBLPrpTmainExtras().getArr(0).getFreeRateUppersBI():"");
				
 				FREERATEUPPERSCI.addText(blProposalBI.getBLPrpTmainExtras().getArr(0).getFreeRateUppersCI()!=null?blProposalBI.getBLPrpTmainExtras().getArr(0).getFreeRateUppersCI():"");
				
 			}
		
 			
		
		if(blProposalCI != null){
					
			
				
				if(blProposalCI.getBLCIInsureDemand().getSize()>0&&"1".equals(blProposalCI.getBLCIInsureDemand().getArr(0).getRenewalFlag())){
					demandNOCI.setText(blProposalCI.getBLCIInsureDemand().getArr(0).getDemandNo());
					QUESTION.setText(blProposalCI.getBLCIInsureDemand().getArr(0).getQuestion());
					return;
				}

			
			boolean flag=false;
			if (((String) paramMap.get(QuotationUtils.QUTATIONTYPE)).equals(QuotationUtils.ROUGHQUOTATION)) {
				flag = true;
			}
			if(!flag&& QuotationUtils.isPlatformInteractCI(blProposalCI)){
				
				String demandnoci = blProposalCI.getBLCIInsureDemand().getArr(0).getDemandNo();
				demandNOCI.setText(demandnoci);
			}else{
				demandNOCI.setText("");
			}		
			
			
			String proposalCI = blProposalCI.getBLPrpTmain().getArr(0).getProposalNo();
			PROPOSALCI.addText(proposalCI);
			
			String sumamountci = blProposalCI.getBLPrpTmain().getArr(0).getSumAmount();
			sumAMOUNTCI.addText(sumamountci);
			String sumpremiumci = blProposalCI.getBLPrpTmain().getArr(0).getSumPremium();
			sumPREMIUMCI.addText(sumpremiumci);
			String sumdiscountci = blProposalCI.getBLPrpTmain().getArr(0).getDiscount();
			sumDISCOUNTCI.addText(df4.format(1-Double.parseDouble(sumdiscountci)));
			
			basePremiumCI.addText(blProposalCI.getBLPrpTmain().getArr(0).getSumBasePremium());			
			
			
			SUMSUBPREMCI.addText(blProposalCI.getBLPrpTmain().getArr(0).getSumSubPrem());
			DISCOUNTCI.addText(blProposalCI.getBLPrpTmain().getArr(0).getDiscount());			
			
			
 			benchMarkPremiumCI.addText(blProposalCI.getBLPrpTitemKind().getArr(0).getBenchMarkPremium());			
 			
 			disRateCI.addText(blProposalCI.getBLPrpTmain().getArr(0).getDisRate());
 			
 			
 			

 				
 			Element SALESSALARYRATECI=body.addElement("SALESSALARYRATECI");
 			if(!blProposalCI.getBLPrpTexpense().getArr(0).getSalesSalaryRate().equals("")){
 				String SalesFeeRate = (Double.parseDouble(blProposalCI.getBLPrpTexpense().getArr(0).getSalesSalaryRate()))+"";
 				SALESSALARYRATECI.addText(SalesFeeRate);
 			}
 			Element MANAGEFEERATECI=body.addElement("MANAGEFEERATECI");
 			if(!blProposalCI.getBLPrpTexpense().getArr(0).getManageFeeRate().equals("")){
 				String ManageFeeRate = (Double.parseDouble(blProposalCI.getBLPrpTexpense().getArr(0).getManageFeeRate()))+"";
 				MANAGEFEERATECI.addText(ManageFeeRate);
 			}
 				
 			
 			
 			
 			
 			BomRuleResult bomRuleResultCI=(BomRuleResult) paramMap.get("ruleResultCI");
 			if(bomRuleResultCI!=null){
 			if(blProposalCI.getBLPrpTmainExtras().getSize()>0){
 					
 				FINALMARKETFEERATECI.addText(blProposalCI.getBLPrpTmainExtras().getArr(0).getFinalMarketFeeRate()!=null?blProposalCI.getBLPrpTmainExtras().getArr(0).getFinalMarketFeeRate():"");
 				DISCOUNTMODELCI.addText(bomRuleResultCI.getDiscountModel()!=null?bomRuleResultCI.getDiscountModel():"");
 				AGREEMENTDECLARECI.addText(Double.toString(bomRuleResultCI.getAgreementDeclare())!=null?Double.toString(bomRuleResultCI.getAgreementDeclare()):"");
 				FINALTHEORYUNDWRTFACTORSCI.addText(Double.toString(bomRuleResultCI.getFinaltheoryUndwrtFactors())!=null?Double.toString(bomRuleResultCI.getFinaltheoryUndwrtFactors()):"");
 				ACTUALUNDWRTFACTORSCI.addText(blProposalCI.getBLPrpTmainExtras().getArr(0).getActualAutoUndwrtFactor()!=null?blProposalCI.getBLPrpTmainExtras().getArr(0).getActualAutoUndwrtFactor():"");
 				ACTUALCOSTCI.addText(blProposalCI.getBLPrpTmainExtras().getArr(0).getActualCost()!=null?blProposalCI.getBLPrpTmainExtras().getArr(0).getActualCost():"");
 				THEORYUNDWRTFACTORSCI.addText(blProposalCI.getBLPrpTmainExtras().getArr(0).getTheoryAutoUndwrtFactor()!=null?blProposalCI.getBLPrpTmainExtras().getArr(0).getTheoryAutoUndwrtFactor():"");
 				REQUIREDMARGINSCI.addText(blProposalCI.getBLPrpTmainExtras().getArr(0).getRequiredMargins()!=null?blProposalCI.getBLPrpTmainExtras().getArr(0).getRequiredMargins():"");
 				
 				ADVICEUNDWRTFACTORSCI.addText(blProposalCI.getBLPrpTmainExtras().getArr(0).getSuggestAutoUndwrtFactor()!=null?blProposalCI.getBLPrpTmainExtras().getArr(0).getSuggestAutoUndwrtFactor():"");
 				
 				ADVICEUNDWRTFACTORSUPPERSCI.addText(blProposalCI.getBLPrpTmainExtras().getArr(0).getAutoUndwrtFactorUpperLimit()!=null?blProposalCI.getBLPrpTmainExtras().getArr(0).getAutoUndwrtFactorUpperLimit():"");
 				
 				ADVICEUNDWRTFACTORSLOWERSCI.addText(blProposalCI.getBLPrpTmainExtras().getArr(0).getAutoUndwrtFactorLowerLimit()!=null?blProposalCI.getBLPrpTmainExtras().getArr(0).getAutoUndwrtFactorLowerLimit():"");
 				BomRuleResult ruleResultCI = (BomRuleResult) paramMap
 						.get("ruleResultCI");
 				if(ruleResultCI!=null){
 				ATTHEENDOFRATECI.addText(Double.toString(ruleResultCI.getGuaranteedCostCI()));
 				String isthroughci="0";
 				if(ruleResultCI.isDirectCI()){
 					isthroughci="1";
 				}
 				ISTHROUGHCI.addText(isthroughci);
 				ISVEHICLECI.addText("");
 				String isattheendofateidentiffcationci="0";
 				if(ruleResultCI.isGuaranteedCostFlagCI()){
 					isattheendofateidentiffcationci="1";
 				}
 				ISATTHEENDOFATEIDENTIFFCATIONCI.addText(isattheendofateidentiffcationci);
 				String istateupperci="0";
 				if(ruleResultCI.isFeeCapFlagCI()){
 					istateupperci="1";
 				}
 				ISTATEUPPERCI.addText(istateupperci);
 				}
 				String isINVERSE= String.valueOf(paramMap.get("ISINVERSE"));
 				ISINVERSE.addText(isINVERSE!=null?isINVERSE:"");
 			}
 			
 			}
 			
	 			
				
				INVERSEBASE.addText(blProposalCI.getBLPrpTmainExtras().getArr(0).getInverseBase()!=null?blProposalCI.getBLPrpTmainExtras().getArr(0).getInverseBase():"");
				
				FREERATEUPPERSBI.addText(blProposalCI.getBLPrpTmainExtras().getArr(0).getFreeRateUppersBI()!=null?blProposalCI.getBLPrpTmainExtras().getArr(0).getFreeRateUppersBI():"");
				
				FREERATEUPPERSCI.addText(blProposalCI.getBLPrpTmainExtras().getArr(0).getFreeRateUppersCI()!=null?blProposalCI.getBLPrpTmainExtras().getArr(0).getFreeRateUppersCI():"");
				
		}
		
		if(blProposalBI != null){
			String makecom = blProposalBI.getBLPrpTmain().getArr(0).getMakeCom();
			MAKECOM.addText(makecom);
			String comcode = blProposalBI.getBLPrpTmain().getArr(0).getComCode();
			COMCODE.addText(comcode);
			String handlercode = blProposalBI.getBLPrpTmain().getArr(0).getHandlerCode();
			HANDLERCODE.addText(handlercode);
			String businessnature = blProposalBI.getBLPrpTmain().getArr(0).getBusinessNature();
			BUSINESSNATURE.addText(businessnature);
			String agentcode = blProposalBI.getBLPrpTmain().getArr(0).getAgentCode();
			AGENTCODE.addText(agentcode);
			
			String usbkey = blProposalBI.getBLPrpTmain().getArr(0).getUsbKey();
			USBKEY.addText(usbkey);
			String contractno = blProposalBI.getBLPrpTmain().getArr(0).getContractNo();
			CONTRACTNO.addText(contractno);
			String operatedate = blProposalBI.getBLPrpTmain().getArr(0).getOperateDate();
			OPERATEDATE.addText(operatedate);
			
			String startdate = blProposalBI.getBLPrpTmain().getArr(0).getStartDate();
			STARTDATE.addText(startdate);
			String startHour = blProposalBI.getBLPrpTmain().getArr(0).getStartHour();
			STARTHOUR.addText(startHour);
			String enddate = blProposalBI.getBLPrpTmain().getArr(0).getEndDate();
			endDATE.addText(enddate);
			String endHour = blProposalBI.getBLPrpTmain().getArr(0).getEndHour();
			endHOUR.addText(endHour);
			
			String lastYearStartDateBI = "";
			LASTYEARSTARTDATEBI.addText(lastYearStartDateBI);
            
			String lastYearEndDateBI = "";
			if(blProposalBI.getBLCIInsureDemand().getSize()>0){
				lastYearEndDateBI = blProposalBI.getBLCIInsureDemand().getArr(0).getBusiLastYearEndDate();
			}
            
			LASTYEARENDDATEBI.addText(lastYearEndDateBI);
			
			
				
			Element TESTCARSTATUS = body.addElement("TESTCARSTATUS");
			
			String CarCheckStatus = blProposalBI.getBLPrpTitemCar().getArr(0).getCarCheckStatus();
			TESTCARSTATUS.addText(CarCheckStatus != null ? CarCheckStatus :"");
			

			String strIsRepeatInsuranceBI = (String)paramMap.get("isRepeatInsuranceBI");
			isRepeatInsuranceBI.addText(strIsRepeatInsuranceBI != null ? strIsRepeatInsuranceBI :"");

			
			String strlowDiscount = (String)paramMap.get("LOWDISCOUNT");
			LOWDISCOUNT.addText(strlowDiscount != null ? strlowDiscount :"");
			
			
			
			String FixedFeeRate = blProposalBI.getBLPrpTmain().getArr(0).getFixedFeeRate();
			FIXEDFEERATEBI.addText(FixedFeeRate != null ? FixedFeeRate :"");
			



			String sumPROFITBI = blProposalBI.getBLPrpTmain().getArr(0).getSumDiscount();
			SUMPROFITBI.addText(sumPROFITBI);
			
		}
		if(blProposalCI != null){
			String makecom = blProposalCI.getBLPrpTmain().getArr(0).getMakeCom();
			MAKECOM.addText(makecom);
			String comcode = blProposalCI.getBLPrpTmain().getArr(0).getComCode();
			COMCODE.addText(comcode);
			String handlercode = blProposalCI.getBLPrpTmain().getArr(0).getHandlerCode();
			HANDLERCODE.addText(handlercode);
			String businessnature = blProposalCI.getBLPrpTmain().getArr(0).getBusinessNature();
			BUSINESSNATURE.addText(businessnature);
			String agentcode = blProposalCI.getBLPrpTmain().getArr(0).getAgentCode();
			AGENTCODE.addText(agentcode);
			
			String usbkey = blProposalCI.getBLPrpTmain().getArr(0).getUsbKey();
			USBKEY.addText(usbkey);
			String contractno = blProposalCI.getBLPrpTmain().getArr(0).getContractNo();
			CONTRACTNO.addText(contractno);
			String operatedate = blProposalCI.getBLPrpTmain().getArr(0).getOperateDate();
			OPERATEDATE.addText(operatedate);
			
			String startdate = blProposalCI.getBLPrpTmain().getArr(0).getStartDate();
			STARTDATE.addText(startdate);
			String startHour = blProposalCI.getBLPrpTmain().getArr(0).getStartHour();
			STARTHOUR.addText(startHour);
			String enddate = blProposalCI.getBLPrpTmain().getArr(0).getEndDate();
			endDATE.addText(enddate);
			String endHour = blProposalCI.getBLPrpTmain().getArr(0).getEndHour();
			endHOUR.addText(endHour);
			
	        
			String lastYearStartDateCI = "";
			if(blProposalCI.getBLCIInsureDemand().getSize()>0){
				lastYearStartDateCI = blProposalCI.getBLCIInsureDemand().getArr(0).getLastYearStartDate();
			}
			LASTYEARSTARTDATECI.addText(lastYearStartDateCI);
			String lastYearEndDateCI = "";
			if(blProposalCI.getBLCIInsureDemand().getSize()>0){
				lastYearEndDateCI = blProposalCI.getBLCIInsureDemand().getArr(0).getLastYearEndDate();
			}
			LASTYEARENDDATECI.addText(lastYearEndDateCI);
			
			
			

			String isRepeatInsurance = (String)paramMap.get("isRepeatInsuranceCI");
			isRepeatInsuranceCI.addText(isRepeatInsurance != null ? isRepeatInsurance :"");

			
			
			String FixedFeeRate = blProposalCI.getBLPrpTmain().getArr(0).getFixedFeeRate();
			FIXEDFEERATECI.addText(FixedFeeRate != null ? FixedFeeRate :"");
			
			


			String sumPROFITCI = blProposalCI.getBLPrpTmain().getArr(0).getSumDiscount();
			SUMPROFITCI.addText(sumPROFITCI);

		}
		
		String strRiskCode = (String) paramMap.get("RiskCodeBI");
		if(strRiskCode != null){
			Element RiskCodeBI = body.addElement("RISKCODEBI");
			RiskCodeBI.addText(strRiskCode);
		}
		
		
		 

		 
		addCar(body, blProposalCI, blProposalBI);
		 
		addCarTax(body, blProposalCI);
		 
		addCarTaxTJ(body, blProposalCI);
		
		if(paramMap.get("INSUREKINDCODE") != null){
			Element INSUREKINDCODE = body.addElement("INSUREKINDCODE");
			List list = (List) paramMap.get("INSUREKINDCODE");
			StringBuffer sb = new StringBuffer();
			for(int i = 0 ;i<list.size();i++){
				sb.append(list.get(i));
			}
			INSUREKINDCODE.addText(sb.toString());


			
		}
		
		
		addKind(body, blProposalCI, blProposalBI);
		
		
		
		if ((blProposalCI!=null)
				||(blProposalBI!=null)) {
			addProfitDetail(body, blProposalCI, blProposalBI);
		}
		
		
		
		addClaim(body, blProposalCI, blProposalBI);
		
		addTrafficDetail(body, blProposalCI, blProposalBI);
		
		addPecc(body, blProposalCI, blProposalBI);
		
		
		addCIInsureDemand(body, blProposalCI, blProposalBI);
		

		
		
		
		if ((blProposalCI!=null)
				||(blProposalBI!=null)) {
			addPrpintefInfo(body, blProposalCI, blProposalBI);
		}
		
		
		
		
		
		addTargetMarket(body, blProposalCI, blProposalBI,paramMap);
		
		addUndwrt(UNDWRT_LIST, blProposalCI, blProposalBI, paramMap);
		
		
		addQuality(QUALITY_LIST, blProposalCI, blProposalBI, paramMap);
		
		
		addLifeTableInfo(LIFETABLEINFO_LIST,blProposalCI, blProposalBI, paramMap);
		
	}

	
	private void addTargetMarket(Element body, BLProposal blProposalCI, BLProposal blProposalBI,Map paramMap) throws Exception{
		if (blProposalCI != null) {
			TargetMarketInfoSchema targetMarketInfoSchema = new TargetMarketInfoSchema();
			targetMarketInfoSchema = (TargetMarketInfoSchema)paramMap.get("targetMarketInfoSchemaCI");
			String BUSINESSKINDCODE ="";
			String BUSINESSKINDNAME ="";
			String BUSINESSCLASSCODE ="";
			String BUSINESSCLASSNAME ="";
			String MANUALFLAG ="";
			String DISRATE ="";
			String MANAGEFEERATE ="";
			String SALESSALARYRATE ="";
			String TEAMFEERATE ="";
			String OFFICEFEERATE ="";
			String RISKCODE ="";
			String BIZFLAG ="";
			String MAXEXPENSERATE ="";
			String PROFITMARGIN ="";
			String EXPENSESPACE ="";
			String RISKPREMIUM ="";
			String FIXSUMFEE ="";
			String PROFITBUSINESSKIND ="";
			String FEECLASS ="";
			String SALESSALARYFEE ="";
			String MANAGEFEEFEE ="";
			String DISRATEFEE ="";
			String ZEROPROFITSPREMIUM ="";
			String FIXEDPROFITMARGIN ="";
			String DEPOSITRATE ="";
			String FINALPAYRATE ="";
			String FINALBUSINESSCLASS ="";
			String PROFITMARGINBC ="";
			String PROFITBUSINESSCLASSBC ="";
			String FINALBUSINESSCLASSBC ="";
			String CUSTOMTYPECODE ="";
			String CUSTOMTYPENAME ="";
			String COSTMARGIN ="";
			String COSTBUSINESSCLASS ="";
			String COSTMARGINBC ="";
			String COSTBUSINESSCLASSBC ="";
			String SUMEXPENSERATE ="";
			String OPERATIONCOSTRATE ="";
			String SALESEXPENSERATE ="";
			String SALESEXPENSETHRESHOLD ="";
			String FINALPAYRATEBC ="";
			String UNSETTLEMENTRATE ="";
			String COMUNRATE ="";
			String FEERATE ="";
			String Pay_Car_Actual_ValueBC = "";
			String PayRateCarKind = "";
			BUSINESSKINDCODE = targetMarketInfoSchema.getBusinessKindCode();
			BUSINESSKINDNAME = targetMarketInfoSchema.getBusinessKindName();
			BUSINESSCLASSCODE = targetMarketInfoSchema.getBusinessClassCode();
			BUSINESSCLASSNAME = targetMarketInfoSchema.getBusinessClassName();
			MANUALFLAG = targetMarketInfoSchema.getManualFlag();
			DISRATE = targetMarketInfoSchema.getDisRate();
			MANAGEFEERATE = targetMarketInfoSchema.getManageFeeRate();
			SALESSALARYRATE = targetMarketInfoSchema.getSalesSalaryRate();
			TEAMFEERATE = targetMarketInfoSchema.getTeamFeeRate();
			OFFICEFEERATE = targetMarketInfoSchema.getOfficeFeeRate();
			RISKCODE = targetMarketInfoSchema.getRiskCode();
			BIZFLAG = targetMarketInfoSchema.getBizFlag();
			MAXEXPENSERATE = targetMarketInfoSchema.getMaxExpenseRate();
			PROFITMARGIN = targetMarketInfoSchema.getProfitMargin();
			EXPENSESPACE = targetMarketInfoSchema.getExpenseSpace();
			RISKPREMIUM = targetMarketInfoSchema.getRiskPermium();
			FIXSUMFEE = targetMarketInfoSchema.getFixSumFee();
			PROFITBUSINESSKIND = targetMarketInfoSchema.getProfitBusinessKind();
			FEECLASS = targetMarketInfoSchema.getFeeClass();
			SALESSALARYFEE = targetMarketInfoSchema.getSalesSalaryRate();
			MANAGEFEEFEE = targetMarketInfoSchema.getManageFeeRate();
			DISRATEFEE = targetMarketInfoSchema.getDisRate();
			ZEROPROFITSPREMIUM = targetMarketInfoSchema.getZeroProfitsPremium();
			FIXEDPROFITMARGIN = targetMarketInfoSchema.getFixedProfitMargin();
			DEPOSITRATE = targetMarketInfoSchema.getDepositRate();
			FINALPAYRATE = targetMarketInfoSchema.getFinalPayRate();
			FINALBUSINESSCLASS = targetMarketInfoSchema.getFinalBusinessClass();
			PROFITMARGINBC = targetMarketInfoSchema.getProfitMarginBC();
			PROFITBUSINESSCLASSBC = targetMarketInfoSchema.getProfitBusinessClassBC();
			FINALBUSINESSCLASSBC = targetMarketInfoSchema.getFinalBusinessClassBC();
			CUSTOMTYPECODE = targetMarketInfoSchema.getCustomTypeCode();
			CUSTOMTYPENAME = targetMarketInfoSchema.getCustomTypeName();
			COSTMARGIN = targetMarketInfoSchema.getCostMargin();
			COSTBUSINESSCLASS = targetMarketInfoSchema.getCostBusinessClass();
			COSTMARGINBC = targetMarketInfoSchema.getCostMarginBC();
			COSTBUSINESSCLASSBC	 = targetMarketInfoSchema.getCostBusinessClassBC();
			SUMEXPENSERATE = targetMarketInfoSchema.getSumExpenseRate();
			OPERATIONCOSTRATE = targetMarketInfoSchema.getOperationCostRate();
			SALESEXPENSERATE = targetMarketInfoSchema.getSalesExpenseRate();
			SALESEXPENSETHRESHOLD = targetMarketInfoSchema.getSalesExpenseThreshold();
			FINALPAYRATEBC = targetMarketInfoSchema.getFinalPayRateBC();
			UNSETTLEMENTRATE = targetMarketInfoSchema.getUNSettlementRate();
			COMUNRATE = targetMarketInfoSchema.getComUNRate();
			FEERATE = targetMarketInfoSchema.getFeeRate();
			Pay_Car_Actual_ValueBC = blProposalCI.getBLPrpTitemCar().getArr(0).getActualValue();
			PayRateCarKind = blProposalCI.getBLPrpTitemCarExt().getArr(0).getPayRateCarKind();

			
			Element TARGEMARKETLIST = body.addElement("TARGET_MARKET_LIST");
			

			String riskCode =  blProposalCI.getBLPrpTmain().getArr(0).getRiskCode();
    		TARGEMARKETLIST.addAttribute("RISKCODE", riskCode);
			Element TARGETMARKETDATA = TARGEMARKETLIST.addElement("TARGET_MARKET_DATA");
			Element eleBUSINESSKINDCODE = TARGETMARKETDATA.addElement("BUSINESSKINDCODE");
			Element eleBUSINESSKINDNAME = TARGETMARKETDATA.addElement("BUSINESSKINDNAME");
			Element eleBUSINESSCLASSCODE = TARGETMARKETDATA.addElement("BUSINESSCLASSCODE");
			Element eleBUSINESSCLASSNAME = TARGETMARKETDATA.addElement("BUSINESSCLASSNAME");
			Element eleMANUALFLAG = TARGETMARKETDATA.addElement("MANUALFLAG");
			Element eleDISRATE = TARGETMARKETDATA.addElement("DISRATE");
			Element eleMANAGEFEERATE = TARGETMARKETDATA.addElement("MANAGEFEERATE");
			Element eleSALESSALARYRATE = TARGETMARKETDATA.addElement("SALESSALARYRATE");
			Element eleTEAMFEERATE = TARGETMARKETDATA.addElement("TEAMFEERATE");
			Element eleOFFICEFEERATE = TARGETMARKETDATA.addElement("OFFICEFEERATE");
			Element eleRISKCODE = TARGETMARKETDATA.addElement("RISKCODE");
			Element eleBIZFLAG = TARGETMARKETDATA.addElement("BIZFLAG");
			Element eleMAXEXPENSERATE = TARGETMARKETDATA.addElement("MAXEXPENSERATE");
			Element elePROFITMARGIN = TARGETMARKETDATA.addElement("PROFITMARGIN");
			Element eleEXPENSESPACE = TARGETMARKETDATA.addElement("EXPENSESPACE");
			Element eleRISKPREMIUM = TARGETMARKETDATA.addElement("RISKPREMIUM");
			Element eleFIXSUMFEE = TARGETMARKETDATA.addElement("FIXSUMFEE");
			Element elePROFITBUSINESSKIND = TARGETMARKETDATA.addElement("PROFITBUSINESSKIND");
			Element eleFEECLASS = TARGETMARKETDATA.addElement("FEECLASS");
			Element eleSALESSALARYFEE = TARGETMARKETDATA.addElement("SALESSALARYFEE");
			Element eleMANAGEFEEFEE = TARGETMARKETDATA.addElement("MANAGEFEEFEE");
			Element eleDISRATEFEE = TARGETMARKETDATA.addElement("DISRATEFEE");
			Element eleZEROPROFITSPREMIUM = TARGETMARKETDATA.addElement("ZEROPROFITSPREMIUM");
			Element eleFIXEDPROFITMARGIN = TARGETMARKETDATA.addElement("FIXEDPROFITMARGIN");
			Element eleDEPOSITRATE = TARGETMARKETDATA.addElement("DEPOSITRATE");
			Element eleFINALPAYRATE = TARGETMARKETDATA.addElement("FINALPAYRATE");
			Element eleFINALBUSINESSCLASS = TARGETMARKETDATA.addElement("FINALBUSINESSCLASS");
			Element elePROFITMARGINBC = TARGETMARKETDATA.addElement("PROFITMARGINBC");
			Element elePROFITBUSINESSCLASSBC = TARGETMARKETDATA.addElement("PROFITBUSINESSCLASSBC");
			Element eleFINALBUSINESSCLASSBC = TARGETMARKETDATA.addElement("FINALBUSINESSCLASSBC");
			Element eleCUSTOMTYPECODE = TARGETMARKETDATA.addElement("CUSTOMTYPECODE");
			Element eleCUSTOMTYPENAME = TARGETMARKETDATA.addElement("CUSTOMTYPENAME");
			Element eleCOSTMARGIN = TARGETMARKETDATA.addElement("COSTMARGIN");
			Element eleCOSTBUSINESSCLASS = TARGETMARKETDATA.addElement("COSTBUSINESSCLASS");
			Element eleCOSTMARGINBC = TARGETMARKETDATA.addElement("COSTMARGINBC");
			Element eleCOSTBUSINESSCLASSBC = TARGETMARKETDATA.addElement("COSTBUSINESSCLASSBC");
			Element eleSUMEXPENSERATE = TARGETMARKETDATA.addElement("SUMEXPENSERATE");
			Element eleOPERATIONCOSTRATE = TARGETMARKETDATA.addElement("OPERATIONCOSTRATE");
			Element eleSALESEXPENSERATE = TARGETMARKETDATA.addElement("SALESEXPENSERATE");
			Element eleSALESEXPENSETHRESHOLD = TARGETMARKETDATA.addElement("SALESEXPENSETHRESHOLD");
			Element eleFINALPAYRATEBC = TARGETMARKETDATA.addElement("FINALPAYRATEBC");
			Element eleUNSETTLEMENTRATE = TARGETMARKETDATA.addElement("UNSETTLEMENTRATE");
			Element eleCOMUNRATE = TARGETMARKETDATA.addElement("COMUNRATE");
			Element eleFEERATE = TARGETMARKETDATA.addElement("FEERATE");
			Element PAYCARACTUALVALUEBC = TARGETMARKETDATA.addElement("PAYCARACTUALVALUEBC");
			Element PAYRATECARKIND = TARGETMARKETDATA.addElement("PAYRATECARKIND");

			
			eleBUSINESSKINDCODE.addText(BUSINESSKINDCODE);
			eleBUSINESSKINDNAME.addText(BUSINESSKINDNAME);
			eleBUSINESSCLASSCODE.addText(BUSINESSCLASSCODE);
			eleBUSINESSCLASSNAME.addText(BUSINESSCLASSNAME);
			eleMANUALFLAG.addText(MANUALFLAG);
			eleDISRATE.addText(DISRATE);
			eleMANAGEFEERATE.addText(MANAGEFEERATE);
			eleSALESSALARYRATE.addText(SALESSALARYRATE);
			eleTEAMFEERATE.addText(TEAMFEERATE);
			eleOFFICEFEERATE.addText(OFFICEFEERATE);
			eleRISKCODE.addText(RISKCODE);
			eleBIZFLAG.addText(BIZFLAG);
			eleMAXEXPENSERATE.addText(MAXEXPENSERATE);
			elePROFITMARGIN.addText(PROFITMARGIN);
			eleEXPENSESPACE.addText(EXPENSESPACE);
			eleRISKPREMIUM.addText(RISKPREMIUM);
			eleFIXSUMFEE.addText(FIXSUMFEE);
			elePROFITBUSINESSKIND.addText(PROFITBUSINESSKIND);
			eleFEECLASS.addText(FEECLASS);
			eleSALESSALARYFEE.addText(SALESSALARYFEE);
			eleMANAGEFEEFEE.addText(MANAGEFEEFEE);
			eleDISRATEFEE.addText(DISRATEFEE);
			eleZEROPROFITSPREMIUM.addText(ZEROPROFITSPREMIUM);
			eleFIXEDPROFITMARGIN.addText(FIXEDPROFITMARGIN);
			eleDEPOSITRATE.addText(DEPOSITRATE);
			eleFINALPAYRATE.addText(FINALPAYRATE);
			eleFINALBUSINESSCLASS.addText(FINALBUSINESSCLASS);
			elePROFITMARGINBC.addText(PROFITMARGINBC);
			elePROFITBUSINESSCLASSBC.addText(PROFITBUSINESSCLASSBC);
			eleFINALBUSINESSCLASSBC.addText(FINALBUSINESSCLASSBC);
			eleCUSTOMTYPECODE.addText(CUSTOMTYPECODE);
			eleCUSTOMTYPENAME.addText(CUSTOMTYPENAME);
			eleCOSTMARGIN.addText(COSTMARGIN);
			eleCOSTBUSINESSCLASS.addText(COSTBUSINESSCLASS);
			eleCOSTMARGINBC.addText(COSTMARGINBC);
			eleCOSTBUSINESSCLASSBC.addText(COSTBUSINESSCLASSBC);
			eleSUMEXPENSERATE.addText(SUMEXPENSERATE);
			eleOPERATIONCOSTRATE.addText(OPERATIONCOSTRATE);
			eleSALESEXPENSERATE.addText(SALESEXPENSERATE);
			eleSALESEXPENSETHRESHOLD.addText(SALESEXPENSETHRESHOLD);
			eleFINALPAYRATEBC.addText(FINALPAYRATEBC);
			eleUNSETTLEMENTRATE.addText(UNSETTLEMENTRATE);
			eleCOMUNRATE.addText(COMUNRATE);
			eleFEERATE.addText(FEERATE);
			PAYCARACTUALVALUEBC.addText(Pay_Car_Actual_ValueBC);  
			PAYRATECARKIND.addText(PayRateCarKind);



		}
		
		if (blProposalBI != null) {
			TargetMarketInfoSchema targetMarketInfoSchema = new TargetMarketInfoSchema();
			targetMarketInfoSchema = (TargetMarketInfoSchema)paramMap.get("targetMarketInfoSchemaBI");
			String BUSINESSKINDCODE ="";
			String BUSINESSKINDNAME ="";
			String BUSINESSCLASSCODE ="";
			String BUSINESSCLASSNAME ="";
			String MANUALFLAG ="";
			String DISRATE ="";
			String MANAGEFEERATE ="";
			String SALESSALARYRATE ="";
			String TEAMFEERATE ="";
			String OFFICEFEERATE ="";
			String RISKCODE ="";
			String BIZFLAG ="";
			String MAXEXPENSERATE ="";
			String PROFITMARGIN ="";
			String EXPENSESPACE ="";
			String RISKPREMIUM ="";
			String FIXSUMFEE ="";
			String PROFITBUSINESSKIND ="";
			String FEECLASS ="";
			String SALESSALARYFEE ="";
			String MANAGEFEEFEE ="";
			String DISRATEFEE ="";
			String ZEROPROFITSPREMIUM ="";
			String FIXEDPROFITMARGIN ="";
			String DEPOSITRATE ="";
			String FINALPAYRATE ="";
			String FINALBUSINESSCLASS ="";
			String PROFITMARGINBC ="";
			String PROFITBUSINESSCLASSBC ="";
			String FINALBUSINESSCLASSBC ="";
			String CUSTOMTYPECODE ="";
			String CUSTOMTYPENAME ="";
			String COSTMARGIN ="";
			String COSTBUSINESSCLASS ="";
			String COSTMARGINBC ="";
			String COSTBUSINESSCLASSBC ="";
			String SUMEXPENSERATE ="";
			String OPERATIONCOSTRATE ="";
			String SALESEXPENSERATE ="";
			String SALESEXPENSETHRESHOLD ="";
			String FINALPAYRATEBC ="";
			String UNSETTLEMENTRATE ="";
			String COMUNRATE ="";
			String FEERATE ="";
			String Pay_Car_Actual_ValueBC = "";
			String PayRateCarKind = "";
			PayRateCarKind =  blProposalBI.getBLPrpTitemCarExt().getArr(0).getPayRateCarKind();


			BUSINESSKINDCODE = targetMarketInfoSchema.getBusinessKindCode();
			BUSINESSKINDNAME = targetMarketInfoSchema.getBusinessKindName();
			BUSINESSCLASSCODE = targetMarketInfoSchema.getBusinessClassCode();
			BUSINESSCLASSNAME = targetMarketInfoSchema.getBusinessClassName();
			MANUALFLAG = targetMarketInfoSchema.getManualFlag();
			DISRATE = targetMarketInfoSchema.getDisRate();
			MANAGEFEERATE = targetMarketInfoSchema.getManageFeeRate();
			SALESSALARYRATE = targetMarketInfoSchema.getSalesSalaryRate();
			TEAMFEERATE = targetMarketInfoSchema.getTeamFeeRate();
			OFFICEFEERATE = targetMarketInfoSchema.getOfficeFeeRate();
			RISKCODE = targetMarketInfoSchema.getRiskCode();
			BIZFLAG = targetMarketInfoSchema.getBizFlag();
			MAXEXPENSERATE = targetMarketInfoSchema.getMaxExpenseRate();
			PROFITMARGIN = targetMarketInfoSchema.getProfitMargin();
			EXPENSESPACE = targetMarketInfoSchema.getExpenseSpace();
			RISKPREMIUM = targetMarketInfoSchema.getRiskPermium();
			FIXSUMFEE = targetMarketInfoSchema.getFixSumFee();
			PROFITBUSINESSKIND = targetMarketInfoSchema.getProfitBusinessKind();
			FEECLASS = targetMarketInfoSchema.getFeeClass();
			SALESSALARYFEE = targetMarketInfoSchema.getSalesSalaryRate();
			MANAGEFEEFEE = targetMarketInfoSchema.getManageFeeRate();
			DISRATEFEE = targetMarketInfoSchema.getDisRate();
			ZEROPROFITSPREMIUM = targetMarketInfoSchema.getZeroProfitsPremium();
			FIXEDPROFITMARGIN = targetMarketInfoSchema.getFixedProfitMargin();
			DEPOSITRATE = targetMarketInfoSchema.getDepositRate();
			FINALPAYRATE = targetMarketInfoSchema.getFinalPayRate();
			FINALBUSINESSCLASS = targetMarketInfoSchema.getFinalBusinessClass();
			PROFITMARGINBC = targetMarketInfoSchema.getProfitMarginBC();
			PROFITBUSINESSCLASSBC = targetMarketInfoSchema.getProfitBusinessClassBC();
			FINALBUSINESSCLASSBC = targetMarketInfoSchema.getFinalBusinessClassBC();
			CUSTOMTYPECODE = targetMarketInfoSchema.getCustomTypeCode();
			CUSTOMTYPENAME = targetMarketInfoSchema.getCustomTypeName();
			COSTMARGIN = targetMarketInfoSchema.getCostMargin();
			COSTBUSINESSCLASS = targetMarketInfoSchema.getCostBusinessClass();
			COSTMARGINBC = targetMarketInfoSchema.getCostMarginBC();
			COSTBUSINESSCLASSBC	 = targetMarketInfoSchema.getCostBusinessClassBC();
			SUMEXPENSERATE = targetMarketInfoSchema.getSumExpenseRate();
			OPERATIONCOSTRATE = targetMarketInfoSchema.getOperationCostRate();
			SALESEXPENSERATE = targetMarketInfoSchema.getSalesExpenseRate();
			SALESEXPENSETHRESHOLD = targetMarketInfoSchema.getSalesExpenseThreshold();
			FINALPAYRATEBC = targetMarketInfoSchema.getFinalPayRateBC();
			UNSETTLEMENTRATE = targetMarketInfoSchema.getUNSettlementRate();
			COMUNRATE = targetMarketInfoSchema.getComUNRate();
			FEERATE = targetMarketInfoSchema.getFeeRate();
			Pay_Car_Actual_ValueBC = blProposalBI.getBLPrpTitemCar().getArr(0).getActualValue();

			Element TARGEMARKETLIST = body.addElement("TARGET_MARKET_LIST");
			String riskCode =  blProposalBI.getBLPrpTmain().getArr(0).getRiskCode();
    		TARGEMARKETLIST.addAttribute("RISKCODE", riskCode);
			Element TARGETMARKETDATA = TARGEMARKETLIST.addElement("TARGET_MARKET_DATA");
			Element eleBUSINESSKINDCODE = TARGETMARKETDATA.addElement("BUSINESSKINDCODE");
			Element eleBUSINESSKINDNAME = TARGETMARKETDATA.addElement("BUSINESSKINDNAME");
			Element eleBUSINESSCLASSCODE = TARGETMARKETDATA.addElement("BUSINESSCLASSCODE");
			Element eleBUSINESSCLASSNAME = TARGETMARKETDATA.addElement("BUSINESSCLASSNAME");
			Element eleMANUALFLAG = TARGETMARKETDATA.addElement("MANUALFLAG");
			Element eleDISRATE = TARGETMARKETDATA.addElement("DISRATE");
			Element eleMANAGEFEERATE = TARGETMARKETDATA.addElement("MANAGEFEERATE");
			Element eleSALESSALARYRATE = TARGETMARKETDATA.addElement("SALESSALARYRATE");
			Element eleTEAMFEERATE = TARGETMARKETDATA.addElement("TEAMFEERATE");
			Element eleOFFICEFEERATE = TARGETMARKETDATA.addElement("OFFICEFEERATE");
			Element eleRISKCODE = TARGETMARKETDATA.addElement("RISKCODE");
			Element eleBIZFLAG = TARGETMARKETDATA.addElement("BIZFLAG");
			Element eleMAXEXPENSERATE = TARGETMARKETDATA.addElement("MAXEXPENSERATE");
			Element elePROFITMARGIN = TARGETMARKETDATA.addElement("PROFITMARGIN");
			Element eleEXPENSESPACE = TARGETMARKETDATA.addElement("EXPENSESPACE");
			Element eleRISKPREMIUM = TARGETMARKETDATA.addElement("RISKPREMIUM");
			Element eleFIXSUMFEE = TARGETMARKETDATA.addElement("FIXSUMFEE");
			Element elePROFITBUSINESSKIND = TARGETMARKETDATA.addElement("PROFITBUSINESSKIND");
			Element eleFEECLASS = TARGETMARKETDATA.addElement("FEECLASS");
			Element eleSALESSALARYFEE = TARGETMARKETDATA.addElement("SALESSALARYFEE");
			Element eleMANAGEFEEFEE = TARGETMARKETDATA.addElement("MANAGEFEEFEE");
			Element eleDISRATEFEE = TARGETMARKETDATA.addElement("DISRATEFEE");
			Element eleZEROPROFITSPREMIUM = TARGETMARKETDATA.addElement("ZEROPROFITSPREMIUM");
			Element eleFIXEDPROFITMARGIN = TARGETMARKETDATA.addElement("FIXEDPROFITMARGIN");
			Element eleDEPOSITRATE = TARGETMARKETDATA.addElement("DEPOSITRATE");
			Element eleFINALPAYRATE = TARGETMARKETDATA.addElement("FINALPAYRATE");
			Element eleFINALBUSINESSCLASS = TARGETMARKETDATA.addElement("FINALBUSINESSCLASS");
			Element elePROFITMARGINBC = TARGETMARKETDATA.addElement("PROFITMARGINBC");
			Element elePROFITBUSINESSCLASSBC = TARGETMARKETDATA.addElement("PROFITBUSINESSCLASSBC");
			Element eleFINALBUSINESSCLASSBC = TARGETMARKETDATA.addElement("FINALBUSINESSCLASSBC");
			Element eleCUSTOMTYPECODE = TARGETMARKETDATA.addElement("CUSTOMTYPECODE");
			Element eleCUSTOMTYPENAME = TARGETMARKETDATA.addElement("CUSTOMTYPENAME");
			Element eleCOSTMARGIN = TARGETMARKETDATA.addElement("COSTMARGIN");
			Element eleCOSTBUSINESSCLASS = TARGETMARKETDATA.addElement("COSTBUSINESSCLASS");
			Element eleCOSTMARGINBC = TARGETMARKETDATA.addElement("COSTMARGINBC");
			Element eleCOSTBUSINESSCLASSBC = TARGETMARKETDATA.addElement("COSTBUSINESSCLASSBC");
			Element eleSUMEXPENSERATE = TARGETMARKETDATA.addElement("SUMEXPENSERATE");
			Element eleOPERATIONCOSTRATE = TARGETMARKETDATA.addElement("OPERATIONCOSTRATE");
			Element eleSALESEXPENSERATE = TARGETMARKETDATA.addElement("SALESEXPENSERATE");
			Element eleSALESEXPENSETHRESHOLD = TARGETMARKETDATA.addElement("SALESEXPENSETHRESHOLD");
			Element eleFINALPAYRATEBC = TARGETMARKETDATA.addElement("FINALPAYRATEBC");
			Element eleUNSETTLEMENTRATE = TARGETMARKETDATA.addElement("UNSETTLEMENTRATE");
			Element eleCOMUNRATE = TARGETMARKETDATA.addElement("COMUNRATE");
			Element eleFEERATE = TARGETMARKETDATA.addElement("FEERATE");
			Element PAYCARACTUALVALUEBC = TARGETMARKETDATA.addElement("PAYCARACTUALVALUEBC");
			Element PAYRATECARKIND = TARGETMARKETDATA.addElement("PAYRATECARKIND");


			eleBUSINESSKINDCODE.addText(BUSINESSKINDCODE);
			eleBUSINESSKINDNAME.addText(BUSINESSKINDNAME);
			eleBUSINESSCLASSCODE.addText(BUSINESSCLASSCODE);
			eleBUSINESSCLASSNAME.addText(BUSINESSCLASSNAME);
			eleMANUALFLAG.addText(MANUALFLAG);
			eleDISRATE.addText(DISRATE);
			eleMANAGEFEERATE.addText(MANAGEFEERATE);
			eleSALESSALARYRATE.addText(SALESSALARYRATE);
			eleTEAMFEERATE.addText(TEAMFEERATE);
			eleOFFICEFEERATE.addText(OFFICEFEERATE);
			eleRISKCODE.addText(RISKCODE);
			eleBIZFLAG.addText(BIZFLAG);
			eleMAXEXPENSERATE.addText(MAXEXPENSERATE);
			elePROFITMARGIN.addText(PROFITMARGIN);
			eleEXPENSESPACE.addText(EXPENSESPACE);
			eleRISKPREMIUM.addText(RISKPREMIUM);
			eleFIXSUMFEE.addText(FIXSUMFEE);
			elePROFITBUSINESSKIND.addText(PROFITBUSINESSKIND);
			eleFEECLASS.addText(FEECLASS);
			eleSALESSALARYFEE.addText(SALESSALARYFEE);
			eleMANAGEFEEFEE.addText(MANAGEFEEFEE);
			eleDISRATEFEE.addText(DISRATEFEE);
			eleZEROPROFITSPREMIUM.addText(ZEROPROFITSPREMIUM);
			eleFIXEDPROFITMARGIN.addText(FIXEDPROFITMARGIN);
			eleDEPOSITRATE.addText(DEPOSITRATE);
			eleFINALPAYRATE.addText(FINALPAYRATE);
			eleFINALBUSINESSCLASS.addText(FINALBUSINESSCLASS);
			elePROFITMARGINBC.addText(PROFITMARGINBC);
			elePROFITBUSINESSCLASSBC.addText(PROFITBUSINESSCLASSBC);
			eleFINALBUSINESSCLASSBC.addText(FINALBUSINESSCLASSBC);
			eleCUSTOMTYPECODE.addText(CUSTOMTYPECODE);
			eleCUSTOMTYPENAME.addText(CUSTOMTYPENAME);
			eleCOSTMARGIN.addText(COSTMARGIN);
			eleCOSTBUSINESSCLASS.addText(COSTBUSINESSCLASS);
			eleCOSTMARGINBC.addText(COSTMARGINBC);
			eleCOSTBUSINESSCLASSBC.addText(COSTBUSINESSCLASSBC);
			eleSUMEXPENSERATE.addText(SUMEXPENSERATE);
			eleOPERATIONCOSTRATE.addText(OPERATIONCOSTRATE);
			eleSALESEXPENSERATE.addText(SALESEXPENSERATE);
			eleSALESEXPENSETHRESHOLD.addText(SALESEXPENSETHRESHOLD);
			eleFINALPAYRATEBC.addText(FINALPAYRATEBC);
			eleUNSETTLEMENTRATE.addText(UNSETTLEMENTRATE);
			eleCOMUNRATE.addText(COMUNRATE);
			eleFEERATE.addText(FEERATE);
			PAYCARACTUALVALUEBC.addText(Pay_Car_Actual_ValueBC); 
			PAYRATECARKIND.addText(PayRateCarKind);



		}
		
	}
	
	/**
     * 	 添加 上一年XX信息 节点
     * @param body
     * @param paramMap
     * @param blProposalMap
     * @throws Exception
     */
	public void addLastPolicy(Element body, BLProposal blProposalCI, BLProposal blProposalBI)throws Exception{
		if (blProposalBI != null || blProposalCI != null) {
			
			int lengthBI=0;
			if (blProposalBI!=null) {
				lengthBI = blProposalBI.getBLCIInsureDemand().getSize();
			}
			int lengthCI=0;
			if (blProposalCI!=null) {
				lengthCI = blProposalCI.getBLCIInsureDemand().getSize();
			}
			
			if (lengthBI > 0 && lengthCI > 0) {
				Element LASTPOLICYLIST = body.addElement("LASTPOLICY_LIST");
				for (int i = 0; i < lengthBI; i++) {
					PrpTmainSchema schema = blProposalBI.getBLPrpTmain().getArr(i);
					String riskCodeStr = schema.getRiskCode();
					
					Element LASTPOLICYDATA = LASTPOLICYLIST.addElement("LASTPOLICY_DATA");
					
					Element RISKCODE = LASTPOLICYDATA.addElement("RISKCODE");
					RISKCODE.addText(riskCodeStr);
				 	CIInsureDemandSchema  insureDemandSchema = blProposalBI.getBLCIInsureDemand().getArr(i);
					
				 	addLastPolicyDataBI(LASTPOLICYDATA, insureDemandSchema);
				}
				for (int i = 0; i < lengthCI; i++) {
					PrpTmainSchema schema = blProposalCI.getBLPrpTmain().getArr(i);
					
					String riskCodeStr = schema.getRiskCode();
					
					Element LASTPOLICYDATA = LASTPOLICYLIST.addElement("LASTPOLICY_DATA");
					
					Element RISKCODE = LASTPOLICYDATA.addElement("RISKCODE");
					RISKCODE.addText(riskCodeStr);
					CIInsureDemandSchema  insureDemandSchema = blProposalCI.getBLCIInsureDemand().getArr(i);
					
				 	addLastPolicyDataBI(LASTPOLICYDATA, insureDemandSchema);
				}
			} else if (lengthBI > 0 && lengthCI == 0) {
				Element LASTPOLICYLIST = body.addElement("LASTPOLICY_LIST");
				for (int i = 0; i < lengthBI; i++) {
					PrpTmainSchema schemaBI = blProposalBI.getBLPrpTmain().getArr(i);
					String riskCodeStrBI = schemaBI.getRiskCode();
					
					Element LASTPOLICYDATA = LASTPOLICYLIST.addElement("LASTPOLICY_DATA");
					
					Element RISKCODEBI = LASTPOLICYDATA.addElement("RISKCODE");
					RISKCODEBI.addText(riskCodeStrBI);
				 	CIInsureDemandSchema  insureDemandSchemaBI = blProposalBI.getBLCIInsureDemand().getArr(i);
					
				 	addLastPolicyDataBI(LASTPOLICYDATA, insureDemandSchemaBI);
				}
				PrpTmainSchema schemaCI = blProposalCI.getBLPrpTmain().getArr(0);
				
		        String riskCodeStrCI = schemaCI.getRiskCode();
		      	
				
				Element LASTPOLICYDATACI = LASTPOLICYLIST.addElement("LASTPOLICY_DATA");
				
				Element RISKCODECI = LASTPOLICYDATACI.addElement("RISKCODE");
				RISKCODECI.addText(riskCodeStrCI);
				CIInsureDemandSchema  insureDemandSchemaCI =new CIInsureDemandSchema();
				
			 	addLastPolicyDataBI(LASTPOLICYDATACI, insureDemandSchemaCI);
			} else if (lengthCI > 0 && lengthBI == 0) {
				Element LASTPOLICYLIST = body.addElement("LASTPOLICY_LIST");
				for (int i = 0; i < lengthCI; i++) {
					PrpTmainSchema schema = blProposalCI.getBLPrpTmain().getArr(i);
					
					String riskCodeStr = schema.getRiskCode();
					
					Element LASTPOLICYDATA = LASTPOLICYLIST.addElement("LASTPOLICY_DATA");
					
					Element RISKCODE = LASTPOLICYDATA.addElement("RISKCODE");
					RISKCODE.addText(riskCodeStr);
					CIInsureDemandSchema  insureDemandSchema = blProposalCI.getBLCIInsureDemand().getArr(i);
					
				 	addLastPolicyDataBI(LASTPOLICYDATA, insureDemandSchema);
				}
				PrpTmainSchema schemaBI = blProposalBI.getBLPrpTmain().getArr(0);
				String riskCodeStrBI = schemaBI.getRiskCode();
				Element LASTPOLICYDATABI = LASTPOLICYLIST.addElement("LASTPOLICY_DATA");
				Element RISKCODE = LASTPOLICYDATABI.addElement("RISKCODE");
				RISKCODE.addText(riskCodeStrBI);
			 	CIInsureDemandSchema  insureDemandSchema = new CIInsureDemandSchema();
				
			 	addLastPolicyDataBI(LASTPOLICYDATABI, insureDemandSchema);
			}else{
				Element LASTPOLICYLIST = body.addElement("LASTPOLICY_LIST");
				
				PrpTmainSchema schemaBI = blProposalBI.getBLPrpTmain().getArr(0);

				String riskCodeStrBI = schemaBI.getRiskCode();
				
				Element LASTPOLICYDATABI = LASTPOLICYLIST.addElement("LASTPOLICY_DATA");
				
				Element RISKCODEBI = LASTPOLICYDATABI.addElement("RISKCODE");
				RISKCODEBI.addText(riskCodeStrBI);
			 	CIInsureDemandSchema  insureDemandSchemaBI = new CIInsureDemandSchema();
				
			 	addLastPolicyDataBI(LASTPOLICYDATABI, insureDemandSchemaBI);

				PrpTmainSchema schemaCI = blProposalCI.getBLPrpTmain().getArr(0);
		        String riskCodeStrCI = schemaCI.getRiskCode();

				
				
		        Element LASTPOLICYDATACI = LASTPOLICYLIST.addElement("LASTPOLICY_DATA");
				Element RISKCODECI = LASTPOLICYDATACI.addElement("RISKCODE");
				RISKCODECI.addText(riskCodeStrCI);
				CIInsureDemandSchema  insureDemandSchemaCI =new CIInsureDemandSchema();
				
			 	addLastPolicyDataBI(LASTPOLICYDATACI, insureDemandSchemaCI);
			}
		}
	}
	/**
	 * 添加  商业XXXXX上一年XX信息  内部数据信息 节点
	 * @param body
	 * @param paramMap
	 * @param blProposalMap
	 * @throws Exception 
	 */
	 public void addLastPolicyDataBI(Element LASTPOLICYDATA, CIInsureDemandSchema  insureDemandSchema)throws Exception{
		
		Element LASTPOLICYQUERYDATE = LASTPOLICYDATA.addElement("LASTPOLICYQUERYDATE");
		String  queryDateStr= insureDemandSchema.getLastPolicyQueryDate();	
		LASTPOLICYQUERYDATE.addText(queryDateStr);
		
		Element LASTBILLDATE  = LASTPOLICYDATA.addElement("LASTBILLDATE");
		String  billDateStr= insureDemandSchema.getLastBillDate();	
		LASTBILLDATE.addText(billDateStr);
		
		Element LASTSTARTDATE  = LASTPOLICYDATA.addElement("LASTSTARTDATE");
		String  startDateStr= insureDemandSchema.getLastYearStartDate();	
		LASTSTARTDATE.addText(startDateStr);
		
		Element LASTENDDATE  = LASTPOLICYDATA.addElement("LASTENDDATE");
		String  endDateStr= insureDemandSchema.getLastYearEndDate();	
		LASTENDDATE.addText(endDateStr);
		
		Element LASTTOTALPREMIUM  = LASTPOLICYDATA.addElement("LASTTOTALPREMIUM");
		String  totalPremiumStr= insureDemandSchema.getLastPolicyTotalPremium();	
		LASTTOTALPREMIUM.addText(totalPremiumStr);
		
		Element LASTBUSINESSCODE  = LASTPOLICYDATA.addElement("LASTBUSINESSCODE");
		String  businesScodeStr= insureDemandSchema.getLastProducerCode();	
		LASTBUSINESSCODE.addText(businesScodeStr);
	 
		}

	/**
	 * 添加 商业XXXXX上一年XX信息 内部数据信息 节点
	 * @param body
	 * @param paramMap
	 * @param blProposalMap
	 * @throws Exception
	 */
    public void addLastPolicyDataCI(Element LASTPOLICYDATA, CIInsureDemandSchema  insureDemandSchema)throws Exception{
    	
		Element LASTPOLICYQUERYDATE = LASTPOLICYDATA.addElement("LASTPOLICYQUERYDATE");
		String  queryDateStr= insureDemandSchema.getLastPolicyQueryDate();	
		LASTPOLICYQUERYDATE.addText(queryDateStr);
		
		Element LASTBILLDATE  = LASTPOLICYDATA.addElement("LASTBILLDATE");
		String  billDateStr= insureDemandSchema.getLastBillDate();	
		LASTBILLDATE.addText(billDateStr);
		
		Element LASTSTARTDATE  = LASTPOLICYDATA.addElement("LASTSTARTDATE");
		String  startDateStr= insureDemandSchema.getLastYearStartDate();	
		LASTSTARTDATE.addText(startDateStr);
		
		Element LASTENDDATE  = LASTPOLICYDATA.addElement("LASTENDDATE");
		String  endDateStr= insureDemandSchema.getLastYearEndDate();	
		LASTENDDATE.addText(endDateStr);
		
		Element LASTTOTALPREMIUM  = LASTPOLICYDATA.addElement("LASTTOTALPREMIUM");
		String  totalPremiumStr= insureDemandSchema.getLastPolicyTotalPremium();	
		LASTTOTALPREMIUM.addText(totalPremiumStr);
		
		Element LASTBUSINESSCODE  = LASTPOLICYDATA.addElement("LASTBUSINESSCODE");
		String  businesScodeStr= insureDemandSchema.getLastProducerCode();	
		LASTBUSINESSCODE.addText(businesScodeStr);
			}
			
	/**
	 * 添加 车辆信息 节点
	 * @param body
	 * @param blProposalCI
	 * @param blProposalBI
	 * @throws Exception
	 */
    
    public void addCar(Element body,  BLProposal blProposalCI, BLProposal blProposalBI)throws Exception{
    	Element carList = body.addElement("CAR_LIST");
    	if (blProposalBI != null) {
    		int lengthBI = blProposalBI.getBLPrpTitemCar().getSize();
    		if (lengthBI > 0) {
	    		for (int i = 0; i < lengthBI; i++) {
					
					Element carData = carList.addElement("CAR_DATA");
					PrpTitemCarSchema  titemCarSchema =  blProposalBI.getBLPrpTitemCar().getArr(i);
					
					PrpTitemCarExtSchema  titemCarExtSchema =  blProposalBI.getBLPrpTitemCarExt().getArr(i);
					addCarData(carData,titemCarSchema,titemCarExtSchema);
					
				}
    		}else{
    			

    		}
			
		}else if (blProposalCI != null) {
			int lengthCI = blProposalCI.getBLPrpTitemCar().getSize();
			if (lengthCI > 0 ) {
				for (int i = 0; i < lengthCI; i++) {
					
					Element carData = carList.addElement("CAR_DATA");
					PrpTitemCarSchema  titemCarSchema =  blProposalCI.getBLPrpTitemCar().getArr(i);
					PrpTitemCarExtSchema  titemCarExtSchema=blProposalCI.getBLPrpTitemCarExt().getArr(i);
					addCarData(carData,titemCarSchema,titemCarExtSchema);
				}
			}else{
				

			}
			
		}
	}
    /**
     * 添加 车辆信息 内部数据信息节点
     * @param body
     * @param paramMap
     * @param blProposalMap
     * @throws Exception
     */
    public void addCarData(Element carData,PrpTitemCarSchema titemCarSchema,PrpTitemCarExtSchema titemCarSchemaExt )throws Exception{
    	
    	Element LICENSENO = carData.addElement("LICENSENO");
    	String licenseNo = titemCarSchema.getLicenseNo();
    	LICENSENO.addText(licenseNo);
    	
    	Element LICENSETYPE = carData.addElement("LICENSETYPE");
    	String licenseType = titemCarSchema.getLicenseKindCode();
    	LICENSETYPE.addText(licenseType);
    	
    	Element MAINCARKINDCODE = carData.addElement("MAINCARKINDCODE");
    	String mainCarKindCode = titemCarSchema.getMainCarKindCode();
    	MAINCARKINDCODE.addText(mainCarKindCode);
    	
    	Element CARKINDCODE = carData.addElement("CARKINDCODE");
    	String CarKindCode = titemCarSchema.getCarKindCode();
    	CARKINDCODE.addText(CarKindCode);
    	
    	Element USENATURECODE = carData.addElement("USENATURECODE");
    	String useNatureCode = titemCarSchema.getUseNatureCode();
    	USENATURECODE.addText(useNatureCode);
    	
    	Element COLORCODE = carData.addElement("COLORCODE");
    	String colorCode = titemCarSchema.getColorCode();
    	COLORCODE.addText(colorCode);
    	
    	Element BRANDNAME = carData.addElement("BRANDNAME");
    	String brandName = titemCarSchema.getBrandName();
    	BRANDNAME.addText(brandName);
    	
    	Element MODELCODE = carData.addElement("MODELCODE");
    	String modelCode = titemCarSchema.getModelCode();
    	MODELCODE.addText(modelCode);
    	
    	Element SEATCOUNT = carData.addElement("SEATCOUNT");
    	String seatCount = titemCarSchema.getSeatCount();
    	SEATCOUNT.addText(seatCount);
    	
    	Element EXHAUSTSCALE = carData.addElement("EXHAUSTSCALE");
    	String exhaustScale = titemCarSchema.getExhaustScale();
    	EXHAUSTSCALE.addText(exhaustScale);
    	
    	Element POWERSCALE = carData.addElement("POWERSCALE");
    	String powerScale = titemCarSchema.getExhaustScale();
    	POWERSCALE.addText(powerScale);
    	
    	Element TONCOUNT = carData.addElement("TONCOUNT");
    	String tonCount = titemCarSchema.getTonCount();
    	TONCOUNT.addText(tonCount);
    	
    	Element FRAMENO = carData.addElement("FRAMENO");
    	String frameNo = titemCarSchema.getFrameNo();
    	FRAMENO.addText(frameNo);
    	
    	Element ENGINENO = carData.addElement("ENGINENO");
    	String engineNo = titemCarSchema.getEngineNo();
    	ENGINENO.addText(engineNo);
    	
    	Element VINNO = carData.addElement("VINNO");
    	String vinNo = titemCarSchema.getVINNo();
    	VINNO.addText(vinNo);
    	
    	Element ENROLLDATE  = carData.addElement("ENROLLDATE ");
    	String enrollDate = titemCarSchema.getEnrollDate();
    	ENROLLDATE.addText(enrollDate);
    	
    	Element COMPLETEKERBMASS = carData.addElement("COMPLETEKERBMASS");
    	String CompleteKerbMass = titemCarSchema.getCompleteKerbMass();
    	COMPLETEKERBMASS.addText(CompleteKerbMass);
    	
    	Element MADEFACTORY = carData.addElement("MADEFACTORY");
    	String madeFactory = titemCarSchema.getMadeFactory();
    	MADEFACTORY.addText(madeFactory);
    	
    	Element MAKEDATE = carData.addElement("MAKEDATE");
    	String makeDate = titemCarSchema.getMakeDate();
    	MAKEDATE.addText(makeDate);
    	
    	Element LICENSECATEGORY = carData.addElement("LICENSECATEGORY");
    	String licenseCategory = titemCarSchema.getLicenseCategory();
    	LICENSECATEGORY.addText(licenseCategory);
    	
    	
		Element CARFLAG =carData.addElement("CARFLAG");
		
		
		Element ProposalNo =carData.addElement("PROPOSALNO");
		String strProposalNo=titemCarSchemaExt.getProposalNo();
		ProposalNo.addText(strProposalNo);
		Element RiskCode = carData.addElement("RISKCODE");
		String strRiskCode=titemCarSchemaExt.getRiskCode();
		RiskCode.addText(strRiskCode);
		Element ItemNo = carData.addElement("ITEMNO");
		String strItemNo=titemCarSchemaExt.getItemNo();
		ItemNo.addText(strItemNo);
		Element LastDamagedA = carData.addElement("LASTDAMAGEDA");
		String strLastDamagedA=titemCarSchemaExt.getLastDamagedA();
		LastDamagedA.addText(strLastDamagedA);
		Element LastDamagedB = carData.addElement("LASTDAMAGEDB");
		String strLastDamagedB=titemCarSchemaExt.getLastDamagedB();
		LastDamagedB.addText(strLastDamagedB);
		Element ThisDamagedA = carData.addElement("THISDAMAGEDA");
		String strThisDamagedA=titemCarSchemaExt.getThisDamagedA();
		ThisDamagedA.addText(strThisDamagedA);
		Element ThisDamagedB = carData.addElement("THISDAMAGEDB");
		String strThisDamagedB=titemCarSchemaExt.getThisDamagedB();
		ThisDamagedB.addText(strThisDamagedB);
		Element CarGoodsType = carData.addElement("CARGOODSTYPE");
		String strCarGoodsType=titemCarSchemaExt.getCarGoodsType();
		CarGoodsType.addText(strCarGoodsType);
		Element NoClaimFavorType = carData.addElement("NOCLAIMFAVORTYPE");
		String strNoClaimFavorType=titemCarSchemaExt.getNoClaimFavorType();
		NoClaimFavorType.addText(strNoClaimFavorType);
		Element Flag = carData.addElement("FLAG");
		String strFlag=titemCarSchemaExt.getFlag();
		Flag.addText(strFlag);
		Element LastDamagedTimes = carData.addElement("LASTDAMAGEDTIMES");
		String strLastDamagedTimes=titemCarSchemaExt.getLastDamagedTimes();
		LastDamagedTimes.addText(strLastDamagedTimes);
		Element ThisDamagedTimes = carData.addElement("THISDAMAGEDTIMES");
		String strThisDamagedTimes=titemCarSchemaExt.getThisDamagedTimes();
		ThisDamagedTimes.addText(strThisDamagedTimes);
		Element DamagedFactorGrade =carData.addElement("DAMAGEDFACTORGRADE");
		String strDamagedFactorGrade=titemCarSchemaExt.getDamagedFactorGrade();
		DamagedFactorGrade.addText(strDamagedFactorGrade);
		Element noneFluctuateFlag = carData.addElement("NONEFLUCTUATEFLAG");
		String strnoneFluctuateFlag=titemCarSchemaExt.getNoneFluctuateFlag();
		noneFluctuateFlag.addText(strnoneFluctuateFlag);
		Element lastOffenceCI = carData.addElement("LASTOFFENCECI");
		String strlastOffenceCI=titemCarSchemaExt.getLastOffenceCI();
		lastOffenceCI.addText(strlastOffenceCI);
		Element thisOffenceCI = carData.addElement("THISOFFENCECI");
		String strthisOffenceCI=titemCarSchemaExt.getThisOffenceCI();
		thisOffenceCI.addText(strthisOffenceCI);
		Element noOffYearsCI = carData.addElement("NOOFFYEARSCI");
		String strnoOffYearsCI=titemCarSchemaExt.getNoOffYearsCI();
		noOffYearsCI.addText(strnoOffYearsCI);
		Element damFloatRatioCI = carData.addElement("DAMFLOATRATIOCI");
		String strdamFloatRatioCI=titemCarSchemaExt.getDamFloatRatioCI();
		damFloatRatioCI.addText(strdamFloatRatioCI);
		Element offFloatRatioCI = carData.addElement("OFFFLOATRATIOCI");
        String stroffFloatRatioCI=titemCarSchemaExt.getOffFloatRatioCI();
        offFloatRatioCI.addText(stroffFloatRatioCI);
		Element noDamageYears=carData.addElement("NODAMAGEYEARS");
		String strnoDamageYears=titemCarSchemaExt.getNoDamageYears();
		noDamageYears.addText(strnoDamageYears);
        Element inDoorFlag=carData.addElement("INDOORFLAG");
		String strinDoorFlag=titemCarSchemaExt.getInDoorFlag();
		inDoorFlag.addText(strinDoorFlag);
		Element claimAmountValue=carData.addElement("CLAIMAMOUNTVALUE");
        String strclaimAmountValue=titemCarSchemaExt.getClaimAmountValue();
        claimAmountValue.addText(strclaimAmountValue);
		Element controlFlag=carData.addElement("CONTROLFLAG");
        String strcontrolFlag=titemCarSchemaExt.getControlFlag();
        controlFlag.addText(strcontrolFlag);
		Element fuelType=carData.addElement("FUELTYPE");
        String strfuelType=titemCarSchemaExt.getFuelType();
        fuelType.addText(strfuelType);
		Element buyCarDate=carData.addElement("BUYCARDATE");
        String strbuyCarDate=titemCarSchemaExt.getBuyCarDate();
        buyCarDate.addText(strbuyCarDate);		
		Element brandECode=carData.addElement("BRANDECODE");
        String strbrandECode=titemCarSchemaExt.getBrandECode();
        brandECode.addText(strbrandECode);		
		Element licenceDate=carData.addElement("LICENCEDATE");
        String strlicenceDate=titemCarSchemaExt.getLicenceDate();
        licenceDate.addText(strlicenceDate);
        Element shieldFirewallFlag=carData.addElement("SHIELDFIREWALLFLAG");
        String strshieldFirewallFlag=titemCarSchemaExt.getShieldFirewall();
        shieldFirewallFlag.addText(strshieldFirewallFlag);
        Element fundAmount=carData.addElement("FUNDAMOUNT"); 
        String strfundAmount=titemCarSchemaExt.getFundAmount();
        fundAmount.addText(strfundAmount);
        Element fundRate=carData.addElement("FUNDRATE");
        String strfundRate=titemCarSchemaExt.getFundRate();
        fundRate.addText(strfundRate);
		Element PayRateCarKind=carData.addElement("PAYRATECARKIND");
        String strPayRateCarKind=titemCarSchemaExt.getPayRateCarKind();
        PayRateCarKind.addText(strPayRateCarKind);
    	
		
		
		
	}
    /**
     * 添加  代缴车船税信息 节点
     * @param body
     * @param blProposalCI
     * @throws Exception
     */
    
    public void addCarTax(Element body,BLProposal blProposalCI)throws Exception{
    	Element carTaxList = body.addElement("CARTAX_LIST");
    	if (blProposalCI != null) {
    		int length = blProposalCI.getBLPrpTcarshipTax().getSize();
    		if (length > 0) {
	    		for (int i = 0; i < length; i++) {
	    			PrpTcarshipTaxSchema carShipTaxSchema = blProposalCI.getBLPrpTcarshipTax().getArr(i);
	    			
	    			Element carTaxData = carTaxList.addElement("CARTAX_DATA");
					
	    			addCarTaxData(carTaxData, carShipTaxSchema);
				}
    		}else{
    			

    		}
		}
	}
    /**
     * 添加  代缴车船税信息 内部信息 节点
     * @param body
     * @param blProposalCI
     * @throws Exception
     */
    public void addCarTaxData(Element carTaxData,PrpTcarshipTaxSchema carShipTaxSchema)throws Exception{
    	
    	Element AREACODE = carTaxData.addElement("AREACODE");
    	String areaCode = carShipTaxSchema.getAreaCode();
    	AREACODE.addText(areaCode);
    	
    	Element TAXITEMCODE = carTaxData.addElement("TAXITEMCODE");
    	String taxItemCode = carShipTaxSchema.getTaxItemCode();
    	TAXITEMCODE.addText(taxItemCode);
    	
    	Element TAXITEMNAME = carTaxData.addElement("TAXITEMNAME");
    	String taxItemName = carShipTaxSchema.getTaxItemName();
    	TAXITEMNAME.addText(taxItemName);
    	
    	Element TAXITEMDETAILCODE = carTaxData.addElement("TAXITEMDETAILCODE");
    	String taxItemDetailCode = carShipTaxSchema.getTaxItemDetailCode();
    	TAXITEMDETAILCODE.addText(taxItemDetailCode);
    	
    	Element TAXITEMDETAILNAME = carTaxData.addElement("TAXITEMDETAILNAME");
    	String taxItemDetailName = carShipTaxSchema.getTaxItemDetailName();
    	TAXITEMDETAILNAME.addText(taxItemDetailName);
    	
    	Element BASETAXATION = carTaxData.addElement("BASETAXATION");
    	String baseTaxation = carShipTaxSchema.getBaseTaxation();
    	BASETAXATION.addText(baseTaxation);
    	
    	Element TAXPAYERCODE = carTaxData.addElement("TAXPAYERCODE");
    	String taxpayerCode = carShipTaxSchema.getTaxpayerCode();
    	TAXPAYERCODE.addText(taxpayerCode);
    	
    	Element TAXPAYERNAME = carTaxData.addElement("TAXPAYERNAME");
    	String taxpayerName = carShipTaxSchema.getTaxpayerName();
    	TAXPAYERNAME.addText(taxpayerName);
    	
    	Element IDENTIFYNUMBER = carTaxData.addElement("IDENTIFYNUMBER");
    	String identifyNumber = carShipTaxSchema.getIdentifyNumber();
    	IDENTIFYNUMBER.addText(identifyNumber);
    	
    	Element TAXPAYERIDENTIFIER = carTaxData.addElement("TAXPAYERIDENTIFIER");
    	String taxpayerIdentifier = carShipTaxSchema.getTaxpayerIdentifier();
    	TAXPAYERIDENTIFIER.addText(taxpayerIdentifier);
    	
    	Element TAXRELIFFLAG = carTaxData.addElement("TAXRELIFFLAG");
    	String taxRelifFlag = carShipTaxSchema.getTaxRelifFlag();
    	TAXRELIFFLAG.addText(taxRelifFlag);
    	
    	Element RELIFREASON = carTaxData.addElement("RELIFREASON");
    	String relifReason = carShipTaxSchema.getRelifReason();
    	RELIFREASON.addText(relifReason);
    	
    	Element FREERATE = carTaxData.addElement("FREERATE");
    	String freeRate = carShipTaxSchema.getFreeRate();
    	FREERATE.addText(freeRate);
    	
    	Element FREERATETEXT = carTaxData.addElement("FREERATETEXT");
    	String freeRateText = carShipTaxSchema.getFreeRateText();
    	FREERATETEXT.addText(freeRateText);
    	
    	Element TAXCOMCODE = carTaxData.addElement("TAXCOMCODE");
    	String taxComCode = carShipTaxSchema.getTaxComCode();
    	TAXCOMCODE.addText(taxComCode);
    	
    	Element TAXCOMNAME = carTaxData.addElement("TAXCOMNAME");
    	String taxComName = carShipTaxSchema.getTaxComName();
    	TAXCOMNAME.addText(taxComName);
    	
    	Element PAIDFREECERTIFICATE = carTaxData.addElement("PAIDFREECERTIFICATE");
    	String paidFreeCertificate = carShipTaxSchema.getPaidFreeCertificate();
    	PAIDFREECERTIFICATE.addText(paidFreeCertificate);
    	
    	Element TAXUNIT = carTaxData.addElement("TAXUNIT");
    	String taxUnit = carShipTaxSchema.getTaxUnit();
    	TAXUNIT.addText(taxUnit);
    	
    	Element TAXUNITTEXT = carTaxData.addElement("TAXUNITTEXT");
    	String taxUnitText = carShipTaxSchema.getTaxUnitText();
    	TAXUNITTEXT.addText(taxUnitText);
    	
    	Element CALCULATEMODE = carTaxData.addElement("CALCULATEMODE");
    	String calculateMode = carShipTaxSchema.getCalculateMode();
    	CALCULATEMODE.addText(calculateMode);
    	
    	Element CALCULATEFLAG = carTaxData.addElement("CALCULATEFLAG");
    	String calculateFlag = carShipTaxSchema.getCalculateFlag();
    	CALCULATEFLAG.addText(calculateFlag);
    	
    	Element PAYLASTYEAR = carTaxData.addElement("PAYLASTYEAR");
    	String payLastYear = carShipTaxSchema.getPayLastYear();
    	PAYLASTYEAR.addText(payLastYear);
    	
    	Element HISPOLICYENDDATE = carTaxData.addElement("HISPOLICYENDDATE");
    	String hisPolicyEndDate = carShipTaxSchema.getHisPolicyEndDate();
    	HISPOLICYENDDATE.addText(hisPolicyEndDate);
    	
    	Element TAXDUE = carTaxData.addElement("TAXDUE");
    	String taxDue = carShipTaxSchema.getTaxDue();
    	TAXDUE.addText(taxDue);
    	
    	Element TAXACTUAL = carTaxData.addElement("TAXACTUAL");
    	String taxActual = carShipTaxSchema.getTaxActual();
    	TAXACTUAL.addText(taxActual);
    	
    	Element TAXRELIEF = carTaxData.addElement("TAXRELIEF");
    	String taxRelief = carShipTaxSchema.getTaxRelief();
    	TAXRELIEF.addText(taxRelief);
    	
    	Element PAYSTARTDATE = carTaxData.addElement("PAYSTARTDATE");
    	String payStartDate = carShipTaxSchema.getPayStartDate();
    	PAYSTARTDATE.addText(payStartDate);
    	
    	Element PAYENDDATE = carTaxData.addElement("PAYENDDATE");
    	String payEndDate = carShipTaxSchema.getPayEndDate();
    	PAYENDDATE.addText(payEndDate);
    	
    	Element PREVIOUSPAY = carTaxData.addElement("PREVIOUSPAY");
    	String previousPay = carShipTaxSchema.getPreviousPay();
    	PREVIOUSPAY.addText(previousPay);
    	
    	Element LATEFEE = carTaxData.addElement("LATEFEE");
    	String lateFee = carShipTaxSchema.getLateFee();
    	LATEFEE.addText(lateFee);
    	
    	Element LATEFEESTARTDATE = carTaxData.addElement("LATEFEESTARTDATE");
    	String lateFeeStartDate = carShipTaxSchema.getLateFeeStartDate();
    	LATEFEESTARTDATE.addText(lateFeeStartDate);
    	
    	Element LATEFEEENDDATE = carTaxData.addElement("LATEFEEENDDATE");
    	String lateFeeEndDate = carShipTaxSchema.getLateFeeEndDate();
    	LATEFEEENDDATE.addText(lateFeeEndDate);
    	
    	Element SUMPAYTAX = carTaxData.addElement("SUMPAYTAX");
    	String sumPayTax = carShipTaxSchema.getSumPayTax();
    	SUMPAYTAX.addText(sumPayTax);
    	
    	Element LEVIEDDATE = carTaxData.addElement("LEVIEDDATE");
    	String leviedDate = carShipTaxSchema.getLeviedDate();
    	LEVIEDDATE.addText(leviedDate);
    	
    	Element PAYTAXTIMES = carTaxData.addElement("PAYTAXTIMES");
    	String payTaxTimes = carShipTaxSchema.getPayTaxTimes();
    	PAYTAXTIMES.addText(payTaxTimes);
    	
    	Element FINALFLAG = carTaxData.addElement("FINALFLAG");
    	String finalFlag = carShipTaxSchema.getFinalFlag();
    	FINALFLAG.addText(finalFlag);
    	
    	Element EXTENDCHAR1 = carTaxData.addElement("EXTENDCHAR1");
    	String extendChar1 = carShipTaxSchema.getExtendChar1();
    	EXTENDCHAR1.addText(extendChar1);
    	
    	Element EXTENDCHAR2 = carTaxData.addElement("EXTENDCHAR2");
    	String extendChar2 = carShipTaxSchema.getExtendChar2();
    	EXTENDCHAR2.addText(extendChar2);
    	
    	Element EXTENDNUM1 = carTaxData.addElement("EXTENDNUM1");
    	String extendNum1 = carShipTaxSchema.getExtendNum1();
    	EXTENDNUM1.addText(extendNum1);
    	
    	Element EXTENDNUM2 = carTaxData.addElement("EXTENDNUM2");
    	String extendNum2 = carShipTaxSchema.getExtendNum2();
    	EXTENDNUM2.addText(extendNum2);
    	
    	Element EXTENDDATE1 = carTaxData.addElement("EXTENDDATE1");
    	String extendDate1 = carShipTaxSchema.getExtendDate1();
    	EXTENDDATE1.addText(extendDate1);
    	
    	Element EXTENDDATE2 = carTaxData.addElement("EXTENDDATE2");
    	String extendDate2 = carShipTaxSchema.getExtendDate2();
    	EXTENDDATE2.addText(extendDate2);
    	
    	Element FLAG = carTaxData.addElement("FLAG");
    	String flag = carShipTaxSchema.getFlag();
    	FLAG.addText(flag);
    	
    	Element PAYBALANCEFEE = carTaxData.addElement("PAYBALANCEFEE");
    	String payBalanceFee = carShipTaxSchema.getPayBalanceFee();
    	PAYBALANCEFEE.addText(payBalanceFee);
    	
    	Element CALFEEFLAG = carTaxData.addElement("CALFEEFLAG");
    	String calFeeFlag = carShipTaxSchema.getCalFeeFlag();
    	CALFEEFLAG.addText(calFeeFlag);
    	
    	Element CERTIFICATEDATE = carTaxData.addElement("CERTIFICATEDATE");
    	String certificateDate = carShipTaxSchema.getCertificateDate();
    	CERTIFICATEDATE.addText(certificateDate);
    	
    	Element TAXFLAG = carTaxData.addElement("TAXFLAG");
    	String taxFlag = carShipTaxSchema.getTaxFlag();
    	TAXFLAG.addText(taxFlag);
    	
    	Element CALCTAXFLAG = carTaxData.addElement("CALCTAXFLAG");
    	String calcTaxFlag = carShipTaxSchema.getCalcTaxFlag();
    	CALCTAXFLAG.addText(calcTaxFlag);
    	
    }
    /**
     * 添加 天津代缴车船税信息 节点
     * @param body
     * @param blProposalCI
     * @throws Exception
     */
    
    public void addCarTaxTJ(Element body, BLProposal blProposalCI)throws Exception{
    	Element carTaxTJList = body.addElement("CARTAXTJ_LIST");
    	if (blProposalCI != null) {
    		int length = blProposalCI.getBLPrpTcarshipTaxTJ().getSize();
    		if (length > 0) {
	    		for (int i = 0; i < length; i++) {
	    			PrpTcarshipTaxTJSchema carShipTaxTJSchema = blProposalCI.getBLPrpTcarshipTaxTJ().getArr(i);
	    			
	    			Element carTaxTJData = carTaxTJList.addElement("CARTAXTJ_DATA");
					
	    			addCarTaxTJData(carTaxTJData, carShipTaxTJSchema);
				}
    		}else{
    			

    		}
		}
	}
    /**
     * 添加 天津车船税 内部信息 节点
     * @param body
     * @param paramMap
     * @param blProposalMap
     * @throws Exception
     */
    public void addCarTaxTJData(Element carTaxTJData,PrpTcarshipTaxTJSchema carShipTaxTJSchema)throws Exception{
    	
    	Element TAXCOMCODE = carTaxTJData.addElement("TAXCOMCODE");
    	String taxComCode = carShipTaxTJSchema.getTaxComCode();
    	TAXCOMCODE.addText(taxComCode);
    	
    	Element PAIDFREECERTIFICATE = carTaxTJData.addElement("PAIDFREECERTIFICATE");
    	String paidFreeCertificate = carShipTaxTJSchema.getPaidFreeCertificate();
    	PAIDFREECERTIFICATE.addText(paidFreeCertificate);
    	
    	Element TAXACTUAL = carTaxTJData.addElement("TAXACTUAL");
    	String taxActual = carShipTaxTJSchema.getTaxActual();
    	TAXACTUAL.addText(taxActual);
    	
    	Element PREVIOUSPAY = carTaxTJData.addElement("PREVIOUSPAY");
    	String previousPay = carShipTaxTJSchema.getPreviousPay();
    	PREVIOUSPAY.addText(previousPay);
    	
    	Element LATEFEE = carTaxTJData.addElement("LATEFEE");
    	String lateFee = carShipTaxTJSchema.getLateFee();
    	LATEFEE.addText(lateFee);
     	
    	Element SUMPAYTAX = carTaxTJData.addElement("SUMPAYTAX");
    	String sumPayTax = carShipTaxTJSchema.getSumPayTax();
    	SUMPAYTAX.addText(sumPayTax);
    	
    	Element TAXTYPE = carTaxTJData.addElement("TAXTYPE");
    	String taxType = carShipTaxTJSchema.getTaxType();
    	TAXTYPE.addText(taxType);
    	
    	Element CAROWNERNATURE = carTaxTJData.addElement("CAROWNERNATURE");
    	String carOwnerNature = carShipTaxTJSchema.getCarOwnerNature();
    	CAROWNERNATURE.addText(carOwnerNature);
    	
    	Element RELIEFFLAG = carTaxTJData.addElement("RELIEFFLAG");
    	String reliefFlag = carShipTaxTJSchema.getReliefFlag();
    	RELIEFFLAG.addText(reliefFlag);
    	
    	Element ENTAXNO = carTaxTJData.addElement("ENTAXNO");
    	String enTaxNo = carShipTaxTJSchema.getEnTaxNo();
    	ENTAXNO.addText(enTaxNo);
    	
    	Element TAXACTUAL2 = carTaxTJData.addElement("TAXACTUAL2");
    	String taxActual2 = carShipTaxTJSchema.getTaxActual2();
    	TAXACTUAL2.addText(taxActual2);
        
    	Element PREVIOUSPAY2 = carTaxTJData.addElement("PREVIOUSPAY2");
    	String previousPay2 = carShipTaxTJSchema.getPreviousPay2();
    	PREVIOUSPAY2.addText(previousPay2);
    	 
    	Element CAROWNERNAME = carTaxTJData.addElement("CAROWNERNAME");
    	String carOwnerName = carShipTaxTJSchema.getCarOwnerName();
    	CAROWNERNAME.addText(carOwnerName);
    	 
    	Element IDENTIFYTYPE = carTaxTJData.addElement("IDENTIFYTYPE");
    	String identifyType = carShipTaxTJSchema.getIdentifyType();
    	IDENTIFYTYPE.addText(identifyType);
    	
     	Element IDENTIFYNUMBER = carTaxTJData.addElement("IDENTIFYNUMBER");
    	String identifyNumber = carShipTaxTJSchema.getIdentifyNumber();
    	IDENTIFYNUMBER.addText(identifyNumber);
    	 
    	Element ADDRESSNAME = carTaxTJData.addElement("ADDRESSNAME");
    	String addressName = carShipTaxTJSchema.getAddressName();
    	ADDRESSNAME.addText(addressName);
    	
     	Element POSTADDRESS = carTaxTJData.addElement("POSTADDRESS");
    	String postAddress = carShipTaxTJSchema.getPostAddress();
    	POSTADDRESS.addText(postAddress);
    	
    	Element POSTCODE = carTaxTJData.addElement("POSTCODE");
    	String postCode = carShipTaxTJSchema.getPostCode();
    	POSTCODE.addText(postCode);
    	
    	Element PHONENUMBER = carTaxTJData.addElement("PHONENUMBER");
    	String phoneNumber = carShipTaxTJSchema.getPhoneNumber();
    	PHONENUMBER.addText(phoneNumber);
    	
    	Element COUNTRYCODE = carTaxTJData.addElement("COUNTRYCODE");
    	String countryCode = carShipTaxTJSchema.getCountryCode();
    	COUNTRYCODE.addText(countryCode);
    	
    	Element COMPANYNAME = carTaxTJData.addElement("COMPANYNAME");
    	String companyName = carShipTaxTJSchema.getCompanyName();
    	COMPANYNAME.addText(companyName);
    	
    	Element UNITCERTINO = carTaxTJData.addElement("UNITCERTINO");
    	String unitCertiNo = carShipTaxTJSchema.getUnitCertiNo();
    	UNITCERTINO.addText(unitCertiNo);
    	
    	Element REGISTEREDADDRESS = carTaxTJData.addElement("REGISTEREDADDRESS");
    	String registeredAddress = carShipTaxTJSchema.getRegisteredAddress();
    	REGISTEREDADDRESS.addText(registeredAddress);
    	
    	Element CARADDRESSCODE = carTaxTJData.addElement("CARADDRESSCODE");
    	String carAddressCode = carShipTaxTJSchema.getCarAddressCode();
    	CARADDRESSCODE.addText(carAddressCode);
    	
    	Element TRANSACTIONDATE = carTaxTJData.addElement("TRANSACTIONDATE");
    	String transactionDate = carShipTaxTJSchema.getTransactionDate();
    	TRANSACTIONDATE.addText(transactionDate);
    	
    	Element PRELICENSENO = carTaxTJData.addElement("PRELICENSENO");
    	String preLicenseNo = carShipTaxTJSchema.getPreLicenseNo();
    	PRELICENSENO.addText(preLicenseNo);
    	
    	Element FINALFLAG = carTaxTJData.addElement("FINALFLAG");
    	String finalFlag = carShipTaxTJSchema.getFinalFlag();
    	FINALFLAG.addText(finalFlag);
    	
    	Element CALCULATEFLAG = carTaxTJData.addElement("CALCULATEFLAG");
    	String calculateFlag = carShipTaxTJSchema.getCalculateFlag();
    	CALCULATEFLAG.addText(calculateFlag);
    	
    	Element EXTENDCHAR1 = carTaxTJData.addElement("EXTENDCHAR1");
    	String extendChar1 = carShipTaxTJSchema.getExtendChar1();
    	EXTENDCHAR1.addText(extendChar1);
    	
    	Element EXTENDCHAR2 = carTaxTJData.addElement("EXTENDCHAR2");
    	String extendChar2 = carShipTaxTJSchema.getExtendChar2();
    	EXTENDCHAR2.addText(extendChar2);
    	
    	Element EXTENDNUM1 = carTaxTJData.addElement("EXTENDNUM1");
    	String extendNum1 = carShipTaxTJSchema.getExtendNum1();
    	EXTENDNUM1.addText(extendNum1);
    	
    	Element EXTENDNUM2 = carTaxTJData.addElement("EXTENDNUM2");
    	String extendNum2 = carShipTaxTJSchema.getExtendNum2();
    	EXTENDNUM2.addText(extendNum2);
    	
    	Element EXTENDDATE1 = carTaxTJData.addElement("EXTENDDATE1");
    	String extendDate1 = carShipTaxTJSchema.getExtendDate1();
    	EXTENDDATE1.addText(extendDate1);
    	
    	Element EXTENDDATE2 = carTaxTJData.addElement("EXTENDDATE2");
    	String extendDate2 = carShipTaxTJSchema.getExtendDate2();
    	EXTENDDATE2.addText(extendDate2);
    	
    	Element PAYTAXDATE =  carTaxTJData.addElement("PAYTAXDATE");
    	
    	
    	Element TAXNUM =  carTaxTJData.addElement("TAXNUM");
    	
    	
    	Element TAXNUMTYPE =  carTaxTJData.addElement("TAXNUMTYPE");
    }
    /**
     * 添加 XXXXX别信息 节点
     * @param body
     * @param blProposalCI
     * @param blProposalBI
     * @throws Exception
     */
    
    public void addKind(Element body,  BLProposal blProposalCI, BLProposal blProposalBI)throws Exception{
    	if (blProposalCI != null) {
    		
    		Element KIND_LIST = body.addElement("KIND_LIST");
    		String riskCode =  blProposalCI.getBLPrpTmain().getArr(0).getRiskCode();
    		KIND_LIST.addAttribute("RISKCODE", riskCode);
    		int length = blProposalCI.getBLPrpTitemKind().getSize();
    		if (length > 0) {
	    		for (int i = 0; i < length; i++) {
	    			PrpTitemKindSchema  KindSchema = blProposalCI.getBLPrpTitemKind().getArr(i);
	    			Element KINDDATA = KIND_LIST.addElement("KIND_DATA");
					addKindData(KINDDATA, KindSchema);
				}
    		}else{
    			

    		}
		}
    	if (blProposalBI != null) {
    		
    		Element KIND_LIST = body.addElement("KIND_LIST");
    		String riskCode =  blProposalBI.getBLPrpTmain().getArr(0).getRiskCode();
    		KIND_LIST.addAttribute("RISKCODE", riskCode);
    		int length = blProposalBI.getBLPrpTitemKind().getSize();
    		if (length > 0) {
	    		for (int i = 0; i < length; i++) {
	    			PrpTitemKindSchema  KindSchema = blProposalBI.getBLPrpTitemKind().getArr(i);
	    			Element KINDDATA = KIND_LIST.addElement("KIND_DATA");
					addKindData(KINDDATA, KindSchema);
				}
    		}else{
    			

    		}
		}
    	
	}
    /**
     * 添加  XXXXX别信息 内部信息节点 
     * @param KIND_LIST
     * @param KindSchema
     * @throws Exception
     */
    public void addKindData(Element KINDDATA, PrpTitemKindSchema  KindSchema)throws Exception{
    	
    	Element ITEMKINDNO = KINDDATA.addElement("ITEMKINDNO");
    	String itemKindNo = KindSchema.getItemKindNo();
    	ITEMKINDNO.addText(itemKindNo);
    	
    	Element KINDCODE = KINDDATA.addElement("KINDCODE");
    	String kindCode = KindSchema.getKindCode();
    	KINDCODE.addText(kindCode);
    	Element KINDNAME = KINDDATA.addElement("KINDNAME");
    	String kindname = KindSchema.getKindName();
    	KINDNAME.addText(kindname);
    	
    	Element MODECODE = KINDDATA.addElement("MODECODE");
    	String modeCode = KindSchema.getModeCode();
    	MODECODE.addText(modeCode);
    	Element MODENAME = KINDDATA.addElement("MODENAME");
    	String modeName = KindSchema.getModeName();
    	MODENAME.addText(modeName);
    	
    	Element STARTDATE = KINDDATA.addElement("STARTDATE");
    	String startDate = KindSchema.getStartDate();
    	STARTDATE.addText(startDate);
    	
    	Element STARTHOUR = KINDDATA.addElement("STARTHOUR");
    	String startHour = KindSchema.getStartHour();
    	STARTHOUR.addText(startHour);
    	
    	Element ENDDATE = KINDDATA.addElement("ENDDATE");
    	String endDate = KindSchema.getEndDate();
    	ENDDATE.addText(endDate);
    	
    	Element ENDHOUR = KINDDATA.addElement("ENDHOUR");
    	String endHour = KindSchema.getEndHour();
    	ENDHOUR.addText(endHour);
    	
    	Element CALCULATEFLAG = KINDDATA.addElement("CALCULATEFLAG");
    	String calculateFlag = KindSchema.getCalculateFlag();
    	CALCULATEFLAG.addText(calculateFlag);
    	
    	Element CURRENCY = KINDDATA.addElement("CURRENCY");
    	String currency = KindSchema.getCurrency();
    	CURRENCY.addText(currency);
    	
    	Element UNITAMOUNT = KINDDATA.addElement("UNITAMOUNT");
    	String unitAmount = KindSchema.getUnitAmount();
    	UNITAMOUNT.addText(unitAmount);
    	
    	Element QUANTITY = KINDDATA.addElement("QUANTITY");
    	String quantity = KindSchema.getQuantity();
    	QUANTITY.addText(quantity);
    	
    	Element VALUE = KINDDATA.addElement("VALUE");
    	String value = KindSchema.getValue();
    	VALUE.addText(value);
    	
    	Element AMOUNT = KINDDATA.addElement("AMOUNT");
    	String amount = KindSchema.getAmount();
    	AMOUNT.addText(amount);
    	
    	Element RATEPERIOD = KINDDATA.addElement("RATEPERIOD");
    	String ratePeriod = KindSchema.getRatePeriod();
    	RATEPERIOD.addText(ratePeriod);
    	
    	Element RATE = KINDDATA.addElement("RATE");
    	String rate = KindSchema.getRate();
    	RATE.addText(rate);
    	
    	Element SHORTRATEFLAG = KINDDATA.addElement("SHORTRATEFLAG");
    	String shortRateFlag = KindSchema.getShortRateFlag();
    	SHORTRATEFLAG.addText(shortRateFlag);
    	
    	Element SHORTRATE = KINDDATA.addElement("SHORTRATE");
    	String shortRate = KindSchema.getShortRate();
    	SHORTRATE.addText(shortRate);
    	
    	Element BASEPREMIUM = KINDDATA.addElement("BASEPREMIUM");
    	String basePremium = KindSchema.getBasePremium();
    	BASEPREMIUM.addText(basePremium);
    	
    	Element BENCHMARKPREMIUM = KINDDATA.addElement("BENCHMARKPREMIUM");
    	String benchMarkPremium = KindSchema.getBenchMarkPremium();
    	BENCHMARKPREMIUM.addText(benchMarkPremium);
    	
    	Element DISCOUNT = KINDDATA.addElement("DISCOUNT");
    	String discount = KindSchema.getDiscount();
    	DISCOUNT.addText(discount);
    	
    	Element ADJUSTRATE = KINDDATA.addElement("ADJUSTRATE");
    	String adjustRate = KindSchema.getAdjustRate();
    	ADJUSTRATE.addText(adjustRate);
    	
    	Element PREMIUM = KINDDATA.addElement("PREMIUM");
    	String premium = KindSchema.getPremium();
    	PREMIUM.addText(premium);
    	
    	Element DEDUCTIBLERATE = KINDDATA.addElement("DEDUCTIBLERATE");
    	String deductibleRate = KindSchema.getDeductibleRate();
    	DEDUCTIBLERATE.addText(deductibleRate);
    	
    	Element DEDUCTIBLE = KINDDATA.addElement("DEDUCTIBLE");
    	String deductible = KindSchema.getDeductible();
    	DEDUCTIBLE.addText(deductible);
    	
    	Element FLAG = KINDDATA.addElement("FLAG");
    	String flag = KindSchema.getFlag();
    	FLAG.addText(flag);
	}
    /**
     * 添加  优惠信息 节点
     * @param body
     * @param blProposalCI
     * @param blProposalBI
     * @throws Exception
     */
    
    public void addProfitDetail(Element body, BLProposal blProposalCI, BLProposal blProposalBI)throws Exception{
    	if (blProposalCI != null) {
    		
    		Element PROFITDETAIL_LIST = body.addElement("PROFITDETAIL_LIST");
    		String riskCode =  blProposalCI.getBLPrpTmain().getArr(0).getRiskCode();
    		PROFITDETAIL_LIST.addAttribute("RISKCODE", riskCode);
    		int length = blProposalCI.getBLPrpTprofitDetail().getSize();
    		if (length > 0) {
	    		for (int i = 0; i < length; i++) {
	    			PrpTprofitDetailSchema  detailSchema = blProposalCI.getBLPrpTprofitDetail().getArr(i);
	    			Element PROFITDETAILDATA = PROFITDETAIL_LIST.addElement("PROFITDETAIL_DATA");
	    			
	    			addProfitDetailData(PROFITDETAILDATA, detailSchema);
				}
    		}else{
    			

    		}
		}
    	if (blProposalBI != null) {
    		
    		Element KIND_LIST = body.addElement("PROFITDETAIL_LIST");
    		String riskCode =  blProposalBI.getBLPrpTmain().getArr(0).getRiskCode();
    		KIND_LIST.addAttribute("RISKCODE", riskCode);
    		int length = blProposalBI.getBLPrpTprofitDetail().getSize();
    		if (length > 0) {
	    		for (int i = 0; i < length; i++) {
	    			PrpTprofitDetailSchema  datailschema = blProposalBI.getBLPrpTprofitDetail().getArr(i);
	    			Element PROFITDETAILDATA = KIND_LIST.addElement("PROFITDETAIL_DATA");
	    			
					addProfitDetailData(PROFITDETAILDATA, datailschema);
				}
    		}else{
    			

    		}
		}
	}
    /**
     * 添加优惠信息 内部信息 节点
     * @param KINDDATA
     * @param KindSchema
     * @throws Exception
     */
    public void addProfitDetailData(Element PROFITDETAILDATA, PrpTprofitDetailSchema  detailSchema)throws Exception{
    	
    	Element KINDCODE = PROFITDETAILDATA.addElement("KINDCODE");
    	String kindCode = detailSchema.getKindCode();
    	KINDCODE.addText(kindCode);
    	
    	Element PROFITCODE = PROFITDETAILDATA.addElement("PROFITCODE");
    	String profitCode = detailSchema.getProfitCode();
    	PROFITCODE.addText(profitCode);
    	
    	Element SERIALNO = PROFITDETAILDATA.addElement("SERIALNO");
    	String serialNo = detailSchema.getSerialNo();
    	SERIALNO.addText(serialNo);
    	
    	Element PROFITRATE = PROFITDETAILDATA.addElement("PROFITRATE");
    	String profitRate = detailSchema.getProfitRate();
    	
    	PROFITRATE.addText(format.format(Double.parseDouble(profitRate)));
    	

    	
    	Element CHOOSEFLAG = PROFITDETAILDATA.addElement("CHOOSEFLAG");
    	String chooseFlag = detailSchema.getChooseFlag();
    	
    	if(chooseFlag!=null&&!"".equals(chooseFlag)&&chooseFlag.matches("^[0-9]+.[0-9]+$")){
    		chooseFlag=Double.parseDouble(chooseFlag)/100+"";
    	}
    	
    	CHOOSEFLAG.addText(chooseFlag);
    	
    	Element PROFITNAME = PROFITDETAILDATA.addElement("PROFITNAME");
    	String profitName = detailSchema.getProfitName();
    	PROFITNAME.addText(profitName);
    	
    	Element PROFITTYPE = PROFITDETAILDATA.addElement("PROFITTYPE");
    	String profitType = detailSchema.getProfitType();
    	PROFITTYPE.addText(profitType);
    	
    	Element CONDITIONCODE = PROFITDETAILDATA.addElement("CONDITIONCODE");
    	String conditionCode = detailSchema.getConditionCode();
    	CONDITIONCODE.addText(conditionCode);
    	
    	Element CONDITIONDESC = PROFITDETAILDATA.addElement("CONDITIONDESC");
    	String condition = detailSchema.getCondition();
    	CONDITIONDESC.addText(condition);
    	
    	Element MINPROFITRATE = PROFITDETAILDATA.addElement("MINPROFITRATE");
    	String minProfitRate = detailSchema.getMinProfitRate();
    	MINPROFITRATE.addText(minProfitRate);
    	
    	Element MAXPROFITRATE = PROFITDETAILDATA.addElement("MAXPROFITRATE");
    	String maxProfitRate = detailSchema.getMaxProfitRate();
    	MAXPROFITRATE.addText(maxProfitRate);
    }
    /**
     * 添加  XXXXX信息 节点
     * @param body
     * @param blProposalCI
     * @param blProposalBI
     * @throws Exception
     */
    public void addClaim(Element body, BLProposal blProposalCI, BLProposal blProposalBI)throws Exception{
    
    	Element CLAIM_LIST = body.addElement("CLAIM_LIST");
    	if (blProposalCI != null) {
    		int lengthCI = blProposalCI.getBLCIInsureDemandPay().getSize();
    		if (lengthCI > 0) {
    			for (int i = 0; i < lengthCI; i++) {
    				CIInsureDemandPaySchema  DemandPaySchema = blProposalCI.getBLCIInsureDemandPay().getArr(i);
    				
    				
	    				Element CLAIMDATA = CLAIM_LIST.addElement("CLAIM_DATA");
	    				
	    				addClaimData(CLAIMDATA, DemandPaySchema);
	    				Element RISKCODE = CLAIMDATA.addElement("RISKCODE");
	    				RISKCODE.addText("0507");
    				
	    			
    			}
			}
		}
    	if (blProposalBI != null) {
    		int lengthBI = blProposalBI.getBLCIInsureDemandPay().getSize();
    		if (lengthBI > 0) {
    			for (int i = 0; i < lengthBI; i++) {
    				
					CIInsureDemandPaySchema DemandPaySchema = blProposalBI.getBLCIInsureDemandPay().getArr(i);
					
					
					
						Element CLAIMDATA = CLAIM_LIST.addElement("CLAIM_DATA");
						
						addClaimData(CLAIMDATA, DemandPaySchema);
						Element RISKCODE = CLAIMDATA.addElement("RISKCODE");
						String RiskCode=blProposalBI.getBLPrpTmain().getArr(0).getRiskCode();
	    				RISKCODE.addText(RiskCode);
	    			
					
	    			
				}
			}
		}
    }
    /**
     * 添加XXXXX信息 内部信息 节点
     * @param body
     * @param blProposalCI
     * @param blProposalBI
     * @throws Exception
     */
    public void addClaimData(Element CLAIMDATA, CIInsureDemandPaySchema  DemandPaySchema)throws Exception{
    	
    	Element EFFECTIVEDATE = CLAIMDATA.addElement("EFFECTIVEDATE");
    	String effectiveDate = DemandPaySchema.getEffectiveDate();
    	EFFECTIVEDATE.addText(effectiveDate);
    
    	
    	Element EXPIREDATE = CLAIMDATA.addElement("EXPIREDATE");
    	String expireDate = DemandPaySchema.getExpireDate();
    	EXPIREDATE.addText(expireDate);
    	
    	Element REMARK = CLAIMDATA.addElement("REMARK");
    	String remark = DemandPaySchema.getRemark();
    	REMARK.addText(remark);
    	
    	Element FLAG = CLAIMDATA.addElement("FLAG");
    	String flag = DemandPaySchema.getFlag();
    	FLAG.addText(flag);
    	
    	Element RISKWARNINGTYPE = CLAIMDATA.addElement("RISKWARNINGTYPE");
    	String riskWarningType = DemandPaySchema.getRiskWarningType();
    	RISKWARNINGTYPE.addText(riskWarningType);
    	
    	Element RISKWARNINGNAME = CLAIMDATA.addElement("RISKWARNINGNAME");
     
    	
    	Element LOSSAREA = CLAIMDATA.addElement("LOSSAREA");
    	String lossArea = DemandPaySchema.getLossArea();
    	LOSSAREA.addText(lossArea);
    	
    	Element SERIALNO = CLAIMDATA.addElement("SERIALNO");
    	String serialNo = DemandPaySchema.getSerialNo();
    	SERIALNO.addText(serialNo);
    	
    	Element PAYDATA=CLAIMDATA.addElement("PAYDATA");
    	
    	
    	Element PERSONPAYFLAG = CLAIMDATA.addElement("PERSONPAYFLAG");
    	String personPayType = DemandPaySchema.getPersonPayType();
    	PERSONPAYFLAG.addText(personPayType);
    	
    	Element ACCIDENTTIME = CLAIMDATA.addElement("ACCIDENTTIME");
    	String lossTime = DemandPaySchema.getLossTime();
    	ACCIDENTTIME.addText(lossTime);
    	
    	Element ENDCASETIME = CLAIMDATA.addElement("ENDCASETIME");
    	String endCaseTime = DemandPaySchema.getEndCaseTime();
    	ENDCASETIME.addText(endCaseTime);
    	
    	Element CLAIMAMOUNT = CLAIMDATA.addElement("CLAIMAMOUNT");
    	String lossFee = DemandPaySchema.getLossFee();
    	CLAIMAMOUNT.addText(lossFee);
    	
    	Element PAYCOMPANY = CLAIMDATA.addElement("PAYCOMPANY");
    	String payCompany = DemandPaySchema.getPayCompany();
    	PAYCOMPANY.addText(payCompany);
    	
    	Element POLICYNO = CLAIMDATA.addElement("POLICYNO");
    	String policyNo = DemandPaySchema.getPolicyNo();
    	POLICYNO.addText(policyNo);
    	
    	Element PAYTYPE = CLAIMDATA.addElement("PAYTYPE");
    	String payType = DemandPaySchema.getPayType();
    	PAYTYPE.addText(payType);
    	
    	Element COMPENSATENO = CLAIMDATA.addElement("COMPENSATENO");
    	String compensateNo = DemandPaySchema.getCompensateNo();
    	COMPENSATENO.addText(compensateNo);
    	
    	Element KINDCODE = CLAIMDATA.addElement("KINDCODE");
    	String kindCode = DemandPaySchema.getKindCode();
    	KINDCODE.addText(kindCode);
    	
    	Element ACCIDENTDEATHFLAG = CLAIMDATA.addElement("ACCIDENTDEATHFLAG");
    	String accidentDeathFlag = DemandPaySchema.getAccidentDeathFlag();
    	
    	ACCIDENTDEATHFLAG.addText(accidentDeathFlag==null?"":accidentDeathFlag);
    
    	
    	Element OPTIONTYPE = CLAIMDATA.addElement("OPTIONTYPE");
    	String optionType = DemandPaySchema.getOptionType();
    	OPTIONTYPE.addText(optionType);
    	
    	Element TOTALAMOUNT = CLAIMDATA.addElement("TOTALAMOUNT");
    	String totalAmount = DemandPaySchema.getTotalAmount();
    	TOTALAMOUNT.addText(totalAmount);
    	
    	Element CLAIMNOTIFICATIONNO = CLAIMDATA.addElement("CLAIMNOTIFICATIONNO");
    	String claimNotificationNo = DemandPaySchema.getClaimNotificationNo();
    	CLAIMNOTIFICATIONNO.addText(claimNotificationNo);
    	
    	Element CLAIMREGISTRATIONNO = CLAIMDATA.addElement("CLAIMREGISTRATIONNO");
    	String claimRegistrationNo = DemandPaySchema.getClaimRegistrationNo();
    	CLAIMREGISTRATIONNO.addText(claimRegistrationNo);
    	
    	Element CASEID = CLAIMDATA.addElement("CASEID");
    	String caseID = DemandPaySchema.getCaseID();
    	CASEID.addText(caseID);
    }
    /**
     * 添加 交通事故信息 节点
     * @param body
     * @param blProposalCI
     * @param blProposalBI
     * @throws Exception
     */
    public void addTrafficDetail(Element body, BLProposal blProposalCI, BLProposal blProposalBI)throws Exception{
    	Element TRAFFICDETAIL_LIST = body.addElement("TRAFFICDETAIL_LIST");
    	if (blProposalCI != null) {
    		int lengthCI = blProposalCI.getBLPrpTTrafficDetail().getSize();
    		if (lengthCI > 0) {
				for (int i = 0; i < lengthCI; i++) {
					PrpTTrafficDetailSchema DetailSchema = blProposalCI.getBLPrpTTrafficDetail().getArr(i);
					Element TRAFFICDETAILDATA = TRAFFICDETAIL_LIST.addElement("TRAFFICDETAIL_DATA");
					
					addTrafficDetailData(TRAFFICDETAILDATA, DetailSchema);
				}
    		}
		}
    	if (blProposalBI != null) {
    		int lengthBI = blProposalBI.getBLPrpTTrafficDetail().getSize();
    		if (lengthBI > 0) {
				for (int i = 0; i < lengthBI; i++) {
	    			PrpTTrafficDetailSchema  DetailSchema = blProposalBI.getBLPrpTTrafficDetail().getArr(i);
	    			Element TRAFFICDETAILDATA = TRAFFICDETAIL_LIST.addElement("TRAFFICDETAIL_DATA");
	    			
	    			addTrafficDetailData(TRAFFICDETAILDATA, DetailSchema);
				}
    		}
		}
	}
    /**
     * 添加  交通事故信息 内部信息 节点
     * @param TRAFFICDETAILDATA
     * @param DetailSchema
     * @throws Exception
     */
    public void addTrafficDetailData(Element TRAFFICDETAILDATA, PrpTTrafficDetailSchema  DetailSchema)throws Exception{
    	
    	Element FLOATRATIO = TRAFFICDETAILDATA.addElement("FLOATRATIO");
    	String floatRatio = DetailSchema.getFloatRatio();
    	FLOATRATIO.addText(floatRatio);
    	
    	Element RISKCODE = TRAFFICDETAILDATA.addElement("RISKCODE");
    	String riskCode = DetailSchema.getRiskCode();
    	RISKCODE.addText(riskCode);
    	
    	Element ITEMNO = TRAFFICDETAILDATA.addElement("ITEMNO");
    	String itemNo = DetailSchema.getItemNo();
    	ITEMNO.addText(itemNo);
    	
    	Element SERIALNO = TRAFFICDETAILDATA.addElement("SERIALNO");
    	String serialNo = DetailSchema.getSerialNo();
    	SERIALNO.addText(serialNo);
    	
    	Element TRAFFICCODE = TRAFFICDETAILDATA.addElement("TRAFFICCODE");
    	String trafficCode = DetailSchema.getTrafficCode();
    	TRAFFICCODE.addText(trafficCode);
    	
    	Element TRAFFICTYPE = TRAFFICDETAILDATA.addElement("TRAFFICTYPE");
    	String trafficType = DetailSchema.getTrafficType();
    	TRAFFICTYPE.addText(trafficType);
    	
    	Element ACCIDENTTYPE = TRAFFICDETAILDATA.addElement("ACCIDENTTYPE");
    	String accidentType = DetailSchema.getAccidentType();
    	ACCIDENTTYPE.addText(accidentType);
    	
    	Element BUSINESSTYPE = TRAFFICDETAILDATA.addElement("BUSINESSTYPE");
    	String businessType = DetailSchema.getBusinessType();
    	BUSINESSTYPE.addText(businessType);
    	
    	Element INDEMNITYDUTY = TRAFFICDETAILDATA.addElement("INDEMNITYDUTY");
    	String indemnityDuty = DetailSchema.getIndemnityDuty();
    	INDEMNITYDUTY.addText(indemnityDuty);
    }
   /**
    * 添加 违法信息 节点
    * @param body
    * @param blProposalCI
    * @param blProposalBI
    * @throws Exception
    */
    public void addPecc(Element body,BLProposal blProposalCI, BLProposal blProposalBI)throws Exception{
    	Element PECC_LIST = body.addElement("PECC_LIST");
    	if (blProposalCI != null) {
    		int lengthCI = blProposalCI.getBLCIInsureDemandLoss().getSize();
    		if (lengthCI > 0) {
	    		for (int i = 0; i < lengthCI; i++) {
	    			CIInsureDemandLossSchema  lossSchema = blProposalCI.getBLCIInsureDemandLoss().getArr(i);
	    			Element PECCDATA = PECC_LIST.addElement("PECC_DATA");
	    			
	    			addPECCData(PECCDATA, lossSchema);
				}
    		}
		}
    	if (blProposalBI != null) {
    		int lengthBI = blProposalBI.getBLCIInsureDemandLoss().getSize();
    		if (lengthBI > 0) {
	    		for (int i = 0; i < lengthBI; i++) {
	    			CIInsureDemandLossSchema  lossSchema = blProposalBI.getBLCIInsureDemandLoss().getArr(i);
	    			Element PECCDATA = PECC_LIST.addElement("PECC_DATA");
	    			
	    			addPECCData(PECCDATA, lossSchema);
				}
    		}
		}
	}
    /**
     * 添加 违法信息 内部信息 节点
     * @param PECCDATA
     * @param lossSchema
     * @throws Exception
     */
    public void addPECCData(Element PECCDATA, CIInsureDemandLossSchema  lossSchema)throws Exception{
    	
    	Element SERIALNO = PECCDATA.addElement("SERIALNO");
    	String serialNo = lossSchema.getSerialNo();
    	SERIALNO.addText(serialNo);
    	
    	Element PECCANCYTIME = PECCDATA.addElement("PECCANCYTIME");
    	String lossTime = lossSchema.getLossTime();
    	PECCANCYTIME.addText(lossTime);
    	
    	Element PECC_DATA = PECCDATA.addElement("PECCDATA");
    	String sanctionDate = lossSchema.getSanctionDate();
    	PECC_DATA.addText(sanctionDate);
    	
    	Element PECCANCYPLACE = PECCDATA.addElement("PECCANCYPLACE");
    	String lossAddress = lossSchema.getLossAddress();
    	PECCANCYPLACE.addText(lossAddress);
    	
    	Element PECCACTION = PECCDATA.addElement("PECCACTION");
    	String lossAction = lossSchema.getLossAction();
    	PECCACTION.addText(lossAction);
    	
    	Element LOSSTYPE = PECCDATA.addElement("LOSSTYPE");
    	String lossType = lossSchema.getLossType();
    	LOSSTYPE.addText(lossType);
    	
    	Element PECCANCYNUMBER = PECCDATA.addElement("PECCANCYNUMBER");
    	String peccancyNumber = lossSchema.getPeccancyNumber();
    	PECCANCYNUMBER.addText(peccancyNumber);
    	
    	Element DECISIONCODE = PECCDATA.addElement("DECISIONCODE");
    	String decisionCode = lossSchema.getDecisionCode();
    	DECISIONCODE.addText(decisionCode);
    	
    	Element DECISIONTYPECODE = PECCDATA.addElement("DECISIONTYPECODE");
    	String decisionTypeCode = lossSchema.getDecisionTypeCode();
    	DECISIONTYPECODE.addText(decisionTypeCode);
    	
    	
    	Element LOSSACCEPTDATE = PECCDATA.addElement("LOSSACCEPTDATE");

    	LOSSACCEPTDATE.addText("");
    	
    	
    	Element IDENTIFYNUMBER = PECCDATA.addElement("IDENTIFYNUMBER");
    	String identifyNumber = lossSchema.getIdentifyNumber();
    	IDENTIFYNUMBER.addText(identifyNumber);
    	
    	Element COEFF = PECCDATA.addElement("COEFF");
    	String coeff = lossSchema.getCoeff();
    	COEFF.addText(coeff);
    	
    	Element IDENTIFYTYPE = PECCDATA.addElement("IDENTIFYTYPE");
    	String identifyType = lossSchema.getIdentifyType();
    	IDENTIFYTYPE.addText(identifyType);
    	
    	Element ADJUSTFLAG = PECCDATA.addElement("ADJUSTFLAG");
    	String adjustFlag = lossSchema.getAdjustFlag();
    	ADJUSTFLAG.addText(adjustFlag);
    	
    	Element LICENSEPLATENO = PECCDATA.addElement("LICENSEPLATENO");
    	String licensePlateNo = lossSchema.getLicensePlateNo();
    	LICENSEPLATENO.addText(licensePlateNo);
    	
    	Element LICENSEPLATETYPECODE = PECCDATA.addElement("LICENSEPLATETYPECODE");
    	String licensePlateTypeCode = lossSchema.getLicensePlateTypeCode();
    	LICENSEPLATETYPECODE.addText(licensePlateTypeCode);
    	
    	Element JURISDICTIONAGENCYCODE = PECCDATA.addElement("JURISDICTIONAGENCYCODE");
    	String jurisdictionAgencyCode = lossSchema.getJurisdictionAgencyCode();
    	JURISDICTIONAGENCYCODE.addText(jurisdictionAgencyCode);
    	
    	Element REMARK = PECCDATA.addElement("REMARK");
    	String remark = lossSchema.getRemark();
    	REMARK.addText(remark);
    	
    	Element FLAG = PECCDATA.addElement("FLAG");
    	String flag = lossSchema.getFlag();
    	FLAG.addText(flag);
    }
    /**
     * 添加 XX交强XXXXX平台返回信息 节点
     * @param body
     * @param blProposalCI
     * @param blProposalBI
     * @throws Exception
     */
    public void addCIInsureDemand(Element body, BLProposal blProposalCI, BLProposal blProposalBI)throws Exception{
    	
    	if (blProposalCI != null || blProposalBI != null) {
        

    		
    		Element CIInsureDemandList = body.addElement("CIINSUREDEMAND_LIST");
    		
    		int lengthCI = 0;
			if (blProposalCI!=null) {
				lengthCI = blProposalCI.getBLCIInsureDemand().getSize();
			}
    		int lengthBI = 0;
			if (blProposalBI!=null) {
				lengthBI = blProposalBI.getBLCIInsureDemand().getSize();
			}
			
    		if (lengthCI > 0 && lengthBI > 0) {
	    		for (int i = 0; i < lengthCI; i++) {
	    			CIInsureDemandSchema  demandSchema = blProposalCI.getBLCIInsureDemand().getArr(i);
	    			
	    			Element CIINSUREDEMANDDATA = CIInsureDemandList.addElement("CIINSUREDEMAND_DATA");
	    			
	    			addCIInsureDemandData(CIINSUREDEMANDDATA, demandSchema,blProposalCI);
				}
	    		for (int i = 0; i < lengthBI; i++) {
	    			CIInsureDemandSchema  demandSchema = blProposalBI.getBLCIInsureDemand().getArr(i);
	    			
	    			Element CIINSUREDEMANDDATA = CIInsureDemandList.addElement("CIINSUREDEMAND_DATA");
	    			
	    			addCIInsureDemandData(CIINSUREDEMANDDATA, demandSchema,blProposalBI);
				}
    		}else if (lengthCI > 0 && lengthBI == 0) {
	    		for (int i = 0; i < lengthCI; i++) {
	    			CIInsureDemandSchema  demandSchema = blProposalCI.getBLCIInsureDemand().getArr(i);
	    			
	    			Element CIINSUREDEMANDDATA = CIInsureDemandList.addElement("CIINSUREDEMAND_DATA");
	    			
	    			addCIInsureDemandData(CIINSUREDEMANDDATA, demandSchema,blProposalCI);
				}
    		}else if (lengthCI == 0 && lengthBI > 0) {
    			for (int i = 0; i < lengthBI; i++) {
	    			CIInsureDemandSchema  demandSchema = blProposalBI.getBLCIInsureDemand().getArr(i);
	    			
	    			Element CIINSUREDEMANDDATA = CIInsureDemandList.addElement("CIINSUREDEMAND_DATA");
	    			
	    			addCIInsureDemandData(CIINSUREDEMANDDATA, demandSchema,blProposalBI);
				}
    		}else{
    			

    		}
		}
	}
   /**
    * 添加 XX交强XXXXX平台返回信息 内部信息 节点
    * @param CIINSUREDEMANDDATA
    * @param demandSchema
    * @throws Exception
    */
    public void addCIInsureDemandData(Element CIINSUREDEMANDDATA,CIInsureDemandSchema  demandSchema,BLProposal blProposal)throws Exception{
    	
    	Element TRANSFERDATE = CIINSUREDEMANDDATA.addElement("TRANSFERDATE");
    	String transferDate = demandSchema.getTransferDate();
    	TRANSFERDATE.addText(transferDate);
    	
    	Element BUSILASTYEARENDDATE = CIINSUREDEMANDDATA.addElement("BUSILASTYEARENDDATE");
    	String busiLastYearEndDate = demandSchema.getBusiLastYearEndDate();
    	BUSILASTYEARENDDATE.addText(busiLastYearEndDate);
    	
    	Element BUSIADJUSTSTART = CIINSUREDEMANDDATA.addElement("BUSIADJUSTSTART");
    	String busiAdjustStart = demandSchema.getBusiAdjustStart();
    	BUSIADJUSTSTART.addText(busiAdjustStart);
    	
    	Element BUSIADJUSTEND = CIINSUREDEMANDDATA.addElement("BUSIADJUSTEND");
    	String busiAdjustEnd = demandSchema.getBusiAdjustEnd();
    	BUSIADJUSTEND.addText(busiAdjustEnd);
    	
    	Element POLICYCOEFF = CIINSUREDEMANDDATA.addElement("POLICYCOEFF");
    	String policyCoeff = demandSchema.getPolicyCoeff();
    	POLICYCOEFF.addText(policyCoeff);
    	
    	Element NOLOYALTYADJUSTREASON = CIINSUREDEMANDDATA.addElement("NOLOYALTYADJUSTREASON");
    	String noLoyaltyAdjustReason = demandSchema.getNoLoyaltyAdjustReason();
    	NOLOYALTYADJUSTREASON.addText(noLoyaltyAdjustReason);
    	
    	Element NOCLAIMADJUSTREASON = CIINSUREDEMANDDATA.addElement("NOCLAIMADJUSTREASON");
    	String noClaimAdjustReason = demandSchema.getNoClaimAdjustReason();
    	NOCLAIMADJUSTREASON.addText(noClaimAdjustReason);
    	
    	Element KINDADJUSTVALUE = CIINSUREDEMANDDATA.addElement("KINDADJUSTVALUE");
    	String kindAdjustValue = demandSchema.getKindAdjustValue();
    	KINDADJUSTVALUE.addText(kindAdjustValue);
    	
    	Element KINDADJUSTREASON = CIINSUREDEMANDDATA.addElement("KINDADJUSTREASON");
    	String kindAdjustReason = demandSchema.getKindAdjustReason();
    	KINDADJUSTREASON.addText(kindAdjustReason);
    	
    	Element NOKINDADJUSTREASON = CIINSUREDEMANDDATA.addElement("NOKINDADJUSTREASON");
    	String noKindAdjustReason = demandSchema.getNoKindAdjustReason();
    	NOKINDADJUSTREASON.addText(noKindAdjustReason);
    	
    	Element NOPECCANCYREASON = CIINSUREDEMANDDATA.addElement("NOPECCANCYREASON");
    	String noPeccancyReason = demandSchema.getNoPeccancyReason();
    	NOPECCANCYREASON.addText(noPeccancyReason);
    	
    	Element CLAIMAMOUNTVALUE = CIINSUREDEMANDDATA.addElement("CLAIMAMOUNTVALUE");
    	String claimAmountValue = demandSchema.getClaimAmountValue();
    	CLAIMAMOUNTVALUE.addText(claimAmountValue);
    	
    	Element CLAIMAMOUNTREASON = CIINSUREDEMANDDATA.addElement("CLAIMAMOUNTREASON");
    	String claimAmountReason = demandSchema.getClaimAmountReason();
    	CLAIMAMOUNTREASON.addText(claimAmountReason);
    	
    	Element NOCLAIMAMOUNTREASON = CIINSUREDEMANDDATA.addElement("NOCLAIMAMOUNTREASON");
    	String noClaimAmountReason = demandSchema.getNoClaimAmountReason();
    	NOCLAIMAMOUNTREASON.addText(noClaimAmountReason);
    	
    	Element INSUREINDOORVALUE = CIINSUREDEMANDDATA.addElement("INSUREINDOORVALUE");
    	String insureInDoorValue = demandSchema.getInsureInDoorValue();
    	INSUREINDOORVALUE.addText(insureInDoorValue);
    	
    	Element INSUREINDOORREASON = CIINSUREDEMANDDATA.addElement("INSUREINDOORREASON");
    	String insureInDoorReason = demandSchema.getInsureInDoorReason();
    	INSUREINDOORREASON.addText(insureInDoorReason);
    	
    	Element CHECKCODE = CIINSUREDEMANDDATA.addElement("CHECKCODE");
    	String checkCode = demandSchema.getCheckCode();
    	CHECKCODE.addText(checkCode);
    	
    	Element QUESTION = CIINSUREDEMANDDATA.addElement("QUESTION");
    	String question = demandSchema.getQuestion();
    	QUESTION.addText(question);
    	
    	Element RENEWALFLAG = CIINSUREDEMANDDATA.addElement("RENEWALFLAG");
    	String renewalFlag = demandSchema.getRenewalFlag();
    	RENEWALFLAG.addText(renewalFlag);
    	
    	Element LASTPRODUCERCODE = CIINSUREDEMANDDATA.addElement("LASTPRODUCERCODE");
    	String lastProducerCode = demandSchema.getLastProducerCode();
    	LASTPRODUCERCODE.addText(lastProducerCode);
    	
    	
    	Element LASTPRODUCERNAME=CIINSUREDEMANDDATA.addElement("LASTPRODUCERNAME");
    	
    	
    	Element CAROWNERMESSAGE = CIINSUREDEMANDDATA.addElement("CAROWNERMESSAGE");
    	String carOwnerMessage = demandSchema.getCarOwnerMessage();
    	CAROWNERMESSAGE.addText(carOwnerMessage);
    	
    	Element PROPOSALNO = CIINSUREDEMANDDATA.addElement("PROPOSALNO");
    	String proposalNo = demandSchema.getProposalNo();
    	PROPOSALNO.addText(proposalNo);
    	
    	Element POLICYNO = CIINSUREDEMANDDATA.addElement("POLICYNO");
    	String policyNo = demandSchema.getPolicyNo();
    	POLICYNO.addText(policyNo);
    	
    	Element LICENSENO = CIINSUREDEMANDDATA.addElement("LICENSENO");
    	String licenseNo = demandSchema.getLicenseNo();
    	LICENSENO.addText(licenseNo);
    	
    	Element LICENSETYPE = CIINSUREDEMANDDATA.addElement("LICENSETYPE");
    	String licenseType = demandSchema.getLicenseType();
    	LICENSETYPE.addText(licenseType);
    	
    	Element USENATURECODE = CIINSUREDEMANDDATA.addElement("USENATURECODE");
    	String useNatureCode = demandSchema.getUseNatureCode();
    	USENATURECODE.addText(useNatureCode);
    	
    	Element FRAMENO = CIINSUREDEMANDDATA.addElement("FRAMENO");
    	String frameNo = demandSchema.getFrameNo();
    	FRAMENO.addText(frameNo);
    	
    	Element ENGINENO = CIINSUREDEMANDDATA.addElement("ENGINENO");
    	String engineNo = demandSchema.getEngineNo();
    	ENGINENO.addText(engineNo);
    	
    	Element LICENSECOLORCODE = CIINSUREDEMANDDATA.addElement("LICENSECOLORCODE");
    	String licenseColorCode = demandSchema.getLicenseColorCode();
    	LICENSECOLORCODE.addText(licenseColorCode);
    	
    	Element CAROWNER = CIINSUREDEMANDDATA.addElement("CAROWNER");
    	String carOwner = demandSchema.getCarOwner();
    	CAROWNER.addText(carOwner);
    	
    	Element ENROLLDATE = CIINSUREDEMANDDATA.addElement("ENROLLDATE");
    	String enrollDate = demandSchema.getEnrollDate();
    	ENROLLDATE.addText(enrollDate);
    	
    	Element MAKEDATE = CIINSUREDEMANDDATA.addElement("MAKEDATE");
    	String makeDate = demandSchema.getMakeDate();
    	MAKEDATE.addText(makeDate);
    	
    	Element SEATCOUNT = CIINSUREDEMANDDATA.addElement("SEATCOUNT");
    	String seatCount = demandSchema.getSeatCount();
    	SEATCOUNT.addText(seatCount);
    	
    	Element TONCOUNT = CIINSUREDEMANDDATA.addElement("TONCOUNT");
    	String tonCount = demandSchema.getTonCount();
    	TONCOUNT.addText(tonCount);
    	
    	Element VALIDCHECKDATE = CIINSUREDEMANDDATA.addElement("VALIDCHECKDATE");
    	String validCheckDate = demandSchema.getValidCheckDate();
    	VALIDCHECKDATE.addText(validCheckDate);
    	
    	Element MANUFACTURERNAME = CIINSUREDEMANDDATA.addElement("MANUFACTURERNAME");
    	String manufacturerName = demandSchema.getManufacturerName();
    	MANUFACTURERNAME.addText(manufacturerName);
    	
    	Element MODELCODE = CIINSUREDEMANDDATA.addElement("MODELCODE");
    	String modelCode = demandSchema.getModelCode();
    	MODELCODE.addText(modelCode);
    	
    	Element BRANDCNAME = CIINSUREDEMANDDATA.addElement("BRANDCNAME");
    	String brandCName = demandSchema.getBrandCName();
    	BRANDCNAME.addText(brandCName);
    	
    	Element BRANDNAME = CIINSUREDEMANDDATA.addElement("BRANDNAME");
    	String brandName = demandSchema.getBrandName();
    	BRANDNAME.addText(brandName);
    	
    	Element CARKINDCODE = CIINSUREDEMANDDATA.addElement("CARKINDCODE");
    	String carKindCode = demandSchema.getCarKindCode();
    	CARKINDCODE.addText(carKindCode);
    	
    	Element CHECKDATE = CIINSUREDEMANDDATA.addElement("CHECKDATE");
    	String checkDate = demandSchema.getCheckDate();
    	CHECKDATE.addText(checkDate);
    	
    	Element ENDVALIDDATE = CIINSUREDEMANDDATA.addElement("ENDVALIDDATE");
    	String endValidDate = demandSchema.getEndValidDate();
    	ENDVALIDDATE.addText(endValidDate);
    	
    	Element CARSTATUS = CIINSUREDEMANDDATA.addElement("CARSTATUS");
    	String carStatus = demandSchema.getCarStatus();
    	CARSTATUS.addText(carStatus);
    	
    	Element HAULAGE = CIINSUREDEMANDDATA.addElement("HAULAGE");
    	String haulage = demandSchema.getHaulage();
    	HAULAGE.addText(haulage);
    	
    	Element STARTDATE = CIINSUREDEMANDDATA.addElement("STARTDATE");
    	String startDate = demandSchema.getStartDate();
    	STARTDATE.addText(startDate);
    	
    	Element ENDDATE = CIINSUREDEMANDDATA.addElement("ENDDATE");
    	String endDate = demandSchema.getEndDate();
    	ENDDATE.addText(endDate);
    	
    	Element AMOUNT = CIINSUREDEMANDDATA.addElement("AMOUNT");
    	String amount = demandSchema.getAmount();
    	AMOUNT.addText(amount);
    	
    	Element PREMIUM = CIINSUREDEMANDDATA.addElement("PREMIUM");
    	String premium = demandSchema.getPremium();
    	PREMIUM.addText(premium);
    	
    	Element BENCHMARKPREMIUM = CIINSUREDEMANDDATA.addElement("BENCHMARKPREMIUM");
    	String benchmarkPremium = demandSchema.getBenchmarkPremium();
    	BENCHMARKPREMIUM.addText(benchmarkPremium);
    	
    	Element PECCANCYCOEFF = CIINSUREDEMANDDATA.addElement("PECCANCYCOEFF");
    	String peccancyCoeff = demandSchema.getPeccancyCoeff();
    	PECCANCYCOEFF.addText(peccancyCoeff);
    	
    	Element CLAIMCOEFF = CIINSUREDEMANDDATA.addElement("CLAIMCOEFF");
    	String claimCoeff = demandSchema.getClaimCoeff();
    	CLAIMCOEFF.addText(claimCoeff);
    	
    	Element DRIVERCOEFF = CIINSUREDEMANDDATA.addElement("DRIVERCOEFF");
    	String driverCoeff = demandSchema.getDriverCoeff();
    	DRIVERCOEFF.addText(driverCoeff);
    	
    	Element DISTRICTCOEFF = CIINSUREDEMANDDATA.addElement("DISTRICTCOEFF");
    	String districtCoeff = demandSchema.getDistrictCoeff();
    	DISTRICTCOEFF.addText(districtCoeff);
    	
    	Element COMMISSIONRATE = CIINSUREDEMANDDATA.addElement("COMMISSIONRATE");
    	String commissionRate = demandSchema.getCommissionRate();
    	COMMISSIONRATE.addText(commissionRate);
    	
    	Element BASEPREMIUM = CIINSUREDEMANDDATA.addElement("BASEPREMIUM");
    	String basePremium = demandSchema.getBasePremium();
    	BASEPREMIUM.addText(basePremium);
    	
    	Element COMCODE = CIINSUREDEMANDDATA.addElement("COMCODE");
    	String comCode = demandSchema.getComCode();
    	COMCODE.addText(comCode);
    	
    	Element OPERATORCODE = CIINSUREDEMANDDATA.addElement("OPERATORCODE");
    	String operatorCode = demandSchema.getOperatorCode();
    	OPERATORCODE.addText(operatorCode);
    	 
    	
    	Element DEMANDTIME = CIINSUREDEMANDDATA.addElement("DEMANDTIME");
    	String demandTime = demandSchema.getDemandTime();
    	DEMANDTIME.addText(demandTime);
    	
    	Element REMARK = CIINSUREDEMANDDATA.addElement("REMARK");

    	
    	if(!"".equals(demandSchema.getReinsureFlag())&&!"0".equals(demandSchema.getReinsureFlag())){
            if(!"".equals(demandSchema.getErrorMessage())){
            	REMARK.addText(demandSchema.getErrorMessage());
            }
        }else if(!"".equals(demandSchema.getReCoverMsg())){
        	REMARK.addText(demandSchema.getReCoverMsg());
        }else{
     	   REMARK.addText(demandSchema.getRemark());
        }
    	
    	
    	Element FLAG = CIINSUREDEMANDDATA.addElement("FLAG");
    	String flag = demandSchema.getFlag();
    	
    	if (flag != null) {
			FLAG.addText(flag);
		}
    	
    	
    	Element BILLDATE = CIINSUREDEMANDDATA.addElement("BILLDATE");
    	String billDate = demandSchema.getBillDate();
    	BILLDATE.addText(billDate);
    	
    	Element REINSUREFLAG = CIINSUREDEMANDDATA.addElement("REINSUREFLAG");
    	String reinsureFlag = demandSchema.getReinsureFlag();
    	REINSUREFLAG.addText(reinsureFlag);
    	
    	Element LASTBILLDATE = CIINSUREDEMANDDATA.addElement("LASTBILLDATE");
    	String lastBillDate = demandSchema.getLastBillDate();
    	LASTBILLDATE.addText(lastBillDate);
    	
    	Element RATEFLOATFLAG = CIINSUREDEMANDDATA.addElement("RATEFLOATFLAG");
    	String rateFloatFlag = demandSchema.getRateFloatFlag();
    	RATEFLOATFLAG.addText(rateFloatFlag);
    	
    	Element CLAIMADJUSTREASON = CIINSUREDEMANDDATA.addElement("CLAIMADJUSTREASON");
    	String claimAdjustReason = demandSchema.getClaimAdjustReason();
    	CLAIMADJUSTREASON.addText(claimAdjustReason);
    	
    	Element PECCANCYADJUSTREASON = CIINSUREDEMANDDATA.addElement("PECCANCYADJUSTREASON");
    	String peccancyAdjustReason = demandSchema.getPeccancyAdjustReason();
    	PECCANCYADJUSTREASON.addText(peccancyAdjustReason);
    	
    	Element DRIVERRATEREASON = CIINSUREDEMANDDATA.addElement("DRIVERRATEREASON");
    	String driverRateReason = demandSchema.getDriverRateReason();
    	DRIVERRATEREASON.addText(driverRateReason);
     
    	
    	Element DISTRICTRATEREASON = CIINSUREDEMANDDATA.addElement("DISTRICTRATEREASON");
    	String districtRateReason = demandSchema.getDistrictRateReason();
    	DISTRICTRATEREASON.addText(districtRateReason);
    	
    	Element LASTYEARSTARTDATE = CIINSUREDEMANDDATA.addElement("LASTYEARSTARTDATE");
    	String lastYearStartDate = demandSchema.getLastYearStartDate();
    	LASTYEARSTARTDATE.addText(lastYearStartDate);
    	
    	Element LASTYEARENDDATE = CIINSUREDEMANDDATA.addElement("LASTYEARENDDATE");
    	String lastYearEndDate = demandSchema.getLastYearEndDate();
    	LASTYEARENDDATE.addText(lastYearEndDate);
    	
    	Element RESTRICFLAG = CIINSUREDEMANDDATA.addElement("RESTRICFLAG");
    	String restricFlag = demandSchema.getRestricFlag();
    	RESTRICFLAG.addText(restricFlag);
    	
    	Element PREFERENTIALPREMIUM = CIINSUREDEMANDDATA.addElement("PREFERENTIALPREMIUM");
    	String preferentialPremium = demandSchema.getPreferentialPremium();
    	PREFERENTIALPREMIUM.addText(preferentialPremium);
    	
    	Element PREFERENTIALFORMULA = CIINSUREDEMANDDATA.addElement("PREFERENTIALFORMULA");
    	String preferentialFormula = demandSchema.getPreferentialFormula();
    	PREFERENTIALFORMULA.addText(preferentialFormula);
    	
    	Element PREFERENTIALDAY = CIINSUREDEMANDDATA.addElement("PREFERENTIALDAY");
    	String preferentialDay = demandSchema.getPreferentialDay();
    	PREFERENTIALDAY.addText(preferentialDay);
    	
    	Element SEARCHSEQUENCENO = CIINSUREDEMANDDATA.addElement("SEARCHSEQUENCENO");
    	String searchSequenceNo = demandSchema.getSearchSequenceNo();
    	SEARCHSEQUENCENO.addText(searchSequenceNo);
    	
    	Element SAFEADJUST = CIINSUREDEMANDDATA.addElement("SAFEADJUST");
    	String safeAdjust = demandSchema.getSafeAdjust();
    	SAFEADJUST.addText(safeAdjust);
    	
    	Element NONCLAIMADJUST = CIINSUREDEMANDDATA.addElement("NONCLAIMADJUST");
    	String nonclaimAdjust = demandSchema.getNonclaimAdjust();
    	NONCLAIMADJUST.addText(nonclaimAdjust);
     
    	
    	Element LOYALTYADJUST = CIINSUREDEMANDDATA.addElement("LOYALTYADJUST");
    	String loyaltyAdjust = demandSchema.getLoyaltyAdjust();
    	LOYALTYADJUST.addText(loyaltyAdjust);
    	
    	Element USETYPESOURCE = CIINSUREDEMANDDATA.addElement("USETYPESOURCE");
    	String useTypeSource = demandSchema.getUseTypeSource();
    	USETYPESOURCE.addText(useTypeSource);
    	
    	Element LOYALTYADJUSTREASON = CIINSUREDEMANDDATA.addElement("LOYALTYADJUSTREASON");
    	String loyaltyAdjustReason = demandSchema.getLoyaltyAdjustReason();
    	LOYALTYADJUSTREASON.addText(loyaltyAdjustReason);
    	
    	Element QUERYPASTDATE = CIINSUREDEMANDDATA.addElement("QUERYPASTDATE");
    	String queryPastDate = demandSchema.getQueryPastDate();
		
    	if(queryPastDate.indexOf("/")>-1){
    		SimpleDateFormat sdf=new SimpleDateFormat();
    		sdf.applyPattern("MM/dd/yyyy");
    		Date qpd=sdf.parse(queryPastDate);
    		sdf.applyPattern("yyyy-MM-dd");
    		String strDate=sdf.format(qpd);
    		QUERYPASTDATE.addText(strDate);
    	}else{
    		QUERYPASTDATE.addText(queryPastDate);
    	}
    	
    	
    	Element LASTMODELCODE = CIINSUREDEMANDDATA.addElement("LASTMODELCODE");
    	String strLastModelCode = demandSchema.getLastModelCode();
    	if(strLastModelCode == null){
    		strLastModelCode = "";
    	}
    	LASTMODELCODE.addText(strLastModelCode);
    	
    	Element LASTMODEL = CIINSUREDEMANDDATA.addElement("LASTMODEL");
    	String strLastModel = demandSchema.getLastModel();
    	if(strLastModel == null){
    		strLastModel = "";
    	}
    	LASTMODEL.addText(strLastModel);
    	
    	Element LASTREPLACEMENTVALUE = CIINSUREDEMANDDATA.addElement("LASTREPLACEMENTVALUE");
    	String strLastReplacementValue = demandSchema.getLastPurchasePrice();
    	if(strLastReplacementValue == null){
    		strLastReplacementValue = "";
    	}
    	LASTREPLACEMENTVALUE.addText(strLastReplacementValue);
    	
    	
    	

    	Element RiskCode = CIINSUREDEMANDDATA.addElement("RISKCODE");
    	String riskCode =  blProposal.getBLPrpTmain().getArr(0).getRiskCode();
    	if(riskCode == null){
    		riskCode = "";
    	}
    	RiskCode.addText(riskCode);
    	
    }
   /**
    * 添加 赔付统计信息 节点
    * @param body
    * @param blProposalCI
    * @param blProposalBI
    * @throws Exception
    */
    public void addPrpintefInfo(Element body,BLProposal blProposalCI, BLProposal blProposalBI)throws Exception{
    	if (blProposalCI != null ) {
    		
    		Element PRPINTEFINFO_LIST = body.addElement("PRPINTEFINFO_LIST");
    		
    		int lengthCI = blProposalCI.getBLPrpIntefInfo().getSize();
    		if (lengthCI > 0) {
    			String strRiskCode = blProposalCI.getBLPrpTmain().getArr(0).getRiskCode();
    			PRPINTEFINFO_LIST.addAttribute("RISKCODE", strRiskCode);
	    		for (int i = 0; i < lengthCI; i++) {
	    			PrpIntefInfoSchema  lossSchema = blProposalCI.getBLPrpIntefInfo().getArr(i);
	    			Element PRPINTEFINFODATA = PRPINTEFINFO_LIST.addElement("PRPINTEFINFO_DATA");
	    			
	    			addPrpIntefinfoData(PRPINTEFINFODATA, lossSchema);
				}
    		}
    		
		}
    	if(blProposalBI != null){
    		Element PRPINTEFINFO_LIST = body.addElement("PRPINTEFINFO_LIST");
    		int lengthBI = blProposalBI.getBLPrpIntefInfo().getSize();
    		if (lengthBI > 0) {
    			String strRiskCode = blProposalBI.getBLPrpTmain().getArr(0).getRiskCode();
    			PRPINTEFINFO_LIST.addAttribute("RISKCODE", strRiskCode);
    			for (int i = 0; i < lengthBI; i++) {
	    			PrpIntefInfoSchema  lossSchema = blProposalBI.getBLPrpIntefInfo().getArr(i);
	    			Element PRPINTEFINFODATA = PRPINTEFINFO_LIST.addElement("PRPINTEFINFO_DATA");
	    			
	    			addPrpIntefinfoData(PRPINTEFINFODATA, lossSchema);
				}
    		}
    	}
	}
    /**
     * 添加 赔付统计信息 内部信息 节点
     * @param PRPINTEFINFODATA
     * @param lossSchema
     * @throws Exception
     */
    public void addPrpIntefinfoData(Element PRPINTEFINFODATA, PrpIntefInfoSchema  lossSchema)throws Exception{
    	
    	lossSchema.getSchemaTransCode();
    	
    	
    	Element BUSINESSNO = PRPINTEFINFODATA.addElement("BUSINESSNO");
    	String businessNo = lossSchema.getBusinessNo();
    	BUSINESSNO.addText(businessNo);
    	
    	Element POLICYNO = PRPINTEFINFODATA.addElement("POLICYNO");
    	String policyNo = lossSchema.getPolicyNo();
    	POLICYNO.addText(policyNo);
    	
    	Element LASTCLAIMCOUNT = PRPINTEFINFODATA.addElement("LASTCLAIMCOUNT");
    	String lastClaimCount = lossSchema.getLastClaimCount();
    	LASTCLAIMCOUNT.addText(lastClaimCount);
    	
    	Element LASTCLAIMCOUNTORIGIN = PRPINTEFINFODATA.addElement("LASTCLAIMCOUNTORIGIN");
    	String lastClaimCountOrigin = lossSchema.getLastClaimCountOrigin();
    	LASTCLAIMCOUNTORIGIN.addText(lastClaimCountOrigin);
    	
    	Element LASTPAYSUM = PRPINTEFINFODATA.addElement("LASTPAYSUM");
    	String lastPaySum = lossSchema.getLastPaySum();
    	LASTPAYSUM.addText(lastPaySum);
    	
    	Element LASTPAYSUMORIGIN = PRPINTEFINFODATA.addElement("LASTPAYSUMORIGIN");
    	String lastPaySumOrigin = lossSchema.getLastPaySumOrigin();
    	LASTPAYSUMORIGIN.addText(lastPaySumOrigin);
    	
    	Element LASTPAYRATE = PRPINTEFINFODATA.addElement("LASTPAYRATE");
    	String lastPayRate = lossSchema.getLastPayRate();
    	LASTPAYRATE.addText(lastPayRate);
    	
    	Element LASTPAYRATEORIGIN = PRPINTEFINFODATA.addElement("LASTPAYRATEORIGIN");
    	String lastPayRateOrigin = lossSchema.getLastPayRateOrigin();
    	LASTPAYRATEORIGIN.addText(lastPayRateOrigin);
    	
    	Element HISCLAIMCOUNT = PRPINTEFINFODATA.addElement("HISCLAIMCOUNT");
    	String hisClaimCount = lossSchema.getHisClaimCount();
    	HISCLAIMCOUNT.addText(hisClaimCount);
    	
    	Element HISCLAIMCOUNTORIGIN = PRPINTEFINFODATA.addElement("HISCLAIMCOUNTORIGIN");
    	String hisClaimCountOrigin = lossSchema.getHisClaimCountOrigin();
    	HISCLAIMCOUNTORIGIN.addText(hisClaimCountOrigin);
    	
    	Element HISPAYSUM = PRPINTEFINFODATA.addElement("HISPAYSUM");
    	String hisPaySum = lossSchema.getHisPaySum();
    	HISPAYSUM.addText(hisPaySum);
    	
    	Element HISPAYSUMORIGIN = PRPINTEFINFODATA.addElement("HISPAYSUMORIGIN");
    	String hisPaySumOrigin = lossSchema.getHisPaySumOrigin();
    	HISPAYSUMORIGIN.addText(hisPaySumOrigin);
    }
    /**
     * 特别约定
     * @param body
     * @param blProposalCI
     * @param blProposalBI
     * @throws 
     * */
    private void addEngage(Element body, BLProposal blProposalCI,
			BLProposal blProposalBI,Map paramMap) throws Exception{
		
    	if (blProposalCI != null) {
    		Element ENGAGE_LIST = body.addElement("ENGAGE_LIST");
    		String riskCode =  blProposalCI.getBLPrpTmain().getArr(0).getRiskCode();
    		ENGAGE_LIST.addAttribute("RISKCODE", riskCode);
    		int length = blProposalCI.getBLPrpTengage().getSize();
    		String Engage = SysConfig.getProperty("ENGAGE");
    		String[] EngageArray = Engage.split(","); 
    		
			String SerialNo="";
			String clauses="";
    		if (length > 0) {    			
    			for(int j=0;j<length;j++){
    				String arrContent="";
    				String content="";
    				String arrTitle="";
    				String title="";
    				String clauseCode="";
    				String engageSerialNo="";
					PrpTengageSchema  engageSchema = blProposalCI.getBLPrpTengage().getArr(j);
					clauseCode= engageSchema.getClauseCode();
					clauses = engageSchema.getClauses();
					if("T9999".equals(clauseCode)&&"职业证特别约定".equals(clauses)){
						SerialNo=engageSchema.getSerialNo();
						paramMap.put("CLAUSECODE", clauseCode);
	    				paramMap.put("ENGAGESERIALNO", SerialNo);
	    				if("0".equals(engageSchema.getTitleFlag())){
	    					title = engageSchema.getClauses();		    			
	    					arrTitle=arrTitle+title;	
	    				}
	    				paramMap.put("TITLE", arrTitle);				
					}	
					engageSerialNo=engageSchema.getSerialNo();
					if(engageSerialNo.equals(SerialNo)){
					
						if("1".equals(engageSchema.getTitleFlag())){
	    					content=engageSchema.getClauses();
	    					arrContent=arrContent+content;		    					
	    				}
	    				paramMap.put("CONTENT", arrContent);
	    				paramMap.put("LINENO", (Integer.parseInt(engageSchema.getLineNo())-1)+"");
	    				if("1".equals(engageSchema.getTitleFlag())){
    					Element engageData = ENGAGE_LIST.addElement("ENGAGE_DATA");
						addEngageData(engageData, paramMap);
	    				}
					}
				}    			
    			for(int m=0;m<EngageArray.length;m++){
    				
		    		for(int i=0;i<length;i++){	
		    			String arrContent1="";
	    				String content1="";
	    				String arrTitle1="";
	    				String title1="";
	    				String clauseCode1="";
	    				String engageSerialNo1="";
	    				
		    			PrpTengageSchema  engageSchema = blProposalCI.getBLPrpTengage().getArr(i);
		    			clauseCode1=engageSchema.getClauseCode();
		    			if(EngageArray[m].equals(clauseCode1)){
		    				engageSerialNo1= engageSchema.getSerialNo();
		    				paramMap.put("CLAUSECODE", clauseCode1);
		    				paramMap.put("ENGAGESERIALNO", engageSerialNo1);
		    				if("0".equals(engageSchema.getTitleFlag())){
		    					title1 = engageSchema.getClauses();
		    					arrTitle1=arrTitle1+title1;	
		    					paramMap.put("TITLE", arrTitle1);
		    				}
		    				
		    				if("1".equals(engageSchema.getTitleFlag())){
		    					content1=engageSchema.getClauses();
		    					arrContent1=arrContent1+content1;
		    					paramMap.put("CONTENT", arrContent1);
		    					paramMap.put("LINENO", (Integer.parseInt(engageSchema.getLineNo())-1)+"");
		    				}
		    				
		    				if("1".equals(engageSchema.getTitleFlag())&&!SerialNo.equals(engageSerialNo1)){
			    				Element engageData = ENGAGE_LIST.addElement("ENGAGE_DATA");
								addEngageData(engageData, paramMap);
		    				}
		    			}		    			
		    		}
    			}
    		}else{
    			
    		}
    	}
    	if (blProposalBI != null) {
    		Element ENGAGE_LIST = body.addElement("ENGAGE_LIST");
    		String riskCode =  blProposalBI.getBLPrpTmain().getArr(0).getRiskCode();
    		ENGAGE_LIST.addAttribute("RISKCODE", riskCode);
    		int length = blProposalBI.getBLPrpTengage().getSize();
    		String Engage = SysConfig.getProperty("ENGAGE");
    		String[] EngageArray = Engage.split(","); 
    		if (length > 0) {
    			
				String SerialNo="";
				String clauses="";
				for(int j=0;j<length;j++){
					String arrContent="";
					String content="";
					String arrTitle="";
					String title="";
					String clauseCode="";
					String engageSerialNo="";
					PrpTengageSchema  engageSchema = blProposalBI.getBLPrpTengage().getArr(j);
					clauseCode= engageSchema.getClauseCode();
					clauses = engageSchema.getClauses();
					if("T9999".equals(clauseCode)&&"职业证特别约定".equals(clauses)){
						SerialNo=engageSchema.getSerialNo();
						paramMap.put("CLAUSECODE", clauseCode);
	    				paramMap.put("ENGAGESERIALNO", SerialNo);
	    				if("0".equals(engageSchema.getTitleFlag())){
	    					title = engageSchema.getClauses();		    			
	    					arrTitle=arrTitle+title;	
	    				}
	    				paramMap.put("TITLE", arrTitle); 				
					}	
					engageSerialNo=engageSchema.getSerialNo();
					if(engageSerialNo.equals(SerialNo)){
						
						if("1".equals(engageSchema.getTitleFlag())){
	    					content=engageSchema.getClauses();
	    					arrContent=arrContent+content;		    					
	    				}
	    				paramMap.put("CONTENT", arrContent);
	    				paramMap.put("LINENO", (Integer.parseInt(engageSchema.getLineNo())-1)+"");
	    				if("1".equals(engageSchema.getTitleFlag())){
    					Element engageData = ENGAGE_LIST.addElement("ENGAGE_DATA");
						addEngageData(engageData, paramMap);
	    				}
					}
				}
    			for(int m=0;m<EngageArray.length;m++){
    				
		    		for(int i=0;i<length;i++){
		    			String arrContent1="";
	    				String content1="";
	    				String arrTitle1="";
	    				String title1="";
	    				String clauseCode1="";
	    				String engageSerialNo1="";
		    			PrpTengageSchema  engageSchema = blProposalBI.getBLPrpTengage().getArr(i);
		    			clauseCode1=engageSchema.getClauseCode();
		    			if(EngageArray[m].equals(clauseCode1)){
		    				engageSerialNo1= engageSchema.getSerialNo();
		    				paramMap.put("CLAUSECODE", clauseCode1);
		    				paramMap.put("ENGAGESERIALNO", engageSerialNo1);
		    				if("0".equals(engageSchema.getTitleFlag())){
		    					title1 = engageSchema.getClauses();		    			
		    					arrTitle1=arrTitle1+title1;	
		    					paramMap.put("TITLE", arrTitle1);
		    				}
		    				
		    				if("1".equals(engageSchema.getTitleFlag())){
		    					content1=engageSchema.getClauses();
		    					arrContent1=arrContent1+content1;	
		    					paramMap.put("CONTENT", arrContent1);
		    					paramMap.put("LINENO", (Integer.parseInt(engageSchema.getLineNo())-1)+"");
		    				}
		    				
		    				if("1".equals(engageSchema.getTitleFlag())&&!SerialNo.equals(engageSerialNo1)){
					    		Element engageData = ENGAGE_LIST.addElement("ENGAGE_DATA");
								addEngageData(engageData, paramMap);
		    				}
		    			}	
		    			
		    		}

    			}
    		}else{
    			
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

	
	/**
	 * 转人工
	 * 
	 * @param body
	 * @param blProposalCI
	 * @param blProposalBI
	 * @throws
	 * */
	private void addUndwrt(Element UNDWRT_LIST, BLProposal blProposalCI,
			BLProposal blProposalBI, Map paramMap) throws Exception {
		BomRuleResult ruleResultBI = (BomRuleResult) paramMap
				.get("ruleResultBI");
		BomRuleResult ruleResultCI = (BomRuleResult) paramMap
				.get("ruleResultCI");
		if (ruleResultBI != null && blProposalBI != null) {
			String riskcode = blProposalBI.getBLPrpTmain().getArr(0)
					.getRiskCode();
			if (ruleResultBI.getUndwrtInfo().size() > 0) {
				for (int i = 0; i < ruleResultBI.getUndwrtInfo().size(); i++) {
					Element undwrtData = UNDWRT_LIST.addElement("UNDWRT_DATA");
					addUndwrtData(undwrtData,
							ruleResultBI.getUndwrtInfo().get(i), true, riskcode);
				}
			}
		}
		if (ruleResultCI != null && blProposalCI != null) {
			String riskcode = blProposalCI.getBLPrpTmain().getArr(0)
					.getRiskCode();
			if (ruleResultCI.getUndwrtInfo().size() > 0) {
				for (int i = 0; i < ruleResultCI.getUndwrtInfo().size(); i++) {
					Element undwrtData = UNDWRT_LIST.addElement("UNDWRT_DATA");
					addUndwrtData(undwrtData,
							ruleResultCI.getUndwrtInfo().get(i), true, riskcode);
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
	private void addUndwrtData(Element undwrtData, String undwrtInfo,
			boolean bIsUndwrt, String riskcode) throws Exception {
		
		Element EBISUNDWRT = undwrtData.addElement("BISUNDWRT");
		String strBisUndwrt= "0";
		if(bIsUndwrt){
			strBisUndwrt="1";
		}
		EBISUNDWRT.addText(strBisUndwrt);
		
		Element UNDWRTINFO = undwrtData.addElement("UNDWRTINFO");
		UNDWRTINFO.addText(undwrtInfo);
		
		Element RISKCODE = undwrtData.addElement("RISKCODE");
		RISKCODE.addText(riskcode);
	}

	
	
	/**
	 * @throws Exception
	 *             质检
	 * @param body
	 * @param blProposalCI
	 * @param blProposalBI
	 * @throws
	 * */
	private void addQuality(Element QUALITY_LIST, BLProposal blProposalCI,
			BLProposal blProposalBI, Map paramMap) throws Exception {
		BomRuleResult ruleResultBI = (BomRuleResult) paramMap
				.get("ruleResultBI");
		BomRuleResult ruleResultCI = (BomRuleResult) paramMap
				.get("ruleResultCI");
		if (ruleResultBI != null && blProposalBI != null) {
			String riskcode = blProposalBI.getBLPrpTmain().getArr(0)
					.getRiskCode();
			if (ruleResultBI.getUndwrtInfo().size() > 0) {
				for (int i = 0; i < ruleResultBI.getUndwrtInfo().size(); i++) {
					Element undwrtData = QUALITY_LIST.addElement("QUALITY_DATA");
					addQualityData(undwrtData, ruleResultBI.getUndwrtInfo()
							.get(i), true, riskcode);
				}
			}
		}
		if (ruleResultCI != null && blProposalCI != null) {
			String riskcode = blProposalCI.getBLPrpTmain().getArr(0)
					.getRiskCode();
			if (ruleResultCI.getUndwrtInfo().size() > 0) {
				for (int i = 0; i < ruleResultCI.getUndwrtInfo().size(); i++) {
					Element undwrtData = QUALITY_LIST.addElement("QUALITY_DATA");
					addQualityData(undwrtData, ruleResultCI.getUndwrtInfo()
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
		String strBisUndwrt ="0";
		if(bIsQuality){
			strBisUndwrt="1";
		}
		BISQUALITY.addText(strBisUndwrt);
		
		Element QUALITYINFO = qualityData.addElement("QUALITYINFO");
		QUALITYINFO.addText(qualityInfo);
		
		Element RISKCODE = qualityData.addElement("RISKCODE");
		RISKCODE.addText(riskcode);
	}
	
	
		/**
		 * @throws Exception
		 *             质检
		 * @param body
		 * @param blProposalCI
		 * @param blProposalBI
		 * @throws
		 * */
		private void addLifeTableInfo(Element LIFETABLEINFO_LIST, BLProposal blProposalCI,
				BLProposal blProposalBI, Map paramMap) throws Exception {
			if (blProposalBI != null) {
				if(blProposalBI.getBlPrpLifeTableInfo().getSize()>0){
					for(int i=0;i<blProposalBI.getBlPrpLifeTableInfo().getSize();i++){
						Element LIFETABLEINFO_DATA=LIFETABLEINFO_LIST.addElement("LIFETABLEINFO_DATA");
						addLifeTableInfoData(LIFETABLEINFO_DATA,blProposalBI.getBlPrpLifeTableInfo().getArr(i));
					}
				}
			}
			if ( blProposalCI != null) {
				if(blProposalCI.getBlPrpLifeTableInfo().getSize()>0){
					for(int i=0;i<blProposalCI.getBlPrpLifeTableInfo().getSize();i++){
						Element LIFETABLEINFO_DATA=LIFETABLEINFO_LIST.addElement("LIFETABLEINFO_DATA");
						addLifeTableInfoData(LIFETABLEINFO_DATA,blProposalCI.getBlPrpLifeTableInfo().getArr(i));
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
		private void addLifeTableInfoData(Element lIFETABLEINFO_DATA,
				PrpLifeTableInfoSchema prpLifeTableInfoSchema) {
			
			Element SUGGESTWAYS = lIFETABLEINFO_DATA.addElement("SUGGESTWAYS");
			SUGGESTWAYS.addText(prpLifeTableInfoSchema.getSuggestWays());
		}
		
}
