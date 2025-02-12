package com.backend.customerapp.repository;

import com.backend.customerapp.entity.ProductReview;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class InMemoryProductReviewRepository implements
    ProductReviewRepository {

  private final List<ProductReview> productReviews = Collections.synchronizedList(
      new LinkedList<ProductReview>());

  @Override
  public Mono<ProductReview> save(ProductReview productReview) {
    productReviews.add(productReview);
    return Mono.just(productReview);
  }

  @Override
  public Flux<ProductReview> findAllByProductId(int productId) {
    return Flux.fromIterable(
        productReviews.stream()
            .filter(product -> product.productId() == productId)
            .toList()
    );
  }
}
