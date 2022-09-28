package com.auction.cloud.pubsub.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBTable(tableName = "bid_info")
public class BuyerBidInfo implements Serializable {

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

    @DynamoDBRangeKey
    private String productName;

    @DynamoDBAttribute
    private double bidAmount;

}
