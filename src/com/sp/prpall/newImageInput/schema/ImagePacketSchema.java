package com.sp.prpall.newImageInput.schema;

import java.io.Serializable;

public class ImagePacketSchema implements Serializable {

	private static final long serialVersionUID = 1L;
	private ImageHeadSchema head;
	private ImageBodySchema body;

	public ImagePacketSchema() {
		super();
	}

	public ImagePacketSchema(ImageHeadSchema head, ImageBodySchema body) {
		super();
		this.head = head;
		this.body = body;
	}

	public ImageHeadSchema getHead() {
		return head;
	}

	public void setHead(ImageHeadSchema head) {
		this.head = head;
	}

	public ImageBodySchema getBody() {
		return body;
	}

	public void setBody(ImageBodySchema body) {
		this.body = body;
	}

}
