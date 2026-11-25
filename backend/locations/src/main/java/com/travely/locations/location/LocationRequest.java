package com.travely.locations.location;

import jakarta.validation.constraints.*;

public record LocationRequest(
        @NotBlank(message = "Name is required")
        @Size(max = 100)
        String name,

        @NotBlank(message = "Country is required")
        @Size(max = 100)
        String country,

        @DecimalMin(value = "-90.0") @DecimalMax(value = "90.0")
        Double latitude,

        @DecimalMin(value = "-180.0") @DecimalMax(value = "180.0")
        Double longitude
) {}
