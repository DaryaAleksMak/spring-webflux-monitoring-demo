package com.backend.customerapp.client;

import com.backend.customerapp.entity.ProductReview;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductReviewsClient {


  Mono<ProductReview> addProductReview(Integer productId, Integer rating, String review);

  Flux<ProductReview> getProductReviews(Integer productId);
}
