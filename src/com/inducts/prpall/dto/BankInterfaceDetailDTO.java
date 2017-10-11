package com.inducts.prpall.dto;

import java.io.Serializable;

public class BankInterfaceDetailDTO implements Serializable{


	 String appliName ="";
	 

	String  appIdno ="";
	 

	String  estateAddress="";
	 

	String  investCount ="";
	  

	String  sumPremium ="";
	 

	String  bfBankCode ="";
	 

	String  bfAccountNo ="";
	 

	String  bankBranchCode ="";
	 

	String  investDate ="";
	 

	String  proposalNo ="";
	 

	String  idType ="";
	 

	String  mobileCode ="";
	 

	String  email ="";
	 

	String  postCode ="";
	 

	String  address ="";
	 

	String  estatePostCode ="";
	 

	String  useFor ="";
	

	String  useForOther ="";
	 

	String  relation ="";
	 

	String  insuredName ="";
	 

	String  insuredAdress ="";
	 

	String  insuredPhone ="";
	 

	String  insurePostCode ="";
	 

	String  insureTerm ="";


	String  operateDate ="";
	 

	String  poundageRate ="";
	 

	String  makeCom ="";
	  

	String  comCode ="";
	 

	String  handlerCode ="";
	 

	String  startDate ="";
	 

	String  endDate ="";
	 

	String  handler1code ="";
	 

	String  operFlag ="";
	 

	String  currency ="";
	 

	String  classCode ="";
	 

	String  riskCode ="";
	 

	String  riskName ="";
	 

	String  uploadDate ="";
	 

	String  uploadFileSeq ="";
	 

	String  appseq ="";	
	

	String bankcode = "";
	

	String counterperson = "";
	

	String phone = "";
	

	String policyno = "";
	

	String printno = "";
	

	String sumamount = "";
	

	String invalid = "";
	

	String dataoutdate = "";
	

	/**
	 * @return the dataoutdate
	 */
	public String getDataoutdate() {
		return dataoutdate;
	}


	/**
	 * @param dataoutdate the dataoutdate to set
	 */
	public void setDataoutdate(String dataoutdate) {
		this.dataoutdate = dataoutdate;
	}


