package com.sp.indiv.ci.schema;

import java.io.Serializable;

import com.sp.utility.string.Str;

/**
 * ����CIIdentifyInfo�����ݴ��������
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
     * ���캯��
     */       
    public CIIdentifyInfoSchema(){
    }

    /**
     * ��������BusinessNo
     * @param BusinessNo BusinessNo
     */
    public void setBusinessNo(String BusinessNo){
        this.BusinessNo = Str.rightTrim(BusinessNo);
    }

    /**
     * ��ȡ����BusinessNo
     * @return BusinessNo
     */
    public String getBusinessNo(){
        return Str.rightTrim(BusinessNo);
    }

    /**
     * ��������SerialNo
     * @param SerialNo SerialNo
     */
    public void setSerialNo(String SerialNo){
        this.SerialNo = Str.rightTrim(SerialNo);
    }

    /**
     * ��ȡ����SerialNo
     * @return SerialNo
     */
    public String getSerialNo(){
        return Str.rightTrim(SerialNo);
    }

    /**
     * ��������IdentifyNumber
     * @param IdentifyNumber IdentifyNumber
     */
    public void setIdentifyNumber(String IdentifyNumber){
        this.IdentifyNumber = Str.rightTrim(IdentifyNumber);
    }

    /**
     * ��ȡ����IdentifyNumber
     * @return IdentifyNumber
     */
    public String getIdentifyNumber(){
        return Str.rightTrim(IdentifyNumber);
    }

    /**
     * ��������IssueCode
     * @param IssueCode IssueCode
     */
    public void setIssueCode(String IssueCode){
        this.IssueCode = Str.rightTrim(IssueCode);
    }

    /**
     * ��ȡ����IssueCode
     * @return IssueCode
     */
    public String getIssueCode(){
        return Str.rightTrim(IssueCode);
    }

    /**
     * ��������UploadDate
     * @param UploadDate UploadDate
     */
    public void setUploadDate(String UploadDate){
        this.UploadDate = Str.rightTrim(UploadDate);
    }

    /**
     * ��ȡ����UploadDate
     * @return UploadDate
     */
    public String getUploadDate(){
        return Str.rightTrim(UploadDate);
    }

    /**
     * ��������Nation
     * @param Nation Nation
     */
    public void setNation(String Nation){
        this.Nation = Str.rightTrim(Nation);
    }

    /**
     * ��ȡ����Nation
     * @return Nation
     */
    public String getNation(){
        return Str.rightTrim(Nation);
    }

    /**
     * ��������Signer
     * @param Signer Signer
     */
    public void setSigner(String Signer){
        this.Signer = Str.rightTrim(Signer);
    }

    /**
     * ��ȡ����Signer
     * @return Signer
     */
    public String getSigner(){
        return Str.rightTrim(Signer);
    }

    /**
     * ��������CollectorCode
     * @param CollectorCode CollectorCode
     */
    public void setCollectorCode(String CollectorCode){
        this.CollectorCode = Str.rightTrim(CollectorCode);
    }

    /**
     * ��ȡ����CollectorCode
     * @return CollectorCode
     */
    public String getCollectorCode(){
        return Str.rightTrim(CollectorCode);
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
     * @param iSchema ����CIIdentifyInfoSchema
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
