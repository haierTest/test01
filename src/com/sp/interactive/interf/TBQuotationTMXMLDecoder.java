package com.sp.interactive.interf;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.sp.sysframework.common.datatype.DateTime;
import com.sp.sysframework.reference.AppConfig;
import com.sp.sysframework.reference.DBManager;
import com.sp.utiall.blsvr.BLPrpDagent;
import com.sp.utiall.blsvr.BLPrpDcompany;
import com.sp.utility.string.ChgDate;
import com.sp.utility.string.Str;
import com.sp.indiv.ci.schema.CIInsureDemandSchema;
import com.sp.platform.bl.action.domain.BLPrpDagreementAction;
import com.sp.platform.bl.action.domain.BLPrpDagreementActionBase;
import com.sp.platform.bl.facade.BLPrpDagreeDetailFacade;
import com.sp.prpall.blsvr.tb.BLProposal;
import com.sp.prpall.schema.PrpIntefInfoSchema;
import com.sp.prpall.schema.PrpTNewEngageSchema;
import com.sp.prpall.schema.PrpTTrafficDetailSchema;
import com.sp.prpall.schema.PrpTcarDeviceSchema;
import com.sp.prpall.schema.PrpTcarDriverSchema;
import com.sp.prpall.schema.PrpTcarshipTaxSchema;
import com.sp.prpall.schema.PrpTcarshipTaxTJSchema;
import com.sp.prpall.schema.PrpTexpenseSchema;
import com.sp.prpall.schema.PrpTinsuredNatureSchema;
import com.sp.prpall.schema.PrpTinsuredSchema;
import com.sp.prpall.schema.PrpTitemCarExtSchema;
import com.sp.prpall.schema.PrpTitemCarSchema;
import com.sp.prpall.schema.PrpTitemKindSchema;
import com.sp.prpall.schema.PrpTmainExtrasSchema;
import com.sp.prpall.schema.PrpTmainSchema;
import com.sp.prpall.schema.PrpTprofitDetailSchema;
import com.sp.prpall.schema.PrpTrenewalSchema ;
import com.sp.quotation.pub.QuotationUtils;
import com.sp.quotation.test.TestClass;
import com.sp.utility.SysConfig;
import com.sp.utility.UtiPower;
import com.sp.utility.database.DbPool;
import com.sp.utility.string.Date;
import com.sp.prpall.pubfun.PubTools;
import com.sp.prpall.pubfun.TransCodeCI;

import com.sp.sysframework.common.datatype.DateTime;
import com.sp.sysframework.reference.DBManager;
import com.sp.utiall.blsvr.BLPrpDagent;
import com.sp.utiall.blsvr.BLPrpDcompany;
import com.sp.utility.string.ChgDate;
import com.sp.prpall.blsvr.tb.BLPrpTmain;
import com.sp.prpall.schema.PrpIntefInfoSchema;
import com.sp.utility.string.ChgDate;
import com.sp.prpall.pubfun.PubTools;
import com.sp.prpall.pubfun.TransCodeCI;


/**
 * 询报价报文解析
 *
 */

public class TBQuotationTMXMLDecoder {
	
	/**
	 * 将询价报文转换为XX单对象，支持同时转换交强XXXXXXX单对象和商业XXXXXXX单对象
	 * @param doc 询价报文
	 * @param paramMap 将一些特殊参数放到此map中，如XXXXX种、询价类型等
	 * @param BLProposalMap 将需要转换的XX单对象放到此map中，如交强XXXXX为map.get(QuotationUtils.BLPROPOSALCI);
	 * @throws Exception
	 */
	public static String request_ip;
	public void decode(Document doc, Map paramMap, Map BLProposalMap)throws Exception{
		Element root = doc.getRootElement();
		parseHead(root.element("HEAD"), paramMap, BLProposalMap);
		parseBody(root.element("BODY"), paramMap, BLProposalMap);
	}
	
	/**
	 * 处理HEAD节
	 * @param head
	 * @param paramMap
	 * @param BLProposalMap
	 * @throws Exception
	 */
	public void parseHead(Element head, Map paramMap, Map BLProposalMap)throws Exception{
		Element REQUESTTYPE = head.element("REQUESTTYPE");
		paramMap.put("requestType", REQUESTTYPE.getText());
		
		
	}
	
