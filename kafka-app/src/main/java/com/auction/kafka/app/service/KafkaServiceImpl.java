package com.auction.kafka.app.service;

import com.auction.kafka.app.model.BuyerBidInfo;
import com.auction.kafka.app.model.BuyerBidInfoWrapper;
import com.auction.kafka.app.producers.KafkaProducer;
import com.auction.kafka.app.util.MethodStartEndLogger;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class KafkaServiceImpl implements KafkaService {

    @Value("${spring.kafka.bid.store.name}")
    private String bidStoreName;

    @Autowired
    private StreamsBuilderFactoryBean streamsBuilderFactoryBean;

    @Autowired
    private KafkaProducer kafkaProducer;

    @Override
    @MethodStartEndLogger
    public BuyerBidInfoWrapper getProductBids(String productId){
        final ReadOnlyKeyValueStore<String, BuyerBidInfo> store = streamsBuilderFactoryBean.getKafkaStreams()
                .store(StoreQueryParameters.fromNameAndType(bidStoreName, QueryableStoreTypes.keyValueStore()));
        List<BuyerBidInfo> buyerBidInfoList = new ArrayList<>();
        BuyerBidInfoWrapper buyerBidInfoWrapper =  new BuyerBidInfoWrapper();
        KafkaStreams kafkaStreams = streamsBuilderFactoryBean.getKafkaStreams();
        if(null != store && kafkaStreams.state().equals(KafkaStreams.State.RUNNING)){
            KeyValueIterator<String,BuyerBidInfo> keyValueIterator = store.all();
            while(keyValueIterator.hasNext()){
                final KeyValue<String,BuyerBidInfo> keyValue = keyValueIterator.next();
                if(productId.equals(keyValue.value.getProductName())){
                    buyerBidInfoList.add(keyValue.value);
                }
            }
            buyerBidInfoWrapper.setBuyerBidInfoList(buyerBidInfoList);
        }else{
            buyerBidInfoWrapper.setErrorMessage("Cannot get state store because the stream thread is not RUNNING");
        }
        return buyerBidInfoWrapper;
    }

    @Override
    @MethodStartEndLogger
    public String produceMessage(String buyerEmail, BuyerBidInfo buyerBidInfo){
        return kafkaProducer.send(buyerEmail, buyerBidInfo);
    }

}
