package com.sp.prpall.newImageInput.schema;

import java.io.Serializable;

public class ImageBodySchema implements Serializable {

	private static final long serialVersionUID = 1L;
	private ImageTaskSchema imageTask;

	public ImageBodySchema() {
		super();
	}

	public ImageBodySchema(ImageTaskSchema imageTask) {
		super();
		this.imageTask = imageTask;
	}

	public ImageTaskSchema getImageTask() {
		return imageTask;
	}

	public void setImageTask(ImageTaskSchema imageTask) {
		this.imageTask = imageTask;
	}

}
