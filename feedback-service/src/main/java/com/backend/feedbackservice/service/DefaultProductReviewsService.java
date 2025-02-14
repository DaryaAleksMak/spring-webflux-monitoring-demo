package com.backend.feedbackservice.service;

import com.backend.feedbackservice.entity.ProductReview;
import com.backend.feedbackservice.repository.ProductReviewRepository;
import java.util.UUID;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class DefaultProductReviewsService implements
    ProductReviewsService {

  private final ProductReviewRepository productReviewRepository;

  public DefaultProductReviewsService(ProductReviewRepository productReviewRepository) {
    this.productReviewRepository = productReviewRepository;
  }

  @Override
  public Mono<ProductReview> addProductReview(int productId, int rating, String review) {
    return productReviewRepository.save(
        new ProductReview(UUID.randomUUID(), productId, rating, review));
  }

  @Override
  public Flux<ProductReview> getProductReviews(int productId) {
    return productReviewRepository.findAllByProductId(productId);
  }
}