	/**
	 * 处理BODY节
	 * @param body
	 * @param paramMap
	 * @param BLProposalMap
	 * @throws Exception
	 */
	public void parseBody(Element body, Map paramMap, Map BLProposalMap)throws Exception{
		
		BLProposal blProposalCI = (BLProposal)BLProposalMap.get(QuotationUtils.BLPROPOSALCI);
		BLProposal blProposalBI = (BLProposal)BLProposalMap.get(QuotationUtils.BLPROPOSALBI);
		
		
		
		String InfoTransNo = body.elementText("InfoTransNo");
		
		String RiskCodeBI = body.elementText("RiskcodeBI");
		
		paramMap.put("RiskCodeBI",RiskCodeBI);
		String OperateSiteBC = body.elementText("OperateSite");
		paramMap.put("OperateSiteBC",OperateSiteBC);
		
		String quotationType = body.elementText("QUOTATIONTYPE");
		String makeCom = body.elementText("MAKECOM");
		String comCode = body.elementText("COMCODE");
		
		paramMap.put("COMCODE", comCode);
		
		
		String SERVICEAREA = body.elementText("SERVICEAREA");
		String PROSPECTNO = body.elementText("PROSPECTNO");
		paramMap.put("PROSPECTNO",PROSPECTNO);
		String handlerCode = body.elementText("HANDLERCODE");
		
		String handler1Code = body.elementText("HANDLER1CODE");
		
		
		String opreratorCode = body.elementText("OPERATORCODE");
		
		String businessNature = body.elementText("BUSINESSNATURE");
		String agentCode = body.elementText("AGENTCODE");
		String agentName = body.elementText("AGENTNAME");
		String usbKey = body.elementText("USBKEY");
		

		String lasyPolicyNoCI = body.elementText("LASYPOLICYNOCI");
		String lasyPolicyNoBI = body.elementText("LASYPOLICYNOBI");

		String contractNo = body.elementText("CONTRACTNO");
		String groupCode = body.elementText("GROUPCODE");
		String OPERATEDATECI = body.elementText("OPERATEDATECI");
		OPERATEDATECI = new ChgDate().getCurrentTime("yyyy-MM-dd");
		String STARTDATECI = body.elementText("STARTDATECI");
		
		paramMap.put("STARTDATECI",STARTDATECI);
		
		String STARTHOURCI = body.elementText("STARTHOURCI");
		String ENDDATECI = body.elementText("ENDDATECI");
		
		String ENDHOURCI = body.elementText("ENDHOURCI");
		if(STARTHOURCI==null||"".equals(STARTHOURCI)){
			STARTHOURCI="00";
		}
		if(ENDHOURCI==null||"".equals(ENDHOURCI)){
			ENDHOURCI="24";
		}
		
		String OPERATEDATEBI = body.elementText("OPERATEDATEBI");
		String STARTDATEBI = body.elementText("STARTDATEBI");
		
		paramMap.put("STARTDATEBI",STARTDATEBI);
		
		String STARTHOURBI = body.elementText("STARTHOURBI");
		String ENDDATEBI = body.elementText("ENDDATEBI");
		String ENDHOURBI = body.elementText("ENDHOURBI");
		if(STARTHOURBI==null||"".equals(STARTHOURBI)){
			STARTHOURBI="00";
		}
		if(ENDHOURBI==null||"".equals(ENDHOURBI)){
			ENDHOURBI="24";
		}

		
		
		String demandNoCI = body.elementText("DEMANDNOCI");
		String demandNoBI = body.elementText("DEMANDNOBI");
		
		
		String cipolicyNo = body.elementText("CIPOLICYNO");
		String cicompanyCode = body.elementText("CICOMPANYCODE");
		
		
		
		String adjustRateOfmainCI=body.elementText("ADJUSTRATEOFMAINCI");
		String adjustPremiumOfmainCI=body.elementText("ADJUSTPREMIUMOFMAINCI");
		String adjustRateOfmainBI=body.elementText("ADJUSTRATEOFMAINBI");
		String adjustPremiumOfmainBI=body.elementText("ADJUSTPREMIUMOFMAINBI");
		
		String bizFlag = body.elementText("BIZFLAG");
		String computerIp = body.elementText("COMPUTERIP");
		String checkCode = body.elementText("CHECKCODE");
		
		
		String channelType = body.elementText("CHANNELTYPE");

		
		
		
		
		String LifeTableDXBom = body.elementText("LIFETABLEDXBOM");
		

		
		String CCSITestFlag = body.elementText("CCSITESTFLAG");
		paramMap.put("CCSITESTFLAG",CCSITestFlag);
		
		
		
		String OperateSite = "";
		 OperateSite = OperateSiteBC;
		
		String ImmeValidFlagCI = "";
		String ImmeValidFlagBI = "";
		String ImmeValidStartDateCI = "";
		String ImmeValidStartDateBI = "";
		String ImmeValidEndDateCI = "";
		String ImmeValidEndDateBI = "";
		
		ImmeValidFlagCI = body.elementText("IMMEVALIDFLAGJ");
		ImmeValidStartDateCI = body.elementText("IMMEVALIDSTARTDATEJ");
		ImmeValidEndDateCI = body.elementText("IMMEVALIDENDDATEJ");
		ImmeValidFlagBI = body.elementText("IMMEVALIDFLAGS");
		ImmeValidStartDateBI = body.elementText("IMMEVALIDSTARTDATES");
		ImmeValidEndDateBI = body.elementText("IMMEVALIDENDDATES");
		
			
		
		

		String answerCI = body.elementText("ANSWERCI");

		String answerBI = body.elementText("ANSWERBI");
		String ATMAnswer = body.elementText("ATMANSWER");
		paramMap.put(QuotationUtils.ATM_ANSWER, ATMAnswer);
		
		String SPECIALISTRATE = body.elementText("SPECIALISTRATE");
		paramMap.put("SPECIALISTRATE",SPECIALISTRATE);
		
		String SUMSUBPREM = body.elementText("SUMSUBPREM");
		paramMap.put("SUMSUBPREM",SUMSUBPREM);
		
		String PROFITMARGIN = body.elementText("PROFITMARGIN");
		paramMap.put("PROFITMARGIN",PROFITMARGIN);
		
		String RISKPREMIUM = body.elementText("RISKPREMIUM");
		paramMap.put("RISKPREMIUM",RISKPREMIUM);
		
		
		String SUPERVISEPREMIUMCI=body.elementText("SUPERVISEPREMIUMCI");
		String SUPERVISEPREMIUMBI=body.elementText("SUPERVISEPREMIUMBI");
		String ACTUALUNDWRTFACTORSCI=body.elementText("ACTUALUNDWRTFACTORSCI");
		String ACTUALUNDWRTFACTORSBI=body.elementText("ACTUALUNDWRTFACTORSBI");
		String AGREEMENTDECLAREBI=body.elementText("AGREEMENTDECLAREBI");
		String AGREEMENTDECLARECI=body.elementText("AGREEMENTDECLARECI");
		String ISMOTORCADEFLAG=body.elementText("ISMOTORCADEFLAG");
		paramMap.put("ISMOTORCADEFLAG", ISMOTORCADEFLAG);
		String LASTYEARENDDATECI=body.elementText("LASTYEARENDDATECI");
		paramMap.put("LASTYEARENDDATECI", LASTYEARENDDATECI);
		String LASTYEARENDDATEBI=body.elementText("LASTYEARENDDATEBI");
		paramMap.put("LASTYEARENDDATEBI", LASTYEARENDDATEBI);
		
		 
		String strAppointAreaCodeBI=body.elementText("APPOINTAREACODEBI");
		String strAppointAreaCodeCI=body.elementText("APPOINTAREACODECI");
		
		
		parseCar(body.element("CAR_LIST"), paramMap, BLProposalMap);
		

		
		
		if(body.element("CARDRIVER_LIST")!=null){
			parseDriver(body.element("CARDRIVER_LIST"), paramMap, BLProposalMap);
		}
		
		
		
		
		parsePrpIntefInfo(body.element("PRPINTEFINFO_LIST"), paramMap, BLProposalMap);
		

		parseDevice(body.element("DEVICE_LIST"), paramMap, BLProposalMap);
		
		parseCarTax(body.element("CARTAX_LIST"), paramMap, BLProposalMap, comCode);
		
		parseCarTaxTJ(body.element("CARTAXTJ_LIST"),body.element("CAR_LIST"), paramMap, BLProposalMap);
		
		parseTrafficDetail(body.element("TRAFFICDETAIL_LIST"), paramMap, BLProposalMap);
		
		parseAppli(body.element("APPLI_LIST"), paramMap, BLProposalMap);
		
		parseInsured(body.element("INSURED_LIST"), paramMap, BLProposalMap);
		
		List kindLists = body.elements("KIND_LIST");
		Iterator iterator = kindLists.iterator();
		while(iterator.hasNext()){
			Element kindList = (Element)iterator.next();
			if("0507".equals(kindList.attributeValue("RISKCODE")) && blProposalCI!=null){
			
			
				parseKind(kindList, paramMap, blProposalCI);
			

			
			

			



			}else{
				parseKind(kindList, paramMap, blProposalBI);
			}
			
			
		}
		
		List ProfitDetailLists = body.elements("PROFITDETAIL_LIST");
		Iterator iterator1 = ProfitDetailLists.iterator();
		while(iterator1.hasNext()){
			Element profitDetailList = (Element)iterator1.next();
			if("0507".equals(profitDetailList.attributeValue("RISKCODE")) && blProposalCI!=null){
				parseProfitDetail(profitDetailList, paramMap, blProposalCI);
			






			}else{
				parseProfitDetail(profitDetailList, paramMap, blProposalBI);
			}
			
			
		}		
		
		
		
		
		paramMap.put(QuotationUtils.QUTATIONTYPE, quotationType);
		
		
		paramMap.put("InfoTransNo",InfoTransNo);
		
		
		if(blProposalCI != null){
			PrpTmainExtrasSchema PrpTmainExtras = new PrpTmainExtrasSchema();
			
			PrpTmainExtras.setRiskCode("0507");
			
			blProposalCI.getBLPrpTmainExtras().setArr(PrpTmainExtras);
			blProposalCI.getBLPrpTmainExtras().getArr(0).setNewFlag("1");
			
			blProposalCI.getBLPrpTmainExtras().getArr(0).setActualAutoUndwrtFactor(ACTUALUNDWRTFACTORSCI);
			blProposalCI.getBLPrpTmainExtras().getArr(0).setRegulatoryPremium(SUPERVISEPREMIUMCI);
			
			
			blProposalCI.getBLPrpTmainExtras().getArr(0).setAppointAreaCode(strAppointAreaCodeCI!=null?strAppointAreaCodeCI:"");
			
			
			blProposalCI.getBLPrpTmain().getArr(0).setInfoTransNo(InfoTransNo);
			
			blProposalCI.getBLPrpTmain().getArr(0).setServiceArea(SERVICEAREA);
			
			blProposalCI.getBLPrpTmain().getArr(0).setClassCode("05");
			
			blProposalCI.getBLPrpTmain().getArr(0).setMakeCom(makeCom);
			blProposalCI.getBLPrpTmain().getArr(0).setComCode(comCode);
			
			blProposalCI.getBLPrpTmain().getArr(0).setLifeTableDXBom(LifeTableDXBom);
			

			
			BLPrpDcompany blPrpDcompany = new BLPrpDcompany();
			blPrpDcompany.query("comCode='"+comCode+"'");
			
			if(channelType.equals("")){
				channelType = blPrpDcompany.getArr(0).getChannelType();
			}
			

			
			blProposalCI.getBLPrpTmain().getArr(0).setChannelType(channelType);
			blProposalCI.getBLPrpTmain().getArr(0).setHandlerCode(handlerCode);
			
			blProposalCI.getBLPrpTmain().getArr(0).setHandler1Code(handler1Code);
			
			blProposalCI.getBLPrpTmain().getArr(0).setBusinessNature(businessNature);
			blProposalCI.getBLPrpTmain().getArr(0).setAgentCode(agentCode);
		    
			DBManager dbManager = new DBManager();
			try{
				BLPrpDagent blPrpDagent = new BLPrpDagent();				
				dbManager.open("ddccDataSource");
				dbManager.beginTransaction();
				blPrpDagent.query("agentCode='"+agentCode+"'");
				if(blPrpDagent.getSize()>0){

					String strDateTimes = new DateTime().current().toString().substring(0, 10);
					String strRiskCode = blProposalCI.getBLPrpTmain().getArr(0).getRiskCode();
					String srtAgreementNo="";
					Collection  collection = new BLPrpDagreeDetailFacade().getAgentAgreementsByRiskCode(agentCode,comCode,strDateTimes,strRiskCode);
					if(collection.size()==0){
					   throw new Exception("未查到相关协议");
					}else{
					    Iterator i=collection.iterator();
					    srtAgreementNo = (String)i.next();					
					   
					    blProposalCI.getBLPrpTmain().getArr(0).setAgreementNo(srtAgreementNo);
					}
				}
				else{
				}
				
		    }catch(Exception e){
		    	dbManager.rollbackTransaction();
		    	e.printStackTrace();
				throw e;
		    }
			finally
			{
				if(dbManager != null)
					dbManager.close();
			}

			blProposalCI.getBLPrpTmain().getArr(0).setUsbKey(usbKey);
			blProposalCI.getBLPrpTmain().getArr(0).setSendLastPolicyNo(lasyPolicyNoCI);








			blProposalCI.getBLPrpTmain().getArr(0).setContractNo(contractNo);
			
			blProposalCI.getBLPrpTmain().getArr(0).setOperateDate(OPERATEDATECI);
			
			blProposalCI.getBLPrpTitemCar().getArr(0).setMakeDate(OPERATEDATECI);			
			blProposalCI.getBLPrpTmain().getArr(0).setStartDate(STARTDATECI);
			blProposalCI.getBLPrpTmain().getArr(0).setStartHour(STARTHOURCI);
			blProposalCI.getBLPrpTmain().getArr(0).setEndDate(ENDDATECI);
			blProposalCI.getBLPrpTmain().getArr(0).setEndHour(ENDHOURCI);
			
			blProposalCI.getBLPrpTmain().getArr(0).setOperatorCode(opreratorCode);
			
			
			blProposalCI.getBLPrpTmain().getArr(0).setIP(computerIp);
			
			
			if(null!=demandNoCI&&!"".equals(demandNoCI)){
				
				if(null!=answerCI&&!"".equals(answerCI)){
					blProposalCI.getBLPrpTmain().getArr(0).setDemandNo(demandNoCI);
					blProposalCI.getBLPrpTmain().getArr(0).setAnswer(answerCI);
				}
				else{
					CIInsureDemandSchema ciInsureDemandSchemaCI=new CIInsureDemandSchema();
					ciInsureDemandSchemaCI.setDemandNo(demandNoCI);
					blProposalCI.getBLCIInsureDemand().setArr(ciInsureDemandSchemaCI);
				}
			}		
			
			
			blProposalCI.getBLPrpTmain().getArr(0).setAdjustRate(adjustRateOfmainCI);
			blProposalCI.getBLPrpTmain().getArr(0).setAdjustPremium(adjustPremiumOfmainCI);
			
			
			blProposalCI.getBLPrpTmain().getArr(0).setImmeValidFlag(ImmeValidFlagCI);
			blProposalCI.getBLPrpTmain().getArr(0).setImmeValidStartDate(ImmeValidStartDateCI);
			blProposalCI.getBLPrpTmain().getArr(0).setImmeValidEndDate(ImmeValidEndDateCI);
			blProposalCI.getBLPrpTmain().getArr(0).setOperateSite(OperateSite);
			
			String Prospect_No  = (String )paramMap.get("PROSPECTNO");
			if(Prospect_No != null && !Prospect_No.equals("")){
				PrpTexpenseSchema prpTexpenseSchema = new PrpTexpenseSchema();
				prpTexpenseSchema.setProspectNo(PROSPECTNO);
				blProposalCI.getBLPrpTexpense().setArr(prpTexpenseSchema);
			}
			
			
			
			
			blProposalCI.getBLPrpTmain().getArr(0).setCCSITestFlag(CCSITestFlag);
			
		}
		
		if(blProposalBI != null){
			PrpTmainExtrasSchema PrpTmainExtras = new PrpTmainExtrasSchema();
			blProposalBI.getBLPrpTmainExtras().setArr(PrpTmainExtras);
			blProposalBI.getBLPrpTmainExtras().getArr(0).setNewFlag("1");
			
			PrpTmainExtras.setRiskCode(RiskCodeBI);
			blProposalBI.getBLPrpTmainExtras().getArr(0).setActualAutoUndwrtFactor(ACTUALUNDWRTFACTORSBI);
			blProposalBI.getBLPrpTmainExtras().getArr(0).setRegulatoryPremium(SUPERVISEPREMIUMBI);
			
			
			blProposalBI.getBLPrpTmainExtras().getArr(0).setAppointAreaCode(strAppointAreaCodeBI!=null?strAppointAreaCodeBI:"");
			
			
			blProposalBI.getBLPrpTmain().getArr(0).setInfoTransNo(InfoTransNo);
			
			
			blProposalBI.getBLPrpTmain().getArr(0).setClassCode("05");
			
			blProposalBI.getBLPrpTmain().getArr(0).setServiceArea(SERVICEAREA);
			blProposalBI.getBLPrpTmain().getArr(0).setMakeCom(makeCom);
			blProposalBI.getBLPrpTmain().getArr(0).setComCode(comCode);
			
			blProposalBI.getBLPrpTmain().getArr(0).setLifeTableDXBom(LifeTableDXBom);
			
			
			BLPrpDcompany blPrpDcompany = new BLPrpDcompany();
			blPrpDcompany.query("comCode='"+comCode+"'");
			
			
			if(channelType.equals("")){
				channelType = blPrpDcompany.getArr(0).getChannelType();
			}
			
			
			blProposalBI.getBLPrpTmain().getArr(0).setChannelType(channelType);
			blProposalBI.getBLPrpTmain().getArr(0).setHandlerCode(handlerCode);
			blProposalBI.getBLPrpTmain().getArr(0).setHandler1Code(handler1Code);
			blProposalBI.getBLPrpTmain().getArr(0).setBusinessNature(businessNature);
			blProposalBI.getBLPrpTmain().getArr(0).setAgentCode(agentCode);
			
			DBManager dbManager = new DBManager();
			try{
				BLPrpDagent blPrpDagent = new BLPrpDagent();				
				dbManager.open("ddccDataSource");
				dbManager.beginTransaction();
				blPrpDagent.query("agentCode='"+agentCode+"'");
				if(blPrpDagent.getSize()>0){
					String strDateTimes = new DateTime().current().toString().substring(0, 10);
					String strRiskCode = blProposalBI.getBLPrpTmain().getArr(0).getRiskCode();
					String srtAgreementNo="";
					Collection  collection = new BLPrpDagreeDetailFacade().getAgentAgreementsByRiskCode(agentCode,comCode,strDateTimes,strRiskCode);
					if(collection.size()==0){
						   throw new Exception("未查到相关协议");
				    }else{
					    Iterator i=collection.iterator();
						srtAgreementNo = (String)i.next();
					   
					    blProposalBI.getBLPrpTmain().getArr(0).setAgreementNo(srtAgreementNo);
				    }
					
				}
				else{
				}
				
				
		    }catch(Exception e){
		    	dbManager.rollbackTransaction();
		    	e.printStackTrace();
				throw e;
		    }
			finally
			{
				if(dbManager != null)
					dbManager.close();
			}

			blProposalBI.getBLPrpTmain().getArr(0).setUsbKey(usbKey);
			blProposalBI.getBLPrpTmain().getArr(0).setSendLastPolicyNo(lasyPolicyNoBI);







			blProposalBI.getBLPrpTmain().getArr(0).setContractNo(contractNo);
			
			blProposalBI.getBLPrpTmain().getArr(0).setOperateDate(OPERATEDATEBI);
			
			blProposalBI.getBLPrpTmain().getArr(0).setStartDate(STARTDATEBI);
			blProposalBI.getBLPrpTmain().getArr(0).setStartHour(STARTHOURBI);
			blProposalBI.getBLPrpTmain().getArr(0).setEndDate(ENDDATEBI);
			blProposalBI.getBLPrpTmain().getArr(0).setEndHour(ENDHOURBI);
			
			
			blProposalBI.getBLPrpTmain().getArr(0).setOperatorCode(opreratorCode);
			
			

			blProposalBI.getBLPrpTmain().getArr(0).setIP(computerIp);
			
			
			if(null!=demandNoBI&&!"".equals(demandNoBI)){
				
				if(null!=answerBI&&!"".equals(answerBI)){
						blProposalBI.getBLPrpTmain().getArr(0).setDemandNo(demandNoBI);
						blProposalBI.getBLPrpTmain().getArr(0).setAnswer(answerBI);
				}
				else{
				
					CIInsureDemandSchema ciInsureDemandSchemaBI=new CIInsureDemandSchema();
					ciInsureDemandSchemaBI.setDemandNo(demandNoBI);
					blProposalBI.getBLCIInsureDemand().setArr(ciInsureDemandSchemaBI);
				}
			}
			
			
			paramMap.put("cipolicyNo", cipolicyNo);
			paramMap.put("cicompanyCode", cicompanyCode);
			
			
			
			blProposalBI.getBLPrpTmain().getArr(0).setAdjustRate(adjustRateOfmainBI);
			blProposalBI.getBLPrpTmain().getArr(0).setAdjustPremium(adjustPremiumOfmainBI);
			
			
			
			blProposalBI.getBLPrpTmain().getArr(0).setImmeValidFlag(ImmeValidFlagBI);
			blProposalBI.getBLPrpTmain().getArr(0).setImmeValidStartDate(ImmeValidStartDateBI);
			blProposalBI.getBLPrpTmain().getArr(0).setImmeValidEndDate(ImmeValidEndDateBI);
			blProposalBI.getBLPrpTmain().getArr(0).setOperateSite(OperateSite);
			
			String Prospect_No  = (String )paramMap.get("PROSPECTNO");
			if(Prospect_No != null && !Prospect_No.equals("")){
				PrpTexpenseSchema prpTexpenseSchema = new PrpTexpenseSchema();
				prpTexpenseSchema.setProspectNo(PROSPECTNO);
				prpTexpenseSchema.setRiskCode(RiskCodeBI);
				blProposalBI.getBLPrpTexpense().setArr(prpTexpenseSchema);
			}
			
			
			blProposalBI.getBLPrpTmain().getArr(0).setCCSITestFlag(CCSITestFlag);
			
		}
	}
	
