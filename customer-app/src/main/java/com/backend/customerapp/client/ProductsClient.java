package com.backend.customerapp.client;

import com.backend.customerapp.entity.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductsClient {

  Flux<Product> findAllProduct(String filter);

  Mono<Product> findProduct(Integer productId);
}
