package com.backend.feedbackservice.repository;

import com.backend.feedbackservice.entity.FavouriteProduct;
import java.util.UUID;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface FavouriteProductRepository extends ReactiveCrudRepository<FavouriteProduct, UUID> {

  Mono<Void> deleteByProductId(Integer productId);

  Mono<FavouriteProduct> findByProductId(Integer productId);
}
