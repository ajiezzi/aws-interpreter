package com.blacksky.command.aws.s3api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blacksky.command.CommandException;
import com.blacksky.command.CommandExecuter;
import com.blacksky.command.CommandResult;
import com.blacksky.command.aws.AWSCommand;
import com.blacksky.command.aws.AWSCommandResult;
import com.google.gson.Gson;

public class ListObjectCommand extends AWSCommand {

	private static final Logger logger = 
			LoggerFactory.getLogger(ListObjectCommand.class);
	
	private static final String LAST_MODIFIED = "LastModified";
	private static final String ETag = "ETag";
	private static final String STORAGE_CLASS = "StorageClass";
	private static final String KEY = "Key";
	private static final String SIZE = "Size";
	
	private static final String HEADER_ROW = LAST_MODIFIED + TAB + 
			ETag + TAB + STORAGE_CLASS + TAB + KEY + TAB + SIZE;
	
	public ListObjectCommand(final String command, final CommandExecuter executer) {
		super(command, executer);
	}
	
	public CommandResult executeCommand(final long timeout) throws CommandException {
		
		logger.info("Executing aws s3api list-objects command.");
		
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
				
				S3ObjectList objectList = 
						gson.fromJson(output, S3ObjectList.class);
					
				// Build the header row for the table
				sb.append(HEADER_ROW);
					
				// Add the values to the table
				for (S3Object s3object : objectList.getContents()) {
					sb.append(NEWLINE +
							s3object.getLastModified() + TAB +
							s3object.getETag() + TAB +
							s3object.getStorageClass() + TAB +
							s3object.getKey() + TAB +
							s3object.getSize()
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
