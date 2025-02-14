package com.backend.feedbackservice.service;

import com.backend.feedbackservice.entity.FavouriteProduct;
import com.backend.feedbackservice.repository.FavouriteProductRepository;
import java.util.UUID;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class FavouriteProductServiceImpl implements
    FavouriteProductService {

  private final FavouriteProductRepository favouriteProductRepository;

  public FavouriteProductServiceImpl(FavouriteProductRepository favouriteProductRepository) {
    this.favouriteProductRepository = favouriteProductRepository;
  }

  @Override
  public Mono<FavouriteProduct> addProductToFavourites(Integer productId) {
    return favouriteProductRepository.save(new FavouriteProduct(UUID.randomUUID(), productId));
  }

  @Override
  public Mono<Void> removeProductFromFavourites(Integer productId) {
    return favouriteProductRepository.deleteByProductId(productId);
  }

  @Override
  public Mono<FavouriteProduct> findFavouriteProduct(Integer productId) {
    return favouriteProductRepository.findByProductId(productId);
  }

  @Override
  public Flux<FavouriteProduct> findFavouriteProducts() {
    return favouriteProductRepository.findAll();
  }
}
