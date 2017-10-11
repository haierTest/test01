package com.sp.indiv.ci.schema;

import java.io.Serializable;

public class CIInsureCompareDetailSchema implements Serializable
{
	
    private String ComCode = "";
    
    private String CompareDate = "";
    
    private String OperateDate = "";
    
    private String BusinessNo = "";
    
    private String SerialNo = "";
    
    private String SerialNoCom = "";
    
    private String RequestType = "";
    
    private String ResponseCode = "";
    
    private String ResponseMessage = "";
    
    private String ReturnTotalNum = "";
    
    private String DemandNo = "";
    
    private String ProposalNo = "";
    
    private String PolicyNo = "";
    
    private String Flag = "";
    
	public String getBusinessNo() {
		return BusinessNo;
	}
	public void setBusinessNo(String businessNo) {
		BusinessNo = businessNo;
	}
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
	public String getOperateDate() {
		return OperateDate;
	}
	public void setOperateDate(String operateDate) {
		OperateDate = operateDate;
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
	public String getRequestType() {
		return RequestType;
	}
	public void setRequestType(String requestType) {
		RequestType = requestType;
	}
	public String getResponseCode() {
		return ResponseCode;
	}
	public void setResponseCode(String responseCode) {
		ResponseCode = responseCode;
	}
	public String getResponseMessage() {
		return ResponseMessage;
	}
	public void setResponseMessage(String responseMessage) {
		ResponseMessage = responseMessage;
	}
	public String getReturnTotalNum() {
		return ReturnTotalNum;
	}
	public void setReturnTotalNum(String returnTotalNum) {
		ReturnTotalNum = returnTotalNum;
	}
	public String getSerialNo() {
		return SerialNo;
	}
	public void setSerialNo(String serialNo) {
		SerialNo = serialNo;
	}
	public String getSerialNoCom() {
		return SerialNoCom;
	}
	public void setSerialNoCom(String serialNoCom) {
		SerialNoCom = serialNoCom;
	}
    
	/**
     * @param iSchema ∂‘œÛCIInsureCompareDetailSchema
     */       
    public void setSchema(CIInsureCompareDetailSchema iSchema)
    {
    	this.ComCode 		= iSchema.getComCode();
        this.CompareDate 	= iSchema.getCompareDate();
        this.OperateDate 	= iSchema.getOperateDate();
        this.BusinessNo 	= iSchema.getBusinessNo();
        this.SerialNo 		= iSchema.getSerialNo();
        this.SerialNoCom 	= iSchema.getSerialNoCom();
        this.RequestType 	= iSchema.getRequestType();
        this.ResponseCode 	= iSchema.getResponseCode();
        this.ResponseMessage = iSchema.getResponseMessage();
        this.ReturnTotalNum = iSchema.getReturnTotalNum();
        this.DemandNo 		= iSchema.getDemandNo();
        this.ProposalNo 	= iSchema.getProposalNo();
        this.PolicyNo 		= iSchema.getPolicyNo();
        this.Flag 			= iSchema.getFlag();
    }
}
