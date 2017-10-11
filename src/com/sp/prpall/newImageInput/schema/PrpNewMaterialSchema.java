package com.sp.prpall.newImageInput.schema;

import com.sp.sysframework.dto.PrpMaterialDto;

public class PrpNewMaterialSchema extends PrpMaterialDto {

	private static final long serialVersionUID = 6072034187830665672L;
	private String followUpFlag;
	private String barCode;
	private String fileStatus;

	public PrpNewMaterialSchema() {
		super();
	}

	public PrpNewMaterialSchema(String followUpFlag, String barCode,
			String fileStatus) {
		super();
		this.followUpFlag = followUpFlag;
		this.barCode = barCode;
		this.fileStatus = fileStatus;
	}

	public String getFileStatus() {
		return fileStatus;
	}

	public void setFileStatus(String fileStatus) {
		this.fileStatus = fileStatus;
	}

	public String getFollowUpFlag() {
		return followUpFlag;
	}

	public void setFollowUpFlag(String followUpFlag) {
		this.followUpFlag = followUpFlag;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}
	 /**
     * @param iSchema ∂‘œÛPrpNewMaterialSchema
     */
    public void setSchema(PrpNewMaterialSchema iSchema)
    {
    	super.setSchema(iSchema);
    	this.barCode=iSchema.getBarCode();
    	this.followUpFlag=iSchema.getFollowUpFlag();
    	this.fileStatus=iSchema.getFileStatus();
    }
}
