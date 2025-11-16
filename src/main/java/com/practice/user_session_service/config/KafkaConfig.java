package com.practice.user_session_service.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ContainerProperties;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConfig {

    @Bean
    public ProducerFactory<String,String> producerFactory(){
        Map<String, Object> props=new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9093");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        ProducerFactory<String, String> objectObjectDefaultKafkaProducerFactory = new DefaultKafkaProducerFactory<>(props);
        return objectObjectDefaultKafkaProducerFactory;



    }

    @Bean
    public KafkaTemplate<String, String > kafkaTemplate(){
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String ,String> kafkaListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory factory=new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(new DefaultKafkaConsumerFactory(Map.of(
                "bootstrap.servers","localhost:9093",
                "group.id", "user-preferences",
                "auto-offset-reset-config", "earliest",
                "key.deserializer","org.apache.kafka.common.serialization.StringDeserializer",
                "value.deserializer","org.apache.kafka.common.serialization.StringDeserializer"

        )));
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        return factory;
    }


}
