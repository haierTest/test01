package com.sp.indiv.ci.schema;

import java.io.Serializable;

/**
 * 定义CICheckCarShipTaxQG的数据传输对象类
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
     * 构造函数
     */       
    public CICheckCarShipTaxQGSchema(){
    }

    /**
     * 设置属性CheckNo
     * @param CheckNo CheckNo
     */
    public void setCheckNo(String CheckNo){
        this.CheckNo = CheckNo;
    }

    /**
     * 获取属性CheckNo
     * @return CheckNo
     */
    public String getCheckNo(){
        return CheckNo;
    }

    /**
     * 设置属性ComCode
     * @param ComCode ComCode
     */
    public void setComCode(String ComCode){
        this.ComCode = ComCode;
    }

    /**
     * 获取属性ComCode
     * @return ComCode
     */
    public String getComCode(){
        return ComCode;
    }

    /**
     * 设置属性StartDate
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
     * 获取属性StartDate
     * @return StartDate
     */
    public String getStartDate(){
        return StartDate;
    }

    /**
     * 设置属性EndDate
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
     * 获取属性EndDate
     * @return EndDate
     */
    public String getEndDate(){
        return EndDate;
    }

    /**
     * 设置属性TaxDeclareQueryNo
     * @param TaxDeclareQueryNo TaxDeclareQueryNo
     */
    public void setTaxDeclareQueryNo(String TaxDeclareQueryNo){
        this.TaxDeclareQueryNo = TaxDeclareQueryNo;
    }

    /**
     * 获取属性TaxDeclareQueryNo
     * @return TaxDeclareQueryNo
     */
    public String getTaxDeclareQueryNo(){
        return TaxDeclareQueryNo;
    }

    /**
     * 设置属性TaxDeclearConfirmNo
     * @param TaxDeclearConfirmNo TaxDeclearConfirmNo
     */
    public void setTaxDeclearConfirmNo(String TaxDeclearConfirmNo){
        this.TaxDeclearConfirmNo = TaxDeclearConfirmNo;
    }

    /**
     * 获取属性TaxDeclearConfirmNo
     * @return TaxDeclearConfirmNo
     */
    public String getTaxDeclearConfirmNo(){
        return TaxDeclearConfirmNo;
    }

    /**
     * 设置属性TaxPolicyCount
     * @param TaxPolicyCount TaxPolicyCount
     */
    public void setTaxPolicyCount(String TaxPolicyCount){
        this.TaxPolicyCount = TaxPolicyCount;
    }

    /**
     * 获取属性TaxPolicyCount
     * @return TaxPolicyCount
     */
    public String getTaxPolicyCount(){
        return TaxPolicyCount;
    }

    /**
     * 设置属性TaxPolicyMoney
     * @param TaxPolicyMoney TaxPolicyMoney
     */
    public void setTaxPolicyMoney(String TaxPolicyMoney){
        this.TaxPolicyMoney = TaxPolicyMoney;
    }

    /**
     * 获取属性TaxPolicyMoney
     * @return TaxPolicyMoney
     */
    public String getTaxPolicyMoney(){
        return TaxPolicyMoney;
    }

    /**
     * 设置属性TaxAmendCount
     * @param TaxAmendCount TaxAmendCount
     */
    public void setTaxAmendCount(String TaxAmendCount){
        this.TaxAmendCount = TaxAmendCount;
    }

    /**
     * 获取属性TaxAmendCount
     * @return TaxAmendCount
     */
    public String getTaxAmendCount(){
        return TaxAmendCount;
    }

    /**
     * 设置属性TaxAmendMoney
     * @param TaxAmendMoney TaxAmendMoney
     */
    public void setTaxAmendMoney(String TaxAmendMoney){
        this.TaxAmendMoney = TaxAmendMoney;
    }

    /**
     * 获取属性TaxAmendMoney
     * @return TaxAmendMoney
     */
    public String getTaxAmendMoney(){
        return TaxAmendMoney;
    }

    /**
     * 设置属性MTaxPolicyCount
     * @param MTaxPolicyCount MTaxPolicyCount
     */
    public void setMTaxPolicyCount(String MTaxPolicyCount){
        this.MTaxPolicyCount = MTaxPolicyCount;
    }

    /**
     * 获取属性MTaxPolicyCount
     * @return MTaxPolicyCount
     */
    public String getMTaxPolicyCount(){
        return MTaxPolicyCount;
    }

    /**
     * 设置属性MTaxPolicyMoney
     * @param MTaxPolicyMoney MTaxPolicyMoney
     */
    public void setMTaxPolicyMoney(String MTaxPolicyMoney){
        this.MTaxPolicyMoney = MTaxPolicyMoney;
    }

    /**
     * 获取属性MTaxPolicyMoney
     * @return MTaxPolicyMoney
     */
    public String getMTaxPolicyMoney(){
        return MTaxPolicyMoney;
    }

    /**
     * 设置属性MTaxAmendCount
     * @param MTaxAmendCount MTaxAmendCount
     */
    public void setMTaxAmendCount(String MTaxAmendCount){
        this.MTaxAmendCount = MTaxAmendCount;
    }

    /**
     * 获取属性MTaxAmendCount
     * @return MTaxAmendCount
     */
    public String getMTaxAmendCount(){
        return MTaxAmendCount;
    }

    /**
     * 设置属性MTaxAmendMoney
     * @param MTaxAmendMoney MTaxAmendMoney
     */
    public void setMTaxAmendMoney(String MTaxAmendMoney){
        this.MTaxAmendMoney = MTaxAmendMoney;
    }

    /**
     * 获取属性MTaxAmendMoney
     * @return MTaxAmendMoney
     */
    public String getMTaxAmendMoney(){
        return MTaxAmendMoney;
    }

    /**
     * 设置属性OperateDate
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
     * 获取属性OperateDate
     * @return OperateDate
     */
    public String getOperateDate(){
        return OperateDate;
    }

    /**
     * 设置属性ConfirmOperateDate
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
     * 获取属性ConfirmOperateDate
     * @return ConfirmOperateDate
     */
    public String getConfirmOperateDate(){
        return ConfirmOperateDate;
    }

    /**
     * 设置属性OperateCode
     * @param OperateCode OperateCode
     */
    public void setOperateCode(String OperateCode){
        this.OperateCode = OperateCode;
    }

    /**
     * 获取属性OperateCode
     * @return OperateCode
     */
    public String getOperateCode(){
        return OperateCode;
    }

    /**
     * 设置属性UpdateCode
     * @param UpdateCode UpdateCode
     */
    public void setUpdateCode(String UpdateCode){
        this.UpdateCode = UpdateCode;
    }

    /**
     * 获取属性UpdateCode
     * @return UpdateCode
     */
    public String getUpdateCode(){
        return UpdateCode;
    }

    /**
     * 设置属性ExtendChar1
     * @param ExtendChar1 ExtendChar1
     */
    public void setExtendChar1(String ExtendChar1){
        this.ExtendChar1 = ExtendChar1;
    }

    /**
     * 获取属性ExtendChar1
     * @return ExtendChar1
     */
    public String getExtendChar1(){
        return ExtendChar1;
    }

    /**
     * 设置属性ExtendChar2
     * @param ExtendChar2 ExtendChar2
     */
    public void setExtendChar2(String ExtendChar2){
        this.ExtendChar2 = ExtendChar2;
    }

    /**
     * 获取属性ExtendChar2
     * @return ExtendChar2
     */
    public String getExtendChar2(){
        return ExtendChar2;
    }

    /**
     * 设置属性CorpDelayPay
     * @param CorpDelayPay CorpDelayPay
     */
    public void setCorpDelayPay(String CorpDelayPay){
        this.CorpDelayPay = CorpDelayPay;
    }

    /**
     * 获取属性CorpDelayPay
     * @return CorpDelayPay
     */
    public String getCorpDelayPay(){
        return CorpDelayPay;
    }

    /**
     * 设置属性CorpDelayPayReason
     * @param CorpDelayPayReason CorpDelayPayReason
     */
    public void setCorpDelayPayReason(String CorpDelayPayReason){
        this.CorpDelayPayReason = CorpDelayPayReason;
    }

    /**
     * 获取属性CorpDelayPayReason
     * @return CorpDelayPayReason
     */
    public String getCorpDelayPayReason(){
        return CorpDelayPayReason;
    }

    /**
     * 设置属性Flag
     * @param Flag Flag
     */
    public void setFlag(String Flag){
        this.Flag = Flag;
    }

    /**
     * 获取属性Flag
     * @return Flag
     */
    public String getFlag(){
        return Flag;
    }

    /**
     * @param iSchema 对象CICheckCarShipTaxQGSchema
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
