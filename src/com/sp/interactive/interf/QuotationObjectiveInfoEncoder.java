package com.sp.interactive.interf;

import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.sp.interactive.schema.PrpObjectiveReturnSchema;
import com.sp.prpall.blsvr.tb.BLProposal;
import com.sp.prpall.pubfun.PubTools;
import com.sp.prpall.schema.PrpTitemKindSchema;
import com.sp.quotation.pub.QuotationUtils;
import com.sp.utility.SysConfig;
import com.sunshine.ruleapp.bomPreUwrt.BomRuleResult;
import com.sunshine.utils.DateUtils;

public class QuotationObjectiveInfoEncoder {
	
	public String encode(Map paramMap, Map blProposalMap, String riskCode)throws Exception{
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("PACKET");
		root.addAttribute("type", "RESPONSE");
		root.addAttribute("version", "1.0");
		addHead(root,paramMap,blProposalMap);
		addBody(root,paramMap,blProposalMap,riskCode);

		return formateXml(doc.asXML());
	}
	
	public String encode(Map paramMap, Map blProposalMap)throws Exception{
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("PACKET");
		root.addAttribute("type", "RESPONSE");
		root.addAttribute("version", "1.0");
		addHead(root,paramMap,blProposalMap);
		addBody(root,paramMap,blProposalMap);

		return formateXml(doc.asXML());
	}
	
