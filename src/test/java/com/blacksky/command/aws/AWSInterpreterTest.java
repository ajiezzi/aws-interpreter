package com.blacksky.command.aws;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.exec.ExecuteException;
import org.apache.zeppelin.interpreter.InterpreterContext;
import org.apache.zeppelin.interpreter.InterpreterResult;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.blacksky.command.AWSInterpreter;

public class AWSInterpreterTest {

	private AWSInterpreter interpreter;
	private InterpreterContext context;
	
	public final static String VULNERABLE_CMD = "aws s3 ls; ls -l";
	public final static String INVALID_CMD = "ls -l";
	public final static String LIST_BUCKETS_CMD = "aws s3 ls";
	public final static String LIST_BUCKET_RESPONSE = "2017-07-12 12:15:43 test-bucket-001";
	
	@Before
	public void setUp() 
			throws ExecuteException, IOException {
		
		// Setup interpreter
	    Properties props = new Properties();
	    props.setProperty("aws.cli.timeout.millis", "30000");
	    
	    interpreter = new AWSInterpreter(props);
	    interpreter.open();
	    
	    Map<String, Object> interpreterConfig = new HashMap<String, Object>();
	    
	    // Use a mock executer instead of the AWS CLI
	    interpreterConfig.put("executer", new MockExecuter());
	    
	    context = new InterpreterContext("", "1", null, "", null, interpreterConfig, null, null, null, null, null);
	    
	}
	
	@Test
	public void testVulnerableCommand() {
	    
		InterpreterResult result = 
	    		interpreter.interpret(VULNERABLE_CMD, context);
	    
		assertEquals(InterpreterResult.Code.ERROR, result.code());
		assertEquals("Vulnerable command can not be executed.", result.message());
		
	}
	
	@Test
	public void testInvalidCommand() {
		
		InterpreterResult result = 
	    		interpreter.interpret(INVALID_CMD, context);
		
		assertEquals(InterpreterResult.Code.ERROR, result.code());
		assertEquals("Not a valid AWS CLI command.", result.message());
		
	}
	
	@Test
	public void testListBucketCommand() {
		
		InterpreterResult result = 
	    		interpreter.interpret(LIST_BUCKETS_CMD, context);
		
		assertEquals(InterpreterResult.Code.SUCCESS, result.code());
		assertEquals(LIST_BUCKET_RESPONSE, result.message());
		
	}
	
	@After
	public void tearDown() {
		interpreter.cancel(context);
		interpreter.close();
	}
	
}