	private void parsePrpIntefInfo(Element element, Map paramMap,
			Map bLProposalMap) throws Exception {
		
		BLProposal blProposalCI = (BLProposal)bLProposalMap.get(QuotationUtils.BLPROPOSALCI);
		BLProposal blProposalBI = (BLProposal)bLProposalMap.get(QuotationUtils.BLPROPOSALBI);
		Iterator iterator = element.elementIterator();
		while(iterator.hasNext()){
			Element PRPINTEFINFO_DATA = (Element)iterator.next();
			String LASTCLAIMCOUNTBI = PRPINTEFINFO_DATA.elementText("LASTCLAIMCOUNTBI");
			paramMap.put("LASTCLAIMCOUNTBI", LASTCLAIMCOUNTBI);
			
			String LASTCLAIMCOUNTCI = PRPINTEFINFO_DATA.elementText("LASTCLAIMCOUNTCI");
			paramMap.put("LASTCLAIMCOUNTCI", LASTCLAIMCOUNTCI);
			
			String LASTPAYSUMCI = PRPINTEFINFO_DATA.elementText("LASTPAYSUMCI");
			paramMap.put("LASTPAYSUMCI", LASTPAYSUMCI);
			
			String LASTPAYSUMBI = PRPINTEFINFO_DATA.elementText("LASTPAYSUMBI");
			paramMap.put("LASTPAYSUMBI", LASTPAYSUMBI);
			

		}
	}

