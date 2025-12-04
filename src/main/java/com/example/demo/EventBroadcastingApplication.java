package com.example.demo;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
    info = @Info(
        title = "Digital Local Event Broadcasting API",
        version = "1.0.0",
        description = "API for managing local events, subscribers, and broadcasting notifications"
    )
)
public class EventBroadcastingApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventBroadcastingApplication.class, args);
    }
}

