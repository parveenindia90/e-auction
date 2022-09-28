package com.auction.seller.app.resource;

import com.auction.seller.app.exception.CustomException;
import com.auction.seller.app.model.ProductInfo;
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

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductResourceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(SellerResourceTest.class);

    @InjectMocks
    private ProductResource productResource;

    @Mock
    private SellerService sellerService;

    @Mock
    private SellerRequestValidator sellerRequestValidator;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        LOGGER.info("setup before test method");
        ReflectionTestUtils.setField(productResource, "sellerRequestValidator", sellerRequestValidator);
        ReflectionTestUtils.setField(productResource, "sellerService", sellerService);
    }

    @Test
    public void test_retrieveProductInfo_Success() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductName("OR_111");
        Mockito.when(sellerService.retreiveProductInfo(Mockito.anyString())).thenReturn(productInfo);
        ResponseEntity<ProductInfo> productInfo1 = productResource.getPrductInfo("OR_111");
        assertEquals("OR_111", productInfo1.getBody().getProductName());
    }

    @Test
    public void test_retrieveProductInfo_Failure() {
        Mockito.when(sellerService.retreiveProductInfo(Mockito.anyString())).thenThrow(CustomException.class);
        assertThrows(CustomException.class,
                () -> productResource.getPrductInfo("OR_111"));
    }

}