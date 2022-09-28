package com.auction.buyer.app.validation;


import com.auction.buyer.app.exception.CustomException;
import com.auction.buyer.app.model.BuyerBidInfo;
import com.auction.buyer.app.util.BuyerConstants;
import com.auction.buyer.app.util.MethodStartEndLogger;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class BuyerRequestValidator {

    /*
     * This method is used to validate bid info
     */
    @MethodStartEndLogger
    public void validateBidInfo(BuyerBidInfo buyerBidInfo) throws CustomException{
        if(null == buyerBidInfo){
            throw new CustomException(BuyerConstants.BUYER_BID_INFO_NULL);
        }

        String firstName = buyerBidInfo.getFirstName();
        if(null == firstName){
            throw new CustomException(BuyerConstants.BUYER_FIRST_NAME_NULL_ERROR_MESSAGE);
        }
        if(firstName.length() < 5 || firstName.length() > 30){
            throw new CustomException(BuyerConstants.BUYER_FIRST_NAME_SIZE_ERROR_MESSAGE);
        }

        String lastName = buyerBidInfo.getLastName();
        if(null == lastName){
            throw new CustomException(BuyerConstants.BUYER_LAST_NAME_NULL_ERROR_MESSAGE);
        }
        if(lastName.length() < 3 || lastName.length() > 25){
            throw new CustomException(BuyerConstants.BUYER_LAST_NAME_SIZE_ERROR_MESSAGE);
        }

        String email = buyerBidInfo.getEmail();
        if(null == email){
            throw new CustomException(BuyerConstants.BUYER_EMAIL_NULL_ERROR_MESSAGE);
        }

        String emailRegex = "^(.+)@(.+)$";
        if(!Pattern.compile(emailRegex).matcher(email).matches()){
            throw new CustomException(BuyerConstants.BUYER_EMAIL_INVALID_ERROR_MESSAGE);
        }

        String phoneNumber = buyerBidInfo.getPhoneNumber();
        if(null == phoneNumber){
            throw new CustomException(BuyerConstants.BUYER_PHONE_NULL_ERROR_MESSAGE);
        }

        String phoneRegex = "\\d{10}";
        if(!Pattern.compile(phoneRegex).matcher(phoneNumber).matches()){
            throw new CustomException(BuyerConstants.BUYER_PHONE_INVALID_ERROR_MESSAGE);
        }

    }

    /*
     * This method is used to validate sellerInfo for adding new product
     */
    @MethodStartEndLogger
    public void validateUpdateBidRequest(String productId, String buyerEmail, String bidAmount) throws CustomException{
       try{
            Double.parseDouble(bidAmount);
        }catch(NumberFormatException e){
            throw new CustomException(BuyerConstants.BID_AMOUNT_INVALID);
        }

        if(null == productId){
            throw new CustomException(BuyerConstants.PRODUCT_NAME_NULL_ERROR_MESSAGE);
        }
        if(productId.length() < 5 || productId.length() > 30){
            throw new CustomException(BuyerConstants.PRODUCT_NAME_SIZE_ERROR_MESSAGE);
        }

        if(null == buyerEmail){
            throw new CustomException(BuyerConstants.BUYER_EMAIL_NULL_ERROR_MESSAGE);
        }

        String emailRegex = "^(.+)@(.+)$";
        if(!Pattern.compile(emailRegex).matcher(buyerEmail).matches()){
            throw new CustomException(BuyerConstants.BUYER_EMAIL_INVALID_ERROR_MESSAGE);
        }


    }

}
