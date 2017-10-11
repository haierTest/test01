package com.sp.indiv.ci.schema;

import java.io.Serializable;

import com.sp.utility.string.Str;

/**
 * 定义CIEndorValidLoss的数据传输对象类
 * 从pdm中取数据库信息,根据数据库表生成对应的Dto或Schema类
 * <p>Title: SinosoftJavaTools v1.4</p>
 * <p>Description: 中科软Java源码生成工具</p>
 * <p>Company: Sinosoft</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>@createdate 2010-11-05</p>
 * @FirstAuthor: Zhouxianli(JavaTools v1.0)
 * @SecondAuthor: Yangkun(JavaTools v1.1->v1.2->v1.3->v1.4)
 * @LastVersion: v1.4.2
 * @UpdateList 1.改造Schema层类生成工具继承关系方法;
 */
public class CIEndorValidLossSchema implements Serializable{
    
    private String DemandNo = "";
    
    private String SerialNo = "";
    
    private String PolicyNo = "";
    
    private String LossTime = "";
    
    private String LossAddress = "";
    
    private String LossAction = "";
    
    private String Coeff = "";
    
    private String LossType = "";
    
    private String IdentifyType = "";
    
    private String IdentifyNumber = "";
    
    private String Remark = "";
    
    private String Flag = "";
    
    private String LossAcceptDate = "";
    
    private String AdjustFlag = "";
    
    private String DecisionCode = "";
    
    private String LicensePlateNo = "";
    
    private String LicensePlateTypeCode = "";
    
    private String JurisdictionAgencyCode = "";
    
    private String DecisionTypeCode = "";
    
    private String PeccancyNumber = "";
    
    private String DriverName = "";
    private String VouchCode="";
    private String SanctionPerson="";
    private String SanctionDate="";
    

    /**
     * 构造函数
     */       
    public CIEndorValidLossSchema(){
    }

    /**
     * 设置属性DemandNo
     * @param DemandNo DemandNo
     */
    public void setDemandNo(String DemandNo){
        this.DemandNo = Str.rightTrim(DemandNo);
    }

    /**
     * 获取属性DemandNo
     * @return DemandNo
     */
    public String getDemandNo(){
        return Str.rightTrim(DemandNo);
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
        return SerialNo;
    }

    /**
     * 设置属性PolicyNo
     * @param PolicyNo PolicyNo
     */
    public void setPolicyNo(String PolicyNo){
        this.PolicyNo = Str.rightTrim(PolicyNo);
    }

    /**
     * 获取属性PolicyNo
     * @return PolicyNo
     */
    public String getPolicyNo(){
        return PolicyNo;
    }

    /**
     * 设置属性LossTime
     * @param LossTime LossTime
     */
    public void setLossTime(String LossTime){
        this.LossTime = Str.rightTrim(LossTime);
    }

    /**
     * 获取属性LossTime
     * @return LossTime
     */
    public String getLossTime(){
        return LossTime;
    }

    /**
     * 设置属性LossAddress
     * @param LossAddress LossAddress
     */
    public void setLossAddress(String LossAddress){
        this.LossAddress = Str.rightTrim(LossAddress);
    }

    /**
     * 获取属性LossAddress
     * @return LossAddress
     */
    public String getLossAddress(){
        return LossAddress;
    }

    /**
     * 设置属性LossAction
     * @param LossAction LossAction
     */
    public void setLossAction(String LossAction){
        this.LossAction = Str.rightTrim(LossAction);
    }

    /**
     * 获取属性LossAction
     * @return LossAction
     */
    public String getLossAction(){
        return LossAction;
    }

    /**
     * 设置属性Coeff
     * @param Coeff Coeff
     */
    public void setCoeff(String Coeff){
        this.Coeff = Str.rightTrim(Coeff);
    }

    /**
     * 获取属性Coeff
     * @return Coeff
     */
    public String getCoeff(){
        return Coeff;
    }

    /**
     * 设置属性LossType
     * @param LossType LossType
     */
    public void setLossType(String LossType){
        this.LossType = Str.rightTrim(LossType);
    }

    /**
     * 获取属性LossType
     * @return LossType
     */
    public String getLossType(){
        return LossType;
    }

    /**
     * 设置属性IdentifyType
     * @param IdentifyType IdentifyType
     */
    public void setIdentifyType(String IdentifyType){
        this.IdentifyType = Str.rightTrim(IdentifyType);
    }

    /**
     * 获取属性IdentifyType
     * @return IdentifyType
     */
    public String getIdentifyType(){
        return IdentifyType;
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
        return IdentifyNumber;
    }

    /**
     * 设置属性Remark
     * @param Remark Remark
     */
    public void setRemark(String Remark){
        this.Remark = Str.rightTrim(Remark);
    }

    /**
     * 获取属性Remark
     * @return Remark
     */
    public String getRemark(){
        return Remark;
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
        return Flag;
    }

    /**
     * 设置属性LossAcceptDate
     * @param LossAcceptDate LossAcceptDate
     */
    public void setLossAcceptDate(String LossAcceptDate){
        this.LossAcceptDate = Str.rightTrim(LossAcceptDate);
    }

    /**
     * 获取属性LossAcceptDate
     * @return LossAcceptDate
     */
    public String getLossAcceptDate(){
        return LossAcceptDate;
    }

    /**
     * 设置属性AdjustFlag
     * @param AdjustFlag AdjustFlag
     */
    public void setAdjustFlag(String AdjustFlag){
        this.AdjustFlag = Str.rightTrim(AdjustFlag);
    }

    /**
     * 获取属性AdjustFlag
     * @return AdjustFlag
     */
    public String getAdjustFlag(){
        return AdjustFlag;
    }

