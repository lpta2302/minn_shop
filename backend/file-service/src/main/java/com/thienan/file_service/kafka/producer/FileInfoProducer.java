package com.thienan.file_service.kafka.producer;

import static java.lang.String.format;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import static org.springframework.kafka.support.KafkaHeaders.TOPIC;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.thienan.file_service.core.entity.FileInfo;
import com.thienan.file_service.core.enumeration.ObjectTypes;
import static com.thienan.file_service.core.enumeration.ObjectTypes.PRODUCT_VARIANT;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
@RequiredArgsConstructor
public class FileInfoProducer {
    @Value("${kafka.topics.created-product-variant-image}")
    private String productVariantImageTopicName;
    private final Map<ObjectTypes, String> topicMap = new HashMap<>();
    
    @PostConstruct
    public void initAfterConstructor(){
        topicMap.put(PRODUCT_VARIANT, productVariantImageTopicName);
    }

    private final KafkaTemplate<String, FileInfo> kafkaTemplate;

    public void sendCreatedFileInfoMessage(FileInfo fileInfo, ObjectTypes objectType){
        log.info(productVariantImageTopicName);
        String topicName = topicMap.get(objectType);
        log.info(format("sending created file info: %s to topic: %s", fileInfo.toString(), topicName));

        Message<FileInfo> message = MessageBuilder
            .withPayload(fileInfo)
            .setHeader(TOPIC, topicName)
            .build();

        kafkaTemplate.send(message);
    }
}
