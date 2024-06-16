package com.company.ai;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.localai.LocalAiChatModel;
import dev.langchain4j.model.output.Response;

@RestController
@RequestMapping("/api/files")
public class LocalAIChatController  {
	
	Logger logger = LoggerFactory.getLogger(LocalAIChatController.class);
	
	@Autowired
	LocalAILLMService llmService;

    @PostMapping("/translate")
    public ResponseEntity<String> handleFileUpload(@RequestParam(defaultValue = "Convert Java source code to C#.") String prompt,
    		                                       @RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Please upload a valid file.");
        }

        try {
            String fileContent = new String(file.getBytes());
            logger.info("Prompt: "+prompt);
            logger.info("Input file name: "+file.getName());
            logger.info("Input file size: "+file.getSize());
            logger.info("Input file text: "+new String(file.getBytes()));
            
            String response = migrate(prompt, fileContent);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
        	e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to read the file content.");
        }
    }

    private String migrate(String prompt, String fileContent) {
    	String response = llmService.chat(prompt, fileContent);
        return response;
    }
}