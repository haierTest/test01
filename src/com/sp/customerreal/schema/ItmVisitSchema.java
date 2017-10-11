package com.sp.customerreal.schema;

/**
 * @ClassName: ItmVisitSchema
 * @Description: 电销回访Schema
 * @date May 25, 2011 10:25:06 AM
  AGENTID            VARCHAR2(30 BYTE),  --坐席工号(电销TSR)
  AGENTNAME          VARCHAR2(60 BYTE),  --坐席姓名(电销TSR)
  DXCUSTOMERID       VARCHAR2(30 BYTE),  --XXXXXID(电销XX独有)
  PROVINCE           VARCHAR2(20 BYTE),  --出单省份(电销XX独有)
  thirdcomcode       VARCHAR2(50 BYTE),  --三级机构(电销XX独有)
  AREACODE           VARCHAR2(50 BYTE),  --区域中心(电销XX独有)
  CENTERCODE         VARCHAR2(50 BYTE),  --分中心(电销XX独有)
  TEAMNAME           VARCHAR2(100 BYTE),  --团队名称(电销XX独有)
  YWMODE             VARCHAR2(60 BYTE),  --业务模式(电销XX独有)
  POLICYNO           VARCHAR2(22 BYTE)  NOT NULL,  --XX号
  comcode            VARCHAR2(8 BYTE),   --XX所属机构代码
  comcodename        VARCHAR2(100 BYTE),   --XX所属机构名称
  compersoncode      VARCHAR2(20 BYTE),  --出单员代码
  compersonname      VARCHAR2(30 BYTE),  --出单员
  LICENSENO          VARCHAR2(20 BYTE),  --车牌号
  UNDERWRITEENDDATE  DATE,    --核XXXXX通过日期
  MOBILE             VARCHAR2(15 BYTE),  --XXXXX手机
  PHONE              VARCHAR2(20 BYTE),  --XXXXX电话
  CUSTOMERID         VARCHAR2(20 BYTE)  NOT NULL,  --XXXXX号
  CUSTOMERCNAME      VARCHAR2(120 BYTE),  --XXXXX姓名
  SEX                VARCHAR2(2 BYTE),  --XXXXX性别
  BIRTHDATE          DATE,  --出生日期
  IDENTIFYTYPE       VARCHAR2(8 BYTE),  --证件类型
  IDENTIFYNUMBER     VARCHAR2(20 BYTE),  --证件号码
  ADDRESS            VARCHAR2(255 BYTE),  --XXXXX地址
  BUSINESSNATURE     VARCHAR2(20 BYTE),    --（核心字段，没有找到中文解释）
  jobcode            VARCHAR2(20 BYTE),  --职业代码
  paiddate           DATE,     --实收日期
  CREATEDATE         DATE,  --数据导入时间
  VENTUREFLAG        VARCHAR2(2),   --服务区域类型
  SERVICEAREA        VARCHAR2(20),  --XXXXXXXXXX业务员代码
  HANDLERCODE        VARCHAR2(12),  --综拓专员代码
  SUPPPOSTSTYLE      VARCHAR2(100), --配送类型
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
