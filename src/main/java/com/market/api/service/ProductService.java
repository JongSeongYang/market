package com.market.api.service;

import com.market.api.config.AppConfig;
import com.market.api.domain.ProductEntity;
import com.market.api.dto.Product;
import com.market.api.exception.CustomResponseStatusException;
import com.market.api.exception.ExceptionCode;
import com.market.api.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final AppConfig appConfig;

    @Transactional
    public Long save(ProductEntity productEntity) {
        ProductEntity save = productRepository.save(productEntity);
        return save.getId();
    }

    @Transactional
    public Product.ProductCommonResponse create(Product.ProductRequest productRequest) {
        checkDuplicate(productRequest);
        try {
            ProductEntity productEntity = ProductEntity.builder()
                    .name(productRequest.getName())
                    .category(productRequest.getCategory())
                    .description(productRequest.getDescription())
                    .price(productRequest.getPrice())
                    .stock(productRequest.getStock())
                    .build();
            productRepository.save(productEntity);
        } catch (Exception e) {
            log.error("product create >> PRODUCT_CREATE_FAIL");
            throw new CustomResponseStatusException(ExceptionCode.PRODUCT_CREATE_FAIL, "");
        }
        return getResponse("CREATE_SUCCESS");
    }

    @Transactional
    public Product.ProductCommonResponse update(Long id, Product.ProductRequest request) {
        ProductEntity exist = findProductById(id);
        String name = request.getName();
        String category = request.getCategory();
        String description = request.getDescription();
        Integer price = request.getPrice();
        Integer stock = request.getStock();

        try {
            if (null != name) {
                if (!name.equals(exist.getName())) {
                    exist.setName(name);
                }
            }
            if (null != category) {
                if (!category.equals(exist.getCategory())) {
                    exist.setCategory(category);
                }
            }
            if (null != description) {
                if (!description.equals(exist.getDescription())) {
                    exist.setDescription(description);
                }
            }
            if (null != price) {
                if (!price.equals(exist.getPrice())) {
                    exist.setPrice(price);
                }
            }
            if (null != stock) {
                if (!stock.equals(exist.getStock())) {
                    exist.setStock(stock);
                }
            }
            productRepository.save(exist);
        } catch (Exception e) {
            log.error("product update >> PRODUCT_UPDATE_FAIL");
            throw new CustomResponseStatusException(ExceptionCode.PRODUCT_UPDATE_FAIL, "");
        }
        return getResponse("UPDATE_SUCCESS");
    }

    @Transactional
    public Product.ProductCommonResponse delete(Long id) {
        ProductEntity product = findProductById(id);
        LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
        try {
            product.setDeletedTime(now);
        } catch (Exception e) {
            log.error("product delete >> PRODUCT_UPDATE_FAIL");
            throw new CustomResponseStatusException(ExceptionCode.PRODUCT_DELETE_FAIL, "");
        }
        return getResponse("DELETE_SUCCESS");
    }

    public Page<Product.ProductListResponse> getList(int page) {
        Pageable pageable = PageRequest.of(page - 1, 20, Sort.by("createdTime").descending());
        Page<ProductEntity> list = productRepository.findAllByDeletedTimeIsNull(pageable);
        return list.map(
                l->{
                    return appConfig.modelMapper().map(l, Product.ProductListResponse.class);
                });
    }

    public Product.ProductResponse getProduct(Long id) {
        ProductEntity product = findProductById(id);
        return appConfig.modelMapper().map(product, Product.ProductResponse.class);
    }

    private ProductEntity findProductById(Long id) {
        return productRepository.findByIdAndDeletedTimeIsNull(id)
                .orElseThrow(() -> new CustomResponseStatusException(ExceptionCode.PRODUCT_NOT_FOUND, ""));
    }

    private void checkDuplicate(Product.ProductRequest productRequest) {
        if(productRepository.findByNameAndDeletedTimeIsNull(productRequest.getName()).isPresent()){
            log.error("product create >> PRODUCT_DUPLICATE");
            throw new CustomResponseStatusException(ExceptionCode.PRODUCT_DUPLICATE, "");
        }
    }

    private Product.ProductCommonResponse getResponse(String message) {
        return Product.ProductCommonResponse.builder()
                .result(true)
                .message(message)
                .build();
    }
}
