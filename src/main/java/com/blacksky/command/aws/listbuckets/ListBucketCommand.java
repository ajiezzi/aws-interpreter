package com.blacksky.command.aws.listbuckets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blacksky.command.CommandException;
import com.blacksky.command.CommandExecuter;
import com.blacksky.command.CommandResult;
import com.blacksky.command.aws.AWSCommand;
import com.blacksky.command.aws.AWSCommandResult;
import com.google.gson.Gson;

public class ListBucketCommand extends AWSCommand {

	private static final Logger logger = 
			LoggerFactory.getLogger(ListBucketCommand.class);
	
	private static final String CREATION_DATE = "CreationDate";
	private static final String NAME = "Name";
	private static final String HEADER_ROW = CREATION_DATE + TAB + NAME;
	
	public ListBucketCommand(final String command, final CommandExecuter executer) {
		super(command, executer);
	}
	
	public CommandResult executeCommand(final long timeout) throws CommandException {
		
		logger.info("Executing aws s3api list-buckets command.");
		StringBuilder sb = new StringBuilder();
		final Gson gson = new Gson();
		
		try {
			 	  
			if (isTableType()) {
				
				if (!super.getCleanedArgumentsAsString().contains(JSON_OUTPUT))
					super.addArgument(JSON_OUTPUT);
				
				String output = 
						executer.executeCommand(
								this, 
								timeout
								);
					
				BucketList bucketList = 
						gson.fromJson(output, BucketList.class);
					
				// Build the header row for the table
				sb.append(HEADER_ROW);
				
				// Add the values to the table
				for (Bucket bucket : bucketList.getBuckets()) {
					sb.append(NEWLINE +
							bucket.getCreationDate() + TAB +
							bucket.getName()
							);
				}
					
			} else {
				
				sb.append(
						executer.executeCommand(
								this, 
								timeout)
						);
				
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommandException(e.getMessage(), e);
		}
	
		return new AWSCommandResult(sb);
		
	}

}