	/**
	 * 处理CAR_LIST节
	 * @param carList
	 * @param paramMap
	 * @param BLProposalMap
	 * @throws Exception
	 */
	public void parseCar(Element carList, Map paramMap, Map BLProposalMap)throws Exception{
		
		BLProposal blProposalCI = (BLProposal)BLProposalMap.get(QuotationUtils.BLPROPOSALCI);
		BLProposal blProposalBI = (BLProposal)BLProposalMap.get(QuotationUtils.BLPROPOSALBI);
		
		Iterator iterator = carList.elementIterator();
		while(iterator.hasNext()){
			
			PrpTitemCarSchema prpTitemCarSchema = new PrpTitemCarSchema();
			PrpTitemCarExtSchema prpTitemCarExtSchema = new PrpTitemCarExtSchema();

			Element carData = (Element)iterator.next();
			String OperateSiteBC =  (String)paramMap.get("OperateSiteBC");
			
			if("".equals(carData.elementText("LICENSENO"))){
				throw new Exception("车牌号不能为空，如果是新车请将车牌号写为“新车”！");
			}
			prpTitemCarSchema.setLicenseNo(carData.elementText("LICENSENO"));

			prpTitemCarExtSchema.setVehicleCode(carData.elementText("VEHICLECODE"));
			prpTitemCarSchema.setHKLicenseNo(carData.elementText("HKLICENSENO"));
			
			if (carData.elementText("HKLICENSENO")==null||carData.elementText("HKLICENSENO").trim().equals("")) {
				prpTitemCarSchema.setRateCode("20");
			}else{
				prpTitemCarSchema.setRateCode("25");
			}
			prpTitemCarSchema.setLicenseColorCode(carData.elementText("LICENSECOLORCODE"));
			prpTitemCarSchema.setLicenseKindCode(carData.elementText("LICENSETYPE"));
			prpTitemCarSchema.setMainCarKindCode(carData.elementText("MAINCARKINDCODE"));
			prpTitemCarSchema.setCarKindCode(carData.elementText("CARKINDCODE"));
			prpTitemCarSchema.setUseNatureCode(carData.elementText("USENATURECODE"));
			prpTitemCarSchema.setColorCode(carData.elementText("COLORCODE"));
			prpTitemCarSchema.setCountryNature(carData.elementText("COUNTRYNATURE"));
		
			prpTitemCarSchema.setBrandName(carData.elementText("BRANDNAME"));
			prpTitemCarSchema.setModelCode(carData.elementText("MODELCODE"));
			prpTitemCarSchema.setSeatCount(carData.elementText("SEATCOUNT"));
			prpTitemCarSchema.setPurchasePrice(carData
						.elementText("PURCHASEPRICEMODI"));
			prpTitemCarSchema.setPurchasePriceOrigin(carData.elementText("PURCHASEPRICE"));
			
			String strPurchasePriceLB =  carData.elementText("PURCHASEPRICELB");
			prpTitemCarSchema.setPurchasePriceLB(strPurchasePriceLB!=null?strPurchasePriceLB:"");
			
			
			String dbEXHAUSTSCALE = carData.elementText("POWERSCALE");
			if(dbEXHAUSTSCALE==null || "".equals(dbEXHAUSTSCALE)){
				dbEXHAUSTSCALE = "0.00";
			}
			double EXHAUSTSCALE = Double.parseDouble(dbEXHAUSTSCALE);
			

			prpTitemCarSchema.setExhaustScale(EXHAUSTSCALE+"");
			prpTitemCarSchema.setTonCount(carData.elementText("TONCOUNT"));
			prpTitemCarSchema.setFrameNo(carData.elementText("FRAMENO"));
			prpTitemCarSchema.setEngineNo(carData.elementText("ENGINENO"));
			prpTitemCarSchema.setVINNo(carData.elementText("VINNO"));
			prpTitemCarSchema.setEnrollDate(carData.elementText("ENROLLDATE").trim());
			prpTitemCarSchema.setUseYears(carData.elementText("USEYEARS"));
			int iCompleteKerbMass = 0;
			if(carData.elementText("COMPLETEKERBMASS")!=null&&!"".equals(carData.elementText("COMPLETEKERBMASS"))){
				iCompleteKerbMass =(int)Double.parseDouble(carData.elementText("COMPLETEKERBMASS").trim());
			}
			prpTitemCarSchema.setCompleteKerbMass(String.valueOf(iCompleteKerbMass));
			prpTitemCarSchema.setRunAreaCode(carData.elementText("RUNAREACODE"));
			
			prpTitemCarSchema.setOtherNature(carData.elementText("OTHERNATURE"));
			

			paramMap.put("ESPECIALLYCARTYPE",carData.elementText("ESPECIALLYCARTYPE"));
			paramMap.put("RENEWALCIFLAG", carData.elementText("RENEWALCIFLAG"));
			paramMap.put("RENEWALBIFLAG", carData.elementText("RENEWALBIFLAG"));
			
			String runMiles = carData.elementText("RUNMILESAVERAGE");
			if(Double.parseDouble(runMiles)<30000){
			    prpTitemCarSchema.setRunMiles("20000");
			}else if(Double.parseDouble(runMiles)>=50000){
				prpTitemCarSchema.setRunMiles("60000");
			}else if(Double.parseDouble(runMiles)>=30000&&Double.parseDouble(runMiles)<50000){
				prpTitemCarSchema.setRunMiles("40000");
			}
			
			prpTitemCarSchema.setSafeDevice(carData.elementText("SAFEDEVICE"));
			prpTitemCarExtSchema.setDamagedFactorGrade(carData.elementText("DAMAGEDFACTORGRADE"));
			prpTitemCarSchema.setAddonCount(carData.elementText("ADDONCOUNT"));
			prpTitemCarSchema.setCarLoanFlag(carData.elementText("EVERPOLICY"));
			
			prpTitemCarSchema.setMadeFactory(carData.elementText("MADEFACTORY"));
			prpTitemCarSchema.setMakeDate(carData.elementText("MAKEDATE").trim());
			prpTitemCarSchema.setLicenseCategory(carData.elementText("LICENSECATEGORY"));
			








			prpTitemCarSchema.setNoLicenseFlag(carData.elementText("NOLICENSEFLAG"));
			prpTitemCarExtSchema.setInDoorFlag(carData.elementText("INDOORFLAG"));
			prpTitemCarSchema.setChgOwnerFlag(carData.elementText("CHGOWNERFLAG"));
			prpTitemCarSchema.setChgOwnerDate(carData.elementText("TRANSFERDATE").trim());
			if("1".equals(prpTitemCarSchema.getChgOwnerFlag())&&"".equals(prpTitemCarSchema.getChgOwnerDate())){
				throw new Exception("过户车时，转移登记日期不能为空");
			}
			prpTitemCarSchema.setLoanVehicleFlag(carData.elementText("LOANVEHICLEFLAG"));
			prpTitemCarExtSchema.setNoDamageYears(carData.elementText("NODAMAGEYEARS"));
			
			prpTitemCarSchema.setRestricFlag(carData.elementText("RESTRICFLAG"));
			prpTitemCarSchema.setClauseType(carData.elementText("CLAUSETYPE"));
			
			
			String Actual_Value = carData.elementText("ACTUALVALUE");
			if(Actual_Value != null){
				prpTitemCarSchema.setActualValue(Actual_Value);
			}
			
	        
			
			String carCheckStatus = carData.elementText("CARCHECKSTATUS");
			if(carCheckStatus != null){
				prpTitemCarSchema.setCarCheckStatus(carCheckStatus);
			}
			

			
			String RelievingAreaCode = carData.elementText("RELIEVINGAREACODE");
			if(RelievingAreaCode != null){
				prpTitemCarSchema.setRelievingAreaCode(RelievingAreaCode);
			}
			
			
			
			String CarOwnerName = carData.elementText("CAROWNERNAME");
			if(CarOwnerName != null){
				prpTitemCarSchema.setCarOwner(CarOwnerName);
			}
			
			
			
			String CarInsuredRealation = carData.elementText("CARINSUREDRELATION");
			if(CarInsuredRealation != null){
				prpTitemCarSchema.setCarInsuredRelation(CarInsuredRealation);
			}
			
			
			
			String Carownernature = carData.elementText("CAROWNERNATURE");
			if(Carownernature != null){
				prpTitemCarSchema.setCarOwnerNature(Carownernature);
			}
			
			
			
			String CarOwnerIdentifyType = carData.elementText("CAROWNERIDENTIFYTYPE");
			String CarOwnerIdentifyNumber = carData.elementText("CAROWNERIDENTIFYNUMBER");
			if(CarOwnerIdentifyType != null){
				prpTitemCarSchema.setInsuredTypeCode(CarOwnerIdentifyType);
			}
			if(CarOwnerIdentifyNumber != null){
				prpTitemCarSchema.setOwnerAddress(CarOwnerIdentifyNumber);
			}
			
			
			
			prpTitemCarExtSchema.setFuelType(carData.elementText("FUELTYPE"));
			

			
			
			
			prpTitemCarExtSchema.setBuyCarDate(carData.elementText("ENROLLDATE").trim());  
			
			

			prpTitemCarExtSchema.setNoneFluctuateFlag(carData.elementText("NEWDEVICEFLAG"));
			
			
			prpTitemCarExtSchema.setNoneFluctuateFlag(carData.elementText("BRANDECODE"));
			
			if(carData.elementText("BRANDECODE") == null){
				prpTitemCarExtSchema.setNoneFluctuateFlag("");
			}else{
				
				prpTitemCarExtSchema.setNoneFluctuateFlag(carData.elementText("BRANDECODE"));
			}
			
			
			
			if(carData.elementText("NONEFLUCTUATEFLAG") != null && !carData.elementText("NONEFLUCTUATEFLAG").equals("") ){
				prpTitemCarExtSchema.setNoneFluctuateFlag(carData.elementText("NONEFLUCTUATEFLAG"));
				prpTitemCarSchema.setNewDeviceFlag(prpTitemCarExtSchema.getNoneFluctuateFlag());

			}
			

			
			
			prpTitemCarExtSchema.setBrandECode(carData.elementText("BRANDECODE"));
			
			
			prpTitemCarExtSchema.setIsEnergyConservation(carData.elementText("ISENERGYCONSERVATION"));
			
			
			 
			prpTitemCarExtSchema.setRepeatFrameNo(carData.elementText("REPEATFRAMENO"));
			 
			
			
			prpTitemCarExtSchema.setBaseRiskCost(carData.elementText("BASERISKCOST")!=null?carData.elementText("BASERISKCOST"):"0.0");
			
			
			if(blProposalCI!=null){
				String strNewCarFlag = "";
				String strEcdemicVehicleFlag = "";
					PubTools pubtools = new PubTools();
					String licenseNo = carData.elementText("LICENSENO");
					
					
					if("".equals(licenseNo.trim()) || SysConfig.getProperty("NewLicenseNoFlag").indexOf(","+licenseNo.trim()+",") > -1){
					
						strNewCarFlag = "1";
			        }else{
			        	strNewCarFlag = "0";
			        }
					
					String STARTDATECI = (String)paramMap.get("STARTDATECI");
					String comCode = (String)paramMap.get("COMCODE");
					if(STARTDATECI!=null && !"".equals(STARTDATECI)){
						Date strStartDate = pubtools.stringToDate((String)paramMap.get("STARTDATECI"));
						Date strEnrollDate = pubtools.stringToDate(carData.elementText("ENROLLDATE").trim());
						String currentDate=new ChgDate().getCurrentTime("yyyy-MM-dd");
						String loanVehicleFlag = carData.elementText("LOANVEHICLEFLAG");  
						if(new UtiPower().checkCarShipTaxJZ(comCode, currentDate) && "0".equals(loanVehicleFlag)){
							if(pubtools.getMonthMinus(strEnrollDate,0,strStartDate,0)>9){
								strNewCarFlag = "0";
							}else{
								strNewCarFlag = "1";
							}
						}
						
					}
					

				     strEcdemicVehicleFlag = TransCodeCI.getEcdemicVehicleFlag(comCode, licenseNo);
					prpTitemCarSchema.setNewCarFlag(strNewCarFlag);
					prpTitemCarSchema.setOutLandCarFlag(strEcdemicVehicleFlag);
				
				PrpTitemCarSchema prpTitemCarSchemaCI = new PrpTitemCarSchema();
				prpTitemCarSchemaCI.setSchema(prpTitemCarSchema);
				blProposalCI.getBLPrpTitemCar().setArr(prpTitemCarSchemaCI);
				
				PrpTitemCarExtSchema prpTitemCarExtSchemaCI = new PrpTitemCarExtSchema();
				prpTitemCarExtSchemaCI.setSchema(prpTitemCarExtSchema);
				blProposalCI.getBLPrpTitemCarExt().setArr(prpTitemCarExtSchemaCI);

				blProposalCI.getBLPrpTitemCar().getArr(0).setSearchSequenceNo(carData.elementText("SEARCHSEQUENCENO"));
				
				 
				String strRELIEVINGAREACODE = carData.elementText("RELIEVINGAREACODE");
			    blProposalCI.getBLPrpTitemCar().getArr(0).setRelievingAreaCode(strRELIEVINGAREACODE);
				
				

				
			
				
			}
			if(blProposalBI != null){
				String strNewCarFlag = "";
				String strEcdemicVehicleFlag = "";
					PubTools pubtools = new PubTools();
					String licenseNo = carData.elementText("LICENSENO");
					
					
					if("".equals(licenseNo.trim()) || SysConfig.getProperty("NewLicenseNoFlag").indexOf(","+licenseNo.trim()+",") > -1){
					
						strNewCarFlag = "1";
			        }else{
			        	strNewCarFlag = "0";
			        }
					
					String STARTDATEBI = (String)paramMap.get("STARTDATEBI");

					String comCode = (String)paramMap.get("COMCODE");
					if(STARTDATEBI!=null &&!"".equals(STARTDATEBI)){
						Date strStartDate = pubtools.stringToDate((String)paramMap.get("STARTDATEBI"));
						Date strEnrollDate = pubtools.stringToDate(carData.elementText("ENROLLDATE").trim());
						String currentDate=new ChgDate().getCurrentTime("yyyy-MM-dd");
						String loanVehicleFlag = carData.elementText("LOANVEHICLEFLAG");  
						if(new UtiPower().checkCarShipTaxJZ(comCode, currentDate) && "0".equals(loanVehicleFlag)){
							if(pubtools.getMonthMinus(strEnrollDate,0,strStartDate,0)>9){
								strNewCarFlag = "0";
							}else{
								strNewCarFlag = "1";
							}
						}
						
					}
						

				     strEcdemicVehicleFlag = TransCodeCI.getEcdemicVehicleFlag(comCode, licenseNo);
					prpTitemCarSchema.setNewCarFlag(strNewCarFlag);
					prpTitemCarSchema.setOutLandCarFlag(strEcdemicVehicleFlag);
				
				PrpTitemCarSchema prpTitemCarSchemaBI = new PrpTitemCarSchema();
				prpTitemCarSchemaBI.setSchema(prpTitemCarSchema);
				blProposalBI.getBLPrpTitemCar().setArr(prpTitemCarSchemaBI);
				
				PrpTitemCarExtSchema prpTitemCarExtSchemaBI = new PrpTitemCarExtSchema();
				prpTitemCarExtSchemaBI.setSchema(prpTitemCarExtSchema);
				blProposalBI.getBLPrpTitemCarExt().setArr(prpTitemCarExtSchemaBI);
				
				blProposalBI.getBLPrpTitemCar().getArr(0).setSearchSequenceNo(carData.elementText("SEARCHSEQUENCENO"));
	
				 
				blProposalBI.getBLPrpTitemCar().getArr(0).setRelievingAreaCode(carData.elementText("RELIEVINGAREACODE"));
				
				
			}
		}
	}
	
