package com.backend.customerapp.api;

import com.backend.customerapp.client.NewProductReviewPayload;
import com.backend.customerapp.client.ProductsClient;
import com.backend.customerapp.entity.Product;
import com.backend.customerapp.service.FavouriteProductService;
import com.backend.customerapp.service.ProductReviewsService;
import jakarta.validation.Valid;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping("customer/products/{productId:\\d+}")
public class ProductController {

  private final ProductsClient productsClient;

  private final FavouriteProductService favoriteProductsService;

  private final ProductReviewsService productReviewsService;


  public ProductController(ProductsClient productsClient,
      FavouriteProductService favoriteProductsService,
      ProductReviewsService productReviewsService) {
    this.productsClient = productsClient;
    this.favoriteProductsService = favoriteProductsService;
    this.productReviewsService = productReviewsService;
  }

  @ModelAttribute(name = "product", binding = false)
  public Mono<Product> loadProduct(@PathVariable("productId") Integer productId) {
    return productsClient.findProduct(productId)
        .switchIfEmpty(Mono.error(new NoSuchElementException("customer.products.error.not_found")));
  }


  @GetMapping
  public Mono<String> getProductPage(@PathVariable("productId") Integer productId, Model model) {
    model.addAttribute("inFavourite", false);
    return this.productReviewsService.getProductReviews(productId).collectList()
        .doOnNext(productReviews -> model.addAttribute("reviews", productReviews))
        .then(this.favoriteProductsService.findFavouriteProduct(productId)
            .doOnNext(favoriteProduct -> model.addAttribute("inFavourite", true)))
        .thenReturn("customer/products/product");
  }

  @PostMapping("add-to-favourites")
  public Mono<String> addProductToFavourites(@ModelAttribute Mono<Product> productMono) {
    return productMono.map(Product::id)
        .flatMap(productId -> this.favoriteProductsService.addProductToFavourites(productId)
            .thenReturn("redirect:/customer/products/%d".formatted(productId)));

  }

  @DeleteMapping("remove-from-favourites")
  public Mono<String> removeProductFromFavourites(@ModelAttribute Mono<Product> productMono) {
    return productMono.map(Product::id)
        .flatMap(productId -> this.favoriteProductsService.removeProductFromFavourites(productId)
            .thenReturn("redirect:/customer/products/%d".formatted(productId)));

  }

  @PostMapping("create-review")
  public Mono<String> createReview(@PathVariable("productId") Integer productId,
      @Valid NewProductReviewPayload payload, BindingResult bindingResult, Model model) {
    if (bindingResult.hasErrors()) {
      model.addAttribute("inFavourite", false);
      model.addAttribute("payload", payload);
      model.addAttribute("errors",
          bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage).toList());
      return Mono.just("/customer/products/product");

    } else {
      return this.productReviewsService.addProductReview(productId, payload.rating(),
              payload.review())
          .thenReturn("redirect:/customer/products/%d".formatted(productId));
    }

  }

  @ExceptionHandler(NoSuchElementException.class)
  public String handleNoSuchElementException(NoSuchElementException exception, Model model) {
    model.addAttribute("error", exception.getMessage());
    return "errors/404";
  }

}
