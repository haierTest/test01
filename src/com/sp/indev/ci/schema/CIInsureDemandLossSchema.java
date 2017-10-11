package com.sp.indiv.ci.schema;

import java.io.Serializable;
import com.sp.utility.string.ChgDate;
import com.sp.utility.string.Str;

/**
 * ����XX��ѯΥ�±�-CIInsureDemandLoss�����ݴ��������
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>@createdate 2006-06-14</p>
 * @author DtoGenerator
 * @version 1.0
 */
public class CIInsureDemandLossSchema implements Serializable{
    
    private String DemandNo = "";
    
    private String SerialNo = "";
    
    private String LossTime = "";
    
    private String LossAddress = "";
    
    private String LossAction = "";
    
    private String Coeff = "";
    
    private String LossType = "";
    
    private String IdentifyType = "";
    
    private String IdentifyNumber = "";
    
    private String Remark = "";
    
    private String Flag = "";
    
    private String acceptDate = "";
    
    
    private String AdjustFlag = "";
    
    
    private String DecisionCode="";
    private String DecisionTypeCode="";
    private String LicensePlateNo="";
    private String LicensePlateTypeCode="";
    private String JurisdictionAgencyCode="";
    
    
    private String PeccancyNumber="";
    
    
    private String ProcessingStatus="";
    
    
    private String DriverName="";
    private String PeccancyDetail="";
    
    
    private String VouchCode="";
    private String SanctionPerson="";
    private String SanctionDate="";
	
    
    private String MonitorId = "";
    
    /**
     * ���캯��
     */       
    public CIInsureDemandLossSchema(){
    }

    /**
     * �������Բ�ѯ��
     * @param DemandNo ��ѯ��
     */
    public void setDemandNo(String DemandNo){
        this.DemandNo = Str.rightTrim(DemandNo);
    }

    /**
     * ��ȡ���Բ�ѯ��
     * @return ��ѯ��
     */
    public String getDemandNo(){
        return Str.rightTrim(DemandNo);
    }

    /**
     * �����������
     * @param SerialNo ���
     */
    public void setSerialNo(String SerialNo){
        this.SerialNo = Str.rightTrim(SerialNo);
    }

    /**
     * ��ȡ�������
     * @return ���
     */
    public String getSerialNo(){
        return Str.rightTrim(SerialNo);
    }

    /**
     * ��������Υ��ʱ��
     * @param LossTime Υ��ʱ��
     */
    public void setLossTime(String LossTime){
        this.LossTime = Str.rightTrim(LossTime);
    }

    /**
     * ��ȡ����Υ��ʱ��
     * @return Υ��ʱ��
     */
    public String getLossTime(){
        return Str.rightTrim(LossTime);
    }

    /**
     * ��������Υ���ص�
     * @param LossAddress Υ���ص�
     */
    public void setLossAddress(String LossAddress){
        this.LossAddress = Str.rightTrim(LossAddress);
    }

    /**
     * ��ȡ����Υ���ص�
     * @return Υ���ص�
     */
    public String getLossAddress(){
        return Str.rightTrim(LossAddress);
    }

    /**
     * ��������Υ����Ϊ����
     * @param LossAction Υ����Ϊ����
     */
    public void setLossAction(String LossAction){
        this.LossAction = Str.rightTrim(LossAction);
    }

    /**
     * ��ȡ����Υ����Ϊ����
     * @return Υ����Ϊ����
     */
    public String getLossAction(){
        return Str.rightTrim(LossAction);
    }

    /**
     * �������Ե���ϵ��
     * @param Coeff ����ϵ��
     */
    public void setCoeff(String Coeff){
        this.Coeff = Str.rightTrim(Coeff);
    }

    /**
     * ��ȡ���Ե���ϵ��
     * @return ����ϵ��
     */
    public String getCoeff(){
        return Str.rightTrim(Coeff);
    }

    /**
     * ��������Υ����Ϊ����
     * @param LossType Υ����Ϊ����
     */
    public void setLossType(String LossType){
        this.LossType = Str.rightTrim(LossType);
    }

    /**
     * ��ȡ����Υ����Ϊ����
     * @return Υ����Ϊ����
     */
    public String getLossType(){
        return Str.rightTrim(LossType);
    }

    /**
     * ��������Υ����ʻԱ���֤������
     * @param IdentifyType Υ����ʻԱ���֤������
     */
    public void setIdentifyType(String IdentifyType){
        this.IdentifyType = Str.rightTrim(IdentifyType);
    }

    /**
     * ��ȡ����Υ����ʻԱ���֤������
     * @return Υ����ʻԱ���֤������
     */
    public String getIdentifyType(){
        return Str.rightTrim(IdentifyType);
    }

    /**
     * ��������Υ����ʻԱ���֤������
     * @param IdentifyNumber Υ����ʻԱ���֤������
     */
    public void setIdentifyNumber(String IdentifyNumber){
        this.IdentifyNumber = Str.rightTrim(IdentifyNumber);
    }

    /**
     * ��ȡ����Υ����ʻԱ���֤������
     * @return Υ����ʻԱ���֤������
     */
    public String getIdentifyNumber(){
        return Str.rightTrim(IdentifyNumber);
    }

    /**
     * �������Ա�ע
     * @param Remark ��ע
     */
    public void setRemark(String Remark){
        this.Remark = Str.rightTrim(Remark);
    }

    /**
     * ��ȡ���Ա�ע
     * @return ��ע
     */
    public String getRemark(){
        return Str.rightTrim(Remark);
    }

    /**
     * ��������״̬�ֶ�
     * @param Flag ״̬�ֶ�
     */
    public void setFlag(String Flag){
        this.Flag = Str.rightTrim(Flag);
    }

