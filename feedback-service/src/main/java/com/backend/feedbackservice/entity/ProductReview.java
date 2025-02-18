package com.backend.feedbackservice.entity;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("product_review")
public class ProductReview {

  @Id
  UUID id;

  int productId;

  int rating;

  String review;
}
