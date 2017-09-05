package com.blacksky.command;

import org.apache.commons.exec.CommandLine;

public interface Command {

	public boolean isSecure();
	public boolean isTableType();
	
	public CommandLine getCommandLine();
	public CommandResult executeCommand(final long timeout) 
			throws CommandException;
	
	public void cancel();
	
}
