package com.blacksky.command.aws;

import java.util.regex.Pattern;

import com.blacksky.command.Command;
import com.blacksky.command.CommandExecuter;

public class AWSCommandFactory {

	private final static String AWS_CLI_COMMAND = "^(\"%table.+)?(aws\\s).+";
	
	public static Command getCommand(final String commandLine, final CommandExecuter executer) {
		
		if (!Pattern.matches(AWS_CLI_COMMAND, commandLine)) 
			throw new IllegalArgumentException("Not a valid AWS CLI command.");
		
		return new GenericCommand(commandLine, executer);
		
	}
	
}
