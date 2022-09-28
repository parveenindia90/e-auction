package com.auction.buyer.app.util;

public interface BuyerConstants {

    String LOG_INFO_METHOD_START_MSG = "START :: {}";
    String LOG_INFO_METHOD_END_MSG = "END :: {}";
    String BUYER_BID_INFO_NULL = "Bid Info Can not be empty";
    String PRODUCT_NAME_NULL_ERROR_MESSAGE = "Product name can not be null";
    String PRODUCT_NAME_SIZE_ERROR_MESSAGE = "Product name should be minimum 5 and maximum 30 characters";
    String BUYER_FIRST_NAME_NULL_ERROR_MESSAGE = "First name can not be null";
    String BUYER_FIRST_NAME_SIZE_ERROR_MESSAGE = "First name should be minimum 5 and maximum 30 characters";
    String BUYER_LAST_NAME_NULL_ERROR_MESSAGE = "Last name can not be null";
    String BUYER_LAST_NAME_SIZE_ERROR_MESSAGE = "Last name should be minimum 3 and maximum 25 characters";
    String BUYER_EMAIL_NULL_ERROR_MESSAGE = "Email can not be null";
    String BUYER_EMAIL_INVALID_ERROR_MESSAGE = "Invalid email adddress";
    String BUYER_PHONE_NULL_ERROR_MESSAGE = "Phone nu,ber can not be null";
    String BUYER_PHONE_INVALID_ERROR_MESSAGE = "Phone number should be 10 digits";
    String BID_AMOUNT_INVALID = "Bid amount is invalid";
    String PRODUCT_FOR_BID_NOT_AVAILABLE = "Product for bid is not available";
    String BID_END_DATE_PASSED = "Can not place bid as bid end date has passed";
    String UNABLE_TO_FETCH_BIDS = "Unable to fetch bids, please try after sometime";
    String BID_ALREADY_PRESENT_FOR_THIS_BUYER = "Bid already present for this buyer";
    String BID_NOT_PRESENT_FOR_UPDATE = "Can not update bid, as bid not present";

}
