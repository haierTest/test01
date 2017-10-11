package com.inducts.prpall.dto;

import java.io.Serializable;

public class BankInterfaceSumDTO  implements Serializable{
	
	 String bankCode = "";

	 

	 String branchBankCode = "";

	 

	  String  polcount = "";
	 

	 String  sumprem = "";
	 

	  String  annulCount = "";
	 

	  String  total = "";
	 

	  String  riskCode = "";          

	 

	  String  procDate = "";
	 

	  String  operFlag = "";


	  String  createFileSeq = "";



     String investcount="";  

	String dataflag="";


	String operatedate = "";
	
	/**
	 * @return the operatedate
	 */
	public String getOperatedate() {
		return operatedate;
	}
	/**
	 * @param operatedate the operatedate to set
	 */
	public void setOperatedate(String operatedate) {
		this.operatedate = operatedate;
	}
	public String getDataflag(){
		return this.dataflag;
	}
	public void setDataflag(String dataflag){
		this.dataflag=dataflag;
	}
	
	public String getInvestcount(){
		return this.investcount;
	}
	public void setInvestcount(String investcount){
		this.investcount=investcount;
	}
	
	public String getAnnulCount() {
		return annulCount;
	}

	public void setAnnulCount(String annulCount) {
		this.annulCount = annulCount;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBranchBankCode() {
		return branchBankCode;
	}

	public void setBranchBankCode(String branchBankCode) {
		this.branchBankCode = branchBankCode;
	}

	public String getCreateFileSeq() {
		return createFileSeq;
	}

	public void setCreateFileSeq(String createFileSeq) {
		this.createFileSeq = createFileSeq;
	}

	public String getOperFlag() {
		return operFlag;
	}

	public void setOperFlag(String operFlag) {
		this.operFlag = operFlag;
	}

	public String getPolcount() {
		return polcount;
	}

	public void setPolcount(String polcount) {
		this.polcount = polcount;
	}

	public String getProcDate() {
		return procDate;
	}

	public void setProcDate(String procDate) {
		this.procDate = procDate;
	}

	public String getRiskCode() {
		return riskCode;
	}

	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}

	public String getSumprem() {
		return sumprem;
	}

	public void setSumprem(String sumprem) {
		this.sumprem = sumprem;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}
	  

	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append(this.getClass()+"=\n");
		sb.append("bankCode\t=\t"+this.bankCode+"\n");
		sb.append("branchBankCode\t=\t"+this.branchBankCode+"\n");
		sb.append("polcount\t=\t"+this.polcount+"\n");
		sb.append("sumprem\t=\t"+this.sumprem+"\n");
		sb.append("annulCount\t=\t"+this.annulCount+"\n");
		sb.append("total\t=\t"+this.total+"\n");
		sb.append("riskCode\t=\t"+this.riskCode+"\n");
		sb.append("procDate\t=\t"+this.procDate+"\n");
		sb.append("operFlag\t=\t"+this.operFlag+"\n");
		sb.append("createFileSeq\t=\t"+this.createFileSeq+"\n");
		sb.append("investcount\t=\t"+this.investcount+"\n");
		sb.append("dataflag\t=\t"+this.dataflag+"\n");
		sb.append("operatedate\t=\t"+this.operatedate+"\n");		
		return sb.toString();
	}

}
