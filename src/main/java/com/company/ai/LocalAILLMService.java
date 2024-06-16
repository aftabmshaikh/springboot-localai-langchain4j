package com.company.ai;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.localai.LocalAiChatModel;
import dev.langchain4j.model.output.Response;

@Service
public class LocalAILLMService {
	
	Logger logger = LoggerFactory.getLogger(LocalAILLMService.class);
		
	@Value("${localai.baseURL}")
	private String baseURL;
	
	@Value("${localai.modelName}")
	private String modelName;
	
	@Value("${localai.temperature}")
	private Double temperature;
	
	@Value("${localai.timout.minutes}")
	private Integer timout;
	
		
	public String chat(String prompt, String context) {
		Instant start = Instant.now();
		logger.info("Query started: "+start);
		ChatLanguageModel model = LocalAiChatModel.builder()
		        .baseUrl(baseURL)
		        .modelName(modelName)
		        .temperature(temperature)
		        .timeout(Duration.ofMinutes(timout))
		        .logRequests(true)
	            .logResponses(true)
		        .build();
		 
		SystemMessage responseInDutch = new SystemMessage(prompt);
		UserMessage question = new UserMessage(context);
		var chatMessages = new ArrayList<ChatMessage>();
		chatMessages.add(responseInDutch);
		chatMessages.add(question);
		logger.info("Processing request....."); 
		Response<AiMessage> response = model.generate(chatMessages);
		Instant finish = Instant.now();
		long timeElapsed = Duration.between(start, finish).toMinutes();
		logger.info("Query end: "+finish);
		logger.info("Total minutes took: "+ timeElapsed);
		logger.info(response.content().text());
		logger.info("Processing completed."); 
	  return response.content().text();	
	}
	
}
