package com.backend.feedbackservice.service;


import com.backend.feedbackservice.entity.ProductReview;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductReviewsService {

  Mono<ProductReview> addProductReview(int productId, int rating, String review);

  Flux<ProductReview> getProductReviews(int productId);
}
