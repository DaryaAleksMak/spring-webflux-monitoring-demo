package com.backend.feedbackservice.repository;

import com.backend.feedbackservice.entity.FavouriteProduct;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class InMemoryFavouriteProductRepository implements
    FavouriteProductRepository {

  private final List<FavouriteProduct> favouriteProductList = new ArrayList<FavouriteProduct>();

  @Override
  public Mono<FavouriteProduct> save(FavouriteProduct product) {
    favouriteProductList.add(product);
    return Mono.just(product);

  }

  @Override
  public Mono<Void> deleteByProductId(Integer productId) {
    favouriteProductList.removeIf(it -> it.getProductId() == productId);
    return Mono.empty();
  }

  @Override
  public Mono<FavouriteProduct> findByProductId(Integer productId) {
    return Flux.fromIterable(favouriteProductList)
        .filter(favouriteProduct -> favouriteProduct.getProductId() == productId).singleOrEmpty();
  }

  @Override
  public Flux<FavouriteProduct> findAll() {
    return Flux.fromIterable(favouriteProductList);
  }

}
