package com.auction.cloud;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.auction.cloud.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.function.Function;

@SpringBootApplication
public class SellerLambdaApplication {

	@Autowired
	private SellerService sellerService;

	public static void main(String[] args) {
		SpringApplication.run(SellerLambdaApplication.class, args);
	}

	@Bean
	public Function<APIGatewayProxyRequestEvent, String> deleteProduct(){
		return (requestEvent) -> sellerService.deleteProduct(requestEvent.getQueryStringParameters().get("productId"));
	}

}
