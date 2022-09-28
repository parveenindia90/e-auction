package com.auction.kafka.app.service;

import com.auction.kafka.app.model.BuyerBidInfo;
import com.auction.kafka.app.model.BuyerBidInfoWrapper;

public interface KafkaService {

    BuyerBidInfoWrapper getProductBids(String productId);

    String produceMessage(String buyerEmail, BuyerBidInfo buyerBidInfo);
}
