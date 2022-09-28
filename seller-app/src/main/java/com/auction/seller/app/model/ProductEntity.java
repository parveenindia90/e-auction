package com.auction.seller.app.model;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBTable(tableName ="product")
public class ProductEntity
{

    @DynamoDBHashKey
    private String productName;

    @DynamoDBAttribute
    private String shortDesc;

    @DynamoDBAttribute
    private String detailsDesc;

    @DynamoDBAttribute
    private String category;

    @DynamoDBAttribute
    private String startingPrice;

    @DynamoDBAttribute
    private Date bidEndDate;

    @DynamoDBAttribute
    private String sellerEmail;

}
