package com.blacksky.command.aws.listobjects;
             
public class S3Object {

	private String LastModified;
	private String ETag;
	private String StorageClass;
	private String Key;
	private int Size;
	
	public S3Object() {}

	public String getLastModified() {
		return LastModified;
	}

	public void setLastModified(String lastModified) {
		LastModified = lastModified;
	}

	public String getETag() {
		return ETag;
	}

	public void setETag(String eTag) {
		ETag = eTag;
	}

	public String getStorageClass() {
		return StorageClass;
	}

	public void setStorageClass(String storageClass) {
		StorageClass = storageClass;
	}

	public String getKey() {
		return Key;
	}

	public void setKey(String key) {
		Key = key;
	}

	public int getSize() {
		return Size;
	}

	public void setSize(int size) {
		Size = size;
	}
	
}
