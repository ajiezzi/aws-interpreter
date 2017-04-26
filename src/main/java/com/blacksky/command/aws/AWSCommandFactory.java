package com.blacksky.command.aws;

import java.util.regex.Pattern;

import com.blacksky.command.Command;
import com.blacksky.command.aws.descws.DescribeWorkSpaces;
import com.blacksky.command.aws.listbuckets.ListBucketCommand;
import com.blacksky.command.aws.listobjects.ListObjectCommand;
import com.blacksky.command.aws.s3ls.S3LSBucketsCommand;
import com.blacksky.command.aws.s3ls.S3LSObjectsCommand;

public class AWSCommandFactory {

	private static final String DOUBLE_QUOTES = "\"";
	private static final String EMPTY_COLUMN_VALUE = "";
	
	// Ladies & Gentleman, welcome to the wonderful world of REGEX
	private final static String AWS_CLI_COMMAND = "^(%table.+)?(aws\\s).+";
	private final static String S3API_LIST_BUCKETS = "^(%table.+)?(aws\\s)(s3api\\s)(list-buckets).+";
	private final static String S3API_LIST_OBJECTS = "^(%table.+)?(aws\\s)(s3api\\s)(list-objects).+";
	private final static String S3_LS_BUCKETS = "^(%table.+)?(aws\\s)(s3\\s)(ls)";
	private final static String S3_LS_OBJECTS = "^(%table.+)?(aws\\s)(s3\\s)(ls\\s)([0-9a-zA-Z:\\/-]+).+";
	private final static String DESC_WORKSPACES = "^(%table.+)?(aws\\s)(workspaces\\s)(describe-workspaces)";
	
	public static Command getCommand(final String commandLine) {
		
		String line = commandLine.trim().replace(DOUBLE_QUOTES, EMPTY_COLUMN_VALUE);
		
		if (!Pattern.matches(AWS_CLI_COMMAND, line))
			throw new IllegalArgumentException("Not a valid AWS CLI command.");
		
		if (Pattern.matches(S3API_LIST_BUCKETS, line)) {
			return new ListBucketCommand(line);
		} else if (Pattern.matches(S3API_LIST_OBJECTS, line)) {
			return new ListObjectCommand(line);
		} else if (Pattern.matches(S3_LS_BUCKETS, line)) {
			return new S3LSBucketsCommand(line);
		} else if (Pattern.matches(S3_LS_OBJECTS, line)) {
			return new S3LSObjectsCommand(line);
		} else if (Pattern.matches(DESC_WORKSPACES, line)) {
			return new DescribeWorkSpaces(line);
		} else {
			return new GenericCommand(line);
		}
		
	}
	
}