	/**
	 * 处理CAROWNER_LIST节
	 * @param carOwnerList
	 * @param paramMap
	 * @param BLProposalMap
	 * @throws Exception
	 */
	


































	
	
	/**
	 * 处理DRIVER_LIST节
	 * @param driverList
	 * @param paramMap
	 * @param BLProposalMap
	 * @throws Exception
	 */
	public void parseDriver(Element driverList, Map paramMap, Map BLProposalMap)throws Exception{
		
		
		BLProposal blProposalBI = (BLProposal)BLProposalMap.get(QuotationUtils.BLPROPOSALBI);
		
		Iterator iterator = driverList.elementIterator();
		int i = 0;
		while(iterator.hasNext()){		
			
			PrpTcarDriverSchema prpTcarDriverSchema = new PrpTcarDriverSchema();

			Element driverData = (Element)iterator.next();
			prpTcarDriverSchema.setDriverName(driverData.elementText("DRIVERNAME"));
			prpTcarDriverSchema.setSex(driverData.elementText("DRIVERSEX"));
			prpTcarDriverSchema.setAge(driverData.elementText("DRIVERAGE"));
			prpTcarDriverSchema.setDrivingCarType(driverData.elementText("DRIVINGCARTYPE"));
			
			
			prpTcarDriverSchema.setDrivingLicenseNo(driverData.elementText("LINCENSENO"));
			
			prpTcarDriverSchema.setIdentifyNumber(driverData.elementText("LINCENSENO"));
			prpTcarDriverSchema.setIdentifyType("05");
			prpTcarDriverSchema.setAcceptLicenseDate(driverData.elementText("ACCEPTLICENSEDATE").trim());
			prpTcarDriverSchema.setDrivingYears(driverData.elementText("DRIVINGYEARS"));
			prpTcarDriverSchema.setMarriage(driverData.elementText("MARRIAGEFLAG"));
			prpTcarDriverSchema.setAppliYearType(driverData.elementText("APPLIYEAR"));
			if(i==0){
				prpTcarDriverSchema.setChangelessFlag("1");
			}else{
				prpTcarDriverSchema.setChangelessFlag("0");
			}
			i++;
			prpTcarDriverSchema.setItemNo("1");
			prpTcarDriverSchema.setSerialNo(""+i);
			prpTcarDriverSchema.setRiskCode((String)paramMap.get("RiskCodeBI")); 
			





			
			if(blProposalBI!=null){
				PrpTcarDriverSchema prpTcarDriverSchemaBI = new PrpTcarDriverSchema();
				prpTcarDriverSchemaBI.setSchema(prpTcarDriverSchema);
				blProposalBI.getBLPrpTcarDriver().setArr(prpTcarDriverSchemaBI);	
				if(i==1){
					if(blProposalBI.getBLPrpTitemCar()!=null && blProposalBI.getBLPrpTitemCar().getSize()>0){
						blProposalBI.getBLPrpTitemCar().getArr(0).setAgreeDriverFlag("1");
					}
				}
			}
		}
	}
	
	
	/**
	 * 处理DEVICE_LIST节
	 * @param deviceList
	 * @param paramMap
	 * @param BLProposalMap
	 * @throws Exception
	 */
	public void parseDevice(Element deviceList, Map paramMap, Map BLProposalMap)throws Exception{
		
		BLProposal blProposalCI = (BLProposal)BLProposalMap.get(QuotationUtils.BLPROPOSALCI);
		BLProposal blProposalBI = (BLProposal)BLProposalMap.get(QuotationUtils.BLPROPOSALBI);
		
		Iterator iterator = deviceList.elementIterator();
		while(iterator.hasNext()){
			
			PrpTcarDeviceSchema prpTcarDeviceSchema = new PrpTcarDeviceSchema();

			Element deviceData = (Element)iterator.next();

			prpTcarDeviceSchema.setDeviceName(deviceData.elementText("DEVICENAME"));
			prpTcarDeviceSchema.setQuantity(deviceData.elementText("QUANTITY"));
			prpTcarDeviceSchema.setPurchasePrice(deviceData.elementText("PURCHASEPRICE"));
			prpTcarDeviceSchema.setBuyDate(deviceData.elementText("BUYDATE").trim());
			prpTcarDeviceSchema.setActualValue(deviceData.elementText("ACTUALVALUE"));
			prpTcarDeviceSchema.setRemark(deviceData.elementText("REMARK"));
			
			
			if(blProposalCI!=null){
				PrpTcarDeviceSchema prpTcarDeviceSchemaCI = new PrpTcarDeviceSchema();
				prpTcarDeviceSchemaCI.setSchema(prpTcarDeviceSchema);
				blProposalCI.getBLPrpTcarDevice().setArr(prpTcarDeviceSchemaCI);	
			}
			
			if(blProposalBI!=null){
				PrpTcarDeviceSchema prpTcarDeviceSchemaBI = new PrpTcarDeviceSchema();
				prpTcarDeviceSchemaBI.setSchema(prpTcarDeviceSchema);
				blProposalBI.getBLPrpTcarDevice().setArr(prpTcarDeviceSchemaBI);	
			}
			
		}
	}
	
