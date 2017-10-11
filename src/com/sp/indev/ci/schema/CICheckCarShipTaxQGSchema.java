package com.sp.indiv.ci.schema;

import java.io.Serializable;

/**
 * ����CICheckCarShipTaxQG�����ݴ��������
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>@createdate 2009-06-02</p>
 * @author DtoGenerator
 * @version 1.1
 */
public class CICheckCarShipTaxQGSchema implements Serializable{
    
    private String CheckNo = "";
    
    private String ComCode = "";
    
    private String StartDate = "";
    
    private String EndDate = "";
    
    private String TaxDeclareQueryNo = "";
    
    private String TaxDeclearConfirmNo = "";
    
    private String TaxPolicyCount = "";
    
    private String TaxPolicyMoney = "";
    
    private String TaxAmendCount = "";
    
    private String TaxAmendMoney = "";
    
    private String MTaxPolicyCount = "";
    
    private String MTaxPolicyMoney = "";
    
    private String MTaxAmendCount = "";
    
    private String MTaxAmendMoney = "";
    
    private String OperateDate = "";
    
    private String ConfirmOperateDate = "";
    
    private String OperateCode = "";
    
    private String UpdateCode = "";
    
    private String ExtendChar1 = "";
    
    private String ExtendChar2 = "";
    
    private String CorpDelayPay = "";
    
    private String CorpDelayPayReason = "";
    
    private String Flag = "";

    /**
     * ���캯��
     */       
    public CICheckCarShipTaxQGSchema(){
    }

    /**
     * ��������CheckNo
     * @param CheckNo CheckNo
     */
    public void setCheckNo(String CheckNo){
        this.CheckNo = CheckNo;
    }

    /**
     * ��ȡ����CheckNo
     * @return CheckNo
     */
    public String getCheckNo(){
        return CheckNo;
    }

    /**
     * ��������ComCode
     * @param ComCode ComCode
     */
    public void setComCode(String ComCode){
        this.ComCode = ComCode;
    }

    /**
     * ��ȡ����ComCode
     * @return ComCode
     */
    public String getComCode(){
        return ComCode;
    }

    /**
     * ��������StartDate
     * @param StartDate StartDate
     */
    public void setStartDate(String StartDate){
     if  (StartDate == null || StartDate.length() == 0 ) 
     {
        this.StartDate = "";
     }
     else
     {
        this.StartDate = StartDate.substring(0,10);
     }
    }

    /**
     * ��ȡ����StartDate
     * @return StartDate
     */
    public String getStartDate(){
        return StartDate;
    }

    /**
     * ��������EndDate
     * @param EndDate EndDate
     */
    public void setEndDate(String EndDate){
     if  (EndDate == null || EndDate.length() == 0) 
     {
        this.EndDate = "";
     }
     else
     {
        this.EndDate = EndDate.substring(0,10);
     }
    }

    /**
     * ��ȡ����EndDate
     * @return EndDate
     */
    public String getEndDate(){
        return EndDate;
    }

    /**
     * ��������TaxDeclareQueryNo
     * @param TaxDeclareQueryNo TaxDeclareQueryNo
     */
    public void setTaxDeclareQueryNo(String TaxDeclareQueryNo){
        this.TaxDeclareQueryNo = TaxDeclareQueryNo;
    }

    /**
     * ��ȡ����TaxDeclareQueryNo
     * @return TaxDeclareQueryNo
     */
    public String getTaxDeclareQueryNo(){
        return TaxDeclareQueryNo;
    }

    /**
     * ��������TaxDeclearConfirmNo
     * @param TaxDeclearConfirmNo TaxDeclearConfirmNo
     */
    public void setTaxDeclearConfirmNo(String TaxDeclearConfirmNo){
        this.TaxDeclearConfirmNo = TaxDeclearConfirmNo;
    }

    /**
     * ��ȡ����TaxDeclearConfirmNo
     * @return TaxDeclearConfirmNo
     */
    public String getTaxDeclearConfirmNo(){
        return TaxDeclearConfirmNo;
    }

    /**
     * ��������TaxPolicyCount
     * @param TaxPolicyCount TaxPolicyCount
     */
    public void setTaxPolicyCount(String TaxPolicyCount){
        this.TaxPolicyCount = TaxPolicyCount;
    }

