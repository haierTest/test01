package com.sp.indiv.ci.schema;

import java.io.Serializable;
import com.sp.utility.string.ChgDate;
import com.sp.utility.string.Str;

/**
 * ����PrpCIEndorClaim�����ݴ��������
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>@createdate 2007-08-23</p>
 * @author DtoGenerator
 * @version 1.0
 */
public class PrpCIEndorClaimSchema implements Serializable{
    
    private String DemandNo = "";
    
    private String SerialNo = "";
    
    private String PayCompany = "";
    
    private String PolicyNo = "";
    
    private String PersonPayType = "";
    
    private String CompensateNo = "";
    
    private String ClaimNo = "";
    
    private String LossTime = "";
    
    private String EndCaseTime = "";
    
    private String Remarks = "";
    
    private String Flag = "";
    
    private String RegistNo  = ""; 
    private String CaseID = "";              
    
    
    private String EffectiveDate = "";
    private String ExpireDate = "";
    
    
    private String totalAmount = "";
    
    
    private String insurerArea = "";
    
    /**
     * ���캯��
     */       
    public PrpCIEndorClaimSchema(){
    }

    /**
     * ��������DemandNod
     * @param DemandNo DemandNod
     */
    public void setDemandNo(String DemandNo){
        this.DemandNo = Str.rightTrim(DemandNo);
    }

    /**
     * ��ȡ����DemandNod
     * @return DemandNod
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
        return Str.rightTrim(SerialNo);
    }

    /**
     * ��������PayCompany
     * @param PayCompany PayCompany
     */
    public void setPayCompany(String PayCompany){
        this.PayCompany = Str.rightTrim(PayCompany);
    }

    /**
     * ��ȡ����PayCompany
     * @return PayCompany
     */
    public String getPayCompany(){
        return Str.rightTrim(PayCompany);
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
        return Str.rightTrim(PolicyNo);
    }

    /**
     * ��������PersonPayType
     * @param PersonPayType PersonPayType
     */
    public void setPersonPayType(String PersonPayType){
        this.PersonPayType = Str.rightTrim(PersonPayType);
    }

    /**
     * ��ȡ����PersonPayType
     * @return PersonPayType
     */
    public String getPersonPayType(){
        return Str.rightTrim(PersonPayType);
    }

    /**
     * ��������CompensateNo
     * @param CompensateNo CompensateNo
     */
    public void setCompensateNo(String CompensateNo){
        this.CompensateNo = Str.rightTrim(CompensateNo);
    }

    /**
     * ��ȡ����CompensateNo
     * @return CompensateNo
     */
    public String getCompensateNo(){
        return Str.rightTrim(CompensateNo);
    }

    /**
     * ��������ClaimNo
     * @param ClaimNo ClaimNo
     */
    public void setClaimNo(String ClaimNo){
        this.ClaimNo = Str.rightTrim(ClaimNo);
    }

    /**
     * ��ȡ����ClaimNo
     * @return ClaimNo
     */
    public String getClaimNo(){
        return Str.rightTrim(ClaimNo);
    }

    /**
     * ��������LossTime
     * @param LossTime LossTime
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
     * ��ȡ����LossTime
     * @return LossTime
     */
    public String getLossTime(){
        return Str.rightTrim(LossTime);
    }

    /**
     * ��������EndCaseTime
     * @param EndCaseTime EndCaseTime
     */
    public void setEndCaseTime(String EndCaseTime){
      ChgDate chgDate = new ChgDate(); 
      EndCaseTime = chgDate.toFormat(EndCaseTime);
      if  (EndCaseTime == null || EndCaseTime.length() == 0 ) 
      {
        this.EndCaseTime = "";
      }else
      {
       this.EndCaseTime = EndCaseTime.trim().substring(0,10);
      }
    }

    /**
     * ��ȡ����EndCaseTime
     * @return EndCaseTime
     */
    public String getEndCaseTime(){
        return Str.rightTrim(EndCaseTime);
    }

    /**
     * ��������Remarks
     * @param Remarks Remarks
     */
    public void setRemarks(String Remarks){
        this.Remarks = Str.rightTrim(Remarks);
    }

    /**
     * ��ȡ����Remarks
     * @return Remarks
     */
    public String getRemarks(){
        return Str.rightTrim(Remarks);
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
    
    public void setRegistNo(String RegistNo){
        this.RegistNo = Str.rightTrim(RegistNo);
    }
    public String getRegistNo(){
        return Str.rightTrim(RegistNo);
    }
    public void setCaseID(String CaseID){
        this.CaseID = Str.rightTrim(CaseID);
    }
    public String getCaseID(){
        return Str.rightTrim(CaseID);
    }
    
    
    /**
     * ��������EffectiveDate
     * @param EffectiveDate EffectiveDate
     */
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
    
    /**
     * ��������ExpireDate
     * @param ExpireDate ExpireDate
     */
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

    /**
     * ��ȡ����ExpireDate
     * @return ExpireDate
     */
    public String getExpireDate(){
        return Str.rightTrim(ExpireDate);
    }
    
    
    /**
     * ��ȡ����totalAmount
     * @return totalAmount
     */
    public String getTotalAmount() {
        return totalAmount;
    }
    
    /**
     * ��������totalAmount
     * @param totalAmount totalAmount
     */
    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }
    
    
    public String getInsurerArea() {
		return Str.rightTrim(insurerArea);
	}

	public void setInsurerArea(String insurerArea) {
		this.insurerArea = Str.rightTrim(insurerArea);
	}
	
	
    /**
     * @param iSchema ����PrpCIEndorClaimSchema
     */       
    public void setSchema(PrpCIEndorClaimSchema iSchema)
    {
        this.DemandNo = iSchema.getDemandNo();
        this.SerialNo = iSchema.getSerialNo();
        this.PayCompany = iSchema.getPayCompany();
        this.PolicyNo = iSchema.getPolicyNo();
        this.PersonPayType = iSchema.getPersonPayType();
        this.CompensateNo = iSchema.getCompensateNo();
        this.ClaimNo = iSchema.getClaimNo();
        this.LossTime = iSchema.getLossTime();
        this.EndCaseTime = iSchema.getEndCaseTime();
        this.Remarks = iSchema.getRemarks();
        this.Flag = iSchema.getFlag();
        
        this.RegistNo = iSchema.getRegistNo();
        this.CaseID = iSchema.getCaseID();
        
        
        this.EffectiveDate = iSchema.getEffectiveDate();
        this.ExpireDate = iSchema.getExpireDate();
        
        
        this.totalAmount=iSchema.getTotalAmount();
        
        
        this.insurerArea = iSchema.getInsurerArea();
        
    }
}
