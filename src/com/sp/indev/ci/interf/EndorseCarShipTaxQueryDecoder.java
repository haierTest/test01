package com.sp.indiv.ci.interf;

import java.text.DecimalFormat;

import org.w3c.dom.Node;

import com.sp.indiv.ci.schema.CICarShipTaxQGDemandSchema;
import com.sp.indiv.ci.schema.CICarShipTaxQGEndorseSchema;

import com.sp.prpall.blsvr.cb.BLPolicy;
import com.sp.prpall.blsvr.pg.BLEndorse;
import com.sp.sysframework.common.util.XMLUtils;
import com.sp.utility.UtiPower;
import com.sp.utility.database.DbPool;
import com.sp.utility.string.ChgData;

public class EndorseCarShipTaxQueryDecoder {


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

    public void addVehicleTaxation(BLPolicy blPolicy, Node node) throws Exception 
    {
        processVehicleTaxation(blPolicy,node);
    }
    protected void processVehicleTaxation(BLPolicy blPolicy, Node node) throws Exception{
        
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
    	String strDeclare_Status_IA ="";
    	String strDeclareDateJZ = "";
    	if(!utiPower.checkCarShipTaxJZ(blPolicy.getBLPrpCmain().getArr(0).getComCode(), blPolicy.getBLPrpCmain().getArr(0).getOperateDate())){
    		 strAnnualTaxDue = XMLUtils.getChildNodeValue(node, "AnnualTaxDue");
    	     strSumTaxDefault = XMLUtils.getChildNodeValue(node, "SumTaxDefault");
    	     strSumOverdue = XMLUtils.getChildNodeValue(node, "SumOverdue");
    	     strSumTax = XMLUtils.getChildNodeValue(node, "SumTax");
    	     strTaxDescription = XMLUtils.getChildNodeValue(node, "TaxDescription");
    	}else{
    		Node taxAmount = XMLUtils.getChildNodeByTagName(node, "TaxAmount");
    		strCalc_Tax_Flag = XMLUtils.getChildNodeValue(node, "Calc_Tax_Flag");
    		strDeclare_Status_IA = XMLUtils.getChildNodeValue(node, "Declare_Status_IA");
    		strTaxAmount_Flag = XMLUtils.getChildNodeValue(taxAmount, "TaxAmount_Flag");
    		strAnnualTaxDue = XMLUtils.getChildNodeValue(taxAmount, "AnnualTaxDue");
        	strSumTaxDefault = XMLUtils.getChildNodeValue(taxAmount, "SumTaxDefault");
        	strSumOverdue = XMLUtils.getChildNodeValue(taxAmount, "SumOverdue");
        	strSumTax = XMLUtils.getChildNodeValue(taxAmount, "SumTax");
        	strDeclareDateJZ = XMLUtils.getChildNodeValue(node, "DeclareDate");
    	}
       
        
        
        CICarShipTaxQGEndorseSchema ciCarShipTaxQGEndorseSchema = new CICarShipTaxQGEndorseSchema();
        ciCarShipTaxQGEndorseSchema.setTaxTermTypeCode(strTaxTermTypeCode);
        ciCarShipTaxQGEndorseSchema.setTaxConditionCode(strTaxConditionCode);
        ciCarShipTaxQGEndorseSchema.setTaxRegistryNumber(strTaxRegistryNumber);
        ciCarShipTaxQGEndorseSchema.setTaxPayerName(strTaxPayerName);
        ciCarShipTaxQGEndorseSchema.setTaxPayerIdentificationCode(strTaxPayerIdentificationCode);
        ciCarShipTaxQGEndorseSchema.setAnnualTaxDue(strAnnualTaxDue);
        ciCarShipTaxQGEndorseSchema.setSumTaxDefault(strSumTaxDefault);
        ciCarShipTaxQGEndorseSchema.setSumOverdue(strSumOverdue);
        ciCarShipTaxQGEndorseSchema.setSumTax(strSumTax);
        ciCarShipTaxQGEndorseSchema.setTaxDescription(strTaxDescription);
        
        ciCarShipTaxQGEndorseSchema.setTaxAmountFlag(strTaxAmount_Flag);
        ciCarShipTaxQGEndorseSchema.setCalcTaxFlag(strCalc_Tax_Flag);
        ciCarShipTaxQGEndorseSchema.setDeclareStatusIA(strDeclare_Status_IA);
    	
        Node currentTaxDue = XMLUtils.getChildNodeByTagName(node, "CurrentTaxDue");
        
        if(!utiPower.checkCarShipTaxJZ(blPolicy.getBLPrpCmain().getArr(0).getComCode(), blPolicy.getBLPrpCmain().getArr(0).getOperateDate())){
        	processAnnualTax(ciCarShipTaxQGEndorseSchema, currentTaxDue);
        }else{
        	processAnnualTaxJZ(ciCarShipTaxQGEndorseSchema, currentTaxDue);
        	ciCarShipTaxQGEndorseSchema.setDeclareDate(strDeclareDateJZ);
        }
        
        ciCarShipTaxQGEndorseSchema.setDemandNo(blPolicy.getBLCIEndorValid().getArr(0).getDemandNo());
        ciCarShipTaxQGEndorseSchema.setTaxType("0");
        ciCarShipTaxQGEndorseSchema.setSerialNo("1");
        blPolicy.getBLCICarShipTaxQGEndorse().setArr(ciCarShipTaxQGEndorseSchema);
        
        Node[] delinquentTaxDue = XMLUtils.getChildNodesByTagName(node, "DelinquentTaxDue");
        for(int i=0; i<delinquentTaxDue.length; i++){
            CICarShipTaxQGEndorseSchema ciCarShipTaxQGDemandSchemaTemp = new CICarShipTaxQGEndorseSchema();
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
          
            if(!utiPower.checkCarShipTaxJZ(blPolicy.getBLPrpCmain().getArr(0).getComCode(), blPolicy.getBLPrpCmain().getArr(0).getOperateDate())){
            	processAnnualTax(ciCarShipTaxQGDemandSchemaTemp, delinquentTaxDue[i]);
            }else{
            	processAnnualTaxJZ(ciCarShipTaxQGDemandSchemaTemp, delinquentTaxDue[i]);
            	ciCarShipTaxQGEndorseSchema.setDeclareDate(strDeclareDateJZ);
            }
            
            ciCarShipTaxQGDemandSchemaTemp.setDemandNo(blPolicy.getBLCIEndorValid().getArr(0).getDemandNo());
            ciCarShipTaxQGDemandSchemaTemp.setTaxType("1");
            ciCarShipTaxQGDemandSchemaTemp.setSerialNo(i+2+"");
            blPolicy.getBLCICarShipTaxQGEndorse().setArr(ciCarShipTaxQGDemandSchemaTemp);
        }
    }
    
