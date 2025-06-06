package com.thienan.product_service.core.product.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.thienan.product_service.common.PageResponse;
import com.thienan.product_service.core.category.CategoryClient;
import com.thienan.product_service.core.product.dto.ProductInformationRequest;
import com.thienan.product_service.core.product.dto.ProductRequest;
import com.thienan.product_service.core.product.dto.ProductResponse;
import com.thienan.product_service.core.product.entity.Product;
import com.thienan.product_service.core.product.enums.ProductStatus;
import com.thienan.product_service.core.product.mapper.ProductMapper;
import com.thienan.product_service.core.product.repository.ProductRepository;
import com.thienan.product_service.core.product.specification.ProductSpecification;
import com.thienan.product_service.core.product.validator.ProductValidatorSteps;
import com.thienan.product_service.core.product_variant.dto.ProductVariantRequest;
import com.thienan.product_service.core.product_variant.entity.ProductVariant;
import com.thienan.product_service.core.product_variant.service.ProductVariantService;
import com.thienan.product_service.handler.exceptions.common.EntityNotFoundByIDException;
import com.thienan.product_service.pipelines.validator.ValidatorPipeline;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    private final CategoryClient categoryClient;
    private final ProductVariantService productVariantService;
    private final ProductValidatorSteps productValidator;

    public Long createAndSave(ProductRequest productRequest){
        ValidatorPipeline<ProductRequest> validatorPipeline = new ValidatorPipeline<ProductRequest>()
            .add(productValidator.checkUniqueCode(ProductRequest::code));
        validatorPipeline.validate(productRequest);

        Product product = productMapper.newProductWithInformation(productRequest);

        if (productRequest.categoryId() != null){
            var category = categoryClient.findById(productRequest.categoryId());
            product.setCategory(category);
        }

        List<ProductVariant> productVariants = productVariantService.createProductVariants(productRequest.productVariants());
        product.setProductVariants(productVariants);

        return productRepository.save(product).getId();
    }

    public Long updateProductInformation(Long id, @Valid ProductInformationRequest productInformationRequest) {
        ValidatorPipeline<ProductInformationRequest> validatorPipeline = new ValidatorPipeline<ProductInformationRequest>()
            .add(productValidator.checkUniqueCode(ProductInformationRequest::code));
        validatorPipeline.validate(productInformationRequest);

        var updatingProduct = findById(id);

        if (updatingProduct.getCode() == null || !updatingProduct.getCode().equals(productInformationRequest.code())){
            updatingProduct.setCode(productInformationRequest.code());
        }

        if (productInformationRequest.categoryId() == null){
            updatingProduct.setCategory(null);
        } else if (updatingProduct.getCategory() == null ||
                !updatingProduct.getCategory().getId().equals(productInformationRequest.categoryId())){
            var newCategory = categoryClient.findById(productInformationRequest.categoryId());
            updatingProduct.setCategory(newCategory);
        }

        updatingProduct.setName(productInformationRequest.name());
        updatingProduct.setDescription(productInformationRequest.description());
        updatingProduct.setStatus(productInformationRequest.status());
        return productRepository.save(updatingProduct).getId();
    }

    public Long addProductVariant(Long productId, ProductVariantRequest productVariantRequest) {
        var updatingProduct = findWithFullVariantsById(productId);

        ProductVariant newProductVariant = productVariantService.createProductVariant(productVariantRequest, productId);
        updatingProduct.addProductVariant(newProductVariant);

        productRepository.save(updatingProduct);

        return newProductVariant.getId();
    }

    public Long removeProductVariant(Long productId, Long productVariantId){
        var updatingProduct = findWithFullVariantsById(productId);

        boolean isDeleted = updatingProduct.getProductVariants().removeIf(variant->variant.getId().equals(productVariantId));

        if (!isDeleted){
            throw new EntityNotFoundByIDException("Product variant", productVariantId.toString());
        }

        return productRepository.save(updatingProduct).getId();
    }

    public Product findById(Long id){
        return productRepository.findById(id)
            .orElseThrow(()-> new EntityNotFoundByIDException("Prodyct", id.toString()));
    }

    public Product findWithFullVariantsById(Long id){
        return productRepository.findWithFullVariantsById(id)
            .orElseThrow(()-> new EntityNotFoundByIDException("Product", id.toString()));
    }

    public ProductResponse findProductResponseWithVariantsById(Long id) {
        var product = productRepository.findWithFullVariantsById(id)
            .orElseThrow(()->new EntityNotFoundByIDException("Product", id.toString()));
        return productMapper.convertToProductResponse(product);
    }

    public PageResponse<ProductResponse> findAll(Pageable pageable) {
        var pageResult = productRepository.findAll(pageable);
        return PageResponse.fromPage(
            pageResult,
            pageResult.stream()
                .map(productMapper::convertToProductResponse)
                .toList()
        );
    }

    public PageResponse<ProductResponse> search(
        Pageable pageable,
        String name,
        String code,
        Long categoryId,
        String categoryCode,
        String categoryName,
        ProductStatus status) {
        var specification = ProductSpecification.hasName(name)
            .and(ProductSpecification.hasCode(code))
            .and(ProductSpecification.hasCategoryId(categoryId))
            .and(ProductSpecification.hasCategoryName(categoryName))
            .and(ProductSpecification.hasCategoryCode(categoryCode))
            .and(ProductSpecification.hasStatus(status));

        var pageResult = productRepository.findAll(specification, pageable);
        return PageResponse.fromPage(
            pageResult,
            pageResult.stream()
                .map(productMapper::convertToProductResponse)
                .toList()
        );
    }

    public PageResponse<ProductResponse> findAllDeleted(Pageable pageable) {
        var pageResult = productRepository.findAllDeleted(pageable);
        return PageResponse.fromPage(
            pageResult,
            pageResult.stream()
                .map(productMapper::convertToProductResponse)
                .toList()
        );
    }

    public void softDeleteById(Long id) {
        productRepository.deleteById(id);
    }

    public void hardDeleteById(Long id) {
        productRepository.hardDeleteById(id);
    }

    @Transactional
    public Long recovery(Long id){
        productRepository.recoveryById(id);
        List<Long> productVariantIds =
                productRepository.findProductVariants_IdById(id);
        productVariantService.recoveryAllById(productVariantIds);
        return id;
    }
}
