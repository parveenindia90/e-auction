package com.auction.buyer.app.clients;

import com.auction.buyer.app.model.BuyerBidInfo;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class SellerServiceFallBackTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(SellerServiceFallBackTest.class);

    @InjectMocks
    private SellerServiceFallBack sellerServiceFallBack;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        LOGGER.info("setup before test method");
    }

    @Test
    public void test_getBidInfo_success(){
        sellerServiceFallBack.getProductInfo("OR_111");
    }

}