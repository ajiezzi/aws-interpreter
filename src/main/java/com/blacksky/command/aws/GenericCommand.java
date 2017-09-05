package com.blacksky.command.aws;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blacksky.command.CommandException;
import com.blacksky.command.CommandExecuter;
import com.blacksky.command.CommandResult;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


public class GenericCommand extends AWSCommand {

	private static final Logger logger = 
			LoggerFactory.getLogger(GenericCommand.class);
	
	public GenericCommand(final String command, final CommandExecuter executer) {
		super(command, executer);
	}
	
	public CommandResult executeCommand(final long timeout) throws CommandException {
		
		logger.info("Executing Generic AWS command.");
		StringBuilder sb = new StringBuilder();
		
		try {
			
			String output = 
					executer.executeCommand(this, timeout);
			
			if (isTableType()) {
				
				Iterator<JsonNode> elements;
				final ObjectMapper mapper = 
						new ObjectMapper(
								new JsonFactory()
								);
				
				final JsonNode rootNode = mapper.readTree(output);
				
				if (rootNode.isArray() && 
						(elements = rootNode.elements()).hasNext()) {
					
					final List<String> headerList = new ArrayList<String>();
					//final Iterator<JsonNode> elements = rootNode.elements();
					
					//if (elements.hasNext()) {
						
					JsonNode node = elements.next();
						
					// First put out the headers into an ordered list
					node.fieldNames().forEachRemaining(header -> headerList.add(header));
						
					// Build the header row for Zeppelin visualization
					for (int i = 0; i < (headerList.size() - 1); i++) {
						sb.append(headerList.get(i) + TAB);
					}
					sb.append(headerList.get(headerList.size() - 1) + NEWLINE);
						
					// Create the first row since we already have an item pulled out
					for (int i = 0; i < (headerList.size() - 1); i++) {
						sb.append(node.get(headerList.get(i)).asText() + TAB);
					}
					sb.append(
							node
							.get(headerList.get(headerList.size() - 1))
							.asText() + NEWLINE
							);
											
					//}

					elements.forEachRemaining(
							new Consumer<JsonNode>() {
								public void accept(JsonNode node) {
									for (int i = 0; i < (headerList.size() - 1); i++) {
										sb.append(
												node
												.get(headerList.get(i))
												.asText() + TAB
												);
									}
									sb.append(
											node
											.get(headerList.get(headerList.size() - 1))
											.asText() + NEWLINE
											);
								}
							}
					);
					
				} 
				
				return new AWSCommandResult(sb);
				
			} 
			
			return new AWSCommandResult(sb.append(output));
			 
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommandException(e.getMessage(), e);
		} 
		
	}

}
