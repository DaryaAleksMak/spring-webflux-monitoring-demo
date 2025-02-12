package com.backend.catalogueservice.controller;

import com.backend.catalogueservice.controller.payload.NewProductPayload;
import com.backend.catalogueservice.entity.Product;
import com.backend.catalogueservice.service.ProductService;
import jakarta.validation.Valid;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("catalogue-api/products")
@RequiredArgsConstructor
public class ProductsRestController {

  private final ProductService productService;


  @GetMapping
  public Iterable<Product> findProduct(@RequestParam(name="filter", required = false) String filter) {
    return productService.findAllProducts(filter);
  }

  @PostMapping
  public ResponseEntity<?> createProduct(@Valid @RequestBody NewProductPayload payload,
      BindingResult bindingResult, UriComponentsBuilder uriComponentsBuilder)
      throws BindException {
    if (bindingResult.hasErrors()) {
      if (bindingResult instanceof BindException exception) {
        throw exception;
      } else {
        throw new BindException(bindingResult);
      }

    } else {
      Product product = productService.createProduct(payload.title(), payload.details());
      return ResponseEntity.created(
              uriComponentsBuilder.replacePath("/catalogue-api/products/{productId}")
                  .build(Map.of("productId", product.getId())))
          .body(product);
    }
  }


}
