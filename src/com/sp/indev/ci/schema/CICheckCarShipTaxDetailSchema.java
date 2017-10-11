package com.sp.indiv.ci.schema;

import java.io.Serializable;
import com.sp.utility.string.ChgDate;
import com.sp.utility.string.Str;

/**
 * ����CICheckCarShipTaxDetail�����ݴ��������
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>@createdate 2008-01-22</p>
 * @author DtoGenerator
 * @version 1.0
 */
public class CICheckCarShipTaxDetailSchema implements Serializable{
    
    private String CheckNo = "";
    
    private String MPolicyNo = "";
    
    private String TPolicyNo = "";
    
    private String MPayNo = "";
    
    private String TPayNo = "";
    
    private String MPayTax = "";
    
    private String TPayTax = "";
    
    private String MLateFee = "";
    
    private String TLateFee = "";
    
    private String ExtendChar1 = "";
    
    private String ExtendChar2 = "";
    
    private String Flag = "";

    /**
     * ���캯��
     */       
    public CICheckCarShipTaxDetailSchema(){
    }

    /**
     * ��������CheckNo
     * @param CheckNo CheckNo
     */
    public void setCheckNo(String CheckNo){
        this.CheckNo = Str.rightTrim(CheckNo);
    }

    /**
     * ��ȡ����CheckNo
     * @return CheckNo
     */
    public String getCheckNo(){
        return Str.rightTrim(CheckNo);
    }

    /**
     * ��������MPolicyNo
     * @param MPolicyNo MPolicyNo
     */
    public void setMPolicyNo(String MPolicyNo){
        this.MPolicyNo = Str.rightTrim(MPolicyNo);
    }

    /**
     * ��ȡ����MPolicyNo
     * @return MPolicyNo
     */
    public String getMPolicyNo(){
        return Str.rightTrim(MPolicyNo);
    }

    /**
     * ��������TPolicyNo
     * @param TPolicyNo TPolicyNo
     */
    public void setTPolicyNo(String TPolicyNo){
        this.TPolicyNo = Str.rightTrim(TPolicyNo);
    }

    /**
     * ��ȡ����TPolicyNo
     * @return TPolicyNo
     */
    public String getTPolicyNo(){
        return Str.rightTrim(TPolicyNo);
    }

    /**
     * ��������MPayNo
     * @param MPayNo MPayNo
     */
    public void setMPayNo(String MPayNo){
        this.MPayNo = Str.rightTrim(MPayNo);
    }

    /**
     * ��ȡ����MPayNo
     * @return MPayNo
     */
    public String getMPayNo(){
        return Str.rightTrim(MPayNo);
    }

    /**
     * ��������TPayNo
     * @param TPayNo TPayNo
     */
    public void setTPayNo(String TPayNo){
        this.TPayNo = Str.rightTrim(TPayNo);
    }

    /**
     * ��ȡ����TPayNo
     * @return TPayNo
     */
    public String getTPayNo(){
        return Str.rightTrim(TPayNo);
    }

    /**
     * ��������MPayTax
     * @param MPayTax MPayTax
     */
    public void setMPayTax(String MPayTax){
        this.MPayTax = Str.rightTrim(MPayTax);
    }

    /**
     * ��ȡ����MPayTax
     * @return MPayTax
     */
    public String getMPayTax(){
        return Str.rightTrim(MPayTax);
    }

    /**
     * ��������TPayTax
     * @param TPayTax TPayTax
     */
    public void setTPayTax(String TPayTax){
        this.TPayTax = Str.rightTrim(TPayTax);
    }

    /**
     * ��ȡ����TPayTax
     * @return TPayTax
     */
    public String getTPayTax(){
        return Str.rightTrim(TPayTax);
    }

    /**
     * ��������MLateFee
     * @param MLateFee MLateFee
     */
    public void setMLateFee(String MLateFee){
        this.MLateFee = Str.rightTrim(MLateFee);
    }

    /**
     * ��ȡ����MLateFee
     * @return MLateFee
     */
    public String getMLateFee(){
        return Str.rightTrim(MLateFee);
    }

    /**
     * ��������TLateFee
     * @param TLateFee TLateFee
     */
    public void setTLateFee(String TLateFee){
        this.TLateFee = Str.rightTrim(TLateFee);
    }

    /**
     * ��ȡ����TLateFee
     * @return TLateFee
     */
    public String getTLateFee(){
        return Str.rightTrim(TLateFee);
    }

    /**
     * ��������ExtendChar1
     * @param ExtendChar1 ExtendChar1
     */
    public void setExtendChar1(String ExtendChar1){
        this.ExtendChar1 = Str.rightTrim(ExtendChar1);
    }

    /**
     * ��ȡ����ExtendChar1
     * @return ExtendChar1
     */
    public String getExtendChar1(){
        return Str.rightTrim(ExtendChar1);
    }

    /**
     * ��������ExtendChar2
     * @param ExtendChar2 ExtendChar2
     */
    public void setExtendChar2(String ExtendChar2){
        this.ExtendChar2 = Str.rightTrim(ExtendChar2);
    }

    /**
     * ��ȡ����ExtendChar2
     * @return ExtendChar2
     */
    public String getExtendChar2(){
        return Str.rightTrim(ExtendChar2);
    }

    /**
     * ��������Flag
     * @param Flag Flag
     */
    public void setFlag(String Flag){
        this.Flag = Str.rightTrim(Flag);
    }

    /**
     * ��ȡ����Flag
     * @return Flag
     */
    public String getFlag(){
        return Str.rightTrim(Flag);
    }

    /**
     * @param iSchema ����CICheckCarShipTaxDetailSchema
     */       
    public void setSchema(CICheckCarShipTaxDetailSchema iSchema)
    {
        this.CheckNo = iSchema.getCheckNo();
        this.MPolicyNo = iSchema.getMPolicyNo();
        this.TPolicyNo = iSchema.getTPolicyNo();
        this.MPayNo = iSchema.getMPayNo();
        this.TPayNo = iSchema.getTPayNo();
        this.MPayTax = iSchema.getMPayTax();
        this.TPayTax = iSchema.getTPayTax();
        this.MLateFee = iSchema.getMLateFee();
        this.TLateFee = iSchema.getTLateFee();
        this.ExtendChar1 = iSchema.getExtendChar1();
        this.ExtendChar2 = iSchema.getExtendChar2();
        this.Flag = iSchema.getFlag();
    }
}