	/**
	 * 报文头部信息
	 * @param root
	 * @param paramMap
	 * @param blPolicyMap
	 * @throws Exception
	 */
	public void addHead(Element root, Map paramMap, Map blPolicyMap)throws Exception{
		Element head = root.addElement("HEAD");
		
        
        Element eTrans_Type = head.addElement("REQUESTTYPE");
        String strRequestType = (String)paramMap.get("requestType");
        eTrans_Type.addText(strRequestType);
        
        Element eErrorCode= head.addElement("RESPONSECODE");
        String strErrorcode = "000000";
        eErrorCode.addText(strErrorcode);
        
        Element eErrorMessage = head.addElement("RESPONSEMESSAGE");
        String strErrorMessage = "成功";
        eErrorMessage.addText(strErrorMessage);
	}
	/**
	 * 报文体信息
	 * @param root
	 * @param paramMap
	 * @param blPolicyMap
	 * @throws Exception
	 */
	public void addBody(Element root, Map paramMap, Map blProposalMap, String riskCode)throws Exception{
		Element body = root.addElement("BODY");
		
		BLProposal blProposalCI = (BLProposal)blProposalMap.get(QuotationUtils.BLPROPOSALCI);
		BLProposal blProposalBI = (BLProposal)blProposalMap.get(QuotationUtils.BLPROPOSALBI);
		
		BLProposal blProposalRuleCI = (BLProposal) blProposalMap.get("BLProposalRuleCI");
		BLProposal blProposalRuleBI = (BLProposal) blProposalMap.get("BLProposalRuleBI");
		
		PrpObjectiveReturnSchema prpObjectiveReturnSchemaCI = null;
		if (blProposalRuleCI!=null) {
			prpObjectiveReturnSchemaCI = blProposalRuleCI.getPrpObjectiveReturnSchema();
		}
		PrpObjectiveReturnSchema prpObjectiveReturnSchemaBI = null;
		if (blProposalRuleBI!=null) {
			prpObjectiveReturnSchemaBI = blProposalRuleBI.getPrpObjectiveReturnSchema();
		}
		
		Element eEnergyType = body.addElement("FUELTYPE");
		Element eInsuredNature = body.addElement("INSUREDNATURE");
		Element eCarOwnerNature = body.addElement("CAROWNERNATURE");
		Element ePurchasePrice = body.addElement("PURCHASEPRICE");
		Element eCarInsuredRelation = body.addElement("CARINSUREDRELATION");
		Element eCountryNature = body.addElement("COUNTRYNATURE");
		
		Element eAvgAnnualMil = body.addElement("AVGANNUALMIL");
		Element eUseNatureCode = body.addElement("USENATURECODE");
		Element eMainCarKindCode = body.addElement("MAINCARKINDCODE");
		Element eSafeDrivedaddonCount = body.addElement("SAFEDRIVEDADDONCOUNT");
		
		Element eRefersProvince= body.addElement("REFERSPROVINCE");
		Element eDrive = body.addElement("ISDRIVER");
		
		Element eForeignCar = body.addElement("FOREIGNCAR");
		
		
		Element eEnergyConservation = body.addElement("ISENERGYCONSERVATION");
		
		
		if("0507".equals(riskCode)){
			Element eStartDayCI = body.addElement("STARTDATEPRECISIONCI");
			Element eTaxRelifFlag = body.addElement("TAXRELIFFLAG");
			Element eFreeRate = body.addElement("FREERATE");
			Element eTaxUnitText = body.addElement("CUTRATEREASON");
			Element eNoUseProfitCodeCI = body.addElement("NOUSEPROFITCODECI");
			Element eTaxPayerName = body.addElement("TAXPAYERNAME");
			
			String strTaxRelifFlag = prpObjectiveReturnSchemaCI.getTaxRelifFlag();
			String strFreeRate = String.valueOf(prpObjectiveReturnSchemaCI.getFreeRate());
			String strEnergyType = prpObjectiveReturnSchemaCI.getEnergyType();
			String strTaxUnitText = prpObjectiveReturnSchemaCI.getTaxUnitText();
			String strProfitCodes = prpObjectiveReturnSchemaCI.getProfitCodes();
			String strStartDay = prpObjectiveReturnSchemaCI.getStartDay();
			String strInsuredNature = prpObjectiveReturnSchemaCI.getInsuredNature();
			String strCarOwnerNature = prpObjectiveReturnSchemaCI.getCarOwnerNature();
			
			String strPurchasePrice = "";
			String OperateSiteBC = blProposalCI.getBLPrpTmain().getArr(0).getOperateSite();
			if (SysConfig.getProperty("App_OperateSite").indexOf(","+OperateSiteBC+",")>-1) {
				if (prpObjectiveReturnSchemaCI.getPurchasePrice() == 0.0) {
					strPurchasePrice = blProposalCI.getBLPrpTitemCar().getArr(0).getPurchasePrice();
				} else {
					strPurchasePrice = String.valueOf(prpObjectiveReturnSchemaCI.getPurchasePrice());
				}
			}else{
				strPurchasePrice = blProposalCI.getBLPrpTitemCar().getArr(0).getPurchasePrice();
			}
			
			String strCarInsuredRelation = prpObjectiveReturnSchemaCI.getCarInsuredRelation();
			String strCountryNature = prpObjectiveReturnSchemaCI.getCountryNature();
			String strTaxPayerName = prpObjectiveReturnSchemaCI.getTaxPayerName();
			
			String strAvgAnnualMil = String.valueOf(prpObjectiveReturnSchemaCI.getAvgAnnualMil());
			String strUseNatureCode = prpObjectiveReturnSchemaCI.getUseNatureCode();
			String strMainCarKindCode = prpObjectiveReturnSchemaCI.getMainCarKindCode();
			String strSafeDrivedaddonCount = prpObjectiveReturnSchemaCI.isSafeDrivedaddonCount() ? "1" : "0";
			
			String strRefersProvince = prpObjectiveReturnSchemaCI.isRefersProvince() ? "1" : "0";
			String strDrive = prpObjectiveReturnSchemaCI.isDriver() ? "1" : "0";
			
			String strForeignCar = prpObjectiveReturnSchemaCI.isForeignCar() ? "1" : "0";
			
			
			String strEnergyConservation = prpObjectiveReturnSchemaCI.isEnergyConservation() ? "1" : "0";
			
			
			eTaxRelifFlag.addText(strTaxRelifFlag);
			eFreeRate.addText(strFreeRate);
			eEnergyType.addText(strEnergyType);
			eTaxUnitText.addText(strTaxUnitText);
			eNoUseProfitCodeCI.addText(strProfitCodes);
			eStartDayCI.addText(strStartDay);
			
			eInsuredNature.addText(strInsuredNature);
			eCarOwnerNature.addText(strCarOwnerNature);
			ePurchasePrice.addText(strPurchasePrice);
			eCarInsuredRelation.addText(strCarInsuredRelation);
			eCountryNature.addText(strCountryNature);
			eTaxPayerName.addText(strTaxPayerName);
			
			
			eAvgAnnualMil.addText(strAvgAnnualMil);
			eUseNatureCode.addText(strUseNatureCode);
			eMainCarKindCode.addText(strMainCarKindCode);
			eSafeDrivedaddonCount.addText(strSafeDrivedaddonCount);
			
			eRefersProvince.addText(strRefersProvince);
			eDrive.addText(strDrive);
			
			eForeignCar.addText(strForeignCar);
			
			
			eEnergyConservation.addText(strEnergyConservation);
			
		}
		
		else{
			Element eCarPrice = body.addElement("CARPRICE");
			Element eStartDayBI = body.addElement("STARTDATEPRECISIONBI");
			Element eNoUseProfitCodeBI = body.addElement("NOUSEPROFITCODEBI");
			Element eRate = body.addElement("SPECIALISTRATE");
			Element eIsUpRate = body.addElement("SPECIALISTSTATUS");
			Element eModeCode = body.addElement("GLASSSPECIES");
			Element eIsUpModeCode = body.addElement("ISMODIFYGLASSSPECIES");
			Element eValue = body.addElement("DEDUCTIBLEAMOUNTQ");
			Element eUpValue = body.addElement("ISUPDEDUCTIBLEAMOUNTQ");
			Element eItemKindList = body.addElement("ITEMKIND_LIST");
			
			String strCarPrice = blProposalBI.getBLPrpTitemCar().getArr(0).getActualValue();
			String strEnergyType = prpObjectiveReturnSchemaBI.getEnergyType();
			String strProfitCodes = prpObjectiveReturnSchemaBI.getProfitCodes();
			String strStartDay = prpObjectiveReturnSchemaBI.getStartDay();
			String strModeCode = prpObjectiveReturnSchemaBI.getModeCode();
			String strIsUpModeCode = prpObjectiveReturnSchemaBI.isUpModeCode() ? "1" : "0";
			
			String strRate = String.valueOf((int)prpObjectiveReturnSchemaBI.getRate());
			
			String strIsUpRate = prpObjectiveReturnSchemaBI.isUpRate() ? "1" : "0";
			String strInsuredNature = prpObjectiveReturnSchemaBI.getInsuredNature();
			String strCarOwnerNature = prpObjectiveReturnSchemaBI.getCarOwnerNature();
			
			String strPurchasePrice = "";
			String OperateSiteBC = blProposalBI.getBLPrpTmain().getArr(0).getOperateSite();
			if (SysConfig.getProperty("App_OperateSite").indexOf(","+OperateSiteBC+",")>-1) {
				if (prpObjectiveReturnSchemaBI.getPurchasePrice() == 0.0) {
					strPurchasePrice = blProposalBI.getBLPrpTitemCar().getArr(0).getPurchasePrice();
				} else {
					strPurchasePrice = String.valueOf(prpObjectiveReturnSchemaBI.getPurchasePrice());
				}
			}else{
				strPurchasePrice = blProposalBI.getBLPrpTitemCar().getArr(0).getPurchasePrice();
			}
			
			String strCarInsuredRelation = prpObjectiveReturnSchemaBI.getCarInsuredRelation();
			String strCountryNature = prpObjectiveReturnSchemaBI.getCountryNature();
			String strValue = prpObjectiveReturnSchemaBI.getValue();
			String strUpValue = prpObjectiveReturnSchemaBI.isUpValue() ? "1" : "0";
			
			
			String strAvgAnnualMil = String.valueOf(prpObjectiveReturnSchemaBI.getAvgAnnualMil());
			String strUseNatureCode = prpObjectiveReturnSchemaBI.getUseNatureCode();
			String strMainCarKindCode = prpObjectiveReturnSchemaBI.getMainCarKindCode();
			String strSafeDrivedaddonCount = prpObjectiveReturnSchemaBI.isSafeDrivedaddonCount() ? "1" : "0";
			
			String strRefersProvince = prpObjectiveReturnSchemaBI.isRefersProvince() ? "1" : "0";
			String strDrive = prpObjectiveReturnSchemaBI.isDriver() ? "1" : "0";
			
			String strForeignCar = prpObjectiveReturnSchemaBI.isForeignCar() ? "1" : "0";
			
			
			String strEnergyConservation = prpObjectiveReturnSchemaBI.isEnergyConservation() ? "1" : "0";
			
			
			eCarPrice.addText(strCarPrice);
			eEnergyType.addText(strEnergyType);
			eNoUseProfitCodeBI.addText(strProfitCodes);
			eStartDayBI.addText(strStartDay);
			eModeCode.addText(strModeCode);
			eIsUpModeCode.addText(strIsUpModeCode);
			eRate.addText(strRate);
			eIsUpRate.addText(strIsUpRate);
			eInsuredNature.addText(strInsuredNature);
			eCarOwnerNature.addText(strCarOwnerNature);
			ePurchasePrice.addText(strPurchasePrice);
			eCarInsuredRelation.addText(strCarInsuredRelation);
			eCountryNature.addText(strCountryNature);
			eValue.addText(strValue);
			eUpValue.addText(strUpValue);
			
			
			eAvgAnnualMil.addText(strAvgAnnualMil);
			eUseNatureCode.addText(strUseNatureCode);
			eMainCarKindCode.addText(strMainCarKindCode);
			eSafeDrivedaddonCount.addText(strSafeDrivedaddonCount);
			
			eRefersProvince.addText(strRefersProvince);
			eDrive.addText(strDrive);
			
			eForeignCar.addText(strForeignCar);
			
			
			eEnergyConservation.addText(strEnergyConservation);
			
			addItemKindData(eItemKindList, paramMap, blProposalRuleBI);
		}
		
	}
	
