package com.sp.indiv.ci.schema;

import com.sp.utility.string.Str;

/**
 * 商业XXXXX平台XXXXX查询详情schema
 * 
 * @author luogang
 * 
 */
public class CIQueryClaimInfoDetailSchema {
	private String CompanyId = "";

	private String PolicyNo = "";

	private String StartDate = "";

	private String EndDate = "";

	private String RegisreationNo = "";

	private String AccidentTime = "";

	private String EndCaseTime = "";

	private String Estimate = "";

	private String ClaimAmount = "";

	private String ClaimStatus = "";

	public String toString() {
		StringBuffer sb = new StringBuffer(250);
		sb.append("[CompanyId=" + CompanyId);
		sb.append(",PolicyNo=" + PolicyNo);
		sb.append(",StartDate=" + StartDate);
		sb.append(",EndDate=" + EndDate);
		sb.append(",RegisreationNo=" + RegisreationNo);
		sb.append(",AccidentTime=" + AccidentTime);
		sb.append(",EndCaseTime=" + EndCaseTime);
		sb.append(",Estimate=" + Estimate);
		sb.append(",ClaimAmount=" + ClaimAmount);
		sb.append(",ClaimStatus=" + ClaimStatus);
		return sb.toString();
	}

	public String getAccidentTime() {
		return AccidentTime;
	}

	public void setAccidentTime(String accidentTime) {
		AccidentTime = Str.rightTrim(accidentTime);
	}

	public String getClaimAmount() {
		return ClaimAmount;
	}

	public void setClaimAmount(String claimAmount) {
		ClaimAmount = Str.rightTrim(claimAmount);
	}

	public String getClaimStatus() {
		return ClaimStatus;
	}

	public void setClaimStatus(String claimStatus) {
		ClaimStatus = Str.rightTrim(claimStatus);
	}

	public String getCompanyId() {
		return CompanyId;
	}

	public void setCompanyId(String companyId) {
		CompanyId = Str.rightTrim(companyId);
	}

	public String getEndCaseTime() {
		return EndCaseTime;
	}

	public void setEndCaseTime(String endCaseTime) {
		EndCaseTime = Str.rightTrim(endCaseTime);
	}

	public String getEndDate() {
		return EndDate;
	}

	public void setEndDate(String endDate) {
		EndDate = Str.rightTrim(endDate);
	}

	public String getEstimate() {
		return Estimate;
	}

	public void setEstimate(String estimate) {
		Estimate = Str.rightTrim(estimate);
	}

	public String getPolicyNo() {
		return PolicyNo;
	}

	public void setPolicyNo(String policyNo) {
		PolicyNo = Str.rightTrim(policyNo);
	}

	public String getRegisreationNo() {
		return RegisreationNo;
	}

	public void setRegisreationNo(String regisreationNo) {
		RegisreationNo = Str.rightTrim(regisreationNo);
	}

	public String getStartDate() {
		return StartDate;
	}

	public void setStartDate(String startDate) {
		StartDate = Str.rightTrim(startDate);
	}
}
