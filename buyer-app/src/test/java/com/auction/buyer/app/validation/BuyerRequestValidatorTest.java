package com.auction.buyer.app.validation;

import com.auction.buyer.app.exception.CustomException;
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
public class BuyerRequestValidatorTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(BuyerRequestValidatorTest.class);

    @InjectMocks
    private BuyerRequestValidator buyerRequestValidator;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        LOGGER.info("setup before test method");
    }

    @Test
    public void test_validateBidInfo_Sucess() throws CustomException {
        BuyerBidInfo buyerBidInfo = new BuyerBidInfo();
        buyerBidInfo.setEmail("test@mail.com");
        buyerBidInfo.setBidAmount(1000);
        buyerBidInfo.setAddress("dummy");
        buyerBidInfo.setCity("dummy");
        buyerBidInfo.setFirstName("firstname");
        buyerBidInfo.setLastName("lastname");
        buyerBidInfo.setProductName("OR_111");
        buyerBidInfo.setPhoneNumber("1234567890");
        buyerBidInfo.setPinCode("123303");
        buyerRequestValidator.validateBidInfo(buyerBidInfo);
        assertEquals("test@mail.com", buyerBidInfo.getEmail());
    }

    @Test
    public void test_validateBidInfo_bidInfoNull() {
        assertThrows(CustomException.class,
                () -> buyerRequestValidator.validateBidInfo(null));
    }

    @Test
    public void test_validateBidInfo_firstNameValidations() {
        BuyerBidInfo buyerBidInfo = new BuyerBidInfo();
        assertThrows(CustomException.class,
                () -> buyerRequestValidator.validateBidInfo(buyerBidInfo));
        buyerBidInfo.setFirstName("fi");
        assertThrows(CustomException.class,
                () -> buyerRequestValidator.validateBidInfo(buyerBidInfo));
        buyerBidInfo.setFirstName("firstooooooooooooooooooooooooooo");
        assertThrows(CustomException.class,
                () -> buyerRequestValidator.validateBidInfo(buyerBidInfo));
    }

    @Test
    public void test_validateBidInfo_lastNameValidations() {
        BuyerBidInfo buyerBidInfo = new BuyerBidInfo();
        buyerBidInfo.setFirstName("first");
        assertThrows(CustomException.class,
                () -> buyerRequestValidator.validateBidInfo(buyerBidInfo));
        buyerBidInfo.setLastName("fi");
        assertThrows(CustomException.class,
                () -> buyerRequestValidator.validateBidInfo(buyerBidInfo));
        buyerBidInfo.setLastName("lastooooooooooooooooooooooooooo");
        assertThrows(CustomException.class,
                () -> buyerRequestValidator.validateBidInfo(buyerBidInfo));
    }

    @Test
    public void test_validateBidInfo_emailValidations() {
        BuyerBidInfo buyerBidInfo = new BuyerBidInfo();
        buyerBidInfo.setFirstName("firstName");
        buyerBidInfo.setLastName("lastname");
        assertThrows(CustomException.class,
                () -> buyerRequestValidator.validateBidInfo(buyerBidInfo));
        buyerBidInfo.setEmail("test");
        assertThrows(CustomException.class,
                () -> buyerRequestValidator.validateBidInfo(buyerBidInfo));
    }

    @Test
    public void test_validateBidInfo_phoneValidations() {
        BuyerBidInfo buyerBidInfo = new BuyerBidInfo();
        buyerBidInfo.setFirstName("firstName");
        buyerBidInfo.setLastName("lastname");
        buyerBidInfo.setEmail("test@mail.com");
        assertThrows(CustomException.class,
                () -> buyerRequestValidator.validateBidInfo(buyerBidInfo));
        buyerBidInfo.setPhoneNumber("12345678900");
        assertThrows(CustomException.class,
                () -> buyerRequestValidator.validateBidInfo(buyerBidInfo));
    }

    @Test
    public void test_validateUpdateBidRequest_InvalidBidAmount(){
        assertThrows(CustomException.class,
                () -> buyerRequestValidator.validateUpdateBidRequest("OR_111", "test@mail.com", "ABC"));
    }

    @Test
    public void test_validateUpdateBidRequest_InvalidProduct(){
        assertThrows(CustomException.class,
                () -> buyerRequestValidator.validateUpdateBidRequest(null, "test@mail.com", "1000"));

        assertThrows(CustomException.class,
                () -> buyerRequestValidator.validateUpdateBidRequest("ABC", "test@mail.com", "1000"));

        assertThrows(CustomException.class,
                () -> buyerRequestValidator.validateUpdateBidRequest("ABCDEFGHIJKLMNOPQRSTUVWXYZABCDEF", "test@mail.com", "1000"));
    }

    @Test
    public void test_validateUpdateBidRequest_InvalidEmail(){
        assertThrows(CustomException.class,
                () -> buyerRequestValidator.validateUpdateBidRequest("OR_111", null, "1000"));

        assertThrows(CustomException.class,
                () -> buyerRequestValidator.validateUpdateBidRequest("OR_111", "test", "1000"));
    }


}