	public String toString(){
		StringBuffer sb=new StringBuffer();
		sb.append(this.getClass()+"=\n");
		sb.append("appliName\t=\t"+this.appliName+"\n");
		sb.append("appIdno\t=\t"+this.appIdno+"\n");
		sb.append("estateAddress\t=\t"+this.estateAddress+"\n");
		sb.append("investCount\t=\t"+this.investCount+"\n");
		sb.append("sumPremium\t=\t"+this.sumPremium+"\n");
		sb.append("bfBankCode\t=\t"+this.bfBankCode+"\n");
		sb.append("bankBranchCode\t=\t"+this.bankBranchCode+"\n");
		sb.append("investDate\t=\t"+this.investDate+"\n");
		sb.append("proposalNo\t=\t"+this.proposalNo+"\n");
		sb.append("idType\t=\t"+this.idType+"\n");
		
		sb.append("mobileCode\t=\t"+this.mobileCode+"\n");
		sb.append("email\t=\t"+this.email+"\n");
		sb.append("postCode\t=\t"+this.postCode+"\n");
		sb.append("address\t=\t"+this.address+"\n");
		sb.append("estatePostCode\t=\t"+this.estatePostCode+"\n");
		sb.append("userFor\t=\t"+this.useFor+"\n");
		sb.append("userForOther\t=\t"+this.useForOther+"\n");
		sb.append("relation\t=\t"+this.relation+"\n");
		sb.append("insuredName\t=\t"+this.insuredName+"\n");
		sb.append("insuredAdress\t=\t"+this.insuredAdress+"\n");
		sb.append("insuredPhone\t=\t"+this.insuredPhone+"\n");
		
		sb.append("insurePostCode\t=\t"+this.insurePostCode+"\n");
		sb.append("insureTerm\t=\t"+this.insureTerm+"\n");
		sb.append("poundageRate\t=\t"+this.poundageRate+"\n");
		sb.append("makeCom\t=\t"+this.makeCom+"\n");
		sb.append("comCode\t=\t"+this.comCode+"\n");
		sb.append("handlerCode\t=\t"+this.handlerCode+"\n");
		sb.append("startDate\t=\t"+this.startDate+"\n");
		sb.append("endDate\t=\t"+this.endDate+"\n");
		sb.append("handler1code\t=\t"+this.handler1code+"\n");
		sb.append("operFlag\t=\t"+this.operFlag+"\n");
		
		
		sb.append("currency\t=\t"+this.currency+"\n");
		sb.append("classCode\t=\t"+this.classCode+"\n");
		sb.append("riskCode\t=\t"+this.riskCode+"\n");
		sb.append("riskName\t=\t"+this.riskName+"\n");
		sb.append("uploadDate\t=\t"+this.uploadDate+"\n");
		sb.append("uploadFileSeq\t=\t"+this.uploadFileSeq+"\n");
		sb.append("appseq\t=\t"+this.appseq+"\n");	

		sb.append("bankcode\t=\t"+this.bankcode+"\n");	
		sb.append("counterperson\t=\t"+this.counterperson+"\n");	
		sb.append("phone\t=\t"+this.phone+"\n");	
		sb.append("policyno\t=\t"+this.policyno+"\n");	
		sb.append("printno\t=\t"+this.printno+"\n");	
		sb.append("sumamount\t=\t"+this.sumamount+"\n");	
		sb.append("invalid\t=\t"+this.invalid+"\n");	
		sb.append("dataoutdate\t=\t"+this.dataoutdate+"\n");	
		return sb.toString();
	}

	
	/*public String getMakecom(){
		return this.makecom;
		
	}

	public String getHandlercode(){
		return this.handlercode;
	}
	
	public void setMakecom(String makecom){
	    this.makecom=makecom;
	}
	public void setHandlercode(String handlercode){
	    this.handlercode=handlercode;
	}
	
	*/
	
	public String getUseForOther() {
		return useForOther;
	}


	public void setUseForOther(String useForOther) {
		this.useForOther = useForOther;
	
	}


	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAppIdno() {
		return appIdno;
	}

	public void setAppIdno(String appIdno) {
		this.appIdno = appIdno;
	}

	public String getAppliName() {
		return appliName;
	}

	public void setAppliName(String appliName) {
		this.appliName = appliName;
	}

	public String getAppseq() {
		return appseq;
	}

	public void setAppseq(String appseq) {
		this.appseq = appseq;
	}

	public String getBankBranchCode() {
		return bankBranchCode;
	}

	public void setBankBranchCode(String bankBranchCode) {
		this.bankBranchCode = bankBranchCode;
	}

	public String getBfAccountNo() {
		return bfAccountNo;
	}

	public void setBfAccountNo(String bfAccountNo) {
		this.bfAccountNo = bfAccountNo;
	}

	public String getBfBankCode() {
		return bfBankCode;
	}

	public void setBfBankCode(String bfBankCode) {
		this.bfBankCode = bfBankCode;
	}

	public String getClassCode() {
		return classCode;
	}

	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	public String getComCode() {
		return comCode;
	}

