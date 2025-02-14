package com.backend.customerapp.client.impl;

import com.backend.customerapp.client.FavouriteProductsClient;
import com.backend.customerapp.client.exception.ClientBadRequestException;
import com.backend.customerapp.client.payload.NewFavouriteProductPayload;
import com.backend.customerapp.entity.FavouriteProduct;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class WebClientFavouriteProductsClient implements
    FavouriteProductsClient {

  private final WebClient webClient;

  public WebClientFavouriteProductsClient(WebClient webClient) {
    this.webClient = webClient;
  }

  @Override
  public Flux<FavouriteProduct> findFavouriteProducts() {
    return this.webClient
        .get()
        .uri("/feedback-api/favourite-products")
        .retrieve()
        .bodyToFlux(FavouriteProduct.class);
  }

  @Override
  public Mono<FavouriteProduct> findFavouriteProductByProductId(int productId) {
    return this.webClient
        .get()
        .uri("/feedback-api/favourite-products/by-product-id/{productId}", productId)
        .retrieve()
        .bodyToMono(FavouriteProduct.class)
        .onErrorComplete(WebClientResponseException.NotFound.class);
  }

  @Override
  public Mono<FavouriteProduct> addProductToFavourites(int productId) {
    return this.webClient
        .post()
        .uri("/feedback-api/favourite-products")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(new NewFavouriteProductPayload(productId))
        .retrieve()
        .bodyToMono(FavouriteProduct.class)
        .onErrorMap(WebClientResponseException.BadRequest.class,
            exception -> new ClientBadRequestException(
                "An error occurred while adding the product to favorites",
                exception, ((List<String>) exception.getResponseBodyAs(ProblemDetail.class)
                .getProperties().get("errors"))));
  }

  @Override
  public Mono<Void> removeProductFromFavourites(int productId) {
    return this.webClient
        .delete()
        .uri("/feedback-api/favourite-products/by-product-id/{productId}", productId)
        .retrieve()
        .toBodilessEntity()
        .then();
  }
}
