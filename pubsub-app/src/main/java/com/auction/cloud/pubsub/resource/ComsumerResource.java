package com.auction.cloud.pubsub.resource;

import com.auction.cloud.pubsub.model.BuyerBidInfoWrapper;
import com.auction.cloud.pubsub.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/e-auction/api/v1/consumer")
public class ComsumerResource {

    @Autowired
    private MessageService messageService;

    @GetMapping("product-bids/{productId}")
    public BuyerBidInfoWrapper getProductBids(@PathVariable("productId") String productId) {
        return messageService.getProductBids(productId);
    }
}
