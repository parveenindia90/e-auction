package com.auction.seller.app.validation;


import com.auction.seller.app.exception.CustomException;
import com.auction.seller.app.model.ProductInfo;
import com.auction.seller.app.model.SellerInfo;
import com.auction.seller.app.model.SellerProductWrapper;
import com.auction.seller.app.util.MethodStartEndLogger;
import com.auction.seller.app.util.SellerConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class SellerRequestValidator {

    private static final Logger logger = LoggerFactory.getLogger(SellerRequestValidator.class);

    @Value("${product.category}")
    private List<String> productCategories;

    /*
     * This method is used to validate productInfo for adding new product
     */
    @MethodStartEndLogger
    public void validateAddProductInfo(SellerProductWrapper sellerProductWrapper) throws CustomException{
        ProductInfo productInfo = sellerProductWrapper.getProductInfo();
        if(null == productInfo){
            throw new CustomException(SellerConstants.PRODUCT_INFO_ERROR_MESSAGE);
        }

        if(!productCategories.contains(productInfo.getCategory())){
            throw new CustomException(SellerConstants.CATEGORY_ERROR_MESSAGE);
        }
        try{
            Double.parseDouble(productInfo.getStartingPrice());
        }catch(NumberFormatException e){
            throw new CustomException(SellerConstants.PRICE_ERROR_MESSAGE);
        }

        try{
            Double.parseDouble(productInfo.getStartingPrice());
        }catch(NumberFormatException e) {
            throw new CustomException(SellerConstants.PRICE_ERROR_MESSAGE);
        }

        LocalDate localDate = LocalDate.from(productInfo.getBidEndDate().toInstant().atZone(ZoneId.systemDefault()));

        if(localDate.isBefore(LocalDate.now())){
            throw new CustomException(SellerConstants.BID_DATE_ERROR_MESSAGE);
        }

        String productName = productInfo.getProductName();
        if(null == productName){
            throw new CustomException(SellerConstants.PRODUCT_NAME_NULL_ERROR_MESSAGE);
        }
        if(productName.length() < 5 || productName.length() > 30){
            throw new CustomException(SellerConstants.PRODUCT_NAME_SIZE_ERROR_MESSAGE);
        }

        validateSellerInformation(sellerProductWrapper.getSellerInfo());

    }

    /*
     * This method is used to validate sellerInfo for adding new product
     */
    @MethodStartEndLogger
    public void validateSellerInformation(SellerInfo sellerInfo) throws CustomException{
        if(null == sellerInfo){
            throw new CustomException(SellerConstants.SELLER_INFO_ERROR_MESSAGE);
        }

        String firstName = sellerInfo.getFirstName();
        if(null == firstName){
            throw new CustomException(SellerConstants.SELLER_FIRST_NAME_NULL_ERROR_MESSAGE);
        }
        if(firstName.length() < 5 || firstName.length() > 30){
            throw new CustomException(SellerConstants.SELLER_FIRST_NAME_SIZE_ERROR_MESSAGE);
        }

        String lastName = sellerInfo.getLastName();
        if(null == lastName){
            throw new CustomException(SellerConstants.SELLER_LAST_NAME_NULL_ERROR_MESSAGE);
        }
        if(lastName.length() < 3 || lastName.length() > 25){
            throw new CustomException(SellerConstants.SELLER_LAST_NAME_SIZE_ERROR_MESSAGE);
        }

        String email = sellerInfo.getEmail();
        if(null == email){
            throw new CustomException(SellerConstants.SELLER_EMAIL_NULL_ERROR_MESSAGE);
        }

        String emailRegex = "^(.+)@(.+)$";
        if(!Pattern.compile(emailRegex).matcher(email).matches()){
            throw new CustomException(SellerConstants.SELLER_EMAIL_INVALID_ERROR_MESSAGE);
        }

        String phoneNumber = sellerInfo.getPhoneNumber();
        if(null == phoneNumber){
            throw new CustomException(SellerConstants.SELLER_PHONE_NULL_ERROR_MESSAGE);
        }

        String phoneRegex = "\\d{10}";
        if(!Pattern.compile(phoneRegex).matcher(phoneNumber).matches()){
            throw new CustomException(SellerConstants.SELLER_PHONE_INVALID_ERROR_MESSAGE);
        }
    }

}
