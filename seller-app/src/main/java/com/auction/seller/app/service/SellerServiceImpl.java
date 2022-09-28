package com.auction.seller.app.service;

import com.auction.seller.app.clients.MessageServiceClient;
import com.auction.seller.app.exception.CustomException;
import com.auction.seller.app.model.*;
import com.auction.seller.app.respository.ProductRepository;
import com.auction.seller.app.respository.SellerRepository;
import com.auction.seller.app.util.MethodStartEndLogger;
import com.auction.seller.app.util.SellerConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SellerServiceImpl implements SellerService{

    @Autowired
    private ProductRepository productRespository;

    @Autowired
    private SellerRepository sellerRespository;

    @Autowired
    private MessageServiceClient messageServiceClient;

    @Override
    @MethodStartEndLogger
    public String addNewProducts(SellerProductWrapper sellerProductWrapper) throws CustomException {
        SellerEntity seller = new SellerEntity();
        ProductEntity product = new ProductEntity();
        BeanUtils.copyProperties(sellerProductWrapper.getSellerInfo(), seller);
        BeanUtils.copyProperties(sellerProductWrapper.getProductInfo(), product);
        product.setSellerEmail(seller.getEmail());
        if(!checkProductAlreadyExists(product.getProductName())){
            productRespository.saveProduct(product);
            sellerRespository.saveSeller(seller);
        }else{
            throw new CustomException(SellerConstants.PRODUCT_DUPLICATE_ERROR);
        }
        return "Product added successfully";
    }

    @MethodStartEndLogger
    public boolean checkProductAlreadyExists(String productId){
        ProductEntity productEntity = productRespository.findProductByProductId(productId);
        return null != productEntity;
    }

    @Override
    @MethodStartEndLogger
    public String deleteProduct(String productId) throws CustomException{
        ProductEntity product = productRespository.findProductByProductId(productId);
        if(null != product){
            checkIfProductBidEndDateNotPassed(product);
            checkIfProductBidAvailable(product);
            productRespository.deleteProduct(product);
        }else{
            throw new CustomException(SellerConstants.PRODUCT_FOR_DELETE_NOT_AVAILABLE);
        }

        return "Product deleted successfully";
    }

    @MethodStartEndLogger
    private void checkIfProductBidEndDateNotPassed(ProductEntity product) throws CustomException{
        LocalDate localDate = LocalDate.now();
        LocalDate bidDate = LocalDate.from(product.getBidEndDate().toInstant().atZone(ZoneId.systemDefault()));

        if(localDate.isAfter(bidDate)){
            throw new CustomException(SellerConstants.BID_END_DATE_PASSED);
        }
    }

    @MethodStartEndLogger
    public void checkIfProductBidAvailable(ProductEntity product) throws CustomException{
        BuyerBidInfoWrapper buyerBidInfoWrapper = messageServiceClient.getBidInfo(product.getProductName());
        if(null != buyerBidInfoWrapper.getErrorMessage()){
            throw new CustomException(SellerConstants.PRODUCT_CAN_NOT_BE_DELETED);
        }
        List<BuyerBidInfo> buyerBidInfoList = buyerBidInfoWrapper.getBuyerBidInfoList();
        if(null != buyerBidInfoList && !buyerBidInfoList.isEmpty()){
            throw new CustomException(SellerConstants.PRODUCT_CAN_NOT_BE_DELETED_BID_AVAILABLE);
        }
    }

    @Override
    @MethodStartEndLogger
    public ProductBidInfo showProductBids(String productId) throws CustomException{
        ProductBidInfo productBidInfo = new ProductBidInfo();
        ProductInfo productInfo = retreiveProductInfo(productId);
        if(null != productInfo.getErrorMessage()){
            throw new CustomException(SellerConstants.PRODUCT_NOT_AVAILABLE);
        }
        productBidInfo.setProductInfo(productInfo);
        BuyerBidInfoWrapper buyerBidInfoWrapper = messageServiceClient.getBidInfo(productId);
        if(null != buyerBidInfoWrapper.getErrorMessage()){
            throw new CustomException(SellerConstants.PRODUCT_BIDS_NOT_AVAILABLE_ERROR);
        }
        List<BuyerBidInfo> buyerBidInfoList = buyerBidInfoWrapper.getBuyerBidInfoList();
        if(null != buyerBidInfoList && !buyerBidInfoList.isEmpty()){
            Comparator<BuyerBidInfo> bidInfoComparator = Comparator.comparingDouble(BuyerBidInfo::getBidAmount);
            productBidInfo.setBuyerBidInfoList(buyerBidInfoList.stream().sorted(bidInfoComparator).collect(Collectors.toList()));
        }else{
            productBidInfo.setBuyerBidInfoList(new ArrayList<>());
        }
        return productBidInfo;
    }

    @Override
    @MethodStartEndLogger
    public ProductInfo retreiveProductInfo(String productId)
    {
        ProductInfo productInfo = new ProductInfo();
        ProductEntity product = productRespository.findProductByProductId(productId);
        if(null != product){
            BeanUtils.copyProperties(product, productInfo);
        }else{
            productInfo.setErrorMessage(SellerConstants.PRODUCT_NOT_AVAILABLE);
        }
        return productInfo;
    }

    @Override
    public List<String> getAllProducts(String sellerEmail) {
        List<ProductEntity> productEntities = productRespository.findAllProduct();
        if(null != sellerEmail){
            return productEntities.stream().filter(productEntity -> sellerEmail.equals(productEntity.getSellerEmail())).map(ProductEntity::getProductName).collect(Collectors.toList());
        }else{
            return productEntities.stream().map(ProductEntity::getProductName).collect(Collectors.toList());
        }
    }

}
