package com.auction.seller.app.service;

import com.auction.seller.app.clients.MessageServiceClient;
import com.auction.seller.app.exception.CustomException;
import com.auction.seller.app.model.*;
import com.auction.seller.app.respository.ProductRepository;
import com.auction.seller.app.respository.SellerRepository;
import com.auction.seller.app.util.SellerConstants;
import com.auction.seller.app.validation.SellerRequestValidatorTest;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Date;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class SellerServiceImplTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(SellerRequestValidatorTest.class);

    @InjectMocks
    private SellerServiceImpl sellerServiceImpl;

    @Mock
    private MessageServiceClient messageServiceClient;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private SellerRepository sellerRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        LOGGER.info("setup before test method");
        ReflectionTestUtils.setField(sellerServiceImpl, "messageServiceClient", messageServiceClient);
        ReflectionTestUtils.setField(sellerServiceImpl, "productRepository", productRepository);
        ReflectionTestUtils.setField(sellerServiceImpl, "sellerRepository", sellerRepository);

    }

    @Test
    public void test_addNewProduct_success(){
        SellerProductWrapper sellerProductWrapper = new SellerProductWrapper();
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductName("OR_111");
        sellerProductWrapper.setProductInfo(productInfo);
        SellerInfo sellerInfo = new SellerInfo();
        sellerInfo.setEmail("test@mail.com");
        sellerProductWrapper.setSellerInfo(sellerInfo);
        Mockito.when(productRepository.saveProduct(Mockito.any())).thenReturn(new ProductEntity());
        Mockito.when(productRepository.findProductByProductId(Mockito.anyString())).thenReturn(null);
        Mockito.when(sellerRepository.saveSeller(Mockito.any())).thenReturn(new SellerEntity());
        assertEquals("Product added successfully", sellerServiceImpl.addNewProducts(sellerProductWrapper));
    }

    @Test
    public void test_addNewProduct_failure(){
        SellerProductWrapper sellerProductWrapper = new SellerProductWrapper();
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductName("OR_111");
        sellerProductWrapper.setProductInfo(productInfo);
        SellerInfo sellerInfo = new SellerInfo();
        sellerInfo.setEmail("test@mail.com");
        sellerProductWrapper.setSellerInfo(sellerInfo);
        Mockito.when(productRepository.findProductByProductId(Mockito.anyString())).thenReturn(new ProductEntity());
        assertThrows(CustomException.class,
                ()->sellerServiceImpl.addNewProducts(sellerProductWrapper));
    }

    @Test
    public void test_deleteProduct_success(){
        ProductEntity productEntity = new ProductEntity();
        productEntity.setBidEndDate(new Date());
        productEntity.setProductName("OR_111");
        BuyerBidInfoWrapper buyerBidInfoWrapper = new BuyerBidInfoWrapper();
        Mockito.when(messageServiceClient.getBidInfo(Mockito.anyString())).thenReturn(buyerBidInfoWrapper);
        Mockito.when(productRepository.findProductByProductId(Mockito.anyString())).thenReturn(productEntity);
        assertEquals("Product deleted successfully", sellerServiceImpl.deleteProduct("OR_111"));
    }

    @Test
    public void test_deleteProduct_productNotAvailable(){
        Mockito.when(productRepository.findProductByProductId(Mockito.anyString())).thenReturn(null);
        assertThrows(CustomException.class,
                ()->sellerServiceImpl.deleteProduct("OR_111"));
    }

    @Test
    public void test_deleteProduct_bidEndDatePassed(){
        ProductEntity productEntity = new ProductEntity();
        productEntity.setBidEndDate(new Date(12/12/1999));
        Mockito.when(productRepository.findProductByProductId(Mockito.anyString())).thenReturn(productEntity);
        assertThrows(CustomException.class,
                ()->sellerServiceImpl.deleteProduct("OR_111"));
    }

    @Test
    public void test_deleteProduct_bidInfoNotAvailable(){
        ProductEntity productEntity = new ProductEntity();
        productEntity.setBidEndDate(new Date());
        productEntity.setProductName("OR_111");
        BuyerBidInfoWrapper buyerBidInfoWrapper = new BuyerBidInfoWrapper();
        buyerBidInfoWrapper.setBuyerBidInfoList(List.of(new BuyerBidInfo()));
        Mockito.when(messageServiceClient.getBidInfo(Mockito.anyString())).thenReturn(buyerBidInfoWrapper);
        Mockito.when(productRepository.findProductByProductId(Mockito.anyString())).thenReturn(productEntity);
        assertThrows(CustomException.class,
                ()->sellerServiceImpl.deleteProduct("OR_111"));
        buyerBidInfoWrapper.setErrorMessage(SellerConstants.PRODUCT_CAN_NOT_BE_DELETED);
        assertThrows(CustomException.class,
                ()->sellerServiceImpl.deleteProduct("OR_111"));
    }

    @Test
    public void test_retreiveProductInfo_success(){
        ProductEntity productEntity = new ProductEntity();
        productEntity.setBidEndDate(new Date());
        productEntity.setProductName("OR_111");
        Mockito.when(productRepository.findProductByProductId(Mockito.anyString())).thenReturn(productEntity);
        ProductInfo productInfo = sellerServiceImpl.retreiveProductInfo("OR_111");
        assertEquals("OR_111", productInfo.getProductName());
    }

    @Test
    public void test_retreiveProductInfo_failure() {
        Mockito.when(productRepository.findProductByProductId(Mockito.anyString())).thenReturn(null);
        ProductInfo productInfo = sellerServiceImpl.retreiveProductInfo("OR_111");
        assertEquals(SellerConstants.PRODUCT_NOT_AVAILABLE, productInfo.getErrorMessage());
    }

    @Test
    public void test_showProductBids_success(){

        ProductEntity productEntity = new ProductEntity();
        productEntity.setBidEndDate(new Date());
        productEntity.setProductName("OR_111");
        Mockito.when(productRepository.findProductByProductId(Mockito.anyString())).thenReturn(productEntity);
        BuyerBidInfoWrapper buyerBidInfoWrapper = new BuyerBidInfoWrapper();
        BuyerBidInfo buyerBidInfo = new BuyerBidInfo();
        buyerBidInfo.setBidAmount(1000);
        buyerBidInfoWrapper.setBuyerBidInfoList(List.of(buyerBidInfo));
        Mockito.when(messageServiceClient.getBidInfo(Mockito.anyString())).thenReturn(buyerBidInfoWrapper);
        ProductBidInfo productBidInfo = sellerServiceImpl.showProductBids("OR_111");
        assertEquals("OR_111", productBidInfo.getProductInfo().getProductName());

    }

    @Test
    public void test_showProductBids_nullProduct(){
        Mockito.when(productRepository.findProductByProductId(Mockito.anyString())).thenReturn(null);
        assertThrows(CustomException.class,
                ()->sellerServiceImpl.showProductBids("OR_111"));
    }

    @Test
    public void test_showProductBids_emptyBids(){
        ProductEntity productEntity = new ProductEntity();
        productEntity.setBidEndDate(new Date());
        productEntity.setProductName("OR_111");
        Mockito.when(productRepository.findProductByProductId(Mockito.anyString())).thenReturn(productEntity);
        BuyerBidInfoWrapper buyerBidInfoWrapper = new BuyerBidInfoWrapper();
        Mockito.when(messageServiceClient.getBidInfo(Mockito.anyString())).thenReturn(buyerBidInfoWrapper);
        ProductBidInfo productBidInfo = sellerServiceImpl.showProductBids("OR_111");
        assertEquals("OR_111", productBidInfo.getProductInfo().getProductName());
        buyerBidInfoWrapper.setErrorMessage(SellerConstants.PRODUCT_BIDS_NOT_AVAILABLE_ERROR);
        assertThrows(CustomException.class,
                ()->sellerServiceImpl.showProductBids("OR_111"));
    }

}