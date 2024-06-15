package com.company.ai;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;

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
		System.out.println("Query started: "+start);
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
		System.out.println("Processing request....."); 
		Response<AiMessage> response = model.generate(chatMessages);
		Instant finish = Instant.now();
		long timeElapsed = Duration.between(start, finish).toMinutes();
		System.out.println("Query end: "+finish);
		System.out.println("Total minutes took: "+ timeElapsed);
		System.out.println(response.content().text());
		System.out.println("Processing completed."); 
	  return response.content().text();	
	}
	
}
