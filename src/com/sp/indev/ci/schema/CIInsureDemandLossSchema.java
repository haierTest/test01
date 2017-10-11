package com.sp.indiv.ci.schema;

import java.io.Serializable;
import com.sp.utility.string.ChgDate;
import com.sp.utility.string.Str;

/**
 * 定义XX查询违章表-CIInsureDemandLoss的数据传输对象类
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
     * 构造函数
     */       
    public CIInsureDemandLossSchema(){
    }

    /**
     * 设置属性查询码
     * @param DemandNo 查询码
     */
    public void setDemandNo(String DemandNo){
        this.DemandNo = Str.rightTrim(DemandNo);
    }

    /**
     * 获取属性查询码
     * @return 查询码
     */
    public String getDemandNo(){
        return Str.rightTrim(DemandNo);
    }

    /**
     * 设置属性序号
     * @param SerialNo 序号
     */
    public void setSerialNo(String SerialNo){
        this.SerialNo = Str.rightTrim(SerialNo);
    }

    /**
     * 获取属性序号
     * @return 序号
     */
    public String getSerialNo(){
        return Str.rightTrim(SerialNo);
    }

    /**
     * 设置属性违法时间
     * @param LossTime 违法时间
     */
    public void setLossTime(String LossTime){
        this.LossTime = Str.rightTrim(LossTime);
    }

    /**
     * 获取属性违法时间
     * @return 违法时间
     */
    public String getLossTime(){
        return Str.rightTrim(LossTime);
    }

    /**
     * 设置属性违法地点
     * @param LossAddress 违法地点
     */
    public void setLossAddress(String LossAddress){
        this.LossAddress = Str.rightTrim(LossAddress);
    }

    /**
     * 获取属性违法地点
     * @return 违法地点
     */
    public String getLossAddress(){
        return Str.rightTrim(LossAddress);
    }

    /**
     * 设置属性违法行为代码
     * @param LossAction 违法行为代码
     */
    public void setLossAction(String LossAction){
        this.LossAction = Str.rightTrim(LossAction);
    }

    /**
     * 获取属性违法行为代码
     * @return 违法行为代码
     */
    public String getLossAction(){
        return Str.rightTrim(LossAction);
    }

    /**
     * 设置属性调整系数
     * @param Coeff 调整系数
     */
    public void setCoeff(String Coeff){
        this.Coeff = Str.rightTrim(Coeff);
    }

    /**
     * 获取属性调整系数
     * @return 调整系数
     */
    public String getCoeff(){
        return Str.rightTrim(Coeff);
    }

    /**
     * 设置属性违法行为分类
     * @param LossType 违法行为分类
     */
    public void setLossType(String LossType){
        this.LossType = Str.rightTrim(LossType);
    }

    /**
     * 获取属性违法行为分类
     * @return 违法行为分类
     */
    public String getLossType(){
        return Str.rightTrim(LossType);
    }

    /**
     * 设置属性违法驾驶员身份证明类型
     * @param IdentifyType 违法驾驶员身份证明类型
     */
    public void setIdentifyType(String IdentifyType){
        this.IdentifyType = Str.rightTrim(IdentifyType);
    }

    /**
     * 获取属性违法驾驶员身份证明类型
     * @return 违法驾驶员身份证明类型
     */
    public String getIdentifyType(){
        return Str.rightTrim(IdentifyType);
    }

    /**
     * 设置属性违法驾驶员身份证明号码
     * @param IdentifyNumber 违法驾驶员身份证明号码
     */
    public void setIdentifyNumber(String IdentifyNumber){
        this.IdentifyNumber = Str.rightTrim(IdentifyNumber);
    }

    /**
     * 获取属性违法驾驶员身份证明号码
     * @return 违法驾驶员身份证明号码
     */
    public String getIdentifyNumber(){
        return Str.rightTrim(IdentifyNumber);
    }

    /**
     * 设置属性备注
     * @param Remark 备注
     */
    public void setRemark(String Remark){
        this.Remark = Str.rightTrim(Remark);
    }

    /**
     * 获取属性备注
     * @return 备注
     */
    public String getRemark(){
        return Str.rightTrim(Remark);
    }

    /**
     * 设置属性状态字段
     * @param Flag 状态字段
     */
    public void setFlag(String Flag){
        this.Flag = Str.rightTrim(Flag);
    }

    /**
     * 获取属性状态字段
     * @return 状态字段
     */
    public String getFlag(){
        return Str.rightTrim(Flag);
    }
    
    /**
     * 设置属性违法受理时间
     * @return 违法受理时间
     */
    public void setAcceptDate(String acceptDate)
    {
    	this.acceptDate = acceptDate;
    }
    
    /**
     * 获取属性违法受理时间
     * @return 违法受理时间
     */
    public String getAcceptDate()
    {
    	return Str.rightTrim(acceptDate);
    }

    
    /**
     * 设置属性违法受理时间
     * @return 违法受理时间
     */
    public void setAdjustFlag(String AdjustFlag)
    {
    	this.AdjustFlag = AdjustFlag;
    }
    
    /**
     * 获取属性违法受理时间
     * @return 违法受理时间
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
     * @param iSchema 对象CIInsureDemandLossSchema
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
