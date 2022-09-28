package com.auction.seller.app.clients;

import com.auction.seller.app.model.BuyerBidInfoWrapper;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name="API-GATEWAY/e-auction/api/v1/consumer", fallback = MessageServiceFallBack.class)
public interface MessageServiceClient {

    @RequestMapping(method = RequestMethod.GET, path = "/product-bids/{productId}", produces = "application/json")
    BuyerBidInfoWrapper getBidInfo(@PathVariable("productId") String productId);

}
