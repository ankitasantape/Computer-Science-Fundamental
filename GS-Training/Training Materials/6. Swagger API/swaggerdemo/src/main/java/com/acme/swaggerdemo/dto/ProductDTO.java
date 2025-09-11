package com.acme.swaggerdemo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Schema(description = "Data transfer object representing a product")
public class ProductDTO {

    @NotBlank(message = "Product name is required")
    @Schema(description = "Name of the product", example = "iPhone 15 Pro", required = true)
    private String name;

    @NotNull(message = "Price must be provided")
    @Positive(message = "Price must be greater than 0")
    @Schema(description = "Price of the product", example = "1299.99", required = true)
    private Double price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
