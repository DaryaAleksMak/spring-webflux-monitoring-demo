package com.backend.catalogueservice.controller.payload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record NewProductPayload(
    @NotNull @Size(min = 3, max = 50, message = "{catalogue.products.create.errors.title_is_null}") String title,
    @Size(max = 1000, message = "{catalogue.products.create.errors.details_size_is_invalid}") String details) {


}
