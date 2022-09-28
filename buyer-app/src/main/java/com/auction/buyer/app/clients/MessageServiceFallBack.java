package com.auction.buyer.app.clients;

import com.auction.buyer.app.model.BuyerBidInfo;
import com.auction.buyer.app.model.BuyerBidInfoWrapper;
import com.auction.buyer.app.util.BuyerConstants;
import com.auction.buyer.app.util.MethodStartEndLogger;
import org.springframework.stereotype.Component;

@Component
public class MessageServiceFallBack implements MessageServiceClient {

    @Override
    @MethodStartEndLogger
    public BuyerBidInfoWrapper getBidInfo(String productId){
        BuyerBidInfoWrapper buyerBidInfoWrapper = new BuyerBidInfoWrapper();
        buyerBidInfoWrapper.setErrorMessage(BuyerConstants.UNABLE_TO_FETCH_BIDS);
        return buyerBidInfoWrapper;
    }

    @Override
    @MethodStartEndLogger
    public String produceMessage(BuyerBidInfo buyerBidInfo){
        return "Message successfully produced";
    }

}
