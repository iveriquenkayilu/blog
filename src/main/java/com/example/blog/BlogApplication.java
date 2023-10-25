package com.example.blog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@SpringBootApplication
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class BlogApplication {

	private static final Logger LOGGER= LoggerFactory.getLogger(BlogApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
		LOGGER.info("Blog post appS is running");
	}
}
