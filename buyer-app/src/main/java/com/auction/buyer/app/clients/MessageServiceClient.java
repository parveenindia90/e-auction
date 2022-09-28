package com.auction.buyer.app.clients;

import com.auction.buyer.app.model.BuyerBidInfo;
import com.auction.buyer.app.model.BuyerBidInfoWrapper;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name="API-GATEWAY/e-auction/api/v1", fallback = MessageServiceFallBack.class)
public interface MessageServiceClient {

    @RequestMapping(method = RequestMethod.GET, path = "/consumer/product-bids/{productId}", produces = "application/json")
    BuyerBidInfoWrapper getBidInfo(@PathVariable("productId") String productId);

    @RequestMapping(method = RequestMethod.POST, path = "/producer/bid/produce", produces = "application/json")
    String produceMessage(@RequestBody BuyerBidInfo buyerBidInfo);

}
