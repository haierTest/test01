package com.sp.indiv.ci.schema;

import java.io.Serializable;

/**
 * 定义CICarShipTaxQGEndorse-全国车船税基本类型批改查询表的数据传输对象类
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>@createdate 2009-04-09</p>
 * @author Zhouxianli(JavaTools v1.0)
 * @updateauthor yangkun(JavaTools v1.1 - v1.2)
 * @lastversion v1.2
 */
public class CICarShipTaxQGEndorseSchema implements Serializable{
    
    private String EndorseNo = "";
    
    private String DemandNo = "";
    
    private String PolicyNo = "";
    
    private String TaxType = "";
    
    private String SerialNo = "";
    
    private String TaxTermTypeCode = "";
    
    private String TaxConditionCode = "";
    
    private String TaxRegistryNumber = "";
    
    private String TaxPayerName = "";
    
    private String TaxPayerIdentificationCode = "";
    
    private String TaxLocationCode = "";
    
    private String TaxStartDate = "";
    
    private String TaxEndDate = "";
    
    private String DeclareDate = "";
    
    private String TaxDepartmentCode = "";
    
    private String TaxDepartment = "";
    
    private String TaxDocumentNumber = "";
    
    private String AnnualTaxAmount = "";
    
    private String AppliedArea = "";
    
    private String TaxRateIdentifier = "";
    
    private String TaxItemDetailCode = "";
    
    private String TaxUnitTypeCode = "";
    
    private String UnitRate = "";
    
    private String TaxRateStartDate = "";
    
    private String TaxRateEndDate = "";
    
    private String DeductionDueCode = "";
    
    private String DeductionDueType = "";
    
    private String DeductionDueProportion = "";
    
    private String Deduction = "";
    
    private String DeductionDocumentNumber = "";
    
    private String DeductionDepartmentCode = "";
    
    private String DeductionDepartment = "";
    
    private String TaxDue = "";
    
    private String ExceedDate = "";
    
    private String ExceedDaysCount = "";
    
    private String OverDue = "";
    
    private String TotalAmount = "";
    
    private String AnnualTaxDue = "";
    
    private String SumTaxDefault = "";
    
    private String SumOverdue = "";
    
    private String SumTax = "";
    
    private String TaxDescription = "";
	
    
    private String TaxAmountFlag = "";
    
    private String DeclareStatusIA = "";
    
    private String CalcTaxFlag = "";
    
    private String TaxPrintNo = "";
	

    /**
     * 构造函数
     */       
    public CICarShipTaxQGEndorseSchema(){
    }

    /**
     * 设置属性批单号
     * @param EndorseNo 批单号
     */
    public void setEndorseNo(String EndorseNo){
        this.EndorseNo = EndorseNo;
    }

    /**
     * 获取属性批单号
     * @return 批单号
     */
    public String getEndorseNo(){
        return EndorseNo;
    }

    /**
     * 设置属性批改查询码
     * @param DemandNo 批改查询码
     */
    public void setDemandNo(String DemandNo){
        this.DemandNo = DemandNo;
    }

    /**
     * 获取属性批改查询码
     * @return 批改查询码
     */
    public String getDemandNo(){
        return DemandNo;
    }

    /**
     * 设置属性XX号
     * @param PolicyNo XX号
     */
    public void setPolicyNo(String PolicyNo){
        this.PolicyNo = PolicyNo;
    }

    /**
     * 获取属性XX号
     * @return XX号
     */
    public String getPolicyNo(){
        return PolicyNo;
    }

    /**
     * 设置属性车船税类型(0是当年应缴,1是往年补缴)
     * @param TaxType 车船税类型(0是当年应缴,1是往年补缴)
     */
    public void setTaxType(String TaxType){
        this.TaxType = TaxType;
    }

    /**
     * 获取属性车船税类型(0是当年应缴,1是往年补缴)
     * @return 车船税类型(0是当年应缴,1是往年补缴)
     */
    public String getTaxType(){
        return TaxType;
    }

    /**
     * 设置属性序号
     * @param SerialNo 序号
     */
    public void setSerialNo(String SerialNo){
        this.SerialNo = SerialNo;
    }

