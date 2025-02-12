package com.backend.customerapp.entity;

import java.util.UUID;

public class FavoriteProduct {

  private UUID id;

  private int productId;

  public FavoriteProduct(UUID id, int productId) {
    this.id = id;
    this.productId = productId;
  }

  public FavoriteProduct() {
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public int getProductId() {
    return productId;
  }

  public void setProductId(int productId) {
    this.productId = productId;
  }
}
