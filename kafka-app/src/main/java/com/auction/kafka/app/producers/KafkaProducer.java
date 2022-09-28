package com.auction.kafka.app.producers;

import com.auction.kafka.app.model.BuyerBidInfo;
import com.auction.kafka.app.util.MethodStartEndLogger;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
@Component
public class KafkaProducer {

    @Value("${spring.kafka.bid.topic.name}")
    private String topicName;

    @Autowired
    private KafkaTemplate<String, BuyerBidInfo> kafkaTemplate;

    @MethodStartEndLogger
    public String send(String buyerEmail, BuyerBidInfo buyerBidInfo){
        log.info("sending product info to {}", topicName);
        kafkaTemplate.send(topicName, (Integer)null, buyerEmail , buyerBidInfo).addCallback(new ListenableFutureCallback<SendResult<String, BuyerBidInfo>>() {
            @Override
            public void onFailure(Throwable ex) {
                log.warn("can not publish bid info to topic {} {}", topicName, ex);
            }

            @Override
            public void onSuccess(SendResult<String, BuyerBidInfo> result) {
                ProducerRecord<String, BuyerBidInfo> producerRecord = result.getProducerRecord();
                log.info("Successfully published message to topic :: {} with key {} ",topicName, producerRecord.key());
            }
        });
        return "message produced successfully";
    }

}
