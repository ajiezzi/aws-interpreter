package com.blacksky.command.aws.descws;

public class WorkSpace {
	
	private String UserName;
	private String DirectoryId;
	private String State;
	private String WorkspaceId;
	private String SubnetId;
    private String IpAddress;
	private String BundleId;
	
	public WorkSpace() {}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public String getDirectoryId() {
		return DirectoryId;
	}

	public void setDirectoryId(String directoryId) {
		DirectoryId = directoryId;
	}

	public String getState() {
		return State;
	}

	public void setState(String state) {
		State = state;
	}

	public String getWorkspaceId() {
		return WorkspaceId;
	}

	public void setWorkspaceId(String workspaceId) {
		WorkspaceId = workspaceId;
	}

	public String getBundleId() {
		return BundleId;
	}

	public void setBundleId(String bundleId) {
		BundleId = bundleId;
	}

	public String getSubnetId() {
		return SubnetId;
	}

	public void setSubnetId(String subnetId) {
		SubnetId = subnetId;
	}

	public String getIpAddress() {
		return IpAddress;
	}

	public void setIpAddress(String ipAddress) {
		IpAddress = ipAddress;
	}
	
}
