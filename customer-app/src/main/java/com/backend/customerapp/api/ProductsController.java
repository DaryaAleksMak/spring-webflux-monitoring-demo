package com.backend.customerapp.api;

import com.backend.customerapp.client.impl.WebClientProductsClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping("customer/products")
public class ProductsController {

  private final WebClientProductsClient productsClient;

  public ProductsController(WebClientProductsClient productsClient) {
    this.productsClient = productsClient;
  }

  @GetMapping("list")
  public Mono<String> getProductsListPage(Model model,
      @RequestParam(name = "filter", required = false) String filter) {
    model.addAttribute("filter", filter);
    return productsClient.findAllProduct(filter).collectList()
        .doOnNext(products -> model.addAttribute("products", products))
        .thenReturn("customer/products/list");
  }


}

