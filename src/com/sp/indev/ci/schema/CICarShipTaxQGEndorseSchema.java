package com.sp.indiv.ci.schema;

import java.io.Serializable;

/**
 * ����CICarShipTaxQGEndorse-ȫ������˰�����������Ĳ�ѯ������ݴ��������
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
     * ���캯��
     */       
    public CICarShipTaxQGEndorseSchema(){
    }

    /**
     * ��������������
     * @param EndorseNo ������
     */
    public void setEndorseNo(String EndorseNo){
        this.EndorseNo = EndorseNo;
    }

    /**
     * ��ȡ����������
     * @return ������
     */
    public String getEndorseNo(){
        return EndorseNo;
    }

    /**
     * �����������Ĳ�ѯ��
     * @param DemandNo ���Ĳ�ѯ��
     */
    public void setDemandNo(String DemandNo){
        this.DemandNo = DemandNo;
    }

    /**
     * ��ȡ�������Ĳ�ѯ��
     * @return ���Ĳ�ѯ��
     */
    public String getDemandNo(){
        return DemandNo;
    }

    /**
     * ��������XX��
     * @param PolicyNo XX��
     */
    public void setPolicyNo(String PolicyNo){
        this.PolicyNo = PolicyNo;
    }

    /**
     * ��ȡ����XX��
     * @return XX��
     */
    public String getPolicyNo(){
        return PolicyNo;
    }

    /**
     * �������Գ���˰����(0�ǵ���Ӧ��,1�����겹��)
     * @param TaxType ����˰����(0�ǵ���Ӧ��,1�����겹��)
     */
    public void setTaxType(String TaxType){
        this.TaxType = TaxType;
    }

    /**
     * ��ȡ���Գ���˰����(0�ǵ���Ӧ��,1�����겹��)
     * @return ����˰����(0�ǵ���Ӧ��,1�����겹��)
     */
    public String getTaxType(){
        return TaxType;
    }

    /**
     * �����������
     * @param SerialNo ���
     */
    public void setSerialNo(String SerialNo){
        this.SerialNo = SerialNo;
    }

    /**
     * ��ȡ�������
     * @return ���
     */
    public String getSerialNo(){
        return SerialNo;
    }

    /**
     * ��������˰�����ʹ���
     * @param TaxTermTypeCode ˰�����ʹ���
     */
    public void setTaxTermTypeCode(String TaxTermTypeCode){
        this.TaxTermTypeCode = TaxTermTypeCode;
    }

    /**
     * ��ȡ����˰�����ʹ���
     * @return ˰�����ʹ���
     */
    public String getTaxTermTypeCode(){
        return TaxTermTypeCode;
    }

    /**
     * ����������˰���ʹ���
     * @param TaxConditionCode ��˰���ʹ���
     */
    public void setTaxConditionCode(String TaxConditionCode){
        this.TaxConditionCode = TaxConditionCode;
    }

    /**
     * ��ȡ������˰���ʹ���
     * @return ��˰���ʹ���
     */
    public String getTaxConditionCode(){
        return TaxConditionCode;
    }

    /**
     * ��������˰��Ǽ�֤��
     * @param TaxRegistryNumber ˰��Ǽ�֤��
     */
    public void setTaxRegistryNumber(String TaxRegistryNumber){
        this.TaxRegistryNumber = TaxRegistryNumber;
    }

    /**
     * ��ȡ����˰��Ǽ�֤��
     * @return ˰��Ǽ�֤��
     */
    public String getTaxRegistryNumber(){
        return TaxRegistryNumber;
    }

    /**
     * ����������˰������
     * @param TaxPayerName ��˰������
     */
    public void setTaxPayerName(String TaxPayerName){
        this.TaxPayerName = TaxPayerName;
    }

    /**
     * ��ȡ������˰������
     * @return ��˰������
     */
    public String getTaxPayerName(){
        return TaxPayerName;
    }

    /**
     * ����������˰��ʶ���
     * @param TaxPayerIdentificationCode ��˰��ʶ���
     */
    public void setTaxPayerIdentificationCode(String TaxPayerIdentificationCode){
        this.TaxPayerIdentificationCode = TaxPayerIdentificationCode;
    }

    /**
     * ��ȡ������˰��ʶ���
     * @return ��˰��ʶ���
     */
    public String getTaxPayerIdentificationCode(){
        return TaxPayerIdentificationCode;
    }

    /**
     * ����������˰��������(�����������)
     * @param TaxLocationCode ��˰��������(�����������)
     */
    public void setTaxLocationCode(String TaxLocationCode){
        this.TaxLocationCode = TaxLocationCode;
    }

    /**
     * ��ȡ������˰��������(�����������)
     * @return ��˰��������(�����������)
     */
    public String getTaxLocationCode(){
        return TaxLocationCode;
    }

    /**
     * ��������˰������ʼ��
     * @param TaxStartDate ˰������ʼ��
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
     * ��ȡ����˰������ʼ��
     * @return ˰������ʼ��
     */
    public String getTaxStartDate(){
        return TaxStartDate;
    }

    /**
     * ��������˰������ֹ��
     * @param TaxEndDate ˰������ֹ��
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
     * ��ȡ����˰������ֹ��
     * @return ˰������ֹ��
     */
    public String getTaxEndDate(){
        return TaxEndDate;
    }

    /**
     * ���������걨����
     * @param DeclareDate �걨����
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
     * ��ȡ�����걨����
     * @return �걨����
     */
    public String getDeclareDate(){
        return DeclareDate;
    }

    /**
     * �������Կ�����˰ƾ֤��˰����ش���
     * @param TaxDepartmentCode ������˰ƾ֤��˰����ش���
     */
    public void setTaxDepartmentCode(String TaxDepartmentCode){
        this.TaxDepartmentCode = TaxDepartmentCode;
    }

    /**
     * ��ȡ���Կ�����˰ƾ֤��˰����ش���
     * @return ������˰ƾ֤��˰����ش���
     */
    public String getTaxDepartmentCode(){
        return TaxDepartmentCode;
    }

    /**
     * �������Կ�����˰ƾ֤��˰���������
     * @param TaxDepartment ������˰ƾ֤��˰���������
     */
    public void setTaxDepartment(String TaxDepartment){
        this.TaxDepartment = TaxDepartment;
    }

    /**
     * ��ȡ���Կ�����˰ƾ֤��˰���������
     * @return ������˰ƾ֤��˰���������
     */
    public String getTaxDepartment(){
        return TaxDepartment;
    }

    /**
     * ����������˰ƾ֤����
     * @param TaxDocumentNumber ��˰ƾ֤����
     */
    public void setTaxDocumentNumber(String TaxDocumentNumber){
        this.TaxDocumentNumber = TaxDocumentNumber;
    }

    /**
     * ��ȡ������˰ƾ֤����
     * @return ��˰ƾ֤����
     */
    public String getTaxDocumentNumber(){
        return TaxDocumentNumber;
    }

    /**
     * �������Ե����굥λ˰��
     * @param AnnualTaxAmount �����굥λ˰��
     */
    public void setAnnualTaxAmount(String AnnualTaxAmount){
        this.AnnualTaxAmount = AnnualTaxAmount;
    }

    /**
     * ��ȡ���Ե����굥λ˰��
     * @return �����굥λ˰��
     */
    public String getAnnualTaxAmount(){
        return AnnualTaxAmount;
    }

    /**
     * ����������������
     * @param AppliedArea ��������
     */
    public void setAppliedArea(String AppliedArea){
        this.AppliedArea = AppliedArea;
    }

    /**
     * ��ȡ������������
     * @return ��������
     */
    public String getAppliedArea(){
        return AppliedArea;
    }

    /**
     * ��������˰�ʷ�������
     * @param TaxRateIdentifier ˰�ʷ�������
     */
    public void setTaxRateIdentifier(String TaxRateIdentifier){
        this.TaxRateIdentifier = TaxRateIdentifier;
    }

    /**
     * ��ȡ����˰�ʷ�������
     * @return ˰�ʷ�������
     */
    public String getTaxRateIdentifier(){
        return TaxRateIdentifier;
    }

    /**
     * ��������˰����ؽ�ͨ���߷������
     * @param TaxItemDetailCode ˰����ؽ�ͨ���߷������
     */
    public void setTaxItemDetailCode(String TaxItemDetailCode){
        this.TaxItemDetailCode = TaxItemDetailCode;
    }

    /**
     * ��ȡ����˰����ؽ�ͨ���߷������
     * @return ˰����ؽ�ͨ���߷������
     */
    public String getTaxItemDetailCode(){
        return TaxItemDetailCode;
    }

    /**
     * �������Լ�˰��λ����
     * @param TaxUnitTypeCode ��˰��λ����
     */
    public void setTaxUnitTypeCode(String TaxUnitTypeCode){
        this.TaxUnitTypeCode = TaxUnitTypeCode;
    }

    /**
     * ��ȡ���Լ�˰��λ����
     * @return ��˰��λ����
     */
    public String getTaxUnitTypeCode(){
        return TaxUnitTypeCode;
    }

    /**
     * �������Ե�λ��˰���
     * @param UnitRate ��λ��˰���
     */
    public void setUnitRate(String UnitRate){
        this.UnitRate = UnitRate;
    }

    /**
     * ��ȡ���Ե�λ��˰���
     * @return ��λ��˰���
     */
    public String getUnitRate(){
        return UnitRate;
    }

    /**
     * ��������������������
     * @param TaxRateStartDate ������������
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
     * ��ȡ����������������
     * @return ������������
     */
    public String getTaxRateStartDate(){
        return TaxRateStartDate;
    }

    /**
     * ����������������ֹ��
     * @param TaxRateEndDate ��������ֹ��
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
     * ��ȡ������������ֹ��
     * @return ��������ֹ��
     */
    public String getTaxRateEndDate(){
        return TaxRateEndDate;
    }

    /**
     * �������Լ���˰ԭ�����
     * @param DeductionDueCode ����˰ԭ�����
     */
    public void setDeductionDueCode(String DeductionDueCode){
        this.DeductionDueCode = DeductionDueCode;
    }

    /**
     * ��ȡ���Լ���˰ԭ�����
     * @return ����˰ԭ�����
     */
    public String getDeductionDueCode(){
        return DeductionDueCode;
    }

    /**
     * �������Լ���˰��������
     * @param DeductionDueType ����˰��������
     */
    public void setDeductionDueType(String DeductionDueType){
        this.DeductionDueType = DeductionDueType;
    }

    /**
     * ��ȡ���Լ���˰��������
     * @return ����˰��������
     */
    public String getDeductionDueType(){
        return DeductionDueType;
    }

    /**
     * �������Լ������
     * @param DeductionDueProportion �������
     */
    public void setDeductionDueProportion(String DeductionDueProportion){
        this.DeductionDueProportion = DeductionDueProportion;
    }

    /**
     * ��ȡ���Լ������
     * @return �������
     */
    public String getDeductionDueProportion(){
        return DeductionDueProportion;
    }

    /**
     * �������Լ�����
     * @param Deduction ������
     */
    public void setDeduction(String Deduction){
        this.Deduction = Deduction;
    }

    /**
     * ��ȡ���Լ�����
     * @return ������
     */
    public String getDeduction(){
        return Deduction;
    }

    /**
     * �������Լ���˰ƾ֤��
     * @param DeductionDocumentNumber ����˰ƾ֤��
     */
    public void setDeductionDocumentNumber(String DeductionDocumentNumber){
        this.DeductionDocumentNumber = DeductionDocumentNumber;
    }

    /**
     * ��ȡ���Լ���˰ƾ֤��
     * @return ����˰ƾ֤��
     */
    public String getDeductionDocumentNumber(){
        return DeductionDocumentNumber;
    }

    /**
     * �������ԣ����⣩˰����ش���
     * @param DeductionDepartmentCode �����⣩˰����ش���
     */
    public void setDeductionDepartmentCode(String DeductionDepartmentCode){
        this.DeductionDepartmentCode = DeductionDepartmentCode;
    }

    /**
     * ��ȡ���ԣ����⣩˰����ش���
     * @return �����⣩˰����ش���
     */
    public String getDeductionDepartmentCode(){
        return DeductionDepartmentCode;
    }

    /**
     * �������ԣ����⣩˰���������
     * @param DeductionDepartment �����⣩˰���������
     */
    public void setDeductionDepartment(String DeductionDepartment){
        this.DeductionDepartment = DeductionDepartment;
    }

    /**
     * ��ȡ���ԣ����⣩˰���������
     * @return �����⣩˰���������
     */
    public String getDeductionDepartment(){
        return DeductionDepartment;
    }

    /**
     * ����������ȵ���Ӧ��˰��
     * @param TaxDue ��ȵ���Ӧ��˰��
     */
    public void setTaxDue(String TaxDue){
        this.TaxDue = TaxDue;
    }

    /**
     * ��ȡ������ȵ���Ӧ��˰��
     * @return ��ȵ���Ӧ��˰��
     */
    public String getTaxDue(){
        return TaxDue;
    }

    /**
     * ������������ʱ��
     * @param ExceedDate ����ʱ��
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
     * ��ȡ��������ʱ��
     * @return ����ʱ��
     */
    public String getExceedDate(){
        return ExceedDate;
    }

    /**
     * ����������������
     * @param ExceedDaysCount ��������
     */
    public void setExceedDaysCount(String ExceedDaysCount){
        this.ExceedDaysCount = ExceedDaysCount;
    }

    /**
     * ��ȡ������������
     * @return ��������
     */
    public String getExceedDaysCount(){
        return ExceedDaysCount;
    }

    /**
     * ��������������ɽ�
     * @param OverDue ������ɽ�
     */
    public void setOverDue(String OverDue){
        this.OverDue = OverDue;
    }

    /**
     * ��ȡ����������ɽ�
     * @return ������ɽ�
     */
    public String getOverDue(){
        return OverDue;
    }

    /**
     * ����������Ⱥϼƽ��
     * @param TotalAmount ��Ⱥϼƽ��
     */
    public void setTotalAmount(String TotalAmount){
        this.TotalAmount = TotalAmount;
    }

    /**
     * ��ȡ������Ⱥϼƽ��
     * @return ��Ⱥϼƽ��
     */
    public String getTotalAmount(){
        return TotalAmount;
    }

    /**
     * �������Ա���Ӧ�����
     * @param AnnualTaxDue ����Ӧ�����
     */
    public void setAnnualTaxDue(String AnnualTaxDue){
        this.AnnualTaxDue = AnnualTaxDue;
    }

    /**
     * ��ȡ���Ա���Ӧ�����
     * @return ����Ӧ�����
     */
    public String getAnnualTaxDue(){
        return AnnualTaxDue;
    }

    /**
     * �������Ժϼ�Ƿ˰���
     * @param SumTaxDefault �ϼ�Ƿ˰���
     */
    public void setSumTaxDefault(String SumTaxDefault){
        this.SumTaxDefault = SumTaxDefault;
    }

    /**
     * ��ȡ���Ժϼ�Ƿ˰���
     * @return �ϼ�Ƿ˰���
     */
    public String getSumTaxDefault(){
        return SumTaxDefault;
    }

    /**
     * �������Ժϼ����ɽ�
     * @param SumOverdue �ϼ����ɽ�
     */
    public void setSumOverdue(String SumOverdue){
        this.SumOverdue = SumOverdue;
    }

    /**
     * ��ȡ���Ժϼ����ɽ�
     * @return �ϼ����ɽ�
     */
    public String getSumOverdue(){
        return SumOverdue;
    }

    /**
     * �������Ժϼƽ��
     * @param SumTax �ϼƽ��
     */
    public void setSumTax(String SumTax){
        this.SumTax = SumTax;
    }

    /**
     * ��ȡ���Ժϼƽ��
     * @return �ϼƽ��
     */
    public String getSumTax(){
        return SumTax;
    }

    /**
     * �������Ը�����Ϣ
     * @param TaxDescription ������Ϣ
     */
    public void setTaxDescription(String TaxDescription){
        this.TaxDescription = TaxDescription;
    }

    /**
     * ��ȡ���Ը�����Ϣ
     * @return ������Ϣ
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
     * @param iSchema ����CICarShipTaxQGEndorseSchema
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