    protected void processAnnualTax(CICarShipTaxQGEndorseSchema ciCarShipTaxQGEndorseSchema, Node node){
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
        
        ciCarShipTaxQGEndorseSchema.setTaxLocationCode(strTaxLocationCode);
        if(strTaxStartDate!=null&&!strTaxStartDate.equals(""))
            ciCarShipTaxQGEndorseSchema.setTaxStartDate(strTaxStartDate);
        if(strTaxEndDate!=null&&!strTaxEndDate.equals(""))
            ciCarShipTaxQGEndorseSchema.setTaxEndDate(strTaxEndDate);
        if(strDeclareDate!=null&&!strDeclareDate.equals(""))
            ciCarShipTaxQGEndorseSchema.setDeclareDate(strDeclareDate);
        ciCarShipTaxQGEndorseSchema.setTaxDepartmentCode(strTaxDepartmentCode);
        ciCarShipTaxQGEndorseSchema.setTaxDepartment(strTaxDepartment);
        ciCarShipTaxQGEndorseSchema.setTaxDocumentNumber(strTaxDocumentNumber);
        ciCarShipTaxQGEndorseSchema.setAnnualTaxAmount(strAnnualTaxAmount);
        
        ciCarShipTaxQGEndorseSchema.setAppliedArea(strAppliedArea);
        ciCarShipTaxQGEndorseSchema.setTaxRateIdentifier(strTaxRateIdentifier);
        ciCarShipTaxQGEndorseSchema.setTaxItemDetailCode(strTaxItemDetailCode);
        ciCarShipTaxQGEndorseSchema.setTaxUnitTypeCode(strTaxUnitTypeCode);
        ciCarShipTaxQGEndorseSchema.setUnitRate(strUnitRate);
        if(strTaxRateStartDate!=null&&!strTaxRateStartDate.equals(""))
            ciCarShipTaxQGEndorseSchema.setTaxRateStartDate(strTaxRateStartDate);
        if(strTaxRateEndDate!=null&&!strTaxRateEndDate.equals(""))
            ciCarShipTaxQGEndorseSchema.setTaxRateEndDate(strTaxRateEndDate);
        
        ciCarShipTaxQGEndorseSchema.setDeductionDueCode(strDeductionDueCode);
        ciCarShipTaxQGEndorseSchema.setDeductionDueType(strDeductionDueType);
        ciCarShipTaxQGEndorseSchema.setDeductionDueProportion(strDeductionDueProportion);
        ciCarShipTaxQGEndorseSchema.setDeduction(strDeduction);
        ciCarShipTaxQGEndorseSchema.setDeductionDocumentNumber(strDeductionDocumentNumber);
        ciCarShipTaxQGEndorseSchema.setDeductionDepartmentCode(strTaxDepartmentCode1);
        ciCarShipTaxQGEndorseSchema.setDeductionDepartment(strTaxDepartment1);
        
        ciCarShipTaxQGEndorseSchema.setTaxDue(strTaxDue);
        if(strExceedDate!=null&&!strExceedDate.equals(""))
            ciCarShipTaxQGEndorseSchema.setExceedDate(strExceedDate);
        ciCarShipTaxQGEndorseSchema.setExceedDaysCount(strExceedDaysCount);
        ciCarShipTaxQGEndorseSchema.setOverDue(strOverDue);
        ciCarShipTaxQGEndorseSchema.setTotalAmount(strTotalAmount);
    }
    
