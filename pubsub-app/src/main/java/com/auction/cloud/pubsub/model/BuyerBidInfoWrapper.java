package com.auction.cloud.pubsub.model;

import lombok.Data;

import java.util.List;

@Data
public class BuyerBidInfoWrapper {

    List<BuyerBidInfo> buyerBidInfoList;

    private String errorMessage;
}
