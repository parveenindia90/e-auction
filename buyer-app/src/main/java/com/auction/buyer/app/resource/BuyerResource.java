package com.auction.buyer.app.resource;

import com.auction.buyer.app.exception.CustomException;
import com.auction.buyer.app.model.ApiResponse;
import com.auction.buyer.app.model.BuyerBidInfo;
import com.auction.buyer.app.service.BuyerService;
import com.auction.buyer.app.service.BuyerServiceImpl;
import com.auction.buyer.app.util.MethodStartEndLogger;
import com.auction.buyer.app.validation.BuyerRequestValidator;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/e-auction/api/v1/buyer")
public class BuyerResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(BuyerResource.class);

    @Autowired
    private BuyerRequestValidator buyerRequestValidator;

    @Autowired
    private BuyerService buyerService;

    /**
     * This methiod is used to place new bid by buyer
     */
    @ApiOperation(
            value = "Buyer App",
            notes = "Add new bid",
            response = String.class
    )
    @PostMapping("place-bid")
    @MethodStartEndLogger
    public ResponseEntity<ApiResponse> placeBid(@ApiParam @RequestBody BuyerBidInfo buyerBidInfo) throws CustomException {
        LOGGER.info("BuyerResource :: placeBid Placing new bid for buyer :: {} ",buyerBidInfo.getEmail());
        buyerRequestValidator.validateBidInfo(buyerBidInfo);
        String message = buyerService.placeNewBid(buyerBidInfo);
        return ResponseEntity.ok(new ApiResponse(true, message));
    }

    /**
     * This methiod is used to delete product by the seller
     */
    @ApiOperation(
            value = "Buyer App",
            notes = "Update Bid",
            response = String.class
    )
    @PutMapping("update-bid/{productId}/{buyerEmailId}/{newBidAmount}")
    @MethodStartEndLogger
    public ResponseEntity<ApiResponse> updateBid(@ApiParam(value="Product Id") @PathVariable("productId") String productId,
                                                 @ApiParam(value="Buyer Email Id") @PathVariable("buyerEmailId") String buyerEmailId,
                                                 @ApiParam(value="New Bid Amount") @PathVariable("newBidAmount") String bidAmount) throws CustomException{
        LOGGER.info("BuyerResource :: updateBid updating bid amount for buyer :: {} ", buyerEmailId);
        buyerRequestValidator.validateUpdateBidRequest(productId, buyerEmailId, bidAmount);
        String message = buyerService.updateBidAmount(productId, buyerEmailId, bidAmount);
        return ResponseEntity.ok(new ApiResponse(true, message));
    }

}
