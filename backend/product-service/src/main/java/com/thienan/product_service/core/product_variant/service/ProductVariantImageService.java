package com.thienan.product_service.core.product_variant.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.thienan.product_service.core.product_variant.dto.ProductVariantImageRequest;
import com.thienan.product_service.core.product_variant.entity.ProductVariantImage;
import com.thienan.product_service.core.product_variant.repository.ProductVariantImageRepository;
import com.thienan.product_service.handler.exceptions.common.EntityNotFoundByIDException;
import com.thienan.product_service.kafka.file_info.FileInfo;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductVariantImageService {
    private final ProductVariantImageRepository productVariantImageRepository;
    
    @Transactional
    public Long updateFileInfoByKey(FileInfo fileInfo) {
        var productVariantImage = 
            productVariantImageRepository.findByKey(fileInfo.key())
            .orElse(null);
        if (productVariantImage == null) {
            return null;
        }

        productVariantImage.setFileId(fileInfo.id());
        productVariantImage.setUrl(fileInfo.objectUrl());
        productVariantImageRepository.save(productVariantImage);

        return productVariantImage.getId();
    }

    public ProductVariantImage createProductVariantImage(
        ProductVariantImageRequest request
    ){
        return ProductVariantImage
                        .builder()
                        .name(request.name())
                        .key(request.key())
                        .isThumbnail(request.isThumbnail())
                        .position(request.position())
                        .build();
    }
    
    public List<ProductVariantImage> createProductVariantImages(
            List<ProductVariantImageRequest> productVariantImageRequests) {
            return productVariantImageRequests
                .stream().map(this::createProductVariantImage).toList();
    }

    public List<ProductVariantImage> updateInProductVariant(
            List<ProductVariantImage> oldProductVariantImages,
            List<ProductVariantImageRequest> productVariantImageRequests) {
        return productVariantImageRequests
            .stream()
            .map(req->{ 
                if (req.id() == null) {
                    return createProductVariantImage(req);
                } else {
                    ProductVariantImage updatedProductVariantImage = oldProductVariantImages
                        .stream()
                        .filter(image->image.getId().equals(req.id()))
                        .findFirst()
                        .orElseThrow(()->new EntityNotFoundByIDException("Product Variant Image", req.id().toString()));
                    updatedProductVariantImage.setPosition(req.position());
                    updatedProductVariantImage.setThumbnail(req.isThumbnail());
                    return null;   
                }
            }).toList();
        
    }
}
