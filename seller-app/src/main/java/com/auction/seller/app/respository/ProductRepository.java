package com.auction.seller.app.respository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.auction.seller.app.model.ProductEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepository {

    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    public ProductEntity saveProduct(ProductEntity product){
        dynamoDBMapper.save(product);
        return product;
    }

    public ProductEntity findProductByProductId(String productId){
        return dynamoDBMapper.load(ProductEntity.class, productId);
    }

    public String deleteProduct(ProductEntity product){
        dynamoDBMapper.delete(product);
        return "Product deleted successfully";
    }

    public List<ProductEntity> findAllProduct(){
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        List<ProductEntity> productEntityList = dynamoDBMapper.scan(ProductEntity.class, scanExpression);
        return productEntityList;
    }


}
