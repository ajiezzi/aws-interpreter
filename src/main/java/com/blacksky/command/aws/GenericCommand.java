package com.blacksky.command.aws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blacksky.command.CommandException;
import com.blacksky.command.CommandExecuter;
import com.blacksky.command.CommandResult;


public class GenericCommand extends AWSCommand {

	private static final Logger logger = 
			LoggerFactory.getLogger(GenericCommand.class);
	
	public GenericCommand(final String command, final CommandExecuter executer) {
		super(command, executer);
	}
	
	public CommandResult executeCommand(final long timeout) throws CommandException {
		
		logger.info("Executing Generic AWS command.");
		StringBuilder result = new StringBuilder();
		
		try {
			
			result.append(
					executer.executeCommand(
							this, 
							timeout
							)
					);
			 
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommandException(e.getMessage(), e);
		} 
	
		return new AWSCommandResult(result);
		
	}

}
