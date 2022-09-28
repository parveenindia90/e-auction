package com.auction.seller.app.resource;

import com.auction.seller.app.exception.CustomException;
import com.auction.seller.app.model.BuyerBidInfo;
import com.auction.seller.app.model.ProductBidInfo;
import com.auction.seller.app.model.SellerProductWrapper;
import com.auction.seller.app.service.SellerService;
import com.auction.seller.app.validation.SellerRequestValidator;
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
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class SellerResourceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(SellerResourceTest.class);

    @InjectMocks
    private SellerResource sellerResource;

    @Mock
    private SellerService sellerService;

    @Mock
    private SellerRequestValidator sellerRequestValidator;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        LOGGER.info("setup before test method");
        ReflectionTestUtils.setField(sellerResource, "sellerRequestValidator", sellerRequestValidator);
        ReflectionTestUtils.setField(sellerResource, "sellerService", sellerService);
    }

    @Test
    public void test_addNewProduct_Success() {
        SellerProductWrapper sellerProductWrapper = new SellerProductWrapper();
        Mockito.when(sellerService.addNewProducts(Mockito.any())).thenReturn("success");
        assertEquals("success", sellerResource.addProduct(sellerProductWrapper).getBody().getMessage());
    }

    @Test
    public void test_addNewProduct_Failure() {
        SellerProductWrapper sellerProductWrapper = new SellerProductWrapper();
        Mockito.when(sellerService.addNewProducts(Mockito.any())).thenThrow(CustomException.class);
        assertThrows(CustomException.class,
                () -> sellerResource.addProduct(sellerProductWrapper));
    }

    @Test
    public void test_deleteProductProduct_Success() {
        Mockito.when(sellerService.deleteProduct(Mockito.anyString())).thenReturn("success");
        assertEquals("success", sellerResource.deleteProduct("OR_111").getBody().getMessage());
    }

    @Test
    public void test_deleteProduct_Failure() {
        Mockito.when(sellerService.deleteProduct(Mockito.anyString())).thenThrow(CustomException.class);
        assertThrows(CustomException.class,
                () -> sellerResource.deleteProduct("OR_111"));
    }

    @Test
    public void test_showProductBids_Success() {
        ProductBidInfo productBidInfo = new ProductBidInfo();
        productBidInfo.setBuyerBidInfoList(List.of(new BuyerBidInfo()));
        Mockito.when(sellerService.showProductBids(Mockito.anyString())).thenReturn(productBidInfo);
        ResponseEntity<ProductBidInfo> productBidInfo1 = sellerResource.showProductBids("OR_111");
        assertEquals( 1, productBidInfo1.getBody().getBuyerBidInfoList().size());
    }

    @Test
    public void test_showProductBids_Failure() {
        Mockito.when(sellerService.showProductBids(Mockito.anyString())).thenThrow(CustomException.class);
        assertThrows(CustomException.class,
                () -> sellerResource.showProductBids("OR_111"));
    }
}