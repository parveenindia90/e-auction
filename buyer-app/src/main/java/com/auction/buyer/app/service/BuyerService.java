package com.auction.buyer.app.service;

import com.auction.buyer.app.exception.CustomException;
import com.auction.buyer.app.model.BuyerBidInfo;

public interface BuyerService {

    String placeNewBid(BuyerBidInfo buyerBidInfo) throws CustomException;

    String updateBidAmount(String productId, String buyerEmail, String bidAmount) throws CustomException;
}
