package com.blacksky.command.aws;

import com.blacksky.command.CommandResult;

public class AWSCommandResult implements CommandResult {

	private StringBuilder result;
	
	public AWSCommandResult() {
		this.setResult(new StringBuilder());
	}
	
	public AWSCommandResult(
			final StringBuilder stringBuilder) {
		this.setResult(stringBuilder);
	}

	public StringBuilder getResult() {
		return result;
	}

	public void setResult(final StringBuilder result) {
		this.result = result;
	}
	
	public String toString() {
		return this.result.toString();
	}
	
}