	public void setComCode(String comCode) {
		this.comCode = comCode;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getEstateAddress() {
		return estateAddress;
	}

	public void setEstateAddress(String estateAddress) {
		this.estateAddress = estateAddress;
	}

	public String getEstatePostCode() {
		return estatePostCode;
	}

	public void setEstatePostCode(String estatePostCode) {
		this.estatePostCode = estatePostCode;
	}

	public String getHandler1code() {
		return handler1code;
	}

	public void setHandler1code(String handler1code) {
		this.handler1code = handler1code;
	}

	public String getHandlerCode() {
		return handlerCode;
	}

	public void setHandlerCode(String handlerCode) {
		this.handlerCode = handlerCode;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getInsuredAdress() {
		return insuredAdress;
	}

	public void setInsuredAdress(String insuredAdress) {
		this.insuredAdress = insuredAdress;
	}

	public String getInsuredName() {
		return insuredName;
	}

	public void setInsuredName(String insuredName) {
		this.insuredName = insuredName;
	}

	public String getInsuredPhone() {
		return insuredPhone;
	}

	public void setInsuredPhone(String insuredPhone) {
		this.insuredPhone = insuredPhone;
	}

	public String getInsurePostCode() {
		return insurePostCode;
	}

	public void setInsurePostCode(String insurePostCode) {
		this.insurePostCode = insurePostCode;
	}

	public String getInsureTerm() {
		return insureTerm;
	}

	public void setInsureTerm(String insureTerm) {
		this.insureTerm = insureTerm;
	}

	public String getInvestCount() {
		return investCount;
	}

	public void setInvestCount(String investCount) {
		this.investCount = investCount;
	}

	public String getInvestDate() {
		return investDate;
	}

	public void setInvestDate(String investDate) {
		this.investDate = investDate;
	}

	public String getMakeCom() {
		return makeCom;
	}

	public void setMakeCom(String makeCom) {
		this.makeCom = makeCom;
	}

	public String getMobileCode() {
		return mobileCode;
	}

	public void setMobileCode(String mobileCode) {
		this.mobileCode = mobileCode;
	}

	public String getOperateDate() {
		return operateDate;
	}

	public void setOperateDate(String operateDate) {
		this.operateDate = operateDate;
	}

	public String getOperFlag() {
		return operFlag;
	}

	public void setOperFlag(String operFlag) {
		this.operFlag = operFlag;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getPoundageRate() {
		return poundageRate;
	}

	public void setPoundageRate(String poundageRate) {
		this.poundageRate = poundageRate;
	}

	public String getProposalNo() {
		return proposalNo;
	}

	public void setProposalNo(String proposalNo) {
		this.proposalNo = proposalNo;
	}

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	public String getRiskCode() {
		return riskCode;
	}

	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}

	public String getRiskName() {
		return riskName;
	}

	public void setRiskName(String riskName) {
		this.riskName = riskName;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getSumPremium() {
		return sumPremium;
	}

	public void setSumPremium(String sumPremium) {
		this.sumPremium = sumPremium;
	}

	public String getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(String uploadDate) {
		this.uploadDate = uploadDate;
	}

	public String getUploadFileSeq() {
		return uploadFileSeq;
	}

	public void setUploadFileSeq(String uploadFileSeq) {
		this.uploadFileSeq = uploadFileSeq;
	}

	public String getUseFor() {
		return useFor;
	}

	public void setUseFor(String useFor) {
		this.useFor = useFor;
	}


	/**
	 * @return the bankcode
	 */
	public String getBankcode() {
		return bankcode;
	}


	/**
	 * @param bankcode the bankcode to set
	 */
	public void setBankcode(String bankcode) {
		this.bankcode = bankcode;
	}


	/**
	 * @return the counterperson
	 */
	public String getCounterperson() {
		return counterperson;
	}


	/**
	 * @param counterperson the counterperson to set
	 */
	public void setCounterperson(String counterperson) {
		this.counterperson = counterperson;
	}


	/**
	 * @return the invalid
	 */
	public String getInvalid() {
		return invalid;
	}


	/**
	 * @param invalid the invalid to set
	 */
	public void setInvalid(String invalid) {
		this.invalid = invalid;
	}


	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}


	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}


	/**
	 * @return the policyno
	 */
	public String getPolicyno() {
		return policyno;
	}


	/**
	 * @param policyno the policyno to set
	 */
	public void setPolicyno(String policyno) {
		this.policyno = policyno;
	}


	/**
	 * @return the printno
	 */
	public String getPrintno() {
		return printno;
	}


	/**
	 * @param printno the printno to set
	 */
	public void setPrintno(String printno) {
		this.printno = printno;
	}


	/**
	 * @return the sumamount
	 */
	public String getSumamount() {
		return sumamount;
	}


	/**
	 * @param sumamount the sumamount to set
	 */
	public void setSumamount(String sumamount) {
		this.sumamount = sumamount;
	}
	

	
	
}
