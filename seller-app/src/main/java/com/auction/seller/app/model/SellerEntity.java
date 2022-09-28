package com.auction.seller.app.model;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBTable(tableName = "seller")
public class SellerEntity {

    @DynamoDBAttribute
    private String firstName;

    @DynamoDBAttribute
    private String lastName;

    @DynamoDBAttribute
    private String address;

    @DynamoDBAttribute
    private String city;

    @DynamoDBAttribute
    private String state;

    @DynamoDBAttribute
    private String pinCode;

    @DynamoDBAttribute
    private String phoneNumber;

    @DynamoDBHashKey
    private String email;

}