	/**
	 * 处理CARTAX_LIST节
	 * @param carTaxList
	 * @param paramMap
	 * @param BLProposalMap
	 * @throws Exception
	 */
	public void parseCarTax(Element carTaxList, Map paramMap, Map BLProposalMap, String comCode)throws Exception{
		
		BLProposal blProposalCI = (BLProposal)BLProposalMap.get(QuotationUtils.BLPROPOSALCI);
		BLProposal blProposalBI = (BLProposal)BLProposalMap.get(QuotationUtils.BLPROPOSALBI);
		
		Iterator iterator = carTaxList.elementIterator();
		while(iterator.hasNext()){
			
			PrpTcarshipTaxSchema prpTcarshipTaxSchema = new PrpTcarshipTaxSchema();

			Element cartaxData = (Element)iterator.next();
			
			prpTcarshipTaxSchema.setAreaCode(cartaxData.elementText("AREACODE"));
			prpTcarshipTaxSchema.setTaxItemCode(cartaxData.elementText("TAXITEMCODE"));
			prpTcarshipTaxSchema.setTaxItemName(cartaxData.elementText("TAXITEMNAME"));
			prpTcarshipTaxSchema.setTaxItemDetailCode(cartaxData.elementText("TAXITEMDETAILCODE"));
			prpTcarshipTaxSchema.setTaxItemDetailName(cartaxData.elementText("TAXITEMDETAILNAME"));
			prpTcarshipTaxSchema.setBaseTaxation(cartaxData.elementText("BASETAXATION"));
			prpTcarshipTaxSchema.setTaxpayerCode(cartaxData.elementText("TAXPAYERCODE"));
			prpTcarshipTaxSchema.setTaxpayerName(cartaxData.elementText("TAXPAYERNAME"));
			prpTcarshipTaxSchema.setIdentifyNumber(cartaxData.elementText("IDENTIFYNUMBER"));
			prpTcarshipTaxSchema.setTaxpayerIdentifier(cartaxData.elementText("TAXPAYERIDENTIFIER"));
			if("07".equals(comCode.substring(0, 2))){
				prpTcarshipTaxSchema.setTaxpayerIdentifier(cartaxData.elementText("TAXPAYERCERTITYPE"));
			}
			prpTcarshipTaxSchema.setTaxRelifFlag(cartaxData.elementText("TAXRELIFFLAG"));
			prpTcarshipTaxSchema.setRelifReason(cartaxData.elementText("RELIFREASON"));
			prpTcarshipTaxSchema.setFreeRate(cartaxData.elementText("FREERATE"));
			prpTcarshipTaxSchema.setFreeRateText(cartaxData.elementText("FREERATETEXT"));
			prpTcarshipTaxSchema.setTaxComCode(cartaxData.elementText("TAXCOMCODE"));
			prpTcarshipTaxSchema.setTaxComName(cartaxData.elementText("TAXCOMNAME"));
			prpTcarshipTaxSchema.setPaidFreeCertificate(cartaxData.elementText("PAIDFREECERTIFICATE"));
			prpTcarshipTaxSchema.setTaxUnit(cartaxData.elementText("TAXUNIT"));
			prpTcarshipTaxSchema.setTaxUnitText(cartaxData.elementText("TAXUNITTEXT"));
			prpTcarshipTaxSchema.setCalculateMode(cartaxData.elementText("CALCULATEMODE"));
			prpTcarshipTaxSchema.setCalculateFlag(cartaxData.elementText("CALCULATEFLAG"));
			prpTcarshipTaxSchema.setPayLastYear(cartaxData.elementText("PAYLASTYEAR"));
			prpTcarshipTaxSchema.setHisPolicyEndDate(cartaxData.elementText("HISPOLICYENDDATE").trim());
			prpTcarshipTaxSchema.setTaxDue(cartaxData.elementText("TAXDUE"));
			prpTcarshipTaxSchema.setTaxActual(cartaxData.elementText("TAXACTUAL"));
			prpTcarshipTaxSchema.setTaxRelief(cartaxData.elementText("TAXRELIEF"));
			
			prpTcarshipTaxSchema.setPayStartDate(cartaxData.elementText("PAYSTARTDATE").trim());
			prpTcarshipTaxSchema.setPayEndDate(cartaxData.elementText("PAYENDDATE").trim());
			
			prpTcarshipTaxSchema.setPreviousPay(cartaxData.elementText("PREVIOUSPAY"));
			prpTcarshipTaxSchema.setLateFee(cartaxData.elementText("LATEFEE"));
			prpTcarshipTaxSchema.setLateFeeStartDate(cartaxData.elementText("LATEFEESTARTDATE").trim());
			prpTcarshipTaxSchema.setLateFeeEndDate(cartaxData.elementText("LATEFEEENDDATE").trim());
			prpTcarshipTaxSchema.setSumPayTax(cartaxData.elementText("SUMPAYTAX"));
			prpTcarshipTaxSchema.setLeviedDate(cartaxData.elementText("LEVIEDDATE").trim());
			prpTcarshipTaxSchema.setPayTaxTimes(cartaxData.elementText("PAYTAXTIMES"));
			prpTcarshipTaxSchema.setFinalFlag(cartaxData.elementText("FINALFLAG"));
			prpTcarshipTaxSchema.setExtendChar1(cartaxData.elementText("EXTENDCHAR1"));
			prpTcarshipTaxSchema.setExtendChar2(cartaxData.elementText("EXTENDCHAR2"));
			prpTcarshipTaxSchema.setExtendNum1(cartaxData.elementText("EXTENDNUM1"));
			prpTcarshipTaxSchema.setExtendNum2(cartaxData.elementText("EXTENDNUM2"));
			prpTcarshipTaxSchema.setExtendDate1(cartaxData.elementText("EXTENDDATE1"));
			prpTcarshipTaxSchema.setExtendDate2(cartaxData.elementText("EXTENDDATE2"));
			prpTcarshipTaxSchema.setFlag(cartaxData.elementText("FLAG"));
			prpTcarshipTaxSchema.setPayBalanceFee(cartaxData.elementText("PAYBALANCEFEE"));
			prpTcarshipTaxSchema.setCalFeeFlag(cartaxData.elementText("CALFEEFLAG"));
			prpTcarshipTaxSchema.setCertificateDate(cartaxData.elementText("CERTIFICATEDATE").trim());
			prpTcarshipTaxSchema.setTaxFlag(cartaxData.elementText("TAXFLAG"));
			
			
			if(blProposalCI!=null){
				PrpTcarshipTaxSchema prpTcarshipTaxSchemaCI = new PrpTcarshipTaxSchema();
				prpTcarshipTaxSchemaCI.setSchema(prpTcarshipTaxSchema);
				blProposalCI.getBLPrpTcarshipTax().setArr(prpTcarshipTaxSchemaCI);	
			}
			
			
		}
	}
	
	/**
	 * 处理CARTAXTJ_LIST节
	 * @param carTaxTJList
	 * @param paramMap
	 * @param BLProposalMap
	 * @throws Exception
	 */
	public void parseCarTaxTJ(Element carTaxTJList, Element carList, Map paramMap, Map BLProposalMap)throws Exception{
		
		BLProposal blProposalCI = (BLProposal)BLProposalMap.get(QuotationUtils.BLPROPOSALCI);
		BLProposal blProposalBI = (BLProposal)BLProposalMap.get(QuotationUtils.BLPROPOSALBI);
		
		Iterator iterator = carTaxTJList.elementIterator();
		Element carData = (Element)carList.elementIterator().next();

		while(iterator.hasNext()){
			
			PrpTcarshipTaxTJSchema prpTcarshipTaxTJSchema = new PrpTcarshipTaxTJSchema();

			Element cartaxtjData = (Element)iterator.next();
			
			
			prpTcarshipTaxTJSchema.setLicenseType(carData.elementText("LICENSETYPE"));
			prpTcarshipTaxTJSchema.setLicenseNo(carData.elementText("LICENSENO"));

			prpTcarshipTaxTJSchema.setCompleteKerbMass(carData.elementText("COMPLETEKERBMASS"));
			prpTcarshipTaxTJSchema.setEnrollDate(carData.elementText("ENROLLDATE").trim());
			
			String EXHAUSTSCALE = carData.elementText("POWERSCALE");
			if(EXHAUSTSCALE ==null){
				EXHAUSTSCALE = "";
			}
			

			prpTcarshipTaxTJSchema.setExhaustScale(EXHAUSTSCALE+"");
			prpTcarshipTaxTJSchema.setSeatCount(carData.elementText("SEATCOUNT"));
			prpTcarshipTaxTJSchema.setFrameNo(carData.elementText("FRAMENO"));
			prpTcarshipTaxTJSchema.setEngineNo(carData.elementText("ENGINENO"));

			prpTcarshipTaxTJSchema.setLicenseCategory(cartaxtjData.elementText("LICENSECATEGORYTJ"));
			
			prpTcarshipTaxTJSchema.setTaxComCode(cartaxtjData.elementText("TAXCOMCODE"));
			prpTcarshipTaxTJSchema.setPaidFreeCertificate(cartaxtjData.elementText("PAIDFREECERTIFICATE").trim());
			prpTcarshipTaxTJSchema.setTaxActual(cartaxtjData.elementText("TAXACTUAL"));
			prpTcarshipTaxTJSchema.setPreviousPay(cartaxtjData.elementText("PREVIOUSPAY"));
			prpTcarshipTaxTJSchema.setLateFee(cartaxtjData.elementText("LATEFEE"));
			prpTcarshipTaxTJSchema.setSumPayTax(cartaxtjData.elementText("SUMPAYTAX"));
			prpTcarshipTaxTJSchema.setTaxType(cartaxtjData.elementText("TAXTYPE"));
			prpTcarshipTaxTJSchema.setCarOwnerNature(cartaxtjData.elementText("CAROWNERNATURE"));
			prpTcarshipTaxTJSchema.setReliefFlag(cartaxtjData.elementText("RELIEFFLAG"));
			prpTcarshipTaxTJSchema.setEnTaxNo(cartaxtjData.elementText("ENTAXNO"));
			prpTcarshipTaxTJSchema.setTaxActual2(cartaxtjData.elementText("TAXACTUAL2"));
			prpTcarshipTaxTJSchema.setPreviousPay2(cartaxtjData.elementText("PREVIOUSPAY2"));
			prpTcarshipTaxTJSchema.setCarOwnerName(cartaxtjData.elementText("CAROWNERNAME"));
			prpTcarshipTaxTJSchema.setIdentifyType(cartaxtjData.elementText("IDENTIFYTYPE"));
			prpTcarshipTaxTJSchema.setIdentifyNumber(cartaxtjData.elementText("IDENTIFYNUMBER"));
			prpTcarshipTaxTJSchema.setAddressName(cartaxtjData.elementText("ADDRESSNAME"));
			prpTcarshipTaxTJSchema.setPostAddress(cartaxtjData.elementText("POSTADDRESS"));
			prpTcarshipTaxTJSchema.setPostCode(cartaxtjData.elementText("POSTCODE"));
			prpTcarshipTaxTJSchema.setPhoneNumber(cartaxtjData.elementText("PHONENUMBER"));
			prpTcarshipTaxTJSchema.setCountryCode(cartaxtjData.elementText("COUNTRYCODE"));
			prpTcarshipTaxTJSchema.setCompanyName(cartaxtjData.elementText("COMPANYNAME"));
			prpTcarshipTaxTJSchema.setUnitCertiNo(cartaxtjData.elementText("UNITCERTINO"));
			prpTcarshipTaxTJSchema.setRegisteredAddress(cartaxtjData.elementText("REGISTEREDADDRESS"));
			prpTcarshipTaxTJSchema.setCarAddressCode(cartaxtjData.elementText("CARADDRESSCODE"));
			prpTcarshipTaxTJSchema.setTransactionDate(cartaxtjData.elementText("TRANSACTIONDATE").trim());
			prpTcarshipTaxTJSchema.setPreLicenseNo(cartaxtjData.elementText("PRELICENSENO"));
			prpTcarshipTaxTJSchema.setFinalFlag(cartaxtjData.elementText("FINALFLAG"));
			prpTcarshipTaxTJSchema.setCalculateFlag(cartaxtjData.elementText("CALCULATEFLAG"));
			prpTcarshipTaxTJSchema.setExtendChar1(cartaxtjData.elementText("EXTENDCHAR1"));
			prpTcarshipTaxTJSchema.setExtendChar2(cartaxtjData.elementText("EXTENDCHAR2"));
			prpTcarshipTaxTJSchema.setExtendNum1(cartaxtjData.elementText("EXTENDNUM1"));
			prpTcarshipTaxTJSchema.setExtendNum2(cartaxtjData.elementText("EXTENDNUM2"));
			prpTcarshipTaxTJSchema.setExtendDate1(cartaxtjData.elementText("EXTENDDATE1"));
			prpTcarshipTaxTJSchema.setExtendDate2(cartaxtjData.elementText("EXTENDDATE2"));
			prpTcarshipTaxTJSchema.setPayTaxDate(cartaxtjData.elementText("PAYTAXDATE").trim());
			prpTcarshipTaxTJSchema.setTaxNum(cartaxtjData.elementText("TAXNUM"));
			prpTcarshipTaxTJSchema.setTaxNumType(cartaxtjData.elementText("TAXNUMTYPE"));
			
			
			if(blProposalCI!=null){
				PrpTcarshipTaxTJSchema prpTcarshipTaxTJSchemaCI = new PrpTcarshipTaxTJSchema();
				prpTcarshipTaxTJSchemaCI.setSchema(prpTcarshipTaxTJSchema);
				blProposalCI.getBLPrpTcarshipTaxTJ().setArr(prpTcarshipTaxTJSchemaCI);	
			}
			
			
		}
	}
	
