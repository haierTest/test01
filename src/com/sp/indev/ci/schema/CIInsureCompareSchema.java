package com.sp.indiv.ci.schema;

import java.io.Serializable;

public class CIInsureCompareSchema implements Serializable
{
	
    private String ComCode = "";
    
    private String SerialNo = "";
    
    private String CompareDate = "";
    
    private String OperateDate = "";
    
    private String StartGetDate = "";
    
    private String EndGetDate = "";
    
    private String PutConfirm  = "";
    
    private String GetConfirm = "";
    
    private String PutCancel = "";
    
    private String GetCancel = "";
    
    private String PutWithDraw = "";
    
    private String GetWithDraw = "";
    
    private String PutReport = "";
    
    private String GetReport = "";
    
    private String PutRegistration = "";
    
    private String GetRegistration = "";
    
    private String PutEndClaim = "";
    
    private String GetEndClaim = "";
    
    private String PutCancelClaim = "";
    
    private String GetCancelClaim = "";
    
    private String ResponseCode = "";
    
    private String ConfirmResult = "";
    
    private String ResponseMessage = "";
    
    private String Remark = "";
    
    private String Flag = "";

	public String getComCode() {
		return ComCode;
	}

	public void setComCode(String comCode) {
		ComCode = comCode;
	}
	
	public String getSerialNo() {
		return SerialNo;
	}

	public void setSerialNo(String serialNo) {
		SerialNo = serialNo;
	}

	public String getCompareDate() {
		return CompareDate;
	}

	public void setCompareDate(String compareDate) {
		CompareDate = compareDate;
	}

	public String getConfirmResult() {
		return ConfirmResult;
	}

	public void setConfirmResult(String confirmResult) {
		ConfirmResult = confirmResult;
	}

	public String getEndGetDate() {
		return EndGetDate;
	}

	public void setEndGetDate(String endGetDate) {
		EndGetDate = endGetDate;
	}

	public String getFlag() {
		return Flag;
	}

	public void setFlag(String flag) {
		Flag = flag;
	}

	public String getGetCancelClaim() {
		return GetCancelClaim;
	}

	public void setGetCancelClaim(String getCancelClaim) {
		GetCancelClaim = getCancelClaim;
	}

	public String getGetCancel() {
		return GetCancel;
	}

	public void setGetCancel(String getCancel) {
		GetCancel = getCancel;
	}

	public String getGetEndClaim() {
		return GetEndClaim;
	}

	public void setGetEndClaim(String getEndClaim) {
		GetEndClaim = getEndClaim;
	}

	public String getGetConfirm() {
		return GetConfirm;
	}

	public void setGetConfirm(String getConfirm) {
		GetConfirm = getConfirm;
	}

	public String getGetRegistration() {
		return GetRegistration;
	}

	public void setGetRegistration(String getRegistration) {
		GetRegistration = getRegistration;
	}

	public String getGetReport() {
		return GetReport;
	}

	public void setGetReport(String getReport) {
		GetReport = getReport;
	}

	public String getGetWithDraw() {
		return GetWithDraw;
	}

	public void setGetWithDraw(String getWithDraw) {
		GetWithDraw = getWithDraw;
	}

	public String getOperateDate() {
		return OperateDate;
	}

	public void setOperateDate(String operateDate) {
		OperateDate = operateDate;
	}

	public String getPutCancelClaim() {
		return PutCancelClaim;
	}

	public void setPutCancelClaim(String putCancelClaim) {
		PutCancelClaim = putCancelClaim;
	}

	public String getPutCancel() {
		return PutCancel;
	}

	public void setPutCancel(String putCancel) {
		PutCancel = putCancel;
	}

	public String getPutEndClaim() {
		return PutEndClaim;
	}

	public void setPutEndClaim(String putEndClaim) {
		PutEndClaim = putEndClaim;
	}

	public String getPutConfirm() {
		return PutConfirm;
	}

	public void setPutConfirm(String putConfirm) {
		PutConfirm = putConfirm;
	}

	public String getPutRegistration() {
		return PutRegistration;
	}

	public void setPutRegistration(String putRegistration) {
		PutRegistration = putRegistration;
	}

	public String getPutReport() {
		return PutReport;
	}

	public void setPutReport(String putReport) {
		PutReport = putReport;
	}

	public String getPutWithDraw() {
		return PutWithDraw;
	}

	public void setPutWithDraw(String putWithDraw) {
		PutWithDraw = putWithDraw;
	}

	public String getRemark() {
		return Remark;
	}

	public void setRemark(String remark) {
		Remark = remark;
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

	public String getStartGetDate() {
		return StartGetDate;
	}

	public void setStartGetDate(String startGetDate) {
		StartGetDate = startGetDate;
	}
	
	/**
     * @param iSchema ∂‘œÛCICaseDemandSchema
     */       
    public void setSchema(CIInsureCompareSchema iSchema)
    {
        this.ComCode 		= iSchema.getComCode();
        this.SerialNo    	= iSchema.getSerialNo();
        this.CompareDate 	= iSchema.getCompareDate();
        this.OperateDate	= iSchema.getOperateDate();
        this.StartGetDate 	= iSchema.getStartGetDate();
        this.EndGetDate 	= iSchema.getEndGetDate();
        this.PutConfirm  	= iSchema.getPutConfirm();
        this.GetConfirm 	= iSchema.getGetConfirm();
        this.PutCancel 		= iSchema.getPutCancel();
        this.GetCancel 		= iSchema.getGetCancel();
        this.PutWithDraw 	= iSchema.getPutWithDraw();
        this.GetWithDraw 	= iSchema.getGetWithDraw();
        this.PutReport 		= iSchema.getPutReport();
        this.GetReport 		= iSchema.getGetReport();
        this.PutRegistration = iSchema.getPutRegistration();
        this.GetRegistration = iSchema.getGetRegistration();
        this.PutEndClaim 	 = iSchema.getPutEndClaim();
        this.GetEndClaim 	 = iSchema.getGetEndClaim();
        this.PutCancelClaim = iSchema.getPutCancelClaim();
        this.GetCancelClaim = iSchema.getGetCancelClaim();
        this.ResponseCode 	= iSchema.getResponseCode();
        this.ConfirmResult 	= iSchema.getConfirmResult();
        this.ResponseMessage = iSchema.getResponseMessage();
        this.Remark 		= iSchema.getRemark();
        this.Flag 			= iSchema.getFlag();
    }
}
