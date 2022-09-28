package com.auction.kafka.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
public class KafkaAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaAppApplication.class, args);
	}

}
