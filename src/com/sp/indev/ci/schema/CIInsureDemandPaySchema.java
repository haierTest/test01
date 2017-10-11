package com.sp.indiv.ci.schema;

import java.io.Serializable;
import com.sp.utility.string.ChgDate;
import com.sp.utility.string.Str;

/**
 * ����XX��ѯXXXXX��-CIInsureDemandPay�����ݴ��������
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
     * ���캯��
     */       
    public CIInsureDemandPaySchema(){
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
     * ��������XX��˾����
     * @param PayCompany XX��˾����
     */
    public void setPayCompany(String PayCompany){
        this.PayCompany = Str.rightTrim(PayCompany);
    }

    /**
     * ��ȡ����XX��˾����
     * @return XX��˾����
     */
    public String getPayCompany(){
        return Str.rightTrim(PayCompany);
    }

    /**
     * ��������XX��
     * @param PolicyNo XX��
     */
    public void setPolicyNo(String PolicyNo){
        this.PolicyNo = Str.rightTrim(PolicyNo);
    }

    /**
     * ��ȡ����XX��
     * @return XX��
     */
    public String getPolicyNo(){
        return Str.rightTrim(PolicyNo);
    }

    /**
     * ��������XXXXXXXXXX��
     * @param KindCode XXXXXXXXXX��
     */
    public void setKindCode(String KindCode){
        this.KindCode = Str.rightTrim(KindCode);
    }

    /**
     * ��ȡ����XXXXXXXXXX��
     * @return XXXXXXXXXX��
     */
    public String getKindCode(){
        return Str.rightTrim(KindCode);
    }

    /**
     * ���������¹����
     * @param PayType �¹����
     */
    public void setPayType(String PayType){
        this.PayType = Str.rightTrim(PayType);
    }

    /**
     * ��ȡ�����¹����
     * @return �¹����
     */
    public String getPayType(){
        return Str.rightTrim(PayType);
    }

    /**
     * ������������������־
     * @param PersonPayType ����������־
     */
    public void setPersonPayType(String PersonPayType){
        this.PersonPayType = Str.rightTrim(PersonPayType);
    }

    /**
     * ��ȡ��������������־
     * @return ����������־
     */
    public String getPersonPayType(){
        return Str.rightTrim(PersonPayType);
    }

    /**
     * ���������ⰸ��
     * @param CompensateNo �ⰸ��
     */
    public void setCompensateNo(String CompensateNo){
        this.CompensateNo = Str.rightTrim(CompensateNo);
    }

    /**
     * ��ȡ�����ⰸ��
     * @return �ⰸ��
     */
    public String getCompensateNo(){
        return Str.rightTrim(CompensateNo);
    }

    /**
     * �������Գ�XXXXXʱ��
     * @param LossTime ��XXXXXʱ��
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
     * ��ȡ���Գ�XXXXXʱ��
     * @return ��XXXXXʱ��
     */
    public String getLossTime(){
        return Str.rightTrim(LossTime);
    }

    /**
     * �������������
     * @param LossFee �����
     */
    public void setLossFee(String LossFee){
        this.LossFee = Str.rightTrim(LossFee);
    }

    /**
     * ��ȡ���������
     * @return �����
     */
    public String getLossFee(){
        return Str.rightTrim(LossFee);
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
     * �������Խ᰸ʱ��
     * @return �᰸ʱ��
     */
    public void setEndCaseTime(String endCaseTime)
    {
    	this.EndCaseTime = endCaseTime;
    }
    
    /**
     * ��ȡ���Խ᰸ʱ��
     * @return �᰸ʱ��
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
     * ��ȡ����EffectiveDate
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
     * @param iSchema ����CIInsureDemandPaySchema
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