	public void addBody(Element root, Map paramMap, Map blProposalMap)throws Exception{
		Element body = root.addElement("BODY");
		
		BLProposal blProposalCI = (BLProposal)blProposalMap.get(QuotationUtils.BLPROPOSALCI);
		BLProposal blProposalBI = (BLProposal)blProposalMap.get(QuotationUtils.BLPROPOSALBI);
		
		BLProposal blProposalRuleCI = (BLProposal) blProposalMap.get("BLProposalRuleCI");
		BLProposal blProposalRuleBI = (BLProposal) blProposalMap.get("BLProposalRuleBI");
		
		PrpObjectiveReturnSchema prpObjectiveReturnSchemaCI = null;
		if (blProposalRuleCI!=null) {
			prpObjectiveReturnSchemaCI = blProposalRuleCI.getPrpObjectiveReturnSchema();
		}
		PrpObjectiveReturnSchema prpObjectiveReturnSchemaBI = null;
		if (blProposalRuleBI!=null) {
			prpObjectiveReturnSchemaBI = blProposalRuleBI.getPrpObjectiveReturnSchema();
		}
		
		Element eInfoTransNo = body.addElement("INFOTRANSNO");
		Element eOperateSite = body.addElement("OPERATESITE");
		Element eCarPrice = body.addElement("CARPRICE");
		Element eTaxRelifFlag = body.addElement("TAXRELIFFLAG");
		Element eFreeRate = body.addElement("FREERATE");
		Element eFuelType = body.addElement("FUELTYPE");
		Element eCutRateReason = body.addElement("CUTRATEREASON");
		Element eNoUseProfitCodeCI = body.addElement("NOUSEPROFITCODECI");
		Element eNoUseProfitCodeBI = body.addElement("NOUSEPROFITCODEBI");
		Element eStartDatePrecisionCI = body.addElement("STARTDATEPRECISIONCI");
		Element eStartDatePrecisionBI = body.addElement("STARTDATEPRECISIONBI");
		Element eIsComplianceCI = body.addElement("ISCOMPLIANCECI");
		Element eIsComplianceBI = body.addElement("ISCOMPLIANCEBI");
		Element eMessageInfoCI = body.addElement("MESSAGEINFOCI");
		Element eMessageInfoBI = body.addElement("MESSAGEINFOBI");
		Element eSpecialistRate = body.addElement("SPECIALISTRATE");
		Element eSpecialistStatus = body.addElement("SPECIALISTSTATUS");
		Element eGlassSpecies = body.addElement("GLASSSPECIES");
		Element eIsModifyGlassSpecies = body.addElement("ISMODIFYGLASSSPECIES");
		Element eInsuredNature = body.addElement("INSUREDNATURE");
		Element eCarOwnerNature = body.addElement("CAROWNERNATURE");
		Element ePurchasePrice = body.addElement("PURCHASEPRICE");
		Element eCarInsuredRelation = body.addElement("CARINSUREDRELATION");
		Element eCountryNature = body.addElement("COUNTRYNATURE");
		Element eTaxPayerName = body.addElement("TAXPAYERNAME");
		Element eUseYearsCI = body.addElement("VEHICLEUSEDYEARSCI");
		Element eUseYearsBI = body.addElement("VEHICLEUSEDYEARSBI");
		Element eValue = body.addElement("DEDUCTIBLEAMOUNTQ");
		Element eUpValue = body.addElement("ISUPDEDUCTIBLEAMOUNTQ");
		
		Element eAvgAnnualMil = body.addElement("AVGANNUALMIL");
		Element eUseNatureCode = body.addElement("USENATURECODE");
		Element eMainCarKindCode = body.addElement("MAINCARKINDCODE");
		Element eSafeDrivedaddonCount = body.addElement("SAFEDRIVEDADDONCOUNT");
		
		Element eRefersProvince= body.addElement("REFERSPROVINCE");
		Element eDrive = body.addElement("ISDRIVER");
		
		Element eForeignCar = body.addElement("FOREIGNCAR");
		
		
		Element eEnergyConservation = body.addElement("ISENERGYCONSERVATION");
		
		Element eItemKindList = body.addElement("ITEMKIND_LIST");
		
		
		Element undwrtList = body.addElement("UNDWRT_LIST");
		Element qualityList = body.addElement("QUALITY_LIST");
		
		
		
		Element eISALLOWDANBAOCI=body.addElement("ISALLOWDANBAOCI");
		Element eARGUESOLUTION=body.addElement("ARGUESOLUTION");
		Element eUSERDISCOUNTRATIOCI=body.addElement("USERDISCOUNTRATIOCI");
		Element eUSERDISCOUNTRATIOBI=body.addElement("USERDISCOUNTRATIOBI");
		Element eCARKINDCODE=body.addElement("CARKINDCODE");
		Element eVINNO=body.addElement("VINNO");
		
		
		String strFuelType;
		
		String strInfoTransNo;
		String strOperateSite;
		String strInsuredNature;
		String strCarOwnerNature;
		String strPurchasePrice;
		String strCarInsuredRelation;
		String strCountryNature;
		
		String strAvgAnnualMil;
		String strUseNatureCode;
		String strMainCarKindCode;
		String strSafeDrivedaddonCount;
		
		String strRefersProvince;
		String strDrive;
		
		String strForeignCar;
		
		
		String strEnergyConservation;
		
		
		String strISALLOWDANBAOCI="";
		String strARGUESOLUTION="";
		String strUSERDISCOUNTRATIOCI="";
		String strUSERDISCOUNTRATIOBI="";
		String strCARKINDCODE="";
		String strVINNO="";
		
		if (prpObjectiveReturnSchemaCI!=null) {
			strFuelType = prpObjectiveReturnSchemaCI.getEnergyType();
			
			strInfoTransNo = blProposalCI.getBLPrpTmain().getArr(0).getInfoTransNo();
			strOperateSite = blProposalCI.getBLPrpTmain().getArr(0).getOperateSite();
			strInsuredNature = prpObjectiveReturnSchemaCI.getInsuredNature();
			strCarOwnerNature = prpObjectiveReturnSchemaCI.getCarOwnerNature();
			
			String OperateSiteBC = blProposalCI.getBLPrpTmain().getArr(0).getOperateSite();
			if (SysConfig.getProperty("App_OperateSite").indexOf(","+OperateSiteBC+",")>-1) {
				if (prpObjectiveReturnSchemaCI.getPurchasePrice() == 0.0) {
					strPurchasePrice = blProposalCI.getBLPrpTitemCar().getArr(0).getPurchasePrice();
				} else {
					strPurchasePrice = String.valueOf(prpObjectiveReturnSchemaCI.getPurchasePrice());
				}
			}else{
				strPurchasePrice = blProposalCI.getBLPrpTitemCar().getArr(0).getPurchasePrice();
			}
			
			strCarInsuredRelation = prpObjectiveReturnSchemaCI.getCarInsuredRelation();
			strCountryNature = prpObjectiveReturnSchemaCI.getCountryNature();
			
			strAvgAnnualMil = String.valueOf(prpObjectiveReturnSchemaCI.getAvgAnnualMil());
			strUseNatureCode = prpObjectiveReturnSchemaCI.getUseNatureCode();
			strMainCarKindCode = prpObjectiveReturnSchemaCI.getMainCarKindCode();
			strSafeDrivedaddonCount = prpObjectiveReturnSchemaCI.isSafeDrivedaddonCount() ? "1" : "0";
			
			strRefersProvince = prpObjectiveReturnSchemaCI.isRefersProvince() ? "1" : "0";
			strDrive = prpObjectiveReturnSchemaCI.isDriver() ? "1" : "0";
			
			strForeignCar = prpObjectiveReturnSchemaCI.isForeignCar() ? "1" : "0";
			
			
			strEnergyConservation = prpObjectiveReturnSchemaCI.isEnergyConservation() ? "1" : "0";
			
		}else{
			strFuelType = prpObjectiveReturnSchemaBI.getEnergyType();
			strInfoTransNo = blProposalBI.getBLPrpTmain().getArr(0).getInfoTransNo();
			strOperateSite = blProposalBI.getBLPrpTmain().getArr(0).getOperateSite();
			strInsuredNature = prpObjectiveReturnSchemaBI.getInsuredNature();
			strCarOwnerNature = prpObjectiveReturnSchemaBI.getCarOwnerNature();
			
			String OperateSiteBC = blProposalBI.getBLPrpTmain().getArr(0).getOperateSite();
			if (SysConfig.getProperty("App_OperateSite").indexOf(","+OperateSiteBC+",")>-1) {
				if (prpObjectiveReturnSchemaBI.getPurchasePrice() == 0.0) {
					strPurchasePrice = blProposalBI.getBLPrpTitemCar().getArr(0).getPurchasePrice();
				} else {
					strPurchasePrice = String.valueOf(prpObjectiveReturnSchemaBI.getPurchasePrice());
				}
			}else{
				strPurchasePrice = blProposalBI.getBLPrpTitemCar().getArr(0).getPurchasePrice();
			}
			
			strCarInsuredRelation = prpObjectiveReturnSchemaBI.getCarInsuredRelation();
			strCountryNature = prpObjectiveReturnSchemaBI.getCountryNature();
			
			strAvgAnnualMil = String.valueOf(prpObjectiveReturnSchemaBI.getAvgAnnualMil());
			strUseNatureCode = prpObjectiveReturnSchemaBI.getUseNatureCode();
			strMainCarKindCode = prpObjectiveReturnSchemaBI.getMainCarKindCode();
			strSafeDrivedaddonCount = prpObjectiveReturnSchemaBI.isSafeDrivedaddonCount() ? "1" : "0";
			
			strRefersProvince = prpObjectiveReturnSchemaBI.isRefersProvince() ? "1" : "0";
			strDrive = prpObjectiveReturnSchemaBI.isDriver() ? "1" : "0";
			
			strForeignCar = prpObjectiveReturnSchemaBI.isForeignCar() ? "1" : "0";
			
			
			strEnergyConservation = prpObjectiveReturnSchemaBI.isEnergyConservation() ? "1" : "0";
			
		}
		
		eFuelType.addText(strFuelType);
		
		eInfoTransNo.addText(strInfoTransNo);
		eOperateSite.addText(strOperateSite);
		eInsuredNature.addText(strInsuredNature);
		eCarOwnerNature.addText(strCarOwnerNature);
		ePurchasePrice.addText(strPurchasePrice);
		eCarInsuredRelation.addText(strCarInsuredRelation);
		eCountryNature.addText(strCountryNature);
		
		eAvgAnnualMil.addText(strAvgAnnualMil);
		eUseNatureCode.addText(strUseNatureCode);
		eMainCarKindCode.addText(strMainCarKindCode);
		eSafeDrivedaddonCount.addText(strSafeDrivedaddonCount);
		
		eRefersProvince.addText(strRefersProvince);
		eDrive.addText(strDrive);
		
		eForeignCar.addText(strForeignCar);
		
		
		eEnergyConservation.addText(strEnergyConservation);
		
		
		if (blProposalCI!=null) {
			
			String strTaxRelifFlag = prpObjectiveReturnSchemaCI.getTaxRelifFlag();
			String strFreeRate = String.valueOf(prpObjectiveReturnSchemaCI.getFreeRate());
			String strCutRateReason = prpObjectiveReturnSchemaCI.getTaxUnitText();
			String strNoUseProfitCodeCI = prpObjectiveReturnSchemaCI.getProfitCodes();
			String strIsComplianceCI = "1";
			String strUseYearsCI = blProposalCI.getBLPrpTitemCar().getArr(0).getUseYears();
			String strTaxPayerName = prpObjectiveReturnSchemaCI.getTaxPayerName();
			String strStartDatePrecisionCI = prpObjectiveReturnSchemaCI.getStartDay();
			
			BomRuleResult bomRuleResultCI =new BomRuleResult();
			bomRuleResultCI= (BomRuleResult) paramMap
					.get("bomRuleResultCI");
			if(bomRuleResultCI!=null){
				boolean bIs0507=bomRuleResultCI.isIs0507();
				String is0507="0";
				if(bIs0507){
					is0507="1";
				}
				strISALLOWDANBAOCI=is0507;
				Set<String> conProfitCodesSet = bomRuleResultCI.getConProfitCodes();
				Iterator<String> conProfitCodeIterator = conProfitCodesSet.iterator();
				while(conProfitCodeIterator.hasNext()){
					strUSERDISCOUNTRATIOCI=strUSERDISCOUNTRATIOCI+","+conProfitCodeIterator.next();
				}
				if(strUSERDISCOUNTRATIOCI.trim().length()>0){
				strUSERDISCOUNTRATIOCI=strUSERDISCOUNTRATIOCI.substring(1,strUSERDISCOUNTRATIOCI.length());
				}
			}
			
			strARGUESOLUTION=blProposalCI.getBLPrpTmain().getArr(0).getArgueSolution();
			if(blProposalCI.getBLPrpTitemCar().getSize()>0){
			strCARKINDCODE=blProposalCI.getBLPrpTitemCar().getArr(0).getCarKindCode();
			}
			strVINNO=blProposalCI.getBLPrpTitemCar().getArr(0).getVINNo();
			
			eTaxRelifFlag.addText(strTaxRelifFlag!=null?strTaxRelifFlag:"");
			eFreeRate.addText(strFreeRate!=null?strFreeRate:"");
			
			eCutRateReason.addText(strCutRateReason!=null?strCutRateReason:"");
			
			eNoUseProfitCodeCI.addText(strNoUseProfitCodeCI!=null?strNoUseProfitCodeCI:"");
			eIsComplianceCI.addText(strIsComplianceCI!=null?strIsComplianceCI:"");
			eMessageInfoCI.addText(" ");
			eTaxPayerName.addText(strTaxPayerName!=null?strTaxPayerName:"");
			eUseYearsCI.addText(strUseYearsCI!=null?strUseYearsCI:"");
			eStartDatePrecisionCI.addText(strStartDatePrecisionCI!=null?strStartDatePrecisionCI:"");
		}
		if (blProposalBI!=null) {
			
			String strCarPrice = blProposalBI.getBLPrpTitemCar().getArr(0).getActualValue();
			String strNoUseProfitCodeBI = prpObjectiveReturnSchemaBI.getProfitCodes();
			String strIsComplianceBI = "1";
			
			String strSpecialistRate = String.valueOf((int)prpObjectiveReturnSchemaBI.getRate());
			
			String strSpecialistStatus = prpObjectiveReturnSchemaBI.isUpRate() ? "1": "0";
			String strGlassSpecies = prpObjectiveReturnSchemaBI.getModeCode();
			String strIsModifyGlassSpecies = prpObjectiveReturnSchemaBI.isUpModeCode() ? "1" : "0";
			String strUseYearsBI = blProposalBI.getBLPrpTitemCar().getArr(0).getUseYears();
			String strStartDatePrecisionBI = prpObjectiveReturnSchemaBI.getStartDay();
			String strValue = prpObjectiveReturnSchemaBI.getValue();
			String strUpValue = prpObjectiveReturnSchemaBI.isUpValue() ? "1" : "0";
			
			BomRuleResult bomRuleResultBI = (BomRuleResult) paramMap
					.get("bomRuleResultBI");
			if(bomRuleResultBI!=null){
				boolean bIs0507=bomRuleResultBI.isIs0507();
				String is0507="0";
				if(bIs0507){
					is0507="1";
				}
				strISALLOWDANBAOCI=is0507;
				Set<String> conProfitCodesSet = bomRuleResultBI.getConProfitCodes();
				Iterator<String> conProfitCodeIterator = conProfitCodesSet.iterator();
				while(conProfitCodeIterator.hasNext()){
					strUSERDISCOUNTRATIOBI=strUSERDISCOUNTRATIOBI+","+conProfitCodeIterator.next();
				}
				if(strUSERDISCOUNTRATIOBI.trim().length()>0){
				strUSERDISCOUNTRATIOBI=strUSERDISCOUNTRATIOBI.substring(1,strUSERDISCOUNTRATIOBI.length());
				}
			}
			strARGUESOLUTION=blProposalBI.getBLPrpTmain().getArr(0).getArgueSolution();
			if(blProposalBI.getBLPrpTitemCar().getSize()>0){
			strCARKINDCODE=blProposalBI.getBLPrpTitemCar().getArr(0).getCarKindCode();
			}
			strVINNO=blProposalBI.getBLPrpTitemCar().getArr(0).getVINNo();
			
			eCarPrice.addText(strCarPrice!=null?strCarPrice:"");
			eNoUseProfitCodeBI.addText(strNoUseProfitCodeBI!=null?strNoUseProfitCodeBI:"");
			eIsComplianceBI.addText(strIsComplianceBI!=null?strIsComplianceBI:"");
			eMessageInfoBI.addText(" ");
			eGlassSpecies.addText(strGlassSpecies!=null?strGlassSpecies:"");
			eIsModifyGlassSpecies.addText(strIsModifyGlassSpecies!=null?strIsModifyGlassSpecies:"");
			eSpecialistRate.addText(strSpecialistRate!=null?strSpecialistRate:"");
			eSpecialistStatus.addText(strSpecialistStatus!=null?strSpecialistStatus:"");
			eUseYearsBI.addText(strUseYearsBI!=null?strUseYearsBI:"");
			eStartDatePrecisionBI.addText(strStartDatePrecisionBI!=null?strStartDatePrecisionBI:"");
			eValue.addText(strValue!=null?strValue:"");
			eUpValue.addText(strUpValue!=null?strUpValue:"");
			addItemKindData(eItemKindList, paramMap, blProposalRuleBI);
		}
		
		eISALLOWDANBAOCI.addText(strISALLOWDANBAOCI!=null?strISALLOWDANBAOCI:"");
		eARGUESOLUTION.addText(strARGUESOLUTION!=null?strARGUESOLUTION:"");
		eUSERDISCOUNTRATIOCI.addText(strUSERDISCOUNTRATIOCI!=null?strUSERDISCOUNTRATIOCI:"");
		eUSERDISCOUNTRATIOBI.addText(strUSERDISCOUNTRATIOBI!=null?strUSERDISCOUNTRATIOBI:"");
		eCARKINDCODE.addText(strCARKINDCODE!=null?strCARKINDCODE:""); 
		eVINNO.addText(strVINNO!=null?strVINNO:"");
		
		
		
		addUndwrt(undwrtList, blProposalCI, blProposalBI, paramMap);
		
		
		addQuality(qualityList, blProposalCI, blProposalBI, paramMap);
		
		
		addCoverage(body.addElement("COVERAGE_LIST"), blProposalCI, blProposalBI, paramMap);
		
		
	}
	
