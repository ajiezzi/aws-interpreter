package com.blacksky.command;

import org.apache.commons.exec.CommandLine;

public interface Command {

	public void addArgument(final String argument);
	public void addArguments(final String[] arguments);
	
	public boolean isSecure();
	public boolean isTableType();
	
	public CommandLine getCommandLine();
	public CommandResult executeCommand(final long timeout) 
			throws CommandException;
	
	public void cancel();
	
}
