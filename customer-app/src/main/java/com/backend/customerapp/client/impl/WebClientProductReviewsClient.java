package com.backend.customerapp.client.impl;

import com.backend.customerapp.client.ProductReviewsClient;
import com.backend.customerapp.client.exception.ClientBadRequestException;
import com.backend.customerapp.client.payload.NewProductReviewPayload;
import com.backend.customerapp.entity.ProductReview;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class WebClientProductReviewsClient implements
    ProductReviewsClient {

  private final WebClient webClient;

  public WebClientProductReviewsClient(WebClient webClient) {
    this.webClient = webClient;
  }


  @Override
  public Flux<ProductReview> getProductReviews(Integer productId) {
    return this.webClient
        .get()
        .uri("/feedback-api/product-reviews/by-product-id/{productId}", productId)
        .retrieve()
        .bodyToFlux(ProductReview.class);
  }

  @Override
  public Mono<ProductReview> addProductReview(Integer productId, Integer rating, String review) {
    return this.webClient
        .post()
        .uri("/feedback-api/product-reviews")
        .bodyValue(new NewProductReviewPayload(productId, rating, review))
        .retrieve()
        .bodyToMono(ProductReview.class)
        .onErrorMap(WebClientResponseException.BadRequest.class,
            exception -> new ClientBadRequestException(
                "There was an error adding a review for this product.",
                exception, ((List<String>) exception.getResponseBodyAs(ProblemDetail.class)
                .getProperties().get("errors"))));
  }
}