    /**
     * 设置属性DecisionCode
     * @param DecisionCode DecisionCode
     */
    public void setDecisionCode(String DecisionCode){
        this.DecisionCode = Str.rightTrim(DecisionCode);
    }

    /**
     * 获取属性DecisionCode
     * @return DecisionCode
     */
    public String getDecisionCode(){
        return DecisionCode;
    }

    /**
     * 设置属性LicensePlateNo
     * @param LicensePlateNo LicensePlateNo
     */
    public void setLicensePlateNo(String LicensePlateNo){
        this.LicensePlateNo = Str.rightTrim(LicensePlateNo);
    }

    /**
     * 获取属性LicensePlateNo
     * @return LicensePlateNo
     */
    public String getLicensePlateNo(){
        return LicensePlateNo;
    }

    /**
     * 设置属性LicensePlateTypeCode
     * @param LicensePlateTypeCode LicensePlateTypeCode
     */
    public void setLicensePlateTypeCode(String LicensePlateTypeCode){
        this.LicensePlateTypeCode = Str.rightTrim(LicensePlateTypeCode);
    }

    /**
     * 获取属性LicensePlateTypeCode
     * @return LicensePlateTypeCode
     */
    public String getLicensePlateTypeCode(){
        return LicensePlateTypeCode;
    }

    /**
     * 设置属性JurisdictionAgencyCode
     * @param JurisdictionAgencyCode JurisdictionAgencyCode
     */
    public void setJurisdictionAgencyCode(String JurisdictionAgencyCode){
        this.JurisdictionAgencyCode = Str.rightTrim(JurisdictionAgencyCode);
    }

    /**
     * 获取属性JurisdictionAgencyCode
     * @return JurisdictionAgencyCode
     */
    public String getJurisdictionAgencyCode(){
        return JurisdictionAgencyCode;
    }

    /**
     * 设置属性DecisionTypeCode
     * @param DecisionTypeCode DecisionTypeCode
     */
    public void setDecisionTypeCode(String DecisionTypeCode){
        this.DecisionTypeCode = Str.rightTrim(DecisionTypeCode);
    }

    /**
     * 获取属性DecisionTypeCode
     * @return DecisionTypeCode
     */
    public String getDecisionTypeCode(){
        return DecisionTypeCode;
    }

    /**
     * 设置属性PeccancyNumber
     * @param PeccancyNumber PeccancyNumber
     */
    public void setPeccancyNumber(String PeccancyNumber){
        this.PeccancyNumber = Str.rightTrim(PeccancyNumber);
    }

    /**
     * 获取属性PeccancyNumber
     * @return PeccancyNumber
     */
    public String getPeccancyNumber(){
        return PeccancyNumber;
    }
    
    /**
     * 获取属性driverName
     * @return driverName
     */
    public String getDriverName() {
		return DriverName;
	}
    /**
     * 设置属性driverName
     * @param driverName driverName
     */
    public void setDriverName(String driverName) {
		this.DriverName = Str.rightTrim(driverName);
	}
	/**
     * 获取属性VouchCode
     * @return VouchCode
     */
	public String getVouchCode() {
		return VouchCode;
	}
	/**
     * 设置属性VouchCode
     * @param VouchCode VouchCode
     */
	public void setVouchCode(String vouchCode) {
		this.VouchCode = Str.rightTrim(vouchCode);
	}
	/**
     * 获取属性SanctionPerson
     * @return SanctionPerson
     */
	public String getSanctionPerson() {
		return SanctionPerson;
	}
	/**
     * 设置属性SanctionPerson
     * @param SanctionPerson SanctionPerson
     */
	public void setSanctionPerson(String sanctionPerson) {
		this.SanctionPerson = Str.rightTrim(sanctionPerson);
	}
	/**
     * 获取属性SanctionDate
     * @return SanctionDate
     */
	public String getSanctionDate() {
		return SanctionDate;
	}
	/**
     * 设置属性SanctionDate
     * @param SanctionDate SanctionDate
     */
	public void setSanctionDate(String sanctionDate) {
		this.SanctionDate = Str.rightTrim(sanctionDate);
	}
    
    /**
     * @param iSchema 对象CIEndorValidLossSchema
     */       
    public void setSchema(CIEndorValidLossSchema iSchema)
    {
        this.DemandNo = iSchema.getDemandNo();
        this.SerialNo = iSchema.getSerialNo();
        this.PolicyNo = iSchema.getPolicyNo();
        this.LossTime = iSchema.getLossTime();
        this.LossAddress = iSchema.getLossAddress();
        this.LossAction = iSchema.getLossAction();
        this.Coeff = iSchema.getCoeff();
        this.LossType = iSchema.getLossType();
        this.IdentifyType = iSchema.getIdentifyType();
        this.IdentifyNumber = iSchema.getIdentifyNumber();
        this.Remark = iSchema.getRemark();
        this.Flag = iSchema.getFlag();
        this.LossAcceptDate = iSchema.getLossAcceptDate();
        this.AdjustFlag = iSchema.getAdjustFlag();
        this.DecisionCode = iSchema.getDecisionCode();
        this.LicensePlateNo = iSchema.getLicensePlateNo();
        this.LicensePlateTypeCode = iSchema.getLicensePlateTypeCode();
        this.JurisdictionAgencyCode = iSchema.getJurisdictionAgencyCode();
        this.DecisionTypeCode = iSchema.getDecisionTypeCode();
        this.PeccancyNumber = iSchema.getPeccancyNumber();
        
        this.DriverName = iSchema.getDriverName();
        this.VouchCode = iSchema.VouchCode;
        this.SanctionPerson = iSchema.SanctionPerson;
        this.SanctionDate = iSchema.SanctionDate;
        
    }
}
