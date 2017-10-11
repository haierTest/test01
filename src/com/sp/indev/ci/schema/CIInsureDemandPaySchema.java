package com.sp.indiv.ci.schema;

import java.io.Serializable;
import com.sp.utility.string.ChgDate;
import com.sp.utility.string.Str;

/**
 * 定义XX查询XXXXX表-CIInsureDemandPay的数据传输对象类
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>@createdate 2006-06-14</p>
 * @author DtoGenerator
 * @version 1.0
 */
public class CIInsureDemandPaySchema implements Serializable{
    
    private String DemandNo = "";
    
    private String SerialNo = "";
    
    private String PayCompany = "";
    
    private String PolicyNo = "";
    
    private String KindCode = "";
    
    private String PayType = "";
    
    private String PersonPayType = "";
    
    private String CompensateNo = "";
    
    private String LossTime = "";
    
    private String LossFee = "";
    
    private String Remark = "";
    
    private String Flag = "";
    
    private String EndCaseTime = "";
    
    
    private String AccidentDeathFlag = "";
    
    
    private String OptionType = "";
    
    
    private String TotalAmount = "";
    
    
    private String ClaimNotificationNo="";
    private String ClaimRegistrationNo="";
    private String CaseID="";
    
    
    private String EffectiveDate="";    
    private String ExpireDate="";      
    
    
    private String RiskWarningType = "";
    private String LossArea = "";
    
    
    private String InsurerArea = "";
    

