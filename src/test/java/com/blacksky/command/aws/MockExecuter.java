package com.blacksky.command.aws;

import com.blacksky.command.Command;
import com.blacksky.command.CommandExecuter;

public class MockExecuter implements CommandExecuter {

	public MockExecuter(){};
	
	public String executeCommand(Command command, long timeout) throws Exception {
		
		String line = "";
		String[] args = command.getCommandLine().toStrings();
		
		for (String s : args) {
			line = line + s + " ";
		}
		
		if (line.trim() != null && line.trim().equals(AWSInterpreterTest.LIST_BUCKETS_CMD))
			return AWSInterpreterTest.LIST_BUCKET_RESPONSE;
		
		return "Failed Command";
				
	}

	public void cancelCommand() {}

}
