package com.blacksky.command.aws;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.Executor;
import org.apache.commons.exec.PumpStreamHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blacksky.command.Command;
import com.blacksky.command.CommandExecuter;

public class CLIExecuter implements CommandExecuter {

	private static final Logger logger = 
			LoggerFactory.getLogger(CLIExecuter.class);
	
	private int BUFFER_SIZE = 1024 * 4;
	
	private Executor executor;
	
	public CLIExecuter() {
		this.executor = new DefaultExecutor();
	}

	public String executeCommand(
			final Command command,
			final long timeout) 
					throws Exception {
		
		final ByteArrayOutputStream os = new ByteArrayOutputStream(BUFFER_SIZE);
		String result = null;
		
		if (command.isSecure()) {
			
			try {
				
				ExecuteWatchdog watchDog = new ExecuteWatchdog(timeout);
				
				executor.setWatchdog(watchDog);
					
				executor.setStreamHandler(
						new PumpStreamHandler(os, os)
						);
				
				logger.info("Issuing Command with arguments " 
						+ command.getCommandLine().toString());
				
				int exitValue = executor.execute(command.getCommandLine());
				logger.info("Command returned with Executor exit value: " + exitValue);
				
				if (executor.isFailure(exitValue)) {
					if (watchDog.killedProcess()) {
						throw new Exception("Interpreter process destroyed. Please increase timeout property.");
					} else {
						throw new Exception("Invalid AWS CLI command.");
					}
					
				}
				
				result = new String(
							os.toByteArray(), 
							StandardCharsets.UTF_8
							);
					
			} catch (Exception e) {
				logger.error(e.getMessage());
				throw new Exception("Error. Invalid AWS CLI command.", e);
			} finally {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return result;
		
	}
	
	public void cancelCommand() {
		logger.info("Cancelling AWS CLI command.");
		if (this.executor != null) {
			try {
				executor.getWatchdog().destroyProcess();
		    } catch (Exception e){
		    	logger.error("Error destroying executor.");
		    }
		}
	}
	
}
