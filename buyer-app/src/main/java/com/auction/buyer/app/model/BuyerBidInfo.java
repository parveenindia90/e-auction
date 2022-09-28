package com.auction.buyer.app.model;

import lombok.Data;

@Data
public class BuyerBidInfo {

    private String firstName;

    private String lastName;

    private String address;

    private String city;

    private String state;

    private String pinCode;

    private String phoneNumber;

    private String email;

    private String productName;

    private double bidAmount;

}
