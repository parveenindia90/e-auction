package com.auction.seller.app.resource;

import com.auction.seller.app.model.ProductInfo;
import com.auction.seller.app.service.SellerService;
import com.auction.seller.app.util.MethodStartEndLogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/productDetails")
public class ProductResource {

    @Autowired
    private SellerService sellerService;

    /**
     * This method will be used to return the product info
     */
    @GetMapping("/{productId}")
    @MethodStartEndLogger
    public ResponseEntity<ProductInfo> getPrductInfo(@PathVariable("productId") String productId){
        log.info("getting product details");
        ProductInfo productInfo = sellerService.retreiveProductInfo(productId);
        return ResponseEntity.ok(productInfo);
    }


}
