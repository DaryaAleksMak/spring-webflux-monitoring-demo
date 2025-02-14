package com.backend.customerapp.config;

import com.backend.customerapp.client.impl.WebClientFavouriteProductsClient;
import com.backend.customerapp.client.impl.WebClientProductReviewsClient;
import com.backend.customerapp.client.impl.WebClientProductsClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientConfig {

  @Bean
  public WebClientProductsClient webClientProductsClient(
      @Value("${customer.services.catalogue.uri}") String catalogueBaseUrl,
      WebClient.Builder selmagServicesWebClientBuilder
  ) {
    return new WebClientProductsClient(selmagServicesWebClientBuilder
        .baseUrl(catalogueBaseUrl)
        .build());
  }

  @Bean
  public WebClientFavouriteProductsClient webClientFavouriteProductsClient(
      @Value("${customer.services.feedback.uri}") String feedbackBaseUrl,
      WebClient.Builder selmagServicesWebClientBuilder
  ) {
    return new WebClientFavouriteProductsClient(selmagServicesWebClientBuilder
        .baseUrl(feedbackBaseUrl)
        .build());
  }

  @Bean
  public WebClientProductReviewsClient webClientProductReviewsClient(
      @Value("${customer.services.feedback.uri}") String feedbackBaseUrl,
      WebClient.Builder selmagServicesWebClientBuilder
  ) {
    return new WebClientProductReviewsClient(selmagServicesWebClientBuilder
        .baseUrl(feedbackBaseUrl)
        .build());
  }
}
