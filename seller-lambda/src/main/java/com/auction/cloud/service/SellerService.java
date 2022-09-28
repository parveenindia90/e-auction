package com.auction.cloud.service;

import com.auction.cloud.SellerLambdaApplication;
import com.auction.cloud.model.BuyerBidInfo;
import com.auction.cloud.model.ProductEntity;
import com.auction.cloud.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Service
public class SellerService {

    public static final Logger LOGGER = LoggerFactory.getLogger(SellerService.class);

    @Autowired
    private ProductRepository productRepository;

    public String deleteProduct(String productId) {
        LOGGER.info("Product Id is :: " , productId);
        if("DUMMY".equals(productId)){
            throw new NullPointerException();
        }
        ProductEntity product = productRepository.findProductByProductId(productId);
        if(null != product){
            if(checkIfProductBidEndDateNotPassed(product)){
                return "Product can not be deleted as bid end date has passed";
            }
            if(checkIfProductBidAvailable(product)){
                return "Product can not be deleted as there is an active bid available";
            }
            productRepository.deleteProduct(product);
        }else{
            return "Product for delete not available";
        }

        return "Product deleted successfully";
    }

    private boolean checkIfProductBidEndDateNotPassed(ProductEntity product) {
        LocalDate localDate = LocalDate.now();
        LocalDate bidDate = LocalDate.from(product.getBidEndDate().toInstant().atZone(ZoneId.systemDefault()));

        if(localDate.isAfter(bidDate)){
            return true;
        }
        return false;
    }

    public boolean checkIfProductBidAvailable(ProductEntity product) {

        List<BuyerBidInfo> buyerBidInfoList = productRepository.findByProductName(product.getProductName());
        if(null != buyerBidInfoList && !buyerBidInfoList.isEmpty()){
            return true;
        }
        return false;
    }

}
