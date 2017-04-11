package com.blacksky.command;

public interface CommandResult {

	public StringBuilder getResult();
	public void setResult(final StringBuilder result);
	
	public String toString();
	
}
