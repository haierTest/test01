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

import com.sp.indiv.ci.schema.CIInsureDemandLossSchema;
import com.sp.indiv.ci.schema.CIInsureDemandPaySchema;
import com.sp.indiv.ci.schema.CIInsureDemandSchema;
import com.sp.phonesale.schema.TargetMarketInfoSchema;

import com.sp.prpall.blsvr.tb.BLProposal;
import com.sp.prpall.schema.PrpIntefInfoSchema;
import com.sp.prpall.schema.PrpTTrafficDetailSchema;
import com.sp.prpall.schema.PrpTcarshipTaxSchema;
import com.sp.prpall.schema.PrpTcarshipTaxTJSchema;
import com.sp.prpall.schema.PrpTengageSchema;
import com.sp.prpall.schema.PrpTitemCarSchema;
import com.sp.prpall.schema.PrpTitemKindSchema;
import com.sp.prpall.schema.PrpTmainSchema;
import com.sp.prpall.schema.PrpTprofitDetailSchema;
import com.sp.quotation.pub.QuotationUtils;
import com.sp.utility.SysConfig;
import com.sp.utility.UtiPower;
import com.sp.utility.error.UserException;
import com.sp.utility.string.ChgData;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import com.sp.prpall.blsvr.cb.BLPrpBasicInfoCaChe;
import com.sp.prpall.blsvr.misc.BLPrpRuleUndwrtInfo;
import com.sp.prpall.schema.PrpRuleUndwrtInfoSchema;


/**
 * 询报价报文封装
 * 
 * */
public class TBQuotationZHXMLEncoder {
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
		
		if(SysConfig.getProperty("App_OperateSite").indexOf(","+OperateSiteBC+",")>-1){
			Element OPERATESITE = head.addElement("OPERATESITE");
			OPERATESITE.addText(OperateSiteBC);
			if((String)paramMap.get("Error_Code") == null || "".equals((String)paramMap.get("Error_Code"))){
				responseCode.addText("100000");
				responseMessage.addText(e.getMessage());
			}else{
				responseCode.addText((String)paramMap.get("Error_Code"));
				responseMessage.addText((String)paramMap.get("Error_Message"));
			}
		}
		
