package com.auction.seller.app.resource;

import com.auction.seller.app.exception.CustomException;
import com.auction.seller.app.model.ApiResponse;
import com.auction.seller.app.model.ProductBidInfo;
import com.auction.seller.app.service.SellerService;
import com.auction.seller.app.util.MethodStartEndLogger;
import com.auction.seller.app.validation.SellerRequestValidator;
import com.auction.seller.app.model.SellerProductWrapper;
import com.auction.seller.app.service.SellerServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.PathParam;
import java.util.List;

@RestController
@RequestMapping("/e-auction/api/v1/seller")
@CrossOrigin(origins = "*")
public class SellerResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(SellerResource.class);

    @Autowired
    private SellerRequestValidator sellerRequestValidator;

    @Autowired
    private SellerService sellerService;

    /**
     * This methiod will be used to add a new product by the seller
     */
    @ApiOperation(
            value = "Seller App",
            notes = "Add new product",
            response = String.class
    )
    @PostMapping("add-product")
    @MethodStartEndLogger
    public ResponseEntity<ApiResponse> addProduct(@ApiParam @RequestBody SellerProductWrapper sellerProductWrapper) throws CustomException{
        sellerRequestValidator.validateAddProductInfo(sellerProductWrapper);
        String message = sellerService.addNewProducts(sellerProductWrapper);
        return ResponseEntity.ok(new ApiResponse(true, message));
    }

    /**
     * This methiod is used to delete product by the seller
     */
    @ApiOperation(
            value = "Seller App",
            notes = "Delete product",
            response = String.class
    )
    @DeleteMapping("delete/{productId}")
    @MethodStartEndLogger
    public ResponseEntity<ApiResponse> deleteProduct(@ApiParam(value="Product Id") @PathVariable("productId") String productId) throws CustomException{
        String message = sellerService.deleteProduct(productId);
        return ResponseEntity.ok(new ApiResponse(true, message));
    }

    /**
     * This methiod is used to delete product by the seller
     */
    @ApiOperation(
            value = "Seller App",
            notes = "Listing all bids",
            response = String.class
    )
    @GetMapping("show-bids/{productId}")
    @MethodStartEndLogger
    public ResponseEntity<ProductBidInfo> showProductBids(@ApiParam(value="Product Id") @PathVariable("productId") String productId) throws CustomException {
        return ResponseEntity.ok(sellerService.showProductBids(productId));
    }

    @GetMapping("/getAllProducts")
    @MethodStartEndLogger
    public ResponseEntity<List<String>> getAllProducts(@PathParam("sellerEmail") String sellerEmail){
        return ResponseEntity.ok(sellerService.getAllProducts(sellerEmail));
    }

}
