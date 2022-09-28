package com.auction.buyer.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableEurekaClient
public class BuyerAppApplication {
	public static void main(String[] args) {
		SpringApplication.run(BuyerAppApplication.class, args);
	}

}
