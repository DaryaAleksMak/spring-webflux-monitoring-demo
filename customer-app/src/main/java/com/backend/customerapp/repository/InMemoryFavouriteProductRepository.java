package com.backend.customerapp.repository;

import com.backend.customerapp.entity.FavoriteProduct;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class InMemoryFavouriteProductRepository implements
    FavouriteProductRepository {

  private final List<FavoriteProduct> favoriteProductList = new ArrayList<FavoriteProduct>();

  @Override
  public Mono<FavoriteProduct> save(FavoriteProduct product) {
    favoriteProductList.add(product);
    return Mono.just(product);

  }

  @Override
  public Mono<Void> deleteByProductId(Integer productId) {
    favoriteProductList.removeIf(it -> it.getProductId() == productId);
    return Mono.empty();
  }

  @Override
  public Mono<FavoriteProduct> findByProductId(Integer productId) {
    return Flux.fromIterable(favoriteProductList)
        .filter(favoriteProduct -> favoriteProduct.getProductId() == productId).singleOrEmpty();
  }

  @Override
  public Flux<FavoriteProduct> findAll() {
    return Flux.fromIterable(favoriteProductList);
  }

}
