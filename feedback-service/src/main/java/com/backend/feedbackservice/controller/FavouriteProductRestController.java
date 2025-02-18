package com.backend.feedbackservice.controller;

import com.backend.feedbackservice.entity.FavouriteProduct;
import com.backend.feedbackservice.model.NewFavouriteProductPayload;
import com.backend.feedbackservice.service.FavouriteProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("feedback-api/favourite-products")
@RequiredArgsConstructor
public class FavouriteProductRestController {

  private final FavouriteProductService favouriteProductService;

  @GetMapping
  public Flux<FavouriteProduct> findFavouriteProducts() {
    return this.favouriteProductService.findFavouriteProducts();
  }


  @GetMapping("by-product-id/{productId:\\d+}")
  public Mono<FavouriteProduct> findFavouriteProductById(@PathVariable("productId") int productId) {
    return this.favouriteProductService.findFavouriteProduct(productId);
  }


  @PostMapping
  public Mono<ResponseEntity<FavouriteProduct>> addFavouriteProduct(@Valid @RequestBody
  NewFavouriteProductPayload payload, UriComponentsBuilder uriComponentsBuilder) {
    return this.favouriteProductService.addProductToFavourites(payload.productId())
        .map(favouriteProduct ->
            ResponseEntity.created(
                    uriComponentsBuilder.replacePath("feedback-api/favourite-products/{id}")
                        .build(favouriteProduct.getId()))
                .body(favouriteProduct)
        );
  }


  @DeleteMapping("by-product-id/{productId:\\d+}")
  public Mono<ResponseEntity<Void>> removeProductFromFavourites(
      @PathVariable("productId") int productId) {
    return this.favouriteProductService.removeProductFromFavourites(productId)
        .then(Mono.just(ResponseEntity.noContent().build()));
  }
}
