package com.auction.kafka.app.streams;

import com.auction.kafka.app.model.BuyerBidInfo;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.state.KeyValueBytesStoreSupplier;
import org.apache.kafka.streams.state.Stores;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaStreams;

@Configuration
@EnableKafkaStreams
public class StreamTopologyConfig {

    final Serde<String> stringSerds =  Serdes.String();

    final Serde<BuyerBidInfo> bidSerds = buyerBidInfoSerde();

    @Bean
    public Topology topology(@Value("${spring.kafka.bid.topic.name}") String topicName, @Value("${spring.kafka.bid.store.name}") String storeName,
                              @Autowired StreamsBuilder streamsBuilder){
        KeyValueBytesStoreSupplier storeSupplier = Stores.inMemoryKeyValueStore(storeName);
        streamsBuilder.table(topicName, Materialized.<String, BuyerBidInfo> as(storeSupplier)
                .withKeySerde(stringSerds)
                .withValueSerde(bidSerds));
        return streamsBuilder.build();
    }

    public static Serde<BuyerBidInfo> buyerBidInfoSerde() {
        JsonSerializer<BuyerBidInfo> serializer = new JsonSerializer<>();
        JsonDeserializer<BuyerBidInfo> deserializer = new JsonDeserializer<>(BuyerBidInfo.class);
        return Serdes.serdeFrom(serializer, deserializer);
    }


}
