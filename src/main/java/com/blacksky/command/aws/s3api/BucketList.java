package com.blacksky.command.aws.s3api;

import java.util.List;

public class BucketList {

	private List<Bucket> Buckets;
	
	public BucketList() {}

	public List<Bucket> getBuckets() {
		return Buckets;
	}

	public void setBuckets(List<Bucket> buckets) {
		this.Buckets = buckets;
	}
	
}
