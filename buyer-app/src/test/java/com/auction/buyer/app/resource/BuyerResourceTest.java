package com.auction.buyer.app.resource;

import com.auction.buyer.app.exception.CustomException;
import com.auction.buyer.app.model.BuyerBidInfo;
import com.auction.buyer.app.service.BuyerService;
import com.auction.buyer.app.validation.BuyerRequestValidator;
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

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class BuyerResourceTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(BuyerResourceTest.class);

    @InjectMocks
    private BuyerResource buyerResource;

    @Mock
    private BuyerService buyerService;

    @Mock
    private BuyerRequestValidator buyerRequestValidator;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        LOGGER.info("setup before test method");
        ReflectionTestUtils.setField(buyerResource, "buyerRequestValidator", buyerRequestValidator);
        ReflectionTestUtils.setField(buyerResource, "buyerService", buyerService);
    }

    @Test
    public void test_placeNewBid_Success() throws CustomException {
        BuyerBidInfo buyerBidInfo = new BuyerBidInfo();
        Mockito.when(buyerService.placeNewBid(buyerBidInfo)).thenReturn("success");
        assertEquals("success", buyerResource.placeBid(buyerBidInfo).getBody().getMessage());
    }

    @Test
    public void test_placeNewBid_Failure() throws CustomException {
        BuyerBidInfo buyerBidInfo = new BuyerBidInfo();
        Mockito.when(buyerService.placeNewBid(buyerBidInfo)).thenThrow(CustomException.class);
        assertThrows(CustomException.class,
                () -> buyerResource.placeBid(buyerBidInfo));
    }

    @Test
    public void test_updateBid_Success() throws CustomException {
        Mockito.when(buyerService.updateBidAmount("OR_111","test@mail.com", "1000")).thenReturn("success");
        assertEquals("success", buyerResource.updateBid("OR_111","test@mail.com", "1000").getBody().getMessage());
    }

    @Test
    public void test_updateBid_Failure() throws CustomException {
        Mockito.when(buyerService.updateBidAmount("OR_111","test@mail.com", "1000")).thenThrow(CustomException.class);
        assertThrows(CustomException.class,
                () -> buyerResource.updateBid("OR_111","test@mail.com", "1000"));
    }

}