	private void addCoverage(Element COVERAGE_LIST, BLProposal blProposalCI,
			BLProposal blProposalBI, Map paramMap) {
		Element eCoverageListRes = COVERAGE_LIST.addElement("COVERAGE_DATA");
		BomRuleResult bomRuleResultBI = (BomRuleResult) paramMap
				.get("bomRuleResultBI"); 
		if(bomRuleResultBI!=null){
			Element COVERAGEUPPER=eCoverageListRes.addElement("COVERAGEUPPER");
			 String amount="";
			 Set<String> setAmounts = bomRuleResultBI.getAmounts();
			 if(setAmounts!=null){
			 Iterator<String> iterator = setAmounts.iterator();
			 while(iterator.hasNext()){
				 amount=amount+","+ iterator.next();
				
			 }
			 if(amount.trim().length()>0){
			 amount=amount.substring(1,amount.length());
			 }
			 }
			COVERAGEUPPER.addText(amount);
		}
		BomRuleResult bomRuleResultCI = (BomRuleResult) paramMap
				.get("bomRuleResultCI");
		if(bomRuleResultCI!=null){
			Element COVERAGEUPPER=eCoverageListRes.addElement("COVERAGEUPPER");
			String amount="";
			  Set<String> setAmounts = bomRuleResultCI.getAmounts();
			 if(setAmounts!=null){
			 Iterator<String> iterator = setAmounts.iterator();
			 while(iterator.hasNext()){
				 amount=amount+","+ iterator.next();
				
			 }
			 if(amount.trim().length()>0){
			 amount= amount.substring(1,amount.length());
			 }
			 }
			COVERAGEUPPER.addText(amount);
		}
	}
	
