package com.blacksky.command.aws;

import java.util.regex.Pattern;

import org.apache.commons.exec.CommandLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blacksky.command.Command;
import com.blacksky.command.CommandExecuter;

public abstract class AWSCommand implements Command {

	private static final Logger logger = 
			LoggerFactory.getLogger(AWSCommand.class);
	
	private static final String AWS_EXECUTABLE = "aws";
	private static final String AWS = "aws ";
	private static final String EMPTY_COLUMN_VALUE = "";
	protected static final String TABLE_MAGIC_TAG = "%table ";
	protected static final char NEWLINE = '\n';
	protected static final char TAB = '\t';
	protected static final String JSON_OUTPUT = " --output json";
	protected static final String SUM_RECURSIVE = "--summarize --recursive";
	
	// Ban arguments with ";","&&" and "|"
	private final static String OS_COMMAND_WHITELIST = "[0-9A-Za-z%@=:\\/\\s-]+";
	
	protected CommandExecuter executer;
	private StringBuilder cleanArguments;
	
	private boolean secureCommand = true;
	private boolean isTableType = false;
	private boolean isSummary = false;
	
	public AWSCommand(final CommandExecuter executer) {
		this.cleanArguments = new StringBuilder();
		this.executer = executer;
	}
	
	public AWSCommand(final String command, final CommandExecuter executer) {
		this(executer);
		this.cleanArguments = 
				new StringBuilder(
						cleanAndSanitize(command)
						);
	}
	
	public void addArgument(final String argument) {
		this.cleanArguments.append(
				cleanAndSanitize(argument)
				);
	}
	
	public void addArguments(final String[] arguments) {
		for (String s : arguments) {
			this.addArgument(s);
		}
	}
	
	public CommandLine getCommandLine() {
		CommandLine commandLine = new CommandLine(AWS_EXECUTABLE);
		commandLine.addArguments(
				this.cleanArguments.toString()
				);
		return commandLine;
	}
	
	public String getCleanedArgumentsAsString() {
		return this.cleanArguments.toString();
	}
	
	private String cleanAndSanitize(final String dirtyline) {
        
		String line = dirtyline;
	
		if (line == null) 
			throw new IllegalArgumentException("Command can not be null.");
			
		if (line.trim().length() == 0) 
		    throw new IllegalArgumentException("Command can not be empty.");
		
		if (!Pattern.matches(OS_COMMAND_WHITELIST, line)) {
			this.secureCommand = false;
			throw new IllegalArgumentException("Vulnerable command can not be executed.");
		}
		
		logger.info("AWS command [" + line + "] is clean.");
		
		if (line.contains(TABLE_MAGIC_TAG)) {
			this.isTableType = true;
			line = line.replace(TABLE_MAGIC_TAG, EMPTY_COLUMN_VALUE);
		}
		
		if (line.contains(SUM_RECURSIVE)) {
			this.isSummary = true;
		}
			
		line = line.replace(AWS, EMPTY_COLUMN_VALUE);
		
		return line;
		
	}

	public boolean isSecure() {
		return this.secureCommand;
	}
	
	public boolean isTableType() {
		return this.isTableType;
	}
	
	public boolean isSummary() {
		return this.isSummary;
	}
	
	public void cancel() {
		this.executer.cancelCommand();
	}
	
}