	/**
     * 构造函数
     */       
    public CIInsureDemandPaySchema(){
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
     * 设置属性XX公司名称
     * @param PayCompany XX公司名称
     */
    public void setPayCompany(String PayCompany){
        this.PayCompany = Str.rightTrim(PayCompany);
    }

    /**
     * 获取属性XX公司名称
     * @return XX公司名称
     */
    public String getPayCompany(){
        return Str.rightTrim(PayCompany);
    }

    /**
     * 设置属性XX号
     * @param PolicyNo XX号
     */
    public void setPolicyNo(String PolicyNo){
        this.PolicyNo = Str.rightTrim(PolicyNo);
    }

    /**
     * 获取属性XX号
     * @return XX号
     */
    public String getPolicyNo(){
        return Str.rightTrim(PolicyNo);
    }

    /**
     * 设置属性XXXXXXXXXX别
     * @param KindCode XXXXXXXXXX别
     */
    public void setKindCode(String KindCode){
        this.KindCode = Str.rightTrim(KindCode);
    }

    /**
     * 获取属性XXXXXXXXXX别
     * @return XXXXXXXXXX别
     */
    public String getKindCode(){
        return Str.rightTrim(KindCode);
    }

    /**
     * 设置属性事故类别
     * @param PayType 事故类别
     */
    public void setPayType(String PayType){
        this.PayType = Str.rightTrim(PayType);
    }

    /**
     * 获取属性事故类别
     * @return 事故类别
     */
    public String getPayType(){
        return Str.rightTrim(PayType);
    }

    /**
     * 设置属性人身伤亡标志
     * @param PersonPayType 人身伤亡标志
     */
    public void setPersonPayType(String PersonPayType){
        this.PersonPayType = Str.rightTrim(PersonPayType);
    }

    /**
     * 获取属性人身伤亡标志
     * @return 人身伤亡标志
     */
    public String getPersonPayType(){
        return Str.rightTrim(PersonPayType);
    }

    /**
     * 设置属性赔案号
     * @param CompensateNo 赔案号
     */
    public void setCompensateNo(String CompensateNo){
        this.CompensateNo = Str.rightTrim(CompensateNo);
    }

    /**
     * 获取属性赔案号
     * @return 赔案号
     */
    public String getCompensateNo(){
        return Str.rightTrim(CompensateNo);
    }

    /**
     * 设置属性出XXXXX时间
     * @param LossTime 出XXXXX时间
     */
    public void setLossTime(String LossTime){
      ChgDate chgDate = new ChgDate(); 
      LossTime = chgDate.toFormat(LossTime);
      if  (LossTime == null || LossTime.length() == 0 ) 
      {
        this.LossTime = "";
      }else
      {
       this.LossTime = LossTime.trim().substring(0,10);
      }
    }

    /**
     * 获取属性出XXXXX时间
     * @return 出XXXXX时间
     */
    public String getLossTime(){
        return Str.rightTrim(LossTime);
    }

    /**
     * 设置属性赔款金额
     * @param LossFee 赔款金额
     */
    public void setLossFee(String LossFee){
        this.LossFee = Str.rightTrim(LossFee);
    }

    /**
     * 获取属性赔款金额
     * @return 赔款金额
     */
    public String getLossFee(){
        return Str.rightTrim(LossFee);
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
     * 设置属性结案时间
     * @return 结案时间
     */
    public void setEndCaseTime(String endCaseTime)
    {
    	this.EndCaseTime = endCaseTime;
    }
    
    /**
     * 获取属性结案时间
     * @return 结案时间
     */
    public String getEndCaseTime()
    {
    	return Str.rightTrim(EndCaseTime);
    }
    
    public String getAccidentDeathFlag() {
		return AccidentDeathFlag;
	}

	public void setAccidentDeathFlag(String accidentDeathFlag) {
		AccidentDeathFlag = accidentDeathFlag;
	}

	
    public String getOptionType() {
		return OptionType;
	}

	public void setOptionType(String optionType) {
		OptionType = Str.rightTrim(optionType);
	}
	
	public String getTotalAmount() {
		return TotalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		TotalAmount = Str.rightTrim(totalAmount);
	}

	
    
    public String getClaimNotificationNo(){
        return Str.rightTrim(ClaimNotificationNo);
    }
    
    public void setClaimNotificationNo(String ClaimNotificationNo){
        this.ClaimNotificationNo=ClaimNotificationNo;
    }
    
    public String getClaimRegistrationNo(){
        return Str.rightTrim(ClaimRegistrationNo);
    }
    
    public void setClaimRegistrationNo(String ClaimRegistrationNo){
        this.ClaimRegistrationNo=ClaimRegistrationNo;
    }
    
    public String getCaseID(){
        return Str.rightTrim(CaseID);
    }
    
    public void setCaseID(String CaseID){
        this.CaseID=CaseID;
    }
    
   
    public void setEffectiveDate(String EffectiveDate){
      ChgDate chgDate = new ChgDate();
      EffectiveDate = chgDate.toFormat(EffectiveDate);
      if  (EffectiveDate == null || EffectiveDate.length() == 0 )
      {
        this.EffectiveDate = "";
      }else
      {
       this.EffectiveDate = EffectiveDate.trim().substring(0,10);
      }
    }

    /**
     * 获取属性EffectiveDate
     * @return EffectiveDate
     */
    public String getEffectiveDate(){
        return Str.rightTrim(EffectiveDate);
    }
    public void setExpireDate(String ExpireDate){
        ChgDate chgDate = new ChgDate(); 
        ExpireDate = chgDate.toFormat(ExpireDate);
        if  (ExpireDate == null || ExpireDate.length() == 0 ) 
        {
          this.ExpireDate = "";
        }else
        {
         this.ExpireDate = ExpireDate.trim().substring(0,10);
        }
      }
    public String getExpireDate(){
        return Str.rightTrim(ExpireDate);
    }
   
    
    
	public String getLossArea() {
		return Str.rightTrim(LossArea);
	}

	public void setLossArea(String lossArea) {
		LossArea = lossArea;
	}

	public String getRiskWarningType() {
		return Str.rightTrim(RiskWarningType);
	}

	public void setRiskWarningType(String riskWarningType) {
		RiskWarningType = riskWarningType;
	}
    
	
	public String getInsurerArea() {
		return Str.rightTrim(InsurerArea);
	}

	public void setInsurerArea(String insurerArea) {
		InsurerArea = insurerArea;
	}
	
    /**
     * @param iSchema 对象CIInsureDemandPaySchema
     */       
    public void setSchema(CIInsureDemandPaySchema iSchema)
    {
        this.DemandNo = iSchema.getDemandNo();
        this.SerialNo = iSchema.getSerialNo();
        this.PayCompany = iSchema.getPayCompany();
        this.PolicyNo = iSchema.getPolicyNo();
        this.KindCode = iSchema.getKindCode();
        this.PayType = iSchema.getPayType();
        this.PersonPayType = iSchema.getPersonPayType();
        this.CompensateNo = iSchema.getCompensateNo();
        this.LossTime = iSchema.getLossTime();
        this.LossFee = iSchema.getLossFee();
        this.Remark = iSchema.getRemark();
        this.Flag = iSchema.getFlag();
        this.EndCaseTime = iSchema.getEndCaseTime();
        this.AccidentDeathFlag = iSchema.getAccidentDeathFlag();
        
        this.OptionType = iSchema.getOptionType();
        this.TotalAmount = iSchema.getTotalAmount();
        
        
        this.ClaimNotificationNo=iSchema.getClaimNotificationNo();
        this.ClaimRegistrationNo=iSchema.getClaimRegistrationNo();
        this.CaseID=iSchema.getCaseID();
        
        
        this.EffectiveDate=iSchema.getEffectiveDate();
        this.ExpireDate=iSchema.getExpireDate();
        
        
        this.LossArea=iSchema.getLossArea();
        this.RiskWarningType=iSchema.getRiskWarningType();
        
        
        this.InsurerArea = iSchema.getInsurerArea();
        
    }



}
