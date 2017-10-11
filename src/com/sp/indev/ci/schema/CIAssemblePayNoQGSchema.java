package com.sp.indiv.ci.schema;

import java.io.Serializable;

/**
 * ����CIAssemblePayNoQG�����ݴ��������
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
     * ���캯��
     */       
    public CIAssemblePayNoQGSchema(){
    }

    /**
     * ��������TaxReceiptNo
     * @param TaxReceiptNo TaxReceiptNo
     */
    public void setTaxReceiptNo(String TaxReceiptNo){
        this.TaxReceiptNo = TaxReceiptNo;
    }

    /**
     * ��ȡ����TaxReceiptNo
     * @return TaxReceiptNo
     */
    public String getTaxReceiptNo(){
        return TaxReceiptNo;
    }

    /**
     * ��������ConFirmNo
     * @param ConFirmNo ConFirmNo
     */
    public void setConFirmNo(String ConFirmNo){
        this.ConFirmNo = ConFirmNo;
    }

    /**
     * ��ȡ����ConFirmNo
     * @return ConFirmNo
     */
    public String getConFirmNo(){
        return ConFirmNo;
    }

    /**
     * ��������DeductStatus
     * @param DeductStatus DeductStatus
     */
    public void setDeductStatus(String DeductStatus){
        this.DeductStatus = DeductStatus;
    }

    /**
     * ��ȡ����DeductStatus
     * @return DeductStatus
     */
    public String getDeductStatus(){
        return DeductStatus;
    }

    /**
     * ��������DeductAmout
     * @param DeductAmout DeductAmout
     */
    public void setDeductAmout(String DeductAmout){
        this.DeductAmout = DeductAmout;
    }

    /**
     * ��ȡ����DeductAmout
     * @return DeductAmout
     */
    public String getDeductAmout(){
        return DeductAmout;
    }

    /**
     * ��������DeductDate
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
     * ��ȡ����DeductDate
     * @return DeductDate
     */
    public String getDeductDate(){
        return DeductDate;
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
     * @param iSchema ����CIAssemblePayNoQGSchema
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
