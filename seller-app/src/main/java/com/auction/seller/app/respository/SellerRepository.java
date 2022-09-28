package com.auction.seller.app.respository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.auction.seller.app.model.SellerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SellerRepository {

    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    public SellerEntity saveSeller(SellerEntity seller){
        SellerEntity sellerEntity = dynamoDBMapper.load(SellerEntity.class, seller.getEmail());
        if(null == sellerEntity){
            dynamoDBMapper.save(seller);
        }
        return seller;
    }

}
