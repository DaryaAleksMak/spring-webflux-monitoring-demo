package com.backend.customerapp.client.impl;

import com.backend.customerapp.client.ProductsClient;
import com.backend.customerapp.entity.Product;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class WebClientProductsClient implements ProductsClient {

  private final WebClient webClient;

  public WebClientProductsClient(WebClient webClient) {
    this.webClient = webClient;
  }

  @Override
  public Flux<Product> findAllProduct(String filter) {
    return webClient.get()
        .uri("/catalogue-api/products?filter={filter}", filter)
        .retrieve()
        .bodyToFlux(Product.class);
  }

  @Override
  public Mono<Product> findProduct(Integer productId) {
    return this.webClient.get().uri("catalogue-api/products/{productId}", productId)
        .retrieve()
        .bodyToMono(Product.class)
        .onErrorComplete(WebClientResponseException.NotFound.class);
  }
}
