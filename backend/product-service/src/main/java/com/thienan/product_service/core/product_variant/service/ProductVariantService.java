package com.thienan.product_service.core.product_variant.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.thienan.product_service.common.PageResponse;
import com.thienan.product_service.core.product_variant.dto.ProductVariantRequest;
import com.thienan.product_service.core.product_variant.dto.ProductVariantResponse;
import com.thienan.product_service.core.product_variant.entity.ProductOption;
import com.thienan.product_service.core.product_variant.entity.ProductVariant;
import com.thienan.product_service.core.product_variant.entity.ProductVariantImage;
import com.thienan.product_service.core.product_variant.mapper.ProductVariantMapper;
import com.thienan.product_service.core.product_variant.repository.ProductVariantRepository;
import com.thienan.product_service.core.product_variant.validator.ProductVariantValidatorSteps;
import com.thienan.product_service.handler.exceptions.common.EntityNotFoundByIDException;
import com.thienan.product_service.pipelines.validator.ValidatorPipeline;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductVariantService {
    private final ProductVariantRepository productVariantRepository;
    private final ProductVariantMapper productVariantMapper;
    private final ProductOptionService productOptionService;
    private final ProductVariantValidatorSteps productVariantValidatorSteps;
    private final ProductVariantImageService productVariantImageService;

    public ProductVariant createProductVariant(ProductVariantRequest productVariantRequest, Long productId){
        ValidatorPipeline<ProductVariantRequest> validatorPipeline =
            productVariantValidatorSteps.getProductVariantRequestValidator(productId);

        validatorPipeline.validate(productVariantRequest);
        
        ProductVariant productVariant = productVariantMapper.convertProductVariant(productVariantRequest);
        updateProductOption(productVariant, productVariantRequest.productOptionName());
        List<ProductVariantImage> images = 
            productVariantImageService.createProductVariantImages(
                productVariantRequest.productVariantImageRequests());
        
        productVariant.setImages(images);

        return productVariant;
    }

    // TODO: NEED VALIDATE PRODUCT VARIANT
    public List<ProductVariant> createProductVariants(List<ProductVariantRequest> productVariantRequests) {
        Map<String, ProductOption> options = new HashMap<>();
        productOptionService
            .findAllByName(
                productVariantRequests.stream().map(ProductVariantRequest::productOptionName).toList())
            .forEach(productOption -> options.put(productOption.getName(), productOption));

        return productVariantRequests.stream()
            .map(productVariantRequest -> {
                ProductOption productOption = options.get(productVariantRequest.productOptionName()) == null ?
                    ProductOption.builder().name(productVariantRequest.productOptionName()).build() :
                    options.get(productVariantRequest.productOptionName());

                List<ProductVariantImage> images = productVariantImageService
                    .createProductVariantImages(productVariantRequest.productVariantImageRequests());

                var productVariant = productVariantMapper.convertProductVariant(productVariantRequest);
                productVariant.setProductOption(productOption);
                productVariant.setImages(images);

                return productVariant;
            })
            .toList();
    }

    @Transactional
    public void recoveryAllById(List<Long> productVariantIds) {
        if (productVariantIds == null || productVariantIds.isEmpty()){
            return;
        }
        productVariantRepository.recoveryAllById(productVariantIds);
    }

    @Transactional
    public Long updateProductVariant(
        Long productVariantId,
        ProductVariantRequest productVariantRequest) {

        var updatingVariant = findById(productVariantId);
        ProductVariant newVariant = productVariantMapper
            .copyToProductVariant(updatingVariant);

        ValidatorPipeline<ProductVariantRequest> validatorPipeline = new ValidatorPipeline<>();

        if (!Objects.equals(productVariantRequest.variantId(), updatingVariant.getVariantId())){
            validatorPipeline
                .add(productVariantValidatorSteps.hasUniqueVariantId(ProductVariantRequest::variantId));
            newVariant.setVariantId(productVariantRequest.variantId());
            log.warn("finish validate and set variantId");
        }

        if (!Objects.equals(productVariantRequest.slug(), updatingVariant.getSlug())){
            validatorPipeline
                .add(productVariantValidatorSteps.hasUniqueSlug(ProductVariantRequest::slug));
            newVariant.setSlug(productVariantRequest.slug());
        }

        if (!Objects.equals(updatingVariant.getProductOption().getName(), productVariantRequest.productOptionName())
        ){
            validatorPipeline
                .add(productVariantValidatorSteps.hasUniqueProductAndProductOption(
                    newVariant.getProduct().getId(),
                    ProductVariantRequest::productOptionName));
            updateProductOption(newVariant, productVariantRequest.productOptionName());
        }

        newVariant.setImages(
            productVariantImageService.updateInProductVariant(
                updatingVariant.getImages(),
                productVariantRequest.productVariantImageRequests())
            );

        validatorPipeline.add(
            productVariantValidatorSteps.hasDiscountAndPriceAfterAllSet(
            ProductVariantRequest::discount,
            ProductVariantRequest::price)
        );
        newVariant.setDiscount(productVariantRequest.discount());
        newVariant.setPrice(productVariantRequest.price());

        newVariant.setName(productVariantRequest.name());
        newVariant.setOriginalPrice(productVariantRequest.originalPrice());
        newVariant.setStatus(productVariantRequest.status());

        validatorPipeline.validate(productVariantRequest);
        return productVariantRepository.save(newVariant).getId();
    }

    private void updateProductOption(ProductVariant productVariant, String productOptionName){
        ProductOption productOption = productOptionService.getReferenceByName(productOptionName);

        if (productOption == null) {
            productVariant.setProductOption(
                ProductOption
                    .builder()
                    .name(productOptionName)
                    .build()
            );
        } else {
            productVariant.setProductOption(productOption);
        }
    }

    private ProductVariant findById(Long productVariantId) {
        return productVariantRepository.findById(productVariantId)
            .orElseThrow(()->new EntityNotFoundByIDException("Product variant", productVariantId.toString()));
    }

    public ProductVariant getReferenceById(Long id){
        if (!productVariantRepository.existsById(id)) {
            throw new EntityNotFoundByIDException("Product variant", id.toString());
        }
        return  productVariantRepository.getReferenceById(id);
    }

    public PageResponse<ProductVariantResponse> findAll(Pageable pageable) {
        var pageResult = productVariantRepository.findAll(pageable);
        return PageResponse.fromPage(
            pageResult,
            pageResult.stream()
                .map((productVariant) -> 
                    productVariantMapper.convertToProductVariantResponse(productVariant, null)    
                )
                .toList()
        );
    }

    public PageResponse<ProductVariantResponse> findAllDisplayed(Pageable pageable) {
        var pageResult = productVariantRepository.findAllDisplayed(pageable);
        return PageResponse.fromPage(
            pageResult,
            pageResult.stream()
                .map((productVariant) -> 
                    productVariantMapper.convertToProductVariantResponse(productVariant, null)    
                )
                .toList()
        );
    }

    // TODO: not be implemented
    public PageResponse<ProductVariantResponse> findAllByCategory(Pageable pageable, Long categoryId) {
        return null;
    }

    public ProductVariantResponse findProductVariantResponseById(long productVariantId) {
        var variant = productVariantRepository.findProductVariantFullDetailById(productVariantId)
            .orElseThrow(()-> new EntityNotFoundByIDException("Product variant", String.valueOf(productVariantId)));

        return productVariantMapper
            .convertToProductVariantResponse(variant);
    }
}
