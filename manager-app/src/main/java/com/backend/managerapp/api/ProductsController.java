package com.backend.managerapp.api;

import com.backend.managerapp.api.payload.NewProductPayload;
import com.backend.managerapp.client.BadRequestException;
import com.backend.managerapp.client.ProductRestClient;
import com.backend.managerapp.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("catalogue/products")
public class ProductsController {

  private final ProductRestClient productRestClient;

  @GetMapping(value = "list")
  public String getProductsList(Model model,
      @RequestParam(name = "filter", required = false) String filter) {
    model.addAttribute("products", this.productRestClient.findAllProducts(filter));
    model.addAttribute("filter", filter);
    return "catalogue/products/list";
  }

  @GetMapping("create")
  public String getNewProductPage() {
    return "catalogue/products/new_product";
  }

  @PostMapping("create")
  public String createProduct(NewProductPayload payload,
      Model model) {
    try {
      Product product = productRestClient.createProduct(payload.title(), payload.details());
      return "redirect:/catalogue/products/%d".formatted(product.id());
    } catch (BadRequestException exception) {
      model.addAttribute("payload", payload);
      model.addAttribute("errors", exception.getErrors());
      return "catalogue/products/new_product";
    }
  }
}
