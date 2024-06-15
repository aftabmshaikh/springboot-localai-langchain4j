# Spring Boot, LangChain4J and LocalAI

This demo spring boot application show the capabilities of LocalAI for code translation.

## Setup Local:
 
 Install & Run LocalAI using below docker command:

* docker run -p 8080:8080 --name local-ai -ti localai/localai:latest-aio-cpu

To stop the docker instance write command:

* docker stop local-ai

## Add LangChain4J dependency to maven:

        <dependency>
			<groupId>dev.langchain4j</groupId>
			<artifactId>langchain4j-local-ai</artifactId>
			<version>0.31.0</version>
		</dependency>

## Run Spring Boot Application:

 mvn spring-boot:run -Dserver.port=8081
 
## curl the sample java source file for conversion:

curl --location 'http://localhost:8081/api/files/translate' \
--form 'file=@"C:JavaProgram.java"' \
--form 'prompt="Convert Java source code to C#."'
