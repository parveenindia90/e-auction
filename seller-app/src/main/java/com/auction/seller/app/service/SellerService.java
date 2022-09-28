package com.auction.seller.app.service;

import com.auction.seller.app.exception.CustomException;
import com.auction.seller.app.model.ProductBidInfo;
import com.auction.seller.app.model.ProductInfo;
import com.auction.seller.app.model.SellerProductWrapper;

import java.util.List;

public interface SellerService {

    String addNewProducts(SellerProductWrapper sellerProductWrapper) throws CustomException;

    String deleteProduct(String productId) throws CustomException;

    ProductBidInfo showProductBids(String productId) throws CustomException;

    ProductInfo retreiveProductInfo(String productId);

    List<String> getAllProducts(String sellerEmail);
}
