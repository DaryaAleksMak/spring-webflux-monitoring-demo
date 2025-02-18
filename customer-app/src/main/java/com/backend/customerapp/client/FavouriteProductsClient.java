package com.backend.customerapp.client;

import com.backend.customerapp.entity.FavouriteProduct;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FavouriteProductsClient {


  Flux<FavouriteProduct> findFavouriteProducts();

  Mono<FavouriteProduct> findFavouriteProductByProductId(Integer productId);

  Mono<FavouriteProduct> addProductToFavourites(Integer productId);

  Mono<Void> removeProductFromFavourites(Integer productId);
}