    /**
     * ��ȡ����״̬�ֶ�
     * @return ״̬�ֶ�
     */
    public String getFlag(){
        return Str.rightTrim(Flag);
    }
    
    /**
     * ��������Υ������ʱ��
     * @return Υ������ʱ��
     */
    public void setAcceptDate(String acceptDate)
    {
    	this.acceptDate = acceptDate;
    }
    
    /**
     * ��ȡ����Υ������ʱ��
     * @return Υ������ʱ��
     */
    public String getAcceptDate()
    {
    	return Str.rightTrim(acceptDate);
    }

    
    /**
     * ��������Υ������ʱ��
     * @return Υ������ʱ��
     */
    public void setAdjustFlag(String AdjustFlag)
    {
    	this.AdjustFlag = AdjustFlag;
    }
    
    /**
     * ��ȡ����Υ������ʱ��
     * @return Υ������ʱ��
     */
    public String getAdjustFlag()
    {
    	return Str.rightTrim(this.AdjustFlag);
    }
    
    
    
    public String getDecisionCode(){
        return Str.rightTrim(DecisionCode);
    }
    
    public void setDecisionCode(String DecisionCode){
        this.DecisionCode=DecisionCode;
    }
    
    public String getDecisionTypeCode(){
        return Str.rightTrim(DecisionTypeCode);
    }
    
    public void setDecisionTypeCode(String DecisionTypeCode){
        this.DecisionTypeCode=DecisionTypeCode;
    }
    
    public String getLicensePlateNo(){
        return Str.rightTrim(LicensePlateNo);
    }
    
    public void setLicensePlateNo(String LicensePlateNo){
        this.LicensePlateNo=LicensePlateNo;
    } 
    
    public String getLicensePlateTypeCode(){
        return Str.rightTrim(LicensePlateTypeCode);
    }
    
    public void setLicensePlateTypeCode(String LicensePlateTypeCode){
        this.LicensePlateTypeCode=LicensePlateTypeCode;
    }
    
    public String getJurisdictionAgencyCode(){
        return Str.rightTrim(JurisdictionAgencyCode);
    }
    
    public void setJurisdictionAgencyCode(String JurisdictionAgencyCode){
        this.JurisdictionAgencyCode=JurisdictionAgencyCode;
    }
    
    
    public String getPeccancyNumber(){
        return Str.rightTrim(PeccancyNumber);
    }
    
    public void setPeccancyNumber(String PeccancyNumber){
        this.PeccancyNumber=PeccancyNumber;
    }
    
    
    public String getProcessingStatus(){
        return Str.rightTrim(ProcessingStatus);
    }
    
    public void setProcessingStatus(String ProcessingStatus){
        this.ProcessingStatus=ProcessingStatus;
    }
    
    
    
    public String getDriverName(){
        return Str.rightTrim(DriverName);
    }
    
    public void setDriverName(String DriverName){
        this.DriverName=DriverName;
    }
    public String getPeccancyDetail(){
        return Str.rightTrim(PeccancyDetail);
    }
    
    public void setPeccancyDetail(String PeccancyDetail){
        this.PeccancyDetail=PeccancyDetail;
    }
    
	
    public String getVouchCode() {
		return Str.rightTrim(VouchCode);
	}

	public void setVouchCode(String vouchCode) {
		this.VouchCode = vouchCode;
	}

	public String getSanctionPerson() {
		return Str.rightTrim(SanctionPerson);
	}

	public void setSanctionPerson(String sanctionPerson) {
		this.SanctionPerson = sanctionPerson;
	}

	public String getSanctionDate() {
		return Str.rightTrim(SanctionDate);
	}

	public void setSanctionDate(String sanctionDate) {
		this.SanctionDate = sanctionDate;
	}
    
    
	public String getMonitorId() {
		return MonitorId;
	}

	public void setMonitorId(String monitorId) {
		MonitorId = monitorId;
	}
    
    /**
     * @param iSchema ����CIInsureDemandLossSchema
     */       
    public void setSchema(CIInsureDemandLossSchema iSchema)
    {
        this.DemandNo = iSchema.getDemandNo();
        this.SerialNo = iSchema.getSerialNo();
        this.LossTime = iSchema.getLossTime();
        this.LossAddress = iSchema.getLossAddress();
        this.LossAction = iSchema.getLossAction();
        this.Coeff = iSchema.getCoeff();
        this.LossType = iSchema.getLossType();
        this.IdentifyType = iSchema.getIdentifyType();
        this.IdentifyNumber = iSchema.getIdentifyNumber();
        this.Remark = iSchema.getRemark();
        this.Flag = iSchema.getFlag();
        this.acceptDate = iSchema.getAcceptDate();
        
        this.AdjustFlag = iSchema.getAdjustFlag();
        
        
        this.DecisionCode=iSchema.getDecisionCode();
        this.DecisionTypeCode=iSchema.getDecisionTypeCode();
        this.LicensePlateNo=iSchema.getLicensePlateNo();
        this.LicensePlateTypeCode=iSchema.getLicensePlateTypeCode();
        this.JurisdictionAgencyCode=iSchema.getJurisdictionAgencyCode();
        
        
        this.PeccancyNumber=iSchema.getPeccancyNumber();
        
        
        this.ProcessingStatus=iSchema.getProcessingStatus();
        
        
        this.DriverName=iSchema.getDriverName();
        this.PeccancyDetail=iSchema.getPeccancyDetail();
        
        
        this.VouchCode = iSchema.getVouchCode();
        this.SanctionPerson = iSchema.getSanctionPerson();
        this.SanctionDate = iSchema.getSanctionDate();
        
        
        this.MonitorId = iSchema.getMonitorId();
        
    }
}