	/**
	 * 封装解析ItemKind
	 * @param itemKindList
	 * @param paramMap
	 * @param blProposalRule
	 * @throws Exception
	 */
	public void addItemKindData(Element itemKindList, Map paramMap, BLProposal blProposalRule)throws Exception{
		int kindSchemaCount = blProposalRule.getBLPrpTitemKind().getSize();
		for(int i=0; i<kindSchemaCount;i++){
			PrpTitemKindSchema prpTitemKindSchema = blProposalRule.getBLPrpTitemKind().getArr(i);
			
			Element itemKindData = itemKindList.addElement("ITEMKIND_DATA");
			
			Element eKindCode = itemKindData.addElement("KINDCODE");
			Element eIsInsure = itemKindData.addElement("ISINSURE");
			Element eIsUpinsure = itemKindData.addElement("ISUPINSURE");
			Element eAmount = itemKindData.addElement("AMOUNT");
			Element eIsUpamount = itemKindData.addElement("ISUPAMOUNT");
			Element eIsInsureper = itemKindData.addElement("ISINSUREPER");
			Element eIsUpinsureper = itemKindData.addElement("ISUPINSUREPER");
			
			
			String strFlag = prpTitemKindSchema.getFlag();
			String strKindCode = prpTitemKindSchema.getKindCode();
			String strInsureCarKind = String.valueOf(strFlag.charAt(0));
			String strModifyCarCode = String.valueOf(strFlag.charAt(1));
			String strAmount = prpTitemKindSchema.getAmount();
			String strIsModifyAmount = String.valueOf(strFlag.charAt(2));
			String strIsDeductibleCarKind = String.valueOf(strFlag.charAt(3));
			String strDeductibleStatus = String.valueOf(strFlag.charAt(4));
			
			
			eKindCode.addText(strKindCode);
			eIsInsure.addText(strInsureCarKind);
			eIsUpinsure.addText(strModifyCarCode);
			eAmount.addText(strAmount);
			eIsUpamount.addText(strIsModifyAmount);
			eIsInsureper.addText(strIsDeductibleCarKind);
			eIsUpinsureper.addText(strDeductibleStatus);
			
		}
		
	}
	
