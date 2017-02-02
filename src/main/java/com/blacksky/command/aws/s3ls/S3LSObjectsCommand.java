package com.blacksky.command.aws.s3ls;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blacksky.command.CommandException;
import com.blacksky.command.CommandResult;
import com.blacksky.command.aws.AWSCommand;
import com.blacksky.command.aws.AWSCommandResult;

public class S3LSObjectsCommand extends AWSCommand {

	private static final Logger logger = 
			LoggerFactory.getLogger(S3LSObjectsCommand.class);
	
	private final static String AWS_LS_OBJECTS_OUTPUT = 
			"^(\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2})\\s+(\\d+)\\s+([0-9a-zA-Z_.]+)";
	
	private static final String CREATION_TIME = "CreationTime";
	private static final String KEY = "Key";
	private static final String SIZE = "Size";
	private static final String HEADER_ROW = CREATION_TIME + TAB + SIZE + TAB + KEY;
	
	private final static int CREATION_TIME_GROUP = 1;
	private final static int SIZE_GROUP = 2;
	private final static int KEY_GROUP = 3; 
	
	public S3LSObjectsCommand() {
		super();
	}
	
	public S3LSObjectsCommand(final String command) {
		super(command);
	}
	
	public CommandResult executeCommand(final long timeout) throws CommandException {
		
		logger.info("Executing AWS S3 LS <bucket> command.");
		StringBuilder sb = new StringBuilder();
		
		try {
			
			String output = 
					cliClient.issueCLICommand(
							this, 
							timeout
							).toString();
			
			if (isTableType()) {
				
				sb.append(HEADER_ROW);
					
				String[] lines = output.split(String.valueOf(NEWLINE));
					
				for (String line : lines) {
					
					// Match specific lines using a REGEX pattern
					Pattern pattern = Pattern.compile(AWS_LS_OBJECTS_OUTPUT);
					Matcher matcher = pattern.matcher(line);
					
					if (matcher.find()) {
						sb.append(NEWLINE +
								matcher.group(CREATION_TIME_GROUP) + TAB +
								matcher.group(SIZE_GROUP) + TAB +
								matcher.group(KEY_GROUP)
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
