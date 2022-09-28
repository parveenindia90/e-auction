package com.auction.cloud.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.auction.cloud.model.BuyerBidInfo;
import com.auction.cloud.model.ProductEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProductRepository {

    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    public String deleteProduct(ProductEntity product) {
        dynamoDBMapper.delete(product);
        return "Product deleted successfully";
    }

    public ProductEntity findProductByProductId(String productId){
        return dynamoDBMapper.load(ProductEntity.class, productId);
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
