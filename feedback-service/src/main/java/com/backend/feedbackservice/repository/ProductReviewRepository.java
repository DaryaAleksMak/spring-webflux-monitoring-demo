package com.backend.feedbackservice.repository;


import com.backend.feedbackservice.entity.ProductReview;
import java.util.UUID;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface ProductReviewRepository extends ReactiveCrudRepository<ProductReview, UUID> {

  @Query("{'productId' : ?0}") //it's not necessary
  Flux<ProductReview> findAllByProductId(int productId);
}
