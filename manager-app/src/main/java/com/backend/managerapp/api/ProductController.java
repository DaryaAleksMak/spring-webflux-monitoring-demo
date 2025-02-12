package com.backend.managerapp.api;

import com.backend.managerapp.api.payload.UpdateProductPayload;
import com.backend.managerapp.client.ProductRestClient;
import com.backend.managerapp.entity.Product;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("catalogue/products/{productId:\\d+}")

public class ProductController {

  private final ProductRestClient productRestClient;

  @ModelAttribute("product")
  public Product product(@PathVariable("productId") int productId) {
    return productRestClient.findProduct(productId)
        .orElseThrow(() -> new NoSuchElementException("Product was not found"));
  }

  @GetMapping
  public String getProduct() {
    return "catalogue/products/product";
  }

  @GetMapping("edit")
  public String getProductEditPage() {
    return "catalogue/products/edit";
  }

  @PostMapping("edit")
  public String updateProduct(@ModelAttribute(name = "product", binding = false) Product product, @Valid UpdateProductPayload payload, Model model,BindingResult bindingResult) {

    if (bindingResult.hasErrors()) {
      model.addAttribute("payload", payload);
      model.addAttribute("errors", bindingResult.getAllErrors().stream()
          .map(ObjectError::getDefaultMessage)
          .toList());
      return "catalogue/products/edit";
    } else {
      productRestClient.updateProduct(product.id(), payload.title(), payload.details());
      return "redirect:/catalogue/products/%d".formatted(product.id());
    }
    }

  @PostMapping("delete")
  public String deleteProduct(@ModelAttribute(name = "product", binding = false) Product product) {
    productRestClient.deleteProduct(product.id());
    return "redirect:/catalogue/products/list";
  }

  @ExceptionHandler(NoSuchElementException.class)
  public String handleNoSuchElementException(NoSuchElementException exception, Model model,
      HttpServletResponse response) {
    model.addAttribute("exception", exception.getMessage());
    response.setStatus(HttpStatus.NOT_FOUND.value());
    return "errors/404";
  }
}