    protected void processAnnualTaxJZ(CICarShipTaxQGEndorseSchema ciCarShipTaxQGEndorseSchema, Node node){
    	String strTaxLocationCode = XMLUtils.getChildNodeValue(node, "TaxLocationCode");
    	String strTaxStartDate = correctDate(XMLUtils.getChildNodeValue(node, "TaxStartDate"));
    	String strTaxEndDate = correctDate(XMLUtils.getChildNodeValue(node, "TaxEndDate"));
    	
    	
    	
    	String strTaxUnitTypeCode = XMLUtils.getChildNodeValue(node, "TaxUnitTypeCode");
    	String strUnitRate = XMLUtils.getChildNodeValue(node, "UnitRate");
    	
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
    	
    	ciCarShipTaxQGEndorseSchema.setTaxLocationCode(strTaxLocationCode);
        if(strTaxStartDate!=null&&!strTaxStartDate.equals(""))
        ciCarShipTaxQGEndorseSchema.setTaxStartDate(strTaxStartDate);
        if(strTaxEndDate!=null&&!strTaxEndDate.equals(""))
        ciCarShipTaxQGEndorseSchema.setTaxEndDate(strTaxEndDate);
        ciCarShipTaxQGEndorseSchema.setTaxDepartmentCode(strTaxDepartmentCode);
        ciCarShipTaxQGEndorseSchema.setTaxDepartment(strTaxDepartment);
        ciCarShipTaxQGEndorseSchema.setTaxDocumentNumber(strTaxDocumentNumber);
        ciCarShipTaxQGEndorseSchema.setAnnualTaxAmount(strAnnualTaxAmount);
        ciCarShipTaxQGEndorseSchema.setTaxUnitTypeCode(strTaxUnitTypeCode);
        ciCarShipTaxQGEndorseSchema.setUnitRate(strUnitRate);
    	
        ciCarShipTaxQGEndorseSchema.setDeductionDueCode(strDeductionDueCode);
        ciCarShipTaxQGEndorseSchema.setDeductionDueType(strDeductionDueType);
        ciCarShipTaxQGEndorseSchema.setDeductionDueProportion(strDeductionDueProportion);
        ciCarShipTaxQGEndorseSchema.setDeduction(strDeduction);
        ciCarShipTaxQGEndorseSchema.setDeductionDocumentNumber(strDeductionDocumentNumber);
        ciCarShipTaxQGEndorseSchema.setDeductionDepartmentCode(strTaxDepartmentCode1);
        ciCarShipTaxQGEndorseSchema.setDeductionDepartment(strTaxDepartment1);
    	
        ciCarShipTaxQGEndorseSchema.setTaxDue(strTaxDue);
        if(strExceedDate!=null&&!strExceedDate.equals(""))
        ciCarShipTaxQGEndorseSchema.setExceedDate(strExceedDate);
        ciCarShipTaxQGEndorseSchema.setExceedDaysCount(strExceedDaysCount);
        ciCarShipTaxQGEndorseSchema.setOverDue(strOverDue);
        ciCarShipTaxQGEndorseSchema.setTotalAmount(strTotalAmount);
    }
    
