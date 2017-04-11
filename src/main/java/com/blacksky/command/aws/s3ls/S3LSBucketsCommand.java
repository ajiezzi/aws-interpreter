package com.blacksky.command.aws.s3ls;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blacksky.command.CommandException;
import com.blacksky.command.CommandResult;
import com.blacksky.command.aws.AWSCommand;
import com.blacksky.command.aws.AWSCommandResult;

public class S3LSBucketsCommand extends AWSCommand {

	private static final Logger logger = 
			LoggerFactory.getLogger(S3LSBucketsCommand.class);
	
	private final static String AWS_LS_BUCKETS_OUTPUT = 
			"^(\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2})\\s+([0-9a-zA-Z_.-]+)";
	
	private static final String BUCKET_NAME = "BucketName";
	private static final String CREATION_TIME = "CreationTime";
	private static final String HEADER_ROW = CREATION_TIME + TAB + BUCKET_NAME;
	
	private final static int CREATION_TIME_GROUP = 1;
	private final static int BUCKET_NAME_GROUP = 2;  
	
	public S3LSBucketsCommand() {}
	
	public S3LSBucketsCommand(final String command) {
		super(command);
	}
	
	public CommandResult executeCommand(long timeout) throws CommandException {
		
		logger.info("Executing aws s3 ls command.");
		StringBuilder sb = new StringBuilder();
		
		try {
			
			String output = 
					cliClient.issueCLICommand(this, timeout);
			
			if (isTableType()) {
				
				sb.append(HEADER_ROW);
				
				String[] lines = output.split(String.valueOf(NEWLINE));
					
				for (String line : lines) {
					
					// Match specific lines using a REGEX pattern
					Pattern pattern = Pattern.compile(AWS_LS_BUCKETS_OUTPUT);
					Matcher matcher = pattern.matcher(line);
					
					if (matcher.find()) {
						sb.append(
								NEWLINE +
								matcher.group(CREATION_TIME_GROUP) + TAB +
								matcher.group(BUCKET_NAME_GROUP)		
								);
					}
					
				}
					
			} else {
				sb.append(output);
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommandException(e.getMessage(), e);
		}
		
		return new AWSCommandResult(sb);
		
	}

}