	/**
	 * 处理TRAFFICDETAIL_LIST节
	 * @param trafficDetailList
	 * @param paramMap
	 * @param BLProposalMap
	 * @throws Exception
	 */
	public void parseTrafficDetail(Element trafficDetailList, Map paramMap, Map BLProposalMap)throws Exception{
		
		BLProposal blProposalCI = (BLProposal)BLProposalMap.get(QuotationUtils.BLPROPOSALCI);
		BLProposal blProposalBI = (BLProposal)BLProposalMap.get(QuotationUtils.BLPROPOSALBI);
		
		Iterator iterator = trafficDetailList.elementIterator();
		while(iterator.hasNext()){
			
			PrpTTrafficDetailSchema prpTTrafficDetailSchema = new PrpTTrafficDetailSchema();

			Element trafficDetailData = (Element)iterator.next();
			
			prpTTrafficDetailSchema.setFloatRatio(trafficDetailData.elementText("FLOATRATIO"));
			prpTTrafficDetailSchema.setRiskCode(trafficDetailData.elementText("RISKCODE"));
			prpTTrafficDetailSchema.setItemNo(trafficDetailData.elementText("ITEMNO"));
			prpTTrafficDetailSchema.setSerialNo(trafficDetailData.elementText("SERIALNO"));
			prpTTrafficDetailSchema.setTrafficCode(trafficDetailData.elementText("TRAFFICCODE"));
			prpTTrafficDetailSchema.setTrafficType(trafficDetailData.elementText("TRAFFICTYPE"));
			prpTTrafficDetailSchema.setAccidentType(trafficDetailData.elementText("ACCIDENTTYPE"));
			prpTTrafficDetailSchema.setBusinessType(trafficDetailData.elementText("BUSINESSTYPE"));
			prpTTrafficDetailSchema.setIndemnityDuty(trafficDetailData.elementText("INDEMNITYDUTY"));
			
			
			if(blProposalCI!=null){
				PrpTTrafficDetailSchema prpTTrafficDetailSchemaCI = new PrpTTrafficDetailSchema();
				prpTTrafficDetailSchemaCI.setSchema(prpTTrafficDetailSchema);
				blProposalCI.getBLPrpTTrafficDetail().setArr(prpTTrafficDetailSchemaCI);		
			}
			
			if(blProposalBI!=null){
				PrpTTrafficDetailSchema prpTTrafficDetailSchemaBI = new PrpTTrafficDetailSchema();
				prpTTrafficDetailSchemaBI.setSchema(prpTTrafficDetailSchema);
				blProposalBI.getBLPrpTTrafficDetail().setArr(prpTTrafficDetailSchemaBI);		
			}
		}
	}
	
	/**
	 * 处理APPLI_LIST节
	 * @param appliList
	 * @param paramMap
	 * @param BLProposalMap
	 * @throws Exception
	 */
	public void parseAppli(Element appliList, Map paramMap, Map BLProposalMap)throws Exception{
		
		BLProposal blProposalCI = (BLProposal)BLProposalMap.get(QuotationUtils.BLPROPOSALCI);
		BLProposal blProposalBI = (BLProposal)BLProposalMap.get(QuotationUtils.BLPROPOSALBI);
		
		Iterator iterator = appliList.elementIterator();
		while(iterator.hasNext()){
			
			PrpTmainSchema prpTmainSchema = new PrpTmainSchema();

			Element appliData = (Element)iterator.next();
			
			prpTmainSchema.setAppliName(appliData.elementText("APPLINAME"));
			
			
			
			if(blProposalCI!=null){
				PrpTmainSchema prpTmainSchemaCI = new PrpTmainSchema();
				prpTmainSchemaCI.setSchema(prpTmainSchema);
				blProposalCI.getBLPrpTmain().setArr(prpTmainSchemaCI);
			}
			
			if(blProposalBI!=null){
				PrpTmainSchema prpTmainSchemaBI = new PrpTmainSchema();
				prpTmainSchemaBI.setSchema(prpTmainSchema);
				blProposalBI.getBLPrpTmain().setArr(prpTmainSchemaBI);

			}
		}
	}
	
	/**
	 * 处理INSURED_LIST节
	 * @param insuredList
	 * @param paramMap
	 * @param BLProposalMap
	 * @throws Exception
	 */
	public void parseInsured(Element insuredList, Map paramMap, Map BLProposalMap)throws Exception{
		
		BLProposal blProposalCI = (BLProposal)BLProposalMap.get(QuotationUtils.BLPROPOSALCI);
		BLProposal blProposalBI = (BLProposal)BLProposalMap.get(QuotationUtils.BLPROPOSALBI);
		
		Iterator iterator = insuredList.elementIterator();
		while(iterator.hasNext()){
			

			Element insuredData = (Element)iterator.next();
			
			PrpTinsuredSchema prpTinsuredSchema = new PrpTinsuredSchema();
			PrpTinsuredNatureSchema prpInsuredNatureSchema = new PrpTinsuredNatureSchema();
			
			if("1".equals(insuredData.elementText("INSUREDFLAG"))||"2".equals(insuredData.elementText("INSUREDFLAG"))){
			  if("1".equals(insuredData.elementText("INSUREDFLAG"))){
				prpTinsuredSchema.setInsuredName(insuredData.elementText("INSUREDNAME"));
				
				prpTinsuredSchema.setInsuredFlag(insuredData.elementText("INSUREDFLAG"));

				prpTinsuredSchema.setInsuredType(insuredData.elementText("INSUREDTYPE"));
				prpTinsuredSchema.setInsuredNature(insuredData.elementText("INSUREDNATURE"));
				prpTinsuredSchema.setIdentifyType(insuredData.elementText("IDENTIFYTYPE"));
				prpTinsuredSchema.setIdentifyNumber(insuredData.elementText("IDENTIFYNUMBER"));
				prpTinsuredSchema.setInsuredAddress(insuredData.elementText("INSUREDADDRESS"));
				prpTinsuredSchema.setPostCode(insuredData.elementText("POSTCODE"));
				prpTinsuredSchema.setMobile(insuredData.elementText("MOBILE"));
				prpTinsuredSchema.setEmail(insuredData.elementText("EMAIL"));
				
                if("3".equals(prpTinsuredSchema.getInsuredNature())){
					
					prpInsuredNatureSchema.setInsuredFlag(prpTinsuredSchema.getInsuredFlag());
					prpInsuredNatureSchema.setSex(insuredData.elementText("SEX"));
					
					String OperateSite = (String)paramMap.get("OperateSiteBC");
					   String IdentifyNumber = insuredData.elementText("IDENTIFYNUMBER");
					   String currentDate = (String)paramMap.get("STARTDATECI");
					    if(currentDate==null || "".equals(currentDate)){
					        currentDate = (String)paramMap.get("STARTDATEBI");
					    }
					   /* String currentYear = currentDate.substring(0,4);
					        String currentMonth= currentDate.substring(5);
					        currentMonth= currentMonth.substring(0,currentMonth.indexOf("-"));
					        int intCurrentYear = Integer.parseInt(currentYear);
					        int intcurrentMonth= Integer.parseInt(currentMonth,10);
					        int intInputYear = 0;
					        int intIntputMonth = 0;
					        String inputYear = "";
					        String intputMonth = "";
					        if(IdentifyNumber.trim().length()==15)
					        {
					             inputYear   = "19" + IdentifyNumber.substring(6,8);
					             intputMonth = IdentifyNumber.substring(8,10);
					            intInputYear   = Integer.parseInt(inputYear);
					            intIntputMonth = Integer.parseInt(intputMonth,10);
					        }
					        if(IdentifyNumber.trim().length()==18)
					        {
					            inputYear   = IdentifyNumber.substring(6,10);
					            intputMonth = IdentifyNumber.substring(10,12);
					            intInputYear   = Integer.parseInt(inputYear);
					            intIntputMonth = Integer.parseInt(intputMonth,10);
					        }
					        int age = intCurrentYear - intInputYear;
					        
					        if (intcurrentMonth<intIntputMonth)
					        {
					            age = age -1;
					        }
					        String ages = ""+age;
					   prpInsuredNatureSchema.setAge(ages);*/
					   
					   
					   
					   
					    prpInsuredNatureSchema.setAge(insuredData.elementText("AGE"));
					
					   
					   
					   
					
					if(blProposalCI!=null){
						PrpTinsuredNatureSchema prpTinsuredNatureSchemaCI = new PrpTinsuredNatureSchema();
						prpTinsuredNatureSchemaCI.setSchema(prpInsuredNatureSchema);
						blProposalCI.getBLPrpTinsuredNature().setArr(prpTinsuredNatureSchemaCI);
						
					}
					if(blProposalBI!=null){
						PrpTinsuredNatureSchema prpTinsuredNatureSchemaBI = new PrpTinsuredNatureSchema();
						prpTinsuredNatureSchemaBI.setSchema(prpInsuredNatureSchema);
						blProposalBI.getBLPrpTinsuredNature().setArr(prpTinsuredNatureSchemaBI);
					}
					
				}				
			  }
			   if("2".equals(insuredData.elementText("INSUREDFLAG"))){
				  
				  prpTinsuredSchema.setInsuredName(insuredData.elementText("INSUREDNAME"));
					
					prpTinsuredSchema.setInsuredFlag(insuredData.elementText("INSUREDFLAG"));
					prpTinsuredSchema.setInsuredType(insuredData.elementText("INSUREDTYPE"));
					prpTinsuredSchema.setInsuredNature(insuredData.elementText("INSUREDNATURE"));
					prpTinsuredSchema.setIdentifyNumber(insuredData.elementText("IDENTIFYNUMBER"));
					prpTinsuredSchema.setInsuredAddress(insuredData.elementText("INSUREDADDRESS"));
					prpTinsuredSchema.setPostCode(insuredData.elementText("POSTCODE"));
					prpTinsuredSchema.setMobile(insuredData.elementText("MOBILE"));
					prpTinsuredSchema.setEmail(insuredData.elementText("EMAIL"));
					prpTinsuredSchema.setIdentifyType(insuredData.elementText("IDENTIFYTYPE"));
					if("2".equals(prpTinsuredSchema.getInsuredNature())){
						prpTinsuredSchema.setInsuredType("2");
					}else{
						prpTinsuredSchema.setInsuredType("1");
					}
					
			  }
			   
			   
			   
			
			if(blProposalCI!=null){
				
				blProposalCI.getBLPrpTmain().getArr(0).setInsuredName(insuredData.elementText("INSUREDNAME"));
				
				PrpTinsuredSchema prpTinsuredSchemaCI = new PrpTinsuredSchema();
				prpTinsuredSchemaCI.setSchema(prpTinsuredSchema);
				blProposalCI.getBLPrpTinsured().setArr(prpTinsuredSchemaCI);
							
			}
			
			if(blProposalBI!=null){
				
				 blProposalBI.getBLPrpTmain().getArr(0).setInsuredName(insuredData.elementText("INSUREDNAME"));
				
				PrpTinsuredSchema prpTinsuredSchemaBI = new PrpTinsuredSchema();
				prpTinsuredSchemaBI.setSchema(prpTinsuredSchema);
				blProposalBI.getBLPrpTinsured().setArr(prpTinsuredSchemaBI);
				
				
			}
			}	
			
		}
	}
	
