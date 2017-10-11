package com.sp.indiv.ci.schema;

import java.io.Serializable;

import com.sp.utility.string.Str;

/**
 * ����CIEndorValidLoss�����ݴ��������
 * ��pdm��ȡ���ݿ���Ϣ,�������ݿ�����ɶ�Ӧ��Dto��Schema��
 * <p>Title: SinosoftJavaTools v1.4</p>
 * <p>Description: �п���JavaԴ�����ɹ���</p>
 * <p>Company: Sinosoft</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>@createdate 2010-11-05</p>
 * @FirstAuthor: Zhouxianli(JavaTools v1.0)
 * @SecondAuthor: Yangkun(JavaTools v1.1->v1.2->v1.3->v1.4)
 * @LastVersion: v1.4.2
 * @UpdateList 1.����Schema�������ɹ��߼̳й�ϵ����;
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
     * ���캯��
     */       
    public CIEndorValidLossSchema(){
    }

    /**
     * ��������DemandNo
     * @param DemandNo DemandNo
     */
    public void setDemandNo(String DemandNo){
        this.DemandNo = Str.rightTrim(DemandNo);
    }

    /**
     * ��ȡ����DemandNo
     * @return DemandNo
     */
    public String getDemandNo(){
        return Str.rightTrim(DemandNo);
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
        return SerialNo;
    }

    /**
     * ��������PolicyNo
     * @param PolicyNo PolicyNo
     */
    public void setPolicyNo(String PolicyNo){
        this.PolicyNo = Str.rightTrim(PolicyNo);
    }

    /**
     * ��ȡ����PolicyNo
     * @return PolicyNo
     */
    public String getPolicyNo(){
        return PolicyNo;
    }

    /**
     * ��������LossTime
     * @param LossTime LossTime
     */
    public void setLossTime(String LossTime){
        this.LossTime = Str.rightTrim(LossTime);
    }

    /**
     * ��ȡ����LossTime
     * @return LossTime
     */
    public String getLossTime(){
        return LossTime;
    }

    /**
     * ��������LossAddress
     * @param LossAddress LossAddress
     */
    public void setLossAddress(String LossAddress){
        this.LossAddress = Str.rightTrim(LossAddress);
    }

    /**
     * ��ȡ����LossAddress
     * @return LossAddress
     */
    public String getLossAddress(){
        return LossAddress;
    }

    /**
     * ��������LossAction
     * @param LossAction LossAction
     */
    public void setLossAction(String LossAction){
        this.LossAction = Str.rightTrim(LossAction);
    }

    /**
     * ��ȡ����LossAction
     * @return LossAction
     */
    public String getLossAction(){
        return LossAction;
    }

    /**
     * ��������Coeff
     * @param Coeff Coeff
     */
    public void setCoeff(String Coeff){
        this.Coeff = Str.rightTrim(Coeff);
    }

    /**
     * ��ȡ����Coeff
     * @return Coeff
     */
    public String getCoeff(){
        return Coeff;
    }

    /**
     * ��������LossType
     * @param LossType LossType
     */
    public void setLossType(String LossType){
        this.LossType = Str.rightTrim(LossType);
    }

    /**
     * ��ȡ����LossType
     * @return LossType
     */
    public String getLossType(){
        return LossType;
    }

    /**
     * ��������IdentifyType
     * @param IdentifyType IdentifyType
     */
    public void setIdentifyType(String IdentifyType){
        this.IdentifyType = Str.rightTrim(IdentifyType);
    }

    /**
     * ��ȡ����IdentifyType
     * @return IdentifyType
     */
    public String getIdentifyType(){
        return IdentifyType;
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
        return IdentifyNumber;
    }

    /**
     * ��������Remark
     * @param Remark Remark
     */
    public void setRemark(String Remark){
        this.Remark = Str.rightTrim(Remark);
    }

    /**
     * ��ȡ����Remark
     * @return Remark
     */
    public String getRemark(){
        return Remark;
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
        return Flag;
    }

    /**
     * ��������LossAcceptDate
     * @param LossAcceptDate LossAcceptDate
     */
    public void setLossAcceptDate(String LossAcceptDate){
        this.LossAcceptDate = Str.rightTrim(LossAcceptDate);
    }

    /**
     * ��ȡ����LossAcceptDate
     * @return LossAcceptDate
     */
    public String getLossAcceptDate(){
        return LossAcceptDate;
    }

    /**
     * ��������AdjustFlag
     * @param AdjustFlag AdjustFlag
     */
    public void setAdjustFlag(String AdjustFlag){
        this.AdjustFlag = Str.rightTrim(AdjustFlag);
    }

    /**
     * ��ȡ����AdjustFlag
     * @return AdjustFlag
     */
    public String getAdjustFlag(){
        return AdjustFlag;
    }

    /**
     * ��������DecisionCode
     * @param DecisionCode DecisionCode
     */
    public void setDecisionCode(String DecisionCode){
        this.DecisionCode = Str.rightTrim(DecisionCode);
    }

    /**
     * ��ȡ����DecisionCode
     * @return DecisionCode
     */
    public String getDecisionCode(){
        return DecisionCode;
    }

    /**
     * ��������LicensePlateNo
     * @param LicensePlateNo LicensePlateNo
     */
    public void setLicensePlateNo(String LicensePlateNo){
        this.LicensePlateNo = Str.rightTrim(LicensePlateNo);
    }

    /**
     * ��ȡ����LicensePlateNo
     * @return LicensePlateNo
     */
    public String getLicensePlateNo(){
        return LicensePlateNo;
    }

    /**
     * ��������LicensePlateTypeCode
     * @param LicensePlateTypeCode LicensePlateTypeCode
     */
    public void setLicensePlateTypeCode(String LicensePlateTypeCode){
        this.LicensePlateTypeCode = Str.rightTrim(LicensePlateTypeCode);
    }

    /**
     * ��ȡ����LicensePlateTypeCode
     * @return LicensePlateTypeCode
     */
    public String getLicensePlateTypeCode(){
        return LicensePlateTypeCode;
    }

    /**
     * ��������JurisdictionAgencyCode
     * @param JurisdictionAgencyCode JurisdictionAgencyCode
     */
    public void setJurisdictionAgencyCode(String JurisdictionAgencyCode){
        this.JurisdictionAgencyCode = Str.rightTrim(JurisdictionAgencyCode);
    }

    /**
     * ��ȡ����JurisdictionAgencyCode
     * @return JurisdictionAgencyCode
     */
    public String getJurisdictionAgencyCode(){
        return JurisdictionAgencyCode;
    }

    /**
     * ��������DecisionTypeCode
     * @param DecisionTypeCode DecisionTypeCode
     */
    public void setDecisionTypeCode(String DecisionTypeCode){
        this.DecisionTypeCode = Str.rightTrim(DecisionTypeCode);
    }

    /**
     * ��ȡ����DecisionTypeCode
     * @return DecisionTypeCode
     */
    public String getDecisionTypeCode(){
        return DecisionTypeCode;
    }

    /**
     * ��������PeccancyNumber
     * @param PeccancyNumber PeccancyNumber
     */
    public void setPeccancyNumber(String PeccancyNumber){
        this.PeccancyNumber = Str.rightTrim(PeccancyNumber);
    }

    /**
     * ��ȡ����PeccancyNumber
     * @return PeccancyNumber
     */
    public String getPeccancyNumber(){
        return PeccancyNumber;
    }
    
    /**
     * ��ȡ����driverName
     * @return driverName
     */
    public String getDriverName() {
		return DriverName;
	}
    /**
     * ��������driverName
     * @param driverName driverName
     */
    public void setDriverName(String driverName) {
		this.DriverName = Str.rightTrim(driverName);
	}
	/**
     * ��ȡ����VouchCode
     * @return VouchCode
     */
	public String getVouchCode() {
		return VouchCode;
	}
	/**
     * ��������VouchCode
     * @param VouchCode VouchCode
     */
	public void setVouchCode(String vouchCode) {
		this.VouchCode = Str.rightTrim(vouchCode);
	}
	/**
     * ��ȡ����SanctionPerson
     * @return SanctionPerson
     */
	public String getSanctionPerson() {
		return SanctionPerson;
	}
	/**
     * ��������SanctionPerson
     * @param SanctionPerson SanctionPerson
     */
	public void setSanctionPerson(String sanctionPerson) {
		this.SanctionPerson = Str.rightTrim(sanctionPerson);
	}
	/**
     * ��ȡ����SanctionDate
     * @return SanctionDate
     */
	public String getSanctionDate() {
		return SanctionDate;
	}
	/**
     * ��������SanctionDate
     * @param SanctionDate SanctionDate
     */
	public void setSanctionDate(String sanctionDate) {
		this.SanctionDate = Str.rightTrim(sanctionDate);
	}
    
    /**
     * @param iSchema ����CIEndorValidLossSchema
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
