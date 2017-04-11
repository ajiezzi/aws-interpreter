package com.blacksky.command.aws.listbuckets;

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
