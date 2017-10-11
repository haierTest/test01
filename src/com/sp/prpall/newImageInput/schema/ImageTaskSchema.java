package com.sp.prpall.newImageInput.schema;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ImageTaskSchema implements Serializable {

	private static final long serialVersionUID = 1L;
	private String flowId;
	private String appliName;
	private String barCode;
	private String docType;
	private String classCode;
	private String riskCode;
	private String businessType;
	private String comCode;
	private String operatorCode;
	private String operatorName;
	private String flowStatus;
	private String imgStatus;
	
	private String batchFlag;
	
	
	private String explain;
	
	
	private String remarks;
	
	
	private List images = new ArrayList();

	public ImageTaskSchema() {
		super();
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getAppliName() {
		return appliName;
	}

	public void setAppliName(String appliName) {
		this.appliName = appliName;
	}

	public String getRiskCode() {
		return riskCode;
	}

	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}

	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public String getClassCode() {
		return classCode;
	}

	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String bussinessType) {
		this.businessType = bussinessType;
	}

	public String getComCode() {
		return comCode;
	}

	public void setComCode(String comCode) {
		this.comCode = comCode;
	}

	public String getOperatorCode() {
		return operatorCode;
	}

	public void setOperatorCode(String operateCode) {
		this.operatorCode = operateCode;
	}

	public String getFlowStatus() {
		return flowStatus;
	}

	public void setFlowStatus(String flowStatus) {
		this.flowStatus = flowStatus;
	}

	public String getImgStatus() {
		return imgStatus;
	}

	public void setImgStatus(String imgStatus) {
		this.imgStatus = imgStatus;
	}

	public List getImages() {
		return images;
	}

	public void setImages(List images) {
		this.images = images;
	}
	
	public String getBatchFlag() {
		return batchFlag;
	}

	public void setBatchFlag(String batchFlag) {
		this.batchFlag = batchFlag;
	}
	

	public String getExplain() {
		return explain;
	}

	public void setExplain(String explain) {
		this.explain = explain;
	}

	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}
