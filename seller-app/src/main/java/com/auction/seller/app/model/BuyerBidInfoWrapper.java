package com.auction.seller.app.model;

import lombok.Data;

import java.util.List;

@Data
public class BuyerBidInfoWrapper {

    List<BuyerBidInfo> buyerBidInfoList;

    private String errorMessage;
}
