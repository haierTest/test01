package com.sp.indiv.ci.schema;

import java.io.Serializable;
import com.sp.utility.string.ChgDate;
import com.sp.utility.string.Str;

/**
 * 定义CICheckCarShipTaxDetail的数据传输对象类
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
     * 构造函数
     */       
    public CICheckCarShipTaxDetailSchema(){
    }

    /**
     * 设置属性CheckNo
     * @param CheckNo CheckNo
     */
    public void setCheckNo(String CheckNo){
        this.CheckNo = Str.rightTrim(CheckNo);
    }

    /**
     * 获取属性CheckNo
     * @return CheckNo
     */
    public String getCheckNo(){
        return Str.rightTrim(CheckNo);
    }

    /**
     * 设置属性MPolicyNo
     * @param MPolicyNo MPolicyNo
     */
    public void setMPolicyNo(String MPolicyNo){
        this.MPolicyNo = Str.rightTrim(MPolicyNo);
    }

    /**
     * 获取属性MPolicyNo
     * @return MPolicyNo
     */
    public String getMPolicyNo(){
        return Str.rightTrim(MPolicyNo);
    }

    /**
     * 设置属性TPolicyNo
     * @param TPolicyNo TPolicyNo
     */
    public void setTPolicyNo(String TPolicyNo){
        this.TPolicyNo = Str.rightTrim(TPolicyNo);
    }

    /**
     * 获取属性TPolicyNo
     * @return TPolicyNo
     */
    public String getTPolicyNo(){
        return Str.rightTrim(TPolicyNo);
    }

    /**
     * 设置属性MPayNo
     * @param MPayNo MPayNo
     */
    public void setMPayNo(String MPayNo){
        this.MPayNo = Str.rightTrim(MPayNo);
    }

    /**
     * 获取属性MPayNo
     * @return MPayNo
     */
    public String getMPayNo(){
        return Str.rightTrim(MPayNo);
    }

    /**
     * 设置属性TPayNo
     * @param TPayNo TPayNo
     */
    public void setTPayNo(String TPayNo){
        this.TPayNo = Str.rightTrim(TPayNo);
    }

    /**
     * 获取属性TPayNo
     * @return TPayNo
     */
    public String getTPayNo(){
        return Str.rightTrim(TPayNo);
    }

    /**
     * 设置属性MPayTax
     * @param MPayTax MPayTax
     */
    public void setMPayTax(String MPayTax){
        this.MPayTax = Str.rightTrim(MPayTax);
    }

    /**
     * 获取属性MPayTax
     * @return MPayTax
     */
    public String getMPayTax(){
        return Str.rightTrim(MPayTax);
    }

    /**
     * 设置属性TPayTax
     * @param TPayTax TPayTax
     */
    public void setTPayTax(String TPayTax){
        this.TPayTax = Str.rightTrim(TPayTax);
    }

    /**
     * 获取属性TPayTax
     * @return TPayTax
     */
    public String getTPayTax(){
        return Str.rightTrim(TPayTax);
    }

    /**
     * 设置属性MLateFee
     * @param MLateFee MLateFee
     */
    public void setMLateFee(String MLateFee){
        this.MLateFee = Str.rightTrim(MLateFee);
    }

    /**
     * 获取属性MLateFee
     * @return MLateFee
     */
    public String getMLateFee(){
        return Str.rightTrim(MLateFee);
    }

    /**
     * 设置属性TLateFee
     * @param TLateFee TLateFee
     */
    public void setTLateFee(String TLateFee){
        this.TLateFee = Str.rightTrim(TLateFee);
    }

    /**
     * 获取属性TLateFee
     * @return TLateFee
     */
    public String getTLateFee(){
        return Str.rightTrim(TLateFee);
    }

    /**
     * 设置属性ExtendChar1
     * @param ExtendChar1 ExtendChar1
     */
    public void setExtendChar1(String ExtendChar1){
        this.ExtendChar1 = Str.rightTrim(ExtendChar1);
    }

    /**
     * 获取属性ExtendChar1
     * @return ExtendChar1
     */
    public String getExtendChar1(){
        return Str.rightTrim(ExtendChar1);
    }

    /**
     * 设置属性ExtendChar2
     * @param ExtendChar2 ExtendChar2
     */
    public void setExtendChar2(String ExtendChar2){
        this.ExtendChar2 = Str.rightTrim(ExtendChar2);
    }

    /**
     * 获取属性ExtendChar2
     * @return ExtendChar2
     */
    public String getExtendChar2(){
        return Str.rightTrim(ExtendChar2);
    }

    /**
     * 设置属性Flag
     * @param Flag Flag
     */
    public void setFlag(String Flag){
        this.Flag = Str.rightTrim(Flag);
    }

    /**
     * 获取属性Flag
     * @return Flag
     */
    public String getFlag(){
        return Str.rightTrim(Flag);
    }

    /**
     * @param iSchema 对象CICheckCarShipTaxDetailSchema
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
