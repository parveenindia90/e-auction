package com.auction.seller.app.validation;

import com.auction.seller.app.exception.CustomException;
import com.auction.seller.app.model.ProductInfo;
import com.auction.seller.app.model.SellerInfo;
import com.auction.seller.app.model.SellerProductWrapper;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class SellerRequestValidatorTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(SellerRequestValidatorTest.class);

    @InjectMocks
    private SellerRequestValidator sellerRequestValidator;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        LOGGER.info("setup before test method");
    }

    @Test
    public void test_validateAddProductInfo_success(){
        SellerProductWrapper sellerProductWrapper = new SellerProductWrapper();
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductName("OR_111");
        productInfo.setCategory("Ornament");
        productInfo.setBidEndDate(new Date());
        productInfo.setShortDesc("asdadasd");
        productInfo.setDetailsDesc("asdsfsdgsdg");
        productInfo.setStartingPrice("1000");
        sellerProductWrapper.setProductInfo(productInfo);
        SellerInfo sellerInfo = new SellerInfo();
        sellerInfo.setEmail("test@mail.com");
        sellerInfo.setAddress("dummy");
        sellerInfo.setFirstName("firstName");
        sellerInfo.setLastName("lastName");
        sellerInfo.setCity("dummy");
        sellerInfo.setPhoneNumber("1234567890");
        sellerProductWrapper.setSellerInfo(sellerInfo);
        ReflectionTestUtils.setField(sellerRequestValidator, "productCategories", List.of("Ornament"));
        sellerRequestValidator.validateAddProductInfo(sellerProductWrapper);
        assertEquals("OR_111", productInfo.getProductName());
    }

    @Test
    public void validateProductInfo_null() {
        SellerProductWrapper sellerProductWrapper = new SellerProductWrapper();
        assertThrows(CustomException.class,
                () -> sellerRequestValidator.validateAddProductInfo(sellerProductWrapper));
    }

    @Test
    public void validateProductInfo_productCategory() {
        SellerProductWrapper sellerProductWrapper = new SellerProductWrapper();
        ReflectionTestUtils.setField(sellerRequestValidator, "productCategories", List.of("Ornament"));
        ProductInfo productInfo = new ProductInfo();
        productInfo.setCategory("ABC");
        sellerProductWrapper.setProductInfo(productInfo);
        assertThrows(CustomException.class,
                () -> sellerRequestValidator.validateAddProductInfo(sellerProductWrapper));
    }

    @Test
    public void validateProductInfo_bidPrice() {
        SellerProductWrapper sellerProductWrapper = new SellerProductWrapper();
        ReflectionTestUtils.setField(sellerRequestValidator, "productCategories", List.of("Ornament"));
        ProductInfo productInfo = new ProductInfo();
        productInfo.setCategory("Ornament");
        productInfo.setStartingPrice("ABC");
        sellerProductWrapper.setProductInfo(productInfo);
        assertThrows(CustomException.class,
                () -> sellerRequestValidator.validateAddProductInfo(sellerProductWrapper));
    }

    @Test
    public void validateProductInfo_bidEndDate() {
        SellerProductWrapper sellerProductWrapper = new SellerProductWrapper();
        ReflectionTestUtils.setField(sellerRequestValidator, "productCategories", List.of("Ornament"));
        ProductInfo productInfo = new ProductInfo();
        productInfo.setCategory("Ornament");
        productInfo.setStartingPrice("1000");
        productInfo.setBidEndDate(new Date("19/12/1999"));
        sellerProductWrapper.setProductInfo(productInfo);
        assertThrows(CustomException.class,
                () -> sellerRequestValidator.validateAddProductInfo(sellerProductWrapper));
    }

    @Test
    public void validateProductInfo_productName() {
        SellerProductWrapper sellerProductWrapper = new SellerProductWrapper();
        ReflectionTestUtils.setField(sellerRequestValidator, "productCategories", List.of("Ornament"));
        ProductInfo productInfo = new ProductInfo();
        productInfo.setCategory("Ornament");
        productInfo.setStartingPrice("1000");
        productInfo.setBidEndDate(new Date("19/12/2099"));
        sellerProductWrapper.setProductInfo(productInfo);
        assertThrows(CustomException.class,
                () -> sellerRequestValidator.validateAddProductInfo(sellerProductWrapper));
        productInfo.setProductName("OR");
        assertThrows(CustomException.class,
                () -> sellerRequestValidator.validateAddProductInfo(sellerProductWrapper));
        productInfo.setProductName("ORDBHFGGFGHFGGFGFKGKGFDKHDKHDKJGKJFGKJGFJKGFKJGFKJGKFJGKF");
        assertThrows(CustomException.class,
                () -> sellerRequestValidator.validateAddProductInfo(sellerProductWrapper));
    }

    @Test
    public void validateSellerInfo_null() {
        assertThrows(CustomException.class,
                () -> sellerRequestValidator.validateSellerInformation(null));
    }

    @Test
    public void validateSellerInfo_firstNameValidations() {
        SellerInfo sellerInfo = new SellerInfo();
        assertThrows(CustomException.class,
                () -> sellerRequestValidator.validateSellerInformation(sellerInfo));
        sellerInfo.setFirstName("fi");
        assertThrows(CustomException.class,
                () -> sellerRequestValidator.validateSellerInformation(sellerInfo));
        sellerInfo.setFirstName("firstooooooooooooooooooooooooooo");
        assertThrows(CustomException.class,
                () -> sellerRequestValidator.validateSellerInformation(sellerInfo));
    }

    @Test
    public void validateBidInfo_lastNameValidations() {
        SellerInfo sellerInfo = new SellerInfo();
        sellerInfo.setFirstName("first");
        assertThrows(CustomException.class,
                () -> sellerRequestValidator.validateSellerInformation(sellerInfo));
        sellerInfo.setLastName("fi");
        assertThrows(CustomException.class,
                () -> sellerRequestValidator.validateSellerInformation(sellerInfo));
        sellerInfo.setLastName("lastooooooooooooooooooooooooooo");
        assertThrows(CustomException.class,
                () -> sellerRequestValidator.validateSellerInformation(sellerInfo));
    }

    @Test
    public void validateBidInfo_emailValidations() {
        SellerInfo sellerInfo = new SellerInfo();
        sellerInfo.setFirstName("firstName");
        sellerInfo.setLastName("lastname");
        assertThrows(CustomException.class,
                () -> sellerRequestValidator.validateSellerInformation(sellerInfo));
        sellerInfo.setEmail("test");
        assertThrows(CustomException.class,
                () -> sellerRequestValidator.validateSellerInformation(sellerInfo));
    }

    @Test
    public void validateBidInfo_phoneValidations() {
        SellerInfo sellerInfo = new SellerInfo();
        sellerInfo.setFirstName("firstName");
        sellerInfo.setLastName("lastname");
        sellerInfo.setEmail("test@mail.com");
        assertThrows(CustomException.class,
                () -> sellerRequestValidator.validateSellerInformation(sellerInfo));
        sellerInfo.setPhoneNumber("12345678900");
        assertThrows(CustomException.class,
                () -> sellerRequestValidator.validateSellerInformation(sellerInfo));
    }




}