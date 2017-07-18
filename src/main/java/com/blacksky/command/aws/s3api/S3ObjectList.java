package com.blacksky.command.aws.s3api;

import java.util.List;

public class S3ObjectList {

	private List<S3Object> Contents;
	
	public S3ObjectList() {}

	public List<S3Object> getContents() {
		return Contents;
	}

	public void setContents(List<S3Object> contents) {
		Contents = contents;
	}
	
}
