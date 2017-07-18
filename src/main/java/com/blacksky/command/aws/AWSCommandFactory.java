package com.blacksky.command.aws;

import java.util.regex.Pattern;

import com.blacksky.command.Command;
import com.blacksky.command.CommandExecuter;
import com.blacksky.command.aws.descws.DescribeWorkSpaces;
import com.blacksky.command.aws.s3.S3LSBucketsCommand;
import com.blacksky.command.aws.s3.S3LSObjectsCommand;
import com.blacksky.command.aws.s3api.ListBucketCommand;
import com.blacksky.command.aws.s3api.ListObjectCommand;
import com.blacksky.command.aws.s3api.ListObjectVersionCommand;

public class AWSCommandFactory {

	private static final String DOUBLE_QUOTES = "\"";
	private static final String EMPTY_COLUMN_VALUE = "";
	
	// Ladies & Gentleman, welcome to the wonderful world of REGEX
	private final static String AWS_CLI_COMMAND = "^(%table.+)?(aws\\s).+";
	private final static String S3API_LIST_BUCKETS = 
			"^(%table.+)?(aws\\s)(s3api\\s)(list-buckets).+";
	private final static String S3API_LIST_OBJECTS = 
			"^(%table.+)?(aws\\s)(s3api\\s)(list-objects).+";
	private final static String S3API_LIST_OBJECT_VERSIONS = 
			"^(%table.+)?(aws\\s)(s3api\\s)(list-object-versions).+";
	private final static String S3_LS_BUCKETS = 
			"^(%table.+)?(aws\\s)(s3\\s)(ls)";
	private final static String S3_LS_OBJECTS = 
			"^(%table.+)?(aws\\s)(s3\\s)(ls\\s)([0-9a-zA-Z:\\/-]+).+";
	private final static String DESC_WORKSPACES = 
			"^(%table.+)?(aws\\s)(workspaces\\s)(describe-workspaces)";
	
	public static Command getCommand(final String commandLine, final CommandExecuter executer) {
		
		String line = commandLine.trim().replace(DOUBLE_QUOTES, EMPTY_COLUMN_VALUE);
		
		if (!Pattern.matches(AWS_CLI_COMMAND, line))
			throw new IllegalArgumentException("Not a valid AWS CLI command.");
		
		if (Pattern.matches(S3API_LIST_BUCKETS, line)) {
			return new ListBucketCommand(line, executer);
		} else if (Pattern.matches(S3API_LIST_OBJECTS, line)) {
			return new ListObjectCommand(line, executer);
		} else if (Pattern.matches(S3API_LIST_OBJECT_VERSIONS, line)) {
			return new ListObjectVersionCommand(line, executer);
		} else if (Pattern.matches(S3_LS_BUCKETS, line)) {
			return new S3LSBucketsCommand(line, executer);
		} else if (Pattern.matches(S3_LS_OBJECTS, line)) {
			return new S3LSObjectsCommand(line, executer);
		} else if (Pattern.matches(DESC_WORKSPACES, line)) {
			return new DescribeWorkSpaces(line, executer);
		} else {
			return new GenericCommand(line, executer);
		}
		
	}
	
}
