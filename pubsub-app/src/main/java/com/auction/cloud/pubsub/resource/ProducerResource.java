package com.auction.cloud.pubsub.resource;

import com.auction.cloud.pubsub.model.ApiResponse;
import com.auction.cloud.pubsub.model.BuyerBidInfo;
import com.auction.cloud.pubsub.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/e-auction/api/v1/producer")
public class ProducerResource {

    @Autowired
    private MessageService messageService;

    @PostMapping("bid/produce")
    public ResponseEntity<ApiResponse> produceMessage(@RequestBody BuyerBidInfo buyerBidInfo) {
        String message = messageService.produceMessage(buyerBidInfo);
        return new ResponseEntity<>(new ApiResponse(message, true), HttpStatus.OK);
    }

}
