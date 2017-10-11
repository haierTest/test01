package com.sp.customerreal.schema;

/**
 * @ClassName: ItmVisitSchema
 * @Description: �����ط�Schema
 * @date May 25, 2011 10:25:06 AM
  AGENTID            VARCHAR2(30 BYTE),  --��ϯ����(����TSR)
  AGENTNAME          VARCHAR2(60 BYTE),  --��ϯ����(����TSR)
  DXCUSTOMERID       VARCHAR2(30 BYTE),  --XXXXXID(����XX����)
  PROVINCE           VARCHAR2(20 BYTE),  --����ʡ��(����XX����)
  thirdcomcode       VARCHAR2(50 BYTE),  --��������(����XX����)
  AREACODE           VARCHAR2(50 BYTE),  --��������(����XX����)
  CENTERCODE         VARCHAR2(50 BYTE),  --������(����XX����)
  TEAMNAME           VARCHAR2(100 BYTE),  --�Ŷ�����(����XX����)
  YWMODE             VARCHAR2(60 BYTE),  --ҵ��ģʽ(����XX����)
  POLICYNO           VARCHAR2(22 BYTE)  NOT NULL,  --XX��
  comcode            VARCHAR2(8 BYTE),   --XX������������
  comcodename        VARCHAR2(100 BYTE),   --XX������������
  compersoncode      VARCHAR2(20 BYTE),  --����Ա����
  compersonname      VARCHAR2(30 BYTE),  --����Ա
  LICENSENO          VARCHAR2(20 BYTE),  --���ƺ�
  UNDERWRITEENDDATE  DATE,    --��XXXXXͨ������
  MOBILE             VARCHAR2(15 BYTE),  --XXXXX�ֻ�
  PHONE              VARCHAR2(20 BYTE),  --XXXXX�绰
  CUSTOMERID         VARCHAR2(20 BYTE)  NOT NULL,  --XXXXX��
  CUSTOMERCNAME      VARCHAR2(120 BYTE),  --XXXXX����
  SEX                VARCHAR2(2 BYTE),  --XXXXX�Ա�
  BIRTHDATE          DATE,  --��������
  IDENTIFYTYPE       VARCHAR2(8 BYTE),  --֤������
  IDENTIFYNUMBER     VARCHAR2(20 BYTE),  --֤������
  ADDRESS            VARCHAR2(255 BYTE),  --XXXXX��ַ
  BUSINESSNATURE     VARCHAR2(20 BYTE),    --�������ֶΣ�û���ҵ����Ľ��ͣ�
  jobcode            VARCHAR2(20 BYTE),  --ְҵ����
  paiddate           DATE,     --ʵ������
  CREATEDATE         DATE,  --���ݵ���ʱ��
  VENTUREFLAG        VARCHAR2(2),   --������������
  SERVICEAREA        VARCHAR2(20),  --XXXXXXXXXXҵ��Ա����
  HANDLERCODE        VARCHAR2(12),  --����רԱ����
  SUPPPOSTSTYLE      VARCHAR2(100), --��������
 */
public class ItmVisitSchema {
	
	private String agentId = "";

	private String agentName = "";

	private String dxCustomerId = "";

	private String province = "";

	private String thirdComcode = "";

	private String areaCode = "";

	private String centerCode = "";

	private String teamName = "";

	private String ywMode = "";

	private String policyNo = "";

	private String comCode = "";

	private String comCodeName = "";

	private String comPersonCode = "";

	private String comPersonName = "";

	private String licenseNo = "";

	private String underWriteEndDate = "";

	private String mobile = "";

	private String phone = "";

	private String customerId = "";

	private String customerCname = "";

	private String sex = "";

	private String birthDate = "";

	private String identifyType = "";

	private String identifyNumber = "";

	private String address = "";

	private String businessNature = "";

	private String jobCode = "";

	private String paidDate = "";
	
	private String ventureFlag = "";
	
	private String serviceArea  = "";
	
	private String handlerCode = "";
	
	private String suppPostStyle = "";

	public String getVentureFlag() {
		return ventureFlag;
	}

	public void setVentureFlag(String ventureFlag) {
		this.ventureFlag = ventureFlag;
	}

	public String getServiceArea() {
		return serviceArea;
	}

	public void setServiceArea(String serviceArea) {
		this.serviceArea = serviceArea;
	}

	public String getHandlerCode() {
		return handlerCode;
	}

	public void setHandlerCode(String handlerCode) {
		this.handlerCode = handlerCode;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getDxCustomerId() {
		return dxCustomerId;
	}

	public void setDxCustomerId(String dxCustomerId) {
		this.dxCustomerId = dxCustomerId;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getThirdComcode() {
		return thirdComcode;
	}

	public void setThirdComcode(String thirdComcode) {
		this.thirdComcode = thirdComcode;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getCenterCode() {
		return centerCode;
	}

	public void setCenterCode(String centerCode) {
		this.centerCode = centerCode;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getYwMode() {
		return ywMode;
	}

	public void setYwMode(String ywMode) {
		this.ywMode = ywMode;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public String getComCode() {
		return comCode;
	}

	public void setComCode(String comCode) {
		this.comCode = comCode;
	}

	public String getComCodeName() {
		return comCodeName;
	}

	public void setComCodeName(String comCodeName) {
		this.comCodeName = comCodeName;
	}

	public String getComPersonCode() {
		return comPersonCode;
	}

	public void setComPersonCode(String comPersonCode) {
		this.comPersonCode = comPersonCode;
	}

	public String getComPersonName() {
		return comPersonName;
	}

	public void setComPersonName(String comPersonName) {
		this.comPersonName = comPersonName;
	}

	public String getLicenseNo() {
		return licenseNo;
	}

	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}

	public String getUnderWriteEndDate() {
		return underWriteEndDate;
	}

	public void setUnderWriteEndDate(String underWriteEndDate) {
		this.underWriteEndDate = underWriteEndDate;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCustomerCname() {
		return customerCname;
	}

	public void setCustomerCname(String customerCname) {
		this.customerCname = customerCname;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getIdentifyType() {
		return identifyType;
	}

	public void setIdentifyType(String identifyType) {
		this.identifyType = identifyType;
	}

	public String getIdentifyNumber() {
		return identifyNumber;
	}

	public void setIdentifyNumber(String identifyNumber) {
		this.identifyNumber = identifyNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBusinessNature() {
		return businessNature;
	}

	public void setBusinessNature(String businessNature) {
		this.businessNature = businessNature;
	}

	public String getJobCode() {
		return jobCode;
	}

	public void setJobCode(String jobCode) {
		this.jobCode = jobCode;
	}

	public String getPaidDate() {
		return paidDate;
	}

	public void setPaidDate(String paidDate) {
		this.paidDate = paidDate;
	}

	public String getSuppPostStyle() {
		return suppPostStyle;
	}

	public void setSuppPostStyle(String suppPostStyle) {
		this.suppPostStyle = suppPostStyle;
	}
    
}
