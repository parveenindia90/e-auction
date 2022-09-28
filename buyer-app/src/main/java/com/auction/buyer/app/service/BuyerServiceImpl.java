package com.auction.buyer.app.service;

import com.auction.buyer.app.clients.MessageServiceClient;
import com.auction.buyer.app.clients.SellerServiceClient;
import com.auction.buyer.app.exception.CustomException;
import com.auction.buyer.app.model.*;
import com.auction.buyer.app.util.BuyerConstants;
import com.auction.buyer.app.util.MethodStartEndLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BuyerServiceImpl implements BuyerService{

    @Autowired
    private MessageServiceClient messageServiceClient;

    @Autowired
    private SellerServiceClient sellerServiceClient;

    @Override
    @MethodStartEndLogger
    public String placeNewBid(BuyerBidInfo buyerBidInfo) throws CustomException{
        doProductValidation(buyerBidInfo.getProductName());
        checkForExistingBid(buyerBidInfo);
        messageServiceClient.produceMessage(buyerBidInfo);
        return "Bid placed successfully";
    }

    @MethodStartEndLogger
    private void doProductValidation(String productId) throws CustomException{
        ProductInfo productInfo = sellerServiceClient.getProductInfo(productId);
        if(null != productInfo.getErrorMessage()){
            throw new CustomException(BuyerConstants.PRODUCT_FOR_BID_NOT_AVAILABLE);
        }
        if(!productId.equals(productInfo.getProductName())){
            throw new CustomException(BuyerConstants.PRODUCT_FOR_BID_NOT_AVAILABLE);
        }

        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime bidDateTime = productInfo.getBidEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        if(localDateTime.isAfter(bidDateTime)){
            throw new CustomException(BuyerConstants.BID_END_DATE_PASSED);
        }

    }

    @MethodStartEndLogger
    private void checkForExistingBid(BuyerBidInfo buyerBidInfo) throws CustomException{
        List<BuyerBidInfo> buyerBidInfoList = fetchBidList(buyerBidInfo.getProductName(), buyerBidInfo.getEmail());
        if(!buyerBidInfoList.isEmpty()){
           throw new CustomException(BuyerConstants.BID_ALREADY_PRESENT_FOR_THIS_BUYER);
        }
    }

    @MethodStartEndLogger
    private List<BuyerBidInfo> fetchBidList(String productId, String buyerEmail) throws CustomException{
        BuyerBidInfoWrapper buyerBidInfoWrapper = messageServiceClient.getBidInfo(productId);
        if(null != buyerBidInfoWrapper.getErrorMessage()){
            throw new CustomException(BuyerConstants.UNABLE_TO_FETCH_BIDS);
        }
        List<BuyerBidInfo> buyerBidInfoList = buyerBidInfoWrapper.getBuyerBidInfoList();
        if(null != buyerBidInfoList && !buyerBidInfoList.isEmpty()){
            return buyerBidInfoList.stream().filter(buyerBid -> buyerEmail.equals(buyerBid.getEmail())).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    @MethodStartEndLogger
    public String updateBidAmount(String productId, String buyerEmail, String bidAmount) throws CustomException{
        List<BuyerBidInfo> buyerBidInfoList = fetchBidList(productId, buyerEmail);

        if(buyerBidInfoList.isEmpty()){
            throw new CustomException(BuyerConstants.BID_NOT_PRESENT_FOR_UPDATE);
        }
        BuyerBidInfo buyerBidInfo = buyerBidInfoList.get(0);
        buyerBidInfo.setBidAmount(Double.parseDouble(bidAmount));
        messageServiceClient.produceMessage(buyerBidInfo);
        return "bid updated successfully";
    }

}
