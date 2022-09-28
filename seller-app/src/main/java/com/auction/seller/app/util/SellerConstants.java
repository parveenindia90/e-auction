package com.auction.seller.app.util;

public interface SellerConstants {

    String LOG_INFO_METHOD_START_MSG = "START :: {}";
    String LOG_INFO_METHOD_END_MSG = "END :: {}";
    String PRODUCT_INFO_ERROR_MESSAGE = "Product Info Can not be empty";
    String CATEGORY_ERROR_MESSAGE = "Invalid Product Category";
    String PRICE_ERROR_MESSAGE = "Starting price should be number";
    String BID_DATE_ERROR_MESSAGE = "Bid end date should be a future date";
    String PRODUCT_NAME_NULL_ERROR_MESSAGE = "Product name can not be null";
    String PRODUCT_NAME_SIZE_ERROR_MESSAGE = "Product name should be minimum 5 and maximum 30 characters";
    String SELLER_INFO_ERROR_MESSAGE = "Seller Info Can not be empty";
    String SELLER_FIRST_NAME_NULL_ERROR_MESSAGE = "First name can not be null";
    String SELLER_FIRST_NAME_SIZE_ERROR_MESSAGE = "First name should be minimum 5 and maximum 30 characters";
    String SELLER_LAST_NAME_NULL_ERROR_MESSAGE = "Last name can not be null";
    String SELLER_LAST_NAME_SIZE_ERROR_MESSAGE = "Last name should be minimum 3 and maximum 25 characters";
    String SELLER_EMAIL_NULL_ERROR_MESSAGE = "Email can not be null";
    String SELLER_EMAIL_INVALID_ERROR_MESSAGE = "Invalid email adddress";
    String SELLER_PHONE_NULL_ERROR_MESSAGE = "Phone nu,ber can not be null";
    String SELLER_PHONE_INVALID_ERROR_MESSAGE = "Phone number should be 10 digits";
    String BID_END_DATE_PASSED = "Bid End date has already passed";
    String PRODUCT_FOR_DELETE_NOT_AVAILABLE = "Product for delete is not avaiable";
    String PRODUCT_CAN_NOT_BE_DELETED = "Product can not be deleted right now, please try after sometime";
    String PRODUCT_CAN_NOT_BE_DELETED_BID_AVAILABLE = "Product can not be deleted , bid available";
    String PRODUCT_DUPLICATE_ERROR = "Product already exists, please add another product";
    String PRODUCT_BIDS_NOT_AVAILABLE_ERROR = "Product bids are currently not available, please try after sometime";
    String PRODUCT_NOT_AVAILABLE = "Product not available";
}
