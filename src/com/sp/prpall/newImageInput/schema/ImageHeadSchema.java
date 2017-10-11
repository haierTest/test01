package com.sp.prpall.newImageInput.schema;

import java.io.Serializable;

public class ImageHeadSchema implements Serializable {

	private static final long serialVersionUID = 1L;
	private String requestType; 

	public ImageHeadSchema() {
		super();
	}

	public ImageHeadSchema(String requestType) {
		super();
		this.requestType = requestType;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

}