	/**
	 * 报错信息
	 * @param paramMap
	 * @param blProposalMap
	 * @throws Exception
	 */
	public String encodeException(Map paramMap, Map blProposalMap, Exception e) throws IOException, DocumentException{
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("PACKET");
		root.addAttribute("type", "RESPONSE");
		root.addAttribute("version", "1.0");
		Element head = root.addElement("HEAD");
		Element body = root.addElement("BODY");
		
		Element Trans_Type = head.addElement("TRANSTYPE");
		String TransType = (String) paramMap.get("requestType");
		Trans_Type.addText(TransType);
		
		Element Error_Code = head.addElement("RESPONSECODE");
		Element Error_Message = head.addElement("RESPONSEMESSAGE");
		Element Exist_Quantity = body.addElement("EXISTQUANTITY");
		String ErrorCode;
		String ErrorMessage;
		if (e==null) {
			ErrorCode = (String) paramMap.get("Error_Code");
			ErrorMessage = (String) paramMap.get("Error_Message");
		}else{
			ErrorCode = "100000";
			ErrorMessage = e.getMessage();
		}
		if("100000".equals(ErrorCode)){
			ErrorMessage = "系统异常，异常信息：" + ErrorMessage;
		}else if("200000".equals(ErrorCode)){
			ErrorMessage = "规则校验错误信息：" + ErrorMessage;
		}else{
			ErrorMessage = "核心校验错误信息：" + ErrorMessage;
		}
		
		String Quantity = (String)blProposalMap.get("Exist_Quantity");
		if(!"".equals(ErrorCode) && ErrorCode != null){
			Error_Code.addText(ErrorCode);
			Error_Message.addText(ErrorMessage);
			Exist_Quantity.addText(Quantity!=null?Quantity:"");
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
	 * 转人工
	 * 
	 * @param body
	 * @param blProposalCI
	 * @param blProposalBI
	 * @throws
	 * */
	public void addUndwrt(Element UNDWRT_LIST, BLProposal blProposalCI,
			BLProposal blProposalBI, Map paramMap) throws Exception {
		BomRuleResult bomRuleResultBI = (BomRuleResult) paramMap
				.get("bomRuleResultBI");
		BomRuleResult bomRuleResultCI = (BomRuleResult) paramMap
				.get("bomRuleResultCI");
		if (bomRuleResultBI != null && blProposalBI != null) {
			String riskcode = blProposalBI.getBLPrpTmain().getArr(0)
					.getRiskCode();
			if (bomRuleResultBI.getUndwrtInfo().size() > 0) {
				for (int i = 0; i < bomRuleResultBI.getUndwrtInfo().size(); i++) {
					Element undwrtData = UNDWRT_LIST.addElement("UNDWRT_DATA");
					addUndwrtData(undwrtData, bomRuleResultBI.getUndwrtInfo()
							.get(i), true, riskcode);
				}
			} 
		}
		if (bomRuleResultCI != null && blProposalCI != null) {
			String riskcode = blProposalCI.getBLPrpTmain().getArr(0)
					.getRiskCode();
			if (bomRuleResultCI.getUndwrtInfo().size() > 0) {
				for (int i = 0; i < bomRuleResultCI.getUndwrtInfo().size(); i++) {
					Element undwrtData = UNDWRT_LIST.addElement("UNDWRT_DATA");
					addUndwrtData(undwrtData, bomRuleResultCI.getUndwrtInfo()
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
		String isundwrt="0";
		if(bIsUndwrt){
			isundwrt="1";
		}
		ebisundwrt.addText(isundwrt);
		
		Element eundwrtinfo = undwrtData.addElement("UNDWRTINFO");
		eundwrtinfo.addText(undwrtInfo!=null?undwrtInfo:"");
		
		Element eriskcode = undwrtData.addElement("RISKCODE");
		eriskcode.addText(riskcode!=null?riskcode:"");
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
		
		BomRuleResult bomRuleResultBI = (BomRuleResult) paramMap
				.get("bomRuleResultBI");
		BomRuleResult bomRuleResultCI = (BomRuleResult) paramMap
				.get("bomRuleResultCI");
		if (bomRuleResultBI != null && blProposalBI != null) {
			String riskcode = blProposalBI.getBLPrpTmain().getArr(0)
					.getRiskCode();
			if (bomRuleResultBI.getQualityInfo().size() > 0) {
				for (int i = 0; i < bomRuleResultBI.getQualityInfo().size(); i++) {
					Element undwrtData = QUALITY_LIST.addElement("QUALITY_DATA");
					addQualityData(undwrtData, bomRuleResultBI.getQualityInfo()
							.get(i), true, riskcode);
				}
			} 
		}
		if (bomRuleResultCI != null && blProposalCI != null) {
			String riskcode = blProposalCI.getBLPrpTmain().getArr(0)
					.getRiskCode();
			if (bomRuleResultCI.getQualityInfo().size() > 0) {
				for (int i = 0; i < bomRuleResultCI.getQualityInfo().size(); i++) {
					Element undwrtData = QUALITY_LIST.addElement("QUALITY_DATA");
					addQualityData(undwrtData, bomRuleResultCI.getQualityInfo()
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
		String isquality="0";
		if(bIsQuality){
			isquality="1";
		}
		BISQUALITY.addText(isquality);
		
		Element QUALITYINFO = qualityData.addElement("QUALITYINFO");
		QUALITYINFO.addText(qualityInfo!=null?qualityInfo:"");
		
		Element RISKCODE = qualityData.addElement("RISKCODE");
		RISKCODE.addText(riskcode!=null?riskcode:"");
	}
	
}
