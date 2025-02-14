package com.backend.feedbackservice.controller;

import com.backend.feedbackservice.entity.ProductReview;
import com.backend.feedbackservice.model.NewRecordReviewPayload;
import com.backend.feedbackservice.service.ProductReviewsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("feedback-api/product-reviews")
@RequiredArgsConstructor
public class ProductReviewsRestController {

  private final ProductReviewsService productReviewsService;

  @GetMapping("by-product-id/{productId}")
  public Flux<ProductReview> findProductReviewsByProductId(
      @PathVariable("productId") int productId) {
    return this.productReviewsService.getProductReviews(productId);
  }

  @PostMapping
  public Mono<ResponseEntity<ProductReview>> addProductReview(
      @Valid @RequestBody Mono<NewRecordReviewPayload> payloadMono,
      UriComponentsBuilder uriComponentsBuilder) {
    return payloadMono.flatMap(
            payload -> this.productReviewsService.addProductReview(payload.productId(),
                payload.rating(), payload.reviews()))
        .map(review -> ResponseEntity.created(
            uriComponentsBuilder.replacePath("/feedback-api/product-reviews/{id}")
                .build(review.id())).body(review));

  }





}
