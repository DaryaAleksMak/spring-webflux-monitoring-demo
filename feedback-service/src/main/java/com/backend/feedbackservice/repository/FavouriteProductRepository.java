package com.backend.feedbackservice.repository;

import com.backend.feedbackservice.entity.FavouriteProduct;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FavouriteProductRepository {

  Mono<FavouriteProduct> save(FavouriteProduct product);

  Mono<Void> deleteByProductId(Integer productId);

  Mono<FavouriteProduct> findByProductId(Integer productId);

  Flux<FavouriteProduct> findAll();
}
