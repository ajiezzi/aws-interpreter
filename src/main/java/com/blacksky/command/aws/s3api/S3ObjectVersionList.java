package com.blacksky.command.aws.s3api;

import java.util.List;

public class S3ObjectVersionList {

	private List<S3Object> Versions;
	
	public S3ObjectVersionList() {}

	public List<S3Object> getVersions() {
		return Versions;
	}

	public void setVersions(List<S3Object> versions) {
		Versions = versions;
	}
	
}
