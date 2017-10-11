package com.sp.indiv.ci.schema;

import java.io.Serializable;

/**
 * 定义CIAssemblePayNoQG的数据传输对象类
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>@createdate 2009-06-02</p>
 * @author DtoGenerator
 * @version 1.1
 */
public class CIAssemblePayNoQGSchema implements Serializable{
    
    private String TaxReceiptNo = "";
    
    private String ConFirmNo = "";
    
    private String DeductStatus = "";
    
    private String DeductAmout = "";
    
    private String DeductDate = "";
    
    private String ExtendChar1 = "";
    
    private String ExtendChar2 = "";

    /**
     * 构造函数
     */       
    public CIAssemblePayNoQGSchema(){
    }

    /**
     * 设置属性TaxReceiptNo
     * @param TaxReceiptNo TaxReceiptNo
     */
    public void setTaxReceiptNo(String TaxReceiptNo){
        this.TaxReceiptNo = TaxReceiptNo;
    }

    /**
     * 获取属性TaxReceiptNo
     * @return TaxReceiptNo
     */
    public String getTaxReceiptNo(){
        return TaxReceiptNo;
    }

    /**
     * 设置属性ConFirmNo
     * @param ConFirmNo ConFirmNo
     */
    public void setConFirmNo(String ConFirmNo){
        this.ConFirmNo = ConFirmNo;
    }

    /**
     * 获取属性ConFirmNo
     * @return ConFirmNo
     */
    public String getConFirmNo(){
        return ConFirmNo;
    }

    /**
     * 设置属性DeductStatus
     * @param DeductStatus DeductStatus
     */
    public void setDeductStatus(String DeductStatus){
        this.DeductStatus = DeductStatus;
    }

    /**
     * 获取属性DeductStatus
     * @return DeductStatus
     */
    public String getDeductStatus(){
        return DeductStatus;
    }

    /**
     * 设置属性DeductAmout
     * @param DeductAmout DeductAmout
     */
    public void setDeductAmout(String DeductAmout){
        this.DeductAmout = DeductAmout;
    }

    /**
     * 获取属性DeductAmout
     * @return DeductAmout
     */
    public String getDeductAmout(){
        return DeductAmout;
    }

    /**
     * 设置属性DeductDate
     * @param DeductDate DeductDate
     */
    public void setDeductDate(String DeductDate){
     if  (DeductDate == null || DeductDate.length() == 0) 
     {
        this.DeductDate = "";
     }
     else
     {
        this.DeductDate = DeductDate.substring(0,10);
     }
    }

    /**
     * 获取属性DeductDate
     * @return DeductDate
     */
    public String getDeductDate(){
        return DeductDate;
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
     * @param iSchema 对象CIAssemblePayNoQGSchema
     */       
    public void setSchema(CIAssemblePayNoQGSchema iSchema)
    {
        this.TaxReceiptNo = iSchema.getTaxReceiptNo();
        this.ConFirmNo = iSchema.getConFirmNo();
        this.DeductStatus = iSchema.getDeductStatus();
        this.DeductAmout = iSchema.getDeductAmout();
        this.DeductDate = iSchema.getDeductDate();
        this.ExtendChar1 = iSchema.getExtendChar1();
        this.ExtendChar2 = iSchema.getExtendChar2();
    }
}
