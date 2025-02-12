package com.backend.customerapp.service;

import com.backend.customerapp.entity.FavoriteProduct;
import com.backend.customerapp.repository.FavouriteProductRepository;
import java.util.UUID;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class FavouriteProductServiceImpl implements
    FavouriteProductService {

  private final FavouriteProductRepository favouriteProductRepository;

  public FavouriteProductServiceImpl(FavouriteProductRepository favouriteProductRepository) {
    this.favouriteProductRepository = favouriteProductRepository;
  }

  @Override
  public Mono<FavoriteProduct> addProductToFavourites(Integer productId) {
    return favouriteProductRepository.save(new FavoriteProduct(UUID.randomUUID(), productId));
  }

  @Override
  public Mono<Void> removeProductFromFavourites(Integer productId) {
    return favouriteProductRepository.deleteByProductId(productId);
  }

  @Override
  public Mono<FavoriteProduct> findFavouriteProduct(Integer productId) {
    return favouriteProductRepository.findByProductId(productId);
  }
}
