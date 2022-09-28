package com.auction.kafka.app.resource;

import com.auction.kafka.app.model.BuyerBidInfo;
import com.auction.kafka.app.model.BuyerBidInfoWrapper;
import com.auction.kafka.app.service.KafkaServiceImpl;
import com.auction.kafka.app.util.MethodStartEndLogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/e-auction/api/v1/kafka")
public class KafkaResource {

    @Autowired
    private KafkaServiceImpl kafkaService;

    /**
     * This method is used to get all the bids placed for a product from kafka
     */

    @GetMapping("product-bids/{productId}")
    @MethodStartEndLogger
    public BuyerBidInfoWrapper getProductBids(@PathVariable("productId") String productId) {
        return kafkaService.getProductBids(productId);
    }

    @PostMapping("produce/{buyerEmail}")
    @MethodStartEndLogger
    public String produceMessage(@PathVariable("buyerEmail") String buyerEmail, @RequestBody BuyerBidInfo buyerBidInfo) {
        return kafkaService.produceMessage(buyerEmail, buyerBidInfo);
    }

}
