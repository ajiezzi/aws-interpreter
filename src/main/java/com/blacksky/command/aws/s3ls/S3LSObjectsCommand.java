package com.blacksky.command.aws.s3ls;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blacksky.command.CommandException;
import com.blacksky.command.CommandExecuter;
import com.blacksky.command.CommandResult;
import com.blacksky.command.aws.AWSCommand;
import com.blacksky.command.aws.AWSCommandResult;

public class S3LSObjectsCommand extends AWSCommand {

	private static final Logger logger = 
			LoggerFactory.getLogger(S3LSObjectsCommand.class);
	
	private final static String AWS_LS_OBJECTS_OUTPUT = 
			"^(\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2})\\s+(\\d+)\\s+([0-9a-zA-Z_.]+)";
	
	private final static String AWS_LS_TOTAL_OBJECTS =
			"^(Total Objects:)\\s([0-9]+)";
	
	private final static String AWS_LS_TOTAL_SIZE =
			"^\\s+(Total Size:)\\s([0-9]+)";
	
	private static final String CREATION_TIME = "CreationTime";
	private static final String KEY = "Key";
	private static final String SIZE = "Size";
	private static final String TOTAL_OBJECTS = "Total Objects";
	private static final String TOTAL_SIZE = "Total Size";
	
	private static final String HEADER_ROW = CREATION_TIME + TAB + SIZE + TAB + KEY;
	private static final String HEADER_ROW_SUM = TOTAL_OBJECTS + TAB + TOTAL_SIZE;
	
	private final static int CREATION_TIME_GROUP = 1;
	private final static int SIZE_GROUP = 2;
	private final static int KEY_GROUP = 3;
	private final static int SUM_GROUP = 2; 
	
	private final Pattern patternObject = Pattern.compile(AWS_LS_TOTAL_OBJECTS);
	private final Pattern patternSize = Pattern.compile(AWS_LS_TOTAL_SIZE);
	
	public S3LSObjectsCommand(final String command, final CommandExecuter executer) {
		super(command, executer);
	}
	
	public CommandResult executeCommand(final long timeout) throws CommandException {
		
		logger.info("Executing AWS S3 LS <bucket> command.");
		StringBuilder sb = new StringBuilder();
		
		try {
			
			String output = 
					executer.executeCommand(
							this, 
							timeout
							).toString();
			
			if (isTableType()) {
				
				if (isSummary()) {
					
					sb.append(HEADER_ROW_SUM);
					
					String totalObjects = "";
					String totalSize = "";
					String[] lines = output.split(String.valueOf(NEWLINE));
						
					for (String line : lines) {
						
						Matcher matcherObj = patternObject.matcher(line);
						Matcher matcherSize = patternSize.matcher(line);
						
						if (matcherObj.find()) {
							totalObjects = matcherObj.group(SUM_GROUP).toString();
						}
						
						if (matcherSize.find()) {
							totalSize = matcherSize.group(SUM_GROUP).toString();
						}
						
					}
					
					sb.append(NEWLINE +
							totalObjects + TAB +
							totalSize
							);
					
				} else {
					
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
