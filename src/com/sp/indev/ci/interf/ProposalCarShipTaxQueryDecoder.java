package com.sp.indiv.ci.interf;



import java.text.DecimalFormat;

import org.w3c.dom.Node;

import com.sp.indiv.ci.schema.CICarShipTaxQGDemandSchema;
import com.sp.indiv.ci.schema.CICarshipTaxDemandSchema;
import com.sp.prpall.blsvr.tb.BLProposal;
import com.sp.prpall.pubfun.PubTools;
import com.sp.sysframework.common.util.XMLUtils;
import com.sp.utility.UtiPower;
import com.sp.utility.database.DbPool;
import com.sp.utility.string.ChgData;
import com.sp.utility.string.ChgDate;

public class ProposalCarShipTaxQueryDecoder {

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		
	}
    /**
     * 解码
     * @return XX查询请求XML格式串
     * @throws Exception
     */
    public void decode(BLProposal blProposal, Node node) throws Exception 
    {
        
        UtiPower utiPower = new UtiPower();
        if(utiPower.checkCICarshipTaxSH(blProposal.getBLPrpTmain().getArr(0).getRiskCode(), blProposal.getBLPrpTmain().getArr(0).getComCode()))
        {
        	processCarShipTaxSH(blProposal,node);
        }
        else if(utiPower.checkCICarshipTaxBJ(blProposal.getBLPrpTmain().getArr(0).getRiskCode(), blProposal.getBLPrpTmain().getArr(0).getComCode())){
        	processCarShipTaxBJ(blProposal,node);
        }
    }
    /**
     * 处理车船税信息
     * @param node Node
     * @throws Exception
     */
    protected void processCarShipTaxSH(BLProposal blProposal, Node node) throws Exception 
    {
    	DecimalFormat idecimalFormat = new DecimalFormat("0.00");
    	CICarshipTaxDemandSchema ciCarShiptaxDemandSchema = new CICarshipTaxDemandSchema();
      	ciCarShiptaxDemandSchema.setDemandNo(XMLUtils.getChildNodeValue(node, "QUERY_SEQUENCE_NO"));
    	
      	 
      	ciCarShiptaxDemandSchema.setTaxFlag(XMLUtils.getChildNodeValue(node, "TAX_FLAG"));
      	ciCarShiptaxDemandSchema.setLicenseCategory(XMLUtils.getChildNodeValue(node, "PO_CATEGORY"));
      	ciCarShiptaxDemandSchema.setCompleteKerbMass(XMLUtils.getChildNodeValue(node, "PO_WEIGHT"));
      	ciCarShiptaxDemandSchema.setTaxpayerCertiType(XMLUtils.getChildNodeValue(node, "TAXPAYER_CERTI_TYPE"));
      	ciCarShiptaxDemandSchema.setIdentifyNumber(XMLUtils.getChildNodeValue(node, "TAXPAYER_CERTI_CODE"));
      	ciCarShiptaxDemandSchema.setPaidCertificate(XMLUtils.getChildNodeValue(node, "PAY_NO"));
      	ciCarShiptaxDemandSchema.setFreeCertificate(XMLUtils.getChildNodeValue(node, "FREE_NO"));
      	ciCarShiptaxDemandSchema.setTaxComName(XMLUtils.getChildNodeValue(node, "DEPARTMENT"));
      	ciCarShiptaxDemandSchema.setPayerCode(XMLUtils.getChildNodeValue(node, "TAXPAYER_NO"));	
      	ciCarShiptaxDemandSchema.setTaxActual(XMLUtils.getChildNodeValue(node, "CURRENT_TAX"));
      	ciCarShiptaxDemandSchema.setPreviousPay(XMLUtils.getChildNodeValue(node, "FORMER_TAX"));
      	ciCarShiptaxDemandSchema.setLateFee(XMLUtils.getChildNodeValue(node, "LATE_FEE"));
    	
      	
      	
    	
      	/*String paidCertificate = "";
    	String freeCertificate = "";
    	if(blProposal.getBLPrpTcarshipTax().getArr(0).getTaxRelifFlag().equals("W")){
    		paidCertificate = blProposal.getBLPrpTcarshipTax().getArr(0).getPaidFreeCertificate();
    	}
    	else if(blProposal.getBLPrpTcarshipTax().getArr(0).getTaxRelifFlag().equals("M")){
    		freeCertificate = blProposal.getBLPrpTcarshipTax().getArr(0).getPaidFreeCertificate();
    	}
      	
    	ciCarShiptaxDemandSchema.setTaxFlag(blProposal.getBLPrpTcarshipTax().getArr(0).getTaxRelifFlag());
    	ciCarShiptaxDemandSchema.setLicenseCategory(blProposal.getBLPrpTcarshipTax().getArr(0).getExtendChar2());
    	ciCarShiptaxDemandSchema.setCompleteKerbMass(blProposal.getBLPrpTcarshipTax().getArr(0).getCompleteKerbMass());
    	ciCarShiptaxDemandSchema.setTaxpayerCertiType(blProposal.getBLPrpTcarshipTax().getArr(0).getTaxpayerIdentifier());
    	ciCarShiptaxDemandSchema.setIdentifyNumber(blProposal.getBLPrpTcarshipTax().getArr(0).getIdentifyNumber());
    	ciCarShiptaxDemandSchema.setPaidCertificate(paidCertificate);
    	ciCarShiptaxDemandSchema.setFreeCertificate(freeCertificate);
    	ciCarShiptaxDemandSchema.setTaxComName(blProposal.getBLPrpTcarshipTax().getArr(0).getTaxComName());
    	ciCarShiptaxDemandSchema.setPayerCode("zhenglu");	
    	ciCarShiptaxDemandSchema.setTaxActual("100");
    	ciCarShiptaxDemandSchema.setPreviousPay("200");
    	ciCarShiptaxDemandSchema.setLateFee("300");*/
        
    	
    	
    	double dblTaxActual = Double.parseDouble(ChgData.chgStrZero(ciCarShiptaxDemandSchema.getTaxActual()));
    	double dblPreviousPay = Double.parseDouble(ChgData.chgStrZero(ciCarShiptaxDemandSchema.getPreviousPay()));
    	double dblLateFee = Double.parseDouble(ChgData.chgStrZero(ciCarShiptaxDemandSchema.getLateFee()));
    	
		double dblSumPayTax = Double.parseDouble(idecimalFormat.format(dblTaxActual + dblPreviousPay + dblLateFee));

    	ciCarShiptaxDemandSchema.setSumTax("" + dblSumPayTax);
    	blProposal.getBLCICarshipTaxDemand().setArr(ciCarShiptaxDemandSchema); 
    }
    
    /**
     * 处理XXXXX车船税
     * @param blProposal
     * @param node
     * @throws Exception
     */
    protected void processCarShipTaxBJ(BLProposal blProposal, Node node) throws Exception 
    {

    	String taxRelifFlag = blProposal.getBLPrpTcarshipTax().getArr(0).getTaxRelifFlag();
	    String licenseNo = blProposal.getBLPrpTitemCar().getArr(0).getLicenseNo().trim().substring(0,1);
	    String paidCertificate = blProposal.getBLPrpTcarshipTax().getArr(0).getPaidFreeCertificate();
	    String taxComName = blProposal.getBLPrpTcarshipTax().getArr(0).getTaxComName();
	    String certificateDate = "";
	    String completeKerbMass = "";
    	String strPayStartDate = null;
    	String strPayEndDate = null;
    	DecimalFormat idecimalFormat = new DecimalFormat("0.00");
    	CICarshipTaxDemandSchema ciCarShiptaxDemandSchema = new CICarshipTaxDemandSchema();
      	ciCarShiptaxDemandSchema.setDemandNo(XMLUtils.getChildNodeValue(node, "QUERY_SEQUENCE_NO"));
        
      	String iLicenseNo = blProposal.getBLPrpTitemCar().getArr(0).getLicenseNo().trim();
		PubTools pubTools = new PubTools();
		String strEcdemicVehicleFlag = pubTools.checkEcdemicVehicleFlag(iLicenseNo);
      	if((taxRelifFlag.equals("3") || taxRelifFlag.equals("2"))&& 
     	       "1".equals(strEcdemicVehicleFlag)){
      	
      		ciCarShiptaxDemandSchema.setPaidCertificate(paidCertificate);
      		ciCarShiptaxDemandSchema.setTaxComName(taxComName);
      	}else{
      		ciCarShiptaxDemandSchema.setPaidCertificate(XMLUtils.getChildNodeValue(node, "PAY_NO"));
      		ciCarShiptaxDemandSchema.setTaxComName(XMLUtils.getChildNodeValue(node, "DEPARTMENT"));
      	}
      	ciCarShiptaxDemandSchema.setTaxActual(XMLUtils.getChildNodeValue(node, "CURRENT_TAX"));
      	ciCarShiptaxDemandSchema.setPreviousPay(XMLUtils.getChildNodeValue(node, "FORMER_TAX"));
      	ciCarShiptaxDemandSchema.setLateFee(XMLUtils.getChildNodeValue(node, "LATE_FEE"));
      	ciCarShiptaxDemandSchema.setCarNumber(XMLUtils.getChildNodeValue(node, "VEHICLE_NUMBER"));
      	certificateDate = correctDate(XMLUtils.getChildNodeValue(node, "CERTIFICATE_DATE"));
      	if(certificateDate==null || certificateDate.equals("")){
      		certificateDate = blProposal.getBLPrpTcarshipTax().getArr(0).getCertificateDate();
      	}
      	completeKerbMass = XMLUtils.getChildNodeValue(node, "PO_WEIGHT");
      	if(completeKerbMass==null || completeKerbMass.equals("")){
      		completeKerbMass = blProposal.getBLPrpTcarshipTax().getArr(0).getCompleteKerbMass();
      	}
      	ciCarShiptaxDemandSchema.setCertificateDate(certificateDate);
      	ciCarShiptaxDemandSchema.setCompleteKerbMass(completeKerbMass);
      	strPayStartDate = XMLUtils.getChildNodeValue(node, "TAX_START_DATE");
      	strPayEndDate   = XMLUtils.getChildNodeValue(node, "TAX_END_DATE");
      	ciCarShiptaxDemandSchema.setPayStartDate(correctDate(strPayStartDate));
      	ciCarShiptaxDemandSchema.setPayEndDate(correctDate(strPayEndDate));
        
      	String currentDate=new ChgDate().getCurrentTime("yyyy-MM-dd");
        if(new UtiPower().checkCarShipTaxBJ(blProposal.getBLPrpTmain().getArr(0).getComCode(), currentDate)){
        	ciCarShiptaxDemandSchema.setOwnerName(XMLUtils.getChildNodeValue(node, "OWNER_NAME"));
        	ciCarShiptaxDemandSchema.setSumTax(XMLUtils.getChildNodeValue(node, "TAX_TOTAL"));
        }else{
        	double dblTaxActual = Double.parseDouble(ChgData.chgStrZero(ciCarShiptaxDemandSchema.getTaxActual()));
        	double dblPreviousPay = Double.parseDouble(ChgData.chgStrZero(ciCarShiptaxDemandSchema.getPreviousPay()));
        	double dblLateFee = Double.parseDouble(ChgData.chgStrZero(ciCarShiptaxDemandSchema.getLateFee()));
        	
    		double dblSumPayTax = Double.parseDouble(idecimalFormat.format(dblTaxActual + dblPreviousPay + dblLateFee));
    	    ciCarShiptaxDemandSchema.setSumTax("" + dblSumPayTax);
        }
        
	    String strPay_ID = XMLUtils.getChildNodeValue(node, "PAY_ID");
	    
	    if(taxRelifFlag.equals("3") && 
	       "1".equals(strEcdemicVehicleFlag) && 
	       strPay_ID!=null && strPay_ID.equals("01")){
	    	strPay_ID = "02";
	    }
	    
      	ciCarShiptaxDemandSchema.setTaxFlag(strPay_ID);
      	ciCarShiptaxDemandSchema.setCommissionTax(XMLUtils.getChildNodeValue(node, "IS_COMMISSION_TAX"));
    	blProposal.getBLCICarshipTaxDemand().setArr(ciCarShiptaxDemandSchema); 
    }
    
    protected void processVehicleTaxation(BLProposal blProposal, Node node) throws Exception{
    	
    	String strTaxTermTypeCode = XMLUtils.getChildNodeValue(node, "TaxTermTypeCode");
    	String strTaxConditionCode = taxRelifFlagDecoder(XMLUtils.getChildNodeValue(node, "TaxConditionCode"));
    	String strTaxRegistryNumber = XMLUtils.getChildNodeValue(node, "TaxRegistryNumber");
    	String strTaxPayerName = XMLUtils.getChildNodeValue(node, "TaxPayerName");
    	String strTaxPayerIdentificationCode = XMLUtils.getChildNodeValue(node, "TaxPayerIdentificationCode");
    	
    	UtiPower utiPower = new UtiPower();
    	String strAnnualTaxDue = "";
    	String strSumTaxDefault = "";
    	String strSumOverdue = "";
    	String strSumTax = "";
    	String strTaxDescription = "";
    	String strTaxAmount_Flag = "";
    	String strCalc_Tax_Flag = "";
    	String strDeclareDateJZ = "";
    	if(!utiPower.checkCarShipTaxJZ(blProposal.getBLPrpTmain().getArr(0).getComCode(), blProposal.getBLPrpTmain().getArr(0).getOperateDate())){
    		strAnnualTaxDue = XMLUtils.getChildNodeValue(node, "AnnualTaxDue");
        	strSumTaxDefault = XMLUtils.getChildNodeValue(node, "SumTaxDefault");
        	strSumOverdue = XMLUtils.getChildNodeValue(node, "SumOverdue");
        	strSumTax = XMLUtils.getChildNodeValue(node, "SumTax");
        	strTaxDescription = XMLUtils.getChildNodeValue(node, "TaxDescription");
    	}else{
    		Node taxAmount = XMLUtils.getChildNodeByTagName(node, "TaxAmount");
    		strCalc_Tax_Flag = XMLUtils.getChildNodeValue(node, "Calc_Tax_Flag");
    		
    		if(taxAmount!=null){
    		strTaxAmount_Flag = XMLUtils.getChildNodeValue(taxAmount, "TaxAmount_Flag");
    		strAnnualTaxDue = XMLUtils.getChildNodeValue(taxAmount, "AnnualTaxDue");
        	strSumTaxDefault = XMLUtils.getChildNodeValue(taxAmount, "SumTaxDefault");
        	strSumOverdue = XMLUtils.getChildNodeValue(taxAmount, "SumOverdue");
        	strSumTax = XMLUtils.getChildNodeValue(taxAmount, "SumTax");
    		}
    		
        	strDeclareDateJZ = XMLUtils.getChildNodeValue(node, "DeclareDate");
    	}
    	
    	
    	CICarShipTaxQGDemandSchema ciCarShipTaxQGDemandSchema = new CICarShipTaxQGDemandSchema();
    	ciCarShipTaxQGDemandSchema.setTaxTermTypeCode(strTaxTermTypeCode);
    	ciCarShipTaxQGDemandSchema.setTaxConditionCode(strTaxConditionCode);
    	ciCarShipTaxQGDemandSchema.setTaxRegistryNumber(strTaxRegistryNumber);
    	ciCarShipTaxQGDemandSchema.setTaxPayerName(strTaxPayerName);
    	ciCarShipTaxQGDemandSchema.setTaxPayerIdentificationCode(strTaxPayerIdentificationCode);
    	ciCarShipTaxQGDemandSchema.setAnnualTaxDue(strAnnualTaxDue);
    	ciCarShipTaxQGDemandSchema.setSumTaxDefault(strSumTaxDefault);
    	ciCarShipTaxQGDemandSchema.setSumOverdue(strSumOverdue);
    	ciCarShipTaxQGDemandSchema.setSumTax(strSumTax);
    	ciCarShipTaxQGDemandSchema.setTaxDescription(strTaxDescription);
    	
    	ciCarShipTaxQGDemandSchema.setTaxAmountFlag(strTaxAmount_Flag);
    	ciCarShipTaxQGDemandSchema.setCalcTaxFlag(strCalc_Tax_Flag);
    	
    	Node currentTaxDue = XMLUtils.getChildNodeByTagName(node, "CurrentTaxDue");
    	
    	if(!utiPower.checkCarShipTaxJZ(blProposal.getBLPrpTmain().getArr(0).getComCode(), blProposal.getBLPrpTmain().getArr(0).getOperateDate())){
    		processAnnualTax(ciCarShipTaxQGDemandSchema, currentTaxDue);
    	}else{
    		
    		if(currentTaxDue != null){
    		processAnnualTaxJZ(ciCarShipTaxQGDemandSchema, currentTaxDue);
    		}
    		
    		ciCarShipTaxQGDemandSchema.setDeclareDate(strDeclareDateJZ);
    	}
    	
    	ciCarShipTaxQGDemandSchema.setDemandNo(blProposal.getBLCIInsureDemand().getArr(0).getDemandNo());
    	ciCarShipTaxQGDemandSchema.setTaxType("0");
    	ciCarShipTaxQGDemandSchema.setSerialNo("1");
    	blProposal.getBLCICarShipTaxQGDemand().setArr(ciCarShipTaxQGDemandSchema);
    	
    	Node[] delinquentTaxDue = XMLUtils.getChildNodesByTagName(node, "DelinquentTaxDue");
    	for(int i=0; i<delinquentTaxDue.length; i++){
    		CICarShipTaxQGDemandSchema ciCarShipTaxQGDemandSchemaTemp = new CICarShipTaxQGDemandSchema();
    		ciCarShipTaxQGDemandSchemaTemp.setTaxTermTypeCode(strTaxTermTypeCode);
    		ciCarShipTaxQGDemandSchemaTemp.setTaxConditionCode(strTaxConditionCode);
    		ciCarShipTaxQGDemandSchemaTemp.setTaxRegistryNumber(strTaxRegistryNumber);
    		ciCarShipTaxQGDemandSchemaTemp.setTaxPayerName(strTaxPayerName);
    		ciCarShipTaxQGDemandSchemaTemp.setTaxPayerIdentificationCode(strTaxPayerIdentificationCode);
    		ciCarShipTaxQGDemandSchemaTemp.setAnnualTaxDue(strAnnualTaxDue);
    		ciCarShipTaxQGDemandSchemaTemp.setSumTaxDefault(strSumTaxDefault);
        	ciCarShipTaxQGDemandSchemaTemp.setSumOverdue(strSumOverdue);
        	ciCarShipTaxQGDemandSchemaTemp.setSumTax(strSumTax);
        	ciCarShipTaxQGDemandSchemaTemp.setTaxDescription(strTaxDescription);
        	
        	if(!utiPower.checkCarShipTaxJZ(blProposal.getBLPrpTmain().getArr(0).getComCode(), blProposal.getBLPrpTmain().getArr(0).getOperateDate())){
        		processAnnualTax(ciCarShipTaxQGDemandSchemaTemp, delinquentTaxDue[i]);
        	}else{
        		
        		if(delinquentTaxDue[i] != null){
        		processAnnualTaxJZ(ciCarShipTaxQGDemandSchemaTemp, delinquentTaxDue[i]);
        		}
        		
        		ciCarShipTaxQGDemandSchema.setDeclareDate(strDeclareDateJZ);
        	}
        	
        	ciCarShipTaxQGDemandSchemaTemp.setDemandNo(blProposal.getBLCIInsureDemand().getArr(0).getDemandNo());
        	ciCarShipTaxQGDemandSchemaTemp.setTaxType("1");
        	ciCarShipTaxQGDemandSchemaTemp.setSerialNo((i+2)+"");
        	blProposal.getBLCICarShipTaxQGDemand().setArr(ciCarShipTaxQGDemandSchemaTemp);
    	}
    }
    
    protected void processAnnualTax(CICarShipTaxQGDemandSchema ciCarShipTaxQGDemandSchema, Node node){
    	String strTaxLocationCode = XMLUtils.getChildNodeValue(node, "TaxLocationCode");
    	String strTaxStartDate = correctDate(XMLUtils.getChildNodeValue(node, "TaxStartDate"));
    	String strTaxEndDate = correctDate(XMLUtils.getChildNodeValue(node, "TaxEndDate"));
    	String strDeclareDate = correctDate(XMLUtils.getChildNodeValue(node, "DeclareDate"));
    	String strTaxDepartmentCode = XMLUtils.getChildNodeValue(node, "TaxDepartmentCode");
    	String strTaxDepartment = XMLUtils.getChildNodeValue(node, "TaxDepartment");
    	String strTaxDocumentNumber = XMLUtils.getChildNodeValue(node, "TaxDocumentNumber");
    	String strAnnualTaxAmount = XMLUtils.getChildNodeValue(node, "AnnualTaxAmount");
    	
    	Node taxRate = XMLUtils.getChildNodeByTagName(node, "TaxRate");
    	String strAppliedArea = XMLUtils.getChildNodeValue(taxRate, "AppliedArea");
    	String strTaxRateIdentifier = XMLUtils.getChildNodeValue(taxRate, "TaxRateIdentifier");
    	String strTaxItemDetailCode = XMLUtils.getChildNodeValue(taxRate, "TaxItemDetailCode");
    	String strTaxUnitTypeCode = XMLUtils.getChildNodeValue(taxRate, "TaxUnitTypeCode");
    	String strUnitRate = XMLUtils.getChildNodeValue(taxRate, "UnitRate");
    	String strTaxRateStartDate = correctDate(XMLUtils.getChildNodeValue(taxRate, "TaxRateStartDate"));
    	String strTaxRateEndDate = correctDate(XMLUtils.getChildNodeValue(taxRate, "TaxRateEndDate"));
    	
    	Node derate = XMLUtils.getChildNodeByTagName(node, "Derate");
    	String strDeductionDueCode = relifReasonDecoder(XMLUtils.getChildNodeValue(derate, "DeductionDueCode"));
    	String strDeductionDueType = baseTaxationdecoder(XMLUtils.getChildNodeValue(derate, "DeductionDueType"));
    	String strDeductionDueProportion = XMLUtils.getChildNodeValue(derate, "DeductionDueProportion");
        strDeductionDueProportion=""+((Double.parseDouble(ChgData.chgStrZero(strDeductionDueProportion)))*100);
        String strDeduction = XMLUtils.getChildNodeValue(derate, "Deduction");
    	String strDeductionDocumentNumber = XMLUtils.getChildNodeValue(derate, "DeductionDocumentNumber");
    	String strTaxDepartmentCode1 = XMLUtils.getChildNodeValue(derate, "TaxDepartmentCode");
    	String strTaxDepartment1 = XMLUtils.getChildNodeValue(derate, "TaxDepartment");
    	
    	String strTaxDue = XMLUtils.getChildNodeValue(node, "TaxDue");
    	String strExceedDate = correctDate(XMLUtils.getChildNodeValue(node, "ExceedDate"));
    	String strExceedDaysCount = XMLUtils.getChildNodeValue(node, "ExceedDaysCount");
    	String strOverDue = XMLUtils.getChildNodeValue(node, "OverDue");
    	String strTotalAmount = XMLUtils.getChildNodeValue(node, "TotalAmount");
    	
    	ciCarShipTaxQGDemandSchema.setTaxLocationCode(strTaxLocationCode);
        if(strTaxStartDate!=null&&!strTaxStartDate.equals(""))
    	ciCarShipTaxQGDemandSchema.setTaxStartDate(strTaxStartDate);
        if(strTaxEndDate!=null&&!strTaxEndDate.equals(""))
    	ciCarShipTaxQGDemandSchema.setTaxEndDate(strTaxEndDate);
        if(strDeclareDate!=null&&!strDeclareDate.equals(""))
    	ciCarShipTaxQGDemandSchema.setDeclareDate(strDeclareDate);
    	ciCarShipTaxQGDemandSchema.setTaxDepartmentCode(strTaxDepartmentCode);
    	ciCarShipTaxQGDemandSchema.setTaxDepartment(strTaxDepartment);
    	ciCarShipTaxQGDemandSchema.setTaxDocumentNumber(strTaxDocumentNumber);
    	ciCarShipTaxQGDemandSchema.setAnnualTaxAmount(strAnnualTaxAmount);
    	
    	ciCarShipTaxQGDemandSchema.setAppliedArea(strAppliedArea);
    	ciCarShipTaxQGDemandSchema.setTaxRateIdentifier(strTaxRateIdentifier);
    	ciCarShipTaxQGDemandSchema.setTaxItemDetailCode(strTaxItemDetailCode);
    	ciCarShipTaxQGDemandSchema.setTaxUnitTypeCode(strTaxUnitTypeCode);
    	ciCarShipTaxQGDemandSchema.setUnitRate(strUnitRate);
        if(strTaxRateStartDate!=null&&!strTaxRateStartDate.equals(""))
    	   ciCarShipTaxQGDemandSchema.setTaxRateStartDate(strTaxRateStartDate);
        if(strTaxRateEndDate!=null&&!strTaxRateEndDate.equals(""))
    	   ciCarShipTaxQGDemandSchema.setTaxRateEndDate(strTaxRateEndDate);
    	
    	ciCarShipTaxQGDemandSchema.setDeductionDueCode(strDeductionDueCode);
    	ciCarShipTaxQGDemandSchema.setDeductionDueType(strDeductionDueType);
    	ciCarShipTaxQGDemandSchema.setDeductionDueProportion(strDeductionDueProportion);
    	ciCarShipTaxQGDemandSchema.setDeduction(strDeduction);
    	ciCarShipTaxQGDemandSchema.setDeductionDocumentNumber(strDeductionDocumentNumber);
    	ciCarShipTaxQGDemandSchema.setDeductionDepartmentCode(strTaxDepartmentCode1);
    	ciCarShipTaxQGDemandSchema.setDeductionDepartment(strTaxDepartment1);
    	
    	ciCarShipTaxQGDemandSchema.setTaxDue(strTaxDue);
        if(strExceedDate!=null&&!strExceedDate.equals(""))
    	   ciCarShipTaxQGDemandSchema.setExceedDate(strExceedDate);
    	ciCarShipTaxQGDemandSchema.setExceedDaysCount(strExceedDaysCount);
    	ciCarShipTaxQGDemandSchema.setOverDue(strOverDue);
    	ciCarShipTaxQGDemandSchema.setTotalAmount(strTotalAmount);
    }
    
    
    protected void processAnnualTaxJZ(CICarShipTaxQGDemandSchema ciCarShipTaxQGDemandSchema, Node node){
    	String strTaxLocationCode = XMLUtils.getChildNodeValue(node, "TaxLocationCode");
    	String strTaxStartDate = correctDate(XMLUtils.getChildNodeValue(node, "TaxStartDate"));
    	String strTaxEndDate = correctDate(XMLUtils.getChildNodeValue(node, "TaxEndDate"));
    	
    	String strTaxUnitTypeCode = XMLUtils.getChildNodeValue(node, "TaxUnitTypeCode");
    	String strUnitRate =XMLUtils.getChildNodeValue(node, "UnitRate");
		
    	String strAnnualTaxAmount = XMLUtils.getChildNodeValue(node, "AnnualTaxAmount");
    	
    	Node paid = XMLUtils.getChildNodeByTagName(node, "Paid");
    	String strTaxDepartmentCode = XMLUtils.getChildNodeValue(paid, "TaxDepartmentCode");
    	String strTaxDepartment = XMLUtils.getChildNodeValue(paid, "TaxDepartment");
    	String strTaxDocumentNumber = XMLUtils.getChildNodeValue(paid, "TaxDocumentNumber");
    	
    	
    	Node derate = XMLUtils.getChildNodeByTagName(node, "Derate");
    	String strDeductionDueCode = relifReasonDecoder(XMLUtils.getChildNodeValue(derate, "DeductionDueCode"));
    	String strDeductionDueType = baseTaxationdecoder(XMLUtils.getChildNodeValue(derate, "DeductionDueType"));
    	String strDeductionDueProportion = XMLUtils.getChildNodeValue(derate, "DeductionDueProportion");
        strDeductionDueProportion=""+((Double.parseDouble(ChgData.chgStrZero(strDeductionDueProportion)))*100);
        String strDeduction = XMLUtils.getChildNodeValue(derate, "Deduction");
    	String strDeductionDocumentNumber = XMLUtils.getChildNodeValue(derate, "DeductionDocumentNumber");
    	String strTaxDepartmentCode1 = XMLUtils.getChildNodeValue(derate, "TaxDepartmentCode");
    	String strTaxDepartment1 = XMLUtils.getChildNodeValue(derate, "TaxDepartment");
    	
    	String strTaxDue = XMLUtils.getChildNodeValue(node, "TaxDue");
    	String strExceedDate = correctDate(XMLUtils.getChildNodeValue(node, "ExceedDate"));
    	String strExceedDaysCount = XMLUtils.getChildNodeValue(node, "ExceedDaysCount");
    	String strOverDue = XMLUtils.getChildNodeValue(node, "OverDue");
    	String strTotalAmount = XMLUtils.getChildNodeValue(node, "TotalAmount");
    	
    	ciCarShipTaxQGDemandSchema.setTaxLocationCode(strTaxLocationCode);
        if(strTaxStartDate!=null&&!strTaxStartDate.equals(""))
    	ciCarShipTaxQGDemandSchema.setTaxStartDate(strTaxStartDate);
        if(strTaxEndDate!=null&&!strTaxEndDate.equals(""))
    	ciCarShipTaxQGDemandSchema.setTaxEndDate(strTaxEndDate);
        ciCarShipTaxQGDemandSchema.setTaxDepartmentCode(strTaxDepartmentCode);
    	ciCarShipTaxQGDemandSchema.setTaxDepartment(strTaxDepartment);
    	ciCarShipTaxQGDemandSchema.setTaxDocumentNumber(strTaxDocumentNumber);
    	ciCarShipTaxQGDemandSchema.setAnnualTaxAmount(strAnnualTaxAmount);
    	ciCarShipTaxQGDemandSchema.setTaxUnitTypeCode(strTaxUnitTypeCode);
    	ciCarShipTaxQGDemandSchema.setUnitRate(strUnitRate);
    	
    	ciCarShipTaxQGDemandSchema.setDeductionDueCode(strDeductionDueCode);
    	ciCarShipTaxQGDemandSchema.setDeductionDueType(strDeductionDueType);
    	ciCarShipTaxQGDemandSchema.setDeductionDueProportion(strDeductionDueProportion);
    	ciCarShipTaxQGDemandSchema.setDeduction(strDeduction);
    	ciCarShipTaxQGDemandSchema.setDeductionDocumentNumber(strDeductionDocumentNumber);
    	ciCarShipTaxQGDemandSchema.setDeductionDepartmentCode(strTaxDepartmentCode1);
    	ciCarShipTaxQGDemandSchema.setDeductionDepartment(strTaxDepartment1);
    	
    	ciCarShipTaxQGDemandSchema.setTaxDue(strTaxDue);
        if(strExceedDate!=null&&!strExceedDate.equals(""))
    	   ciCarShipTaxQGDemandSchema.setExceedDate(strExceedDate);
    	ciCarShipTaxQGDemandSchema.setExceedDaysCount(strExceedDaysCount);
    	ciCarShipTaxQGDemandSchema.setOverDue(strOverDue);
    	ciCarShipTaxQGDemandSchema.setTotalAmount(strTotalAmount);
    }
    
    /**
     * XXXXX存请求日志
     * @param blProposal
     * @throws Exception
     */
    protected void saveRequestLog(BLProposal blProposal,DbPool dbpool) throws Exception 
    {
        
        UtiPower utiPower = new UtiPower();
        if(utiPower.checkCICarshipTaxSH(blProposal.getBLPrpTmain().getArr(0).getRiskCode(), blProposal.getBLPrpTmain().getArr(0).getComCode())
           || utiPower.checkCICarshipTaxBJ(blProposal.getBLPrpTmain().getArr(0).getRiskCode(), blProposal.getBLPrpTmain().getArr(0).getComCode()))
        {
           if( blProposal.getBLCICarshipTaxDemand().getSize()>0)
           {
        	   blProposal.getBLCICarshipTaxDemand().save(dbpool);
           }
        }
        else if(utiPower.checkCICarShipTaxQG(blProposal.getBLPrpTmain().getArr(0).getRiskCode(), blProposal.getBLPrpTmain().getArr(0).getComCode())
        		||new UtiPower().checkCarShipTaxJZ(blProposal.getBLPrpTmain().getArr(0).getComCode(),blProposal.getBLPrpTmain().getArr(0).getOperateDate())){
        	if( blProposal.getBLCICarShipTaxQGDemand().getSize()>0)
            {
         	   blProposal.getBLCICarShipTaxQGDemand().save(dbpool);
            }
        }
        
    }
    /**
     * 纠正日期格式
     * @param dateString 8个字符的日期
     * @return YYYY-MM-DD形式的日期
     */
    private String correctDate(String dateString) {
    	if (dateString == null || dateString.equals("")){
    		return "";
    	}
        if (dateString.length() < 8) {
            throw new IllegalArgumentException(dateString + "的日期格式不对，必须为大于8位的纯数字的字符串");
        }
        String result = dateString.substring(0, 4) + "-" + dateString.substring(4, 6) + "-"
                + dateString.substring(6, 8);
        return result;
    }
    
    
    /**
     * 纳税代码转换
     *
     * @param 行业平台纳税类型
     *           
     * @return 核心业务纳税类型
     */
    public String taxRelifFlagDecoder(String taxConditionCode){
        if("T".equals(taxConditionCode)){
            return "1";
        }else if("E".equals(taxConditionCode)){
            return "2";
        }else if("C".equals(taxConditionCode)){
            return "3";
        }else if("P".equals(taxConditionCode)){
            return "4";
        }else if("D".equals(taxConditionCode)){
            return "5";
        }else if("R".equals(taxConditionCode)){
            return "6";
        }else{
            return "";
        }
    }
    
    public String relifReasonDecoder(String deductionDueCode){
        if("F".equals(deductionDueCode)){
            return "01";
        }else if("A".equals(deductionDueCode)){
            return "02";
        }else if("P".equals(deductionDueCode)){
            return "03";
        }else if("C".equals(deductionDueCode)){
            return "04";
        }else if("D".equals(deductionDueCode)){
            return "05";
        }else if("O".equals(deductionDueCode)){
            return "06";
        
        }else if("E".equals(deductionDueCode)){
            return "07";
        
        }else{
            return "";
        }
        
    }
    
    public String baseTaxationdecoder(String deductionDueType){
        if("A".equals(deductionDueType)){
            return "1";
        }else if("P".equals(deductionDueType)){
            return "2";
        }else if("E".equals(deductionDueType)){
            return "3";
        }else{
            return "";
        }
    }
   
}