		else{
			responseCode.addText("100000");
			responseMessage.addText(e.getMessage());
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
		
		if(SysConfig.getProperty("App_OperateSite").indexOf(","+OperateSiteBC+",")>-1){
			OperateSite.addText((String) paramMap.get("OperateSiteBC"));
		}
		
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
		
		
		
		
		if(SysConfig.getProperty("App_OperateSite").indexOf(","+OperateSiteBC+",")>-1){
			
			Element INFOTRANSNO = body.addElement("INFOTRANSNO");
			String InfoTransNo = (String) paramMap.get("InfoTransNo");
			INFOTRANSNO.addText(InfoTransNo);
			
			
			Element OPERATESITE = body.addElement("OPERATESITE");
			OPERATESITE.addText(OperateSiteBC);
			




		}
		
		
		
		
		
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
		
		
		
		
		
		

		
		
		
		Element REMARKBI=body.addElement("REMARKBI");
		
		Element UNDWRT_LIST = body.addElement("UNDWRT_LIST");
		
		if(blProposalBI!=null&& blProposalBI.getBLCIInsureDemand()!=null&&blProposalBI.getBLCIInsureDemand().getSize()>0){
			CIInsureDemandSchema  demandSchema =blProposalBI.getBLCIInsureDemand().getArr(0);
			if(!"".equals(demandSchema.getReCoverMsg())){
	        	REMARKBI.addText(demandSchema.getReCoverMsg());
	        }else{
	        	REMARKBI.addText(demandSchema.getRemark());
	        }
		}
		
		
		if (blProposalBI != null) {
			if(SysConfig.getProperty("App_OperateSite").indexOf(","+OperateSiteBC+",")>-1){
				demandNOBI.setText(blProposalBI.getBLCIInsureDemand().getArr(0).getDemandNo());
			}else{
			
			if(blProposalBI.getBLCIInsureDemand().getSize()>0&&"1".equals(blProposalBI.getBLCIInsureDemand().getArr(0).getRenewalFlag())){
				demandNOBI.setText(blProposalBI.getBLCIInsureDemand().getArr(0).getDemandNo());
				QUESTION.setText(blProposalBI.getBLCIInsureDemand().getArr(0).getQuestion());
				return;
				}
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
			
			String sumamountbi = blProposalBI.getBLPrpTmain().getArr(0).getSumAmount();
			sumAMOUNTBI.addText(sumamountbi);
			String sumpremiumbi = blProposalBI.getBLPrpTmain().getArr(0).getSumPremium();
			sumPREMIUMBI.addText(sumpremiumbi);
			String sumdiscountbi = blProposalBI.getBLPrpTmain().getArr(0).getDiscount();
			
			if(SysConfig.getProperty("App_OperateSite").indexOf(","+OperateSiteBC+",")>-1){
				sumDISCOUNTBI.addText(sumdiscountbi);

			}else{
				sumDISCOUNTBI.addText(df4.format(1-Double.parseDouble(sumdiscountbi)));
			}
			

			
			
			basePREMIUMBI.addText(blProposalBI.getBLPrpTmain().getArr(0).getSumBasePremium());			
			
			
			SUMSUBPREMBI.addText(blProposalBI.getBLPrpTmain().getArr(0).getSumSubPrem());
			DISCOUNTBI.addText(blProposalBI.getBLPrpTmain().getArr(0).getDiscount());
			
			
 			benchMarkPremiumBI.addText(blProposalBI.getBLPrpTmain().getArr(0).getSumBenchMarkPremium()); 			
 			
 			
 			disRateBI.addText(blProposalBI.getBLPrpTmain().getArr(0).getDisRate());
 			
 			

 			if(SysConfig.getProperty("App_OperateSite").indexOf(","+OperateSiteBC+",")>-1){
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
 			
 			}
 			
 			
		}
		
		if(blProposalCI != null){
				if(SysConfig.getProperty("App_OperateSite").indexOf(","+OperateSiteBC+",")>-1){
					demandNOCI.setText(blProposalCI.getBLCIInsureDemand().getArr(0).getDemandNo());
				}else{
					
			
				
					if(blProposalCI.getBLCIInsureDemand().getSize()>0&&"1".equals(blProposalCI.getBLCIInsureDemand().getArr(0).getRenewalFlag())){
						demandNOCI.setText(blProposalCI.getBLCIInsureDemand().getArr(0).getDemandNo());
						QUESTION.setText(blProposalCI.getBLCIInsureDemand().getArr(0).getQuestion());
				return;
				}
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
			
			if(SysConfig.getProperty("App_OperateSite").indexOf(","+OperateSiteBC+",")>-1){
				sumDISCOUNTCI.addText(sumdiscountci);

			}else{
				sumDISCOUNTCI.addText(df4.format(1-Double.parseDouble(sumdiscountci)));
			}
			

	
	
			
			basePremiumCI.addText(blProposalCI.getBLPrpTmain().getArr(0).getSumBasePremium());			
			
			
			SUMSUBPREMCI.addText(blProposalCI.getBLPrpTmain().getArr(0).getSumSubPrem());
			DISCOUNTCI.addText(blProposalCI.getBLPrpTmain().getArr(0).getDiscount());			
			
			
 			benchMarkPremiumCI.addText(blProposalCI.getBLPrpTitemKind().getArr(0).getBenchMarkPremium());			
 			
 			disRateCI.addText(blProposalCI.getBLPrpTmain().getArr(0).getDisRate());
 			
 			
 			

 			if(SysConfig.getProperty("App_OperateSite").indexOf(","+OperateSiteBC+",")>-1){
 				
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
 				
 			
 			
 			}
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
			String lastYearEndDateBI = blProposalBI.getBLCIInsureDemand().getArr(0).getBusiLastYearEndDate();
			LASTYEARENDDATEBI.addText(lastYearEndDateBI);
			
			
			if(SysConfig.getProperty("App_OperateSite").indexOf(","+OperateSiteBC+",")>-1){
				
				Element TESTCARSTATUS = body.addElement("TESTCARSTATUS");
				TESTCARSTATUS.addText(blProposalBI.getBLPrpTitemCar().getArr(0).getCarCheckStatus());
			}
			
			
			
		
			
	
			
			
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
			
			String lastYearStartDateCI = blProposalCI.getBLCIInsureDemand().getArr(0).getLastYearStartDate();
			LASTYEARSTARTDATECI.addText(lastYearStartDateCI);
			String lastYearEndDateCI = blProposalCI.getBLCIInsureDemand().getArr(0).getLastYearEndDate();
			LASTYEARENDDATECI.addText(lastYearEndDateCI);
			
	



		

	
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
		
		
		
		if ((blProposalCI!=null&&SysConfig.getProperty("App_OperateSite").indexOf("," + blProposalCI.getBLPrpTmain().getArr(0).getOperateSite() + ",") > -1)
				||(blProposalBI!=null&& SysConfig.getProperty("App_OperateSite").indexOf("," + blProposalBI.getBLPrpTmain().getArr(0).getOperateSite() + ",") > -1)) {
			addProfitDetail(body, blProposalCI, blProposalBI);
		}
		
		
		
		addClaim(body, blProposalCI, blProposalBI);
		
		addTrafficDetail(body, blProposalCI, blProposalBI);
		
		addPecc(body, blProposalCI, blProposalBI);
		
		
		addCIInsureDemand(body, blProposalCI, blProposalBI);
		
		
		
		
		if((blProposalCI!=null&&SysConfig.getProperty("App_OperateSite").indexOf(","+blProposalCI.getBLPrpTmain().getArr(0).getOperateSite()+",")>-1)
				||(blProposalBI!=null&& SysConfig.getProperty("App_OperateSite").indexOf(","+blProposalBI.getBLPrpTmain().getArr(0).getOperateSite()+",")>-1)){
			addPrpintefInfo(body, blProposalCI, blProposalBI);
		}
		
		
		
		
		
		if(SysConfig.getProperty("App_OperateSite").indexOf(","+OperateSiteBC+",")>-1){
			addTargetMarket(body, blProposalCI, blProposalBI,paramMap);
		}
		
		else{
			addEngage(body, blProposalCI, blProposalBI,paramMap);
		}
		
		addUndwrt(UNDWRT_LIST, blProposalCI, blProposalBI, paramMap);
		
	}
	
	private void addTargetMarket(Element body, BLProposal blProposalCI, BLProposal blProposalBI,Map paramMap) throws Exception{
		if (blProposalCI != null) {
			String Business_Kind  = "";
			String Business_Class= "";
			String Final_Business_Class = "";
			String Final_Pay_Rate = "";
			String Cost_Business_Class = "";
			String Cost_Margin = "";
			String Expense_Type = "";
			String Cost_MarginBC = "";
			String Cost_Business_Class_BC = "";
			String Final_Business_Class_BC = "";
			String Custom_Type_BC = "";
			
			String Pay_Rate_Car_Kind_BC = "";
			String Pay_Car_Actual_ValueBC = "";
			
			
			
			String SumExpense_Rate="";
			String OperationCost_Rate="";
			String SalesExpense_Rate="";
			String SalesExpense_Threshold="";
			String UNSettlement_Rate="";
			String ComUN_Rate="";
			
			
			
			Business_Kind = blProposalCI.getBLPrpTmain().getArr(0).getBusinessKind();
			Business_Class =  blProposalCI.getBLPrpTmain().getArr(0).getBusinessClass();
			Final_Business_Class = blProposalCI.getBlPrpLifeTableInfo().getArr(0).getFinalBusinessClass();
			Final_Pay_Rate = blProposalCI.getBlPrpLifeTableInfo().getArr(0).getFinalPayRate();
			Cost_Business_Class  = blProposalCI.getBlPrpLifeTableInfo().getArr(0).getCostBusinessClass();
			Cost_Margin = blProposalCI.getBlPrpLifeTableInfo().getArr(0).getCostMargin();
			Expense_Type = blProposalCI.getBlPrpLifeTableInfo().getArr(0).getExpenseType();
			Cost_MarginBC = blProposalCI.getBLPrpTmain().getArr(0).getCostMarginBC();
			Cost_Business_Class_BC = blProposalCI.getBLPrpTmain().getArr(0).getCostBusinessClassBC();
			Final_Business_Class_BC = blProposalCI.getBLPrpTmain().getArr(0).getFinalBusinessClassBC();
			Custom_Type_BC = blProposalCI.getBLPrpTmain().getArr(0).getBusinessRemark1();
			Pay_Car_Actual_ValueBC = blProposalCI.getBLPrpTitemCar().getArr(0).getActualValue();
			Pay_Rate_Car_Kind_BC = blProposalCI.getBLPrpTitemCarExt().getArr(0).getPayRateCarKind();
			
			Element TARGEMARKETLIST = body.addElement("TARGET_MARKET_LIST");
			

			String riskCode =  blProposalCI.getBLPrpTmain().getArr(0).getRiskCode();
    		TARGEMARKETLIST.addAttribute("RISKCODE", riskCode);
			Element TARGETMARKETDATA = TARGEMARKETLIST.addElement("TARGET_MARKET_DATA");
		    Element BUSINESSKIND  = TARGETMARKETDATA.addElement("BUSINESSKIND");        
		    Element BUSINESSCLASS= TARGETMARKETDATA.addElement("BUSINESSCLASS");         
		    Element FINALBUSINESSCLASS = TARGETMARKETDATA.addElement("FINALBUSINESSCLASS");  
		    Element FINALPAYRATE = TARGETMARKETDATA.addElement("FINALPAYRATE");        
			Element COSTBUSINESSCLASS = TARGETMARKETDATA.addElement("COSTBUSINESSCLASS");   
			Element COSTMARGIN = TARGETMARKETDATA.addElement("COSTMARGIN");           
			Element EXPENSETYPE = TARGETMARKETDATA.addElement("EXPENSETYPE");          
			Element COSTMARGINBC = TARGETMARKETDATA.addElement("COSTMARGINBC");         
			Element COSTBUSINESSCLASSBC = TARGETMARKETDATA.addElement("COSTBUSINESSCLASSBC ");
			Element FINALBUSINESSCLASSBC = TARGETMARKETDATA.addElement("FINALBUSINESSCLASSBC");
			Element CUSTOMTYPEBC = TARGETMARKETDATA.addElement("CUSTOMTYPEBC");        
		    Element TARGETMARKETMODELCODEBC = TARGETMARKETDATA.addElement("TARGETMARKETMODELCODEBC");
			Element PAYRATECARKINDBC = TARGETMARKETDATA.addElement("PAYRATECARKINDBC");  
			Element PAYCARACTUALVALUEBC = TARGETMARKETDATA.addElement("PAYCARACTUALVALUEBC");
			
		    BUSINESSKIND.addText(Business_Kind);               
		    BUSINESSCLASS.addText(Business_Class);              
		    FINALBUSINESSCLASS.addText(Final_Business_Class);         
		    FINALPAYRATE.addText(Final_Pay_Rate);               
			COSTBUSINESSCLASS.addText(Cost_Business_Class);          
			COSTMARGIN.addText(Cost_Margin);                 
			EXPENSETYPE.addText(Expense_Type);                
			COSTMARGINBC.addText(Cost_MarginBC);               
			COSTBUSINESSCLASSBC.addText(Cost_Business_Class_BC);        
			FINALBUSINESSCLASSBC.addText(Final_Business_Class_BC);       
			CUSTOMTYPEBC.addText(Custom_Type_BC);               
		    
			PAYRATECARKINDBC.addText(Pay_Rate_Car_Kind_BC);           
			PAYCARACTUALVALUEBC.addText(Pay_Car_Actual_ValueBC);
			
			
	
			
			
			if(!paramMap.isEmpty()){
				TargetMarketInfoSchema targetMarketInfoSchemaCI=(TargetMarketInfoSchema) paramMap.get("targetMarketInfoSchemaCI");
				if(targetMarketInfoSchemaCI!=null){
					
				
				SumExpense_Rate=targetMarketInfoSchemaCI.getSumExpenseRate();
				OperationCost_Rate=targetMarketInfoSchemaCI.getOperationCostRate();
				SalesExpense_Rate=targetMarketInfoSchemaCI.getSalesExpenseRate();
				SalesExpense_Threshold=targetMarketInfoSchemaCI.getSalesExpenseThreshold();
				UNSettlement_Rate=targetMarketInfoSchemaCI.getUNSettlementRate();
				ComUN_Rate=targetMarketInfoSchemaCI.getComUNRate();
				
				Element SUMEXPENSERATE=TARGETMARKETDATA.addElement("SUMEXPENSERATE");
				Element OPERATIONCOSTRATE=TARGETMARKETDATA.addElement("OPERATIONCOSTRATE");
				Element SALESEXPENSERATE=TARGETMARKETDATA.addElement("SALESEXPENSERATE");
				Element SALESEXPENSETHRESHOLD=TARGETMARKETDATA.addElement("SALESEXPENSETHRESHOLD");
				Element UNSETTLEMENTRATE=TARGETMARKETDATA.addElement("UNSETTLEMENTRATE");
				Element COMUNRATE=TARGETMARKETDATA.addElement("COMUNRATE");
				
				 SUMEXPENSERATE.addText(SumExpense_Rate);
				 OPERATIONCOSTRATE.addText(OperationCost_Rate);
				 SALESEXPENSERATE.addText(SalesExpense_Rate);
				 SALESEXPENSETHRESHOLD.addText(SalesExpense_Threshold);
				 UNSETTLEMENTRATE.addText(UNSettlement_Rate);
				 COMUNRATE.addText(ComUN_Rate);
				}
			}
			
			
			
			

		}
		
		if (blProposalBI != null) {
			String Business_Kind  = "";
			String Business_Class= "";
			String Final_Business_Class = "";
			String Final_Pay_Rate = "";
			String Cost_Business_Class = "";
			String Cost_Margin = "";
			String Expense_Type = "";
			String Cost_MarginBC = "";
			String Cost_Business_Class_BC = "";
			String Final_Business_Class_BC = "";
			String Custom_Type_BC = "";
			
			String Pay_Rate_Car_Kind_BC = "";
			String Pay_Car_Actual_ValueBC = "";
			
			
			String SumExpense_Rate="";
			String OperationCost_Rate="";
			String SalesExpense_Rate="";
			String SalesExpense_Threshold="";
			String UNSettlement_Rate="";
			String ComUN_Rate="";
			
			
			
			Business_Kind = blProposalBI.getBLPrpTmain().getArr(0).getBusinessKind();
			Business_Class =  blProposalBI.getBLPrpTmain().getArr(0).getBusinessClass();
			Final_Business_Class = blProposalBI.getBlPrpLifeTableInfo().getArr(0).getFinalBusinessClass();
			Final_Pay_Rate = blProposalBI.getBlPrpLifeTableInfo().getArr(0).getFinalPayRate();
			Cost_Business_Class  = blProposalBI.getBlPrpLifeTableInfo().getArr(0).getCostBusinessClass();
			Cost_Margin = blProposalBI.getBlPrpLifeTableInfo().getArr(0).getCostMargin();
			Expense_Type = blProposalBI.getBlPrpLifeTableInfo().getArr(0).getExpenseType();
			Cost_MarginBC = blProposalBI.getBLPrpTmain().getArr(0).getCostMarginBC();
			Cost_Business_Class_BC = blProposalBI.getBLPrpTmain().getArr(0).getCostBusinessClassBC();
			Final_Business_Class_BC = blProposalBI.getBLPrpTmain().getArr(0).getFinalBusinessClassBC();
			Custom_Type_BC = blProposalBI.getBLPrpTmain().getArr(0).getBusinessRemark1();
			Pay_Car_Actual_ValueBC = blProposalBI.getBLPrpTitemCar().getArr(0).getActualValue();
			Pay_Rate_Car_Kind_BC = blProposalBI.getBLPrpTitemCarExt().getArr(0).getPayRateCarKind();
			
			Element TARGEMARKETLIST = body.addElement("TARGET_MARKET_LIST");
			String riskCode =  blProposalBI.getBLPrpTmain().getArr(0).getRiskCode();
    		TARGEMARKETLIST.addAttribute("RISKCODE", riskCode);
			Element TARGETMARKETDATA = TARGEMARKETLIST.addElement("TARGET_MARKET_DATA");
		    Element BUSINESSKIND  = TARGETMARKETDATA.addElement("BUSINESSKIND");        
		    Element BUSINESSCLASS= TARGETMARKETDATA.addElement("BUSINESSCLASS");
		    Element FINALBUSINESSCLASS = TARGETMARKETDATA.addElement("FINALBUSINESSCLASS");  
		    Element FINALPAYRATE = TARGETMARKETDATA.addElement("FINALPAYRATE");        
			Element COSTBUSINESSCLASS = TARGETMARKETDATA.addElement("COSTBUSINESSCLASS");   
			Element COSTMARGIN = TARGETMARKETDATA.addElement("COSTMARGIN");           
			Element EXPENSETYPE = TARGETMARKETDATA.addElement("EXPENSETYPE");          
			Element COSTMARGINBC = TARGETMARKETDATA.addElement("COSTMARGINBC");         
			Element COSTBUSINESSCLASSBC = TARGETMARKETDATA.addElement("COSTBUSINESSCLASSBC ");
			Element FINALBUSINESSCLASSBC = TARGETMARKETDATA.addElement("FINALBUSINESSCLASSBC");
			Element CUSTOMTYPEBC = TARGETMARKETDATA.addElement("CUSTOMTYPEBC");        
		    Element TARGETMARKETMODELCODEBC = TARGETMARKETDATA.addElement("TARGETMARKETMODELCODEBC");
			Element PAYRATECARKINDBC = TARGETMARKETDATA.addElement("PAYRATECARKINDBC");  
			Element PAYCARACTUALVALUEBC = TARGETMARKETDATA.addElement("PAYCARACTUALVALUEBC");
			
		    BUSINESSKIND.addText(Business_Kind);               
		    BUSINESSCLASS.addText(Business_Class);              
		    FINALBUSINESSCLASS.addText(Final_Business_Class);         
		    FINALPAYRATE.addText(Final_Pay_Rate);               
			COSTBUSINESSCLASS.addText(Cost_Business_Class);          
			COSTMARGIN.addText(Cost_Margin);                 
			EXPENSETYPE.addText(Expense_Type);                
			COSTMARGINBC.addText(Cost_MarginBC);               
			COSTBUSINESSCLASSBC.addText(Cost_Business_Class_BC);        
			FINALBUSINESSCLASSBC.addText(Final_Business_Class_BC);       
			CUSTOMTYPEBC.addText(Custom_Type_BC);               
		    
			PAYRATECARKINDBC.addText(Pay_Rate_Car_Kind_BC);           
			PAYCARACTUALVALUEBC.addText(Pay_Car_Actual_ValueBC); 
			
			
			if(!paramMap.isEmpty()){
				TargetMarketInfoSchema targetMarketInfoSchemaBI=(TargetMarketInfoSchema) paramMap.get("targetMarketInfoSchemaBI");
				if(targetMarketInfoSchemaBI!=null){
					
				
				SumExpense_Rate=targetMarketInfoSchemaBI.getSumExpenseRate();
				OperationCost_Rate=targetMarketInfoSchemaBI.getOperationCostRate();
				SalesExpense_Rate=targetMarketInfoSchemaBI.getSalesExpenseRate();
				SalesExpense_Threshold=targetMarketInfoSchemaBI.getSalesExpenseThreshold();
				UNSettlement_Rate=targetMarketInfoSchemaBI.getUNSettlementRate();
				ComUN_Rate=targetMarketInfoSchemaBI.getComUNRate();
				
				Element SUMEXPENSERATE=TARGETMARKETDATA.addElement("SUMEXPENSERATE");
				Element OPERATIONCOSTRATE=TARGETMARKETDATA.addElement("OPERATIONCOSTRATE");
				Element SALESEXPENSERATE=TARGETMARKETDATA.addElement("SALESEXPENSERATE");
				Element SALESEXPENSETHRESHOLD=TARGETMARKETDATA.addElement("SALESEXPENSETHRESHOLD");
				Element UNSETTLEMENTRATE=TARGETMARKETDATA.addElement("UNSETTLEMENTRATE");
				Element COMUNRATE=TARGETMARKETDATA.addElement("COMUNRATE");
				
				 SUMEXPENSERATE.addText(SumExpense_Rate);
				 OPERATIONCOSTRATE.addText(OperationCost_Rate);
				 SALESEXPENSERATE.addText(SalesExpense_Rate);
				 SALESEXPENSETHRESHOLD.addText(SalesExpense_Threshold);
				 UNSETTLEMENTRATE.addText(UNSettlement_Rate);
				 COMUNRATE.addText(ComUN_Rate);
				}
			}
			
			
			
			
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
					addCarData(carData,titemCarSchema);
				}
    		}else{
    			

    		}
			
		}else if (blProposalCI != null) {
			int lengthCI = blProposalCI.getBLPrpTitemCar().getSize();
			if (lengthCI > 0 ) {
				for (int i = 0; i < lengthCI; i++) {
					
					Element carData = carList.addElement("CAR_DATA");
					PrpTitemCarSchema  titemCarSchema =  blProposalCI.getBLPrpTitemCar().getArr(i);
					addCarData(carData,titemCarSchema);
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
    public void addCarData(Element carData,PrpTitemCarSchema titemCarSchema)throws Exception{
    	
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
    	
    	Element MODECODE = KINDDATA.addElement("MODECODE");
    	String modeCode = KindSchema.getModeCode();
    	MODECODE.addText(modeCode);
    	
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
    				if("".equals(DemandPaySchema.getClaimNotificationNo()) && DemandPaySchema.getClaimNotificationNo()!=null){
	    				Element CLAIMDATA = CLAIM_LIST.addElement("CLAIM_DATA");
	    				
	    				addClaimData(CLAIMDATA, DemandPaySchema);
	    				Element RISKCODE = CLAIMDATA.addElement("RISKCODE");
	    				RISKCODE.addText("0507");
    				}
    			}
			}
		}
    	if (blProposalBI != null) {
    		int lengthBI = blProposalBI.getBLCIInsureDemandPay().getSize();
    		if (lengthBI > 0) {
    			for (int i = 0; i < lengthBI; i++) {
					CIInsureDemandPaySchema DemandPaySchema = blProposalBI.getBLCIInsureDemandPay().getArr(i);
					if("".equals(DemandPaySchema.getClaimNotificationNo()) && DemandPaySchema.getClaimNotificationNo()!=null){
						Element CLAIMDATA = CLAIM_LIST.addElement("CLAIM_DATA");
						
						addClaimData(CLAIMDATA, DemandPaySchema);
						Element RISKCODE = CLAIMDATA.addElement("RISKCODE");
						RISKCODE.addText("0508");
						
						String comCode = blProposalBI.getBLPrpTmain().getArr(0).getComCode();
	    				RISKCODE.addText(UtiPower.ChangeRiskCode0528("0508",comCode));    				
	    				
					}
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
	    			
	    			addCIInsureDemandData(CIINSUREDEMANDDATA, demandSchema);
				}
	    		for (int i = 0; i < lengthBI; i++) {
	    			CIInsureDemandSchema  demandSchema = blProposalBI.getBLCIInsureDemand().getArr(i);
	    			
	    			Element CIINSUREDEMANDDATA = CIInsureDemandList.addElement("CIINSUREDEMAND_DATA");
	    			
	    			addCIInsureDemandData(CIINSUREDEMANDDATA, demandSchema);
				}
    		}else if (lengthCI > 0 && lengthBI == 0) {
	    		for (int i = 0; i < lengthCI; i++) {
	    			CIInsureDemandSchema  demandSchema = blProposalCI.getBLCIInsureDemand().getArr(i);
	    			
	    			Element CIINSUREDEMANDDATA = CIInsureDemandList.addElement("CIINSUREDEMAND_DATA");
	    			
	    			addCIInsureDemandData(CIINSUREDEMANDDATA, demandSchema);
				}
    		}else if (lengthCI == 0 && lengthBI > 0) {
    			for (int i = 0; i < lengthBI; i++) {
	    			CIInsureDemandSchema  demandSchema = blProposalBI.getBLCIInsureDemand().getArr(i);
	    			
	    			Element CIINSUREDEMANDDATA = CIInsureDemandList.addElement("CIINSUREDEMAND_DATA");
	    			
	    			addCIInsureDemandData(CIINSUREDEMANDDATA, demandSchema);
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
    public void addCIInsureDemandData(Element CIINSUREDEMANDDATA,CIInsureDemandSchema  demandSchema)throws Exception{
    	
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
	    	double dSafeAdjust=Double.parseDouble(ChgData.chgStrZero(safeAdjust));
	    	String addonCount="";
	    	if(dSafeAdjust==0.9){
	    		addonCount="1";
	    	}else{
	    		addonCount="2";
	    	}
	    	SAFEADJUST.addText(addonCount);
	    	
	    	Element NONCLAIMADJUST = CIINSUREDEMANDDATA.addElement("NONCLAIMADJUST");
	    	String nonclaimAdjust = demandSchema.getNonclaimAdjust();
	    	double dNonclaimAdjust=Double.parseDouble(ChgData.chgStrZero(nonclaimAdjust));
	    	String damagedFactorGrade="";
	    	if (dNonclaimAdjust==0.7) {
	    		damagedFactorGrade = "11";
			} else if (dNonclaimAdjust ==0.8) {
				damagedFactorGrade = "12";
			} else if (dNonclaimAdjust ==0.9) {
				damagedFactorGrade = "13";
			} else if (dNonclaimAdjust==1.0) {
				damagedFactorGrade = "14";
			} else if (dNonclaimAdjust==1.1) {
				damagedFactorGrade = "15";
			} else if (dNonclaimAdjust ==1.2) {
				damagedFactorGrade = "16";
			} else if (dNonclaimAdjust==1.3 ){
				damagedFactorGrade = "17";
			}
	    	NONCLAIMADJUST.addText(damagedFactorGrade);
     
    	
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
     * @param body
     * @param blProposalCI
     * @param blProposalBI
     * @throws 
     * */
	private void addUndwrt(Element UNDWRT_LIST, BLProposal blProposalCI,
			BLProposal blProposalBI, Map paramMap) throws Exception {
		if (blProposalCI != null) {
			if(paramMap.get("undwrtInfoCI")!=null){
			BLPrpRuleUndwrtInfo blPrpRuleUndwrtInfo = (BLPrpRuleUndwrtInfo) paramMap
					.get("undwrtInfoCI");
			if (blPrpRuleUndwrtInfo.getSize() > 0) {
				boolean bIsUndwrtCI=(Boolean) paramMap.get("bIsUndwrtCI");
				for(int i=0;i<blPrpRuleUndwrtInfo.getSize();i++){
					Element undwrtData = UNDWRT_LIST.addElement("UNDWRT_DATA");
					addUndwrtData(undwrtData, blPrpRuleUndwrtInfo.getArr(i),bIsUndwrtCI);
				}
			}else{
				
			}
			}
		}
		if (blProposalBI != null) {
			if(paramMap.get("undwrtInfoBI")!=null){
			BLPrpRuleUndwrtInfo blPrpRuleUndwrtInfo = (BLPrpRuleUndwrtInfo) paramMap
					.get("undwrtInfoBI");
			if (blPrpRuleUndwrtInfo.getSize() > 0) {
				boolean bIsUndwrtBI=(Boolean) paramMap.get("bIsUndwrtBI");
				for(int i=0;i<blPrpRuleUndwrtInfo.getSize();i++){
						Element undwrtData = UNDWRT_LIST.addElement("UNDWRT_DATA");
						addUndwrtData(undwrtData, blPrpRuleUndwrtInfo.getArr(i),bIsUndwrtBI);
				}
			}else{
				
			}
		}
		}
	}
/**
 * 转人工 内部信息节点
 * @param engageData
 * @param engageSchema
 * @throws Exception
 * */
	private void addUndwrtData(Element undwrtData,PrpRuleUndwrtInfoSchema prpRuleUndwrtInfoSchema,boolean bIsUndwrt)throws Exception {
		
		Element EBISUNDWRT = undwrtData.addElement("BISUNDWRT");
    	String strBisUndwrt = Boolean.toString(bIsUndwrt);
    	EBISUNDWRT.addText(strBisUndwrt);
    	
    	Element UNDWRTINFO = undwrtData.addElement("UNDWRTINFO");
    	String strUndwrtInfo = prpRuleUndwrtInfoSchema.getUndwrtInfo();
    	UNDWRTINFO.addText(strUndwrtInfo);
    	
    	Element RISKCODE = undwrtData.addElement("RISKCODE");
    	String strRiskCode = prpRuleUndwrtInfoSchema.getRiskCode();
    	RISKCODE.addText(strRiskCode);
	}
	
	
}
