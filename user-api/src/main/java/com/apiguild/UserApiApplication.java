package com.apiguild;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class UserApiApplication {
	@Bean
	public AlwaysSampler defaultSampler(){
		return new AlwaysSampler();
	}
	public static void main(String[] args) {
		SpringApplication.run(UserApiApplication.class, args);
	}
}