    /**
     * 获取属性序号
     * @return 序号
     */
    public String getSerialNo(){
        return SerialNo;
    }

    /**
     * 设置属性税种类型代码
     * @param TaxTermTypeCode 税种类型代码
     */
    public void setTaxTermTypeCode(String TaxTermTypeCode){
        this.TaxTermTypeCode = TaxTermTypeCode;
    }

    /**
     * 获取属性税种类型代码
     * @return 税种类型代码
     */
    public String getTaxTermTypeCode(){
        return TaxTermTypeCode;
    }

    /**
     * 设置属性纳税类型代码
     * @param TaxConditionCode 纳税类型代码
     */
    public void setTaxConditionCode(String TaxConditionCode){
        this.TaxConditionCode = TaxConditionCode;
    }

    /**
     * 获取属性纳税类型代码
     * @return 纳税类型代码
     */
    public String getTaxConditionCode(){
        return TaxConditionCode;
    }

    /**
     * 设置属性税务登记证号
     * @param TaxRegistryNumber 税务登记证号
     */
    public void setTaxRegistryNumber(String TaxRegistryNumber){
        this.TaxRegistryNumber = TaxRegistryNumber;
    }

    /**
     * 获取属性税务登记证号
     * @return 税务登记证号
     */
    public String getTaxRegistryNumber(){
        return TaxRegistryNumber;
    }

    /**
     * 设置属性纳税人名称
     * @param TaxPayerName 纳税人名称
     */
    public void setTaxPayerName(String TaxPayerName){
        this.TaxPayerName = TaxPayerName;
    }

    /**
     * 获取属性纳税人名称
     * @return 纳税人名称
     */
    public String getTaxPayerName(){
        return TaxPayerName;
    }

    /**
     * 设置属性纳税人识别号
     * @param TaxPayerIdentificationCode 纳税人识别号
     */
    public void setTaxPayerIdentificationCode(String TaxPayerIdentificationCode){
        this.TaxPayerIdentificationCode = TaxPayerIdentificationCode;
    }

    /**
     * 获取属性纳税人识别号
     * @return 纳税人识别号
     */
    public String getTaxPayerIdentificationCode(){
        return TaxPayerIdentificationCode;
    }

    /**
     * 设置属性纳税地区代码(国标地区代码)
     * @param TaxLocationCode 纳税地区代码(国标地区代码)
     */
    public void setTaxLocationCode(String TaxLocationCode){
        this.TaxLocationCode = TaxLocationCode;
    }

    /**
     * 获取属性纳税地区代码(国标地区代码)
     * @return 纳税地区代码(国标地区代码)
     */
    public String getTaxLocationCode(){
        return TaxLocationCode;
    }

    /**
     * 设置属性税款所属始期
     * @param TaxStartDate 税款所属始期
     */
    public void setTaxStartDate(String TaxStartDate){
     if  (TaxStartDate == null ||TaxStartDate.equals("")) 
     {
        this.TaxStartDate = "";
     }
     else
     {
        if(TaxStartDate.length()>9)
        this.TaxStartDate = TaxStartDate.substring(0,10);
     }
    }

    /**
     * 获取属性税款所属始期
     * @return 税款所属始期
     */
    public String getTaxStartDate(){
        return TaxStartDate;
    }

    /**
     * 设置属性税款所属止期
     * @param TaxEndDate 税款所属止期
     */
    public void setTaxEndDate(String TaxEndDate){
     if  (TaxEndDate == null ||TaxEndDate.equals("")) 
     {
        this.TaxEndDate = "";
     }
     else
     {
        if(TaxEndDate.length()>9)
        this.TaxEndDate = TaxEndDate.substring(0,10);
     }
    }

    /**
     * 获取属性税款所属止期
     * @return 税款所属止期
     */
    public String getTaxEndDate(){
        return TaxEndDate;
    }

