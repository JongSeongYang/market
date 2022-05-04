package com.market.api.controller;

import com.market.api.domain.MemberEntity;
import com.market.api.dto.Auth;
import com.market.api.dto.Product;
import com.market.api.exception.ExceptionCode;
import com.market.api.service.AuthService;
import com.market.api.service.MemberService;
import com.market.api.service.ProductService;
import com.market.api.utils.annotation.Authenticate;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @Authenticate
    @ApiOperation(value = "상품 등록", notes = "상품 등록")
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product.ProductCommonResponse> create(@RequestBody Product.ProductRequest request) {
        Product.ProductCommonResponse response = productService.create(request);
        return ResponseEntity.ok(response);
    }

    @Authenticate
    @ApiOperation(value = "상품 수정", notes = "상품 수정")
    @PostMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product.ProductCommonResponse> update(@RequestBody Product.ProductRequest request,
                                                                @PathVariable("id") Long id) {
        Product.ProductCommonResponse response = productService.update(id, request);
        return ResponseEntity.ok(response);
    }

    @Authenticate
    @ApiOperation(value = "상품 삭제", notes = "상품 삭제")
    @DeleteMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product.ProductCommonResponse> delete(@PathVariable("id") Long id) {
        Product.ProductCommonResponse response = productService.delete(id);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "상품 리스트", notes = "상품 리스트")
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<Product.ProductListResponse>> list(@RequestParam Integer page) {
        Page<Product.ProductListResponse> response = productService.getList(page);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "상품 조회", notes = "상품 조회")
    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product.ProductResponse> getProduct(@PathVariable("id") Long id) {
        Product.ProductResponse response = productService.getProduct(id);
        return ResponseEntity.ok(response);
    }
}
