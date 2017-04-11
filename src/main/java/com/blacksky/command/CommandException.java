package com.blacksky.command;

public class CommandException extends Exception {

	private static final long serialVersionUID = 8000291994637170345L;

	public CommandException(String message, Exception e) {
		super(message, e);
	}
		
}