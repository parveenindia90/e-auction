package com.auction.cloud.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.Date;

@DynamoDBTable(tableName ="product")
public class ProductEntity
{

    public ProductEntity(){}

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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getDetailsDesc() {
        return detailsDesc;
    }

    public void setDetailsDesc(String detailsDesc) {
        this.detailsDesc = detailsDesc;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStartingPrice() {
        return startingPrice;
    }

    public void setStartingPrice(String startingPrice) {
        this.startingPrice = startingPrice;
    }

    public Date getBidEndDate() {
        return bidEndDate;
    }

    public void setBidEndDate(Date bidEndDate) {
        this.bidEndDate = bidEndDate;
    }

    public String getSellerEmail() {
        return sellerEmail;
    }

    public void setSellerEmail(String sellerEmail) {
        this.sellerEmail = sellerEmail;
    }
}
