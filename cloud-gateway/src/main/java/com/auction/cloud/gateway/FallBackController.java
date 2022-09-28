package com.auction.cloud.gateway;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallBackController {

    @RequestMapping("/sellerServiceFallBack")
    public String userServiceFallBackMethod() {
        return "Seller service is taking longer than Expected." +
                " Please try again later";
    }

    @RequestMapping("/buyerServiceFallBack")
    public String buyerServiceFallBackMethod() {
        return "Buyer service is taking longer than Expected." +
                " Please try again later";
    }

    @RequestMapping("/pubsubServiceFallBack")
    public String producerServiceFallBacl() {
        return "Pubsub service is taking longer than Expected." +
                " Please try again later";
    }

}