    /**
     * 设置属性申报日期
     * @param DeclareDate 申报日期
     */
    public void setDeclareDate(String DeclareDate){
     if  (DeclareDate == null ||DeclareDate.equals("")) 
     {
        this.DeclareDate = "";
     }
     else
     {
        if(DeclareDate.length()>9)
        this.DeclareDate = DeclareDate.substring(0,10);
     }
    }

    /**
     * 获取属性申报日期
     * @return 申报日期
     */
    public String getDeclareDate(){
        return DeclareDate;
    }

    /**
     * 设置属性开具完税凭证的税务机关代码
     * @param TaxDepartmentCode 开具完税凭证的税务机关代码
     */
    public void setTaxDepartmentCode(String TaxDepartmentCode){
        this.TaxDepartmentCode = TaxDepartmentCode;
    }

    /**
     * 获取属性开具完税凭证的税务机关代码
     * @return 开具完税凭证的税务机关代码
     */
    public String getTaxDepartmentCode(){
        return TaxDepartmentCode;
    }

    /**
     * 设置属性开具完税凭证的税务机关名称
     * @param TaxDepartment 开具完税凭证的税务机关名称
     */
    public void setTaxDepartment(String TaxDepartment){
        this.TaxDepartment = TaxDepartment;
    }

    /**
     * 获取属性开具完税凭证的税务机关名称
     * @return 开具完税凭证的税务机关名称
     */
    public String getTaxDepartment(){
        return TaxDepartment;
    }

    /**
     * 设置属性完税凭证号码
     * @param TaxDocumentNumber 完税凭证号码
     */
    public void setTaxDocumentNumber(String TaxDocumentNumber){
        this.TaxDocumentNumber = TaxDocumentNumber;
    }

    /**
     * 获取属性完税凭证号码
     * @return 完税凭证号码
     */
    public String getTaxDocumentNumber(){
        return TaxDocumentNumber;
    }

    /**
     * 设置属性当期年单位税额
     * @param AnnualTaxAmount 当期年单位税额
     */
    public void setAnnualTaxAmount(String AnnualTaxAmount){
        this.AnnualTaxAmount = AnnualTaxAmount;
    }

    /**
     * 获取属性当期年单位税额
     * @return 当期年单位税额
     */
    public String getAnnualTaxAmount(){
        return AnnualTaxAmount;
    }

    /**
     * 设置属性适用区域
     * @param AppliedArea 适用区域
     */
    public void setAppliedArea(String AppliedArea){
        this.AppliedArea = AppliedArea;
    }

    /**
     * 获取属性适用区域
     * @return 适用区域
     */
    public String getAppliedArea(){
        return AppliedArea;
    }

    /**
     * 设置属性税率方案代码
     * @param TaxRateIdentifier 税率方案代码
     */
    public void setTaxRateIdentifier(String TaxRateIdentifier){
        this.TaxRateIdentifier = TaxRateIdentifier;
    }

    /**
     * 获取属性税率方案代码
     * @return 税率方案代码
     */
    public String getTaxRateIdentifier(){
        return TaxRateIdentifier;
    }

    /**
     * 设置属性税务机关交通工具分类代码
     * @param TaxItemDetailCode 税务机关交通工具分类代码
     */
    public void setTaxItemDetailCode(String TaxItemDetailCode){
        this.TaxItemDetailCode = TaxItemDetailCode;
    }

    /**
     * 获取属性税务机关交通工具分类代码
     * @return 税务机关交通工具分类代码
     */
    public String getTaxItemDetailCode(){
        return TaxItemDetailCode;
    }

    /**
     * 设置属性计税单位代码
     * @param TaxUnitTypeCode 计税单位代码
     */
    public void setTaxUnitTypeCode(String TaxUnitTypeCode){
        this.TaxUnitTypeCode = TaxUnitTypeCode;
    }

    /**
     * 获取属性计税单位代码
     * @return 计税单位代码
     */
    public String getTaxUnitTypeCode(){
        return TaxUnitTypeCode;
    }

    /**
     * 设置属性单位计税金额
     * @param UnitRate 单位计税金额
     */
    public void setUnitRate(String UnitRate){
        this.UnitRate = UnitRate;
    }

