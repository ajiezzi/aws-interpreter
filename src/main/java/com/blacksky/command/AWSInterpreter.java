package com.blacksky.command;

import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.zeppelin.interpreter.Interpreter;
import org.apache.zeppelin.interpreter.InterpreterContext;
import org.apache.zeppelin.interpreter.InterpreterResult;
import org.apache.zeppelin.interpreter.InterpreterResult.Code;
import org.apache.zeppelin.interpreter.InterpreterResult.Type;
import org.apache.zeppelin.scheduler.Scheduler;
import org.apache.zeppelin.scheduler.SchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blacksky.command.aws.AWSCommandFactory;


/***
 * AWS CLI interpreter for Zeppelin. At the moment, this interpreter 
 * will only format the following commands into the table display:
 * 
 * @author adamiezzi
 *
 */
public class AWSInterpreter extends Interpreter {

	private static final Logger logger = 
			LoggerFactory.getLogger(AWSInterpreter.class);
	
	private static final String TIMEOUT = "aws.cli.timeout.millis";
	//private static final String AWS_DEFAULT_REGION = "aws.cli.default.region";
	
	ConcurrentHashMap<String, Command> commandMap;
	
	public AWSInterpreter(Properties property) {
		super(property);
	}

	@Override
	public void open() {
	    commandMap = new ConcurrentHashMap<String, Command>();
	}
	
	@Override
	public void cancel(InterpreterContext context) {
		Command command = commandMap.remove(context.getParagraphId());
		if (command != null) {
			command.close();
		}
	}

	@Override
	public void close() {
		
	}

	@Override
	public FormType getFormType() {
		return FormType.SIMPLE;
	}

	@Override
	public int getProgress(InterpreterContext context) {
		return 0;
	}
	
	@Override
	public Scheduler getScheduler() {
		return SchedulerFactory.singleton().createOrGetParallelScheduler(
	        AWSInterpreter.class.getName() + this.hashCode(), 10);
	}

	@Override
	public InterpreterResult interpret(String interpreterCommand, InterpreterContext context) {
		
		logger.info("Interpreting AWS command '" + interpreterCommand + "'");
		
		try {
			
			Command command = AWSCommandFactory.getCommand(interpreterCommand);
			
			commandMap.put(context.getParagraphId(), command);
			
			CommandResult result = 
					command.executeCommand(
							Long.parseLong(getProperty(TIMEOUT))
							);
			
			return new InterpreterResult(
					Code.SUCCESS, 
					command.isTableType() ? Type.TABLE : Type.TEXT,
					result.getResult().toString()
					);
			
		} catch (CommandException ce) {
			logger.error(ce.getMessage());
			return new InterpreterResult(
					Code.ERROR, 
					ce.getMessage()
					);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new InterpreterResult(
					Code.ERROR, 
					e.getMessage()
					);
		} finally {
			commandMap.remove(context.getParagraphId());
		}	
		
	}
	
}
