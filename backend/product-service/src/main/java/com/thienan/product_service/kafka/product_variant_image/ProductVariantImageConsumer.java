package com.thienan.product_service.kafka.product_variant_image;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.thienan.product_service.core.product_variant.service.ProductVariantImageService;
import com.thienan.product_service.kafka.file_info.FileInfo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductVariantImageConsumer {
    private final ProductVariantImageService productVariantImageService;

    @KafkaListener(
        groupId="${app.kafka.group-id.product-variant-image}", 
        topics={"${app.kafka.topics.product-variant-image.created-product-variant-image}"})
    public void consumeCreatedProductVariantImage(
        FileInfo fileInfo
    ){
        log.info("consume file info: ", fileInfo.toString());
        productVariantImageService.updateFileInfoByKey(fileInfo);
    }
}