    /**
     * 获取属性单位计税金额
     * @return 单位计税金额
     */
    public String getUnitRate(){
        return UnitRate;
    }

    /**
     * 设置属性适用期限起期
     * @param TaxRateStartDate 适用期限起期
     */
    public void setTaxRateStartDate(String TaxRateStartDate){
     if  (TaxRateStartDate == null || TaxRateStartDate.equals("")) 
     {
        this.TaxRateStartDate = "";
     }
     else
     {
        if(TaxRateStartDate.length()>9)
        this.TaxRateStartDate = TaxRateStartDate.substring(0,10);
     }
    }

    /**
     * 获取属性适用期限起期
     * @return 适用期限起期
     */
    public String getTaxRateStartDate(){
        return TaxRateStartDate;
    }

    /**
     * 设置属性适用期限止期
     * @param TaxRateEndDate 适用期限止期
     */
    public void setTaxRateEndDate(String TaxRateEndDate){
     if  (TaxRateEndDate == null ||TaxRateEndDate.equals("")) 
     {
        this.TaxRateEndDate = "";
     }
     else
     {
        if(TaxRateEndDate.length()>9)
        this.TaxRateEndDate = TaxRateEndDate.substring(0,10);
     }
    }

    /**
     * 获取属性适用期限止期
     * @return 适用期限止期
     */
    public String getTaxRateEndDate(){
        return TaxRateEndDate;
    }

    /**
     * 设置属性减免税原因代码
     * @param DeductionDueCode 减免税原因代码
     */
    public void setDeductionDueCode(String DeductionDueCode){
        this.DeductionDueCode = DeductionDueCode;
    }

    /**
     * 获取属性减免税原因代码
     * @return 减免税原因代码
     */
    public String getDeductionDueCode(){
        return DeductionDueCode;
    }

    /**
     * 设置属性减免税方案代码
     * @param DeductionDueType 减免税方案代码
     */
    public void setDeductionDueType(String DeductionDueType){
        this.DeductionDueType = DeductionDueType;
    }

    /**
     * 获取属性减免税方案代码
     * @return 减免税方案代码
     */
    public String getDeductionDueType(){
        return DeductionDueType;
    }

    /**
     * 设置属性减免比例
     * @param DeductionDueProportion 减免比例
     */
    public void setDeductionDueProportion(String DeductionDueProportion){
        this.DeductionDueProportion = DeductionDueProportion;
    }

    /**
     * 获取属性减免比例
     * @return 减免比例
     */
    public String getDeductionDueProportion(){
        return DeductionDueProportion;
    }

    /**
     * 设置属性减免金额
     * @param Deduction 减免金额
     */
    public void setDeduction(String Deduction){
        this.Deduction = Deduction;
    }

    /**
     * 获取属性减免金额
     * @return 减免金额
     */
    public String getDeduction(){
        return Deduction;
    }

    /**
     * 设置属性减免税凭证号
     * @param DeductionDocumentNumber 减免税凭证号
     */
    public void setDeductionDocumentNumber(String DeductionDocumentNumber){
        this.DeductionDocumentNumber = DeductionDocumentNumber;
    }

    /**
     * 获取属性减免税凭证号
     * @return 减免税凭证号
     */
    public String getDeductionDocumentNumber(){
        return DeductionDocumentNumber;
    }

    /**
     * 设置属性（减免）税务机关代码
     * @param DeductionDepartmentCode （减免）税务机关代码
     */
    public void setDeductionDepartmentCode(String DeductionDepartmentCode){
        this.DeductionDepartmentCode = DeductionDepartmentCode;
    }

    /**
     * 获取属性（减免）税务机关代码
     * @return （减免）税务机关代码
     */
    public String getDeductionDepartmentCode(){
        return DeductionDepartmentCode;
    }

    /**
     * 设置属性（减免）税务机关名称
     * @param DeductionDepartment （减免）税务机关名称
     */
    public void setDeductionDepartment(String DeductionDepartment){
        this.DeductionDepartment = DeductionDepartment;
    }

