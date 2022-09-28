package com.auction.cloud.pubsub.service;

import com.auction.cloud.pubsub.model.BuyerBidInfo;
import com.auction.cloud.pubsub.model.BuyerBidInfoWrapper;

public interface MessageService {

    String produceMessage(BuyerBidInfo buyerBidInfo);

    BuyerBidInfoWrapper getProductBids(String productId);

}