    /**
     * XXXXX存请求日志
     * @param blProposal
     * @throws Exception
     */
    protected void saveRequestLog(BLPolicy blPolicy,DbPool dbpool) throws Exception 
    {
        
        UtiPower utiPower = new UtiPower();
        if(utiPower.checkCICarShipTaxQG(blPolicy.getBLPrpCmain().getArr(0).getRiskCode(), blPolicy.getBLPrpCmain().getArr(0).getComCode())){
            if( blPolicy.getBLCICarShipTaxQGDemand().getSize()>0)
            {
                blPolicy.getBLCICarShipTaxQGDemand().save(dbpool);
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
    
    
    public void addVehicleTaxation(BLEndorse blEndorse, Node node) throws Exception 
    {
        processVehicleTaxation(blEndorse,node);
    }
    protected void processVehicleTaxation(BLEndorse blEndorse, Node node) throws Exception{
        
        String strTaxTermTypeCode = XMLUtils.getChildNodeValue(node, "TaxTermTypeCode");
        String strTaxConditionCode = taxRelifFlagDecoder(XMLUtils.getChildNodeValue(node, "TaxConditionCode"));
        String strTaxRegistryNumber = XMLUtils.getChildNodeValue(node, "TaxRegistryNumber");
        String strTaxPayerName = XMLUtils.getChildNodeValue(node, "TaxPayerName");
        String strTaxPayerIdentificationCode = XMLUtils.getChildNodeValue(node, "TaxPayerIdentificationCode");

    	String strAnnualTaxDue = "";
    	String strSumTaxDefault = "";
    	String strSumOverdue = "";
    	String strSumTax = "";
    	String strTaxDescription = "";
    	String strTaxAmount_Flag = "";
    	String strCalc_Tax_Flag = "";
    	String strDeclare_Status_IA ="";
    	String strDeclareDateJZ = "";
    	Node taxAmount = XMLUtils.getChildNodeByTagName(node, "TaxAmount");
    	strCalc_Tax_Flag = XMLUtils.getChildNodeValue(node, "Calc_Tax_Flag");
    	strDeclare_Status_IA = XMLUtils.getChildNodeValue(node, "Declare_Status_IA");
    	strTaxAmount_Flag = XMLUtils.getChildNodeValue(taxAmount, "TaxAmount_Flag");
    	strAnnualTaxDue = XMLUtils.getChildNodeValue(taxAmount, "AnnualTaxDue");
    	strSumTaxDefault = XMLUtils.getChildNodeValue(taxAmount, "SumTaxDefault");
    	strSumOverdue = XMLUtils.getChildNodeValue(taxAmount, "SumOverdue");
    	strSumTax = XMLUtils.getChildNodeValue(taxAmount, "SumTax");
    	strDeclareDateJZ = XMLUtils.getChildNodeValue(node, "DeclareDate");
        
        CICarShipTaxQGEndorseSchema ciCarShipTaxQGEndorseSchema = new CICarShipTaxQGEndorseSchema();
        ciCarShipTaxQGEndorseSchema.setTaxTermTypeCode(strTaxTermTypeCode);
        ciCarShipTaxQGEndorseSchema.setTaxConditionCode(strTaxConditionCode);
        ciCarShipTaxQGEndorseSchema.setTaxRegistryNumber(strTaxRegistryNumber);
        ciCarShipTaxQGEndorseSchema.setTaxPayerName(strTaxPayerName);
        ciCarShipTaxQGEndorseSchema.setTaxPayerIdentificationCode(strTaxPayerIdentificationCode);
        ciCarShipTaxQGEndorseSchema.setAnnualTaxDue(strAnnualTaxDue);
        ciCarShipTaxQGEndorseSchema.setSumTaxDefault(strSumTaxDefault);
        ciCarShipTaxQGEndorseSchema.setSumOverdue(strSumOverdue);
        ciCarShipTaxQGEndorseSchema.setSumTax(strSumTax);
        ciCarShipTaxQGEndorseSchema.setTaxDescription(strTaxDescription);
        ciCarShipTaxQGEndorseSchema.setTaxAmountFlag(strTaxAmount_Flag);
        ciCarShipTaxQGEndorseSchema.setCalcTaxFlag(strCalc_Tax_Flag);
        ciCarShipTaxQGEndorseSchema.setDeclareStatusIA(strDeclare_Status_IA);
        Node currentTaxDue = XMLUtils.getChildNodeByTagName(node, "CurrentTaxDue");
        processAnnualTaxJZ(ciCarShipTaxQGEndorseSchema, currentTaxDue);
        ciCarShipTaxQGEndorseSchema.setDeclareDate(strDeclareDateJZ);
        ciCarShipTaxQGEndorseSchema.setDemandNo(blEndorse.getBLCIEndorValid().getArr(0).getDemandNo());
        ciCarShipTaxQGEndorseSchema.setTaxType("0");
        ciCarShipTaxQGEndorseSchema.setSerialNo("1");
        blEndorse.getBLCICarShipTaxQGEndorse().setArr(ciCarShipTaxQGEndorseSchema);
        
        Node[] delinquentTaxDue = XMLUtils.getChildNodesByTagName(node, "DelinquentTaxDue");
        for(int i=0; i<delinquentTaxDue.length; i++){
            CICarShipTaxQGEndorseSchema ciCarShipTaxQGDemandSchemaTemp = new CICarShipTaxQGEndorseSchema();
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
            processAnnualTaxJZ(ciCarShipTaxQGDemandSchemaTemp, delinquentTaxDue[i]);
            ciCarShipTaxQGEndorseSchema.setDeclareDate(strDeclareDateJZ);
            ciCarShipTaxQGDemandSchemaTemp.setDemandNo(blEndorse.getBLCIEndorValid().getArr(0).getDemandNo());
            ciCarShipTaxQGDemandSchemaTemp.setTaxType("1");
            ciCarShipTaxQGDemandSchemaTemp.setSerialNo(i+2+"");
            blEndorse.getBLCICarShipTaxQGEndorse().setArr(ciCarShipTaxQGDemandSchemaTemp);
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
