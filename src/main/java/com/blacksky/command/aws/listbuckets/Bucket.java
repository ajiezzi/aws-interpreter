package com.blacksky.command.aws.listbuckets;

public class Bucket {

	private String CreationDate;
	private String Name;
	
	public Bucket() {}

	public String getCreationDate() {
		return CreationDate;
	}

	public void setCreationDate(String creationDate) {
		this.CreationDate = creationDate;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		this.Name = name;
	}
	
}
