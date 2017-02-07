package com.blacksky.command.aws.descws;

import java.util.List;

public class WorkSpaceList {

private List<WorkSpace> Workspaces;
	
	public WorkSpaceList() {}

	public List<WorkSpace> getWorkSpaces() {
		return Workspaces;
	}

	public void setWorkSpaces(List<WorkSpace> workspaces) {
		Workspaces = workspaces;
	}
	
}
