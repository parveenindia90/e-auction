package com.auction.buyer.app.service;

import com.auction.buyer.app.clients.MessageServiceClient;
import com.auction.buyer.app.clients.SellerServiceClient;
import com.auction.buyer.app.exception.CustomException;
import com.auction.buyer.app.model.BuyerBidInfo;
import com.auction.buyer.app.model.BuyerBidInfoWrapper;
import com.auction.buyer.app.model.ProductInfo;
import com.auction.buyer.app.util.BuyerConstants;
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
public class BuyerServiceImplTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(BuyerServiceImplTest.class);
    @InjectMocks
    private BuyerServiceImpl buyerServiceimpl;

    @Mock
    private SellerServiceClient sellerServiceClient;

    @Mock
    private MessageServiceClient messageServiceClient;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        LOGGER.info("setup before test method");
        ReflectionTestUtils.setField(buyerServiceimpl, "sellerServiceClient", sellerServiceClient);
        ReflectionTestUtils.setField(buyerServiceimpl, "messageServiceClient", messageServiceClient);
    }
    
    @Test
    public void test_placeNewBid_Success() throws CustomException {
        BuyerBidInfo buyerBidInfo = new BuyerBidInfo();
        buyerBidInfo.setProductName("OR_111");
        buyerBidInfo.setBidAmount(1000.0);
        buyerBidInfo.setEmail("test@mail.com");
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductName("OR_111");
        productInfo.setBidEndDate(new Date("12/12/2099"));
        Mockito.when(sellerServiceClient.getProductInfo(Mockito.anyString())).thenReturn(productInfo);
        Mockito.when(messageServiceClient.getBidInfo(Mockito.anyString())).thenReturn(new BuyerBidInfoWrapper());
        Mockito.when(messageServiceClient.produceMessage(Mockito.any())).thenReturn("message produced successfully");
        assertEquals("Bid placed successfully",buyerServiceimpl.placeNewBid(buyerBidInfo));
    }

    @Test
    public void test_placeNewBid_productValidations() {
        BuyerBidInfo buyerBidInfo = new BuyerBidInfo();
        buyerBidInfo.setProductName("OR_111");
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductName("OR_113");
        Mockito.when(sellerServiceClient.getProductInfo(Mockito.anyString())).thenReturn(productInfo);
        assertThrows(CustomException.class,
                ()->buyerServiceimpl.placeNewBid(buyerBidInfo));
        productInfo.setProductName("OR_111");
        productInfo.setBidEndDate(new Date("12/12/2021"));
        assertThrows(CustomException.class,
                ()->buyerServiceimpl.placeNewBid(buyerBidInfo));
        productInfo.setErrorMessage(BuyerConstants.PRODUCT_FOR_BID_NOT_AVAILABLE);
        assertThrows(CustomException.class,
                ()->buyerServiceimpl.placeNewBid(buyerBidInfo));
    }

    @Test
    public void test_placeNewBid_bidValidations() {
        BuyerBidInfo buyerBidInfo = new BuyerBidInfo();
        buyerBidInfo.setProductName("OR_111");
        buyerBidInfo.setBidAmount(1000.0);
        buyerBidInfo.setEmail("test@mail.com");
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductName("OR_111");
        productInfo.setBidEndDate(new Date("12/12/2099"));
        BuyerBidInfoWrapper buyerBidInfoWrapper = new BuyerBidInfoWrapper();
        buyerBidInfoWrapper.setBuyerBidInfoList(List.of(buyerBidInfo));
        Mockito.when(sellerServiceClient.getProductInfo(Mockito.anyString())).thenReturn(productInfo);
        Mockito.when(messageServiceClient.getBidInfo(Mockito.anyString())).thenReturn(buyerBidInfoWrapper);
        assertThrows(CustomException.class,
                ()->buyerServiceimpl.placeNewBid(buyerBidInfo));
        buyerBidInfoWrapper.setErrorMessage(BuyerConstants.UNABLE_TO_FETCH_BIDS);
        assertThrows(CustomException.class,
                ()->buyerServiceimpl.placeNewBid(buyerBidInfo));
    }

    @Test
    public void test_updateNewBid_Success() throws CustomException {
        BuyerBidInfo buyerBidInfo = new BuyerBidInfo();
        buyerBidInfo.setEmail("test@mail.com");
        BuyerBidInfoWrapper buyerBidInfoWrapper = new BuyerBidInfoWrapper();
        buyerBidInfoWrapper.setBuyerBidInfoList(List.of(buyerBidInfo));
        Mockito.when(messageServiceClient.getBidInfo(Mockito.anyString())).thenReturn(buyerBidInfoWrapper);
        Mockito.when(messageServiceClient.produceMessage(Mockito.any())).thenReturn("message produced successfully");
        assertEquals("bid updated successfully",buyerServiceimpl.updateBidAmount("OR_111","test@mail.com","1000"));
    }

    @Test
    public void test_updateNewBid_bidValidations() {
        BuyerBidInfoWrapper buyerBidInfoWrapper = new BuyerBidInfoWrapper();
        Mockito.when(messageServiceClient.getBidInfo(Mockito.anyString())).thenReturn(buyerBidInfoWrapper);
        assertThrows(CustomException.class,
                ()->buyerServiceimpl.updateBidAmount("OR_111","test@mail.com","1000"));
    }
}