    /**
     * ��ȡ����TaxPolicyCount
     * @return TaxPolicyCount
     */
    public String getTaxPolicyCount(){
        return TaxPolicyCount;
    }

    /**
     * ��������TaxPolicyMoney
     * @param TaxPolicyMoney TaxPolicyMoney
     */
    public void setTaxPolicyMoney(String TaxPolicyMoney){
        this.TaxPolicyMoney = TaxPolicyMoney;
    }

    /**
     * ��ȡ����TaxPolicyMoney
     * @return TaxPolicyMoney
     */
    public String getTaxPolicyMoney(){
        return TaxPolicyMoney;
    }

    /**
     * ��������TaxAmendCount
     * @param TaxAmendCount TaxAmendCount
     */
    public void setTaxAmendCount(String TaxAmendCount){
        this.TaxAmendCount = TaxAmendCount;
    }

    /**
     * ��ȡ����TaxAmendCount
     * @return TaxAmendCount
     */
    public String getTaxAmendCount(){
        return TaxAmendCount;
    }

    /**
     * ��������TaxAmendMoney
     * @param TaxAmendMoney TaxAmendMoney
     */
    public void setTaxAmendMoney(String TaxAmendMoney){
        this.TaxAmendMoney = TaxAmendMoney;
    }

    /**
     * ��ȡ����TaxAmendMoney
     * @return TaxAmendMoney
     */
    public String getTaxAmendMoney(){
        return TaxAmendMoney;
    }

    /**
     * ��������MTaxPolicyCount
     * @param MTaxPolicyCount MTaxPolicyCount
     */
    public void setMTaxPolicyCount(String MTaxPolicyCount){
        this.MTaxPolicyCount = MTaxPolicyCount;
    }

    /**
     * ��ȡ����MTaxPolicyCount
     * @return MTaxPolicyCount
     */
    public String getMTaxPolicyCount(){
        return MTaxPolicyCount;
    }

    /**
     * ��������MTaxPolicyMoney
     * @param MTaxPolicyMoney MTaxPolicyMoney
     */
    public void setMTaxPolicyMoney(String MTaxPolicyMoney){
        this.MTaxPolicyMoney = MTaxPolicyMoney;
    }

    /**
     * ��ȡ����MTaxPolicyMoney
     * @return MTaxPolicyMoney
     */
    public String getMTaxPolicyMoney(){
        return MTaxPolicyMoney;
    }

    /**
     * ��������MTaxAmendCount
     * @param MTaxAmendCount MTaxAmendCount
     */
    public void setMTaxAmendCount(String MTaxAmendCount){
        this.MTaxAmendCount = MTaxAmendCount;
    }

    /**
     * ��ȡ����MTaxAmendCount
     * @return MTaxAmendCount
     */
    public String getMTaxAmendCount(){
        return MTaxAmendCount;
    }

    /**
     * ��������MTaxAmendMoney
     * @param MTaxAmendMoney MTaxAmendMoney
     */
    public void setMTaxAmendMoney(String MTaxAmendMoney){
        this.MTaxAmendMoney = MTaxAmendMoney;
    }

    /**
     * ��ȡ����MTaxAmendMoney
     * @return MTaxAmendMoney
     */
    public String getMTaxAmendMoney(){
        return MTaxAmendMoney;
    }

    /**
     * ��������OperateDate
     * @param OperateDate OperateDate
     */
    public void setOperateDate(String OperateDate){
     if  (OperateDate == null ||OperateDate.length() == 0 ) 
     {
        this.OperateDate = "";
     }
     else
     {
        this.OperateDate = OperateDate.substring(0,10);
     }
    }

    /**
     * ��ȡ����OperateDate
     * @return OperateDate
     */
    public String getOperateDate(){
        return OperateDate;
    }

    /**
     * ��������ConfirmOperateDate
     * @param ConfirmOperateDate ConfirmOperateDate
     */
    public void setConfirmOperateDate(String ConfirmOperateDate){
     if  (ConfirmOperateDate == null ||ConfirmOperateDate.length() == 0) 
     {
        this.ConfirmOperateDate = "";
     }
     else
     {
        this.ConfirmOperateDate = ConfirmOperateDate.substring(0,10);
     }
    }

    /**
     * ��ȡ����ConfirmOperateDate
     * @return ConfirmOperateDate
     */
    public String getConfirmOperateDate(){
        return ConfirmOperateDate;
    }

