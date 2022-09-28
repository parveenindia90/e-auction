package com.auction.cloud.pubsub.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.auction.cloud.pubsub.model.BuyerBidInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BuyerBidInfoRepository {

    @Autowired
    private DynamoDBMapper dynamoDBMapper;


    public BuyerBidInfo saveBidInfo(BuyerBidInfo buyerBidInfo) {
        dynamoDBMapper.save(buyerBidInfo);
        return buyerBidInfo;
    }

    public List<BuyerBidInfo> findByProductName(String productId) {
        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(":productName", new AttributeValue().withS(productId));
        DynamoDBScanExpression expression = new DynamoDBScanExpression().withFilterExpression("productName = :productName")
                .withExpressionAttributeValues(eav);
        List<BuyerBidInfo> buyerBidInfoList = dynamoDBMapper.scan(BuyerBidInfo.class, expression);
        return  buyerBidInfoList;
    }

}
