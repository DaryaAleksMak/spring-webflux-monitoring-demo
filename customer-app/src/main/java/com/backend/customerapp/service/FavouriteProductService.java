package com.backend.customerapp.service;

import com.backend.customerapp.entity.FavoriteProduct;
import reactor.core.publisher.Mono;

public interface FavouriteProductService {

  Mono<FavoriteProduct> addProductToFavourites(Integer productId);

  Mono<Void> removeProductFromFavourites(Integer productId);

  Mono<FavoriteProduct> findFavouriteProduct(Integer productId);
}