    /**
     * ��������OperateCode
     * @param OperateCode OperateCode
     */
    public void setOperateCode(String OperateCode){
        this.OperateCode = OperateCode;
    }

    /**
     * ��ȡ����OperateCode
     * @return OperateCode
     */
    public String getOperateCode(){
        return OperateCode;
    }

    /**
     * ��������UpdateCode
     * @param UpdateCode UpdateCode
     */
    public void setUpdateCode(String UpdateCode){
        this.UpdateCode = UpdateCode;
    }

    /**
     * ��ȡ����UpdateCode
     * @return UpdateCode
     */
    public String getUpdateCode(){
        return UpdateCode;
    }

    /**
     * ��������ExtendChar1
     * @param ExtendChar1 ExtendChar1
     */
    public void setExtendChar1(String ExtendChar1){
        this.ExtendChar1 = ExtendChar1;
    }

    /**
     * ��ȡ����ExtendChar1
     * @return ExtendChar1
     */
    public String getExtendChar1(){
        return ExtendChar1;
    }

    /**
     * ��������ExtendChar2
     * @param ExtendChar2 ExtendChar2
     */
    public void setExtendChar2(String ExtendChar2){
        this.ExtendChar2 = ExtendChar2;
    }

    /**
     * ��ȡ����ExtendChar2
     * @return ExtendChar2
     */
    public String getExtendChar2(){
        return ExtendChar2;
    }

    /**
     * ��������CorpDelayPay
     * @param CorpDelayPay CorpDelayPay
     */
    public void setCorpDelayPay(String CorpDelayPay){
        this.CorpDelayPay = CorpDelayPay;
    }

    /**
     * ��ȡ����CorpDelayPay
     * @return CorpDelayPay
     */
    public String getCorpDelayPay(){
        return CorpDelayPay;
    }

    /**
     * ��������CorpDelayPayReason
     * @param CorpDelayPayReason CorpDelayPayReason
     */
    public void setCorpDelayPayReason(String CorpDelayPayReason){
        this.CorpDelayPayReason = CorpDelayPayReason;
    }

    /**
     * ��ȡ����CorpDelayPayReason
     * @return CorpDelayPayReason
     */
    public String getCorpDelayPayReason(){
        return CorpDelayPayReason;
    }

    /**
     * ��������Flag
     * @param Flag Flag
     */
    public void setFlag(String Flag){
        this.Flag = Flag;
    }

    /**
     * ��ȡ����Flag
     * @return Flag
     */
    public String getFlag(){
        return Flag;
    }

    /**
     * @param iSchema ����CICheckCarShipTaxQGSchema
     */       
    public void setSchema(CICheckCarShipTaxQGSchema iSchema)
    {
        this.CheckNo = iSchema.getCheckNo();
        this.ComCode = iSchema.getComCode();
        this.StartDate = iSchema.getStartDate();
        this.EndDate = iSchema.getEndDate();
        this.TaxDeclareQueryNo = iSchema.getTaxDeclareQueryNo();
        this.TaxDeclearConfirmNo = iSchema.getTaxDeclearConfirmNo();
        this.TaxPolicyCount = iSchema.getTaxPolicyCount();
        this.TaxPolicyMoney = iSchema.getTaxPolicyMoney();
        this.TaxAmendCount = iSchema.getTaxAmendCount();
        this.TaxAmendMoney = iSchema.getTaxAmendMoney();
        this.MTaxPolicyCount = iSchema.getMTaxPolicyCount();
        this.MTaxPolicyMoney = iSchema.getMTaxPolicyMoney();
        this.MTaxAmendCount = iSchema.getMTaxAmendCount();
        this.MTaxAmendMoney = iSchema.getMTaxAmendMoney();
        this.OperateDate = iSchema.getOperateDate();
        this.ConfirmOperateDate = iSchema.getConfirmOperateDate();
        this.OperateCode = iSchema.getOperateCode();
        this.UpdateCode = iSchema.getUpdateCode();
        this.ExtendChar1 = iSchema.getExtendChar1();
        this.ExtendChar2 = iSchema.getExtendChar2();
        this.CorpDelayPay = iSchema.getCorpDelayPay();
        this.CorpDelayPayReason = iSchema.getCorpDelayPayReason();
        this.Flag = iSchema.getFlag();
    }
}
