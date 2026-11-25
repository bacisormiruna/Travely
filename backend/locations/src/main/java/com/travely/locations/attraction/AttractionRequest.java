package com.travely.locations.attraction;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record AttractionRequest(
        @NotBlank(message = "Name is required")
        @Size(max = 150)
        String name,

        @NotBlank(message = "Description is required")
        String description,

        @NotNull(message = "Category is required")
        Category category,

        @NotNull(message = "Location is required")
        Long locationId,

        @NotNull @DecimalMin("-90.0") @DecimalMax("90.0")
        Double latitude,

        @NotNull @DecimalMin("-180.0") @DecimalMax("180.0")
        Double longitude,

        @PositiveOrZero(message = "Entry price cannot be negative")
        BigDecimal entryPrice
) {}
