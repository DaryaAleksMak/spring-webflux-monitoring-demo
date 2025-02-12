package com.backend.customerapp.repository;

import com.backend.customerapp.entity.FavoriteProduct;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FavouriteProductRepository {

  Mono<FavoriteProduct> save(FavoriteProduct product);

  Mono<Void> deleteByProductId(Integer productId);

  Mono<FavoriteProduct> findByProductId(Integer productId);

  Flux<FavoriteProduct> findAll();
}
