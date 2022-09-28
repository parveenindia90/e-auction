package com.auction.cloud.pubsub.service;

import com.auction.cloud.pubsub.model.BuyerBidInfo;
import com.auction.cloud.pubsub.model.BuyerBidInfoWrapper;
import com.auction.cloud.pubsub.repository.BuyerBidInfoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MessageServiceImpl implements MessageService{

    private static final ObjectMapper OBJECT_MAPPER = Jackson2ObjectMapperBuilder.json().build();

    @Autowired
    private BuyerBidInfoRepository buyerBidInfoRepository;

    @Autowired
    private QueueMessagingTemplate queueMessagingTemplate;

    @Value("${cloud.aws.end-point.uri}")
    private String endpoint;

    @Override
    public String produceMessage(BuyerBidInfo buyerBidInfo) {
        queueMessagingTemplate.convertAndSend(endpoint, buyerBidInfo);
        //output.send(MessageBuilder.withPayload(buyerBidInfo).build());
        return "Message published successfully";
    }

    @SqsListener(value="bid-stream", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void loadMessageFromSQS(String message)  {
        try {
            log.info("Message received :: {}",message);
            BuyerBidInfo buyerBidInfo = OBJECT_MAPPER.readValue(message, BuyerBidInfo.class);
            buyerBidInfoRepository.saveBidInfo(buyerBidInfo);
        } catch (Exception e) {
            throw new RuntimeException("Cannot process message from SQS", e);
        }
    }
    @Override
    public BuyerBidInfoWrapper getProductBids(String productId) {
        BuyerBidInfoWrapper buyerBidInfoWrapper = new BuyerBidInfoWrapper();
        buyerBidInfoWrapper.setBuyerBidInfoList(buyerBidInfoRepository.findByProductName(productId));
        return buyerBidInfoWrapper;
    }



}