    /**
     * 获取属性（减免）税务机关名称
     * @return （减免）税务机关名称
     */
    public String getDeductionDepartment(){
        return DeductionDepartment;
    }

    /**
     * 设置属性年度当期应纳税额
     * @param TaxDue 年度当期应纳税额
     */
    public void setTaxDue(String TaxDue){
        this.TaxDue = TaxDue;
    }

    /**
     * 获取属性年度当期应纳税额
     * @return 年度当期应纳税额
     */
    public String getTaxDue(){
        return TaxDue;
    }

    /**
     * 设置属性逾期时间
     * @param ExceedDate 逾期时间
     */
    public void setExceedDate(String ExceedDate){
     if  (ExceedDate == null ||ExceedDate.equals("")) 
     {
        this.ExceedDate = "";
     }
     else
     {
        if(ExceedDate.length()>9)
        this.ExceedDate = ExceedDate.substring(0,10);
     }
    }

    /**
     * 获取属性逾期时间
     * @return 逾期时间
     */
    public String getExceedDate(){
        return ExceedDate;
    }

    /**
     * 设置属性逾期天数
     * @param ExceedDaysCount 逾期天数
     */
    public void setExceedDaysCount(String ExceedDaysCount){
        this.ExceedDaysCount = ExceedDaysCount;
    }

    /**
     * 获取属性逾期天数
     * @return 逾期天数
     */
    public String getExceedDaysCount(){
        return ExceedDaysCount;
    }

    /**
     * 设置属性年度滞纳金
     * @param OverDue 年度滞纳金
     */
    public void setOverDue(String OverDue){
        this.OverDue = OverDue;
    }

    /**
     * 获取属性年度滞纳金
     * @return 年度滞纳金
     */
    public String getOverDue(){
        return OverDue;
    }

    /**
     * 设置属性年度合计金额
     * @param TotalAmount 年度合计金额
     */
    public void setTotalAmount(String TotalAmount){
        this.TotalAmount = TotalAmount;
    }

    /**
     * 获取属性年度合计金额
     * @return 年度合计金额
     */
    public String getTotalAmount(){
        return TotalAmount;
    }

    /**
     * 设置属性本年应交额度
     * @param AnnualTaxDue 本年应交额度
     */
    public void setAnnualTaxDue(String AnnualTaxDue){
        this.AnnualTaxDue = AnnualTaxDue;
    }

    /**
     * 获取属性本年应交额度
     * @return 本年应交额度
     */
    public String getAnnualTaxDue(){
        return AnnualTaxDue;
    }

    /**
     * 设置属性合计欠税金额
     * @param SumTaxDefault 合计欠税金额
     */
    public void setSumTaxDefault(String SumTaxDefault){
        this.SumTaxDefault = SumTaxDefault;
    }

    /**
     * 获取属性合计欠税金额
     * @return 合计欠税金额
     */
    public String getSumTaxDefault(){
        return SumTaxDefault;
    }

    /**
     * 设置属性合计滞纳金
     * @param SumOverdue 合计滞纳金
     */
    public void setSumOverdue(String SumOverdue){
        this.SumOverdue = SumOverdue;
    }

    /**
     * 获取属性合计滞纳金
     * @return 合计滞纳金
     */
    public String getSumOverdue(){
        return SumOverdue;
    }

    /**
     * 设置属性合计金额
     * @param SumTax 合计金额
     */
    public void setSumTax(String SumTax){
        this.SumTax = SumTax;
    }

    /**
     * 获取属性合计金额
     * @return 合计金额
     */
    public String getSumTax(){
        return SumTax;
    }

    /**
     * 设置属性附加信息
     * @param TaxDescription 附加信息
     */
    public void setTaxDescription(String TaxDescription){
        this.TaxDescription = TaxDescription;
    }

    /**
     * 获取属性附加信息
     * @return 附加信息
     */
    public String getTaxDescription(){
        return TaxDescription;
    }	
    
    
    public void setTaxAmountFlag(String TaxAmountFlag){
        this.TaxAmountFlag = TaxAmountFlag;
    }
    
    public String getTaxAmountFlag(){
        return TaxAmountFlag;
    }
    
