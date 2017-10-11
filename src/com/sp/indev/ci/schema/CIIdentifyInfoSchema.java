package com.sp.indiv.ci.schema;

import java.io.Serializable;

import com.sp.utility.string.Str;

/**
 * 定义CIIdentifyInfo的数据传输对象类
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>@createdate 2015-01-11</p>
 * @author DtoGenerator
 * @version 1.1
 */
public class CIIdentifyInfoSchema implements Serializable{
    
    private String BusinessNo = "";
    
    private String SerialNo = "";
    
    private String IdentifyNumber = "";
    
    private String IssueCode = "";
    
    private String UploadDate = "";
    
    private String Nation = "";
    
    private String Signer = "";
    
    private String CollectorCode = "";
    
    private String Flag = "";

    /**
     * 构造函数
     */       
    public CIIdentifyInfoSchema(){
    }

    /**
     * 设置属性BusinessNo
     * @param BusinessNo BusinessNo
     */
    public void setBusinessNo(String BusinessNo){
        this.BusinessNo = Str.rightTrim(BusinessNo);
    }

    /**
     * 获取属性BusinessNo
     * @return BusinessNo
     */
    public String getBusinessNo(){
        return Str.rightTrim(BusinessNo);
    }

    /**
     * 设置属性SerialNo
     * @param SerialNo SerialNo
     */
    public void setSerialNo(String SerialNo){
        this.SerialNo = Str.rightTrim(SerialNo);
    }

    /**
     * 获取属性SerialNo
     * @return SerialNo
     */
    public String getSerialNo(){
        return Str.rightTrim(SerialNo);
    }

    /**
     * 设置属性IdentifyNumber
     * @param IdentifyNumber IdentifyNumber
     */
    public void setIdentifyNumber(String IdentifyNumber){
        this.IdentifyNumber = Str.rightTrim(IdentifyNumber);
    }

    /**
     * 获取属性IdentifyNumber
     * @return IdentifyNumber
     */
    public String getIdentifyNumber(){
        return Str.rightTrim(IdentifyNumber);
    }

    /**
     * 设置属性IssueCode
     * @param IssueCode IssueCode
     */
    public void setIssueCode(String IssueCode){
        this.IssueCode = Str.rightTrim(IssueCode);
    }

    /**
     * 获取属性IssueCode
     * @return IssueCode
     */
    public String getIssueCode(){
        return Str.rightTrim(IssueCode);
    }

    /**
     * 设置属性UploadDate
     * @param UploadDate UploadDate
     */
    public void setUploadDate(String UploadDate){
        this.UploadDate = Str.rightTrim(UploadDate);
    }

    /**
     * 获取属性UploadDate
     * @return UploadDate
     */
    public String getUploadDate(){
        return Str.rightTrim(UploadDate);
    }

    /**
     * 设置属性Nation
     * @param Nation Nation
     */
    public void setNation(String Nation){
        this.Nation = Str.rightTrim(Nation);
    }

    /**
     * 获取属性Nation
     * @return Nation
     */
    public String getNation(){
        return Str.rightTrim(Nation);
    }

    /**
     * 设置属性Signer
     * @param Signer Signer
     */
    public void setSigner(String Signer){
        this.Signer = Str.rightTrim(Signer);
    }

    /**
     * 获取属性Signer
     * @return Signer
     */
    public String getSigner(){
        return Str.rightTrim(Signer);
    }

    /**
     * 设置属性CollectorCode
     * @param CollectorCode CollectorCode
     */
    public void setCollectorCode(String CollectorCode){
        this.CollectorCode = Str.rightTrim(CollectorCode);
    }

    /**
     * 获取属性CollectorCode
     * @return CollectorCode
     */
    public String getCollectorCode(){
        return Str.rightTrim(CollectorCode);
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
     * @param iSchema 对象CIIdentifyInfoSchema
     */       
    public void setSchema(CIIdentifyInfoSchema iSchema)
    {
        this.BusinessNo = iSchema.getBusinessNo();
        this.SerialNo = iSchema.getSerialNo();
        this.IdentifyNumber = iSchema.getIdentifyNumber();
        this.IssueCode = iSchema.getIssueCode();
        this.UploadDate = iSchema.getUploadDate();
        this.Nation = iSchema.getNation();
        this.Signer = iSchema.getSigner();
        this.CollectorCode = iSchema.getCollectorCode();
        this.Flag = iSchema.getFlag();
    }
}
