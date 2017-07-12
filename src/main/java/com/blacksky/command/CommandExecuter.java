package com.blacksky.command;

public interface CommandExecuter {

	public String executeCommand(final Command command, final long timeout) throws Exception;
	public void cancelCommand();
	
}
