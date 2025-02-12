package com.backend.catalogueservice.service;

import com.backend.catalogueservice.entity.Product;
import com.backend.catalogueservice.repository.ProductRepository;
import jakarta.transaction.Transactional;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultProductService implements ProductService {

  private final ProductRepository repository;

  @Override
  public Iterable<Product> findAllProducts(String filter) {
    if (filter != null && !filter.isBlank()) {
      return repository.findAllByTitleLikeIgnoreCase("%" + filter + "%");
    } else {
      return repository.findAll();
    }
  }

  @Override
  @Transactional
  public Product createProduct(String title, String details) {
    return repository.save(new Product(null, title, details));
  }

  @Override
  public Optional<Product> findProduct(int productId) {
    return repository.findById(productId);
  }

  @Override
  @Transactional
  public void updateProduct(Integer id, String title, String details) {
    repository.findById(id)
        .ifPresentOrElse(product -> {
          product.setTitle(title);
          product.setDetails(details);
        }, NoSuchElementException::new);
  }

  @Override
  @Transactional
  public void deleteProduct(Integer id) {
    repository.deleteById(id);
  }
}