    public void setDeclareStatusIA(String DeclareStatusIA){
        this.DeclareStatusIA = DeclareStatusIA;
    }
    
    public String getDeclareStatusIA(){
        return DeclareStatusIA;
    }
    
    public void setCalcTaxFlag(String CalcTaxFlag){
        this.CalcTaxFlag = CalcTaxFlag;
    }
    
    public String getCalcTaxFlag(){
        return CalcTaxFlag;
    }
    
    public void setTaxPrintNo(String TaxPrintNo){
        this.TaxPrintNo = TaxPrintNo;
    }
    
    public String getTaxPrintNo(){
        return TaxPrintNo;
    }
    
	

    /**
     * @param iSchema 对象CICarShipTaxQGEndorseSchema
     */       
    public void setSchema(CICarShipTaxQGEndorseSchema iSchema)
    {
        this.EndorseNo = iSchema.getEndorseNo();
        this.DemandNo = iSchema.getDemandNo();
        this.PolicyNo = iSchema.getPolicyNo();
        this.TaxType = iSchema.getTaxType();
        this.SerialNo = iSchema.getSerialNo();
        this.TaxTermTypeCode = iSchema.getTaxTermTypeCode();
        this.TaxConditionCode = iSchema.getTaxConditionCode();
        this.TaxRegistryNumber = iSchema.getTaxRegistryNumber();
        this.TaxPayerName = iSchema.getTaxPayerName();
        this.TaxPayerIdentificationCode = iSchema.getTaxPayerIdentificationCode();
        this.TaxLocationCode = iSchema.getTaxLocationCode();
        this.TaxStartDate = iSchema.getTaxStartDate();
        this.TaxEndDate = iSchema.getTaxEndDate();
        this.DeclareDate = iSchema.getDeclareDate();
        this.TaxDepartmentCode = iSchema.getTaxDepartmentCode();
        this.TaxDepartment = iSchema.getTaxDepartment();
        this.TaxDocumentNumber = iSchema.getTaxDocumentNumber();
        this.AnnualTaxAmount = iSchema.getAnnualTaxAmount();
        this.AppliedArea = iSchema.getAppliedArea();
        this.TaxRateIdentifier = iSchema.getTaxRateIdentifier();
        this.TaxItemDetailCode = iSchema.getTaxItemDetailCode();
        this.TaxUnitTypeCode = iSchema.getTaxUnitTypeCode();
        this.UnitRate = iSchema.getUnitRate();
        this.TaxRateStartDate = iSchema.getTaxRateStartDate();
        this.TaxRateEndDate = iSchema.getTaxRateEndDate();
        this.DeductionDueCode = iSchema.getDeductionDueCode();
        this.DeductionDueType = iSchema.getDeductionDueType();
        this.DeductionDueProportion = iSchema.getDeductionDueProportion();
        this.Deduction = iSchema.getDeduction();
        this.DeductionDocumentNumber = iSchema.getDeductionDocumentNumber();
        this.DeductionDepartmentCode = iSchema.getDeductionDepartmentCode();
        this.DeductionDepartment = iSchema.getDeductionDepartment();
        this.TaxDue = iSchema.getTaxDue();
        this.ExceedDate = iSchema.getExceedDate();
        this.ExceedDaysCount = iSchema.getExceedDaysCount();
        this.OverDue = iSchema.getOverDue();
        this.TotalAmount = iSchema.getTotalAmount();
        this.AnnualTaxDue = iSchema.getAnnualTaxDue();
        this.SumTaxDefault = iSchema.getSumTaxDefault();
        this.SumOverdue = iSchema.getSumOverdue();
        this.SumTax = iSchema.getSumTax();
        this.TaxDescription = iSchema.getTaxDescription();
    	
        this.TaxAmountFlag = iSchema.getTaxAmountFlag();
        this.DeclareStatusIA = iSchema.getDeclareStatusIA();
        this.CalcTaxFlag = iSchema.getCalcTaxFlag();
        this.TaxPrintNo = iSchema.getTaxPrintNo();
    	

    }
}
