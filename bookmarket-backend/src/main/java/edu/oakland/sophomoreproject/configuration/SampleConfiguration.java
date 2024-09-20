package edu.oakland.sophomoreproject.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SampleConfiguration {
	@Bean
	@Qualifier("sampleBean")
	public String sampleBean() {
		return "Hello, World!";
	}
}
