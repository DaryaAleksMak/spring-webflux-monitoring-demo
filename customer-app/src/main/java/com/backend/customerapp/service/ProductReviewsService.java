package com.backend.customerapp.service;

import com.backend.customerapp.entity.ProductReview;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductReviewsService {

  Mono<ProductReview> addProductReview(int productId, int rating, String review);

  Flux<ProductReview> getProductReviews(int productId);
}
