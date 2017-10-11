package com.sp.prpall.newImageInput.schema;

import java.io.Serializable;

public class ImageItemSchema implements Serializable {

	private static final long serialVersionUID = 1L;
	private String serialNo;
	private String sysFileName;
	private String fileName;
	private String filePath;
	private String fileType;
	private String fileStatus;
	private String discription;

	public ImageItemSchema() {
		super();
	}

	public ImageItemSchema(String serialNo, String sysFileName, String fileName,
			String filePath, String fileType, String fileStatus,
			String discription) {
		super();
		this.serialNo = serialNo;
		this.sysFileName = sysFileName;
		this.fileName = fileName;
		this.filePath = filePath;
		this.fileType = fileType;
		this.fileStatus = fileStatus;
		this.discription = discription;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getSysFileName() {
		return sysFileName;
	}

	public void setSysFileName(String sysFileName) {
		this.sysFileName = sysFileName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFileStatus() {
		return fileStatus;
	}

	public void setFileStatus(String fileStatus) {
		this.fileStatus = fileStatus;
	}

	public String getDiscription() {
		return discription;
	}

	public void setDiscription(String discription) {
		this.discription = discription;
	}

}
