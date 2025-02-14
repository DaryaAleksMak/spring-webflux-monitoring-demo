package com.backend.feedbackservice.service;

import com.backend.feedbackservice.entity.FavouriteProduct;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FavouriteProductService {

  Mono<FavouriteProduct> addProductToFavourites(Integer productId);

  Mono<Void> removeProductFromFavourites(Integer productId);

  Mono<FavouriteProduct> findFavouriteProduct(Integer productId);

  Flux<FavouriteProduct> findFavouriteProducts();
}
