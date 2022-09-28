package com.auction.seller.app.model;

import lombok.Data;

import java.util.List;

@Data
public class ProductBidInfo {

    private ProductInfo productInfo;

    private List<BuyerBidInfo> buyerBidInfoList;
}
