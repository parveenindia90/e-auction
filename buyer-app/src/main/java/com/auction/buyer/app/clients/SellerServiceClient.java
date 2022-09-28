package com.auction.buyer.app.clients;

import com.auction.buyer.app.model.ProductInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name="API-GATEWAY", fallback = SellerServiceFallBack.class)
public interface SellerServiceClient {

    @RequestMapping(method = RequestMethod.GET, path = "/productDetails/{productId}", produces = "application/json")
    ProductInfo getProductInfo(@PathVariable("productId") String productId);

}
