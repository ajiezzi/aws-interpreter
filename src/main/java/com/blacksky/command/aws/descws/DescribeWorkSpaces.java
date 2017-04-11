package com.blacksky.command.aws.descws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blacksky.command.CommandException;
import com.blacksky.command.CommandResult;
import com.blacksky.command.aws.AWSCommand;
import com.blacksky.command.aws.AWSCommandResult;
import com.google.gson.Gson;

public class DescribeWorkSpaces extends AWSCommand {
	
	private static final Logger logger = 
			LoggerFactory.getLogger(DescribeWorkSpaces.class);
	
	private static final String USERNAME = "UserName";
	private static final String DIR_ID = "DirectoryId";
	private static final String STATE = "State";
	private static final String WORKSPACE_ID = "WorkspaceId";
	private static final String SUBNET_ID = "SubnetId";
	private static final String IP_ADDRESS = "IpAddress";
	private static final String BUNDLE_ID = "BundleId";
	
	private static final String HEADER_ROW = USERNAME + TAB + 
			DIR_ID + TAB + STATE + TAB + WORKSPACE_ID + TAB + 
			SUBNET_ID + TAB + IP_ADDRESS + TAB + BUNDLE_ID;
	
	public DescribeWorkSpaces(final String command) {
		super(command);
	}
	
	public CommandResult executeCommand(long timeout) throws CommandException {
		
		logger.info("Executing aws workspaces describe-workspaces command.");
		
		StringBuilder sb = new StringBuilder();
		final Gson gson = new Gson();
		
		try {
			
			if (isTableType()) {
				
				if (!super.getCleanedArgumentsAsString().contains(JSON_OUTPUT))
					super.addArgument(JSON_OUTPUT);
					
				String output = 
						cliClient.issueCLICommand(
								this, 
								timeout
								);
				
				WorkSpaceList workspaceList = 
						gson.fromJson(output, WorkSpaceList.class);
					
				// Build the header row for the table
				sb.append(HEADER_ROW);
					
				// Add the values to the table
				for (WorkSpace workspace : workspaceList.getWorkSpaces()) {
					sb.append(NEWLINE +
							workspace.getUserName() + TAB +
							workspace.getDirectoryId() + TAB +
							workspace.getState() + TAB +
							workspace.getWorkspaceId() + TAB +
							workspace.getSubnetId() + TAB +
							workspace.getIpAddress() + TAB +
							workspace.getBundleId()
							);
				}
					
			} else {
				sb.append(
						cliClient.issueCLICommand(
								this, 
								timeout)
						);
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommandException(e.getMessage(), e);
		}
	
		return new AWSCommandResult(sb);
		
	}

}
