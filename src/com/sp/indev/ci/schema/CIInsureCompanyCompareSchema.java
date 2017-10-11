package com.sp.indiv.ci.schema;

import java.io.Serializable;

public class CIInsureCompanyCompareSchema implements Serializable
{
	
    private String ComCode = "";
    
    private String CompareDate = "";
    
    private String OperateDate = "";
    
    private String PolicyNo = "";
    
    private String SerialNo = "";
    
    private String ProposalNo  = "";
    
    private String DemandNo = "";
    
    private String ValidNo = "";
    
    private String Flag = "";
    
    private String UnderWriteFlag = "";
    
    private String OthFlag = "";
    
    private String UnderWriteEndDate = "";
    
	public String getComCode() {
		return ComCode;
	}
	public void setComCode(String comCode) {
		ComCode = comCode;
	}
	public String getCompareDate() {
		return CompareDate;
	}
	public void setCompareDate(String compareDate) {
		CompareDate = compareDate;
	}
	
	public String getOperateDate() {
		return OperateDate;
	}
	public void setOperateDate(String operateDate) {
		OperateDate = operateDate;
	}
	
	public String getDemandNo() {
		return DemandNo;
	}
	public void setDemandNo(String demandNo) {
		DemandNo = demandNo;
	}
	public String getFlag() {
		return Flag;
	}
	public void setFlag(String flag) {
		Flag = flag;
	}
	public String getOthFlag() {
		return OthFlag;
	}
	public void setOthFlag(String othFlag) {
		OthFlag = othFlag;
	}
	public String getPolicyNo() {
		return PolicyNo;
	}
	public void setPolicyNo(String policyNo) {
		PolicyNo = policyNo;
	}
	public String getProposalNo() {
		return ProposalNo;
	}
	public void setProposalNo(String proposalNo) {
		ProposalNo = proposalNo;
	}
	public String getSerialNo() {
		return SerialNo;
	}
	public void setSerialNo(String serialNo) {
		SerialNo = serialNo;
	}
	public String getUnderWriteEndDate() {
		return UnderWriteEndDate;
	}
	public void setUnderWriteEndDate(String underWriteEndDate) {
		UnderWriteEndDate = underWriteEndDate;
	}
	public String getUnderWriteFlag() {
		return UnderWriteFlag;
	}
	public void setUnderWriteFlag(String underWriteFlag) {
		UnderWriteFlag = underWriteFlag;
	}
	public String getValidNo() {
		return ValidNo;
	}
	public void setValidNo(String validNo) {
		ValidNo = validNo;
	}
	
	/**
     * @param iSchema ∂‘œÛCICaseDemandSchema
     */       
    public void setSchema(CIInsureCompanyCompareSchema iSchema)
    {
        this.ComCode = iSchema.getComCode();
        this.CompareDate = iSchema.getCompareDate();
        this.OperateDate = iSchema.getOperateDate();
        this.PolicyNo = iSchema.getPolicyNo();
        this.SerialNo = iSchema.getSerialNo();
        this.ProposalNo = iSchema.getProposalNo();
        this.DemandNo = iSchema.getDemandNo();
        this.ValidNo = iSchema.getValidNo();
        this.Flag = iSchema.getFlag();
        this.UnderWriteFlag = iSchema.getUnderWriteFlag();
        this.OthFlag = iSchema.getOthFlag();
        this.UnderWriteEndDate = iSchema.getUnderWriteEndDate();
    }
}
