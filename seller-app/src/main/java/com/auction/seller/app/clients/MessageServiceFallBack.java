package com.auction.seller.app.clients;

import com.auction.seller.app.model.BuyerBidInfoWrapper;
import com.auction.seller.app.util.MethodStartEndLogger;
import com.auction.seller.app.util.SellerConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MessageServiceFallBack implements MessageServiceClient {

    @Override
    @MethodStartEndLogger
    public BuyerBidInfoWrapper getBidInfo(String productId){
        BuyerBidInfoWrapper buyerBidInfoWrapper = new BuyerBidInfoWrapper();
        buyerBidInfoWrapper.setErrorMessage(SellerConstants.PRODUCT_BIDS_NOT_AVAILABLE_ERROR);
        return buyerBidInfoWrapper;
    }

}
