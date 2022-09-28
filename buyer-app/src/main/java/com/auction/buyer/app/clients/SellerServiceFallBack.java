package com.auction.buyer.app.clients;

import com.auction.buyer.app.model.ProductInfo;
import com.auction.buyer.app.util.BuyerConstants;
import com.auction.buyer.app.util.MethodStartEndLogger;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

@Component
public class SellerServiceFallBack implements SellerServiceClient{

    @Override
    @MethodStartEndLogger
    public ProductInfo getProductInfo(String productId) {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setErrorMessage(BuyerConstants.PRODUCT_FOR_BID_NOT_AVAILABLE);
        return productInfo;
    }

}
