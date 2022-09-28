package com.auction.cloud.pubsub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.aws.autoconfigure.context.ContextStackAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication(exclude = {ContextStackAutoConfiguration.class})
@EnableEurekaClient
public class PubSubApplication {

	public static void main(String[] args) {
		SpringApplication.run(PubSubApplication.class, args);
	}

}