	/**
	 * 处理KIND_LIST节
	 * @param kindList
	 * @param paramMap
	 * @param BLProposalMap
	 * @throws Exception
	 */
	public void parseKind(Element kindList, Map paramMap, BLProposal blProposal)throws Exception{
		
		Iterator iterator = kindList.elementIterator();

		String riskCode = kindList.attributeValue("RISKCODE");
		paramMap.put(QuotationUtils.RISKCODE, riskCode);
		blProposal.getBLPrpTmain().getArr(0).setRiskCode(riskCode);

		List kindCodeMainList = new ArrayList();
		List kindCodeAttachList = new ArrayList();
		while(iterator.hasNext()){

			Element kindData = (Element)iterator.next();

			
			String itemKindNo = kindData.elementText("ITEMKINDNO");
			String kindCode = kindData.elementText("KINDCODE");
			if("0508".equals(riskCode)){
				if("A,B,D3,D4,G1".indexOf(kindCode)>-1){
					kindCodeMainList.add(kindCode);
				}else{
					kindCodeAttachList.add(kindCode);
				}
			}
			String modeCode = kindData.elementText("MODECODE");
			String startDate = kindData.elementText("STARTDATE").trim();
			String startHour = kindData.elementText("STARTHOUR");
			String endDate = kindData.elementText("ENDDATE").trim();
			String endHour = kindData.elementText("ENDHOUR");
			String calculateFlag = kindData.elementText("CALCULATEFLAG");
			String currency = kindData.elementText("CURRENCY");
			String unitAmount = kindData.elementText("UNITAMOUNT");
			String quantity = kindData.elementText("QUANTITY");
			String value = kindData.elementText("VALUE");
			String amount = kindData.elementText("AMOUNT");
			String ratePeriod = kindData.elementText("RATEPERIOD");
			String rate = kindData.elementText("RATE");
			String shortRateFlag = kindData.elementText("SHORTRATEFLAG");
			String shortRate = kindData.elementText("SHORTRATE");
			String basePremium = kindData.elementText("BASEPREMIUM");
			String benchMarkPremium = kindData.elementText("BENCHMARKPREMIUM");
			String discount = kindData.elementText("DISCOUNT");
			String adjustRate = kindData.elementText("ADJUSTRATE");
			String premium = kindData.elementText("PREMIUM");
			String deductibleRate = kindData.elementText("DEDUCTIBLERATE");
			String deductible = kindData.elementText("DEDUCTIBLE");
			String flag = kindData.elementText("FLAG");
			String VehicleRepairPlant = kindData.elementText("VEHICLEREPAIRPLANT");
			
			
			PrpTitemKindSchema prpTitemKindSchema = new PrpTitemKindSchema();
			prpTitemKindSchema.setItemKindNo(itemKindNo);
			prpTitemKindSchema.setKindCode(kindCode);
			prpTitemKindSchema.setModeCode(modeCode);
			prpTitemKindSchema.setStartDate(startDate);
			prpTitemKindSchema.setStartHour(startHour);
			prpTitemKindSchema.setEndDate(endDate);
			prpTitemKindSchema.setEndHour(endHour);
			prpTitemKindSchema.setCalculateFlag(calculateFlag);
			prpTitemKindSchema.setCurrency(currency);
			prpTitemKindSchema.setUnitAmount(unitAmount);
			prpTitemKindSchema.setQuantity(quantity);
			prpTitemKindSchema.setValue(value);
			prpTitemKindSchema.setAmount(amount);
			prpTitemKindSchema.setRatePeriod(ratePeriod);
			prpTitemKindSchema.setRate(rate);
			
			prpTitemKindSchema.setShortRateFlag(shortRateFlag);
			prpTitemKindSchema.setShortRate(shortRate);
			
			prpTitemKindSchema.setBasePremium(basePremium);
			prpTitemKindSchema.setBenchMarkPremium(benchMarkPremium);
			prpTitemKindSchema.setDiscount(discount);
			prpTitemKindSchema.setAdjustRate(adjustRate);
			prpTitemKindSchema.setPremium(premium);
			prpTitemKindSchema.setDeductibleRate(deductibleRate);
			prpTitemKindSchema.setDeductible(deductible);
			prpTitemKindSchema.setFlag(flag);
			prpTitemKindSchema.setModel(VehicleRepairPlant);
			
			blProposal.getBLPrpTitemKind().setArr(prpTitemKindSchema);
			}
		
			if(kindCodeMainList.size()==0 && kindCodeAttachList.size()>0){
				throw new Exception("商业XXXXX不能只有附加XXXXX!");
			}
		}
	
	/**
	 * 处理PROFITDETAIL_LIST节
	 * @param profitDetailList
	 * @param paramMap
	 * @param BLProposalMap
	 * @throws Exception
	 */
	public void parseProfitDetail(Element profitDetailList, Map paramMap, BLProposal blProposal)throws Exception{
		
		Iterator iterator = profitDetailList.elementIterator();
		while(iterator.hasNext()){

			Element profitdetailData = (Element)iterator.next();

			
			String kindCode = profitdetailData.elementText("KINDCODE");
			String profitCode = profitdetailData.elementText("PROFITCODE");
			String serialNo = profitdetailData.elementText("SERIALNO");
			String profitRate = profitdetailData.elementText("PROFITRATE");
			String chooseFlag = profitdetailData.elementText("CHOOSEFLAG");
			String profitName = profitdetailData.elementText("PROFITNAME");
			String profitType = profitdetailData.elementText("PROFITTYPE");
			String conditionCode = profitdetailData.elementText("CONDITIONCODE");
			String conditionDesc = profitdetailData.elementText("CONDITIONDESC");
			String minProfitRate = profitdetailData.elementText("MINPROFITRATE");
			String maxProfitRate = profitdetailData.elementText("MAXPROFITRATE");
			
			
			PrpTprofitDetailSchema prpTprofitDetailSchema = new PrpTprofitDetailSchema();
			prpTprofitDetailSchema.setKindCode(kindCode);
			prpTprofitDetailSchema.setProfitCode(profitCode);
			prpTprofitDetailSchema.setSerialNo(serialNo);
			prpTprofitDetailSchema.setProfitRate(profitRate);
			prpTprofitDetailSchema.setChooseFlag(chooseFlag);
			prpTprofitDetailSchema.setProfitName(profitName);
			prpTprofitDetailSchema.setProfitType(profitType);
			prpTprofitDetailSchema.setConditionCode(conditionCode);
			prpTprofitDetailSchema.setCondition(conditionDesc);
			prpTprofitDetailSchema.setMinProfitRate(minProfitRate);
			prpTprofitDetailSchema.setMaxProfitRate(maxProfitRate);
		
		    blProposal.getBLPrpTprofitDetail().setArr(prpTprofitDetailSchema);
		}
	}

}
	

	
	
	